/*     */ package net.minecraft.world.entity.projectile;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.ListTag;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.network.protocol.game.ClientGamePacketListener;
/*     */ import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.level.ClipContext;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.phys.HitResult;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public abstract class AbstractHurtingProjectile
/*     */   extends Projectile {
/*     */   public double xPower;
/*     */   public double yPower;
/*     */   public double zPower;
/*     */   
/*     */   protected AbstractHurtingProjectile(EntityType<? extends AbstractHurtingProjectile> $$0, Level $$1) {
/*  28 */     super((EntityType)$$0, $$1);
/*     */   }
/*     */   
/*     */   protected AbstractHurtingProjectile(EntityType<? extends AbstractHurtingProjectile> $$0, double $$1, double $$2, double $$3, Level $$4) {
/*  32 */     this($$0, $$4);
/*  33 */     setPos($$1, $$2, $$3);
/*     */   }
/*     */   
/*     */   public AbstractHurtingProjectile(EntityType<? extends AbstractHurtingProjectile> $$0, double $$1, double $$2, double $$3, double $$4, double $$5, double $$6, Level $$7) {
/*  37 */     this($$0, $$7);
/*     */     
/*  39 */     moveTo($$1, $$2, $$3, getYRot(), getXRot());
/*  40 */     reapplyPosition();
/*     */     
/*  42 */     double $$8 = Math.sqrt($$4 * $$4 + $$5 * $$5 + $$6 * $$6);
/*  43 */     if ($$8 != 0.0D) {
/*  44 */       this.xPower = $$4 / $$8 * 0.1D;
/*  45 */       this.yPower = $$5 / $$8 * 0.1D;
/*  46 */       this.zPower = $$6 / $$8 * 0.1D;
/*     */     } 
/*     */   }
/*     */   
/*     */   public AbstractHurtingProjectile(EntityType<? extends AbstractHurtingProjectile> $$0, LivingEntity $$1, double $$2, double $$3, double $$4, Level $$5) {
/*  51 */     this($$0, $$1.getX(), $$1.getY(), $$1.getZ(), $$2, $$3, $$4, $$5);
/*  52 */     setOwner((Entity)$$1);
/*  53 */     setRot($$1.getYRot(), $$1.getXRot());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {}
/*     */ 
/*     */   
/*     */   public boolean shouldRenderAtSqrDistance(double $$0) {
/*  62 */     double $$1 = getBoundingBox().getSize() * 4.0D;
/*  63 */     if (Double.isNaN($$1)) {
/*  64 */       $$1 = 4.0D;
/*     */     }
/*  66 */     $$1 *= 64.0D;
/*  67 */     return ($$0 < $$1 * $$1);
/*     */   }
/*     */   
/*     */   protected ClipContext.Block getClipType() {
/*  71 */     return ClipContext.Block.COLLIDER;
/*     */   }
/*     */   
/*     */   public void tick() {
/*     */     float $$9;
/*  76 */     Entity $$0 = getOwner();
/*  77 */     if (!(level()).isClientSide && (($$0 != null && $$0.isRemoved()) || !level().hasChunkAt(blockPosition()))) {
/*  78 */       discard();
/*     */       
/*     */       return;
/*     */     } 
/*  82 */     super.tick();
/*  83 */     if (shouldBurn()) {
/*  84 */       setSecondsOnFire(1);
/*     */     }
/*     */     
/*  87 */     HitResult $$1 = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity, getClipType());
/*  88 */     if ($$1.getType() != HitResult.Type.MISS) {
/*  89 */       onHit($$1);
/*     */     }
/*     */     
/*  92 */     checkInsideBlocks();
/*  93 */     Vec3 $$2 = getDeltaMovement();
/*  94 */     double $$3 = getX() + $$2.x;
/*  95 */     double $$4 = getY() + $$2.y;
/*  96 */     double $$5 = getZ() + $$2.z;
/*     */     
/*  98 */     ProjectileUtil.rotateTowardsMovement(this, 0.2F);
/*     */ 
/*     */     
/* 101 */     if (isInWater()) {
/* 102 */       for (int $$6 = 0; $$6 < 4; $$6++) {
/* 103 */         float $$7 = 0.25F;
/* 104 */         level().addParticle((ParticleOptions)ParticleTypes.BUBBLE, $$3 - $$2.x * 0.25D, $$4 - $$2.y * 0.25D, $$5 - $$2.z * 0.25D, $$2.x, $$2.y, $$2.z);
/*     */       } 
/* 106 */       float $$8 = getLiquidInertia();
/*     */     } else {
/* 108 */       $$9 = getInertia();
/*     */     } 
/*     */     
/* 111 */     setDeltaMovement($$2.add(this.xPower, this.yPower, this.zPower).scale($$9));
/*     */     
/* 113 */     ParticleOptions $$10 = getTrailParticle();
/* 114 */     if ($$10 != null) {
/* 115 */       level().addParticle($$10, $$3, $$4 + 0.5D, $$5, 0.0D, 0.0D, 0.0D);
/*     */     }
/*     */     
/* 118 */     setPos($$3, $$4, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canHitEntity(Entity $$0) {
/* 123 */     return (super.canHitEntity($$0) && !$$0.noPhysics);
/*     */   }
/*     */   
/*     */   protected boolean shouldBurn() {
/* 127 */     return true;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected ParticleOptions getTrailParticle() {
/* 132 */     return (ParticleOptions)ParticleTypes.SMOKE;
/*     */   }
/*     */   
/*     */   protected float getInertia() {
/* 136 */     return 0.95F;
/*     */   }
/*     */   
/*     */   protected float getLiquidInertia() {
/* 140 */     return 0.8F;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/* 145 */     super.addAdditionalSaveData($$0);
/* 146 */     $$0.put("power", (Tag)newDoubleList(new double[] { this.xPower, this.yPower, this.zPower }));
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/* 151 */     super.readAdditionalSaveData($$0);
/* 152 */     if ($$0.contains("power", 9)) {
/* 153 */       ListTag $$1 = $$0.getList("power", 6);
/* 154 */       if ($$1.size() == 3) {
/* 155 */         this.xPower = $$1.getDouble(0);
/* 156 */         this.yPower = $$1.getDouble(1);
/* 157 */         this.zPower = $$1.getDouble(2);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPickable() {
/* 164 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getPickRadius() {
/* 169 */     return 1.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hurt(DamageSource $$0, float $$1) {
/* 174 */     if (isInvulnerableTo($$0)) {
/* 175 */       return false;
/*     */     }
/* 177 */     markHurt();
/*     */     
/* 179 */     Entity $$2 = $$0.getEntity();
/* 180 */     if ($$2 != null) {
/* 181 */       if (!(level()).isClientSide) {
/* 182 */         Vec3 $$3 = $$2.getLookAngle();
/* 183 */         setDeltaMovement($$3);
/* 184 */         this.xPower = $$3.x * 0.1D;
/* 185 */         this.yPower = $$3.y * 0.1D;
/* 186 */         this.zPower = $$3.z * 0.1D;
/*     */         
/* 188 */         setOwner($$2);
/*     */       } 
/* 190 */       return true;
/*     */     } 
/* 192 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getLightLevelDependentMagicValue() {
/* 197 */     return 1.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public Packet<ClientGamePacketListener> getAddEntityPacket() {
/* 202 */     Entity $$0 = getOwner();
/* 203 */     int $$1 = ($$0 == null) ? 0 : $$0.getId();
/* 204 */     return (Packet<ClientGamePacketListener>)new ClientboundAddEntityPacket(getId(), getUUID(), getX(), getY(), getZ(), getXRot(), getYRot(), getType(), $$1, new Vec3(this.xPower, this.yPower, this.zPower), 0.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public void recreateFromPacket(ClientboundAddEntityPacket $$0) {
/* 209 */     super.recreateFromPacket($$0);
/*     */     
/* 211 */     double $$1 = $$0.getXa();
/* 212 */     double $$2 = $$0.getYa();
/* 213 */     double $$3 = $$0.getZa();
/*     */     
/* 215 */     double $$4 = Math.sqrt($$1 * $$1 + $$2 * $$2 + $$3 * $$3);
/* 216 */     if ($$4 != 0.0D) {
/* 217 */       this.xPower = $$1 / $$4 * 0.1D;
/* 218 */       this.yPower = $$2 / $$4 * 0.1D;
/* 219 */       this.zPower = $$3 / $$4 * 0.1D;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\projectile\AbstractHurtingProjectile.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */