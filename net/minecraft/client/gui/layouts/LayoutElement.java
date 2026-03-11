/*    */ package net.minecraft.client.gui.layouts;
/*    */ 
/*    */ import java.util.function.Consumer;
/*    */ import net.minecraft.client.gui.components.AbstractWidget;
/*    */ import net.minecraft.client.gui.navigation.ScreenRectangle;
/*    */ 
/*    */ public interface LayoutElement
/*    */ {
/*    */   void setX(int paramInt);
/*    */   
/*    */   void setY(int paramInt);
/*    */   
/*    */   int getX();
/*    */   
/*    */   int getY();
/*    */   
/*    */   int getWidth();
/*    */   
/*    */   int getHeight();
/*    */   
/*    */   default ScreenRectangle getRectangle() {
/* 22 */     return new ScreenRectangle(getX(), getY(), getWidth(), getHeight());
/*    */   }
/*    */   
/*    */   default void setPosition(int $$0, int $$1) {
/* 26 */     setX($$0);
/* 27 */     setY($$1);
/*    */   }
/*    */   
/*    */   void visitWidgets(Consumer<AbstractWidget> paramConsumer);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\layouts\LayoutElement.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */