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
/*    */ public class UniformInt extends IntProvider {
/*    */   static {
/* 15 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Codec.INT.fieldOf("min_inclusive").forGetter(()), (App)Codec.INT.fieldOf("max_inclusive").forGetter(())).apply((Applicative)$$0, UniformInt::new)).comapFlatMap($$0 -> ($$0.maxInclusive < $$0.minInclusive) ? DataResult.error(()) : DataResult.success($$0), 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 22 */         Function.identity());
/*    */   }
/*    */ 
/*    */   
/*    */   public static final Codec<UniformInt> CODEC;
/*    */   
/*    */   private UniformInt(int $$0, int $$1) {
/* 29 */     this.minInclusive = $$0;
/* 30 */     this.maxInclusive = $$1;
/*    */   }
/*    */   private final int minInclusive; private final int maxInclusive;
/*    */   public static UniformInt of(int $$0, int $$1) {
/* 34 */     return new UniformInt($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public int sample(RandomSource $$0) {
/* 39 */     return Mth.randomBetweenInclusive($$0, this.minInclusive, this.maxInclusive);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMinValue() {
/* 44 */     return this.minInclusive;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxValue() {
/* 49 */     return this.maxInclusive;
/*    */   }
/*    */ 
/*    */   
/*    */   public IntProviderType<?> getType() {
/* 54 */     return IntProviderType.UNIFORM;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 59 */     return "[" + this.minInclusive + "-" + this.maxInclusive + "]";
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\valueproviders\UniformInt.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */