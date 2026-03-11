/*    */ package net.minecraft.core;
/*    */ 
/*    */ public enum AxisCycle {
/*  4 */   NONE
/*    */   {
/*    */     public int cycle(int $$0, int $$1, int $$2, Direction.Axis $$3) {
/*  7 */       return $$3.choose($$0, $$1, $$2);
/*    */     }
/*    */ 
/*    */     
/*    */     public double cycle(double $$0, double $$1, double $$2, Direction.Axis $$3) {
/* 12 */       return $$3.choose($$0, $$1, $$2);
/*    */     }
/*    */ 
/*    */     
/*    */     public Direction.Axis cycle(Direction.Axis $$0) {
/* 17 */       return $$0;
/*    */     }
/*    */ 
/*    */     
/*    */     public AxisCycle inverse() {
/* 22 */       return this;
/*    */     }
/*    */   },
/*    */ 
/*    */ 
/*    */   
/* 28 */   FORWARD
/*    */   {
/*    */     public int cycle(int $$0, int $$1, int $$2, Direction.Axis $$3) {
/* 31 */       return $$3.choose($$2, $$0, $$1);
/*    */     }
/*    */ 
/*    */     
/*    */     public double cycle(double $$0, double $$1, double $$2, Direction.Axis $$3) {
/* 36 */       return $$3.choose($$2, $$0, $$1);
/*    */     }
/*    */ 
/*    */     
/*    */     public Direction.Axis cycle(Direction.Axis $$0) {
/* 41 */       return AXIS_VALUES[Math.floorMod($$0.ordinal() + 1, 3)];
/*    */     }
/*    */ 
/*    */     
/*    */     public AxisCycle inverse() {
/* 46 */       return BACKWARD;
/*    */     }
/*    */   },
/* 49 */   BACKWARD
/*    */   {
/*    */     public int cycle(int $$0, int $$1, int $$2, Direction.Axis $$3) {
/* 52 */       return $$3.choose($$1, $$2, $$0);
/*    */     }
/*    */ 
/*    */     
/*    */     public double cycle(double $$0, double $$1, double $$2, Direction.Axis $$3) {
/* 57 */       return $$3.choose($$1, $$2, $$0);
/*    */     }
/*    */ 
/*    */     
/*    */     public Direction.Axis cycle(Direction.Axis $$0) {
/* 62 */       return AXIS_VALUES[Math.floorMod($$0.ordinal() - 1, 3)];
/*    */     }
/*    */ 
/*    */     
/*    */     public AxisCycle inverse() {
/* 67 */       return FORWARD;
/*    */     }
/*    */   };
/*    */   
/*    */   static {
/* 72 */     AXIS_VALUES = Direction.Axis.values();
/* 73 */     VALUES = values();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static final Direction.Axis[] AXIS_VALUES;
/*    */ 
/*    */ 
/*    */   
/*    */   public static final AxisCycle[] VALUES;
/*    */ 
/*    */ 
/*    */   
/*    */   public static AxisCycle between(Direction.Axis $$0, Direction.Axis $$1) {
/* 88 */     return VALUES[Math.floorMod($$1.ordinal() - $$0.ordinal(), 3)];
/*    */   }
/*    */   
/*    */   public abstract int cycle(int paramInt1, int paramInt2, int paramInt3, Direction.Axis paramAxis);
/*    */   
/*    */   public abstract double cycle(double paramDouble1, double paramDouble2, double paramDouble3, Direction.Axis paramAxis);
/*    */   
/*    */   public abstract Direction.Axis cycle(Direction.Axis paramAxis);
/*    */   
/*    */   public abstract AxisCycle inverse();
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\AxisCycle.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */