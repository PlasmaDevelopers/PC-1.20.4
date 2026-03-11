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
/*    */ public class RealmsServerPlayerLists
/*    */   extends ValueObject {
/* 15 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   
/*    */   public List<RealmsServerPlayerList> servers;
/*    */   
/*    */   public static RealmsServerPlayerLists parse(String $$0) {
/* 20 */     RealmsServerPlayerLists $$1 = new RealmsServerPlayerLists();
/* 21 */     $$1.servers = Lists.newArrayList();
/*    */     
/*    */     try {
/* 24 */       JsonParser $$2 = new JsonParser();
/* 25 */       JsonObject $$3 = $$2.parse($$0).getAsJsonObject();
/*    */       
/* 27 */       if ($$3.get("lists").isJsonArray()) {
/* 28 */         JsonArray $$4 = $$3.get("lists").getAsJsonArray();
/*    */         
/* 30 */         Iterator<JsonElement> $$5 = $$4.iterator();
/*    */         
/* 32 */         while ($$5.hasNext()) {
/* 33 */           $$1.servers.add(RealmsServerPlayerList.parse(((JsonElement)$$5.next()).getAsJsonObject()));
/*    */         }
/*    */       } 
/* 36 */     } catch (Exception $$6) {
/* 37 */       LOGGER.error("Could not parse RealmsServerPlayerLists: {}", $$6.getMessage());
/*    */     } 
/*    */     
/* 40 */     return $$1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\dto\RealmsServerPlayerLists.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */