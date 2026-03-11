/*    */ package net.minecraft.client.gui.screens.inventory.tooltip;
/*    */ 
/*    */ import net.minecraft.client.gui.navigation.ScreenRectangle;
/*    */ import org.joml.Vector2i;
/*    */ import org.joml.Vector2ic;
/*    */ 
/*    */ public class BelowOrAboveWidgetTooltipPositioner implements ClientTooltipPositioner {
/*    */   private final ScreenRectangle screenRectangle;
/*    */   
/*    */   public BelowOrAboveWidgetTooltipPositioner(ScreenRectangle $$0) {
/* 11 */     this.screenRectangle = $$0;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Vector2ic positionTooltip(int $$0, int $$1, int $$2, int $$3, int $$4, int $$5) {
/* 17 */     Vector2i $$6 = new Vector2i();
/* 18 */     $$6.x = this.screenRectangle.left() + 3;
/* 19 */     $$6.y = this.screenRectangle.bottom() + 3 + 1;
/*    */ 
/*    */     
/* 22 */     if ($$6.y + $$5 + 3 > $$1) {
/* 23 */       $$6.y = this.screenRectangle.top() - $$5 - 3 - 1;
/*    */     }
/*    */ 
/*    */     
/* 27 */     if ($$6.x + $$4 > $$0) {
/* 28 */       $$6.x = Math.max(this.screenRectangle.right() - $$4 - 3, 4);
/*    */     }
/* 30 */     return (Vector2ic)$$6;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\inventory\tooltip\BelowOrAboveWidgetTooltipPositioner.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */