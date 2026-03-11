/*    */ package net.minecraft.world.level.levelgen.feature;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.WorldGenLevel;
/*    */ import net.minecraft.world.level.chunk.ChunkGenerator;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.SimpleRandomFeatureConfiguration;
/*    */ import net.minecraft.world.level.levelgen.placement.PlacedFeature;
/*    */ 
/*    */ public class SimpleRandomSelectorFeature extends Feature<SimpleRandomFeatureConfiguration> {
/*    */   public SimpleRandomSelectorFeature(Codec<SimpleRandomFeatureConfiguration> $$0) {
/* 13 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean place(FeaturePlaceContext<SimpleRandomFeatureConfiguration> $$0) {
/* 18 */     RandomSource $$1 = $$0.random();
/* 19 */     SimpleRandomFeatureConfiguration $$2 = $$0.config();
/* 20 */     WorldGenLevel $$3 = $$0.level();
/* 21 */     BlockPos $$4 = $$0.origin();
/* 22 */     ChunkGenerator $$5 = $$0.chunkGenerator();
/* 23 */     int $$6 = $$1.nextInt($$2.features.size());
/* 24 */     PlacedFeature $$7 = (PlacedFeature)$$2.features.get($$6).value();
/* 25 */     return $$7.place($$3, $$5, $$1, $$4);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\SimpleRandomSelectorFeature.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */