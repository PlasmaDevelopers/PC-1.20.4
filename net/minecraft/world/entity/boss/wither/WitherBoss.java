/*     */ package net.minecraft.world.entity.boss.wither;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.util.EnumSet;
/*     */ import java.util.List;
/*     */ import java.util.function.Predicate;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.syncher.EntityDataAccessor;
/*     */ import net.minecraft.network.syncher.EntityDataSerializers;
/*     */ import net.minecraft.network.syncher.SynchedEntityData;
/*     */ import net.minecraft.server.level.ServerBossEvent;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.tags.DamageTypeTags;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.BossEvent;
/*     */ import net.minecraft.world.Difficulty;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.effect.MobEffectInstance;
/*     */ import net.minecraft.world.effect.MobEffects;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.MobType;
/*     */ import net.minecraft.world.entity.PathfinderMob;
/*     */ import net.minecraft.world.entity.PowerableMob;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.entity.ai.control.FlyingMoveControl;
/*     */ import net.minecraft.world.entity.ai.control.MoveControl;
/*     */ import net.minecraft.world.entity.ai.goal.Goal;
/*     */ import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
/*     */ import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
/*     */ import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
/*     */ import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
/*     */ import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
/*     */ import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
/*     */ import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
/*     */ import net.minecraft.world.entity.ai.navigation.PathNavigation;
/*     */ import net.minecraft.world.entity.ai.targeting.TargetingConditions;
/*     */ import net.minecraft.world.entity.item.ItemEntity;
/*     */ import net.minecraft.world.entity.monster.Monster;
/*     */ import net.minecraft.world.entity.monster.RangedAttackMob;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.entity.projectile.WitherSkull;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.GameRules;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class WitherBoss extends Monster implements PowerableMob, RangedAttackMob {
/*  62 */   private static final EntityDataAccessor<Integer> DATA_TARGET_A = SynchedEntityData.defineId(WitherBoss.class, EntityDataSerializers.INT);
/*  63 */   private static final EntityDataAccessor<Integer> DATA_TARGET_B = SynchedEntityData.defineId(WitherBoss.class, EntityDataSerializers.INT);
/*  64 */   private static final EntityDataAccessor<Integer> DATA_TARGET_C = SynchedEntityData.defineId(WitherBoss.class, EntityDataSerializers.INT);
/*  65 */   private static final List<EntityDataAccessor<Integer>> DATA_TARGETS = (List<EntityDataAccessor<Integer>>)ImmutableList.of(DATA_TARGET_A, DATA_TARGET_B, DATA_TARGET_C);
/*  66 */   private static final EntityDataAccessor<Integer> DATA_ID_INV = SynchedEntityData.defineId(WitherBoss.class, EntityDataSerializers.INT);
/*     */   
/*     */   private static final int INVULNERABLE_TICKS = 220;
/*     */   
/*  70 */   private final float[] xRotHeads = new float[2];
/*  71 */   private final float[] yRotHeads = new float[2];
/*  72 */   private final float[] xRotOHeads = new float[2];
/*  73 */   private final float[] yRotOHeads = new float[2];
/*  74 */   private final int[] nextHeadUpdate = new int[2];
/*  75 */   private final int[] idleHeadUpdates = new int[2]; private int destroyBlocksTick;
/*     */   private static final Predicate<LivingEntity> LIVING_ENTITY_SELECTOR;
/*  77 */   private final ServerBossEvent bossEvent = (ServerBossEvent)(new ServerBossEvent(getDisplayName(), BossEvent.BossBarColor.PURPLE, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true);
/*     */   static {
/*  79 */     LIVING_ENTITY_SELECTOR = ($$0 -> ($$0.getMobType() != MobType.UNDEAD && $$0.attackable()));
/*  80 */   } private static final TargetingConditions TARGETING_CONDITIONS = TargetingConditions.forCombat().range(20.0D).selector(LIVING_ENTITY_SELECTOR);
/*     */   
/*     */   public WitherBoss(EntityType<? extends WitherBoss> $$0, Level $$1) {
/*  83 */     super($$0, $$1);
/*     */     
/*  85 */     this.moveControl = (MoveControl)new FlyingMoveControl((Mob)this, 10, false);
/*     */     
/*  87 */     setHealth(getMaxHealth());
/*     */     
/*  89 */     this.xpReward = 50;
/*     */   }
/*     */ 
/*     */   
/*     */   protected PathNavigation createNavigation(Level $$0) {
/*  94 */     FlyingPathNavigation $$1 = new FlyingPathNavigation((Mob)this, $$0);
/*  95 */     $$1.setCanOpenDoors(false);
/*  96 */     $$1.setCanFloat(true);
/*  97 */     $$1.setCanPassDoors(true);
/*  98 */     return (PathNavigation)$$1;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerGoals() {
/* 103 */     this.goalSelector.addGoal(0, new WitherDoNothingGoal());
/* 104 */     this.goalSelector.addGoal(2, (Goal)new RangedAttackGoal(this, 1.0D, 40, 20.0F));
/*     */     
/* 106 */     this.goalSelector.addGoal(5, (Goal)new WaterAvoidingRandomFlyingGoal((PathfinderMob)this, 1.0D));
/* 107 */     this.goalSelector.addGoal(6, (Goal)new LookAtPlayerGoal((Mob)this, Player.class, 8.0F));
/* 108 */     this.goalSelector.addGoal(7, (Goal)new RandomLookAroundGoal((Mob)this));
/*     */     
/* 110 */     this.targetSelector.addGoal(1, (Goal)new HurtByTargetGoal((PathfinderMob)this, new Class[0]));
/* 111 */     this.targetSelector.addGoal(2, (Goal)new NearestAttackableTargetGoal((Mob)this, LivingEntity.class, 0, false, false, LIVING_ENTITY_SELECTOR));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {
/* 116 */     super.defineSynchedData();
/*     */     
/* 118 */     this.entityData.define(DATA_TARGET_A, Integer.valueOf(0));
/* 119 */     this.entityData.define(DATA_TARGET_B, Integer.valueOf(0));
/* 120 */     this.entityData.define(DATA_TARGET_C, Integer.valueOf(0));
/* 121 */     this.entityData.define(DATA_ID_INV, Integer.valueOf(0));
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/* 126 */     super.addAdditionalSaveData($$0);
/*     */     
/* 128 */     $$0.putInt("Invul", getInvulnerableTicks());
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/* 133 */     super.readAdditionalSaveData($$0);
/*     */     
/* 135 */     setInvulnerableTicks($$0.getInt("Invul"));
/* 136 */     if (hasCustomName()) {
/* 137 */       this.bossEvent.setName(getDisplayName());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCustomName(@Nullable Component $$0) {
/* 143 */     super.setCustomName($$0);
/* 144 */     this.bossEvent.setName(getDisplayName());
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/* 149 */     return SoundEvents.WITHER_AMBIENT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource $$0) {
/* 154 */     return SoundEvents.WITHER_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/* 159 */     return SoundEvents.WITHER_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   public void aiStep() {
/* 164 */     Vec3 $$0 = getDeltaMovement().multiply(1.0D, 0.6D, 1.0D);
/*     */     
/* 166 */     if (!(level()).isClientSide && getAlternativeTarget(0) > 0) {
/* 167 */       Entity $$1 = level().getEntity(getAlternativeTarget(0));
/* 168 */       if ($$1 != null) {
/* 169 */         double $$2 = $$0.y;
/* 170 */         if (getY() < $$1.getY() || (!isPowered() && getY() < $$1.getY() + 5.0D)) {
/* 171 */           $$2 = Math.max(0.0D, $$2);
/*     */           
/* 173 */           $$2 += 0.3D - $$2 * 0.6000000238418579D;
/*     */         } 
/* 175 */         $$0 = new Vec3($$0.x, $$2, $$0.z);
/*     */         
/* 177 */         Vec3 $$3 = new Vec3($$1.getX() - getX(), 0.0D, $$1.getZ() - getZ());
/* 178 */         if ($$3.horizontalDistanceSqr() > 9.0D) {
/* 179 */           Vec3 $$4 = $$3.normalize();
/* 180 */           $$0 = $$0.add($$4.x * 0.3D - $$0.x * 0.6D, 0.0D, $$4.z * 0.3D - $$0.z * 0.6D);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 189 */     setDeltaMovement($$0);
/* 190 */     if ($$0.horizontalDistanceSqr() > 0.05D) {
/* 191 */       setYRot((float)Mth.atan2($$0.z, $$0.x) * 57.295776F - 90.0F);
/*     */     }
/* 193 */     super.aiStep();
/*     */     
/* 195 */     for (int $$5 = 0; $$5 < 2; $$5++) {
/* 196 */       this.yRotOHeads[$$5] = this.yRotHeads[$$5];
/* 197 */       this.xRotOHeads[$$5] = this.xRotHeads[$$5];
/*     */     } 
/*     */     
/* 200 */     for (int $$6 = 0; $$6 < 2; $$6++) {
/* 201 */       int $$7 = getAlternativeTarget($$6 + 1);
/* 202 */       Entity $$8 = null;
/* 203 */       if ($$7 > 0) {
/* 204 */         $$8 = level().getEntity($$7);
/*     */       }
/* 206 */       if ($$8 != null) {
/* 207 */         double $$9 = getHeadX($$6 + 1);
/* 208 */         double $$10 = getHeadY($$6 + 1);
/* 209 */         double $$11 = getHeadZ($$6 + 1);
/*     */         
/* 211 */         double $$12 = $$8.getX() - $$9;
/* 212 */         double $$13 = $$8.getEyeY() - $$10;
/* 213 */         double $$14 = $$8.getZ() - $$11;
/* 214 */         double $$15 = Math.sqrt($$12 * $$12 + $$14 * $$14);
/*     */         
/* 216 */         float $$16 = (float)(Mth.atan2($$14, $$12) * 57.2957763671875D) - 90.0F;
/* 217 */         float $$17 = (float)-(Mth.atan2($$13, $$15) * 57.2957763671875D);
/* 218 */         this.xRotHeads[$$6] = rotlerp(this.xRotHeads[$$6], $$17, 40.0F);
/* 219 */         this.yRotHeads[$$6] = rotlerp(this.yRotHeads[$$6], $$16, 10.0F);
/*     */       } else {
/* 221 */         this.yRotHeads[$$6] = rotlerp(this.yRotHeads[$$6], this.yBodyRot, 10.0F);
/*     */       } 
/*     */     } 
/* 224 */     boolean $$18 = isPowered();
/* 225 */     for (int $$19 = 0; $$19 < 3; $$19++) {
/* 226 */       double $$20 = getHeadX($$19);
/* 227 */       double $$21 = getHeadY($$19);
/* 228 */       double $$22 = getHeadZ($$19);
/*     */       
/* 230 */       level().addParticle((ParticleOptions)ParticleTypes.SMOKE, $$20 + this.random.nextGaussian() * 0.30000001192092896D, $$21 + this.random.nextGaussian() * 0.30000001192092896D, $$22 + this.random.nextGaussian() * 0.30000001192092896D, 0.0D, 0.0D, 0.0D);
/* 231 */       if ($$18 && (level()).random.nextInt(4) == 0) {
/* 232 */         level().addParticle((ParticleOptions)ParticleTypes.ENTITY_EFFECT, $$20 + this.random.nextGaussian() * 0.30000001192092896D, $$21 + this.random.nextGaussian() * 0.30000001192092896D, $$22 + this.random.nextGaussian() * 0.30000001192092896D, 0.699999988079071D, 0.699999988079071D, 0.5D);
/*     */       }
/*     */     } 
/* 235 */     if (getInvulnerableTicks() > 0) {
/* 236 */       for (int $$23 = 0; $$23 < 3; $$23++) {
/* 237 */         level().addParticle((ParticleOptions)ParticleTypes.ENTITY_EFFECT, getX() + this.random.nextGaussian(), getY() + (this.random.nextFloat() * 3.3F), getZ() + this.random.nextGaussian(), 0.699999988079071D, 0.699999988079071D, 0.8999999761581421D);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void customServerAiStep() {
/* 244 */     if (getInvulnerableTicks() > 0) {
/* 245 */       int $$0 = getInvulnerableTicks() - 1;
/* 246 */       this.bossEvent.setProgress(1.0F - $$0 / 220.0F);
/*     */       
/* 248 */       if ($$0 <= 0) {
/* 249 */         level().explode((Entity)this, getX(), getEyeY(), getZ(), 7.0F, false, Level.ExplosionInteraction.MOB);
/* 250 */         if (!isSilent()) {
/* 251 */           level().globalLevelEvent(1023, blockPosition(), 0);
/*     */         }
/*     */       } 
/*     */       
/* 255 */       setInvulnerableTicks($$0);
/* 256 */       if (this.tickCount % 10 == 0) {
/* 257 */         heal(10.0F);
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 263 */     super.customServerAiStep();
/*     */     
/* 265 */     for (int $$1 = 1; $$1 < 3; $$1++) {
/* 266 */       if (this.tickCount >= this.nextHeadUpdate[$$1 - 1]) {
/* 267 */         this.nextHeadUpdate[$$1 - 1] = this.tickCount + 10 + this.random.nextInt(10);
/*     */         
/* 269 */         this.idleHeadUpdates[$$1 - 1] = this.idleHeadUpdates[$$1 - 1] + 1; if ((level().getDifficulty() == Difficulty.NORMAL || level().getDifficulty() == Difficulty.HARD) && this.idleHeadUpdates[$$1 - 1] > 15) {
/* 270 */           float $$2 = 10.0F;
/* 271 */           float $$3 = 5.0F;
/* 272 */           double $$4 = Mth.nextDouble(this.random, getX() - 10.0D, getX() + 10.0D);
/* 273 */           double $$5 = Mth.nextDouble(this.random, getY() - 5.0D, getY() + 5.0D);
/* 274 */           double $$6 = Mth.nextDouble(this.random, getZ() - 10.0D, getZ() + 10.0D);
/* 275 */           performRangedAttack($$1 + 1, $$4, $$5, $$6, true);
/* 276 */           this.idleHeadUpdates[$$1 - 1] = 0;
/*     */         } 
/*     */         
/* 279 */         int $$7 = getAlternativeTarget($$1);
/* 280 */         if ($$7 > 0) {
/* 281 */           LivingEntity $$8 = (LivingEntity)level().getEntity($$7);
/* 282 */           if ($$8 == null || !canAttack($$8) || distanceToSqr((Entity)$$8) > 900.0D || !hasLineOfSight((Entity)$$8)) {
/* 283 */             setAlternativeTarget($$1, 0);
/*     */           } else {
/* 285 */             performRangedAttack($$1 + 1, $$8);
/* 286 */             this.nextHeadUpdate[$$1 - 1] = this.tickCount + 40 + this.random.nextInt(20);
/* 287 */             this.idleHeadUpdates[$$1 - 1] = 0;
/*     */           } 
/*     */         } else {
/* 290 */           List<LivingEntity> $$9 = level().getNearbyEntities(LivingEntity.class, TARGETING_CONDITIONS, (LivingEntity)this, getBoundingBox().inflate(20.0D, 8.0D, 20.0D));
/*     */           
/* 292 */           if (!$$9.isEmpty()) {
/* 293 */             LivingEntity $$10 = $$9.get(this.random.nextInt($$9.size()));
/* 294 */             setAlternativeTarget($$1, $$10.getId());
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 299 */     if (getTarget() != null) {
/* 300 */       setAlternativeTarget(0, getTarget().getId());
/*     */     } else {
/* 302 */       setAlternativeTarget(0, 0);
/*     */     } 
/*     */     
/* 305 */     if (this.destroyBlocksTick > 0) {
/* 306 */       this.destroyBlocksTick--;
/*     */       
/* 308 */       if (this.destroyBlocksTick == 0 && level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
/*     */ 
/*     */ 
/*     */         
/* 312 */         int $$11 = Mth.floor(getY());
/* 313 */         int $$12 = Mth.floor(getX());
/* 314 */         int $$13 = Mth.floor(getZ());
/* 315 */         boolean $$14 = false;
/*     */         
/* 317 */         for (int $$15 = -1; $$15 <= 1; $$15++) {
/* 318 */           for (int $$16 = -1; $$16 <= 1; $$16++) {
/* 319 */             for (int $$17 = 0; $$17 <= 3; $$17++) {
/* 320 */               int $$18 = $$12 + $$15;
/* 321 */               int $$19 = $$11 + $$17;
/* 322 */               int $$20 = $$13 + $$16;
/* 323 */               BlockPos $$21 = new BlockPos($$18, $$19, $$20);
/* 324 */               BlockState $$22 = level().getBlockState($$21);
/* 325 */               if (canDestroy($$22)) {
/* 326 */                 $$14 = (level().destroyBlock($$21, true, (Entity)this) || $$14);
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/* 331 */         if ($$14) {
/* 332 */           level().levelEvent(null, 1022, blockPosition(), 0);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 337 */     if (this.tickCount % 20 == 0) {
/* 338 */       heal(1.0F);
/*     */     }
/*     */     
/* 341 */     this.bossEvent.setProgress(getHealth() / getMaxHealth());
/*     */   }
/*     */   
/*     */   public static boolean canDestroy(BlockState $$0) {
/* 345 */     return (!$$0.isAir() && !$$0.is(BlockTags.WITHER_IMMUNE));
/*     */   }
/*     */   
/*     */   public void makeInvulnerable() {
/* 349 */     setInvulnerableTicks(220);
/* 350 */     this.bossEvent.setProgress(0.0F);
/* 351 */     setHealth(getMaxHealth() / 3.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void makeStuckInBlock(BlockState $$0, Vec3 $$1) {}
/*     */ 
/*     */   
/*     */   public void startSeenByPlayer(ServerPlayer $$0) {
/* 360 */     super.startSeenByPlayer($$0);
/* 361 */     this.bossEvent.addPlayer($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void stopSeenByPlayer(ServerPlayer $$0) {
/* 366 */     super.stopSeenByPlayer($$0);
/* 367 */     this.bossEvent.removePlayer($$0);
/*     */   }
/*     */   
/*     */   private double getHeadX(int $$0) {
/* 371 */     if ($$0 <= 0) {
/* 372 */       return getX();
/*     */     }
/* 374 */     float $$1 = (this.yBodyRot + (180 * ($$0 - 1))) * 0.017453292F;
/* 375 */     float $$2 = Mth.cos($$1);
/* 376 */     return getX() + $$2 * 1.3D;
/*     */   }
/*     */   
/*     */   private double getHeadY(int $$0) {
/* 380 */     if ($$0 <= 0) {
/* 381 */       return getY() + 3.0D;
/*     */     }
/* 383 */     return getY() + 2.2D;
/*     */   }
/*     */ 
/*     */   
/*     */   private double getHeadZ(int $$0) {
/* 388 */     if ($$0 <= 0) {
/* 389 */       return getZ();
/*     */     }
/* 391 */     float $$1 = (this.yBodyRot + (180 * ($$0 - 1))) * 0.017453292F;
/* 392 */     float $$2 = Mth.sin($$1);
/* 393 */     return getZ() + $$2 * 1.3D;
/*     */   }
/*     */   
/*     */   private float rotlerp(float $$0, float $$1, float $$2) {
/* 397 */     float $$3 = Mth.wrapDegrees($$1 - $$0);
/* 398 */     if ($$3 > $$2) {
/* 399 */       $$3 = $$2;
/*     */     }
/* 401 */     if ($$3 < -$$2) {
/* 402 */       $$3 = -$$2;
/*     */     }
/* 404 */     return $$0 + $$3;
/*     */   }
/*     */   
/*     */   private void performRangedAttack(int $$0, LivingEntity $$1) {
/* 408 */     performRangedAttack($$0, $$1.getX(), $$1.getY() + $$1.getEyeHeight() * 0.5D, $$1.getZ(), ($$0 == 0 && this.random.nextFloat() < 0.001F));
/*     */   }
/*     */   
/*     */   private void performRangedAttack(int $$0, double $$1, double $$2, double $$3, boolean $$4) {
/* 412 */     if (!isSilent()) {
/* 413 */       level().levelEvent(null, 1024, blockPosition(), 0);
/*     */     }
/*     */     
/* 416 */     double $$5 = getHeadX($$0);
/* 417 */     double $$6 = getHeadY($$0);
/* 418 */     double $$7 = getHeadZ($$0);
/*     */     
/* 420 */     double $$8 = $$1 - $$5;
/* 421 */     double $$9 = $$2 - $$6;
/* 422 */     double $$10 = $$3 - $$7;
/*     */     
/* 424 */     WitherSkull $$11 = new WitherSkull(level(), (LivingEntity)this, $$8, $$9, $$10);
/* 425 */     $$11.setOwner((Entity)this);
/* 426 */     if ($$4) {
/* 427 */       $$11.setDangerous(true);
/*     */     }
/*     */     
/* 430 */     $$11.setPosRaw($$5, $$6, $$7);
/* 431 */     level().addFreshEntity((Entity)$$11);
/*     */   }
/*     */ 
/*     */   
/*     */   public void performRangedAttack(LivingEntity $$0, float $$1) {
/* 436 */     performRangedAttack(0, $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hurt(DamageSource $$0, float $$1) {
/* 441 */     if (isInvulnerableTo($$0)) {
/* 442 */       return false;
/*     */     }
/* 444 */     if ($$0.is(DamageTypeTags.WITHER_IMMUNE_TO) || $$0.getEntity() instanceof WitherBoss) {
/* 445 */       return false;
/*     */     }
/* 447 */     if (getInvulnerableTicks() > 0 && !$$0.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
/* 448 */       return false;
/*     */     }
/*     */     
/* 451 */     if (isPowered()) {
/* 452 */       Entity $$2 = $$0.getDirectEntity();
/* 453 */       if ($$2 instanceof net.minecraft.world.entity.projectile.AbstractArrow) {
/* 454 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 458 */     Entity $$3 = $$0.getEntity();
/* 459 */     if ($$3 != null && 
/* 460 */       !($$3 instanceof Player) && 
/* 461 */       $$3 instanceof LivingEntity && ((LivingEntity)$$3).getMobType() == getMobType())
/*     */     {
/* 463 */       return false;
/*     */     }
/*     */     
/* 466 */     if (this.destroyBlocksTick <= 0) {
/* 467 */       this.destroyBlocksTick = 20;
/*     */     }
/*     */     
/* 470 */     for (int $$4 = 0; $$4 < this.idleHeadUpdates.length; $$4++) {
/* 471 */       this.idleHeadUpdates[$$4] = this.idleHeadUpdates[$$4] + 3;
/*     */     }
/*     */     
/* 474 */     return super.hurt($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void dropCustomDeathLoot(DamageSource $$0, int $$1, boolean $$2) {
/* 479 */     super.dropCustomDeathLoot($$0, $$1, $$2);
/* 480 */     ItemEntity $$3 = spawnAtLocation((ItemLike)Items.NETHER_STAR);
/* 481 */     if ($$3 != null) {
/* 482 */       $$3.setExtendedLifetime();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void checkDespawn() {
/* 488 */     if (level().getDifficulty() == Difficulty.PEACEFUL && shouldDespawnInPeaceful()) {
/* 489 */       discard();
/*     */       
/*     */       return;
/*     */     } 
/* 493 */     this.noActionTime = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addEffect(MobEffectInstance $$0, @Nullable Entity $$1) {
/* 498 */     return false;
/*     */   }
/*     */   
/*     */   public static AttributeSupplier.Builder createAttributes() {
/* 502 */     return Monster.createMonsterAttributes()
/* 503 */       .add(Attributes.MAX_HEALTH, 300.0D)
/* 504 */       .add(Attributes.MOVEMENT_SPEED, 0.6000000238418579D)
/* 505 */       .add(Attributes.FLYING_SPEED, 0.6000000238418579D)
/* 506 */       .add(Attributes.FOLLOW_RANGE, 40.0D)
/* 507 */       .add(Attributes.ARMOR, 4.0D);
/*     */   }
/*     */   
/*     */   public float getHeadYRot(int $$0) {
/* 511 */     return this.yRotHeads[$$0];
/*     */   }
/*     */   
/*     */   public float getHeadXRot(int $$0) {
/* 515 */     return this.xRotHeads[$$0];
/*     */   }
/*     */   
/*     */   public int getInvulnerableTicks() {
/* 519 */     return ((Integer)this.entityData.get(DATA_ID_INV)).intValue();
/*     */   }
/*     */   
/*     */   public void setInvulnerableTicks(int $$0) {
/* 523 */     this.entityData.set(DATA_ID_INV, Integer.valueOf($$0));
/*     */   }
/*     */   
/*     */   public int getAlternativeTarget(int $$0) {
/* 527 */     return ((Integer)this.entityData.get(DATA_TARGETS.get($$0))).intValue();
/*     */   }
/*     */   
/*     */   public void setAlternativeTarget(int $$0, int $$1) {
/* 531 */     this.entityData.set(DATA_TARGETS.get($$0), Integer.valueOf($$1));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPowered() {
/* 536 */     return (getHealth() <= getMaxHealth() / 2.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public MobType getMobType() {
/* 541 */     return MobType.UNDEAD;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canRide(Entity $$0) {
/* 546 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canChangeDimensions() {
/* 551 */     return false;
/*     */   }
/*     */   
/*     */   private class WitherDoNothingGoal extends Goal {
/*     */     public WitherDoNothingGoal() {
/* 556 */       setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP, Goal.Flag.LOOK));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canUse() {
/* 561 */       return (WitherBoss.this.getInvulnerableTicks() > 0);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canBeAffected(MobEffectInstance $$0) {
/* 567 */     if ($$0.getEffect() == MobEffects.WITHER) {
/* 568 */       return false;
/*     */     }
/* 570 */     return super.canBeAffected($$0);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\boss\wither\WitherBoss.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */