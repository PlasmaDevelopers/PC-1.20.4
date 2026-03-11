/*    */ package net.minecraft.world.level.levelgen.feature.configurations;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.Codec;
/*    */ 
/*    */ public class UnderwaterMagmaConfiguration implements FeatureConfiguration {
/*    */   static {
/*  7 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Codec.intRange(0, 512).fieldOf("floor_search_range").forGetter(()), (App)Codec.intRange(0, 64).fieldOf("placement_radius_around_floor").forGetter(()), (App)Codec.floatRange(0.0F, 1.0F).fieldOf("placement_probability_per_valid_position").forGetter(())).apply((Applicative)$$0, UnderwaterMagmaConfiguration::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final Codec<UnderwaterMagmaConfiguration> CODEC;
/*    */   
/*    */   public final int floorSearchRange;
/*    */   public final int placementRadiusAroundFloor;
/*    */   public final float placementProbabilityPerValidPosition;
/*    */   
/*    */   public UnderwaterMagmaConfiguration(int $$0, int $$1, float $$2) {
/* 18 */     this.floorSearchRange = $$0;
/* 19 */     this.placementRadiusAroundFloor = $$1;
/* 20 */     this.placementProbabilityPerValidPosition = $$2;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\configurations\UnderwaterMagmaConfiguration.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */