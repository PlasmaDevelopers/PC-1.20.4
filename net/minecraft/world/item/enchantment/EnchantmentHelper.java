/*     */ package net.minecraft.world.item.enchantment;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Predicate;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.ListTag;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.util.random.WeightedRandom;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EquipmentSlot;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.MobType;
/*     */ import net.minecraft.world.item.EnchantedBookItem;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import org.apache.commons.lang3.mutable.MutableFloat;
/*     */ import org.apache.commons.lang3.mutable.MutableInt;
/*     */ 
/*     */ public class EnchantmentHelper {
/*     */   private static final String TAG_ENCH_ID = "id";
/*     */   private static final String TAG_ENCH_LEVEL = "lvl";
/*     */   private static final float SWIFT_SNEAK_EXTRA_FACTOR = 0.15F;
/*     */   
/*     */   public static CompoundTag storeEnchantment(@Nullable ResourceLocation $$0, int $$1) {
/*  40 */     CompoundTag $$2 = new CompoundTag();
/*  41 */     $$2.putString("id", String.valueOf($$0));
/*  42 */     $$2.putShort("lvl", (short)$$1);
/*  43 */     return $$2;
/*     */   }
/*     */   
/*     */   public static void setEnchantmentLevel(CompoundTag $$0, int $$1) {
/*  47 */     $$0.putShort("lvl", (short)$$1);
/*     */   }
/*     */   
/*     */   public static int getEnchantmentLevel(CompoundTag $$0) {
/*  51 */     return Mth.clamp($$0.getInt("lvl"), 0, 255);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static ResourceLocation getEnchantmentId(CompoundTag $$0) {
/*  56 */     return ResourceLocation.tryParse($$0.getString("id"));
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static ResourceLocation getEnchantmentId(Enchantment $$0) {
/*  61 */     return BuiltInRegistries.ENCHANTMENT.getKey($$0);
/*     */   }
/*     */   
/*     */   public static int getItemEnchantmentLevel(Enchantment $$0, ItemStack $$1) {
/*  65 */     if ($$1.isEmpty()) {
/*  66 */       return 0;
/*     */     }
/*     */     
/*  69 */     ResourceLocation $$2 = getEnchantmentId($$0);
/*     */     
/*  71 */     ListTag $$3 = $$1.getEnchantmentTags();
/*  72 */     for (int $$4 = 0; $$4 < $$3.size(); $$4++) {
/*  73 */       CompoundTag $$5 = $$3.getCompound($$4);
/*     */       
/*  75 */       ResourceLocation $$6 = getEnchantmentId($$5);
/*  76 */       if ($$6 != null && $$6.equals($$2)) {
/*  77 */         return getEnchantmentLevel($$5);
/*     */       }
/*     */     } 
/*  80 */     return 0;
/*     */   }
/*     */   
/*     */   public static Map<Enchantment, Integer> getEnchantments(ItemStack $$0) {
/*  84 */     ListTag $$1 = $$0.is(Items.ENCHANTED_BOOK) ? EnchantedBookItem.getEnchantments($$0) : $$0.getEnchantmentTags();
/*  85 */     return deserializeEnchantments($$1);
/*     */   }
/*     */   
/*     */   public static Map<Enchantment, Integer> deserializeEnchantments(ListTag $$0) {
/*  89 */     Map<Enchantment, Integer> $$1 = Maps.newLinkedHashMap();
/*  90 */     for (int $$2 = 0; $$2 < $$0.size(); $$2++) {
/*  91 */       CompoundTag $$3 = $$0.getCompound($$2);
/*     */       
/*  93 */       BuiltInRegistries.ENCHANTMENT.getOptional(getEnchantmentId($$3))
/*  94 */         .ifPresent($$2 -> $$0.put($$2, Integer.valueOf(getEnchantmentLevel($$1))));
/*     */     } 
/*     */     
/*  97 */     return $$1;
/*     */   }
/*     */   
/*     */   public static void setEnchantments(Map<Enchantment, Integer> $$0, ItemStack $$1) {
/* 101 */     ListTag $$2 = new ListTag();
/*     */     
/* 103 */     for (Map.Entry<Enchantment, Integer> $$3 : $$0.entrySet()) {
/* 104 */       Enchantment $$4 = $$3.getKey();
/* 105 */       if ($$4 == null) {
/*     */         continue;
/*     */       }
/* 108 */       int $$5 = ((Integer)$$3.getValue()).intValue();
/*     */       
/* 110 */       $$2.add(storeEnchantment(getEnchantmentId($$4), $$5));
/*     */       
/* 112 */       if ($$1.is(Items.ENCHANTED_BOOK)) {
/* 113 */         EnchantedBookItem.addEnchantment($$1, new EnchantmentInstance($$4, $$5));
/*     */       }
/*     */     } 
/*     */     
/* 117 */     if ($$2.isEmpty()) {
/* 118 */       $$1.removeTagKey("Enchantments");
/* 119 */     } else if (!$$1.is(Items.ENCHANTED_BOOK)) {
/* 120 */       $$1.addTagElement("Enchantments", (Tag)$$2);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void runIterationOnItem(EnchantmentVisitor $$0, ItemStack $$1) {
/* 130 */     if ($$1.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/* 134 */     ListTag $$2 = $$1.getEnchantmentTags();
/* 135 */     for (int $$3 = 0; $$3 < $$2.size(); $$3++) {
/* 136 */       CompoundTag $$4 = $$2.getCompound($$3);
/* 137 */       BuiltInRegistries.ENCHANTMENT.getOptional(getEnchantmentId($$4))
/* 138 */         .ifPresent($$2 -> $$0.accept($$2, getEnchantmentLevel($$1)));
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void runIterationOnInventory(EnchantmentVisitor $$0, Iterable<ItemStack> $$1) {
/* 143 */     for (ItemStack $$2 : $$1) {
/* 144 */       runIterationOnItem($$0, $$2);
/*     */     }
/*     */   }
/*     */   
/*     */   public static int getDamageProtection(Iterable<ItemStack> $$0, DamageSource $$1) {
/* 149 */     MutableInt $$2 = new MutableInt();
/* 150 */     runIterationOnInventory(($$2, $$3) -> $$0.add($$2.getDamageProtection($$3, $$1)), $$0);
/* 151 */     return $$2.intValue();
/*     */   }
/*     */   
/*     */   public static float getDamageBonus(ItemStack $$0, MobType $$1) {
/* 155 */     MutableFloat $$2 = new MutableFloat();
/* 156 */     runIterationOnItem(($$2, $$3) -> $$0.add($$2.getDamageBonus($$3, $$1)), $$0);
/* 157 */     return $$2.floatValue();
/*     */   }
/*     */   
/*     */   public static float getSweepingDamageRatio(LivingEntity $$0) {
/* 161 */     int $$1 = getEnchantmentLevel(Enchantments.SWEEPING_EDGE, $$0);
/* 162 */     if ($$1 > 0) {
/* 163 */       return SweepingEdgeEnchantment.getSweepingDamageRatio($$1);
/*     */     }
/* 165 */     return 0.0F;
/*     */   }
/*     */   
/*     */   public static void doPostHurtEffects(LivingEntity $$0, Entity $$1) {
/* 169 */     EnchantmentVisitor $$2 = ($$2, $$3) -> $$2.doPostHurt($$0, $$1, $$3);
/* 170 */     if ($$0 != null) {
/* 171 */       runIterationOnInventory($$2, $$0.getAllSlots());
/*     */     }
/* 173 */     if ($$1 instanceof net.minecraft.world.entity.player.Player) {
/* 174 */       runIterationOnItem($$2, $$0.getMainHandItem());
/*     */     }
/*     */   }
/*     */   
/*     */   public static void doPostDamageEffects(LivingEntity $$0, Entity $$1) {
/* 179 */     EnchantmentVisitor $$2 = ($$2, $$3) -> $$2.doPostAttack($$0, $$1, $$3);
/* 180 */     if ($$0 != null) {
/* 181 */       runIterationOnInventory($$2, $$0.getAllSlots());
/*     */     }
/* 183 */     if ($$0 instanceof net.minecraft.world.entity.player.Player) {
/* 184 */       runIterationOnItem($$2, $$0.getMainHandItem());
/*     */     }
/*     */   }
/*     */   
/*     */   public static int getEnchantmentLevel(Enchantment $$0, LivingEntity $$1) {
/* 189 */     Iterable<ItemStack> $$2 = $$0.getSlotItems($$1).values();
/* 190 */     if ($$2 == null) {
/* 191 */       return 0;
/*     */     }
/* 193 */     int $$3 = 0;
/* 194 */     for (ItemStack $$4 : $$2) {
/* 195 */       int $$5 = getItemEnchantmentLevel($$0, $$4);
/* 196 */       if ($$5 > $$3) {
/* 197 */         $$3 = $$5;
/*     */       }
/*     */     } 
/* 200 */     return $$3;
/*     */   }
/*     */   
/*     */   public static float getSneakingSpeedBonus(LivingEntity $$0) {
/* 204 */     return getEnchantmentLevel(Enchantments.SWIFT_SNEAK, $$0) * 0.15F;
/*     */   }
/*     */   
/*     */   public static int getKnockbackBonus(LivingEntity $$0) {
/* 208 */     return getEnchantmentLevel(Enchantments.KNOCKBACK, $$0);
/*     */   }
/*     */   
/*     */   public static int getFireAspect(LivingEntity $$0) {
/* 212 */     return getEnchantmentLevel(Enchantments.FIRE_ASPECT, $$0);
/*     */   }
/*     */   
/*     */   public static int getRespiration(LivingEntity $$0) {
/* 216 */     return getEnchantmentLevel(Enchantments.RESPIRATION, $$0);
/*     */   }
/*     */   
/*     */   public static int getDepthStrider(LivingEntity $$0) {
/* 220 */     return getEnchantmentLevel(Enchantments.DEPTH_STRIDER, $$0);
/*     */   }
/*     */   
/*     */   public static int getBlockEfficiency(LivingEntity $$0) {
/* 224 */     return getEnchantmentLevel(Enchantments.BLOCK_EFFICIENCY, $$0);
/*     */   }
/*     */   
/*     */   public static int getFishingLuckBonus(ItemStack $$0) {
/* 228 */     return getItemEnchantmentLevel(Enchantments.FISHING_LUCK, $$0);
/*     */   }
/*     */   
/*     */   public static int getFishingSpeedBonus(ItemStack $$0) {
/* 232 */     return getItemEnchantmentLevel(Enchantments.FISHING_SPEED, $$0);
/*     */   }
/*     */   
/*     */   public static int getMobLooting(LivingEntity $$0) {
/* 236 */     return getEnchantmentLevel(Enchantments.MOB_LOOTING, $$0);
/*     */   }
/*     */   
/*     */   public static boolean hasAquaAffinity(LivingEntity $$0) {
/* 240 */     return (getEnchantmentLevel(Enchantments.AQUA_AFFINITY, $$0) > 0);
/*     */   }
/*     */   
/*     */   public static boolean hasFrostWalker(LivingEntity $$0) {
/* 244 */     return (getEnchantmentLevel(Enchantments.FROST_WALKER, $$0) > 0);
/*     */   }
/*     */   
/*     */   public static boolean hasSoulSpeed(LivingEntity $$0) {
/* 248 */     return (getEnchantmentLevel(Enchantments.SOUL_SPEED, $$0) > 0);
/*     */   }
/*     */   
/*     */   public static boolean hasBindingCurse(ItemStack $$0) {
/* 252 */     return (getItemEnchantmentLevel(Enchantments.BINDING_CURSE, $$0) > 0);
/*     */   }
/*     */   
/*     */   public static boolean hasVanishingCurse(ItemStack $$0) {
/* 256 */     return (getItemEnchantmentLevel(Enchantments.VANISHING_CURSE, $$0) > 0);
/*     */   }
/*     */   
/*     */   public static boolean hasSilkTouch(ItemStack $$0) {
/* 260 */     return (getItemEnchantmentLevel(Enchantments.SILK_TOUCH, $$0) > 0);
/*     */   }
/*     */   
/*     */   public static int getLoyalty(ItemStack $$0) {
/* 264 */     return getItemEnchantmentLevel(Enchantments.LOYALTY, $$0);
/*     */   }
/*     */   
/*     */   public static int getRiptide(ItemStack $$0) {
/* 268 */     return getItemEnchantmentLevel(Enchantments.RIPTIDE, $$0);
/*     */   }
/*     */   
/*     */   public static boolean hasChanneling(ItemStack $$0) {
/* 272 */     return (getItemEnchantmentLevel(Enchantments.CHANNELING, $$0) > 0);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static Map.Entry<EquipmentSlot, ItemStack> getRandomItemWith(Enchantment $$0, LivingEntity $$1) {
/* 277 */     return getRandomItemWith($$0, $$1, $$0 -> true);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static Map.Entry<EquipmentSlot, ItemStack> getRandomItemWith(Enchantment $$0, LivingEntity $$1, Predicate<ItemStack> $$2) {
/* 282 */     Map<EquipmentSlot, ItemStack> $$3 = $$0.getSlotItems($$1);
/* 283 */     if ($$3.isEmpty()) {
/* 284 */       return null;
/*     */     }
/* 286 */     List<Map.Entry<EquipmentSlot, ItemStack>> $$4 = Lists.newArrayList();
/* 287 */     for (Map.Entry<EquipmentSlot, ItemStack> $$5 : $$3.entrySet()) {
/* 288 */       ItemStack $$6 = $$5.getValue();
/* 289 */       if (!$$6.isEmpty() && getItemEnchantmentLevel($$0, $$6) > 0 && $$2.test($$6)) {
/* 290 */         $$4.add($$5);
/*     */       }
/*     */     } 
/*     */     
/* 294 */     return $$4.isEmpty() ? null : $$4.get($$1.getRandom().nextInt($$4.size()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getEnchantmentCost(RandomSource $$0, int $$1, int $$2, ItemStack $$3) {
/* 305 */     Item $$4 = $$3.getItem();
/* 306 */     int $$5 = $$4.getEnchantmentValue();
/*     */     
/* 308 */     if ($$5 <= 0)
/*     */     {
/* 310 */       return 0;
/*     */     }
/*     */     
/* 313 */     if ($$2 > 15) {
/* 314 */       $$2 = 15;
/*     */     }
/* 316 */     int $$6 = $$0.nextInt(8) + 1 + ($$2 >> 1) + $$0.nextInt($$2 + 1);
/* 317 */     if ($$1 == 0) {
/* 318 */       return Math.max($$6 / 3, 1);
/*     */     }
/* 320 */     if ($$1 == 1) {
/* 321 */       return $$6 * 2 / 3 + 1;
/*     */     }
/* 323 */     return Math.max($$6, $$2 * 2);
/*     */   }
/*     */   
/*     */   public static ItemStack enchantItem(RandomSource $$0, ItemStack $$1, int $$2, boolean $$3) {
/* 327 */     List<EnchantmentInstance> $$4 = selectEnchantment($$0, $$1, $$2, $$3);
/*     */     
/* 329 */     boolean $$5 = $$1.is(Items.BOOK);
/* 330 */     if ($$5) {
/* 331 */       $$1 = new ItemStack((ItemLike)Items.ENCHANTED_BOOK);
/*     */     }
/*     */     
/* 334 */     for (EnchantmentInstance $$6 : $$4) {
/* 335 */       if ($$5) {
/* 336 */         EnchantedBookItem.addEnchantment($$1, $$6); continue;
/*     */       } 
/* 338 */       $$1.enchant($$6.enchantment, $$6.level);
/*     */     } 
/*     */ 
/*     */     
/* 342 */     return $$1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<EnchantmentInstance> selectEnchantment(RandomSource $$0, ItemStack $$1, int $$2, boolean $$3) {
/* 353 */     List<EnchantmentInstance> $$4 = Lists.newArrayList();
/*     */ 
/*     */     
/* 356 */     Item $$5 = $$1.getItem();
/* 357 */     int $$6 = $$5.getEnchantmentValue();
/*     */     
/* 359 */     if ($$6 <= 0) {
/* 360 */       return $$4;
/*     */     }
/*     */     
/* 363 */     $$2 += 1 + $$0.nextInt($$6 / 4 + 1) + $$0.nextInt($$6 / 4 + 1);
/*     */ 
/*     */     
/* 366 */     float $$7 = ($$0.nextFloat() + $$0.nextFloat() - 1.0F) * 0.15F;
/* 367 */     $$2 = Mth.clamp(Math.round($$2 + $$2 * $$7), 1, 2147483647);
/*     */     
/* 369 */     List<EnchantmentInstance> $$8 = getAvailableEnchantmentResults($$2, $$1, $$3);
/* 370 */     if (!$$8.isEmpty()) {
/* 371 */       Objects.requireNonNull($$4); WeightedRandom.getRandomItem($$0, $$8).ifPresent($$4::add);
/*     */       
/* 373 */       while ($$0.nextInt(50) <= $$2) {
/* 374 */         if (!$$4.isEmpty()) {
/* 375 */           filterCompatibleEnchantments($$8, (EnchantmentInstance)Util.lastOf($$4));
/*     */         }
/*     */         
/* 378 */         if ($$8.isEmpty()) {
/*     */           break;
/*     */         }
/*     */         
/* 382 */         Objects.requireNonNull($$4); WeightedRandom.getRandomItem($$0, $$8).ifPresent($$4::add);
/* 383 */         $$2 /= 2;
/*     */       } 
/*     */     } 
/* 386 */     return $$4;
/*     */   }
/*     */   
/*     */   public static void filterCompatibleEnchantments(List<EnchantmentInstance> $$0, EnchantmentInstance $$1) {
/* 390 */     Iterator<EnchantmentInstance> $$2 = $$0.iterator();
/* 391 */     while ($$2.hasNext()) {
/* 392 */       if (!$$1.enchantment.isCompatibleWith(((EnchantmentInstance)$$2.next()).enchantment)) {
/* 393 */         $$2.remove();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean isEnchantmentCompatible(Collection<Enchantment> $$0, Enchantment $$1) {
/* 399 */     for (Enchantment $$2 : $$0) {
/* 400 */       if (!$$2.isCompatibleWith($$1)) {
/* 401 */         return false;
/*     */       }
/*     */     } 
/* 404 */     return true;
/*     */   }
/*     */   
/*     */   public static List<EnchantmentInstance> getAvailableEnchantmentResults(int $$0, ItemStack $$1, boolean $$2) {
/* 408 */     List<EnchantmentInstance> $$3 = Lists.newArrayList();
/*     */     
/* 410 */     Item $$4 = $$1.getItem();
/* 411 */     boolean $$5 = $$1.is(Items.BOOK);
/* 412 */     for (Enchantment $$6 : BuiltInRegistries.ENCHANTMENT) {
/* 413 */       if ($$6.isTreasureOnly() && !$$2) {
/*     */         continue;
/*     */       }
/* 416 */       if (!$$6.isDiscoverable()) {
/*     */         continue;
/*     */       }
/*     */       
/* 420 */       if (!$$6.category.canEnchant($$4) && !$$5) {
/*     */         continue;
/*     */       }
/*     */       
/* 424 */       for (int $$7 = $$6.getMaxLevel(); $$7 > $$6.getMinLevel() - 1; $$7--) {
/* 425 */         if ($$0 >= $$6.getMinCost($$7) && $$0 <= $$6.getMaxCost($$7)) {
/* 426 */           $$3.add(new EnchantmentInstance($$6, $$7));
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 432 */     return $$3;
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   private static interface EnchantmentVisitor {
/*     */     void accept(Enchantment param1Enchantment, int param1Int);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\enchantment\EnchantmentHelper.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */