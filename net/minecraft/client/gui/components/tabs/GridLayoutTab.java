/*    */ package net.minecraft.client.gui.components.tabs;
/*    */ 
/*    */ import java.util.function.Consumer;
/*    */ import net.minecraft.client.gui.components.AbstractWidget;
/*    */ import net.minecraft.client.gui.layouts.FrameLayout;
/*    */ import net.minecraft.client.gui.layouts.GridLayout;
/*    */ import net.minecraft.client.gui.layouts.LayoutElement;
/*    */ import net.minecraft.client.gui.navigation.ScreenRectangle;
/*    */ import net.minecraft.network.chat.Component;
/*    */ 
/*    */ public class GridLayoutTab implements Tab {
/*    */   private final Component title;
/* 13 */   protected final GridLayout layout = new GridLayout();
/*    */   
/*    */   public GridLayoutTab(Component $$0) {
/* 16 */     this.title = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public Component getTabTitle() {
/* 21 */     return this.title;
/*    */   }
/*    */ 
/*    */   
/*    */   public void visitChildren(Consumer<AbstractWidget> $$0) {
/* 26 */     this.layout.visitWidgets($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void doLayout(ScreenRectangle $$0) {
/* 31 */     this.layout.arrangeElements();
/* 32 */     FrameLayout.alignInRectangle((LayoutElement)this.layout, $$0, 0.5F, 0.16666667F);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\tabs\GridLayoutTab.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */