/*    */ package net.minecraft.client.renderer.entity;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import net.minecraft.client.model.PhantomModel;
/*    */ import net.minecraft.client.model.geom.ModelLayers;
/*    */ import net.minecraft.client.renderer.entity.layers.RenderLayer;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.monster.Phantom;
/*    */ 
/*    */ public class PhantomRenderer extends MobRenderer<Phantom, PhantomModel<Phantom>> {
/* 12 */   private static final ResourceLocation PHANTOM_LOCATION = new ResourceLocation("textures/entity/phantom.png");
/*    */   
/*    */   public PhantomRenderer(EntityRendererProvider.Context $$0) {
/* 15 */     super($$0, new PhantomModel($$0.bakeLayer(ModelLayers.PHANTOM)), 0.75F);
/*    */     
/* 17 */     addLayer((RenderLayer<Phantom, PhantomModel<Phantom>>)new PhantomEyesLayer(this));
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(Phantom $$0) {
/* 22 */     return PHANTOM_LOCATION;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void scale(Phantom $$0, PoseStack $$1, float $$2) {
/* 27 */     int $$3 = $$0.getPhantomSize();
/* 28 */     float $$4 = 1.0F + 0.15F * $$3;
/* 29 */     $$1.scale($$4, $$4, $$4);
/*    */     
/* 31 */     $$1.translate(0.0F, 1.3125F, 0.1875F);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void setupRotations(Phantom $$0, PoseStack $$1, float $$2, float $$3, float $$4) {
/* 36 */     super.setupRotations($$0, $$1, $$2, $$3, $$4);
/* 37 */     $$1.mulPose(Axis.XP.rotationDegrees($$0.getXRot()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\PhantomRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */