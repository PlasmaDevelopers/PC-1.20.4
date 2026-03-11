/*    */ package net.minecraft.world.level.levelgen.feature;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Vec3i;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.WorldGenLevel;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
/*    */ 
/*    */ public class BlockBlobFeature extends Feature<BlockStateConfiguration> {
/*    */   public BlockBlobFeature(Codec<BlockStateConfiguration> $$0) {
/* 13 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean place(FeaturePlaceContext<BlockStateConfiguration> $$0) {
/* 18 */     BlockPos $$1 = $$0.origin();
/* 19 */     WorldGenLevel $$2 = $$0.level();
/* 20 */     RandomSource $$3 = $$0.random();
/* 21 */     BlockStateConfiguration $$4 = $$0.config();
/* 22 */     while ($$1.getY() > $$2.getMinBuildHeight() + 3) {
/* 23 */       if (!$$2.isEmptyBlock($$1.below())) {
/* 24 */         BlockState $$5 = $$2.getBlockState($$1.below());
/* 25 */         if (isDirt($$5) || isStone($$5)) {
/*    */           break;
/*    */         }
/*    */       } 
/* 29 */       $$1 = $$1.below();
/*    */     } 
/* 31 */     if ($$1.getY() <= $$2.getMinBuildHeight() + 3) {
/* 32 */       return false;
/*    */     }
/*    */     
/* 35 */     int $$6 = 0;
/* 36 */     while ($$6 < 3) {
/* 37 */       int $$7 = $$3.nextInt(2);
/* 38 */       int $$8 = $$3.nextInt(2);
/* 39 */       int $$9 = $$3.nextInt(2);
/* 40 */       float $$10 = ($$7 + $$8 + $$9) * 0.333F + 0.5F;
/*    */       
/* 42 */       for (BlockPos $$11 : BlockPos.betweenClosed($$1.offset(-$$7, -$$8, -$$9), $$1.offset($$7, $$8, $$9))) {
/* 43 */         if ($$11.distSqr((Vec3i)$$1) <= ($$10 * $$10)) {
/* 44 */           $$2.setBlock($$11, $$4.state, 3);
/*    */         }
/*    */       } 
/*    */       
/* 48 */       $$1 = $$1.offset(-1 + $$3.nextInt(2), -$$3.nextInt(2), -1 + $$3.nextInt(2));
/* 49 */       $$6++;
/*    */     } 
/*    */     
/* 52 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\BlockBlobFeature.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */