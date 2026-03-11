/*    */ package net.minecraft;
/*    */ 
/*    */ public class ReportedException extends RuntimeException {
/*    */   private final CrashReport report;
/*    */   
/*    */   public ReportedException(CrashReport $$0) {
/*  7 */     this.report = $$0;
/*    */   }
/*    */   
/*    */   public CrashReport getReport() {
/* 11 */     return this.report;
/*    */   }
/*    */ 
/*    */   
/*    */   public Throwable getCause() {
/* 16 */     return this.report.getException();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getMessage() {
/* 21 */     return this.report.getTitle();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\ReportedException.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */