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
/*    */ enum null
/*    */ {
/*    */   public int cycle(int $$0, int $$1, int $$2, Direction.Axis $$3) {
/* 31 */     return $$3.choose($$2, $$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public double cycle(double $$0, double $$1, double $$2, Direction.Axis $$3) {
/* 36 */     return $$3.choose($$2, $$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public Direction.Axis cycle(Direction.Axis $$0) {
/* 41 */     return AXIS_VALUES[Math.floorMod($$0.ordinal() + 1, 3)];
/*    */   }
/*    */ 
/*    */   
/*    */   public AxisCycle inverse() {
/* 46 */     return BACKWARD;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\AxisCycle$2.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */