/*     */ package net.minecraft.world.item.enchantment;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EquipmentSlot;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.MobType;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ 
/*     */ public abstract class Enchantment {
/*     */   private final EquipmentSlot[] slots;
/*     */   private final Rarity rarity;
/*     */   
/*     */   @Nullable
/*     */   public static Enchantment byId(int $$0) {
/*  26 */     return (Enchantment)BuiltInRegistries.ENCHANTMENT.byId($$0);
/*     */   }
/*     */   public final EnchantmentCategory category; @Nullable
/*     */   protected String descriptionId;
/*  30 */   public enum Rarity { COMMON(10),
/*  31 */     UNCOMMON(5),
/*  32 */     RARE(2),
/*  33 */     VERY_RARE(1);
/*     */     
/*     */     private final int weight;
/*     */     
/*     */     Rarity(int $$0) {
/*  38 */       this.weight = $$0;
/*     */     }
/*     */     
/*     */     public int getWeight() {
/*  42 */       return this.weight;
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  52 */   private final Holder.Reference<Enchantment> builtInRegistryHolder = BuiltInRegistries.ENCHANTMENT.createIntrusiveHolder(this);
/*     */   
/*     */   protected Enchantment(Rarity $$0, EnchantmentCategory $$1, EquipmentSlot[] $$2) {
/*  55 */     this.rarity = $$0;
/*  56 */     this.category = $$1;
/*  57 */     this.slots = $$2;
/*     */   }
/*     */   
/*     */   public Map<EquipmentSlot, ItemStack> getSlotItems(LivingEntity $$0) {
/*  61 */     Map<EquipmentSlot, ItemStack> $$1 = Maps.newEnumMap(EquipmentSlot.class);
/*  62 */     for (EquipmentSlot $$2 : this.slots) {
/*  63 */       ItemStack $$3 = $$0.getItemBySlot($$2);
/*  64 */       if (!$$3.isEmpty()) {
/*  65 */         $$1.put($$2, $$3);
/*     */       }
/*     */     } 
/*  68 */     return $$1;
/*     */   }
/*     */   
/*     */   public Rarity getRarity() {
/*  72 */     return this.rarity;
/*     */   }
/*     */   
/*     */   public int getMinLevel() {
/*  76 */     return 1;
/*     */   }
/*     */   
/*     */   public int getMaxLevel() {
/*  80 */     return 1;
/*     */   }
/*     */   
/*     */   public int getMinCost(int $$0) {
/*  84 */     return 1 + $$0 * 10;
/*     */   }
/*     */   
/*     */   public int getMaxCost(int $$0) {
/*  88 */     return getMinCost($$0) + 5;
/*     */   }
/*     */   
/*     */   public int getDamageProtection(int $$0, DamageSource $$1) {
/*  92 */     return 0;
/*     */   }
/*     */   
/*     */   public float getDamageBonus(int $$0, MobType $$1) {
/*  96 */     return 0.0F;
/*     */   }
/*     */   
/*     */   public final boolean isCompatibleWith(Enchantment $$0) {
/* 100 */     return (checkCompatibility($$0) && $$0.checkCompatibility(this));
/*     */   }
/*     */   
/*     */   protected boolean checkCompatibility(Enchantment $$0) {
/* 104 */     return (this != $$0);
/*     */   }
/*     */   
/*     */   protected String getOrCreateDescriptionId() {
/* 108 */     if (this.descriptionId == null) {
/* 109 */       this.descriptionId = Util.makeDescriptionId("enchantment", BuiltInRegistries.ENCHANTMENT.getKey(this));
/*     */     }
/* 111 */     return this.descriptionId;
/*     */   }
/*     */   
/*     */   public String getDescriptionId() {
/* 115 */     return getOrCreateDescriptionId();
/*     */   }
/*     */   
/*     */   public Component getFullname(int $$0) {
/* 119 */     MutableComponent $$1 = Component.translatable(getDescriptionId());
/* 120 */     if (isCurse()) {
/* 121 */       $$1.withStyle(ChatFormatting.RED);
/*     */     } else {
/* 123 */       $$1.withStyle(ChatFormatting.GRAY);
/*     */     } 
/* 125 */     if ($$0 != 1 || getMaxLevel() != 1) {
/* 126 */       $$1.append(CommonComponents.SPACE).append((Component)Component.translatable("enchantment.level." + $$0));
/*     */     }
/* 128 */     return (Component)$$1;
/*     */   }
/*     */   
/*     */   public boolean canEnchant(ItemStack $$0) {
/* 132 */     return this.category.canEnchant($$0.getItem());
/*     */   }
/*     */ 
/*     */   
/*     */   public void doPostAttack(LivingEntity $$0, Entity $$1, int $$2) {}
/*     */ 
/*     */   
/*     */   public void doPostHurt(LivingEntity $$0, Entity $$1, int $$2) {}
/*     */   
/*     */   public boolean isTreasureOnly() {
/* 142 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isCurse() {
/* 146 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isTradeable() {
/* 151 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDiscoverable() {
/* 160 */     return true;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public Holder.Reference<Enchantment> builtInRegistryHolder() {
/* 165 */     return this.builtInRegistryHolder;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\enchantment\Enchantment.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */