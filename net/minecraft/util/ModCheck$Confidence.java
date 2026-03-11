/*    */ package net.minecraft.util;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum Confidence
/*    */ {
/* 13 */   PROBABLY_NOT("Probably not.", false),
/* 14 */   VERY_LIKELY("Very likely;", true),
/* 15 */   DEFINITELY("Definitely;", true);
/*    */   
/*    */   final String description;
/*    */   
/*    */   final boolean shouldReportAsModified;
/*    */   
/*    */   Confidence(String $$0, boolean $$1) {
/* 22 */     this.description = $$0;
/* 23 */     this.shouldReportAsModified = $$1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\ModCheck$Confidence.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */