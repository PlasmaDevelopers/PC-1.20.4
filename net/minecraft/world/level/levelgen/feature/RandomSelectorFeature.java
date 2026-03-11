/*    */ package net.minecraft.world.level.levelgen.feature;
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.WorldGenLevel;
/*    */ import net.minecraft.world.level.chunk.ChunkGenerator;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
/*    */ import net.minecraft.world.level.levelgen.placement.PlacedFeature;
/*    */ 
/*    */ public class RandomSelectorFeature extends Feature<RandomFeatureConfiguration> {
/*    */   public RandomSelectorFeature(Codec<RandomFeatureConfiguration> $$0) {
/* 12 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean place(FeaturePlaceContext<RandomFeatureConfiguration> $$0) {
/* 17 */     RandomFeatureConfiguration $$1 = $$0.config();
/* 18 */     RandomSource $$2 = $$0.random();
/* 19 */     WorldGenLevel $$3 = $$0.level();
/* 20 */     ChunkGenerator $$4 = $$0.chunkGenerator();
/* 21 */     BlockPos $$5 = $$0.origin();
/* 22 */     for (WeightedPlacedFeature $$6 : $$1.features) {
/* 23 */       if ($$2.nextFloat() < $$6.chance) {
/* 24 */         return $$6.place($$3, $$4, $$2, $$5);
/*    */       }
/*    */     } 
/* 27 */     return ((PlacedFeature)$$1.defaultFeature.value()).place($$3, $$4, $$2, $$5);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\RandomSelectorFeature.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */