/*    */ package net.minecraft.realms;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.components.AbstractSelectionList;
/*    */ import net.minecraft.client.gui.components.ObjectSelectionList;
/*    */ 
/*    */ public abstract class RealmsObjectSelectionList<E extends ObjectSelectionList.Entry<E>> extends ObjectSelectionList<E> {
/*    */   protected RealmsObjectSelectionList(int $$0, int $$1, int $$2, int $$3) {
/* 10 */     super(Minecraft.getInstance(), $$0, $$1, $$2, $$3);
/*    */   }
/*    */   
/*    */   public void setSelectedItem(int $$0) {
/* 14 */     if ($$0 == -1) {
/* 15 */       setSelected(null);
/* 16 */     } else if (super.getItemCount() != 0) {
/* 17 */       setSelected(getEntry($$0));
/*    */     } 
/*    */   }
/*    */   
/*    */   public void selectItem(int $$0) {
/* 22 */     setSelectedItem($$0);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMaxPosition() {
/* 28 */     return 0;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getScrollbarPosition() {
/* 34 */     return getRowLeft() + getRowWidth();
/*    */   }
/*    */ 
/*    */   
/*    */   public int getRowWidth() {
/* 39 */     return (int)(this.width * 0.6D);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void replaceEntries(Collection<E> $$0) {
/* 45 */     super.replaceEntries($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getItemCount() {
/* 50 */     return super.getItemCount();
/*    */   }
/*    */ 
/*    */   
/*    */   public int getRowTop(int $$0) {
/* 55 */     return super.getRowTop($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getRowLeft() {
/* 60 */     return super.getRowLeft();
/*    */   }
/*    */ 
/*    */   
/*    */   public int addEntry(E $$0) {
/* 65 */     return super.addEntry((AbstractSelectionList.Entry)$$0);
/*    */   }
/*    */   
/*    */   public void clear() {
/* 69 */     clearEntries();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\realms\RealmsObjectSelectionList.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */