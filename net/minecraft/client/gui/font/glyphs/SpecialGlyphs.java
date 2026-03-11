/*    */ package net.minecraft.client.gui.font.glyphs;
/*    */ 
/*    */ import com.mojang.blaze3d.font.GlyphInfo;
/*    */ import com.mojang.blaze3d.font.SheetGlyphInfo;
/*    */ import com.mojang.blaze3d.platform.NativeImage;
/*    */ import java.util.function.Function;
/*    */ import java.util.function.Supplier;
/*    */ 
/*    */ public enum SpecialGlyphs
/*    */   implements GlyphInfo
/*    */ {
/* 12 */   WHITE(() -> generate(5, 8, ())),
/* 13 */   MISSING(() -> {
/*    */       int $$0 = 5;
/*    */       int $$1 = 8;
/*    */       return generate(5, 8, ());
/*    */     });
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   final NativeImage image;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static NativeImage generate(int $$0, int $$1, PixelProvider $$2) {
/* 30 */     NativeImage $$3 = new NativeImage(NativeImage.Format.RGBA, $$0, $$1, false);
/* 31 */     for (int $$4 = 0; $$4 < $$1; $$4++) {
/* 32 */       for (int $$5 = 0; $$5 < $$0; $$5++) {
/* 33 */         $$3.setPixelRGBA($$5, $$4, $$2.getColor($$5, $$4));
/*    */       }
/*    */     } 
/* 36 */     $$3.untrack();
/* 37 */     return $$3;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   SpecialGlyphs(Supplier<NativeImage> $$0) {
/* 43 */     this.image = $$0.get();
/*    */   }
/*    */ 
/*    */   
/*    */   public float getAdvance() {
/* 48 */     return (this.image.getWidth() + 1);
/*    */   }
/*    */ 
/*    */   
/*    */   public BakedGlyph bake(Function<SheetGlyphInfo, BakedGlyph> $$0) {
/* 53 */     return $$0.apply(new SheetGlyphInfo()
/*    */         {
/*    */           public int getPixelWidth() {
/* 56 */             return SpecialGlyphs.this.image.getWidth();
/*    */           }
/*    */ 
/*    */           
/*    */           public int getPixelHeight() {
/* 61 */             return SpecialGlyphs.this.image.getHeight();
/*    */           }
/*    */ 
/*    */           
/*    */           public float getOversample() {
/* 66 */             return 1.0F;
/*    */           }
/*    */ 
/*    */           
/*    */           public void upload(int $$0, int $$1) {
/* 71 */             SpecialGlyphs.this.image.upload(0, $$0, $$1, false);
/*    */           }
/*    */ 
/*    */           
/*    */           public boolean isColored() {
/* 76 */             return true;
/*    */           }
/*    */         });
/*    */   }
/*    */   
/*    */   @FunctionalInterface
/*    */   private static interface PixelProvider {
/*    */     int getColor(int param1Int1, int param1Int2);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\font\glyphs\SpecialGlyphs.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */