/*     */ package net.minecraft.world.item;
/*     */ 
/*     */ import java.util.function.Predicate;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.stats.Stats;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResultHolder;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.entity.projectile.AbstractArrow;
/*     */ import net.minecraft.world.item.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.world.item.enchantment.Enchantments;
/*     */ import net.minecraft.world.level.Level;
/*     */ 
/*     */ public class BowItem
/*     */   extends ProjectileWeaponItem implements Vanishable {
/*     */   public static final int MAX_DRAW_DURATION = 20;
/*     */   public static final int DEFAULT_RANGE = 15;
/*     */   
/*     */   public BowItem(Item.Properties $$0) {
/*  23 */     super($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void releaseUsing(ItemStack $$0, Level $$1, LivingEntity $$2, int $$3) {
/*  28 */     if (!($$2 instanceof Player)) {
/*     */       return;
/*     */     }
/*     */     
/*  32 */     Player $$4 = (Player)$$2;
/*  33 */     boolean $$5 = (($$4.getAbilities()).instabuild || EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, $$0) > 0);
/*  34 */     ItemStack $$6 = $$4.getProjectile($$0);
/*     */     
/*  36 */     if ($$6.isEmpty() && !$$5) {
/*     */       return;
/*     */     }
/*     */     
/*  40 */     if ($$6.isEmpty()) {
/*  41 */       $$6 = new ItemStack(Items.ARROW);
/*     */     }
/*     */     
/*  44 */     int $$7 = getUseDuration($$0) - $$3;
/*  45 */     float $$8 = getPowerForTime($$7);
/*  46 */     if ($$8 < 0.1D) {
/*     */       return;
/*     */     }
/*     */     
/*  50 */     boolean $$9 = ($$5 && $$6.is(Items.ARROW));
/*  51 */     if (!$$1.isClientSide) {
/*  52 */       ArrowItem $$10 = ($$6.getItem() instanceof ArrowItem) ? (ArrowItem)$$6.getItem() : (ArrowItem)Items.ARROW;
/*     */       
/*  54 */       AbstractArrow $$11 = $$10.createArrow($$1, $$6, (LivingEntity)$$4);
/*  55 */       $$11.shootFromRotation((Entity)$$4, $$4.getXRot(), $$4.getYRot(), 0.0F, $$8 * 3.0F, 1.0F);
/*  56 */       if ($$8 == 1.0F) {
/*  57 */         $$11.setCritArrow(true);
/*     */       }
/*  59 */       int $$12 = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, $$0);
/*  60 */       if ($$12 > 0) {
/*  61 */         $$11.setBaseDamage($$11.getBaseDamage() + $$12 * 0.5D + 0.5D);
/*     */       }
/*  63 */       int $$13 = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, $$0);
/*  64 */       if ($$13 > 0) {
/*  65 */         $$11.setKnockback($$13);
/*     */       }
/*  67 */       if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, $$0) > 0) {
/*  68 */         $$11.setSecondsOnFire(100);
/*     */       }
/*  70 */       $$0.hurtAndBreak(1, $$4, $$1 -> $$1.broadcastBreakEvent($$0.getUsedItemHand()));
/*     */       
/*  72 */       if ($$9 || (($$4.getAbilities()).instabuild && ($$6.is(Items.SPECTRAL_ARROW) || $$6.is(Items.TIPPED_ARROW)))) {
/*  73 */         $$11.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
/*     */       }
/*     */       
/*  76 */       $$1.addFreshEntity((Entity)$$11);
/*     */     } 
/*     */     
/*  79 */     $$1.playSound(null, $$4.getX(), $$4.getY(), $$4.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / ($$1.getRandom().nextFloat() * 0.4F + 1.2F) + $$8 * 0.5F);
/*  80 */     if (!$$9 && !($$4.getAbilities()).instabuild) {
/*  81 */       $$6.shrink(1);
/*  82 */       if ($$6.isEmpty())
/*     */       {
/*  84 */         $$4.getInventory().removeItem($$6);
/*     */       }
/*     */     } 
/*  87 */     $$4.awardStat(Stats.ITEM_USED.get(this));
/*     */   }
/*     */   
/*     */   public static float getPowerForTime(int $$0) {
/*  91 */     float $$1 = $$0 / 20.0F;
/*  92 */     $$1 = ($$1 * $$1 + $$1 * 2.0F) / 3.0F;
/*  93 */     if ($$1 > 1.0F) {
/*  94 */       $$1 = 1.0F;
/*     */     }
/*  96 */     return $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getUseDuration(ItemStack $$0) {
/* 101 */     return 72000;
/*     */   }
/*     */ 
/*     */   
/*     */   public UseAnim getUseAnimation(ItemStack $$0) {
/* 106 */     return UseAnim.BOW;
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResultHolder<ItemStack> use(Level $$0, Player $$1, InteractionHand $$2) {
/* 111 */     ItemStack $$3 = $$1.getItemInHand($$2);
/* 112 */     boolean $$4 = !$$1.getProjectile($$3).isEmpty();
/* 113 */     if (($$1.getAbilities()).instabuild || $$4) {
/* 114 */       $$1.startUsingItem($$2);
/* 115 */       return InteractionResultHolder.consume($$3);
/*     */     } 
/* 117 */     return InteractionResultHolder.fail($$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public Predicate<ItemStack> getAllSupportedProjectiles() {
/* 122 */     return ARROW_ONLY;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDefaultProjectileRange() {
/* 127 */     return 15;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\BowItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */