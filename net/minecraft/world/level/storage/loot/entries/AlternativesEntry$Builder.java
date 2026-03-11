/*    */ package net.minecraft.world.level.storage.loot.entries;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Builder
/*    */   extends LootPoolEntryContainer.Builder<AlternativesEntry.Builder>
/*    */ {
/* 53 */   private final ImmutableList.Builder<LootPoolEntryContainer> entries = ImmutableList.builder();
/*    */   
/*    */   public Builder(LootPoolEntryContainer.Builder<?>... $$0) {
/* 56 */     for (LootPoolEntryContainer.Builder<?> $$1 : $$0) {
/* 57 */       this.entries.add($$1.build());
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   protected Builder getThis() {
/* 63 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public Builder otherwise(LootPoolEntryContainer.Builder<?> $$0) {
/* 68 */     this.entries.add($$0.build());
/* 69 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public LootPoolEntryContainer build() {
/* 74 */     return new AlternativesEntry((List<LootPoolEntryContainer>)this.entries.build(), getConditions());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\entries\AlternativesEntry$Builder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */