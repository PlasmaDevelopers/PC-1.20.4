/*    */ package net.minecraft.world.level.levelgen.feature;
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.LevelAccessor;
/*    */ import net.minecraft.world.level.block.HugeMushroomBlock;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;
/*    */ 
/*    */ public class HugeRedMushroomFeature extends AbstractHugeMushroomFeature {
/*    */   public HugeRedMushroomFeature(Codec<HugeMushroomFeatureConfiguration> $$0) {
/* 13 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void makeCap(LevelAccessor $$0, RandomSource $$1, BlockPos $$2, int $$3, BlockPos.MutableBlockPos $$4, HugeMushroomFeatureConfiguration $$5) {
/* 18 */     for (int $$6 = $$3 - 3; $$6 <= $$3; $$6++) {
/* 19 */       int $$7 = ($$6 < $$3) ? $$5.foliageRadius : ($$5.foliageRadius - 1);
/* 20 */       int $$8 = $$5.foliageRadius - 2;
/*    */       
/* 22 */       for (int $$9 = -$$7; $$9 <= $$7; $$9++) {
/* 23 */         for (int $$10 = -$$7; $$10 <= $$7; $$10++) {
/* 24 */           boolean $$11 = ($$9 == -$$7);
/* 25 */           boolean $$12 = ($$9 == $$7);
/* 26 */           boolean $$13 = ($$10 == -$$7);
/* 27 */           boolean $$14 = ($$10 == $$7);
/*    */           
/* 29 */           boolean $$15 = ($$11 || $$12);
/* 30 */           boolean $$16 = ($$13 || $$14);
/*    */           
/* 32 */           if ($$6 >= $$3 || $$15 != $$16) {
/*    */ 
/*    */ 
/*    */             
/* 36 */             $$4.setWithOffset((Vec3i)$$2, $$9, $$6, $$10);
/* 37 */             if (!$$0.getBlockState((BlockPos)$$4).isSolidRender((BlockGetter)$$0, (BlockPos)$$4)) {
/* 38 */               BlockState $$17 = $$5.capProvider.getState($$1, $$2);
/* 39 */               if ($$17.hasProperty((Property)HugeMushroomBlock.WEST) && $$17
/* 40 */                 .hasProperty((Property)HugeMushroomBlock.EAST) && $$17
/* 41 */                 .hasProperty((Property)HugeMushroomBlock.NORTH) && $$17
/* 42 */                 .hasProperty((Property)HugeMushroomBlock.SOUTH) && $$17
/* 43 */                 .hasProperty((Property)HugeMushroomBlock.UP))
/*    */               {
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */                 
/* 50 */                 $$17 = (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)$$17.setValue((Property)HugeMushroomBlock.UP, Boolean.valueOf(($$6 >= $$3 - 1)))).setValue((Property)HugeMushroomBlock.WEST, Boolean.valueOf(($$9 < -$$8)))).setValue((Property)HugeMushroomBlock.EAST, Boolean.valueOf(($$9 > $$8)))).setValue((Property)HugeMushroomBlock.NORTH, Boolean.valueOf(($$10 < -$$8)))).setValue((Property)HugeMushroomBlock.SOUTH, Boolean.valueOf(($$10 > $$8)));
/*    */               }
/* 52 */               setBlock((LevelWriter)$$0, (BlockPos)$$4, $$17);
/*    */             } 
/*    */           } 
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   protected int getTreeRadiusForHeight(int $$0, int $$1, int $$2, int $$3) {
/* 61 */     int $$4 = 0;
/* 62 */     if ($$3 < $$1 && $$3 >= $$1 - 3) {
/* 63 */       $$4 = $$2;
/* 64 */     } else if ($$3 == $$1) {
/* 65 */       $$4 = $$2;
/*    */     } 
/* 67 */     return $$4;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\HugeRedMushroomFeature.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */