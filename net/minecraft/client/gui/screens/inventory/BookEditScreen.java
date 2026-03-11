/*     */ package net.minecraft.client.gui.screens.inventory;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import it.unimi.dsi.fastutil.ints.IntArrayList;
/*     */ import it.unimi.dsi.fastutil.ints.IntList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.SharedConstants;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.client.GameNarrator;
/*     */ import net.minecraft.client.StringSplitter;
/*     */ import net.minecraft.client.gui.Font;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.client.gui.font.TextFieldHelper;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.client.renderer.Rect2i;
/*     */ import net.minecraft.client.renderer.RenderType;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.ListTag;
/*     */ import net.minecraft.nbt.StringTag;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.FormattedText;
/*     */ import net.minecraft.network.chat.Style;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.network.protocol.game.ServerboundEditBookPacket;
/*     */ import net.minecraft.util.FormattedCharSequence;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ import org.apache.commons.lang3.mutable.MutableBoolean;
/*     */ import org.apache.commons.lang3.mutable.MutableInt;
/*     */ 
/*     */ public class BookEditScreen extends Screen {
/*     */   private static final int TEXT_WIDTH = 114;
/*     */   private static final int TEXT_HEIGHT = 128;
/*     */   private static final int IMAGE_WIDTH = 192;
/*     */   private static final int IMAGE_HEIGHT = 192;
/*  48 */   private static final Component EDIT_TITLE_LABEL = (Component)Component.translatable("book.editTitle");
/*  49 */   private static final Component FINALIZE_WARNING_LABEL = (Component)Component.translatable("book.finalizeWarning");
/*  50 */   private static final FormattedCharSequence BLACK_CURSOR = FormattedCharSequence.forward("_", Style.EMPTY.withColor(ChatFormatting.BLACK));
/*  51 */   private static final FormattedCharSequence GRAY_CURSOR = FormattedCharSequence.forward("_", Style.EMPTY.withColor(ChatFormatting.GRAY));
/*     */   
/*     */   private final Player owner;
/*     */   
/*     */   private final ItemStack book;
/*     */   
/*     */   private boolean isModified;
/*     */   private boolean isSigning;
/*     */   private int frameTick;
/*     */   private int currentPage;
/*  61 */   private final List<String> pages = Lists.newArrayList();
/*  62 */   private String title = "";
/*     */ 
/*     */   
/*     */   private final TextFieldHelper pageEdit;
/*     */ 
/*     */   
/*     */   private final TextFieldHelper titleEdit;
/*     */   
/*     */   private long lastClickTime;
/*     */   
/*     */   private int lastIndex;
/*     */   
/*     */   private PageButton forwardButton;
/*     */   
/*     */   private PageButton backButton;
/*     */   
/*     */   private Button doneButton;
/*     */   
/*     */   private Button signButton;
/*     */   
/*     */   private Button finalizeButton;
/*     */   
/*     */   private Button cancelButton;
/*     */   
/*     */   private final InteractionHand hand;
/*     */   
/*     */   @Nullable
/*     */   private DisplayCache displayCache;
/*     */   
/*     */   private Component pageMsg;
/*     */   
/*     */   private final Component ownerText;
/*     */ 
/*     */   
/*     */   public BookEditScreen(Player $$0, ItemStack $$1, InteractionHand $$2) {
/*  97 */     super(GameNarrator.NO_TITLE); this.pageEdit = new TextFieldHelper(this::getCurrentPageText, this::setCurrentPageText, this::getClipboard, this::setClipboard, $$0 -> ($$0.length() < 1024 && this.font.wordWrapHeight($$0, 114) <= 128)); this.titleEdit = new TextFieldHelper(() -> this.title, $$0 -> this.title = $$0, this::getClipboard, this::setClipboard, $$0 -> ($$0.length() < 16)); this.lastIndex = -1; this.displayCache = DisplayCache.EMPTY; this.pageMsg = CommonComponents.EMPTY;
/*  98 */     this.owner = $$0;
/*  99 */     this.book = $$1;
/* 100 */     this.hand = $$2;
/*     */     
/* 102 */     CompoundTag $$3 = $$1.getTag();
/* 103 */     if ($$3 != null) {
/* 104 */       Objects.requireNonNull(this.pages); BookViewScreen.loadPages($$3, this.pages::add);
/*     */     } 
/*     */     
/* 107 */     if (this.pages.isEmpty()) {
/* 108 */       this.pages.add("");
/*     */     }
/*     */     
/* 111 */     this.ownerText = (Component)Component.translatable("book.byAuthor", new Object[] { $$0.getName() }).withStyle(ChatFormatting.DARK_GRAY);
/*     */   }
/*     */   
/*     */   private void setClipboard(String $$0) {
/* 115 */     if (this.minecraft != null) {
/* 116 */       TextFieldHelper.setClipboardContents(this.minecraft, $$0);
/*     */     }
/*     */   }
/*     */   
/*     */   private String getClipboard() {
/* 121 */     return (this.minecraft != null) ? TextFieldHelper.getClipboardContents(this.minecraft) : "";
/*     */   }
/*     */   
/*     */   private int getNumPages() {
/* 125 */     return this.pages.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 130 */     super.tick();
/* 131 */     this.frameTick++;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void init() {
/* 136 */     clearDisplayCache();
/*     */     
/* 138 */     this.signButton = (Button)addRenderableWidget((GuiEventListener)Button.builder((Component)Component.translatable("book.signButton"), $$0 -> {
/*     */             this.isSigning = true;
/*     */             updateButtonVisibility();
/* 141 */           }).bounds(this.width / 2 - 100, 196, 98, 20).build());
/* 142 */     this.doneButton = (Button)addRenderableWidget((GuiEventListener)Button.builder(CommonComponents.GUI_DONE, $$0 -> {
/*     */             this.minecraft.setScreen(null);
/*     */             saveChanges(false);
/* 145 */           }).bounds(this.width / 2 + 2, 196, 98, 20).build());
/*     */     
/* 147 */     this.finalizeButton = (Button)addRenderableWidget((GuiEventListener)Button.builder((Component)Component.translatable("book.finalizeButton"), $$0 -> {
/*     */             if (this.isSigning) {
/*     */               saveChanges(true);
/*     */               this.minecraft.setScreen(null);
/*     */             } 
/* 152 */           }).bounds(this.width / 2 - 100, 196, 98, 20).build());
/* 153 */     this.cancelButton = (Button)addRenderableWidget((GuiEventListener)Button.builder(CommonComponents.GUI_CANCEL, $$0 -> {
/*     */             if (this.isSigning) {
/*     */               this.isSigning = false;
/*     */             }
/*     */             updateButtonVisibility();
/* 158 */           }).bounds(this.width / 2 + 2, 196, 98, 20).build());
/*     */     
/* 160 */     int $$0 = (this.width - 192) / 2;
/* 161 */     int $$1 = 2;
/*     */     
/* 163 */     this.forwardButton = (PageButton)addRenderableWidget((GuiEventListener)new PageButton($$0 + 116, 159, true, $$0 -> pageForward(), true));
/* 164 */     this.backButton = (PageButton)addRenderableWidget((GuiEventListener)new PageButton($$0 + 43, 159, false, $$0 -> pageBack(), true));
/*     */     
/* 166 */     updateButtonVisibility();
/*     */   }
/*     */   
/*     */   private void pageBack() {
/* 170 */     if (this.currentPage > 0) {
/* 171 */       this.currentPage--;
/*     */     }
/* 173 */     updateButtonVisibility();
/* 174 */     clearDisplayCacheAfterPageChange();
/*     */   }
/*     */   
/*     */   private void pageForward() {
/* 178 */     if (this.currentPage < getNumPages() - 1) {
/* 179 */       this.currentPage++;
/*     */     } else {
/* 181 */       appendPageToBook();
/* 182 */       if (this.currentPage < getNumPages() - 1) {
/* 183 */         this.currentPage++;
/*     */       }
/*     */     } 
/* 186 */     updateButtonVisibility();
/* 187 */     clearDisplayCacheAfterPageChange();
/*     */   }
/*     */   
/*     */   private void updateButtonVisibility() {
/* 191 */     this.backButton.visible = (!this.isSigning && this.currentPage > 0);
/* 192 */     this.forwardButton.visible = !this.isSigning;
/*     */     
/* 194 */     this.doneButton.visible = !this.isSigning;
/* 195 */     this.signButton.visible = !this.isSigning;
/* 196 */     this.cancelButton.visible = this.isSigning;
/* 197 */     this.finalizeButton.visible = this.isSigning;
/* 198 */     this.finalizeButton.active = !Util.isBlank(this.title);
/*     */   }
/*     */   
/*     */   private void eraseEmptyTrailingPages() {
/* 202 */     ListIterator<String> $$0 = this.pages.listIterator(this.pages.size());
/* 203 */     while ($$0.hasPrevious() && ((String)$$0.previous()).isEmpty()) {
/* 204 */       $$0.remove();
/*     */     }
/*     */   }
/*     */   
/*     */   private void saveChanges(boolean $$0) {
/* 209 */     if (!this.isModified) {
/*     */       return;
/*     */     }
/*     */     
/* 213 */     eraseEmptyTrailingPages();
/*     */ 
/*     */     
/* 216 */     updateLocalCopy($$0);
/*     */     
/* 218 */     int $$1 = (this.hand == InteractionHand.MAIN_HAND) ? (this.owner.getInventory()).selected : 40;
/* 219 */     this.minecraft.getConnection().send((Packet)new ServerboundEditBookPacket($$1, this.pages, $$0 ? Optional.<String>of(this.title.trim()) : Optional.empty()));
/*     */   }
/*     */   
/*     */   private void updateLocalCopy(boolean $$0) {
/* 223 */     ListTag $$1 = new ListTag();
/* 224 */     Objects.requireNonNull($$1); this.pages.stream().map(StringTag::valueOf).forEach($$1::add);
/* 225 */     if (!this.pages.isEmpty()) {
/* 226 */       this.book.addTagElement("pages", (Tag)$$1);
/*     */     }
/*     */     
/* 229 */     if ($$0) {
/* 230 */       this.book.addTagElement("author", (Tag)StringTag.valueOf(this.owner.getGameProfile().getName()));
/* 231 */       this.book.addTagElement("title", (Tag)StringTag.valueOf(this.title.trim()));
/*     */     } 
/*     */   }
/*     */   
/*     */   private void appendPageToBook() {
/* 236 */     if (getNumPages() >= 100) {
/*     */       return;
/*     */     }
/* 239 */     this.pages.add("");
/* 240 */     this.isModified = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean keyPressed(int $$0, int $$1, int $$2) {
/* 245 */     if (super.keyPressed($$0, $$1, $$2)) {
/* 246 */       return true;
/*     */     }
/*     */     
/* 249 */     if (this.isSigning) {
/* 250 */       return titleKeyPressed($$0, $$1, $$2);
/*     */     }
/* 252 */     boolean $$3 = bookKeyPressed($$0, $$1, $$2);
/* 253 */     if ($$3) {
/* 254 */       clearDisplayCache();
/* 255 */       return true;
/*     */     } 
/* 257 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean charTyped(char $$0, int $$1) {
/* 263 */     if (super.charTyped($$0, $$1)) {
/* 264 */       return true;
/*     */     }
/*     */     
/* 267 */     if (this.isSigning) {
/* 268 */       boolean $$2 = this.titleEdit.charTyped($$0);
/* 269 */       if ($$2) {
/* 270 */         updateButtonVisibility();
/* 271 */         this.isModified = true;
/* 272 */         return true;
/*     */       } 
/* 274 */       return false;
/*     */     } 
/* 276 */     if (SharedConstants.isAllowedChatCharacter($$0)) {
/* 277 */       this.pageEdit.insertText(Character.toString($$0));
/* 278 */       clearDisplayCache();
/* 279 */       return true;
/*     */     } 
/* 281 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean bookKeyPressed(int $$0, int $$1, int $$2) {
/* 287 */     if (Screen.isSelectAll($$0)) {
/* 288 */       this.pageEdit.selectAll();
/* 289 */       return true;
/* 290 */     }  if (Screen.isCopy($$0)) {
/* 291 */       this.pageEdit.copy();
/* 292 */       return true;
/* 293 */     }  if (Screen.isPaste($$0)) {
/* 294 */       this.pageEdit.paste();
/* 295 */       return true;
/* 296 */     }  if (Screen.isCut($$0)) {
/* 297 */       this.pageEdit.cut();
/* 298 */       return true;
/*     */     } 
/*     */     
/* 301 */     TextFieldHelper.CursorStep $$3 = Screen.hasControlDown() ? TextFieldHelper.CursorStep.WORD : TextFieldHelper.CursorStep.CHARACTER;
/*     */     
/* 303 */     switch ($$0) {
/*     */       case 259:
/* 305 */         this.pageEdit.removeFromCursor(-1, $$3);
/* 306 */         return true;
/*     */       case 261:
/* 308 */         this.pageEdit.removeFromCursor(1, $$3);
/* 309 */         return true;
/*     */       case 257:
/*     */       case 335:
/* 312 */         this.pageEdit.insertText("\n");
/* 313 */         return true;
/*     */       case 263:
/* 315 */         this.pageEdit.moveBy(-1, Screen.hasShiftDown(), $$3);
/* 316 */         return true;
/*     */       case 262:
/* 318 */         this.pageEdit.moveBy(1, Screen.hasShiftDown(), $$3);
/* 319 */         return true;
/*     */       case 265:
/* 321 */         keyUp();
/* 322 */         return true;
/*     */       case 264:
/* 324 */         keyDown();
/* 325 */         return true;
/*     */       case 266:
/* 327 */         this.backButton.onPress();
/* 328 */         return true;
/*     */       case 267:
/* 330 */         this.forwardButton.onPress();
/* 331 */         return true;
/*     */       case 268:
/* 333 */         keyHome();
/* 334 */         return true;
/*     */       case 269:
/* 336 */         keyEnd();
/* 337 */         return true;
/*     */     } 
/*     */ 
/*     */     
/* 341 */     return false;
/*     */   }
/*     */   
/*     */   private void keyUp() {
/* 345 */     changeLine(-1);
/*     */   }
/*     */   
/*     */   private void keyDown() {
/* 349 */     changeLine(1);
/*     */   }
/*     */   
/*     */   private void changeLine(int $$0) {
/* 353 */     int $$1 = this.pageEdit.getCursorPos();
/* 354 */     int $$2 = getDisplayCache().changeLine($$1, $$0);
/* 355 */     this.pageEdit.setCursorPos($$2, Screen.hasShiftDown());
/*     */   }
/*     */   
/*     */   private void keyHome() {
/* 359 */     if (Screen.hasControlDown()) {
/* 360 */       this.pageEdit.setCursorToStart(Screen.hasShiftDown());
/*     */     } else {
/* 362 */       int $$0 = this.pageEdit.getCursorPos();
/* 363 */       int $$1 = getDisplayCache().findLineStart($$0);
/* 364 */       this.pageEdit.setCursorPos($$1, Screen.hasShiftDown());
/*     */     } 
/*     */   }
/*     */   
/*     */   private void keyEnd() {
/* 369 */     if (Screen.hasControlDown()) {
/* 370 */       this.pageEdit.setCursorToEnd(Screen.hasShiftDown());
/*     */     } else {
/* 372 */       DisplayCache $$0 = getDisplayCache();
/* 373 */       int $$1 = this.pageEdit.getCursorPos();
/* 374 */       int $$2 = $$0.findLineEnd($$1);
/* 375 */       this.pageEdit.setCursorPos($$2, Screen.hasShiftDown());
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean titleKeyPressed(int $$0, int $$1, int $$2) {
/* 380 */     switch ($$0) {
/*     */       case 259:
/* 382 */         this.titleEdit.removeCharsFromCursor(-1);
/* 383 */         updateButtonVisibility();
/* 384 */         this.isModified = true;
/* 385 */         return true;
/*     */       case 257:
/*     */       case 335:
/* 388 */         if (!this.title.isEmpty()) {
/* 389 */           saveChanges(true);
/* 390 */           this.minecraft.setScreen(null);
/*     */         } 
/* 392 */         return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 397 */     return false;
/*     */   }
/*     */   
/*     */   private String getCurrentPageText() {
/* 401 */     if (this.currentPage >= 0 && this.currentPage < this.pages.size()) {
/* 402 */       return this.pages.get(this.currentPage);
/*     */     }
/* 404 */     return "";
/*     */   }
/*     */   
/*     */   private void setCurrentPageText(String $$0) {
/* 408 */     if (this.currentPage >= 0 && this.currentPage < this.pages.size()) {
/* 409 */       this.pages.set(this.currentPage, $$0);
/* 410 */       this.isModified = true;
/* 411 */       clearDisplayCache();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 417 */     super.render($$0, $$1, $$2, $$3);
/*     */ 
/*     */     
/* 420 */     setFocused(null);
/*     */     
/* 422 */     int $$4 = (this.width - 192) / 2;
/* 423 */     int $$5 = 2;
/*     */     
/* 425 */     if (this.isSigning) {
/* 426 */       boolean $$6 = (this.frameTick / 6 % 2 == 0);
/* 427 */       FormattedCharSequence $$7 = FormattedCharSequence.composite(FormattedCharSequence.forward(this.title, Style.EMPTY), $$6 ? BLACK_CURSOR : GRAY_CURSOR);
/*     */       
/* 429 */       int $$8 = this.font.width((FormattedText)EDIT_TITLE_LABEL);
/* 430 */       $$0.drawString(this.font, EDIT_TITLE_LABEL, $$4 + 36 + (114 - $$8) / 2, 34, 0, false);
/*     */       
/* 432 */       int $$9 = this.font.width($$7);
/* 433 */       $$0.drawString(this.font, $$7, $$4 + 36 + (114 - $$9) / 2, 50, 0, false);
/* 434 */       int $$10 = this.font.width((FormattedText)this.ownerText);
/* 435 */       $$0.drawString(this.font, this.ownerText, $$4 + 36 + (114 - $$10) / 2, 60, 0, false);
/*     */       
/* 437 */       $$0.drawWordWrap(this.font, (FormattedText)FINALIZE_WARNING_LABEL, $$4 + 36, 82, 114, 0);
/*     */     } else {
/* 439 */       int $$11 = this.font.width((FormattedText)this.pageMsg);
/* 440 */       $$0.drawString(this.font, this.pageMsg, $$4 - $$11 + 192 - 44, 18, 0, false);
/*     */       
/* 442 */       DisplayCache $$12 = getDisplayCache();
/* 443 */       for (LineInfo $$13 : $$12.lines) {
/* 444 */         $$0.drawString(this.font, $$13.asComponent, $$13.x, $$13.y, -16777216, false);
/*     */       }
/* 446 */       renderHighlight($$0, $$12.selection);
/* 447 */       renderCursor($$0, $$12.cursor, $$12.cursorAtEnd);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderBackground(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 453 */     super.renderBackground($$0, $$1, $$2, $$3);
/* 454 */     $$0.blit(BookViewScreen.BOOK_LOCATION, (this.width - 192) / 2, 2, 0, 0, 192, 192);
/*     */   }
/*     */   
/*     */   private void renderCursor(GuiGraphics $$0, Pos2i $$1, boolean $$2) {
/* 458 */     if (this.frameTick / 6 % 2 == 0) {
/* 459 */       $$1 = convertLocalToScreen($$1);
/* 460 */       if (!$$2) {
/* 461 */         Objects.requireNonNull(this.font); $$0.fill($$1.x, $$1.y - 1, $$1.x + 1, $$1.y + 9, -16777216);
/*     */       } else {
/* 463 */         $$0.drawString(this.font, "_", $$1.x, $$1.y, 0, false);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void renderHighlight(GuiGraphics $$0, Rect2i[] $$1) {
/* 469 */     for (Rect2i $$2 : $$1) {
/* 470 */       int $$3 = $$2.getX();
/* 471 */       int $$4 = $$2.getY();
/* 472 */       int $$5 = $$3 + $$2.getWidth();
/* 473 */       int $$6 = $$4 + $$2.getHeight();
/* 474 */       $$0.fill(RenderType.guiTextHighlight(), $$3, $$4, $$5, $$6, -16776961);
/*     */     } 
/*     */   }
/*     */   
/*     */   private Pos2i convertScreenToLocal(Pos2i $$0) {
/* 479 */     return new Pos2i($$0.x - (this.width - 192) / 2 - 36, $$0.y - 32);
/*     */   }
/*     */   
/*     */   private Pos2i convertLocalToScreen(Pos2i $$0) {
/* 483 */     return new Pos2i($$0.x + (this.width - 192) / 2 + 36, $$0.y + 32);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(double $$0, double $$1, int $$2) {
/* 488 */     if (super.mouseClicked($$0, $$1, $$2)) {
/* 489 */       return true;
/*     */     }
/*     */     
/* 492 */     if ($$2 == 0) {
/* 493 */       long $$3 = Util.getMillis();
/*     */       
/* 495 */       DisplayCache $$4 = getDisplayCache();
/* 496 */       int $$5 = $$4.getIndexAtPosition(this.font, convertScreenToLocal(new Pos2i((int)$$0, (int)$$1)));
/* 497 */       if ($$5 >= 0) {
/* 498 */         if ($$5 == this.lastIndex && $$3 - this.lastClickTime < 250L) {
/* 499 */           if (!this.pageEdit.isSelecting()) {
/* 500 */             selectWord($$5);
/*     */           } else {
/* 502 */             this.pageEdit.selectAll();
/*     */           } 
/*     */         } else {
/* 505 */           this.pageEdit.setCursorPos($$5, Screen.hasShiftDown());
/*     */         } 
/* 507 */         clearDisplayCache();
/*     */       } 
/* 509 */       this.lastIndex = $$5;
/* 510 */       this.lastClickTime = $$3;
/*     */     } 
/*     */     
/* 513 */     return true;
/*     */   }
/*     */   
/*     */   private void selectWord(int $$0) {
/* 517 */     String $$1 = getCurrentPageText();
/* 518 */     this.pageEdit.setSelectionRange(
/* 519 */         StringSplitter.getWordPosition($$1, -1, $$0, false), 
/* 520 */         StringSplitter.getWordPosition($$1, 1, $$0, false));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean mouseDragged(double $$0, double $$1, int $$2, double $$3, double $$4) {
/* 526 */     if (super.mouseDragged($$0, $$1, $$2, $$3, $$4)) {
/* 527 */       return true;
/*     */     }
/*     */     
/* 530 */     if ($$2 == 0) {
/* 531 */       DisplayCache $$5 = getDisplayCache();
/* 532 */       int $$6 = $$5.getIndexAtPosition(this.font, convertScreenToLocal(new Pos2i((int)$$0, (int)$$1)));
/* 533 */       this.pageEdit.setCursorPos($$6, true);
/* 534 */       clearDisplayCache();
/*     */     } 
/* 536 */     return true;
/*     */   }
/*     */   
/*     */   private DisplayCache getDisplayCache() {
/* 540 */     if (this.displayCache == null) {
/* 541 */       this.displayCache = rebuildDisplayCache();
/* 542 */       this.pageMsg = (Component)Component.translatable("book.pageIndicator", new Object[] { Integer.valueOf(this.currentPage + 1), Integer.valueOf(getNumPages()) });
/*     */     } 
/*     */     
/* 545 */     return this.displayCache;
/*     */   }
/*     */   
/*     */   private void clearDisplayCache() {
/* 549 */     this.displayCache = null;
/*     */   }
/*     */   
/*     */   private void clearDisplayCacheAfterPageChange() {
/* 553 */     this.pageEdit.setCursorToEnd();
/* 554 */     clearDisplayCache();
/*     */   }
/*     */   private DisplayCache rebuildDisplayCache() {
/*     */     Pos2i $$13;
/* 558 */     String $$0 = getCurrentPageText();
/* 559 */     if ($$0.isEmpty()) {
/* 560 */       return DisplayCache.EMPTY;
/*     */     }
/* 562 */     int $$1 = this.pageEdit.getCursorPos();
/* 563 */     int $$2 = this.pageEdit.getSelectionPos();
/*     */     
/* 565 */     IntArrayList intArrayList = new IntArrayList();
/* 566 */     List<LineInfo> $$4 = Lists.newArrayList();
/* 567 */     MutableInt $$5 = new MutableInt();
/* 568 */     MutableBoolean $$6 = new MutableBoolean();
/* 569 */     StringSplitter $$7 = this.font.getSplitter();
/* 570 */     $$7.splitLines($$0, 114, Style.EMPTY, true, ($$5, $$6, $$7) -> {
/*     */           int $$8 = $$0.getAndIncrement();
/*     */           String $$9 = $$1.substring($$6, $$7);
/*     */           $$2.setValue($$9.endsWith("\n"));
/*     */           String $$10 = StringUtils.stripEnd($$9, " \n");
/*     */           Objects.requireNonNull(this.font);
/*     */           int $$11 = $$8 * 9;
/*     */           Pos2i $$12 = convertLocalToScreen(new Pos2i(0, $$11));
/*     */           $$3.add($$6);
/*     */           $$4.add(new LineInfo($$5, $$10, $$12.x, $$12.y));
/*     */         });
/* 581 */     int[] $$8 = intArrayList.toIntArray();
/*     */     
/* 583 */     boolean $$9 = ($$1 == $$0.length());
/*     */ 
/*     */     
/* 586 */     if ($$9 && $$6.isTrue()) {
/* 587 */       Objects.requireNonNull(this.font); Pos2i $$10 = new Pos2i(0, $$4.size() * 9);
/*     */     } else {
/* 589 */       int $$11 = findLineFromPos($$8, $$1);
/* 590 */       int $$12 = this.font.width($$0.substring($$8[$$11], $$1));
/* 591 */       Objects.requireNonNull(this.font); $$13 = new Pos2i($$12, $$11 * 9);
/*     */     } 
/*     */     
/* 594 */     List<Rect2i> $$14 = Lists.newArrayList();
/*     */     
/* 596 */     if ($$1 != $$2) {
/* 597 */       int $$15 = Math.min($$1, $$2);
/* 598 */       int $$16 = Math.max($$1, $$2);
/*     */       
/* 600 */       int $$17 = findLineFromPos($$8, $$15);
/* 601 */       int $$18 = findLineFromPos($$8, $$16);
/* 602 */       if ($$17 == $$18) {
/* 603 */         Objects.requireNonNull(this.font); int $$19 = $$17 * 9;
/* 604 */         int $$20 = $$8[$$17];
/* 605 */         $$14.add(createPartialLineSelection($$0, $$7, $$15, $$16, $$19, $$20));
/*     */       } else {
/* 607 */         int $$21 = ($$17 + 1 > $$8.length) ? $$0.length() : $$8[$$17 + 1];
/* 608 */         Objects.requireNonNull(this.font); $$14.add(createPartialLineSelection($$0, $$7, $$15, $$21, $$17 * 9, $$8[$$17]));
/* 609 */         for (int $$22 = $$17 + 1; $$22 < $$18; $$22++) {
/* 610 */           Objects.requireNonNull(this.font); int $$23 = $$22 * 9;
/* 611 */           String $$24 = $$0.substring($$8[$$22], $$8[$$22 + 1]);
/* 612 */           int $$25 = (int)$$7.stringWidth($$24);
/* 613 */           Objects.requireNonNull(this.font); $$14.add(createSelection(new Pos2i(0, $$23), new Pos2i($$25, $$23 + 9)));
/*     */         } 
/* 615 */         Objects.requireNonNull(this.font); $$14.add(createPartialLineSelection($$0, $$7, $$8[$$18], $$16, $$18 * 9, $$8[$$18]));
/*     */       } 
/*     */     } 
/*     */     
/* 619 */     return new DisplayCache($$0, $$13, $$9, $$8, $$4.<LineInfo>toArray(new LineInfo[0]), $$14.<Rect2i>toArray(new Rect2i[0]));
/*     */   }
/*     */   
/*     */   static int findLineFromPos(int[] $$0, int $$1) {
/* 623 */     int $$2 = Arrays.binarySearch($$0, $$1);
/* 624 */     if ($$2 < 0) {
/* 625 */       return -($$2 + 2);
/*     */     }
/* 627 */     return $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   private Rect2i createPartialLineSelection(String $$0, StringSplitter $$1, int $$2, int $$3, int $$4, int $$5) {
/* 632 */     String $$6 = $$0.substring($$5, $$2);
/* 633 */     String $$7 = $$0.substring($$5, $$3);
/*     */     
/* 635 */     Pos2i $$8 = new Pos2i((int)$$1.stringWidth($$6), $$4);
/* 636 */     Objects.requireNonNull(this.font); Pos2i $$9 = new Pos2i((int)$$1.stringWidth($$7), $$4 + 9);
/*     */     
/* 638 */     return createSelection($$8, $$9);
/*     */   }
/*     */   
/*     */   private Rect2i createSelection(Pos2i $$0, Pos2i $$1) {
/* 642 */     Pos2i $$2 = convertLocalToScreen($$0);
/* 643 */     Pos2i $$3 = convertLocalToScreen($$1);
/*     */     
/* 645 */     int $$4 = Math.min($$2.x, $$3.x);
/* 646 */     int $$5 = Math.max($$2.x, $$3.x);
/*     */     
/* 648 */     int $$6 = Math.min($$2.y, $$3.y);
/* 649 */     int $$7 = Math.max($$2.y, $$3.y);
/*     */     
/* 651 */     return new Rect2i($$4, $$6, $$5 - $$4, $$7 - $$6);
/*     */   }
/*     */   
/*     */   private static class Pos2i {
/*     */     public final int x;
/*     */     public final int y;
/*     */     
/*     */     Pos2i(int $$0, int $$1) {
/* 659 */       this.x = $$0;
/* 660 */       this.y = $$1;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class LineInfo {
/*     */     final Style style;
/*     */     final String contents;
/*     */     final Component asComponent;
/*     */     final int x;
/*     */     final int y;
/*     */     
/*     */     public LineInfo(Style $$0, String $$1, int $$2, int $$3) {
/* 672 */       this.style = $$0;
/* 673 */       this.contents = $$1;
/* 674 */       this.x = $$2;
/* 675 */       this.y = $$3;
/* 676 */       this.asComponent = (Component)Component.literal($$1).setStyle($$0);
/*     */     }
/*     */   }
/*     */   
/*     */   private static class DisplayCache {
/* 681 */     static final DisplayCache EMPTY = new DisplayCache("", new BookEditScreen.Pos2i(0, 0), true, new int[] { 0 }, new BookEditScreen.LineInfo[] { new BookEditScreen.LineInfo(Style.EMPTY, "", 0, 0) }new Rect2i[0]);
/*     */     
/*     */     private final String fullText;
/*     */     
/*     */     final BookEditScreen.Pos2i cursor;
/*     */     final boolean cursorAtEnd;
/*     */     private final int[] lineStarts;
/*     */     final BookEditScreen.LineInfo[] lines;
/*     */     final Rect2i[] selection;
/*     */     
/*     */     public DisplayCache(String $$0, BookEditScreen.Pos2i $$1, boolean $$2, int[] $$3, BookEditScreen.LineInfo[] $$4, Rect2i[] $$5) {
/* 692 */       this.fullText = $$0;
/* 693 */       this.cursor = $$1;
/* 694 */       this.cursorAtEnd = $$2;
/* 695 */       this.lineStarts = $$3;
/* 696 */       this.lines = $$4;
/* 697 */       this.selection = $$5;
/*     */     }
/*     */     
/*     */     public int getIndexAtPosition(Font $$0, BookEditScreen.Pos2i $$1) {
/* 701 */       Objects.requireNonNull($$0); int $$2 = $$1.y / 9;
/* 702 */       if ($$2 < 0) {
/* 703 */         return 0;
/*     */       }
/* 705 */       if ($$2 >= this.lines.length) {
/* 706 */         return this.fullText.length();
/*     */       }
/* 708 */       BookEditScreen.LineInfo $$3 = this.lines[$$2];
/* 709 */       return this.lineStarts[$$2] + $$0.getSplitter().plainIndexAtWidth($$3.contents, $$1.x, $$3.style);
/*     */     }
/*     */     
/*     */     public int changeLine(int $$0, int $$1) {
/* 713 */       int $$7, $$2 = BookEditScreen.findLineFromPos(this.lineStarts, $$0);
/* 714 */       int $$3 = $$2 + $$1;
/*     */       
/* 716 */       if (0 <= $$3 && $$3 < this.lineStarts.length) {
/* 717 */         int $$4 = $$0 - this.lineStarts[$$2];
/* 718 */         int $$5 = (this.lines[$$3]).contents.length();
/* 719 */         int $$6 = this.lineStarts[$$3] + Math.min($$4, $$5);
/*     */       } else {
/* 721 */         $$7 = $$0;
/*     */       } 
/* 723 */       return $$7;
/*     */     }
/*     */     
/*     */     public int findLineStart(int $$0) {
/* 727 */       int $$1 = BookEditScreen.findLineFromPos(this.lineStarts, $$0);
/* 728 */       return this.lineStarts[$$1];
/*     */     }
/*     */     
/*     */     public int findLineEnd(int $$0) {
/* 732 */       int $$1 = BookEditScreen.findLineFromPos(this.lineStarts, $$0);
/* 733 */       return this.lineStarts[$$1] + (this.lines[$$1]).contents.length();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\inventory\BookEditScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */