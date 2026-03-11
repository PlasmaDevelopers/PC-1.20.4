/*    */ package net.minecraft.world.phys.shapes;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.doubles.DoubleList;
/*    */ import net.minecraft.core.Direction;
/*    */ 
/*    */ public class SliceShape extends VoxelShape {
/*    */   private final VoxelShape delegate;
/*    */   private final Direction.Axis axis;
/*  9 */   private static final DoubleList SLICE_COORDS = (DoubleList)new CubePointRange(1);
/*    */   
/*    */   public SliceShape(VoxelShape $$0, Direction.Axis $$1, int $$2) {
/* 12 */     super(makeSlice($$0.shape, $$1, $$2));
/* 13 */     this.delegate = $$0;
/* 14 */     this.axis = $$1;
/*    */   }
/*    */   
/*    */   private static DiscreteVoxelShape makeSlice(DiscreteVoxelShape $$0, Direction.Axis $$1, int $$2) {
/* 18 */     return new SubShape($$0, $$1
/* 19 */         .choose($$2, 0, 0), $$1
/* 20 */         .choose(0, $$2, 0), $$1
/* 21 */         .choose(0, 0, $$2), $$1
/* 22 */         .choose($$2 + 1, $$0.xSize, $$0.xSize), $$1
/* 23 */         .choose($$0.ySize, $$2 + 1, $$0.ySize), $$1
/* 24 */         .choose($$0.zSize, $$0.zSize, $$2 + 1));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected DoubleList getCoords(Direction.Axis $$0) {
/* 30 */     if ($$0 == this.axis) {
/* 31 */       return SLICE_COORDS;
/*    */     }
/* 33 */     return this.delegate.getCoords($$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\phys\shapes\SliceShape.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */