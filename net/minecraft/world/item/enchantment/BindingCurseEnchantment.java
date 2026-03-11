/*    */ package net.minecraft.world.item.enchantment;
/*    */ 
/*    */ import net.minecraft.world.entity.EquipmentSlot;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.Items;
/*    */ 
/*    */ public class BindingCurseEnchantment extends Enchantment {
/*    */   public BindingCurseEnchantment(Enchantment.Rarity $$0, EquipmentSlot... $$1) {
/*  9 */     super($$0, EnchantmentCategory.WEARABLE, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMinCost(int $$0) {
/* 14 */     return 25;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxCost(int $$0) {
/* 19 */     return 50;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isTreasureOnly() {
/* 24 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isCurse() {
/* 29 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canEnchant(ItemStack $$0) {
/* 34 */     return (!$$0.is(Items.SHIELD) && super.canEnchant($$0));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\enchantment\BindingCurseEnchantment.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */