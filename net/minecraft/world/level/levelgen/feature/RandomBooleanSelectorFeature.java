/*    */ package net.minecraft.world.level.levelgen.feature;
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.WorldGenLevel;
/*    */ import net.minecraft.world.level.chunk.ChunkGenerator;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.RandomBooleanFeatureConfiguration;
/*    */ import net.minecraft.world.level.levelgen.placement.PlacedFeature;
/*    */ 
/*    */ public class RandomBooleanSelectorFeature extends Feature<RandomBooleanFeatureConfiguration> {
/*    */   public RandomBooleanSelectorFeature(Codec<RandomBooleanFeatureConfiguration> $$0) {
/* 12 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean place(FeaturePlaceContext<RandomBooleanFeatureConfiguration> $$0) {
/* 17 */     RandomSource $$1 = $$0.random();
/* 18 */     RandomBooleanFeatureConfiguration $$2 = $$0.config();
/* 19 */     WorldGenLevel $$3 = $$0.level();
/* 20 */     ChunkGenerator $$4 = $$0.chunkGenerator();
/* 21 */     BlockPos $$5 = $$0.origin();
/* 22 */     boolean $$6 = $$1.nextBoolean();
/* 23 */     return ((PlacedFeature)($$6 ? $$2.featureTrue : $$2.featureFalse).value()).place($$3, $$4, $$1, $$5);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\RandomBooleanSelectorFeature.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */