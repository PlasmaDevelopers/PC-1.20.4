/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.blaze3d.font.GlyphInfo;
/*     */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.gui.font.FontSet;
/*     */ import net.minecraft.client.gui.font.glyphs.BakedGlyph;
/*     */ import net.minecraft.client.renderer.MultiBufferSource;
/*     */ import net.minecraft.network.chat.Style;
/*     */ import net.minecraft.network.chat.TextColor;
/*     */ import net.minecraft.util.FormattedCharSink;
/*     */ import org.joml.Matrix4f;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class StringRenderOutput
/*     */   implements FormattedCharSink
/*     */ {
/*     */   final MultiBufferSource bufferSource;
/*     */   private final boolean dropShadow;
/*     */   private final float dimFactor;
/*     */   private final float r;
/*     */   private final float g;
/*     */   private final float b;
/*     */   private final float a;
/*     */   private final Matrix4f pose;
/*     */   private final Font.DisplayMode mode;
/*     */   private final int packedLightCoords;
/*     */   float x;
/*     */   float y;
/*     */   @Nullable
/*     */   private List<BakedGlyph.Effect> effects;
/*     */   
/*     */   private void addEffect(BakedGlyph.Effect $$0) {
/* 181 */     if (this.effects == null) {
/* 182 */       this.effects = Lists.newArrayList();
/*     */     }
/* 184 */     this.effects.add($$0);
/*     */   }
/*     */   
/*     */   public StringRenderOutput(MultiBufferSource $$0, float $$1, float $$2, int $$3, boolean $$4, Matrix4f $$5, Font.DisplayMode $$6, int $$7) {
/* 188 */     this.bufferSource = $$0;
/* 189 */     this.x = $$1;
/* 190 */     this.y = $$2;
/* 191 */     this.dropShadow = $$4;
/* 192 */     this.dimFactor = $$4 ? 0.25F : 1.0F;
/* 193 */     this.r = ($$3 >> 16 & 0xFF) / 255.0F * this.dimFactor;
/* 194 */     this.g = ($$3 >> 8 & 0xFF) / 255.0F * this.dimFactor;
/* 195 */     this.b = ($$3 & 0xFF) / 255.0F * this.dimFactor;
/* 196 */     this.a = ($$3 >> 24 & 0xFF) / 255.0F;
/* 197 */     this.pose = $$5;
/* 198 */     this.mode = $$6;
/* 199 */     this.packedLightCoords = $$7;
/*     */   }
/*     */   
/*     */   public boolean accept(int $$0, Style $$1, int $$2) {
/*     */     float $$13, $$14, $$15;
/* 204 */     FontSet $$3 = Font.this.getFontSet($$1.getFont());
/* 205 */     GlyphInfo $$4 = $$3.getGlyphInfo($$2, Font.this.filterFishyGlyphs);
/* 206 */     BakedGlyph $$5 = ($$1.isObfuscated() && $$2 != 32) ? $$3.getRandomGlyph($$4) : $$3.getGlyph($$2);
/*     */     
/* 208 */     boolean $$6 = $$1.isBold();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 213 */     float $$7 = this.a;
/*     */     
/* 215 */     TextColor $$8 = $$1.getColor();
/* 216 */     if ($$8 != null) {
/* 217 */       int $$9 = $$8.getValue();
/* 218 */       float $$10 = ($$9 >> 16 & 0xFF) / 255.0F * this.dimFactor;
/* 219 */       float $$11 = ($$9 >> 8 & 0xFF) / 255.0F * this.dimFactor;
/* 220 */       float $$12 = ($$9 & 0xFF) / 255.0F * this.dimFactor;
/*     */     } else {
/* 222 */       $$13 = this.r;
/* 223 */       $$14 = this.g;
/* 224 */       $$15 = this.b;
/*     */     } 
/*     */     
/* 227 */     if (!($$5 instanceof net.minecraft.client.gui.font.glyphs.EmptyGlyph)) {
/* 228 */       float $$16 = $$6 ? $$4.getBoldOffset() : 0.0F;
/* 229 */       float $$17 = this.dropShadow ? $$4.getShadowOffset() : 0.0F;
/*     */       
/* 231 */       VertexConsumer $$18 = this.bufferSource.getBuffer($$5.renderType(this.mode));
/* 232 */       Font.this.renderChar($$5, $$6, $$1.isItalic(), $$16, this.x + $$17, this.y + $$17, this.pose, $$18, $$13, $$14, $$15, $$7, this.packedLightCoords);
/*     */     } 
/*     */     
/* 235 */     float $$19 = $$4.getAdvance($$6);
/*     */     
/* 237 */     float $$20 = this.dropShadow ? 1.0F : 0.0F;
/* 238 */     if ($$1.isStrikethrough()) {
/* 239 */       addEffect(new BakedGlyph.Effect(this.x + $$20 - 1.0F, this.y + $$20 + 4.5F, this.x + $$20 + $$19, this.y + $$20 + 4.5F - 1.0F, 0.01F, $$13, $$14, $$15, $$7));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 249 */     if ($$1.isUnderlined()) {
/* 250 */       addEffect(new BakedGlyph.Effect(this.x + $$20 - 1.0F, this.y + $$20 + 9.0F, this.x + $$20 + $$19, this.y + $$20 + 9.0F - 1.0F, 0.01F, $$13, $$14, $$15, $$7));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 259 */     this.x += $$19;
/* 260 */     return true;
/*     */   }
/*     */   
/*     */   public float finish(int $$0, float $$1) {
/* 264 */     if ($$0 != 0) {
/* 265 */       float $$2 = ($$0 >> 24 & 0xFF) / 255.0F;
/* 266 */       float $$3 = ($$0 >> 16 & 0xFF) / 255.0F;
/* 267 */       float $$4 = ($$0 >> 8 & 0xFF) / 255.0F;
/* 268 */       float $$5 = ($$0 & 0xFF) / 255.0F;
/* 269 */       addEffect(new BakedGlyph.Effect($$1 - 1.0F, this.y + 9.0F, this.x + 1.0F, this.y - 1.0F, 0.01F, $$3, $$4, $$5, $$2));
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 278 */     if (this.effects != null) {
/* 279 */       BakedGlyph $$6 = Font.this.getFontSet(Style.DEFAULT_FONT).whiteGlyph();
/* 280 */       VertexConsumer $$7 = this.bufferSource.getBuffer($$6.renderType(this.mode));
/* 281 */       for (BakedGlyph.Effect $$8 : this.effects) {
/* 282 */         $$6.renderEffect($$8, this.pose, $$7, this.packedLightCoords);
/*     */       }
/*     */     } 
/*     */     
/* 286 */     return this.x;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\Font$StringRenderOutput.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */