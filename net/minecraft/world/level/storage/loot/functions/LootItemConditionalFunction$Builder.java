/*    */ package net.minecraft.world.level.storage.loot.functions;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.List;
/*    */ import net.minecraft.world.level.storage.loot.predicates.ConditionUserBuilder;
/*    */ import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
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
/*    */ public abstract class Builder<T extends LootItemConditionalFunction.Builder<T>>
/*    */   implements LootItemFunction.Builder, ConditionUserBuilder<T>
/*    */ {
/* 50 */   private final ImmutableList.Builder<LootItemCondition> conditions = ImmutableList.builder();
/*    */ 
/*    */   
/*    */   public T when(LootItemCondition.Builder $$0) {
/* 54 */     this.conditions.add($$0.build());
/* 55 */     return getThis();
/*    */   }
/*    */ 
/*    */   
/*    */   public final T unwrap() {
/* 60 */     return getThis();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected List<LootItemCondition> getConditions() {
/* 66 */     return (List<LootItemCondition>)this.conditions.build();
/*    */   }
/*    */   
/*    */   protected abstract T getThis();
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\functions\LootItemConditionalFunction$Builder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */