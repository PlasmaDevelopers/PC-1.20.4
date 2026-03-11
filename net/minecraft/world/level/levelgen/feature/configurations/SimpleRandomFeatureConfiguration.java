/*    */ package net.minecraft.world.level.levelgen.feature.configurations;
/*    */ import java.util.function.Function;
/*    */ import java.util.stream.Stream;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.core.HolderSet;
/*    */ import net.minecraft.util.ExtraCodecs;
/*    */ import net.minecraft.world.level.levelgen.placement.PlacedFeature;
/*    */ 
/*    */ public class SimpleRandomFeatureConfiguration implements FeatureConfiguration {
/*    */   public static final Codec<SimpleRandomFeatureConfiguration> CODEC;
/*    */   
/*    */   static {
/* 13 */     CODEC = ExtraCodecs.nonEmptyHolderSet(PlacedFeature.LIST_CODEC).fieldOf("features").xmap(SimpleRandomFeatureConfiguration::new, $$0 -> $$0.features).codec();
/*    */   }
/*    */   public final HolderSet<PlacedFeature> features;
/*    */   
/*    */   public SimpleRandomFeatureConfiguration(HolderSet<PlacedFeature> $$0) {
/* 18 */     this.features = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public Stream<ConfiguredFeature<?, ?>> getFeatures() {
/* 23 */     return this.features.stream().flatMap($$0 -> ((PlacedFeature)$$0.value()).getFeatures());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\configurations\SimpleRandomFeatureConfiguration.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */