/*     */ package net.minecraft.client.gui.font.providers;
/*     */ 
/*     */ import com.mojang.blaze3d.font.GlyphInfo;
/*     */ import com.mojang.blaze3d.font.SheetGlyphInfo;
/*     */ import com.mojang.blaze3d.platform.NativeImage;
/*     */ import java.util.function.Function;
/*     */ import net.minecraft.client.gui.font.glyphs.BakedGlyph;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class Glyph
/*     */   extends Record
/*     */   implements GlyphInfo
/*     */ {
/*     */   final float scale;
/*     */   final NativeImage image;
/*     */   final int offsetX;
/*     */   final int offsetY;
/*     */   final int width;
/*     */   final int height;
/*     */   private final int advance;
/*     */   final int ascent;
/*     */   
/*     */   public final String toString() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> toString : (Lnet/minecraft/client/gui/font/providers/BitmapProvider$Glyph;)Ljava/lang/String;
/*     */     //   6: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #181	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lnet/minecraft/client/gui/font/providers/BitmapProvider$Glyph;
/*     */   }
/*     */   
/*     */   public final int hashCode() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/gui/font/providers/BitmapProvider$Glyph;)I
/*     */     //   6: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #181	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lnet/minecraft/client/gui/font/providers/BitmapProvider$Glyph;
/*     */   }
/*     */   
/*     */   public final boolean equals(Object $$0) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: <illegal opcode> equals : (Lnet/minecraft/client/gui/font/providers/BitmapProvider$Glyph;Ljava/lang/Object;)Z
/*     */     //   7: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #181	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	8	0	this	Lnet/minecraft/client/gui/font/providers/BitmapProvider$Glyph;
/*     */     //   0	8	1	$$0	Ljava/lang/Object;
/*     */   }
/*     */   
/*     */   Glyph(float $$0, NativeImage $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7) {
/* 181 */     this.scale = $$0; this.image = $$1; this.offsetX = $$2; this.offsetY = $$3; this.width = $$4; this.height = $$5; this.advance = $$6; this.ascent = $$7; } public float scale() { return this.scale; } public NativeImage image() { return this.image; } public int offsetX() { return this.offsetX; } public int offsetY() { return this.offsetY; } public int width() { return this.width; } public int height() { return this.height; } public int advance() { return this.advance; } public int ascent() { return this.ascent; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getAdvance() {
/* 194 */     return this.advance;
/*     */   }
/*     */ 
/*     */   
/*     */   public BakedGlyph bake(Function<SheetGlyphInfo, BakedGlyph> $$0) {
/* 199 */     return $$0.apply(new SheetGlyphInfo()
/*     */         {
/*     */           public float getOversample() {
/* 202 */             return 1.0F / BitmapProvider.Glyph.this.scale;
/*     */           }
/*     */ 
/*     */           
/*     */           public int getPixelWidth() {
/* 207 */             return BitmapProvider.Glyph.this.width;
/*     */           }
/*     */ 
/*     */           
/*     */           public int getPixelHeight() {
/* 212 */             return BitmapProvider.Glyph.this.height;
/*     */           }
/*     */ 
/*     */           
/*     */           public float getBearingY() {
/* 217 */             return super.getBearingY() + 7.0F - BitmapProvider.Glyph.this.ascent;
/*     */           }
/*     */ 
/*     */           
/*     */           public void upload(int $$0, int $$1) {
/* 222 */             BitmapProvider.Glyph.this.image.upload(0, $$0, $$1, BitmapProvider.Glyph.this.offsetX, BitmapProvider.Glyph.this.offsetY, BitmapProvider.Glyph.this.width, BitmapProvider.Glyph.this.height, false, false);
/*     */           }
/*     */ 
/*     */           
/*     */           public boolean isColored() {
/* 227 */             return (BitmapProvider.Glyph.this.image.format().components() > 1);
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\font\providers\BitmapProvider$Glyph.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */