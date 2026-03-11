/*     */ package net.minecraft.world.entity.monster;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import com.mojang.serialization.Dynamic;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.protocol.game.DebugPackets;
/*     */ import net.minecraft.network.syncher.EntityDataAccessor;
/*     */ import net.minecraft.network.syncher.EntityDataSerializers;
/*     */ import net.minecraft.network.syncher.SynchedEntityData;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.util.valueproviders.UniformInt;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityDimensions;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.MobType;
/*     */ import net.minecraft.world.entity.ai.Brain;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
/*     */ import net.minecraft.world.entity.ai.behavior.DoNothing;
/*     */ import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
/*     */ import net.minecraft.world.entity.ai.behavior.MeleeAttack;
/*     */ import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
/*     */ import net.minecraft.world.entity.ai.behavior.RandomStroll;
/*     */ import net.minecraft.world.entity.ai.behavior.RunOne;
/*     */ import net.minecraft.world.entity.ai.behavior.SetEntityLookTargetSometimes;
/*     */ import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromAttackTargetIfTargetOutOfReach;
/*     */ import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromLookTarget;
/*     */ import net.minecraft.world.entity.ai.behavior.StartAttacking;
/*     */ import net.minecraft.world.entity.ai.behavior.StopAttackingIfTargetInvalid;
/*     */ import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*     */ import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
/*     */ import net.minecraft.world.entity.ai.sensing.Sensor;
/*     */ import net.minecraft.world.entity.ai.sensing.SensorType;
/*     */ import net.minecraft.world.entity.monster.hoglin.HoglinBase;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.entity.schedule.Activity;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import org.joml.Vector3f;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Zoglin
/*     */   extends Monster
/*     */   implements Enemy, HoglinBase
/*     */ {
/*  60 */   private static final EntityDataAccessor<Boolean> DATA_BABY_ID = SynchedEntityData.defineId(Zoglin.class, EntityDataSerializers.BOOLEAN);
/*     */   
/*     */   private static final int MAX_HEALTH = 40;
/*     */   
/*     */   private static final int ATTACK_KNOCKBACK = 1;
/*     */   
/*     */   private static final float KNOCKBACK_RESISTANCE = 0.6F;
/*     */   
/*     */   private static final int ATTACK_DAMAGE = 6;
/*     */   
/*     */   private static final float BABY_ATTACK_DAMAGE = 0.5F;
/*     */   private static final int ATTACK_INTERVAL = 40;
/*     */   private static final int BABY_ATTACK_INTERVAL = 15;
/*     */   private static final int ATTACK_DURATION = 200;
/*     */   private static final float MOVEMENT_SPEED_WHEN_FIGHTING = 0.3F;
/*     */   private static final float SPEED_MULTIPLIER_WHEN_IDLING = 0.4F;
/*     */   private int attackAnimationRemainingTicks;
/*  77 */   protected static final ImmutableList<? extends SensorType<? extends Sensor<? super Zoglin>>> SENSOR_TYPES = ImmutableList.of(SensorType.NEAREST_LIVING_ENTITIES, SensorType.NEAREST_PLAYERS);
/*     */ 
/*     */ 
/*     */   
/*  81 */   protected static final ImmutableList<? extends MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(MemoryModuleType.NEAREST_LIVING_ENTITIES, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER, MemoryModuleType.LOOK_TARGET, MemoryModuleType.WALK_TARGET, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.PATH, MemoryModuleType.ATTACK_TARGET, MemoryModuleType.ATTACK_COOLING_DOWN);
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
/*     */   public Zoglin(EntityType<? extends Zoglin> $$0, Level $$1) {
/*  95 */     super((EntityType)$$0, $$1);
/*  96 */     this.xpReward = 5;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Brain.Provider<Zoglin> brainProvider() {
/* 101 */     return Brain.provider((Collection)MEMORY_TYPES, (Collection)SENSOR_TYPES);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Brain<?> makeBrain(Dynamic<?> $$0) {
/* 106 */     Brain<Zoglin> $$1 = brainProvider().makeBrain($$0);
/* 107 */     initCoreActivity($$1);
/* 108 */     initIdleActivity($$1);
/* 109 */     initFightActivity($$1);
/*     */     
/* 111 */     $$1.setCoreActivities((Set)ImmutableSet.of(Activity.CORE));
/* 112 */     $$1.setDefaultActivity(Activity.IDLE);
/* 113 */     $$1.useDefaultActivity();
/* 114 */     return $$1;
/*     */   }
/*     */   
/*     */   private static void initCoreActivity(Brain<Zoglin> $$0) {
/* 118 */     $$0.addActivity(Activity.CORE, 0, ImmutableList.of(new LookAtTargetSink(45, 90), new MoveToTargetSink()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void initIdleActivity(Brain<Zoglin> $$0) {
/* 125 */     $$0.addActivity(Activity.IDLE, 10, ImmutableList.of(
/* 126 */           StartAttacking.create(Zoglin::findNearestValidAttackTarget), 
/* 127 */           SetEntityLookTargetSometimes.create(8.0F, UniformInt.of(30, 60)), new RunOne(
/* 128 */             (List)ImmutableList.of(
/* 129 */               Pair.of(RandomStroll.stroll(0.4F), Integer.valueOf(2)), 
/* 130 */               Pair.of(SetWalkTargetFromLookTarget.create(0.4F, 3), Integer.valueOf(2)), 
/* 131 */               Pair.of(new DoNothing(30, 60), Integer.valueOf(1))))));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void initFightActivity(Brain<Zoglin> $$0) {
/* 137 */     $$0.addActivityAndRemoveMemoryWhenStopped(Activity.FIGHT, 10, ImmutableList.of(
/* 138 */           SetWalkTargetFromAttackTargetIfTargetOutOfReach.create(1.0F), 
/* 139 */           BehaviorBuilder.triggerIf(Zoglin::isAdult, MeleeAttack.create(40)), 
/* 140 */           BehaviorBuilder.triggerIf(Zoglin::isBaby, MeleeAttack.create(15)), 
/* 141 */           StopAttackingIfTargetInvalid.create()), MemoryModuleType.ATTACK_TARGET);
/*     */   }
/*     */ 
/*     */   
/*     */   private Optional<? extends LivingEntity> findNearestValidAttackTarget() {
/* 146 */     return ((NearestVisibleLivingEntities)getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES).orElse(NearestVisibleLivingEntities.empty())).findClosest(this::isTargetable);
/*     */   }
/*     */   
/*     */   private boolean isTargetable(LivingEntity $$0) {
/* 150 */     EntityType<?> $$1 = $$0.getType();
/* 151 */     return ($$1 != EntityType.ZOGLIN && $$1 != EntityType.CREEPER && Sensor.isEntityAttackable((LivingEntity)this, $$0));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {
/* 156 */     super.defineSynchedData();
/* 157 */     this.entityData.define(DATA_BABY_ID, Boolean.valueOf(false));
/*     */   }
/*     */ 
/*     */   
/*     */   public void onSyncedDataUpdated(EntityDataAccessor<?> $$0) {
/* 162 */     super.onSyncedDataUpdated($$0);
/* 163 */     if (DATA_BABY_ID.equals($$0)) {
/* 164 */       refreshDimensions();
/*     */     }
/*     */   }
/*     */   
/*     */   public static AttributeSupplier.Builder createAttributes() {
/* 169 */     return Monster.createMonsterAttributes()
/* 170 */       .add(Attributes.MAX_HEALTH, 40.0D)
/* 171 */       .add(Attributes.MOVEMENT_SPEED, 0.30000001192092896D)
/* 172 */       .add(Attributes.KNOCKBACK_RESISTANCE, 0.6000000238418579D)
/* 173 */       .add(Attributes.ATTACK_KNOCKBACK, 1.0D)
/* 174 */       .add(Attributes.ATTACK_DAMAGE, 6.0D);
/*     */   }
/*     */   
/*     */   public boolean isAdult() {
/* 178 */     return !isBaby();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean doHurtTarget(Entity $$0) {
/* 183 */     if (!($$0 instanceof LivingEntity)) {
/* 184 */       return false;
/*     */     }
/* 186 */     this.attackAnimationRemainingTicks = 10;
/* 187 */     level().broadcastEntityEvent((Entity)this, (byte)4);
/*     */     
/* 189 */     playSound(SoundEvents.ZOGLIN_ATTACK, 1.0F, getVoicePitch());
/* 190 */     return HoglinBase.hurtAndThrowTarget((LivingEntity)this, (LivingEntity)$$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canBeLeashed(Player $$0) {
/* 195 */     return !isLeashed();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void blockedByShield(LivingEntity $$0) {
/* 200 */     if (!isBaby()) {
/* 201 */       HoglinBase.throwTarget((LivingEntity)this, $$0);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected Vector3f getPassengerAttachmentPoint(Entity $$0, EntityDimensions $$1, float $$2) {
/* 207 */     return new Vector3f(0.0F, $$1.height + 0.09375F * $$2, 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hurt(DamageSource $$0, float $$1) {
/* 212 */     boolean $$2 = super.hurt($$0, $$1);
/* 213 */     if ((level()).isClientSide) {
/* 214 */       return false;
/*     */     }
/* 216 */     if (!$$2 || !($$0.getEntity() instanceof LivingEntity)) {
/* 217 */       return $$2;
/*     */     }
/* 219 */     LivingEntity $$3 = (LivingEntity)$$0.getEntity();
/* 220 */     if (canAttack($$3) && !BehaviorUtils.isOtherTargetMuchFurtherAwayThanCurrentAttackTarget((LivingEntity)this, $$3, 4.0D)) {
/* 221 */       setAttackTarget($$3);
/*     */     }
/* 223 */     return $$2;
/*     */   }
/*     */   
/*     */   private void setAttackTarget(LivingEntity $$0) {
/* 227 */     this.brain.eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
/* 228 */     this.brain.setMemoryWithExpiry(MemoryModuleType.ATTACK_TARGET, $$0, 200L);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Brain<Zoglin> getBrain() {
/* 234 */     return super.getBrain();
/*     */   }
/*     */   
/*     */   protected void updateActivity() {
/* 238 */     Activity $$0 = this.brain.getActiveNonCoreActivity().orElse(null);
/*     */ 
/*     */     
/* 241 */     this.brain.setActiveActivityToFirstValid((List)ImmutableList.of(Activity.FIGHT, Activity.IDLE));
/*     */     
/* 243 */     Activity $$1 = this.brain.getActiveNonCoreActivity().orElse(null);
/* 244 */     if ($$1 == Activity.FIGHT && $$0 != Activity.FIGHT)
/*     */     {
/* 246 */       playAngrySound();
/*     */     }
/*     */ 
/*     */     
/* 250 */     setAggressive(this.brain.hasMemoryValue(MemoryModuleType.ATTACK_TARGET));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void customServerAiStep() {
/* 255 */     level().getProfiler().push("zoglinBrain");
/* 256 */     getBrain().tick((ServerLevel)level(), (LivingEntity)this);
/* 257 */     level().getProfiler().pop();
/*     */     
/* 259 */     updateActivity();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBaby(boolean $$0) {
/* 264 */     getEntityData().set(DATA_BABY_ID, Boolean.valueOf($$0));
/* 265 */     if (!(level()).isClientSide && $$0) {
/* 266 */       getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(0.5D);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBaby() {
/* 272 */     return ((Boolean)getEntityData().get(DATA_BABY_ID)).booleanValue();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void aiStep() {
/* 278 */     if (this.attackAnimationRemainingTicks > 0) {
/* 279 */       this.attackAnimationRemainingTicks--;
/*     */     }
/* 281 */     super.aiStep();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleEntityEvent(byte $$0) {
/* 287 */     if ($$0 == 4) {
/*     */       
/* 289 */       this.attackAnimationRemainingTicks = 10;
/* 290 */       playSound(SoundEvents.ZOGLIN_ATTACK, 1.0F, getVoicePitch());
/*     */     } else {
/* 292 */       super.handleEntityEvent($$0);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAttackAnimationRemainingTicks() {
/* 298 */     return this.attackAnimationRemainingTicks;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/* 303 */     if ((level()).isClientSide) {
/* 304 */       return null;
/*     */     }
/* 306 */     if (this.brain.hasMemoryValue(MemoryModuleType.ATTACK_TARGET)) {
/* 307 */       return SoundEvents.ZOGLIN_ANGRY;
/*     */     }
/* 309 */     return SoundEvents.ZOGLIN_AMBIENT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource $$0) {
/* 314 */     return SoundEvents.ZOGLIN_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/* 319 */     return SoundEvents.ZOGLIN_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void playStepSound(BlockPos $$0, BlockState $$1) {
/* 324 */     playSound(SoundEvents.ZOGLIN_STEP, 0.15F, 1.0F);
/*     */   }
/*     */   
/*     */   protected void playAngrySound() {
/* 328 */     playSound(SoundEvents.ZOGLIN_ANGRY, 1.0F, getVoicePitch());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void sendDebugPackets() {
/* 333 */     super.sendDebugPackets();
/* 334 */     DebugPackets.sendEntityBrain((LivingEntity)this);
/*     */   }
/*     */ 
/*     */   
/*     */   public MobType getMobType() {
/* 339 */     return MobType.UNDEAD;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/* 344 */     super.addAdditionalSaveData($$0);
/*     */     
/* 346 */     if (isBaby()) {
/* 347 */       $$0.putBoolean("IsBaby", true);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/* 353 */     super.readAdditionalSaveData($$0);
/*     */     
/* 355 */     if ($$0.getBoolean("IsBaby"))
/* 356 */       setBaby(true); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\Zoglin.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */