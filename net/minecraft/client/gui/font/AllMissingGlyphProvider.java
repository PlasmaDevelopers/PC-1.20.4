/*    */ package net.minecraft.client.gui.font;
/*    */ 
/*    */ import com.mojang.blaze3d.font.GlyphInfo;
/*    */ import com.mojang.blaze3d.font.GlyphProvider;
/*    */ import it.unimi.dsi.fastutil.ints.IntSet;
/*    */ import it.unimi.dsi.fastutil.ints.IntSets;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.client.gui.font.glyphs.SpecialGlyphs;
/*    */ 
/*    */ public class AllMissingGlyphProvider
/*    */   implements GlyphProvider
/*    */ {
/*    */   @Nullable
/*    */   public GlyphInfo getGlyph(int $$0) {
/* 15 */     return (GlyphInfo)SpecialGlyphs.MISSING;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IntSet getSupportedGlyphs() {
/* 21 */     return (IntSet)IntSets.EMPTY_SET;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\font\AllMissingGlyphProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */