/*    */ package com.mojang.blaze3d.font;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.ints.IntSet;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface GlyphProvider
/*    */   extends AutoCloseable
/*    */ {
/*    */   default void close() {}
/*    */   
/*    */   @Nullable
/*    */   default GlyphInfo getGlyph(int $$0) {
/* 15 */     return null;
/*    */   }
/*    */   
/*    */   IntSet getSupportedGlyphs();
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\font\GlyphProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */