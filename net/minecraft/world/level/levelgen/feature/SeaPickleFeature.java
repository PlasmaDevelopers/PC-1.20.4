/*    */ package net.minecraft.world.level.levelgen.feature;
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.LevelReader;
/*    */ import net.minecraft.world.level.WorldGenLevel;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.block.SeaPickleBlock;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ import net.minecraft.world.level.levelgen.Heightmap;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.CountConfiguration;
/*    */ 
/*    */ public class SeaPickleFeature extends Feature<CountConfiguration> {
/*    */   public SeaPickleFeature(Codec<CountConfiguration> $$0) {
/* 16 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean place(FeaturePlaceContext<CountConfiguration> $$0) {
/* 21 */     int $$1 = 0;
/* 22 */     RandomSource $$2 = $$0.random();
/* 23 */     WorldGenLevel $$3 = $$0.level();
/* 24 */     BlockPos $$4 = $$0.origin();
/* 25 */     int $$5 = ((CountConfiguration)$$0.config()).count().sample($$2);
/* 26 */     for (int $$6 = 0; $$6 < $$5; $$6++) {
/* 27 */       int $$7 = $$2.nextInt(8) - $$2.nextInt(8);
/* 28 */       int $$8 = $$2.nextInt(8) - $$2.nextInt(8);
/* 29 */       int $$9 = $$3.getHeight(Heightmap.Types.OCEAN_FLOOR, $$4.getX() + $$7, $$4.getZ() + $$8);
/* 30 */       BlockPos $$10 = new BlockPos($$4.getX() + $$7, $$9, $$4.getZ() + $$8);
/*    */       
/* 32 */       BlockState $$11 = (BlockState)Blocks.SEA_PICKLE.defaultBlockState().setValue((Property)SeaPickleBlock.PICKLES, Integer.valueOf($$2.nextInt(4) + 1));
/* 33 */       if ($$3.getBlockState($$10).is(Blocks.WATER) && $$11.canSurvive((LevelReader)$$3, $$10)) {
/* 34 */         $$3.setBlock($$10, $$11, 2);
/* 35 */         $$1++;
/*    */       } 
/*    */     } 
/* 38 */     return ($$1 > 0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\SeaPickleFeature.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */