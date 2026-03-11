/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.OcelotModel;
/*    */ import net.minecraft.client.model.geom.ModelLayers;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.animal.Ocelot;
/*    */ 
/*    */ public class OcelotRenderer extends MobRenderer<Ocelot, OcelotModel<Ocelot>> {
/*  9 */   private static final ResourceLocation CAT_OCELOT_LOCATION = new ResourceLocation("textures/entity/cat/ocelot.png");
/*    */   
/*    */   public OcelotRenderer(EntityRendererProvider.Context $$0) {
/* 12 */     super($$0, new OcelotModel($$0.bakeLayer(ModelLayers.OCELOT)), 0.4F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(Ocelot $$0) {
/* 17 */     return CAT_OCELOT_LOCATION;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\OcelotRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */