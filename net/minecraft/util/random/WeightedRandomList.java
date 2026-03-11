/*    */ package net.minecraft.util.random;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import com.mojang.serialization.Codec;
/*    */ import java.util.List;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.util.RandomSource;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WeightedRandomList<E extends WeightedEntry>
/*    */ {
/*    */   private final int totalWeight;
/*    */   private final ImmutableList<E> items;
/*    */   
/*    */   WeightedRandomList(List<? extends E> $$0) {
/* 20 */     this.items = ImmutableList.copyOf($$0);
/* 21 */     this.totalWeight = WeightedRandom.getTotalWeight($$0);
/*    */   }
/*    */   
/*    */   public static <E extends WeightedEntry> WeightedRandomList<E> create() {
/* 25 */     return new WeightedRandomList<>((List<? extends E>)ImmutableList.of());
/*    */   }
/*    */   
/*    */   @SafeVarargs
/*    */   public static <E extends WeightedEntry> WeightedRandomList<E> create(E... $$0) {
/* 30 */     return new WeightedRandomList<>((List<? extends E>)ImmutableList.copyOf((Object[])$$0));
/*    */   }
/*    */   
/*    */   public static <E extends WeightedEntry> WeightedRandomList<E> create(List<E> $$0) {
/* 34 */     return new WeightedRandomList<>($$0);
/*    */   }
/*    */   
/*    */   public boolean isEmpty() {
/* 38 */     return this.items.isEmpty();
/*    */   }
/*    */   
/*    */   public Optional<E> getRandom(RandomSource $$0) {
/* 42 */     if (this.totalWeight == 0) {
/* 43 */       return Optional.empty();
/*    */     }
/*    */     
/* 46 */     int $$1 = $$0.nextInt(this.totalWeight);
/* 47 */     return WeightedRandom.getWeightedItem((List<E>)this.items, $$1);
/*    */   }
/*    */   
/*    */   public List<E> unwrap() {
/* 51 */     return (List<E>)this.items;
/*    */   }
/*    */   
/*    */   public static <E extends WeightedEntry> Codec<WeightedRandomList<E>> codec(Codec<E> $$0) {
/* 55 */     return $$0.listOf().xmap(WeightedRandomList::create, WeightedRandomList::unwrap);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\random\WeightedRandomList.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */