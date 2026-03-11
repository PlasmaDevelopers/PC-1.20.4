/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ 
/*    */ import net.minecraft.client.model.CreeperModel;
/*    */ import net.minecraft.client.model.EntityModel;
/*    */ import net.minecraft.client.model.geom.EntityModelSet;
/*    */ import net.minecraft.client.model.geom.ModelLayers;
/*    */ import net.minecraft.client.renderer.entity.RenderLayerParent;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.monster.Creeper;
/*    */ 
/*    */ public class CreeperPowerLayer extends EnergySwirlLayer<Creeper, CreeperModel<Creeper>> {
/* 12 */   private static final ResourceLocation POWER_LOCATION = new ResourceLocation("textures/entity/creeper/creeper_armor.png");
/*    */   
/*    */   private final CreeperModel<Creeper> model;
/*    */   
/*    */   public CreeperPowerLayer(RenderLayerParent<Creeper, CreeperModel<Creeper>> $$0, EntityModelSet $$1) {
/* 17 */     super($$0);
/* 18 */     this.model = new CreeperModel($$1.bakeLayer(ModelLayers.CREEPER_ARMOR));
/*    */   }
/*    */ 
/*    */   
/*    */   protected float xOffset(float $$0) {
/* 23 */     return $$0 * 0.01F;
/*    */   }
/*    */ 
/*    */   
/*    */   protected ResourceLocation getTextureLocation() {
/* 28 */     return POWER_LOCATION;
/*    */   }
/*    */ 
/*    */   
/*    */   protected EntityModel<Creeper> model() {
/* 33 */     return (EntityModel<Creeper>)this.model;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\layers\CreeperPowerLayer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */