/*    */ package com.mojang.realmsclient.dto;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.mojang.logging.LogUtils;
/*    */ import com.mojang.realmsclient.util.JsonUtils;
/*    */ import java.util.Date;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Backup
/*    */   extends ValueObject
/*    */ {
/* 18 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   
/*    */   public String backupId;
/*    */   
/*    */   public Date lastModifiedDate;
/*    */   
/*    */   public long size;
/*    */   private boolean uploadedVersion;
/* 26 */   public Map<String, String> metadata = Maps.newHashMap();
/*    */ 
/*    */   
/* 29 */   public Map<String, String> changeList = Maps.newHashMap();
/*    */   
/*    */   public static Backup parse(JsonElement $$0) {
/* 32 */     JsonObject $$1 = $$0.getAsJsonObject();
/* 33 */     Backup $$2 = new Backup();
/*    */     try {
/* 35 */       $$2.backupId = JsonUtils.getStringOr("backupId", $$1, "");
/* 36 */       $$2.lastModifiedDate = JsonUtils.getDateOr("lastModifiedDate", $$1);
/* 37 */       $$2.size = JsonUtils.getLongOr("size", $$1, 0L);
/* 38 */       if ($$1.has("metadata")) {
/* 39 */         JsonObject $$3 = $$1.getAsJsonObject("metadata");
/* 40 */         Set<Map.Entry<String, JsonElement>> $$4 = $$3.entrySet();
/* 41 */         for (Map.Entry<String, JsonElement> $$5 : $$4) {
/* 42 */           if (!((JsonElement)$$5.getValue()).isJsonNull()) {
/* 43 */             $$2.metadata.put($$5.getKey(), ((JsonElement)$$5.getValue()).getAsString());
/*    */           }
/*    */         } 
/*    */       } 
/* 47 */     } catch (Exception $$6) {
/* 48 */       LOGGER.error("Could not parse Backup: {}", $$6.getMessage());
/*    */     } 
/* 50 */     return $$2;
/*    */   }
/*    */   
/*    */   public boolean isUploadedVersion() {
/* 54 */     return this.uploadedVersion;
/*    */   }
/*    */   
/*    */   public void setUploadedVersion(boolean $$0) {
/* 58 */     this.uploadedVersion = $$0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\dto\Backup.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */