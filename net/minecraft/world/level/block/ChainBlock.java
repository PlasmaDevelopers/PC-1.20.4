/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.world.item.context.BlockPlaceContext;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.LevelAccessor;
/*    */ import net.minecraft.world.level.LevelReader;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.StateDefinition;
/*    */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ import net.minecraft.world.level.material.FluidState;
/*    */ import net.minecraft.world.level.material.Fluids;
/*    */ import net.minecraft.world.level.pathfinder.PathComputationType;
/*    */ import net.minecraft.world.phys.shapes.CollisionContext;
/*    */ import net.minecraft.world.phys.shapes.VoxelShape;
/*    */ 
/*    */ public class ChainBlock extends RotatedPillarBlock implements SimpleWaterloggedBlock {
/* 22 */   public static final MapCodec<ChainBlock> CODEC = simpleCodec(ChainBlock::new);
/*    */ 
/*    */   
/*    */   public MapCodec<ChainBlock> codec() {
/* 26 */     return CODEC;
/*    */   }
/*    */   
/* 29 */   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
/*    */   
/*    */   protected static final float AABB_MIN = 6.5F;
/*    */   
/*    */   protected static final float AABB_MAX = 9.5F;
/* 34 */   protected static final VoxelShape Y_AXIS_AABB = Block.box(6.5D, 0.0D, 6.5D, 9.5D, 16.0D, 9.5D);
/* 35 */   protected static final VoxelShape Z_AXIS_AABB = Block.box(6.5D, 6.5D, 0.0D, 9.5D, 9.5D, 16.0D);
/* 36 */   protected static final VoxelShape X_AXIS_AABB = Block.box(0.0D, 6.5D, 6.5D, 16.0D, 9.5D, 9.5D);
/*    */   
/*    */   public ChainBlock(BlockBehaviour.Properties $$0) {
/* 39 */     super($$0);
/* 40 */     registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)WATERLOGGED, Boolean.valueOf(false))).setValue((Property)AXIS, (Comparable)Direction.Axis.Y));
/*    */   }
/*    */ 
/*    */   
/*    */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 45 */     switch ((Direction.Axis)$$0.getValue((Property)AXIS))
/*    */     
/*    */     { default:
/* 48 */         return X_AXIS_AABB;
/*    */       case Z:
/* 50 */         return Z_AXIS_AABB;
/*    */       case Y:
/* 52 */         break; }  return Y_AXIS_AABB;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/* 59 */     FluidState $$1 = $$0.getLevel().getFluidState($$0.getClickedPos());
/* 60 */     boolean $$2 = ($$1.getType() == Fluids.WATER);
/* 61 */     return (BlockState)super.getStateForPlacement($$0).setValue((Property)WATERLOGGED, Boolean.valueOf($$2));
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/* 66 */     if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/* 67 */       $$3.scheduleTick($$4, (Fluid)Fluids.WATER, Fluids.WATER.getTickDelay((LevelReader)$$3));
/*    */     }
/* 69 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 74 */     $$0.add(new Property[] { (Property)WATERLOGGED }).add(new Property[] { (Property)AXIS });
/*    */   }
/*    */ 
/*    */   
/*    */   public FluidState getFluidState(BlockState $$0) {
/* 79 */     if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/* 80 */       return Fluids.WATER.getSource(false);
/*    */     }
/* 82 */     return super.getFluidState($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isPathfindable(BlockState $$0, BlockGetter $$1, BlockPos $$2, PathComputationType $$3) {
/* 87 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\ChainBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */