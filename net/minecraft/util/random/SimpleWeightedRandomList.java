/*    */ package net.minecraft.util.random;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import com.mojang.serialization.Codec;
/*    */ import java.util.List;
/*    */ import java.util.Optional;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.util.ExtraCodecs;
/*    */ import net.minecraft.util.RandomSource;
/*    */ 
/*    */ public class SimpleWeightedRandomList<E> extends WeightedRandomList<WeightedEntry.Wrapper<E>> {
/*    */   public static <E> Codec<SimpleWeightedRandomList<E>> wrappedCodecAllowingEmpty(Codec<E> $$0) {
/* 13 */     return WeightedEntry.Wrapper.<E>codec($$0).listOf().xmap(SimpleWeightedRandomList::new, WeightedRandomList::unwrap);
/*    */   }
/*    */   
/*    */   public static <E> Codec<SimpleWeightedRandomList<E>> wrappedCodec(Codec<E> $$0) {
/* 17 */     return ExtraCodecs.nonEmptyList(WeightedEntry.Wrapper.<E>codec($$0).listOf()).xmap(SimpleWeightedRandomList::new, WeightedRandomList::unwrap);
/*    */   }
/*    */   
/*    */   SimpleWeightedRandomList(List<? extends WeightedEntry.Wrapper<E>> $$0) {
/* 21 */     super($$0);
/*    */   }
/*    */   
/*    */   public static <E> Builder<E> builder() {
/* 25 */     return new Builder<>();
/*    */   }
/*    */   
/*    */   public static <E> SimpleWeightedRandomList<E> empty() {
/* 29 */     return new SimpleWeightedRandomList<>(List.of());
/*    */   }
/*    */   
/*    */   public static <E> SimpleWeightedRandomList<E> single(E $$0) {
/* 33 */     return new SimpleWeightedRandomList<>(List.of(WeightedEntry.wrap($$0, 1)));
/*    */   }
/*    */   
/*    */   public Optional<E> getRandomValue(RandomSource $$0) {
/* 37 */     return getRandom($$0).map(WeightedEntry.Wrapper::getData);
/*    */   }
/*    */   
/*    */   public static class Builder<E> {
/* 41 */     private final ImmutableList.Builder<WeightedEntry.Wrapper<E>> result = ImmutableList.builder();
/*    */     
/*    */     public Builder<E> add(E $$0) {
/* 44 */       return add($$0, 1);
/*    */     }
/*    */     
/*    */     public Builder<E> add(E $$0, int $$1) {
/* 48 */       this.result.add(WeightedEntry.wrap($$0, $$1));
/* 49 */       return this;
/*    */     }
/*    */     
/*    */     public SimpleWeightedRandomList<E> build() {
/* 53 */       return new SimpleWeightedRandomList<>((List<? extends WeightedEntry.Wrapper<E>>)this.result.build());
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\random\SimpleWeightedRandomList.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */