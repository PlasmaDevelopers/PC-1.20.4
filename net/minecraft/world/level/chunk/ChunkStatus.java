/*     */ package net.minecraft.world.level.chunk;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.datafixers.util.Either;
/*     */ import it.unimi.dsi.fastutil.ints.IntArrayList;
/*     */ import it.unimi.dsi.fastutil.ints.IntList;
/*     */ import java.util.Collections;
/*     */ import java.util.EnumSet;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.Registry;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.level.ChunkHolder;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.server.level.ThreadedLevelLightEngine;
/*     */ import net.minecraft.server.level.WorldGenRegion;
/*     */ import net.minecraft.util.profiling.jfr.JvmProfiler;
/*     */ import net.minecraft.util.profiling.jfr.callback.ProfiledDuration;
/*     */ import net.minecraft.world.level.WorldGenLevel;
/*     */ import net.minecraft.world.level.levelgen.BelowZeroRetrogen;
/*     */ import net.minecraft.world.level.levelgen.GenerationStep;
/*     */ import net.minecraft.world.level.levelgen.Heightmap;
/*     */ import net.minecraft.world.level.levelgen.blending.Blender;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
/*     */ import net.minecraft.world.level.lighting.LevelLightEngine;
/*     */ 
/*     */ public class ChunkStatus
/*     */ {
/*     */   public static final int MAX_STRUCTURE_DISTANCE = 8;
/*  36 */   private static final EnumSet<Heightmap.Types> PRE_FEATURES = EnumSet.of(Heightmap.Types.OCEAN_FLOOR_WG, Heightmap.Types.WORLD_SURFACE_WG);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  41 */   public static final EnumSet<Heightmap.Types> POST_FEATURES = EnumSet.of(Heightmap.Types.OCEAN_FLOOR, Heightmap.Types.WORLD_SURFACE, Heightmap.Types.MOTION_BLOCKING, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES);
/*     */ 
/*     */   
/*     */   private static final LoadingTask PASSTHROUGH_LOAD_TASK;
/*     */ 
/*     */   
/*     */   static {
/*  48 */     PASSTHROUGH_LOAD_TASK = (($$0, $$1, $$2, $$3, $$4, $$5) -> CompletableFuture.completedFuture(Either.left($$5)));
/*     */   }
/*  50 */   public static final ChunkStatus STRUCTURE_STARTS; public static final ChunkStatus STRUCTURE_REFERENCES; public static final ChunkStatus BIOMES; public static final ChunkStatus NOISE; public static final ChunkStatus SURFACE; public static final ChunkStatus CARVERS; public static final ChunkStatus EMPTY = registerSimple("empty", null, -1, PRE_FEATURES, ChunkType.PROTOCHUNK, ($$0, $$1, $$2, $$3, $$4) -> {
/*     */       
/*  52 */       }); public static final ChunkStatus FEATURES; public static final ChunkStatus INITIALIZE_LIGHT; public static final ChunkStatus LIGHT; public static final ChunkStatus SPAWN; public static final ChunkStatus FULL; static { STRUCTURE_STARTS = register("structure_starts", EMPTY, 0, false, PRE_FEATURES, ChunkType.PROTOCHUNK, ($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$8) -> {
/*     */           if ($$2.getServer().getWorldData().worldGenOptions().generateStructures()) {
/*     */             $$3.createStructures($$2.registryAccess(), $$2.getChunkSource().getGeneratorState(), $$2.structureManager(), $$8, $$4);
/*     */           }
/*     */           
/*     */           $$2.onStructureStartsAvailable($$8);
/*     */           return CompletableFuture.completedFuture(Either.left($$8));
/*     */         }($$0, $$1, $$2, $$3, $$4, $$5) -> {
/*     */           $$1.onStructureStartsAvailable($$5);
/*     */           return CompletableFuture.completedFuture(Either.left($$5));
/*     */         });
/*  63 */     STRUCTURE_REFERENCES = registerSimple("structure_references", STRUCTURE_STARTS, 8, PRE_FEATURES, ChunkType.PROTOCHUNK, ($$0, $$1, $$2, $$3, $$4) -> {
/*     */           WorldGenRegion $$5 = new WorldGenRegion($$1, $$3, $$0, -1);
/*     */           
/*     */           $$2.createReferences((WorldGenLevel)$$5, $$1.structureManager().forWorldGenRegion($$5), $$4);
/*     */         });
/*  68 */     BIOMES = register("biomes", STRUCTURE_REFERENCES, 8, PRE_FEATURES, ChunkType.PROTOCHUNK, ($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$8) -> {
/*     */           WorldGenRegion $$9 = new WorldGenRegion($$2, $$7, $$0, -1);
/*     */           
/*     */           return $$3.createBiomes($$1, $$2.getChunkSource().randomState(), Blender.of($$9), $$2.structureManager().forWorldGenRegion($$9), $$8).thenApply(());
/*     */         });
/*  73 */     NOISE = register("noise", BIOMES, 8, PRE_FEATURES, ChunkType.PROTOCHUNK, ($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$8) -> {
/*     */           WorldGenRegion $$9 = new WorldGenRegion($$2, $$7, $$0, 0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           return $$3.fillFromNoise($$1, Blender.of($$9), $$2.getChunkSource().randomState(), $$2.structureManager().forWorldGenRegion($$9), $$8).thenApply(());
/*     */         });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  91 */     SURFACE = registerSimple("surface", NOISE, 8, PRE_FEATURES, ChunkType.PROTOCHUNK, ($$0, $$1, $$2, $$3, $$4) -> {
/*     */           WorldGenRegion $$5 = new WorldGenRegion($$1, $$3, $$0, 0);
/*     */           
/*     */           $$2.buildSurface($$5, $$1.structureManager().forWorldGenRegion($$5), $$1.getChunkSource().randomState(), $$4);
/*     */         });
/*  96 */     CARVERS = registerSimple("carvers", SURFACE, 8, POST_FEATURES, ChunkType.PROTOCHUNK, ($$0, $$1, $$2, $$3, $$4) -> {
/*     */           WorldGenRegion $$5 = new WorldGenRegion($$1, $$3, $$0, 0);
/*     */           if ($$4 instanceof ProtoChunk) {
/*     */             ProtoChunk $$6 = (ProtoChunk)$$4;
/*     */             Blender.addAroundOldChunksCarvingMaskFilter((WorldGenLevel)$$5, $$6);
/*     */           } 
/*     */           $$2.applyCarvers($$5, $$1.getSeed(), $$1.getChunkSource().randomState(), $$1.getBiomeManager(), $$1.structureManager().forWorldGenRegion($$5), $$4, GenerationStep.Carving.AIR);
/*     */         });
/* 104 */     FEATURES = registerSimple("features", CARVERS, 8, POST_FEATURES, ChunkType.PROTOCHUNK, ($$0, $$1, $$2, $$3, $$4) -> {
/*     */           Heightmap.primeHeightmaps($$4, EnumSet.of(Heightmap.Types.MOTION_BLOCKING, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Heightmap.Types.OCEAN_FLOOR, Heightmap.Types.WORLD_SURFACE));
/*     */           
/*     */           WorldGenRegion $$5 = new WorldGenRegion($$1, $$3, $$0, 1);
/*     */           
/*     */           $$2.applyBiomeDecoration((WorldGenLevel)$$5, $$4, $$1.structureManager().forWorldGenRegion($$5));
/*     */           
/*     */           Blender.generateBorderTicks($$5, $$4);
/*     */         });
/*     */     
/* 114 */     INITIALIZE_LIGHT = register("initialize_light", FEATURES, 0, false, POST_FEATURES, ChunkType.PROTOCHUNK, ($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$8) -> initializeLight($$5, $$8), ($$0, $$1, $$2, $$3, $$4, $$5) -> initializeLight($$3, $$5));
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
/*     */ 
/*     */     
/* 127 */     LIGHT = register("light", INITIALIZE_LIGHT, 1, true, POST_FEATURES, ChunkType.PROTOCHUNK, ($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$8) -> lightChunk($$5, $$8), ($$0, $$1, $$2, $$3, $$4, $$5) -> lightChunk($$3, $$5));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 137 */     SPAWN = registerSimple("spawn", LIGHT, 0, POST_FEATURES, ChunkType.PROTOCHUNK, ($$0, $$1, $$2, $$3, $$4) -> {
/*     */           if (!$$4.isUpgrading()) {
/*     */             $$2.spawnOriginalMobs(new WorldGenRegion($$1, $$3, $$0, -1));
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 144 */     FULL = register("full", SPAWN, 0, false, POST_FEATURES, ChunkType.LEVELCHUNK, ($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$8) -> (CompletableFuture)$$6.apply($$8), ($$0, $$1, $$2, $$3, $$4, $$5) -> (CompletableFuture)$$4.apply($$5)); } private static CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> initializeLight(ThreadedLevelLightEngine $$0, ChunkAccess $$1) { $$1.initializeLightSources();
/*     */     ((ProtoChunk)$$1).setLightEngine((LevelLightEngine)$$0);
/*     */     boolean $$2 = isLighted($$1);
/*     */     return $$0.initializeLight($$1, $$2).thenApply(Either::left); }
/*     */   private static CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> lightChunk(ThreadedLevelLightEngine $$0, ChunkAccess $$1) { boolean $$2 = isLighted($$1);
/*     */     return $$0.lightChunk($$1, $$2).thenApply(Either::left); }
/* 150 */   private static ChunkStatus registerSimple(String $$0, @Nullable ChunkStatus $$1, int $$2, EnumSet<Heightmap.Types> $$3, ChunkType $$4, SimpleGenerationTask $$5) { return register($$0, $$1, $$2, $$3, $$4, $$5); }
/*     */ 
/*     */   
/*     */   private static ChunkStatus register(String $$0, @Nullable ChunkStatus $$1, int $$2, EnumSet<Heightmap.Types> $$3, ChunkType $$4, GenerationTask $$5) {
/* 154 */     return register($$0, $$1, $$2, false, $$3, $$4, $$5, PASSTHROUGH_LOAD_TASK);
/*     */   }
/*     */   
/*     */   private static ChunkStatus register(String $$0, @Nullable ChunkStatus $$1, int $$2, boolean $$3, EnumSet<Heightmap.Types> $$4, ChunkType $$5, GenerationTask $$6, LoadingTask $$7) {
/* 158 */     return (ChunkStatus)Registry.register((Registry)BuiltInRegistries.CHUNK_STATUS, $$0, new ChunkStatus($$1, $$2, $$3, $$4, $$5, $$6, $$7));
/*     */   }
/*     */   
/*     */   public static List<ChunkStatus> getStatusList() {
/* 162 */     List<ChunkStatus> $$0 = Lists.newArrayList();
/* 163 */     ChunkStatus $$1 = FULL;
/* 164 */     while ($$1.getParent() != $$1) {
/* 165 */       $$0.add($$1);
/* 166 */       $$1 = $$1.getParent();
/*     */     } 
/* 168 */     $$0.add($$1);
/* 169 */     Collections.reverse($$0);
/* 170 */     return $$0;
/*     */   }
/*     */   
/*     */   private static boolean isLighted(ChunkAccess $$0) {
/* 174 */     return ($$0.getStatus().isOrAfter(LIGHT) && $$0.isLightCorrect());
/*     */   }
/*     */   
/* 177 */   private static final List<ChunkStatus> STATUS_BY_RANGE = (List<ChunkStatus>)ImmutableList.of(FULL, INITIALIZE_LIGHT, CARVERS, BIOMES, STRUCTURE_STARTS, STRUCTURE_STARTS, STRUCTURE_STARTS, STRUCTURE_STARTS, STRUCTURE_STARTS, STRUCTURE_STARTS, STRUCTURE_STARTS, STRUCTURE_STARTS, (Object[])new ChunkStatus[0]);
/*     */   
/*     */   private static final IntList RANGE_BY_STATUS;
/*     */   
/*     */   private final int index;
/*     */   
/*     */   private final ChunkStatus parent;
/*     */   private final GenerationTask generationTask;
/*     */   private final LoadingTask loadingTask;
/*     */   private final int range;
/*     */   private final boolean hasLoadDependencies;
/*     */   private final ChunkType chunkType;
/*     */   private final EnumSet<Heightmap.Types> heightmapsAfter;
/*     */   
/*     */   static {
/* 192 */     RANGE_BY_STATUS = (IntList)Util.make(new IntArrayList(getStatusList().size()), $$0 -> {
/*     */           int $$1 = 0;
/*     */           for (int $$2 = getStatusList().size() - 1; $$2 >= 0; $$2--) {
/*     */             while ($$1 + 1 < STATUS_BY_RANGE.size() && $$2 <= ((ChunkStatus)STATUS_BY_RANGE.get($$1 + 1)).getIndex())
/*     */               $$1++; 
/*     */             $$0.add(0, $$1);
/*     */           } 
/*     */         });
/*     */   }
/*     */   
/*     */   public static ChunkStatus getStatusAroundFullChunk(int $$0) {
/* 203 */     if ($$0 >= STATUS_BY_RANGE.size()) {
/* 204 */       return EMPTY;
/*     */     }
/* 206 */     if ($$0 < 0) {
/* 207 */       return FULL;
/*     */     }
/* 209 */     return STATUS_BY_RANGE.get($$0);
/*     */   }
/*     */   
/*     */   public static int maxDistance() {
/* 213 */     return STATUS_BY_RANGE.size();
/*     */   }
/*     */   
/*     */   public static int getDistance(ChunkStatus $$0) {
/* 217 */     return RANGE_BY_STATUS.getInt($$0.getIndex());
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
/*     */   ChunkStatus(@Nullable ChunkStatus $$0, int $$1, boolean $$2, EnumSet<Heightmap.Types> $$3, ChunkType $$4, GenerationTask $$5, LoadingTask $$6) {
/* 230 */     this.parent = ($$0 == null) ? this : $$0;
/* 231 */     this.generationTask = $$5;
/* 232 */     this.loadingTask = $$6;
/* 233 */     this.range = $$1;
/* 234 */     this.hasLoadDependencies = $$2;
/* 235 */     this.chunkType = $$4;
/* 236 */     this.heightmapsAfter = $$3;
/* 237 */     this.index = ($$0 == null) ? 0 : ($$0.getIndex() + 1);
/*     */   }
/*     */   
/*     */   public int getIndex() {
/* 241 */     return this.index;
/*     */   }
/*     */   
/*     */   public ChunkStatus getParent() {
/* 245 */     return this.parent;
/*     */   }
/*     */   
/*     */   public CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> generate(Executor $$0, ServerLevel $$1, ChunkGenerator $$2, StructureTemplateManager $$3, ThreadedLevelLightEngine $$4, Function<ChunkAccess, CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>>> $$5, List<ChunkAccess> $$6) {
/* 249 */     ChunkAccess $$7 = $$6.get($$6.size() / 2);
/* 250 */     ProfiledDuration $$8 = JvmProfiler.INSTANCE.onChunkGenerate($$7.getPos(), $$1.dimension(), toString());
/*     */     
/* 252 */     return this.generationTask.doWork(this, $$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7).thenApply($$1 -> {
/*     */           $$1.ifLeft(());
/*     */           if ($$0 != null) {
/*     */             $$0.finish();
/*     */           }
/*     */           return $$1;
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> load(ServerLevel $$0, StructureTemplateManager $$1, ThreadedLevelLightEngine $$2, Function<ChunkAccess, CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>>> $$3, ChunkAccess $$4) {
/* 266 */     return this.loadingTask.doWork(this, $$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */   
/*     */   public int getRange() {
/* 270 */     return this.range;
/*     */   }
/*     */   
/*     */   public boolean hasLoadDependencies() {
/* 274 */     return this.hasLoadDependencies;
/*     */   }
/*     */   
/*     */   public ChunkType getChunkType() {
/* 278 */     return this.chunkType;
/*     */   }
/*     */   
/*     */   public static ChunkStatus byName(String $$0) {
/* 282 */     return (ChunkStatus)BuiltInRegistries.CHUNK_STATUS.get(ResourceLocation.tryParse($$0));
/*     */   }
/*     */   
/*     */   public EnumSet<Heightmap.Types> heightmapsAfter() {
/* 286 */     return this.heightmapsAfter;
/*     */   }
/*     */   
/*     */   public boolean isOrAfter(ChunkStatus $$0) {
/* 290 */     return (getIndex() >= $$0.getIndex());
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 295 */     return BuiltInRegistries.CHUNK_STATUS.getKey(this).toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static interface SimpleGenerationTask
/*     */     extends GenerationTask
/*     */   {
/*     */     default CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> doWork(ChunkStatus $$0, Executor $$1, ServerLevel $$2, ChunkGenerator $$3, StructureTemplateManager $$4, ThreadedLevelLightEngine $$5, Function<ChunkAccess, CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>>> $$6, List<ChunkAccess> $$7, ChunkAccess $$8) {
/* 309 */       doWork($$0, $$2, $$3, $$7, $$8);
/* 310 */       return CompletableFuture.completedFuture(Either.left($$8));
/*     */     }
/*     */     void doWork(ChunkStatus param1ChunkStatus, ServerLevel param1ServerLevel, ChunkGenerator param1ChunkGenerator, List<ChunkAccess> param1List, ChunkAccess param1ChunkAccess);
/*     */   }
/*     */   private static interface LoadingTask { CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> doWork(ChunkStatus param1ChunkStatus, ServerLevel param1ServerLevel, StructureTemplateManager param1StructureTemplateManager, ThreadedLevelLightEngine param1ThreadedLevelLightEngine, Function<ChunkAccess, CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>>> param1Function, ChunkAccess param1ChunkAccess); }
/*     */   private static interface GenerationTask { CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> doWork(ChunkStatus param1ChunkStatus, Executor param1Executor, ServerLevel param1ServerLevel, ChunkGenerator param1ChunkGenerator, StructureTemplateManager param1StructureTemplateManager, ThreadedLevelLightEngine param1ThreadedLevelLightEngine, Function<ChunkAccess, CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>>> param1Function, List<ChunkAccess> param1List, ChunkAccess param1ChunkAccess); }
/*     */   
/* 317 */   public enum ChunkType { PROTOCHUNK,
/* 318 */     LEVELCHUNK; }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\chunk\ChunkStatus.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */