/*    */ package net.minecraft.util;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class ExceptionCollector<T extends Throwable> {
/*    */   @Nullable
/*    */   private T result;
/*    */   
/*    */   public void add(T $$0) {
/* 10 */     if (this.result == null) {
/* 11 */       this.result = $$0;
/*    */     } else {
/* 13 */       this.result.addSuppressed((Throwable)$$0);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void throwIfPresent() throws T {
/* 18 */     if (this.result != null)
/* 19 */       throw this.result; 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\ExceptionCollector.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */