/*    */ package net.minecraft.gametest.framework;
/*    */ 
/*    */ 
/*    */ class ExhaustedAttemptsException
/*    */   extends Throwable
/*    */ {
/*    */   public ExhaustedAttemptsException(int $$0, int $$1, GameTestInfo $$2) {
/*  8 */     super("Not enough successes: " + $$1 + " out of " + $$0 + " attempts. Required successes: " + $$2
/*    */         
/* 10 */         .requiredSuccesses() + ". max attempts: " + $$2.maxAttempts() + ".", $$2.getError());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\gametest\framework\ExhaustedAttemptsException.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */