/*      */ package net.minecraft.world.entity.animal;
/*      */ import java.util.Comparator;
/*      */ import java.util.EnumSet;
/*      */ import java.util.List;
/*      */ import java.util.Optional;
/*      */ import java.util.UUID;
/*      */ import java.util.function.Predicate;
/*      */ import java.util.stream.Collectors;
/*      */ import java.util.stream.Stream;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.core.BlockPos;
/*      */ import net.minecraft.core.Holder;
/*      */ import net.minecraft.core.Position;
/*      */ import net.minecraft.core.Vec3i;
/*      */ import net.minecraft.core.particles.ParticleOptions;
/*      */ import net.minecraft.core.particles.ParticleTypes;
/*      */ import net.minecraft.nbt.CompoundTag;
/*      */ import net.minecraft.nbt.NbtUtils;
/*      */ import net.minecraft.nbt.Tag;
/*      */ import net.minecraft.network.protocol.game.DebugPackets;
/*      */ import net.minecraft.network.syncher.EntityDataAccessor;
/*      */ import net.minecraft.network.syncher.EntityDataSerializers;
/*      */ import net.minecraft.network.syncher.SynchedEntityData;
/*      */ import net.minecraft.server.level.ServerLevel;
/*      */ import net.minecraft.sounds.SoundEvent;
/*      */ import net.minecraft.sounds.SoundEvents;
/*      */ import net.minecraft.tags.BlockTags;
/*      */ import net.minecraft.tags.ItemTags;
/*      */ import net.minecraft.tags.PoiTypeTags;
/*      */ import net.minecraft.tags.TagKey;
/*      */ import net.minecraft.util.Mth;
/*      */ import net.minecraft.util.RandomSource;
/*      */ import net.minecraft.util.TimeUtil;
/*      */ import net.minecraft.util.VisibleForDebug;
/*      */ import net.minecraft.util.valueproviders.UniformInt;
/*      */ import net.minecraft.world.Difficulty;
/*      */ import net.minecraft.world.damagesource.DamageSource;
/*      */ import net.minecraft.world.effect.MobEffectInstance;
/*      */ import net.minecraft.world.effect.MobEffects;
/*      */ import net.minecraft.world.entity.AgeableMob;
/*      */ import net.minecraft.world.entity.Entity;
/*      */ import net.minecraft.world.entity.EntityDimensions;
/*      */ import net.minecraft.world.entity.EntityType;
/*      */ import net.minecraft.world.entity.LivingEntity;
/*      */ import net.minecraft.world.entity.Mob;
/*      */ import net.minecraft.world.entity.MobType;
/*      */ import net.minecraft.world.entity.NeutralMob;
/*      */ import net.minecraft.world.entity.PathfinderMob;
/*      */ import net.minecraft.world.entity.Pose;
/*      */ import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
/*      */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*      */ import net.minecraft.world.entity.ai.control.FlyingMoveControl;
/*      */ import net.minecraft.world.entity.ai.control.LookControl;
/*      */ import net.minecraft.world.entity.ai.control.MoveControl;
/*      */ import net.minecraft.world.entity.ai.goal.BreedGoal;
/*      */ import net.minecraft.world.entity.ai.goal.FloatGoal;
/*      */ import net.minecraft.world.entity.ai.goal.FollowParentGoal;
/*      */ import net.minecraft.world.entity.ai.goal.Goal;
/*      */ import net.minecraft.world.entity.ai.goal.GoalSelector;
/*      */ import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
/*      */ import net.minecraft.world.entity.ai.goal.TemptGoal;
/*      */ import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
/*      */ import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
/*      */ import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
/*      */ import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
/*      */ import net.minecraft.world.entity.ai.navigation.PathNavigation;
/*      */ import net.minecraft.world.entity.ai.util.AirRandomPos;
/*      */ import net.minecraft.world.entity.ai.util.HoverRandomPos;
/*      */ import net.minecraft.world.entity.ai.village.poi.PoiManager;
/*      */ import net.minecraft.world.entity.ai.village.poi.PoiRecord;
/*      */ import net.minecraft.world.entity.player.Player;
/*      */ import net.minecraft.world.item.ItemStack;
/*      */ import net.minecraft.world.item.crafting.Ingredient;
/*      */ import net.minecraft.world.level.Level;
/*      */ import net.minecraft.world.level.LevelReader;
/*      */ import net.minecraft.world.level.block.Block;
/*      */ import net.minecraft.world.level.block.Blocks;
/*      */ import net.minecraft.world.level.block.BonemealableBlock;
/*      */ import net.minecraft.world.level.block.CropBlock;
/*      */ import net.minecraft.world.level.block.DoublePlantBlock;
/*      */ import net.minecraft.world.level.block.StemBlock;
/*      */ import net.minecraft.world.level.block.SweetBerryBushBlock;
/*      */ import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
/*      */ import net.minecraft.world.level.block.entity.BlockEntity;
/*      */ import net.minecraft.world.level.block.state.BlockState;
/*      */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*      */ import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
/*      */ import net.minecraft.world.level.block.state.properties.Property;
/*      */ import net.minecraft.world.level.material.Fluid;
/*      */ import net.minecraft.world.level.pathfinder.BlockPathTypes;
/*      */ import net.minecraft.world.level.pathfinder.Path;
/*      */ import net.minecraft.world.phys.Vec3;
/*      */ 
/*      */ public class Bee extends Animal implements NeutralMob, FlyingAnimal {
/*      */   public static final float FLAP_DEGREES_PER_TICK = 120.32113F;
/*   96 */   public static final int TICKS_PER_FLAP = Mth.ceil(1.4959966F);
/*      */   
/*   98 */   private static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(Bee.class, EntityDataSerializers.BYTE);
/*   99 */   private static final EntityDataAccessor<Integer> DATA_REMAINING_ANGER_TIME = SynchedEntityData.defineId(Bee.class, EntityDataSerializers.INT);
/*      */   
/*      */   private static final int FLAG_ROLL = 2;
/*      */   
/*      */   private static final int FLAG_HAS_STUNG = 4;
/*      */   
/*      */   private static final int FLAG_HAS_NECTAR = 8;
/*      */   
/*      */   private static final int STING_DEATH_COUNTDOWN = 1200;
/*      */   
/*      */   private static final int TICKS_BEFORE_GOING_TO_KNOWN_FLOWER = 2400;
/*      */   
/*      */   private static final int TICKS_WITHOUT_NECTAR_BEFORE_GOING_HOME = 3600;
/*      */   
/*      */   private static final int MIN_ATTACK_DIST = 4;
/*      */   
/*      */   private static final int MAX_CROPS_GROWABLE = 10;
/*      */   
/*      */   private static final int POISON_SECONDS_NORMAL = 10;
/*      */   
/*      */   private static final int POISON_SECONDS_HARD = 18;
/*      */   
/*      */   private static final int TOO_FAR_DISTANCE = 32;
/*      */   
/*      */   private static final int HIVE_CLOSE_ENOUGH_DISTANCE = 2;
/*      */   private static final int PATHFIND_TO_HIVE_WHEN_CLOSER_THAN = 16;
/*      */   private static final int HIVE_SEARCH_DISTANCE = 20;
/*      */   public static final String TAG_CROPS_GROWN_SINCE_POLLINATION = "CropsGrownSincePollination";
/*      */   public static final String TAG_CANNOT_ENTER_HIVE_TICKS = "CannotEnterHiveTicks";
/*      */   public static final String TAG_TICKS_SINCE_POLLINATION = "TicksSincePollination";
/*      */   public static final String TAG_HAS_STUNG = "HasStung";
/*      */   public static final String TAG_HAS_NECTAR = "HasNectar";
/*      */   public static final String TAG_FLOWER_POS = "FlowerPos";
/*      */   public static final String TAG_HIVE_POS = "HivePos";
/*  133 */   private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   private UUID persistentAngerTarget;
/*      */ 
/*      */   
/*      */   private float rollAmount;
/*      */ 
/*      */   
/*      */   private float rollAmountO;
/*      */   
/*      */   private int timeSinceSting;
/*      */   
/*      */   int ticksWithoutNectarSinceExitingHive;
/*      */   
/*      */   private int stayOutOfHiveCountdown;
/*      */   
/*      */   private int numCropsGrownSincePollination;
/*      */   
/*      */   private static final int COOLDOWN_BEFORE_LOCATING_NEW_HIVE = 200;
/*      */   
/*      */   int remainingCooldownBeforeLocatingNewHive;
/*      */   
/*      */   private static final int COOLDOWN_BEFORE_LOCATING_NEW_FLOWER = 200;
/*      */   
/*  159 */   int remainingCooldownBeforeLocatingNewFlower = Mth.nextInt(this.random, 20, 60);
/*      */   
/*      */   @Nullable
/*      */   BlockPos savedFlowerPos;
/*      */   
/*      */   @Nullable
/*      */   BlockPos hivePos;
/*      */   
/*      */   BeePollinateGoal beePollinateGoal;
/*      */   
/*      */   BeeGoToHiveGoal goToHiveGoal;
/*      */   private BeeGoToKnownFlowerGoal goToKnownFlowerGoal;
/*      */   private int underWaterTicks;
/*      */   
/*      */   public Bee(EntityType<? extends Bee> $$0, Level $$1) {
/*  174 */     super((EntityType)$$0, $$1);
/*  175 */     this.moveControl = (MoveControl)new FlyingMoveControl((Mob)this, 20, true);
/*  176 */     this.lookControl = new BeeLookControl((Mob)this);
/*      */     
/*  178 */     setPathfindingMalus(BlockPathTypes.DANGER_FIRE, -1.0F);
/*  179 */     setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
/*  180 */     setPathfindingMalus(BlockPathTypes.WATER_BORDER, 16.0F);
/*  181 */     setPathfindingMalus(BlockPathTypes.COCOA, -1.0F);
/*  182 */     setPathfindingMalus(BlockPathTypes.FENCE, -1.0F);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void defineSynchedData() {
/*  187 */     super.defineSynchedData();
/*  188 */     this.entityData.define(DATA_FLAGS_ID, Byte.valueOf((byte)0));
/*  189 */     this.entityData.define(DATA_REMAINING_ANGER_TIME, Integer.valueOf(0));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float getWalkTargetValue(BlockPos $$0, LevelReader $$1) {
/*  195 */     if ($$1.getBlockState($$0).isAir()) {
/*  196 */       return 10.0F;
/*      */     }
/*  198 */     return 0.0F;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void registerGoals() {
/*  203 */     this.goalSelector.addGoal(0, (Goal)new BeeAttackGoal((PathfinderMob)this, 1.399999976158142D, true));
/*  204 */     this.goalSelector.addGoal(1, new BeeEnterHiveGoal());
/*  205 */     this.goalSelector.addGoal(2, (Goal)new BreedGoal(this, 1.0D));
/*  206 */     this.goalSelector.addGoal(3, (Goal)new TemptGoal((PathfinderMob)this, 1.25D, Ingredient.of(ItemTags.FLOWERS), false));
/*      */     
/*  208 */     this.beePollinateGoal = new BeePollinateGoal();
/*  209 */     this.goalSelector.addGoal(4, this.beePollinateGoal);
/*      */     
/*  211 */     this.goalSelector.addGoal(5, (Goal)new FollowParentGoal(this, 1.25D));
/*      */     
/*  213 */     this.goalSelector.addGoal(5, new BeeLocateHiveGoal());
/*      */     
/*  215 */     this.goToHiveGoal = new BeeGoToHiveGoal();
/*  216 */     this.goalSelector.addGoal(5, this.goToHiveGoal);
/*      */     
/*  218 */     this.goToKnownFlowerGoal = new BeeGoToKnownFlowerGoal();
/*  219 */     this.goalSelector.addGoal(6, this.goToKnownFlowerGoal);
/*      */     
/*  221 */     this.goalSelector.addGoal(7, new BeeGrowCropGoal());
/*  222 */     this.goalSelector.addGoal(8, new BeeWanderGoal());
/*  223 */     this.goalSelector.addGoal(9, (Goal)new FloatGoal((Mob)this));
/*      */     
/*  225 */     this.targetSelector.addGoal(1, (Goal)(new BeeHurtByOtherGoal(this)).setAlertOthers(new Class[0]));
/*  226 */     this.targetSelector.addGoal(2, (Goal)new BeeBecomeAngryTargetGoal(this));
/*  227 */     this.targetSelector.addGoal(3, (Goal)new ResetUniversalAngerTargetGoal((Mob)this, true));
/*      */   }
/*      */ 
/*      */   
/*      */   public void addAdditionalSaveData(CompoundTag $$0) {
/*  232 */     super.addAdditionalSaveData($$0);
/*      */     
/*  234 */     if (hasHive()) {
/*  235 */       $$0.put("HivePos", (Tag)NbtUtils.writeBlockPos(getHivePos()));
/*      */     }
/*  237 */     if (hasSavedFlowerPos()) {
/*  238 */       $$0.put("FlowerPos", (Tag)NbtUtils.writeBlockPos(getSavedFlowerPos()));
/*      */     }
/*  240 */     $$0.putBoolean("HasNectar", hasNectar());
/*  241 */     $$0.putBoolean("HasStung", hasStung());
/*  242 */     $$0.putInt("TicksSincePollination", this.ticksWithoutNectarSinceExitingHive);
/*  243 */     $$0.putInt("CannotEnterHiveTicks", this.stayOutOfHiveCountdown);
/*  244 */     $$0.putInt("CropsGrownSincePollination", this.numCropsGrownSincePollination);
/*      */     
/*  246 */     addPersistentAngerSaveData($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public void readAdditionalSaveData(CompoundTag $$0) {
/*  251 */     this.hivePos = null;
/*  252 */     if ($$0.contains("HivePos")) {
/*  253 */       this.hivePos = NbtUtils.readBlockPos($$0.getCompound("HivePos"));
/*      */     }
/*      */     
/*  256 */     this.savedFlowerPos = null;
/*  257 */     if ($$0.contains("FlowerPos")) {
/*  258 */       this.savedFlowerPos = NbtUtils.readBlockPos($$0.getCompound("FlowerPos"));
/*      */     }
/*      */     
/*  261 */     super.readAdditionalSaveData($$0);
/*  262 */     setHasNectar($$0.getBoolean("HasNectar"));
/*  263 */     setHasStung($$0.getBoolean("HasStung"));
/*  264 */     this.ticksWithoutNectarSinceExitingHive = $$0.getInt("TicksSincePollination");
/*  265 */     this.stayOutOfHiveCountdown = $$0.getInt("CannotEnterHiveTicks");
/*  266 */     this.numCropsGrownSincePollination = $$0.getInt("CropsGrownSincePollination");
/*      */     
/*  268 */     readPersistentAngerSaveData(level(), $$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean doHurtTarget(Entity $$0) {
/*  273 */     boolean $$1 = $$0.hurt(damageSources().sting((LivingEntity)this), (int)getAttributeValue(Attributes.ATTACK_DAMAGE));
/*  274 */     if ($$1) {
/*  275 */       doEnchantDamageEffects((LivingEntity)this, $$0);
/*  276 */       if ($$0 instanceof LivingEntity) {
/*  277 */         ((LivingEntity)$$0).setStingerCount(((LivingEntity)$$0).getStingerCount() + 1);
/*  278 */         int $$2 = 0;
/*  279 */         if (level().getDifficulty() == Difficulty.NORMAL) {
/*  280 */           $$2 = 10;
/*  281 */         } else if (level().getDifficulty() == Difficulty.HARD) {
/*  282 */           $$2 = 18;
/*      */         } 
/*      */         
/*  285 */         if ($$2 > 0) {
/*  286 */           ((LivingEntity)$$0).addEffect(new MobEffectInstance(MobEffects.POISON, $$2 * 20, 0), (Entity)this);
/*      */         }
/*      */       } 
/*  289 */       setHasStung(true);
/*  290 */       stopBeingAngry();
/*      */       
/*  292 */       playSound(SoundEvents.BEE_STING, 1.0F, 1.0F);
/*      */     } 
/*  294 */     return $$1;
/*      */   }
/*      */ 
/*      */   
/*      */   public void tick() {
/*  299 */     super.tick();
/*      */ 
/*      */     
/*  302 */     if (hasNectar() && getCropsGrownSincePollination() < 10 && this.random.nextFloat() < 0.05F) {
/*  303 */       for (int $$0 = 0; $$0 < this.random.nextInt(2) + 1; $$0++) {
/*  304 */         spawnFluidParticle(level(), getX() - 0.30000001192092896D, getX() + 0.30000001192092896D, getZ() - 0.30000001192092896D, getZ() + 0.30000001192092896D, getY(0.5D), (ParticleOptions)ParticleTypes.FALLING_NECTAR);
/*      */       }
/*      */     }
/*      */     
/*  308 */     updateRollAmount();
/*      */   }
/*      */   
/*      */   private void spawnFluidParticle(Level $$0, double $$1, double $$2, double $$3, double $$4, double $$5, ParticleOptions $$6) {
/*  312 */     $$0.addParticle($$6, Mth.lerp($$0.random.nextDouble(), $$1, $$2), $$5, Mth.lerp($$0.random.nextDouble(), $$3, $$4), 0.0D, 0.0D, 0.0D);
/*      */   }
/*      */   
/*      */   void pathfindRandomlyTowards(BlockPos $$0) {
/*  316 */     Vec3 $$1 = Vec3.atBottomCenterOf((Vec3i)$$0);
/*  317 */     int $$2 = 0;
/*  318 */     BlockPos $$3 = blockPosition();
/*  319 */     int $$4 = (int)$$1.y - $$3.getY();
/*  320 */     if ($$4 > 2) {
/*  321 */       $$2 = 4;
/*  322 */     } else if ($$4 < -2) {
/*  323 */       $$2 = -4;
/*      */     } 
/*      */     
/*  326 */     int $$5 = 6;
/*  327 */     int $$6 = 8;
/*  328 */     int $$7 = $$3.distManhattan((Vec3i)$$0);
/*  329 */     if ($$7 < 15) {
/*  330 */       $$5 = $$7 / 2;
/*  331 */       $$6 = $$7 / 2;
/*      */     } 
/*      */     
/*  334 */     Vec3 $$8 = AirRandomPos.getPosTowards((PathfinderMob)this, $$5, $$6, $$2, $$1, 0.3141592741012573D);
/*  335 */     if ($$8 == null) {
/*      */       return;
/*      */     }
/*      */     
/*  339 */     this.navigation.setMaxVisitedNodesMultiplier(0.5F);
/*  340 */     this.navigation.moveTo($$8.x, $$8.y, $$8.z, 1.0D);
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public BlockPos getSavedFlowerPos() {
/*  345 */     return this.savedFlowerPos;
/*      */   }
/*      */   
/*      */   public boolean hasSavedFlowerPos() {
/*  349 */     return (this.savedFlowerPos != null);
/*      */   }
/*      */   
/*      */   public void setSavedFlowerPos(BlockPos $$0) {
/*  353 */     this.savedFlowerPos = $$0;
/*      */   }
/*      */   
/*      */   @VisibleForDebug
/*      */   public int getTravellingTicks() {
/*  358 */     return Math.max(this.goToHiveGoal.travellingTicks, this.goToKnownFlowerGoal.travellingTicks);
/*      */   }
/*      */   
/*      */   @VisibleForDebug
/*      */   public List<BlockPos> getBlacklistedHives() {
/*  363 */     return this.goToHiveGoal.blacklistedTargets;
/*      */   }
/*      */   
/*      */   private boolean isTiredOfLookingForNectar() {
/*  367 */     return (this.ticksWithoutNectarSinceExitingHive > 3600);
/*      */   }
/*      */ 
/*      */   
/*      */   boolean wantsToEnterHive() {
/*  372 */     if (this.stayOutOfHiveCountdown > 0 || this.beePollinateGoal.isPollinating() || hasStung() || getTarget() != null) {
/*  373 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  377 */     boolean $$0 = (isTiredOfLookingForNectar() || level().isRaining() || level().isNight() || hasNectar());
/*      */ 
/*      */     
/*  380 */     return ($$0 && !isHiveNearFire());
/*      */   }
/*      */   
/*      */   public void setStayOutOfHiveCountdown(int $$0) {
/*  384 */     this.stayOutOfHiveCountdown = $$0;
/*      */   }
/*      */   
/*      */   public float getRollAmount(float $$0) {
/*  388 */     return Mth.lerp($$0, this.rollAmountO, this.rollAmount);
/*      */   }
/*      */   
/*      */   private void updateRollAmount() {
/*  392 */     this.rollAmountO = this.rollAmount;
/*  393 */     if (isRolling()) {
/*  394 */       this.rollAmount = Math.min(1.0F, this.rollAmount + 0.2F);
/*      */     } else {
/*  396 */       this.rollAmount = Math.max(0.0F, this.rollAmount - 0.24F);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void customServerAiStep() {
/*  402 */     boolean $$0 = hasStung();
/*      */     
/*  404 */     if (isInWaterOrBubble()) {
/*  405 */       this.underWaterTicks++;
/*      */     } else {
/*  407 */       this.underWaterTicks = 0;
/*      */     } 
/*      */     
/*  410 */     if (this.underWaterTicks > 20) {
/*  411 */       hurt(damageSources().drown(), 1.0F);
/*      */     }
/*      */     
/*  414 */     if ($$0) {
/*  415 */       this.timeSinceSting++;
/*      */ 
/*      */ 
/*      */       
/*  419 */       if (this.timeSinceSting % 5 == 0 && this.random.nextInt(Mth.clamp(1200 - this.timeSinceSting, 1, 1200)) == 0) {
/*  420 */         hurt(damageSources().generic(), getHealth());
/*      */       }
/*      */     } 
/*      */     
/*  424 */     if (!hasNectar()) {
/*  425 */       this.ticksWithoutNectarSinceExitingHive++;
/*      */     }
/*      */     
/*  428 */     if (!(level()).isClientSide) {
/*  429 */       updatePersistentAnger((ServerLevel)level(), false);
/*      */     }
/*      */   }
/*      */   
/*      */   public void resetTicksWithoutNectarSinceExitingHive() {
/*  434 */     this.ticksWithoutNectarSinceExitingHive = 0;
/*      */   }
/*      */   
/*      */   private boolean isHiveNearFire() {
/*  438 */     if (this.hivePos == null) {
/*  439 */       return false;
/*      */     }
/*  441 */     BlockEntity $$0 = level().getBlockEntity(this.hivePos);
/*  442 */     return ($$0 instanceof BeehiveBlockEntity && ((BeehiveBlockEntity)$$0).isFireNearby());
/*      */   }
/*      */ 
/*      */   
/*      */   public int getRemainingPersistentAngerTime() {
/*  447 */     return ((Integer)this.entityData.get(DATA_REMAINING_ANGER_TIME)).intValue();
/*      */   }
/*      */ 
/*      */   
/*      */   public void setRemainingPersistentAngerTime(int $$0) {
/*  452 */     this.entityData.set(DATA_REMAINING_ANGER_TIME, Integer.valueOf($$0));
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public UUID getPersistentAngerTarget() {
/*  458 */     return this.persistentAngerTarget;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setPersistentAngerTarget(@Nullable UUID $$0) {
/*  463 */     this.persistentAngerTarget = $$0;
/*      */   }
/*      */ 
/*      */   
/*      */   public void startPersistentAngerTimer() {
/*  468 */     setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
/*      */   }
/*      */   
/*      */   private boolean doesHiveHaveSpace(BlockPos $$0) {
/*  472 */     BlockEntity $$1 = level().getBlockEntity($$0);
/*  473 */     if ($$1 instanceof BeehiveBlockEntity) {
/*  474 */       return !((BeehiveBlockEntity)$$1).isFull();
/*      */     }
/*  476 */     return false;
/*      */   }
/*      */   
/*      */   @VisibleForDebug
/*      */   public boolean hasHive() {
/*  481 */     return (this.hivePos != null);
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   @VisibleForDebug
/*      */   public BlockPos getHivePos() {
/*  487 */     return this.hivePos;
/*      */   }
/*      */   
/*      */   @VisibleForDebug
/*      */   public GoalSelector getGoalSelector() {
/*  492 */     return this.goalSelector;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void sendDebugPackets() {
/*  497 */     super.sendDebugPackets();
/*      */     
/*  499 */     DebugPackets.sendBeeInfo(this);
/*      */   }
/*      */   
/*      */   int getCropsGrownSincePollination() {
/*  503 */     return this.numCropsGrownSincePollination;
/*      */   }
/*      */   
/*      */   private void resetNumCropsGrownSincePollination() {
/*  507 */     this.numCropsGrownSincePollination = 0;
/*      */   }
/*      */   
/*      */   void incrementNumCropsGrownSincePollination() {
/*  511 */     this.numCropsGrownSincePollination++;
/*      */   }
/*      */ 
/*      */   
/*      */   public void aiStep() {
/*  516 */     super.aiStep();
/*      */     
/*  518 */     if (!(level()).isClientSide) {
/*  519 */       if (this.stayOutOfHiveCountdown > 0) {
/*  520 */         this.stayOutOfHiveCountdown--;
/*      */       }
/*  522 */       if (this.remainingCooldownBeforeLocatingNewHive > 0) {
/*  523 */         this.remainingCooldownBeforeLocatingNewHive--;
/*      */       }
/*  525 */       if (this.remainingCooldownBeforeLocatingNewFlower > 0) {
/*  526 */         this.remainingCooldownBeforeLocatingNewFlower--;
/*      */       }
/*      */ 
/*      */       
/*  530 */       boolean $$0 = (isAngry() && !hasStung() && getTarget() != null && getTarget().distanceToSqr((Entity)this) < 4.0D);
/*  531 */       setRolling($$0);
/*      */       
/*  533 */       if (this.tickCount % 20 == 0)
/*      */       {
/*  535 */         if (!isHiveValid()) {
/*  536 */           this.hivePos = null;
/*      */         }
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   boolean isHiveValid() {
/*  543 */     if (!hasHive()) {
/*  544 */       return false;
/*      */     }
/*  546 */     if (isTooFarAway(this.hivePos)) {
/*  547 */       return false;
/*      */     }
/*  549 */     BlockEntity $$0 = level().getBlockEntity(this.hivePos);
/*  550 */     return ($$0 != null && $$0.getType() == BlockEntityType.BEEHIVE);
/*      */   }
/*      */   
/*      */   public boolean hasNectar() {
/*  554 */     return getFlag(8);
/*      */   }
/*      */   
/*      */   void setHasNectar(boolean $$0) {
/*  558 */     if ($$0) {
/*  559 */       resetTicksWithoutNectarSinceExitingHive();
/*      */     }
/*  561 */     setFlag(8, $$0);
/*      */   }
/*      */   
/*      */   public boolean hasStung() {
/*  565 */     return getFlag(4);
/*      */   }
/*      */   
/*      */   private void setHasStung(boolean $$0) {
/*  569 */     setFlag(4, $$0);
/*      */   }
/*      */   
/*      */   private boolean isRolling() {
/*  573 */     return getFlag(2);
/*      */   }
/*      */   
/*      */   private void setRolling(boolean $$0) {
/*  577 */     setFlag(2, $$0);
/*      */   }
/*      */   
/*      */   boolean isTooFarAway(BlockPos $$0) {
/*  581 */     return !closerThan($$0, 32);
/*      */   }
/*      */   
/*      */   private void setFlag(int $$0, boolean $$1) {
/*  585 */     if ($$1) {
/*  586 */       this.entityData.set(DATA_FLAGS_ID, Byte.valueOf((byte)(((Byte)this.entityData.get(DATA_FLAGS_ID)).byteValue() | $$0)));
/*      */     } else {
/*  588 */       this.entityData.set(DATA_FLAGS_ID, Byte.valueOf((byte)(((Byte)this.entityData.get(DATA_FLAGS_ID)).byteValue() & ($$0 ^ 0xFFFFFFFF))));
/*      */     } 
/*      */   }
/*      */   
/*      */   private boolean getFlag(int $$0) {
/*  593 */     return ((((Byte)this.entityData.get(DATA_FLAGS_ID)).byteValue() & $$0) != 0);
/*      */   }
/*      */   
/*      */   public static AttributeSupplier.Builder createAttributes() {
/*  597 */     return Mob.createMobAttributes()
/*  598 */       .add(Attributes.MAX_HEALTH, 10.0D)
/*  599 */       .add(Attributes.FLYING_SPEED, 0.6000000238418579D)
/*  600 */       .add(Attributes.MOVEMENT_SPEED, 0.30000001192092896D)
/*  601 */       .add(Attributes.ATTACK_DAMAGE, 2.0D)
/*  602 */       .add(Attributes.FOLLOW_RANGE, 48.0D);
/*      */   }
/*      */ 
/*      */   
/*      */   protected PathNavigation createNavigation(Level $$0) {
/*  607 */     FlyingPathNavigation $$1 = new FlyingPathNavigation((Mob)this, $$0)
/*      */       {
/*      */         public boolean isStableDestination(BlockPos $$0) {
/*  610 */           return !this.level.getBlockState($$0.below()).isAir();
/*      */         }
/*      */ 
/*      */         
/*      */         public void tick() {
/*  615 */           if (Bee.this.beePollinateGoal.isPollinating()) {
/*      */             return;
/*      */           }
/*      */           
/*  619 */           super.tick();
/*      */         }
/*      */       };
/*  622 */     $$1.setCanOpenDoors(false);
/*  623 */     $$1.setCanFloat(false);
/*  624 */     $$1.setCanPassDoors(true);
/*  625 */     return (PathNavigation)$$1;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isFood(ItemStack $$0) {
/*  630 */     return $$0.is(ItemTags.FLOWERS);
/*      */   }
/*      */   
/*      */   boolean isFlowerValid(BlockPos $$0) {
/*  634 */     return (level().isLoaded($$0) && level().getBlockState($$0).is(BlockTags.FLOWERS));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void playStepSound(BlockPos $$0, BlockState $$1) {}
/*      */ 
/*      */ 
/*      */   
/*      */   protected SoundEvent getAmbientSound() {
/*  644 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   protected SoundEvent getHurtSound(DamageSource $$0) {
/*  649 */     return SoundEvents.BEE_HURT;
/*      */   }
/*      */ 
/*      */   
/*      */   protected SoundEvent getDeathSound() {
/*  654 */     return SoundEvents.BEE_DEATH;
/*      */   }
/*      */ 
/*      */   
/*      */   protected float getSoundVolume() {
/*  659 */     return 0.4F;
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public Bee getBreedOffspring(ServerLevel $$0, AgeableMob $$1) {
/*  665 */     return (Bee)EntityType.BEE.create((Level)$$0);
/*      */   }
/*      */ 
/*      */   
/*      */   protected float getStandingEyeHeight(Pose $$0, EntityDimensions $$1) {
/*  670 */     if (isBaby()) {
/*  671 */       return $$1.height * 0.5F;
/*      */     }
/*  673 */     return $$1.height * 0.5F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void checkFallDamage(double $$0, boolean $$1, BlockState $$2, BlockPos $$3) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isFlapping() {
/*  685 */     return (isFlying() && this.tickCount % TICKS_PER_FLAP == 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isFlying() {
/*  690 */     return !onGround();
/*      */   }
/*      */   
/*      */   public void dropOffNectar() {
/*  694 */     setHasNectar(false);
/*  695 */     resetNumCropsGrownSincePollination();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hurt(DamageSource $$0, float $$1) {
/*  700 */     if (isInvulnerableTo($$0)) {
/*  701 */       return false;
/*      */     }
/*  703 */     if (!(level()).isClientSide) {
/*  704 */       this.beePollinateGoal.stopPollinating();
/*      */     }
/*  706 */     return super.hurt($$0, $$1);
/*      */   }
/*      */ 
/*      */   
/*      */   public MobType getMobType() {
/*  711 */     return MobType.ARTHROPOD;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void jumpInLiquid(TagKey<Fluid> $$0) {
/*  716 */     setDeltaMovement(getDeltaMovement().add(0.0D, 0.01D, 0.0D));
/*      */   }
/*      */ 
/*      */   
/*      */   public Vec3 getLeashOffset() {
/*  721 */     return new Vec3(0.0D, (0.5F * getEyeHeight()), (getBbWidth() * 0.2F));
/*      */   }
/*      */   
/*      */   boolean closerThan(BlockPos $$0, int $$1) {
/*  725 */     return $$0.closerThan((Vec3i)blockPosition(), $$1);
/*      */   }
/*      */   
/*      */   private class BeeHurtByOtherGoal extends HurtByTargetGoal {
/*      */     BeeHurtByOtherGoal(Bee $$0) {
/*  730 */       super((PathfinderMob)$$0, new Class[0]);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean canContinueToUse() {
/*  736 */       return (Bee.this.isAngry() && super.canContinueToUse());
/*      */     }
/*      */ 
/*      */     
/*      */     protected void alertOther(Mob $$0, LivingEntity $$1) {
/*  741 */       if ($$0 instanceof Bee && this.mob.hasLineOfSight((Entity)$$1))
/*  742 */         $$0.setTarget($$1); 
/*      */     }
/*      */   }
/*      */   
/*      */   private static class BeeBecomeAngryTargetGoal
/*      */     extends NearestAttackableTargetGoal<Player> {
/*      */     BeeBecomeAngryTargetGoal(Bee $$0) {
/*  749 */       super((Mob)$$0, Player.class, 10, true, false, $$0::isAngryAt);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean canUse() {
/*  754 */       return (beeCanTarget() && super.canUse());
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean canContinueToUse() {
/*  759 */       boolean $$0 = beeCanTarget();
/*  760 */       if (!$$0 || this.mob.getTarget() == null) {
/*  761 */         this.targetMob = null;
/*  762 */         return false;
/*      */       } 
/*  764 */       return super.canContinueToUse();
/*      */     }
/*      */     
/*      */     private boolean beeCanTarget() {
/*  768 */       Bee $$0 = (Bee)this.mob;
/*  769 */       return ($$0.isAngry() && !$$0.hasStung());
/*      */     }
/*      */   }
/*      */   
/*      */   private abstract class BaseBeeGoal
/*      */     extends Goal
/*      */   {
/*      */     public abstract boolean canBeeUse();
/*      */     
/*      */     public abstract boolean canBeeContinueToUse();
/*      */     
/*      */     public boolean canUse() {
/*  781 */       return (canBeeUse() && !Bee.this.isAngry());
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean canContinueToUse() {
/*  786 */       return (canBeeContinueToUse() && !Bee.this.isAngry());
/*      */     }
/*      */   }
/*      */   
/*      */   private class BeeWanderGoal
/*      */     extends Goal {
/*      */     private static final int WANDER_THRESHOLD = 22;
/*      */     
/*      */     BeeWanderGoal() {
/*  795 */       setFlags(EnumSet.of(Goal.Flag.MOVE));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean canUse() {
/*  800 */       return (Bee.this.navigation.isDone() && Bee.this.random.nextInt(10) == 0);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean canContinueToUse() {
/*  805 */       return Bee.this.navigation.isInProgress();
/*      */     }
/*      */ 
/*      */     
/*      */     public void start() {
/*  810 */       Vec3 $$0 = findPos();
/*  811 */       if ($$0 != null) {
/*  812 */         Bee.this.navigation.moveTo(Bee.this.navigation.createPath(BlockPos.containing((Position)$$0), 1), 1.0D);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     @Nullable
/*      */     private Vec3 findPos() {
/*      */       Vec3 $$2;
/*  820 */       if (Bee.this.isHiveValid() && !Bee.this.closerThan(Bee.this.hivePos, 22)) {
/*      */         
/*  822 */         Vec3 $$0 = Vec3.atCenterOf((Vec3i)Bee.this.hivePos);
/*  823 */         Vec3 $$1 = $$0.subtract(Bee.this.position()).normalize();
/*      */       } else {
/*  825 */         $$2 = Bee.this.getViewVector(0.0F);
/*      */       } 
/*      */       
/*  828 */       int $$3 = 8;
/*  829 */       Vec3 $$4 = HoverRandomPos.getPos((PathfinderMob)Bee.this, 8, 7, $$2.x, $$2.z, 1.5707964F, 3, 1);
/*  830 */       if ($$4 != null) {
/*  831 */         return $$4;
/*      */       }
/*      */ 
/*      */       
/*  835 */       return AirAndWaterRandomPos.getPos((PathfinderMob)Bee.this, 8, 4, -2, $$2.x, $$2.z, 1.5707963705062866D);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @VisibleForDebug
/*      */   public class BeeGoToHiveGoal
/*      */     extends BaseBeeGoal
/*      */   {
/*      */     public static final int MAX_TRAVELLING_TICKS = 600;
/*      */ 
/*      */     
/*  848 */     int travellingTicks = (Bee.this.level()).random.nextInt(10);
/*      */     
/*      */     private static final int MAX_BLACKLISTED_TARGETS = 3;
/*  851 */     final List<BlockPos> blacklistedTargets = Lists.newArrayList();
/*      */     
/*      */     @Nullable
/*      */     private Path lastPath;
/*      */     
/*      */     private static final int TICKS_BEFORE_HIVE_DROP = 60;
/*      */     private int ticksStuck;
/*      */     
/*      */     BeeGoToHiveGoal() {
/*  860 */       setFlags(EnumSet.of(Goal.Flag.MOVE));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean canBeeUse() {
/*  865 */       return (Bee.this.hivePos != null && 
/*  866 */         !Bee.this.hasRestriction() && Bee.this
/*  867 */         .wantsToEnterHive() && 
/*  868 */         !hasReachedTarget(Bee.this.hivePos) && Bee.this
/*  869 */         .level().getBlockState(Bee.this.hivePos).is(BlockTags.BEEHIVES));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean canBeeContinueToUse() {
/*  874 */       return canBeeUse();
/*      */     }
/*      */ 
/*      */     
/*      */     public void start() {
/*  879 */       this.travellingTicks = 0;
/*  880 */       this.ticksStuck = 0;
/*  881 */       super.start();
/*      */     }
/*      */ 
/*      */     
/*      */     public void stop() {
/*  886 */       this.travellingTicks = 0;
/*  887 */       this.ticksStuck = 0;
/*  888 */       Bee.this.navigation.stop();
/*  889 */       Bee.this.navigation.resetMaxVisitedNodesMultiplier();
/*      */     }
/*      */ 
/*      */     
/*      */     public void tick() {
/*  894 */       if (Bee.this.hivePos == null) {
/*      */         return;
/*      */       }
/*      */       
/*  898 */       this.travellingTicks++;
/*      */       
/*  900 */       if (this.travellingTicks > adjustedTickDelay(600)) {
/*      */         
/*  902 */         dropAndBlacklistHive();
/*      */         
/*      */         return;
/*      */       } 
/*  906 */       if (Bee.this.navigation.isInProgress()) {
/*      */         return;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  913 */       if (Bee.this.closerThan(Bee.this.hivePos, 16)) {
/*      */         
/*  915 */         boolean $$0 = pathfindDirectlyTowards(Bee.this.hivePos);
/*  916 */         if (!$$0) {
/*      */           
/*  918 */           dropAndBlacklistHive();
/*      */         }
/*  920 */         else if (this.lastPath != null && Bee.this.navigation.getPath().sameAs(this.lastPath)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  926 */           this.ticksStuck++;
/*      */           
/*  928 */           if (this.ticksStuck > 60) {
/*  929 */             dropHive();
/*  930 */             this.ticksStuck = 0;
/*      */           } 
/*      */         } else {
/*      */           
/*  934 */           this.lastPath = Bee.this.navigation.getPath();
/*      */         } 
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/*  940 */       if (Bee.this.isTooFarAway(Bee.this.hivePos)) {
/*      */         
/*  942 */         dropHive();
/*      */ 
/*      */         
/*      */         return;
/*      */       } 
/*      */ 
/*      */       
/*  949 */       Bee.this.pathfindRandomlyTowards(Bee.this.hivePos);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean pathfindDirectlyTowards(BlockPos $$0) {
/*  956 */       Bee.this.navigation.setMaxVisitedNodesMultiplier(10.0F);
/*  957 */       Bee.this.navigation.moveTo($$0.getX(), $$0.getY(), $$0.getZ(), 1.0D);
/*  958 */       return (Bee.this.navigation.getPath() != null && Bee.this.navigation.getPath().canReach());
/*      */     }
/*      */     
/*      */     boolean isTargetBlacklisted(BlockPos $$0) {
/*  962 */       return this.blacklistedTargets.contains($$0);
/*      */     }
/*      */     
/*      */     private void blacklistTarget(BlockPos $$0) {
/*  966 */       this.blacklistedTargets.add($$0);
/*  967 */       while (this.blacklistedTargets.size() > 3) {
/*  968 */         this.blacklistedTargets.remove(0);
/*      */       }
/*      */     }
/*      */     
/*      */     void clearBlacklist() {
/*  973 */       this.blacklistedTargets.clear();
/*      */     }
/*      */     
/*      */     private void dropAndBlacklistHive() {
/*  977 */       if (Bee.this.hivePos != null) {
/*  978 */         blacklistTarget(Bee.this.hivePos);
/*      */       }
/*  980 */       dropHive();
/*      */     }
/*      */     
/*      */     private void dropHive() {
/*  984 */       Bee.this.hivePos = null;
/*  985 */       Bee.this.remainingCooldownBeforeLocatingNewHive = 200;
/*      */     }
/*      */     
/*      */     private boolean hasReachedTarget(BlockPos $$0) {
/*  989 */       if (Bee.this.closerThan($$0, 2)) {
/*  990 */         return true;
/*      */       }
/*  992 */       Path $$1 = Bee.this.navigation.getPath();
/*  993 */       return ($$1 != null && $$1.getTarget().equals($$0) && $$1.canReach() && $$1.isDone());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public class BeeGoToKnownFlowerGoal
/*      */     extends BaseBeeGoal
/*      */   {
/*      */     private static final int MAX_TRAVELLING_TICKS = 600;
/*      */ 
/*      */     
/* 1006 */     int travellingTicks = (Bee.this.level()).random.nextInt(10);
/*      */     
/*      */     BeeGoToKnownFlowerGoal() {
/* 1009 */       setFlags(EnumSet.of(Goal.Flag.MOVE));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean canBeeUse() {
/* 1014 */       return (Bee.this.savedFlowerPos != null && 
/* 1015 */         !Bee.this.hasRestriction() && 
/* 1016 */         wantsToGoToKnownFlower() && Bee.this
/* 1017 */         .isFlowerValid(Bee.this.savedFlowerPos) && 
/* 1018 */         !Bee.this.closerThan(Bee.this.savedFlowerPos, 2));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean canBeeContinueToUse() {
/* 1023 */       return canBeeUse();
/*      */     }
/*      */ 
/*      */     
/*      */     public void start() {
/* 1028 */       this.travellingTicks = 0;
/* 1029 */       super.start();
/*      */     }
/*      */ 
/*      */     
/*      */     public void stop() {
/* 1034 */       this.travellingTicks = 0;
/* 1035 */       Bee.this.navigation.stop();
/* 1036 */       Bee.this.navigation.resetMaxVisitedNodesMultiplier();
/*      */     }
/*      */ 
/*      */     
/*      */     public void tick() {
/* 1041 */       if (Bee.this.savedFlowerPos == null) {
/*      */         return;
/*      */       }
/* 1044 */       this.travellingTicks++;
/*      */       
/* 1046 */       if (this.travellingTicks > adjustedTickDelay(600)) {
/*      */         
/* 1048 */         Bee.this.savedFlowerPos = null;
/*      */         
/*      */         return;
/*      */       } 
/* 1052 */       if (Bee.this.navigation.isInProgress()) {
/*      */         return;
/*      */       }
/*      */ 
/*      */       
/* 1057 */       if (Bee.this.isTooFarAway(Bee.this.savedFlowerPos)) {
/*      */         
/* 1059 */         Bee.this.savedFlowerPos = null;
/*      */ 
/*      */         
/*      */         return;
/*      */       } 
/*      */ 
/*      */       
/* 1066 */       Bee.this.pathfindRandomlyTowards(Bee.this.savedFlowerPos);
/*      */     }
/*      */     
/*      */     private boolean wantsToGoToKnownFlower() {
/* 1070 */       return (Bee.this.ticksWithoutNectarSinceExitingHive > 2400);
/*      */     }
/*      */   }
/*      */   
/*      */   private class BeeLookControl extends LookControl {
/*      */     BeeLookControl(Mob $$0) {
/* 1076 */       super($$0);
/*      */     }
/*      */ 
/*      */     
/*      */     public void tick() {
/* 1081 */       if (Bee.this.isAngry()) {
/*      */         return;
/*      */       }
/* 1084 */       super.tick();
/*      */     }
/*      */ 
/*      */     
/*      */     protected boolean resetXRotOnTick() {
/* 1089 */       return !Bee.this.beePollinateGoal.isPollinating();
/*      */     } }
/*      */   private class BeePollinateGoal extends BaseBeeGoal { private static final int MIN_POLLINATION_TICKS = 400;
/*      */     private static final int MIN_FIND_FLOWER_RETRY_COOLDOWN = 20;
/*      */     private static final int MAX_FIND_FLOWER_RETRY_COOLDOWN = 60;
/*      */     private final Predicate<BlockState> VALID_POLLINATION_BLOCKS;
/*      */     private static final double ARRIVAL_THRESHOLD = 0.1D;
/*      */     private static final int POSITION_CHANGE_CHANCE = 25;
/*      */     private static final float SPEED_MODIFIER = 0.35F;
/*      */     
/*      */     BeePollinateGoal() {
/* 1100 */       this.VALID_POLLINATION_BLOCKS = ($$0 -> 
/* 1101 */         ($$0.hasProperty((Property)BlockStateProperties.WATERLOGGED) && ((Boolean)$$0.getValue((Property)BlockStateProperties.WATERLOGGED)).booleanValue()) ? false : ($$0.is(BlockTags.FLOWERS) ? ($$0.is(Blocks.SUNFLOWER) ? (($$0.getValue((Property)DoublePlantBlock.HALF) == DoubleBlockHalf.UPPER)) : true) : false));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1130 */       setFlags(EnumSet.of(Goal.Flag.MOVE));
/*      */     }
/*      */     private static final float HOVER_HEIGHT_WITHIN_FLOWER = 0.6F; private static final float HOVER_POS_OFFSET = 0.33333334F; private int successfulPollinatingTicks; private int lastSoundPlayedTick; private boolean pollinating; @Nullable
/*      */     private Vec3 hoverPos; private int pollinatingTicks; private static final int MAX_POLLINATING_TICKS = 600;
/*      */     public boolean canBeeUse() {
/* 1135 */       if (Bee.this.remainingCooldownBeforeLocatingNewFlower > 0) {
/* 1136 */         return false;
/*      */       }
/*      */       
/* 1139 */       if (Bee.this.hasNectar()) {
/* 1140 */         return false;
/*      */       }
/* 1142 */       if (Bee.this.level().isRaining()) {
/* 1143 */         return false;
/*      */       }
/*      */       
/* 1146 */       Optional<BlockPos> $$0 = findNearbyFlower();
/* 1147 */       if ($$0.isPresent()) {
/* 1148 */         Bee.this.savedFlowerPos = $$0.get();
/*      */         
/* 1150 */         Bee.this.navigation.moveTo(Bee.this.savedFlowerPos.getX() + 0.5D, Bee.this.savedFlowerPos.getY() + 0.5D, Bee.this.savedFlowerPos.getZ() + 0.5D, 1.2000000476837158D);
/* 1151 */         return true;
/*      */       } 
/*      */ 
/*      */       
/* 1155 */       Bee.this.remainingCooldownBeforeLocatingNewFlower = Mth.nextInt(Bee.this.random, 20, 60);
/* 1156 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean canBeeContinueToUse() {
/* 1161 */       if (!this.pollinating) {
/* 1162 */         return false;
/*      */       }
/* 1164 */       if (!Bee.this.hasSavedFlowerPos()) {
/* 1165 */         return false;
/*      */       }
/* 1167 */       if (Bee.this.level().isRaining()) {
/* 1168 */         return false;
/*      */       }
/* 1170 */       if (hasPollinatedLongEnough()) {
/* 1171 */         return (Bee.this.random.nextFloat() < 0.2F);
/*      */       }
/*      */       
/* 1174 */       if (Bee.this.tickCount % 20 == 0 && !Bee.this.isFlowerValid(Bee.this.savedFlowerPos)) {
/* 1175 */         Bee.this.savedFlowerPos = null;
/* 1176 */         return false;
/*      */       } 
/* 1178 */       return true;
/*      */     }
/*      */     
/*      */     private boolean hasPollinatedLongEnough() {
/* 1182 */       return (this.successfulPollinatingTicks > 400);
/*      */     }
/*      */     
/*      */     boolean isPollinating() {
/* 1186 */       return this.pollinating;
/*      */     }
/*      */     
/*      */     void stopPollinating() {
/* 1190 */       this.pollinating = false;
/*      */     }
/*      */ 
/*      */     
/*      */     public void start() {
/* 1195 */       this.successfulPollinatingTicks = 0;
/* 1196 */       this.pollinatingTicks = 0;
/* 1197 */       this.lastSoundPlayedTick = 0;
/* 1198 */       this.pollinating = true;
/* 1199 */       Bee.this.resetTicksWithoutNectarSinceExitingHive();
/*      */     }
/*      */ 
/*      */     
/*      */     public void stop() {
/* 1204 */       if (hasPollinatedLongEnough()) {
/* 1205 */         Bee.this.setHasNectar(true);
/*      */       }
/* 1207 */       this.pollinating = false;
/* 1208 */       Bee.this.navigation.stop();
/*      */       
/* 1210 */       Bee.this.remainingCooldownBeforeLocatingNewFlower = 200;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean requiresUpdateEveryTick() {
/* 1215 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public void tick() {
/* 1220 */       this.pollinatingTicks++;
/* 1221 */       if (this.pollinatingTicks > 600) {
/*      */         
/* 1223 */         Bee.this.savedFlowerPos = null;
/*      */         
/*      */         return;
/*      */       } 
/* 1227 */       Vec3 $$0 = Vec3.atBottomCenterOf((Vec3i)Bee.this.savedFlowerPos).add(0.0D, 0.6000000238418579D, 0.0D);
/*      */       
/* 1229 */       if ($$0.distanceTo(Bee.this.position()) > 1.0D) {
/* 1230 */         this.hoverPos = $$0;
/* 1231 */         setWantedPos();
/*      */         
/*      */         return;
/*      */       } 
/* 1235 */       if (this.hoverPos == null) {
/* 1236 */         this.hoverPos = $$0;
/*      */       }
/*      */       
/* 1239 */       boolean $$1 = (Bee.this.position().distanceTo(this.hoverPos) <= 0.1D);
/* 1240 */       boolean $$2 = true;
/*      */       
/* 1242 */       if (!$$1 && this.pollinatingTicks > 600) {
/*      */         
/* 1244 */         Bee.this.savedFlowerPos = null;
/*      */         
/*      */         return;
/*      */       } 
/* 1248 */       if ($$1) {
/* 1249 */         boolean $$3 = (Bee.this.random.nextInt(25) == 0);
/* 1250 */         if ($$3) {
/* 1251 */           this.hoverPos = new Vec3($$0.x() + getOffset(), $$0.y(), $$0.z() + getOffset());
/*      */           
/* 1253 */           Bee.this.navigation.stop();
/*      */         } else {
/* 1255 */           $$2 = false;
/*      */         } 
/*      */         
/* 1258 */         Bee.this.getLookControl().setLookAt($$0.x(), $$0.y(), $$0.z());
/*      */       } 
/*      */       
/* 1261 */       if ($$2) {
/* 1262 */         setWantedPos();
/*      */       }
/*      */       
/* 1265 */       this.successfulPollinatingTicks++;
/*      */       
/* 1267 */       if (Bee.this.random.nextFloat() < 0.05F && this.successfulPollinatingTicks > this.lastSoundPlayedTick + 60) {
/* 1268 */         this.lastSoundPlayedTick = this.successfulPollinatingTicks;
/* 1269 */         Bee.this.playSound(SoundEvents.BEE_POLLINATE, 1.0F, 1.0F);
/*      */       } 
/*      */     }
/*      */     
/*      */     private void setWantedPos() {
/* 1274 */       Bee.this.getMoveControl().setWantedPosition(this.hoverPos.x(), this.hoverPos.y(), this.hoverPos.z(), 0.3499999940395355D);
/*      */     }
/*      */     
/*      */     private float getOffset() {
/* 1278 */       return (Bee.this.random.nextFloat() * 2.0F - 1.0F) * 0.33333334F;
/*      */     }
/*      */     
/*      */     private Optional<BlockPos> findNearbyFlower() {
/* 1282 */       return findNearestBlock(this.VALID_POLLINATION_BLOCKS, 5.0D);
/*      */     }
/*      */     
/*      */     private Optional<BlockPos> findNearestBlock(Predicate<BlockState> $$0, double $$1) {
/* 1286 */       BlockPos $$2 = Bee.this.blockPosition();
/*      */       
/* 1288 */       BlockPos.MutableBlockPos $$3 = new BlockPos.MutableBlockPos(); int $$4;
/* 1289 */       for ($$4 = 0; $$4 <= $$1; $$4 = ($$4 > 0) ? -$$4 : (1 - $$4)) {
/* 1290 */         for (int $$5 = 0; $$5 < $$1; $$5++) {
/* 1291 */           int $$6; for ($$6 = 0; $$6 <= $$5; $$6 = ($$6 > 0) ? -$$6 : (1 - $$6)) {
/*      */             
/* 1293 */             int $$7 = ($$6 < $$5 && $$6 > -$$5) ? $$5 : 0;
/* 1294 */             for (; $$7 <= $$5; $$7 = ($$7 > 0) ? -$$7 : (1 - $$7)) {
/* 1295 */               $$3.setWithOffset((Vec3i)$$2, $$6, $$4 - 1, $$7);
/* 1296 */               if ($$2.closerThan((Vec3i)$$3, $$1) && $$0.test(Bee.this.level().getBlockState((BlockPos)$$3))) {
/* 1297 */                 return (Optional)Optional.of($$3);
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1304 */       return Optional.empty();
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class BeeLocateHiveGoal
/*      */     extends BaseBeeGoal
/*      */   {
/*      */     public boolean canBeeUse() {
/* 1314 */       return (Bee.this.remainingCooldownBeforeLocatingNewHive == 0 && 
/* 1315 */         !Bee.this.hasHive() && Bee.this
/* 1316 */         .wantsToEnterHive());
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean canBeeContinueToUse() {
/* 1321 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     public void start() {
/* 1326 */       Bee.this.remainingCooldownBeforeLocatingNewHive = 200;
/*      */ 
/*      */       
/* 1329 */       List<BlockPos> $$0 = findNearbyHivesWithSpace();
/*      */       
/* 1331 */       if ($$0.isEmpty()) {
/*      */         return;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1337 */       for (BlockPos $$1 : $$0) {
/* 1338 */         if (!Bee.this.goToHiveGoal.isTargetBlacklisted($$1)) {
/*      */           
/* 1340 */           Bee.this.hivePos = $$1;
/*      */ 
/*      */           
/*      */           return;
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1348 */       Bee.this.goToHiveGoal.clearBlacklist();
/* 1349 */       Bee.this.hivePos = $$0.get(0);
/*      */     }
/*      */     
/*      */     private List<BlockPos> findNearbyHivesWithSpace() {
/* 1353 */       BlockPos $$0 = Bee.this.blockPosition();
/* 1354 */       PoiManager $$1 = ((ServerLevel)Bee.this.level()).getPoiManager();
/* 1355 */       Stream<PoiRecord> $$2 = $$1.getInRange($$0 -> $$0.is(PoiTypeTags.BEE_HOME), $$0, 20, PoiManager.Occupancy.ANY);
/* 1356 */       return (List<BlockPos>)$$2.map(PoiRecord::getPos)
/* 1357 */         .filter(Bee.this::doesHiveHaveSpace)
/* 1358 */         .sorted(Comparator.comparingDouble($$1 -> $$1.distSqr((Vec3i)$$0))).collect(Collectors.toList());
/*      */     }
/*      */   }
/*      */   
/*      */   private class BeeGrowCropGoal
/*      */     extends BaseBeeGoal {
/*      */     static final int GROW_CHANCE = 30;
/*      */     
/*      */     public boolean canBeeUse() {
/* 1367 */       if (Bee.this.getCropsGrownSincePollination() >= 10) {
/* 1368 */         return false;
/*      */       }
/*      */       
/* 1371 */       if (Bee.this.random.nextFloat() < 0.3F) {
/* 1372 */         return false;
/*      */       }
/*      */ 
/*      */       
/* 1376 */       return (Bee.this.hasNectar() && Bee.this.isHiveValid());
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean canBeeContinueToUse() {
/* 1381 */       return canBeeUse();
/*      */     }
/*      */ 
/*      */     
/*      */     public void tick() {
/* 1386 */       if (Bee.this.random.nextInt(adjustedTickDelay(30)) != 0) {
/*      */         return;
/*      */       }
/*      */       
/* 1390 */       for (int $$0 = 1; $$0 <= 2; $$0++) {
/* 1391 */         BlockPos $$1 = Bee.this.blockPosition().below($$0);
/* 1392 */         BlockState $$2 = Bee.this.level().getBlockState($$1);
/* 1393 */         Block $$3 = $$2.getBlock();
/* 1394 */         BlockState $$4 = null;
/* 1395 */         if ($$2.is(BlockTags.BEE_GROWABLES)) {
/* 1396 */           if ($$3 instanceof CropBlock) { CropBlock $$5 = (CropBlock)$$3;
/* 1397 */             if (!$$5.isMaxAge($$2)) {
/* 1398 */               $$4 = $$5.getStateForAge($$5.getAge($$2) + 1);
/*      */             } }
/* 1400 */           else if ($$3 instanceof StemBlock)
/* 1401 */           { int $$6 = ((Integer)$$2.getValue((Property)StemBlock.AGE)).intValue();
/* 1402 */             if ($$6 < 7) {
/* 1403 */               $$4 = (BlockState)$$2.setValue((Property)StemBlock.AGE, Integer.valueOf($$6 + 1));
/*      */             } }
/* 1405 */           else if ($$2.is(Blocks.SWEET_BERRY_BUSH))
/* 1406 */           { int $$7 = ((Integer)$$2.getValue((Property)SweetBerryBushBlock.AGE)).intValue();
/* 1407 */             if ($$7 < 3) {
/* 1408 */               $$4 = (BlockState)$$2.setValue((Property)SweetBerryBushBlock.AGE, Integer.valueOf($$7 + 1));
/*      */             } }
/* 1410 */           else if ($$2.is(Blocks.CAVE_VINES) || $$2.is(Blocks.CAVE_VINES_PLANT))
/* 1411 */           { ((BonemealableBlock)$$2.getBlock()).performBonemeal((ServerLevel)Bee.this.level(), Bee.this.random, $$1, $$2); }
/*      */ 
/*      */           
/* 1414 */           if ($$4 != null) {
/* 1415 */             Bee.this.level().levelEvent(2005, $$1, 0);
/* 1416 */             Bee.this.level().setBlockAndUpdate($$1, $$4);
/* 1417 */             Bee.this.incrementNumCropsGrownSincePollination();
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   private class BeeAttackGoal extends MeleeAttackGoal {
/*      */     BeeAttackGoal(PathfinderMob $$0, double $$1, boolean $$2) {
/* 1426 */       super($$0, $$1, $$2);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean canUse() {
/* 1431 */       return (super.canUse() && Bee.this.isAngry() && !Bee.this.hasStung());
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean canContinueToUse() {
/* 1436 */       return (super.canContinueToUse() && Bee.this.isAngry() && !Bee.this.hasStung());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private class BeeEnterHiveGoal
/*      */     extends BaseBeeGoal
/*      */   {
/*      */     public boolean canBeeUse() {
/* 1446 */       if (Bee.this.hasHive() && Bee.this.wantsToEnterHive() && Bee.this.hivePos.closerToCenterThan((Position)Bee.this.position(), 2.0D)) {
/* 1447 */         BlockEntity $$0 = Bee.this.level().getBlockEntity(Bee.this.hivePos);
/* 1448 */         if ($$0 instanceof BeehiveBlockEntity) { BeehiveBlockEntity $$1 = (BeehiveBlockEntity)$$0;
/* 1449 */           if ($$1.isFull()) {
/* 1450 */             Bee.this.hivePos = null;
/*      */           } else {
/* 1452 */             return true;
/*      */           }  }
/*      */       
/*      */       } 
/* 1456 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean canBeeContinueToUse() {
/* 1461 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     public void start() {
/* 1466 */       BlockEntity $$0 = Bee.this.level().getBlockEntity(Bee.this.hivePos);
/* 1467 */       if ($$0 instanceof BeehiveBlockEntity) { BeehiveBlockEntity $$1 = (BeehiveBlockEntity)$$0;
/* 1468 */         $$1.addOccupant((Entity)Bee.this, Bee.this.hasNectar()); }
/*      */     
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\animal\Bee.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */