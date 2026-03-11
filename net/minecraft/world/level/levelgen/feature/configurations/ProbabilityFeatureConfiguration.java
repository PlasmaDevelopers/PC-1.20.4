/*    */ package net.minecraft.world.level.levelgen.feature.configurations;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ 
/*    */ public class ProbabilityFeatureConfiguration implements FeatureConfiguration {
/*    */   static {
/*  7 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Codec.floatRange(0.0F, 1.0F).fieldOf("probability").forGetter(())).apply((Applicative)$$0, ProbabilityFeatureConfiguration::new));
/*    */   }
/*    */   
/*    */   public static final Codec<ProbabilityFeatureConfiguration> CODEC;
/*    */   public final float probability;
/*    */   
/*    */   public ProbabilityFeatureConfiguration(float $$0) {
/* 14 */     this.probability = $$0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\configurations\ProbabilityFeatureConfiguration.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */