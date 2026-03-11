/*     */ package net.minecraft.world.entity.animal;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.syncher.EntityDataAccessor;
/*     */ import net.minecraft.network.syncher.EntityDataSerializers;
/*     */ import net.minecraft.network.syncher.SynchedEntityData;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.tags.BiomeTags;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.util.TimeUtil;
/*     */ import net.minecraft.util.valueproviders.UniformInt;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.entity.AgeableMob;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityDimensions;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.MobSpawnType;
/*     */ import net.minecraft.world.entity.NeutralMob;
/*     */ import net.minecraft.world.entity.PathfinderMob;
/*     */ import net.minecraft.world.entity.Pose;
/*     */ import net.minecraft.world.entity.SpawnGroupData;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.entity.ai.goal.FloatGoal;
/*     */ import net.minecraft.world.entity.ai.goal.FollowParentGoal;
/*     */ import net.minecraft.world.entity.ai.goal.Goal;
/*     */ import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
/*     */ import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
/*     */ import net.minecraft.world.entity.ai.goal.PanicGoal;
/*     */ import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
/*     */ import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
/*     */ import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
/*     */ import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
/*     */ import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.BlockAndTintGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.ServerLevelAccessor;
/*     */ import net.minecraft.world.level.biome.Biome;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ 
/*     */ public class PolarBear extends Animal implements NeutralMob {
/*  56 */   private static final EntityDataAccessor<Boolean> DATA_STANDING_ID = SynchedEntityData.defineId(PolarBear.class, EntityDataSerializers.BOOLEAN);
/*     */   
/*     */   private static final float STAND_ANIMATION_TICKS = 6.0F;
/*     */   
/*     */   private float clientSideStandAnimationO;
/*     */   private float clientSideStandAnimation;
/*     */   private int warningSoundTicks;
/*  63 */   private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
/*     */   private int remainingPersistentAngerTime;
/*     */   @Nullable
/*     */   private UUID persistentAngerTarget;
/*     */   
/*     */   public PolarBear(EntityType<? extends PolarBear> $$0, Level $$1) {
/*  69 */     super((EntityType)$$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public AgeableMob getBreedOffspring(ServerLevel $$0, AgeableMob $$1) {
/*  75 */     return (AgeableMob)EntityType.POLAR_BEAR.create((Level)$$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFood(ItemStack $$0) {
/*  80 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerGoals() {
/*  85 */     super.registerGoals();
/*     */     
/*  87 */     this.goalSelector.addGoal(0, (Goal)new FloatGoal((Mob)this));
/*  88 */     this.goalSelector.addGoal(1, (Goal)new PolarBearMeleeAttackGoal());
/*  89 */     this.goalSelector.addGoal(1, (Goal)new PolarBearPanicGoal());
/*  90 */     this.goalSelector.addGoal(4, (Goal)new FollowParentGoal(this, 1.25D));
/*  91 */     this.goalSelector.addGoal(5, (Goal)new RandomStrollGoal((PathfinderMob)this, 1.0D));
/*  92 */     this.goalSelector.addGoal(6, (Goal)new LookAtPlayerGoal((Mob)this, Player.class, 6.0F));
/*  93 */     this.goalSelector.addGoal(7, (Goal)new RandomLookAroundGoal((Mob)this));
/*     */     
/*  95 */     this.targetSelector.addGoal(1, (Goal)new PolarBearHurtByTargetGoal());
/*  96 */     this.targetSelector.addGoal(2, (Goal)new PolarBearAttackPlayersGoal());
/*  97 */     this.targetSelector.addGoal(3, (Goal)new NearestAttackableTargetGoal((Mob)this, Player.class, 10, true, false, this::isAngryAt));
/*  98 */     this.targetSelector.addGoal(4, (Goal)new NearestAttackableTargetGoal((Mob)this, Fox.class, 10, true, true, null));
/*  99 */     this.targetSelector.addGoal(5, (Goal)new ResetUniversalAngerTargetGoal((Mob)this, false));
/*     */   }
/*     */   
/*     */   public static AttributeSupplier.Builder createAttributes() {
/* 103 */     return Mob.createMobAttributes()
/* 104 */       .add(Attributes.MAX_HEALTH, 30.0D)
/* 105 */       .add(Attributes.FOLLOW_RANGE, 20.0D)
/* 106 */       .add(Attributes.MOVEMENT_SPEED, 0.25D)
/* 107 */       .add(Attributes.ATTACK_DAMAGE, 6.0D);
/*     */   }
/*     */   
/*     */   public static boolean checkPolarBearSpawnRules(EntityType<PolarBear> $$0, LevelAccessor $$1, MobSpawnType $$2, BlockPos $$3, RandomSource $$4) {
/* 111 */     Holder<Biome> $$5 = $$1.getBiome($$3);
/*     */     
/* 113 */     if ($$5.is(BiomeTags.POLAR_BEARS_SPAWN_ON_ALTERNATE_BLOCKS)) {
/* 114 */       return (isBrightEnoughToSpawn((BlockAndTintGetter)$$1, $$3) && $$1.getBlockState($$3.below()).is(BlockTags.POLAR_BEARS_SPAWNABLE_ON_ALTERNATE));
/*     */     }
/*     */     
/* 117 */     return checkAnimalSpawnRules((EntityType)$$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/* 122 */     super.readAdditionalSaveData($$0);
/* 123 */     readPersistentAngerSaveData(level(), $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/* 128 */     super.addAdditionalSaveData($$0);
/* 129 */     addPersistentAngerSaveData($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void startPersistentAngerTimer() {
/* 134 */     setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRemainingPersistentAngerTime(int $$0) {
/* 139 */     this.remainingPersistentAngerTime = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRemainingPersistentAngerTime() {
/* 144 */     return this.remainingPersistentAngerTime;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPersistentAngerTarget(@Nullable UUID $$0) {
/* 149 */     this.persistentAngerTarget = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public UUID getPersistentAngerTarget() {
/* 155 */     return this.persistentAngerTarget;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/* 160 */     if (isBaby()) {
/* 161 */       return SoundEvents.POLAR_BEAR_AMBIENT_BABY;
/*     */     }
/* 163 */     return SoundEvents.POLAR_BEAR_AMBIENT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource $$0) {
/* 168 */     return SoundEvents.POLAR_BEAR_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/* 173 */     return SoundEvents.POLAR_BEAR_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void playStepSound(BlockPos $$0, BlockState $$1) {
/* 178 */     playSound(SoundEvents.POLAR_BEAR_STEP, 0.15F, 1.0F);
/*     */   }
/*     */   
/*     */   protected void playWarningSound() {
/* 182 */     if (this.warningSoundTicks <= 0) {
/* 183 */       playSound(SoundEvents.POLAR_BEAR_WARNING, 1.0F, getVoicePitch());
/*     */       
/* 185 */       this.warningSoundTicks = 40;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {
/* 191 */     super.defineSynchedData();
/*     */     
/* 193 */     this.entityData.define(DATA_STANDING_ID, Boolean.valueOf(false));
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 198 */     super.tick();
/*     */     
/* 200 */     if ((level()).isClientSide) {
/* 201 */       if (this.clientSideStandAnimation != this.clientSideStandAnimationO) {
/* 202 */         refreshDimensions();
/*     */       }
/* 204 */       this.clientSideStandAnimationO = this.clientSideStandAnimation;
/* 205 */       if (isStanding()) {
/* 206 */         this.clientSideStandAnimation = Mth.clamp(this.clientSideStandAnimation + 1.0F, 0.0F, 6.0F);
/*     */       } else {
/* 208 */         this.clientSideStandAnimation = Mth.clamp(this.clientSideStandAnimation - 1.0F, 0.0F, 6.0F);
/*     */       } 
/*     */     } 
/*     */     
/* 212 */     if (this.warningSoundTicks > 0) {
/* 213 */       this.warningSoundTicks--;
/*     */     }
/*     */     
/* 216 */     if (!(level()).isClientSide) {
/* 217 */       updatePersistentAnger((ServerLevel)level(), true);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityDimensions getDimensions(Pose $$0) {
/* 223 */     if (this.clientSideStandAnimation > 0.0F) {
/*     */       
/* 225 */       float $$1 = this.clientSideStandAnimation / 6.0F;
/* 226 */       float $$2 = 1.0F + $$1;
/* 227 */       return super.getDimensions($$0).scale(1.0F, $$2);
/*     */     } 
/* 229 */     return super.getDimensions($$0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean doHurtTarget(Entity $$0) {
/* 236 */     boolean $$1 = $$0.hurt(damageSources().mobAttack((LivingEntity)this), (int)getAttributeValue(Attributes.ATTACK_DAMAGE));
/* 237 */     if ($$1) {
/* 238 */       doEnchantDamageEffects((LivingEntity)this, $$0);
/*     */     }
/* 240 */     return $$1;
/*     */   }
/*     */   
/*     */   public boolean isStanding() {
/* 244 */     return ((Boolean)this.entityData.get(DATA_STANDING_ID)).booleanValue();
/*     */   }
/*     */   
/*     */   public void setStanding(boolean $$0) {
/* 248 */     this.entityData.set(DATA_STANDING_ID, Boolean.valueOf($$0));
/*     */   }
/*     */   
/*     */   public float getStandingAnimationScale(float $$0) {
/* 252 */     return Mth.lerp($$0, this.clientSideStandAnimationO, this.clientSideStandAnimation) / 6.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getWaterSlowDown() {
/* 257 */     return 0.98F;
/*     */   }
/*     */   
/*     */   public SpawnGroupData finalizeSpawn(ServerLevelAccessor $$0, DifficultyInstance $$1, MobSpawnType $$2, @Nullable SpawnGroupData $$3, @Nullable CompoundTag $$4) {
/*     */     AgeableMob.AgeableMobGroupData ageableMobGroupData;
/* 262 */     if ($$3 == null) {
/* 263 */       ageableMobGroupData = new AgeableMob.AgeableMobGroupData(1.0F);
/*     */     }
/*     */     
/* 266 */     return super.finalizeSpawn($$0, $$1, $$2, (SpawnGroupData)ageableMobGroupData, $$4);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private class PolarBearHurtByTargetGoal
/*     */     extends HurtByTargetGoal
/*     */   {
/*     */     public PolarBearHurtByTargetGoal() {
/* 275 */       super((PathfinderMob)PolarBear.this, new Class[0]);
/*     */     }
/*     */ 
/*     */     
/*     */     public void start() {
/* 280 */       super.start();
/* 281 */       if (PolarBear.this.isBaby()) {
/* 282 */         alertOthers();
/* 283 */         stop();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     protected void alertOther(Mob $$0, LivingEntity $$1) {
/* 289 */       if ($$0 instanceof PolarBear && 
/* 290 */         !$$0.isBaby()) {
/* 291 */         super.alertOther($$0, $$1);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class PolarBearAttackPlayersGoal
/*     */     extends NearestAttackableTargetGoal<Player>
/*     */   {
/*     */     public PolarBearAttackPlayersGoal() {
/* 303 */       super((Mob)PolarBear.this, Player.class, 20, true, true, null);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canUse() {
/* 308 */       if (PolarBear.this.isBaby()) {
/* 309 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 313 */       if (super.canUse()) {
/* 314 */         List<PolarBear> $$0 = PolarBear.this.level().getEntitiesOfClass(PolarBear.class, PolarBear.this.getBoundingBox().inflate(8.0D, 4.0D, 8.0D));
/* 315 */         for (PolarBear $$1 : $$0) {
/* 316 */           if ($$1.isBaby()) {
/* 317 */             return true;
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 322 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     protected double getFollowDistance() {
/* 327 */       return super.getFollowDistance() * 0.5D;
/*     */     }
/*     */   }
/*     */   
/*     */   private class PolarBearMeleeAttackGoal extends MeleeAttackGoal {
/*     */     public PolarBearMeleeAttackGoal() {
/* 333 */       super((PathfinderMob)PolarBear.this, 1.25D, true);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void checkAndPerformAttack(LivingEntity $$0) {
/* 338 */       if (canPerformAttack($$0)) {
/* 339 */         resetAttackCooldown();
/* 340 */         this.mob.doHurtTarget((Entity)$$0);
/* 341 */         PolarBear.this.setStanding(false);
/* 342 */       } else if (this.mob.distanceToSqr((Entity)$$0) < (($$0.getBbWidth() + 3.0F) * ($$0.getBbWidth() + 3.0F))) {
/* 343 */         if (isTimeToAttack()) {
/* 344 */           PolarBear.this.setStanding(false);
/* 345 */           resetAttackCooldown();
/*     */         } 
/* 347 */         if (getTicksUntilNextAttack() <= 10) {
/* 348 */           PolarBear.this.setStanding(true);
/* 349 */           PolarBear.this.playWarningSound();
/*     */         } 
/*     */       } else {
/*     */         
/* 353 */         resetAttackCooldown();
/* 354 */         PolarBear.this.setStanding(false);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void stop() {
/* 360 */       PolarBear.this.setStanding(false);
/* 361 */       super.stop();
/*     */     }
/*     */   }
/*     */   
/*     */   private class PolarBearPanicGoal extends PanicGoal {
/*     */     public PolarBearPanicGoal() {
/* 367 */       super((PathfinderMob)PolarBear.this, 2.0D);
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean shouldPanic() {
/* 372 */       return ((this.mob.getLastHurtByMob() != null && this.mob.isBaby()) || this.mob.isOnFire());
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\animal\PolarBear.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */