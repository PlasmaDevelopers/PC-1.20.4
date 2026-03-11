/*    */ package net.minecraft.world.item.enchantment;
/*    */ 
/*    */ import net.minecraft.world.effect.MobEffectInstance;
/*    */ import net.minecraft.world.effect.MobEffects;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.EquipmentSlot;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.MobType;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ 
/*    */ 
/*    */ public class DamageEnchantment
/*    */   extends Enchantment
/*    */ {
/*    */   public static final int ALL = 0;
/*    */   public static final int UNDEAD = 1;
/*    */   public static final int ARTHROPODS = 2;
/* 18 */   private static final String[] NAMES = new String[] { "all", "undead", "arthropods" };
/*    */ 
/*    */ 
/*    */   
/* 22 */   private static final int[] MIN_COST = new int[] { 1, 5, 5 };
/*    */ 
/*    */ 
/*    */   
/* 26 */   private static final int[] LEVEL_COST = new int[] { 11, 8, 8 };
/*    */ 
/*    */ 
/*    */   
/* 30 */   private static final int[] LEVEL_COST_SPAN = new int[] { 20, 20, 20 };
/*    */ 
/*    */   
/*    */   public final int type;
/*    */ 
/*    */   
/*    */   public DamageEnchantment(Enchantment.Rarity $$0, int $$1, EquipmentSlot... $$2) {
/* 37 */     super($$0, EnchantmentCategory.WEAPON, $$2);
/* 38 */     this.type = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMinCost(int $$0) {
/* 43 */     return MIN_COST[this.type] + ($$0 - 1) * LEVEL_COST[this.type];
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxCost(int $$0) {
/* 48 */     return getMinCost($$0) + LEVEL_COST_SPAN[this.type];
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxLevel() {
/* 53 */     return 5;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getDamageBonus(int $$0, MobType $$1) {
/* 58 */     if (this.type == 0) {
/* 59 */       return 1.0F + Math.max(0, $$0 - 1) * 0.5F;
/*    */     }
/* 61 */     if (this.type == 1 && $$1 == MobType.UNDEAD) {
/* 62 */       return $$0 * 2.5F;
/*    */     }
/* 64 */     if (this.type == 2 && $$1 == MobType.ARTHROPOD) {
/* 65 */       return $$0 * 2.5F;
/*    */     }
/* 67 */     return 0.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean checkCompatibility(Enchantment $$0) {
/* 72 */     return !($$0 instanceof DamageEnchantment);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canEnchant(ItemStack $$0) {
/* 77 */     if ($$0.getItem() instanceof net.minecraft.world.item.AxeItem) {
/* 78 */       return true;
/*    */     }
/* 80 */     return super.canEnchant($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void doPostAttack(LivingEntity $$0, Entity $$1, int $$2) {
/* 85 */     if ($$1 instanceof LivingEntity) { LivingEntity $$3 = (LivingEntity)$$1;
/* 86 */       if (this.type == 2 && $$2 > 0 && $$3.getMobType() == MobType.ARTHROPOD) {
/* 87 */         int $$4 = 20 + $$0.getRandom().nextInt(10 * $$2);
/* 88 */         $$3.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, $$4, 3));
/*    */       }  }
/*    */   
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\enchantment\DamageEnchantment.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */