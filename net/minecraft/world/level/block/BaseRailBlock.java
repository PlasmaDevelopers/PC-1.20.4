/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.world.item.context.BlockPlaceContext;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.BooleanProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.block.state.properties.RailShape;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ import net.minecraft.world.level.material.Fluids;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public abstract class BaseRailBlock extends Block implements SimpleWaterloggedBlock {
/*  23 */   protected static final VoxelShape FLAT_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D);
/*  24 */   protected static final VoxelShape HALF_BLOCK_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
/*  25 */   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
/*     */   
/*     */   private final boolean isStraight;
/*     */   
/*     */   public static boolean isRail(Level $$0, BlockPos $$1) {
/*  30 */     return isRail($$0.getBlockState($$1));
/*     */   }
/*     */   
/*     */   public static boolean isRail(BlockState $$0) {
/*  34 */     return ($$0.is(BlockTags.RAILS) && $$0.getBlock() instanceof BaseRailBlock);
/*     */   }
/*     */   
/*     */   protected BaseRailBlock(boolean $$0, BlockBehaviour.Properties $$1) {
/*  38 */     super($$1);
/*  39 */     this.isStraight = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract MapCodec<? extends BaseRailBlock> codec();
/*     */   
/*     */   public boolean isStraight() {
/*  46 */     return this.isStraight;
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/*  51 */     RailShape $$4 = $$0.is(this) ? (RailShape)$$0.getValue(getShapeProperty()) : null;
/*  52 */     if ($$4 != null && $$4.isAscending()) {
/*  53 */       return HALF_BLOCK_AABB;
/*     */     }
/*  55 */     return FLAT_AABB;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canSurvive(BlockState $$0, LevelReader $$1, BlockPos $$2) {
/*  61 */     return canSupportRigidBlock((BlockGetter)$$1, $$2.below());
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPlace(BlockState $$0, Level $$1, BlockPos $$2, BlockState $$3, boolean $$4) {
/*  66 */     if ($$3.is($$0.getBlock())) {
/*     */       return;
/*     */     }
/*  69 */     updateState($$0, $$1, $$2, $$4);
/*     */   }
/*     */   
/*     */   protected BlockState updateState(BlockState $$0, Level $$1, BlockPos $$2, boolean $$3) {
/*  73 */     $$0 = updateDir($$1, $$2, $$0, true);
/*     */     
/*  75 */     if (this.isStraight) {
/*  76 */       $$1.neighborChanged($$0, $$2, this, $$2, $$3);
/*     */     }
/*     */     
/*  79 */     return $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void neighborChanged(BlockState $$0, Level $$1, BlockPos $$2, Block $$3, BlockPos $$4, boolean $$5) {
/*  84 */     if ($$1.isClientSide || !$$1.getBlockState($$2).is(this)) {
/*     */       return;
/*     */     }
/*     */     
/*  88 */     RailShape $$6 = (RailShape)$$0.getValue(getShapeProperty());
/*     */     
/*  90 */     if (shouldBeRemoved($$2, $$1, $$6)) {
/*  91 */       dropResources($$0, $$1, $$2);
/*  92 */       $$1.removeBlock($$2, $$5);
/*     */     } else {
/*  94 */       updateState($$0, $$1, $$2, $$3);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static boolean shouldBeRemoved(BlockPos $$0, Level $$1, RailShape $$2) {
/*  99 */     if (!canSupportRigidBlock((BlockGetter)$$1, $$0.below())) {
/* 100 */       return true;
/*     */     }
/* 102 */     switch ($$2) {
/*     */       case ASCENDING_EAST:
/* 104 */         return !canSupportRigidBlock((BlockGetter)$$1, $$0.east());
/*     */       case ASCENDING_WEST:
/* 106 */         return !canSupportRigidBlock((BlockGetter)$$1, $$0.west());
/*     */       case ASCENDING_NORTH:
/* 108 */         return !canSupportRigidBlock((BlockGetter)$$1, $$0.north());
/*     */       case ASCENDING_SOUTH:
/* 110 */         return !canSupportRigidBlock((BlockGetter)$$1, $$0.south());
/*     */     } 
/* 112 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateState(BlockState $$0, Level $$1, BlockPos $$2, Block $$3) {}
/*     */ 
/*     */   
/*     */   protected BlockState updateDir(Level $$0, BlockPos $$1, BlockState $$2, boolean $$3) {
/* 120 */     if ($$0.isClientSide) {
/* 121 */       return $$2;
/*     */     }
/* 123 */     RailShape $$4 = (RailShape)$$2.getValue(getShapeProperty());
/* 124 */     return (new RailState($$0, $$1, $$2)).place($$0.hasNeighborSignal($$1), $$3, $$4).getState();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRemove(BlockState $$0, Level $$1, BlockPos $$2, BlockState $$3, boolean $$4) {
/* 129 */     if ($$4) {
/*     */       return;
/*     */     }
/*     */     
/* 133 */     super.onRemove($$0, $$1, $$2, $$3, $$4);
/*     */     
/* 135 */     if (((RailShape)$$0.getValue(getShapeProperty())).isAscending()) {
/* 136 */       $$1.updateNeighborsAt($$2.above(), this);
/*     */     }
/*     */     
/* 139 */     if (this.isStraight) {
/* 140 */       $$1.updateNeighborsAt($$2, this);
/* 141 */       $$1.updateNeighborsAt($$2.below(), this);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/* 147 */     FluidState $$1 = $$0.getLevel().getFluidState($$0.getClickedPos());
/* 148 */     boolean $$2 = ($$1.getType() == Fluids.WATER);
/* 149 */     BlockState $$3 = defaultBlockState();
/* 150 */     Direction $$4 = $$0.getHorizontalDirection();
/* 151 */     boolean $$5 = ($$4 == Direction.EAST || $$4 == Direction.WEST);
/* 152 */     return (BlockState)((BlockState)$$3.setValue(getShapeProperty(), $$5 ? (Comparable)RailShape.EAST_WEST : (Comparable)RailShape.NORTH_SOUTH)).setValue((Property)WATERLOGGED, Boolean.valueOf($$2));
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract Property<RailShape> getShapeProperty();
/*     */   
/*     */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/* 159 */     if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/* 160 */       $$3.scheduleTick($$4, (Fluid)Fluids.WATER, Fluids.WATER.getTickDelay((LevelReader)$$3));
/*     */     }
/* 162 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   public FluidState getFluidState(BlockState $$0) {
/* 167 */     if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/* 168 */       return Fluids.WATER.getSource(false);
/*     */     }
/* 170 */     return super.getFluidState($$0);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\BaseRailBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */