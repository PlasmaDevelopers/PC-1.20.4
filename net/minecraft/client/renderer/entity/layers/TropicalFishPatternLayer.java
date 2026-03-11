/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import net.minecraft.client.model.ColorableHierarchicalModel;
/*    */ import net.minecraft.client.model.EntityModel;
/*    */ import net.minecraft.client.model.TropicalFishModelA;
/*    */ import net.minecraft.client.model.TropicalFishModelB;
/*    */ import net.minecraft.client.model.geom.EntityModelSet;
/*    */ import net.minecraft.client.model.geom.ModelLayers;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.entity.RenderLayerParent;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.animal.TropicalFish;
/*    */ 
/*    */ public class TropicalFishPatternLayer extends RenderLayer<TropicalFish, ColorableHierarchicalModel<TropicalFish>> {
/* 16 */   private static final ResourceLocation KOB_TEXTURE = new ResourceLocation("textures/entity/fish/tropical_a_pattern_1.png");
/* 17 */   private static final ResourceLocation SUNSTREAK_TEXTURE = new ResourceLocation("textures/entity/fish/tropical_a_pattern_2.png");
/* 18 */   private static final ResourceLocation SNOOPER_TEXTURE = new ResourceLocation("textures/entity/fish/tropical_a_pattern_3.png");
/* 19 */   private static final ResourceLocation DASHER_TEXTURE = new ResourceLocation("textures/entity/fish/tropical_a_pattern_4.png");
/* 20 */   private static final ResourceLocation BRINELY_TEXTURE = new ResourceLocation("textures/entity/fish/tropical_a_pattern_5.png");
/* 21 */   private static final ResourceLocation SPOTTY_TEXTURE = new ResourceLocation("textures/entity/fish/tropical_a_pattern_6.png");
/*    */   
/* 23 */   private static final ResourceLocation FLOPPER_TEXTURE = new ResourceLocation("textures/entity/fish/tropical_b_pattern_1.png");
/* 24 */   private static final ResourceLocation STRIPEY_TEXTURE = new ResourceLocation("textures/entity/fish/tropical_b_pattern_2.png");
/* 25 */   private static final ResourceLocation GLITTER_TEXTURE = new ResourceLocation("textures/entity/fish/tropical_b_pattern_3.png");
/* 26 */   private static final ResourceLocation BLOCKFISH_TEXTURE = new ResourceLocation("textures/entity/fish/tropical_b_pattern_4.png");
/* 27 */   private static final ResourceLocation BETTY_TEXTURE = new ResourceLocation("textures/entity/fish/tropical_b_pattern_5.png");
/* 28 */   private static final ResourceLocation CLAYFISH_TEXTURE = new ResourceLocation("textures/entity/fish/tropical_b_pattern_6.png");
/*    */   
/*    */   private final TropicalFishModelA<TropicalFish> modelA;
/*    */   private final TropicalFishModelB<TropicalFish> modelB;
/*    */   
/*    */   public TropicalFishPatternLayer(RenderLayerParent<TropicalFish, ColorableHierarchicalModel<TropicalFish>> $$0, EntityModelSet $$1) {
/* 34 */     super($$0);
/* 35 */     this.modelA = new TropicalFishModelA($$1.bakeLayer(ModelLayers.TROPICAL_FISH_SMALL_PATTERN));
/* 36 */     this.modelB = new TropicalFishModelB($$1.bakeLayer(ModelLayers.TROPICAL_FISH_LARGE_PATTERN));
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(PoseStack $$0, MultiBufferSource $$1, int $$2, TropicalFish $$3, float $$4, float $$5, float $$6, float $$7, float $$8, float $$9) {
/* 41 */     TropicalFish.Pattern $$10 = $$3.getVariant();
/* 42 */     switch ($$10.base()) { default: throw new IncompatibleClassChangeError();
/*    */       case KOB: 
/* 44 */       case SUNSTREAK: break; }  TropicalFishModelB<TropicalFish> tropicalFishModelB = this.modelB;
/*    */     
/* 46 */     switch ($$10) { default: throw new IncompatibleClassChangeError();
/*    */       case KOB: 
/*    */       case SUNSTREAK: 
/*    */       case SNOOPER: 
/*    */       case DASHER: 
/*    */       case BRINELY: 
/*    */       case SPOTTY: 
/*    */       case FLOPPER: 
/*    */       case STRIPEY: 
/*    */       case GLITTER: 
/*    */       case BLOCKFISH: 
/*    */       case BETTY: 
/*    */       case CLAYFISH:
/* 59 */         break; }  ResourceLocation $$12 = CLAYFISH_TEXTURE;
/*    */     
/* 61 */     float[] $$13 = $$3.getPatternColor().getTextureDiffuseColors();
/* 62 */     coloredCutoutModelCopyLayerRender((EntityModel<TropicalFish>)getParentModel(), (EntityModel<TropicalFish>)tropicalFishModelB, $$12, $$0, $$1, $$2, $$3, $$4, $$5, $$7, $$8, $$9, $$6, $$13[0], $$13[1], $$13[2]);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\layers\TropicalFishPatternLayer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */