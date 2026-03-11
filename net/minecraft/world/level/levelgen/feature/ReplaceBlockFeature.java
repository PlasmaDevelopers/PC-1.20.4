/*    */ package net.minecraft.world.level.levelgen.feature;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.level.WorldGenLevel;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.ReplaceBlockConfiguration;
/*    */ 
/*    */ public class ReplaceBlockFeature
/*    */   extends Feature<ReplaceBlockConfiguration> {
/*    */   public ReplaceBlockFeature(Codec<ReplaceBlockConfiguration> $$0) {
/* 12 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean place(FeaturePlaceContext<ReplaceBlockConfiguration> $$0) {
/* 17 */     WorldGenLevel $$1 = $$0.level();
/* 18 */     BlockPos $$2 = $$0.origin();
/* 19 */     ReplaceBlockConfiguration $$3 = $$0.config();
/* 20 */     for (OreConfiguration.TargetBlockState $$4 : $$3.targetStates) {
/* 21 */       if ($$4.target.test($$1.getBlockState($$2), $$0.random())) {
/* 22 */         $$1.setBlock($$2, $$4.state, 2);
/*    */         break;
/*    */       } 
/*    */     } 
/* 26 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\ReplaceBlockFeature.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */