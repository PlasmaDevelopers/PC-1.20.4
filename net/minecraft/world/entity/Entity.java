/*      */ package net.minecraft.world.entity;
/*      */ import com.google.common.collect.ImmutableList;
/*      */ import com.google.common.collect.Iterables;
/*      */ import com.google.common.collect.Lists;
/*      */ import com.google.common.collect.Sets;
/*      */ import com.google.common.collect.UnmodifiableIterator;
/*      */ import com.mojang.logging.LogUtils;
/*      */ import it.unimi.dsi.fastutil.objects.Object2DoubleArrayMap;
/*      */ import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Objects;
/*      */ import java.util.Optional;
/*      */ import java.util.Set;
/*      */ import java.util.UUID;
/*      */ import java.util.concurrent.atomic.AtomicInteger;
/*      */ import java.util.function.BiConsumer;
/*      */ import java.util.function.Predicate;
/*      */ import java.util.stream.Stream;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.BlockUtil;
/*      */ import net.minecraft.CrashReport;
/*      */ import net.minecraft.CrashReportCategory;
/*      */ import net.minecraft.ReportedException;
/*      */ import net.minecraft.Util;
/*      */ import net.minecraft.advancements.CriteriaTriggers;
/*      */ import net.minecraft.commands.CommandSource;
/*      */ import net.minecraft.commands.CommandSourceStack;
/*      */ import net.minecraft.commands.arguments.EntityAnchorArgument;
/*      */ import net.minecraft.core.BlockPos;
/*      */ import net.minecraft.core.Direction;
/*      */ import net.minecraft.core.Position;
/*      */ import net.minecraft.core.SectionPos;
/*      */ import net.minecraft.core.Vec3i;
/*      */ import net.minecraft.core.particles.BlockParticleOption;
/*      */ import net.minecraft.core.particles.ParticleOptions;
/*      */ import net.minecraft.core.particles.ParticleTypes;
/*      */ import net.minecraft.nbt.CompoundTag;
/*      */ import net.minecraft.nbt.DoubleTag;
/*      */ import net.minecraft.nbt.FloatTag;
/*      */ import net.minecraft.nbt.ListTag;
/*      */ import net.minecraft.nbt.StringTag;
/*      */ import net.minecraft.nbt.Tag;
/*      */ import net.minecraft.network.chat.Component;
/*      */ import net.minecraft.network.chat.HoverEvent;
/*      */ import net.minecraft.network.chat.MutableComponent;
/*      */ import net.minecraft.network.chat.Style;
/*      */ import net.minecraft.network.protocol.Packet;
/*      */ import net.minecraft.network.protocol.game.ClientGamePacketListener;
/*      */ import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
/*      */ import net.minecraft.network.protocol.game.VecDeltaCodec;
/*      */ import net.minecraft.network.syncher.EntityDataAccessor;
/*      */ import net.minecraft.network.syncher.EntityDataSerializers;
/*      */ import net.minecraft.network.syncher.SynchedEntityData;
/*      */ import net.minecraft.resources.ResourceKey;
/*      */ import net.minecraft.resources.ResourceLocation;
/*      */ import net.minecraft.server.MinecraftServer;
/*      */ import net.minecraft.server.level.ServerLevel;
/*      */ import net.minecraft.server.level.ServerPlayer;
/*      */ import net.minecraft.server.level.TicketType;
/*      */ import net.minecraft.sounds.SoundEvent;
/*      */ import net.minecraft.sounds.SoundEvents;
/*      */ import net.minecraft.sounds.SoundSource;
/*      */ import net.minecraft.tags.BlockTags;
/*      */ import net.minecraft.tags.DamageTypeTags;
/*      */ import net.minecraft.tags.EntityTypeTags;
/*      */ import net.minecraft.tags.FluidTags;
/*      */ import net.minecraft.tags.TagKey;
/*      */ import net.minecraft.util.Mth;
/*      */ import net.minecraft.util.RandomSource;
/*      */ import net.minecraft.world.InteractionHand;
/*      */ import net.minecraft.world.InteractionResult;
/*      */ import net.minecraft.world.Nameable;
/*      */ import net.minecraft.world.damagesource.DamageSource;
/*      */ import net.minecraft.world.damagesource.DamageSources;
/*      */ import net.minecraft.world.entity.item.ItemEntity;
/*      */ import net.minecraft.world.entity.player.Player;
/*      */ import net.minecraft.world.entity.vehicle.Boat;
/*      */ import net.minecraft.world.item.Item;
/*      */ import net.minecraft.world.item.ItemStack;
/*      */ import net.minecraft.world.item.enchantment.EnchantmentHelper;
/*      */ import net.minecraft.world.item.enchantment.ProtectionEnchantment;
/*      */ import net.minecraft.world.level.BlockGetter;
/*      */ import net.minecraft.world.level.ChunkPos;
/*      */ import net.minecraft.world.level.ClipContext;
/*      */ import net.minecraft.world.level.Explosion;
/*      */ import net.minecraft.world.level.GameRules;
/*      */ import net.minecraft.world.level.ItemLike;
/*      */ import net.minecraft.world.level.Level;
/*      */ import net.minecraft.world.level.LevelHeightAccessor;
/*      */ import net.minecraft.world.level.block.Block;
/*      */ import net.minecraft.world.level.block.Blocks;
/*      */ import net.minecraft.world.level.block.HoneyBlock;
/*      */ import net.minecraft.world.level.block.Mirror;
/*      */ import net.minecraft.world.level.block.Rotation;
/*      */ import net.minecraft.world.level.block.SoundType;
/*      */ import net.minecraft.world.level.block.state.BlockState;
/*      */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*      */ import net.minecraft.world.level.block.state.properties.Property;
/*      */ import net.minecraft.world.level.border.WorldBorder;
/*      */ import net.minecraft.world.level.dimension.DimensionType;
/*      */ import net.minecraft.world.level.entity.EntityAccess;
/*      */ import net.minecraft.world.level.entity.EntityInLevelCallback;
/*      */ import net.minecraft.world.level.gameevent.DynamicGameEventListener;
/*      */ import net.minecraft.world.level.gameevent.GameEvent;
/*      */ import net.minecraft.world.level.levelgen.Heightmap;
/*      */ import net.minecraft.world.level.material.Fluid;
/*      */ import net.minecraft.world.level.material.FluidState;
/*      */ import net.minecraft.world.level.material.PushReaction;
/*      */ import net.minecraft.world.level.portal.PortalInfo;
/*      */ import net.minecraft.world.level.portal.PortalShape;
/*      */ import net.minecraft.world.phys.AABB;
/*      */ import net.minecraft.world.phys.BlockHitResult;
/*      */ import net.minecraft.world.phys.HitResult;
/*      */ import net.minecraft.world.phys.Vec2;
/*      */ import net.minecraft.world.phys.Vec3;
/*      */ import net.minecraft.world.phys.shapes.BooleanOp;
/*      */ import net.minecraft.world.phys.shapes.CollisionContext;
/*      */ import net.minecraft.world.phys.shapes.Shapes;
/*      */ import net.minecraft.world.phys.shapes.VoxelShape;
/*      */ import net.minecraft.world.scores.PlayerTeam;
/*      */ import net.minecraft.world.scores.ScoreHolder;
/*      */ import net.minecraft.world.scores.Team;
/*      */ import org.joml.Vector3f;
/*      */ import org.slf4j.Logger;
/*      */ 
/*      */ public abstract class Entity implements Nameable, EntityAccess, CommandSource, ScoreHolder {
/*  132 */   private static final Logger LOGGER = LogUtils.getLogger();
/*      */   
/*      */   public static final String ID_TAG = "id";
/*      */   public static final String PASSENGERS_TAG = "Passengers";
/*  136 */   private static final AtomicInteger ENTITY_COUNTER = new AtomicInteger();
/*  137 */   private static final List<ItemStack> EMPTY_LIST = Collections.emptyList();
/*      */   
/*      */   public static final int BOARDING_COOLDOWN = 60;
/*      */   
/*      */   public static final int TOTAL_AIR_SUPPLY = 300;
/*      */   
/*      */   public static final int MAX_ENTITY_TAG_COUNT = 1024;
/*      */   public static final float DELTA_AFFECTED_BY_BLOCKS_BELOW_0_2 = 0.2F;
/*      */   public static final double DELTA_AFFECTED_BY_BLOCKS_BELOW_0_5 = 0.500001D;
/*      */   public static final double DELTA_AFFECTED_BY_BLOCKS_BELOW_1_0 = 0.999999D;
/*      */   public static final float BREATHING_DISTANCE_BELOW_EYES = 0.11111111F;
/*      */   public static final int BASE_TICKS_REQUIRED_TO_FREEZE = 140;
/*      */   public static final int FREEZE_HURT_FREQUENCY = 40;
/*  150 */   private static final AABB INITIAL_AABB = new AABB(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
/*      */   
/*      */   private static final double WATER_FLOW_SCALE = 0.014D;
/*      */   
/*      */   private static final double LAVA_FAST_FLOW_SCALE = 0.007D;
/*      */   
/*      */   private static final double LAVA_SLOW_FLOW_SCALE = 0.0023333333333333335D;
/*      */   public static final String UUID_TAG = "UUID";
/*  158 */   private static double viewScale = 1.0D;
/*      */   
/*      */   private final EntityType<?> type;
/*  161 */   private int id = ENTITY_COUNTER.incrementAndGet();
/*      */   
/*      */   public boolean blocksBuilding;
/*  164 */   private ImmutableList<Entity> passengers = ImmutableList.of();
/*      */   
/*      */   protected int boardingCooldown;
/*      */   @Nullable
/*      */   private Entity vehicle;
/*      */   private Level level;
/*      */   public double xo;
/*      */   public double yo;
/*      */   public double zo;
/*      */   private Vec3 position;
/*      */   private BlockPos blockPosition;
/*      */   private ChunkPos chunkPosition;
/*  176 */   private Vec3 deltaMovement = Vec3.ZERO;
/*      */   private float yRot;
/*      */   private float xRot;
/*      */   public float yRotO;
/*      */   public float xRotO;
/*  181 */   private AABB bb = INITIAL_AABB;
/*      */   private boolean onGround;
/*      */   public boolean horizontalCollision;
/*      */   public boolean verticalCollision;
/*      */   public boolean verticalCollisionBelow;
/*      */   public boolean minorHorizontalCollision;
/*      */   public boolean hurtMarked;
/*  188 */   protected Vec3 stuckSpeedMultiplier = Vec3.ZERO;
/*      */   
/*      */   @Nullable
/*      */   private RemovalReason removalReason;
/*      */   
/*      */   public static final float DEFAULT_BB_WIDTH = 0.6F;
/*      */   
/*      */   public static final float DEFAULT_BB_HEIGHT = 1.8F;
/*      */   
/*      */   public float walkDistO;
/*      */   public float walkDist;
/*      */   public float moveDist;
/*      */   public float flyDist;
/*      */   public float fallDistance;
/*  202 */   private float nextStep = 1.0F;
/*      */   
/*      */   public double xOld;
/*      */   public double yOld;
/*      */   public double zOld;
/*      */   private float maxUpStep;
/*      */   public boolean noPhysics;
/*  209 */   protected final RandomSource random = RandomSource.create();
/*      */   
/*      */   public int tickCount;
/*  212 */   private int remainingFireTicks = -getFireImmuneTicks();
/*      */   
/*      */   protected boolean wasTouchingWater;
/*  215 */   protected Object2DoubleMap<TagKey<Fluid>> fluidHeight = (Object2DoubleMap<TagKey<Fluid>>)new Object2DoubleArrayMap(2);
/*      */   protected boolean wasEyeInWater;
/*  217 */   private final Set<TagKey<Fluid>> fluidOnEyes = new HashSet<>();
/*      */ 
/*      */   
/*      */   public int invulnerableTime;
/*      */ 
/*      */   
/*      */   protected boolean firstTick = true;
/*      */   
/*      */   protected final SynchedEntityData entityData;
/*      */   
/*  227 */   protected static final EntityDataAccessor<Byte> DATA_SHARED_FLAGS_ID = SynchedEntityData.defineId(Entity.class, EntityDataSerializers.BYTE);
/*      */   
/*      */   protected static final int FLAG_ONFIRE = 0;
/*      */   private static final int FLAG_SHIFT_KEY_DOWN = 1;
/*      */   private static final int FLAG_SPRINTING = 3;
/*      */   private static final int FLAG_SWIMMING = 4;
/*      */   private static final int FLAG_INVISIBLE = 5;
/*      */   protected static final int FLAG_GLOWING = 6;
/*      */   protected static final int FLAG_FALL_FLYING = 7;
/*  236 */   private static final EntityDataAccessor<Integer> DATA_AIR_SUPPLY_ID = SynchedEntityData.defineId(Entity.class, EntityDataSerializers.INT);
/*  237 */   private static final EntityDataAccessor<Optional<Component>> DATA_CUSTOM_NAME = SynchedEntityData.defineId(Entity.class, EntityDataSerializers.OPTIONAL_COMPONENT);
/*  238 */   private static final EntityDataAccessor<Boolean> DATA_CUSTOM_NAME_VISIBLE = SynchedEntityData.defineId(Entity.class, EntityDataSerializers.BOOLEAN);
/*  239 */   private static final EntityDataAccessor<Boolean> DATA_SILENT = SynchedEntityData.defineId(Entity.class, EntityDataSerializers.BOOLEAN);
/*  240 */   private static final EntityDataAccessor<Boolean> DATA_NO_GRAVITY = SynchedEntityData.defineId(Entity.class, EntityDataSerializers.BOOLEAN);
/*  241 */   protected static final EntityDataAccessor<Pose> DATA_POSE = SynchedEntityData.defineId(Entity.class, EntityDataSerializers.POSE);
/*  242 */   private static final EntityDataAccessor<Integer> DATA_TICKS_FROZEN = SynchedEntityData.defineId(Entity.class, EntityDataSerializers.INT);
/*      */   
/*  244 */   private EntityInLevelCallback levelCallback = EntityInLevelCallback.NULL;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  249 */   private final VecDeltaCodec packetPositionCodec = new VecDeltaCodec();
/*      */   public boolean noCulling;
/*      */   public boolean hasImpulse;
/*      */   private int portalCooldown;
/*      */   protected boolean isInsidePortal;
/*      */   protected int portalTime;
/*      */   protected BlockPos portalEntrancePos;
/*      */   private boolean invulnerable;
/*  257 */   protected UUID uuid = Mth.createInsecureUUID(this.random);
/*  258 */   protected String stringUUID = this.uuid.toString();
/*      */   
/*      */   private boolean hasGlowingTag;
/*  261 */   private final Set<String> tags = Sets.newHashSet();
/*      */   
/*  263 */   private final double[] pistonDeltas = new double[] { 0.0D, 0.0D, 0.0D };
/*      */   
/*      */   private long pistonDeltasGameTime;
/*      */   private EntityDimensions dimensions;
/*      */   private float eyeHeight;
/*      */   public boolean isInPowderSnow;
/*      */   public boolean wasInPowderSnow;
/*      */   public boolean wasOnFire;
/*  271 */   public Optional<BlockPos> mainSupportingBlockPos = Optional.empty();
/*      */   
/*      */   private boolean onGroundNoBlocks = false;
/*      */   
/*      */   private float crystalSoundIntensity;
/*      */   private int lastCrystalSoundPlayTick;
/*      */   private boolean hasVisualFire;
/*      */   @Nullable
/*  279 */   private BlockState feetBlockState = null;
/*      */ 
/*      */   
/*      */   public Entity(EntityType<?> $$0, Level $$1) {
/*  283 */     this.type = $$0;
/*  284 */     this.level = $$1;
/*      */     
/*  286 */     this.dimensions = $$0.getDimensions();
/*  287 */     this.position = Vec3.ZERO;
/*  288 */     this.blockPosition = BlockPos.ZERO;
/*  289 */     this.chunkPosition = ChunkPos.ZERO;
/*      */     
/*  291 */     this.entityData = new SynchedEntityData(this);
/*  292 */     this.entityData.define(DATA_SHARED_FLAGS_ID, Byte.valueOf((byte)0));
/*  293 */     this.entityData.define(DATA_AIR_SUPPLY_ID, Integer.valueOf(getMaxAirSupply()));
/*  294 */     this.entityData.define(DATA_CUSTOM_NAME_VISIBLE, Boolean.valueOf(false));
/*  295 */     this.entityData.define(DATA_CUSTOM_NAME, Optional.empty());
/*  296 */     this.entityData.define(DATA_SILENT, Boolean.valueOf(false));
/*  297 */     this.entityData.define(DATA_NO_GRAVITY, Boolean.valueOf(false));
/*  298 */     this.entityData.define(DATA_POSE, Pose.STANDING);
/*  299 */     this.entityData.define(DATA_TICKS_FROZEN, Integer.valueOf(0));
/*  300 */     defineSynchedData();
/*      */     
/*  302 */     setPos(0.0D, 0.0D, 0.0D);
/*      */     
/*  304 */     this.eyeHeight = getEyeHeight(Pose.STANDING, this.dimensions);
/*      */   }
/*      */   
/*      */   public boolean isColliding(BlockPos $$0, BlockState $$1) {
/*  308 */     VoxelShape $$2 = $$1.getCollisionShape((BlockGetter)level(), $$0, CollisionContext.of(this));
/*  309 */     VoxelShape $$3 = $$2.move($$0.getX(), $$0.getY(), $$0.getZ());
/*  310 */     return Shapes.joinIsNotEmpty($$3, Shapes.create(getBoundingBox()), BooleanOp.AND);
/*      */   }
/*      */   
/*      */   public int getTeamColor() {
/*  314 */     PlayerTeam playerTeam = getTeam();
/*  315 */     if (playerTeam != null && playerTeam.getColor().getColor() != null) {
/*  316 */       return playerTeam.getColor().getColor().intValue();
/*      */     }
/*  318 */     return 16777215;
/*      */   }
/*      */   
/*      */   public boolean isSpectator() {
/*  322 */     return false;
/*      */   }
/*      */   
/*      */   public final void unRide() {
/*  326 */     if (isVehicle()) {
/*  327 */       ejectPassengers();
/*      */     }
/*  329 */     if (isPassenger()) {
/*  330 */       stopRiding();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void syncPacketPositionCodec(double $$0, double $$1, double $$2) {
/*  340 */     this.packetPositionCodec.setBase(new Vec3($$0, $$1, $$2));
/*      */   }
/*      */   
/*      */   public VecDeltaCodec getPositionCodec() {
/*  344 */     return this.packetPositionCodec;
/*      */   }
/*      */   
/*      */   public EntityType<?> getType() {
/*  348 */     return this.type;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getId() {
/*  353 */     return this.id;
/*      */   }
/*      */   
/*      */   public void setId(int $$0) {
/*  357 */     this.id = $$0;
/*      */   }
/*      */   
/*      */   public Set<String> getTags() {
/*  361 */     return this.tags;
/*      */   }
/*      */   
/*      */   public boolean addTag(String $$0) {
/*  365 */     if (this.tags.size() >= 1024) {
/*  366 */       return false;
/*      */     }
/*  368 */     return this.tags.add($$0);
/*      */   }
/*      */   
/*      */   public boolean removeTag(String $$0) {
/*  372 */     return this.tags.remove($$0);
/*      */   }
/*      */   
/*      */   public void kill() {
/*  376 */     remove(RemovalReason.KILLED);
/*  377 */     gameEvent(GameEvent.ENTITY_DIE);
/*      */   }
/*      */   
/*      */   public final void discard() {
/*  381 */     remove(RemovalReason.DISCARDED);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public SynchedEntityData getEntityData() {
/*  387 */     return this.entityData;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean equals(Object $$0) {
/*  392 */     if ($$0 instanceof Entity) {
/*  393 */       return (((Entity)$$0).id == this.id);
/*      */     }
/*  395 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public int hashCode() {
/*  400 */     return this.id;
/*      */   }
/*      */ 
/*      */   
/*      */   public void remove(RemovalReason $$0) {
/*  405 */     setRemoved($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public void onClientRemoval() {}
/*      */   
/*      */   public void setPose(Pose $$0) {
/*  412 */     this.entityData.set(DATA_POSE, $$0);
/*      */   }
/*      */   
/*      */   public Pose getPose() {
/*  416 */     return (Pose)this.entityData.get(DATA_POSE);
/*      */   }
/*      */   
/*      */   public boolean hasPose(Pose $$0) {
/*  420 */     return (getPose() == $$0);
/*      */   }
/*      */   
/*      */   public boolean closerThan(Entity $$0, double $$1) {
/*  424 */     return position().closerThan((Position)$$0.position(), $$1);
/*      */   }
/*      */   
/*      */   public boolean closerThan(Entity $$0, double $$1, double $$2) {
/*  428 */     double $$3 = $$0.getX() - getX();
/*  429 */     double $$4 = $$0.getY() - getY();
/*  430 */     double $$5 = $$0.getZ() - getZ();
/*  431 */     return (Mth.lengthSquared($$3, $$5) < Mth.square($$1) && 
/*  432 */       Mth.square($$4) < Mth.square($$2));
/*      */   }
/*      */   
/*      */   protected void setRot(float $$0, float $$1) {
/*  436 */     setYRot($$0 % 360.0F);
/*  437 */     setXRot($$1 % 360.0F);
/*      */   }
/*      */   
/*      */   public final void setPos(Vec3 $$0) {
/*  441 */     setPos($$0.x(), $$0.y(), $$0.z());
/*      */   }
/*      */ 
/*      */   
/*      */   public void setPos(double $$0, double $$1, double $$2) {
/*  446 */     setPosRaw($$0, $$1, $$2);
/*  447 */     setBoundingBox(makeBoundingBox());
/*      */   }
/*      */   
/*      */   protected AABB makeBoundingBox() {
/*  451 */     return this.dimensions.makeBoundingBox(this.position);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void reapplyPosition() {
/*  456 */     setPos(this.position.x, this.position.y, this.position.z);
/*      */   }
/*      */   
/*      */   public void turn(double $$0, double $$1) {
/*  460 */     float $$2 = (float)$$1 * 0.15F;
/*  461 */     float $$3 = (float)$$0 * 0.15F;
/*      */     
/*  463 */     setXRot(getXRot() + $$2);
/*  464 */     setYRot(getYRot() + $$3);
/*  465 */     setXRot(Mth.clamp(getXRot(), -90.0F, 90.0F));
/*      */     
/*  467 */     this.xRotO += $$2;
/*  468 */     this.yRotO += $$3;
/*  469 */     this.xRotO = Mth.clamp(this.xRotO, -90.0F, 90.0F);
/*      */     
/*  471 */     if (this.vehicle != null) {
/*  472 */       this.vehicle.onPassengerTurned(this);
/*      */     }
/*      */   }
/*      */   
/*      */   public void tick() {
/*  477 */     baseTick();
/*      */   }
/*      */   
/*      */   public void baseTick() {
/*  481 */     level().getProfiler().push("entityBaseTick");
/*      */     
/*  483 */     this.feetBlockState = null;
/*      */     
/*  485 */     if (isPassenger() && getVehicle().isRemoved()) {
/*  486 */       stopRiding();
/*      */     }
/*      */     
/*  489 */     if (this.boardingCooldown > 0) {
/*  490 */       this.boardingCooldown--;
/*      */     }
/*      */     
/*  493 */     this.walkDistO = this.walkDist;
/*  494 */     this.xRotO = getXRot();
/*  495 */     this.yRotO = getYRot();
/*      */     
/*  497 */     handleNetherPortal();
/*      */ 
/*      */     
/*  500 */     if (canSpawnSprintParticle()) {
/*  501 */       spawnSprintParticle();
/*      */     }
/*      */     
/*  504 */     this.wasInPowderSnow = this.isInPowderSnow;
/*  505 */     this.isInPowderSnow = false;
/*  506 */     updateInWaterStateAndDoFluidPushing();
/*  507 */     updateFluidOnEyes();
/*  508 */     updateSwimming();
/*      */     
/*  510 */     if ((level()).isClientSide) {
/*  511 */       clearFire();
/*      */     }
/*  513 */     else if (this.remainingFireTicks > 0) {
/*  514 */       if (fireImmune()) {
/*  515 */         setRemainingFireTicks(this.remainingFireTicks - 4);
/*  516 */         if (this.remainingFireTicks < 0) {
/*  517 */           clearFire();
/*      */         }
/*      */       } else {
/*  520 */         if (this.remainingFireTicks % 20 == 0 && !isInLava()) {
/*  521 */           hurt(damageSources().onFire(), 1.0F);
/*      */         }
/*  523 */         setRemainingFireTicks(this.remainingFireTicks - 1);
/*      */       } 
/*      */ 
/*      */       
/*  527 */       if (getTicksFrozen() > 0) {
/*  528 */         setTicksFrozen(0);
/*  529 */         level().levelEvent(null, 1009, this.blockPosition, 1);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  534 */     if (isInLava()) {
/*  535 */       lavaHurt();
/*  536 */       this.fallDistance *= 0.5F;
/*      */     } 
/*      */     
/*  539 */     checkBelowWorld();
/*      */     
/*  541 */     if (!(level()).isClientSide) {
/*  542 */       setSharedFlagOnFire((this.remainingFireTicks > 0));
/*      */     }
/*      */     
/*  545 */     this.firstTick = false;
/*      */     
/*  547 */     level().getProfiler().pop();
/*      */   }
/*      */   
/*      */   public void setSharedFlagOnFire(boolean $$0) {
/*  551 */     setSharedFlag(0, ($$0 || this.hasVisualFire));
/*      */   }
/*      */   
/*      */   public void checkBelowWorld() {
/*  555 */     if (getY() < (level().getMinBuildHeight() - 64)) {
/*  556 */       onBelowWorld();
/*      */     }
/*      */   }
/*      */   
/*      */   public void setPortalCooldown() {
/*  561 */     this.portalCooldown = getDimensionChangingDelay();
/*      */   }
/*      */   
/*      */   public void setPortalCooldown(int $$0) {
/*  565 */     this.portalCooldown = $$0;
/*      */   }
/*      */   
/*      */   public int getPortalCooldown() {
/*  569 */     return this.portalCooldown;
/*      */   }
/*      */   
/*      */   public boolean isOnPortalCooldown() {
/*  573 */     return (this.portalCooldown > 0);
/*      */   }
/*      */   
/*      */   protected void processPortalCooldown() {
/*  577 */     if (isOnPortalCooldown()) {
/*  578 */       this.portalCooldown--;
/*      */     }
/*      */   }
/*      */   
/*      */   public int getPortalWaitTime() {
/*  583 */     return 0;
/*      */   }
/*      */   
/*      */   public void lavaHurt() {
/*  587 */     if (fireImmune()) {
/*      */       return;
/*      */     }
/*      */     
/*  591 */     setSecondsOnFire(15);
/*      */     
/*  593 */     if (hurt(damageSources().lava(), 4.0F)) {
/*  594 */       playSound(SoundEvents.GENERIC_BURN, 0.4F, 2.0F + this.random.nextFloat() * 0.4F);
/*      */     }
/*      */   }
/*      */   
/*      */   public void setSecondsOnFire(int $$0) {
/*  599 */     int $$1 = $$0 * 20;
/*  600 */     if (this instanceof LivingEntity) {
/*  601 */       $$1 = ProtectionEnchantment.getFireAfterDampener((LivingEntity)this, $$1);
/*      */     }
/*  603 */     if (this.remainingFireTicks < $$1) {
/*  604 */       setRemainingFireTicks($$1);
/*      */     }
/*      */   }
/*      */   
/*      */   public void setRemainingFireTicks(int $$0) {
/*  609 */     this.remainingFireTicks = $$0;
/*      */   }
/*      */   
/*      */   public int getRemainingFireTicks() {
/*  613 */     return this.remainingFireTicks;
/*      */   }
/*      */   
/*      */   public void clearFire() {
/*  617 */     setRemainingFireTicks(0);
/*      */   }
/*      */   
/*      */   protected void onBelowWorld() {
/*  621 */     discard();
/*      */   }
/*      */   
/*      */   public boolean isFree(double $$0, double $$1, double $$2) {
/*  625 */     return isFree(getBoundingBox().move($$0, $$1, $$2));
/*      */   }
/*      */   
/*      */   private boolean isFree(AABB $$0) {
/*  629 */     return (level().noCollision(this, $$0) && !level().containsAnyLiquid($$0));
/*      */   }
/*      */   
/*      */   public void setOnGround(boolean $$0) {
/*  633 */     this.onGround = $$0;
/*  634 */     checkSupportingBlock($$0, null);
/*      */   }
/*      */   
/*      */   public void setOnGroundWithKnownMovement(boolean $$0, Vec3 $$1) {
/*  638 */     this.onGround = $$0;
/*  639 */     checkSupportingBlock($$0, $$1);
/*      */   }
/*      */   
/*      */   public boolean isSupportedBy(BlockPos $$0) {
/*  643 */     return (this.mainSupportingBlockPos.isPresent() && ((BlockPos)this.mainSupportingBlockPos.get()).equals($$0));
/*      */   }
/*      */   
/*      */   protected void checkSupportingBlock(boolean $$0, @Nullable Vec3 $$1) {
/*  647 */     if ($$0) {
/*  648 */       AABB $$2 = getBoundingBox();
/*  649 */       AABB $$3 = new AABB($$2.minX, $$2.minY - 1.0E-6D, $$2.minZ, $$2.maxX, $$2.minY, $$2.maxZ);
/*  650 */       Optional<BlockPos> $$4 = this.level.findSupportingBlock(this, $$3);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  659 */       if ($$4.isPresent() || this.onGroundNoBlocks) {
/*  660 */         this.mainSupportingBlockPos = $$4;
/*  661 */       } else if ($$1 != null) {
/*      */ 
/*      */         
/*  664 */         AABB $$5 = $$3.move(-$$1.x, 0.0D, -$$1.z);
/*  665 */         $$4 = this.level.findSupportingBlock(this, $$5);
/*  666 */         this.mainSupportingBlockPos = $$4;
/*      */       } 
/*  668 */       this.onGroundNoBlocks = $$4.isEmpty();
/*      */     } else {
/*  670 */       this.onGroundNoBlocks = false;
/*  671 */       if (this.mainSupportingBlockPos.isPresent()) {
/*  672 */         this.mainSupportingBlockPos = Optional.empty();
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean onGround() {
/*  678 */     return this.onGround;
/*      */   }
/*      */   
/*      */   public void move(MoverType $$0, Vec3 $$1) {
/*  682 */     if (this.noPhysics) {
/*  683 */       setPos(getX() + $$1.x, getY() + $$1.y, getZ() + $$1.z);
/*      */       
/*      */       return;
/*      */     } 
/*  687 */     this.wasOnFire = isOnFire();
/*      */ 
/*      */     
/*  690 */     if ($$0 == MoverType.PISTON) {
/*  691 */       $$1 = limitPistonMovement($$1);
/*  692 */       if ($$1.equals(Vec3.ZERO)) {
/*      */         return;
/*      */       }
/*      */     } 
/*      */     
/*  697 */     level().getProfiler().push("move");
/*      */     
/*  699 */     if (this.stuckSpeedMultiplier.lengthSqr() > 1.0E-7D) {
/*  700 */       $$1 = $$1.multiply(this.stuckSpeedMultiplier);
/*  701 */       this.stuckSpeedMultiplier = Vec3.ZERO;
/*  702 */       setDeltaMovement(Vec3.ZERO);
/*      */     } 
/*      */     
/*  705 */     $$1 = maybeBackOffFromEdge($$1, $$0);
/*      */     
/*  707 */     Vec3 $$2 = collide($$1);
/*  708 */     double $$3 = $$2.lengthSqr();
/*  709 */     if ($$3 > 1.0E-7D) {
/*  710 */       if (this.fallDistance != 0.0F && $$3 >= 1.0D) {
/*      */         
/*  712 */         BlockHitResult $$4 = level().clip(new ClipContext(position(), position().add($$2), ClipContext.Block.FALLDAMAGE_RESETTING, ClipContext.Fluid.WATER, this));
/*  713 */         if ($$4.getType() != HitResult.Type.MISS) {
/*  714 */           resetFallDistance();
/*      */         }
/*      */       } 
/*  717 */       setPos(getX() + $$2.x, getY() + $$2.y, getZ() + $$2.z);
/*      */     } 
/*      */     
/*  720 */     level().getProfiler().pop();
/*  721 */     level().getProfiler().push("rest");
/*      */ 
/*      */     
/*  724 */     boolean $$5 = !Mth.equal($$1.x, $$2.x);
/*  725 */     boolean $$6 = !Mth.equal($$1.z, $$2.z);
/*  726 */     this.horizontalCollision = ($$5 || $$6);
/*  727 */     this.verticalCollision = ($$1.y != $$2.y);
/*  728 */     this.verticalCollisionBelow = (this.verticalCollision && $$1.y < 0.0D);
/*      */     
/*  730 */     if (this.horizontalCollision) {
/*  731 */       this.minorHorizontalCollision = isHorizontalCollisionMinor($$2);
/*      */     } else {
/*  733 */       this.minorHorizontalCollision = false;
/*      */     } 
/*      */     
/*  736 */     setOnGroundWithKnownMovement(this.verticalCollisionBelow, $$2);
/*      */     
/*  738 */     BlockPos $$7 = getOnPosLegacy();
/*  739 */     BlockState $$8 = level().getBlockState($$7);
/*      */     
/*  741 */     checkFallDamage($$2.y, onGround(), $$8, $$7);
/*  742 */     if (isRemoved()) {
/*  743 */       level().getProfiler().pop();
/*      */       
/*      */       return;
/*      */     } 
/*  747 */     if (this.horizontalCollision) {
/*  748 */       Vec3 $$9 = getDeltaMovement();
/*  749 */       setDeltaMovement($$5 ? 0.0D : $$9.x, $$9.y, $$6 ? 0.0D : $$9.z);
/*      */     } 
/*      */     
/*  752 */     Block $$10 = $$8.getBlock();
/*  753 */     if ($$1.y != $$2.y) {
/*  754 */       $$10.updateEntityAfterFallOn((BlockGetter)level(), this);
/*      */     }
/*      */     
/*  757 */     if (onGround()) {
/*  758 */       $$10.stepOn(level(), $$7, $$8, this);
/*      */     }
/*      */     
/*  761 */     MovementEmission $$11 = getMovementEmission();
/*  762 */     if ($$11.emitsAnything() && !isPassenger()) {
/*  763 */       double $$12 = $$2.x;
/*  764 */       double $$13 = $$2.y;
/*  765 */       double $$14 = $$2.z;
/*      */ 
/*      */       
/*  768 */       this.flyDist += (float)($$2.length() * 0.6D);
/*      */       
/*  770 */       BlockPos $$15 = getOnPos();
/*  771 */       BlockState $$16 = level().getBlockState($$15);
/*      */       
/*  773 */       boolean $$17 = isStateClimbable($$16);
/*  774 */       if (!$$17) {
/*  775 */         $$13 = 0.0D;
/*      */       }
/*      */       
/*  778 */       this.walkDist += (float)$$2.horizontalDistance() * 0.6F;
/*  779 */       this.moveDist += (float)Math.sqrt($$12 * $$12 + $$13 * $$13 + $$14 * $$14) * 0.6F;
/*  780 */       if (this.moveDist > this.nextStep && !$$16.isAir()) {
/*  781 */         boolean $$18 = $$15.equals($$7);
/*  782 */         boolean $$19 = vibrationAndSoundEffectsFromBlock($$7, $$8, $$11.emitsSounds(), $$18, $$1);
/*  783 */         if (!$$18) {
/*  784 */           $$19 |= vibrationAndSoundEffectsFromBlock($$15, $$16, false, $$11.emitsEvents(), $$1);
/*      */         }
/*  786 */         if ($$19) {
/*  787 */           this.nextStep = nextStep();
/*  788 */         } else if (isInWater()) {
/*  789 */           this.nextStep = nextStep();
/*  790 */           if ($$11.emitsSounds()) {
/*  791 */             waterSwimSound();
/*      */           }
/*  793 */           if ($$11.emitsEvents()) {
/*  794 */             gameEvent(GameEvent.SWIM);
/*      */           }
/*      */         } 
/*  797 */       } else if ($$16.isAir()) {
/*  798 */         processFlappingMovement();
/*      */       } 
/*      */     } 
/*      */     
/*  802 */     tryCheckInsideBlocks();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  807 */     float $$20 = getBlockSpeedFactor();
/*  808 */     setDeltaMovement(getDeltaMovement().multiply($$20, 1.0D, $$20));
/*      */     
/*  810 */     if (level().getBlockStatesIfLoaded(getBoundingBox().deflate(1.0E-6D)).noneMatch($$0 -> ($$0.is(BlockTags.FIRE) || $$0.is(Blocks.LAVA)))) {
/*  811 */       if (this.remainingFireTicks <= 0) {
/*  812 */         setRemainingFireTicks(-getFireImmuneTicks());
/*      */       }
/*      */       
/*  815 */       if (this.wasOnFire && (this.isInPowderSnow || isInWaterRainOrBubble())) {
/*  816 */         playEntityOnFireExtinguishedSound();
/*      */       }
/*      */     } 
/*      */     
/*  820 */     if (isOnFire() && (this.isInPowderSnow || isInWaterRainOrBubble())) {
/*  821 */       setRemainingFireTicks(-getFireImmuneTicks());
/*      */     }
/*      */     
/*  824 */     level().getProfiler().pop();
/*      */   }
/*      */   
/*      */   private boolean isStateClimbable(BlockState $$0) {
/*  828 */     return ($$0.is(BlockTags.CLIMBABLE) || $$0.is(Blocks.POWDER_SNOW));
/*      */   }
/*      */   
/*      */   private boolean vibrationAndSoundEffectsFromBlock(BlockPos $$0, BlockState $$1, boolean $$2, boolean $$3, Vec3 $$4) {
/*  832 */     if ($$1.isAir()) {
/*  833 */       return false;
/*      */     }
/*  835 */     boolean $$5 = isStateClimbable($$1);
/*  836 */     if ((onGround() || $$5 || (isCrouching() && $$4.y == 0.0D) || isOnRails()) && !isSwimming()) {
/*  837 */       if ($$2) {
/*  838 */         walkingStepSound($$0, $$1);
/*      */       }
/*  840 */       if ($$3) {
/*  841 */         level().gameEvent(GameEvent.STEP, position(), GameEvent.Context.of(this, $$1));
/*      */       }
/*  843 */       return true;
/*      */     } 
/*  845 */     return false;
/*      */   }
/*      */   
/*      */   protected boolean isHorizontalCollisionMinor(Vec3 $$0) {
/*  849 */     return false;
/*      */   }
/*      */   
/*      */   protected void tryCheckInsideBlocks() {
/*      */     try {
/*  854 */       checkInsideBlocks();
/*  855 */     } catch (Throwable $$0) {
/*  856 */       CrashReport $$1 = CrashReport.forThrowable($$0, "Checking entity block collision");
/*  857 */       CrashReportCategory $$2 = $$1.addCategory("Entity being checked for collision");
/*      */       
/*  859 */       fillCrashReportCategory($$2);
/*      */       
/*  861 */       throw new ReportedException($$1);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void playEntityOnFireExtinguishedSound() {
/*  866 */     playSound(SoundEvents.GENERIC_EXTINGUISH_FIRE, 0.7F, 1.6F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);
/*      */   }
/*      */   
/*      */   public void extinguishFire() {
/*  870 */     if (!(level()).isClientSide && this.wasOnFire) {
/*  871 */       playEntityOnFireExtinguishedSound();
/*      */     }
/*  873 */     clearFire();
/*      */   }
/*      */   
/*      */   protected void processFlappingMovement() {
/*  877 */     if (isFlapping()) {
/*  878 */       onFlap();
/*  879 */       if (getMovementEmission().emitsEvents()) {
/*  880 */         gameEvent(GameEvent.FLAP);
/*      */       }
/*      */     } 
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
/*      */   @Deprecated
/*      */   public BlockPos getOnPosLegacy() {
/*  897 */     return getOnPos(0.2F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected BlockPos getBlockPosBelowThatAffectsMyMovement() {
/*  908 */     return getOnPos(0.500001F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BlockPos getOnPos() {
/*  918 */     return getOnPos(1.0E-5F);
/*      */   }
/*      */   
/*      */   protected BlockPos getOnPos(float $$0) {
/*  922 */     if (this.mainSupportingBlockPos.isPresent()) {
/*  923 */       BlockPos $$1 = this.mainSupportingBlockPos.get();
/*  924 */       if ($$0 > 1.0E-5F) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  931 */         BlockState $$2 = level().getBlockState($$1);
/*      */         
/*  933 */         if (($$0 <= 0.5D && $$2.is(BlockTags.FENCES)) || $$2.is(BlockTags.WALLS) || $$2.getBlock() instanceof net.minecraft.world.level.block.FenceGateBlock) {
/*  934 */           return $$1;
/*      */         }
/*  936 */         return $$1.atY(Mth.floor(this.position.y - $$0));
/*      */       } 
/*  938 */       return $$1;
/*      */     } 
/*  940 */     int $$3 = Mth.floor(this.position.x);
/*  941 */     int $$4 = Mth.floor(this.position.y - $$0);
/*  942 */     int $$5 = Mth.floor(this.position.z);
/*      */     
/*  944 */     return new BlockPos($$3, $$4, $$5);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected float getBlockJumpFactor() {
/*  953 */     float $$0 = level().getBlockState(blockPosition()).getBlock().getJumpFactor();
/*  954 */     float $$1 = level().getBlockState(getBlockPosBelowThatAffectsMyMovement()).getBlock().getJumpFactor();
/*  955 */     return ($$0 == 1.0D) ? $$1 : $$0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected float getBlockSpeedFactor() {
/*  964 */     BlockState $$0 = level().getBlockState(blockPosition());
/*  965 */     float $$1 = $$0.getBlock().getSpeedFactor();
/*  966 */     if ($$0.is(Blocks.WATER) || $$0.is(Blocks.BUBBLE_COLUMN)) {
/*  967 */       return $$1;
/*      */     }
/*  969 */     return ($$1 == 1.0D) ? level().getBlockState(getBlockPosBelowThatAffectsMyMovement()).getBlock().getSpeedFactor() : $$1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Vec3 maybeBackOffFromEdge(Vec3 $$0, MoverType $$1) {
/*  976 */     return $$0;
/*      */   }
/*      */ 
/*      */   
/*      */   protected Vec3 limitPistonMovement(Vec3 $$0) {
/*  981 */     if ($$0.lengthSqr() <= 1.0E-7D) {
/*  982 */       return $$0;
/*      */     }
/*      */     
/*  985 */     long $$1 = level().getGameTime();
/*  986 */     if ($$1 != this.pistonDeltasGameTime) {
/*  987 */       Arrays.fill(this.pistonDeltas, 0.0D);
/*  988 */       this.pistonDeltasGameTime = $$1;
/*      */     } 
/*      */     
/*  991 */     if ($$0.x != 0.0D) {
/*  992 */       double $$2 = applyPistonMovementRestriction(Direction.Axis.X, $$0.x);
/*  993 */       return (Math.abs($$2) <= 9.999999747378752E-6D) ? Vec3.ZERO : new Vec3($$2, 0.0D, 0.0D);
/*      */     } 
/*  995 */     if ($$0.y != 0.0D) {
/*  996 */       double $$3 = applyPistonMovementRestriction(Direction.Axis.Y, $$0.y);
/*  997 */       return (Math.abs($$3) <= 9.999999747378752E-6D) ? Vec3.ZERO : new Vec3(0.0D, $$3, 0.0D);
/*      */     } 
/*  999 */     if ($$0.z != 0.0D) {
/* 1000 */       double $$4 = applyPistonMovementRestriction(Direction.Axis.Z, $$0.z);
/* 1001 */       return (Math.abs($$4) <= 9.999999747378752E-6D) ? Vec3.ZERO : new Vec3(0.0D, 0.0D, $$4);
/*      */     } 
/*      */     
/* 1004 */     return Vec3.ZERO;
/*      */   }
/*      */   
/*      */   private double applyPistonMovementRestriction(Direction.Axis $$0, double $$1) {
/* 1008 */     int $$2 = $$0.ordinal();
/* 1009 */     double $$3 = Mth.clamp($$1 + this.pistonDeltas[$$2], -0.51D, 0.51D);
/* 1010 */     $$1 = $$3 - this.pistonDeltas[$$2];
/* 1011 */     this.pistonDeltas[$$2] = $$3;
/* 1012 */     return $$1;
/*      */   }
/*      */   
/*      */   private Vec3 collide(Vec3 $$0) {
/* 1016 */     AABB $$1 = getBoundingBox();
/*      */ 
/*      */     
/* 1019 */     List<VoxelShape> $$2 = level().getEntityCollisions(this, $$1.expandTowards($$0));
/*      */     
/* 1021 */     Vec3 $$3 = ($$0.lengthSqr() == 0.0D) ? $$0 : collideBoundingBox(this, $$0, $$1, level(), $$2);
/*      */ 
/*      */     
/* 1024 */     boolean $$4 = ($$0.x != $$3.x);
/* 1025 */     boolean $$5 = ($$0.y != $$3.y);
/* 1026 */     boolean $$6 = ($$0.z != $$3.z);
/*      */     
/* 1028 */     boolean $$7 = (onGround() || ($$5 && $$0.y < 0.0D));
/* 1029 */     if (maxUpStep() > 0.0F && $$7 && ($$4 || $$6)) {
/* 1030 */       Vec3 $$8 = collideBoundingBox(this, new Vec3($$0.x, maxUpStep(), $$0.z), $$1, level(), $$2);
/*      */ 
/*      */       
/* 1033 */       Vec3 $$9 = collideBoundingBox(this, new Vec3(0.0D, maxUpStep(), 0.0D), $$1.expandTowards($$0.x, 0.0D, $$0.z), level(), $$2);
/* 1034 */       if ($$9.y < maxUpStep()) {
/* 1035 */         Vec3 $$10 = collideBoundingBox(this, new Vec3($$0.x, 0.0D, $$0.z), $$1.move($$9), level(), $$2).add($$9);
/*      */         
/* 1037 */         if ($$10.horizontalDistanceSqr() > $$8.horizontalDistanceSqr()) {
/* 1038 */           $$8 = $$10;
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 1043 */       if ($$8.horizontalDistanceSqr() > $$3.horizontalDistanceSqr()) {
/* 1044 */         return $$8.add(collideBoundingBox(this, new Vec3(0.0D, -$$8.y + $$0.y, 0.0D), $$1.move($$8), level(), $$2));
/*      */       }
/*      */     } 
/* 1047 */     return $$3;
/*      */   }
/*      */   
/*      */   public static Vec3 collideBoundingBox(@Nullable Entity $$0, Vec3 $$1, AABB $$2, Level $$3, List<VoxelShape> $$4) {
/* 1051 */     ImmutableList.Builder<VoxelShape> $$5 = ImmutableList.builderWithExpectedSize($$4.size() + 1);
/*      */     
/* 1053 */     if (!$$4.isEmpty()) {
/* 1054 */       $$5.addAll($$4);
/*      */     }
/*      */     
/* 1057 */     WorldBorder $$6 = $$3.getWorldBorder();
/* 1058 */     boolean $$7 = ($$0 != null && $$6.isInsideCloseToBorder($$0, $$2.expandTowards($$1)));
/*      */     
/* 1060 */     if ($$7) {
/* 1061 */       $$5.add($$6.getCollisionShape());
/*      */     }
/*      */     
/* 1064 */     $$5.addAll($$3.getBlockCollisions($$0, $$2.expandTowards($$1)));
/*      */     
/* 1066 */     return collideWithShapes($$1, $$2, (List<VoxelShape>)$$5.build());
/*      */   }
/*      */   
/*      */   private static Vec3 collideWithShapes(Vec3 $$0, AABB $$1, List<VoxelShape> $$2) {
/* 1070 */     if ($$2.isEmpty()) {
/* 1071 */       return $$0;
/*      */     }
/*      */     
/* 1074 */     double $$3 = $$0.x;
/* 1075 */     double $$4 = $$0.y;
/* 1076 */     double $$5 = $$0.z;
/*      */ 
/*      */     
/* 1079 */     if ($$4 != 0.0D) {
/* 1080 */       $$4 = Shapes.collide(Direction.Axis.Y, $$1, $$2, $$4);
/* 1081 */       if ($$4 != 0.0D) {
/* 1082 */         $$1 = $$1.move(0.0D, $$4, 0.0D);
/*      */       }
/*      */     } 
/*      */     
/* 1086 */     boolean $$6 = (Math.abs($$3) < Math.abs($$5));
/*      */     
/* 1088 */     if ($$6 && $$5 != 0.0D) {
/* 1089 */       $$5 = Shapes.collide(Direction.Axis.Z, $$1, $$2, $$5);
/* 1090 */       if ($$5 != 0.0D) {
/* 1091 */         $$1 = $$1.move(0.0D, 0.0D, $$5);
/*      */       }
/*      */     } 
/*      */     
/* 1095 */     if ($$3 != 0.0D) {
/* 1096 */       $$3 = Shapes.collide(Direction.Axis.X, $$1, $$2, $$3);
/* 1097 */       if (!$$6 && $$3 != 0.0D) {
/* 1098 */         $$1 = $$1.move($$3, 0.0D, 0.0D);
/*      */       }
/*      */     } 
/*      */     
/* 1102 */     if (!$$6 && $$5 != 0.0D) {
/* 1103 */       $$5 = Shapes.collide(Direction.Axis.Z, $$1, $$2, $$5);
/*      */     }
/*      */     
/* 1106 */     return new Vec3($$3, $$4, $$5);
/*      */   }
/*      */   
/*      */   protected float nextStep() {
/* 1110 */     return ((int)this.moveDist + 1);
/*      */   }
/*      */   
/*      */   protected SoundEvent getSwimSound() {
/* 1114 */     return SoundEvents.GENERIC_SWIM;
/*      */   }
/*      */   
/*      */   protected SoundEvent getSwimSplashSound() {
/* 1118 */     return SoundEvents.GENERIC_SPLASH;
/*      */   }
/*      */   
/*      */   protected SoundEvent getSwimHighSpeedSplashSound() {
/* 1122 */     return SoundEvents.GENERIC_SPLASH;
/*      */   }
/*      */   
/*      */   protected void checkInsideBlocks() {
/* 1126 */     AABB $$0 = getBoundingBox();
/* 1127 */     BlockPos $$1 = BlockPos.containing($$0.minX + 1.0E-7D, $$0.minY + 1.0E-7D, $$0.minZ + 1.0E-7D);
/* 1128 */     BlockPos $$2 = BlockPos.containing($$0.maxX - 1.0E-7D, $$0.maxY - 1.0E-7D, $$0.maxZ - 1.0E-7D);
/* 1129 */     if (level().hasChunksAt($$1, $$2)) {
/*      */       
/* 1131 */       BlockPos.MutableBlockPos $$3 = new BlockPos.MutableBlockPos();
/* 1132 */       for (int $$4 = $$1.getX(); $$4 <= $$2.getX(); $$4++) {
/* 1133 */         for (int $$5 = $$1.getY(); $$5 <= $$2.getY(); $$5++) {
/* 1134 */           for (int $$6 = $$1.getZ(); $$6 <= $$2.getZ(); $$6++) {
/* 1135 */             if (!isAlive()) {
/*      */               return;
/*      */             }
/* 1138 */             $$3.set($$4, $$5, $$6);
/* 1139 */             BlockState $$7 = level().getBlockState((BlockPos)$$3);
/*      */             
/*      */             try {
/* 1142 */               $$7.entityInside(level(), (BlockPos)$$3, this);
/* 1143 */               onInsideBlock($$7);
/* 1144 */             } catch (Throwable $$8) {
/* 1145 */               CrashReport $$9 = CrashReport.forThrowable($$8, "Colliding entity with block");
/* 1146 */               CrashReportCategory $$10 = $$9.addCategory("Block being collided with");
/*      */               
/* 1148 */               CrashReportCategory.populateBlockDetails($$10, (LevelHeightAccessor)level(), (BlockPos)$$3, $$7);
/*      */               
/* 1150 */               throw new ReportedException($$9);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void onInsideBlock(BlockState $$0) {}
/*      */   
/*      */   public void gameEvent(GameEvent $$0, @Nullable Entity $$1) {
/* 1162 */     level().gameEvent($$1, $$0, this.position);
/*      */   }
/*      */   
/*      */   public void gameEvent(GameEvent $$0) {
/* 1166 */     gameEvent($$0, this);
/*      */   }
/*      */   
/*      */   private void walkingStepSound(BlockPos $$0, BlockState $$1) {
/* 1170 */     playStepSound($$0, $$1);
/*      */     
/* 1172 */     if (shouldPlayAmethystStepSound($$1)) {
/* 1173 */       playAmethystStepSound();
/*      */     }
/*      */   }
/*      */   
/*      */   protected void waterSwimSound() {
/* 1178 */     Entity $$0 = Objects.<Entity>requireNonNullElse(getControllingPassenger(), this);
/* 1179 */     float $$1 = ($$0 == this) ? 0.35F : 0.4F;
/* 1180 */     Vec3 $$2 = $$0.getDeltaMovement();
/* 1181 */     float $$3 = Math.min(1.0F, (float)Math.sqrt($$2.x * $$2.x * 0.20000000298023224D + $$2.y * $$2.y + $$2.z * $$2.z * 0.20000000298023224D) * $$1);
/* 1182 */     playSwimSound($$3);
/*      */   }
/*      */   
/*      */   protected BlockPos getPrimaryStepSoundBlockPos(BlockPos $$0) {
/* 1186 */     BlockPos $$1 = $$0.above();
/* 1187 */     BlockState $$2 = level().getBlockState($$1);
/* 1188 */     if ($$2.is(BlockTags.INSIDE_STEP_SOUND_BLOCKS) || $$2.is(BlockTags.COMBINATION_STEP_SOUND_BLOCKS)) {
/* 1189 */       return $$1;
/*      */     }
/* 1191 */     return $$0;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void playCombinationStepSounds(BlockState $$0, BlockState $$1) {
/* 1196 */     SoundType $$2 = $$0.getSoundType();
/* 1197 */     playSound($$2.getStepSound(), $$2.getVolume() * 0.15F, $$2.getPitch());
/* 1198 */     playMuffledStepSound($$1);
/*      */   }
/*      */   
/*      */   protected void playMuffledStepSound(BlockState $$0) {
/* 1202 */     SoundType $$1 = $$0.getSoundType();
/* 1203 */     playSound($$1.getStepSound(), $$1.getVolume() * 0.05F, $$1.getPitch() * 0.8F);
/*      */   }
/*      */   
/*      */   protected void playStepSound(BlockPos $$0, BlockState $$1) {
/* 1207 */     SoundType $$2 = $$1.getSoundType();
/* 1208 */     playSound($$2.getStepSound(), $$2.getVolume() * 0.15F, $$2.getPitch());
/*      */   }
/*      */   
/*      */   private boolean shouldPlayAmethystStepSound(BlockState $$0) {
/* 1212 */     return ($$0.is(BlockTags.CRYSTAL_SOUND_BLOCKS) && this.tickCount >= this.lastCrystalSoundPlayTick + 20);
/*      */   }
/*      */ 
/*      */   
/*      */   private void playAmethystStepSound() {
/* 1217 */     this.crystalSoundIntensity *= (float)Math.pow(0.997D, (this.tickCount - this.lastCrystalSoundPlayTick));
/* 1218 */     this.crystalSoundIntensity = Math.min(1.0F, this.crystalSoundIntensity + 0.07F);
/*      */     
/* 1220 */     float $$0 = 0.5F + this.crystalSoundIntensity * this.random.nextFloat() * 1.2F;
/* 1221 */     float $$1 = 0.1F + this.crystalSoundIntensity * 1.2F;
/* 1222 */     playSound(SoundEvents.AMETHYST_BLOCK_CHIME, $$1, $$0);
/* 1223 */     this.lastCrystalSoundPlayTick = this.tickCount;
/*      */   }
/*      */   
/*      */   protected void playSwimSound(float $$0) {
/* 1227 */     playSound(getSwimSound(), $$0, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void onFlap() {}
/*      */   
/*      */   protected boolean isFlapping() {
/* 1234 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void playSound(SoundEvent $$0, float $$1, float $$2) {
/* 1242 */     if (!isSilent()) {
/* 1243 */       level().playSound(null, getX(), getY(), getZ(), $$0, getSoundSource(), $$1, $$2);
/*      */     }
/*      */   }
/*      */   
/*      */   public void playSound(SoundEvent $$0) {
/* 1248 */     if (!isSilent()) {
/* 1249 */       playSound($$0, 1.0F, 1.0F);
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean isSilent() {
/* 1254 */     return ((Boolean)this.entityData.get(DATA_SILENT)).booleanValue();
/*      */   }
/*      */   
/*      */   public void setSilent(boolean $$0) {
/* 1258 */     this.entityData.set(DATA_SILENT, Boolean.valueOf($$0));
/*      */   }
/*      */   
/*      */   public boolean isNoGravity() {
/* 1262 */     return ((Boolean)this.entityData.get(DATA_NO_GRAVITY)).booleanValue();
/*      */   }
/*      */   
/*      */   public void setNoGravity(boolean $$0) {
/* 1266 */     this.entityData.set(DATA_NO_GRAVITY, Boolean.valueOf($$0));
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
/*      */   protected MovementEmission getMovementEmission() {
/* 1280 */     return MovementEmission.ALL;
/*      */   }
/*      */   
/*      */   public boolean dampensVibrations() {
/* 1284 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void checkFallDamage(double $$0, boolean $$1, BlockState $$2, BlockPos $$3) {
/* 1289 */     if ($$1) {
/*      */       
/* 1291 */       if (this.fallDistance > 0.0F) {
/* 1292 */         $$2.getBlock().fallOn(level(), $$2, $$3, this, this.fallDistance);
/*      */ 
/*      */         
/* 1295 */         level().gameEvent(GameEvent.HIT_GROUND, this.position, GameEvent.Context.of(this, this.mainSupportingBlockPos.<BlockState>map($$0 -> level().getBlockState($$0)).orElse($$2)));
/*      */       } 
/* 1297 */       resetFallDistance();
/* 1298 */     } else if ($$0 < 0.0D) {
/* 1299 */       this.fallDistance -= (float)$$0;
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean fireImmune() {
/* 1304 */     return getType().fireImmune();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean causeFallDamage(float $$0, float $$1, DamageSource $$2) {
/* 1311 */     if (this.type.is(EntityTypeTags.FALL_DAMAGE_IMMUNE)) {
/* 1312 */       return false;
/*      */     }
/* 1314 */     if (isVehicle()) {
/* 1315 */       for (Entity $$3 : getPassengers()) {
/* 1316 */         $$3.causeFallDamage($$0, $$1, $$2);
/*      */       }
/*      */     }
/* 1319 */     return false;
/*      */   }
/*      */   
/*      */   public boolean isInWater() {
/* 1323 */     return this.wasTouchingWater;
/*      */   }
/*      */   
/*      */   private boolean isInRain() {
/* 1327 */     BlockPos $$0 = blockPosition();
/* 1328 */     return (level().isRainingAt($$0) || level().isRainingAt(BlockPos.containing($$0.getX(), (getBoundingBox()).maxY, $$0.getZ())));
/*      */   }
/*      */   
/*      */   private boolean isInBubbleColumn() {
/* 1332 */     return level().getBlockState(blockPosition()).is(Blocks.BUBBLE_COLUMN);
/*      */   }
/*      */   
/*      */   public boolean isInWaterOrRain() {
/* 1336 */     return (isInWater() || isInRain());
/*      */   }
/*      */   
/*      */   public boolean isInWaterRainOrBubble() {
/* 1340 */     return (isInWater() || isInRain() || isInBubbleColumn());
/*      */   }
/*      */   
/*      */   public boolean isInWaterOrBubble() {
/* 1344 */     return (isInWater() || isInBubbleColumn());
/*      */   }
/*      */   
/*      */   public boolean isInLiquid() {
/* 1348 */     return (isInWaterOrBubble() || isInLava());
/*      */   }
/*      */   
/*      */   public boolean isUnderWater() {
/* 1352 */     return (this.wasEyeInWater && isInWater());
/*      */   }
/*      */   
/*      */   public void updateSwimming() {
/* 1356 */     if (isSwimming()) {
/* 1357 */       setSwimming((isSprinting() && isInWater() && !isPassenger()));
/*      */     } else {
/* 1359 */       setSwimming((isSprinting() && isUnderWater() && !isPassenger() && level().getFluidState(this.blockPosition).is(FluidTags.WATER)));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean updateInWaterStateAndDoFluidPushing() {
/* 1367 */     this.fluidHeight.clear();
/* 1368 */     updateInWaterStateAndDoWaterCurrentPushing();
/* 1369 */     double $$0 = level().dimensionType().ultraWarm() ? 0.007D : 0.0023333333333333335D;
/* 1370 */     boolean $$1 = updateFluidHeightAndDoFluidPushing(FluidTags.LAVA, $$0);
/* 1371 */     return (isInWater() || $$1);
/*      */   }
/*      */   
/*      */   void updateInWaterStateAndDoWaterCurrentPushing() {
/* 1375 */     Entity entity = getVehicle(); if (entity instanceof Boat) { Boat $$0 = (Boat)entity; if (!$$0.isUnderWater())
/* 1376 */       { this.wasTouchingWater = false; return; }  }
/* 1377 */      if (updateFluidHeightAndDoFluidPushing(FluidTags.WATER, 0.014D)) {
/* 1378 */       if (!this.wasTouchingWater && !this.firstTick) {
/* 1379 */         doWaterSplashEffect();
/*      */       }
/* 1381 */       resetFallDistance();
/* 1382 */       this.wasTouchingWater = true;
/* 1383 */       clearFire();
/*      */     } else {
/* 1385 */       this.wasTouchingWater = false;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void updateFluidOnEyes() {
/* 1390 */     this.wasEyeInWater = isEyeInFluid(FluidTags.WATER);
/*      */     
/* 1392 */     this.fluidOnEyes.clear();
/* 1393 */     double $$0 = getEyeY() - 0.1111111119389534D;
/* 1394 */     Entity $$1 = getVehicle();
/* 1395 */     if ($$1 instanceof Boat) { Boat $$2 = (Boat)$$1;
/* 1396 */       if (!$$2.isUnderWater() && ($$2.getBoundingBox()).maxY >= $$0 && ($$2.getBoundingBox()).minY <= $$0) {
/*      */         return;
/*      */       } }
/*      */     
/* 1400 */     BlockPos $$3 = BlockPos.containing(getX(), $$0, getZ());
/* 1401 */     FluidState $$4 = level().getFluidState($$3);
/*      */     
/* 1403 */     double $$5 = ($$3.getY() + $$4.getHeight((BlockGetter)level(), $$3));
/* 1404 */     if ($$5 > $$0) {
/* 1405 */       Objects.requireNonNull(this.fluidOnEyes); $$4.getTags().forEach(this.fluidOnEyes::add);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void doWaterSplashEffect() {
/* 1410 */     Entity $$0 = Objects.<Entity>requireNonNullElse(getControllingPassenger(), this);
/* 1411 */     float $$1 = ($$0 == this) ? 0.2F : 0.9F;
/* 1412 */     Vec3 $$2 = $$0.getDeltaMovement();
/*      */ 
/*      */     
/* 1415 */     float $$3 = Math.min(1.0F, (float)Math.sqrt($$2.x * $$2.x * 0.20000000298023224D + $$2.y * $$2.y + $$2.z * $$2.z * 0.20000000298023224D) * $$1);
/*      */     
/* 1417 */     if ($$3 < 0.25F) {
/* 1418 */       playSound(getSwimSplashSound(), $$3, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);
/*      */     } else {
/* 1420 */       playSound(getSwimHighSpeedSplashSound(), $$3, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);
/*      */     } 
/*      */     
/* 1423 */     float $$4 = Mth.floor(getY());
/* 1424 */     for (int $$5 = 0; $$5 < 1.0F + this.dimensions.width * 20.0F; $$5++) {
/* 1425 */       double $$6 = (this.random.nextDouble() * 2.0D - 1.0D) * this.dimensions.width;
/* 1426 */       double $$7 = (this.random.nextDouble() * 2.0D - 1.0D) * this.dimensions.width;
/* 1427 */       level().addParticle((ParticleOptions)ParticleTypes.BUBBLE, getX() + $$6, ($$4 + 1.0F), getZ() + $$7, $$2.x, $$2.y - this.random.nextDouble() * 0.20000000298023224D, $$2.z);
/*      */     } 
/* 1429 */     for (int $$8 = 0; $$8 < 1.0F + this.dimensions.width * 20.0F; $$8++) {
/* 1430 */       double $$9 = (this.random.nextDouble() * 2.0D - 1.0D) * this.dimensions.width;
/* 1431 */       double $$10 = (this.random.nextDouble() * 2.0D - 1.0D) * this.dimensions.width;
/* 1432 */       level().addParticle((ParticleOptions)ParticleTypes.SPLASH, getX() + $$9, ($$4 + 1.0F), getZ() + $$10, $$2.x, $$2.y, $$2.z);
/*      */     } 
/*      */     
/* 1435 */     gameEvent(GameEvent.SPLASH);
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
/*      */   @Deprecated
/*      */   protected BlockState getBlockStateOnLegacy() {
/* 1449 */     return level().getBlockState(getOnPosLegacy());
/*      */   }
/*      */   
/*      */   public BlockState getBlockStateOn() {
/* 1453 */     return level().getBlockState(getOnPos());
/*      */   }
/*      */   
/*      */   public boolean canSpawnSprintParticle() {
/* 1457 */     return (isSprinting() && !isInWater() && !isSpectator() && !isCrouching() && !isInLava() && isAlive());
/*      */   }
/*      */   
/*      */   protected void spawnSprintParticle() {
/* 1461 */     BlockPos $$0 = getOnPosLegacy();
/* 1462 */     BlockState $$1 = level().getBlockState($$0);
/* 1463 */     if ($$1.getRenderShape() != RenderShape.INVISIBLE) {
/* 1464 */       Vec3 $$2 = getDeltaMovement();
/* 1465 */       BlockPos $$3 = blockPosition();
/* 1466 */       double $$4 = getX() + (this.random.nextDouble() - 0.5D) * this.dimensions.width;
/* 1467 */       double $$5 = getZ() + (this.random.nextDouble() - 0.5D) * this.dimensions.width;
/* 1468 */       if ($$3.getX() != $$0.getX())
/*      */       {
/* 1470 */         $$4 = Mth.clamp($$4, $$0.getX(), $$0.getX() + 1.0D);
/*      */       }
/* 1472 */       if ($$3.getZ() != $$0.getZ())
/*      */       {
/* 1474 */         $$5 = Mth.clamp($$5, $$0.getZ(), $$0.getZ() + 1.0D);
/*      */       }
/* 1476 */       level().addParticle((ParticleOptions)new BlockParticleOption(ParticleTypes.BLOCK, $$1), $$4, getY() + 0.1D, $$5, $$2.x * -4.0D, 1.5D, $$2.z * -4.0D);
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean isEyeInFluid(TagKey<Fluid> $$0) {
/* 1481 */     return this.fluidOnEyes.contains($$0);
/*      */   }
/*      */   
/*      */   public boolean isInLava() {
/* 1485 */     return (!this.firstTick && this.fluidHeight.getDouble(FluidTags.LAVA) > 0.0D);
/*      */   }
/*      */   
/*      */   public void moveRelative(float $$0, Vec3 $$1) {
/* 1489 */     Vec3 $$2 = getInputVector($$1, $$0, getYRot());
/*      */     
/* 1491 */     setDeltaMovement(getDeltaMovement().add($$2));
/*      */   }
/*      */   
/*      */   private static Vec3 getInputVector(Vec3 $$0, float $$1, float $$2) {
/* 1495 */     double $$3 = $$0.lengthSqr();
/* 1496 */     if ($$3 < 1.0E-7D) {
/* 1497 */       return Vec3.ZERO;
/*      */     }
/*      */     
/* 1500 */     Vec3 $$4 = (($$3 > 1.0D) ? $$0.normalize() : $$0).scale($$1);
/*      */     
/* 1502 */     float $$5 = Mth.sin($$2 * 0.017453292F);
/* 1503 */     float $$6 = Mth.cos($$2 * 0.017453292F);
/* 1504 */     return new Vec3($$4.x * $$6 - $$4.z * $$5, $$4.y, $$4.z * $$6 + $$4.x * $$5);
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public float getLightLevelDependentMagicValue() {
/* 1510 */     if (level().hasChunkAt(getBlockX(), getBlockZ())) {
/* 1511 */       return level().getLightLevelDependentMagicValue(BlockPos.containing(getX(), getEyeY(), getZ()));
/*      */     }
/* 1513 */     return 0.0F;
/*      */   }
/*      */   
/*      */   public void absMoveTo(double $$0, double $$1, double $$2, float $$3, float $$4) {
/* 1517 */     absMoveTo($$0, $$1, $$2);
/*      */     
/* 1519 */     setYRot($$3 % 360.0F);
/* 1520 */     setXRot(Mth.clamp($$4, -90.0F, 90.0F) % 360.0F);
/*      */     
/* 1522 */     this.yRotO = getYRot();
/* 1523 */     this.xRotO = getXRot();
/*      */   }
/*      */   
/*      */   public void absMoveTo(double $$0, double $$1, double $$2) {
/* 1527 */     double $$3 = Mth.clamp($$0, -3.0E7D, 3.0E7D);
/* 1528 */     double $$4 = Mth.clamp($$2, -3.0E7D, 3.0E7D);
/*      */     
/* 1530 */     this.xo = $$3;
/* 1531 */     this.yo = $$1;
/* 1532 */     this.zo = $$4;
/*      */     
/* 1534 */     setPos($$3, $$1, $$4);
/*      */   }
/*      */   
/*      */   public void moveTo(Vec3 $$0) {
/* 1538 */     moveTo($$0.x, $$0.y, $$0.z);
/*      */   }
/*      */   
/*      */   public void moveTo(double $$0, double $$1, double $$2) {
/* 1542 */     moveTo($$0, $$1, $$2, getYRot(), getXRot());
/*      */   }
/*      */   
/*      */   public void moveTo(BlockPos $$0, float $$1, float $$2) {
/* 1546 */     moveTo($$0.getX() + 0.5D, $$0.getY(), $$0.getZ() + 0.5D, $$1, $$2);
/*      */   }
/*      */   
/*      */   public void moveTo(double $$0, double $$1, double $$2, float $$3, float $$4) {
/* 1550 */     setPosRaw($$0, $$1, $$2);
/* 1551 */     setYRot($$3);
/* 1552 */     setXRot($$4);
/*      */     
/* 1554 */     setOldPosAndRot();
/*      */     
/* 1556 */     reapplyPosition();
/*      */   }
/*      */   
/*      */   public final void setOldPosAndRot() {
/* 1560 */     double $$0 = getX();
/* 1561 */     double $$1 = getY();
/* 1562 */     double $$2 = getZ();
/* 1563 */     this.xo = $$0;
/* 1564 */     this.yo = $$1;
/* 1565 */     this.zo = $$2;
/* 1566 */     this.xOld = $$0;
/* 1567 */     this.yOld = $$1;
/* 1568 */     this.zOld = $$2;
/* 1569 */     this.yRotO = getYRot();
/* 1570 */     this.xRotO = getXRot();
/*      */   }
/*      */   
/*      */   public float distanceTo(Entity $$0) {
/* 1574 */     float $$1 = (float)(getX() - $$0.getX());
/* 1575 */     float $$2 = (float)(getY() - $$0.getY());
/* 1576 */     float $$3 = (float)(getZ() - $$0.getZ());
/* 1577 */     return Mth.sqrt($$1 * $$1 + $$2 * $$2 + $$3 * $$3);
/*      */   }
/*      */   
/*      */   public double distanceToSqr(double $$0, double $$1, double $$2) {
/* 1581 */     double $$3 = getX() - $$0;
/* 1582 */     double $$4 = getY() - $$1;
/* 1583 */     double $$5 = getZ() - $$2;
/* 1584 */     return $$3 * $$3 + $$4 * $$4 + $$5 * $$5;
/*      */   }
/*      */   
/*      */   public double distanceToSqr(Entity $$0) {
/* 1588 */     return distanceToSqr($$0.position());
/*      */   }
/*      */   
/*      */   public double distanceToSqr(Vec3 $$0) {
/* 1592 */     double $$1 = getX() - $$0.x;
/* 1593 */     double $$2 = getY() - $$0.y;
/* 1594 */     double $$3 = getZ() - $$0.z;
/* 1595 */     return $$1 * $$1 + $$2 * $$2 + $$3 * $$3;
/*      */   }
/*      */ 
/*      */   
/*      */   public void playerTouch(Player $$0) {}
/*      */   
/*      */   public void push(Entity $$0) {
/* 1602 */     if (isPassengerOfSameVehicle($$0)) {
/*      */       return;
/*      */     }
/* 1605 */     if ($$0.noPhysics || this.noPhysics) {
/*      */       return;
/*      */     }
/*      */     
/* 1609 */     double $$1 = $$0.getX() - getX();
/* 1610 */     double $$2 = $$0.getZ() - getZ();
/*      */     
/* 1612 */     double $$3 = Mth.absMax($$1, $$2);
/*      */     
/* 1614 */     if ($$3 >= 0.009999999776482582D) {
/* 1615 */       $$3 = Math.sqrt($$3);
/* 1616 */       $$1 /= $$3;
/* 1617 */       $$2 /= $$3;
/*      */       
/* 1619 */       double $$4 = 1.0D / $$3;
/* 1620 */       if ($$4 > 1.0D) {
/* 1621 */         $$4 = 1.0D;
/*      */       }
/* 1623 */       $$1 *= $$4;
/* 1624 */       $$2 *= $$4;
/*      */       
/* 1626 */       $$1 *= 0.05000000074505806D;
/* 1627 */       $$2 *= 0.05000000074505806D;
/*      */       
/* 1629 */       if (!isVehicle() && isPushable()) {
/* 1630 */         push(-$$1, 0.0D, -$$2);
/*      */       }
/* 1632 */       if (!$$0.isVehicle() && $$0.isPushable()) {
/* 1633 */         $$0.push($$1, 0.0D, $$2);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public void push(double $$0, double $$1, double $$2) {
/* 1639 */     setDeltaMovement(getDeltaMovement().add($$0, $$1, $$2));
/* 1640 */     this.hasImpulse = true;
/*      */   }
/*      */   
/*      */   protected void markHurt() {
/* 1644 */     this.hurtMarked = true;
/*      */   }
/*      */   
/*      */   public boolean hurt(DamageSource $$0, float $$1) {
/* 1648 */     if (isInvulnerableTo($$0)) {
/* 1649 */       return false;
/*      */     }
/* 1651 */     markHurt();
/*      */ 
/*      */     
/* 1654 */     return false;
/*      */   }
/*      */   
/*      */   public final Vec3 getViewVector(float $$0) {
/* 1658 */     return calculateViewVector(getViewXRot($$0), getViewYRot($$0));
/*      */   }
/*      */   
/*      */   public float getViewXRot(float $$0) {
/* 1662 */     if ($$0 == 1.0F) {
/* 1663 */       return getXRot();
/*      */     }
/* 1665 */     return Mth.lerp($$0, this.xRotO, getXRot());
/*      */   }
/*      */   
/*      */   public float getViewYRot(float $$0) {
/* 1669 */     if ($$0 == 1.0F) {
/* 1670 */       return getYRot();
/*      */     }
/* 1672 */     return Mth.lerp($$0, this.yRotO, getYRot());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected final Vec3 calculateViewVector(float $$0, float $$1) {
/* 1678 */     float $$2 = $$0 * 0.017453292F;
/* 1679 */     float $$3 = -$$1 * 0.017453292F;
/*      */     
/* 1681 */     float $$4 = Mth.cos($$3);
/* 1682 */     float $$5 = Mth.sin($$3);
/* 1683 */     float $$6 = Mth.cos($$2);
/* 1684 */     float $$7 = Mth.sin($$2);
/*      */     
/* 1686 */     return new Vec3(($$5 * $$6), -$$7, ($$4 * $$6));
/*      */   }
/*      */   
/*      */   public final Vec3 getUpVector(float $$0) {
/* 1690 */     return calculateUpVector(getViewXRot($$0), getViewYRot($$0));
/*      */   }
/*      */   
/*      */   protected final Vec3 calculateUpVector(float $$0, float $$1) {
/* 1694 */     return calculateViewVector($$0 - 90.0F, $$1);
/*      */   }
/*      */   
/*      */   public final Vec3 getEyePosition() {
/* 1698 */     return new Vec3(getX(), getEyeY(), getZ());
/*      */   }
/*      */   
/*      */   public final Vec3 getEyePosition(float $$0) {
/* 1702 */     double $$1 = Mth.lerp($$0, this.xo, getX());
/* 1703 */     double $$2 = Mth.lerp($$0, this.yo, getY()) + getEyeHeight();
/* 1704 */     double $$3 = Mth.lerp($$0, this.zo, getZ());
/*      */     
/* 1706 */     return new Vec3($$1, $$2, $$3);
/*      */   }
/*      */   
/*      */   public Vec3 getLightProbePosition(float $$0) {
/* 1710 */     return getEyePosition($$0);
/*      */   }
/*      */   
/*      */   public final Vec3 getPosition(float $$0) {
/* 1714 */     double $$1 = Mth.lerp($$0, this.xo, getX());
/* 1715 */     double $$2 = Mth.lerp($$0, this.yo, getY());
/* 1716 */     double $$3 = Mth.lerp($$0, this.zo, getZ());
/* 1717 */     return new Vec3($$1, $$2, $$3);
/*      */   }
/*      */   
/*      */   public HitResult pick(double $$0, float $$1, boolean $$2) {
/* 1721 */     Vec3 $$3 = getEyePosition($$1);
/* 1722 */     Vec3 $$4 = getViewVector($$1);
/* 1723 */     Vec3 $$5 = $$3.add($$4.x * $$0, $$4.y * $$0, $$4.z * $$0);
/* 1724 */     return (HitResult)level().clip(new ClipContext($$3, $$5, ClipContext.Block.OUTLINE, $$2 ? ClipContext.Fluid.ANY : ClipContext.Fluid.NONE, this));
/*      */   }
/*      */   
/*      */   public boolean canBeHitByProjectile() {
/* 1728 */     return (isAlive() && isPickable());
/*      */   }
/*      */   
/*      */   public boolean isPickable() {
/* 1732 */     return false;
/*      */   }
/*      */   
/*      */   public boolean isPushable() {
/* 1736 */     return false;
/*      */   }
/*      */   
/*      */   public void awardKillScore(Entity $$0, int $$1, DamageSource $$2) {
/* 1740 */     if ($$0 instanceof ServerPlayer) {
/* 1741 */       CriteriaTriggers.ENTITY_KILLED_PLAYER.trigger((ServerPlayer)$$0, this, $$2);
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean shouldRender(double $$0, double $$1, double $$2) {
/* 1746 */     double $$3 = getX() - $$0;
/* 1747 */     double $$4 = getY() - $$1;
/* 1748 */     double $$5 = getZ() - $$2;
/* 1749 */     double $$6 = $$3 * $$3 + $$4 * $$4 + $$5 * $$5;
/* 1750 */     return shouldRenderAtSqrDistance($$6);
/*      */   }
/*      */   
/*      */   public boolean shouldRenderAtSqrDistance(double $$0) {
/* 1754 */     double $$1 = getBoundingBox().getSize();
/* 1755 */     if (Double.isNaN($$1)) {
/* 1756 */       $$1 = 1.0D;
/*      */     }
/* 1758 */     $$1 *= 64.0D * viewScale;
/* 1759 */     return ($$0 < $$1 * $$1);
/*      */   }
/*      */   
/*      */   public boolean saveAsPassenger(CompoundTag $$0) {
/* 1763 */     if (this.removalReason != null && !this.removalReason.shouldSave()) {
/* 1764 */       return false;
/*      */     }
/* 1766 */     String $$1 = getEncodeId();
/* 1767 */     if ($$1 == null) {
/* 1768 */       return false;
/*      */     }
/* 1770 */     $$0.putString("id", $$1);
/* 1771 */     saveWithoutId($$0);
/* 1772 */     return true;
/*      */   }
/*      */   
/*      */   public boolean save(CompoundTag $$0) {
/* 1776 */     if (isPassenger()) {
/* 1777 */       return false;
/*      */     }
/* 1779 */     return saveAsPassenger($$0);
/*      */   }
/*      */   
/*      */   public CompoundTag saveWithoutId(CompoundTag $$0) {
/*      */     try {
/* 1784 */       if (this.vehicle != null) {
/*      */         
/* 1786 */         $$0.put("Pos", (Tag)newDoubleList(new double[] { this.vehicle.getX(), getY(), this.vehicle.getZ() }));
/*      */       } else {
/* 1788 */         $$0.put("Pos", (Tag)newDoubleList(new double[] { getX(), getY(), getZ() }));
/*      */       } 
/*      */       
/* 1791 */       Vec3 $$1 = getDeltaMovement();
/* 1792 */       $$0.put("Motion", (Tag)newDoubleList(new double[] { $$1.x, $$1.y, $$1.z }));
/* 1793 */       $$0.put("Rotation", (Tag)newFloatList(new float[] { getYRot(), getXRot() }));
/*      */       
/* 1795 */       $$0.putFloat("FallDistance", this.fallDistance);
/* 1796 */       $$0.putShort("Fire", (short)this.remainingFireTicks);
/* 1797 */       $$0.putShort("Air", (short)getAirSupply());
/* 1798 */       $$0.putBoolean("OnGround", onGround());
/* 1799 */       $$0.putBoolean("Invulnerable", this.invulnerable);
/* 1800 */       $$0.putInt("PortalCooldown", this.portalCooldown);
/*      */       
/* 1802 */       $$0.putUUID("UUID", getUUID());
/*      */       
/* 1804 */       Component $$2 = getCustomName();
/* 1805 */       if ($$2 != null) {
/* 1806 */         $$0.putString("CustomName", Component.Serializer.toJson($$2));
/*      */       }
/* 1808 */       if (isCustomNameVisible()) {
/* 1809 */         $$0.putBoolean("CustomNameVisible", isCustomNameVisible());
/*      */       }
/* 1811 */       if (isSilent()) {
/* 1812 */         $$0.putBoolean("Silent", isSilent());
/*      */       }
/* 1814 */       if (isNoGravity()) {
/* 1815 */         $$0.putBoolean("NoGravity", isNoGravity());
/*      */       }
/* 1817 */       if (this.hasGlowingTag) {
/* 1818 */         $$0.putBoolean("Glowing", true);
/*      */       }
/* 1820 */       int $$3 = getTicksFrozen();
/* 1821 */       if ($$3 > 0) {
/* 1822 */         $$0.putInt("TicksFrozen", getTicksFrozen());
/*      */       }
/* 1824 */       if (this.hasVisualFire) {
/* 1825 */         $$0.putBoolean("HasVisualFire", this.hasVisualFire);
/*      */       }
/* 1827 */       if (!this.tags.isEmpty()) {
/* 1828 */         ListTag $$4 = new ListTag();
/* 1829 */         for (String $$5 : this.tags) {
/* 1830 */           $$4.add(StringTag.valueOf($$5));
/*      */         }
/* 1832 */         $$0.put("Tags", (Tag)$$4);
/*      */       } 
/*      */       
/* 1835 */       addAdditionalSaveData($$0);
/*      */       
/* 1837 */       if (isVehicle()) {
/* 1838 */         ListTag $$6 = new ListTag();
/* 1839 */         for (Entity $$7 : getPassengers()) {
/* 1840 */           CompoundTag $$8 = new CompoundTag();
/* 1841 */           if ($$7.saveAsPassenger($$8)) {
/* 1842 */             $$6.add($$8);
/*      */           }
/*      */         } 
/* 1845 */         if (!$$6.isEmpty()) {
/* 1846 */           $$0.put("Passengers", (Tag)$$6);
/*      */         }
/*      */       } 
/* 1849 */     } catch (Throwable $$9) {
/* 1850 */       CrashReport $$10 = CrashReport.forThrowable($$9, "Saving entity NBT");
/* 1851 */       CrashReportCategory $$11 = $$10.addCategory("Entity being saved");
/* 1852 */       fillCrashReportCategory($$11);
/* 1853 */       throw new ReportedException($$10);
/*      */     } 
/*      */     
/* 1856 */     return $$0;
/*      */   }
/*      */   
/*      */   public void load(CompoundTag $$0) {
/*      */     try {
/* 1861 */       ListTag $$1 = $$0.getList("Pos", 6);
/* 1862 */       ListTag $$2 = $$0.getList("Motion", 6);
/* 1863 */       ListTag $$3 = $$0.getList("Rotation", 5);
/*      */       
/* 1865 */       double $$4 = $$2.getDouble(0);
/* 1866 */       double $$5 = $$2.getDouble(1);
/* 1867 */       double $$6 = $$2.getDouble(2);
/*      */ 
/*      */       
/* 1870 */       setDeltaMovement(
/* 1871 */           (Math.abs($$4) > 10.0D) ? 0.0D : $$4, 
/* 1872 */           (Math.abs($$5) > 10.0D) ? 0.0D : $$5, 
/* 1873 */           (Math.abs($$6) > 10.0D) ? 0.0D : $$6);
/*      */ 
/*      */       
/* 1876 */       double $$7 = 3.0000512E7D;
/* 1877 */       setPosRaw(
/* 1878 */           Mth.clamp($$1.getDouble(0), -3.0000512E7D, 3.0000512E7D), 
/* 1879 */           Mth.clamp($$1.getDouble(1), -2.0E7D, 2.0E7D), 
/* 1880 */           Mth.clamp($$1.getDouble(2), -3.0000512E7D, 3.0000512E7D));
/*      */       
/* 1882 */       setYRot($$3.getFloat(0));
/* 1883 */       setXRot($$3.getFloat(1));
/*      */       
/* 1885 */       setOldPosAndRot();
/*      */       
/* 1887 */       setYHeadRot(getYRot());
/* 1888 */       setYBodyRot(getYRot());
/*      */       
/* 1890 */       this.fallDistance = $$0.getFloat("FallDistance");
/* 1891 */       this.remainingFireTicks = $$0.getShort("Fire");
/* 1892 */       if ($$0.contains("Air")) {
/* 1893 */         setAirSupply($$0.getShort("Air"));
/*      */       }
/*      */       
/* 1896 */       this.onGround = $$0.getBoolean("OnGround");
/* 1897 */       this.invulnerable = $$0.getBoolean("Invulnerable");
/* 1898 */       this.portalCooldown = $$0.getInt("PortalCooldown");
/*      */       
/* 1900 */       if ($$0.hasUUID("UUID")) {
/* 1901 */         this.uuid = $$0.getUUID("UUID");
/* 1902 */         this.stringUUID = this.uuid.toString();
/*      */       } 
/*      */       
/* 1905 */       if (!Double.isFinite(getX()) || !Double.isFinite(getY()) || !Double.isFinite(getZ())) {
/* 1906 */         throw new IllegalStateException("Entity has invalid position");
/*      */       }
/* 1908 */       if (!Double.isFinite(getYRot()) || !Double.isFinite(getXRot())) {
/* 1909 */         throw new IllegalStateException("Entity has invalid rotation");
/*      */       }
/*      */       
/* 1912 */       reapplyPosition();
/* 1913 */       setRot(getYRot(), getXRot());
/*      */       
/* 1915 */       if ($$0.contains("CustomName", 8)) {
/* 1916 */         String $$8 = $$0.getString("CustomName");
/*      */         try {
/* 1918 */           setCustomName((Component)Component.Serializer.fromJson($$8));
/* 1919 */         } catch (Exception $$9) {
/* 1920 */           LOGGER.warn("Failed to parse entity custom name {}", $$8, $$9);
/*      */         } 
/*      */       } 
/* 1923 */       setCustomNameVisible($$0.getBoolean("CustomNameVisible"));
/* 1924 */       setSilent($$0.getBoolean("Silent"));
/* 1925 */       setNoGravity($$0.getBoolean("NoGravity"));
/* 1926 */       setGlowingTag($$0.getBoolean("Glowing"));
/* 1927 */       setTicksFrozen($$0.getInt("TicksFrozen"));
/* 1928 */       this.hasVisualFire = $$0.getBoolean("HasVisualFire");
/*      */       
/* 1930 */       if ($$0.contains("Tags", 9)) {
/* 1931 */         this.tags.clear();
/* 1932 */         ListTag $$10 = $$0.getList("Tags", 8);
/* 1933 */         int $$11 = Math.min($$10.size(), 1024);
/* 1934 */         for (int $$12 = 0; $$12 < $$11; $$12++) {
/* 1935 */           this.tags.add($$10.getString($$12));
/*      */         }
/*      */       } 
/*      */       
/* 1939 */       readAdditionalSaveData($$0);
/*      */       
/* 1941 */       if (repositionEntityAfterLoad()) {
/* 1942 */         reapplyPosition();
/*      */       }
/* 1944 */     } catch (Throwable $$13) {
/* 1945 */       CrashReport $$14 = CrashReport.forThrowable($$13, "Loading entity NBT");
/* 1946 */       CrashReportCategory $$15 = $$14.addCategory("Entity being loaded");
/* 1947 */       fillCrashReportCategory($$15);
/* 1948 */       throw new ReportedException($$14);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected boolean repositionEntityAfterLoad() {
/* 1953 */     return true;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   protected final String getEncodeId() {
/* 1958 */     EntityType<?> $$0 = getType();
/* 1959 */     ResourceLocation $$1 = EntityType.getKey($$0);
/* 1960 */     return (!$$0.canSerialize() || $$1 == null) ? null : $$1.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ListTag newDoubleList(double... $$0) {
/* 1968 */     ListTag $$1 = new ListTag();
/* 1969 */     for (double $$2 : $$0) {
/* 1970 */       $$1.add(DoubleTag.valueOf($$2));
/*      */     }
/* 1972 */     return $$1;
/*      */   }
/*      */   
/*      */   protected ListTag newFloatList(float... $$0) {
/* 1976 */     ListTag $$1 = new ListTag();
/* 1977 */     for (float $$2 : $$0) {
/* 1978 */       $$1.add(FloatTag.valueOf($$2));
/*      */     }
/* 1980 */     return $$1;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public ItemEntity spawnAtLocation(ItemLike $$0) {
/* 1985 */     return spawnAtLocation($$0, 0);
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public ItemEntity spawnAtLocation(ItemLike $$0, int $$1) {
/* 1990 */     return spawnAtLocation(new ItemStack($$0), $$1);
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public ItemEntity spawnAtLocation(ItemStack $$0) {
/* 1995 */     return spawnAtLocation($$0, 0.0F);
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public ItemEntity spawnAtLocation(ItemStack $$0, float $$1) {
/* 2000 */     if ($$0.isEmpty()) {
/* 2001 */       return null;
/*      */     }
/*      */     
/* 2004 */     if ((level()).isClientSide) {
/* 2005 */       return null;
/*      */     }
/* 2007 */     ItemEntity $$2 = new ItemEntity(level(), getX(), getY() + $$1, getZ(), $$0);
/* 2008 */     $$2.setDefaultPickUpDelay();
/* 2009 */     level().addFreshEntity((Entity)$$2);
/* 2010 */     return $$2;
/*      */   }
/*      */   
/*      */   public boolean isAlive() {
/* 2014 */     return !isRemoved();
/*      */   }
/*      */   
/*      */   public boolean isInWall() {
/* 2018 */     if (this.noPhysics) {
/* 2019 */       return false;
/*      */     }
/*      */     
/* 2022 */     float $$0 = this.dimensions.width * 0.8F;
/* 2023 */     AABB $$1 = AABB.ofSize(getEyePosition(), $$0, 1.0E-6D, $$0);
/* 2024 */     return BlockPos.betweenClosedStream($$1)
/* 2025 */       .anyMatch($$1 -> {
/*      */           BlockState $$2 = level().getBlockState($$1);
/* 2027 */           return (!$$2.isAir() && $$2.isSuffocating((BlockGetter)level(), $$1) && Shapes.joinIsNotEmpty($$2.getCollisionShape((BlockGetter)level(), $$1).move($$1.getX(), $$1.getY(), $$1.getZ()), Shapes.create($$0), BooleanOp.AND));
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public InteractionResult interact(Player $$0, InteractionHand $$1) {
/* 2035 */     return InteractionResult.PASS;
/*      */   }
/*      */   
/*      */   public boolean canCollideWith(Entity $$0) {
/* 2039 */     return ($$0.canBeCollidedWith() && !isPassengerOfSameVehicle($$0));
/*      */   }
/*      */   
/*      */   public boolean canBeCollidedWith() {
/* 2043 */     return false;
/*      */   }
/*      */   
/*      */   public void rideTick() {
/* 2047 */     setDeltaMovement(Vec3.ZERO);
/* 2048 */     tick();
/* 2049 */     if (!isPassenger()) {
/*      */       return;
/*      */     }
/*      */     
/* 2053 */     getVehicle().positionRider(this);
/*      */   }
/*      */   
/*      */   public final void positionRider(Entity $$0) {
/* 2057 */     if (!hasPassenger($$0)) {
/*      */       return;
/*      */     }
/* 2060 */     positionRider($$0, Entity::setPos);
/*      */   }
/*      */   
/*      */   protected void positionRider(Entity $$0, MoveFunction $$1) {
/* 2064 */     Vec3 $$2 = getPassengerRidingPosition($$0);
/* 2065 */     $$1.accept($$0, $$2.x, $$2.y + $$0.getMyRidingOffset(this), $$2.z);
/*      */   }
/*      */ 
/*      */   
/*      */   public void onPassengerTurned(Entity $$0) {}
/*      */   
/*      */   public float getMyRidingOffset(Entity $$0) {
/* 2072 */     return ridingOffset($$0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected float ridingOffset(Entity $$0) {
/* 2081 */     return 0.0F;
/*      */   }
/*      */   
/*      */   public Vec3 getPassengerRidingPosition(Entity $$0) {
/* 2085 */     return (new Vec3(getPassengerAttachmentPoint($$0, this.dimensions, 1.0F)
/* 2086 */         .rotateY(-this.yRot * 0.017453292F)))
/* 2087 */       .add(position());
/*      */   }
/*      */   
/*      */   protected Vector3f getPassengerAttachmentPoint(Entity $$0, EntityDimensions $$1, float $$2) {
/* 2091 */     return new Vector3f(0.0F, $$1.height, 0.0F);
/*      */   }
/*      */   
/*      */   public boolean startRiding(Entity $$0) {
/* 2095 */     return startRiding($$0, false);
/*      */   }
/*      */   
/*      */   public boolean showVehicleHealth() {
/* 2099 */     return this instanceof LivingEntity;
/*      */   }
/*      */   
/*      */   public boolean startRiding(Entity $$0, boolean $$1) {
/* 2103 */     if ($$0 == this.vehicle) {
/* 2104 */       return false;
/*      */     }
/* 2106 */     if (!$$0.couldAcceptPassenger()) {
/* 2107 */       return false;
/*      */     }
/* 2109 */     Entity $$2 = $$0;
/* 2110 */     while ($$2.vehicle != null) {
/* 2111 */       if ($$2.vehicle == this) {
/* 2112 */         return false;
/*      */       }
/*      */       
/* 2115 */       $$2 = $$2.vehicle;
/*      */     } 
/*      */     
/* 2118 */     if (!$$1 && (!canRide($$0) || !$$0.canAddPassenger(this))) {
/* 2119 */       return false;
/*      */     }
/*      */     
/* 2122 */     if (isPassenger()) {
/* 2123 */       stopRiding();
/*      */     }
/*      */     
/* 2126 */     setPose(Pose.STANDING);
/* 2127 */     this.vehicle = $$0;
/* 2128 */     this.vehicle.addPassenger(this);
/*      */     
/* 2130 */     $$0.getIndirectPassengersStream()
/* 2131 */       .filter($$0 -> $$0 instanceof ServerPlayer)
/* 2132 */       .forEach($$0 -> CriteriaTriggers.START_RIDING_TRIGGER.trigger((ServerPlayer)$$0));
/*      */ 
/*      */ 
/*      */     
/* 2136 */     return true;
/*      */   }
/*      */   
/*      */   protected boolean canRide(Entity $$0) {
/* 2140 */     return (!isShiftKeyDown() && this.boardingCooldown <= 0);
/*      */   }
/*      */   
/*      */   public void ejectPassengers() {
/* 2144 */     for (int $$0 = this.passengers.size() - 1; $$0 >= 0; $$0--) {
/* 2145 */       ((Entity)this.passengers.get($$0)).stopRiding();
/*      */     }
/*      */   }
/*      */   
/*      */   public void removeVehicle() {
/* 2150 */     if (this.vehicle != null) {
/* 2151 */       Entity $$0 = this.vehicle;
/* 2152 */       this.vehicle = null;
/* 2153 */       $$0.removePassenger(this);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void stopRiding() {
/* 2158 */     removeVehicle();
/*      */   }
/*      */   
/*      */   protected void addPassenger(Entity $$0) {
/* 2162 */     if ($$0.getVehicle() != this) {
/* 2163 */       throw new IllegalStateException("Use x.startRiding(y), not y.addPassenger(x)");
/*      */     }
/*      */     
/* 2166 */     if (this.passengers.isEmpty()) {
/* 2167 */       this.passengers = ImmutableList.of($$0);
/*      */     } else {
/* 2169 */       List<Entity> $$1 = Lists.newArrayList((Iterable)this.passengers);
/* 2170 */       if (!(level()).isClientSide && $$0 instanceof Player && !(getFirstPassenger() instanceof Player)) {
/* 2171 */         $$1.add(0, $$0);
/*      */       } else {
/* 2173 */         $$1.add($$0);
/*      */       } 
/* 2175 */       this.passengers = ImmutableList.copyOf($$1);
/*      */     } 
/* 2177 */     gameEvent(GameEvent.ENTITY_MOUNT, $$0);
/*      */   }
/*      */   
/*      */   protected void removePassenger(Entity $$0) {
/* 2181 */     if ($$0.getVehicle() == this) {
/* 2182 */       throw new IllegalStateException("Use x.stopRiding(y), not y.removePassenger(x)");
/*      */     }
/*      */     
/* 2185 */     if (this.passengers.size() == 1 && this.passengers.get(0) == $$0) {
/* 2186 */       this.passengers = ImmutableList.of();
/*      */     } else {
/* 2188 */       this.passengers = (ImmutableList<Entity>)this.passengers.stream().filter($$1 -> ($$1 != $$0)).collect(ImmutableList.toImmutableList());
/*      */     } 
/* 2190 */     $$0.boardingCooldown = 60;
/* 2191 */     gameEvent(GameEvent.ENTITY_DISMOUNT, $$0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean canAddPassenger(Entity $$0) {
/* 2198 */     return this.passengers.isEmpty();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean couldAcceptPassenger() {
/* 2205 */     return true;
/*      */   }
/*      */   
/*      */   public void lerpTo(double $$0, double $$1, double $$2, float $$3, float $$4, int $$5) {
/* 2209 */     setPos($$0, $$1, $$2);
/* 2210 */     setRot($$3, $$4);
/*      */   }
/*      */   
/*      */   public double lerpTargetX() {
/* 2214 */     return getX();
/*      */   }
/*      */   
/*      */   public double lerpTargetY() {
/* 2218 */     return getY();
/*      */   }
/*      */   
/*      */   public double lerpTargetZ() {
/* 2222 */     return getZ();
/*      */   }
/*      */   
/*      */   public float lerpTargetXRot() {
/* 2226 */     return getXRot();
/*      */   }
/*      */   
/*      */   public float lerpTargetYRot() {
/* 2230 */     return getYRot();
/*      */   }
/*      */   
/*      */   public void lerpHeadTo(float $$0, int $$1) {
/* 2234 */     setYHeadRot($$0);
/*      */   }
/*      */   
/*      */   public float getPickRadius() {
/* 2238 */     return 0.0F;
/*      */   }
/*      */   
/*      */   public Vec3 getLookAngle() {
/* 2242 */     return calculateViewVector(getXRot(), getYRot());
/*      */   }
/*      */   
/*      */   public Vec3 getHandHoldingItemAngle(Item $$0) {
/* 2246 */     Entity entity = this; if (entity instanceof Player) { Player $$1 = (Player)entity;
/* 2247 */       boolean $$2 = ($$1.getOffhandItem().is($$0) && !$$1.getMainHandItem().is($$0));
/* 2248 */       HumanoidArm $$3 = $$2 ? $$1.getMainArm().getOpposite() : $$1.getMainArm();
/* 2249 */       return calculateViewVector(0.0F, getYRot() + (($$3 == HumanoidArm.RIGHT) ? 80 : -80)).scale(0.5D); }
/*      */     
/* 2251 */     return Vec3.ZERO;
/*      */   }
/*      */ 
/*      */   
/*      */   public Vec2 getRotationVector() {
/* 2256 */     return new Vec2(getXRot(), getYRot());
/*      */   }
/*      */   
/*      */   public Vec3 getForward() {
/* 2260 */     return Vec3.directionFromRotation(getRotationVector());
/*      */   }
/*      */   
/*      */   public void handleInsidePortal(BlockPos $$0) {
/* 2264 */     if (isOnPortalCooldown()) {
/* 2265 */       setPortalCooldown();
/*      */       
/*      */       return;
/*      */     } 
/* 2269 */     if (!(level()).isClientSide && !$$0.equals(this.portalEntrancePos)) {
/* 2270 */       this.portalEntrancePos = $$0.immutable();
/*      */     }
/*      */     
/* 2273 */     this.isInsidePortal = true;
/*      */   }
/*      */   
/*      */   protected void handleNetherPortal() {
/* 2277 */     if (!(level() instanceof ServerLevel)) {
/*      */       return;
/*      */     }
/* 2280 */     int $$0 = getPortalWaitTime();
/* 2281 */     ServerLevel $$1 = (ServerLevel)level();
/*      */     
/* 2283 */     if (this.isInsidePortal) {
/* 2284 */       MinecraftServer $$2 = $$1.getServer();
/* 2285 */       ResourceKey<Level> $$3 = (level().dimension() == Level.NETHER) ? Level.OVERWORLD : Level.NETHER;
/* 2286 */       ServerLevel $$4 = $$2.getLevel($$3);
/*      */       
/* 2288 */       if ($$4 != null && $$2.isNetherEnabled() && !isPassenger() && this.portalTime++ >= $$0) {
/* 2289 */         level().getProfiler().push("portal");
/*      */         
/* 2291 */         this.portalTime = $$0;
/* 2292 */         setPortalCooldown();
/*      */         
/* 2294 */         changeDimension($$4);
/*      */         
/* 2296 */         level().getProfiler().pop();
/*      */       } 
/* 2298 */       this.isInsidePortal = false;
/*      */     } else {
/* 2300 */       if (this.portalTime > 0) {
/* 2301 */         this.portalTime -= 4;
/*      */       }
/* 2303 */       if (this.portalTime < 0) {
/* 2304 */         this.portalTime = 0;
/*      */       }
/*      */     } 
/* 2307 */     processPortalCooldown();
/*      */   }
/*      */   
/*      */   public int getDimensionChangingDelay() {
/* 2311 */     return 300;
/*      */   }
/*      */   
/*      */   public void lerpMotion(double $$0, double $$1, double $$2) {
/* 2315 */     setDeltaMovement($$0, $$1, $$2);
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleDamageEvent(DamageSource $$0) {}
/*      */   
/*      */   public void handleEntityEvent(byte $$0) {
/* 2322 */     switch ($$0) {
/*      */       case 53:
/* 2324 */         HoneyBlock.showSlideParticles(this);
/*      */         break;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void animateHurt(float $$0) {}
/*      */   
/*      */   public Iterable<ItemStack> getHandSlots() {
/* 2333 */     return EMPTY_LIST;
/*      */   }
/*      */   
/*      */   public Iterable<ItemStack> getArmorSlots() {
/* 2337 */     return EMPTY_LIST;
/*      */   }
/*      */   
/*      */   public Iterable<ItemStack> getAllSlots() {
/* 2341 */     return Iterables.concat(getHandSlots(), getArmorSlots());
/*      */   }
/*      */ 
/*      */   
/*      */   public void setItemSlot(EquipmentSlot $$0, ItemStack $$1) {}
/*      */ 
/*      */   
/*      */   public boolean isOnFire() {
/* 2349 */     boolean $$0 = (level() != null && (level()).isClientSide);
/*      */     
/* 2351 */     return (!fireImmune() && (this.remainingFireTicks > 0 || ($$0 && getSharedFlag(0))));
/*      */   }
/*      */   
/*      */   public boolean isPassenger() {
/* 2355 */     return (getVehicle() != null);
/*      */   }
/*      */   
/*      */   public boolean isVehicle() {
/* 2359 */     return !this.passengers.isEmpty();
/*      */   }
/*      */   
/*      */   public boolean dismountsUnderwater() {
/* 2363 */     return getType().is(EntityTypeTags.DISMOUNTS_UNDERWATER);
/*      */   }
/*      */   
/*      */   public boolean canControlVehicle() {
/* 2367 */     return !getType().is(EntityTypeTags.NON_CONTROLLING_RIDER);
/*      */   }
/*      */   
/*      */   public void setShiftKeyDown(boolean $$0) {
/* 2371 */     setSharedFlag(1, $$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isShiftKeyDown() {
/* 2376 */     return getSharedFlag(1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSteppingCarefully() {
/* 2383 */     return isShiftKeyDown();
/*      */   }
/*      */   
/*      */   public boolean isSuppressingBounce() {
/* 2387 */     return isShiftKeyDown();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isDiscrete() {
/* 2394 */     return isShiftKeyDown();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isDescending() {
/* 2401 */     return isShiftKeyDown();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isCrouching() {
/* 2408 */     return hasPose(Pose.CROUCHING);
/*      */   }
/*      */   
/*      */   public boolean isSprinting() {
/* 2412 */     return getSharedFlag(3);
/*      */   }
/*      */   
/*      */   public void setSprinting(boolean $$0) {
/* 2416 */     setSharedFlag(3, $$0);
/*      */   }
/*      */   
/*      */   public boolean isSwimming() {
/* 2420 */     return getSharedFlag(4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isVisuallySwimming() {
/* 2430 */     return hasPose(Pose.SWIMMING);
/*      */   }
/*      */   
/*      */   public boolean isVisuallyCrawling() {
/* 2434 */     return (isVisuallySwimming() && !isInWater());
/*      */   }
/*      */   
/*      */   public void setSwimming(boolean $$0) {
/* 2438 */     setSharedFlag(4, $$0);
/*      */   }
/*      */   
/*      */   public final boolean hasGlowingTag() {
/* 2442 */     return this.hasGlowingTag;
/*      */   }
/*      */   
/*      */   public final void setGlowingTag(boolean $$0) {
/* 2446 */     this.hasGlowingTag = $$0;
/* 2447 */     setSharedFlag(6, isCurrentlyGlowing());
/*      */   }
/*      */   
/*      */   public boolean isCurrentlyGlowing() {
/* 2451 */     if (level().isClientSide()) {
/* 2452 */       return getSharedFlag(6);
/*      */     }
/* 2454 */     return this.hasGlowingTag;
/*      */   }
/*      */   
/*      */   public boolean isInvisible() {
/* 2458 */     return getSharedFlag(5);
/*      */   }
/*      */   
/*      */   public boolean isInvisibleTo(Player $$0) {
/* 2462 */     if ($$0.isSpectator()) {
/* 2463 */       return false;
/*      */     }
/* 2465 */     PlayerTeam playerTeam = getTeam();
/* 2466 */     if (playerTeam != null && $$0 != null && $$0.getTeam() == playerTeam && playerTeam.canSeeFriendlyInvisibles()) {
/* 2467 */       return false;
/*      */     }
/* 2469 */     return isInvisible();
/*      */   }
/*      */   
/*      */   public boolean isOnRails() {
/* 2473 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateDynamicGameEventListener(BiConsumer<DynamicGameEventListener<?>, ServerLevel> $$0) {}
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public PlayerTeam getTeam() {
/* 2482 */     return level().getScoreboard().getPlayersTeam(getScoreboardName());
/*      */   }
/*      */   
/*      */   public boolean isAlliedTo(Entity $$0) {
/* 2486 */     return isAlliedTo((Team)$$0.getTeam());
/*      */   }
/*      */   
/*      */   public boolean isAlliedTo(Team $$0) {
/* 2490 */     if (getTeam() != null) {
/* 2491 */       return getTeam().isAlliedTo($$0);
/*      */     }
/* 2493 */     return false;
/*      */   }
/*      */   
/*      */   public void setInvisible(boolean $$0) {
/* 2497 */     setSharedFlag(5, $$0);
/*      */   }
/*      */   
/*      */   protected boolean getSharedFlag(int $$0) {
/* 2501 */     return ((((Byte)this.entityData.get(DATA_SHARED_FLAGS_ID)).byteValue() & 1 << $$0) != 0);
/*      */   }
/*      */   
/*      */   protected void setSharedFlag(int $$0, boolean $$1) {
/* 2505 */     byte $$2 = ((Byte)this.entityData.get(DATA_SHARED_FLAGS_ID)).byteValue();
/* 2506 */     if ($$1) {
/* 2507 */       this.entityData.set(DATA_SHARED_FLAGS_ID, Byte.valueOf((byte)($$2 | 1 << $$0)));
/*      */     } else {
/* 2509 */       this.entityData.set(DATA_SHARED_FLAGS_ID, Byte.valueOf((byte)($$2 & (1 << $$0 ^ 0xFFFFFFFF))));
/*      */     } 
/*      */   }
/*      */   
/*      */   public int getMaxAirSupply() {
/* 2514 */     return 300;
/*      */   }
/*      */   
/*      */   public int getAirSupply() {
/* 2518 */     return ((Integer)this.entityData.get(DATA_AIR_SUPPLY_ID)).intValue();
/*      */   }
/*      */   
/*      */   public void setAirSupply(int $$0) {
/* 2522 */     this.entityData.set(DATA_AIR_SUPPLY_ID, Integer.valueOf($$0));
/*      */   }
/*      */   
/*      */   public int getTicksFrozen() {
/* 2526 */     return ((Integer)this.entityData.get(DATA_TICKS_FROZEN)).intValue();
/*      */   }
/*      */   
/*      */   public void setTicksFrozen(int $$0) {
/* 2530 */     this.entityData.set(DATA_TICKS_FROZEN, Integer.valueOf($$0));
/*      */   }
/*      */   
/*      */   public float getPercentFrozen() {
/* 2534 */     int $$0 = getTicksRequiredToFreeze();
/* 2535 */     return Math.min(getTicksFrozen(), $$0) / $$0;
/*      */   }
/*      */   
/*      */   public boolean isFullyFrozen() {
/* 2539 */     return (getTicksFrozen() >= getTicksRequiredToFreeze());
/*      */   }
/*      */   
/*      */   public int getTicksRequiredToFreeze() {
/* 2543 */     return 140;
/*      */   }
/*      */   
/*      */   public void thunderHit(ServerLevel $$0, LightningBolt $$1) {
/* 2547 */     setRemainingFireTicks(this.remainingFireTicks + 1);
/* 2548 */     if (this.remainingFireTicks == 0) {
/* 2549 */       setSecondsOnFire(8);
/*      */     }
/* 2551 */     hurt(damageSources().lightningBolt(), 5.0F);
/*      */   }
/*      */   public void onAboveBubbleCol(boolean $$0) {
/*      */     double $$3;
/* 2555 */     Vec3 $$1 = getDeltaMovement();
/*      */     
/* 2557 */     if ($$0) {
/* 2558 */       double $$2 = Math.max(-0.9D, $$1.y - 0.03D);
/*      */     } else {
/* 2560 */       $$3 = Math.min(1.8D, $$1.y + 0.1D);
/*      */     } 
/* 2562 */     setDeltaMovement($$1.x, $$3, $$1.z);
/*      */   }
/*      */   public void onInsideBubbleColumn(boolean $$0) {
/*      */     double $$3;
/* 2566 */     Vec3 $$1 = getDeltaMovement();
/*      */     
/* 2568 */     if ($$0) {
/* 2569 */       double $$2 = Math.max(-0.3D, $$1.y - 0.03D);
/*      */     } else {
/* 2571 */       $$3 = Math.min(0.7D, $$1.y + 0.06D);
/*      */     } 
/* 2573 */     setDeltaMovement($$1.x, $$3, $$1.z);
/* 2574 */     resetFallDistance();
/*      */   }
/*      */   
/*      */   public boolean killedEntity(ServerLevel $$0, LivingEntity $$1) {
/* 2578 */     return true;
/*      */   }
/*      */   
/*      */   public void checkSlowFallDistance() {
/* 2582 */     if (getDeltaMovement().y() > -0.5D && this.fallDistance > 1.0F) {
/* 2583 */       this.fallDistance = 1.0F;
/*      */     }
/*      */   }
/*      */   
/*      */   public void resetFallDistance() {
/* 2588 */     this.fallDistance = 0.0F;
/*      */   }
/*      */   
/*      */   protected void moveTowardsClosestSpace(double $$0, double $$1, double $$2) {
/* 2592 */     BlockPos $$3 = BlockPos.containing($$0, $$1, $$2);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2597 */     Vec3 $$4 = new Vec3($$0 - $$3.getX(), $$1 - $$3.getY(), $$2 - $$3.getZ());
/*      */ 
/*      */     
/* 2600 */     BlockPos.MutableBlockPos $$5 = new BlockPos.MutableBlockPos();
/* 2601 */     Direction $$6 = Direction.UP;
/* 2602 */     double $$7 = Double.MAX_VALUE;
/* 2603 */     for (Direction $$8 : new Direction[] { Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST, Direction.UP }) {
/* 2604 */       $$5.setWithOffset((Vec3i)$$3, $$8);
/* 2605 */       if (!level().getBlockState((BlockPos)$$5).isCollisionShapeFullBlock((BlockGetter)level(), (BlockPos)$$5)) {
/* 2606 */         double $$9 = $$4.get($$8.getAxis());
/* 2607 */         double $$10 = ($$8.getAxisDirection() == Direction.AxisDirection.POSITIVE) ? (1.0D - $$9) : $$9;
/* 2608 */         if ($$10 < $$7) {
/* 2609 */           $$7 = $$10;
/* 2610 */           $$6 = $$8;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 2615 */     float $$11 = this.random.nextFloat() * 0.2F + 0.1F;
/* 2616 */     float $$12 = $$6.getAxisDirection().getStep();
/*      */     
/* 2618 */     Vec3 $$13 = getDeltaMovement().scale(0.75D);
/* 2619 */     if ($$6.getAxis() == Direction.Axis.X) {
/* 2620 */       setDeltaMovement(($$12 * $$11), $$13.y, $$13.z);
/*      */ 
/*      */ 
/*      */     
/*      */     }
/* 2625 */     else if ($$6.getAxis() == Direction.Axis.Y) {
/* 2626 */       setDeltaMovement($$13.x, ($$12 * $$11), $$13.z);
/*      */ 
/*      */ 
/*      */     
/*      */     }
/* 2631 */     else if ($$6.getAxis() == Direction.Axis.Z) {
/* 2632 */       setDeltaMovement($$13.x, $$13.y, ($$12 * $$11));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void makeStuckInBlock(BlockState $$0, Vec3 $$1) {
/* 2641 */     resetFallDistance();
/* 2642 */     this.stuckSpeedMultiplier = $$1;
/*      */   }
/*      */   
/*      */   private static Component removeAction(Component $$0) {
/* 2646 */     MutableComponent $$1 = $$0.plainCopy().setStyle($$0.getStyle().withClickEvent(null));
/* 2647 */     for (Component $$2 : $$0.getSiblings()) {
/* 2648 */       $$1.append(removeAction($$2));
/*      */     }
/* 2650 */     return (Component)$$1;
/*      */   }
/*      */ 
/*      */   
/*      */   public Component getName() {
/* 2655 */     Component $$0 = getCustomName();
/* 2656 */     if ($$0 != null) {
/* 2657 */       return removeAction($$0);
/*      */     }
/* 2659 */     return getTypeName();
/*      */   }
/*      */   
/*      */   protected Component getTypeName() {
/* 2663 */     return this.type.getDescription();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean is(Entity $$0) {
/* 2668 */     return (this == $$0);
/*      */   }
/*      */   
/*      */   public float getYHeadRot() {
/* 2672 */     return 0.0F;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setYHeadRot(float $$0) {}
/*      */ 
/*      */   
/*      */   public void setYBodyRot(float $$0) {}
/*      */   
/*      */   public boolean isAttackable() {
/* 2682 */     return true;
/*      */   }
/*      */   
/*      */   public boolean skipAttackInteraction(Entity $$0) {
/* 2686 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString() {
/* 2691 */     String $$0 = (level() == null) ? "~NULL~" : level().toString();
/* 2692 */     if (this.removalReason != null) {
/* 2693 */       return String.format(Locale.ROOT, "%s['%s'/%d, l='%s', x=%.2f, y=%.2f, z=%.2f, removed=%s]", new Object[] { getClass().getSimpleName(), getName().getString(), Integer.valueOf(this.id), $$0, Double.valueOf(getX()), Double.valueOf(getY()), Double.valueOf(getZ()), this.removalReason });
/*      */     }
/* 2695 */     return String.format(Locale.ROOT, "%s['%s'/%d, l='%s', x=%.2f, y=%.2f, z=%.2f]", new Object[] { getClass().getSimpleName(), getName().getString(), Integer.valueOf(this.id), $$0, Double.valueOf(getX()), Double.valueOf(getY()), Double.valueOf(getZ()) });
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isInvulnerableTo(DamageSource $$0) {
/* 2700 */     return (isRemoved() || (this.invulnerable && 
/* 2701 */       !$$0.is(DamageTypeTags.BYPASSES_INVULNERABILITY) && !$$0.isCreativePlayer()) || ($$0
/* 2702 */       .is(DamageTypeTags.IS_FIRE) && fireImmune()) || ($$0
/* 2703 */       .is(DamageTypeTags.IS_FALL) && getType().is(EntityTypeTags.FALL_DAMAGE_IMMUNE)));
/*      */   }
/*      */   
/*      */   public boolean isInvulnerable() {
/* 2707 */     return this.invulnerable;
/*      */   }
/*      */   
/*      */   public void setInvulnerable(boolean $$0) {
/* 2711 */     this.invulnerable = $$0;
/*      */   }
/*      */   
/*      */   public void copyPosition(Entity $$0) {
/* 2715 */     moveTo($$0.getX(), $$0.getY(), $$0.getZ(), $$0.getYRot(), $$0.getXRot());
/*      */   }
/*      */   
/*      */   public void restoreFrom(Entity $$0) {
/* 2719 */     CompoundTag $$1 = $$0.saveWithoutId(new CompoundTag());
/* 2720 */     $$1.remove("Dimension");
/* 2721 */     load($$1);
/* 2722 */     this.portalCooldown = $$0.portalCooldown;
/* 2723 */     this.portalEntrancePos = $$0.portalEntrancePos;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public Entity changeDimension(ServerLevel $$0) {
/* 2728 */     if (!(level() instanceof ServerLevel) || isRemoved()) {
/* 2729 */       return null;
/*      */     }
/* 2731 */     level().getProfiler().push("changeDimension");
/*      */     
/* 2733 */     unRide();
/* 2734 */     level().getProfiler().push("reposition");
/*      */     
/* 2736 */     PortalInfo $$1 = findDimensionEntryPoint($$0);
/* 2737 */     if ($$1 == null) {
/* 2738 */       return null;
/*      */     }
/*      */     
/* 2741 */     level().getProfiler().popPush("reloading");
/* 2742 */     Entity $$2 = (Entity)getType().create((Level)$$0);
/*      */     
/* 2744 */     if ($$2 != null) {
/* 2745 */       $$2.restoreFrom(this);
/*      */       
/* 2747 */       $$2.moveTo($$1.pos.x, $$1.pos.y, $$1.pos.z, $$1.yRot, $$2.getXRot());
/* 2748 */       $$2.setDeltaMovement($$1.speed);
/*      */       
/* 2750 */       $$0.addDuringTeleport($$2);
/*      */       
/* 2752 */       if ($$0.dimension() == Level.END) {
/* 2753 */         ServerLevel.makeObsidianPlatform($$0);
/*      */       }
/*      */     } 
/* 2756 */     removeAfterChangingDimensions();
/* 2757 */     level().getProfiler().pop();
/*      */     
/* 2759 */     ((ServerLevel)level()).resetEmptyTime();
/* 2760 */     $$0.resetEmptyTime();
/* 2761 */     level().getProfiler().pop();
/* 2762 */     return $$2;
/*      */   }
/*      */   
/*      */   protected void removeAfterChangingDimensions() {
/* 2766 */     setRemoved(RemovalReason.CHANGED_DIMENSION);
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   protected PortalInfo findDimensionEntryPoint(ServerLevel $$0) {
/* 2771 */     boolean $$1 = (level().dimension() == Level.END && $$0.dimension() == Level.OVERWORLD);
/* 2772 */     boolean $$2 = ($$0.dimension() == Level.END);
/*      */     
/* 2774 */     if ($$1 || $$2) {
/*      */       BlockPos $$4;
/* 2776 */       if ($$2) {
/* 2777 */         BlockPos $$3 = ServerLevel.END_SPAWN_POINT;
/*      */       } else {
/* 2779 */         $$4 = $$0.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, $$0.getSharedSpawnPos());
/*      */       } 
/*      */       
/* 2782 */       return new PortalInfo(new Vec3($$4
/* 2783 */             .getX() + 0.5D, $$4.getY(), $$4.getZ() + 0.5D), 
/* 2784 */           getDeltaMovement(), 
/* 2785 */           getYRot(), 
/* 2786 */           getXRot());
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2791 */     boolean $$5 = ($$0.dimension() == Level.NETHER);
/* 2792 */     if (level().dimension() != Level.NETHER && !$$5) {
/* 2793 */       return null;
/*      */     }
/*      */     
/* 2796 */     WorldBorder $$6 = $$0.getWorldBorder();
/* 2797 */     double $$7 = DimensionType.getTeleportationScale(level().dimensionType(), $$0.dimensionType());
/* 2798 */     BlockPos $$8 = $$6.clampToBounds(
/* 2799 */         getX() * $$7, 
/* 2800 */         getY(), 
/* 2801 */         getZ() * $$7);
/*      */     
/* 2803 */     return getExitPortal($$0, $$8, $$5, $$6)
/* 2804 */       .<PortalInfo>map($$1 -> {
/*      */           Direction.Axis $$6;
/*      */           
/*      */           Vec3 $$7;
/*      */           
/*      */           BlockState $$2 = level().getBlockState(this.portalEntrancePos);
/*      */           
/*      */           if ($$2.hasProperty((Property)BlockStateProperties.HORIZONTAL_AXIS)) {
/*      */             Direction.Axis $$3 = (Direction.Axis)$$2.getValue((Property)BlockStateProperties.HORIZONTAL_AXIS);
/*      */             
/*      */             BlockUtil.FoundRectangle $$4 = BlockUtil.getLargestRectangleAround(this.portalEntrancePos, $$3, 21, Direction.Axis.Y, 21, ());
/*      */             
/*      */             Vec3 $$5 = getRelativePortalPosition($$3, $$4);
/*      */           } else {
/*      */             $$6 = Direction.Axis.X;
/*      */             $$7 = new Vec3(0.5D, 0.0D, 0.0D);
/*      */           } 
/*      */           return PortalShape.createPortalInfo($$0, $$1, $$6, $$7, this, getDeltaMovement(), getYRot(), getXRot());
/* 2822 */         }).orElse(null);
/*      */   }
/*      */ 
/*      */   
/*      */   protected Vec3 getRelativePortalPosition(Direction.Axis $$0, BlockUtil.FoundRectangle $$1) {
/* 2827 */     return PortalShape.getRelativePosition($$1, $$0, position(), getDimensions(getPose()));
/*      */   }
/*      */   
/*      */   protected Optional<BlockUtil.FoundRectangle> getExitPortal(ServerLevel $$0, BlockPos $$1, boolean $$2, WorldBorder $$3) {
/* 2831 */     return $$0.getPortalForcer().findPortalAround($$1, $$2, $$3);
/*      */   }
/*      */   
/*      */   public boolean canChangeDimensions() {
/* 2835 */     return (!isPassenger() && !isVehicle());
/*      */   }
/*      */   
/*      */   public float getBlockExplosionResistance(Explosion $$0, BlockGetter $$1, BlockPos $$2, BlockState $$3, FluidState $$4, float $$5) {
/* 2839 */     return $$5;
/*      */   }
/*      */   
/*      */   public boolean shouldBlockExplode(Explosion $$0, BlockGetter $$1, BlockPos $$2, BlockState $$3, float $$4) {
/* 2843 */     return true;
/*      */   }
/*      */   
/*      */   public int getMaxFallDistance() {
/* 2847 */     return 3;
/*      */   }
/*      */   
/*      */   public boolean isIgnoringBlockTriggers() {
/* 2851 */     return false;
/*      */   }
/*      */   
/*      */   public void fillCrashReportCategory(CrashReportCategory $$0) {
/* 2855 */     $$0.setDetail("Entity Type", () -> "" + EntityType.getKey(getType()) + " (" + EntityType.getKey(getType()) + ")");
/* 2856 */     $$0.setDetail("Entity ID", Integer.valueOf(this.id));
/* 2857 */     $$0.setDetail("Entity Name", () -> getName().getString());
/* 2858 */     $$0.setDetail("Entity's Exact location", String.format(Locale.ROOT, "%.2f, %.2f, %.2f", new Object[] { Double.valueOf(getX()), Double.valueOf(getY()), Double.valueOf(getZ()) }));
/* 2859 */     $$0.setDetail("Entity's Block location", CrashReportCategory.formatLocation((LevelHeightAccessor)level(), Mth.floor(getX()), Mth.floor(getY()), Mth.floor(getZ())));
/* 2860 */     Vec3 $$1 = getDeltaMovement();
/* 2861 */     $$0.setDetail("Entity's Momentum", String.format(Locale.ROOT, "%.2f, %.2f, %.2f", new Object[] { Double.valueOf($$1.x), Double.valueOf($$1.y), Double.valueOf($$1.z) }));
/* 2862 */     $$0.setDetail("Entity's Passengers", () -> getPassengers().toString());
/* 2863 */     $$0.setDetail("Entity's Vehicle", () -> String.valueOf(getVehicle()));
/*      */   }
/*      */   
/*      */   public boolean displayFireAnimation() {
/* 2867 */     return (isOnFire() && !isSpectator());
/*      */   }
/*      */   
/*      */   public void setUUID(UUID $$0) {
/* 2871 */     this.uuid = $$0;
/* 2872 */     this.stringUUID = this.uuid.toString();
/*      */   }
/*      */ 
/*      */   
/*      */   public UUID getUUID() {
/* 2877 */     return this.uuid;
/*      */   }
/*      */   
/*      */   public String getStringUUID() {
/* 2881 */     return this.stringUUID;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getScoreboardName() {
/* 2886 */     return this.stringUUID;
/*      */   }
/*      */   
/*      */   public boolean isPushedByFluid() {
/* 2890 */     return true;
/*      */   }
/*      */   
/*      */   public static double getViewScale() {
/* 2894 */     return viewScale;
/*      */   }
/*      */   
/*      */   public static void setViewScale(double $$0) {
/* 2898 */     viewScale = $$0;
/*      */   }
/*      */ 
/*      */   
/*      */   public Component getDisplayName() {
/* 2903 */     return (Component)PlayerTeam.formatNameForTeam((Team)getTeam(), getName()).withStyle($$0 -> $$0.withHoverEvent(createHoverEvent()).withInsertion(getStringUUID()));
/*      */   }
/*      */   
/*      */   public void setCustomName(@Nullable Component $$0) {
/* 2907 */     this.entityData.set(DATA_CUSTOM_NAME, Optional.ofNullable($$0));
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public Component getCustomName() {
/* 2913 */     return ((Optional<Component>)this.entityData.get(DATA_CUSTOM_NAME)).orElse(null);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hasCustomName() {
/* 2918 */     return ((Optional)this.entityData.get(DATA_CUSTOM_NAME)).isPresent();
/*      */   }
/*      */   
/*      */   public void setCustomNameVisible(boolean $$0) {
/* 2922 */     this.entityData.set(DATA_CUSTOM_NAME_VISIBLE, Boolean.valueOf($$0));
/*      */   }
/*      */   
/*      */   public boolean isCustomNameVisible() {
/* 2926 */     return ((Boolean)this.entityData.get(DATA_CUSTOM_NAME_VISIBLE)).booleanValue();
/*      */   }
/*      */   
/*      */   public final void teleportToWithTicket(double $$0, double $$1, double $$2) {
/* 2930 */     if (!(level() instanceof ServerLevel)) {
/*      */       return;
/*      */     }
/* 2933 */     ChunkPos $$3 = new ChunkPos(BlockPos.containing($$0, $$1, $$2));
/* 2934 */     ((ServerLevel)level()).getChunkSource().addRegionTicket(TicketType.POST_TELEPORT, $$3, 0, Integer.valueOf(getId()));
/*      */     
/* 2936 */     level().getChunk($$3.x, $$3.z);
/*      */     
/* 2938 */     teleportTo($$0, $$1, $$2);
/*      */   }
/*      */   
/*      */   public boolean teleportTo(ServerLevel $$0, double $$1, double $$2, double $$3, Set<RelativeMovement> $$4, float $$5, float $$6) {
/* 2942 */     float $$7 = Mth.clamp($$6, -90.0F, 90.0F);
/*      */     
/* 2944 */     if ($$0 == level()) {
/* 2945 */       moveTo($$1, $$2, $$3, $$5, $$7);
/* 2946 */       teleportPassengers();
/* 2947 */       setYHeadRot($$5);
/*      */     } else {
/* 2949 */       unRide();
/* 2950 */       Entity $$8 = (Entity)getType().create((Level)$$0);
/* 2951 */       if ($$8 != null) {
/* 2952 */         $$8.restoreFrom(this);
/* 2953 */         $$8.moveTo($$1, $$2, $$3, $$5, $$7);
/* 2954 */         $$8.setYHeadRot($$5);
/*      */         
/* 2956 */         setRemoved(RemovalReason.CHANGED_DIMENSION);
/* 2957 */         $$0.addDuringTeleport($$8);
/*      */       } else {
/* 2959 */         return false;
/*      */       } 
/*      */     } 
/* 2962 */     return true;
/*      */   }
/*      */   
/*      */   public void dismountTo(double $$0, double $$1, double $$2) {
/* 2966 */     teleportTo($$0, $$1, $$2);
/*      */   }
/*      */   
/*      */   public void teleportTo(double $$0, double $$1, double $$2) {
/* 2970 */     if (!(level() instanceof ServerLevel)) {
/*      */       return;
/*      */     }
/* 2973 */     moveTo($$0, $$1, $$2, getYRot(), getXRot());
/* 2974 */     teleportPassengers();
/*      */   }
/*      */   
/*      */   private void teleportPassengers() {
/* 2978 */     getSelfAndPassengers().forEach($$0 -> {
/*      */           UnmodifiableIterator<Entity> unmodifiableIterator = $$0.passengers.iterator();
/*      */           while (unmodifiableIterator.hasNext()) {
/*      */             Entity $$1 = unmodifiableIterator.next();
/*      */             $$0.positionRider($$1, Entity::moveTo);
/*      */           } 
/*      */         });
/*      */   } public void teleportRelative(double $$0, double $$1, double $$2) {
/* 2986 */     teleportTo(getX() + $$0, getY() + $$1, getZ() + $$2);
/*      */   }
/*      */   
/*      */   public boolean shouldShowName() {
/* 2990 */     return isCustomNameVisible();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void onSyncedDataUpdated(List<SynchedEntityData.DataValue<?>> $$0) {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void onSyncedDataUpdated(EntityDataAccessor<?> $$0) {
/* 3000 */     if (DATA_POSE.equals($$0)) {
/* 3001 */       refreshDimensions();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   protected void fixupDimensions() {
/* 3012 */     Pose $$0 = getPose();
/* 3013 */     EntityDimensions $$1 = getDimensions($$0);
/*      */     
/* 3015 */     this.dimensions = $$1;
/* 3016 */     this.eyeHeight = getEyeHeight($$0, $$1);
/*      */   }
/*      */   
/*      */   public void refreshDimensions() {
/* 3020 */     EntityDimensions $$0 = this.dimensions;
/* 3021 */     Pose $$1 = getPose();
/* 3022 */     EntityDimensions $$2 = getDimensions($$1);
/*      */     
/* 3024 */     this.dimensions = $$2;
/* 3025 */     this.eyeHeight = getEyeHeight($$1, $$2);
/*      */     
/* 3027 */     reapplyPosition();
/*      */     
/* 3029 */     boolean $$3 = ($$2.width <= 4.0D && $$2.height <= 4.0D);
/* 3030 */     if (!(level()).isClientSide && !this.firstTick && !this.noPhysics && $$3 && ($$2.width > $$0.width || $$2.height > $$0.height) && !(this instanceof Player)) {
/* 3031 */       Vec3 $$4 = position().add(0.0D, $$0.height / 2.0D, 0.0D);
/* 3032 */       double $$5 = Math.max(0.0F, $$2.width - $$0.width) + 1.0E-6D;
/* 3033 */       double $$6 = Math.max(0.0F, $$2.height - $$0.height) + 1.0E-6D;
/* 3034 */       VoxelShape $$7 = Shapes.create(AABB.ofSize($$4, $$5, $$6, $$5));
/*      */       
/* 3036 */       level().findFreePosition(this, $$7, $$4, $$2.width, $$2.height, $$2.width).ifPresent($$1 -> setPos($$1.add(0.0D, -$$0.height / 2.0D, 0.0D)));
/*      */     } 
/*      */   }
/*      */   
/*      */   public Direction getDirection() {
/* 3041 */     return Direction.fromYRot(getYRot());
/*      */   }
/*      */   
/*      */   public Direction getMotionDirection() {
/* 3045 */     return getDirection();
/*      */   }
/*      */   
/*      */   protected HoverEvent createHoverEvent() {
/* 3049 */     return new HoverEvent(HoverEvent.Action.SHOW_ENTITY, new HoverEvent.EntityTooltipInfo(getType(), getUUID(), getName()));
/*      */   }
/*      */   
/*      */   public boolean broadcastToPlayer(ServerPlayer $$0) {
/* 3053 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public final AABB getBoundingBox() {
/* 3058 */     return this.bb;
/*      */   }
/*      */   
/*      */   public AABB getBoundingBoxForCulling() {
/* 3062 */     return getBoundingBox();
/*      */   }
/*      */   
/*      */   public final void setBoundingBox(AABB $$0) {
/* 3066 */     this.bb = $$0;
/*      */   }
/*      */   
/*      */   protected float getEyeHeight(Pose $$0, EntityDimensions $$1) {
/* 3070 */     return $$1.height * 0.85F;
/*      */   }
/*      */   
/*      */   public float getEyeHeight(Pose $$0) {
/* 3074 */     return getEyeHeight($$0, getDimensions($$0));
/*      */   }
/*      */   
/*      */   public final float getEyeHeight() {
/* 3078 */     return this.eyeHeight;
/*      */   }
/*      */   
/*      */   public Vec3 getLeashOffset(float $$0) {
/* 3082 */     return getLeashOffset();
/*      */   }
/*      */   
/*      */   protected Vec3 getLeashOffset() {
/* 3086 */     return new Vec3(0.0D, getEyeHeight(), (getBbWidth() * 0.4F));
/*      */   }
/*      */   
/*      */   public SlotAccess getSlot(int $$0) {
/* 3090 */     return SlotAccess.NULL;
/*      */   }
/*      */ 
/*      */   
/*      */   public void sendSystemMessage(Component $$0) {}
/*      */ 
/*      */   
/*      */   public Level getCommandSenderWorld() {
/* 3098 */     return level();
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public MinecraftServer getServer() {
/* 3103 */     return level().getServer();
/*      */   }
/*      */   
/*      */   public InteractionResult interactAt(Player $$0, Vec3 $$1, InteractionHand $$2) {
/* 3107 */     return InteractionResult.PASS;
/*      */   }
/*      */   
/*      */   public boolean ignoreExplosion(Explosion $$0) {
/* 3111 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void doEnchantDamageEffects(LivingEntity $$0, Entity $$1) {
/* 3118 */     if ($$1 instanceof LivingEntity) {
/* 3119 */       EnchantmentHelper.doPostHurtEffects((LivingEntity)$$1, $$0);
/*      */     }
/* 3121 */     EnchantmentHelper.doPostDamageEffects($$0, $$1);
/*      */   }
/*      */ 
/*      */   
/*      */   public void startSeenByPlayer(ServerPlayer $$0) {}
/*      */ 
/*      */   
/*      */   public void stopSeenByPlayer(ServerPlayer $$0) {}
/*      */   
/*      */   public float rotate(Rotation $$0) {
/* 3131 */     float $$1 = Mth.wrapDegrees(getYRot());
/* 3132 */     switch ($$0) {
/*      */       case FRONT_BACK:
/* 3134 */         return $$1 + 180.0F;
/*      */       case LEFT_RIGHT:
/* 3136 */         return $$1 + 270.0F;
/*      */       case null:
/* 3138 */         return $$1 + 90.0F;
/*      */     } 
/* 3140 */     return $$1;
/*      */   }
/*      */ 
/*      */   
/*      */   public float mirror(Mirror $$0) {
/* 3145 */     float $$1 = Mth.wrapDegrees(getYRot());
/* 3146 */     switch ($$0) {
/*      */       case FRONT_BACK:
/* 3148 */         return -$$1;
/*      */       case LEFT_RIGHT:
/* 3150 */         return 180.0F - $$1;
/*      */     } 
/* 3152 */     return $$1;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean onlyOpCanSetNbt() {
/* 3157 */     return false;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public LivingEntity getControllingPassenger() {
/* 3162 */     return null;
/*      */   }
/*      */   
/*      */   public final boolean hasControllingPassenger() {
/* 3166 */     return (getControllingPassenger() != null);
/*      */   }
/*      */   
/*      */   public final List<Entity> getPassengers() {
/* 3170 */     return (List<Entity>)this.passengers;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public Entity getFirstPassenger() {
/* 3175 */     return this.passengers.isEmpty() ? null : (Entity)this.passengers.get(0);
/*      */   }
/*      */   
/*      */   public boolean hasPassenger(Entity $$0) {
/* 3179 */     return this.passengers.contains($$0);
/*      */   }
/*      */   
/*      */   public boolean hasPassenger(Predicate<Entity> $$0) {
/* 3183 */     for (UnmodifiableIterator<Entity> unmodifiableIterator = this.passengers.iterator(); unmodifiableIterator.hasNext(); ) { Entity $$1 = unmodifiableIterator.next();
/* 3184 */       if ($$0.test($$1)) {
/* 3185 */         return true;
/*      */       } }
/*      */     
/* 3188 */     return false;
/*      */   }
/*      */   
/*      */   private Stream<Entity> getIndirectPassengersStream() {
/* 3192 */     return this.passengers.stream().flatMap(Entity::getSelfAndPassengers);
/*      */   }
/*      */ 
/*      */   
/*      */   public Stream<Entity> getSelfAndPassengers() {
/* 3197 */     return Stream.concat(Stream.of(this), getIndirectPassengersStream());
/*      */   }
/*      */ 
/*      */   
/*      */   public Stream<Entity> getPassengersAndSelf() {
/* 3202 */     return Stream.concat(this.passengers.stream().flatMap(Entity::getPassengersAndSelf), Stream.of(this));
/*      */   }
/*      */   
/*      */   public Iterable<Entity> getIndirectPassengers() {
/* 3206 */     return () -> getIndirectPassengersStream().iterator();
/*      */   }
/*      */   
/*      */   public int countPlayerPassengers() {
/* 3210 */     return (int)getIndirectPassengersStream().filter($$0 -> $$0 instanceof Player).count();
/*      */   }
/*      */   
/*      */   public boolean hasExactlyOnePlayerPassenger() {
/* 3214 */     return (countPlayerPassengers() == 1);
/*      */   }
/*      */   
/*      */   public Entity getRootVehicle() {
/* 3218 */     Entity $$0 = this;
/* 3219 */     while ($$0.isPassenger()) {
/* 3220 */       $$0 = $$0.getVehicle();
/*      */     }
/* 3222 */     return $$0;
/*      */   }
/*      */   
/*      */   public boolean isPassengerOfSameVehicle(Entity $$0) {
/* 3226 */     return (getRootVehicle() == $$0.getRootVehicle());
/*      */   }
/*      */   
/*      */   public boolean hasIndirectPassenger(Entity $$0) {
/* 3230 */     if (!$$0.isPassenger()) {
/* 3231 */       return false;
/*      */     }
/* 3233 */     Entity $$1 = $$0.getVehicle();
/* 3234 */     if ($$1 == this) {
/* 3235 */       return true;
/*      */     }
/* 3237 */     return hasIndirectPassenger($$1);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isControlledByLocalInstance() {
/* 3242 */     LivingEntity livingEntity = getControllingPassenger(); if (livingEntity instanceof Player) { Player $$0 = (Player)livingEntity;
/* 3243 */       return $$0.isLocalPlayer(); }
/*      */     
/* 3245 */     return isEffectiveAi();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEffectiveAi() {
/* 3250 */     return !(level()).isClientSide;
/*      */   }
/*      */   
/*      */   protected static Vec3 getCollisionHorizontalEscapeVector(double $$0, double $$1, float $$2) {
/* 3254 */     double $$3 = ($$0 + $$1 + 9.999999747378752E-6D) / 2.0D;
/*      */     
/* 3256 */     float $$4 = -Mth.sin($$2 * 0.017453292F);
/* 3257 */     float $$5 = Mth.cos($$2 * 0.017453292F);
/*      */     
/* 3259 */     float $$6 = Math.max(Math.abs($$4), Math.abs($$5));
/*      */     
/* 3261 */     return new Vec3($$4 * $$3 / $$6, 0.0D, $$5 * $$3 / $$6);
/*      */   }
/*      */   
/*      */   public Vec3 getDismountLocationForPassenger(LivingEntity $$0) {
/* 3265 */     return new Vec3(getX(), (getBoundingBox()).maxY, getZ());
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public Entity getVehicle() {
/* 3270 */     return this.vehicle;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public Entity getControlledVehicle() {
/* 3275 */     return (this.vehicle != null && this.vehicle.getControllingPassenger() == this) ? this.vehicle : null;
/*      */   }
/*      */   
/*      */   public PushReaction getPistonPushReaction() {
/* 3279 */     return PushReaction.NORMAL;
/*      */   }
/*      */   
/*      */   public SoundSource getSoundSource() {
/* 3283 */     return SoundSource.NEUTRAL;
/*      */   }
/*      */   
/*      */   protected int getFireImmuneTicks() {
/* 3287 */     return 1;
/*      */   }
/*      */ 
/*      */   
/*      */   public CommandSourceStack createCommandSourceStack() {
/* 3292 */     return new CommandSourceStack(this, position(), getRotationVector(), (level() instanceof ServerLevel) ? (ServerLevel)level() : null, getPermissionLevel(), getName().getString(), getDisplayName(), level().getServer(), this);
/*      */   }
/*      */   
/*      */   protected int getPermissionLevel() {
/* 3296 */     return 0;
/*      */   }
/*      */   
/*      */   public boolean hasPermissions(int $$0) {
/* 3300 */     return (getPermissionLevel() >= $$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean acceptsSuccess() {
/* 3305 */     return level().getGameRules().getBoolean(GameRules.RULE_SENDCOMMANDFEEDBACK);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean acceptsFailure() {
/* 3310 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean shouldInformAdmins() {
/* 3315 */     return true;
/*      */   }
/*      */   
/*      */   public void lookAt(EntityAnchorArgument.Anchor $$0, Vec3 $$1) {
/* 3319 */     Vec3 $$2 = $$0.apply(this);
/* 3320 */     double $$3 = $$1.x - $$2.x;
/* 3321 */     double $$4 = $$1.y - $$2.y;
/* 3322 */     double $$5 = $$1.z - $$2.z;
/* 3323 */     double $$6 = Math.sqrt($$3 * $$3 + $$5 * $$5);
/*      */     
/* 3325 */     setXRot(Mth.wrapDegrees((float)-(Mth.atan2($$4, $$6) * 57.2957763671875D)));
/* 3326 */     setYRot(Mth.wrapDegrees((float)(Mth.atan2($$5, $$3) * 57.2957763671875D) - 90.0F));
/* 3327 */     setYHeadRot(getYRot());
/* 3328 */     this.xRotO = getXRot();
/* 3329 */     this.yRotO = getYRot();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean updateFluidHeightAndDoFluidPushing(TagKey<Fluid> $$0, double $$1) {
/* 3337 */     if (touchingUnloadedChunk()) {
/* 3338 */       return false;
/*      */     }
/*      */     
/* 3341 */     AABB $$2 = getBoundingBox().deflate(0.001D);
/* 3342 */     int $$3 = Mth.floor($$2.minX);
/* 3343 */     int $$4 = Mth.ceil($$2.maxX);
/* 3344 */     int $$5 = Mth.floor($$2.minY);
/* 3345 */     int $$6 = Mth.ceil($$2.maxY);
/* 3346 */     int $$7 = Mth.floor($$2.minZ);
/* 3347 */     int $$8 = Mth.ceil($$2.maxZ);
/*      */     
/* 3349 */     double $$9 = 0.0D;
/* 3350 */     boolean $$10 = isPushedByFluid();
/*      */     
/* 3352 */     boolean $$11 = false;
/* 3353 */     Vec3 $$12 = Vec3.ZERO;
/* 3354 */     int $$13 = 0;
/* 3355 */     BlockPos.MutableBlockPos $$14 = new BlockPos.MutableBlockPos();
/* 3356 */     for (int $$15 = $$3; $$15 < $$4; $$15++) {
/* 3357 */       for (int $$16 = $$5; $$16 < $$6; $$16++) {
/* 3358 */         for (int $$17 = $$7; $$17 < $$8; $$17++) {
/* 3359 */           $$14.set($$15, $$16, $$17);
/* 3360 */           FluidState $$18 = level().getFluidState((BlockPos)$$14);
/* 3361 */           if ($$18.is($$0)) {
/* 3362 */             double $$19 = ($$16 + $$18.getHeight((BlockGetter)level(), (BlockPos)$$14));
/* 3363 */             if ($$19 >= $$2.minY) {
/* 3364 */               $$11 = true;
/* 3365 */               $$9 = Math.max($$19 - $$2.minY, $$9);
/* 3366 */               if ($$10) {
/* 3367 */                 Vec3 $$20 = $$18.getFlow((BlockGetter)level(), (BlockPos)$$14);
/* 3368 */                 if ($$9 < 0.4D) {
/* 3369 */                   $$20 = $$20.scale($$9);
/*      */                 }
/*      */                 
/* 3372 */                 $$12 = $$12.add($$20);
/* 3373 */                 $$13++;
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 3381 */     if ($$12.length() > 0.0D) {
/* 3382 */       if ($$13 > 0) {
/* 3383 */         $$12 = $$12.scale(1.0D / $$13);
/*      */       }
/*      */       
/* 3386 */       if (!(this instanceof Player)) {
/* 3387 */         $$12 = $$12.normalize();
/*      */       }
/*      */       
/* 3390 */       Vec3 $$21 = getDeltaMovement();
/* 3391 */       $$12 = $$12.scale($$1 * 1.0D);
/*      */ 
/*      */       
/* 3394 */       double $$22 = 0.003D;
/* 3395 */       if (Math.abs($$21.x) < 0.003D && Math.abs($$21.z) < 0.003D && $$12.length() < 0.0045000000000000005D) {
/* 3396 */         $$12 = $$12.normalize().scale(0.0045000000000000005D);
/*      */       }
/*      */       
/* 3399 */       setDeltaMovement(getDeltaMovement().add($$12));
/*      */     } 
/* 3401 */     this.fluidHeight.put($$0, $$9);
/* 3402 */     return $$11;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean touchingUnloadedChunk() {
/* 3407 */     AABB $$0 = getBoundingBox().inflate(1.0D);
/* 3408 */     int $$1 = Mth.floor($$0.minX);
/* 3409 */     int $$2 = Mth.ceil($$0.maxX);
/* 3410 */     int $$3 = Mth.floor($$0.minZ);
/* 3411 */     int $$4 = Mth.ceil($$0.maxZ);
/* 3412 */     return !level().hasChunksAt($$1, $$3, $$2, $$4);
/*      */   }
/*      */   
/*      */   public double getFluidHeight(TagKey<Fluid> $$0) {
/* 3416 */     return this.fluidHeight.getDouble($$0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getFluidJumpThreshold() {
/* 3423 */     return (getEyeHeight() < 0.4D) ? 0.0D : 0.4D;
/*      */   }
/*      */   
/*      */   public final float getBbWidth() {
/* 3427 */     return this.dimensions.width;
/*      */   }
/*      */   
/*      */   public final float getBbHeight() {
/* 3431 */     return this.dimensions.height;
/*      */   }
/*      */   
/*      */   public float getNameTagOffsetY() {
/* 3435 */     return getBbHeight() + 0.5F;
/*      */   }
/*      */   
/*      */   public Packet<ClientGamePacketListener> getAddEntityPacket() {
/* 3439 */     return (Packet<ClientGamePacketListener>)new ClientboundAddEntityPacket(this);
/*      */   }
/*      */   
/*      */   public EntityDimensions getDimensions(Pose $$0) {
/* 3443 */     return this.type.getDimensions();
/*      */   }
/*      */   
/*      */   public Vec3 position() {
/* 3447 */     return this.position;
/*      */   }
/*      */   
/*      */   public Vec3 trackingPosition() {
/* 3451 */     return position();
/*      */   }
/*      */ 
/*      */   
/*      */   public BlockPos blockPosition() {
/* 3456 */     return this.blockPosition;
/*      */   }
/*      */   
/*      */   public BlockState getFeetBlockState() {
/* 3460 */     if (this.feetBlockState == null) {
/* 3461 */       this.feetBlockState = level().getBlockState(blockPosition());
/*      */     }
/* 3463 */     return this.feetBlockState;
/*      */   }
/*      */   
/*      */   public ChunkPos chunkPosition() {
/* 3467 */     return this.chunkPosition;
/*      */   }
/*      */   
/*      */   public Vec3 getDeltaMovement() {
/* 3471 */     return this.deltaMovement;
/*      */   }
/*      */   
/*      */   public void setDeltaMovement(Vec3 $$0) {
/* 3475 */     this.deltaMovement = $$0;
/*      */   }
/*      */   
/*      */   public void addDeltaMovement(Vec3 $$0) {
/* 3479 */     setDeltaMovement(getDeltaMovement().add($$0));
/*      */   }
/*      */   
/*      */   public void setDeltaMovement(double $$0, double $$1, double $$2) {
/* 3483 */     setDeltaMovement(new Vec3($$0, $$1, $$2));
/*      */   }
/*      */   
/*      */   public final int getBlockX() {
/* 3487 */     return this.blockPosition.getX();
/*      */   }
/*      */   
/*      */   public final double getX() {
/* 3491 */     return this.position.x;
/*      */   }
/*      */   
/*      */   public double getX(double $$0) {
/* 3495 */     return this.position.x + getBbWidth() * $$0;
/*      */   }
/*      */   
/*      */   public double getRandomX(double $$0) {
/* 3499 */     return getX((2.0D * this.random.nextDouble() - 1.0D) * $$0);
/*      */   }
/*      */   
/*      */   public final int getBlockY() {
/* 3503 */     return this.blockPosition.getY();
/*      */   }
/*      */   
/*      */   public final double getY() {
/* 3507 */     return this.position.y;
/*      */   }
/*      */   
/*      */   public double getY(double $$0) {
/* 3511 */     return this.position.y + getBbHeight() * $$0;
/*      */   }
/*      */   
/*      */   public double getRandomY() {
/* 3515 */     return getY(this.random.nextDouble());
/*      */   }
/*      */   
/*      */   public double getEyeY() {
/* 3519 */     return this.position.y + this.eyeHeight;
/*      */   }
/*      */   
/*      */   public final int getBlockZ() {
/* 3523 */     return this.blockPosition.getZ();
/*      */   }
/*      */   
/*      */   public final double getZ() {
/* 3527 */     return this.position.z;
/*      */   }
/*      */   
/*      */   public double getZ(double $$0) {
/* 3531 */     return this.position.z + getBbWidth() * $$0;
/*      */   }
/*      */   
/*      */   public double getRandomZ(double $$0) {
/* 3535 */     return getZ((2.0D * this.random.nextDouble() - 1.0D) * $$0);
/*      */   }
/*      */   
/*      */   public final void setPosRaw(double $$0, double $$1, double $$2) {
/* 3539 */     if (this.position.x != $$0 || this.position.y != $$1 || this.position.z != $$2) {
/* 3540 */       this.position = new Vec3($$0, $$1, $$2);
/*      */       
/* 3542 */       int $$3 = Mth.floor($$0);
/* 3543 */       int $$4 = Mth.floor($$1);
/* 3544 */       int $$5 = Mth.floor($$2);
/* 3545 */       if ($$3 != this.blockPosition.getX() || $$4 != this.blockPosition.getY() || $$5 != this.blockPosition.getZ()) {
/* 3546 */         this.blockPosition = new BlockPos($$3, $$4, $$5);
/* 3547 */         this.feetBlockState = null;
/* 3548 */         if (SectionPos.blockToSectionCoord($$3) != this.chunkPosition.x || SectionPos.blockToSectionCoord($$5) != this.chunkPosition.z) {
/* 3549 */           this.chunkPosition = new ChunkPos(this.blockPosition);
/*      */         }
/*      */       } 
/* 3552 */       this.levelCallback.onMove();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void checkDespawn() {}
/*      */   
/*      */   public Vec3 getRopeHoldPosition(float $$0) {
/* 3560 */     return getPosition($$0).add(0.0D, this.eyeHeight * 0.7D, 0.0D);
/*      */   }
/*      */   
/*      */   public void recreateFromPacket(ClientboundAddEntityPacket $$0) {
/* 3564 */     int $$1 = $$0.getId();
/* 3565 */     double $$2 = $$0.getX();
/* 3566 */     double $$3 = $$0.getY();
/* 3567 */     double $$4 = $$0.getZ();
/* 3568 */     syncPacketPositionCodec($$2, $$3, $$4);
/* 3569 */     moveTo($$2, $$3, $$4);
/* 3570 */     setXRot($$0.getXRot());
/* 3571 */     setYRot($$0.getYRot());
/* 3572 */     setId($$1);
/* 3573 */     setUUID($$0.getUUID());
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public ItemStack getPickResult() {
/* 3578 */     return null;
/*      */   }
/*      */   
/*      */   public void setIsInPowderSnow(boolean $$0) {
/* 3582 */     this.isInPowderSnow = $$0;
/*      */   }
/*      */   
/*      */   public boolean canFreeze() {
/* 3586 */     return !getType().is(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES);
/*      */   }
/*      */   
/*      */   public boolean isFreezing() {
/* 3590 */     return ((this.isInPowderSnow || this.wasInPowderSnow) && canFreeze());
/*      */   }
/*      */   
/*      */   public float getYRot() {
/* 3594 */     return this.yRot;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getVisualRotationYInDegrees() {
/* 3603 */     return getYRot();
/*      */   }
/*      */   
/*      */   public void setYRot(float $$0) {
/* 3607 */     if (!Float.isFinite($$0)) {
/* 3608 */       Util.logAndPauseIfInIde("Invalid entity rotation: " + $$0 + ", discarding.");
/*      */       return;
/*      */     } 
/* 3611 */     this.yRot = $$0;
/*      */   }
/*      */   
/*      */   public float getXRot() {
/* 3615 */     return this.xRot;
/*      */   }
/*      */   
/*      */   public void setXRot(float $$0) {
/* 3619 */     if (!Float.isFinite($$0)) {
/* 3620 */       Util.logAndPauseIfInIde("Invalid entity rotation: " + $$0 + ", discarding.");
/*      */       return;
/*      */     } 
/* 3623 */     this.xRot = $$0;
/*      */   }
/*      */   
/*      */   public boolean canSprint() {
/* 3627 */     return false;
/*      */   }
/*      */   
/*      */   public float maxUpStep() {
/* 3631 */     return this.maxUpStep;
/*      */   }
/*      */   
/*      */   public void setMaxUpStep(float $$0) {
/* 3635 */     this.maxUpStep = $$0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean isRemoved() {
/* 3644 */     return (this.removalReason != null);
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public RemovalReason getRemovalReason() {
/* 3649 */     return this.removalReason;
/*      */   }
/*      */ 
/*      */   
/*      */   public final void setRemoved(RemovalReason $$0) {
/* 3654 */     if (this.removalReason == null) {
/* 3655 */       this.removalReason = $$0;
/*      */     }
/* 3657 */     if (this.removalReason.shouldDestroy()) {
/* 3658 */       stopRiding();
/*      */     }
/* 3660 */     getPassengers().forEach(Entity::stopRiding);
/* 3661 */     this.levelCallback.onRemove($$0);
/*      */   } @FunctionalInterface
/*      */   public static interface MoveFunction {
/*      */     void accept(Entity param1Entity, double param1Double1, double param1Double2, double param1Double3); }
/*      */   protected void unsetRemoved() {
/* 3666 */     this.removalReason = null;
/*      */   }
/*      */   
/*      */   public enum MovementEmission {
/* 3670 */     NONE(false, false),
/* 3671 */     SOUNDS(true, false),
/* 3672 */     EVENTS(false, true),
/* 3673 */     ALL(true, true);
/*      */     
/*      */     final boolean sounds;
/*      */     final boolean events;
/*      */     
/*      */     MovementEmission(boolean $$0, boolean $$1) {
/* 3679 */       this.sounds = $$0;
/* 3680 */       this.events = $$1;
/*      */     }
/*      */     
/*      */     public boolean emitsAnything() {
/* 3684 */       return (this.events || this.sounds);
/*      */     }
/*      */     
/*      */     public boolean emitsEvents() {
/* 3688 */       return this.events;
/*      */     }
/*      */     
/*      */     public boolean emitsSounds() {
/* 3692 */       return this.sounds;
/*      */     }
/*      */   }
/*      */   
/*      */   public enum RemovalReason
/*      */   {
/* 3698 */     KILLED(true, false),
/*      */     
/* 3700 */     DISCARDED(true, false),
/*      */     
/* 3702 */     UNLOADED_TO_CHUNK(false, true),
/*      */     
/* 3704 */     UNLOADED_WITH_PLAYER(false, false),
/*      */     
/* 3706 */     CHANGED_DIMENSION(false, false);
/*      */     
/*      */     private final boolean destroy;
/*      */     
/*      */     private final boolean save;
/*      */ 
/*      */     
/*      */     RemovalReason(boolean $$0, boolean $$1) {
/* 3714 */       this.destroy = $$0;
/* 3715 */       this.save = $$1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean shouldDestroy() {
/* 3722 */       return this.destroy;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean shouldSave() {
/* 3729 */       return this.save;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void setLevelCallback(EntityInLevelCallback $$0) {
/* 3735 */     this.levelCallback = $$0;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean shouldBeSaved() {
/* 3740 */     if (this.removalReason != null && !this.removalReason.shouldSave()) {
/* 3741 */       return false;
/*      */     }
/* 3743 */     if (isPassenger()) {
/* 3744 */       return false;
/*      */     }
/* 3746 */     if (isVehicle() && hasExactlyOnePlayerPassenger()) {
/* 3747 */       return false;
/*      */     }
/* 3749 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isAlwaysTicking() {
/* 3754 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean mayInteract(Level $$0, BlockPos $$1) {
/* 3761 */     return true;
/*      */   }
/*      */   
/*      */   public Level level() {
/* 3765 */     return this.level;
/*      */   }
/*      */   
/*      */   protected void setLevel(Level $$0) {
/* 3769 */     this.level = $$0;
/*      */   }
/*      */   
/*      */   public DamageSources damageSources() {
/* 3773 */     return level().damageSources();
/*      */   }
/*      */   
/*      */   protected void lerpPositionAndRotationStep(int $$0, double $$1, double $$2, double $$3, double $$4, double $$5) {
/* 3777 */     double $$6 = 1.0D / $$0;
/*      */     
/* 3779 */     double $$7 = Mth.lerp($$6, getX(), $$1);
/* 3780 */     double $$8 = Mth.lerp($$6, getY(), $$2);
/* 3781 */     double $$9 = Mth.lerp($$6, getZ(), $$3);
/*      */     
/* 3783 */     float $$10 = (float)Mth.rotLerp($$6, getYRot(), $$4);
/*      */     
/* 3785 */     float $$11 = (float)Mth.lerp($$6, getXRot(), $$5);
/*      */     
/* 3787 */     setPos($$7, $$8, $$9);
/* 3788 */     setRot($$10, $$11);
/*      */   }
/*      */   
/*      */   protected abstract void defineSynchedData();
/*      */   
/*      */   protected abstract void readAdditionalSaveData(CompoundTag paramCompoundTag);
/*      */   
/*      */   protected abstract void addAdditionalSaveData(CompoundTag paramCompoundTag);
/*      */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\Entity.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */