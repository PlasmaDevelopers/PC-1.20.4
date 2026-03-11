/*      */ package net.minecraft.world.entity.player;
/*      */ 
/*      */ import com.google.common.collect.ImmutableList;
/*      */ import com.google.common.collect.ImmutableMap;
/*      */ import com.google.common.collect.Lists;
/*      */ import com.mojang.authlib.GameProfile;
/*      */ import com.mojang.datafixers.util.Either;
/*      */ import com.mojang.logging.LogUtils;
/*      */ import com.mojang.serialization.DynamicOps;
/*      */ import java.util.Collection;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Objects;
/*      */ import java.util.Optional;
/*      */ import java.util.OptionalInt;
/*      */ import java.util.function.Predicate;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.Util;
/*      */ import net.minecraft.advancements.CriteriaTriggers;
/*      */ import net.minecraft.core.BlockPos;
/*      */ import net.minecraft.core.Direction;
/*      */ import net.minecraft.core.GlobalPos;
/*      */ import net.minecraft.core.particles.ParticleOptions;
/*      */ import net.minecraft.core.particles.ParticleTypes;
/*      */ import net.minecraft.core.registries.Registries;
/*      */ import net.minecraft.nbt.CompoundTag;
/*      */ import net.minecraft.nbt.ListTag;
/*      */ import net.minecraft.nbt.NbtOps;
/*      */ import net.minecraft.nbt.NbtUtils;
/*      */ import net.minecraft.nbt.Tag;
/*      */ import net.minecraft.network.chat.ClickEvent;
/*      */ import net.minecraft.network.chat.Component;
/*      */ import net.minecraft.network.chat.MutableComponent;
/*      */ import net.minecraft.network.chat.Style;
/*      */ import net.minecraft.network.protocol.Packet;
/*      */ import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
/*      */ import net.minecraft.network.syncher.EntityDataAccessor;
/*      */ import net.minecraft.network.syncher.EntityDataSerializers;
/*      */ import net.minecraft.network.syncher.SynchedEntityData;
/*      */ import net.minecraft.resources.ResourceLocation;
/*      */ import net.minecraft.server.level.ServerLevel;
/*      */ import net.minecraft.server.level.ServerPlayer;
/*      */ import net.minecraft.sounds.SoundEvent;
/*      */ import net.minecraft.sounds.SoundEvents;
/*      */ import net.minecraft.sounds.SoundSource;
/*      */ import net.minecraft.stats.Stat;
/*      */ import net.minecraft.stats.Stats;
/*      */ import net.minecraft.tags.BlockTags;
/*      */ import net.minecraft.tags.DamageTypeTags;
/*      */ import net.minecraft.tags.FluidTags;
/*      */ import net.minecraft.util.Mth;
/*      */ import net.minecraft.util.Unit;
/*      */ import net.minecraft.world.Container;
/*      */ import net.minecraft.world.Difficulty;
/*      */ import net.minecraft.world.InteractionHand;
/*      */ import net.minecraft.world.InteractionResult;
/*      */ import net.minecraft.world.MenuProvider;
/*      */ import net.minecraft.world.damagesource.DamageSource;
/*      */ import net.minecraft.world.effect.MobEffectInstance;
/*      */ import net.minecraft.world.effect.MobEffectUtil;
/*      */ import net.minecraft.world.effect.MobEffects;
/*      */ import net.minecraft.world.entity.Entity;
/*      */ import net.minecraft.world.entity.EntityDimensions;
/*      */ import net.minecraft.world.entity.EntityType;
/*      */ import net.minecraft.world.entity.EquipmentSlot;
/*      */ import net.minecraft.world.entity.HumanoidArm;
/*      */ import net.minecraft.world.entity.LivingEntity;
/*      */ import net.minecraft.world.entity.Mob;
/*      */ import net.minecraft.world.entity.MobType;
/*      */ import net.minecraft.world.entity.MoverType;
/*      */ import net.minecraft.world.entity.Pose;
/*      */ import net.minecraft.world.entity.SlotAccess;
/*      */ import net.minecraft.world.entity.TamableAnimal;
/*      */ import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
/*      */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*      */ import net.minecraft.world.entity.animal.Parrot;
/*      */ import net.minecraft.world.entity.animal.horse.AbstractHorse;
/*      */ import net.minecraft.world.entity.boss.EnderDragonPart;
/*      */ import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
/*      */ import net.minecraft.world.entity.decoration.ArmorStand;
/*      */ import net.minecraft.world.entity.item.ItemEntity;
/*      */ import net.minecraft.world.entity.monster.warden.WardenSpawnTracker;
/*      */ import net.minecraft.world.entity.projectile.FishingHook;
/*      */ import net.minecraft.world.food.FoodData;
/*      */ import net.minecraft.world.inventory.AbstractContainerMenu;
/*      */ import net.minecraft.world.inventory.ClickAction;
/*      */ import net.minecraft.world.inventory.InventoryMenu;
/*      */ import net.minecraft.world.inventory.PlayerEnderChestContainer;
/*      */ import net.minecraft.world.item.ElytraItem;
/*      */ import net.minecraft.world.item.ItemCooldowns;
/*      */ import net.minecraft.world.item.ItemStack;
/*      */ import net.minecraft.world.item.Items;
/*      */ import net.minecraft.world.item.ProjectileWeaponItem;
/*      */ import net.minecraft.world.item.crafting.RecipeHolder;
/*      */ import net.minecraft.world.item.enchantment.EnchantmentHelper;
/*      */ import net.minecraft.world.item.trading.MerchantOffers;
/*      */ import net.minecraft.world.level.BaseCommandBlock;
/*      */ import net.minecraft.world.level.BlockGetter;
/*      */ import net.minecraft.world.level.CollisionGetter;
/*      */ import net.minecraft.world.level.GameRules;
/*      */ import net.minecraft.world.level.GameType;
/*      */ import net.minecraft.world.level.ItemLike;
/*      */ import net.minecraft.world.level.Level;
/*      */ import net.minecraft.world.level.LevelReader;
/*      */ import net.minecraft.world.level.block.BedBlock;
/*      */ import net.minecraft.world.level.block.Block;
/*      */ import net.minecraft.world.level.block.RespawnAnchorBlock;
/*      */ import net.minecraft.world.level.block.entity.CommandBlockEntity;
/*      */ import net.minecraft.world.level.block.entity.JigsawBlockEntity;
/*      */ import net.minecraft.world.level.block.entity.SignBlockEntity;
/*      */ import net.minecraft.world.level.block.entity.StructureBlockEntity;
/*      */ import net.minecraft.world.level.block.state.BlockState;
/*      */ import net.minecraft.world.level.block.state.pattern.BlockInWorld;
/*      */ import net.minecraft.world.level.block.state.properties.Property;
/*      */ import net.minecraft.world.level.gameevent.GameEvent;
/*      */ import net.minecraft.world.phys.AABB;
/*      */ import net.minecraft.world.phys.Vec3;
/*      */ import net.minecraft.world.scores.PlayerTeam;
/*      */ import net.minecraft.world.scores.Scoreboard;
/*      */ import net.minecraft.world.scores.Team;
/*      */ import org.slf4j.Logger;
/*      */ 
/*      */ public abstract class Player
/*      */   extends LivingEntity
/*      */ {
/*  126 */   private static final Logger LOGGER = LogUtils.getLogger();
/*      */   
/*      */   public static final int MAX_NAME_LENGTH = 16;
/*      */   
/*  130 */   public static final HumanoidArm DEFAULT_MAIN_HAND = HumanoidArm.RIGHT;
/*      */   
/*      */   public static final int DEFAULT_MODEL_CUSTOMIZATION = 0;
/*      */   
/*      */   public static final int MAX_HEALTH = 20;
/*      */   
/*      */   public static final int SLEEP_DURATION = 100;
/*      */   
/*      */   public static final int WAKE_UP_DURATION = 10;
/*      */   public static final int ENDER_SLOT_OFFSET = 200;
/*      */   public static final float CROUCH_BB_HEIGHT = 1.5F;
/*      */   public static final float SWIMMING_BB_WIDTH = 0.6F;
/*      */   public static final float SWIMMING_BB_HEIGHT = 0.6F;
/*      */   public static final float DEFAULT_EYE_HEIGHT = 1.62F;
/*  144 */   public static final EntityDimensions STANDING_DIMENSIONS = EntityDimensions.scalable(0.6F, 1.8F);
/*  145 */   private static final Map<Pose, EntityDimensions> POSES = (Map<Pose, EntityDimensions>)ImmutableMap.builder()
/*  146 */     .put(Pose.STANDING, STANDING_DIMENSIONS)
/*  147 */     .put(Pose.SLEEPING, SLEEPING_DIMENSIONS)
/*  148 */     .put(Pose.FALL_FLYING, EntityDimensions.scalable(0.6F, 0.6F))
/*  149 */     .put(Pose.SWIMMING, EntityDimensions.scalable(0.6F, 0.6F))
/*  150 */     .put(Pose.SPIN_ATTACK, EntityDimensions.scalable(0.6F, 0.6F))
/*  151 */     .put(Pose.CROUCHING, EntityDimensions.scalable(0.6F, 1.5F))
/*  152 */     .put(Pose.DYING, EntityDimensions.fixed(0.2F, 0.2F))
/*  153 */     .build();
/*      */   
/*  155 */   private static final EntityDataAccessor<Float> DATA_PLAYER_ABSORPTION_ID = SynchedEntityData.defineId(Player.class, EntityDataSerializers.FLOAT);
/*  156 */   private static final EntityDataAccessor<Integer> DATA_SCORE_ID = SynchedEntityData.defineId(Player.class, EntityDataSerializers.INT);
/*  157 */   protected static final EntityDataAccessor<Byte> DATA_PLAYER_MODE_CUSTOMISATION = SynchedEntityData.defineId(Player.class, EntityDataSerializers.BYTE);
/*  158 */   protected static final EntityDataAccessor<Byte> DATA_PLAYER_MAIN_HAND = SynchedEntityData.defineId(Player.class, EntityDataSerializers.BYTE);
/*      */   
/*  160 */   protected static final EntityDataAccessor<CompoundTag> DATA_SHOULDER_LEFT = SynchedEntityData.defineId(Player.class, EntityDataSerializers.COMPOUND_TAG);
/*  161 */   protected static final EntityDataAccessor<CompoundTag> DATA_SHOULDER_RIGHT = SynchedEntityData.defineId(Player.class, EntityDataSerializers.COMPOUND_TAG);
/*      */   
/*      */   private long timeEntitySatOnShoulder;
/*  164 */   private final Inventory inventory = new Inventory(this);
/*  165 */   protected PlayerEnderChestContainer enderChestInventory = new PlayerEnderChestContainer();
/*      */   
/*      */   public final InventoryMenu inventoryMenu;
/*      */   public AbstractContainerMenu containerMenu;
/*  169 */   protected FoodData foodData = new FoodData();
/*      */   
/*      */   protected int jumpTriggerTime;
/*      */   
/*      */   public float oBob;
/*      */   
/*      */   public float bob;
/*      */   
/*      */   public int takeXpDelay;
/*      */   
/*      */   public double xCloakO;
/*      */   
/*      */   public double yCloakO;
/*      */   
/*      */   public double zCloakO;
/*      */   public double xCloak;
/*      */   public double yCloak;
/*      */   public double zCloak;
/*      */   private int sleepCounter;
/*      */   protected boolean wasUnderwater;
/*  189 */   private final Abilities abilities = new Abilities();
/*      */   
/*      */   public int experienceLevel;
/*      */   
/*      */   public int totalExperience;
/*      */   public float experienceProgress;
/*      */   protected int enchantmentSeed;
/*  196 */   protected final float defaultFlySpeed = 0.02F;
/*      */   private int lastLevelUpTime;
/*      */   private final GameProfile gameProfile;
/*      */   private boolean reducedDebugInfo;
/*  200 */   private ItemStack lastItemInMainHand = ItemStack.EMPTY;
/*  201 */   private final ItemCooldowns cooldowns = createItemCooldowns();
/*  202 */   private Optional<GlobalPos> lastDeathLocation = Optional.empty();
/*      */   
/*      */   @Nullable
/*      */   public FishingHook fishing;
/*      */   protected float hurtDir;
/*      */   
/*      */   public Player(Level $$0, BlockPos $$1, float $$2, GameProfile $$3) {
/*  209 */     super(EntityType.PLAYER, $$0);
/*  210 */     setUUID($$3.getId());
/*      */     
/*  212 */     this.gameProfile = $$3;
/*      */     
/*  214 */     this.inventoryMenu = new InventoryMenu(this.inventory, !$$0.isClientSide, this);
/*  215 */     this.containerMenu = (AbstractContainerMenu)this.inventoryMenu;
/*      */     
/*  217 */     moveTo($$1.getX() + 0.5D, ($$1.getY() + 1), $$1.getZ() + 0.5D, $$2, 0.0F);
/*      */     
/*  219 */     this.rotOffs = 180.0F;
/*      */   }
/*      */   
/*      */   public boolean blockActionRestricted(Level $$0, BlockPos $$1, GameType $$2) {
/*  223 */     if (!$$2.isBlockPlacingRestricted()) {
/*  224 */       return false;
/*      */     }
/*  226 */     if ($$2 == GameType.SPECTATOR) {
/*  227 */       return true;
/*      */     }
/*  229 */     if (mayBuild()) {
/*  230 */       return false;
/*      */     }
/*  232 */     ItemStack $$3 = getMainHandItem();
/*  233 */     return ($$3.isEmpty() || !$$3.hasAdventureModeBreakTagForBlock($$0.registryAccess().registryOrThrow(Registries.BLOCK), new BlockInWorld((LevelReader)$$0, $$1, false)));
/*      */   }
/*      */   
/*      */   public static AttributeSupplier.Builder createAttributes() {
/*  237 */     return LivingEntity.createLivingAttributes()
/*  238 */       .add(Attributes.ATTACK_DAMAGE, 1.0D)
/*  239 */       .add(Attributes.MOVEMENT_SPEED, 0.10000000149011612D)
/*  240 */       .add(Attributes.ATTACK_SPEED)
/*  241 */       .add(Attributes.LUCK);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void defineSynchedData() {
/*  246 */     super.defineSynchedData();
/*      */     
/*  248 */     this.entityData.define(DATA_PLAYER_ABSORPTION_ID, Float.valueOf(0.0F));
/*  249 */     this.entityData.define(DATA_SCORE_ID, Integer.valueOf(0));
/*  250 */     this.entityData.define(DATA_PLAYER_MODE_CUSTOMISATION, Byte.valueOf((byte)0));
/*  251 */     this.entityData.define(DATA_PLAYER_MAIN_HAND, Byte.valueOf((byte)DEFAULT_MAIN_HAND.getId()));
/*  252 */     this.entityData.define(DATA_SHOULDER_LEFT, new CompoundTag());
/*  253 */     this.entityData.define(DATA_SHOULDER_RIGHT, new CompoundTag());
/*      */   }
/*      */ 
/*      */   
/*      */   public void tick() {
/*  258 */     this.noPhysics = isSpectator();
/*  259 */     if (isSpectator()) {
/*  260 */       setOnGround(false);
/*      */     }
/*      */     
/*  263 */     if (this.takeXpDelay > 0) {
/*  264 */       this.takeXpDelay--;
/*      */     }
/*  266 */     if (isSleeping()) {
/*  267 */       this.sleepCounter++;
/*  268 */       if (this.sleepCounter > 100) {
/*  269 */         this.sleepCounter = 100;
/*      */       }
/*      */       
/*  272 */       if (!(level()).isClientSide && level().isDay()) {
/*  273 */         stopSleepInBed(false, true);
/*      */       }
/*  275 */     } else if (this.sleepCounter > 0) {
/*  276 */       this.sleepCounter++;
/*  277 */       if (this.sleepCounter >= 110) {
/*  278 */         this.sleepCounter = 0;
/*      */       }
/*      */     } 
/*      */     
/*  282 */     updateIsUnderwater();
/*      */     
/*  284 */     super.tick();
/*      */     
/*  286 */     if (!(level()).isClientSide && 
/*  287 */       this.containerMenu != null && !this.containerMenu.stillValid(this)) {
/*  288 */       closeContainer();
/*  289 */       this.containerMenu = (AbstractContainerMenu)this.inventoryMenu;
/*      */     } 
/*      */ 
/*      */     
/*  293 */     moveCloak();
/*      */     
/*  295 */     if (!(level()).isClientSide) {
/*  296 */       this.foodData.tick(this);
/*  297 */       awardStat(Stats.PLAY_TIME);
/*  298 */       awardStat(Stats.TOTAL_WORLD_TIME);
/*  299 */       if (isAlive()) {
/*  300 */         awardStat(Stats.TIME_SINCE_DEATH);
/*      */       }
/*  302 */       if (isDiscrete()) {
/*  303 */         awardStat(Stats.CROUCH_TIME);
/*      */       }
/*  305 */       if (!isSleeping()) {
/*  306 */         awardStat(Stats.TIME_SINCE_REST);
/*      */       }
/*      */     } 
/*      */     
/*  310 */     int $$0 = 29999999;
/*  311 */     double $$1 = Mth.clamp(getX(), -2.9999999E7D, 2.9999999E7D);
/*  312 */     double $$2 = Mth.clamp(getZ(), -2.9999999E7D, 2.9999999E7D);
/*  313 */     if ($$1 != getX() || $$2 != getZ()) {
/*  314 */       setPos($$1, getY(), $$2);
/*      */     }
/*      */     
/*  317 */     this.attackStrengthTicker++;
/*      */     
/*  319 */     ItemStack $$3 = getMainHandItem();
/*  320 */     if (!ItemStack.matches(this.lastItemInMainHand, $$3)) {
/*      */ 
/*      */ 
/*      */       
/*  324 */       if (!ItemStack.isSameItem(this.lastItemInMainHand, $$3)) {
/*  325 */         resetAttackStrengthTicker();
/*      */       }
/*  327 */       this.lastItemInMainHand = $$3.copy();
/*      */     } 
/*      */     
/*  330 */     turtleHelmetTick();
/*  331 */     this.cooldowns.tick();
/*  332 */     updatePlayerPose();
/*      */   }
/*      */ 
/*      */   
/*      */   protected float getMaxHeadRotationRelativeToBody() {
/*  337 */     if (isBlocking()) {
/*  338 */       return 15.0F;
/*      */     }
/*  340 */     return super.getMaxHeadRotationRelativeToBody();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSecondaryUseActive() {
/*  350 */     return isShiftKeyDown();
/*      */   }
/*      */   
/*      */   protected boolean wantsToStopRiding() {
/*  354 */     return isShiftKeyDown();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isStayingOnGroundSurface() {
/*  362 */     return isShiftKeyDown();
/*      */   }
/*      */   
/*      */   protected boolean updateIsUnderwater() {
/*  366 */     this.wasUnderwater = isEyeInFluid(FluidTags.WATER);
/*  367 */     return this.wasUnderwater;
/*      */   }
/*      */   
/*      */   private void turtleHelmetTick() {
/*  371 */     ItemStack $$0 = getItemBySlot(EquipmentSlot.HEAD);
/*  372 */     if ($$0.is(Items.TURTLE_HELMET) && !isEyeInFluid(FluidTags.WATER)) {
/*  373 */       addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 200, 0, false, false, true));
/*      */     }
/*      */   }
/*      */   
/*      */   protected ItemCooldowns createItemCooldowns() {
/*  378 */     return new ItemCooldowns();
/*      */   }
/*      */   
/*      */   private void moveCloak() {
/*  382 */     this.xCloakO = this.xCloak;
/*  383 */     this.yCloakO = this.yCloak;
/*  384 */     this.zCloakO = this.zCloak;
/*      */     
/*  386 */     double $$0 = getX() - this.xCloak;
/*  387 */     double $$1 = getY() - this.yCloak;
/*  388 */     double $$2 = getZ() - this.zCloak;
/*      */     
/*  390 */     double $$3 = 10.0D;
/*  391 */     if ($$0 > 10.0D) {
/*  392 */       this.xCloak = getX();
/*  393 */       this.xCloakO = this.xCloak;
/*      */     } 
/*  395 */     if ($$2 > 10.0D) {
/*  396 */       this.zCloak = getZ();
/*  397 */       this.zCloakO = this.zCloak;
/*      */     } 
/*  399 */     if ($$1 > 10.0D) {
/*  400 */       this.yCloak = getY();
/*  401 */       this.yCloakO = this.yCloak;
/*      */     } 
/*  403 */     if ($$0 < -10.0D) {
/*  404 */       this.xCloak = getX();
/*  405 */       this.xCloakO = this.xCloak;
/*      */     } 
/*  407 */     if ($$2 < -10.0D) {
/*  408 */       this.zCloak = getZ();
/*  409 */       this.zCloakO = this.zCloak;
/*      */     } 
/*  411 */     if ($$1 < -10.0D) {
/*  412 */       this.yCloak = getY();
/*  413 */       this.yCloakO = this.yCloak;
/*      */     } 
/*      */     
/*  416 */     this.xCloak += $$0 * 0.25D;
/*  417 */     this.zCloak += $$2 * 0.25D;
/*  418 */     this.yCloak += $$1 * 0.25D;
/*      */   }
/*      */   protected void updatePlayerPose() {
/*      */     Pose $$5, $$8;
/*  422 */     if (!canPlayerFitWithinBlocksAndEntitiesWhen(Pose.SWIMMING)) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  431 */     if (isFallFlying()) {
/*  432 */       Pose $$0 = Pose.FALL_FLYING;
/*  433 */     } else if (isSleeping()) {
/*  434 */       Pose $$1 = Pose.SLEEPING;
/*  435 */     } else if (isSwimming()) {
/*  436 */       Pose $$2 = Pose.SWIMMING;
/*  437 */     } else if (isAutoSpinAttack()) {
/*  438 */       Pose $$3 = Pose.SPIN_ATTACK;
/*  439 */     } else if (isShiftKeyDown() && !this.abilities.flying) {
/*  440 */       Pose $$4 = Pose.CROUCHING;
/*      */     } else {
/*  442 */       $$5 = Pose.STANDING;
/*      */     } 
/*      */ 
/*      */     
/*  446 */     if (isSpectator() || isPassenger() || canPlayerFitWithinBlocksAndEntitiesWhen($$5)) {
/*  447 */       Pose $$6 = $$5;
/*  448 */     } else if (canPlayerFitWithinBlocksAndEntitiesWhen(Pose.CROUCHING)) {
/*      */       
/*  450 */       Pose $$7 = Pose.CROUCHING;
/*      */     } else {
/*      */       
/*  453 */       $$8 = Pose.SWIMMING;
/*      */     } 
/*  455 */     setPose($$8);
/*      */   }
/*      */   
/*      */   protected boolean canPlayerFitWithinBlocksAndEntitiesWhen(Pose $$0) {
/*  459 */     return level().noCollision((Entity)this, getDimensions($$0).makeBoundingBox(position()).deflate(1.0E-7D));
/*      */   }
/*      */ 
/*      */   
/*      */   public int getPortalWaitTime() {
/*  464 */     return Math.max(1, level().getGameRules().getInt(
/*  465 */           this.abilities.invulnerable ? 
/*  466 */           GameRules.RULE_PLAYERS_NETHER_PORTAL_CREATIVE_DELAY : 
/*  467 */           GameRules.RULE_PLAYERS_NETHER_PORTAL_DEFAULT_DELAY));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected SoundEvent getSwimSound() {
/*  473 */     return SoundEvents.PLAYER_SWIM;
/*      */   }
/*      */ 
/*      */   
/*      */   protected SoundEvent getSwimSplashSound() {
/*  478 */     return SoundEvents.PLAYER_SPLASH;
/*      */   }
/*      */ 
/*      */   
/*      */   protected SoundEvent getSwimHighSpeedSplashSound() {
/*  483 */     return SoundEvents.PLAYER_SPLASH_HIGH_SPEED;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getDimensionChangingDelay() {
/*  488 */     return 10;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void playSound(SoundEvent $$0, float $$1, float $$2) {
/*  494 */     level().playSound(this, getX(), getY(), getZ(), $$0, getSoundSource(), $$1, $$2);
/*      */   }
/*      */ 
/*      */   
/*      */   public void playNotifySound(SoundEvent $$0, SoundSource $$1, float $$2, float $$3) {}
/*      */ 
/*      */   
/*      */   public SoundSource getSoundSource() {
/*  502 */     return SoundSource.PLAYERS;
/*      */   }
/*      */ 
/*      */   
/*      */   protected int getFireImmuneTicks() {
/*  507 */     return 20;
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleEntityEvent(byte $$0) {
/*  512 */     if ($$0 == 9) {
/*  513 */       completeUsingItem();
/*  514 */     } else if ($$0 == 23) {
/*  515 */       this.reducedDebugInfo = false;
/*  516 */     } else if ($$0 == 22) {
/*  517 */       this.reducedDebugInfo = true;
/*  518 */     } else if ($$0 == 43) {
/*  519 */       addParticlesAroundSelf((ParticleOptions)ParticleTypes.CLOUD);
/*      */     } else {
/*  521 */       super.handleEntityEvent($$0);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void addParticlesAroundSelf(ParticleOptions $$0) {
/*  526 */     for (int $$1 = 0; $$1 < 5; $$1++) {
/*  527 */       double $$2 = this.random.nextGaussian() * 0.02D;
/*  528 */       double $$3 = this.random.nextGaussian() * 0.02D;
/*  529 */       double $$4 = this.random.nextGaussian() * 0.02D;
/*  530 */       level().addParticle($$0, getRandomX(1.0D), getRandomY() + 1.0D, getRandomZ(1.0D), $$2, $$3, $$4);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void closeContainer() {
/*  535 */     this.containerMenu = (AbstractContainerMenu)this.inventoryMenu;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void doCloseContainer() {}
/*      */ 
/*      */   
/*      */   public void rideTick() {
/*  543 */     if (!(level()).isClientSide && wantsToStopRiding() && isPassenger()) {
/*  544 */       stopRiding();
/*  545 */       setShiftKeyDown(false);
/*      */       return;
/*      */     } 
/*  548 */     super.rideTick();
/*  549 */     this.oBob = this.bob;
/*  550 */     this.bob = 0.0F;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void serverAiStep() {
/*  555 */     super.serverAiStep();
/*  556 */     updateSwingTime();
/*      */     
/*  558 */     this.yHeadRot = getYRot();
/*      */   }
/*      */   
/*      */   public void aiStep() {
/*      */     float $$1;
/*  563 */     if (this.jumpTriggerTime > 0) {
/*  564 */       this.jumpTriggerTime--;
/*      */     }
/*      */     
/*  567 */     if (level().getDifficulty() == Difficulty.PEACEFUL && level().getGameRules().getBoolean(GameRules.RULE_NATURAL_REGENERATION)) {
/*  568 */       if (getHealth() < getMaxHealth() && 
/*  569 */         this.tickCount % 20 == 0) {
/*  570 */         heal(1.0F);
/*      */       }
/*      */       
/*  573 */       if (this.foodData.needsFood() && 
/*  574 */         this.tickCount % 10 == 0) {
/*  575 */         this.foodData.setFoodLevel(this.foodData.getFoodLevel() + 1);
/*      */       }
/*      */     } 
/*      */     
/*  579 */     this.inventory.tick();
/*  580 */     this.oBob = this.bob;
/*      */     
/*  582 */     super.aiStep();
/*      */     
/*  584 */     setSpeed((float)getAttributeValue(Attributes.MOVEMENT_SPEED));
/*      */ 
/*      */     
/*  587 */     if (!onGround() || isDeadOrDying() || isSwimming()) {
/*  588 */       float $$0 = 0.0F;
/*      */     } else {
/*  590 */       $$1 = Math.min(0.1F, (float)getDeltaMovement().horizontalDistance());
/*      */     } 
/*      */     
/*  593 */     this.bob += ($$1 - this.bob) * 0.4F;
/*      */     
/*  595 */     if (getHealth() > 0.0F && !isSpectator()) {
/*      */       AABB $$3;
/*  597 */       if (isPassenger() && !getVehicle().isRemoved()) {
/*      */         
/*  599 */         AABB $$2 = getBoundingBox().minmax(getVehicle().getBoundingBox()).inflate(1.0D, 0.0D, 1.0D);
/*      */       } else {
/*  601 */         $$3 = getBoundingBox().inflate(1.0D, 0.5D, 1.0D);
/*      */       } 
/*      */       
/*  604 */       List<Entity> $$4 = level().getEntities((Entity)this, $$3);
/*  605 */       List<Entity> $$5 = Lists.newArrayList();
/*  606 */       for (Entity $$6 : $$4) {
/*  607 */         if ($$6.getType() == EntityType.EXPERIENCE_ORB) {
/*  608 */           $$5.add($$6); continue;
/*  609 */         }  if (!$$6.isRemoved()) {
/*  610 */           touch($$6);
/*      */         }
/*      */       } 
/*  613 */       if (!$$5.isEmpty()) {
/*  614 */         touch((Entity)Util.getRandom($$5, this.random));
/*      */       }
/*      */     } 
/*      */     
/*  618 */     playShoulderEntityAmbientSound(getShoulderEntityLeft());
/*  619 */     playShoulderEntityAmbientSound(getShoulderEntityRight());
/*  620 */     if ((!(level()).isClientSide && (this.fallDistance > 0.5F || isInWater())) || this.abilities.flying || isSleeping() || this.isInPowderSnow) {
/*  621 */       removeEntitiesOnShoulder();
/*      */     }
/*      */   }
/*      */   
/*      */   private void playShoulderEntityAmbientSound(@Nullable CompoundTag $$0) {
/*  626 */     if ($$0 != null && (!$$0.contains("Silent") || !$$0.getBoolean("Silent")) && (level()).random.nextInt(200) == 0) {
/*  627 */       String $$1 = $$0.getString("id");
/*  628 */       EntityType.byString($$1).filter($$0 -> ($$0 == EntityType.PARROT)).ifPresent($$0 -> {
/*      */             if (!Parrot.imitateNearbyMobs(level(), (Entity)this)) {
/*      */               level().playSound(null, getX(), getY(), getZ(), Parrot.getAmbient(level(), (level()).random), getSoundSource(), 1.0F, Parrot.getPitch((level()).random));
/*      */             }
/*      */           });
/*      */     } 
/*      */   }
/*      */   
/*      */   private void touch(Entity $$0) {
/*  637 */     $$0.playerTouch(this);
/*      */   }
/*      */   
/*      */   public int getScore() {
/*  641 */     return ((Integer)this.entityData.get(DATA_SCORE_ID)).intValue();
/*      */   }
/*      */   
/*      */   public void setScore(int $$0) {
/*  645 */     this.entityData.set(DATA_SCORE_ID, Integer.valueOf($$0));
/*      */   }
/*      */   
/*      */   public void increaseScore(int $$0) {
/*  649 */     int $$1 = getScore();
/*  650 */     this.entityData.set(DATA_SCORE_ID, Integer.valueOf($$1 + $$0));
/*      */   }
/*      */   
/*      */   public void startAutoSpinAttack(int $$0) {
/*  654 */     this.autoSpinAttackTicks = $$0;
/*  655 */     if (!(level()).isClientSide) {
/*  656 */       removeEntitiesOnShoulder();
/*  657 */       setLivingEntityFlag(4, true);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void die(DamageSource $$0) {
/*  663 */     super.die($$0);
/*  664 */     reapplyPosition();
/*      */     
/*  666 */     if (!isSpectator()) {
/*  667 */       dropAllDeathLoot($$0);
/*      */     }
/*      */     
/*  670 */     if ($$0 != null) {
/*  671 */       setDeltaMovement((
/*  672 */           -Mth.cos((getHurtDir() + getYRot()) * 0.017453292F) * 0.1F), 0.10000000149011612D, (
/*      */           
/*  674 */           -Mth.sin((getHurtDir() + getYRot()) * 0.017453292F) * 0.1F));
/*      */     } else {
/*      */       
/*  677 */       setDeltaMovement(0.0D, 0.1D, 0.0D);
/*      */     } 
/*      */     
/*  680 */     awardStat(Stats.DEATHS);
/*  681 */     resetStat(Stats.CUSTOM.get(Stats.TIME_SINCE_DEATH));
/*  682 */     resetStat(Stats.CUSTOM.get(Stats.TIME_SINCE_REST));
/*  683 */     clearFire();
/*  684 */     setSharedFlagOnFire(false);
/*  685 */     setLastDeathLocation(Optional.of(GlobalPos.of(level().dimension(), blockPosition())));
/*      */   }
/*      */ 
/*      */   
/*      */   protected void dropEquipment() {
/*  690 */     super.dropEquipment();
/*  691 */     if (!level().getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY)) {
/*  692 */       destroyVanishingCursedItems();
/*  693 */       this.inventory.dropAll();
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void destroyVanishingCursedItems() {
/*  698 */     for (int $$0 = 0; $$0 < this.inventory.getContainerSize(); $$0++) {
/*  699 */       ItemStack $$1 = this.inventory.getItem($$0);
/*  700 */       if (!$$1.isEmpty() && EnchantmentHelper.hasVanishingCurse($$1)) {
/*  701 */         this.inventory.removeItemNoUpdate($$0);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected SoundEvent getHurtSound(DamageSource $$0) {
/*  708 */     return $$0.type().effects().sound();
/*      */   }
/*      */ 
/*      */   
/*      */   protected SoundEvent getDeathSound() {
/*  713 */     return SoundEvents.PLAYER_DEATH;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public ItemEntity drop(ItemStack $$0, boolean $$1) {
/*  718 */     return drop($$0, false, $$1);
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public ItemEntity drop(ItemStack $$0, boolean $$1, boolean $$2) {
/*  723 */     if ($$0.isEmpty()) {
/*  724 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  728 */     if ((level()).isClientSide) {
/*  729 */       swing(InteractionHand.MAIN_HAND);
/*      */     }
/*      */     
/*  732 */     double $$3 = getEyeY() - 0.30000001192092896D;
/*  733 */     ItemEntity $$4 = new ItemEntity(level(), getX(), $$3, getZ(), $$0);
/*  734 */     $$4.setPickUpDelay(40);
/*      */     
/*  736 */     if ($$2) {
/*  737 */       $$4.setThrower((Entity)this);
/*      */     }
/*      */     
/*  740 */     if ($$1) {
/*  741 */       float $$5 = this.random.nextFloat() * 0.5F;
/*  742 */       float $$6 = this.random.nextFloat() * 6.2831855F;
/*  743 */       $$4.setDeltaMovement((
/*  744 */           -Mth.sin($$6) * $$5), 0.20000000298023224D, (
/*      */           
/*  746 */           Mth.cos($$6) * $$5));
/*      */     } else {
/*      */       
/*  749 */       float $$7 = 0.3F;
/*  750 */       float $$8 = Mth.sin(getXRot() * 0.017453292F);
/*  751 */       float $$9 = Mth.cos(getXRot() * 0.017453292F);
/*  752 */       float $$10 = Mth.sin(getYRot() * 0.017453292F);
/*  753 */       float $$11 = Mth.cos(getYRot() * 0.017453292F);
/*      */       
/*  755 */       float $$12 = this.random.nextFloat() * 6.2831855F;
/*  756 */       float $$13 = 0.02F * this.random.nextFloat();
/*      */       
/*  758 */       $$4.setDeltaMovement((-$$10 * $$9 * 0.3F) + 
/*  759 */           Math.cos($$12) * $$13, (-$$8 * 0.3F + 0.1F + (this.random
/*  760 */           .nextFloat() - this.random.nextFloat()) * 0.1F), ($$11 * $$9 * 0.3F) + 
/*  761 */           Math.sin($$12) * $$13);
/*      */     } 
/*      */     
/*  764 */     return $$4;
/*      */   }
/*      */   
/*      */   public float getDestroySpeed(BlockState $$0) {
/*  768 */     float $$1 = this.inventory.getDestroySpeed($$0);
/*  769 */     if ($$1 > 1.0F) {
/*  770 */       int $$2 = EnchantmentHelper.getBlockEfficiency(this);
/*  771 */       ItemStack $$3 = getMainHandItem();
/*      */       
/*  773 */       if ($$2 > 0 && !$$3.isEmpty()) {
/*  774 */         $$1 += ($$2 * $$2 + 1);
/*      */       }
/*      */     } 
/*      */     
/*  778 */     if (MobEffectUtil.hasDigSpeed(this)) {
/*  779 */       $$1 *= 1.0F + (MobEffectUtil.getDigSpeedAmplification(this) + 1) * 0.2F;
/*      */     }
/*  781 */     if (hasEffect(MobEffects.DIG_SLOWDOWN)) {
/*      */       float $$4, $$5, $$6, $$7;
/*      */ 
/*      */       
/*  785 */       switch (getEffect(MobEffects.DIG_SLOWDOWN).getAmplifier()) {
/*      */         case 0:
/*  787 */           $$4 = 0.3F;
/*      */           break;
/*      */         case 1:
/*  790 */           $$5 = 0.09F;
/*      */           break;
/*      */         case 2:
/*  793 */           $$6 = 0.0027F;
/*      */           break;
/*      */         
/*      */         default:
/*  797 */           $$7 = 8.1E-4F;
/*      */           break;
/*      */       } 
/*  800 */       $$1 *= $$7;
/*      */     } 
/*      */     
/*  803 */     if (isEyeInFluid(FluidTags.WATER) && !EnchantmentHelper.hasAquaAffinity(this)) {
/*  804 */       $$1 /= 5.0F;
/*      */     }
/*  806 */     if (!onGround()) {
/*  807 */       $$1 /= 5.0F;
/*      */     }
/*      */     
/*  810 */     return $$1;
/*      */   }
/*      */   
/*      */   public boolean hasCorrectToolForDrops(BlockState $$0) {
/*  814 */     return (!$$0.requiresCorrectToolForDrops() || this.inventory.getSelected().isCorrectToolForDrops($$0));
/*      */   }
/*      */ 
/*      */   
/*      */   public void readAdditionalSaveData(CompoundTag $$0) {
/*  819 */     super.readAdditionalSaveData($$0);
/*      */     
/*  821 */     setUUID(this.gameProfile.getId());
/*  822 */     ListTag $$1 = $$0.getList("Inventory", 10);
/*  823 */     this.inventory.load($$1);
/*  824 */     this.inventory.selected = $$0.getInt("SelectedItemSlot");
/*  825 */     this.sleepCounter = $$0.getShort("SleepTimer");
/*      */     
/*  827 */     this.experienceProgress = $$0.getFloat("XpP");
/*  828 */     this.experienceLevel = $$0.getInt("XpLevel");
/*  829 */     this.totalExperience = $$0.getInt("XpTotal");
/*  830 */     this.enchantmentSeed = $$0.getInt("XpSeed");
/*  831 */     if (this.enchantmentSeed == 0) {
/*  832 */       this.enchantmentSeed = this.random.nextInt();
/*      */     }
/*  834 */     setScore($$0.getInt("Score"));
/*      */     
/*  836 */     this.foodData.readAdditionalSaveData($$0);
/*      */     
/*  838 */     this.abilities.loadSaveData($$0);
/*      */     
/*  840 */     getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(this.abilities.getWalkingSpeed());
/*      */     
/*  842 */     if ($$0.contains("EnderItems", 9)) {
/*  843 */       this.enderChestInventory.fromTag($$0.getList("EnderItems", 10));
/*      */     }
/*      */     
/*  846 */     if ($$0.contains("ShoulderEntityLeft", 10)) {
/*  847 */       setShoulderEntityLeft($$0.getCompound("ShoulderEntityLeft"));
/*      */     }
/*  849 */     if ($$0.contains("ShoulderEntityRight", 10)) {
/*  850 */       setShoulderEntityRight($$0.getCompound("ShoulderEntityRight"));
/*      */     }
/*      */     
/*  853 */     if ($$0.contains("LastDeathLocation", 10)) {
/*  854 */       Objects.requireNonNull(LOGGER); setLastDeathLocation(GlobalPos.CODEC.parse((DynamicOps)NbtOps.INSTANCE, $$0.get("LastDeathLocation")).resultOrPartial(LOGGER::error));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void addAdditionalSaveData(CompoundTag $$0) {
/*  860 */     super.addAdditionalSaveData($$0);
/*  861 */     NbtUtils.addCurrentDataVersion($$0);
/*  862 */     $$0.put("Inventory", (Tag)this.inventory.save(new ListTag()));
/*  863 */     $$0.putInt("SelectedItemSlot", this.inventory.selected);
/*  864 */     $$0.putShort("SleepTimer", (short)this.sleepCounter);
/*  865 */     $$0.putFloat("XpP", this.experienceProgress);
/*  866 */     $$0.putInt("XpLevel", this.experienceLevel);
/*  867 */     $$0.putInt("XpTotal", this.totalExperience);
/*  868 */     $$0.putInt("XpSeed", this.enchantmentSeed);
/*  869 */     $$0.putInt("Score", getScore());
/*      */     
/*  871 */     this.foodData.addAdditionalSaveData($$0);
/*      */     
/*  873 */     this.abilities.addSaveData($$0);
/*  874 */     $$0.put("EnderItems", (Tag)this.enderChestInventory.createTag());
/*      */     
/*  876 */     if (!getShoulderEntityLeft().isEmpty()) {
/*  877 */       $$0.put("ShoulderEntityLeft", (Tag)getShoulderEntityLeft());
/*      */     }
/*  879 */     if (!getShoulderEntityRight().isEmpty()) {
/*  880 */       $$0.put("ShoulderEntityRight", (Tag)getShoulderEntityRight());
/*      */     }
/*      */     
/*  883 */     getLastDeathLocation().flatMap($$0 -> { Objects.requireNonNull(LOGGER); return GlobalPos.CODEC.encodeStart((DynamicOps)NbtOps.INSTANCE, $$0).resultOrPartial(LOGGER::error); }).ifPresent($$1 -> $$0.put("LastDeathLocation", $$1));
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isInvulnerableTo(DamageSource $$0) {
/*  888 */     if (super.isInvulnerableTo($$0)) {
/*  889 */       return true;
/*      */     }
/*      */     
/*  892 */     if ($$0.is(DamageTypeTags.IS_DROWNING))
/*  893 */       return !level().getGameRules().getBoolean(GameRules.RULE_DROWNING_DAMAGE); 
/*  894 */     if ($$0.is(DamageTypeTags.IS_FALL))
/*  895 */       return !level().getGameRules().getBoolean(GameRules.RULE_FALL_DAMAGE); 
/*  896 */     if ($$0.is(DamageTypeTags.IS_FIRE))
/*  897 */       return !level().getGameRules().getBoolean(GameRules.RULE_FIRE_DAMAGE); 
/*  898 */     if ($$0.is(DamageTypeTags.IS_FREEZING)) {
/*  899 */       return !level().getGameRules().getBoolean(GameRules.RULE_FREEZE_DAMAGE);
/*      */     }
/*  901 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hurt(DamageSource $$0, float $$1) {
/*  906 */     if (isInvulnerableTo($$0)) {
/*  907 */       return false;
/*      */     }
/*  909 */     if (this.abilities.invulnerable && !$$0.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
/*  910 */       return false;
/*      */     }
/*      */     
/*  913 */     this.noActionTime = 0;
/*  914 */     if (isDeadOrDying()) {
/*  915 */       return false;
/*      */     }
/*      */     
/*  918 */     if (!(level()).isClientSide) {
/*  919 */       removeEntitiesOnShoulder();
/*      */     }
/*      */     
/*  922 */     if ($$0.scalesWithDifficulty()) {
/*  923 */       if (level().getDifficulty() == Difficulty.PEACEFUL) {
/*  924 */         $$1 = 0.0F;
/*      */       }
/*  926 */       if (level().getDifficulty() == Difficulty.EASY) {
/*  927 */         $$1 = Math.min($$1 / 2.0F + 1.0F, $$1);
/*      */       }
/*  929 */       if (level().getDifficulty() == Difficulty.HARD) {
/*  930 */         $$1 = $$1 * 3.0F / 2.0F;
/*      */       }
/*      */     } 
/*      */     
/*  934 */     if ($$1 == 0.0F) {
/*  935 */       return false;
/*      */     }
/*      */     
/*  938 */     return super.hurt($$0, $$1);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void blockUsingShield(LivingEntity $$0) {
/*  943 */     super.blockUsingShield($$0);
/*      */     
/*  945 */     if ($$0.canDisableShield()) {
/*  946 */       disableShield(true);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canBeSeenAsEnemy() {
/*  952 */     return (!(getAbilities()).invulnerable && super.canBeSeenAsEnemy());
/*      */   }
/*      */   
/*      */   public boolean canHarmPlayer(Player $$0) {
/*  956 */     PlayerTeam playerTeam1 = getTeam();
/*  957 */     PlayerTeam playerTeam2 = $$0.getTeam();
/*      */     
/*  959 */     if (playerTeam1 == null) {
/*  960 */       return true;
/*      */     }
/*  962 */     if (!playerTeam1.isAlliedTo((Team)playerTeam2)) {
/*  963 */       return true;
/*      */     }
/*  965 */     return playerTeam1.isAllowFriendlyFire();
/*      */   }
/*      */ 
/*      */   
/*      */   protected void hurtArmor(DamageSource $$0, float $$1) {
/*  970 */     this.inventory.hurtArmor($$0, $$1, Inventory.ALL_ARMOR_SLOTS);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void hurtHelmet(DamageSource $$0, float $$1) {
/*  975 */     this.inventory.hurtArmor($$0, $$1, Inventory.HELMET_SLOT_ONLY);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void hurtCurrentlyUsedShield(float $$0) {
/*  980 */     if (!this.useItem.is(Items.SHIELD)) {
/*      */       return;
/*      */     }
/*  983 */     if (!(level()).isClientSide) {
/*  984 */       awardStat(Stats.ITEM_USED.get(this.useItem.getItem()));
/*      */     }
/*  986 */     if ($$0 >= 3.0F) {
/*  987 */       int $$1 = 1 + Mth.floor($$0);
/*  988 */       InteractionHand $$2 = getUsedItemHand();
/*  989 */       this.useItem.hurtAndBreak($$1, this, $$1 -> $$1.broadcastBreakEvent($$0));
/*  990 */       if (this.useItem.isEmpty()) {
/*      */         
/*  992 */         if ($$2 == InteractionHand.MAIN_HAND) {
/*  993 */           setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
/*      */         } else {
/*  995 */           setItemSlot(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
/*      */         } 
/*  997 */         this.useItem = ItemStack.EMPTY;
/*  998 */         playSound(SoundEvents.SHIELD_BREAK, 0.8F, 0.8F + (level()).random.nextFloat() * 0.4F);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void actuallyHurt(DamageSource $$0, float $$1) {
/* 1005 */     if (isInvulnerableTo($$0)) {
/*      */       return;
/*      */     }
/* 1008 */     $$1 = getDamageAfterArmorAbsorb($$0, $$1);
/* 1009 */     $$1 = getDamageAfterMagicAbsorb($$0, $$1);
/*      */     
/* 1011 */     float $$2 = $$1;
/* 1012 */     $$1 = Math.max($$1 - getAbsorptionAmount(), 0.0F);
/* 1013 */     setAbsorptionAmount(getAbsorptionAmount() - $$2 - $$1);
/*      */     
/* 1015 */     float $$3 = $$2 - $$1;
/* 1016 */     if ($$3 > 0.0F && $$3 < 3.4028235E37F) {
/* 1017 */       awardStat(Stats.DAMAGE_ABSORBED, Math.round($$3 * 10.0F));
/*      */     }
/*      */     
/* 1020 */     if ($$1 == 0.0F) {
/*      */       return;
/*      */     }
/*      */     
/* 1024 */     causeFoodExhaustion($$0.getFoodExhaustion());
/* 1025 */     getCombatTracker().recordDamage($$0, $$1);
/* 1026 */     setHealth(getHealth() - $$1);
/* 1027 */     if ($$1 < 3.4028235E37F) {
/* 1028 */       awardStat(Stats.DAMAGE_TAKEN, Math.round($$1 * 10.0F));
/*      */     }
/* 1030 */     gameEvent(GameEvent.ENTITY_DAMAGE);
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean onSoulSpeedBlock() {
/* 1035 */     return (!this.abilities.flying && super.onSoulSpeedBlock());
/*      */   }
/*      */   
/*      */   public boolean isTextFilteringEnabled() {
/* 1039 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public void openTextEdit(SignBlockEntity $$0, boolean $$1) {}
/*      */ 
/*      */   
/*      */   public void openMinecartCommandBlock(BaseCommandBlock $$0) {}
/*      */ 
/*      */   
/*      */   public void openCommandBlock(CommandBlockEntity $$0) {}
/*      */ 
/*      */   
/*      */   public void openStructureBlock(StructureBlockEntity $$0) {}
/*      */ 
/*      */   
/*      */   public void openJigsawBlock(JigsawBlockEntity $$0) {}
/*      */ 
/*      */   
/*      */   public void openHorseInventory(AbstractHorse $$0, Container $$1) {}
/*      */   
/*      */   public OptionalInt openMenu(@Nullable MenuProvider $$0) {
/* 1061 */     return OptionalInt.empty();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendMerchantOffers(int $$0, MerchantOffers $$1, int $$2, int $$3, boolean $$4, boolean $$5) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void openItemGui(ItemStack $$0, InteractionHand $$1) {}
/*      */ 
/*      */ 
/*      */   
/*      */   public InteractionResult interactOn(Entity $$0, InteractionHand $$1) {
/* 1077 */     if (isSpectator()) {
/* 1078 */       if ($$0 instanceof MenuProvider) {
/* 1079 */         openMenu((MenuProvider)$$0);
/*      */       }
/* 1081 */       return InteractionResult.PASS;
/*      */     } 
/*      */     
/* 1084 */     ItemStack $$2 = getItemInHand($$1);
/*      */     
/* 1086 */     ItemStack $$3 = $$2.copy();
/* 1087 */     InteractionResult $$4 = $$0.interact(this, $$1);
/* 1088 */     if ($$4.consumesAction()) {
/* 1089 */       if (this.abilities.instabuild && $$2 == getItemInHand($$1) && $$2.getCount() < $$3.getCount()) {
/* 1090 */         $$2.setCount($$3.getCount());
/*      */       }
/* 1092 */       return $$4;
/*      */     } 
/*      */     
/* 1095 */     if (!$$2.isEmpty() && $$0 instanceof LivingEntity) {
/*      */       
/* 1097 */       if (this.abilities.instabuild) {
/* 1098 */         $$2 = $$3;
/*      */       }
/* 1100 */       InteractionResult $$5 = $$2.interactLivingEntity(this, (LivingEntity)$$0, $$1);
/* 1101 */       if ($$5.consumesAction()) {
/* 1102 */         level().gameEvent(GameEvent.ENTITY_INTERACT, $$0.position(), GameEvent.Context.of((Entity)this));
/*      */         
/* 1104 */         if ($$2.isEmpty() && !this.abilities.instabuild) {
/* 1105 */           setItemInHand($$1, ItemStack.EMPTY);
/*      */         }
/* 1107 */         return $$5;
/*      */       } 
/*      */     } 
/* 1110 */     return InteractionResult.PASS;
/*      */   }
/*      */ 
/*      */   
/*      */   protected float ridingOffset(Entity $$0) {
/* 1115 */     return -0.6F;
/*      */   }
/*      */ 
/*      */   
/*      */   public void removeVehicle() {
/* 1120 */     super.removeVehicle();
/*      */     
/* 1122 */     this.boardingCooldown = 0;
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean isImmobile() {
/* 1127 */     return (super.isImmobile() || isSleeping());
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isAffectedByFluids() {
/* 1132 */     return !this.abilities.flying;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Vec3 maybeBackOffFromEdge(Vec3 $$0, MoverType $$1) {
/* 1141 */     if (!this.abilities.flying && $$0.y <= 0.0D && ($$1 == MoverType.SELF || $$1 == MoverType.PLAYER) && isStayingOnGroundSurface() && isAboveGround()) {
/* 1142 */       double $$2 = $$0.x;
/* 1143 */       double $$3 = $$0.z;
/* 1144 */       double $$4 = 0.05D;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1149 */       while ($$2 != 0.0D && level().noCollision((Entity)this, getBoundingBox().move($$2, -maxUpStep(), 0.0D))) {
/* 1150 */         if ($$2 < 0.05D && $$2 >= -0.05D) {
/* 1151 */           $$2 = 0.0D; continue;
/* 1152 */         }  if ($$2 > 0.0D) {
/* 1153 */           $$2 -= 0.05D; continue;
/*      */         } 
/* 1155 */         $$2 += 0.05D;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1160 */       while ($$3 != 0.0D && level().noCollision((Entity)this, getBoundingBox().move(0.0D, -maxUpStep(), $$3))) {
/* 1161 */         if ($$3 < 0.05D && $$3 >= -0.05D) {
/* 1162 */           $$3 = 0.0D; continue;
/* 1163 */         }  if ($$3 > 0.0D) {
/* 1164 */           $$3 -= 0.05D; continue;
/*      */         } 
/* 1166 */         $$3 += 0.05D;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1171 */       while ($$2 != 0.0D && $$3 != 0.0D && level().noCollision((Entity)this, getBoundingBox().move($$2, -maxUpStep(), $$3))) {
/* 1172 */         if ($$2 < 0.05D && $$2 >= -0.05D) {
/* 1173 */           $$2 = 0.0D;
/* 1174 */         } else if ($$2 > 0.0D) {
/* 1175 */           $$2 -= 0.05D;
/*      */         } else {
/* 1177 */           $$2 += 0.05D;
/*      */         } 
/*      */         
/* 1180 */         if ($$3 < 0.05D && $$3 >= -0.05D) {
/* 1181 */           $$3 = 0.0D; continue;
/* 1182 */         }  if ($$3 > 0.0D) {
/* 1183 */           $$3 -= 0.05D; continue;
/*      */         } 
/* 1185 */         $$3 += 0.05D;
/*      */       } 
/*      */       
/* 1188 */       $$0 = new Vec3($$2, $$0.y, $$3);
/*      */     } 
/* 1190 */     return $$0;
/*      */   }
/*      */   
/*      */   private boolean isAboveGround() {
/* 1194 */     return (onGround() || (this.fallDistance < maxUpStep() && !level().noCollision((Entity)this, getBoundingBox().move(0.0D, (this.fallDistance - maxUpStep()), 0.0D))));
/*      */   }
/*      */   public void attack(Entity $$0) {
/*      */     float $$3;
/* 1198 */     if (!$$0.isAttackable()) {
/*      */       return;
/*      */     }
/* 1201 */     if ($$0.skipAttackInteraction((Entity)this)) {
/*      */       return;
/*      */     }
/*      */     
/* 1205 */     float $$1 = (float)getAttributeValue(Attributes.ATTACK_DAMAGE);
/*      */ 
/*      */     
/* 1208 */     if ($$0 instanceof LivingEntity) {
/* 1209 */       float $$2 = EnchantmentHelper.getDamageBonus(getMainHandItem(), ((LivingEntity)$$0).getMobType());
/*      */     } else {
/* 1211 */       $$3 = EnchantmentHelper.getDamageBonus(getMainHandItem(), MobType.UNDEFINED);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1216 */     float $$4 = getAttackStrengthScale(0.5F);
/* 1217 */     $$1 *= 0.2F + $$4 * $$4 * 0.8F;
/* 1218 */     $$3 *= $$4;
/*      */     
/* 1220 */     resetAttackStrengthTicker();
/*      */     
/* 1222 */     if ($$1 > 0.0F || $$3 > 0.0F) {
/* 1223 */       boolean $$5 = ($$4 > 0.9F);
/*      */       
/* 1225 */       boolean $$6 = false;
/* 1226 */       int $$7 = 0;
/* 1227 */       $$7 += EnchantmentHelper.getKnockbackBonus(this);
/*      */       
/* 1229 */       if (isSprinting() && $$5) {
/* 1230 */         level().playSound(null, getX(), getY(), getZ(), SoundEvents.PLAYER_ATTACK_KNOCKBACK, getSoundSource(), 1.0F, 1.0F);
/* 1231 */         $$7++;
/* 1232 */         $$6 = true;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1237 */       boolean $$8 = ($$5 && this.fallDistance > 0.0F && !onGround() && !onClimbable() && !isInWater() && !hasEffect(MobEffects.BLINDNESS) && !isPassenger() && $$0 instanceof LivingEntity);
/*      */       
/* 1239 */       $$8 = ($$8 && !isSprinting());
/* 1240 */       if ($$8) {
/* 1241 */         $$1 *= 1.5F;
/*      */       }
/* 1243 */       $$1 += $$3;
/*      */       
/* 1245 */       boolean $$9 = false;
/*      */ 
/*      */       
/* 1248 */       double $$10 = (this.walkDist - this.walkDistO);
/* 1249 */       if ($$5 && !$$8 && !$$6 && onGround() && $$10 < getSpeed()) {
/*      */         
/* 1251 */         ItemStack $$11 = getItemInHand(InteractionHand.MAIN_HAND);
/* 1252 */         if ($$11.getItem() instanceof net.minecraft.world.item.SwordItem) {
/* 1253 */           $$9 = true;
/*      */         }
/*      */       } 
/*      */       
/* 1257 */       float $$12 = 0.0F;
/* 1258 */       boolean $$13 = false;
/* 1259 */       int $$14 = EnchantmentHelper.getFireAspect(this);
/*      */       
/* 1261 */       if ($$0 instanceof LivingEntity) {
/* 1262 */         $$12 = ((LivingEntity)$$0).getHealth();
/*      */ 
/*      */         
/* 1265 */         if ($$14 > 0 && !$$0.isOnFire()) {
/* 1266 */           $$13 = true;
/* 1267 */           $$0.setSecondsOnFire(1);
/*      */         } 
/*      */       } 
/*      */       
/* 1271 */       Vec3 $$15 = $$0.getDeltaMovement();
/*      */       
/* 1273 */       boolean $$16 = $$0.hurt(damageSources().playerAttack(this), $$1);
/* 1274 */       if ($$16) {
/* 1275 */         EnderDragon enderDragon; if ($$7 > 0) {
/* 1276 */           if ($$0 instanceof LivingEntity) {
/* 1277 */             ((LivingEntity)$$0).knockback(($$7 * 0.5F), Mth.sin(getYRot() * 0.017453292F), -Mth.cos(getYRot() * 0.017453292F));
/*      */           } else {
/* 1279 */             $$0.push((-Mth.sin(getYRot() * 0.017453292F) * $$7 * 0.5F), 0.1D, (Mth.cos(getYRot() * 0.017453292F) * $$7 * 0.5F));
/*      */           } 
/* 1281 */           setDeltaMovement(getDeltaMovement().multiply(0.6D, 1.0D, 0.6D));
/* 1282 */           setSprinting(false);
/*      */         } 
/* 1284 */         if ($$9) {
/* 1285 */           float $$17 = 1.0F + EnchantmentHelper.getSweepingDamageRatio(this) * $$1;
/* 1286 */           List<LivingEntity> $$18 = level().getEntitiesOfClass(LivingEntity.class, $$0.getBoundingBox().inflate(1.0D, 0.25D, 1.0D));
/* 1287 */           for (LivingEntity $$19 : $$18) {
/* 1288 */             if ($$19 == this || $$19 == $$0 || isAlliedTo((Entity)$$19)) {
/*      */               continue;
/*      */             }
/*      */             
/* 1292 */             if ($$19 instanceof ArmorStand && ((ArmorStand)$$19).isMarker()) {
/*      */               continue;
/*      */             }
/*      */             
/* 1296 */             if (distanceToSqr((Entity)$$19) < 9.0D) {
/* 1297 */               $$19.knockback(0.4000000059604645D, Mth.sin(getYRot() * 0.017453292F), -Mth.cos(getYRot() * 0.017453292F));
/* 1298 */               $$19.hurt(damageSources().playerAttack(this), $$17);
/*      */             } 
/*      */           } 
/* 1301 */           level().playSound(null, getX(), getY(), getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, getSoundSource(), 1.0F, 1.0F);
/* 1302 */           sweepAttack();
/*      */         } 
/*      */         
/* 1305 */         if ($$0 instanceof ServerPlayer && $$0.hurtMarked) {
/* 1306 */           ((ServerPlayer)$$0).connection.send((Packet)new ClientboundSetEntityMotionPacket($$0));
/* 1307 */           $$0.hurtMarked = false;
/* 1308 */           $$0.setDeltaMovement($$15);
/*      */         } 
/*      */         
/* 1311 */         if ($$8) {
/* 1312 */           level().playSound(null, getX(), getY(), getZ(), SoundEvents.PLAYER_ATTACK_CRIT, getSoundSource(), 1.0F, 1.0F);
/* 1313 */           crit($$0);
/*      */         } 
/*      */         
/* 1316 */         if (!$$8 && !$$9) {
/* 1317 */           if ($$5) {
/* 1318 */             level().playSound(null, getX(), getY(), getZ(), SoundEvents.PLAYER_ATTACK_STRONG, getSoundSource(), 1.0F, 1.0F);
/*      */           } else {
/* 1320 */             level().playSound(null, getX(), getY(), getZ(), SoundEvents.PLAYER_ATTACK_WEAK, getSoundSource(), 1.0F, 1.0F);
/*      */           } 
/*      */         }
/*      */         
/* 1324 */         if ($$3 > 0.0F) {
/* 1325 */           magicCrit($$0);
/*      */         }
/*      */         
/* 1328 */         setLastHurtMob($$0);
/*      */         
/* 1330 */         if ($$0 instanceof LivingEntity) {
/* 1331 */           EnchantmentHelper.doPostHurtEffects((LivingEntity)$$0, (Entity)this);
/*      */         }
/* 1333 */         EnchantmentHelper.doPostDamageEffects(this, $$0);
/*      */         
/* 1335 */         ItemStack $$20 = getMainHandItem();
/* 1336 */         Entity $$21 = $$0;
/* 1337 */         if ($$0 instanceof EnderDragonPart) {
/* 1338 */           enderDragon = ((EnderDragonPart)$$0).parentMob;
/*      */         }
/* 1340 */         if (!(level()).isClientSide && !$$20.isEmpty() && enderDragon instanceof LivingEntity) {
/* 1341 */           $$20.hurtEnemy((LivingEntity)enderDragon, this);
/*      */ 
/*      */           
/* 1344 */           if ($$20.isEmpty()) {
/* 1345 */             setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
/*      */           }
/*      */         } 
/* 1348 */         if ($$0 instanceof LivingEntity) {
/* 1349 */           float $$22 = $$12 - ((LivingEntity)$$0).getHealth();
/*      */           
/* 1351 */           awardStat(Stats.DAMAGE_DEALT, Math.round($$22 * 10.0F));
/*      */           
/* 1353 */           if ($$14 > 0) {
/* 1354 */             $$0.setSecondsOnFire($$14 * 4);
/*      */           }
/*      */ 
/*      */           
/* 1358 */           if (level() instanceof ServerLevel && $$22 > 2.0F) {
/* 1359 */             int $$23 = (int)($$22 * 0.5D);
/* 1360 */             ((ServerLevel)level()).sendParticles((ParticleOptions)ParticleTypes.DAMAGE_INDICATOR, $$0.getX(), $$0.getY(0.5D), $$0.getZ(), $$23, 0.1D, 0.0D, 0.1D, 0.2D);
/*      */           } 
/*      */         } 
/*      */         
/* 1364 */         causeFoodExhaustion(0.1F);
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */         
/* 1370 */         level().playSound(null, getX(), getY(), getZ(), SoundEvents.PLAYER_ATTACK_NODAMAGE, getSoundSource(), 1.0F, 1.0F);
/*      */         
/* 1372 */         if ($$13) {
/* 1373 */           $$0.clearFire();
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void doAutoAttackOnTouch(LivingEntity $$0) {
/* 1381 */     attack((Entity)$$0);
/*      */   }
/*      */   
/*      */   public void disableShield(boolean $$0) {
/* 1385 */     float $$1 = 0.25F + EnchantmentHelper.getBlockEfficiency(this) * 0.05F;
/*      */     
/* 1387 */     if ($$0) {
/* 1388 */       $$1 += 0.75F;
/*      */     }
/*      */     
/* 1391 */     if (this.random.nextFloat() < $$1) {
/* 1392 */       getCooldowns().addCooldown(Items.SHIELD, 100);
/* 1393 */       stopUsingItem();
/* 1394 */       level().broadcastEntityEvent((Entity)this, (byte)30);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void crit(Entity $$0) {}
/*      */ 
/*      */   
/*      */   public void magicCrit(Entity $$0) {}
/*      */ 
/*      */   
/*      */   public void sweepAttack() {
/* 1406 */     double $$0 = -Mth.sin(getYRot() * 0.017453292F);
/* 1407 */     double $$1 = Mth.cos(getYRot() * 0.017453292F);
/* 1408 */     if (level() instanceof ServerLevel) {
/* 1409 */       ((ServerLevel)level()).sendParticles((ParticleOptions)ParticleTypes.SWEEP_ATTACK, getX() + $$0, getY(0.5D), getZ() + $$1, 0, $$0, 0.0D, $$1, 0.0D);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void respawn() {}
/*      */ 
/*      */   
/*      */   public void remove(Entity.RemovalReason $$0) {
/* 1418 */     super.remove($$0);
/*      */     
/* 1420 */     this.inventoryMenu.removed(this);
/* 1421 */     if (this.containerMenu != null && hasContainerOpen()) {
/* 1422 */       doCloseContainer();
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean isLocalPlayer() {
/* 1427 */     return false;
/*      */   }
/*      */   
/*      */   public GameProfile getGameProfile() {
/* 1431 */     return this.gameProfile;
/*      */   }
/*      */   
/*      */   public Inventory getInventory() {
/* 1435 */     return this.inventory;
/*      */   }
/*      */   
/*      */   public Abilities getAbilities() {
/* 1439 */     return this.abilities;
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateTutorialInventoryAction(ItemStack $$0, ItemStack $$1, ClickAction $$2) {}
/*      */ 
/*      */   
/*      */   public boolean hasContainerOpen() {
/* 1447 */     return (this.containerMenu != this.inventoryMenu);
/*      */   }
/*      */   
/*      */   public enum BedSleepingProblem {
/* 1451 */     NOT_POSSIBLE_HERE,
/* 1452 */     NOT_POSSIBLE_NOW((String)Component.translatable("block.minecraft.bed.no_sleep")),
/* 1453 */     TOO_FAR_AWAY((String)Component.translatable("block.minecraft.bed.too_far_away")),
/* 1454 */     OBSTRUCTED((String)Component.translatable("block.minecraft.bed.obstructed")),
/* 1455 */     OTHER_PROBLEM,
/* 1456 */     NOT_SAFE((String)Component.translatable("block.minecraft.bed.not_safe"));
/*      */     
/*      */     @Nullable
/*      */     private final Component message;
/*      */     
/*      */     BedSleepingProblem() {
/* 1462 */       this.message = null;
/*      */     }
/*      */     
/*      */     BedSleepingProblem(Component $$0) {
/* 1466 */       this.message = $$0;
/*      */     }
/*      */     
/*      */     @Nullable
/*      */     public Component getMessage() {
/* 1471 */       return this.message;
/*      */     }
/*      */   }
/*      */   
/*      */   public Either<BedSleepingProblem, Unit> startSleepInBed(BlockPos $$0) {
/* 1476 */     startSleeping($$0);
/*      */     
/* 1478 */     this.sleepCounter = 0;
/*      */     
/* 1480 */     return Either.right(Unit.INSTANCE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void stopSleepInBed(boolean $$0, boolean $$1) {
/* 1491 */     super.stopSleeping();
/*      */     
/* 1493 */     if (level() instanceof ServerLevel && $$1) {
/* 1494 */       ((ServerLevel)level()).updateSleepingPlayerList();
/*      */     }
/*      */     
/* 1497 */     this.sleepCounter = $$0 ? 0 : 100;
/*      */   }
/*      */ 
/*      */   
/*      */   public void stopSleeping() {
/* 1502 */     stopSleepInBed(true, true);
/*      */   }
/*      */   
/*      */   public static Optional<Vec3> findRespawnPositionAndUseSpawnBlock(ServerLevel $$0, BlockPos $$1, float $$2, boolean $$3, boolean $$4) {
/* 1506 */     BlockState $$5 = $$0.getBlockState($$1);
/* 1507 */     Block $$6 = $$5.getBlock();
/* 1508 */     if ($$6 instanceof RespawnAnchorBlock && ($$3 || ((Integer)$$5.getValue((Property)RespawnAnchorBlock.CHARGE)).intValue() > 0) && RespawnAnchorBlock.canSetSpawn((Level)$$0)) {
/* 1509 */       Optional<Vec3> $$7 = RespawnAnchorBlock.findStandUpPosition(EntityType.PLAYER, (CollisionGetter)$$0, $$1);
/* 1510 */       if (!$$3 && !$$4 && $$7.isPresent()) {
/* 1511 */         $$0.setBlock($$1, (BlockState)$$5.setValue((Property)RespawnAnchorBlock.CHARGE, Integer.valueOf(((Integer)$$5.getValue((Property)RespawnAnchorBlock.CHARGE)).intValue() - 1)), 3);
/*      */       }
/* 1513 */       return $$7;
/* 1514 */     }  if ($$6 instanceof BedBlock && BedBlock.canSetSpawn((Level)$$0))
/*      */     {
/* 1516 */       return BedBlock.findStandUpPosition(EntityType.PLAYER, (CollisionGetter)$$0, $$1, (Direction)$$5.getValue((Property)BedBlock.FACING), $$2);
/*      */     }
/*      */     
/* 1519 */     if (!$$3) {
/* 1520 */       return Optional.empty();
/*      */     }
/*      */     
/* 1523 */     boolean $$8 = $$6.isPossibleToRespawnInThis($$5);
/* 1524 */     BlockState $$9 = $$0.getBlockState($$1.above());
/* 1525 */     boolean $$10 = $$9.getBlock().isPossibleToRespawnInThis($$9);
/*      */     
/* 1527 */     if ($$8 && $$10) {
/* 1528 */       return Optional.of(new Vec3($$1.getX() + 0.5D, $$1.getY() + 0.1D, $$1.getZ() + 0.5D));
/*      */     }
/*      */     
/* 1531 */     return Optional.empty();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isSleepingLongEnough() {
/* 1536 */     return (isSleeping() && this.sleepCounter >= 100);
/*      */   }
/*      */   
/*      */   public int getSleepTimer() {
/* 1540 */     return this.sleepCounter;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void displayClientMessage(Component $$0, boolean $$1) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void awardStat(ResourceLocation $$0) {
/* 1555 */     awardStat(Stats.CUSTOM.get($$0));
/*      */   }
/*      */   
/*      */   public void awardStat(ResourceLocation $$0, int $$1) {
/* 1559 */     awardStat(Stats.CUSTOM.get($$0), $$1);
/*      */   }
/*      */   
/*      */   public void awardStat(Stat<?> $$0) {
/* 1563 */     awardStat($$0, 1);
/*      */   }
/*      */ 
/*      */   
/*      */   public void awardStat(Stat<?> $$0, int $$1) {}
/*      */ 
/*      */   
/*      */   public void resetStat(Stat<?> $$0) {}
/*      */   
/*      */   public int awardRecipes(Collection<RecipeHolder<?>> $$0) {
/* 1573 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public void triggerRecipeCrafted(RecipeHolder<?> $$0, List<ItemStack> $$1) {}
/*      */ 
/*      */   
/*      */   public void awardRecipesByKey(List<ResourceLocation> $$0) {}
/*      */   
/*      */   public int resetRecipes(Collection<RecipeHolder<?>> $$0) {
/* 1583 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public void jumpFromGround() {
/* 1588 */     super.jumpFromGround();
/*      */     
/* 1590 */     awardStat(Stats.JUMP);
/* 1591 */     if (isSprinting()) {
/* 1592 */       causeFoodExhaustion(0.2F);
/*      */     } else {
/* 1594 */       causeFoodExhaustion(0.05F);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void travel(Vec3 $$0) {
/* 1600 */     if (isSwimming() && !isPassenger()) {
/* 1601 */       double $$1 = (getLookAngle()).y;
/* 1602 */       double $$2 = ($$1 < -0.2D) ? 0.085D : 0.06D;
/*      */       
/* 1604 */       if ($$1 <= 0.0D || this.jumping || !level().getBlockState(BlockPos.containing(getX(), getY() + 1.0D - 0.1D, getZ())).getFluidState().isEmpty()) {
/* 1605 */         Vec3 $$3 = getDeltaMovement();
/* 1606 */         setDeltaMovement($$3.add(0.0D, ($$1 - $$3.y) * $$2, 0.0D));
/*      */       } 
/*      */     } 
/*      */     
/* 1610 */     if (this.abilities.flying && !isPassenger()) {
/* 1611 */       double $$4 = (getDeltaMovement()).y;
/* 1612 */       super.travel($$0);
/* 1613 */       Vec3 $$5 = getDeltaMovement();
/* 1614 */       setDeltaMovement($$5.x, $$4 * 0.6D, $$5.z);
/*      */       
/* 1616 */       resetFallDistance();
/*      */       
/* 1618 */       setSharedFlag(7, false);
/*      */     } else {
/* 1620 */       super.travel($$0);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateSwimming() {
/* 1626 */     if (this.abilities.flying) {
/* 1627 */       setSwimming(false);
/*      */     } else {
/* 1629 */       super.updateSwimming();
/*      */     } 
/*      */   }
/*      */   
/*      */   protected boolean freeAt(BlockPos $$0) {
/* 1634 */     return !level().getBlockState($$0).isSuffocating((BlockGetter)level(), $$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public float getSpeed() {
/* 1639 */     return (float)getAttributeValue(Attributes.MOVEMENT_SPEED);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean causeFallDamage(float $$0, float $$1, DamageSource $$2) {
/* 1647 */     if (this.abilities.mayfly) {
/* 1648 */       return false;
/*      */     }
/*      */     
/* 1651 */     if ($$0 >= 2.0F) {
/* 1652 */       awardStat(Stats.FALL_ONE_CM, (int)Math.round($$0 * 100.0D));
/*      */     }
/* 1654 */     return super.causeFallDamage($$0, $$1, $$2);
/*      */   }
/*      */   
/*      */   public boolean tryToStartFallFlying() {
/* 1658 */     if (!onGround() && !isFallFlying() && !isInWater() && !hasEffect(MobEffects.LEVITATION)) {
/* 1659 */       ItemStack $$0 = getItemBySlot(EquipmentSlot.CHEST);
/* 1660 */       if ($$0.is(Items.ELYTRA) && ElytraItem.isFlyEnabled($$0)) {
/* 1661 */         startFallFlying();
/* 1662 */         return true;
/*      */       } 
/*      */     } 
/* 1665 */     return false;
/*      */   }
/*      */   
/*      */   public void startFallFlying() {
/* 1669 */     setSharedFlag(7, true);
/*      */   }
/*      */ 
/*      */   
/*      */   public void stopFallFlying() {
/* 1674 */     setSharedFlag(7, true);
/* 1675 */     setSharedFlag(7, false);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void doWaterSplashEffect() {
/* 1680 */     if (!isSpectator()) {
/* 1681 */       super.doWaterSplashEffect();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected void playStepSound(BlockPos $$0, BlockState $$1) {
/* 1687 */     if (isInWater()) {
/* 1688 */       waterSwimSound();
/* 1689 */       playMuffledStepSound($$1);
/*      */     } else {
/* 1691 */       BlockPos $$2 = getPrimaryStepSoundBlockPos($$0);
/* 1692 */       if (!$$0.equals($$2)) {
/* 1693 */         BlockState $$3 = level().getBlockState($$2);
/* 1694 */         if ($$3.is(BlockTags.COMBINATION_STEP_SOUND_BLOCKS)) {
/* 1695 */           playCombinationStepSounds($$3, $$1);
/*      */         } else {
/* 1697 */           super.playStepSound($$2, $$3);
/*      */         } 
/*      */       } else {
/* 1700 */         super.playStepSound($$0, $$1);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public LivingEntity.Fallsounds getFallSounds() {
/* 1707 */     return new LivingEntity.Fallsounds(SoundEvents.PLAYER_SMALL_FALL, SoundEvents.PLAYER_BIG_FALL);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean killedEntity(ServerLevel $$0, LivingEntity $$1) {
/* 1712 */     awardStat(Stats.ENTITY_KILLED.get($$1.getType()));
/* 1713 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public void makeStuckInBlock(BlockState $$0, Vec3 $$1) {
/* 1718 */     if (!this.abilities.flying) {
/* 1719 */       super.makeStuckInBlock($$0, $$1);
/*      */     }
/*      */   }
/*      */   
/*      */   public void giveExperiencePoints(int $$0) {
/* 1724 */     increaseScore($$0);
/* 1725 */     this.experienceProgress += $$0 / getXpNeededForNextLevel();
/* 1726 */     this.totalExperience = Mth.clamp(this.totalExperience + $$0, 0, 2147483647);
/* 1727 */     while (this.experienceProgress < 0.0F) {
/* 1728 */       float $$1 = this.experienceProgress * getXpNeededForNextLevel();
/* 1729 */       if (this.experienceLevel > 0) {
/* 1730 */         giveExperienceLevels(-1);
/* 1731 */         this.experienceProgress = 1.0F + $$1 / getXpNeededForNextLevel(); continue;
/*      */       } 
/* 1733 */       giveExperienceLevels(-1);
/* 1734 */       this.experienceProgress = 0.0F;
/*      */     } 
/*      */     
/* 1737 */     while (this.experienceProgress >= 1.0F) {
/* 1738 */       this.experienceProgress = (this.experienceProgress - 1.0F) * getXpNeededForNextLevel();
/* 1739 */       giveExperienceLevels(1);
/* 1740 */       this.experienceProgress /= getXpNeededForNextLevel();
/*      */     } 
/*      */   }
/*      */   
/*      */   public int getEnchantmentSeed() {
/* 1745 */     return this.enchantmentSeed;
/*      */   }
/*      */   
/*      */   public void onEnchantmentPerformed(ItemStack $$0, int $$1) {
/* 1749 */     this.experienceLevel -= $$1;
/* 1750 */     if (this.experienceLevel < 0) {
/* 1751 */       this.experienceLevel = 0;
/* 1752 */       this.experienceProgress = 0.0F;
/* 1753 */       this.totalExperience = 0;
/*      */     } 
/* 1755 */     this.enchantmentSeed = this.random.nextInt();
/*      */   }
/*      */   
/*      */   public void giveExperienceLevels(int $$0) {
/* 1759 */     this.experienceLevel += $$0;
/* 1760 */     if (this.experienceLevel < 0) {
/* 1761 */       this.experienceLevel = 0;
/* 1762 */       this.experienceProgress = 0.0F;
/* 1763 */       this.totalExperience = 0;
/*      */     } 
/*      */     
/* 1766 */     if ($$0 > 0 && this.experienceLevel % 5 == 0 && this.lastLevelUpTime < this.tickCount - 100.0F) {
/* 1767 */       float $$1 = (this.experienceLevel > 30) ? 1.0F : (this.experienceLevel / 30.0F);
/* 1768 */       level().playSound(null, getX(), getY(), getZ(), SoundEvents.PLAYER_LEVELUP, getSoundSource(), $$1 * 0.75F, 1.0F);
/* 1769 */       this.lastLevelUpTime = this.tickCount;
/*      */     } 
/*      */   }
/*      */   
/*      */   public int getXpNeededForNextLevel() {
/* 1774 */     if (this.experienceLevel >= 30) {
/* 1775 */       return 112 + (this.experienceLevel - 30) * 9;
/*      */     }
/* 1777 */     if (this.experienceLevel >= 15) {
/* 1778 */       return 37 + (this.experienceLevel - 15) * 5;
/*      */     }
/* 1780 */     return 7 + this.experienceLevel * 2;
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
/*      */   public void causeFoodExhaustion(float $$0) {
/* 1792 */     if (this.abilities.invulnerable) {
/*      */       return;
/*      */     }
/*      */     
/* 1796 */     if (!(level()).isClientSide) {
/* 1797 */       this.foodData.addExhaustion($$0);
/*      */     }
/*      */   }
/*      */   
/*      */   public Optional<WardenSpawnTracker> getWardenSpawnTracker() {
/* 1802 */     return Optional.empty();
/*      */   }
/*      */   
/*      */   public FoodData getFoodData() {
/* 1806 */     return this.foodData;
/*      */   }
/*      */   
/*      */   public boolean canEat(boolean $$0) {
/* 1810 */     return (this.abilities.invulnerable || $$0 || this.foodData.needsFood());
/*      */   }
/*      */   
/*      */   public boolean isHurt() {
/* 1814 */     return (getHealth() > 0.0F && getHealth() < getMaxHealth());
/*      */   }
/*      */   
/*      */   public boolean mayBuild() {
/* 1818 */     return this.abilities.mayBuild;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean mayUseItemAt(BlockPos $$0, Direction $$1, ItemStack $$2) {
/* 1823 */     if (this.abilities.mayBuild) {
/* 1824 */       return true;
/*      */     }
/*      */     
/* 1827 */     BlockPos $$3 = $$0.relative($$1.getOpposite());
/* 1828 */     BlockInWorld $$4 = new BlockInWorld((LevelReader)level(), $$3, false);
/* 1829 */     return $$2.hasAdventureModePlaceTagForBlock(level().registryAccess().registryOrThrow(Registries.BLOCK), $$4);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getExperienceReward() {
/* 1834 */     if (level().getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY) || isSpectator()) {
/* 1835 */       return 0;
/*      */     }
/*      */     
/* 1838 */     int $$0 = this.experienceLevel * 7;
/* 1839 */     if ($$0 > 100) {
/* 1840 */       return 100;
/*      */     }
/* 1842 */     return $$0;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isAlwaysExperienceDropper() {
/* 1848 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean shouldShowName() {
/* 1853 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Entity.MovementEmission getMovementEmission() {
/* 1860 */     return (!this.abilities.flying && (!onGround() || !isDiscrete())) ? Entity.MovementEmission.ALL : Entity.MovementEmission.NONE;
/*      */   }
/*      */ 
/*      */   
/*      */   public void onUpdateAbilities() {}
/*      */ 
/*      */   
/*      */   public Component getName() {
/* 1868 */     return (Component)Component.literal(this.gameProfile.getName());
/*      */   }
/*      */   
/*      */   public PlayerEnderChestContainer getEnderChestInventory() {
/* 1872 */     return this.enderChestInventory;
/*      */   }
/*      */ 
/*      */   
/*      */   public ItemStack getItemBySlot(EquipmentSlot $$0) {
/* 1877 */     if ($$0 == EquipmentSlot.MAINHAND)
/* 1878 */       return this.inventory.getSelected(); 
/* 1879 */     if ($$0 == EquipmentSlot.OFFHAND)
/* 1880 */       return (ItemStack)this.inventory.offhand.get(0); 
/* 1881 */     if ($$0.getType() == EquipmentSlot.Type.ARMOR) {
/* 1882 */       return (ItemStack)this.inventory.armor.get($$0.getIndex());
/*      */     }
/* 1884 */     return ItemStack.EMPTY;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean doesEmitEquipEvent(EquipmentSlot $$0) {
/* 1891 */     return ($$0.getType() == EquipmentSlot.Type.ARMOR);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setItemSlot(EquipmentSlot $$0, ItemStack $$1) {
/* 1896 */     verifyEquippedItem($$1);
/* 1897 */     if ($$0 == EquipmentSlot.MAINHAND) {
/* 1898 */       onEquipItem($$0, (ItemStack)this.inventory.items.set(this.inventory.selected, $$1), $$1);
/* 1899 */     } else if ($$0 == EquipmentSlot.OFFHAND) {
/* 1900 */       onEquipItem($$0, (ItemStack)this.inventory.offhand.set(0, $$1), $$1);
/* 1901 */     } else if ($$0.getType() == EquipmentSlot.Type.ARMOR) {
/* 1902 */       onEquipItem($$0, (ItemStack)this.inventory.armor.set($$0.getIndex(), $$1), $$1);
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean addItem(ItemStack $$0) {
/* 1907 */     return this.inventory.add($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public Iterable<ItemStack> getHandSlots() {
/* 1912 */     return Lists.newArrayList((Object[])new ItemStack[] { getMainHandItem(), getOffhandItem() });
/*      */   }
/*      */ 
/*      */   
/*      */   public Iterable<ItemStack> getArmorSlots() {
/* 1917 */     return (Iterable<ItemStack>)this.inventory.armor;
/*      */   }
/*      */   
/*      */   public boolean setEntityOnShoulder(CompoundTag $$0) {
/* 1921 */     if (isPassenger() || !onGround() || isInWater() || this.isInPowderSnow) {
/* 1922 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1926 */     if (getShoulderEntityLeft().isEmpty()) {
/* 1927 */       setShoulderEntityLeft($$0);
/* 1928 */       this.timeEntitySatOnShoulder = level().getGameTime();
/* 1929 */       return true;
/* 1930 */     }  if (getShoulderEntityRight().isEmpty()) {
/* 1931 */       setShoulderEntityRight($$0);
/* 1932 */       this.timeEntitySatOnShoulder = level().getGameTime();
/* 1933 */       return true;
/*      */     } 
/*      */     
/* 1936 */     return false;
/*      */   }
/*      */   
/*      */   protected void removeEntitiesOnShoulder() {
/* 1940 */     if (this.timeEntitySatOnShoulder + 20L < level().getGameTime()) {
/* 1941 */       respawnEntityOnShoulder(getShoulderEntityLeft());
/* 1942 */       setShoulderEntityLeft(new CompoundTag());
/* 1943 */       respawnEntityOnShoulder(getShoulderEntityRight());
/* 1944 */       setShoulderEntityRight(new CompoundTag());
/*      */     } 
/*      */   }
/*      */   
/*      */   private void respawnEntityOnShoulder(CompoundTag $$0) {
/* 1949 */     if (!(level()).isClientSide && !$$0.isEmpty()) {
/* 1950 */       EntityType.create($$0, level()).ifPresent($$0 -> {
/*      */             if ($$0 instanceof TamableAnimal) {
/*      */               ((TamableAnimal)$$0).setOwnerUUID(this.uuid);
/*      */             }
/*      */             $$0.setPos(getX(), getY() + 0.699999988079071D, getZ());
/*      */             ((ServerLevel)level()).addWithUUID($$0);
/*      */           });
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canBeHitByProjectile() {
/* 1967 */     return (!isSpectator() && super.canBeHitByProjectile());
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isSwimming() {
/* 1972 */     return (!this.abilities.flying && !isSpectator() && super.isSwimming());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isPushedByFluid() {
/* 1979 */     return !this.abilities.flying;
/*      */   }
/*      */   
/*      */   public Scoreboard getScoreboard() {
/* 1983 */     return level().getScoreboard();
/*      */   }
/*      */ 
/*      */   
/*      */   public Component getDisplayName() {
/* 1988 */     MutableComponent $$0 = PlayerTeam.formatNameForTeam((Team)getTeam(), getName());
/* 1989 */     return (Component)decorateDisplayNameComponent($$0);
/*      */   }
/*      */   
/*      */   private MutableComponent decorateDisplayNameComponent(MutableComponent $$0) {
/* 1993 */     String $$1 = getGameProfile().getName();
/*      */     
/* 1995 */     return $$0.withStyle($$1 -> $$1.withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tell " + $$0 + " ")).withHoverEvent(createHoverEvent()).withInsertion($$0));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getScoreboardName() {
/* 2004 */     return getGameProfile().getName();
/*      */   }
/*      */ 
/*      */   
/*      */   public float getStandingEyeHeight(Pose $$0, EntityDimensions $$1) {
/* 2009 */     switch ($$0) {
/*      */       case SWIMMING:
/*      */       case FALL_FLYING:
/*      */       case SPIN_ATTACK:
/* 2013 */         return 0.4F;
/*      */       
/*      */       case CROUCHING:
/* 2016 */         return 1.27F;
/*      */     } 
/* 2018 */     return 1.62F;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void internalSetAbsorptionAmount(float $$0) {
/* 2024 */     getEntityData().set(DATA_PLAYER_ABSORPTION_ID, Float.valueOf($$0));
/*      */   }
/*      */ 
/*      */   
/*      */   public float getAbsorptionAmount() {
/* 2029 */     return ((Float)getEntityData().get(DATA_PLAYER_ABSORPTION_ID)).floatValue();
/*      */   }
/*      */   
/*      */   public boolean isModelPartShown(PlayerModelPart $$0) {
/* 2033 */     return ((((Byte)getEntityData().get(DATA_PLAYER_MODE_CUSTOMISATION)).byteValue() & $$0.getMask()) == $$0.getMask());
/*      */   }
/*      */ 
/*      */   
/*      */   public SlotAccess getSlot(int $$0) {
/* 2038 */     if ($$0 >= 0 && $$0 < this.inventory.items.size()) {
/* 2039 */       return SlotAccess.forContainer(this.inventory, $$0);
/*      */     }
/* 2041 */     int $$1 = $$0 - 200;
/* 2042 */     if ($$1 >= 0 && $$1 < this.enderChestInventory.getContainerSize()) {
/* 2043 */       return SlotAccess.forContainer((Container)this.enderChestInventory, $$1);
/*      */     }
/* 2045 */     return super.getSlot($$0);
/*      */   }
/*      */   
/*      */   public boolean isReducedDebugInfo() {
/* 2049 */     return this.reducedDebugInfo;
/*      */   }
/*      */   
/*      */   public void setReducedDebugInfo(boolean $$0) {
/* 2053 */     this.reducedDebugInfo = $$0;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setRemainingFireTicks(int $$0) {
/* 2058 */     super.setRemainingFireTicks(this.abilities.invulnerable ? Math.min($$0, 1) : $$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public HumanoidArm getMainArm() {
/* 2063 */     return (((Byte)this.entityData.get(DATA_PLAYER_MAIN_HAND)).byteValue() == 0) ? HumanoidArm.LEFT : HumanoidArm.RIGHT;
/*      */   }
/*      */   
/*      */   public void setMainArm(HumanoidArm $$0) {
/* 2067 */     this.entityData.set(DATA_PLAYER_MAIN_HAND, Byte.valueOf((byte)(($$0 == HumanoidArm.LEFT) ? 0 : 1)));
/*      */   }
/*      */   
/*      */   public CompoundTag getShoulderEntityLeft() {
/* 2071 */     return (CompoundTag)this.entityData.get(DATA_SHOULDER_LEFT);
/*      */   }
/*      */   
/*      */   protected void setShoulderEntityLeft(CompoundTag $$0) {
/* 2075 */     this.entityData.set(DATA_SHOULDER_LEFT, $$0);
/*      */   }
/*      */   
/*      */   public CompoundTag getShoulderEntityRight() {
/* 2079 */     return (CompoundTag)this.entityData.get(DATA_SHOULDER_RIGHT);
/*      */   }
/*      */   
/*      */   protected void setShoulderEntityRight(CompoundTag $$0) {
/* 2083 */     this.entityData.set(DATA_SHOULDER_RIGHT, $$0);
/*      */   }
/*      */   
/*      */   public float getCurrentItemAttackStrengthDelay() {
/* 2087 */     return (float)(1.0D / getAttributeValue(Attributes.ATTACK_SPEED) * 20.0D);
/*      */   }
/*      */   
/*      */   public float getAttackStrengthScale(float $$0) {
/* 2091 */     return Mth.clamp((this.attackStrengthTicker + $$0) / getCurrentItemAttackStrengthDelay(), 0.0F, 1.0F);
/*      */   }
/*      */   
/*      */   public void resetAttackStrengthTicker() {
/* 2095 */     this.attackStrengthTicker = 0;
/*      */   }
/*      */   
/*      */   public ItemCooldowns getCooldowns() {
/* 2099 */     return this.cooldowns;
/*      */   }
/*      */ 
/*      */   
/*      */   protected float getBlockSpeedFactor() {
/* 2104 */     return (this.abilities.flying || isFallFlying()) ? 1.0F : super.getBlockSpeedFactor();
/*      */   }
/*      */   
/*      */   public float getLuck() {
/* 2108 */     return (float)getAttributeValue(Attributes.LUCK);
/*      */   }
/*      */   
/*      */   public boolean canUseGameMasterBlocks() {
/* 2112 */     return (this.abilities.instabuild && getPermissionLevel() >= 2);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canTakeItem(ItemStack $$0) {
/* 2117 */     EquipmentSlot $$1 = Mob.getEquipmentSlotForItem($$0);
/* 2118 */     return getItemBySlot($$1).isEmpty();
/*      */   }
/*      */ 
/*      */   
/*      */   public EntityDimensions getDimensions(Pose $$0) {
/* 2123 */     return POSES.getOrDefault($$0, STANDING_DIMENSIONS);
/*      */   }
/*      */ 
/*      */   
/*      */   public ImmutableList<Pose> getDismountPoses() {
/* 2128 */     return ImmutableList.of(Pose.STANDING, Pose.CROUCHING, Pose.SWIMMING);
/*      */   }
/*      */ 
/*      */   
/*      */   public ItemStack getProjectile(ItemStack $$0) {
/* 2133 */     if (!($$0.getItem() instanceof ProjectileWeaponItem)) {
/* 2134 */       return ItemStack.EMPTY;
/*      */     }
/*      */     
/* 2137 */     Predicate<ItemStack> $$1 = ((ProjectileWeaponItem)$$0.getItem()).getSupportedHeldProjectiles();
/* 2138 */     ItemStack $$2 = ProjectileWeaponItem.getHeldProjectile(this, $$1);
/* 2139 */     if (!$$2.isEmpty()) {
/* 2140 */       return $$2;
/*      */     }
/*      */     
/* 2143 */     $$1 = ((ProjectileWeaponItem)$$0.getItem()).getAllSupportedProjectiles();
/* 2144 */     for (int $$3 = 0; $$3 < this.inventory.getContainerSize(); $$3++) {
/* 2145 */       ItemStack $$4 = this.inventory.getItem($$3);
/* 2146 */       if ($$1.test($$4)) {
/* 2147 */         return $$4;
/*      */       }
/*      */     } 
/* 2150 */     return this.abilities.instabuild ? new ItemStack((ItemLike)Items.ARROW) : ItemStack.EMPTY;
/*      */   }
/*      */ 
/*      */   
/*      */   public ItemStack eat(Level $$0, ItemStack $$1) {
/* 2155 */     getFoodData().eat($$1.getItem(), $$1);
/* 2156 */     awardStat(Stats.ITEM_USED.get($$1.getItem()));
/*      */     
/* 2158 */     $$0.playSound(null, getX(), getY(), getZ(), SoundEvents.PLAYER_BURP, SoundSource.PLAYERS, 0.5F, $$0.random.nextFloat() * 0.1F + 0.9F);
/* 2159 */     if (this instanceof ServerPlayer) {
/* 2160 */       CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer)this, $$1);
/*      */     }
/* 2162 */     return super.eat($$0, $$1);
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean shouldRemoveSoulSpeed(BlockState $$0) {
/* 2167 */     return (this.abilities.flying || super.shouldRemoveSoulSpeed($$0));
/*      */   }
/*      */ 
/*      */   
/*      */   public Vec3 getRopeHoldPosition(float $$0) {
/* 2172 */     double $$1 = 0.22D * ((getMainArm() == HumanoidArm.RIGHT) ? -1.0D : 1.0D);
/* 2173 */     float $$2 = Mth.lerp($$0 * 0.5F, getXRot(), this.xRotO) * 0.017453292F;
/* 2174 */     float $$3 = Mth.lerp($$0, this.yBodyRotO, this.yBodyRot) * 0.017453292F;
/* 2175 */     if (isFallFlying() || isAutoSpinAttack()) {
/*      */       float $$11;
/* 2177 */       Vec3 $$4 = getViewVector($$0);
/* 2178 */       Vec3 $$5 = getDeltaMovement();
/* 2179 */       double $$6 = $$5.horizontalDistanceSqr();
/* 2180 */       double $$7 = $$4.horizontalDistanceSqr();
/*      */       
/* 2182 */       if ($$6 > 0.0D && $$7 > 0.0D) {
/* 2183 */         double $$8 = ($$5.x * $$4.x + $$5.z * $$4.z) / Math.sqrt($$6 * $$7);
/* 2184 */         double $$9 = $$5.x * $$4.z - $$5.z * $$4.x;
/* 2185 */         float $$10 = (float)(Math.signum($$9) * Math.acos($$8));
/*      */       } else {
/* 2187 */         $$11 = 0.0F;
/*      */       } 
/* 2189 */       return getPosition($$0).add((new Vec3($$1, -0.11D, 0.85D)).zRot(-$$11).xRot(-$$2).yRot(-$$3));
/* 2190 */     }  if (isVisuallySwimming()) {
/* 2191 */       return getPosition($$0).add((new Vec3($$1, 0.2D, -0.15D)).xRot(-$$2).yRot(-$$3));
/*      */     }
/* 2193 */     double $$12 = getBoundingBox().getYsize() - 1.0D;
/* 2194 */     double $$13 = isCrouching() ? -0.2D : 0.07D;
/* 2195 */     return getPosition($$0).add((new Vec3($$1, $$12, $$13)).yRot(-$$3));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAlwaysTicking() {
/* 2201 */     return true;
/*      */   }
/*      */   
/*      */   public boolean isScoping() {
/* 2205 */     return (isUsingItem() && getUseItem().is(Items.SPYGLASS));
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean shouldBeSaved() {
/* 2210 */     return false;
/*      */   }
/*      */   
/*      */   public Optional<GlobalPos> getLastDeathLocation() {
/* 2214 */     return this.lastDeathLocation;
/*      */   }
/*      */   
/*      */   public void setLastDeathLocation(Optional<GlobalPos> $$0) {
/* 2218 */     this.lastDeathLocation = $$0;
/*      */   }
/*      */ 
/*      */   
/*      */   public float getHurtDir() {
/* 2223 */     return this.hurtDir;
/*      */   }
/*      */ 
/*      */   
/*      */   public void animateHurt(float $$0) {
/* 2228 */     super.animateHurt($$0);
/* 2229 */     this.hurtDir = $$0;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canSprint() {
/* 2234 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   protected float getFlyingSpeed() {
/* 2239 */     if (this.abilities.flying && !isPassenger()) {
/* 2240 */       return isSprinting() ? (this.abilities.getFlyingSpeed() * 2.0F) : this.abilities.getFlyingSpeed();
/*      */     }
/* 2242 */     return isSprinting() ? 0.025999999F : 0.02F;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isValidUsername(String $$0) {
/* 2247 */     if ($$0.length() > 16) {
/* 2248 */       return false;
/*      */     }
/* 2250 */     return $$0.chars().filter($$0 -> ($$0 <= 32 || $$0 >= 127)).findAny().isEmpty();
/*      */   }
/*      */   
/*      */   public static float getPickRange(boolean $$0) {
/* 2254 */     if ($$0) {
/* 2255 */       return 5.0F;
/*      */     }
/* 2257 */     return 4.5F;
/*      */   }
/*      */   
/*      */   public abstract boolean isSpectator();
/*      */   
/*      */   public abstract boolean isCreative();
/*      */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\player\Player.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */