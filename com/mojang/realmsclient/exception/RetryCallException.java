/*    */ package com.mojang.realmsclient.exception;
/*    */ 
/*    */ import com.mojang.realmsclient.client.RealmsError;
/*    */ 
/*    */ public class RetryCallException
/*    */   extends RealmsServiceException {
/*    */   public static final int DEFAULT_DELAY = 5;
/*    */   public final int delaySeconds;
/*    */   
/*    */   public RetryCallException(int $$0, int $$1) {
/* 11 */     super((RealmsError)RealmsError.CustomError.retry($$1));
/*    */     
/* 13 */     if ($$0 < 0 || $$0 > 120) {
/* 14 */       this.delaySeconds = 5;
/*    */     } else {
/* 16 */       this.delaySeconds = $$0;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\exception\RetryCallException.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */