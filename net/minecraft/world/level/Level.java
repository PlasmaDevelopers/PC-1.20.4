/*     */ package net.minecraft.world.level;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.serialization.Codec;
/*     */ import java.io.IOException;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Predicate;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.CrashReport;
/*     */ import net.minecraft.CrashReportCategory;
/*     */ import net.minecraft.ReportedException;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.RegistryAccess;
/*     */ import net.minecraft.core.SectionPos;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.server.level.FullChunkStatus;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.util.AbortableIterationConsumer;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.util.profiling.ProfilerFiller;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.TickRateManager;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.damagesource.DamageSources;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.boss.EnderDragonPart;
/*     */ import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.crafting.RecipeManager;
/*     */ import net.minecraft.world.level.biome.Biome;
/*     */ import net.minecraft.world.level.biome.BiomeManager;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.entity.TickingBlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.border.WorldBorder;
/*     */ import net.minecraft.world.level.chunk.ChunkAccess;
/*     */ import net.minecraft.world.level.chunk.ChunkStatus;
/*     */ import net.minecraft.world.level.chunk.LevelChunk;
/*     */ import net.minecraft.world.level.dimension.DimensionType;
/*     */ import net.minecraft.world.level.entity.EntityTypeTest;
/*     */ import net.minecraft.world.level.entity.LevelEntityGetter;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.level.levelgen.Heightmap;
/*     */ import net.minecraft.world.level.lighting.LevelLightEngine;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ import net.minecraft.world.level.material.Fluids;
/*     */ import net.minecraft.world.level.redstone.CollectingNeighborUpdater;
/*     */ import net.minecraft.world.level.redstone.NeighborUpdater;
/*     */ import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
/*     */ import net.minecraft.world.level.storage.LevelData;
/*     */ import net.minecraft.world.level.storage.WritableLevelData;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import net.minecraft.world.scores.Scoreboard;
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Level
/*     */   implements LevelAccessor, AutoCloseable
/*     */ {
/*  81 */   public static final Codec<ResourceKey<Level>> RESOURCE_KEY_CODEC = ResourceKey.codec(Registries.DIMENSION);
/*     */   
/*  83 */   public static final ResourceKey<Level> OVERWORLD = ResourceKey.create(Registries.DIMENSION, new ResourceLocation("overworld"));
/*  84 */   public static final ResourceKey<Level> NETHER = ResourceKey.create(Registries.DIMENSION, new ResourceLocation("the_nether"));
/*  85 */   public static final ResourceKey<Level> END = ResourceKey.create(Registries.DIMENSION, new ResourceLocation("the_end"));
/*     */   
/*     */   public static final int MAX_LEVEL_SIZE = 30000000;
/*     */   
/*     */   public static final int LONG_PARTICLE_CLIP_RANGE = 512;
/*     */   
/*     */   public static final int SHORT_PARTICLE_CLIP_RANGE = 32;
/*     */   
/*     */   public static final int MAX_BRIGHTNESS = 15;
/*     */   
/*     */   public static final int TICKS_PER_DAY = 24000;
/*     */   public static final int MAX_ENTITY_SPAWN_Y = 20000000;
/*     */   public static final int MIN_ENTITY_SPAWN_Y = -20000000;
/*  98 */   protected final List<TickingBlockEntity> blockEntityTickers = Lists.newArrayList();
/*     */   protected final NeighborUpdater neighborUpdater;
/* 100 */   private final List<TickingBlockEntity> pendingBlockEntityTickers = Lists.newArrayList();
/*     */   
/*     */   private boolean tickingBlockEntities;
/*     */   
/*     */   private final Thread thread;
/*     */   
/*     */   private final boolean isDebug;
/*     */   private int skyDarken;
/* 108 */   protected int randValue = RandomSource.create().nextInt();
/* 109 */   protected final int addend = 1013904223;
/*     */   
/*     */   protected float oRainLevel;
/*     */   protected float rainLevel;
/*     */   protected float oThunderLevel;
/*     */   protected float thunderLevel;
/* 115 */   public final RandomSource random = RandomSource.create();
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/* 120 */   private final RandomSource threadSafeRandom = RandomSource.createThreadSafe();
/*     */   
/*     */   private final ResourceKey<DimensionType> dimensionTypeId;
/*     */   
/*     */   private final Holder<DimensionType> dimensionTypeRegistration;
/*     */   
/*     */   protected final WritableLevelData levelData;
/*     */   
/*     */   private final Supplier<ProfilerFiller> profiler;
/*     */   public final boolean isClientSide;
/*     */   private final WorldBorder worldBorder;
/*     */   private final BiomeManager biomeManager;
/*     */   private final ResourceKey<Level> dimension;
/*     */   private final RegistryAccess registryAccess;
/*     */   private final DamageSources damageSources;
/*     */   private long subTickCount;
/*     */   
/*     */   protected Level(WritableLevelData $$0, ResourceKey<Level> $$1, RegistryAccess $$2, Holder<DimensionType> $$3, Supplier<ProfilerFiller> $$4, boolean $$5, boolean $$6, long $$7, int $$8) {
/* 138 */     this.profiler = $$4;
/* 139 */     this.levelData = $$0;
/* 140 */     this.dimensionTypeRegistration = $$3;
/* 141 */     this.dimensionTypeId = (ResourceKey<DimensionType>)$$3.unwrapKey().orElseThrow(() -> new IllegalArgumentException("Dimension must be registered, got " + $$0));
/* 142 */     final DimensionType dimensionType = (DimensionType)$$3.value();
/* 143 */     this.dimension = $$1;
/* 144 */     this.isClientSide = $$5;
/* 145 */     if ($$9.coordinateScale() != 1.0D) {
/* 146 */       this.worldBorder = new WorldBorder()
/*     */         {
/*     */           public double getCenterX() {
/* 149 */             return super.getCenterX() / dimensionType.coordinateScale();
/*     */           }
/*     */ 
/*     */           
/*     */           public double getCenterZ() {
/* 154 */             return super.getCenterZ() / dimensionType.coordinateScale();
/*     */           }
/*     */         };
/*     */     } else {
/* 158 */       this.worldBorder = new WorldBorder();
/*     */     } 
/* 160 */     this.thread = Thread.currentThread();
/* 161 */     this.biomeManager = new BiomeManager(this, $$7);
/* 162 */     this.isDebug = $$6;
/* 163 */     this.neighborUpdater = (NeighborUpdater)new CollectingNeighborUpdater(this, $$8);
/* 164 */     this.registryAccess = $$2;
/* 165 */     this.damageSources = new DamageSources($$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isClientSide() {
/* 170 */     return this.isClientSide;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public MinecraftServer getServer() {
/* 176 */     return null;
/*     */   }
/*     */   
/*     */   public boolean isInWorldBounds(BlockPos $$0) {
/* 180 */     return (!isOutsideBuildHeight($$0) && isInWorldBoundsHorizontal($$0));
/*     */   }
/*     */   
/*     */   public static boolean isInSpawnableBounds(BlockPos $$0) {
/* 184 */     return (!isOutsideSpawnableHeight($$0.getY()) && isInWorldBoundsHorizontal($$0));
/*     */   }
/*     */   
/*     */   private static boolean isInWorldBoundsHorizontal(BlockPos $$0) {
/* 188 */     return ($$0.getX() >= -30000000 && $$0.getZ() >= -30000000 && $$0.getX() < 30000000 && $$0.getZ() < 30000000);
/*     */   }
/*     */   
/*     */   private static boolean isOutsideSpawnableHeight(int $$0) {
/* 192 */     return ($$0 < -20000000 || $$0 >= 20000000);
/*     */   }
/*     */   
/*     */   public LevelChunk getChunkAt(BlockPos $$0) {
/* 196 */     return getChunk(SectionPos.blockToSectionCoord($$0.getX()), SectionPos.blockToSectionCoord($$0.getZ()));
/*     */   }
/*     */ 
/*     */   
/*     */   public LevelChunk getChunk(int $$0, int $$1) {
/* 201 */     return (LevelChunk)getChunk($$0, $$1, ChunkStatus.FULL);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public ChunkAccess getChunk(int $$0, int $$1, ChunkStatus $$2, boolean $$3) {
/* 207 */     ChunkAccess $$4 = getChunkSource().getChunk($$0, $$1, $$2, $$3);
/* 208 */     if ($$4 == null && $$3) {
/* 209 */       throw new IllegalStateException("Should always be able to create a chunk!");
/*     */     }
/* 211 */     return $$4;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean setBlock(BlockPos $$0, BlockState $$1, int $$2) {
/* 216 */     return setBlock($$0, $$1, $$2, 512);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean setBlock(BlockPos $$0, BlockState $$1, int $$2, int $$3) {
/* 221 */     if (isOutsideBuildHeight($$0)) {
/* 222 */       return false;
/*     */     }
/*     */     
/* 225 */     if (!this.isClientSide && isDebug()) {
/* 226 */       return false;
/*     */     }
/*     */     
/* 229 */     LevelChunk $$4 = getChunkAt($$0);
/* 230 */     Block $$5 = $$1.getBlock();
/* 231 */     BlockState $$6 = $$4.setBlockState($$0, $$1, (($$2 & 0x40) != 0));
/*     */ 
/*     */     
/* 234 */     if ($$6 != null) {
/*     */       
/* 236 */       BlockState $$7 = getBlockState($$0);
/*     */ 
/*     */       
/* 239 */       if ($$7 == $$1) {
/* 240 */         if ($$6 != $$7) {
/* 241 */           setBlocksDirty($$0, $$6, $$7);
/*     */         }
/*     */         
/* 244 */         if (($$2 & 0x2) != 0 && (!this.isClientSide || ($$2 & 0x4) == 0) && (this.isClientSide || ($$4.getFullStatus() != null && $$4.getFullStatus().isOrAfter(FullChunkStatus.BLOCK_TICKING)))) {
/* 245 */           sendBlockUpdated($$0, $$6, $$1, $$2);
/*     */         }
/*     */         
/* 248 */         if (($$2 & 0x1) != 0) {
/* 249 */           blockUpdated($$0, $$6.getBlock());
/* 250 */           if (!this.isClientSide && $$1.hasAnalogOutputSignal()) {
/* 251 */             updateNeighbourForOutputSignal($$0, $$5);
/*     */           }
/*     */         } 
/*     */ 
/*     */         
/* 256 */         if (($$2 & 0x10) == 0 && $$3 > 0) {
/* 257 */           int $$8 = $$2 & 0xFFFFFFDE;
/* 258 */           $$6.updateIndirectNeighbourShapes(this, $$0, $$8, $$3 - 1);
/* 259 */           $$1.updateNeighbourShapes(this, $$0, $$8, $$3 - 1);
/* 260 */           $$1.updateIndirectNeighbourShapes(this, $$0, $$8, $$3 - 1);
/*     */         } 
/*     */         
/* 263 */         onBlockStateChange($$0, $$6, $$7);
/*     */       } 
/*     */       
/* 266 */       return true;
/*     */     } 
/*     */     
/* 269 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockStateChange(BlockPos $$0, BlockState $$1, BlockState $$2) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean removeBlock(BlockPos $$0, boolean $$1) {
/* 284 */     FluidState $$2 = getFluidState($$0);
/* 285 */     return setBlock($$0, $$2.createLegacyBlock(), 0x3 | ($$1 ? 64 : 0));
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
/*     */   public boolean destroyBlock(BlockPos $$0, boolean $$1, @Nullable Entity $$2, int $$3) {
/* 298 */     BlockState $$4 = getBlockState($$0);
/* 299 */     if ($$4.isAir()) {
/* 300 */       return false;
/*     */     }
/*     */     
/* 303 */     FluidState $$5 = getFluidState($$0);
/* 304 */     if (!($$4.getBlock() instanceof net.minecraft.world.level.block.BaseFireBlock)) {
/* 305 */       levelEvent(2001, $$0, Block.getId($$4));
/*     */     }
/* 307 */     if ($$1) {
/* 308 */       BlockEntity $$6 = $$4.hasBlockEntity() ? getBlockEntity($$0) : null;
/* 309 */       Block.dropResources($$4, this, $$0, $$6, $$2, ItemStack.EMPTY);
/*     */     } 
/*     */     
/* 312 */     boolean $$7 = setBlock($$0, $$5.createLegacyBlock(), 3, $$3);
/*     */     
/* 314 */     if ($$7) {
/* 315 */       gameEvent(GameEvent.BLOCK_DESTROY, $$0, GameEvent.Context.of($$2, $$4));
/*     */     }
/*     */     
/* 318 */     return $$7;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addDestroyBlockEffect(BlockPos $$0, BlockState $$1) {}
/*     */   
/*     */   public boolean setBlockAndUpdate(BlockPos $$0, BlockState $$1) {
/* 325 */     return setBlock($$0, $$1, 3);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBlocksDirty(BlockPos $$0, BlockState $$1, BlockState $$2) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateNeighborsAt(BlockPos $$0, Block $$1) {}
/*     */ 
/*     */   
/*     */   public void updateNeighborsAtExceptFromFacing(BlockPos $$0, Block $$1, Direction $$2) {}
/*     */ 
/*     */   
/*     */   public void neighborChanged(BlockPos $$0, Block $$1, BlockPos $$2) {}
/*     */ 
/*     */   
/*     */   public void neighborChanged(BlockState $$0, BlockPos $$1, Block $$2, BlockPos $$3, boolean $$4) {}
/*     */ 
/*     */   
/*     */   public void neighborShapeChanged(Direction $$0, BlockState $$1, BlockPos $$2, BlockPos $$3, int $$4, int $$5) {
/* 347 */     this.neighborUpdater.shapeUpdate($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHeight(Heightmap.Types $$0, int $$1, int $$2) {
/*     */     int $$5;
/* 353 */     if ($$1 < -30000000 || $$2 < -30000000 || $$1 >= 30000000 || $$2 >= 30000000) {
/* 354 */       int $$3 = getSeaLevel() + 1;
/* 355 */     } else if (hasChunk(SectionPos.blockToSectionCoord($$1), SectionPos.blockToSectionCoord($$2))) {
/* 356 */       int $$4 = getChunk(SectionPos.blockToSectionCoord($$1), SectionPos.blockToSectionCoord($$2)).getHeight($$0, $$1 & 0xF, $$2 & 0xF) + 1;
/*     */     } else {
/* 358 */       $$5 = getMinBuildHeight();
/*     */     } 
/* 360 */     return $$5;
/*     */   }
/*     */ 
/*     */   
/*     */   public LevelLightEngine getLightEngine() {
/* 365 */     return getChunkSource().getLightEngine();
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState getBlockState(BlockPos $$0) {
/* 370 */     if (isOutsideBuildHeight($$0)) {
/* 371 */       return Blocks.VOID_AIR.defaultBlockState();
/*     */     }
/* 373 */     LevelChunk $$1 = getChunk(SectionPos.blockToSectionCoord($$0.getX()), SectionPos.blockToSectionCoord($$0.getZ()));
/* 374 */     return $$1.getBlockState($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public FluidState getFluidState(BlockPos $$0) {
/* 379 */     if (isOutsideBuildHeight($$0)) {
/* 380 */       return Fluids.EMPTY.defaultFluidState();
/*     */     }
/* 382 */     LevelChunk $$1 = getChunkAt($$0);
/* 383 */     return $$1.getFluidState($$0);
/*     */   }
/*     */   
/*     */   public boolean isDay() {
/* 387 */     return (!dimensionType().hasFixedTime() && this.skyDarken < 4);
/*     */   }
/*     */   
/*     */   public boolean isNight() {
/* 391 */     return (!dimensionType().hasFixedTime() && !isDay());
/*     */   }
/*     */ 
/*     */   
/*     */   public void playSound(@Nullable Entity $$0, BlockPos $$1, SoundEvent $$2, SoundSource $$3, float $$4, float $$5) {
/* 396 */     Player $$6 = (Player)$$0; playSound(($$0 instanceof Player) ? $$6 : null, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void playSound(@Nullable Player $$0, BlockPos $$1, SoundEvent $$2, SoundSource $$3, float $$4, float $$5) {
/* 402 */     playSound($$0, $$1.getX() + 0.5D, $$1.getY() + 0.5D, $$1.getZ() + 0.5D, $$2, $$3, $$4, $$5);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void playSeededSound(@Nullable Player $$0, double $$1, double $$2, double $$3, SoundEvent $$4, SoundSource $$5, float $$6, float $$7, long $$8) {
/* 409 */     playSeededSound($$0, $$1, $$2, $$3, BuiltInRegistries.SOUND_EVENT.wrapAsHolder($$4), $$5, $$6, $$7, $$8);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void playSound(@Nullable Player $$0, double $$1, double $$2, double $$3, SoundEvent $$4, SoundSource $$5) {
/* 417 */     playSound($$0, $$1, $$2, $$3, $$4, $$5, 1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public void playSound(@Nullable Player $$0, double $$1, double $$2, double $$3, SoundEvent $$4, SoundSource $$5, float $$6, float $$7) {
/* 422 */     playSeededSound($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7, this.threadSafeRandom.nextLong());
/*     */   }
/*     */ 
/*     */   
/*     */   public void playSound(@Nullable Player $$0, Entity $$1, SoundEvent $$2, SoundSource $$3, float $$4, float $$5) {
/* 427 */     playSeededSound($$0, $$1, BuiltInRegistries.SOUND_EVENT.wrapAsHolder($$2), $$3, $$4, $$5, this.threadSafeRandom.nextLong());
/*     */   }
/*     */   
/*     */   public void playLocalSound(BlockPos $$0, SoundEvent $$1, SoundSource $$2, float $$3, float $$4, boolean $$5) {
/* 431 */     playLocalSound($$0.getX() + 0.5D, $$0.getY() + 0.5D, $$0.getZ() + 0.5D, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void playLocalSound(Entity $$0, SoundEvent $$1, SoundSource $$2, float $$3, float $$4) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void playLocalSound(double $$0, double $$1, double $$2, SoundEvent $$3, SoundSource $$4, float $$5, float $$6, boolean $$7) {}
/*     */ 
/*     */   
/*     */   public void addParticle(ParticleOptions $$0, double $$1, double $$2, double $$3, double $$4, double $$5, double $$6) {}
/*     */ 
/*     */   
/*     */   public void addParticle(ParticleOptions $$0, boolean $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {}
/*     */ 
/*     */   
/*     */   public void addAlwaysVisibleParticle(ParticleOptions $$0, double $$1, double $$2, double $$3, double $$4, double $$5, double $$6) {}
/*     */ 
/*     */   
/*     */   public void addAlwaysVisibleParticle(ParticleOptions $$0, boolean $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {}
/*     */ 
/*     */   
/*     */   public float getSunAngle(float $$0) {
/* 456 */     float $$1 = getTimeOfDay($$0);
/* 457 */     return $$1 * 6.2831855F;
/*     */   }
/*     */   
/*     */   public void addBlockEntityTicker(TickingBlockEntity $$0) {
/* 461 */     (this.tickingBlockEntities ? this.pendingBlockEntityTickers : this.blockEntityTickers).add($$0);
/*     */   }
/*     */   
/*     */   protected void tickBlockEntities() {
/* 465 */     ProfilerFiller $$0 = getProfiler();
/* 466 */     $$0.push("blockEntities");
/* 467 */     this.tickingBlockEntities = true;
/*     */     
/* 469 */     if (!this.pendingBlockEntityTickers.isEmpty()) {
/* 470 */       this.blockEntityTickers.addAll(this.pendingBlockEntityTickers);
/* 471 */       this.pendingBlockEntityTickers.clear();
/*     */     } 
/*     */     
/* 474 */     Iterator<TickingBlockEntity> $$1 = this.blockEntityTickers.iterator();
/* 475 */     boolean $$2 = tickRateManager().runsNormally();
/* 476 */     while ($$1.hasNext()) {
/* 477 */       TickingBlockEntity $$3 = $$1.next();
/* 478 */       if ($$3.isRemoved()) {
/* 479 */         $$1.remove(); continue;
/*     */       } 
/* 481 */       if ($$2 && shouldTickBlocksAt($$3.getPos())) {
/* 482 */         $$3.tick();
/*     */       }
/*     */     } 
/*     */     
/* 486 */     this.tickingBlockEntities = false;
/* 487 */     $$0.pop();
/*     */   }
/*     */   
/*     */   public <T extends Entity> void guardEntityTick(Consumer<T> $$0, T $$1) {
/*     */     try {
/* 492 */       $$0.accept($$1);
/* 493 */     } catch (Throwable $$2) {
/* 494 */       CrashReport $$3 = CrashReport.forThrowable($$2, "Ticking entity");
/* 495 */       CrashReportCategory $$4 = $$3.addCategory("Entity being ticked");
/*     */       
/* 497 */       $$1.fillCrashReportCategory($$4);
/*     */       
/* 499 */       throw new ReportedException($$3);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean shouldTickDeath(Entity $$0) {
/* 504 */     return true;
/*     */   }
/*     */   
/*     */   public boolean shouldTickBlocksAt(long $$0) {
/* 508 */     return true;
/*     */   }
/*     */   
/*     */   public boolean shouldTickBlocksAt(BlockPos $$0) {
/* 512 */     return shouldTickBlocksAt(ChunkPos.asLong($$0));
/*     */   }
/*     */   
/*     */   public enum ExplosionInteraction {
/* 516 */     NONE,
/* 517 */     BLOCK,
/* 518 */     MOB,
/* 519 */     TNT,
/* 520 */     BLOW;
/*     */   }
/*     */   
/*     */   public Explosion explode(@Nullable Entity $$0, double $$1, double $$2, double $$3, float $$4, ExplosionInteraction $$5) {
/* 524 */     return explode($$0, Explosion.getDefaultDamageSource(this, $$0), (ExplosionDamageCalculator)null, $$1, $$2, $$3, $$4, false, $$5, (ParticleOptions)ParticleTypes.EXPLOSION, (ParticleOptions)ParticleTypes.EXPLOSION_EMITTER, SoundEvents.GENERIC_EXPLODE);
/*     */   }
/*     */   
/*     */   public Explosion explode(@Nullable Entity $$0, double $$1, double $$2, double $$3, float $$4, boolean $$5, ExplosionInteraction $$6) {
/* 528 */     return explode($$0, Explosion.getDefaultDamageSource(this, $$0), (ExplosionDamageCalculator)null, $$1, $$2, $$3, $$4, $$5, $$6, (ParticleOptions)ParticleTypes.EXPLOSION, (ParticleOptions)ParticleTypes.EXPLOSION_EMITTER, SoundEvents.GENERIC_EXPLODE);
/*     */   }
/*     */   
/*     */   public Explosion explode(@Nullable Entity $$0, @Nullable DamageSource $$1, @Nullable ExplosionDamageCalculator $$2, Vec3 $$3, float $$4, boolean $$5, ExplosionInteraction $$6) {
/* 532 */     return explode($$0, $$1, $$2, $$3.x(), $$3.y(), $$3.z(), $$4, $$5, $$6, (ParticleOptions)ParticleTypes.EXPLOSION, (ParticleOptions)ParticleTypes.EXPLOSION_EMITTER, SoundEvents.GENERIC_EXPLODE);
/*     */   }
/*     */   
/*     */   public Explosion explode(@Nullable Entity $$0, @Nullable DamageSource $$1, @Nullable ExplosionDamageCalculator $$2, double $$3, double $$4, double $$5, float $$6, boolean $$7, ExplosionInteraction $$8) {
/* 536 */     return explode($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$8, (ParticleOptions)ParticleTypes.EXPLOSION, (ParticleOptions)ParticleTypes.EXPLOSION_EMITTER, SoundEvents.GENERIC_EXPLODE);
/*     */   }
/*     */   
/*     */   public Explosion explode(@Nullable Entity $$0, @Nullable DamageSource $$1, @Nullable ExplosionDamageCalculator $$2, double $$3, double $$4, double $$5, float $$6, boolean $$7, ExplosionInteraction $$8, ParticleOptions $$9, ParticleOptions $$10, SoundEvent $$11) {
/* 540 */     return explode($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$8, true, $$9, $$10, $$11);
/*     */   }
/*     */   
/*     */   public Explosion explode(@Nullable Entity $$0, @Nullable DamageSource $$1, @Nullable ExplosionDamageCalculator $$2, double $$3, double $$4, double $$5, float $$6, boolean $$7, ExplosionInteraction $$8, boolean $$9, ParticleOptions $$10, ParticleOptions $$11, SoundEvent $$12) {
/* 544 */     switch ($$8) { default: throw new IncompatibleClassChangeError();
/*     */       
/*     */       case NONE:
/*     */       
/*     */       case BLOCK:
/*     */       
/*     */       case MOB:
/* 551 */         $$13 = getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING) ? getDestroyType(GameRules.RULE_MOB_EXPLOSION_DROP_DECAY) : Explosion.BlockInteraction.KEEP;
/*     */         
/* 553 */         $$14 = new Explosion(this, $$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$13, $$10, $$11, $$12);
/* 554 */         $$14.explode();
/* 555 */         $$14.finalizeExplosion($$9);
/* 556 */         return $$14;case TNT: case BLOW: break; }  Explosion.BlockInteraction $$13 = Explosion.BlockInteraction.TRIGGER_BLOCK; Explosion $$14 = new Explosion(this, $$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$13, $$10, $$11, $$12); $$14.explode(); $$14.finalizeExplosion($$9); return $$14;
/*     */   }
/*     */   
/*     */   private Explosion.BlockInteraction getDestroyType(GameRules.Key<GameRules.BooleanValue> $$0) {
/* 560 */     return getGameRules().getBoolean($$0) ? Explosion.BlockInteraction.DESTROY_WITH_DECAY : Explosion.BlockInteraction.DESTROY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BlockEntity getBlockEntity(BlockPos $$0) {
/* 568 */     if (isOutsideBuildHeight($$0)) {
/* 569 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 573 */     if (!this.isClientSide && Thread.currentThread() != this.thread) {
/* 574 */       return null;
/*     */     }
/*     */     
/* 577 */     return getChunkAt($$0).getBlockEntity($$0, LevelChunk.EntityCreationType.IMMEDIATE);
/*     */   }
/*     */   
/*     */   public void setBlockEntity(BlockEntity $$0) {
/* 581 */     BlockPos $$1 = $$0.getBlockPos();
/* 582 */     if (isOutsideBuildHeight($$1)) {
/*     */       return;
/*     */     }
/*     */     
/* 586 */     getChunkAt($$1).addAndRegisterBlockEntity($$0);
/*     */   }
/*     */   
/*     */   public void removeBlockEntity(BlockPos $$0) {
/* 590 */     if (isOutsideBuildHeight($$0)) {
/*     */       return;
/*     */     }
/*     */     
/* 594 */     getChunkAt($$0).removeBlockEntity($$0);
/*     */   }
/*     */   
/*     */   public boolean isLoaded(BlockPos $$0) {
/* 598 */     if (isOutsideBuildHeight($$0)) {
/* 599 */       return false;
/*     */     }
/* 601 */     return getChunkSource().hasChunk(SectionPos.blockToSectionCoord($$0.getX()), SectionPos.blockToSectionCoord($$0.getZ()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean loadedAndEntityCanStandOnFace(BlockPos $$0, Entity $$1, Direction $$2) {
/* 610 */     if (isOutsideBuildHeight($$0)) {
/* 611 */       return false;
/*     */     }
/*     */     
/* 614 */     ChunkAccess $$3 = getChunk(SectionPos.blockToSectionCoord($$0.getX()), SectionPos.blockToSectionCoord($$0.getZ()), ChunkStatus.FULL, false);
/* 615 */     if ($$3 == null) {
/* 616 */       return false;
/*     */     }
/*     */     
/* 619 */     return $$3.getBlockState($$0).entityCanStandOnFace(this, $$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   public boolean loadedAndEntityCanStandOn(BlockPos $$0, Entity $$1) {
/* 623 */     return loadedAndEntityCanStandOnFace($$0, $$1, Direction.UP);
/*     */   }
/*     */   
/*     */   public void updateSkyBrightness() {
/* 627 */     double $$0 = 1.0D - (getRainLevel(1.0F) * 5.0F) / 16.0D;
/* 628 */     double $$1 = 1.0D - (getThunderLevel(1.0F) * 5.0F) / 16.0D;
/*     */     
/* 630 */     double $$2 = 0.5D + 2.0D * Mth.clamp(Mth.cos(getTimeOfDay(1.0F) * 6.2831855F), -0.25D, 0.25D);
/*     */     
/* 632 */     this.skyDarken = (int)((1.0D - $$2 * $$0 * $$1) * 11.0D);
/*     */   }
/*     */   
/*     */   public void setSpawnSettings(boolean $$0, boolean $$1) {
/* 636 */     getChunkSource().setSpawnSettings($$0, $$1);
/*     */   }
/*     */   
/*     */   public BlockPos getSharedSpawnPos() {
/* 640 */     BlockPos $$0 = new BlockPos(this.levelData.getXSpawn(), this.levelData.getYSpawn(), this.levelData.getZSpawn());
/* 641 */     if (!getWorldBorder().isWithinBounds($$0)) {
/* 642 */       $$0 = getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, BlockPos.containing(getWorldBorder().getCenterX(), 0.0D, getWorldBorder().getCenterZ()));
/*     */     }
/* 644 */     return $$0;
/*     */   }
/*     */   
/*     */   public float getSharedSpawnAngle() {
/* 648 */     return this.levelData.getSpawnAngle();
/*     */   }
/*     */   
/*     */   protected void prepareWeather() {
/* 652 */     if (this.levelData.isRaining()) {
/* 653 */       this.rainLevel = 1.0F;
/* 654 */       if (this.levelData.isThundering()) {
/* 655 */         this.thunderLevel = 1.0F;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 662 */     getChunkSource().close();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BlockGetter getChunkForCollisions(int $$0, int $$1) {
/* 668 */     return (BlockGetter)getChunk($$0, $$1, ChunkStatus.FULL, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Entity> getEntities(@Nullable Entity $$0, AABB $$1, Predicate<? super Entity> $$2) {
/* 673 */     getProfiler().incrementCounter("getEntities");
/*     */     
/* 675 */     List<Entity> $$3 = Lists.newArrayList();
/* 676 */     getEntities().get($$1, $$3 -> {
/*     */           if ($$3 != $$0 && $$1.test($$3)) {
/*     */             $$2.add($$3);
/*     */           }
/*     */           
/*     */           if ($$3 instanceof EnderDragon) {
/*     */             for (EnderDragonPart $$4 : ((EnderDragon)$$3).getSubEntities()) {
/*     */               if ($$3 != $$0 && $$1.test($$4)) {
/*     */                 $$2.add($$4);
/*     */               }
/*     */             } 
/*     */           }
/*     */         });
/* 689 */     return $$3;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T extends Entity> List<T> getEntities(EntityTypeTest<Entity, T> $$0, AABB $$1, Predicate<? super T> $$2) {
/* 694 */     List<T> $$3 = Lists.newArrayList();
/* 695 */     getEntities($$0, $$1, $$2, $$3);
/* 696 */     return $$3;
/*     */   }
/*     */   
/*     */   public <T extends Entity> void getEntities(EntityTypeTest<Entity, T> $$0, AABB $$1, Predicate<? super T> $$2, List<? super T> $$3) {
/* 700 */     getEntities($$0, $$1, $$2, $$3, 2147483647);
/*     */   }
/*     */   
/*     */   public <T extends Entity> void getEntities(EntityTypeTest<Entity, T> $$0, AABB $$1, Predicate<? super T> $$2, List<? super T> $$3, int $$4) {
/* 704 */     getProfiler().incrementCounter("getEntities");
/*     */     
/* 706 */     getEntities().get($$0, $$1, $$4 -> {
/*     */           if ($$0.test($$4)) {
/*     */             $$1.add($$4);
/*     */             if ($$1.size() >= $$2) {
/*     */               return AbortableIterationConsumer.Continuation.ABORT;
/*     */             }
/*     */           } 
/*     */           if ($$4 instanceof EnderDragon) {
/*     */             EnderDragon $$5 = (EnderDragon)$$4;
/*     */             for (EnderDragonPart $$6 : $$5.getSubEntities()) {
/*     */               Entity entity = (Entity)$$3.tryCast($$6);
/*     */               if (entity != null && $$0.test(entity)) {
/*     */                 $$1.add(entity);
/*     */                 if ($$1.size() >= $$2) {
/*     */                   return AbortableIterationConsumer.Continuation.ABORT;
/*     */                 }
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           return AbortableIterationConsumer.Continuation.CONTINUE;
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void blockEntityChanged(BlockPos $$0) {
/* 733 */     if (hasChunkAt($$0)) {
/* 734 */       getChunkAt($$0).setUnsaved(true);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSeaLevel() {
/* 740 */     return 63;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void disconnect() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public long getGameTime() {
/* 750 */     return this.levelData.getGameTime();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getDayTime() {
/* 758 */     return this.levelData.getDayTime();
/*     */   }
/*     */   
/*     */   public boolean mayInteract(Player $$0, BlockPos $$1) {
/* 762 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void broadcastEntityEvent(Entity $$0, byte $$1) {}
/*     */ 
/*     */   
/*     */   public void broadcastDamageEvent(Entity $$0, DamageSource $$1) {}
/*     */   
/*     */   public void blockEvent(BlockPos $$0, Block $$1, int $$2, int $$3) {
/* 772 */     getBlockState($$0).triggerEvent(this, $$0, $$2, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public LevelData getLevelData() {
/* 777 */     return (LevelData)this.levelData;
/*     */   }
/*     */   
/*     */   public GameRules getGameRules() {
/* 781 */     return this.levelData.getGameRules();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getThunderLevel(float $$0) {
/* 787 */     return Mth.lerp($$0, this.oThunderLevel, this.thunderLevel) * getRainLevel($$0);
/*     */   }
/*     */   
/*     */   public void setThunderLevel(float $$0) {
/* 791 */     float $$1 = Mth.clamp($$0, 0.0F, 1.0F);
/* 792 */     this.oThunderLevel = $$1;
/* 793 */     this.thunderLevel = $$1;
/*     */   }
/*     */   
/*     */   public float getRainLevel(float $$0) {
/* 797 */     return Mth.lerp($$0, this.oRainLevel, this.rainLevel);
/*     */   }
/*     */   
/*     */   public void setRainLevel(float $$0) {
/* 801 */     float $$1 = Mth.clamp($$0, 0.0F, 1.0F);
/* 802 */     this.oRainLevel = $$1;
/* 803 */     this.rainLevel = $$1;
/*     */   }
/*     */   
/*     */   public boolean isThundering() {
/* 807 */     if (!dimensionType().hasSkyLight() || dimensionType().hasCeiling()) {
/* 808 */       return false;
/*     */     }
/* 810 */     return (getThunderLevel(1.0F) > 0.9D);
/*     */   }
/*     */   
/*     */   public boolean isRaining() {
/* 814 */     return (getRainLevel(1.0F) > 0.2D);
/*     */   }
/*     */   
/*     */   public boolean isRainingAt(BlockPos $$0) {
/* 818 */     if (!isRaining()) {
/* 819 */       return false;
/*     */     }
/* 821 */     if (!canSeeSky($$0)) {
/* 822 */       return false;
/*     */     }
/* 824 */     if (getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, $$0).getY() > $$0.getY()) {
/* 825 */       return false;
/*     */     }
/*     */     
/* 828 */     Biome $$1 = (Biome)getBiome($$0).value();
/*     */     
/* 830 */     return ($$1.getPrecipitationAt($$0) == Biome.Precipitation.RAIN);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void globalLevelEvent(int $$0, BlockPos $$1, int $$2) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CrashReportCategory fillReportDetails(CrashReport $$0) {
/* 844 */     CrashReportCategory $$1 = $$0.addCategory("Affected level", 1);
/*     */     
/* 846 */     $$1.setDetail("All players", () -> "" + players().size() + " total; " + players().size());
/* 847 */     Objects.requireNonNull(getChunkSource()); $$1.setDetail("Chunk stats", getChunkSource()::gatherStats);
/* 848 */     $$1.setDetail("Level dimension", () -> dimension().location().toString());
/*     */     
/*     */     try {
/* 851 */       this.levelData.fillCrashReportCategory($$1, this);
/* 852 */     } catch (Throwable $$2) {
/* 853 */       $$1.setDetailError("Level Data Unobtainable", $$2);
/*     */     } 
/*     */     
/* 856 */     return $$1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void createFireworks(double $$0, double $$1, double $$2, double $$3, double $$4, double $$5, @Nullable CompoundTag $$6) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateNeighbourForOutputSignal(BlockPos $$0, Block $$1) {
/* 867 */     for (Direction $$2 : Direction.Plane.HORIZONTAL) {
/* 868 */       BlockPos $$3 = $$0.relative($$2);
/*     */       
/* 870 */       if (hasChunkAt($$3)) {
/* 871 */         BlockState $$4 = getBlockState($$3);
/* 872 */         if ($$4.is(Blocks.COMPARATOR)) {
/* 873 */           neighborChanged($$4, $$3, $$1, $$0, false); continue;
/* 874 */         }  if ($$4.isRedstoneConductor(this, $$3)) {
/* 875 */           $$3 = $$3.relative($$2);
/* 876 */           $$4 = getBlockState($$3);
/*     */           
/* 878 */           if ($$4.is(Blocks.COMPARATOR)) {
/* 879 */             neighborChanged($$4, $$3, $$1, $$0, false);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public DifficultyInstance getCurrentDifficultyAt(BlockPos $$0) {
/* 888 */     long $$1 = 0L;
/* 889 */     float $$2 = 0.0F;
/* 890 */     if (hasChunkAt($$0)) {
/* 891 */       $$2 = getMoonBrightness();
/* 892 */       $$1 = getChunkAt($$0).getInhabitedTime();
/*     */     } 
/*     */     
/* 895 */     return new DifficultyInstance(getDifficulty(), getDayTime(), $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSkyDarken() {
/* 900 */     return this.skyDarken;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSkyFlashTime(int $$0) {}
/*     */ 
/*     */   
/*     */   public WorldBorder getWorldBorder() {
/* 908 */     return this.worldBorder;
/*     */   }
/*     */   
/*     */   public void sendPacketToServer(Packet<?> $$0) {
/* 912 */     throw new UnsupportedOperationException("Can't send packets to server unless you're on the client.");
/*     */   }
/*     */ 
/*     */   
/*     */   public DimensionType dimensionType() {
/* 917 */     return (DimensionType)this.dimensionTypeRegistration.value();
/*     */   }
/*     */   
/*     */   public ResourceKey<DimensionType> dimensionTypeId() {
/* 921 */     return this.dimensionTypeId;
/*     */   }
/*     */   
/*     */   public Holder<DimensionType> dimensionTypeRegistration() {
/* 925 */     return this.dimensionTypeRegistration;
/*     */   }
/*     */   
/*     */   public ResourceKey<Level> dimension() {
/* 929 */     return this.dimension;
/*     */   }
/*     */ 
/*     */   
/*     */   public RandomSource getRandom() {
/* 934 */     return this.random;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isStateAtPosition(BlockPos $$0, Predicate<BlockState> $$1) {
/* 939 */     return $$1.test(getBlockState($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFluidAtPosition(BlockPos $$0, Predicate<FluidState> $$1) {
/* 944 */     return $$1.test(getFluidState($$0));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos getBlockRandomPos(int $$0, int $$1, int $$2, int $$3) {
/* 950 */     this.randValue = this.randValue * 3 + 1013904223;
/* 951 */     int $$4 = this.randValue >> 2;
/*     */     
/* 953 */     return new BlockPos($$0 + ($$4 & 0xF), $$1 + ($$4 >> 16 & $$3), $$2 + ($$4 >> 8 & 0xF));
/*     */   }
/*     */   
/*     */   public boolean noSave() {
/* 957 */     return false;
/*     */   }
/*     */   
/*     */   public ProfilerFiller getProfiler() {
/* 961 */     return this.profiler.get();
/*     */   }
/*     */   
/*     */   public Supplier<ProfilerFiller> getProfilerSupplier() {
/* 965 */     return this.profiler;
/*     */   }
/*     */ 
/*     */   
/*     */   public BiomeManager getBiomeManager() {
/* 970 */     return this.biomeManager;
/*     */   }
/*     */   
/*     */   public final boolean isDebug() {
/* 974 */     return this.isDebug;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long nextSubTickCount() {
/* 981 */     return this.subTickCount++;
/*     */   }
/*     */ 
/*     */   
/*     */   public RegistryAccess registryAccess() {
/* 986 */     return this.registryAccess;
/*     */   }
/*     */   
/*     */   public DamageSources damageSources() {
/* 990 */     return this.damageSources;
/*     */   }
/*     */   
/*     */   public abstract void sendBlockUpdated(BlockPos paramBlockPos, BlockState paramBlockState1, BlockState paramBlockState2, int paramInt);
/*     */   
/*     */   public abstract void playSeededSound(@Nullable Player paramPlayer, double paramDouble1, double paramDouble2, double paramDouble3, Holder<SoundEvent> paramHolder, SoundSource paramSoundSource, float paramFloat1, float paramFloat2, long paramLong);
/*     */   
/*     */   public abstract void playSeededSound(@Nullable Player paramPlayer, Entity paramEntity, Holder<SoundEvent> paramHolder, SoundSource paramSoundSource, float paramFloat1, float paramFloat2, long paramLong);
/*     */   
/*     */   public abstract String gatherChunkSourceStats();
/*     */   
/*     */   @Nullable
/*     */   public abstract Entity getEntity(int paramInt);
/*     */   
/*     */   public abstract TickRateManager tickRateManager();
/*     */   
/*     */   @Nullable
/*     */   public abstract MapItemSavedData getMapData(String paramString);
/*     */   
/*     */   public abstract void setMapData(String paramString, MapItemSavedData paramMapItemSavedData);
/*     */   
/*     */   public abstract int getFreeMapId();
/*     */   
/*     */   public abstract void destroyBlockProgress(int paramInt1, BlockPos paramBlockPos, int paramInt2);
/*     */   
/*     */   public abstract Scoreboard getScoreboard();
/*     */   
/*     */   public abstract RecipeManager getRecipeManager();
/*     */   
/*     */   protected abstract LevelEntityGetter<Entity> getEntities();
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\Level.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */