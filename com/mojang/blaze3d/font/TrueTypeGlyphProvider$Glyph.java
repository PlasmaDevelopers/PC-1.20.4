/*     */ package com.mojang.blaze3d.font;
/*     */ 
/*     */ import com.mojang.blaze3d.platform.NativeImage;
/*     */ import java.util.function.Function;
/*     */ import net.minecraft.client.gui.font.glyphs.BakedGlyph;
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
/*     */ class Glyph
/*     */   implements GlyphInfo
/*     */ {
/*     */   final int width;
/*     */   final int height;
/*     */   final float bearingX;
/*     */   final float bearingY;
/*     */   private final float advance;
/*     */   final int index;
/*     */   
/*     */   Glyph(int $$0, int $$1, int $$2, int $$3, float $$4, float $$5, int $$6) {
/* 127 */     this.width = $$1 - $$0;
/* 128 */     this.height = $$2 - $$3;
/*     */     
/* 130 */     this.advance = $$4 / paramTrueTypeGlyphProvider.oversample;
/*     */     
/* 132 */     this.bearingX = ($$5 + $$0 + paramTrueTypeGlyphProvider.shiftX) / paramTrueTypeGlyphProvider.oversample;
/* 133 */     this.bearingY = (paramTrueTypeGlyphProvider.ascent - $$2 + paramTrueTypeGlyphProvider.shiftY) / paramTrueTypeGlyphProvider.oversample;
/*     */     
/* 135 */     this.index = $$6;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getAdvance() {
/* 140 */     return this.advance;
/*     */   }
/*     */ 
/*     */   
/*     */   public BakedGlyph bake(Function<SheetGlyphInfo, BakedGlyph> $$0) {
/* 145 */     return $$0.apply(new SheetGlyphInfo()
/*     */         {
/*     */           public int getPixelWidth() {
/* 148 */             return TrueTypeGlyphProvider.Glyph.this.width;
/*     */           }
/*     */ 
/*     */           
/*     */           public int getPixelHeight() {
/* 153 */             return TrueTypeGlyphProvider.Glyph.this.height;
/*     */           }
/*     */ 
/*     */           
/*     */           public float getOversample() {
/* 158 */             return TrueTypeGlyphProvider.this.oversample;
/*     */           }
/*     */ 
/*     */           
/*     */           public float getBearingX() {
/* 163 */             return TrueTypeGlyphProvider.Glyph.this.bearingX;
/*     */           }
/*     */ 
/*     */           
/*     */           public float getBearingY() {
/* 168 */             return TrueTypeGlyphProvider.Glyph.this.bearingY;
/*     */           }
/*     */ 
/*     */           
/*     */           public void upload(int $$0, int $$1) {
/* 173 */             STBTTFontinfo $$2 = TrueTypeGlyphProvider.this.validateFontOpen();
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 178 */             NativeImage $$3 = new NativeImage(NativeImage.Format.LUMINANCE, TrueTypeGlyphProvider.Glyph.this.width, TrueTypeGlyphProvider.Glyph.this.height, false);
/* 179 */             $$3.copyFromFont($$2, TrueTypeGlyphProvider.Glyph.this.index, TrueTypeGlyphProvider.Glyph.this.width, TrueTypeGlyphProvider.Glyph.this.height, TrueTypeGlyphProvider.this.pointScale, TrueTypeGlyphProvider.this.pointScale, TrueTypeGlyphProvider.this.shiftX, TrueTypeGlyphProvider.this.shiftY, 0, 0);
/* 180 */             $$3.upload(0, $$0, $$1, 0, 0, TrueTypeGlyphProvider.Glyph.this.width, TrueTypeGlyphProvider.Glyph.this.height, false, true);
/*     */           }
/*     */ 
/*     */           
/*     */           public boolean isColored() {
/* 185 */             return false;
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\font\TrueTypeGlyphProvider$Glyph.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */