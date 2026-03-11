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
/*    */ public class HugeBrownMushroomFeature extends AbstractHugeMushroomFeature {
/*    */   public HugeBrownMushroomFeature(Codec<HugeMushroomFeatureConfiguration> $$0) {
/* 13 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void makeCap(LevelAccessor $$0, RandomSource $$1, BlockPos $$2, int $$3, BlockPos.MutableBlockPos $$4, HugeMushroomFeatureConfiguration $$5) {
/* 18 */     int $$6 = $$5.foliageRadius;
/* 19 */     for (int $$7 = -$$6; $$7 <= $$6; $$7++) {
/* 20 */       for (int $$8 = -$$6; $$8 <= $$6; $$8++) {
/* 21 */         boolean $$9 = ($$7 == -$$6);
/* 22 */         boolean $$10 = ($$7 == $$6);
/* 23 */         boolean $$11 = ($$8 == -$$6);
/* 24 */         boolean $$12 = ($$8 == $$6);
/*    */         
/* 26 */         boolean $$13 = ($$9 || $$10);
/* 27 */         boolean $$14 = ($$11 || $$12);
/* 28 */         if (!$$13 || !$$14) {
/*    */ 
/*    */ 
/*    */           
/* 32 */           $$4.setWithOffset((Vec3i)$$2, $$7, $$3, $$8);
/* 33 */           if (!$$0.getBlockState((BlockPos)$$4).isSolidRender((BlockGetter)$$0, (BlockPos)$$4)) {
/* 34 */             boolean $$15 = ($$9 || ($$14 && $$7 == 1 - $$6));
/* 35 */             boolean $$16 = ($$10 || ($$14 && $$7 == $$6 - 1));
/* 36 */             boolean $$17 = ($$11 || ($$13 && $$8 == 1 - $$6));
/* 37 */             boolean $$18 = ($$12 || ($$13 && $$8 == $$6 - 1));
/* 38 */             BlockState $$19 = $$5.capProvider.getState($$1, $$2);
/* 39 */             if ($$19.hasProperty((Property)HugeMushroomBlock.WEST) && $$19
/* 40 */               .hasProperty((Property)HugeMushroomBlock.EAST) && $$19
/* 41 */               .hasProperty((Property)HugeMushroomBlock.NORTH) && $$19
/* 42 */               .hasProperty((Property)HugeMushroomBlock.SOUTH))
/*    */             {
/*    */ 
/*    */ 
/*    */ 
/*    */               
/* 48 */               $$19 = (BlockState)((BlockState)((BlockState)((BlockState)$$19.setValue((Property)HugeMushroomBlock.WEST, Boolean.valueOf($$15))).setValue((Property)HugeMushroomBlock.EAST, Boolean.valueOf($$16))).setValue((Property)HugeMushroomBlock.NORTH, Boolean.valueOf($$17))).setValue((Property)HugeMushroomBlock.SOUTH, Boolean.valueOf($$18));
/*    */             }
/* 50 */             setBlock((LevelWriter)$$0, (BlockPos)$$4, $$19);
/*    */           } 
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   protected int getTreeRadiusForHeight(int $$0, int $$1, int $$2, int $$3) {
/* 58 */     return ($$3 <= 3) ? 0 : $$2;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\HugeBrownMushroomFeature.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */