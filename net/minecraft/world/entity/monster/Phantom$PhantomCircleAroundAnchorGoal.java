/*     */ package net.minecraft.world.entity.monster;
/*     */ 
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.util.Mth;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class PhantomCircleAroundAnchorGoal
/*     */   extends Phantom.PhantomMoveTargetGoal
/*     */ {
/*     */   private float angle;
/*     */   private float distance;
/*     */   private float height;
/*     */   private float clockwise;
/*     */   
/*     */   public boolean canUse() {
/* 350 */     return (Phantom.this.getTarget() == null || Phantom.this.attackPhase == Phantom.AttackPhase.CIRCLE);
/*     */   }
/*     */ 
/*     */   
/*     */   public void start() {
/* 355 */     this.distance = 5.0F + Phantom.access$000(Phantom.this).nextFloat() * 10.0F;
/* 356 */     this.height = -4.0F + Phantom.access$100(Phantom.this).nextFloat() * 9.0F;
/* 357 */     this.clockwise = Phantom.access$200(Phantom.this).nextBoolean() ? 1.0F : -1.0F;
/* 358 */     selectNext();
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 363 */     if (Phantom.access$300(Phantom.this).nextInt(adjustedTickDelay(350)) == 0) {
/* 364 */       this.height = -4.0F + Phantom.access$400(Phantom.this).nextFloat() * 9.0F;
/*     */     }
/* 366 */     if (Phantom.access$500(Phantom.this).nextInt(adjustedTickDelay(250)) == 0) {
/* 367 */       this.distance++;
/* 368 */       if (this.distance > 15.0F) {
/* 369 */         this.distance = 5.0F;
/* 370 */         this.clockwise = -this.clockwise;
/*     */       } 
/*     */     } 
/* 373 */     if (Phantom.access$600(Phantom.this).nextInt(adjustedTickDelay(450)) == 0) {
/* 374 */       this.angle = Phantom.access$700(Phantom.this).nextFloat() * 2.0F * 3.1415927F;
/* 375 */       selectNext();
/*     */     } 
/* 377 */     if (touchingTarget()) {
/* 378 */       selectNext();
/*     */     }
/*     */     
/* 381 */     if (Phantom.this.moveTargetPoint.y < Phantom.this.getY() && !Phantom.this.level().isEmptyBlock(Phantom.this.blockPosition().below(1))) {
/* 382 */       this.height = Math.max(1.0F, this.height);
/* 383 */       selectNext();
/*     */     } 
/*     */     
/* 386 */     if (Phantom.this.moveTargetPoint.y > Phantom.this.getY() && !Phantom.this.level().isEmptyBlock(Phantom.this.blockPosition().above(1))) {
/* 387 */       this.height = Math.min(-1.0F, this.height);
/* 388 */       selectNext();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void selectNext() {
/* 393 */     if (BlockPos.ZERO.equals(Phantom.this.anchorPoint)) {
/* 394 */       Phantom.this.anchorPoint = Phantom.this.blockPosition();
/*     */     }
/* 396 */     this.angle += this.clockwise * 15.0F * 0.017453292F;
/* 397 */     Phantom.this.moveTargetPoint = Vec3.atLowerCornerOf((Vec3i)Phantom.this.anchorPoint).add((this.distance * Mth.cos(this.angle)), (-4.0F + this.height), (this.distance * Mth.sin(this.angle)));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\Phantom$PhantomCircleAroundAnchorGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */