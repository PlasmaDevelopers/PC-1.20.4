/*      */ package net.minecraft.world.entity;
/*      */ import com.google.common.collect.ImmutableList;
/*      */ import com.google.common.collect.ImmutableMap;
/*      */ import com.google.common.collect.Lists;
/*      */ import com.google.common.collect.Maps;
/*      */ import com.mojang.datafixers.util.Pair;
/*      */ import com.mojang.logging.LogUtils;
/*      */ import com.mojang.serialization.DataResult;
/*      */ import com.mojang.serialization.Dynamic;
/*      */ import com.mojang.serialization.DynamicOps;
/*      */ import java.util.Collection;
/*      */ import java.util.ConcurrentModificationException;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Objects;
/*      */ import java.util.Optional;
/*      */ import java.util.UUID;
/*      */ import java.util.function.Predicate;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.BlockUtil;
/*      */ import net.minecraft.advancements.CriteriaTriggers;
/*      */ import net.minecraft.commands.arguments.EntityAnchorArgument;
/*      */ import net.minecraft.core.BlockPos;
/*      */ import net.minecraft.core.Direction;
/*      */ import net.minecraft.core.Holder;
/*      */ import net.minecraft.core.NonNullList;
/*      */ import net.minecraft.core.Vec3i;
/*      */ import net.minecraft.core.particles.BlockParticleOption;
/*      */ import net.minecraft.core.particles.ItemParticleOption;
/*      */ import net.minecraft.core.particles.ParticleOptions;
/*      */ import net.minecraft.core.particles.ParticleTypes;
/*      */ import net.minecraft.nbt.CompoundTag;
/*      */ import net.minecraft.nbt.ListTag;
/*      */ import net.minecraft.nbt.NbtOps;
/*      */ import net.minecraft.nbt.Tag;
/*      */ import net.minecraft.network.protocol.Packet;
/*      */ import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundEntityEventPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundRemoveMobEffectPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundSetEquipmentPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundTakeItemEntityPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
/*      */ import net.minecraft.network.syncher.EntityDataAccessor;
/*      */ import net.minecraft.network.syncher.EntityDataSerializers;
/*      */ import net.minecraft.network.syncher.SynchedEntityData;
/*      */ import net.minecraft.resources.ResourceLocation;
/*      */ import net.minecraft.server.level.ServerChunkCache;
/*      */ import net.minecraft.server.level.ServerLevel;
/*      */ import net.minecraft.server.level.ServerPlayer;
/*      */ import net.minecraft.sounds.SoundEvent;
/*      */ import net.minecraft.sounds.SoundEvents;
/*      */ import net.minecraft.sounds.SoundSource;
/*      */ import net.minecraft.stats.Stats;
/*      */ import net.minecraft.tags.BlockTags;
/*      */ import net.minecraft.tags.DamageTypeTags;
/*      */ import net.minecraft.tags.EntityTypeTags;
/*      */ import net.minecraft.tags.FluidTags;
/*      */ import net.minecraft.tags.ItemTags;
/*      */ import net.minecraft.util.Mth;
/*      */ import net.minecraft.util.RandomSource;
/*      */ import net.minecraft.world.Difficulty;
/*      */ import net.minecraft.world.InteractionHand;
/*      */ import net.minecraft.world.damagesource.CombatRules;
/*      */ import net.minecraft.world.damagesource.CombatTracker;
/*      */ import net.minecraft.world.damagesource.DamageSource;
/*      */ import net.minecraft.world.effect.MobEffect;
/*      */ import net.minecraft.world.effect.MobEffectInstance;
/*      */ import net.minecraft.world.effect.MobEffectUtil;
/*      */ import net.minecraft.world.effect.MobEffects;
/*      */ import net.minecraft.world.entity.ai.Brain;
/*      */ import net.minecraft.world.entity.ai.attributes.Attribute;
/*      */ import net.minecraft.world.entity.ai.attributes.AttributeInstance;
/*      */ import net.minecraft.world.entity.ai.attributes.AttributeMap;
/*      */ import net.minecraft.world.entity.ai.attributes.AttributeModifier;
/*      */ import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
/*      */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*      */ import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
/*      */ import net.minecraft.world.entity.ai.targeting.TargetingConditions;
/*      */ import net.minecraft.world.entity.animal.Wolf;
/*      */ import net.minecraft.world.entity.item.ItemEntity;
/*      */ import net.minecraft.world.entity.player.Player;
/*      */ import net.minecraft.world.entity.projectile.AbstractArrow;
/*      */ import net.minecraft.world.food.FoodProperties;
/*      */ import net.minecraft.world.item.ElytraItem;
/*      */ import net.minecraft.world.item.Equipable;
/*      */ import net.minecraft.world.item.Item;
/*      */ import net.minecraft.world.item.ItemStack;
/*      */ import net.minecraft.world.item.Items;
/*      */ import net.minecraft.world.item.UseAnim;
/*      */ import net.minecraft.world.item.alchemy.PotionUtils;
/*      */ import net.minecraft.world.item.enchantment.EnchantmentHelper;
/*      */ import net.minecraft.world.item.enchantment.Enchantments;
/*      */ import net.minecraft.world.item.enchantment.FrostWalkerEnchantment;
/*      */ import net.minecraft.world.level.BlockGetter;
/*      */ import net.minecraft.world.level.ClipContext;
/*      */ import net.minecraft.world.level.CollisionGetter;
/*      */ import net.minecraft.world.level.GameRules;
/*      */ import net.minecraft.world.level.ItemLike;
/*      */ import net.minecraft.world.level.Level;
/*      */ import net.minecraft.world.level.LevelReader;
/*      */ import net.minecraft.world.level.block.BedBlock;
/*      */ import net.minecraft.world.level.block.Blocks;
/*      */ import net.minecraft.world.level.block.HoneyBlock;
/*      */ import net.minecraft.world.level.block.LadderBlock;
/*      */ import net.minecraft.world.level.block.PowderSnowBlock;
/*      */ import net.minecraft.world.level.block.SoundType;
/*      */ import net.minecraft.world.level.block.TrapDoorBlock;
/*      */ import net.minecraft.world.level.block.state.BlockState;
/*      */ import net.minecraft.world.level.block.state.properties.Property;
/*      */ import net.minecraft.world.level.entity.EntityTypeTest;
/*      */ import net.minecraft.world.level.gameevent.GameEvent;
/*      */ import net.minecraft.world.level.material.Fluid;
/*      */ import net.minecraft.world.level.material.FluidState;
/*      */ import net.minecraft.world.level.storage.loot.LootParams;
/*      */ import net.minecraft.world.level.storage.loot.LootTable;
/*      */ import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
/*      */ import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
/*      */ import net.minecraft.world.phys.AABB;
/*      */ import net.minecraft.world.phys.HitResult;
/*      */ import net.minecraft.world.phys.Vec3;
/*      */ import net.minecraft.world.scores.PlayerTeam;
/*      */ import org.slf4j.Logger;
/*      */ 
/*      */ public abstract class LivingEntity extends Entity implements Attackable {
/*  127 */   private static final Logger LOGGER = LogUtils.getLogger();
/*      */   
/*      */   private static final String TAG_ACTIVE_EFFECTS = "active_effects";
/*      */   
/*  131 */   private static final UUID SPEED_MODIFIER_SOUL_SPEED_UUID = UUID.fromString("87f46a96-686f-4796-b035-22e16ee9e038");
/*  132 */   private static final UUID SPEED_MODIFIER_POWDER_SNOW_UUID = UUID.fromString("1eaf83ff-7207-4596-b37a-d7a07b3ec4ce");
/*  133 */   private static final AttributeModifier SPEED_MODIFIER_SPRINTING = new AttributeModifier(UUID.fromString("662A6B8D-DA3E-4C1C-8813-96EA6097278D"), "Sprinting speed boost", 0.30000001192092896D, AttributeModifier.Operation.MULTIPLY_TOTAL);
/*      */   
/*      */   public static final int HAND_SLOTS = 2;
/*      */   
/*      */   public static final int ARMOR_SLOTS = 4;
/*      */   
/*      */   public static final int EQUIPMENT_SLOT_OFFSET = 98;
/*      */   
/*      */   public static final int ARMOR_SLOT_OFFSET = 100;
/*      */   
/*      */   public static final int SWING_DURATION = 6;
/*      */   
/*      */   public static final int PLAYER_HURT_EXPERIENCE_TIME = 100;
/*      */   private static final int DAMAGE_SOURCE_TIMEOUT = 40;
/*      */   public static final double MIN_MOVEMENT_DISTANCE = 0.003D;
/*      */   public static final double DEFAULT_BASE_GRAVITY = 0.08D;
/*      */   public static final int DEATH_DURATION = 20;
/*      */   private static final int WAIT_TICKS_BEFORE_ITEM_USE_EFFECTS = 7;
/*      */   private static final int TICKS_PER_ELYTRA_FREE_FALL_EVENT = 10;
/*      */   private static final int FREE_FALL_EVENTS_PER_ELYTRA_BREAK = 2;
/*      */   public static final int USE_ITEM_INTERVAL = 4;
/*      */   private static final float BASE_JUMP_POWER = 0.42F;
/*      */   private static final double MAX_LINE_OF_SIGHT_TEST_RANGE = 128.0D;
/*      */   protected static final int LIVING_ENTITY_FLAG_IS_USING = 1;
/*      */   protected static final int LIVING_ENTITY_FLAG_OFF_HAND = 2;
/*      */   protected static final int LIVING_ENTITY_FLAG_SPIN_ATTACK = 4;
/*  159 */   protected static final EntityDataAccessor<Byte> DATA_LIVING_ENTITY_FLAGS = SynchedEntityData.defineId(LivingEntity.class, EntityDataSerializers.BYTE);
/*  160 */   private static final EntityDataAccessor<Float> DATA_HEALTH_ID = SynchedEntityData.defineId(LivingEntity.class, EntityDataSerializers.FLOAT);
/*  161 */   private static final EntityDataAccessor<Integer> DATA_EFFECT_COLOR_ID = SynchedEntityData.defineId(LivingEntity.class, EntityDataSerializers.INT);
/*  162 */   private static final EntityDataAccessor<Boolean> DATA_EFFECT_AMBIENCE_ID = SynchedEntityData.defineId(LivingEntity.class, EntityDataSerializers.BOOLEAN);
/*  163 */   private static final EntityDataAccessor<Integer> DATA_ARROW_COUNT_ID = SynchedEntityData.defineId(LivingEntity.class, EntityDataSerializers.INT);
/*  164 */   private static final EntityDataAccessor<Integer> DATA_STINGER_COUNT_ID = SynchedEntityData.defineId(LivingEntity.class, EntityDataSerializers.INT);
/*  165 */   private static final EntityDataAccessor<Optional<BlockPos>> SLEEPING_POS_ID = SynchedEntityData.defineId(LivingEntity.class, EntityDataSerializers.OPTIONAL_BLOCK_POS);
/*      */   protected static final float DEFAULT_EYE_HEIGHT = 1.74F;
/*  167 */   protected static final EntityDimensions SLEEPING_DIMENSIONS = EntityDimensions.fixed(0.2F, 0.2F);
/*      */   
/*      */   public static final float EXTRA_RENDER_CULLING_SIZE_WITH_BIG_HAT = 0.5F;
/*      */   private final AttributeMap attributes;
/*  171 */   private final CombatTracker combatTracker = new CombatTracker(this);
/*  172 */   private final Map<MobEffect, MobEffectInstance> activeEffects = Maps.newHashMap();
/*  173 */   private final NonNullList<ItemStack> lastHandItemStacks = NonNullList.withSize(2, ItemStack.EMPTY);
/*  174 */   private final NonNullList<ItemStack> lastArmorItemStacks = NonNullList.withSize(4, ItemStack.EMPTY);
/*      */   public boolean swinging;
/*      */   private boolean discardFriction = false;
/*      */   public InteractionHand swingingArm;
/*      */   public int swingTime;
/*      */   public int removeArrowTime;
/*      */   public int removeStingerTime;
/*      */   public int hurtTime;
/*      */   public int hurtDuration;
/*      */   public int deathTime;
/*      */   public float oAttackAnim;
/*      */   public float attackAnim;
/*      */   protected int attackStrengthTicker;
/*  187 */   public final WalkAnimationState walkAnimation = new WalkAnimationState();
/*  188 */   public final int invulnerableDuration = 20;
/*      */   
/*      */   public final float timeOffs;
/*      */   
/*      */   public final float rotA;
/*      */   
/*      */   public float yBodyRot;
/*      */   public float yBodyRotO;
/*      */   public float yHeadRot;
/*      */   public float yHeadRotO;
/*      */   @Nullable
/*      */   protected Player lastHurtByPlayer;
/*      */   protected int lastHurtByPlayerTime;
/*      */   protected boolean dead;
/*      */   protected int noActionTime;
/*      */   protected float oRun;
/*      */   protected float run;
/*      */   protected float animStep;
/*      */   protected float animStepO;
/*      */   protected float rotOffs;
/*      */   protected int deathScore;
/*      */   protected float lastHurt;
/*      */   protected boolean jumping;
/*      */   public float xxa;
/*      */   public float yya;
/*      */   public float zza;
/*      */   protected int lerpSteps;
/*      */   protected double lerpX;
/*      */   protected double lerpY;
/*      */   protected double lerpZ;
/*      */   protected double lerpYRot;
/*      */   protected double lerpXRot;
/*      */   protected double lerpYHeadRot;
/*      */   protected int lerpHeadSteps;
/*      */   private boolean effectsDirty = true;
/*      */   @Nullable
/*      */   private LivingEntity lastHurtByMob;
/*      */   private int lastHurtByMobTimestamp;
/*      */   private LivingEntity lastHurtMob;
/*      */   private int lastHurtMobTimestamp;
/*      */   private float speed;
/*      */   private int noJumpDelay;
/*      */   private float absorptionAmount;
/*  231 */   protected ItemStack useItem = ItemStack.EMPTY;
/*      */   protected int useItemRemaining;
/*      */   protected int fallFlyTicks;
/*      */   private BlockPos lastPos;
/*  235 */   private Optional<BlockPos> lastClimbablePos = Optional.empty();
/*      */   @Nullable
/*      */   private DamageSource lastDamageSource;
/*      */   private long lastDamageStamp;
/*      */   protected int autoSpinAttackTicks;
/*      */   private float swimAmount;
/*      */   private float swimAmountO;
/*      */   protected Brain<?> brain;
/*      */   private boolean skipDropExperience;
/*      */   
/*      */   protected LivingEntity(EntityType<? extends LivingEntity> $$0, Level $$1) {
/*  246 */     super($$0, $$1);
/*      */     
/*  248 */     this.attributes = new AttributeMap(DefaultAttributes.getSupplier($$0));
/*  249 */     setHealth(getMaxHealth());
/*      */     
/*  251 */     this.blocksBuilding = true;
/*  252 */     this.rotA = (float)((Math.random() + 1.0D) * 0.009999999776482582D);
/*  253 */     reapplyPosition();
/*  254 */     this.timeOffs = (float)Math.random() * 12398.0F;
/*  255 */     setYRot((float)(Math.random() * 6.2831854820251465D));
/*  256 */     this.yHeadRot = getYRot();
/*  257 */     setMaxUpStep(0.6F);
/*      */     
/*  259 */     NbtOps $$2 = NbtOps.INSTANCE;
/*  260 */     this.brain = makeBrain(new Dynamic((DynamicOps)$$2, $$2.createMap((Map)ImmutableMap.of($$2.createString("memories"), $$2.emptyMap()))));
/*      */   }
/*      */   
/*      */   public Brain<?> getBrain() {
/*  264 */     return this.brain;
/*      */   }
/*      */   
/*      */   protected Brain.Provider<?> brainProvider() {
/*  268 */     return Brain.provider((Collection)ImmutableList.of(), (Collection)ImmutableList.of());
/*      */   }
/*      */   
/*      */   protected Brain<?> makeBrain(Dynamic<?> $$0) {
/*  272 */     return brainProvider().makeBrain($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public void kill() {
/*  277 */     hurt(damageSources().genericKill(), Float.MAX_VALUE);
/*      */   }
/*      */   
/*      */   public boolean canAttackType(EntityType<?> $$0) {
/*  281 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void defineSynchedData() {
/*  286 */     this.entityData.define(DATA_LIVING_ENTITY_FLAGS, Byte.valueOf((byte)0));
/*  287 */     this.entityData.define(DATA_EFFECT_COLOR_ID, Integer.valueOf(0));
/*  288 */     this.entityData.define(DATA_EFFECT_AMBIENCE_ID, Boolean.valueOf(false));
/*  289 */     this.entityData.define(DATA_ARROW_COUNT_ID, Integer.valueOf(0));
/*  290 */     this.entityData.define(DATA_STINGER_COUNT_ID, Integer.valueOf(0));
/*  291 */     this.entityData.define(DATA_HEALTH_ID, Float.valueOf(1.0F));
/*  292 */     this.entityData.define(SLEEPING_POS_ID, Optional.empty());
/*      */   }
/*      */   
/*      */   public static AttributeSupplier.Builder createLivingAttributes() {
/*  296 */     return AttributeSupplier.builder()
/*  297 */       .add(Attributes.MAX_HEALTH)
/*  298 */       .add(Attributes.KNOCKBACK_RESISTANCE)
/*  299 */       .add(Attributes.MOVEMENT_SPEED)
/*  300 */       .add(Attributes.ARMOR)
/*  301 */       .add(Attributes.ARMOR_TOUGHNESS)
/*  302 */       .add(Attributes.MAX_ABSORPTION);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void checkFallDamage(double $$0, boolean $$1, BlockState $$2, BlockPos $$3) {
/*  307 */     if (!isInWater())
/*      */     {
/*  309 */       updateInWaterStateAndDoWaterCurrentPushing();
/*      */     }
/*      */     
/*  312 */     if (!(level()).isClientSide && $$1 && this.fallDistance > 0.0F) {
/*      */ 
/*      */       
/*  315 */       removeSoulSpeed();
/*  316 */       tryAddSoulSpeed();
/*      */     } 
/*      */     
/*  319 */     if (!(level()).isClientSide && this.fallDistance > 3.0F && $$1 && !$$2.isAir()) {
/*  320 */       double $$4 = getX();
/*  321 */       double $$5 = getY();
/*  322 */       double $$6 = getZ();
/*      */       
/*  324 */       BlockPos $$7 = blockPosition();
/*  325 */       if ($$3.getX() != $$7.getX() || $$3.getZ() != $$7.getZ()) {
/*  326 */         double $$8 = $$4 - $$3.getX() - 0.5D;
/*  327 */         double $$9 = $$6 - $$3.getZ() - 0.5D;
/*  328 */         double $$10 = Math.max(Math.abs($$8), Math.abs($$9));
/*      */         
/*  330 */         $$4 = $$3.getX() + 0.5D + $$8 / $$10 * 0.5D;
/*  331 */         $$6 = $$3.getZ() + 0.5D + $$9 / $$10 * 0.5D;
/*      */       } 
/*  333 */       float $$11 = Mth.ceil(this.fallDistance - 3.0F);
/*      */       
/*  335 */       double $$12 = Math.min((0.2F + $$11 / 15.0F), 2.5D);
/*  336 */       int $$13 = (int)(150.0D * $$12);
/*  337 */       ((ServerLevel)level()).sendParticles((ParticleOptions)new BlockParticleOption(ParticleTypes.BLOCK, $$2), $$4, $$5, $$6, $$13, 0.0D, 0.0D, 0.0D, 0.15000000596046448D);
/*      */     } 
/*      */     
/*  340 */     super.checkFallDamage($$0, $$1, $$2, $$3);
/*      */     
/*  342 */     if ($$1) {
/*  343 */       this.lastClimbablePos = Optional.empty();
/*      */     }
/*      */   }
/*      */   
/*      */   public final boolean canBreatheUnderwater() {
/*  348 */     return getType().is(EntityTypeTags.CAN_BREATHE_UNDER_WATER);
/*      */   }
/*      */   
/*      */   public float getSwimAmount(float $$0) {
/*  352 */     return Mth.lerp($$0, this.swimAmountO, this.swimAmount);
/*      */   }
/*      */ 
/*      */   
/*      */   public void baseTick() {
/*  357 */     this.oAttackAnim = this.attackAnim;
/*      */     
/*  359 */     if (this.firstTick) {
/*  360 */       getSleepingPos().ifPresent(this::setPosToBed);
/*      */     }
/*      */ 
/*      */     
/*  364 */     if (canSpawnSoulSpeedParticle()) {
/*  365 */       spawnSoulSpeedParticle();
/*      */     }
/*      */     
/*  368 */     super.baseTick();
/*      */     
/*  370 */     level().getProfiler().push("livingEntityBaseTick");
/*      */     
/*  372 */     if (fireImmune() || (level()).isClientSide) {
/*  373 */       clearFire();
/*      */     }
/*      */     
/*  376 */     if (isAlive()) {
/*  377 */       boolean $$0 = this instanceof Player;
/*  378 */       if (!(level()).isClientSide) {
/*  379 */         if (isInWall()) {
/*  380 */           hurt(damageSources().inWall(), 1.0F);
/*      */         }
/*  382 */         else if ($$0 && !level().getWorldBorder().isWithinBounds(getBoundingBox())) {
/*  383 */           double $$1 = level().getWorldBorder().getDistanceToBorder(this) + level().getWorldBorder().getDamageSafeZone();
/*  384 */           if ($$1 < 0.0D) {
/*  385 */             double $$2 = level().getWorldBorder().getDamagePerBlock();
/*  386 */             if ($$2 > 0.0D) {
/*  387 */               hurt(damageSources().outOfBorder(), Math.max(1, Mth.floor(-$$1 * $$2)));
/*      */             }
/*      */           } 
/*      */         } 
/*      */       }
/*      */ 
/*      */       
/*  394 */       if (isEyeInFluid(FluidTags.WATER) && !level().getBlockState(BlockPos.containing(getX(), getEyeY(), getZ())).is(Blocks.BUBBLE_COLUMN)) {
/*  395 */         boolean $$3 = (!canBreatheUnderwater() && !MobEffectUtil.hasWaterBreathing(this) && (!$$0 || !(((Player)this).getAbilities()).invulnerable));
/*  396 */         if ($$3) {
/*  397 */           setAirSupply(decreaseAirSupply(getAirSupply()));
/*  398 */           if (getAirSupply() == -20) {
/*  399 */             setAirSupply(0);
/*  400 */             Vec3 $$4 = getDeltaMovement();
/*  401 */             for (int $$5 = 0; $$5 < 8; $$5++) {
/*  402 */               double $$6 = this.random.nextDouble() - this.random.nextDouble();
/*  403 */               double $$7 = this.random.nextDouble() - this.random.nextDouble();
/*  404 */               double $$8 = this.random.nextDouble() - this.random.nextDouble();
/*  405 */               level().addParticle((ParticleOptions)ParticleTypes.BUBBLE, getX() + $$6, getY() + $$7, getZ() + $$8, $$4.x, $$4.y, $$4.z);
/*      */             } 
/*  407 */             hurt(damageSources().drown(), 2.0F);
/*      */           } 
/*      */         } 
/*      */         
/*  411 */         if (!(level()).isClientSide && isPassenger() && getVehicle() != null && getVehicle().dismountsUnderwater()) {
/*  412 */           stopRiding();
/*      */         }
/*  414 */       } else if (getAirSupply() < getMaxAirSupply()) {
/*  415 */         setAirSupply(increaseAirSupply(getAirSupply()));
/*      */       } 
/*      */       
/*  418 */       if (!(level()).isClientSide) {
/*  419 */         BlockPos $$9 = blockPosition();
/*  420 */         if (!Objects.equal(this.lastPos, $$9)) {
/*  421 */           this.lastPos = $$9;
/*  422 */           onChangedBlock($$9);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  427 */     if (isAlive() && (isInWaterRainOrBubble() || this.isInPowderSnow)) {
/*  428 */       extinguishFire();
/*      */     }
/*      */     
/*  431 */     if (this.hurtTime > 0) {
/*  432 */       this.hurtTime--;
/*      */     }
/*  434 */     if (this.invulnerableTime > 0 && !(this instanceof ServerPlayer)) {
/*  435 */       this.invulnerableTime--;
/*      */     }
/*  437 */     if (isDeadOrDying() && level().shouldTickDeath(this)) {
/*  438 */       tickDeath();
/*      */     }
/*  440 */     if (this.lastHurtByPlayerTime > 0) {
/*  441 */       this.lastHurtByPlayerTime--;
/*      */     } else {
/*  443 */       this.lastHurtByPlayer = null;
/*      */     } 
/*  445 */     if (this.lastHurtMob != null && !this.lastHurtMob.isAlive()) {
/*  446 */       this.lastHurtMob = null;
/*      */     }
/*      */     
/*  449 */     if (this.lastHurtByMob != null) {
/*  450 */       if (!this.lastHurtByMob.isAlive()) {
/*  451 */         setLastHurtByMob((LivingEntity)null);
/*  452 */       } else if (this.tickCount - this.lastHurtByMobTimestamp > 100) {
/*  453 */         setLastHurtByMob((LivingEntity)null);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*  458 */     tickEffects();
/*      */     
/*  460 */     this.animStepO = this.animStep;
/*      */     
/*  462 */     this.yBodyRotO = this.yBodyRot;
/*  463 */     this.yHeadRotO = this.yHeadRot;
/*  464 */     this.yRotO = getYRot();
/*  465 */     this.xRotO = getXRot();
/*      */     
/*  467 */     level().getProfiler().pop();
/*      */   }
/*      */   
/*      */   public boolean canSpawnSoulSpeedParticle() {
/*  471 */     return (this.tickCount % 5 == 0 && (getDeltaMovement()).x != 0.0D && (getDeltaMovement()).z != 0.0D && !isSpectator() && EnchantmentHelper.hasSoulSpeed(this) && onSoulSpeedBlock());
/*      */   }
/*      */   
/*      */   protected void spawnSoulSpeedParticle() {
/*  475 */     Vec3 $$0 = getDeltaMovement();
/*  476 */     level().addParticle((ParticleOptions)ParticleTypes.SOUL, getX() + (this.random.nextDouble() - 0.5D) * getBbWidth(), getY() + 0.1D, getZ() + (this.random.nextDouble() - 0.5D) * getBbWidth(), $$0.x * -0.2D, 0.1D, $$0.z * -0.2D);
/*      */     
/*  478 */     float $$1 = (this.random.nextFloat() * 0.4F + this.random.nextFloat() > 0.9F) ? 0.6F : 0.0F;
/*  479 */     playSound(SoundEvents.SOUL_ESCAPE, $$1, 0.6F + this.random.nextFloat() * 0.4F);
/*      */   }
/*      */   
/*      */   protected boolean onSoulSpeedBlock() {
/*  483 */     return level().getBlockState(getBlockPosBelowThatAffectsMyMovement()).is(BlockTags.SOUL_SPEED_BLOCKS);
/*      */   }
/*      */ 
/*      */   
/*      */   protected float getBlockSpeedFactor() {
/*  488 */     if (onSoulSpeedBlock() && EnchantmentHelper.getEnchantmentLevel(Enchantments.SOUL_SPEED, this) > 0) {
/*  489 */       return 1.0F;
/*      */     }
/*      */     
/*  492 */     return super.getBlockSpeedFactor();
/*      */   }
/*      */   
/*      */   protected boolean shouldRemoveSoulSpeed(BlockState $$0) {
/*  496 */     return (!$$0.isAir() || isFallFlying());
/*      */   }
/*      */   
/*      */   protected void removeSoulSpeed() {
/*  500 */     AttributeInstance $$0 = getAttribute(Attributes.MOVEMENT_SPEED);
/*      */     
/*  502 */     if ($$0 == null) {
/*      */       return;
/*      */     }
/*      */     
/*  506 */     if ($$0.getModifier(SPEED_MODIFIER_SOUL_SPEED_UUID) != null) {
/*  507 */       $$0.removeModifier(SPEED_MODIFIER_SOUL_SPEED_UUID);
/*      */     }
/*      */   }
/*      */   
/*      */   protected void tryAddSoulSpeed() {
/*  512 */     if (!getBlockStateOnLegacy().isAir()) {
/*  513 */       int $$0 = EnchantmentHelper.getEnchantmentLevel(Enchantments.SOUL_SPEED, this);
/*      */       
/*  515 */       if ($$0 > 0 && 
/*  516 */         onSoulSpeedBlock()) {
/*  517 */         AttributeInstance $$1 = getAttribute(Attributes.MOVEMENT_SPEED);
/*      */         
/*  519 */         if ($$1 == null) {
/*      */           return;
/*      */         }
/*      */         
/*  523 */         $$1.addTransientModifier(new AttributeModifier(SPEED_MODIFIER_SOUL_SPEED_UUID, "Soul speed boost", (0.03F * (1.0F + $$0 * 0.35F)), AttributeModifier.Operation.ADDITION));
/*      */         
/*  525 */         if (getRandom().nextFloat() < 0.04F) {
/*  526 */           ItemStack $$2 = getItemBySlot(EquipmentSlot.FEET);
/*      */ 
/*      */           
/*  529 */           $$2.hurtAndBreak(1, this, $$0 -> $$0.broadcastBreakEvent(EquipmentSlot.FEET));
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void removeFrost() {
/*  537 */     AttributeInstance $$0 = getAttribute(Attributes.MOVEMENT_SPEED);
/*      */     
/*  539 */     if ($$0 == null) {
/*      */       return;
/*      */     }
/*      */     
/*  543 */     if ($$0.getModifier(SPEED_MODIFIER_POWDER_SNOW_UUID) != null) {
/*  544 */       $$0.removeModifier(SPEED_MODIFIER_POWDER_SNOW_UUID);
/*      */     }
/*      */   }
/*      */   
/*      */   protected void tryAddFrost() {
/*  549 */     if (!getBlockStateOnLegacy().isAir()) {
/*  550 */       int $$0 = getTicksFrozen();
/*  551 */       if ($$0 > 0) {
/*  552 */         AttributeInstance $$1 = getAttribute(Attributes.MOVEMENT_SPEED);
/*      */         
/*  554 */         if ($$1 == null) {
/*      */           return;
/*      */         }
/*      */         
/*  558 */         float $$2 = -0.05F * getPercentFrozen();
/*  559 */         $$1.addTransientModifier(new AttributeModifier(SPEED_MODIFIER_POWDER_SNOW_UUID, "Powder snow slow", $$2, AttributeModifier.Operation.ADDITION));
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void onChangedBlock(BlockPos $$0) {
/*  565 */     int $$1 = EnchantmentHelper.getEnchantmentLevel(Enchantments.FROST_WALKER, this);
/*  566 */     if ($$1 > 0) {
/*  567 */       FrostWalkerEnchantment.onEntityMoved(this, level(), $$0, $$1);
/*      */     }
/*      */     
/*  570 */     if (shouldRemoveSoulSpeed(getBlockStateOnLegacy())) {
/*  571 */       removeSoulSpeed();
/*      */     }
/*      */     
/*  574 */     tryAddSoulSpeed();
/*      */   }
/*      */   
/*      */   public boolean isBaby() {
/*  578 */     return false;
/*      */   }
/*      */   
/*      */   public float getScale() {
/*  582 */     return isBaby() ? 0.5F : 1.0F;
/*      */   }
/*      */   
/*      */   protected boolean isAffectedByFluids() {
/*  586 */     return true;
/*      */   }
/*      */   
/*      */   protected void tickDeath() {
/*  590 */     this.deathTime++;
/*  591 */     if (this.deathTime >= 20 && !level().isClientSide() && !isRemoved()) {
/*  592 */       level().broadcastEntityEvent(this, (byte)60);
/*  593 */       remove(Entity.RemovalReason.KILLED);
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean shouldDropExperience() {
/*  598 */     return !isBaby();
/*      */   }
/*      */   
/*      */   protected boolean shouldDropLoot() {
/*  602 */     return !isBaby();
/*      */   }
/*      */   
/*      */   protected int decreaseAirSupply(int $$0) {
/*  606 */     int $$1 = EnchantmentHelper.getRespiration(this);
/*  607 */     if ($$1 > 0 && 
/*  608 */       this.random.nextInt($$1 + 1) > 0)
/*      */     {
/*  610 */       return $$0;
/*      */     }
/*      */     
/*  613 */     return $$0 - 1;
/*      */   }
/*      */   
/*      */   protected int increaseAirSupply(int $$0) {
/*  617 */     return Math.min($$0 + 4, getMaxAirSupply());
/*      */   }
/*      */   
/*      */   public int getExperienceReward() {
/*  621 */     return 0;
/*      */   }
/*      */   
/*      */   protected boolean isAlwaysExperienceDropper() {
/*  625 */     return false;
/*      */   }
/*      */   
/*      */   public RandomSource getRandom() {
/*  629 */     return this.random;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public LivingEntity getLastHurtByMob() {
/*  634 */     return this.lastHurtByMob;
/*      */   }
/*      */ 
/*      */   
/*      */   public LivingEntity getLastAttacker() {
/*  639 */     return getLastHurtByMob();
/*      */   }
/*      */   
/*      */   public int getLastHurtByMobTimestamp() {
/*  643 */     return this.lastHurtByMobTimestamp;
/*      */   }
/*      */   
/*      */   public void setLastHurtByPlayer(@Nullable Player $$0) {
/*  647 */     this.lastHurtByPlayer = $$0;
/*  648 */     this.lastHurtByPlayerTime = this.tickCount;
/*      */   }
/*      */   
/*      */   public void setLastHurtByMob(@Nullable LivingEntity $$0) {
/*  652 */     this.lastHurtByMob = $$0;
/*  653 */     this.lastHurtByMobTimestamp = this.tickCount;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public LivingEntity getLastHurtMob() {
/*  658 */     return this.lastHurtMob;
/*      */   }
/*      */   
/*      */   public int getLastHurtMobTimestamp() {
/*  662 */     return this.lastHurtMobTimestamp;
/*      */   }
/*      */   
/*      */   public void setLastHurtMob(Entity $$0) {
/*  666 */     if ($$0 instanceof LivingEntity) {
/*  667 */       this.lastHurtMob = (LivingEntity)$$0;
/*      */     } else {
/*  669 */       this.lastHurtMob = null;
/*      */     } 
/*  671 */     this.lastHurtMobTimestamp = this.tickCount;
/*      */   }
/*      */   
/*      */   public int getNoActionTime() {
/*  675 */     return this.noActionTime;
/*      */   }
/*      */   
/*      */   public void setNoActionTime(int $$0) {
/*  679 */     this.noActionTime = $$0;
/*      */   }
/*      */   
/*      */   public boolean shouldDiscardFriction() {
/*  683 */     return this.discardFriction;
/*      */   }
/*      */   
/*      */   public void setDiscardFriction(boolean $$0) {
/*  687 */     this.discardFriction = $$0;
/*      */   }
/*      */   
/*      */   protected boolean doesEmitEquipEvent(EquipmentSlot $$0) {
/*  691 */     return true;
/*      */   }
/*      */   
/*      */   public void onEquipItem(EquipmentSlot $$0, ItemStack $$1, ItemStack $$2) {
/*  695 */     boolean $$3 = ($$2.isEmpty() && $$1.isEmpty());
/*  696 */     if ($$3 || ItemStack.isSameItemSameTags($$1, $$2) || this.firstTick) {
/*      */       return;
/*      */     }
/*      */     
/*  700 */     Equipable $$4 = Equipable.get($$2);
/*  701 */     if (!level().isClientSide() && !isSpectator()) {
/*  702 */       if (!isSilent() && $$4 != null && $$4.getEquipmentSlot() == $$0) {
/*  703 */         level().playSound(null, getX(), getY(), getZ(), $$4.getEquipSound(), getSoundSource(), 1.0F, 1.0F);
/*      */       }
/*  705 */       if (doesEmitEquipEvent($$0)) {
/*  706 */         gameEvent(($$4 != null) ? GameEvent.EQUIP : GameEvent.UNEQUIP);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void remove(Entity.RemovalReason $$0) {
/*  713 */     super.remove($$0);
/*  714 */     this.brain.clearMemories();
/*      */   }
/*      */ 
/*      */   
/*      */   public void addAdditionalSaveData(CompoundTag $$0) {
/*  719 */     $$0.putFloat("Health", getHealth());
/*  720 */     $$0.putShort("HurtTime", (short)this.hurtTime);
/*  721 */     $$0.putInt("HurtByTimestamp", this.lastHurtByMobTimestamp);
/*  722 */     $$0.putShort("DeathTime", (short)this.deathTime);
/*  723 */     $$0.putFloat("AbsorptionAmount", getAbsorptionAmount());
/*      */     
/*  725 */     $$0.put("Attributes", (Tag)getAttributes().save());
/*      */     
/*  727 */     if (!this.activeEffects.isEmpty()) {
/*  728 */       ListTag $$1 = new ListTag();
/*      */       
/*  730 */       for (MobEffectInstance $$2 : this.activeEffects.values()) {
/*  731 */         $$1.add($$2.save(new CompoundTag()));
/*      */       }
/*  733 */       $$0.put("active_effects", (Tag)$$1);
/*      */     } 
/*      */     
/*  736 */     $$0.putBoolean("FallFlying", isFallFlying());
/*      */     
/*  738 */     getSleepingPos().ifPresent($$1 -> {
/*      */           $$0.putInt("SleepingX", $$1.getX());
/*      */           
/*      */           $$0.putInt("SleepingY", $$1.getY());
/*      */           $$0.putInt("SleepingZ", $$1.getZ());
/*      */         });
/*  744 */     DataResult<Tag> $$3 = this.brain.serializeStart((DynamicOps)NbtOps.INSTANCE);
/*  745 */     Objects.requireNonNull(LOGGER); $$3.resultOrPartial(LOGGER::error).ifPresent($$1 -> $$0.put("Brain", $$1));
/*      */   }
/*      */ 
/*      */   
/*      */   public void readAdditionalSaveData(CompoundTag $$0) {
/*  750 */     internalSetAbsorptionAmount($$0.getFloat("AbsorptionAmount"));
/*      */     
/*  752 */     if ($$0.contains("Attributes", 9) && level() != null && !(level()).isClientSide) {
/*  753 */       getAttributes().load($$0.getList("Attributes", 10));
/*      */     }
/*      */     
/*  756 */     if ($$0.contains("active_effects", 9)) {
/*  757 */       ListTag $$1 = $$0.getList("active_effects", 10);
/*  758 */       for (int $$2 = 0; $$2 < $$1.size(); $$2++) {
/*  759 */         CompoundTag $$3 = $$1.getCompound($$2);
/*  760 */         MobEffectInstance $$4 = MobEffectInstance.load($$3);
/*  761 */         if ($$4 != null) {
/*  762 */           this.activeEffects.put($$4.getEffect(), $$4);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  767 */     if ($$0.contains("Health", 99)) {
/*  768 */       setHealth($$0.getFloat("Health"));
/*      */     }
/*      */     
/*  771 */     this.hurtTime = $$0.getShort("HurtTime");
/*  772 */     this.deathTime = $$0.getShort("DeathTime");
/*  773 */     this.lastHurtByMobTimestamp = $$0.getInt("HurtByTimestamp");
/*      */ 
/*      */     
/*  776 */     if ($$0.contains("Team", 8)) {
/*  777 */       String $$5 = $$0.getString("Team");
/*  778 */       PlayerTeam $$6 = level().getScoreboard().getPlayerTeam($$5);
/*  779 */       boolean $$7 = ($$6 != null && level().getScoreboard().addPlayerToTeam(getStringUUID(), $$6));
/*  780 */       if (!$$7) {
/*  781 */         LOGGER.warn("Unable to add mob to team \"{}\" (that team probably doesn't exist)", $$5);
/*      */       }
/*      */     } 
/*      */     
/*  785 */     if ($$0.getBoolean("FallFlying")) {
/*  786 */       setSharedFlag(7, true);
/*      */     }
/*      */     
/*  789 */     if ($$0.contains("SleepingX", 99) && $$0
/*  790 */       .contains("SleepingY", 99) && $$0
/*  791 */       .contains("SleepingZ", 99)) {
/*      */       
/*  793 */       BlockPos $$8 = new BlockPos($$0.getInt("SleepingX"), $$0.getInt("SleepingY"), $$0.getInt("SleepingZ"));
/*  794 */       setSleepingPos($$8);
/*  795 */       this.entityData.set(DATA_POSE, Pose.SLEEPING);
/*      */       
/*  797 */       if (!this.firstTick)
/*      */       {
/*  799 */         setPosToBed($$8);
/*      */       }
/*      */     } 
/*      */     
/*  803 */     if ($$0.contains("Brain", 10)) {
/*  804 */       this.brain = makeBrain(new Dynamic((DynamicOps)NbtOps.INSTANCE, $$0.get("Brain")));
/*      */     }
/*      */   }
/*      */   
/*      */   protected void tickEffects() {
/*  809 */     Iterator<MobEffect> $$0 = this.activeEffects.keySet().iterator();
/*      */     try {
/*  811 */       while ($$0.hasNext()) {
/*  812 */         MobEffect $$1 = $$0.next();
/*  813 */         MobEffectInstance $$2 = this.activeEffects.get($$1);
/*      */         
/*  815 */         if (!$$2.tick(this, () -> onEffectUpdated($$0, true, (Entity)null))) {
/*  816 */           if (!(level()).isClientSide) {
/*  817 */             $$0.remove();
/*  818 */             onEffectRemoved($$2);
/*      */           }  continue;
/*  820 */         }  if ($$2.getDuration() % 600 == 0)
/*      */         {
/*  822 */           onEffectUpdated($$2, false, (Entity)null);
/*      */         }
/*      */       } 
/*  825 */     } catch (ConcurrentModificationException concurrentModificationException) {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  830 */     if (this.effectsDirty) {
/*  831 */       if (!(level()).isClientSide) {
/*  832 */         updateInvisibilityStatus();
/*  833 */         updateGlowingStatus();
/*      */       } 
/*  835 */       this.effectsDirty = false;
/*      */     } 
/*  837 */     int $$3 = ((Integer)this.entityData.get(DATA_EFFECT_COLOR_ID)).intValue();
/*  838 */     boolean $$4 = ((Boolean)this.entityData.get(DATA_EFFECT_AMBIENCE_ID)).booleanValue();
/*      */     
/*  840 */     if ($$3 > 0) {
/*      */       boolean $$6; int i;
/*  842 */       if (isInvisible()) {
/*      */         
/*  844 */         boolean $$5 = (this.random.nextInt(15) == 0);
/*      */       } else {
/*  846 */         $$6 = this.random.nextBoolean();
/*      */       } 
/*      */       
/*  849 */       if ($$4) {
/*  850 */         i = $$6 & ((this.random.nextInt(5) == 0) ? 1 : 0);
/*      */       }
/*      */       
/*  853 */       if (i != 0 && 
/*  854 */         $$3 > 0) {
/*  855 */         double $$7 = ($$3 >> 16 & 0xFF) / 255.0D;
/*  856 */         double $$8 = ($$3 >> 8 & 0xFF) / 255.0D;
/*  857 */         double $$9 = ($$3 >> 0 & 0xFF) / 255.0D;
/*  858 */         level().addParticle($$4 ? (ParticleOptions)ParticleTypes.AMBIENT_ENTITY_EFFECT : (ParticleOptions)ParticleTypes.ENTITY_EFFECT, getRandomX(0.5D), getRandomY(), getRandomZ(0.5D), $$7, $$8, $$9);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void updateInvisibilityStatus() {
/*  865 */     if (this.activeEffects.isEmpty()) {
/*  866 */       removeEffectParticles();
/*  867 */       setInvisible(false);
/*      */     } else {
/*  869 */       Collection<MobEffectInstance> $$0 = this.activeEffects.values();
/*  870 */       this.entityData.set(DATA_EFFECT_AMBIENCE_ID, Boolean.valueOf(areAllEffectsAmbient($$0)));
/*  871 */       this.entityData.set(DATA_EFFECT_COLOR_ID, Integer.valueOf(PotionUtils.getColor($$0)));
/*  872 */       setInvisible(hasEffect(MobEffects.INVISIBILITY));
/*      */     } 
/*      */   }
/*      */   
/*      */   private void updateGlowingStatus() {
/*  877 */     boolean $$0 = isCurrentlyGlowing();
/*  878 */     if (getSharedFlag(6) != $$0) {
/*  879 */       setSharedFlag(6, $$0);
/*      */     }
/*      */   }
/*      */   
/*      */   public double getVisibilityPercent(@Nullable Entity $$0) {
/*  884 */     double $$1 = 1.0D;
/*      */     
/*  886 */     if (isDiscrete()) {
/*  887 */       $$1 *= 0.8D;
/*      */     }
/*  889 */     if (isInvisible()) {
/*  890 */       float $$2 = getArmorCoverPercentage();
/*  891 */       if ($$2 < 0.1F) {
/*  892 */         $$2 = 0.1F;
/*      */       }
/*  894 */       $$1 *= 0.7D * $$2;
/*      */     } 
/*  896 */     if ($$0 != null) {
/*  897 */       ItemStack $$3 = getItemBySlot(EquipmentSlot.HEAD);
/*  898 */       EntityType<?> $$4 = $$0.getType();
/*      */       
/*  900 */       if (($$4 == EntityType.SKELETON && $$3.is(Items.SKELETON_SKULL)) || ($$4 == EntityType.ZOMBIE && $$3
/*  901 */         .is(Items.ZOMBIE_HEAD)) || ($$4 == EntityType.PIGLIN && $$3
/*  902 */         .is(Items.PIGLIN_HEAD)) || ($$4 == EntityType.PIGLIN_BRUTE && $$3
/*  903 */         .is(Items.PIGLIN_HEAD)) || ($$4 == EntityType.CREEPER && $$3
/*  904 */         .is(Items.CREEPER_HEAD)))
/*      */       {
/*  906 */         $$1 *= 0.5D;
/*      */       }
/*      */     } 
/*      */     
/*  910 */     return $$1;
/*      */   }
/*      */   
/*      */   public boolean canAttack(LivingEntity $$0) {
/*  914 */     if ($$0 instanceof Player && level().getDifficulty() == Difficulty.PEACEFUL) {
/*  915 */       return false;
/*      */     }
/*  917 */     return $$0.canBeSeenAsEnemy();
/*      */   }
/*      */   
/*      */   public boolean canAttack(LivingEntity $$0, TargetingConditions $$1) {
/*  921 */     return $$1.test(this, $$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canBeSeenAsEnemy() {
/*  926 */     return (!isInvulnerable() && canBeSeenByAnyone());
/*      */   }
/*      */   
/*      */   public boolean canBeSeenByAnyone() {
/*  930 */     return (!isSpectator() && isAlive());
/*      */   }
/*      */   
/*      */   public static boolean areAllEffectsAmbient(Collection<MobEffectInstance> $$0) {
/*  934 */     for (MobEffectInstance $$1 : $$0) {
/*  935 */       if ($$1.isVisible() && !$$1.isAmbient()) {
/*  936 */         return false;
/*      */       }
/*      */     } 
/*      */     
/*  940 */     return true;
/*      */   }
/*      */   
/*      */   protected void removeEffectParticles() {
/*  944 */     this.entityData.set(DATA_EFFECT_AMBIENCE_ID, Boolean.valueOf(false));
/*  945 */     this.entityData.set(DATA_EFFECT_COLOR_ID, Integer.valueOf(0));
/*      */   }
/*      */   
/*      */   public boolean removeAllEffects() {
/*  949 */     if ((level()).isClientSide) {
/*  950 */       return false;
/*      */     }
/*      */     
/*  953 */     Iterator<MobEffectInstance> $$0 = this.activeEffects.values().iterator();
/*  954 */     boolean $$1 = false;
/*  955 */     while ($$0.hasNext()) {
/*  956 */       onEffectRemoved($$0.next());
/*  957 */       $$0.remove();
/*  958 */       $$1 = true;
/*      */     } 
/*  960 */     return $$1;
/*      */   }
/*      */   
/*      */   public Collection<MobEffectInstance> getActiveEffects() {
/*  964 */     return this.activeEffects.values();
/*      */   }
/*      */   
/*      */   public Map<MobEffect, MobEffectInstance> getActiveEffectsMap() {
/*  968 */     return this.activeEffects;
/*      */   }
/*      */   
/*      */   public boolean hasEffect(MobEffect $$0) {
/*  972 */     return this.activeEffects.containsKey($$0);
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public MobEffectInstance getEffect(MobEffect $$0) {
/*  977 */     return this.activeEffects.get($$0);
/*      */   }
/*      */   
/*      */   public final boolean addEffect(MobEffectInstance $$0) {
/*  981 */     return addEffect($$0, (Entity)null);
/*      */   }
/*      */   
/*      */   public boolean addEffect(MobEffectInstance $$0, @Nullable Entity $$1) {
/*  985 */     if (!canBeAffected($$0)) {
/*  986 */       return false;
/*      */     }
/*      */     
/*  989 */     MobEffectInstance $$2 = this.activeEffects.get($$0.getEffect());
/*  990 */     boolean $$3 = false;
/*  991 */     if ($$2 == null) {
/*  992 */       this.activeEffects.put($$0.getEffect(), $$0);
/*  993 */       onEffectAdded($$0, $$1);
/*  994 */       $$3 = true;
/*      */     
/*      */     }
/*  997 */     else if ($$2.update($$0)) {
/*  998 */       onEffectUpdated($$2, true, $$1);
/*  999 */       $$3 = true;
/*      */     } 
/*      */     
/* 1002 */     $$0.onEffectStarted(this);
/*      */     
/* 1004 */     return $$3;
/*      */   }
/*      */   
/*      */   public boolean canBeAffected(MobEffectInstance $$0) {
/* 1008 */     if (getMobType() == MobType.UNDEAD) {
/* 1009 */       MobEffect $$1 = $$0.getEffect();
/* 1010 */       if ($$1 == MobEffects.REGENERATION || $$1 == MobEffects.POISON) {
/* 1011 */         return false;
/*      */       }
/*      */     } 
/*      */     
/* 1015 */     return true;
/*      */   }
/*      */   
/*      */   public void forceAddEffect(MobEffectInstance $$0, @Nullable Entity $$1) {
/* 1019 */     if (!canBeAffected($$0)) {
/*      */       return;
/*      */     }
/*      */     
/* 1023 */     MobEffectInstance $$2 = this.activeEffects.put($$0.getEffect(), $$0);
/* 1024 */     if ($$2 == null) {
/* 1025 */       onEffectAdded($$0, $$1);
/*      */     } else {
/* 1027 */       onEffectUpdated($$0, true, $$1);
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean isInvertedHealAndHarm() {
/* 1032 */     return (getMobType() == MobType.UNDEAD);
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public MobEffectInstance removeEffectNoUpdate(@Nullable MobEffect $$0) {
/* 1037 */     return this.activeEffects.remove($$0);
/*      */   }
/*      */   
/*      */   public boolean removeEffect(MobEffect $$0) {
/* 1041 */     MobEffectInstance $$1 = removeEffectNoUpdate($$0);
/* 1042 */     if ($$1 != null) {
/* 1043 */       onEffectRemoved($$1);
/* 1044 */       return true;
/*      */     } 
/* 1046 */     return false;
/*      */   }
/*      */   
/*      */   protected void onEffectAdded(MobEffectInstance $$0, @Nullable Entity $$1) {
/* 1050 */     this.effectsDirty = true;
/* 1051 */     if (!(level()).isClientSide) {
/* 1052 */       $$0.getEffect().addAttributeModifiers(getAttributes(), $$0.getAmplifier());
/* 1053 */       sendEffectToPassengers($$0);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void sendEffectToPassengers(MobEffectInstance $$0) {
/* 1058 */     for (Entity $$1 : getPassengers()) {
/* 1059 */       if ($$1 instanceof ServerPlayer) { ServerPlayer $$2 = (ServerPlayer)$$1;
/* 1060 */         $$2.connection.send((Packet)new ClientboundUpdateMobEffectPacket(getId(), $$0)); }
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void onEffectUpdated(MobEffectInstance $$0, boolean $$1, @Nullable Entity $$2) {
/* 1066 */     this.effectsDirty = true;
/* 1067 */     if ($$1 && !(level()).isClientSide) {
/* 1068 */       MobEffect $$3 = $$0.getEffect();
/* 1069 */       $$3.removeAttributeModifiers(getAttributes());
/* 1070 */       $$3.addAttributeModifiers(getAttributes(), $$0.getAmplifier());
/* 1071 */       refreshDirtyAttributes();
/*      */     } 
/* 1073 */     if (!(level()).isClientSide) {
/* 1074 */       sendEffectToPassengers($$0);
/*      */     }
/*      */   }
/*      */   
/*      */   protected void onEffectRemoved(MobEffectInstance $$0) {
/* 1079 */     this.effectsDirty = true;
/* 1080 */     if (!(level()).isClientSide) {
/* 1081 */       $$0.getEffect().removeAttributeModifiers(getAttributes());
/* 1082 */       refreshDirtyAttributes();
/* 1083 */       for (Entity $$1 : getPassengers()) {
/* 1084 */         if ($$1 instanceof ServerPlayer) { ServerPlayer $$2 = (ServerPlayer)$$1;
/* 1085 */           $$2.connection.send((Packet)new ClientboundRemoveMobEffectPacket(getId(), $$0.getEffect())); }
/*      */       
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void refreshDirtyAttributes() {
/* 1092 */     for (AttributeInstance $$0 : getAttributes().getDirtyAttributes()) {
/* 1093 */       onAttributeUpdated($$0.getAttribute());
/*      */     }
/*      */   }
/*      */   
/*      */   private void onAttributeUpdated(Attribute $$0) {
/* 1098 */     if ($$0 == Attributes.MAX_HEALTH) {
/* 1099 */       float $$1 = getMaxHealth();
/* 1100 */       if (getHealth() > $$1) {
/* 1101 */         setHealth($$1);
/*      */       }
/* 1103 */     } else if ($$0 == Attributes.MAX_ABSORPTION) {
/* 1104 */       float $$2 = getMaxAbsorption();
/* 1105 */       if (getAbsorptionAmount() > $$2) {
/* 1106 */         setAbsorptionAmount($$2);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public void heal(float $$0) {
/* 1112 */     float $$1 = getHealth();
/* 1113 */     if ($$1 > 0.0F) {
/* 1114 */       setHealth($$1 + $$0);
/*      */     }
/*      */   }
/*      */   
/*      */   public float getHealth() {
/* 1119 */     return ((Float)this.entityData.get(DATA_HEALTH_ID)).floatValue();
/*      */   }
/*      */   
/*      */   public void setHealth(float $$0) {
/* 1123 */     this.entityData.set(DATA_HEALTH_ID, Float.valueOf(Mth.clamp($$0, 0.0F, getMaxHealth())));
/*      */   }
/*      */   
/*      */   public boolean isDeadOrDying() {
/* 1127 */     return (getHealth() <= 0.0F);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hurt(DamageSource $$0, float $$1) {
/* 1132 */     if (isInvulnerableTo($$0)) {
/* 1133 */       return false;
/*      */     }
/* 1135 */     if ((level()).isClientSide) {
/* 1136 */       return false;
/*      */     }
/*      */     
/* 1139 */     if (isDeadOrDying()) {
/* 1140 */       return false;
/*      */     }
/*      */     
/* 1143 */     if ($$0.is(DamageTypeTags.IS_FIRE) && hasEffect(MobEffects.FIRE_RESISTANCE)) {
/* 1144 */       return false;
/*      */     }
/*      */     
/* 1147 */     if (isSleeping() && !(level()).isClientSide) {
/* 1148 */       stopSleeping();
/*      */     }
/*      */     
/* 1151 */     this.noActionTime = 0;
/* 1152 */     float $$2 = $$1;
/*      */     
/* 1154 */     boolean $$3 = false;
/* 1155 */     float $$4 = 0.0F;
/* 1156 */     if ($$1 > 0.0F && isDamageSourceBlocked($$0)) {
/* 1157 */       hurtCurrentlyUsedShield($$1);
/*      */       
/* 1159 */       $$4 = $$1;
/* 1160 */       $$1 = 0.0F;
/* 1161 */       if (!$$0.is(DamageTypeTags.IS_PROJECTILE)) {
/* 1162 */         Entity $$5 = $$0.getDirectEntity();
/* 1163 */         if ($$5 instanceof LivingEntity) { LivingEntity $$6 = (LivingEntity)$$5;
/* 1164 */           blockUsingShield($$6); }
/*      */       
/*      */       } 
/*      */       
/* 1168 */       $$3 = true;
/*      */     } 
/*      */     
/* 1171 */     if ($$0.is(DamageTypeTags.IS_FREEZING) && getType().is(EntityTypeTags.FREEZE_HURTS_EXTRA_TYPES)) {
/* 1172 */       $$1 *= 5.0F;
/*      */     }
/*      */     
/* 1175 */     this.walkAnimation.setSpeed(1.5F);
/*      */     
/* 1177 */     boolean $$7 = true;
/* 1178 */     if (this.invulnerableTime > 10.0F && !$$0.is(DamageTypeTags.BYPASSES_COOLDOWN)) {
/* 1179 */       if ($$1 <= this.lastHurt) {
/* 1180 */         return false;
/*      */       }
/* 1182 */       actuallyHurt($$0, $$1 - this.lastHurt);
/* 1183 */       this.lastHurt = $$1;
/* 1184 */       $$7 = false;
/*      */     } else {
/* 1186 */       this.lastHurt = $$1;
/* 1187 */       this.invulnerableTime = 20;
/* 1188 */       actuallyHurt($$0, $$1);
/* 1189 */       this.hurtDuration = 10;
/* 1190 */       this.hurtTime = this.hurtDuration;
/*      */     } 
/*      */     
/* 1193 */     if ($$0.is(DamageTypeTags.DAMAGES_HELMET) && !getItemBySlot(EquipmentSlot.HEAD).isEmpty()) {
/* 1194 */       hurtHelmet($$0, $$1);
/* 1195 */       $$1 *= 0.75F;
/*      */     } 
/*      */     
/* 1198 */     Entity $$8 = $$0.getEntity();
/* 1199 */     if ($$8 != null) {
/* 1200 */       if ($$8 instanceof LivingEntity) { LivingEntity $$9 = (LivingEntity)$$8; if (!$$0.is(DamageTypeTags.NO_ANGER))
/* 1201 */           setLastHurtByMob($$9);  }
/*      */       
/* 1203 */       if ($$8 instanceof Player) { Player $$10 = (Player)$$8;
/* 1204 */         this.lastHurtByPlayerTime = 100;
/* 1205 */         this.lastHurtByPlayer = $$10; }
/* 1206 */       else if ($$8 instanceof Wolf) { Wolf $$11 = (Wolf)$$8;
/* 1207 */         if ($$11.isTame()) {
/* 1208 */           this.lastHurtByPlayerTime = 100;
/* 1209 */           LivingEntity livingEntity = $$11.getOwner(); if (livingEntity instanceof Player) { Player $$12 = (Player)livingEntity;
/* 1210 */             this.lastHurtByPlayer = $$12; }
/*      */           else
/* 1212 */           { this.lastHurtByPlayer = null; }
/*      */         
/*      */         }  }
/*      */     
/*      */     } 
/* 1217 */     if ($$7) {
/* 1218 */       if ($$3) {
/* 1219 */         level().broadcastEntityEvent(this, (byte)29);
/*      */       } else {
/* 1221 */         level().broadcastDamageEvent(this, $$0);
/*      */       } 
/* 1223 */       if (!$$0.is(DamageTypeTags.NO_IMPACT) && (!$$3 || $$1 > 0.0F)) {
/* 1224 */         markHurt();
/*      */       }
/* 1226 */       if ($$8 != null && !$$0.is(DamageTypeTags.NO_KNOCKBACK)) {
/* 1227 */         double $$13 = $$8.getX() - getX();
/* 1228 */         double $$14 = $$8.getZ() - getZ();
/* 1229 */         while ($$13 * $$13 + $$14 * $$14 < 1.0E-4D) {
/* 1230 */           $$13 = (Math.random() - Math.random()) * 0.01D;
/* 1231 */           $$14 = (Math.random() - Math.random()) * 0.01D;
/*      */         } 
/* 1233 */         knockback(0.4000000059604645D, $$13, $$14);
/* 1234 */         if (!$$3) {
/* 1235 */           indicateDamage($$13, $$14);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1240 */     if (isDeadOrDying()) {
/* 1241 */       if (!checkTotemDeathProtection($$0)) {
/* 1242 */         SoundEvent $$15 = getDeathSound();
/* 1243 */         if ($$7 && $$15 != null) {
/* 1244 */           playSound($$15, getSoundVolume(), getVoicePitch());
/*      */         }
/* 1246 */         die($$0);
/*      */       }
/*      */     
/* 1249 */     } else if ($$7) {
/* 1250 */       playHurtSound($$0);
/*      */     } 
/*      */ 
/*      */     
/* 1254 */     boolean $$16 = (!$$3 || $$1 > 0.0F);
/* 1255 */     if ($$16) {
/* 1256 */       this.lastDamageSource = $$0;
/* 1257 */       this.lastDamageStamp = level().getGameTime();
/*      */     } 
/* 1259 */     if (this instanceof ServerPlayer) {
/* 1260 */       CriteriaTriggers.ENTITY_HURT_PLAYER.trigger((ServerPlayer)this, $$0, $$2, $$1, $$3);
/*      */       
/* 1262 */       if ($$4 > 0.0F && $$4 < 3.4028235E37F) {
/* 1263 */         ((ServerPlayer)this).awardStat(Stats.DAMAGE_BLOCKED_BY_SHIELD, Math.round($$4 * 10.0F));
/*      */       }
/*      */     } 
/* 1266 */     if ($$8 instanceof ServerPlayer) {
/* 1267 */       CriteriaTriggers.PLAYER_HURT_ENTITY.trigger((ServerPlayer)$$8, this, $$0, $$2, $$1, $$3);
/*      */     }
/*      */     
/* 1270 */     return $$16;
/*      */   }
/*      */   
/*      */   protected void blockUsingShield(LivingEntity $$0) {
/* 1274 */     $$0.blockedByShield(this);
/*      */   }
/*      */   
/*      */   protected void blockedByShield(LivingEntity $$0) {
/* 1278 */     $$0.knockback(0.5D, $$0.getX() - getX(), $$0.getZ() - getZ());
/*      */   }
/*      */   
/*      */   private boolean checkTotemDeathProtection(DamageSource $$0) {
/* 1282 */     if ($$0.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
/* 1283 */       return false;
/*      */     }
/*      */     
/* 1286 */     ItemStack $$1 = null;
/*      */     
/* 1288 */     for (InteractionHand $$2 : InteractionHand.values()) {
/* 1289 */       ItemStack $$3 = getItemInHand($$2);
/* 1290 */       if ($$3.is(Items.TOTEM_OF_UNDYING)) {
/* 1291 */         $$1 = $$3.copy();
/* 1292 */         $$3.shrink(1);
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/* 1297 */     if ($$1 != null) {
/*      */       
/* 1299 */       LivingEntity livingEntity = this; if (livingEntity instanceof ServerPlayer) { ServerPlayer $$4 = (ServerPlayer)livingEntity;
/* 1300 */         $$4.awardStat(Stats.ITEM_USED.get(Items.TOTEM_OF_UNDYING));
/* 1301 */         CriteriaTriggers.USED_TOTEM.trigger($$4, $$1);
/* 1302 */         gameEvent(GameEvent.ITEM_INTERACT_FINISH); }
/*      */       
/* 1304 */       setHealth(1.0F);
/* 1305 */       removeAllEffects();
/* 1306 */       addEffect(new MobEffectInstance(MobEffects.REGENERATION, 900, 1));
/* 1307 */       addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 100, 1));
/* 1308 */       addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 800, 0));
/* 1309 */       level().broadcastEntityEvent(this, (byte)35);
/*      */     } 
/* 1311 */     return ($$1 != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public DamageSource getLastDamageSource() {
/* 1319 */     if (level().getGameTime() - this.lastDamageStamp > 40L) {
/* 1320 */       this.lastDamageSource = null;
/*      */     }
/* 1322 */     return this.lastDamageSource;
/*      */   }
/*      */   
/*      */   protected void playHurtSound(DamageSource $$0) {
/* 1326 */     SoundEvent $$1 = getHurtSound($$0);
/* 1327 */     if ($$1 != null) {
/* 1328 */       playSound($$1, getSoundVolume(), getVoicePitch());
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean isDamageSourceBlocked(DamageSource $$0) {
/* 1333 */     Entity $$1 = $$0.getDirectEntity();
/*      */     
/* 1335 */     boolean $$2 = false;
/* 1336 */     if ($$1 instanceof AbstractArrow) { AbstractArrow $$3 = (AbstractArrow)$$1;
/* 1337 */       if ($$3.getPierceLevel() > 0) {
/* 1338 */         $$2 = true;
/*      */       } }
/*      */     
/* 1341 */     if (!$$0.is(DamageTypeTags.BYPASSES_SHIELD) && isBlocking() && !$$2) {
/* 1342 */       Vec3 $$4 = $$0.getSourcePosition();
/* 1343 */       if ($$4 != null) {
/* 1344 */         Vec3 $$5 = calculateViewVector(0.0F, getYHeadRot());
/* 1345 */         Vec3 $$6 = $$4.vectorTo(position());
/* 1346 */         $$6 = (new Vec3($$6.x, 0.0D, $$6.z)).normalize();
/*      */ 
/*      */ 
/*      */         
/* 1350 */         return ($$6.dot($$5) < 0.0D);
/*      */       } 
/*      */     } 
/* 1353 */     return false;
/*      */   }
/*      */   
/*      */   private void breakItem(ItemStack $$0) {
/* 1357 */     if (!$$0.isEmpty()) {
/* 1358 */       if (!isSilent()) {
/* 1359 */         level().playLocalSound(getX(), getY(), getZ(), SoundEvents.ITEM_BREAK, getSoundSource(), 0.8F, 0.8F + (level()).random.nextFloat() * 0.4F, false);
/*      */       }
/* 1361 */       spawnItemParticles($$0, 5);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void die(DamageSource $$0) {
/* 1366 */     if (isRemoved() || this.dead) {
/*      */       return;
/*      */     }
/* 1369 */     Entity $$1 = $$0.getEntity();
/* 1370 */     LivingEntity $$2 = getKillCredit();
/* 1371 */     if (this.deathScore >= 0 && $$2 != null) {
/* 1372 */       $$2.awardKillScore(this, this.deathScore, $$0);
/*      */     }
/*      */     
/* 1375 */     if (isSleeping()) {
/* 1376 */       stopSleeping();
/*      */     }
/* 1378 */     if (!(level()).isClientSide && hasCustomName()) {
/* 1379 */       LOGGER.info("Named entity {} died: {}", this, getCombatTracker().getDeathMessage().getString());
/*      */     }
/*      */     
/* 1382 */     this.dead = true;
/* 1383 */     getCombatTracker().recheckStatus();
/*      */     
/* 1385 */     Level level = level(); if (level instanceof ServerLevel) { ServerLevel $$3 = (ServerLevel)level;
/* 1386 */       if ($$1 == null || $$1.killedEntity($$3, this)) {
/* 1387 */         gameEvent(GameEvent.ENTITY_DIE);
/* 1388 */         dropAllDeathLoot($$0);
/* 1389 */         createWitherRose($$2);
/*      */       } 
/* 1391 */       level().broadcastEntityEvent(this, (byte)3); }
/*      */     
/* 1393 */     setPose(Pose.DYING);
/*      */   }
/*      */   
/*      */   protected void createWitherRose(@Nullable LivingEntity $$0) {
/* 1397 */     if ((level()).isClientSide) {
/*      */       return;
/*      */     }
/*      */     
/* 1401 */     boolean $$1 = false;
/* 1402 */     if ($$0 instanceof net.minecraft.world.entity.boss.wither.WitherBoss) {
/* 1403 */       if (level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
/* 1404 */         BlockPos $$2 = blockPosition();
/* 1405 */         BlockState $$3 = Blocks.WITHER_ROSE.defaultBlockState();
/* 1406 */         if (level().getBlockState($$2).isAir() && $$3.canSurvive((LevelReader)level(), $$2)) {
/* 1407 */           level().setBlock($$2, $$3, 3);
/* 1408 */           $$1 = true;
/*      */         } 
/*      */       } 
/*      */       
/* 1412 */       if (!$$1) {
/* 1413 */         ItemEntity $$4 = new ItemEntity(level(), getX(), getY(), getZ(), new ItemStack((ItemLike)Items.WITHER_ROSE));
/* 1414 */         level().addFreshEntity((Entity)$$4);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   protected void dropAllDeathLoot(DamageSource $$0) {
/*      */     int $$3;
/* 1420 */     Entity $$1 = $$0.getEntity();
/*      */ 
/*      */     
/* 1423 */     if ($$1 instanceof Player) {
/* 1424 */       int $$2 = EnchantmentHelper.getMobLooting((LivingEntity)$$1);
/*      */     } else {
/* 1426 */       $$3 = 0;
/*      */     } 
/*      */     
/* 1429 */     boolean $$4 = (this.lastHurtByPlayerTime > 0);
/*      */     
/* 1431 */     if (shouldDropLoot() && level().getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
/* 1432 */       dropFromLootTable($$0, $$4);
/* 1433 */       dropCustomDeathLoot($$0, $$3, $$4);
/*      */     } 
/* 1435 */     dropEquipment();
/* 1436 */     dropExperience();
/*      */   }
/*      */ 
/*      */   
/*      */   protected void dropEquipment() {}
/*      */   
/*      */   protected void dropExperience() {
/* 1443 */     if (level() instanceof ServerLevel && !wasExperienceConsumed() && (isAlwaysExperienceDropper() || (this.lastHurtByPlayerTime > 0 && shouldDropExperience() && level().getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)))) {
/* 1444 */       ExperienceOrb.award((ServerLevel)level(), position(), getExperienceReward());
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected void dropCustomDeathLoot(DamageSource $$0, int $$1, boolean $$2) {}
/*      */   
/*      */   public ResourceLocation getLootTable() {
/* 1452 */     return getType().getDefaultLootTable();
/*      */   }
/*      */   
/*      */   public long getLootTableSeed() {
/* 1456 */     return 0L;
/*      */   }
/*      */   
/*      */   protected void dropFromLootTable(DamageSource $$0, boolean $$1) {
/* 1460 */     ResourceLocation $$2 = getLootTable();
/*      */     
/* 1462 */     LootTable $$3 = level().getServer().getLootData().getLootTable($$2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1469 */     LootParams.Builder $$4 = (new LootParams.Builder((ServerLevel)level())).withParameter(LootContextParams.THIS_ENTITY, this).withParameter(LootContextParams.ORIGIN, position()).withParameter(LootContextParams.DAMAGE_SOURCE, $$0).withOptionalParameter(LootContextParams.KILLER_ENTITY, $$0.getEntity()).withOptionalParameter(LootContextParams.DIRECT_KILLER_ENTITY, $$0.getDirectEntity());
/*      */     
/* 1471 */     if ($$1 && this.lastHurtByPlayer != null) {
/* 1472 */       $$4 = $$4.withParameter(LootContextParams.LAST_DAMAGE_PLAYER, this.lastHurtByPlayer).withLuck(this.lastHurtByPlayer.getLuck());
/*      */     }
/* 1474 */     LootParams $$5 = $$4.create(LootContextParamSets.ENTITY);
/* 1475 */     $$3.getRandomItems($$5, getLootTableSeed(), this::spawnAtLocation);
/*      */   }
/*      */   
/*      */   public void knockback(double $$0, double $$1, double $$2) {
/* 1479 */     $$0 *= 1.0D - getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
/* 1480 */     if ($$0 <= 0.0D) {
/*      */       return;
/*      */     }
/*      */     
/* 1484 */     this.hasImpulse = true;
/*      */     
/* 1486 */     Vec3 $$3 = getDeltaMovement();
/*      */     
/* 1488 */     Vec3 $$4 = (new Vec3($$1, 0.0D, $$2)).normalize().scale($$0);
/*      */     
/* 1490 */     setDeltaMovement($$3.x / 2.0D - $$4.x, 
/*      */         
/* 1492 */         onGround() ? Math.min(0.4D, $$3.y / 2.0D + $$0) : $$3.y, $$3.z / 2.0D - $$4.z);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void indicateDamage(double $$0, double $$1) {}
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   protected SoundEvent getHurtSound(DamageSource $$0) {
/* 1502 */     return SoundEvents.GENERIC_HURT;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   protected SoundEvent getDeathSound() {
/* 1507 */     return SoundEvents.GENERIC_DEATH;
/*      */   }
/*      */   
/*      */   private SoundEvent getFallDamageSound(int $$0) {
/* 1511 */     return ($$0 > 4) ? getFallSounds().big() : getFallSounds().small();
/*      */   }
/*      */   
/*      */   public void skipDropExperience() {
/* 1515 */     this.skipDropExperience = true;
/*      */   }
/*      */   
/*      */   public boolean wasExperienceConsumed() {
/* 1519 */     return this.skipDropExperience;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getHurtDir() {
/* 1526 */     return 0.0F;
/*      */   }
/*      */   
/*      */   protected AABB getHitbox() {
/* 1530 */     AABB $$0 = getBoundingBox();
/* 1531 */     Entity $$1 = getVehicle();
/* 1532 */     if ($$1 != null) {
/* 1533 */       Vec3 $$2 = $$1.getPassengerRidingPosition(this);
/* 1534 */       return $$0.setMinY(Math.max($$2.y, $$0.minY));
/*      */     } 
/* 1536 */     return $$0;
/*      */   }
/*      */   public static final class Fallsounds extends Record { private final SoundEvent small; private final SoundEvent big;
/* 1539 */     public Fallsounds(SoundEvent $$0, SoundEvent $$1) { this.small = $$0; this.big = $$1; } public final String toString() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/entity/LivingEntity$Fallsounds;)Ljava/lang/String;
/*      */       //   6: areturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1539	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/* 1539 */       //   0	7	0	this	Lnet/minecraft/world/entity/LivingEntity$Fallsounds; } public SoundEvent small() { return this.small; } public final int hashCode() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/entity/LivingEntity$Fallsounds;)I
/*      */       //   6: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1539	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lnet/minecraft/world/entity/LivingEntity$Fallsounds; } public final boolean equals(Object $$0) { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: aload_1
/*      */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/entity/LivingEntity$Fallsounds;Ljava/lang/Object;)Z
/*      */       //   7: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1539	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	8	0	this	Lnet/minecraft/world/entity/LivingEntity$Fallsounds;
/* 1539 */       //   0	8	1	$$0	Ljava/lang/Object; } public SoundEvent big() { return this.big; }
/*      */      }
/*      */   
/*      */   public Fallsounds getFallSounds() {
/* 1543 */     return new Fallsounds(SoundEvents.GENERIC_SMALL_FALL, SoundEvents.GENERIC_BIG_FALL);
/*      */   }
/*      */   
/*      */   protected SoundEvent getDrinkingSound(ItemStack $$0) {
/* 1547 */     return $$0.getDrinkingSound();
/*      */   }
/*      */   
/*      */   public SoundEvent getEatingSound(ItemStack $$0) {
/* 1551 */     return $$0.getEatingSound();
/*      */   }
/*      */   
/*      */   public Optional<BlockPos> getLastClimbablePos() {
/* 1555 */     return this.lastClimbablePos;
/*      */   }
/*      */   
/*      */   public boolean onClimbable() {
/* 1559 */     if (isSpectator()) {
/* 1560 */       return false;
/*      */     }
/*      */     
/* 1563 */     BlockPos $$0 = blockPosition();
/*      */     
/* 1565 */     BlockState $$1 = getFeetBlockState();
/* 1566 */     if ($$1.is(BlockTags.CLIMBABLE)) {
/* 1567 */       this.lastClimbablePos = Optional.of($$0);
/* 1568 */       return true;
/*      */     } 
/*      */     
/* 1571 */     if ($$1.getBlock() instanceof TrapDoorBlock && trapdoorUsableAsLadder($$0, $$1)) {
/* 1572 */       this.lastClimbablePos = Optional.of($$0);
/* 1573 */       return true;
/*      */     } 
/* 1575 */     return false;
/*      */   }
/*      */   
/*      */   private boolean trapdoorUsableAsLadder(BlockPos $$0, BlockState $$1) {
/* 1579 */     if (((Boolean)$$1.getValue((Property)TrapDoorBlock.OPEN)).booleanValue()) {
/* 1580 */       BlockState $$2 = level().getBlockState($$0.below());
/* 1581 */       if ($$2.is(Blocks.LADDER) && $$2.getValue((Property)LadderBlock.FACING) == $$1.getValue((Property)TrapDoorBlock.FACING)) {
/* 1582 */         return true;
/*      */       }
/*      */     } 
/* 1585 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAlive() {
/* 1593 */     return (!isRemoved() && getHealth() > 0.0F);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean causeFallDamage(float $$0, float $$1, DamageSource $$2) {
/* 1598 */     boolean $$3 = super.causeFallDamage($$0, $$1, $$2);
/* 1599 */     int $$4 = calculateFallDamage($$0, $$1);
/*      */     
/* 1601 */     if ($$4 > 0) {
/* 1602 */       playSound(getFallDamageSound($$4), 1.0F, 1.0F);
/* 1603 */       playBlockFallSound();
/* 1604 */       hurt($$2, $$4);
/* 1605 */       return true;
/*      */     } 
/* 1607 */     return $$3;
/*      */   }
/*      */   
/*      */   protected int calculateFallDamage(float $$0, float $$1) {
/* 1611 */     if (getType().is(EntityTypeTags.FALL_DAMAGE_IMMUNE)) {
/* 1612 */       return 0;
/*      */     }
/* 1614 */     MobEffectInstance $$2 = getEffect(MobEffects.JUMP);
/* 1615 */     float $$3 = ($$2 == null) ? 0.0F : ($$2.getAmplifier() + 1);
/* 1616 */     return Mth.ceil(($$0 - 3.0F - $$3) * $$1);
/*      */   }
/*      */   
/*      */   protected void playBlockFallSound() {
/* 1620 */     if (isSilent()) {
/*      */       return;
/*      */     }
/* 1623 */     int $$0 = Mth.floor(getX());
/* 1624 */     int $$1 = Mth.floor(getY() - 0.20000000298023224D);
/* 1625 */     int $$2 = Mth.floor(getZ());
/*      */     
/* 1627 */     BlockState $$3 = level().getBlockState(new BlockPos($$0, $$1, $$2));
/* 1628 */     if (!$$3.isAir()) {
/* 1629 */       SoundType $$4 = $$3.getSoundType();
/* 1630 */       playSound($$4.getFallSound(), $$4.getVolume() * 0.5F, $$4.getPitch() * 0.75F);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void animateHurt(float $$0) {
/* 1636 */     this.hurtDuration = 10;
/* 1637 */     this.hurtTime = this.hurtDuration;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getArmorValue() {
/* 1646 */     return Mth.floor(getAttributeValue(Attributes.ARMOR));
/*      */   }
/*      */ 
/*      */   
/*      */   protected void hurtArmor(DamageSource $$0, float $$1) {}
/*      */ 
/*      */   
/*      */   protected void hurtHelmet(DamageSource $$0, float $$1) {}
/*      */ 
/*      */   
/*      */   protected void hurtCurrentlyUsedShield(float $$0) {}
/*      */   
/*      */   protected float getDamageAfterArmorAbsorb(DamageSource $$0, float $$1) {
/* 1659 */     if (!$$0.is(DamageTypeTags.BYPASSES_ARMOR)) {
/* 1660 */       hurtArmor($$0, $$1);
/* 1661 */       $$1 = CombatRules.getDamageAfterAbsorb($$1, getArmorValue(), (float)getAttributeValue(Attributes.ARMOR_TOUGHNESS));
/*      */     } 
/* 1663 */     return $$1;
/*      */   }
/*      */   
/*      */   protected float getDamageAfterMagicAbsorb(DamageSource $$0, float $$1) {
/* 1667 */     if ($$0.is(DamageTypeTags.BYPASSES_EFFECTS)) {
/* 1668 */       return $$1;
/*      */     }
/*      */     
/* 1671 */     if (hasEffect(MobEffects.DAMAGE_RESISTANCE) && !$$0.is(DamageTypeTags.BYPASSES_RESISTANCE)) {
/* 1672 */       int $$2 = (getEffect(MobEffects.DAMAGE_RESISTANCE).getAmplifier() + 1) * 5;
/* 1673 */       int $$3 = 25 - $$2;
/* 1674 */       float $$4 = $$1 * $$3;
/* 1675 */       float $$5 = $$1;
/* 1676 */       $$1 = Math.max($$4 / 25.0F, 0.0F);
/*      */       
/* 1678 */       float $$6 = $$5 - $$1;
/* 1679 */       if ($$6 > 0.0F && $$6 < 3.4028235E37F) {
/* 1680 */         if (this instanceof ServerPlayer) {
/* 1681 */           ((ServerPlayer)this).awardStat(Stats.DAMAGE_RESISTED, Math.round($$6 * 10.0F));
/* 1682 */         } else if ($$0.getEntity() instanceof ServerPlayer) {
/* 1683 */           ((ServerPlayer)$$0.getEntity()).awardStat(Stats.DAMAGE_DEALT_RESISTED, Math.round($$6 * 10.0F));
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/* 1688 */     if ($$1 <= 0.0F) {
/* 1689 */       return 0.0F;
/*      */     }
/*      */     
/* 1692 */     if ($$0.is(DamageTypeTags.BYPASSES_ENCHANTMENTS)) {
/* 1693 */       return $$1;
/*      */     }
/*      */     
/* 1696 */     int $$7 = EnchantmentHelper.getDamageProtection(getArmorSlots(), $$0);
/* 1697 */     if ($$7 > 0) {
/* 1698 */       $$1 = CombatRules.getDamageAfterMagicAbsorb($$1, $$7);
/*      */     }
/*      */     
/* 1701 */     return $$1;
/*      */   }
/*      */   
/*      */   protected void actuallyHurt(DamageSource $$0, float $$1) {
/* 1705 */     if (isInvulnerableTo($$0)) {
/*      */       return;
/*      */     }
/* 1708 */     $$1 = getDamageAfterArmorAbsorb($$0, $$1);
/* 1709 */     $$1 = getDamageAfterMagicAbsorb($$0, $$1);
/*      */     
/* 1711 */     float $$2 = $$1;
/* 1712 */     $$1 = Math.max($$1 - getAbsorptionAmount(), 0.0F);
/* 1713 */     setAbsorptionAmount(getAbsorptionAmount() - $$2 - $$1);
/*      */     
/* 1715 */     float $$3 = $$2 - $$1;
/* 1716 */     if ($$3 > 0.0F && $$3 < 3.4028235E37F) { Entity entity = $$0.getEntity(); if (entity instanceof ServerPlayer) { ServerPlayer $$4 = (ServerPlayer)entity;
/* 1717 */         $$4.awardStat(Stats.DAMAGE_DEALT_ABSORBED, Math.round($$3 * 10.0F)); }
/*      */        }
/*      */     
/* 1720 */     if ($$1 == 0.0F) {
/*      */       return;
/*      */     }
/*      */     
/* 1724 */     getCombatTracker().recordDamage($$0, $$1);
/* 1725 */     setHealth(getHealth() - $$1);
/* 1726 */     setAbsorptionAmount(getAbsorptionAmount() - $$1);
/* 1727 */     gameEvent(GameEvent.ENTITY_DAMAGE);
/*      */   }
/*      */   
/*      */   public CombatTracker getCombatTracker() {
/* 1731 */     return this.combatTracker;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public LivingEntity getKillCredit() {
/* 1736 */     if (this.lastHurtByPlayer != null) {
/* 1737 */       return (LivingEntity)this.lastHurtByPlayer;
/*      */     }
/* 1739 */     if (this.lastHurtByMob != null) {
/* 1740 */       return this.lastHurtByMob;
/*      */     }
/* 1742 */     return null;
/*      */   }
/*      */   
/*      */   public final float getMaxHealth() {
/* 1746 */     return (float)getAttributeValue(Attributes.MAX_HEALTH);
/*      */   }
/*      */   
/*      */   public final float getMaxAbsorption() {
/* 1750 */     return (float)getAttributeValue(Attributes.MAX_ABSORPTION);
/*      */   }
/*      */   
/*      */   public final int getArrowCount() {
/* 1754 */     return ((Integer)this.entityData.get(DATA_ARROW_COUNT_ID)).intValue();
/*      */   }
/*      */   
/*      */   public final void setArrowCount(int $$0) {
/* 1758 */     this.entityData.set(DATA_ARROW_COUNT_ID, Integer.valueOf($$0));
/*      */   }
/*      */   
/*      */   public final int getStingerCount() {
/* 1762 */     return ((Integer)this.entityData.get(DATA_STINGER_COUNT_ID)).intValue();
/*      */   }
/*      */   
/*      */   public final void setStingerCount(int $$0) {
/* 1766 */     this.entityData.set(DATA_STINGER_COUNT_ID, Integer.valueOf($$0));
/*      */   }
/*      */   
/*      */   private int getCurrentSwingDuration() {
/* 1770 */     if (MobEffectUtil.hasDigSpeed(this)) {
/* 1771 */       return 6 - 1 + MobEffectUtil.getDigSpeedAmplification(this);
/*      */     }
/* 1773 */     if (hasEffect(MobEffects.DIG_SLOWDOWN)) {
/* 1774 */       return 6 + (1 + getEffect(MobEffects.DIG_SLOWDOWN).getAmplifier()) * 2;
/*      */     }
/* 1776 */     return 6;
/*      */   }
/*      */   
/*      */   public void swing(InteractionHand $$0) {
/* 1780 */     swing($$0, false);
/*      */   }
/*      */   
/*      */   public void swing(InteractionHand $$0, boolean $$1) {
/* 1784 */     if (!this.swinging || this.swingTime >= getCurrentSwingDuration() / 2 || this.swingTime < 0) {
/* 1785 */       this.swingTime = -1;
/* 1786 */       this.swinging = true;
/* 1787 */       this.swingingArm = $$0;
/*      */       
/* 1789 */       if (level() instanceof ServerLevel) {
/* 1790 */         ClientboundAnimatePacket $$2 = new ClientboundAnimatePacket(this, ($$0 == InteractionHand.MAIN_HAND) ? 0 : 3);
/* 1791 */         ServerChunkCache $$3 = ((ServerLevel)level()).getChunkSource();
/*      */         
/* 1793 */         if ($$1) {
/* 1794 */           $$3.broadcastAndSend(this, (Packet)$$2);
/*      */         } else {
/* 1796 */           $$3.broadcast(this, (Packet)$$2);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleDamageEvent(DamageSource $$0) {
/* 1804 */     this.walkAnimation.setSpeed(1.5F);
/*      */     
/* 1806 */     this.invulnerableTime = 20;
/* 1807 */     this.hurtDuration = 10;
/* 1808 */     this.hurtTime = this.hurtDuration;
/*      */     
/* 1810 */     SoundEvent $$1 = getHurtSound($$0);
/* 1811 */     if ($$1 != null) {
/* 1812 */       playSound($$1, getSoundVolume(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
/*      */     }
/* 1814 */     hurt(damageSources().generic(), 0.0F);
/* 1815 */     this.lastDamageSource = $$0;
/* 1816 */     this.lastDamageStamp = level().getGameTime();
/*      */   }
/*      */   public void handleEntityEvent(byte $$0) {
/*      */     SoundEvent $$1;
/*      */     int $$2, $$3;
/* 1821 */     switch ($$0) {
/*      */       case 3:
/* 1823 */         $$1 = getDeathSound();
/* 1824 */         if ($$1 != null) {
/* 1825 */           playSound($$1, getSoundVolume(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
/*      */         }
/*      */         
/* 1828 */         if (!(this instanceof Player)) {
/* 1829 */           setHealth(0.0F);
/* 1830 */           die(damageSources().generic());
/*      */         } 
/*      */         return;
/*      */       
/*      */       case 30:
/* 1835 */         playSound(SoundEvents.SHIELD_BREAK, 0.8F, 0.8F + (level()).random.nextFloat() * 0.4F);
/*      */         return;
/*      */       case 29:
/* 1838 */         playSound(SoundEvents.SHIELD_BLOCK, 1.0F, 0.8F + (level()).random.nextFloat() * 0.4F);
/*      */         return;
/*      */       case 46:
/* 1841 */         $$2 = 128;
/*      */         
/* 1843 */         for ($$3 = 0; $$3 < 128; $$3++) {
/* 1844 */           double $$4 = $$3 / 127.0D;
/* 1845 */           float $$5 = (this.random.nextFloat() - 0.5F) * 0.2F;
/* 1846 */           float $$6 = (this.random.nextFloat() - 0.5F) * 0.2F;
/* 1847 */           float $$7 = (this.random.nextFloat() - 0.5F) * 0.2F;
/*      */           
/* 1849 */           double $$8 = Mth.lerp($$4, this.xo, getX()) + (this.random.nextDouble() - 0.5D) * getBbWidth() * 2.0D;
/* 1850 */           double $$9 = Mth.lerp($$4, this.yo, getY()) + this.random.nextDouble() * getBbHeight();
/* 1851 */           double $$10 = Mth.lerp($$4, this.zo, getZ()) + (this.random.nextDouble() - 0.5D) * getBbWidth() * 2.0D;
/* 1852 */           level().addParticle((ParticleOptions)ParticleTypes.PORTAL, $$8, $$9, $$10, $$5, $$6, $$7);
/*      */         } 
/*      */         return;
/*      */       
/*      */       case 47:
/* 1857 */         breakItem(getItemBySlot(EquipmentSlot.MAINHAND));
/*      */         return;
/*      */       case 48:
/* 1860 */         breakItem(getItemBySlot(EquipmentSlot.OFFHAND));
/*      */         return;
/*      */       case 49:
/* 1863 */         breakItem(getItemBySlot(EquipmentSlot.HEAD));
/*      */         return;
/*      */       case 50:
/* 1866 */         breakItem(getItemBySlot(EquipmentSlot.CHEST));
/*      */         return;
/*      */       case 51:
/* 1869 */         breakItem(getItemBySlot(EquipmentSlot.LEGS));
/*      */         return;
/*      */       case 52:
/* 1872 */         breakItem(getItemBySlot(EquipmentSlot.FEET));
/*      */         return;
/*      */       case 54:
/* 1875 */         HoneyBlock.showJumpParticles(this);
/*      */         return;
/*      */       case 55:
/* 1878 */         swapHandItems();
/*      */         return;
/*      */       case 60:
/* 1881 */         makePoofParticles();
/*      */         return;
/*      */     } 
/* 1884 */     super.handleEntityEvent($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   private void makePoofParticles() {
/* 1889 */     for (int $$0 = 0; $$0 < 20; $$0++) {
/* 1890 */       double $$1 = this.random.nextGaussian() * 0.02D;
/* 1891 */       double $$2 = this.random.nextGaussian() * 0.02D;
/* 1892 */       double $$3 = this.random.nextGaussian() * 0.02D;
/* 1893 */       level().addParticle((ParticleOptions)ParticleTypes.POOF, getRandomX(1.0D), getRandomY(), getRandomZ(1.0D), $$1, $$2, $$3);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void swapHandItems() {
/* 1898 */     ItemStack $$0 = getItemBySlot(EquipmentSlot.OFFHAND);
/* 1899 */     setItemSlot(EquipmentSlot.OFFHAND, getItemBySlot(EquipmentSlot.MAINHAND));
/* 1900 */     setItemSlot(EquipmentSlot.MAINHAND, $$0);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void onBelowWorld() {
/* 1905 */     hurt(damageSources().fellOutOfWorld(), 4.0F);
/*      */   }
/*      */   
/*      */   protected void updateSwingTime() {
/* 1909 */     int $$0 = getCurrentSwingDuration();
/* 1910 */     if (this.swinging) {
/* 1911 */       this.swingTime++;
/* 1912 */       if (this.swingTime >= $$0) {
/* 1913 */         this.swingTime = 0;
/* 1914 */         this.swinging = false;
/*      */       } 
/*      */     } else {
/* 1917 */       this.swingTime = 0;
/*      */     } 
/*      */     
/* 1920 */     this.attackAnim = this.swingTime / $$0;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public AttributeInstance getAttribute(Attribute $$0) {
/* 1925 */     return getAttributes().getInstance($$0);
/*      */   }
/*      */   
/*      */   public double getAttributeValue(Holder<Attribute> $$0) {
/* 1929 */     return getAttributeValue((Attribute)$$0.value());
/*      */   }
/*      */   
/*      */   public double getAttributeValue(Attribute $$0) {
/* 1933 */     return getAttributes().getValue($$0);
/*      */   }
/*      */   
/*      */   public double getAttributeBaseValue(Holder<Attribute> $$0) {
/* 1937 */     return getAttributeBaseValue((Attribute)$$0.value());
/*      */   }
/*      */   
/*      */   public double getAttributeBaseValue(Attribute $$0) {
/* 1941 */     return getAttributes().getBaseValue($$0);
/*      */   }
/*      */   
/*      */   public AttributeMap getAttributes() {
/* 1945 */     return this.attributes;
/*      */   }
/*      */   
/*      */   public MobType getMobType() {
/* 1949 */     return MobType.UNDEFINED;
/*      */   }
/*      */   
/*      */   public ItemStack getMainHandItem() {
/* 1953 */     return getItemBySlot(EquipmentSlot.MAINHAND);
/*      */   }
/*      */   
/*      */   public ItemStack getOffhandItem() {
/* 1957 */     return getItemBySlot(EquipmentSlot.OFFHAND);
/*      */   }
/*      */   
/*      */   public boolean isHolding(Item $$0) {
/* 1961 */     return isHolding($$1 -> $$1.is($$0));
/*      */   }
/*      */   
/*      */   public boolean isHolding(Predicate<ItemStack> $$0) {
/* 1965 */     return ($$0.test(getMainHandItem()) || $$0.test(getOffhandItem()));
/*      */   }
/*      */   
/*      */   public ItemStack getItemInHand(InteractionHand $$0) {
/* 1969 */     if ($$0 == InteractionHand.MAIN_HAND)
/* 1970 */       return getItemBySlot(EquipmentSlot.MAINHAND); 
/* 1971 */     if ($$0 == InteractionHand.OFF_HAND) {
/* 1972 */       return getItemBySlot(EquipmentSlot.OFFHAND);
/*      */     }
/* 1974 */     throw new IllegalArgumentException("Invalid hand " + $$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setItemInHand(InteractionHand $$0, ItemStack $$1) {
/* 1979 */     if ($$0 == InteractionHand.MAIN_HAND) {
/* 1980 */       setItemSlot(EquipmentSlot.MAINHAND, $$1);
/* 1981 */     } else if ($$0 == InteractionHand.OFF_HAND) {
/* 1982 */       setItemSlot(EquipmentSlot.OFFHAND, $$1);
/*      */     } else {
/* 1984 */       throw new IllegalArgumentException("Invalid hand " + $$0);
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean hasItemInSlot(EquipmentSlot $$0) {
/* 1989 */     return !getItemBySlot($$0).isEmpty();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void verifyEquippedItem(ItemStack $$0) {
/* 2001 */     CompoundTag $$1 = $$0.getTag();
/* 2002 */     if ($$1 != null) {
/* 2003 */       $$0.getItem().verifyTagAfterLoad($$1);
/*      */     }
/*      */   }
/*      */   
/*      */   public float getArmorCoverPercentage() {
/* 2008 */     Iterable<ItemStack> $$0 = getArmorSlots();
/*      */     
/* 2010 */     int $$1 = 0;
/* 2011 */     int $$2 = 0;
/* 2012 */     for (ItemStack $$3 : $$0) {
/* 2013 */       if (!$$3.isEmpty()) {
/* 2014 */         $$2++;
/*      */       }
/* 2016 */       $$1++;
/*      */     } 
/* 2018 */     return ($$1 > 0) ? ($$2 / $$1) : 0.0F;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setSprinting(boolean $$0) {
/* 2023 */     super.setSprinting($$0);
/*      */     
/* 2025 */     AttributeInstance $$1 = getAttribute(Attributes.MOVEMENT_SPEED);
/* 2026 */     $$1.removeModifier(SPEED_MODIFIER_SPRINTING.getId());
/* 2027 */     if ($$0) {
/* 2028 */       $$1.addTransientModifier(SPEED_MODIFIER_SPRINTING);
/*      */     }
/*      */   }
/*      */   
/*      */   protected float getSoundVolume() {
/* 2033 */     return 1.0F;
/*      */   }
/*      */   
/*      */   public float getVoicePitch() {
/* 2037 */     if (isBaby()) {
/* 2038 */       return (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.5F;
/*      */     }
/* 2040 */     return (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F;
/*      */   }
/*      */   
/*      */   protected boolean isImmobile() {
/* 2044 */     return isDeadOrDying();
/*      */   }
/*      */ 
/*      */   
/*      */   public void push(Entity $$0) {
/* 2049 */     if (!isSleeping()) {
/* 2050 */       super.push($$0);
/*      */     }
/*      */   }
/*      */   
/*      */   private void dismountVehicle(Entity $$0) {
/*      */     Vec3 $$4;
/* 2056 */     if (isRemoved()) {
/* 2057 */       Vec3 $$1 = position();
/* 2058 */     } else if ($$0.isRemoved() || level().getBlockState($$0.blockPosition()).is(BlockTags.PORTALS)) {
/*      */ 
/*      */       
/* 2061 */       double $$2 = Math.max(getY(), $$0.getY());
/* 2062 */       Vec3 $$3 = new Vec3(getX(), $$2, getZ());
/*      */     } else {
/* 2064 */       $$4 = $$0.getDismountLocationForPassenger(this);
/*      */     } 
/*      */     
/* 2067 */     dismountTo($$4.x, $$4.y, $$4.z);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean shouldShowName() {
/* 2072 */     return isCustomNameVisible();
/*      */   }
/*      */   
/*      */   protected float getJumpPower() {
/* 2076 */     return 0.42F * getBlockJumpFactor() + getJumpBoostPower();
/*      */   }
/*      */   
/*      */   public float getJumpBoostPower() {
/* 2080 */     return hasEffect(MobEffects.JUMP) ? (0.1F * (getEffect(MobEffects.JUMP).getAmplifier() + 1.0F)) : 0.0F;
/*      */   }
/*      */   
/*      */   protected void jumpFromGround() {
/* 2084 */     Vec3 $$0 = getDeltaMovement();
/* 2085 */     setDeltaMovement($$0.x, getJumpPower(), $$0.z);
/*      */     
/* 2087 */     if (isSprinting()) {
/* 2088 */       float $$1 = getYRot() * 0.017453292F;
/*      */       
/* 2090 */       setDeltaMovement(getDeltaMovement().add((
/* 2091 */             -Mth.sin($$1) * 0.2F), 0.0D, (
/*      */             
/* 2093 */             Mth.cos($$1) * 0.2F)));
/*      */     } 
/*      */     
/* 2096 */     this.hasImpulse = true;
/*      */   }
/*      */   
/*      */   protected void goDownInWater() {
/* 2100 */     setDeltaMovement(getDeltaMovement().add(0.0D, -0.03999999910593033D, 0.0D));
/*      */   }
/*      */   
/*      */   protected void jumpInLiquid(TagKey<Fluid> $$0) {
/* 2104 */     setDeltaMovement(getDeltaMovement().add(0.0D, 0.03999999910593033D, 0.0D));
/*      */   }
/*      */   
/*      */   protected float getWaterSlowDown() {
/* 2108 */     return 0.8F;
/*      */   }
/*      */   
/*      */   public boolean canStandOnFluid(FluidState $$0) {
/* 2112 */     return false;
/*      */   }
/*      */   
/*      */   public void travel(Vec3 $$0) {
/* 2116 */     if (isControlledByLocalInstance()) {
/* 2117 */       double $$1 = 0.08D;
/* 2118 */       boolean $$2 = ((getDeltaMovement()).y <= 0.0D);
/* 2119 */       if ($$2 && hasEffect(MobEffects.SLOW_FALLING)) {
/* 2120 */         $$1 = 0.01D;
/*      */       }
/*      */       
/* 2123 */       FluidState $$3 = level().getFluidState(blockPosition());
/*      */       
/* 2125 */       if (isInWater() && isAffectedByFluids() && !canStandOnFluid($$3)) {
/* 2126 */         double $$4 = getY();
/*      */         
/* 2128 */         float $$5 = isSprinting() ? 0.9F : getWaterSlowDown();
/* 2129 */         float $$6 = 0.02F;
/*      */         
/* 2131 */         float $$7 = EnchantmentHelper.getDepthStrider(this);
/* 2132 */         if ($$7 > 3.0F) {
/* 2133 */           $$7 = 3.0F;
/*      */         }
/* 2135 */         if (!onGround()) {
/* 2136 */           $$7 *= 0.5F;
/*      */         }
/* 2138 */         if ($$7 > 0.0F) {
/*      */           
/* 2140 */           $$5 += (0.54600006F - $$5) * $$7 / 3.0F;
/*      */           
/* 2142 */           $$6 += (getSpeed() - $$6) * $$7 / 3.0F;
/*      */         } 
/*      */         
/* 2145 */         if (hasEffect(MobEffects.DOLPHINS_GRACE)) {
/* 2146 */           $$5 = 0.96F;
/*      */         }
/*      */         
/* 2149 */         moveRelative($$6, $$0);
/* 2150 */         move(MoverType.SELF, getDeltaMovement());
/*      */         
/* 2152 */         Vec3 $$8 = getDeltaMovement();
/* 2153 */         if (this.horizontalCollision && onClimbable()) {
/* 2154 */           $$8 = new Vec3($$8.x, 0.2D, $$8.z);
/*      */         }
/*      */         
/* 2157 */         setDeltaMovement($$8.multiply($$5, 0.800000011920929D, $$5));
/* 2158 */         Vec3 $$9 = getFluidFallingAdjustedMovement($$1, $$2, getDeltaMovement());
/* 2159 */         setDeltaMovement($$9);
/*      */         
/* 2161 */         if (this.horizontalCollision && isFree($$9.x, $$9.y + 0.6000000238418579D - getY() + $$4, $$9.z)) {
/* 2162 */           setDeltaMovement($$9.x, 0.30000001192092896D, $$9.z);
/*      */         }
/* 2164 */       } else if (isInLava() && isAffectedByFluids() && !canStandOnFluid($$3)) {
/* 2165 */         double $$10 = getY();
/* 2166 */         moveRelative(0.02F, $$0);
/* 2167 */         move(MoverType.SELF, getDeltaMovement());
/*      */ 
/*      */ 
/*      */         
/* 2171 */         if (getFluidHeight(FluidTags.LAVA) <= getFluidJumpThreshold()) {
/* 2172 */           setDeltaMovement(getDeltaMovement().multiply(0.5D, 0.800000011920929D, 0.5D));
/* 2173 */           Vec3 $$11 = getFluidFallingAdjustedMovement($$1, $$2, getDeltaMovement());
/* 2174 */           setDeltaMovement($$11);
/*      */         } else {
/* 2176 */           setDeltaMovement(getDeltaMovement().scale(0.5D));
/*      */         } 
/*      */         
/* 2179 */         if (!isNoGravity()) {
/* 2180 */           setDeltaMovement(getDeltaMovement().add(0.0D, -$$1 / 4.0D, 0.0D));
/*      */         }
/*      */         
/* 2183 */         Vec3 $$12 = getDeltaMovement();
/* 2184 */         if (this.horizontalCollision && isFree($$12.x, $$12.y + 0.6000000238418579D - getY() + $$10, $$12.z)) {
/* 2185 */           setDeltaMovement($$12.x, 0.30000001192092896D, $$12.z);
/*      */         }
/* 2187 */       } else if (isFallFlying()) {
/*      */         
/* 2189 */         checkSlowFallDistance();
/*      */         
/* 2191 */         Vec3 $$13 = getDeltaMovement();
/* 2192 */         Vec3 $$14 = getLookAngle();
/* 2193 */         float $$15 = getXRot() * 0.017453292F;
/* 2194 */         double $$16 = Math.sqrt($$14.x * $$14.x + $$14.z * $$14.z);
/* 2195 */         double $$17 = $$13.horizontalDistance();
/* 2196 */         double $$18 = $$14.length();
/*      */ 
/*      */         
/* 2199 */         double $$19 = Math.cos($$15);
/* 2200 */         $$19 = $$19 * $$19 * Math.min(1.0D, $$18 / 0.4D);
/* 2201 */         $$13 = getDeltaMovement().add(0.0D, $$1 * (-1.0D + $$19 * 0.75D), 0.0D);
/*      */ 
/*      */         
/* 2204 */         if ($$13.y < 0.0D && $$16 > 0.0D) {
/* 2205 */           double $$20 = $$13.y * -0.1D * $$19;
/* 2206 */           $$13 = $$13.add($$14.x * $$20 / $$16, $$20, $$14.z * $$20 / $$16);
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2213 */         if ($$15 < 0.0F && $$16 > 0.0D) {
/* 2214 */           double $$21 = $$17 * -Mth.sin($$15) * 0.04D;
/*      */           
/* 2216 */           $$13 = $$13.add(-$$14.x * $$21 / $$16, $$21 * 3.2D, -$$14.z * $$21 / $$16);
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2224 */         if ($$16 > 0.0D) {
/* 2225 */           $$13 = $$13.add(($$14.x / $$16 * $$17 - $$13.x) * 0.1D, 0.0D, ($$14.z / $$16 * $$17 - $$13.z) * 0.1D);
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2232 */         setDeltaMovement($$13.multiply(0.9900000095367432D, 0.9800000190734863D, 0.9900000095367432D));
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2237 */         move(MoverType.SELF, getDeltaMovement());
/*      */         
/* 2239 */         if (this.horizontalCollision && !(level()).isClientSide) {
/* 2240 */           double $$22 = getDeltaMovement().horizontalDistance();
/* 2241 */           double $$23 = $$17 - $$22;
/* 2242 */           float $$24 = (float)($$23 * 10.0D - 3.0D);
/*      */           
/* 2244 */           if ($$24 > 0.0F) {
/* 2245 */             playSound(getFallDamageSound((int)$$24), 1.0F, 1.0F);
/* 2246 */             hurt(damageSources().flyIntoWall(), $$24);
/*      */           } 
/*      */         } 
/*      */         
/* 2250 */         if (onGround() && !(level()).isClientSide) {
/* 2251 */           setSharedFlag(7, false);
/*      */         }
/*      */       } else {
/* 2254 */         BlockPos $$25 = getBlockPosBelowThatAffectsMyMovement();
/*      */         
/* 2256 */         float $$26 = level().getBlockState($$25).getBlock().getFriction();
/* 2257 */         float $$27 = onGround() ? ($$26 * 0.91F) : 0.91F;
/*      */         
/* 2259 */         Vec3 $$28 = handleRelativeFrictionAndCalculateMovement($$0, $$26);
/*      */         
/* 2261 */         double $$29 = $$28.y;
/* 2262 */         if (hasEffect(MobEffects.LEVITATION)) {
/* 2263 */           $$29 += (0.05D * (getEffect(MobEffects.LEVITATION).getAmplifier() + 1) - $$28.y) * 0.2D;
/*      */         }
/* 2265 */         else if (!(level()).isClientSide || level().hasChunkAt($$25)) {
/* 2266 */           if (!isNoGravity()) {
/* 2267 */             $$29 -= $$1;
/*      */           }
/* 2269 */         } else if (getY() > level().getMinBuildHeight()) {
/* 2270 */           $$29 = -0.1D;
/*      */         } else {
/* 2272 */           $$29 = 0.0D;
/*      */         } 
/*      */ 
/*      */         
/* 2276 */         if (shouldDiscardFriction()) {
/* 2277 */           setDeltaMovement($$28.x, $$29, $$28.z);
/*      */         
/*      */         }
/*      */         else {
/*      */ 
/*      */           
/* 2283 */           setDeltaMovement($$28.x * $$27, $$29 * 0.9800000190734863D, $$28.z * $$27);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2291 */     calculateEntityAnimation(this instanceof net.minecraft.world.entity.animal.FlyingAnimal);
/*      */   }
/*      */   
/*      */   private void travelRidden(Player $$0, Vec3 $$1) {
/* 2295 */     Vec3 $$2 = getRiddenInput($$0, $$1);
/* 2296 */     tickRidden($$0, $$2);
/*      */     
/* 2298 */     if (isControlledByLocalInstance()) {
/* 2299 */       setSpeed(getRiddenSpeed($$0));
/* 2300 */       travel($$2);
/*      */     } else {
/* 2302 */       calculateEntityAnimation(false);
/* 2303 */       setDeltaMovement(Vec3.ZERO);
/*      */ 
/*      */       
/* 2306 */       tryCheckInsideBlocks();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void tickRidden(Player $$0, Vec3 $$1) {}
/*      */   
/*      */   protected Vec3 getRiddenInput(Player $$0, Vec3 $$1) {
/* 2314 */     return $$1;
/*      */   }
/*      */   
/*      */   protected float getRiddenSpeed(Player $$0) {
/* 2318 */     return getSpeed();
/*      */   }
/*      */   
/*      */   public void calculateEntityAnimation(boolean $$0) {
/* 2322 */     float $$1 = (float)Mth.length(
/* 2323 */         getX() - this.xo, 
/* 2324 */         $$0 ? (getY() - this.yo) : 0.0D, 
/* 2325 */         getZ() - this.zo);
/*      */     
/* 2327 */     updateWalkAnimation($$1);
/*      */   }
/*      */   
/*      */   protected void updateWalkAnimation(float $$0) {
/* 2331 */     float $$1 = Math.min($$0 * 4.0F, 1.0F);
/* 2332 */     this.walkAnimation.update($$1, 0.4F);
/*      */   }
/*      */   
/*      */   public Vec3 handleRelativeFrictionAndCalculateMovement(Vec3 $$0, float $$1) {
/* 2336 */     moveRelative(getFrictionInfluencedSpeed($$1), $$0);
/*      */     
/* 2338 */     setDeltaMovement(handleOnClimbable(getDeltaMovement()));
/* 2339 */     move(MoverType.SELF, getDeltaMovement());
/*      */     
/* 2341 */     Vec3 $$2 = getDeltaMovement();
/* 2342 */     if ((this.horizontalCollision || this.jumping) && (onClimbable() || (getFeetBlockState().is(Blocks.POWDER_SNOW) && PowderSnowBlock.canEntityWalkOnPowderSnow(this)))) {
/* 2343 */       $$2 = new Vec3($$2.x, 0.2D, $$2.z);
/*      */     }
/* 2345 */     return $$2;
/*      */   }
/*      */   
/*      */   public Vec3 getFluidFallingAdjustedMovement(double $$0, boolean $$1, Vec3 $$2) {
/* 2349 */     if (!isNoGravity() && !isSprinting()) {
/*      */       double $$4;
/* 2351 */       if ($$1 && Math.abs($$2.y - 0.005D) >= 0.003D && Math.abs($$2.y - $$0 / 16.0D) < 0.003D) {
/*      */         
/* 2353 */         double $$3 = -0.003D;
/*      */       } else {
/* 2355 */         $$4 = $$2.y - $$0 / 16.0D;
/*      */       } 
/* 2357 */       return new Vec3($$2.x, $$4, $$2.z);
/*      */     } 
/* 2359 */     return $$2;
/*      */   }
/*      */   
/*      */   private Vec3 handleOnClimbable(Vec3 $$0) {
/* 2363 */     if (onClimbable()) {
/* 2364 */       resetFallDistance();
/*      */       
/* 2366 */       float $$1 = 0.15F;
/* 2367 */       double $$2 = Mth.clamp($$0.x, -0.15000000596046448D, 0.15000000596046448D);
/* 2368 */       double $$3 = Mth.clamp($$0.z, -0.15000000596046448D, 0.15000000596046448D);
/*      */       
/* 2370 */       double $$4 = Math.max($$0.y, -0.15000000596046448D);
/* 2371 */       if ($$4 < 0.0D && !getFeetBlockState().is(Blocks.SCAFFOLDING) && isSuppressingSlidingDownLadder() && this instanceof Player) {
/* 2372 */         $$4 = 0.0D;
/*      */       }
/*      */       
/* 2375 */       $$0 = new Vec3($$2, $$4, $$3);
/*      */     } 
/* 2377 */     return $$0;
/*      */   }
/*      */   
/*      */   private float getFrictionInfluencedSpeed(float $$0) {
/* 2381 */     if (onGround()) {
/* 2382 */       return getSpeed() * 0.21600002F / $$0 * $$0 * $$0;
/*      */     }
/* 2384 */     return getFlyingSpeed();
/*      */   }
/*      */ 
/*      */   
/*      */   protected float getFlyingSpeed() {
/* 2389 */     return (getControllingPassenger() instanceof Player) ? (getSpeed() * 0.1F) : 0.02F;
/*      */   }
/*      */   
/*      */   public float getSpeed() {
/* 2393 */     return this.speed;
/*      */   }
/*      */   
/*      */   public void setSpeed(float $$0) {
/* 2397 */     this.speed = $$0;
/*      */   }
/*      */   
/*      */   public boolean doHurtTarget(Entity $$0) {
/* 2401 */     setLastHurtMob($$0);
/* 2402 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public void tick() {
/* 2407 */     super.tick();
/* 2408 */     updatingUsingItem();
/* 2409 */     updateSwimAmount();
/*      */     
/* 2411 */     if (!(level()).isClientSide) {
/* 2412 */       int $$0 = getArrowCount();
/* 2413 */       if ($$0 > 0) {
/* 2414 */         if (this.removeArrowTime <= 0) {
/* 2415 */           this.removeArrowTime = 20 * (30 - $$0);
/*      */         }
/* 2417 */         this.removeArrowTime--;
/* 2418 */         if (this.removeArrowTime <= 0) {
/* 2419 */           setArrowCount($$0 - 1);
/*      */         }
/*      */       } 
/*      */       
/* 2423 */       int $$1 = getStingerCount();
/* 2424 */       if ($$1 > 0) {
/* 2425 */         if (this.removeStingerTime <= 0) {
/* 2426 */           this.removeStingerTime = 20 * (30 - $$1);
/*      */         }
/* 2428 */         this.removeStingerTime--;
/* 2429 */         if (this.removeStingerTime <= 0) {
/* 2430 */           setStingerCount($$1 - 1);
/*      */         }
/*      */       } 
/*      */       
/* 2434 */       detectEquipmentUpdates();
/*      */       
/* 2436 */       if (this.tickCount % 20 == 0) {
/* 2437 */         getCombatTracker().recheckStatus();
/*      */       }
/*      */       
/* 2440 */       if (isSleeping() && !checkBedExists()) {
/* 2441 */         stopSleeping();
/*      */       }
/*      */     } 
/*      */     
/* 2445 */     if (!isRemoved()) {
/* 2446 */       aiStep();
/*      */     }
/*      */     
/* 2449 */     double $$2 = getX() - this.xo;
/* 2450 */     double $$3 = getZ() - this.zo;
/*      */     
/* 2452 */     float $$4 = (float)($$2 * $$2 + $$3 * $$3);
/*      */     
/* 2454 */     float $$5 = this.yBodyRot;
/*      */     
/* 2456 */     float $$6 = 0.0F;
/* 2457 */     this.oRun = this.run;
/* 2458 */     float $$7 = 0.0F;
/* 2459 */     if ($$4 > 0.0025000002F) {
/* 2460 */       $$7 = 1.0F;
/* 2461 */       $$6 = (float)Math.sqrt($$4) * 3.0F;
/*      */       
/* 2463 */       float $$8 = (float)Mth.atan2($$3, $$2) * 57.295776F - 90.0F;
/* 2464 */       float $$9 = Mth.abs(Mth.wrapDegrees(getYRot()) - $$8);
/* 2465 */       if (95.0F < $$9 && $$9 < 265.0F) {
/* 2466 */         $$5 = $$8 - 180.0F;
/*      */       } else {
/* 2468 */         $$5 = $$8;
/*      */       } 
/*      */     } 
/* 2471 */     if (this.attackAnim > 0.0F) {
/* 2472 */       $$5 = getYRot();
/*      */     }
/* 2474 */     if (!onGround()) {
/* 2475 */       $$7 = 0.0F;
/*      */     }
/* 2477 */     this.run += ($$7 - this.run) * 0.3F;
/*      */     
/* 2479 */     level().getProfiler().push("headTurn");
/*      */     
/* 2481 */     $$6 = tickHeadTurn($$5, $$6);
/*      */     
/* 2483 */     level().getProfiler().pop();
/*      */     
/* 2485 */     level().getProfiler().push("rangeChecks");
/* 2486 */     while (getYRot() - this.yRotO < -180.0F) {
/* 2487 */       this.yRotO -= 360.0F;
/*      */     }
/* 2489 */     while (getYRot() - this.yRotO >= 180.0F) {
/* 2490 */       this.yRotO += 360.0F;
/*      */     }
/*      */     
/* 2493 */     while (this.yBodyRot - this.yBodyRotO < -180.0F) {
/* 2494 */       this.yBodyRotO -= 360.0F;
/*      */     }
/* 2496 */     while (this.yBodyRot - this.yBodyRotO >= 180.0F) {
/* 2497 */       this.yBodyRotO += 360.0F;
/*      */     }
/*      */     
/* 2500 */     while (getXRot() - this.xRotO < -180.0F) {
/* 2501 */       this.xRotO -= 360.0F;
/*      */     }
/* 2503 */     while (getXRot() - this.xRotO >= 180.0F) {
/* 2504 */       this.xRotO += 360.0F;
/*      */     }
/*      */     
/* 2507 */     while (this.yHeadRot - this.yHeadRotO < -180.0F) {
/* 2508 */       this.yHeadRotO -= 360.0F;
/*      */     }
/* 2510 */     while (this.yHeadRot - this.yHeadRotO >= 180.0F) {
/* 2511 */       this.yHeadRotO += 360.0F;
/*      */     }
/* 2513 */     level().getProfiler().pop();
/*      */     
/* 2515 */     this.animStep += $$6;
/*      */     
/* 2517 */     if (isFallFlying()) {
/* 2518 */       this.fallFlyTicks++;
/*      */     } else {
/* 2520 */       this.fallFlyTicks = 0;
/*      */     } 
/*      */     
/* 2523 */     if (isSleeping()) {
/* 2524 */       setXRot(0.0F);
/*      */     }
/*      */     
/* 2527 */     refreshDirtyAttributes();
/*      */   }
/*      */   
/*      */   private void detectEquipmentUpdates() {
/* 2531 */     Map<EquipmentSlot, ItemStack> $$0 = collectEquipmentChanges();
/*      */     
/* 2533 */     if ($$0 != null) {
/* 2534 */       handleHandSwap($$0);
/*      */       
/* 2536 */       if (!$$0.isEmpty()) {
/* 2537 */         handleEquipmentChanges($$0);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   private Map<EquipmentSlot, ItemStack> collectEquipmentChanges() {
/* 2544 */     Map<EquipmentSlot, ItemStack> $$0 = null; EquipmentSlot[] arrayOfEquipmentSlot; int i; byte b;
/* 2545 */     for (arrayOfEquipmentSlot = EquipmentSlot.values(), i = arrayOfEquipmentSlot.length, b = 0; b < i; ) { ItemStack $$2, $$3; EquipmentSlot $$1 = arrayOfEquipmentSlot[b];
/*      */       
/* 2547 */       switch ($$1.getType()) {
/*      */         case MAINHAND:
/* 2549 */           $$2 = getLastHandItem($$1);
/*      */           break;
/*      */         case OFFHAND:
/* 2552 */           $$3 = getLastArmorItem($$1); break;
/*      */         default:
/*      */           b++;
/*      */           continue;
/*      */       } 
/* 2557 */       ItemStack $$5 = getItemBySlot($$1);
/*      */       
/* 2559 */       if (equipmentHasChanged($$3, $$5)) {
/* 2560 */         if ($$0 == null) {
/* 2561 */           $$0 = Maps.newEnumMap(EquipmentSlot.class);
/*      */         }
/* 2563 */         $$0.put($$1, $$5);
/*      */         
/* 2565 */         if (!$$3.isEmpty()) {
/* 2566 */           getAttributes().removeAttributeModifiers($$3.getAttributeModifiers($$1));
/*      */         }
/* 2568 */         if (!$$5.isEmpty()) {
/* 2569 */           getAttributes().addTransientAttributeModifiers($$5.getAttributeModifiers($$1));
/*      */         }
/*      */       }  }
/*      */     
/* 2573 */     return $$0;
/*      */   }
/*      */   
/*      */   public boolean equipmentHasChanged(ItemStack $$0, ItemStack $$1) {
/* 2577 */     return !ItemStack.matches($$1, $$0);
/*      */   }
/*      */ 
/*      */   
/*      */   private void handleHandSwap(Map<EquipmentSlot, ItemStack> $$0) {
/* 2582 */     ItemStack $$1 = $$0.get(EquipmentSlot.MAINHAND);
/* 2583 */     ItemStack $$2 = $$0.get(EquipmentSlot.OFFHAND);
/*      */     
/* 2585 */     if ($$1 != null && $$2 != null && 
/* 2586 */       ItemStack.matches($$1, getLastHandItem(EquipmentSlot.OFFHAND)) && 
/* 2587 */       ItemStack.matches($$2, getLastHandItem(EquipmentSlot.MAINHAND))) {
/*      */       
/* 2589 */       ((ServerLevel)level()).getChunkSource().broadcast(this, (Packet)new ClientboundEntityEventPacket(this, (byte)55));
/* 2590 */       $$0.remove(EquipmentSlot.MAINHAND);
/* 2591 */       $$0.remove(EquipmentSlot.OFFHAND);
/* 2592 */       setLastHandItem(EquipmentSlot.MAINHAND, $$1.copy());
/* 2593 */       setLastHandItem(EquipmentSlot.OFFHAND, $$2.copy());
/*      */     } 
/*      */   }
/*      */   
/*      */   private void handleEquipmentChanges(Map<EquipmentSlot, ItemStack> $$0) {
/* 2598 */     List<Pair<EquipmentSlot, ItemStack>> $$1 = Lists.newArrayListWithCapacity($$0.size());
/* 2599 */     $$0.forEach(($$1, $$2) -> {
/*      */           ItemStack $$3 = $$2.copy();
/*      */           $$0.add(Pair.of($$1, $$3));
/*      */           switch ($$1.getType()) {
/*      */             case MAINHAND:
/*      */               setLastHandItem($$1, $$3);
/*      */               break;
/*      */             
/*      */             case OFFHAND:
/*      */               setLastArmorItem($$1, $$3);
/*      */               break;
/*      */           } 
/*      */         });
/* 2612 */     ((ServerLevel)level()).getChunkSource().broadcast(this, (Packet)new ClientboundSetEquipmentPacket(getId(), $$1));
/*      */   }
/*      */   
/*      */   private ItemStack getLastArmorItem(EquipmentSlot $$0) {
/* 2616 */     return (ItemStack)this.lastArmorItemStacks.get($$0.getIndex());
/*      */   }
/*      */   
/*      */   private void setLastArmorItem(EquipmentSlot $$0, ItemStack $$1) {
/* 2620 */     this.lastArmorItemStacks.set($$0.getIndex(), $$1);
/*      */   }
/*      */   
/*      */   private ItemStack getLastHandItem(EquipmentSlot $$0) {
/* 2624 */     return (ItemStack)this.lastHandItemStacks.get($$0.getIndex());
/*      */   }
/*      */   
/*      */   private void setLastHandItem(EquipmentSlot $$0, ItemStack $$1) {
/* 2628 */     this.lastHandItemStacks.set($$0.getIndex(), $$1);
/*      */   }
/*      */   
/*      */   protected float tickHeadTurn(float $$0, float $$1) {
/* 2632 */     float $$2 = Mth.wrapDegrees($$0 - this.yBodyRot);
/* 2633 */     this.yBodyRot += $$2 * 0.3F;
/*      */     
/* 2635 */     float $$3 = Mth.wrapDegrees(getYRot() - this.yBodyRot);
/*      */     
/* 2637 */     float $$4 = getMaxHeadRotationRelativeToBody();
/* 2638 */     if (Math.abs($$3) > $$4) {
/* 2639 */       this.yBodyRot += $$3 - Mth.sign($$3) * $$4;
/*      */     }
/*      */     
/* 2642 */     boolean $$5 = ($$3 < -90.0F || $$3 >= 90.0F);
/* 2643 */     if ($$5) {
/* 2644 */       $$1 *= -1.0F;
/*      */     }
/*      */     
/* 2647 */     return $$1;
/*      */   }
/*      */   
/*      */   protected float getMaxHeadRotationRelativeToBody() {
/* 2651 */     return 50.0F;
/*      */   }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void aiStep() {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: getfield noJumpDelay : I
/*      */     //   4: ifle -> 17
/*      */     //   7: aload_0
/*      */     //   8: dup
/*      */     //   9: getfield noJumpDelay : I
/*      */     //   12: iconst_1
/*      */     //   13: isub
/*      */     //   14: putfield noJumpDelay : I
/*      */     //   17: aload_0
/*      */     //   18: invokevirtual isControlledByLocalInstance : ()Z
/*      */     //   21: ifeq -> 45
/*      */     //   24: aload_0
/*      */     //   25: iconst_0
/*      */     //   26: putfield lerpSteps : I
/*      */     //   29: aload_0
/*      */     //   30: aload_0
/*      */     //   31: invokevirtual getX : ()D
/*      */     //   34: aload_0
/*      */     //   35: invokevirtual getY : ()D
/*      */     //   38: aload_0
/*      */     //   39: invokevirtual getZ : ()D
/*      */     //   42: invokevirtual syncPacketPositionCodec : (DDD)V
/*      */     //   45: aload_0
/*      */     //   46: getfield lerpSteps : I
/*      */     //   49: ifle -> 93
/*      */     //   52: aload_0
/*      */     //   53: aload_0
/*      */     //   54: getfield lerpSteps : I
/*      */     //   57: aload_0
/*      */     //   58: getfield lerpX : D
/*      */     //   61: aload_0
/*      */     //   62: getfield lerpY : D
/*      */     //   65: aload_0
/*      */     //   66: getfield lerpZ : D
/*      */     //   69: aload_0
/*      */     //   70: getfield lerpYRot : D
/*      */     //   73: aload_0
/*      */     //   74: getfield lerpXRot : D
/*      */     //   77: invokevirtual lerpPositionAndRotationStep : (IDDDDD)V
/*      */     //   80: aload_0
/*      */     //   81: dup
/*      */     //   82: getfield lerpSteps : I
/*      */     //   85: iconst_1
/*      */     //   86: isub
/*      */     //   87: putfield lerpSteps : I
/*      */     //   90: goto -> 114
/*      */     //   93: aload_0
/*      */     //   94: invokevirtual isEffectiveAi : ()Z
/*      */     //   97: ifne -> 114
/*      */     //   100: aload_0
/*      */     //   101: aload_0
/*      */     //   102: invokevirtual getDeltaMovement : ()Lnet/minecraft/world/phys/Vec3;
/*      */     //   105: ldc2_w 0.98
/*      */     //   108: invokevirtual scale : (D)Lnet/minecraft/world/phys/Vec3;
/*      */     //   111: invokevirtual setDeltaMovement : (Lnet/minecraft/world/phys/Vec3;)V
/*      */     //   114: aload_0
/*      */     //   115: getfield lerpHeadSteps : I
/*      */     //   118: ifle -> 143
/*      */     //   121: aload_0
/*      */     //   122: aload_0
/*      */     //   123: getfield lerpHeadSteps : I
/*      */     //   126: aload_0
/*      */     //   127: getfield lerpYHeadRot : D
/*      */     //   130: invokevirtual lerpHeadRotationStep : (ID)V
/*      */     //   133: aload_0
/*      */     //   134: dup
/*      */     //   135: getfield lerpHeadSteps : I
/*      */     //   138: iconst_1
/*      */     //   139: isub
/*      */     //   140: putfield lerpHeadSteps : I
/*      */     //   143: aload_0
/*      */     //   144: invokevirtual getDeltaMovement : ()Lnet/minecraft/world/phys/Vec3;
/*      */     //   147: astore_1
/*      */     //   148: aload_1
/*      */     //   149: getfield x : D
/*      */     //   152: dstore_2
/*      */     //   153: aload_1
/*      */     //   154: getfield y : D
/*      */     //   157: dstore #4
/*      */     //   159: aload_1
/*      */     //   160: getfield z : D
/*      */     //   163: dstore #6
/*      */     //   165: aload_1
/*      */     //   166: getfield x : D
/*      */     //   169: invokestatic abs : (D)D
/*      */     //   172: ldc2_w 0.003
/*      */     //   175: dcmpg
/*      */     //   176: ifge -> 181
/*      */     //   179: dconst_0
/*      */     //   180: dstore_2
/*      */     //   181: aload_1
/*      */     //   182: getfield y : D
/*      */     //   185: invokestatic abs : (D)D
/*      */     //   188: ldc2_w 0.003
/*      */     //   191: dcmpg
/*      */     //   192: ifge -> 198
/*      */     //   195: dconst_0
/*      */     //   196: dstore #4
/*      */     //   198: aload_1
/*      */     //   199: getfield z : D
/*      */     //   202: invokestatic abs : (D)D
/*      */     //   205: ldc2_w 0.003
/*      */     //   208: dcmpg
/*      */     //   209: ifge -> 215
/*      */     //   212: dconst_0
/*      */     //   213: dstore #6
/*      */     //   215: aload_0
/*      */     //   216: dload_2
/*      */     //   217: dload #4
/*      */     //   219: dload #6
/*      */     //   221: invokevirtual setDeltaMovement : (DDD)V
/*      */     //   224: aload_0
/*      */     //   225: invokevirtual level : ()Lnet/minecraft/world/level/Level;
/*      */     //   228: invokevirtual getProfiler : ()Lnet/minecraft/util/profiling/ProfilerFiller;
/*      */     //   231: ldc_w 'ai'
/*      */     //   234: invokeinterface push : (Ljava/lang/String;)V
/*      */     //   239: aload_0
/*      */     //   240: invokevirtual isImmobile : ()Z
/*      */     //   243: ifeq -> 264
/*      */     //   246: aload_0
/*      */     //   247: iconst_0
/*      */     //   248: putfield jumping : Z
/*      */     //   251: aload_0
/*      */     //   252: fconst_0
/*      */     //   253: putfield xxa : F
/*      */     //   256: aload_0
/*      */     //   257: fconst_0
/*      */     //   258: putfield zza : F
/*      */     //   261: goto -> 302
/*      */     //   264: aload_0
/*      */     //   265: invokevirtual isEffectiveAi : ()Z
/*      */     //   268: ifeq -> 302
/*      */     //   271: aload_0
/*      */     //   272: invokevirtual level : ()Lnet/minecraft/world/level/Level;
/*      */     //   275: invokevirtual getProfiler : ()Lnet/minecraft/util/profiling/ProfilerFiller;
/*      */     //   278: ldc_w 'newAi'
/*      */     //   281: invokeinterface push : (Ljava/lang/String;)V
/*      */     //   286: aload_0
/*      */     //   287: invokevirtual serverAiStep : ()V
/*      */     //   290: aload_0
/*      */     //   291: invokevirtual level : ()Lnet/minecraft/world/level/Level;
/*      */     //   294: invokevirtual getProfiler : ()Lnet/minecraft/util/profiling/ProfilerFiller;
/*      */     //   297: invokeinterface pop : ()V
/*      */     //   302: aload_0
/*      */     //   303: invokevirtual level : ()Lnet/minecraft/world/level/Level;
/*      */     //   306: invokevirtual getProfiler : ()Lnet/minecraft/util/profiling/ProfilerFiller;
/*      */     //   309: invokeinterface pop : ()V
/*      */     //   314: aload_0
/*      */     //   315: invokevirtual level : ()Lnet/minecraft/world/level/Level;
/*      */     //   318: invokevirtual getProfiler : ()Lnet/minecraft/util/profiling/ProfilerFiller;
/*      */     //   321: ldc_w 'jump'
/*      */     //   324: invokeinterface push : (Ljava/lang/String;)V
/*      */     //   329: aload_0
/*      */     //   330: getfield jumping : Z
/*      */     //   333: ifeq -> 500
/*      */     //   336: aload_0
/*      */     //   337: invokevirtual isAffectedByFluids : ()Z
/*      */     //   340: ifeq -> 500
/*      */     //   343: aload_0
/*      */     //   344: invokevirtual isInLava : ()Z
/*      */     //   347: ifeq -> 362
/*      */     //   350: aload_0
/*      */     //   351: getstatic net/minecraft/tags/FluidTags.LAVA : Lnet/minecraft/tags/TagKey;
/*      */     //   354: invokevirtual getFluidHeight : (Lnet/minecraft/tags/TagKey;)D
/*      */     //   357: dstore #8
/*      */     //   359: goto -> 371
/*      */     //   362: aload_0
/*      */     //   363: getstatic net/minecraft/tags/FluidTags.WATER : Lnet/minecraft/tags/TagKey;
/*      */     //   366: invokevirtual getFluidHeight : (Lnet/minecraft/tags/TagKey;)D
/*      */     //   369: dstore #8
/*      */     //   371: aload_0
/*      */     //   372: invokevirtual isInWater : ()Z
/*      */     //   375: ifeq -> 389
/*      */     //   378: dload #8
/*      */     //   380: dconst_0
/*      */     //   381: dcmpl
/*      */     //   382: ifle -> 389
/*      */     //   385: iconst_1
/*      */     //   386: goto -> 390
/*      */     //   389: iconst_0
/*      */     //   390: istore #10
/*      */     //   392: aload_0
/*      */     //   393: invokevirtual getFluidJumpThreshold : ()D
/*      */     //   396: dstore #11
/*      */     //   398: iload #10
/*      */     //   400: ifeq -> 428
/*      */     //   403: aload_0
/*      */     //   404: invokevirtual onGround : ()Z
/*      */     //   407: ifeq -> 418
/*      */     //   410: dload #8
/*      */     //   412: dload #11
/*      */     //   414: dcmpl
/*      */     //   415: ifle -> 428
/*      */     //   418: aload_0
/*      */     //   419: getstatic net/minecraft/tags/FluidTags.WATER : Lnet/minecraft/tags/TagKey;
/*      */     //   422: invokevirtual jumpInLiquid : (Lnet/minecraft/tags/TagKey;)V
/*      */     //   425: goto -> 497
/*      */     //   428: aload_0
/*      */     //   429: invokevirtual isInLava : ()Z
/*      */     //   432: ifeq -> 460
/*      */     //   435: aload_0
/*      */     //   436: invokevirtual onGround : ()Z
/*      */     //   439: ifeq -> 450
/*      */     //   442: dload #8
/*      */     //   444: dload #11
/*      */     //   446: dcmpl
/*      */     //   447: ifle -> 460
/*      */     //   450: aload_0
/*      */     //   451: getstatic net/minecraft/tags/FluidTags.LAVA : Lnet/minecraft/tags/TagKey;
/*      */     //   454: invokevirtual jumpInLiquid : (Lnet/minecraft/tags/TagKey;)V
/*      */     //   457: goto -> 497
/*      */     //   460: aload_0
/*      */     //   461: invokevirtual onGround : ()Z
/*      */     //   464: ifne -> 480
/*      */     //   467: iload #10
/*      */     //   469: ifeq -> 497
/*      */     //   472: dload #8
/*      */     //   474: dload #11
/*      */     //   476: dcmpg
/*      */     //   477: ifgt -> 497
/*      */     //   480: aload_0
/*      */     //   481: getfield noJumpDelay : I
/*      */     //   484: ifne -> 497
/*      */     //   487: aload_0
/*      */     //   488: invokevirtual jumpFromGround : ()V
/*      */     //   491: aload_0
/*      */     //   492: bipush #10
/*      */     //   494: putfield noJumpDelay : I
/*      */     //   497: goto -> 505
/*      */     //   500: aload_0
/*      */     //   501: iconst_0
/*      */     //   502: putfield noJumpDelay : I
/*      */     //   505: aload_0
/*      */     //   506: invokevirtual level : ()Lnet/minecraft/world/level/Level;
/*      */     //   509: invokevirtual getProfiler : ()Lnet/minecraft/util/profiling/ProfilerFiller;
/*      */     //   512: invokeinterface pop : ()V
/*      */     //   517: aload_0
/*      */     //   518: invokevirtual level : ()Lnet/minecraft/world/level/Level;
/*      */     //   521: invokevirtual getProfiler : ()Lnet/minecraft/util/profiling/ProfilerFiller;
/*      */     //   524: ldc_w 'travel'
/*      */     //   527: invokeinterface push : (Ljava/lang/String;)V
/*      */     //   532: aload_0
/*      */     //   533: dup
/*      */     //   534: getfield xxa : F
/*      */     //   537: ldc_w 0.98
/*      */     //   540: fmul
/*      */     //   541: putfield xxa : F
/*      */     //   544: aload_0
/*      */     //   545: dup
/*      */     //   546: getfield zza : F
/*      */     //   549: ldc_w 0.98
/*      */     //   552: fmul
/*      */     //   553: putfield zza : F
/*      */     //   556: aload_0
/*      */     //   557: invokevirtual updateFallFlying : ()V
/*      */     //   560: aload_0
/*      */     //   561: invokevirtual getBoundingBox : ()Lnet/minecraft/world/phys/AABB;
/*      */     //   564: astore #8
/*      */     //   566: new net/minecraft/world/phys/Vec3
/*      */     //   569: dup
/*      */     //   570: aload_0
/*      */     //   571: getfield xxa : F
/*      */     //   574: f2d
/*      */     //   575: aload_0
/*      */     //   576: getfield yya : F
/*      */     //   579: f2d
/*      */     //   580: aload_0
/*      */     //   581: getfield zza : F
/*      */     //   584: f2d
/*      */     //   585: invokespecial <init> : (DDD)V
/*      */     //   588: astore #9
/*      */     //   590: aload_0
/*      */     //   591: getstatic net/minecraft/world/effect/MobEffects.SLOW_FALLING : Lnet/minecraft/world/effect/MobEffect;
/*      */     //   594: invokevirtual hasEffect : (Lnet/minecraft/world/effect/MobEffect;)Z
/*      */     //   597: ifne -> 610
/*      */     //   600: aload_0
/*      */     //   601: getstatic net/minecraft/world/effect/MobEffects.LEVITATION : Lnet/minecraft/world/effect/MobEffect;
/*      */     //   604: invokevirtual hasEffect : (Lnet/minecraft/world/effect/MobEffect;)Z
/*      */     //   607: ifeq -> 614
/*      */     //   610: aload_0
/*      */     //   611: invokevirtual resetFallDistance : ()V
/*      */     //   614: aload_0
/*      */     //   615: invokevirtual getControllingPassenger : ()Lnet/minecraft/world/entity/LivingEntity;
/*      */     //   618: astore #11
/*      */     //   620: aload #11
/*      */     //   622: instanceof net/minecraft/world/entity/player/Player
/*      */     //   625: ifeq -> 653
/*      */     //   628: aload #11
/*      */     //   630: checkcast net/minecraft/world/entity/player/Player
/*      */     //   633: astore #10
/*      */     //   635: aload_0
/*      */     //   636: invokevirtual isAlive : ()Z
/*      */     //   639: ifeq -> 653
/*      */     //   642: aload_0
/*      */     //   643: aload #10
/*      */     //   645: aload #9
/*      */     //   647: invokevirtual travelRidden : (Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/phys/Vec3;)V
/*      */     //   650: goto -> 659
/*      */     //   653: aload_0
/*      */     //   654: aload #9
/*      */     //   656: invokevirtual travel : (Lnet/minecraft/world/phys/Vec3;)V
/*      */     //   659: aload_0
/*      */     //   660: invokevirtual level : ()Lnet/minecraft/world/level/Level;
/*      */     //   663: invokevirtual getProfiler : ()Lnet/minecraft/util/profiling/ProfilerFiller;
/*      */     //   666: invokeinterface pop : ()V
/*      */     //   671: aload_0
/*      */     //   672: invokevirtual level : ()Lnet/minecraft/world/level/Level;
/*      */     //   675: invokevirtual getProfiler : ()Lnet/minecraft/util/profiling/ProfilerFiller;
/*      */     //   678: ldc_w 'freezing'
/*      */     //   681: invokeinterface push : (Ljava/lang/String;)V
/*      */     //   686: aload_0
/*      */     //   687: invokevirtual level : ()Lnet/minecraft/world/level/Level;
/*      */     //   690: getfield isClientSide : Z
/*      */     //   693: ifne -> 753
/*      */     //   696: aload_0
/*      */     //   697: invokevirtual isDeadOrDying : ()Z
/*      */     //   700: ifne -> 753
/*      */     //   703: aload_0
/*      */     //   704: invokevirtual getTicksFrozen : ()I
/*      */     //   707: istore #10
/*      */     //   709: aload_0
/*      */     //   710: getfield isInPowderSnow : Z
/*      */     //   713: ifeq -> 741
/*      */     //   716: aload_0
/*      */     //   717: invokevirtual canFreeze : ()Z
/*      */     //   720: ifeq -> 741
/*      */     //   723: aload_0
/*      */     //   724: aload_0
/*      */     //   725: invokevirtual getTicksRequiredToFreeze : ()I
/*      */     //   728: iload #10
/*      */     //   730: iconst_1
/*      */     //   731: iadd
/*      */     //   732: invokestatic min : (II)I
/*      */     //   735: invokevirtual setTicksFrozen : (I)V
/*      */     //   738: goto -> 753
/*      */     //   741: aload_0
/*      */     //   742: iconst_0
/*      */     //   743: iload #10
/*      */     //   745: iconst_2
/*      */     //   746: isub
/*      */     //   747: invokestatic max : (II)I
/*      */     //   750: invokevirtual setTicksFrozen : (I)V
/*      */     //   753: aload_0
/*      */     //   754: invokevirtual removeFrost : ()V
/*      */     //   757: aload_0
/*      */     //   758: invokevirtual tryAddFrost : ()V
/*      */     //   761: aload_0
/*      */     //   762: invokevirtual level : ()Lnet/minecraft/world/level/Level;
/*      */     //   765: getfield isClientSide : Z
/*      */     //   768: ifne -> 808
/*      */     //   771: aload_0
/*      */     //   772: getfield tickCount : I
/*      */     //   775: bipush #40
/*      */     //   777: irem
/*      */     //   778: ifne -> 808
/*      */     //   781: aload_0
/*      */     //   782: invokevirtual isFullyFrozen : ()Z
/*      */     //   785: ifeq -> 808
/*      */     //   788: aload_0
/*      */     //   789: invokevirtual canFreeze : ()Z
/*      */     //   792: ifeq -> 808
/*      */     //   795: aload_0
/*      */     //   796: aload_0
/*      */     //   797: invokevirtual damageSources : ()Lnet/minecraft/world/damagesource/DamageSources;
/*      */     //   800: invokevirtual freeze : ()Lnet/minecraft/world/damagesource/DamageSource;
/*      */     //   803: fconst_1
/*      */     //   804: invokevirtual hurt : (Lnet/minecraft/world/damagesource/DamageSource;F)Z
/*      */     //   807: pop
/*      */     //   808: aload_0
/*      */     //   809: invokevirtual level : ()Lnet/minecraft/world/level/Level;
/*      */     //   812: invokevirtual getProfiler : ()Lnet/minecraft/util/profiling/ProfilerFiller;
/*      */     //   815: invokeinterface pop : ()V
/*      */     //   820: aload_0
/*      */     //   821: invokevirtual level : ()Lnet/minecraft/world/level/Level;
/*      */     //   824: invokevirtual getProfiler : ()Lnet/minecraft/util/profiling/ProfilerFiller;
/*      */     //   827: ldc_w 'push'
/*      */     //   830: invokeinterface push : (Ljava/lang/String;)V
/*      */     //   835: aload_0
/*      */     //   836: getfield autoSpinAttackTicks : I
/*      */     //   839: ifle -> 862
/*      */     //   842: aload_0
/*      */     //   843: dup
/*      */     //   844: getfield autoSpinAttackTicks : I
/*      */     //   847: iconst_1
/*      */     //   848: isub
/*      */     //   849: putfield autoSpinAttackTicks : I
/*      */     //   852: aload_0
/*      */     //   853: aload #8
/*      */     //   855: aload_0
/*      */     //   856: invokevirtual getBoundingBox : ()Lnet/minecraft/world/phys/AABB;
/*      */     //   859: invokevirtual checkAutoSpinAttack : (Lnet/minecraft/world/phys/AABB;Lnet/minecraft/world/phys/AABB;)V
/*      */     //   862: aload_0
/*      */     //   863: invokevirtual pushEntities : ()V
/*      */     //   866: aload_0
/*      */     //   867: invokevirtual level : ()Lnet/minecraft/world/level/Level;
/*      */     //   870: invokevirtual getProfiler : ()Lnet/minecraft/util/profiling/ProfilerFiller;
/*      */     //   873: invokeinterface pop : ()V
/*      */     //   878: aload_0
/*      */     //   879: invokevirtual level : ()Lnet/minecraft/world/level/Level;
/*      */     //   882: getfield isClientSide : Z
/*      */     //   885: ifne -> 915
/*      */     //   888: aload_0
/*      */     //   889: invokevirtual isSensitiveToWater : ()Z
/*      */     //   892: ifeq -> 915
/*      */     //   895: aload_0
/*      */     //   896: invokevirtual isInWaterRainOrBubble : ()Z
/*      */     //   899: ifeq -> 915
/*      */     //   902: aload_0
/*      */     //   903: aload_0
/*      */     //   904: invokevirtual damageSources : ()Lnet/minecraft/world/damagesource/DamageSources;
/*      */     //   907: invokevirtual drown : ()Lnet/minecraft/world/damagesource/DamageSource;
/*      */     //   910: fconst_1
/*      */     //   911: invokevirtual hurt : (Lnet/minecraft/world/damagesource/DamageSource;F)Z
/*      */     //   914: pop
/*      */     //   915: return
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #2655	-> 0
/*      */     //   #2656	-> 7
/*      */     //   #2658	-> 17
/*      */     //   #2659	-> 24
/*      */     //   #2660	-> 29
/*      */     //   #2662	-> 45
/*      */     //   #2663	-> 52
/*      */     //   #2664	-> 80
/*      */     //   #2665	-> 93
/*      */     //   #2667	-> 100
/*      */     //   #2669	-> 114
/*      */     //   #2670	-> 121
/*      */     //   #2671	-> 133
/*      */     //   #2674	-> 143
/*      */     //   #2675	-> 148
/*      */     //   #2676	-> 153
/*      */     //   #2677	-> 159
/*      */     //   #2678	-> 165
/*      */     //   #2679	-> 179
/*      */     //   #2681	-> 181
/*      */     //   #2682	-> 195
/*      */     //   #2684	-> 198
/*      */     //   #2685	-> 212
/*      */     //   #2687	-> 215
/*      */     //   #2689	-> 224
/*      */     //   #2690	-> 239
/*      */     //   #2691	-> 246
/*      */     //   #2692	-> 251
/*      */     //   #2693	-> 256
/*      */     //   #2695	-> 264
/*      */     //   #2696	-> 271
/*      */     //   #2697	-> 286
/*      */     //   #2698	-> 290
/*      */     //   #2701	-> 302
/*      */     //   #2703	-> 314
/*      */     //   #2704	-> 329
/*      */     //   #2708	-> 343
/*      */     //   #2709	-> 350
/*      */     //   #2711	-> 362
/*      */     //   #2713	-> 371
/*      */     //   #2714	-> 392
/*      */     //   #2715	-> 398
/*      */     //   #2716	-> 418
/*      */     //   #2717	-> 428
/*      */     //   #2718	-> 450
/*      */     //   #2719	-> 460
/*      */     //   #2720	-> 480
/*      */     //   #2721	-> 487
/*      */     //   #2722	-> 491
/*      */     //   #2725	-> 497
/*      */     //   #2726	-> 500
/*      */     //   #2728	-> 505
/*      */     //   #2730	-> 517
/*      */     //   #2731	-> 532
/*      */     //   #2732	-> 544
/*      */     //   #2734	-> 556
/*      */     //   #2735	-> 560
/*      */     //   #2736	-> 566
/*      */     //   #2737	-> 590
/*      */     //   #2738	-> 610
/*      */     //   #2740	-> 614
/*      */     //   #2741	-> 642
/*      */     //   #2743	-> 653
/*      */     //   #2745	-> 659
/*      */     //   #2747	-> 671
/*      */     //   #2749	-> 686
/*      */     //   #2750	-> 703
/*      */     //   #2751	-> 709
/*      */     //   #2752	-> 723
/*      */     //   #2754	-> 741
/*      */     //   #2758	-> 753
/*      */     //   #2759	-> 757
/*      */     //   #2761	-> 761
/*      */     //   #2762	-> 795
/*      */     //   #2764	-> 808
/*      */     //   #2766	-> 820
/*      */     //   #2767	-> 835
/*      */     //   #2768	-> 842
/*      */     //   #2769	-> 852
/*      */     //   #2771	-> 862
/*      */     //   #2772	-> 866
/*      */     //   #2775	-> 878
/*      */     //   #2776	-> 902
/*      */     //   #2778	-> 915
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   0	916	0	this	Lnet/minecraft/world/entity/LivingEntity;
/*      */     //   148	768	1	$$0	Lnet/minecraft/world/phys/Vec3;
/*      */     //   153	763	2	$$1	D
/*      */     //   159	757	4	$$2	D
/*      */     //   165	751	6	$$3	D
/*      */     //   359	3	8	$$4	D
/*      */     //   371	126	8	$$5	D
/*      */     //   392	105	10	$$6	Z
/*      */     //   398	99	11	$$7	D
/*      */     //   566	350	8	$$8	Lnet/minecraft/world/phys/AABB;
/*      */     //   590	326	9	$$9	Lnet/minecraft/world/phys/Vec3;
/*      */     //   635	18	10	$$10	Lnet/minecraft/world/entity/player/Player;
/*      */     //   709	44	10	$$11	I
/*      */   }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSensitiveToWater() {
/* 2784 */     return false;
/*      */   }
/*      */   
/*      */   private void updateFallFlying() {
/* 2788 */     boolean $$0 = getSharedFlag(7);
/* 2789 */     if ($$0 && !onGround() && !isPassenger() && !hasEffect(MobEffects.LEVITATION)) {
/* 2790 */       ItemStack $$1 = getItemBySlot(EquipmentSlot.CHEST);
/* 2791 */       if ($$1.is(Items.ELYTRA) && ElytraItem.isFlyEnabled($$1)) {
/* 2792 */         $$0 = true;
/* 2793 */         int $$2 = this.fallFlyTicks + 1;
/* 2794 */         if (!(level()).isClientSide && $$2 % 10 == 0) {
/* 2795 */           int $$3 = $$2 / 10;
/*      */           
/* 2797 */           if ($$3 % 2 == 0)
/*      */           {
/* 2799 */             $$1.hurtAndBreak(1, this, $$0 -> $$0.broadcastBreakEvent(EquipmentSlot.CHEST));
/*      */           }
/*      */           
/* 2802 */           gameEvent(GameEvent.ELYTRA_GLIDE);
/*      */         } 
/*      */       } else {
/* 2805 */         $$0 = false;
/*      */       } 
/*      */     } else {
/* 2808 */       $$0 = false;
/*      */     } 
/* 2810 */     if (!(level()).isClientSide) {
/* 2811 */       setSharedFlag(7, $$0);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected void serverAiStep() {}
/*      */   
/*      */   protected void pushEntities() {
/* 2819 */     if (level().isClientSide()) {
/*      */       
/* 2821 */       level().getEntities(EntityTypeTest.forClass(Player.class), getBoundingBox(), EntitySelector.pushableBy(this)).forEach(this::doPush);
/*      */       
/*      */       return;
/*      */     } 
/* 2825 */     List<Entity> $$0 = level().getEntities(this, getBoundingBox(), EntitySelector.pushableBy(this));
/*      */     
/* 2827 */     if (!$$0.isEmpty()) {
/* 2828 */       int $$1 = level().getGameRules().getInt(GameRules.RULE_MAX_ENTITY_CRAMMING);
/* 2829 */       if ($$1 > 0 && $$0.size() > $$1 - 1 && this.random.nextInt(4) == 0) {
/* 2830 */         int $$2 = 0;
/* 2831 */         for (Entity $$3 : $$0) {
/* 2832 */           if (!$$3.isPassenger()) {
/* 2833 */             $$2++;
/*      */           }
/*      */         } 
/* 2836 */         if ($$2 > $$1 - 1) {
/* 2837 */           hurt(damageSources().cramming(), 6.0F);
/*      */         }
/*      */       } 
/* 2840 */       for (Entity $$4 : $$0) {
/* 2841 */         doPush($$4);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void checkAutoSpinAttack(AABB $$0, AABB $$1) {
/* 2847 */     AABB $$2 = $$0.minmax($$1);
/* 2848 */     List<Entity> $$3 = level().getEntities(this, $$2);
/* 2849 */     if (!$$3.isEmpty()) {
/* 2850 */       for (Entity $$4 : $$3) {
/* 2851 */         if ($$4 instanceof LivingEntity) {
/* 2852 */           doAutoAttackOnTouch((LivingEntity)$$4);
/* 2853 */           this.autoSpinAttackTicks = 0;
/* 2854 */           setDeltaMovement(getDeltaMovement().scale(-0.2D));
/*      */           break;
/*      */         } 
/*      */       } 
/* 2858 */     } else if (this.horizontalCollision) {
/* 2859 */       this.autoSpinAttackTicks = 0;
/*      */     } 
/* 2861 */     if (!(level()).isClientSide && this.autoSpinAttackTicks <= 0) {
/* 2862 */       setLivingEntityFlag(4, false);
/*      */     }
/*      */   }
/*      */   
/*      */   protected void doPush(Entity $$0) {
/* 2867 */     $$0.push(this);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void doAutoAttackOnTouch(LivingEntity $$0) {}
/*      */   
/*      */   public boolean isAutoSpinAttack() {
/* 2874 */     return ((((Byte)this.entityData.get(DATA_LIVING_ENTITY_FLAGS)).byteValue() & 0x4) != 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public void stopRiding() {
/* 2879 */     Entity $$0 = getVehicle();
/* 2880 */     super.stopRiding();
/* 2881 */     if ($$0 != null && $$0 != getVehicle() && !(level()).isClientSide) {
/* 2882 */       dismountVehicle($$0);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void rideTick() {
/* 2888 */     super.rideTick();
/* 2889 */     this.oRun = this.run;
/* 2890 */     this.run = 0.0F;
/* 2891 */     resetFallDistance();
/*      */   }
/*      */ 
/*      */   
/*      */   public void lerpTo(double $$0, double $$1, double $$2, float $$3, float $$4, int $$5) {
/* 2896 */     this.lerpX = $$0;
/* 2897 */     this.lerpY = $$1;
/* 2898 */     this.lerpZ = $$2;
/* 2899 */     this.lerpYRot = $$3;
/* 2900 */     this.lerpXRot = $$4;
/*      */     
/* 2902 */     this.lerpSteps = $$5;
/*      */   }
/*      */ 
/*      */   
/*      */   public double lerpTargetX() {
/* 2907 */     return (this.lerpSteps > 0) ? this.lerpX : getX();
/*      */   }
/*      */ 
/*      */   
/*      */   public double lerpTargetY() {
/* 2912 */     return (this.lerpSteps > 0) ? this.lerpY : getY();
/*      */   }
/*      */ 
/*      */   
/*      */   public double lerpTargetZ() {
/* 2917 */     return (this.lerpSteps > 0) ? this.lerpZ : getZ();
/*      */   }
/*      */ 
/*      */   
/*      */   public float lerpTargetXRot() {
/* 2922 */     return (this.lerpSteps > 0) ? (float)this.lerpXRot : getXRot();
/*      */   }
/*      */ 
/*      */   
/*      */   public float lerpTargetYRot() {
/* 2927 */     return (this.lerpSteps > 0) ? (float)this.lerpYRot : getYRot();
/*      */   }
/*      */ 
/*      */   
/*      */   public void lerpHeadTo(float $$0, int $$1) {
/* 2932 */     this.lerpYHeadRot = $$0;
/*      */     
/* 2934 */     this.lerpHeadSteps = $$1;
/*      */   }
/*      */   
/*      */   public void setJumping(boolean $$0) {
/* 2938 */     this.jumping = $$0;
/*      */   }
/*      */   
/*      */   public void onItemPickup(ItemEntity $$0) {
/* 2942 */     Entity $$1 = $$0.getOwner();
/* 2943 */     if ($$1 instanceof ServerPlayer) {
/* 2944 */       CriteriaTriggers.THROWN_ITEM_PICKED_UP_BY_ENTITY.trigger((ServerPlayer)$$1, $$0.getItem(), this);
/*      */     }
/*      */   }
/*      */   
/*      */   public void take(Entity $$0, int $$1) {
/* 2949 */     if (!$$0.isRemoved() && !(level()).isClientSide && (
/* 2950 */       $$0 instanceof ItemEntity || $$0 instanceof AbstractArrow || $$0 instanceof ExperienceOrb)) {
/* 2951 */       ((ServerLevel)level()).getChunkSource().broadcast($$0, (Packet)new ClientboundTakeItemEntityPacket($$0.getId(), getId(), $$1));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasLineOfSight(Entity $$0) {
/* 2958 */     if ($$0.level() != level()) {
/* 2959 */       return false;
/*      */     }
/* 2961 */     Vec3 $$1 = new Vec3(getX(), getEyeY(), getZ());
/* 2962 */     Vec3 $$2 = new Vec3($$0.getX(), $$0.getEyeY(), $$0.getZ());
/*      */     
/* 2964 */     if ($$2.distanceTo($$1) > 128.0D) {
/* 2965 */       return false;
/*      */     }
/* 2967 */     return (level().clip(new ClipContext($$1, $$2, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this)).getType() == HitResult.Type.MISS);
/*      */   }
/*      */ 
/*      */   
/*      */   public float getViewYRot(float $$0) {
/* 2972 */     if ($$0 == 1.0F) {
/* 2973 */       return this.yHeadRot;
/*      */     }
/* 2975 */     return Mth.lerp($$0, this.yHeadRotO, this.yHeadRot);
/*      */   }
/*      */   
/*      */   public float getAttackAnim(float $$0) {
/* 2979 */     float $$1 = this.attackAnim - this.oAttackAnim;
/* 2980 */     if ($$1 < 0.0F) {
/* 2981 */       $$1++;
/*      */     }
/* 2983 */     return this.oAttackAnim + $$1 * $$0;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isPickable() {
/* 2988 */     return !isRemoved();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isPushable() {
/* 2993 */     return (isAlive() && !isSpectator() && !onClimbable());
/*      */   }
/*      */ 
/*      */   
/*      */   public float getYHeadRot() {
/* 2998 */     return this.yHeadRot;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setYHeadRot(float $$0) {
/* 3003 */     this.yHeadRot = $$0;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setYBodyRot(float $$0) {
/* 3008 */     this.yBodyRot = $$0;
/*      */   }
/*      */ 
/*      */   
/*      */   protected Vec3 getRelativePortalPosition(Direction.Axis $$0, BlockUtil.FoundRectangle $$1) {
/* 3013 */     return resetForwardDirectionOfRelativePortalPosition(super.getRelativePortalPosition($$0, $$1));
/*      */   }
/*      */ 
/*      */   
/*      */   public static Vec3 resetForwardDirectionOfRelativePortalPosition(Vec3 $$0) {
/* 3018 */     return new Vec3($$0.x, $$0.y, 0.0D);
/*      */   }
/*      */   
/*      */   public float getAbsorptionAmount() {
/* 3022 */     return this.absorptionAmount;
/*      */   }
/*      */   
/*      */   public final void setAbsorptionAmount(float $$0) {
/* 3026 */     internalSetAbsorptionAmount(Mth.clamp($$0, 0.0F, getMaxAbsorption()));
/*      */   }
/*      */   
/*      */   protected void internalSetAbsorptionAmount(float $$0) {
/* 3030 */     this.absorptionAmount = $$0;
/*      */   }
/*      */ 
/*      */   
/*      */   public void onEnterCombat() {}
/*      */ 
/*      */   
/*      */   public void onLeaveCombat() {}
/*      */   
/*      */   protected void updateEffectVisibility() {
/* 3040 */     this.effectsDirty = true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isUsingItem() {
/* 3046 */     return ((((Byte)this.entityData.get(DATA_LIVING_ENTITY_FLAGS)).byteValue() & 0x1) > 0);
/*      */   }
/*      */   
/*      */   public InteractionHand getUsedItemHand() {
/* 3050 */     return ((((Byte)this.entityData.get(DATA_LIVING_ENTITY_FLAGS)).byteValue() & 0x2) > 0) ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
/*      */   }
/*      */   
/*      */   private void updatingUsingItem() {
/* 3054 */     if (isUsingItem())
/*      */     {
/* 3056 */       if (ItemStack.isSameItem(getItemInHand(getUsedItemHand()), this.useItem)) {
/* 3057 */         this.useItem = getItemInHand(getUsedItemHand());
/* 3058 */         updateUsingItem(this.useItem);
/*      */       } else {
/* 3060 */         stopUsingItem();
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   protected void updateUsingItem(ItemStack $$0) {
/* 3066 */     $$0.onUseTick(level(), this, getUseItemRemainingTicks());
/* 3067 */     if (shouldTriggerItemUseEffects()) {
/* 3068 */       triggerItemUseEffects($$0, 5);
/*      */     }
/* 3070 */     if (--this.useItemRemaining == 0 && !(level()).isClientSide && !$$0.useOnRelease()) {
/* 3071 */       completeUsingItem();
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean shouldTriggerItemUseEffects() {
/* 3076 */     int $$0 = getUseItemRemainingTicks();
/* 3077 */     FoodProperties $$1 = this.useItem.getItem().getFoodProperties();
/* 3078 */     boolean $$2 = ($$1 != null && $$1.isFastFood());
/* 3079 */     int i = $$2 | (($$0 <= this.useItem.getUseDuration() - 7) ? 1 : 0);
/*      */     
/* 3081 */     return (i != 0 && $$0 % 4 == 0);
/*      */   }
/*      */   
/*      */   private void updateSwimAmount() {
/* 3085 */     this.swimAmountO = this.swimAmount;
/* 3086 */     if (isVisuallySwimming()) {
/* 3087 */       this.swimAmount = Math.min(1.0F, this.swimAmount + 0.09F);
/*      */     } else {
/* 3089 */       this.swimAmount = Math.max(0.0F, this.swimAmount - 0.09F);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void setLivingEntityFlag(int $$0, boolean $$1) {
/* 3094 */     int $$2 = ((Byte)this.entityData.get(DATA_LIVING_ENTITY_FLAGS)).byteValue();
/* 3095 */     if ($$1) {
/* 3096 */       $$2 |= $$0;
/*      */     } else {
/* 3098 */       $$2 &= $$0 ^ 0xFFFFFFFF;
/*      */     } 
/* 3100 */     this.entityData.set(DATA_LIVING_ENTITY_FLAGS, Byte.valueOf((byte)$$2));
/*      */   }
/*      */   
/*      */   public void startUsingItem(InteractionHand $$0) {
/* 3104 */     ItemStack $$1 = getItemInHand($$0);
/* 3105 */     if ($$1.isEmpty() || isUsingItem()) {
/*      */       return;
/*      */     }
/*      */     
/* 3109 */     this.useItem = $$1;
/* 3110 */     this.useItemRemaining = $$1.getUseDuration();
/*      */     
/* 3112 */     if (!(level()).isClientSide) {
/* 3113 */       setLivingEntityFlag(1, true);
/* 3114 */       setLivingEntityFlag(2, ($$0 == InteractionHand.OFF_HAND));
/* 3115 */       gameEvent(GameEvent.ITEM_INTERACT_START);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void onSyncedDataUpdated(EntityDataAccessor<?> $$0) {
/* 3121 */     super.onSyncedDataUpdated($$0);
/*      */     
/* 3123 */     if (SLEEPING_POS_ID.equals($$0)) {
/* 3124 */       if ((level()).isClientSide)
/*      */       {
/* 3126 */         getSleepingPos().ifPresent(this::setPosToBed);
/*      */       }
/* 3128 */     } else if (DATA_LIVING_ENTITY_FLAGS.equals($$0) && (level()).isClientSide) {
/* 3129 */       if (isUsingItem() && this.useItem.isEmpty()) {
/* 3130 */         this.useItem = getItemInHand(getUsedItemHand());
/* 3131 */         if (!this.useItem.isEmpty()) {
/* 3132 */           this.useItemRemaining = this.useItem.getUseDuration();
/*      */         }
/* 3134 */       } else if (!isUsingItem() && !this.useItem.isEmpty()) {
/* 3135 */         this.useItem = ItemStack.EMPTY;
/* 3136 */         this.useItemRemaining = 0;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void lookAt(EntityAnchorArgument.Anchor $$0, Vec3 $$1) {
/* 3143 */     super.lookAt($$0, $$1);
/* 3144 */     this.yHeadRotO = this.yHeadRot;
/* 3145 */     this.yBodyRot = this.yHeadRot;
/* 3146 */     this.yBodyRotO = this.yBodyRot;
/*      */   }
/*      */   
/*      */   protected void triggerItemUseEffects(ItemStack $$0, int $$1) {
/* 3150 */     if ($$0.isEmpty() || !isUsingItem()) {
/*      */       return;
/*      */     }
/*      */     
/* 3154 */     if ($$0.getUseAnimation() == UseAnim.DRINK) {
/* 3155 */       playSound(getDrinkingSound($$0), 0.5F, (level()).random.nextFloat() * 0.1F + 0.9F);
/*      */     }
/*      */     
/* 3158 */     if ($$0.getUseAnimation() == UseAnim.EAT) {
/* 3159 */       spawnItemParticles($$0, $$1);
/* 3160 */       playSound(getEatingSound($$0), 0.5F + 0.5F * this.random.nextInt(2), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void spawnItemParticles(ItemStack $$0, int $$1) {
/* 3165 */     for (int $$2 = 0; $$2 < $$1; $$2++) {
/* 3166 */       Vec3 $$3 = new Vec3((this.random.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);
/* 3167 */       $$3 = $$3.xRot(-getXRot() * 0.017453292F);
/* 3168 */       $$3 = $$3.yRot(-getYRot() * 0.017453292F);
/*      */       
/* 3170 */       double $$4 = -this.random.nextFloat() * 0.6D - 0.3D;
/* 3171 */       Vec3 $$5 = new Vec3((this.random.nextFloat() - 0.5D) * 0.3D, $$4, 0.6D);
/* 3172 */       $$5 = $$5.xRot(-getXRot() * 0.017453292F);
/* 3173 */       $$5 = $$5.yRot(-getYRot() * 0.017453292F);
/* 3174 */       $$5 = $$5.add(getX(), getEyeY(), getZ());
/* 3175 */       level().addParticle((ParticleOptions)new ItemParticleOption(ParticleTypes.ITEM, $$0), $$5.x, $$5.y, $$5.z, $$3.x, $$3.y + 0.05D, $$3.z);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void completeUsingItem() {
/* 3180 */     if ((level()).isClientSide && !isUsingItem()) {
/*      */       return;
/*      */     }
/*      */     
/* 3184 */     InteractionHand $$0 = getUsedItemHand();
/* 3185 */     if (!this.useItem.equals(getItemInHand($$0))) {
/* 3186 */       releaseUsingItem();
/*      */       
/*      */       return;
/*      */     } 
/* 3190 */     if (!this.useItem.isEmpty() && isUsingItem()) {
/* 3191 */       triggerItemUseEffects(this.useItem, 16);
/* 3192 */       ItemStack $$1 = this.useItem.finishUsingItem(level(), this);
/* 3193 */       if ($$1 != this.useItem) {
/* 3194 */         setItemInHand($$0, $$1);
/*      */       }
/* 3196 */       stopUsingItem();
/*      */     } 
/*      */   }
/*      */   
/*      */   public ItemStack getUseItem() {
/* 3201 */     return this.useItem;
/*      */   }
/*      */   
/*      */   public int getUseItemRemainingTicks() {
/* 3205 */     return this.useItemRemaining;
/*      */   }
/*      */   
/*      */   public int getTicksUsingItem() {
/* 3209 */     if (isUsingItem()) {
/* 3210 */       return this.useItem.getUseDuration() - getUseItemRemainingTicks();
/*      */     }
/* 3212 */     return 0;
/*      */   }
/*      */   
/*      */   public void releaseUsingItem() {
/* 3216 */     if (!this.useItem.isEmpty()) {
/* 3217 */       this.useItem.releaseUsing(level(), this, getUseItemRemainingTicks());
/* 3218 */       if (this.useItem.useOnRelease()) {
/* 3219 */         updatingUsingItem();
/*      */       }
/*      */     } 
/* 3222 */     stopUsingItem();
/*      */   }
/*      */   
/*      */   public void stopUsingItem() {
/* 3226 */     if (!(level()).isClientSide) {
/* 3227 */       boolean $$0 = isUsingItem();
/* 3228 */       setLivingEntityFlag(1, false);
/* 3229 */       if ($$0) {
/* 3230 */         gameEvent(GameEvent.ITEM_INTERACT_FINISH);
/*      */       }
/*      */     } 
/* 3233 */     this.useItem = ItemStack.EMPTY;
/* 3234 */     this.useItemRemaining = 0;
/*      */   }
/*      */   
/*      */   public boolean isBlocking() {
/* 3238 */     if (!isUsingItem() || this.useItem.isEmpty()) {
/* 3239 */       return false;
/*      */     }
/* 3241 */     Item $$0 = this.useItem.getItem();
/* 3242 */     if ($$0.getUseAnimation(this.useItem) != UseAnim.BLOCK) {
/* 3243 */       return false;
/*      */     }
/* 3245 */     if ($$0.getUseDuration(this.useItem) - this.useItemRemaining < 5) {
/* 3246 */       return false;
/*      */     }
/* 3248 */     return true;
/*      */   }
/*      */   
/*      */   public boolean isSuppressingSlidingDownLadder() {
/* 3252 */     return isShiftKeyDown();
/*      */   }
/*      */   
/*      */   public boolean isFallFlying() {
/* 3256 */     return getSharedFlag(7);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isVisuallySwimming() {
/* 3263 */     return (super.isVisuallySwimming() || (!isFallFlying() && hasPose(Pose.FALL_FLYING)));
/*      */   }
/*      */   
/*      */   public int getFallFlyingTicks() {
/* 3267 */     return this.fallFlyTicks;
/*      */   }
/*      */   
/*      */   public boolean randomTeleport(double $$0, double $$1, double $$2, boolean $$3) {
/* 3271 */     double $$4 = getX();
/* 3272 */     double $$5 = getY();
/* 3273 */     double $$6 = getZ();
/*      */     
/* 3275 */     double $$7 = $$1;
/* 3276 */     boolean $$8 = false;
/* 3277 */     BlockPos $$9 = BlockPos.containing($$0, $$7, $$2);
/* 3278 */     Level $$10 = level();
/*      */     
/* 3280 */     if ($$10.hasChunkAt($$9)) {
/*      */       
/* 3282 */       boolean $$11 = false;
/* 3283 */       while (!$$11 && $$9.getY() > $$10.getMinBuildHeight()) {
/* 3284 */         BlockPos $$12 = $$9.below();
/* 3285 */         BlockState $$13 = $$10.getBlockState($$12);
/* 3286 */         if ($$13.blocksMotion()) {
/* 3287 */           $$11 = true; continue;
/*      */         } 
/* 3289 */         $$7--;
/* 3290 */         $$9 = $$12;
/*      */       } 
/*      */       
/* 3293 */       if ($$11) {
/* 3294 */         teleportTo($$0, $$7, $$2);
/* 3295 */         if ($$10.noCollision(this) && !$$10.containsAnyLiquid(getBoundingBox())) {
/* 3296 */           $$8 = true;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 3301 */     if (!$$8) {
/* 3302 */       teleportTo($$4, $$5, $$6);
/* 3303 */       return false;
/*      */     } 
/*      */     
/* 3306 */     if ($$3) {
/* 3307 */       $$10.broadcastEntityEvent(this, (byte)46);
/*      */     }
/*      */     
/* 3310 */     LivingEntity livingEntity = this; if (livingEntity instanceof PathfinderMob) { PathfinderMob $$14 = (PathfinderMob)livingEntity;
/* 3311 */       $$14.getNavigation().stop(); }
/*      */ 
/*      */     
/* 3314 */     return true;
/*      */   }
/*      */   
/*      */   public boolean isAffectedByPotions() {
/* 3318 */     return !isDeadOrDying();
/*      */   }
/*      */   
/*      */   public boolean attackable() {
/* 3322 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setRecordPlayingNearby(BlockPos $$0, boolean $$1) {}
/*      */   
/*      */   public boolean canTakeItem(ItemStack $$0) {
/* 3329 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public EntityDimensions getDimensions(Pose $$0) {
/* 3334 */     return ($$0 == Pose.SLEEPING) ? SLEEPING_DIMENSIONS : super.getDimensions($$0).scale(getScale());
/*      */   }
/*      */   
/*      */   public ImmutableList<Pose> getDismountPoses() {
/* 3338 */     return ImmutableList.of(Pose.STANDING);
/*      */   }
/*      */   
/*      */   public AABB getLocalBoundsForPose(Pose $$0) {
/* 3342 */     EntityDimensions $$1 = getDimensions($$0);
/* 3343 */     return new AABB((-$$1.width / 2.0F), 0.0D, (-$$1.width / 2.0F), ($$1.width / 2.0F), $$1.height, ($$1.width / 2.0F));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean wouldNotSuffocateAtTargetPose(Pose $$0) {
/* 3350 */     AABB $$1 = getDimensions($$0).makeBoundingBox(position());
/* 3351 */     return level().noBlockCollision(this, $$1);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canChangeDimensions() {
/* 3356 */     return (super.canChangeDimensions() && !isSleeping());
/*      */   }
/*      */   
/*      */   public Optional<BlockPos> getSleepingPos() {
/* 3360 */     return (Optional<BlockPos>)this.entityData.get(SLEEPING_POS_ID);
/*      */   }
/*      */   
/*      */   public void setSleepingPos(BlockPos $$0) {
/* 3364 */     this.entityData.set(SLEEPING_POS_ID, Optional.of($$0));
/*      */   }
/*      */   
/*      */   public void clearSleepingPos() {
/* 3368 */     this.entityData.set(SLEEPING_POS_ID, Optional.empty());
/*      */   }
/*      */   
/*      */   public boolean isSleeping() {
/* 3372 */     return getSleepingPos().isPresent();
/*      */   }
/*      */   
/*      */   public void startSleeping(BlockPos $$0) {
/* 3376 */     if (isPassenger()) {
/* 3377 */       stopRiding();
/*      */     }
/*      */     
/* 3380 */     BlockState $$1 = level().getBlockState($$0);
/* 3381 */     if ($$1.getBlock() instanceof BedBlock) {
/* 3382 */       level().setBlock($$0, (BlockState)$$1.setValue((Property)BedBlock.OCCUPIED, Boolean.valueOf(true)), 3);
/*      */     }
/*      */     
/* 3385 */     setPose(Pose.SLEEPING);
/* 3386 */     setPosToBed($$0);
/* 3387 */     setSleepingPos($$0);
/* 3388 */     setDeltaMovement(Vec3.ZERO);
/* 3389 */     this.hasImpulse = true;
/*      */   }
/*      */   
/*      */   private void setPosToBed(BlockPos $$0) {
/* 3393 */     setPos($$0.getX() + 0.5D, $$0.getY() + 0.6875D, $$0.getZ() + 0.5D);
/*      */   }
/*      */   
/*      */   private boolean checkBedExists() {
/* 3397 */     return ((Boolean)getSleepingPos().<Boolean>map($$0 -> Boolean.valueOf(level().getBlockState($$0).getBlock() instanceof BedBlock)).orElse(Boolean.valueOf(false))).booleanValue();
/*      */   }
/*      */   
/*      */   public void stopSleeping() {
/* 3401 */     Objects.requireNonNull(level()); getSleepingPos().filter(level()::hasChunkAt).ifPresent($$0 -> {
/*      */           BlockState $$1 = level().getBlockState($$0);
/*      */           
/*      */           if ($$1.getBlock() instanceof BedBlock) {
/*      */             Direction $$2 = (Direction)$$1.getValue((Property)BedBlock.FACING);
/*      */             
/*      */             level().setBlock($$0, (BlockState)$$1.setValue((Property)BedBlock.OCCUPIED, Boolean.valueOf(false)), 3);
/*      */             
/*      */             Vec3 $$3 = BedBlock.findStandUpPosition(getType(), (CollisionGetter)level(), $$0, $$2, getYRot()).orElseGet(());
/*      */             
/*      */             Vec3 $$4 = Vec3.atBottomCenterOf((Vec3i)$$0).subtract($$3).normalize();
/*      */             
/*      */             float $$5 = (float)Mth.wrapDegrees(Mth.atan2($$4.z, $$4.x) * 57.2957763671875D - 90.0D);
/*      */             
/*      */             setPos($$3.x, $$3.y, $$3.z);
/*      */             
/*      */             setYRot($$5);
/*      */             setXRot(0.0F);
/*      */           } 
/*      */         });
/* 3421 */     Vec3 $$0 = position();
/* 3422 */     setPose(Pose.STANDING);
/* 3423 */     setPos($$0.x, $$0.y, $$0.z);
/* 3424 */     clearSleepingPos();
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public Direction getBedOrientation() {
/* 3429 */     BlockPos $$0 = getSleepingPos().orElse(null);
/* 3430 */     return ($$0 != null) ? BedBlock.getBedOrientation((BlockGetter)level(), $$0) : null;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isInWall() {
/* 3435 */     return (!isSleeping() && super.isInWall());
/*      */   }
/*      */ 
/*      */   
/*      */   protected final float getEyeHeight(Pose $$0, EntityDimensions $$1) {
/* 3440 */     return ($$0 == Pose.SLEEPING) ? 0.2F : getStandingEyeHeight($$0, $$1);
/*      */   }
/*      */   
/*      */   protected float getStandingEyeHeight(Pose $$0, EntityDimensions $$1) {
/* 3444 */     return super.getEyeHeight($$0, $$1);
/*      */   }
/*      */   
/*      */   public ItemStack getProjectile(ItemStack $$0) {
/* 3448 */     return ItemStack.EMPTY;
/*      */   }
/*      */   
/*      */   public ItemStack eat(Level $$0, ItemStack $$1) {
/* 3452 */     if ($$1.isEdible()) {
/* 3453 */       $$0.playSound(null, getX(), getY(), getZ(), getEatingSound($$1), SoundSource.NEUTRAL, 1.0F, 1.0F + ($$0.random.nextFloat() - $$0.random.nextFloat()) * 0.4F);
/* 3454 */       addEatEffect($$1, $$0, this);
/*      */       
/* 3456 */       if (!(this instanceof Player) || !(((Player)this).getAbilities()).instabuild) {
/* 3457 */         $$1.shrink(1);
/*      */       }
/* 3459 */       gameEvent(GameEvent.EAT);
/*      */     } 
/* 3461 */     return $$1;
/*      */   }
/*      */   
/*      */   private void addEatEffect(ItemStack $$0, Level $$1, LivingEntity $$2) {
/* 3465 */     Item $$3 = $$0.getItem();
/* 3466 */     if ($$3.isEdible()) {
/* 3467 */       List<Pair<MobEffectInstance, Float>> $$4 = $$3.getFoodProperties().getEffects();
/* 3468 */       for (Pair<MobEffectInstance, Float> $$5 : $$4) {
/* 3469 */         if (!$$1.isClientSide && $$5.getFirst() != null && $$1.random.nextFloat() < ((Float)$$5.getSecond()).floatValue()) {
/* 3470 */           $$2.addEffect(new MobEffectInstance((MobEffectInstance)$$5.getFirst()));
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private static byte entityEventForEquipmentBreak(EquipmentSlot $$0) {
/* 3477 */     switch ($$0) {
/*      */       case MAINHAND:
/* 3479 */         return 47;
/*      */       case OFFHAND:
/* 3481 */         return 48;
/*      */       case HEAD:
/* 3483 */         return 49;
/*      */       case CHEST:
/* 3485 */         return 50;
/*      */       case FEET:
/* 3487 */         return 52;
/*      */       case LEGS:
/* 3489 */         return 51;
/*      */     } 
/* 3491 */     return 47;
/*      */   }
/*      */ 
/*      */   
/*      */   public void broadcastBreakEvent(EquipmentSlot $$0) {
/* 3496 */     level().broadcastEntityEvent(this, entityEventForEquipmentBreak($$0));
/*      */   }
/*      */   
/*      */   public void broadcastBreakEvent(InteractionHand $$0) {
/* 3500 */     broadcastBreakEvent(($$0 == InteractionHand.MAIN_HAND) ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
/*      */   }
/*      */ 
/*      */   
/*      */   public AABB getBoundingBoxForCulling() {
/* 3505 */     if (getItemBySlot(EquipmentSlot.HEAD).is(Items.DRAGON_HEAD)) {
/* 3506 */       float $$0 = 0.5F;
/* 3507 */       return getBoundingBox().inflate(0.5D, 0.5D, 0.5D);
/*      */     } 
/* 3509 */     return super.getBoundingBoxForCulling();
/*      */   }
/*      */   
/*      */   public static EquipmentSlot getEquipmentSlotForItem(ItemStack $$0) {
/* 3513 */     Equipable $$1 = Equipable.get($$0);
/*      */     
/* 3515 */     if ($$1 != null) {
/* 3516 */       return $$1.getEquipmentSlot();
/*      */     }
/*      */     
/* 3519 */     return EquipmentSlot.MAINHAND;
/*      */   }
/*      */   
/*      */   private static SlotAccess createEquipmentSlotAccess(LivingEntity $$0, EquipmentSlot $$1) {
/* 3523 */     if ($$1 == EquipmentSlot.HEAD || $$1 == EquipmentSlot.MAINHAND || $$1 == EquipmentSlot.OFFHAND) {
/* 3524 */       return SlotAccess.forEquipmentSlot($$0, $$1);
/*      */     }
/* 3526 */     return SlotAccess.forEquipmentSlot($$0, $$1, $$1 -> ($$1.isEmpty() || Mob.getEquipmentSlotForItem($$1) == $$0));
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   private static EquipmentSlot getEquipmentSlot(int $$0) {
/* 3532 */     if ($$0 == 100 + EquipmentSlot.HEAD.getIndex()) {
/* 3533 */       return EquipmentSlot.HEAD;
/*      */     }
/* 3535 */     if ($$0 == 100 + EquipmentSlot.CHEST.getIndex()) {
/* 3536 */       return EquipmentSlot.CHEST;
/*      */     }
/* 3538 */     if ($$0 == 100 + EquipmentSlot.LEGS.getIndex()) {
/* 3539 */       return EquipmentSlot.LEGS;
/*      */     }
/* 3541 */     if ($$0 == 100 + EquipmentSlot.FEET.getIndex()) {
/* 3542 */       return EquipmentSlot.FEET;
/*      */     }
/* 3544 */     if ($$0 == 98) {
/* 3545 */       return EquipmentSlot.MAINHAND;
/*      */     }
/* 3547 */     if ($$0 == 99) {
/* 3548 */       return EquipmentSlot.OFFHAND;
/*      */     }
/* 3550 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public SlotAccess getSlot(int $$0) {
/* 3555 */     EquipmentSlot $$1 = getEquipmentSlot($$0);
/*      */     
/* 3557 */     if ($$1 != null) {
/* 3558 */       return createEquipmentSlotAccess(this, $$1);
/*      */     }
/*      */     
/* 3561 */     return super.getSlot($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canFreeze() {
/* 3566 */     if (isSpectator()) {
/* 3567 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 3572 */     boolean $$0 = (!getItemBySlot(EquipmentSlot.HEAD).is(ItemTags.FREEZE_IMMUNE_WEARABLES) && !getItemBySlot(EquipmentSlot.CHEST).is(ItemTags.FREEZE_IMMUNE_WEARABLES) && !getItemBySlot(EquipmentSlot.LEGS).is(ItemTags.FREEZE_IMMUNE_WEARABLES) && !getItemBySlot(EquipmentSlot.FEET).is(ItemTags.FREEZE_IMMUNE_WEARABLES));
/* 3573 */     return ($$0 && super.canFreeze());
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isCurrentlyGlowing() {
/* 3578 */     return ((!level().isClientSide() && hasEffect(MobEffects.GLOWING)) || super.isCurrentlyGlowing());
/*      */   }
/*      */ 
/*      */   
/*      */   public float getVisualRotationYInDegrees() {
/* 3583 */     return this.yBodyRot;
/*      */   }
/*      */ 
/*      */   
/*      */   public void recreateFromPacket(ClientboundAddEntityPacket $$0) {
/* 3588 */     double $$1 = $$0.getX();
/* 3589 */     double $$2 = $$0.getY();
/* 3590 */     double $$3 = $$0.getZ();
/* 3591 */     float $$4 = $$0.getYRot();
/* 3592 */     float $$5 = $$0.getXRot();
/* 3593 */     syncPacketPositionCodec($$1, $$2, $$3);
/* 3594 */     this.yBodyRot = $$0.getYHeadRot();
/* 3595 */     this.yHeadRot = $$0.getYHeadRot();
/* 3596 */     this.yBodyRotO = this.yBodyRot;
/* 3597 */     this.yHeadRotO = this.yHeadRot;
/*      */     
/* 3599 */     setId($$0.getId());
/* 3600 */     setUUID($$0.getUUID());
/* 3601 */     absMoveTo($$1, $$2, $$3, $$4, $$5);
/* 3602 */     setDeltaMovement($$0
/* 3603 */         .getXa(), $$0
/* 3604 */         .getYa(), $$0
/* 3605 */         .getZa());
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canDisableShield() {
/* 3610 */     return getMainHandItem().getItem() instanceof net.minecraft.world.item.AxeItem;
/*      */   }
/*      */ 
/*      */   
/*      */   public float maxUpStep() {
/* 3615 */     float $$0 = super.maxUpStep();
/* 3616 */     return (getControllingPassenger() instanceof Player) ? Math.max($$0, 1.0F) : $$0;
/*      */   }
/*      */ 
/*      */   
/*      */   public Vec3 getPassengerRidingPosition(Entity $$0) {
/* 3621 */     return (new Vec3(getPassengerAttachmentPoint($$0, getDimensions(getPose()), getScale())
/* 3622 */         .rotateY(-this.yBodyRot * 0.017453292F)))
/* 3623 */       .add(position());
/*      */   }
/*      */ 
/*      */   
/*      */   public float getMyRidingOffset(Entity $$0) {
/* 3628 */     return ridingOffset($$0) * getScale();
/*      */   }
/*      */   
/*      */   protected void lerpHeadRotationStep(int $$0, double $$1) {
/* 3632 */     this.yHeadRot = (float)Mth.rotLerp(1.0D / $$0, this.yHeadRot, $$1);
/*      */   }
/*      */   
/*      */   public abstract Iterable<ItemStack> getArmorSlots();
/*      */   
/*      */   public abstract ItemStack getItemBySlot(EquipmentSlot paramEquipmentSlot);
/*      */   
/*      */   public abstract void setItemSlot(EquipmentSlot paramEquipmentSlot, ItemStack paramItemStack);
/*      */   
/*      */   public abstract HumanoidArm getMainArm();
/*      */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\LivingEntity.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */