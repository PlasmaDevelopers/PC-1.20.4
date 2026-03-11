/*     */ package net.minecraft.world.entity.monster;
/*     */ 
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.ai.goal.Goal;
/*     */ import net.minecraft.world.entity.projectile.LargeFireball;
/*     */ import net.minecraft.world.level.Level;
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
/*     */ class GhastShootFireballGoal
/*     */   extends Goal
/*     */ {
/*     */   private final Ghast ghast;
/*     */   public int chargeTime;
/*     */   
/*     */   public GhastShootFireballGoal(Ghast $$0) {
/* 314 */     this.ghast = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canUse() {
/* 319 */     return (this.ghast.getTarget() != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void start() {
/* 324 */     this.chargeTime = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void stop() {
/* 329 */     this.ghast.setCharging(false);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean requiresUpdateEveryTick() {
/* 334 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 339 */     LivingEntity $$0 = this.ghast.getTarget();
/* 340 */     if ($$0 == null) {
/*     */       return;
/*     */     }
/*     */     
/* 344 */     double $$1 = 64.0D;
/* 345 */     if ($$0.distanceToSqr((Entity)this.ghast) < 4096.0D && this.ghast.hasLineOfSight((Entity)$$0)) {
/* 346 */       Level $$2 = this.ghast.level();
/*     */       
/* 348 */       this.chargeTime++;
/* 349 */       if (this.chargeTime == 10 && !this.ghast.isSilent()) {
/* 350 */         $$2.levelEvent(null, 1015, this.ghast.blockPosition(), 0);
/*     */       }
/* 352 */       if (this.chargeTime == 20) {
/* 353 */         double $$3 = 4.0D;
/* 354 */         Vec3 $$4 = this.ghast.getViewVector(1.0F);
/*     */         
/* 356 */         double $$5 = $$0.getX() - this.ghast.getX() + $$4.x * 4.0D;
/* 357 */         double $$6 = $$0.getY(0.5D) - 0.5D + this.ghast.getY(0.5D);
/* 358 */         double $$7 = $$0.getZ() - this.ghast.getZ() + $$4.z * 4.0D;
/*     */         
/* 360 */         if (!this.ghast.isSilent()) {
/* 361 */           $$2.levelEvent(null, 1016, this.ghast.blockPosition(), 0);
/*     */         }
/* 363 */         LargeFireball $$8 = new LargeFireball($$2, (LivingEntity)this.ghast, $$5, $$6, $$7, this.ghast.getExplosionPower());
/* 364 */         $$8.setPos(this.ghast.getX() + $$4.x * 4.0D, this.ghast.getY(0.5D) + 0.5D, $$8.getZ() + $$4.z * 4.0D);
/* 365 */         $$2.addFreshEntity((Entity)$$8);
/* 366 */         this.chargeTime = -40;
/*     */       } 
/* 368 */     } else if (this.chargeTime > 0) {
/* 369 */       this.chargeTime--;
/*     */     } 
/* 371 */     this.ghast.setCharging((this.chargeTime > 10));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\Ghast$GhastShootFireballGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */