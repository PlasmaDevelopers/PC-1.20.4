/*    */ package net.minecraft.world.level.levelgen.feature;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.level.WorldGenLevel;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.LayerConfiguration;
/*    */ 
/*    */ public class FillLayerFeature
/*    */   extends Feature<LayerConfiguration> {
/*    */   public FillLayerFeature(Codec<LayerConfiguration> $$0) {
/* 11 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean place(FeaturePlaceContext<LayerConfiguration> $$0) {
/* 16 */     BlockPos $$1 = $$0.origin();
/* 17 */     LayerConfiguration $$2 = $$0.config();
/* 18 */     WorldGenLevel $$3 = $$0.level();
/* 19 */     BlockPos.MutableBlockPos $$4 = new BlockPos.MutableBlockPos();
/*    */     
/* 21 */     for (int $$5 = 0; $$5 < 16; $$5++) {
/* 22 */       for (int $$6 = 0; $$6 < 16; $$6++) {
/* 23 */         int $$7 = $$1.getX() + $$5;
/* 24 */         int $$8 = $$1.getZ() + $$6;
/* 25 */         int $$9 = $$3.getMinBuildHeight() + $$2.height;
/* 26 */         $$4.set($$7, $$9, $$8);
/*    */         
/* 28 */         if ($$3.getBlockState((BlockPos)$$4).isAir()) {
/* 29 */           $$3.setBlock((BlockPos)$$4, $$2.state, 2);
/*    */         }
/*    */       } 
/*    */     } 
/* 33 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\FillLayerFeature.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */