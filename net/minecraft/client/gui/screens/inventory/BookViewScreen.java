/*     */ package net.minecraft.client.gui.screens.inventory;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.IntFunction;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.client.GameNarrator;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.ListTag;
/*     */ import net.minecraft.network.chat.ClickEvent;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.FormattedText;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.network.chat.Style;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.FormattedCharSequence;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.item.WrittenBookItem;
/*     */ 
/*     */ public class BookViewScreen
/*     */   extends Screen {
/*     */   public static final int PAGE_INDICATOR_TEXT_Y_OFFSET = 16;
/*     */   public static final int PAGE_TEXT_X_OFFSET = 36;
/*     */   public static final int PAGE_TEXT_Y_OFFSET = 30;
/*     */   
/*     */   public static interface BookAccess {
/*     */     int getPageCount();
/*     */     
/*     */     FormattedText getPageRaw(int param1Int);
/*     */     
/*     */     default FormattedText getPage(int $$0) {
/*  44 */       if ($$0 >= 0 && $$0 < getPageCount()) {
/*  45 */         return getPageRaw($$0);
/*     */       }
/*  47 */       return FormattedText.EMPTY;
/*     */     }
/*     */     
/*     */     static BookAccess fromItem(ItemStack $$0) {
/*  51 */       if ($$0.is(Items.WRITTEN_BOOK))
/*  52 */         return new BookViewScreen.WrittenBookAccess($$0); 
/*  53 */       if ($$0.is(Items.WRITABLE_BOOK)) {
/*  54 */         return new BookViewScreen.WritableBookAccess($$0);
/*     */       }
/*  56 */       return BookViewScreen.EMPTY_ACCESS;
/*     */     }
/*     */   }
/*     */   
/*  60 */   public static final BookAccess EMPTY_ACCESS = new BookAccess()
/*     */     {
/*     */       public int getPageCount() {
/*  63 */         return 0;
/*     */       }
/*     */ 
/*     */       
/*     */       public FormattedText getPageRaw(int $$0) {
/*  68 */         return FormattedText.EMPTY;
/*     */       }
/*     */     };
/*     */   
/*     */   public static class WrittenBookAccess implements BookAccess {
/*     */     private final List<String> pages;
/*     */     
/*     */     public WrittenBookAccess(ItemStack $$0) {
/*  76 */       this.pages = readPages($$0);
/*     */     }
/*     */     
/*     */     private static List<String> readPages(ItemStack $$0) {
/*  80 */       CompoundTag $$1 = $$0.getTag();
/*  81 */       if ($$1 != null && WrittenBookItem.makeSureTagIsValid($$1)) {
/*  82 */         return BookViewScreen.loadPages($$1);
/*     */       }
/*     */       
/*  85 */       return (List<String>)ImmutableList.of(Component.Serializer.toJson((Component)Component.translatable("book.invalid.tag").withStyle(ChatFormatting.DARK_RED)));
/*     */     }
/*     */ 
/*     */     
/*     */     public int getPageCount() {
/*  90 */       return this.pages.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public FormattedText getPageRaw(int $$0) {
/*  95 */       String $$1 = this.pages.get($$0);
/*     */       try {
/*  97 */         MutableComponent mutableComponent = Component.Serializer.fromJson($$1);
/*  98 */         if (mutableComponent != null) {
/*  99 */           return (FormattedText)mutableComponent;
/*     */         }
/* 101 */       } catch (Exception exception) {}
/*     */       
/* 103 */       return FormattedText.of($$1);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class WritableBookAccess implements BookAccess {
/*     */     private final List<String> pages;
/*     */     
/*     */     public WritableBookAccess(ItemStack $$0) {
/* 111 */       this.pages = readPages($$0);
/*     */     }
/*     */     
/*     */     private static List<String> readPages(ItemStack $$0) {
/* 115 */       CompoundTag $$1 = $$0.getTag();
/* 116 */       return ($$1 != null) ? BookViewScreen.loadPages($$1) : (List<String>)ImmutableList.of();
/*     */     }
/*     */ 
/*     */     
/*     */     public int getPageCount() {
/* 121 */       return this.pages.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public FormattedText getPageRaw(int $$0) {
/* 126 */       return FormattedText.of(this.pages.get($$0));
/*     */     }
/*     */   }
/*     */   
/* 130 */   public static final ResourceLocation BOOK_LOCATION = new ResourceLocation("textures/gui/book.png");
/*     */   
/*     */   protected static final int TEXT_WIDTH = 114;
/*     */   
/*     */   protected static final int TEXT_HEIGHT = 128;
/*     */   
/*     */   protected static final int IMAGE_WIDTH = 192;
/*     */   protected static final int IMAGE_HEIGHT = 192;
/*     */   private BookAccess bookAccess;
/*     */   private int currentPage;
/* 140 */   private List<FormattedCharSequence> cachedPageComponents = Collections.emptyList();
/* 141 */   private int cachedPage = -1;
/* 142 */   private Component pageMsg = CommonComponents.EMPTY;
/*     */   
/*     */   private PageButton forwardButton;
/*     */   
/*     */   private PageButton backButton;
/*     */   private final boolean playTurnSound;
/*     */   
/*     */   public BookViewScreen(BookAccess $$0) {
/* 150 */     this($$0, true);
/*     */   }
/*     */   
/*     */   public BookViewScreen() {
/* 154 */     this(EMPTY_ACCESS, false);
/*     */   }
/*     */   
/*     */   private BookViewScreen(BookAccess $$0, boolean $$1) {
/* 158 */     super(GameNarrator.NO_TITLE);
/* 159 */     this.bookAccess = $$0;
/* 160 */     this.playTurnSound = $$1;
/*     */   }
/*     */   
/*     */   public void setBookAccess(BookAccess $$0) {
/* 164 */     this.bookAccess = $$0;
/* 165 */     this.currentPage = Mth.clamp(this.currentPage, 0, $$0.getPageCount());
/* 166 */     updateButtonVisibility();
/* 167 */     this.cachedPage = -1;
/*     */   }
/*     */   
/*     */   public boolean setPage(int $$0) {
/* 171 */     int $$1 = Mth.clamp($$0, 0, this.bookAccess.getPageCount() - 1);
/* 172 */     if ($$1 != this.currentPage) {
/* 173 */       this.currentPage = $$1;
/* 174 */       updateButtonVisibility();
/* 175 */       this.cachedPage = -1;
/* 176 */       return true;
/*     */     } 
/* 178 */     return false;
/*     */   }
/*     */   
/*     */   protected boolean forcePage(int $$0) {
/* 182 */     return setPage($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void init() {
/* 187 */     createMenuControls();
/* 188 */     createPageControlButtons();
/*     */   }
/*     */   
/*     */   protected void createMenuControls() {
/* 192 */     addRenderableWidget((GuiEventListener)Button.builder(CommonComponents.GUI_DONE, $$0 -> onClose()).bounds(this.width / 2 - 100, 196, 200, 20).build());
/*     */   }
/*     */   
/*     */   protected void createPageControlButtons() {
/* 196 */     int $$0 = (this.width - 192) / 2;
/* 197 */     int $$1 = 2;
/*     */     
/* 199 */     this.forwardButton = (PageButton)addRenderableWidget((GuiEventListener)new PageButton($$0 + 116, 159, true, $$0 -> pageForward(), this.playTurnSound));
/* 200 */     this.backButton = (PageButton)addRenderableWidget((GuiEventListener)new PageButton($$0 + 43, 159, false, $$0 -> pageBack(), this.playTurnSound));
/*     */     
/* 202 */     updateButtonVisibility();
/*     */   }
/*     */   
/*     */   private int getNumPages() {
/* 206 */     return this.bookAccess.getPageCount();
/*     */   }
/*     */   
/*     */   protected void pageBack() {
/* 210 */     if (this.currentPage > 0) {
/* 211 */       this.currentPage--;
/*     */     }
/* 213 */     updateButtonVisibility();
/*     */   }
/*     */   
/*     */   protected void pageForward() {
/* 217 */     if (this.currentPage < getNumPages() - 1) {
/* 218 */       this.currentPage++;
/*     */     }
/* 220 */     updateButtonVisibility();
/*     */   }
/*     */   
/*     */   private void updateButtonVisibility() {
/* 224 */     this.forwardButton.visible = (this.currentPage < getNumPages() - 1);
/* 225 */     this.backButton.visible = (this.currentPage > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean keyPressed(int $$0, int $$1, int $$2) {
/* 230 */     if (super.keyPressed($$0, $$1, $$2)) {
/* 231 */       return true;
/*     */     }
/*     */     
/* 234 */     switch ($$0) {
/*     */       case 266:
/* 236 */         this.backButton.onPress();
/* 237 */         return true;
/*     */       case 267:
/* 239 */         this.forwardButton.onPress();
/* 240 */         return true;
/*     */     } 
/* 242 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 248 */     super.render($$0, $$1, $$2, $$3);
/*     */     
/* 250 */     int $$4 = (this.width - 192) / 2;
/* 251 */     int $$5 = 2;
/*     */     
/* 253 */     if (this.cachedPage != this.currentPage) {
/* 254 */       FormattedText $$6 = this.bookAccess.getPage(this.currentPage);
/* 255 */       this.cachedPageComponents = this.font.split($$6, 114);
/* 256 */       this.pageMsg = (Component)Component.translatable("book.pageIndicator", new Object[] { Integer.valueOf(this.currentPage + 1), Integer.valueOf(Math.max(getNumPages(), 1)) });
/*     */     } 
/* 258 */     this.cachedPage = this.currentPage;
/*     */     
/* 260 */     int $$7 = this.font.width((FormattedText)this.pageMsg);
/* 261 */     $$0.drawString(this.font, this.pageMsg, $$4 - $$7 + 192 - 44, 18, 0, false);
/*     */     
/* 263 */     Objects.requireNonNull(this.font); int $$8 = Math.min(128 / 9, this.cachedPageComponents.size());
/* 264 */     for (int $$9 = 0; $$9 < $$8; $$9++) {
/* 265 */       FormattedCharSequence $$10 = this.cachedPageComponents.get($$9);
/* 266 */       Objects.requireNonNull(this.font); $$0.drawString(this.font, $$10, $$4 + 36, 32 + $$9 * 9, 0, false);
/*     */     } 
/*     */     
/* 269 */     Style $$11 = getClickedComponentStyleAt($$1, $$2);
/* 270 */     if ($$11 != null) {
/* 271 */       $$0.renderComponentHoverEffect(this.font, $$11, $$1, $$2);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderBackground(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 277 */     super.renderBackground($$0, $$1, $$2, $$3);
/* 278 */     $$0.blit(BOOK_LOCATION, (this.width - 192) / 2, 2, 0, 0, 192, 192);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(double $$0, double $$1, int $$2) {
/* 283 */     if ($$2 == 0) {
/* 284 */       Style $$3 = getClickedComponentStyleAt($$0, $$1);
/* 285 */       if ($$3 != null && handleComponentClicked($$3)) {
/* 286 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 290 */     return super.mouseClicked($$0, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean handleComponentClicked(Style $$0) {
/* 295 */     ClickEvent $$1 = $$0.getClickEvent();
/* 296 */     if ($$1 == null) {
/* 297 */       return false;
/*     */     }
/*     */     
/* 300 */     if ($$1.getAction() == ClickEvent.Action.CHANGE_PAGE) {
/* 301 */       String $$2 = $$1.getValue();
/*     */       try {
/* 303 */         int $$3 = Integer.parseInt($$2) - 1;
/* 304 */         return forcePage($$3);
/* 305 */       } catch (Exception exception) {
/*     */ 
/*     */         
/* 308 */         return false;
/*     */       } 
/*     */     } 
/* 311 */     boolean $$4 = super.handleComponentClicked($$0);
/* 312 */     if ($$4 && $$1.getAction() == ClickEvent.Action.RUN_COMMAND) {
/* 313 */       closeScreen();
/*     */     }
/* 315 */     return $$4;
/*     */   }
/*     */   
/*     */   protected void closeScreen() {
/* 319 */     this.minecraft.setScreen(null);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Style getClickedComponentStyleAt(double $$0, double $$1) {
/* 324 */     if (this.cachedPageComponents.isEmpty()) {
/* 325 */       return null;
/*     */     }
/*     */     
/* 328 */     int $$2 = Mth.floor($$0 - ((this.width - 192) / 2) - 36.0D);
/* 329 */     int $$3 = Mth.floor($$1 - 2.0D - 30.0D);
/*     */     
/* 331 */     if ($$2 < 0 || $$3 < 0) {
/* 332 */       return null;
/*     */     }
/* 334 */     Objects.requireNonNull(this.font); int $$4 = Math.min(128 / 9, this.cachedPageComponents.size());
/*     */     
/* 336 */     Objects.requireNonNull(this.minecraft.font); if ($$2 <= 114 && $$3 < 9 * $$4 + $$4) {
/* 337 */       Objects.requireNonNull(this.minecraft.font); int $$5 = $$3 / 9;
/* 338 */       if ($$5 >= 0 && $$5 < this.cachedPageComponents.size()) {
/* 339 */         FormattedCharSequence $$6 = this.cachedPageComponents.get($$5);
/* 340 */         return this.minecraft.font.getSplitter().componentStyleAtWidth($$6, $$2);
/*     */       } 
/*     */       
/* 343 */       return null;
/*     */     } 
/*     */     
/* 346 */     return null;
/*     */   }
/*     */   
/*     */   static List<String> loadPages(CompoundTag $$0) {
/* 350 */     ImmutableList.Builder<String> $$1 = ImmutableList.builder();
/* 351 */     Objects.requireNonNull($$1); loadPages($$0, $$1::add);
/* 352 */     return (List<String>)$$1.build();
/*     */   }
/*     */   
/*     */   public static void loadPages(CompoundTag $$0, Consumer<String> $$1) {
/*     */     IntFunction<String> $$5;
/* 357 */     ListTag $$2 = $$0.getList("pages", 8).copy();
/* 358 */     if (Minecraft.getInstance().isTextFilteringEnabled() && $$0.contains("filtered_pages", 10)) {
/* 359 */       CompoundTag $$3 = $$0.getCompound("filtered_pages");
/* 360 */       IntFunction<String> $$4 = $$2 -> {
/*     */           String $$3 = String.valueOf($$2);
/*     */           return $$0.contains($$3) ? $$0.getString($$3) : $$1.getString($$2);
/*     */         };
/*     */     } else {
/* 365 */       Objects.requireNonNull($$2); $$5 = $$2::getString;
/*     */     } 
/*     */     
/* 368 */     for (int $$6 = 0; $$6 < $$2.size(); $$6++)
/* 369 */       $$1.accept($$5.apply($$6)); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\inventory\BookViewScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */