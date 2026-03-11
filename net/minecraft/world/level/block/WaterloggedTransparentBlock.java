/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.world.item.context.BlockPlaceContext;
/*    */ import net.minecraft.world.level.LevelAccessor;
/*    */ import net.minecraft.world.level.LevelReader;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.StateDefinition;
/*    */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ import net.minecraft.world.level.material.Fluid;
/*    */ import net.minecraft.world.level.material.FluidState;
/*    */ import net.minecraft.world.level.material.Fluids;
/*    */ 
/*    */ public class WaterloggedTransparentBlock extends TransparentBlock implements SimpleWaterloggedBlock {
/* 18 */   public static final MapCodec<WaterloggedTransparentBlock> CODEC = simpleCodec(WaterloggedTransparentBlock::new);
/*    */ 
/*    */   
/*    */   protected MapCodec<? extends WaterloggedTransparentBlock> codec() {
/* 22 */     return CODEC;
/*    */   }
/*    */   
/* 25 */   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
/*    */   
/*    */   protected WaterloggedTransparentBlock(BlockBehaviour.Properties $$0) {
/* 28 */     super($$0);
/* 29 */     registerDefaultState((BlockState)defaultBlockState().setValue((Property)WATERLOGGED, Boolean.valueOf(false)));
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/* 35 */     FluidState $$1 = $$0.getLevel().getFluidState($$0.getClickedPos());
/* 36 */     return (BlockState)super.getStateForPlacement($$0).setValue((Property)WATERLOGGED, Boolean.valueOf($$1.is((Fluid)Fluids.WATER)));
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/* 41 */     if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/* 42 */       $$3.scheduleTick($$4, (Fluid)Fluids.WATER, Fluids.WATER.getTickDelay((LevelReader)$$3));
/*    */     }
/*    */     
/* 45 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*    */   }
/*    */ 
/*    */   
/*    */   public FluidState getFluidState(BlockState $$0) {
/* 50 */     if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/* 51 */       return Fluids.WATER.getSource(true);
/*    */     }
/*    */     
/* 54 */     return super.getFluidState($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 59 */     $$0.add(new Property[] { (Property)WATERLOGGED });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\WaterloggedTransparentBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */