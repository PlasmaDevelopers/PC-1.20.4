/*     */ package net.minecraft.client.gui.components;
/*     */ 
/*     */ import java.util.Objects;
/*     */ import java.util.function.Consumer;
/*     */ import net.minecraft.SharedConstants;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.client.gui.Font;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.narration.NarratedElementType;
/*     */ import net.minecraft.client.gui.narration.NarrationElementOutput;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.client.renderer.RenderType;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.FormattedText;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ 
/*     */ public class MultiLineEditBox
/*     */   extends AbstractScrollWidget
/*     */ {
/*     */   private static final int CURSOR_INSERT_WIDTH = 1;
/*     */   private static final int CURSOR_INSERT_COLOR = -3092272;
/*     */   private static final String CURSOR_APPEND_CHARACTER = "_";
/*     */   private static final int TEXT_COLOR = -2039584;
/*     */   private static final int PLACEHOLDER_TEXT_COLOR = -857677600;
/*     */   private static final int CURSOR_BLINK_INTERVAL_MS = 300;
/*     */   private final Font font;
/*     */   private final Component placeholder;
/*     */   private final MultilineTextField textField;
/*  29 */   private long focusedTime = Util.getMillis();
/*     */   
/*     */   public MultiLineEditBox(Font $$0, int $$1, int $$2, int $$3, int $$4, Component $$5, Component $$6) {
/*  32 */     super($$1, $$2, $$3, $$4, $$6);
/*  33 */     this.font = $$0;
/*  34 */     this.placeholder = $$5;
/*  35 */     this.textField = new MultilineTextField($$0, $$3 - totalInnerPadding());
/*  36 */     this.textField.setCursorListener(this::scrollToCursor);
/*     */   }
/*     */   
/*     */   public void setCharacterLimit(int $$0) {
/*  40 */     this.textField.setCharacterLimit($$0);
/*     */   }
/*     */   
/*     */   public void setValueListener(Consumer<String> $$0) {
/*  44 */     this.textField.setValueListener($$0);
/*     */   }
/*     */   
/*     */   public void setValue(String $$0) {
/*  48 */     this.textField.setValue($$0);
/*     */   }
/*     */   
/*     */   public String getValue() {
/*  52 */     return this.textField.value();
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateWidgetNarration(NarrationElementOutput $$0) {
/*  57 */     $$0.add(NarratedElementType.TITLE, (Component)Component.translatable("gui.narrate.editBox", new Object[] { getMessage(), getValue() }));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(double $$0, double $$1, int $$2) {
/*  62 */     if (withinContentAreaPoint($$0, $$1) && $$2 == 0) {
/*  63 */       this.textField.setSelecting(Screen.hasShiftDown());
/*  64 */       seekCursorScreen($$0, $$1);
/*  65 */       return true;
/*     */     } 
/*  67 */     return super.mouseClicked($$0, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseDragged(double $$0, double $$1, int $$2, double $$3, double $$4) {
/*  72 */     if (super.mouseDragged($$0, $$1, $$2, $$3, $$4)) {
/*  73 */       return true;
/*     */     }
/*     */     
/*  76 */     if (withinContentAreaPoint($$0, $$1) && $$2 == 0) {
/*  77 */       this.textField.setSelecting(true);
/*  78 */       seekCursorScreen($$0, $$1);
/*  79 */       this.textField.setSelecting(Screen.hasShiftDown());
/*  80 */       return true;
/*     */     } 
/*     */     
/*  83 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean keyPressed(int $$0, int $$1, int $$2) {
/*  88 */     return this.textField.keyPressed($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean charTyped(char $$0, int $$1) {
/*  93 */     if (!this.visible || !isFocused() || !SharedConstants.isAllowedChatCharacter($$0)) {
/*  94 */       return false;
/*     */     }
/*     */     
/*  97 */     this.textField.insertText(Character.toString($$0));
/*  98 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderContents(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 103 */     String $$4 = this.textField.value();
/* 104 */     if ($$4.isEmpty() && !isFocused()) {
/* 105 */       $$0.drawWordWrap(this.font, (FormattedText)this.placeholder, getX() + innerPadding(), getY() + innerPadding(), this.width - totalInnerPadding(), -857677600);
/*     */       
/*     */       return;
/*     */     } 
/* 109 */     int $$5 = this.textField.cursor();
/* 110 */     boolean $$6 = (isFocused() && (Util.getMillis() - this.focusedTime) / 300L % 2L == 0L);
/* 111 */     boolean $$7 = ($$5 < $$4.length());
/*     */     
/* 113 */     int $$8 = 0;
/* 114 */     int $$9 = 0;
/* 115 */     int $$10 = getY() + innerPadding();
/* 116 */     for (MultilineTextField.StringView $$11 : this.textField.iterateLines()) {
/* 117 */       Objects.requireNonNull(this.font); boolean $$12 = withinContentAreaTopBottom($$10, $$10 + 9);
/*     */       
/* 119 */       if ($$6 && $$7 && $$5 >= $$11.beginIndex() && $$5 <= $$11.endIndex()) {
/* 120 */         if ($$12) {
/* 121 */           $$8 = $$0.drawString(this.font, $$4.substring($$11.beginIndex(), $$5), getX() + innerPadding(), $$10, -2039584) - 1;
/* 122 */           Objects.requireNonNull(this.font); $$0.fill($$8, $$10 - 1, $$8 + 1, $$10 + 1 + 9, -3092272);
/* 123 */           $$0.drawString(this.font, $$4.substring($$5, $$11.endIndex()), $$8, $$10, -2039584);
/*     */         } 
/*     */       } else {
/* 126 */         if ($$12) {
/* 127 */           $$8 = $$0.drawString(this.font, $$4.substring($$11.beginIndex(), $$11.endIndex()), getX() + innerPadding(), $$10, -2039584) - 1;
/*     */         }
/* 129 */         $$9 = $$10;
/*     */       } 
/*     */       
/* 132 */       Objects.requireNonNull(this.font); $$10 += 9;
/*     */     } 
/*     */     
/* 135 */     Objects.requireNonNull(this.font); if ($$6 && !$$7 && withinContentAreaTopBottom($$9, $$9 + 9)) {
/* 136 */       $$0.drawString(this.font, "_", $$8, $$9, -3092272);
/*     */     }
/*     */     
/* 139 */     if (this.textField.hasSelection()) {
/* 140 */       MultilineTextField.StringView $$13 = this.textField.getSelected();
/*     */       
/* 142 */       int $$14 = getX() + innerPadding();
/* 143 */       $$10 = getY() + innerPadding();
/* 144 */       for (MultilineTextField.StringView $$15 : this.textField.iterateLines()) {
/* 145 */         if ($$13.beginIndex() > $$15.endIndex()) {
/* 146 */           Objects.requireNonNull(this.font); $$10 += 9; continue;
/*     */         } 
/* 148 */         if ($$15.beginIndex() > $$13.endIndex()) {
/*     */           break;
/*     */         }
/*     */         
/* 152 */         Objects.requireNonNull(this.font); if (withinContentAreaTopBottom($$10, $$10 + 9)) {
/* 153 */           int $$18, $$16 = this.font.width($$4.substring($$15.beginIndex(), Math.max($$13.beginIndex(), $$15.beginIndex())));
/*     */           
/* 155 */           if ($$13.endIndex() > $$15.endIndex()) {
/* 156 */             int $$17 = this.width - innerPadding();
/*     */           } else {
/* 158 */             $$18 = this.font.width($$4.substring($$15.beginIndex(), $$13.endIndex()));
/*     */           } 
/* 160 */           Objects.requireNonNull(this.font); renderHighlight($$0, $$14 + $$16, $$10, $$14 + $$18, $$10 + 9);
/*     */         } 
/*     */         
/* 163 */         Objects.requireNonNull(this.font); $$10 += 9;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderDecorations(GuiGraphics $$0) {
/* 170 */     super.renderDecorations($$0);
/*     */     
/* 172 */     if (this.textField.hasCharacterLimit()) {
/* 173 */       int $$1 = this.textField.characterLimit();
/* 174 */       MutableComponent mutableComponent = Component.translatable("gui.multiLineEditBox.character_limit", new Object[] { Integer.valueOf(this.textField.value().length()), Integer.valueOf($$1) });
/* 175 */       $$0.drawString(this.font, (Component)mutableComponent, getX() + this.width - this.font.width((FormattedText)mutableComponent), getY() + this.height + 4, 10526880);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getInnerHeight() {
/* 181 */     Objects.requireNonNull(this.font); return 9 * this.textField.getLineCount();
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean scrollbarVisible() {
/* 186 */     return (this.textField.getLineCount() > getDisplayableLineCount());
/*     */   }
/*     */ 
/*     */   
/*     */   protected double scrollRate() {
/* 191 */     Objects.requireNonNull(this.font); return 9.0D / 2.0D;
/*     */   }
/*     */   
/*     */   private void renderHighlight(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4) {
/* 195 */     $$0.fill(RenderType.guiTextHighlight(), $$1, $$2, $$3, $$4, -16776961);
/*     */   }
/*     */   
/*     */   private void scrollToCursor() {
/* 199 */     double $$0 = scrollAmount();
/*     */     
/* 201 */     Objects.requireNonNull(this.font); MultilineTextField.StringView $$1 = this.textField.getLineView((int)($$0 / 9.0D));
/*     */     
/* 203 */     if (this.textField.cursor() <= $$1.beginIndex()) {
/* 204 */       Objects.requireNonNull(this.font); $$0 = (this.textField.getLineAtCursor() * 9);
/*     */     } else {
/* 206 */       Objects.requireNonNull(this.font); MultilineTextField.StringView $$2 = this.textField.getLineView((int)(($$0 + this.height) / 9.0D) - 1);
/*     */       
/* 208 */       if (this.textField.cursor() > $$2.endIndex()) {
/* 209 */         Objects.requireNonNull(this.font); Objects.requireNonNull(this.font); $$0 = (this.textField.getLineAtCursor() * 9 - this.height + 9 + totalInnerPadding());
/*     */       } 
/*     */     } 
/*     */     
/* 213 */     setScrollAmount($$0);
/*     */   }
/*     */   
/*     */   private double getDisplayableLineCount() {
/* 217 */     Objects.requireNonNull(this.font); return (this.height - totalInnerPadding()) / 9.0D;
/*     */   }
/*     */   
/*     */   private void seekCursorScreen(double $$0, double $$1) {
/* 221 */     double $$2 = $$0 - getX() - innerPadding();
/* 222 */     double $$3 = $$1 - getY() - innerPadding() + scrollAmount();
/*     */     
/* 224 */     this.textField.seekCursorToPoint($$2, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFocused(boolean $$0) {
/* 229 */     super.setFocused($$0);
/* 230 */     if ($$0)
/* 231 */       this.focusedTime = Util.getMillis(); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\MultiLineEditBox.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */