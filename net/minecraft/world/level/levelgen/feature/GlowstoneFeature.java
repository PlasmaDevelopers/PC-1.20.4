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
/*    */ public class GlowstoneFeature
/*    */   extends Feature<NoneFeatureConfiguration> {
/*    */   public GlowstoneFeature(Codec<NoneFeatureConfiguration> $$0) {
/* 15 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> $$0) {
/* 20 */     WorldGenLevel $$1 = $$0.level();
/* 21 */     BlockPos $$2 = $$0.origin();
/* 22 */     RandomSource $$3 = $$0.random();
/* 23 */     if (!$$1.isEmptyBlock($$2)) {
/* 24 */       return false;
/*    */     }
/*    */     
/* 27 */     BlockState $$4 = $$1.getBlockState($$2.above());
/* 28 */     if (!$$4.is(Blocks.NETHERRACK) && !$$4.is(Blocks.BASALT) && !$$4.is(Blocks.BLACKSTONE)) {
/* 29 */       return false;
/*    */     }
/*    */     
/* 32 */     $$1.setBlock($$2, Blocks.GLOWSTONE.defaultBlockState(), 2);
/*    */     
/* 34 */     for (int $$5 = 0; $$5 < 1500; $$5++) {
/* 35 */       BlockPos $$6 = $$2.offset($$3.nextInt(8) - $$3.nextInt(8), -$$3.nextInt(12), $$3.nextInt(8) - $$3.nextInt(8));
/* 36 */       if ($$1.getBlockState($$6).isAir()) {
/*    */ 
/*    */ 
/*    */         
/* 40 */         int $$7 = 0;
/* 41 */         for (Direction $$8 : Direction.values()) {
/* 42 */           if ($$1.getBlockState($$6.relative($$8)).is(Blocks.GLOWSTONE)) {
/* 43 */             $$7++;
/*    */           }
/*    */           
/* 46 */           if ($$7 > 1) {
/*    */             break;
/*    */           }
/*    */         } 
/*    */         
/* 51 */         if ($$7 == 1) {
/* 52 */           $$1.setBlock($$6, Blocks.GLOWSTONE.defaultBlockState(), 2);
/*    */         }
/*    */       } 
/*    */     } 
/* 56 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\GlowstoneFeature.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */