/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.entity.item.FallingBlockEntity;
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
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ import net.minecraft.world.level.material.Fluids;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.Shapes;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class ScaffoldingBlock extends Block implements SimpleWaterloggedBlock {
/*  26 */   public static final MapCodec<ScaffoldingBlock> CODEC = simpleCodec(ScaffoldingBlock::new); private static final int TICK_DELAY = 1; private static final VoxelShape STABLE_SHAPE;
/*     */   private static final VoxelShape UNSTABLE_SHAPE;
/*     */   
/*     */   public MapCodec<ScaffoldingBlock> codec() {
/*  30 */     return CODEC;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  37 */   private static final VoxelShape UNSTABLE_SHAPE_BOTTOM = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D);
/*  38 */   private static final VoxelShape BELOW_BLOCK = Shapes.block().move(0.0D, -1.0D, 0.0D);
/*     */   
/*     */   public static final int STABILITY_MAX_DISTANCE = 7;
/*  41 */   public static final IntegerProperty DISTANCE = BlockStateProperties.STABILITY_DISTANCE;
/*  42 */   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
/*  43 */   public static final BooleanProperty BOTTOM = BlockStateProperties.BOTTOM;
/*     */   
/*     */   static {
/*  46 */     VoxelShape $$0 = Block.box(0.0D, 14.0D, 0.0D, 16.0D, 16.0D, 16.0D);
/*  47 */     VoxelShape $$1 = Block.box(0.0D, 0.0D, 0.0D, 2.0D, 16.0D, 2.0D);
/*  48 */     VoxelShape $$2 = Block.box(14.0D, 0.0D, 0.0D, 16.0D, 16.0D, 2.0D);
/*  49 */     VoxelShape $$3 = Block.box(0.0D, 0.0D, 14.0D, 2.0D, 16.0D, 16.0D);
/*  50 */     VoxelShape $$4 = Block.box(14.0D, 0.0D, 14.0D, 16.0D, 16.0D, 16.0D);
/*  51 */     STABLE_SHAPE = Shapes.or($$0, new VoxelShape[] { $$1, $$2, $$3, $$4 });
/*     */     
/*  53 */     VoxelShape $$5 = Block.box(0.0D, 0.0D, 0.0D, 2.0D, 2.0D, 16.0D);
/*  54 */     VoxelShape $$6 = Block.box(14.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D);
/*  55 */     VoxelShape $$7 = Block.box(0.0D, 0.0D, 14.0D, 16.0D, 2.0D, 16.0D);
/*  56 */     VoxelShape $$8 = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 2.0D);
/*  57 */     UNSTABLE_SHAPE = Shapes.or(UNSTABLE_SHAPE_BOTTOM, new VoxelShape[] { STABLE_SHAPE, $$6, $$5, $$8, $$7 });
/*     */   }
/*     */   
/*     */   protected ScaffoldingBlock(BlockBehaviour.Properties $$0) {
/*  61 */     super($$0);
/*  62 */     registerDefaultState((BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)DISTANCE, Integer.valueOf(7))).setValue((Property)WATERLOGGED, Boolean.valueOf(false))).setValue((Property)BOTTOM, Boolean.valueOf(false)));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/*  67 */     $$0.add(new Property[] { (Property)DISTANCE, (Property)WATERLOGGED, (Property)BOTTOM });
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/*  72 */     if (!$$3.isHoldingItem($$0.getBlock().asItem())) {
/*  73 */       return ((Boolean)$$0.getValue((Property)BOTTOM)).booleanValue() ? UNSTABLE_SHAPE : STABLE_SHAPE;
/*     */     }
/*  75 */     return Shapes.block();
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getInteractionShape(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/*  80 */     return Shapes.block();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canBeReplaced(BlockState $$0, BlockPlaceContext $$1) {
/*  85 */     return $$1.getItemInHand().is(asItem());
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/*  90 */     BlockPos $$1 = $$0.getClickedPos();
/*  91 */     Level $$2 = $$0.getLevel();
/*     */     
/*  93 */     int $$3 = getDistance((BlockGetter)$$2, $$1);
/*  94 */     return (BlockState)((BlockState)((BlockState)defaultBlockState()
/*  95 */       .setValue((Property)WATERLOGGED, Boolean.valueOf(($$2.getFluidState($$1).getType() == Fluids.WATER))))
/*  96 */       .setValue((Property)DISTANCE, Integer.valueOf($$3)))
/*  97 */       .setValue((Property)BOTTOM, Boolean.valueOf(isBottom((BlockGetter)$$2, $$1, $$3)));
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPlace(BlockState $$0, Level $$1, BlockPos $$2, BlockState $$3, boolean $$4) {
/* 102 */     if (!$$1.isClientSide) {
/* 103 */       $$1.scheduleTick($$2, this, 1);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/* 109 */     if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/* 110 */       $$3.scheduleTick($$4, (Fluid)Fluids.WATER, Fluids.WATER.getTickDelay((LevelReader)$$3));
/*     */     }
/*     */     
/* 113 */     if (!$$3.isClientSide()) {
/* 114 */       $$3.scheduleTick($$4, this, 1);
/*     */     }
/*     */     
/* 117 */     return $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/* 122 */     int $$4 = getDistance((BlockGetter)$$1, $$2);
/*     */ 
/*     */     
/* 125 */     BlockState $$5 = (BlockState)((BlockState)$$0.setValue((Property)DISTANCE, Integer.valueOf($$4))).setValue((Property)BOTTOM, Boolean.valueOf(isBottom((BlockGetter)$$1, $$2, $$4)));
/*     */     
/* 127 */     if (((Integer)$$5.getValue((Property)DISTANCE)).intValue() == 7) {
/* 128 */       if (((Integer)$$0.getValue((Property)DISTANCE)).intValue() == 7) {
/*     */         
/* 130 */         FallingBlockEntity.fall((Level)$$1, $$2, $$5);
/*     */       } else {
/*     */         
/* 133 */         $$1.destroyBlock($$2, true);
/*     */       } 
/* 135 */     } else if ($$0 != $$5) {
/* 136 */       $$1.setBlock($$2, $$5, 3);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canSurvive(BlockState $$0, LevelReader $$1, BlockPos $$2) {
/* 142 */     return (getDistance((BlockGetter)$$1, $$2) < 7);
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getCollisionShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 147 */     if (!$$3.isAbove(Shapes.block(), $$2, true) || $$3.isDescending()) {
/* 148 */       if (((Integer)$$0.getValue((Property)DISTANCE)).intValue() != 0 && ((Boolean)$$0.getValue((Property)BOTTOM)).booleanValue() && $$3.isAbove(BELOW_BLOCK, $$2, true)) {
/* 149 */         return UNSTABLE_SHAPE_BOTTOM;
/*     */       }
/* 151 */       return Shapes.empty();
/*     */     } 
/* 153 */     return STABLE_SHAPE;
/*     */   }
/*     */ 
/*     */   
/*     */   public FluidState getFluidState(BlockState $$0) {
/* 158 */     if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/* 159 */       return Fluids.WATER.getSource(false);
/*     */     }
/* 161 */     return super.getFluidState($$0);
/*     */   }
/*     */   
/*     */   private boolean isBottom(BlockGetter $$0, BlockPos $$1, int $$2) {
/* 165 */     return ($$2 > 0 && !$$0.getBlockState($$1.below()).is(this));
/*     */   }
/*     */   
/*     */   public static int getDistance(BlockGetter $$0, BlockPos $$1) {
/* 169 */     BlockPos.MutableBlockPos $$2 = $$1.mutable().move(Direction.DOWN);
/* 170 */     BlockState $$3 = $$0.getBlockState((BlockPos)$$2);
/*     */     
/* 172 */     int $$4 = 7;
/* 173 */     if ($$3.is(Blocks.SCAFFOLDING)) {
/* 174 */       $$4 = ((Integer)$$3.getValue((Property)DISTANCE)).intValue();
/*     */     }
/* 176 */     else if ($$3.isFaceSturdy($$0, (BlockPos)$$2, Direction.UP)) {
/* 177 */       return 0;
/*     */     } 
/*     */     
/* 180 */     for (Direction $$5 : Direction.Plane.HORIZONTAL) {
/* 181 */       BlockState $$6 = $$0.getBlockState((BlockPos)$$2.setWithOffset((Vec3i)$$1, $$5));
/* 182 */       if (!$$6.is(Blocks.SCAFFOLDING)) {
/*     */         continue;
/*     */       }
/*     */       
/* 186 */       $$4 = Math.min($$4, ((Integer)$$6.getValue((Property)DISTANCE)).intValue() + 1);
/*     */       
/* 188 */       if ($$4 == 1) {
/*     */         break;
/*     */       }
/*     */     } 
/* 192 */     return $$4;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\ScaffoldingBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */