/*     */ package net.minecraft.world.entity.projectile;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityDimensions;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Pose;
/*     */ import net.minecraft.world.entity.monster.breeze.Breeze;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.ClipContext;
/*     */ import net.minecraft.world.level.Explosion;
/*     */ import net.minecraft.world.level.ExplosionDamageCalculator;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ import net.minecraft.world.phys.EntityHitResult;
/*     */ import net.minecraft.world.phys.HitResult;
/*     */ 
/*     */ public class WindCharge
/*     */   extends AbstractHurtingProjectile
/*     */   implements ItemSupplier {
/*     */   public static final class WindChargeExplosionDamageCalculator
/*     */     extends ExplosionDamageCalculator {
/*     */     public boolean shouldDamageEntity(Explosion $$0, Entity $$1) {
/*  29 */       return false;
/*     */     }
/*     */   }
/*     */   
/*  33 */   public static final WindChargeExplosionDamageCalculator EXPLOSION_DAMAGE_CALCULATOR = new WindChargeExplosionDamageCalculator();
/*     */   
/*     */   public WindCharge(EntityType<? extends WindCharge> $$0, Level $$1) {
/*  36 */     super((EntityType)$$0, $$1);
/*     */   }
/*     */   
/*     */   public WindCharge(EntityType<? extends WindCharge> $$0, Breeze $$1, Level $$2) {
/*  40 */     super((EntityType)$$0, $$1.getX(), $$1.getSnoutYPosition(), $$1.getZ(), $$2);
/*  41 */     setOwner((Entity)$$1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected AABB makeBoundingBox() {
/*  46 */     float $$0 = (getType().getDimensions()).width / 2.0F;
/*  47 */     float $$1 = (getType().getDimensions()).height;
/*  48 */     float $$2 = 0.15F;
/*     */     
/*  50 */     return new AABB((position()).x - $$0, (position()).y - 0.15000000596046448D, (position()).z - $$0, (position()).x + $$0, (position()).y - 0.15000000596046448D + $$1, (position()).z + $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getEyeHeight(Pose $$0, EntityDimensions $$1) {
/*  55 */     return 0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canCollideWith(Entity $$0) {
/*  60 */     if ($$0 instanceof WindCharge) {
/*  61 */       return false;
/*     */     }
/*  63 */     return super.canCollideWith($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canHitEntity(Entity $$0) {
/*  68 */     if ($$0 instanceof WindCharge) {
/*  69 */       return false;
/*     */     }
/*     */     
/*  72 */     return super.canHitEntity($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onHitEntity(EntityHitResult $$0) {
/*  77 */     super.onHitEntity($$0);
/*  78 */     if ((level()).isClientSide) {
/*     */       return;
/*     */     }
/*     */     
/*  82 */     Entity entity = getOwner(); LivingEntity $$1 = (LivingEntity)entity; $$0.getEntity().hurt(damageSources().mobProjectile(this, (entity instanceof LivingEntity) ? $$1 : null), 1.0F);
/*  83 */     explode();
/*     */   }
/*     */   
/*     */   private void explode() {
/*  87 */     level().explode(this, null, EXPLOSION_DAMAGE_CALCULATOR, getX(), getY(), getZ(), (float)(3.0D + this.random.nextDouble()), false, Level.ExplosionInteraction.BLOW, (ParticleOptions)ParticleTypes.GUST, (ParticleOptions)ParticleTypes.GUST_EMITTER, SoundEvents.WIND_BURST);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onHitBlock(BlockHitResult $$0) {
/*  92 */     super.onHitBlock($$0);
/*     */     
/*  94 */     explode();
/*  95 */     discard();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onHit(HitResult $$0) {
/* 100 */     super.onHit($$0);
/* 101 */     if (!(level()).isClientSide) {
/* 102 */       discard();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean shouldBurn() {
/* 108 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getItem() {
/* 114 */     return ItemStack.EMPTY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float getInertia() {
/* 121 */     return 1.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getLiquidInertia() {
/* 126 */     return getInertia();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected ParticleOptions getTrailParticle() {
/* 132 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected ClipContext.Block getClipType() {
/* 137 */     return ClipContext.Block.OUTLINE;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\projectile\WindCharge.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */