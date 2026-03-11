/*    */ package net.minecraft.world.level.levelgen.feature;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.WorldGenLevel;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
/*    */ 
/*    */ public class BlueIceFeature
/*    */   extends Feature<NoneFeatureConfiguration> {
/*    */   public BlueIceFeature(Codec<NoneFeatureConfiguration> $$0) {
/* 15 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> $$0) {
/* 20 */     BlockPos $$1 = $$0.origin();
/* 21 */     WorldGenLevel $$2 = $$0.level();
/* 22 */     RandomSource $$3 = $$0.random();
/* 23 */     if ($$1.getY() > $$2.getSeaLevel() - 1) {
/* 24 */       return false;
/*    */     }
/* 26 */     if (!$$2.getBlockState($$1).is(Blocks.WATER) && !$$2.getBlockState($$1.below()).is(Blocks.WATER)) {
/* 27 */       return false;
/*    */     }
/*    */     
/* 30 */     boolean $$4 = false;
/* 31 */     for (Direction $$5 : Direction.values()) {
/* 32 */       if ($$5 != Direction.DOWN)
/*    */       {
/*    */         
/* 35 */         if ($$2.getBlockState($$1.relative($$5)).is(Blocks.PACKED_ICE)) {
/* 36 */           $$4 = true;
/*    */           break;
/*    */         }  } 
/*    */     } 
/* 40 */     if (!$$4) {
/* 41 */       return false;
/*    */     }
/*    */     
/* 44 */     $$2.setBlock($$1, Blocks.BLUE_ICE.defaultBlockState(), 2);
/*    */     
/* 46 */     for (int $$6 = 0; $$6 < 200; $$6++) {
/* 47 */       int $$7 = $$3.nextInt(5) - $$3.nextInt(6);
/* 48 */       int $$8 = 3;
/* 49 */       if ($$7 < 2) {
/* 50 */         $$8 += $$7 / 2;
/*    */       }
/* 52 */       if ($$8 >= 1) {
/*    */ 
/*    */ 
/*    */         
/* 56 */         BlockPos $$9 = $$1.offset($$3.nextInt($$8) - $$3.nextInt($$8), $$7, $$3.nextInt($$8) - $$3.nextInt($$8));
/* 57 */         BlockState $$10 = $$2.getBlockState($$9);
/* 58 */         if ($$10.isAir() || $$10.is(Blocks.WATER) || $$10.is(Blocks.PACKED_ICE) || $$10.is(Blocks.ICE))
/*    */         {
/*    */ 
/*    */           
/* 62 */           for (Direction $$11 : Direction.values()) {
/* 63 */             BlockState $$12 = $$2.getBlockState($$9.relative($$11));
/* 64 */             if ($$12.is(Blocks.BLUE_ICE)) {
/* 65 */               $$2.setBlock($$9, Blocks.BLUE_ICE.defaultBlockState(), 2);
/*    */               break;
/*    */             } 
/*    */           }  } 
/*    */       } 
/*    */     } 
/* 71 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\BlueIceFeature.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */