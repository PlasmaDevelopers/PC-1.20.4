/*    */ package com.mojang.blaze3d.font;
/*    */ 
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.client.gui.font.glyphs.BakedGlyph;
/*    */ import net.minecraft.client.gui.font.glyphs.EmptyGlyph;
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
/*    */ public interface SpaceGlyphInfo
/*    */   extends GlyphInfo
/*    */ {
/*    */   default BakedGlyph bake(Function<SheetGlyphInfo, BakedGlyph> $$0) {
/* 28 */     return (BakedGlyph)EmptyGlyph.INSTANCE;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\font\GlyphInfo$SpaceGlyphInfo.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */