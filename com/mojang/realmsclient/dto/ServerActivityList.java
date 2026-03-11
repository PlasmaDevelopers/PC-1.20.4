/*    */ package com.mojang.realmsclient.dto;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import com.google.gson.JsonArray;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonParser;
/*    */ import com.mojang.realmsclient.util.JsonUtils;
/*    */ import java.util.List;
/*    */ 
/*    */ public class ServerActivityList
/*    */   extends ValueObject
/*    */ {
/*    */   public long periodInMillis;
/* 15 */   public List<ServerActivity> serverActivities = Lists.newArrayList();
/*    */   
/*    */   public static ServerActivityList parse(String $$0) {
/* 18 */     ServerActivityList $$1 = new ServerActivityList();
/* 19 */     JsonParser $$2 = new JsonParser();
/*    */     try {
/* 21 */       JsonElement $$3 = $$2.parse($$0);
/* 22 */       JsonObject $$4 = $$3.getAsJsonObject();
/* 23 */       $$1.periodInMillis = JsonUtils.getLongOr("periodInMillis", $$4, -1L);
/* 24 */       JsonElement $$5 = $$4.get("playerActivityDto");
/* 25 */       if ($$5 != null && $$5.isJsonArray()) {
/* 26 */         JsonArray $$6 = $$5.getAsJsonArray();
/* 27 */         for (JsonElement $$7 : $$6) {
/* 28 */           ServerActivity $$8 = ServerActivity.parse($$7.getAsJsonObject());
/* 29 */           $$1.serverActivities.add($$8);
/*    */         } 
/*    */       } 
/* 32 */     } catch (Exception exception) {}
/*    */     
/* 34 */     return $$1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\dto\ServerActivityList.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */