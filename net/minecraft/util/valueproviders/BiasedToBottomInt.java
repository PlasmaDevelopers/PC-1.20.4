/*    */ package net.minecraft.util.valueproviders;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.DataResult;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.function.BiFunction;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.util.RandomSource;
/*    */ 
/*    */ public class BiasedToBottomInt extends IntProvider {
/*    */   static {
/* 14 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Codec.INT.fieldOf("min_inclusive").forGetter(()), (App)Codec.INT.fieldOf("max_inclusive").forGetter(())).apply((Applicative)$$0, BiasedToBottomInt::new)).comapFlatMap($$0 -> ($$0.maxInclusive < $$0.minInclusive) ? DataResult.error(()) : DataResult.success($$0), 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 21 */         Function.identity());
/*    */   }
/*    */ 
/*    */   
/*    */   public static final Codec<BiasedToBottomInt> CODEC;
/*    */   
/*    */   private BiasedToBottomInt(int $$0, int $$1) {
/* 28 */     this.minInclusive = $$0;
/* 29 */     this.maxInclusive = $$1;
/*    */   }
/*    */   private final int minInclusive; private final int maxInclusive;
/*    */   public static BiasedToBottomInt of(int $$0, int $$1) {
/* 33 */     return new BiasedToBottomInt($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public int sample(RandomSource $$0) {
/* 38 */     return this.minInclusive + $$0.nextInt($$0.nextInt(this.maxInclusive - this.minInclusive + 1) + 1);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMinValue() {
/* 43 */     return this.minInclusive;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxValue() {
/* 48 */     return this.maxInclusive;
/*    */   }
/*    */ 
/*    */   
/*    */   public IntProviderType<?> getType() {
/* 53 */     return IntProviderType.BIASED_TO_BOTTOM;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 58 */     return "[" + this.minInclusive + "-" + this.maxInclusive + "]";
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\valueproviders\BiasedToBottomInt.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */