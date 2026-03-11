/*    */ package net.minecraft.world.flag;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import java.util.Set;
/*    */ import java.util.stream.Collectors;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ 
/*    */ public class FeatureFlags {
/*    */   public static final FeatureFlag VANILLA;
/*    */   public static final FeatureFlag BUNDLE;
/*    */   public static final FeatureFlag UPDATE_1_21;
/*    */   public static final FeatureFlag TRADE_REBALANCE;
/*    */   public static final FeatureFlagRegistry REGISTRY;
/*    */   public static final Codec<FeatureFlagSet> CODEC;
/*    */   public static final FeatureFlagSet VANILLA_SET;
/*    */   public static final FeatureFlagSet DEFAULT_FLAGS;
/*    */   
/*    */   static {
/* 19 */     FeatureFlagRegistry.Builder $$0 = new FeatureFlagRegistry.Builder("main");
/* 20 */     VANILLA = $$0.createVanilla("vanilla");
/* 21 */     BUNDLE = $$0.createVanilla("bundle");
/* 22 */     TRADE_REBALANCE = $$0.createVanilla("trade_rebalance");
/* 23 */     UPDATE_1_21 = $$0.createVanilla("update_1_21");
/*    */     
/* 25 */     REGISTRY = $$0.build();
/*    */ 
/*    */     
/* 28 */     CODEC = REGISTRY.codec();
/*    */     
/* 30 */     VANILLA_SET = FeatureFlagSet.of(VANILLA);
/* 31 */     DEFAULT_FLAGS = VANILLA_SET;
/*    */   }
/*    */   public static String printMissingFlags(FeatureFlagSet $$0, FeatureFlagSet $$1) {
/* 34 */     return printMissingFlags(REGISTRY, $$0, $$1);
/*    */   }
/*    */   
/*    */   public static String printMissingFlags(FeatureFlagRegistry $$0, FeatureFlagSet $$1, FeatureFlagSet $$2) {
/* 38 */     Set<ResourceLocation> $$3 = $$0.toNames($$2);
/* 39 */     Set<ResourceLocation> $$4 = $$0.toNames($$1);
/* 40 */     return $$3.stream().filter($$1 -> !$$0.contains($$1)).map(ResourceLocation::toString).collect(Collectors.joining(", "));
/*    */   }
/*    */   
/*    */   public static boolean isExperimental(FeatureFlagSet $$0) {
/* 44 */     return !$$0.isSubsetOf(VANILLA_SET);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\flag\FeatureFlags.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */