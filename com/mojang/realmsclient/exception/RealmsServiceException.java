/*    */ package com.mojang.realmsclient.exception;
/*    */ 
/*    */ import com.mojang.realmsclient.client.RealmsError;
/*    */ 
/*    */ public class RealmsServiceException extends Exception {
/*    */   public final RealmsError realmsError;
/*    */   
/*    */   public RealmsServiceException(RealmsError $$0) {
/*  9 */     this.realmsError = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getMessage() {
/* 14 */     return this.realmsError.logMessage();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\exception\RealmsServiceException.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */