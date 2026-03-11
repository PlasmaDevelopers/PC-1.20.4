/*    */ package net.minecraft.util.valueproviders;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.List;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.util.random.SimpleWeightedRandomList;
/*    */ import net.minecraft.util.random.WeightedEntry;
/*    */ 
/*    */ public class WeightedListInt extends IntProvider {
/*    */   static {
/* 12 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)SimpleWeightedRandomList.wrappedCodec(IntProvider.CODEC).fieldOf("distribution").forGetter(())).apply((Applicative)$$0, WeightedListInt::new));
/*    */   }
/*    */   
/*    */   public static final Codec<WeightedListInt> CODEC;
/*    */   private final SimpleWeightedRandomList<IntProvider> distribution;
/*    */   private final int minValue;
/*    */   private final int maxValue;
/*    */   
/*    */   public WeightedListInt(SimpleWeightedRandomList<IntProvider> $$0) {
/* 21 */     this.distribution = $$0;
/* 22 */     List<WeightedEntry.Wrapper<IntProvider>> $$1 = $$0.unwrap();
/* 23 */     int $$2 = Integer.MAX_VALUE;
/* 24 */     int $$3 = Integer.MIN_VALUE;
/* 25 */     for (WeightedEntry.Wrapper<IntProvider> $$4 : $$1) {
/* 26 */       int $$5 = ((IntProvider)$$4.getData()).getMinValue();
/* 27 */       int $$6 = ((IntProvider)$$4.getData()).getMaxValue();
/* 28 */       $$2 = Math.min($$2, $$5);
/* 29 */       $$3 = Math.max($$3, $$6);
/*    */     } 
/* 31 */     this.minValue = $$2;
/* 32 */     this.maxValue = $$3;
/*    */   }
/*    */ 
/*    */   
/*    */   public int sample(RandomSource $$0) {
/* 37 */     return ((IntProvider)this.distribution.getRandomValue($$0).orElseThrow(IllegalStateException::new)).sample($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMinValue() {
/* 42 */     return this.minValue;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxValue() {
/* 47 */     return this.maxValue;
/*    */   }
/*    */ 
/*    */   
/*    */   public IntProviderType<?> getType() {
/* 52 */     return IntProviderType.WEIGHTED_LIST;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\valueproviders\WeightedListInt.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */