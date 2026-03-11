/*    */ package net.minecraft.util.random;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Builder<E>
/*    */ {
/* 41 */   private final ImmutableList.Builder<WeightedEntry.Wrapper<E>> result = ImmutableList.builder();
/*    */   
/*    */   public Builder<E> add(E $$0) {
/* 44 */     return add($$0, 1);
/*    */   }
/*    */   
/*    */   public Builder<E> add(E $$0, int $$1) {
/* 48 */     this.result.add(WeightedEntry.wrap($$0, $$1));
/* 49 */     return this;
/*    */   }
/*    */   
/*    */   public SimpleWeightedRandomList<E> build() {
/* 53 */     return new SimpleWeightedRandomList<>((List<? extends WeightedEntry.Wrapper<E>>)this.result.build());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\random\SimpleWeightedRandomList$Builder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */