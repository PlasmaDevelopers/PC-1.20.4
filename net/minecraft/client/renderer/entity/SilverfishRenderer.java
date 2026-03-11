/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.SilverfishModel;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.monster.Silverfish;
/*    */ 
/*    */ public class SilverfishRenderer extends MobRenderer<Silverfish, SilverfishModel<Silverfish>> {
/*  9 */   private static final ResourceLocation SILVERFISH_LOCATION = new ResourceLocation("textures/entity/silverfish.png");
/*    */   
/*    */   public SilverfishRenderer(EntityRendererProvider.Context $$0) {
/* 12 */     super($$0, new SilverfishModel($$0.bakeLayer(ModelLayers.SILVERFISH)), 0.3F);
/*    */   }
/*    */ 
/*    */   
/*    */   protected float getFlipDegrees(Silverfish $$0) {
/* 17 */     return 180.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(Silverfish $$0) {
/* 22 */     return SILVERFISH_LOCATION;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\SilverfishRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */