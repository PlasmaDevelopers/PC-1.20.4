/*     */ package net.minecraft.world.level.chunk;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
/*     */ import it.unimi.dsi.fastutil.longs.LongSet;
/*     */ import it.unimi.dsi.fastutil.shorts.ShortArrayList;
/*     */ import it.unimi.dsi.fastutil.shorts.ShortList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.EnumSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Predicate;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.CrashReport;
/*     */ import net.minecraft.CrashReportCategory;
/*     */ import net.minecraft.ReportedException;
/*     */ import net.minecraft.SharedConstants;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.QuartPos;
/*     */ import net.minecraft.core.Registry;
/*     */ import net.minecraft.core.SectionPos;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.LevelHeightAccessor;
/*     */ import net.minecraft.world.level.biome.Biome;
/*     */ import net.minecraft.world.level.biome.BiomeGenerationSettings;
/*     */ import net.minecraft.world.level.biome.BiomeManager;
/*     */ import net.minecraft.world.level.biome.BiomeResolver;
/*     */ import net.minecraft.world.level.biome.Climate;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.gameevent.GameEventListenerRegistry;
/*     */ import net.minecraft.world.level.levelgen.BelowZeroRetrogen;
/*     */ import net.minecraft.world.level.levelgen.Heightmap;
/*     */ import net.minecraft.world.level.levelgen.NoiseChunk;
/*     */ import net.minecraft.world.level.levelgen.blending.BlendingData;
/*     */ import net.minecraft.world.level.levelgen.structure.Structure;
/*     */ import net.minecraft.world.level.levelgen.structure.StructureStart;
/*     */ import net.minecraft.world.level.lighting.ChunkSkyLightSources;
/*     */ import net.minecraft.world.level.material.Fluid;
/*     */ import net.minecraft.world.ticks.SerializableTickContainer;
/*     */ import net.minecraft.world.ticks.TickContainerAccess;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public abstract class ChunkAccess
/*     */   implements BlockGetter, BiomeManager.NoiseBiomeSource, LightChunk, StructureAccess {
/*     */   public static final int NO_FILLED_SECTION = -1;
/*  60 */   private static final Logger LOGGER = LogUtils.getLogger();
/*  61 */   private static final LongSet EMPTY_REFERENCE_SET = (LongSet)new LongOpenHashSet();
/*     */   
/*     */   protected final ShortList[] postProcessing;
/*     */   
/*     */   protected volatile boolean unsaved;
/*     */   
/*     */   private volatile boolean isLightCorrect;
/*     */   
/*     */   protected final ChunkPos chunkPos;
/*     */   
/*     */   private long inhabitedTime;
/*     */   @Nullable
/*     */   @Deprecated
/*     */   private BiomeGenerationSettings carverBiomeSettings;
/*     */   @Nullable
/*     */   protected NoiseChunk noiseChunk;
/*     */   protected final UpgradeData upgradeData;
/*     */   @Nullable
/*     */   protected BlendingData blendingData;
/*  80 */   protected final Map<Heightmap.Types, Heightmap> heightmaps = Maps.newEnumMap(Heightmap.Types.class);
/*     */   
/*     */   protected ChunkSkyLightSources skyLightSources;
/*  83 */   private final Map<Structure, StructureStart> structureStarts = Maps.newHashMap();
/*  84 */   private final Map<Structure, LongSet> structuresRefences = Maps.newHashMap();
/*     */   
/*  86 */   protected final Map<BlockPos, CompoundTag> pendingBlockEntities = Maps.newHashMap();
/*  87 */   protected final Map<BlockPos, BlockEntity> blockEntities = Maps.newHashMap();
/*     */   
/*     */   protected final LevelHeightAccessor levelHeightAccessor;
/*     */   
/*     */   protected final LevelChunkSection[] sections;
/*     */   
/*     */   public ChunkAccess(ChunkPos $$0, UpgradeData $$1, LevelHeightAccessor $$2, Registry<Biome> $$3, long $$4, @Nullable LevelChunkSection[] $$5, @Nullable BlendingData $$6) {
/*  94 */     this.chunkPos = $$0;
/*  95 */     this.upgradeData = $$1;
/*  96 */     this.levelHeightAccessor = $$2;
/*  97 */     this.sections = new LevelChunkSection[$$2.getSectionsCount()];
/*  98 */     this.inhabitedTime = $$4;
/*  99 */     this.postProcessing = new ShortList[$$2.getSectionsCount()];
/* 100 */     this.blendingData = $$6;
/* 101 */     this.skyLightSources = new ChunkSkyLightSources($$2);
/*     */     
/* 103 */     if ($$5 != null) {
/* 104 */       if (this.sections.length == $$5.length) {
/* 105 */         System.arraycopy($$5, 0, this.sections, 0, this.sections.length);
/*     */       } else {
/* 107 */         LOGGER.warn("Could not set level chunk sections, array length is {} instead of {}", Integer.valueOf($$5.length), Integer.valueOf(this.sections.length));
/*     */       } 
/*     */     }
/*     */     
/* 111 */     replaceMissingSections($$3, this.sections);
/*     */   }
/*     */   
/*     */   private static void replaceMissingSections(Registry<Biome> $$0, LevelChunkSection[] $$1) {
/* 115 */     for (int $$2 = 0; $$2 < $$1.length; $$2++) {
/* 116 */       if ($$1[$$2] == null) {
/* 117 */         $$1[$$2] = new LevelChunkSection($$0);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public GameEventListenerRegistry getListenerRegistry(int $$0) {
/* 123 */     return GameEventListenerRegistry.NOOP;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHighestFilledSectionIndex() {
/* 134 */     LevelChunkSection[] $$0 = getSections();
/* 135 */     for (int $$1 = $$0.length - 1; $$1 >= 0; $$1--) {
/* 136 */       LevelChunkSection $$2 = $$0[$$1];
/* 137 */       if (!$$2.hasOnlyAir()) {
/* 138 */         return $$1;
/*     */       }
/*     */     } 
/* 141 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated(forRemoval = true)
/*     */   public int getHighestSectionPosition() {
/* 147 */     int $$0 = getHighestFilledSectionIndex();
/* 148 */     return ($$0 == -1) ? getMinBuildHeight() : SectionPos.sectionToBlockCoord(getSectionYFromSectionIndex($$0));
/*     */   }
/*     */   
/*     */   public Set<BlockPos> getBlockEntitiesPos() {
/* 152 */     Set<BlockPos> $$0 = Sets.newHashSet(this.pendingBlockEntities.keySet());
/* 153 */     $$0.addAll(this.blockEntities.keySet());
/* 154 */     return $$0;
/*     */   }
/*     */   
/*     */   public LevelChunkSection[] getSections() {
/* 158 */     return this.sections;
/*     */   }
/*     */   
/*     */   public LevelChunkSection getSection(int $$0) {
/* 162 */     return getSections()[$$0];
/*     */   }
/*     */   
/*     */   public Collection<Map.Entry<Heightmap.Types, Heightmap>> getHeightmaps() {
/* 166 */     return Collections.unmodifiableSet(this.heightmaps.entrySet());
/*     */   }
/*     */   
/*     */   public void setHeightmap(Heightmap.Types $$0, long[] $$1) {
/* 170 */     getOrCreateHeightmapUnprimed($$0).setRawData(this, $$0, $$1);
/*     */   }
/*     */   
/*     */   public Heightmap getOrCreateHeightmapUnprimed(Heightmap.Types $$0) {
/* 174 */     return this.heightmaps.computeIfAbsent($$0, $$0 -> new Heightmap(this, $$0));
/*     */   }
/*     */   
/*     */   public boolean hasPrimedHeightmap(Heightmap.Types $$0) {
/* 178 */     return (this.heightmaps.get($$0) != null);
/*     */   }
/*     */   
/*     */   public int getHeight(Heightmap.Types $$0, int $$1, int $$2) {
/* 182 */     Heightmap $$3 = this.heightmaps.get($$0);
/* 183 */     if ($$3 == null) {
/* 184 */       if (SharedConstants.IS_RUNNING_IN_IDE && this instanceof LevelChunk) {
/* 185 */         LOGGER.error("Unprimed heightmap: " + $$0 + " " + $$1 + " " + $$2);
/*     */       }
/* 187 */       Heightmap.primeHeightmaps(this, EnumSet.of($$0));
/* 188 */       $$3 = this.heightmaps.get($$0);
/*     */     } 
/* 190 */     return $$3.getFirstAvailable($$1 & 0xF, $$2 & 0xF) - 1;
/*     */   }
/*     */   
/*     */   public ChunkPos getPos() {
/* 194 */     return this.chunkPos;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public StructureStart getStartForStructure(Structure $$0) {
/* 200 */     return this.structureStarts.get($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setStartForStructure(Structure $$0, StructureStart $$1) {
/* 205 */     this.structureStarts.put($$0, $$1);
/* 206 */     this.unsaved = true;
/*     */   }
/*     */   
/*     */   public Map<Structure, StructureStart> getAllStarts() {
/* 210 */     return Collections.unmodifiableMap(this.structureStarts);
/*     */   }
/*     */   
/*     */   public void setAllStarts(Map<Structure, StructureStart> $$0) {
/* 214 */     this.structureStarts.clear();
/* 215 */     this.structureStarts.putAll($$0);
/* 216 */     this.unsaved = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public LongSet getReferencesForStructure(Structure $$0) {
/* 221 */     return this.structuresRefences.getOrDefault($$0, EMPTY_REFERENCE_SET);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addReferenceForStructure(Structure $$0, long $$1) {
/* 226 */     ((LongSet)this.structuresRefences.computeIfAbsent($$0, $$0 -> new LongOpenHashSet())).add($$1);
/* 227 */     this.unsaved = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<Structure, LongSet> getAllReferences() {
/* 232 */     return Collections.unmodifiableMap(this.structuresRefences);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAllReferences(Map<Structure, LongSet> $$0) {
/* 237 */     this.structuresRefences.clear();
/* 238 */     this.structuresRefences.putAll($$0);
/* 239 */     this.unsaved = true;
/*     */   }
/*     */   
/*     */   public boolean isYSpaceEmpty(int $$0, int $$1) {
/* 243 */     if ($$0 < getMinBuildHeight()) {
/* 244 */       $$0 = getMinBuildHeight();
/*     */     }
/* 246 */     if ($$1 >= getMaxBuildHeight()) {
/* 247 */       $$1 = getMaxBuildHeight() - 1;
/*     */     }
/* 249 */     for (int $$2 = $$0; $$2 <= $$1; $$2 += 16) {
/* 250 */       if (!getSection(getSectionIndex($$2)).hasOnlyAir()) {
/* 251 */         return false;
/*     */       }
/*     */     } 
/* 254 */     return true;
/*     */   }
/*     */   
/*     */   public void setUnsaved(boolean $$0) {
/* 258 */     this.unsaved = $$0;
/*     */   }
/*     */   
/*     */   public boolean isUnsaved() {
/* 262 */     return this.unsaved;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChunkStatus getHighestGeneratedStatus() {
/* 269 */     ChunkStatus $$0 = getStatus();
/* 270 */     BelowZeroRetrogen $$1 = getBelowZeroRetrogen();
/* 271 */     if ($$1 != null) {
/* 272 */       ChunkStatus $$2 = $$1.targetStatus();
/* 273 */       return $$2.isOrAfter($$0) ? $$2 : $$0;
/*     */     } 
/* 275 */     return $$0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void markPosForPostprocessing(BlockPos $$0) {
/* 281 */     LOGGER.warn("Trying to mark a block for PostProcessing @ {}, but this operation is not supported.", $$0);
/*     */   }
/*     */   
/*     */   public ShortList[] getPostProcessing() {
/* 285 */     return this.postProcessing;
/*     */   }
/*     */   
/*     */   public void addPackedPostProcess(short $$0, int $$1) {
/* 289 */     getOrCreateOffsetList(getPostProcessing(), $$1).add($$0);
/*     */   }
/*     */   
/*     */   public void setBlockEntityNbt(CompoundTag $$0) {
/* 293 */     this.pendingBlockEntities.put(BlockEntity.getPosFromTag($$0), $$0);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public CompoundTag getBlockEntityNbt(BlockPos $$0) {
/* 298 */     return this.pendingBlockEntities.get($$0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void findBlockLightSources(BiConsumer<BlockPos, BlockState> $$0) {
/* 306 */     findBlocks($$0 -> ($$0.getLightEmission() != 0), $$0);
/*     */   }
/*     */   
/*     */   public void findBlocks(Predicate<BlockState> $$0, BiConsumer<BlockPos, BlockState> $$1) {
/* 310 */     BlockPos.MutableBlockPos $$2 = new BlockPos.MutableBlockPos();
/* 311 */     for (int $$3 = getMinSection(); $$3 < getMaxSection(); $$3++) {
/* 312 */       LevelChunkSection $$4 = getSection(getSectionIndexFromSectionY($$3));
/* 313 */       if ($$4.maybeHas($$0)) {
/*     */ 
/*     */         
/* 316 */         BlockPos $$5 = SectionPos.of(this.chunkPos, $$3).origin();
/* 317 */         for (int $$6 = 0; $$6 < 16; $$6++) {
/* 318 */           for (int $$7 = 0; $$7 < 16; $$7++) {
/* 319 */             for (int $$8 = 0; $$8 < 16; $$8++) {
/* 320 */               BlockState $$9 = $$4.getBlockState($$8, $$6, $$7);
/* 321 */               if ($$0.test($$9))
/* 322 */                 $$1.accept($$2.setWithOffset((Vec3i)$$5, $$8, $$6, $$7), $$9); 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   } public static final class TicksToSave extends Record {
/*     */     private final SerializableTickContainer<Block> blocks; private final SerializableTickContainer<Fluid> fluids; public final String toString() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/chunk/ChunkAccess$TicksToSave;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #334	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/chunk/ChunkAccess$TicksToSave;
/*     */     } public final int hashCode() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/chunk/ChunkAccess$TicksToSave;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #334	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/chunk/ChunkAccess$TicksToSave;
/*     */     }
/* 334 */     public TicksToSave(SerializableTickContainer<Block> $$0, SerializableTickContainer<Fluid> $$1) { this.blocks = $$0; this.fluids = $$1; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/chunk/ChunkAccess$TicksToSave;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #334	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/world/level/chunk/ChunkAccess$TicksToSave;
/* 334 */       //   0	8	1	$$0	Ljava/lang/Object; } public SerializableTickContainer<Block> blocks() { return this.blocks; } public SerializableTickContainer<Fluid> fluids() { return this.fluids; }
/*     */   
/*     */   }
/*     */   
/*     */   public UpgradeData getUpgradeData() {
/* 339 */     return this.upgradeData;
/*     */   }
/*     */   
/*     */   public boolean isOldNoiseGeneration() {
/* 343 */     return (this.blendingData != null);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public BlendingData getBlendingData() {
/* 348 */     return this.blendingData;
/*     */   }
/*     */   
/*     */   public void setBlendingData(BlendingData $$0) {
/* 352 */     this.blendingData = $$0;
/*     */   }
/*     */   
/*     */   public long getInhabitedTime() {
/* 356 */     return this.inhabitedTime;
/*     */   }
/*     */   
/*     */   public void incrementInhabitedTime(long $$0) {
/* 360 */     this.inhabitedTime += $$0;
/*     */   }
/*     */   
/*     */   public void setInhabitedTime(long $$0) {
/* 364 */     this.inhabitedTime = $$0;
/*     */   }
/*     */   
/*     */   public static ShortList getOrCreateOffsetList(ShortList[] $$0, int $$1) {
/* 368 */     if ($$0[$$1] == null) {
/* 369 */       $$0[$$1] = (ShortList)new ShortArrayList();
/*     */     }
/* 371 */     return $$0[$$1];
/*     */   }
/*     */   
/*     */   public boolean isLightCorrect() {
/* 375 */     return this.isLightCorrect;
/*     */   }
/*     */   
/*     */   public void setLightCorrect(boolean $$0) {
/* 379 */     this.isLightCorrect = $$0;
/* 380 */     setUnsaved(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMinBuildHeight() {
/* 385 */     return this.levelHeightAccessor.getMinBuildHeight();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHeight() {
/* 390 */     return this.levelHeightAccessor.getHeight();
/*     */   }
/*     */   
/*     */   public NoiseChunk getOrCreateNoiseChunk(Function<ChunkAccess, NoiseChunk> $$0) {
/* 394 */     if (this.noiseChunk == null) {
/* 395 */       this.noiseChunk = $$0.apply(this);
/*     */     }
/* 397 */     return this.noiseChunk;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public BiomeGenerationSettings carverBiome(Supplier<BiomeGenerationSettings> $$0) {
/* 402 */     if (this.carverBiomeSettings == null) {
/* 403 */       this.carverBiomeSettings = $$0.get();
/*     */     }
/* 405 */     return this.carverBiomeSettings;
/*     */   }
/*     */ 
/*     */   
/*     */   public Holder<Biome> getNoiseBiome(int $$0, int $$1, int $$2) {
/*     */     try {
/* 411 */       int $$3 = QuartPos.fromBlock(getMinBuildHeight());
/* 412 */       int $$4 = $$3 + QuartPos.fromBlock(getHeight()) - 1;
/* 413 */       int $$5 = Mth.clamp($$1, $$3, $$4);
/* 414 */       int $$6 = getSectionIndex(QuartPos.toBlock($$5));
/* 415 */       return this.sections[$$6].getNoiseBiome($$0 & 0x3, $$5 & 0x3, $$2 & 0x3);
/* 416 */     } catch (Throwable $$7) {
/* 417 */       CrashReport $$8 = CrashReport.forThrowable($$7, "Getting biome");
/* 418 */       CrashReportCategory $$9 = $$8.addCategory("Biome being got");
/* 419 */       $$9.setDetail("Location", () -> CrashReportCategory.formatLocation((LevelHeightAccessor)this, $$0, $$1, $$2));
/* 420 */       throw new ReportedException($$8);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void fillBiomesFromNoise(BiomeResolver $$0, Climate.Sampler $$1) {
/* 425 */     ChunkPos $$2 = getPos();
/* 426 */     int $$3 = QuartPos.fromBlock($$2.getMinBlockX());
/* 427 */     int $$4 = QuartPos.fromBlock($$2.getMinBlockZ());
/* 428 */     LevelHeightAccessor $$5 = getHeightAccessorForGeneration();
/* 429 */     for (int $$6 = $$5.getMinSection(); $$6 < $$5.getMaxSection(); $$6++) {
/* 430 */       LevelChunkSection $$7 = getSection(getSectionIndexFromSectionY($$6));
/* 431 */       int $$8 = QuartPos.fromSection($$6);
/* 432 */       $$7.fillBiomesFromNoise($$0, $$1, $$3, $$8, $$4);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean hasAnyStructureReferences() {
/* 437 */     return !getAllReferences().isEmpty();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public BelowZeroRetrogen getBelowZeroRetrogen() {
/* 442 */     return null;
/*     */   }
/*     */   
/*     */   public boolean isUpgrading() {
/* 446 */     return (getBelowZeroRetrogen() != null);
/*     */   }
/*     */   
/*     */   public LevelHeightAccessor getHeightAccessorForGeneration() {
/* 450 */     return (LevelHeightAccessor)this;
/*     */   }
/*     */   
/*     */   public void initializeLightSources() {
/* 454 */     this.skyLightSources.fillFrom(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChunkSkyLightSources getSkyLightSources() {
/* 459 */     return this.skyLightSources;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public abstract BlockState setBlockState(BlockPos paramBlockPos, BlockState paramBlockState, boolean paramBoolean);
/*     */   
/*     */   public abstract void setBlockEntity(BlockEntity paramBlockEntity);
/*     */   
/*     */   public abstract void addEntity(Entity paramEntity);
/*     */   
/*     */   public abstract ChunkStatus getStatus();
/*     */   
/*     */   public abstract void removeBlockEntity(BlockPos paramBlockPos);
/*     */   
/*     */   @Nullable
/*     */   public abstract CompoundTag getBlockEntityNbtForSaving(BlockPos paramBlockPos);
/*     */   
/*     */   public abstract TickContainerAccess<Block> getBlockTicks();
/*     */   
/*     */   public abstract TickContainerAccess<Fluid> getFluidTicks();
/*     */   
/*     */   public abstract TicksToSave getTicksForSerialization();
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\chunk\ChunkAccess.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */