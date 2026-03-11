/*    */ package com.mojang.realmsclient.dto;
/*    */ 
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonParser;
/*    */ import com.mojang.logging.LogUtils;
/*    */ import com.mojang.realmsclient.util.JsonUtils;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class RealmsNews
/*    */   extends ValueObject {
/* 11 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   
/*    */   public String newsLink;
/*    */   
/*    */   public static RealmsNews parse(String $$0) {
/* 16 */     RealmsNews $$1 = new RealmsNews();
/*    */     
/*    */     try {
/* 19 */       JsonParser $$2 = new JsonParser();
/* 20 */       JsonObject $$3 = $$2.parse($$0).getAsJsonObject();
/* 21 */       $$1.newsLink = JsonUtils.getStringOr("newsLink", $$3, null);
/* 22 */     } catch (Exception $$4) {
/* 23 */       LOGGER.error("Could not parse RealmsNews: {}", $$4.getMessage());
/*    */     } 
/*    */     
/* 26 */     return $$1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\dto\RealmsNews.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */