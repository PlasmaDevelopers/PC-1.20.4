/*    */ package net.minecraft.world.item.enchantment;
/*    */ 
/*    */ import net.minecraft.world.entity.EquipmentSlot;
/*    */ 
/*    */ public class QuickChargeEnchantment extends Enchantment {
/*    */   public QuickChargeEnchantment(Enchantment.Rarity $$0, EquipmentSlot... $$1) {
/*  7 */     super($$0, EnchantmentCategory.CROSSBOW, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMinCost(int $$0) {
/* 12 */     return 12 + ($$0 - 1) * 20;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxCost(int $$0) {
/* 17 */     return 50;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxLevel() {
/* 22 */     return 3;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\enchantment\QuickChargeEnchantment.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */