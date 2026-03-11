/*    */ package net.minecraft.client.renderer.entity;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import net.minecraft.client.model.GiantZombieModel;
/*    */ import net.minecraft.client.model.HumanoidModel;
/*    */ import net.minecraft.client.model.geom.ModelLayers;
/*    */ import net.minecraft.client.renderer.entity.layers.RenderLayer;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.monster.Giant;
/*    */ 
/*    */ public class GiantMobRenderer extends MobRenderer<Giant, HumanoidModel<Giant>> {
/* 13 */   private static final ResourceLocation ZOMBIE_LOCATION = new ResourceLocation("textures/entity/zombie/zombie.png");
/*    */   
/*    */   private final float scale;
/*    */   
/*    */   public GiantMobRenderer(EntityRendererProvider.Context $$0, float $$1) {
/* 18 */     super($$0, new GiantZombieModel($$0.bakeLayer(ModelLayers.GIANT)), 0.5F * $$1);
/*    */     
/* 20 */     this.scale = $$1;
/*    */     
/* 22 */     addLayer((RenderLayer<Giant, HumanoidModel<Giant>>)new ItemInHandLayer(this, $$0.getItemInHandRenderer()));
/* 23 */     addLayer((RenderLayer<Giant, HumanoidModel<Giant>>)new HumanoidArmorLayer(this, (HumanoidModel)new GiantZombieModel($$0
/* 24 */             .bakeLayer(ModelLayers.GIANT_INNER_ARMOR)), (HumanoidModel)new GiantZombieModel($$0
/* 25 */             .bakeLayer(ModelLayers.GIANT_OUTER_ARMOR)), $$0
/* 26 */           .getModelManager()));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void scale(Giant $$0, PoseStack $$1, float $$2) {
/* 32 */     $$1.scale(this.scale, this.scale, this.scale);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(Giant $$0) {
/* 37 */     return ZOMBIE_LOCATION;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\GiantMobRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */