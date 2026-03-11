/*    */ package net.minecraft.world.level.levelgen.feature.configurations;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.Codec;
/*    */ 
/*    */ public class PointedDripstoneConfiguration implements FeatureConfiguration {
/*    */   static {
/*  7 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Codec.floatRange(0.0F, 1.0F).fieldOf("chance_of_taller_dripstone").orElse(Float.valueOf(0.2F)).forGetter(()), (App)Codec.floatRange(0.0F, 1.0F).fieldOf("chance_of_directional_spread").orElse(Float.valueOf(0.7F)).forGetter(()), (App)Codec.floatRange(0.0F, 1.0F).fieldOf("chance_of_spread_radius2").orElse(Float.valueOf(0.5F)).forGetter(()), (App)Codec.floatRange(0.0F, 1.0F).fieldOf("chance_of_spread_radius3").orElse(Float.valueOf(0.5F)).forGetter(())).apply((Applicative)$$0, PointedDripstoneConfiguration::new));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static final Codec<PointedDripstoneConfiguration> CODEC;
/*    */   
/*    */   public final float chanceOfTallerDripstone;
/*    */   
/*    */   public final float chanceOfDirectionalSpread;
/*    */   
/*    */   public final float chanceOfSpreadRadius2;
/*    */   
/*    */   public final float chanceOfSpreadRadius3;
/*    */ 
/*    */   
/*    */   public PointedDripstoneConfiguration(float $$0, float $$1, float $$2, float $$3) {
/* 24 */     this.chanceOfTallerDripstone = $$0;
/* 25 */     this.chanceOfDirectionalSpread = $$1;
/* 26 */     this.chanceOfSpreadRadius2 = $$2;
/* 27 */     this.chanceOfSpreadRadius3 = $$3;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\configurations\PointedDripstoneConfiguration.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */