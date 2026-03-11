/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.function.Function;
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
/*    */ import net.minecraft.world.level.material.FluidState;
/*    */ import net.minecraft.world.level.material.Fluids;
/*    */ 
/*    */ public class MangroveRootsBlock extends Block implements SimpleWaterloggedBlock {
/* 18 */   public static final MapCodec<MangroveRootsBlock> CODEC = simpleCodec(MangroveRootsBlock::new);
/*    */ 
/*    */   
/*    */   public MapCodec<MangroveRootsBlock> codec() {
/* 22 */     return CODEC;
/*    */   }
/*    */   
/* 25 */   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
/*    */   
/*    */   protected MangroveRootsBlock(BlockBehaviour.Properties $$0) {
/* 28 */     super($$0);
/* 29 */     registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)WATERLOGGED, Boolean.valueOf(false)));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean skipRendering(BlockState $$0, BlockState $$1, Direction $$2) {
/* 35 */     return ($$1.is(Blocks.MANGROVE_ROOTS) && $$2.getAxis() == Direction.Axis.Y);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/* 41 */     FluidState $$1 = $$0.getLevel().getFluidState($$0.getClickedPos());
/* 42 */     boolean $$2 = ($$1.getType() == Fluids.WATER);
/* 43 */     return (BlockState)super.getStateForPlacement($$0).setValue((Property)WATERLOGGED, Boolean.valueOf($$2));
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/* 48 */     if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/* 49 */       $$3.scheduleTick($$4, (Fluid)Fluids.WATER, Fluids.WATER.getTickDelay((LevelReader)$$3));
/*    */     }
/*    */     
/* 52 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*    */   }
/*    */ 
/*    */   
/*    */   public FluidState getFluidState(BlockState $$0) {
/* 57 */     if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/* 58 */       return Fluids.WATER.getSource(false);
/*    */     }
/*    */     
/* 61 */     return super.getFluidState($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 66 */     $$0.add(new Property[] { (Property)WATERLOGGED });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\MangroveRootsBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */