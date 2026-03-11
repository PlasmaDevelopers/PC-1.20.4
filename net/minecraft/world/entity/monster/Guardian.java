/*     */ package net.minecraft.world.entity.monster;
/*     */ 
/*     */ import java.util.EnumSet;
/*     */ import java.util.function.Predicate;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.network.syncher.EntityDataAccessor;
/*     */ import net.minecraft.network.syncher.EntityDataSerializers;
/*     */ import net.minecraft.network.syncher.SynchedEntityData;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.tags.DamageTypeTags;
/*     */ import net.minecraft.tags.FluidTags;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.Difficulty;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.damagesource.DamageTypes;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityDimensions;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.MobSpawnType;
/*     */ import net.minecraft.world.entity.MobType;
/*     */ import net.minecraft.world.entity.MoverType;
/*     */ import net.minecraft.world.entity.Pose;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.entity.ai.control.LookControl;
/*     */ import net.minecraft.world.entity.ai.control.MoveControl;
/*     */ import net.minecraft.world.entity.ai.goal.Goal;
/*     */ import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
/*     */ import net.minecraft.world.entity.ai.goal.MoveTowardsRestrictionGoal;
/*     */ import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
/*     */ import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
/*     */ import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
/*     */ import net.minecraft.world.entity.ai.navigation.PathNavigation;
/*     */ import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.pathfinder.BlockPathTypes;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import org.joml.Vector3f;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Guardian
/*     */   extends Monster
/*     */ {
/*     */   protected static final int ATTACK_TIME = 80;
/*  56 */   private static final EntityDataAccessor<Boolean> DATA_ID_MOVING = SynchedEntityData.defineId(Guardian.class, EntityDataSerializers.BOOLEAN);
/*  57 */   private static final EntityDataAccessor<Integer> DATA_ID_ATTACK_TARGET = SynchedEntityData.defineId(Guardian.class, EntityDataSerializers.INT);
/*     */   
/*     */   private float clientSideTailAnimation;
/*     */   private float clientSideTailAnimationO;
/*     */   private float clientSideTailAnimationSpeed;
/*     */   private float clientSideSpikesAnimation;
/*     */   private float clientSideSpikesAnimationO;
/*     */   @Nullable
/*     */   private LivingEntity clientSideCachedAttackTarget;
/*     */   private int clientSideAttackTime;
/*     */   private boolean clientSideTouchedGround;
/*     */   @Nullable
/*     */   protected RandomStrollGoal randomStrollGoal;
/*     */   
/*     */   public Guardian(EntityType<? extends Guardian> $$0, Level $$1) {
/*  72 */     super((EntityType)$$0, $$1);
/*     */     
/*  74 */     this.xpReward = 10;
/*     */     
/*  76 */     setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
/*  77 */     this.moveControl = new GuardianMoveControl(this);
/*     */     
/*  79 */     this.clientSideTailAnimation = this.random.nextFloat();
/*  80 */     this.clientSideTailAnimationO = this.clientSideTailAnimation;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerGoals() {
/*  85 */     MoveTowardsRestrictionGoal $$0 = new MoveTowardsRestrictionGoal(this, 1.0D);
/*  86 */     this.randomStrollGoal = new RandomStrollGoal(this, 1.0D, 80);
/*     */     
/*  88 */     this.goalSelector.addGoal(4, new GuardianAttackGoal(this));
/*  89 */     this.goalSelector.addGoal(5, (Goal)$$0);
/*  90 */     this.goalSelector.addGoal(7, (Goal)this.randomStrollGoal);
/*  91 */     this.goalSelector.addGoal(8, (Goal)new LookAtPlayerGoal((Mob)this, Player.class, 8.0F));
/*     */     
/*  93 */     this.goalSelector.addGoal(8, (Goal)new LookAtPlayerGoal((Mob)this, Guardian.class, 12.0F, 0.01F));
/*  94 */     this.goalSelector.addGoal(9, (Goal)new RandomLookAroundGoal((Mob)this));
/*     */ 
/*     */     
/*  97 */     this.randomStrollGoal.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
/*  98 */     $$0.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
/*     */     
/* 100 */     this.targetSelector.addGoal(1, (Goal)new NearestAttackableTargetGoal((Mob)this, LivingEntity.class, 10, true, false, new GuardianAttackSelector(this)));
/*     */   }
/*     */   
/*     */   public static AttributeSupplier.Builder createAttributes() {
/* 104 */     return Monster.createMonsterAttributes()
/* 105 */       .add(Attributes.ATTACK_DAMAGE, 6.0D)
/* 106 */       .add(Attributes.MOVEMENT_SPEED, 0.5D)
/* 107 */       .add(Attributes.FOLLOW_RANGE, 16.0D)
/* 108 */       .add(Attributes.MAX_HEALTH, 30.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   protected PathNavigation createNavigation(Level $$0) {
/* 113 */     return (PathNavigation)new WaterBoundPathNavigation((Mob)this, $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {
/* 118 */     super.defineSynchedData();
/*     */     
/* 120 */     this.entityData.define(DATA_ID_MOVING, Boolean.valueOf(false));
/* 121 */     this.entityData.define(DATA_ID_ATTACK_TARGET, Integer.valueOf(0));
/*     */   }
/*     */ 
/*     */   
/*     */   public MobType getMobType() {
/* 126 */     return MobType.WATER;
/*     */   }
/*     */   
/*     */   public boolean isMoving() {
/* 130 */     return ((Boolean)this.entityData.get(DATA_ID_MOVING)).booleanValue();
/*     */   }
/*     */   
/*     */   void setMoving(boolean $$0) {
/* 134 */     this.entityData.set(DATA_ID_MOVING, Boolean.valueOf($$0));
/*     */   }
/*     */   
/*     */   public int getAttackDuration() {
/* 138 */     return 80;
/*     */   }
/*     */   
/*     */   void setActiveAttackTarget(int $$0) {
/* 142 */     this.entityData.set(DATA_ID_ATTACK_TARGET, Integer.valueOf($$0));
/*     */   }
/*     */   
/*     */   public boolean hasActiveAttackTarget() {
/* 146 */     return (((Integer)this.entityData.get(DATA_ID_ATTACK_TARGET)).intValue() != 0);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public LivingEntity getActiveAttackTarget() {
/* 151 */     if (!hasActiveAttackTarget()) {
/* 152 */       return null;
/*     */     }
/* 154 */     if ((level()).isClientSide) {
/* 155 */       if (this.clientSideCachedAttackTarget != null) {
/* 156 */         return this.clientSideCachedAttackTarget;
/*     */       }
/* 158 */       Entity $$0 = level().getEntity(((Integer)this.entityData.get(DATA_ID_ATTACK_TARGET)).intValue());
/* 159 */       if ($$0 instanceof LivingEntity) {
/* 160 */         this.clientSideCachedAttackTarget = (LivingEntity)$$0;
/* 161 */         return this.clientSideCachedAttackTarget;
/*     */       } 
/* 163 */       return null;
/*     */     } 
/* 165 */     return getTarget();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onSyncedDataUpdated(EntityDataAccessor<?> $$0) {
/* 170 */     super.onSyncedDataUpdated($$0);
/*     */     
/* 172 */     if (DATA_ID_ATTACK_TARGET.equals($$0)) {
/* 173 */       this.clientSideAttackTime = 0;
/* 174 */       this.clientSideCachedAttackTarget = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAmbientSoundInterval() {
/* 180 */     return 160;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/* 185 */     return isInWaterOrBubble() ? SoundEvents.GUARDIAN_AMBIENT : SoundEvents.GUARDIAN_AMBIENT_LAND;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource $$0) {
/* 190 */     return isInWaterOrBubble() ? SoundEvents.GUARDIAN_HURT : SoundEvents.GUARDIAN_HURT_LAND;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/* 195 */     return isInWaterOrBubble() ? SoundEvents.GUARDIAN_DEATH : SoundEvents.GUARDIAN_DEATH_LAND;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Entity.MovementEmission getMovementEmission() {
/* 200 */     return Entity.MovementEmission.EVENTS;
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getStandingEyeHeight(Pose $$0, EntityDimensions $$1) {
/* 205 */     return $$1.height * 0.5F;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getWalkTargetValue(BlockPos $$0, LevelReader $$1) {
/* 210 */     if ($$1.getFluidState($$0).is(FluidTags.WATER)) {
/* 211 */       return 10.0F + $$1.getPathfindingCostFromLightLevels($$0);
/*     */     }
/* 213 */     return super.getWalkTargetValue($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void aiStep() {
/* 218 */     if (isAlive()) {
/* 219 */       if ((level()).isClientSide) {
/*     */         
/* 221 */         this.clientSideTailAnimationO = this.clientSideTailAnimation;
/* 222 */         if (!isInWater()) {
/* 223 */           this.clientSideTailAnimationSpeed = 2.0F;
/* 224 */           Vec3 $$0 = getDeltaMovement();
/* 225 */           if ($$0.y > 0.0D && this.clientSideTouchedGround && !isSilent()) {
/* 226 */             level().playLocalSound(getX(), getY(), getZ(), getFlopSound(), getSoundSource(), 1.0F, 1.0F, false);
/*     */           }
/* 228 */           this.clientSideTouchedGround = ($$0.y < 0.0D && level().loadedAndEntityCanStandOn(blockPosition().below(), (Entity)this));
/* 229 */         } else if (isMoving()) {
/* 230 */           if (this.clientSideTailAnimationSpeed < 0.5F) {
/* 231 */             this.clientSideTailAnimationSpeed = 4.0F;
/*     */           } else {
/* 233 */             this.clientSideTailAnimationSpeed += (0.5F - this.clientSideTailAnimationSpeed) * 0.1F;
/*     */           } 
/*     */         } else {
/* 236 */           this.clientSideTailAnimationSpeed += (0.125F - this.clientSideTailAnimationSpeed) * 0.2F;
/*     */         } 
/* 238 */         this.clientSideTailAnimation += this.clientSideTailAnimationSpeed;
/*     */ 
/*     */         
/* 241 */         this.clientSideSpikesAnimationO = this.clientSideSpikesAnimation;
/* 242 */         if (!isInWaterOrBubble()) {
/* 243 */           this.clientSideSpikesAnimation = this.random.nextFloat();
/* 244 */         } else if (isMoving()) {
/* 245 */           this.clientSideSpikesAnimation += (0.0F - this.clientSideSpikesAnimation) * 0.25F;
/*     */         } else {
/* 247 */           this.clientSideSpikesAnimation += (1.0F - this.clientSideSpikesAnimation) * 0.06F;
/*     */         } 
/*     */         
/* 250 */         if (isMoving() && isInWater()) {
/* 251 */           Vec3 $$1 = getViewVector(0.0F);
/* 252 */           for (int $$2 = 0; $$2 < 2; $$2++) {
/* 253 */             level().addParticle((ParticleOptions)ParticleTypes.BUBBLE, getRandomX(0.5D) - $$1.x * 1.5D, getRandomY() - $$1.y * 1.5D, getRandomZ(0.5D) - $$1.z * 1.5D, 0.0D, 0.0D, 0.0D);
/*     */           }
/*     */         } 
/*     */         
/* 257 */         if (hasActiveAttackTarget()) {
/* 258 */           if (this.clientSideAttackTime < getAttackDuration()) {
/* 259 */             this.clientSideAttackTime++;
/*     */           }
/* 261 */           LivingEntity $$3 = getActiveAttackTarget();
/* 262 */           if ($$3 != null) {
/* 263 */             getLookControl().setLookAt((Entity)$$3, 90.0F, 90.0F);
/* 264 */             getLookControl().tick();
/*     */             
/* 266 */             double $$4 = getAttackAnimationScale(0.0F);
/* 267 */             double $$5 = $$3.getX() - getX();
/* 268 */             double $$6 = $$3.getY(0.5D) - getEyeY();
/* 269 */             double $$7 = $$3.getZ() - getZ();
/* 270 */             double $$8 = Math.sqrt($$5 * $$5 + $$6 * $$6 + $$7 * $$7);
/* 271 */             $$5 /= $$8;
/* 272 */             $$6 /= $$8;
/* 273 */             $$7 /= $$8;
/* 274 */             double $$9 = this.random.nextDouble();
/* 275 */             while ($$9 < $$8) {
/* 276 */               $$9 += 1.8D - $$4 + this.random.nextDouble() * (1.7D - $$4);
/* 277 */               level().addParticle((ParticleOptions)ParticleTypes.BUBBLE, getX() + $$5 * $$9, getEyeY() + $$6 * $$9, getZ() + $$7 * $$9, 0.0D, 0.0D, 0.0D);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 283 */       if (isInWaterOrBubble()) {
/* 284 */         setAirSupply(300);
/*     */       }
/* 286 */       else if (onGround()) {
/* 287 */         setDeltaMovement(getDeltaMovement().add(((this.random
/* 288 */               .nextFloat() * 2.0F - 1.0F) * 0.4F), 0.5D, ((this.random
/*     */               
/* 290 */               .nextFloat() * 2.0F - 1.0F) * 0.4F)));
/*     */         
/* 292 */         setYRot(this.random.nextFloat() * 360.0F);
/* 293 */         setOnGround(false);
/* 294 */         this.hasImpulse = true;
/*     */       } 
/*     */ 
/*     */       
/* 298 */       if (hasActiveAttackTarget()) {
/* 299 */         setYRot(this.yHeadRot);
/*     */       }
/*     */     } 
/*     */     
/* 303 */     super.aiStep();
/*     */   }
/*     */   
/*     */   protected SoundEvent getFlopSound() {
/* 307 */     return SoundEvents.GUARDIAN_FLOP;
/*     */   }
/*     */   
/*     */   public float getTailAnimation(float $$0) {
/* 311 */     return Mth.lerp($$0, this.clientSideTailAnimationO, this.clientSideTailAnimation);
/*     */   }
/*     */   
/*     */   public float getSpikesAnimation(float $$0) {
/* 315 */     return Mth.lerp($$0, this.clientSideSpikesAnimationO, this.clientSideSpikesAnimation);
/*     */   }
/*     */   
/*     */   public float getAttackAnimationScale(float $$0) {
/* 319 */     return (this.clientSideAttackTime + $$0) / getAttackDuration();
/*     */   }
/*     */   
/*     */   public float getClientSideAttackTime() {
/* 323 */     return this.clientSideAttackTime;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean checkSpawnObstruction(LevelReader $$0) {
/* 328 */     return $$0.isUnobstructed((Entity)this);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean checkGuardianSpawnRules(EntityType<? extends Guardian> $$0, LevelAccessor $$1, MobSpawnType $$2, BlockPos $$3, RandomSource $$4) {
/* 333 */     return (($$4.nextInt(20) == 0 || !$$1.canSeeSkyFromBelowWater($$3)) && $$1
/* 334 */       .getDifficulty() != Difficulty.PEACEFUL && (
/* 335 */       MobSpawnType.isSpawner($$2) || $$1.getFluidState($$3).is(FluidTags.WATER)) && $$1
/* 336 */       .getFluidState($$3.below()).is(FluidTags.WATER));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hurt(DamageSource $$0, float $$1) {
/* 341 */     if ((level()).isClientSide) {
/* 342 */       return false;
/*     */     }
/*     */     
/* 345 */     if (!isMoving() && !$$0.is(DamageTypeTags.AVOIDS_GUARDIAN_THORNS) && !$$0.is(DamageTypes.THORNS)) { Entity entity = $$0.getDirectEntity(); if (entity instanceof LivingEntity) { LivingEntity $$2 = (LivingEntity)entity;
/* 346 */         $$2.hurt(damageSources().thorns((Entity)this), 2.0F); }
/*     */        }
/*     */     
/* 349 */     if (this.randomStrollGoal != null) {
/* 350 */       this.randomStrollGoal.trigger();
/*     */     }
/*     */     
/* 353 */     return super.hurt($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxHeadXRot() {
/* 358 */     return 180;
/*     */   }
/*     */ 
/*     */   
/*     */   public void travel(Vec3 $$0) {
/* 363 */     if (isControlledByLocalInstance() && isInWater()) {
/* 364 */       moveRelative(0.1F, $$0);
/* 365 */       move(MoverType.SELF, getDeltaMovement());
/*     */       
/* 367 */       setDeltaMovement(getDeltaMovement().scale(0.9D));
/*     */       
/* 369 */       if (!isMoving() && getTarget() == null) {
/* 370 */         setDeltaMovement(getDeltaMovement().add(0.0D, -0.005D, 0.0D));
/*     */       }
/*     */     } else {
/* 373 */       super.travel($$0);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected Vector3f getPassengerAttachmentPoint(Entity $$0, EntityDimensions $$1, float $$2) {
/* 379 */     return new Vector3f(0.0F, $$1.height + 0.125F * $$2, 0.0F);
/*     */   }
/*     */   
/*     */   private static class GuardianAttackSelector implements Predicate<LivingEntity> {
/*     */     private final Guardian guardian;
/*     */     
/*     */     public GuardianAttackSelector(Guardian $$0) {
/* 386 */       this.guardian = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean test(@Nullable LivingEntity $$0) {
/* 391 */       return (($$0 instanceof Player || $$0 instanceof net.minecraft.world.entity.animal.Squid || $$0 instanceof net.minecraft.world.entity.animal.axolotl.Axolotl) && $$0.distanceToSqr((Entity)this.guardian) > 9.0D);
/*     */     }
/*     */   }
/*     */   
/*     */   private static class GuardianAttackGoal extends Goal {
/*     */     private final Guardian guardian;
/*     */     private int attackTime;
/*     */     private final boolean elder;
/*     */     
/*     */     public GuardianAttackGoal(Guardian $$0) {
/* 401 */       this.guardian = $$0;
/*     */ 
/*     */       
/* 404 */       this.elder = $$0 instanceof ElderGuardian;
/*     */       
/* 406 */       setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canUse() {
/* 411 */       LivingEntity $$0 = this.guardian.getTarget();
/* 412 */       return ($$0 != null && $$0.isAlive());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canContinueToUse() {
/* 417 */       return (super.canContinueToUse() && (this.elder || (this.guardian.getTarget() != null && this.guardian.distanceToSqr((Entity)this.guardian.getTarget()) > 9.0D)));
/*     */     }
/*     */ 
/*     */     
/*     */     public void start() {
/* 422 */       this.attackTime = -10;
/* 423 */       this.guardian.getNavigation().stop();
/* 424 */       LivingEntity $$0 = this.guardian.getTarget();
/* 425 */       if ($$0 != null) {
/* 426 */         this.guardian.getLookControl().setLookAt((Entity)$$0, 90.0F, 90.0F);
/*     */       }
/*     */ 
/*     */       
/* 430 */       this.guardian.hasImpulse = true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void stop() {
/* 435 */       this.guardian.setActiveAttackTarget(0);
/* 436 */       this.guardian.setTarget(null);
/*     */       
/* 438 */       this.guardian.randomStrollGoal.trigger();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean requiresUpdateEveryTick() {
/* 443 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick() {
/* 448 */       LivingEntity $$0 = this.guardian.getTarget();
/* 449 */       if ($$0 == null) {
/*     */         return;
/*     */       }
/*     */       
/* 453 */       this.guardian.getNavigation().stop();
/* 454 */       this.guardian.getLookControl().setLookAt((Entity)$$0, 90.0F, 90.0F);
/*     */       
/* 456 */       if (!this.guardian.hasLineOfSight((Entity)$$0)) {
/* 457 */         this.guardian.setTarget(null);
/*     */         
/*     */         return;
/*     */       } 
/* 461 */       this.attackTime++;
/* 462 */       if (this.attackTime == 0) {
/*     */         
/* 464 */         this.guardian.setActiveAttackTarget($$0.getId());
/* 465 */         if (!this.guardian.isSilent()) {
/* 466 */           this.guardian.level().broadcastEntityEvent((Entity)this.guardian, (byte)21);
/*     */         }
/* 468 */       } else if (this.attackTime >= this.guardian.getAttackDuration()) {
/* 469 */         float $$1 = 1.0F;
/* 470 */         if (this.guardian.level().getDifficulty() == Difficulty.HARD) {
/* 471 */           $$1 += 2.0F;
/*     */         }
/* 473 */         if (this.elder) {
/* 474 */           $$1 += 2.0F;
/*     */         }
/* 476 */         $$0.hurt(this.guardian.damageSources().indirectMagic((Entity)this.guardian, (Entity)this.guardian), $$1);
/* 477 */         $$0.hurt(this.guardian.damageSources().mobAttack((LivingEntity)this.guardian), (float)this.guardian.getAttributeValue(Attributes.ATTACK_DAMAGE));
/* 478 */         this.guardian.setTarget(null);
/*     */       } 
/*     */       
/* 481 */       super.tick();
/*     */     }
/*     */   }
/*     */   
/*     */   private static class GuardianMoveControl extends MoveControl {
/*     */     private final Guardian guardian;
/*     */     
/*     */     public GuardianMoveControl(Guardian $$0) {
/* 489 */       super((Mob)$$0);
/* 490 */       this.guardian = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick() {
/* 495 */       if (this.operation != MoveControl.Operation.MOVE_TO || this.guardian.getNavigation().isDone()) {
/*     */         
/* 497 */         this.guardian.setSpeed(0.0F);
/* 498 */         this.guardian.setMoving(false);
/*     */ 
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */ 
/*     */       
/* 506 */       Vec3 $$0 = new Vec3(this.wantedX - this.guardian.getX(), this.wantedY - this.guardian.getY(), this.wantedZ - this.guardian.getZ());
/*     */       
/* 508 */       double $$1 = $$0.length();
/*     */       
/* 510 */       double $$2 = $$0.x / $$1;
/* 511 */       double $$3 = $$0.y / $$1;
/* 512 */       double $$4 = $$0.z / $$1;
/*     */       
/* 514 */       float $$5 = (float)(Mth.atan2($$0.z, $$0.x) * 57.2957763671875D) - 90.0F;
/*     */       
/* 516 */       this.guardian.setYRot(rotlerp(this.guardian.getYRot(), $$5, 90.0F));
/* 517 */       this.guardian.yBodyRot = this.guardian.getYRot();
/*     */       
/* 519 */       float $$6 = (float)(this.speedModifier * this.guardian.getAttributeValue(Attributes.MOVEMENT_SPEED));
/* 520 */       float $$7 = Mth.lerp(0.125F, this.guardian.getSpeed(), $$6);
/* 521 */       this.guardian.setSpeed($$7);
/* 522 */       double $$8 = Math.sin((this.guardian.tickCount + this.guardian.getId()) * 0.5D) * 0.05D;
/* 523 */       double $$9 = Math.cos((this.guardian.getYRot() * 0.017453292F));
/* 524 */       double $$10 = Math.sin((this.guardian.getYRot() * 0.017453292F));
/* 525 */       double $$11 = Math.sin((this.guardian.tickCount + this.guardian.getId()) * 0.75D) * 0.05D;
/*     */       
/* 527 */       this.guardian.setDeltaMovement(this.guardian.getDeltaMovement().add($$8 * $$9, $$11 * ($$10 + $$9) * 0.25D + $$7 * $$3 * 0.1D, $$8 * $$10));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 533 */       LookControl $$12 = this.guardian.getLookControl();
/* 534 */       double $$13 = this.guardian.getX() + $$2 * 2.0D;
/* 535 */       double $$14 = this.guardian.getEyeY() + $$3 / $$1;
/* 536 */       double $$15 = this.guardian.getZ() + $$4 * 2.0D;
/* 537 */       double $$16 = $$12.getWantedX();
/* 538 */       double $$17 = $$12.getWantedY();
/* 539 */       double $$18 = $$12.getWantedZ();
/* 540 */       if (!$$12.isLookingAtTarget()) {
/* 541 */         $$16 = $$13;
/* 542 */         $$17 = $$14;
/* 543 */         $$18 = $$15;
/*     */       } 
/* 545 */       this.guardian.getLookControl().setLookAt(Mth.lerp(0.125D, $$16, $$13), Mth.lerp(0.125D, $$17, $$14), Mth.lerp(0.125D, $$18, $$15), 10.0F, 40.0F);
/* 546 */       this.guardian.setMoving(true);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\Guardian.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */