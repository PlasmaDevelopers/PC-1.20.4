/*    */ package net.minecraft.gametest.framework;
/*    */ 
/*    */ import java.util.function.Consumer;
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
/*    */ 
/*    */ class null
/*    */   implements GameTestListener
/*    */ {
/*    */   public void testStructureLoaded(GameTestInfo $$0) {}
/*    */   
/*    */   public void testPassed(GameTestInfo $$0) {}
/*    */   
/*    */   public void testFailed(GameTestInfo $$0) {
/* 53 */     listener.accept($$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\gametest\framework\MultipleTestTracker$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */