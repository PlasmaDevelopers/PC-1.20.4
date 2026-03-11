/*    */ package net.minecraft.client.renderer.blockentity;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.math.Axis;
/*    */ import net.minecraft.client.renderer.LevelRenderer;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.entity.ItemRenderer;
/*    */ import net.minecraft.client.renderer.texture.OverlayTexture;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.world.item.ItemDisplayContext;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.BlockAndTintGetter;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ import net.minecraft.world.level.block.entity.BrushableBlockEntity;
/*    */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ 
/*    */ public class BrushableBlockRenderer implements BlockEntityRenderer<BrushableBlockEntity> {
/*    */   public BrushableBlockRenderer(BlockEntityRendererProvider.Context $$0) {
/* 19 */     this.itemRenderer = $$0.getItemRenderer();
/*    */   }
/*    */   private final ItemRenderer itemRenderer;
/*    */   
/*    */   public void render(BrushableBlockEntity $$0, float $$1, PoseStack $$2, MultiBufferSource $$3, int $$4, int $$5) {
/* 24 */     if ($$0.getLevel() == null) {
/*    */       return;
/*    */     }
/* 27 */     int $$6 = ((Integer)$$0.getBlockState().getValue((Property)BlockStateProperties.DUSTED)).intValue();
/* 28 */     if ($$6 <= 0) {
/*    */       return;
/*    */     }
/* 31 */     Direction $$7 = $$0.getHitDirection();
/* 32 */     if ($$7 == null) {
/*    */       return;
/*    */     }
/*    */     
/* 36 */     ItemStack $$8 = $$0.getItem();
/* 37 */     if ($$8.isEmpty()) {
/*    */       return;
/*    */     }
/*    */     
/* 41 */     $$2.pushPose();
/* 42 */     $$2.translate(0.0F, 0.5F, 0.0F);
/* 43 */     float[] $$9 = translations($$7, $$6);
/*    */     
/* 45 */     $$2.translate($$9[0], $$9[1], $$9[2]);
/* 46 */     $$2.mulPose(Axis.YP.rotationDegrees(75.0F));
/* 47 */     boolean $$10 = ($$7 == Direction.EAST || $$7 == Direction.WEST);
/* 48 */     $$2.mulPose(Axis.YP.rotationDegrees((($$10 ? 90 : 0) + 11)));
/* 49 */     $$2.scale(0.5F, 0.5F, 0.5F);
/*    */     
/* 51 */     int $$11 = LevelRenderer.getLightColor((BlockAndTintGetter)$$0.getLevel(), $$0.getBlockState(), $$0.getBlockPos().relative($$7));
/*    */     
/* 53 */     this.itemRenderer.renderStatic($$8, ItemDisplayContext.FIXED, $$11, OverlayTexture.NO_OVERLAY, $$2, $$3, $$0.getLevel(), 0);
/* 54 */     $$2.popPose();
/*    */   }
/*    */   
/*    */   private float[] translations(Direction $$0, int $$1) {
/* 58 */     float[] $$2 = { 0.5F, 0.0F, 0.5F };
/*    */     
/* 60 */     float $$3 = $$1 / 10.0F * 0.75F;
/* 61 */     switch ($$0) { case EAST:
/* 62 */         $$2[0] = 0.73F + $$3; break;
/* 63 */       case WEST: $$2[0] = 0.25F - $$3; break;
/* 64 */       case UP: $$2[1] = 0.25F + $$3; break;
/* 65 */       case DOWN: $$2[1] = -0.23F - $$3; break;
/* 66 */       case NORTH: $$2[2] = 0.25F - $$3; break;
/* 67 */       case SOUTH: $$2[2] = 0.73F + $$3; break; }
/*    */     
/* 69 */     return $$2;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\blockentity\BrushableBlockRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */