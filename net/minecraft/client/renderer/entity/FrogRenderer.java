/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.FrogModel;
/*    */ import net.minecraft.client.model.geom.ModelLayers;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.animal.frog.Frog;
/*    */ 
/*    */ public class FrogRenderer extends MobRenderer<Frog, FrogModel<Frog>> {
/*    */   public FrogRenderer(EntityRendererProvider.Context $$0) {
/* 10 */     super($$0, new FrogModel($$0.bakeLayer(ModelLayers.FROG)), 0.3F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(Frog $$0) {
/* 15 */     return $$0.getVariant().texture();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\FrogRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */