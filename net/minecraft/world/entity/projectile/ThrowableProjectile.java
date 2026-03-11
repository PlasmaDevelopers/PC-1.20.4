/*    */ package net.minecraft.world.entity.projectile;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ import net.minecraft.core.particles.ParticleTypes;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ import net.minecraft.world.level.block.entity.TheEndGatewayBlockEntity;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.phys.BlockHitResult;
/*    */ import net.minecraft.world.phys.HitResult;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public abstract class ThrowableProjectile extends Projectile {
/*    */   protected ThrowableProjectile(EntityType<? extends ThrowableProjectile> $$0, Level $$1) {
/* 18 */     super((EntityType)$$0, $$1);
/*    */   }
/*    */   
/*    */   protected ThrowableProjectile(EntityType<? extends ThrowableProjectile> $$0, double $$1, double $$2, double $$3, Level $$4) {
/* 22 */     this($$0, $$4);
/*    */     
/* 24 */     setPos($$1, $$2, $$3);
/*    */   }
/*    */   
/*    */   protected ThrowableProjectile(EntityType<? extends ThrowableProjectile> $$0, LivingEntity $$1, Level $$2) {
/* 28 */     this($$0, $$1.getX(), $$1.getEyeY() - 0.10000000149011612D, $$1.getZ(), $$2);
/*    */     
/* 30 */     setOwner((Entity)$$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldRenderAtSqrDistance(double $$0) {
/* 35 */     double $$1 = getBoundingBox().getSize() * 4.0D;
/* 36 */     if (Double.isNaN($$1)) {
/* 37 */       $$1 = 4.0D;
/*    */     }
/* 39 */     $$1 *= 64.0D;
/* 40 */     return ($$0 < $$1 * $$1);
/*    */   }
/*    */   
/*    */   public void tick() {
/*    */     float $$12;
/* 45 */     super.tick();
/*    */     
/* 47 */     HitResult $$0 = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
/*    */     
/* 49 */     boolean $$1 = false;
/* 50 */     if ($$0.getType() == HitResult.Type.BLOCK) {
/* 51 */       BlockPos $$2 = ((BlockHitResult)$$0).getBlockPos();
/* 52 */       BlockState $$3 = level().getBlockState($$2);
/* 53 */       if ($$3.is(Blocks.NETHER_PORTAL)) {
/* 54 */         handleInsidePortal($$2);
/* 55 */         $$1 = true;
/* 56 */       } else if ($$3.is(Blocks.END_GATEWAY)) {
/* 57 */         BlockEntity $$4 = level().getBlockEntity($$2);
/* 58 */         if ($$4 instanceof TheEndGatewayBlockEntity && TheEndGatewayBlockEntity.canEntityTeleport(this)) {
/* 59 */           TheEndGatewayBlockEntity.teleportEntity(level(), $$2, $$3, this, (TheEndGatewayBlockEntity)$$4);
/*    */         }
/* 61 */         $$1 = true;
/*    */       } 
/*    */     } 
/* 64 */     if ($$0.getType() != HitResult.Type.MISS && !$$1) {
/* 65 */       onHit($$0);
/*    */     }
/*    */     
/* 68 */     checkInsideBlocks();
/* 69 */     Vec3 $$5 = getDeltaMovement();
/* 70 */     double $$6 = getX() + $$5.x;
/* 71 */     double $$7 = getY() + $$5.y;
/* 72 */     double $$8 = getZ() + $$5.z;
/*    */     
/* 74 */     updateRotation();
/*    */ 
/*    */     
/* 77 */     if (isInWater()) {
/* 78 */       for (int $$9 = 0; $$9 < 4; $$9++) {
/* 79 */         float $$10 = 0.25F;
/* 80 */         level().addParticle((ParticleOptions)ParticleTypes.BUBBLE, $$6 - $$5.x * 0.25D, $$7 - $$5.y * 0.25D, $$8 - $$5.z * 0.25D, $$5.x, $$5.y, $$5.z);
/*    */       } 
/* 82 */       float $$11 = 0.8F;
/*    */     } else {
/* 84 */       $$12 = 0.99F;
/*    */     } 
/*    */     
/* 87 */     setDeltaMovement($$5.scale($$12));
/*    */     
/* 89 */     if (!isNoGravity()) {
/* 90 */       Vec3 $$13 = getDeltaMovement();
/* 91 */       setDeltaMovement($$13.x, $$13.y - getGravity(), $$13.z);
/*    */     } 
/*    */     
/* 94 */     setPos($$6, $$7, $$8);
/*    */   }
/*    */   
/*    */   protected float getGravity() {
/* 98 */     return 0.03F;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\projectile\ThrowableProjectile.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */