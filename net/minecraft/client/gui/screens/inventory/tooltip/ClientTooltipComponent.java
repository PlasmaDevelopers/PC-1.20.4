/*    */ package net.minecraft.client.gui.screens.inventory.tooltip;
/*    */ 
/*    */ import net.minecraft.client.gui.Font;
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.util.FormattedCharSequence;
/*    */ import net.minecraft.world.inventory.tooltip.BundleTooltip;
/*    */ import net.minecraft.world.inventory.tooltip.TooltipComponent;
/*    */ import org.joml.Matrix4f;
/*    */ 
/*    */ public interface ClientTooltipComponent
/*    */ {
/*    */   static ClientTooltipComponent create(FormattedCharSequence $$0) {
/* 14 */     return new ClientTextTooltip($$0);
/*    */   }
/*    */   
/*    */   static ClientTooltipComponent create(TooltipComponent $$0) {
/* 18 */     if ($$0 instanceof BundleTooltip) {
/* 19 */       return new ClientBundleTooltip((BundleTooltip)$$0);
/*    */     }
/* 21 */     throw new IllegalArgumentException("Unknown TooltipComponent");
/*    */   }
/*    */   
/*    */   int getHeight();
/*    */   
/*    */   int getWidth(Font paramFont);
/*    */   
/*    */   default void renderText(Font $$0, int $$1, int $$2, Matrix4f $$3, MultiBufferSource.BufferSource $$4) {}
/*    */   
/*    */   default void renderImage(Font $$0, int $$1, int $$2, GuiGraphics $$3) {}
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\inventory\tooltip\ClientTooltipComponent.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */