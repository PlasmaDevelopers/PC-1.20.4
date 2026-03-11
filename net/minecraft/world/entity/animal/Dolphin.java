/*     */ package net.minecraft.world.entity.animal;
/*     */ import java.util.EnumSet;
/*     */ import java.util.List;
/*     */ import java.util.function.Predicate;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Position;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.syncher.EntityDataAccessor;
/*     */ import net.minecraft.network.syncher.EntityDataSerializers;
/*     */ import net.minecraft.network.syncher.SynchedEntityData;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.tags.FluidTags;
/*     */ import net.minecraft.tags.ItemTags;
/*     */ import net.minecraft.tags.StructureTags;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.effect.MobEffectInstance;
/*     */ import net.minecraft.world.effect.MobEffects;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityDimensions;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.EquipmentSlot;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.MobSpawnType;
/*     */ import net.minecraft.world.entity.MoverType;
/*     */ import net.minecraft.world.entity.SpawnGroupData;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.entity.ai.control.LookControl;
/*     */ import net.minecraft.world.entity.ai.control.MoveControl;
/*     */ import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
/*     */ import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
/*     */ import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
/*     */ import net.minecraft.world.entity.ai.goal.BreathAirGoal;
/*     */ import net.minecraft.world.entity.ai.goal.DolphinJumpGoal;
/*     */ import net.minecraft.world.entity.ai.goal.FollowBoatGoal;
/*     */ import net.minecraft.world.entity.ai.goal.Goal;
/*     */ import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
/*     */ import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
/*     */ import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
/*     */ import net.minecraft.world.entity.ai.goal.TryFindWaterGoal;
/*     */ import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
/*     */ import net.minecraft.world.entity.ai.navigation.PathNavigation;
/*     */ import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
/*     */ import net.minecraft.world.entity.ai.targeting.TargetingConditions;
/*     */ import net.minecraft.world.entity.ai.util.DefaultRandomPos;
/*     */ import net.minecraft.world.entity.item.ItemEntity;
/*     */ import net.minecraft.world.entity.monster.Guardian;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.ServerLevelAccessor;
/*     */ import net.minecraft.world.level.pathfinder.PathComputationType;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class Dolphin extends WaterAnimal {
/*  68 */   private static final EntityDataAccessor<BlockPos> TREASURE_POS = SynchedEntityData.defineId(Dolphin.class, EntityDataSerializers.BLOCK_POS);
/*  69 */   private static final EntityDataAccessor<Boolean> GOT_FISH = SynchedEntityData.defineId(Dolphin.class, EntityDataSerializers.BOOLEAN);
/*  70 */   private static final EntityDataAccessor<Integer> MOISTNESS_LEVEL = SynchedEntityData.defineId(Dolphin.class, EntityDataSerializers.INT);
/*     */   
/*  72 */   static final TargetingConditions SWIM_WITH_PLAYER_TARGETING = TargetingConditions.forNonCombat().range(10.0D).ignoreLineOfSight(); public static final int TOTAL_AIR_SUPPLY = 4800; private static final int TOTAL_MOISTNESS_LEVEL = 2400;
/*     */   public static final Predicate<ItemEntity> ALLOWED_ITEMS;
/*     */   
/*     */   static {
/*  76 */     ALLOWED_ITEMS = ($$0 -> (!$$0.hasPickUpDelay() && $$0.isAlive() && $$0.isInWater()));
/*     */   }
/*     */   public Dolphin(EntityType<? extends Dolphin> $$0, Level $$1) {
/*  79 */     super((EntityType)$$0, $$1);
/*     */     
/*  81 */     this.moveControl = (MoveControl)new SmoothSwimmingMoveControl((Mob)this, 85, 10, 0.02F, 0.1F, true);
/*  82 */     this.lookControl = (LookControl)new SmoothSwimmingLookControl((Mob)this, 10);
/*     */     
/*  84 */     setCanPickUpLoot(true);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public SpawnGroupData finalizeSpawn(ServerLevelAccessor $$0, DifficultyInstance $$1, MobSpawnType $$2, @Nullable SpawnGroupData $$3, @Nullable CompoundTag $$4) {
/*  90 */     setAirSupply(getMaxAirSupply());
/*  91 */     setXRot(0.0F);
/*     */     
/*  93 */     return super.finalizeSpawn($$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void handleAirSupply(int $$0) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTreasurePos(BlockPos $$0) {
/* 103 */     this.entityData.set(TREASURE_POS, $$0);
/*     */   }
/*     */   
/*     */   public BlockPos getTreasurePos() {
/* 107 */     return (BlockPos)this.entityData.get(TREASURE_POS);
/*     */   }
/*     */   
/*     */   public boolean gotFish() {
/* 111 */     return ((Boolean)this.entityData.get(GOT_FISH)).booleanValue();
/*     */   }
/*     */   
/*     */   public void setGotFish(boolean $$0) {
/* 115 */     this.entityData.set(GOT_FISH, Boolean.valueOf($$0));
/*     */   }
/*     */   
/*     */   public int getMoistnessLevel() {
/* 119 */     return ((Integer)this.entityData.get(MOISTNESS_LEVEL)).intValue();
/*     */   }
/*     */   
/*     */   public void setMoisntessLevel(int $$0) {
/* 123 */     this.entityData.set(MOISTNESS_LEVEL, Integer.valueOf($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {
/* 128 */     super.defineSynchedData();
/* 129 */     this.entityData.define(TREASURE_POS, BlockPos.ZERO);
/* 130 */     this.entityData.define(GOT_FISH, Boolean.valueOf(false));
/* 131 */     this.entityData.define(MOISTNESS_LEVEL, Integer.valueOf(2400));
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/* 136 */     super.addAdditionalSaveData($$0);
/*     */     
/* 138 */     $$0.putInt("TreasurePosX", getTreasurePos().getX());
/* 139 */     $$0.putInt("TreasurePosY", getTreasurePos().getY());
/* 140 */     $$0.putInt("TreasurePosZ", getTreasurePos().getZ());
/* 141 */     $$0.putBoolean("GotFish", gotFish());
/* 142 */     $$0.putInt("Moistness", getMoistnessLevel());
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/* 147 */     int $$1 = $$0.getInt("TreasurePosX");
/* 148 */     int $$2 = $$0.getInt("TreasurePosY");
/* 149 */     int $$3 = $$0.getInt("TreasurePosZ");
/* 150 */     setTreasurePos(new BlockPos($$1, $$2, $$3));
/*     */     
/* 152 */     super.readAdditionalSaveData($$0);
/* 153 */     setGotFish($$0.getBoolean("GotFish"));
/* 154 */     setMoisntessLevel($$0.getInt("Moistness"));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerGoals() {
/* 159 */     this.goalSelector.addGoal(0, (Goal)new BreathAirGoal(this));
/* 160 */     this.goalSelector.addGoal(0, (Goal)new TryFindWaterGoal(this));
/* 161 */     this.goalSelector.addGoal(1, new DolphinSwimToTreasureGoal(this));
/* 162 */     this.goalSelector.addGoal(2, new DolphinSwimWithPlayerGoal(this, 4.0D));
/* 163 */     this.goalSelector.addGoal(4, (Goal)new RandomSwimmingGoal(this, 1.0D, 10));
/* 164 */     this.goalSelector.addGoal(4, (Goal)new RandomLookAroundGoal((Mob)this));
/* 165 */     this.goalSelector.addGoal(5, (Goal)new LookAtPlayerGoal((Mob)this, Player.class, 6.0F));
/* 166 */     this.goalSelector.addGoal(5, (Goal)new DolphinJumpGoal(this, 10));
/* 167 */     this.goalSelector.addGoal(6, (Goal)new MeleeAttackGoal(this, 1.2000000476837158D, true));
/* 168 */     this.goalSelector.addGoal(8, new PlayWithItemsGoal());
/* 169 */     this.goalSelector.addGoal(8, (Goal)new FollowBoatGoal(this));
/* 170 */     this.goalSelector.addGoal(9, (Goal)new AvoidEntityGoal(this, Guardian.class, 8.0F, 1.0D, 1.0D));
/*     */     
/* 172 */     this.targetSelector.addGoal(1, (Goal)(new HurtByTargetGoal(this, new Class[] { Guardian.class })).setAlertOthers(new Class[0]));
/*     */   }
/*     */   
/*     */   public static AttributeSupplier.Builder createAttributes() {
/* 176 */     return Mob.createMobAttributes()
/* 177 */       .add(Attributes.MAX_HEALTH, 10.0D)
/* 178 */       .add(Attributes.MOVEMENT_SPEED, 1.2000000476837158D)
/* 179 */       .add(Attributes.ATTACK_DAMAGE, 3.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   protected PathNavigation createNavigation(Level $$0) {
/* 184 */     return (PathNavigation)new WaterBoundPathNavigation((Mob)this, $$0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean doHurtTarget(Entity $$0) {
/* 190 */     boolean $$1 = $$0.hurt(damageSources().mobAttack((LivingEntity)this), (int)getAttributeValue(Attributes.ATTACK_DAMAGE));
/* 191 */     if ($$1) {
/* 192 */       doEnchantDamageEffects((LivingEntity)this, $$0);
/* 193 */       playSound(SoundEvents.DOLPHIN_ATTACK, 1.0F, 1.0F);
/*     */     } 
/* 195 */     return $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxAirSupply() {
/* 200 */     return 4800;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int increaseAirSupply(int $$0) {
/* 205 */     return getMaxAirSupply();
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getStandingEyeHeight(Pose $$0, EntityDimensions $$1) {
/* 210 */     return 0.3F;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxHeadXRot() {
/* 215 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxHeadYRot() {
/* 220 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canRide(Entity $$0) {
/* 225 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canTakeItem(ItemStack $$0) {
/* 230 */     EquipmentSlot $$1 = Mob.getEquipmentSlotForItem($$0);
/* 231 */     if (!getItemBySlot($$1).isEmpty()) {
/* 232 */       return false;
/*     */     }
/* 234 */     return ($$1 == EquipmentSlot.MAINHAND && super.canTakeItem($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void pickUpItem(ItemEntity $$0) {
/* 239 */     if (getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()) {
/* 240 */       ItemStack $$1 = $$0.getItem();
/* 241 */       if (canHoldItem($$1)) {
/* 242 */         onItemPickup($$0);
/* 243 */         setItemSlot(EquipmentSlot.MAINHAND, $$1);
/* 244 */         setGuaranteedDrop(EquipmentSlot.MAINHAND);
/* 245 */         take((Entity)$$0, $$1.getCount());
/* 246 */         $$0.discard();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 253 */     super.tick();
/*     */     
/* 255 */     if (isNoAi()) {
/*     */       
/* 257 */       setAirSupply(getMaxAirSupply());
/*     */       
/*     */       return;
/*     */     } 
/* 261 */     if (isInWaterRainOrBubble()) {
/* 262 */       setMoisntessLevel(2400);
/*     */     } else {
/* 264 */       setMoisntessLevel(getMoistnessLevel() - 1);
/*     */       
/* 266 */       if (getMoistnessLevel() <= 0) {
/* 267 */         hurt(damageSources().dryOut(), 1.0F);
/*     */       }
/*     */       
/* 270 */       if (onGround()) {
/* 271 */         setDeltaMovement(getDeltaMovement().add(((this.random
/* 272 */               .nextFloat() * 2.0F - 1.0F) * 0.2F), 0.5D, ((this.random
/*     */               
/* 274 */               .nextFloat() * 2.0F - 1.0F) * 0.2F)));
/*     */         
/* 276 */         setYRot(this.random.nextFloat() * 360.0F);
/* 277 */         setOnGround(false);
/* 278 */         this.hasImpulse = true;
/*     */       } 
/*     */     } 
/*     */     
/* 282 */     if ((level()).isClientSide && isInWater() && getDeltaMovement().lengthSqr() > 0.03D) {
/* 283 */       Vec3 $$0 = getViewVector(0.0F);
/* 284 */       float $$1 = Mth.cos(getYRot() * 0.017453292F) * 0.3F;
/* 285 */       float $$2 = Mth.sin(getYRot() * 0.017453292F) * 0.3F;
/* 286 */       float $$3 = 1.2F - this.random.nextFloat() * 0.7F;
/* 287 */       for (int $$4 = 0; $$4 < 2; $$4++) {
/* 288 */         level().addParticle((ParticleOptions)ParticleTypes.DOLPHIN, getX() - $$0.x * $$3 + $$1, getY() - $$0.y, getZ() - $$0.z * $$3 + $$2, 0.0D, 0.0D, 0.0D);
/* 289 */         level().addParticle((ParticleOptions)ParticleTypes.DOLPHIN, getX() - $$0.x * $$3 - $$1, getY() - $$0.y, getZ() - $$0.z * $$3 - $$2, 0.0D, 0.0D, 0.0D);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleEntityEvent(byte $$0) {
/* 296 */     if ($$0 == 38) {
/* 297 */       addParticlesAroundSelf((ParticleOptions)ParticleTypes.HAPPY_VILLAGER);
/*     */     } else {
/* 299 */       super.handleEntityEvent($$0);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void addParticlesAroundSelf(ParticleOptions $$0) {
/* 304 */     for (int $$1 = 0; $$1 < 7; $$1++) {
/* 305 */       double $$2 = this.random.nextGaussian() * 0.01D;
/* 306 */       double $$3 = this.random.nextGaussian() * 0.01D;
/* 307 */       double $$4 = this.random.nextGaussian() * 0.01D;
/* 308 */       level().addParticle($$0, getRandomX(1.0D), getRandomY() + 0.2D, getRandomZ(1.0D), $$2, $$3, $$4);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected InteractionResult mobInteract(Player $$0, InteractionHand $$1) {
/* 314 */     ItemStack $$2 = $$0.getItemInHand($$1);
/*     */     
/* 316 */     if (!$$2.isEmpty() && $$2.is(ItemTags.FISHES)) {
/* 317 */       if (!(level()).isClientSide) {
/* 318 */         playSound(SoundEvents.DOLPHIN_EAT, 1.0F, 1.0F);
/*     */       }
/*     */       
/* 321 */       setGotFish(true);
/*     */       
/* 323 */       if (!($$0.getAbilities()).instabuild) {
/* 324 */         $$2.shrink(1);
/*     */       }
/*     */       
/* 327 */       return InteractionResult.sidedSuccess((level()).isClientSide);
/*     */     } 
/*     */     
/* 330 */     return super.mobInteract($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource $$0) {
/* 335 */     return SoundEvents.DOLPHIN_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected SoundEvent getDeathSound() {
/* 341 */     return SoundEvents.DOLPHIN_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected SoundEvent getAmbientSound() {
/* 347 */     return isInWater() ? SoundEvents.DOLPHIN_AMBIENT_WATER : SoundEvents.DOLPHIN_AMBIENT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getSwimSplashSound() {
/* 352 */     return SoundEvents.DOLPHIN_SPLASH;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getSwimSound() {
/* 357 */     return SoundEvents.DOLPHIN_SWIM;
/*     */   }
/*     */   
/*     */   protected boolean closeToNextPos() {
/* 361 */     BlockPos $$0 = getNavigation().getTargetPos();
/* 362 */     if ($$0 != null) {
/* 363 */       return $$0.closerToCenterThan((Position)position(), 12.0D);
/*     */     }
/* 365 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void travel(Vec3 $$0) {
/* 370 */     if (isEffectiveAi() && isInWater()) {
/* 371 */       moveRelative(getSpeed(), $$0);
/* 372 */       move(MoverType.SELF, getDeltaMovement());
/*     */       
/* 374 */       setDeltaMovement(getDeltaMovement().scale(0.9D));
/*     */       
/* 376 */       if (getTarget() == null) {
/* 377 */         setDeltaMovement(getDeltaMovement().add(0.0D, -0.005D, 0.0D));
/*     */       }
/*     */     } else {
/* 380 */       super.travel($$0);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canBeLeashed(Player $$0) {
/* 386 */     return true;
/*     */   }
/*     */   
/*     */   private class PlayWithItemsGoal
/*     */     extends Goal {
/*     */     private int cooldown;
/*     */     
/*     */     public boolean canUse() {
/* 394 */       if (this.cooldown > Dolphin.this.tickCount) {
/* 395 */         return false;
/*     */       }
/* 397 */       List<ItemEntity> $$0 = Dolphin.this.level().getEntitiesOfClass(ItemEntity.class, Dolphin.this.getBoundingBox().inflate(8.0D, 8.0D, 8.0D), Dolphin.ALLOWED_ITEMS);
/* 398 */       return (!$$0.isEmpty() || !Dolphin.this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty());
/*     */     }
/*     */ 
/*     */     
/*     */     public void start() {
/* 403 */       List<ItemEntity> $$0 = Dolphin.this.level().getEntitiesOfClass(ItemEntity.class, Dolphin.this.getBoundingBox().inflate(8.0D, 8.0D, 8.0D), Dolphin.ALLOWED_ITEMS);
/* 404 */       if (!$$0.isEmpty()) {
/* 405 */         Dolphin.this.getNavigation().moveTo((Entity)$$0.get(0), 1.2000000476837158D);
/* 406 */         Dolphin.this.playSound(SoundEvents.DOLPHIN_PLAY, 1.0F, 1.0F);
/*     */       } 
/* 408 */       this.cooldown = 0;
/*     */     }
/*     */ 
/*     */     
/*     */     public void stop() {
/* 413 */       ItemStack $$0 = Dolphin.this.getItemBySlot(EquipmentSlot.MAINHAND);
/* 414 */       if (!$$0.isEmpty()) {
/* 415 */         drop($$0);
/* 416 */         Dolphin.this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
/* 417 */         this.cooldown = Dolphin.this.tickCount + Dolphin.this.random.nextInt(100);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick() {
/* 423 */       List<ItemEntity> $$0 = Dolphin.this.level().getEntitiesOfClass(ItemEntity.class, Dolphin.this.getBoundingBox().inflate(8.0D, 8.0D, 8.0D), Dolphin.ALLOWED_ITEMS);
/*     */       
/* 425 */       ItemStack $$1 = Dolphin.this.getItemBySlot(EquipmentSlot.MAINHAND);
/* 426 */       if (!$$1.isEmpty()) {
/* 427 */         drop($$1);
/* 428 */         Dolphin.this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
/* 429 */       } else if (!$$0.isEmpty()) {
/* 430 */         Dolphin.this.getNavigation().moveTo((Entity)$$0.get(0), 1.2000000476837158D);
/*     */       } 
/*     */     }
/*     */     
/*     */     private void drop(ItemStack $$0) {
/* 435 */       if ($$0.isEmpty()) {
/*     */         return;
/*     */       }
/*     */       
/* 439 */       double $$1 = Dolphin.this.getEyeY() - 0.30000001192092896D;
/* 440 */       ItemEntity $$2 = new ItemEntity(Dolphin.this.level(), Dolphin.this.getX(), $$1, Dolphin.this.getZ(), $$0);
/* 441 */       $$2.setPickUpDelay(40);
/*     */       
/* 443 */       $$2.setThrower((Entity)Dolphin.this);
/*     */       
/* 445 */       float $$3 = 0.3F;
/* 446 */       float $$4 = Dolphin.this.random.nextFloat() * 6.2831855F;
/* 447 */       float $$5 = 0.02F * Dolphin.this.random.nextFloat();
/* 448 */       $$2.setDeltaMovement((0.3F * 
/* 449 */           -Mth.sin(Dolphin.this.getYRot() * 0.017453292F) * Mth.cos(Dolphin.this.getXRot() * 0.017453292F) + Mth.cos($$4) * $$5), (0.3F * 
/* 450 */           Mth.sin(Dolphin.this.getXRot() * 0.017453292F) * 1.5F), (0.3F * 
/* 451 */           Mth.cos(Dolphin.this.getYRot() * 0.017453292F) * Mth.cos(Dolphin.this.getXRot() * 0.017453292F) + Mth.sin($$4) * $$5));
/*     */ 
/*     */       
/* 454 */       Dolphin.this.level().addFreshEntity((Entity)$$2);
/*     */     }
/*     */   }
/*     */   
/*     */   private static class DolphinSwimWithPlayerGoal extends Goal {
/*     */     private final Dolphin dolphin;
/*     */     private final double speedModifier;
/*     */     @Nullable
/*     */     private Player player;
/*     */     
/*     */     DolphinSwimWithPlayerGoal(Dolphin $$0, double $$1) {
/* 465 */       this.dolphin = $$0;
/* 466 */       this.speedModifier = $$1;
/* 467 */       setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canUse() {
/* 472 */       this.player = this.dolphin.level().getNearestPlayer(Dolphin.SWIM_WITH_PLAYER_TARGETING, (LivingEntity)this.dolphin);
/* 473 */       if (this.player == null) {
/* 474 */         return false;
/*     */       }
/* 476 */       return (this.player.isSwimming() && this.dolphin.getTarget() != this.player);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canContinueToUse() {
/* 481 */       return (this.player != null && this.player.isSwimming() && this.dolphin.distanceToSqr((Entity)this.player) < 256.0D);
/*     */     }
/*     */ 
/*     */     
/*     */     public void start() {
/* 486 */       this.player.addEffect(new MobEffectInstance(MobEffects.DOLPHINS_GRACE, 100), (Entity)this.dolphin);
/*     */     }
/*     */ 
/*     */     
/*     */     public void stop() {
/* 491 */       this.player = null;
/* 492 */       this.dolphin.getNavigation().stop();
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick() {
/* 497 */       this.dolphin.getLookControl().setLookAt((Entity)this.player, (this.dolphin.getMaxHeadYRot() + 20), this.dolphin.getMaxHeadXRot());
/* 498 */       if (this.dolphin.distanceToSqr((Entity)this.player) < 6.25D) {
/* 499 */         this.dolphin.getNavigation().stop();
/*     */       } else {
/* 501 */         this.dolphin.getNavigation().moveTo((Entity)this.player, this.speedModifier);
/*     */       } 
/*     */       
/* 504 */       if (this.player.isSwimming() && (this.player.level()).random.nextInt(6) == 0)
/* 505 */         this.player.addEffect(new MobEffectInstance(MobEffects.DOLPHINS_GRACE, 100), (Entity)this.dolphin); 
/*     */     }
/*     */   }
/*     */   
/*     */   private static class DolphinSwimToTreasureGoal
/*     */     extends Goal {
/*     */     private final Dolphin dolphin;
/*     */     private boolean stuck;
/*     */     
/*     */     DolphinSwimToTreasureGoal(Dolphin $$0) {
/* 515 */       this.dolphin = $$0;
/* 516 */       setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isInterruptable() {
/* 521 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canUse() {
/* 526 */       return (this.dolphin.gotFish() && this.dolphin.getAirSupply() >= 100);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canContinueToUse() {
/* 531 */       BlockPos $$0 = this.dolphin.getTreasurePos();
/* 532 */       return (!BlockPos.containing($$0.getX(), this.dolphin.getY(), $$0.getZ()).closerToCenterThan((Position)this.dolphin.position(), 4.0D) && !this.stuck && this.dolphin.getAirSupply() >= 100);
/*     */     }
/*     */ 
/*     */     
/*     */     public void start() {
/* 537 */       if (!(this.dolphin.level() instanceof ServerLevel)) {
/*     */         return;
/*     */       }
/* 540 */       ServerLevel $$0 = (ServerLevel)this.dolphin.level();
/* 541 */       this.stuck = false;
/* 542 */       this.dolphin.getNavigation().stop();
/*     */       
/* 544 */       BlockPos $$1 = this.dolphin.blockPosition();
/*     */       
/* 546 */       BlockPos $$2 = $$0.findNearestMapStructure(StructureTags.DOLPHIN_LOCATED, $$1, 50, false);
/* 547 */       if ($$2 != null) {
/* 548 */         this.dolphin.setTreasurePos($$2);
/*     */       } else {
/*     */         
/* 551 */         this.stuck = true;
/*     */         
/*     */         return;
/*     */       } 
/* 555 */       $$0.broadcastEntityEvent((Entity)this.dolphin, (byte)38);
/*     */     }
/*     */ 
/*     */     
/*     */     public void stop() {
/* 560 */       BlockPos $$0 = this.dolphin.getTreasurePos();
/* 561 */       if (BlockPos.containing($$0.getX(), this.dolphin.getY(), $$0.getZ()).closerToCenterThan((Position)this.dolphin.position(), 4.0D) || this.stuck) {
/* 562 */         this.dolphin.setGotFish(false);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick() {
/* 568 */       Level $$0 = this.dolphin.level();
/*     */       
/* 570 */       if (this.dolphin.closeToNextPos() || this.dolphin.getNavigation().isDone()) {
/* 571 */         Vec3 $$1 = Vec3.atCenterOf((Vec3i)this.dolphin.getTreasurePos());
/* 572 */         Vec3 $$2 = DefaultRandomPos.getPosTowards(this.dolphin, 16, 1, $$1, 0.39269909262657166D);
/* 573 */         if ($$2 == null) {
/* 574 */           $$2 = DefaultRandomPos.getPosTowards(this.dolphin, 8, 4, $$1, 1.5707963705062866D);
/*     */         }
/*     */         
/* 577 */         if ($$2 != null) {
/* 578 */           BlockPos $$3 = BlockPos.containing((Position)$$2);
/* 579 */           if (!$$0.getFluidState($$3).is(FluidTags.WATER) || !$$0.getBlockState($$3).isPathfindable((BlockGetter)$$0, $$3, PathComputationType.WATER)) {
/* 580 */             $$2 = DefaultRandomPos.getPosTowards(this.dolphin, 8, 5, $$1, 1.5707963705062866D);
/*     */           }
/*     */         } 
/*     */         
/* 584 */         if ($$2 == null) {
/* 585 */           this.stuck = true;
/*     */           
/*     */           return;
/*     */         } 
/* 589 */         this.dolphin.getLookControl().setLookAt($$2.x, $$2.y, $$2.z, (this.dolphin.getMaxHeadYRot() + 20), this.dolphin.getMaxHeadXRot());
/* 590 */         this.dolphin.getNavigation().moveTo($$2.x, $$2.y, $$2.z, 1.3D);
/*     */         
/* 592 */         if ($$0.random.nextInt(adjustedTickDelay(80)) == 0)
/* 593 */           $$0.broadcastEntityEvent((Entity)this.dolphin, (byte)38); 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\animal\Dolphin.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */