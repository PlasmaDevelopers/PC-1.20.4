/*    */ package net.minecraft;
/*    */ 
/*    */ import org.apache.commons.lang3.StringEscapeUtils;
/*    */ 
/*    */ public class ResourceLocationException extends RuntimeException {
/*    */   public ResourceLocationException(String $$0) {
/*  7 */     super(StringEscapeUtils.escapeJava($$0));
/*    */   }
/*    */   
/*    */   public ResourceLocationException(String $$0, Throwable $$1) {
/* 11 */     super(StringEscapeUtils.escapeJava($$0), $$1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\ResourceLocationException.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */