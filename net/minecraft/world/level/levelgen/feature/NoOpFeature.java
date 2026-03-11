/*    */ package net.minecraft.world.level.levelgen.feature;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
/*    */ 
/*    */ public class NoOpFeature extends Feature<NoneFeatureConfiguration> {
/*    */   public NoOpFeature(Codec<NoneFeatureConfiguration> $$0) {
/*  8 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> $$0) {
/* 13 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\NoOpFeature.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */