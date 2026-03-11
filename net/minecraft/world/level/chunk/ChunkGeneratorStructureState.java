/*     */ package net.minecraft.world.level.chunk;
/*     */ 
/*     */ import com.google.common.base.Stopwatch;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.stream.Collectors;
/*     */ import java.util.stream.Stream;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.HolderLookup;
/*     */ import net.minecraft.core.HolderSet;
/*     */ import net.minecraft.core.SectionPos;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.biome.Biome;
/*     */ import net.minecraft.world.level.biome.BiomeSource;
/*     */ import net.minecraft.world.level.levelgen.RandomState;
/*     */ import net.minecraft.world.level.levelgen.structure.Structure;
/*     */ import net.minecraft.world.level.levelgen.structure.StructureSet;
/*     */ import net.minecraft.world.level.levelgen.structure.placement.ConcentricRingsStructurePlacement;
/*     */ import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class ChunkGeneratorStructureState {
/*  36 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private final RandomState randomState;
/*     */   
/*     */   private final BiomeSource biomeSource;
/*     */   
/*     */   private final long levelSeed;
/*     */   
/*     */   private final long concentricRingsSeed;
/*     */   
/*  46 */   private final Map<Structure, List<StructurePlacement>> placementsForStructure = (Map<Structure, List<StructurePlacement>>)new Object2ObjectOpenHashMap();
/*     */   
/*  48 */   private final Map<ConcentricRingsStructurePlacement, CompletableFuture<List<ChunkPos>>> ringPositions = (Map<ConcentricRingsStructurePlacement, CompletableFuture<List<ChunkPos>>>)new Object2ObjectArrayMap();
/*     */ 
/*     */   
/*     */   private boolean hasGeneratedPositions;
/*     */   
/*     */   private final List<Holder<StructureSet>> possibleStructureSets;
/*     */ 
/*     */   
/*     */   public static ChunkGeneratorStructureState createForFlat(RandomState $$0, long $$1, BiomeSource $$2, Stream<Holder<StructureSet>> $$3) {
/*  57 */     List<Holder<StructureSet>> $$4 = $$3.filter($$1 -> hasBiomesForStructureSet((StructureSet)$$1.value(), $$0)).toList();
/*     */     
/*  59 */     return new ChunkGeneratorStructureState($$0, $$2, $$1, 0L, $$4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ChunkGeneratorStructureState createForNormal(RandomState $$0, long $$1, BiomeSource $$2, HolderLookup<StructureSet> $$3) {
/*  67 */     List<Holder<StructureSet>> $$4 = (List<Holder<StructureSet>>)$$3.listElements().filter($$1 -> hasBiomesForStructureSet((StructureSet)$$1.value(), $$0)).collect(Collectors.toUnmodifiableList());
/*     */     
/*  69 */     return new ChunkGeneratorStructureState($$0, $$2, $$1, $$1, $$4);
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean hasBiomesForStructureSet(StructureSet $$0, BiomeSource $$1) {
/*  74 */     Stream<Holder<Biome>> $$2 = $$0.structures().stream().flatMap($$0 -> {
/*     */           Structure $$1 = (Structure)$$0.structure().value();
/*     */           
/*     */           return $$1.biomes().stream();
/*     */         });
/*  79 */     Objects.requireNonNull($$1.possibleBiomes()); return $$2.anyMatch($$1.possibleBiomes()::contains);
/*     */   }
/*     */   
/*     */   private ChunkGeneratorStructureState(RandomState $$0, BiomeSource $$1, long $$2, long $$3, List<Holder<StructureSet>> $$4) {
/*  83 */     this.randomState = $$0;
/*  84 */     this.levelSeed = $$2;
/*  85 */     this.biomeSource = $$1;
/*  86 */     this.concentricRingsSeed = $$3;
/*  87 */     this.possibleStructureSets = $$4;
/*     */   }
/*     */   
/*     */   public List<Holder<StructureSet>> possibleStructureSets() {
/*  91 */     return this.possibleStructureSets;
/*     */   }
/*     */   
/*     */   private void generatePositions() {
/*  95 */     Set<Holder<Biome>> $$0 = this.biomeSource.possibleBiomes();
/*  96 */     possibleStructureSets().forEach($$1 -> {
/*     */           StructureSet $$2 = (StructureSet)$$1.value();
/*     */           boolean $$3 = false;
/*     */           for (StructureSet.StructureSelectionEntry $$4 : $$2.structures()) {
/*     */             Structure $$5 = (Structure)$$4.structure().value();
/*     */             Objects.requireNonNull($$0);
/*     */             if ($$5.biomes().stream().anyMatch($$0::contains)) {
/*     */               ((List<StructurePlacement>)this.placementsForStructure.computeIfAbsent($$5, ())).add($$2.placement());
/*     */               $$3 = true;
/*     */             } 
/*     */           } 
/*     */           if ($$3) {
/*     */             StructurePlacement $$6 = $$2.placement();
/*     */             if ($$6 instanceof ConcentricRingsStructurePlacement) {
/*     */               ConcentricRingsStructurePlacement $$7 = (ConcentricRingsStructurePlacement)$$6;
/*     */               this.ringPositions.put($$7, generateRingPositions($$1, $$7));
/*     */             } 
/*     */           } 
/*     */         }); } private CompletableFuture<List<ChunkPos>> generateRingPositions(Holder<StructureSet> $$0, ConcentricRingsStructurePlacement $$1) {
/* 115 */     if ($$1.count() == 0) {
/* 116 */       return CompletableFuture.completedFuture(List.of());
/*     */     }
/*     */     
/* 119 */     Stopwatch $$2 = Stopwatch.createStarted(Util.TICKER);
/*     */     
/* 121 */     int $$3 = $$1.distance();
/* 122 */     int $$4 = $$1.count();
/*     */     
/* 124 */     List<CompletableFuture<ChunkPos>> $$5 = new ArrayList<>($$4);
/*     */     
/* 126 */     int $$6 = $$1.spread();
/* 127 */     HolderSet<Biome> $$7 = $$1.preferredBiomes();
/*     */     
/* 129 */     RandomSource $$8 = RandomSource.create();
/*     */ 
/*     */     
/* 132 */     $$8.setSeed(this.concentricRingsSeed);
/*     */     
/* 134 */     double $$9 = $$8.nextDouble() * Math.PI * 2.0D;
/*     */     
/* 136 */     int $$10 = 0;
/* 137 */     int $$11 = 0;
/* 138 */     for (int $$12 = 0; $$12 < $$4; $$12++) {
/* 139 */       double $$13 = (4 * $$3 + $$3 * $$11 * 6) + ($$8.nextDouble() - 0.5D) * $$3 * 2.5D;
/* 140 */       int $$14 = (int)Math.round(Math.cos($$9) * $$13);
/* 141 */       int $$15 = (int)Math.round(Math.sin($$9) * $$13);
/*     */       
/* 143 */       RandomSource $$16 = $$8.fork();
/* 144 */       $$5.add(CompletableFuture.supplyAsync(() -> {
/*     */               Objects.requireNonNull($$2); Pair<BlockPos, Holder<Biome>> $$4 = this.biomeSource.findBiomeHorizontal(SectionPos.sectionToBlockCoord($$0, 8), 0, SectionPos.sectionToBlockCoord($$1, 8), 112, $$2::contains, $$3, this.randomState.sampler());
/*     */               if ($$4 != null) {
/*     */                 BlockPos $$5 = (BlockPos)$$4.getFirst();
/*     */                 return new ChunkPos(SectionPos.blockToSectionCoord($$5.getX()), SectionPos.blockToSectionCoord($$5.getZ()));
/*     */               } 
/*     */               return new ChunkPos($$0, $$1);
/* 151 */             }Util.backgroundExecutor()));
/*     */       
/* 153 */       $$9 += 6.283185307179586D / $$6;
/*     */       
/* 155 */       if (++$$10 == $$6) {
/* 156 */         $$11++;
/* 157 */         $$10 = 0;
/* 158 */         $$6 += 2 * $$6 / ($$11 + 1);
/* 159 */         $$6 = Math.min($$6, $$4 - $$12);
/* 160 */         $$9 += $$8.nextDouble() * Math.PI * 2.0D;
/*     */       } 
/*     */     } 
/*     */     
/* 164 */     return Util.sequence($$5).thenApply($$2 -> {
/*     */           double $$3 = $$0.stop().elapsed(TimeUnit.MILLISECONDS) / 1000.0D;
/*     */           LOGGER.debug("Calculation for {} took {}s", $$1, Double.valueOf($$3));
/*     */           return $$2;
/*     */         });
/*     */   }
/*     */   
/*     */   public void ensureStructuresGenerated() {
/* 172 */     if (!this.hasGeneratedPositions) {
/* 173 */       generatePositions();
/* 174 */       this.hasGeneratedPositions = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public List<ChunkPos> getRingPositionsFor(ConcentricRingsStructurePlacement $$0) {
/* 180 */     ensureStructuresGenerated();
/* 181 */     CompletableFuture<List<ChunkPos>> $$1 = this.ringPositions.get($$0);
/* 182 */     return ($$1 != null) ? $$1.join() : null;
/*     */   }
/*     */   
/*     */   public List<StructurePlacement> getPlacementsForStructure(Holder<Structure> $$0) {
/* 186 */     ensureStructuresGenerated();
/* 187 */     return this.placementsForStructure.getOrDefault($$0.value(), List.of());
/*     */   }
/*     */   
/*     */   public RandomState randomState() {
/* 191 */     return this.randomState;
/*     */   }
/*     */   
/*     */   public boolean hasStructureChunkInRange(Holder<StructureSet> $$0, int $$1, int $$2, int $$3) {
/* 195 */     StructurePlacement $$4 = ((StructureSet)$$0.value()).placement();
/* 196 */     for (int $$5 = $$1 - $$3; $$5 <= $$1 + $$3; $$5++) {
/* 197 */       for (int $$6 = $$2 - $$3; $$6 <= $$2 + $$3; $$6++) {
/* 198 */         if ($$4.isStructureChunk(this, $$5, $$6)) {
/* 199 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 204 */     return false;
/*     */   }
/*     */   
/*     */   public long getLevelSeed() {
/* 208 */     return this.levelSeed;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\chunk\ChunkGeneratorStructureState.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */