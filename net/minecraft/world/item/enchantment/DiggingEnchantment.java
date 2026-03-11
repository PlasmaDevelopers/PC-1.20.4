/*    */ package net.minecraft.world.item.enchantment;
/*    */ 
/*    */ import net.minecraft.world.entity.EquipmentSlot;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.Items;
/*    */ 
/*    */ public class DiggingEnchantment extends Enchantment {
/*    */   protected DiggingEnchantment(Enchantment.Rarity $$0, EquipmentSlot... $$1) {
/*  9 */     super($$0, EnchantmentCategory.DIGGER, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMinCost(int $$0) {
/* 14 */     return 1 + 10 * ($$0 - 1);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxCost(int $$0) {
/* 19 */     return super.getMinCost($$0) + 50;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxLevel() {
/* 24 */     return 5;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canEnchant(ItemStack $$0) {
/* 29 */     if ($$0.is(Items.SHEARS)) {
/* 30 */       return true;
/*    */     }
/* 32 */     return super.canEnchant($$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\enchantment\DiggingEnchantment.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */