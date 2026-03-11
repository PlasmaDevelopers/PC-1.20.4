/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.BiFunction;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.context.BlockPlaceContext;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Explosion;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockSetType;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.BooleanProperty;
/*     */ import net.minecraft.world.level.block.state.properties.DoorHingeSide;
/*     */ import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
/*     */ import net.minecraft.world.level.block.state.properties.EnumProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.level.pathfinder.PathComputationType;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class DoorBlock extends Block {
/*     */   static {
/*  41 */     CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)BlockSetType.CODEC.fieldOf("block_set_type").forGetter(DoorBlock::type), (App)propertiesCodec()).apply((Applicative)$$0, DoorBlock::new));
/*     */   }
/*     */ 
/*     */   
/*     */   public static final MapCodec<DoorBlock> CODEC;
/*     */   
/*     */   public MapCodec<? extends DoorBlock> codec() {
/*  48 */     return CODEC;
/*     */   }
/*     */   
/*  51 */   public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
/*  52 */   public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
/*  53 */   public static final EnumProperty<DoorHingeSide> HINGE = BlockStateProperties.DOOR_HINGE;
/*  54 */   public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
/*  55 */   public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
/*     */   
/*     */   protected static final float AABB_DOOR_THICKNESS = 3.0F;
/*     */   
/*  59 */   protected static final VoxelShape SOUTH_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 3.0D);
/*  60 */   protected static final VoxelShape NORTH_AABB = Block.box(0.0D, 0.0D, 13.0D, 16.0D, 16.0D, 16.0D);
/*  61 */   protected static final VoxelShape WEST_AABB = Block.box(13.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
/*  62 */   protected static final VoxelShape EAST_AABB = Block.box(0.0D, 0.0D, 0.0D, 3.0D, 16.0D, 16.0D);
/*     */   
/*     */   private final BlockSetType type;
/*     */   
/*     */   protected DoorBlock(BlockSetType $$0, BlockBehaviour.Properties $$1) {
/*  67 */     super($$1.sound($$0.soundType()));
/*  68 */     this.type = $$0;
/*  69 */     registerDefaultState((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)FACING, (Comparable)Direction.NORTH)).setValue((Property)OPEN, Boolean.valueOf(false))).setValue((Property)HINGE, (Comparable)DoorHingeSide.LEFT)).setValue((Property)POWERED, Boolean.valueOf(false))).setValue((Property)HALF, (Comparable)DoubleBlockHalf.LOWER));
/*     */   }
/*     */   
/*     */   public BlockSetType type() {
/*  73 */     return this.type;
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/*  78 */     Direction $$4 = (Direction)$$0.getValue((Property)FACING);
/*  79 */     boolean $$5 = !((Boolean)$$0.getValue((Property)OPEN)).booleanValue();
/*  80 */     boolean $$6 = ($$0.getValue((Property)HINGE) == DoorHingeSide.RIGHT);
/*     */     
/*  82 */     switch ($$4) { default: return 
/*  83 */           $$5 ? EAST_AABB : ($$6 ? NORTH_AABB : SOUTH_AABB);
/*  84 */       case LAND: return $$5 ? SOUTH_AABB : ($$6 ? EAST_AABB : WEST_AABB);
/*  85 */       case AIR: return $$5 ? WEST_AABB : ($$6 ? SOUTH_AABB : NORTH_AABB);
/*  86 */       case WATER: break; }  return $$5 ? NORTH_AABB : ($$6 ? WEST_AABB : EAST_AABB);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/*  92 */     DoubleBlockHalf $$6 = (DoubleBlockHalf)$$0.getValue((Property)HALF);
/*  93 */     if ($$1.getAxis() == Direction.Axis.Y) if ((($$6 == DoubleBlockHalf.LOWER) ? true : false) == (($$1 == Direction.UP) ? true : false)) {
/*     */         
/*  95 */         if ($$2.getBlock() instanceof DoorBlock && $$2.getValue((Property)HALF) != $$6)
/*     */         {
/*  97 */           return (BlockState)$$2.setValue((Property)HALF, (Comparable)$$6);
/*     */         }
/*  99 */         return Blocks.AIR.defaultBlockState();
/*     */       } 
/*     */ 
/*     */     
/* 103 */     if ($$6 == DoubleBlockHalf.LOWER && $$1 == Direction.DOWN && !$$0.canSurvive((LevelReader)$$3, $$4)) {
/* 104 */       return Blocks.AIR.defaultBlockState();
/*     */     }
/*     */     
/* 107 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onExplosionHit(BlockState $$0, Level $$1, BlockPos $$2, Explosion $$3, BiConsumer<ItemStack, BlockPos> $$4) {
/* 112 */     if ($$3.getBlockInteraction() == Explosion.BlockInteraction.TRIGGER_BLOCK && $$0.getValue((Property)HALF) == DoubleBlockHalf.LOWER && !$$1.isClientSide() && this.type.canOpenByWindCharge() && !((Boolean)$$0.getValue((Property)POWERED)).booleanValue()) {
/* 113 */       setOpen((Entity)null, $$1, $$0, $$2, !isOpen($$0));
/*     */     }
/* 115 */     super.onExplosionHit($$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState playerWillDestroy(Level $$0, BlockPos $$1, BlockState $$2, Player $$3) {
/* 120 */     if (!$$0.isClientSide && ($$3.isCreative() || !$$3.hasCorrectToolForDrops($$2))) {
/* 121 */       DoublePlantBlock.preventDropFromBottomPart($$0, $$1, $$2, $$3);
/*     */     }
/*     */     
/* 124 */     return super.playerWillDestroy($$0, $$1, $$2, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPathfindable(BlockState $$0, BlockGetter $$1, BlockPos $$2, PathComputationType $$3) {
/* 129 */     switch ($$3) { default: throw new IncompatibleClassChangeError();case LAND: case AIR: case WATER: break; }  return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/* 138 */     BlockPos $$1 = $$0.getClickedPos();
/* 139 */     Level $$2 = $$0.getLevel();
/* 140 */     if ($$1.getY() < $$2.getMaxBuildHeight() - 1 && $$2.getBlockState($$1.above()).canBeReplaced($$0)) {
/* 141 */       boolean $$3 = ($$2.hasNeighborSignal($$1) || $$2.hasNeighborSignal($$1.above()));
/*     */       
/* 143 */       return (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)defaultBlockState().setValue((Property)FACING, (Comparable)$$0.getHorizontalDirection())).setValue((Property)HINGE, (Comparable)getHinge($$0))).setValue((Property)POWERED, Boolean.valueOf($$3))).setValue((Property)OPEN, Boolean.valueOf($$3))).setValue((Property)HALF, (Comparable)DoubleBlockHalf.LOWER);
/*     */     } 
/*     */     
/* 146 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPlacedBy(Level $$0, BlockPos $$1, BlockState $$2, LivingEntity $$3, ItemStack $$4) {
/* 151 */     $$0.setBlock($$1.above(), (BlockState)$$2.setValue((Property)HALF, (Comparable)DoubleBlockHalf.UPPER), 3);
/*     */   }
/*     */   
/*     */   private DoorHingeSide getHinge(BlockPlaceContext $$0) {
/* 155 */     Level level = $$0.getLevel();
/* 156 */     BlockPos $$2 = $$0.getClickedPos();
/* 157 */     Direction $$3 = $$0.getHorizontalDirection();
/* 158 */     BlockPos $$4 = $$2.above();
/*     */     
/* 160 */     Direction $$5 = $$3.getCounterClockWise();
/* 161 */     BlockPos $$6 = $$2.relative($$5);
/* 162 */     BlockState $$7 = level.getBlockState($$6);
/* 163 */     BlockPos $$8 = $$4.relative($$5);
/* 164 */     BlockState $$9 = level.getBlockState($$8);
/*     */     
/* 166 */     Direction $$10 = $$3.getClockWise();
/* 167 */     BlockPos $$11 = $$2.relative($$10);
/* 168 */     BlockState $$12 = level.getBlockState($$11);
/* 169 */     BlockPos $$13 = $$4.relative($$10);
/* 170 */     BlockState $$14 = level.getBlockState($$13);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 175 */     int $$15 = ($$7.isCollisionShapeFullBlock((BlockGetter)level, $$6) ? -1 : 0) + ($$9.isCollisionShapeFullBlock((BlockGetter)level, $$8) ? -1 : 0) + ($$12.isCollisionShapeFullBlock((BlockGetter)level, $$11) ? 1 : 0) + ($$14.isCollisionShapeFullBlock((BlockGetter)level, $$13) ? 1 : 0);
/*     */     
/* 177 */     boolean $$16 = ($$7.is(this) && $$7.getValue((Property)HALF) == DoubleBlockHalf.LOWER);
/* 178 */     boolean $$17 = ($$12.is(this) && $$12.getValue((Property)HALF) == DoubleBlockHalf.LOWER);
/*     */     
/* 180 */     if (($$16 && !$$17) || $$15 > 0) {
/* 181 */       return DoorHingeSide.RIGHT;
/*     */     }
/* 183 */     if (($$17 && !$$16) || $$15 < 0) {
/* 184 */       return DoorHingeSide.LEFT;
/*     */     }
/*     */     
/* 187 */     int $$18 = $$3.getStepX();
/* 188 */     int $$19 = $$3.getStepZ();
/*     */     
/* 190 */     Vec3 $$20 = $$0.getClickLocation();
/* 191 */     double $$21 = $$20.x - $$2.getX();
/* 192 */     double $$22 = $$20.z - $$2.getZ();
/*     */     
/* 194 */     return (($$18 < 0 && $$22 < 0.5D) || ($$18 > 0 && $$22 > 0.5D) || ($$19 < 0 && $$21 > 0.5D) || ($$19 > 0 && $$21 < 0.5D)) ? DoorHingeSide.RIGHT : DoorHingeSide.LEFT;
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult use(BlockState $$0, Level $$1, BlockPos $$2, Player $$3, InteractionHand $$4, BlockHitResult $$5) {
/* 199 */     if (!this.type.canOpenByHand()) {
/* 200 */       return InteractionResult.PASS;
/*     */     }
/*     */     
/* 203 */     $$0 = (BlockState)$$0.cycle((Property)OPEN);
/* 204 */     $$1.setBlock($$2, $$0, 10);
/* 205 */     playSound((Entity)$$3, $$1, $$2, ((Boolean)$$0.getValue((Property)OPEN)).booleanValue());
/* 206 */     $$1.gameEvent((Entity)$$3, isOpen($$0) ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, $$2);
/* 207 */     return InteractionResult.sidedSuccess($$1.isClientSide);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpen(BlockState $$0) {
/* 215 */     return ((Boolean)$$0.getValue((Property)OPEN)).booleanValue();
/*     */   }
/*     */   
/*     */   public void setOpen(@Nullable Entity $$0, Level $$1, BlockState $$2, BlockPos $$3, boolean $$4) {
/* 219 */     if (!$$2.is(this) || ((Boolean)$$2.getValue((Property)OPEN)).booleanValue() == $$4) {
/*     */       return;
/*     */     }
/*     */     
/* 223 */     $$1.setBlock($$3, (BlockState)$$2.setValue((Property)OPEN, Boolean.valueOf($$4)), 10);
/* 224 */     playSound($$0, $$1, $$3, $$4);
/* 225 */     $$1.gameEvent($$0, $$4 ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public void neighborChanged(BlockState $$0, Level $$1, BlockPos $$2, Block $$3, BlockPos $$4, boolean $$5) {
/* 230 */     boolean $$6 = ($$1.hasNeighborSignal($$2) || $$1.hasNeighborSignal($$2.relative(($$0.getValue((Property)HALF) == DoubleBlockHalf.LOWER) ? Direction.UP : Direction.DOWN)));
/* 231 */     if (!defaultBlockState().is($$3) && $$6 != ((Boolean)$$0.getValue((Property)POWERED)).booleanValue()) {
/* 232 */       if ($$6 != ((Boolean)$$0.getValue((Property)OPEN)).booleanValue()) {
/* 233 */         playSound((Entity)null, $$1, $$2, $$6);
/* 234 */         $$1.gameEvent(null, $$6 ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, $$2);
/*     */       } 
/* 236 */       $$1.setBlock($$2, (BlockState)((BlockState)$$0.setValue((Property)POWERED, Boolean.valueOf($$6))).setValue((Property)OPEN, Boolean.valueOf($$6)), 2);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canSurvive(BlockState $$0, LevelReader $$1, BlockPos $$2) {
/* 243 */     BlockPos $$3 = $$2.below();
/* 244 */     BlockState $$4 = $$1.getBlockState($$3);
/* 245 */     if ($$0.getValue((Property)HALF) == DoubleBlockHalf.LOWER) {
/* 246 */       return $$4.isFaceSturdy((BlockGetter)$$1, $$3, Direction.UP);
/*     */     }
/* 248 */     return $$4.is(this);
/*     */   }
/*     */ 
/*     */   
/*     */   private void playSound(@Nullable Entity $$0, Level $$1, BlockPos $$2, boolean $$3) {
/* 253 */     $$1.playSound($$0, $$2, $$3 ? this.type.doorOpen() : this.type.doorClose(), SoundSource.BLOCKS, 1.0F, $$1.getRandom().nextFloat() * 0.1F + 0.9F);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState rotate(BlockState $$0, Rotation $$1) {
/* 258 */     return (BlockState)$$0.setValue((Property)FACING, (Comparable)$$1.rotate((Direction)$$0.getValue((Property)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState mirror(BlockState $$0, Mirror $$1) {
/* 263 */     if ($$1 == Mirror.NONE) {
/* 264 */       return $$0;
/*     */     }
/* 266 */     return (BlockState)$$0.rotate($$1.getRotation((Direction)$$0.getValue((Property)FACING))).cycle((Property)HINGE);
/*     */   }
/*     */ 
/*     */   
/*     */   public long getSeed(BlockState $$0, BlockPos $$1) {
/* 271 */     return Mth.getSeed($$1.getX(), $$1.below(($$0.getValue((Property)HALF) == DoubleBlockHalf.LOWER) ? 0 : 1).getY(), $$1.getZ());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 276 */     $$0.add(new Property[] { (Property)HALF, (Property)FACING, (Property)OPEN, (Property)HINGE, (Property)POWERED });
/*     */   }
/*     */   
/*     */   public static boolean isWoodenDoor(Level $$0, BlockPos $$1) {
/* 280 */     return isWoodenDoor($$0.getBlockState($$1));
/*     */   }
/*     */   
/*     */   public static boolean isWoodenDoor(BlockState $$0) {
/* 284 */     Block block = $$0.getBlock(); if (block instanceof DoorBlock) { DoorBlock $$1 = (DoorBlock)block; if ($$1.type().canOpenByHand()); }  return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\DoorBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */