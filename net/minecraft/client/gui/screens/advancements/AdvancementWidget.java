/*     */ package net.minecraft.client.gui.screens.advancements;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.advancements.AdvancementNode;
/*     */ import net.minecraft.advancements.AdvancementProgress;
/*     */ import net.minecraft.advancements.DisplayInfo;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.StringSplitter;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.locale.Language;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.ComponentUtils;
/*     */ import net.minecraft.network.chat.FormattedText;
/*     */ import net.minecraft.network.chat.Style;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.FormattedCharSequence;
/*     */ import net.minecraft.util.Mth;
/*     */ 
/*     */ public class AdvancementWidget {
/*  24 */   private static final ResourceLocation TITLE_BOX_SPRITE = new ResourceLocation("advancements/title_box");
/*     */   private static final int HEIGHT = 26;
/*     */   private static final int BOX_X = 0;
/*     */   private static final int BOX_WIDTH = 200;
/*     */   private static final int FRAME_WIDTH = 26;
/*     */   private static final int ICON_X = 8;
/*     */   private static final int ICON_Y = 5;
/*     */   private static final int ICON_WIDTH = 26;
/*     */   private static final int TITLE_PADDING_LEFT = 3;
/*     */   private static final int TITLE_PADDING_RIGHT = 5;
/*     */   private static final int TITLE_X = 32;
/*     */   private static final int TITLE_Y = 9;
/*     */   private static final int TITLE_MAX_WIDTH = 163;
/*  37 */   private static final int[] TEST_SPLIT_OFFSETS = new int[] { 0, 10, -10, 25, -25 };
/*     */   
/*     */   private final AdvancementTab tab;
/*     */   private final AdvancementNode advancementNode;
/*     */   private final DisplayInfo display;
/*     */   private final FormattedCharSequence title;
/*     */   private final int width;
/*     */   private final List<FormattedCharSequence> description;
/*     */   private final Minecraft minecraft;
/*     */   @Nullable
/*     */   private AdvancementWidget parent;
/*  48 */   private final List<AdvancementWidget> children = Lists.newArrayList();
/*     */   @Nullable
/*     */   private AdvancementProgress progress;
/*     */   private final int x;
/*     */   private final int y;
/*     */   
/*     */   public AdvancementWidget(AdvancementTab $$0, Minecraft $$1, AdvancementNode $$2, DisplayInfo $$3) {
/*  55 */     this.tab = $$0;
/*  56 */     this.advancementNode = $$2;
/*  57 */     this.display = $$3;
/*  58 */     this.minecraft = $$1;
/*  59 */     this.title = Language.getInstance().getVisualOrder($$1.font.substrByWidth((FormattedText)$$3.getTitle(), 163));
/*  60 */     this.x = Mth.floor($$3.getX() * 28.0F);
/*  61 */     this.y = Mth.floor($$3.getY() * 27.0F);
/*     */     
/*  63 */     int $$4 = $$2.advancement().requirements().size();
/*  64 */     int $$5 = String.valueOf($$4).length();
/*  65 */     int $$6 = ($$4 > 1) ? ($$1.font.width("  ") + $$1.font.width("0") * $$5 * 2 + $$1.font.width("/")) : 0;
/*  66 */     int $$7 = 29 + $$1.font.width(this.title) + $$6;
/*  67 */     this.description = Language.getInstance().getVisualOrder(findOptimalLines((Component)ComponentUtils.mergeStyles($$3.getDescription().copy(), Style.EMPTY.withColor($$3.getType().getChatColor())), $$7));
/*  68 */     for (FormattedCharSequence $$8 : this.description) {
/*  69 */       $$7 = Math.max($$7, $$1.font.width($$8));
/*     */     }
/*  71 */     this.width = $$7 + 3 + 5;
/*     */   }
/*     */   
/*     */   private static float getMaxWidth(StringSplitter $$0, List<FormattedText> $$1) {
/*  75 */     Objects.requireNonNull($$0); return (float)$$1.stream().mapToDouble($$0::stringWidth).max().orElse(0.0D);
/*     */   }
/*     */   
/*     */   private List<FormattedText> findOptimalLines(Component $$0, int $$1) {
/*  79 */     StringSplitter $$2 = this.minecraft.font.getSplitter();
/*     */     
/*  81 */     List<FormattedText> $$3 = null;
/*  82 */     float $$4 = Float.MAX_VALUE;
/*     */     
/*  84 */     for (int $$5 : TEST_SPLIT_OFFSETS) {
/*  85 */       List<FormattedText> $$6 = $$2.splitLines((FormattedText)$$0, $$1 - $$5, Style.EMPTY);
/*  86 */       float $$7 = Math.abs(getMaxWidth($$2, $$6) - $$1);
/*  87 */       if ($$7 <= 10.0F) {
/*  88 */         return $$6;
/*     */       }
/*  90 */       if ($$7 < $$4) {
/*  91 */         $$4 = $$7;
/*  92 */         $$3 = $$6;
/*     */       } 
/*     */     } 
/*     */     
/*  96 */     return $$3;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private AdvancementWidget getFirstVisibleParent(AdvancementNode $$0) {
/*     */     do {
/* 102 */       $$0 = $$0.parent();
/* 103 */     } while ($$0 != null && $$0.advancement().display().isEmpty());
/* 104 */     if ($$0 == null || $$0.advancement().display().isEmpty()) {
/* 105 */       return null;
/*     */     }
/* 107 */     return this.tab.getWidget($$0.holder());
/*     */   }
/*     */   
/*     */   public void drawConnectivity(GuiGraphics $$0, int $$1, int $$2, boolean $$3) {
/* 111 */     if (this.parent != null) {
/* 112 */       int $$4 = $$1 + this.parent.x + 13;
/* 113 */       int $$5 = $$1 + this.parent.x + 26 + 4;
/* 114 */       int $$6 = $$2 + this.parent.y + 13;
/* 115 */       int $$7 = $$1 + this.x + 13;
/* 116 */       int $$8 = $$2 + this.y + 13;
/* 117 */       int $$9 = $$3 ? -16777216 : -1;
/* 118 */       if ($$3) {
/* 119 */         $$0.hLine($$5, $$4, $$6 - 1, $$9);
/* 120 */         $$0.hLine($$5 + 1, $$4, $$6, $$9);
/* 121 */         $$0.hLine($$5, $$4, $$6 + 1, $$9);
/* 122 */         $$0.hLine($$7, $$5 - 1, $$8 - 1, $$9);
/* 123 */         $$0.hLine($$7, $$5 - 1, $$8, $$9);
/* 124 */         $$0.hLine($$7, $$5 - 1, $$8 + 1, $$9);
/* 125 */         $$0.vLine($$5 - 1, $$8, $$6, $$9);
/* 126 */         $$0.vLine($$5 + 1, $$8, $$6, $$9);
/*     */       } else {
/* 128 */         $$0.hLine($$5, $$4, $$6, $$9);
/* 129 */         $$0.hLine($$7, $$5, $$8, $$9);
/* 130 */         $$0.vLine($$5, $$8, $$6, $$9);
/*     */       } 
/*     */     } 
/*     */     
/* 134 */     for (AdvancementWidget $$10 : this.children) {
/* 135 */       $$10.drawConnectivity($$0, $$1, $$2, $$3);
/*     */     }
/*     */   }
/*     */   
/*     */   public void draw(GuiGraphics $$0, int $$1, int $$2) {
/* 140 */     if (!this.display.isHidden() || (this.progress != null && this.progress.isDone())) {
/* 141 */       AdvancementWidgetType $$5; float $$3 = (this.progress == null) ? 0.0F : this.progress.getPercent();
/*     */ 
/*     */       
/* 144 */       if ($$3 >= 1.0F) {
/* 145 */         AdvancementWidgetType $$4 = AdvancementWidgetType.OBTAINED;
/*     */       } else {
/* 147 */         $$5 = AdvancementWidgetType.UNOBTAINED;
/*     */       } 
/*     */       
/* 150 */       $$0.blitSprite($$5.frameSprite(this.display.getType()), $$1 + this.x + 3, $$2 + this.y, 26, 26);
/* 151 */       $$0.renderFakeItem(this.display.getIcon(), $$1 + this.x + 8, $$2 + this.y + 5);
/*     */     } 
/*     */     
/* 154 */     for (AdvancementWidget $$6 : this.children) {
/* 155 */       $$6.draw($$0, $$1, $$2);
/*     */     }
/*     */   }
/*     */   
/*     */   public int getWidth() {
/* 160 */     return this.width;
/*     */   }
/*     */   
/*     */   public void setProgress(AdvancementProgress $$0) {
/* 164 */     this.progress = $$0;
/*     */   }
/*     */   
/*     */   public void addChild(AdvancementWidget $$0) {
/* 168 */     this.children.add($$0);
/*     */   } public void drawHover(GuiGraphics $$0, int $$1, int $$2, float $$3, int $$4, int $$5) {
/*     */     AdvancementWidgetType $$21, $$22, $$23;
/*     */     int $$27;
/* 172 */     boolean $$6 = ($$4 + $$1 + this.x + this.width + 26 >= (this.tab.getScreen()).width);
/* 173 */     Component $$7 = (this.progress == null) ? null : this.progress.getProgressText();
/* 174 */     int $$8 = ($$7 == null) ? 0 : this.minecraft.font.width((FormattedText)$$7);
/* 175 */     Objects.requireNonNull(this.minecraft.font); boolean $$9 = (113 - $$2 - this.y - 26 <= 6 + this.description.size() * 9);
/* 176 */     float $$10 = (this.progress == null) ? 0.0F : this.progress.getPercent();
/*     */ 
/*     */ 
/*     */     
/* 180 */     int $$11 = Mth.floor($$10 * this.width);
/*     */     
/* 182 */     if ($$10 >= 1.0F) {
/* 183 */       $$11 = this.width / 2;
/* 184 */       AdvancementWidgetType $$12 = AdvancementWidgetType.OBTAINED;
/* 185 */       AdvancementWidgetType $$13 = AdvancementWidgetType.OBTAINED;
/* 186 */       AdvancementWidgetType $$14 = AdvancementWidgetType.OBTAINED;
/* 187 */     } else if ($$11 < 2) {
/* 188 */       $$11 = this.width / 2;
/* 189 */       AdvancementWidgetType $$15 = AdvancementWidgetType.UNOBTAINED;
/* 190 */       AdvancementWidgetType $$16 = AdvancementWidgetType.UNOBTAINED;
/* 191 */       AdvancementWidgetType $$17 = AdvancementWidgetType.UNOBTAINED;
/* 192 */     } else if ($$11 > this.width - 2) {
/* 193 */       $$11 = this.width / 2;
/* 194 */       AdvancementWidgetType $$18 = AdvancementWidgetType.OBTAINED;
/* 195 */       AdvancementWidgetType $$19 = AdvancementWidgetType.OBTAINED;
/* 196 */       AdvancementWidgetType $$20 = AdvancementWidgetType.UNOBTAINED;
/*     */     } else {
/* 198 */       $$21 = AdvancementWidgetType.OBTAINED;
/* 199 */       $$22 = AdvancementWidgetType.UNOBTAINED;
/* 200 */       $$23 = AdvancementWidgetType.UNOBTAINED;
/*     */     } 
/* 202 */     int $$24 = this.width - $$11;
/*     */     
/* 204 */     RenderSystem.enableBlend();
/*     */     
/* 206 */     int $$25 = $$2 + this.y;
/*     */     
/* 208 */     if ($$6) {
/* 209 */       int $$26 = $$1 + this.x - this.width + 26 + 6;
/*     */     } else {
/* 211 */       $$27 = $$1 + this.x;
/*     */     } 
/*     */     
/* 214 */     Objects.requireNonNull(this.minecraft.font); int $$28 = 32 + this.description.size() * 9;
/* 215 */     if (!this.description.isEmpty()) {
/* 216 */       if ($$9) {
/* 217 */         $$0.blitSprite(TITLE_BOX_SPRITE, $$27, $$25 + 26 - $$28, this.width, $$28);
/*     */       } else {
/* 219 */         $$0.blitSprite(TITLE_BOX_SPRITE, $$27, $$25, this.width, $$28);
/*     */       } 
/*     */     }
/*     */     
/* 223 */     $$0.blitSprite($$21.boxSprite(), 200, 26, 0, 0, $$27, $$25, $$11, 26);
/* 224 */     $$0.blitSprite($$22.boxSprite(), 200, 26, 200 - $$24, 0, $$27 + $$11, $$25, $$24, 26);
/*     */     
/* 226 */     $$0.blitSprite($$23.frameSprite(this.display.getType()), $$1 + this.x + 3, $$2 + this.y, 26, 26);
/*     */     
/* 228 */     if ($$6) {
/* 229 */       $$0.drawString(this.minecraft.font, this.title, $$27 + 5, $$2 + this.y + 9, -1);
/* 230 */       if ($$7 != null) {
/* 231 */         $$0.drawString(this.minecraft.font, $$7, $$1 + this.x - $$8, $$2 + this.y + 9, -1);
/*     */       }
/*     */     } else {
/* 234 */       $$0.drawString(this.minecraft.font, this.title, $$1 + this.x + 32, $$2 + this.y + 9, -1);
/* 235 */       if ($$7 != null) {
/* 236 */         $$0.drawString(this.minecraft.font, $$7, $$1 + this.x + this.width - $$8 - 5, $$2 + this.y + 9, -1);
/*     */       }
/*     */     } 
/*     */     
/* 240 */     if ($$9) {
/* 241 */       for (int $$29 = 0; $$29 < this.description.size(); $$29++) {
/* 242 */         Objects.requireNonNull(this.minecraft.font); $$0.drawString(this.minecraft.font, this.description.get($$29), $$27 + 5, $$25 + 26 - $$28 + 7 + $$29 * 9, -5592406, false);
/*     */       } 
/*     */     } else {
/* 245 */       for (int $$30 = 0; $$30 < this.description.size(); $$30++) {
/* 246 */         Objects.requireNonNull(this.minecraft.font); $$0.drawString(this.minecraft.font, this.description.get($$30), $$27 + 5, $$2 + this.y + 9 + 17 + $$30 * 9, -5592406, false);
/*     */       } 
/*     */     } 
/*     */     
/* 250 */     $$0.renderFakeItem(this.display.getIcon(), $$1 + this.x + 8, $$2 + this.y + 5);
/*     */   }
/*     */   
/*     */   public boolean isMouseOver(int $$0, int $$1, int $$2, int $$3) {
/* 254 */     if (this.display.isHidden() && (this.progress == null || !this.progress.isDone())) {
/* 255 */       return false;
/*     */     }
/* 257 */     int $$4 = $$0 + this.x;
/* 258 */     int $$5 = $$4 + 26;
/* 259 */     int $$6 = $$1 + this.y;
/* 260 */     int $$7 = $$6 + 26;
/* 261 */     return ($$2 >= $$4 && $$2 <= $$5 && $$3 >= $$6 && $$3 <= $$7);
/*     */   }
/*     */   
/*     */   public void attachToParent() {
/* 265 */     if (this.parent == null && this.advancementNode.parent() != null) {
/* 266 */       this.parent = getFirstVisibleParent(this.advancementNode);
/* 267 */       if (this.parent != null) {
/* 268 */         this.parent.addChild(this);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getY() {
/* 274 */     return this.y;
/*     */   }
/*     */   
/*     */   public int getX() {
/* 278 */     return this.x;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\advancements\AdvancementWidget.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */