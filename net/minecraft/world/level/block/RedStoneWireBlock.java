/*     */ package net.minecraft.world.level.block;
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.UnmodifiableIterator;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.core.particles.DustParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.context.BlockPlaceContext;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.EnumProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.block.state.properties.RedstoneSide;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.Shapes;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class RedStoneWireBlock extends Block {
/*  39 */   public static final MapCodec<RedStoneWireBlock> CODEC = simpleCodec(RedStoneWireBlock::new);
/*     */ 
/*     */   
/*     */   public MapCodec<RedStoneWireBlock> codec() {
/*  43 */     return CODEC;
/*     */   }
/*     */   
/*  46 */   public static final EnumProperty<RedstoneSide> NORTH = BlockStateProperties.NORTH_REDSTONE;
/*  47 */   public static final EnumProperty<RedstoneSide> EAST = BlockStateProperties.EAST_REDSTONE;
/*  48 */   public static final EnumProperty<RedstoneSide> SOUTH = BlockStateProperties.SOUTH_REDSTONE;
/*  49 */   public static final EnumProperty<RedstoneSide> WEST = BlockStateProperties.WEST_REDSTONE;
/*  50 */   public static final IntegerProperty POWER = BlockStateProperties.POWER;
/*     */   
/*  52 */   public static final Map<Direction, EnumProperty<RedstoneSide>> PROPERTY_BY_DIRECTION = Maps.newEnumMap((Map)ImmutableMap.of(Direction.NORTH, NORTH, Direction.EAST, EAST, Direction.SOUTH, SOUTH, Direction.WEST, WEST));
/*     */ 
/*     */   
/*     */   protected static final int H = 1;
/*     */ 
/*     */   
/*     */   protected static final int W = 3;
/*     */   
/*     */   protected static final int E = 13;
/*     */   
/*     */   protected static final int N = 3;
/*     */   
/*     */   protected static final int S = 13;
/*     */   
/*  66 */   private static final VoxelShape SHAPE_DOT = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 1.0D, 13.0D);
/*  67 */   private static final Map<Direction, VoxelShape> SHAPES_FLOOR = Maps.newEnumMap((Map)ImmutableMap.of(Direction.NORTH, 
/*  68 */         Block.box(3.0D, 0.0D, 0.0D, 13.0D, 1.0D, 13.0D), Direction.SOUTH, 
/*  69 */         Block.box(3.0D, 0.0D, 3.0D, 13.0D, 1.0D, 16.0D), Direction.EAST, 
/*  70 */         Block.box(3.0D, 0.0D, 3.0D, 16.0D, 1.0D, 13.0D), Direction.WEST, 
/*  71 */         Block.box(0.0D, 0.0D, 3.0D, 13.0D, 1.0D, 13.0D)));
/*     */   
/*  73 */   private static final Map<Direction, VoxelShape> SHAPES_UP = Maps.newEnumMap((Map)ImmutableMap.of(Direction.NORTH, 
/*  74 */         Shapes.or(SHAPES_FLOOR.get(Direction.NORTH), Block.box(3.0D, 0.0D, 0.0D, 13.0D, 16.0D, 1.0D)), Direction.SOUTH, 
/*  75 */         Shapes.or(SHAPES_FLOOR.get(Direction.SOUTH), Block.box(3.0D, 0.0D, 15.0D, 13.0D, 16.0D, 16.0D)), Direction.EAST, 
/*  76 */         Shapes.or(SHAPES_FLOOR.get(Direction.EAST), Block.box(15.0D, 0.0D, 3.0D, 16.0D, 16.0D, 13.0D)), Direction.WEST, 
/*  77 */         Shapes.or(SHAPES_FLOOR.get(Direction.WEST), Block.box(0.0D, 0.0D, 3.0D, 1.0D, 16.0D, 13.0D))));
/*     */ 
/*     */   
/*  80 */   private static final Map<BlockState, VoxelShape> SHAPES_CACHE = Maps.newHashMap(); private static final Vec3[] COLORS; private static final float PARTICLE_DENSITY = 0.2F; private final BlockState crossState;
/*     */   static {
/*  82 */     COLORS = (Vec3[])Util.make(new Vec3[16], $$0 -> {
/*     */           for (int $$1 = 0; $$1 <= 15; $$1++) {
/*     */             float $$2 = $$1 / 15.0F;
/*     */             float $$3 = $$2 * 0.6F + (($$2 > 0.0F) ? 0.4F : 0.3F);
/*     */             float $$4 = Mth.clamp($$2 * $$2 * 0.7F - 0.5F, 0.0F, 1.0F);
/*     */             float $$5 = Mth.clamp($$2 * $$2 * 0.6F - 0.7F, 0.0F, 1.0F);
/*     */             $$0[$$1] = new Vec3($$3, $$4, $$5);
/*     */           } 
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean shouldSignal = true;
/*     */ 
/*     */   
/*     */   public RedStoneWireBlock(BlockBehaviour.Properties $$0) {
/*  98 */     super($$0);
/*  99 */     registerDefaultState((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)NORTH, (Comparable)RedstoneSide.NONE)).setValue((Property)EAST, (Comparable)RedstoneSide.NONE)).setValue((Property)SOUTH, (Comparable)RedstoneSide.NONE)).setValue((Property)WEST, (Comparable)RedstoneSide.NONE)).setValue((Property)POWER, Integer.valueOf(0)));
/* 100 */     this.crossState = (BlockState)((BlockState)((BlockState)((BlockState)defaultBlockState().setValue((Property)NORTH, (Comparable)RedstoneSide.SIDE)).setValue((Property)EAST, (Comparable)RedstoneSide.SIDE)).setValue((Property)SOUTH, (Comparable)RedstoneSide.SIDE)).setValue((Property)WEST, (Comparable)RedstoneSide.SIDE);
/* 101 */     for (UnmodifiableIterator<BlockState> unmodifiableIterator = getStateDefinition().getPossibleStates().iterator(); unmodifiableIterator.hasNext(); ) { BlockState $$1 = unmodifiableIterator.next();
/* 102 */       if (((Integer)$$1.getValue((Property)POWER)).intValue() == 0) {
/* 103 */         SHAPES_CACHE.put($$1, calculateShape($$1));
/*     */       } }
/*     */   
/*     */   }
/*     */   
/*     */   private VoxelShape calculateShape(BlockState $$0) {
/* 109 */     VoxelShape $$1 = SHAPE_DOT;
/* 110 */     for (Direction $$2 : Direction.Plane.HORIZONTAL) {
/* 111 */       RedstoneSide $$3 = (RedstoneSide)$$0.getValue((Property)PROPERTY_BY_DIRECTION.get($$2));
/* 112 */       if ($$3 == RedstoneSide.SIDE) {
/* 113 */         $$1 = Shapes.or($$1, SHAPES_FLOOR.get($$2)); continue;
/* 114 */       }  if ($$3 == RedstoneSide.UP) {
/* 115 */         $$1 = Shapes.or($$1, SHAPES_UP.get($$2));
/*     */       }
/*     */     } 
/* 118 */     return $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 123 */     return SHAPES_CACHE.get($$0.setValue((Property)POWER, Integer.valueOf(0)));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/* 128 */     return getConnectionState((BlockGetter)$$0.getLevel(), this.crossState, $$0.getClickedPos());
/*     */   }
/*     */   
/*     */   private BlockState getConnectionState(BlockGetter $$0, BlockState $$1, BlockPos $$2) {
/* 132 */     boolean $$3 = isDot($$1);
/* 133 */     $$1 = getMissingConnections($$0, (BlockState)defaultBlockState().setValue((Property)POWER, $$1.getValue((Property)POWER)), $$2);
/*     */ 
/*     */     
/* 136 */     if ($$3 && isDot($$1)) {
/* 137 */       return $$1;
/*     */     }
/*     */     
/* 140 */     boolean $$4 = ((RedstoneSide)$$1.getValue((Property)NORTH)).isConnected();
/* 141 */     boolean $$5 = ((RedstoneSide)$$1.getValue((Property)SOUTH)).isConnected();
/* 142 */     boolean $$6 = ((RedstoneSide)$$1.getValue((Property)EAST)).isConnected();
/* 143 */     boolean $$7 = ((RedstoneSide)$$1.getValue((Property)WEST)).isConnected();
/* 144 */     boolean $$8 = (!$$4 && !$$5);
/* 145 */     boolean $$9 = (!$$6 && !$$7);
/*     */     
/* 147 */     if (!$$7 && $$8) {
/* 148 */       $$1 = (BlockState)$$1.setValue((Property)WEST, (Comparable)RedstoneSide.SIDE);
/*     */     }
/* 150 */     if (!$$6 && $$8) {
/* 151 */       $$1 = (BlockState)$$1.setValue((Property)EAST, (Comparable)RedstoneSide.SIDE);
/*     */     }
/* 153 */     if (!$$4 && $$9) {
/* 154 */       $$1 = (BlockState)$$1.setValue((Property)NORTH, (Comparable)RedstoneSide.SIDE);
/*     */     }
/* 156 */     if (!$$5 && $$9) {
/* 157 */       $$1 = (BlockState)$$1.setValue((Property)SOUTH, (Comparable)RedstoneSide.SIDE);
/*     */     }
/* 159 */     return $$1;
/*     */   }
/*     */   
/*     */   private BlockState getMissingConnections(BlockGetter $$0, BlockState $$1, BlockPos $$2) {
/* 163 */     boolean $$3 = !$$0.getBlockState($$2.above()).isRedstoneConductor($$0, $$2);
/* 164 */     for (Direction $$4 : Direction.Plane.HORIZONTAL) {
/* 165 */       if (!((RedstoneSide)$$1.getValue((Property)PROPERTY_BY_DIRECTION.get($$4))).isConnected()) {
/* 166 */         RedstoneSide $$5 = getConnectingSide($$0, $$2, $$4, $$3);
/* 167 */         $$1 = (BlockState)$$1.setValue((Property)PROPERTY_BY_DIRECTION.get($$4), (Comparable)$$5);
/*     */       } 
/*     */     } 
/* 170 */     return $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/* 175 */     if ($$1 == Direction.DOWN) {
/* 176 */       if (!canSurviveOn((BlockGetter)$$3, $$5, $$2)) {
/* 177 */         return Blocks.AIR.defaultBlockState();
/*     */       }
/* 179 */       return $$0;
/*     */     } 
/* 181 */     if ($$1 == Direction.UP) {
/* 182 */       return getConnectionState((BlockGetter)$$3, $$0, $$4);
/*     */     }
/*     */     
/* 185 */     RedstoneSide $$6 = getConnectingSide((BlockGetter)$$3, $$4, $$1);
/* 186 */     if ($$6.isConnected() == ((RedstoneSide)$$0.getValue((Property)PROPERTY_BY_DIRECTION.get($$1))).isConnected() && !isCross($$0)) {
/* 187 */       return (BlockState)$$0.setValue((Property)PROPERTY_BY_DIRECTION.get($$1), (Comparable)$$6);
/*     */     }
/* 189 */     return getConnectionState((BlockGetter)$$3, (BlockState)((BlockState)this.crossState.setValue((Property)POWER, $$0.getValue((Property)POWER))).setValue((Property)PROPERTY_BY_DIRECTION.get($$1), (Comparable)$$6), $$4);
/*     */   }
/*     */   
/*     */   private static boolean isCross(BlockState $$0) {
/* 193 */     return (((RedstoneSide)$$0.getValue((Property)NORTH)).isConnected() && ((RedstoneSide)$$0.getValue((Property)SOUTH)).isConnected() && ((RedstoneSide)$$0.getValue((Property)EAST)).isConnected() && ((RedstoneSide)$$0.getValue((Property)WEST)).isConnected());
/*     */   }
/*     */   
/*     */   private static boolean isDot(BlockState $$0) {
/* 197 */     return (!((RedstoneSide)$$0.getValue((Property)NORTH)).isConnected() && !((RedstoneSide)$$0.getValue((Property)SOUTH)).isConnected() && !((RedstoneSide)$$0.getValue((Property)EAST)).isConnected() && !((RedstoneSide)$$0.getValue((Property)WEST)).isConnected());
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateIndirectNeighbourShapes(BlockState $$0, LevelAccessor $$1, BlockPos $$2, int $$3, int $$4) {
/* 202 */     BlockPos.MutableBlockPos $$5 = new BlockPos.MutableBlockPos();
/* 203 */     for (Direction $$6 : Direction.Plane.HORIZONTAL) {
/* 204 */       RedstoneSide $$7 = (RedstoneSide)$$0.getValue((Property)PROPERTY_BY_DIRECTION.get($$6));
/* 205 */       if ($$7 != RedstoneSide.NONE && !$$1.getBlockState((BlockPos)$$5.setWithOffset((Vec3i)$$2, $$6)).is(this)) {
/* 206 */         $$5.move(Direction.DOWN);
/* 207 */         BlockState $$8 = $$1.getBlockState((BlockPos)$$5);
/* 208 */         if ($$8.is(this)) {
/* 209 */           BlockPos $$9 = $$5.relative($$6.getOpposite());
/* 210 */           $$1.neighborShapeChanged($$6.getOpposite(), $$1.getBlockState($$9), (BlockPos)$$5, $$9, $$3, $$4);
/*     */         } 
/*     */         
/* 213 */         $$5.setWithOffset((Vec3i)$$2, $$6).move(Direction.UP);
/* 214 */         BlockState $$10 = $$1.getBlockState((BlockPos)$$5);
/* 215 */         if ($$10.is(this)) {
/* 216 */           BlockPos $$11 = $$5.relative($$6.getOpposite());
/* 217 */           $$1.neighborShapeChanged($$6.getOpposite(), $$1.getBlockState($$11), (BlockPos)$$5, $$11, $$3, $$4);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private RedstoneSide getConnectingSide(BlockGetter $$0, BlockPos $$1, Direction $$2) {
/* 224 */     return getConnectingSide($$0, $$1, $$2, !$$0.getBlockState($$1.above()).isRedstoneConductor($$0, $$1));
/*     */   }
/*     */   
/*     */   private RedstoneSide getConnectingSide(BlockGetter $$0, BlockPos $$1, Direction $$2, boolean $$3) {
/* 228 */     BlockPos $$4 = $$1.relative($$2);
/* 229 */     BlockState $$5 = $$0.getBlockState($$4);
/* 230 */     if ($$3) {
/*     */       
/* 232 */       boolean $$6 = ($$5.getBlock() instanceof TrapDoorBlock || canSurviveOn($$0, $$4, $$5));
/* 233 */       if ($$6 && shouldConnectTo($$0.getBlockState($$4.above()))) {
/*     */ 
/*     */         
/* 236 */         if ($$5.isFaceSturdy($$0, $$4, $$2.getOpposite())) {
/* 237 */           return RedstoneSide.UP;
/*     */         }
/* 239 */         return RedstoneSide.SIDE;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 244 */     if (shouldConnectTo($$5, $$2) || (!$$5.isRedstoneConductor($$0, $$4) && shouldConnectTo($$0.getBlockState($$4.below())))) {
/* 245 */       return RedstoneSide.SIDE;
/*     */     }
/* 247 */     return RedstoneSide.NONE;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canSurvive(BlockState $$0, LevelReader $$1, BlockPos $$2) {
/* 252 */     BlockPos $$3 = $$2.below();
/* 253 */     BlockState $$4 = $$1.getBlockState($$3);
/* 254 */     return canSurviveOn((BlockGetter)$$1, $$3, $$4);
/*     */   }
/*     */   
/*     */   private boolean canSurviveOn(BlockGetter $$0, BlockPos $$1, BlockState $$2) {
/* 258 */     return ($$2.isFaceSturdy($$0, $$1, Direction.UP) || $$2.is(Blocks.HOPPER));
/*     */   }
/*     */   
/*     */   private void updatePowerStrength(Level $$0, BlockPos $$1, BlockState $$2) {
/* 262 */     int $$3 = calculateTargetStrength($$0, $$1);
/*     */     
/* 264 */     if (((Integer)$$2.getValue((Property)POWER)).intValue() != $$3) {
/* 265 */       if ($$0.getBlockState($$1) == $$2) {
/* 266 */         $$0.setBlock($$1, (BlockState)$$2.setValue((Property)POWER, Integer.valueOf($$3)), 2);
/*     */       }
/*     */ 
/*     */       
/* 270 */       Set<BlockPos> $$4 = Sets.newHashSet();
/* 271 */       $$4.add($$1);
/* 272 */       for (Direction $$5 : Direction.values()) {
/* 273 */         $$4.add($$1.relative($$5));
/*     */       }
/* 275 */       for (BlockPos $$6 : $$4) {
/* 276 */         $$0.updateNeighborsAt($$6, this);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private int calculateTargetStrength(Level $$0, BlockPos $$1) {
/* 282 */     this.shouldSignal = false;
/* 283 */     int $$2 = $$0.getBestNeighborSignal($$1);
/* 284 */     this.shouldSignal = true;
/*     */     
/* 286 */     int $$3 = 0;
/* 287 */     if ($$2 < 15) {
/* 288 */       for (Direction $$4 : Direction.Plane.HORIZONTAL) {
/* 289 */         BlockPos $$5 = $$1.relative($$4);
/* 290 */         BlockState $$6 = $$0.getBlockState($$5);
/*     */         
/* 292 */         $$3 = Math.max($$3, getWireSignal($$6));
/*     */         
/* 294 */         BlockPos $$7 = $$1.above();
/* 295 */         if ($$6.isRedstoneConductor((BlockGetter)$$0, $$5) && !$$0.getBlockState($$7).isRedstoneConductor((BlockGetter)$$0, $$7)) {
/* 296 */           $$3 = Math.max($$3, getWireSignal($$0.getBlockState($$5.above()))); continue;
/* 297 */         }  if (!$$6.isRedstoneConductor((BlockGetter)$$0, $$5)) {
/* 298 */           $$3 = Math.max($$3, getWireSignal($$0.getBlockState($$5.below())));
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 303 */     return Math.max($$2, $$3 - 1);
/*     */   }
/*     */   
/*     */   private int getWireSignal(BlockState $$0) {
/* 307 */     return $$0.is(this) ? ((Integer)$$0.getValue((Property)POWER)).intValue() : 0;
/*     */   }
/*     */   
/*     */   private void checkCornerChangeAt(Level $$0, BlockPos $$1) {
/* 311 */     if (!$$0.getBlockState($$1).is(this)) {
/*     */       return;
/*     */     }
/*     */     
/* 315 */     $$0.updateNeighborsAt($$1, this);
/* 316 */     for (Direction $$2 : Direction.values()) {
/* 317 */       $$0.updateNeighborsAt($$1.relative($$2), this);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPlace(BlockState $$0, Level $$1, BlockPos $$2, BlockState $$3, boolean $$4) {
/* 323 */     if ($$3.is($$0.getBlock()) || $$1.isClientSide) {
/*     */       return;
/*     */     }
/*     */     
/* 327 */     updatePowerStrength($$1, $$2, $$0);
/*     */     
/* 329 */     for (Direction $$5 : Direction.Plane.VERTICAL) {
/* 330 */       $$1.updateNeighborsAt($$2.relative($$5), this);
/*     */     }
/*     */     
/* 333 */     updateNeighborsOfNeighboringWires($$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRemove(BlockState $$0, Level $$1, BlockPos $$2, BlockState $$3, boolean $$4) {
/* 338 */     if ($$4 || $$0.is($$3.getBlock())) {
/*     */       return;
/*     */     }
/* 341 */     super.onRemove($$0, $$1, $$2, $$3, $$4);
/* 342 */     if ($$1.isClientSide) {
/*     */       return;
/*     */     }
/*     */     
/* 346 */     for (Direction $$5 : Direction.values()) {
/* 347 */       $$1.updateNeighborsAt($$2.relative($$5), this);
/*     */     }
/* 349 */     updatePowerStrength($$1, $$2, $$0);
/*     */     
/* 351 */     updateNeighborsOfNeighboringWires($$1, $$2);
/*     */   }
/*     */   
/*     */   private void updateNeighborsOfNeighboringWires(Level $$0, BlockPos $$1) {
/* 355 */     for (Direction $$2 : Direction.Plane.HORIZONTAL) {
/* 356 */       checkCornerChangeAt($$0, $$1.relative($$2));
/*     */     }
/*     */     
/* 359 */     for (Direction $$3 : Direction.Plane.HORIZONTAL) {
/* 360 */       BlockPos $$4 = $$1.relative($$3);
/*     */       
/* 362 */       if ($$0.getBlockState($$4).isRedstoneConductor((BlockGetter)$$0, $$4)) {
/* 363 */         checkCornerChangeAt($$0, $$4.above()); continue;
/*     */       } 
/* 365 */       checkCornerChangeAt($$0, $$4.below());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void neighborChanged(BlockState $$0, Level $$1, BlockPos $$2, Block $$3, BlockPos $$4, boolean $$5) {
/* 372 */     if ($$1.isClientSide) {
/*     */       return;
/*     */     }
/*     */     
/* 376 */     if ($$0.canSurvive((LevelReader)$$1, $$2)) {
/* 377 */       updatePowerStrength($$1, $$2, $$0);
/*     */     } else {
/* 379 */       dropResources($$0, $$1, $$2);
/* 380 */       $$1.removeBlock($$2, false);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDirectSignal(BlockState $$0, BlockGetter $$1, BlockPos $$2, Direction $$3) {
/* 386 */     if (!this.shouldSignal) {
/* 387 */       return 0;
/*     */     }
/* 389 */     return $$0.getSignal($$1, $$2, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSignal(BlockState $$0, BlockGetter $$1, BlockPos $$2, Direction $$3) {
/* 394 */     if (!this.shouldSignal || $$3 == Direction.DOWN) {
/* 395 */       return 0;
/*     */     }
/* 397 */     int $$4 = ((Integer)$$0.getValue((Property)POWER)).intValue();
/* 398 */     if ($$4 == 0) {
/* 399 */       return 0;
/*     */     }
/*     */     
/* 402 */     if ($$3 == Direction.UP || ((RedstoneSide)getConnectionState($$1, $$0, $$2).getValue((Property)PROPERTY_BY_DIRECTION.get($$3.getOpposite()))).isConnected()) {
/* 403 */       return $$4;
/*     */     }
/* 405 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected static boolean shouldConnectTo(BlockState $$0) {
/* 410 */     return shouldConnectTo($$0, (Direction)null);
/*     */   }
/*     */   
/*     */   protected static boolean shouldConnectTo(BlockState $$0, @Nullable Direction $$1) {
/* 414 */     if ($$0.is(Blocks.REDSTONE_WIRE)) {
/* 415 */       return true;
/*     */     }
/*     */     
/* 418 */     if ($$0.is(Blocks.REPEATER)) {
/* 419 */       Direction $$2 = (Direction)$$0.getValue((Property)RepeaterBlock.FACING);
/* 420 */       return ($$2 == $$1 || $$2.getOpposite() == $$1);
/*     */     } 
/*     */     
/* 423 */     if ($$0.is(Blocks.OBSERVER)) {
/* 424 */       return ($$1 == $$0.getValue((Property)ObserverBlock.FACING));
/*     */     }
/*     */     
/* 427 */     return ($$0.isSignalSource() && $$1 != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSignalSource(BlockState $$0) {
/* 432 */     return this.shouldSignal;
/*     */   }
/*     */   
/*     */   public static int getColorForPower(int $$0) {
/* 436 */     Vec3 $$1 = COLORS[$$0];
/* 437 */     return Mth.color((float)$$1.x(), (float)$$1.y(), (float)$$1.z());
/*     */   }
/*     */   
/*     */   private void spawnParticlesAlongLine(Level $$0, RandomSource $$1, BlockPos $$2, Vec3 $$3, Direction $$4, Direction $$5, float $$6, float $$7) {
/* 441 */     float $$8 = $$7 - $$6;
/* 442 */     if ($$1.nextFloat() >= 0.2F * $$8) {
/*     */       return;
/*     */     }
/* 445 */     float $$9 = 0.4375F;
/* 446 */     float $$10 = $$6 + $$8 * $$1.nextFloat();
/* 447 */     double $$11 = 0.5D + (0.4375F * $$4.getStepX()) + ($$10 * $$5.getStepX());
/* 448 */     double $$12 = 0.5D + (0.4375F * $$4.getStepY()) + ($$10 * $$5.getStepY());
/* 449 */     double $$13 = 0.5D + (0.4375F * $$4.getStepZ()) + ($$10 * $$5.getStepZ());
/* 450 */     $$0.addParticle((ParticleOptions)new DustParticleOptions($$3.toVector3f(), 1.0F), $$2.getX() + $$11, $$2.getY() + $$12, $$2.getZ() + $$13, 0.0D, 0.0D, 0.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public void animateTick(BlockState $$0, Level $$1, BlockPos $$2, RandomSource $$3) {
/* 455 */     int $$4 = ((Integer)$$0.getValue((Property)POWER)).intValue();
/* 456 */     if ($$4 == 0) {
/*     */       return;
/*     */     }
/* 459 */     for (Direction $$5 : Direction.Plane.HORIZONTAL) {
/* 460 */       RedstoneSide $$6 = (RedstoneSide)$$0.getValue((Property)PROPERTY_BY_DIRECTION.get($$5));
/* 461 */       switch ($$6) {
/*     */         case LEFT_RIGHT:
/* 463 */           spawnParticlesAlongLine($$1, $$3, $$2, COLORS[$$4], $$5, Direction.UP, -0.5F, 0.5F);
/*     */         
/*     */         case FRONT_BACK:
/* 466 */           spawnParticlesAlongLine($$1, $$3, $$2, COLORS[$$4], Direction.DOWN, $$5, 0.0F, 0.5F);
/*     */           continue;
/*     */       } 
/*     */       
/* 470 */       spawnParticlesAlongLine($$1, $$3, $$2, COLORS[$$4], Direction.DOWN, $$5, 0.0F, 0.3F);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockState rotate(BlockState $$0, Rotation $$1) {
/* 477 */     switch ($$1) {
/*     */       case LEFT_RIGHT:
/* 479 */         return (BlockState)((BlockState)((BlockState)((BlockState)$$0.setValue((Property)NORTH, $$0.getValue((Property)SOUTH))).setValue((Property)EAST, $$0.getValue((Property)WEST))).setValue((Property)SOUTH, $$0.getValue((Property)NORTH))).setValue((Property)WEST, $$0.getValue((Property)EAST));
/*     */       case FRONT_BACK:
/* 481 */         return (BlockState)((BlockState)((BlockState)((BlockState)$$0.setValue((Property)NORTH, $$0.getValue((Property)EAST))).setValue((Property)EAST, $$0.getValue((Property)SOUTH))).setValue((Property)SOUTH, $$0.getValue((Property)WEST))).setValue((Property)WEST, $$0.getValue((Property)NORTH));
/*     */       case null:
/* 483 */         return (BlockState)((BlockState)((BlockState)((BlockState)$$0.setValue((Property)NORTH, $$0.getValue((Property)WEST))).setValue((Property)EAST, $$0.getValue((Property)NORTH))).setValue((Property)SOUTH, $$0.getValue((Property)EAST))).setValue((Property)WEST, $$0.getValue((Property)SOUTH));
/*     */     } 
/* 485 */     return $$0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockState mirror(BlockState $$0, Mirror $$1) {
/* 491 */     switch ($$1) {
/*     */       case LEFT_RIGHT:
/* 493 */         return (BlockState)((BlockState)$$0.setValue((Property)NORTH, $$0.getValue((Property)SOUTH))).setValue((Property)SOUTH, $$0.getValue((Property)NORTH));
/*     */       case FRONT_BACK:
/* 495 */         return (BlockState)((BlockState)$$0.setValue((Property)EAST, $$0.getValue((Property)WEST))).setValue((Property)WEST, $$0.getValue((Property)EAST));
/*     */     } 
/*     */ 
/*     */     
/* 499 */     return super.mirror($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 504 */     $$0.add(new Property[] { (Property)NORTH, (Property)EAST, (Property)SOUTH, (Property)WEST, (Property)POWER });
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult use(BlockState $$0, Level $$1, BlockPos $$2, Player $$3, InteractionHand $$4, BlockHitResult $$5) {
/* 509 */     if (!($$3.getAbilities()).mayBuild) {
/* 510 */       return InteractionResult.PASS;
/*     */     }
/*     */     
/* 513 */     if (isCross($$0) || isDot($$0)) {
/* 514 */       BlockState $$6 = isCross($$0) ? defaultBlockState() : this.crossState;
/* 515 */       $$6 = (BlockState)$$6.setValue((Property)POWER, $$0.getValue((Property)POWER));
/* 516 */       $$6 = getConnectionState((BlockGetter)$$1, $$6, $$2);
/* 517 */       if ($$6 != $$0) {
/* 518 */         $$1.setBlock($$2, $$6, 3);
/*     */         
/* 520 */         updatesOnShapeChange($$1, $$2, $$0, $$6);
/* 521 */         return InteractionResult.SUCCESS;
/*     */       } 
/*     */     } 
/* 524 */     return InteractionResult.PASS;
/*     */   }
/*     */   
/*     */   private void updatesOnShapeChange(Level $$0, BlockPos $$1, BlockState $$2, BlockState $$3) {
/* 528 */     for (Direction $$4 : Direction.Plane.HORIZONTAL) {
/* 529 */       BlockPos $$5 = $$1.relative($$4);
/* 530 */       if (((RedstoneSide)$$2.getValue((Property)PROPERTY_BY_DIRECTION.get($$4))).isConnected() != ((RedstoneSide)$$3.getValue((Property)PROPERTY_BY_DIRECTION.get($$4))).isConnected() && $$0.getBlockState($$5).isRedstoneConductor((BlockGetter)$$0, $$5))
/* 531 */         $$0.updateNeighborsAtExceptFromFacing($$5, $$3.getBlock(), $$4.getOpposite()); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\RedStoneWireBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */