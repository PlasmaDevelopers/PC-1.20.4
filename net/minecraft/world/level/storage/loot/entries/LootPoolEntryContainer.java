/*    */ package net.minecraft.world.level.storage.loot.entries;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import com.mojang.datafixers.Products;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.List;
/*    */ import java.util.function.Predicate;
/*    */ import net.minecraft.util.ExtraCodecs;
/*    */ import net.minecraft.world.level.storage.loot.LootContext;
/*    */ import net.minecraft.world.level.storage.loot.ValidationContext;
/*    */ import net.minecraft.world.level.storage.loot.predicates.ConditionUserBuilder;
/*    */ import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
/*    */ import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;
/*    */ 
/*    */ public abstract class LootPoolEntryContainer implements ComposableEntryContainer {
/*    */   protected final List<LootItemCondition> conditions;
/*    */   private final Predicate<LootContext> compositeCondition;
/*    */   
/*    */   protected LootPoolEntryContainer(List<LootItemCondition> $$0) {
/* 21 */     this.conditions = $$0;
/* 22 */     this.compositeCondition = LootItemConditions.andConditions($$0);
/*    */   }
/*    */   
/*    */   protected static <T extends LootPoolEntryContainer> Products.P1<RecordCodecBuilder.Mu<T>, List<LootItemCondition>> commonFields(RecordCodecBuilder.Instance<T> $$0) {
/* 26 */     return $$0.group(
/* 27 */         (App)ExtraCodecs.strictOptionalField(LootItemConditions.CODEC.listOf(), "conditions", List.of()).forGetter($$0 -> $$0.conditions));
/*    */   }
/*    */ 
/*    */   
/*    */   public void validate(ValidationContext $$0) {
/* 32 */     for (int $$1 = 0; $$1 < this.conditions.size(); $$1++) {
/* 33 */       ((LootItemCondition)this.conditions.get($$1)).validate($$0.forChild(".condition[" + $$1 + "]"));
/*    */     }
/*    */   }
/*    */   
/*    */   protected final boolean canRun(LootContext $$0) {
/* 38 */     return this.compositeCondition.test($$0);
/*    */   }
/*    */   
/*    */   public abstract LootPoolEntryType getType();
/*    */   
/*    */   public static abstract class Builder<T extends Builder<T>> implements ConditionUserBuilder<T> {
/* 44 */     private final ImmutableList.Builder<LootItemCondition> conditions = ImmutableList.builder();
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public T when(LootItemCondition.Builder $$0) {
/* 50 */       this.conditions.add($$0.build());
/* 51 */       return getThis();
/*    */     }
/*    */ 
/*    */     
/*    */     public final T unwrap() {
/* 56 */       return getThis();
/*    */     }
/*    */     
/*    */     protected List<LootItemCondition> getConditions() {
/* 60 */       return (List<LootItemCondition>)this.conditions.build();
/*    */     }
/*    */     
/*    */     public AlternativesEntry.Builder otherwise(Builder<?> $$0) {
/* 64 */       return new AlternativesEntry.Builder((Builder<?>[])new Builder[] { this, $$0 });
/*    */     }
/*    */     
/*    */     public EntryGroup.Builder append(Builder<?> $$0) {
/* 68 */       return new EntryGroup.Builder((Builder<?>[])new Builder[] { this, $$0 });
/*    */     }
/*    */     
/*    */     public SequentialEntry.Builder then(Builder<?> $$0) {
/* 72 */       return new SequentialEntry.Builder((Builder<?>[])new Builder[] { this, $$0 });
/*    */     }
/*    */     
/*    */     protected abstract T getThis();
/*    */     
/*    */     public abstract LootPoolEntryContainer build();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\entries\LootPoolEntryContainer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */