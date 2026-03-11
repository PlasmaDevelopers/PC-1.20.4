/*     */ package net.minecraft.world.level.storage.loot.entries;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class DummyBuilder
/*     */   extends LootPoolSingletonContainer.Builder<LootPoolSingletonContainer.DummyBuilder>
/*     */ {
/*     */   private final LootPoolSingletonContainer.EntryConstructor constructor;
/*     */   
/*     */   public DummyBuilder(LootPoolSingletonContainer.EntryConstructor $$0) {
/* 119 */     this.constructor = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected DummyBuilder getThis() {
/* 124 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public LootPoolEntryContainer build() {
/* 129 */     return this.constructor.build(this.weight, this.quality, getConditions(), getFunctions());
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\entries\LootPoolSingletonContainer$DummyBuilder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */