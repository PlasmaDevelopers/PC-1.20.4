/*    */ package com.mojang.realmsclient.dto;
/*    */ 
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonParser;
/*    */ import com.mojang.logging.LogUtils;
/*    */ import com.mojang.realmsclient.util.JsonUtils;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class RealmsServerAddress
/*    */   extends ValueObject {
/* 11 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   
/*    */   public String address;
/*    */   public String resourcePackUrl;
/*    */   public String resourcePackHash;
/*    */   
/*    */   public static RealmsServerAddress parse(String $$0) {
/* 18 */     JsonParser $$1 = new JsonParser();
/* 19 */     RealmsServerAddress $$2 = new RealmsServerAddress();
/*    */     try {
/* 21 */       JsonObject $$3 = $$1.parse($$0).getAsJsonObject();
/* 22 */       $$2.address = JsonUtils.getStringOr("address", $$3, null);
/* 23 */       $$2.resourcePackUrl = JsonUtils.getStringOr("resourcePackUrl", $$3, null);
/* 24 */       $$2.resourcePackHash = JsonUtils.getStringOr("resourcePackHash", $$3, null);
/* 25 */     } catch (Exception $$4) {
/* 26 */       LOGGER.error("Could not parse RealmsServerAddress: {}", $$4.getMessage());
/*    */     } 
/*    */     
/* 29 */     return $$2;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\dto\RealmsServerAddress.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */