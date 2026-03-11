/*    */ package net.minecraft.util.valueproviders;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.datafixers.util.Function4;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.DataResult;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.util.RandomSource;
/*    */ 
/*    */ public class ClampedNormalFloat extends FloatProvider {
/*    */   public static final Codec<ClampedNormalFloat> CODEC;
/*    */   
/*    */   static {
/* 17 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Codec.FLOAT.fieldOf("mean").forGetter(()), (App)Codec.FLOAT.fieldOf("deviation").forGetter(()), (App)Codec.FLOAT.fieldOf("min").forGetter(()), (App)Codec.FLOAT.fieldOf("max").forGetter(())).apply((Applicative)$$0, ClampedNormalFloat::new)).comapFlatMap($$0 -> ($$0.max < $$0.min) ? DataResult.error(()) : DataResult.success($$0), 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 24 */         Function.identity());
/*    */   }
/*    */   
/*    */   private final float mean;
/*    */   private final float deviation;
/*    */   private final float min;
/*    */   private final float max;
/*    */   
/*    */   public static ClampedNormalFloat of(float $$0, float $$1, float $$2, float $$3) {
/* 33 */     return new ClampedNormalFloat($$0, $$1, $$2, $$3);
/*    */   }
/*    */   
/*    */   private ClampedNormalFloat(float $$0, float $$1, float $$2, float $$3) {
/* 37 */     this.mean = $$0;
/* 38 */     this.deviation = $$1;
/* 39 */     this.min = $$2;
/* 40 */     this.max = $$3;
/*    */   }
/*    */ 
/*    */   
/*    */   public float sample(RandomSource $$0) {
/* 45 */     return sample($$0, this.mean, this.deviation, this.min, this.max);
/*    */   }
/*    */   
/*    */   public static float sample(RandomSource $$0, float $$1, float $$2, float $$3, float $$4) {
/* 49 */     return Mth.clamp(Mth.normal($$0, $$1, $$2), $$3, $$4);
/*    */   }
/*    */ 
/*    */   
/*    */   public float getMinValue() {
/* 54 */     return this.min;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getMaxValue() {
/* 59 */     return this.max;
/*    */   }
/*    */ 
/*    */   
/*    */   public FloatProviderType<?> getType() {
/* 64 */     return FloatProviderType.CLAMPED_NORMAL;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 69 */     return "normal(" + this.mean + ", " + this.deviation + ") in [" + this.min + "-" + this.max + "]";
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\valueproviders\ClampedNormalFloat.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */