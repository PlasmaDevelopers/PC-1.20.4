/*    */ package net.minecraft.world.item.enchantment;
/*    */ 
/*    */ import net.minecraft.world.entity.EquipmentSlot;
/*    */ import net.minecraft.world.entity.MobType;
/*    */ 
/*    */ public class TridentImpalerEnchantment extends Enchantment {
/*    */   public TridentImpalerEnchantment(Enchantment.Rarity $$0, EquipmentSlot... $$1) {
/*  8 */     super($$0, EnchantmentCategory.TRIDENT, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMinCost(int $$0) {
/* 13 */     return 1 + ($$0 - 1) * 8;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxCost(int $$0) {
/* 18 */     return getMinCost($$0) + 20;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxLevel() {
/* 23 */     return 5;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getDamageBonus(int $$0, MobType $$1) {
/* 28 */     if ($$1 == MobType.WATER) {
/* 29 */       return $$0 * 2.5F;
/*    */     }
/* 31 */     return 0.0F;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\enchantment\TridentImpalerEnchantment.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */