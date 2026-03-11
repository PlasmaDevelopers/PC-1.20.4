/*    */ package com.mojang.realmsclient.dto;
/*    */ 
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonParser;
/*    */ import com.mojang.logging.LogUtils;
/*    */ import com.mojang.realmsclient.util.JsonUtils;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class WorldDownload
/*    */   extends ValueObject {
/* 11 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   
/*    */   public String downloadLink;
/*    */   public String resourcePackUrl;
/*    */   public String resourcePackHash;
/*    */   
/*    */   public static WorldDownload parse(String $$0) {
/* 18 */     JsonParser $$1 = new JsonParser();
/* 19 */     JsonObject $$2 = $$1.parse($$0).getAsJsonObject();
/*    */     
/* 21 */     WorldDownload $$3 = new WorldDownload();
/*    */     
/*    */     try {
/* 24 */       $$3.downloadLink = JsonUtils.getStringOr("downloadLink", $$2, "");
/* 25 */       $$3.resourcePackUrl = JsonUtils.getStringOr("resourcePackUrl", $$2, "");
/* 26 */       $$3.resourcePackHash = JsonUtils.getStringOr("resourcePackHash", $$2, "");
/* 27 */     } catch (Exception $$4) {
/* 28 */       LOGGER.error("Could not parse WorldDownload: {}", $$4.getMessage());
/*    */     } 
/*    */     
/* 31 */     return $$3;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\dto\WorldDownload.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */