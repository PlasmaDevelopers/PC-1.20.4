/*    */ package net.minecraft.client.renderer.entity;
/*    */ 
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.math.Axis;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.texture.OverlayTexture;
/*    */ import net.minecraft.client.renderer.texture.TextureAtlas;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.projectile.ItemSupplier;
/*    */ import net.minecraft.world.item.ItemDisplayContext;
/*    */ 
/*    */ public class ThrownItemRenderer<T extends Entity & ItemSupplier>
/*    */   extends EntityRenderer<T>
/*    */ {
/*    */   private static final float MIN_CAMERA_DISTANCE_SQUARED = 12.25F;
/*    */   private final ItemRenderer itemRenderer;
/*    */   private final float scale;
/*    */   private final boolean fullBright;
/*    */   
/*    */   public ThrownItemRenderer(EntityRendererProvider.Context $$0, float $$1, boolean $$2) {
/* 23 */     super($$0);
/* 24 */     this.itemRenderer = $$0.getItemRenderer();
/* 25 */     this.scale = $$1;
/* 26 */     this.fullBright = $$2;
/*    */   }
/*    */   
/*    */   public ThrownItemRenderer(EntityRendererProvider.Context $$0) {
/* 30 */     this($$0, 1.0F, false);
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getBlockLightLevel(T $$0, BlockPos $$1) {
/* 35 */     return this.fullBright ? 15 : super.getBlockLightLevel($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(T $$0, float $$1, float $$2, PoseStack $$3, MultiBufferSource $$4, int $$5) {
/* 40 */     if (((Entity)$$0).tickCount < 2 && this.entityRenderDispatcher.camera.getEntity().distanceToSqr((Entity)$$0) < 12.25D) {
/*    */       return;
/*    */     }
/*    */     
/* 44 */     $$3.pushPose();
/*    */     
/* 46 */     $$3.scale(this.scale, this.scale, this.scale);
/*    */     
/* 48 */     $$3.mulPose(this.entityRenderDispatcher.cameraOrientation());
/* 49 */     $$3.mulPose(Axis.YP.rotationDegrees(180.0F));
/*    */     
/* 51 */     this.itemRenderer.renderStatic(((ItemSupplier)$$0).getItem(), ItemDisplayContext.GROUND, $$5, OverlayTexture.NO_OVERLAY, $$3, $$4, $$0.level(), $$0.getId());
/*    */     
/* 53 */     $$3.popPose();
/*    */     
/* 55 */     super.render($$0, $$1, $$2, $$3, $$4, $$5);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(Entity $$0) {
/* 60 */     return TextureAtlas.LOCATION_BLOCKS;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\ThrownItemRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */