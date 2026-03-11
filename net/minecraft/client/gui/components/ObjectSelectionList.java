/*    */ package net.minecraft.client.gui.components;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.ComponentPath;
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ import net.minecraft.client.gui.narration.NarratedElementType;
/*    */ import net.minecraft.client.gui.narration.NarrationElementOutput;
/*    */ import net.minecraft.client.gui.narration.NarrationSupplier;
/*    */ import net.minecraft.client.gui.navigation.FocusNavigationEvent;
/*    */ import net.minecraft.network.chat.Component;
/*    */ 
/*    */ public abstract class ObjectSelectionList<E extends ObjectSelectionList.Entry<E>> extends AbstractSelectionList<E> {
/* 14 */   private static final Component USAGE_NARRATION = (Component)Component.translatable("narration.selection.usage");
/*    */   
/*    */   public ObjectSelectionList(Minecraft $$0, int $$1, int $$2, int $$3, int $$4) {
/* 17 */     super($$0, $$1, $$2, $$3, $$4);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public ComponentPath nextFocusPath(FocusNavigationEvent $$0) {
/* 23 */     if (getItemCount() == 0)
/*    */     {
/* 25 */       return null;
/*    */     }
/*    */     
/* 28 */     if (isFocused() && $$0 instanceof FocusNavigationEvent.ArrowNavigation) { FocusNavigationEvent.ArrowNavigation $$1 = (FocusNavigationEvent.ArrowNavigation)$$0;
/* 29 */       Entry entry = (Entry)nextEntry($$1.direction());
/* 30 */       if (entry != null) {
/* 31 */         return ComponentPath.path(this, ComponentPath.leaf(entry));
/*    */       }
/* 33 */       return null; }
/*    */ 
/*    */ 
/*    */     
/* 37 */     if (!isFocused()) {
/* 38 */       Entry entry = (Entry)getSelected();
/* 39 */       if (entry == null) {
/* 40 */         entry = (Entry)nextEntry($$0.getVerticalDirectionForInitialFocus());
/*    */       }
/* 42 */       if (entry == null) {
/* 43 */         return null;
/*    */       }
/* 45 */       return ComponentPath.path(this, ComponentPath.leaf(entry));
/*    */     } 
/* 47 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateWidgetNarration(NarrationElementOutput $$0) {
/* 52 */     Entry entry = (Entry)getHovered();
/* 53 */     if (entry != null) {
/* 54 */       narrateListElementPosition($$0.nest(), (E)entry);
/* 55 */       entry.updateNarration($$0);
/*    */     } else {
/* 57 */       Entry entry1 = (Entry)getSelected();
/* 58 */       if (entry1 != null) {
/* 59 */         narrateListElementPosition($$0.nest(), (E)entry1);
/* 60 */         entry1.updateNarration($$0);
/*    */       } 
/*    */     } 
/* 63 */     if (isFocused()) {
/* 64 */       $$0.add(NarratedElementType.USAGE, USAGE_NARRATION);
/*    */     }
/*    */   }
/*    */   
/*    */   public static abstract class Entry<E extends Entry<E>>
/*    */     extends AbstractSelectionList.Entry<E>
/*    */     implements NarrationSupplier
/*    */   {
/*    */     public void updateNarration(NarrationElementOutput $$0) {
/* 73 */       $$0.add(NarratedElementType.TITLE, getNarration());
/*    */     }
/*    */     
/*    */     public abstract Component getNarration();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\ObjectSelectionList.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */