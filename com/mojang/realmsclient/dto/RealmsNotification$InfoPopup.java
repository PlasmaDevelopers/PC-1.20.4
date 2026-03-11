/*     */ package com.mojang.realmsclient.dto;
/*     */ 
/*     */ import com.google.gson.JsonObject;
/*     */ import com.mojang.realmsclient.util.JsonUtils;
/*     */ import java.util.UUID;
/*     */ import java.util.function.Consumer;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.components.PopupScreen;
/*     */ import net.minecraft.client.gui.screens.ConfirmLinkScreen;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.ResourceLocation;
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
/*     */ public class InfoPopup
/*     */   extends RealmsNotification
/*     */ {
/*     */   private static final String TITLE = "title";
/*     */   private static final String MESSAGE = "message";
/*     */   private static final String IMAGE = "image";
/*     */   private static final String URL_BUTTON = "urlButton";
/*     */   private final RealmsText title;
/*     */   private final RealmsText message;
/*     */   private final ResourceLocation image;
/*     */   @Nullable
/*     */   private final RealmsNotification.UrlButton urlButton;
/*     */   
/*     */   private InfoPopup(RealmsNotification $$0, RealmsText $$1, RealmsText $$2, ResourceLocation $$3, @Nullable RealmsNotification.UrlButton $$4) {
/* 139 */     super($$0.uuid, $$0.dismissable, $$0.seen, $$0.type);
/* 140 */     this.title = $$1;
/* 141 */     this.message = $$2;
/* 142 */     this.image = $$3;
/* 143 */     this.urlButton = $$4;
/*     */   }
/*     */   
/*     */   public static InfoPopup parse(RealmsNotification $$0, JsonObject $$1) {
/* 147 */     RealmsText $$2 = (RealmsText)JsonUtils.getRequired("title", $$1, RealmsText::parse);
/* 148 */     RealmsText $$3 = (RealmsText)JsonUtils.getRequired("message", $$1, RealmsText::parse);
/* 149 */     ResourceLocation $$4 = new ResourceLocation(JsonUtils.getRequiredString("image", $$1));
/* 150 */     RealmsNotification.UrlButton $$5 = (RealmsNotification.UrlButton)JsonUtils.getOptional("urlButton", $$1, RealmsNotification.UrlButton::parse);
/* 151 */     return new InfoPopup($$0, $$2, $$3, $$4, $$5);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public PopupScreen buildScreen(Screen $$0, Consumer<UUID> $$1) {
/* 156 */     Component $$2 = this.title.createComponent();
/* 157 */     if ($$2 == null) {
/* 158 */       RealmsNotification.LOGGER.warn("Realms info popup had title with no available translation: {}", this.title);
/* 159 */       return null;
/*     */     } 
/*     */ 
/*     */     
/* 163 */     PopupScreen.Builder $$3 = (new PopupScreen.Builder($$0, $$2)).setImage(this.image).setMessage(this.message.createComponent(CommonComponents.EMPTY));
/* 164 */     if (this.urlButton != null) {
/* 165 */       $$3.addButton(this.urlButton.urlText.createComponent(RealmsNotification.BUTTON_TEXT_FALLBACK), $$2 -> {
/*     */             Minecraft $$3 = Minecraft.getInstance();
/*     */ 
/*     */ 
/*     */             
/*     */             $$3.setScreen((Screen)new ConfirmLinkScreen((), this.urlButton.url, true));
/*     */ 
/*     */             
/*     */             $$1.accept(uuid());
/*     */           });
/*     */     }
/*     */ 
/*     */     
/* 178 */     $$3.addButton(CommonComponents.GUI_OK, $$1 -> {
/*     */           $$1.onClose();
/*     */           $$0.accept(uuid());
/*     */         });
/* 182 */     $$3.onClose(() -> $$0.accept(uuid()));
/* 183 */     return $$3.build();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\dto\RealmsNotification$InfoPopup.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */