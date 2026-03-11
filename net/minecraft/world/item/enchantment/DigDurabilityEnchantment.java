/*    */ package net.minecraft.world.item.enchantment;
/*    */ 
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.entity.EquipmentSlot;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ 
/*    */ public class DigDurabilityEnchantment
/*    */   extends Enchantment {
/*    */   protected DigDurabilityEnchantment(Enchantment.Rarity $$0, EquipmentSlot... $$1) {
/* 10 */     super($$0, EnchantmentCategory.BREAKABLE, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMinCost(int $$0) {
/* 15 */     return 5 + ($$0 - 1) * 8;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxCost(int $$0) {
/* 20 */     return super.getMinCost($$0) + 50;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxLevel() {
/* 25 */     return 3;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canEnchant(ItemStack $$0) {
/* 30 */     if ($$0.isDamageableItem()) {
/* 31 */       return true;
/*    */     }
/* 33 */     return super.canEnchant($$0);
/*    */   }
/*    */   
/*    */   public static boolean shouldIgnoreDurabilityDrop(ItemStack $$0, int $$1, RandomSource $$2) {
/* 37 */     if ($$0.getItem() instanceof net.minecraft.world.item.ArmorItem && $$2.nextFloat() < 0.6F) {
/* 38 */       return false;
/*    */     }
/* 40 */     return ($$2.nextInt($$1 + 1) > 0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\enchantment\DigDurabilityEnchantment.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */