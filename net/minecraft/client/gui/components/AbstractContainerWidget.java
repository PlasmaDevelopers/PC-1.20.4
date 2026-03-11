/*    */ package net.minecraft.client.gui.components;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.client.gui.ComponentPath;
/*    */ import net.minecraft.client.gui.components.events.ContainerEventHandler;
/*    */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*    */ import net.minecraft.client.gui.navigation.FocusNavigationEvent;
/*    */ import net.minecraft.network.chat.Component;
/*    */ 
/*    */ public abstract class AbstractContainerWidget
/*    */   extends AbstractWidget
/*    */   implements ContainerEventHandler {
/*    */   @Nullable
/*    */   private GuiEventListener focused;
/*    */   private boolean isDragging;
/*    */   
/*    */   public AbstractContainerWidget(int $$0, int $$1, int $$2, int $$3, Component $$4) {
/* 18 */     super($$0, $$1, $$2, $$3, $$4);
/*    */   }
/*    */ 
/*    */   
/*    */   public final boolean isDragging() {
/* 23 */     return this.isDragging;
/*    */   }
/*    */ 
/*    */   
/*    */   public final void setDragging(boolean $$0) {
/* 28 */     this.isDragging = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public GuiEventListener getFocused() {
/* 34 */     return this.focused;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setFocused(@Nullable GuiEventListener $$0) {
/* 39 */     if (this.focused != null) {
/* 40 */       this.focused.setFocused(false);
/*    */     }
/* 42 */     if ($$0 != null) {
/* 43 */       $$0.setFocused(true);
/*    */     }
/* 45 */     this.focused = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public ComponentPath nextFocusPath(FocusNavigationEvent $$0) {
/* 51 */     return super.nextFocusPath($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean mouseClicked(double $$0, double $$1, int $$2) {
/* 56 */     return super.mouseClicked($$0, $$1, $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean mouseReleased(double $$0, double $$1, int $$2) {
/* 61 */     return super.mouseReleased($$0, $$1, $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean mouseDragged(double $$0, double $$1, int $$2, double $$3, double $$4) {
/* 66 */     return super.mouseDragged($$0, $$1, $$2, $$3, $$4);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isFocused() {
/* 71 */     return super.isFocused();
/*    */   }
/*    */ 
/*    */   
/*    */   public void setFocused(boolean $$0) {
/* 76 */     super.setFocused($$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\AbstractContainerWidget.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */