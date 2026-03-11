/*    */ package net.minecraft.world.level.storage.loot.entries;
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import com.mojang.serialization.Codec;
/*    */ import java.util.List;
/*    */ import java.util.function.Consumer;
/*    */ import net.minecraft.world.level.storage.loot.LootContext;
/*    */ import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
/*    */ 
/*    */ public class SequentialEntry extends CompositeEntryBase {
/* 10 */   public static final Codec<SequentialEntry> CODEC = createCodec(SequentialEntry::new);
/*    */   
/*    */   SequentialEntry(List<LootPoolEntryContainer> $$0, List<LootItemCondition> $$1) {
/* 13 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public LootPoolEntryType getType() {
/* 18 */     return LootPoolEntries.SEQUENCE;
/*    */   }
/*    */ 
/*    */   
/*    */   protected ComposableEntryContainer compose(List<? extends ComposableEntryContainer> $$0) {
/* 23 */     switch ($$0.size()) { case 0: case 1: case 2:  }  return ($$1, $$2) -> {
/*    */         for (ComposableEntryContainer $$3 : $$0) {
/*    */           if (!$$3.expand($$1, $$2)) {
/*    */             return false;
/*    */           }
/*    */         } 
/*    */         return true;
/*    */       };
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static class Builder
/*    */     extends LootPoolEntryContainer.Builder<Builder>
/*    */   {
/* 39 */     private final ImmutableList.Builder<LootPoolEntryContainer> entries = ImmutableList.builder();
/*    */     
/*    */     public Builder(LootPoolEntryContainer.Builder<?>... $$0) {
/* 42 */       for (LootPoolEntryContainer.Builder<?> $$1 : $$0) {
/* 43 */         this.entries.add($$1.build());
/*    */       }
/*    */     }
/*    */ 
/*    */     
/*    */     protected Builder getThis() {
/* 49 */       return this;
/*    */     }
/*    */ 
/*    */     
/*    */     public Builder then(LootPoolEntryContainer.Builder<?> $$0) {
/* 54 */       this.entries.add($$0.build());
/* 55 */       return this;
/*    */     }
/*    */ 
/*    */     
/*    */     public LootPoolEntryContainer build() {
/* 60 */       return new SequentialEntry((List<LootPoolEntryContainer>)this.entries.build(), getConditions());
/*    */     }
/*    */   }
/*    */   
/*    */   public static Builder sequential(LootPoolEntryContainer.Builder<?>... $$0) {
/* 65 */     return new Builder($$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\entries\SequentialEntry.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */