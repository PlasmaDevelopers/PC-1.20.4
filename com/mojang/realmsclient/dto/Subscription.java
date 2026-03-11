/*    */ package com.mojang.realmsclient.dto;
/*    */ 
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonParser;
/*    */ import com.mojang.logging.LogUtils;
/*    */ import com.mojang.realmsclient.util.JsonUtils;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ 
/*    */ public class Subscription
/*    */   extends ValueObject
/*    */ {
/* 13 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   
/*    */   public long startDate;
/*    */   public int daysLeft;
/* 17 */   public SubscriptionType type = SubscriptionType.NORMAL;
/*    */   
/*    */   public static Subscription parse(String $$0) {
/* 20 */     Subscription $$1 = new Subscription();
/*    */     try {
/* 22 */       JsonParser $$2 = new JsonParser();
/* 23 */       JsonObject $$3 = $$2.parse($$0).getAsJsonObject();
/* 24 */       $$1.startDate = JsonUtils.getLongOr("startDate", $$3, 0L);
/* 25 */       $$1.daysLeft = JsonUtils.getIntOr("daysLeft", $$3, 0);
/* 26 */       $$1.type = typeFrom(JsonUtils.getStringOr("subscriptionType", $$3, SubscriptionType.NORMAL.name()));
/* 27 */     } catch (Exception $$4) {
/* 28 */       LOGGER.error("Could not parse Subscription: {}", $$4.getMessage());
/*    */     } 
/* 30 */     return $$1;
/*    */   }
/*    */   
/*    */   private static SubscriptionType typeFrom(String $$0) {
/*    */     try {
/* 35 */       return SubscriptionType.valueOf($$0);
/* 36 */     } catch (Exception $$1) {
/* 37 */       return SubscriptionType.NORMAL;
/*    */     } 
/*    */   }
/*    */   
/*    */   public enum SubscriptionType {
/* 42 */     NORMAL, RECURRING;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\dto\Subscription.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */