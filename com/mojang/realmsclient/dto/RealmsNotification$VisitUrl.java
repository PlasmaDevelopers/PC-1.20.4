/*     */ package com.mojang.realmsclient.dto;
/*     */ 
/*     */ import com.google.gson.JsonObject;
/*     */ import com.mojang.realmsclient.util.JsonUtils;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.gui.screens.ConfirmLinkScreen;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.network.chat.Component;
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
/*     */ 
/*     */ 
/*     */ public class VisitUrl
/*     */   extends RealmsNotification
/*     */ {
/*     */   private static final String URL = "url";
/*     */   private static final String BUTTON_TEXT = "buttonText";
/*     */   private static final String MESSAGE = "message";
/*     */   private final String url;
/*     */   private final RealmsText buttonText;
/*     */   private final RealmsText message;
/*     */   
/*     */   private VisitUrl(RealmsNotification $$0, String $$1, RealmsText $$2, RealmsText $$3) {
/* 102 */     super($$0.uuid, $$0.dismissable, $$0.seen, $$0.type);
/* 103 */     this.url = $$1;
/* 104 */     this.buttonText = $$2;
/* 105 */     this.message = $$3;
/*     */   }
/*     */   
/*     */   public static VisitUrl parse(RealmsNotification $$0, JsonObject $$1) {
/* 109 */     String $$2 = JsonUtils.getRequiredString("url", $$1);
/* 110 */     RealmsText $$3 = (RealmsText)JsonUtils.getRequired("buttonText", $$1, RealmsText::parse);
/* 111 */     RealmsText $$4 = (RealmsText)JsonUtils.getRequired("message", $$1, RealmsText::parse);
/* 112 */     return new VisitUrl($$0, $$2, $$3, $$4);
/*     */   }
/*     */   
/*     */   public Component getMessage() {
/* 116 */     return this.message.createComponent((Component)Component.translatable("mco.notification.visitUrl.message.default"));
/*     */   }
/*     */   
/*     */   public Button buildOpenLinkButton(Screen $$0) {
/* 120 */     Component $$1 = this.buttonText.createComponent(RealmsNotification.BUTTON_TEXT_FALLBACK);
/* 121 */     return Button.builder($$1, ConfirmLinkScreen.confirmLink($$0, this.url)).build();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\dto\RealmsNotification$VisitUrl.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */