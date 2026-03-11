/*    */ package net.minecraft.world.level.levelgen.feature;
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.LevelWriter;
/*    */ import net.minecraft.world.level.WorldGenLevel;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
/*    */ 
/*    */ public class EndIslandFeature extends Feature<NoneFeatureConfiguration> {
/*    */   public EndIslandFeature(Codec<NoneFeatureConfiguration> $$0) {
/* 13 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> $$0) {
/* 18 */     WorldGenLevel $$1 = $$0.level();
/* 19 */     RandomSource $$2 = $$0.random();
/* 20 */     BlockPos $$3 = $$0.origin();
/* 21 */     float $$4 = $$2.nextInt(3) + 4.0F;
/* 22 */     int $$5 = 0;
/* 23 */     while ($$4 > 0.5F) {
/* 24 */       for (int $$6 = Mth.floor(-$$4); $$6 <= Mth.ceil($$4); $$6++) {
/* 25 */         for (int $$7 = Mth.floor(-$$4); $$7 <= Mth.ceil($$4); $$7++) {
/* 26 */           if (($$6 * $$6 + $$7 * $$7) <= ($$4 + 1.0F) * ($$4 + 1.0F)) {
/* 27 */             setBlock((LevelWriter)$$1, $$3.offset($$6, $$5, $$7), Blocks.END_STONE.defaultBlockState());
/*    */           }
/*    */         } 
/*    */       } 
/* 31 */       $$4 -= $$2.nextInt(2) + 0.5F;
/* 32 */       $$5--;
/*    */     } 
/*    */     
/* 35 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\EndIslandFeature.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */