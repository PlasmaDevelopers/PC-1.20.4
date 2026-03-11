/*    */ package net.minecraft.core;
/*    */ 
/*    */ 
/*    */ enum null
/*    */ {
/*    */   public int cycle(int $$0, int $$1, int $$2, Direction.Axis $$3) {
/*  7 */     return $$3.choose($$0, $$1, $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   public double cycle(double $$0, double $$1, double $$2, Direction.Axis $$3) {
/* 12 */     return $$3.choose($$0, $$1, $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   public Direction.Axis cycle(Direction.Axis $$0) {
/* 17 */     return $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public AxisCycle inverse() {
/* 22 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\AxisCycle$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */