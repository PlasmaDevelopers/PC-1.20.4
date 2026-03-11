/*    */ package net.minecraft.world.level.storage.loot.functions;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.core.HolderSet;
/*    */ import net.minecraft.world.item.enchantment.Enchantment;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Builder
/*    */   extends LootItemConditionalFunction.Builder<EnchantRandomlyFunction.Builder>
/*    */ {
/* 82 */   private final List<Holder<Enchantment>> enchantments = new ArrayList<>();
/*    */ 
/*    */   
/*    */   protected Builder getThis() {
/* 86 */     return this;
/*    */   }
/*    */   
/*    */   public Builder withEnchantment(Enchantment $$0) {
/* 90 */     this.enchantments.add($$0.builtInRegistryHolder());
/* 91 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public LootItemFunction build() {
/* 96 */     return new EnchantRandomlyFunction(getConditions(), this.enchantments.isEmpty() ? Optional.<HolderSet<Enchantment>>empty() : (Optional)Optional.of(HolderSet.direct(this.enchantments)));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\functions\EnchantRandomlyFunction$Builder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */