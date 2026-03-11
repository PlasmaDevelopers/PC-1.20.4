/*    */ package net.minecraft.world.item.enchantment;
/*    */ 
/*    */ import net.minecraft.world.entity.EquipmentSlot;
/*    */ 
/*    */ public class SweepingEdgeEnchantment extends Enchantment {
/*    */   public SweepingEdgeEnchantment(Enchantment.Rarity $$0, EquipmentSlot... $$1) {
/*  7 */     super($$0, EnchantmentCategory.WEAPON, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMinCost(int $$0) {
/* 12 */     return 5 + ($$0 - 1) * 9;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxCost(int $$0) {
/* 17 */     return getMinCost($$0) + 15;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxLevel() {
/* 22 */     return 3;
/*    */   }
/*    */   
/*    */   public static float getSweepingDamageRatio(int $$0) {
/* 26 */     return 1.0F - 1.0F / ($$0 + 1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\enchantment\SweepingEdgeEnchantment.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */