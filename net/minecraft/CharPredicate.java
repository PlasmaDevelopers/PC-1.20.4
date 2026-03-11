/*    */ package net.minecraft;
/*    */ 
/*    */ import java.util.Objects;
/*    */ 
/*    */ 
/*    */ @FunctionalInterface
/*    */ public interface CharPredicate
/*    */ {
/*    */   default CharPredicate and(CharPredicate $$0) {
/* 10 */     Objects.requireNonNull($$0);
/* 11 */     return $$1 -> (test($$1) && $$0.test($$1));
/*    */   }
/*    */   
/*    */   default CharPredicate negate() {
/* 15 */     return $$0 -> !test($$0);
/*    */   }
/*    */   
/*    */   default CharPredicate or(CharPredicate $$0) {
/* 19 */     Objects.requireNonNull($$0);
/* 20 */     return $$1 -> (test($$1) || $$0.test($$1));
/*    */   }
/*    */   
/*    */   boolean test(char paramChar);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\CharPredicate.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */