/*     */ package net.minecraft.world.entity.animal.allay;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.serialization.Dynamic;
/*     */ import com.mojang.serialization.DynamicOps;
/*     */ import java.util.Collection;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.UUID;
/*     */ import java.util.function.BiConsumer;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.GlobalPos;
/*     */ import net.minecraft.core.Position;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.NbtOps;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.network.protocol.game.DebugPackets;
/*     */ import net.minecraft.network.syncher.EntityDataAccessor;
/*     */ import net.minecraft.network.syncher.EntityDataSerializers;
/*     */ import net.minecraft.network.syncher.SynchedEntityData;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.tags.GameEventTags;
/*     */ import net.minecraft.util.Mth;
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
/*     */ import net.minecraft.world.entity.MoverType;
/*     */ import net.minecraft.world.entity.PathfinderMob;
/*     */ import net.minecraft.world.entity.Pose;
/*     */ import net.minecraft.world.entity.ai.Brain;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.entity.ai.control.MoveControl;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryStatus;
/*     */ import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
/*     */ import net.minecraft.world.entity.ai.navigation.PathNavigation;
/*     */ import net.minecraft.world.entity.ai.sensing.Sensor;
/*     */ import net.minecraft.world.entity.ai.sensing.SensorType;
/*     */ import net.minecraft.world.entity.item.ItemEntity;
/*     */ import net.minecraft.world.entity.npc.InventoryCarrier;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.item.crafting.Ingredient;
/*     */ import net.minecraft.world.item.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.world.level.GameRules;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.gameevent.DynamicGameEventListener;
/*     */ import net.minecraft.world.level.gameevent.EntityPositionSource;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.level.gameevent.GameEventListener;
/*     */ import net.minecraft.world.level.gameevent.PositionSource;
/*     */ import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class Allay extends PathfinderMob implements InventoryCarrier, VibrationSystem {
/*  76 */   private static final Logger LOGGER = LogUtils.getLogger();
/*  77 */   private static final Vec3i ITEM_PICKUP_REACH = new Vec3i(1, 1, 1);
/*     */   private static final int LIFTING_ITEM_ANIMATION_DURATION = 5;
/*     */   private static final float DANCING_LOOP_DURATION = 55.0F;
/*     */   private static final float SPINNING_ANIMATION_DURATION = 15.0F;
/*  81 */   private static final Ingredient DUPLICATION_ITEM = Ingredient.of(new ItemLike[] { (ItemLike)Items.AMETHYST_SHARD });
/*     */   
/*     */   private static final int DUPLICATION_COOLDOWN_TICKS = 6000;
/*     */   private static final int NUM_OF_DUPLICATION_HEARTS = 3;
/*  85 */   private static final EntityDataAccessor<Boolean> DATA_DANCING = SynchedEntityData.defineId(Allay.class, EntityDataSerializers.BOOLEAN);
/*  86 */   private static final EntityDataAccessor<Boolean> DATA_CAN_DUPLICATE = SynchedEntityData.defineId(Allay.class, EntityDataSerializers.BOOLEAN);
/*     */   
/*  88 */   protected static final ImmutableList<SensorType<? extends Sensor<? super Allay>>> SENSOR_TYPES = ImmutableList.of(SensorType.NEAREST_LIVING_ENTITIES, SensorType.NEAREST_PLAYERS, SensorType.HURT_BY, SensorType.NEAREST_ITEMS);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  95 */   protected static final ImmutableList<MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(MemoryModuleType.PATH, MemoryModuleType.LOOK_TARGET, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, MemoryModuleType.WALK_TARGET, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.HURT_BY, MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM, MemoryModuleType.LIKED_PLAYER, MemoryModuleType.LIKED_NOTEBLOCK_POSITION, MemoryModuleType.LIKED_NOTEBLOCK_COOLDOWN_TICKS, MemoryModuleType.ITEM_PICKUP_COOLDOWN_TICKS, MemoryModuleType.IS_PANICKING, (Object[])new MemoryModuleType[0]);
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
/* 110 */   public static final ImmutableList<Float> THROW_SOUND_PITCHES = ImmutableList.of(
/* 111 */       Float.valueOf(0.5625F), 
/* 112 */       Float.valueOf(0.625F), 
/* 113 */       Float.valueOf(0.75F), 
/* 114 */       Float.valueOf(0.9375F), 
/* 115 */       Float.valueOf(1.0F), 
/* 116 */       Float.valueOf(1.0F), 
/* 117 */       Float.valueOf(1.125F), 
/* 118 */       Float.valueOf(1.25F), 
/* 119 */       Float.valueOf(1.5F), 
/* 120 */       Float.valueOf(1.875F), 
/* 121 */       Float.valueOf(2.0F), 
/* 122 */       Float.valueOf(2.25F), (Object[])new Float[] {
/* 123 */         Float.valueOf(2.5F), 
/* 124 */         Float.valueOf(3.0F), 
/* 125 */         Float.valueOf(3.75F), 
/* 126 */         Float.valueOf(4.0F)
/*     */       });
/*     */   
/*     */   private final DynamicGameEventListener<VibrationSystem.Listener> dynamicVibrationListener;
/*     */   
/*     */   private VibrationSystem.Data vibrationData;
/*     */   
/*     */   private final VibrationSystem.User vibrationUser;
/*     */   private final DynamicGameEventListener<JukeboxListener> dynamicJukeboxListener;
/* 135 */   private final SimpleContainer inventory = new SimpleContainer(1);
/*     */   
/*     */   @Nullable
/*     */   private BlockPos jukeboxPos;
/*     */   private long duplicationCooldown;
/*     */   private float holdingItemAnimationTicks;
/*     */   private float holdingItemAnimationTicks0;
/*     */   private float dancingAnimationTicks;
/*     */   private float spinningAnimationTicks;
/*     */   private float spinningAnimationTicks0;
/*     */   
/*     */   public Allay(EntityType<? extends Allay> $$0, Level $$1) {
/* 147 */     super($$0, $$1);
/* 148 */     this.moveControl = (MoveControl)new FlyingMoveControl((Mob)this, 20, true);
/* 149 */     setCanPickUpLoot(canPickUpLoot());
/*     */     
/* 151 */     this.vibrationUser = new VibrationUser();
/* 152 */     this.vibrationData = new VibrationSystem.Data();
/* 153 */     this.dynamicVibrationListener = new DynamicGameEventListener((GameEventListener)new VibrationSystem.Listener(this));
/* 154 */     this.dynamicJukeboxListener = new DynamicGameEventListener(new JukeboxListener(this.vibrationUser.getPositionSource(), GameEvent.JUKEBOX_PLAY.getNotificationRadius()));
/*     */   }
/*     */ 
/*     */   
/*     */   protected Brain.Provider<Allay> brainProvider() {
/* 159 */     return Brain.provider((Collection)MEMORY_TYPES, (Collection)SENSOR_TYPES);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Brain<?> makeBrain(Dynamic<?> $$0) {
/* 164 */     return AllayAi.makeBrain(brainProvider().makeBrain($$0));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Brain<Allay> getBrain() {
/* 170 */     return super.getBrain();
/*     */   }
/*     */   
/*     */   public static AttributeSupplier.Builder createAttributes() {
/* 174 */     return Mob.createMobAttributes()
/* 175 */       .add(Attributes.MAX_HEALTH, 20.0D)
/* 176 */       .add(Attributes.FLYING_SPEED, 0.10000000149011612D)
/* 177 */       .add(Attributes.MOVEMENT_SPEED, 0.10000000149011612D)
/* 178 */       .add(Attributes.ATTACK_DAMAGE, 2.0D)
/* 179 */       .add(Attributes.FOLLOW_RANGE, 48.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   protected PathNavigation createNavigation(Level $$0) {
/* 184 */     FlyingPathNavigation $$1 = new FlyingPathNavigation((Mob)this, $$0);
/* 185 */     $$1.setCanOpenDoors(false);
/* 186 */     $$1.setCanFloat(true);
/* 187 */     $$1.setCanPassDoors(true);
/* 188 */     return (PathNavigation)$$1;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {
/* 193 */     super.defineSynchedData();
/* 194 */     this.entityData.define(DATA_DANCING, Boolean.valueOf(false));
/* 195 */     this.entityData.define(DATA_CAN_DUPLICATE, Boolean.valueOf(true));
/*     */   }
/*     */ 
/*     */   
/*     */   public void travel(Vec3 $$0) {
/* 200 */     if (isControlledByLocalInstance()) {
/* 201 */       if (isInWater()) {
/* 202 */         moveRelative(0.02F, $$0);
/* 203 */         move(MoverType.SELF, getDeltaMovement());
/* 204 */         setDeltaMovement(getDeltaMovement().scale(0.800000011920929D));
/* 205 */       } else if (isInLava()) {
/* 206 */         moveRelative(0.02F, $$0);
/* 207 */         move(MoverType.SELF, getDeltaMovement());
/* 208 */         setDeltaMovement(getDeltaMovement().scale(0.5D));
/*     */       } else {
/* 210 */         moveRelative(getSpeed(), $$0);
/* 211 */         move(MoverType.SELF, getDeltaMovement());
/*     */         
/* 213 */         setDeltaMovement(getDeltaMovement().scale(0.9100000262260437D));
/*     */       } 
/*     */     }
/* 216 */     calculateEntityAnimation(false);
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getStandingEyeHeight(Pose $$0, EntityDimensions $$1) {
/* 221 */     return $$1.height * 0.6F;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hurt(DamageSource $$0, float $$1) {
/* 226 */     Entity entity = $$0.getEntity(); if (entity instanceof Player) { Player $$2 = (Player)entity;
/* 227 */       Optional<UUID> $$3 = getBrain().getMemory(MemoryModuleType.LIKED_PLAYER);
/* 228 */       if ($$3.isPresent() && $$2.getUUID().equals($$3.get())) {
/* 229 */         return false;
/*     */       } }
/*     */     
/* 232 */     return super.hurt($$0, $$1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void playStepSound(BlockPos $$0, BlockState $$1) {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void checkFallDamage(double $$0, boolean $$1, BlockState $$2, BlockPos $$3) {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/* 247 */     return hasItemInSlot(EquipmentSlot.MAINHAND) ? SoundEvents.ALLAY_AMBIENT_WITH_ITEM : SoundEvents.ALLAY_AMBIENT_WITHOUT_ITEM;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource $$0) {
/* 252 */     return SoundEvents.ALLAY_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/* 257 */     return SoundEvents.ALLAY_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getSoundVolume() {
/* 262 */     return 0.4F;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void customServerAiStep() {
/* 267 */     level().getProfiler().push("allayBrain");
/* 268 */     getBrain().tick((ServerLevel)level(), (LivingEntity)this);
/* 269 */     level().getProfiler().pop();
/*     */     
/* 271 */     level().getProfiler().push("allayActivityUpdate");
/* 272 */     AllayAi.updateActivity(this);
/* 273 */     level().getProfiler().pop();
/*     */     
/* 275 */     super.customServerAiStep();
/*     */   }
/*     */ 
/*     */   
/*     */   public void aiStep() {
/* 280 */     super.aiStep();
/*     */     
/* 282 */     if (!(level()).isClientSide && isAlive() && this.tickCount % 10 == 0) {
/* 283 */       heal(1.0F);
/*     */     }
/*     */     
/* 286 */     if (isDancing() && shouldStopDancing() && this.tickCount % 20 == 0) {
/* 287 */       setDancing(false);
/* 288 */       this.jukeboxPos = null;
/*     */     } 
/* 290 */     updateDuplicationCooldown();
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 295 */     super.tick();
/*     */     
/* 297 */     if ((level()).isClientSide) {
/* 298 */       this.holdingItemAnimationTicks0 = this.holdingItemAnimationTicks;
/* 299 */       if (hasItemInHand()) {
/* 300 */         this.holdingItemAnimationTicks = Mth.clamp(this.holdingItemAnimationTicks + 1.0F, 0.0F, 5.0F);
/*     */       } else {
/* 302 */         this.holdingItemAnimationTicks = Mth.clamp(this.holdingItemAnimationTicks - 1.0F, 0.0F, 5.0F);
/*     */       } 
/*     */       
/* 305 */       if (isDancing()) {
/* 306 */         this.dancingAnimationTicks++;
/* 307 */         this.spinningAnimationTicks0 = this.spinningAnimationTicks;
/* 308 */         if (isSpinning()) {
/* 309 */           this.spinningAnimationTicks++;
/*     */         } else {
/* 311 */           this.spinningAnimationTicks--;
/*     */         } 
/* 313 */         this.spinningAnimationTicks = Mth.clamp(this.spinningAnimationTicks, 0.0F, 15.0F);
/*     */       } else {
/* 315 */         this.dancingAnimationTicks = 0.0F;
/* 316 */         this.spinningAnimationTicks = 0.0F;
/* 317 */         this.spinningAnimationTicks0 = 0.0F;
/*     */       } 
/*     */     } else {
/* 320 */       VibrationSystem.Ticker.tick(level(), this.vibrationData, this.vibrationUser);
/* 321 */       if (isPanicking()) {
/* 322 */         setDancing(false);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPickUpLoot() {
/* 329 */     return (!isOnPickupCooldown() && hasItemInHand());
/*     */   }
/*     */   
/*     */   public boolean hasItemInHand() {
/* 333 */     return !getItemInHand(InteractionHand.MAIN_HAND).isEmpty();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canTakeItem(ItemStack $$0) {
/* 338 */     return false;
/*     */   }
/*     */   
/*     */   private boolean isOnPickupCooldown() {
/* 342 */     return getBrain().checkMemory(MemoryModuleType.ITEM_PICKUP_COOLDOWN_TICKS, MemoryStatus.VALUE_PRESENT);
/*     */   }
/*     */ 
/*     */   
/*     */   protected InteractionResult mobInteract(Player $$0, InteractionHand $$1) {
/* 347 */     ItemStack $$2 = $$0.getItemInHand($$1);
/* 348 */     ItemStack $$3 = getItemInHand(InteractionHand.MAIN_HAND);
/*     */     
/* 350 */     if (isDancing() && isDuplicationItem($$2) && canDuplicate()) {
/* 351 */       duplicateAllay();
/* 352 */       level().broadcastEntityEvent((Entity)this, (byte)18);
/* 353 */       level().playSound($$0, (Entity)this, SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.NEUTRAL, 2.0F, 1.0F);
/* 354 */       removeInteractionItem($$0, $$2);
/* 355 */       return InteractionResult.SUCCESS;
/*     */     } 
/*     */     
/* 358 */     if ($$3.isEmpty() && !$$2.isEmpty()) {
/* 359 */       ItemStack $$4 = $$2.copyWithCount(1);
/* 360 */       setItemInHand(InteractionHand.MAIN_HAND, $$4);
/* 361 */       removeInteractionItem($$0, $$2);
/* 362 */       level().playSound($$0, (Entity)this, SoundEvents.ALLAY_ITEM_GIVEN, SoundSource.NEUTRAL, 2.0F, 1.0F);
/* 363 */       getBrain().setMemory(MemoryModuleType.LIKED_PLAYER, $$0.getUUID());
/*     */       
/* 365 */       return InteractionResult.SUCCESS;
/* 366 */     }  if (!$$3.isEmpty() && $$1 == InteractionHand.MAIN_HAND && $$2.isEmpty()) {
/* 367 */       setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
/* 368 */       level().playSound($$0, (Entity)this, SoundEvents.ALLAY_ITEM_TAKEN, SoundSource.NEUTRAL, 2.0F, 1.0F);
/* 369 */       swing(InteractionHand.MAIN_HAND);
/* 370 */       for (ItemStack $$5 : getInventory().removeAllItems()) {
/* 371 */         BehaviorUtils.throwItem((LivingEntity)this, $$5, position());
/*     */       }
/* 373 */       getBrain().eraseMemory(MemoryModuleType.LIKED_PLAYER);
/* 374 */       $$0.addItem($$3);
/*     */       
/* 376 */       return InteractionResult.SUCCESS;
/*     */     } 
/*     */     
/* 379 */     return super.mobInteract($$0, $$1);
/*     */   }
/*     */   
/*     */   public void setJukeboxPlaying(BlockPos $$0, boolean $$1) {
/* 383 */     if ($$1) {
/* 384 */       if (!isDancing()) {
/* 385 */         this.jukeboxPos = $$0;
/* 386 */         setDancing(true);
/*     */       } 
/* 388 */     } else if ($$0.equals(this.jukeboxPos) || this.jukeboxPos == null) {
/* 389 */       this.jukeboxPos = null;
/* 390 */       setDancing(false);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public SimpleContainer getInventory() {
/* 396 */     return this.inventory;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Vec3i getPickupReach() {
/* 401 */     return ITEM_PICKUP_REACH;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean wantsToPickUp(ItemStack $$0) {
/* 406 */     ItemStack $$1 = getItemInHand(InteractionHand.MAIN_HAND);
/* 407 */     return (!$$1.isEmpty() && 
/* 408 */       level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING) && this.inventory
/* 409 */       .canAddItem($$0) && 
/* 410 */       allayConsidersItemEqual($$1, $$0));
/*     */   }
/*     */   
/*     */   private boolean allayConsidersItemEqual(ItemStack $$0, ItemStack $$1) {
/* 414 */     return (ItemStack.isSameItem($$0, $$1) && !hasNonMatchingPotion($$0, $$1));
/*     */   }
/*     */   
/*     */   private boolean hasNonMatchingPotion(ItemStack $$0, ItemStack $$1) {
/* 418 */     CompoundTag $$2 = $$0.getTag();
/* 419 */     boolean $$3 = ($$2 != null && $$2.contains("Potion"));
/*     */     
/* 421 */     if (!$$3) {
/* 422 */       return false;
/*     */     }
/*     */     
/* 425 */     CompoundTag $$4 = $$1.getTag();
/* 426 */     boolean $$5 = ($$4 != null && $$4.contains("Potion"));
/* 427 */     if (!$$5) {
/* 428 */       return true;
/*     */     }
/*     */     
/* 431 */     Tag $$6 = $$2.get("Potion");
/* 432 */     Tag $$7 = $$4.get("Potion");
/* 433 */     return ($$6 != null && $$7 != null && 
/*     */       
/* 435 */       !$$6.equals($$7));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void pickUpItem(ItemEntity $$0) {
/* 440 */     InventoryCarrier.pickUpItem((Mob)this, this, $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void sendDebugPackets() {
/* 445 */     super.sendDebugPackets();
/*     */     
/* 447 */     DebugPackets.sendEntityBrain((LivingEntity)this);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFlapping() {
/* 452 */     return !onGround();
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateDynamicGameEventListener(BiConsumer<DynamicGameEventListener<?>, ServerLevel> $$0) {
/* 457 */     Level level = level(); if (level instanceof ServerLevel) { ServerLevel $$1 = (ServerLevel)level;
/* 458 */       $$0.accept(this.dynamicVibrationListener, $$1);
/* 459 */       $$0.accept(this.dynamicJukeboxListener, $$1); }
/*     */   
/*     */   }
/*     */   
/*     */   public boolean isDancing() {
/* 464 */     return ((Boolean)this.entityData.get(DATA_DANCING)).booleanValue();
/*     */   }
/*     */   
/*     */   public void setDancing(boolean $$0) {
/* 468 */     if ((level()).isClientSide || !isEffectiveAi() || ($$0 && isPanicking())) {
/*     */       return;
/*     */     }
/* 471 */     this.entityData.set(DATA_DANCING, Boolean.valueOf($$0));
/*     */   }
/*     */   
/*     */   private boolean shouldStopDancing() {
/* 475 */     return (this.jukeboxPos == null || 
/* 476 */       !this.jukeboxPos.closerToCenterThan((Position)position(), GameEvent.JUKEBOX_PLAY.getNotificationRadius()) || 
/* 477 */       !level().getBlockState(this.jukeboxPos).is(Blocks.JUKEBOX));
/*     */   }
/*     */   
/*     */   public float getHoldingItemAnimationProgress(float $$0) {
/* 481 */     return Mth.lerp($$0, this.holdingItemAnimationTicks0, this.holdingItemAnimationTicks) / 5.0F;
/*     */   }
/*     */   
/*     */   public boolean isSpinning() {
/* 485 */     float $$0 = this.dancingAnimationTicks % 55.0F;
/* 486 */     return ($$0 < 15.0F);
/*     */   }
/*     */   
/*     */   public float getSpinningProgress(float $$0) {
/* 490 */     return Mth.lerp($$0, this.spinningAnimationTicks0, this.spinningAnimationTicks) / 15.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equipmentHasChanged(ItemStack $$0, ItemStack $$1) {
/* 495 */     return !allayConsidersItemEqual($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void dropEquipment() {
/* 500 */     super.dropEquipment();
/* 501 */     this.inventory.removeAllItems().forEach(this::spawnAtLocation);
/*     */ 
/*     */ 
/*     */     
/* 505 */     ItemStack $$0 = getItemBySlot(EquipmentSlot.MAINHAND);
/* 506 */     if (!$$0.isEmpty() && !EnchantmentHelper.hasVanishingCurse($$0)) {
/* 507 */       spawnAtLocation($$0);
/* 508 */       setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean removeWhenFarAway(double $$0) {
/* 514 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/* 519 */     super.addAdditionalSaveData($$0);
/*     */     
/* 521 */     writeInventoryToTag($$0);
/*     */ 
/*     */ 
/*     */     
/* 525 */     Objects.requireNonNull(LOGGER); VibrationSystem.Data.CODEC.encodeStart((DynamicOps)NbtOps.INSTANCE, this.vibrationData).resultOrPartial(LOGGER::error)
/* 526 */       .ifPresent($$1 -> $$0.put("listener", $$1));
/*     */     
/* 528 */     $$0.putLong("DuplicationCooldown", this.duplicationCooldown);
/* 529 */     $$0.putBoolean("CanDuplicate", canDuplicate());
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/* 534 */     super.readAdditionalSaveData($$0);
/*     */     
/* 536 */     readInventoryFromTag($$0);
/*     */     
/* 538 */     if ($$0.contains("listener", 10)) {
/*     */ 
/*     */       
/* 541 */       Objects.requireNonNull(LOGGER); VibrationSystem.Data.CODEC.parse(new Dynamic((DynamicOps)NbtOps.INSTANCE, $$0.getCompound("listener"))).resultOrPartial(LOGGER::error)
/* 542 */         .ifPresent($$0 -> this.vibrationData = $$0);
/*     */     } 
/*     */     
/* 545 */     this.duplicationCooldown = $$0.getInt("DuplicationCooldown");
/* 546 */     this.entityData.set(DATA_CAN_DUPLICATE, Boolean.valueOf($$0.getBoolean("CanDuplicate")));
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean shouldStayCloseToLeashHolder() {
/* 551 */     return false;
/*     */   }
/*     */   
/*     */   private void updateDuplicationCooldown() {
/* 555 */     if (this.duplicationCooldown > 0L) {
/* 556 */       this.duplicationCooldown--;
/*     */     }
/*     */     
/* 559 */     if (!level().isClientSide() && 
/* 560 */       this.duplicationCooldown == 0L && !canDuplicate()) {
/* 561 */       this.entityData.set(DATA_CAN_DUPLICATE, Boolean.valueOf(true));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isDuplicationItem(ItemStack $$0) {
/* 567 */     return DUPLICATION_ITEM.test($$0);
/*     */   }
/*     */   
/*     */   private void duplicateAllay() {
/* 571 */     Allay $$0 = (Allay)EntityType.ALLAY.create(level());
/* 572 */     if ($$0 != null) {
/* 573 */       $$0.moveTo(position());
/* 574 */       $$0.setPersistenceRequired();
/* 575 */       $$0.resetDuplicationCooldown();
/* 576 */       resetDuplicationCooldown();
/* 577 */       level().addFreshEntity((Entity)$$0);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void resetDuplicationCooldown() {
/* 582 */     this.duplicationCooldown = 6000L;
/* 583 */     this.entityData.set(DATA_CAN_DUPLICATE, Boolean.valueOf(false));
/*     */   }
/*     */   
/*     */   private boolean canDuplicate() {
/* 587 */     return ((Boolean)this.entityData.get(DATA_CAN_DUPLICATE)).booleanValue();
/*     */   }
/*     */   
/*     */   private void removeInteractionItem(Player $$0, ItemStack $$1) {
/* 591 */     if (!($$0.getAbilities()).instabuild) {
/* 592 */       $$1.shrink(1);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Vec3 getLeashOffset() {
/* 598 */     return new Vec3(0.0D, getEyeHeight() * 0.6D, getBbWidth() * 0.1D);
/*     */   }
/*     */ 
/*     */   
/*     */   protected float ridingOffset(Entity $$0) {
/* 603 */     return 0.04F;
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleEntityEvent(byte $$0) {
/* 608 */     if ($$0 == 18) {
/* 609 */       for (int $$1 = 0; $$1 < 3; $$1++) {
/* 610 */         spawnHeartParticle();
/*     */       }
/*     */     } else {
/* 613 */       super.handleEntityEvent($$0);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void spawnHeartParticle() {
/* 618 */     double $$0 = this.random.nextGaussian() * 0.02D;
/* 619 */     double $$1 = this.random.nextGaussian() * 0.02D;
/* 620 */     double $$2 = this.random.nextGaussian() * 0.02D;
/* 621 */     level().addParticle((ParticleOptions)ParticleTypes.HEART, getRandomX(1.0D), getRandomY() + 0.5D, getRandomZ(1.0D), $$0, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public VibrationSystem.Data getVibrationData() {
/* 626 */     return this.vibrationData;
/*     */   }
/*     */ 
/*     */   
/*     */   public VibrationSystem.User getVibrationUser() {
/* 631 */     return this.vibrationUser;
/*     */   }
/*     */   
/*     */   private class JukeboxListener implements GameEventListener {
/*     */     private final PositionSource listenerSource;
/*     */     private final int listenerRadius;
/*     */     
/*     */     public JukeboxListener(PositionSource $$0, int $$1) {
/* 639 */       this.listenerSource = $$0;
/* 640 */       this.listenerRadius = $$1;
/*     */     }
/*     */ 
/*     */     
/*     */     public PositionSource getListenerSource() {
/* 645 */       return this.listenerSource;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getListenerRadius() {
/* 650 */       return this.listenerRadius;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean handleGameEvent(ServerLevel $$0, GameEvent $$1, GameEvent.Context $$2, Vec3 $$3) {
/* 655 */       if ($$1 == GameEvent.JUKEBOX_PLAY) {
/* 656 */         Allay.this.setJukeboxPlaying(BlockPos.containing((Position)$$3), true);
/* 657 */         return true;
/*     */       } 
/*     */       
/* 660 */       if ($$1 == GameEvent.JUKEBOX_STOP_PLAY) {
/* 661 */         Allay.this.setJukeboxPlaying(BlockPos.containing((Position)$$3), false);
/* 662 */         return true;
/*     */       } 
/*     */       
/* 665 */       return false;
/*     */     }
/*     */   }
/*     */   
/*     */   private class VibrationUser
/*     */     implements VibrationSystem.User {
/*     */     private static final int VIBRATION_EVENT_LISTENER_RANGE = 16;
/* 672 */     private final PositionSource positionSource = (PositionSource)new EntityPositionSource((Entity)Allay.this, Allay.this.getEyeHeight());
/*     */ 
/*     */     
/*     */     public int getListenerRadius() {
/* 676 */       return 16;
/*     */     }
/*     */ 
/*     */     
/*     */     public PositionSource getPositionSource() {
/* 681 */       return this.positionSource;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canReceiveVibration(ServerLevel $$0, BlockPos $$1, GameEvent $$2, GameEvent.Context $$3) {
/* 686 */       if (Allay.this.isNoAi()) {
/* 687 */         return false;
/*     */       }
/*     */       
/* 690 */       Optional<GlobalPos> $$4 = Allay.this.getBrain().getMemory(MemoryModuleType.LIKED_NOTEBLOCK_POSITION);
/* 691 */       if ($$4.isEmpty()) {
/* 692 */         return true;
/*     */       }
/* 694 */       GlobalPos $$5 = $$4.get();
/* 695 */       return ($$5.dimension().equals($$0.dimension()) && $$5.pos().equals($$1));
/*     */     }
/*     */ 
/*     */     
/*     */     public void onReceiveVibration(ServerLevel $$0, BlockPos $$1, GameEvent $$2, @Nullable Entity $$3, @Nullable Entity $$4, float $$5) {
/* 700 */       if ($$2 == GameEvent.NOTE_BLOCK_PLAY) {
/* 701 */         AllayAi.hearNoteblock((LivingEntity)Allay.this, new BlockPos((Vec3i)$$1));
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public TagKey<GameEvent> getListenableEvents() {
/* 707 */       return GameEventTags.ALLAY_CAN_LISTEN;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\animal\allay\Allay.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */