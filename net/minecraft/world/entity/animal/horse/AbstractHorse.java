/*      */ package net.minecraft.world.entity.animal.horse;
/*      */ 
/*      */ import com.google.common.collect.UnmodifiableIterator;
/*      */ import java.util.UUID;
/*      */ import java.util.function.DoubleSupplier;
/*      */ import java.util.function.IntUnaryOperator;
/*      */ import java.util.function.Predicate;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.advancements.CriteriaTriggers;
/*      */ import net.minecraft.core.BlockPos;
/*      */ import net.minecraft.core.Direction;
/*      */ import net.minecraft.core.particles.ParticleOptions;
/*      */ import net.minecraft.core.particles.ParticleTypes;
/*      */ import net.minecraft.core.particles.SimpleParticleType;
/*      */ import net.minecraft.nbt.CompoundTag;
/*      */ import net.minecraft.nbt.Tag;
/*      */ import net.minecraft.network.syncher.EntityDataAccessor;
/*      */ import net.minecraft.network.syncher.EntityDataSerializers;
/*      */ import net.minecraft.network.syncher.SynchedEntityData;
/*      */ import net.minecraft.server.level.ServerLevel;
/*      */ import net.minecraft.server.level.ServerPlayer;
/*      */ import net.minecraft.server.players.OldUsersConverter;
/*      */ import net.minecraft.sounds.SoundEvent;
/*      */ import net.minecraft.sounds.SoundEvents;
/*      */ import net.minecraft.sounds.SoundSource;
/*      */ import net.minecraft.util.Mth;
/*      */ import net.minecraft.util.RandomSource;
/*      */ import net.minecraft.world.Container;
/*      */ import net.minecraft.world.ContainerListener;
/*      */ import net.minecraft.world.DifficultyInstance;
/*      */ import net.minecraft.world.InteractionHand;
/*      */ import net.minecraft.world.InteractionResult;
/*      */ import net.minecraft.world.SimpleContainer;
/*      */ import net.minecraft.world.damagesource.DamageSource;
/*      */ import net.minecraft.world.entity.AgeableMob;
/*      */ import net.minecraft.world.entity.Entity;
/*      */ import net.minecraft.world.entity.EntityDimensions;
/*      */ import net.minecraft.world.entity.EntityType;
/*      */ import net.minecraft.world.entity.EquipmentSlot;
/*      */ import net.minecraft.world.entity.HasCustomInventoryScreen;
/*      */ import net.minecraft.world.entity.HumanoidArm;
/*      */ import net.minecraft.world.entity.LivingEntity;
/*      */ import net.minecraft.world.entity.Mob;
/*      */ import net.minecraft.world.entity.MobSpawnType;
/*      */ import net.minecraft.world.entity.OwnableEntity;
/*      */ import net.minecraft.world.entity.PathfinderMob;
/*      */ import net.minecraft.world.entity.PlayerRideableJumping;
/*      */ import net.minecraft.world.entity.Pose;
/*      */ import net.minecraft.world.entity.Saddleable;
/*      */ import net.minecraft.world.entity.SlotAccess;
/*      */ import net.minecraft.world.entity.SpawnGroupData;
/*      */ import net.minecraft.world.entity.ai.attributes.Attribute;
/*      */ import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
/*      */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*      */ import net.minecraft.world.entity.ai.goal.BreedGoal;
/*      */ import net.minecraft.world.entity.ai.goal.FloatGoal;
/*      */ import net.minecraft.world.entity.ai.goal.FollowParentGoal;
/*      */ import net.minecraft.world.entity.ai.goal.Goal;
/*      */ import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
/*      */ import net.minecraft.world.entity.ai.goal.PanicGoal;
/*      */ import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
/*      */ import net.minecraft.world.entity.ai.goal.RandomStandGoal;
/*      */ import net.minecraft.world.entity.ai.goal.RunAroundLikeCrazyGoal;
/*      */ import net.minecraft.world.entity.ai.goal.TemptGoal;
/*      */ import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
/*      */ import net.minecraft.world.entity.ai.targeting.TargetingConditions;
/*      */ import net.minecraft.world.entity.animal.Animal;
/*      */ import net.minecraft.world.entity.player.Player;
/*      */ import net.minecraft.world.entity.vehicle.DismountHelper;
/*      */ import net.minecraft.world.item.ItemStack;
/*      */ import net.minecraft.world.item.Items;
/*      */ import net.minecraft.world.item.crafting.Ingredient;
/*      */ import net.minecraft.world.item.enchantment.EnchantmentHelper;
/*      */ import net.minecraft.world.level.CollisionGetter;
/*      */ import net.minecraft.world.level.EntityGetter;
/*      */ import net.minecraft.world.level.ItemLike;
/*      */ import net.minecraft.world.level.Level;
/*      */ import net.minecraft.world.level.ServerLevelAccessor;
/*      */ import net.minecraft.world.level.block.Blocks;
/*      */ import net.minecraft.world.level.block.SoundType;
/*      */ import net.minecraft.world.level.block.state.BlockState;
/*      */ import net.minecraft.world.level.gameevent.GameEvent;
/*      */ import net.minecraft.world.phys.AABB;
/*      */ import net.minecraft.world.phys.Vec2;
/*      */ import net.minecraft.world.phys.Vec3;
/*      */ import org.joml.Vector3f;
/*      */ 
/*      */ public abstract class AbstractHorse extends Animal implements ContainerListener, HasCustomInventoryScreen, OwnableEntity, PlayerRideableJumping, Saddleable {
/*      */   public static final int EQUIPMENT_SLOT_OFFSET = 400;
/*      */   public static final int CHEST_SLOT_OFFSET = 499;
/*      */   public static final int INVENTORY_SLOT_OFFSET = 500;
/*      */   public static final double BREEDING_CROSS_FACTOR = 0.15D;
/*   93 */   private static final float MIN_MOVEMENT_SPEED = (float)generateSpeed(() -> 0.0D);
/*   94 */   private static final float MAX_MOVEMENT_SPEED = (float)generateSpeed(() -> 1.0D);
/*   95 */   private static final float MIN_JUMP_STRENGTH = (float)generateJumpStrength(() -> 0.0D);
/*   96 */   private static final float MAX_JUMP_STRENGTH = (float)generateJumpStrength(() -> 1.0D); private static final float MAX_HEALTH; private static final float BACKWARDS_MOVE_SPEED_FACTOR = 0.25F;
/*   97 */   private static final float MIN_HEALTH = generateMaxHealth($$0 -> 0); private static final float SIDEWAYS_MOVE_SPEED_FACTOR = 0.5F; private static final Predicate<LivingEntity> PARENT_HORSE_SELECTOR; static {
/*   98 */     MAX_HEALTH = generateMaxHealth($$0 -> $$0 - 1);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  103 */     PARENT_HORSE_SELECTOR = ($$0 -> ($$0 instanceof AbstractHorse && ((AbstractHorse)$$0).isBred()));
/*  104 */   } private static final TargetingConditions MOMMY_TARGETING = TargetingConditions.forNonCombat().range(16.0D).ignoreLineOfSight().selector(PARENT_HORSE_SELECTOR);
/*      */   
/*  106 */   private static final Ingredient FOOD_ITEMS = Ingredient.of(new ItemLike[] { (ItemLike)Items.WHEAT, (ItemLike)Items.SUGAR, (ItemLike)Blocks.HAY_BLOCK.asItem(), (ItemLike)Items.APPLE, (ItemLike)Items.GOLDEN_CARROT, (ItemLike)Items.GOLDEN_APPLE, (ItemLike)Items.ENCHANTED_GOLDEN_APPLE });
/*      */   
/*  108 */   private static final EntityDataAccessor<Byte> DATA_ID_FLAGS = SynchedEntityData.defineId(AbstractHorse.class, EntityDataSerializers.BYTE);
/*      */   
/*      */   private static final int FLAG_TAME = 2;
/*      */   
/*      */   private static final int FLAG_SADDLE = 4;
/*      */   
/*      */   private static final int FLAG_BRED = 8;
/*      */   
/*      */   private static final int FLAG_EATING = 16;
/*      */   
/*      */   private static final int FLAG_STANDING = 32;
/*      */   private static final int FLAG_OPEN_MOUTH = 64;
/*      */   public static final int INV_SLOT_SADDLE = 0;
/*      */   public static final int INV_SLOT_ARMOR = 1;
/*      */   public static final int INV_BASE_COUNT = 2;
/*      */   private int eatingCounter;
/*      */   private int mouthCounter;
/*      */   private int standCounter;
/*      */   public int tailCounter;
/*      */   public int sprintCounter;
/*      */   protected boolean isJumping;
/*      */   protected SimpleContainer inventory;
/*      */   protected int temper;
/*      */   protected float playerJumpPendingScale;
/*      */   protected boolean allowStandSliding;
/*      */   private float eatAnim;
/*      */   private float eatAnimO;
/*      */   private float standAnim;
/*      */   private float standAnimO;
/*      */   private float mouthAnim;
/*      */   private float mouthAnimO;
/*      */   protected boolean canGallop = true;
/*      */   protected int gallopSoundCounter;
/*      */   @Nullable
/*      */   private UUID owner;
/*      */   
/*      */   protected AbstractHorse(EntityType<? extends AbstractHorse> $$0, Level $$1) {
/*  145 */     super($$0, $$1);
/*      */     
/*  147 */     setMaxUpStep(1.0F);
/*      */     
/*  149 */     createInventory();
/*      */   }
/*      */ 
/*      */   
/*      */   protected void registerGoals() {
/*  154 */     this.goalSelector.addGoal(1, (Goal)new PanicGoal((PathfinderMob)this, 1.2D));
/*  155 */     this.goalSelector.addGoal(1, (Goal)new RunAroundLikeCrazyGoal(this, 1.2D));
/*  156 */     this.goalSelector.addGoal(2, (Goal)new BreedGoal(this, 1.0D, AbstractHorse.class));
/*  157 */     this.goalSelector.addGoal(4, (Goal)new FollowParentGoal(this, 1.0D));
/*  158 */     this.goalSelector.addGoal(6, (Goal)new WaterAvoidingRandomStrollGoal((PathfinderMob)this, 0.7D));
/*  159 */     this.goalSelector.addGoal(7, (Goal)new LookAtPlayerGoal((Mob)this, Player.class, 6.0F));
/*  160 */     this.goalSelector.addGoal(8, (Goal)new RandomLookAroundGoal((Mob)this));
/*  161 */     if (canPerformRearing()) {
/*  162 */       this.goalSelector.addGoal(9, (Goal)new RandomStandGoal(this));
/*      */     }
/*      */     
/*  165 */     addBehaviourGoals();
/*      */   }
/*      */   
/*      */   protected void addBehaviourGoals() {
/*  169 */     this.goalSelector.addGoal(0, (Goal)new FloatGoal((Mob)this));
/*  170 */     this.goalSelector.addGoal(3, (Goal)new TemptGoal((PathfinderMob)this, 1.25D, Ingredient.of(new ItemLike[] { (ItemLike)Items.GOLDEN_CARROT, (ItemLike)Items.GOLDEN_APPLE, (ItemLike)Items.ENCHANTED_GOLDEN_APPLE }, ), false));
/*      */   }
/*      */ 
/*      */   
/*      */   protected void defineSynchedData() {
/*  175 */     super.defineSynchedData();
/*  176 */     this.entityData.define(DATA_ID_FLAGS, Byte.valueOf((byte)0));
/*      */   }
/*      */   
/*      */   protected boolean getFlag(int $$0) {
/*  180 */     return ((((Byte)this.entityData.get(DATA_ID_FLAGS)).byteValue() & $$0) != 0);
/*      */   }
/*      */   
/*      */   protected void setFlag(int $$0, boolean $$1) {
/*  184 */     byte $$2 = ((Byte)this.entityData.get(DATA_ID_FLAGS)).byteValue();
/*  185 */     if ($$1) {
/*  186 */       this.entityData.set(DATA_ID_FLAGS, Byte.valueOf((byte)($$2 | $$0)));
/*      */     } else {
/*  188 */       this.entityData.set(DATA_ID_FLAGS, Byte.valueOf((byte)($$2 & ($$0 ^ 0xFFFFFFFF))));
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean isTamed() {
/*  193 */     return getFlag(2);
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public UUID getOwnerUUID() {
/*  199 */     return this.owner;
/*      */   }
/*      */   
/*      */   public void setOwnerUUID(@Nullable UUID $$0) {
/*  203 */     this.owner = $$0;
/*      */   }
/*      */   
/*      */   public boolean isJumping() {
/*  207 */     return this.isJumping;
/*      */   }
/*      */   
/*      */   public void setTamed(boolean $$0) {
/*  211 */     setFlag(2, $$0);
/*      */   }
/*      */   
/*      */   public void setIsJumping(boolean $$0) {
/*  215 */     this.isJumping = $$0;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void onLeashDistance(float $$0) {
/*  220 */     if ($$0 > 6.0F && isEating()) {
/*  221 */       setEating(false);
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean isEating() {
/*  226 */     return getFlag(16);
/*      */   }
/*      */   
/*      */   public boolean isStanding() {
/*  230 */     return getFlag(32);
/*      */   }
/*      */   
/*      */   public boolean isBred() {
/*  234 */     return getFlag(8);
/*      */   }
/*      */   
/*      */   public void setBred(boolean $$0) {
/*  238 */     setFlag(8, $$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isSaddleable() {
/*  243 */     return (isAlive() && !isBaby() && isTamed());
/*      */   }
/*      */ 
/*      */   
/*      */   public void equipSaddle(@Nullable SoundSource $$0) {
/*  248 */     this.inventory.setItem(0, new ItemStack((ItemLike)Items.SADDLE));
/*      */   }
/*      */   
/*      */   public void equipArmor(Player $$0, ItemStack $$1) {
/*  252 */     if (isArmor($$1)) {
/*  253 */       this.inventory.setItem(1, $$1.copyWithCount(1));
/*  254 */       if (!($$0.getAbilities()).instabuild) {
/*  255 */         $$1.shrink(1);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isSaddled() {
/*  262 */     return getFlag(4);
/*      */   }
/*      */   
/*      */   public int getTemper() {
/*  266 */     return this.temper;
/*      */   }
/*      */   
/*      */   public void setTemper(int $$0) {
/*  270 */     this.temper = $$0;
/*      */   }
/*      */   
/*      */   public int modifyTemper(int $$0) {
/*  274 */     int $$1 = Mth.clamp(getTemper() + $$0, 0, getMaxTemper());
/*      */     
/*  276 */     setTemper($$1);
/*  277 */     return $$1;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isPushable() {
/*  282 */     return !isVehicle();
/*      */   }
/*      */   
/*      */   private void eating() {
/*  286 */     openMouth();
/*  287 */     if (!isSilent()) {
/*  288 */       SoundEvent $$0 = getEatingSound();
/*  289 */       if ($$0 != null) {
/*  290 */         level().playSound(null, getX(), getY(), getZ(), $$0, getSoundSource(), 1.0F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean causeFallDamage(float $$0, float $$1, DamageSource $$2) {
/*  297 */     if ($$0 > 1.0F) {
/*  298 */       playSound(SoundEvents.HORSE_LAND, 0.4F, 1.0F);
/*      */     }
/*      */     
/*  301 */     int $$3 = calculateFallDamage($$0, $$1);
/*  302 */     if ($$3 <= 0) {
/*  303 */       return false;
/*      */     }
/*      */     
/*  306 */     hurt($$2, $$3);
/*      */     
/*  308 */     if (isVehicle()) {
/*  309 */       for (Entity $$4 : getIndirectPassengers()) {
/*  310 */         $$4.hurt($$2, $$3);
/*      */       }
/*      */     }
/*      */     
/*  314 */     playBlockFallSound();
/*  315 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   protected int calculateFallDamage(float $$0, float $$1) {
/*  320 */     return Mth.ceil(($$0 * 0.5F - 3.0F) * $$1);
/*      */   }
/*      */   
/*      */   protected int getInventorySize() {
/*  324 */     return 2;
/*      */   }
/*      */   
/*      */   protected void createInventory() {
/*  328 */     SimpleContainer $$0 = this.inventory;
/*  329 */     this.inventory = new SimpleContainer(getInventorySize());
/*  330 */     if ($$0 != null) {
/*  331 */       $$0.removeListener(this);
/*      */       
/*  333 */       int $$1 = Math.min($$0.getContainerSize(), this.inventory.getContainerSize());
/*  334 */       for (int $$2 = 0; $$2 < $$1; $$2++) {
/*  335 */         ItemStack $$3 = $$0.getItem($$2);
/*  336 */         if (!$$3.isEmpty()) {
/*  337 */           this.inventory.setItem($$2, $$3.copy());
/*      */         }
/*      */       } 
/*      */     } 
/*  341 */     this.inventory.addListener(this);
/*  342 */     updateContainerEquipment();
/*      */   }
/*      */   
/*      */   protected void updateContainerEquipment() {
/*  346 */     if ((level()).isClientSide) {
/*      */       return;
/*      */     }
/*      */     
/*  350 */     setFlag(4, !this.inventory.getItem(0).isEmpty());
/*      */   }
/*      */ 
/*      */   
/*      */   public void containerChanged(Container $$0) {
/*  355 */     boolean $$1 = isSaddled();
/*  356 */     updateContainerEquipment();
/*  357 */     if (this.tickCount > 20 && !$$1 && isSaddled()) {
/*  358 */       playSound(getSaddleSoundEvent(), 0.5F, 1.0F);
/*      */     }
/*      */   }
/*      */   
/*      */   public double getCustomJump() {
/*  363 */     return getAttributeValue(Attributes.JUMP_STRENGTH);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hurt(DamageSource $$0, float $$1) {
/*  368 */     boolean $$2 = super.hurt($$0, $$1);
/*  369 */     if ($$2 && this.random.nextInt(3) == 0) {
/*  370 */       standIfPossible();
/*      */     }
/*  372 */     return $$2;
/*      */   }
/*      */   
/*      */   protected boolean canPerformRearing() {
/*  376 */     return true;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   protected SoundEvent getEatingSound() {
/*  381 */     return null;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   protected SoundEvent getAngrySound() {
/*  386 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void playStepSound(BlockPos $$0, BlockState $$1) {
/*  391 */     if ($$1.liquid()) {
/*      */       return;
/*      */     }
/*      */     
/*  395 */     BlockState $$2 = level().getBlockState($$0.above());
/*  396 */     SoundType $$3 = $$1.getSoundType();
/*  397 */     if ($$2.is(Blocks.SNOW)) {
/*  398 */       $$3 = $$2.getSoundType();
/*      */     }
/*      */     
/*  401 */     if (isVehicle() && this.canGallop) {
/*  402 */       this.gallopSoundCounter++;
/*  403 */       if (this.gallopSoundCounter > 5 && this.gallopSoundCounter % 3 == 0) {
/*  404 */         playGallopSound($$3);
/*  405 */       } else if (this.gallopSoundCounter <= 5) {
/*  406 */         playSound(SoundEvents.HORSE_STEP_WOOD, $$3.getVolume() * 0.15F, $$3.getPitch());
/*      */       } 
/*  408 */     } else if (isWoodSoundType($$3)) {
/*  409 */       playSound(SoundEvents.HORSE_STEP_WOOD, $$3.getVolume() * 0.15F, $$3.getPitch());
/*      */     } else {
/*  411 */       playSound(SoundEvents.HORSE_STEP, $$3.getVolume() * 0.15F, $$3.getPitch());
/*      */     } 
/*      */   }
/*      */   
/*      */   private boolean isWoodSoundType(SoundType $$0) {
/*  416 */     return ($$0 == SoundType.WOOD || $$0 == SoundType.NETHER_WOOD || $$0 == SoundType.STEM || $$0 == SoundType.CHERRY_WOOD || $$0 == SoundType.BAMBOO_WOOD);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void playGallopSound(SoundType $$0) {
/*  421 */     playSound(SoundEvents.HORSE_GALLOP, $$0.getVolume() * 0.15F, $$0.getPitch());
/*      */   }
/*      */   
/*      */   public static AttributeSupplier.Builder createBaseHorseAttributes() {
/*  425 */     return Mob.createMobAttributes()
/*  426 */       .add(Attributes.JUMP_STRENGTH)
/*  427 */       .add(Attributes.MAX_HEALTH, 53.0D)
/*  428 */       .add(Attributes.MOVEMENT_SPEED, 0.22499999403953552D);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getMaxSpawnClusterSize() {
/*  433 */     return 6;
/*      */   }
/*      */   
/*      */   public int getMaxTemper() {
/*  437 */     return 100;
/*      */   }
/*      */ 
/*      */   
/*      */   protected float getSoundVolume() {
/*  442 */     return 0.8F;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getAmbientSoundInterval() {
/*  447 */     return 400;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void openCustomInventoryScreen(Player $$0) {
/*  455 */     if (!(level()).isClientSide && (!isVehicle() || hasPassenger((Entity)$$0)) && isTamed()) {
/*  456 */       $$0.openHorseInventory(this, (Container)this.inventory);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public InteractionResult fedFood(Player $$0, ItemStack $$1) {
/*  462 */     boolean $$2 = handleEating($$0, $$1);
/*  463 */     if (($$2 & (!($$0.getAbilities()).instabuild ? 1 : 0)) != 0) {
/*  464 */       $$1.shrink(1);
/*      */     }
/*  466 */     if ((level()).isClientSide) {
/*  467 */       return InteractionResult.CONSUME;
/*      */     }
/*  469 */     return $$2 ? InteractionResult.SUCCESS : InteractionResult.PASS;
/*      */   }
/*      */   
/*      */   protected boolean handleEating(Player $$0, ItemStack $$1) {
/*  473 */     boolean $$2 = false;
/*  474 */     float $$3 = 0.0F;
/*  475 */     int $$4 = 0;
/*  476 */     int $$5 = 0;
/*      */     
/*  478 */     if ($$1.is(Items.WHEAT)) {
/*  479 */       $$3 = 2.0F;
/*  480 */       $$4 = 20;
/*  481 */       $$5 = 3;
/*  482 */     } else if ($$1.is(Items.SUGAR)) {
/*  483 */       $$3 = 1.0F;
/*  484 */       $$4 = 30;
/*  485 */       $$5 = 3;
/*  486 */     } else if ($$1.is(Blocks.HAY_BLOCK.asItem())) {
/*  487 */       $$3 = 20.0F;
/*  488 */       $$4 = 180;
/*  489 */     } else if ($$1.is(Items.APPLE)) {
/*  490 */       $$3 = 3.0F;
/*  491 */       $$4 = 60;
/*  492 */       $$5 = 3;
/*  493 */     } else if ($$1.is(Items.GOLDEN_CARROT)) {
/*  494 */       $$3 = 4.0F;
/*  495 */       $$4 = 60;
/*  496 */       $$5 = 5;
/*  497 */       if (!(level()).isClientSide && isTamed() && getAge() == 0 && !isInLove()) {
/*  498 */         $$2 = true;
/*  499 */         setInLove($$0);
/*      */       } 
/*  501 */     } else if ($$1.is(Items.GOLDEN_APPLE) || $$1.is(Items.ENCHANTED_GOLDEN_APPLE)) {
/*  502 */       $$3 = 10.0F;
/*  503 */       $$4 = 240;
/*  504 */       $$5 = 10;
/*  505 */       if (!(level()).isClientSide && isTamed() && getAge() == 0 && !isInLove()) {
/*  506 */         $$2 = true;
/*  507 */         setInLove($$0);
/*      */       } 
/*      */     } 
/*  510 */     if (getHealth() < getMaxHealth() && $$3 > 0.0F) {
/*  511 */       heal($$3);
/*  512 */       $$2 = true;
/*      */     } 
/*  514 */     if (isBaby() && $$4 > 0) {
/*  515 */       level().addParticle((ParticleOptions)ParticleTypes.HAPPY_VILLAGER, getRandomX(1.0D), getRandomY() + 0.5D, getRandomZ(1.0D), 0.0D, 0.0D, 0.0D);
/*  516 */       if (!(level()).isClientSide) {
/*  517 */         ageUp($$4);
/*  518 */         $$2 = true;
/*      */       } 
/*      */     } 
/*  521 */     if ($$5 > 0 && ($$2 || !isTamed()) && getTemper() < getMaxTemper() && 
/*  522 */       !(level()).isClientSide) {
/*  523 */       modifyTemper($$5);
/*  524 */       $$2 = true;
/*      */     } 
/*      */     
/*  527 */     if ($$2) {
/*  528 */       eating();
/*  529 */       gameEvent(GameEvent.EAT);
/*      */     } 
/*  531 */     return $$2;
/*      */   }
/*      */   
/*      */   protected void doPlayerRide(Player $$0) {
/*  535 */     setEating(false);
/*  536 */     setStanding(false);
/*  537 */     if (!(level()).isClientSide) {
/*  538 */       $$0.setYRot(getYRot());
/*  539 */       $$0.setXRot(getXRot());
/*  540 */       $$0.startRiding((Entity)this);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isImmobile() {
/*  546 */     return ((super.isImmobile() && isVehicle() && isSaddled()) || isEating() || isStanding());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isFood(ItemStack $$0) {
/*  553 */     return FOOD_ITEMS.test($$0);
/*      */   }
/*      */   
/*      */   private void moveTail() {
/*  557 */     this.tailCounter = 1;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void dropEquipment() {
/*  562 */     super.dropEquipment();
/*  563 */     if (this.inventory == null) {
/*      */       return;
/*      */     }
/*  566 */     for (int $$0 = 0; $$0 < this.inventory.getContainerSize(); $$0++) {
/*  567 */       ItemStack $$1 = this.inventory.getItem($$0);
/*  568 */       if (!$$1.isEmpty() && !EnchantmentHelper.hasVanishingCurse($$1))
/*      */       {
/*      */         
/*  571 */         spawnAtLocation($$1);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public void aiStep() {
/*  577 */     if (this.random.nextInt(200) == 0) {
/*  578 */       moveTail();
/*      */     }
/*      */     
/*  581 */     super.aiStep();
/*      */     
/*  583 */     if ((level()).isClientSide || !isAlive()) {
/*      */       return;
/*      */     }
/*      */     
/*  587 */     if (this.random.nextInt(900) == 0 && this.deathTime == 0) {
/*  588 */       heal(1.0F);
/*      */     }
/*      */     
/*  591 */     if (canEatGrass()) {
/*  592 */       if (!isEating() && !isVehicle() && this.random.nextInt(300) == 0 && 
/*  593 */         level().getBlockState(blockPosition().below()).is(Blocks.GRASS_BLOCK)) {
/*  594 */         setEating(true);
/*      */       }
/*      */ 
/*      */       
/*  598 */       if (isEating() && ++this.eatingCounter > 50) {
/*  599 */         this.eatingCounter = 0;
/*  600 */         setEating(false);
/*      */       } 
/*      */     } 
/*      */     
/*  604 */     followMommy();
/*      */   }
/*      */   
/*      */   protected void followMommy() {
/*  608 */     if (isBred() && isBaby() && !isEating()) {
/*  609 */       LivingEntity $$0 = level().getNearestEntity(AbstractHorse.class, MOMMY_TARGETING, (LivingEntity)this, getX(), getY(), getZ(), getBoundingBox().inflate(16.0D));
/*  610 */       if ($$0 != null && distanceToSqr((Entity)$$0) > 4.0D) {
/*  611 */         this.navigation.createPath((Entity)$$0, 0);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean canEatGrass() {
/*  617 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public void tick() {
/*  622 */     super.tick();
/*      */     
/*  624 */     if (this.mouthCounter > 0 && ++this.mouthCounter > 30) {
/*  625 */       this.mouthCounter = 0;
/*  626 */       setFlag(64, false);
/*      */     } 
/*      */     
/*  629 */     if (isEffectiveAi() && 
/*  630 */       this.standCounter > 0 && ++this.standCounter > 20) {
/*  631 */       this.standCounter = 0;
/*  632 */       setStanding(false);
/*      */     } 
/*      */ 
/*      */     
/*  636 */     if (this.tailCounter > 0 && ++this.tailCounter > 8) {
/*  637 */       this.tailCounter = 0;
/*      */     }
/*      */     
/*  640 */     if (this.sprintCounter > 0) {
/*  641 */       this.sprintCounter++;
/*      */       
/*  643 */       if (this.sprintCounter > 300) {
/*  644 */         this.sprintCounter = 0;
/*      */       }
/*      */     } 
/*      */     
/*  648 */     this.eatAnimO = this.eatAnim;
/*  649 */     if (isEating()) {
/*  650 */       this.eatAnim += (1.0F - this.eatAnim) * 0.4F + 0.05F;
/*  651 */       if (this.eatAnim > 1.0F) {
/*  652 */         this.eatAnim = 1.0F;
/*      */       }
/*      */     } else {
/*  655 */       this.eatAnim += (0.0F - this.eatAnim) * 0.4F - 0.05F;
/*  656 */       if (this.eatAnim < 0.0F) {
/*  657 */         this.eatAnim = 0.0F;
/*      */       }
/*      */     } 
/*  660 */     this.standAnimO = this.standAnim;
/*  661 */     if (isStanding()) {
/*      */       
/*  663 */       this.eatAnim = 0.0F;
/*  664 */       this.eatAnimO = this.eatAnim;
/*  665 */       this.standAnim += (1.0F - this.standAnim) * 0.4F + 0.05F;
/*  666 */       if (this.standAnim > 1.0F) {
/*  667 */         this.standAnim = 1.0F;
/*      */       }
/*      */     } else {
/*  670 */       this.allowStandSliding = false;
/*      */       
/*  672 */       this.standAnim += (0.8F * this.standAnim * this.standAnim * this.standAnim - this.standAnim) * 0.6F - 0.05F;
/*  673 */       if (this.standAnim < 0.0F) {
/*  674 */         this.standAnim = 0.0F;
/*      */       }
/*      */     } 
/*  677 */     this.mouthAnimO = this.mouthAnim;
/*  678 */     if (getFlag(64)) {
/*  679 */       this.mouthAnim += (1.0F - this.mouthAnim) * 0.7F + 0.05F;
/*  680 */       if (this.mouthAnim > 1.0F) {
/*  681 */         this.mouthAnim = 1.0F;
/*      */       }
/*      */     } else {
/*  684 */       this.mouthAnim += (0.0F - this.mouthAnim) * 0.7F - 0.05F;
/*  685 */       if (this.mouthAnim < 0.0F) {
/*  686 */         this.mouthAnim = 0.0F;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public InteractionResult mobInteract(Player $$0, InteractionHand $$1) {
/*  693 */     if (isVehicle() || isBaby()) {
/*  694 */       return super.mobInteract($$0, $$1);
/*      */     }
/*  696 */     if (isTamed() && $$0.isSecondaryUseActive()) {
/*  697 */       openCustomInventoryScreen($$0);
/*  698 */       return InteractionResult.sidedSuccess((level()).isClientSide);
/*      */     } 
/*      */     
/*  701 */     ItemStack $$2 = $$0.getItemInHand($$1);
/*  702 */     if (!$$2.isEmpty()) {
/*  703 */       InteractionResult $$3 = $$2.interactLivingEntity($$0, (LivingEntity)this, $$1);
/*  704 */       if ($$3.consumesAction()) {
/*  705 */         return $$3;
/*      */       }
/*      */       
/*  708 */       if (canWearArmor() && isArmor($$2) && !isWearingArmor()) {
/*  709 */         equipArmor($$0, $$2);
/*  710 */         return InteractionResult.sidedSuccess((level()).isClientSide);
/*      */       } 
/*      */     } 
/*      */     
/*  714 */     doPlayerRide($$0);
/*  715 */     return InteractionResult.sidedSuccess((level()).isClientSide);
/*      */   }
/*      */   
/*      */   private void openMouth() {
/*  719 */     if (!(level()).isClientSide) {
/*  720 */       this.mouthCounter = 1;
/*  721 */       setFlag(64, true);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setEating(boolean $$0) {
/*  726 */     setFlag(16, $$0);
/*      */   }
/*      */   
/*      */   public void setStanding(boolean $$0) {
/*  730 */     if ($$0) {
/*  731 */       setEating(false);
/*      */     }
/*  733 */     setFlag(32, $$0);
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public SoundEvent getAmbientStandSound() {
/*  738 */     return getAmbientSound();
/*      */   }
/*      */   
/*      */   public void standIfPossible() {
/*  742 */     if (canPerformRearing() && isEffectiveAi()) {
/*  743 */       this.standCounter = 1;
/*  744 */       setStanding(true);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void makeMad() {
/*  749 */     if (!isStanding()) {
/*  750 */       standIfPossible();
/*  751 */       SoundEvent $$0 = getAngrySound();
/*  752 */       if ($$0 != null) {
/*  753 */         playSound($$0, getSoundVolume(), getVoicePitch());
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean tameWithName(Player $$0) {
/*  759 */     setOwnerUUID($$0.getUUID());
/*  760 */     setTamed(true);
/*  761 */     if ($$0 instanceof ServerPlayer) {
/*  762 */       CriteriaTriggers.TAME_ANIMAL.trigger((ServerPlayer)$$0, this);
/*      */     }
/*  764 */     level().broadcastEntityEvent((Entity)this, (byte)7);
/*  765 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void tickRidden(Player $$0, Vec3 $$1) {
/*  770 */     super.tickRidden($$0, $$1);
/*  771 */     Vec2 $$2 = getRiddenRotation((LivingEntity)$$0);
/*  772 */     setRot($$2.y, $$2.x);
/*  773 */     this.yRotO = this.yBodyRot = this.yHeadRot = getYRot();
/*      */     
/*  775 */     if (isControlledByLocalInstance()) {
/*  776 */       if ($$1.z <= 0.0D) {
/*  777 */         this.gallopSoundCounter = 0;
/*      */       }
/*      */       
/*  780 */       if (onGround()) {
/*  781 */         setIsJumping(false);
/*  782 */         if (this.playerJumpPendingScale > 0.0F && !isJumping()) {
/*  783 */           executeRidersJump(this.playerJumpPendingScale, $$1);
/*      */         }
/*  785 */         this.playerJumpPendingScale = 0.0F;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   protected Vec2 getRiddenRotation(LivingEntity $$0) {
/*  791 */     return new Vec2($$0.getXRot() * 0.5F, $$0.getYRot());
/*      */   }
/*      */ 
/*      */   
/*      */   protected Vec3 getRiddenInput(Player $$0, Vec3 $$1) {
/*  796 */     if (onGround() && this.playerJumpPendingScale == 0.0F && isStanding() && !this.allowStandSliding) {
/*  797 */       return Vec3.ZERO;
/*      */     }
/*      */     
/*  800 */     float $$2 = $$0.xxa * 0.5F;
/*      */     
/*  802 */     float $$3 = $$0.zza;
/*  803 */     if ($$3 <= 0.0F) {
/*  804 */       $$3 *= 0.25F;
/*      */     }
/*      */     
/*  807 */     return new Vec3($$2, 0.0D, $$3);
/*      */   }
/*      */ 
/*      */   
/*      */   protected float getRiddenSpeed(Player $$0) {
/*  812 */     return (float)getAttributeValue(Attributes.MOVEMENT_SPEED);
/*      */   }
/*      */   
/*      */   protected void executeRidersJump(float $$0, Vec3 $$1) {
/*  816 */     double $$2 = getCustomJump() * $$0 * getBlockJumpFactor();
/*      */     
/*  818 */     double $$3 = $$2 + getJumpBoostPower();
/*  819 */     Vec3 $$4 = getDeltaMovement();
/*  820 */     setDeltaMovement($$4.x, $$3, $$4.z);
/*      */     
/*  822 */     setIsJumping(true);
/*  823 */     this.hasImpulse = true;
/*      */     
/*  825 */     if ($$1.z > 0.0D) {
/*  826 */       float $$5 = Mth.sin(getYRot() * 0.017453292F);
/*  827 */       float $$6 = Mth.cos(getYRot() * 0.017453292F);
/*      */       
/*  829 */       setDeltaMovement(getDeltaMovement().add((-0.4F * $$5 * $$0), 0.0D, (0.4F * $$6 * $$0)));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void playJumpSound() {
/*  838 */     playSound(SoundEvents.HORSE_JUMP, 0.4F, 1.0F);
/*      */   }
/*      */ 
/*      */   
/*      */   public void addAdditionalSaveData(CompoundTag $$0) {
/*  843 */     super.addAdditionalSaveData($$0);
/*      */     
/*  845 */     $$0.putBoolean("EatingHaystack", isEating());
/*  846 */     $$0.putBoolean("Bred", isBred());
/*  847 */     $$0.putInt("Temper", getTemper());
/*  848 */     $$0.putBoolean("Tame", isTamed());
/*      */     
/*  850 */     if (getOwnerUUID() != null) {
/*  851 */       $$0.putUUID("Owner", getOwnerUUID());
/*      */     }
/*      */     
/*  854 */     if (!this.inventory.getItem(0).isEmpty()) {
/*  855 */       $$0.put("SaddleItem", (Tag)this.inventory.getItem(0).save(new CompoundTag()));
/*      */     }
/*      */   }
/*      */   
/*      */   public void readAdditionalSaveData(CompoundTag $$0) {
/*      */     UUID $$3;
/*  861 */     super.readAdditionalSaveData($$0);
/*  862 */     setEating($$0.getBoolean("EatingHaystack"));
/*  863 */     setBred($$0.getBoolean("Bred"));
/*  864 */     setTemper($$0.getInt("Temper"));
/*  865 */     setTamed($$0.getBoolean("Tame"));
/*      */ 
/*      */     
/*  868 */     if ($$0.hasUUID("Owner")) {
/*  869 */       UUID $$1 = $$0.getUUID("Owner");
/*      */     } else {
/*  871 */       String $$2 = $$0.getString("Owner");
/*  872 */       $$3 = OldUsersConverter.convertMobOwnerIfNecessary(getServer(), $$2);
/*      */     } 
/*  874 */     if ($$3 != null) {
/*  875 */       setOwnerUUID($$3);
/*      */     }
/*      */     
/*  878 */     if ($$0.contains("SaddleItem", 10)) {
/*  879 */       ItemStack $$4 = ItemStack.of($$0.getCompound("SaddleItem"));
/*  880 */       if ($$4.is(Items.SADDLE)) {
/*  881 */         this.inventory.setItem(0, $$4);
/*      */       }
/*      */     } 
/*  884 */     updateContainerEquipment();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canMate(Animal $$0) {
/*  889 */     return false;
/*      */   }
/*      */   
/*      */   protected boolean canParent() {
/*  893 */     return (!isVehicle() && !isPassenger() && isTamed() && !isBaby() && getHealth() >= getMaxHealth() && isInLove());
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public AgeableMob getBreedOffspring(ServerLevel $$0, AgeableMob $$1) {
/*  899 */     return null;
/*      */   }
/*      */   
/*      */   protected void setOffspringAttributes(AgeableMob $$0, AbstractHorse $$1) {
/*  903 */     setOffspringAttribute($$0, $$1, Attributes.MAX_HEALTH, MIN_HEALTH, MAX_HEALTH);
/*  904 */     setOffspringAttribute($$0, $$1, Attributes.JUMP_STRENGTH, MIN_JUMP_STRENGTH, MAX_JUMP_STRENGTH);
/*  905 */     setOffspringAttribute($$0, $$1, Attributes.MOVEMENT_SPEED, MIN_MOVEMENT_SPEED, MAX_MOVEMENT_SPEED);
/*      */   }
/*      */   
/*      */   private void setOffspringAttribute(AgeableMob $$0, AbstractHorse $$1, Attribute $$2, double $$3, double $$4) {
/*  909 */     double $$5 = createOffspringAttribute(getAttributeBaseValue($$2), $$0.getAttributeBaseValue($$2), $$3, $$4, this.random);
/*  910 */     $$1.getAttribute($$2).setBaseValue($$5);
/*      */   }
/*      */   
/*      */   static double createOffspringAttribute(double $$0, double $$1, double $$2, double $$3, RandomSource $$4) {
/*  914 */     if ($$3 <= $$2) {
/*  915 */       throw new IllegalArgumentException("Incorrect range for an attribute");
/*      */     }
/*  917 */     $$0 = Mth.clamp($$0, $$2, $$3);
/*  918 */     $$1 = Mth.clamp($$1, $$2, $$3);
/*      */     
/*  920 */     double $$5 = 0.15D * ($$3 - $$2);
/*  921 */     double $$6 = Math.abs($$0 - $$1) + $$5 * 2.0D;
/*      */     
/*  923 */     double $$7 = ($$0 + $$1) / 2.0D;
/*  924 */     double $$8 = ($$4.nextDouble() + $$4.nextDouble() + $$4.nextDouble()) / 3.0D - 0.5D;
/*  925 */     double $$9 = $$7 + $$6 * $$8;
/*      */ 
/*      */     
/*  928 */     if ($$9 > $$3) {
/*  929 */       double $$10 = $$9 - $$3;
/*  930 */       return $$3 - $$10;
/*      */     } 
/*  932 */     if ($$9 < $$2) {
/*  933 */       double $$11 = $$2 - $$9;
/*  934 */       return $$2 + $$11;
/*      */     } 
/*  936 */     return $$9;
/*      */   }
/*      */   
/*      */   public float getEatAnim(float $$0) {
/*  940 */     return Mth.lerp($$0, this.eatAnimO, this.eatAnim);
/*      */   }
/*      */   
/*      */   public float getStandAnim(float $$0) {
/*  944 */     return Mth.lerp($$0, this.standAnimO, this.standAnim);
/*      */   }
/*      */   
/*      */   public float getMouthAnim(float $$0) {
/*  948 */     return Mth.lerp($$0, this.mouthAnimO, this.mouthAnim);
/*      */   }
/*      */ 
/*      */   
/*      */   public void onPlayerJump(int $$0) {
/*  953 */     if (!isSaddled()) {
/*      */       return;
/*      */     }
/*      */     
/*  957 */     if ($$0 < 0) {
/*  958 */       $$0 = 0;
/*      */     } else {
/*  960 */       this.allowStandSliding = true;
/*  961 */       standIfPossible();
/*      */     } 
/*      */     
/*  964 */     if ($$0 >= 90) {
/*  965 */       this.playerJumpPendingScale = 1.0F;
/*      */     } else {
/*  967 */       this.playerJumpPendingScale = 0.4F + 0.4F * $$0 / 90.0F;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canJump() {
/*  973 */     return isSaddled();
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleStartJump(int $$0) {
/*  978 */     this.allowStandSliding = true;
/*  979 */     standIfPossible();
/*  980 */     playJumpSound();
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleStopJump() {}
/*      */ 
/*      */   
/*      */   protected void spawnTamingParticles(boolean $$0) {
/*  988 */     SimpleParticleType simpleParticleType = $$0 ? ParticleTypes.HEART : ParticleTypes.SMOKE;
/*      */     
/*  990 */     for (int $$2 = 0; $$2 < 7; $$2++) {
/*  991 */       double $$3 = this.random.nextGaussian() * 0.02D;
/*  992 */       double $$4 = this.random.nextGaussian() * 0.02D;
/*  993 */       double $$5 = this.random.nextGaussian() * 0.02D;
/*  994 */       level().addParticle((ParticleOptions)simpleParticleType, getRandomX(1.0D), getRandomY() + 0.5D, getRandomZ(1.0D), $$3, $$4, $$5);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleEntityEvent(byte $$0) {
/* 1000 */     if ($$0 == 7) {
/* 1001 */       spawnTamingParticles(true);
/* 1002 */     } else if ($$0 == 6) {
/* 1003 */       spawnTamingParticles(false);
/*      */     } else {
/* 1005 */       super.handleEntityEvent($$0);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void positionRider(Entity $$0, Entity.MoveFunction $$1) {
/* 1011 */     super.positionRider($$0, $$1);
/* 1012 */     if ($$0 instanceof LivingEntity) {
/* 1013 */       ((LivingEntity)$$0).yBodyRot = this.yBodyRot;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected static float generateMaxHealth(IntUnaryOperator $$0) {
/* 1019 */     return 15.0F + $$0.applyAsInt(8) + $$0.applyAsInt(9);
/*      */   }
/*      */   
/*      */   protected static double generateJumpStrength(DoubleSupplier $$0) {
/* 1023 */     return 0.4000000059604645D + $$0.getAsDouble() * 0.2D + $$0.getAsDouble() * 0.2D + $$0.getAsDouble() * 0.2D;
/*      */   }
/*      */   
/*      */   protected static double generateSpeed(DoubleSupplier $$0) {
/* 1027 */     return (0.44999998807907104D + $$0.getAsDouble() * 0.3D + $$0.getAsDouble() * 0.3D + $$0.getAsDouble() * 0.3D) * 0.25D;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean onClimbable() {
/* 1032 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   protected float getStandingEyeHeight(Pose $$0, EntityDimensions $$1) {
/* 1037 */     return $$1.height * 0.95F;
/*      */   }
/*      */   
/*      */   public boolean canWearArmor() {
/* 1041 */     return false;
/*      */   }
/*      */   
/*      */   public boolean isWearingArmor() {
/* 1045 */     return !getItemBySlot(EquipmentSlot.CHEST).isEmpty();
/*      */   }
/*      */   
/*      */   public boolean isArmor(ItemStack $$0) {
/* 1049 */     return false;
/*      */   }
/*      */   
/*      */   private SlotAccess createEquipmentSlotAccess(final int slot, final Predicate<ItemStack> check) {
/* 1053 */     return new SlotAccess()
/*      */       {
/*      */         public ItemStack get() {
/* 1056 */           return AbstractHorse.this.inventory.getItem(slot);
/*      */         }
/*      */ 
/*      */         
/*      */         public boolean set(ItemStack $$0) {
/* 1061 */           if (!check.test($$0)) {
/* 1062 */             return false;
/*      */           }
/* 1064 */           AbstractHorse.this.inventory.setItem(slot, $$0);
/* 1065 */           AbstractHorse.this.updateContainerEquipment();
/* 1066 */           return true;
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */   
/*      */   public SlotAccess getSlot(int $$0) {
/* 1073 */     int $$1 = $$0 - 400;
/* 1074 */     if ($$1 >= 0 && $$1 < 2 && $$1 < this.inventory.getContainerSize()) {
/* 1075 */       if ($$1 == 0) {
/* 1076 */         return createEquipmentSlotAccess($$1, $$0 -> ($$0.isEmpty() || $$0.is(Items.SADDLE)));
/*      */       }
/* 1078 */       if ($$1 == 1) {
/* 1079 */         if (!canWearArmor()) {
/* 1080 */           return SlotAccess.NULL;
/*      */         }
/* 1082 */         return createEquipmentSlotAccess($$1, $$0 -> ($$0.isEmpty() || isArmor($$0)));
/*      */       } 
/*      */     } 
/* 1085 */     int $$2 = $$0 - 500 + 2;
/* 1086 */     if ($$2 >= 2 && $$2 < this.inventory.getContainerSize()) {
/* 1087 */       return SlotAccess.forContainer((Container)this.inventory, $$2);
/*      */     }
/* 1089 */     return super.getSlot($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public LivingEntity getControllingPassenger() {
/* 1095 */     if (isSaddled()) { Entity entity = getFirstPassenger(); if (entity instanceof Player) { Player $$0 = (Player)entity;
/* 1096 */         return (LivingEntity)$$0; }
/*      */        }
/* 1098 */      return super.getControllingPassenger();
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   private Vec3 getDismountLocationInDirection(Vec3 $$0, LivingEntity $$1) {
/* 1103 */     double $$2 = getX() + $$0.x;
/* 1104 */     double $$3 = (getBoundingBox()).minY;
/* 1105 */     double $$4 = getZ() + $$0.z;
/*      */     
/* 1107 */     BlockPos.MutableBlockPos $$5 = new BlockPos.MutableBlockPos(); UnmodifiableIterator<Pose> unmodifiableIterator;
/* 1108 */     label18: for (unmodifiableIterator = $$1.getDismountPoses().iterator(); unmodifiableIterator.hasNext(); ) { Pose $$6 = unmodifiableIterator.next();
/* 1109 */       $$5.set($$2, $$3, $$4);
/* 1110 */       double $$7 = (getBoundingBox()).maxY + 0.75D;
/*      */       
/*      */       while (true) {
/* 1113 */         double $$8 = level().getBlockFloorHeight((BlockPos)$$5);
/*      */         
/* 1115 */         if ($$5.getY() + $$8 > $$7) {
/*      */           continue label18;
/*      */         }
/*      */         
/* 1119 */         if (DismountHelper.isBlockFloorValid($$8)) {
/* 1120 */           AABB $$9 = $$1.getLocalBoundsForPose($$6);
/* 1121 */           Vec3 $$10 = new Vec3($$2, $$5.getY() + $$8, $$4);
/*      */           
/* 1123 */           if (DismountHelper.canDismountTo((CollisionGetter)level(), $$1, $$9.move($$10))) {
/* 1124 */             $$1.setPose($$6);
/* 1125 */             return $$10;
/*      */           } 
/*      */         } 
/*      */         
/* 1129 */         $$5.move(Direction.UP);
/* 1130 */         if ($$5.getY() >= $$7)
/*      */           continue label18; 
/*      */       }  }
/* 1133 */      return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public Vec3 getDismountLocationForPassenger(LivingEntity $$0) {
/* 1138 */     Vec3 $$1 = getCollisionHorizontalEscapeVector(getBbWidth(), $$0.getBbWidth(), getYRot() + (($$0.getMainArm() == HumanoidArm.RIGHT) ? 90.0F : -90.0F));
/* 1139 */     Vec3 $$2 = getDismountLocationInDirection($$1, $$0);
/*      */     
/* 1141 */     if ($$2 != null) {
/* 1142 */       return $$2;
/*      */     }
/*      */     
/* 1145 */     Vec3 $$3 = getCollisionHorizontalEscapeVector(getBbWidth(), $$0.getBbWidth(), getYRot() + (($$0.getMainArm() == HumanoidArm.LEFT) ? 90.0F : -90.0F));
/* 1146 */     Vec3 $$4 = getDismountLocationInDirection($$3, $$0);
/*      */     
/* 1148 */     if ($$4 != null) {
/* 1149 */       return $$4;
/*      */     }
/*      */     
/* 1152 */     return position();
/*      */   }
/*      */ 
/*      */   
/*      */   protected void randomizeAttributes(RandomSource $$0) {}
/*      */   
/*      */   @Nullable
/*      */   public SpawnGroupData finalizeSpawn(ServerLevelAccessor $$0, DifficultyInstance $$1, MobSpawnType $$2, @Nullable SpawnGroupData $$3, @Nullable CompoundTag $$4) {
/*      */     AgeableMob.AgeableMobGroupData ageableMobGroupData;
/* 1161 */     if ($$3 == null) {
/* 1162 */       ageableMobGroupData = new AgeableMob.AgeableMobGroupData(0.2F);
/*      */     }
/*      */     
/* 1165 */     randomizeAttributes($$0.getRandom());
/*      */     
/* 1167 */     return super.finalizeSpawn($$0, $$1, $$2, (SpawnGroupData)ageableMobGroupData, $$4);
/*      */   }
/*      */   
/*      */   public boolean hasInventoryChanged(Container $$0) {
/* 1171 */     return (this.inventory != $$0);
/*      */   }
/*      */   
/*      */   public int getAmbientStandInterval() {
/* 1175 */     return getAmbientSoundInterval();
/*      */   }
/*      */ 
/*      */   
/*      */   protected Vector3f getPassengerAttachmentPoint(Entity $$0, EntityDimensions $$1, float $$2) {
/* 1180 */     return new Vector3f(0.0F, 
/*      */         
/* 1182 */         getPassengersRidingOffsetY($$1, $$2) + 0.15F * this.standAnimO * $$2, -0.7F * this.standAnimO * $$2);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected float getPassengersRidingOffsetY(EntityDimensions $$0, float $$1) {
/* 1188 */     return $$0.height + (isBaby() ? 0.125F : -0.15625F) * $$1;
/*      */   }
/*      */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\animal\horse\AbstractHorse.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */