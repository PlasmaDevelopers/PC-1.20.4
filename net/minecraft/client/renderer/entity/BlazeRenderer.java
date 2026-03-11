/*    */ package net.minecraft.client.renderer.entity;
/*    */ 
/*    */ import net.minecraft.client.model.BlazeModel;
/*    */ import net.minecraft.client.model.geom.ModelLayers;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.monster.Blaze;
/*    */ 
/*    */ public class BlazeRenderer extends MobRenderer<Blaze, BlazeModel<Blaze>> {
/* 11 */   private static final ResourceLocation BLAZE_LOCATION = new ResourceLocation("textures/entity/blaze.png");
/*    */   
/*    */   public BlazeRenderer(EntityRendererProvider.Context $$0) {
/* 14 */     super($$0, new BlazeModel($$0.bakeLayer(ModelLayers.BLAZE)), 0.5F);
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getBlockLightLevel(Blaze $$0, BlockPos $$1) {
/* 19 */     return 15;
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(Blaze $$0) {
/* 24 */     return BLAZE_LOCATION;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\BlazeRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */