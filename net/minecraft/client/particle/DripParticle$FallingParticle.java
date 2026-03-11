/*     */ package net.minecraft.client.particle;
/*     */ 
/*     */ import net.minecraft.client.multiplayer.ClientLevel;
/*     */ import net.minecraft.world.level.material.Fluid;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class FallingParticle
/*     */   extends DripParticle
/*     */ {
/*     */   FallingParticle(ClientLevel $$0, double $$1, double $$2, double $$3, Fluid $$4) {
/* 111 */     this($$0, $$1, $$2, $$3, $$4, (int)(64.0D / (Math.random() * 0.8D + 0.2D)));
/*     */   }
/*     */   
/*     */   FallingParticle(ClientLevel $$0, double $$1, double $$2, double $$3, Fluid $$4, int $$5) {
/* 115 */     super($$0, $$1, $$2, $$3, $$4);
/* 116 */     this.lifetime = $$5;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void postMoveUpdate() {
/* 121 */     if (this.onGround)
/* 122 */       remove(); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\DripParticle$FallingParticle.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */