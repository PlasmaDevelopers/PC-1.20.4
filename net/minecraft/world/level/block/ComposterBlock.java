/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import it.unimi.dsi.fastutil.objects.Object2FloatMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.stats.Stats;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.SimpleContainer;
/*     */ import net.minecraft.world.WorldlyContainer;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.item.ItemEntity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import net.minecraft.world.phys.shapes.BooleanOp;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.Shapes;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class ComposterBlock extends Block implements WorldlyContainerHolder {
/*  45 */   public static final MapCodec<ComposterBlock> CODEC = simpleCodec(ComposterBlock::new); public static final int READY = 8; public static final int MIN_LEVEL = 0;
/*     */   public static final int MAX_LEVEL = 7;
/*     */   
/*     */   public MapCodec<ComposterBlock> codec() {
/*  49 */     return CODEC;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  55 */   public static final IntegerProperty LEVEL = BlockStateProperties.LEVEL_COMPOSTER;
/*     */   
/*  57 */   public static final Object2FloatMap<ItemLike> COMPOSTABLES = (Object2FloatMap<ItemLike>)new Object2FloatOpenHashMap(); private static final int AABB_SIDE_THICKNESS = 2;
/*     */   
/*     */   public static void bootStrap() {
/*  60 */     COMPOSTABLES.defaultReturnValue(-1.0F);
/*     */     
/*  62 */     float $$0 = 0.3F;
/*  63 */     float $$1 = 0.5F;
/*  64 */     float $$2 = 0.65F;
/*  65 */     float $$3 = 0.85F;
/*  66 */     float $$4 = 1.0F;
/*     */     
/*  68 */     add(0.3F, (ItemLike)Items.JUNGLE_LEAVES);
/*  69 */     add(0.3F, (ItemLike)Items.OAK_LEAVES);
/*  70 */     add(0.3F, (ItemLike)Items.SPRUCE_LEAVES);
/*  71 */     add(0.3F, (ItemLike)Items.DARK_OAK_LEAVES);
/*  72 */     add(0.3F, (ItemLike)Items.ACACIA_LEAVES);
/*  73 */     add(0.3F, (ItemLike)Items.CHERRY_LEAVES);
/*  74 */     add(0.3F, (ItemLike)Items.BIRCH_LEAVES);
/*  75 */     add(0.3F, (ItemLike)Items.AZALEA_LEAVES);
/*  76 */     add(0.3F, (ItemLike)Items.MANGROVE_LEAVES);
/*  77 */     add(0.3F, (ItemLike)Items.OAK_SAPLING);
/*  78 */     add(0.3F, (ItemLike)Items.SPRUCE_SAPLING);
/*  79 */     add(0.3F, (ItemLike)Items.BIRCH_SAPLING);
/*  80 */     add(0.3F, (ItemLike)Items.JUNGLE_SAPLING);
/*  81 */     add(0.3F, (ItemLike)Items.ACACIA_SAPLING);
/*  82 */     add(0.3F, (ItemLike)Items.CHERRY_SAPLING);
/*  83 */     add(0.3F, (ItemLike)Items.DARK_OAK_SAPLING);
/*  84 */     add(0.3F, (ItemLike)Items.MANGROVE_PROPAGULE);
/*  85 */     add(0.3F, (ItemLike)Items.BEETROOT_SEEDS);
/*  86 */     add(0.3F, (ItemLike)Items.DRIED_KELP);
/*  87 */     add(0.3F, (ItemLike)Items.SHORT_GRASS);
/*  88 */     add(0.3F, (ItemLike)Items.KELP);
/*  89 */     add(0.3F, (ItemLike)Items.MELON_SEEDS);
/*  90 */     add(0.3F, (ItemLike)Items.PUMPKIN_SEEDS);
/*  91 */     add(0.3F, (ItemLike)Items.SEAGRASS);
/*  92 */     add(0.3F, (ItemLike)Items.SWEET_BERRIES);
/*  93 */     add(0.3F, (ItemLike)Items.GLOW_BERRIES);
/*  94 */     add(0.3F, (ItemLike)Items.WHEAT_SEEDS);
/*  95 */     add(0.3F, (ItemLike)Items.MOSS_CARPET);
/*  96 */     add(0.3F, (ItemLike)Items.PINK_PETALS);
/*  97 */     add(0.3F, (ItemLike)Items.SMALL_DRIPLEAF);
/*  98 */     add(0.3F, (ItemLike)Items.HANGING_ROOTS);
/*  99 */     add(0.3F, (ItemLike)Items.MANGROVE_ROOTS);
/* 100 */     add(0.3F, (ItemLike)Items.TORCHFLOWER_SEEDS);
/* 101 */     add(0.3F, (ItemLike)Items.PITCHER_POD);
/*     */     
/* 103 */     add(0.5F, (ItemLike)Items.DRIED_KELP_BLOCK);
/* 104 */     add(0.5F, (ItemLike)Items.TALL_GRASS);
/* 105 */     add(0.5F, (ItemLike)Items.FLOWERING_AZALEA_LEAVES);
/* 106 */     add(0.5F, (ItemLike)Items.CACTUS);
/* 107 */     add(0.5F, (ItemLike)Items.SUGAR_CANE);
/* 108 */     add(0.5F, (ItemLike)Items.VINE);
/* 109 */     add(0.5F, (ItemLike)Items.NETHER_SPROUTS);
/* 110 */     add(0.5F, (ItemLike)Items.WEEPING_VINES);
/* 111 */     add(0.5F, (ItemLike)Items.TWISTING_VINES);
/* 112 */     add(0.5F, (ItemLike)Items.MELON_SLICE);
/* 113 */     add(0.5F, (ItemLike)Items.GLOW_LICHEN);
/*     */     
/* 115 */     add(0.65F, (ItemLike)Items.SEA_PICKLE);
/* 116 */     add(0.65F, (ItemLike)Items.LILY_PAD);
/* 117 */     add(0.65F, (ItemLike)Items.PUMPKIN);
/* 118 */     add(0.65F, (ItemLike)Items.CARVED_PUMPKIN);
/* 119 */     add(0.65F, (ItemLike)Items.MELON);
/* 120 */     add(0.65F, (ItemLike)Items.APPLE);
/* 121 */     add(0.65F, (ItemLike)Items.BEETROOT);
/* 122 */     add(0.65F, (ItemLike)Items.CARROT);
/* 123 */     add(0.65F, (ItemLike)Items.COCOA_BEANS);
/* 124 */     add(0.65F, (ItemLike)Items.POTATO);
/* 125 */     add(0.65F, (ItemLike)Items.WHEAT);
/* 126 */     add(0.65F, (ItemLike)Items.BROWN_MUSHROOM);
/* 127 */     add(0.65F, (ItemLike)Items.RED_MUSHROOM);
/* 128 */     add(0.65F, (ItemLike)Items.MUSHROOM_STEM);
/* 129 */     add(0.65F, (ItemLike)Items.CRIMSON_FUNGUS);
/* 130 */     add(0.65F, (ItemLike)Items.WARPED_FUNGUS);
/* 131 */     add(0.65F, (ItemLike)Items.NETHER_WART);
/* 132 */     add(0.65F, (ItemLike)Items.CRIMSON_ROOTS);
/* 133 */     add(0.65F, (ItemLike)Items.WARPED_ROOTS);
/* 134 */     add(0.65F, (ItemLike)Items.SHROOMLIGHT);
/* 135 */     add(0.65F, (ItemLike)Items.DANDELION);
/* 136 */     add(0.65F, (ItemLike)Items.POPPY);
/* 137 */     add(0.65F, (ItemLike)Items.BLUE_ORCHID);
/* 138 */     add(0.65F, (ItemLike)Items.ALLIUM);
/* 139 */     add(0.65F, (ItemLike)Items.AZURE_BLUET);
/* 140 */     add(0.65F, (ItemLike)Items.RED_TULIP);
/* 141 */     add(0.65F, (ItemLike)Items.ORANGE_TULIP);
/* 142 */     add(0.65F, (ItemLike)Items.WHITE_TULIP);
/* 143 */     add(0.65F, (ItemLike)Items.PINK_TULIP);
/* 144 */     add(0.65F, (ItemLike)Items.OXEYE_DAISY);
/* 145 */     add(0.65F, (ItemLike)Items.CORNFLOWER);
/* 146 */     add(0.65F, (ItemLike)Items.LILY_OF_THE_VALLEY);
/* 147 */     add(0.65F, (ItemLike)Items.WITHER_ROSE);
/* 148 */     add(0.65F, (ItemLike)Items.FERN);
/* 149 */     add(0.65F, (ItemLike)Items.SUNFLOWER);
/* 150 */     add(0.65F, (ItemLike)Items.LILAC);
/* 151 */     add(0.65F, (ItemLike)Items.ROSE_BUSH);
/* 152 */     add(0.65F, (ItemLike)Items.PEONY);
/* 153 */     add(0.65F, (ItemLike)Items.LARGE_FERN);
/* 154 */     add(0.65F, (ItemLike)Items.SPORE_BLOSSOM);
/* 155 */     add(0.65F, (ItemLike)Items.AZALEA);
/* 156 */     add(0.65F, (ItemLike)Items.MOSS_BLOCK);
/* 157 */     add(0.65F, (ItemLike)Items.BIG_DRIPLEAF);
/*     */     
/* 159 */     add(0.85F, (ItemLike)Items.HAY_BLOCK);
/* 160 */     add(0.85F, (ItemLike)Items.BROWN_MUSHROOM_BLOCK);
/* 161 */     add(0.85F, (ItemLike)Items.RED_MUSHROOM_BLOCK);
/* 162 */     add(0.85F, (ItemLike)Items.NETHER_WART_BLOCK);
/* 163 */     add(0.85F, (ItemLike)Items.WARPED_WART_BLOCK);
/* 164 */     add(0.85F, (ItemLike)Items.FLOWERING_AZALEA);
/* 165 */     add(0.85F, (ItemLike)Items.BREAD);
/* 166 */     add(0.85F, (ItemLike)Items.BAKED_POTATO);
/* 167 */     add(0.85F, (ItemLike)Items.COOKIE);
/* 168 */     add(0.85F, (ItemLike)Items.TORCHFLOWER);
/* 169 */     add(0.85F, (ItemLike)Items.PITCHER_PLANT);
/*     */     
/* 171 */     add(1.0F, (ItemLike)Items.CAKE);
/* 172 */     add(1.0F, (ItemLike)Items.PUMPKIN_PIE);
/*     */   }
/*     */   
/*     */   private static void add(float $$0, ItemLike $$1) {
/* 176 */     COMPOSTABLES.put($$1.asItem(), $$0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 181 */   private static final VoxelShape OUTER_SHAPE = Shapes.block(); private static final VoxelShape[] SHAPES;
/*     */   static {
/* 183 */     SHAPES = (VoxelShape[])Util.make(new VoxelShape[9], $$0 -> {
/*     */           for (int $$1 = 0; $$1 < 8; $$1++)
/*     */             $$0[$$1] = Shapes.join(OUTER_SHAPE, Block.box(2.0D, Math.max(2, 1 + $$1 * 2), 2.0D, 14.0D, 16.0D, 14.0D), BooleanOp.ONLY_FIRST); 
/*     */           $$0[8] = $$0[7];
/*     */         });
/*     */   }
/*     */   
/*     */   public ComposterBlock(BlockBehaviour.Properties $$0) {
/* 191 */     super($$0);
/* 192 */     registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)LEVEL, Integer.valueOf(0)));
/*     */   }
/*     */   
/*     */   public static void handleFill(Level $$0, BlockPos $$1, boolean $$2) {
/* 196 */     BlockState $$3 = $$0.getBlockState($$1);
/*     */     
/* 198 */     $$0.playLocalSound($$1, $$2 ? SoundEvents.COMPOSTER_FILL_SUCCESS : SoundEvents.COMPOSTER_FILL, SoundSource.BLOCKS, 1.0F, 1.0F, false);
/*     */     
/* 200 */     double $$4 = $$3.getShape((BlockGetter)$$0, $$1).max(Direction.Axis.Y, 0.5D, 0.5D) + 0.03125D;
/* 201 */     double $$5 = 0.13124999403953552D;
/* 202 */     double $$6 = 0.737500011920929D;
/*     */     
/* 204 */     RandomSource $$7 = $$0.getRandom();
/* 205 */     for (int $$8 = 0; $$8 < 10; $$8++) {
/* 206 */       double $$9 = $$7.nextGaussian() * 0.02D;
/* 207 */       double $$10 = $$7.nextGaussian() * 0.02D;
/* 208 */       double $$11 = $$7.nextGaussian() * 0.02D;
/* 209 */       $$0.addParticle((ParticleOptions)ParticleTypes.COMPOSTER, $$1
/*     */           
/* 211 */           .getX() + 0.13124999403953552D + 0.737500011920929D * $$7.nextFloat(), $$1
/* 212 */           .getY() + $$4 + $$7.nextFloat() * (1.0D - $$4), $$1
/* 213 */           .getZ() + 0.13124999403953552D + 0.737500011920929D * $$7.nextFloat(), $$9, $$10, $$11);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 221 */     return SHAPES[((Integer)$$0.getValue((Property)LEVEL)).intValue()];
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getInteractionShape(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/* 226 */     return OUTER_SHAPE;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public VoxelShape getCollisionShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 232 */     return SHAPES[0];
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPlace(BlockState $$0, Level $$1, BlockPos $$2, BlockState $$3, boolean $$4) {
/* 237 */     if (((Integer)$$0.getValue((Property)LEVEL)).intValue() == 7) {
/* 238 */       $$1.scheduleTick($$2, $$0.getBlock(), 20);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult use(BlockState $$0, Level $$1, BlockPos $$2, Player $$3, InteractionHand $$4, BlockHitResult $$5) {
/* 244 */     int $$6 = ((Integer)$$0.getValue((Property)LEVEL)).intValue();
/*     */     
/* 246 */     ItemStack $$7 = $$3.getItemInHand($$4);
/* 247 */     if ($$6 < 8 && COMPOSTABLES.containsKey($$7.getItem())) {
/* 248 */       if ($$6 < 7 && !$$1.isClientSide) {
/* 249 */         BlockState $$8 = addItem((Entity)$$3, $$0, (LevelAccessor)$$1, $$2, $$7);
/* 250 */         $$1.levelEvent(1500, $$2, ($$0 != $$8) ? 1 : 0);
/* 251 */         $$3.awardStat(Stats.ITEM_USED.get($$7.getItem()));
/*     */         
/* 253 */         if (!($$3.getAbilities()).instabuild) {
/* 254 */           $$7.shrink(1);
/*     */         }
/*     */       } 
/*     */       
/* 258 */       return InteractionResult.sidedSuccess($$1.isClientSide);
/* 259 */     }  if ($$6 == 8) {
/* 260 */       extractProduce((Entity)$$3, $$0, $$1, $$2);
/* 261 */       return InteractionResult.sidedSuccess($$1.isClientSide);
/*     */     } 
/*     */     
/* 264 */     return InteractionResult.PASS;
/*     */   }
/*     */   
/*     */   public static BlockState insertItem(Entity $$0, BlockState $$1, ServerLevel $$2, ItemStack $$3, BlockPos $$4) {
/* 268 */     int $$5 = ((Integer)$$1.getValue((Property)LEVEL)).intValue();
/*     */     
/* 270 */     if ($$5 < 7 && COMPOSTABLES.containsKey($$3.getItem())) {
/* 271 */       BlockState $$6 = addItem($$0, $$1, (LevelAccessor)$$2, $$4, $$3);
/* 272 */       $$3.shrink(1);
/* 273 */       return $$6;
/*     */     } 
/*     */     
/* 276 */     return $$1;
/*     */   }
/*     */   
/*     */   public static BlockState extractProduce(Entity $$0, BlockState $$1, Level $$2, BlockPos $$3) {
/* 280 */     if (!$$2.isClientSide) {
/* 281 */       Vec3 $$4 = Vec3.atLowerCornerWithOffset((Vec3i)$$3, 0.5D, 1.01D, 0.5D).offsetRandom($$2.random, 0.7F);
/* 282 */       ItemEntity $$5 = new ItemEntity($$2, $$4.x(), $$4.y(), $$4.z(), new ItemStack((ItemLike)Items.BONE_MEAL));
/* 283 */       $$5.setDefaultPickUpDelay();
/* 284 */       $$2.addFreshEntity((Entity)$$5);
/*     */     } 
/*     */     
/* 287 */     BlockState $$6 = empty($$0, $$1, (LevelAccessor)$$2, $$3);
/* 288 */     $$2.playSound(null, $$3, SoundEvents.COMPOSTER_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
/* 289 */     return $$6;
/*     */   }
/*     */   
/*     */   static BlockState empty(@Nullable Entity $$0, BlockState $$1, LevelAccessor $$2, BlockPos $$3) {
/* 293 */     BlockState $$4 = (BlockState)$$1.setValue((Property)LEVEL, Integer.valueOf(0));
/* 294 */     $$2.setBlock($$3, $$4, 3);
/* 295 */     $$2.gameEvent(GameEvent.BLOCK_CHANGE, $$3, GameEvent.Context.of($$0, $$4));
/* 296 */     return $$4;
/*     */   }
/*     */   
/*     */   static BlockState addItem(@Nullable Entity $$0, BlockState $$1, LevelAccessor $$2, BlockPos $$3, ItemStack $$4) {
/* 300 */     int $$5 = ((Integer)$$1.getValue((Property)LEVEL)).intValue();
/* 301 */     float $$6 = COMPOSTABLES.getFloat($$4.getItem());
/* 302 */     if (($$5 == 0 && $$6 > 0.0F) || $$2.getRandom().nextDouble() < $$6) {
/* 303 */       int $$7 = $$5 + 1;
/* 304 */       BlockState $$8 = (BlockState)$$1.setValue((Property)LEVEL, Integer.valueOf($$7));
/* 305 */       $$2.setBlock($$3, $$8, 3);
/* 306 */       $$2.gameEvent(GameEvent.BLOCK_CHANGE, $$3, GameEvent.Context.of($$0, $$8));
/*     */       
/* 308 */       if ($$7 == 7) {
/* 309 */         $$2.scheduleTick($$3, $$1.getBlock(), 20);
/*     */       }
/*     */       
/* 312 */       return $$8;
/*     */     } 
/* 314 */     return $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/* 319 */     if (((Integer)$$0.getValue((Property)LEVEL)).intValue() == 7) {
/* 320 */       $$1.setBlock($$2, (BlockState)$$0.cycle((Property)LEVEL), 3);
/* 321 */       $$1.playSound(null, $$2, SoundEvents.COMPOSTER_READY, SoundSource.BLOCKS, 1.0F, 1.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasAnalogOutputSignal(BlockState $$0) {
/* 327 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAnalogOutputSignal(BlockState $$0, Level $$1, BlockPos $$2) {
/* 332 */     return ((Integer)$$0.getValue((Property)LEVEL)).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 337 */     $$0.add(new Property[] { (Property)LEVEL });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPathfindable(BlockState $$0, BlockGetter $$1, BlockPos $$2, PathComputationType $$3) {
/* 342 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public WorldlyContainer getContainer(BlockState $$0, LevelAccessor $$1, BlockPos $$2) {
/* 347 */     int $$3 = ((Integer)$$0.getValue((Property)LEVEL)).intValue();
/* 348 */     if ($$3 == 8) {
/* 349 */       return new OutputContainer($$0, $$1, $$2, new ItemStack((ItemLike)Items.BONE_MEAL));
/*     */     }
/*     */     
/* 352 */     if ($$3 < 7) {
/* 353 */       return new InputContainer($$0, $$1, $$2);
/*     */     }
/*     */     
/* 356 */     return new EmptyContainer();
/*     */   }
/*     */   
/*     */   private static class EmptyContainer extends SimpleContainer implements WorldlyContainer {
/*     */     public EmptyContainer() {
/* 361 */       super(0);
/*     */     }
/*     */ 
/*     */     
/*     */     public int[] getSlotsForFace(Direction $$0) {
/* 366 */       return new int[0];
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canPlaceItemThroughFace(int $$0, ItemStack $$1, @Nullable Direction $$2) {
/* 371 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canTakeItemThroughFace(int $$0, ItemStack $$1, Direction $$2) {
/* 376 */       return false;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class OutputContainer extends SimpleContainer implements WorldlyContainer {
/*     */     private final BlockState state;
/*     */     private final LevelAccessor level;
/*     */     private final BlockPos pos;
/*     */     private boolean changed;
/*     */     
/*     */     public OutputContainer(BlockState $$0, LevelAccessor $$1, BlockPos $$2, ItemStack $$3) {
/* 387 */       super(new ItemStack[] { $$3 });
/* 388 */       this.state = $$0;
/* 389 */       this.level = $$1;
/* 390 */       this.pos = $$2;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getMaxStackSize() {
/* 395 */       return 1;
/*     */     }
/*     */ 
/*     */     
/*     */     public int[] getSlotsForFace(Direction $$0) {
/* 400 */       (new int[1])[0] = 0; return ($$0 == Direction.DOWN) ? new int[1] : new int[0];
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canPlaceItemThroughFace(int $$0, ItemStack $$1, @Nullable Direction $$2) {
/* 405 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canTakeItemThroughFace(int $$0, ItemStack $$1, Direction $$2) {
/* 410 */       return (!this.changed && $$2 == Direction.DOWN && $$1.is(Items.BONE_MEAL));
/*     */     }
/*     */ 
/*     */     
/*     */     public void setChanged() {
/* 415 */       ComposterBlock.empty((Entity)null, this.state, this.level, this.pos);
/* 416 */       this.changed = true;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class InputContainer extends SimpleContainer implements WorldlyContainer {
/*     */     private final BlockState state;
/*     */     private final LevelAccessor level;
/*     */     private final BlockPos pos;
/*     */     private boolean changed;
/*     */     
/*     */     public InputContainer(BlockState $$0, LevelAccessor $$1, BlockPos $$2) {
/* 427 */       super(1);
/* 428 */       this.state = $$0;
/* 429 */       this.level = $$1;
/* 430 */       this.pos = $$2;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getMaxStackSize() {
/* 435 */       return 1;
/*     */     }
/*     */ 
/*     */     
/*     */     public int[] getSlotsForFace(Direction $$0) {
/* 440 */       (new int[1])[0] = 0; return ($$0 == Direction.UP) ? new int[1] : new int[0];
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canPlaceItemThroughFace(int $$0, ItemStack $$1, @Nullable Direction $$2) {
/* 445 */       return (!this.changed && $$2 == Direction.UP && ComposterBlock.COMPOSTABLES.containsKey($$1.getItem()));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canTakeItemThroughFace(int $$0, ItemStack $$1, Direction $$2) {
/* 450 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setChanged() {
/* 455 */       ItemStack $$0 = getItem(0);
/* 456 */       if (!$$0.isEmpty()) {
/* 457 */         this.changed = true;
/* 458 */         BlockState $$1 = ComposterBlock.addItem((Entity)null, this.state, this.level, this.pos, $$0);
/* 459 */         this.level.levelEvent(1500, this.pos, ($$1 != this.state) ? 1 : 0);
/* 460 */         removeItemNoUpdate(0);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\ComposterBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */