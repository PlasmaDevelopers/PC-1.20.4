/*     */ package com.mojang.blaze3d.font;
/*     */ 
/*     */ import com.mojang.blaze3d.platform.NativeImage;
/*     */ import org.lwjgl.stb.STBTTFontinfo;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class null
/*     */   implements SheetGlyphInfo
/*     */ {
/*     */   public int getPixelWidth() {
/* 148 */     return TrueTypeGlyphProvider.Glyph.this.width;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPixelHeight() {
/* 153 */     return TrueTypeGlyphProvider.Glyph.this.height;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getOversample() {
/* 158 */     return this.this$1.this$0.oversample;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getBearingX() {
/* 163 */     return TrueTypeGlyphProvider.Glyph.this.bearingX;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getBearingY() {
/* 168 */     return TrueTypeGlyphProvider.Glyph.this.bearingY;
/*     */   }
/*     */ 
/*     */   
/*     */   public void upload(int $$0, int $$1) {
/* 173 */     STBTTFontinfo $$2 = this.this$1.this$0.validateFontOpen();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 178 */     NativeImage $$3 = new NativeImage(NativeImage.Format.LUMINANCE, TrueTypeGlyphProvider.Glyph.this.width, TrueTypeGlyphProvider.Glyph.this.height, false);
/* 179 */     $$3.copyFromFont($$2, TrueTypeGlyphProvider.Glyph.this.index, TrueTypeGlyphProvider.Glyph.this.width, TrueTypeGlyphProvider.Glyph.this.height, this.this$1.this$0.pointScale, this.this$1.this$0.pointScale, this.this$1.this$0.shiftX, this.this$1.this$0.shiftY, 0, 0);
/* 180 */     $$3.upload(0, $$0, $$1, 0, 0, TrueTypeGlyphProvider.Glyph.this.width, TrueTypeGlyphProvider.Glyph.this.height, false, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isColored() {
/* 185 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\font\TrueTypeGlyphProvider$Glyph$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */