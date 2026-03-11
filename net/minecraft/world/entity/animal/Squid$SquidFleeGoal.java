/*     */ package net.minecraft.world.entity.animal;
/*     */ 
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.tags.FluidTags;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.ai.goal.Goal;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class SquidFleeGoal
/*     */   extends Goal
/*     */ {
/*     */   private static final float SQUID_FLEE_SPEED = 3.0F;
/*     */   private static final float SQUID_FLEE_MIN_DISTANCE = 5.0F;
/*     */   private static final float SQUID_FLEE_MAX_DISTANCE = 10.0F;
/*     */   private int fleeTicks;
/*     */   
/*     */   public boolean canUse() {
/* 274 */     LivingEntity $$0 = Squid.this.getLastHurtByMob();
/* 275 */     if (Squid.this.isInWater() && $$0 != null) {
/* 276 */       return (Squid.this.distanceToSqr((Entity)$$0) < 100.0D);
/*     */     }
/*     */     
/* 279 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void start() {
/* 284 */     this.fleeTicks = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean requiresUpdateEveryTick() {
/* 289 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 294 */     this.fleeTicks++;
/*     */     
/* 296 */     LivingEntity $$0 = Squid.this.getLastHurtByMob();
/* 297 */     if ($$0 == null) {
/*     */       return;
/*     */     }
/*     */     
/* 301 */     Vec3 $$1 = new Vec3(Squid.this.getX() - $$0.getX(), Squid.this.getY() - $$0.getY(), Squid.this.getZ() - $$0.getZ());
/*     */     
/* 303 */     BlockState $$2 = Squid.this.level().getBlockState(BlockPos.containing(Squid.this.getX() + $$1.x, Squid.this.getY() + $$1.y, Squid.this.getZ() + $$1.z));
/* 304 */     FluidState $$3 = Squid.this.level().getFluidState(BlockPos.containing(Squid.this.getX() + $$1.x, Squid.this.getY() + $$1.y, Squid.this.getZ() + $$1.z));
/* 305 */     if ($$3.is(FluidTags.WATER) || $$2.isAir()) {
/* 306 */       double $$4 = $$1.length();
/* 307 */       if ($$4 > 0.0D) {
/* 308 */         $$1.normalize();
/*     */         
/* 310 */         double $$5 = 3.0D;
/* 311 */         if ($$4 > 5.0D) {
/* 312 */           $$5 -= ($$4 - 5.0D) / 5.0D;
/*     */         }
/*     */         
/* 315 */         if ($$5 > 0.0D) {
/* 316 */           $$1 = $$1.scale($$5);
/*     */         }
/*     */       } 
/*     */       
/* 320 */       if ($$2.isAir()) {
/* 321 */         $$1 = $$1.subtract(0.0D, $$1.y, 0.0D);
/*     */       }
/*     */       
/* 324 */       Squid.this.setMovementVector((float)$$1.x / 20.0F, (float)$$1.y / 20.0F, (float)$$1.z / 20.0F);
/*     */     } 
/*     */     
/* 327 */     if (this.fleeTicks % 10 == 5)
/* 328 */       Squid.this.level().addParticle((ParticleOptions)ParticleTypes.BUBBLE, Squid.this.getX(), Squid.this.getY(), Squid.this.getZ(), 0.0D, 0.0D, 0.0D); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\animal\Squid$SquidFleeGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */