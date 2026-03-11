/*     */ package net.minecraft.world.item;
/*     */ import com.google.common.collect.ImmutableMultimap;
/*     */ import com.google.common.collect.Multimap;
/*     */ import java.util.EnumMap;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import java.util.function.Predicate;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.dispenser.BlockSource;
/*     */ import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
/*     */ import net.minecraft.core.dispenser.DispenseItemBehavior;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.entity.EntitySelector;
/*     */ import net.minecraft.world.entity.EquipmentSlot;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.ai.attributes.Attribute;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeModifier;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.DispenserBlock;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ 
/*     */ public class ArmorItem extends Item implements Equipable {
/*     */   private static final EnumMap<Type, UUID> ARMOR_MODIFIER_UUID_PER_TYPE;
/*     */   
/*     */   static {
/*  30 */     ARMOR_MODIFIER_UUID_PER_TYPE = (EnumMap<Type, UUID>)Util.make(new EnumMap<>(Type.class), $$0 -> {
/*     */           $$0.put(Type.BOOTS, UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"));
/*     */           $$0.put(Type.LEGGINGS, UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"));
/*     */           $$0.put(Type.CHESTPLATE, UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"));
/*     */           $$0.put(Type.HELMET, UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150"));
/*     */         });
/*     */   }
/*  37 */   public static final DispenseItemBehavior DISPENSE_ITEM_BEHAVIOR = (DispenseItemBehavior)new DefaultDispenseItemBehavior()
/*     */     {
/*     */       protected ItemStack execute(BlockSource $$0, ItemStack $$1) {
/*  40 */         return ArmorItem.dispenseArmor($$0, $$1) ? $$1 : super.execute($$0, $$1);
/*     */       }
/*     */     };
/*     */   protected final Type type; private final int defense; private final float toughness;
/*     */   public static boolean dispenseArmor(BlockSource $$0, ItemStack $$1) {
/*  45 */     BlockPos $$2 = $$0.pos().relative((Direction)$$0.state().getValue((Property)DispenserBlock.FACING));
/*     */     
/*  47 */     List<LivingEntity> $$3 = $$0.level().getEntitiesOfClass(LivingEntity.class, new AABB($$2), EntitySelector.NO_SPECTATORS.and((Predicate)new EntitySelector.MobCanWearArmorEntitySelector($$1)));
/*     */     
/*  49 */     if ($$3.isEmpty()) {
/*  50 */       return false;
/*     */     }
/*     */     
/*  53 */     LivingEntity $$4 = $$3.get(0);
/*  54 */     EquipmentSlot $$5 = Mob.getEquipmentSlotForItem($$1);
/*     */     
/*  56 */     ItemStack $$6 = $$1.split(1);
/*  57 */     $$4.setItemSlot($$5, $$6);
/*  58 */     if ($$4 instanceof Mob) {
/*  59 */       ((Mob)$$4).setDropChance($$5, 2.0F);
/*  60 */       ((Mob)$$4).setPersistenceRequired();
/*     */     } 
/*  62 */     return true;
/*     */   }
/*     */   protected final float knockbackResistance; protected final ArmorMaterial material; private final Multimap<Attribute, AttributeModifier> defaultModifiers;
/*     */   
/*  66 */   public enum Type { HELMET((String)EquipmentSlot.HEAD, "helmet"),
/*  67 */     CHESTPLATE((String)EquipmentSlot.CHEST, "chestplate"),
/*  68 */     LEGGINGS((String)EquipmentSlot.LEGS, "leggings"),
/*  69 */     BOOTS((String)EquipmentSlot.FEET, "boots");
/*     */     
/*     */     private final EquipmentSlot slot;
/*     */     private final String name;
/*     */     
/*     */     Type(EquipmentSlot $$0, String $$1) {
/*  75 */       this.slot = $$0;
/*  76 */       this.name = $$1;
/*     */     }
/*     */     
/*     */     public EquipmentSlot getSlot() {
/*  80 */       return this.slot;
/*     */     }
/*     */     
/*     */     public String getName() {
/*  84 */       return this.name;
/*     */     } }
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
/*     */   public ArmorItem(ArmorMaterial $$0, Type $$1, Item.Properties $$2) {
/*  97 */     super($$2.defaultDurability($$0.getDurabilityForType($$1)));
/*  98 */     this.material = $$0;
/*  99 */     this.type = $$1;
/* 100 */     this.defense = $$0.getDefenseForType($$1);
/* 101 */     this.toughness = $$0.getToughness();
/* 102 */     this.knockbackResistance = $$0.getKnockbackResistance();
/*     */     
/* 104 */     DispenserBlock.registerBehavior(this, DISPENSE_ITEM_BEHAVIOR);
/*     */     
/* 106 */     ImmutableMultimap.Builder<Attribute, AttributeModifier> $$3 = ImmutableMultimap.builder();
/*     */ 
/*     */     
/* 109 */     UUID $$4 = ARMOR_MODIFIER_UUID_PER_TYPE.get($$1);
/* 110 */     $$3.put(Attributes.ARMOR, new AttributeModifier($$4, "Armor modifier", this.defense, AttributeModifier.Operation.ADDITION));
/* 111 */     $$3.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier($$4, "Armor toughness", this.toughness, AttributeModifier.Operation.ADDITION));
/*     */     
/* 113 */     if ($$0 == ArmorMaterials.NETHERITE) {
/* 114 */       $$3.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier($$4, "Armor knockback resistance", this.knockbackResistance, AttributeModifier.Operation.ADDITION));
/*     */     }
/*     */     
/* 117 */     this.defaultModifiers = (Multimap<Attribute, AttributeModifier>)$$3.build();
/*     */   }
/*     */   
/*     */   public Type getType() {
/* 121 */     return this.type;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getEnchantmentValue() {
/* 126 */     return this.material.getEnchantmentValue();
/*     */   }
/*     */   
/*     */   public ArmorMaterial getMaterial() {
/* 130 */     return this.material;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isValidRepairItem(ItemStack $$0, ItemStack $$1) {
/* 135 */     return (this.material.getRepairIngredient().test($$1) || super.isValidRepairItem($$0, $$1));
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResultHolder<ItemStack> use(Level $$0, Player $$1, InteractionHand $$2) {
/* 140 */     return swapWithEquipmentSlot(this, $$0, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot $$0) {
/* 145 */     if ($$0 == this.type.getSlot()) {
/* 146 */       return this.defaultModifiers;
/*     */     }
/* 148 */     return super.getDefaultAttributeModifiers($$0);
/*     */   }
/*     */   
/*     */   public int getDefense() {
/* 152 */     return this.defense;
/*     */   }
/*     */   
/*     */   public float getToughness() {
/* 156 */     return this.toughness;
/*     */   }
/*     */ 
/*     */   
/*     */   public EquipmentSlot getEquipmentSlot() {
/* 161 */     return this.type.getSlot();
/*     */   }
/*     */ 
/*     */   
/*     */   public SoundEvent getEquipSound() {
/* 166 */     return getMaterial().getEquipSound();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\ArmorItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */