/*    */ package net.minecraft.world.level.levelgen.feature;
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.LevelReader;
/*    */ import net.minecraft.world.level.WorldGenLevel;
/*    */ import net.minecraft.world.level.block.BambooStalkBlock;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.properties.BambooLeaves;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ import net.minecraft.world.level.levelgen.Heightmap;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;
/*    */ 
/*    */ public class BambooFeature extends Feature<ProbabilityFeatureConfiguration> {
/* 17 */   private static final BlockState BAMBOO_TRUNK = (BlockState)((BlockState)((BlockState)Blocks.BAMBOO.defaultBlockState().setValue((Property)BambooStalkBlock.AGE, Integer.valueOf(1))).setValue((Property)BambooStalkBlock.LEAVES, (Comparable)BambooLeaves.NONE)).setValue((Property)BambooStalkBlock.STAGE, Integer.valueOf(0));
/* 18 */   private static final BlockState BAMBOO_FINAL_LARGE = (BlockState)((BlockState)BAMBOO_TRUNK.setValue((Property)BambooStalkBlock.LEAVES, (Comparable)BambooLeaves.LARGE)).setValue((Property)BambooStalkBlock.STAGE, Integer.valueOf(1));
/* 19 */   private static final BlockState BAMBOO_TOP_LARGE = (BlockState)BAMBOO_TRUNK.setValue((Property)BambooStalkBlock.LEAVES, (Comparable)BambooLeaves.LARGE);
/* 20 */   private static final BlockState BAMBOO_TOP_SMALL = (BlockState)BAMBOO_TRUNK.setValue((Property)BambooStalkBlock.LEAVES, (Comparable)BambooLeaves.SMALL);
/*    */   
/*    */   public BambooFeature(Codec<ProbabilityFeatureConfiguration> $$0) {
/* 23 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean place(FeaturePlaceContext<ProbabilityFeatureConfiguration> $$0) {
/* 28 */     int $$1 = 0;
/*    */     
/* 30 */     BlockPos $$2 = $$0.origin();
/* 31 */     WorldGenLevel $$3 = $$0.level();
/* 32 */     RandomSource $$4 = $$0.random();
/* 33 */     ProbabilityFeatureConfiguration $$5 = $$0.config();
/* 34 */     BlockPos.MutableBlockPos $$6 = $$2.mutable();
/* 35 */     BlockPos.MutableBlockPos $$7 = $$2.mutable();
/* 36 */     if ($$3.isEmptyBlock((BlockPos)$$6)) {
/* 37 */       if (Blocks.BAMBOO.defaultBlockState().canSurvive((LevelReader)$$3, (BlockPos)$$6)) {
/* 38 */         int $$8 = $$4.nextInt(12) + 5;
/*    */ 
/*    */         
/* 41 */         if ($$4.nextFloat() < $$5.probability) {
/* 42 */           int $$9 = $$4.nextInt(4) + 1;
/* 43 */           for (int $$10 = $$2.getX() - $$9; $$10 <= $$2.getX() + $$9; $$10++) {
/* 44 */             for (int $$11 = $$2.getZ() - $$9; $$11 <= $$2.getZ() + $$9; $$11++) {
/* 45 */               int $$12 = $$10 - $$2.getX();
/* 46 */               int $$13 = $$11 - $$2.getZ();
/* 47 */               if ($$12 * $$12 + $$13 * $$13 <= $$9 * $$9) {
/*    */ 
/*    */ 
/*    */                 
/* 51 */                 $$7.set($$10, $$3.getHeight(Heightmap.Types.WORLD_SURFACE, $$10, $$11) - 1, $$11);
/* 52 */                 if (isDirt($$3.getBlockState((BlockPos)$$7))) {
/* 53 */                   $$3.setBlock((BlockPos)$$7, Blocks.PODZOL.defaultBlockState(), 2);
/*    */                 }
/*    */               } 
/*    */             } 
/*    */           } 
/*    */         } 
/* 59 */         for (int $$14 = 0; $$14 < $$8 && 
/* 60 */           $$3.isEmptyBlock((BlockPos)$$6); $$14++) {
/* 61 */           $$3.setBlock((BlockPos)$$6, BAMBOO_TRUNK, 2);
/*    */ 
/*    */ 
/*    */           
/* 65 */           $$6.move(Direction.UP, 1);
/*    */         } 
/*    */         
/* 68 */         if ($$6.getY() - $$2.getY() >= 3) {
/* 69 */           $$3.setBlock((BlockPos)$$6, BAMBOO_FINAL_LARGE, 2);
/* 70 */           $$3.setBlock((BlockPos)$$6.move(Direction.DOWN, 1), BAMBOO_TOP_LARGE, 2);
/* 71 */           $$3.setBlock((BlockPos)$$6.move(Direction.DOWN, 1), BAMBOO_TOP_SMALL, 2);
/*    */         } 
/*    */       } 
/* 74 */       $$1++;
/*    */     } 
/*    */     
/* 77 */     return ($$1 > 0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\BambooFeature.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */