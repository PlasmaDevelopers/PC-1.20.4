/*    */ package net.minecraft.client.renderer.blockentity;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*    */ import com.mojang.math.Axis;
/*    */ import net.minecraft.client.model.BookModel;
/*    */ import net.minecraft.client.model.geom.ModelLayers;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.RenderType;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.world.level.block.LecternBlock;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ import net.minecraft.world.level.block.entity.LecternBlockEntity;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ 
/*    */ public class LecternRenderer implements BlockEntityRenderer<LecternBlockEntity> {
/*    */   public LecternRenderer(BlockEntityRendererProvider.Context $$0) {
/* 18 */     this.bookModel = new BookModel($$0.bakeLayer(ModelLayers.BOOK));
/*    */   }
/*    */   private final BookModel bookModel;
/*    */   
/*    */   public void render(LecternBlockEntity $$0, float $$1, PoseStack $$2, MultiBufferSource $$3, int $$4, int $$5) {
/* 23 */     BlockState $$6 = $$0.getBlockState();
/* 24 */     if (!((Boolean)$$6.getValue((Property)LecternBlock.HAS_BOOK)).booleanValue()) {
/*    */       return;
/*    */     }
/*    */     
/* 28 */     $$2.pushPose();
/* 29 */     $$2.translate(0.5F, 1.0625F, 0.5F);
/*    */     
/* 31 */     float $$7 = ((Direction)$$6.getValue((Property)LecternBlock.FACING)).getClockWise().toYRot();
/*    */     
/* 33 */     $$2.mulPose(Axis.YP.rotationDegrees(-$$7));
/* 34 */     $$2.mulPose(Axis.ZP.rotationDegrees(67.5F));
/*    */     
/* 36 */     $$2.translate(0.0F, -0.125F, 0.0F);
/*    */     
/* 38 */     this.bookModel.setupAnim(0.0F, 0.1F, 0.9F, 1.2F);
/* 39 */     VertexConsumer $$8 = EnchantTableRenderer.BOOK_LOCATION.buffer($$3, RenderType::entitySolid);
/* 40 */     this.bookModel.render($$2, $$8, $$4, $$5, 1.0F, 1.0F, 1.0F, 1.0F);
/*    */     
/* 42 */     $$2.popPose();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\blockentity\LecternRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */