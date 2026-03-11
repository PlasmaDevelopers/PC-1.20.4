/*     */ package net.minecraft.world.entity.projectile;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.OptionalInt;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.ListTag;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.network.syncher.EntityDataAccessor;
/*     */ import net.minecraft.network.syncher.EntityDataSerializers;
/*     */ import net.minecraft.network.syncher.SynchedEntityData;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.MoverType;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.ClipContext;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ import net.minecraft.world.phys.EntityHitResult;
/*     */ import net.minecraft.world.phys.HitResult;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class FireworkRocketEntity
/*     */   extends Projectile
/*     */   implements ItemSupplier {
/*  36 */   private static final EntityDataAccessor<ItemStack> DATA_ID_FIREWORKS_ITEM = SynchedEntityData.defineId(FireworkRocketEntity.class, EntityDataSerializers.ITEM_STACK);
/*  37 */   private static final EntityDataAccessor<OptionalInt> DATA_ATTACHED_TO_TARGET = SynchedEntityData.defineId(FireworkRocketEntity.class, EntityDataSerializers.OPTIONAL_UNSIGNED_INT);
/*  38 */   private static final EntityDataAccessor<Boolean> DATA_SHOT_AT_ANGLE = SynchedEntityData.defineId(FireworkRocketEntity.class, EntityDataSerializers.BOOLEAN);
/*     */   
/*     */   private int life;
/*     */   private int lifetime;
/*     */   @Nullable
/*     */   private LivingEntity attachedToEntity;
/*     */   
/*     */   public FireworkRocketEntity(EntityType<? extends FireworkRocketEntity> $$0, Level $$1) {
/*  46 */     super((EntityType)$$0, $$1);
/*     */   }
/*     */   
/*     */   public FireworkRocketEntity(Level $$0, double $$1, double $$2, double $$3, ItemStack $$4) {
/*  50 */     super(EntityType.FIREWORK_ROCKET, $$0);
/*  51 */     this.life = 0;
/*     */     
/*  53 */     setPos($$1, $$2, $$3);
/*     */     
/*  55 */     int $$5 = 1;
/*  56 */     if (!$$4.isEmpty() && $$4.hasTag()) {
/*  57 */       this.entityData.set(DATA_ID_FIREWORKS_ITEM, $$4.copy());
/*     */       
/*  59 */       $$5 += $$4.getOrCreateTagElement("Fireworks").getByte("Flight");
/*     */     } 
/*  61 */     setDeltaMovement(this.random
/*  62 */         .triangle(0.0D, 0.002297D), 0.05D, this.random
/*     */         
/*  64 */         .triangle(0.0D, 0.002297D));
/*     */ 
/*     */     
/*  67 */     this.lifetime = 10 * $$5 + this.random.nextInt(6) + this.random.nextInt(7);
/*     */   }
/*     */   
/*     */   public FireworkRocketEntity(Level $$0, @Nullable Entity $$1, double $$2, double $$3, double $$4, ItemStack $$5) {
/*  71 */     this($$0, $$2, $$3, $$4, $$5);
/*  72 */     setOwner($$1);
/*     */   }
/*     */   
/*     */   public FireworkRocketEntity(Level $$0, ItemStack $$1, LivingEntity $$2) {
/*  76 */     this($$0, (Entity)$$2, $$2.getX(), $$2.getY(), $$2.getZ(), $$1);
/*  77 */     this.entityData.set(DATA_ATTACHED_TO_TARGET, OptionalInt.of($$2.getId()));
/*  78 */     this.attachedToEntity = $$2;
/*     */   }
/*     */   
/*     */   public FireworkRocketEntity(Level $$0, ItemStack $$1, double $$2, double $$3, double $$4, boolean $$5) {
/*  82 */     this($$0, $$2, $$3, $$4, $$1);
/*  83 */     this.entityData.set(DATA_SHOT_AT_ANGLE, Boolean.valueOf($$5));
/*     */   }
/*     */   
/*     */   public FireworkRocketEntity(Level $$0, ItemStack $$1, Entity $$2, double $$3, double $$4, double $$5, boolean $$6) {
/*  87 */     this($$0, $$1, $$3, $$4, $$5, $$6);
/*  88 */     setOwner($$2);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {
/*  93 */     this.entityData.define(DATA_ID_FIREWORKS_ITEM, ItemStack.EMPTY);
/*  94 */     this.entityData.define(DATA_ATTACHED_TO_TARGET, OptionalInt.empty());
/*  95 */     this.entityData.define(DATA_SHOT_AT_ANGLE, Boolean.valueOf(false));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldRenderAtSqrDistance(double $$0) {
/* 100 */     return ($$0 < 4096.0D && !isAttachedToEntity());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldRender(double $$0, double $$1, double $$2) {
/* 105 */     return (super.shouldRender($$0, $$1, $$2) && !isAttachedToEntity());
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 110 */     super.tick();
/*     */     
/* 112 */     if (isAttachedToEntity()) {
/* 113 */       if (this.attachedToEntity == null) {
/* 114 */         ((OptionalInt)this.entityData.get(DATA_ATTACHED_TO_TARGET)).ifPresent($$0 -> {
/*     */               Entity $$1 = level().getEntity($$0);
/*     */               if ($$1 instanceof LivingEntity) {
/*     */                 this.attachedToEntity = (LivingEntity)$$1;
/*     */               }
/*     */             });
/*     */       }
/* 121 */       if (this.attachedToEntity != null) {
/*     */         Vec3 $$5;
/* 123 */         if (this.attachedToEntity.isFallFlying()) {
/* 124 */           Vec3 $$0 = this.attachedToEntity.getLookAngle();
/* 125 */           double $$1 = 1.5D;
/* 126 */           double $$2 = 0.1D;
/*     */           
/* 128 */           Vec3 $$3 = this.attachedToEntity.getDeltaMovement();
/* 129 */           this.attachedToEntity.setDeltaMovement($$3.add($$0.x * 0.1D + ($$0.x * 1.5D - $$3.x) * 0.5D, $$0.y * 0.1D + ($$0.y * 1.5D - $$3.y) * 0.5D, $$0.z * 0.1D + ($$0.z * 1.5D - $$3.z) * 0.5D));
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 134 */           Vec3 $$4 = this.attachedToEntity.getHandHoldingItemAngle(Items.FIREWORK_ROCKET);
/*     */         } else {
/* 136 */           $$5 = Vec3.ZERO;
/*     */         } 
/* 138 */         setPos(this.attachedToEntity.getX() + $$5.x, this.attachedToEntity.getY() + $$5.y, this.attachedToEntity.getZ() + $$5.z);
/*     */         
/* 140 */         setDeltaMovement(this.attachedToEntity.getDeltaMovement());
/*     */       } 
/*     */     } else {
/* 143 */       if (!isShotAtAngle()) {
/*     */         
/* 145 */         double $$6 = this.horizontalCollision ? 1.0D : 1.15D;
/* 146 */         setDeltaMovement(getDeltaMovement().multiply($$6, 1.0D, $$6).add(0.0D, 0.04D, 0.0D));
/*     */       } 
/* 148 */       Vec3 $$7 = getDeltaMovement();
/* 149 */       move(MoverType.SELF, $$7);
/* 150 */       setDeltaMovement($$7);
/*     */     } 
/*     */     
/* 153 */     HitResult $$8 = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
/*     */     
/* 155 */     if (!this.noPhysics) {
/* 156 */       onHit($$8);
/* 157 */       this.hasImpulse = true;
/*     */     } 
/*     */     
/* 160 */     updateRotation();
/*     */     
/* 162 */     if (this.life == 0 && !isSilent()) {
/* 163 */       level().playSound(null, getX(), getY(), getZ(), SoundEvents.FIREWORK_ROCKET_LAUNCH, SoundSource.AMBIENT, 3.0F, 1.0F);
/*     */     }
/*     */     
/* 166 */     this.life++;
/* 167 */     if ((level()).isClientSide && this.life % 2 < 2) {
/* 168 */       level().addParticle((ParticleOptions)ParticleTypes.FIREWORK, getX(), getY(), getZ(), this.random.nextGaussian() * 0.05D, -(getDeltaMovement()).y * 0.5D, this.random.nextGaussian() * 0.05D);
/*     */     }
/* 170 */     if (!(level()).isClientSide && this.life > this.lifetime) {
/* 171 */       explode();
/*     */     }
/*     */   }
/*     */   
/*     */   private void explode() {
/* 176 */     level().broadcastEntityEvent(this, (byte)17);
/* 177 */     gameEvent(GameEvent.EXPLODE, getOwner());
/* 178 */     dealExplosionDamage();
/* 179 */     discard();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onHitEntity(EntityHitResult $$0) {
/* 184 */     super.onHitEntity($$0);
/* 185 */     if ((level()).isClientSide) {
/*     */       return;
/*     */     }
/* 188 */     explode();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onHitBlock(BlockHitResult $$0) {
/* 193 */     BlockPos $$1 = new BlockPos((Vec3i)$$0.getBlockPos());
/* 194 */     level().getBlockState($$1).entityInside(level(), $$1, this);
/* 195 */     if (!level().isClientSide() && hasExplosion()) {
/* 196 */       explode();
/*     */     }
/* 198 */     super.onHitBlock($$0);
/*     */   }
/*     */   
/*     */   private boolean hasExplosion() {
/* 202 */     ItemStack $$0 = (ItemStack)this.entityData.get(DATA_ID_FIREWORKS_ITEM);
/* 203 */     CompoundTag $$1 = $$0.isEmpty() ? null : $$0.getTagElement("Fireworks");
/* 204 */     ListTag $$2 = ($$1 != null) ? $$1.getList("Explosions", 10) : null;
/* 205 */     return ($$2 != null && !$$2.isEmpty());
/*     */   }
/*     */ 
/*     */   
/*     */   private void dealExplosionDamage() {
/* 210 */     float $$0 = 0.0F;
/* 211 */     ItemStack $$1 = (ItemStack)this.entityData.get(DATA_ID_FIREWORKS_ITEM);
/* 212 */     CompoundTag $$2 = $$1.isEmpty() ? null : $$1.getTagElement("Fireworks");
/* 213 */     ListTag $$3 = ($$2 != null) ? $$2.getList("Explosions", 10) : null;
/* 214 */     if ($$3 != null && !$$3.isEmpty()) {
/* 215 */       $$0 = 5.0F + ($$3.size() * 2);
/*     */     }
/* 217 */     if ($$0 > 0.0F) {
/* 218 */       if (this.attachedToEntity != null) {
/* 219 */         this.attachedToEntity.hurt(damageSources().fireworks(this, getOwner()), 5.0F + ($$3.size() * 2));
/*     */       }
/*     */       
/* 222 */       double $$4 = 5.0D;
/* 223 */       Vec3 $$5 = position();
/* 224 */       List<LivingEntity> $$6 = level().getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(5.0D));
/* 225 */       for (LivingEntity $$7 : $$6) {
/* 226 */         if ($$7 == this.attachedToEntity) {
/*     */           continue;
/*     */         }
/* 229 */         if (distanceToSqr((Entity)$$7) > 25.0D) {
/*     */           continue;
/*     */         }
/*     */         
/* 233 */         boolean $$8 = false;
/* 234 */         for (int $$9 = 0; $$9 < 2; $$9++) {
/* 235 */           Vec3 $$10 = new Vec3($$7.getX(), $$7.getY(0.5D * $$9), $$7.getZ());
/* 236 */           BlockHitResult blockHitResult = level().clip(new ClipContext($$5, $$10, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
/* 237 */           if (blockHitResult.getType() == HitResult.Type.MISS) {
/* 238 */             $$8 = true;
/*     */             break;
/*     */           } 
/*     */         } 
/* 242 */         if ($$8) {
/* 243 */           float $$12 = $$0 * (float)Math.sqrt((5.0D - distanceTo((Entity)$$7)) / 5.0D);
/* 244 */           $$7.hurt(damageSources().fireworks(this, getOwner()), $$12);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isAttachedToEntity() {
/* 251 */     return ((OptionalInt)this.entityData.get(DATA_ATTACHED_TO_TARGET)).isPresent();
/*     */   }
/*     */   
/*     */   public boolean isShotAtAngle() {
/* 255 */     return ((Boolean)this.entityData.get(DATA_SHOT_AT_ANGLE)).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleEntityEvent(byte $$0) {
/* 260 */     if ($$0 == 17 && (level()).isClientSide)
/*     */     {
/* 262 */       if (!hasExplosion()) {
/* 263 */         for (int $$1 = 0; $$1 < this.random.nextInt(3) + 2; $$1++) {
/* 264 */           level().addParticle((ParticleOptions)ParticleTypes.POOF, getX(), getY(), getZ(), this.random.nextGaussian() * 0.05D, 0.005D, this.random.nextGaussian() * 0.05D);
/*     */         }
/*     */       } else {
/* 267 */         ItemStack $$2 = (ItemStack)this.entityData.get(DATA_ID_FIREWORKS_ITEM);
/* 268 */         CompoundTag $$3 = $$2.isEmpty() ? null : $$2.getTagElement("Fireworks");
/* 269 */         Vec3 $$4 = getDeltaMovement();
/* 270 */         level().createFireworks(getX(), getY(), getZ(), $$4.x, $$4.y, $$4.z, $$3);
/*     */       } 
/*     */     }
/* 273 */     super.handleEntityEvent($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/* 278 */     super.addAdditionalSaveData($$0);
/* 279 */     $$0.putInt("Life", this.life);
/* 280 */     $$0.putInt("LifeTime", this.lifetime);
/* 281 */     ItemStack $$1 = (ItemStack)this.entityData.get(DATA_ID_FIREWORKS_ITEM);
/* 282 */     if (!$$1.isEmpty()) {
/* 283 */       $$0.put("FireworksItem", (Tag)$$1.save(new CompoundTag()));
/*     */     }
/* 285 */     $$0.putBoolean("ShotAtAngle", ((Boolean)this.entityData.get(DATA_SHOT_AT_ANGLE)).booleanValue());
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/* 290 */     super.readAdditionalSaveData($$0);
/* 291 */     this.life = $$0.getInt("Life");
/* 292 */     this.lifetime = $$0.getInt("LifeTime");
/*     */     
/* 294 */     ItemStack $$1 = ItemStack.of($$0.getCompound("FireworksItem"));
/* 295 */     if (!$$1.isEmpty()) {
/* 296 */       this.entityData.set(DATA_ID_FIREWORKS_ITEM, $$1);
/*     */     }
/*     */     
/* 299 */     if ($$0.contains("ShotAtAngle")) {
/* 300 */       this.entityData.set(DATA_SHOT_AT_ANGLE, Boolean.valueOf($$0.getBoolean("ShotAtAngle")));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getItem() {
/* 306 */     ItemStack $$0 = (ItemStack)this.entityData.get(DATA_ID_FIREWORKS_ITEM);
/* 307 */     return $$0.isEmpty() ? new ItemStack((ItemLike)Items.FIREWORK_ROCKET) : $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAttackable() {
/* 312 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\projectile\FireworkRocketEntity.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */