/*    */ package net.minecraft.world.level.levelgen.feature;
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.WorldGenLevel;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.block.VineBlock;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
/*    */ 
/*    */ public class VinesFeature extends Feature<NoneFeatureConfiguration> {
/*    */   public VinesFeature(Codec<NoneFeatureConfiguration> $$0) {
/* 14 */     super($$0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> $$0) {
/* 31 */     WorldGenLevel $$1 = $$0.level();
/* 32 */     BlockPos $$2 = $$0.origin();
/* 33 */     $$0.config();
/* 34 */     if (!$$1.isEmptyBlock($$2)) {
/* 35 */       return false;
/*    */     }
/*    */     
/* 38 */     for (Direction $$3 : Direction.values()) {
/* 39 */       if ($$3 != Direction.DOWN)
/*    */       {
/*    */ 
/*    */         
/* 43 */         if (VineBlock.isAcceptableNeighbour((BlockGetter)$$1, $$2.relative($$3), $$3)) {
/* 44 */           $$1.setBlock($$2, (BlockState)Blocks.VINE.defaultBlockState().setValue((Property)VineBlock.getPropertyForFace($$3), Boolean.valueOf(true)), 2);
/* 45 */           return true;
/*    */         }  } 
/*    */     } 
/* 48 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\VinesFeature.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */