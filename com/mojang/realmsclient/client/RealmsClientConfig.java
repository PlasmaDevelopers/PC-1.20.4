/*    */ package com.mojang.realmsclient.client;
/*    */ 
/*    */ import java.net.Proxy;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class RealmsClientConfig {
/*    */   @Nullable
/*    */   private static Proxy proxy;
/*    */   
/*    */   @Nullable
/*    */   public static Proxy getProxy() {
/* 12 */     return proxy;
/*    */   }
/*    */   
/*    */   public static void setProxy(Proxy $$0) {
/* 16 */     if (proxy == null)
/* 17 */       proxy = $$0; 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\client\RealmsClientConfig.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */