/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.context.BlockPlaceContext;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
/*     */ import net.minecraft.world.level.block.state.properties.EnumProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ 
/*     */ public class DoublePlantBlock extends BushBlock {
/*  25 */   public static final MapCodec<DoublePlantBlock> CODEC = simpleCodec(DoublePlantBlock::new);
/*     */ 
/*     */   
/*     */   public MapCodec<? extends DoublePlantBlock> codec() {
/*  29 */     return CODEC;
/*     */   }
/*     */   
/*  32 */   public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
/*     */   
/*     */   public DoublePlantBlock(BlockBehaviour.Properties $$0) {
/*  35 */     super($$0);
/*     */     
/*  37 */     registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)HALF, (Comparable)DoubleBlockHalf.LOWER));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/*  42 */     DoubleBlockHalf $$6 = (DoubleBlockHalf)$$0.getValue((Property)HALF);
/*  43 */     if ($$1.getAxis() == Direction.Axis.Y) if ((($$6 == DoubleBlockHalf.LOWER) ? true : false) == (($$1 == Direction.UP) ? true : false) && (
/*  44 */         !$$2.is(this) || $$2.getValue((Property)HALF) == $$6)) {
/*  45 */         return Blocks.AIR.defaultBlockState();
/*     */       }
/*     */ 
/*     */     
/*  49 */     if ($$6 == DoubleBlockHalf.LOWER && $$1 == Direction.DOWN && !$$0.canSurvive((LevelReader)$$3, $$4)) {
/*  50 */       return Blocks.AIR.defaultBlockState();
/*     */     }
/*     */     
/*  53 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/*  59 */     BlockPos $$1 = $$0.getClickedPos();
/*  60 */     Level $$2 = $$0.getLevel();
/*  61 */     if ($$1.getY() < $$2.getMaxBuildHeight() - 1 && $$2.getBlockState($$1.above()).canBeReplaced($$0)) {
/*  62 */       return super.getStateForPlacement($$0);
/*     */     }
/*     */     
/*  65 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPlacedBy(Level $$0, BlockPos $$1, BlockState $$2, LivingEntity $$3, ItemStack $$4) {
/*  70 */     BlockPos $$5 = $$1.above();
/*  71 */     $$0.setBlock($$5, copyWaterloggedFrom((LevelReader)$$0, $$5, (BlockState)defaultBlockState().setValue((Property)HALF, (Comparable)DoubleBlockHalf.UPPER)), 3);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canSurvive(BlockState $$0, LevelReader $$1, BlockPos $$2) {
/*  77 */     if ($$0.getValue((Property)HALF) == DoubleBlockHalf.UPPER) {
/*  78 */       BlockState $$3 = $$1.getBlockState($$2.below());
/*  79 */       return ($$3.is(this) && $$3.getValue((Property)HALF) == DoubleBlockHalf.LOWER);
/*     */     } 
/*     */     
/*  82 */     return super.canSurvive($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   public static void placeAt(LevelAccessor $$0, BlockState $$1, BlockPos $$2, int $$3) {
/*  86 */     BlockPos $$4 = $$2.above();
/*     */     
/*  88 */     $$0.setBlock($$2, copyWaterloggedFrom((LevelReader)$$0, $$2, (BlockState)$$1.setValue((Property)HALF, (Comparable)DoubleBlockHalf.LOWER)), $$3);
/*  89 */     $$0.setBlock($$4, copyWaterloggedFrom((LevelReader)$$0, $$4, (BlockState)$$1.setValue((Property)HALF, (Comparable)DoubleBlockHalf.UPPER)), $$3);
/*     */   }
/*     */   
/*     */   public static BlockState copyWaterloggedFrom(LevelReader $$0, BlockPos $$1, BlockState $$2) {
/*  93 */     if ($$2.hasProperty((Property)BlockStateProperties.WATERLOGGED)) {
/*  94 */       return (BlockState)$$2.setValue((Property)BlockStateProperties.WATERLOGGED, Boolean.valueOf($$0.isWaterAt($$1)));
/*     */     }
/*  96 */     return $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState playerWillDestroy(Level $$0, BlockPos $$1, BlockState $$2, Player $$3) {
/* 101 */     if (!$$0.isClientSide) {
/* 102 */       if ($$3.isCreative()) {
/* 103 */         preventDropFromBottomPart($$0, $$1, $$2, $$3);
/*     */       } else {
/*     */         
/* 106 */         dropResources($$2, $$0, $$1, (BlockEntity)null, (Entity)$$3, $$3.getMainHandItem());
/*     */       } 
/*     */     }
/*     */     
/* 110 */     return super.playerWillDestroy($$0, $$1, $$2, $$3);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void playerDestroy(Level $$0, Player $$1, BlockPos $$2, BlockState $$3, @Nullable BlockEntity $$4, ItemStack $$5) {
/* 116 */     super.playerDestroy($$0, $$1, $$2, Blocks.AIR.defaultBlockState(), $$4, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   protected static void preventDropFromBottomPart(Level $$0, BlockPos $$1, BlockState $$2, Player $$3) {
/* 121 */     DoubleBlockHalf $$4 = (DoubleBlockHalf)$$2.getValue((Property)HALF);
/* 122 */     if ($$4 == DoubleBlockHalf.UPPER) {
/* 123 */       BlockPos $$5 = $$1.below();
/* 124 */       BlockState $$6 = $$0.getBlockState($$5);
/* 125 */       if ($$6.is($$2.getBlock()) && $$6.getValue((Property)HALF) == DoubleBlockHalf.LOWER) {
/*     */         
/* 127 */         BlockState $$7 = $$6.getFluidState().is((Fluid)Fluids.WATER) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();
/* 128 */         $$0.setBlock($$5, $$7, 35);
/* 129 */         $$0.levelEvent($$3, 2001, $$5, Block.getId($$6));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 136 */     $$0.add(new Property[] { (Property)HALF });
/*     */   }
/*     */ 
/*     */   
/*     */   public long getSeed(BlockState $$0, BlockPos $$1) {
/* 141 */     return Mth.getSeed($$1.getX(), $$1.below(($$0.getValue((Property)HALF) == DoubleBlockHalf.LOWER) ? 0 : 1).getY(), $$1.getZ());
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\DoublePlantBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */