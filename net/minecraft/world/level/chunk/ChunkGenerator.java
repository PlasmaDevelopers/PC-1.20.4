/*     */ package net.minecraft.world.level.chunk;
/*     */ import com.google.common.base.Suppliers;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import com.mojang.serialization.Codec;
/*     */ import it.unimi.dsi.fastutil.ints.IntArraySet;
/*     */ import it.unimi.dsi.fastutil.ints.IntSet;
/*     */ import it.unimi.dsi.fastutil.longs.LongSet;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArraySet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Predicate;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.stream.Collectors;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.CrashReport;
/*     */ import net.minecraft.CrashReportCategory;
/*     */ import net.minecraft.ReportedException;
/*     */ import net.minecraft.SharedConstants;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.HolderLookup;
/*     */ import net.minecraft.core.HolderSet;
/*     */ import net.minecraft.core.Registry;
/*     */ import net.minecraft.core.RegistryAccess;
/*     */ import net.minecraft.core.SectionPos;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.network.protocol.game.DebugPackets;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.server.level.WorldGenRegion;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.util.random.WeightedRandomList;
/*     */ import net.minecraft.world.entity.MobCategory;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.LevelHeightAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.NoiseColumn;
/*     */ import net.minecraft.world.level.StructureManager;
/*     */ import net.minecraft.world.level.WorldGenLevel;
/*     */ import net.minecraft.world.level.biome.Biome;
/*     */ import net.minecraft.world.level.biome.BiomeGenerationSettings;
/*     */ import net.minecraft.world.level.biome.BiomeManager;
/*     */ import net.minecraft.world.level.biome.BiomeResolver;
/*     */ import net.minecraft.world.level.biome.BiomeSource;
/*     */ import net.minecraft.world.level.biome.FeatureSorter;
/*     */ import net.minecraft.world.level.levelgen.GenerationStep;
/*     */ import net.minecraft.world.level.levelgen.Heightmap;
/*     */ import net.minecraft.world.level.levelgen.LegacyRandomSource;
/*     */ import net.minecraft.world.level.levelgen.RandomState;
/*     */ import net.minecraft.world.level.levelgen.RandomSupport;
/*     */ import net.minecraft.world.level.levelgen.WorldgenRandom;
/*     */ import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
/*     */ import net.minecraft.world.level.levelgen.blending.Blender;
/*     */ import net.minecraft.world.level.levelgen.placement.PlacedFeature;
/*     */ import net.minecraft.world.level.levelgen.structure.BoundingBox;
/*     */ import net.minecraft.world.level.levelgen.structure.Structure;
/*     */ import net.minecraft.world.level.levelgen.structure.StructureCheckResult;
/*     */ import net.minecraft.world.level.levelgen.structure.StructureSet;
/*     */ import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride;
/*     */ import net.minecraft.world.level.levelgen.structure.StructureStart;
/*     */ import net.minecraft.world.level.levelgen.structure.placement.ConcentricRingsStructurePlacement;
/*     */ import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
/*     */ import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
/*     */ import org.apache.commons.lang3.mutable.MutableBoolean;
/*     */ 
/*     */ public abstract class ChunkGenerator {
/*  81 */   public static final Codec<ChunkGenerator> CODEC = BuiltInRegistries.CHUNK_GENERATOR.byNameCodec().dispatchStable(ChunkGenerator::codec, Function.identity());
/*     */ 
/*     */   
/*     */   protected final BiomeSource biomeSource;
/*     */ 
/*     */   
/*     */   private final Supplier<List<FeatureSorter.StepFeatureData>> featuresPerStep;
/*     */ 
/*     */   
/*     */   private final Function<Holder<Biome>, BiomeGenerationSettings> generationSettingsGetter;
/*     */ 
/*     */   
/*     */   public ChunkGenerator(BiomeSource $$0) {
/*  94 */     this($$0, $$0 -> ((Biome)$$0.value()).getGenerationSettings());
/*     */   }
/*     */   
/*     */   public ChunkGenerator(BiomeSource $$0, Function<Holder<Biome>, BiomeGenerationSettings> $$1) {
/*  98 */     this.biomeSource = $$0;
/*  99 */     this.generationSettingsGetter = $$1;
/*     */ 
/*     */     
/* 102 */     this.featuresPerStep = (Supplier<List<FeatureSorter.StepFeatureData>>)Suppliers.memoize(() -> FeatureSorter.buildFeaturesPerStep(List.copyOf($$0.possibleBiomes()), (), true));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ChunkGeneratorStructureState createState(HolderLookup<StructureSet> $$0, RandomState $$1, long $$2) {
/* 108 */     return ChunkGeneratorStructureState.createForNormal($$1, $$2, this.biomeSource, $$0);
/*     */   }
/*     */   
/*     */   public Optional<ResourceKey<Codec<? extends ChunkGenerator>>> getTypeNameForDataFixer() {
/* 112 */     return BuiltInRegistries.CHUNK_GENERATOR.getResourceKey(codec());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CompletableFuture<ChunkAccess> createBiomes(Executor $$0, RandomState $$1, Blender $$2, StructureManager $$3, ChunkAccess $$4) {
/* 119 */     return CompletableFuture.supplyAsync(Util.wrapThreadWithTaskName("init_biomes", () -> {
/*     */             $$0.fillBiomesFromNoise((BiomeResolver)this.biomeSource, $$1.sampler());
/*     */             return $$0;
/* 122 */           }), Util.backgroundExecutor());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Pair<BlockPos, Holder<Structure>> findNearestMapStructure(ServerLevel $$0, HolderSet<Structure> $$1, BlockPos $$2, int $$3, boolean $$4) {
/* 133 */     ChunkGeneratorStructureState $$5 = $$0.getChunkSource().getGeneratorState();
/* 134 */     Object2ObjectArrayMap<StructurePlacement, Set<Holder<Structure>>> object2ObjectArrayMap = new Object2ObjectArrayMap();
/* 135 */     for (Holder<Structure> $$7 : $$1) {
/* 136 */       for (StructurePlacement $$8 : $$5.getPlacementsForStructure($$7)) {
/* 137 */         ((Set<Holder<Structure>>)object2ObjectArrayMap.computeIfAbsent($$8, $$0 -> new ObjectArraySet())).add($$7);
/*     */       }
/*     */     } 
/*     */     
/* 141 */     if (object2ObjectArrayMap.isEmpty()) {
/* 142 */       return null;
/*     */     }
/*     */     
/* 145 */     Pair<BlockPos, Holder<Structure>> $$9 = null;
/* 146 */     double $$10 = Double.MAX_VALUE;
/* 147 */     StructureManager $$11 = $$0.structureManager();
/* 148 */     List<Map.Entry<StructurePlacement, Set<Holder<Structure>>>> $$12 = new ArrayList<>(object2ObjectArrayMap.size());
/* 149 */     for (Map.Entry<StructurePlacement, Set<Holder<Structure>>> $$13 : object2ObjectArrayMap.entrySet()) {
/* 150 */       StructurePlacement $$14 = $$13.getKey();
/* 151 */       if ($$14 instanceof ConcentricRingsStructurePlacement) { ConcentricRingsStructurePlacement $$15 = (ConcentricRingsStructurePlacement)$$14;
/* 152 */         Pair<BlockPos, Holder<Structure>> $$16 = getNearestGeneratedStructure($$13.getValue(), $$0, $$11, $$2, $$4, $$15);
/* 153 */         if ($$16 != null) {
/* 154 */           BlockPos $$17 = (BlockPos)$$16.getFirst();
/* 155 */           double $$18 = $$2.distSqr((Vec3i)$$17);
/* 156 */           if ($$18 < $$10) {
/* 157 */             $$10 = $$18;
/* 158 */             $$9 = $$16;
/*     */           } 
/*     */         }  continue; }
/* 161 */        if ($$14 instanceof RandomSpreadStructurePlacement) {
/* 162 */         $$12.add($$13);
/*     */       }
/*     */     } 
/*     */     
/* 166 */     if (!$$12.isEmpty()) {
/* 167 */       int $$19 = SectionPos.blockToSectionCoord($$2.getX());
/* 168 */       int $$20 = SectionPos.blockToSectionCoord($$2.getZ());
/*     */ 
/*     */       
/* 171 */       for (int $$21 = 0; $$21 <= $$3; $$21++) {
/* 172 */         boolean $$22 = false;
/* 173 */         for (Map.Entry<StructurePlacement, Set<Holder<Structure>>> $$23 : $$12) {
/* 174 */           RandomSpreadStructurePlacement $$24 = (RandomSpreadStructurePlacement)$$23.getKey();
/* 175 */           Pair<BlockPos, Holder<Structure>> $$25 = getNearestGeneratedStructure($$23.getValue(), (LevelReader)$$0, $$11, $$19, $$20, $$21, $$4, $$5.getLevelSeed(), $$24);
/* 176 */           if ($$25 != null) {
/* 177 */             $$22 = true;
/* 178 */             double $$26 = $$2.distSqr((Vec3i)$$25.getFirst());
/* 179 */             if ($$26 < $$10) {
/* 180 */               $$10 = $$26;
/* 181 */               $$9 = $$25;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 186 */         if ($$22) {
/* 187 */           return $$9;
/*     */         }
/*     */       } 
/*     */     } 
/* 191 */     return $$9;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private Pair<BlockPos, Holder<Structure>> getNearestGeneratedStructure(Set<Holder<Structure>> $$0, ServerLevel $$1, StructureManager $$2, BlockPos $$3, boolean $$4, ConcentricRingsStructurePlacement $$5) {
/* 196 */     List<ChunkPos> $$6 = $$1.getChunkSource().getGeneratorState().getRingPositionsFor($$5);
/* 197 */     if ($$6 == null) {
/* 198 */       throw new IllegalStateException("Somehow tried to find structures for a placement that doesn't exist");
/*     */     }
/* 200 */     Pair<BlockPos, Holder<Structure>> $$7 = null;
/* 201 */     double $$8 = Double.MAX_VALUE;
/* 202 */     BlockPos.MutableBlockPos $$9 = new BlockPos.MutableBlockPos();
/* 203 */     for (ChunkPos $$10 : $$6) {
/* 204 */       $$9.set(SectionPos.sectionToBlockCoord($$10.x, 8), 32, SectionPos.sectionToBlockCoord($$10.z, 8));
/* 205 */       double $$11 = $$9.distSqr((Vec3i)$$3);
/* 206 */       boolean $$12 = ($$7 == null || $$11 < $$8);
/* 207 */       if ($$12) {
/* 208 */         Pair<BlockPos, Holder<Structure>> $$13 = getStructureGeneratingAt($$0, (LevelReader)$$1, $$2, $$4, (StructurePlacement)$$5, $$10);
/* 209 */         if ($$13 != null) {
/* 210 */           $$7 = $$13;
/* 211 */           $$8 = $$11;
/*     */         } 
/*     */       } 
/*     */     } 
/* 215 */     return $$7;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private static Pair<BlockPos, Holder<Structure>> getNearestGeneratedStructure(Set<Holder<Structure>> $$0, LevelReader $$1, StructureManager $$2, int $$3, int $$4, int $$5, boolean $$6, long $$7, RandomSpreadStructurePlacement $$8) {
/* 220 */     int $$9 = $$8.spacing();
/*     */     
/* 222 */     for (int $$10 = -$$5; $$10 <= $$5; $$10++) {
/* 223 */       boolean $$11 = ($$10 == -$$5 || $$10 == $$5);
/* 224 */       for (int $$12 = -$$5; $$12 <= $$5; $$12++) {
/* 225 */         boolean $$13 = ($$12 == -$$5 || $$12 == $$5);
/* 226 */         if ($$11 || $$13) {
/*     */ 
/*     */ 
/*     */           
/* 230 */           int $$14 = $$3 + $$9 * $$10;
/* 231 */           int $$15 = $$4 + $$9 * $$12;
/*     */           
/* 233 */           ChunkPos $$16 = $$8.getPotentialStructureChunk($$7, $$14, $$15);
/*     */           
/* 235 */           Pair<BlockPos, Holder<Structure>> $$17 = getStructureGeneratingAt($$0, $$1, $$2, $$6, (StructurePlacement)$$8, $$16);
/* 236 */           if ($$17 != null) {
/* 237 */             return $$17;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 242 */     return null;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private static Pair<BlockPos, Holder<Structure>> getStructureGeneratingAt(Set<Holder<Structure>> $$0, LevelReader $$1, StructureManager $$2, boolean $$3, StructurePlacement $$4, ChunkPos $$5) {
/* 247 */     for (Holder<Structure> $$6 : $$0) {
/* 248 */       StructureCheckResult $$7 = $$2.checkStructurePresence($$5, (Structure)$$6.value(), $$3);
/*     */       
/* 250 */       if ($$7 == StructureCheckResult.START_NOT_PRESENT)
/*     */         continue; 
/* 252 */       if (!$$3 && $$7 == StructureCheckResult.START_PRESENT) {
/* 253 */         return Pair.of($$4.getLocatePos($$5), $$6);
/*     */       }
/*     */       
/* 256 */       ChunkAccess $$8 = $$1.getChunk($$5.x, $$5.z, ChunkStatus.STRUCTURE_STARTS);
/* 257 */       StructureStart $$9 = $$2.getStartForStructure(SectionPos.bottomOf($$8), (Structure)$$6.value(), $$8);
/* 258 */       if ($$9 != null && $$9.isValid() && (
/* 259 */         !$$3 || tryAddReference($$2, $$9))) {
/* 260 */         return Pair.of($$4.getLocatePos($$9.getChunkPos()), $$6);
/*     */       }
/*     */     } 
/*     */     
/* 264 */     return null;
/*     */   }
/*     */   
/*     */   private static boolean tryAddReference(StructureManager $$0, StructureStart $$1) {
/* 268 */     if ($$1.canBeReferenced()) {
/* 269 */       $$0.addReference($$1);
/* 270 */       return true;
/*     */     } 
/* 272 */     return false;
/*     */   }
/*     */   
/*     */   public void applyBiomeDecoration(WorldGenLevel $$0, ChunkAccess $$1, StructureManager $$2) {
/* 276 */     ChunkPos $$3 = $$1.getPos();
/*     */     
/* 278 */     if (SharedConstants.debugVoidTerrain($$3)) {
/*     */       return;
/*     */     }
/*     */     
/* 282 */     SectionPos $$4 = SectionPos.of($$3, $$0.getMinSection());
/* 283 */     BlockPos $$5 = $$4.origin();
/*     */     
/* 285 */     Registry<Structure> $$6 = $$0.registryAccess().registryOrThrow(Registries.STRUCTURE);
/* 286 */     Map<Integer, List<Structure>> $$7 = (Map<Integer, List<Structure>>)$$6.stream().collect(Collectors.groupingBy($$0 -> Integer.valueOf($$0.step().ordinal())));
/*     */     
/* 288 */     List<FeatureSorter.StepFeatureData> $$8 = this.featuresPerStep.get();
/*     */     
/* 290 */     WorldgenRandom $$9 = new WorldgenRandom((RandomSource)new XoroshiroRandomSource(RandomSupport.generateUniqueSeed()));
/* 291 */     long $$10 = $$9.setDecorationSeed($$0.getSeed(), $$5.getX(), $$5.getZ());
/*     */     
/* 293 */     ObjectArraySet objectArraySet = new ObjectArraySet();
/* 294 */     ChunkPos.rangeClosed($$4.chunk(), 1).forEach($$2 -> {
/*     */           ChunkAccess $$3 = $$0.getChunk($$2.x, $$2.z);
/*     */           for (LevelChunkSection $$4 : $$3.getSections()) {
/*     */             Objects.requireNonNull($$1);
/*     */             $$4.getBiomes().getAll($$1::add);
/*     */           } 
/*     */         });
/* 301 */     objectArraySet.retainAll(this.biomeSource.possibleBiomes());
/* 302 */     int $$12 = $$8.size();
/*     */     
/*     */     try {
/* 305 */       Registry<PlacedFeature> $$13 = $$0.registryAccess().registryOrThrow(Registries.PLACED_FEATURE);
/*     */       
/* 307 */       int $$14 = Math.max((GenerationStep.Decoration.values()).length, $$12);
/* 308 */       for (int $$15 = 0; $$15 < $$14; $$15++) {
/* 309 */         int $$16 = 0;
/* 310 */         if ($$2.shouldGenerateStructures()) {
/* 311 */           List<Structure> $$17 = $$7.getOrDefault(Integer.valueOf($$15), Collections.emptyList());
/* 312 */           for (Structure $$18 : $$17) {
/* 313 */             $$9.setFeatureSeed($$10, $$16, $$15);
/*     */             
/* 315 */             Supplier<String> $$19 = () -> { Objects.requireNonNull($$1); return $$0.getResourceKey($$1).map(Object::toString).orElseGet($$1::toString);
/*     */               }; try {
/* 317 */               $$0.setCurrentlyGenerating($$19);
/*     */               
/* 319 */               $$2.startsForStructure($$4, $$18).forEach($$5 -> $$5.placeInChunk($$0, $$1, this, (RandomSource)$$2, getWritableArea($$3), $$4));
/*     */             
/*     */             }
/* 322 */             catch (Exception $$20) {
/* 323 */               CrashReport $$21 = CrashReport.forThrowable($$20, "Feature placement");
/*     */               
/* 325 */               Objects.requireNonNull($$19); $$21.addCategory("Feature").setDetail("Description", $$19::get);
/* 326 */               throw new ReportedException($$21);
/*     */             } 
/* 328 */             $$16++;
/*     */           } 
/*     */         } 
/* 331 */         if ($$15 < $$12) {
/* 332 */           IntArraySet intArraySet = new IntArraySet();
/* 333 */           for (Holder<Biome> $$23 : (Iterable<Holder<Biome>>)objectArraySet) {
/*     */             
/* 335 */             List<HolderSet<PlacedFeature>> $$24 = ((BiomeGenerationSettings)this.generationSettingsGetter.apply($$23)).features();
/* 336 */             if ($$15 >= $$24.size()) {
/*     */               continue;
/*     */             }
/* 339 */             HolderSet<PlacedFeature> $$25 = $$24.get($$15);
/* 340 */             FeatureSorter.StepFeatureData $$26 = $$8.get($$15);
/* 341 */             $$25.stream().map(Holder::value).forEach($$2 -> $$0.add($$1.indexMapping().applyAsInt($$2)));
/*     */           } 
/*     */           
/* 344 */           int $$27 = intArraySet.size();
/* 345 */           int[] $$28 = intArraySet.toIntArray();
/* 346 */           Arrays.sort($$28);
/*     */           
/* 348 */           FeatureSorter.StepFeatureData $$29 = $$8.get($$15);
/* 349 */           for (int $$30 = 0; $$30 < $$27; $$30++) {
/* 350 */             int $$31 = $$28[$$30];
/* 351 */             PlacedFeature $$32 = $$29.features().get($$31);
/*     */             
/* 353 */             Supplier<String> $$33 = () -> { Objects.requireNonNull($$1); return $$0.getResourceKey($$1).map(Object::toString).orElseGet($$1::toString);
/* 354 */               }; $$9.setFeatureSeed($$10, $$31, $$15);
/*     */             try {
/* 356 */               $$0.setCurrentlyGenerating($$33);
/* 357 */               $$32.placeWithBiomeCheck($$0, this, (RandomSource)$$9, $$5);
/* 358 */             } catch (Exception $$34) {
/* 359 */               CrashReport $$35 = CrashReport.forThrowable($$34, "Feature placement");
/*     */               
/* 361 */               Objects.requireNonNull($$33); $$35.addCategory("Feature").setDetail("Description", $$33::get);
/* 362 */               throw new ReportedException($$35);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/* 367 */       $$0.setCurrentlyGenerating(null);
/*     */ 
/*     */     
/*     */     }
/* 371 */     catch (Exception $$36) {
/* 372 */       CrashReport $$37 = CrashReport.forThrowable($$36, "Biome decoration");
/* 373 */       $$37.addCategory("Generation")
/* 374 */         .setDetail("CenterX", Integer.valueOf($$3.x))
/* 375 */         .setDetail("CenterZ", Integer.valueOf($$3.z))
/* 376 */         .setDetail("Seed", Long.valueOf($$10));
/* 377 */       throw new ReportedException($$37);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static BoundingBox getWritableArea(ChunkAccess $$0) {
/* 382 */     ChunkPos $$1 = $$0.getPos();
/* 383 */     int $$2 = $$1.getMinBlockX();
/* 384 */     int $$3 = $$1.getMinBlockZ();
/*     */     
/* 386 */     LevelHeightAccessor $$4 = $$0.getHeightAccessorForGeneration();
/* 387 */     int $$5 = $$4.getMinBuildHeight() + 1;
/* 388 */     int $$6 = $$4.getMaxBuildHeight() - 1;
/*     */     
/* 390 */     return new BoundingBox($$2, $$5, $$3, $$2 + 15, $$6, $$3 + 15);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSpawnHeight(LevelHeightAccessor $$0) {
/* 401 */     return 64;
/*     */   }
/*     */   
/*     */   public BiomeSource getBiomeSource() {
/* 405 */     return this.biomeSource;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public WeightedRandomList<MobSpawnSettings.SpawnerData> getMobsAt(Holder<Biome> $$0, StructureManager $$1, MobCategory $$2, BlockPos $$3) {
/* 411 */     Map<Structure, LongSet> $$4 = $$1.getAllStructuresAt($$3);
/*     */     
/* 413 */     for (Map.Entry<Structure, LongSet> $$5 : $$4.entrySet()) {
/* 414 */       Structure $$6 = $$5.getKey();
/* 415 */       StructureSpawnOverride $$7 = (StructureSpawnOverride)$$6.spawnOverrides().get($$2);
/* 416 */       if ($$7 == null) {
/*     */         continue;
/*     */       }
/*     */       
/* 420 */       MutableBoolean $$8 = new MutableBoolean(false);
/*     */ 
/*     */       
/* 423 */       Predicate<StructureStart> $$9 = ($$7.boundingBox() == StructureSpawnOverride.BoundingBoxType.PIECE) ? ($$2 -> $$0.structureHasPieceAt($$1, $$2)) : ($$1 -> $$1.getBoundingBox().isInside((Vec3i)$$0));
/*     */       
/* 425 */       $$1.fillStartsForStructure($$6, $$5.getValue(), $$2 -> {
/*     */             if ($$0.isFalse() && $$1.test($$2)) {
/*     */               $$0.setTrue();
/*     */             }
/*     */           });
/* 430 */       if ($$8.isTrue()) {
/* 431 */         return $$7.spawns();
/*     */       }
/*     */     } 
/*     */     
/* 435 */     return ((Biome)$$0.value()).getMobSettings().getMobs($$2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void createStructures(RegistryAccess $$0, ChunkGeneratorStructureState $$1, StructureManager $$2, ChunkAccess $$3, StructureTemplateManager $$4) {
/* 443 */     ChunkPos $$5 = $$3.getPos();
/* 444 */     SectionPos $$6 = SectionPos.bottomOf($$3);
/*     */     
/* 446 */     RandomState $$7 = $$1.randomState();
/*     */     
/* 448 */     $$1.possibleStructureSets().forEach($$8 -> {
/*     */           StructurePlacement $$9 = ((StructureSet)$$8.value()).placement();
/*     */           List<StructureSet.StructureSelectionEntry> $$10 = ((StructureSet)$$8.value()).structures();
/*     */           for (StructureSet.StructureSelectionEntry $$11 : $$10) {
/*     */             StructureStart $$12 = $$0.getStartForStructure($$1, (Structure)$$11.structure().value(), $$2);
/*     */             if ($$12 != null && $$12.isValid()) {
/*     */               return;
/*     */             }
/*     */           } 
/*     */           if (!$$9.isStructureChunk($$3, $$4.x, $$4.z)) {
/*     */             return;
/*     */           }
/*     */           if ($$10.size() == 1) {
/*     */             tryGenerateStructure($$10.get(0), $$0, $$5, $$6, $$7, $$3.getLevelSeed(), $$2, $$4, $$1);
/*     */             return;
/*     */           } 
/*     */           ArrayList<StructureSet.StructureSelectionEntry> $$13 = new ArrayList<>($$10.size());
/*     */           $$13.addAll($$10);
/*     */           WorldgenRandom $$14 = new WorldgenRandom((RandomSource)new LegacyRandomSource(0L));
/*     */           $$14.setLargeFeatureSeed($$3.getLevelSeed(), $$4.x, $$4.z);
/*     */           int $$15 = 0;
/*     */           for (StructureSet.StructureSelectionEntry $$16 : $$13) {
/*     */             $$15 += $$16.weight();
/*     */           }
/*     */           while (!$$13.isEmpty()) {
/*     */             int $$17 = $$14.nextInt($$15);
/*     */             int $$18 = 0;
/*     */             for (StructureSet.StructureSelectionEntry $$19 : $$13) {
/*     */               $$17 -= $$19.weight();
/*     */               if ($$17 < 0) {
/*     */                 break;
/*     */               }
/*     */               $$18++;
/*     */             } 
/*     */             StructureSet.StructureSelectionEntry $$20 = $$13.get($$18);
/*     */             if (tryGenerateStructure($$20, $$0, $$5, $$6, $$7, $$3.getLevelSeed(), $$2, $$4, $$1)) {
/*     */               return;
/*     */             }
/*     */             $$13.remove($$18);
/*     */             $$15 -= $$20.weight();
/*     */           } 
/*     */         });
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean tryGenerateStructure(StructureSet.StructureSelectionEntry $$0, StructureManager $$1, RegistryAccess $$2, RandomState $$3, StructureTemplateManager $$4, long $$5, ChunkAccess $$6, ChunkPos $$7, SectionPos $$8) {
/* 507 */     Structure $$9 = (Structure)$$0.structure().value();
/* 508 */     int $$10 = fetchReferences($$1, $$6, $$8, $$9);
/*     */ 
/*     */     
/* 511 */     HolderSet<Biome> $$11 = $$9.biomes();
/* 512 */     Objects.requireNonNull($$11); Predicate<Holder<Biome>> $$12 = $$11::contains;
/* 513 */     StructureStart $$13 = $$9.generate($$2, this, this.biomeSource, $$3, $$4, $$5, $$7, $$10, (LevelHeightAccessor)$$6, $$12);
/* 514 */     if ($$13.isValid()) {
/* 515 */       $$1.setStartForStructure($$8, $$9, $$13, $$6);
/* 516 */       return true;
/*     */     } 
/* 518 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int fetchReferences(StructureManager $$0, ChunkAccess $$1, SectionPos $$2, Structure $$3) {
/* 523 */     StructureStart $$4 = $$0.getStartForStructure($$2, $$3, $$1);
/* 524 */     return ($$4 != null) ? $$4.getReferences() : 0;
/*     */   }
/*     */   
/*     */   public void createReferences(WorldGenLevel $$0, StructureManager $$1, ChunkAccess $$2) {
/* 528 */     int $$3 = 8;
/* 529 */     ChunkPos $$4 = $$2.getPos();
/* 530 */     int $$5 = $$4.x;
/* 531 */     int $$6 = $$4.z;
/* 532 */     int $$7 = $$4.getMinBlockX();
/* 533 */     int $$8 = $$4.getMinBlockZ();
/*     */     
/* 535 */     SectionPos $$9 = SectionPos.bottomOf($$2);
/*     */     
/* 537 */     for (int $$10 = $$5 - 8; $$10 <= $$5 + 8; $$10++) {
/* 538 */       for (int $$11 = $$6 - 8; $$11 <= $$6 + 8; $$11++) {
/* 539 */         long $$12 = ChunkPos.asLong($$10, $$11);
/*     */         
/* 541 */         for (StructureStart $$13 : $$0.getChunk($$10, $$11).getAllStarts().values()) {
/*     */           try {
/* 543 */             if ($$13.isValid() && $$13.getBoundingBox().intersects($$7, $$8, $$7 + 15, $$8 + 15)) {
/* 544 */               $$1.addReferenceForStructure($$9, $$13.getStructure(), $$12, $$2);
/* 545 */               DebugPackets.sendStructurePacket($$0, $$13);
/*     */             } 
/* 547 */           } catch (Exception $$14) {
/* 548 */             CrashReport $$15 = CrashReport.forThrowable($$14, "Generating structure reference");
/* 549 */             CrashReportCategory $$16 = $$15.addCategory("Structure");
/* 550 */             Optional<? extends Registry<Structure>> $$17 = $$0.registryAccess().registry(Registries.STRUCTURE);
/* 551 */             $$16.setDetail("Id", () -> (String)$$0.map(()).orElse("UNKNOWN"));
/* 552 */             $$16.setDetail("Name", () -> BuiltInRegistries.STRUCTURE_TYPE.getKey($$0.getStructure().type()).toString());
/* 553 */             $$16.setDetail("Class", () -> $$0.getStructure().getClass().getCanonicalName());
/* 554 */             throw new ReportedException($$15);
/*     */           } 
/*     */         } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFirstFreeHeight(int $$0, int $$1, Heightmap.Types $$2, LevelHeightAccessor $$3, RandomState $$4) {
/* 575 */     return getBaseHeight($$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */   
/*     */   public int getFirstOccupiedHeight(int $$0, int $$1, Heightmap.Types $$2, LevelHeightAccessor $$3, RandomState $$4) {
/* 579 */     return getBaseHeight($$0, $$1, $$2, $$3, $$4) - 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public BiomeGenerationSettings getBiomeGenerationSettings(Holder<Biome> $$0) {
/* 590 */     return this.generationSettingsGetter.apply($$0);
/*     */   }
/*     */   
/*     */   protected abstract Codec<? extends ChunkGenerator> codec();
/*     */   
/*     */   public abstract void applyCarvers(WorldGenRegion paramWorldGenRegion, long paramLong, RandomState paramRandomState, BiomeManager paramBiomeManager, StructureManager paramStructureManager, ChunkAccess paramChunkAccess, GenerationStep.Carving paramCarving);
/*     */   
/*     */   public abstract void buildSurface(WorldGenRegion paramWorldGenRegion, StructureManager paramStructureManager, RandomState paramRandomState, ChunkAccess paramChunkAccess);
/*     */   
/*     */   public abstract void spawnOriginalMobs(WorldGenRegion paramWorldGenRegion);
/*     */   
/*     */   public abstract int getGenDepth();
/*     */   
/*     */   public abstract CompletableFuture<ChunkAccess> fillFromNoise(Executor paramExecutor, Blender paramBlender, RandomState paramRandomState, StructureManager paramStructureManager, ChunkAccess paramChunkAccess);
/*     */   
/*     */   public abstract int getSeaLevel();
/*     */   
/*     */   public abstract int getMinY();
/*     */   
/*     */   public abstract int getBaseHeight(int paramInt1, int paramInt2, Heightmap.Types paramTypes, LevelHeightAccessor paramLevelHeightAccessor, RandomState paramRandomState);
/*     */   
/*     */   public abstract NoiseColumn getBaseColumn(int paramInt1, int paramInt2, LevelHeightAccessor paramLevelHeightAccessor, RandomState paramRandomState);
/*     */   
/*     */   public abstract void addDebugScreenInfo(List<String> paramList, RandomState paramRandomState, BlockPos paramBlockPos);
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\chunk\ChunkGenerator.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */