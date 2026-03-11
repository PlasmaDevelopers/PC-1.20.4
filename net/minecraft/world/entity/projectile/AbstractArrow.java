/*     */ package net.minecraft.world.entity.projectile;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.advancements.CriteriaTriggers;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.HolderGetter;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.NbtUtils;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
/*     */ import net.minecraft.network.syncher.EntityDataAccessor;
/*     */ import net.minecraft.network.syncher.EntityDataSerializers;
/*     */ import net.minecraft.network.syncher.SynchedEntityData;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.tags.EntityTypeTags;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityDimensions;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.MoverType;
/*     */ import net.minecraft.world.entity.Pose;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.world.item.enchantment.Enchantments;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.ClipContext;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ import net.minecraft.world.phys.EntityHitResult;
/*     */ import net.minecraft.world.phys.HitResult;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public abstract class AbstractArrow
/*     */   extends Projectile {
/*     */   private static final double ARROW_BASE_DAMAGE = 2.0D;
/*     */   
/*     */   public enum Pickup {
/*  58 */     DISALLOWED, ALLOWED, CREATIVE_ONLY;
/*     */     
/*     */     public static Pickup byOrdinal(int $$0) {
/*  61 */       if ($$0 < 0 || $$0 > (values()).length) {
/*  62 */         $$0 = 0;
/*     */       }
/*     */       
/*  65 */       return values()[$$0];
/*     */     }
/*     */   }
/*     */   
/*  69 */   private static final EntityDataAccessor<Byte> ID_FLAGS = SynchedEntityData.defineId(AbstractArrow.class, EntityDataSerializers.BYTE);
/*  70 */   private static final EntityDataAccessor<Byte> PIERCE_LEVEL = SynchedEntityData.defineId(AbstractArrow.class, EntityDataSerializers.BYTE);
/*     */   
/*     */   private static final int FLAG_CRIT = 1;
/*     */   private static final int FLAG_NOPHYSICS = 2;
/*     */   private static final int FLAG_CROSSBOW = 4;
/*     */   @Nullable
/*     */   private BlockState lastState;
/*     */   protected boolean inGround;
/*     */   protected int inGroundTime;
/*  79 */   public Pickup pickup = Pickup.DISALLOWED;
/*     */   public int shakeTime;
/*     */   private int life;
/*  82 */   private double baseDamage = 2.0D;
/*     */   
/*     */   private int knockback;
/*     */   
/*     */   private SoundEvent soundEvent;
/*     */   @Nullable
/*     */   private IntOpenHashSet piercingIgnoreEntityIds;
/*     */   @Nullable
/*     */   private List<Entity> piercedAndKilledEntities;
/*     */   private ItemStack pickupItemStack;
/*     */   
/*     */   protected AbstractArrow(EntityType<? extends AbstractArrow> $$0, Level $$1, ItemStack $$2) {
/*  94 */     super((EntityType)$$0, $$1);
/*     */     
/*  96 */     this.soundEvent = getDefaultHitGroundSoundEvent();
/*  97 */     this.pickupItemStack = $$2.copy();
/*     */     
/*  99 */     if ($$2.hasCustomHoverName()) {
/* 100 */       setCustomName($$2.getHoverName());
/*     */     }
/*     */   }
/*     */   
/*     */   protected AbstractArrow(EntityType<? extends AbstractArrow> $$0, double $$1, double $$2, double $$3, Level $$4, ItemStack $$5) {
/* 105 */     this($$0, $$4, $$5);
/*     */     
/* 107 */     setPos($$1, $$2, $$3);
/*     */   }
/*     */   
/*     */   protected AbstractArrow(EntityType<? extends AbstractArrow> $$0, LivingEntity $$1, Level $$2, ItemStack $$3) {
/* 111 */     this($$0, $$1.getX(), $$1.getEyeY() - 0.10000000149011612D, $$1.getZ(), $$2, $$3);
/*     */     
/* 113 */     setOwner((Entity)$$1);
/*     */     
/* 115 */     if ($$1 instanceof Player) {
/* 116 */       this.pickup = Pickup.ALLOWED;
/*     */     }
/*     */   }
/*     */   
/*     */   public void setSoundEvent(SoundEvent $$0) {
/* 121 */     this.soundEvent = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldRenderAtSqrDistance(double $$0) {
/* 126 */     double $$1 = getBoundingBox().getSize() * 10.0D;
/* 127 */     if (Double.isNaN($$1)) {
/* 128 */       $$1 = 1.0D;
/*     */     }
/* 130 */     $$1 *= 64.0D * getViewScale();
/* 131 */     return ($$0 < $$1 * $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {
/* 136 */     this.entityData.define(ID_FLAGS, Byte.valueOf((byte)0));
/* 137 */     this.entityData.define(PIERCE_LEVEL, Byte.valueOf((byte)0));
/*     */   }
/*     */ 
/*     */   
/*     */   public void shoot(double $$0, double $$1, double $$2, float $$3, float $$4) {
/* 142 */     super.shoot($$0, $$1, $$2, $$3, $$4);
/* 143 */     this.life = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void lerpTo(double $$0, double $$1, double $$2, float $$3, float $$4, int $$5) {
/* 148 */     setPos($$0, $$1, $$2);
/* 149 */     setRot($$3, $$4);
/*     */   }
/*     */ 
/*     */   
/*     */   public void lerpMotion(double $$0, double $$1, double $$2) {
/* 154 */     super.lerpMotion($$0, $$1, $$2);
/* 155 */     this.life = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 160 */     super.tick();
/*     */     
/* 162 */     boolean $$0 = isNoPhysics();
/*     */     
/* 164 */     Vec3 $$1 = getDeltaMovement();
/* 165 */     if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
/* 166 */       double $$2 = $$1.horizontalDistance();
/* 167 */       setYRot((float)(Mth.atan2($$1.x, $$1.z) * 57.2957763671875D));
/* 168 */       setXRot((float)(Mth.atan2($$1.y, $$2) * 57.2957763671875D));
/* 169 */       this.yRotO = getYRot();
/* 170 */       this.xRotO = getXRot();
/*     */     } 
/*     */     
/* 173 */     BlockPos $$3 = blockPosition();
/* 174 */     BlockState $$4 = level().getBlockState($$3);
/* 175 */     if (!$$4.isAir() && !$$0) {
/* 176 */       VoxelShape $$5 = $$4.getCollisionShape((BlockGetter)level(), $$3);
/* 177 */       if (!$$5.isEmpty()) {
/* 178 */         Vec3 $$6 = position();
/* 179 */         for (AABB $$7 : $$5.toAabbs()) {
/* 180 */           if ($$7.move($$3).contains($$6)) {
/* 181 */             this.inGround = true;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 188 */     if (this.shakeTime > 0) {
/* 189 */       this.shakeTime--;
/*     */     }
/*     */     
/* 192 */     if (isInWaterOrRain() || $$4.is(Blocks.POWDER_SNOW)) {
/* 193 */       clearFire();
/*     */     }
/*     */     
/* 196 */     if (this.inGround && !$$0) {
/* 197 */       if (this.lastState != $$4 && shouldFall()) {
/* 198 */         startFalling();
/* 199 */       } else if (!(level()).isClientSide) {
/* 200 */         tickDespawn();
/*     */       } 
/*     */       
/* 203 */       this.inGroundTime++;
/*     */       return;
/*     */     } 
/* 206 */     this.inGroundTime = 0;
/*     */ 
/*     */     
/* 209 */     Vec3 $$8 = position();
/* 210 */     Vec3 $$9 = $$8.add($$1);
/* 211 */     BlockHitResult blockHitResult = level().clip(new ClipContext($$8, $$9, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
/*     */     
/* 213 */     if (blockHitResult.getType() != HitResult.Type.MISS) {
/* 214 */       $$9 = blockHitResult.getLocation();
/*     */     }
/*     */ 
/*     */     
/* 218 */     while (!isRemoved()) {
/* 219 */       EntityHitResult $$11 = findHitEntity($$8, $$9);
/*     */       
/* 221 */       if ($$11 != null) {
/* 222 */         entityHitResult1 = $$11;
/*     */       }
/*     */       
/* 225 */       if (entityHitResult1 != null && entityHitResult1.getType() == HitResult.Type.ENTITY) {
/* 226 */         Entity $$12 = entityHitResult1.getEntity();
/* 227 */         Entity $$13 = getOwner();
/* 228 */         if ($$12 instanceof Player && $$13 instanceof Player && !((Player)$$13).canHarmPlayer((Player)$$12)) {
/* 229 */           entityHitResult1 = null;
/* 230 */           $$11 = null;
/*     */         } 
/*     */       } 
/*     */       
/* 234 */       if (entityHitResult1 != null && !$$0) {
/* 235 */         onHit((HitResult)entityHitResult1);
/* 236 */         this.hasImpulse = true;
/*     */       } 
/*     */       
/* 239 */       if ($$11 == null || getPierceLevel() <= 0) {
/*     */         break;
/*     */       }
/* 242 */       EntityHitResult entityHitResult1 = null;
/*     */     } 
/*     */     
/* 245 */     $$1 = getDeltaMovement();
/* 246 */     double $$14 = $$1.x;
/* 247 */     double $$15 = $$1.y;
/* 248 */     double $$16 = $$1.z;
/*     */     
/* 250 */     if (isCritArrow()) {
/* 251 */       for (int $$17 = 0; $$17 < 4; $$17++) {
/* 252 */         level().addParticle((ParticleOptions)ParticleTypes.CRIT, getX() + $$14 * $$17 / 4.0D, getY() + $$15 * $$17 / 4.0D, getZ() + $$16 * $$17 / 4.0D, -$$14, -$$15 + 0.2D, -$$16);
/*     */       }
/*     */     }
/*     */     
/* 256 */     double $$18 = getX() + $$14;
/* 257 */     double $$19 = getY() + $$15;
/* 258 */     double $$20 = getZ() + $$16;
/*     */     
/* 260 */     double $$21 = $$1.horizontalDistance();
/* 261 */     if ($$0) {
/* 262 */       setYRot((float)(Mth.atan2(-$$14, -$$16) * 57.2957763671875D));
/*     */     } else {
/* 264 */       setYRot((float)(Mth.atan2($$14, $$16) * 57.2957763671875D));
/*     */     } 
/* 266 */     setXRot((float)(Mth.atan2($$15, $$21) * 57.2957763671875D));
/*     */     
/* 268 */     setXRot(lerpRotation(this.xRotO, getXRot()));
/* 269 */     setYRot(lerpRotation(this.yRotO, getYRot()));
/*     */     
/* 271 */     float $$22 = 0.99F;
/* 272 */     float $$23 = 0.05F;
/*     */     
/* 274 */     if (isInWater()) {
/* 275 */       for (int $$24 = 0; $$24 < 4; $$24++) {
/* 276 */         float $$25 = 0.25F;
/* 277 */         level().addParticle((ParticleOptions)ParticleTypes.BUBBLE, $$18 - $$14 * 0.25D, $$19 - $$15 * 0.25D, $$20 - $$16 * 0.25D, $$14, $$15, $$16);
/*     */       } 
/* 279 */       $$22 = getWaterInertia();
/*     */     } 
/*     */     
/* 282 */     setDeltaMovement($$1.scale($$22));
/*     */     
/* 284 */     if (!isNoGravity() && !$$0) {
/* 285 */       Vec3 $$26 = getDeltaMovement();
/* 286 */       setDeltaMovement($$26.x, $$26.y - 0.05000000074505806D, $$26.z);
/*     */     } 
/*     */     
/* 289 */     setPos($$18, $$19, $$20);
/*     */     
/* 291 */     checkInsideBlocks();
/*     */   }
/*     */   
/*     */   private boolean shouldFall() {
/* 295 */     return (this.inGround && level().noCollision((new AABB(position(), position())).inflate(0.06D)));
/*     */   }
/*     */   
/*     */   private void startFalling() {
/* 299 */     this.inGround = false;
/* 300 */     Vec3 $$0 = getDeltaMovement();
/* 301 */     setDeltaMovement($$0.multiply((this.random
/* 302 */           .nextFloat() * 0.2F), (this.random
/* 303 */           .nextFloat() * 0.2F), (this.random
/* 304 */           .nextFloat() * 0.2F)));
/*     */     
/* 306 */     this.life = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void move(MoverType $$0, Vec3 $$1) {
/* 311 */     super.move($$0, $$1);
/* 312 */     if ($$0 != MoverType.SELF && shouldFall()) {
/* 313 */       startFalling();
/*     */     }
/*     */   }
/*     */   
/*     */   protected void tickDespawn() {
/* 318 */     this.life++;
/* 319 */     if (this.life >= 1200) {
/* 320 */       discard();
/*     */     }
/*     */   }
/*     */   
/*     */   private void resetPiercedEntities() {
/* 325 */     if (this.piercedAndKilledEntities != null) {
/* 326 */       this.piercedAndKilledEntities.clear();
/*     */     }
/* 328 */     if (this.piercingIgnoreEntityIds != null) {
/* 329 */       this.piercingIgnoreEntityIds.clear();
/*     */     }
/*     */   }
/*     */   
/*     */   protected void onHitEntity(EntityHitResult $$0) {
/*     */     DamageSource $$7;
/* 335 */     super.onHitEntity($$0);
/* 336 */     Entity $$1 = $$0.getEntity();
/* 337 */     float $$2 = (float)getDeltaMovement().length();
/* 338 */     int $$3 = Mth.ceil(Mth.clamp($$2 * this.baseDamage, 0.0D, 2.147483647E9D));
/*     */     
/* 340 */     if (getPierceLevel() > 0) {
/* 341 */       if (this.piercingIgnoreEntityIds == null) {
/* 342 */         this.piercingIgnoreEntityIds = new IntOpenHashSet(5);
/*     */       }
/*     */       
/* 345 */       if (this.piercedAndKilledEntities == null) {
/* 346 */         this.piercedAndKilledEntities = Lists.newArrayListWithCapacity(5);
/*     */       }
/*     */ 
/*     */       
/* 350 */       if (this.piercingIgnoreEntityIds.size() < getPierceLevel() + 1) {
/* 351 */         this.piercingIgnoreEntityIds.add($$1.getId());
/*     */       } else {
/* 353 */         discard();
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 358 */     if (isCritArrow()) {
/* 359 */       long $$4 = this.random.nextInt($$3 / 2 + 2);
/* 360 */       $$3 = (int)Math.min($$4 + $$3, 2147483647L);
/*     */     } 
/*     */ 
/*     */     
/* 364 */     Entity $$5 = getOwner();
/* 365 */     if ($$5 == null) {
/* 366 */       DamageSource $$6 = damageSources().arrow(this, this);
/*     */     } else {
/* 368 */       $$7 = damageSources().arrow(this, $$5);
/* 369 */       if ($$5 instanceof LivingEntity) {
/* 370 */         ((LivingEntity)$$5).setLastHurtMob($$1);
/*     */       }
/*     */     } 
/*     */     
/* 374 */     boolean $$8 = ($$1.getType() == EntityType.ENDERMAN);
/* 375 */     int $$9 = $$1.getRemainingFireTicks();
/* 376 */     boolean $$10 = $$1.getType().is(EntityTypeTags.DEFLECTS_ARROWS);
/*     */ 
/*     */     
/* 379 */     if (isOnFire() && !$$8 && !$$10) {
/* 380 */       $$1.setSecondsOnFire(5);
/*     */     }
/*     */     
/* 383 */     if ($$1.hurt($$7, $$3)) {
/*     */       
/* 385 */       if ($$8) {
/*     */         return;
/*     */       }
/* 388 */       if ($$1 instanceof LivingEntity) { LivingEntity $$11 = (LivingEntity)$$1;
/* 389 */         if (!(level()).isClientSide && getPierceLevel() <= 0) {
/* 390 */           $$11.setArrowCount($$11.getArrowCount() + 1);
/*     */         }
/*     */         
/* 393 */         if (this.knockback > 0) {
/* 394 */           double $$12 = Math.max(0.0D, 1.0D - $$11.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
/* 395 */           Vec3 $$13 = getDeltaMovement().multiply(1.0D, 0.0D, 1.0D).normalize().scale(this.knockback * 0.6D * $$12);
/* 396 */           if ($$13.lengthSqr() > 0.0D) {
/* 397 */             $$11.push($$13.x, 0.1D, $$13.z);
/*     */           }
/*     */         } 
/*     */         
/* 401 */         if (!(level()).isClientSide && $$5 instanceof LivingEntity) {
/* 402 */           EnchantmentHelper.doPostHurtEffects($$11, $$5);
/* 403 */           EnchantmentHelper.doPostDamageEffects((LivingEntity)$$5, (Entity)$$11);
/*     */         } 
/*     */         
/* 406 */         doPostHurtEffects($$11);
/*     */         
/* 408 */         if ($$5 != null && $$11 != $$5 && $$11 instanceof Player && $$5 instanceof ServerPlayer && !isSilent()) {
/* 409 */           ((ServerPlayer)$$5).connection.send((Packet)new ClientboundGameEventPacket(ClientboundGameEventPacket.ARROW_HIT_PLAYER, 0.0F));
/*     */         }
/*     */         
/* 412 */         if (!$$1.isAlive() && this.piercedAndKilledEntities != null) {
/* 413 */           this.piercedAndKilledEntities.add($$11);
/*     */         }
/*     */         
/* 416 */         if (!(level()).isClientSide && $$5 instanceof ServerPlayer) { ServerPlayer $$14 = (ServerPlayer)$$5;
/* 417 */           if (this.piercedAndKilledEntities != null && shotFromCrossbow()) {
/* 418 */             CriteriaTriggers.KILLED_BY_CROSSBOW.trigger($$14, this.piercedAndKilledEntities);
/*     */           }
/* 420 */           else if (!$$1.isAlive() && shotFromCrossbow()) {
/* 421 */             CriteriaTriggers.KILLED_BY_CROSSBOW.trigger($$14, Arrays.asList(new Entity[] { $$1 }));
/*     */           }  }
/*     */          }
/*     */ 
/*     */       
/* 426 */       playSound(this.soundEvent, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
/* 427 */       if (getPierceLevel() <= 0) {
/* 428 */         discard();
/*     */       }
/* 430 */     } else if ($$10) {
/* 431 */       deflect();
/*     */     } else {
/*     */       
/* 434 */       $$1.setRemainingFireTicks($$9);
/*     */       
/* 436 */       setDeltaMovement(getDeltaMovement().scale(-0.1D));
/* 437 */       setYRot(getYRot() + 180.0F);
/* 438 */       this.yRotO += 180.0F;
/*     */       
/* 440 */       if (!(level()).isClientSide && getDeltaMovement().lengthSqr() < 1.0E-7D) {
/* 441 */         if (this.pickup == Pickup.ALLOWED) {
/* 442 */           spawnAtLocation(getPickupItem(), 0.1F);
/*     */         }
/* 444 */         discard();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void deflect() {
/* 450 */     float $$0 = this.random.nextFloat() * 360.0F;
/* 451 */     setDeltaMovement(getDeltaMovement().yRot($$0 * 0.017453292F).scale(0.5D));
/* 452 */     setYRot(getYRot() + $$0);
/* 453 */     this.yRotO += $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onHitBlock(BlockHitResult $$0) {
/* 458 */     this.lastState = level().getBlockState($$0.getBlockPos());
/* 459 */     super.onHitBlock($$0);
/*     */     
/* 461 */     Vec3 $$1 = $$0.getLocation().subtract(getX(), getY(), getZ());
/* 462 */     setDeltaMovement($$1);
/*     */     
/* 464 */     Vec3 $$2 = $$1.normalize().scale(0.05000000074505806D);
/* 465 */     setPosRaw(getX() - $$2.x, getY() - $$2.y, getZ() - $$2.z);
/*     */     
/* 467 */     playSound(getHitGroundSoundEvent(), 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
/* 468 */     this.inGround = true;
/* 469 */     this.shakeTime = 7;
/* 470 */     setCritArrow(false);
/* 471 */     setPierceLevel((byte)0);
/* 472 */     setSoundEvent(SoundEvents.ARROW_HIT);
/* 473 */     setShotFromCrossbow(false);
/* 474 */     resetPiercedEntities();
/*     */   }
/*     */   
/*     */   protected SoundEvent getDefaultHitGroundSoundEvent() {
/* 478 */     return SoundEvents.ARROW_HIT;
/*     */   }
/*     */   
/*     */   protected final SoundEvent getHitGroundSoundEvent() {
/* 482 */     return this.soundEvent;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doPostHurtEffects(LivingEntity $$0) {}
/*     */   
/*     */   @Nullable
/*     */   protected EntityHitResult findHitEntity(Vec3 $$0, Vec3 $$1) {
/* 490 */     return ProjectileUtil.getEntityHitResult(level(), this, $$0, $$1, getBoundingBox().expandTowards(getDeltaMovement()).inflate(1.0D), this::canHitEntity);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canHitEntity(Entity $$0) {
/* 495 */     return (super.canHitEntity($$0) && (this.piercingIgnoreEntityIds == null || !this.piercingIgnoreEntityIds.contains($$0.getId())));
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/* 500 */     super.addAdditionalSaveData($$0);
/*     */     
/* 502 */     $$0.putShort("life", (short)this.life);
/*     */     
/* 504 */     if (this.lastState != null) {
/* 505 */       $$0.put("inBlockState", (Tag)NbtUtils.writeBlockState(this.lastState));
/*     */     }
/*     */     
/* 508 */     $$0.putByte("shake", (byte)this.shakeTime);
/* 509 */     $$0.putBoolean("inGround", this.inGround);
/* 510 */     $$0.putByte("pickup", (byte)this.pickup.ordinal());
/* 511 */     $$0.putDouble("damage", this.baseDamage);
/* 512 */     $$0.putBoolean("crit", isCritArrow());
/* 513 */     $$0.putByte("PierceLevel", getPierceLevel());
/* 514 */     $$0.putString("SoundEvent", BuiltInRegistries.SOUND_EVENT.getKey(this.soundEvent).toString());
/* 515 */     $$0.putBoolean("ShotFromCrossbow", shotFromCrossbow());
/* 516 */     $$0.put("item", (Tag)this.pickupItemStack.save(new CompoundTag()));
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/* 521 */     super.readAdditionalSaveData($$0);
/*     */     
/* 523 */     this.life = $$0.getShort("life");
/* 524 */     if ($$0.contains("inBlockState", 10)) {
/* 525 */       this.lastState = NbtUtils.readBlockState((HolderGetter)level().holderLookup(Registries.BLOCK), $$0.getCompound("inBlockState"));
/*     */     }
/* 527 */     this.shakeTime = $$0.getByte("shake") & 0xFF;
/* 528 */     this.inGround = $$0.getBoolean("inGround");
/* 529 */     if ($$0.contains("damage", 99)) {
/* 530 */       this.baseDamage = $$0.getDouble("damage");
/*     */     }
/* 532 */     this.pickup = Pickup.byOrdinal($$0.getByte("pickup"));
/*     */     
/* 534 */     setCritArrow($$0.getBoolean("crit"));
/* 535 */     setPierceLevel($$0.getByte("PierceLevel"));
/*     */     
/* 537 */     if ($$0.contains("SoundEvent", 8)) {
/* 538 */       this.soundEvent = BuiltInRegistries.SOUND_EVENT.getOptional(new ResourceLocation($$0.getString("SoundEvent"))).orElse(getDefaultHitGroundSoundEvent());
/*     */     }
/*     */     
/* 541 */     setShotFromCrossbow($$0.getBoolean("ShotFromCrossbow"));
/*     */     
/* 543 */     if ($$0.contains("item", 10))
/*     */     {
/* 545 */       this.pickupItemStack = ItemStack.of($$0.getCompound("item"));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void setOwner(@Nullable Entity $$0) {
/* 551 */     super.setOwner($$0);
/*     */     
/* 553 */     if ($$0 instanceof Player) {
/* 554 */       this.pickup = (((Player)$$0).getAbilities()).instabuild ? Pickup.CREATIVE_ONLY : Pickup.ALLOWED;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void playerTouch(Player $$0) {
/* 560 */     if ((level()).isClientSide || (!this.inGround && !isNoPhysics()) || this.shakeTime > 0) {
/*     */       return;
/*     */     }
/*     */     
/* 564 */     if (tryPickup($$0)) {
/* 565 */       $$0.take(this, 1);
/* 566 */       discard();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean tryPickup(Player $$0) {
/* 572 */     switch (this.pickup) {
/*     */       case ALLOWED:
/* 574 */         return $$0.getInventory().add(getPickupItem());
/*     */       case CREATIVE_ONLY:
/* 576 */         return ($$0.getAbilities()).instabuild;
/*     */     } 
/*     */     
/* 579 */     return false;
/*     */   }
/*     */   
/*     */   protected ItemStack getPickupItem() {
/* 583 */     return this.pickupItemStack.copy();
/*     */   }
/*     */ 
/*     */   
/*     */   protected Entity.MovementEmission getMovementEmission() {
/* 588 */     return Entity.MovementEmission.NONE;
/*     */   }
/*     */   
/*     */   public ItemStack getPickupItemStackOrigin() {
/* 592 */     return this.pickupItemStack;
/*     */   }
/*     */   
/*     */   public void setBaseDamage(double $$0) {
/* 596 */     this.baseDamage = $$0;
/*     */   }
/*     */   
/*     */   public double getBaseDamage() {
/* 600 */     return this.baseDamage;
/*     */   }
/*     */   
/*     */   public void setKnockback(int $$0) {
/* 604 */     this.knockback = $$0;
/*     */   }
/*     */   
/*     */   public int getKnockback() {
/* 608 */     return this.knockback;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAttackable() {
/* 613 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getEyeHeight(Pose $$0, EntityDimensions $$1) {
/* 618 */     return 0.13F;
/*     */   }
/*     */   
/*     */   public void setCritArrow(boolean $$0) {
/* 622 */     setFlag(1, $$0);
/*     */   }
/*     */   
/*     */   public void setPierceLevel(byte $$0) {
/* 626 */     this.entityData.set(PIERCE_LEVEL, Byte.valueOf($$0));
/*     */   }
/*     */   
/*     */   private void setFlag(int $$0, boolean $$1) {
/* 630 */     byte $$2 = ((Byte)this.entityData.get(ID_FLAGS)).byteValue();
/* 631 */     if ($$1) {
/* 632 */       this.entityData.set(ID_FLAGS, Byte.valueOf((byte)($$2 | $$0)));
/*     */     } else {
/* 634 */       this.entityData.set(ID_FLAGS, Byte.valueOf((byte)($$2 & ($$0 ^ 0xFFFFFFFF))));
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isCritArrow() {
/* 639 */     byte $$0 = ((Byte)this.entityData.get(ID_FLAGS)).byteValue();
/* 640 */     return (($$0 & 0x1) != 0);
/*     */   }
/*     */   
/*     */   public boolean shotFromCrossbow() {
/* 644 */     byte $$0 = ((Byte)this.entityData.get(ID_FLAGS)).byteValue();
/* 645 */     return (($$0 & 0x4) != 0);
/*     */   }
/*     */   
/*     */   public byte getPierceLevel() {
/* 649 */     return ((Byte)this.entityData.get(PIERCE_LEVEL)).byteValue();
/*     */   }
/*     */   
/*     */   public void setEnchantmentEffectsFromEntity(LivingEntity $$0, float $$1) {
/* 653 */     int $$2 = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER_ARROWS, $$0);
/* 654 */     int $$3 = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH_ARROWS, $$0);
/* 655 */     setBaseDamage(($$1 * 2.0F) + this.random.triangle(level().getDifficulty().getId() * 0.11D, 0.57425D));
/*     */     
/* 657 */     if ($$2 > 0) {
/* 658 */       setBaseDamage(getBaseDamage() + $$2 * 0.5D + 0.5D);
/*     */     }
/* 660 */     if ($$3 > 0) {
/* 661 */       setKnockback($$3);
/*     */     }
/* 663 */     if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAMING_ARROWS, $$0) > 0) {
/* 664 */       setSecondsOnFire(100);
/*     */     }
/*     */   }
/*     */   
/*     */   protected float getWaterInertia() {
/* 669 */     return 0.6F;
/*     */   }
/*     */   
/*     */   public void setNoPhysics(boolean $$0) {
/* 673 */     this.noPhysics = $$0;
/* 674 */     setFlag(2, $$0);
/*     */   }
/*     */   
/*     */   public boolean isNoPhysics() {
/* 678 */     if (!(level()).isClientSide) {
/* 679 */       return this.noPhysics;
/*     */     }
/* 681 */     return ((((Byte)this.entityData.get(ID_FLAGS)).byteValue() & 0x2) != 0);
/*     */   }
/*     */   
/*     */   public void setShotFromCrossbow(boolean $$0) {
/* 685 */     setFlag(4, $$0);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\projectile\AbstractArrow.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */