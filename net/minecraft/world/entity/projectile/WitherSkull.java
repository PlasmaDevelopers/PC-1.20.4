/*     */ package net.minecraft.world.entity.projectile;
/*     */ 
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.syncher.EntityDataAccessor;
/*     */ import net.minecraft.network.syncher.EntityDataSerializers;
/*     */ import net.minecraft.network.syncher.SynchedEntityData;
/*     */ import net.minecraft.world.Difficulty;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.effect.MobEffectInstance;
/*     */ import net.minecraft.world.effect.MobEffects;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.boss.wither.WitherBoss;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Explosion;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ import net.minecraft.world.phys.EntityHitResult;
/*     */ import net.minecraft.world.phys.HitResult;
/*     */ 
/*     */ 
/*     */ public class WitherSkull
/*     */   extends AbstractHurtingProjectile
/*     */ {
/*  28 */   private static final EntityDataAccessor<Boolean> DATA_DANGEROUS = SynchedEntityData.defineId(WitherSkull.class, EntityDataSerializers.BOOLEAN);
/*     */   
/*     */   public WitherSkull(EntityType<? extends WitherSkull> $$0, Level $$1) {
/*  31 */     super((EntityType)$$0, $$1);
/*     */   }
/*     */   
/*     */   public WitherSkull(Level $$0, LivingEntity $$1, double $$2, double $$3, double $$4) {
/*  35 */     super(EntityType.WITHER_SKULL, $$1, $$2, $$3, $$4, $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getInertia() {
/*  40 */     return isDangerous() ? 0.73F : super.getInertia();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOnFire() {
/*  45 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getBlockExplosionResistance(Explosion $$0, BlockGetter $$1, BlockPos $$2, BlockState $$3, FluidState $$4, float $$5) {
/*  50 */     if (isDangerous() && WitherBoss.canDestroy($$3)) {
/*  51 */       return Math.min(0.8F, $$5);
/*     */     }
/*     */     
/*  54 */     return $$5;
/*     */   }
/*     */   
/*     */   protected void onHitEntity(EntityHitResult $$0) {
/*     */     boolean $$5;
/*  59 */     super.onHitEntity($$0);
/*  60 */     if ((level()).isClientSide) {
/*     */       return;
/*     */     }
/*  63 */     Entity $$1 = $$0.getEntity();
/*  64 */     Entity $$2 = getOwner();
/*     */     
/*  66 */     if ($$2 instanceof LivingEntity) { LivingEntity $$3 = (LivingEntity)$$2;
/*  67 */       boolean $$4 = $$1.hurt(damageSources().witherSkull(this, (Entity)$$3), 8.0F);
/*  68 */       if ($$4) {
/*  69 */         if ($$1.isAlive()) {
/*  70 */           doEnchantDamageEffects($$3, $$1);
/*     */         } else {
/*  72 */           $$3.heal(5.0F);
/*     */         } 
/*     */       } }
/*     */     else
/*  76 */     { $$5 = $$1.hurt(damageSources().magic(), 5.0F); }
/*     */     
/*  78 */     if ($$5 && $$1 instanceof LivingEntity) { LivingEntity $$6 = (LivingEntity)$$1;
/*  79 */       int $$7 = 0;
/*  80 */       if (level().getDifficulty() == Difficulty.NORMAL) {
/*  81 */         $$7 = 10;
/*  82 */       } else if (level().getDifficulty() == Difficulty.HARD) {
/*  83 */         $$7 = 40;
/*     */       } 
/*  85 */       if ($$7 > 0) {
/*  86 */         $$6.addEffect(new MobEffectInstance(MobEffects.WITHER, 20 * $$7, 1), getEffectSource());
/*     */       } }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onHit(HitResult $$0) {
/*  93 */     super.onHit($$0);
/*  94 */     if (!(level()).isClientSide) {
/*  95 */       level().explode(this, getX(), getY(), getZ(), 1.0F, false, Level.ExplosionInteraction.MOB);
/*  96 */       discard();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPickable() {
/* 102 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hurt(DamageSource $$0, float $$1) {
/* 107 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {
/* 112 */     this.entityData.define(DATA_DANGEROUS, Boolean.valueOf(false));
/*     */   }
/*     */   
/*     */   public boolean isDangerous() {
/* 116 */     return ((Boolean)this.entityData.get(DATA_DANGEROUS)).booleanValue();
/*     */   }
/*     */   
/*     */   public void setDangerous(boolean $$0) {
/* 120 */     this.entityData.set(DATA_DANGEROUS, Boolean.valueOf($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean shouldBurn() {
/* 125 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/* 130 */     super.addAdditionalSaveData($$0);
/* 131 */     $$0.putBoolean("dangerous", isDangerous());
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/* 136 */     super.readAdditionalSaveData($$0);
/* 137 */     setDangerous($$0.getBoolean("dangerous"));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\projectile\WitherSkull.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */