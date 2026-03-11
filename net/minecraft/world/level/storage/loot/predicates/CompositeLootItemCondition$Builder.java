/*    */ package net.minecraft.world.level.storage.loot.predicates;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class Builder
/*    */   implements LootItemCondition.Builder
/*    */ {
/* 48 */   private final ImmutableList.Builder<LootItemCondition> terms = ImmutableList.builder();
/*    */   
/*    */   protected Builder(LootItemCondition.Builder... $$0) {
/* 51 */     for (LootItemCondition.Builder $$1 : $$0) {
/* 52 */       this.terms.add($$1.build());
/*    */     }
/*    */   }
/*    */   
/*    */   public void addTerm(LootItemCondition.Builder $$0) {
/* 57 */     this.terms.add($$0.build());
/*    */   }
/*    */ 
/*    */   
/*    */   public LootItemCondition build() {
/* 62 */     return create((List<LootItemCondition>)this.terms.build());
/*    */   }
/*    */   
/*    */   protected abstract LootItemCondition create(List<LootItemCondition> paramList);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\predicates\CompositeLootItemCondition$Builder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */