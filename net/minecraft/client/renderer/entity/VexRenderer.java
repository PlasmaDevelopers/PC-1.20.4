/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.VexModel;
/*    */ import net.minecraft.client.model.geom.ModelLayers;
/*    */ import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
/*    */ import net.minecraft.client.renderer.entity.layers.RenderLayer;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.monster.Vex;
/*    */ 
/*    */ public class VexRenderer extends MobRenderer<Vex, VexModel> {
/* 12 */   private static final ResourceLocation VEX_LOCATION = new ResourceLocation("textures/entity/illager/vex.png");
/* 13 */   private static final ResourceLocation VEX_CHARGING_LOCATION = new ResourceLocation("textures/entity/illager/vex_charging.png");
/*    */   
/*    */   public VexRenderer(EntityRendererProvider.Context $$0) {
/* 16 */     super($$0, new VexModel($$0.bakeLayer(ModelLayers.VEX)), 0.3F);
/* 17 */     addLayer((RenderLayer<Vex, VexModel>)new ItemInHandLayer(this, $$0.getItemInHandRenderer()));
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getBlockLightLevel(Vex $$0, BlockPos $$1) {
/* 22 */     return 15;
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(Vex $$0) {
/* 27 */     if ($$0.isCharging()) {
/* 28 */       return VEX_CHARGING_LOCATION;
/*    */     }
/* 30 */     return VEX_LOCATION;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\VexRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */