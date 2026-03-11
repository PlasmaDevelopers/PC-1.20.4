/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*    */ import net.minecraft.client.Camera;
/*    */ import net.minecraft.client.multiplayer.ClientLevel;
/*    */ 
/*    */ public class NoRenderParticle extends Particle {
/*    */   protected NoRenderParticle(ClientLevel $$0, double $$1, double $$2, double $$3) {
/*  9 */     super($$0, $$1, $$2, $$3);
/*    */   }
/*    */   
/*    */   protected NoRenderParticle(ClientLevel $$0, double $$1, double $$2, double $$3, double $$4, double $$5, double $$6) {
/* 13 */     super($$0, $$1, $$2, $$3, $$4, $$5, $$6);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public final void render(VertexConsumer $$0, Camera $$1, float $$2) {}
/*    */ 
/*    */   
/*    */   public ParticleRenderType getRenderType() {
/* 22 */     return ParticleRenderType.NO_RENDER;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\NoRenderParticle.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */