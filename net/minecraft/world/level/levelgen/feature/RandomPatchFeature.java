/*    */ package net.minecraft.world.level.levelgen.feature;
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Vec3i;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.WorldGenLevel;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
/*    */ 
/*    */ public class RandomPatchFeature extends Feature<RandomPatchConfiguration> {
/*    */   public RandomPatchFeature(Codec<RandomPatchConfiguration> $$0) {
/* 11 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean place(FeaturePlaceContext<RandomPatchConfiguration> $$0) {
/* 16 */     RandomPatchConfiguration $$1 = $$0.config();
/* 17 */     RandomSource $$2 = $$0.random();
/* 18 */     BlockPos $$3 = $$0.origin();
/* 19 */     WorldGenLevel $$4 = $$0.level();
/*    */     
/* 21 */     int $$5 = 0;
/*    */     
/* 23 */     BlockPos.MutableBlockPos $$6 = new BlockPos.MutableBlockPos();
/* 24 */     int $$7 = $$1.xzSpread() + 1;
/* 25 */     int $$8 = $$1.ySpread() + 1;
/* 26 */     for (int $$9 = 0; $$9 < $$1.tries(); $$9++) {
/* 27 */       $$6.setWithOffset((Vec3i)$$3, $$2.nextInt($$7) - $$2.nextInt($$7), $$2.nextInt($$8) - $$2.nextInt($$8), $$2.nextInt($$7) - $$2.nextInt($$7));
/* 28 */       if (((PlacedFeature)$$1.feature().value()).place($$4, $$0.chunkGenerator(), $$2, (BlockPos)$$6)) {
/* 29 */         $$5++;
/*    */       }
/*    */     } 
/*    */     
/* 33 */     return ($$5 > 0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\RandomPatchFeature.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */