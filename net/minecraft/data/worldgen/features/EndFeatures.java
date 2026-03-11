/*    */ package net.minecraft.data.worldgen.features;
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.List;
/*    */ import net.minecraft.data.worldgen.BootstapContext;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
/*    */ import net.minecraft.world.level.levelgen.feature.Feature;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.EndGatewayConfiguration;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.SpikeConfiguration;
/*    */ 
/*    */ public class EndFeatures {
/* 13 */   public static final ResourceKey<ConfiguredFeature<?, ?>> END_SPIKE = FeatureUtils.createKey("end_spike");
/* 14 */   public static final ResourceKey<ConfiguredFeature<?, ?>> END_GATEWAY_RETURN = FeatureUtils.createKey("end_gateway_return");
/* 15 */   public static final ResourceKey<ConfiguredFeature<?, ?>> END_GATEWAY_DELAYED = FeatureUtils.createKey("end_gateway_delayed");
/* 16 */   public static final ResourceKey<ConfiguredFeature<?, ?>> CHORUS_PLANT = FeatureUtils.createKey("chorus_plant");
/* 17 */   public static final ResourceKey<ConfiguredFeature<?, ?>> END_ISLAND = FeatureUtils.createKey("end_island");
/*    */   
/*    */   public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> $$0) {
/* 20 */     FeatureUtils.register($$0, END_SPIKE, Feature.END_SPIKE, new SpikeConfiguration(false, 
/*    */           
/* 22 */           (List)ImmutableList.of(), null));
/*    */ 
/*    */     
/* 25 */     FeatureUtils.register($$0, END_GATEWAY_RETURN, Feature.END_GATEWAY, 
/* 26 */         EndGatewayConfiguration.knownExit(ServerLevel.END_SPAWN_POINT, true));
/*    */     
/* 28 */     FeatureUtils.register($$0, END_GATEWAY_DELAYED, Feature.END_GATEWAY, EndGatewayConfiguration.delayedExitSearch());
/* 29 */     FeatureUtils.register($$0, CHORUS_PLANT, Feature.CHORUS_PLANT);
/* 30 */     FeatureUtils.register($$0, END_ISLAND, Feature.END_ISLAND);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\worldgen\features\EndFeatures.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */