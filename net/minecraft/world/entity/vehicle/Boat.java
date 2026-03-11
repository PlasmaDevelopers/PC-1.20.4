/*     */ package net.minecraft.world.entity.vehicle;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.UnmodifiableIterator;
/*     */ import java.util.List;
/*     */ import java.util.function.IntFunction;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.BlockUtil;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.network.protocol.game.ServerboundPaddleBoatPacket;
/*     */ import net.minecraft.network.syncher.EntityDataAccessor;
/*     */ import net.minecraft.network.syncher.EntityDataSerializers;
/*     */ import net.minecraft.network.syncher.SynchedEntityData;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.tags.EntityTypeTags;
/*     */ import net.minecraft.tags.FluidTags;
/*     */ import net.minecraft.util.ByIdMap;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.StringRepresentable;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityDimensions;
/*     */ import net.minecraft.world.entity.EntitySelector;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.MoverType;
/*     */ import net.minecraft.world.entity.Pose;
/*     */ import net.minecraft.world.entity.VariantHolder;
/*     */ import net.minecraft.world.entity.animal.Animal;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.CollisionGetter;
/*     */ import net.minecraft.world.level.GameRules;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import net.minecraft.world.phys.shapes.BooleanOp;
/*     */ import net.minecraft.world.phys.shapes.Shapes;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ import org.joml.Vector3f;
/*     */ 
/*     */ public class Boat extends VehicleEntity implements VariantHolder<Boat.Type> {
/*  59 */   private static final EntityDataAccessor<Integer> DATA_ID_TYPE = SynchedEntityData.defineId(Boat.class, EntityDataSerializers.INT);
/*  60 */   private static final EntityDataAccessor<Boolean> DATA_ID_PADDLE_LEFT = SynchedEntityData.defineId(Boat.class, EntityDataSerializers.BOOLEAN);
/*  61 */   private static final EntityDataAccessor<Boolean> DATA_ID_PADDLE_RIGHT = SynchedEntityData.defineId(Boat.class, EntityDataSerializers.BOOLEAN);
/*  62 */   private static final EntityDataAccessor<Integer> DATA_ID_BUBBLE_TIME = SynchedEntityData.defineId(Boat.class, EntityDataSerializers.INT);
/*     */   
/*     */   public static final int PADDLE_LEFT = 0;
/*     */   public static final int PADDLE_RIGHT = 1;
/*     */   private static final int TIME_TO_EJECT = 60;
/*     */   private static final float PADDLE_SPEED = 0.3926991F;
/*     */   public static final double PADDLE_SOUND_TIME = 0.7853981852531433D;
/*     */   public static final int BUBBLE_TIME = 60;
/*  70 */   private final float[] paddlePositions = new float[2];
/*     */   
/*     */   private float invFriction;
/*     */   
/*     */   private float outOfControlTicks;
/*     */   private float deltaRotation;
/*     */   private int lerpSteps;
/*     */   private double lerpX;
/*     */   private double lerpY;
/*     */   private double lerpZ;
/*     */   private double lerpYRot;
/*     */   private double lerpXRot;
/*     */   private boolean inputLeft;
/*     */   private boolean inputRight;
/*     */   private boolean inputUp;
/*     */   private boolean inputDown;
/*     */   private double waterLevel;
/*     */   private float landFriction;
/*     */   private Status status;
/*     */   private Status oldStatus;
/*     */   private double lastYd;
/*     */   private boolean isAboveBubbleColumn;
/*     */   private boolean bubbleColumnDirectionIsDown;
/*     */   private float bubbleMultiplier;
/*     */   private float bubbleAngle;
/*     */   private float bubbleAngleO;
/*     */   
/*     */   public Boat(EntityType<? extends Boat> $$0, Level $$1) {
/*  98 */     super($$0, $$1);
/*  99 */     this.blocksBuilding = true;
/*     */   }
/*     */   
/*     */   public Boat(Level $$0, double $$1, double $$2, double $$3) {
/* 103 */     this(EntityType.BOAT, $$0);
/* 104 */     setPos($$1, $$2, $$3);
/*     */     
/* 106 */     this.xo = $$1;
/* 107 */     this.yo = $$2;
/* 108 */     this.zo = $$3;
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getEyeHeight(Pose $$0, EntityDimensions $$1) {
/* 113 */     return $$1.height;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Entity.MovementEmission getMovementEmission() {
/* 118 */     return Entity.MovementEmission.EVENTS;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {
/* 123 */     super.defineSynchedData();
/* 124 */     this.entityData.define(DATA_ID_TYPE, Integer.valueOf(Type.OAK.ordinal()));
/* 125 */     this.entityData.define(DATA_ID_PADDLE_LEFT, Boolean.valueOf(false));
/* 126 */     this.entityData.define(DATA_ID_PADDLE_RIGHT, Boolean.valueOf(false));
/* 127 */     this.entityData.define(DATA_ID_BUBBLE_TIME, Integer.valueOf(0));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canCollideWith(Entity $$0) {
/* 132 */     return canVehicleCollide(this, $$0);
/*     */   }
/*     */   
/*     */   public static boolean canVehicleCollide(Entity $$0, Entity $$1) {
/* 136 */     return (($$1.canBeCollidedWith() || $$1.isPushable()) && !$$0.isPassengerOfSameVehicle($$1));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canBeCollidedWith() {
/* 141 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPushable() {
/* 146 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Vec3 getRelativePortalPosition(Direction.Axis $$0, BlockUtil.FoundRectangle $$1) {
/* 151 */     return LivingEntity.resetForwardDirectionOfRelativePortalPosition(super.getRelativePortalPosition($$0, $$1));
/*     */   }
/*     */ 
/*     */   
/*     */   protected Vector3f getPassengerAttachmentPoint(Entity $$0, EntityDimensions $$1, float $$2) {
/* 156 */     float $$3 = getSinglePassengerXOffset();
/* 157 */     if (getPassengers().size() > 1) {
/* 158 */       int $$4 = getPassengers().indexOf($$0);
/* 159 */       if ($$4 == 0) {
/* 160 */         $$3 = 0.2F;
/*     */       } else {
/* 162 */         $$3 = -0.6F;
/*     */       } 
/*     */       
/* 165 */       if ($$0 instanceof Animal) {
/* 166 */         $$3 += 0.2F;
/*     */       }
/*     */     } 
/*     */     
/* 170 */     return new Vector3f(0.0F, (getVariant() == Type.BAMBOO) ? ($$1.height * 0.8888889F) : ($$1.height / 3.0F), $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onAboveBubbleCol(boolean $$0) {
/* 175 */     if (!(level()).isClientSide) {
/* 176 */       this.isAboveBubbleColumn = true;
/* 177 */       this.bubbleColumnDirectionIsDown = $$0;
/* 178 */       if (getBubbleTime() == 0) {
/* 179 */         setBubbleTime(60);
/*     */       }
/*     */     } 
/*     */     
/* 183 */     level().addParticle((ParticleOptions)ParticleTypes.SPLASH, getX() + this.random.nextFloat(), getY() + 0.7D, getZ() + this.random.nextFloat(), 0.0D, 0.0D, 0.0D);
/* 184 */     if (this.random.nextInt(20) == 0) {
/* 185 */       level().playLocalSound(getX(), getY(), getZ(), getSwimSplashSound(), getSoundSource(), 1.0F, 0.8F + 0.4F * this.random.nextFloat(), false);
/* 186 */       gameEvent(GameEvent.SPLASH, (Entity)getControllingPassenger());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void push(Entity $$0) {
/* 192 */     if ($$0 instanceof Boat) {
/* 193 */       if (($$0.getBoundingBox()).minY < (getBoundingBox()).maxY) {
/* 194 */         super.push($$0);
/*     */       }
/* 196 */     } else if (($$0.getBoundingBox()).minY <= (getBoundingBox()).minY) {
/* 197 */       super.push($$0);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Item getDropItem() {
/* 203 */     switch (getVariant()) { case IN_WATER: case UNDER_WATER: case UNDER_FLOWING_WATER: case ON_LAND: case IN_AIR: case null: case null: case null:  }  return 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 212 */       Items.OAK_BOAT;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void animateHurt(float $$0) {
/* 218 */     setHurtDir(-getHurtDir());
/* 219 */     setHurtTime(10);
/* 220 */     setDamage(getDamage() * 11.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPickable() {
/* 225 */     return !isRemoved();
/*     */   }
/*     */ 
/*     */   
/*     */   public void lerpTo(double $$0, double $$1, double $$2, float $$3, float $$4, int $$5) {
/* 230 */     this.lerpX = $$0;
/* 231 */     this.lerpY = $$1;
/* 232 */     this.lerpZ = $$2;
/* 233 */     this.lerpYRot = $$3;
/* 234 */     this.lerpXRot = $$4;
/* 235 */     this.lerpSteps = 10;
/*     */   }
/*     */ 
/*     */   
/*     */   public double lerpTargetX() {
/* 240 */     return (this.lerpSteps > 0) ? this.lerpX : getX();
/*     */   }
/*     */ 
/*     */   
/*     */   public double lerpTargetY() {
/* 245 */     return (this.lerpSteps > 0) ? this.lerpY : getY();
/*     */   }
/*     */ 
/*     */   
/*     */   public double lerpTargetZ() {
/* 250 */     return (this.lerpSteps > 0) ? this.lerpZ : getZ();
/*     */   }
/*     */ 
/*     */   
/*     */   public float lerpTargetXRot() {
/* 255 */     return (this.lerpSteps > 0) ? (float)this.lerpXRot : getXRot();
/*     */   }
/*     */ 
/*     */   
/*     */   public float lerpTargetYRot() {
/* 260 */     return (this.lerpSteps > 0) ? (float)this.lerpYRot : getYRot();
/*     */   }
/*     */ 
/*     */   
/*     */   public Direction getMotionDirection() {
/* 265 */     return getDirection().getClockWise();
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 270 */     this.oldStatus = this.status;
/* 271 */     this.status = getStatus();
/*     */     
/* 273 */     if (this.status == Status.UNDER_WATER || this.status == Status.UNDER_FLOWING_WATER) {
/* 274 */       this.outOfControlTicks++;
/*     */     } else {
/* 276 */       this.outOfControlTicks = 0.0F;
/*     */     } 
/*     */     
/* 279 */     if (!(level()).isClientSide && this.outOfControlTicks >= 60.0F) {
/* 280 */       ejectPassengers();
/*     */     }
/*     */     
/* 283 */     if (getHurtTime() > 0) {
/* 284 */       setHurtTime(getHurtTime() - 1);
/*     */     }
/* 286 */     if (getDamage() > 0.0F) {
/* 287 */       setDamage(getDamage() - 1.0F);
/*     */     }
/*     */     
/* 290 */     super.tick();
/* 291 */     tickLerp();
/*     */     
/* 293 */     if (isControlledByLocalInstance()) {
/* 294 */       if (!(getFirstPassenger() instanceof Player)) {
/* 295 */         setPaddleState(false, false);
/*     */       }
/*     */       
/* 298 */       floatBoat();
/* 299 */       if ((level()).isClientSide) {
/* 300 */         controlBoat();
/* 301 */         level().sendPacketToServer((Packet)new ServerboundPaddleBoatPacket(getPaddleState(0), getPaddleState(1)));
/*     */       } 
/* 303 */       move(MoverType.SELF, getDeltaMovement());
/*     */     } else {
/* 305 */       setDeltaMovement(Vec3.ZERO);
/*     */     } 
/*     */     
/* 308 */     tickBubbleColumn();
/*     */     
/* 310 */     for (int $$0 = 0; $$0 <= 1; $$0++) {
/* 311 */       if (getPaddleState($$0)) {
/* 312 */         if (!isSilent() && (this.paddlePositions[$$0] % 6.2831855F) <= 0.7853981852531433D && ((this.paddlePositions[$$0] + 0.3926991F) % 6.2831855F) >= 0.7853981852531433D) {
/* 313 */           SoundEvent $$1 = getPaddleSound();
/* 314 */           if ($$1 != null) {
/* 315 */             Vec3 $$2 = getViewVector(1.0F);
/* 316 */             double $$3 = ($$0 == 1) ? -$$2.z : $$2.z;
/* 317 */             double $$4 = ($$0 == 1) ? $$2.x : -$$2.x;
/*     */             
/* 319 */             level().playSound(null, getX() + $$3, getY(), getZ() + $$4, $$1, getSoundSource(), 1.0F, 0.8F + 0.4F * this.random.nextFloat());
/*     */           } 
/*     */         } 
/* 322 */         this.paddlePositions[$$0] = this.paddlePositions[$$0] + 0.3926991F;
/*     */       } else {
/* 324 */         this.paddlePositions[$$0] = 0.0F;
/*     */       } 
/*     */     } 
/*     */     
/* 328 */     checkInsideBlocks();
/*     */     
/* 330 */     List<Entity> $$5 = level().getEntities(this, getBoundingBox().inflate(0.20000000298023224D, -0.009999999776482582D, 0.20000000298023224D), EntitySelector.pushableBy(this));
/*     */     
/* 332 */     if (!$$5.isEmpty()) {
/* 333 */       boolean $$6 = (!(level()).isClientSide && !(getControllingPassenger() instanceof Player));
/* 334 */       for (Entity $$7 : $$5) {
/* 335 */         if ($$7.hasPassenger(this)) {
/*     */           continue;
/*     */         }
/*     */         
/* 339 */         if ($$6 && 
/* 340 */           getPassengers().size() < getMaxPassengers() && 
/* 341 */           !$$7.isPassenger() && 
/* 342 */           hasEnoughSpaceFor($$7) && $$7 instanceof LivingEntity && !($$7 instanceof net.minecraft.world.entity.animal.WaterAnimal) && !($$7 instanceof Player)) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 347 */           $$7.startRiding(this); continue;
/*     */         } 
/* 349 */         push($$7);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void tickBubbleColumn() {
/* 356 */     if ((level()).isClientSide) {
/* 357 */       int $$0 = getBubbleTime();
/* 358 */       if ($$0 > 0) {
/* 359 */         this.bubbleMultiplier += 0.05F;
/*     */       } else {
/* 361 */         this.bubbleMultiplier -= 0.1F;
/*     */       } 
/* 363 */       this.bubbleMultiplier = Mth.clamp(this.bubbleMultiplier, 0.0F, 1.0F);
/*     */       
/* 365 */       this.bubbleAngleO = this.bubbleAngle;
/* 366 */       this.bubbleAngle = 10.0F * (float)Math.sin((0.5F * (float)level().getGameTime())) * this.bubbleMultiplier;
/*     */     } else {
/* 368 */       if (!this.isAboveBubbleColumn) {
/* 369 */         setBubbleTime(0);
/*     */       }
/*     */       
/* 372 */       int $$1 = getBubbleTime();
/* 373 */       if ($$1 > 0) {
/* 374 */         $$1--;
/* 375 */         setBubbleTime($$1);
/*     */         
/* 377 */         int $$2 = 60 - $$1 - 1;
/* 378 */         if ($$2 > 0 && 
/* 379 */           $$1 == 0) {
/* 380 */           setBubbleTime(0);
/* 381 */           Vec3 $$3 = getDeltaMovement();
/* 382 */           if (this.bubbleColumnDirectionIsDown) {
/* 383 */             setDeltaMovement($$3.add(0.0D, -0.7D, 0.0D));
/* 384 */             ejectPassengers();
/*     */           } else {
/* 386 */             setDeltaMovement($$3.x, hasPassenger($$0 -> $$0 instanceof Player) ? 2.7D : 0.6D, $$3.z);
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 391 */         this.isAboveBubbleColumn = false;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected SoundEvent getPaddleSound() {
/* 398 */     switch (getStatus()) {
/*     */       case IN_WATER:
/*     */       case UNDER_WATER:
/*     */       case UNDER_FLOWING_WATER:
/* 402 */         return SoundEvents.BOAT_PADDLE_WATER;
/*     */       case ON_LAND:
/* 404 */         return SoundEvents.BOAT_PADDLE_LAND;
/*     */     } 
/*     */     
/* 407 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private void tickLerp() {
/* 412 */     if (isControlledByLocalInstance()) {
/* 413 */       this.lerpSteps = 0;
/* 414 */       syncPacketPositionCodec(getX(), getY(), getZ());
/*     */     } 
/* 416 */     if (this.lerpSteps <= 0) {
/*     */       return;
/*     */     }
/*     */     
/* 420 */     lerpPositionAndRotationStep(this.lerpSteps, this.lerpX, this.lerpY, this.lerpZ, this.lerpYRot, this.lerpXRot);
/* 421 */     this.lerpSteps--;
/*     */   }
/*     */   
/*     */   public void setPaddleState(boolean $$0, boolean $$1) {
/* 425 */     this.entityData.set(DATA_ID_PADDLE_LEFT, Boolean.valueOf($$0));
/* 426 */     this.entityData.set(DATA_ID_PADDLE_RIGHT, Boolean.valueOf($$1));
/*     */   }
/*     */   
/*     */   public float getRowingTime(int $$0, float $$1) {
/* 430 */     if (getPaddleState($$0)) {
/* 431 */       return Mth.clampedLerp(this.paddlePositions[$$0] - 0.3926991F, this.paddlePositions[$$0], $$1);
/*     */     }
/* 433 */     return 0.0F;
/*     */   }
/*     */   
/*     */   public enum Status {
/* 437 */     IN_WATER,
/* 438 */     UNDER_WATER,
/* 439 */     UNDER_FLOWING_WATER,
/* 440 */     ON_LAND,
/* 441 */     IN_AIR;
/*     */   }
/*     */   
/*     */   private Status getStatus() {
/* 445 */     Status $$0 = isUnderwater();
/* 446 */     if ($$0 != null) {
/* 447 */       this.waterLevel = (getBoundingBox()).maxY;
/* 448 */       return $$0;
/*     */     } 
/*     */     
/* 451 */     if (checkInWater()) {
/* 452 */       return Status.IN_WATER;
/*     */     }
/*     */     
/* 455 */     float $$1 = getGroundFriction();
/* 456 */     if ($$1 > 0.0F) {
/* 457 */       this.landFriction = $$1;
/* 458 */       return Status.ON_LAND;
/*     */     } 
/*     */     
/* 461 */     return Status.IN_AIR;
/*     */   }
/*     */   
/*     */   public float getWaterLevelAbove() {
/* 465 */     AABB $$0 = getBoundingBox();
/* 466 */     int $$1 = Mth.floor($$0.minX);
/* 467 */     int $$2 = Mth.ceil($$0.maxX);
/* 468 */     int $$3 = Mth.floor($$0.maxY);
/* 469 */     int $$4 = Mth.ceil($$0.maxY - this.lastYd);
/* 470 */     int $$5 = Mth.floor($$0.minZ);
/* 471 */     int $$6 = Mth.ceil($$0.maxZ);
/*     */     
/* 473 */     BlockPos.MutableBlockPos $$7 = new BlockPos.MutableBlockPos();
/*     */     int $$8;
/* 475 */     label24: for ($$8 = $$3; $$8 < $$4; $$8++) {
/* 476 */       float $$9 = 0.0F;
/* 477 */       for (int $$10 = $$1; $$10 < $$2; $$10++) {
/* 478 */         for (int $$11 = $$5; $$11 < $$6; $$11++) {
/* 479 */           $$7.set($$10, $$8, $$11);
/* 480 */           FluidState $$12 = level().getFluidState((BlockPos)$$7);
/* 481 */           if ($$12.is(FluidTags.WATER)) {
/* 482 */             $$9 = Math.max($$9, $$12.getHeight((BlockGetter)level(), (BlockPos)$$7));
/*     */           }
/* 484 */           if ($$9 >= 1.0F) {
/*     */             continue label24;
/*     */           }
/*     */         } 
/*     */       } 
/* 489 */       if ($$9 < 1.0F) {
/* 490 */         return $$7.getY() + $$9;
/*     */       }
/*     */     } 
/* 493 */     return ($$4 + 1);
/*     */   }
/*     */   
/*     */   public float getGroundFriction() {
/* 497 */     AABB $$0 = getBoundingBox();
/* 498 */     AABB $$1 = new AABB($$0.minX, $$0.minY - 0.001D, $$0.minZ, $$0.maxX, $$0.minY, $$0.maxZ);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 508 */     int $$2 = Mth.floor($$1.minX) - 1;
/* 509 */     int $$3 = Mth.ceil($$1.maxX) + 1;
/* 510 */     int $$4 = Mth.floor($$1.minY) - 1;
/* 511 */     int $$5 = Mth.ceil($$1.maxY) + 1;
/* 512 */     int $$6 = Mth.floor($$1.minZ) - 1;
/* 513 */     int $$7 = Mth.ceil($$1.maxZ) + 1;
/*     */     
/* 515 */     VoxelShape $$8 = Shapes.create($$1);
/* 516 */     float $$9 = 0.0F;
/* 517 */     int $$10 = 0;
/*     */     
/* 519 */     BlockPos.MutableBlockPos $$11 = new BlockPos.MutableBlockPos();
/* 520 */     for (int $$12 = $$2; $$12 < $$3; $$12++) {
/* 521 */       for (int $$13 = $$6; $$13 < $$7; $$13++) {
/*     */         
/* 523 */         int $$14 = (($$12 == $$2 || $$12 == $$3 - 1) ? 1 : 0) + (($$13 == $$6 || $$13 == $$7 - 1) ? 1 : 0);
/* 524 */         if ($$14 != 2)
/*     */         {
/*     */ 
/*     */           
/* 528 */           for (int $$15 = $$4; $$15 < $$5; $$15++) {
/*     */             
/* 530 */             if ($$14 <= 0 || ($$15 != $$4 && $$15 != $$5 - 1)) {
/*     */ 
/*     */ 
/*     */               
/* 534 */               $$11.set($$12, $$15, $$13);
/*     */               
/* 536 */               BlockState $$16 = level().getBlockState((BlockPos)$$11);
/* 537 */               if (!($$16.getBlock() instanceof net.minecraft.world.level.block.WaterlilyBlock))
/*     */               {
/*     */                 
/* 540 */                 if (Shapes.joinIsNotEmpty($$16.getCollisionShape((BlockGetter)level(), (BlockPos)$$11).move($$12, $$15, $$13), $$8, BooleanOp.AND)) {
/* 541 */                   $$9 += $$16.getBlock().getFriction();
/* 542 */                   $$10++;
/*     */                 }  } 
/*     */             } 
/*     */           }  } 
/*     */       } 
/*     */     } 
/* 548 */     return $$9 / $$10;
/*     */   }
/*     */   private boolean checkInWater() {
/*     */     int i;
/* 552 */     AABB $$0 = getBoundingBox();
/* 553 */     int $$1 = Mth.floor($$0.minX);
/* 554 */     int $$2 = Mth.ceil($$0.maxX);
/* 555 */     int $$3 = Mth.floor($$0.minY);
/* 556 */     int $$4 = Mth.ceil($$0.minY + 0.001D);
/* 557 */     int $$5 = Mth.floor($$0.minZ);
/* 558 */     int $$6 = Mth.ceil($$0.maxZ);
/*     */     
/* 560 */     boolean $$7 = false;
/* 561 */     this.waterLevel = -1.7976931348623157E308D;
/*     */     
/* 563 */     BlockPos.MutableBlockPos $$8 = new BlockPos.MutableBlockPos();
/* 564 */     for (int $$9 = $$1; $$9 < $$2; $$9++) {
/* 565 */       for (int $$10 = $$3; $$10 < $$4; $$10++) {
/* 566 */         for (int $$11 = $$5; $$11 < $$6; $$11++) {
/* 567 */           $$8.set($$9, $$10, $$11);
/* 568 */           FluidState $$12 = level().getFluidState((BlockPos)$$8);
/*     */           
/* 570 */           if ($$12.is(FluidTags.WATER)) {
/*     */ 
/*     */ 
/*     */             
/* 574 */             float $$13 = $$10 + $$12.getHeight((BlockGetter)level(), (BlockPos)$$8);
/* 575 */             this.waterLevel = Math.max($$13, this.waterLevel);
/* 576 */             i = $$7 | (($$0.minY < $$13) ? 1 : 0);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 581 */     return i;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private Status isUnderwater() {
/* 586 */     AABB $$0 = getBoundingBox();
/* 587 */     double $$1 = $$0.maxY + 0.001D;
/*     */     
/* 589 */     int $$2 = Mth.floor($$0.minX);
/* 590 */     int $$3 = Mth.ceil($$0.maxX);
/* 591 */     int $$4 = Mth.floor($$0.maxY);
/* 592 */     int $$5 = Mth.ceil($$1);
/* 593 */     int $$6 = Mth.floor($$0.minZ);
/* 594 */     int $$7 = Mth.ceil($$0.maxZ);
/*     */     
/* 596 */     boolean $$8 = false;
/* 597 */     BlockPos.MutableBlockPos $$9 = new BlockPos.MutableBlockPos();
/* 598 */     for (int $$10 = $$2; $$10 < $$3; $$10++) {
/* 599 */       for (int $$11 = $$4; $$11 < $$5; $$11++) {
/* 600 */         for (int $$12 = $$6; $$12 < $$7; $$12++) {
/* 601 */           $$9.set($$10, $$11, $$12);
/* 602 */           FluidState $$13 = level().getFluidState((BlockPos)$$9);
/* 603 */           if ($$13.is(FluidTags.WATER) && 
/* 604 */             $$1 < ($$9.getY() + $$13.getHeight((BlockGetter)level(), (BlockPos)$$9))) {
/* 605 */             if ($$13.isSource()) {
/* 606 */               $$8 = true;
/*     */             } else {
/* 608 */               return Status.UNDER_FLOWING_WATER;
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 616 */     return $$8 ? Status.UNDER_WATER : null;
/*     */   }
/*     */   
/*     */   private void floatBoat() {
/* 620 */     double $$0 = -0.03999999910593033D;
/* 621 */     double $$1 = isNoGravity() ? 0.0D : -0.03999999910593033D;
/* 622 */     double $$2 = 0.0D;
/* 623 */     this.invFriction = 0.05F;
/*     */     
/* 625 */     if (this.oldStatus == Status.IN_AIR && this.status != Status.IN_AIR && this.status != Status.ON_LAND) {
/* 626 */       this.waterLevel = getY(1.0D);
/* 627 */       setPos(getX(), (getWaterLevelAbove() - getBbHeight()) + 0.101D, getZ());
/* 628 */       setDeltaMovement(getDeltaMovement().multiply(1.0D, 0.0D, 1.0D));
/* 629 */       this.lastYd = 0.0D;
/*     */       
/* 631 */       this.status = Status.IN_WATER;
/*     */     } else {
/* 633 */       if (this.status == Status.IN_WATER) {
/* 634 */         $$2 = (this.waterLevel - getY()) / getBbHeight();
/* 635 */         this.invFriction = 0.9F;
/* 636 */       } else if (this.status == Status.UNDER_FLOWING_WATER) {
/* 637 */         $$1 = -7.0E-4D;
/* 638 */         this.invFriction = 0.9F;
/* 639 */       } else if (this.status == Status.UNDER_WATER) {
/* 640 */         $$2 = 0.009999999776482582D;
/* 641 */         this.invFriction = 0.45F;
/* 642 */       } else if (this.status == Status.IN_AIR) {
/* 643 */         this.invFriction = 0.9F;
/* 644 */       } else if (this.status == Status.ON_LAND) {
/* 645 */         this.invFriction = this.landFriction;
/* 646 */         if (getControllingPassenger() instanceof Player) {
/* 647 */           this.landFriction /= 2.0F;
/*     */         }
/*     */       } 
/*     */       
/* 651 */       Vec3 $$3 = getDeltaMovement();
/* 652 */       setDeltaMovement($$3.x * this.invFriction, $$3.y + $$1, $$3.z * this.invFriction);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 657 */       this.deltaRotation *= this.invFriction;
/*     */       
/* 659 */       if ($$2 > 0.0D) {
/* 660 */         Vec3 $$4 = getDeltaMovement();
/* 661 */         setDeltaMovement($$4.x, ($$4.y + $$2 * 0.06153846016296973D) * 0.75D, $$4.z);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void controlBoat() {
/* 671 */     if (!isVehicle()) {
/*     */       return;
/*     */     }
/*     */     
/* 675 */     float $$0 = 0.0F;
/* 676 */     if (this.inputLeft) {
/* 677 */       this.deltaRotation--;
/*     */     }
/* 679 */     if (this.inputRight) {
/* 680 */       this.deltaRotation++;
/*     */     }
/* 682 */     if (this.inputRight != this.inputLeft && !this.inputUp && !this.inputDown) {
/* 683 */       $$0 += 0.005F;
/*     */     }
/* 685 */     setYRot(getYRot() + this.deltaRotation);
/*     */     
/* 687 */     if (this.inputUp) {
/* 688 */       $$0 += 0.04F;
/*     */     }
/* 690 */     if (this.inputDown) {
/* 691 */       $$0 -= 0.005F;
/*     */     }
/*     */     
/* 694 */     setDeltaMovement(getDeltaMovement().add((
/* 695 */           Mth.sin(-getYRot() * 0.017453292F) * $$0), 0.0D, (
/*     */           
/* 697 */           Mth.cos(getYRot() * 0.017453292F) * $$0)));
/*     */ 
/*     */     
/* 700 */     setPaddleState(((this.inputRight && !this.inputLeft) || this.inputUp), ((this.inputLeft && !this.inputRight) || this.inputUp));
/*     */   }
/*     */   
/*     */   protected float getSinglePassengerXOffset() {
/* 704 */     return 0.0F;
/*     */   }
/*     */   
/*     */   public boolean hasEnoughSpaceFor(Entity $$0) {
/* 708 */     return ($$0.getBbWidth() < getBbWidth());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void positionRider(Entity $$0, Entity.MoveFunction $$1) {
/* 713 */     super.positionRider($$0, $$1);
/*     */     
/* 715 */     if ($$0.getType().is(EntityTypeTags.CAN_TURN_IN_BOATS)) {
/*     */       return;
/*     */     }
/*     */     
/* 719 */     $$0.setYRot($$0.getYRot() + this.deltaRotation);
/* 720 */     $$0.setYHeadRot($$0.getYHeadRot() + this.deltaRotation);
/*     */     
/* 722 */     clampRotation($$0);
/*     */     
/* 724 */     if ($$0 instanceof Animal && getPassengers().size() == getMaxPassengers()) {
/* 725 */       int $$2 = ($$0.getId() % 2 == 0) ? 90 : 270;
/* 726 */       $$0.setYBodyRot(((Animal)$$0).yBodyRot + $$2);
/* 727 */       $$0.setYHeadRot($$0.getYHeadRot() + $$2);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Vec3 getDismountLocationForPassenger(LivingEntity $$0) {
/* 733 */     Vec3 $$1 = getCollisionHorizontalEscapeVector((getBbWidth() * Mth.SQRT_OF_TWO), $$0.getBbWidth(), $$0.getYRot());
/*     */     
/* 735 */     double $$2 = getX() + $$1.x;
/* 736 */     double $$3 = getZ() + $$1.z;
/*     */     
/* 738 */     BlockPos $$4 = BlockPos.containing($$2, (getBoundingBox()).maxY, $$3);
/* 739 */     BlockPos $$5 = $$4.below();
/*     */     
/* 741 */     if (!level().isWaterAt($$5)) {
/* 742 */       List<Vec3> $$6 = Lists.newArrayList();
/*     */       
/* 744 */       double $$7 = level().getBlockFloorHeight($$4);
/* 745 */       if (DismountHelper.isBlockFloorValid($$7)) {
/* 746 */         $$6.add(new Vec3($$2, $$4.getY() + $$7, $$3));
/*     */       }
/*     */       
/* 749 */       double $$8 = level().getBlockFloorHeight($$5);
/* 750 */       if (DismountHelper.isBlockFloorValid($$8)) {
/* 751 */         $$6.add(new Vec3($$2, $$5.getY() + $$8, $$3));
/*     */       }
/*     */       
/* 754 */       for (UnmodifiableIterator<Pose> unmodifiableIterator = $$0.getDismountPoses().iterator(); unmodifiableIterator.hasNext(); ) { Pose $$9 = unmodifiableIterator.next();
/* 755 */         for (Vec3 $$10 : $$6) {
/* 756 */           if (DismountHelper.canDismountTo((CollisionGetter)level(), $$10, $$0, $$9)) {
/* 757 */             $$0.setPose($$9);
/* 758 */             return $$10;
/*     */           } 
/*     */         }  }
/*     */     
/*     */     } 
/*     */     
/* 764 */     return super.getDismountLocationForPassenger($$0);
/*     */   }
/*     */   
/*     */   protected void clampRotation(Entity $$0) {
/* 768 */     $$0.setYBodyRot(getYRot());
/*     */     
/* 770 */     float $$1 = Mth.wrapDegrees($$0.getYRot() - getYRot());
/* 771 */     float $$2 = Mth.clamp($$1, -105.0F, 105.0F);
/* 772 */     $$0.yRotO += $$2 - $$1;
/* 773 */     $$0.setYRot($$0.getYRot() + $$2 - $$1);
/* 774 */     $$0.setYHeadRot($$0.getYRot());
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPassengerTurned(Entity $$0) {
/* 779 */     clampRotation($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addAdditionalSaveData(CompoundTag $$0) {
/* 784 */     $$0.putString("Type", getVariant().getSerializedName());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void readAdditionalSaveData(CompoundTag $$0) {
/* 789 */     if ($$0.contains("Type", 8)) {
/* 790 */       setVariant(Type.byName($$0.getString("Type")));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult interact(Player $$0, InteractionHand $$1) {
/* 796 */     if ($$0.isSecondaryUseActive()) {
/* 797 */       return InteractionResult.PASS;
/*     */     }
/*     */     
/* 800 */     if (this.outOfControlTicks < 60.0F) {
/* 801 */       if (!(level()).isClientSide) {
/* 802 */         return $$0.startRiding(this) ? InteractionResult.CONSUME : InteractionResult.PASS;
/*     */       }
/* 804 */       return InteractionResult.SUCCESS;
/*     */     } 
/* 806 */     return InteractionResult.PASS;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void checkFallDamage(double $$0, boolean $$1, BlockState $$2, BlockPos $$3) {
/* 811 */     this.lastYd = (getDeltaMovement()).y;
/* 812 */     if (isPassenger()) {
/*     */       return;
/*     */     }
/*     */     
/* 816 */     if ($$1) {
/* 817 */       if (this.fallDistance > 3.0F) {
/*     */         
/* 819 */         if (this.status != Status.ON_LAND) {
/* 820 */           resetFallDistance();
/*     */           
/*     */           return;
/*     */         } 
/* 824 */         causeFallDamage(this.fallDistance, 1.0F, damageSources().fall());
/* 825 */         if (!(level()).isClientSide && !isRemoved()) {
/* 826 */           kill();
/* 827 */           if (level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
/* 828 */             for (int $$4 = 0; $$4 < 3; $$4++) {
/* 829 */               spawnAtLocation((ItemLike)getVariant().getPlanks());
/*     */             }
/* 831 */             for (int $$5 = 0; $$5 < 2; $$5++) {
/* 832 */               spawnAtLocation((ItemLike)Items.STICK);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/* 837 */       resetFallDistance();
/* 838 */     } else if (!level().getFluidState(blockPosition().below()).is(FluidTags.WATER) && 
/* 839 */       $$0 < 0.0D) {
/* 840 */       this.fallDistance -= (float)$$0;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getPaddleState(int $$0) {
/* 846 */     return (((Boolean)this.entityData.get(($$0 == 0) ? DATA_ID_PADDLE_LEFT : DATA_ID_PADDLE_RIGHT)).booleanValue() && getControllingPassenger() != null);
/*     */   }
/*     */   
/*     */   private void setBubbleTime(int $$0) {
/* 850 */     this.entityData.set(DATA_ID_BUBBLE_TIME, Integer.valueOf($$0));
/*     */   }
/*     */   
/*     */   private int getBubbleTime() {
/* 854 */     return ((Integer)this.entityData.get(DATA_ID_BUBBLE_TIME)).intValue();
/*     */   }
/*     */   
/*     */   public float getBubbleAngle(float $$0) {
/* 858 */     return Mth.lerp($$0, this.bubbleAngleO, this.bubbleAngle);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setVariant(Type $$0) {
/* 863 */     this.entityData.set(DATA_ID_TYPE, Integer.valueOf($$0.ordinal()));
/*     */   }
/*     */ 
/*     */   
/*     */   public Type getVariant() {
/* 868 */     return Type.byId(((Integer)this.entityData.get(DATA_ID_TYPE)).intValue());
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canAddPassenger(Entity $$0) {
/* 873 */     return (getPassengers().size() < getMaxPassengers() && !isEyeInFluid(FluidTags.WATER));
/*     */   }
/*     */   
/*     */   protected int getMaxPassengers() {
/* 877 */     return 2;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public LivingEntity getControllingPassenger() {
/* 883 */     Entity entity = getFirstPassenger(); LivingEntity $$0 = (LivingEntity)entity; return (entity instanceof LivingEntity) ? $$0 : super.getControllingPassenger();
/*     */   }
/*     */   
/*     */   public void setInput(boolean $$0, boolean $$1, boolean $$2, boolean $$3) {
/* 887 */     this.inputLeft = $$0;
/* 888 */     this.inputRight = $$1;
/* 889 */     this.inputUp = $$2;
/* 890 */     this.inputDown = $$3;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Component getTypeName() {
/* 895 */     return (Component)Component.translatable(getDropItem().getDescriptionId());
/*     */   }
/*     */   
/*     */   public enum Type implements StringRepresentable {
/* 899 */     OAK((String)Blocks.OAK_PLANKS, "oak"),
/* 900 */     SPRUCE((String)Blocks.SPRUCE_PLANKS, "spruce"),
/* 901 */     BIRCH((String)Blocks.BIRCH_PLANKS, "birch"),
/* 902 */     JUNGLE((String)Blocks.JUNGLE_PLANKS, "jungle"),
/* 903 */     ACACIA((String)Blocks.ACACIA_PLANKS, "acacia"),
/* 904 */     CHERRY((String)Blocks.CHERRY_PLANKS, "cherry"),
/* 905 */     DARK_OAK((String)Blocks.DARK_OAK_PLANKS, "dark_oak"),
/* 906 */     MANGROVE((String)Blocks.MANGROVE_PLANKS, "mangrove"),
/* 907 */     BAMBOO((String)Blocks.BAMBOO_PLANKS, "bamboo");
/*     */ 
/*     */ 
/*     */     
/*     */     private final String name;
/*     */ 
/*     */     
/*     */     private final Block planks;
/*     */ 
/*     */     
/* 917 */     public static final StringRepresentable.EnumCodec<Type> CODEC = StringRepresentable.fromEnum(Type::values);
/*     */     
/* 919 */     private static final IntFunction<Type> BY_ID = ByIdMap.continuous(Enum::ordinal, (Object[])values(), ByIdMap.OutOfBoundsStrategy.ZERO);
/*     */     Type(Block $$0, String $$1) { this.name = $$1;
/*     */       this.planks = $$0; }
/*     */     static {  } public String getSerializedName() {
/* 923 */       return this.name;
/*     */     }
/*     */     
/*     */     public String getName() {
/* 927 */       return this.name;
/*     */     }
/*     */     
/*     */     public Block getPlanks() {
/* 931 */       return this.planks;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 936 */       return this.name;
/*     */     }
/*     */     
/*     */     public static Type byId(int $$0) {
/* 940 */       return BY_ID.apply($$0);
/*     */     }
/*     */     
/*     */     public static Type byName(String $$0) {
/* 944 */       return (Type)CODEC.byName($$0, OAK);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isUnderWater() {
/* 950 */     return (this.status == Status.UNDER_WATER || this.status == Status.UNDER_FLOWING_WATER);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getPickResult() {
/* 955 */     return new ItemStack((ItemLike)getDropItem());
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\vehicle\Boat.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */