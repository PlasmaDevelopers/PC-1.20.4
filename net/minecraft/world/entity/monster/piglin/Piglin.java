/*     */ package net.minecraft.world.entity.monster.piglin;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.mojang.serialization.Dynamic;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.syncher.EntityDataAccessor;
/*     */ import net.minecraft.network.syncher.EntityDataSerializers;
/*     */ import net.minecraft.network.syncher.SynchedEntityData;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.util.VisibleForDebug;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.SimpleContainer;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityDimensions;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.EquipmentSlot;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.MobSpawnType;
/*     */ import net.minecraft.world.entity.Pose;
/*     */ import net.minecraft.world.entity.SpawnGroupData;
/*     */ import net.minecraft.world.entity.ai.Brain;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeInstance;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeModifier;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*     */ import net.minecraft.world.entity.ai.sensing.Sensor;
/*     */ import net.minecraft.world.entity.ai.sensing.SensorType;
/*     */ import net.minecraft.world.entity.item.ItemEntity;
/*     */ import net.minecraft.world.entity.monster.Creeper;
/*     */ import net.minecraft.world.entity.monster.CrossbowAttackMob;
/*     */ import net.minecraft.world.entity.monster.Monster;
/*     */ import net.minecraft.world.entity.npc.InventoryCarrier;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.entity.projectile.Projectile;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.item.ProjectileWeaponItem;
/*     */ import net.minecraft.world.item.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.world.level.GameRules;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.ServerLevelAccessor;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ 
/*     */ public class Piglin
/*     */   extends AbstractPiglin
/*     */   implements CrossbowAttackMob, InventoryCarrier
/*     */ {
/*  64 */   private static final EntityDataAccessor<Boolean> DATA_BABY_ID = SynchedEntityData.defineId(Piglin.class, EntityDataSerializers.BOOLEAN);
/*  65 */   private static final EntityDataAccessor<Boolean> DATA_IS_CHARGING_CROSSBOW = SynchedEntityData.defineId(Piglin.class, EntityDataSerializers.BOOLEAN);
/*  66 */   private static final EntityDataAccessor<Boolean> DATA_IS_DANCING = SynchedEntityData.defineId(Piglin.class, EntityDataSerializers.BOOLEAN);
/*     */   
/*  68 */   private static final UUID SPEED_MODIFIER_BABY_UUID = UUID.fromString("766bfa64-11f3-11ea-8d71-362b9e155667");
/*  69 */   private static final AttributeModifier SPEED_MODIFIER_BABY = new AttributeModifier(SPEED_MODIFIER_BABY_UUID, "Baby speed boost", 0.20000000298023224D, AttributeModifier.Operation.MULTIPLY_BASE);
/*     */   
/*     */   private static final int MAX_HEALTH = 16;
/*     */   
/*     */   private static final float MOVEMENT_SPEED_WHEN_FIGHTING = 0.35F;
/*     */   private static final int ATTACK_DAMAGE = 5;
/*     */   private static final float CROSSBOW_POWER = 1.6F;
/*     */   private static final float CHANCE_OF_WEARING_EACH_ARMOUR_ITEM = 0.1F;
/*     */   private static final int MAX_PASSENGERS_ON_ONE_HOGLIN = 3;
/*     */   private static final float PROBABILITY_OF_SPAWNING_AS_BABY = 0.2F;
/*     */   private static final float BABY_EYE_HEIGHT_ADJUSTMENT = 0.82F;
/*     */   private static final double PROBABILITY_OF_SPAWNING_WITH_CROSSBOW_INSTEAD_OF_SWORD = 0.5D;
/*  81 */   private final SimpleContainer inventory = new SimpleContainer(8);
/*     */   
/*     */   private boolean cannotHunt;
/*  84 */   protected static final ImmutableList<SensorType<? extends Sensor<? super Piglin>>> SENSOR_TYPES = ImmutableList.of(SensorType.NEAREST_LIVING_ENTITIES, SensorType.NEAREST_PLAYERS, SensorType.NEAREST_ITEMS, SensorType.HURT_BY, SensorType.PIGLIN_SPECIFIC_SENSOR);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  92 */   protected static final ImmutableList<MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(MemoryModuleType.LOOK_TARGET, MemoryModuleType.DOORS_TO_CLOSE, MemoryModuleType.NEAREST_LIVING_ENTITIES, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_ADULT_PIGLINS, MemoryModuleType.NEARBY_ADULT_PIGLINS, MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM, MemoryModuleType.ITEM_PICKUP_COOLDOWN_TICKS, MemoryModuleType.HURT_BY, MemoryModuleType.HURT_BY_ENTITY, (Object[])new MemoryModuleType[] { MemoryModuleType.WALK_TARGET, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.ATTACK_TARGET, MemoryModuleType.ATTACK_COOLING_DOWN, MemoryModuleType.INTERACTION_TARGET, MemoryModuleType.PATH, MemoryModuleType.ANGRY_AT, MemoryModuleType.UNIVERSAL_ANGER, MemoryModuleType.AVOID_TARGET, MemoryModuleType.ADMIRING_ITEM, MemoryModuleType.TIME_TRYING_TO_REACH_ADMIRE_ITEM, MemoryModuleType.ADMIRING_DISABLED, MemoryModuleType.DISABLE_WALK_TO_ADMIRE_ITEM, MemoryModuleType.CELEBRATE_LOCATION, MemoryModuleType.DANCING, MemoryModuleType.HUNTED_RECENTLY, MemoryModuleType.NEAREST_VISIBLE_BABY_HOGLIN, MemoryModuleType.NEAREST_VISIBLE_NEMESIS, MemoryModuleType.NEAREST_VISIBLE_ZOMBIFIED, MemoryModuleType.RIDE_TARGET, MemoryModuleType.VISIBLE_ADULT_PIGLIN_COUNT, MemoryModuleType.VISIBLE_ADULT_HOGLIN_COUNT, MemoryModuleType.NEAREST_VISIBLE_HUNTABLE_HOGLIN, MemoryModuleType.NEAREST_TARGETABLE_PLAYER_NOT_WEARING_GOLD, MemoryModuleType.NEAREST_PLAYER_HOLDING_WANTED_ITEM, MemoryModuleType.ATE_RECENTLY, MemoryModuleType.NEAREST_REPELLENT });
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
/*     */ 
/*     */   
/*     */   public Piglin(EntityType<? extends AbstractPiglin> $$0, Level $$1) {
/* 135 */     super($$0, $$1);
/* 136 */     this.xpReward = 5;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/* 141 */     super.addAdditionalSaveData($$0);
/*     */     
/* 143 */     if (isBaby()) {
/* 144 */       $$0.putBoolean("IsBaby", true);
/*     */     }
/* 146 */     if (this.cannotHunt) {
/* 147 */       $$0.putBoolean("CannotHunt", true);
/*     */     }
/* 149 */     writeInventoryToTag($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/* 154 */     super.readAdditionalSaveData($$0);
/*     */     
/* 156 */     setBaby($$0.getBoolean("IsBaby"));
/* 157 */     setCannotHunt($$0.getBoolean("CannotHunt"));
/* 158 */     readInventoryFromTag($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   @VisibleForDebug
/*     */   public SimpleContainer getInventory() {
/* 164 */     return this.inventory;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void dropCustomDeathLoot(DamageSource $$0, int $$1, boolean $$2) {
/* 169 */     super.dropCustomDeathLoot($$0, $$1, $$2);
/*     */     
/* 171 */     Entity $$3 = $$0.getEntity();
/* 172 */     if ($$3 instanceof Creeper) { Creeper $$4 = (Creeper)$$3;
/* 173 */       if ($$4.canDropMobsSkull()) {
/* 174 */         ItemStack $$5 = new ItemStack((ItemLike)Items.PIGLIN_HEAD);
/* 175 */         $$4.increaseDroppedSkulls();
/* 176 */         spawnAtLocation($$5);
/*     */       }  }
/*     */ 
/*     */     
/* 180 */     this.inventory.removeAllItems().forEach(this::spawnAtLocation);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ItemStack addToInventory(ItemStack $$0) {
/* 187 */     return this.inventory.addItem($$0);
/*     */   }
/*     */   
/*     */   protected boolean canAddToInventory(ItemStack $$0) {
/* 191 */     return this.inventory.canAddItem($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {
/* 196 */     super.defineSynchedData();
/* 197 */     this.entityData.define(DATA_BABY_ID, Boolean.valueOf(false));
/* 198 */     this.entityData.define(DATA_IS_CHARGING_CROSSBOW, Boolean.valueOf(false));
/* 199 */     this.entityData.define(DATA_IS_DANCING, Boolean.valueOf(false));
/*     */   }
/*     */ 
/*     */   
/*     */   public void onSyncedDataUpdated(EntityDataAccessor<?> $$0) {
/* 204 */     super.onSyncedDataUpdated($$0);
/* 205 */     if (DATA_BABY_ID.equals($$0)) {
/* 206 */       refreshDimensions();
/*     */     }
/*     */   }
/*     */   
/*     */   public static AttributeSupplier.Builder createAttributes() {
/* 211 */     return Monster.createMonsterAttributes()
/* 212 */       .add(Attributes.MAX_HEALTH, 16.0D)
/* 213 */       .add(Attributes.MOVEMENT_SPEED, 0.3499999940395355D)
/* 214 */       .add(Attributes.ATTACK_DAMAGE, 5.0D);
/*     */   }
/*     */   
/*     */   public static boolean checkPiglinSpawnRules(EntityType<Piglin> $$0, LevelAccessor $$1, MobSpawnType $$2, BlockPos $$3, RandomSource $$4) {
/* 218 */     return !$$1.getBlockState($$3.below()).is(Blocks.NETHER_WART_BLOCK);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public SpawnGroupData finalizeSpawn(ServerLevelAccessor $$0, DifficultyInstance $$1, MobSpawnType $$2, @Nullable SpawnGroupData $$3, @Nullable CompoundTag $$4) {
/* 224 */     RandomSource $$5 = $$0.getRandom();
/* 225 */     if ($$2 != MobSpawnType.STRUCTURE) {
/* 226 */       if ($$5.nextFloat() < 0.2F) {
/* 227 */         setBaby(true);
/* 228 */       } else if (isAdult()) {
/* 229 */         setItemSlot(EquipmentSlot.MAINHAND, createSpawnWeapon());
/*     */       } 
/*     */     }
/* 232 */     PiglinAi.initMemories(this, $$0.getRandom());
/* 233 */     populateDefaultEquipmentSlots($$5, $$1);
/* 234 */     populateDefaultEquipmentEnchantments($$5, $$1);
/* 235 */     return super.finalizeSpawn($$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean shouldDespawnInPeaceful() {
/* 240 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean removeWhenFarAway(double $$0) {
/* 245 */     return !isPersistenceRequired();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void populateDefaultEquipmentSlots(RandomSource $$0, DifficultyInstance $$1) {
/* 250 */     if (isAdult()) {
/* 251 */       maybeWearArmor(EquipmentSlot.HEAD, new ItemStack((ItemLike)Items.GOLDEN_HELMET), $$0);
/* 252 */       maybeWearArmor(EquipmentSlot.CHEST, new ItemStack((ItemLike)Items.GOLDEN_CHESTPLATE), $$0);
/* 253 */       maybeWearArmor(EquipmentSlot.LEGS, new ItemStack((ItemLike)Items.GOLDEN_LEGGINGS), $$0);
/* 254 */       maybeWearArmor(EquipmentSlot.FEET, new ItemStack((ItemLike)Items.GOLDEN_BOOTS), $$0);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void maybeWearArmor(EquipmentSlot $$0, ItemStack $$1, RandomSource $$2) {
/* 259 */     if ($$2.nextFloat() < 0.1F) {
/* 260 */       setItemSlot($$0, $$1);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected Brain.Provider<Piglin> brainProvider() {
/* 266 */     return Brain.provider((Collection)MEMORY_TYPES, (Collection)SENSOR_TYPES);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Brain<?> makeBrain(Dynamic<?> $$0) {
/* 271 */     return PiglinAi.makeBrain(this, brainProvider().makeBrain($$0));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Brain<Piglin> getBrain() {
/* 277 */     return super.getBrain();
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult mobInteract(Player $$0, InteractionHand $$1) {
/* 282 */     InteractionResult $$2 = super.mobInteract($$0, $$1);
/* 283 */     if ($$2.consumesAction()) {
/* 284 */       return $$2;
/*     */     }
/* 286 */     if ((level()).isClientSide) {
/* 287 */       boolean $$3 = (PiglinAi.canAdmire(this, $$0.getItemInHand($$1)) && getArmPose() != PiglinArmPose.ADMIRING_ITEM);
/* 288 */       return $$3 ? InteractionResult.SUCCESS : InteractionResult.PASS;
/*     */     } 
/*     */     
/* 291 */     return PiglinAi.mobInteract(this, $$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getStandingEyeHeight(Pose $$0, EntityDimensions $$1) {
/* 296 */     float $$2 = super.getStandingEyeHeight($$0, $$1);
/* 297 */     return isBaby() ? ($$2 - 0.82F) : $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBaby(boolean $$0) {
/* 302 */     getEntityData().set(DATA_BABY_ID, Boolean.valueOf($$0));
/*     */     
/* 304 */     if (!(level()).isClientSide) {
/* 305 */       AttributeInstance $$1 = getAttribute(Attributes.MOVEMENT_SPEED);
/* 306 */       $$1.removeModifier(SPEED_MODIFIER_BABY.getId());
/* 307 */       if ($$0) {
/* 308 */         $$1.addTransientModifier(SPEED_MODIFIER_BABY);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBaby() {
/* 315 */     return ((Boolean)getEntityData().get(DATA_BABY_ID)).booleanValue();
/*     */   }
/*     */   
/*     */   private void setCannotHunt(boolean $$0) {
/* 319 */     this.cannotHunt = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canHunt() {
/* 324 */     return !this.cannotHunt;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void customServerAiStep() {
/* 329 */     level().getProfiler().push("piglinBrain");
/* 330 */     getBrain().tick((ServerLevel)level(), (LivingEntity)this);
/* 331 */     level().getProfiler().pop();
/*     */     
/* 333 */     PiglinAi.updateActivity(this);
/*     */     
/* 335 */     super.customServerAiStep();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getExperienceReward() {
/* 340 */     return this.xpReward;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void finishConversion(ServerLevel $$0) {
/* 345 */     PiglinAi.cancelAdmiring(this);
/* 346 */     this.inventory.removeAllItems().forEach(this::spawnAtLocation);
/* 347 */     super.finishConversion($$0);
/*     */   }
/*     */   
/*     */   private ItemStack createSpawnWeapon() {
/* 351 */     if (this.random.nextFloat() < 0.5D) {
/* 352 */       return new ItemStack((ItemLike)Items.CROSSBOW);
/*     */     }
/* 354 */     return new ItemStack((ItemLike)Items.GOLDEN_SWORD);
/*     */   }
/*     */   
/*     */   private boolean isChargingCrossbow() {
/* 358 */     return ((Boolean)this.entityData.get(DATA_IS_CHARGING_CROSSBOW)).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setChargingCrossbow(boolean $$0) {
/* 363 */     this.entityData.set(DATA_IS_CHARGING_CROSSBOW, Boolean.valueOf($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public void onCrossbowAttackPerformed() {
/* 368 */     this.noActionTime = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public PiglinArmPose getArmPose() {
/* 374 */     if (isDancing())
/* 375 */       return PiglinArmPose.DANCING; 
/* 376 */     if (PiglinAi.isLovedItem(getOffhandItem()))
/* 377 */       return PiglinArmPose.ADMIRING_ITEM; 
/* 378 */     if (isAggressive() && isHoldingMeleeWeapon())
/* 379 */       return PiglinArmPose.ATTACKING_WITH_MELEE_WEAPON; 
/* 380 */     if (isChargingCrossbow())
/* 381 */       return PiglinArmPose.CROSSBOW_CHARGE; 
/* 382 */     if (isAggressive() && isHolding(Items.CROSSBOW)) {
/* 383 */       return PiglinArmPose.CROSSBOW_HOLD;
/*     */     }
/* 385 */     return PiglinArmPose.DEFAULT;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDancing() {
/* 390 */     return ((Boolean)this.entityData.get(DATA_IS_DANCING)).booleanValue();
/*     */   }
/*     */   
/*     */   public void setDancing(boolean $$0) {
/* 394 */     this.entityData.set(DATA_IS_DANCING, Boolean.valueOf($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hurt(DamageSource $$0, float $$1) {
/* 399 */     boolean $$2 = super.hurt($$0, $$1);
/* 400 */     if ((level()).isClientSide) {
/* 401 */       return false;
/*     */     }
/* 403 */     if ($$2 && $$0.getEntity() instanceof LivingEntity) {
/* 404 */       PiglinAi.wasHurtBy(this, (LivingEntity)$$0.getEntity());
/*     */     }
/* 406 */     return $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void performRangedAttack(LivingEntity $$0, float $$1) {
/* 411 */     performCrossbowAttack((LivingEntity)this, 1.6F);
/*     */   }
/*     */ 
/*     */   
/*     */   public void shootCrossbowProjectile(LivingEntity $$0, ItemStack $$1, Projectile $$2, float $$3) {
/* 416 */     shootCrossbowProjectile((LivingEntity)this, $$0, $$2, $$3, 1.6F);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canFireProjectileWeapon(ProjectileWeaponItem $$0) {
/* 421 */     return ($$0 == Items.CROSSBOW);
/*     */   }
/*     */   
/*     */   protected void holdInMainHand(ItemStack $$0) {
/* 425 */     setItemSlotAndDropWhenKilled(EquipmentSlot.MAINHAND, $$0);
/*     */   }
/*     */   
/*     */   protected void holdInOffHand(ItemStack $$0) {
/* 429 */     if ($$0.is(PiglinAi.BARTERING_ITEM)) {
/*     */       
/* 431 */       setItemSlot(EquipmentSlot.OFFHAND, $$0);
/* 432 */       setGuaranteedDrop(EquipmentSlot.OFFHAND);
/*     */     } else {
/* 434 */       setItemSlotAndDropWhenKilled(EquipmentSlot.OFFHAND, $$0);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean wantsToPickUp(ItemStack $$0) {
/* 440 */     return (level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING) && canPickUpLoot() && PiglinAi.wantsToPickup(this, $$0));
/*     */   }
/*     */   
/*     */   protected boolean canReplaceCurrentItem(ItemStack $$0) {
/* 444 */     EquipmentSlot $$1 = Mob.getEquipmentSlotForItem($$0);
/* 445 */     ItemStack $$2 = getItemBySlot($$1);
/* 446 */     return canReplaceCurrentItem($$0, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canReplaceCurrentItem(ItemStack $$0, ItemStack $$1) {
/* 451 */     if (EnchantmentHelper.hasBindingCurse($$1)) {
/* 452 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 457 */     boolean $$2 = (PiglinAi.isLovedItem($$0) || $$0.is(Items.CROSSBOW));
/* 458 */     boolean $$3 = (PiglinAi.isLovedItem($$1) || $$1.is(Items.CROSSBOW));
/*     */ 
/*     */ 
/*     */     
/* 462 */     if ($$2 && !$$3) {
/* 463 */       return true;
/*     */     }
/* 465 */     if (!$$2 && $$3) {
/* 466 */       return false;
/*     */     }
/* 468 */     if (isAdult() && !$$0.is(Items.CROSSBOW) && $$1.is(Items.CROSSBOW))
/*     */     {
/* 470 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 474 */     return super.canReplaceCurrentItem($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void pickUpItem(ItemEntity $$0) {
/* 479 */     onItemPickup($$0);
/* 480 */     PiglinAi.pickUpItem(this, $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean startRiding(Entity $$0, boolean $$1) {
/* 485 */     if (isBaby() && $$0.getType() == EntityType.HOGLIN) {
/* 486 */       $$0 = getTopPassenger($$0, 3);
/*     */     }
/* 488 */     return super.startRiding($$0, $$1);
/*     */   }
/*     */   
/*     */   private Entity getTopPassenger(Entity $$0, int $$1) {
/* 492 */     List<Entity> $$2 = $$0.getPassengers();
/* 493 */     if ($$1 == 1 || $$2.isEmpty()) {
/* 494 */       return $$0;
/*     */     }
/* 496 */     return getTopPassenger($$2.get(0), $$1 - 1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/* 502 */     if ((level()).isClientSide) {
/* 503 */       return null;
/*     */     }
/* 505 */     return PiglinAi.getSoundForCurrentActivity(this).orElse(null);
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource $$0) {
/* 510 */     return SoundEvents.PIGLIN_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/* 515 */     return SoundEvents.PIGLIN_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void playStepSound(BlockPos $$0, BlockState $$1) {
/* 520 */     playSound(SoundEvents.PIGLIN_STEP, 0.15F, 1.0F);
/*     */   }
/*     */   
/*     */   protected void playSoundEvent(SoundEvent $$0) {
/* 524 */     playSound($$0, getSoundVolume(), getVoicePitch());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void playConvertedSound() {
/* 529 */     playSoundEvent(SoundEvents.PIGLIN_CONVERTED_TO_ZOMBIFIED);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\piglin\Piglin.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */