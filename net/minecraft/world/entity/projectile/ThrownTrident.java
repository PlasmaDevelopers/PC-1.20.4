/*     */ package net.minecraft.world.entity.projectile;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.syncher.EntityDataAccessor;
/*     */ import net.minecraft.network.syncher.EntityDataSerializers;
/*     */ import net.minecraft.network.syncher.SynchedEntityData;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.tags.EntityTypeTags;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LightningBolt;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.item.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.phys.EntityHitResult;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class ThrownTrident
/*     */   extends AbstractArrow {
/*  30 */   private static final EntityDataAccessor<Byte> ID_LOYALTY = SynchedEntityData.defineId(ThrownTrident.class, EntityDataSerializers.BYTE);
/*  31 */   private static final EntityDataAccessor<Boolean> ID_FOIL = SynchedEntityData.defineId(ThrownTrident.class, EntityDataSerializers.BOOLEAN);
/*  32 */   private static final ItemStack DEFAULT_ARROW_STACK = new ItemStack((ItemLike)Items.TRIDENT);
/*     */   
/*     */   private boolean dealtDamage;
/*     */   
/*     */   public int clientSideReturnTridentTickCount;
/*     */   
/*     */   public ThrownTrident(EntityType<? extends ThrownTrident> $$0, Level $$1) {
/*  39 */     super((EntityType)$$0, $$1, DEFAULT_ARROW_STACK);
/*     */   }
/*     */   
/*     */   public ThrownTrident(Level $$0, LivingEntity $$1, ItemStack $$2) {
/*  43 */     super(EntityType.TRIDENT, $$1, $$0, $$2);
/*  44 */     this.entityData.set(ID_LOYALTY, Byte.valueOf((byte)EnchantmentHelper.getLoyalty($$2)));
/*  45 */     this.entityData.set(ID_FOIL, Boolean.valueOf($$2.hasFoil()));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {
/*  50 */     super.defineSynchedData();
/*     */     
/*  52 */     this.entityData.define(ID_LOYALTY, Byte.valueOf((byte)0));
/*  53 */     this.entityData.define(ID_FOIL, Boolean.valueOf(false));
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/*  58 */     if (this.inGroundTime > 4) {
/*  59 */       this.dealtDamage = true;
/*     */     }
/*     */     
/*  62 */     Entity $$0 = getOwner();
/*  63 */     int $$1 = ((Byte)this.entityData.get(ID_LOYALTY)).byteValue();
/*     */     
/*  65 */     if ($$1 > 0 && (this.dealtDamage || isNoPhysics()) && $$0 != null) {
/*  66 */       if (!isAcceptibleReturnOwner()) {
/*  67 */         if (!(level()).isClientSide && this.pickup == AbstractArrow.Pickup.ALLOWED) {
/*  68 */           spawnAtLocation(getPickupItem(), 0.1F);
/*     */         }
/*  70 */         discard();
/*     */       } else {
/*  72 */         setNoPhysics(true);
/*  73 */         Vec3 $$2 = $$0.getEyePosition().subtract(position());
/*  74 */         setPosRaw(getX(), getY() + $$2.y * 0.015D * $$1, getZ());
/*  75 */         if ((level()).isClientSide) {
/*  76 */           this.yOld = getY();
/*     */         }
/*  78 */         double $$3 = 0.05D * $$1;
/*  79 */         setDeltaMovement(getDeltaMovement().scale(0.95D).add($$2.normalize().scale($$3)));
/*     */         
/*  81 */         if (this.clientSideReturnTridentTickCount == 0) {
/*  82 */           playSound(SoundEvents.TRIDENT_RETURN, 10.0F, 1.0F);
/*     */         }
/*     */         
/*  85 */         this.clientSideReturnTridentTickCount++;
/*     */       } 
/*     */     }
/*     */     
/*  89 */     super.tick();
/*     */   }
/*     */   
/*     */   private boolean isAcceptibleReturnOwner() {
/*  93 */     Entity $$0 = getOwner();
/*  94 */     if ($$0 == null || !$$0.isAlive()) {
/*  95 */       return false;
/*     */     }
/*  97 */     if ($$0 instanceof ServerPlayer && $$0.isSpectator()) {
/*  98 */       return false;
/*     */     }
/* 100 */     return true;
/*     */   }
/*     */   
/*     */   public boolean isFoil() {
/* 104 */     return ((Boolean)this.entityData.get(ID_FOIL)).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected EntityHitResult findHitEntity(Vec3 $$0, Vec3 $$1) {
/* 110 */     if (this.dealtDamage) {
/* 111 */       return null;
/*     */     }
/* 113 */     return super.findHitEntity($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onHitEntity(EntityHitResult $$0) {
/* 118 */     Entity $$1 = $$0.getEntity();
/* 119 */     float $$2 = 8.0F;
/*     */     
/* 121 */     if ($$1 instanceof LivingEntity) {
/* 122 */       LivingEntity $$3 = (LivingEntity)$$1;
/* 123 */       $$2 += EnchantmentHelper.getDamageBonus(getPickupItemStackOrigin(), $$3.getMobType());
/*     */     } 
/*     */     
/* 126 */     Entity $$4 = getOwner();
/* 127 */     DamageSource $$5 = damageSources().trident(this, ($$4 == null) ? this : $$4);
/*     */     
/* 129 */     this.dealtDamage = true;
/* 130 */     SoundEvent $$6 = SoundEvents.TRIDENT_HIT;
/*     */     
/* 132 */     if ($$1.hurt($$5, $$2)) {
/* 133 */       if ($$1.getType() == EntityType.ENDERMAN) {
/*     */         return;
/*     */       }
/* 136 */       if ($$1 instanceof LivingEntity) { LivingEntity $$7 = (LivingEntity)$$1;
/* 137 */         if ($$4 instanceof LivingEntity) {
/* 138 */           EnchantmentHelper.doPostHurtEffects($$7, $$4);
/* 139 */           EnchantmentHelper.doPostDamageEffects((LivingEntity)$$4, (Entity)$$7);
/*     */         } 
/* 141 */         doPostHurtEffects($$7); }
/*     */     
/* 143 */     } else if ($$1.getType().is(EntityTypeTags.DEFLECTS_TRIDENTS)) {
/* 144 */       deflect();
/*     */       
/*     */       return;
/*     */     } 
/* 148 */     setDeltaMovement(getDeltaMovement().multiply(-0.01D, -0.1D, -0.01D));
/*     */     
/* 150 */     float $$8 = 1.0F;
/* 151 */     if (level() instanceof net.minecraft.server.level.ServerLevel && level().isThundering() && isChanneling()) {
/* 152 */       BlockPos $$9 = $$1.blockPosition();
/* 153 */       if (level().canSeeSky($$9)) {
/* 154 */         LightningBolt $$10 = (LightningBolt)EntityType.LIGHTNING_BOLT.create(level());
/* 155 */         if ($$10 != null) {
/* 156 */           $$10.moveTo(Vec3.atBottomCenterOf((Vec3i)$$9));
/* 157 */           $$10.setCause(($$4 instanceof ServerPlayer) ? (ServerPlayer)$$4 : null);
/* 158 */           level().addFreshEntity((Entity)$$10);
/* 159 */           $$6 = SoundEvents.TRIDENT_THUNDER;
/* 160 */           $$8 = 5.0F;
/*     */         } 
/*     */       } 
/*     */     } 
/* 164 */     playSound($$6, $$8, 1.0F);
/*     */   }
/*     */   
/*     */   public boolean isChanneling() {
/* 168 */     return EnchantmentHelper.hasChanneling(getPickupItemStackOrigin());
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean tryPickup(Player $$0) {
/* 173 */     return (super.tryPickup($$0) || (isNoPhysics() && ownedBy((Entity)$$0) && $$0.getInventory().add(getPickupItem())));
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDefaultHitGroundSoundEvent() {
/* 178 */     return SoundEvents.TRIDENT_HIT_GROUND;
/*     */   }
/*     */ 
/*     */   
/*     */   public void playerTouch(Player $$0) {
/* 183 */     if (ownedBy((Entity)$$0) || getOwner() == null) {
/* 184 */       super.playerTouch($$0);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/* 190 */     super.readAdditionalSaveData($$0);
/*     */     
/* 192 */     this.dealtDamage = $$0.getBoolean("DealtDamage");
/*     */     
/* 194 */     this.entityData.set(ID_LOYALTY, Byte.valueOf((byte)EnchantmentHelper.getLoyalty(getPickupItemStackOrigin())));
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/* 199 */     super.addAdditionalSaveData($$0);
/*     */     
/* 201 */     $$0.putBoolean("DealtDamage", this.dealtDamage);
/*     */   }
/*     */ 
/*     */   
/*     */   public void tickDespawn() {
/* 206 */     int $$0 = ((Byte)this.entityData.get(ID_LOYALTY)).byteValue();
/*     */     
/* 208 */     if (this.pickup != AbstractArrow.Pickup.ALLOWED || $$0 <= 0) {
/* 209 */       super.tickDespawn();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getWaterInertia() {
/* 215 */     return 0.99F;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldRender(double $$0, double $$1, double $$2) {
/* 220 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\projectile\ThrownTrident.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */