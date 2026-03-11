/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.AllayModel;
/*    */ import net.minecraft.client.model.geom.ModelLayers;
/*    */ import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
/*    */ import net.minecraft.client.renderer.entity.layers.RenderLayer;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.animal.allay.Allay;
/*    */ 
/*    */ public class AllayRenderer extends MobRenderer<Allay, AllayModel> {
/* 12 */   private static final ResourceLocation ALLAY_TEXTURE = new ResourceLocation("textures/entity/allay/allay.png");
/*    */   
/*    */   public AllayRenderer(EntityRendererProvider.Context $$0) {
/* 15 */     super($$0, new AllayModel($$0.bakeLayer(ModelLayers.ALLAY)), 0.4F);
/*    */     
/* 17 */     addLayer((RenderLayer<Allay, AllayModel>)new ItemInHandLayer(this, $$0.getItemInHandRenderer()));
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(Allay $$0) {
/* 22 */     return ALLAY_TEXTURE;
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getBlockLightLevel(Allay $$0, BlockPos $$1) {
/* 27 */     return 15;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\AllayRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */