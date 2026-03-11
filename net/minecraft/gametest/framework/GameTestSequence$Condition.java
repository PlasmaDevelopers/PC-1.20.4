/*    */ package net.minecraft.gametest.framework;
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
/*    */ public class Condition
/*    */ {
/*    */   private static final long NOT_TRIGGERED = -1L;
/* 16 */   private long triggerTime = -1L;
/*    */   
/*    */   void trigger(long $$0) {
/* 19 */     if (this.triggerTime != -1L) {
/* 20 */       throw new IllegalStateException("Condition already triggered at " + this.triggerTime);
/*    */     }
/* 22 */     this.triggerTime = $$0;
/*    */   }
/*    */   
/*    */   public void assertTriggeredThisTick() {
/* 26 */     long $$0 = GameTestSequence.this.parent.getTick();
/* 27 */     if (this.triggerTime != $$0) {
/* 28 */       if (this.triggerTime == -1L) {
/* 29 */         throw new GameTestAssertException("Condition not triggered (t=" + $$0 + ")");
/*    */       }
/* 31 */       throw new GameTestAssertException("Condition triggered at " + this.triggerTime + ", (t=" + $$0 + ")");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\gametest\framework\GameTestSequence$Condition.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */