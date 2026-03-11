/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ 
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.model.WardenModel;
/*    */ import net.minecraft.client.model.geom.ModelPart;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.RenderType;
/*    */ import net.minecraft.client.renderer.entity.LivingEntityRenderer;
/*    */ import net.minecraft.client.renderer.entity.RenderLayerParent;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.monster.warden.Warden;
/*    */ 
/*    */ public class WardenEmissiveLayer<T extends Warden, M extends WardenModel<T>> extends RenderLayer<T, M> {
/*    */   private final ResourceLocation texture;
/*    */   private final AlphaFunction<T> alphaFunction;
/*    */   private final DrawSelector<T, M> drawSelector;
/*    */   
/*    */   public WardenEmissiveLayer(RenderLayerParent<T, M> $$0, ResourceLocation $$1, AlphaFunction<T> $$2, DrawSelector<T, M> $$3) {
/* 23 */     super($$0);
/* 24 */     this.texture = $$1;
/* 25 */     this.alphaFunction = $$2;
/* 26 */     this.drawSelector = $$3;
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(PoseStack $$0, MultiBufferSource $$1, int $$2, T $$3, float $$4, float $$5, float $$6, float $$7, float $$8, float $$9) {
/* 31 */     if ($$3.isInvisible()) {
/*    */       return;
/*    */     }
/*    */     
/* 35 */     onlyDrawSelectedParts();
/* 36 */     VertexConsumer $$10 = $$1.getBuffer(RenderType.entityTranslucentEmissive(this.texture));
/* 37 */     ((WardenModel)getParentModel()).renderToBuffer($$0, $$10, $$2, LivingEntityRenderer.getOverlayCoords((LivingEntity)$$3, 0.0F), 1.0F, 1.0F, 1.0F, this.alphaFunction.apply($$3, $$6, $$7));
/* 38 */     resetDrawForAllParts();
/*    */   }
/*    */   
/*    */   private void onlyDrawSelectedParts() {
/* 42 */     List<ModelPart> $$0 = this.drawSelector.getPartsToDraw(getParentModel());
/* 43 */     ((WardenModel)getParentModel()).root().getAllParts().forEach($$0 -> $$0.skipDraw = true);
/* 44 */     $$0.forEach($$0 -> $$0.skipDraw = false);
/*    */   }
/*    */   
/*    */   private void resetDrawForAllParts() {
/* 48 */     ((WardenModel)getParentModel()).root().getAllParts().forEach($$0 -> $$0.skipDraw = false);
/*    */   }
/*    */   
/*    */   public static interface AlphaFunction<T extends Warden> {
/*    */     float apply(T param1T, float param1Float1, float param1Float2);
/*    */   }
/*    */   
/*    */   public static interface DrawSelector<T extends Warden, M extends net.minecraft.client.model.EntityModel<T>> {
/*    */     List<ModelPart> getPartsToDraw(M param1M);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\layers\WardenEmissiveLayer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */