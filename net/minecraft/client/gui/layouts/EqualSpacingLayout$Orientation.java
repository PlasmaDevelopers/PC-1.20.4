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
/* 106 */   HORIZONTAL, VERTICAL;
/*     */   
/*     */   int getPrimaryLength(LayoutElement $$0) {
/* 109 */     switch (EqualSpacingLayout.null.$SwitchMap$net$minecraft$client$gui$layouts$EqualSpacingLayout$Orientation[ordinal()]) { default: throw new IncompatibleClassChangeError();case 1: case 2: break; }  return 
/*     */       
/* 111 */       $$0.getHeight();
/*     */   }
/*     */ 
/*     */   
/*     */   int getPrimaryLength(EqualSpacingLayout.ChildContainer $$0) {
/* 116 */     switch (EqualSpacingLayout.null.$SwitchMap$net$minecraft$client$gui$layouts$EqualSpacingLayout$Orientation[ordinal()]) { default: throw new IncompatibleClassChangeError();case 1: case 2: break; }  return 
/*     */       
/* 118 */       $$0.getHeight();
/*     */   }
/*     */ 
/*     */   
/*     */   int getSecondaryLength(LayoutElement $$0) {
/* 123 */     switch (EqualSpacingLayout.null.$SwitchMap$net$minecraft$client$gui$layouts$EqualSpacingLayout$Orientation[ordinal()]) { default: throw new IncompatibleClassChangeError();case 1: case 2: break; }  return 
/*     */       
/* 125 */       $$0.getWidth();
/*     */   }
/*     */ 
/*     */   
/*     */   int getSecondaryLength(EqualSpacingLayout.ChildContainer $$0) {
/* 130 */     switch (EqualSpacingLayout.null.$SwitchMap$net$minecraft$client$gui$layouts$EqualSpacingLayout$Orientation[ordinal()]) { default: throw new IncompatibleClassChangeError();case 1: case 2: break; }  return 
/*     */       
/* 132 */       $$0.getWidth();
/*     */   }
/*     */ 
/*     */   
/*     */   void setPrimaryPosition(EqualSpacingLayout.ChildContainer $$0, int $$1) {
/* 137 */     switch (EqualSpacingLayout.null.$SwitchMap$net$minecraft$client$gui$layouts$EqualSpacingLayout$Orientation[ordinal()]) { case 1:
/* 138 */         $$0.setX($$1, $$0.getWidth()); break;
/* 139 */       case 2: $$0.setY($$1, $$0.getHeight());
/*     */         break; }
/*     */   
/*     */   }
/*     */   void setSecondaryPosition(EqualSpacingLayout.ChildContainer $$0, int $$1, int $$2) {
/* 144 */     switch (EqualSpacingLayout.null.$SwitchMap$net$minecraft$client$gui$layouts$EqualSpacingLayout$Orientation[ordinal()]) { case 1:
/* 145 */         $$0.setY($$1, $$2); break;
/* 146 */       case 2: $$0.setX($$1, $$2);
/*     */         break; }
/*     */   
/*     */   }
/*     */   int getPrimaryPosition(LayoutElement $$0) {
/* 151 */     switch (EqualSpacingLayout.null.$SwitchMap$net$minecraft$client$gui$layouts$EqualSpacingLayout$Orientation[ordinal()]) { default: throw new IncompatibleClassChangeError();case 1: case 2: break; }  return 
/*     */       
/* 153 */       $$0.getY();
/*     */   }
/*     */ 
/*     */   
/*     */   int getSecondaryPosition(LayoutElement $$0) {
/* 158 */     switch (EqualSpacingLayout.null.$SwitchMap$net$minecraft$client$gui$layouts$EqualSpacingLayout$Orientation[ordinal()]) { default: throw new IncompatibleClassChangeError();case 1: case 2: break; }  return 
/*     */       
/* 160 */       $$0.getX();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\layouts\EqualSpacingLayout$Orientation.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */