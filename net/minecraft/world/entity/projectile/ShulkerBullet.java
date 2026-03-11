/*     */ package net.minecraft.world.entity.projectile;
/*     */ 
/*     */ import com.google.common.base.MoreObjects;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Position;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.Difficulty;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.effect.MobEffectInstance;
/*     */ import net.minecraft.world.effect.MobEffects;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ import net.minecraft.world.phys.EntityHitResult;
/*     */ import net.minecraft.world.phys.HitResult;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ShulkerBullet
/*     */   extends Projectile
/*     */ {
/*     */   private static final double SPEED = 0.15D;
/*     */   @Nullable
/*     */   private Entity finalTarget;
/*     */   @Nullable
/*     */   private Direction currentMoveDirection;
/*     */   private int flightSteps;
/*     */   private double targetDeltaX;
/*     */   private double targetDeltaY;
/*     */   private double targetDeltaZ;
/*     */   @Nullable
/*     */   private UUID targetId;
/*     */   
/*     */   public ShulkerBullet(EntityType<? extends ShulkerBullet> $$0, Level $$1) {
/*  52 */     super((EntityType)$$0, $$1);
/*     */     
/*  54 */     this.noPhysics = true;
/*     */   }
/*     */   
/*     */   public ShulkerBullet(Level $$0, LivingEntity $$1, Entity $$2, Direction.Axis $$3) {
/*  58 */     this(EntityType.SHULKER_BULLET, $$0);
/*  59 */     setOwner((Entity)$$1);
/*     */     
/*  61 */     BlockPos $$4 = $$1.blockPosition();
/*  62 */     double $$5 = $$4.getX() + 0.5D;
/*  63 */     double $$6 = $$4.getY() + 0.5D;
/*  64 */     double $$7 = $$4.getZ() + 0.5D;
/*     */     
/*  66 */     moveTo($$5, $$6, $$7, getYRot(), getXRot());
/*     */     
/*  68 */     this.finalTarget = $$2;
/*     */     
/*  70 */     this.currentMoveDirection = Direction.UP;
/*  71 */     selectNextMoveDirection($$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public SoundSource getSoundSource() {
/*  76 */     return SoundSource.HOSTILE;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addAdditionalSaveData(CompoundTag $$0) {
/*  81 */     super.addAdditionalSaveData($$0);
/*  82 */     if (this.finalTarget != null) {
/*  83 */       $$0.putUUID("Target", this.finalTarget.getUUID());
/*     */     }
/*  85 */     if (this.currentMoveDirection != null) {
/*  86 */       $$0.putInt("Dir", this.currentMoveDirection.get3DDataValue());
/*     */     }
/*  88 */     $$0.putInt("Steps", this.flightSteps);
/*  89 */     $$0.putDouble("TXD", this.targetDeltaX);
/*  90 */     $$0.putDouble("TYD", this.targetDeltaY);
/*  91 */     $$0.putDouble("TZD", this.targetDeltaZ);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void readAdditionalSaveData(CompoundTag $$0) {
/*  96 */     super.readAdditionalSaveData($$0);
/*  97 */     this.flightSteps = $$0.getInt("Steps");
/*  98 */     this.targetDeltaX = $$0.getDouble("TXD");
/*  99 */     this.targetDeltaY = $$0.getDouble("TYD");
/* 100 */     this.targetDeltaZ = $$0.getDouble("TZD");
/* 101 */     if ($$0.contains("Dir", 99)) {
/* 102 */       this.currentMoveDirection = Direction.from3DDataValue($$0.getInt("Dir"));
/*     */     }
/* 104 */     if ($$0.hasUUID("Target")) {
/* 105 */       this.targetId = $$0.getUUID("Target");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {}
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private Direction getMoveDirection() {
/* 115 */     return this.currentMoveDirection;
/*     */   }
/*     */   
/*     */   private void setMoveDirection(@Nullable Direction $$0) {
/* 119 */     this.currentMoveDirection = $$0;
/*     */   }
/*     */   
/*     */   private void selectNextMoveDirection(@Nullable Direction.Axis $$0) {
/*     */     BlockPos $$3;
/* 124 */     double $$1 = 0.5D;
/* 125 */     if (this.finalTarget == null) {
/* 126 */       BlockPos $$2 = blockPosition().below();
/*     */     } else {
/* 128 */       $$1 = this.finalTarget.getBbHeight() * 0.5D;
/* 129 */       $$3 = BlockPos.containing(this.finalTarget.getX(), this.finalTarget.getY() + $$1, this.finalTarget.getZ());
/*     */     } 
/*     */     
/* 132 */     double $$4 = $$3.getX() + 0.5D;
/* 133 */     double $$5 = $$3.getY() + $$1;
/* 134 */     double $$6 = $$3.getZ() + 0.5D;
/*     */     
/* 136 */     Direction $$7 = null;
/* 137 */     if (!$$3.closerToCenterThan((Position)position(), 2.0D)) {
/* 138 */       BlockPos $$8 = blockPosition();
/* 139 */       List<Direction> $$9 = Lists.newArrayList();
/*     */       
/* 141 */       if ($$0 != Direction.Axis.X) {
/* 142 */         if ($$8.getX() < $$3.getX() && level().isEmptyBlock($$8.east())) {
/* 143 */           $$9.add(Direction.EAST);
/* 144 */         } else if ($$8.getX() > $$3.getX() && level().isEmptyBlock($$8.west())) {
/* 145 */           $$9.add(Direction.WEST);
/*     */         } 
/*     */       }
/* 148 */       if ($$0 != Direction.Axis.Y) {
/* 149 */         if ($$8.getY() < $$3.getY() && level().isEmptyBlock($$8.above())) {
/* 150 */           $$9.add(Direction.UP);
/* 151 */         } else if ($$8.getY() > $$3.getY() && level().isEmptyBlock($$8.below())) {
/* 152 */           $$9.add(Direction.DOWN);
/*     */         } 
/*     */       }
/* 155 */       if ($$0 != Direction.Axis.Z) {
/* 156 */         if ($$8.getZ() < $$3.getZ() && level().isEmptyBlock($$8.south())) {
/* 157 */           $$9.add(Direction.SOUTH);
/* 158 */         } else if ($$8.getZ() > $$3.getZ() && level().isEmptyBlock($$8.north())) {
/* 159 */           $$9.add(Direction.NORTH);
/*     */         } 
/*     */       }
/*     */       
/* 163 */       $$7 = Direction.getRandom(this.random);
/* 164 */       if ($$9.isEmpty()) {
/* 165 */         int $$10 = 5;
/* 166 */         while (!level().isEmptyBlock($$8.relative($$7)) && $$10 > 0) {
/* 167 */           $$7 = Direction.getRandom(this.random);
/* 168 */           $$10--;
/*     */         } 
/*     */       } else {
/* 171 */         $$7 = $$9.get(this.random.nextInt($$9.size()));
/*     */       } 
/*     */       
/* 174 */       $$4 = getX() + $$7.getStepX();
/* 175 */       $$5 = getY() + $$7.getStepY();
/* 176 */       $$6 = getZ() + $$7.getStepZ();
/*     */     } 
/*     */     
/* 179 */     setMoveDirection($$7);
/*     */     
/* 181 */     double $$11 = $$4 - getX();
/* 182 */     double $$12 = $$5 - getY();
/* 183 */     double $$13 = $$6 - getZ();
/*     */     
/* 185 */     double $$14 = Math.sqrt($$11 * $$11 + $$12 * $$12 + $$13 * $$13);
/* 186 */     if ($$14 == 0.0D) {
/* 187 */       this.targetDeltaX = 0.0D;
/* 188 */       this.targetDeltaY = 0.0D;
/* 189 */       this.targetDeltaZ = 0.0D;
/*     */     } else {
/* 191 */       this.targetDeltaX = $$11 / $$14 * 0.15D;
/* 192 */       this.targetDeltaY = $$12 / $$14 * 0.15D;
/* 193 */       this.targetDeltaZ = $$13 / $$14 * 0.15D;
/*     */     } 
/*     */     
/* 196 */     this.hasImpulse = true;
/* 197 */     this.flightSteps = 10 + this.random.nextInt(5) * 10;
/*     */   }
/*     */ 
/*     */   
/*     */   public void checkDespawn() {
/* 202 */     if (level().getDifficulty() == Difficulty.PEACEFUL) {
/* 203 */       discard();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 209 */     super.tick();
/*     */     
/* 211 */     if (!(level()).isClientSide) {
/* 212 */       if (this.finalTarget == null && this.targetId != null) {
/* 213 */         this.finalTarget = ((ServerLevel)level()).getEntity(this.targetId);
/* 214 */         if (this.finalTarget == null) {
/* 215 */           this.targetId = null;
/*     */         }
/*     */       } 
/*     */       
/* 219 */       if (this.finalTarget != null && this.finalTarget.isAlive() && (!(this.finalTarget instanceof net.minecraft.world.entity.player.Player) || !this.finalTarget.isSpectator())) {
/* 220 */         this.targetDeltaX = Mth.clamp(this.targetDeltaX * 1.025D, -1.0D, 1.0D);
/* 221 */         this.targetDeltaY = Mth.clamp(this.targetDeltaY * 1.025D, -1.0D, 1.0D);
/* 222 */         this.targetDeltaZ = Mth.clamp(this.targetDeltaZ * 1.025D, -1.0D, 1.0D);
/*     */         
/* 224 */         Vec3 $$0 = getDeltaMovement();
/* 225 */         setDeltaMovement($$0.add((this.targetDeltaX - $$0.x) * 0.2D, (this.targetDeltaY - $$0.y) * 0.2D, (this.targetDeltaZ - $$0.z) * 0.2D));
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       }
/* 231 */       else if (!isNoGravity()) {
/* 232 */         setDeltaMovement(getDeltaMovement().add(0.0D, -0.04D, 0.0D));
/*     */       } 
/*     */ 
/*     */       
/* 236 */       HitResult $$1 = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
/* 237 */       if ($$1.getType() != HitResult.Type.MISS) {
/* 238 */         onHit($$1);
/*     */       }
/*     */     } 
/*     */     
/* 242 */     checkInsideBlocks();
/* 243 */     Vec3 $$2 = getDeltaMovement();
/* 244 */     setPos(getX() + $$2.x, getY() + $$2.y, getZ() + $$2.z);
/*     */     
/* 246 */     ProjectileUtil.rotateTowardsMovement(this, 0.5F);
/*     */     
/* 248 */     if ((level()).isClientSide) {
/* 249 */       level().addParticle((ParticleOptions)ParticleTypes.END_ROD, getX() - $$2.x, getY() - $$2.y + 0.15D, getZ() - $$2.z, 0.0D, 0.0D, 0.0D);
/*     */     }
/* 251 */     else if (this.finalTarget != null && !this.finalTarget.isRemoved()) {
/* 252 */       if (this.flightSteps > 0) {
/* 253 */         this.flightSteps--;
/* 254 */         if (this.flightSteps == 0) {
/* 255 */           selectNextMoveDirection((this.currentMoveDirection == null) ? null : this.currentMoveDirection.getAxis());
/*     */         }
/*     */       } 
/*     */       
/* 259 */       if (this.currentMoveDirection != null) {
/*     */         
/* 261 */         BlockPos $$3 = blockPosition();
/* 262 */         Direction.Axis $$4 = this.currentMoveDirection.getAxis();
/* 263 */         if (level().loadedAndEntityCanStandOn($$3.relative(this.currentMoveDirection), this)) {
/* 264 */           selectNextMoveDirection($$4);
/*     */         } else {
/* 266 */           BlockPos $$5 = this.finalTarget.blockPosition();
/* 267 */           if (($$4 == Direction.Axis.X && $$3.getX() == $$5.getX()) || ($$4 == Direction.Axis.Z && $$3
/* 268 */             .getZ() == $$5.getZ()) || ($$4 == Direction.Axis.Y && $$3
/* 269 */             .getY() == $$5.getY()))
/*     */           {
/* 271 */             selectNextMoveDirection($$4);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canHitEntity(Entity $$0) {
/* 281 */     return (super.canHitEntity($$0) && !$$0.noPhysics);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOnFire() {
/* 286 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldRenderAtSqrDistance(double $$0) {
/* 291 */     return ($$0 < 16384.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getLightLevelDependentMagicValue() {
/* 296 */     return 1.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onHitEntity(EntityHitResult $$0) {
/* 301 */     super.onHitEntity($$0);
/* 302 */     Entity $$1 = $$0.getEntity();
/* 303 */     Entity $$2 = getOwner();
/* 304 */     LivingEntity $$3 = ($$2 instanceof LivingEntity) ? (LivingEntity)$$2 : null;
/* 305 */     boolean $$4 = $$1.hurt(damageSources().mobProjectile(this, $$3), 4.0F);
/* 306 */     if ($$4) {
/* 307 */       doEnchantDamageEffects($$3, $$1);
/* 308 */       if ($$1 instanceof LivingEntity) { LivingEntity $$5 = (LivingEntity)$$1;
/* 309 */         $$5.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 200), (Entity)MoreObjects.firstNonNull($$2, this)); }
/*     */     
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onHitBlock(BlockHitResult $$0) {
/* 316 */     super.onHitBlock($$0);
/* 317 */     ((ServerLevel)level()).sendParticles((ParticleOptions)ParticleTypes.EXPLOSION, getX(), getY(), getZ(), 2, 0.2D, 0.2D, 0.2D, 0.0D);
/* 318 */     playSound(SoundEvents.SHULKER_BULLET_HIT, 1.0F, 1.0F);
/*     */   }
/*     */   
/*     */   private void destroy() {
/* 322 */     discard();
/* 323 */     level().gameEvent(GameEvent.ENTITY_DAMAGE, position(), GameEvent.Context.of(this));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onHit(HitResult $$0) {
/* 328 */     super.onHit($$0);
/* 329 */     destroy();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPickable() {
/* 334 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hurt(DamageSource $$0, float $$1) {
/* 339 */     if (!(level()).isClientSide) {
/* 340 */       playSound(SoundEvents.SHULKER_BULLET_HURT, 1.0F, 1.0F);
/* 341 */       ((ServerLevel)level()).sendParticles((ParticleOptions)ParticleTypes.CRIT, getX(), getY(), getZ(), 15, 0.2D, 0.2D, 0.2D, 0.0D);
/* 342 */       destroy();
/*     */     } 
/* 344 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void recreateFromPacket(ClientboundAddEntityPacket $$0) {
/* 349 */     super.recreateFromPacket($$0);
/*     */     
/* 351 */     double $$1 = $$0.getXa();
/* 352 */     double $$2 = $$0.getYa();
/* 353 */     double $$3 = $$0.getZa();
/*     */     
/* 355 */     setDeltaMovement($$1, $$2, $$3);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\projectile\ShulkerBullet.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */