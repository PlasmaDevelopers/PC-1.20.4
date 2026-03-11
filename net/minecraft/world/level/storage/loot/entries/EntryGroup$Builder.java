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
/*    */ public class Builder
/*    */   extends LootPoolEntryContainer.Builder<EntryGroup.Builder>
/*    */ {
/* 45 */   private final ImmutableList.Builder<LootPoolEntryContainer> entries = ImmutableList.builder();
/*    */   
/*    */   public Builder(LootPoolEntryContainer.Builder<?>... $$0) {
/* 48 */     for (LootPoolEntryContainer.Builder<?> $$1 : $$0) {
/* 49 */       this.entries.add($$1.build());
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   protected Builder getThis() {
/* 55 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public Builder append(LootPoolEntryContainer.Builder<?> $$0) {
/* 60 */     this.entries.add($$0.build());
/* 61 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public LootPoolEntryContainer build() {
/* 66 */     return new EntryGroup((List<LootPoolEntryContainer>)this.entries.build(), getConditions());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\entries\EntryGroup$Builder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */