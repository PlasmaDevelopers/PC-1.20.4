/*    */ package net.minecraft.client.gui.components.events;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.client.gui.ComponentPath;
/*    */ import net.minecraft.client.gui.components.TabOrderedElement;
/*    */ import net.minecraft.client.gui.navigation.FocusNavigationEvent;
/*    */ import net.minecraft.client.gui.navigation.ScreenRectangle;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface GuiEventListener
/*    */   extends TabOrderedElement
/*    */ {
/*    */   public static final long DOUBLE_CLICK_THRESHOLD_MS = 250L;
/*    */   
/*    */   default void mouseMoved(double $$0, double $$1) {}
/*    */   
/*    */   default boolean mouseClicked(double $$0, double $$1, int $$2) {
/* 21 */     return false;
/*    */   }
/*    */   
/*    */   default boolean mouseReleased(double $$0, double $$1, int $$2) {
/* 25 */     return false;
/*    */   }
/*    */   
/*    */   default boolean mouseDragged(double $$0, double $$1, int $$2, double $$3, double $$4) {
/* 29 */     return false;
/*    */   }
/*    */   
/*    */   default boolean mouseScrolled(double $$0, double $$1, double $$2, double $$3) {
/* 33 */     return false;
/*    */   }
/*    */   
/*    */   default boolean keyPressed(int $$0, int $$1, int $$2) {
/* 37 */     return false;
/*    */   }
/*    */   
/*    */   default boolean keyReleased(int $$0, int $$1, int $$2) {
/* 41 */     return false;
/*    */   }
/*    */   
/*    */   default boolean charTyped(char $$0, int $$1) {
/* 45 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   default ComponentPath nextFocusPath(FocusNavigationEvent $$0) {
/* 56 */     return null;
/*    */   }
/*    */   
/*    */   default boolean isMouseOver(double $$0, double $$1) {
/* 60 */     return false;
/*    */   }
/*    */   
/*    */   void setFocused(boolean paramBoolean);
/*    */   
/*    */   boolean isFocused();
/*    */   
/*    */   @Nullable
/*    */   default ComponentPath getCurrentFocusPath() {
/* 69 */     if (isFocused()) {
/* 70 */       return ComponentPath.leaf(this);
/*    */     }
/* 72 */     return null;
/*    */   }
/*    */   
/*    */   default ScreenRectangle getRectangle() {
/* 76 */     return ScreenRectangle.empty();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\events\GuiEventListener.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */