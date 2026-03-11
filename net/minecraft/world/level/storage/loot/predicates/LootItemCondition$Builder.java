/*    */ package net.minecraft.world.level.storage.loot.predicates;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @FunctionalInterface
/*    */ public interface Builder
/*    */ {
/*    */   LootItemCondition build();
/*    */   
/*    */   default Builder invert() {
/* 16 */     return InvertedLootItemCondition.invert(this);
/*    */   }
/*    */   
/*    */   default AnyOfCondition.Builder or(Builder $$0) {
/* 20 */     return AnyOfCondition.anyOf(new Builder[] { this, $$0 });
/*    */   }
/*    */   
/*    */   default AllOfCondition.Builder and(Builder $$0) {
/* 24 */     return AllOfCondition.allOf(new Builder[] { this, $$0 });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\predicates\LootItemCondition$Builder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */