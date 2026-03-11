/*     */ package net.minecraft.client.gui.components;
/*     */ 
/*     */ import java.util.Objects;
/*     */ import java.util.function.BiFunction;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Predicate;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.SharedConstants;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.Font;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.narration.NarratedElementType;
/*     */ import net.minecraft.client.gui.narration.NarrationElementOutput;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.client.renderer.RenderType;
/*     */ import net.minecraft.client.sounds.SoundManager;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.network.chat.Style;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.FormattedCharSequence;
/*     */ import net.minecraft.util.Mth;
/*     */ 
/*     */ public class EditBox
/*     */   extends AbstractWidget
/*     */   implements Renderable {
/*  28 */   private static final WidgetSprites SPRITES = new WidgetSprites(new ResourceLocation("widget/text_field"), new ResourceLocation("widget/text_field_highlighted"));
/*     */   
/*     */   public static final int BACKWARDS = -1;
/*     */   
/*     */   public static final int FORWARDS = 1;
/*     */   
/*     */   private static final int CURSOR_INSERT_WIDTH = 1;
/*     */   private static final int CURSOR_INSERT_COLOR = -3092272;
/*     */   private static final String CURSOR_APPEND_CHARACTER = "_";
/*     */   public static final int DEFAULT_TEXT_COLOR = 14737632;
/*     */   private static final int CURSOR_BLINK_INTERVAL_MS = 300;
/*     */   private final Font font;
/*  40 */   private String value = "";
/*  41 */   private int maxLength = 32;
/*     */   private boolean bordered = true;
/*     */   private boolean canLoseFocus = true;
/*     */   private boolean isEditable = true;
/*     */   private int displayPos;
/*     */   private int cursorPos;
/*     */   private int highlightPos;
/*  48 */   private int textColor = 14737632;
/*  49 */   private int textColorUneditable = 7368816;
/*     */   @Nullable
/*     */   private String suggestion;
/*     */   @Nullable
/*     */   private Consumer<String> responder;
/*  54 */   private Predicate<String> filter = Objects::nonNull;
/*     */   
/*     */   private BiFunction<String, Integer, FormattedCharSequence> formatter;
/*     */   @Nullable
/*     */   private Component hint;
/*     */   private long focusedTime;
/*     */   
/*     */   public EditBox(Font $$0, int $$1, int $$2, Component $$3) {
/*  62 */     this($$0, 0, 0, $$1, $$2, $$3);
/*     */   }
/*     */   
/*     */   public EditBox(Font $$0, int $$1, int $$2, int $$3, int $$4, Component $$5) {
/*  66 */     this($$0, $$1, $$2, $$3, $$4, (EditBox)null, $$5);
/*     */   }
/*     */   
/*     */   public EditBox(Font $$0, int $$1, int $$2, int $$3, int $$4, @Nullable EditBox $$5, Component $$6) {
/*  70 */     super($$1, $$2, $$3, $$4, $$6); this.formatter = (($$0, $$1) -> FormattedCharSequence.forward($$0, Style.EMPTY)); this.focusedTime = Util.getMillis();
/*  71 */     this.font = $$0;
/*  72 */     if ($$5 != null) {
/*  73 */       setValue($$5.getValue());
/*     */     }
/*     */   }
/*     */   
/*     */   public void setResponder(Consumer<String> $$0) {
/*  78 */     this.responder = $$0;
/*     */   }
/*     */   
/*     */   public void setFormatter(BiFunction<String, Integer, FormattedCharSequence> $$0) {
/*  82 */     this.formatter = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected MutableComponent createNarrationMessage() {
/*  87 */     Component $$0 = getMessage();
/*  88 */     return Component.translatable("gui.narrate.editBox", new Object[] { $$0, this.value });
/*     */   }
/*     */   
/*     */   public void setValue(String $$0) {
/*  92 */     if (!this.filter.test($$0)) {
/*     */       return;
/*     */     }
/*     */     
/*  96 */     if ($$0.length() > this.maxLength) {
/*  97 */       this.value = $$0.substring(0, this.maxLength);
/*     */     } else {
/*  99 */       this.value = $$0;
/*     */     } 
/*     */     
/* 102 */     moveCursorToEnd(false);
/* 103 */     setHighlightPos(this.cursorPos);
/* 104 */     onValueChange($$0);
/*     */   }
/*     */   
/*     */   public String getValue() {
/* 108 */     return this.value;
/*     */   }
/*     */   
/*     */   public String getHighlighted() {
/* 112 */     int $$0 = Math.min(this.cursorPos, this.highlightPos);
/* 113 */     int $$1 = Math.max(this.cursorPos, this.highlightPos);
/*     */     
/* 115 */     return this.value.substring($$0, $$1);
/*     */   }
/*     */   
/*     */   public void setFilter(Predicate<String> $$0) {
/* 119 */     this.filter = $$0;
/*     */   }
/*     */   
/*     */   public void insertText(String $$0) {
/* 123 */     int $$1 = Math.min(this.cursorPos, this.highlightPos);
/* 124 */     int $$2 = Math.max(this.cursorPos, this.highlightPos);
/* 125 */     int $$3 = this.maxLength - this.value.length() - $$1 - $$2;
/* 126 */     if ($$3 <= 0) {
/*     */       return;
/*     */     }
/*     */     
/* 130 */     String $$4 = SharedConstants.filterText($$0);
/* 131 */     int $$5 = $$4.length();
/* 132 */     if ($$3 < $$5) {
/* 133 */       if (Character.isHighSurrogate($$4.charAt($$3 - 1))) {
/* 134 */         $$3--;
/*     */       }
/* 136 */       $$4 = $$4.substring(0, $$3);
/* 137 */       $$5 = $$3;
/*     */     } 
/*     */     
/* 140 */     String $$6 = (new StringBuilder(this.value)).replace($$1, $$2, $$4).toString();
/* 141 */     if (!this.filter.test($$6)) {
/*     */       return;
/*     */     }
/*     */     
/* 145 */     this.value = $$6;
/* 146 */     setCursorPosition($$1 + $$5);
/* 147 */     setHighlightPos(this.cursorPos);
/*     */     
/* 149 */     onValueChange(this.value);
/*     */   }
/*     */   
/*     */   private void onValueChange(String $$0) {
/* 153 */     if (this.responder != null) {
/* 154 */       this.responder.accept($$0);
/*     */     }
/*     */   }
/*     */   
/*     */   private void deleteText(int $$0) {
/* 159 */     if (Screen.hasControlDown()) {
/* 160 */       deleteWords($$0);
/*     */     } else {
/* 162 */       deleteChars($$0);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void deleteWords(int $$0) {
/* 167 */     if (this.value.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/* 171 */     if (this.highlightPos != this.cursorPos) {
/* 172 */       insertText("");
/*     */       
/*     */       return;
/*     */     } 
/* 176 */     deleteCharsToPos(getWordPosition($$0));
/*     */   }
/*     */   
/*     */   public void deleteChars(int $$0) {
/* 180 */     deleteCharsToPos(getCursorPos($$0));
/*     */   }
/*     */   
/*     */   public void deleteCharsToPos(int $$0) {
/* 184 */     if (this.value.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/* 188 */     if (this.highlightPos != this.cursorPos) {
/* 189 */       insertText("");
/*     */       
/*     */       return;
/*     */     } 
/* 193 */     int $$1 = Math.min($$0, this.cursorPos);
/* 194 */     int $$2 = Math.max($$0, this.cursorPos);
/* 195 */     if ($$1 == $$2) {
/*     */       return;
/*     */     }
/*     */     
/* 199 */     String $$3 = (new StringBuilder(this.value)).delete($$1, $$2).toString();
/*     */     
/* 201 */     if (!this.filter.test($$3)) {
/*     */       return;
/*     */     }
/*     */     
/* 205 */     this.value = $$3;
/* 206 */     moveCursorTo($$1, false);
/*     */   }
/*     */   
/*     */   public int getWordPosition(int $$0) {
/* 210 */     return getWordPosition($$0, getCursorPosition());
/*     */   }
/*     */   
/*     */   private int getWordPosition(int $$0, int $$1) {
/* 214 */     return getWordPosition($$0, $$1, true);
/*     */   }
/*     */   
/*     */   private int getWordPosition(int $$0, int $$1, boolean $$2) {
/* 218 */     int $$3 = $$1;
/* 219 */     boolean $$4 = ($$0 < 0);
/* 220 */     int $$5 = Math.abs($$0);
/*     */     
/* 222 */     for (int $$6 = 0; $$6 < $$5; $$6++) {
/* 223 */       if ($$4) {
/* 224 */         while ($$2 && $$3 > 0 && this.value.charAt($$3 - 1) == ' ') {
/* 225 */           $$3--;
/*     */         }
/* 227 */         while ($$3 > 0 && this.value.charAt($$3 - 1) != ' ') {
/* 228 */           $$3--;
/*     */         }
/*     */       } else {
/* 231 */         int $$7 = this.value.length();
/*     */         
/* 233 */         $$3 = this.value.indexOf(' ', $$3);
/* 234 */         if ($$3 == -1) {
/* 235 */           $$3 = $$7;
/*     */         } else {
/* 237 */           while ($$2 && $$3 < $$7 && this.value.charAt($$3) == ' ') {
/* 238 */             $$3++;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 244 */     return $$3;
/*     */   }
/*     */   
/*     */   public void moveCursor(int $$0, boolean $$1) {
/* 248 */     moveCursorTo(getCursorPos($$0), $$1);
/*     */   }
/*     */   
/*     */   private int getCursorPos(int $$0) {
/* 252 */     return Util.offsetByCodepoints(this.value, this.cursorPos, $$0);
/*     */   }
/*     */   
/*     */   public void moveCursorTo(int $$0, boolean $$1) {
/* 256 */     setCursorPosition($$0);
/*     */     
/* 258 */     if (!$$1) {
/* 259 */       setHighlightPos(this.cursorPos);
/*     */     }
/*     */     
/* 262 */     onValueChange(this.value);
/*     */   }
/*     */   
/*     */   public void setCursorPosition(int $$0) {
/* 266 */     this.cursorPos = Mth.clamp($$0, 0, this.value.length());
/* 267 */     scrollTo(this.cursorPos);
/*     */   }
/*     */   
/*     */   public void moveCursorToStart(boolean $$0) {
/* 271 */     moveCursorTo(0, $$0);
/*     */   }
/*     */   
/*     */   public void moveCursorToEnd(boolean $$0) {
/* 275 */     moveCursorTo(this.value.length(), $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean keyPressed(int $$0, int $$1, int $$2) {
/* 280 */     if (!isActive() || !isFocused()) {
/* 281 */       return false;
/*     */     }
/*     */     
/* 284 */     switch ($$0) {
/*     */       case 263:
/* 286 */         if (Screen.hasControlDown()) {
/* 287 */           moveCursorTo(getWordPosition(-1), Screen.hasShiftDown());
/*     */         } else {
/* 289 */           moveCursor(-1, Screen.hasShiftDown());
/*     */         } 
/*     */         
/* 292 */         return true;
/*     */       case 262:
/* 294 */         if (Screen.hasControlDown()) {
/* 295 */           moveCursorTo(getWordPosition(1), Screen.hasShiftDown());
/*     */         } else {
/* 297 */           moveCursor(1, Screen.hasShiftDown());
/*     */         } 
/*     */         
/* 300 */         return true;
/*     */       case 259:
/* 302 */         if (this.isEditable) {
/* 303 */           deleteText(-1);
/*     */         }
/*     */         
/* 306 */         return true;
/*     */       case 261:
/* 308 */         if (this.isEditable) {
/* 309 */           deleteText(1);
/*     */         }
/*     */         
/* 312 */         return true;
/*     */       case 268:
/* 314 */         moveCursorToStart(Screen.hasShiftDown());
/*     */         
/* 316 */         return true;
/*     */       case 269:
/* 318 */         moveCursorToEnd(Screen.hasShiftDown());
/*     */         
/* 320 */         return true;
/*     */     } 
/*     */     
/* 323 */     if (Screen.isSelectAll($$0)) {
/* 324 */       moveCursorToEnd(false);
/* 325 */       setHighlightPos(0);
/* 326 */       return true;
/* 327 */     }  if (Screen.isCopy($$0)) {
/* 328 */       (Minecraft.getInstance()).keyboardHandler.setClipboard(getHighlighted());
/* 329 */       return true;
/* 330 */     }  if (Screen.isPaste($$0)) {
/* 331 */       if (isEditable()) {
/* 332 */         insertText((Minecraft.getInstance()).keyboardHandler.getClipboard());
/*     */       }
/* 334 */       return true;
/* 335 */     }  if (Screen.isCut($$0)) {
/* 336 */       (Minecraft.getInstance()).keyboardHandler.setClipboard(getHighlighted());
/* 337 */       if (isEditable()) {
/* 338 */         insertText("");
/*     */       }
/* 340 */       return true;
/*     */     } 
/*     */     
/* 343 */     return false;
/*     */   }
/*     */   
/*     */   public boolean canConsumeInput() {
/* 347 */     return (isActive() && isFocused() && isEditable());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean charTyped(char $$0, int $$1) {
/* 352 */     if (!canConsumeInput()) {
/* 353 */       return false;
/*     */     }
/* 355 */     if (SharedConstants.isAllowedChatCharacter($$0)) {
/* 356 */       if (this.isEditable) {
/* 357 */         insertText(Character.toString($$0));
/*     */       }
/*     */       
/* 360 */       return true;
/*     */     } 
/*     */     
/* 363 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onClick(double $$0, double $$1) {
/* 368 */     int $$2 = Mth.floor($$0) - getX();
/* 369 */     if (this.bordered) {
/* 370 */       $$2 -= 4;
/*     */     }
/* 372 */     String $$3 = this.font.plainSubstrByWidth(this.value.substring(this.displayPos), getInnerWidth());
/* 373 */     moveCursorTo(this.font.plainSubstrByWidth($$3, $$2).length() + this.displayPos, Screen.hasShiftDown());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void playDownSound(SoundManager $$0) {}
/*     */ 
/*     */   
/*     */   public void renderWidget(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 382 */     if (!isVisible()) {
/*     */       return;
/*     */     }
/*     */     
/* 386 */     if (isBordered()) {
/* 387 */       ResourceLocation $$4 = SPRITES.get(isActive(), isFocused());
/* 388 */       $$0.blitSprite($$4, getX(), getY(), getWidth(), getHeight());
/*     */     } 
/*     */     
/* 391 */     int $$5 = this.isEditable ? this.textColor : this.textColorUneditable;
/* 392 */     int $$6 = this.cursorPos - this.displayPos;
/* 393 */     String $$7 = this.font.plainSubstrByWidth(this.value.substring(this.displayPos), getInnerWidth());
/* 394 */     boolean $$8 = ($$6 >= 0 && $$6 <= $$7.length());
/* 395 */     boolean $$9 = (isFocused() && (Util.getMillis() - this.focusedTime) / 300L % 2L == 0L && $$8);
/* 396 */     int $$10 = this.bordered ? (getX() + 4) : getX();
/* 397 */     int $$11 = this.bordered ? (getY() + (this.height - 8) / 2) : getY();
/* 398 */     int $$12 = $$10;
/*     */     
/* 400 */     int $$13 = Mth.clamp(this.highlightPos - this.displayPos, 0, $$7.length());
/*     */     
/* 402 */     if (!$$7.isEmpty()) {
/* 403 */       String $$14 = $$8 ? $$7.substring(0, $$6) : $$7;
/* 404 */       $$12 = $$0.drawString(this.font, this.formatter.apply($$14, Integer.valueOf(this.displayPos)), $$12, $$11, $$5);
/*     */     } 
/*     */     
/* 407 */     boolean $$15 = (this.cursorPos < this.value.length() || this.value.length() >= getMaxLength());
/* 408 */     int $$16 = $$12;
/*     */     
/* 410 */     if (!$$8) {
/* 411 */       $$16 = ($$6 > 0) ? ($$10 + this.width) : $$10;
/* 412 */     } else if ($$15) {
/* 413 */       $$16--;
/* 414 */       $$12--;
/*     */     } 
/*     */     
/* 417 */     if (!$$7.isEmpty() && $$8 && $$6 < $$7.length()) {
/* 418 */       $$0.drawString(this.font, this.formatter.apply($$7.substring($$6), Integer.valueOf(this.cursorPos)), $$12, $$11, $$5);
/*     */     }
/*     */     
/* 421 */     if (this.hint != null && $$7.isEmpty() && !isFocused()) {
/* 422 */       $$0.drawString(this.font, this.hint, $$12, $$11, $$5);
/*     */     }
/*     */     
/* 425 */     if (!$$15 && this.suggestion != null) {
/* 426 */       $$0.drawString(this.font, this.suggestion, $$16 - 1, $$11, -8355712);
/*     */     }
/*     */     
/* 429 */     if ($$9) {
/* 430 */       if ($$15) {
/* 431 */         Objects.requireNonNull(this.font); $$0.fill(RenderType.guiOverlay(), $$16, $$11 - 1, $$16 + 1, $$11 + 1 + 9, -3092272);
/*     */       } else {
/* 433 */         $$0.drawString(this.font, "_", $$16, $$11, $$5);
/*     */       } 
/*     */     }
/*     */     
/* 437 */     if ($$13 != $$6) {
/* 438 */       int $$17 = $$10 + this.font.width($$7.substring(0, $$13));
/* 439 */       Objects.requireNonNull(this.font); renderHighlight($$0, $$16, $$11 - 1, $$17 - 1, $$11 + 1 + 9);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void renderHighlight(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4) {
/* 444 */     if ($$1 < $$3) {
/* 445 */       int $$5 = $$1;
/* 446 */       $$1 = $$3;
/* 447 */       $$3 = $$5;
/*     */     } 
/* 449 */     if ($$2 < $$4) {
/* 450 */       int $$6 = $$2;
/* 451 */       $$2 = $$4;
/* 452 */       $$4 = $$6;
/*     */     } 
/*     */     
/* 455 */     if ($$3 > getX() + this.width) {
/* 456 */       $$3 = getX() + this.width;
/*     */     }
/* 458 */     if ($$1 > getX() + this.width) {
/* 459 */       $$1 = getX() + this.width;
/*     */     }
/*     */     
/* 462 */     $$0.fill(RenderType.guiTextHighlight(), $$1, $$2, $$3, $$4, -16776961);
/*     */   }
/*     */   
/*     */   public void setMaxLength(int $$0) {
/* 466 */     this.maxLength = $$0;
/*     */     
/* 468 */     if (this.value.length() > $$0) {
/* 469 */       this.value = this.value.substring(0, $$0);
/* 470 */       onValueChange(this.value);
/*     */     } 
/*     */   }
/*     */   
/*     */   private int getMaxLength() {
/* 475 */     return this.maxLength;
/*     */   }
/*     */   
/*     */   public int getCursorPosition() {
/* 479 */     return this.cursorPos;
/*     */   }
/*     */   
/*     */   public boolean isBordered() {
/* 483 */     return this.bordered;
/*     */   }
/*     */   
/*     */   public void setBordered(boolean $$0) {
/* 487 */     this.bordered = $$0;
/*     */   }
/*     */   
/*     */   public void setTextColor(int $$0) {
/* 491 */     this.textColor = $$0;
/*     */   }
/*     */   
/*     */   public void setTextColorUneditable(int $$0) {
/* 495 */     this.textColorUneditable = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFocused(boolean $$0) {
/* 500 */     if (!this.canLoseFocus && !$$0) {
/*     */       return;
/*     */     }
/* 503 */     super.setFocused($$0);
/* 504 */     if ($$0) {
/* 505 */       this.focusedTime = Util.getMillis();
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean isEditable() {
/* 510 */     return this.isEditable;
/*     */   }
/*     */   
/*     */   public void setEditable(boolean $$0) {
/* 514 */     this.isEditable = $$0;
/*     */   }
/*     */   
/*     */   public int getInnerWidth() {
/* 518 */     return isBordered() ? (this.width - 8) : this.width;
/*     */   }
/*     */   
/*     */   public void setHighlightPos(int $$0) {
/* 522 */     this.highlightPos = Mth.clamp($$0, 0, this.value.length());
/* 523 */     scrollTo(this.highlightPos);
/*     */   }
/*     */ 
/*     */   
/*     */   private void scrollTo(int $$0) {
/* 528 */     if (this.font == null) {
/*     */       return;
/*     */     }
/*     */     
/* 532 */     this.displayPos = Math.min(this.displayPos, this.value.length());
/*     */     
/* 534 */     int $$1 = getInnerWidth();
/* 535 */     String $$2 = this.font.plainSubstrByWidth(this.value.substring(this.displayPos), $$1);
/* 536 */     int $$3 = $$2.length() + this.displayPos;
/*     */     
/* 538 */     if ($$0 == this.displayPos) {
/* 539 */       this.displayPos -= this.font.plainSubstrByWidth(this.value, $$1, true).length();
/*     */     }
/* 541 */     if ($$0 > $$3) {
/* 542 */       this.displayPos += $$0 - $$3;
/* 543 */     } else if ($$0 <= this.displayPos) {
/* 544 */       this.displayPos -= this.displayPos - $$0;
/*     */     } 
/*     */     
/* 547 */     this.displayPos = Mth.clamp(this.displayPos, 0, this.value.length());
/*     */   }
/*     */   
/*     */   public void setCanLoseFocus(boolean $$0) {
/* 551 */     this.canLoseFocus = $$0;
/*     */   }
/*     */   
/*     */   public boolean isVisible() {
/* 555 */     return this.visible;
/*     */   }
/*     */   
/*     */   public void setVisible(boolean $$0) {
/* 559 */     this.visible = $$0;
/*     */   }
/*     */   
/*     */   public void setSuggestion(@Nullable String $$0) {
/* 563 */     this.suggestion = $$0;
/*     */   }
/*     */   
/*     */   public int getScreenX(int $$0) {
/* 567 */     if ($$0 > this.value.length()) {
/* 568 */       return getX();
/*     */     }
/* 570 */     return getX() + this.font.width(this.value.substring(0, $$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateWidgetNarration(NarrationElementOutput $$0) {
/* 575 */     $$0.add(NarratedElementType.TITLE, (Component)createNarrationMessage());
/*     */   }
/*     */   
/*     */   public void setHint(Component $$0) {
/* 579 */     this.hint = $$0;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\EditBox.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */