/*     */ package net.minecraft.client.gui.layouts;
/*     */ 
/*     */ import net.minecraft.util.Mth;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class RowHelper
/*     */ {
/*     */   private final int columns;
/*     */   private int index;
/*     */   
/*     */   RowHelper(int $$1) {
/* 177 */     this.columns = $$1;
/*     */   }
/*     */   
/*     */   public <T extends LayoutElement> T addChild(T $$0) {
/* 181 */     return addChild($$0, 1);
/*     */   }
/*     */   
/*     */   public <T extends LayoutElement> T addChild(T $$0, int $$1) {
/* 185 */     return addChild($$0, $$1, defaultCellSetting());
/*     */   }
/*     */   
/*     */   public <T extends LayoutElement> T addChild(T $$0, LayoutSettings $$1) {
/* 189 */     return addChild($$0, 1, $$1);
/*     */   }
/*     */   
/*     */   public <T extends LayoutElement> T addChild(T $$0, int $$1, LayoutSettings $$2) {
/* 193 */     int $$3 = this.index / this.columns;
/* 194 */     int $$4 = this.index % this.columns;
/*     */     
/* 196 */     if ($$4 + $$1 > this.columns) {
/* 197 */       $$3++;
/* 198 */       $$4 = 0;
/* 199 */       this.index = Mth.roundToward(this.index, this.columns);
/*     */     } 
/* 201 */     this.index += $$1;
/*     */     
/* 203 */     return GridLayout.this.addChild($$0, $$3, $$4, 1, $$1, $$2);
/*     */   }
/*     */   
/*     */   public GridLayout getGrid() {
/* 207 */     return GridLayout.this;
/*     */   }
/*     */   
/*     */   public LayoutSettings newCellSettings() {
/* 211 */     return GridLayout.this.newCellSettings();
/*     */   }
/*     */   
/*     */   public LayoutSettings defaultCellSetting() {
/* 215 */     return GridLayout.this.defaultCellSetting();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\layouts\GridLayout$RowHelper.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */