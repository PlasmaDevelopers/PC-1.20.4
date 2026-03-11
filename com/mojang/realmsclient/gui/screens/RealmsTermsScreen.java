/*    */ package com.mojang.realmsclient.gui.screens;
/*    */ 
/*    */ import com.mojang.logging.LogUtils;
/*    */ import com.mojang.realmsclient.client.RealmsClient;
/*    */ import com.mojang.realmsclient.dto.RealmsServer;
/*    */ import com.mojang.realmsclient.exception.RealmsServiceException;
/*    */ import com.mojang.realmsclient.util.task.GetServerDetailsTask;
/*    */ import com.mojang.realmsclient.util.task.LongRunningTask;
/*    */ import java.util.Objects;
/*    */ import net.minecraft.Util;
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ import net.minecraft.client.gui.components.Button;
/*    */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*    */ import net.minecraft.client.gui.screens.Screen;
/*    */ import net.minecraft.network.chat.CommonComponents;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.chat.FormattedText;
/*    */ import net.minecraft.network.chat.Style;
/*    */ import net.minecraft.realms.RealmsScreen;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class RealmsTermsScreen
/*    */   extends RealmsScreen
/*    */ {
/* 25 */   private static final Logger LOGGER = LogUtils.getLogger();
/* 26 */   private static final Component TITLE = (Component)Component.translatable("mco.terms.title");
/* 27 */   private static final Component TERMS_STATIC_TEXT = (Component)Component.translatable("mco.terms.sentence.1");
/* 28 */   private static final Component TERMS_LINK_TEXT = (Component)CommonComponents.space().append((Component)Component.translatable("mco.terms.sentence.2").withStyle(Style.EMPTY.withUnderlined(Boolean.valueOf(true))));
/*    */   
/*    */   private final Screen lastScreen;
/*    */   
/*    */   private final RealmsServer realmsServer;
/*    */   private boolean onLink;
/*    */   
/*    */   public RealmsTermsScreen(Screen $$0, RealmsServer $$1) {
/* 36 */     super(TITLE);
/* 37 */     this.lastScreen = $$0;
/* 38 */     this.realmsServer = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public void init() {
/* 43 */     int $$0 = this.width / 4 - 2;
/*    */     
/* 45 */     addRenderableWidget((GuiEventListener)Button.builder((Component)Component.translatable("mco.terms.buttons.agree"), $$0 -> agreedToTos()).bounds(this.width / 4, row(12), $$0, 20).build());
/* 46 */     addRenderableWidget((GuiEventListener)Button.builder((Component)Component.translatable("mco.terms.buttons.disagree"), $$0 -> this.minecraft.setScreen(this.lastScreen)).bounds(this.width / 2 + 4, row(12), $$0, 20).build());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean keyPressed(int $$0, int $$1, int $$2) {
/* 51 */     if ($$0 == 256) {
/* 52 */       this.minecraft.setScreen(this.lastScreen);
/* 53 */       return true;
/*    */     } 
/* 55 */     return super.keyPressed($$0, $$1, $$2);
/*    */   }
/*    */   
/*    */   private void agreedToTos() {
/* 59 */     RealmsClient $$0 = RealmsClient.create();
/*    */     try {
/* 61 */       $$0.agreeToTos();
/* 62 */       this.minecraft.setScreen((Screen)new RealmsLongRunningMcoTaskScreen(this.lastScreen, new LongRunningTask[] { (LongRunningTask)new GetServerDetailsTask(this.lastScreen, this.realmsServer) }));
/* 63 */     } catch (RealmsServiceException $$1) {
/* 64 */       LOGGER.error("Couldn't agree to TOS", (Throwable)$$1);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean mouseClicked(double $$0, double $$1, int $$2) {
/* 70 */     if (this.onLink) {
/* 71 */       this.minecraft.keyboardHandler.setClipboard("https://aka.ms/MinecraftRealmsTerms");
/* 72 */       Util.getPlatform().openUri("https://aka.ms/MinecraftRealmsTerms");
/* 73 */       return true;
/*    */     } 
/*    */     
/* 76 */     return super.mouseClicked($$0, $$1, $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   public Component getNarrationMessage() {
/* 81 */     return (Component)CommonComponents.joinForNarration(new Component[] { super.getNarrationMessage(), TERMS_STATIC_TEXT }).append(CommonComponents.SPACE).append(TERMS_LINK_TEXT);
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 86 */     super.render($$0, $$1, $$2, $$3);
/*    */     
/* 88 */     $$0.drawCenteredString(this.font, this.title, this.width / 2, 17, -1);
/* 89 */     $$0.drawString(this.font, TERMS_STATIC_TEXT, this.width / 2 - 120, row(5), -1, false);
/* 90 */     int $$4 = this.font.width((FormattedText)TERMS_STATIC_TEXT);
/*    */     
/* 92 */     int $$5 = this.width / 2 - 121 + $$4;
/* 93 */     int $$6 = row(5);
/* 94 */     int $$7 = $$5 + this.font.width((FormattedText)TERMS_LINK_TEXT) + 1;
/* 95 */     Objects.requireNonNull(this.font); int $$8 = $$6 + 1 + 9;
/*    */     
/* 97 */     this.onLink = ($$5 <= $$1 && $$1 <= $$7 && $$6 <= $$2 && $$2 <= $$8);
/* 98 */     $$0.drawString(this.font, TERMS_LINK_TEXT, this.width / 2 - 120 + $$4, row(5), this.onLink ? 7107012 : 3368635, false);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\gui\screens\RealmsTermsScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */