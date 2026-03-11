/*     */ package net.minecraft.world.entity.monster;
/*     */ 
/*     */ import java.util.EnumSet;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.syncher.EntityDataAccessor;
/*     */ import net.minecraft.network.syncher.EntityDataSerializers;
/*     */ import net.minecraft.network.syncher.SynchedEntityData;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.Difficulty;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityDimensions;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.FlyingMob;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.MobSpawnType;
/*     */ import net.minecraft.world.entity.Pose;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.entity.ai.control.MoveControl;
/*     */ import net.minecraft.world.entity.ai.goal.Goal;
/*     */ import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.entity.projectile.LargeFireball;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import org.joml.Vector3f;
/*     */ 
/*     */ public class Ghast
/*     */   extends FlyingMob
/*     */   implements Enemy
/*     */ {
/*  41 */   private static final EntityDataAccessor<Boolean> DATA_IS_CHARGING = SynchedEntityData.defineId(Ghast.class, EntityDataSerializers.BOOLEAN);
/*     */   
/*  43 */   private int explosionPower = 1;
/*     */   
/*     */   public Ghast(EntityType<? extends Ghast> $$0, Level $$1) {
/*  46 */     super($$0, $$1);
/*     */     
/*  48 */     this.xpReward = 5;
/*     */     
/*  50 */     this.moveControl = new GhastMoveControl(this);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerGoals() {
/*  55 */     this.goalSelector.addGoal(5, new RandomFloatAroundGoal(this));
/*     */     
/*  57 */     this.goalSelector.addGoal(7, new GhastLookGoal(this));
/*  58 */     this.goalSelector.addGoal(7, new GhastShootFireballGoal(this));
/*     */ 
/*     */     
/*  61 */     this.targetSelector.addGoal(1, (Goal)new NearestAttackableTargetGoal((Mob)this, Player.class, 10, true, false, $$0 -> (Math.abs($$0.getY() - getY()) <= 4.0D)));
/*     */   }
/*     */   
/*     */   public boolean isCharging() {
/*  65 */     return ((Boolean)this.entityData.get(DATA_IS_CHARGING)).booleanValue();
/*     */   }
/*     */   
/*     */   public void setCharging(boolean $$0) {
/*  69 */     this.entityData.set(DATA_IS_CHARGING, Boolean.valueOf($$0));
/*     */   }
/*     */   
/*     */   public int getExplosionPower() {
/*  73 */     return this.explosionPower;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean shouldDespawnInPeaceful() {
/*  78 */     return true;
/*     */   }
/*     */   
/*     */   private static boolean isReflectedFireball(DamageSource $$0) {
/*  82 */     return ($$0.getDirectEntity() instanceof LargeFireball && $$0.getEntity() instanceof Player);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInvulnerableTo(DamageSource $$0) {
/*  87 */     return (!isReflectedFireball($$0) && super.isInvulnerableTo($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hurt(DamageSource $$0, float $$1) {
/*  92 */     if (isReflectedFireball($$0)) {
/*     */       
/*  94 */       super.hurt($$0, 1000.0F);
/*  95 */       return true;
/*     */     } 
/*     */     
/*  98 */     if (isInvulnerableTo($$0)) {
/*  99 */       return false;
/*     */     }
/*     */     
/* 102 */     return super.hurt($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {
/* 107 */     super.defineSynchedData();
/*     */     
/* 109 */     this.entityData.define(DATA_IS_CHARGING, Boolean.valueOf(false));
/*     */   }
/*     */   
/*     */   public static AttributeSupplier.Builder createAttributes() {
/* 113 */     return Mob.createMobAttributes()
/* 114 */       .add(Attributes.MAX_HEALTH, 10.0D)
/* 115 */       .add(Attributes.FOLLOW_RANGE, 100.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public SoundSource getSoundSource() {
/* 120 */     return SoundSource.HOSTILE;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/* 125 */     return SoundEvents.GHAST_AMBIENT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource $$0) {
/* 130 */     return SoundEvents.GHAST_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/* 135 */     return SoundEvents.GHAST_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getSoundVolume() {
/* 140 */     return 5.0F;
/*     */   }
/*     */   
/*     */   public static boolean checkGhastSpawnRules(EntityType<Ghast> $$0, LevelAccessor $$1, MobSpawnType $$2, BlockPos $$3, RandomSource $$4) {
/* 144 */     return ($$1.getDifficulty() != Difficulty.PEACEFUL && $$4
/* 145 */       .nextInt(20) == 0 && 
/* 146 */       checkMobSpawnRules($$0, $$1, $$2, $$3, $$4));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxSpawnClusterSize() {
/* 151 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Vector3f getPassengerAttachmentPoint(Entity $$0, EntityDimensions $$1, float $$2) {
/* 156 */     return new Vector3f(0.0F, $$1.height + 0.0625F * $$2, 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   protected float ridingOffset(Entity $$0) {
/* 161 */     return 0.5F;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/* 166 */     super.addAdditionalSaveData($$0);
/* 167 */     $$0.putByte("ExplosionPower", (byte)this.explosionPower);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/* 172 */     super.readAdditionalSaveData($$0);
/* 173 */     if ($$0.contains("ExplosionPower", 99))
/* 174 */       this.explosionPower = $$0.getByte("ExplosionPower"); 
/*     */   }
/*     */   
/*     */   private static class GhastMoveControl
/*     */     extends MoveControl {
/*     */     private final Ghast ghast;
/*     */     private int floatDuration;
/*     */     
/*     */     public GhastMoveControl(Ghast $$0) {
/* 183 */       super((Mob)$$0);
/* 184 */       this.ghast = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick() {
/* 189 */       if (this.operation != MoveControl.Operation.MOVE_TO) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 194 */       if (this.floatDuration-- <= 0) {
/* 195 */         this.floatDuration += this.ghast.getRandom().nextInt(5) + 2;
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 200 */         Vec3 $$0 = new Vec3(this.wantedX - this.ghast.getX(), this.wantedY - this.ghast.getY(), this.wantedZ - this.ghast.getZ());
/*     */         
/* 202 */         double $$1 = $$0.length();
/* 203 */         $$0 = $$0.normalize();
/*     */         
/* 205 */         if (canReach($$0, Mth.ceil($$1))) {
/* 206 */           this.ghast.setDeltaMovement(this.ghast.getDeltaMovement().add($$0.scale(0.1D)));
/*     */         } else {
/* 208 */           this.operation = MoveControl.Operation.WAIT;
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     private boolean canReach(Vec3 $$0, int $$1) {
/* 214 */       AABB $$2 = this.ghast.getBoundingBox();
/* 215 */       for (int $$3 = 1; $$3 < $$1; $$3++) {
/* 216 */         $$2 = $$2.move($$0);
/* 217 */         if (!this.ghast.level().noCollision((Entity)this.ghast, $$2)) {
/* 218 */           return false;
/*     */         }
/*     */       } 
/*     */       
/* 222 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class RandomFloatAroundGoal extends Goal {
/*     */     private final Ghast ghast;
/*     */     
/*     */     public RandomFloatAroundGoal(Ghast $$0) {
/* 230 */       this.ghast = $$0;
/*     */       
/* 232 */       setFlags(EnumSet.of(Goal.Flag.MOVE));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canUse() {
/* 237 */       MoveControl $$0 = this.ghast.getMoveControl();
/* 238 */       if (!$$0.hasWanted()) {
/* 239 */         return true;
/*     */       }
/*     */       
/* 242 */       double $$1 = $$0.getWantedX() - this.ghast.getX();
/* 243 */       double $$2 = $$0.getWantedY() - this.ghast.getY();
/* 244 */       double $$3 = $$0.getWantedZ() - this.ghast.getZ();
/*     */       
/* 246 */       double $$4 = $$1 * $$1 + $$2 * $$2 + $$3 * $$3;
/*     */       
/* 248 */       if ($$4 < 1.0D || $$4 > 3600.0D) {
/* 249 */         return true;
/*     */       }
/*     */       
/* 252 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canContinueToUse() {
/* 257 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public void start() {
/* 262 */       RandomSource $$0 = this.ghast.getRandom();
/* 263 */       double $$1 = this.ghast.getX() + (($$0.nextFloat() * 2.0F - 1.0F) * 16.0F);
/* 264 */       double $$2 = this.ghast.getY() + (($$0.nextFloat() * 2.0F - 1.0F) * 16.0F);
/* 265 */       double $$3 = this.ghast.getZ() + (($$0.nextFloat() * 2.0F - 1.0F) * 16.0F);
/* 266 */       this.ghast.getMoveControl().setWantedPosition($$1, $$2, $$3, 1.0D);
/*     */     }
/*     */   }
/*     */   
/*     */   private static class GhastLookGoal extends Goal {
/*     */     private final Ghast ghast;
/*     */     
/*     */     public GhastLookGoal(Ghast $$0) {
/* 274 */       this.ghast = $$0;
/*     */       
/* 276 */       setFlags(EnumSet.of(Goal.Flag.LOOK));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canUse() {
/* 281 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean requiresUpdateEveryTick() {
/* 286 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick() {
/* 291 */       if (this.ghast.getTarget() == null) {
/* 292 */         Vec3 $$0 = this.ghast.getDeltaMovement();
/* 293 */         this.ghast.setYRot(-((float)Mth.atan2($$0.x, $$0.z)) * 57.295776F);
/* 294 */         this.ghast.yBodyRot = this.ghast.getYRot();
/*     */       } else {
/* 296 */         LivingEntity $$1 = this.ghast.getTarget();
/*     */         
/* 298 */         double $$2 = 64.0D;
/* 299 */         if ($$1.distanceToSqr((Entity)this.ghast) < 4096.0D) {
/* 300 */           double $$3 = $$1.getX() - this.ghast.getX();
/* 301 */           double $$4 = $$1.getZ() - this.ghast.getZ();
/* 302 */           this.ghast.setYRot(-((float)Mth.atan2($$3, $$4)) * 57.295776F);
/* 303 */           this.ghast.yBodyRot = this.ghast.getYRot();
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private static class GhastShootFireballGoal extends Goal {
/*     */     private final Ghast ghast;
/*     */     public int chargeTime;
/*     */     
/*     */     public GhastShootFireballGoal(Ghast $$0) {
/* 314 */       this.ghast = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canUse() {
/* 319 */       return (this.ghast.getTarget() != null);
/*     */     }
/*     */ 
/*     */     
/*     */     public void start() {
/* 324 */       this.chargeTime = 0;
/*     */     }
/*     */ 
/*     */     
/*     */     public void stop() {
/* 329 */       this.ghast.setCharging(false);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean requiresUpdateEveryTick() {
/* 334 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick() {
/* 339 */       LivingEntity $$0 = this.ghast.getTarget();
/* 340 */       if ($$0 == null) {
/*     */         return;
/*     */       }
/*     */       
/* 344 */       double $$1 = 64.0D;
/* 345 */       if ($$0.distanceToSqr((Entity)this.ghast) < 4096.0D && this.ghast.hasLineOfSight((Entity)$$0)) {
/* 346 */         Level $$2 = this.ghast.level();
/*     */         
/* 348 */         this.chargeTime++;
/* 349 */         if (this.chargeTime == 10 && !this.ghast.isSilent()) {
/* 350 */           $$2.levelEvent(null, 1015, this.ghast.blockPosition(), 0);
/*     */         }
/* 352 */         if (this.chargeTime == 20) {
/* 353 */           double $$3 = 4.0D;
/* 354 */           Vec3 $$4 = this.ghast.getViewVector(1.0F);
/*     */           
/* 356 */           double $$5 = $$0.getX() - this.ghast.getX() + $$4.x * 4.0D;
/* 357 */           double $$6 = $$0.getY(0.5D) - 0.5D + this.ghast.getY(0.5D);
/* 358 */           double $$7 = $$0.getZ() - this.ghast.getZ() + $$4.z * 4.0D;
/*     */           
/* 360 */           if (!this.ghast.isSilent()) {
/* 361 */             $$2.levelEvent(null, 1016, this.ghast.blockPosition(), 0);
/*     */           }
/* 363 */           LargeFireball $$8 = new LargeFireball($$2, (LivingEntity)this.ghast, $$5, $$6, $$7, this.ghast.getExplosionPower());
/* 364 */           $$8.setPos(this.ghast.getX() + $$4.x * 4.0D, this.ghast.getY(0.5D) + 0.5D, $$8.getZ() + $$4.z * 4.0D);
/* 365 */           $$2.addFreshEntity((Entity)$$8);
/* 366 */           this.chargeTime = -40;
/*     */         } 
/* 368 */       } else if (this.chargeTime > 0) {
/* 369 */         this.chargeTime--;
/*     */       } 
/* 371 */       this.ghast.setCharging((this.chargeTime > 10));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getStandingEyeHeight(Pose $$0, EntityDimensions $$1) {
/* 377 */     return 2.6F;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\Ghast.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */