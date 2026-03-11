/*    */ package net.minecraft.world.level.levelgen.feature;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.tags.BlockTags;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.LevelReader;
/*    */ import net.minecraft.world.level.WorldGenLevel;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.NetherForestVegetationConfig;
/*    */ 
/*    */ public class NetherForestVegetationFeature extends Feature<NetherForestVegetationConfig> {
/*    */   public NetherForestVegetationFeature(Codec<NetherForestVegetationConfig> $$0) {
/* 14 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean place(FeaturePlaceContext<NetherForestVegetationConfig> $$0) {
/* 19 */     WorldGenLevel $$1 = $$0.level();
/* 20 */     BlockPos $$2 = $$0.origin();
/* 21 */     BlockState $$3 = $$1.getBlockState($$2.below());
/* 22 */     NetherForestVegetationConfig $$4 = $$0.config();
/* 23 */     RandomSource $$5 = $$0.random();
/*    */     
/* 25 */     if (!$$3.is(BlockTags.NYLIUM)) {
/* 26 */       return false;
/*    */     }
/*    */     
/* 29 */     int $$6 = $$2.getY();
/*    */     
/* 31 */     if ($$6 < $$1.getMinBuildHeight() + 1 || $$6 + 1 >= $$1.getMaxBuildHeight()) {
/* 32 */       return false;
/*    */     }
/*    */     
/* 35 */     int $$7 = 0;
/*    */ 
/*    */     
/* 38 */     for (int $$8 = 0; $$8 < $$4.spreadWidth * $$4.spreadWidth; $$8++) {
/* 39 */       BlockPos $$9 = $$2.offset($$5.nextInt($$4.spreadWidth) - $$5.nextInt($$4.spreadWidth), $$5.nextInt($$4.spreadHeight) - $$5.nextInt($$4.spreadHeight), $$5.nextInt($$4.spreadWidth) - $$5.nextInt($$4.spreadWidth));
/* 40 */       BlockState $$10 = $$4.stateProvider.getState($$5, $$9);
/* 41 */       if ($$1.isEmptyBlock($$9) && $$9.getY() > $$1.getMinBuildHeight() && 
/* 42 */         $$10.canSurvive((LevelReader)$$1, $$9)) {
/* 43 */         $$1.setBlock($$9, $$10, 2);
/* 44 */         $$7++;
/*    */       } 
/*    */     } 
/*    */ 
/*    */     
/* 49 */     return ($$7 > 0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\NetherForestVegetationFeature.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */