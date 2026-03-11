/*    */ package com.mojang.realmsclient.gui.task;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class null
/*    */   implements RepeatedDelayStrategy
/*    */ {
/*    */   public long delayCyclesAfterSuccess() {
/* 10 */     return 1L;
/*    */   }
/*    */ 
/*    */   
/*    */   public long delayCyclesAfterFailure() {
/* 15 */     return 1L;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\gui\task\RepeatedDelayStrategy$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */