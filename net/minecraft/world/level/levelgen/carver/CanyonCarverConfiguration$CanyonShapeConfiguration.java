/*    */ package net.minecraft.world.level.levelgen.carver;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.datafixers.util.Function6;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.util.ExtraCodecs;
/*    */ import net.minecraft.util.valueproviders.FloatProvider;
/*    */ 
/*    */ public class CanyonShapeConfiguration
/*    */ {
/*    */   static {
/* 14 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)FloatProvider.CODEC.fieldOf("distance_factor").forGetter(()), (App)FloatProvider.CODEC.fieldOf("thickness").forGetter(()), (App)ExtraCodecs.NON_NEGATIVE_INT.fieldOf("width_smoothness").forGetter(()), (App)FloatProvider.CODEC.fieldOf("horizontal_radius_factor").forGetter(()), (App)Codec.FLOAT.fieldOf("vertical_radius_default_factor").forGetter(()), (App)Codec.FLOAT.fieldOf("vertical_radius_center_factor").forGetter(())).apply((Applicative)$$0, CanyonShapeConfiguration::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final Codec<CanyonShapeConfiguration> CODEC;
/*    */   
/*    */   public final FloatProvider distanceFactor;
/*    */   
/*    */   public final FloatProvider thickness;
/*    */   
/*    */   public final int widthSmoothness;
/*    */   
/*    */   public final FloatProvider horizontalRadiusFactor;
/*    */   public final float verticalRadiusDefaultFactor;
/*    */   public final float verticalRadiusCenterFactor;
/*    */   
/*    */   public CanyonShapeConfiguration(FloatProvider $$0, FloatProvider $$1, int $$2, FloatProvider $$3, float $$4, float $$5) {
/* 31 */     this.widthSmoothness = $$2;
/* 32 */     this.horizontalRadiusFactor = $$3;
/* 33 */     this.verticalRadiusDefaultFactor = $$4;
/* 34 */     this.verticalRadiusCenterFactor = $$5;
/* 35 */     this.distanceFactor = $$0;
/* 36 */     this.thickness = $$1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\carver\CanyonCarverConfiguration$CanyonShapeConfiguration.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */