/*     */ package net.minecraft.client.gui.components;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import net.minecraft.client.gui.Font;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class null
/*     */   implements MultiLineLabel
/*     */ {
/*     */   private final int width;
/*     */   
/*     */   null() {
/*  72 */     this.width = lines.stream().mapToInt($$0 -> $$0.width).max().orElse(0);
/*     */   }
/*     */   
/*     */   public int renderCentered(GuiGraphics $$0, int $$1, int $$2) {
/*  76 */     Objects.requireNonNull(font); return renderCentered($$0, $$1, $$2, 9, 16777215);
/*     */   }
/*     */ 
/*     */   
/*     */   public int renderCentered(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4) {
/*  81 */     int $$5 = $$2;
/*  82 */     for (MultiLineLabel.TextWithWidth $$6 : lines) {
/*  83 */       $$0.drawString(font, $$6.text, $$1 - $$6.width / 2, $$5, $$4);
/*  84 */       $$5 += $$3;
/*     */     } 
/*  86 */     return $$5;
/*     */   }
/*     */ 
/*     */   
/*     */   public int renderLeftAligned(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4) {
/*  91 */     int $$5 = $$2;
/*  92 */     for (MultiLineLabel.TextWithWidth $$6 : lines) {
/*  93 */       $$0.drawString(font, $$6.text, $$1, $$5, $$4);
/*  94 */       $$5 += $$3;
/*     */     } 
/*  96 */     return $$5;
/*     */   }
/*     */ 
/*     */   
/*     */   public int renderLeftAlignedNoShadow(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4) {
/* 101 */     int $$5 = $$2;
/* 102 */     for (MultiLineLabel.TextWithWidth $$6 : lines) {
/* 103 */       $$0.drawString(font, $$6.text, $$1, $$5, $$4, false);
/* 104 */       $$5 += $$3;
/*     */     } 
/* 106 */     return $$5;
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderBackgroundCentered(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5) {
/* 111 */     int $$6 = lines.stream().mapToInt($$0 -> $$0.width).max().orElse(0);
/* 112 */     if ($$6 > 0) {
/* 113 */       $$0.fill($$1 - $$6 / 2 - $$4, $$2 - $$4, $$1 + $$6 / 2 + $$4, $$2 + lines
/* 114 */           .size() * $$3 + $$4, $$5);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLineCount() {
/* 120 */     return lines.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWidth() {
/* 125 */     return this.width;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\MultiLineLabel$2.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */