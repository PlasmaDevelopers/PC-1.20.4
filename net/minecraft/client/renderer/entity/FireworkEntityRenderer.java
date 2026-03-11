/*    */ package net.minecraft.client.renderer.entity;
/*    */ 
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.math.Axis;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.texture.OverlayTexture;
/*    */ import net.minecraft.client.renderer.texture.TextureAtlas;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.projectile.FireworkRocketEntity;
/*    */ import net.minecraft.world.item.ItemDisplayContext;
/*    */ 
/*    */ public class FireworkEntityRenderer extends EntityRenderer<FireworkRocketEntity> {
/*    */   private final ItemRenderer itemRenderer;
/*    */   
/*    */   public FireworkEntityRenderer(EntityRendererProvider.Context $$0) {
/* 17 */     super($$0);
/* 18 */     this.itemRenderer = $$0.getItemRenderer();
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(FireworkRocketEntity $$0, float $$1, float $$2, PoseStack $$3, MultiBufferSource $$4, int $$5) {
/* 23 */     $$3.pushPose();
/*    */     
/* 25 */     $$3.mulPose(this.entityRenderDispatcher.cameraOrientation());
/* 26 */     $$3.mulPose(Axis.YP.rotationDegrees(180.0F));
/*    */     
/* 28 */     if ($$0.isShotAtAngle()) {
/* 29 */       $$3.mulPose(Axis.ZP.rotationDegrees(180.0F));
/* 30 */       $$3.mulPose(Axis.YP.rotationDegrees(180.0F));
/* 31 */       $$3.mulPose(Axis.XP.rotationDegrees(90.0F));
/*    */     } 
/*    */     
/* 34 */     this.itemRenderer.renderStatic($$0.getItem(), ItemDisplayContext.GROUND, $$5, OverlayTexture.NO_OVERLAY, $$3, $$4, $$0.level(), $$0.getId());
/*    */     
/* 36 */     $$3.popPose();
/*    */     
/* 38 */     super.render($$0, $$1, $$2, $$3, $$4, $$5);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(FireworkRocketEntity $$0) {
/* 43 */     return TextureAtlas.LOCATION_BLOCKS;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\FireworkEntityRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */