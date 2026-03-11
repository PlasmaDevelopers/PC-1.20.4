/*    */ package net.minecraft.client.gui.screens.inventory.tooltip;
/*    */ 
/*    */ import org.joml.Vector2i;
/*    */ import org.joml.Vector2ic;
/*    */ 
/*    */ public class DefaultTooltipPositioner implements ClientTooltipPositioner {
/*  7 */   public static final ClientTooltipPositioner INSTANCE = new DefaultTooltipPositioner();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Vector2ic positionTooltip(int $$0, int $$1, int $$2, int $$3, int $$4, int $$5) {
/* 17 */     Vector2i $$6 = (new Vector2i($$2, $$3)).add(12, -12);
/* 18 */     positionTooltip($$0, $$1, $$6, $$4, $$5);
/* 19 */     return (Vector2ic)$$6;
/*    */   }
/*    */ 
/*    */   
/*    */   private void positionTooltip(int $$0, int $$1, Vector2i $$2, int $$3, int $$4) {
/* 24 */     if ($$2.x + $$3 > $$0) {
/* 25 */       $$2.x = Math.max($$2.x - 24 - $$3, 4);
/*    */     }
/*    */ 
/*    */     
/* 29 */     int $$5 = $$4 + 3;
/* 30 */     if ($$2.y + $$5 > $$1)
/* 31 */       $$2.y = $$1 - $$5; 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\inventory\tooltip\DefaultTooltipPositioner.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */