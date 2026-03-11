/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.function.Function;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.context.BlockPlaceContext;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.LevelAccessor;
/*    */ import net.minecraft.world.level.LevelReader;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.StateDefinition;
/*    */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ import net.minecraft.world.level.material.Fluid;
/*    */ import net.minecraft.world.level.material.Fluids;
/*    */ 
/*    */ public class BarrierBlock extends Block implements SimpleWaterloggedBlock {
/* 22 */   public static final MapCodec<BarrierBlock> CODEC = simpleCodec(BarrierBlock::new);
/*    */ 
/*    */   
/*    */   public MapCodec<BarrierBlock> codec() {
/* 26 */     return CODEC;
/*    */   }
/*    */   
/* 29 */   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
/*    */   
/*    */   protected BarrierBlock(BlockBehaviour.Properties $$0) {
/* 32 */     super($$0);
/* 33 */     registerDefaultState((BlockState)defaultBlockState().setValue((Property)WATERLOGGED, Boolean.valueOf(false)));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean propagatesSkylightDown(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/* 38 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public RenderShape getRenderShape(BlockState $$0) {
/* 43 */     return RenderShape.INVISIBLE;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getShadeBrightness(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/* 48 */     return 1.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/* 53 */     if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/* 54 */       $$3.scheduleTick($$4, (Fluid)Fluids.WATER, Fluids.WATER.getTickDelay((LevelReader)$$3));
/*    */     }
/* 56 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*    */   }
/*    */ 
/*    */   
/*    */   public FluidState getFluidState(BlockState $$0) {
/* 61 */     if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/* 62 */       return Fluids.WATER.getSource(false);
/*    */     }
/* 64 */     return super.getFluidState($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/* 70 */     return (BlockState)defaultBlockState().setValue((Property)WATERLOGGED, Boolean.valueOf(($$0.getLevel().getFluidState($$0.getClickedPos()).getType() == Fluids.WATER)));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 75 */     $$0.add(new Property[] { (Property)WATERLOGGED });
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack pickupBlock(@Nullable Player $$0, LevelAccessor $$1, BlockPos $$2, BlockState $$3) {
/* 80 */     if ($$0 == null || !$$0.isCreative()) {
/* 81 */       return ItemStack.EMPTY;
/*    */     }
/* 83 */     return super.pickupBlock($$0, $$1, $$2, $$3);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canPlaceLiquid(@Nullable Player $$0, BlockGetter $$1, BlockPos $$2, BlockState $$3, Fluid $$4) {
/* 88 */     if ($$0 == null || !$$0.isCreative()) {
/* 89 */       return false;
/*    */     }
/* 91 */     return super.canPlaceLiquid($$0, $$1, $$2, $$3, $$4);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\BarrierBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */