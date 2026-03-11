/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.stats.Stats;
/*     */ import net.minecraft.world.Containers;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.MenuProvider;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.context.BlockPlaceContext;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*     */ import net.minecraft.world.level.block.entity.Hopper;
/*     */ import net.minecraft.world.level.block.entity.HopperBlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.BooleanProperty;
/*     */ import net.minecraft.world.level.block.state.properties.DirectionProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.pathfinder.PathComputationType;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ import net.minecraft.world.phys.shapes.BooleanOp;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.Shapes;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class HopperBlock extends BaseEntityBlock {
/*  38 */   public static final MapCodec<HopperBlock> CODEC = simpleCodec(HopperBlock::new);
/*     */ 
/*     */   
/*     */   public MapCodec<HopperBlock> codec() {
/*  42 */     return CODEC;
/*     */   }
/*     */   
/*  45 */   public static final DirectionProperty FACING = BlockStateProperties.FACING_HOPPER;
/*  46 */   public static final BooleanProperty ENABLED = BlockStateProperties.ENABLED;
/*     */   
/*  48 */   private static final VoxelShape TOP = Block.box(0.0D, 10.0D, 0.0D, 16.0D, 16.0D, 16.0D);
/*  49 */   private static final VoxelShape FUNNEL = Block.box(4.0D, 4.0D, 4.0D, 12.0D, 10.0D, 12.0D);
/*     */   
/*  51 */   private static final VoxelShape CONVEX_BASE = Shapes.or(FUNNEL, TOP);
/*  52 */   private static final VoxelShape BASE = Shapes.join(CONVEX_BASE, Hopper.INSIDE, BooleanOp.ONLY_FIRST);
/*     */   
/*  54 */   private static final VoxelShape DOWN_SHAPE = Shapes.or(BASE, Block.box(6.0D, 0.0D, 6.0D, 10.0D, 4.0D, 10.0D));
/*  55 */   private static final VoxelShape EAST_SHAPE = Shapes.or(BASE, Block.box(12.0D, 4.0D, 6.0D, 16.0D, 8.0D, 10.0D));
/*  56 */   private static final VoxelShape NORTH_SHAPE = Shapes.or(BASE, Block.box(6.0D, 4.0D, 0.0D, 10.0D, 8.0D, 4.0D));
/*  57 */   private static final VoxelShape SOUTH_SHAPE = Shapes.or(BASE, Block.box(6.0D, 4.0D, 12.0D, 10.0D, 8.0D, 16.0D));
/*  58 */   private static final VoxelShape WEST_SHAPE = Shapes.or(BASE, Block.box(0.0D, 4.0D, 6.0D, 4.0D, 8.0D, 10.0D));
/*     */   
/*  60 */   private static final VoxelShape DOWN_INTERACTION_SHAPE = Hopper.INSIDE;
/*  61 */   private static final VoxelShape EAST_INTERACTION_SHAPE = Shapes.or(Hopper.INSIDE, Block.box(12.0D, 8.0D, 6.0D, 16.0D, 10.0D, 10.0D));
/*  62 */   private static final VoxelShape NORTH_INTERACTION_SHAPE = Shapes.or(Hopper.INSIDE, Block.box(6.0D, 8.0D, 0.0D, 10.0D, 10.0D, 4.0D));
/*  63 */   private static final VoxelShape SOUTH_INTERACTION_SHAPE = Shapes.or(Hopper.INSIDE, Block.box(6.0D, 8.0D, 12.0D, 10.0D, 10.0D, 16.0D));
/*  64 */   private static final VoxelShape WEST_INTERACTION_SHAPE = Shapes.or(Hopper.INSIDE, Block.box(0.0D, 8.0D, 6.0D, 4.0D, 10.0D, 10.0D));
/*     */   
/*     */   public HopperBlock(BlockBehaviour.Properties $$0) {
/*  67 */     super($$0);
/*  68 */     registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)FACING, (Comparable)Direction.DOWN)).setValue((Property)ENABLED, Boolean.valueOf(true)));
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/*  73 */     switch ((Direction)$$0.getValue((Property)FACING)) {
/*     */       case DOWN:
/*  75 */         return DOWN_SHAPE;
/*     */       case NORTH:
/*  77 */         return NORTH_SHAPE;
/*     */       case SOUTH:
/*  79 */         return SOUTH_SHAPE;
/*     */       case WEST:
/*  81 */         return WEST_SHAPE;
/*     */       case EAST:
/*  83 */         return EAST_SHAPE;
/*     */     } 
/*  85 */     return BASE;
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getInteractionShape(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/*  90 */     switch ((Direction)$$0.getValue((Property)FACING)) {
/*     */       case DOWN:
/*  92 */         return DOWN_INTERACTION_SHAPE;
/*     */       case NORTH:
/*  94 */         return NORTH_INTERACTION_SHAPE;
/*     */       case SOUTH:
/*  96 */         return SOUTH_INTERACTION_SHAPE;
/*     */       case WEST:
/*  98 */         return WEST_INTERACTION_SHAPE;
/*     */       case EAST:
/* 100 */         return EAST_INTERACTION_SHAPE;
/*     */     } 
/* 102 */     return Hopper.INSIDE;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/* 107 */     Direction $$1 = $$0.getClickedFace().getOpposite();
/* 108 */     return (BlockState)((BlockState)defaultBlockState().setValue((Property)FACING, ($$1.getAxis() == Direction.Axis.Y) ? (Comparable)Direction.DOWN : (Comparable)$$1)).setValue((Property)ENABLED, Boolean.valueOf(true));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockEntity newBlockEntity(BlockPos $$0, BlockState $$1) {
/* 113 */     return (BlockEntity)new HopperBlockEntity($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level $$0, BlockState $$1, BlockEntityType<T> $$2) {
/* 119 */     return $$0.isClientSide ? null : createTickerHelper($$2, BlockEntityType.HOPPER, HopperBlockEntity::pushItemsTick);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPlacedBy(Level $$0, BlockPos $$1, BlockState $$2, LivingEntity $$3, ItemStack $$4) {
/* 124 */     if ($$4.hasCustomHoverName()) {
/* 125 */       BlockEntity $$5 = $$0.getBlockEntity($$1);
/* 126 */       if ($$5 instanceof HopperBlockEntity) {
/* 127 */         ((HopperBlockEntity)$$5).setCustomName($$4.getHoverName());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPlace(BlockState $$0, Level $$1, BlockPos $$2, BlockState $$3, boolean $$4) {
/* 134 */     if ($$3.is($$0.getBlock())) {
/*     */       return;
/*     */     }
/* 137 */     checkPoweredState($$1, $$2, $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult use(BlockState $$0, Level $$1, BlockPos $$2, Player $$3, InteractionHand $$4, BlockHitResult $$5) {
/* 142 */     if ($$1.isClientSide) {
/* 143 */       return InteractionResult.SUCCESS;
/*     */     }
/*     */     
/* 146 */     BlockEntity $$6 = $$1.getBlockEntity($$2);
/* 147 */     if ($$6 instanceof HopperBlockEntity) {
/* 148 */       $$3.openMenu((MenuProvider)$$6);
/* 149 */       $$3.awardStat(Stats.INSPECT_HOPPER);
/*     */     } 
/*     */     
/* 152 */     return InteractionResult.CONSUME;
/*     */   }
/*     */ 
/*     */   
/*     */   public void neighborChanged(BlockState $$0, Level $$1, BlockPos $$2, Block $$3, BlockPos $$4, boolean $$5) {
/* 157 */     checkPoweredState($$1, $$2, $$0);
/*     */   }
/*     */   
/*     */   private void checkPoweredState(Level $$0, BlockPos $$1, BlockState $$2) {
/* 161 */     boolean $$3 = !$$0.hasNeighborSignal($$1);
/* 162 */     if ($$3 != ((Boolean)$$2.getValue((Property)ENABLED)).booleanValue()) {
/* 163 */       $$0.setBlock($$1, (BlockState)$$2.setValue((Property)ENABLED, Boolean.valueOf($$3)), 2);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRemove(BlockState $$0, Level $$1, BlockPos $$2, BlockState $$3, boolean $$4) {
/* 169 */     Containers.dropContentsOnDestroy($$0, $$3, $$1, $$2);
/* 170 */     super.onRemove($$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */ 
/*     */   
/*     */   public RenderShape getRenderShape(BlockState $$0) {
/* 175 */     return RenderShape.MODEL;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasAnalogOutputSignal(BlockState $$0) {
/* 180 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAnalogOutputSignal(BlockState $$0, Level $$1, BlockPos $$2) {
/* 185 */     return AbstractContainerMenu.getRedstoneSignalFromBlockEntity($$1.getBlockEntity($$2));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState rotate(BlockState $$0, Rotation $$1) {
/* 190 */     return (BlockState)$$0.setValue((Property)FACING, (Comparable)$$1.rotate((Direction)$$0.getValue((Property)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState mirror(BlockState $$0, Mirror $$1) {
/* 195 */     return $$0.rotate($$1.getRotation((Direction)$$0.getValue((Property)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 200 */     $$0.add(new Property[] { (Property)FACING, (Property)ENABLED });
/*     */   }
/*     */ 
/*     */   
/*     */   public void entityInside(BlockState $$0, Level $$1, BlockPos $$2, Entity $$3) {
/* 205 */     BlockEntity $$4 = $$1.getBlockEntity($$2);
/* 206 */     if ($$4 instanceof HopperBlockEntity) {
/* 207 */       HopperBlockEntity.entityInside($$1, $$2, $$0, $$3, (HopperBlockEntity)$$4);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPathfindable(BlockState $$0, BlockGetter $$1, BlockPos $$2, PathComputationType $$3) {
/* 213 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\HopperBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */