/*    */ package net.minecraft.world.level.levelgen.feature;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.level.WorldGenLevel;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.SpringConfiguration;
/*    */ 
/*    */ public class SpringFeature
/*    */   extends Feature<SpringConfiguration> {
/*    */   public SpringFeature(Codec<SpringConfiguration> $$0) {
/* 12 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean place(FeaturePlaceContext<SpringConfiguration> $$0) {
/* 17 */     SpringConfiguration $$1 = $$0.config();
/* 18 */     WorldGenLevel $$2 = $$0.level();
/* 19 */     BlockPos $$3 = $$0.origin();
/* 20 */     if (!$$2.getBlockState($$3.above()).is($$1.validBlocks)) {
/* 21 */       return false;
/*    */     }
/* 23 */     if ($$1.requiresBlockBelow && !$$2.getBlockState($$3.below()).is($$1.validBlocks)) {
/* 24 */       return false;
/*    */     }
/*    */     
/* 27 */     BlockState $$4 = $$2.getBlockState($$3);
/* 28 */     if (!$$4.isAir() && !$$4.is($$1.validBlocks)) {
/* 29 */       return false;
/*    */     }
/*    */     
/* 32 */     int $$5 = 0;
/*    */     
/* 34 */     int $$6 = 0;
/* 35 */     if ($$2.getBlockState($$3.west()).is($$1.validBlocks)) {
/* 36 */       $$6++;
/*    */     }
/* 38 */     if ($$2.getBlockState($$3.east()).is($$1.validBlocks)) {
/* 39 */       $$6++;
/*    */     }
/* 41 */     if ($$2.getBlockState($$3.north()).is($$1.validBlocks)) {
/* 42 */       $$6++;
/*    */     }
/* 44 */     if ($$2.getBlockState($$3.south()).is($$1.validBlocks)) {
/* 45 */       $$6++;
/*    */     }
/* 47 */     if ($$2.getBlockState($$3.below()).is($$1.validBlocks)) {
/* 48 */       $$6++;
/*    */     }
/*    */     
/* 51 */     int $$7 = 0;
/* 52 */     if ($$2.isEmptyBlock($$3.west())) {
/* 53 */       $$7++;
/*    */     }
/* 55 */     if ($$2.isEmptyBlock($$3.east())) {
/* 56 */       $$7++;
/*    */     }
/* 58 */     if ($$2.isEmptyBlock($$3.north())) {
/* 59 */       $$7++;
/*    */     }
/* 61 */     if ($$2.isEmptyBlock($$3.south())) {
/* 62 */       $$7++;
/*    */     }
/* 64 */     if ($$2.isEmptyBlock($$3.below())) {
/* 65 */       $$7++;
/*    */     }
/*    */     
/* 68 */     if ($$6 == $$1.rockCount && $$7 == $$1.holeCount) {
/* 69 */       $$2.setBlock($$3, $$1.state.createLegacyBlock(), 2);
/* 70 */       $$2.scheduleTick($$3, $$1.state.getType(), 0);
/* 71 */       $$5++;
/*    */     } 
/*    */     
/* 74 */     return ($$5 > 0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\SpringFeature.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */