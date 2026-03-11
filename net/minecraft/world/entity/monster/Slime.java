/*     */ package net.minecraft.world.entity.monster;
/*     */ 
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import java.util.EnumSet;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.syncher.EntityDataAccessor;
/*     */ import net.minecraft.network.syncher.EntityDataSerializers;
/*     */ import net.minecraft.network.syncher.SynchedEntityData;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.tags.BiomeTags;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.Difficulty;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.effect.MobEffects;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityDimensions;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.MobSpawnType;
/*     */ import net.minecraft.world.entity.Pose;
/*     */ import net.minecraft.world.entity.SpawnGroupData;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.entity.ai.control.MoveControl;
/*     */ import net.minecraft.world.entity.ai.goal.Goal;
/*     */ import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
/*     */ import net.minecraft.world.entity.animal.IronGolem;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.ServerLevelAccessor;
/*     */ import net.minecraft.world.level.WorldGenLevel;
/*     */ import net.minecraft.world.level.levelgen.WorldgenRandom;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import org.joml.Vector3f;
/*     */ 
/*     */ public class Slime
/*     */   extends Mob
/*     */   implements Enemy {
/*  50 */   private static final EntityDataAccessor<Integer> ID_SIZE = SynchedEntityData.defineId(Slime.class, EntityDataSerializers.INT);
/*     */   
/*     */   public static final int MIN_SIZE = 1;
/*     */   public static final int MAX_SIZE = 127;
/*     */   public float targetSquish;
/*     */   public float squish;
/*     */   public float oSquish;
/*     */   private boolean wasOnGround;
/*     */   
/*     */   public Slime(EntityType<? extends Slime> $$0, Level $$1) {
/*  60 */     super($$0, $$1);
/*     */ 
/*     */     
/*  63 */     fixupDimensions();
/*  64 */     this.moveControl = new SlimeMoveControl(this);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerGoals() {
/*  69 */     this.goalSelector.addGoal(1, new SlimeFloatGoal(this));
/*     */     
/*  71 */     this.goalSelector.addGoal(2, new SlimeAttackGoal(this));
/*  72 */     this.goalSelector.addGoal(3, new SlimeRandomDirectionGoal(this));
/*     */     
/*  74 */     this.goalSelector.addGoal(5, new SlimeKeepOnJumpingGoal(this));
/*     */ 
/*     */     
/*  77 */     this.targetSelector.addGoal(1, (Goal)new NearestAttackableTargetGoal(this, Player.class, 10, true, false, $$0 -> (Math.abs($$0.getY() - getY()) <= 4.0D)));
/*  78 */     this.targetSelector.addGoal(3, (Goal)new NearestAttackableTargetGoal(this, IronGolem.class, true));
/*     */   }
/*     */ 
/*     */   
/*     */   public SoundSource getSoundSource() {
/*  83 */     return SoundSource.HOSTILE;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {
/*  88 */     super.defineSynchedData();
/*     */     
/*  90 */     this.entityData.define(ID_SIZE, Integer.valueOf(1));
/*     */   }
/*     */   
/*     */   @VisibleForTesting
/*     */   public void setSize(int $$0, boolean $$1) {
/*  95 */     int $$2 = Mth.clamp($$0, 1, 127);
/*  96 */     this.entityData.set(ID_SIZE, Integer.valueOf($$2));
/*  97 */     reapplyPosition();
/*     */     
/*  99 */     refreshDimensions();
/*     */     
/* 101 */     getAttribute(Attributes.MAX_HEALTH).setBaseValue(($$2 * $$2));
/* 102 */     getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue((0.2F + 0.1F * $$2));
/* 103 */     getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue($$2);
/* 104 */     if ($$1) {
/* 105 */       setHealth(getMaxHealth());
/*     */     }
/* 107 */     this.xpReward = $$2;
/*     */   }
/*     */   
/*     */   public int getSize() {
/* 111 */     return ((Integer)this.entityData.get(ID_SIZE)).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/* 116 */     super.addAdditionalSaveData($$0);
/* 117 */     $$0.putInt("Size", getSize() - 1);
/* 118 */     $$0.putBoolean("wasOnGround", this.wasOnGround);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/* 123 */     setSize($$0.getInt("Size") + 1, false);
/* 124 */     super.readAdditionalSaveData($$0);
/* 125 */     this.wasOnGround = $$0.getBoolean("wasOnGround");
/*     */   }
/*     */   
/*     */   public boolean isTiny() {
/* 129 */     return (getSize() <= 1);
/*     */   }
/*     */   
/*     */   protected ParticleOptions getParticleType() {
/* 133 */     return (ParticleOptions)ParticleTypes.ITEM_SLIME;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean shouldDespawnInPeaceful() {
/* 138 */     return (getSize() > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 143 */     this.squish += (this.targetSquish - this.squish) * 0.5F;
/* 144 */     this.oSquish = this.squish;
/* 145 */     super.tick();
/*     */     
/* 147 */     if (onGround() && !this.wasOnGround) {
/* 148 */       int $$0 = getSize();
/* 149 */       for (int $$1 = 0; $$1 < $$0 * 8; $$1++) {
/* 150 */         float $$2 = this.random.nextFloat() * 6.2831855F;
/* 151 */         float $$3 = this.random.nextFloat() * 0.5F + 0.5F;
/* 152 */         float $$4 = Mth.sin($$2) * $$0 * 0.5F * $$3;
/* 153 */         float $$5 = Mth.cos($$2) * $$0 * 0.5F * $$3;
/* 154 */         level().addParticle(getParticleType(), getX() + $$4, getY(), getZ() + $$5, 0.0D, 0.0D, 0.0D);
/*     */       } 
/*     */       
/* 157 */       playSound(getSquishSound(), getSoundVolume(), ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) / 0.8F);
/* 158 */       this.targetSquish = -0.5F;
/* 159 */     } else if (!onGround() && this.wasOnGround) {
/* 160 */       this.targetSquish = 1.0F;
/*     */     } 
/* 162 */     this.wasOnGround = onGround();
/* 163 */     decreaseSquish();
/*     */   }
/*     */   
/*     */   protected void decreaseSquish() {
/* 167 */     this.targetSquish *= 0.6F;
/*     */   }
/*     */   
/*     */   protected int getJumpDelay() {
/* 171 */     return this.random.nextInt(20) + 10;
/*     */   }
/*     */ 
/*     */   
/*     */   public void refreshDimensions() {
/* 176 */     double $$0 = getX();
/* 177 */     double $$1 = getY();
/* 178 */     double $$2 = getZ();
/* 179 */     super.refreshDimensions();
/* 180 */     setPos($$0, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onSyncedDataUpdated(EntityDataAccessor<?> $$0) {
/* 185 */     if (ID_SIZE.equals($$0)) {
/* 186 */       refreshDimensions();
/* 187 */       setYRot(this.yHeadRot);
/* 188 */       this.yBodyRot = this.yHeadRot;
/*     */       
/* 190 */       if (isInWater() && 
/* 191 */         this.random.nextInt(20) == 0) {
/* 192 */         doWaterSplashEffect();
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 197 */     super.onSyncedDataUpdated($$0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityType<? extends Slime> getType() {
/* 203 */     return super.getType();
/*     */   }
/*     */ 
/*     */   
/*     */   public void remove(Entity.RemovalReason $$0) {
/* 208 */     int $$1 = getSize();
/* 209 */     if (!(level()).isClientSide && $$1 > 1 && isDeadOrDying()) {
/* 210 */       Component $$2 = getCustomName();
/* 211 */       boolean $$3 = isNoAi();
/* 212 */       float $$4 = $$1 / 4.0F;
/* 213 */       int $$5 = $$1 / 2;
/*     */       
/* 215 */       int $$6 = 2 + this.random.nextInt(3);
/* 216 */       for (int $$7 = 0; $$7 < $$6; $$7++) {
/* 217 */         float $$8 = (($$7 % 2) - 0.5F) * $$4;
/* 218 */         float $$9 = (($$7 / 2) - 0.5F) * $$4;
/* 219 */         Slime $$10 = (Slime)getType().create(level());
/* 220 */         if ($$10 != null) {
/* 221 */           if (isPersistenceRequired()) {
/* 222 */             $$10.setPersistenceRequired();
/*     */           }
/* 224 */           $$10.setCustomName($$2);
/* 225 */           $$10.setNoAi($$3);
/* 226 */           $$10.setInvulnerable(isInvulnerable());
/*     */           
/* 228 */           $$10.setSize($$5, true);
/* 229 */           $$10.moveTo(getX() + $$8, getY() + 0.5D, getZ() + $$9, this.random.nextFloat() * 360.0F, 0.0F);
/* 230 */           level().addFreshEntity((Entity)$$10);
/*     */         } 
/*     */       } 
/*     */     } 
/* 234 */     super.remove($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void push(Entity $$0) {
/* 239 */     super.push($$0);
/* 240 */     if ($$0 instanceof IronGolem && isDealsDamage()) {
/* 241 */       dealDamage((LivingEntity)$$0);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void playerTouch(Player $$0) {
/* 247 */     if (isDealsDamage()) {
/* 248 */       dealDamage((LivingEntity)$$0);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void dealDamage(LivingEntity $$0) {
/* 253 */     if (isAlive()) {
/* 254 */       int $$1 = getSize();
/* 255 */       if (distanceToSqr((Entity)$$0) < 0.6D * $$1 * 0.6D * $$1 && hasLineOfSight((Entity)$$0) && 
/* 256 */         $$0.hurt(damageSources().mobAttack((LivingEntity)this), getAttackDamage())) {
/* 257 */         playSound(SoundEvents.SLIME_ATTACK, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
/* 258 */         doEnchantDamageEffects((LivingEntity)this, (Entity)$$0);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected float getStandingEyeHeight(Pose $$0, EntityDimensions $$1) {
/* 266 */     return 0.625F * $$1.height;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Vector3f getPassengerAttachmentPoint(Entity $$0, EntityDimensions $$1, float $$2) {
/* 271 */     return new Vector3f(0.0F, $$1.height - 0.015625F * getSize() * $$2, 0.0F);
/*     */   }
/*     */   
/*     */   protected boolean isDealsDamage() {
/* 275 */     return (!isTiny() && isEffectiveAi());
/*     */   }
/*     */   
/*     */   protected float getAttackDamage() {
/* 279 */     return (float)getAttributeValue(Attributes.ATTACK_DAMAGE);
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource $$0) {
/* 284 */     if (isTiny()) {
/* 285 */       return SoundEvents.SLIME_HURT_SMALL;
/*     */     }
/* 287 */     return SoundEvents.SLIME_HURT;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/* 293 */     if (isTiny()) {
/* 294 */       return SoundEvents.SLIME_DEATH_SMALL;
/*     */     }
/* 296 */     return SoundEvents.SLIME_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getSquishSound() {
/* 301 */     if (isTiny()) {
/* 302 */       return SoundEvents.SLIME_SQUISH_SMALL;
/*     */     }
/* 304 */     return SoundEvents.SLIME_SQUISH;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean checkSlimeSpawnRules(EntityType<Slime> $$0, LevelAccessor $$1, MobSpawnType $$2, BlockPos $$3, RandomSource $$4) {
/* 309 */     if (MobSpawnType.isSpawner($$2)) {
/* 310 */       return checkMobSpawnRules($$0, $$1, $$2, $$3, $$4);
/*     */     }
/*     */     
/* 313 */     if ($$1.getDifficulty() != Difficulty.PEACEFUL) {
/* 314 */       if ($$2 == MobSpawnType.SPAWNER) {
/* 315 */         return checkMobSpawnRules($$0, $$1, $$2, $$3, $$4);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 320 */       if ($$1.getBiome($$3).is(BiomeTags.ALLOWS_SURFACE_SLIME_SPAWNS) && $$3.getY() > 50 && $$3.getY() < 70 && $$4.nextFloat() < 0.5F && 
/* 321 */         $$4.nextFloat() < $$1.getMoonBrightness() && $$1.getMaxLocalRawBrightness($$3) <= $$4.nextInt(8)) {
/* 322 */         return checkMobSpawnRules($$0, $$1, $$2, $$3, $$4);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 327 */       if (!($$1 instanceof WorldGenLevel)) {
/* 328 */         return false;
/*     */       }
/* 330 */       ChunkPos $$5 = new ChunkPos($$3);
/* 331 */       boolean $$6 = (WorldgenRandom.seedSlimeChunk($$5.x, $$5.z, ((WorldGenLevel)$$1).getSeed(), 987234911L).nextInt(10) == 0);
/* 332 */       if ($$4.nextInt(10) == 0 && $$6 && $$3.getY() < 40) {
/* 333 */         return checkMobSpawnRules($$0, $$1, $$2, $$3, $$4);
/*     */       }
/*     */     } 
/* 336 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getSoundVolume() {
/* 341 */     return 0.4F * getSize();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxHeadXRot() {
/* 346 */     return 0;
/*     */   }
/*     */   
/*     */   protected boolean doPlayJumpSound() {
/* 350 */     return (getSize() > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void jumpFromGround() {
/* 355 */     Vec3 $$0 = getDeltaMovement();
/* 356 */     setDeltaMovement($$0.x, getJumpPower(), $$0.z);
/* 357 */     this.hasImpulse = true;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public SpawnGroupData finalizeSpawn(ServerLevelAccessor $$0, DifficultyInstance $$1, MobSpawnType $$2, @Nullable SpawnGroupData $$3, @Nullable CompoundTag $$4) {
/* 363 */     RandomSource $$5 = $$0.getRandom();
/* 364 */     int $$6 = $$5.nextInt(3);
/* 365 */     if ($$6 < 2 && $$5.nextFloat() < 0.5F * $$1.getSpecialMultiplier()) {
/* 366 */       $$6++;
/*     */     }
/* 368 */     int $$7 = 1 << $$6;
/* 369 */     setSize($$7, true);
/*     */     
/* 371 */     return super.finalizeSpawn($$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */   
/*     */   private static class SlimeMoveControl extends MoveControl {
/*     */     private float yRot;
/*     */     private int jumpDelay;
/*     */     private final Slime slime;
/*     */     private boolean isAggressive;
/*     */     
/*     */     public SlimeMoveControl(Slime $$0) {
/* 381 */       super($$0);
/* 382 */       this.slime = $$0;
/* 383 */       this.yRot = 180.0F * $$0.getYRot() / 3.1415927F;
/*     */     }
/*     */     
/*     */     public void setDirection(float $$0, boolean $$1) {
/* 387 */       this.yRot = $$0;
/* 388 */       this.isAggressive = $$1;
/*     */     }
/*     */     
/*     */     public void setWantedMovement(double $$0) {
/* 392 */       this.speedModifier = $$0;
/* 393 */       this.operation = MoveControl.Operation.MOVE_TO;
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick() {
/* 398 */       this.mob.setYRot(rotlerp(this.mob.getYRot(), this.yRot, 90.0F));
/* 399 */       this.mob.yHeadRot = this.mob.getYRot();
/* 400 */       this.mob.yBodyRot = this.mob.getYRot();
/*     */       
/* 402 */       if (this.operation != MoveControl.Operation.MOVE_TO) {
/* 403 */         this.mob.setZza(0.0F);
/*     */         return;
/*     */       } 
/* 406 */       this.operation = MoveControl.Operation.WAIT;
/*     */       
/* 408 */       if (this.mob.onGround()) {
/* 409 */         this.mob.setSpeed((float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
/* 410 */         if (this.jumpDelay-- <= 0) {
/* 411 */           this.jumpDelay = this.slime.getJumpDelay();
/* 412 */           if (this.isAggressive) {
/* 413 */             this.jumpDelay /= 3;
/*     */           }
/* 415 */           this.slime.getJumpControl().jump();
/* 416 */           if (this.slime.doPlayJumpSound()) {
/* 417 */             this.slime.playSound(this.slime.getJumpSound(), this.slime.getSoundVolume(), this.slime.getSoundPitch());
/*     */           }
/*     */         } else {
/* 420 */           this.slime.xxa = 0.0F;
/* 421 */           this.slime.zza = 0.0F;
/* 422 */           this.mob.setSpeed(0.0F);
/*     */         } 
/*     */       } else {
/* 425 */         this.mob.setSpeed((float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   float getSoundPitch() {
/* 431 */     float $$0 = isTiny() ? 1.4F : 0.8F;
/* 432 */     return ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) * $$0;
/*     */   }
/*     */   
/*     */   protected SoundEvent getJumpSound() {
/* 436 */     return isTiny() ? SoundEvents.SLIME_JUMP_SMALL : SoundEvents.SLIME_JUMP;
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityDimensions getDimensions(Pose $$0) {
/* 441 */     return super.getDimensions($$0).scale(0.255F * getSize());
/*     */   }
/*     */   
/*     */   private static class SlimeAttackGoal extends Goal {
/*     */     private final Slime slime;
/*     */     private int growTiredTimer;
/*     */     
/*     */     public SlimeAttackGoal(Slime $$0) {
/* 449 */       this.slime = $$0;
/* 450 */       setFlags(EnumSet.of(Goal.Flag.LOOK));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canUse() {
/* 455 */       LivingEntity $$0 = this.slime.getTarget();
/*     */       
/* 457 */       if ($$0 == null) {
/* 458 */         return false;
/*     */       }
/*     */       
/* 461 */       if (!this.slime.canAttack($$0)) {
/* 462 */         return false;
/*     */       }
/*     */       
/* 465 */       return this.slime.getMoveControl() instanceof Slime.SlimeMoveControl;
/*     */     }
/*     */ 
/*     */     
/*     */     public void start() {
/* 470 */       this.growTiredTimer = reducedTickDelay(300);
/* 471 */       super.start();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canContinueToUse() {
/* 476 */       LivingEntity $$0 = this.slime.getTarget();
/*     */       
/* 478 */       if ($$0 == null) {
/* 479 */         return false;
/*     */       }
/*     */       
/* 482 */       if (!this.slime.canAttack($$0)) {
/* 483 */         return false;
/*     */       }
/*     */       
/* 486 */       if (--this.growTiredTimer <= 0) {
/* 487 */         return false;
/*     */       }
/*     */       
/* 490 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean requiresUpdateEveryTick() {
/* 495 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick() {
/* 500 */       LivingEntity $$0 = this.slime.getTarget();
/* 501 */       if ($$0 != null) {
/* 502 */         this.slime.lookAt((Entity)$$0, 10.0F, 10.0F);
/*     */       }
/* 504 */       MoveControl moveControl = this.slime.getMoveControl(); if (moveControl instanceof Slime.SlimeMoveControl) { Slime.SlimeMoveControl $$1 = (Slime.SlimeMoveControl)moveControl;
/* 505 */         $$1.setDirection(this.slime.getYRot(), this.slime.isDealsDamage()); }
/*     */     
/*     */     }
/*     */   }
/*     */   
/*     */   private static class SlimeRandomDirectionGoal
/*     */     extends Goal {
/*     */     private final Slime slime;
/*     */     private float chosenDegrees;
/*     */     private int nextRandomizeTime;
/*     */     
/*     */     public SlimeRandomDirectionGoal(Slime $$0) {
/* 517 */       this.slime = $$0;
/* 518 */       setFlags(EnumSet.of(Goal.Flag.LOOK));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canUse() {
/* 523 */       return (this.slime.getTarget() == null && (this.slime.onGround() || this.slime.isInWater() || this.slime.isInLava() || this.slime.hasEffect(MobEffects.LEVITATION)) && this.slime.getMoveControl() instanceof Slime.SlimeMoveControl);
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick() {
/* 528 */       if (--this.nextRandomizeTime <= 0) {
/* 529 */         this.nextRandomizeTime = adjustedTickDelay(40 + this.slime.getRandom().nextInt(60));
/* 530 */         this.chosenDegrees = this.slime.getRandom().nextInt(360);
/*     */       } 
/* 532 */       MoveControl moveControl = this.slime.getMoveControl(); if (moveControl instanceof Slime.SlimeMoveControl) { Slime.SlimeMoveControl $$0 = (Slime.SlimeMoveControl)moveControl;
/* 533 */         $$0.setDirection(this.chosenDegrees, false); }
/*     */     
/*     */     }
/*     */   }
/*     */   
/*     */   private static class SlimeFloatGoal extends Goal {
/*     */     private final Slime slime;
/*     */     
/*     */     public SlimeFloatGoal(Slime $$0) {
/* 542 */       this.slime = $$0;
/* 543 */       setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
/* 544 */       $$0.getNavigation().setCanFloat(true);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canUse() {
/* 549 */       return ((this.slime.isInWater() || this.slime.isInLava()) && this.slime.getMoveControl() instanceof Slime.SlimeMoveControl);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean requiresUpdateEveryTick() {
/* 554 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick() {
/* 559 */       if (this.slime.getRandom().nextFloat() < 0.8F) {
/* 560 */         this.slime.getJumpControl().jump();
/*     */       }
/* 562 */       MoveControl moveControl = this.slime.getMoveControl(); if (moveControl instanceof Slime.SlimeMoveControl) { Slime.SlimeMoveControl $$0 = (Slime.SlimeMoveControl)moveControl;
/* 563 */         $$0.setWantedMovement(1.2D); }
/*     */     
/*     */     }
/*     */   }
/*     */   
/*     */   private static class SlimeKeepOnJumpingGoal extends Goal {
/*     */     private final Slime slime;
/*     */     
/*     */     public SlimeKeepOnJumpingGoal(Slime $$0) {
/* 572 */       this.slime = $$0;
/* 573 */       setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canUse() {
/* 578 */       return !this.slime.isPassenger();
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick() {
/* 583 */       MoveControl moveControl = this.slime.getMoveControl(); if (moveControl instanceof Slime.SlimeMoveControl) { Slime.SlimeMoveControl $$0 = (Slime.SlimeMoveControl)moveControl;
/* 584 */         $$0.setWantedMovement(1.0D); }
/*     */     
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\Slime.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */