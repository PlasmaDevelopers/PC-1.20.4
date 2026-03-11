/*    */ package net.minecraft.util.valueproviders;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.datafixers.util.Function3;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.DataResult;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.util.RandomSource;
/*    */ 
/*    */ public class ClampedInt
/*    */   extends IntProvider {
/*    */   static {
/* 16 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)IntProvider.CODEC.fieldOf("source").forGetter(()), (App)Codec.INT.fieldOf("min_inclusive").forGetter(()), (App)Codec.INT.fieldOf("max_inclusive").forGetter(())).apply((Applicative)$$0, ClampedInt::new)).comapFlatMap($$0 -> ($$0.maxInclusive < $$0.minInclusive) ? DataResult.error(()) : DataResult.success($$0), 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 23 */         Function.identity());
/*    */   }
/*    */   public static final Codec<ClampedInt> CODEC;
/*    */   private final IntProvider source;
/*    */   private final int minInclusive;
/*    */   private final int maxInclusive;
/*    */   
/*    */   public static ClampedInt of(IntProvider $$0, int $$1, int $$2) {
/* 31 */     return new ClampedInt($$0, $$1, $$2);
/*    */   }
/*    */   
/*    */   public ClampedInt(IntProvider $$0, int $$1, int $$2) {
/* 35 */     this.source = $$0;
/* 36 */     this.minInclusive = $$1;
/* 37 */     this.maxInclusive = $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   public int sample(RandomSource $$0) {
/* 42 */     return Mth.clamp(this.source.sample($$0), this.minInclusive, this.maxInclusive);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMinValue() {
/* 47 */     return Math.max(this.minInclusive, this.source.getMinValue());
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxValue() {
/* 52 */     return Math.min(this.maxInclusive, this.source.getMaxValue());
/*    */   }
/*    */ 
/*    */   
/*    */   public IntProviderType<?> getType() {
/* 57 */     return IntProviderType.CLAMPED;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\valueproviders\ClampedInt.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */