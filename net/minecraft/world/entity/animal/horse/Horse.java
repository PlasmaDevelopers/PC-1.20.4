/*     */ package net.minecraft.world.entity.animal.horse;
/*     */ 
/*     */ import java.util.Objects;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.network.syncher.EntityDataAccessor;
/*     */ import net.minecraft.network.syncher.EntityDataSerializers;
/*     */ import net.minecraft.network.syncher.SynchedEntityData;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.Container;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.entity.AgeableMob;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.EquipmentSlot;
/*     */ import net.minecraft.world.entity.MobSpawnType;
/*     */ import net.minecraft.world.entity.SpawnGroupData;
/*     */ import net.minecraft.world.entity.VariantHolder;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeModifier;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.entity.animal.Animal;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.HorseArmorItem;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.ServerLevelAccessor;
/*     */ import net.minecraft.world.level.block.SoundType;
/*     */ 
/*     */ public class Horse extends AbstractHorse implements VariantHolder<Variant> {
/*  38 */   private static final UUID ARMOR_MODIFIER_UUID = UUID.fromString("556E1665-8B10-40C8-8F9D-CF9B1667F295");
/*     */   
/*  40 */   private static final EntityDataAccessor<Integer> DATA_ID_TYPE_VARIANT = SynchedEntityData.defineId(Horse.class, EntityDataSerializers.INT);
/*     */   
/*     */   public Horse(EntityType<? extends Horse> $$0, Level $$1) {
/*  43 */     super((EntityType)$$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void randomizeAttributes(RandomSource $$0) {
/*  48 */     Objects.requireNonNull($$0); getAttribute(Attributes.MAX_HEALTH).setBaseValue(generateMaxHealth($$0::nextInt));
/*  49 */     Objects.requireNonNull($$0); getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(generateSpeed($$0::nextDouble));
/*  50 */     Objects.requireNonNull($$0); getAttribute(Attributes.JUMP_STRENGTH).setBaseValue(generateJumpStrength($$0::nextDouble));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {
/*  55 */     super.defineSynchedData();
/*     */     
/*  57 */     this.entityData.define(DATA_ID_TYPE_VARIANT, Integer.valueOf(0));
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/*  62 */     super.addAdditionalSaveData($$0);
/*     */     
/*  64 */     $$0.putInt("Variant", getTypeVariant());
/*     */     
/*  66 */     if (!this.inventory.getItem(1).isEmpty()) {
/*  67 */       $$0.put("ArmorItem", (Tag)this.inventory.getItem(1).save(new CompoundTag()));
/*     */     }
/*     */   }
/*     */   
/*     */   public ItemStack getArmor() {
/*  72 */     return getItemBySlot(EquipmentSlot.CHEST);
/*     */   }
/*     */   
/*     */   private void setArmor(ItemStack $$0) {
/*  76 */     setItemSlot(EquipmentSlot.CHEST, $$0);
/*  77 */     setDropChance(EquipmentSlot.CHEST, 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/*  82 */     super.readAdditionalSaveData($$0);
/*     */     
/*  84 */     setTypeVariant($$0.getInt("Variant"));
/*     */     
/*  86 */     if ($$0.contains("ArmorItem", 10)) {
/*  87 */       ItemStack $$1 = ItemStack.of($$0.getCompound("ArmorItem"));
/*  88 */       if (!$$1.isEmpty() && isArmor($$1)) {
/*  89 */         this.inventory.setItem(1, $$1);
/*     */       }
/*     */     } 
/*     */     
/*  93 */     updateContainerEquipment();
/*     */   }
/*     */   
/*     */   private void setTypeVariant(int $$0) {
/*  97 */     this.entityData.set(DATA_ID_TYPE_VARIANT, Integer.valueOf($$0));
/*     */   }
/*     */   
/*     */   private int getTypeVariant() {
/* 101 */     return ((Integer)this.entityData.get(DATA_ID_TYPE_VARIANT)).intValue();
/*     */   }
/*     */   
/*     */   private void setVariantAndMarkings(Variant $$0, Markings $$1) {
/* 105 */     setTypeVariant($$0.getId() & 0xFF | $$1.getId() << 8 & 0xFF00);
/*     */   }
/*     */ 
/*     */   
/*     */   public Variant getVariant() {
/* 110 */     return Variant.byId(getTypeVariant() & 0xFF);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setVariant(Variant $$0) {
/* 115 */     setTypeVariant($$0.getId() & 0xFF | getTypeVariant() & 0xFFFFFF00);
/*     */   }
/*     */   
/*     */   public Markings getMarkings() {
/* 119 */     return Markings.byId((getTypeVariant() & 0xFF00) >> 8);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateContainerEquipment() {
/* 124 */     if ((level()).isClientSide) {
/*     */       return;
/*     */     }
/*     */     
/* 128 */     super.updateContainerEquipment();
/*     */     
/* 130 */     setArmorEquipment(this.inventory.getItem(1));
/*     */     
/* 132 */     setDropChance(EquipmentSlot.CHEST, 0.0F);
/*     */   }
/*     */   
/*     */   private void setArmorEquipment(ItemStack $$0) {
/* 136 */     setArmor($$0);
/*     */     
/* 138 */     if (!(level()).isClientSide) {
/* 139 */       getAttribute(Attributes.ARMOR).removeModifier(ARMOR_MODIFIER_UUID);
/* 140 */       if (isArmor($$0)) {
/* 141 */         int $$1 = ((HorseArmorItem)$$0.getItem()).getProtection();
/* 142 */         if ($$1 != 0) {
/* 143 */           getAttribute(Attributes.ARMOR).addTransientModifier(new AttributeModifier(ARMOR_MODIFIER_UUID, "Horse armor bonus", $$1, AttributeModifier.Operation.ADDITION));
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void containerChanged(Container $$0) {
/* 151 */     ItemStack $$1 = getArmor();
/*     */     
/* 153 */     super.containerChanged($$0);
/*     */     
/* 155 */     ItemStack $$2 = getArmor();
/* 156 */     if (this.tickCount > 20 && isArmor($$2) && $$1 != $$2) {
/* 157 */       playSound(SoundEvents.HORSE_ARMOR, 0.5F, 1.0F);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void playGallopSound(SoundType $$0) {
/* 163 */     super.playGallopSound($$0);
/* 164 */     if (this.random.nextInt(10) == 0) {
/* 165 */       playSound(SoundEvents.HORSE_BREATHE, $$0.getVolume() * 0.6F, $$0.getPitch());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/* 171 */     return SoundEvents.HORSE_AMBIENT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/* 176 */     return SoundEvents.HORSE_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected SoundEvent getEatingSound() {
/* 182 */     return SoundEvents.HORSE_EAT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource $$0) {
/* 187 */     return SoundEvents.HORSE_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAngrySound() {
/* 192 */     return SoundEvents.HORSE_ANGRY;
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult mobInteract(Player $$0, InteractionHand $$1) {
/* 197 */     boolean $$2 = (!isBaby() && isTamed() && $$0.isSecondaryUseActive());
/* 198 */     if (isVehicle() || $$2) {
/* 199 */       return super.mobInteract($$0, $$1);
/*     */     }
/*     */     
/* 202 */     ItemStack $$3 = $$0.getItemInHand($$1);
/*     */     
/* 204 */     if (!$$3.isEmpty()) {
/* 205 */       if (isFood($$3)) {
/* 206 */         return fedFood($$0, $$3);
/*     */       }
/*     */       
/* 209 */       if (!isTamed()) {
/* 210 */         makeMad();
/* 211 */         return InteractionResult.sidedSuccess((level()).isClientSide);
/*     */       } 
/*     */     } 
/* 214 */     return super.mobInteract($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canMate(Animal $$0) {
/* 219 */     if ($$0 == this) {
/* 220 */       return false;
/*     */     }
/*     */     
/* 223 */     if ($$0 instanceof Donkey || $$0 instanceof Horse) {
/* 224 */       return (canParent() && ((AbstractHorse)$$0).canParent());
/*     */     }
/*     */     
/* 227 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public AgeableMob getBreedOffspring(ServerLevel $$0, AgeableMob $$1) {
/* 233 */     if ($$1 instanceof Donkey) {
/* 234 */       Mule $$2 = (Mule)EntityType.MULE.create((Level)$$0);
/* 235 */       if ($$2 != null) {
/* 236 */         setOffspringAttributes($$1, $$2);
/*     */       }
/* 238 */       return (AgeableMob)$$2;
/*     */     } 
/* 240 */     Horse $$3 = (Horse)$$1;
/*     */     
/* 242 */     Horse $$4 = (Horse)EntityType.HORSE.create((Level)$$0);
/* 243 */     if ($$4 != null) {
/*     */       Variant $$8; Markings $$12;
/* 245 */       int $$5 = this.random.nextInt(9);
/* 246 */       if ($$5 < 4) {
/* 247 */         Variant $$6 = getVariant();
/* 248 */       } else if ($$5 < 8) {
/* 249 */         Variant $$7 = $$3.getVariant();
/*     */       } else {
/* 251 */         $$8 = (Variant)Util.getRandom((Object[])Variant.values(), this.random);
/*     */       } 
/*     */ 
/*     */       
/* 255 */       int $$9 = this.random.nextInt(5);
/* 256 */       if ($$9 < 2) {
/* 257 */         Markings $$10 = getMarkings();
/* 258 */       } else if ($$9 < 4) {
/* 259 */         Markings $$11 = $$3.getMarkings();
/*     */       } else {
/* 261 */         $$12 = (Markings)Util.getRandom((Object[])Markings.values(), this.random);
/*     */       } 
/*     */       
/* 264 */       $$4.setVariantAndMarkings($$8, $$12);
/* 265 */       setOffspringAttributes($$1, $$4);
/*     */     } 
/* 267 */     return (AgeableMob)$$4;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canWearArmor() {
/* 272 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isArmor(ItemStack $$0) {
/* 277 */     return $$0.getItem() instanceof HorseArmorItem;
/*     */   }
/*     */   @Nullable
/*     */   public SpawnGroupData finalizeSpawn(ServerLevelAccessor $$0, DifficultyInstance $$1, MobSpawnType $$2, @Nullable SpawnGroupData $$3, @Nullable CompoundTag $$4) {
/*     */     HorseGroupData horseGroupData;
/*     */     Variant $$7;
/* 283 */     RandomSource $$5 = $$0.getRandom();
/*     */     
/* 285 */     if ($$3 instanceof HorseGroupData) {
/* 286 */       Variant $$6 = ((HorseGroupData)$$3).variant;
/*     */     } else {
/* 288 */       $$7 = (Variant)Util.getRandom((Object[])Variant.values(), $$5);
/* 289 */       horseGroupData = new HorseGroupData($$7);
/*     */     } 
/* 291 */     setVariantAndMarkings($$7, (Markings)Util.getRandom((Object[])Markings.values(), $$5));
/*     */     
/* 293 */     return super.finalizeSpawn($$0, $$1, $$2, (SpawnGroupData)horseGroupData, $$4);
/*     */   }
/*     */   
/*     */   public static class HorseGroupData extends AgeableMob.AgeableMobGroupData {
/*     */     public final Variant variant;
/*     */     
/*     */     public HorseGroupData(Variant $$0) {
/* 300 */       super(true);
/* 301 */       this.variant = $$0;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\animal\horse\Horse.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */