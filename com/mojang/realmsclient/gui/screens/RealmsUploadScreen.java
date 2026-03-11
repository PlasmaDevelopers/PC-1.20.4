/*     */ package com.mojang.realmsclient.gui.screens;
/*     */ import com.google.common.util.concurrent.RateLimiter;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.realmsclient.RealmsMainScreen;
/*     */ import com.mojang.realmsclient.Unit;
/*     */ import com.mojang.realmsclient.client.FileUpload;
/*     */ import com.mojang.realmsclient.client.RealmsClient;
/*     */ import com.mojang.realmsclient.client.UploadStatus;
/*     */ import com.mojang.realmsclient.dto.UploadInfo;
/*     */ import com.mojang.realmsclient.exception.RealmsServiceException;
/*     */ import com.mojang.realmsclient.exception.RetryCallException;
/*     */ import com.mojang.realmsclient.util.UploadTokenCache;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ import java.util.zip.GZIPOutputStream;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.SharedConstants;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.client.GameNarrator;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.client.gui.screens.TitleScreen;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.FormattedText;
/*     */ import net.minecraft.realms.RealmsScreen;
/*     */ import net.minecraft.world.level.storage.LevelSummary;
/*     */ import org.apache.commons.compress.archivers.ArchiveEntry;
/*     */ import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
/*     */ import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
/*     */ import org.apache.commons.compress.utils.IOUtils;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class RealmsUploadScreen extends RealmsScreen {
/*  44 */   private static final Logger LOGGER = LogUtils.getLogger();
/*  45 */   private static final ReentrantLock UPLOAD_LOCK = new ReentrantLock();
/*     */   
/*     */   private static final int BAR_WIDTH = 200;
/*     */   
/*     */   private static final int BAR_TOP = 80;
/*     */   private static final int BAR_BOTTOM = 95;
/*     */   private static final int BAR_BORDER = 1;
/*  52 */   private static final String[] DOTS = new String[] { "", ".", ". .", ". . ." };
/*  53 */   private static final Component VERIFYING_TEXT = (Component)Component.translatable("mco.upload.verifying");
/*     */   
/*     */   private final RealmsResetWorldScreen lastScreen;
/*     */   
/*     */   private final LevelSummary selectedLevel;
/*     */   private final long worldId;
/*     */   private final int slotId;
/*     */   private final UploadStatus uploadStatus;
/*     */   private final RateLimiter narrationRateLimiter;
/*     */   @Nullable
/*     */   private volatile Component[] errorMessage;
/*  64 */   private volatile Component status = (Component)Component.translatable("mco.upload.preparing");
/*     */   
/*     */   private volatile String progress;
/*     */   
/*     */   private volatile boolean cancelled;
/*     */   
/*     */   private volatile boolean uploadFinished;
/*     */   
/*     */   private volatile boolean showDots = true;
/*     */   private volatile boolean uploadStarted;
/*     */   private Button backButton;
/*     */   private Button cancelButton;
/*     */   private int tickCount;
/*     */   @Nullable
/*     */   private Long previousWrittenBytes;
/*     */   @Nullable
/*     */   private Long previousTimeSnapshot;
/*     */   private long bytesPersSecond;
/*     */   
/*     */   public RealmsUploadScreen(long $$0, int $$1, RealmsResetWorldScreen $$2, LevelSummary $$3) {
/*  84 */     super(GameNarrator.NO_TITLE);
/*  85 */     this.worldId = $$0;
/*  86 */     this.slotId = $$1;
/*  87 */     this.lastScreen = $$2;
/*  88 */     this.selectedLevel = $$3;
/*  89 */     this.uploadStatus = new UploadStatus();
/*  90 */     this.narrationRateLimiter = RateLimiter.create(0.10000000149011612D);
/*     */   }
/*     */ 
/*     */   
/*     */   public void init() {
/*  95 */     this.backButton = (Button)addRenderableWidget((GuiEventListener)Button.builder(CommonComponents.GUI_BACK, $$0 -> onBack()).bounds((this.width - 200) / 2, this.height - 42, 200, 20).build());
/*  96 */     this.backButton.visible = false;
/*  97 */     this.cancelButton = (Button)addRenderableWidget((GuiEventListener)Button.builder(CommonComponents.GUI_CANCEL, $$0 -> onCancel()).bounds((this.width - 200) / 2, this.height - 42, 200, 20).build());
/*     */     
/*  99 */     if (!this.uploadStarted) {
/* 100 */       if (this.lastScreen.slot == -1) {
/* 101 */         upload();
/*     */       } else {
/* 103 */         this.lastScreen.switchSlot(() -> {
/*     */               if (!this.uploadStarted) {
/*     */                 this.uploadStarted = true;
/*     */                 this.minecraft.setScreen((Screen)this);
/*     */                 upload();
/*     */               } 
/*     */             });
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private void onBack() {
/* 115 */     this.minecraft.setScreen((Screen)new RealmsConfigureWorldScreen(new RealmsMainScreen((Screen)new TitleScreen()), this.worldId));
/*     */   }
/*     */   
/*     */   private void onCancel() {
/* 119 */     this.cancelled = true;
/* 120 */     this.minecraft.setScreen((Screen)this.lastScreen);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean keyPressed(int $$0, int $$1, int $$2) {
/* 125 */     if ($$0 == 256) {
/* 126 */       if (this.showDots) {
/* 127 */         onCancel();
/*     */       } else {
/* 129 */         onBack();
/*     */       } 
/* 131 */       return true;
/*     */     } 
/* 133 */     return super.keyPressed($$0, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 138 */     super.render($$0, $$1, $$2, $$3);
/*     */     
/* 140 */     if (!this.uploadFinished && this.uploadStatus.bytesWritten != 0L && this.uploadStatus.bytesWritten == this.uploadStatus.totalBytes) {
/* 141 */       this.status = VERIFYING_TEXT;
/* 142 */       this.cancelButton.active = false;
/*     */     } 
/*     */     
/* 145 */     $$0.drawCenteredString(this.font, this.status, this.width / 2, 50, 16777215);
/*     */     
/* 147 */     if (this.showDots) {
/* 148 */       drawDots($$0);
/*     */     }
/*     */     
/* 151 */     if (this.uploadStatus.bytesWritten != 0L && !this.cancelled) {
/* 152 */       drawProgressBar($$0);
/* 153 */       drawUploadSpeed($$0);
/*     */     } 
/*     */     
/* 156 */     if (this.errorMessage != null) {
/* 157 */       for (int $$4 = 0; $$4 < this.errorMessage.length; $$4++) {
/* 158 */         $$0.drawCenteredString(this.font, this.errorMessage[$$4], this.width / 2, 110 + 12 * $$4, 16711680);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void drawDots(GuiGraphics $$0) {
/* 164 */     int $$1 = this.font.width((FormattedText)this.status);
/*     */     
/* 166 */     $$0.drawString(this.font, DOTS[this.tickCount / 10 % DOTS.length], this.width / 2 + $$1 / 2 + 5, 50, 16777215, false);
/*     */   }
/*     */   
/*     */   private void drawProgressBar(GuiGraphics $$0) {
/* 170 */     double $$1 = Math.min(this.uploadStatus.bytesWritten / this.uploadStatus.totalBytes, 1.0D);
/* 171 */     this.progress = String.format(Locale.ROOT, "%.1f", new Object[] { Double.valueOf($$1 * 100.0D) });
/*     */     
/* 173 */     int $$2 = (this.width - 200) / 2;
/* 174 */     int $$3 = $$2 + (int)Math.round(200.0D * $$1);
/* 175 */     $$0.fill($$2 - 1, 79, $$3 + 1, 96, -2501934);
/* 176 */     $$0.fill($$2, 80, $$3, 95, -8355712);
/*     */     
/* 178 */     $$0.drawCenteredString(this.font, (Component)Component.translatable("mco.upload.percent", new Object[] { this.progress }), this.width / 2, 84, 16777215);
/*     */   }
/*     */   
/*     */   private void drawUploadSpeed(GuiGraphics $$0) {
/* 182 */     if (this.tickCount % 20 == 0) {
/* 183 */       if (this.previousWrittenBytes != null) {
/* 184 */         long $$1 = Util.getMillis() - this.previousTimeSnapshot.longValue();
/* 185 */         if ($$1 == 0L) {
/* 186 */           $$1 = 1L;
/*     */         }
/* 188 */         this.bytesPersSecond = 1000L * (this.uploadStatus.bytesWritten - this.previousWrittenBytes.longValue()) / $$1;
/* 189 */         drawUploadSpeed0($$0, this.bytesPersSecond);
/*     */       } 
/*     */       
/* 192 */       this.previousWrittenBytes = Long.valueOf(this.uploadStatus.bytesWritten);
/* 193 */       this.previousTimeSnapshot = Long.valueOf(Util.getMillis());
/*     */     } else {
/* 195 */       drawUploadSpeed0($$0, this.bytesPersSecond);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void drawUploadSpeed0(GuiGraphics $$0, long $$1) {
/* 200 */     if ($$1 > 0L) {
/* 201 */       int $$2 = this.font.width(this.progress);
/* 202 */       String $$3 = "(" + Unit.humanReadable($$1) + "/s)";
/* 203 */       $$0.drawString(this.font, $$3, this.width / 2 + $$2 / 2 + 15, 84, 16777215, false);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 209 */     super.tick();
/*     */     
/* 211 */     this.tickCount++;
/*     */     
/* 213 */     if (this.status != null && 
/* 214 */       this.narrationRateLimiter.tryAcquire(1)) {
/* 215 */       Component $$0 = createProgressNarrationMessage();
/* 216 */       this.minecraft.getNarrator().sayNow($$0);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private Component createProgressNarrationMessage() {
/* 222 */     List<Component> $$0 = Lists.newArrayList();
/* 223 */     $$0.add(this.status);
/* 224 */     if (this.progress != null) {
/* 225 */       $$0.add(Component.translatable("mco.upload.percent", new Object[] { this.progress }));
/*     */     }
/* 227 */     if (this.errorMessage != null) {
/* 228 */       $$0.addAll(Arrays.asList(this.errorMessage));
/*     */     }
/* 230 */     return CommonComponents.joinLines($$0);
/*     */   }
/*     */   
/*     */   private void upload() {
/* 234 */     this.uploadStarted = true;
/*     */     
/* 236 */     (new Thread(() -> {
/*     */           File $$0 = null;
/*     */           
/*     */           RealmsClient $$1 = RealmsClient.create();
/*     */           
/*     */           long $$2 = this.worldId;
/*     */           
/*     */           try {
/*     */             if (!UPLOAD_LOCK.tryLock(1L, TimeUnit.SECONDS)) {
/*     */               this.status = (Component)Component.translatable("mco.upload.close.failure");
/*     */               
/*     */               return;
/*     */             } 
/*     */             UploadInfo $$3 = null;
/*     */             for (int $$4 = 0; $$4 < 20; $$4++) {
/*     */               try {
/*     */                 if (this.cancelled) {
/*     */                   uploadCancelled();
/*     */                   return;
/*     */                 } 
/*     */                 $$3 = $$1.requestUploadInfo($$2, UploadTokenCache.get($$2));
/*     */                 if ($$3 != null) {
/*     */                   break;
/*     */                 }
/* 260 */               } catch (RetryCallException $$5) {
/*     */                 Thread.sleep(($$5.delaySeconds * 1000));
/*     */               } 
/*     */             } 
/*     */ 
/*     */             
/*     */             if ($$3 == null) {
/*     */               this.status = (Component)Component.translatable("mco.upload.close.failure");
/*     */ 
/*     */               
/*     */               return;
/*     */             } 
/*     */ 
/*     */             
/*     */             UploadTokenCache.put($$2, $$3.getToken());
/*     */ 
/*     */             
/*     */             if (!$$3.isWorldClosed()) {
/*     */               this.status = (Component)Component.translatable("mco.upload.close.failure");
/*     */ 
/*     */               
/*     */               return;
/*     */             } 
/*     */ 
/*     */             
/*     */             if (this.cancelled) {
/*     */               uploadCancelled();
/*     */ 
/*     */               
/*     */               return;
/*     */             } 
/*     */ 
/*     */             
/*     */             File $$6 = new File(this.minecraft.gameDirectory.getAbsolutePath(), "saves");
/*     */ 
/*     */             
/*     */             $$0 = tarGzipArchive(new File($$6, this.selectedLevel.getLevelId()));
/*     */             
/*     */             if (this.cancelled) {
/*     */               uploadCancelled();
/*     */               
/*     */               return;
/*     */             } 
/*     */             
/*     */             if (!verify($$0)) {
/*     */               long $$7 = $$0.length();
/*     */               
/*     */               Unit $$8 = Unit.getLargest($$7);
/*     */               
/*     */               Unit $$9 = Unit.getLargest(5368709120L);
/*     */               
/*     */               if (Unit.humanReadable($$7, $$8).equals(Unit.humanReadable(5368709120L, $$9)) && $$8 != Unit.B) {
/*     */                 Unit $$10 = Unit.values()[$$8.ordinal() - 1];
/*     */                 
/*     */                 setErrorMessage(new Component[] { (Component)Component.translatable("mco.upload.size.failure.line1", new Object[] { this.selectedLevel.getLevelName() }), (Component)Component.translatable("mco.upload.size.failure.line2", new Object[] { Unit.humanReadable($$7, $$10), Unit.humanReadable(5368709120L, $$10) }) });
/*     */                 
/*     */                 return;
/*     */               } 
/*     */               
/*     */               setErrorMessage(new Component[] { (Component)Component.translatable("mco.upload.size.failure.line1", new Object[] { this.selectedLevel.getLevelName() }), (Component)Component.translatable("mco.upload.size.failure.line2", new Object[] { Unit.humanReadable($$7, $$8), Unit.humanReadable(5368709120L, $$9) }) });
/*     */               
/*     */               return;
/*     */             } 
/*     */             
/*     */             this.status = (Component)Component.translatable("mco.upload.uploading", new Object[] { this.selectedLevel.getLevelName() });
/*     */             
/*     */             FileUpload $$11 = new FileUpload($$0, this.worldId, this.slotId, $$3, this.minecraft.getUser(), SharedConstants.getCurrentVersion().getName(), this.uploadStatus);
/*     */             
/*     */             $$11.upload(());
/*     */             
/*     */             while (!$$11.isFinished()) {
/*     */               if (this.cancelled) {
/*     */                 $$11.cancel();
/*     */                 
/*     */                 uploadCancelled();
/*     */                 
/*     */                 return;
/*     */               } 
/*     */               
/*     */               try {
/*     */                 Thread.sleep(500L);
/* 341 */               } catch (InterruptedException $$12) {
/*     */                 LOGGER.error("Failed to check Realms file upload status");
/*     */               } 
/*     */             } 
/* 345 */           } catch (IOException $$13) {
/*     */             setErrorMessage(new Component[] { (Component)Component.translatable("mco.upload.failed", new Object[] { $$13.getMessage() }) });
/* 347 */           } catch (RealmsServiceException $$14) {
/*     */             setErrorMessage(new Component[] { (Component)Component.translatable("mco.upload.failed", new Object[] { $$14.realmsError.errorMessage() }) });
/* 349 */           } catch (InterruptedException $$15) {
/*     */             LOGGER.error("Could not acquire upload lock");
/*     */           } finally {
/*     */             this.uploadFinished = true;
/*     */             
/*     */             if (UPLOAD_LOCK.isHeldByCurrentThread()) {
/*     */               UPLOAD_LOCK.unlock();
/*     */             } else {
/*     */               return;
/*     */             } 
/*     */             
/*     */             this.showDots = false;
/*     */             
/*     */             this.backButton.visible = true;
/*     */             
/*     */             this.cancelButton.visible = false;
/*     */             if ($$0 != null) {
/*     */               LOGGER.debug("Deleting file {}", $$0.getAbsolutePath());
/*     */               $$0.delete();
/*     */             } 
/*     */           } 
/* 370 */         })).start();
/*     */   }
/*     */   
/*     */   private void setErrorMessage(Component... $$0) {
/* 374 */     this.errorMessage = $$0;
/*     */   }
/*     */   
/*     */   private void uploadCancelled() {
/* 378 */     this.status = (Component)Component.translatable("mco.upload.cancelled");
/* 379 */     LOGGER.debug("Upload was cancelled");
/*     */   }
/*     */   
/*     */   private boolean verify(File $$0) {
/* 383 */     return ($$0.length() < 5368709120L);
/*     */   }
/*     */   
/*     */   private File tarGzipArchive(File $$0) throws IOException {
/* 387 */     TarArchiveOutputStream $$1 = null;
/*     */     try {
/* 389 */       File $$2 = File.createTempFile("realms-upload-file", ".tar.gz");
/* 390 */       $$1 = new TarArchiveOutputStream(new GZIPOutputStream(new FileOutputStream($$2)));
/* 391 */       $$1.setLongFileMode(3);
/* 392 */       addFileToTarGz($$1, $$0.getAbsolutePath(), "world", true);
/* 393 */       $$1.finish();
/* 394 */       return $$2;
/*     */     } finally {
/* 396 */       if ($$1 != null) {
/* 397 */         $$1.close();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void addFileToTarGz(TarArchiveOutputStream $$0, String $$1, String $$2, boolean $$3) throws IOException {
/* 403 */     if (this.cancelled) {
/*     */       return;
/*     */     }
/*     */     
/* 407 */     File $$4 = new File($$1);
/* 408 */     String $$5 = $$3 ? $$2 : ($$2 + $$2);
/* 409 */     TarArchiveEntry $$6 = new TarArchiveEntry($$4, $$5);
/* 410 */     $$0.putArchiveEntry((ArchiveEntry)$$6);
/*     */     
/* 412 */     if ($$4.isFile()) {
/* 413 */       IOUtils.copy(new FileInputStream($$4), (OutputStream)$$0);
/* 414 */       $$0.closeArchiveEntry();
/*     */     } else {
/* 416 */       $$0.closeArchiveEntry();
/* 417 */       File[] $$7 = $$4.listFiles();
/*     */       
/* 419 */       if ($$7 != null)
/* 420 */         for (File $$8 : $$7)
/* 421 */           addFileToTarGz($$0, $$8.getAbsolutePath(), $$5 + "/", false);  
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\gui\screens\RealmsUploadScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */