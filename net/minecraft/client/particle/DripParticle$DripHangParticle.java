/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.client.multiplayer.ClientLevel;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ import net.minecraft.world.level.material.Fluid;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class DripHangParticle
/*    */   extends DripParticle
/*    */ {
/*    */   private final ParticleOptions fallingParticle;
/*    */   
/*    */   DripHangParticle(ClientLevel $$0, double $$1, double $$2, double $$3, Fluid $$4, ParticleOptions $$5) {
/* 23 */     super($$0, $$1, $$2, $$3, $$4);
/* 24 */     this.fallingParticle = $$5;
/* 25 */     this.gravity *= 0.02F;
/* 26 */     this.lifetime = 40;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void preMoveUpdate() {
/* 31 */     if (this.lifetime-- <= 0) {
/* 32 */       remove();
/* 33 */       this.level.addParticle(this.fallingParticle, this.x, this.y, this.z, this.xd, this.yd, this.zd);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected void postMoveUpdate() {
/* 39 */     this.xd *= 0.02D;
/* 40 */     this.yd *= 0.02D;
/* 41 */     this.zd *= 0.02D;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\DripParticle$DripHangParticle.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */