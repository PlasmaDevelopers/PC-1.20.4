/*    */ package net.minecraft.util.valueproviders;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.DataResult;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.function.BiFunction;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.util.RandomSource;
/*    */ 
/*    */ public class UniformFloat
/*    */   extends FloatProvider {
/*    */   public static final Codec<UniformFloat> CODEC;
/*    */   
/*    */   static {
/* 18 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Codec.FLOAT.fieldOf("min_inclusive").forGetter(()), (App)Codec.FLOAT.fieldOf("max_exclusive").forGetter(())).apply((Applicative)$$0, UniformFloat::new)).comapFlatMap($$0 -> ($$0.maxExclusive <= $$0.minInclusive) ? DataResult.error(()) : DataResult.success($$0), 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 25 */         Function.identity());
/*    */   }
/*    */   
/*    */   private final float minInclusive;
/*    */   private final float maxExclusive;
/*    */   
/*    */   private UniformFloat(float $$0, float $$1) {
/* 32 */     this.minInclusive = $$0;
/* 33 */     this.maxExclusive = $$1;
/*    */   }
/*    */   
/*    */   public static UniformFloat of(float $$0, float $$1) {
/* 37 */     if ($$1 <= $$0) {
/* 38 */       throw new IllegalArgumentException("Max must exceed min");
/*    */     }
/* 40 */     return new UniformFloat($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public float sample(RandomSource $$0) {
/* 45 */     return Mth.randomBetween($$0, this.minInclusive, this.maxExclusive);
/*    */   }
/*    */ 
/*    */   
/*    */   public float getMinValue() {
/* 50 */     return this.minInclusive;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getMaxValue() {
/* 55 */     return this.maxExclusive;
/*    */   }
/*    */ 
/*    */   
/*    */   public FloatProviderType<?> getType() {
/* 60 */     return FloatProviderType.UNIFORM;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 65 */     return "[" + this.minInclusive + "-" + this.maxExclusive + "]";
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\valueproviders\UniformFloat.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */