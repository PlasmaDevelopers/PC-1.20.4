/*    */ package net.minecraft.world.level.levelgen;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ 
/*    */ public class GeodeLayerSettings {
/*  7 */   private static final Codec<Double> LAYER_RANGE = Codec.doubleRange(0.01D, 50.0D); public static final Codec<GeodeLayerSettings> CODEC; static {
/*  8 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)LAYER_RANGE.fieldOf("filling").orElse(Double.valueOf(1.7D)).forGetter(()), (App)LAYER_RANGE.fieldOf("inner_layer").orElse(Double.valueOf(2.2D)).forGetter(()), (App)LAYER_RANGE.fieldOf("middle_layer").orElse(Double.valueOf(3.2D)).forGetter(()), (App)LAYER_RANGE.fieldOf("outer_layer").orElse(Double.valueOf(4.2D)).forGetter(())).apply((Applicative)$$0, GeodeLayerSettings::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public final double filling;
/*    */   
/*    */   public final double innerLayer;
/*    */   
/*    */   public final double middleLayer;
/*    */   
/*    */   public final double outerLayer;
/*    */   
/*    */   public GeodeLayerSettings(double $$0, double $$1, double $$2, double $$3) {
/* 21 */     this.filling = $$0;
/* 22 */     this.innerLayer = $$1;
/* 23 */     this.middleLayer = $$2;
/* 24 */     this.outerLayer = $$3;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\GeodeLayerSettings.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */