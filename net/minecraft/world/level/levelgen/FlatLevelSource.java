/*     */ package net.minecraft.world.level.levelgen;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.function.Function;
/*     */ import java.util.stream.Stream;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.HolderLookup;
/*     */ import net.minecraft.core.HolderSet;
/*     */ import net.minecraft.server.level.WorldGenRegion;
/*     */ import net.minecraft.world.level.LevelHeightAccessor;
/*     */ import net.minecraft.world.level.NoiseColumn;
/*     */ import net.minecraft.world.level.StructureManager;
/*     */ import net.minecraft.world.level.biome.BiomeManager;
/*     */ import net.minecraft.world.level.biome.BiomeSource;
/*     */ import net.minecraft.world.level.biome.FixedBiomeSource;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.chunk.ChunkAccess;
/*     */ import net.minecraft.world.level.chunk.ChunkGenerator;
/*     */ import net.minecraft.world.level.chunk.ChunkGeneratorStructureState;
/*     */ import net.minecraft.world.level.levelgen.blending.Blender;
/*     */ import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorSettings;
/*     */ import net.minecraft.world.level.levelgen.structure.StructureSet;
/*     */ 
/*     */ public class FlatLevelSource extends ChunkGenerator {
/*     */   static {
/*  33 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)FlatLevelGeneratorSettings.CODEC.fieldOf("settings").forGetter(FlatLevelSource::settings)).apply((Applicative)$$0, $$0.stable(FlatLevelSource::new)));
/*     */   }
/*     */   
/*     */   public static final Codec<FlatLevelSource> CODEC;
/*     */   private final FlatLevelGeneratorSettings settings;
/*     */   
/*     */   public FlatLevelSource(FlatLevelGeneratorSettings $$0) {
/*  40 */     super((BiomeSource)new FixedBiomeSource($$0.getBiome()), Util.memoize($$0::adjustGenerationSettings));
/*  41 */     this.settings = $$0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChunkGeneratorStructureState createState(HolderLookup<StructureSet> $$0, RandomState $$1, long $$2) {
/*  52 */     Stream<Holder<StructureSet>> $$3 = this.settings.structureOverrides().map(HolderSet::stream).orElseGet(() -> $$0.listElements().map(()));
/*  53 */     return ChunkGeneratorStructureState.createForFlat($$1, $$2, this.biomeSource, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Codec<? extends ChunkGenerator> codec() {
/*  58 */     return (Codec)CODEC;
/*     */   }
/*     */   
/*     */   public FlatLevelGeneratorSettings settings() {
/*  62 */     return this.settings;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void buildSurface(WorldGenRegion $$0, StructureManager $$1, RandomState $$2, ChunkAccess $$3) {}
/*     */ 
/*     */   
/*     */   public int getSpawnHeight(LevelHeightAccessor $$0) {
/*  71 */     return $$0.getMinBuildHeight() + Math.min($$0.getHeight(), this.settings.getLayers().size());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CompletableFuture<ChunkAccess> fillFromNoise(Executor $$0, Blender $$1, RandomState $$2, StructureManager $$3, ChunkAccess $$4) {
/*  78 */     List<BlockState> $$5 = this.settings.getLayers();
/*     */     
/*  80 */     BlockPos.MutableBlockPos $$6 = new BlockPos.MutableBlockPos();
/*  81 */     Heightmap $$7 = $$4.getOrCreateHeightmapUnprimed(Heightmap.Types.OCEAN_FLOOR_WG);
/*  82 */     Heightmap $$8 = $$4.getOrCreateHeightmapUnprimed(Heightmap.Types.WORLD_SURFACE_WG);
/*     */     
/*  84 */     for (int $$9 = 0; $$9 < Math.min($$4.getHeight(), $$5.size()); $$9++) {
/*  85 */       BlockState $$10 = $$5.get($$9);
/*  86 */       if ($$10 != null) {
/*     */ 
/*     */         
/*  89 */         int $$11 = $$4.getMinBuildHeight() + $$9;
/*     */         
/*  91 */         for (int $$12 = 0; $$12 < 16; $$12++) {
/*  92 */           for (int $$13 = 0; $$13 < 16; $$13++) {
/*  93 */             $$4.setBlockState((BlockPos)$$6.set($$12, $$11, $$13), $$10, false);
/*  94 */             $$7.update($$12, $$11, $$13, $$10);
/*  95 */             $$8.update($$12, $$11, $$13, $$10);
/*     */           } 
/*     */         } 
/*     */       } 
/*  99 */     }  return CompletableFuture.completedFuture($$4);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBaseHeight(int $$0, int $$1, Heightmap.Types $$2, LevelHeightAccessor $$3, RandomState $$4) {
/* 104 */     List<BlockState> $$5 = this.settings.getLayers();
/* 105 */     for (int $$6 = Math.min($$5.size(), $$3.getMaxBuildHeight()) - 1; $$6 >= 0; $$6--) {
/* 106 */       BlockState $$7 = $$5.get($$6);
/* 107 */       if ($$7 != null)
/*     */       {
/*     */         
/* 110 */         if ($$2.isOpaque().test($$7))
/* 111 */           return $$3.getMinBuildHeight() + $$6 + 1; 
/*     */       }
/*     */     } 
/* 114 */     return $$3.getMinBuildHeight();
/*     */   }
/*     */ 
/*     */   
/*     */   public NoiseColumn getBaseColumn(int $$0, int $$1, LevelHeightAccessor $$2, RandomState $$3) {
/* 119 */     return new NoiseColumn($$2.getMinBuildHeight(), (BlockState[])this.settings.getLayers().stream().limit($$2.getHeight()).map($$0 -> ($$0 == null) ? Blocks.AIR.defaultBlockState() : $$0).toArray($$0 -> new BlockState[$$0]));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addDebugScreenInfo(List<String> $$0, RandomState $$1, BlockPos $$2) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyCarvers(WorldGenRegion $$0, long $$1, RandomState $$2, BiomeManager $$3, StructureManager $$4, ChunkAccess $$5, GenerationStep.Carving $$6) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void spawnOriginalMobs(WorldGenRegion $$0) {}
/*     */ 
/*     */   
/*     */   public int getMinY() {
/* 136 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getGenDepth() {
/* 141 */     return 384;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSeaLevel() {
/* 147 */     return -63;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\FlatLevelSource.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */