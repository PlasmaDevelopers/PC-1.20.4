/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ import net.minecraft.world.level.pathfinder.PathComputationType;
/*    */ import net.minecraft.world.phys.shapes.CollisionContext;
/*    */ import net.minecraft.world.phys.shapes.VoxelShape;
/*    */ 
/*    */ public abstract class RodBlock extends DirectionalBlock {
/*    */   protected static final float AABB_MIN = 6.0F;
/* 15 */   protected static final VoxelShape Y_AXIS_AABB = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 16.0D, 10.0D); protected static final float AABB_MAX = 10.0F;
/* 16 */   protected static final VoxelShape Z_AXIS_AABB = Block.box(6.0D, 6.0D, 0.0D, 10.0D, 10.0D, 16.0D);
/* 17 */   protected static final VoxelShape X_AXIS_AABB = Block.box(0.0D, 6.0D, 6.0D, 16.0D, 10.0D, 10.0D);
/*    */   
/*    */   protected RodBlock(BlockBehaviour.Properties $$0) {
/* 20 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   protected abstract MapCodec<? extends RodBlock> codec();
/*    */ 
/*    */   
/*    */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 28 */     switch (((Direction)$$0.getValue((Property)FACING)).getAxis())
/*    */     
/*    */     { default:
/* 31 */         return X_AXIS_AABB;
/*    */       case Z:
/* 33 */         return Z_AXIS_AABB;
/*    */       case Y:
/* 35 */         break; }  return Y_AXIS_AABB;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public BlockState rotate(BlockState $$0, Rotation $$1) {
/* 41 */     return (BlockState)$$0.setValue((Property)FACING, (Comparable)$$1.rotate((Direction)$$0.getValue((Property)FACING)));
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockState mirror(BlockState $$0, Mirror $$1) {
/* 46 */     return (BlockState)$$0.setValue((Property)FACING, (Comparable)$$1.mirror((Direction)$$0.getValue((Property)FACING)));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isPathfindable(BlockState $$0, BlockGetter $$1, BlockPos $$2, PathComputationType $$3) {
/* 52 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\RodBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */