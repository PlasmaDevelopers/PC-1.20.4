/*    */ package com.mojang.realmsclient.dto;
/*    */ 
/*    */ import com.google.gson.Gson;
/*    */ import com.google.gson.JsonElement;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class GuardedSerializer
/*    */ {
/*  9 */   private final Gson gson = new Gson();
/*    */   
/*    */   public String toJson(ReflectionBasedSerialization $$0) {
/* 12 */     return this.gson.toJson($$0);
/*    */   }
/*    */   
/*    */   public String toJson(JsonElement $$0) {
/* 16 */     return this.gson.toJson($$0);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public <T extends ReflectionBasedSerialization> T fromJson(String $$0, Class<T> $$1) {
/* 21 */     return (T)this.gson.fromJson($$0, $$1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\dto\GuardedSerializer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */