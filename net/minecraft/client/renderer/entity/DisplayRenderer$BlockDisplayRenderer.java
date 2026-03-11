/*     */ package net.minecraft.client.renderer.entity;
/*     */ 
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.renderer.MultiBufferSource;
/*     */ import net.minecraft.client.renderer.block.BlockRenderDispatcher;
/*     */ import net.minecraft.client.renderer.texture.OverlayTexture;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.entity.Display;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockDisplayRenderer
/*     */   extends DisplayRenderer<Display.BlockDisplay, Display.BlockDisplay.BlockRenderState>
/*     */ {
/*     */   private final BlockRenderDispatcher blockRenderer;
/*     */   
/*     */   protected BlockDisplayRenderer(EntityRendererProvider.Context $$0) {
/* 109 */     super($$0);
/* 110 */     this.blockRenderer = $$0.getBlockRenderDispatcher();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected Display.BlockDisplay.BlockRenderState getSubState(Display.BlockDisplay $$0) {
/* 116 */     return $$0.blockRenderState();
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderInner(Display.BlockDisplay $$0, Display.BlockDisplay.BlockRenderState $$1, PoseStack $$2, MultiBufferSource $$3, int $$4, float $$5) {
/* 121 */     this.blockRenderer.renderSingleBlock($$1.blockState(), $$2, $$3, $$4, OverlayTexture.NO_OVERLAY);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\DisplayRenderer$BlockDisplayRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */