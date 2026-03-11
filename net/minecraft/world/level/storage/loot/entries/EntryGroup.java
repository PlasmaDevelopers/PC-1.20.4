/*    */ package net.minecraft.world.level.storage.loot.entries;
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import com.mojang.serialization.Codec;
/*    */ import java.util.List;
/*    */ import java.util.function.Consumer;
/*    */ import net.minecraft.world.level.storage.loot.LootContext;
/*    */ import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
/*    */ 
/*    */ public class EntryGroup extends CompositeEntryBase {
/* 10 */   public static final Codec<EntryGroup> CODEC = createCodec(EntryGroup::new);
/*    */   
/*    */   EntryGroup(List<LootPoolEntryContainer> $$0, List<LootItemCondition> $$1) {
/* 13 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public LootPoolEntryType getType() {
/* 18 */     return LootPoolEntries.GROUP;
/*    */   }
/*    */   protected ComposableEntryContainer compose(List<? extends ComposableEntryContainer> $$0) {
/*    */     ComposableEntryContainer $$1;
/*    */     ComposableEntryContainer $$2;
/* 23 */     switch ($$0.size()) { case 0: 
/*    */       case 1:
/*    */       
/*    */       case 2:
/* 27 */         $$1 = $$0.get(0);
/* 28 */         $$2 = $$0.get(1); }
/*    */     
/*    */     return ($$1, $$2) -> {
/*    */         for (ComposableEntryContainer $$3 : $$0) {
/*    */           $$3.expand($$1, $$2);
/*    */         }
/*    */         return true;
/*    */       };
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static class Builder
/*    */     extends LootPoolEntryContainer.Builder<Builder>
/*    */   {
/* 45 */     private final ImmutableList.Builder<LootPoolEntryContainer> entries = ImmutableList.builder();
/*    */     
/*    */     public Builder(LootPoolEntryContainer.Builder<?>... $$0) {
/* 48 */       for (LootPoolEntryContainer.Builder<?> $$1 : $$0) {
/* 49 */         this.entries.add($$1.build());
/*    */       }
/*    */     }
/*    */ 
/*    */     
/*    */     protected Builder getThis() {
/* 55 */       return this;
/*    */     }
/*    */ 
/*    */     
/*    */     public Builder append(LootPoolEntryContainer.Builder<?> $$0) {
/* 60 */       this.entries.add($$0.build());
/* 61 */       return this;
/*    */     }
/*    */ 
/*    */     
/*    */     public LootPoolEntryContainer build() {
/* 66 */       return new EntryGroup((List<LootPoolEntryContainer>)this.entries.build(), getConditions());
/*    */     }
/*    */   }
/*    */   
/*    */   public static Builder list(LootPoolEntryContainer.Builder<?>... $$0) {
/* 71 */     return new Builder($$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\entries\EntryGroup.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */