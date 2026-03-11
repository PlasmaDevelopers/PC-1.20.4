/*    */ package net.minecraft.client.renderer.entity;
/*    */ 
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.math.Axis;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.block.BlockRenderDispatcher;
/*    */ import net.minecraft.client.renderer.texture.TextureAtlas;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.item.PrimedTnt;
/*    */ 
/*    */ public class TntRenderer
/*    */   extends EntityRenderer<PrimedTnt> {
/*    */   public TntRenderer(EntityRendererProvider.Context $$0) {
/* 16 */     super($$0);
/* 17 */     this.shadowRadius = 0.5F;
/* 18 */     this.blockRenderer = $$0.getBlockRenderDispatcher();
/*    */   }
/*    */   private final BlockRenderDispatcher blockRenderer;
/*    */   
/*    */   public void render(PrimedTnt $$0, float $$1, float $$2, PoseStack $$3, MultiBufferSource $$4, int $$5) {
/* 23 */     $$3.pushPose();
/* 24 */     $$3.translate(0.0F, 0.5F, 0.0F);
/* 25 */     int $$6 = $$0.getFuse();
/* 26 */     if ($$6 - $$2 + 1.0F < 10.0F) {
/* 27 */       float $$7 = 1.0F - ($$6 - $$2 + 1.0F) / 10.0F;
/* 28 */       $$7 = Mth.clamp($$7, 0.0F, 1.0F);
/* 29 */       $$7 *= $$7;
/* 30 */       $$7 *= $$7;
/* 31 */       float $$8 = 1.0F + $$7 * 0.3F;
/* 32 */       $$3.scale($$8, $$8, $$8);
/*    */     } 
/*    */     
/* 35 */     $$3.mulPose(Axis.YP.rotationDegrees(-90.0F));
/* 36 */     $$3.translate(-0.5F, -0.5F, 0.5F);
/* 37 */     $$3.mulPose(Axis.YP.rotationDegrees(90.0F));
/*    */     
/* 39 */     TntMinecartRenderer.renderWhiteSolidBlock(this.blockRenderer, $$0.getBlockState(), $$3, $$4, $$5, ($$6 / 5 % 2 == 0));
/*    */     
/* 41 */     $$3.popPose();
/*    */     
/* 43 */     super.render($$0, $$1, $$2, $$3, $$4, $$5);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(PrimedTnt $$0) {
/* 48 */     return TextureAtlas.LOCATION_BLOCKS;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\TntRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */