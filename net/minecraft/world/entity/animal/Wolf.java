/*     */ package net.minecraft.world.entity.animal;
/*     */ 
/*     */ import java.util.UUID;
/*     */ import java.util.function.Predicate;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.syncher.EntityDataAccessor;
/*     */ import net.minecraft.network.syncher.EntityDataSerializers;
/*     */ import net.minecraft.network.syncher.SynchedEntityData;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.util.TimeUtil;
/*     */ import net.minecraft.util.valueproviders.UniformInt;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
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
/*     */ import net.minecraft.world.entity.TamableAnimal;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
/*     */ import net.minecraft.world.entity.ai.goal.BegGoal;
/*     */ import net.minecraft.world.entity.ai.goal.BreedGoal;
/*     */ import net.minecraft.world.entity.ai.goal.FloatGoal;
/*     */ import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
/*     */ import net.minecraft.world.entity.ai.goal.Goal;
/*     */ import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
/*     */ import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
/*     */ import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
/*     */ import net.minecraft.world.entity.ai.goal.PanicGoal;
/*     */ import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
/*     */ import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
/*     */ import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
/*     */ import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
/*     */ import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
/*     */ import net.minecraft.world.entity.ai.goal.target.NonTameRandomTargetGoal;
/*     */ import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
/*     */ import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
/*     */ import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
/*     */ import net.minecraft.world.entity.animal.horse.AbstractHorse;
/*     */ import net.minecraft.world.entity.animal.horse.Llama;
/*     */ import net.minecraft.world.entity.monster.AbstractSkeleton;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.DyeColor;
/*     */ import net.minecraft.world.item.DyeItem;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.BlockAndTintGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.level.pathfinder.BlockPathTypes;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import org.joml.Vector3f;
/*     */ 
/*     */ public class Wolf
/*     */   extends TamableAnimal
/*     */   implements NeutralMob {
/*  77 */   private static final EntityDataAccessor<Boolean> DATA_INTERESTED_ID = SynchedEntityData.defineId(Wolf.class, EntityDataSerializers.BOOLEAN);
/*  78 */   private static final EntityDataAccessor<Integer> DATA_COLLAR_COLOR = SynchedEntityData.defineId(Wolf.class, EntityDataSerializers.INT);
/*  79 */   private static final EntityDataAccessor<Integer> DATA_REMAINING_ANGER_TIME = SynchedEntityData.defineId(Wolf.class, EntityDataSerializers.INT); public static final Predicate<LivingEntity> PREY_SELECTOR; private static final float START_HEALTH = 8.0F; private static final float TAME_HEALTH = 20.0F; private float interestedAngle; private float interestedAngleO; private boolean isWet; private boolean isShaking; private float shakeAnim; private float shakeAnimO;
/*     */   static {
/*  81 */     PREY_SELECTOR = ($$0 -> {
/*     */         EntityType<?> $$1 = $$0.getType();
/*  83 */         return ($$1 == EntityType.SHEEP || $$1 == EntityType.RABBIT || $$1 == EntityType.FOX);
/*     */       });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  96 */   private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
/*     */   @Nullable
/*     */   private UUID persistentAngerTarget;
/*     */   
/*     */   public Wolf(EntityType<? extends Wolf> $$0, Level $$1) {
/* 101 */     super($$0, $$1);
/*     */     
/* 103 */     setTame(false);
/*     */     
/* 105 */     setPathfindingMalus(BlockPathTypes.POWDER_SNOW, -1.0F);
/* 106 */     setPathfindingMalus(BlockPathTypes.DANGER_POWDER_SNOW, -1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerGoals() {
/* 111 */     this.goalSelector.addGoal(1, (Goal)new FloatGoal((Mob)this));
/* 112 */     this.goalSelector.addGoal(1, (Goal)new WolfPanicGoal(1.5D));
/* 113 */     this.goalSelector.addGoal(2, (Goal)new SitWhenOrderedToGoal(this));
/* 114 */     this.goalSelector.addGoal(3, (Goal)new WolfAvoidEntityGoal<>(this, Llama.class, 24.0F, 1.5D, 1.5D));
/* 115 */     this.goalSelector.addGoal(4, (Goal)new LeapAtTargetGoal((Mob)this, 0.4F));
/* 116 */     this.goalSelector.addGoal(5, (Goal)new MeleeAttackGoal((PathfinderMob)this, 1.0D, true));
/* 117 */     this.goalSelector.addGoal(6, (Goal)new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
/* 118 */     this.goalSelector.addGoal(7, (Goal)new BreedGoal((Animal)this, 1.0D));
/* 119 */     this.goalSelector.addGoal(8, (Goal)new WaterAvoidingRandomStrollGoal((PathfinderMob)this, 1.0D));
/* 120 */     this.goalSelector.addGoal(9, (Goal)new BegGoal(this, 8.0F));
/* 121 */     this.goalSelector.addGoal(10, (Goal)new LookAtPlayerGoal((Mob)this, Player.class, 8.0F));
/* 122 */     this.goalSelector.addGoal(10, (Goal)new RandomLookAroundGoal((Mob)this));
/*     */     
/* 124 */     this.targetSelector.addGoal(1, (Goal)new OwnerHurtByTargetGoal(this));
/* 125 */     this.targetSelector.addGoal(2, (Goal)new OwnerHurtTargetGoal(this));
/* 126 */     this.targetSelector.addGoal(3, (Goal)(new HurtByTargetGoal((PathfinderMob)this, new Class[0])).setAlertOthers(new Class[0]));
/* 127 */     this.targetSelector.addGoal(4, (Goal)new NearestAttackableTargetGoal((Mob)this, Player.class, 10, true, false, this::isAngryAt));
/* 128 */     this.targetSelector.addGoal(5, (Goal)new NonTameRandomTargetGoal(this, Animal.class, false, PREY_SELECTOR));
/* 129 */     this.targetSelector.addGoal(6, (Goal)new NonTameRandomTargetGoal(this, Turtle.class, false, Turtle.BABY_ON_LAND_SELECTOR));
/* 130 */     this.targetSelector.addGoal(7, (Goal)new NearestAttackableTargetGoal((Mob)this, AbstractSkeleton.class, false));
/* 131 */     this.targetSelector.addGoal(8, (Goal)new ResetUniversalAngerTargetGoal((Mob)this, true));
/*     */   }
/*     */   
/*     */   public static AttributeSupplier.Builder createAttributes() {
/* 135 */     return Mob.createMobAttributes()
/* 136 */       .add(Attributes.MOVEMENT_SPEED, 0.30000001192092896D)
/* 137 */       .add(Attributes.MAX_HEALTH, 8.0D)
/* 138 */       .add(Attributes.ATTACK_DAMAGE, 2.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {
/* 143 */     super.defineSynchedData();
/* 144 */     this.entityData.define(DATA_INTERESTED_ID, Boolean.valueOf(false));
/* 145 */     this.entityData.define(DATA_COLLAR_COLOR, Integer.valueOf(DyeColor.RED.getId()));
/* 146 */     this.entityData.define(DATA_REMAINING_ANGER_TIME, Integer.valueOf(0));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void playStepSound(BlockPos $$0, BlockState $$1) {
/* 151 */     playSound(SoundEvents.WOLF_STEP, 0.15F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/* 156 */     super.addAdditionalSaveData($$0);
/*     */     
/* 158 */     $$0.putByte("CollarColor", (byte)getCollarColor().getId());
/* 159 */     addPersistentAngerSaveData($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/* 164 */     super.readAdditionalSaveData($$0);
/*     */     
/* 166 */     if ($$0.contains("CollarColor", 99)) {
/* 167 */       setCollarColor(DyeColor.byId($$0.getInt("CollarColor")));
/*     */     }
/* 169 */     readPersistentAngerSaveData(level(), $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/* 174 */     if (isAngry()) {
/* 175 */       return SoundEvents.WOLF_GROWL;
/*     */     }
/* 177 */     if (this.random.nextInt(3) == 0) {
/* 178 */       if (isTame() && getHealth() < 10.0F) {
/* 179 */         return SoundEvents.WOLF_WHINE;
/*     */       }
/* 181 */       return SoundEvents.WOLF_PANT;
/*     */     } 
/* 183 */     return SoundEvents.WOLF_AMBIENT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource $$0) {
/* 188 */     return SoundEvents.WOLF_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/* 193 */     return SoundEvents.WOLF_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getSoundVolume() {
/* 198 */     return 0.4F;
/*     */   }
/*     */ 
/*     */   
/*     */   public void aiStep() {
/* 203 */     super.aiStep();
/*     */     
/* 205 */     if (!(level()).isClientSide && this.isWet && !this.isShaking && !isPathFinding() && onGround()) {
/* 206 */       this.isShaking = true;
/* 207 */       this.shakeAnim = 0.0F;
/* 208 */       this.shakeAnimO = 0.0F;
/* 209 */       level().broadcastEntityEvent((Entity)this, (byte)8);
/*     */     } 
/* 211 */     if (!(level()).isClientSide) {
/* 212 */       updatePersistentAnger((ServerLevel)level(), true);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 218 */     super.tick();
/*     */     
/* 220 */     if (!isAlive()) {
/*     */       return;
/*     */     }
/*     */     
/* 224 */     this.interestedAngleO = this.interestedAngle;
/* 225 */     if (isInterested()) {
/* 226 */       this.interestedAngle += (1.0F - this.interestedAngle) * 0.4F;
/*     */     } else {
/* 228 */       this.interestedAngle += (0.0F - this.interestedAngle) * 0.4F;
/*     */     } 
/*     */     
/* 231 */     if (isInWaterRainOrBubble()) {
/* 232 */       this.isWet = true;
/* 233 */       if (this.isShaking && !(level()).isClientSide) {
/* 234 */         level().broadcastEntityEvent((Entity)this, (byte)56);
/* 235 */         cancelShake();
/*     */       } 
/* 237 */     } else if ((this.isWet || this.isShaking) && 
/* 238 */       this.isShaking) {
/* 239 */       if (this.shakeAnim == 0.0F) {
/* 240 */         playSound(SoundEvents.WOLF_SHAKE, getSoundVolume(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
/* 241 */         gameEvent(GameEvent.ENTITY_ACTION);
/*     */       } 
/*     */       
/* 244 */       this.shakeAnimO = this.shakeAnim;
/* 245 */       this.shakeAnim += 0.05F;
/*     */       
/* 247 */       if (this.shakeAnimO >= 2.0F) {
/* 248 */         this.isWet = false;
/* 249 */         this.isShaking = false;
/* 250 */         this.shakeAnimO = 0.0F;
/* 251 */         this.shakeAnim = 0.0F;
/*     */       } 
/*     */       
/* 254 */       if (this.shakeAnim > 0.4F) {
/* 255 */         float $$0 = (float)getY();
/* 256 */         int $$1 = (int)(Mth.sin((this.shakeAnim - 0.4F) * 3.1415927F) * 7.0F);
/* 257 */         Vec3 $$2 = getDeltaMovement();
/* 258 */         for (int $$3 = 0; $$3 < $$1; $$3++) {
/* 259 */           float $$4 = (this.random.nextFloat() * 2.0F - 1.0F) * getBbWidth() * 0.5F;
/* 260 */           float $$5 = (this.random.nextFloat() * 2.0F - 1.0F) * getBbWidth() * 0.5F;
/* 261 */           level().addParticle((ParticleOptions)ParticleTypes.SPLASH, getX() + $$4, ($$0 + 0.8F), getZ() + $$5, $$2.x, $$2.y, $$2.z);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void cancelShake() {
/* 269 */     this.isShaking = false;
/* 270 */     this.shakeAnim = 0.0F;
/* 271 */     this.shakeAnimO = 0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public void die(DamageSource $$0) {
/* 276 */     this.isWet = false;
/* 277 */     this.isShaking = false;
/* 278 */     this.shakeAnimO = 0.0F;
/* 279 */     this.shakeAnim = 0.0F;
/*     */     
/* 281 */     super.die($$0);
/*     */   }
/*     */   
/*     */   public boolean isWet() {
/* 285 */     return this.isWet;
/*     */   }
/*     */   
/*     */   public float getWetShade(float $$0) {
/* 289 */     return Math.min(0.5F + Mth.lerp($$0, this.shakeAnimO, this.shakeAnim) / 2.0F * 0.5F, 1.0F);
/*     */   }
/*     */   
/*     */   public float getBodyRollAngle(float $$0, float $$1) {
/* 293 */     float $$2 = (Mth.lerp($$0, this.shakeAnimO, this.shakeAnim) + $$1) / 1.8F;
/* 294 */     if ($$2 < 0.0F) {
/* 295 */       $$2 = 0.0F;
/* 296 */     } else if ($$2 > 1.0F) {
/* 297 */       $$2 = 1.0F;
/*     */     } 
/* 299 */     return Mth.sin($$2 * 3.1415927F) * Mth.sin($$2 * 3.1415927F * 11.0F) * 0.15F * 3.1415927F;
/*     */   }
/*     */   
/*     */   public float getHeadRollAngle(float $$0) {
/* 303 */     return Mth.lerp($$0, this.interestedAngleO, this.interestedAngle) * 0.15F * 3.1415927F;
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getStandingEyeHeight(Pose $$0, EntityDimensions $$1) {
/* 308 */     return $$1.height * 0.8F;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxHeadXRot() {
/* 313 */     if (isInSittingPose()) {
/* 314 */       return 20;
/*     */     }
/* 316 */     return super.getMaxHeadXRot();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hurt(DamageSource $$0, float $$1) {
/* 321 */     if (isInvulnerableTo($$0)) {
/* 322 */       return false;
/*     */     }
/* 324 */     Entity $$2 = $$0.getEntity();
/*     */     
/* 326 */     if (!(level()).isClientSide) {
/* 327 */       setOrderedToSit(false);
/*     */     }
/*     */     
/* 330 */     if ($$2 != null && !($$2 instanceof Player) && !($$2 instanceof net.minecraft.world.entity.projectile.AbstractArrow))
/*     */     {
/* 332 */       $$1 = ($$1 + 1.0F) / 2.0F;
/*     */     }
/* 334 */     return super.hurt($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean doHurtTarget(Entity $$0) {
/* 339 */     boolean $$1 = $$0.hurt(damageSources().mobAttack((LivingEntity)this), (int)getAttributeValue(Attributes.ATTACK_DAMAGE));
/* 340 */     if ($$1) {
/* 341 */       doEnchantDamageEffects((LivingEntity)this, $$0);
/*     */     }
/* 343 */     return $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTame(boolean $$0) {
/* 348 */     super.setTame($$0);
/*     */     
/* 350 */     if ($$0) {
/* 351 */       getAttribute(Attributes.MAX_HEALTH).setBaseValue(20.0D);
/* 352 */       setHealth(20.0F);
/*     */     } else {
/* 354 */       getAttribute(Attributes.MAX_HEALTH).setBaseValue(8.0D);
/*     */     } 
/*     */     
/* 357 */     getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(4.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult mobInteract(Player $$0, InteractionHand $$1) {
/* 362 */     ItemStack $$2 = $$0.getItemInHand($$1);
/* 363 */     Item $$3 = $$2.getItem();
/*     */     
/* 365 */     if ((level()).isClientSide) {
/* 366 */       boolean $$4 = (isOwnedBy((LivingEntity)$$0) || isTame() || ($$2.is(Items.BONE) && !isTame() && !isAngry()));
/* 367 */       return $$4 ? InteractionResult.CONSUME : InteractionResult.PASS;
/*     */     } 
/*     */     
/* 370 */     if (isTame())
/* 371 */     { if (isFood($$2) && getHealth() < getMaxHealth()) {
/* 372 */         if (!($$0.getAbilities()).instabuild) {
/* 373 */           $$2.shrink(1);
/*     */         }
/* 375 */         heal($$3.getFoodProperties().getNutrition());
/* 376 */         return InteractionResult.SUCCESS;
/* 377 */       }  if ($$3 instanceof DyeItem) { DyeItem $$5 = (DyeItem)$$3; if (isOwnedBy((LivingEntity)$$0))
/* 378 */         { DyeColor $$6 = $$5.getDyeColor();
/* 379 */           if ($$6 != getCollarColor()) {
/* 380 */             setCollarColor($$6);
/*     */             
/* 382 */             if (!($$0.getAbilities()).instabuild) {
/* 383 */               $$2.shrink(1);
/*     */             }
/*     */             
/* 386 */             return InteractionResult.SUCCESS;
/*     */           } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 421 */           return super.mobInteract($$0, $$1); }  }  InteractionResult $$7 = super.mobInteract($$0, $$1); if ((!$$7.consumesAction() || isBaby()) && isOwnedBy((LivingEntity)$$0)) { setOrderedToSit(!isOrderedToSit()); this.jumping = false; this.navigation.stop(); setTarget(null); return InteractionResult.SUCCESS; }  return $$7; }  if ($$2.is(Items.BONE) && !isAngry()) { if (!($$0.getAbilities()).instabuild) $$2.shrink(1);  if (this.random.nextInt(3) == 0) { tame($$0); this.navigation.stop(); setTarget(null); setOrderedToSit(true); level().broadcastEntityEvent((Entity)this, (byte)7); } else { level().broadcastEntityEvent((Entity)this, (byte)6); }  return InteractionResult.SUCCESS; }  return super.mobInteract($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleEntityEvent(byte $$0) {
/* 426 */     if ($$0 == 8) {
/* 427 */       this.isShaking = true;
/* 428 */       this.shakeAnim = 0.0F;
/* 429 */       this.shakeAnimO = 0.0F;
/* 430 */     } else if ($$0 == 56) {
/* 431 */       cancelShake();
/*     */     } else {
/* 433 */       super.handleEntityEvent($$0);
/*     */     } 
/*     */   }
/*     */   
/*     */   public float getTailAngle() {
/* 438 */     if (isAngry())
/* 439 */       return 1.5393804F; 
/* 440 */     if (isTame()) {
/* 441 */       return (0.55F - (getMaxHealth() - getHealth()) * 0.02F) * 3.1415927F;
/*     */     }
/* 443 */     return 0.62831855F;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFood(ItemStack $$0) {
/* 448 */     Item $$1 = $$0.getItem();
/* 449 */     return ($$1.isEdible() && $$1.getFoodProperties().isMeat());
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxSpawnClusterSize() {
/* 454 */     return 8;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRemainingPersistentAngerTime() {
/* 459 */     return ((Integer)this.entityData.get(DATA_REMAINING_ANGER_TIME)).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRemainingPersistentAngerTime(int $$0) {
/* 464 */     this.entityData.set(DATA_REMAINING_ANGER_TIME, Integer.valueOf($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public void startPersistentAngerTimer() {
/* 469 */     setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public UUID getPersistentAngerTarget() {
/* 475 */     return this.persistentAngerTarget;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPersistentAngerTarget(@Nullable UUID $$0) {
/* 480 */     this.persistentAngerTarget = $$0;
/*     */   }
/*     */   
/*     */   public DyeColor getCollarColor() {
/* 484 */     return DyeColor.byId(((Integer)this.entityData.get(DATA_COLLAR_COLOR)).intValue());
/*     */   }
/*     */   
/*     */   public void setCollarColor(DyeColor $$0) {
/* 488 */     this.entityData.set(DATA_COLLAR_COLOR, Integer.valueOf($$0.getId()));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Wolf getBreedOffspring(ServerLevel $$0, AgeableMob $$1) {
/* 494 */     Wolf $$2 = (Wolf)EntityType.WOLF.create((Level)$$0);
/* 495 */     if ($$2 != null) {
/* 496 */       UUID $$3 = getOwnerUUID();
/* 497 */       if ($$3 != null) {
/* 498 */         $$2.setOwnerUUID($$3);
/* 499 */         $$2.setTame(true);
/*     */       } 
/*     */     } 
/* 502 */     return $$2;
/*     */   }
/*     */   
/*     */   public void setIsInterested(boolean $$0) {
/* 506 */     this.entityData.set(DATA_INTERESTED_ID, Boolean.valueOf($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canMate(Animal $$0) {
/* 511 */     if ($$0 == this) {
/* 512 */       return false;
/*     */     }
/* 514 */     if (!isTame()) {
/* 515 */       return false;
/*     */     }
/* 517 */     if (!($$0 instanceof Wolf)) {
/* 518 */       return false;
/*     */     }
/*     */     
/* 521 */     Wolf $$1 = (Wolf)$$0;
/* 522 */     if (!$$1.isTame()) {
/* 523 */       return false;
/*     */     }
/* 525 */     if ($$1.isInSittingPose()) {
/* 526 */       return false;
/*     */     }
/*     */     
/* 529 */     return (isInLove() && $$1.isInLove());
/*     */   }
/*     */   
/*     */   public boolean isInterested() {
/* 533 */     return ((Boolean)this.entityData.get(DATA_INTERESTED_ID)).booleanValue();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean wantsToAttack(LivingEntity $$0, LivingEntity $$1) {
/* 539 */     if ($$0 instanceof net.minecraft.world.entity.monster.Creeper || $$0 instanceof net.minecraft.world.entity.monster.Ghast) {
/* 540 */       return false;
/*     */     }
/*     */     
/* 543 */     if ($$0 instanceof Wolf) { Wolf $$2 = (Wolf)$$0;
/* 544 */       return (!$$2.isTame() || $$2.getOwner() != $$1); }
/*     */     
/* 546 */     if ($$0 instanceof Player && $$1 instanceof Player && !((Player)$$1).canHarmPlayer((Player)$$0))
/*     */     {
/* 548 */       return false;
/*     */     }
/*     */     
/* 551 */     if ($$0 instanceof AbstractHorse && ((AbstractHorse)$$0).isTamed()) {
/* 552 */       return false;
/*     */     }
/*     */     
/* 555 */     return (!($$0 instanceof TamableAnimal) || !((TamableAnimal)$$0).isTame());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canBeLeashed(Player $$0) {
/* 560 */     return (!isAngry() && super.canBeLeashed($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public Vec3 getLeashOffset() {
/* 565 */     return new Vec3(0.0D, (0.6F * getEyeHeight()), (getBbWidth() * 0.4F));
/*     */   }
/*     */ 
/*     */   
/*     */   protected Vector3f getPassengerAttachmentPoint(Entity $$0, EntityDimensions $$1, float $$2) {
/* 570 */     return new Vector3f(0.0F, $$1.height - 0.03125F * $$2, -0.0625F * $$2);
/*     */   }
/*     */   
/*     */   public static boolean checkWolfSpawnRules(EntityType<Wolf> $$0, LevelAccessor $$1, MobSpawnType $$2, BlockPos $$3, RandomSource $$4) {
/* 574 */     return ($$1.getBlockState($$3.below()).is(BlockTags.WOLVES_SPAWNABLE_ON) && 
/* 575 */       isBrightEnoughToSpawn((BlockAndTintGetter)$$1, $$3));
/*     */   }
/*     */ 
/*     */   
/*     */   private class WolfAvoidEntityGoal<T extends LivingEntity>
/*     */     extends AvoidEntityGoal<T>
/*     */   {
/*     */     private final Wolf wolf;
/*     */     
/*     */     public WolfAvoidEntityGoal(Wolf $$0, Class<T> $$1, float $$2, double $$3, double $$4) {
/* 585 */       super((PathfinderMob)$$0, $$1, $$2, $$3, $$4);
/* 586 */       this.wolf = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canUse() {
/* 591 */       if (super.canUse() && 
/* 592 */         this.toAvoid instanceof Llama) {
/* 593 */         return (!this.wolf.isTame() && avoidLlama((Llama)this.toAvoid));
/*     */       }
/*     */ 
/*     */       
/* 597 */       return false;
/*     */     }
/*     */     
/*     */     private boolean avoidLlama(Llama $$0) {
/* 601 */       return ($$0.getStrength() >= Wolf.this.random.nextInt(5));
/*     */     }
/*     */ 
/*     */     
/*     */     public void start() {
/* 606 */       Wolf.this.setTarget(null);
/* 607 */       super.start();
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick() {
/* 612 */       Wolf.this.setTarget(null);
/* 613 */       super.tick();
/*     */     }
/*     */   }
/*     */   
/*     */   private class WolfPanicGoal extends PanicGoal {
/*     */     public WolfPanicGoal(double $$0) {
/* 619 */       super((PathfinderMob)Wolf.this, $$0);
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean shouldPanic() {
/* 624 */       return (this.mob.isFreezing() || this.mob.isOnFire());
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\animal\Wolf.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */