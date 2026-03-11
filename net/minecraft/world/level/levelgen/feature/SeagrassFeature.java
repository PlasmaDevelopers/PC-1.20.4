/*    */ package net.minecraft.world.level.levelgen.feature;
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.LevelReader;
/*    */ import net.minecraft.world.level.WorldGenLevel;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.block.TallSeagrassBlock;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ import net.minecraft.world.level.levelgen.Heightmap;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;
/*    */ 
/*    */ public class SeagrassFeature extends Feature<ProbabilityFeatureConfiguration> {
/*    */   public SeagrassFeature(Codec<ProbabilityFeatureConfiguration> $$0) {
/* 17 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean place(FeaturePlaceContext<ProbabilityFeatureConfiguration> $$0) {
/* 22 */     boolean $$1 = false;
/* 23 */     RandomSource $$2 = $$0.random();
/* 24 */     WorldGenLevel $$3 = $$0.level();
/* 25 */     BlockPos $$4 = $$0.origin();
/* 26 */     ProbabilityFeatureConfiguration $$5 = $$0.config();
/* 27 */     int $$6 = $$2.nextInt(8) - $$2.nextInt(8);
/* 28 */     int $$7 = $$2.nextInt(8) - $$2.nextInt(8);
/* 29 */     int $$8 = $$3.getHeight(Heightmap.Types.OCEAN_FLOOR, $$4.getX() + $$6, $$4.getZ() + $$7);
/* 30 */     BlockPos $$9 = new BlockPos($$4.getX() + $$6, $$8, $$4.getZ() + $$7);
/*    */     
/* 32 */     if ($$3.getBlockState($$9).is(Blocks.WATER)) {
/* 33 */       boolean $$10 = ($$2.nextDouble() < $$5.probability);
/* 34 */       BlockState $$11 = $$10 ? Blocks.TALL_SEAGRASS.defaultBlockState() : Blocks.SEAGRASS.defaultBlockState();
/* 35 */       if ($$11.canSurvive((LevelReader)$$3, $$9)) {
/* 36 */         if ($$10) {
/* 37 */           BlockState $$12 = (BlockState)$$11.setValue((Property)TallSeagrassBlock.HALF, (Comparable)DoubleBlockHalf.UPPER);
/* 38 */           BlockPos $$13 = $$9.above();
/* 39 */           if ($$3.getBlockState($$13).is(Blocks.WATER)) {
/* 40 */             $$3.setBlock($$9, $$11, 2);
/* 41 */             $$3.setBlock($$13, $$12, 2);
/*    */           } 
/*    */         } else {
/* 44 */           $$3.setBlock($$9, $$11, 2);
/*    */         } 
/* 46 */         $$1 = true;
/*    */       } 
/*    */     } 
/* 49 */     return $$1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\SeagrassFeature.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */