/*    */ package com.mojang.blaze3d.font;
/*    */ 
/*    */ public interface SheetGlyphInfo {
/*    */   int getPixelWidth();
/*    */   
/*    */   int getPixelHeight();
/*    */   
/*    */   void upload(int paramInt1, int paramInt2);
/*    */   
/*    */   boolean isColored();
/*    */   
/*    */   float getOversample();
/*    */   
/*    */   default float getLeft() {
/* 15 */     return getBearingX();
/*    */   }
/*    */   
/*    */   default float getRight() {
/* 19 */     return getLeft() + getPixelWidth() / getOversample();
/*    */   }
/*    */   
/*    */   default float getUp() {
/* 23 */     return getBearingY();
/*    */   }
/*    */   
/*    */   default float getDown() {
/* 27 */     return getUp() + getPixelHeight() / getOversample();
/*    */   }
/*    */   
/*    */   default float getBearingX() {
/* 31 */     return 0.0F;
/*    */   }
/*    */   
/*    */   default float getBearingY() {
/* 35 */     return 3.0F;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\font\SheetGlyphInfo.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */