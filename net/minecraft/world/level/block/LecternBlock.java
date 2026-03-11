/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.stats.Stats;
/*     */ import net.minecraft.tags.ItemTags;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.MenuProvider;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.item.ItemEntity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.BlockItem;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.context.BlockPlaceContext;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.entity.LecternBlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.BooleanProperty;
/*     */ import net.minecraft.world.level.block.state.properties.DirectionProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.level.pathfinder.PathComputationType;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.Shapes;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class LecternBlock extends BaseEntityBlock {
/*  42 */   public static final MapCodec<LecternBlock> CODEC = simpleCodec(LecternBlock::new);
/*     */ 
/*     */   
/*     */   public MapCodec<LecternBlock> codec() {
/*  46 */     return CODEC;
/*     */   }
/*     */   
/*  49 */   public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
/*  50 */   public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
/*  51 */   public static final BooleanProperty HAS_BOOK = BlockStateProperties.HAS_BOOK;
/*     */   
/*  53 */   public static final VoxelShape SHAPE_BASE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D);
/*  54 */   public static final VoxelShape SHAPE_POST = Block.box(4.0D, 2.0D, 4.0D, 12.0D, 14.0D, 12.0D);
/*     */   
/*  56 */   public static final VoxelShape SHAPE_COMMON = Shapes.or(SHAPE_BASE, SHAPE_POST);
/*     */   
/*  58 */   public static final VoxelShape SHAPE_TOP_PLATE = Block.box(0.0D, 15.0D, 0.0D, 16.0D, 15.0D, 16.0D);
/*  59 */   public static final VoxelShape SHAPE_COLLISION = Shapes.or(SHAPE_COMMON, SHAPE_TOP_PLATE);
/*     */   
/*  61 */   public static final VoxelShape SHAPE_WEST = Shapes.or(
/*  62 */       Block.box(1.0D, 10.0D, 0.0D, 5.333333D, 14.0D, 16.0D), new VoxelShape[] {
/*  63 */         Block.box(5.333333D, 12.0D, 0.0D, 9.666667D, 16.0D, 16.0D), 
/*  64 */         Block.box(9.666667D, 14.0D, 0.0D, 14.0D, 18.0D, 16.0D), SHAPE_COMMON
/*     */       });
/*     */   
/*  67 */   public static final VoxelShape SHAPE_NORTH = Shapes.or(
/*  68 */       Block.box(0.0D, 10.0D, 1.0D, 16.0D, 14.0D, 5.333333D), new VoxelShape[] {
/*  69 */         Block.box(0.0D, 12.0D, 5.333333D, 16.0D, 16.0D, 9.666667D), 
/*  70 */         Block.box(0.0D, 14.0D, 9.666667D, 16.0D, 18.0D, 14.0D), SHAPE_COMMON
/*     */       });
/*     */   
/*  73 */   public static final VoxelShape SHAPE_EAST = Shapes.or(
/*  74 */       Block.box(10.666667D, 10.0D, 0.0D, 15.0D, 14.0D, 16.0D), new VoxelShape[] {
/*  75 */         Block.box(6.333333D, 12.0D, 0.0D, 10.666667D, 16.0D, 16.0D), 
/*  76 */         Block.box(2.0D, 14.0D, 0.0D, 6.333333D, 18.0D, 16.0D), SHAPE_COMMON
/*     */       });
/*     */   
/*  79 */   public static final VoxelShape SHAPE_SOUTH = Shapes.or(
/*  80 */       Block.box(0.0D, 10.0D, 10.666667D, 16.0D, 14.0D, 15.0D), new VoxelShape[] {
/*  81 */         Block.box(0.0D, 12.0D, 6.333333D, 16.0D, 16.0D, 10.666667D), 
/*  82 */         Block.box(0.0D, 14.0D, 2.0D, 16.0D, 18.0D, 6.333333D), SHAPE_COMMON
/*     */       });
/*     */   
/*     */   private static final int PAGE_CHANGE_IMPULSE_TICKS = 2;
/*     */ 
/*     */   
/*     */   protected LecternBlock(BlockBehaviour.Properties $$0) {
/*  89 */     super($$0);
/*  90 */     registerDefaultState((BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)FACING, (Comparable)Direction.NORTH)).setValue((Property)POWERED, Boolean.valueOf(false))).setValue((Property)HAS_BOOK, Boolean.valueOf(false)));
/*     */   }
/*     */ 
/*     */   
/*     */   public RenderShape getRenderShape(BlockState $$0) {
/*  95 */     return RenderShape.MODEL;
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getOcclusionShape(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/* 100 */     return SHAPE_COMMON;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean useShapeForLightOcclusion(BlockState $$0) {
/* 105 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/* 110 */     Level $$1 = $$0.getLevel();
/* 111 */     ItemStack $$2 = $$0.getItemInHand();
/* 112 */     Player $$3 = $$0.getPlayer();
/* 113 */     boolean $$4 = false;
/*     */     
/* 115 */     if (!$$1.isClientSide && $$3 != null && $$3.canUseGameMasterBlocks()) {
/* 116 */       CompoundTag $$5 = BlockItem.getBlockEntityData($$2);
/* 117 */       if ($$5 != null && $$5.contains("Book")) {
/* 118 */         $$4 = true;
/*     */       }
/*     */     } 
/* 121 */     return (BlockState)((BlockState)defaultBlockState().setValue((Property)FACING, (Comparable)$$0.getHorizontalDirection().getOpposite())).setValue((Property)HAS_BOOK, Boolean.valueOf($$4));
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getCollisionShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 126 */     return SHAPE_COLLISION;
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 131 */     switch ((Direction)$$0.getValue((Property)FACING)) {
/*     */       case NORTH:
/* 133 */         return SHAPE_NORTH;
/*     */       case SOUTH:
/* 135 */         return SHAPE_SOUTH;
/*     */       case EAST:
/* 137 */         return SHAPE_EAST;
/*     */       case WEST:
/* 139 */         return SHAPE_WEST;
/*     */     } 
/* 141 */     return SHAPE_COMMON;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockState rotate(BlockState $$0, Rotation $$1) {
/* 147 */     return (BlockState)$$0.setValue((Property)FACING, (Comparable)$$1.rotate((Direction)$$0.getValue((Property)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState mirror(BlockState $$0, Mirror $$1) {
/* 152 */     return $$0.rotate($$1.getRotation((Direction)$$0.getValue((Property)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 157 */     $$0.add(new Property[] { (Property)FACING, (Property)POWERED, (Property)HAS_BOOK });
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockEntity newBlockEntity(BlockPos $$0, BlockState $$1) {
/* 162 */     return (BlockEntity)new LecternBlockEntity($$0, $$1);
/*     */   }
/*     */   
/*     */   public static boolean tryPlaceBook(@Nullable Entity $$0, Level $$1, BlockPos $$2, BlockState $$3, ItemStack $$4) {
/* 166 */     if (!((Boolean)$$3.getValue((Property)HAS_BOOK)).booleanValue()) {
/* 167 */       if (!$$1.isClientSide) {
/* 168 */         placeBook($$0, $$1, $$2, $$3, $$4);
/*     */       }
/* 170 */       return true;
/*     */     } 
/*     */     
/* 173 */     return false;
/*     */   }
/*     */   
/*     */   private static void placeBook(@Nullable Entity $$0, Level $$1, BlockPos $$2, BlockState $$3, ItemStack $$4) {
/* 177 */     BlockEntity $$5 = $$1.getBlockEntity($$2);
/* 178 */     if ($$5 instanceof LecternBlockEntity) { LecternBlockEntity $$6 = (LecternBlockEntity)$$5;
/* 179 */       $$6.setBook($$4.split(1));
/* 180 */       resetBookState($$0, $$1, $$2, $$3, true);
/* 181 */       $$1.playSound(null, $$2, SoundEvents.BOOK_PUT, SoundSource.BLOCKS, 1.0F, 1.0F); }
/*     */   
/*     */   }
/*     */   
/*     */   public static void resetBookState(@Nullable Entity $$0, Level $$1, BlockPos $$2, BlockState $$3, boolean $$4) {
/* 186 */     BlockState $$5 = (BlockState)((BlockState)$$3.setValue((Property)POWERED, Boolean.valueOf(false))).setValue((Property)HAS_BOOK, Boolean.valueOf($$4));
/* 187 */     $$1.setBlock($$2, $$5, 3);
/* 188 */     $$1.gameEvent(GameEvent.BLOCK_CHANGE, $$2, GameEvent.Context.of($$0, $$5));
/* 189 */     updateBelow($$1, $$2, $$3);
/*     */   }
/*     */   
/*     */   public static void signalPageChange(Level $$0, BlockPos $$1, BlockState $$2) {
/* 193 */     changePowered($$0, $$1, $$2, true);
/* 194 */     $$0.scheduleTick($$1, $$2.getBlock(), 2);
/* 195 */     $$0.levelEvent(1043, $$1, 0);
/*     */   }
/*     */   
/*     */   private static void changePowered(Level $$0, BlockPos $$1, BlockState $$2, boolean $$3) {
/* 199 */     $$0.setBlock($$1, (BlockState)$$2.setValue((Property)POWERED, Boolean.valueOf($$3)), 3);
/* 200 */     updateBelow($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   private static void updateBelow(Level $$0, BlockPos $$1, BlockState $$2) {
/* 204 */     $$0.updateNeighborsAt($$1.below(), $$2.getBlock());
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/* 209 */     changePowered((Level)$$1, $$2, $$0, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRemove(BlockState $$0, Level $$1, BlockPos $$2, BlockState $$3, boolean $$4) {
/* 214 */     if ($$0.is($$3.getBlock())) {
/*     */       return;
/*     */     }
/*     */     
/* 218 */     if (((Boolean)$$0.getValue((Property)HAS_BOOK)).booleanValue()) {
/* 219 */       popBook($$0, $$1, $$2);
/*     */     }
/*     */     
/* 222 */     if (((Boolean)$$0.getValue((Property)POWERED)).booleanValue()) {
/* 223 */       $$1.updateNeighborsAt($$2.below(), this);
/*     */     }
/*     */     
/* 226 */     super.onRemove($$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */   
/*     */   private void popBook(BlockState $$0, Level $$1, BlockPos $$2) {
/* 230 */     BlockEntity $$3 = $$1.getBlockEntity($$2);
/* 231 */     if ($$3 instanceof LecternBlockEntity) { LecternBlockEntity $$4 = (LecternBlockEntity)$$3;
/* 232 */       Direction $$5 = (Direction)$$0.getValue((Property)FACING);
/* 233 */       ItemStack $$6 = $$4.getBook().copy();
/* 234 */       float $$7 = 0.25F * $$5.getStepX();
/* 235 */       float $$8 = 0.25F * $$5.getStepZ();
/* 236 */       ItemEntity $$9 = new ItemEntity($$1, $$2.getX() + 0.5D + $$7, ($$2.getY() + 1), $$2.getZ() + 0.5D + $$8, $$6);
/* 237 */       $$9.setDefaultPickUpDelay();
/* 238 */       $$1.addFreshEntity((Entity)$$9);
/*     */       
/* 240 */       $$4.clearContent(); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSignalSource(BlockState $$0) {
/* 246 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSignal(BlockState $$0, BlockGetter $$1, BlockPos $$2, Direction $$3) {
/* 251 */     return ((Boolean)$$0.getValue((Property)POWERED)).booleanValue() ? 15 : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDirectSignal(BlockState $$0, BlockGetter $$1, BlockPos $$2, Direction $$3) {
/* 256 */     return ($$3 == Direction.UP && ((Boolean)$$0.getValue((Property)POWERED)).booleanValue()) ? 15 : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasAnalogOutputSignal(BlockState $$0) {
/* 261 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAnalogOutputSignal(BlockState $$0, Level $$1, BlockPos $$2) {
/* 266 */     if (((Boolean)$$0.getValue((Property)HAS_BOOK)).booleanValue()) {
/* 267 */       BlockEntity $$3 = $$1.getBlockEntity($$2);
/* 268 */       if ($$3 instanceof LecternBlockEntity) {
/* 269 */         return ((LecternBlockEntity)$$3).getRedstoneSignal();
/*     */       }
/*     */     } 
/*     */     
/* 273 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult use(BlockState $$0, Level $$1, BlockPos $$2, Player $$3, InteractionHand $$4, BlockHitResult $$5) {
/* 278 */     if (((Boolean)$$0.getValue((Property)HAS_BOOK)).booleanValue()) {
/* 279 */       if (!$$1.isClientSide) {
/* 280 */         openScreen($$1, $$2, $$3);
/*     */       }
/* 282 */       return InteractionResult.sidedSuccess($$1.isClientSide);
/*     */     } 
/*     */ 
/*     */     
/* 286 */     ItemStack $$6 = $$3.getItemInHand($$4);
/*     */     
/* 288 */     if ($$6.isEmpty() || $$6.is(ItemTags.LECTERN_BOOKS)) {
/* 289 */       return InteractionResult.PASS;
/*     */     }
/*     */     
/* 292 */     return InteractionResult.CONSUME;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public MenuProvider getMenuProvider(BlockState $$0, Level $$1, BlockPos $$2) {
/* 298 */     if (!((Boolean)$$0.getValue((Property)HAS_BOOK)).booleanValue()) {
/* 299 */       return null;
/*     */     }
/*     */     
/* 302 */     return super.getMenuProvider($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   private void openScreen(Level $$0, BlockPos $$1, Player $$2) {
/* 306 */     BlockEntity $$3 = $$0.getBlockEntity($$1);
/* 307 */     if ($$3 instanceof LecternBlockEntity) {
/* 308 */       $$2.openMenu((MenuProvider)$$3);
/* 309 */       $$2.awardStat(Stats.INTERACT_WITH_LECTERN);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPathfindable(BlockState $$0, BlockGetter $$1, BlockPos $$2, PathComputationType $$3) {
/* 315 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\LecternBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */