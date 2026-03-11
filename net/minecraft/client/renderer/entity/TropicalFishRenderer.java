/*    */ package net.minecraft.client.renderer.entity;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.math.Axis;
/*    */ import net.minecraft.client.model.ColorableHierarchicalModel;
/*    */ import net.minecraft.client.model.TropicalFishModelA;
/*    */ import net.minecraft.client.model.TropicalFishModelB;
/*    */ import net.minecraft.client.model.geom.ModelLayers;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.Mob;
/*    */ import net.minecraft.world.entity.animal.TropicalFish;
/*    */ 
/*    */ public class TropicalFishRenderer extends MobRenderer<TropicalFish, ColorableHierarchicalModel<TropicalFish>> {
/*    */   private final ColorableHierarchicalModel<TropicalFish> modelA;
/*    */   private final ColorableHierarchicalModel<TropicalFish> modelB;
/* 19 */   private static final ResourceLocation MODEL_A_TEXTURE = new ResourceLocation("textures/entity/fish/tropical_a.png");
/* 20 */   private static final ResourceLocation MODEL_B_TEXTURE = new ResourceLocation("textures/entity/fish/tropical_b.png");
/*    */   
/*    */   public TropicalFishRenderer(EntityRendererProvider.Context $$0) {
/* 23 */     super($$0, new TropicalFishModelA($$0.bakeLayer(ModelLayers.TROPICAL_FISH_SMALL)), 0.15F);
/* 24 */     this.modelA = getModel();
/* 25 */     this.modelB = (ColorableHierarchicalModel<TropicalFish>)new TropicalFishModelB($$0.bakeLayer(ModelLayers.TROPICAL_FISH_LARGE));
/* 26 */     addLayer((RenderLayer<TropicalFish, ColorableHierarchicalModel<TropicalFish>>)new TropicalFishPatternLayer(this, $$0.getModelSet()));
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(TropicalFish $$0) {
/* 31 */     switch ($$0.getVariant().base()) { default: throw new IncompatibleClassChangeError();case SMALL: case LARGE: break; }  return 
/*    */       
/* 33 */       MODEL_B_TEXTURE;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(TropicalFish $$0, float $$1, float $$2, PoseStack $$3, MultiBufferSource $$4, int $$5) {
/* 39 */     switch ($$0.getVariant().base()) { default: throw new IncompatibleClassChangeError();
/*    */       case SMALL: 
/* 41 */       case LARGE: break; }  ColorableHierarchicalModel<TropicalFish> $$6 = this.modelB;
/*    */     
/* 43 */     this.model = $$6;
/*    */     
/* 45 */     float[] $$7 = $$0.getBaseColor().getTextureDiffuseColors();
/* 46 */     $$6.setColor($$7[0], $$7[1], $$7[2]);
/* 47 */     super.render($$0, $$1, $$2, $$3, $$4, $$5);
/* 48 */     $$6.setColor(1.0F, 1.0F, 1.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void setupRotations(TropicalFish $$0, PoseStack $$1, float $$2, float $$3, float $$4) {
/* 53 */     super.setupRotations($$0, $$1, $$2, $$3, $$4);
/*    */     
/* 55 */     float $$5 = 4.3F * Mth.sin(0.6F * $$2);
/* 56 */     $$1.mulPose(Axis.YP.rotationDegrees($$5));
/*    */     
/* 58 */     if (!$$0.isInWater()) {
/* 59 */       $$1.translate(0.2F, 0.1F, 0.0F);
/* 60 */       $$1.mulPose(Axis.ZP.rotationDegrees(90.0F));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\TropicalFishRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */