/*     */ package net.minecraft.world.entity.monster;
/*     */ import java.util.EnumSet;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import java.util.UUID;
/*     */ import java.util.function.Predicate;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.HolderGetter;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.NbtUtils;
/*     */ import net.minecraft.network.syncher.EntityDataAccessor;
/*     */ import net.minecraft.network.syncher.EntityDataSerializers;
/*     */ import net.minecraft.network.syncher.SynchedEntityData;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.tags.DamageTypeTags;
/*     */ import net.minecraft.tags.FluidTags;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.util.TimeUtil;
/*     */ import net.minecraft.util.valueproviders.UniformInt;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.effect.MobEffectInstance;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityDimensions;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.NeutralMob;
/*     */ import net.minecraft.world.entity.Pose;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeInstance;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeModifier;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.entity.ai.goal.FloatGoal;
/*     */ import net.minecraft.world.entity.ai.goal.Goal;
/*     */ import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
/*     */ import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
/*     */ import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
/*     */ import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
/*     */ import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
/*     */ import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
/*     */ import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
/*     */ import net.minecraft.world.entity.ai.targeting.TargetingConditions;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.entity.projectile.ThrownPotion;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.item.alchemy.Potion;
/*     */ import net.minecraft.world.item.alchemy.PotionUtils;
/*     */ import net.minecraft.world.item.alchemy.Potions;
/*     */ import net.minecraft.world.item.enchantment.Enchantments;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.ClipContext;
/*     */ import net.minecraft.world.level.GameRules;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.level.storage.loot.LootParams;
/*     */ import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import org.joml.Vector3f;
/*     */ 
/*     */ public class EnderMan extends Monster implements NeutralMob {
/*  77 */   private static final UUID SPEED_MODIFIER_ATTACKING_UUID = UUID.fromString("020E0DFB-87AE-4653-9556-831010E291A0");
/*  78 */   private static final AttributeModifier SPEED_MODIFIER_ATTACKING = new AttributeModifier(SPEED_MODIFIER_ATTACKING_UUID, "Attacking speed boost", 0.15000000596046448D, AttributeModifier.Operation.ADDITION);
/*     */   
/*     */   private static final int DELAY_BETWEEN_CREEPY_STARE_SOUND = 400;
/*     */   private static final int MIN_DEAGGRESSION_TIME = 600;
/*  82 */   private static final EntityDataAccessor<Optional<BlockState>> DATA_CARRY_STATE = SynchedEntityData.defineId(EnderMan.class, EntityDataSerializers.OPTIONAL_BLOCK_STATE);
/*  83 */   private static final EntityDataAccessor<Boolean> DATA_CREEPY = SynchedEntityData.defineId(EnderMan.class, EntityDataSerializers.BOOLEAN);
/*  84 */   private static final EntityDataAccessor<Boolean> DATA_STARED_AT = SynchedEntityData.defineId(EnderMan.class, EntityDataSerializers.BOOLEAN);
/*     */   
/*  86 */   private int lastStareSound = Integer.MIN_VALUE;
/*     */   
/*     */   private int targetChangeTime;
/*  89 */   private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
/*     */   private int remainingPersistentAngerTime;
/*     */   @Nullable
/*     */   private UUID persistentAngerTarget;
/*     */   
/*     */   public EnderMan(EntityType<? extends EnderMan> $$0, Level $$1) {
/*  95 */     super((EntityType)$$0, $$1);
/*     */     
/*  97 */     setMaxUpStep(1.0F);
/*     */     
/*  99 */     setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerGoals() {
/* 104 */     this.goalSelector.addGoal(0, (Goal)new FloatGoal((Mob)this));
/* 105 */     this.goalSelector.addGoal(1, new EndermanFreezeWhenLookedAt(this));
/* 106 */     this.goalSelector.addGoal(2, (Goal)new MeleeAttackGoal(this, 1.0D, false));
/* 107 */     this.goalSelector.addGoal(7, (Goal)new WaterAvoidingRandomStrollGoal(this, 1.0D, 0.0F));
/* 108 */     this.goalSelector.addGoal(8, (Goal)new LookAtPlayerGoal((Mob)this, Player.class, 8.0F));
/* 109 */     this.goalSelector.addGoal(8, (Goal)new RandomLookAroundGoal((Mob)this));
/*     */     
/* 111 */     this.goalSelector.addGoal(10, new EndermanLeaveBlockGoal(this));
/* 112 */     this.goalSelector.addGoal(11, new EndermanTakeBlockGoal(this));
/*     */     
/* 114 */     this.targetSelector.addGoal(1, (Goal)new EndermanLookForPlayerGoal(this, this::isAngryAt));
/* 115 */     this.targetSelector.addGoal(2, (Goal)new HurtByTargetGoal(this, new Class[0]));
/* 116 */     this.targetSelector.addGoal(3, (Goal)new NearestAttackableTargetGoal((Mob)this, Endermite.class, true, false));
/* 117 */     this.targetSelector.addGoal(4, (Goal)new ResetUniversalAngerTargetGoal((Mob)this, false));
/*     */   }
/*     */   
/*     */   public static AttributeSupplier.Builder createAttributes() {
/* 121 */     return Monster.createMonsterAttributes()
/* 122 */       .add(Attributes.MAX_HEALTH, 40.0D)
/* 123 */       .add(Attributes.MOVEMENT_SPEED, 0.30000001192092896D)
/* 124 */       .add(Attributes.ATTACK_DAMAGE, 7.0D)
/* 125 */       .add(Attributes.FOLLOW_RANGE, 64.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTarget(@Nullable LivingEntity $$0) {
/* 130 */     super.setTarget($$0);
/*     */     
/* 132 */     AttributeInstance $$1 = getAttribute(Attributes.MOVEMENT_SPEED);
/*     */     
/* 134 */     if ($$0 == null) {
/* 135 */       this.targetChangeTime = 0;
/* 136 */       this.entityData.set(DATA_CREEPY, Boolean.valueOf(false));
/* 137 */       this.entityData.set(DATA_STARED_AT, Boolean.valueOf(false));
/*     */       
/* 139 */       $$1.removeModifier(SPEED_MODIFIER_ATTACKING.getId());
/*     */     } else {
/* 141 */       this.targetChangeTime = this.tickCount;
/* 142 */       this.entityData.set(DATA_CREEPY, Boolean.valueOf(true));
/*     */       
/* 144 */       if (!$$1.hasModifier(SPEED_MODIFIER_ATTACKING)) {
/* 145 */         $$1.addTransientModifier(SPEED_MODIFIER_ATTACKING);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {
/* 152 */     super.defineSynchedData();
/*     */     
/* 154 */     this.entityData.define(DATA_CARRY_STATE, Optional.empty());
/* 155 */     this.entityData.define(DATA_CREEPY, Boolean.valueOf(false));
/* 156 */     this.entityData.define(DATA_STARED_AT, Boolean.valueOf(false));
/*     */   }
/*     */ 
/*     */   
/*     */   public void startPersistentAngerTimer() {
/* 161 */     setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRemainingPersistentAngerTime(int $$0) {
/* 166 */     this.remainingPersistentAngerTime = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRemainingPersistentAngerTime() {
/* 171 */     return this.remainingPersistentAngerTime;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPersistentAngerTarget(@Nullable UUID $$0) {
/* 176 */     this.persistentAngerTarget = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public UUID getPersistentAngerTarget() {
/* 182 */     return this.persistentAngerTarget;
/*     */   }
/*     */   
/*     */   public void playStareSound() {
/* 186 */     if (this.tickCount >= this.lastStareSound + 400) {
/* 187 */       this.lastStareSound = this.tickCount;
/* 188 */       if (!isSilent()) {
/* 189 */         level().playLocalSound(getX(), getEyeY(), getZ(), SoundEvents.ENDERMAN_STARE, getSoundSource(), 2.5F, 1.0F, false);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onSyncedDataUpdated(EntityDataAccessor<?> $$0) {
/* 196 */     if (DATA_CREEPY.equals($$0) && 
/* 197 */       hasBeenStaredAt() && (level()).isClientSide) {
/* 198 */       playStareSound();
/*     */     }
/*     */     
/* 201 */     super.onSyncedDataUpdated($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/* 206 */     super.addAdditionalSaveData($$0);
/* 207 */     BlockState $$1 = getCarriedBlock();
/* 208 */     if ($$1 != null) {
/* 209 */       $$0.put("carriedBlockState", (Tag)NbtUtils.writeBlockState($$1));
/*     */     }
/* 211 */     addPersistentAngerSaveData($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/* 216 */     super.readAdditionalSaveData($$0);
/* 217 */     BlockState $$1 = null;
/* 218 */     if ($$0.contains("carriedBlockState", 10)) {
/* 219 */       $$1 = NbtUtils.readBlockState((HolderGetter)level().holderLookup(Registries.BLOCK), $$0.getCompound("carriedBlockState"));
/* 220 */       if ($$1.isAir()) {
/* 221 */         $$1 = null;
/*     */       }
/*     */     } 
/* 224 */     setCarriedBlock($$1);
/* 225 */     readPersistentAngerSaveData(level(), $$0);
/*     */   }
/*     */   
/*     */   boolean isLookingAtMe(Player $$0) {
/* 229 */     ItemStack $$1 = (ItemStack)($$0.getInventory()).armor.get(3);
/* 230 */     if ($$1.is(Blocks.CARVED_PUMPKIN.asItem())) {
/* 231 */       return false;
/*     */     }
/*     */     
/* 234 */     Vec3 $$2 = $$0.getViewVector(1.0F).normalize();
/* 235 */     Vec3 $$3 = new Vec3(getX() - $$0.getX(), getEyeY() - $$0.getEyeY(), getZ() - $$0.getZ());
/* 236 */     double $$4 = $$3.length();
/* 237 */     $$3 = $$3.normalize();
/* 238 */     double $$5 = $$2.dot($$3);
/* 239 */     if ($$5 > 1.0D - 0.025D / $$4) {
/* 240 */       return $$0.hasLineOfSight((Entity)this);
/*     */     }
/* 242 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getStandingEyeHeight(Pose $$0, EntityDimensions $$1) {
/* 247 */     return 2.55F;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Vector3f getPassengerAttachmentPoint(Entity $$0, EntityDimensions $$1, float $$2) {
/* 252 */     return new Vector3f(0.0F, $$1.height - 0.09375F * $$2, 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public void aiStep() {
/* 257 */     if ((level()).isClientSide) {
/* 258 */       for (int $$0 = 0; $$0 < 2; $$0++) {
/* 259 */         level().addParticle((ParticleOptions)ParticleTypes.PORTAL, getRandomX(0.5D), getRandomY() - 0.25D, getRandomZ(0.5D), (this.random.nextDouble() - 0.5D) * 2.0D, -this.random.nextDouble(), (this.random.nextDouble() - 0.5D) * 2.0D);
/*     */       }
/*     */     }
/*     */     
/* 263 */     this.jumping = false;
/*     */     
/* 265 */     if (!(level()).isClientSide) {
/* 266 */       updatePersistentAnger((ServerLevel)level(), true);
/*     */     }
/* 268 */     super.aiStep();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSensitiveToWater() {
/* 273 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void customServerAiStep() {
/* 278 */     if (level().isDay() && this.tickCount >= this.targetChangeTime + 600) {
/* 279 */       float $$0 = getLightLevelDependentMagicValue();
/* 280 */       if ($$0 > 0.5F && 
/* 281 */         level().canSeeSky(blockPosition()) && this.random.nextFloat() * 30.0F < ($$0 - 0.4F) * 2.0F) {
/* 282 */         setTarget((LivingEntity)null);
/* 283 */         teleport();
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 288 */     super.customServerAiStep();
/*     */   }
/*     */   
/*     */   protected boolean teleport() {
/* 292 */     if (level().isClientSide() || !isAlive()) {
/* 293 */       return false;
/*     */     }
/*     */     
/* 296 */     double $$0 = getX() + (this.random.nextDouble() - 0.5D) * 64.0D;
/* 297 */     double $$1 = getY() + (this.random.nextInt(64) - 32);
/* 298 */     double $$2 = getZ() + (this.random.nextDouble() - 0.5D) * 64.0D;
/* 299 */     return teleport($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   boolean teleportTowards(Entity $$0) {
/* 303 */     Vec3 $$1 = new Vec3(getX() - $$0.getX(), getY(0.5D) - $$0.getEyeY(), getZ() - $$0.getZ());
/* 304 */     $$1 = $$1.normalize();
/* 305 */     double $$2 = 16.0D;
/* 306 */     double $$3 = getX() + (this.random.nextDouble() - 0.5D) * 8.0D - $$1.x * 16.0D;
/* 307 */     double $$4 = getY() + (this.random.nextInt(16) - 8) - $$1.y * 16.0D;
/* 308 */     double $$5 = getZ() + (this.random.nextDouble() - 0.5D) * 8.0D - $$1.z * 16.0D;
/* 309 */     return teleport($$3, $$4, $$5);
/*     */   }
/*     */   
/*     */   private boolean teleport(double $$0, double $$1, double $$2) {
/* 313 */     BlockPos.MutableBlockPos $$3 = new BlockPos.MutableBlockPos($$0, $$1, $$2);
/* 314 */     while ($$3.getY() > level().getMinBuildHeight() && !level().getBlockState((BlockPos)$$3).blocksMotion()) {
/* 315 */       $$3.move(Direction.DOWN);
/*     */     }
/* 317 */     BlockState $$4 = level().getBlockState((BlockPos)$$3);
/* 318 */     boolean $$5 = $$4.blocksMotion();
/* 319 */     boolean $$6 = $$4.getFluidState().is(FluidTags.WATER);
/* 320 */     if (!$$5 || $$6) {
/* 321 */       return false;
/*     */     }
/*     */     
/* 324 */     Vec3 $$7 = position();
/* 325 */     boolean $$8 = randomTeleport($$0, $$1, $$2, true);
/* 326 */     if ($$8) {
/* 327 */       level().gameEvent(GameEvent.TELEPORT, $$7, GameEvent.Context.of((Entity)this));
/*     */       
/* 329 */       if (!isSilent()) {
/* 330 */         level().playSound(null, this.xo, this.yo, this.zo, SoundEvents.ENDERMAN_TELEPORT, getSoundSource(), 1.0F, 1.0F);
/* 331 */         playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0F, 1.0F);
/*     */       } 
/*     */     } 
/*     */     
/* 335 */     return $$8;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/* 340 */     return isCreepy() ? SoundEvents.ENDERMAN_SCREAM : SoundEvents.ENDERMAN_AMBIENT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource $$0) {
/* 345 */     return SoundEvents.ENDERMAN_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/* 350 */     return SoundEvents.ENDERMAN_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void dropCustomDeathLoot(DamageSource $$0, int $$1, boolean $$2) {
/* 355 */     super.dropCustomDeathLoot($$0, $$1, $$2);
/* 356 */     BlockState $$3 = getCarriedBlock();
/* 357 */     if ($$3 != null) {
/* 358 */       ItemStack $$4 = new ItemStack((ItemLike)Items.DIAMOND_AXE);
/* 359 */       $$4.enchant(Enchantments.SILK_TOUCH, 1);
/*     */ 
/*     */ 
/*     */       
/* 363 */       LootParams.Builder $$5 = (new LootParams.Builder((ServerLevel)level())).withParameter(LootContextParams.ORIGIN, position()).withParameter(LootContextParams.TOOL, $$4).withOptionalParameter(LootContextParams.THIS_ENTITY, this);
/* 364 */       List<ItemStack> $$6 = $$3.getDrops($$5);
/* 365 */       for (ItemStack $$7 : $$6) {
/* 366 */         spawnAtLocation($$7);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setCarriedBlock(@Nullable BlockState $$0) {
/* 372 */     this.entityData.set(DATA_CARRY_STATE, Optional.ofNullable($$0));
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public BlockState getCarriedBlock() {
/* 377 */     return ((Optional<BlockState>)this.entityData.get(DATA_CARRY_STATE)).orElse(null);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hurt(DamageSource $$0, float $$1) {
/* 382 */     if (isInvulnerableTo($$0)) {
/* 383 */       return false;
/*     */     }
/*     */     
/* 386 */     boolean $$2 = $$0.getDirectEntity() instanceof ThrownPotion;
/* 387 */     if ($$0.is(DamageTypeTags.IS_PROJECTILE) || $$2) {
/* 388 */       boolean $$3 = ($$2 && hurtWithCleanWater($$0, (ThrownPotion)$$0.getDirectEntity(), $$1));
/* 389 */       for (int $$4 = 0; $$4 < 64; $$4++) {
/* 390 */         if (teleport()) {
/* 391 */           return true;
/*     */         }
/*     */       } 
/* 394 */       return $$3;
/*     */     } 
/*     */     
/* 397 */     boolean $$5 = super.hurt($$0, $$1);
/* 398 */     if (!level().isClientSide() && !($$0.getEntity() instanceof LivingEntity) && this.random.nextInt(10) != 0) {
/* 399 */       teleport();
/*     */     }
/*     */     
/* 402 */     return $$5;
/*     */   }
/*     */   
/*     */   private boolean hurtWithCleanWater(DamageSource $$0, ThrownPotion $$1, float $$2) {
/* 406 */     ItemStack $$3 = $$1.getItem();
/*     */     
/* 408 */     Potion $$4 = PotionUtils.getPotion($$3);
/* 409 */     List<MobEffectInstance> $$5 = PotionUtils.getMobEffects($$3);
/*     */     
/* 411 */     boolean $$6 = ($$4 == Potions.WATER && $$5.isEmpty());
/* 412 */     if ($$6) {
/* 413 */       return super.hurt($$0, $$2);
/*     */     }
/*     */     
/* 416 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isCreepy() {
/* 420 */     return ((Boolean)this.entityData.get(DATA_CREEPY)).booleanValue();
/*     */   }
/*     */   
/*     */   public boolean hasBeenStaredAt() {
/* 424 */     return ((Boolean)this.entityData.get(DATA_STARED_AT)).booleanValue();
/*     */   }
/*     */   
/*     */   public void setBeingStaredAt() {
/* 428 */     this.entityData.set(DATA_STARED_AT, Boolean.valueOf(true));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean requiresCustomPersistence() {
/* 433 */     return (super.requiresCustomPersistence() || getCarriedBlock() != null);
/*     */   }
/*     */   
/*     */   private static class EndermanLookForPlayerGoal
/*     */     extends NearestAttackableTargetGoal<Player> {
/*     */     private final EnderMan enderman;
/*     */     @Nullable
/*     */     private Player pendingTarget;
/*     */     private int aggroTime;
/*     */     private int teleportTime;
/*     */     private final TargetingConditions startAggroTargetConditions;
/* 444 */     private final TargetingConditions continueAggroTargetConditions = TargetingConditions.forCombat().ignoreLineOfSight();
/*     */     private final Predicate<LivingEntity> isAngerInducing;
/*     */     
/*     */     public EndermanLookForPlayerGoal(EnderMan $$0, @Nullable Predicate<LivingEntity> $$1) {
/* 448 */       super((Mob)$$0, Player.class, 10, false, false, $$1);
/* 449 */       this.enderman = $$0;
/* 450 */       this.isAngerInducing = ($$1 -> (($$0.isLookingAtMe((Player)$$1) || $$0.isAngryAt($$1)) && !$$0.hasIndirectPassenger((Entity)$$1)));
/*     */       
/* 452 */       this.startAggroTargetConditions = TargetingConditions.forCombat().range(getFollowDistance()).selector(this.isAngerInducing);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canUse() {
/* 457 */       this.pendingTarget = this.enderman.level().getNearestPlayer(this.startAggroTargetConditions, (LivingEntity)this.enderman);
/* 458 */       return (this.pendingTarget != null);
/*     */     }
/*     */ 
/*     */     
/*     */     public void start() {
/* 463 */       this.aggroTime = adjustedTickDelay(5);
/* 464 */       this.teleportTime = 0;
/* 465 */       this.enderman.setBeingStaredAt();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void stop() {
/* 471 */       this.pendingTarget = null;
/*     */       
/* 473 */       super.stop();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canContinueToUse() {
/* 478 */       if (this.pendingTarget != null) {
/* 479 */         if (!this.isAngerInducing.test(this.pendingTarget)) {
/* 480 */           return false;
/*     */         }
/* 482 */         this.enderman.lookAt((Entity)this.pendingTarget, 10.0F, 10.0F);
/* 483 */         return true;
/* 484 */       }  if (this.target != null) {
/* 485 */         if (this.enderman.hasIndirectPassenger((Entity)this.target))
/* 486 */           return false; 
/* 487 */         if (this.continueAggroTargetConditions.test((LivingEntity)this.enderman, this.target)) {
/* 488 */           return true;
/*     */         }
/*     */       } 
/* 491 */       return super.canContinueToUse();
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick() {
/* 496 */       if (this.enderman.getTarget() == null) {
/* 497 */         setTarget(null);
/*     */       }
/*     */       
/* 500 */       if (this.pendingTarget != null) {
/* 501 */         if (--this.aggroTime <= 0) {
/* 502 */           this.target = (LivingEntity)this.pendingTarget;
/* 503 */           this.pendingTarget = null;
/* 504 */           super.start();
/*     */         } 
/*     */       } else {
/* 507 */         if (this.target != null && !this.enderman.isPassenger()) {
/* 508 */           if (this.enderman.isLookingAtMe((Player)this.target)) {
/* 509 */             if (this.target.distanceToSqr((Entity)this.enderman) < 16.0D) {
/* 510 */               this.enderman.teleport();
/*     */             }
/* 512 */             this.teleportTime = 0;
/* 513 */           } else if (this.target.distanceToSqr((Entity)this.enderman) > 256.0D && 
/* 514 */             this.teleportTime++ >= adjustedTickDelay(30) && 
/* 515 */             this.enderman.teleportTowards((Entity)this.target)) {
/* 516 */             this.teleportTime = 0;
/*     */           } 
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 522 */         super.tick();
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private static class EndermanFreezeWhenLookedAt extends Goal {
/*     */     private final EnderMan enderman;
/*     */     @Nullable
/*     */     private LivingEntity target;
/*     */     
/*     */     public EndermanFreezeWhenLookedAt(EnderMan $$0) {
/* 533 */       this.enderman = $$0;
/* 534 */       setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canUse() {
/* 539 */       this.target = this.enderman.getTarget();
/* 540 */       if (!(this.target instanceof Player)) {
/* 541 */         return false;
/*     */       }
/* 543 */       double $$0 = this.target.distanceToSqr((Entity)this.enderman);
/* 544 */       if ($$0 > 256.0D) {
/* 545 */         return false;
/*     */       }
/* 547 */       return this.enderman.isLookingAtMe((Player)this.target);
/*     */     }
/*     */ 
/*     */     
/*     */     public void start() {
/* 552 */       this.enderman.getNavigation().stop();
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick() {
/* 557 */       this.enderman.getLookControl().setLookAt(this.target.getX(), this.target.getEyeY(), this.target.getZ());
/*     */     }
/*     */   }
/*     */   
/*     */   private static class EndermanLeaveBlockGoal extends Goal {
/*     */     private final EnderMan enderman;
/*     */     
/*     */     public EndermanLeaveBlockGoal(EnderMan $$0) {
/* 565 */       this.enderman = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canUse() {
/* 570 */       if (this.enderman.getCarriedBlock() == null) {
/* 571 */         return false;
/*     */       }
/* 573 */       if (!this.enderman.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
/* 574 */         return false;
/*     */       }
/* 576 */       return (this.enderman.getRandom().nextInt(reducedTickDelay(2000)) == 0);
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick() {
/* 581 */       RandomSource $$0 = this.enderman.getRandom();
/* 582 */       Level $$1 = this.enderman.level();
/*     */       
/* 584 */       int $$2 = Mth.floor(this.enderman.getX() - 1.0D + $$0.nextDouble() * 2.0D);
/* 585 */       int $$3 = Mth.floor(this.enderman.getY() + $$0.nextDouble() * 2.0D);
/* 586 */       int $$4 = Mth.floor(this.enderman.getZ() - 1.0D + $$0.nextDouble() * 2.0D);
/* 587 */       BlockPos $$5 = new BlockPos($$2, $$3, $$4);
/* 588 */       BlockState $$6 = $$1.getBlockState($$5);
/* 589 */       BlockPos $$7 = $$5.below();
/* 590 */       BlockState $$8 = $$1.getBlockState($$7);
/*     */       
/* 592 */       BlockState $$9 = this.enderman.getCarriedBlock();
/* 593 */       if ($$9 == null) {
/*     */         return;
/*     */       }
/*     */       
/* 597 */       $$9 = Block.updateFromNeighbourShapes($$9, (LevelAccessor)this.enderman.level(), $$5);
/* 598 */       if (canPlaceBlock($$1, $$5, $$9, $$6, $$8, $$7)) {
/* 599 */         $$1.setBlock($$5, $$9, 3);
/* 600 */         $$1.gameEvent(GameEvent.BLOCK_PLACE, $$5, GameEvent.Context.of((Entity)this.enderman, $$9));
/* 601 */         this.enderman.setCarriedBlock((BlockState)null);
/*     */       } 
/*     */     }
/*     */     
/*     */     private boolean canPlaceBlock(Level $$0, BlockPos $$1, BlockState $$2, BlockState $$3, BlockState $$4, BlockPos $$5) {
/* 606 */       return ($$3.isAir() && !$$4.isAir() && !$$4.is(Blocks.BEDROCK) && $$4.isCollisionShapeFullBlock((BlockGetter)$$0, $$5) && $$2.canSurvive((LevelReader)$$0, $$1) && $$0
/* 607 */         .getEntities((Entity)this.enderman, AABB.unitCubeFromLowerCorner(Vec3.atLowerCornerOf((Vec3i)$$1))).isEmpty());
/*     */     }
/*     */   }
/*     */   
/*     */   private static class EndermanTakeBlockGoal extends Goal {
/*     */     private final EnderMan enderman;
/*     */     
/*     */     public EndermanTakeBlockGoal(EnderMan $$0) {
/* 615 */       this.enderman = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canUse() {
/* 620 */       if (this.enderman.getCarriedBlock() != null) {
/* 621 */         return false;
/*     */       }
/* 623 */       if (!this.enderman.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
/* 624 */         return false;
/*     */       }
/* 626 */       return (this.enderman.getRandom().nextInt(reducedTickDelay(20)) == 0);
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick() {
/* 631 */       RandomSource $$0 = this.enderman.getRandom();
/* 632 */       Level $$1 = this.enderman.level();
/*     */       
/* 634 */       int $$2 = Mth.floor(this.enderman.getX() - 2.0D + $$0.nextDouble() * 4.0D);
/* 635 */       int $$3 = Mth.floor(this.enderman.getY() + $$0.nextDouble() * 3.0D);
/* 636 */       int $$4 = Mth.floor(this.enderman.getZ() - 2.0D + $$0.nextDouble() * 4.0D);
/* 637 */       BlockPos $$5 = new BlockPos($$2, $$3, $$4);
/* 638 */       BlockState $$6 = $$1.getBlockState($$5);
/*     */       
/* 640 */       Vec3 $$7 = new Vec3(this.enderman.getBlockX() + 0.5D, $$3 + 0.5D, this.enderman.getBlockZ() + 0.5D);
/* 641 */       Vec3 $$8 = new Vec3($$2 + 0.5D, $$3 + 0.5D, $$4 + 0.5D);
/* 642 */       BlockHitResult $$9 = $$1.clip(new ClipContext($$7, $$8, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, (Entity)this.enderman));
/* 643 */       boolean $$10 = $$9.getBlockPos().equals($$5);
/*     */       
/* 645 */       if ($$6.is(BlockTags.ENDERMAN_HOLDABLE) && $$10) {
/* 646 */         $$1.removeBlock($$5, false);
/* 647 */         $$1.gameEvent(GameEvent.BLOCK_DESTROY, $$5, GameEvent.Context.of((Entity)this.enderman, $$6));
/* 648 */         this.enderman.setCarriedBlock($$6.getBlock().defaultBlockState());
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\EnderMan.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */