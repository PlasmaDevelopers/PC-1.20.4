/*    */ package net.minecraft.world.level.levelgen.feature.featuresize;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.core.Registry;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ 
/*    */ public class FeatureSizeType<P extends FeatureSize> {
/*  8 */   public static final FeatureSizeType<TwoLayersFeatureSize> TWO_LAYERS_FEATURE_SIZE = register("two_layers_feature_size", TwoLayersFeatureSize.CODEC);
/*  9 */   public static final FeatureSizeType<ThreeLayersFeatureSize> THREE_LAYERS_FEATURE_SIZE = register("three_layers_feature_size", ThreeLayersFeatureSize.CODEC);
/*    */   
/*    */   private static <P extends FeatureSize> FeatureSizeType<P> register(String $$0, Codec<P> $$1) {
/* 12 */     return (FeatureSizeType<P>)Registry.register(BuiltInRegistries.FEATURE_SIZE_TYPE, $$0, new FeatureSizeType<>($$1));
/*    */   }
/*    */   
/*    */   private final Codec<P> codec;
/*    */   
/*    */   private FeatureSizeType(Codec<P> $$0) {
/* 18 */     this.codec = $$0;
/*    */   }
/*    */   
/*    */   public Codec<P> codec() {
/* 22 */     return this.codec;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\featuresize\FeatureSizeType.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */