/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.ibm.icu.text.ArabicShaping;
/*     */ import com.ibm.icu.text.ArabicShapingException;
/*     */ import com.ibm.icu.text.Bidi;
/*     */ import com.mojang.blaze3d.font.GlyphInfo;
/*     */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*     */ import java.util.List;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.StringSplitter;
/*     */ import net.minecraft.client.gui.font.FontSet;
/*     */ import net.minecraft.client.gui.font.glyphs.BakedGlyph;
/*     */ import net.minecraft.client.renderer.MultiBufferSource;
/*     */ import net.minecraft.locale.Language;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.FormattedText;
/*     */ import net.minecraft.network.chat.Style;
/*     */ import net.minecraft.network.chat.TextColor;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.FormattedCharSequence;
/*     */ import net.minecraft.util.FormattedCharSink;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.util.StringDecomposer;
/*     */ import org.joml.Matrix4f;
/*     */ import org.joml.Matrix4fc;
/*     */ import org.joml.Vector3f;
/*     */ import org.joml.Vector3fc;
/*     */ 
/*     */ public class Font
/*     */ {
/*     */   private static final float EFFECT_DEPTH = 0.01F;
/*  35 */   private static final Vector3f SHADOW_OFFSET = new Vector3f(0.0F, 0.0F, 0.03F);
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int ALPHA_CUTOFF = 8;
/*     */ 
/*     */   
/*  42 */   public final int lineHeight = 9;
/*  43 */   public final RandomSource random = RandomSource.create();
/*     */   
/*     */   private final Function<ResourceLocation, FontSet> fonts;
/*     */   
/*     */   final boolean filterFishyGlyphs;
/*     */   private final StringSplitter splitter;
/*     */   
/*     */   public Font(Function<ResourceLocation, FontSet> $$0, boolean $$1) {
/*  51 */     this.fonts = $$0;
/*  52 */     this.filterFishyGlyphs = $$1;
/*  53 */     this.splitter = new StringSplitter(($$0, $$1) -> getFontSet($$1.getFont()).getGlyphInfo($$0, this.filterFishyGlyphs).getAdvance($$1.isBold()));
/*     */   }
/*     */   
/*     */   FontSet getFontSet(ResourceLocation $$0) {
/*  57 */     return this.fonts.apply($$0);
/*     */   }
/*     */   
/*     */   public String bidirectionalShaping(String $$0) {
/*     */     try {
/*  62 */       Bidi $$1 = new Bidi((new ArabicShaping(8)).shape($$0), 127);
/*  63 */       $$1.setReorderingMode(0);
/*  64 */       return $$1.writeReordered(2);
/*  65 */     } catch (ArabicShapingException arabicShapingException) {
/*     */ 
/*     */       
/*  68 */       return $$0;
/*     */     } 
/*     */   }
/*     */   public int drawInBatch(String $$0, float $$1, float $$2, int $$3, boolean $$4, Matrix4f $$5, MultiBufferSource $$6, DisplayMode $$7, int $$8, int $$9) {
/*  72 */     return drawInBatch($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$8, $$9, isBidirectional());
/*     */   }
/*     */   
/*     */   public int drawInBatch(String $$0, float $$1, float $$2, int $$3, boolean $$4, Matrix4f $$5, MultiBufferSource $$6, DisplayMode $$7, int $$8, int $$9, boolean $$10) {
/*  76 */     return drawInternal($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$8, $$9, $$10);
/*     */   }
/*     */   
/*     */   public int drawInBatch(Component $$0, float $$1, float $$2, int $$3, boolean $$4, Matrix4f $$5, MultiBufferSource $$6, DisplayMode $$7, int $$8, int $$9) {
/*  80 */     return drawInBatch($$0.getVisualOrderText(), $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$8, $$9);
/*     */   }
/*     */   
/*     */   public int drawInBatch(FormattedCharSequence $$0, float $$1, float $$2, int $$3, boolean $$4, Matrix4f $$5, MultiBufferSource $$6, DisplayMode $$7, int $$8, int $$9) {
/*  84 */     return drawInternal($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$8, $$9);
/*     */   }
/*     */   
/*     */   public void drawInBatch8xOutline(FormattedCharSequence $$0, float $$1, float $$2, int $$3, int $$4, Matrix4f $$5, MultiBufferSource $$6, int $$7) {
/*  88 */     int $$8 = adjustColor($$4);
/*  89 */     StringRenderOutput $$9 = new StringRenderOutput($$6, 0.0F, 0.0F, $$8, false, $$5, DisplayMode.NORMAL, $$7);
/*  90 */     for (int $$10 = -1; $$10 <= 1; $$10++) {
/*  91 */       for (int $$11 = -1; $$11 <= 1; $$11++) {
/*  92 */         if ($$10 != 0 || $$11 != 0) {
/*  93 */           float[] $$12 = { $$1 };
/*  94 */           int $$13 = $$10;
/*  95 */           int $$14 = $$11;
/*  96 */           $$0.accept(($$6, $$7, $$8) -> {
/*     */                 boolean $$9 = $$7.isBold();
/*     */                 
/*     */                 FontSet $$10 = getFontSet($$7.getFont());
/*     */                 
/*     */                 GlyphInfo $$11 = $$10.getGlyphInfo($$8, this.filterFishyGlyphs);
/*     */                 
/*     */                 $$0.x = $$1[0] + $$2 * $$11.getShadowOffset();
/*     */                 $$0.y = $$3 + $$4 * $$11.getShadowOffset();
/*     */                 $$1[0] = $$1[0] + $$11.getAdvance($$9);
/*     */                 return $$0.accept($$6, $$7.withColor($$5), $$8);
/*     */               });
/*     */         } 
/*     */       } 
/*     */     } 
/* 111 */     StringRenderOutput $$15 = new StringRenderOutput($$6, $$1, $$2, adjustColor($$3), false, $$5, DisplayMode.POLYGON_OFFSET, $$7);
/* 112 */     $$0.accept($$15);
/* 113 */     $$15.finish(0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   private static int adjustColor(int $$0) {
/* 118 */     if (($$0 & 0xFC000000) == 0) {
/* 119 */       return $$0 | 0xFF000000;
/*     */     }
/* 121 */     return $$0;
/*     */   }
/*     */   
/*     */   private int drawInternal(String $$0, float $$1, float $$2, int $$3, boolean $$4, Matrix4f $$5, MultiBufferSource $$6, DisplayMode $$7, int $$8, int $$9, boolean $$10) {
/* 125 */     if ($$10) {
/* 126 */       $$0 = bidirectionalShaping($$0);
/*     */     }
/*     */     
/* 129 */     $$3 = adjustColor($$3);
/*     */     
/* 131 */     Matrix4f $$11 = new Matrix4f((Matrix4fc)$$5);
/* 132 */     if ($$4) {
/* 133 */       renderText($$0, $$1, $$2, $$3, true, $$5, $$6, $$7, $$8, $$9);
/* 134 */       $$11.translate((Vector3fc)SHADOW_OFFSET);
/*     */     } 
/*     */     
/* 137 */     $$1 = renderText($$0, $$1, $$2, $$3, false, $$11, $$6, $$7, $$8, $$9);
/*     */     
/* 139 */     return (int)$$1 + ($$4 ? 1 : 0);
/*     */   }
/*     */   
/*     */   private int drawInternal(FormattedCharSequence $$0, float $$1, float $$2, int $$3, boolean $$4, Matrix4f $$5, MultiBufferSource $$6, DisplayMode $$7, int $$8, int $$9) {
/* 143 */     $$3 = adjustColor($$3);
/*     */     
/* 145 */     Matrix4f $$10 = new Matrix4f((Matrix4fc)$$5);
/* 146 */     if ($$4) {
/* 147 */       renderText($$0, $$1, $$2, $$3, true, $$5, $$6, $$7, $$8, $$9);
/* 148 */       $$10.translate((Vector3fc)SHADOW_OFFSET);
/*     */     } 
/*     */     
/* 151 */     $$1 = renderText($$0, $$1, $$2, $$3, false, $$10, $$6, $$7, $$8, $$9);
/*     */     
/* 153 */     return (int)$$1 + ($$4 ? 1 : 0);
/*     */   }
/*     */   
/*     */   public enum DisplayMode {
/* 157 */     NORMAL,
/* 158 */     SEE_THROUGH,
/* 159 */     POLYGON_OFFSET;
/*     */   }
/*     */   
/*     */   private class StringRenderOutput
/*     */     implements FormattedCharSink
/*     */   {
/*     */     final MultiBufferSource bufferSource;
/*     */     private final boolean dropShadow;
/*     */     private final float dimFactor;
/*     */     private final float r;
/*     */     private final float g;
/*     */     private final float b;
/*     */     private final float a;
/*     */     private final Matrix4f pose;
/*     */     private final Font.DisplayMode mode;
/*     */     private final int packedLightCoords;
/*     */     float x;
/*     */     float y;
/*     */     @Nullable
/*     */     private List<BakedGlyph.Effect> effects;
/*     */     
/*     */     private void addEffect(BakedGlyph.Effect $$0) {
/* 181 */       if (this.effects == null) {
/* 182 */         this.effects = Lists.newArrayList();
/*     */       }
/* 184 */       this.effects.add($$0);
/*     */     }
/*     */     
/*     */     public StringRenderOutput(MultiBufferSource $$0, float $$1, float $$2, int $$3, boolean $$4, Matrix4f $$5, Font.DisplayMode $$6, int $$7) {
/* 188 */       this.bufferSource = $$0;
/* 189 */       this.x = $$1;
/* 190 */       this.y = $$2;
/* 191 */       this.dropShadow = $$4;
/* 192 */       this.dimFactor = $$4 ? 0.25F : 1.0F;
/* 193 */       this.r = ($$3 >> 16 & 0xFF) / 255.0F * this.dimFactor;
/* 194 */       this.g = ($$3 >> 8 & 0xFF) / 255.0F * this.dimFactor;
/* 195 */       this.b = ($$3 & 0xFF) / 255.0F * this.dimFactor;
/* 196 */       this.a = ($$3 >> 24 & 0xFF) / 255.0F;
/* 197 */       this.pose = $$5;
/* 198 */       this.mode = $$6;
/* 199 */       this.packedLightCoords = $$7;
/*     */     }
/*     */     
/*     */     public boolean accept(int $$0, Style $$1, int $$2) {
/*     */       float $$13, $$14, $$15;
/* 204 */       FontSet $$3 = Font.this.getFontSet($$1.getFont());
/* 205 */       GlyphInfo $$4 = $$3.getGlyphInfo($$2, Font.this.filterFishyGlyphs);
/* 206 */       BakedGlyph $$5 = ($$1.isObfuscated() && $$2 != 32) ? $$3.getRandomGlyph($$4) : $$3.getGlyph($$2);
/*     */       
/* 208 */       boolean $$6 = $$1.isBold();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 213 */       float $$7 = this.a;
/*     */       
/* 215 */       TextColor $$8 = $$1.getColor();
/* 216 */       if ($$8 != null) {
/* 217 */         int $$9 = $$8.getValue();
/* 218 */         float $$10 = ($$9 >> 16 & 0xFF) / 255.0F * this.dimFactor;
/* 219 */         float $$11 = ($$9 >> 8 & 0xFF) / 255.0F * this.dimFactor;
/* 220 */         float $$12 = ($$9 & 0xFF) / 255.0F * this.dimFactor;
/*     */       } else {
/* 222 */         $$13 = this.r;
/* 223 */         $$14 = this.g;
/* 224 */         $$15 = this.b;
/*     */       } 
/*     */       
/* 227 */       if (!($$5 instanceof net.minecraft.client.gui.font.glyphs.EmptyGlyph)) {
/* 228 */         float $$16 = $$6 ? $$4.getBoldOffset() : 0.0F;
/* 229 */         float $$17 = this.dropShadow ? $$4.getShadowOffset() : 0.0F;
/*     */         
/* 231 */         VertexConsumer $$18 = this.bufferSource.getBuffer($$5.renderType(this.mode));
/* 232 */         Font.this.renderChar($$5, $$6, $$1.isItalic(), $$16, this.x + $$17, this.y + $$17, this.pose, $$18, $$13, $$14, $$15, $$7, this.packedLightCoords);
/*     */       } 
/*     */       
/* 235 */       float $$19 = $$4.getAdvance($$6);
/*     */       
/* 237 */       float $$20 = this.dropShadow ? 1.0F : 0.0F;
/* 238 */       if ($$1.isStrikethrough()) {
/* 239 */         addEffect(new BakedGlyph.Effect(this.x + $$20 - 1.0F, this.y + $$20 + 4.5F, this.x + $$20 + $$19, this.y + $$20 + 4.5F - 1.0F, 0.01F, $$13, $$14, $$15, $$7));
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 249 */       if ($$1.isUnderlined()) {
/* 250 */         addEffect(new BakedGlyph.Effect(this.x + $$20 - 1.0F, this.y + $$20 + 9.0F, this.x + $$20 + $$19, this.y + $$20 + 9.0F - 1.0F, 0.01F, $$13, $$14, $$15, $$7));
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 259 */       this.x += $$19;
/* 260 */       return true;
/*     */     }
/*     */     
/*     */     public float finish(int $$0, float $$1) {
/* 264 */       if ($$0 != 0) {
/* 265 */         float $$2 = ($$0 >> 24 & 0xFF) / 255.0F;
/* 266 */         float $$3 = ($$0 >> 16 & 0xFF) / 255.0F;
/* 267 */         float $$4 = ($$0 >> 8 & 0xFF) / 255.0F;
/* 268 */         float $$5 = ($$0 & 0xFF) / 255.0F;
/* 269 */         addEffect(new BakedGlyph.Effect($$1 - 1.0F, this.y + 9.0F, this.x + 1.0F, this.y - 1.0F, 0.01F, $$3, $$4, $$5, $$2));
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 278 */       if (this.effects != null) {
/* 279 */         BakedGlyph $$6 = Font.this.getFontSet(Style.DEFAULT_FONT).whiteGlyph();
/* 280 */         VertexConsumer $$7 = this.bufferSource.getBuffer($$6.renderType(this.mode));
/* 281 */         for (BakedGlyph.Effect $$8 : this.effects) {
/* 282 */           $$6.renderEffect($$8, this.pose, $$7, this.packedLightCoords);
/*     */         }
/*     */       } 
/*     */       
/* 286 */       return this.x;
/*     */     }
/*     */   }
/*     */   
/*     */   private float renderText(String $$0, float $$1, float $$2, int $$3, boolean $$4, Matrix4f $$5, MultiBufferSource $$6, DisplayMode $$7, int $$8, int $$9) {
/* 291 */     StringRenderOutput $$10 = new StringRenderOutput($$6, $$1, $$2, $$3, $$4, $$5, $$7, $$9);
/* 292 */     StringDecomposer.iterateFormatted($$0, Style.EMPTY, $$10);
/* 293 */     return $$10.finish($$8, $$1);
/*     */   }
/*     */   
/*     */   private float renderText(FormattedCharSequence $$0, float $$1, float $$2, int $$3, boolean $$4, Matrix4f $$5, MultiBufferSource $$6, DisplayMode $$7, int $$8, int $$9) {
/* 297 */     StringRenderOutput $$10 = new StringRenderOutput($$6, $$1, $$2, $$3, $$4, $$5, $$7, $$9);
/* 298 */     $$0.accept($$10);
/* 299 */     return $$10.finish($$8, $$1);
/*     */   }
/*     */   
/*     */   void renderChar(BakedGlyph $$0, boolean $$1, boolean $$2, float $$3, float $$4, float $$5, Matrix4f $$6, VertexConsumer $$7, float $$8, float $$9, float $$10, float $$11, int $$12) {
/* 303 */     $$0.render($$2, $$4, $$5, $$6, $$7, $$8, $$9, $$10, $$11, $$12);
/* 304 */     if ($$1) {
/* 305 */       $$0.render($$2, $$4 + $$3, $$5, $$6, $$7, $$8, $$9, $$10, $$11, $$12);
/*     */     }
/*     */   }
/*     */   
/*     */   public int width(String $$0) {
/* 310 */     return Mth.ceil(this.splitter.stringWidth($$0));
/*     */   }
/*     */   
/*     */   public int width(FormattedText $$0) {
/* 314 */     return Mth.ceil(this.splitter.stringWidth($$0));
/*     */   }
/*     */   
/*     */   public int width(FormattedCharSequence $$0) {
/* 318 */     return Mth.ceil(this.splitter.stringWidth($$0));
/*     */   }
/*     */   
/*     */   public String plainSubstrByWidth(String $$0, int $$1, boolean $$2) {
/* 322 */     return $$2 ? this.splitter.plainTailByWidth($$0, $$1, Style.EMPTY) : this.splitter.plainHeadByWidth($$0, $$1, Style.EMPTY);
/*     */   }
/*     */   
/*     */   public String plainSubstrByWidth(String $$0, int $$1) {
/* 326 */     return this.splitter.plainHeadByWidth($$0, $$1, Style.EMPTY);
/*     */   }
/*     */   
/*     */   public FormattedText substrByWidth(FormattedText $$0, int $$1) {
/* 330 */     return this.splitter.headByWidth($$0, $$1, Style.EMPTY);
/*     */   }
/*     */   
/*     */   public int wordWrapHeight(String $$0, int $$1) {
/* 334 */     return 9 * this.splitter.splitLines($$0, $$1, Style.EMPTY).size();
/*     */   }
/*     */   
/*     */   public int wordWrapHeight(FormattedText $$0, int $$1) {
/* 338 */     return 9 * this.splitter.splitLines($$0, $$1, Style.EMPTY).size();
/*     */   }
/*     */   
/*     */   public List<FormattedCharSequence> split(FormattedText $$0, int $$1) {
/* 342 */     return Language.getInstance().getVisualOrder(this.splitter.splitLines($$0, $$1, Style.EMPTY));
/*     */   }
/*     */   
/*     */   public boolean isBidirectional() {
/* 346 */     return Language.getInstance().isDefaultRightToLeft();
/*     */   }
/*     */   
/*     */   public StringSplitter getSplitter() {
/* 350 */     return this.splitter;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\Font.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */