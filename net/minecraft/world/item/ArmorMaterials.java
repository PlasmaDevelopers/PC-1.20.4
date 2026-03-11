/*     */ package net.minecraft.world.item;
/*     */ import com.mojang.serialization.Codec;
/*     */ import java.util.EnumMap;
/*     */ import java.util.function.Supplier;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.util.StringRepresentable;
/*     */ import net.minecraft.world.item.crafting.Ingredient;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ 
/*     */ public enum ArmorMaterials implements StringRepresentable, ArmorMaterial {
/*     */   LEATHER, CHAIN, IRON, GOLD, DIAMOND, TURTLE, NETHERITE;
/*     */   public static final Codec<ArmorMaterials> CODEC;
/*     */   
/*     */   static {
/*  17 */     LEATHER = new ArmorMaterials("LEATHER", 0, "leather", 5, (EnumMap<ArmorItem.Type, Integer>)Util.make(new EnumMap<>(ArmorItem.Type.class), $$0 -> { $$0.put(ArmorItem.Type.BOOTS, Integer.valueOf(1)); $$0.put(ArmorItem.Type.LEGGINGS, Integer.valueOf(2)); $$0.put(ArmorItem.Type.CHESTPLATE, Integer.valueOf(3)); $$0.put(ArmorItem.Type.HELMET, Integer.valueOf(1)); }), 15, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F, 0.0F, () -> Ingredient.of(new ItemLike[] { Items.LEATHER }));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  31 */     CHAIN = new ArmorMaterials("CHAIN", 1, "chainmail", 15, (EnumMap<ArmorItem.Type, Integer>)Util.make(new EnumMap<>(ArmorItem.Type.class), $$0 -> { $$0.put(ArmorItem.Type.BOOTS, Integer.valueOf(1)); $$0.put(ArmorItem.Type.LEGGINGS, Integer.valueOf(4)); $$0.put(ArmorItem.Type.CHESTPLATE, Integer.valueOf(5)); $$0.put(ArmorItem.Type.HELMET, Integer.valueOf(2)); }), 12, SoundEvents.ARMOR_EQUIP_CHAIN, 0.0F, 0.0F, () -> Ingredient.of(new ItemLike[] { Items.IRON_INGOT }));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  45 */     IRON = new ArmorMaterials("IRON", 2, "iron", 15, (EnumMap<ArmorItem.Type, Integer>)Util.make(new EnumMap<>(ArmorItem.Type.class), $$0 -> { $$0.put(ArmorItem.Type.BOOTS, Integer.valueOf(2)); $$0.put(ArmorItem.Type.LEGGINGS, Integer.valueOf(5)); $$0.put(ArmorItem.Type.CHESTPLATE, Integer.valueOf(6)); $$0.put(ArmorItem.Type.HELMET, Integer.valueOf(2)); }), 9, SoundEvents.ARMOR_EQUIP_IRON, 0.0F, 0.0F, () -> Ingredient.of(new ItemLike[] { Items.IRON_INGOT }));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  59 */     GOLD = new ArmorMaterials("GOLD", 3, "gold", 7, (EnumMap<ArmorItem.Type, Integer>)Util.make(new EnumMap<>(ArmorItem.Type.class), $$0 -> { $$0.put(ArmorItem.Type.BOOTS, Integer.valueOf(1)); $$0.put(ArmorItem.Type.LEGGINGS, Integer.valueOf(3)); $$0.put(ArmorItem.Type.CHESTPLATE, Integer.valueOf(5)); $$0.put(ArmorItem.Type.HELMET, Integer.valueOf(2)); }), 25, SoundEvents.ARMOR_EQUIP_GOLD, 0.0F, 0.0F, () -> Ingredient.of(new ItemLike[] { Items.GOLD_INGOT }));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  73 */     DIAMOND = new ArmorMaterials("DIAMOND", 4, "diamond", 33, (EnumMap<ArmorItem.Type, Integer>)Util.make(new EnumMap<>(ArmorItem.Type.class), $$0 -> { $$0.put(ArmorItem.Type.BOOTS, Integer.valueOf(3)); $$0.put(ArmorItem.Type.LEGGINGS, Integer.valueOf(6)); $$0.put(ArmorItem.Type.CHESTPLATE, Integer.valueOf(8)); $$0.put(ArmorItem.Type.HELMET, Integer.valueOf(3)); }), 10, SoundEvents.ARMOR_EQUIP_DIAMOND, 2.0F, 0.0F, () -> Ingredient.of(new ItemLike[] { Items.DIAMOND }));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  87 */     TURTLE = new ArmorMaterials("TURTLE", 5, "turtle", 25, (EnumMap<ArmorItem.Type, Integer>)Util.make(new EnumMap<>(ArmorItem.Type.class), $$0 -> { $$0.put(ArmorItem.Type.BOOTS, Integer.valueOf(2)); $$0.put(ArmorItem.Type.LEGGINGS, Integer.valueOf(5)); $$0.put(ArmorItem.Type.CHESTPLATE, Integer.valueOf(6)); $$0.put(ArmorItem.Type.HELMET, Integer.valueOf(2)); }), 9, SoundEvents.ARMOR_EQUIP_TURTLE, 0.0F, 0.0F, () -> Ingredient.of(new ItemLike[] { Items.SCUTE }));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 101 */     NETHERITE = new ArmorMaterials("NETHERITE", 6, "netherite", 37, (EnumMap<ArmorItem.Type, Integer>)Util.make(new EnumMap<>(ArmorItem.Type.class), $$0 -> { $$0.put(ArmorItem.Type.BOOTS, Integer.valueOf(3)); $$0.put(ArmorItem.Type.LEGGINGS, Integer.valueOf(6)); $$0.put(ArmorItem.Type.CHESTPLATE, Integer.valueOf(8)); $$0.put(ArmorItem.Type.HELMET, Integer.valueOf(3)); }), 15, SoundEvents.ARMOR_EQUIP_NETHERITE, 3.0F, 0.1F, () -> Ingredient.of(new ItemLike[] { Items.NETHERITE_INGOT }));
/*     */   }
/*     */   
/*     */   private static final EnumMap<ArmorItem.Type, Integer> HEALTH_FUNCTION_FOR_TYPE;
/*     */   private final String name;
/*     */   private final int durabilityMultiplier;
/*     */   private final EnumMap<ArmorItem.Type, Integer> protectionFunctionForType;
/*     */   private final int enchantmentValue;
/*     */   private final SoundEvent sound;
/*     */   private final float toughness;
/*     */   private final float knockbackResistance;
/*     */   private final LazyLoadedValue<Ingredient> repairIngredient;
/*     */   
/*     */   static {
/* 115 */     CODEC = (Codec<ArmorMaterials>)StringRepresentable.fromEnum(ArmorMaterials::values);
/*     */     
/* 117 */     HEALTH_FUNCTION_FOR_TYPE = (EnumMap<ArmorItem.Type, Integer>)Util.make(new EnumMap<>(ArmorItem.Type.class), $$0 -> {
/*     */           $$0.put(ArmorItem.Type.BOOTS, Integer.valueOf(13));
/*     */           $$0.put(ArmorItem.Type.LEGGINGS, Integer.valueOf(15));
/*     */           $$0.put(ArmorItem.Type.CHESTPLATE, Integer.valueOf(16));
/*     */           $$0.put(ArmorItem.Type.HELMET, Integer.valueOf(11));
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ArmorMaterials(String $$0, int $$1, EnumMap<ArmorItem.Type, Integer> $$2, int $$3, SoundEvent $$4, float $$5, float $$6, Supplier<Ingredient> $$7) {
/* 134 */     this.name = $$0;
/* 135 */     this.durabilityMultiplier = $$1;
/* 136 */     this.protectionFunctionForType = $$2;
/* 137 */     this.enchantmentValue = $$3;
/* 138 */     this.sound = $$4;
/* 139 */     this.toughness = $$5;
/* 140 */     this.knockbackResistance = $$6;
/* 141 */     this.repairIngredient = new LazyLoadedValue($$7);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDurabilityForType(ArmorItem.Type $$0) {
/* 146 */     return ((Integer)HEALTH_FUNCTION_FOR_TYPE.get($$0)).intValue() * this.durabilityMultiplier;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDefenseForType(ArmorItem.Type $$0) {
/* 151 */     return ((Integer)this.protectionFunctionForType.get($$0)).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getEnchantmentValue() {
/* 156 */     return this.enchantmentValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public SoundEvent getEquipSound() {
/* 161 */     return this.sound;
/*     */   }
/*     */ 
/*     */   
/*     */   public Ingredient getRepairIngredient() {
/* 166 */     return (Ingredient)this.repairIngredient.get();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/* 171 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getToughness() {
/* 176 */     return this.toughness;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getKnockbackResistance() {
/* 181 */     return this.knockbackResistance;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSerializedName() {
/* 186 */     return this.name;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\ArmorMaterials.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */