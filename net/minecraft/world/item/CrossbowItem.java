/*     */ package net.minecraft.world.item;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import java.util.function.Predicate;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.advancements.CriteriaTriggers;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.ListTag;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.stats.Stats;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResultHolder;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.monster.CrossbowAttackMob;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.entity.projectile.AbstractArrow;
/*     */ import net.minecraft.world.entity.projectile.FireworkRocketEntity;
/*     */ import net.minecraft.world.entity.projectile.Projectile;
/*     */ import net.minecraft.world.item.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.world.item.enchantment.Enchantments;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import org.joml.Quaternionf;
/*     */ import org.joml.Quaternionfc;
/*     */ import org.joml.Vector3f;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CrossbowItem
/*     */   extends ProjectileWeaponItem
/*     */   implements Vanishable
/*     */ {
/*     */   private static final String TAG_CHARGED = "Charged";
/*     */   private static final String TAG_CHARGED_PROJECTILES = "ChargedProjectiles";
/*     */   private static final int MAX_CHARGE_DURATION = 25;
/*     */   public static final int DEFAULT_RANGE = 8;
/*     */   private boolean startSoundPlayed;
/*     */   private boolean midLoadSoundPlayed;
/*     */   private static final float START_SOUND_PERCENT = 0.2F;
/*     */   private static final float MID_SOUND_PERCENT = 0.5F;
/*     */   private static final float ARROW_POWER = 3.15F;
/*     */   private static final float FIREWORK_POWER = 1.6F;
/*     */   
/*     */   public CrossbowItem(Item.Properties $$0) {
/*  56 */     super($$0);
/*  57 */     this.startSoundPlayed = false;
/*  58 */     this.midLoadSoundPlayed = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public Predicate<ItemStack> getSupportedHeldProjectiles() {
/*  63 */     return ARROW_OR_FIREWORK;
/*     */   }
/*     */ 
/*     */   
/*     */   public Predicate<ItemStack> getAllSupportedProjectiles() {
/*  68 */     return ARROW_ONLY;
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResultHolder<ItemStack> use(Level $$0, Player $$1, InteractionHand $$2) {
/*  73 */     ItemStack $$3 = $$1.getItemInHand($$2);
/*     */     
/*  75 */     if (isCharged($$3)) {
/*  76 */       performShooting($$0, (LivingEntity)$$1, $$2, $$3, getShootingPower($$3), 1.0F);
/*  77 */       setCharged($$3, false);
/*  78 */       return InteractionResultHolder.consume($$3);
/*     */     } 
/*     */     
/*  81 */     if (!$$1.getProjectile($$3).isEmpty()) {
/*  82 */       if (!isCharged($$3)) {
/*  83 */         this.startSoundPlayed = false;
/*  84 */         this.midLoadSoundPlayed = false;
/*  85 */         $$1.startUsingItem($$2);
/*     */       } 
/*  87 */       return InteractionResultHolder.consume($$3);
/*     */     } 
/*  89 */     return InteractionResultHolder.fail($$3);
/*     */   }
/*     */   
/*     */   private static float getShootingPower(ItemStack $$0) {
/*  93 */     if (containsChargedProjectile($$0, Items.FIREWORK_ROCKET)) {
/*  94 */       return 1.6F;
/*     */     }
/*  96 */     return 3.15F;
/*     */   }
/*     */ 
/*     */   
/*     */   public void releaseUsing(ItemStack $$0, Level $$1, LivingEntity $$2, int $$3) {
/* 101 */     int $$4 = getUseDuration($$0) - $$3;
/* 102 */     float $$5 = getPowerForTime($$4, $$0);
/*     */     
/* 104 */     if ($$5 >= 1.0F && !isCharged($$0) && 
/* 105 */       tryLoadProjectiles($$2, $$0)) {
/* 106 */       setCharged($$0, true);
/* 107 */       SoundSource $$6 = ($$2 instanceof Player) ? SoundSource.PLAYERS : SoundSource.HOSTILE;
/* 108 */       $$1.playSound(null, $$2.getX(), $$2.getY(), $$2.getZ(), SoundEvents.CROSSBOW_LOADING_END, $$6, 1.0F, 1.0F / ($$1.getRandom().nextFloat() * 0.5F + 1.0F) + 0.2F);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean tryLoadProjectiles(LivingEntity $$0, ItemStack $$1) {
/* 114 */     int $$2 = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.MULTISHOT, $$1);
/* 115 */     int $$3 = ($$2 == 0) ? 1 : 3;
/* 116 */     boolean $$4 = ($$0 instanceof Player && (((Player)$$0).getAbilities()).instabuild);
/*     */     
/* 118 */     ItemStack $$5 = $$0.getProjectile($$1);
/* 119 */     ItemStack $$6 = $$5.copy();
/* 120 */     for (int $$7 = 0; $$7 < $$3; $$7++) {
/* 121 */       if ($$7 > 0) {
/* 122 */         $$5 = $$6.copy();
/*     */       }
/*     */       
/* 125 */       if ($$5.isEmpty() && $$4) {
/* 126 */         $$5 = new ItemStack(Items.ARROW);
/* 127 */         $$6 = $$5.copy();
/*     */       } 
/*     */       
/* 130 */       if (!loadProjectile($$0, $$1, $$5, ($$7 > 0), $$4)) {
/* 131 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 135 */     return true;
/*     */   }
/*     */   private static boolean loadProjectile(LivingEntity $$0, ItemStack $$1, ItemStack $$2, boolean $$3, boolean $$4) {
/*     */     ItemStack $$7;
/* 139 */     if ($$2.isEmpty()) {
/* 140 */       return false;
/*     */     }
/* 142 */     boolean $$5 = ($$4 && $$2.getItem() instanceof ArrowItem);
/*     */ 
/*     */     
/* 145 */     if (!$$5 && !$$4 && !$$3) {
/* 146 */       ItemStack $$6 = $$2.split(1);
/* 147 */       if ($$2.isEmpty() && $$0 instanceof Player)
/*     */       {
/* 149 */         ((Player)$$0).getInventory().removeItem($$2);
/*     */       }
/*     */     } else {
/* 152 */       $$7 = $$2.copy();
/*     */     } 
/*     */     
/* 155 */     addChargedProjectile($$1, $$7);
/* 156 */     return true;
/*     */   }
/*     */   
/*     */   public static boolean isCharged(ItemStack $$0) {
/* 160 */     CompoundTag $$1 = $$0.getTag();
/* 161 */     return ($$1 != null && $$1.getBoolean("Charged"));
/*     */   }
/*     */   
/*     */   public static void setCharged(ItemStack $$0, boolean $$1) {
/* 165 */     CompoundTag $$2 = $$0.getOrCreateTag();
/* 166 */     $$2.putBoolean("Charged", $$1);
/*     */   }
/*     */   private static void addChargedProjectile(ItemStack $$0, ItemStack $$1) {
/*     */     ListTag $$4;
/* 170 */     CompoundTag $$2 = $$0.getOrCreateTag();
/*     */     
/* 172 */     if ($$2.contains("ChargedProjectiles", 9)) {
/* 173 */       ListTag $$3 = $$2.getList("ChargedProjectiles", 10);
/*     */     } else {
/* 175 */       $$4 = new ListTag();
/*     */     } 
/* 177 */     CompoundTag $$5 = new CompoundTag();
/* 178 */     $$1.save($$5);
/* 179 */     $$4.add($$5);
/* 180 */     $$2.put("ChargedProjectiles", (Tag)$$4);
/*     */   }
/*     */   
/*     */   private static List<ItemStack> getChargedProjectiles(ItemStack $$0) {
/* 184 */     List<ItemStack> $$1 = Lists.newArrayList();
/* 185 */     CompoundTag $$2 = $$0.getTag();
/* 186 */     if ($$2 != null && 
/* 187 */       $$2.contains("ChargedProjectiles", 9)) {
/* 188 */       ListTag $$3 = $$2.getList("ChargedProjectiles", 10);
/* 189 */       if ($$3 != null) {
/* 190 */         for (int $$4 = 0; $$4 < $$3.size(); $$4++) {
/* 191 */           CompoundTag $$5 = $$3.getCompound($$4);
/* 192 */           $$1.add(ItemStack.of($$5));
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 197 */     return $$1;
/*     */   }
/*     */   
/*     */   private static void clearChargedProjectiles(ItemStack $$0) {
/* 201 */     CompoundTag $$1 = $$0.getTag();
/* 202 */     if ($$1 != null) {
/* 203 */       ListTag $$2 = $$1.getList("ChargedProjectiles", 9);
/* 204 */       $$2.clear();
/* 205 */       $$1.put("ChargedProjectiles", (Tag)$$2);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean containsChargedProjectile(ItemStack $$0, Item $$1) {
/* 210 */     return getChargedProjectiles($$0).stream().anyMatch($$1 -> $$1.is($$0));
/*     */   }
/*     */   private static void shootProjectile(Level $$0, LivingEntity $$1, InteractionHand $$2, ItemStack $$3, ItemStack $$4, float $$5, boolean $$6, float $$7, float $$8, float $$9) {
/*     */     AbstractArrow abstractArrow;
/* 214 */     if ($$0.isClientSide) {
/*     */       return;
/*     */     }
/*     */     
/* 218 */     boolean $$10 = $$4.is(Items.FIREWORK_ROCKET);
/*     */ 
/*     */     
/* 221 */     if ($$10) {
/* 222 */       FireworkRocketEntity fireworkRocketEntity = new FireworkRocketEntity($$0, $$4, (Entity)$$1, $$1.getX(), $$1.getEyeY() - 0.15000000596046448D, $$1.getZ(), true);
/*     */     } else {
/* 224 */       abstractArrow = getArrow($$0, $$1, $$3, $$4);
/* 225 */       if ($$6 || $$9 != 0.0F) {
/* 226 */         abstractArrow.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
/*     */       }
/*     */     } 
/*     */     
/* 230 */     if ($$1 instanceof CrossbowAttackMob) { CrossbowAttackMob $$13 = (CrossbowAttackMob)$$1;
/* 231 */       $$13.shootCrossbowProjectile($$13.getTarget(), $$3, (Projectile)abstractArrow, $$9); }
/*     */     else
/* 233 */     { Vec3 $$14 = $$1.getUpVector(1.0F);
/* 234 */       Quaternionf $$15 = (new Quaternionf()).setAngleAxis(($$9 * 0.017453292F), $$14.x, $$14.y, $$14.z);
/* 235 */       Vec3 $$16 = $$1.getViewVector(1.0F);
/* 236 */       Vector3f $$17 = $$16.toVector3f().rotate((Quaternionfc)$$15);
/* 237 */       abstractArrow.shoot($$17.x(), $$17.y(), $$17.z(), $$7, $$8); }
/*     */ 
/*     */     
/* 240 */     $$3.hurtAndBreak($$10 ? 3 : 1, $$1, $$1 -> $$1.broadcastBreakEvent($$0));
/* 241 */     $$0.addFreshEntity((Entity)abstractArrow);
/* 242 */     $$0.playSound(null, $$1.getX(), $$1.getY(), $$1.getZ(), SoundEvents.CROSSBOW_SHOOT, SoundSource.PLAYERS, 1.0F, $$5);
/*     */   }
/*     */   
/*     */   private static AbstractArrow getArrow(Level $$0, LivingEntity $$1, ItemStack $$2, ItemStack $$3) {
/* 246 */     ArrowItem $$4 = ($$3.getItem() instanceof ArrowItem) ? (ArrowItem)$$3.getItem() : (ArrowItem)Items.ARROW;
/* 247 */     AbstractArrow $$5 = $$4.createArrow($$0, $$3, $$1);
/* 248 */     if ($$1 instanceof Player) {
/* 249 */       $$5.setCritArrow(true);
/*     */     }
/* 251 */     $$5.setSoundEvent(SoundEvents.CROSSBOW_HIT);
/* 252 */     $$5.setShotFromCrossbow(true);
/*     */     
/* 254 */     int $$6 = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PIERCING, $$2);
/* 255 */     if ($$6 > 0) {
/* 256 */       $$5.setPierceLevel((byte)$$6);
/*     */     }
/*     */     
/* 259 */     return $$5;
/*     */   }
/*     */   
/*     */   public static void performShooting(Level $$0, LivingEntity $$1, InteractionHand $$2, ItemStack $$3, float $$4, float $$5) {
/* 263 */     List<ItemStack> $$6 = getChargedProjectiles($$3);
/* 264 */     float[] $$7 = getShotPitches($$1.getRandom());
/*     */     
/* 266 */     for (int $$8 = 0; $$8 < $$6.size(); $$8++) {
/* 267 */       ItemStack $$9 = $$6.get($$8);
/* 268 */       boolean $$10 = ($$1 instanceof Player && (((Player)$$1).getAbilities()).instabuild);
/*     */       
/* 270 */       if (!$$9.isEmpty())
/*     */       {
/*     */ 
/*     */         
/* 274 */         if ($$8 == 0) {
/* 275 */           shootProjectile($$0, $$1, $$2, $$3, $$9, $$7[$$8], $$10, $$4, $$5, 0.0F);
/* 276 */         } else if ($$8 == 1) {
/* 277 */           shootProjectile($$0, $$1, $$2, $$3, $$9, $$7[$$8], $$10, $$4, $$5, -10.0F);
/* 278 */         } else if ($$8 == 2) {
/* 279 */           shootProjectile($$0, $$1, $$2, $$3, $$9, $$7[$$8], $$10, $$4, $$5, 10.0F);
/*     */         } 
/*     */       }
/*     */     } 
/* 283 */     onCrossbowShot($$0, $$1, $$3);
/*     */   }
/*     */   
/*     */   private static float[] getShotPitches(RandomSource $$0) {
/* 287 */     boolean $$1 = $$0.nextBoolean();
/* 288 */     return new float[] { 1.0F, getRandomShotPitch($$1, $$0), getRandomShotPitch(!$$1, $$0) };
/*     */   }
/*     */   
/*     */   private static float getRandomShotPitch(boolean $$0, RandomSource $$1) {
/* 292 */     float $$2 = $$0 ? 0.63F : 0.43F;
/* 293 */     return 1.0F / ($$1.nextFloat() * 0.5F + 1.8F) + $$2;
/*     */   }
/*     */   
/*     */   private static void onCrossbowShot(Level $$0, LivingEntity $$1, ItemStack $$2) {
/* 297 */     if ($$1 instanceof ServerPlayer) { ServerPlayer $$3 = (ServerPlayer)$$1;
/* 298 */       if (!$$0.isClientSide) {
/* 299 */         CriteriaTriggers.SHOT_CROSSBOW.trigger($$3, $$2);
/*     */       }
/*     */       
/* 302 */       $$3.awardStat(Stats.ITEM_USED.get($$2.getItem())); }
/*     */ 
/*     */     
/* 305 */     clearChargedProjectiles($$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUseTick(Level $$0, LivingEntity $$1, ItemStack $$2, int $$3) {
/* 310 */     if (!$$0.isClientSide) {
/* 311 */       int $$4 = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.QUICK_CHARGE, $$2);
/* 312 */       SoundEvent $$5 = getStartSound($$4);
/* 313 */       SoundEvent $$6 = ($$4 == 0) ? SoundEvents.CROSSBOW_LOADING_MIDDLE : null;
/* 314 */       float $$7 = ($$2.getUseDuration() - $$3) / getChargeDuration($$2);
/*     */       
/* 316 */       if ($$7 < 0.2F) {
/* 317 */         this.startSoundPlayed = false;
/* 318 */         this.midLoadSoundPlayed = false;
/*     */       } 
/*     */       
/* 321 */       if ($$7 >= 0.2F && !this.startSoundPlayed) {
/* 322 */         this.startSoundPlayed = true;
/* 323 */         $$0.playSound(null, $$1.getX(), $$1.getY(), $$1.getZ(), $$5, SoundSource.PLAYERS, 0.5F, 1.0F);
/*     */       } 
/*     */       
/* 326 */       if ($$7 >= 0.5F && $$6 != null && !this.midLoadSoundPlayed) {
/* 327 */         this.midLoadSoundPlayed = true;
/* 328 */         $$0.playSound(null, $$1.getX(), $$1.getY(), $$1.getZ(), $$6, SoundSource.PLAYERS, 0.5F, 1.0F);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getUseDuration(ItemStack $$0) {
/* 335 */     return getChargeDuration($$0) + 3;
/*     */   }
/*     */   
/*     */   public static int getChargeDuration(ItemStack $$0) {
/* 339 */     int $$1 = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.QUICK_CHARGE, $$0);
/* 340 */     return ($$1 == 0) ? 25 : (25 - 5 * $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public UseAnim getUseAnimation(ItemStack $$0) {
/* 345 */     return UseAnim.CROSSBOW;
/*     */   }
/*     */   
/*     */   private SoundEvent getStartSound(int $$0) {
/* 349 */     switch ($$0) {
/*     */       case 1:
/* 351 */         return SoundEvents.CROSSBOW_QUICK_CHARGE_1;
/*     */       case 2:
/* 353 */         return SoundEvents.CROSSBOW_QUICK_CHARGE_2;
/*     */       case 3:
/* 355 */         return SoundEvents.CROSSBOW_QUICK_CHARGE_3;
/*     */     } 
/* 357 */     return SoundEvents.CROSSBOW_LOADING_START;
/*     */   }
/*     */ 
/*     */   
/*     */   private static float getPowerForTime(int $$0, ItemStack $$1) {
/* 362 */     float $$2 = $$0 / getChargeDuration($$1);
/* 363 */     if ($$2 > 1.0F) {
/* 364 */       $$2 = 1.0F;
/*     */     }
/* 366 */     return $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void appendHoverText(ItemStack $$0, @Nullable Level $$1, List<Component> $$2, TooltipFlag $$3) {
/* 371 */     List<ItemStack> $$4 = getChargedProjectiles($$0);
/* 372 */     if (!isCharged($$0) || $$4.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/* 376 */     ItemStack $$5 = $$4.get(0);
/* 377 */     $$2.add(Component.translatable("item.minecraft.crossbow.projectile").append(CommonComponents.SPACE).append($$5.getDisplayName()));
/* 378 */     if ($$3.isAdvanced() && $$5.is(Items.FIREWORK_ROCKET)) {
/* 379 */       List<Component> $$6 = Lists.newArrayList();
/* 380 */       Items.FIREWORK_ROCKET.appendHoverText($$5, $$1, $$6, $$3);
/* 381 */       if (!$$6.isEmpty()) {
/* 382 */         for (int $$7 = 0; $$7 < $$6.size(); $$7++) {
/* 383 */           $$6.set($$7, Component.literal("  ").append($$6.get($$7)).withStyle(ChatFormatting.GRAY));
/*     */         }
/*     */         
/* 386 */         $$2.addAll($$6);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean useOnRelease(ItemStack $$0) {
/* 393 */     return $$0.is(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDefaultProjectileRange() {
/* 398 */     return 8;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\CrossbowItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */