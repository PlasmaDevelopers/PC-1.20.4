/*     */ package net.minecraft.world.entity.monster;
/*     */ 
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.ai.control.MoveControl;
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
/*     */ class PhantomMoveControl
/*     */   extends MoveControl
/*     */ {
/* 254 */   private float speed = 0.1F;
/*     */   
/*     */   public PhantomMoveControl(Mob $$0) {
/* 257 */     super($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 262 */     if (Phantom.this.horizontalCollision) {
/*     */       
/* 264 */       Phantom.this.setYRot(Phantom.this.getYRot() + 180.0F);
/* 265 */       this.speed = 0.1F;
/*     */     } 
/*     */ 
/*     */     
/* 269 */     double $$0 = Phantom.this.moveTargetPoint.x - Phantom.this.getX();
/* 270 */     double $$1 = Phantom.this.moveTargetPoint.y - Phantom.this.getY();
/* 271 */     double $$2 = Phantom.this.moveTargetPoint.z - Phantom.this.getZ();
/* 272 */     double $$3 = Math.sqrt($$0 * $$0 + $$2 * $$2);
/*     */ 
/*     */     
/* 275 */     if (Math.abs($$3) > 9.999999747378752E-6D) {
/* 276 */       double $$4 = 1.0D - Math.abs($$1 * 0.699999988079071D) / $$3;
/* 277 */       $$0 *= $$4;
/* 278 */       $$2 *= $$4;
/* 279 */       $$3 = Math.sqrt($$0 * $$0 + $$2 * $$2);
/* 280 */       double $$5 = Math.sqrt($$0 * $$0 + $$2 * $$2 + $$1 * $$1);
/*     */ 
/*     */       
/* 283 */       float $$6 = Phantom.this.getYRot();
/* 284 */       float $$7 = (float)Mth.atan2($$2, $$0);
/* 285 */       float $$8 = Mth.wrapDegrees(Phantom.this.getYRot() + 90.0F);
/* 286 */       float $$9 = Mth.wrapDegrees($$7 * 57.295776F);
/* 287 */       Phantom.this.setYRot(Mth.approachDegrees($$8, $$9, 4.0F) - 90.0F);
/* 288 */       Phantom.this.yBodyRot = Phantom.this.getYRot();
/*     */       
/* 290 */       if (Mth.degreesDifferenceAbs($$6, Phantom.this.getYRot()) < 3.0F) {
/* 291 */         this.speed = Mth.approach(this.speed, 1.8F, 0.005F * 1.8F / this.speed);
/*     */       } else {
/* 293 */         this.speed = Mth.approach(this.speed, 0.2F, 0.025F);
/*     */       } 
/*     */       
/* 296 */       float $$10 = (float)-(Mth.atan2(-$$1, $$3) * 57.2957763671875D);
/* 297 */       Phantom.this.setXRot($$10);
/*     */       
/* 299 */       float $$11 = Phantom.this.getYRot() + 90.0F;
/* 300 */       double $$12 = (this.speed * Mth.cos($$11 * 0.017453292F)) * Math.abs($$0 / $$5);
/* 301 */       double $$13 = (this.speed * Mth.sin($$11 * 0.017453292F)) * Math.abs($$2 / $$5);
/* 302 */       double $$14 = (this.speed * Mth.sin($$10 * 0.017453292F)) * Math.abs($$1 / $$5);
/*     */       
/* 304 */       Vec3 $$15 = Phantom.this.getDeltaMovement();
/* 305 */       Phantom.this.setDeltaMovement($$15.add((new Vec3($$12, $$14, $$13)).subtract($$15).scale(0.2D)));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\Phantom$PhantomMoveControl.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */