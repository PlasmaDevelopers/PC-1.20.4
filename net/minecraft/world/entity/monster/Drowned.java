/*     */ package net.minecraft.world.entity.monster;
/*     */ 
/*     */ import java.util.EnumSet;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.tags.BiomeTags;
/*     */ import net.minecraft.tags.FluidTags;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.Difficulty;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.EquipmentSlot;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.MobSpawnType;
/*     */ import net.minecraft.world.entity.MoverType;
/*     */ import net.minecraft.world.entity.PathfinderMob;
/*     */ import net.minecraft.world.entity.SpawnGroupData;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.entity.ai.control.MoveControl;
/*     */ import net.minecraft.world.entity.ai.goal.Goal;
/*     */ import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
/*     */ import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
/*     */ import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
/*     */ import net.minecraft.world.entity.ai.goal.ZombieAttackGoal;
/*     */ import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
/*     */ import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
/*     */ import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
/*     */ import net.minecraft.world.entity.ai.navigation.PathNavigation;
/*     */ import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
/*     */ import net.minecraft.world.entity.ai.util.DefaultRandomPos;
/*     */ import net.minecraft.world.entity.animal.IronGolem;
/*     */ import net.minecraft.world.entity.animal.Turtle;
/*     */ import net.minecraft.world.entity.animal.axolotl.Axolotl;
/*     */ import net.minecraft.world.entity.npc.AbstractVillager;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.entity.projectile.ThrownTrident;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.ServerLevelAccessor;
/*     */ import net.minecraft.world.level.biome.Biome;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.pathfinder.BlockPathTypes;
/*     */ import net.minecraft.world.level.pathfinder.Path;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class Drowned extends Zombie implements RangedAttackMob {
/*     */   public static final float NAUTILUS_SHELL_CHANCE = 0.03F;
/*     */   boolean searchingForLand;
/*     */   
/*     */   public Drowned(EntityType<? extends Drowned> $$0, Level $$1) {
/*  66 */     super((EntityType)$$0, $$1);
/*  67 */     setMaxUpStep(1.0F);
/*  68 */     this.moveControl = new DrownedMoveControl(this);
/*     */     
/*  70 */     setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
/*  71 */     this.waterNavigation = new WaterBoundPathNavigation((Mob)this, $$1);
/*  72 */     this.groundNavigation = new GroundPathNavigation((Mob)this, $$1);
/*     */   }
/*     */   protected final WaterBoundPathNavigation waterNavigation; protected final GroundPathNavigation groundNavigation;
/*     */   
/*     */   protected void addBehaviourGoals() {
/*  77 */     this.goalSelector.addGoal(1, new DrownedGoToWaterGoal(this, 1.0D));
/*  78 */     this.goalSelector.addGoal(2, (Goal)new DrownedTridentAttackGoal(this, 1.0D, 40, 10.0F));
/*  79 */     this.goalSelector.addGoal(2, (Goal)new DrownedAttackGoal(this, 1.0D, false));
/*  80 */     this.goalSelector.addGoal(5, (Goal)new DrownedGoToBeachGoal(this, 1.0D));
/*  81 */     this.goalSelector.addGoal(6, new DrownedSwimUpGoal(this, 1.0D, level().getSeaLevel()));
/*  82 */     this.goalSelector.addGoal(7, (Goal)new RandomStrollGoal(this, 1.0D));
/*     */     
/*  84 */     this.targetSelector.addGoal(1, (Goal)(new HurtByTargetGoal(this, new Class[] { Drowned.class })).setAlertOthers(new Class[] { ZombifiedPiglin.class }));
/*  85 */     this.targetSelector.addGoal(2, (Goal)new NearestAttackableTargetGoal((Mob)this, Player.class, 10, true, false, this::okTarget));
/*  86 */     this.targetSelector.addGoal(3, (Goal)new NearestAttackableTargetGoal((Mob)this, AbstractVillager.class, false));
/*  87 */     this.targetSelector.addGoal(3, (Goal)new NearestAttackableTargetGoal((Mob)this, IronGolem.class, true));
/*  88 */     this.targetSelector.addGoal(3, (Goal)new NearestAttackableTargetGoal((Mob)this, Axolotl.class, true, false));
/*  89 */     this.targetSelector.addGoal(5, (Goal)new NearestAttackableTargetGoal((Mob)this, Turtle.class, 10, true, false, Turtle.BABY_ON_LAND_SELECTOR));
/*     */   }
/*     */ 
/*     */   
/*     */   public SpawnGroupData finalizeSpawn(ServerLevelAccessor $$0, DifficultyInstance $$1, MobSpawnType $$2, @Nullable SpawnGroupData $$3, @Nullable CompoundTag $$4) {
/*  94 */     $$3 = super.finalizeSpawn($$0, $$1, $$2, $$3, $$4);
/*     */     
/*  96 */     if (getItemBySlot(EquipmentSlot.OFFHAND).isEmpty() && 
/*  97 */       $$0.getRandom().nextFloat() < 0.03F) {
/*  98 */       setItemSlot(EquipmentSlot.OFFHAND, new ItemStack((ItemLike)Items.NAUTILUS_SHELL));
/*  99 */       setGuaranteedDrop(EquipmentSlot.OFFHAND);
/*     */     } 
/*     */ 
/*     */     
/* 103 */     return $$3;
/*     */   }
/*     */   
/*     */   public static boolean checkDrownedSpawnRules(EntityType<Drowned> $$0, ServerLevelAccessor $$1, MobSpawnType $$2, BlockPos $$3, RandomSource $$4) {
/* 107 */     if (!$$1.getFluidState($$3.below()).is(FluidTags.WATER) && !MobSpawnType.isSpawner($$2)) {
/* 108 */       return false;
/*     */     }
/*     */     
/* 111 */     Holder<Biome> $$5 = $$1.getBiome($$3);
/*     */ 
/*     */     
/* 114 */     boolean $$6 = ($$1.getDifficulty() != Difficulty.PEACEFUL && (MobSpawnType.ignoresLightRequirements($$2) || isDarkEnoughToSpawn($$1, $$3, $$4)) && (MobSpawnType.isSpawner($$2) || $$1.getFluidState($$3).is(FluidTags.WATER)));
/*     */     
/* 116 */     if ($$6 && MobSpawnType.isSpawner($$2))
/* 117 */       return true; 
/* 118 */     if ($$5.is(BiomeTags.MORE_FREQUENT_DROWNED_SPAWNS)) {
/* 119 */       return ($$4.nextInt(15) == 0 && $$6);
/*     */     }
/* 121 */     return ($$4.nextInt(40) == 0 && isDeepEnoughToSpawn((LevelAccessor)$$1, $$3) && $$6);
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isDeepEnoughToSpawn(LevelAccessor $$0, BlockPos $$1) {
/* 126 */     return ($$1.getY() < $$0.getSeaLevel() - 5);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean supportsBreakDoorGoal() {
/* 131 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/* 136 */     if (isInWater()) {
/* 137 */       return SoundEvents.DROWNED_AMBIENT_WATER;
/*     */     }
/* 139 */     return SoundEvents.DROWNED_AMBIENT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource $$0) {
/* 144 */     if (isInWater()) {
/* 145 */       return SoundEvents.DROWNED_HURT_WATER;
/*     */     }
/* 147 */     return SoundEvents.DROWNED_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/* 152 */     if (isInWater()) {
/* 153 */       return SoundEvents.DROWNED_DEATH_WATER;
/*     */     }
/* 155 */     return SoundEvents.DROWNED_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getStepSound() {
/* 160 */     return SoundEvents.DROWNED_STEP;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getSwimSound() {
/* 165 */     return SoundEvents.DROWNED_SWIM;
/*     */   }
/*     */ 
/*     */   
/*     */   protected ItemStack getSkull() {
/* 170 */     return ItemStack.EMPTY;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void populateDefaultEquipmentSlots(RandomSource $$0, DifficultyInstance $$1) {
/* 175 */     if ($$0.nextFloat() > 0.9D) {
/* 176 */       int $$2 = $$0.nextInt(16);
/* 177 */       if ($$2 < 10) {
/* 178 */         setItemSlot(EquipmentSlot.MAINHAND, new ItemStack((ItemLike)Items.TRIDENT));
/*     */       } else {
/* 180 */         setItemSlot(EquipmentSlot.MAINHAND, new ItemStack((ItemLike)Items.FISHING_ROD));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canReplaceCurrentItem(ItemStack $$0, ItemStack $$1) {
/* 187 */     if ($$1.is(Items.NAUTILUS_SHELL)) {
/* 188 */       return false;
/*     */     }
/*     */     
/* 191 */     if ($$1.is(Items.TRIDENT)) {
/* 192 */       if ($$0.is(Items.TRIDENT)) {
/* 193 */         return ($$0.getDamageValue() < $$1.getDamageValue());
/*     */       }
/*     */       
/* 196 */       return false;
/* 197 */     }  if ($$0.is(Items.TRIDENT)) {
/* 198 */       return true;
/*     */     }
/*     */     
/* 201 */     return super.canReplaceCurrentItem($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean convertsInWater() {
/* 206 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean checkSpawnObstruction(LevelReader $$0) {
/* 211 */     return $$0.isUnobstructed((Entity)this);
/*     */   }
/*     */   
/*     */   public boolean okTarget(@Nullable LivingEntity $$0) {
/* 215 */     if ($$0 != null) {
/* 216 */       if (level().isDay() && !$$0.isInWater()) {
/* 217 */         return false;
/*     */       }
/*     */       
/* 220 */       return true;
/*     */     } 
/* 222 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPushedByFluid() {
/* 227 */     return !isSwimming();
/*     */   }
/*     */   
/*     */   boolean wantsToSwim() {
/* 231 */     if (this.searchingForLand) {
/* 232 */       return true;
/*     */     }
/*     */     
/* 235 */     LivingEntity $$0 = getTarget();
/* 236 */     if ($$0 != null && $$0.isInWater()) {
/* 237 */       return true;
/*     */     }
/*     */     
/* 240 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void travel(Vec3 $$0) {
/* 245 */     if (isControlledByLocalInstance() && isInWater() && wantsToSwim()) {
/* 246 */       moveRelative(0.01F, $$0);
/* 247 */       move(MoverType.SELF, getDeltaMovement());
/*     */       
/* 249 */       setDeltaMovement(getDeltaMovement().scale(0.9D));
/*     */     } else {
/* 251 */       super.travel($$0);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateSwimming() {
/* 257 */     if (!(level()).isClientSide) {
/* 258 */       if (isEffectiveAi() && isInWater() && wantsToSwim()) {
/* 259 */         this.navigation = (PathNavigation)this.waterNavigation;
/* 260 */         setSwimming(true);
/*     */       } else {
/* 262 */         this.navigation = (PathNavigation)this.groundNavigation;
/* 263 */         setSwimming(false);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isVisuallySwimming() {
/* 270 */     return isSwimming();
/*     */   }
/*     */   
/*     */   protected boolean closeToNextPos() {
/* 274 */     Path $$0 = getNavigation().getPath();
/* 275 */     if ($$0 != null) {
/* 276 */       BlockPos $$1 = $$0.getTarget();
/* 277 */       if ($$1 != null) {
/* 278 */         double $$2 = distanceToSqr($$1.getX(), $$1.getY(), $$1.getZ());
/* 279 */         if ($$2 < 4.0D) {
/* 280 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/* 284 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void performRangedAttack(LivingEntity $$0, float $$1) {
/* 289 */     ThrownTrident $$2 = new ThrownTrident(level(), (LivingEntity)this, new ItemStack((ItemLike)Items.TRIDENT));
/*     */     
/* 291 */     double $$3 = $$0.getX() - getX();
/* 292 */     double $$4 = $$0.getY(0.3333333333333333D) - $$2.getY();
/* 293 */     double $$5 = $$0.getZ() - getZ();
/* 294 */     double $$6 = Math.sqrt($$3 * $$3 + $$5 * $$5);
/* 295 */     $$2.shoot($$3, $$4 + $$6 * 0.20000000298023224D, $$5, 1.6F, (14 - level().getDifficulty().getId() * 4));
/* 296 */     playSound(SoundEvents.DROWNED_SHOOT, 1.0F, 1.0F / (getRandom().nextFloat() * 0.4F + 0.8F));
/* 297 */     level().addFreshEntity((Entity)$$2);
/*     */   }
/*     */   
/*     */   public void setSearchingForLand(boolean $$0) {
/* 301 */     this.searchingForLand = $$0;
/*     */   }
/*     */   
/*     */   private static class DrownedTridentAttackGoal extends RangedAttackGoal {
/*     */     private final Drowned drowned;
/*     */     
/*     */     public DrownedTridentAttackGoal(RangedAttackMob $$0, double $$1, int $$2, float $$3) {
/* 308 */       super($$0, $$1, $$2, $$3);
/* 309 */       this.drowned = (Drowned)$$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canUse() {
/* 314 */       return (super.canUse() && this.drowned.getMainHandItem().is(Items.TRIDENT));
/*     */     }
/*     */ 
/*     */     
/*     */     public void start() {
/* 319 */       super.start();
/* 320 */       this.drowned.setAggressive(true);
/* 321 */       this.drowned.startUsingItem(InteractionHand.MAIN_HAND);
/*     */     }
/*     */ 
/*     */     
/*     */     public void stop() {
/* 326 */       super.stop();
/* 327 */       this.drowned.stopUsingItem();
/* 328 */       this.drowned.setAggressive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private static class DrownedSwimUpGoal extends Goal {
/*     */     private final Drowned drowned;
/*     */     private final double speedModifier;
/*     */     private final int seaLevel;
/*     */     private boolean stuck;
/*     */     
/*     */     public DrownedSwimUpGoal(Drowned $$0, double $$1, int $$2) {
/* 339 */       this.drowned = $$0;
/* 340 */       this.speedModifier = $$1;
/* 341 */       this.seaLevel = $$2;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canUse() {
/* 346 */       return (!this.drowned.level().isDay() && this.drowned.isInWater() && this.drowned.getY() < (this.seaLevel - 2));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canContinueToUse() {
/* 351 */       return (canUse() && !this.stuck);
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick() {
/* 356 */       if (this.drowned.getY() < (this.seaLevel - 1) && (this.drowned.getNavigation().isDone() || this.drowned.closeToNextPos())) {
/*     */         
/* 358 */         Vec3 $$0 = DefaultRandomPos.getPosTowards(this.drowned, 4, 8, new Vec3(this.drowned.getX(), (this.seaLevel - 1), this.drowned.getZ()), 1.5707963705062866D);
/*     */         
/* 360 */         if ($$0 == null) {
/* 361 */           this.stuck = true;
/*     */           
/*     */           return;
/*     */         } 
/* 365 */         this.drowned.getNavigation().moveTo($$0.x, $$0.y, $$0.z, this.speedModifier);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void start() {
/* 371 */       this.drowned.setSearchingForLand(true);
/* 372 */       this.stuck = false;
/*     */     }
/*     */ 
/*     */     
/*     */     public void stop() {
/* 377 */       this.drowned.setSearchingForLand(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private static class DrownedGoToBeachGoal
/*     */     extends MoveToBlockGoal {
/*     */     private final Drowned drowned;
/*     */     
/*     */     public DrownedGoToBeachGoal(Drowned $$0, double $$1) {
/* 386 */       super($$0, $$1, 8, 2);
/* 387 */       this.drowned = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canUse() {
/* 392 */       return (super.canUse() && !this.drowned.level().isDay() && this.drowned.isInWater() && this.drowned.getY() >= (this.drowned.level().getSeaLevel() - 3));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canContinueToUse() {
/* 397 */       return super.canContinueToUse();
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean isValidTarget(LevelReader $$0, BlockPos $$1) {
/* 402 */       BlockPos $$2 = $$1.above();
/* 403 */       if (!$$0.isEmptyBlock($$2) || !$$0.isEmptyBlock($$2.above())) {
/* 404 */         return false;
/*     */       }
/*     */       
/* 407 */       return $$0.getBlockState($$1).entityCanStandOn((BlockGetter)$$0, $$1, (Entity)this.drowned);
/*     */     }
/*     */ 
/*     */     
/*     */     public void start() {
/* 412 */       this.drowned.setSearchingForLand(false);
/* 413 */       this.drowned.navigation = (PathNavigation)this.drowned.groundNavigation;
/* 414 */       super.start();
/*     */     }
/*     */ 
/*     */     
/*     */     public void stop() {
/* 419 */       super.stop();
/*     */     }
/*     */   }
/*     */   
/*     */   private static class DrownedGoToWaterGoal extends Goal {
/*     */     private final PathfinderMob mob;
/*     */     private double wantedX;
/*     */     private double wantedY;
/*     */     private double wantedZ;
/*     */     private final double speedModifier;
/*     */     private final Level level;
/*     */     
/*     */     public DrownedGoToWaterGoal(PathfinderMob $$0, double $$1) {
/* 432 */       this.mob = $$0;
/* 433 */       this.speedModifier = $$1;
/* 434 */       this.level = $$0.level();
/* 435 */       setFlags(EnumSet.of(Goal.Flag.MOVE));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canUse() {
/* 440 */       if (!this.level.isDay()) {
/* 441 */         return false;
/*     */       }
/* 443 */       if (this.mob.isInWater()) {
/* 444 */         return false;
/*     */       }
/*     */       
/* 447 */       Vec3 $$0 = getWaterPos();
/* 448 */       if ($$0 == null) {
/* 449 */         return false;
/*     */       }
/* 451 */       this.wantedX = $$0.x;
/* 452 */       this.wantedY = $$0.y;
/* 453 */       this.wantedZ = $$0.z;
/* 454 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canContinueToUse() {
/* 459 */       return !this.mob.getNavigation().isDone();
/*     */     }
/*     */ 
/*     */     
/*     */     public void start() {
/* 464 */       this.mob.getNavigation().moveTo(this.wantedX, this.wantedY, this.wantedZ, this.speedModifier);
/*     */     }
/*     */     
/*     */     @Nullable
/*     */     private Vec3 getWaterPos() {
/* 469 */       RandomSource $$0 = this.mob.getRandom();
/* 470 */       BlockPos $$1 = this.mob.blockPosition();
/*     */       
/* 472 */       for (int $$2 = 0; $$2 < 10; $$2++) {
/* 473 */         BlockPos $$3 = $$1.offset($$0.nextInt(20) - 10, 2 - $$0.nextInt(8), $$0.nextInt(20) - 10);
/*     */         
/* 475 */         if (this.level.getBlockState($$3).is(Blocks.WATER)) {
/* 476 */           return Vec3.atBottomCenterOf((Vec3i)$$3);
/*     */         }
/*     */       } 
/* 479 */       return null;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class DrownedAttackGoal extends ZombieAttackGoal {
/*     */     private final Drowned drowned;
/*     */     
/*     */     public DrownedAttackGoal(Drowned $$0, double $$1, boolean $$2) {
/* 487 */       super($$0, $$1, $$2);
/* 488 */       this.drowned = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canUse() {
/* 493 */       return (super.canUse() && this.drowned.okTarget(this.drowned.getTarget()));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canContinueToUse() {
/* 498 */       return (super.canContinueToUse() && this.drowned.okTarget(this.drowned.getTarget()));
/*     */     }
/*     */   }
/*     */   
/*     */   private static class DrownedMoveControl extends MoveControl {
/*     */     private final Drowned drowned;
/*     */     
/*     */     public DrownedMoveControl(Drowned $$0) {
/* 506 */       super((Mob)$$0);
/* 507 */       this.drowned = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick() {
/* 512 */       LivingEntity $$0 = this.drowned.getTarget();
/* 513 */       if (this.drowned.wantsToSwim() && this.drowned.isInWater()) {
/* 514 */         if (($$0 != null && $$0.getY() > this.drowned.getY()) || this.drowned.searchingForLand)
/*     */         {
/* 516 */           this.drowned.setDeltaMovement(this.drowned.getDeltaMovement().add(0.0D, 0.002D, 0.0D));
/*     */         }
/*     */         
/* 519 */         if (this.operation != MoveControl.Operation.MOVE_TO || this.drowned.getNavigation().isDone()) {
/* 520 */           this.drowned.setSpeed(0.0F);
/*     */           
/*     */           return;
/*     */         } 
/* 524 */         double $$1 = this.wantedX - this.drowned.getX();
/* 525 */         double $$2 = this.wantedY - this.drowned.getY();
/* 526 */         double $$3 = this.wantedZ - this.drowned.getZ();
/* 527 */         double $$4 = Math.sqrt($$1 * $$1 + $$2 * $$2 + $$3 * $$3);
/* 528 */         $$2 /= $$4;
/*     */         
/* 530 */         float $$5 = (float)(Mth.atan2($$3, $$1) * 57.2957763671875D) - 90.0F;
/* 531 */         this.drowned.setYRot(rotlerp(this.drowned.getYRot(), $$5, 90.0F));
/* 532 */         this.drowned.yBodyRot = this.drowned.getYRot();
/*     */         
/* 534 */         float $$6 = (float)(this.speedModifier * this.drowned.getAttributeValue(Attributes.MOVEMENT_SPEED));
/* 535 */         float $$7 = Mth.lerp(0.125F, this.drowned.getSpeed(), $$6);
/* 536 */         this.drowned.setSpeed($$7);
/* 537 */         this.drowned.setDeltaMovement(this.drowned.getDeltaMovement().add($$7 * $$1 * 0.005D, $$7 * $$2 * 0.1D, $$7 * $$3 * 0.005D));
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */         
/* 543 */         if (!this.drowned.onGround()) {
/* 544 */           this.drowned.setDeltaMovement(this.drowned.getDeltaMovement().add(0.0D, -0.008D, 0.0D));
/*     */         }
/* 546 */         super.tick();
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\Drowned.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */