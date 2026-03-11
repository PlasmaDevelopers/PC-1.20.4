/*    */ package net.minecraft.world.level.levelgen.feature.configurations;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.util.valueproviders.FloatProvider;
/*    */ import net.minecraft.util.valueproviders.IntProvider;
/*    */ 
/*    */ public class DripstoneClusterConfiguration implements FeatureConfiguration {
/*    */   static {
/* 10 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Codec.intRange(1, 512).fieldOf("floor_to_ceiling_search_range").forGetter(()), (App)IntProvider.codec(1, 128).fieldOf("height").forGetter(()), (App)IntProvider.codec(1, 128).fieldOf("radius").forGetter(()), (App)Codec.intRange(0, 64).fieldOf("max_stalagmite_stalactite_height_diff").forGetter(()), (App)Codec.intRange(1, 64).fieldOf("height_deviation").forGetter(()), (App)IntProvider.codec(0, 128).fieldOf("dripstone_block_layer_thickness").forGetter(()), (App)FloatProvider.codec(0.0F, 2.0F).fieldOf("density").forGetter(()), (App)FloatProvider.codec(0.0F, 2.0F).fieldOf("wetness").forGetter(()), (App)Codec.floatRange(0.0F, 1.0F).fieldOf("chance_of_dripstone_column_at_max_distance_from_center").forGetter(()), (App)Codec.intRange(1, 64).fieldOf("max_distance_from_edge_affecting_chance_of_dripstone_column").forGetter(()), (App)Codec.intRange(1, 64).fieldOf("max_distance_from_center_affecting_height_bias").forGetter(())).apply((Applicative)$$0, DripstoneClusterConfiguration::new));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static final Codec<DripstoneClusterConfiguration> CODEC;
/*    */ 
/*    */   
/*    */   public final int floorToCeilingSearchRange;
/*    */   
/*    */   public final IntProvider height;
/*    */   
/*    */   public final IntProvider radius;
/*    */   
/*    */   public final int maxStalagmiteStalactiteHeightDiff;
/*    */   
/*    */   public final int heightDeviation;
/*    */   
/*    */   public final IntProvider dripstoneBlockLayerThickness;
/*    */   
/*    */   public final FloatProvider density;
/*    */   
/*    */   public final FloatProvider wetness;
/*    */   
/*    */   public final float chanceOfDripstoneColumnAtMaxDistanceFromCenter;
/*    */   
/*    */   public final int maxDistanceFromEdgeAffectingChanceOfDripstoneColumn;
/*    */   
/*    */   public final int maxDistanceFromCenterAffectingHeightBias;
/*    */ 
/*    */   
/*    */   public DripstoneClusterConfiguration(int $$0, IntProvider $$1, IntProvider $$2, int $$3, int $$4, IntProvider $$5, FloatProvider $$6, FloatProvider $$7, float $$8, int $$9, int $$10) {
/* 42 */     this.floorToCeilingSearchRange = $$0;
/* 43 */     this.height = $$1;
/* 44 */     this.radius = $$2;
/* 45 */     this.maxStalagmiteStalactiteHeightDiff = $$3;
/* 46 */     this.heightDeviation = $$4;
/* 47 */     this.dripstoneBlockLayerThickness = $$5;
/* 48 */     this.density = $$6;
/* 49 */     this.wetness = $$7;
/* 50 */     this.chanceOfDripstoneColumnAtMaxDistanceFromCenter = $$8;
/* 51 */     this.maxDistanceFromEdgeAffectingChanceOfDripstoneColumn = $$9;
/* 52 */     this.maxDistanceFromCenterAffectingHeightBias = $$10;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\configurations\DripstoneClusterConfiguration.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */