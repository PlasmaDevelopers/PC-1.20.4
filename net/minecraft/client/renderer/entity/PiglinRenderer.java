/*    */ package net.minecraft.client.renderer.entity;
/*    */ import java.util.Map;
/*    */ import net.minecraft.client.model.HumanoidArmorModel;
/*    */ import net.minecraft.client.model.HumanoidModel;
/*    */ import net.minecraft.client.model.PiglinModel;
/*    */ import net.minecraft.client.model.geom.EntityModelSet;
/*    */ import net.minecraft.client.model.geom.ModelLayerLocation;
/*    */ import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
/*    */ import net.minecraft.client.renderer.entity.layers.RenderLayer;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.Mob;
/*    */ 
/*    */ public class PiglinRenderer extends HumanoidMobRenderer<Mob, PiglinModel<Mob>> {
/* 17 */   private static final Map<EntityType<?>, ResourceLocation> TEXTURES = (Map<EntityType<?>, ResourceLocation>)ImmutableMap.of(EntityType.PIGLIN, new ResourceLocation("textures/entity/piglin/piglin.png"), EntityType.ZOMBIFIED_PIGLIN, new ResourceLocation("textures/entity/piglin/zombified_piglin.png"), EntityType.PIGLIN_BRUTE, new ResourceLocation("textures/entity/piglin/piglin_brute.png"));
/*    */ 
/*    */ 
/*    */   
/*    */   private static final float PIGLIN_CUSTOM_HEAD_SCALE = 1.0019531F;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PiglinRenderer(EntityRendererProvider.Context $$0, ModelLayerLocation $$1, ModelLayerLocation $$2, ModelLayerLocation $$3, boolean $$4) {
/* 27 */     super($$0, createModel($$0.getModelSet(), $$1, $$4), 0.5F, 1.0019531F, 1.0F, 1.0019531F);
/*    */     
/* 29 */     addLayer((RenderLayer<Mob, PiglinModel<Mob>>)new HumanoidArmorLayer(this, (HumanoidModel)new HumanoidArmorModel($$0
/* 30 */             .bakeLayer($$2)), (HumanoidModel)new HumanoidArmorModel($$0
/* 31 */             .bakeLayer($$3)), $$0
/* 32 */           .getModelManager()));
/*    */   }
/*    */ 
/*    */   
/*    */   private static PiglinModel<Mob> createModel(EntityModelSet $$0, ModelLayerLocation $$1, boolean $$2) {
/* 37 */     PiglinModel<Mob> $$3 = new PiglinModel($$0.bakeLayer($$1));
/* 38 */     if ($$2) {
/* 39 */       $$3.rightEar.visible = false;
/*    */     }
/* 41 */     return $$3;
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(Mob $$0) {
/* 46 */     ResourceLocation $$1 = TEXTURES.get($$0.getType());
/* 47 */     if ($$1 == null) {
/* 48 */       throw new IllegalArgumentException("I don't know what texture to use for " + $$0.getType());
/*    */     }
/* 50 */     return $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean isShaking(Mob $$0) {
/* 55 */     return (super.isShaking($$0) || ($$0 instanceof AbstractPiglin && ((AbstractPiglin)$$0).isConverting()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\PiglinRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */