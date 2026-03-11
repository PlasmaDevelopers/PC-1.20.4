/*    */ package net.minecraft.world.level.storage.loot.predicates;
/*    */ 
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
/*    */ public class Builder
/*    */   extends CompositeLootItemCondition.Builder
/*    */ {
/*    */   public Builder(LootItemCondition.Builder... $$0) {
/* 26 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public Builder and(LootItemCondition.Builder $$0) {
/* 31 */     addTerm($$0);
/* 32 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   protected LootItemCondition create(List<LootItemCondition> $$0) {
/* 37 */     return new AllOfCondition($$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\predicates\AllOfCondition$Builder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */