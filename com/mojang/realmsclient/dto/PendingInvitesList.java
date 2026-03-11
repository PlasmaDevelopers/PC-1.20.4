/*    */ package com.mojang.realmsclient.dto;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonParser;
/*    */ import com.mojang.logging.LogUtils;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class PendingInvitesList
/*    */   extends ValueObject {
/* 14 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   
/* 16 */   public List<PendingInvite> pendingInvites = Lists.newArrayList();
/*    */   
/*    */   public static PendingInvitesList parse(String $$0) {
/* 19 */     PendingInvitesList $$1 = new PendingInvitesList();
/*    */     try {
/* 21 */       JsonParser $$2 = new JsonParser();
/* 22 */       JsonObject $$3 = $$2.parse($$0).getAsJsonObject();
/* 23 */       if ($$3.get("invites").isJsonArray()) {
/* 24 */         Iterator<JsonElement> $$4 = $$3.get("invites").getAsJsonArray().iterator();
/* 25 */         while ($$4.hasNext()) {
/* 26 */           $$1.pendingInvites.add(PendingInvite.parse(((JsonElement)$$4.next()).getAsJsonObject()));
/*    */         }
/*    */       } 
/* 29 */     } catch (Exception $$5) {
/* 30 */       LOGGER.error("Could not parse PendingInvitesList: {}", $$5.getMessage());
/*    */     } 
/* 32 */     return $$1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\dto\PendingInvitesList.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */