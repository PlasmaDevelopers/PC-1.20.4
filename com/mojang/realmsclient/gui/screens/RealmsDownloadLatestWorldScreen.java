/*     */ package com.mojang.realmsclient.gui.screens;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.util.concurrent.RateLimiter;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.realmsclient.Unit;
/*     */ import com.mojang.realmsclient.client.FileDownload;
/*     */ import com.mojang.realmsclient.dto.WorldDownload;
/*     */ import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.client.GameNarrator;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.FormattedText;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.realms.RealmsScreen;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class RealmsDownloadLatestWorldScreen extends RealmsScreen {
/*  29 */   private static final Logger LOGGER = LogUtils.getLogger();
/*  30 */   private static final ReentrantLock DOWNLOAD_LOCK = new ReentrantLock();
/*     */   
/*     */   private static final int BAR_WIDTH = 200;
/*     */   
/*     */   private static final int BAR_TOP = 80;
/*     */   
/*     */   private static final int BAR_BOTTOM = 95;
/*     */   private static final int BAR_BORDER = 1;
/*     */   private final Screen lastScreen;
/*     */   private final WorldDownload worldDownload;
/*     */   private final Component downloadTitle;
/*     */   private final RateLimiter narrationRateLimiter;
/*     */   private Button cancelButton;
/*     */   private final String worldName;
/*     */   private final DownloadStatus downloadStatus;
/*     */   @Nullable
/*     */   private volatile Component errorMessage;
/*  47 */   private volatile Component status = (Component)Component.translatable("mco.download.preparing");
/*     */   
/*     */   @Nullable
/*     */   private volatile String progress;
/*     */   
/*     */   private volatile boolean cancelled;
/*     */   private volatile boolean showDots = true;
/*     */   private volatile boolean finished;
/*     */   private volatile boolean extracting;
/*     */   @Nullable
/*     */   private Long previousWrittenBytes;
/*     */   @Nullable
/*     */   private Long previousTimeSnapshot;
/*     */   private long bytesPersSecond;
/*     */   private int animTick;
/*  62 */   private static final String[] DOTS = new String[] { "", ".", ". .", ". . ." };
/*     */   
/*     */   private int dotIndex;
/*     */   private boolean checked;
/*     */   private final BooleanConsumer callback;
/*     */   
/*     */   public RealmsDownloadLatestWorldScreen(Screen $$0, WorldDownload $$1, String $$2, BooleanConsumer $$3) {
/*  69 */     super(GameNarrator.NO_TITLE);
/*  70 */     this.callback = $$3;
/*  71 */     this.lastScreen = $$0;
/*  72 */     this.worldName = $$2;
/*  73 */     this.worldDownload = $$1;
/*  74 */     this.downloadStatus = new DownloadStatus();
/*  75 */     this.downloadTitle = (Component)Component.translatable("mco.download.title");
/*  76 */     this.narrationRateLimiter = RateLimiter.create(0.10000000149011612D);
/*     */   }
/*     */ 
/*     */   
/*     */   public void init() {
/*  81 */     this.cancelButton = (Button)addRenderableWidget((GuiEventListener)Button.builder(CommonComponents.GUI_CANCEL, $$0 -> {
/*     */             this.cancelled = true;
/*     */             backButtonClicked();
/*  84 */           }).bounds((this.width - 200) / 2, this.height - 42, 200, 20).build());
/*  85 */     checkDownloadSize();
/*     */   }
/*     */   
/*     */   private void checkDownloadSize() {
/*  89 */     if (this.finished) {
/*     */       return;
/*     */     }
/*     */     
/*  93 */     if (!this.checked && getContentLength(this.worldDownload.downloadLink) >= 5368709120L) {
/*  94 */       MutableComponent mutableComponent1 = Component.translatable("mco.download.confirmation.line1", new Object[] { Unit.humanReadable(5368709120L) });
/*  95 */       MutableComponent mutableComponent2 = Component.translatable("mco.download.confirmation.line2");
/*  96 */       this.minecraft.setScreen((Screen)new RealmsLongConfirmationScreen($$0 -> { this.checked = true; this.minecraft.setScreen((Screen)this); downloadSave(); }RealmsLongConfirmationScreen.Type.WARNING, (Component)mutableComponent1, (Component)mutableComponent2, false));
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 102 */       downloadSave();
/*     */     } 
/*     */   }
/*     */   
/*     */   private long getContentLength(String $$0) {
/* 107 */     FileDownload $$1 = new FileDownload();
/* 108 */     return $$1.contentLength($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 113 */     super.tick();
/*     */     
/* 115 */     this.animTick++;
/*     */     
/* 117 */     if (this.status != null && 
/* 118 */       this.narrationRateLimiter.tryAcquire(1)) {
/* 119 */       Component $$0 = createProgressNarrationMessage();
/* 120 */       this.minecraft.getNarrator().sayNow($$0);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private Component createProgressNarrationMessage() {
/* 126 */     List<Component> $$0 = Lists.newArrayList();
/* 127 */     $$0.add(this.downloadTitle);
/* 128 */     $$0.add(this.status);
/* 129 */     if (this.progress != null) {
/* 130 */       $$0.add(Component.translatable("mco.download.percent", new Object[] { this.progress }));
/* 131 */       $$0.add(Component.translatable("mco.download.speed.narration", new Object[] { Unit.humanReadable(this.bytesPersSecond) }));
/*     */     } 
/* 133 */     if (this.errorMessage != null) {
/* 134 */       $$0.add(this.errorMessage);
/*     */     }
/* 136 */     return CommonComponents.joinLines($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean keyPressed(int $$0, int $$1, int $$2) {
/* 141 */     if ($$0 == 256) {
/* 142 */       this.cancelled = true;
/* 143 */       backButtonClicked();
/* 144 */       return true;
/*     */     } 
/* 146 */     return super.keyPressed($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   private void backButtonClicked() {
/* 150 */     if (this.finished && this.callback != null && this.errorMessage == null) {
/* 151 */       this.callback.accept(true);
/*     */     }
/*     */     
/* 154 */     this.minecraft.setScreen(this.lastScreen);
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 159 */     super.render($$0, $$1, $$2, $$3);
/*     */     
/* 161 */     $$0.drawCenteredString(this.font, this.downloadTitle, this.width / 2, 20, 16777215);
/*     */     
/* 163 */     $$0.drawCenteredString(this.font, this.status, this.width / 2, 50, 16777215);
/*     */     
/* 165 */     if (this.showDots) {
/* 166 */       drawDots($$0);
/*     */     }
/*     */     
/* 169 */     if (this.downloadStatus.bytesWritten != 0L && !this.cancelled) {
/* 170 */       drawProgressBar($$0);
/* 171 */       drawDownloadSpeed($$0);
/*     */     } 
/*     */     
/* 174 */     if (this.errorMessage != null) {
/* 175 */       $$0.drawCenteredString(this.font, this.errorMessage, this.width / 2, 110, 16711680);
/*     */     }
/*     */   }
/*     */   
/*     */   private void drawDots(GuiGraphics $$0) {
/* 180 */     int $$1 = this.font.width((FormattedText)this.status);
/*     */     
/* 182 */     if (this.animTick % 10 == 0) {
/* 183 */       this.dotIndex++;
/*     */     }
/*     */     
/* 186 */     $$0.drawString(this.font, DOTS[this.dotIndex % DOTS.length], this.width / 2 + $$1 / 2 + 5, 50, 16777215, false);
/*     */   }
/*     */   
/*     */   private void drawProgressBar(GuiGraphics $$0) {
/* 190 */     double $$1 = Math.min(this.downloadStatus.bytesWritten / this.downloadStatus.totalBytes, 1.0D);
/* 191 */     this.progress = String.format(Locale.ROOT, "%.1f", new Object[] { Double.valueOf($$1 * 100.0D) });
/*     */     
/* 193 */     int $$2 = (this.width - 200) / 2;
/* 194 */     int $$3 = $$2 + (int)Math.round(200.0D * $$1);
/* 195 */     $$0.fill($$2 - 1, 79, $$3 + 1, 96, -2501934);
/* 196 */     $$0.fill($$2, 80, $$3, 95, -8355712);
/*     */     
/* 198 */     $$0.drawCenteredString(this.font, (Component)Component.translatable("mco.download.percent", new Object[] { this.progress }), this.width / 2, 84, 16777215);
/*     */   }
/*     */   
/*     */   private void drawDownloadSpeed(GuiGraphics $$0) {
/* 202 */     if (this.animTick % 20 == 0) {
/* 203 */       if (this.previousWrittenBytes != null) {
/* 204 */         long $$1 = Util.getMillis() - this.previousTimeSnapshot.longValue();
/* 205 */         if ($$1 == 0L) {
/* 206 */           $$1 = 1L;
/*     */         }
/* 208 */         this.bytesPersSecond = 1000L * (this.downloadStatus.bytesWritten - this.previousWrittenBytes.longValue()) / $$1;
/* 209 */         drawDownloadSpeed0($$0, this.bytesPersSecond);
/*     */       } 
/* 211 */       this.previousWrittenBytes = Long.valueOf(this.downloadStatus.bytesWritten);
/* 212 */       this.previousTimeSnapshot = Long.valueOf(Util.getMillis());
/*     */     } else {
/* 214 */       drawDownloadSpeed0($$0, this.bytesPersSecond);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void drawDownloadSpeed0(GuiGraphics $$0, long $$1) {
/* 219 */     if ($$1 > 0L) {
/* 220 */       int $$2 = this.font.width(this.progress);
/* 221 */       $$0.drawString(this.font, (Component)Component.translatable("mco.download.speed", new Object[] { Unit.humanReadable($$1) }), this.width / 2 + $$2 / 2 + 15, 84, 16777215, false);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void downloadSave() {
/* 227 */     (new Thread(() -> {
/*     */           try {
/*     */             if (!DOWNLOAD_LOCK.tryLock(1L, TimeUnit.SECONDS)) {
/*     */               this.status = (Component)Component.translatable("mco.download.failed");
/*     */               
/*     */               return;
/*     */             } 
/*     */             
/*     */             if (this.cancelled) {
/*     */               downloadCancelled();
/*     */               
/*     */               return;
/*     */             } 
/*     */             
/*     */             this.status = (Component)Component.translatable("mco.download.downloading", new Object[] { this.worldName });
/*     */             
/*     */             FileDownload $$0 = new FileDownload();
/*     */             
/*     */             $$0.contentLength(this.worldDownload.downloadLink);
/*     */             
/*     */             $$0.download(this.worldDownload, this.worldName, this.downloadStatus, this.minecraft.getLevelSource());
/*     */             while (!$$0.isFinished()) {
/*     */               if ($$0.isError()) {
/*     */                 $$0.cancel();
/*     */                 this.errorMessage = (Component)Component.translatable("mco.download.failed");
/*     */                 this.cancelButton.setMessage(CommonComponents.GUI_DONE);
/*     */                 return;
/*     */               } 
/*     */               if ($$0.isExtracting()) {
/*     */                 if (!this.extracting) {
/*     */                   this.status = (Component)Component.translatable("mco.download.extracting");
/*     */                 }
/*     */                 this.extracting = true;
/*     */               } 
/*     */               if (this.cancelled) {
/*     */                 $$0.cancel();
/*     */                 downloadCancelled();
/*     */                 return;
/*     */               } 
/*     */               try {
/*     */                 Thread.sleep(500L);
/* 268 */               } catch (InterruptedException $$1) {
/*     */                 LOGGER.error("Failed to check Realms backup download status");
/*     */               } 
/*     */             } 
/*     */             
/*     */             this.finished = true;
/*     */             this.status = (Component)Component.translatable("mco.download.done");
/*     */             this.cancelButton.setMessage(CommonComponents.GUI_DONE);
/* 276 */           } catch (InterruptedException $$2) {
/*     */             LOGGER.error("Could not acquire upload lock");
/* 278 */           } catch (Exception $$3) {
/*     */             this.errorMessage = (Component)Component.translatable("mco.download.failed");
/*     */             
/*     */             LOGGER.info("Exception while downloading world", $$3);
/*     */           } finally {
/*     */             if (DOWNLOAD_LOCK.isHeldByCurrentThread()) {
/*     */               DOWNLOAD_LOCK.unlock();
/*     */             } else {
/*     */               return;
/*     */             } 
/*     */             this.showDots = false;
/*     */             this.finished = true;
/*     */           } 
/* 291 */         })).start();
/*     */   }
/*     */   
/*     */   private void downloadCancelled() {
/* 295 */     this.status = (Component)Component.translatable("mco.download.cancelled");
/*     */   }
/*     */   
/*     */   public static class DownloadStatus {
/*     */     public volatile long bytesWritten;
/*     */     public volatile long totalBytes;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\gui\screens\RealmsDownloadLatestWorldScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */