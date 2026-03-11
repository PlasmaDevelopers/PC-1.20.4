/*    */ package net.minecraft.client.gui.layouts;
/*    */ 
/*    */ import java.util.function.Consumer;
/*    */ import net.minecraft.client.gui.components.AbstractWidget;
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface Layout
/*    */   extends LayoutElement
/*    */ {
/*    */   default void visitWidgets(Consumer<AbstractWidget> $$0) {
/* 12 */     visitChildren($$1 -> $$1.visitWidgets($$0));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default void arrangeElements() {
/* 22 */     visitChildren($$0 -> {
/*    */           if ($$0 instanceof Layout) {
/*    */             Layout $$1 = (Layout)$$0;
/*    */             $$1.arrangeElements();
/*    */           } 
/*    */         });
/*    */   }
/*    */   
/*    */   void visitChildren(Consumer<LayoutElement> paramConsumer);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\layouts\Layout.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */