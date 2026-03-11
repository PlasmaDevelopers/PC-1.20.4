/*    */ package com.mojang.realmsclient.dto;
/*    */ 
/*    */ import com.google.common.collect.Sets;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonParser;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class Ops
/*    */   extends ValueObject {
/* 11 */   public Set<String> ops = Sets.newHashSet();
/*    */   
/*    */   public static Ops parse(String $$0) {
/* 14 */     Ops $$1 = new Ops();
/* 15 */     JsonParser $$2 = new JsonParser();
/*    */     try {
/* 17 */       JsonElement $$3 = $$2.parse($$0);
/* 18 */       JsonObject $$4 = $$3.getAsJsonObject();
/* 19 */       JsonElement $$5 = $$4.get("ops");
/* 20 */       if ($$5.isJsonArray()) {
/* 21 */         for (JsonElement $$6 : $$5.getAsJsonArray()) {
/* 22 */           $$1.ops.add($$6.getAsString());
/*    */         }
/*    */       }
/* 25 */     } catch (Exception exception) {}
/*    */     
/* 27 */     return $$1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\dto\Ops.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */