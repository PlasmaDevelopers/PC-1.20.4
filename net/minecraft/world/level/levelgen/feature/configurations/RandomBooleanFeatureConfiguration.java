/*    */ package net.minecraft.world.level.levelgen.feature.configurations;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.function.BiFunction;
/*    */ import java.util.stream.Stream;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.world.level.levelgen.placement.PlacedFeature;
/*    */ 
/*    */ public class RandomBooleanFeatureConfiguration implements FeatureConfiguration {
/*    */   static {
/* 12 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)PlacedFeature.CODEC.fieldOf("feature_true").forGetter(()), (App)PlacedFeature.CODEC.fieldOf("feature_false").forGetter(())).apply((Applicative)$$0, RandomBooleanFeatureConfiguration::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final Codec<RandomBooleanFeatureConfiguration> CODEC;
/*    */   public final Holder<PlacedFeature> featureTrue;
/*    */   public final Holder<PlacedFeature> featureFalse;
/*    */   
/*    */   public RandomBooleanFeatureConfiguration(Holder<PlacedFeature> $$0, Holder<PlacedFeature> $$1) {
/* 21 */     this.featureTrue = $$0;
/* 22 */     this.featureFalse = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public Stream<ConfiguredFeature<?, ?>> getFeatures() {
/* 27 */     return Stream.concat(((PlacedFeature)this.featureTrue.value()).getFeatures(), ((PlacedFeature)this.featureFalse.value()).getFeatures());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\configurations\RandomBooleanFeatureConfiguration.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */