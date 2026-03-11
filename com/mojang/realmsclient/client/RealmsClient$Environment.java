/*    */ package com.mojang.realmsclient.client;
/*    */ 
/*    */ import java.util.Locale;
/*    */ import java.util.Optional;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum Environment
/*    */ {
/* 47 */   PRODUCTION("pc.realms.minecraft.net", "https"),
/* 48 */   STAGE("pc-stage.realms.minecraft.net", "https"),
/* 49 */   LOCAL("localhost:8080", "http");
/*    */   
/*    */   public final String baseUrl;
/*    */   public final String protocol;
/*    */   
/*    */   Environment(String $$0, String $$1) {
/* 55 */     this.baseUrl = $$0;
/* 56 */     this.protocol = $$1;
/*    */   }
/*    */   
/*    */   public static Optional<Environment> byName(String $$0) {
/* 60 */     switch ($$0.toLowerCase(Locale.ROOT)) { case "production": case "local": case "stage": case "staging":  }  return 
/*    */ 
/*    */ 
/*    */       
/* 64 */       Optional.empty();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\client\RealmsClient$Environment.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */