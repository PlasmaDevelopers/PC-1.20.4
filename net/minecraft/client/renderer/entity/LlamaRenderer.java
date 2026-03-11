/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.LlamaModel;
/*    */ import net.minecraft.client.model.geom.ModelLayerLocation;
/*    */ import net.minecraft.client.renderer.entity.layers.LlamaDecorLayer;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.animal.horse.Llama;
/*    */ 
/*    */ public class LlamaRenderer extends MobRenderer<Llama, LlamaModel<Llama>> {
/* 10 */   private static final ResourceLocation CREAMY = new ResourceLocation("textures/entity/llama/creamy.png");
/* 11 */   private static final ResourceLocation WHITE = new ResourceLocation("textures/entity/llama/white.png");
/* 12 */   private static final ResourceLocation BROWN = new ResourceLocation("textures/entity/llama/brown.png");
/* 13 */   private static final ResourceLocation GRAY = new ResourceLocation("textures/entity/llama/gray.png");
/*    */   
/*    */   public LlamaRenderer(EntityRendererProvider.Context $$0, ModelLayerLocation $$1) {
/* 16 */     super($$0, new LlamaModel($$0.bakeLayer($$1)), 0.7F);
/*    */     
/* 18 */     addLayer((RenderLayer<Llama, LlamaModel<Llama>>)new LlamaDecorLayer(this, $$0.getModelSet()));
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(Llama $$0) {
/* 23 */     switch ($$0.getVariant()) { default: throw new IncompatibleClassChangeError();case CREAMY: case WHITE: case BROWN: case GRAY: break; }  return 
/*    */ 
/*    */ 
/*    */       
/* 27 */       GRAY;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\LlamaRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */