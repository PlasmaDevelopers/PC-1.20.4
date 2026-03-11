/*    */ package com.mojang.realmsclient.dto;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonParser;
/*    */ import com.mojang.logging.LogUtils;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class BackupList
/*    */   extends ValueObject {
/* 13 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   
/*    */   public List<Backup> backups;
/*    */   
/*    */   public static BackupList parse(String $$0) {
/* 18 */     JsonParser $$1 = new JsonParser();
/*    */     
/* 20 */     BackupList $$2 = new BackupList();
/* 21 */     $$2.backups = Lists.newArrayList();
/*    */     try {
/* 23 */       JsonElement $$3 = $$1.parse($$0).getAsJsonObject().get("backups");
/* 24 */       if ($$3.isJsonArray()) {
/* 25 */         Iterator<JsonElement> $$4 = $$3.getAsJsonArray().iterator();
/* 26 */         while ($$4.hasNext()) {
/* 27 */           $$2.backups.add(Backup.parse($$4.next()));
/*    */         }
/*    */       } 
/* 30 */     } catch (Exception $$5) {
/* 31 */       LOGGER.error("Could not parse BackupList: {}", $$5.getMessage());
/*    */     } 
/* 33 */     return $$2;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\dto\BackupList.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */