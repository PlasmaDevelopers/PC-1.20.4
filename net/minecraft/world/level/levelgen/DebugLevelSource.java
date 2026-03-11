/*     */ package net.minecraft.world.level.levelgen;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.function.Function;
/*     */ import java.util.stream.Collectors;
/*     */ import java.util.stream.Stream;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.SectionPos;
/*     */ import net.minecraft.resources.RegistryOps;
/*     */ import net.minecraft.server.level.WorldGenRegion;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.LevelHeightAccessor;
/*     */ import net.minecraft.world.level.NoiseColumn;
/*     */ import net.minecraft.world.level.StructureManager;
/*     */ import net.minecraft.world.level.WorldGenLevel;
/*     */ import net.minecraft.world.level.biome.Biome;
/*     */ import net.minecraft.world.level.biome.BiomeManager;
/*     */ import net.minecraft.world.level.biome.Biomes;
/*     */ import net.minecraft.world.level.biome.FixedBiomeSource;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.chunk.ChunkAccess;
/*     */ import net.minecraft.world.level.chunk.ChunkGenerator;
/*     */ import net.minecraft.world.level.levelgen.blending.Blender;
/*     */ 
/*     */ public class DebugLevelSource extends ChunkGenerator {
/*     */   public static final Codec<DebugLevelSource> CODEC;
/*     */   
/*     */   static {
/*  37 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)RegistryOps.retrieveElement(Biomes.PLAINS)).apply((Applicative)$$0, $$0.stable(DebugLevelSource::new)));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  42 */     ALL_BLOCKS = (List<BlockState>)StreamSupport.stream(BuiltInRegistries.BLOCK.spliterator(), false).flatMap($$0 -> $$0.getStateDefinition().getPossibleStates().stream()).collect(Collectors.toList());
/*  43 */   } private static final int BLOCK_MARGIN = 2; private static final List<BlockState> ALL_BLOCKS; private static final int GRID_WIDTH = Mth.ceil(Mth.sqrt(ALL_BLOCKS.size()));
/*  44 */   private static final int GRID_HEIGHT = Mth.ceil(ALL_BLOCKS.size() / GRID_WIDTH);
/*     */   
/*  46 */   protected static final BlockState AIR = Blocks.AIR.defaultBlockState();
/*  47 */   protected static final BlockState BARRIER = Blocks.BARRIER.defaultBlockState();
/*     */   
/*     */   public static final int HEIGHT = 70;
/*     */   public static final int BARRIER_HEIGHT = 60;
/*     */   
/*     */   public DebugLevelSource(Holder.Reference<Biome> $$0) {
/*  53 */     super((BiomeSource)new FixedBiomeSource((Holder)$$0));
/*     */   }
/*     */ 
/*     */   
/*     */   protected Codec<? extends ChunkGenerator> codec() {
/*  58 */     return (Codec)CODEC;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void buildSurface(WorldGenRegion $$0, StructureManager $$1, RandomState $$2, ChunkAccess $$3) {}
/*     */ 
/*     */   
/*     */   public void applyBiomeDecoration(WorldGenLevel $$0, ChunkAccess $$1, StructureManager $$2) {
/*  67 */     BlockPos.MutableBlockPos $$3 = new BlockPos.MutableBlockPos();
/*     */     
/*  69 */     ChunkPos $$4 = $$1.getPos();
/*  70 */     int $$5 = $$4.x;
/*  71 */     int $$6 = $$4.z;
/*     */     
/*  73 */     for (int $$7 = 0; $$7 < 16; $$7++) {
/*  74 */       for (int $$8 = 0; $$8 < 16; $$8++) {
/*  75 */         int $$9 = SectionPos.sectionToBlockCoord($$5, $$7);
/*  76 */         int $$10 = SectionPos.sectionToBlockCoord($$6, $$8);
/*  77 */         $$0.setBlock((BlockPos)$$3.set($$9, 60, $$10), BARRIER, 2);
/*  78 */         BlockState $$11 = getBlockStateFor($$9, $$10);
/*  79 */         $$0.setBlock((BlockPos)$$3.set($$9, 70, $$10), $$11, 2);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public CompletableFuture<ChunkAccess> fillFromNoise(Executor $$0, Blender $$1, RandomState $$2, StructureManager $$3, ChunkAccess $$4) {
/*  86 */     return CompletableFuture.completedFuture($$4);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBaseHeight(int $$0, int $$1, Heightmap.Types $$2, LevelHeightAccessor $$3, RandomState $$4) {
/*  91 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public NoiseColumn getBaseColumn(int $$0, int $$1, LevelHeightAccessor $$2, RandomState $$3) {
/*  96 */     return new NoiseColumn(0, new BlockState[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addDebugScreenInfo(List<String> $$0, RandomState $$1, BlockPos $$2) {}
/*     */ 
/*     */   
/*     */   public static BlockState getBlockStateFor(int $$0, int $$1) {
/* 104 */     BlockState $$2 = AIR;
/*     */     
/* 106 */     if ($$0 > 0 && $$1 > 0 && $$0 % 2 != 0 && $$1 % 2 != 0) {
/* 107 */       $$0 /= 2;
/* 108 */       $$1 /= 2;
/*     */       
/* 110 */       if ($$0 <= GRID_WIDTH && $$1 <= GRID_HEIGHT) {
/* 111 */         int $$3 = Mth.abs($$0 * GRID_WIDTH + $$1);
/* 112 */         if ($$3 < ALL_BLOCKS.size()) {
/* 113 */           $$2 = ALL_BLOCKS.get($$3);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 118 */     return $$2;
/*     */   }
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
/* 131 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getGenDepth() {
/* 136 */     return 384;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSeaLevel() {
/* 141 */     return 63;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\DebugLevelSource.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */