/*    */ package net.minecraft.world.phys.shapes;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.doubles.DoubleList;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.util.Mth;
/*    */ 
/*    */ public final class CubeVoxelShape extends VoxelShape {
/*    */   protected CubeVoxelShape(DiscreteVoxelShape $$0) {
/*  9 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   protected DoubleList getCoords(Direction.Axis $$0) {
/* 14 */     return (DoubleList)new CubePointRange(this.shape.getSize($$0));
/*    */   }
/*    */ 
/*    */   
/*    */   protected int findIndex(Direction.Axis $$0, double $$1) {
/* 19 */     int $$2 = this.shape.getSize($$0);
/* 20 */     return Mth.floor(Mth.clamp($$1 * $$2, -1.0D, $$2));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\phys\shapes\CubeVoxelShape.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */