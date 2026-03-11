/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*    */ import net.minecraft.client.model.LlamaModel;
/*    */ import net.minecraft.client.model.geom.EntityModelSet;
/*    */ import net.minecraft.client.model.geom.ModelLayers;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.RenderType;
/*    */ import net.minecraft.client.renderer.entity.RenderLayerParent;
/*    */ import net.minecraft.client.renderer.texture.OverlayTexture;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.animal.horse.Llama;
/*    */ import net.minecraft.world.item.DyeColor;
/*    */ 
/*    */ public class LlamaDecorLayer extends RenderLayer<Llama, LlamaModel<Llama>> {
/* 17 */   private static final ResourceLocation[] TEXTURE_LOCATION = new ResourceLocation[] { new ResourceLocation("textures/entity/llama/decor/white.png"), new ResourceLocation("textures/entity/llama/decor/orange.png"), new ResourceLocation("textures/entity/llama/decor/magenta.png"), new ResourceLocation("textures/entity/llama/decor/light_blue.png"), new ResourceLocation("textures/entity/llama/decor/yellow.png"), new ResourceLocation("textures/entity/llama/decor/lime.png"), new ResourceLocation("textures/entity/llama/decor/pink.png"), new ResourceLocation("textures/entity/llama/decor/gray.png"), new ResourceLocation("textures/entity/llama/decor/light_gray.png"), new ResourceLocation("textures/entity/llama/decor/cyan.png"), new ResourceLocation("textures/entity/llama/decor/purple.png"), new ResourceLocation("textures/entity/llama/decor/blue.png"), new ResourceLocation("textures/entity/llama/decor/brown.png"), new ResourceLocation("textures/entity/llama/decor/green.png"), new ResourceLocation("textures/entity/llama/decor/red.png"), new ResourceLocation("textures/entity/llama/decor/black.png") };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 36 */   private static final ResourceLocation TRADER_LLAMA = new ResourceLocation("textures/entity/llama/decor/trader_llama.png");
/*    */   
/*    */   private final LlamaModel<Llama> model;
/*    */   
/*    */   public LlamaDecorLayer(RenderLayerParent<Llama, LlamaModel<Llama>> $$0, EntityModelSet $$1) {
/* 41 */     super($$0);
/* 42 */     this.model = new LlamaModel($$1.bakeLayer(ModelLayers.LLAMA_DECOR));
/*    */   }
/*    */   
/*    */   public void render(PoseStack $$0, MultiBufferSource $$1, int $$2, Llama $$3, float $$4, float $$5, float $$6, float $$7, float $$8, float $$9) {
/*    */     ResourceLocation $$12;
/* 47 */     DyeColor $$10 = $$3.getSwag();
/*    */     
/* 49 */     if ($$10 != null) {
/* 50 */       ResourceLocation $$11 = TEXTURE_LOCATION[$$10.getId()];
/* 51 */     } else if ($$3.isTraderLlama()) {
/* 52 */       $$12 = TRADER_LLAMA;
/*    */     } else {
/*    */       return;
/*    */     } 
/*    */     
/* 57 */     getParentModel().copyPropertiesTo((EntityModel)this.model);
/* 58 */     this.model.setupAnim((AbstractChestedHorse)$$3, $$4, $$5, $$7, $$8, $$9);
/* 59 */     VertexConsumer $$14 = $$1.getBuffer(RenderType.entityCutoutNoCull($$12));
/* 60 */     this.model.renderToBuffer($$0, $$14, $$2, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\layers\LlamaDecorLayer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */