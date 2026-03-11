/*    */ package net.minecraft.world.item.enchantment;
/*    */ 
/*    */ import java.util.Map;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.EquipmentSlot;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ 
/*    */ public class ThornsEnchantment
/*    */   extends Enchantment
/*    */ {
/*    */   private static final float CHANCE_PER_LEVEL = 0.15F;
/*    */   
/*    */   public ThornsEnchantment(Enchantment.Rarity $$0, EquipmentSlot... $$1) {
/* 16 */     super($$0, EnchantmentCategory.ARMOR_CHEST, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMinCost(int $$0) {
/* 21 */     return 10 + 20 * ($$0 - 1);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxCost(int $$0) {
/* 26 */     return super.getMinCost($$0) + 50;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxLevel() {
/* 31 */     return 3;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canEnchant(ItemStack $$0) {
/* 36 */     if ($$0.getItem() instanceof net.minecraft.world.item.ArmorItem) {
/* 37 */       return true;
/*    */     }
/* 39 */     return super.canEnchant($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void doPostHurt(LivingEntity $$0, Entity $$1, int $$2) {
/* 44 */     RandomSource $$3 = $$0.getRandom();
/* 45 */     Map.Entry<EquipmentSlot, ItemStack> $$4 = EnchantmentHelper.getRandomItemWith(Enchantments.THORNS, $$0);
/*    */     
/* 47 */     if (shouldHit($$2, $$3)) {
/* 48 */       if ($$1 != null) {
/* 49 */         $$1.hurt($$0.damageSources().thorns((Entity)$$0), getDamage($$2, $$3));
/*    */       }
/*    */       
/* 52 */       if ($$4 != null) {
/* 53 */         ((ItemStack)$$4.getValue()).hurtAndBreak(2, $$0, $$1 -> $$1.broadcastBreakEvent((EquipmentSlot)$$0.getKey()));
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public static boolean shouldHit(int $$0, RandomSource $$1) {
/* 59 */     if ($$0 <= 0) {
/* 60 */       return false;
/*    */     }
/* 62 */     return ($$1.nextFloat() < 0.15F * $$0);
/*    */   }
/*    */   
/*    */   public static int getDamage(int $$0, RandomSource $$1) {
/* 66 */     if ($$0 > 10) {
/* 67 */       return $$0 - 10;
/*    */     }
/* 69 */     return 1 + $$1.nextInt(4);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\enchantment\ThornsEnchantment.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */