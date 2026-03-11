/*     */ package net.minecraft.world.entity.animal.camel;
/*     */ import com.mojang.serialization.Dynamic;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.protocol.game.DebugPackets;
/*     */ import net.minecraft.network.syncher.EntityDataAccessor;
/*     */ import net.minecraft.network.syncher.EntityDataSerializers;
/*     */ import net.minecraft.network.syncher.SynchedEntityData;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.Container;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.entity.AgeableMob;
/*     */ import net.minecraft.world.entity.AnimationState;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityDimensions;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.MobSpawnType;
/*     */ import net.minecraft.world.entity.PlayerRideableJumping;
/*     */ import net.minecraft.world.entity.Pose;
/*     */ import net.minecraft.world.entity.SpawnGroupData;
/*     */ import net.minecraft.world.entity.ai.Brain;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.entity.ai.control.BodyRotationControl;
/*     */ import net.minecraft.world.entity.ai.control.LookControl;
/*     */ import net.minecraft.world.entity.ai.control.MoveControl;
/*     */ import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
/*     */ import net.minecraft.world.entity.animal.Animal;
/*     */ import net.minecraft.world.entity.animal.horse.AbstractHorse;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.item.crafting.Ingredient;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.ServerLevelAccessor;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.phys.Vec2;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import org.joml.Vector3f;
/*     */ 
/*     */ public class Camel extends AbstractHorse implements PlayerRideableJumping, Saddleable {
/*  57 */   public static final Ingredient TEMPTATION_ITEM = Ingredient.of(new ItemLike[] { (ItemLike)Items.CACTUS });
/*     */   
/*     */   public static final float BABY_SCALE = 0.45F;
/*     */   
/*     */   public static final int DASH_COOLDOWN_TICKS = 55;
/*     */   
/*     */   public static final int MAX_HEAD_Y_ROT = 30;
/*     */   
/*     */   private static final float RUNNING_SPEED_BONUS = 0.1F;
/*     */   
/*     */   private static final float DASH_VERTICAL_MOMENTUM = 1.4285F;
/*     */   
/*     */   private static final float DASH_HORIZONTAL_MOMENTUM = 22.2222F;
/*     */   
/*     */   private static final int DASH_MINIMUM_DURATION_TICKS = 5;
/*     */   
/*     */   private static final int SITDOWN_DURATION_TICKS = 40;
/*     */   private static final int STANDUP_DURATION_TICKS = 52;
/*     */   private static final int IDLE_MINIMAL_DURATION_TICKS = 80;
/*     */   private static final float SITTING_HEIGHT_DIFFERENCE = 1.43F;
/*  77 */   public static final EntityDataAccessor<Boolean> DASH = SynchedEntityData.defineId(Camel.class, EntityDataSerializers.BOOLEAN);
/*     */   
/*  79 */   public static final EntityDataAccessor<Long> LAST_POSE_CHANGE_TICK = SynchedEntityData.defineId(Camel.class, EntityDataSerializers.LONG);
/*     */   
/*  81 */   public final AnimationState sitAnimationState = new AnimationState();
/*  82 */   public final AnimationState sitPoseAnimationState = new AnimationState();
/*  83 */   public final AnimationState sitUpAnimationState = new AnimationState();
/*  84 */   public final AnimationState idleAnimationState = new AnimationState();
/*  85 */   public final AnimationState dashAnimationState = new AnimationState();
/*     */   
/*  87 */   private static final EntityDimensions SITTING_DIMENSIONS = EntityDimensions.scalable(EntityType.CAMEL.getWidth(), EntityType.CAMEL.getHeight() - 1.43F);
/*     */   
/*  89 */   private int dashCooldown = 0;
/*     */ 
/*     */   
/*  92 */   private int idleAnimationTimeout = 0;
/*     */   
/*     */   public Camel(EntityType<? extends Camel> $$0, Level $$1) {
/*  95 */     super($$0, $$1);
/*  96 */     setMaxUpStep(1.5F);
/*  97 */     this.moveControl = new CamelMoveControl();
/*  98 */     this.lookControl = new CamelLookControl();
/*  99 */     GroundPathNavigation $$2 = (GroundPathNavigation)getNavigation();
/* 100 */     $$2.setCanFloat(true);
/* 101 */     $$2.setCanWalkOverFences(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/* 106 */     super.addAdditionalSaveData($$0);
/* 107 */     $$0.putLong("LastPoseTick", ((Long)this.entityData.get(LAST_POSE_CHANGE_TICK)).longValue());
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/* 112 */     super.readAdditionalSaveData($$0);
/* 113 */     long $$1 = $$0.getLong("LastPoseTick");
/* 114 */     if ($$1 < 0L) {
/* 115 */       setPose(Pose.SITTING);
/*     */     }
/* 117 */     resetLastPoseChangeTick($$1);
/*     */   }
/*     */   
/*     */   public static AttributeSupplier.Builder createAttributes() {
/* 121 */     return createBaseHorseAttributes()
/* 122 */       .add(Attributes.MAX_HEALTH, 32.0D)
/* 123 */       .add(Attributes.MOVEMENT_SPEED, 0.09000000357627869D)
/* 124 */       .add(Attributes.JUMP_STRENGTH, 0.41999998688697815D);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {
/* 129 */     super.defineSynchedData();
/* 130 */     this.entityData.define(DASH, Boolean.valueOf(false));
/* 131 */     this.entityData.define(LAST_POSE_CHANGE_TICK, Long.valueOf(0L));
/*     */   }
/*     */ 
/*     */   
/*     */   public SpawnGroupData finalizeSpawn(ServerLevelAccessor $$0, DifficultyInstance $$1, MobSpawnType $$2, @Nullable SpawnGroupData $$3, @Nullable CompoundTag $$4) {
/* 136 */     CamelAi.initMemories(this, $$0.getRandom());
/* 137 */     resetLastPoseChangeTickToFullStand($$0.getLevel().getGameTime());
/* 138 */     return super.finalizeSpawn($$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Brain.Provider<Camel> brainProvider() {
/* 143 */     return CamelAi.brainProvider();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void registerGoals() {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected Brain<?> makeBrain(Dynamic<?> $$0) {
/* 154 */     return CamelAi.makeBrain(brainProvider().makeBrain($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityDimensions getDimensions(Pose $$0) {
/* 159 */     return ($$0 == Pose.SITTING) ? SITTING_DIMENSIONS.scale(getScale()) : super.getDimensions($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getStandingEyeHeight(Pose $$0, EntityDimensions $$1) {
/* 164 */     return $$1.height - 0.1F * getScale();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void customServerAiStep() {
/* 169 */     level().getProfiler().push("camelBrain");
/* 170 */     Brain<?> $$0 = getBrain();
/* 171 */     $$0.tick((ServerLevel)level(), (LivingEntity)this);
/* 172 */     level().getProfiler().pop();
/*     */     
/* 174 */     level().getProfiler().push("camelActivityUpdate");
/* 175 */     CamelAi.updateActivity(this);
/* 176 */     level().getProfiler().pop();
/*     */     
/* 178 */     super.customServerAiStep();
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 183 */     super.tick();
/* 184 */     if (isDashing() && this.dashCooldown < 50 && (onGround() || isInLiquid() || isPassenger())) {
/* 185 */       setDashing(false);
/*     */     }
/* 187 */     if (this.dashCooldown > 0) {
/* 188 */       this.dashCooldown--;
/* 189 */       if (this.dashCooldown == 0) {
/* 190 */         level().playSound(null, blockPosition(), SoundEvents.CAMEL_DASH_READY, SoundSource.NEUTRAL, 1.0F, 1.0F);
/*     */       }
/*     */     } 
/* 193 */     if (level().isClientSide()) {
/* 194 */       setupAnimationStates();
/*     */     }
/*     */     
/* 197 */     if (refuseToMove()) {
/* 198 */       clampHeadRotationToBody((Entity)this, 30.0F);
/*     */     }
/*     */     
/* 201 */     if (isCamelSitting() && isInWater()) {
/* 202 */       standUpInstantly();
/*     */     }
/*     */   }
/*     */   
/*     */   private void setupAnimationStates() {
/* 207 */     if (this.idleAnimationTimeout <= 0) {
/* 208 */       this.idleAnimationTimeout = this.random.nextInt(40) + 80;
/* 209 */       this.idleAnimationState.start(this.tickCount);
/*     */     } else {
/* 211 */       this.idleAnimationTimeout--;
/*     */     } 
/* 213 */     if (isCamelVisuallySitting()) {
/* 214 */       this.sitUpAnimationState.stop();
/* 215 */       this.dashAnimationState.stop();
/* 216 */       if (isVisuallySittingDown()) {
/* 217 */         this.sitAnimationState.startIfStopped(this.tickCount);
/* 218 */         this.sitPoseAnimationState.stop();
/*     */       } else {
/* 220 */         this.sitAnimationState.stop();
/* 221 */         this.sitPoseAnimationState.startIfStopped(this.tickCount);
/*     */       } 
/*     */     } else {
/* 224 */       this.sitAnimationState.stop();
/* 225 */       this.sitPoseAnimationState.stop();
/* 226 */       this.dashAnimationState.animateWhen(isDashing(), this.tickCount);
/* 227 */       this.sitUpAnimationState.animateWhen((isInPoseTransition() && getPoseTime() >= 0L), this.tickCount);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateWalkAnimation(float $$0) {
/*     */     float $$2;
/* 234 */     if (getPose() == Pose.STANDING && !this.dashAnimationState.isStarted()) {
/* 235 */       float $$1 = Math.min($$0 * 6.0F, 1.0F);
/*     */     } else {
/* 237 */       $$2 = 0.0F;
/*     */     } 
/* 239 */     this.walkAnimation.update($$2, 0.2F);
/*     */   }
/*     */ 
/*     */   
/*     */   public void travel(Vec3 $$0) {
/* 244 */     if (refuseToMove() && onGround()) {
/* 245 */       setDeltaMovement(getDeltaMovement().multiply(0.0D, 1.0D, 0.0D));
/* 246 */       $$0 = $$0.multiply(0.0D, 1.0D, 0.0D);
/*     */     } 
/* 248 */     super.travel($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void tickRidden(Player $$0, Vec3 $$1) {
/* 253 */     super.tickRidden($$0, $$1);
/* 254 */     if ($$0.zza > 0.0F && 
/* 255 */       isCamelSitting() && !isInPoseTransition()) {
/* 256 */       standUp();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean refuseToMove() {
/* 262 */     return (isCamelSitting() || isInPoseTransition());
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getRiddenSpeed(Player $$0) {
/* 267 */     float $$1 = ($$0.isSprinting() && getJumpCooldown() == 0) ? 0.1F : 0.0F;
/* 268 */     return (float)getAttributeValue(Attributes.MOVEMENT_SPEED) + $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Vec2 getRiddenRotation(LivingEntity $$0) {
/* 273 */     if (refuseToMove()) {
/* 274 */       return new Vec2(getXRot(), getYRot());
/*     */     }
/* 276 */     return super.getRiddenRotation($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Vec3 getRiddenInput(Player $$0, Vec3 $$1) {
/* 281 */     if (refuseToMove()) {
/* 282 */       return Vec3.ZERO;
/*     */     }
/* 284 */     return super.getRiddenInput($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canJump() {
/* 289 */     return (!refuseToMove() && super.canJump());
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPlayerJump(int $$0) {
/* 294 */     if (!isSaddled() || this.dashCooldown > 0 || !onGround()) {
/*     */       return;
/*     */     }
/* 297 */     super.onPlayerJump($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canSprint() {
/* 302 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void executeRidersJump(float $$0, Vec3 $$1) {
/* 307 */     double $$2 = getAttributeValue(Attributes.JUMP_STRENGTH) * getBlockJumpFactor() + getJumpBoostPower();
/*     */     
/* 309 */     addDeltaMovement(getLookAngle().multiply(1.0D, 0.0D, 1.0D).normalize()
/* 310 */         .scale((22.2222F * $$0) * getAttributeValue(Attributes.MOVEMENT_SPEED) * getBlockSpeedFactor())
/* 311 */         .add(0.0D, (1.4285F * $$0) * $$2, 0.0D));
/*     */ 
/*     */     
/* 314 */     this.dashCooldown = 55;
/* 315 */     setDashing(true);
/* 316 */     this.hasImpulse = true;
/*     */   }
/*     */   
/*     */   public boolean isDashing() {
/* 320 */     return ((Boolean)this.entityData.get(DASH)).booleanValue();
/*     */   }
/*     */   
/*     */   public void setDashing(boolean $$0) {
/* 324 */     this.entityData.set(DASH, Boolean.valueOf($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleStartJump(int $$0) {
/* 329 */     playSound(SoundEvents.CAMEL_DASH, 1.0F, getVoicePitch());
/* 330 */     gameEvent(GameEvent.ENTITY_ACTION);
/* 331 */     setDashing(true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleStopJump() {}
/*     */ 
/*     */   
/*     */   public int getJumpCooldown() {
/* 340 */     return this.dashCooldown;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/* 345 */     return SoundEvents.CAMEL_AMBIENT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/* 350 */     return SoundEvents.CAMEL_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource $$0) {
/* 355 */     return SoundEvents.CAMEL_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void playStepSound(BlockPos $$0, BlockState $$1) {
/* 360 */     if ($$1.is(BlockTags.CAMEL_SAND_STEP_SOUND_BLOCKS)) {
/* 361 */       playSound(SoundEvents.CAMEL_STEP_SAND, 1.0F, 1.0F);
/*     */     } else {
/* 363 */       playSound(SoundEvents.CAMEL_STEP, 1.0F, 1.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFood(ItemStack $$0) {
/* 369 */     return TEMPTATION_ITEM.test($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult mobInteract(Player $$0, InteractionHand $$1) {
/* 374 */     ItemStack $$2 = $$0.getItemInHand($$1);
/*     */     
/* 376 */     if ($$0.isSecondaryUseActive() && !isBaby()) {
/* 377 */       openCustomInventoryScreen($$0);
/* 378 */       return InteractionResult.sidedSuccess((level()).isClientSide);
/*     */     } 
/*     */ 
/*     */     
/* 382 */     InteractionResult $$3 = $$2.interactLivingEntity($$0, (LivingEntity)this, $$1);
/* 383 */     if ($$3.consumesAction()) {
/* 384 */       return $$3;
/*     */     }
/*     */     
/* 387 */     if (isFood($$2)) {
/* 388 */       return fedFood($$0, $$2);
/*     */     }
/*     */     
/* 391 */     if (getPassengers().size() < 2 && !isBaby()) {
/* 392 */       doPlayerRide($$0);
/*     */     }
/* 394 */     return InteractionResult.sidedSuccess((level()).isClientSide);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onLeashDistance(float $$0) {
/* 399 */     if ($$0 > 6.0F && isCamelSitting() && !isInPoseTransition() && canCamelChangePose()) {
/* 400 */       standUp();
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean canCamelChangePose() {
/* 405 */     return wouldNotSuffocateAtTargetPose(isCamelSitting() ? Pose.STANDING : Pose.SITTING);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean handleEating(Player $$0, ItemStack $$1) {
/* 410 */     if (!isFood($$1)) {
/* 411 */       return false;
/*     */     }
/*     */     
/* 414 */     boolean $$2 = (getHealth() < getMaxHealth());
/* 415 */     if ($$2) {
/* 416 */       heal(2.0F);
/*     */     }
/*     */     
/* 419 */     boolean $$3 = (isTamed() && getAge() == 0 && canFallInLove());
/* 420 */     if ($$3) {
/* 421 */       setInLove($$0);
/*     */     }
/*     */     
/* 424 */     boolean $$4 = isBaby();
/* 425 */     if ($$4) {
/* 426 */       level().addParticle((ParticleOptions)ParticleTypes.HAPPY_VILLAGER, getRandomX(1.0D), getRandomY() + 0.5D, getRandomZ(1.0D), 0.0D, 0.0D, 0.0D);
/* 427 */       if (!(level()).isClientSide) {
/* 428 */         ageUp(10);
/*     */       }
/*     */     } 
/*     */     
/* 432 */     if ($$2 || $$3 || $$4) {
/* 433 */       if (!isSilent()) {
/* 434 */         SoundEvent $$5 = getEatingSound();
/* 435 */         if ($$5 != null) {
/* 436 */           level().playSound(null, getX(), getY(), getZ(), $$5, getSoundSource(), 1.0F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
/*     */         }
/*     */       } 
/* 439 */       gameEvent(GameEvent.EAT);
/* 440 */       return true;
/*     */     } 
/* 442 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canPerformRearing() {
/* 447 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canMate(Animal $$0) {
/* 452 */     if ($$0 != this && $$0 instanceof Camel) { Camel $$1 = (Camel)$$0; if (canParent() && $$1.canParent()); }  return false;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Camel getBreedOffspring(ServerLevel $$0, AgeableMob $$1) {
/* 458 */     return (Camel)EntityType.CAMEL.create((Level)$$0);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected SoundEvent getEatingSound() {
/* 464 */     return SoundEvents.CAMEL_EAT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void actuallyHurt(DamageSource $$0, float $$1) {
/* 469 */     standUpInstantly();
/* 470 */     super.actuallyHurt($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Vector3f getPassengerAttachmentPoint(Entity $$0, EntityDimensions $$1, float $$2) {
/* 475 */     int $$3 = Math.max(getPassengers().indexOf($$0), 0);
/* 476 */     boolean $$4 = ($$3 == 0);
/* 477 */     float $$5 = 0.5F;
/* 478 */     float $$6 = (float)(isRemoved() ? 0.009999999776482582D : getBodyAnchorAnimationYOffset($$4, 0.0F, $$1, $$2));
/*     */     
/* 480 */     if (getPassengers().size() > 1) {
/* 481 */       if (!$$4) {
/* 482 */         $$5 = -0.7F;
/*     */       }
/*     */       
/* 485 */       if ($$0 instanceof Animal) {
/* 486 */         $$5 += 0.2F;
/*     */       }
/*     */     } 
/*     */     
/* 490 */     return new Vector3f(0.0F, $$6, $$5 * $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getScale() {
/* 495 */     return isBaby() ? 0.45F : 1.0F;
/*     */   }
/*     */   
/*     */   private double getBodyAnchorAnimationYOffset(boolean $$0, float $$1, EntityDimensions $$2, float $$3) {
/* 499 */     double $$4 = ($$2.height - 0.375F * $$3);
/* 500 */     float $$5 = $$3 * 1.43F;
/* 501 */     float $$6 = $$5 - $$3 * 0.2F;
/* 502 */     float $$7 = $$5 - $$6;
/* 503 */     boolean $$8 = isInPoseTransition();
/* 504 */     boolean $$9 = isCamelSitting();
/* 505 */     if ($$8) {
/*     */       int $$13; float $$14;
/* 507 */       int $$10 = $$9 ? 40 : 52;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 512 */       if ($$9) {
/* 513 */         int $$11 = 28;
/* 514 */         float $$12 = $$0 ? 0.5F : 0.1F;
/*     */       } else {
/* 516 */         $$13 = $$0 ? 24 : 32;
/* 517 */         $$14 = $$0 ? 0.6F : 0.35F;
/*     */       } 
/* 519 */       float $$15 = Mth.clamp((float)getPoseTime() + $$1, 0.0F, $$10);
/* 520 */       boolean $$16 = ($$15 < $$13);
/* 521 */       float $$17 = $$16 ? ($$15 / $$13) : (($$15 - $$13) / ($$10 - $$13));
/* 522 */       float $$18 = $$5 - $$14 * $$6;
/* 523 */       $$4 += $$9 ? 
/* 524 */         Mth.lerp($$17, $$16 ? $$5 : $$18, $$16 ? $$18 : $$7) : 
/* 525 */         Mth.lerp($$17, $$16 ? ($$7 - $$5) : ($$7 - $$18), $$16 ? ($$7 - $$18) : 0.0F);
/*     */     } 
/* 527 */     if ($$9 && !$$8) {
/* 528 */       $$4 += $$7;
/*     */     }
/* 530 */     return $$4;
/*     */   }
/*     */ 
/*     */   
/*     */   public Vec3 getLeashOffset(float $$0) {
/* 535 */     EntityDimensions $$1 = getDimensions(getPose());
/* 536 */     float $$2 = getScale();
/* 537 */     return new Vec3(0.0D, getBodyAnchorAnimationYOffset(true, $$0, $$1, $$2) - (0.2F * $$2), ($$1.width * 0.56F));
/*     */   }
/*     */   
/*     */   private void clampHeadRotationToBody(Entity $$0, float $$1) {
/* 541 */     float $$2 = $$0.getYHeadRot();
/* 542 */     float $$3 = Mth.wrapDegrees(this.yBodyRot - $$2);
/* 543 */     float $$4 = Mth.clamp(Mth.wrapDegrees(this.yBodyRot - $$2), -$$1, $$1);
/* 544 */     float $$5 = $$2 + $$3 - $$4;
/* 545 */     $$0.setYHeadRot($$5);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxHeadYRot() {
/* 550 */     return 30;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canAddPassenger(Entity $$0) {
/* 555 */     return (getPassengers().size() <= 2);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void sendDebugPackets() {
/* 560 */     super.sendDebugPackets();
/* 561 */     DebugPackets.sendEntityBrain((LivingEntity)this);
/*     */   }
/*     */   
/*     */   public boolean isCamelSitting() {
/* 565 */     return (((Long)this.entityData.get(LAST_POSE_CHANGE_TICK)).longValue() < 0L);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCamelVisuallySitting() {
/* 570 */     return (((getPoseTime() < 0L)) != isCamelSitting());
/*     */   }
/*     */   
/*     */   public boolean isInPoseTransition() {
/* 574 */     long $$0 = getPoseTime();
/* 575 */     return ($$0 < (isCamelSitting() ? 40L : 52L));
/*     */   }
/*     */   
/*     */   private boolean isVisuallySittingDown() {
/* 579 */     return (isCamelSitting() && getPoseTime() < 40L && getPoseTime() >= 0L);
/*     */   }
/*     */   
/*     */   public void sitDown() {
/* 583 */     if (isCamelSitting()) {
/*     */       return;
/*     */     }
/* 586 */     playSound(SoundEvents.CAMEL_SIT, 1.0F, getVoicePitch());
/* 587 */     setPose(Pose.SITTING);
/* 588 */     gameEvent(GameEvent.ENTITY_ACTION);
/* 589 */     resetLastPoseChangeTick(-level().getGameTime());
/*     */   }
/*     */   
/*     */   public void standUp() {
/* 593 */     if (!isCamelSitting()) {
/*     */       return;
/*     */     }
/* 596 */     playSound(SoundEvents.CAMEL_STAND, 1.0F, getVoicePitch());
/* 597 */     setPose(Pose.STANDING);
/* 598 */     gameEvent(GameEvent.ENTITY_ACTION);
/* 599 */     resetLastPoseChangeTick(level().getGameTime());
/*     */   }
/*     */   
/*     */   public void standUpInstantly() {
/* 603 */     setPose(Pose.STANDING);
/* 604 */     gameEvent(GameEvent.ENTITY_ACTION);
/* 605 */     resetLastPoseChangeTickToFullStand(level().getGameTime());
/*     */   }
/*     */   
/*     */   @VisibleForTesting
/*     */   public void resetLastPoseChangeTick(long $$0) {
/* 610 */     this.entityData.set(LAST_POSE_CHANGE_TICK, Long.valueOf($$0));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void resetLastPoseChangeTickToFullStand(long $$0) {
/* 616 */     resetLastPoseChangeTick(Math.max(0L, $$0 - 52L - 1L));
/*     */   }
/*     */   
/*     */   public long getPoseTime() {
/* 620 */     return level().getGameTime() - Math.abs(((Long)this.entityData.get(LAST_POSE_CHANGE_TICK)).longValue());
/*     */   }
/*     */ 
/*     */   
/*     */   public SoundEvent getSaddleSoundEvent() {
/* 625 */     return SoundEvents.CAMEL_SADDLE;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onSyncedDataUpdated(EntityDataAccessor<?> $$0) {
/* 630 */     if (!this.firstTick && 
/* 631 */       DASH.equals($$0)) {
/* 632 */       this.dashCooldown = (this.dashCooldown == 0) ? 55 : this.dashCooldown;
/*     */     }
/*     */     
/* 635 */     super.onSyncedDataUpdated($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isTamed() {
/* 640 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void openCustomInventoryScreen(Player $$0) {
/* 645 */     if (!(level()).isClientSide) {
/* 646 */       $$0.openHorseInventory(this, (Container)this.inventory);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected BodyRotationControl createBodyControl() {
/* 652 */     return new CamelBodyRotationControl(this);
/*     */   }
/*     */   
/*     */   private class CamelBodyRotationControl extends BodyRotationControl {
/*     */     public CamelBodyRotationControl(Camel $$0) {
/* 657 */       super((Mob)$$0);
/*     */     }
/*     */ 
/*     */     
/*     */     public void clientTick() {
/* 662 */       if (!Camel.this.refuseToMove())
/* 663 */         super.clientTick(); 
/*     */     }
/*     */   }
/*     */   
/*     */   private class CamelLookControl
/*     */     extends LookControl {
/*     */     CamelLookControl() {
/* 670 */       super((Mob)Camel.this);
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick() {
/* 675 */       if (!Camel.this.hasControllingPassenger())
/* 676 */         super.tick(); 
/*     */     }
/*     */   }
/*     */   
/*     */   private class CamelMoveControl
/*     */     extends MoveControl {
/*     */     public CamelMoveControl() {
/* 683 */       super((Mob)Camel.this);
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick() {
/* 688 */       if (this.operation == MoveControl.Operation.MOVE_TO && !Camel.this.isLeashed() && Camel.this.isCamelSitting() && !Camel.this.isInPoseTransition() && Camel.this.canCamelChangePose()) {
/* 689 */         Camel.this.standUp();
/*     */       }
/* 691 */       super.tick();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\animal\camel\Camel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */