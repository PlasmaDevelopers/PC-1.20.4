/*    */ package net.minecraft.world.level.storage.loot.predicates;
/*    */ 
/*    */ import java.util.Optional;
/*    */ import net.minecraft.advancements.critereon.StatePropertiesPredicate;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.world.level.block.Block;
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
/*    */   implements LootItemCondition.Builder
/*    */ {
/*    */   private final Holder<Block> block;
/* 53 */   private Optional<StatePropertiesPredicate> properties = Optional.empty();
/*    */   
/*    */   public Builder(Block $$0) {
/* 56 */     this.block = (Holder<Block>)$$0.builtInRegistryHolder();
/*    */   }
/*    */   
/*    */   public Builder setProperties(StatePropertiesPredicate.Builder $$0) {
/* 60 */     this.properties = $$0.build();
/* 61 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public LootItemCondition build() {
/* 66 */     return new LootItemBlockStatePropertyCondition(this.block, this.properties);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\predicates\LootItemBlockStatePropertyCondition$Builder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */