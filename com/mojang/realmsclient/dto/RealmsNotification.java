/*     */ package com.mojang.realmsclient.dto;
/*     */ 
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParser;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.realmsclient.util.JsonUtils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import java.util.function.Consumer;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.gui.components.PopupScreen;
/*     */ import net.minecraft.client.gui.screens.ConfirmLinkScreen;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class RealmsNotification
/*     */ {
/*  27 */   static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private static final String NOTIFICATION_UUID = "notificationUuid";
/*     */   
/*     */   private static final String DISMISSABLE = "dismissable";
/*     */   private static final String SEEN = "seen";
/*     */   private static final String TYPE = "type";
/*     */   private static final String VISIT_URL = "visitUrl";
/*     */   private static final String INFO_POPUP = "infoPopup";
/*  36 */   static final Component BUTTON_TEXT_FALLBACK = (Component)Component.translatable("mco.notification.visitUrl.buttonText.default");
/*     */   
/*     */   final UUID uuid;
/*     */   final boolean dismissable;
/*     */   final boolean seen;
/*     */   final String type;
/*     */   
/*     */   RealmsNotification(UUID $$0, boolean $$1, boolean $$2, String $$3) {
/*  44 */     this.uuid = $$0;
/*  45 */     this.dismissable = $$1;
/*  46 */     this.seen = $$2;
/*  47 */     this.type = $$3;
/*     */   }
/*     */   
/*     */   public boolean seen() {
/*  51 */     return this.seen;
/*     */   }
/*     */   
/*     */   public boolean dismissable() {
/*  55 */     return this.dismissable;
/*     */   }
/*     */   
/*     */   public UUID uuid() {
/*  59 */     return this.uuid;
/*     */   }
/*     */   
/*     */   public static List<RealmsNotification> parseList(String $$0) {
/*  63 */     List<RealmsNotification> $$1 = new ArrayList<>();
/*     */     try {
/*  65 */       JsonArray $$2 = JsonParser.parseString($$0).getAsJsonObject().get("notifications").getAsJsonArray();
/*  66 */       for (JsonElement $$3 : $$2) {
/*  67 */         $$1.add(parse($$3.getAsJsonObject()));
/*     */       }
/*  69 */     } catch (Exception $$4) {
/*  70 */       LOGGER.error("Could not parse list of RealmsNotifications", $$4);
/*     */     } 
/*  72 */     return $$1;
/*     */   }
/*     */   
/*     */   private static RealmsNotification parse(JsonObject $$0) {
/*  76 */     UUID $$1 = JsonUtils.getUuidOr("notificationUuid", $$0, null);
/*  77 */     if ($$1 == null) {
/*  78 */       throw new IllegalStateException("Missing required property notificationUuid");
/*     */     }
/*  80 */     boolean $$2 = JsonUtils.getBooleanOr("dismissable", $$0, true);
/*  81 */     boolean $$3 = JsonUtils.getBooleanOr("seen", $$0, false);
/*  82 */     String $$4 = JsonUtils.getRequiredString("type", $$0);
/*     */     
/*  84 */     RealmsNotification $$5 = new RealmsNotification($$1, $$2, $$3, $$4);
/*  85 */     switch ($$4) { case "visitUrl": case "infoPopup":  }  return 
/*     */ 
/*     */       
/*  88 */       $$5;
/*     */   }
/*     */   
/*     */   public static class VisitUrl
/*     */     extends RealmsNotification
/*     */   {
/*     */     private static final String URL = "url";
/*     */     private static final String BUTTON_TEXT = "buttonText";
/*     */     private static final String MESSAGE = "message";
/*     */     private final String url;
/*     */     private final RealmsText buttonText;
/*     */     private final RealmsText message;
/*     */     
/*     */     private VisitUrl(RealmsNotification $$0, String $$1, RealmsText $$2, RealmsText $$3) {
/* 102 */       super($$0.uuid, $$0.dismissable, $$0.seen, $$0.type);
/* 103 */       this.url = $$1;
/* 104 */       this.buttonText = $$2;
/* 105 */       this.message = $$3;
/*     */     }
/*     */     
/*     */     public static VisitUrl parse(RealmsNotification $$0, JsonObject $$1) {
/* 109 */       String $$2 = JsonUtils.getRequiredString("url", $$1);
/* 110 */       RealmsText $$3 = (RealmsText)JsonUtils.getRequired("buttonText", $$1, RealmsText::parse);
/* 111 */       RealmsText $$4 = (RealmsText)JsonUtils.getRequired("message", $$1, RealmsText::parse);
/* 112 */       return new VisitUrl($$0, $$2, $$3, $$4);
/*     */     }
/*     */     
/*     */     public Component getMessage() {
/* 116 */       return this.message.createComponent((Component)Component.translatable("mco.notification.visitUrl.message.default"));
/*     */     }
/*     */     
/*     */     public Button buildOpenLinkButton(Screen $$0) {
/* 120 */       Component $$1 = this.buttonText.createComponent(RealmsNotification.BUTTON_TEXT_FALLBACK);
/* 121 */       return Button.builder($$1, ConfirmLinkScreen.confirmLink($$0, this.url)).build();
/*     */     }
/*     */   }
/*     */   
/*     */   public static class InfoPopup
/*     */     extends RealmsNotification
/*     */   {
/*     */     private static final String TITLE = "title";
/*     */     private static final String MESSAGE = "message";
/*     */     private static final String IMAGE = "image";
/*     */     private static final String URL_BUTTON = "urlButton";
/*     */     private final RealmsText title;
/*     */     private final RealmsText message;
/*     */     private final ResourceLocation image;
/*     */     @Nullable
/*     */     private final RealmsNotification.UrlButton urlButton;
/*     */     
/*     */     private InfoPopup(RealmsNotification $$0, RealmsText $$1, RealmsText $$2, ResourceLocation $$3, @Nullable RealmsNotification.UrlButton $$4) {
/* 139 */       super($$0.uuid, $$0.dismissable, $$0.seen, $$0.type);
/* 140 */       this.title = $$1;
/* 141 */       this.message = $$2;
/* 142 */       this.image = $$3;
/* 143 */       this.urlButton = $$4;
/*     */     }
/*     */     
/*     */     public static InfoPopup parse(RealmsNotification $$0, JsonObject $$1) {
/* 147 */       RealmsText $$2 = (RealmsText)JsonUtils.getRequired("title", $$1, RealmsText::parse);
/* 148 */       RealmsText $$3 = (RealmsText)JsonUtils.getRequired("message", $$1, RealmsText::parse);
/* 149 */       ResourceLocation $$4 = new ResourceLocation(JsonUtils.getRequiredString("image", $$1));
/* 150 */       RealmsNotification.UrlButton $$5 = (RealmsNotification.UrlButton)JsonUtils.getOptional("urlButton", $$1, RealmsNotification.UrlButton::parse);
/* 151 */       return new InfoPopup($$0, $$2, $$3, $$4, $$5);
/*     */     }
/*     */     
/*     */     @Nullable
/*     */     public PopupScreen buildScreen(Screen $$0, Consumer<UUID> $$1) {
/* 156 */       Component $$2 = this.title.createComponent();
/* 157 */       if ($$2 == null) {
/* 158 */         RealmsNotification.LOGGER.warn("Realms info popup had title with no available translation: {}", this.title);
/* 159 */         return null;
/*     */       } 
/*     */ 
/*     */       
/* 163 */       PopupScreen.Builder $$3 = (new PopupScreen.Builder($$0, $$2)).setImage(this.image).setMessage(this.message.createComponent(CommonComponents.EMPTY));
/* 164 */       if (this.urlButton != null) {
/* 165 */         $$3.addButton(this.urlButton.urlText.createComponent(RealmsNotification.BUTTON_TEXT_FALLBACK), $$2 -> {
/*     */               Minecraft $$3 = Minecraft.getInstance();
/*     */ 
/*     */ 
/*     */               
/*     */               $$3.setScreen((Screen)new ConfirmLinkScreen((), this.urlButton.url, true));
/*     */ 
/*     */               
/*     */               $$1.accept(uuid());
/*     */             });
/*     */       }
/*     */ 
/*     */       
/* 178 */       $$3.addButton(CommonComponents.GUI_OK, $$1 -> {
/*     */             $$1.onClose();
/*     */             $$0.accept(uuid());
/*     */           });
/* 182 */       $$3.onClose(() -> $$0.accept(uuid()));
/* 183 */       return $$3.build();
/*     */     } }
/*     */   private static final class UrlButton extends Record { final String url; final RealmsText urlText; private static final String URL = "url"; private static final String URL_TEXT = "urlText";
/*     */     
/* 187 */     private UrlButton(String $$0, RealmsText $$1) { this.url = $$0; this.urlText = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lcom/mojang/realmsclient/dto/RealmsNotification$UrlButton;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #187	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 187 */       //   0	7	0	this	Lcom/mojang/realmsclient/dto/RealmsNotification$UrlButton; } public String url() { return this.url; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lcom/mojang/realmsclient/dto/RealmsNotification$UrlButton;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #187	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/mojang/realmsclient/dto/RealmsNotification$UrlButton; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lcom/mojang/realmsclient/dto/RealmsNotification$UrlButton;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #187	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lcom/mojang/realmsclient/dto/RealmsNotification$UrlButton;
/* 187 */       //   0	8	1	$$0	Ljava/lang/Object; } public RealmsText urlText() { return this.urlText; }
/*     */ 
/*     */ 
/*     */     
/*     */     public static UrlButton parse(JsonObject $$0) {
/* 192 */       String $$1 = JsonUtils.getRequiredString("url", $$0);
/* 193 */       RealmsText $$2 = (RealmsText)JsonUtils.getRequired("urlText", $$0, RealmsText::parse);
/* 194 */       return new UrlButton($$1, $$2);
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\dto\RealmsNotification.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */