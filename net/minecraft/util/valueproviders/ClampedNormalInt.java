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
/*    */ public class ClampedNormalInt extends IntProvider {
/*    */   public static final Codec<ClampedNormalInt> CODEC;
/*    */   
/*    */   static {
/* 17 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Codec.FLOAT.fieldOf("mean").forGetter(()), (App)Codec.FLOAT.fieldOf("deviation").forGetter(()), (App)Codec.INT.fieldOf("min_inclusive").forGetter(()), (App)Codec.INT.fieldOf("max_inclusive").forGetter(())).apply((Applicative)$$0, ClampedNormalInt::new)).comapFlatMap($$0 -> ($$0.max_inclusive < $$0.min_inclusive) ? DataResult.error(()) : DataResult.success($$0), 
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
/*    */   private final int min_inclusive;
/*    */   private final int max_inclusive;
/*    */   
/*    */   public static ClampedNormalInt of(float $$0, float $$1, int $$2, int $$3) {
/* 33 */     return new ClampedNormalInt($$0, $$1, $$2, $$3);
/*    */   }
/*    */   
/*    */   private ClampedNormalInt(float $$0, float $$1, int $$2, int $$3) {
/* 37 */     this.mean = $$0;
/* 38 */     this.deviation = $$1;
/* 39 */     this.min_inclusive = $$2;
/* 40 */     this.max_inclusive = $$3;
/*    */   }
/*    */ 
/*    */   
/*    */   public int sample(RandomSource $$0) {
/* 45 */     return sample($$0, this.mean, this.deviation, this.min_inclusive, this.max_inclusive);
/*    */   }
/*    */   
/*    */   public static int sample(RandomSource $$0, float $$1, float $$2, float $$3, float $$4) {
/* 49 */     return (int)Mth.clamp(Mth.normal($$0, $$1, $$2), $$3, $$4);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMinValue() {
/* 54 */     return this.min_inclusive;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxValue() {
/* 59 */     return this.max_inclusive;
/*    */   }
/*    */ 
/*    */   
/*    */   public IntProviderType<?> getType() {
/* 64 */     return IntProviderType.CLAMPED_NORMAL;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 69 */     return "normal(" + this.mean + ", " + this.deviation + ") in [" + this.min_inclusive + "-" + this.max_inclusive + "]";
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\valueproviders\ClampedNormalInt.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */