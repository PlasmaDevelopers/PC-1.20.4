/*     */ package com.mojang.blaze3d.font;
/*     */ 
/*     */ import com.mojang.blaze3d.platform.NativeImage;
/*     */ import it.unimi.dsi.fastutil.ints.IntArraySet;
/*     */ import it.unimi.dsi.fastutil.ints.IntCollection;
/*     */ import it.unimi.dsi.fastutil.ints.IntSet;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.stream.IntStream;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.gui.font.glyphs.BakedGlyph;
/*     */ import org.lwjgl.stb.STBTTFontinfo;
/*     */ import org.lwjgl.stb.STBTruetype;
/*     */ import org.lwjgl.system.MemoryStack;
/*     */ import org.lwjgl.system.MemoryUtil;
/*     */ 
/*     */ public class TrueTypeGlyphProvider
/*     */   implements GlyphProvider {
/*     */   @Nullable
/*     */   private ByteBuffer fontMemory;
/*     */   @Nullable
/*     */   private STBTTFontinfo font;
/*     */   final float oversample;
/*  27 */   private final IntSet skip = (IntSet)new IntArraySet();
/*     */   final float shiftX;
/*     */   final float shiftY;
/*     */   final float pointScale;
/*     */   final float ascent;
/*     */   
/*     */   public TrueTypeGlyphProvider(ByteBuffer $$0, STBTTFontinfo $$1, float $$2, float $$3, float $$4, float $$5, String $$6) {
/*  34 */     this.fontMemory = $$0;
/*  35 */     this.font = $$1;
/*     */     
/*  37 */     this.oversample = $$3;
/*     */     
/*  39 */     Objects.requireNonNull(this.skip); $$6.codePoints().forEach(this.skip::add);
/*     */     
/*  41 */     this.shiftX = $$4 * $$3;
/*  42 */     this.shiftY = $$5 * $$3;
/*     */     
/*  44 */     this.pointScale = STBTruetype.stbtt_ScaleForPixelHeight($$1, $$2 * $$3);
/*     */     
/*  46 */     MemoryStack $$7 = MemoryStack.stackPush(); 
/*  47 */     try { IntBuffer $$8 = $$7.mallocInt(1);
/*  48 */       IntBuffer $$9 = $$7.mallocInt(1);
/*  49 */       IntBuffer $$10 = $$7.mallocInt(1);
/*  50 */       STBTruetype.stbtt_GetFontVMetrics($$1, $$8, $$9, $$10);
/*     */       
/*  52 */       this.ascent = $$8.get(0) * this.pointScale;
/*  53 */       if ($$7 != null) $$7.close();  }
/*     */     catch (Throwable throwable) { if ($$7 != null)
/*     */         try { $$7.close(); }
/*     */         catch (Throwable throwable1)
/*     */         { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/*  59 */      } @Nullable public GlyphInfo getGlyph(int $$0) { STBTTFontinfo $$1 = validateFontOpen();
/*     */     
/*  61 */     if (this.skip.contains($$0)) {
/*  62 */       return null;
/*     */     }
/*     */     
/*  65 */     MemoryStack $$2 = MemoryStack.stackPush(); 
/*  66 */     try { int $$3 = STBTruetype.stbtt_FindGlyphIndex($$1, $$0);
/*  67 */       if ($$3 == 0)
/*  68 */       { GlyphInfo glyphInfo = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  92 */         if ($$2 != null) $$2.close();  return glyphInfo; }  IntBuffer $$4 = $$2.mallocInt(1); IntBuffer $$5 = $$2.mallocInt(1); IntBuffer $$6 = $$2.mallocInt(1); IntBuffer $$7 = $$2.mallocInt(1); IntBuffer $$8 = $$2.mallocInt(1); IntBuffer $$9 = $$2.mallocInt(1); STBTruetype.stbtt_GetGlyphHMetrics($$1, $$3, $$8, $$9); STBTruetype.stbtt_GetGlyphBitmapBoxSubpixel($$1, $$3, this.pointScale, this.pointScale, this.shiftX, this.shiftY, $$4, $$5, $$6, $$7); float $$10 = $$8.get(0) * this.pointScale; int $$11 = $$6.get(0) - $$4.get(0); int $$12 = $$7.get(0) - $$5.get(0); if ($$11 <= 0 || $$12 <= 0) { GlyphInfo.SpaceGlyphInfo spaceGlyphInfo = () -> $$0 / this.oversample; if ($$2 != null) $$2.close();  return spaceGlyphInfo; }  Glyph glyph = new Glyph($$4.get(0), $$6.get(0), -$$5.get(0), -$$7.get(0), $$10, $$9.get(0) * this.pointScale, $$3); if ($$2 != null) $$2.close();  return glyph; } catch (Throwable throwable) { if ($$2 != null)
/*     */         try { $$2.close(); }
/*     */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/*  96 */      } STBTTFontinfo validateFontOpen() { if (this.fontMemory == null || this.font == null) {
/*  97 */       throw new IllegalArgumentException("Provider already closed");
/*     */     }
/*  99 */     return this.font; }
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() {
/* 104 */     if (this.font != null) {
/* 105 */       this.font.free();
/* 106 */       this.font = null;
/*     */     } 
/* 108 */     MemoryUtil.memFree(this.fontMemory);
/* 109 */     this.fontMemory = null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IntSet getSupportedGlyphs() {
/* 115 */     return IntStream.range(0, 65535).filter($$0 -> !this.skip.contains($$0)).<IntSet>collect(it.unimi.dsi.fastutil.ints.IntOpenHashSet::new, IntCollection::add, IntCollection::addAll);
/*     */   }
/*     */   
/*     */   private class Glyph implements GlyphInfo {
/*     */     final int width;
/*     */     final int height;
/*     */     final float bearingX;
/*     */     final float bearingY;
/*     */     private final float advance;
/*     */     final int index;
/*     */     
/*     */     Glyph(int $$0, int $$1, int $$2, int $$3, float $$4, float $$5, int $$6) {
/* 127 */       this.width = $$1 - $$0;
/* 128 */       this.height = $$2 - $$3;
/*     */       
/* 130 */       this.advance = $$4 / TrueTypeGlyphProvider.this.oversample;
/*     */       
/* 132 */       this.bearingX = ($$5 + $$0 + TrueTypeGlyphProvider.this.shiftX) / TrueTypeGlyphProvider.this.oversample;
/* 133 */       this.bearingY = (TrueTypeGlyphProvider.this.ascent - $$2 + TrueTypeGlyphProvider.this.shiftY) / TrueTypeGlyphProvider.this.oversample;
/*     */       
/* 135 */       this.index = $$6;
/*     */     }
/*     */ 
/*     */     
/*     */     public float getAdvance() {
/* 140 */       return this.advance;
/*     */     }
/*     */     
/*     */     public BakedGlyph bake(Function<SheetGlyphInfo, BakedGlyph> $$0)
/*     */     {
/* 145 */       return $$0.apply(new SheetGlyphInfo()
/*     */           {
/*     */             public int getPixelWidth() {
/* 148 */               return TrueTypeGlyphProvider.Glyph.this.width;
/*     */             }
/*     */ 
/*     */             
/*     */             public int getPixelHeight() {
/* 153 */               return TrueTypeGlyphProvider.Glyph.this.height;
/*     */             }
/*     */ 
/*     */             
/*     */             public float getOversample() {
/* 158 */               return TrueTypeGlyphProvider.this.oversample;
/*     */             }
/*     */ 
/*     */             
/*     */             public float getBearingX() {
/* 163 */               return TrueTypeGlyphProvider.Glyph.this.bearingX;
/*     */             }
/*     */ 
/*     */             
/*     */             public float getBearingY() {
/* 168 */               return TrueTypeGlyphProvider.Glyph.this.bearingY;
/*     */             }
/*     */ 
/*     */             
/*     */             public void upload(int $$0, int $$1) {
/* 173 */               STBTTFontinfo $$2 = TrueTypeGlyphProvider.this.validateFontOpen();
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 178 */               NativeImage $$3 = new NativeImage(NativeImage.Format.LUMINANCE, TrueTypeGlyphProvider.Glyph.this.width, TrueTypeGlyphProvider.Glyph.this.height, false);
/* 179 */               $$3.copyFromFont($$2, TrueTypeGlyphProvider.Glyph.this.index, TrueTypeGlyphProvider.Glyph.this.width, TrueTypeGlyphProvider.Glyph.this.height, TrueTypeGlyphProvider.this.pointScale, TrueTypeGlyphProvider.this.pointScale, TrueTypeGlyphProvider.this.shiftX, TrueTypeGlyphProvider.this.shiftY, 0, 0);
/* 180 */               $$3.upload(0, $$0, $$1, 0, 0, TrueTypeGlyphProvider.Glyph.this.width, TrueTypeGlyphProvider.Glyph.this.height, false, true);
/*     */             }
/*     */             
/*     */             public boolean isColored()
/*     */             {
/* 185 */               return false; } }); } } class null implements SheetGlyphInfo { public int getPixelWidth() { return TrueTypeGlyphProvider.Glyph.this.width; } public boolean isColored() { return false; }
/*     */ 
/*     */     
/*     */     public int getPixelHeight() {
/*     */       return TrueTypeGlyphProvider.Glyph.this.height;
/*     */     }
/*     */     
/*     */     public float getOversample() {
/*     */       return TrueTypeGlyphProvider.this.oversample;
/*     */     }
/*     */     
/*     */     public float getBearingX() {
/*     */       return TrueTypeGlyphProvider.Glyph.this.bearingX;
/*     */     }
/*     */     
/*     */     public float getBearingY() {
/*     */       return TrueTypeGlyphProvider.Glyph.this.bearingY;
/*     */     }
/*     */     
/*     */     public void upload(int $$0, int $$1) {
/*     */       STBTTFontinfo $$2 = TrueTypeGlyphProvider.this.validateFontOpen();
/*     */       NativeImage $$3 = new NativeImage(NativeImage.Format.LUMINANCE, TrueTypeGlyphProvider.Glyph.this.width, TrueTypeGlyphProvider.Glyph.this.height, false);
/*     */       $$3.copyFromFont($$2, TrueTypeGlyphProvider.Glyph.this.index, TrueTypeGlyphProvider.Glyph.this.width, TrueTypeGlyphProvider.Glyph.this.height, TrueTypeGlyphProvider.this.pointScale, TrueTypeGlyphProvider.this.pointScale, TrueTypeGlyphProvider.this.shiftX, TrueTypeGlyphProvider.this.shiftY, 0, 0);
/*     */       $$3.upload(0, $$0, $$1, 0, 0, TrueTypeGlyphProvider.Glyph.this.width, TrueTypeGlyphProvider.Glyph.this.height, false, true);
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\font\TrueTypeGlyphProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */