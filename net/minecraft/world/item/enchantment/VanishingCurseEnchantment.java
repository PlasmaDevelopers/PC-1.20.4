/*    */ package net.minecraft.world.item.enchantment;
/*    */ 
/*    */ import net.minecraft.world.entity.EquipmentSlot;
/*    */ 
/*    */ public class VanishingCurseEnchantment extends Enchantment {
/*    */   public VanishingCurseEnchantment(Enchantment.Rarity $$0, EquipmentSlot... $$1) {
/*  7 */     super($$0, EnchantmentCategory.VANISHABLE, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMinCost(int $$0) {
/* 12 */     return 25;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxCost(int $$0) {
/* 17 */     return 50;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isTreasureOnly() {
/* 22 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isCurse() {
/* 27 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\enchantment\VanishingCurseEnchantment.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */