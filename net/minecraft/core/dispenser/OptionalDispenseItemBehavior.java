/*    */ package net.minecraft.core.dispenser;
/*    */ 
/*    */ public abstract class OptionalDispenseItemBehavior
/*    */   extends DefaultDispenseItemBehavior
/*    */ {
/*    */   private boolean success = true;
/*    */   
/*    */   public boolean isSuccess() {
/*  9 */     return this.success;
/*    */   }
/*    */   
/*    */   public void setSuccess(boolean $$0) {
/* 13 */     this.success = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void playSound(BlockSource $$0) {
/* 18 */     $$0.level().levelEvent(isSuccess() ? 1000 : 1001, $$0.pos(), 0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\dispenser\OptionalDispenseItemBehavior.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */