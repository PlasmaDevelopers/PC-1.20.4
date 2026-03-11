/*    */ package net.minecraft.world.level.levelgen.carver;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.util.valueproviders.FloatProvider;
/*    */ 
/*    */ public class CanyonCarverConfiguration extends CarverConfiguration {
/*    */   public static final Codec<CanyonCarverConfiguration> CODEC;
/*    */   public final FloatProvider verticalRotation;
/*    */   public final CanyonShapeConfiguration shape;
/*    */   
/*    */   public static class CanyonShapeConfiguration {
/*    */     static {
/* 14 */       CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)FloatProvider.CODEC.fieldOf("distance_factor").forGetter(()), (App)FloatProvider.CODEC.fieldOf("thickness").forGetter(()), (App)ExtraCodecs.NON_NEGATIVE_INT.fieldOf("width_smoothness").forGetter(()), (App)FloatProvider.CODEC.fieldOf("horizontal_radius_factor").forGetter(()), (App)Codec.FLOAT.fieldOf("vertical_radius_default_factor").forGetter(()), (App)Codec.FLOAT.fieldOf("vertical_radius_center_factor").forGetter(())).apply((Applicative)$$0, CanyonShapeConfiguration::new));
/*    */     }
/*    */ 
/*    */     
/*    */     public static final Codec<CanyonShapeConfiguration> CODEC;
/*    */     
/*    */     public final FloatProvider distanceFactor;
/*    */     
/*    */     public final FloatProvider thickness;
/*    */     
/*    */     public final int widthSmoothness;
/*    */     
/*    */     public final FloatProvider horizontalRadiusFactor;
/*    */     public final float verticalRadiusDefaultFactor;
/*    */     public final float verticalRadiusCenterFactor;
/*    */     
/*    */     public CanyonShapeConfiguration(FloatProvider $$0, FloatProvider $$1, int $$2, FloatProvider $$3, float $$4, float $$5) {
/* 31 */       this.widthSmoothness = $$2;
/* 32 */       this.horizontalRadiusFactor = $$3;
/* 33 */       this.verticalRadiusDefaultFactor = $$4;
/* 34 */       this.verticalRadiusCenterFactor = $$5;
/* 35 */       this.distanceFactor = $$0;
/* 36 */       this.thickness = $$1;
/*    */     } }
/*    */   
/*    */   static {
/* 40 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)CarverConfiguration.CODEC.forGetter(()), (App)FloatProvider.CODEC.fieldOf("vertical_rotation").forGetter(()), (App)CanyonShapeConfiguration.CODEC.fieldOf("shape").forGetter(())).apply((Applicative)$$0, CanyonCarverConfiguration::new));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public CanyonCarverConfiguration(float $$0, HeightProvider $$1, FloatProvider $$2, VerticalAnchor $$3, CarverDebugSettings $$4, HolderSet<Block> $$5, FloatProvider $$6, CanyonShapeConfiguration $$7) {
/* 50 */     super($$0, $$1, $$2, $$3, $$4, $$5);
/* 51 */     this.verticalRotation = $$6;
/* 52 */     this.shape = $$7;
/*    */   }
/*    */   
/*    */   public CanyonCarverConfiguration(CarverConfiguration $$0, FloatProvider $$1, CanyonShapeConfiguration $$2) {
/* 56 */     this($$0.probability, $$0.y, $$0.yScale, $$0.lavaLevel, $$0.debugSettings, $$0.replaceable, $$1, $$2);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\carver\CanyonCarverConfiguration.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */