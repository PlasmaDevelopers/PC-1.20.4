/*     */ package net.minecraft.world.entity.animal;
/*     */ import java.util.function.Predicate;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.advancements.CriteriaTriggers;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Position;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.syncher.EntityDataAccessor;
/*     */ import net.minecraft.network.syncher.EntityDataSerializers;
/*     */ import net.minecraft.network.syncher.SynchedEntityData;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.stats.Stats;
/*     */ import net.minecraft.tags.FluidTags;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.entity.AgeableMob;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityDimensions;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.ExperienceOrb;
/*     */ import net.minecraft.world.entity.LightningBolt;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.MobSpawnType;
/*     */ import net.minecraft.world.entity.MobType;
/*     */ import net.minecraft.world.entity.MoverType;
/*     */ import net.minecraft.world.entity.PathfinderMob;
/*     */ import net.minecraft.world.entity.SpawnGroupData;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.entity.ai.control.MoveControl;
/*     */ import net.minecraft.world.entity.ai.goal.BreedGoal;
/*     */ import net.minecraft.world.entity.ai.goal.Goal;
/*     */ import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
/*     */ import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
/*     */ import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
/*     */ import net.minecraft.world.entity.ai.goal.TemptGoal;
/*     */ import net.minecraft.world.entity.ai.navigation.PathNavigation;
/*     */ import net.minecraft.world.entity.ai.util.DefaultRandomPos;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.item.crafting.Ingredient;
/*     */ import net.minecraft.world.level.BlockAndTintGetter;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.GameRules;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.ServerLevelAccessor;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.TurtleEggBlock;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.level.pathfinder.BlockPathTypes;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import org.joml.Vector3f;
/*     */ 
/*     */ public class Turtle extends Animal {
/*  69 */   private static final EntityDataAccessor<BlockPos> HOME_POS = SynchedEntityData.defineId(Turtle.class, EntityDataSerializers.BLOCK_POS);
/*  70 */   private static final EntityDataAccessor<Boolean> HAS_EGG = SynchedEntityData.defineId(Turtle.class, EntityDataSerializers.BOOLEAN);
/*  71 */   private static final EntityDataAccessor<Boolean> LAYING_EGG = SynchedEntityData.defineId(Turtle.class, EntityDataSerializers.BOOLEAN);
/*  72 */   private static final EntityDataAccessor<BlockPos> TRAVEL_POS = SynchedEntityData.defineId(Turtle.class, EntityDataSerializers.BLOCK_POS);
/*  73 */   private static final EntityDataAccessor<Boolean> GOING_HOME = SynchedEntityData.defineId(Turtle.class, EntityDataSerializers.BOOLEAN);
/*  74 */   private static final EntityDataAccessor<Boolean> TRAVELLING = SynchedEntityData.defineId(Turtle.class, EntityDataSerializers.BOOLEAN);
/*  75 */   public static final Ingredient FOOD_ITEMS = Ingredient.of(new ItemLike[] { (ItemLike)Blocks.SEAGRASS.asItem() }); int layEggCounter;
/*     */   public static final Predicate<LivingEntity> BABY_ON_LAND_SELECTOR;
/*     */   
/*     */   static {
/*  79 */     BABY_ON_LAND_SELECTOR = ($$0 -> ($$0.isBaby() && !$$0.isInWater()));
/*     */   }
/*     */   public Turtle(EntityType<? extends Turtle> $$0, Level $$1) {
/*  82 */     super((EntityType)$$0, $$1);
/*     */     
/*  84 */     setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
/*  85 */     setPathfindingMalus(BlockPathTypes.DOOR_IRON_CLOSED, -1.0F);
/*  86 */     setPathfindingMalus(BlockPathTypes.DOOR_WOOD_CLOSED, -1.0F);
/*  87 */     setPathfindingMalus(BlockPathTypes.DOOR_OPEN, -1.0F);
/*  88 */     this.moveControl = new TurtleMoveControl(this);
/*  89 */     setMaxUpStep(1.0F);
/*     */   }
/*     */   
/*     */   public void setHomePos(BlockPos $$0) {
/*  93 */     this.entityData.set(HOME_POS, $$0);
/*     */   }
/*     */   
/*     */   BlockPos getHomePos() {
/*  97 */     return (BlockPos)this.entityData.get(HOME_POS);
/*     */   }
/*     */   
/*     */   void setTravelPos(BlockPos $$0) {
/* 101 */     this.entityData.set(TRAVEL_POS, $$0);
/*     */   }
/*     */   
/*     */   BlockPos getTravelPos() {
/* 105 */     return (BlockPos)this.entityData.get(TRAVEL_POS);
/*     */   }
/*     */   
/*     */   public boolean hasEgg() {
/* 109 */     return ((Boolean)this.entityData.get(HAS_EGG)).booleanValue();
/*     */   }
/*     */   
/*     */   void setHasEgg(boolean $$0) {
/* 113 */     this.entityData.set(HAS_EGG, Boolean.valueOf($$0));
/*     */   }
/*     */   
/*     */   public boolean isLayingEgg() {
/* 117 */     return ((Boolean)this.entityData.get(LAYING_EGG)).booleanValue();
/*     */   }
/*     */   
/*     */   void setLayingEgg(boolean $$0) {
/* 121 */     this.layEggCounter = $$0 ? 1 : 0;
/* 122 */     this.entityData.set(LAYING_EGG, Boolean.valueOf($$0));
/*     */   }
/*     */   
/*     */   boolean isGoingHome() {
/* 126 */     return ((Boolean)this.entityData.get(GOING_HOME)).booleanValue();
/*     */   }
/*     */   
/*     */   void setGoingHome(boolean $$0) {
/* 130 */     this.entityData.set(GOING_HOME, Boolean.valueOf($$0));
/*     */   }
/*     */   
/*     */   boolean isTravelling() {
/* 134 */     return ((Boolean)this.entityData.get(TRAVELLING)).booleanValue();
/*     */   }
/*     */   
/*     */   void setTravelling(boolean $$0) {
/* 138 */     this.entityData.set(TRAVELLING, Boolean.valueOf($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {
/* 143 */     super.defineSynchedData();
/* 144 */     this.entityData.define(HOME_POS, BlockPos.ZERO);
/* 145 */     this.entityData.define(HAS_EGG, Boolean.valueOf(false));
/* 146 */     this.entityData.define(TRAVEL_POS, BlockPos.ZERO);
/* 147 */     this.entityData.define(GOING_HOME, Boolean.valueOf(false));
/* 148 */     this.entityData.define(TRAVELLING, Boolean.valueOf(false));
/* 149 */     this.entityData.define(LAYING_EGG, Boolean.valueOf(false));
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/* 154 */     super.addAdditionalSaveData($$0);
/*     */     
/* 156 */     $$0.putInt("HomePosX", getHomePos().getX());
/* 157 */     $$0.putInt("HomePosY", getHomePos().getY());
/* 158 */     $$0.putInt("HomePosZ", getHomePos().getZ());
/* 159 */     $$0.putBoolean("HasEgg", hasEgg());
/*     */     
/* 161 */     $$0.putInt("TravelPosX", getTravelPos().getX());
/* 162 */     $$0.putInt("TravelPosY", getTravelPos().getY());
/* 163 */     $$0.putInt("TravelPosZ", getTravelPos().getZ());
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/* 168 */     int $$1 = $$0.getInt("HomePosX");
/* 169 */     int $$2 = $$0.getInt("HomePosY");
/* 170 */     int $$3 = $$0.getInt("HomePosZ");
/* 171 */     setHomePos(new BlockPos($$1, $$2, $$3));
/*     */     
/* 173 */     super.readAdditionalSaveData($$0);
/* 174 */     setHasEgg($$0.getBoolean("HasEgg"));
/*     */     
/* 176 */     int $$4 = $$0.getInt("TravelPosX");
/* 177 */     int $$5 = $$0.getInt("TravelPosY");
/* 178 */     int $$6 = $$0.getInt("TravelPosZ");
/* 179 */     setTravelPos(new BlockPos($$4, $$5, $$6));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public SpawnGroupData finalizeSpawn(ServerLevelAccessor $$0, DifficultyInstance $$1, MobSpawnType $$2, @Nullable SpawnGroupData $$3, @Nullable CompoundTag $$4) {
/* 185 */     setHomePos(blockPosition());
/* 186 */     setTravelPos(BlockPos.ZERO);
/* 187 */     return super.finalizeSpawn($$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */   
/*     */   public static boolean checkTurtleSpawnRules(EntityType<Turtle> $$0, LevelAccessor $$1, MobSpawnType $$2, BlockPos $$3, RandomSource $$4) {
/* 191 */     return ($$3.getY() < $$1.getSeaLevel() + 4 && 
/* 192 */       TurtleEggBlock.onSand((BlockGetter)$$1, $$3) && 
/* 193 */       isBrightEnoughToSpawn((BlockAndTintGetter)$$1, $$3));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerGoals() {
/* 198 */     this.goalSelector.addGoal(0, (Goal)new TurtlePanicGoal(this, 1.2D));
/* 199 */     this.goalSelector.addGoal(1, (Goal)new TurtleBreedGoal(this, 1.0D));
/* 200 */     this.goalSelector.addGoal(1, (Goal)new TurtleLayEggGoal(this, 1.0D));
/* 201 */     this.goalSelector.addGoal(2, (Goal)new TemptGoal((PathfinderMob)this, 1.1D, FOOD_ITEMS, false));
/* 202 */     this.goalSelector.addGoal(3, (Goal)new TurtleGoToWaterGoal(this, 1.0D));
/* 203 */     this.goalSelector.addGoal(4, new TurtleGoHomeGoal(this, 1.0D));
/* 204 */     this.goalSelector.addGoal(7, new TurtleTravelGoal(this, 1.0D));
/* 205 */     this.goalSelector.addGoal(8, (Goal)new LookAtPlayerGoal((Mob)this, Player.class, 8.0F));
/* 206 */     this.goalSelector.addGoal(9, (Goal)new TurtleRandomStrollGoal(this, 1.0D, 100));
/*     */   }
/*     */   
/*     */   public static AttributeSupplier.Builder createAttributes() {
/* 210 */     return Mob.createMobAttributes()
/* 211 */       .add(Attributes.MAX_HEALTH, 30.0D)
/* 212 */       .add(Attributes.MOVEMENT_SPEED, 0.25D);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPushedByFluid() {
/* 217 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public MobType getMobType() {
/* 222 */     return MobType.WATER;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAmbientSoundInterval() {
/* 227 */     return 200;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected SoundEvent getAmbientSound() {
/* 233 */     if (!isInWater() && onGround() && !isBaby()) {
/* 234 */       return SoundEvents.TURTLE_AMBIENT_LAND;
/*     */     }
/*     */     
/* 237 */     return super.getAmbientSound();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void playSwimSound(float $$0) {
/* 242 */     super.playSwimSound($$0 * 1.5F);
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getSwimSound() {
/* 247 */     return SoundEvents.TURTLE_SWIM;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected SoundEvent getHurtSound(DamageSource $$0) {
/* 253 */     if (isBaby()) {
/* 254 */       return SoundEvents.TURTLE_HURT_BABY;
/*     */     }
/* 256 */     return SoundEvents.TURTLE_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected SoundEvent getDeathSound() {
/* 262 */     if (isBaby()) {
/* 263 */       return SoundEvents.TURTLE_DEATH_BABY;
/*     */     }
/* 265 */     return SoundEvents.TURTLE_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void playStepSound(BlockPos $$0, BlockState $$1) {
/* 270 */     SoundEvent $$2 = isBaby() ? SoundEvents.TURTLE_SHAMBLE_BABY : SoundEvents.TURTLE_SHAMBLE;
/*     */     
/* 272 */     playSound($$2, 0.15F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canFallInLove() {
/* 277 */     return (super.canFallInLove() && !hasEgg());
/*     */   }
/*     */ 
/*     */   
/*     */   protected float nextStep() {
/* 282 */     return this.moveDist + 0.15F;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getScale() {
/* 287 */     return isBaby() ? 0.3F : 1.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   protected PathNavigation createNavigation(Level $$0) {
/* 292 */     return (PathNavigation)new TurtlePathNavigation(this, $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public AgeableMob getBreedOffspring(ServerLevel $$0, AgeableMob $$1) {
/* 298 */     return (AgeableMob)EntityType.TURTLE.create((Level)$$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFood(ItemStack $$0) {
/* 303 */     return $$0.is(Blocks.SEAGRASS.asItem());
/*     */   }
/*     */ 
/*     */   
/*     */   public float getWalkTargetValue(BlockPos $$0, LevelReader $$1) {
/* 308 */     if (!isGoingHome() && $$1.getFluidState($$0).is(FluidTags.WATER)) {
/* 309 */       return 10.0F;
/*     */     }
/*     */     
/* 312 */     if (TurtleEggBlock.onSand((BlockGetter)$$1, $$0)) {
/* 313 */       return 10.0F;
/*     */     }
/*     */     
/* 316 */     return $$1.getPathfindingCostFromLightLevels($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void aiStep() {
/* 321 */     super.aiStep();
/*     */     
/* 323 */     if (isAlive() && isLayingEgg() && this.layEggCounter >= 1 && this.layEggCounter % 5 == 0) {
/* 324 */       BlockPos $$0 = blockPosition();
/* 325 */       if (TurtleEggBlock.onSand((BlockGetter)level(), $$0)) {
/* 326 */         level().levelEvent(2001, $$0, Block.getId(level().getBlockState($$0.below())));
/* 327 */         gameEvent(GameEvent.ENTITY_ACTION);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void ageBoundaryReached() {
/* 334 */     super.ageBoundaryReached();
/*     */ 
/*     */     
/* 337 */     if (!isBaby() && level().getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
/* 338 */       spawnAtLocation((ItemLike)Items.SCUTE, 1);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void travel(Vec3 $$0) {
/* 344 */     if (isControlledByLocalInstance() && isInWater()) {
/* 345 */       moveRelative(0.1F, $$0);
/* 346 */       move(MoverType.SELF, getDeltaMovement());
/*     */       
/* 348 */       setDeltaMovement(getDeltaMovement().scale(0.9D));
/* 349 */       if (getTarget() == null && (!isGoingHome() || !getHomePos().closerToCenterThan((Position)position(), 20.0D))) {
/* 350 */         setDeltaMovement(getDeltaMovement().add(0.0D, -0.005D, 0.0D));
/*     */       }
/*     */     } else {
/* 353 */       super.travel($$0);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canBeLeashed(Player $$0) {
/* 359 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void thunderHit(ServerLevel $$0, LightningBolt $$1) {
/* 364 */     hurt(damageSources().lightningBolt(), Float.MAX_VALUE);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Vector3f getPassengerAttachmentPoint(Entity $$0, EntityDimensions $$1, float $$2) {
/* 369 */     return new Vector3f(0.0F, $$1.height + (isBaby() ? 0.0F : 0.15625F) * $$2, -0.25F * $$2);
/*     */   }
/*     */   
/*     */   private static class TurtlePanicGoal extends PanicGoal {
/*     */     TurtlePanicGoal(Turtle $$0, double $$1) {
/* 374 */       super((PathfinderMob)$$0, $$1);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canUse() {
/* 379 */       if (!shouldPanic()) {
/* 380 */         return false;
/*     */       }
/*     */       
/* 383 */       BlockPos $$0 = lookForWater((BlockGetter)this.mob.level(), (Entity)this.mob, 7);
/* 384 */       if ($$0 != null) {
/* 385 */         this.posX = $$0.getX();
/* 386 */         this.posY = $$0.getY();
/* 387 */         this.posZ = $$0.getZ();
/*     */         
/* 389 */         return true;
/*     */       } 
/*     */       
/* 392 */       return findRandomPosition();
/*     */     }
/*     */   }
/*     */   
/*     */   private static class TurtleTravelGoal extends Goal {
/*     */     private final Turtle turtle;
/*     */     private final double speedModifier;
/*     */     private boolean stuck;
/*     */     
/*     */     TurtleTravelGoal(Turtle $$0, double $$1) {
/* 402 */       this.turtle = $$0;
/* 403 */       this.speedModifier = $$1;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canUse() {
/* 408 */       return (!this.turtle.isGoingHome() && !this.turtle.hasEgg() && this.turtle.isInWater());
/*     */     }
/*     */ 
/*     */     
/*     */     public void start() {
/* 413 */       int $$0 = 512;
/* 414 */       int $$1 = 4;
/* 415 */       RandomSource $$2 = this.turtle.random;
/* 416 */       int $$3 = $$2.nextInt(1025) - 512;
/* 417 */       int $$4 = $$2.nextInt(9) - 4;
/* 418 */       int $$5 = $$2.nextInt(1025) - 512;
/*     */       
/* 420 */       if ($$4 + this.turtle.getY() > (this.turtle.level().getSeaLevel() - 1)) {
/* 421 */         $$4 = 0;
/*     */       }
/* 423 */       BlockPos $$6 = BlockPos.containing($$3 + this.turtle.getX(), $$4 + this.turtle.getY(), $$5 + this.turtle.getZ());
/* 424 */       this.turtle.setTravelPos($$6);
/* 425 */       this.turtle.setTravelling(true);
/* 426 */       this.stuck = false;
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick() {
/* 431 */       if (this.turtle.getNavigation().isDone()) {
/* 432 */         Vec3 $$0 = Vec3.atBottomCenterOf((Vec3i)this.turtle.getTravelPos());
/* 433 */         Vec3 $$1 = DefaultRandomPos.getPosTowards((PathfinderMob)this.turtle, 16, 3, $$0, 0.3141592741012573D);
/* 434 */         if ($$1 == null) {
/* 435 */           $$1 = DefaultRandomPos.getPosTowards((PathfinderMob)this.turtle, 8, 7, $$0, 1.5707963705062866D);
/*     */         }
/*     */ 
/*     */         
/* 439 */         if ($$1 != null) {
/* 440 */           int $$2 = Mth.floor($$1.x);
/* 441 */           int $$3 = Mth.floor($$1.z);
/* 442 */           int $$4 = 34;
/* 443 */           if (!this.turtle.level().hasChunksAt($$2 - 34, $$3 - 34, $$2 + 34, $$3 + 34)) {
/* 444 */             $$1 = null;
/*     */           }
/*     */         } 
/*     */         
/* 448 */         if ($$1 == null) {
/* 449 */           this.stuck = true;
/*     */           
/*     */           return;
/*     */         } 
/* 453 */         this.turtle.getNavigation().moveTo($$1.x, $$1.y, $$1.z, this.speedModifier);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canContinueToUse() {
/* 459 */       return (!this.turtle.getNavigation().isDone() && !this.stuck && !this.turtle.isGoingHome() && !this.turtle.isInLove() && !this.turtle.hasEgg());
/*     */     }
/*     */ 
/*     */     
/*     */     public void stop() {
/* 464 */       this.turtle.setTravelling(false);
/* 465 */       super.stop();
/*     */     }
/*     */   }
/*     */   
/*     */   private static class TurtleGoHomeGoal extends Goal {
/*     */     private final Turtle turtle;
/*     */     private final double speedModifier;
/*     */     private boolean stuck;
/*     */     private int closeToHomeTryTicks;
/*     */     private static final int GIVE_UP_TICKS = 600;
/*     */     
/*     */     TurtleGoHomeGoal(Turtle $$0, double $$1) {
/* 477 */       this.turtle = $$0;
/* 478 */       this.speedModifier = $$1;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canUse() {
/* 483 */       if (this.turtle.isBaby()) {
/* 484 */         return false;
/*     */       }
/*     */       
/* 487 */       if (this.turtle.hasEgg()) {
/* 488 */         return true;
/*     */       }
/*     */       
/* 491 */       if (this.turtle.getRandom().nextInt(reducedTickDelay(700)) != 0) {
/* 492 */         return false;
/*     */       }
/*     */       
/* 495 */       return !this.turtle.getHomePos().closerToCenterThan((Position)this.turtle.position(), 64.0D);
/*     */     }
/*     */ 
/*     */     
/*     */     public void start() {
/* 500 */       this.turtle.setGoingHome(true);
/* 501 */       this.stuck = false;
/* 502 */       this.closeToHomeTryTicks = 0;
/*     */     }
/*     */ 
/*     */     
/*     */     public void stop() {
/* 507 */       this.turtle.setGoingHome(false);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canContinueToUse() {
/* 512 */       return (!this.turtle.getHomePos().closerToCenterThan((Position)this.turtle.position(), 7.0D) && !this.stuck && this.closeToHomeTryTicks <= adjustedTickDelay(600));
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick() {
/* 517 */       BlockPos $$0 = this.turtle.getHomePos();
/* 518 */       boolean $$1 = $$0.closerToCenterThan((Position)this.turtle.position(), 16.0D);
/* 519 */       if ($$1) {
/* 520 */         this.closeToHomeTryTicks++;
/*     */       }
/*     */       
/* 523 */       if (this.turtle.getNavigation().isDone()) {
/* 524 */         Vec3 $$2 = Vec3.atBottomCenterOf((Vec3i)$$0);
/* 525 */         Vec3 $$3 = DefaultRandomPos.getPosTowards((PathfinderMob)this.turtle, 16, 3, $$2, 0.3141592741012573D);
/* 526 */         if ($$3 == null) {
/* 527 */           $$3 = DefaultRandomPos.getPosTowards((PathfinderMob)this.turtle, 8, 7, $$2, 1.5707963705062866D);
/*     */         }
/*     */         
/* 530 */         if ($$3 != null && !$$1 && !this.turtle.level().getBlockState(BlockPos.containing((Position)$$3)).is(Blocks.WATER))
/*     */         {
/* 532 */           $$3 = DefaultRandomPos.getPosTowards((PathfinderMob)this.turtle, 16, 5, $$2, 1.5707963705062866D);
/*     */         }
/*     */         
/* 535 */         if ($$3 == null) {
/* 536 */           this.stuck = true;
/*     */           
/*     */           return;
/*     */         } 
/* 540 */         this.turtle.getNavigation().moveTo($$3.x, $$3.y, $$3.z, this.speedModifier);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private static class TurtleBreedGoal extends BreedGoal {
/*     */     private final Turtle turtle;
/*     */     
/*     */     TurtleBreedGoal(Turtle $$0, double $$1) {
/* 549 */       super($$0, $$1);
/* 550 */       this.turtle = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canUse() {
/* 555 */       return (super.canUse() && !this.turtle.hasEgg());
/*     */     }
/*     */ 
/*     */     
/*     */     protected void breed() {
/* 560 */       ServerPlayer $$0 = this.animal.getLoveCause();
/* 561 */       if ($$0 == null && this.partner.getLoveCause() != null) {
/* 562 */         $$0 = this.partner.getLoveCause();
/*     */       }
/*     */       
/* 565 */       if ($$0 != null) {
/* 566 */         $$0.awardStat(Stats.ANIMALS_BRED);
/* 567 */         CriteriaTriggers.BRED_ANIMALS.trigger($$0, this.animal, this.partner, null);
/*     */       } 
/*     */       
/* 570 */       this.turtle.setHasEgg(true);
/* 571 */       this.animal.setAge(6000);
/* 572 */       this.partner.setAge(6000);
/* 573 */       this.animal.resetLove();
/* 574 */       this.partner.resetLove();
/*     */       
/* 576 */       RandomSource $$1 = this.animal.getRandom();
/* 577 */       if (this.level.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT))
/* 578 */         this.level.addFreshEntity((Entity)new ExperienceOrb(this.level, this.animal.getX(), this.animal.getY(), this.animal.getZ(), $$1.nextInt(7) + 1)); 
/*     */     }
/*     */   }
/*     */   
/*     */   private static class TurtleLayEggGoal
/*     */     extends MoveToBlockGoal {
/*     */     private final Turtle turtle;
/*     */     
/*     */     TurtleLayEggGoal(Turtle $$0, double $$1) {
/* 587 */       super((PathfinderMob)$$0, $$1, 16);
/* 588 */       this.turtle = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canUse() {
/* 593 */       if (this.turtle.hasEgg() && this.turtle.getHomePos().closerToCenterThan((Position)this.turtle.position(), 9.0D)) {
/* 594 */         return super.canUse();
/*     */       }
/* 596 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canContinueToUse() {
/* 601 */       return (super.canContinueToUse() && this.turtle.hasEgg() && this.turtle.getHomePos().closerToCenterThan((Position)this.turtle.position(), 9.0D));
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick() {
/* 606 */       super.tick();
/*     */       
/* 608 */       BlockPos $$0 = this.turtle.blockPosition();
/* 609 */       if (!this.turtle.isInWater() && isReachedTarget()) {
/* 610 */         if (this.turtle.layEggCounter < 1) {
/* 611 */           this.turtle.setLayingEgg(true);
/* 612 */         } else if (this.turtle.layEggCounter > adjustedTickDelay(200)) {
/* 613 */           Level $$1 = this.turtle.level();
/* 614 */           $$1.playSound(null, $$0, SoundEvents.TURTLE_LAY_EGG, SoundSource.BLOCKS, 0.3F, 0.9F + $$1.random.nextFloat() * 0.2F);
/* 615 */           BlockPos $$2 = this.blockPos.above();
/* 616 */           BlockState $$3 = (BlockState)Blocks.TURTLE_EGG.defaultBlockState().setValue((Property)TurtleEggBlock.EGGS, Integer.valueOf(this.turtle.random.nextInt(4) + 1));
/* 617 */           $$1.setBlock($$2, $$3, 3);
/* 618 */           $$1.gameEvent(GameEvent.BLOCK_PLACE, $$2, GameEvent.Context.of((Entity)this.turtle, $$3));
/* 619 */           this.turtle.setHasEgg(false);
/* 620 */           this.turtle.setLayingEgg(false);
/* 621 */           this.turtle.setInLoveTime(600);
/*     */         } 
/* 623 */         if (this.turtle.isLayingEgg()) {
/* 624 */           this.turtle.layEggCounter++;
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean isValidTarget(LevelReader $$0, BlockPos $$1) {
/* 631 */       if (!$$0.isEmptyBlock($$1.above())) {
/* 632 */         return false;
/*     */       }
/*     */       
/* 635 */       return TurtleEggBlock.isSand((BlockGetter)$$0, $$1);
/*     */     }
/*     */   }
/*     */   
/*     */   private static class TurtleRandomStrollGoal extends RandomStrollGoal {
/*     */     private final Turtle turtle;
/*     */     
/*     */     TurtleRandomStrollGoal(Turtle $$0, double $$1, int $$2) {
/* 643 */       super((PathfinderMob)$$0, $$1, $$2);
/* 644 */       this.turtle = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canUse() {
/* 649 */       if (!this.mob.isInWater() && !this.turtle.isGoingHome() && !this.turtle.hasEgg()) {
/* 650 */         return super.canUse();
/*     */       }
/*     */       
/* 653 */       return false;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class TurtleGoToWaterGoal
/*     */     extends MoveToBlockGoal {
/*     */     private static final int GIVE_UP_TICKS = 1200;
/*     */     private final Turtle turtle;
/*     */     
/*     */     TurtleGoToWaterGoal(Turtle $$0, double $$1) {
/* 663 */       super((PathfinderMob)$$0, $$0.isBaby() ? 2.0D : $$1, 24);
/* 664 */       this.turtle = $$0;
/* 665 */       this.verticalSearchStart = -1;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canContinueToUse() {
/* 670 */       return (!this.turtle.isInWater() && this.tryTicks <= 1200 && isValidTarget((LevelReader)this.turtle.level(), this.blockPos));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canUse() {
/* 675 */       if (this.turtle.isBaby() && !this.turtle.isInWater()) {
/* 676 */         return super.canUse();
/*     */       }
/*     */       
/* 679 */       if (!this.turtle.isGoingHome() && !this.turtle.isInWater() && !this.turtle.hasEgg()) {
/* 680 */         return super.canUse();
/*     */       }
/*     */       
/* 683 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean shouldRecalculatePath() {
/* 688 */       return (this.tryTicks % 160 == 0);
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean isValidTarget(LevelReader $$0, BlockPos $$1) {
/* 693 */       return $$0.getBlockState($$1).is(Blocks.WATER);
/*     */     }
/*     */   }
/*     */   
/*     */   private static class TurtleMoveControl extends MoveControl {
/*     */     private final Turtle turtle;
/*     */     
/*     */     TurtleMoveControl(Turtle $$0) {
/* 701 */       super((Mob)$$0);
/* 702 */       this.turtle = $$0;
/*     */     }
/*     */     
/*     */     private void updateSpeed() {
/* 706 */       if (this.turtle.isInWater()) {
/*     */         
/* 708 */         this.turtle.setDeltaMovement(this.turtle.getDeltaMovement().add(0.0D, 0.005D, 0.0D));
/*     */         
/* 710 */         if (!this.turtle.getHomePos().closerToCenterThan((Position)this.turtle.position(), 16.0D)) {
/* 711 */           this.turtle.setSpeed(Math.max(this.turtle.getSpeed() / 2.0F, 0.08F));
/*     */         }
/*     */         
/* 714 */         if (this.turtle.isBaby()) {
/* 715 */           this.turtle.setSpeed(Math.max(this.turtle.getSpeed() / 3.0F, 0.06F));
/*     */         }
/* 717 */       } else if (this.turtle.onGround()) {
/* 718 */         this.turtle.setSpeed(Math.max(this.turtle.getSpeed() / 2.0F, 0.06F));
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick() {
/* 724 */       updateSpeed();
/*     */       
/* 726 */       if (this.operation != MoveControl.Operation.MOVE_TO || this.turtle.getNavigation().isDone()) {
/* 727 */         this.turtle.setSpeed(0.0F);
/*     */         
/*     */         return;
/*     */       } 
/* 731 */       double $$0 = this.wantedX - this.turtle.getX();
/* 732 */       double $$1 = this.wantedY - this.turtle.getY();
/* 733 */       double $$2 = this.wantedZ - this.turtle.getZ();
/* 734 */       double $$3 = Math.sqrt($$0 * $$0 + $$1 * $$1 + $$2 * $$2);
/* 735 */       if ($$3 < 9.999999747378752E-6D) {
/* 736 */         this.mob.setSpeed(0.0F);
/*     */         
/*     */         return;
/*     */       } 
/* 740 */       $$1 /= $$3;
/*     */       
/* 742 */       float $$4 = (float)(Mth.atan2($$2, $$0) * 57.2957763671875D) - 90.0F;
/* 743 */       this.turtle.setYRot(rotlerp(this.turtle.getYRot(), $$4, 90.0F));
/* 744 */       this.turtle.yBodyRot = this.turtle.getYRot();
/*     */       
/* 746 */       float $$5 = (float)(this.speedModifier * this.turtle.getAttributeValue(Attributes.MOVEMENT_SPEED));
/* 747 */       this.turtle.setSpeed(Mth.lerp(0.125F, this.turtle.getSpeed(), $$5));
/*     */       
/* 749 */       this.turtle.setDeltaMovement(this.turtle.getDeltaMovement().add(0.0D, this.turtle.getSpeed() * $$1 * 0.1D, 0.0D));
/*     */     }
/*     */   }
/*     */   
/*     */   private static class TurtlePathNavigation extends AmphibiousPathNavigation {
/*     */     TurtlePathNavigation(Turtle $$0, Level $$1) {
/* 755 */       super((Mob)$$0, $$1);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isStableDestination(BlockPos $$0) {
/* 760 */       Mob mob = this.mob; if (mob instanceof Turtle) { Turtle $$1 = (Turtle)mob;
/* 761 */         if ($$1.isTravelling()) {
/* 762 */           return this.level.getBlockState($$0).is(Blocks.WATER);
/*     */         } }
/*     */ 
/*     */       
/* 766 */       return !this.level.getBlockState($$0.below()).isAir();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\animal\Turtle.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */