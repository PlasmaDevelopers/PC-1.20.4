/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import net.minecraft.client.model.WolfModel;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.entity.RenderLayerParent;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.animal.Wolf;
/*    */ 
/*    */ public class WolfCollarLayer extends RenderLayer<Wolf, WolfModel<Wolf>> {
/* 11 */   private static final ResourceLocation WOLF_COLLAR_LOCATION = new ResourceLocation("textures/entity/wolf/wolf_collar.png");
/*    */   
/*    */   public WolfCollarLayer(RenderLayerParent<Wolf, WolfModel<Wolf>> $$0) {
/* 14 */     super($$0);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(PoseStack $$0, MultiBufferSource $$1, int $$2, Wolf $$3, float $$4, float $$5, float $$6, float $$7, float $$8, float $$9) {
/* 20 */     if (!$$3.isTame() || $$3.isInvisible()) {
/*    */       return;
/*    */     }
/*    */     
/* 24 */     float[] $$10 = $$3.getCollarColor().getTextureDiffuseColors();
/*    */     
/* 26 */     renderColoredCutoutModel((EntityModel<Wolf>)getParentModel(), WOLF_COLLAR_LOCATION, $$0, $$1, $$2, $$3, $$10[0], $$10[1], $$10[2]);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\layers\WolfCollarLayer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */