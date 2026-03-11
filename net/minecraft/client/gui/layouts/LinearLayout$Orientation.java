/*     */ package net.minecraft.client.gui.layouts;
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
/*     */ public enum Orientation
/*     */ {
/*  98 */   HORIZONTAL, VERTICAL;
/*     */   
/*     */   void setSpacing(GridLayout $$0, int $$1) {
/* 101 */     switch (LinearLayout.null.$SwitchMap$net$minecraft$client$gui$layouts$LinearLayout$Orientation[ordinal()]) { case 1:
/* 102 */         $$0.columnSpacing($$1); break;
/* 103 */       case 2: $$0.rowSpacing($$1);
/*     */         break; }
/*     */   
/*     */   }
/*     */   public <T extends LayoutElement> T addChild(GridLayout $$0, T $$1, int $$2, LayoutSettings $$3) {
/* 108 */     switch (LinearLayout.null.$SwitchMap$net$minecraft$client$gui$layouts$LinearLayout$Orientation[ordinal()]) { default: throw new IncompatibleClassChangeError();case 1: case 2: break; }  return 
/*     */       
/* 110 */       $$0.addChild($$1, $$2, 0, $$3);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\layouts\LinearLayout$Orientation.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */