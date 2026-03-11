/*     */ package net.minecraft.client.gui.components;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import net.minecraft.client.gui.Font;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.FormattedText;
/*     */ import net.minecraft.util.FormattedCharSequence;
/*     */ 
/*     */ public interface MultiLineLabel {
/*  14 */   public static final MultiLineLabel EMPTY = new MultiLineLabel()
/*     */     {
/*     */       public int renderCentered(GuiGraphics $$0, int $$1, int $$2) {
/*  17 */         return $$2;
/*     */       }
/*     */ 
/*     */       
/*     */       public int renderCentered(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4) {
/*  22 */         return $$2;
/*     */       }
/*     */ 
/*     */       
/*     */       public int renderLeftAligned(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4) {
/*  27 */         return $$2;
/*     */       }
/*     */ 
/*     */       
/*     */       public int renderLeftAlignedNoShadow(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4) {
/*  32 */         return $$2;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       public void renderBackgroundCentered(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5) {}
/*     */ 
/*     */       
/*     */       public int getLineCount() {
/*  41 */         return 0;
/*     */       }
/*     */ 
/*     */       
/*     */       public int getWidth() {
/*  46 */         return 0;
/*     */       }
/*     */     };
/*     */   
/*     */   static MultiLineLabel create(Font $$0, FormattedText $$1, int $$2) {
/*  51 */     return createFixed($$0, (List<TextWithWidth>)$$0.split($$1, $$2).stream().map($$1 -> new TextWithWidth($$1, $$0.width($$1))).collect(ImmutableList.toImmutableList()));
/*     */   }
/*     */   
/*     */   static MultiLineLabel create(Font $$0, FormattedText $$1, int $$2, int $$3) {
/*  55 */     return createFixed($$0, (List<TextWithWidth>)$$0.split($$1, $$2).stream().limit($$3).map($$1 -> new TextWithWidth($$1, $$0.width($$1))).collect(ImmutableList.toImmutableList()));
/*     */   }
/*     */   
/*     */   static MultiLineLabel create(Font $$0, Component... $$1) {
/*  59 */     return createFixed($$0, (List<TextWithWidth>)Arrays.<Component>stream($$1).map(Component::getVisualOrderText).map($$1 -> new TextWithWidth($$1, $$0.width($$1))).collect(ImmutableList.toImmutableList()));
/*     */   }
/*     */   
/*     */   static MultiLineLabel create(Font $$0, List<Component> $$1) {
/*  63 */     return createFixed($$0, (List<TextWithWidth>)$$1.stream().map(Component::getVisualOrderText).map($$1 -> new TextWithWidth($$1, $$0.width($$1))).collect(ImmutableList.toImmutableList()));
/*     */   }
/*     */   
/*     */   static MultiLineLabel createFixed(final Font font, final List<TextWithWidth> lines) {
/*  67 */     if (lines.isEmpty()) {
/*  68 */       return EMPTY;
/*     */     }
/*     */     
/*  71 */     return new MultiLineLabel()
/*     */       {
/*     */         private final int width;
/*     */         
/*     */         public int renderCentered(GuiGraphics $$0, int $$1, int $$2) {
/*  76 */           Objects.requireNonNull(font); return renderCentered($$0, $$1, $$2, 9, 16777215);
/*     */         }
/*     */ 
/*     */         
/*     */         public int renderCentered(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4) {
/*  81 */           int $$5 = $$2;
/*  82 */           for (MultiLineLabel.TextWithWidth $$6 : lines) {
/*  83 */             $$0.drawString(font, $$6.text, $$1 - $$6.width / 2, $$5, $$4);
/*  84 */             $$5 += $$3;
/*     */           } 
/*  86 */           return $$5;
/*     */         }
/*     */ 
/*     */         
/*     */         public int renderLeftAligned(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4) {
/*  91 */           int $$5 = $$2;
/*  92 */           for (MultiLineLabel.TextWithWidth $$6 : lines) {
/*  93 */             $$0.drawString(font, $$6.text, $$1, $$5, $$4);
/*  94 */             $$5 += $$3;
/*     */           } 
/*  96 */           return $$5;
/*     */         }
/*     */ 
/*     */         
/*     */         public int renderLeftAlignedNoShadow(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4) {
/* 101 */           int $$5 = $$2;
/* 102 */           for (MultiLineLabel.TextWithWidth $$6 : lines) {
/* 103 */             $$0.drawString(font, $$6.text, $$1, $$5, $$4, false);
/* 104 */             $$5 += $$3;
/*     */           } 
/* 106 */           return $$5;
/*     */         }
/*     */ 
/*     */         
/*     */         public void renderBackgroundCentered(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5) {
/* 111 */           int $$6 = lines.stream().mapToInt($$0 -> $$0.width).max().orElse(0);
/* 112 */           if ($$6 > 0) {
/* 113 */             $$0.fill($$1 - $$6 / 2 - $$4, $$2 - $$4, $$1 + $$6 / 2 + $$4, $$2 + lines
/* 114 */                 .size() * $$3 + $$4, $$5);
/*     */           }
/*     */         }
/*     */ 
/*     */         
/*     */         public int getLineCount() {
/* 120 */           return lines.size();
/*     */         }
/*     */ 
/*     */         
/*     */         public int getWidth() {
/* 125 */           return this.width;
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   int renderCentered(GuiGraphics paramGuiGraphics, int paramInt1, int paramInt2);
/*     */   
/*     */   int renderCentered(GuiGraphics paramGuiGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
/*     */   
/*     */   int renderLeftAligned(GuiGraphics paramGuiGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
/*     */   
/*     */   int renderLeftAlignedNoShadow(GuiGraphics paramGuiGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
/*     */   
/*     */   void renderBackgroundCentered(GuiGraphics paramGuiGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5);
/*     */   
/*     */   int getLineCount();
/*     */   
/*     */   int getWidth();
/*     */   
/*     */   public static class TextWithWidth {
/*     */     final FormattedCharSequence text;
/*     */     final int width;
/*     */     
/*     */     TextWithWidth(FormattedCharSequence $$0, int $$1) {
/* 149 */       this.text = $$0;
/* 150 */       this.width = $$1;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\MultiLineLabel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */