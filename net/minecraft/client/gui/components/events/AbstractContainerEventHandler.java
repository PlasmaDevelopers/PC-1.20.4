/*    */ package net.minecraft.client.gui.components.events;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class AbstractContainerEventHandler
/*    */   implements ContainerEventHandler
/*    */ {
/*    */   @Nullable
/*    */   private GuiEventListener focused;
/*    */   private boolean isDragging;
/*    */   
/*    */   public final boolean isDragging() {
/* 19 */     return this.isDragging;
/*    */   }
/*    */ 
/*    */   
/*    */   public final void setDragging(boolean $$0) {
/* 24 */     this.isDragging = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public GuiEventListener getFocused() {
/* 30 */     return this.focused;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setFocused(@Nullable GuiEventListener $$0) {
/* 35 */     if (this.focused != null) {
/* 36 */       this.focused.setFocused(false);
/*    */     }
/* 38 */     if ($$0 != null) {
/* 39 */       $$0.setFocused(true);
/*    */     }
/* 41 */     this.focused = $$0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\events\AbstractContainerEventHandler.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */