/*    */ package net.minecraft.client.renderer.blockentity;
/*    */ 
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*    */ import net.minecraft.client.model.ShulkerModel;
/*    */ import net.minecraft.client.model.geom.ModelLayers;
/*    */ import net.minecraft.client.model.geom.ModelPart;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.RenderType;
/*    */ import net.minecraft.client.renderer.Sheets;
/*    */ import net.minecraft.client.resources.model.Material;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.world.item.DyeColor;
/*    */ import net.minecraft.world.level.block.ShulkerBoxBlock;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ 
/*    */ public class ShulkerBoxRenderer
/*    */   implements BlockEntityRenderer<ShulkerBoxBlockEntity> {
/*    */   public ShulkerBoxRenderer(BlockEntityRendererProvider.Context $$0) {
/* 23 */     this.model = new ShulkerModel($$0.bakeLayer(ModelLayers.SHULKER));
/*    */   }
/*    */   private final ShulkerModel<?> model;
/*    */   
/*    */   public void render(ShulkerBoxBlockEntity $$0, float $$1, PoseStack $$2, MultiBufferSource $$3, int $$4, int $$5) {
/*    */     Material $$10;
/* 29 */     Direction $$6 = Direction.UP;
/* 30 */     if ($$0.hasLevel()) {
/* 31 */       BlockState $$7 = $$0.getLevel().getBlockState($$0.getBlockPos());
/* 32 */       if ($$7.getBlock() instanceof ShulkerBoxBlock) {
/* 33 */         $$6 = (Direction)$$7.getValue((Property)ShulkerBoxBlock.FACING);
/*    */       }
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 39 */     DyeColor $$8 = $$0.getColor();
/* 40 */     if ($$8 == null) {
/* 41 */       Material $$9 = Sheets.DEFAULT_SHULKER_TEXTURE_LOCATION;
/*    */     } else {
/* 43 */       $$10 = Sheets.SHULKER_TEXTURE_LOCATION.get($$8.getId());
/*    */     } 
/*    */     
/* 46 */     $$2.pushPose();
/*    */     
/* 48 */     $$2.translate(0.5F, 0.5F, 0.5F);
/* 49 */     float $$11 = 0.9995F;
/* 50 */     $$2.scale(0.9995F, 0.9995F, 0.9995F);
/*    */     
/* 52 */     $$2.mulPose($$6.getRotation());
/*    */     
/* 54 */     $$2.scale(1.0F, -1.0F, -1.0F);
/* 55 */     $$2.translate(0.0F, -1.0F, 0.0F);
/*    */     
/* 57 */     ModelPart $$12 = this.model.getLid();
/* 58 */     $$12.setPos(0.0F, 24.0F - $$0.getProgress($$1) * 0.5F * 16.0F, 0.0F);
/* 59 */     $$12.yRot = 270.0F * $$0.getProgress($$1) * 0.017453292F;
/*    */     
/* 61 */     VertexConsumer $$13 = $$10.buffer($$3, RenderType::entityCutoutNoCull);
/* 62 */     this.model.renderToBuffer($$2, $$13, $$4, $$5, 1.0F, 1.0F, 1.0F, 1.0F);
/* 63 */     $$2.popPose();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\blockentity\ShulkerBoxRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */