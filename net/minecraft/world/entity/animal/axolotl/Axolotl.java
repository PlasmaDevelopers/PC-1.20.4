/*     */ package net.minecraft.world.entity.animal.axolotl;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.Dynamic;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import java.util.function.IntFunction;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.protocol.game.DebugPackets;
/*     */ import net.minecraft.network.syncher.EntityDataAccessor;
/*     */ import net.minecraft.network.syncher.EntityDataSerializers;
/*     */ import net.minecraft.network.syncher.SynchedEntityData;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.tags.ItemTags;
/*     */ import net.minecraft.util.ByIdMap;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.util.StringRepresentable;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.effect.MobEffectInstance;
/*     */ import net.minecraft.world.effect.MobEffects;
/*     */ import net.minecraft.world.entity.AgeableMob;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityDimensions;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LerpingModel;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.MobSpawnType;
/*     */ import net.minecraft.world.entity.MobType;
/*     */ import net.minecraft.world.entity.MoverType;
/*     */ import net.minecraft.world.entity.Pose;
/*     */ import net.minecraft.world.entity.SpawnGroupData;
/*     */ import net.minecraft.world.entity.VariantHolder;
/*     */ import net.minecraft.world.entity.ai.Brain;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.entity.ai.control.LookControl;
/*     */ import net.minecraft.world.entity.ai.control.MoveControl;
/*     */ import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
/*     */ import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*     */ import net.minecraft.world.entity.ai.navigation.AmphibiousPathNavigation;
/*     */ import net.minecraft.world.entity.ai.navigation.PathNavigation;
/*     */ import net.minecraft.world.entity.ai.sensing.Sensor;
/*     */ import net.minecraft.world.entity.ai.sensing.SensorType;
/*     */ import net.minecraft.world.entity.animal.Animal;
/*     */ import net.minecraft.world.entity.animal.Bucketable;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.ServerLevelAccessor;
/*     */ import net.minecraft.world.level.pathfinder.BlockPathTypes;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import org.joml.Vector3f;
/*     */ 
/*     */ public class Axolotl extends Animal implements LerpingModel, VariantHolder<Axolotl.Variant>, Bucketable {
/*     */   public static final int TOTAL_PLAYDEAD_TIME = 200;
/*  75 */   protected static final ImmutableList<? extends SensorType<? extends Sensor<? super Axolotl>>> SENSOR_TYPES = ImmutableList.of(SensorType.NEAREST_LIVING_ENTITIES, SensorType.NEAREST_ADULT, SensorType.HURT_BY, SensorType.AXOLOTL_ATTACKABLES, SensorType.AXOLOTL_TEMPTATIONS);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  83 */   protected static final ImmutableList<? extends MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(MemoryModuleType.BREED_TARGET, MemoryModuleType.NEAREST_LIVING_ENTITIES, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER, MemoryModuleType.LOOK_TARGET, MemoryModuleType.WALK_TARGET, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.PATH, MemoryModuleType.ATTACK_TARGET, MemoryModuleType.ATTACK_COOLING_DOWN, MemoryModuleType.NEAREST_VISIBLE_ADULT, (Object[])new MemoryModuleType[] { MemoryModuleType.HURT_BY_ENTITY, MemoryModuleType.PLAY_DEAD_TICKS, MemoryModuleType.NEAREST_ATTACKABLE, MemoryModuleType.TEMPTING_PLAYER, MemoryModuleType.TEMPTATION_COOLDOWN_TICKS, MemoryModuleType.IS_TEMPTED, MemoryModuleType.HAS_HUNTING_COOLDOWN, MemoryModuleType.IS_PANICKING });
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
/* 106 */   private static final EntityDataAccessor<Integer> DATA_VARIANT = SynchedEntityData.defineId(Axolotl.class, EntityDataSerializers.INT);
/* 107 */   private static final EntityDataAccessor<Boolean> DATA_PLAYING_DEAD = SynchedEntityData.defineId(Axolotl.class, EntityDataSerializers.BOOLEAN);
/* 108 */   private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(Axolotl.class, EntityDataSerializers.BOOLEAN);
/*     */   
/*     */   public static final double PLAYER_REGEN_DETECTION_RANGE = 20.0D;
/*     */   
/*     */   public static final int RARE_VARIANT_CHANCE = 1200;
/*     */   
/*     */   private static final int AXOLOTL_TOTAL_AIR_SUPPLY = 6000;
/*     */   
/*     */   public static final String VARIANT_TAG = "Variant";
/*     */   private static final int REHYDRATE_AIR_SUPPLY = 1800;
/*     */   private static final int REGEN_BUFF_MAX_DURATION = 2400;
/* 119 */   private final Map<String, Vector3f> modelRotationValues = Maps.newHashMap();
/*     */   private static final int REGEN_BUFF_BASE_DURATION = 100;
/*     */   
/* 122 */   public enum Variant implements StringRepresentable { LUCY(0, "lucy", true),
/* 123 */     WILD(1, "wild", true),
/* 124 */     GOLD(2, "gold", true),
/* 125 */     CYAN(3, "cyan", true),
/* 126 */     BLUE(4, "blue", false);
/*     */     
/* 128 */     private static final IntFunction<Variant> BY_ID = ByIdMap.continuous(Variant::getId, (Object[])values(), ByIdMap.OutOfBoundsStrategy.ZERO);
/*     */     
/* 130 */     public static final Codec<Variant> CODEC = (Codec<Variant>)StringRepresentable.fromEnum(Variant::values);
/*     */     
/*     */     private final int id;
/*     */     
/*     */     private final String name;
/*     */     
/*     */     Variant(int $$0, String $$1, boolean $$2) {
/* 137 */       this.id = $$0;
/* 138 */       this.name = $$1;
/* 139 */       this.common = $$2;
/*     */     } private final boolean common; static {
/*     */     
/*     */     } public int getId() {
/* 143 */       return this.id;
/*     */     }
/*     */     
/*     */     public String getName() {
/* 147 */       return this.name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getSerializedName() {
/* 152 */       return this.name;
/*     */     }
/*     */     
/*     */     public static Variant byId(int $$0) {
/* 156 */       return BY_ID.apply($$0);
/*     */     }
/*     */     
/*     */     public static Variant getCommonSpawnVariant(RandomSource $$0) {
/* 160 */       return getSpawnVariant($$0, true);
/*     */     }
/*     */     
/*     */     public static Variant getRareSpawnVariant(RandomSource $$0) {
/* 164 */       return getSpawnVariant($$0, false);
/*     */     }
/*     */     
/*     */     private static Variant getSpawnVariant(RandomSource $$0, boolean $$1) {
/* 168 */       Variant[] $$2 = (Variant[])Arrays.<Variant>stream(values()).filter($$1 -> ($$1.common == $$0)).toArray($$0 -> new Variant[$$0]);
/* 169 */       return (Variant)Util.getRandom((Object[])$$2, $$0);
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Axolotl(EntityType<? extends Axolotl> $$0, Level $$1) {
/* 176 */     super($$0, $$1);
/*     */     
/* 178 */     setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
/* 179 */     this.moveControl = (MoveControl)new AxolotlMoveControl(this);
/* 180 */     this.lookControl = (LookControl)new AxolotlLookControl(this, 20);
/*     */     
/* 182 */     setMaxUpStep(1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<String, Vector3f> getModelRotationValues() {
/* 187 */     return this.modelRotationValues;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getWalkTargetValue(BlockPos $$0, LevelReader $$1) {
/* 192 */     return 0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {
/* 197 */     super.defineSynchedData();
/* 198 */     this.entityData.define(DATA_VARIANT, Integer.valueOf(0));
/* 199 */     this.entityData.define(DATA_PLAYING_DEAD, Boolean.valueOf(false));
/* 200 */     this.entityData.define(FROM_BUCKET, Boolean.valueOf(false));
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/* 205 */     super.addAdditionalSaveData($$0);
/* 206 */     $$0.putInt("Variant", getVariant().getId());
/* 207 */     $$0.putBoolean("FromBucket", fromBucket());
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/* 212 */     super.readAdditionalSaveData($$0);
/* 213 */     setVariant(Variant.byId($$0.getInt("Variant")));
/* 214 */     setFromBucket($$0.getBoolean("FromBucket"));
/*     */   }
/*     */ 
/*     */   
/*     */   public void playAmbientSound() {
/* 219 */     if (isPlayingDead()) {
/*     */       return;
/*     */     }
/* 222 */     super.playAmbientSound();
/*     */   }
/*     */   
/*     */   public SpawnGroupData finalizeSpawn(ServerLevelAccessor $$0, DifficultyInstance $$1, MobSpawnType $$2, @Nullable SpawnGroupData $$3, @Nullable CompoundTag $$4) {
/*     */     AxolotlGroupData axolotlGroupData;
/* 227 */     boolean $$5 = false;
/*     */     
/* 229 */     if ($$2 == MobSpawnType.BUCKET) {
/* 230 */       return $$3;
/*     */     }
/*     */     
/* 233 */     RandomSource $$6 = $$0.getRandom();
/* 234 */     if ($$3 instanceof AxolotlGroupData) {
/* 235 */       if (((AxolotlGroupData)$$3).getGroupSize() >= 2) {
/* 236 */         $$5 = true;
/*     */       }
/*     */     } else {
/*     */       
/* 240 */       axolotlGroupData = new AxolotlGroupData(new Variant[] { Variant.getCommonSpawnVariant($$6), Variant.getCommonSpawnVariant($$6) });
/*     */     } 
/*     */     
/* 243 */     setVariant(axolotlGroupData.getVariant($$6));
/* 244 */     if ($$5) {
/* 245 */       setAge(-24000);
/*     */     }
/*     */     
/* 248 */     return super.finalizeSpawn($$0, $$1, $$2, (SpawnGroupData)axolotlGroupData, $$4);
/*     */   }
/*     */ 
/*     */   
/*     */   public void baseTick() {
/* 253 */     int $$0 = getAirSupply();
/* 254 */     super.baseTick();
/* 255 */     if (!isNoAi()) {
/* 256 */       handleAirSupply($$0);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void handleAirSupply(int $$0) {
/* 262 */     if (isAlive() && !isInWaterRainOrBubble()) {
/* 263 */       setAirSupply($$0 - 1);
/* 264 */       if (getAirSupply() == -20) {
/* 265 */         setAirSupply(0);
/* 266 */         hurt(damageSources().dryOut(), 2.0F);
/*     */       } 
/*     */     } else {
/* 269 */       setAirSupply(getMaxAirSupply());
/*     */     } 
/*     */   }
/*     */   
/*     */   public void rehydrate() {
/* 274 */     int $$0 = getAirSupply() + 1800;
/* 275 */     setAirSupply(Math.min($$0, getMaxAirSupply()));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxAirSupply() {
/* 280 */     return 6000;
/*     */   }
/*     */ 
/*     */   
/*     */   public Variant getVariant() {
/* 285 */     return Variant.byId(((Integer)this.entityData.get(DATA_VARIANT)).intValue());
/*     */   }
/*     */ 
/*     */   
/*     */   public void setVariant(Variant $$0) {
/* 290 */     this.entityData.set(DATA_VARIANT, Integer.valueOf($$0.getId()));
/*     */   }
/*     */   
/*     */   private static boolean useRareVariant(RandomSource $$0) {
/* 294 */     return ($$0.nextInt(1200) == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean checkSpawnObstruction(LevelReader $$0) {
/* 299 */     return $$0.isUnobstructed((Entity)this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPushedByFluid() {
/* 305 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public MobType getMobType() {
/* 310 */     return MobType.WATER;
/*     */   }
/*     */   
/*     */   public void setPlayingDead(boolean $$0) {
/* 314 */     this.entityData.set(DATA_PLAYING_DEAD, Boolean.valueOf($$0));
/*     */   }
/*     */   
/*     */   public boolean isPlayingDead() {
/* 318 */     return ((Boolean)this.entityData.get(DATA_PLAYING_DEAD)).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean fromBucket() {
/* 323 */     return ((Boolean)this.entityData.get(FROM_BUCKET)).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFromBucket(boolean $$0) {
/* 328 */     this.entityData.set(FROM_BUCKET, Boolean.valueOf($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public AgeableMob getBreedOffspring(ServerLevel $$0, AgeableMob $$1) {
/* 334 */     Axolotl $$2 = (Axolotl)EntityType.AXOLOTL.create((Level)$$0);
/* 335 */     if ($$2 != null) {
/*     */       Variant $$4;
/* 337 */       if (useRareVariant(this.random)) {
/* 338 */         Variant $$3 = Variant.getRareSpawnVariant(this.random);
/*     */       } else {
/* 340 */         $$4 = this.random.nextBoolean() ? getVariant() : ((Axolotl)$$1).getVariant();
/*     */       } 
/* 342 */       $$2.setVariant($$4);
/* 343 */       $$2.setPersistenceRequired();
/*     */     } 
/* 345 */     return (AgeableMob)$$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFood(ItemStack $$0) {
/* 350 */     return $$0.is(ItemTags.AXOLOTL_TEMPT_ITEMS);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canBeLeashed(Player $$0) {
/* 355 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void customServerAiStep() {
/* 360 */     level().getProfiler().push("axolotlBrain");
/* 361 */     getBrain().tick((ServerLevel)level(), (LivingEntity)this);
/* 362 */     level().getProfiler().pop();
/*     */     
/* 364 */     level().getProfiler().push("axolotlActivityUpdate");
/* 365 */     AxolotlAi.updateActivity(this);
/* 366 */     level().getProfiler().pop();
/*     */     
/* 368 */     if (!isNoAi()) {
/* 369 */       Optional<Integer> $$0 = getBrain().getMemory(MemoryModuleType.PLAY_DEAD_TICKS);
/* 370 */       setPlayingDead(($$0.isPresent() && ((Integer)$$0.get()).intValue() > 0));
/*     */     } 
/*     */   }
/*     */   
/*     */   public static AttributeSupplier.Builder createAttributes() {
/* 375 */     return Mob.createMobAttributes()
/* 376 */       .add(Attributes.MAX_HEALTH, 14.0D)
/* 377 */       .add(Attributes.MOVEMENT_SPEED, 1.0D)
/* 378 */       .add(Attributes.ATTACK_DAMAGE, 2.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   protected PathNavigation createNavigation(Level $$0) {
/* 383 */     return (PathNavigation)new AmphibiousPathNavigation((Mob)this, $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean doHurtTarget(Entity $$0) {
/* 388 */     boolean $$1 = $$0.hurt(damageSources().mobAttack((LivingEntity)this), (int)getAttributeValue(Attributes.ATTACK_DAMAGE));
/* 389 */     if ($$1) {
/* 390 */       doEnchantDamageEffects((LivingEntity)this, $$0);
/* 391 */       playSound(SoundEvents.AXOLOTL_ATTACK, 1.0F, 1.0F);
/*     */     } 
/* 393 */     return $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hurt(DamageSource $$0, float $$1) {
/* 398 */     float $$2 = getHealth();
/* 399 */     if (!(level()).isClientSide && 
/* 400 */       !isNoAi() && 
/* 401 */       (level()).random.nextInt(3) == 0 && (
/* 402 */       (level()).random.nextInt(3) < $$1 || $$2 / getMaxHealth() < 0.5F) && $$1 < $$2 && 
/*     */       
/* 404 */       isInWater() && ($$0
/* 405 */       .getEntity() != null || $$0.getDirectEntity() != null) && 
/* 406 */       !isPlayingDead()) {
/* 407 */       this.brain.setMemory(MemoryModuleType.PLAY_DEAD_TICKS, Integer.valueOf(200));
/*     */     }
/*     */     
/* 410 */     return super.hurt($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getStandingEyeHeight(Pose $$0, EntityDimensions $$1) {
/* 415 */     return $$1.height * 0.655F;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxHeadXRot() {
/* 420 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxHeadYRot() {
/* 425 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult mobInteract(Player $$0, InteractionHand $$1) {
/* 430 */     return Bucketable.bucketMobPickup($$0, $$1, (LivingEntity)this).orElse(super.mobInteract($$0, $$1));
/*     */   }
/*     */ 
/*     */   
/*     */   public void saveToBucketTag(ItemStack $$0) {
/* 435 */     Bucketable.saveDefaultDataToBucketTag((Mob)this, $$0);
/*     */     
/* 437 */     CompoundTag $$1 = $$0.getOrCreateTag();
/* 438 */     $$1.putInt("Variant", getVariant().getId());
/* 439 */     $$1.putInt("Age", getAge());
/*     */     
/* 441 */     Brain<?> $$2 = getBrain();
/* 442 */     if ($$2.hasMemoryValue(MemoryModuleType.HAS_HUNTING_COOLDOWN)) {
/* 443 */       $$1.putLong("HuntingCooldown", $$2.getTimeUntilExpiry(MemoryModuleType.HAS_HUNTING_COOLDOWN));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadFromBucketTag(CompoundTag $$0) {
/* 449 */     Bucketable.loadDefaultDataFromBucketTag((Mob)this, $$0);
/*     */ 
/*     */     
/* 452 */     setVariant(Variant.byId($$0.getInt("Variant")));
/*     */     
/* 454 */     if ($$0.contains("Age")) {
/* 455 */       setAge($$0.getInt("Age"));
/*     */     }
/* 457 */     if ($$0.contains("HuntingCooldown")) {
/* 458 */       getBrain().setMemoryWithExpiry(MemoryModuleType.HAS_HUNTING_COOLDOWN, Boolean.valueOf(true), $$0.getLong("HuntingCooldown"));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getBucketItemStack() {
/* 464 */     return new ItemStack((ItemLike)Items.AXOLOTL_BUCKET);
/*     */   }
/*     */ 
/*     */   
/*     */   public SoundEvent getPickupSound() {
/* 469 */     return SoundEvents.BUCKET_FILL_AXOLOTL;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canBeSeenAsEnemy() {
/* 474 */     return (!isPlayingDead() && super.canBeSeenAsEnemy());
/*     */   }
/*     */   
/*     */   public static void onStopAttacking(Axolotl $$0, LivingEntity $$1) {
/* 478 */     Level $$2 = $$0.level();
/*     */     
/* 480 */     if ($$1.isDeadOrDying()) {
/* 481 */       DamageSource $$3 = $$1.getLastDamageSource();
/* 482 */       if ($$3 != null) {
/* 483 */         Entity $$4 = $$3.getEntity();
/* 484 */         if ($$4 != null && $$4.getType() == EntityType.PLAYER) {
/* 485 */           Player $$5 = (Player)$$4;
/* 486 */           List<Player> $$6 = $$2.getEntitiesOfClass(Player.class, $$0.getBoundingBox().inflate(20.0D));
/*     */ 
/*     */ 
/*     */           
/* 490 */           if ($$6.contains($$5)) {
/* 491 */             $$0.applySupportingEffects($$5);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void applySupportingEffects(Player $$0) {
/* 500 */     MobEffectInstance $$1 = $$0.getEffect(MobEffects.REGENERATION);
/*     */     
/* 502 */     if ($$1 == null || $$1.endsWithin(2399)) {
/* 503 */       int $$2 = ($$1 != null) ? $$1.getDuration() : 0;
/* 504 */       int $$3 = Math.min(2400, 100 + $$2);
/* 505 */       $$0.addEffect(new MobEffectInstance(MobEffects.REGENERATION, $$3, 0), (Entity)this);
/*     */     } 
/*     */ 
/*     */     
/* 509 */     $$0.removeEffect(MobEffects.DIG_SLOWDOWN);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean requiresCustomPersistence() {
/* 514 */     return (super.requiresCustomPersistence() || fromBucket());
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource $$0) {
/* 519 */     return SoundEvents.AXOLOTL_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected SoundEvent getDeathSound() {
/* 525 */     return SoundEvents.AXOLOTL_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected SoundEvent getAmbientSound() {
/* 531 */     return isInWater() ? SoundEvents.AXOLOTL_IDLE_WATER : SoundEvents.AXOLOTL_IDLE_AIR;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getSwimSplashSound() {
/* 536 */     return SoundEvents.AXOLOTL_SPLASH;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getSwimSound() {
/* 541 */     return SoundEvents.AXOLOTL_SWIM;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Brain.Provider<Axolotl> brainProvider() {
/* 546 */     return Brain.provider((Collection)MEMORY_TYPES, (Collection)SENSOR_TYPES);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Brain<?> makeBrain(Dynamic<?> $$0) {
/* 551 */     return AxolotlAi.makeBrain(brainProvider().makeBrain($$0));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Brain<Axolotl> getBrain() {
/* 557 */     return super.getBrain();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void sendDebugPackets() {
/* 562 */     super.sendDebugPackets();
/* 563 */     DebugPackets.sendEntityBrain((LivingEntity)this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void travel(Vec3 $$0) {
/* 568 */     if (isControlledByLocalInstance() && isInWater()) {
/* 569 */       moveRelative(getSpeed(), $$0);
/* 570 */       move(MoverType.SELF, getDeltaMovement());
/*     */       
/* 572 */       setDeltaMovement(getDeltaMovement().scale(0.9D));
/*     */     } else {
/* 574 */       super.travel($$0);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void usePlayerItem(Player $$0, InteractionHand $$1, ItemStack $$2) {
/* 581 */     if ($$2.is(Items.TROPICAL_FISH_BUCKET)) {
/* 582 */       $$0.setItemInHand($$1, new ItemStack((ItemLike)Items.WATER_BUCKET));
/*     */     } else {
/* 584 */       super.usePlayerItem($$0, $$1, $$2);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean removeWhenFarAway(double $$0) {
/* 590 */     return (!fromBucket() && !hasCustomName());
/*     */   }
/*     */   
/*     */   public static boolean checkAxolotlSpawnRules(EntityType<? extends LivingEntity> $$0, ServerLevelAccessor $$1, MobSpawnType $$2, BlockPos $$3, RandomSource $$4) {
/* 594 */     return $$1.getBlockState($$3.below()).is(BlockTags.AXOLOTLS_SPAWNABLE_ON);
/*     */   }
/*     */   
/*     */   private static class AxolotlMoveControl extends SmoothSwimmingMoveControl {
/*     */     private final Axolotl axolotl;
/*     */     
/*     */     public AxolotlMoveControl(Axolotl $$0) {
/* 601 */       super((Mob)$$0, 85, 10, 0.1F, 0.5F, false);
/* 602 */       this.axolotl = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick() {
/* 607 */       if (!this.axolotl.isPlayingDead())
/* 608 */         super.tick(); 
/*     */     }
/*     */   }
/*     */   
/*     */   private class AxolotlLookControl
/*     */     extends SmoothSwimmingLookControl {
/*     */     public AxolotlLookControl(Axolotl $$0, int $$1) {
/* 615 */       super((Mob)$$0, $$1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick() {
/* 620 */       if (!Axolotl.this.isPlayingDead())
/* 621 */         super.tick(); 
/*     */     }
/*     */   }
/*     */   
/*     */   public static class AxolotlGroupData
/*     */     extends AgeableMob.AgeableMobGroupData {
/*     */     public final Axolotl.Variant[] types;
/*     */     
/*     */     public AxolotlGroupData(Axolotl.Variant... $$0) {
/* 630 */       super(false);
/* 631 */       this.types = $$0;
/*     */     }
/*     */     
/*     */     public Axolotl.Variant getVariant(RandomSource $$0) {
/* 635 */       return this.types[$$0.nextInt(this.types.length)];
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\animal\axolotl\Axolotl.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */