/*     */ package com.mojang.realmsclient.gui.screens;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.realmsclient.RealmsMainScreen;
/*     */ import com.mojang.realmsclient.client.RealmsClient;
/*     */ import com.mojang.realmsclient.dto.RealmsServer;
/*     */ import com.mojang.realmsclient.dto.RealmsWorldOptions;
/*     */ import com.mojang.realmsclient.dto.WorldTemplate;
/*     */ import com.mojang.realmsclient.exception.RealmsServiceException;
/*     */ import com.mojang.realmsclient.gui.RealmsWorldSlotButton;
/*     */ import com.mojang.realmsclient.util.task.CloseServerTask;
/*     */ import com.mojang.realmsclient.util.task.LongRunningTask;
/*     */ import com.mojang.realmsclient.util.task.OpenServerTask;
/*     */ import com.mojang.realmsclient.util.task.SwitchMinigameTask;
/*     */ import com.mojang.realmsclient.util.task.SwitchSlotTask;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.FormattedText;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.realms.RealmsScreen;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class RealmsConfigureWorldScreen extends RealmsScreen {
/*  32 */   private static final ResourceLocation EXPIRED_SPRITE = new ResourceLocation("realm_status/expired");
/*  33 */   private static final ResourceLocation EXPIRES_SOON_SPRITE = new ResourceLocation("realm_status/expires_soon");
/*  34 */   private static final ResourceLocation OPEN_SPRITE = new ResourceLocation("realm_status/open");
/*  35 */   private static final ResourceLocation CLOSED_SPRITE = new ResourceLocation("realm_status/closed");
/*  36 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*  38 */   private static final Component WORLD_LIST_TITLE = (Component)Component.translatable("mco.configure.worlds.title");
/*  39 */   private static final Component TITLE = (Component)Component.translatable("mco.configure.world.title");
/*  40 */   private static final Component SERVER_EXPIRED_TOOLTIP = (Component)Component.translatable("mco.selectServer.expired");
/*  41 */   private static final Component SERVER_EXPIRING_SOON_TOOLTIP = (Component)Component.translatable("mco.selectServer.expires.soon");
/*  42 */   private static final Component SERVER_EXPIRING_IN_DAY_TOOLTIP = (Component)Component.translatable("mco.selectServer.expires.day");
/*  43 */   private static final Component SERVER_OPEN_TOOLTIP = (Component)Component.translatable("mco.selectServer.open");
/*  44 */   private static final Component SERVER_CLOSED_TOOLTIP = (Component)Component.translatable("mco.selectServer.closed");
/*     */   
/*     */   private static final int DEFAULT_BUTTON_WIDTH = 80;
/*     */   
/*     */   private static final int DEFAULT_BUTTON_OFFSET = 5;
/*     */   
/*     */   @Nullable
/*     */   private Component toolTip;
/*     */   
/*     */   private final RealmsMainScreen lastScreen;
/*     */   
/*     */   @Nullable
/*     */   private RealmsServer serverData;
/*     */   
/*     */   private final long serverId;
/*     */   
/*     */   private int leftX;
/*     */   
/*     */   private int rightX;
/*     */   
/*     */   private Button playersButton;
/*     */   private Button settingsButton;
/*     */   private Button subscriptionButton;
/*     */   private Button optionsButton;
/*     */   private Button backupButton;
/*     */   private Button resetWorldButton;
/*     */   private Button switchMinigameButton;
/*     */   private boolean stateChanged;
/*  72 */   private final List<RealmsWorldSlotButton> slotButtonList = Lists.newArrayList();
/*     */   
/*     */   public RealmsConfigureWorldScreen(RealmsMainScreen $$0, long $$1) {
/*  75 */     super(TITLE);
/*  76 */     this.lastScreen = $$0;
/*  77 */     this.serverId = $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void init() {
/*  82 */     if (this.serverData == null) {
/*  83 */       fetchServerData(this.serverId);
/*     */     }
/*     */     
/*  86 */     this.leftX = this.width / 2 - 187;
/*  87 */     this.rightX = this.width / 2 + 190;
/*     */     
/*  89 */     this.playersButton = (Button)addRenderableWidget((GuiEventListener)Button.builder((Component)Component.translatable("mco.configure.world.buttons.players"), $$0 -> this.minecraft.setScreen((Screen)new RealmsPlayerScreen(this, this.serverData)))
/*  90 */         .bounds(centerButton(0, 3), row(0), 100, 20).build());
/*  91 */     this.settingsButton = (Button)addRenderableWidget((GuiEventListener)Button.builder((Component)Component.translatable("mco.configure.world.buttons.settings"), $$0 -> this.minecraft.setScreen((Screen)new RealmsSettingsScreen(this, this.serverData.clone())))
/*  92 */         .bounds(centerButton(1, 3), row(0), 100, 20).build());
/*  93 */     this.subscriptionButton = (Button)addRenderableWidget((GuiEventListener)Button.builder((Component)Component.translatable("mco.configure.world.buttons.subscription"), $$0 -> this.minecraft.setScreen((Screen)new RealmsSubscriptionInfoScreen((Screen)this, this.serverData.clone(), (Screen)this.lastScreen)))
/*  94 */         .bounds(centerButton(2, 3), row(0), 100, 20).build());
/*     */     
/*  96 */     this.slotButtonList.clear();
/*  97 */     for (int $$0 = 1; $$0 < 5; $$0++) {
/*  98 */       this.slotButtonList.add(addSlotButton($$0));
/*     */     }
/*     */     
/* 101 */     this.switchMinigameButton = (Button)addRenderableWidget((GuiEventListener)Button.builder((Component)Component.translatable("mco.configure.world.buttons.switchminigame"), $$0 -> this.minecraft.setScreen((Screen)new RealmsSelectWorldTemplateScreen((Component)Component.translatable("mco.template.title.minigame"), this::templateSelectionCallback, RealmsServer.WorldType.MINIGAME)))
/* 102 */         .bounds(leftButton(0), row(13) - 5, 100, 20).build());
/*     */     
/* 104 */     this.optionsButton = (Button)addRenderableWidget((GuiEventListener)Button.builder((Component)Component.translatable("mco.configure.world.buttons.options"), $$0 -> this.minecraft.setScreen((Screen)new RealmsSlotOptionsScreen(this, ((RealmsWorldOptions)this.serverData.slots.get(Integer.valueOf(this.serverData.activeSlot))).clone(), this.serverData.worldType, this.serverData.activeSlot)))
/* 105 */         .bounds(leftButton(0), row(13) - 5, 90, 20).build());
/* 106 */     this.backupButton = (Button)addRenderableWidget((GuiEventListener)Button.builder((Component)Component.translatable("mco.configure.world.backup"), $$0 -> this.minecraft.setScreen((Screen)new RealmsBackupScreen(this, this.serverData.clone(), this.serverData.activeSlot)))
/* 107 */         .bounds(leftButton(1), row(13) - 5, 90, 20).build());
/* 108 */     this.resetWorldButton = (Button)addRenderableWidget((GuiEventListener)Button.builder((Component)Component.translatable("mco.configure.world.buttons.resetworld"), $$0 -> this.minecraft.setScreen((Screen)RealmsResetWorldScreen.forResetSlot((Screen)this, this.serverData.clone(), ())))
/*     */         
/* 110 */         .bounds(leftButton(2), row(13) - 5, 90, 20).build());
/* 111 */     addRenderableWidget((GuiEventListener)Button.builder(CommonComponents.GUI_BACK, $$0 -> onClose())
/* 112 */         .bounds(this.rightX - 80 + 8, row(13) - 5, 70, 20).build());
/*     */     
/* 114 */     this.backupButton.active = true;
/*     */     
/* 116 */     if (this.serverData == null) {
/* 117 */       hideMinigameButtons();
/* 118 */       hideRegularButtons();
/*     */       
/* 120 */       this.playersButton.active = false;
/* 121 */       this.settingsButton.active = false;
/* 122 */       this.subscriptionButton.active = false;
/*     */     } else {
/* 124 */       disableButtons();
/*     */       
/* 126 */       if (isMinigame()) {
/* 127 */         hideRegularButtons();
/*     */       } else {
/* 129 */         hideMinigameButtons();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private RealmsWorldSlotButton addSlotButton(int $$0) {
/* 135 */     int $$1 = frame($$0);
/* 136 */     int $$2 = row(5) + 5;
/* 137 */     RealmsWorldSlotButton $$3 = new RealmsWorldSlotButton($$1, $$2, 80, 80, $$0, $$1 -> {
/*     */           RealmsWorldSlotButton.State $$2 = ((RealmsWorldSlotButton)$$1).getState();
/*     */           if ($$2 != null) {
/*     */             switch ($$2.action) {
/*     */               case NOTHING:
/*     */                 return;
/*     */               
/*     */               case JOIN:
/*     */                 joinRealm(this.serverData);
/*     */               
/*     */               case SWITCH_SLOT:
/*     */                 if ($$2.minigame) {
/*     */                   switchToMinigame();
/*     */                 } else if ($$2.empty) {
/*     */                   switchToEmptySlot($$0, this.serverData);
/*     */                 } else {
/*     */                   switchToFullSlot($$0, this.serverData);
/*     */                 } 
/*     */             } 
/*     */             throw new IllegalStateException("Unknown action " + $$2.action);
/*     */           } 
/*     */         });
/* 159 */     if (this.serverData != null) {
/* 160 */       $$3.setServerData(this.serverData);
/*     */     }
/* 162 */     return (RealmsWorldSlotButton)addRenderableWidget((GuiEventListener)$$3);
/*     */   }
/*     */   
/*     */   private int leftButton(int $$0) {
/* 166 */     return this.leftX + $$0 * 95;
/*     */   }
/*     */   
/*     */   private int centerButton(int $$0, int $$1) {
/* 170 */     return this.width / 2 - ($$1 * 105 - 5) / 2 + $$0 * 105;
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 175 */     super.render($$0, $$1, $$2, $$3);
/*     */     
/* 177 */     this.toolTip = null;
/* 178 */     $$0.drawCenteredString(this.font, WORLD_LIST_TITLE, this.width / 2, row(4), -1);
/*     */     
/* 180 */     if (this.serverData == null) {
/* 181 */       $$0.drawCenteredString(this.font, this.title, this.width / 2, 17, -1);
/*     */       
/*     */       return;
/*     */     } 
/* 185 */     String $$4 = this.serverData.getName();
/* 186 */     int $$5 = this.font.width($$4);
/* 187 */     int $$6 = (this.serverData.state == RealmsServer.State.CLOSED) ? -6250336 : 8388479;
/* 188 */     int $$7 = this.font.width((FormattedText)this.title);
/*     */     
/* 190 */     $$0.drawCenteredString(this.font, this.title, this.width / 2, 12, -1);
/*     */     
/* 192 */     $$0.drawCenteredString(this.font, $$4, this.width / 2, 24, $$6);
/*     */     
/* 194 */     int $$8 = Math.min(centerButton(2, 3) + 80 - 11, this.width / 2 + $$5 / 2 + $$7 / 2 + 10);
/* 195 */     drawServerStatus($$0, $$8, 7, $$1, $$2);
/*     */     
/* 197 */     if (isMinigame()) {
/* 198 */       $$0.drawString(this.font, (Component)Component.translatable("mco.configure.world.minigame", new Object[] { this.serverData.getMinigameName() }), this.leftX + 80 + 20 + 10, row(13), -1, false);
/*     */     }
/*     */   }
/*     */   
/*     */   private int frame(int $$0) {
/* 203 */     return this.leftX + ($$0 - 1) * 98;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onClose() {
/* 208 */     this.minecraft.setScreen((Screen)this.lastScreen);
/* 209 */     if (this.stateChanged) {
/* 210 */       this.lastScreen.resetScreen();
/*     */     }
/*     */   }
/*     */   
/*     */   private void fetchServerData(long $$0) {
/* 215 */     (new Thread(() -> {
/*     */           RealmsClient $$1 = RealmsClient.create();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           try {
/*     */             RealmsServer $$2 = $$1.getOwnWorld($$0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             this.minecraft.execute(());
/* 235 */           } catch (RealmsServiceException $$3) {
/*     */             LOGGER.error("Couldn't get own world", (Throwable)$$3);
/*     */             this.minecraft.execute(());
/*     */           } 
/* 239 */         })).start();
/*     */   }
/*     */   
/*     */   private void disableButtons() {
/* 243 */     this.playersButton.active = !this.serverData.expired;
/* 244 */     this.settingsButton.active = !this.serverData.expired;
/* 245 */     this.subscriptionButton.active = true;
/*     */     
/* 247 */     this.switchMinigameButton.active = !this.serverData.expired;
/*     */     
/* 249 */     this.optionsButton.active = !this.serverData.expired;
/* 250 */     this.resetWorldButton.active = !this.serverData.expired;
/*     */   }
/*     */   
/*     */   private void joinRealm(RealmsServer $$0) {
/* 254 */     if (this.serverData.state == RealmsServer.State.OPEN) {
/* 255 */       RealmsMainScreen.play($$0, (Screen)new RealmsConfigureWorldScreen(this.lastScreen, this.serverId));
/*     */     } else {
/* 257 */       openTheWorld(true, (Screen)new RealmsConfigureWorldScreen(this.lastScreen, this.serverId));
/*     */     } 
/*     */   }
/*     */   
/*     */   private void switchToMinigame() {
/* 262 */     RealmsSelectWorldTemplateScreen $$0 = new RealmsSelectWorldTemplateScreen((Component)Component.translatable("mco.template.title.minigame"), this::templateSelectionCallback, RealmsServer.WorldType.MINIGAME);
/* 263 */     $$0.setWarning(new Component[] { (Component)Component.translatable("mco.minigame.world.info.line1"), (Component)Component.translatable("mco.minigame.world.info.line2") });
/* 264 */     this.minecraft.setScreen((Screen)$$0);
/*     */   }
/*     */   
/*     */   private void switchToFullSlot(int $$0, RealmsServer $$1) {
/* 268 */     MutableComponent mutableComponent1 = Component.translatable("mco.configure.world.slot.switch.question.line1");
/* 269 */     MutableComponent mutableComponent2 = Component.translatable("mco.configure.world.slot.switch.question.line2");
/* 270 */     this.minecraft.setScreen((Screen)new RealmsLongConfirmationScreen($$2 -> { if ($$2) { stateChanged(); this.minecraft.setScreen((Screen)new RealmsLongRunningMcoTaskScreen((Screen)this.lastScreen, new LongRunningTask[] { (LongRunningTask)new SwitchSlotTask($$0.id, $$1, ()) })); } else { this.minecraft.setScreen((Screen)this); }  }RealmsLongConfirmationScreen.Type.INFO, (Component)mutableComponent1, (Component)mutableComponent2, true));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void switchToEmptySlot(int $$0, RealmsServer $$1) {
/* 283 */     MutableComponent mutableComponent1 = Component.translatable("mco.configure.world.slot.switch.question.line1");
/* 284 */     MutableComponent mutableComponent2 = Component.translatable("mco.configure.world.slot.switch.question.line2");
/* 285 */     this.minecraft.setScreen((Screen)new RealmsLongConfirmationScreen($$2 -> { if ($$2) { stateChanged(); RealmsResetWorldScreen $$3 = RealmsResetWorldScreen.forEmptySlot((Screen)this, $$0, $$1, ()); this.minecraft.setScreen((Screen)$$3); } else { this.minecraft.setScreen((Screen)this); }  }RealmsLongConfirmationScreen.Type.INFO, (Component)mutableComponent1, (Component)mutableComponent2, true));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void drawServerStatus(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4) {
/* 297 */     if (this.serverData.expired) {
/* 298 */       drawRealmStatus($$0, $$1, $$2, $$3, $$4, EXPIRED_SPRITE, () -> SERVER_EXPIRED_TOOLTIP);
/* 299 */     } else if (this.serverData.state == RealmsServer.State.CLOSED) {
/* 300 */       drawRealmStatus($$0, $$1, $$2, $$3, $$4, CLOSED_SPRITE, () -> SERVER_CLOSED_TOOLTIP);
/* 301 */     } else if (this.serverData.state == RealmsServer.State.OPEN) {
/* 302 */       if (this.serverData.daysLeft < 7) {
/* 303 */         drawRealmStatus($$0, $$1, $$2, $$3, $$4, EXPIRES_SOON_SPRITE, () -> (this.serverData.daysLeft <= 0) ? SERVER_EXPIRING_SOON_TOOLTIP : ((this.serverData.daysLeft == 1) ? SERVER_EXPIRING_IN_DAY_TOOLTIP : Component.translatable("mco.selectServer.expires.days", new Object[] { Integer.valueOf(this.serverData.daysLeft) })));
/*     */ 
/*     */ 
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 313 */         drawRealmStatus($$0, $$1, $$2, $$3, $$4, OPEN_SPRITE, () -> SERVER_OPEN_TOOLTIP);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void drawRealmStatus(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, ResourceLocation $$5, Supplier<Component> $$6) {
/* 319 */     $$0.blitSprite($$5, $$1, $$2, 10, 28);
/* 320 */     if ($$3 >= $$1 && $$3 <= $$1 + 9 && $$4 >= $$2 && $$4 <= $$2 + 27) {
/* 321 */       setTooltipForNextRenderPass($$6.get());
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean isMinigame() {
/* 326 */     return (this.serverData != null && this.serverData.worldType == RealmsServer.WorldType.MINIGAME);
/*     */   }
/*     */   
/*     */   private void hideRegularButtons() {
/* 330 */     hide(this.optionsButton);
/* 331 */     hide(this.backupButton);
/* 332 */     hide(this.resetWorldButton);
/*     */   }
/*     */   
/*     */   private void hide(Button $$0) {
/* 336 */     $$0.visible = false;
/*     */   }
/*     */   
/*     */   private void show(Button $$0) {
/* 340 */     $$0.visible = true;
/*     */   }
/*     */   
/*     */   private void hideMinigameButtons() {
/* 344 */     hide(this.switchMinigameButton);
/*     */   }
/*     */   
/*     */   public void saveSlotSettings(RealmsWorldOptions $$0) {
/* 348 */     RealmsWorldOptions $$1 = (RealmsWorldOptions)this.serverData.slots.get(Integer.valueOf(this.serverData.activeSlot));
/* 349 */     $$0.templateId = $$1.templateId;
/* 350 */     $$0.templateImage = $$1.templateImage;
/*     */     
/* 352 */     RealmsClient $$2 = RealmsClient.create();
/*     */     
/*     */     try {
/* 355 */       $$2.updateSlot(this.serverData.id, this.serverData.activeSlot, $$0);
/* 356 */       this.serverData.slots.put(Integer.valueOf(this.serverData.activeSlot), $$0);
/* 357 */     } catch (RealmsServiceException $$3) {
/* 358 */       LOGGER.error("Couldn't save slot settings", (Throwable)$$3);
/* 359 */       this.minecraft.setScreen((Screen)new RealmsGenericErrorScreen($$3, (Screen)this));
/*     */       
/*     */       return;
/*     */     } 
/* 363 */     this.minecraft.setScreen((Screen)this);
/*     */   }
/*     */   
/*     */   public void saveSettings(String $$0, String $$1) {
/* 367 */     String $$2 = Util.isBlank($$1) ? null : $$1;
/*     */     
/* 369 */     RealmsClient $$3 = RealmsClient.create();
/*     */     
/*     */     try {
/* 372 */       $$3.update(this.serverData.id, $$0, $$2);
/* 373 */       this.serverData.setName($$0);
/* 374 */       this.serverData.setDescription($$2);
/* 375 */       stateChanged();
/* 376 */     } catch (RealmsServiceException $$4) {
/* 377 */       LOGGER.error("Couldn't save settings", (Throwable)$$4);
/* 378 */       this.minecraft.setScreen((Screen)new RealmsGenericErrorScreen($$4, (Screen)this));
/*     */       
/*     */       return;
/*     */     } 
/* 382 */     this.minecraft.setScreen((Screen)this);
/*     */   }
/*     */   
/*     */   public void openTheWorld(boolean $$0, Screen $$1) {
/* 386 */     this.minecraft.setScreen((Screen)new RealmsLongRunningMcoTaskScreen($$1, new LongRunningTask[] { (LongRunningTask)new OpenServerTask(this.serverData, (Screen)this, $$0, this.minecraft) }));
/*     */   }
/*     */   
/*     */   public void closeTheWorld(Screen $$0) {
/* 390 */     this.minecraft.setScreen((Screen)new RealmsLongRunningMcoTaskScreen($$0, new LongRunningTask[] { (LongRunningTask)new CloseServerTask(this.serverData, this) }));
/*     */   }
/*     */   
/*     */   public void stateChanged() {
/* 394 */     this.stateChanged = true;
/*     */   }
/*     */   
/*     */   private void templateSelectionCallback(@Nullable WorldTemplate $$0) {
/* 398 */     if ($$0 != null && WorldTemplate.WorldTemplateType.MINIGAME == $$0.type) {
/* 399 */       stateChanged();
/* 400 */       this.minecraft.setScreen((Screen)new RealmsLongRunningMcoTaskScreen((Screen)this.lastScreen, new LongRunningTask[] { (LongRunningTask)new SwitchMinigameTask(this.serverData.id, $$0, getNewScreen()) }));
/*     */     } else {
/* 402 */       this.minecraft.setScreen((Screen)this);
/*     */     } 
/*     */   }
/*     */   
/*     */   public RealmsConfigureWorldScreen getNewScreen() {
/* 407 */     RealmsConfigureWorldScreen $$0 = new RealmsConfigureWorldScreen(this.lastScreen, this.serverId);
/* 408 */     $$0.stateChanged = this.stateChanged;
/* 409 */     return $$0;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\gui\screens\RealmsConfigureWorldScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */