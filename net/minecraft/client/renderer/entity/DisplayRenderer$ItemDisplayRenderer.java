/*     */ package net.minecraft.client.renderer.entity;
/*     */ 
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.math.Axis;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.renderer.MultiBufferSource;
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
/*     */ public class ItemDisplayRenderer
/*     */   extends DisplayRenderer<Display.ItemDisplay, Display.ItemDisplay.ItemRenderState>
/*     */ {
/*     */   private final ItemRenderer itemRenderer;
/*     */   
/*     */   protected ItemDisplayRenderer(EntityRendererProvider.Context $$0) {
/* 129 */     super($$0);
/* 130 */     this.itemRenderer = $$0.getItemRenderer();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected Display.ItemDisplay.ItemRenderState getSubState(Display.ItemDisplay $$0) {
/* 136 */     return $$0.itemRenderState();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderInner(Display.ItemDisplay $$0, Display.ItemDisplay.ItemRenderState $$1, PoseStack $$2, MultiBufferSource $$3, int $$4, float $$5) {
/* 142 */     $$2.mulPose(Axis.YP.rotation(3.1415927F));
/* 143 */     this.itemRenderer.renderStatic($$1.itemStack(), $$1.itemTransform(), $$4, OverlayTexture.NO_OVERLAY, $$2, $$3, $$0.level(), $$0.getId());
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\DisplayRenderer$ItemDisplayRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */