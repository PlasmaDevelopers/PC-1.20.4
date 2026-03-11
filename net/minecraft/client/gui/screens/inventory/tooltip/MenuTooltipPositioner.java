/*    */ package net.minecraft.client.gui.screens.inventory.tooltip;
/*    */ 
/*    */ import net.minecraft.client.gui.navigation.ScreenRectangle;
/*    */ import net.minecraft.util.Mth;
/*    */ import org.joml.Vector2i;
/*    */ import org.joml.Vector2ic;
/*    */ 
/*    */ public class MenuTooltipPositioner implements ClientTooltipPositioner {
/*    */   private static final int MARGIN = 5;
/*    */   private static final int MOUSE_OFFSET_X = 12;
/*    */   public static final int MAX_OVERLAP_WITH_WIDGET = 3;
/*    */   public static final int MAX_DISTANCE_TO_WIDGET = 5;
/*    */   private final ScreenRectangle screenRectangle;
/*    */   
/*    */   public MenuTooltipPositioner(ScreenRectangle $$0) {
/* 16 */     this.screenRectangle = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public Vector2ic positionTooltip(int $$0, int $$1, int $$2, int $$3, int $$4, int $$5) {
/* 21 */     Vector2i $$6 = new Vector2i($$2 + 12, $$3);
/*    */     
/* 23 */     if ($$6.x + $$4 > $$0 - 5) {
/* 24 */       $$6.x = Math.max($$2 - 12 - $$4, 9);
/*    */     }
/*    */     
/* 27 */     $$6.y += 3;
/*    */     
/* 29 */     int $$7 = $$5 + 3 + 3;
/*    */     
/* 31 */     int $$8 = this.screenRectangle.bottom() + 3 + getOffset(0, 0, this.screenRectangle.height());
/* 32 */     int $$9 = $$1 - 5;
/* 33 */     if ($$8 + $$7 <= $$9) {
/*    */       
/* 35 */       $$6.y += getOffset($$6.y, this.screenRectangle.top(), this.screenRectangle.height());
/*    */     } else {
/*    */       
/* 38 */       $$6.y -= $$7 + getOffset($$6.y, this.screenRectangle.bottom(), this.screenRectangle.height());
/*    */     } 
/* 40 */     return (Vector2ic)$$6;
/*    */   }
/*    */   
/*    */   private static int getOffset(int $$0, int $$1, int $$2) {
/* 44 */     int $$3 = Math.min(Math.abs($$0 - $$1), $$2);
/* 45 */     return Math.round(Mth.lerp($$3 / $$2, ($$2 - 3), 5.0F));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\inventory\tooltip\MenuTooltipPositioner.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */