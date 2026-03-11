/*    */ package net.minecraft;
/*    */ 
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class DefaultUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
/*    */   private final Logger logger;
/*    */   
/*    */   public DefaultUncaughtExceptionHandler(Logger $$0) {
/*  9 */     this.logger = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void uncaughtException(Thread $$0, Throwable $$1) {
/* 14 */     this.logger.error("Caught previously unhandled exception :", $$1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\DefaultUncaughtExceptionHandler.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */