/*    */ package net.minecraft.world.item.enchantment;
/*    */ 
/*    */ import net.minecraft.world.entity.EquipmentSlot;
/*    */ 
/*    */ public class LootBonusEnchantment extends Enchantment {
/*    */   protected LootBonusEnchantment(Enchantment.Rarity $$0, EnchantmentCategory $$1, EquipmentSlot... $$2) {
/*  7 */     super($$0, $$1, $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMinCost(int $$0) {
/* 12 */     return 15 + ($$0 - 1) * 9;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxCost(int $$0) {
/* 17 */     return super.getMinCost($$0) + 50;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxLevel() {
/* 22 */     return 3;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean checkCompatibility(Enchantment $$0) {
/* 27 */     return (super.checkCompatibility($$0) && $$0 != Enchantments.SILK_TOUCH);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\enchantment\LootBonusEnchantment.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */