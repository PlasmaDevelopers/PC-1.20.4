/*    */ package net.minecraft.client.gui.font.glyphs;
/*    */ 
/*    */ import com.mojang.blaze3d.font.SheetGlyphInfo;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class null
/*    */   implements SheetGlyphInfo
/*    */ {
/*    */   public int getPixelWidth() {
/* 56 */     return SpecialGlyphs.this.image.getWidth();
/*    */   }
/*    */ 
/*    */   
/*    */   public int getPixelHeight() {
/* 61 */     return SpecialGlyphs.this.image.getHeight();
/*    */   }
/*    */ 
/*    */   
/*    */   public float getOversample() {
/* 66 */     return 1.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   public void upload(int $$0, int $$1) {
/* 71 */     SpecialGlyphs.this.image.upload(0, $$0, $$1, false);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isColored() {
/* 76 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\font\glyphs\SpecialGlyphs$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */