/*     */ package com.mojang.realmsclient.gui.screens;
/*     */ 
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.realmsclient.client.RealmsClient;
/*     */ import com.mojang.realmsclient.dto.RealmsServer;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.client.GameNarrator;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.gui.components.EditBox;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.realms.RealmsScreen;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class RealmsInviteScreen
/*     */   extends RealmsScreen
/*     */ {
/*  23 */   private static final Logger LOGGER = LogUtils.getLogger();
/*  24 */   private static final Component NAME_LABEL = (Component)Component.translatable("mco.configure.world.invite.profile.name").withColor(-6250336);
/*  25 */   private static final Component INVITING_PLAYER_TEXT = (Component)Component.translatable("mco.configure.world.players.inviting").withColor(-6250336);
/*  26 */   private static final Component NO_SUCH_PLAYER_ERROR_TEXT = (Component)Component.translatable("mco.configure.world.players.error").withColor(-65536);
/*     */   
/*     */   private EditBox profileName;
/*     */   
/*     */   private Button inviteButton;
/*     */   
/*     */   private final RealmsServer serverData;
/*     */   private final RealmsConfigureWorldScreen configureScreen;
/*     */   private final Screen lastScreen;
/*     */   @Nullable
/*     */   private Component message;
/*     */   
/*     */   public RealmsInviteScreen(RealmsConfigureWorldScreen $$0, Screen $$1, RealmsServer $$2) {
/*  39 */     super(GameNarrator.NO_TITLE);
/*  40 */     this.configureScreen = $$0;
/*  41 */     this.lastScreen = $$1;
/*  42 */     this.serverData = $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void init() {
/*  47 */     this.profileName = new EditBox(this.minecraft.font, this.width / 2 - 100, row(2), 200, 20, null, (Component)Component.translatable("mco.configure.world.invite.profile.name"));
/*  48 */     addWidget((GuiEventListener)this.profileName);
/*  49 */     setInitialFocus((GuiEventListener)this.profileName);
/*     */     
/*  51 */     this.inviteButton = (Button)addRenderableWidget((GuiEventListener)Button.builder((Component)Component.translatable("mco.configure.world.buttons.invite"), $$0 -> onInvite()).bounds(this.width / 2 - 100, row(10), 200, 20).build());
/*  52 */     addRenderableWidget((GuiEventListener)Button.builder(CommonComponents.GUI_CANCEL, $$0 -> this.minecraft.setScreen(this.lastScreen)).bounds(this.width / 2 - 100, row(12), 200, 20).build());
/*     */   }
/*     */   
/*     */   private void onInvite() {
/*  56 */     if (Util.isBlank(this.profileName.getValue())) {
/*  57 */       showMessage(NO_SUCH_PLAYER_ERROR_TEXT);
/*     */       
/*     */       return;
/*     */     } 
/*  61 */     long $$0 = this.serverData.id;
/*  62 */     String $$1 = this.profileName.getValue().trim();
/*  63 */     this.inviteButton.active = false;
/*  64 */     this.profileName.setEditable(false);
/*  65 */     showMessage(INVITING_PLAYER_TEXT);
/*     */     
/*  67 */     CompletableFuture.supplyAsync(() -> {
/*     */           try {
/*     */             return RealmsClient.create().invite($$0, $$1);
/*  70 */           } catch (Exception $$2) {
/*     */             LOGGER.error("Couldn't invite user");
/*     */             return null;
/*     */           } 
/*  74 */         }Util.ioPool())
/*  75 */       .thenAcceptAsync($$0 -> {
/*     */           if ($$0 != null) {
/*     */             this.serverData.players = $$0.players;
/*     */             this.minecraft.setScreen((Screen)new RealmsPlayerScreen(this.configureScreen, this.serverData));
/*     */           } else {
/*     */             showMessage(NO_SUCH_PLAYER_ERROR_TEXT);
/*     */           } 
/*     */           this.profileName.setEditable(true);
/*     */           this.inviteButton.active = true;
/*     */         }this.screenExecutor);
/*     */   }
/*     */   
/*     */   private void showMessage(Component $$0) {
/*  88 */     this.message = $$0;
/*  89 */     this.minecraft.getNarrator().sayNow($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean keyPressed(int $$0, int $$1, int $$2) {
/*  94 */     if ($$0 == 256) {
/*  95 */       this.minecraft.setScreen(this.lastScreen);
/*  96 */       return true;
/*     */     } 
/*     */     
/*  99 */     return super.keyPressed($$0, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 104 */     super.render($$0, $$1, $$2, $$3);
/*     */     
/* 106 */     $$0.drawString(this.font, NAME_LABEL, this.width / 2 - 100, row(1), -1, false);
/*     */     
/* 108 */     if (this.message != null) {
/* 109 */       $$0.drawCenteredString(this.font, this.message, this.width / 2, row(5), -1);
/*     */     }
/*     */     
/* 112 */     this.profileName.render($$0, $$1, $$2, $$3);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\gui\screens\RealmsInviteScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */