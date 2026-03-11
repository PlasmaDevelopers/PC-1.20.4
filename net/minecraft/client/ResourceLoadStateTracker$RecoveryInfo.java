/*    */ package net.minecraft.client;
/*    */ 
/*    */ import java.io.PrintWriter;
/*    */ import java.io.StringWriter;
/*    */ import net.minecraft.CrashReportCategory;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class RecoveryInfo
/*    */ {
/*    */   private final Throwable error;
/*    */   
/*    */   RecoveryInfo(Throwable $$0) {
/* 60 */     this.error = $$0;
/*    */   }
/*    */   
/*    */   public void fillCrashInfo(CrashReportCategory $$0) {
/* 64 */     $$0.setDetail("Recovery", "Yes");
/*    */     
/* 66 */     $$0.setDetail("Recovery reason", () -> {
/*    */           StringWriter $$0 = new StringWriter();
/*    */           this.error.printStackTrace(new PrintWriter($$0));
/*    */           return $$0.toString();
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\ResourceLoadStateTracker$RecoveryInfo.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */