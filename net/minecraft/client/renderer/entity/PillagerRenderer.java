/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.IllagerModel;
/*    */ import net.minecraft.client.model.geom.ModelLayers;
/*    */ import net.minecraft.client.renderer.entity.layers.RenderLayer;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.monster.Pillager;
/*    */ 
/*    */ public class PillagerRenderer extends IllagerRenderer<Pillager> {
/* 10 */   private static final ResourceLocation PILLAGER = new ResourceLocation("textures/entity/illager/pillager.png");
/*    */   
/*    */   public PillagerRenderer(EntityRendererProvider.Context $$0) {
/* 13 */     super($$0, new IllagerModel($$0.bakeLayer(ModelLayers.PILLAGER)), 0.5F);
/*    */     
/* 15 */     addLayer((RenderLayer<Pillager, IllagerModel<Pillager>>)new ItemInHandLayer(this, $$0.getItemInHandRenderer()));
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(Pillager $$0) {
/* 20 */     return PILLAGER;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\PillagerRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */