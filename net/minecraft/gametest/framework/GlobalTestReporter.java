/*    */ package net.minecraft.gametest.framework;
/*    */ 
/*    */ public class GlobalTestReporter {
/*  4 */   private static TestReporter DELEGATE = new LogTestReporter();
/*    */   
/*    */   public static void replaceWith(TestReporter $$0) {
/*  7 */     DELEGATE = $$0;
/*    */   }
/*    */   
/*    */   public static void onTestFailed(GameTestInfo $$0) {
/* 11 */     DELEGATE.onTestFailed($$0);
/*    */   }
/*    */   
/*    */   public static void onTestSuccess(GameTestInfo $$0) {
/* 15 */     DELEGATE.onTestSuccess($$0);
/*    */   }
/*    */   
/*    */   public static void finish() {
/* 19 */     DELEGATE.finish();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\gametest\framework\GlobalTestReporter.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */