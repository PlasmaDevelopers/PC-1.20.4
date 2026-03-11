/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.EndermiteModel;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.monster.Endermite;
/*    */ 
/*    */ public class EndermiteRenderer extends MobRenderer<Endermite, EndermiteModel<Endermite>> {
/*  9 */   private static final ResourceLocation ENDERMITE_LOCATION = new ResourceLocation("textures/entity/endermite.png");
/*    */   
/*    */   public EndermiteRenderer(EntityRendererProvider.Context $$0) {
/* 12 */     super($$0, new EndermiteModel($$0.bakeLayer(ModelLayers.ENDERMITE)), 0.3F);
/*    */   }
/*    */ 
/*    */   
/*    */   protected float getFlipDegrees(Endermite $$0) {
/* 17 */     return 180.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(Endermite $$0) {
/* 22 */     return ENDERMITE_LOCATION;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\EndermiteRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */