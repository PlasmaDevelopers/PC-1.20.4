/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*    */ import com.mojang.math.Axis;
/*    */ import net.minecraft.client.Camera;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.GuardianModel;
/*    */ import net.minecraft.client.model.Model;
/*    */ import net.minecraft.client.model.geom.ModelLayers;
/*    */ import net.minecraft.client.multiplayer.ClientLevel;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.RenderType;
/*    */ import net.minecraft.client.renderer.entity.ElderGuardianRenderer;
/*    */ import net.minecraft.client.renderer.texture.OverlayTexture;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ import net.minecraft.core.particles.SimpleParticleType;
/*    */ import net.minecraft.util.Mth;
/*    */ 
/*    */ public class MobAppearanceParticle extends Particle {
/*    */   private final Model model;
/* 22 */   private final RenderType renderType = RenderType.entityTranslucent(ElderGuardianRenderer.GUARDIAN_ELDER_LOCATION);
/*    */   
/*    */   MobAppearanceParticle(ClientLevel $$0, double $$1, double $$2, double $$3) {
/* 25 */     super($$0, $$1, $$2, $$3);
/* 26 */     this.model = (Model)new GuardianModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModelLayers.ELDER_GUARDIAN));
/* 27 */     this.gravity = 0.0F;
/* 28 */     this.lifetime = 30;
/*    */   }
/*    */ 
/*    */   
/*    */   public ParticleRenderType getRenderType() {
/* 33 */     return ParticleRenderType.CUSTOM;
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(VertexConsumer $$0, Camera $$1, float $$2) {
/* 38 */     float $$3 = (this.age + $$2) / this.lifetime;
/* 39 */     float $$4 = 0.05F + 0.5F * Mth.sin($$3 * 3.1415927F);
/*    */     
/* 41 */     PoseStack $$5 = new PoseStack();
/*    */     
/* 43 */     $$5.mulPose($$1.rotation());
/* 44 */     $$5.mulPose(Axis.XP.rotationDegrees(150.0F * $$3 - 60.0F));
/*    */     
/* 46 */     $$5.scale(-1.0F, -1.0F, 1.0F);
/* 47 */     $$5.translate(0.0F, -1.101F, 1.5F);
/*    */     
/* 49 */     MultiBufferSource.BufferSource $$6 = Minecraft.getInstance().renderBuffers().bufferSource();
/* 50 */     VertexConsumer $$7 = $$6.getBuffer(this.renderType);
/* 51 */     this.model.renderToBuffer($$5, $$7, 15728880, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, $$4);
/*    */     
/* 53 */     $$6.endBatch();
/*    */   }
/*    */   
/*    */   public static class Provider
/*    */     implements ParticleProvider<SimpleParticleType> {
/*    */     public Particle createParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 59 */       return new MobAppearanceParticle($$1, $$2, $$3, $$4);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\MobAppearanceParticle.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */