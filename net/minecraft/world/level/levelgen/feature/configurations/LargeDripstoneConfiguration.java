/*    */ package net.minecraft.world.level.levelgen.feature.configurations;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.util.valueproviders.FloatProvider;
/*    */ 
/*    */ public class LargeDripstoneConfiguration implements FeatureConfiguration {
/*    */   static {
/*  9 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Codec.intRange(1, 512).fieldOf("floor_to_ceiling_search_range").orElse(Integer.valueOf(30)).forGetter(()), (App)IntProvider.codec(1, 60).fieldOf("column_radius").forGetter(()), (App)FloatProvider.codec(0.0F, 20.0F).fieldOf("height_scale").forGetter(()), (App)Codec.floatRange(0.1F, 1.0F).fieldOf("max_column_radius_to_cave_height_ratio").forGetter(()), (App)FloatProvider.codec(0.1F, 10.0F).fieldOf("stalactite_bluntness").forGetter(()), (App)FloatProvider.codec(0.1F, 10.0F).fieldOf("stalagmite_bluntness").forGetter(()), (App)FloatProvider.codec(0.0F, 2.0F).fieldOf("wind_speed").forGetter(()), (App)Codec.intRange(0, 100).fieldOf("min_radius_for_wind").forGetter(()), (App)Codec.floatRange(0.0F, 5.0F).fieldOf("min_bluntness_for_wind").forGetter(())).apply((Applicative)$$0, LargeDripstoneConfiguration::new));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static final Codec<LargeDripstoneConfiguration> CODEC;
/*    */ 
/*    */   
/*    */   public final int floorToCeilingSearchRange;
/*    */ 
/*    */   
/*    */   public final IntProvider columnRadius;
/*    */ 
/*    */   
/*    */   public final FloatProvider heightScale;
/*    */ 
/*    */   
/*    */   public final float maxColumnRadiusToCaveHeightRatio;
/*    */ 
/*    */   
/*    */   public final FloatProvider stalactiteBluntness;
/*    */ 
/*    */   
/*    */   public final FloatProvider stalagmiteBluntness;
/*    */ 
/*    */   
/*    */   public final FloatProvider windSpeed;
/*    */ 
/*    */   
/*    */   public final int minRadiusForWind;
/*    */   
/*    */   public final float minBluntnessForWind;
/*    */ 
/*    */   
/*    */   public LargeDripstoneConfiguration(int $$0, IntProvider $$1, FloatProvider $$2, float $$3, FloatProvider $$4, FloatProvider $$5, FloatProvider $$6, int $$7, float $$8) {
/* 44 */     this.floorToCeilingSearchRange = $$0;
/* 45 */     this.columnRadius = $$1;
/* 46 */     this.heightScale = $$2;
/* 47 */     this.maxColumnRadiusToCaveHeightRatio = $$3;
/* 48 */     this.stalactiteBluntness = $$4;
/* 49 */     this.stalagmiteBluntness = $$5;
/* 50 */     this.windSpeed = $$6;
/* 51 */     this.minRadiusForWind = $$7;
/* 52 */     this.minBluntnessForWind = $$8;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\configurations\LargeDripstoneConfiguration.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */