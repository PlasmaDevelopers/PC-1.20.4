/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.projectile.Arrow;
/*    */ 
/*    */ public class TippableArrowRenderer extends ArrowRenderer<Arrow> {
/*  7 */   public static final ResourceLocation NORMAL_ARROW_LOCATION = new ResourceLocation("textures/entity/projectiles/arrow.png");
/*  8 */   public static final ResourceLocation TIPPED_ARROW_LOCATION = new ResourceLocation("textures/entity/projectiles/tipped_arrow.png");
/*    */   
/*    */   public TippableArrowRenderer(EntityRendererProvider.Context $$0) {
/* 11 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(Arrow $$0) {
/* 16 */     return ($$0.getColor() > 0) ? TIPPED_ARROW_LOCATION : NORMAL_ARROW_LOCATION;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\TippableArrowRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */