/*    */ package net.minecraft.world.level.levelgen.feature;
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.level.LevelAccessor;
/*    */ import net.minecraft.world.level.LevelReader;
/*    */ import net.minecraft.world.level.WorldGenLevel;
/*    */ import net.minecraft.world.level.block.DoublePlantBlock;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
/*    */ 
/*    */ public class SimpleBlockFeature extends Feature<SimpleBlockConfiguration> {
/*    */   public SimpleBlockFeature(Codec<SimpleBlockConfiguration> $$0) {
/* 13 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean place(FeaturePlaceContext<SimpleBlockConfiguration> $$0) {
/* 18 */     SimpleBlockConfiguration $$1 = $$0.config();
/* 19 */     WorldGenLevel $$2 = $$0.level();
/* 20 */     BlockPos $$3 = $$0.origin();
/* 21 */     BlockState $$4 = $$1.toPlace().getState($$0.random(), $$3);
/*    */     
/* 23 */     if ($$4.canSurvive((LevelReader)$$2, $$3)) {
/* 24 */       if ($$4.getBlock() instanceof DoublePlantBlock) {
/* 25 */         if ($$2.isEmptyBlock($$3.above())) {
/* 26 */           DoublePlantBlock.placeAt((LevelAccessor)$$2, $$4, $$3, 2);
/*    */         } else {
/* 28 */           return false;
/*    */         } 
/*    */       } else {
/* 31 */         $$2.setBlock($$3, $$4, 2);
/*    */       } 
/* 33 */       return true;
/*    */     } 
/* 35 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\SimpleBlockFeature.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */