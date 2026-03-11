/*    */ package com.mojang.blaze3d.font;
/*    */ 
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.client.gui.font.glyphs.BakedGlyph;
/*    */ import net.minecraft.client.gui.font.glyphs.EmptyGlyph;
/*    */ 
/*    */ public interface GlyphInfo
/*    */ {
/*    */   float getAdvance();
/*    */   
/*    */   default float getAdvance(boolean $$0) {
/* 12 */     return getAdvance() + ($$0 ? getBoldOffset() : 0.0F);
/*    */   }
/*    */   
/*    */   default float getBoldOffset() {
/* 16 */     return 1.0F;
/*    */   }
/*    */   
/*    */   default float getShadowOffset() {
/* 20 */     return 1.0F;
/*    */   }
/*    */   
/*    */   BakedGlyph bake(Function<SheetGlyphInfo, BakedGlyph> paramFunction);
/*    */   
/*    */   public static interface SpaceGlyphInfo
/*    */     extends GlyphInfo {
/*    */     default BakedGlyph bake(Function<SheetGlyphInfo, BakedGlyph> $$0) {
/* 28 */       return (BakedGlyph)EmptyGlyph.INSTANCE;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\font\GlyphInfo.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */