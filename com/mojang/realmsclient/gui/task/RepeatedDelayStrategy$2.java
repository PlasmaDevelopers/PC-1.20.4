/*    */ package com.mojang.realmsclient.gui.task;
/*    */ 
/*    */ import com.mojang.logging.LogUtils;
/*    */ import org.slf4j.Logger;
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
/*    */ class null
/*    */   implements RepeatedDelayStrategy
/*    */ {
/* 25 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   
/*    */   private int failureCount;
/*    */ 
/*    */   
/*    */   public long delayCyclesAfterSuccess() {
/* 31 */     this.failureCount = 0;
/* 32 */     return 1L;
/*    */   }
/*    */ 
/*    */   
/*    */   public long delayCyclesAfterFailure() {
/* 37 */     this.failureCount++;
/* 38 */     long $$0 = Math.min(1L << this.failureCount, maxBackoff);
/* 39 */     LOGGER.debug("Skipping for {} extra cycles", Long.valueOf($$0));
/* 40 */     return $$0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\gui\task\RepeatedDelayStrategy$2.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */