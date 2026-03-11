/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.projectile.SpectralArrow;
/*    */ 
/*    */ public class SpectralArrowRenderer extends ArrowRenderer<SpectralArrow> {
/*  7 */   public static final ResourceLocation SPECTRAL_ARROW_LOCATION = new ResourceLocation("textures/entity/projectiles/spectral_arrow.png");
/*    */   
/*    */   public SpectralArrowRenderer(EntityRendererProvider.Context $$0) {
/* 10 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(SpectralArrow $$0) {
/* 15 */     return SPECTRAL_ARROW_LOCATION;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\SpectralArrowRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */