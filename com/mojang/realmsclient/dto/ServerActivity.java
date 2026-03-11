/*    */ package com.mojang.realmsclient.dto;
/*    */ 
/*    */ import com.google.gson.JsonObject;
/*    */ import com.mojang.realmsclient.util.JsonUtils;
/*    */ 
/*    */ public class ServerActivity
/*    */   extends ValueObject
/*    */ {
/*    */   public String profileUuid;
/*    */   public long joinTime;
/*    */   public long leaveTime;
/*    */   
/*    */   public static ServerActivity parse(JsonObject $$0) {
/* 14 */     ServerActivity $$1 = new ServerActivity();
/*    */     try {
/* 16 */       $$1.profileUuid = JsonUtils.getStringOr("profileUuid", $$0, null);
/* 17 */       $$1.joinTime = JsonUtils.getLongOr("joinTime", $$0, Long.MIN_VALUE);
/* 18 */       $$1.leaveTime = JsonUtils.getLongOr("leaveTime", $$0, Long.MIN_VALUE);
/* 19 */     } catch (Exception exception) {}
/*    */     
/* 21 */     return $$1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\dto\ServerActivity.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */