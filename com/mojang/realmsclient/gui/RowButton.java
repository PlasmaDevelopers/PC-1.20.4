/*    */ package com.mojang.realmsclient.gui;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ import net.minecraft.client.gui.components.ObjectSelectionList;
/*    */ import net.minecraft.realms.RealmsObjectSelectionList;
/*    */ 
/*    */ public abstract class RowButton
/*    */ {
/*    */   public final int width;
/*    */   public final int height;
/*    */   public final int xOffset;
/*    */   public final int yOffset;
/*    */   
/*    */   public RowButton(int $$0, int $$1, int $$2, int $$3) {
/* 16 */     this.width = $$0;
/* 17 */     this.height = $$1;
/* 18 */     this.xOffset = $$2;
/* 19 */     this.yOffset = $$3;
/*    */   }
/*    */   
/*    */   public void drawForRowAt(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4) {
/* 23 */     int $$5 = $$1 + this.xOffset;
/* 24 */     int $$6 = $$2 + this.yOffset;
/* 25 */     boolean $$7 = ($$3 >= $$5 && $$3 <= $$5 + this.width && $$4 >= $$6 && $$4 <= $$6 + this.height);
/* 26 */     draw($$0, $$5, $$6, $$7);
/*    */   }
/*    */   
/*    */   protected abstract void draw(GuiGraphics paramGuiGraphics, int paramInt1, int paramInt2, boolean paramBoolean);
/*    */   
/*    */   public int getRight() {
/* 32 */     return this.xOffset + this.width;
/*    */   }
/*    */   
/*    */   public int getBottom() {
/* 36 */     return this.yOffset + this.height;
/*    */   }
/*    */   
/*    */   public abstract void onClick(int paramInt);
/*    */   
/*    */   public static void drawButtonsInRow(GuiGraphics $$0, List<RowButton> $$1, RealmsObjectSelectionList<?> $$2, int $$3, int $$4, int $$5, int $$6) {
/* 42 */     for (RowButton $$7 : $$1) {
/* 43 */       if ($$2.getRowWidth() > $$7.getRight()) {
/* 44 */         $$7.drawForRowAt($$0, $$3, $$4, $$5, $$6);
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void rowButtonMouseClicked(RealmsObjectSelectionList<?> $$0, ObjectSelectionList.Entry<?> $$1, List<RowButton> $$2, int $$3, double $$4, double $$5) {
/* 50 */     int $$6 = $$0.children().indexOf($$1);
/* 51 */     if ($$6 > -1) {
/*    */       
/* 53 */       $$0.selectItem($$6);
/* 54 */       int $$7 = $$0.getRowLeft();
/* 55 */       int $$8 = $$0.getRowTop($$6);
/* 56 */       int $$9 = (int)($$4 - $$7);
/* 57 */       int $$10 = (int)($$5 - $$8);
/*    */       
/* 59 */       for (RowButton $$11 : $$2) {
/* 60 */         if ($$9 >= $$11.xOffset && $$9 <= $$11.getRight() && $$10 >= $$11.yOffset && $$10 <= $$11.getBottom())
/* 61 */           $$11.onClick($$6); 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\gui\RowButton.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */