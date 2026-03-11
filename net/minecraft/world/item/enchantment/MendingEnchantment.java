/*    */ package net.minecraft.world.item.enchantment;
/*    */ 
/*    */ import net.minecraft.world.entity.EquipmentSlot;
/*    */ 
/*    */ public class MendingEnchantment extends Enchantment {
/*    */   public MendingEnchantment(Enchantment.Rarity $$0, EquipmentSlot... $$1) {
/*  7 */     super($$0, EnchantmentCategory.BREAKABLE, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMinCost(int $$0) {
/* 12 */     return $$0 * 25;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxCost(int $$0) {
/* 17 */     return getMinCost($$0) + 50;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isTreasureOnly() {
/* 22 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\enchantment\MendingEnchantment.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */