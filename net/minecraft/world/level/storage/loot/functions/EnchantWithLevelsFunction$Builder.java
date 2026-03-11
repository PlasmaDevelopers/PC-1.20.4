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
/*    */ public class Builder
/*    */   extends LootItemConditionalFunction.Builder<EnchantWithLevelsFunction.Builder>
/*    */ {
/*    */   private final NumberProvider levels;
/*    */   private boolean treasure;
/*    */   
/*    */   public Builder(NumberProvider $$0) {
/* 53 */     this.levels = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   protected Builder getThis() {
/* 58 */     return this;
/*    */   }
/*    */   
/*    */   public Builder allowTreasure() {
/* 62 */     this.treasure = true;
/* 63 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public LootItemFunction build() {
/* 68 */     return new EnchantWithLevelsFunction(getConditions(), this.levels, this.treasure);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\functions\EnchantWithLevelsFunction$Builder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */