/*    */ package net.minecraft.world.item.enchantment;
/*    */ 
/*    */ import net.minecraft.world.entity.EquipmentSlot;
/*    */ 
/*    */ public class SwiftSneakEnchantment extends Enchantment {
/*    */   public SwiftSneakEnchantment(Enchantment.Rarity $$0, EquipmentSlot... $$1) {
/*  7 */     super($$0, EnchantmentCategory.ARMOR_LEGS, $$1);
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
/*    */ 
/*    */   
/*    */   public boolean isTradeable() {
/* 27 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isDiscoverable() {
/* 32 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxLevel() {
/* 37 */     return 3;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\enchantment\SwiftSneakEnchantment.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */