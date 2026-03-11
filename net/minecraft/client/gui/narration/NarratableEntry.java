/*    */ package net.minecraft.client.gui.narration;
/*    */ 
/*    */ import net.minecraft.client.gui.components.TabOrderedElement;
/*    */ 
/*    */ public interface NarratableEntry extends TabOrderedElement, NarrationSupplier {
/*    */   NarrationPriority narrationPriority();
/*    */   
/*    */   default boolean isActive() {
/*  9 */     return true;
/*    */   }
/*    */   
/*    */   public enum NarrationPriority
/*    */   {
/* 14 */     NONE,
/* 15 */     HOVERED,
/* 16 */     FOCUSED;
/*    */ 
/*    */     
/*    */     public boolean isTerminal() {
/* 20 */       return (this == FOCUSED);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\narration\NarratableEntry.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */