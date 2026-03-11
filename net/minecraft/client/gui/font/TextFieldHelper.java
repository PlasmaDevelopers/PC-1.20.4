/*     */ package net.minecraft.client.gui.font;
/*     */ 
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Predicate;
/*     */ import java.util.function.Supplier;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.SharedConstants;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.StringSplitter;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.util.Mth;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TextFieldHelper
/*     */ {
/*     */   private final Supplier<String> getMessageFn;
/*     */   private final Consumer<String> setMessageFn;
/*     */   private final Supplier<String> getClipboardFn;
/*     */   private final Consumer<String> setClipboardFn;
/*     */   private final Predicate<String> stringValidator;
/*     */   private int cursorPos;
/*     */   private int selectionPos;
/*     */   
/*     */   public TextFieldHelper(Supplier<String> $$0, Consumer<String> $$1, Supplier<String> $$2, Consumer<String> $$3, Predicate<String> $$4) {
/*  29 */     this.getMessageFn = $$0;
/*  30 */     this.setMessageFn = $$1;
/*  31 */     this.getClipboardFn = $$2;
/*  32 */     this.setClipboardFn = $$3;
/*  33 */     this.stringValidator = $$4;
/*     */     
/*  35 */     setCursorToEnd();
/*     */   }
/*     */   
/*     */   public static Supplier<String> createClipboardGetter(Minecraft $$0) {
/*  39 */     return () -> getClipboardContents($$0);
/*     */   }
/*     */   
/*     */   public static String getClipboardContents(Minecraft $$0) {
/*  43 */     return ChatFormatting.stripFormatting($$0.keyboardHandler.getClipboard().replaceAll("\\r", ""));
/*     */   }
/*     */   
/*     */   public static Consumer<String> createClipboardSetter(Minecraft $$0) {
/*  47 */     return $$1 -> setClipboardContents($$0, $$1);
/*     */   }
/*     */   
/*     */   public static void setClipboardContents(Minecraft $$0, String $$1) {
/*  51 */     $$0.keyboardHandler.setClipboard($$1);
/*     */   }
/*     */   
/*     */   public boolean charTyped(char $$0) {
/*  55 */     if (SharedConstants.isAllowedChatCharacter($$0)) {
/*  56 */       insertText(this.getMessageFn.get(), Character.toString($$0));
/*     */     }
/*  58 */     return true;
/*     */   }
/*     */   
/*     */   public boolean keyPressed(int $$0) {
/*  62 */     if (Screen.isSelectAll($$0)) {
/*  63 */       selectAll();
/*  64 */       return true;
/*  65 */     }  if (Screen.isCopy($$0)) {
/*  66 */       copy();
/*  67 */       return true;
/*  68 */     }  if (Screen.isPaste($$0)) {
/*  69 */       paste();
/*  70 */       return true;
/*  71 */     }  if (Screen.isCut($$0)) {
/*  72 */       cut();
/*  73 */       return true;
/*     */     } 
/*     */     
/*  76 */     CursorStep $$1 = Screen.hasControlDown() ? CursorStep.WORD : CursorStep.CHARACTER;
/*  77 */     if ($$0 == 259) {
/*  78 */       removeFromCursor(-1, $$1);
/*  79 */       return true;
/*  80 */     }  if ($$0 == 261)
/*  81 */     { removeFromCursor(1, $$1); }
/*  82 */     else { if ($$0 == 263) {
/*  83 */         moveBy(-1, Screen.hasShiftDown(), $$1);
/*  84 */         return true;
/*  85 */       }  if ($$0 == 262) {
/*  86 */         moveBy(1, Screen.hasShiftDown(), $$1);
/*  87 */         return true;
/*  88 */       }  if ($$0 == 268) {
/*  89 */         setCursorToStart(Screen.hasShiftDown());
/*  90 */         return true;
/*  91 */       }  if ($$0 == 269) {
/*  92 */         setCursorToEnd(Screen.hasShiftDown());
/*  93 */         return true;
/*     */       }  }
/*  95 */      return false;
/*     */   }
/*     */   
/*     */   private int clampToMsgLength(int $$0) {
/*  99 */     return Mth.clamp($$0, 0, ((String)this.getMessageFn.get()).length());
/*     */   }
/*     */   
/*     */   private void insertText(String $$0, String $$1) {
/* 103 */     if (this.selectionPos != this.cursorPos) {
/* 104 */       $$0 = deleteSelection($$0);
/*     */     }
/*     */     
/* 107 */     this.cursorPos = Mth.clamp(this.cursorPos, 0, $$0.length());
/* 108 */     String $$2 = (new StringBuilder($$0)).insert(this.cursorPos, $$1).toString();
/* 109 */     if (this.stringValidator.test($$2)) {
/* 110 */       this.setMessageFn.accept($$2);
/* 111 */       this.selectionPos = this.cursorPos = Math.min($$2.length(), this.cursorPos + $$1.length());
/*     */     } 
/*     */   }
/*     */   
/*     */   public void insertText(String $$0) {
/* 116 */     insertText(this.getMessageFn.get(), $$0);
/*     */   }
/*     */   
/*     */   private void resetSelectionIfNeeded(boolean $$0) {
/* 120 */     if (!$$0) {
/* 121 */       this.selectionPos = this.cursorPos;
/*     */     }
/*     */   }
/*     */   
/*     */   public void moveBy(int $$0, boolean $$1, CursorStep $$2) {
/* 126 */     switch ($$2) { case CHARACTER:
/* 127 */         moveByChars($$0, $$1); break;
/* 128 */       case WORD: moveByWords($$0, $$1);
/*     */         break; }
/*     */   
/*     */   }
/*     */   public void moveByChars(int $$0) {
/* 133 */     moveByChars($$0, false);
/*     */   }
/*     */   
/*     */   public void moveByChars(int $$0, boolean $$1) {
/* 137 */     this.cursorPos = Util.offsetByCodepoints(this.getMessageFn.get(), this.cursorPos, $$0);
/* 138 */     resetSelectionIfNeeded($$1);
/*     */   }
/*     */   
/*     */   public void moveByWords(int $$0) {
/* 142 */     moveByWords($$0, false);
/*     */   }
/*     */   
/*     */   public void moveByWords(int $$0, boolean $$1) {
/* 146 */     this.cursorPos = StringSplitter.getWordPosition(this.getMessageFn.get(), $$0, this.cursorPos, true);
/* 147 */     resetSelectionIfNeeded($$1);
/*     */   }
/*     */   
/*     */   public void removeFromCursor(int $$0, CursorStep $$1) {
/* 151 */     switch ($$1) { case CHARACTER:
/* 152 */         removeCharsFromCursor($$0); break;
/* 153 */       case WORD: removeWordsFromCursor($$0);
/*     */         break; }
/*     */   
/*     */   }
/*     */   public void removeWordsFromCursor(int $$0) {
/* 158 */     int $$1 = StringSplitter.getWordPosition(this.getMessageFn.get(), $$0, this.cursorPos, true);
/* 159 */     removeCharsFromCursor($$1 - this.cursorPos);
/*     */   }
/*     */   
/*     */   public void removeCharsFromCursor(int $$0) {
/* 163 */     String $$1 = this.getMessageFn.get();
/* 164 */     if (!$$1.isEmpty()) {
/*     */       String $$6;
/* 166 */       if (this.selectionPos != this.cursorPos) {
/* 167 */         String $$2 = deleteSelection($$1);
/*     */       } else {
/* 169 */         int $$3 = Util.offsetByCodepoints($$1, this.cursorPos, $$0);
/* 170 */         int $$4 = Math.min($$3, this.cursorPos);
/* 171 */         int $$5 = Math.max($$3, this.cursorPos);
/* 172 */         $$6 = (new StringBuilder($$1)).delete($$4, $$5).toString();
/* 173 */         if ($$0 < 0) {
/* 174 */           this.selectionPos = this.cursorPos = $$4;
/*     */         }
/*     */       } 
/* 177 */       this.setMessageFn.accept($$6);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void cut() {
/* 182 */     String $$0 = this.getMessageFn.get();
/* 183 */     this.setClipboardFn.accept(getSelected($$0));
/* 184 */     this.setMessageFn.accept(deleteSelection($$0));
/*     */   }
/*     */   
/*     */   public void paste() {
/* 188 */     insertText(this.getMessageFn.get(), this.getClipboardFn.get());
/* 189 */     this.selectionPos = this.cursorPos;
/*     */   }
/*     */   
/*     */   public void copy() {
/* 193 */     this.setClipboardFn.accept(getSelected(this.getMessageFn.get()));
/*     */   }
/*     */   
/*     */   public void selectAll() {
/* 197 */     this.selectionPos = 0;
/* 198 */     this.cursorPos = ((String)this.getMessageFn.get()).length();
/*     */   }
/*     */   
/*     */   private String getSelected(String $$0) {
/* 202 */     int $$1 = Math.min(this.cursorPos, this.selectionPos);
/* 203 */     int $$2 = Math.max(this.cursorPos, this.selectionPos);
/* 204 */     return $$0.substring($$1, $$2);
/*     */   }
/*     */   
/*     */   private String deleteSelection(String $$0) {
/* 208 */     if (this.selectionPos == this.cursorPos) {
/* 209 */       return $$0;
/*     */     }
/* 211 */     int $$1 = Math.min(this.cursorPos, this.selectionPos);
/* 212 */     int $$2 = Math.max(this.cursorPos, this.selectionPos);
/* 213 */     String $$3 = $$0.substring(0, $$1) + $$0.substring(0, $$1);
/* 214 */     this.selectionPos = this.cursorPos = $$1;
/* 215 */     return $$3;
/*     */   }
/*     */   
/*     */   public void setCursorToStart() {
/* 219 */     setCursorToStart(false);
/*     */   }
/*     */   
/*     */   public void setCursorToStart(boolean $$0) {
/* 223 */     this.cursorPos = 0;
/* 224 */     resetSelectionIfNeeded($$0);
/*     */   }
/*     */   
/*     */   public void setCursorToEnd() {
/* 228 */     setCursorToEnd(false);
/*     */   }
/*     */   
/*     */   public void setCursorToEnd(boolean $$0) {
/* 232 */     this.cursorPos = ((String)this.getMessageFn.get()).length();
/* 233 */     resetSelectionIfNeeded($$0);
/*     */   }
/*     */   
/*     */   public int getCursorPos() {
/* 237 */     return this.cursorPos;
/*     */   }
/*     */   
/*     */   public void setCursorPos(int $$0) {
/* 241 */     setCursorPos($$0, true);
/*     */   }
/*     */   
/*     */   public void setCursorPos(int $$0, boolean $$1) {
/* 245 */     this.cursorPos = clampToMsgLength($$0);
/* 246 */     resetSelectionIfNeeded($$1);
/*     */   }
/*     */   
/*     */   public int getSelectionPos() {
/* 250 */     return this.selectionPos;
/*     */   }
/*     */   
/*     */   public void setSelectionPos(int $$0) {
/* 254 */     this.selectionPos = clampToMsgLength($$0);
/*     */   }
/*     */   
/*     */   public void setSelectionRange(int $$0, int $$1) {
/* 258 */     int $$2 = ((String)this.getMessageFn.get()).length();
/* 259 */     this.cursorPos = Mth.clamp($$0, 0, $$2);
/* 260 */     this.selectionPos = Mth.clamp($$1, 0, $$2);
/*     */   }
/*     */   
/*     */   public boolean isSelecting() {
/* 264 */     return (this.cursorPos != this.selectionPos);
/*     */   }
/*     */   
/*     */   public enum CursorStep {
/* 268 */     CHARACTER,
/* 269 */     WORD;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\font\TextFieldHelper.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */