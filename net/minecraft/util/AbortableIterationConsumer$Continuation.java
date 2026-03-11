/*    */ package net.minecraft.util;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum Continuation
/*    */ {
/*  8 */   CONTINUE,
/*  9 */   ABORT;
/*    */   
/*    */   public boolean shouldAbort() {
/* 12 */     return (this == ABORT);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\AbortableIterationConsumer$Continuation.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */