/*    */ package net.minecraft.world.level.storage.loot.entries;
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import com.mojang.serialization.Codec;
/*    */ import java.util.Collection;
/*    */ import java.util.List;
/*    */ import java.util.function.Consumer;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.world.level.storage.loot.LootContext;
/*    */ import net.minecraft.world.level.storage.loot.ValidationContext;
/*    */ import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
/*    */ 
/*    */ public class AlternativesEntry extends CompositeEntryBase {
/* 13 */   public static final Codec<AlternativesEntry> CODEC = createCodec(AlternativesEntry::new);
/*    */   
/*    */   AlternativesEntry(List<LootPoolEntryContainer> $$0, List<LootItemCondition> $$1) {
/* 16 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public LootPoolEntryType getType() {
/* 21 */     return LootPoolEntries.ALTERNATIVES;
/*    */   }
/*    */ 
/*    */   
/*    */   protected ComposableEntryContainer compose(List<? extends ComposableEntryContainer> $$0) {
/* 26 */     switch ($$0.size()) { case 0: case 1: case 2:  }  return ($$1, $$2) -> {
/*    */         for (ComposableEntryContainer $$3 : $$0) {
/*    */           if ($$3.expand($$1, $$2)) {
/*    */             return true;
/*    */           }
/*    */         } 
/*    */         return false;
/*    */       };
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void validate(ValidationContext $$0) {
/* 43 */     super.validate($$0);
/*    */     
/* 45 */     for (int $$1 = 0; $$1 < this.children.size() - 1; $$1++) {
/* 46 */       if (((LootPoolEntryContainer)this.children.get($$1)).conditions.isEmpty())
/* 47 */         $$0.reportProblem("Unreachable entry!"); 
/*    */     } 
/*    */   }
/*    */   
/*    */   public static class Builder
/*    */     extends LootPoolEntryContainer.Builder<Builder> {
/* 53 */     private final ImmutableList.Builder<LootPoolEntryContainer> entries = ImmutableList.builder();
/*    */     
/*    */     public Builder(LootPoolEntryContainer.Builder<?>... $$0) {
/* 56 */       for (LootPoolEntryContainer.Builder<?> $$1 : $$0) {
/* 57 */         this.entries.add($$1.build());
/*    */       }
/*    */     }
/*    */ 
/*    */     
/*    */     protected Builder getThis() {
/* 63 */       return this;
/*    */     }
/*    */ 
/*    */     
/*    */     public Builder otherwise(LootPoolEntryContainer.Builder<?> $$0) {
/* 68 */       this.entries.add($$0.build());
/* 69 */       return this;
/*    */     }
/*    */ 
/*    */     
/*    */     public LootPoolEntryContainer build() {
/* 74 */       return new AlternativesEntry((List<LootPoolEntryContainer>)this.entries.build(), getConditions());
/*    */     }
/*    */   }
/*    */   
/*    */   public static Builder alternatives(LootPoolEntryContainer.Builder<?>... $$0) {
/* 79 */     return new Builder($$0);
/*    */   }
/*    */   
/*    */   public static <E> Builder alternatives(Collection<E> $$0, Function<E, LootPoolEntryContainer.Builder<?>> $$1) {
/* 83 */     Objects.requireNonNull($$1); return new Builder((LootPoolEntryContainer.Builder<?>[])$$0.stream().map($$1::apply).toArray($$0 -> new LootPoolEntryContainer.Builder[$$0]));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\entries\AlternativesEntry.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */