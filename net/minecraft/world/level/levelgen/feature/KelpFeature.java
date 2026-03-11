/*    */ package net.minecraft.world.level.levelgen.feature;
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.LevelReader;
/*    */ import net.minecraft.world.level.WorldGenLevel;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.block.KelpBlock;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ import net.minecraft.world.level.levelgen.Heightmap;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
/*    */ 
/*    */ public class KelpFeature extends Feature<NoneFeatureConfiguration> {
/*    */   public KelpFeature(Codec<NoneFeatureConfiguration> $$0) {
/* 16 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> $$0) {
/* 21 */     int $$1 = 0;
/* 22 */     WorldGenLevel $$2 = $$0.level();
/* 23 */     BlockPos $$3 = $$0.origin();
/* 24 */     RandomSource $$4 = $$0.random();
/* 25 */     int $$5 = $$2.getHeight(Heightmap.Types.OCEAN_FLOOR, $$3.getX(), $$3.getZ());
/* 26 */     BlockPos $$6 = new BlockPos($$3.getX(), $$5, $$3.getZ());
/*    */     
/* 28 */     if ($$2.getBlockState($$6).is(Blocks.WATER)) {
/* 29 */       BlockState $$7 = Blocks.KELP.defaultBlockState();
/* 30 */       BlockState $$8 = Blocks.KELP_PLANT.defaultBlockState();
/* 31 */       int $$9 = 1 + $$4.nextInt(10);
/* 32 */       for (int $$10 = 0; $$10 <= $$9; $$10++) {
/* 33 */         if ($$2.getBlockState($$6).is(Blocks.WATER) && $$2.getBlockState($$6.above()).is(Blocks.WATER) && $$8.canSurvive((LevelReader)$$2, $$6)) {
/* 34 */           if ($$10 == $$9) {
/* 35 */             $$2.setBlock($$6, (BlockState)$$7.setValue((Property)KelpBlock.AGE, Integer.valueOf($$4.nextInt(4) + 20)), 2);
/* 36 */             $$1++;
/*    */           } else {
/* 38 */             $$2.setBlock($$6, $$8, 2);
/*    */           } 
/* 40 */         } else if ($$10 > 0) {
/* 41 */           BlockPos $$11 = $$6.below();
/* 42 */           if ($$7.canSurvive((LevelReader)$$2, $$11) && !$$2.getBlockState($$11.below()).is(Blocks.KELP)) {
/* 43 */             $$2.setBlock($$11, (BlockState)$$7.setValue((Property)KelpBlock.AGE, Integer.valueOf($$4.nextInt(4) + 20)), 2);
/* 44 */             $$1++;
/*    */           } 
/*    */           
/*    */           break;
/*    */         } 
/* 49 */         $$6 = $$6.above();
/*    */       } 
/*    */     } 
/*    */     
/* 53 */     return ($$1 > 0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\KelpFeature.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */