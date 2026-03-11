/*    */ package net.minecraft.world.level.storage.loot.functions;
/*    */ 
/*    */ import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
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
/*    */   extends LootItemConditionalFunction.Builder<LootingEnchantFunction.Builder>
/*    */ {
/*    */   private final NumberProvider count;
/* 75 */   private int limit = 0;
/*    */   
/*    */   public Builder(NumberProvider $$0) {
/* 78 */     this.count = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   protected Builder getThis() {
/* 83 */     return this;
/*    */   }
/*    */   
/*    */   public Builder setLimit(int $$0) {
/* 87 */     this.limit = $$0;
/* 88 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public LootItemFunction build() {
/* 93 */     return new LootingEnchantFunction(getConditions(), this.count, this.limit);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\functions\LootingEnchantFunction$Builder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */