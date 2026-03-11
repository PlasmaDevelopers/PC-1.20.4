/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.function.Function;
/*    */ import javax.annotation.Nullable;
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
/*    */ import net.minecraft.world.phys.shapes.CollisionContext;
/*    */ import net.minecraft.world.phys.shapes.VoxelShape;
/*    */ 
/*    */ public class HangingRootsBlock extends Block implements SimpleWaterloggedBlock {
/* 22 */   public static final MapCodec<HangingRootsBlock> CODEC = simpleCodec(HangingRootsBlock::new);
/*    */ 
/*    */   
/*    */   public MapCodec<HangingRootsBlock> codec() {
/* 26 */     return CODEC;
/*    */   }
/*    */   
/* 29 */   private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
/* 30 */   protected static final VoxelShape SHAPE = Block.box(2.0D, 10.0D, 2.0D, 14.0D, 16.0D, 14.0D);
/*    */   
/*    */   protected HangingRootsBlock(BlockBehaviour.Properties $$0) {
/* 33 */     super($$0);
/* 34 */     registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)WATERLOGGED, Boolean.valueOf(false)));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 39 */     $$0.add(new Property[] { (Property)WATERLOGGED });
/*    */   }
/*    */ 
/*    */   
/*    */   public FluidState getFluidState(BlockState $$0) {
/* 44 */     if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/* 45 */       return Fluids.WATER.getSource(false);
/*    */     }
/* 47 */     return super.getFluidState($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/* 53 */     BlockState $$1 = super.getStateForPlacement($$0);
/* 54 */     if ($$1 != null) {
/* 55 */       FluidState $$2 = $$0.getLevel().getFluidState($$0.getClickedPos());
/* 56 */       return (BlockState)$$1.setValue((Property)WATERLOGGED, Boolean.valueOf(($$2.getType() == Fluids.WATER)));
/*    */     } 
/* 58 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canSurvive(BlockState $$0, LevelReader $$1, BlockPos $$2) {
/* 63 */     BlockPos $$3 = $$2.above();
/* 64 */     BlockState $$4 = $$1.getBlockState($$3);
/* 65 */     return $$4.isFaceSturdy((BlockGetter)$$1, $$3, Direction.DOWN);
/*    */   }
/*    */ 
/*    */   
/*    */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 70 */     return SHAPE;
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/* 75 */     if ($$1 == Direction.UP && !canSurvive($$0, (LevelReader)$$3, $$4)) {
/* 76 */       return Blocks.AIR.defaultBlockState();
/*    */     }
/* 78 */     if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/* 79 */       $$3.scheduleTick($$4, (Fluid)Fluids.WATER, Fluids.WATER.getTickDelay((LevelReader)$$3));
/*    */     }
/* 81 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\HangingRootsBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */