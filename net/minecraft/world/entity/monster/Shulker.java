/*     */ package net.minecraft.world.entity.monster;
/*     */ 
/*     */ import java.util.EnumSet;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
/*     */ import net.minecraft.network.syncher.EntityDataAccessor;
/*     */ import net.minecraft.network.syncher.EntityDataSerializers;
/*     */ import net.minecraft.network.syncher.SynchedEntityData;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.tags.DamageTypeTags;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.Difficulty;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityDimensions;
/*     */ import net.minecraft.world.entity.EntitySelector;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.MobSpawnType;
/*     */ import net.minecraft.world.entity.MoverType;
/*     */ import net.minecraft.world.entity.PathfinderMob;
/*     */ import net.minecraft.world.entity.Pose;
/*     */ import net.minecraft.world.entity.SpawnGroupData;
/*     */ import net.minecraft.world.entity.VariantHolder;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeModifier;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.entity.ai.control.BodyRotationControl;
/*     */ import net.minecraft.world.entity.ai.control.LookControl;
/*     */ import net.minecraft.world.entity.ai.goal.Goal;
/*     */ import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
/*     */ import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
/*     */ import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
/*     */ import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
/*     */ import net.minecraft.world.entity.animal.AbstractGolem;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.entity.projectile.ShulkerBullet;
/*     */ import net.minecraft.world.item.DyeColor;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.ServerLevelAccessor;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.entity.EntityTypeTest;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import org.joml.Vector3f;
/*     */ import org.joml.Vector3fc;
/*     */ 
/*     */ public class Shulker
/*     */   extends AbstractGolem implements VariantHolder<Optional<DyeColor>>, Enemy {
/*  65 */   private static final UUID COVERED_ARMOR_MODIFIER_UUID = UUID.fromString("7E0292F2-9434-48D5-A29F-9583AF7DF27F");
/*  66 */   private static final AttributeModifier COVERED_ARMOR_MODIFIER = new AttributeModifier(COVERED_ARMOR_MODIFIER_UUID, "Covered armor bonus", 20.0D, AttributeModifier.Operation.ADDITION);
/*     */   
/*  68 */   protected static final EntityDataAccessor<Direction> DATA_ATTACH_FACE_ID = SynchedEntityData.defineId(Shulker.class, EntityDataSerializers.DIRECTION);
/*  69 */   protected static final EntityDataAccessor<Byte> DATA_PEEK_ID = SynchedEntityData.defineId(Shulker.class, EntityDataSerializers.BYTE);
/*  70 */   protected static final EntityDataAccessor<Byte> DATA_COLOR_ID = SynchedEntityData.defineId(Shulker.class, EntityDataSerializers.BYTE);
/*     */   
/*     */   private static final int TELEPORT_STEPS = 6;
/*     */   
/*     */   private static final byte NO_COLOR = 16;
/*     */   private static final byte DEFAULT_COLOR = 16;
/*     */   private static final int MAX_TELEPORT_DISTANCE = 8;
/*     */   private static final int OTHER_SHULKER_SCAN_RADIUS = 8;
/*     */   private static final int OTHER_SHULKER_LIMIT = 5;
/*     */   
/*     */   static {
/*  81 */     FORWARD = (Vector3f)Util.make(() -> {
/*     */           Vec3i $$0 = Direction.SOUTH.getNormal();
/*     */           return new Vector3f($$0.getX(), $$0.getY(), $$0.getZ());
/*     */         });
/*     */   }
/*     */   
/*     */   private static final float PEEK_PER_TICK = 0.05F;
/*     */   static final Vector3f FORWARD;
/*     */   private float currentPeekAmountO;
/*     */   private float currentPeekAmount;
/*     */   @Nullable
/*     */   private BlockPos clientOldAttachPosition;
/*     */   private int clientSideTeleportInterpolation;
/*     */   private static final float MAX_LID_OPEN = 1.0F;
/*     */   
/*     */   public Shulker(EntityType<? extends Shulker> $$0, Level $$1) {
/*  97 */     super($$0, $$1);
/*     */     
/*  99 */     this.xpReward = 5;
/*     */     
/* 101 */     this.lookControl = new ShulkerLookControl((Mob)this);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerGoals() {
/* 106 */     this.goalSelector.addGoal(1, (Goal)new LookAtPlayerGoal((Mob)this, Player.class, 8.0F, 0.02F, true));
/* 107 */     this.goalSelector.addGoal(4, new ShulkerAttackGoal());
/* 108 */     this.goalSelector.addGoal(7, new ShulkerPeekGoal());
/* 109 */     this.goalSelector.addGoal(8, (Goal)new RandomLookAroundGoal((Mob)this));
/*     */     
/* 111 */     this.targetSelector.addGoal(1, (Goal)(new HurtByTargetGoal((PathfinderMob)this, new Class[] { getClass() })).setAlertOthers(new Class[0]));
/* 112 */     this.targetSelector.addGoal(2, (Goal)new ShulkerNearestAttackGoal(this));
/* 113 */     this.targetSelector.addGoal(3, (Goal)new ShulkerDefenseAttackGoal(this));
/*     */   }
/*     */   
/*     */   private class ShulkerLookControl extends LookControl {
/*     */     public ShulkerLookControl(Mob $$0) {
/* 118 */       super($$0);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected void clampHeadRotationToBody() {}
/*     */ 
/*     */ 
/*     */     
/*     */     protected Optional<Float> getYRotD() {
/* 128 */       Direction $$0 = Shulker.this.getAttachFace().getOpposite();
/*     */ 
/*     */       
/* 131 */       Vector3f $$1 = $$0.getRotation().transform(new Vector3f((Vector3fc)Shulker.FORWARD));
/*     */       
/* 133 */       Vec3i $$2 = $$0.getNormal();
/* 134 */       Vector3f $$3 = new Vector3f($$2.getX(), $$2.getY(), $$2.getZ());
/* 135 */       $$3.cross((Vector3fc)$$1);
/*     */       
/* 137 */       double $$4 = this.wantedX - this.mob.getX();
/* 138 */       double $$5 = this.wantedY - this.mob.getEyeY();
/* 139 */       double $$6 = this.wantedZ - this.mob.getZ();
/*     */ 
/*     */       
/* 142 */       Vector3f $$7 = new Vector3f((float)$$4, (float)$$5, (float)$$6);
/* 143 */       float $$8 = $$3.dot((Vector3fc)$$7);
/* 144 */       float $$9 = $$1.dot((Vector3fc)$$7);
/*     */       
/* 146 */       return (Math.abs($$8) > 1.0E-5F || Math.abs($$9) > 1.0E-5F) ? Optional.<Float>of(Float.valueOf((float)(Mth.atan2(-$$8, $$9) * 57.2957763671875D))) : Optional.<Float>empty();
/*     */     }
/*     */ 
/*     */     
/*     */     protected Optional<Float> getXRotD() {
/* 151 */       return Optional.of(Float.valueOf(0.0F));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected Entity.MovementEmission getMovementEmission() {
/* 157 */     return Entity.MovementEmission.NONE;
/*     */   }
/*     */ 
/*     */   
/*     */   public SoundSource getSoundSource() {
/* 162 */     return SoundSource.HOSTILE;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/* 167 */     return SoundEvents.SHULKER_AMBIENT;
/*     */   }
/*     */ 
/*     */   
/*     */   public void playAmbientSound() {
/* 172 */     if (!isClosed()) {
/* 173 */       super.playAmbientSound();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/* 179 */     return SoundEvents.SHULKER_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource $$0) {
/* 184 */     if (isClosed()) {
/* 185 */       return SoundEvents.SHULKER_HURT_CLOSED;
/*     */     }
/* 187 */     return SoundEvents.SHULKER_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {
/* 192 */     super.defineSynchedData();
/*     */     
/* 194 */     this.entityData.define(DATA_ATTACH_FACE_ID, Direction.DOWN);
/* 195 */     this.entityData.define(DATA_PEEK_ID, Byte.valueOf((byte)0));
/* 196 */     this.entityData.define(DATA_COLOR_ID, Byte.valueOf((byte)16));
/*     */   }
/*     */   
/*     */   public static AttributeSupplier.Builder createAttributes() {
/* 200 */     return Mob.createMobAttributes()
/* 201 */       .add(Attributes.MAX_HEALTH, 30.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   protected BodyRotationControl createBodyControl() {
/* 206 */     return new ShulkerBodyRotationControl((Mob)this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/* 211 */     super.readAdditionalSaveData($$0);
/*     */     
/* 213 */     setAttachFace(Direction.from3DDataValue($$0.getByte("AttachFace")));
/* 214 */     this.entityData.set(DATA_PEEK_ID, Byte.valueOf($$0.getByte("Peek")));
/* 215 */     if ($$0.contains("Color", 99)) {
/* 216 */       this.entityData.set(DATA_COLOR_ID, Byte.valueOf($$0.getByte("Color")));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/* 222 */     super.addAdditionalSaveData($$0);
/*     */     
/* 224 */     $$0.putByte("AttachFace", (byte)getAttachFace().get3DDataValue());
/* 225 */     $$0.putByte("Peek", ((Byte)this.entityData.get(DATA_PEEK_ID)).byteValue());
/* 226 */     $$0.putByte("Color", ((Byte)this.entityData.get(DATA_COLOR_ID)).byteValue());
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 231 */     super.tick();
/*     */     
/* 233 */     if (!(level()).isClientSide && !isPassenger() && !canStayAt(blockPosition(), getAttachFace())) {
/* 234 */       findNewAttachment();
/*     */     }
/*     */     
/* 237 */     if (updatePeekAmount()) {
/* 238 */       onPeekAmountChange();
/*     */     }
/*     */     
/* 241 */     if ((level()).isClientSide) {
/* 242 */       if (this.clientSideTeleportInterpolation > 0) {
/* 243 */         this.clientSideTeleportInterpolation--;
/*     */       } else {
/* 245 */         this.clientOldAttachPosition = null;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void findNewAttachment() {
/* 252 */     Direction $$0 = findAttachableSurface(blockPosition());
/* 253 */     if ($$0 != null) {
/* 254 */       setAttachFace($$0);
/*     */     } else {
/*     */       
/* 257 */       teleportSomewhere();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected AABB makeBoundingBox() {
/* 263 */     float $$0 = getPhysicalPeek(this.currentPeekAmount);
/* 264 */     Direction $$1 = getAttachFace().getOpposite();
/* 265 */     float $$2 = getType().getWidth() / 2.0F;
/*     */     
/* 267 */     return getProgressAabb($$1, $$0).move(
/* 268 */         getX() - $$2, 
/* 269 */         getY(), 
/* 270 */         getZ() - $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   private static float getPhysicalPeek(float $$0) {
/* 275 */     return 0.5F - Mth.sin((0.5F + $$0) * 3.1415927F) * 0.5F;
/*     */   }
/*     */   
/*     */   private boolean updatePeekAmount() {
/* 279 */     this.currentPeekAmountO = this.currentPeekAmount;
/* 280 */     float $$0 = getRawPeekAmount() * 0.01F;
/* 281 */     if (this.currentPeekAmount == $$0) {
/* 282 */       return false;
/*     */     }
/*     */     
/* 285 */     if (this.currentPeekAmount > $$0) {
/* 286 */       this.currentPeekAmount = Mth.clamp(this.currentPeekAmount - 0.05F, $$0, 1.0F);
/*     */     } else {
/* 288 */       this.currentPeekAmount = Mth.clamp(this.currentPeekAmount + 0.05F, 0.0F, $$0);
/*     */     } 
/* 290 */     return true;
/*     */   }
/*     */   
/*     */   private void onPeekAmountChange() {
/* 294 */     reapplyPosition();
/*     */     
/* 296 */     float $$0 = getPhysicalPeek(this.currentPeekAmount);
/* 297 */     float $$1 = getPhysicalPeek(this.currentPeekAmountO);
/* 298 */     Direction $$2 = getAttachFace().getOpposite();
/*     */     
/* 300 */     float $$3 = $$0 - $$1;
/* 301 */     if ($$3 <= 0.0F) {
/*     */       return;
/*     */     }
/* 304 */     List<Entity> $$4 = level().getEntities((Entity)this, getProgressDeltaAabb($$2, $$1, $$0).move(getX() - 0.5D, getY(), getZ() - 0.5D), EntitySelector.NO_SPECTATORS.and($$0 -> !$$0.isPassengerOfSameVehicle((Entity)this)));
/* 305 */     for (Entity $$5 : $$4) {
/* 306 */       if (!($$5 instanceof Shulker) && !$$5.noPhysics) {
/* 307 */         $$5.move(MoverType.SHULKER, new Vec3(($$3 * $$2
/* 308 */               .getStepX()), ($$3 * $$2
/* 309 */               .getStepY()), ($$3 * $$2
/* 310 */               .getStepZ())));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static AABB getProgressAabb(Direction $$0, float $$1) {
/* 317 */     return getProgressDeltaAabb($$0, -1.0F, $$1);
/*     */   }
/*     */   
/*     */   public static AABB getProgressDeltaAabb(Direction $$0, float $$1, float $$2) {
/* 321 */     double $$3 = Math.max($$1, $$2);
/* 322 */     double $$4 = Math.min($$1, $$2);
/* 323 */     return (new AABB(BlockPos.ZERO)).expandTowards($$0
/* 324 */         .getStepX() * $$3, $$0
/* 325 */         .getStepY() * $$3, $$0
/* 326 */         .getStepZ() * $$3)
/* 327 */       .contract(
/* 328 */         -$$0.getStepX() * (1.0D + $$4), 
/* 329 */         -$$0.getStepY() * (1.0D + $$4), 
/* 330 */         -$$0.getStepZ() * (1.0D + $$4));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean startRiding(Entity $$0, boolean $$1) {
/* 336 */     if (level().isClientSide()) {
/* 337 */       this.clientOldAttachPosition = null;
/* 338 */       this.clientSideTeleportInterpolation = 0;
/*     */     } 
/* 340 */     setAttachFace(Direction.DOWN);
/* 341 */     return super.startRiding($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void stopRiding() {
/* 346 */     super.stopRiding();
/* 347 */     if ((level()).isClientSide) {
/* 348 */       this.clientOldAttachPosition = blockPosition();
/*     */     }
/* 350 */     this.yBodyRotO = 0.0F;
/* 351 */     this.yBodyRot = 0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public SpawnGroupData finalizeSpawn(ServerLevelAccessor $$0, DifficultyInstance $$1, MobSpawnType $$2, @Nullable SpawnGroupData $$3, @Nullable CompoundTag $$4) {
/* 357 */     setYRot(0.0F);
/* 358 */     this.yHeadRot = getYRot();
/* 359 */     setOldPosAndRot();
/*     */     
/* 361 */     return super.finalizeSpawn($$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */ 
/*     */   
/*     */   public void move(MoverType $$0, Vec3 $$1) {
/* 366 */     if ($$0 == MoverType.SHULKER_BOX) {
/* 367 */       teleportSomewhere();
/*     */     } else {
/* 369 */       super.move($$0, $$1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Vec3 getDeltaMovement() {
/* 375 */     return Vec3.ZERO;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDeltaMovement(Vec3 $$0) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPos(double $$0, double $$1, double $$2) {
/* 385 */     BlockPos $$3 = blockPosition();
/* 386 */     if (isPassenger()) {
/* 387 */       super.setPos($$0, $$1, $$2);
/*     */     } else {
/* 389 */       super.setPos(Mth.floor($$0) + 0.5D, Mth.floor($$1 + 0.5D), Mth.floor($$2) + 0.5D);
/*     */     } 
/* 391 */     if (this.tickCount == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 395 */     BlockPos $$4 = blockPosition();
/* 396 */     if (!$$4.equals($$3)) {
/* 397 */       this.entityData.set(DATA_PEEK_ID, Byte.valueOf((byte)0));
/* 398 */       this.hasImpulse = true;
/* 399 */       if ((level()).isClientSide && !isPassenger() && !$$4.equals(this.clientOldAttachPosition)) {
/* 400 */         this.clientOldAttachPosition = $$3;
/* 401 */         this.clientSideTeleportInterpolation = 6;
/*     */ 
/*     */         
/* 404 */         this.xOld = getX();
/* 405 */         this.yOld = getY();
/* 406 */         this.zOld = getZ();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected Direction findAttachableSurface(BlockPos $$0) {
/* 413 */     for (Direction $$1 : Direction.values()) {
/* 414 */       if (canStayAt($$0, $$1)) {
/* 415 */         return $$1;
/*     */       }
/*     */     } 
/* 418 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   boolean canStayAt(BlockPos $$0, Direction $$1) {
/* 423 */     if (isPositionBlocked($$0)) {
/* 424 */       return false;
/*     */     }
/*     */     
/* 427 */     Direction $$2 = $$1.getOpposite();
/* 428 */     if (!level().loadedAndEntityCanStandOnFace($$0.relative($$1), (Entity)this, $$2)) {
/* 429 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 433 */     AABB $$3 = getProgressAabb($$2, 1.0F).move($$0).deflate(1.0E-6D);
/* 434 */     return level().noCollision((Entity)this, $$3);
/*     */   }
/*     */   
/*     */   private boolean isPositionBlocked(BlockPos $$0) {
/* 438 */     BlockState $$1 = level().getBlockState($$0);
/* 439 */     if ($$1.isAir()) {
/* 440 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 444 */     boolean $$2 = ($$1.is(Blocks.MOVING_PISTON) && $$0.equals(blockPosition()));
/* 445 */     return !$$2;
/*     */   }
/*     */   
/*     */   protected boolean teleportSomewhere() {
/* 449 */     if (isNoAi() || !isAlive()) {
/* 450 */       return false;
/*     */     }
/* 452 */     BlockPos $$0 = blockPosition();
/* 453 */     for (int $$1 = 0; $$1 < 5; $$1++) {
/* 454 */       BlockPos $$2 = $$0.offset(
/* 455 */           Mth.randomBetweenInclusive(this.random, -8, 8), 
/* 456 */           Mth.randomBetweenInclusive(this.random, -8, 8), 
/* 457 */           Mth.randomBetweenInclusive(this.random, -8, 8));
/*     */       
/* 459 */       if ($$2.getY() > level().getMinBuildHeight() && level().isEmptyBlock($$2) && level().getWorldBorder().isWithinBounds($$2) && level().noCollision((Entity)this, (new AABB($$2)).deflate(1.0E-6D))) {
/* 460 */         Direction $$3 = findAttachableSurface($$2);
/* 461 */         if ($$3 != null) {
/* 462 */           unRide();
/*     */ 
/*     */           
/* 465 */           setAttachFace($$3);
/*     */           
/* 467 */           playSound(SoundEvents.SHULKER_TELEPORT, 1.0F, 1.0F);
/* 468 */           setPos($$2.getX() + 0.5D, $$2.getY(), $$2.getZ() + 0.5D);
/* 469 */           level().gameEvent(GameEvent.TELEPORT, $$0, GameEvent.Context.of((Entity)this));
/* 470 */           this.entityData.set(DATA_PEEK_ID, Byte.valueOf((byte)0));
/* 471 */           setTarget(null);
/* 472 */           return true;
/*     */         } 
/*     */       } 
/*     */     } 
/* 476 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void lerpTo(double $$0, double $$1, double $$2, float $$3, float $$4, int $$5) {
/* 481 */     this.lerpSteps = 0;
/* 482 */     setPos($$0, $$1, $$2);
/* 483 */     setRot($$3, $$4);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hurt(DamageSource $$0, float $$1) {
/* 488 */     if (isClosed()) {
/* 489 */       Entity $$2 = $$0.getDirectEntity();
/* 490 */       if ($$2 instanceof net.minecraft.world.entity.projectile.AbstractArrow) {
/* 491 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 495 */     if (super.hurt($$0, $$1)) {
/* 496 */       if (getHealth() < getMaxHealth() * 0.5D && this.random.nextInt(4) == 0) {
/* 497 */         teleportSomewhere();
/* 498 */       } else if ($$0.is(DamageTypeTags.IS_PROJECTILE)) {
/* 499 */         Entity $$3 = $$0.getDirectEntity();
/* 500 */         if ($$3 != null && $$3.getType() == EntityType.SHULKER_BULLET) {
/* 501 */           hitByShulkerBullet();
/*     */         }
/*     */       } 
/*     */       
/* 505 */       return true;
/*     */     } 
/* 507 */     return false;
/*     */   }
/*     */   
/*     */   private boolean isClosed() {
/* 511 */     return (getRawPeekAmount() == 0);
/*     */   }
/*     */   
/*     */   private void hitByShulkerBullet() {
/* 515 */     Vec3 $$0 = position();
/* 516 */     AABB $$1 = getBoundingBox();
/*     */     
/* 518 */     if (isClosed() || !teleportSomewhere()) {
/*     */       return;
/*     */     }
/*     */     
/* 522 */     int $$2 = level().getEntities((EntityTypeTest)EntityType.SHULKER, $$1.inflate(8.0D), Entity::isAlive).size();
/*     */     
/* 524 */     float $$3 = ($$2 - 1) / 5.0F;
/* 525 */     if ((level()).random.nextFloat() < $$3) {
/*     */       return;
/*     */     }
/*     */     
/* 529 */     Shulker $$4 = (Shulker)EntityType.SHULKER.create(level());
/*     */     
/* 531 */     if ($$4 != null) {
/* 532 */       $$4.setVariant(getVariant());
/* 533 */       $$4.moveTo($$0);
/* 534 */       level().addFreshEntity((Entity)$$4);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canBeCollidedWith() {
/* 540 */     return isAlive();
/*     */   }
/*     */   
/*     */   public Direction getAttachFace() {
/* 544 */     return (Direction)this.entityData.get(DATA_ATTACH_FACE_ID);
/*     */   }
/*     */   
/*     */   private void setAttachFace(Direction $$0) {
/* 548 */     this.entityData.set(DATA_ATTACH_FACE_ID, $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onSyncedDataUpdated(EntityDataAccessor<?> $$0) {
/* 553 */     if (DATA_ATTACH_FACE_ID.equals($$0)) {
/* 554 */       setBoundingBox(makeBoundingBox());
/*     */     }
/* 556 */     super.onSyncedDataUpdated($$0);
/*     */   }
/*     */   
/*     */   private int getRawPeekAmount() {
/* 560 */     return ((Byte)this.entityData.get(DATA_PEEK_ID)).byteValue();
/*     */   }
/*     */   
/*     */   void setRawPeekAmount(int $$0) {
/* 564 */     if (!(level()).isClientSide) {
/* 565 */       getAttribute(Attributes.ARMOR).removeModifier(COVERED_ARMOR_MODIFIER.getId());
/* 566 */       if ($$0 == 0) {
/* 567 */         getAttribute(Attributes.ARMOR).addPermanentModifier(COVERED_ARMOR_MODIFIER);
/* 568 */         playSound(SoundEvents.SHULKER_CLOSE, 1.0F, 1.0F);
/* 569 */         gameEvent(GameEvent.CONTAINER_CLOSE);
/*     */       } else {
/* 571 */         playSound(SoundEvents.SHULKER_OPEN, 1.0F, 1.0F);
/* 572 */         gameEvent(GameEvent.CONTAINER_OPEN);
/*     */       } 
/*     */     } 
/*     */     
/* 576 */     this.entityData.set(DATA_PEEK_ID, Byte.valueOf((byte)$$0));
/*     */   }
/*     */   
/*     */   public float getClientPeekAmount(float $$0) {
/* 580 */     return Mth.lerp($$0, this.currentPeekAmountO, this.currentPeekAmount);
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getStandingEyeHeight(Pose $$0, EntityDimensions $$1) {
/* 585 */     return 0.5F;
/*     */   }
/*     */ 
/*     */   
/*     */   public void recreateFromPacket(ClientboundAddEntityPacket $$0) {
/* 590 */     super.recreateFromPacket($$0);
/* 591 */     this.yBodyRot = 0.0F;
/* 592 */     this.yBodyRotO = 0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxHeadXRot() {
/* 597 */     return 180;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxHeadYRot() {
/* 602 */     return 180;
/*     */   }
/*     */ 
/*     */   
/*     */   public void push(Entity $$0) {}
/*     */ 
/*     */   
/*     */   public Optional<Vec3> getRenderPosition(float $$0) {
/* 610 */     if (this.clientOldAttachPosition == null || this.clientSideTeleportInterpolation <= 0) {
/* 611 */       return Optional.empty();
/*     */     }
/*     */     
/* 614 */     double $$1 = (this.clientSideTeleportInterpolation - $$0) / 6.0D;
/* 615 */     $$1 *= $$1;
/*     */     
/* 617 */     BlockPos $$2 = blockPosition();
/* 618 */     double $$3 = ($$2.getX() - this.clientOldAttachPosition.getX()) * $$1;
/* 619 */     double $$4 = ($$2.getY() - this.clientOldAttachPosition.getY()) * $$1;
/* 620 */     double $$5 = ($$2.getZ() - this.clientOldAttachPosition.getZ()) * $$1;
/*     */     
/* 622 */     return Optional.of(new Vec3(-$$3, -$$4, -$$5));
/*     */   }
/*     */   
/*     */   private static class ShulkerBodyRotationControl extends BodyRotationControl {
/*     */     public ShulkerBodyRotationControl(Mob $$0) {
/* 627 */       super($$0);
/*     */     }
/*     */ 
/*     */     
/*     */     public void clientTick() {}
/*     */   }
/*     */ 
/*     */   
/*     */   private class ShulkerPeekGoal
/*     */     extends Goal
/*     */   {
/*     */     private int peekTime;
/*     */     
/*     */     public boolean canUse() {
/* 641 */       return (Shulker.this.getTarget() == null && Shulker.this.random.nextInt(reducedTickDelay(40)) == 0 && Shulker.this.canStayAt(Shulker.this.blockPosition(), Shulker.this.getAttachFace()));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canContinueToUse() {
/* 646 */       return (Shulker.this.getTarget() == null && this.peekTime > 0);
/*     */     }
/*     */ 
/*     */     
/*     */     public void start() {
/* 651 */       this.peekTime = adjustedTickDelay(20 * (1 + Shulker.this.random.nextInt(3)));
/* 652 */       Shulker.this.setRawPeekAmount(30);
/*     */     }
/*     */ 
/*     */     
/*     */     public void stop() {
/* 657 */       if (Shulker.this.getTarget() == null) {
/* 658 */         Shulker.this.setRawPeekAmount(0);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick() {
/* 664 */       this.peekTime--;
/*     */     }
/*     */   }
/*     */   
/*     */   private class ShulkerAttackGoal extends Goal {
/*     */     private int attackTime;
/*     */     
/*     */     public ShulkerAttackGoal() {
/* 672 */       setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canUse() {
/* 677 */       LivingEntity $$0 = Shulker.this.getTarget();
/* 678 */       if ($$0 == null || !$$0.isAlive()) {
/* 679 */         return false;
/*     */       }
/* 681 */       if (Shulker.this.level().getDifficulty() == Difficulty.PEACEFUL) {
/* 682 */         return false;
/*     */       }
/*     */       
/* 685 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void start() {
/* 690 */       this.attackTime = 20;
/* 691 */       Shulker.this.setRawPeekAmount(100);
/*     */     }
/*     */ 
/*     */     
/*     */     public void stop() {
/* 696 */       Shulker.this.setRawPeekAmount(0);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean requiresUpdateEveryTick() {
/* 701 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick() {
/* 706 */       if (Shulker.this.level().getDifficulty() == Difficulty.PEACEFUL) {
/*     */         return;
/*     */       }
/* 709 */       this.attackTime--;
/*     */       
/* 711 */       LivingEntity $$0 = Shulker.this.getTarget();
/* 712 */       if ($$0 == null) {
/*     */         return;
/*     */       }
/* 715 */       Shulker.this.getLookControl().setLookAt((Entity)$$0, 180.0F, 180.0F);
/*     */       
/* 717 */       double $$1 = Shulker.this.distanceToSqr((Entity)$$0);
/*     */       
/* 719 */       if ($$1 < 400.0D) {
/* 720 */         if (this.attackTime <= 0) {
/* 721 */           this.attackTime = 20 + Shulker.this.random.nextInt(10) * 20 / 2;
/*     */           
/* 723 */           Shulker.this.level().addFreshEntity((Entity)new ShulkerBullet(Shulker.this.level(), (LivingEntity)Shulker.this, (Entity)$$0, Shulker.this.getAttachFace().getAxis()));
/* 724 */           Shulker.this.playSound(SoundEvents.SHULKER_SHOOT, 2.0F, (Shulker.this.random.nextFloat() - Shulker.this.random.nextFloat()) * 0.2F + 1.0F);
/*     */         } 
/*     */       } else {
/* 727 */         Shulker.this.setTarget(null);
/*     */       } 
/*     */       
/* 730 */       super.tick();
/*     */     }
/*     */   }
/*     */   
/*     */   private class ShulkerNearestAttackGoal extends NearestAttackableTargetGoal<Player> {
/*     */     public ShulkerNearestAttackGoal(Shulker $$0) {
/* 736 */       super((Mob)$$0, Player.class, true);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canUse() {
/* 741 */       if (Shulker.this.level().getDifficulty() == Difficulty.PEACEFUL) {
/* 742 */         return false;
/*     */       }
/* 744 */       return super.canUse();
/*     */     }
/*     */ 
/*     */     
/*     */     protected AABB getTargetSearchArea(double $$0) {
/* 749 */       Direction $$1 = ((Shulker)this.mob).getAttachFace();
/* 750 */       if ($$1.getAxis() == Direction.Axis.X) {
/* 751 */         return this.mob.getBoundingBox().inflate(4.0D, $$0, $$0);
/*     */       }
/* 753 */       if ($$1.getAxis() == Direction.Axis.Z) {
/* 754 */         return this.mob.getBoundingBox().inflate($$0, $$0, 4.0D);
/*     */       }
/* 756 */       return this.mob.getBoundingBox().inflate($$0, 4.0D, $$0);
/*     */     }
/*     */   }
/*     */   
/*     */   private static class ShulkerDefenseAttackGoal extends NearestAttackableTargetGoal<LivingEntity> {
/*     */     public ShulkerDefenseAttackGoal(Shulker $$0) {
/* 762 */       super((Mob)$$0, LivingEntity.class, 10, true, false, $$0 -> $$0 instanceof Enemy);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canUse() {
/* 767 */       if (this.mob.getTeam() == null) {
/* 768 */         return false;
/*     */       }
/* 770 */       return super.canUse();
/*     */     }
/*     */ 
/*     */     
/*     */     protected AABB getTargetSearchArea(double $$0) {
/* 775 */       Direction $$1 = ((Shulker)this.mob).getAttachFace();
/* 776 */       if ($$1.getAxis() == Direction.Axis.X) {
/* 777 */         return this.mob.getBoundingBox().inflate(4.0D, $$0, $$0);
/*     */       }
/* 779 */       if ($$1.getAxis() == Direction.Axis.Z) {
/* 780 */         return this.mob.getBoundingBox().inflate($$0, $$0, 4.0D);
/*     */       }
/* 782 */       return this.mob.getBoundingBox().inflate($$0, 4.0D, $$0);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void setVariant(Optional<DyeColor> $$0) {
/* 788 */     this.entityData.set(DATA_COLOR_ID, $$0.<Byte>map($$0 -> Byte.valueOf((byte)$$0.getId())).orElse(Byte.valueOf((byte)16)));
/*     */   }
/*     */ 
/*     */   
/*     */   public Optional<DyeColor> getVariant() {
/* 793 */     return Optional.ofNullable(getColor());
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public DyeColor getColor() {
/* 798 */     byte $$0 = ((Byte)this.entityData.get(DATA_COLOR_ID)).byteValue();
/* 799 */     if ($$0 == 16 || $$0 > 15) {
/* 800 */       return null;
/*     */     }
/* 802 */     return DyeColor.byId($$0);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\Shulker.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */