/*     */ package com.mojang.realmsclient.gui.screens;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.realmsclient.RealmsMainScreen;
/*     */ import com.mojang.realmsclient.client.RealmsClient;
/*     */ import com.mojang.realmsclient.dto.RealmsServer;
/*     */ import com.mojang.realmsclient.dto.RealmsWorldOptions;
/*     */ import com.mojang.realmsclient.dto.WorldDownload;
/*     */ import com.mojang.realmsclient.exception.RealmsServiceException;
/*     */ import com.mojang.realmsclient.gui.RealmsWorldSlotButton;
/*     */ import com.mojang.realmsclient.util.RealmsTextureManager;
/*     */ import com.mojang.realmsclient.util.task.LongRunningTask;
/*     */ import com.mojang.realmsclient.util.task.SwitchSlotTask;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.stream.Collectors;
/*     */ import java.util.stream.Stream;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.ComponentUtils;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.realms.RealmsScreen;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.Mth;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class RealmsBrokenWorldScreen extends RealmsScreen {
/*  34 */   private static final ResourceLocation SLOT_FRAME_SPRITE = new ResourceLocation("widget/slot_frame");
/*  35 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private static final int DEFAULT_BUTTON_WIDTH = 80;
/*     */   
/*     */   private final Screen lastScreen;
/*     */   
/*     */   @Nullable
/*     */   private RealmsServer serverData;
/*     */   
/*     */   private final long serverId;
/*  45 */   private final Component[] message = new Component[] {
/*  46 */       (Component)Component.translatable("mco.brokenworld.message.line1"), 
/*  47 */       (Component)Component.translatable("mco.brokenworld.message.line2")
/*     */     };
/*     */   
/*     */   private int leftX;
/*     */   
/*  52 */   private final List<Integer> slotsThatHasBeenDownloaded = Lists.newArrayList();
/*     */   
/*     */   private int animTick;
/*     */   
/*     */   public RealmsBrokenWorldScreen(Screen $$0, long $$1, boolean $$2) {
/*  57 */     super($$2 ? (Component)Component.translatable("mco.brokenworld.minigame.title") : (Component)Component.translatable("mco.brokenworld.title"));
/*  58 */     this.lastScreen = $$0;
/*  59 */     this.serverId = $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void init() {
/*  64 */     this.leftX = this.width / 2 - 150;
/*     */     
/*  66 */     addRenderableWidget((GuiEventListener)Button.builder(CommonComponents.GUI_BACK, $$0 -> onClose()).bounds((this.width - 150) / 2, row(13) - 5, 150, 20).build());
/*     */     
/*  68 */     if (this.serverData == null) {
/*  69 */       fetchServerData(this.serverId);
/*     */     } else {
/*  71 */       addButtons();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Component getNarrationMessage() {
/*  77 */     return ComponentUtils.formatList((Collection)Stream.concat(Stream.of(this.title), Stream.of((Object[])this.message)).collect(Collectors.toList()), CommonComponents.SPACE);
/*     */   }
/*     */   
/*     */   private void addButtons() {
/*  81 */     for (Iterator<Map.Entry<Integer, RealmsWorldOptions>> iterator = this.serverData.slots.entrySet().iterator(); iterator.hasNext(); ) { Button $$4; Map.Entry<Integer, RealmsWorldOptions> $$0 = iterator.next();
/*  82 */       int $$1 = ((Integer)$$0.getKey()).intValue();
/*  83 */       boolean $$2 = ($$1 != this.serverData.activeSlot || this.serverData.worldType == RealmsServer.WorldType.MINIGAME);
/*     */ 
/*     */       
/*  86 */       if ($$2) {
/*     */ 
/*     */         
/*  89 */         Button $$3 = Button.builder((Component)Component.translatable("mco.brokenworld.play"), $$1 -> this.minecraft.setScreen((Screen)new RealmsLongRunningMcoTaskScreen(this.lastScreen, new LongRunningTask[] { (LongRunningTask)new SwitchSlotTask(this.serverData.id, $$0, this::doSwitchOrReset) }))).bounds(getFramePositionX($$1), row(8), 80, 20).build();
/*  90 */         $$3.active = !((RealmsWorldOptions)this.serverData.slots.get(Integer.valueOf($$1))).empty;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 102 */         $$4 = Button.builder((Component)Component.translatable("mco.brokenworld.download"), $$1 -> { MutableComponent mutableComponent1 = Component.translatable("mco.configure.world.restore.download.question.line1"); MutableComponent mutableComponent2 = Component.translatable("mco.configure.world.restore.download.question.line2"); this.minecraft.setScreen((Screen)new RealmsLongConfirmationScreen((), RealmsLongConfirmationScreen.Type.INFO, (Component)mutableComponent1, (Component)mutableComponent2, true)); }).bounds(getFramePositionX($$1), row(8), 80, 20).build();
/*     */       } 
/*     */       
/* 105 */       if (this.slotsThatHasBeenDownloaded.contains(Integer.valueOf($$1))) {
/* 106 */         $$4.active = false;
/* 107 */         $$4.setMessage((Component)Component.translatable("mco.brokenworld.downloaded"));
/*     */       } 
/*     */       
/* 110 */       addRenderableWidget((GuiEventListener)$$4); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 116 */     this.animTick++;
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 121 */     super.render($$0, $$1, $$2, $$3);
/*     */     
/* 123 */     $$0.drawCenteredString(this.font, this.title, this.width / 2, 17, -1);
/*     */     
/* 125 */     for (int $$4 = 0; $$4 < this.message.length; $$4++) {
/* 126 */       $$0.drawCenteredString(this.font, this.message[$$4], this.width / 2, row(-1) + 3 + $$4 * 12, -6250336);
/*     */     }
/*     */     
/* 129 */     if (this.serverData == null) {
/*     */       return;
/*     */     }
/*     */     
/* 133 */     for (Map.Entry<Integer, RealmsWorldOptions> $$5 : (Iterable<Map.Entry<Integer, RealmsWorldOptions>>)this.serverData.slots.entrySet()) {
/* 134 */       if (((RealmsWorldOptions)$$5.getValue()).templateImage != null && ((RealmsWorldOptions)$$5.getValue()).templateId != -1L) {
/* 135 */         drawSlotFrame($$0, getFramePositionX(((Integer)$$5.getKey()).intValue()), row(1) + 5, $$1, $$2, (this.serverData.activeSlot == ((Integer)$$5.getKey()).intValue() && !isMinigame()), ((RealmsWorldOptions)$$5.getValue()).getSlotName(((Integer)$$5.getKey()).intValue()), ((Integer)$$5.getKey()).intValue(), ((RealmsWorldOptions)$$5.getValue()).templateId, ((RealmsWorldOptions)$$5.getValue()).templateImage, ((RealmsWorldOptions)$$5.getValue()).empty); continue;
/*     */       } 
/* 137 */       drawSlotFrame($$0, getFramePositionX(((Integer)$$5.getKey()).intValue()), row(1) + 5, $$1, $$2, (this.serverData.activeSlot == ((Integer)$$5.getKey()).intValue() && !isMinigame()), ((RealmsWorldOptions)$$5.getValue()).getSlotName(((Integer)$$5.getKey()).intValue()), ((Integer)$$5.getKey()).intValue(), -1L, (String)null, ((RealmsWorldOptions)$$5.getValue()).empty);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private int getFramePositionX(int $$0) {
/* 143 */     return this.leftX + ($$0 - 1) * 110;
/*     */   }
/*     */   
/*     */   private void fetchServerData(long $$0) {
/* 147 */     (new Thread(() -> {
/*     */           RealmsClient $$1 = RealmsClient.create();
/*     */           
/*     */           try {
/*     */             this.serverData = $$1.getOwnWorld($$0);
/*     */             addButtons();
/* 153 */           } catch (RealmsServiceException $$2) {
/*     */             LOGGER.error("Couldn't get own world", (Throwable)$$2);
/*     */             this.minecraft.setScreen((Screen)new RealmsGenericErrorScreen($$2, this.lastScreen));
/*     */           } 
/* 157 */         })).start();
/*     */   }
/*     */   
/*     */   public void doSwitchOrReset() {
/* 161 */     (new Thread(() -> {
/*     */           RealmsClient $$0 = RealmsClient.create();
/*     */           
/*     */           if (this.serverData.state == RealmsServer.State.CLOSED) {
/*     */             this.minecraft.execute(());
/*     */           } else {
/*     */             try {
/*     */               RealmsServer $$1 = $$0.getOwnWorld(this.serverId);
/*     */               this.minecraft.execute(());
/* 170 */             } catch (RealmsServiceException $$2) {
/*     */               LOGGER.error("Couldn't get own world", (Throwable)$$2);
/*     */               this.minecraft.execute(());
/*     */             } 
/*     */           } 
/* 175 */         })).start();
/*     */   }
/*     */   
/*     */   private void downloadWorld(int $$0) {
/* 179 */     RealmsClient $$1 = RealmsClient.create();
/*     */     
/*     */     try {
/* 182 */       WorldDownload $$2 = $$1.requestDownloadInfo(this.serverData.id, $$0);
/* 183 */       RealmsDownloadLatestWorldScreen $$3 = new RealmsDownloadLatestWorldScreen((Screen)this, $$2, this.serverData.getWorldName($$0), $$1 -> {
/*     */             if ($$1) {
/*     */               this.slotsThatHasBeenDownloaded.add(Integer.valueOf($$0));
/*     */               
/*     */               clearWidgets();
/*     */               addButtons();
/*     */             } else {
/*     */               this.minecraft.setScreen((Screen)this);
/*     */             } 
/*     */           });
/* 193 */       this.minecraft.setScreen((Screen)$$3);
/* 194 */     } catch (RealmsServiceException $$4) {
/* 195 */       LOGGER.error("Couldn't download world data", (Throwable)$$4);
/* 196 */       this.minecraft.setScreen((Screen)new RealmsGenericErrorScreen($$4, (Screen)this));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onClose() {
/* 202 */     this.minecraft.setScreen(this.lastScreen);
/*     */   }
/*     */   
/*     */   private boolean isMinigame() {
/* 206 */     return (this.serverData != null && this.serverData.worldType == RealmsServer.WorldType.MINIGAME);
/*     */   }
/*     */ 
/*     */   
/*     */   private void drawSlotFrame(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, boolean $$5, String $$6, int $$7, long $$8, @Nullable String $$9, boolean $$10) {
/*     */     ResourceLocation $$16;
/* 212 */     if ($$10) {
/* 213 */       ResourceLocation $$11 = RealmsWorldSlotButton.EMPTY_SLOT_LOCATION;
/* 214 */     } else if ($$9 != null && $$8 != -1L) {
/* 215 */       ResourceLocation $$12 = RealmsTextureManager.worldTemplate(String.valueOf($$8), $$9);
/* 216 */     } else if ($$7 == 1) {
/* 217 */       ResourceLocation $$13 = RealmsWorldSlotButton.DEFAULT_WORLD_SLOT_1;
/* 218 */     } else if ($$7 == 2) {
/* 219 */       ResourceLocation $$14 = RealmsWorldSlotButton.DEFAULT_WORLD_SLOT_2;
/* 220 */     } else if ($$7 == 3) {
/* 221 */       ResourceLocation $$15 = RealmsWorldSlotButton.DEFAULT_WORLD_SLOT_3;
/*     */     } else {
/* 223 */       $$16 = RealmsTextureManager.worldTemplate(String.valueOf(this.serverData.minigameId), this.serverData.minigameImage);
/*     */     } 
/*     */     
/* 226 */     if (!$$5) {
/* 227 */       $$0.setColor(0.56F, 0.56F, 0.56F, 1.0F);
/* 228 */     } else if ($$5) {
/* 229 */       float $$17 = 0.9F + 0.1F * Mth.cos(this.animTick * 0.2F);
/* 230 */       $$0.setColor($$17, $$17, $$17, 1.0F);
/*     */     } 
/*     */     
/* 233 */     $$0.blit($$16, $$1 + 3, $$2 + 3, 0.0F, 0.0F, 74, 74, 74, 74);
/*     */     
/* 235 */     if ($$5) {
/* 236 */       $$0.setColor(1.0F, 1.0F, 1.0F, 1.0F);
/*     */     } else {
/* 238 */       $$0.setColor(0.56F, 0.56F, 0.56F, 1.0F);
/*     */     } 
/*     */     
/* 241 */     $$0.blitSprite(SLOT_FRAME_SPRITE, $$1, $$2, 80, 80);
/*     */     
/* 243 */     $$0.drawCenteredString(this.font, $$6, $$1 + 40, $$2 + 66, -1);
/* 244 */     $$0.setColor(1.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\gui\screens\RealmsBrokenWorldScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */