/*     */ package net.minecraft.world.level.block;
/*     */ 
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.function.Function;
/*     */ import java.util.stream.Collectors;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.tags.BiomeTags;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.item.context.BlockPlaceContext;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.GameRules;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.BooleanProperty;
/*     */ import net.minecraft.world.level.block.state.properties.IntegerProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.Shapes;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class FireBlock extends BaseFireBlock {
/*  35 */   public static final MapCodec<FireBlock> CODEC = simpleCodec(FireBlock::new);
/*     */   public static final int MAX_AGE = 15;
/*     */   
/*     */   public MapCodec<FireBlock> codec() {
/*  39 */     return CODEC;
/*     */   }
/*     */ 
/*     */   
/*  43 */   public static final IntegerProperty AGE = BlockStateProperties.AGE_15;
/*     */   
/*  45 */   public static final BooleanProperty NORTH = PipeBlock.NORTH;
/*  46 */   public static final BooleanProperty EAST = PipeBlock.EAST;
/*  47 */   public static final BooleanProperty SOUTH = PipeBlock.SOUTH;
/*  48 */   public static final BooleanProperty WEST = PipeBlock.WEST; private static final Map<Direction, BooleanProperty> PROPERTY_BY_DIRECTION;
/*  49 */   public static final BooleanProperty UP = PipeBlock.UP;
/*     */   static {
/*  51 */     PROPERTY_BY_DIRECTION = (Map<Direction, BooleanProperty>)PipeBlock.PROPERTY_BY_DIRECTION.entrySet().stream().filter($$0 -> ($$0.getKey() != Direction.DOWN)).collect(Util.toMap());
/*     */   }
/*  53 */   private static final VoxelShape UP_AABB = Block.box(0.0D, 15.0D, 0.0D, 16.0D, 16.0D, 16.0D);
/*  54 */   private static final VoxelShape WEST_AABB = Block.box(0.0D, 0.0D, 0.0D, 1.0D, 16.0D, 16.0D);
/*  55 */   private static final VoxelShape EAST_AABB = Block.box(15.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
/*  56 */   private static final VoxelShape NORTH_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 1.0D);
/*  57 */   private static final VoxelShape SOUTH_AABB = Block.box(0.0D, 0.0D, 15.0D, 16.0D, 16.0D, 16.0D);
/*     */   
/*     */   private final Map<BlockState, VoxelShape> shapesCache;
/*     */   
/*     */   private static final int IGNITE_INSTANT = 60;
/*     */   
/*     */   private static final int IGNITE_EASY = 30;
/*     */   
/*     */   private static final int IGNITE_MEDIUM = 15;
/*     */   
/*     */   private static final int IGNITE_HARD = 5;
/*     */   
/*     */   private static final int BURN_INSTANT = 100;
/*     */   
/*     */   private static final int BURN_EASY = 60;
/*     */   private static final int BURN_MEDIUM = 20;
/*     */   private static final int BURN_HARD = 5;
/*  74 */   private final Object2IntMap<Block> igniteOdds = (Object2IntMap<Block>)new Object2IntOpenHashMap();
/*  75 */   private final Object2IntMap<Block> burnOdds = (Object2IntMap<Block>)new Object2IntOpenHashMap();
/*     */   
/*     */   public FireBlock(BlockBehaviour.Properties $$0) {
/*  78 */     super($$0, 1.0F);
/*  79 */     registerDefaultState((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)AGE, Integer.valueOf(0))).setValue((Property)NORTH, Boolean.valueOf(false))).setValue((Property)EAST, Boolean.valueOf(false))).setValue((Property)SOUTH, Boolean.valueOf(false))).setValue((Property)WEST, Boolean.valueOf(false))).setValue((Property)UP, Boolean.valueOf(false)));
/*     */     
/*  81 */     this.shapesCache = (Map<BlockState, VoxelShape>)ImmutableMap.copyOf((Map)this.stateDefinition.getPossibleStates().stream().filter($$0 -> (((Integer)$$0.getValue((Property)AGE)).intValue() == 0)).collect(Collectors.toMap(Function.identity(), FireBlock::calculateShape)));
/*     */   }
/*     */   
/*     */   private static VoxelShape calculateShape(BlockState $$0) {
/*  85 */     VoxelShape $$1 = Shapes.empty();
/*  86 */     if (((Boolean)$$0.getValue((Property)UP)).booleanValue()) {
/*  87 */       $$1 = UP_AABB;
/*     */     }
/*  89 */     if (((Boolean)$$0.getValue((Property)NORTH)).booleanValue()) {
/*  90 */       $$1 = Shapes.or($$1, NORTH_AABB);
/*     */     }
/*  92 */     if (((Boolean)$$0.getValue((Property)SOUTH)).booleanValue()) {
/*  93 */       $$1 = Shapes.or($$1, SOUTH_AABB);
/*     */     }
/*  95 */     if (((Boolean)$$0.getValue((Property)EAST)).booleanValue()) {
/*  96 */       $$1 = Shapes.or($$1, EAST_AABB);
/*     */     }
/*  98 */     if (((Boolean)$$0.getValue((Property)WEST)).booleanValue()) {
/*  99 */       $$1 = Shapes.or($$1, WEST_AABB);
/*     */     }
/* 101 */     return $$1.isEmpty() ? DOWN_AABB : $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/* 106 */     if (canSurvive($$0, (LevelReader)$$3, $$4)) {
/* 107 */       return getStateWithAge($$3, $$4, ((Integer)$$0.getValue((Property)AGE)).intValue());
/*     */     }
/*     */     
/* 110 */     return Blocks.AIR.defaultBlockState();
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 115 */     return this.shapesCache.get($$0.setValue((Property)AGE, Integer.valueOf(0)));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/* 120 */     return getStateForPlacement((BlockGetter)$$0.getLevel(), $$0.getClickedPos());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected BlockState getStateForPlacement(BlockGetter $$0, BlockPos $$1) {
/* 126 */     BlockPos $$2 = $$1.below();
/* 127 */     BlockState $$3 = $$0.getBlockState($$2);
/* 128 */     if (canBurn($$3) || $$3.isFaceSturdy($$0, $$2, Direction.UP)) {
/* 129 */       return defaultBlockState();
/*     */     }
/*     */     
/* 132 */     BlockState $$4 = defaultBlockState();
/* 133 */     for (Direction $$5 : Direction.values()) {
/* 134 */       BooleanProperty $$6 = PROPERTY_BY_DIRECTION.get($$5);
/* 135 */       if ($$6 != null) {
/* 136 */         $$4 = (BlockState)$$4.setValue((Property)$$6, Boolean.valueOf(canBurn($$0.getBlockState($$1.relative($$5)))));
/*     */       }
/*     */     } 
/*     */     
/* 140 */     return $$4;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canSurvive(BlockState $$0, LevelReader $$1, BlockPos $$2) {
/* 145 */     BlockPos $$3 = $$2.below();
/* 146 */     return ($$1.getBlockState($$3).isFaceSturdy((BlockGetter)$$1, $$3, Direction.UP) || isValidFireLocation((BlockGetter)$$1, $$2));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void tick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/* 152 */     $$1.scheduleTick($$2, this, getFireTickDelay($$1.random));
/*     */     
/* 154 */     if (!$$1.getGameRules().getBoolean(GameRules.RULE_DOFIRETICK)) {
/*     */       return;
/*     */     }
/*     */     
/* 158 */     if (!$$0.canSurvive((LevelReader)$$1, $$2)) {
/* 159 */       $$1.removeBlock($$2, false);
/*     */     }
/*     */     
/* 162 */     BlockState $$4 = $$1.getBlockState($$2.below());
/* 163 */     boolean $$5 = $$4.is($$1.dimensionType().infiniburn());
/*     */     
/* 165 */     int $$6 = ((Integer)$$0.getValue((Property)AGE)).intValue();
/* 166 */     if (!$$5 && $$1.isRaining() && isNearRain((Level)$$1, $$2) && $$3.nextFloat() < 0.2F + $$6 * 0.03F) {
/* 167 */       $$1.removeBlock($$2, false);
/*     */       
/*     */       return;
/*     */     } 
/* 171 */     int $$7 = Math.min(15, $$6 + $$3.nextInt(3) / 2);
/* 172 */     if ($$6 != $$7) {
/* 173 */       $$0 = (BlockState)$$0.setValue((Property)AGE, Integer.valueOf($$7));
/* 174 */       $$1.setBlock($$2, $$0, 4);
/*     */     } 
/*     */     
/* 177 */     if (!$$5) {
/* 178 */       if (!isValidFireLocation((BlockGetter)$$1, $$2)) {
/* 179 */         BlockPos $$8 = $$2.below();
/* 180 */         if (!$$1.getBlockState($$8).isFaceSturdy((BlockGetter)$$1, $$8, Direction.UP) || $$6 > 3) {
/* 181 */           $$1.removeBlock($$2, false);
/*     */         }
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 187 */       if ($$6 == 15 && $$3.nextInt(4) == 0 && !canBurn($$1.getBlockState($$2.below()))) {
/* 188 */         $$1.removeBlock($$2, false);
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 193 */     boolean $$9 = $$1.getBiome($$2).is(BiomeTags.INCREASED_FIRE_BURNOUT);
/* 194 */     int $$10 = $$9 ? -50 : 0;
/*     */     
/* 196 */     checkBurnOut((Level)$$1, $$2.east(), 300 + $$10, $$3, $$6);
/* 197 */     checkBurnOut((Level)$$1, $$2.west(), 300 + $$10, $$3, $$6);
/* 198 */     checkBurnOut((Level)$$1, $$2.below(), 250 + $$10, $$3, $$6);
/* 199 */     checkBurnOut((Level)$$1, $$2.above(), 250 + $$10, $$3, $$6);
/* 200 */     checkBurnOut((Level)$$1, $$2.north(), 300 + $$10, $$3, $$6);
/* 201 */     checkBurnOut((Level)$$1, $$2.south(), 300 + $$10, $$3, $$6);
/*     */     
/* 203 */     BlockPos.MutableBlockPos $$11 = new BlockPos.MutableBlockPos();
/* 204 */     for (int $$12 = -1; $$12 <= 1; $$12++) {
/* 205 */       for (int $$13 = -1; $$13 <= 1; $$13++) {
/* 206 */         for (int $$14 = -1; $$14 <= 4; $$14++) {
/* 207 */           if ($$12 != 0 || $$14 != 0 || $$13 != 0) {
/*     */ 
/*     */ 
/*     */             
/* 211 */             int $$15 = 100;
/* 212 */             if ($$14 > 1) {
/* 213 */               $$15 += ($$14 - 1) * 100;
/*     */             }
/*     */             
/* 216 */             $$11.setWithOffset((Vec3i)$$2, $$12, $$14, $$13);
/* 217 */             int $$16 = getIgniteOdds((LevelReader)$$1, (BlockPos)$$11);
/* 218 */             if ($$16 > 0) {
/*     */ 
/*     */ 
/*     */               
/* 222 */               int $$17 = ($$16 + 40 + $$1.getDifficulty().getId() * 7) / ($$6 + 30);
/* 223 */               if ($$9) {
/* 224 */                 $$17 /= 2;
/*     */               }
/* 226 */               if ($$17 > 0 && $$3.nextInt($$15) <= $$17 && (
/* 227 */                 !$$1.isRaining() || !isNearRain((Level)$$1, (BlockPos)$$11))) {
/*     */ 
/*     */ 
/*     */                 
/* 231 */                 int $$18 = Math.min(15, $$6 + $$3.nextInt(5) / 4);
/* 232 */                 $$1.setBlock((BlockPos)$$11, getStateWithAge((LevelAccessor)$$1, (BlockPos)$$11, $$18), 3);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   } protected boolean isNearRain(Level $$0, BlockPos $$1) {
/* 240 */     return ($$0.isRainingAt($$1) || $$0.isRainingAt($$1.west()) || $$0.isRainingAt($$1.east()) || $$0.isRainingAt($$1.north()) || $$0.isRainingAt($$1.south()));
/*     */   }
/*     */   
/*     */   private int getBurnOdds(BlockState $$0) {
/* 244 */     if ($$0.hasProperty((Property)BlockStateProperties.WATERLOGGED) && ((Boolean)$$0.getValue((Property)BlockStateProperties.WATERLOGGED)).booleanValue()) {
/* 245 */       return 0;
/*     */     }
/* 247 */     return this.burnOdds.getInt($$0.getBlock());
/*     */   }
/*     */   
/*     */   private int getIgniteOdds(BlockState $$0) {
/* 251 */     if ($$0.hasProperty((Property)BlockStateProperties.WATERLOGGED) && ((Boolean)$$0.getValue((Property)BlockStateProperties.WATERLOGGED)).booleanValue()) {
/* 252 */       return 0;
/*     */     }
/* 254 */     return this.igniteOdds.getInt($$0.getBlock());
/*     */   }
/*     */   
/*     */   private void checkBurnOut(Level $$0, BlockPos $$1, int $$2, RandomSource $$3, int $$4) {
/* 258 */     int $$5 = getBurnOdds($$0.getBlockState($$1));
/* 259 */     if ($$3.nextInt($$2) < $$5) {
/* 260 */       BlockState $$6 = $$0.getBlockState($$1);
/*     */       
/* 262 */       if ($$3.nextInt($$4 + 10) < 5 && !$$0.isRainingAt($$1)) {
/* 263 */         int $$7 = Math.min($$4 + $$3.nextInt(5) / 4, 15);
/* 264 */         $$0.setBlock($$1, getStateWithAge((LevelAccessor)$$0, $$1, $$7), 3);
/*     */       } else {
/* 266 */         $$0.removeBlock($$1, false);
/*     */       } 
/*     */       
/* 269 */       Block $$8 = $$6.getBlock();
/* 270 */       if ($$8 instanceof TntBlock) {
/* 271 */         TntBlock.explode($$0, $$1);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private BlockState getStateWithAge(LevelAccessor $$0, BlockPos $$1, int $$2) {
/* 277 */     BlockState $$3 = getState((BlockGetter)$$0, $$1);
/* 278 */     if ($$3.is(Blocks.FIRE)) {
/* 279 */       return (BlockState)$$3.setValue((Property)AGE, Integer.valueOf($$2));
/*     */     }
/*     */     
/* 282 */     return $$3;
/*     */   }
/*     */   
/*     */   private boolean isValidFireLocation(BlockGetter $$0, BlockPos $$1) {
/* 286 */     for (Direction $$2 : Direction.values()) {
/* 287 */       if (canBurn($$0.getBlockState($$1.relative($$2)))) {
/* 288 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 292 */     return false;
/*     */   }
/*     */   
/*     */   private int getIgniteOdds(LevelReader $$0, BlockPos $$1) {
/* 296 */     if (!$$0.isEmptyBlock($$1)) {
/* 297 */       return 0;
/*     */     }
/*     */     
/* 300 */     int $$2 = 0;
/* 301 */     for (Direction $$3 : Direction.values()) {
/* 302 */       BlockState $$4 = $$0.getBlockState($$1.relative($$3));
/* 303 */       $$2 = Math.max(getIgniteOdds($$4), $$2);
/*     */     } 
/*     */     
/* 306 */     return $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canBurn(BlockState $$0) {
/* 311 */     return (getIgniteOdds($$0) > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPlace(BlockState $$0, Level $$1, BlockPos $$2, BlockState $$3, boolean $$4) {
/* 316 */     super.onPlace($$0, $$1, $$2, $$3, $$4);
/*     */     
/* 318 */     $$1.scheduleTick($$2, this, getFireTickDelay($$1.random));
/*     */   }
/*     */   
/*     */   private static int getFireTickDelay(RandomSource $$0) {
/* 322 */     return 30 + $$0.nextInt(10);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 327 */     $$0.add(new Property[] { (Property)AGE, (Property)NORTH, (Property)EAST, (Property)SOUTH, (Property)WEST, (Property)UP });
/*     */   }
/*     */   
/*     */   public void setFlammable(Block $$0, int $$1, int $$2) {
/* 331 */     this.igniteOdds.put($$0, $$1);
/* 332 */     this.burnOdds.put($$0, $$2);
/*     */   }
/*     */   
/*     */   public static void bootStrap() {
/* 336 */     FireBlock $$0 = (FireBlock)Blocks.FIRE;
/* 337 */     $$0.setFlammable(Blocks.OAK_PLANKS, 5, 20);
/* 338 */     $$0.setFlammable(Blocks.SPRUCE_PLANKS, 5, 20);
/* 339 */     $$0.setFlammable(Blocks.BIRCH_PLANKS, 5, 20);
/* 340 */     $$0.setFlammable(Blocks.JUNGLE_PLANKS, 5, 20);
/* 341 */     $$0.setFlammable(Blocks.ACACIA_PLANKS, 5, 20);
/* 342 */     $$0.setFlammable(Blocks.CHERRY_PLANKS, 5, 20);
/* 343 */     $$0.setFlammable(Blocks.DARK_OAK_PLANKS, 5, 20);
/* 344 */     $$0.setFlammable(Blocks.MANGROVE_PLANKS, 5, 20);
/* 345 */     $$0.setFlammable(Blocks.BAMBOO_PLANKS, 5, 20);
/* 346 */     $$0.setFlammable(Blocks.BAMBOO_MOSAIC, 5, 20);
/* 347 */     $$0.setFlammable(Blocks.OAK_SLAB, 5, 20);
/* 348 */     $$0.setFlammable(Blocks.SPRUCE_SLAB, 5, 20);
/* 349 */     $$0.setFlammable(Blocks.BIRCH_SLAB, 5, 20);
/* 350 */     $$0.setFlammable(Blocks.JUNGLE_SLAB, 5, 20);
/* 351 */     $$0.setFlammable(Blocks.ACACIA_SLAB, 5, 20);
/* 352 */     $$0.setFlammable(Blocks.CHERRY_SLAB, 5, 20);
/* 353 */     $$0.setFlammable(Blocks.DARK_OAK_SLAB, 5, 20);
/* 354 */     $$0.setFlammable(Blocks.MANGROVE_SLAB, 5, 20);
/* 355 */     $$0.setFlammable(Blocks.BAMBOO_SLAB, 5, 20);
/* 356 */     $$0.setFlammable(Blocks.BAMBOO_MOSAIC_SLAB, 5, 20);
/* 357 */     $$0.setFlammable(Blocks.OAK_FENCE_GATE, 5, 20);
/* 358 */     $$0.setFlammable(Blocks.SPRUCE_FENCE_GATE, 5, 20);
/* 359 */     $$0.setFlammable(Blocks.BIRCH_FENCE_GATE, 5, 20);
/* 360 */     $$0.setFlammable(Blocks.JUNGLE_FENCE_GATE, 5, 20);
/* 361 */     $$0.setFlammable(Blocks.ACACIA_FENCE_GATE, 5, 20);
/* 362 */     $$0.setFlammable(Blocks.CHERRY_FENCE_GATE, 5, 20);
/* 363 */     $$0.setFlammable(Blocks.DARK_OAK_FENCE_GATE, 5, 20);
/* 364 */     $$0.setFlammable(Blocks.MANGROVE_FENCE_GATE, 5, 20);
/* 365 */     $$0.setFlammable(Blocks.BAMBOO_FENCE_GATE, 5, 20);
/* 366 */     $$0.setFlammable(Blocks.OAK_FENCE, 5, 20);
/* 367 */     $$0.setFlammable(Blocks.SPRUCE_FENCE, 5, 20);
/* 368 */     $$0.setFlammable(Blocks.BIRCH_FENCE, 5, 20);
/* 369 */     $$0.setFlammable(Blocks.JUNGLE_FENCE, 5, 20);
/* 370 */     $$0.setFlammable(Blocks.ACACIA_FENCE, 5, 20);
/* 371 */     $$0.setFlammable(Blocks.CHERRY_FENCE, 5, 20);
/* 372 */     $$0.setFlammable(Blocks.DARK_OAK_FENCE, 5, 20);
/* 373 */     $$0.setFlammable(Blocks.MANGROVE_FENCE, 5, 20);
/* 374 */     $$0.setFlammable(Blocks.BAMBOO_FENCE, 5, 20);
/* 375 */     $$0.setFlammable(Blocks.OAK_STAIRS, 5, 20);
/* 376 */     $$0.setFlammable(Blocks.BIRCH_STAIRS, 5, 20);
/* 377 */     $$0.setFlammable(Blocks.SPRUCE_STAIRS, 5, 20);
/* 378 */     $$0.setFlammable(Blocks.JUNGLE_STAIRS, 5, 20);
/* 379 */     $$0.setFlammable(Blocks.ACACIA_STAIRS, 5, 20);
/* 380 */     $$0.setFlammable(Blocks.CHERRY_STAIRS, 5, 20);
/* 381 */     $$0.setFlammable(Blocks.DARK_OAK_STAIRS, 5, 20);
/* 382 */     $$0.setFlammable(Blocks.MANGROVE_STAIRS, 5, 20);
/* 383 */     $$0.setFlammable(Blocks.BAMBOO_STAIRS, 5, 20);
/* 384 */     $$0.setFlammable(Blocks.BAMBOO_MOSAIC_STAIRS, 5, 20);
/* 385 */     $$0.setFlammable(Blocks.OAK_LOG, 5, 5);
/* 386 */     $$0.setFlammable(Blocks.SPRUCE_LOG, 5, 5);
/* 387 */     $$0.setFlammable(Blocks.BIRCH_LOG, 5, 5);
/* 388 */     $$0.setFlammable(Blocks.JUNGLE_LOG, 5, 5);
/* 389 */     $$0.setFlammable(Blocks.ACACIA_LOG, 5, 5);
/* 390 */     $$0.setFlammable(Blocks.CHERRY_LOG, 5, 5);
/* 391 */     $$0.setFlammable(Blocks.DARK_OAK_LOG, 5, 5);
/* 392 */     $$0.setFlammable(Blocks.MANGROVE_LOG, 5, 5);
/* 393 */     $$0.setFlammable(Blocks.BAMBOO_BLOCK, 5, 5);
/* 394 */     $$0.setFlammable(Blocks.STRIPPED_OAK_LOG, 5, 5);
/* 395 */     $$0.setFlammable(Blocks.STRIPPED_SPRUCE_LOG, 5, 5);
/* 396 */     $$0.setFlammable(Blocks.STRIPPED_BIRCH_LOG, 5, 5);
/* 397 */     $$0.setFlammable(Blocks.STRIPPED_JUNGLE_LOG, 5, 5);
/* 398 */     $$0.setFlammable(Blocks.STRIPPED_ACACIA_LOG, 5, 5);
/* 399 */     $$0.setFlammable(Blocks.STRIPPED_CHERRY_LOG, 5, 5);
/* 400 */     $$0.setFlammable(Blocks.STRIPPED_DARK_OAK_LOG, 5, 5);
/* 401 */     $$0.setFlammable(Blocks.STRIPPED_MANGROVE_LOG, 5, 5);
/* 402 */     $$0.setFlammable(Blocks.STRIPPED_BAMBOO_BLOCK, 5, 5);
/* 403 */     $$0.setFlammable(Blocks.STRIPPED_OAK_WOOD, 5, 5);
/* 404 */     $$0.setFlammable(Blocks.STRIPPED_SPRUCE_WOOD, 5, 5);
/* 405 */     $$0.setFlammable(Blocks.STRIPPED_BIRCH_WOOD, 5, 5);
/* 406 */     $$0.setFlammable(Blocks.STRIPPED_JUNGLE_WOOD, 5, 5);
/* 407 */     $$0.setFlammable(Blocks.STRIPPED_ACACIA_WOOD, 5, 5);
/* 408 */     $$0.setFlammable(Blocks.STRIPPED_CHERRY_WOOD, 5, 5);
/* 409 */     $$0.setFlammable(Blocks.STRIPPED_DARK_OAK_WOOD, 5, 5);
/* 410 */     $$0.setFlammable(Blocks.STRIPPED_MANGROVE_WOOD, 5, 5);
/* 411 */     $$0.setFlammable(Blocks.OAK_WOOD, 5, 5);
/* 412 */     $$0.setFlammable(Blocks.SPRUCE_WOOD, 5, 5);
/* 413 */     $$0.setFlammable(Blocks.BIRCH_WOOD, 5, 5);
/* 414 */     $$0.setFlammable(Blocks.JUNGLE_WOOD, 5, 5);
/* 415 */     $$0.setFlammable(Blocks.ACACIA_WOOD, 5, 5);
/* 416 */     $$0.setFlammable(Blocks.CHERRY_WOOD, 5, 5);
/* 417 */     $$0.setFlammable(Blocks.DARK_OAK_WOOD, 5, 5);
/* 418 */     $$0.setFlammable(Blocks.MANGROVE_WOOD, 5, 5);
/* 419 */     $$0.setFlammable(Blocks.MANGROVE_ROOTS, 5, 20);
/* 420 */     $$0.setFlammable(Blocks.OAK_LEAVES, 30, 60);
/* 421 */     $$0.setFlammable(Blocks.SPRUCE_LEAVES, 30, 60);
/* 422 */     $$0.setFlammable(Blocks.BIRCH_LEAVES, 30, 60);
/* 423 */     $$0.setFlammable(Blocks.JUNGLE_LEAVES, 30, 60);
/* 424 */     $$0.setFlammable(Blocks.ACACIA_LEAVES, 30, 60);
/* 425 */     $$0.setFlammable(Blocks.CHERRY_LEAVES, 30, 60);
/* 426 */     $$0.setFlammable(Blocks.DARK_OAK_LEAVES, 30, 60);
/* 427 */     $$0.setFlammable(Blocks.MANGROVE_LEAVES, 30, 60);
/* 428 */     $$0.setFlammable(Blocks.BOOKSHELF, 30, 20);
/* 429 */     $$0.setFlammable(Blocks.TNT, 15, 100);
/* 430 */     $$0.setFlammable(Blocks.SHORT_GRASS, 60, 100);
/* 431 */     $$0.setFlammable(Blocks.FERN, 60, 100);
/* 432 */     $$0.setFlammable(Blocks.DEAD_BUSH, 60, 100);
/* 433 */     $$0.setFlammable(Blocks.SUNFLOWER, 60, 100);
/* 434 */     $$0.setFlammable(Blocks.LILAC, 60, 100);
/* 435 */     $$0.setFlammable(Blocks.ROSE_BUSH, 60, 100);
/* 436 */     $$0.setFlammable(Blocks.PEONY, 60, 100);
/* 437 */     $$0.setFlammable(Blocks.TALL_GRASS, 60, 100);
/* 438 */     $$0.setFlammable(Blocks.LARGE_FERN, 60, 100);
/* 439 */     $$0.setFlammable(Blocks.DANDELION, 60, 100);
/* 440 */     $$0.setFlammable(Blocks.POPPY, 60, 100);
/* 441 */     $$0.setFlammable(Blocks.BLUE_ORCHID, 60, 100);
/* 442 */     $$0.setFlammable(Blocks.ALLIUM, 60, 100);
/* 443 */     $$0.setFlammable(Blocks.AZURE_BLUET, 60, 100);
/* 444 */     $$0.setFlammable(Blocks.RED_TULIP, 60, 100);
/* 445 */     $$0.setFlammable(Blocks.ORANGE_TULIP, 60, 100);
/* 446 */     $$0.setFlammable(Blocks.WHITE_TULIP, 60, 100);
/* 447 */     $$0.setFlammable(Blocks.PINK_TULIP, 60, 100);
/* 448 */     $$0.setFlammable(Blocks.OXEYE_DAISY, 60, 100);
/* 449 */     $$0.setFlammable(Blocks.CORNFLOWER, 60, 100);
/* 450 */     $$0.setFlammable(Blocks.LILY_OF_THE_VALLEY, 60, 100);
/* 451 */     $$0.setFlammable(Blocks.TORCHFLOWER, 60, 100);
/* 452 */     $$0.setFlammable(Blocks.PITCHER_PLANT, 60, 100);
/* 453 */     $$0.setFlammable(Blocks.WITHER_ROSE, 60, 100);
/* 454 */     $$0.setFlammable(Blocks.PINK_PETALS, 60, 100);
/* 455 */     $$0.setFlammable(Blocks.WHITE_WOOL, 30, 60);
/* 456 */     $$0.setFlammable(Blocks.ORANGE_WOOL, 30, 60);
/* 457 */     $$0.setFlammable(Blocks.MAGENTA_WOOL, 30, 60);
/* 458 */     $$0.setFlammable(Blocks.LIGHT_BLUE_WOOL, 30, 60);
/* 459 */     $$0.setFlammable(Blocks.YELLOW_WOOL, 30, 60);
/* 460 */     $$0.setFlammable(Blocks.LIME_WOOL, 30, 60);
/* 461 */     $$0.setFlammable(Blocks.PINK_WOOL, 30, 60);
/* 462 */     $$0.setFlammable(Blocks.GRAY_WOOL, 30, 60);
/* 463 */     $$0.setFlammable(Blocks.LIGHT_GRAY_WOOL, 30, 60);
/* 464 */     $$0.setFlammable(Blocks.CYAN_WOOL, 30, 60);
/* 465 */     $$0.setFlammable(Blocks.PURPLE_WOOL, 30, 60);
/* 466 */     $$0.setFlammable(Blocks.BLUE_WOOL, 30, 60);
/* 467 */     $$0.setFlammable(Blocks.BROWN_WOOL, 30, 60);
/* 468 */     $$0.setFlammable(Blocks.GREEN_WOOL, 30, 60);
/* 469 */     $$0.setFlammable(Blocks.RED_WOOL, 30, 60);
/* 470 */     $$0.setFlammable(Blocks.BLACK_WOOL, 30, 60);
/* 471 */     $$0.setFlammable(Blocks.VINE, 15, 100);
/* 472 */     $$0.setFlammable(Blocks.COAL_BLOCK, 5, 5);
/* 473 */     $$0.setFlammable(Blocks.HAY_BLOCK, 60, 20);
/* 474 */     $$0.setFlammable(Blocks.TARGET, 15, 20);
/* 475 */     $$0.setFlammable(Blocks.WHITE_CARPET, 60, 20);
/* 476 */     $$0.setFlammable(Blocks.ORANGE_CARPET, 60, 20);
/* 477 */     $$0.setFlammable(Blocks.MAGENTA_CARPET, 60, 20);
/* 478 */     $$0.setFlammable(Blocks.LIGHT_BLUE_CARPET, 60, 20);
/* 479 */     $$0.setFlammable(Blocks.YELLOW_CARPET, 60, 20);
/* 480 */     $$0.setFlammable(Blocks.LIME_CARPET, 60, 20);
/* 481 */     $$0.setFlammable(Blocks.PINK_CARPET, 60, 20);
/* 482 */     $$0.setFlammable(Blocks.GRAY_CARPET, 60, 20);
/* 483 */     $$0.setFlammable(Blocks.LIGHT_GRAY_CARPET, 60, 20);
/* 484 */     $$0.setFlammable(Blocks.CYAN_CARPET, 60, 20);
/* 485 */     $$0.setFlammable(Blocks.PURPLE_CARPET, 60, 20);
/* 486 */     $$0.setFlammable(Blocks.BLUE_CARPET, 60, 20);
/* 487 */     $$0.setFlammable(Blocks.BROWN_CARPET, 60, 20);
/* 488 */     $$0.setFlammable(Blocks.GREEN_CARPET, 60, 20);
/* 489 */     $$0.setFlammable(Blocks.RED_CARPET, 60, 20);
/* 490 */     $$0.setFlammable(Blocks.BLACK_CARPET, 60, 20);
/* 491 */     $$0.setFlammable(Blocks.DRIED_KELP_BLOCK, 30, 60);
/* 492 */     $$0.setFlammable(Blocks.BAMBOO, 60, 60);
/* 493 */     $$0.setFlammable(Blocks.SCAFFOLDING, 60, 60);
/* 494 */     $$0.setFlammable(Blocks.LECTERN, 30, 20);
/* 495 */     $$0.setFlammable(Blocks.COMPOSTER, 5, 20);
/* 496 */     $$0.setFlammable(Blocks.SWEET_BERRY_BUSH, 60, 100);
/* 497 */     $$0.setFlammable(Blocks.BEEHIVE, 5, 20);
/* 498 */     $$0.setFlammable(Blocks.BEE_NEST, 30, 20);
/* 499 */     $$0.setFlammable(Blocks.AZALEA_LEAVES, 30, 60);
/* 500 */     $$0.setFlammable(Blocks.FLOWERING_AZALEA_LEAVES, 30, 60);
/* 501 */     $$0.setFlammable(Blocks.CAVE_VINES, 15, 60);
/* 502 */     $$0.setFlammable(Blocks.CAVE_VINES_PLANT, 15, 60);
/* 503 */     $$0.setFlammable(Blocks.SPORE_BLOSSOM, 60, 100);
/* 504 */     $$0.setFlammable(Blocks.AZALEA, 30, 60);
/* 505 */     $$0.setFlammable(Blocks.FLOWERING_AZALEA, 30, 60);
/* 506 */     $$0.setFlammable(Blocks.BIG_DRIPLEAF, 60, 100);
/* 507 */     $$0.setFlammable(Blocks.BIG_DRIPLEAF_STEM, 60, 100);
/* 508 */     $$0.setFlammable(Blocks.SMALL_DRIPLEAF, 60, 100);
/* 509 */     $$0.setFlammable(Blocks.HANGING_ROOTS, 30, 60);
/* 510 */     $$0.setFlammable(Blocks.GLOW_LICHEN, 15, 100);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\FireBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */