/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import java.util.Optional;
/*     */ import net.minecraft.BlockUtil;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.BooleanProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.material.Fluid;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ import net.minecraft.world.level.material.Fluids;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class BigDripleafStemBlock extends HorizontalDirectionalBlock implements BonemealableBlock, SimpleWaterloggedBlock {
/*  27 */   public static final MapCodec<BigDripleafStemBlock> CODEC = simpleCodec(BigDripleafStemBlock::new);
/*     */ 
/*     */   
/*     */   public MapCodec<BigDripleafStemBlock> codec() {
/*  31 */     return CODEC;
/*     */   }
/*     */   
/*  34 */   private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
/*     */   private static final int STEM_WIDTH = 6;
/*  36 */   protected static final VoxelShape NORTH_SHAPE = Block.box(5.0D, 0.0D, 9.0D, 11.0D, 16.0D, 15.0D);
/*  37 */   protected static final VoxelShape SOUTH_SHAPE = Block.box(5.0D, 0.0D, 1.0D, 11.0D, 16.0D, 7.0D);
/*  38 */   protected static final VoxelShape EAST_SHAPE = Block.box(1.0D, 0.0D, 5.0D, 7.0D, 16.0D, 11.0D);
/*  39 */   protected static final VoxelShape WEST_SHAPE = Block.box(9.0D, 0.0D, 5.0D, 15.0D, 16.0D, 11.0D);
/*     */   
/*     */   protected BigDripleafStemBlock(BlockBehaviour.Properties $$0) {
/*  42 */     super($$0);
/*  43 */     registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)WATERLOGGED, Boolean.valueOf(false))).setValue((Property)FACING, (Comparable)Direction.NORTH));
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/*  48 */     switch ((Direction)$$0.getValue((Property)FACING))
/*     */     { case SOUTH:
/*  50 */         return SOUTH_SHAPE;
/*     */       
/*     */       default:
/*  53 */         return NORTH_SHAPE;
/*     */       case WEST:
/*  55 */         return WEST_SHAPE;
/*     */       case EAST:
/*  57 */         break; }  return EAST_SHAPE;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/*  63 */     $$0.add(new Property[] { (Property)WATERLOGGED, (Property)FACING });
/*     */   }
/*     */ 
/*     */   
/*     */   public FluidState getFluidState(BlockState $$0) {
/*  68 */     if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/*  69 */       return Fluids.WATER.getSource(false);
/*     */     }
/*     */     
/*  72 */     return super.getFluidState($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canSurvive(BlockState $$0, LevelReader $$1, BlockPos $$2) {
/*  77 */     BlockPos $$3 = $$2.below();
/*  78 */     BlockState $$4 = $$1.getBlockState($$3);
/*  79 */     BlockState $$5 = $$1.getBlockState($$2.above());
/*  80 */     return (($$4.is(this) || $$4.is(BlockTags.BIG_DRIPLEAF_PLACEABLE)) && ($$5
/*  81 */       .is(this) || $$5.is(Blocks.BIG_DRIPLEAF)));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static boolean place(LevelAccessor $$0, BlockPos $$1, FluidState $$2, Direction $$3) {
/*  87 */     BlockState $$4 = (BlockState)((BlockState)Blocks.BIG_DRIPLEAF_STEM.defaultBlockState().setValue((Property)WATERLOGGED, Boolean.valueOf($$2.isSourceOfType((Fluid)Fluids.WATER)))).setValue((Property)FACING, (Comparable)$$3);
/*  88 */     return $$0.setBlock($$1, $$4, 3);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/*  93 */     if (($$1 == Direction.DOWN || $$1 == Direction.UP) && !$$0.canSurvive((LevelReader)$$3, $$4)) {
/*  94 */       $$3.scheduleTick($$4, this, 1);
/*     */     }
/*  96 */     if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/*  97 */       $$3.scheduleTick($$4, (Fluid)Fluids.WATER, Fluids.WATER.getTickDelay((LevelReader)$$3));
/*     */     }
/*  99 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/* 104 */     if (!$$0.canSurvive((LevelReader)$$1, $$2)) {
/* 105 */       $$1.destroyBlock($$2, true);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isValidBonemealTarget(LevelReader $$0, BlockPos $$1, BlockState $$2) {
/* 111 */     Optional<BlockPos> $$3 = BlockUtil.getTopConnectedBlock((BlockGetter)$$0, $$1, $$2.getBlock(), Direction.UP, Blocks.BIG_DRIPLEAF);
/* 112 */     if ($$3.isEmpty()) {
/* 113 */       return false;
/*     */     }
/* 115 */     BlockPos $$4 = ((BlockPos)$$3.get()).above();
/* 116 */     BlockState $$5 = $$0.getBlockState($$4);
/* 117 */     return BigDripleafBlock.canPlaceAt((LevelHeightAccessor)$$0, $$4, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBonemealSuccess(Level $$0, RandomSource $$1, BlockPos $$2, BlockState $$3) {
/* 122 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void performBonemeal(ServerLevel $$0, RandomSource $$1, BlockPos $$2, BlockState $$3) {
/* 127 */     Optional<BlockPos> $$4 = BlockUtil.getTopConnectedBlock((BlockGetter)$$0, $$2, $$3.getBlock(), Direction.UP, Blocks.BIG_DRIPLEAF);
/* 128 */     if ($$4.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/* 132 */     BlockPos $$5 = $$4.get();
/* 133 */     BlockPos $$6 = $$5.above();
/* 134 */     Direction $$7 = (Direction)$$3.getValue((Property)FACING);
/*     */     
/* 136 */     place((LevelAccessor)$$0, $$5, $$0.getFluidState($$5), $$7);
/* 137 */     BigDripleafBlock.place((LevelAccessor)$$0, $$6, $$0.getFluidState($$6), $$7);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getCloneItemStack(LevelReader $$0, BlockPos $$1, BlockState $$2) {
/* 142 */     return new ItemStack(Blocks.BIG_DRIPLEAF);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\BigDripleafStemBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */