/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.BatModel;
/*    */ import net.minecraft.client.model.geom.ModelLayers;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.ambient.Bat;
/*    */ 
/*    */ public class BatRenderer extends MobRenderer<Bat, BatModel> {
/*  9 */   private static final ResourceLocation BAT_LOCATION = new ResourceLocation("textures/entity/bat.png");
/*    */   
/*    */   public BatRenderer(EntityRendererProvider.Context $$0) {
/* 12 */     super($$0, new BatModel($$0.bakeLayer(ModelLayers.BAT)), 0.25F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(Bat $$0) {
/* 17 */     return BAT_LOCATION;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\BatRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */