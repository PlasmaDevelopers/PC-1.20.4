/*     */ package net.minecraft.world.entity.monster;
/*     */ 
/*     */ import java.util.Comparator;
/*     */ import java.util.EnumSet;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.syncher.EntityDataAccessor;
/*     */ import net.minecraft.network.syncher.EntityDataSerializers;
/*     */ import net.minecraft.network.syncher.SynchedEntityData;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityDimensions;
/*     */ import net.minecraft.world.entity.EntitySelector;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.FlyingMob;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.MobSpawnType;
/*     */ import net.minecraft.world.entity.MobType;
/*     */ import net.minecraft.world.entity.Pose;
/*     */ import net.minecraft.world.entity.SpawnGroupData;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.entity.ai.control.BodyRotationControl;
/*     */ import net.minecraft.world.entity.ai.control.LookControl;
/*     */ import net.minecraft.world.entity.ai.control.MoveControl;
/*     */ import net.minecraft.world.entity.ai.goal.Goal;
/*     */ import net.minecraft.world.entity.ai.targeting.TargetingConditions;
/*     */ import net.minecraft.world.entity.animal.Cat;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.ServerLevelAccessor;
/*     */ import net.minecraft.world.level.levelgen.Heightmap;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import org.joml.Vector3f;
/*     */ 
/*     */ public class Phantom extends FlyingMob implements Enemy {
/*     */   public static final float FLAP_DEGREES_PER_TICK = 7.448451F;
/*  49 */   public static final int TICKS_PER_FLAP = Mth.ceil(24.166098F);
/*     */   
/*  51 */   private static final EntityDataAccessor<Integer> ID_SIZE = SynchedEntityData.defineId(Phantom.class, EntityDataSerializers.INT);
/*     */   
/*  53 */   Vec3 moveTargetPoint = Vec3.ZERO;
/*  54 */   BlockPos anchorPoint = BlockPos.ZERO;
/*     */   
/*     */   private enum AttackPhase {
/*  57 */     CIRCLE,
/*  58 */     SWOOP;
/*     */   }
/*     */   
/*  61 */   AttackPhase attackPhase = AttackPhase.CIRCLE;
/*     */   
/*     */   public Phantom(EntityType<? extends Phantom> $$0, Level $$1) {
/*  64 */     super($$0, $$1);
/*  65 */     this.xpReward = 5;
/*     */     
/*  67 */     this.moveControl = new PhantomMoveControl((Mob)this);
/*  68 */     this.lookControl = new PhantomLookControl((Mob)this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFlapping() {
/*  75 */     return ((getUniqueFlapTickOffset() + this.tickCount) % TICKS_PER_FLAP == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected BodyRotationControl createBodyControl() {
/*  80 */     return new PhantomBodyRotationControl((Mob)this);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerGoals() {
/*  85 */     this.goalSelector.addGoal(1, new PhantomAttackStrategyGoal());
/*  86 */     this.goalSelector.addGoal(2, new PhantomSweepAttackGoal());
/*  87 */     this.goalSelector.addGoal(3, new PhantomCircleAroundAnchorGoal());
/*     */     
/*  89 */     this.targetSelector.addGoal(1, new PhantomAttackPlayerTargetGoal());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {
/*  94 */     super.defineSynchedData();
/*     */     
/*  96 */     this.entityData.define(ID_SIZE, Integer.valueOf(0));
/*     */   }
/*     */   
/*     */   public void setPhantomSize(int $$0) {
/* 100 */     this.entityData.set(ID_SIZE, Integer.valueOf(Mth.clamp($$0, 0, 64)));
/*     */   }
/*     */   
/*     */   private void updatePhantomSizeInfo() {
/* 104 */     refreshDimensions();
/* 105 */     getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue((6 + getPhantomSize()));
/*     */   }
/*     */   
/*     */   public int getPhantomSize() {
/* 109 */     return ((Integer)this.entityData.get(ID_SIZE)).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getStandingEyeHeight(Pose $$0, EntityDimensions $$1) {
/* 114 */     return $$1.height * 0.35F;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onSyncedDataUpdated(EntityDataAccessor<?> $$0) {
/* 119 */     if (ID_SIZE.equals($$0)) {
/* 120 */       updatePhantomSizeInfo();
/*     */     }
/*     */     
/* 123 */     super.onSyncedDataUpdated($$0);
/*     */   }
/*     */   
/*     */   public int getUniqueFlapTickOffset() {
/* 127 */     return getId() * 3;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean shouldDespawnInPeaceful() {
/* 132 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 137 */     super.tick();
/*     */     
/* 139 */     if ((level()).isClientSide) {
/* 140 */       float $$0 = Mth.cos((getUniqueFlapTickOffset() + this.tickCount) * 7.448451F * 0.017453292F + 3.1415927F);
/* 141 */       float $$1 = Mth.cos((getUniqueFlapTickOffset() + this.tickCount + 1) * 7.448451F * 0.017453292F + 3.1415927F);
/* 142 */       if ($$0 > 0.0F && $$1 <= 0.0F) {
/* 143 */         level().playLocalSound(getX(), getY(), getZ(), SoundEvents.PHANTOM_FLAP, getSoundSource(), 0.95F + this.random.nextFloat() * 0.05F, 0.95F + this.random.nextFloat() * 0.05F, false);
/*     */       }
/*     */       
/* 146 */       int $$2 = getPhantomSize();
/* 147 */       float $$3 = Mth.cos(getYRot() * 0.017453292F) * (1.3F + 0.21F * $$2);
/* 148 */       float $$4 = Mth.sin(getYRot() * 0.017453292F) * (1.3F + 0.21F * $$2);
/* 149 */       float $$5 = (0.3F + $$0 * 0.45F) * ($$2 * 0.2F + 1.0F);
/* 150 */       level().addParticle((ParticleOptions)ParticleTypes.MYCELIUM, getX() + $$3, getY() + $$5, getZ() + $$4, 0.0D, 0.0D, 0.0D);
/* 151 */       level().addParticle((ParticleOptions)ParticleTypes.MYCELIUM, getX() - $$3, getY() + $$5, getZ() - $$4, 0.0D, 0.0D, 0.0D);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void aiStep() {
/* 157 */     if (isAlive() && isSunBurnTick()) {
/* 158 */       setSecondsOnFire(8);
/*     */     }
/* 160 */     super.aiStep();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void customServerAiStep() {
/* 165 */     super.customServerAiStep();
/*     */   }
/*     */ 
/*     */   
/*     */   public SpawnGroupData finalizeSpawn(ServerLevelAccessor $$0, DifficultyInstance $$1, MobSpawnType $$2, @Nullable SpawnGroupData $$3, @Nullable CompoundTag $$4) {
/* 170 */     this.anchorPoint = blockPosition().above(5);
/* 171 */     setPhantomSize(0);
/* 172 */     return super.finalizeSpawn($$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/* 177 */     super.readAdditionalSaveData($$0);
/*     */     
/* 179 */     if ($$0.contains("AX")) {
/* 180 */       this.anchorPoint = new BlockPos($$0.getInt("AX"), $$0.getInt("AY"), $$0.getInt("AZ"));
/*     */     }
/* 182 */     setPhantomSize($$0.getInt("Size"));
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/* 187 */     super.addAdditionalSaveData($$0);
/*     */     
/* 189 */     $$0.putInt("AX", this.anchorPoint.getX());
/* 190 */     $$0.putInt("AY", this.anchorPoint.getY());
/* 191 */     $$0.putInt("AZ", this.anchorPoint.getZ());
/* 192 */     $$0.putInt("Size", getPhantomSize());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldRenderAtSqrDistance(double $$0) {
/* 197 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public SoundSource getSoundSource() {
/* 202 */     return SoundSource.HOSTILE;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/* 207 */     return SoundEvents.PHANTOM_AMBIENT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource $$0) {
/* 212 */     return SoundEvents.PHANTOM_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/* 217 */     return SoundEvents.PHANTOM_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   public MobType getMobType() {
/* 222 */     return MobType.UNDEAD;
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getSoundVolume() {
/* 227 */     return 1.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canAttackType(EntityType<?> $$0) {
/* 232 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityDimensions getDimensions(Pose $$0) {
/* 237 */     int $$1 = getPhantomSize();
/*     */     
/* 239 */     EntityDimensions $$2 = super.getDimensions($$0);
/* 240 */     return $$2.scale(1.0F + 0.15F * $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Vector3f getPassengerAttachmentPoint(Entity $$0, EntityDimensions $$1, float $$2) {
/* 245 */     return new Vector3f(0.0F, $$1.height * 0.675F, 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   protected float ridingOffset(Entity $$0) {
/* 250 */     return -0.125F;
/*     */   }
/*     */   
/*     */   private class PhantomMoveControl extends MoveControl {
/* 254 */     private float speed = 0.1F;
/*     */     
/*     */     public PhantomMoveControl(Mob $$0) {
/* 257 */       super($$0);
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick() {
/* 262 */       if (Phantom.this.horizontalCollision) {
/*     */         
/* 264 */         Phantom.this.setYRot(Phantom.this.getYRot() + 180.0F);
/* 265 */         this.speed = 0.1F;
/*     */       } 
/*     */ 
/*     */       
/* 269 */       double $$0 = Phantom.this.moveTargetPoint.x - Phantom.this.getX();
/* 270 */       double $$1 = Phantom.this.moveTargetPoint.y - Phantom.this.getY();
/* 271 */       double $$2 = Phantom.this.moveTargetPoint.z - Phantom.this.getZ();
/* 272 */       double $$3 = Math.sqrt($$0 * $$0 + $$2 * $$2);
/*     */ 
/*     */       
/* 275 */       if (Math.abs($$3) > 9.999999747378752E-6D) {
/* 276 */         double $$4 = 1.0D - Math.abs($$1 * 0.699999988079071D) / $$3;
/* 277 */         $$0 *= $$4;
/* 278 */         $$2 *= $$4;
/* 279 */         $$3 = Math.sqrt($$0 * $$0 + $$2 * $$2);
/* 280 */         double $$5 = Math.sqrt($$0 * $$0 + $$2 * $$2 + $$1 * $$1);
/*     */ 
/*     */         
/* 283 */         float $$6 = Phantom.this.getYRot();
/* 284 */         float $$7 = (float)Mth.atan2($$2, $$0);
/* 285 */         float $$8 = Mth.wrapDegrees(Phantom.this.getYRot() + 90.0F);
/* 286 */         float $$9 = Mth.wrapDegrees($$7 * 57.295776F);
/* 287 */         Phantom.this.setYRot(Mth.approachDegrees($$8, $$9, 4.0F) - 90.0F);
/* 288 */         Phantom.this.yBodyRot = Phantom.this.getYRot();
/*     */         
/* 290 */         if (Mth.degreesDifferenceAbs($$6, Phantom.this.getYRot()) < 3.0F) {
/* 291 */           this.speed = Mth.approach(this.speed, 1.8F, 0.005F * 1.8F / this.speed);
/*     */         } else {
/* 293 */           this.speed = Mth.approach(this.speed, 0.2F, 0.025F);
/*     */         } 
/*     */         
/* 296 */         float $$10 = (float)-(Mth.atan2(-$$1, $$3) * 57.2957763671875D);
/* 297 */         Phantom.this.setXRot($$10);
/*     */         
/* 299 */         float $$11 = Phantom.this.getYRot() + 90.0F;
/* 300 */         double $$12 = (this.speed * Mth.cos($$11 * 0.017453292F)) * Math.abs($$0 / $$5);
/* 301 */         double $$13 = (this.speed * Mth.sin($$11 * 0.017453292F)) * Math.abs($$2 / $$5);
/* 302 */         double $$14 = (this.speed * Mth.sin($$10 * 0.017453292F)) * Math.abs($$1 / $$5);
/*     */         
/* 304 */         Vec3 $$15 = Phantom.this.getDeltaMovement();
/* 305 */         Phantom.this.setDeltaMovement($$15.add((new Vec3($$12, $$14, $$13)).subtract($$15).scale(0.2D)));
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private class PhantomBodyRotationControl extends BodyRotationControl {
/*     */     public PhantomBodyRotationControl(Mob $$0) {
/* 312 */       super($$0);
/*     */     }
/*     */ 
/*     */     
/*     */     public void clientTick() {
/* 317 */       Phantom.this.yHeadRot = Phantom.this.yBodyRot;
/* 318 */       Phantom.this.yBodyRot = Phantom.this.getYRot();
/*     */     }
/*     */   }
/*     */   
/*     */   private class PhantomLookControl extends LookControl {
/*     */     public PhantomLookControl(Mob $$0) {
/* 324 */       super($$0);
/*     */     }
/*     */     
/*     */     public void tick() {}
/*     */   }
/*     */   
/*     */   private abstract class PhantomMoveTargetGoal
/*     */     extends Goal
/*     */   {
/*     */     public PhantomMoveTargetGoal() {
/* 334 */       setFlags(EnumSet.of(Goal.Flag.MOVE));
/*     */     }
/*     */     
/*     */     protected boolean touchingTarget() {
/* 338 */       return (Phantom.this.moveTargetPoint.distanceToSqr(Phantom.this.getX(), Phantom.this.getY(), Phantom.this.getZ()) < 4.0D);
/*     */     }
/*     */   }
/*     */   
/*     */   private class PhantomCircleAroundAnchorGoal
/*     */     extends PhantomMoveTargetGoal {
/*     */     private float angle;
/*     */     private float distance;
/*     */     private float height;
/*     */     private float clockwise;
/*     */     
/*     */     public boolean canUse() {
/* 350 */       return (Phantom.this.getTarget() == null || Phantom.this.attackPhase == Phantom.AttackPhase.CIRCLE);
/*     */     }
/*     */ 
/*     */     
/*     */     public void start() {
/* 355 */       this.distance = 5.0F + Phantom.this.random.nextFloat() * 10.0F;
/* 356 */       this.height = -4.0F + Phantom.this.random.nextFloat() * 9.0F;
/* 357 */       this.clockwise = Phantom.this.random.nextBoolean() ? 1.0F : -1.0F;
/* 358 */       selectNext();
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick() {
/* 363 */       if (Phantom.this.random.nextInt(adjustedTickDelay(350)) == 0) {
/* 364 */         this.height = -4.0F + Phantom.this.random.nextFloat() * 9.0F;
/*     */       }
/* 366 */       if (Phantom.this.random.nextInt(adjustedTickDelay(250)) == 0) {
/* 367 */         this.distance++;
/* 368 */         if (this.distance > 15.0F) {
/* 369 */           this.distance = 5.0F;
/* 370 */           this.clockwise = -this.clockwise;
/*     */         } 
/*     */       } 
/* 373 */       if (Phantom.this.random.nextInt(adjustedTickDelay(450)) == 0) {
/* 374 */         this.angle = Phantom.this.random.nextFloat() * 2.0F * 3.1415927F;
/* 375 */         selectNext();
/*     */       } 
/* 377 */       if (touchingTarget()) {
/* 378 */         selectNext();
/*     */       }
/*     */       
/* 381 */       if (Phantom.this.moveTargetPoint.y < Phantom.this.getY() && !Phantom.this.level().isEmptyBlock(Phantom.this.blockPosition().below(1))) {
/* 382 */         this.height = Math.max(1.0F, this.height);
/* 383 */         selectNext();
/*     */       } 
/*     */       
/* 386 */       if (Phantom.this.moveTargetPoint.y > Phantom.this.getY() && !Phantom.this.level().isEmptyBlock(Phantom.this.blockPosition().above(1))) {
/* 387 */         this.height = Math.min(-1.0F, this.height);
/* 388 */         selectNext();
/*     */       } 
/*     */     }
/*     */     
/*     */     private void selectNext() {
/* 393 */       if (BlockPos.ZERO.equals(Phantom.this.anchorPoint)) {
/* 394 */         Phantom.this.anchorPoint = Phantom.this.blockPosition();
/*     */       }
/* 396 */       this.angle += this.clockwise * 15.0F * 0.017453292F;
/* 397 */       Phantom.this.moveTargetPoint = Vec3.atLowerCornerOf((Vec3i)Phantom.this.anchorPoint).add((this.distance * Mth.cos(this.angle)), (-4.0F + this.height), (this.distance * Mth.sin(this.angle)));
/*     */     }
/*     */   }
/*     */   
/*     */   private class PhantomSweepAttackGoal
/*     */     extends PhantomMoveTargetGoal
/*     */   {
/*     */     private static final int CAT_SEARCH_TICK_DELAY = 20;
/*     */     private boolean isScaredOfCat;
/*     */     private int catSearchTick;
/*     */     
/*     */     public boolean canUse() {
/* 409 */       return (Phantom.this.getTarget() != null && Phantom.this.attackPhase == Phantom.AttackPhase.SWOOP);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canContinueToUse() {
/* 414 */       LivingEntity $$0 = Phantom.this.getTarget();
/* 415 */       if ($$0 == null) {
/* 416 */         return false;
/*     */       }
/* 418 */       if (!$$0.isAlive()) {
/* 419 */         return false;
/*     */       }
/* 421 */       if ($$0 instanceof Player) { Player $$1 = (Player)$$0; if ($$0.isSpectator() || $$1.isCreative()) {
/* 422 */           return false;
/*     */         } }
/*     */       
/* 425 */       if (!canUse()) {
/* 426 */         return false;
/*     */       }
/*     */       
/* 429 */       if (Phantom.this.tickCount > this.catSearchTick) {
/* 430 */         this.catSearchTick = Phantom.this.tickCount + 20;
/* 431 */         List<Cat> $$2 = Phantom.this.level().getEntitiesOfClass(Cat.class, Phantom.this.getBoundingBox().inflate(16.0D), EntitySelector.ENTITY_STILL_ALIVE);
/* 432 */         for (Cat $$3 : $$2) {
/* 433 */           $$3.hiss();
/*     */         }
/* 435 */         this.isScaredOfCat = !$$2.isEmpty();
/*     */       } 
/*     */       
/* 438 */       return !this.isScaredOfCat;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void start() {}
/*     */ 
/*     */     
/*     */     public void stop() {
/* 447 */       Phantom.this.setTarget(null);
/* 448 */       Phantom.this.attackPhase = Phantom.AttackPhase.CIRCLE;
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick() {
/* 453 */       LivingEntity $$0 = Phantom.this.getTarget();
/* 454 */       if ($$0 == null) {
/*     */         return;
/*     */       }
/* 457 */       Phantom.this.moveTargetPoint = new Vec3($$0.getX(), $$0.getY(0.5D), $$0.getZ());
/*     */       
/* 459 */       if (Phantom.this.getBoundingBox().inflate(0.20000000298023224D).intersects($$0.getBoundingBox())) {
/* 460 */         Phantom.this.doHurtTarget((Entity)$$0);
/* 461 */         Phantom.this.attackPhase = Phantom.AttackPhase.CIRCLE;
/* 462 */         if (!Phantom.this.isSilent()) {
/* 463 */           Phantom.this.level().levelEvent(1039, Phantom.this.blockPosition(), 0);
/*     */         }
/* 465 */       } else if (Phantom.this.horizontalCollision || Phantom.this.hurtTime > 0) {
/* 466 */         Phantom.this.attackPhase = Phantom.AttackPhase.CIRCLE;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private class PhantomAttackStrategyGoal
/*     */     extends Goal {
/*     */     private int nextSweepTick;
/*     */     
/*     */     public boolean canUse() {
/* 476 */       LivingEntity $$0 = Phantom.this.getTarget();
/* 477 */       if ($$0 != null) {
/* 478 */         return Phantom.this.canAttack($$0, TargetingConditions.DEFAULT);
/*     */       }
/* 480 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public void start() {
/* 485 */       this.nextSweepTick = adjustedTickDelay(10);
/* 486 */       Phantom.this.attackPhase = Phantom.AttackPhase.CIRCLE;
/* 487 */       setAnchorAboveTarget();
/*     */     }
/*     */ 
/*     */     
/*     */     public void stop() {
/* 492 */       Phantom.this.anchorPoint = Phantom.this.level().getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, Phantom.this.anchorPoint).above(10 + Phantom.this.random.nextInt(20));
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick() {
/* 497 */       if (Phantom.this.attackPhase == Phantom.AttackPhase.CIRCLE) {
/* 498 */         this.nextSweepTick--;
/* 499 */         if (this.nextSweepTick <= 0) {
/* 500 */           Phantom.this.attackPhase = Phantom.AttackPhase.SWOOP;
/* 501 */           setAnchorAboveTarget();
/* 502 */           this.nextSweepTick = adjustedTickDelay((8 + Phantom.this.random.nextInt(4)) * 20);
/*     */           
/* 504 */           Phantom.this.playSound(SoundEvents.PHANTOM_SWOOP, 10.0F, 0.95F + Phantom.this.random.nextFloat() * 0.1F);
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     private void setAnchorAboveTarget() {
/* 510 */       Phantom.this.anchorPoint = Phantom.this.getTarget().blockPosition().above(20 + Phantom.this.random.nextInt(20));
/* 511 */       if (Phantom.this.anchorPoint.getY() < Phantom.this.level().getSeaLevel())
/* 512 */         Phantom.this.anchorPoint = new BlockPos(Phantom.this.anchorPoint.getX(), Phantom.this.level().getSeaLevel() + 1, Phantom.this.anchorPoint.getZ()); 
/*     */     } }
/*     */   
/*     */   private class PhantomAttackPlayerTargetGoal extends Goal {
/*     */     private final TargetingConditions attackTargeting;
/*     */     
/*     */     PhantomAttackPlayerTargetGoal() {
/* 519 */       this.attackTargeting = TargetingConditions.forCombat().range(64.0D);
/*     */       
/* 521 */       this.nextScanTick = reducedTickDelay(20);
/*     */     }
/*     */     private int nextScanTick;
/*     */     public boolean canUse() {
/* 525 */       if (this.nextScanTick > 0) {
/* 526 */         this.nextScanTick--;
/* 527 */         return false;
/*     */       } 
/* 529 */       this.nextScanTick = reducedTickDelay(60);
/*     */       
/* 531 */       List<Player> $$0 = Phantom.this.level().getNearbyPlayers(this.attackTargeting, (LivingEntity)Phantom.this, Phantom.this.getBoundingBox().inflate(16.0D, 64.0D, 16.0D));
/* 532 */       if (!$$0.isEmpty()) {
/*     */         
/* 534 */         $$0.sort(Comparator.<Player, Comparable>comparing(Entity::getY).reversed());
/* 535 */         for (Player $$1 : $$0) {
/* 536 */           if (Phantom.this.canAttack((LivingEntity)$$1, TargetingConditions.DEFAULT)) {
/* 537 */             Phantom.this.setTarget((LivingEntity)$$1);
/* 538 */             return true;
/*     */           } 
/*     */         } 
/*     */       } 
/* 542 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canContinueToUse() {
/* 547 */       LivingEntity $$0 = Phantom.this.getTarget();
/* 548 */       if ($$0 != null) {
/* 549 */         return Phantom.this.canAttack($$0, TargetingConditions.DEFAULT);
/*     */       }
/*     */       
/* 552 */       return false;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\Phantom.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */