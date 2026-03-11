/*    */ package com.mojang.realmsclient.dto;
/*    */ 
/*    */ import com.google.gson.JsonObject;
/*    */ import com.mojang.logging.LogUtils;
/*    */ import com.mojang.realmsclient.util.JsonUtils;
/*    */ import java.util.Date;
/*    */ import java.util.UUID;
/*    */ import net.minecraft.Util;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PendingInvite
/*    */   extends ValueObject
/*    */ {
/* 16 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   
/*    */   public String invitationId;
/*    */   public String worldName;
/*    */   public String worldOwnerName;
/*    */   public UUID worldOwnerUuid;
/*    */   public Date date;
/*    */   
/*    */   public static PendingInvite parse(JsonObject $$0) {
/* 25 */     PendingInvite $$1 = new PendingInvite();
/*    */     try {
/* 27 */       $$1.invitationId = JsonUtils.getStringOr("invitationId", $$0, "");
/* 28 */       $$1.worldName = JsonUtils.getStringOr("worldName", $$0, "");
/* 29 */       $$1.worldOwnerName = JsonUtils.getStringOr("worldOwnerName", $$0, "");
/* 30 */       $$1.worldOwnerUuid = JsonUtils.getUuidOr("worldOwnerUuid", $$0, Util.NIL_UUID);
/* 31 */       $$1.date = JsonUtils.getDateOr("date", $$0);
/* 32 */     } catch (Exception $$2) {
/* 33 */       LOGGER.error("Could not parse PendingInvite: {}", $$2.getMessage());
/*    */     } 
/* 35 */     return $$1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\dto\PendingInvite.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */