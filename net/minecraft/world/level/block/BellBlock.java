/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.stats.Stats;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.entity.projectile.Projectile;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.context.BlockPlaceContext;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Explosion;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.entity.BellBlockEntity;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.entity.BlockEntityTicker;
/*     */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BellAttachType;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.BooleanProperty;
/*     */ import net.minecraft.world.level.block.state.properties.DirectionProperty;
/*     */ import net.minecraft.world.level.block.state.properties.EnumProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.level.pathfinder.PathComputationType;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.Shapes;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class BellBlock extends BaseEntityBlock {
/*  44 */   public static final MapCodec<BellBlock> CODEC = simpleCodec(BellBlock::new);
/*     */ 
/*     */   
/*     */   public MapCodec<BellBlock> codec() {
/*  48 */     return CODEC;
/*     */   }
/*     */   
/*  51 */   public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
/*  52 */   public static final EnumProperty<BellAttachType> ATTACHMENT = BlockStateProperties.BELL_ATTACHMENT;
/*  53 */   public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
/*     */   
/*  55 */   private static final VoxelShape NORTH_SOUTH_FLOOR_SHAPE = Block.box(0.0D, 0.0D, 4.0D, 16.0D, 16.0D, 12.0D);
/*  56 */   private static final VoxelShape EAST_WEST_FLOOR_SHAPE = Block.box(4.0D, 0.0D, 0.0D, 12.0D, 16.0D, 16.0D);
/*     */   
/*  58 */   private static final VoxelShape BELL_TOP_SHAPE = Block.box(5.0D, 6.0D, 5.0D, 11.0D, 13.0D, 11.0D);
/*  59 */   private static final VoxelShape BELL_BOTTOM_SHAPE = Block.box(4.0D, 4.0D, 4.0D, 12.0D, 6.0D, 12.0D);
/*  60 */   private static final VoxelShape BELL_SHAPE = Shapes.or(BELL_BOTTOM_SHAPE, BELL_TOP_SHAPE);
/*     */   
/*  62 */   private static final VoxelShape NORTH_SOUTH_BETWEEN = Shapes.or(BELL_SHAPE, Block.box(7.0D, 13.0D, 0.0D, 9.0D, 15.0D, 16.0D));
/*  63 */   private static final VoxelShape EAST_WEST_BETWEEN = Shapes.or(BELL_SHAPE, Block.box(0.0D, 13.0D, 7.0D, 16.0D, 15.0D, 9.0D));
/*  64 */   private static final VoxelShape TO_WEST = Shapes.or(BELL_SHAPE, Block.box(0.0D, 13.0D, 7.0D, 13.0D, 15.0D, 9.0D));
/*  65 */   private static final VoxelShape TO_EAST = Shapes.or(BELL_SHAPE, Block.box(3.0D, 13.0D, 7.0D, 16.0D, 15.0D, 9.0D));
/*  66 */   private static final VoxelShape TO_NORTH = Shapes.or(BELL_SHAPE, Block.box(7.0D, 13.0D, 0.0D, 9.0D, 15.0D, 13.0D));
/*  67 */   private static final VoxelShape TO_SOUTH = Shapes.or(BELL_SHAPE, Block.box(7.0D, 13.0D, 3.0D, 9.0D, 15.0D, 16.0D));
/*  68 */   private static final VoxelShape CEILING_SHAPE = Shapes.or(BELL_SHAPE, Block.box(7.0D, 13.0D, 7.0D, 9.0D, 16.0D, 9.0D));
/*     */   
/*     */   public static final int EVENT_BELL_RING = 1;
/*     */   
/*     */   public BellBlock(BlockBehaviour.Properties $$0) {
/*  73 */     super($$0);
/*  74 */     registerDefaultState((BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)FACING, (Comparable)Direction.NORTH)).setValue((Property)ATTACHMENT, (Comparable)BellAttachType.FLOOR)).setValue((Property)POWERED, Boolean.valueOf(false)));
/*     */   }
/*     */ 
/*     */   
/*     */   public void neighborChanged(BlockState $$0, Level $$1, BlockPos $$2, Block $$3, BlockPos $$4, boolean $$5) {
/*  79 */     boolean $$6 = $$1.hasNeighborSignal($$2);
/*     */     
/*  81 */     if ($$6 != ((Boolean)$$0.getValue((Property)POWERED)).booleanValue()) {
/*  82 */       if ($$6) {
/*  83 */         attemptToRing($$1, $$2, (Direction)null);
/*     */       }
/*  85 */       $$1.setBlock($$2, (BlockState)$$0.setValue((Property)POWERED, Boolean.valueOf($$6)), 3);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onProjectileHit(Level $$0, BlockState $$1, BlockHitResult $$2, Projectile $$3) {
/*  91 */     Entity $$4 = $$3.getOwner();
/*  92 */     Player $$5 = ($$4 instanceof Player) ? (Player)$$4 : null;
/*  93 */     onHit($$0, $$1, $$2, $$5, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult use(BlockState $$0, Level $$1, BlockPos $$2, Player $$3, InteractionHand $$4, BlockHitResult $$5) {
/*  98 */     return onHit($$1, $$0, $$5, $$3, true) ? InteractionResult.sidedSuccess($$1.isClientSide) : InteractionResult.PASS;
/*     */   }
/*     */   
/*     */   public boolean onHit(Level $$0, BlockState $$1, BlockHitResult $$2, @Nullable Player $$3, boolean $$4) {
/* 102 */     Direction $$5 = $$2.getDirection();
/* 103 */     BlockPos $$6 = $$2.getBlockPos();
/* 104 */     boolean $$7 = (!$$4 || isProperHit($$1, $$5, ($$2.getLocation()).y - $$6.getY()));
/* 105 */     if ($$7) {
/* 106 */       boolean $$8 = attemptToRing((Entity)$$3, $$0, $$6, $$5);
/* 107 */       if ($$8 && $$3 != null) {
/* 108 */         $$3.awardStat(Stats.BELL_RING);
/*     */       }
/* 110 */       return true;
/*     */     } 
/* 112 */     return false;
/*     */   }
/*     */   
/*     */   private boolean isProperHit(BlockState $$0, Direction $$1, double $$2) {
/* 116 */     if ($$1.getAxis() == Direction.Axis.Y || $$2 > 0.8123999834060669D) {
/* 117 */       return false;
/*     */     }
/*     */     
/* 120 */     Direction $$3 = (Direction)$$0.getValue((Property)FACING);
/* 121 */     BellAttachType $$4 = (BellAttachType)$$0.getValue((Property)ATTACHMENT);
/*     */     
/* 123 */     switch ($$4) {
/*     */       case FLOOR:
/* 125 */         return ($$3.getAxis() == $$1.getAxis());
/*     */       case SINGLE_WALL:
/*     */       case DOUBLE_WALL:
/* 128 */         return ($$3.getAxis() != $$1.getAxis());
/*     */       case CEILING:
/* 130 */         return true;
/*     */     } 
/* 132 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean attemptToRing(Level $$0, BlockPos $$1, @Nullable Direction $$2) {
/* 137 */     return attemptToRing((Entity)null, $$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   public boolean attemptToRing(@Nullable Entity $$0, Level $$1, BlockPos $$2, @Nullable Direction $$3) {
/* 141 */     BlockEntity $$4 = $$1.getBlockEntity($$2);
/* 142 */     if (!$$1.isClientSide && $$4 instanceof BellBlockEntity) {
/* 143 */       if ($$3 == null) {
/* 144 */         $$3 = (Direction)$$1.getBlockState($$2).getValue((Property)FACING);
/*     */       }
/* 146 */       ((BellBlockEntity)$$4).onHit($$3);
/* 147 */       $$1.playSound(null, $$2, SoundEvents.BELL_BLOCK, SoundSource.BLOCKS, 2.0F, 1.0F);
/* 148 */       $$1.gameEvent($$0, GameEvent.BLOCK_CHANGE, $$2);
/* 149 */       return true;
/*     */     } 
/* 151 */     return false;
/*     */   }
/*     */   
/*     */   private VoxelShape getVoxelShape(BlockState $$0) {
/* 155 */     Direction $$1 = (Direction)$$0.getValue((Property)FACING);
/* 156 */     BellAttachType $$2 = (BellAttachType)$$0.getValue((Property)ATTACHMENT);
/*     */     
/* 158 */     if ($$2 == BellAttachType.FLOOR) {
/* 159 */       if ($$1 == Direction.NORTH || $$1 == Direction.SOUTH) {
/* 160 */         return NORTH_SOUTH_FLOOR_SHAPE;
/*     */       }
/* 162 */       return EAST_WEST_FLOOR_SHAPE;
/* 163 */     }  if ($$2 == BellAttachType.CEILING)
/* 164 */       return CEILING_SHAPE; 
/* 165 */     if ($$2 == BellAttachType.DOUBLE_WALL) {
/* 166 */       if ($$1 == Direction.NORTH || $$1 == Direction.SOUTH) {
/* 167 */         return NORTH_SOUTH_BETWEEN;
/*     */       }
/* 169 */       return EAST_WEST_BETWEEN;
/*     */     } 
/* 171 */     if ($$1 == Direction.NORTH)
/* 172 */       return TO_NORTH; 
/* 173 */     if ($$1 == Direction.SOUTH)
/* 174 */       return TO_SOUTH; 
/* 175 */     if ($$1 == Direction.EAST) {
/* 176 */       return TO_EAST;
/*     */     }
/* 178 */     return TO_WEST;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public VoxelShape getCollisionShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 184 */     return getVoxelShape($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 189 */     return getVoxelShape($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public RenderShape getRenderShape(BlockState $$0) {
/* 194 */     return RenderShape.MODEL;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/* 201 */     Direction $$1 = $$0.getClickedFace();
/* 202 */     BlockPos $$2 = $$0.getClickedPos();
/* 203 */     Level $$3 = $$0.getLevel();
/* 204 */     Direction.Axis $$4 = $$1.getAxis();
/*     */     
/* 206 */     if ($$4 == Direction.Axis.Y) {
/* 207 */       BlockState $$5 = (BlockState)((BlockState)defaultBlockState().setValue((Property)ATTACHMENT, ($$1 == Direction.DOWN) ? (Comparable)BellAttachType.CEILING : (Comparable)BellAttachType.FLOOR)).setValue((Property)FACING, (Comparable)$$0.getHorizontalDirection());
/*     */       
/* 209 */       if ($$5.canSurvive((LevelReader)$$0.getLevel(), $$2)) {
/* 210 */         return $$5;
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 215 */       boolean $$6 = (($$4 == Direction.Axis.X && $$3.getBlockState($$2.west()).isFaceSturdy((BlockGetter)$$3, $$2.west(), Direction.EAST) && $$3.getBlockState($$2.east()).isFaceSturdy((BlockGetter)$$3, $$2.east(), Direction.WEST)) || ($$4 == Direction.Axis.Z && $$3.getBlockState($$2.north()).isFaceSturdy((BlockGetter)$$3, $$2.north(), Direction.SOUTH) && $$3.getBlockState($$2.south()).isFaceSturdy((BlockGetter)$$3, $$2.south(), Direction.NORTH)));
/*     */       
/* 217 */       BlockState $$7 = (BlockState)((BlockState)defaultBlockState().setValue((Property)FACING, (Comparable)$$1.getOpposite())).setValue((Property)ATTACHMENT, $$6 ? (Comparable)BellAttachType.DOUBLE_WALL : (Comparable)BellAttachType.SINGLE_WALL);
/*     */       
/* 219 */       if ($$7.canSurvive((LevelReader)$$0.getLevel(), $$0.getClickedPos())) {
/* 220 */         return $$7;
/*     */       }
/* 222 */       boolean $$8 = $$3.getBlockState($$2.below()).isFaceSturdy((BlockGetter)$$3, $$2.below(), Direction.UP);
/*     */       
/* 224 */       $$7 = (BlockState)$$7.setValue((Property)ATTACHMENT, $$8 ? (Comparable)BellAttachType.FLOOR : (Comparable)BellAttachType.CEILING);
/*     */       
/* 226 */       if ($$7.canSurvive((LevelReader)$$0.getLevel(), $$0.getClickedPos())) {
/* 227 */         return $$7;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 232 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onExplosionHit(BlockState $$0, Level $$1, BlockPos $$2, Explosion $$3, BiConsumer<ItemStack, BlockPos> $$4) {
/* 237 */     if ($$3.getBlockInteraction() == Explosion.BlockInteraction.TRIGGER_BLOCK && !$$1.isClientSide()) {
/* 238 */       attemptToRing($$1, $$2, (Direction)null);
/*     */     }
/* 240 */     super.onExplosionHit($$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/* 245 */     BellAttachType $$6 = (BellAttachType)$$0.getValue((Property)ATTACHMENT);
/*     */     
/* 247 */     Direction $$7 = getConnectedDirection($$0).getOpposite();
/* 248 */     if ($$7 == $$1 && !$$0.canSurvive((LevelReader)$$3, $$4) && $$6 != BellAttachType.DOUBLE_WALL) {
/* 249 */       return Blocks.AIR.defaultBlockState();
/*     */     }
/*     */     
/* 252 */     if ($$1.getAxis() == ((Direction)$$0.getValue((Property)FACING)).getAxis()) {
/* 253 */       if ($$6 == BellAttachType.DOUBLE_WALL && !$$2.isFaceSturdy((BlockGetter)$$3, $$5, $$1))
/* 254 */         return (BlockState)((BlockState)$$0.setValue((Property)ATTACHMENT, (Comparable)BellAttachType.SINGLE_WALL)).setValue((Property)FACING, (Comparable)$$1.getOpposite()); 
/* 255 */       if ($$6 == BellAttachType.SINGLE_WALL && $$7.getOpposite() == $$1 && $$2.isFaceSturdy((BlockGetter)$$3, $$5, (Direction)$$0.getValue((Property)FACING))) {
/* 256 */         return (BlockState)$$0.setValue((Property)ATTACHMENT, (Comparable)BellAttachType.DOUBLE_WALL);
/*     */       }
/*     */     } 
/*     */     
/* 260 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canSurvive(BlockState $$0, LevelReader $$1, BlockPos $$2) {
/* 265 */     Direction $$3 = getConnectedDirection($$0).getOpposite();
/*     */     
/* 267 */     if ($$3 == Direction.UP) {
/* 268 */       return Block.canSupportCenter($$1, $$2.above(), Direction.DOWN);
/*     */     }
/* 270 */     return FaceAttachedHorizontalDirectionalBlock.canAttach($$1, $$2, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   private static Direction getConnectedDirection(BlockState $$0) {
/* 275 */     switch ((BellAttachType)$$0.getValue((Property)ATTACHMENT)) {
/*     */       case CEILING:
/* 277 */         return Direction.DOWN;
/*     */       case FLOOR:
/* 279 */         return Direction.UP;
/*     */     } 
/* 281 */     return ((Direction)$$0.getValue((Property)FACING)).getOpposite();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 287 */     $$0.add(new Property[] { (Property)FACING, (Property)ATTACHMENT, (Property)POWERED });
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BlockEntity newBlockEntity(BlockPos $$0, BlockState $$1) {
/* 293 */     return (BlockEntity)new BellBlockEntity($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level $$0, BlockState $$1, BlockEntityType<T> $$2) {
/* 299 */     return createTickerHelper($$2, BlockEntityType.BELL, $$0.isClientSide ? BellBlockEntity::clientTick : BellBlockEntity::serverTick);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPathfindable(BlockState $$0, BlockGetter $$1, BlockPos $$2, PathComputationType $$3) {
/* 304 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState rotate(BlockState $$0, Rotation $$1) {
/* 309 */     return (BlockState)$$0.setValue((Property)FACING, (Comparable)$$1.rotate((Direction)$$0.getValue((Property)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState mirror(BlockState $$0, Mirror $$1) {
/* 314 */     return $$0.rotate($$1.getRotation((Direction)$$0.getValue((Property)FACING)));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\BellBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */