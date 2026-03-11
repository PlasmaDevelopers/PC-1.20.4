/*     */ package net.minecraft.world.level.block;
/*     */ 
/*     */ import com.google.common.cache.CacheBuilder;
/*     */ import com.google.common.cache.CacheLoader;
/*     */ import com.google.common.cache.LoadingCache;
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ByteLinkedOpenHashMap;
/*     */ import java.util.List;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.SharedConstants;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.IdMapper;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.stats.Stats;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.util.valueproviders.IntProvider;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.ExperienceOrb;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.item.ItemEntity;
/*     */ import net.minecraft.world.entity.monster.piglin.PiglinAi;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.BlockItem;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.TooltipFlag;
/*     */ import net.minecraft.world.item.context.BlockPlaceContext;
/*     */ import net.minecraft.world.item.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.world.item.enchantment.Enchantments;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Explosion;
/*     */ import net.minecraft.world.level.GameRules;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.biome.Biome;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.level.storage.loot.LootParams;
/*     */ import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import net.minecraft.world.phys.shapes.BooleanOp;
/*     */ import net.minecraft.world.phys.shapes.Shapes;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Block
/*     */   extends BlockBehaviour
/*     */   implements ItemLike
/*     */ {
/*  77 */   public static final MapCodec<Block> CODEC = simpleCodec(Block::new);
/*     */   
/*  79 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*  81 */   private final Holder.Reference<Block> builtInRegistryHolder = BuiltInRegistries.BLOCK.createIntrusiveHolder(this);
/*     */   
/*  83 */   public static final IdMapper<BlockState> BLOCK_STATE_REGISTRY = new IdMapper();
/*     */   
/*  85 */   private static final LoadingCache<VoxelShape, Boolean> SHAPE_FULL_BLOCK_CACHE = CacheBuilder.newBuilder()
/*  86 */     .maximumSize(512L)
/*  87 */     .weakKeys()
/*  88 */     .build(new CacheLoader<VoxelShape, Boolean>()
/*     */       {
/*     */         public Boolean load(VoxelShape $$0) {
/*  91 */           return Boolean.valueOf(!Shapes.joinIsNotEmpty(Shapes.block(), $$0, BooleanOp.NOT_SAME));
/*     */         }
/*     */       });
/*     */ 
/*     */   
/*     */   public static final int UPDATE_NEIGHBORS = 1;
/*     */   
/*     */   public static final int UPDATE_CLIENTS = 2;
/*     */   
/*     */   public static final int UPDATE_INVISIBLE = 4;
/*     */   
/*     */   public static final int UPDATE_IMMEDIATE = 8;
/*     */   
/*     */   public static final int UPDATE_KNOWN_SHAPE = 16;
/*     */   
/*     */   public static final int UPDATE_SUPPRESS_DROPS = 32;
/*     */   
/*     */   public static final int UPDATE_MOVE_BY_PISTON = 64;
/*     */   
/*     */   public static final int UPDATE_NONE = 4;
/*     */   
/*     */   public static final int UPDATE_ALL = 3;
/*     */   
/*     */   protected MapCodec<? extends Block> codec() {
/* 115 */     return CODEC;
/*     */   } public static final int UPDATE_ALL_IMMEDIATE = 11; public static final float INDESTRUCTIBLE = -1.0F; public static final float INSTANT = 0.0F; public static final int UPDATE_LIMIT = 512; protected final StateDefinition<Block, BlockState> stateDefinition; private BlockState defaultBlockState; @Nullable
/*     */   private String descriptionId; @Nullable
/*     */   private Item item; private static final int CACHE_SIZE = 2048; private static final ThreadLocal<Object2ByteLinkedOpenHashMap<BlockStatePairKey>> OCCLUSION_CACHE; public static int getId(@Nullable BlockState $$0) {
/* 119 */     if ($$0 == null) {
/* 120 */       return 0;
/*     */     }
/* 122 */     int $$1 = BLOCK_STATE_REGISTRY.getId($$0);
/* 123 */     return ($$1 == -1) ? 0 : $$1;
/*     */   }
/*     */   
/*     */   public static BlockState stateById(int $$0) {
/* 127 */     BlockState $$1 = (BlockState)BLOCK_STATE_REGISTRY.byId($$0);
/* 128 */     return ($$1 == null) ? Blocks.AIR.defaultBlockState() : $$1;
/*     */   }
/*     */   
/*     */   public static Block byItem(@Nullable Item $$0) {
/* 132 */     if ($$0 instanceof BlockItem) {
/* 133 */       return ((BlockItem)$$0).getBlock();
/*     */     }
/*     */     
/* 136 */     return Blocks.AIR;
/*     */   }
/*     */   
/*     */   public static BlockState pushEntitiesUp(BlockState $$0, BlockState $$1, LevelAccessor $$2, BlockPos $$3) {
/* 140 */     VoxelShape $$4 = Shapes.joinUnoptimized($$0.getCollisionShape((BlockGetter)$$2, $$3), $$1.getCollisionShape((BlockGetter)$$2, $$3), BooleanOp.ONLY_SECOND).move($$3.getX(), $$3.getY(), $$3.getZ());
/* 141 */     if ($$4.isEmpty()) {
/* 142 */       return $$1;
/*     */     }
/* 144 */     List<Entity> $$5 = $$2.getEntities(null, $$4.bounds());
/* 145 */     for (Entity $$6 : $$5) {
/*     */       
/* 147 */       double $$7 = Shapes.collide(Direction.Axis.Y, $$6.getBoundingBox().move(0.0D, 1.0D, 0.0D), List.of($$4), -1.0D);
/* 148 */       $$6.teleportRelative(0.0D, 1.0D + $$7, 0.0D);
/*     */     } 
/* 150 */     return $$1;
/*     */   }
/*     */   
/*     */   public static VoxelShape box(double $$0, double $$1, double $$2, double $$3, double $$4, double $$5) {
/* 154 */     return Shapes.box($$0 / 16.0D, $$1 / 16.0D, $$2 / 16.0D, $$3 / 16.0D, $$4 / 16.0D, $$5 / 16.0D);
/*     */   }
/*     */   
/*     */   public static BlockState updateFromNeighbourShapes(BlockState $$0, LevelAccessor $$1, BlockPos $$2) {
/* 158 */     BlockState $$3 = $$0;
/*     */     
/* 160 */     BlockPos.MutableBlockPos $$4 = new BlockPos.MutableBlockPos();
/* 161 */     for (Direction $$5 : UPDATE_SHAPE_ORDER) {
/* 162 */       $$4.setWithOffset((Vec3i)$$2, $$5);
/* 163 */       $$3 = $$3.updateShape($$5, $$1.getBlockState((BlockPos)$$4), $$1, $$2, (BlockPos)$$4);
/*     */     } 
/*     */     
/* 166 */     return $$3;
/*     */   }
/*     */   
/*     */   public static void updateOrDestroy(BlockState $$0, BlockState $$1, LevelAccessor $$2, BlockPos $$3, int $$4) {
/* 170 */     updateOrDestroy($$0, $$1, $$2, $$3, $$4, 512);
/*     */   }
/*     */   
/*     */   public static void updateOrDestroy(BlockState $$0, BlockState $$1, LevelAccessor $$2, BlockPos $$3, int $$4, int $$5) {
/* 174 */     if ($$1 != $$0) {
/* 175 */       if ($$1.isAir()) {
/* 176 */         if (!$$2.isClientSide()) {
/* 177 */           $$2.destroyBlock($$3, (($$4 & 0x20) == 0), null, $$5);
/*     */         }
/*     */       } else {
/* 180 */         $$2.setBlock($$3, $$1, $$4 & 0xFFFFFFDF, $$5);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Block(BlockBehaviour.Properties $$0) {
/* 195 */     super($$0);
/* 196 */     StateDefinition.Builder<Block, BlockState> $$1 = new StateDefinition.Builder(this);
/* 197 */     createBlockStateDefinition($$1);
/*     */     
/* 199 */     this.stateDefinition = $$1.create(Block::defaultBlockState, BlockState::new);
/* 200 */     registerDefaultState((BlockState)this.stateDefinition.any());
/*     */     
/* 202 */     if (SharedConstants.IS_RUNNING_IN_IDE) {
/* 203 */       String $$2 = getClass().getSimpleName();
/* 204 */       if (!$$2.endsWith("Block")) {
/* 205 */         LOGGER.error("Block classes should end with Block and {} doesn't.", $$2);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean isExceptionForConnection(BlockState $$0) {
/* 211 */     return ($$0.getBlock() instanceof LeavesBlock || $$0
/* 212 */       .is(Blocks.BARRIER) || $$0
/* 213 */       .is(Blocks.CARVED_PUMPKIN) || $$0
/* 214 */       .is(Blocks.JACK_O_LANTERN) || $$0
/* 215 */       .is(Blocks.MELON) || $$0
/* 216 */       .is(Blocks.PUMPKIN) || $$0
/* 217 */       .is(BlockTags.SHULKER_BOXES));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isRandomlyTicking(BlockState $$0) {
/* 222 */     return this.isRandomlyTicking;
/*     */   }
/*     */   
/*     */   public static final class BlockStatePairKey {
/*     */     private final BlockState first;
/*     */     private final BlockState second;
/*     */     private final Direction direction;
/*     */     
/*     */     public BlockStatePairKey(BlockState $$0, BlockState $$1, Direction $$2) {
/* 231 */       this.first = $$0;
/* 232 */       this.second = $$1;
/* 233 */       this.direction = $$2;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object $$0) {
/* 238 */       if (this == $$0) {
/* 239 */         return true;
/*     */       }
/* 241 */       if (!($$0 instanceof BlockStatePairKey)) {
/* 242 */         return false;
/*     */       }
/* 244 */       BlockStatePairKey $$1 = (BlockStatePairKey)$$0;
/* 245 */       return (this.first == $$1.first && this.second == $$1.second && this.direction == $$1.direction);
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 250 */       int $$0 = this.first.hashCode();
/* 251 */       $$0 = 31 * $$0 + this.second.hashCode();
/* 252 */       $$0 = 31 * $$0 + this.direction.hashCode();
/* 253 */       return $$0;
/*     */     }
/*     */   }
/*     */   
/*     */   static {
/* 258 */     OCCLUSION_CACHE = ThreadLocal.withInitial(() -> {
/*     */           Object2ByteLinkedOpenHashMap<BlockStatePairKey> $$0 = new Object2ByteLinkedOpenHashMap<BlockStatePairKey>(2048, 0.25F)
/*     */             {
/*     */               protected void rehash(int $$0) {}
/*     */             };
/*     */           $$0.defaultReturnValue(127);
/*     */           return $$0;
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean shouldRenderFace(BlockState $$0, BlockGetter $$1, BlockPos $$2, Direction $$3, BlockPos $$4) {
/* 272 */     BlockState $$5 = $$1.getBlockState($$4);
/*     */     
/* 274 */     if ($$0.skipRendering($$5, $$3)) {
/* 275 */       return false;
/*     */     }
/*     */     
/* 278 */     if ($$5.canOcclude()) {
/* 279 */       BlockStatePairKey $$6 = new BlockStatePairKey($$0, $$5, $$3);
/* 280 */       Object2ByteLinkedOpenHashMap<BlockStatePairKey> $$7 = OCCLUSION_CACHE.get();
/* 281 */       byte $$8 = $$7.getAndMoveToFirst($$6);
/* 282 */       if ($$8 != Byte.MAX_VALUE) {
/* 283 */         return ($$8 != 0);
/*     */       }
/* 285 */       VoxelShape $$9 = $$0.getFaceOcclusionShape($$1, $$2, $$3);
/* 286 */       if ($$9.isEmpty()) {
/* 287 */         return true;
/*     */       }
/* 289 */       VoxelShape $$10 = $$5.getFaceOcclusionShape($$1, $$4, $$3.getOpposite());
/*     */       
/* 291 */       boolean $$11 = Shapes.joinIsNotEmpty($$9, $$10, BooleanOp.ONLY_FIRST);
/* 292 */       if ($$7.size() == 2048) {
/* 293 */         $$7.removeLastByte();
/*     */       }
/* 295 */       $$7.putAndMoveToFirst($$6, (byte)($$11 ? 1 : 0));
/* 296 */       return $$11;
/*     */     } 
/* 298 */     return true;
/*     */   }
/*     */   
/*     */   public static boolean canSupportRigidBlock(BlockGetter $$0, BlockPos $$1) {
/* 302 */     return $$0.getBlockState($$1).isFaceSturdy($$0, $$1, Direction.UP, SupportType.RIGID);
/*     */   }
/*     */   
/*     */   public static boolean canSupportCenter(LevelReader $$0, BlockPos $$1, Direction $$2) {
/* 306 */     BlockState $$3 = $$0.getBlockState($$1);
/*     */     
/* 308 */     if ($$2 == Direction.DOWN && $$3.is(BlockTags.UNSTABLE_BOTTOM_CENTER)) {
/* 309 */       return false;
/*     */     }
/*     */     
/* 312 */     return $$3.isFaceSturdy((BlockGetter)$$0, $$1, $$2, SupportType.CENTER);
/*     */   }
/*     */   
/*     */   public static boolean isFaceFull(VoxelShape $$0, Direction $$1) {
/* 316 */     VoxelShape $$2 = $$0.getFaceShape($$1);
/* 317 */     return isShapeFullBlock($$2);
/*     */   }
/*     */   
/*     */   public static boolean isShapeFullBlock(VoxelShape $$0) {
/* 321 */     return ((Boolean)SHAPE_FULL_BLOCK_CACHE.getUnchecked($$0)).booleanValue();
/*     */   }
/*     */   
/*     */   public boolean propagatesSkylightDown(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/* 325 */     return (!isShapeFullBlock($$0.getShape($$1, $$2)) && $$0.getFluidState().isEmpty());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void animateTick(BlockState $$0, Level $$1, BlockPos $$2, RandomSource $$3) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void destroy(LevelAccessor $$0, BlockPos $$1, BlockState $$2) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<ItemStack> getDrops(BlockState $$0, ServerLevel $$1, BlockPos $$2, @Nullable BlockEntity $$3) {
/* 339 */     LootParams.Builder $$4 = (new LootParams.Builder($$1)).withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf((Vec3i)$$2)).withParameter(LootContextParams.TOOL, ItemStack.EMPTY).withOptionalParameter(LootContextParams.BLOCK_ENTITY, $$3);
/* 340 */     return $$0.getDrops($$4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<ItemStack> getDrops(BlockState $$0, ServerLevel $$1, BlockPos $$2, @Nullable BlockEntity $$3, @Nullable Entity $$4, ItemStack $$5) {
/* 348 */     LootParams.Builder $$6 = (new LootParams.Builder($$1)).withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf((Vec3i)$$2)).withParameter(LootContextParams.TOOL, $$5).withOptionalParameter(LootContextParams.THIS_ENTITY, $$4).withOptionalParameter(LootContextParams.BLOCK_ENTITY, $$3);
/* 349 */     return $$0.getDrops($$6);
/*     */   }
/*     */   
/*     */   public static void dropResources(BlockState $$0, Level $$1, BlockPos $$2) {
/* 353 */     if ($$1 instanceof ServerLevel) {
/* 354 */       getDrops($$0, (ServerLevel)$$1, $$2, (BlockEntity)null).forEach($$2 -> popResource($$0, $$1, $$2));
/* 355 */       $$0.spawnAfterBreak((ServerLevel)$$1, $$2, ItemStack.EMPTY, true);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void dropResources(BlockState $$0, LevelAccessor $$1, BlockPos $$2, @Nullable BlockEntity $$3) {
/* 360 */     if ($$1 instanceof ServerLevel) {
/* 361 */       getDrops($$0, (ServerLevel)$$1, $$2, $$3).forEach($$2 -> popResource((Level)$$0, $$1, $$2));
/* 362 */       $$0.spawnAfterBreak((ServerLevel)$$1, $$2, ItemStack.EMPTY, true);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void dropResources(BlockState $$0, Level $$1, BlockPos $$2, @Nullable BlockEntity $$3, @Nullable Entity $$4, ItemStack $$5) {
/* 367 */     if ($$1 instanceof ServerLevel) {
/* 368 */       getDrops($$0, (ServerLevel)$$1, $$2, $$3, $$4, $$5).forEach($$2 -> popResource($$0, $$1, $$2));
/* 369 */       $$0.spawnAfterBreak((ServerLevel)$$1, $$2, $$5, true);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void popResource(Level $$0, BlockPos $$1, ItemStack $$2) {
/* 374 */     double $$3 = EntityType.ITEM.getHeight() / 2.0D;
/*     */     
/* 376 */     double $$4 = $$1.getX() + 0.5D + Mth.nextDouble($$0.random, -0.25D, 0.25D);
/* 377 */     double $$5 = $$1.getY() + 0.5D + Mth.nextDouble($$0.random, -0.25D, 0.25D) - $$3;
/* 378 */     double $$6 = $$1.getZ() + 0.5D + Mth.nextDouble($$0.random, -0.25D, 0.25D);
/*     */     
/* 380 */     popResource($$0, () -> new ItemEntity($$0, $$1, $$2, $$3, $$4), $$2);
/*     */   }
/*     */   
/*     */   public static void popResourceFromFace(Level $$0, BlockPos $$1, Direction $$2, ItemStack $$3) {
/* 384 */     int $$4 = $$2.getStepX();
/* 385 */     int $$5 = $$2.getStepY();
/* 386 */     int $$6 = $$2.getStepZ();
/*     */     
/* 388 */     double $$7 = EntityType.ITEM.getWidth() / 2.0D;
/* 389 */     double $$8 = EntityType.ITEM.getHeight() / 2.0D;
/*     */     
/* 391 */     double $$9 = $$1.getX() + 0.5D + (($$4 == 0) ? Mth.nextDouble($$0.random, -0.25D, 0.25D) : ($$4 * (0.5D + $$7)));
/* 392 */     double $$10 = $$1.getY() + 0.5D + (($$5 == 0) ? Mth.nextDouble($$0.random, -0.25D, 0.25D) : ($$5 * (0.5D + $$8))) - $$8;
/* 393 */     double $$11 = $$1.getZ() + 0.5D + (($$6 == 0) ? Mth.nextDouble($$0.random, -0.25D, 0.25D) : ($$6 * (0.5D + $$7)));
/*     */     
/* 395 */     double $$12 = ($$4 == 0) ? Mth.nextDouble($$0.random, -0.1D, 0.1D) : ($$4 * 0.1D);
/* 396 */     double $$13 = ($$5 == 0) ? Mth.nextDouble($$0.random, 0.0D, 0.1D) : ($$5 * 0.1D + 0.1D);
/* 397 */     double $$14 = ($$6 == 0) ? Mth.nextDouble($$0.random, -0.1D, 0.1D) : ($$6 * 0.1D);
/*     */     
/* 399 */     popResource($$0, () -> new ItemEntity($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7), $$3);
/*     */   }
/*     */   
/*     */   private static void popResource(Level $$0, Supplier<ItemEntity> $$1, ItemStack $$2) {
/* 403 */     if ($$0.isClientSide || $$2.isEmpty() || !$$0.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS)) {
/*     */       return;
/*     */     }
/*     */     
/* 407 */     ItemEntity $$3 = $$1.get();
/* 408 */     $$3.setDefaultPickUpDelay();
/* 409 */     $$0.addFreshEntity((Entity)$$3);
/*     */   }
/*     */   
/*     */   protected void popExperience(ServerLevel $$0, BlockPos $$1, int $$2) {
/* 413 */     if ($$0.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS)) {
/* 414 */       ExperienceOrb.award($$0, Vec3.atCenterOf((Vec3i)$$1), $$2);
/*     */     }
/*     */   }
/*     */   
/*     */   public float getExplosionResistance() {
/* 419 */     return this.explosionResistance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void wasExploded(Level $$0, BlockPos $$1, Explosion $$2) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void stepOn(Level $$0, BlockPos $$1, BlockState $$2, Entity $$3) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/* 440 */     return defaultBlockState();
/*     */   }
/*     */ 
/*     */   
/*     */   public void playerDestroy(Level $$0, Player $$1, BlockPos $$2, BlockState $$3, @Nullable BlockEntity $$4, ItemStack $$5) {
/* 445 */     $$1.awardStat(Stats.BLOCK_MINED.get(this));
/* 446 */     $$1.causeFoodExhaustion(0.005F);
/* 447 */     dropResources($$3, $$0, $$2, $$4, (Entity)$$1, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPlacedBy(Level $$0, BlockPos $$1, BlockState $$2, @Nullable LivingEntity $$3, ItemStack $$4) {}
/*     */ 
/*     */   
/*     */   public boolean isPossibleToRespawnInThis(BlockState $$0) {
/* 455 */     return (!$$0.isSolid() && !$$0.liquid());
/*     */   }
/*     */   
/*     */   public MutableComponent getName() {
/* 459 */     return Component.translatable(getDescriptionId());
/*     */   }
/*     */   
/*     */   public String getDescriptionId() {
/* 463 */     if (this.descriptionId == null) {
/* 464 */       this.descriptionId = Util.makeDescriptionId("block", BuiltInRegistries.BLOCK.getKey(this));
/*     */     }
/* 466 */     return this.descriptionId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fallOn(Level $$0, BlockState $$1, BlockPos $$2, Entity $$3, float $$4) {
/* 473 */     $$3.causeFallDamage($$4, 1.0F, $$3.damageSources().fall());
/*     */   }
/*     */   
/*     */   public void updateEntityAfterFallOn(BlockGetter $$0, Entity $$1) {
/* 477 */     $$1.setDeltaMovement($$1.getDeltaMovement().multiply(1.0D, 0.0D, 1.0D));
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getCloneItemStack(LevelReader $$0, BlockPos $$1, BlockState $$2) {
/* 482 */     return new ItemStack(this);
/*     */   }
/*     */   
/*     */   public float getFriction() {
/* 486 */     return this.friction;
/*     */   }
/*     */   
/*     */   public float getSpeedFactor() {
/* 490 */     return this.speedFactor;
/*     */   }
/*     */   
/*     */   public float getJumpFactor() {
/* 494 */     return this.jumpFactor;
/*     */   }
/*     */   
/*     */   protected void spawnDestroyParticles(Level $$0, Player $$1, BlockPos $$2, BlockState $$3) {
/* 498 */     $$0.levelEvent($$1, 2001, $$2, getId($$3));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState playerWillDestroy(Level $$0, BlockPos $$1, BlockState $$2, Player $$3) {
/* 503 */     spawnDestroyParticles($$0, $$3, $$1, $$2);
/*     */     
/* 505 */     if ($$2.is(BlockTags.GUARDED_BY_PIGLINS)) {
/* 506 */       PiglinAi.angerNearbyPiglins($$3, false);
/*     */     }
/* 508 */     $$0.gameEvent(GameEvent.BLOCK_DESTROY, $$1, GameEvent.Context.of((Entity)$$3, $$2));
/* 509 */     return $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void handlePrecipitation(BlockState $$0, Level $$1, BlockPos $$2, Biome.Precipitation $$3) {}
/*     */   
/*     */   public boolean dropFromExplosion(Explosion $$0) {
/* 516 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {}
/*     */   
/*     */   public StateDefinition<Block, BlockState> getStateDefinition() {
/* 523 */     return this.stateDefinition;
/*     */   }
/*     */   
/*     */   protected final void registerDefaultState(BlockState $$0) {
/* 527 */     this.defaultBlockState = $$0;
/*     */   }
/*     */   
/*     */   public final BlockState defaultBlockState() {
/* 531 */     return this.defaultBlockState;
/*     */   }
/*     */   
/*     */   public final BlockState withPropertiesOf(BlockState $$0) {
/* 535 */     BlockState $$1 = defaultBlockState();
/* 536 */     for (Property<?> $$2 : (Iterable<Property<?>>)$$0.getBlock().getStateDefinition().getProperties()) {
/* 537 */       if ($$1.hasProperty($$2)) {
/* 538 */         $$1 = copyProperty($$0, $$1, $$2);
/*     */       }
/*     */     } 
/* 541 */     return $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   private static <T extends Comparable<T>> BlockState copyProperty(BlockState $$0, BlockState $$1, Property<T> $$2) {
/* 546 */     return (BlockState)$$1.setValue($$2, $$0.getValue($$2));
/*     */   }
/*     */   
/*     */   public SoundType getSoundType(BlockState $$0) {
/* 550 */     return this.soundType;
/*     */   }
/*     */ 
/*     */   
/*     */   public Item asItem() {
/* 555 */     if (this.item == null) {
/* 556 */       this.item = Item.byBlock(this);
/*     */     }
/* 558 */     return this.item;
/*     */   }
/*     */   
/*     */   public boolean hasDynamicShape() {
/* 562 */     return this.dynamicShape;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 567 */     return "Block{" + BuiltInRegistries.BLOCK.getKey(this) + "}";
/*     */   }
/*     */ 
/*     */   
/*     */   public void appendHoverText(ItemStack $$0, @Nullable BlockGetter $$1, List<Component> $$2, TooltipFlag $$3) {}
/*     */ 
/*     */   
/*     */   protected Block asBlock() {
/* 575 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ImmutableMap<BlockState, VoxelShape> getShapeForEachState(Function<BlockState, VoxelShape> $$0) {
/* 582 */     return (ImmutableMap<BlockState, VoxelShape>)this.stateDefinition.getPossibleStates().stream().collect(ImmutableMap.toImmutableMap(Function.identity(), $$0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Holder.Reference<Block> builtInRegistryHolder() {
/* 590 */     return this.builtInRegistryHolder;
/*     */   }
/*     */   
/*     */   protected void tryDropExperience(ServerLevel $$0, BlockPos $$1, ItemStack $$2, IntProvider $$3) {
/* 594 */     if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, $$2) == 0) {
/* 595 */       int $$4 = $$3.sample($$0.random);
/* 596 */       if ($$4 > 0)
/* 597 */         popExperience($$0, $$1, $$4); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\Block.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */