/*    */ package net.minecraft.world.phys.shapes;
/*    */ 
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.util.Mth;
/*    */ 
/*    */ public final class SubShape extends DiscreteVoxelShape {
/*    */   private final DiscreteVoxelShape parent;
/*    */   private final int startX;
/*    */   private final int startY;
/*    */   private final int startZ;
/*    */   private final int endX;
/*    */   private final int endY;
/*    */   private final int endZ;
/*    */   
/*    */   protected SubShape(DiscreteVoxelShape $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6) {
/* 16 */     super($$4 - $$1, $$5 - $$2, $$6 - $$3);
/* 17 */     this.parent = $$0;
/* 18 */     this.startX = $$1;
/* 19 */     this.startY = $$2;
/* 20 */     this.startZ = $$3;
/* 21 */     this.endX = $$4;
/* 22 */     this.endY = $$5;
/* 23 */     this.endZ = $$6;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isFull(int $$0, int $$1, int $$2) {
/* 28 */     return this.parent.isFull(this.startX + $$0, this.startY + $$1, this.startZ + $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   public void fill(int $$0, int $$1, int $$2) {
/* 33 */     this.parent.fill(this.startX + $$0, this.startY + $$1, this.startZ + $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   public int firstFull(Direction.Axis $$0) {
/* 38 */     return clampToShape($$0, this.parent.firstFull($$0));
/*    */   }
/*    */ 
/*    */   
/*    */   public int lastFull(Direction.Axis $$0) {
/* 43 */     return clampToShape($$0, this.parent.lastFull($$0));
/*    */   }
/*    */   
/*    */   private int clampToShape(Direction.Axis $$0, int $$1) {
/* 47 */     int $$2 = $$0.choose(this.startX, this.startY, this.startZ);
/* 48 */     int $$3 = $$0.choose(this.endX, this.endY, this.endZ);
/* 49 */     return Mth.clamp($$1, $$2, $$3) - $$2;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\phys\shapes\SubShape.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */