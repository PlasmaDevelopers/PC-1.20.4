/*    */ package net.minecraft.world;
/*    */ 
/*    */ public class InteractionResultHolder<T> {
/*    */   private final InteractionResult result;
/*    */   private final T object;
/*    */   
/*    */   public InteractionResultHolder(InteractionResult $$0, T $$1) {
/*  8 */     this.result = $$0;
/*  9 */     this.object = $$1;
/*    */   }
/*    */   
/*    */   public InteractionResult getResult() {
/* 13 */     return this.result;
/*    */   }
/*    */   
/*    */   public T getObject() {
/* 17 */     return this.object;
/*    */   }
/*    */   
/*    */   public static <T> InteractionResultHolder<T> success(T $$0) {
/* 21 */     return new InteractionResultHolder<>(InteractionResult.SUCCESS, $$0);
/*    */   }
/*    */   
/*    */   public static <T> InteractionResultHolder<T> consume(T $$0) {
/* 25 */     return new InteractionResultHolder<>(InteractionResult.CONSUME, $$0);
/*    */   }
/*    */   
/*    */   public static <T> InteractionResultHolder<T> pass(T $$0) {
/* 29 */     return new InteractionResultHolder<>(InteractionResult.PASS, $$0);
/*    */   }
/*    */   
/*    */   public static <T> InteractionResultHolder<T> fail(T $$0) {
/* 33 */     return new InteractionResultHolder<>(InteractionResult.FAIL, $$0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static <T> InteractionResultHolder<T> sidedSuccess(T $$0, boolean $$1) {
/* 41 */     return $$1 ? success($$0) : consume($$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\InteractionResultHolder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */