/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.DolphinModel;
/*    */ import net.minecraft.client.model.geom.ModelLayers;
/*    */ import net.minecraft.client.renderer.entity.layers.RenderLayer;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.animal.Dolphin;
/*    */ 
/*    */ public class DolphinRenderer extends MobRenderer<Dolphin, DolphinModel<Dolphin>> {
/* 10 */   private static final ResourceLocation DOLPHIN_LOCATION = new ResourceLocation("textures/entity/dolphin.png");
/*    */   
/*    */   public DolphinRenderer(EntityRendererProvider.Context $$0) {
/* 13 */     super($$0, new DolphinModel($$0.bakeLayer(ModelLayers.DOLPHIN)), 0.7F);
/*    */     
/* 15 */     addLayer((RenderLayer<Dolphin, DolphinModel<Dolphin>>)new DolphinCarryingItemLayer(this, $$0.getItemInHandRenderer()));
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(Dolphin $$0) {
/* 20 */     return DOLPHIN_LOCATION;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\DolphinRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */