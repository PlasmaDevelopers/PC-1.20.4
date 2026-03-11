/*    */ package com.mojang.realmsclient.dto;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import com.google.gson.JsonArray;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonParser;
/*    */ import com.mojang.logging.LogUtils;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class RealmsServerList
/*    */   extends ValueObject {
/* 15 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   
/*    */   public List<RealmsServer> servers;
/*    */   
/*    */   public static RealmsServerList parse(String $$0) {
/* 20 */     RealmsServerList $$1 = new RealmsServerList();
/* 21 */     $$1.servers = Lists.newArrayList();
/*    */     try {
/* 23 */       JsonParser $$2 = new JsonParser();
/* 24 */       JsonObject $$3 = $$2.parse($$0).getAsJsonObject();
/* 25 */       if ($$3.get("servers").isJsonArray()) {
/* 26 */         JsonArray $$4 = $$3.get("servers").getAsJsonArray();
/* 27 */         Iterator<JsonElement> $$5 = $$4.iterator();
/* 28 */         while ($$5.hasNext()) {
/* 29 */           $$1.servers.add(RealmsServer.parse(((JsonElement)$$5.next()).getAsJsonObject()));
/*    */         }
/*    */       } 
/* 32 */     } catch (Exception $$6) {
/* 33 */       LOGGER.error("Could not parse McoServerList: {}", $$6.getMessage());
/*    */     } 
/* 35 */     return $$1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\dto\RealmsServerList.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */