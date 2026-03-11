/*    */ package com.mojang.realmsclient.dto;
/*    */ 
/*    */ import com.google.gson.JsonArray;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.mojang.realmsclient.util.JsonUtils;
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.client.resources.language.I18n;
/*    */ import net.minecraft.network.chat.Component;
/*    */ 
/*    */ 
/*    */ public class RealmsText
/*    */ {
/*    */   private static final String TRANSLATION_KEY = "translationKey";
/*    */   private static final String ARGS = "args";
/*    */   private final String translationKey;
/*    */   @Nullable
/*    */   private final String[] args;
/*    */   
/*    */   private RealmsText(String $$0, @Nullable String[] $$1) {
/* 22 */     this.translationKey = $$0;
/* 23 */     this.args = $$1;
/*    */   }
/*    */   
/*    */   public Component createComponent(Component $$0) {
/* 27 */     return Objects.<Component>requireNonNullElse(createComponent(), $$0);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public Component createComponent() {
/* 32 */     if (!I18n.exists(this.translationKey)) {
/* 33 */       return null;
/*    */     }
/* 35 */     if (this.args == null) {
/* 36 */       return (Component)Component.translatable(this.translationKey);
/*    */     }
/* 38 */     return (Component)Component.translatable(this.translationKey, (Object[])this.args);
/*    */   }
/*    */ 
/*    */   
/*    */   public static RealmsText parse(JsonObject $$0) {
/* 43 */     String $$5[], $$1 = JsonUtils.getRequiredString("translationKey", $$0);
/* 44 */     JsonElement $$2 = $$0.get("args");
/*    */     
/* 46 */     if ($$2 == null || $$2.isJsonNull()) {
/* 47 */       String[] $$3 = null;
/*    */     } else {
/* 49 */       JsonArray $$4 = $$2.getAsJsonArray();
/* 50 */       $$5 = new String[$$4.size()];
/* 51 */       for (int $$6 = 0; $$6 < $$4.size(); $$6++) {
/* 52 */         $$5[$$6] = $$4.get($$6).getAsString();
/*    */       }
/*    */     } 
/* 55 */     return new RealmsText($$1, $$5);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 60 */     return this.translationKey;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\dto\RealmsText.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */