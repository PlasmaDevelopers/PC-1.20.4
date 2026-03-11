/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.GoatModel;
/*    */ import net.minecraft.client.model.geom.ModelLayers;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.animal.goat.Goat;
/*    */ 
/*    */ public class GoatRenderer extends MobRenderer<Goat, GoatModel<Goat>> {
/*  9 */   private static final ResourceLocation GOAT_LOCATION = new ResourceLocation("textures/entity/goat/goat.png");
/*    */   
/*    */   public GoatRenderer(EntityRendererProvider.Context $$0) {
/* 12 */     super($$0, new GoatModel($$0.bakeLayer(ModelLayers.GOAT)), 0.7F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(Goat $$0) {
/* 17 */     return GOAT_LOCATION;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\GoatRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */