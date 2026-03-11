/*    */ package net.minecraft.util;
/*    */ 
/*    */ @FunctionalInterface
/*    */ public interface AbortableIterationConsumer<T> {
/*    */   Continuation accept(T paramT);
/*    */   
/*    */   public enum Continuation {
/*  8 */     CONTINUE,
/*  9 */     ABORT;
/*    */     
/*    */     public boolean shouldAbort() {
/* 12 */       return (this == ABORT);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static <T> AbortableIterationConsumer<T> forConsumer(Consumer<T> $$0) {
/* 24 */     return $$1 -> {
/*    */         $$0.accept($$1);
/*    */         return Continuation.CONTINUE;
/*    */       };
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\AbortableIterationConsumer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */