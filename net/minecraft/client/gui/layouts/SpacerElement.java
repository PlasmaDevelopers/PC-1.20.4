/*    */ package net.minecraft.client.gui.layouts;
/*    */ 
/*    */ import java.util.function.Consumer;
/*    */ import net.minecraft.client.gui.components.AbstractWidget;
/*    */ 
/*    */ public class SpacerElement
/*    */   implements LayoutElement {
/*    */   private int x;
/*    */   private int y;
/*    */   private final int width;
/*    */   private final int height;
/*    */   
/*    */   public SpacerElement(int $$0, int $$1) {
/* 14 */     this(0, 0, $$0, $$1);
/*    */   }
/*    */   
/*    */   public SpacerElement(int $$0, int $$1, int $$2, int $$3) {
/* 18 */     this.x = $$0;
/* 19 */     this.y = $$1;
/* 20 */     this.width = $$2;
/* 21 */     this.height = $$3;
/*    */   }
/*    */   
/*    */   public static SpacerElement width(int $$0) {
/* 25 */     return new SpacerElement($$0, 0);
/*    */   }
/*    */   
/*    */   public static SpacerElement height(int $$0) {
/* 29 */     return new SpacerElement(0, $$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setX(int $$0) {
/* 34 */     this.x = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setY(int $$0) {
/* 39 */     this.y = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getX() {
/* 44 */     return this.x;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getY() {
/* 49 */     return this.y;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getWidth() {
/* 54 */     return this.width;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getHeight() {
/* 59 */     return this.height;
/*    */   }
/*    */   
/*    */   public void visitWidgets(Consumer<AbstractWidget> $$0) {}
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\layouts\SpacerElement.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */