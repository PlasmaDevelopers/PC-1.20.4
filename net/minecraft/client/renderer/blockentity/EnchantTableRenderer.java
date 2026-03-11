/*    */ package net.minecraft.client.renderer.blockentity;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*    */ import com.mojang.math.Axis;
/*    */ import net.minecraft.client.model.BookModel;
/*    */ import net.minecraft.client.model.geom.ModelLayers;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.RenderType;
/*    */ import net.minecraft.client.renderer.texture.TextureAtlas;
/*    */ import net.minecraft.client.resources.model.Material;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ import net.minecraft.world.level.block.entity.EnchantmentTableBlockEntity;
/*    */ 
/*    */ public class EnchantTableRenderer implements BlockEntityRenderer<EnchantmentTableBlockEntity> {
/* 17 */   public static final Material BOOK_LOCATION = new Material(TextureAtlas.LOCATION_BLOCKS, new ResourceLocation("entity/enchanting_table_book"));
/*    */   
/*    */   private final BookModel bookModel;
/*    */   
/*    */   public EnchantTableRenderer(BlockEntityRendererProvider.Context $$0) {
/* 22 */     this.bookModel = new BookModel($$0.bakeLayer(ModelLayers.BOOK));
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(EnchantmentTableBlockEntity $$0, float $$1, PoseStack $$2, MultiBufferSource $$3, int $$4, int $$5) {
/* 27 */     $$2.pushPose();
/* 28 */     $$2.translate(0.5F, 0.75F, 0.5F);
/*    */     
/* 30 */     float $$6 = $$0.time + $$1;
/*    */     
/* 32 */     $$2.translate(0.0F, 0.1F + Mth.sin($$6 * 0.1F) * 0.01F, 0.0F);
/*    */     
/* 34 */     float $$7 = $$0.rot - $$0.oRot;
/*    */     
/* 36 */     while ($$7 >= 3.1415927F) {
/* 37 */       $$7 -= 6.2831855F;
/*    */     }
/* 39 */     while ($$7 < -3.1415927F) {
/* 40 */       $$7 += 6.2831855F;
/*    */     }
/*    */     
/* 43 */     float $$8 = $$0.oRot + $$7 * $$1;
/*    */     
/* 45 */     $$2.mulPose(Axis.YP.rotation(-$$8));
/* 46 */     $$2.mulPose(Axis.ZP.rotationDegrees(80.0F));
/*    */     
/* 48 */     float $$9 = Mth.lerp($$1, $$0.oFlip, $$0.flip);
/* 49 */     float $$10 = Mth.frac($$9 + 0.25F) * 1.6F - 0.3F;
/* 50 */     float $$11 = Mth.frac($$9 + 0.75F) * 1.6F - 0.3F;
/*    */     
/* 52 */     float $$12 = Mth.lerp($$1, $$0.oOpen, $$0.open);
/*    */     
/* 54 */     this.bookModel.setupAnim($$6, Mth.clamp($$10, 0.0F, 1.0F), Mth.clamp($$11, 0.0F, 1.0F), $$12);
/* 55 */     VertexConsumer $$13 = BOOK_LOCATION.buffer($$3, RenderType::entitySolid);
/* 56 */     this.bookModel.render($$2, $$13, $$4, $$5, 1.0F, 1.0F, 1.0F, 1.0F);
/* 57 */     $$2.popPose();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\blockentity\EnchantTableRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */