/*    */ package net.minecraft.core;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ enum null
/*    */ {
/*    */   public int cycle(int $$0, int $$1, int $$2, Direction.Axis $$3) {
/* 52 */     return $$3.choose($$1, $$2, $$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public double cycle(double $$0, double $$1, double $$2, Direction.Axis $$3) {
/* 57 */     return $$3.choose($$1, $$2, $$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public Direction.Axis cycle(Direction.Axis $$0) {
/* 62 */     return AXIS_VALUES[Math.floorMod($$0.ordinal() - 1, 3)];
/*    */   }
/*    */ 
/*    */   
/*    */   public AxisCycle inverse() {
/* 67 */     return FORWARD;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\AxisCycle$3.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */