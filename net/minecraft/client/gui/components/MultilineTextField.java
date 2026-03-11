/*     */ package net.minecraft.client.gui.components;
/*     */ 
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Consumer;
/*     */ import net.minecraft.SharedConstants;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.Font;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.network.chat.Style;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.StringUtil;
/*     */ 
/*     */ 
/*     */ public class MultilineTextField
/*     */ {
/*     */   public static final int NO_CHARACTER_LIMIT = 2147483647;
/*     */   private static final int LINE_SEEK_PIXEL_BIAS = 2;
/*     */   private final Font font;
/*  22 */   private final List<StringView> displayLines = Lists.newArrayList();
/*     */   
/*     */   private String value;
/*     */   private int cursor;
/*     */   private int selectCursor;
/*     */   private boolean selecting;
/*  28 */   private int characterLimit = Integer.MAX_VALUE; private final int width; private Consumer<String> valueListener = $$0 -> {
/*     */     
/*     */     }; private Runnable cursorListener = () -> {
/*     */     
/*     */     };
/*     */   
/*     */   public MultilineTextField(Font $$0, int $$1) {
/*  35 */     this.font = $$0;
/*  36 */     this.width = $$1;
/*  37 */     setValue("");
/*     */   }
/*     */   
/*     */   public int characterLimit() {
/*  41 */     return this.characterLimit;
/*     */   }
/*     */   
/*     */   public void setCharacterLimit(int $$0) {
/*  45 */     if ($$0 < 0) {
/*  46 */       throw new IllegalArgumentException("Character limit cannot be negative");
/*     */     }
/*  48 */     this.characterLimit = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasCharacterLimit() {
/*  53 */     return (this.characterLimit != Integer.MAX_VALUE);
/*     */   }
/*     */   
/*     */   public void setValueListener(Consumer<String> $$0) {
/*  57 */     this.valueListener = $$0;
/*     */   }
/*     */   
/*     */   public void setCursorListener(Runnable $$0) {
/*  61 */     this.cursorListener = $$0;
/*     */   }
/*     */   
/*     */   public void setValue(String $$0) {
/*  65 */     this.value = truncateFullText($$0);
/*  66 */     this.cursor = this.value.length();
/*  67 */     this.selectCursor = this.cursor;
/*  68 */     onValueChange();
/*     */   }
/*     */   
/*     */   public String value() {
/*  72 */     return this.value;
/*     */   }
/*     */   
/*     */   public void insertText(String $$0) {
/*  76 */     if ($$0.isEmpty() && !hasSelection()) {
/*     */       return;
/*     */     }
/*     */     
/*  80 */     String $$1 = truncateInsertionText(SharedConstants.filterText($$0, true));
/*     */     
/*  82 */     StringView $$2 = getSelected();
/*     */     
/*  84 */     this.value = (new StringBuilder(this.value)).replace($$2.beginIndex, $$2.endIndex, $$1).toString();
/*     */     
/*  86 */     this.cursor = $$2.beginIndex + $$1.length();
/*  87 */     this.selectCursor = this.cursor;
/*     */     
/*  89 */     onValueChange();
/*     */   }
/*     */   
/*     */   public void deleteText(int $$0) {
/*  93 */     if (!hasSelection()) {
/*  94 */       this.selectCursor = Mth.clamp(this.cursor + $$0, 0, this.value.length());
/*     */     }
/*     */     
/*  97 */     insertText("");
/*     */   }
/*     */   
/*     */   public int cursor() {
/* 101 */     return this.cursor;
/*     */   }
/*     */   
/*     */   public void setSelecting(boolean $$0) {
/* 105 */     this.selecting = $$0;
/*     */   }
/*     */   
/*     */   public StringView getSelected() {
/* 109 */     return new StringView(Math.min(this.selectCursor, this.cursor), Math.max(this.selectCursor, this.cursor));
/*     */   }
/*     */   
/*     */   public int getLineCount() {
/* 113 */     return this.displayLines.size();
/*     */   }
/*     */   
/*     */   public int getLineAtCursor() {
/* 117 */     for (int $$0 = 0; $$0 < this.displayLines.size(); $$0++) {
/* 118 */       StringView $$1 = this.displayLines.get($$0);
/* 119 */       if (this.cursor >= $$1.beginIndex && this.cursor <= $$1.endIndex) {
/* 120 */         return $$0;
/*     */       }
/*     */     } 
/* 123 */     return -1;
/*     */   }
/*     */   
/*     */   public StringView getLineView(int $$0) {
/* 127 */     return this.displayLines.get(Mth.clamp($$0, 0, this.displayLines.size() - 1));
/*     */   }
/*     */   
/*     */   public void seekCursor(Whence $$0, int $$1) {
/* 131 */     switch ($$0) { case ABSOLUTE:
/* 132 */         this.cursor = $$1; break;
/* 133 */       case RELATIVE: this.cursor += $$1; break;
/* 134 */       case END: this.cursor = this.value.length() + $$1; break; }
/*     */     
/* 136 */     this.cursor = Mth.clamp(this.cursor, 0, this.value.length());
/* 137 */     this.cursorListener.run();
/*     */     
/* 139 */     if (!this.selecting) {
/* 140 */       this.selectCursor = this.cursor;
/*     */     }
/*     */   }
/*     */   
/*     */   public void seekCursorLine(int $$0) {
/* 145 */     if ($$0 == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 149 */     int $$1 = this.font.width(this.value.substring((getCursorLineView()).beginIndex, this.cursor)) + 2;
/*     */     
/* 151 */     StringView $$2 = getCursorLineView($$0);
/* 152 */     int $$3 = this.font.plainSubstrByWidth(this.value.substring($$2.beginIndex, $$2.endIndex), $$1).length();
/* 153 */     seekCursor(Whence.ABSOLUTE, $$2.beginIndex + $$3);
/*     */   }
/*     */   
/*     */   public void seekCursorToPoint(double $$0, double $$1) {
/* 157 */     int $$2 = Mth.floor($$0);
/* 158 */     Objects.requireNonNull(this.font); int $$3 = Mth.floor($$1 / 9.0D);
/*     */     
/* 160 */     StringView $$4 = this.displayLines.get(Mth.clamp($$3, 0, this.displayLines.size() - 1));
/* 161 */     int $$5 = this.font.plainSubstrByWidth(this.value.substring($$4.beginIndex, $$4.endIndex), $$2).length();
/*     */     
/* 163 */     seekCursor(Whence.ABSOLUTE, $$4.beginIndex + $$5);
/*     */   }
/*     */   
/*     */   public boolean keyPressed(int $$0) {
/* 167 */     this.selecting = Screen.hasShiftDown();
/*     */     
/* 169 */     if (Screen.isSelectAll($$0)) {
/* 170 */       this.cursor = this.value.length();
/* 171 */       this.selectCursor = 0;
/* 172 */       return true;
/* 173 */     }  if (Screen.isCopy($$0)) {
/* 174 */       (Minecraft.getInstance()).keyboardHandler.setClipboard(getSelectedText());
/* 175 */       return true;
/* 176 */     }  if (Screen.isPaste($$0)) {
/* 177 */       insertText((Minecraft.getInstance()).keyboardHandler.getClipboard());
/* 178 */       return true;
/* 179 */     }  if (Screen.isCut($$0)) {
/* 180 */       (Minecraft.getInstance()).keyboardHandler.setClipboard(getSelectedText());
/* 181 */       insertText("");
/* 182 */       return true;
/*     */     } 
/*     */     
/* 185 */     switch ($$0) {
/*     */       case 263:
/* 187 */         if (Screen.hasControlDown()) {
/* 188 */           StringView $$1 = getPreviousWord();
/* 189 */           seekCursor(Whence.ABSOLUTE, $$1.beginIndex);
/*     */         } else {
/* 191 */           seekCursor(Whence.RELATIVE, -1);
/*     */         } 
/* 193 */         return true;
/*     */       
/*     */       case 262:
/* 196 */         if (Screen.hasControlDown()) {
/* 197 */           StringView $$2 = getNextWord();
/* 198 */           seekCursor(Whence.ABSOLUTE, $$2.beginIndex);
/*     */         } else {
/* 200 */           seekCursor(Whence.RELATIVE, 1);
/*     */         } 
/* 202 */         return true;
/*     */       
/*     */       case 265:
/* 205 */         if (!Screen.hasControlDown()) {
/* 206 */           seekCursorLine(-1);
/*     */         }
/* 208 */         return true;
/*     */       
/*     */       case 264:
/* 211 */         if (!Screen.hasControlDown()) {
/* 212 */           seekCursorLine(1);
/*     */         }
/* 214 */         return true;
/*     */       
/*     */       case 266:
/* 217 */         seekCursor(Whence.ABSOLUTE, 0);
/* 218 */         return true;
/*     */       
/*     */       case 267:
/* 221 */         seekCursor(Whence.END, 0);
/* 222 */         return true;
/*     */       
/*     */       case 268:
/* 225 */         if (Screen.hasControlDown()) {
/* 226 */           seekCursor(Whence.ABSOLUTE, 0);
/*     */         } else {
/* 228 */           seekCursor(Whence.ABSOLUTE, (getCursorLineView()).beginIndex);
/*     */         } 
/* 230 */         return true;
/*     */       
/*     */       case 269:
/* 233 */         if (Screen.hasControlDown()) {
/* 234 */           seekCursor(Whence.END, 0);
/*     */         } else {
/* 236 */           seekCursor(Whence.ABSOLUTE, (getCursorLineView()).endIndex);
/*     */         } 
/* 238 */         return true;
/*     */       
/*     */       case 259:
/* 241 */         if (Screen.hasControlDown()) {
/* 242 */           StringView $$3 = getPreviousWord();
/* 243 */           deleteText($$3.beginIndex - this.cursor);
/*     */         } else {
/* 245 */           deleteText(-1);
/*     */         } 
/* 247 */         return true;
/*     */       
/*     */       case 261:
/* 250 */         if (Screen.hasControlDown()) {
/* 251 */           StringView $$4 = getNextWord();
/* 252 */           deleteText($$4.beginIndex - this.cursor);
/*     */         } else {
/* 254 */           deleteText(1);
/*     */         } 
/* 256 */         return true;
/*     */       case 257:
/*     */       case 335:
/* 259 */         insertText("\n");
/* 260 */         return true;
/*     */     } 
/*     */     
/* 263 */     return false;
/*     */   }
/*     */   
/*     */   public Iterable<StringView> iterateLines() {
/* 267 */     return this.displayLines;
/*     */   }
/*     */   
/*     */   public boolean hasSelection() {
/* 271 */     return (this.selectCursor != this.cursor);
/*     */   }
/*     */   
/*     */   @VisibleForTesting
/*     */   public String getSelectedText() {
/* 276 */     StringView $$0 = getSelected();
/* 277 */     return this.value.substring($$0.beginIndex, $$0.endIndex);
/*     */   }
/*     */   
/*     */   private StringView getCursorLineView() {
/* 281 */     return getCursorLineView(0);
/*     */   }
/*     */   
/*     */   private StringView getCursorLineView(int $$0) {
/* 285 */     int $$1 = getLineAtCursor();
/* 286 */     if ($$1 < 0) {
/* 287 */       throw new IllegalStateException("Cursor is not within text (cursor = " + this.cursor + ", length = " + this.value.length() + ")");
/*     */     }
/*     */     
/* 290 */     return this.displayLines.get(Mth.clamp($$1 + $$0, 0, this.displayLines.size() - 1));
/*     */   }
/*     */   
/*     */   @VisibleForTesting
/*     */   public StringView getPreviousWord() {
/* 295 */     if (this.value.isEmpty()) {
/* 296 */       return StringView.EMPTY;
/*     */     }
/*     */     
/* 299 */     int $$0 = Mth.clamp(this.cursor, 0, this.value.length() - 1);
/*     */ 
/*     */     
/* 302 */     while ($$0 > 0 && Character.isWhitespace(this.value.charAt($$0 - 1))) {
/* 303 */       $$0--;
/*     */     }
/*     */ 
/*     */     
/* 307 */     while ($$0 > 0 && !Character.isWhitespace(this.value.charAt($$0 - 1))) {
/* 308 */       $$0--;
/*     */     }
/*     */     
/* 311 */     return new StringView($$0, getWordEndPosition($$0));
/*     */   }
/*     */   
/*     */   @VisibleForTesting
/*     */   public StringView getNextWord() {
/* 316 */     if (this.value.isEmpty()) {
/* 317 */       return StringView.EMPTY;
/*     */     }
/*     */     
/* 320 */     int $$0 = Mth.clamp(this.cursor, 0, this.value.length() - 1);
/*     */ 
/*     */     
/* 323 */     while ($$0 < this.value.length() && !Character.isWhitespace(this.value.charAt($$0))) {
/* 324 */       $$0++;
/*     */     }
/*     */ 
/*     */     
/* 328 */     while ($$0 < this.value.length() && Character.isWhitespace(this.value.charAt($$0))) {
/* 329 */       $$0++;
/*     */     }
/*     */     
/* 332 */     return new StringView($$0, getWordEndPosition($$0));
/*     */   }
/*     */   
/*     */   private int getWordEndPosition(int $$0) {
/* 336 */     int $$1 = $$0;
/* 337 */     while ($$1 < this.value.length() && !Character.isWhitespace(this.value.charAt($$1))) {
/* 338 */       $$1++;
/*     */     }
/*     */     
/* 341 */     return $$1;
/*     */   }
/*     */   
/*     */   private void onValueChange() {
/* 345 */     reflowDisplayLines();
/* 346 */     this.valueListener.accept(this.value);
/* 347 */     this.cursorListener.run();
/*     */   }
/*     */   
/*     */   private void reflowDisplayLines() {
/* 351 */     this.displayLines.clear();
/*     */     
/* 353 */     if (this.value.isEmpty()) {
/* 354 */       this.displayLines.add(StringView.EMPTY);
/*     */       
/*     */       return;
/*     */     } 
/* 358 */     this.font.getSplitter().splitLines(this.value, this.width, Style.EMPTY, false, ($$0, $$1, $$2) -> this.displayLines.add(new StringView($$1, $$2)));
/*     */ 
/*     */     
/* 361 */     if (this.value.charAt(this.value.length() - 1) == '\n') {
/* 362 */       this.displayLines.add(new StringView(this.value.length(), this.value.length()));
/*     */     }
/*     */   }
/*     */   
/*     */   private String truncateFullText(String $$0) {
/* 367 */     if (hasCharacterLimit()) {
/* 368 */       return StringUtil.truncateStringIfNecessary($$0, this.characterLimit, false);
/*     */     }
/* 370 */     return $$0;
/*     */   }
/*     */   
/*     */   private String truncateInsertionText(String $$0) {
/* 374 */     if (hasCharacterLimit()) {
/* 375 */       int $$1 = this.characterLimit - this.value.length();
/* 376 */       return StringUtil.truncateStringIfNecessary($$0, $$1, false);
/*     */     } 
/* 378 */     return $$0;
/*     */   }
/*     */   protected static final class StringView extends Record { final int beginIndex; final int endIndex;
/* 381 */     protected StringView(int $$0, int $$1) { this.beginIndex = $$0; this.endIndex = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/client/gui/components/MultilineTextField$StringView;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #381	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 381 */       //   0	7	0	this	Lnet/minecraft/client/gui/components/MultilineTextField$StringView; } public int beginIndex() { return this.beginIndex; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/gui/components/MultilineTextField$StringView;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #381	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/gui/components/MultilineTextField$StringView; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/client/gui/components/MultilineTextField$StringView;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #381	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/client/gui/components/MultilineTextField$StringView;
/* 381 */       //   0	8	1	$$0	Ljava/lang/Object; } public int endIndex() { return this.endIndex; }
/* 382 */      static final StringView EMPTY = new StringView(0, 0); }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\MultilineTextField.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */