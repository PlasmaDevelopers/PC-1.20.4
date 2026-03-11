/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.math.Axis;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.SnowGolemModel;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.RenderType;
/*    */ import net.minecraft.client.renderer.block.BlockRenderDispatcher;
/*    */ import net.minecraft.client.renderer.entity.ItemRenderer;
/*    */ import net.minecraft.client.renderer.entity.LivingEntityRenderer;
/*    */ import net.minecraft.client.renderer.entity.RenderLayerParent;
/*    */ import net.minecraft.client.renderer.texture.TextureAtlas;
/*    */ import net.minecraft.client.resources.model.BakedModel;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.animal.SnowGolem;
/*    */ import net.minecraft.world.item.ItemDisplayContext;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class SnowGolemHeadLayer extends RenderLayer<SnowGolem, SnowGolemModel<SnowGolem>> {
/*    */   private final BlockRenderDispatcher blockRenderer;
/*    */   
/*    */   public SnowGolemHeadLayer(RenderLayerParent<SnowGolem, SnowGolemModel<SnowGolem>> $$0, BlockRenderDispatcher $$1, ItemRenderer $$2) {
/* 26 */     super($$0);
/* 27 */     this.blockRenderer = $$1;
/* 28 */     this.itemRenderer = $$2;
/*    */   }
/*    */   private final ItemRenderer itemRenderer;
/*    */   
/*    */   public void render(PoseStack $$0, MultiBufferSource $$1, int $$2, SnowGolem $$3, float $$4, float $$5, float $$6, float $$7, float $$8, float $$9) {
/* 33 */     if (!$$3.hasPumpkin()) {
/*    */       return;
/*    */     }
/*    */     
/* 37 */     boolean $$10 = (Minecraft.getInstance().shouldEntityAppearGlowing((Entity)$$3) && $$3.isInvisible());
/*    */     
/* 39 */     if ($$3.isInvisible() && !$$10) {
/*    */       return;
/*    */     }
/*    */     
/* 43 */     $$0.pushPose();
/* 44 */     getParentModel().getHead().translateAndRotate($$0);
/*    */     
/* 46 */     float $$11 = 0.625F;
/* 47 */     $$0.translate(0.0F, -0.34375F, 0.0F);
/* 48 */     $$0.mulPose(Axis.YP.rotationDegrees(180.0F));
/* 49 */     $$0.scale(0.625F, -0.625F, -0.625F);
/*    */     
/* 51 */     ItemStack $$12 = new ItemStack((ItemLike)Blocks.CARVED_PUMPKIN);
/* 52 */     if ($$10) {
/* 53 */       BlockState $$13 = Blocks.CARVED_PUMPKIN.defaultBlockState();
/* 54 */       BakedModel $$14 = this.blockRenderer.getBlockModel($$13);
/* 55 */       int $$15 = LivingEntityRenderer.getOverlayCoords((LivingEntity)$$3, 0.0F);
/*    */       
/* 57 */       $$0.translate(-0.5F, -0.5F, -0.5F);
/* 58 */       this.blockRenderer.getModelRenderer().renderModel($$0.last(), $$1.getBuffer(RenderType.outline(TextureAtlas.LOCATION_BLOCKS)), $$13, $$14, 0.0F, 0.0F, 0.0F, $$2, $$15);
/*    */     } else {
/* 60 */       this.itemRenderer.renderStatic((LivingEntity)$$3, $$12, ItemDisplayContext.HEAD, false, $$0, $$1, $$3.level(), $$2, LivingEntityRenderer.getOverlayCoords((LivingEntity)$$3, 0.0F), $$3.getId());
/*    */     } 
/* 62 */     $$0.popPose();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\layers\SnowGolemHeadLayer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */