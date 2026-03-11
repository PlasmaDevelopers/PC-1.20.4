/*    */ package net.minecraft.world;
/*    */ 
/*    */ public enum InteractionResult {
/*  4 */   SUCCESS,
/*  5 */   CONSUME,
/*  6 */   CONSUME_PARTIAL,
/*  7 */   PASS,
/*  8 */   FAIL;
/*    */   
/*    */   public boolean consumesAction() {
/* 11 */     return (this == SUCCESS || this == CONSUME || this == CONSUME_PARTIAL);
/*    */   }
/*    */   
/*    */   public boolean shouldSwing() {
/* 15 */     return (this == SUCCESS);
/*    */   }
/*    */   
/*    */   public boolean shouldAwardStats() {
/* 19 */     return (this == SUCCESS || this == CONSUME);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static InteractionResult sidedSuccess(boolean $$0) {
/* 27 */     return $$0 ? SUCCESS : CONSUME;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\InteractionResult.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */