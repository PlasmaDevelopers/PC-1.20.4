/*     */ package net.minecraft.world.item;
/*     */ 
/*     */ import com.google.common.collect.ImmutableMultimap;
/*     */ import com.google.common.collect.Multimap;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.stats.Stats;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResultHolder;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EquipmentSlot;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.MoverType;
/*     */ import net.minecraft.world.entity.ai.attributes.Attribute;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeModifier;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.entity.projectile.AbstractArrow;
/*     */ import net.minecraft.world.entity.projectile.ThrownTrident;
/*     */ import net.minecraft.world.item.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class TridentItem
/*     */   extends Item implements Vanishable {
/*     */   public static final int THROW_THRESHOLD_TIME = 10;
/*     */   public static final float BASE_DAMAGE = 8.0F;
/*     */   
/*     */   public TridentItem(Item.Properties $$0) {
/*  35 */     super($$0);
/*     */ 
/*     */     
/*  38 */     ImmutableMultimap.Builder<Attribute, AttributeModifier> $$1 = ImmutableMultimap.builder();
/*  39 */     $$1.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", 8.0D, AttributeModifier.Operation.ADDITION));
/*  40 */     $$1.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", -2.9000000953674316D, AttributeModifier.Operation.ADDITION));
/*  41 */     this.defaultModifiers = (Multimap<Attribute, AttributeModifier>)$$1.build();
/*     */   }
/*     */   public static final float SHOOT_POWER = 2.5F; private final Multimap<Attribute, AttributeModifier> defaultModifiers;
/*     */   
/*     */   public boolean canAttackBlock(BlockState $$0, Level $$1, BlockPos $$2, Player $$3) {
/*  46 */     return !$$3.isCreative();
/*     */   }
/*     */ 
/*     */   
/*     */   public UseAnim getUseAnimation(ItemStack $$0) {
/*  51 */     return UseAnim.SPEAR;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getUseDuration(ItemStack $$0) {
/*  56 */     return 72000;
/*     */   }
/*     */ 
/*     */   
/*     */   public void releaseUsing(ItemStack $$0, Level $$1, LivingEntity $$2, int $$3) {
/*  61 */     if (!($$2 instanceof Player)) {
/*     */       return;
/*     */     }
/*     */     
/*  65 */     Player $$4 = (Player)$$2;
/*     */     
/*  67 */     int $$5 = getUseDuration($$0) - $$3;
/*  68 */     if ($$5 < 10) {
/*     */       return;
/*     */     }
/*     */     
/*  72 */     int $$6 = EnchantmentHelper.getRiptide($$0);
/*  73 */     if ($$6 > 0 && !$$4.isInWaterOrRain()) {
/*     */       return;
/*     */     }
/*     */     
/*  77 */     if (!$$1.isClientSide) {
/*  78 */       $$0.hurtAndBreak(1, $$4, $$1 -> $$1.broadcastBreakEvent($$0.getUsedItemHand()));
/*     */       
/*  80 */       if ($$6 == 0) {
/*  81 */         ThrownTrident $$7 = new ThrownTrident($$1, (LivingEntity)$$4, $$0);
/*  82 */         $$7.shootFromRotation((Entity)$$4, $$4.getXRot(), $$4.getYRot(), 0.0F, 2.5F + $$6 * 0.5F, 1.0F);
/*     */         
/*  84 */         if (($$4.getAbilities()).instabuild) {
/*  85 */           $$7.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
/*     */         }
/*     */         
/*  88 */         $$1.addFreshEntity((Entity)$$7);
/*     */         
/*  90 */         $$1.playSound(null, (Entity)$$7, SoundEvents.TRIDENT_THROW, SoundSource.PLAYERS, 1.0F, 1.0F);
/*     */         
/*  92 */         if (!($$4.getAbilities()).instabuild) {
/*  93 */           $$4.getInventory().removeItem($$0);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  98 */     $$4.awardStat(Stats.ITEM_USED.get(this));
/*     */     
/* 100 */     if ($$6 > 0) {
/* 101 */       SoundEvent $$18; float $$8 = $$4.getYRot();
/* 102 */       float $$9 = $$4.getXRot();
/*     */ 
/*     */       
/* 105 */       float $$10 = -Mth.sin($$8 * 0.017453292F) * Mth.cos($$9 * 0.017453292F);
/* 106 */       float $$11 = -Mth.sin($$9 * 0.017453292F);
/* 107 */       float $$12 = Mth.cos($$8 * 0.017453292F) * Mth.cos($$9 * 0.017453292F);
/* 108 */       float $$13 = Mth.sqrt($$10 * $$10 + $$11 * $$11 + $$12 * $$12);
/* 109 */       float $$14 = 3.0F * (1.0F + $$6) / 4.0F;
/* 110 */       $$10 *= $$14 / $$13;
/* 111 */       $$11 *= $$14 / $$13;
/* 112 */       $$12 *= $$14 / $$13;
/* 113 */       $$4.push($$10, $$11, $$12);
/*     */       
/* 115 */       $$4.startAutoSpinAttack(20);
/* 116 */       if ($$4.onGround()) {
/* 117 */         float $$15 = 1.1999999F;
/* 118 */         $$4.move(MoverType.SELF, new Vec3(0.0D, 1.1999999284744263D, 0.0D));
/*     */       } 
/*     */ 
/*     */       
/* 122 */       if ($$6 >= 3) {
/* 123 */         SoundEvent $$16 = SoundEvents.TRIDENT_RIPTIDE_3;
/* 124 */       } else if ($$6 == 2) {
/* 125 */         SoundEvent $$17 = SoundEvents.TRIDENT_RIPTIDE_2;
/*     */       } else {
/* 127 */         $$18 = SoundEvents.TRIDENT_RIPTIDE_1;
/*     */       } 
/* 129 */       $$1.playSound(null, (Entity)$$4, $$18, SoundSource.PLAYERS, 1.0F, 1.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResultHolder<ItemStack> use(Level $$0, Player $$1, InteractionHand $$2) {
/* 135 */     ItemStack $$3 = $$1.getItemInHand($$2);
/* 136 */     if ($$3.getDamageValue() >= $$3.getMaxDamage() - 1)
/*     */     {
/* 138 */       return InteractionResultHolder.fail($$3);
/*     */     }
/* 140 */     if (EnchantmentHelper.getRiptide($$3) > 0 && !$$1.isInWaterOrRain())
/*     */     {
/* 142 */       return InteractionResultHolder.fail($$3);
/*     */     }
/* 144 */     $$1.startUsingItem($$2);
/* 145 */     return InteractionResultHolder.consume($$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hurtEnemy(ItemStack $$0, LivingEntity $$1, LivingEntity $$2) {
/* 150 */     $$0.hurtAndBreak(1, $$2, $$0 -> $$0.broadcastBreakEvent(EquipmentSlot.MAINHAND));
/* 151 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean mineBlock(ItemStack $$0, Level $$1, BlockState $$2, BlockPos $$3, LivingEntity $$4) {
/* 157 */     if ($$2.getDestroySpeed((BlockGetter)$$1, $$3) != 0.0D) {
/* 158 */       $$0.hurtAndBreak(2, $$4, $$0 -> $$0.broadcastBreakEvent(EquipmentSlot.MAINHAND));
/*     */     }
/* 160 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot $$0) {
/* 165 */     if ($$0 == EquipmentSlot.MAINHAND) {
/* 166 */       return this.defaultModifiers;
/*     */     }
/* 168 */     return super.getDefaultAttributeModifiers($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getEnchantmentValue() {
/* 173 */     return 1;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\TridentItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */