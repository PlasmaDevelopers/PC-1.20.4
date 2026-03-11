/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.TadpoleModel;
/*    */ import net.minecraft.client.model.geom.ModelLayers;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.animal.frog.Tadpole;
/*    */ 
/*    */ public class TadpoleRenderer extends MobRenderer<Tadpole, TadpoleModel<Tadpole>> {
/*  9 */   private static final ResourceLocation TADPOLE_TEXTURE = new ResourceLocation("textures/entity/tadpole/tadpole.png");
/*    */   
/*    */   public TadpoleRenderer(EntityRendererProvider.Context $$0) {
/* 12 */     super($$0, new TadpoleModel($$0.bakeLayer(ModelLayers.TADPOLE)), 0.14F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(Tadpole $$0) {
/* 17 */     return TADPOLE_TEXTURE;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\TadpoleRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */