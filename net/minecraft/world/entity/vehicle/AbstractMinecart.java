/*     */ package net.minecraft.world.entity.vehicle;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.UnmodifiableIterator;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import java.util.EnumMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.BlockUtil;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.HolderGetter;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.NbtUtils;
/*     */ import net.minecraft.network.syncher.EntityDataAccessor;
/*     */ import net.minecraft.network.syncher.EntityDataSerializers;
/*     */ import net.minecraft.network.syncher.SynchedEntityData;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityDimensions;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.MoverType;
/*     */ import net.minecraft.world.entity.Pose;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.BaseRailBlock;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.PoweredRailBlock;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.block.state.properties.RailShape;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ import org.joml.Vector3f;
/*     */ 
/*     */ public abstract class AbstractMinecart extends VehicleEntity {
/*     */   private static final float LOWERED_PASSENGER_ATTACHMENT_Y = 0.0F;
/*     */   private static final float PASSENGER_ATTACHMENT_Y = 0.1875F;
/*     */   
/*     */   public enum Type {
/*  56 */     RIDEABLE,
/*  57 */     CHEST,
/*  58 */     FURNACE,
/*  59 */     TNT,
/*  60 */     SPAWNER,
/*  61 */     HOPPER,
/*  62 */     COMMAND_BLOCK;
/*     */   }
/*     */ 
/*     */   
/*  66 */   private static final EntityDataAccessor<Integer> DATA_ID_DISPLAY_BLOCK = SynchedEntityData.defineId(AbstractMinecart.class, EntityDataSerializers.INT);
/*  67 */   private static final EntityDataAccessor<Integer> DATA_ID_DISPLAY_OFFSET = SynchedEntityData.defineId(AbstractMinecart.class, EntityDataSerializers.INT);
/*  68 */   private static final EntityDataAccessor<Boolean> DATA_ID_CUSTOM_DISPLAY = SynchedEntityData.defineId(AbstractMinecart.class, EntityDataSerializers.BOOLEAN);
/*     */   
/*  70 */   private static final ImmutableMap<Pose, ImmutableList<Integer>> POSE_DISMOUNT_HEIGHTS = ImmutableMap.of(Pose.STANDING, 
/*  71 */       ImmutableList.of(Integer.valueOf(0), Integer.valueOf(1), Integer.valueOf(-1)), Pose.CROUCHING, 
/*  72 */       ImmutableList.of(Integer.valueOf(0), Integer.valueOf(1), Integer.valueOf(-1)), Pose.SWIMMING, 
/*  73 */       ImmutableList.of(Integer.valueOf(0), Integer.valueOf(1)));
/*     */   
/*     */   protected static final float WATER_SLOWDOWN_FACTOR = 0.95F;
/*     */   
/*     */   private boolean flipped;
/*     */   
/*     */   private boolean onRails;
/*     */   
/*     */   private int lerpSteps;
/*     */   
/*     */   private double lerpX;
/*     */   
/*     */   private double lerpY;
/*     */   private double lerpZ;
/*     */   private double lerpYRot;
/*     */   private double lerpXRot;
/*  89 */   private Vec3 targetDeltaMovement = Vec3.ZERO; private static final Map<RailShape, Pair<Vec3i, Vec3i>> EXITS;
/*     */   
/*     */   protected AbstractMinecart(EntityType<?> $$0, Level $$1) {
/*  92 */     super($$0, $$1);
/*  93 */     this.blocksBuilding = true;
/*     */   }
/*     */   
/*     */   protected AbstractMinecart(EntityType<?> $$0, Level $$1, double $$2, double $$3, double $$4) {
/*  97 */     this($$0, $$1);
/*  98 */     setPos($$2, $$3, $$4);
/*     */     
/* 100 */     this.xo = $$2;
/* 101 */     this.yo = $$3;
/* 102 */     this.zo = $$4;
/*     */   }
/*     */   
/*     */   public static AbstractMinecart createMinecart(ServerLevel $$0, double $$1, double $$2, double $$3, Type $$4, ItemStack $$5, @Nullable Player $$6) {
/* 106 */     switch ($$4) { case ASCENDING_EAST: 
/*     */       case ASCENDING_WEST: 
/*     */       case ASCENDING_NORTH: 
/*     */       case ASCENDING_SOUTH: 
/*     */       case null:
/*     */       
/*     */       case null:
/* 113 */        }  AbstractMinecart $$7 = new Minecart((Level)$$0, $$1, $$2, $$3);
/*     */     
/* 115 */     EntityType.createDefaultStackConfig($$0, $$5, $$6)
/* 116 */       .accept($$7);
/* 117 */     return $$7;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Entity.MovementEmission getMovementEmission() {
/* 122 */     return Entity.MovementEmission.EVENTS;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {
/* 127 */     super.defineSynchedData();
/* 128 */     this.entityData.define(DATA_ID_DISPLAY_BLOCK, Integer.valueOf(Block.getId(Blocks.AIR.defaultBlockState())));
/* 129 */     this.entityData.define(DATA_ID_DISPLAY_OFFSET, Integer.valueOf(6));
/* 130 */     this.entityData.define(DATA_ID_CUSTOM_DISPLAY, Boolean.valueOf(false));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canCollideWith(Entity $$0) {
/* 135 */     return Boat.canVehicleCollide(this, $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPushable() {
/* 140 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Vec3 getRelativePortalPosition(Direction.Axis $$0, BlockUtil.FoundRectangle $$1) {
/* 145 */     return LivingEntity.resetForwardDirectionOfRelativePortalPosition(super.getRelativePortalPosition($$0, $$1));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Vector3f getPassengerAttachmentPoint(Entity $$0, EntityDimensions $$1, float $$2) {
/* 152 */     boolean $$3 = ($$0 instanceof net.minecraft.world.entity.npc.Villager || $$0 instanceof net.minecraft.world.entity.npc.WanderingTrader);
/* 153 */     return new Vector3f(0.0F, $$3 ? 0.0F : 0.1875F, 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public Vec3 getDismountLocationForPassenger(LivingEntity $$0) {
/* 158 */     Direction $$1 = getMotionDirection();
/* 159 */     if ($$1.getAxis() == Direction.Axis.Y) {
/* 160 */       return super.getDismountLocationForPassenger($$0);
/*     */     }
/*     */     
/* 163 */     int[][] $$2 = DismountHelper.offsetsForDirection($$1);
/* 164 */     BlockPos $$3 = blockPosition();
/* 165 */     BlockPos.MutableBlockPos $$4 = new BlockPos.MutableBlockPos();
/*     */     
/* 167 */     ImmutableList<Pose> $$5 = $$0.getDismountPoses();
/*     */     
/* 169 */     for (UnmodifiableIterator<Pose> unmodifiableIterator1 = $$5.iterator(); unmodifiableIterator1.hasNext(); ) { Pose $$6 = unmodifiableIterator1.next();
/* 170 */       EntityDimensions $$7 = $$0.getDimensions($$6);
/*     */ 
/*     */       
/* 173 */       float $$8 = Math.min($$7.width, 1.0F) / 2.0F;
/*     */       
/* 175 */       for (UnmodifiableIterator<Integer> unmodifiableIterator = ((ImmutableList)POSE_DISMOUNT_HEIGHTS.get($$6)).iterator(); unmodifiableIterator.hasNext(); ) { int $$9 = ((Integer)unmodifiableIterator.next()).intValue();
/* 176 */         for (int[] $$10 : $$2) {
/* 177 */           $$4.set($$3.getX() + $$10[0], $$3.getY() + $$9, $$3.getZ() + $$10[1]);
/*     */           
/* 179 */           double $$11 = level().getBlockFloorHeight(DismountHelper.nonClimbableShape((BlockGetter)level(), (BlockPos)$$4), () -> DismountHelper.nonClimbableShape((BlockGetter)level(), $$0.below()));
/* 180 */           if (DismountHelper.isBlockFloorValid($$11)) {
/*     */ 
/*     */ 
/*     */             
/* 184 */             AABB $$12 = new AABB(-$$8, 0.0D, -$$8, $$8, $$7.height, $$8);
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 189 */             Vec3 $$13 = Vec3.upFromBottomCenterOf((Vec3i)$$4, $$11);
/* 190 */             if (DismountHelper.canDismountTo((CollisionGetter)level(), $$0, $$12.move($$13))) {
/* 191 */               $$0.setPose($$6);
/* 192 */               return $$13;
/*     */             } 
/*     */           } 
/*     */         }  }
/*     */        }
/*     */     
/* 198 */     double $$14 = (getBoundingBox()).maxY;
/* 199 */     $$4.set($$3.getX(), $$14, $$3.getZ());
/*     */     
/* 201 */     for (UnmodifiableIterator<Pose> unmodifiableIterator2 = $$5.iterator(); unmodifiableIterator2.hasNext(); ) { Pose $$15 = unmodifiableIterator2.next();
/* 202 */       double $$16 = ($$0.getDimensions($$15)).height;
/* 203 */       int $$17 = Mth.ceil($$14 - $$4.getY() + $$16);
/* 204 */       double $$18 = DismountHelper.findCeilingFrom((BlockPos)$$4, $$17, $$0 -> level().getBlockState($$0).getCollisionShape((BlockGetter)level(), $$0));
/*     */       
/* 206 */       if ($$14 + $$16 <= $$18) {
/* 207 */         $$0.setPose($$15);
/*     */         
/*     */         break;
/*     */       }  }
/*     */     
/* 212 */     return super.getDismountLocationForPassenger($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getBlockSpeedFactor() {
/* 217 */     BlockState $$0 = level().getBlockState(blockPosition());
/* 218 */     if ($$0.is(BlockTags.RAILS)) {
/* 219 */       return 1.0F;
/*     */     }
/* 221 */     return super.getBlockSpeedFactor();
/*     */   }
/*     */ 
/*     */   
/*     */   public void animateHurt(float $$0) {
/* 226 */     setHurtDir(-getHurtDir());
/* 227 */     setHurtTime(10);
/* 228 */     setDamage(getDamage() + getDamage() * 10.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPickable() {
/* 233 */     return !isRemoved();
/*     */   }
/*     */   static {
/* 236 */     EXITS = (Map<RailShape, Pair<Vec3i, Vec3i>>)Util.make(Maps.newEnumMap(RailShape.class), $$0 -> {
/*     */           Vec3i $$1 = Direction.WEST.getNormal();
/*     */           Vec3i $$2 = Direction.EAST.getNormal();
/*     */           Vec3i $$3 = Direction.NORTH.getNormal();
/*     */           Vec3i $$4 = Direction.SOUTH.getNormal();
/*     */           Vec3i $$5 = $$1.below();
/*     */           Vec3i $$6 = $$2.below();
/*     */           Vec3i $$7 = $$3.below();
/*     */           Vec3i $$8 = $$4.below();
/*     */           $$0.put(RailShape.NORTH_SOUTH, Pair.of($$3, $$4));
/*     */           $$0.put(RailShape.EAST_WEST, Pair.of($$1, $$2));
/*     */           $$0.put(RailShape.ASCENDING_EAST, Pair.of($$5, $$2));
/*     */           $$0.put(RailShape.ASCENDING_WEST, Pair.of($$1, $$6));
/*     */           $$0.put(RailShape.ASCENDING_NORTH, Pair.of($$3, $$8));
/*     */           $$0.put(RailShape.ASCENDING_SOUTH, Pair.of($$7, $$4));
/*     */           $$0.put(RailShape.SOUTH_EAST, Pair.of($$4, $$2));
/*     */           $$0.put(RailShape.SOUTH_WEST, Pair.of($$4, $$1));
/*     */           $$0.put(RailShape.NORTH_WEST, Pair.of($$3, $$1));
/*     */           $$0.put(RailShape.NORTH_EAST, Pair.of($$3, $$2));
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   private static Pair<Vec3i, Vec3i> exits(RailShape $$0) {
/* 260 */     return EXITS.get($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public Direction getMotionDirection() {
/* 265 */     return this.flipped ? getDirection().getOpposite().getClockWise() : getDirection().getClockWise();
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 270 */     if (getHurtTime() > 0) {
/* 271 */       setHurtTime(getHurtTime() - 1);
/*     */     }
/* 273 */     if (getDamage() > 0.0F) {
/* 274 */       setDamage(getDamage() - 1.0F);
/*     */     }
/* 276 */     checkBelowWorld();
/*     */     
/* 278 */     handleNetherPortal();
/*     */     
/* 280 */     if ((level()).isClientSide) {
/* 281 */       if (this.lerpSteps > 0) {
/* 282 */         lerpPositionAndRotationStep(this.lerpSteps, this.lerpX, this.lerpY, this.lerpZ, this.lerpYRot, this.lerpXRot);
/* 283 */         this.lerpSteps--;
/*     */       } else {
/* 285 */         reapplyPosition();
/* 286 */         setRot(getYRot(), getXRot());
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/* 291 */     if (!isNoGravity()) {
/* 292 */       double $$0 = isInWater() ? -0.005D : -0.04D;
/* 293 */       setDeltaMovement(getDeltaMovement().add(0.0D, $$0, 0.0D));
/*     */     } 
/*     */     
/* 296 */     int $$1 = Mth.floor(getX());
/* 297 */     int $$2 = Mth.floor(getY());
/* 298 */     int $$3 = Mth.floor(getZ());
/* 299 */     if (level().getBlockState(new BlockPos($$1, $$2 - 1, $$3)).is(BlockTags.RAILS)) {
/* 300 */       $$2--;
/*     */     }
/*     */     
/* 303 */     BlockPos $$4 = new BlockPos($$1, $$2, $$3);
/* 304 */     BlockState $$5 = level().getBlockState($$4);
/* 305 */     this.onRails = BaseRailBlock.isRail($$5);
/* 306 */     if (this.onRails) {
/* 307 */       moveAlongTrack($$4, $$5);
/*     */       
/* 309 */       if ($$5.is(Blocks.ACTIVATOR_RAIL)) {
/* 310 */         activateMinecart($$1, $$2, $$3, ((Boolean)$$5.getValue((Property)PoweredRailBlock.POWERED)).booleanValue());
/*     */       }
/*     */     } else {
/* 313 */       comeOffTrack();
/*     */     } 
/*     */     
/* 316 */     checkInsideBlocks();
/*     */     
/* 318 */     setXRot(0.0F);
/* 319 */     double $$6 = this.xo - getX();
/* 320 */     double $$7 = this.zo - getZ();
/* 321 */     if ($$6 * $$6 + $$7 * $$7 > 0.001D) {
/* 322 */       setYRot((float)(Mth.atan2($$7, $$6) * 180.0D / Math.PI));
/* 323 */       if (this.flipped) {
/* 324 */         setYRot(getYRot() + 180.0F);
/*     */       }
/*     */     } 
/*     */     
/* 328 */     double $$8 = Mth.wrapDegrees(getYRot() - this.yRotO);
/* 329 */     if ($$8 < -170.0D || $$8 >= 170.0D) {
/* 330 */       setYRot(getYRot() + 180.0F);
/* 331 */       this.flipped = !this.flipped;
/*     */     } 
/* 333 */     setRot(getYRot(), getXRot());
/*     */     
/* 335 */     if (getMinecartType() == Type.RIDEABLE && getDeltaMovement().horizontalDistanceSqr() > 0.01D) {
/* 336 */       List<Entity> $$9 = level().getEntities(this, getBoundingBox().inflate(0.20000000298023224D, 0.0D, 0.20000000298023224D), EntitySelector.pushableBy(this));
/* 337 */       if (!$$9.isEmpty()) {
/* 338 */         for (Entity $$10 : $$9) {
/* 339 */           if ($$10 instanceof Player || $$10 instanceof net.minecraft.world.entity.animal.IronGolem || $$10 instanceof AbstractMinecart || isVehicle() || $$10.isPassenger()) {
/* 340 */             $$10.push(this); continue;
/*     */           } 
/* 342 */           $$10.startRiding(this);
/*     */         } 
/*     */       }
/*     */     } else {
/*     */       
/* 347 */       for (Entity $$11 : level().getEntities(this, getBoundingBox().inflate(0.20000000298023224D, 0.0D, 0.20000000298023224D))) {
/* 348 */         if (!hasPassenger($$11) && $$11.isPushable() && $$11 instanceof AbstractMinecart) {
/* 349 */           $$11.push(this);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 354 */     updateInWaterStateAndDoFluidPushing();
/*     */     
/* 356 */     if (isInLava()) {
/* 357 */       lavaHurt();
/* 358 */       this.fallDistance *= 0.5F;
/*     */     } 
/*     */     
/* 361 */     this.firstTick = false;
/*     */   }
/*     */   
/*     */   protected double getMaxSpeed() {
/* 365 */     return (isInWater() ? 4.0D : 8.0D) / 20.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public void activateMinecart(int $$0, int $$1, int $$2, boolean $$3) {}
/*     */   
/*     */   protected void comeOffTrack() {
/* 372 */     double $$0 = getMaxSpeed();
/* 373 */     Vec3 $$1 = getDeltaMovement();
/* 374 */     setDeltaMovement(
/* 375 */         Mth.clamp($$1.x, -$$0, $$0), $$1.y, 
/*     */         
/* 377 */         Mth.clamp($$1.z, -$$0, $$0));
/*     */     
/* 379 */     if (onGround()) {
/* 380 */       setDeltaMovement(getDeltaMovement().scale(0.5D));
/*     */     }
/* 382 */     move(MoverType.SELF, getDeltaMovement());
/*     */     
/* 384 */     if (!onGround())
/* 385 */       setDeltaMovement(getDeltaMovement().scale(0.95D)); 
/*     */   }
/*     */   
/*     */   protected void moveAlongTrack(BlockPos $$0, BlockState $$1) {
/*     */     double $$32;
/* 390 */     resetFallDistance();
/*     */     
/* 392 */     double $$2 = getX();
/* 393 */     double $$3 = getY();
/* 394 */     double $$4 = getZ();
/* 395 */     Vec3 $$5 = getPos($$2, $$3, $$4);
/* 396 */     $$3 = $$0.getY();
/*     */     
/* 398 */     boolean $$6 = false;
/* 399 */     boolean $$7 = false;
/*     */     
/* 401 */     if ($$1.is(Blocks.POWERED_RAIL)) {
/* 402 */       $$6 = ((Boolean)$$1.getValue((Property)PoweredRailBlock.POWERED)).booleanValue();
/* 403 */       $$7 = !$$6;
/*     */     } 
/*     */     
/* 406 */     double $$8 = 0.0078125D;
/* 407 */     if (isInWater()) {
/* 408 */       $$8 *= 0.2D;
/*     */     }
/* 410 */     Vec3 $$9 = getDeltaMovement();
/* 411 */     RailShape $$10 = (RailShape)$$1.getValue(((BaseRailBlock)$$1.getBlock()).getShapeProperty());
/* 412 */     switch ($$10) {
/*     */       case ASCENDING_EAST:
/* 414 */         setDeltaMovement($$9.add(-$$8, 0.0D, 0.0D));
/* 415 */         $$3++;
/*     */         break;
/*     */       case ASCENDING_WEST:
/* 418 */         setDeltaMovement($$9.add($$8, 0.0D, 0.0D));
/* 419 */         $$3++;
/*     */         break;
/*     */       case ASCENDING_NORTH:
/* 422 */         setDeltaMovement($$9.add(0.0D, 0.0D, $$8));
/* 423 */         $$3++;
/*     */         break;
/*     */       case ASCENDING_SOUTH:
/* 426 */         setDeltaMovement($$9.add(0.0D, 0.0D, -$$8));
/* 427 */         $$3++;
/*     */         break;
/*     */     } 
/*     */     
/* 431 */     $$9 = getDeltaMovement();
/*     */     
/* 433 */     Pair<Vec3i, Vec3i> $$11 = exits($$10);
/* 434 */     Vec3i $$12 = (Vec3i)$$11.getFirst();
/* 435 */     Vec3i $$13 = (Vec3i)$$11.getSecond();
/*     */     
/* 437 */     double $$14 = ($$13.getX() - $$12.getX());
/* 438 */     double $$15 = ($$13.getZ() - $$12.getZ());
/* 439 */     double $$16 = Math.sqrt($$14 * $$14 + $$15 * $$15);
/*     */     
/* 441 */     double $$17 = $$9.x * $$14 + $$9.z * $$15;
/* 442 */     if ($$17 < 0.0D) {
/* 443 */       $$14 = -$$14;
/* 444 */       $$15 = -$$15;
/*     */     } 
/*     */     
/* 447 */     double $$18 = Math.min(2.0D, $$9.horizontalDistance());
/*     */     
/* 449 */     $$9 = new Vec3($$18 * $$14 / $$16, $$9.y, $$18 * $$15 / $$16);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 454 */     setDeltaMovement($$9);
/*     */     
/* 456 */     Entity $$19 = getFirstPassenger();
/* 457 */     if ($$19 instanceof Player) {
/* 458 */       Vec3 $$20 = $$19.getDeltaMovement();
/* 459 */       double $$21 = $$20.horizontalDistanceSqr();
/* 460 */       double $$22 = getDeltaMovement().horizontalDistanceSqr();
/* 461 */       if ($$21 > 1.0E-4D && $$22 < 0.01D) {
/* 462 */         setDeltaMovement(getDeltaMovement().add($$20.x * 0.1D, 0.0D, $$20.z * 0.1D));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 468 */         $$7 = false;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 473 */     if ($$7) {
/* 474 */       double $$23 = getDeltaMovement().horizontalDistance();
/* 475 */       if ($$23 < 0.03D) {
/* 476 */         setDeltaMovement(Vec3.ZERO);
/*     */       } else {
/* 478 */         setDeltaMovement(getDeltaMovement().multiply(0.5D, 0.0D, 0.5D));
/*     */       } 
/*     */     } 
/*     */     
/* 482 */     double $$24 = $$0.getX() + 0.5D + $$12.getX() * 0.5D;
/* 483 */     double $$25 = $$0.getZ() + 0.5D + $$12.getZ() * 0.5D;
/* 484 */     double $$26 = $$0.getX() + 0.5D + $$13.getX() * 0.5D;
/* 485 */     double $$27 = $$0.getZ() + 0.5D + $$13.getZ() * 0.5D;
/*     */     
/* 487 */     $$14 = $$26 - $$24;
/* 488 */     $$15 = $$27 - $$25;
/*     */ 
/*     */     
/* 491 */     if ($$14 == 0.0D) {
/* 492 */       double $$28 = $$4 - $$0.getZ();
/* 493 */     } else if ($$15 == 0.0D) {
/* 494 */       double $$29 = $$2 - $$0.getX();
/*     */     } else {
/* 496 */       double $$30 = $$2 - $$24;
/* 497 */       double $$31 = $$4 - $$25;
/*     */       
/* 499 */       $$32 = ($$30 * $$14 + $$31 * $$15) * 2.0D;
/*     */     } 
/*     */     
/* 502 */     $$2 = $$24 + $$14 * $$32;
/* 503 */     $$4 = $$25 + $$15 * $$32;
/*     */     
/* 505 */     setPos($$2, $$3, $$4);
/*     */     
/* 507 */     double $$33 = isVehicle() ? 0.75D : 1.0D;
/* 508 */     double $$34 = getMaxSpeed();
/*     */     
/* 510 */     $$9 = getDeltaMovement();
/* 511 */     move(MoverType.SELF, new Vec3(
/* 512 */           Mth.clamp($$33 * $$9.x, -$$34, $$34), 0.0D, 
/*     */           
/* 514 */           Mth.clamp($$33 * $$9.z, -$$34, $$34)));
/*     */ 
/*     */     
/* 517 */     if ($$12.getY() != 0 && Mth.floor(getX()) - $$0.getX() == $$12.getX() && Mth.floor(getZ()) - $$0.getZ() == $$12.getZ()) {
/* 518 */       setPos(getX(), getY() + $$12.getY(), getZ());
/* 519 */     } else if ($$13.getY() != 0 && Mth.floor(getX()) - $$0.getX() == $$13.getX() && Mth.floor(getZ()) - $$0.getZ() == $$13.getZ()) {
/* 520 */       setPos(getX(), getY() + $$13.getY(), getZ());
/*     */     } 
/*     */     
/* 523 */     applyNaturalSlowdown();
/*     */     
/* 525 */     Vec3 $$35 = getPos(getX(), getY(), getZ());
/* 526 */     if ($$35 != null && $$5 != null) {
/* 527 */       double $$36 = ($$5.y - $$35.y) * 0.05D;
/*     */       
/* 529 */       Vec3 $$37 = getDeltaMovement();
/* 530 */       double $$38 = $$37.horizontalDistance();
/* 531 */       if ($$38 > 0.0D) {
/* 532 */         setDeltaMovement($$37.multiply(($$38 + $$36) / $$38, 1.0D, ($$38 + $$36) / $$38));
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 538 */       setPos(getX(), $$35.y, getZ());
/*     */     } 
/*     */     
/* 541 */     int $$39 = Mth.floor(getX());
/* 542 */     int $$40 = Mth.floor(getZ());
/* 543 */     if ($$39 != $$0.getX() || $$40 != $$0.getZ()) {
/* 544 */       Vec3 $$41 = getDeltaMovement();
/* 545 */       double $$42 = $$41.horizontalDistance();
/* 546 */       setDeltaMovement($$42 * ($$39 - $$0
/* 547 */           .getX()), $$41.y, $$42 * ($$40 - $$0
/*     */           
/* 549 */           .getZ()));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 554 */     if ($$6) {
/* 555 */       Vec3 $$43 = getDeltaMovement();
/* 556 */       double $$44 = $$43.horizontalDistance();
/* 557 */       if ($$44 > 0.01D) {
/* 558 */         double $$45 = 0.06D;
/* 559 */         setDeltaMovement($$43.add($$43.x / $$44 * 0.06D, 0.0D, $$43.z / $$44 * 0.06D));
/*     */ 
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */         
/* 566 */         Vec3 $$46 = getDeltaMovement();
/* 567 */         double $$47 = $$46.x;
/* 568 */         double $$48 = $$46.z;
/* 569 */         if ($$10 == RailShape.EAST_WEST) {
/* 570 */           if (isRedstoneConductor($$0.west())) {
/* 571 */             $$47 = 0.02D;
/* 572 */           } else if (isRedstoneConductor($$0.east())) {
/* 573 */             $$47 = -0.02D;
/*     */           } 
/* 575 */         } else if ($$10 == RailShape.NORTH_SOUTH) {
/* 576 */           if (isRedstoneConductor($$0.north())) {
/* 577 */             $$48 = 0.02D;
/* 578 */           } else if (isRedstoneConductor($$0.south())) {
/* 579 */             $$48 = -0.02D;
/*     */           } 
/*     */         } else {
/*     */           return;
/*     */         } 
/* 584 */         setDeltaMovement($$47, $$46.y, $$48);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOnRails() {
/* 591 */     return this.onRails;
/*     */   }
/*     */   
/*     */   private boolean isRedstoneConductor(BlockPos $$0) {
/* 595 */     return level().getBlockState($$0).isRedstoneConductor((BlockGetter)level(), $$0);
/*     */   }
/*     */   
/*     */   protected void applyNaturalSlowdown() {
/* 599 */     double $$0 = isVehicle() ? 0.997D : 0.96D;
/* 600 */     Vec3 $$1 = getDeltaMovement();
/* 601 */     $$1 = $$1.multiply($$0, 0.0D, $$0);
/* 602 */     if (isInWater()) {
/* 603 */       $$1 = $$1.scale(0.949999988079071D);
/*     */     }
/* 605 */     setDeltaMovement($$1);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Vec3 getPosOffs(double $$0, double $$1, double $$2, double $$3) {
/* 610 */     int $$4 = Mth.floor($$0);
/* 611 */     int $$5 = Mth.floor($$1);
/* 612 */     int $$6 = Mth.floor($$2);
/* 613 */     if (level().getBlockState(new BlockPos($$4, $$5 - 1, $$6)).is(BlockTags.RAILS)) {
/* 614 */       $$5--;
/*     */     }
/*     */     
/* 617 */     BlockState $$7 = level().getBlockState(new BlockPos($$4, $$5, $$6));
/* 618 */     if (BaseRailBlock.isRail($$7)) {
/* 619 */       RailShape $$8 = (RailShape)$$7.getValue(((BaseRailBlock)$$7.getBlock()).getShapeProperty());
/* 620 */       $$1 = $$5;
/* 621 */       if ($$8.isAscending()) {
/* 622 */         $$1 = ($$5 + 1);
/*     */       }
/*     */       
/* 625 */       Pair<Vec3i, Vec3i> $$9 = exits($$8);
/* 626 */       Vec3i $$10 = (Vec3i)$$9.getFirst();
/* 627 */       Vec3i $$11 = (Vec3i)$$9.getSecond();
/*     */       
/* 629 */       double $$12 = ($$11.getX() - $$10.getX());
/* 630 */       double $$13 = ($$11.getZ() - $$10.getZ());
/* 631 */       double $$14 = Math.sqrt($$12 * $$12 + $$13 * $$13);
/* 632 */       $$12 /= $$14;
/* 633 */       $$13 /= $$14;
/*     */       
/* 635 */       $$0 += $$12 * $$3;
/* 636 */       $$2 += $$13 * $$3;
/*     */       
/* 638 */       if ($$10.getY() != 0 && Mth.floor($$0) - $$4 == $$10.getX() && Mth.floor($$2) - $$6 == $$10.getZ()) {
/* 639 */         $$1 += $$10.getY();
/* 640 */       } else if ($$11.getY() != 0 && Mth.floor($$0) - $$4 == $$11.getX() && Mth.floor($$2) - $$6 == $$11.getZ()) {
/* 641 */         $$1 += $$11.getY();
/*     */       } 
/*     */       
/* 644 */       return getPos($$0, $$1, $$2);
/*     */     } 
/* 646 */     return null;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Vec3 getPos(double $$0, double $$1, double $$2) {
/* 651 */     int $$3 = Mth.floor($$0);
/* 652 */     int $$4 = Mth.floor($$1);
/* 653 */     int $$5 = Mth.floor($$2);
/* 654 */     if (level().getBlockState(new BlockPos($$3, $$4 - 1, $$5)).is(BlockTags.RAILS)) {
/* 655 */       $$4--;
/*     */     }
/*     */     
/* 658 */     BlockState $$6 = level().getBlockState(new BlockPos($$3, $$4, $$5));
/* 659 */     if (BaseRailBlock.isRail($$6)) {
/* 660 */       double $$24; RailShape $$7 = (RailShape)$$6.getValue(((BaseRailBlock)$$6.getBlock()).getShapeProperty());
/*     */       
/* 662 */       Pair<Vec3i, Vec3i> $$8 = exits($$7);
/* 663 */       Vec3i $$9 = (Vec3i)$$8.getFirst();
/* 664 */       Vec3i $$10 = (Vec3i)$$8.getSecond();
/*     */       
/* 666 */       double $$11 = $$3 + 0.5D + $$9.getX() * 0.5D;
/* 667 */       double $$12 = $$4 + 0.0625D + $$9.getY() * 0.5D;
/* 668 */       double $$13 = $$5 + 0.5D + $$9.getZ() * 0.5D;
/* 669 */       double $$14 = $$3 + 0.5D + $$10.getX() * 0.5D;
/* 670 */       double $$15 = $$4 + 0.0625D + $$10.getY() * 0.5D;
/* 671 */       double $$16 = $$5 + 0.5D + $$10.getZ() * 0.5D;
/*     */       
/* 673 */       double $$17 = $$14 - $$11;
/* 674 */       double $$18 = ($$15 - $$12) * 2.0D;
/* 675 */       double $$19 = $$16 - $$13;
/*     */ 
/*     */       
/* 678 */       if ($$17 == 0.0D) {
/* 679 */         double $$20 = $$2 - $$5;
/* 680 */       } else if ($$19 == 0.0D) {
/* 681 */         double $$21 = $$0 - $$3;
/*     */       } else {
/* 683 */         double $$22 = $$0 - $$11;
/* 684 */         double $$23 = $$2 - $$13;
/*     */         
/* 686 */         $$24 = ($$22 * $$17 + $$23 * $$19) * 2.0D;
/*     */       } 
/*     */       
/* 689 */       $$0 = $$11 + $$17 * $$24;
/* 690 */       $$1 = $$12 + $$18 * $$24;
/* 691 */       $$2 = $$13 + $$19 * $$24;
/* 692 */       if ($$18 < 0.0D) {
/* 693 */         $$1++;
/* 694 */       } else if ($$18 > 0.0D) {
/* 695 */         $$1 += 0.5D;
/*     */       } 
/* 697 */       return new Vec3($$0, $$1, $$2);
/*     */     } 
/* 699 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public AABB getBoundingBoxForCulling() {
/* 704 */     AABB $$0 = getBoundingBox();
/* 705 */     if (hasCustomDisplay()) {
/* 706 */       return $$0.inflate(Math.abs(getDisplayOffset()) / 16.0D);
/*     */     }
/* 708 */     return $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void readAdditionalSaveData(CompoundTag $$0) {
/* 713 */     if ($$0.getBoolean("CustomDisplayTile")) {
/* 714 */       setDisplayBlockState(NbtUtils.readBlockState((HolderGetter)level().holderLookup(Registries.BLOCK), $$0.getCompound("DisplayState")));
/*     */       
/* 716 */       setDisplayOffset($$0.getInt("DisplayOffset"));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addAdditionalSaveData(CompoundTag $$0) {
/* 722 */     if (hasCustomDisplay()) {
/* 723 */       $$0.putBoolean("CustomDisplayTile", true);
/* 724 */       $$0.put("DisplayState", (Tag)NbtUtils.writeBlockState(getDisplayBlockState()));
/* 725 */       $$0.putInt("DisplayOffset", getDisplayOffset());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void push(Entity $$0) {
/* 731 */     if ((level()).isClientSide) {
/*     */       return;
/*     */     }
/* 734 */     if ($$0.noPhysics || this.noPhysics) {
/*     */       return;
/*     */     }
/*     */     
/* 738 */     if (hasPassenger($$0)) {
/*     */       return;
/*     */     }
/*     */     
/* 742 */     double $$1 = $$0.getX() - getX();
/* 743 */     double $$2 = $$0.getZ() - getZ();
/*     */     
/* 745 */     double $$3 = $$1 * $$1 + $$2 * $$2;
/* 746 */     if ($$3 >= 9.999999747378752E-5D) {
/* 747 */       $$3 = Math.sqrt($$3);
/* 748 */       $$1 /= $$3;
/* 749 */       $$2 /= $$3;
/* 750 */       double $$4 = 1.0D / $$3;
/* 751 */       if ($$4 > 1.0D) {
/* 752 */         $$4 = 1.0D;
/*     */       }
/* 754 */       $$1 *= $$4;
/* 755 */       $$2 *= $$4;
/* 756 */       $$1 *= 0.10000000149011612D;
/* 757 */       $$2 *= 0.10000000149011612D;
/*     */       
/* 759 */       $$1 *= 0.5D;
/* 760 */       $$2 *= 0.5D;
/*     */       
/* 762 */       if ($$0 instanceof AbstractMinecart) {
/* 763 */         double $$5 = $$0.getX() - getX();
/* 764 */         double $$6 = $$0.getZ() - getZ();
/*     */         
/* 766 */         Vec3 $$7 = (new Vec3($$5, 0.0D, $$6)).normalize();
/* 767 */         Vec3 $$8 = (new Vec3(Mth.cos(getYRot() * 0.017453292F), 0.0D, Mth.sin(getYRot() * 0.017453292F))).normalize();
/*     */         
/* 769 */         double $$9 = Math.abs($$7.dot($$8));
/*     */         
/* 771 */         if ($$9 < 0.800000011920929D) {
/*     */           return;
/*     */         }
/*     */         
/* 775 */         Vec3 $$10 = getDeltaMovement();
/* 776 */         Vec3 $$11 = $$0.getDeltaMovement();
/*     */         
/* 778 */         if (((AbstractMinecart)$$0).getMinecartType() == Type.FURNACE && getMinecartType() != Type.FURNACE) {
/* 779 */           setDeltaMovement($$10.multiply(0.2D, 1.0D, 0.2D));
/* 780 */           push($$11.x - $$1, 0.0D, $$11.z - $$2);
/* 781 */           $$0.setDeltaMovement($$11.multiply(0.95D, 1.0D, 0.95D));
/* 782 */         } else if (((AbstractMinecart)$$0).getMinecartType() != Type.FURNACE && getMinecartType() == Type.FURNACE) {
/* 783 */           $$0.setDeltaMovement($$11.multiply(0.2D, 1.0D, 0.2D));
/* 784 */           $$0.push($$10.x + $$1, 0.0D, $$10.z + $$2);
/* 785 */           setDeltaMovement($$10.multiply(0.95D, 1.0D, 0.95D));
/*     */         } else {
/* 787 */           double $$12 = ($$11.x + $$10.x) / 2.0D;
/* 788 */           double $$13 = ($$11.z + $$10.z) / 2.0D;
/*     */           
/* 790 */           setDeltaMovement($$10.multiply(0.2D, 1.0D, 0.2D));
/* 791 */           push($$12 - $$1, 0.0D, $$13 - $$2);
/* 792 */           $$0.setDeltaMovement($$11.multiply(0.2D, 1.0D, 0.2D));
/* 793 */           $$0.push($$12 + $$1, 0.0D, $$13 + $$2);
/*     */         } 
/*     */       } else {
/* 796 */         push(-$$1, 0.0D, -$$2);
/* 797 */         $$0.push($$1 / 4.0D, 0.0D, $$2 / 4.0D);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void lerpTo(double $$0, double $$1, double $$2, float $$3, float $$4, int $$5) {
/* 804 */     this.lerpX = $$0;
/* 805 */     this.lerpY = $$1;
/* 806 */     this.lerpZ = $$2;
/* 807 */     this.lerpYRot = $$3;
/* 808 */     this.lerpXRot = $$4;
/*     */     
/* 810 */     this.lerpSteps = $$5 + 2;
/*     */     
/* 812 */     setDeltaMovement(this.targetDeltaMovement);
/*     */   }
/*     */ 
/*     */   
/*     */   public double lerpTargetX() {
/* 817 */     return (this.lerpSteps > 0) ? this.lerpX : getX();
/*     */   }
/*     */ 
/*     */   
/*     */   public double lerpTargetY() {
/* 822 */     return (this.lerpSteps > 0) ? this.lerpY : getY();
/*     */   }
/*     */ 
/*     */   
/*     */   public double lerpTargetZ() {
/* 827 */     return (this.lerpSteps > 0) ? this.lerpZ : getZ();
/*     */   }
/*     */ 
/*     */   
/*     */   public float lerpTargetXRot() {
/* 832 */     return (this.lerpSteps > 0) ? (float)this.lerpXRot : getXRot();
/*     */   }
/*     */ 
/*     */   
/*     */   public float lerpTargetYRot() {
/* 837 */     return (this.lerpSteps > 0) ? (float)this.lerpYRot : getYRot();
/*     */   }
/*     */ 
/*     */   
/*     */   public void lerpMotion(double $$0, double $$1, double $$2) {
/* 842 */     this.targetDeltaMovement = new Vec3($$0, $$1, $$2);
/* 843 */     setDeltaMovement(this.targetDeltaMovement);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockState getDisplayBlockState() {
/* 849 */     if (!hasCustomDisplay()) {
/* 850 */       return getDefaultDisplayBlockState();
/*     */     }
/* 852 */     return Block.stateById(((Integer)getEntityData().get(DATA_ID_DISPLAY_BLOCK)).intValue());
/*     */   }
/*     */   
/*     */   public BlockState getDefaultDisplayBlockState() {
/* 856 */     return Blocks.AIR.defaultBlockState();
/*     */   }
/*     */   
/*     */   public int getDisplayOffset() {
/* 860 */     if (!hasCustomDisplay()) {
/* 861 */       return getDefaultDisplayOffset();
/*     */     }
/* 863 */     return ((Integer)getEntityData().get(DATA_ID_DISPLAY_OFFSET)).intValue();
/*     */   }
/*     */   
/*     */   public int getDefaultDisplayOffset() {
/* 867 */     return 6;
/*     */   }
/*     */   
/*     */   public void setDisplayBlockState(BlockState $$0) {
/* 871 */     getEntityData().set(DATA_ID_DISPLAY_BLOCK, Integer.valueOf(Block.getId($$0)));
/* 872 */     setCustomDisplay(true);
/*     */   }
/*     */   
/*     */   public void setDisplayOffset(int $$0) {
/* 876 */     getEntityData().set(DATA_ID_DISPLAY_OFFSET, Integer.valueOf($$0));
/* 877 */     setCustomDisplay(true);
/*     */   }
/*     */   
/*     */   public boolean hasCustomDisplay() {
/* 881 */     return ((Boolean)getEntityData().get(DATA_ID_CUSTOM_DISPLAY)).booleanValue();
/*     */   }
/*     */   
/*     */   public void setCustomDisplay(boolean $$0) {
/* 885 */     getEntityData().set(DATA_ID_CUSTOM_DISPLAY, Boolean.valueOf($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getPickResult() {
/*     */     Item $$0, $$1, $$2, $$3, $$4;
/* 891 */     switch (getMinecartType())
/*     */     { case ASCENDING_WEST:
/* 893 */         $$0 = Items.FURNACE_MINECART;
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
/* 911 */         return new ItemStack((ItemLike)$$0);case ASCENDING_EAST: $$1 = Items.CHEST_MINECART; return new ItemStack((ItemLike)$$1);case ASCENDING_NORTH: $$2 = Items.TNT_MINECART; return new ItemStack((ItemLike)$$2);case null: $$3 = Items.HOPPER_MINECART; return new ItemStack((ItemLike)$$3);case null: $$4 = Items.COMMAND_BLOCK_MINECART; return new ItemStack((ItemLike)$$4); }  Item $$5 = Items.MINECART; return new ItemStack((ItemLike)$$5);
/*     */   }
/*     */   
/*     */   public abstract Type getMinecartType();
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\vehicle\AbstractMinecart.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */