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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class CoolingDripHangParticle
/*    */   extends DripParticle.DripHangParticle
/*    */ {
/*    */   CoolingDripHangParticle(ClientLevel $$0, double $$1, double $$2, double $$3, Fluid $$4, ParticleOptions $$5) {
/* 47 */     super($$0, $$1, $$2, $$3, $$4, $$5);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void preMoveUpdate() {
/* 52 */     this.rCol = 1.0F;
/* 53 */     this.gCol = 16.0F / (40 - this.lifetime + 16);
/* 54 */     this.bCol = 4.0F / (40 - this.lifetime + 8);
/* 55 */     super.preMoveUpdate();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\DripParticle$CoolingDripHangParticle.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */