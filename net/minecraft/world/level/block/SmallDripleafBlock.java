/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.item.context.BlockPlaceContext;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.BooleanProperty;
/*     */ import net.minecraft.world.level.block.state.properties.DirectionProperty;
/*     */ import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.material.Fluid;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ import net.minecraft.world.level.material.Fluids;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class SmallDripleafBlock extends DoublePlantBlock implements BonemealableBlock, SimpleWaterloggedBlock {
/*  30 */   public static final MapCodec<SmallDripleafBlock> CODEC = simpleCodec(SmallDripleafBlock::new);
/*     */ 
/*     */   
/*     */   public MapCodec<SmallDripleafBlock> codec() {
/*  34 */     return CODEC;
/*     */   }
/*     */   
/*  37 */   private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
/*  38 */   public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
/*     */   
/*     */   protected static final float AABB_OFFSET = 6.0F;
/*  41 */   protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D);
/*     */   
/*     */   public SmallDripleafBlock(BlockBehaviour.Properties $$0) {
/*  44 */     super($$0);
/*     */     
/*  46 */     registerDefaultState((BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)HALF, (Comparable)DoubleBlockHalf.LOWER)).setValue((Property)WATERLOGGED, Boolean.valueOf(false))).setValue((Property)FACING, (Comparable)Direction.NORTH));
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/*  51 */     return SHAPE;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean mayPlaceOn(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/*  56 */     return ($$0.is(BlockTags.SMALL_DRIPLEAF_PLACEABLE) || ($$1.getFluidState($$2.above()).isSourceOfType((Fluid)Fluids.WATER) && super.mayPlaceOn($$0, $$1, $$2)));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/*  62 */     BlockState $$1 = super.getStateForPlacement($$0);
/*  63 */     if ($$1 != null) {
/*  64 */       return copyWaterloggedFrom((LevelReader)$$0.getLevel(), $$0.getClickedPos(), (BlockState)$$1.setValue((Property)FACING, (Comparable)$$0.getHorizontalDirection().getOpposite()));
/*     */     }
/*  66 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPlacedBy(Level $$0, BlockPos $$1, BlockState $$2, LivingEntity $$3, ItemStack $$4) {
/*  71 */     if (!$$0.isClientSide()) {
/*  72 */       BlockPos $$5 = $$1.above();
/*  73 */       BlockState $$6 = DoublePlantBlock.copyWaterloggedFrom((LevelReader)$$0, $$5, (BlockState)((BlockState)defaultBlockState().setValue((Property)HALF, (Comparable)DoubleBlockHalf.UPPER)).setValue((Property)FACING, $$2.getValue((Property)FACING)));
/*  74 */       $$0.setBlock($$5, $$6, 3);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public FluidState getFluidState(BlockState $$0) {
/*  80 */     if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/*  81 */       return Fluids.WATER.getSource(false);
/*     */     }
/*  83 */     return super.getFluidState($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canSurvive(BlockState $$0, LevelReader $$1, BlockPos $$2) {
/*  88 */     if ($$0.getValue((Property)HALF) == DoubleBlockHalf.UPPER) {
/*  89 */       return super.canSurvive($$0, $$1, $$2);
/*     */     }
/*     */     
/*  92 */     BlockPos $$3 = $$2.below();
/*  93 */     BlockState $$4 = $$1.getBlockState($$3);
/*  94 */     return mayPlaceOn($$4, (BlockGetter)$$1, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/*  99 */     if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/* 100 */       $$3.scheduleTick($$4, (Fluid)Fluids.WATER, Fluids.WATER.getTickDelay((LevelReader)$$3));
/*     */     }
/* 102 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 107 */     $$0.add(new Property[] { (Property)HALF, (Property)WATERLOGGED, (Property)FACING });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isValidBonemealTarget(LevelReader $$0, BlockPos $$1, BlockState $$2) {
/* 112 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBonemealSuccess(Level $$0, RandomSource $$1, BlockPos $$2, BlockState $$3) {
/* 117 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void performBonemeal(ServerLevel $$0, RandomSource $$1, BlockPos $$2, BlockState $$3) {
/* 122 */     if ($$3.getValue((Property)DoublePlantBlock.HALF) == DoubleBlockHalf.LOWER) {
/*     */       
/* 124 */       BlockPos $$4 = $$2.above();
/* 125 */       $$0.setBlock($$4, $$0.getFluidState($$4).createLegacyBlock(), 18);
/* 126 */       BigDripleafBlock.placeWithRandomHeight((LevelAccessor)$$0, $$1, $$2, (Direction)$$3.getValue((Property)FACING));
/*     */     } else {
/* 128 */       BlockPos $$5 = $$2.below();
/* 129 */       performBonemeal($$0, $$1, $$5, $$0.getBlockState($$5));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState rotate(BlockState $$0, Rotation $$1) {
/* 135 */     return (BlockState)$$0.setValue((Property)FACING, (Comparable)$$1.rotate((Direction)$$0.getValue((Property)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState mirror(BlockState $$0, Mirror $$1) {
/* 140 */     return $$0.rotate($$1.getRotation((Direction)$$0.getValue((Property)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   public float getMaxVerticalOffset() {
/* 145 */     return 0.1F;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\SmallDripleafBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */