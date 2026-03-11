/*    */ package net.minecraft.world.level.levelgen.feature;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.WorldGenLevel;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.BlockColumnConfiguration;
/*    */ 
/*    */ public class BlockColumnFeature
/*    */   extends Feature<BlockColumnConfiguration> {
/*    */   public BlockColumnFeature(Codec<BlockColumnConfiguration> $$0) {
/* 12 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean place(FeaturePlaceContext<BlockColumnConfiguration> $$0) {
/* 17 */     WorldGenLevel $$1 = $$0.level();
/* 18 */     BlockColumnConfiguration $$2 = $$0.config();
/* 19 */     RandomSource $$3 = $$0.random();
/*    */     
/* 21 */     int $$4 = $$2.layers().size();
/* 22 */     int[] $$5 = new int[$$4];
/* 23 */     int $$6 = 0;
/* 24 */     for (int $$7 = 0; $$7 < $$4; $$7++) {
/* 25 */       $$5[$$7] = ((BlockColumnConfiguration.Layer)$$2.layers().get($$7)).height().sample($$3);
/* 26 */       $$6 += $$5[$$7];
/*    */     } 
/* 28 */     if ($$6 == 0) {
/* 29 */       return false;
/*    */     }
/*    */     
/* 32 */     BlockPos.MutableBlockPos $$8 = $$0.origin().mutable();
/* 33 */     BlockPos.MutableBlockPos $$9 = $$8.mutable().move($$2.direction());
/* 34 */     for (int $$10 = 0; $$10 < $$6; $$10++) {
/* 35 */       if (!$$2.allowedPlacement().test($$1, $$9)) {
/* 36 */         truncate($$5, $$6, $$10, $$2.prioritizeTip());
/*    */         break;
/*    */       } 
/* 39 */       $$9.move($$2.direction());
/*    */     } 
/*    */     
/* 42 */     for (int $$11 = 0; $$11 < $$4; $$11++) {
/* 43 */       int $$12 = $$5[$$11];
/* 44 */       if ($$12 != 0) {
/*    */ 
/*    */ 
/*    */         
/* 48 */         BlockColumnConfiguration.Layer $$13 = $$2.layers().get($$11);
/* 49 */         for (int $$14 = 0; $$14 < $$12; $$14++) {
/* 50 */           $$1.setBlock((BlockPos)$$8, $$13.state().getState($$3, (BlockPos)$$8), 2);
/* 51 */           $$8.move($$2.direction());
/*    */         } 
/*    */       } 
/* 54 */     }  return true;
/*    */   }
/*    */ 
/*    */   
/*    */   private static void truncate(int[] $$0, int $$1, int $$2, boolean $$3) {
/* 59 */     int $$4 = $$1 - $$2;
/* 60 */     int $$5 = $$3 ? 1 : -1;
/* 61 */     int $$6 = $$3 ? 0 : ($$0.length - 1);
/* 62 */     int $$7 = $$3 ? $$0.length : -1;
/*    */     int $$8;
/* 64 */     for ($$8 = $$6; $$8 != $$7 && $$4 > 0; $$8 += $$5) {
/* 65 */       int $$9 = $$0[$$8];
/* 66 */       int $$10 = Math.min($$9, $$4);
/* 67 */       $$4 -= $$10;
/* 68 */       $$0[$$8] = $$0[$$8] - $$10;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\BlockColumnFeature.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */