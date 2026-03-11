/*    */ package net.minecraft.world.level.storage.loot.entries;
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
/*    */ public abstract class Builder<T extends LootPoolEntryContainer.Builder<T>>
/*    */   implements ConditionUserBuilder<T>
/*    */ {
/* 44 */   private final ImmutableList.Builder<LootItemCondition> conditions = ImmutableList.builder();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public T when(LootItemCondition.Builder $$0) {
/* 50 */     this.conditions.add($$0.build());
/* 51 */     return getThis();
/*    */   }
/*    */ 
/*    */   
/*    */   public final T unwrap() {
/* 56 */     return getThis();
/*    */   }
/*    */   
/*    */   protected List<LootItemCondition> getConditions() {
/* 60 */     return (List<LootItemCondition>)this.conditions.build();
/*    */   }
/*    */   
/*    */   public AlternativesEntry.Builder otherwise(Builder<?> $$0) {
/* 64 */     return new AlternativesEntry.Builder((Builder<?>[])new Builder[] { this, $$0 });
/*    */   }
/*    */   
/*    */   public EntryGroup.Builder append(Builder<?> $$0) {
/* 68 */     return new EntryGroup.Builder((Builder<?>[])new Builder[] { this, $$0 });
/*    */   }
/*    */   
/*    */   public SequentialEntry.Builder then(Builder<?> $$0) {
/* 72 */     return new SequentialEntry.Builder((Builder<?>[])new Builder[] { this, $$0 });
/*    */   }
/*    */   
/*    */   protected abstract T getThis();
/*    */   
/*    */   public abstract LootPoolEntryContainer build();
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\entries\LootPoolEntryContainer$Builder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */