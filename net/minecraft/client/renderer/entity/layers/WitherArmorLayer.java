/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ 
/*    */ import net.minecraft.client.model.EntityModel;
/*    */ import net.minecraft.client.model.WitherBossModel;
/*    */ import net.minecraft.client.model.geom.EntityModelSet;
/*    */ import net.minecraft.client.model.geom.ModelLayers;
/*    */ import net.minecraft.client.renderer.entity.RenderLayerParent;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.entity.boss.wither.WitherBoss;
/*    */ 
/*    */ public class WitherArmorLayer extends EnergySwirlLayer<WitherBoss, WitherBossModel<WitherBoss>> {
/* 13 */   private static final ResourceLocation WITHER_ARMOR_LOCATION = new ResourceLocation("textures/entity/wither/wither_armor.png");
/*    */   
/*    */   private final WitherBossModel<WitherBoss> model;
/*    */   
/*    */   public WitherArmorLayer(RenderLayerParent<WitherBoss, WitherBossModel<WitherBoss>> $$0, EntityModelSet $$1) {
/* 18 */     super($$0);
/* 19 */     this.model = new WitherBossModel($$1.bakeLayer(ModelLayers.WITHER_ARMOR));
/*    */   }
/*    */ 
/*    */   
/*    */   protected float xOffset(float $$0) {
/* 24 */     return Mth.cos($$0 * 0.02F) * 3.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   protected ResourceLocation getTextureLocation() {
/* 29 */     return WITHER_ARMOR_LOCATION;
/*    */   }
/*    */ 
/*    */   
/*    */   protected EntityModel<WitherBoss> model() {
/* 34 */     return (EntityModel<WitherBoss>)this.model;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\layers\WitherArmorLayer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */