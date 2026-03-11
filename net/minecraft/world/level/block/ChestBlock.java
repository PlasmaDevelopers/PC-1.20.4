/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.function.BiPredicate;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.stats.Stat;
/*     */ import net.minecraft.stats.Stats;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.CompoundContainer;
/*     */ import net.minecraft.world.Container;
/*     */ import net.minecraft.world.Containers;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.MenuProvider;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.animal.Cat;
/*     */ import net.minecraft.world.entity.player.Inventory;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.inventory.AbstractContainerMenu;
/*     */ import net.minecraft.world.inventory.ChestMenu;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.context.BlockPlaceContext;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*     */ import net.minecraft.world.level.block.entity.ChestBlockEntity;
/*     */ import net.minecraft.world.level.block.entity.LidBlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.BooleanProperty;
/*     */ import net.minecraft.world.level.block.state.properties.ChestType;
/*     */ import net.minecraft.world.level.block.state.properties.DirectionProperty;
/*     */ import net.minecraft.world.level.block.state.properties.EnumProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.material.Fluid;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ import net.minecraft.world.level.material.Fluids;
/*     */ import net.minecraft.world.level.pathfinder.PathComputationType;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class ChestBlock extends AbstractChestBlock<ChestBlockEntity> implements SimpleWaterloggedBlock {
/*     */   static {
/*  59 */     CODEC = simpleCodec($$0 -> new ChestBlock($$0, ()));
/*     */   }
/*     */   public static final MapCodec<ChestBlock> CODEC;
/*     */   public MapCodec<? extends ChestBlock> codec() {
/*  63 */     return CODEC;
/*     */   }
/*     */   
/*  66 */   public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
/*  67 */   public static final EnumProperty<ChestType> TYPE = BlockStateProperties.CHEST_TYPE;
/*  68 */   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
/*     */   
/*     */   public static final int EVENT_SET_OPEN_COUNT = 1;
/*     */   protected static final int AABB_OFFSET = 1;
/*     */   protected static final int AABB_HEIGHT = 14;
/*  73 */   protected static final VoxelShape NORTH_AABB = Block.box(1.0D, 0.0D, 0.0D, 15.0D, 14.0D, 15.0D);
/*  74 */   protected static final VoxelShape SOUTH_AABB = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 14.0D, 16.0D);
/*  75 */   protected static final VoxelShape WEST_AABB = Block.box(0.0D, 0.0D, 1.0D, 15.0D, 14.0D, 15.0D);
/*  76 */   protected static final VoxelShape EAST_AABB = Block.box(1.0D, 0.0D, 1.0D, 16.0D, 14.0D, 15.0D);
/*  77 */   protected static final VoxelShape AABB = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 14.0D, 15.0D);
/*     */   
/*     */   protected ChestBlock(BlockBehaviour.Properties $$0, Supplier<BlockEntityType<? extends ChestBlockEntity>> $$1) {
/*  80 */     super($$0, $$1);
/*  81 */     registerDefaultState((BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)FACING, (Comparable)Direction.NORTH)).setValue((Property)TYPE, (Comparable)ChestType.SINGLE)).setValue((Property)WATERLOGGED, Boolean.valueOf(false)));
/*     */   }
/*     */   
/*     */   public static DoubleBlockCombiner.BlockType getBlockType(BlockState $$0) {
/*  85 */     ChestType $$1 = (ChestType)$$0.getValue((Property)TYPE);
/*  86 */     if ($$1 == ChestType.SINGLE) {
/*  87 */       return DoubleBlockCombiner.BlockType.SINGLE;
/*     */     }
/*  89 */     if ($$1 == ChestType.RIGHT) {
/*  90 */       return DoubleBlockCombiner.BlockType.FIRST;
/*     */     }
/*  92 */     return DoubleBlockCombiner.BlockType.SECOND;
/*     */   }
/*     */ 
/*     */   
/*     */   public RenderShape getRenderShape(BlockState $$0) {
/*  97 */     return RenderShape.ENTITYBLOCK_ANIMATED;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/* 102 */     if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/* 103 */       $$3.scheduleTick($$4, (Fluid)Fluids.WATER, Fluids.WATER.getTickDelay((LevelReader)$$3));
/*     */     }
/* 105 */     if ($$2.is(this) && $$1.getAxis().isHorizontal()) {
/* 106 */       ChestType $$6 = (ChestType)$$2.getValue((Property)TYPE);
/* 107 */       if ($$0.getValue((Property)TYPE) == ChestType.SINGLE && $$6 != ChestType.SINGLE && 
/* 108 */         $$0.getValue((Property)FACING) == $$2.getValue((Property)FACING) && getConnectedDirection($$2) == $$1.getOpposite()) {
/* 109 */         return (BlockState)$$0.setValue((Property)TYPE, (Comparable)$$6.getOpposite());
/*     */       }
/*     */     }
/* 112 */     else if (getConnectedDirection($$0) == $$1) {
/* 113 */       return (BlockState)$$0.setValue((Property)TYPE, (Comparable)ChestType.SINGLE);
/*     */     } 
/* 115 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 120 */     if ($$0.getValue((Property)TYPE) == ChestType.SINGLE) {
/* 121 */       return AABB;
/*     */     }
/*     */     
/* 124 */     switch (getConnectedDirection($$0))
/*     */     
/*     */     { default:
/* 127 */         return NORTH_AABB;
/*     */       case SOUTH:
/* 129 */         return SOUTH_AABB;
/*     */       case WEST:
/* 131 */         return WEST_AABB;
/*     */       case EAST:
/* 133 */         break; }  return EAST_AABB;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Direction getConnectedDirection(BlockState $$0) {
/* 138 */     Direction $$1 = (Direction)$$0.getValue((Property)FACING);
/* 139 */     return ($$0.getValue((Property)TYPE) == ChestType.LEFT) ? $$1.getClockWise() : $$1.getCounterClockWise();
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/* 144 */     ChestType $$1 = ChestType.SINGLE;
/* 145 */     Direction $$2 = $$0.getHorizontalDirection().getOpposite();
/* 146 */     FluidState $$3 = $$0.getLevel().getFluidState($$0.getClickedPos());
/*     */     
/* 148 */     boolean $$4 = $$0.isSecondaryUseActive();
/* 149 */     Direction $$5 = $$0.getClickedFace();
/*     */     
/* 151 */     if ($$5.getAxis().isHorizontal() && $$4) {
/* 152 */       Direction $$6 = candidatePartnerFacing($$0, $$5.getOpposite());
/* 153 */       if ($$6 != null && $$6.getAxis() != $$5.getAxis()) {
/* 154 */         $$2 = $$6;
/* 155 */         $$1 = ($$2.getCounterClockWise() == $$5.getOpposite()) ? ChestType.RIGHT : ChestType.LEFT;
/*     */       } 
/*     */     } 
/* 158 */     if ($$1 == ChestType.SINGLE && !$$4) {
/* 159 */       if ($$2 == candidatePartnerFacing($$0, $$2.getClockWise())) {
/* 160 */         $$1 = ChestType.LEFT;
/* 161 */       } else if ($$2 == candidatePartnerFacing($$0, $$2.getCounterClockWise())) {
/* 162 */         $$1 = ChestType.RIGHT;
/*     */       } 
/*     */     }
/*     */     
/* 166 */     return (BlockState)((BlockState)((BlockState)defaultBlockState().setValue((Property)FACING, (Comparable)$$2)).setValue((Property)TYPE, (Comparable)$$1)).setValue((Property)WATERLOGGED, Boolean.valueOf(($$3.getType() == Fluids.WATER)));
/*     */   }
/*     */ 
/*     */   
/*     */   public FluidState getFluidState(BlockState $$0) {
/* 171 */     if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/* 172 */       return Fluids.WATER.getSource(false);
/*     */     }
/* 174 */     return super.getFluidState($$0);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private Direction candidatePartnerFacing(BlockPlaceContext $$0, Direction $$1) {
/* 179 */     BlockState $$2 = $$0.getLevel().getBlockState($$0.getClickedPos().relative($$1));
/*     */     
/* 181 */     return ($$2.is(this) && $$2.getValue((Property)TYPE) == ChestType.SINGLE) ? (Direction)$$2.getValue((Property)FACING) : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPlacedBy(Level $$0, BlockPos $$1, BlockState $$2, LivingEntity $$3, ItemStack $$4) {
/* 186 */     if ($$4.hasCustomHoverName()) {
/* 187 */       BlockEntity $$5 = $$0.getBlockEntity($$1);
/* 188 */       if ($$5 instanceof ChestBlockEntity) {
/* 189 */         ((ChestBlockEntity)$$5).setCustomName($$4.getHoverName());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRemove(BlockState $$0, Level $$1, BlockPos $$2, BlockState $$3, boolean $$4) {
/* 196 */     Containers.dropContentsOnDestroy($$0, $$3, $$1, $$2);
/* 197 */     super.onRemove($$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult use(BlockState $$0, Level $$1, BlockPos $$2, Player $$3, InteractionHand $$4, BlockHitResult $$5) {
/* 202 */     if ($$1.isClientSide) {
/* 203 */       return InteractionResult.SUCCESS;
/*     */     }
/*     */     
/* 206 */     MenuProvider $$6 = getMenuProvider($$0, $$1, $$2);
/* 207 */     if ($$6 != null) {
/* 208 */       $$3.openMenu($$6);
/* 209 */       $$3.awardStat(getOpenChestStat());
/* 210 */       PiglinAi.angerNearbyPiglins($$3, true);
/*     */     } 
/*     */     
/* 213 */     return InteractionResult.CONSUME;
/*     */   }
/*     */   
/*     */   protected Stat<ResourceLocation> getOpenChestStat() {
/* 217 */     return Stats.CUSTOM.get(Stats.OPEN_CHEST);
/*     */   }
/*     */   
/*     */   public BlockEntityType<? extends ChestBlockEntity> blockEntityType() {
/* 221 */     return this.blockEntityType.get();
/*     */   }
/*     */   
/* 224 */   private static final DoubleBlockCombiner.Combiner<ChestBlockEntity, Optional<Container>> CHEST_COMBINER = new DoubleBlockCombiner.Combiner<ChestBlockEntity, Optional<Container>>()
/*     */     {
/*     */       public Optional<Container> acceptDouble(ChestBlockEntity $$0, ChestBlockEntity $$1) {
/* 227 */         return (Optional)Optional.of(new CompoundContainer((Container)$$0, (Container)$$1));
/*     */       }
/*     */ 
/*     */       
/*     */       public Optional<Container> acceptSingle(ChestBlockEntity $$0) {
/* 232 */         return (Optional)Optional.of($$0);
/*     */       }
/*     */ 
/*     */       
/*     */       public Optional<Container> acceptNone() {
/* 237 */         return Optional.empty();
/*     */       }
/*     */     };
/*     */   
/*     */   @Nullable
/*     */   public static Container getContainer(ChestBlock $$0, BlockState $$1, Level $$2, BlockPos $$3, boolean $$4) {
/* 243 */     return ((Optional<Container>)$$0.combine($$1, $$2, $$3, $$4).<Optional<Container>>apply(CHEST_COMBINER)).orElse(null);
/*     */   }
/*     */ 
/*     */   
/*     */   public DoubleBlockCombiner.NeighborCombineResult<? extends ChestBlockEntity> combine(BlockState $$0, Level $$1, BlockPos $$2, boolean $$3) {
/*     */     BiPredicate<LevelAccessor, BlockPos> $$5;
/* 249 */     if ($$3) {
/* 250 */       BiPredicate<LevelAccessor, BlockPos> $$4 = ($$0, $$1) -> false;
/*     */     } else {
/* 252 */       $$5 = ChestBlock::isChestBlockedAt;
/*     */     } 
/* 254 */     return DoubleBlockCombiner.combineWithNeigbour(this.blockEntityType.get(), ChestBlock::getBlockType, ChestBlock::getConnectedDirection, FACING, $$0, (LevelAccessor)$$1, $$2, $$5);
/*     */   }
/*     */   
/* 257 */   private static final DoubleBlockCombiner.Combiner<ChestBlockEntity, Optional<MenuProvider>> MENU_PROVIDER_COMBINER = new DoubleBlockCombiner.Combiner<ChestBlockEntity, Optional<MenuProvider>>()
/*     */     {
/*     */       public Optional<MenuProvider> acceptDouble(final ChestBlockEntity first, final ChestBlockEntity second) {
/* 260 */         final CompoundContainer container = new CompoundContainer((Container)first, (Container)second);
/* 261 */         return Optional.of(new MenuProvider()
/*     */             {
/*     */               @Nullable
/*     */               public AbstractContainerMenu createMenu(int $$0, Inventory $$1, Player $$2) {
/* 265 */                 if (first.canOpen($$2) && second.canOpen($$2)) {
/* 266 */                   first.unpackLootTable($$1.player);
/* 267 */                   second.unpackLootTable($$1.player);
/*     */                   
/* 269 */                   return (AbstractContainerMenu)ChestMenu.sixRows($$0, $$1, container);
/*     */                 } 
/* 271 */                 return null;
/*     */               }
/*     */ 
/*     */               
/*     */               public Component getDisplayName() {
/* 276 */                 if (first.hasCustomName()) {
/* 277 */                   return first.getDisplayName();
/*     */                 }
/* 279 */                 if (second.hasCustomName()) {
/* 280 */                   return second.getDisplayName();
/*     */                 }
/* 282 */                 return (Component)Component.translatable("container.chestDouble");
/*     */               }
/*     */             });
/*     */       }
/*     */ 
/*     */       
/*     */       public Optional<MenuProvider> acceptSingle(ChestBlockEntity $$0) {
/* 289 */         return (Optional)Optional.of($$0);
/*     */       }
/*     */ 
/*     */       
/*     */       public Optional<MenuProvider> acceptNone() {
/* 294 */         return Optional.empty();
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public MenuProvider getMenuProvider(BlockState $$0, Level $$1, BlockPos $$2) {
/* 301 */     return ((Optional<MenuProvider>)combine($$0, $$1, $$2, false).<Optional<MenuProvider>>apply(MENU_PROVIDER_COMBINER)).orElse(null);
/*     */   }
/*     */   
/*     */   public static DoubleBlockCombiner.Combiner<ChestBlockEntity, Float2FloatFunction> opennessCombiner(final LidBlockEntity entity) {
/* 305 */     return new DoubleBlockCombiner.Combiner<ChestBlockEntity, Float2FloatFunction>()
/*     */       {
/*     */         public Float2FloatFunction acceptDouble(ChestBlockEntity $$0, ChestBlockEntity $$1) {
/* 308 */           return $$2 -> Math.max($$0.getOpenNess($$2), $$1.getOpenNess($$2));
/*     */         }
/*     */ 
/*     */         
/*     */         public Float2FloatFunction acceptSingle(ChestBlockEntity $$0) {
/* 313 */           Objects.requireNonNull($$0); return $$0::getOpenNess;
/*     */         }
/*     */ 
/*     */         
/*     */         public Float2FloatFunction acceptNone() {
/* 318 */           Objects.requireNonNull(entity); return entity::getOpenNess;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockEntity newBlockEntity(BlockPos $$0, BlockState $$1) {
/* 325 */     return (BlockEntity)new ChestBlockEntity($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level $$0, BlockState $$1, BlockEntityType<T> $$2) {
/* 331 */     return $$0.isClientSide ? createTickerHelper($$2, (BlockEntityType)blockEntityType(), ChestBlockEntity::lidAnimateTick) : null;
/*     */   }
/*     */   
/*     */   public static boolean isChestBlockedAt(LevelAccessor $$0, BlockPos $$1) {
/* 335 */     return (isBlockedChestByBlock((BlockGetter)$$0, $$1) || isCatSittingOnChest($$0, $$1));
/*     */   }
/*     */   
/*     */   private static boolean isBlockedChestByBlock(BlockGetter $$0, BlockPos $$1) {
/* 339 */     BlockPos $$2 = $$1.above();
/* 340 */     return $$0.getBlockState($$2).isRedstoneConductor($$0, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isCatSittingOnChest(LevelAccessor $$0, BlockPos $$1) {
/* 345 */     List<Cat> $$2 = $$0.getEntitiesOfClass(Cat.class, new AABB($$1.getX(), ($$1.getY() + 1), $$1.getZ(), ($$1.getX() + 1), ($$1.getY() + 2), ($$1.getZ() + 1)));
/* 346 */     if (!$$2.isEmpty()) {
/* 347 */       for (Cat $$3 : $$2) {
/* 348 */         if ($$3.isInSittingPose()) {
/* 349 */           return true;
/*     */         }
/*     */       } 
/*     */     }
/* 353 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasAnalogOutputSignal(BlockState $$0) {
/* 358 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAnalogOutputSignal(BlockState $$0, Level $$1, BlockPos $$2) {
/* 363 */     return AbstractContainerMenu.getRedstoneSignalFromContainer(getContainer(this, $$0, $$1, $$2, false));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState rotate(BlockState $$0, Rotation $$1) {
/* 368 */     return (BlockState)$$0.setValue((Property)FACING, (Comparable)$$1.rotate((Direction)$$0.getValue((Property)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState mirror(BlockState $$0, Mirror $$1) {
/* 373 */     return $$0.rotate($$1.getRotation((Direction)$$0.getValue((Property)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 378 */     $$0.add(new Property[] { (Property)FACING, (Property)TYPE, (Property)WATERLOGGED });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPathfindable(BlockState $$0, BlockGetter $$1, BlockPos $$2, PathComputationType $$3) {
/* 383 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/* 388 */     BlockEntity $$4 = $$1.getBlockEntity($$2);
/*     */     
/* 390 */     if ($$4 instanceof ChestBlockEntity)
/* 391 */       ((ChestBlockEntity)$$4).recheckOpen(); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\ChestBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */