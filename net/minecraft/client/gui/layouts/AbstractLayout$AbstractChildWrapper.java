/*    */ package net.minecraft.client.gui.layouts;
/*    */ 
/*    */ import net.minecraft.util.Mth;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class AbstractChildWrapper
/*    */ {
/*    */   public final LayoutElement child;
/*    */   public final LayoutSettings.LayoutSettingsImpl layoutSettings;
/*    */   
/*    */   protected AbstractChildWrapper(LayoutElement $$0, LayoutSettings $$1) {
/* 61 */     this.child = $$0;
/* 62 */     this.layoutSettings = $$1.getExposed();
/*    */   }
/*    */   
/*    */   public int getHeight() {
/* 66 */     return this.child.getHeight() + this.layoutSettings.paddingTop + this.layoutSettings.paddingBottom;
/*    */   }
/*    */   
/*    */   public int getWidth() {
/* 70 */     return this.child.getWidth() + this.layoutSettings.paddingLeft + this.layoutSettings.paddingRight;
/*    */   }
/*    */   
/*    */   public void setX(int $$0, int $$1) {
/* 74 */     float $$2 = this.layoutSettings.paddingLeft;
/* 75 */     float $$3 = ($$1 - this.child.getWidth() - this.layoutSettings.paddingRight);
/* 76 */     int $$4 = (int)Mth.lerp(this.layoutSettings.xAlignment, $$2, $$3);
/* 77 */     this.child.setX($$4 + $$0);
/*    */   }
/*    */   
/*    */   public void setY(int $$0, int $$1) {
/* 81 */     float $$2 = this.layoutSettings.paddingTop;
/* 82 */     float $$3 = ($$1 - this.child.getHeight() - this.layoutSettings.paddingBottom);
/* 83 */     int $$4 = Math.round(Mth.lerp(this.layoutSettings.yAlignment, $$2, $$3));
/* 84 */     this.child.setY($$4 + $$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\layouts\AbstractLayout$AbstractChildWrapper.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */