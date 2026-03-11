/*    */ package net.minecraft.client.renderer.entity;
/*    */ 
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import net.minecraft.client.model.geom.ModelLayers;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.block.BlockRenderDispatcher;
/*    */ import net.minecraft.client.renderer.texture.OverlayTexture;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.entity.vehicle.AbstractMinecart;
/*    */ import net.minecraft.world.entity.vehicle.MinecartTNT;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class TntMinecartRenderer
/*    */   extends MinecartRenderer<MinecartTNT> {
/*    */   public TntMinecartRenderer(EntityRendererProvider.Context $$0) {
/* 16 */     super($$0, ModelLayers.TNT_MINECART);
/* 17 */     this.blockRenderer = $$0.getBlockRenderDispatcher();
/*    */   }
/*    */   private final BlockRenderDispatcher blockRenderer;
/*    */   
/*    */   protected void renderMinecartContents(MinecartTNT $$0, float $$1, BlockState $$2, PoseStack $$3, MultiBufferSource $$4, int $$5) {
/* 22 */     int $$6 = $$0.getFuse();
/*    */     
/* 24 */     if ($$6 > -1 && 
/* 25 */       $$6 - $$1 + 1.0F < 10.0F) {
/* 26 */       float $$7 = 1.0F - ($$6 - $$1 + 1.0F) / 10.0F;
/* 27 */       $$7 = Mth.clamp($$7, 0.0F, 1.0F);
/* 28 */       $$7 *= $$7;
/* 29 */       $$7 *= $$7;
/* 30 */       float $$8 = 1.0F + $$7 * 0.3F;
/* 31 */       $$3.scale($$8, $$8, $$8);
/*    */     } 
/*    */ 
/*    */     
/* 35 */     renderWhiteSolidBlock(this.blockRenderer, $$2, $$3, $$4, $$5, ($$6 > -1 && $$6 / 5 % 2 == 0));
/*    */   }
/*    */ 
/*    */   
/*    */   public static void renderWhiteSolidBlock(BlockRenderDispatcher $$0, BlockState $$1, PoseStack $$2, MultiBufferSource $$3, int $$4, boolean $$5) {
/*    */     int $$7;
/* 41 */     if ($$5) {
/* 42 */       int $$6 = OverlayTexture.pack(OverlayTexture.u(1.0F), 10);
/*    */     } else {
/* 44 */       $$7 = OverlayTexture.NO_OVERLAY;
/*    */     } 
/*    */     
/* 47 */     $$0.renderSingleBlock($$1, $$2, $$3, $$4, $$7);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\TntMinecartRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */