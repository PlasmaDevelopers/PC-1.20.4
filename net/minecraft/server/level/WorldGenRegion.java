/*     */ package net.minecraft.server.level;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.concurrent.atomic.AtomicLong;
/*     */ import java.util.function.Predicate;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.RegistryAccess;
/*     */ import net.minecraft.core.SectionPos;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.flag.FeatureFlagSet;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.LevelHeightAccessor;
/*     */ import net.minecraft.world.level.StructureManager;
/*     */ import net.minecraft.world.level.WorldGenLevel;
/*     */ import net.minecraft.world.level.biome.Biome;
/*     */ import net.minecraft.world.level.biome.BiomeManager;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.EntityBlock;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.border.WorldBorder;
/*     */ import net.minecraft.world.level.chunk.ChunkAccess;
/*     */ import net.minecraft.world.level.chunk.ChunkSource;
/*     */ import net.minecraft.world.level.chunk.ChunkStatus;
/*     */ import net.minecraft.world.level.dimension.DimensionType;
/*     */ import net.minecraft.world.level.entity.EntityTypeTest;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.level.levelgen.Heightmap;
/*     */ import net.minecraft.world.level.lighting.LevelLightEngine;
/*     */ import net.minecraft.world.level.material.Fluid;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ import net.minecraft.world.level.storage.LevelData;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import net.minecraft.world.ticks.LevelTickAccess;
/*     */ import net.minecraft.world.ticks.TickContainerAccess;
/*     */ import net.minecraft.world.ticks.WorldGenTickAccess;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class WorldGenRegion implements WorldGenLevel {
/*  61 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private final List<ChunkAccess> cache;
/*     */   
/*     */   private final ChunkAccess center;
/*     */   private final int size;
/*     */   private final ServerLevel level;
/*     */   private final long seed;
/*     */   private final LevelData levelData;
/*     */   private final RandomSource random;
/*     */   private final DimensionType dimensionType;
/*     */   private final WorldGenTickAccess<Block> blockTicks;
/*     */   private final WorldGenTickAccess<Fluid> fluidTicks;
/*     */   private final BiomeManager biomeManager;
/*     */   private final ChunkPos firstPos;
/*     */   private final ChunkPos lastPos;
/*     */   private final StructureManager structureManager;
/*     */   private final ChunkStatus generatingStatus;
/*     */   private final int writeRadiusCutoff;
/*     */   @Nullable
/*     */   private Supplier<String> currentlyGenerating;
/*     */   private final AtomicLong subTickCount;
/*  83 */   private static final ResourceLocation WORLDGEN_REGION_RANDOM = new ResourceLocation("worldgen_region_random"); public WorldGenRegion(ServerLevel $$0, List<ChunkAccess> $$1, ChunkStatus $$2, int $$3) { this.blockTicks = new WorldGenTickAccess($$0 -> getChunk($$0).getBlockTicks());
/*     */     this.fluidTicks = new WorldGenTickAccess($$0 -> getChunk($$0).getFluidTicks());
/*     */     this.subTickCount = new AtomicLong();
/*  86 */     this.generatingStatus = $$2;
/*  87 */     this.writeRadiusCutoff = $$3;
/*  88 */     int $$4 = Mth.floor(Math.sqrt($$1.size()));
/*  89 */     if ($$4 * $$4 != $$1.size()) {
/*  90 */       throw (IllegalStateException)Util.pauseInIde(new IllegalStateException("Cache size is not a square."));
/*     */     }
/*     */     
/*  93 */     this.cache = $$1;
/*  94 */     this.center = $$1.get($$1.size() / 2);
/*  95 */     this.size = $$4;
/*  96 */     this.level = $$0;
/*  97 */     this.seed = $$0.getSeed();
/*  98 */     this.levelData = $$0.getLevelData();
/*  99 */     this.random = $$0.getChunkSource().randomState().getOrCreateRandomFactory(WORLDGEN_REGION_RANDOM).at(this.center.getPos().getWorldPosition());
/*     */     
/* 101 */     this.dimensionType = $$0.dimensionType();
/* 102 */     this.biomeManager = new BiomeManager((BiomeManager.NoiseBiomeSource)this, BiomeManager.obfuscateSeed(this.seed));
/* 103 */     this.firstPos = ((ChunkAccess)$$1.get(0)).getPos();
/* 104 */     this.lastPos = ((ChunkAccess)$$1.get($$1.size() - 1)).getPos();
/* 105 */     this.structureManager = $$0.structureManager().forWorldGenRegion(this); }
/*     */ 
/*     */   
/*     */   public boolean isOldChunkAround(ChunkPos $$0, int $$1) {
/* 109 */     return (this.level.getChunkSource()).chunkMap.isOldChunkAround($$0, $$1);
/*     */   }
/*     */   
/*     */   public ChunkPos getCenter() {
/* 113 */     return this.center.getPos();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCurrentlyGenerating(@Nullable Supplier<String> $$0) {
/* 118 */     this.currentlyGenerating = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChunkAccess getChunk(int $$0, int $$1) {
/* 123 */     return getChunk($$0, $$1, ChunkStatus.EMPTY);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public ChunkAccess getChunk(int $$0, int $$1, ChunkStatus $$2, boolean $$3) {
/*     */     ChunkAccess $$7;
/* 130 */     if (hasChunk($$0, $$1)) {
/* 131 */       int $$4 = $$0 - this.firstPos.x;
/* 132 */       int $$5 = $$1 - this.firstPos.z;
/* 133 */       ChunkAccess $$6 = this.cache.get($$4 + $$5 * this.size);
/* 134 */       if ($$6.getStatus().isOrAfter($$2)) {
/* 135 */         return $$6;
/*     */       }
/*     */     } else {
/* 138 */       $$7 = null;
/*     */     } 
/*     */     
/* 141 */     if (!$$3) {
/* 142 */       return null;
/*     */     }
/*     */     
/* 145 */     LOGGER.error("Requested chunk : {} {}", Integer.valueOf($$0), Integer.valueOf($$1));
/* 146 */     LOGGER.error("Region bounds : {} {} | {} {}", new Object[] { Integer.valueOf(this.firstPos.x), Integer.valueOf(this.firstPos.z), Integer.valueOf(this.lastPos.x), Integer.valueOf(this.lastPos.z) });
/* 147 */     if ($$7 != null) {
/* 148 */       throw (RuntimeException)Util.pauseInIde(new RuntimeException(String.format(Locale.ROOT, "Chunk is not of correct status. Expecting %s, got %s | %s %s", new Object[] { $$2, $$7.getStatus(), Integer.valueOf($$0), Integer.valueOf($$1) })));
/*     */     }
/* 150 */     throw (RuntimeException)Util.pauseInIde(new RuntimeException(String.format(Locale.ROOT, "We are asking a region for a chunk out of bound | %s %s", new Object[] { Integer.valueOf($$0), Integer.valueOf($$1) })));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasChunk(int $$0, int $$1) {
/* 156 */     return ($$0 >= this.firstPos.x && $$0 <= this.lastPos.x && $$1 >= this.firstPos.z && $$1 <= this.lastPos.z);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState getBlockState(BlockPos $$0) {
/* 161 */     return getChunk(SectionPos.blockToSectionCoord($$0.getX()), SectionPos.blockToSectionCoord($$0.getZ())).getBlockState($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public FluidState getFluidState(BlockPos $$0) {
/* 166 */     return getChunk($$0).getFluidState($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Player getNearestPlayer(double $$0, double $$1, double $$2, double $$3, Predicate<Entity> $$4) {
/* 172 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSkyDarken() {
/* 177 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public BiomeManager getBiomeManager() {
/* 182 */     return this.biomeManager;
/*     */   }
/*     */ 
/*     */   
/*     */   public Holder<Biome> getUncachedNoiseBiome(int $$0, int $$1, int $$2) {
/* 187 */     return this.level.getUncachedNoiseBiome($$0, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getShade(Direction $$0, boolean $$1) {
/* 192 */     return 1.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public LevelLightEngine getLightEngine() {
/* 197 */     return this.level.getLightEngine();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean destroyBlock(BlockPos $$0, boolean $$1, @Nullable Entity $$2, int $$3) {
/* 202 */     BlockState $$4 = getBlockState($$0);
/* 203 */     if ($$4.isAir()) {
/* 204 */       return false;
/*     */     }
/*     */     
/* 207 */     if ($$1) {
/* 208 */       BlockEntity $$5 = $$4.hasBlockEntity() ? getBlockEntity($$0) : null;
/* 209 */       Block.dropResources($$4, this.level, $$0, $$5, $$2, ItemStack.EMPTY);
/*     */     } 
/* 211 */     return setBlock($$0, Blocks.AIR.defaultBlockState(), 3, $$3);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BlockEntity getBlockEntity(BlockPos $$0) {
/* 218 */     ChunkAccess $$1 = getChunk($$0);
/* 219 */     BlockEntity $$2 = $$1.getBlockEntity($$0);
/*     */     
/* 221 */     if ($$2 != null) {
/* 222 */       return $$2;
/*     */     }
/*     */     
/* 225 */     CompoundTag $$3 = $$1.getBlockEntityNbt($$0);
/* 226 */     BlockState $$4 = $$1.getBlockState($$0);
/* 227 */     if ($$3 != null) {
/* 228 */       if ("DUMMY".equals($$3.getString("id"))) {
/* 229 */         if (!$$4.hasBlockEntity()) {
/* 230 */           return null;
/*     */         }
/* 232 */         $$2 = ((EntityBlock)$$4.getBlock()).newBlockEntity($$0, $$4);
/*     */       } else {
/* 234 */         $$2 = BlockEntity.loadStatic($$0, $$4, $$3);
/*     */       } 
/*     */       
/* 237 */       if ($$2 != null) {
/* 238 */         $$1.setBlockEntity($$2);
/* 239 */         return $$2;
/*     */       } 
/*     */     } 
/*     */     
/* 243 */     if ($$4.hasBlockEntity()) {
/* 244 */       LOGGER.warn("Tried to access a block entity before it was created. {}", $$0);
/*     */     }
/*     */     
/* 247 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean ensureCanWrite(BlockPos $$0) {
/* 252 */     int $$1 = SectionPos.blockToSectionCoord($$0.getX());
/* 253 */     int $$2 = SectionPos.blockToSectionCoord($$0.getZ());
/*     */     
/* 255 */     ChunkPos $$3 = getCenter();
/* 256 */     int $$4 = Math.abs($$3.x - $$1);
/* 257 */     int $$5 = Math.abs($$3.z - $$2);
/*     */     
/* 259 */     if ($$4 > this.writeRadiusCutoff || $$5 > this.writeRadiusCutoff) {
/* 260 */       Util.logAndPauseIfInIde("Detected setBlock in a far chunk [" + $$1 + ", " + $$2 + "], pos: " + $$0 + ", status: " + this.generatingStatus + ((this.currentlyGenerating == null) ? "" : (", currently generating: " + (String)this.currentlyGenerating.get())));
/* 261 */       return false;
/*     */     } 
/*     */     
/* 264 */     if (this.center.isUpgrading()) {
/* 265 */       LevelHeightAccessor $$6 = this.center.getHeightAccessorForGeneration();
/* 266 */       if ($$0.getY() < $$6.getMinBuildHeight() || $$0.getY() >= $$6.getMaxBuildHeight()) {
/* 267 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 271 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean setBlock(BlockPos $$0, BlockState $$1, int $$2, int $$3) {
/* 276 */     if (!ensureCanWrite($$0)) {
/* 277 */       return false;
/*     */     }
/*     */     
/* 280 */     ChunkAccess $$4 = getChunk($$0);
/* 281 */     BlockState $$5 = $$4.setBlockState($$0, $$1, false);
/*     */     
/* 283 */     if ($$5 != null) {
/* 284 */       this.level.onBlockStateChange($$0, $$5, $$1);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 289 */     if ($$1.hasBlockEntity()) {
/* 290 */       if ($$4.getStatus().getChunkType() == ChunkStatus.ChunkType.LEVELCHUNK) {
/* 291 */         BlockEntity $$6 = ((EntityBlock)$$1.getBlock()).newBlockEntity($$0, $$1);
/* 292 */         if ($$6 != null) {
/* 293 */           $$4.setBlockEntity($$6);
/*     */         } else {
/* 295 */           $$4.removeBlockEntity($$0);
/*     */         } 
/*     */       } else {
/* 298 */         CompoundTag $$7 = new CompoundTag();
/* 299 */         $$7.putInt("x", $$0.getX());
/* 300 */         $$7.putInt("y", $$0.getY());
/* 301 */         $$7.putInt("z", $$0.getZ());
/* 302 */         $$7.putString("id", "DUMMY");
/* 303 */         $$4.setBlockEntityNbt($$7);
/*     */       } 
/* 305 */     } else if ($$5 != null && $$5.hasBlockEntity()) {
/* 306 */       $$4.removeBlockEntity($$0);
/*     */     } 
/*     */     
/* 309 */     if ($$1.hasPostProcess((BlockGetter)this, $$0)) {
/* 310 */       markPosForPostprocessing($$0);
/*     */     }
/*     */     
/* 313 */     return true;
/*     */   }
/*     */   
/*     */   private void markPosForPostprocessing(BlockPos $$0) {
/* 317 */     getChunk($$0).markPosForPostprocessing($$0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addFreshEntity(Entity $$0) {
/* 325 */     int $$1 = SectionPos.blockToSectionCoord($$0.getBlockX());
/* 326 */     int $$2 = SectionPos.blockToSectionCoord($$0.getBlockZ());
/*     */     
/* 328 */     getChunk($$1, $$2).addEntity($$0);
/* 329 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean removeBlock(BlockPos $$0, boolean $$1) {
/* 334 */     return setBlock($$0, Blocks.AIR.defaultBlockState(), 3);
/*     */   }
/*     */ 
/*     */   
/*     */   public WorldBorder getWorldBorder() {
/* 339 */     return this.level.getWorldBorder();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isClientSide() {
/* 344 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public ServerLevel getLevel() {
/* 350 */     return this.level;
/*     */   }
/*     */ 
/*     */   
/*     */   public RegistryAccess registryAccess() {
/* 355 */     return this.level.registryAccess();
/*     */   }
/*     */ 
/*     */   
/*     */   public FeatureFlagSet enabledFeatures() {
/* 360 */     return this.level.enabledFeatures();
/*     */   }
/*     */ 
/*     */   
/*     */   public LevelData getLevelData() {
/* 365 */     return this.levelData;
/*     */   }
/*     */ 
/*     */   
/*     */   public DifficultyInstance getCurrentDifficultyAt(BlockPos $$0) {
/* 370 */     if (!hasChunk(SectionPos.blockToSectionCoord($$0.getX()), SectionPos.blockToSectionCoord($$0.getZ()))) {
/* 371 */       throw new RuntimeException("We are asking a region for a chunk out of bound");
/*     */     }
/*     */     
/* 374 */     return new DifficultyInstance(this.level.getDifficulty(), this.level.getDayTime(), 0L, this.level.getMoonBrightness());
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public MinecraftServer getServer() {
/* 380 */     return this.level.getServer();
/*     */   }
/*     */ 
/*     */   
/*     */   public ChunkSource getChunkSource() {
/* 385 */     return this.level.getChunkSource();
/*     */   }
/*     */ 
/*     */   
/*     */   public long getSeed() {
/* 390 */     return this.seed;
/*     */   }
/*     */ 
/*     */   
/*     */   public LevelTickAccess<Block> getBlockTicks() {
/* 395 */     return (LevelTickAccess<Block>)this.blockTicks;
/*     */   }
/*     */ 
/*     */   
/*     */   public LevelTickAccess<Fluid> getFluidTicks() {
/* 400 */     return (LevelTickAccess<Fluid>)this.fluidTicks;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSeaLevel() {
/* 405 */     return this.level.getSeaLevel();
/*     */   }
/*     */ 
/*     */   
/*     */   public RandomSource getRandom() {
/* 410 */     return this.random;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHeight(Heightmap.Types $$0, int $$1, int $$2) {
/* 415 */     return getChunk(SectionPos.blockToSectionCoord($$1), SectionPos.blockToSectionCoord($$2)).getHeight($$0, $$1 & 0xF, $$2 & 0xF) + 1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void playSound(@Nullable Player $$0, BlockPos $$1, SoundEvent $$2, SoundSource $$3, float $$4, float $$5) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void addParticle(ParticleOptions $$0, double $$1, double $$2, double $$3, double $$4, double $$5, double $$6) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void levelEvent(@Nullable Player $$0, int $$1, BlockPos $$2, int $$3) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void gameEvent(GameEvent $$0, Vec3 $$1, GameEvent.Context $$2) {}
/*     */ 
/*     */   
/*     */   public DimensionType dimensionType() {
/* 436 */     return this.dimensionType;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isStateAtPosition(BlockPos $$0, Predicate<BlockState> $$1) {
/* 441 */     return $$1.test(getBlockState($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFluidAtPosition(BlockPos $$0, Predicate<FluidState> $$1) {
/* 446 */     return $$1.test(getFluidState($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public <T extends Entity> List<T> getEntities(EntityTypeTest<Entity, T> $$0, AABB $$1, Predicate<? super T> $$2) {
/* 451 */     return Collections.emptyList();
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Entity> getEntities(@Nullable Entity $$0, AABB $$1, @Nullable Predicate<? super Entity> $$2) {
/* 456 */     return Collections.emptyList();
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Player> players() {
/* 461 */     return Collections.emptyList();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMinBuildHeight() {
/* 466 */     return this.level.getMinBuildHeight();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHeight() {
/* 471 */     return this.level.getHeight();
/*     */   }
/*     */ 
/*     */   
/*     */   public long nextSubTickCount() {
/* 476 */     return this.subTickCount.getAndIncrement();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\level\WorldGenRegion.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */