/*    */ package com.mojang.realmsclient.dto;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import com.google.gson.JsonArray;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonParser;
/*    */ import com.mojang.logging.LogUtils;
/*    */ import com.mojang.realmsclient.util.JsonUtils;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.UUID;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RealmsServerPlayerList
/*    */   extends ValueObject
/*    */ {
/* 20 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   
/*    */   public long serverId;
/*    */   public List<UUID> players;
/*    */   
/*    */   public static RealmsServerPlayerList parse(JsonObject $$0) {
/* 26 */     RealmsServerPlayerList $$1 = new RealmsServerPlayerList();
/*    */     
/*    */     try {
/* 29 */       $$1.serverId = JsonUtils.getLongOr("serverId", $$0, -1L);
/*    */       
/* 31 */       String $$2 = JsonUtils.getStringOr("playerList", $$0, null);
/*    */       
/* 33 */       if ($$2 != null) {
/* 34 */         JsonElement $$3 = JsonParser.parseString($$2);
/*    */         
/* 36 */         if ($$3.isJsonArray()) {
/* 37 */           $$1.players = parsePlayers($$3.getAsJsonArray());
/*    */         } else {
/* 39 */           $$1.players = Lists.newArrayList();
/*    */         } 
/*    */       } else {
/* 42 */         $$1.players = Lists.newArrayList();
/*    */       } 
/* 44 */     } catch (Exception $$4) {
/* 45 */       LOGGER.error("Could not parse RealmsServerPlayerList: {}", $$4.getMessage());
/*    */     } 
/*    */     
/* 48 */     return $$1;
/*    */   }
/*    */   
/*    */   private static List<UUID> parsePlayers(JsonArray $$0) {
/* 52 */     List<UUID> $$1 = new ArrayList<>($$0.size());
/* 53 */     for (JsonElement $$2 : $$0) {
/* 54 */       if ($$2.isJsonObject()) {
/* 55 */         UUID $$3 = JsonUtils.getUuidOr("playerId", $$2.getAsJsonObject(), null);
/* 56 */         if ($$3 != null) {
/* 57 */           $$1.add($$3);
/*    */         }
/*    */       } 
/*    */     } 
/* 61 */     return $$1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\dto\RealmsServerPlayerList.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */