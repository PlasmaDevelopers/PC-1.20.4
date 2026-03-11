/*    */ package net.minecraft.world.level.levelgen.feature.configurations;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.List;
/*    */ import java.util.function.BiFunction;
/*    */ import java.util.stream.Stream;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
/*    */ import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
/*    */ import net.minecraft.world.level.levelgen.placement.PlacedFeature;
/*    */ 
/*    */ public class RandomFeatureConfiguration implements FeatureConfiguration {
/*    */   static {
/* 15 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.apply2(RandomFeatureConfiguration::new, (App)WeightedPlacedFeature.CODEC.listOf().fieldOf("features").forGetter(()), (App)PlacedFeature.CODEC.fieldOf("default").forGetter(())));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final Codec<RandomFeatureConfiguration> CODEC;
/*    */   
/*    */   public final List<WeightedPlacedFeature> features;
/*    */   public final Holder<PlacedFeature> defaultFeature;
/*    */   
/*    */   public RandomFeatureConfiguration(List<WeightedPlacedFeature> $$0, Holder<PlacedFeature> $$1) {
/* 25 */     this.features = $$0;
/* 26 */     this.defaultFeature = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public Stream<ConfiguredFeature<?, ?>> getFeatures() {
/* 31 */     return Stream.concat(this.features.stream().flatMap($$0 -> ((PlacedFeature)$$0.feature.value()).getFeatures()), ((PlacedFeature)this.defaultFeature.value()).getFeatures());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\configurations\RandomFeatureConfiguration.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */