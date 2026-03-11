/*    */ package net.minecraft.world.entity.projectile;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ import net.minecraft.core.particles.ParticleTypes;
/*    */ import net.minecraft.world.damagesource.DamageSource;
/*    */ import net.minecraft.world.effect.MobEffectInstance;
/*    */ import net.minecraft.world.effect.MobEffects;
/*    */ import net.minecraft.world.entity.AreaEffectCloud;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.phys.EntityHitResult;
/*    */ import net.minecraft.world.phys.HitResult;
/*    */ 
/*    */ 
/*    */ public class DragonFireball
/*    */   extends AbstractHurtingProjectile
/*    */ {
/*    */   public static final float SPLASH_RANGE = 4.0F;
/*    */   
/*    */   public DragonFireball(EntityType<? extends DragonFireball> $$0, Level $$1) {
/* 24 */     super((EntityType)$$0, $$1);
/*    */   }
/*    */   
/*    */   public DragonFireball(Level $$0, LivingEntity $$1, double $$2, double $$3, double $$4) {
/* 28 */     super(EntityType.DRAGON_FIREBALL, $$1, $$2, $$3, $$4, $$0);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onHit(HitResult $$0) {
/* 33 */     super.onHit($$0);
/* 34 */     if ($$0.getType() == HitResult.Type.ENTITY && ownedBy(((EntityHitResult)$$0).getEntity())) {
/*    */       return;
/*    */     }
/* 37 */     if (!(level()).isClientSide) {
/* 38 */       List<LivingEntity> $$1 = level().getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(4.0D, 2.0D, 4.0D));
/*    */       
/* 40 */       AreaEffectCloud $$2 = new AreaEffectCloud(level(), getX(), getY(), getZ());
/* 41 */       Entity $$3 = getOwner();
/* 42 */       if ($$3 instanceof LivingEntity) {
/* 43 */         $$2.setOwner((LivingEntity)$$3);
/*    */       }
/* 45 */       $$2.setParticle((ParticleOptions)ParticleTypes.DRAGON_BREATH);
/* 46 */       $$2.setRadius(3.0F);
/* 47 */       $$2.setDuration(600);
/* 48 */       $$2.setRadiusPerTick((7.0F - $$2.getRadius()) / $$2.getDuration());
/* 49 */       $$2.addEffect(new MobEffectInstance(MobEffects.HARM, 1, 1));
/*    */       
/* 51 */       if (!$$1.isEmpty()) {
/* 52 */         for (LivingEntity $$4 : $$1) {
/* 53 */           double $$5 = distanceToSqr((Entity)$$4);
/* 54 */           if ($$5 < 16.0D) {
/* 55 */             $$2.setPos($$4.getX(), $$4.getY(), $$4.getZ());
/*    */             
/*    */             break;
/*    */           } 
/*    */         } 
/*    */       }
/* 61 */       level().levelEvent(2006, blockPosition(), isSilent() ? -1 : 1);
/* 62 */       level().addFreshEntity((Entity)$$2);
/*    */       
/* 64 */       discard();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isPickable() {
/* 70 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hurt(DamageSource $$0, float $$1) {
/* 75 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   protected ParticleOptions getTrailParticle() {
/* 80 */     return (ParticleOptions)ParticleTypes.DRAGON_BREATH;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean shouldBurn() {
/* 85 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\projectile\DragonFireball.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */