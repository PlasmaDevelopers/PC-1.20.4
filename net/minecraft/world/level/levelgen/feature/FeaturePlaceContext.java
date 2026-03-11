/*    */ package net.minecraft.world.level.levelgen.feature;
/*    */ 
/*    */ import java.util.Optional;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.WorldGenLevel;
/*    */ import net.minecraft.world.level.chunk.ChunkGenerator;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FeaturePlaceContext<FC extends FeatureConfiguration>
/*    */ {
/*    */   private final Optional<ConfiguredFeature<?, ?>> topFeature;
/*    */   private final WorldGenLevel level;
/*    */   private final ChunkGenerator chunkGenerator;
/*    */   private final RandomSource random;
/*    */   private final BlockPos origin;
/*    */   private final FC config;
/*    */   
/*    */   public FeaturePlaceContext(Optional<ConfiguredFeature<?, ?>> $$0, WorldGenLevel $$1, ChunkGenerator $$2, RandomSource $$3, BlockPos $$4, FC $$5) {
/* 23 */     this.topFeature = $$0;
/* 24 */     this.level = $$1;
/* 25 */     this.chunkGenerator = $$2;
/* 26 */     this.random = $$3;
/* 27 */     this.origin = $$4;
/* 28 */     this.config = $$5;
/*    */   }
/*    */   
/*    */   public Optional<ConfiguredFeature<?, ?>> topFeature() {
/* 32 */     return this.topFeature;
/*    */   }
/*    */   
/*    */   public WorldGenLevel level() {
/* 36 */     return this.level;
/*    */   }
/*    */   
/*    */   public ChunkGenerator chunkGenerator() {
/* 40 */     return this.chunkGenerator;
/*    */   }
/*    */   
/*    */   public RandomSource random() {
/* 44 */     return this.random;
/*    */   }
/*    */   
/*    */   public BlockPos origin() {
/* 48 */     return this.origin;
/*    */   }
/*    */   
/*    */   public FC config() {
/* 52 */     return this.config;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\FeaturePlaceContext.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */