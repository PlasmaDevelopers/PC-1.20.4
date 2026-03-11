/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.function.Function;
/*    */ import java.util.function.ToIntFunction;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.item.Items;
/*    */ import net.minecraft.world.item.context.BlockPlaceContext;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.Level;
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
/*    */ public class GlowLichenBlock extends MultifaceBlock implements BonemealableBlock, SimpleWaterloggedBlock {
/* 24 */   public static final MapCodec<GlowLichenBlock> CODEC = simpleCodec(GlowLichenBlock::new);
/*    */ 
/*    */   
/*    */   public MapCodec<GlowLichenBlock> codec() {
/* 28 */     return CODEC;
/*    */   }
/*    */   
/* 31 */   private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
/* 32 */   private final MultifaceSpreader spreader = new MultifaceSpreader(this);
/*    */   
/*    */   public GlowLichenBlock(BlockBehaviour.Properties $$0) {
/* 35 */     super($$0);
/* 36 */     registerDefaultState((BlockState)defaultBlockState().setValue((Property)WATERLOGGED, Boolean.valueOf(false)));
/*    */   }
/*    */   
/*    */   public static ToIntFunction<BlockState> emission(int $$0) {
/* 40 */     return $$1 -> MultifaceBlock.hasAnyFace($$1) ? $$0 : 0;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 45 */     super.createBlockStateDefinition($$0);
/* 46 */     $$0.add(new Property[] { (Property)WATERLOGGED });
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/* 51 */     if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/* 52 */       $$3.scheduleTick($$4, (Fluid)Fluids.WATER, Fluids.WATER.getTickDelay((LevelReader)$$3));
/*    */     }
/* 54 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canBeReplaced(BlockState $$0, BlockPlaceContext $$1) {
/* 59 */     return (!$$1.getItemInHand().is(Items.GLOW_LICHEN) || super.canBeReplaced($$0, $$1));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isValidBonemealTarget(LevelReader $$0, BlockPos $$1, BlockState $$2) {
/* 64 */     return Direction.stream().anyMatch($$3 -> this.spreader.canSpreadInAnyDirection($$0, (BlockGetter)$$1, $$2, $$3.getOpposite()));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isBonemealSuccess(Level $$0, RandomSource $$1, BlockPos $$2, BlockState $$3) {
/* 69 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void performBonemeal(ServerLevel $$0, RandomSource $$1, BlockPos $$2, BlockState $$3) {
/* 74 */     this.spreader.spreadFromRandomFaceTowardRandomDirection($$3, (LevelAccessor)$$0, $$2, $$1);
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
/*    */   public boolean propagatesSkylightDown(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/* 87 */     return $$0.getFluidState().isEmpty();
/*    */   }
/*    */ 
/*    */   
/*    */   public MultifaceSpreader getSpreader() {
/* 92 */     return this.spreader;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\GlowLichenBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */