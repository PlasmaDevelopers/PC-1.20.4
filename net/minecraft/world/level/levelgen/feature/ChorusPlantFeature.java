/*    */ package net.minecraft.world.level.levelgen.feature;
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.LevelAccessor;
/*    */ import net.minecraft.world.level.WorldGenLevel;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.block.ChorusFlowerBlock;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
/*    */ 
/*    */ public class ChorusPlantFeature extends Feature<NoneFeatureConfiguration> {
/*    */   public ChorusPlantFeature(Codec<NoneFeatureConfiguration> $$0) {
/* 13 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> $$0) {
/* 18 */     WorldGenLevel $$1 = $$0.level();
/* 19 */     BlockPos $$2 = $$0.origin();
/* 20 */     RandomSource $$3 = $$0.random();
/* 21 */     if ($$1.isEmptyBlock($$2) && $$1.getBlockState($$2.below()).is(Blocks.END_STONE)) {
/* 22 */       ChorusFlowerBlock.generatePlant((LevelAccessor)$$1, $$2, $$3, 8);
/* 23 */       return true;
/*    */     } 
/* 25 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\ChorusPlantFeature.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */