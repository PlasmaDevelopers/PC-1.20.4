/*    */ package net.minecraft.client.renderer.blockentity;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*    */ import net.minecraft.client.renderer.ItemBlockRenderTypes;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.RenderType;
/*    */ import net.minecraft.client.renderer.block.BlockRenderDispatcher;
/*    */ import net.minecraft.client.renderer.block.ModelBlockRenderer;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.BlockAndTintGetter;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ import net.minecraft.world.level.block.piston.PistonBaseBlock;
/*    */ import net.minecraft.world.level.block.piston.PistonHeadBlock;
/*    */ import net.minecraft.world.level.block.piston.PistonMovingBlockEntity;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.properties.PistonType;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ 
/*    */ public class PistonHeadRenderer implements BlockEntityRenderer<PistonMovingBlockEntity> {
/*    */   public PistonHeadRenderer(BlockEntityRendererProvider.Context $$0) {
/* 24 */     this.blockRenderer = $$0.getBlockRenderDispatcher();
/*    */   }
/*    */   private final BlockRenderDispatcher blockRenderer;
/*    */   
/*    */   public void render(PistonMovingBlockEntity $$0, float $$1, PoseStack $$2, MultiBufferSource $$3, int $$4, int $$5) {
/* 29 */     Level $$6 = $$0.getLevel();
/* 30 */     if ($$6 == null) {
/*    */       return;
/*    */     }
/* 33 */     BlockPos $$7 = $$0.getBlockPos().relative($$0.getMovementDirection().getOpposite());
/* 34 */     BlockState $$8 = $$0.getMovedState();
/* 35 */     if ($$8.isAir()) {
/*    */       return;
/*    */     }
/*    */     
/* 39 */     ModelBlockRenderer.enableCaching();
/* 40 */     $$2.pushPose();
/* 41 */     $$2.translate($$0.getXOff($$1), $$0.getYOff($$1), $$0.getZOff($$1));
/* 42 */     if ($$8.is(Blocks.PISTON_HEAD) && $$0.getProgress($$1) <= 4.0F) {
/*    */       
/* 44 */       $$8 = (BlockState)$$8.setValue((Property)PistonHeadBlock.SHORT, Boolean.valueOf(($$0.getProgress($$1) <= 0.5F)));
/* 45 */       renderBlock($$7, $$8, $$2, $$3, $$6, false, $$5);
/* 46 */     } else if ($$0.isSourcePiston() && !$$0.isExtending()) {
/*    */       
/* 48 */       PistonType $$9 = $$8.is(Blocks.STICKY_PISTON) ? PistonType.STICKY : PistonType.DEFAULT;
/* 49 */       BlockState $$10 = (BlockState)((BlockState)Blocks.PISTON_HEAD.defaultBlockState().setValue((Property)PistonHeadBlock.TYPE, (Comparable)$$9)).setValue((Property)PistonHeadBlock.FACING, $$8.getValue((Property)PistonBaseBlock.FACING));
/* 50 */       $$10 = (BlockState)$$10.setValue((Property)PistonHeadBlock.SHORT, Boolean.valueOf(($$0.getProgress($$1) >= 0.5F)));
/* 51 */       renderBlock($$7, $$10, $$2, $$3, $$6, false, $$5);
/*    */ 
/*    */       
/* 54 */       BlockPos $$11 = $$7.relative($$0.getMovementDirection());
/* 55 */       $$2.popPose();
/* 56 */       $$2.pushPose();
/* 57 */       $$8 = (BlockState)$$8.setValue((Property)PistonBaseBlock.EXTENDED, Boolean.valueOf(true));
/* 58 */       renderBlock($$11, $$8, $$2, $$3, $$6, true, $$5);
/*    */     } else {
/* 60 */       renderBlock($$7, $$8, $$2, $$3, $$6, false, $$5);
/*    */     } 
/* 62 */     $$2.popPose();
/* 63 */     ModelBlockRenderer.clearCache();
/*    */   }
/*    */   
/*    */   private void renderBlock(BlockPos $$0, BlockState $$1, PoseStack $$2, MultiBufferSource $$3, Level $$4, boolean $$5, int $$6) {
/* 67 */     RenderType $$7 = ItemBlockRenderTypes.getMovingBlockRenderType($$1);
/* 68 */     VertexConsumer $$8 = $$3.getBuffer($$7);
/*    */     
/* 70 */     this.blockRenderer.getModelRenderer().tesselateBlock((BlockAndTintGetter)$$4, this.blockRenderer.getBlockModel($$1), $$1, $$0, $$2, $$8, $$5, RandomSource.create(), $$1.getSeed($$0), $$6);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getViewDistance() {
/* 76 */     return 68;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\blockentity\PistonHeadRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */