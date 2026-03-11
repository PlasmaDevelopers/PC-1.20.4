/*    */ package net.minecraft.client.gui.screens.inventory.tooltip;
/*    */ 
/*    */ import net.minecraft.client.gui.Font;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.util.FormattedCharSequence;
/*    */ import org.joml.Matrix4f;
/*    */ 
/*    */ public class ClientTextTooltip
/*    */   implements ClientTooltipComponent {
/*    */   private final FormattedCharSequence text;
/*    */   
/*    */   public ClientTextTooltip(FormattedCharSequence $$0) {
/* 13 */     this.text = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getWidth(Font $$0) {
/* 18 */     return $$0.width(this.text);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getHeight() {
/* 23 */     return 10;
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderText(Font $$0, int $$1, int $$2, Matrix4f $$3, MultiBufferSource.BufferSource $$4) {
/* 28 */     $$0.drawInBatch(this.text, $$1, $$2, -1, true, $$3, (MultiBufferSource)$$4, Font.DisplayMode.NORMAL, 0, 15728880);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\inventory\tooltip\ClientTextTooltip.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */