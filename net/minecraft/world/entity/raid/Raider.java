/*     */ package net.minecraft.world.entity.raid;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.EnumSet;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.function.Predicate;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.Position;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.syncher.EntityDataAccessor;
/*     */ import net.minecraft.network.syncher.EntityDataSerializers;
/*     */ import net.minecraft.network.syncher.SynchedEntityData;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.effect.MobEffectInstance;
/*     */ import net.minecraft.world.effect.MobEffects;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.EquipmentSlot;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.MobSpawnType;
/*     */ import net.minecraft.world.entity.PathfinderMob;
/*     */ import net.minecraft.world.entity.SpawnGroupData;
/*     */ import net.minecraft.world.entity.ai.goal.Goal;
/*     */ import net.minecraft.world.entity.ai.targeting.TargetingConditions;
/*     */ import net.minecraft.world.entity.ai.util.DefaultRandomPos;
/*     */ import net.minecraft.world.entity.ai.village.poi.PoiManager;
/*     */ import net.minecraft.world.entity.ai.village.poi.PoiTypes;
/*     */ import net.minecraft.world.entity.animal.Wolf;
/*     */ import net.minecraft.world.entity.item.ItemEntity;
/*     */ import net.minecraft.world.entity.monster.AbstractIllager;
/*     */ import net.minecraft.world.entity.monster.PatrollingMonster;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.GameRules;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.ServerLevelAccessor;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public abstract class Raider extends PatrollingMonster {
/*  48 */   protected static final EntityDataAccessor<Boolean> IS_CELEBRATING = SynchedEntityData.defineId(Raider.class, EntityDataSerializers.BOOLEAN); static final Predicate<ItemEntity> ALLOWED_ITEMS;
/*     */   static {
/*  50 */     ALLOWED_ITEMS = ($$0 -> (!$$0.hasPickUpDelay() && $$0.isAlive() && ItemStack.matches($$0.getItem(), Raid.getLeaderBannerInstance())));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected Raid raid;
/*     */   private int wave;
/*     */   private boolean canJoinRaid;
/*     */   private int ticksOutsideRaid;
/*     */   
/*     */   protected Raider(EntityType<? extends Raider> $$0, Level $$1) {
/*  61 */     super($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerGoals() {
/*  66 */     super.registerGoals();
/*  67 */     this.goalSelector.addGoal(1, new ObtainRaidLeaderBannerGoal<>(this));
/*  68 */     this.goalSelector.addGoal(3, (Goal)new PathfindToRaidGoal(this));
/*  69 */     this.goalSelector.addGoal(4, new RaiderMoveThroughVillageGoal(this, 1.0499999523162842D, 1));
/*  70 */     this.goalSelector.addGoal(5, new RaiderCelebration(this));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {
/*  75 */     super.defineSynchedData();
/*     */     
/*  77 */     this.entityData.define(IS_CELEBRATING, Boolean.valueOf(false));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canJoinRaid() {
/*  83 */     return this.canJoinRaid;
/*     */   }
/*     */   
/*     */   public void setCanJoinRaid(boolean $$0) {
/*  87 */     this.canJoinRaid = $$0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void aiStep() {
/*  95 */     if (level() instanceof ServerLevel && isAlive()) {
/*  96 */       Raid $$0 = getCurrentRaid();
/*  97 */       if (canJoinRaid()) {
/*  98 */         if ($$0 == null) {
/*  99 */           if (level().getGameTime() % 20L == 0L) {
/* 100 */             Raid $$1 = ((ServerLevel)level()).getRaidAt(blockPosition());
/* 101 */             if ($$1 != null && Raids.canJoinRaid(this, $$1)) {
/* 102 */               $$1.joinRaid($$1.getGroupsSpawned(), this, null, true);
/*     */             }
/*     */           } 
/*     */         } else {
/*     */           
/* 107 */           LivingEntity $$2 = getTarget();
/* 108 */           if ($$2 != null && ($$2.getType() == EntityType.PLAYER || $$2.getType() == EntityType.IRON_GOLEM)) {
/* 109 */             this.noActionTime = 0;
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/* 114 */     super.aiStep();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateNoActionTime() {
/* 120 */     this.noActionTime += 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void die(DamageSource $$0) {
/* 125 */     if (level() instanceof ServerLevel) {
/* 126 */       Entity $$1 = $$0.getEntity();
/* 127 */       Raid $$2 = getCurrentRaid();
/* 128 */       if ($$2 != null) {
/* 129 */         if (isPatrolLeader()) {
/* 130 */           $$2.removeLeader(getWave());
/*     */         }
/*     */         
/* 133 */         if ($$1 != null && $$1.getType() == EntityType.PLAYER) {
/* 134 */           $$2.addHeroOfTheVillage($$1);
/*     */         }
/*     */         
/* 137 */         $$2.removeFromRaid(this, false);
/*     */       } 
/*     */ 
/*     */       
/* 141 */       if (isPatrolLeader() && $$2 == null && ((ServerLevel)level()).getRaidAt(blockPosition()) == null) {
/* 142 */         ItemStack $$3 = getItemBySlot(EquipmentSlot.HEAD);
/*     */         
/* 144 */         Player $$4 = null;
/* 145 */         Entity $$5 = $$1;
/* 146 */         if ($$5 instanceof Player)
/* 147 */         { $$4 = (Player)$$5; }
/* 148 */         else if ($$5 instanceof Wolf) { Wolf $$6 = (Wolf)$$5;
/* 149 */           LivingEntity $$7 = $$6.getOwner();
/* 150 */           if ($$6.isTame() && $$7 instanceof Player) {
/* 151 */             $$4 = (Player)$$7;
/*     */           } }
/*     */ 
/*     */         
/* 155 */         if (!$$3.isEmpty() && ItemStack.matches($$3, Raid.getLeaderBannerInstance()) && $$4 != null) {
/* 156 */           MobEffectInstance $$8 = $$4.getEffect(MobEffects.BAD_OMEN);
/* 157 */           int $$9 = 1;
/*     */           
/* 159 */           if ($$8 != null) {
/* 160 */             $$9 += $$8.getAmplifier();
/* 161 */             $$4.removeEffectNoUpdate(MobEffects.BAD_OMEN);
/*     */           } else {
/*     */             
/* 164 */             $$9--;
/*     */           } 
/*     */           
/* 167 */           $$9 = Mth.clamp($$9, 0, 4);
/*     */           
/* 169 */           MobEffectInstance $$10 = new MobEffectInstance(MobEffects.BAD_OMEN, 120000, $$9, false, false, true);
/* 170 */           if (!level().getGameRules().getBoolean(GameRules.RULE_DISABLE_RAIDS)) {
/* 171 */             $$4.addEffect($$10);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 177 */     super.die($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canJoinPatrol() {
/* 182 */     return !hasActiveRaid();
/*     */   }
/*     */   
/*     */   public void setCurrentRaid(@Nullable Raid $$0) {
/* 186 */     this.raid = $$0;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Raid getCurrentRaid() {
/* 191 */     return this.raid;
/*     */   }
/*     */   
/*     */   public boolean hasActiveRaid() {
/* 195 */     return (getCurrentRaid() != null && getCurrentRaid().isActive());
/*     */   }
/*     */   
/*     */   public void setWave(int $$0) {
/* 199 */     this.wave = $$0;
/*     */   }
/*     */   
/*     */   public int getWave() {
/* 203 */     return this.wave;
/*     */   }
/*     */   
/*     */   public boolean isCelebrating() {
/* 207 */     return ((Boolean)this.entityData.get(IS_CELEBRATING)).booleanValue();
/*     */   }
/*     */   
/*     */   public void setCelebrating(boolean $$0) {
/* 211 */     this.entityData.set(IS_CELEBRATING, Boolean.valueOf($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/* 216 */     super.addAdditionalSaveData($$0);
/* 217 */     $$0.putInt("Wave", this.wave);
/* 218 */     $$0.putBoolean("CanJoinRaid", this.canJoinRaid);
/* 219 */     if (this.raid != null) {
/* 220 */       $$0.putInt("RaidId", this.raid.getId());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/* 226 */     super.readAdditionalSaveData($$0);
/* 227 */     this.wave = $$0.getInt("Wave");
/* 228 */     this.canJoinRaid = $$0.getBoolean("CanJoinRaid");
/* 229 */     if ($$0.contains("RaidId", 3)) {
/* 230 */       if (level() instanceof ServerLevel) {
/* 231 */         this.raid = ((ServerLevel)level()).getRaids().get($$0.getInt("RaidId"));
/*     */       }
/*     */       
/* 234 */       if (this.raid != null) {
/* 235 */         this.raid.addWaveMob(this.wave, this, false);
/*     */         
/* 237 */         if (isPatrolLeader()) {
/* 238 */           this.raid.setLeader(this.wave, this);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void pickUpItem(ItemEntity $$0) {
/* 246 */     ItemStack $$1 = $$0.getItem();
/* 247 */     boolean $$2 = (hasActiveRaid() && getCurrentRaid().getLeader(getWave()) != null);
/*     */ 
/*     */     
/* 250 */     if (hasActiveRaid() && !$$2 && ItemStack.matches($$1, Raid.getLeaderBannerInstance())) {
/* 251 */       EquipmentSlot $$3 = EquipmentSlot.HEAD;
/* 252 */       ItemStack $$4 = getItemBySlot($$3);
/* 253 */       double $$5 = getEquipmentDropChance($$3);
/* 254 */       if (!$$4.isEmpty() && Math.max(this.random.nextFloat() - 0.1F, 0.0F) < $$5) {
/* 255 */         spawnAtLocation($$4);
/*     */       }
/* 257 */       onItemPickup($$0);
/* 258 */       setItemSlot($$3, $$1);
/* 259 */       take((Entity)$$0, $$1.getCount());
/* 260 */       $$0.discard();
/* 261 */       getCurrentRaid().setLeader(getWave(), this);
/* 262 */       setPatrolLeader(true);
/*     */     } else {
/* 264 */       super.pickUpItem($$0);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean removeWhenFarAway(double $$0) {
/* 270 */     if (getCurrentRaid() == null) {
/* 271 */       return super.removeWhenFarAway($$0);
/*     */     }
/* 273 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean requiresCustomPersistence() {
/* 278 */     return (super.requiresCustomPersistence() || getCurrentRaid() != null);
/*     */   }
/*     */   
/*     */   public int getTicksOutsideRaid() {
/* 282 */     return this.ticksOutsideRaid;
/*     */   }
/*     */   
/*     */   public void setTicksOutsideRaid(int $$0) {
/* 286 */     this.ticksOutsideRaid = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hurt(DamageSource $$0, float $$1) {
/* 291 */     if (hasActiveRaid()) {
/* 292 */       getCurrentRaid().updateBossbar();
/*     */     }
/* 294 */     return super.hurt($$0, $$1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public SpawnGroupData finalizeSpawn(ServerLevelAccessor $$0, DifficultyInstance $$1, MobSpawnType $$2, @Nullable SpawnGroupData $$3, @Nullable CompoundTag $$4) {
/* 301 */     setCanJoinRaid((getType() != EntityType.WITCH || $$2 != MobSpawnType.NATURAL));
/*     */     
/* 303 */     return super.finalizeSpawn($$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */   public abstract void applyRaidBuffs(int paramInt, boolean paramBoolean);
/*     */   
/*     */   public abstract SoundEvent getCelebrateSound();
/*     */   
/*     */   public class ObtainRaidLeaderBannerGoal<T extends Raider> extends Goal { private final T mob;
/*     */     
/*     */     public ObtainRaidLeaderBannerGoal(T $$1) {
/* 312 */       this.mob = $$1;
/* 313 */       setFlags(EnumSet.of(Goal.Flag.MOVE));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canUse() {
/* 318 */       Raid $$0 = this.mob.getCurrentRaid();
/* 319 */       if (!this.mob.hasActiveRaid() || this.mob.getCurrentRaid().isOver() || !this.mob.canBeLeader() || ItemStack.matches(this.mob.getItemBySlot(EquipmentSlot.HEAD), Raid.getLeaderBannerInstance())) {
/* 320 */         return false;
/*     */       }
/*     */       
/* 323 */       Raider $$1 = $$0.getLeader(this.mob.getWave());
/* 324 */       if ($$1 == null || !$$1.isAlive()) {
/* 325 */         List<ItemEntity> $$2 = this.mob.level().getEntitiesOfClass(ItemEntity.class, this.mob.getBoundingBox().inflate(16.0D, 8.0D, 16.0D), Raider.ALLOWED_ITEMS);
/* 326 */         if (!$$2.isEmpty()) {
/* 327 */           return this.mob.getNavigation().moveTo((Entity)$$2.get(0), 1.149999976158142D);
/*     */         }
/*     */       } 
/*     */       
/* 331 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick() {
/* 336 */       if (this.mob.getNavigation().getTargetPos().closerToCenterThan((Position)this.mob.position(), 1.414D)) {
/* 337 */         List<ItemEntity> $$0 = this.mob.level().getEntitiesOfClass(ItemEntity.class, this.mob.getBoundingBox().inflate(4.0D, 4.0D, 4.0D), Raider.ALLOWED_ITEMS);
/* 338 */         if (!$$0.isEmpty())
/* 339 */           this.mob.pickUpItem($$0.get(0)); 
/*     */       } 
/*     */     } }
/*     */ 
/*     */   
/*     */   public class RaiderCelebration
/*     */     extends Goal {
/*     */     private final Raider mob;
/*     */     
/*     */     RaiderCelebration(Raider $$1) {
/* 349 */       this.mob = $$1;
/* 350 */       setFlags(EnumSet.of(Goal.Flag.MOVE));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canUse() {
/* 355 */       Raid $$0 = this.mob.getCurrentRaid();
/* 356 */       return (this.mob.isAlive() && this.mob.getTarget() == null && $$0 != null && $$0.isLoss());
/*     */     }
/*     */ 
/*     */     
/*     */     public void start() {
/* 361 */       this.mob.setCelebrating(true);
/* 362 */       super.start();
/*     */     }
/*     */ 
/*     */     
/*     */     public void stop() {
/* 367 */       this.mob.setCelebrating(false);
/* 368 */       super.stop();
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick() {
/* 373 */       if (!this.mob.isSilent() && this.mob.random.nextInt(adjustedTickDelay(100)) == 0) {
/* 374 */         Raider.this.playSound(Raider.this.getCelebrateSound(), Raider.this.getSoundVolume(), Raider.this.getVoicePitch());
/*     */       }
/*     */       
/* 377 */       if (!this.mob.isPassenger() && this.mob.random.nextInt(adjustedTickDelay(50)) == 0) {
/* 378 */         this.mob.getJumpControl().jump();
/*     */       }
/*     */       
/* 381 */       super.tick();
/*     */     }
/*     */   }
/*     */   
/*     */   protected class HoldGroundAttackGoal extends Goal {
/*     */     private final Raider mob;
/*     */     private final float hostileRadiusSqr;
/* 388 */     public final TargetingConditions shoutTargeting = TargetingConditions.forNonCombat().range(8.0D).ignoreLineOfSight().ignoreInvisibilityTesting();
/*     */     
/*     */     public HoldGroundAttackGoal(AbstractIllager $$1, float $$2) {
/* 391 */       this.mob = (Raider)$$1;
/* 392 */       this.hostileRadiusSqr = $$2 * $$2;
/* 393 */       setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean canUse() {
/* 399 */       LivingEntity $$0 = this.mob.getLastHurtByMob();
/* 400 */       return (this.mob.getCurrentRaid() == null && this.mob.isPatrolling() && this.mob.getTarget() != null && !this.mob.isAggressive() && ($$0 == null || $$0.getType() != EntityType.PLAYER));
/*     */     }
/*     */ 
/*     */     
/*     */     public void start() {
/* 405 */       super.start();
/* 406 */       this.mob.getNavigation().stop();
/*     */       
/* 408 */       List<Raider> $$0 = this.mob.level().getNearbyEntities(Raider.class, this.shoutTargeting, (LivingEntity)this.mob, this.mob.getBoundingBox().inflate(8.0D, 8.0D, 8.0D));
/* 409 */       for (Raider $$1 : $$0) {
/* 410 */         $$1.setTarget(this.mob.getTarget());
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public void stop() {
/* 416 */       super.stop();
/*     */       
/* 418 */       LivingEntity $$0 = this.mob.getTarget();
/* 419 */       if ($$0 != null) {
/* 420 */         List<Raider> $$1 = this.mob.level().getNearbyEntities(Raider.class, this.shoutTargeting, (LivingEntity)this.mob, this.mob.getBoundingBox().inflate(8.0D, 8.0D, 8.0D));
/* 421 */         for (Raider $$2 : $$1) {
/* 422 */           $$2.setTarget($$0);
/* 423 */           $$2.setAggressive(true);
/*     */         } 
/* 425 */         this.mob.setAggressive(true);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean requiresUpdateEveryTick() {
/* 431 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick() {
/* 436 */       LivingEntity $$0 = this.mob.getTarget();
/* 437 */       if ($$0 == null) {
/*     */         return;
/*     */       }
/*     */       
/* 441 */       if (this.mob.distanceToSqr((Entity)$$0) > this.hostileRadiusSqr) {
/* 442 */         this.mob.getLookControl().setLookAt((Entity)$$0, 30.0F, 30.0F);
/*     */         
/* 444 */         if (this.mob.random.nextInt(50) == 0) {
/* 445 */           this.mob.playAmbientSound();
/*     */         }
/*     */       } else {
/* 448 */         this.mob.setAggressive(true);
/*     */       } 
/*     */       
/* 451 */       super.tick();
/*     */     }
/*     */   }
/*     */   
/*     */   private static class RaiderMoveThroughVillageGoal extends Goal {
/*     */     private final Raider raider;
/*     */     private final double speedModifier;
/*     */     private BlockPos poiPos;
/* 459 */     private final List<BlockPos> visited = Lists.newArrayList();
/*     */     private final int distanceToPoi;
/*     */     private boolean stuck;
/*     */     
/*     */     public RaiderMoveThroughVillageGoal(Raider $$0, double $$1, int $$2) {
/* 464 */       this.raider = $$0;
/* 465 */       this.speedModifier = $$1;
/* 466 */       this.distanceToPoi = $$2;
/* 467 */       setFlags(EnumSet.of(Goal.Flag.MOVE));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canUse() {
/* 472 */       updateVisited();
/* 473 */       return (isValidRaid() && hasSuitablePoi() && this.raider.getTarget() == null);
/*     */     }
/*     */     
/*     */     private boolean isValidRaid() {
/* 477 */       return (this.raider.hasActiveRaid() && !this.raider.getCurrentRaid().isOver());
/*     */     }
/*     */     
/*     */     private boolean hasSuitablePoi() {
/* 481 */       ServerLevel $$0 = (ServerLevel)this.raider.level();
/* 482 */       BlockPos $$1 = this.raider.blockPosition();
/* 483 */       Optional<BlockPos> $$2 = $$0.getPoiManager().getRandom($$0 -> $$0.is(PoiTypes.HOME), this::hasNotVisited, PoiManager.Occupancy.ANY, $$1, 48, this.raider.random);
/* 484 */       if ($$2.isEmpty()) {
/* 485 */         return false;
/*     */       }
/*     */       
/* 488 */       this.poiPos = ((BlockPos)$$2.get()).immutable();
/*     */       
/* 490 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canContinueToUse() {
/* 495 */       if (this.raider.getNavigation().isDone()) {
/* 496 */         return false;
/*     */       }
/* 498 */       return (this.raider.getTarget() == null && !this.poiPos.closerToCenterThan((Position)this.raider.position(), (this.raider.getBbWidth() + this.distanceToPoi)) && !this.stuck);
/*     */     }
/*     */ 
/*     */     
/*     */     public void stop() {
/* 503 */       if (this.poiPos.closerToCenterThan((Position)this.raider.position(), this.distanceToPoi)) {
/* 504 */         this.visited.add(this.poiPos);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public void start() {
/* 510 */       super.start();
/* 511 */       this.raider.setNoActionTime(0);
/* 512 */       this.raider.getNavigation().moveTo(this.poiPos.getX(), this.poiPos.getY(), this.poiPos.getZ(), this.speedModifier);
/* 513 */       this.stuck = false;
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick() {
/* 518 */       if (this.raider.getNavigation().isDone()) {
/* 519 */         Vec3 $$0 = Vec3.atBottomCenterOf((Vec3i)this.poiPos);
/* 520 */         Vec3 $$1 = DefaultRandomPos.getPosTowards((PathfinderMob)this.raider, 16, 7, $$0, 0.3141592741012573D);
/* 521 */         if ($$1 == null) {
/* 522 */           $$1 = DefaultRandomPos.getPosTowards((PathfinderMob)this.raider, 8, 7, $$0, 1.5707963705062866D);
/*     */         }
/*     */         
/* 525 */         if ($$1 == null) {
/* 526 */           this.stuck = true;
/*     */           
/*     */           return;
/*     */         } 
/* 530 */         this.raider.getNavigation().moveTo($$1.x, $$1.y, $$1.z, this.speedModifier);
/*     */       } 
/*     */     }
/*     */     
/*     */     private boolean hasNotVisited(BlockPos $$0) {
/* 535 */       for (BlockPos $$1 : this.visited) {
/* 536 */         if (Objects.equals($$0, $$1)) {
/* 537 */           return false;
/*     */         }
/*     */       } 
/* 540 */       return true;
/*     */     }
/*     */     
/*     */     private void updateVisited() {
/* 544 */       if (this.visited.size() > 2)
/* 545 */         this.visited.remove(0); 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\raid\Raider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */