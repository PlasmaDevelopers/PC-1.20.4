/*    */ package net.minecraft.world.entity.projectile;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ import net.minecraft.core.particles.ParticleTypes;
/*    */ import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.animal.horse.Llama;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.phys.BlockHitResult;
/*    */ import net.minecraft.world.phys.EntityHitResult;
/*    */ import net.minecraft.world.phys.HitResult;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public class LlamaSpit extends Projectile {
/*    */   public LlamaSpit(EntityType<? extends LlamaSpit> $$0, Level $$1) {
/* 18 */     super((EntityType)$$0, $$1);
/*    */   }
/*    */   
/*    */   public LlamaSpit(Level $$0, Llama $$1) {
/* 22 */     this(EntityType.LLAMA_SPIT, $$0);
/* 23 */     setOwner((Entity)$$1);
/* 24 */     setPos($$1.getX() - ($$1.getBbWidth() + 1.0F) * 0.5D * Mth.sin($$1.yBodyRot * 0.017453292F), $$1.getEyeY() - 0.10000000149011612D, $$1.getZ() + ($$1.getBbWidth() + 1.0F) * 0.5D * Mth.cos($$1.yBodyRot * 0.017453292F));
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick() {
/* 29 */     super.tick();
/*    */     
/* 31 */     Vec3 $$0 = getDeltaMovement();
/* 32 */     HitResult $$1 = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
/* 33 */     onHit($$1);
/*    */     
/* 35 */     double $$2 = getX() + $$0.x;
/* 36 */     double $$3 = getY() + $$0.y;
/* 37 */     double $$4 = getZ() + $$0.z;
/*    */     
/* 39 */     updateRotation();
/*    */     
/* 41 */     float $$5 = 0.99F;
/* 42 */     float $$6 = 0.06F;
/*    */     
/* 44 */     if (level().getBlockStates(getBoundingBox()).noneMatch(BlockBehaviour.BlockStateBase::isAir)) {
/* 45 */       discard();
/*    */       
/*    */       return;
/*    */     } 
/* 49 */     if (isInWaterOrBubble()) {
/* 50 */       discard();
/*    */       
/*    */       return;
/*    */     } 
/* 54 */     setDeltaMovement($$0.scale(0.9900000095367432D));
/* 55 */     if (!isNoGravity()) {
/* 56 */       setDeltaMovement(getDeltaMovement().add(0.0D, -0.05999999865889549D, 0.0D));
/*    */     }
/*    */     
/* 59 */     setPos($$2, $$3, $$4);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onHitEntity(EntityHitResult $$0) {
/* 64 */     super.onHitEntity($$0);
/* 65 */     Entity entity = getOwner(); if (entity instanceof LivingEntity) { LivingEntity $$1 = (LivingEntity)entity;
/* 66 */       $$0.getEntity().hurt(damageSources().mobProjectile(this, $$1), 1.0F); }
/*    */   
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onHitBlock(BlockHitResult $$0) {
/* 72 */     super.onHitBlock($$0);
/*    */     
/* 74 */     if (!(level()).isClientSide) {
/* 75 */       discard();
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void defineSynchedData() {}
/*    */ 
/*    */   
/*    */   public void recreateFromPacket(ClientboundAddEntityPacket $$0) {
/* 85 */     super.recreateFromPacket($$0);
/*    */     
/* 87 */     double $$1 = $$0.getXa();
/* 88 */     double $$2 = $$0.getYa();
/* 89 */     double $$3 = $$0.getZa();
/*    */     
/* 91 */     for (int $$4 = 0; $$4 < 7; $$4++) {
/* 92 */       double $$5 = 0.4D + 0.1D * $$4;
/* 93 */       level().addParticle((ParticleOptions)ParticleTypes.SPIT, getX(), getY(), getZ(), $$1 * $$5, $$2, $$3 * $$5);
/*    */     } 
/*    */     
/* 96 */     setDeltaMovement($$1, $$2, $$3);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\projectile\LlamaSpit.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */