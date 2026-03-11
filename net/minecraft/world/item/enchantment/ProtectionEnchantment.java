/*     */ package net.minecraft.world.item.enchantment;
/*     */ import net.minecraft.tags.DamageTypeTags;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ 
/*     */ public class ProtectionEnchantment extends Enchantment {
/*     */   public final Type type;
/*     */   
/*     */   public enum Type {
/*  11 */     ALL(1, 11),
/*  12 */     FIRE(10, 8),
/*  13 */     FALL(5, 6),
/*  14 */     EXPLOSION(5, 8),
/*  15 */     PROJECTILE(3, 6);
/*     */     
/*     */     private final int minCost;
/*     */     private final int levelCost;
/*     */     
/*     */     Type(int $$0, int $$1) {
/*  21 */       this.minCost = $$0;
/*  22 */       this.levelCost = $$1;
/*     */     }
/*     */     
/*     */     public int getMinCost() {
/*  26 */       return this.minCost;
/*     */     }
/*     */     
/*     */     public int getLevelCost() {
/*  30 */       return this.levelCost;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ProtectionEnchantment(Enchantment.Rarity $$0, Type $$1, EquipmentSlot... $$2) {
/*  37 */     super($$0, ($$1 == Type.FALL) ? EnchantmentCategory.ARMOR_FEET : EnchantmentCategory.ARMOR, $$2);
/*  38 */     this.type = $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMinCost(int $$0) {
/*  43 */     return this.type.getMinCost() + ($$0 - 1) * this.type.getLevelCost();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxCost(int $$0) {
/*  48 */     return getMinCost($$0) + this.type.getLevelCost();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxLevel() {
/*  53 */     return 4;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDamageProtection(int $$0, DamageSource $$1) {
/*  58 */     if ($$1.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
/*  59 */       return 0;
/*     */     }
/*     */     
/*  62 */     if (this.type == Type.ALL) {
/*  63 */       return $$0;
/*     */     }
/*  65 */     if (this.type == Type.FIRE && $$1.is(DamageTypeTags.IS_FIRE)) {
/*  66 */       return $$0 * 2;
/*     */     }
/*  68 */     if (this.type == Type.FALL && $$1.is(DamageTypeTags.IS_FALL)) {
/*  69 */       return $$0 * 3;
/*     */     }
/*  71 */     if (this.type == Type.EXPLOSION && $$1.is(DamageTypeTags.IS_EXPLOSION)) {
/*  72 */       return $$0 * 2;
/*     */     }
/*  74 */     if (this.type == Type.PROJECTILE && $$1.is(DamageTypeTags.IS_PROJECTILE)) {
/*  75 */       return $$0 * 2;
/*     */     }
/*  77 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean checkCompatibility(Enchantment $$0) {
/*  82 */     if ($$0 instanceof ProtectionEnchantment) { ProtectionEnchantment $$1 = (ProtectionEnchantment)$$0;
/*  83 */       if (this.type == $$1.type) {
/*  84 */         return false;
/*     */       }
/*  86 */       return (this.type == Type.FALL || $$1.type == Type.FALL); }
/*     */     
/*  88 */     return super.checkCompatibility($$0);
/*     */   }
/*     */   
/*     */   public static int getFireAfterDampener(LivingEntity $$0, int $$1) {
/*  92 */     int $$2 = EnchantmentHelper.getEnchantmentLevel(Enchantments.FIRE_PROTECTION, $$0);
/*     */     
/*  94 */     if ($$2 > 0) {
/*  95 */       $$1 -= Mth.floor($$1 * $$2 * 0.15F);
/*     */     }
/*     */     
/*  98 */     return $$1;
/*     */   }
/*     */   
/*     */   public static double getExplosionKnockbackAfterDampener(LivingEntity $$0, double $$1) {
/* 102 */     int $$2 = EnchantmentHelper.getEnchantmentLevel(Enchantments.BLAST_PROTECTION, $$0);
/*     */     
/* 104 */     if ($$2 > 0) {
/* 105 */       $$1 *= Mth.clamp(1.0D - $$2 * 0.15D, 0.0D, 1.0D);
/*     */     }
/*     */     
/* 108 */     return $$1;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\enchantment\ProtectionEnchantment.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */