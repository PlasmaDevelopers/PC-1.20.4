/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.RavagerModel;
/*    */ import net.minecraft.client.model.geom.ModelLayers;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.monster.Ravager;
/*    */ 
/*    */ public class RavagerRenderer extends MobRenderer<Ravager, RavagerModel> {
/*  9 */   private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation("textures/entity/illager/ravager.png");
/*    */   
/*    */   public RavagerRenderer(EntityRendererProvider.Context $$0) {
/* 12 */     super($$0, new RavagerModel($$0.bakeLayer(ModelLayers.RAVAGER)), 1.1F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(Ravager $$0) {
/* 17 */     return TEXTURE_LOCATION;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\RavagerRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */