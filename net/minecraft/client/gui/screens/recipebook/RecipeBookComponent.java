/*     */ package net.minecraft.client.gui.screens.recipebook;
/*     */ import com.google.common.collect.Lists;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.client.ClientRecipeBook;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.RecipeBookCategories;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.AbstractWidget;
/*     */ import net.minecraft.client.gui.components.EditBox;
/*     */ import net.minecraft.client.gui.components.Renderable;
/*     */ import net.minecraft.client.gui.components.StateSwitchingButton;
/*     */ import net.minecraft.client.gui.components.Tooltip;
/*     */ import net.minecraft.client.gui.components.WidgetSprites;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.client.gui.narration.NarratableEntry;
/*     */ import net.minecraft.client.gui.narration.NarrationElementOutput;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.client.resources.language.LanguageInfo;
/*     */ import net.minecraft.client.resources.language.LanguageManager;
/*     */ import net.minecraft.client.searchtree.SearchRegistry;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.network.protocol.game.ServerboundRecipeBookChangeSettingsPacket;
/*     */ import net.minecraft.recipebook.PlaceRecipe;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.stats.RecipeBook;
/*     */ import net.minecraft.world.entity.player.StackedContents;
/*     */ import net.minecraft.world.inventory.RecipeBookMenu;
/*     */ import net.minecraft.world.inventory.RecipeBookType;
/*     */ import net.minecraft.world.inventory.Slot;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.crafting.Ingredient;
/*     */ import net.minecraft.world.item.crafting.RecipeHolder;
/*     */ 
/*     */ public class RecipeBookComponent implements PlaceRecipe<Ingredient>, Renderable, GuiEventListener, NarratableEntry, RecipeShownListener {
/*  42 */   public static final WidgetSprites RECIPE_BUTTON_SPRITES = new WidgetSprites(new ResourceLocation("recipe_book/button"), new ResourceLocation("recipe_book/button_highlighted"));
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  47 */   private static final WidgetSprites FILTER_BUTTON_SPRITES = new WidgetSprites(new ResourceLocation("recipe_book/filter_enabled"), new ResourceLocation("recipe_book/filter_disabled"), new ResourceLocation("recipe_book/filter_enabled_highlighted"), new ResourceLocation("recipe_book/filter_disabled_highlighted"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  54 */   protected static final ResourceLocation RECIPE_BOOK_LOCATION = new ResourceLocation("textures/gui/recipe_book.png");
/*  55 */   private static final Component SEARCH_HINT = (Component)Component.translatable("gui.recipebook.search_hint").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY);
/*     */   
/*     */   public static final int IMAGE_WIDTH = 147;
/*     */   public static final int IMAGE_HEIGHT = 166;
/*     */   private static final int OFFSET_X_POSITION = 86;
/*  60 */   private static final Component ONLY_CRAFTABLES_TOOLTIP = (Component)Component.translatable("gui.recipebook.toggleRecipes.craftable");
/*  61 */   private static final Component ALL_RECIPES_TOOLTIP = (Component)Component.translatable("gui.recipebook.toggleRecipes.all");
/*     */   
/*     */   private int xOffset;
/*     */   
/*     */   private int width;
/*     */   
/*     */   private int height;
/*  68 */   protected final GhostRecipe ghostRecipe = new GhostRecipe();
/*  69 */   private final List<RecipeBookTabButton> tabButtons = Lists.newArrayList();
/*     */   
/*     */   @Nullable
/*     */   private RecipeBookTabButton selectedTab;
/*     */   
/*     */   protected StateSwitchingButton filterButton;
/*     */   protected RecipeBookMenu<?> menu;
/*     */   protected Minecraft minecraft;
/*     */   @Nullable
/*     */   private EditBox searchBox;
/*  79 */   private String lastSearch = "";
/*     */   
/*     */   private ClientRecipeBook book;
/*  82 */   private final RecipeBookPage recipeBookPage = new RecipeBookPage();
/*     */   
/*  84 */   private final StackedContents stackedContents = new StackedContents();
/*     */   
/*     */   private int timesInventoryChanged;
/*     */   
/*     */   private boolean ignoreTextInput;
/*     */   
/*     */   private boolean visible;
/*     */   private boolean widthTooNarrow;
/*     */   
/*     */   public void init(int $$0, int $$1, Minecraft $$2, boolean $$3, RecipeBookMenu<?> $$4) {
/*  94 */     this.minecraft = $$2;
/*  95 */     this.width = $$0;
/*  96 */     this.height = $$1;
/*  97 */     this.menu = $$4;
/*  98 */     this.widthTooNarrow = $$3;
/*  99 */     $$2.player.containerMenu = (AbstractContainerMenu)$$4;
/* 100 */     this.book = $$2.player.getRecipeBook();
/*     */     
/* 102 */     this.timesInventoryChanged = $$2.player.getInventory().getTimesChanged();
/*     */     
/* 104 */     this.visible = isVisibleAccordingToBookData();
/* 105 */     if (this.visible) {
/* 106 */       initVisuals();
/*     */     }
/*     */   }
/*     */   
/*     */   public void initVisuals() {
/* 111 */     this.xOffset = this.widthTooNarrow ? 0 : 86;
/* 112 */     int $$0 = (this.width - 147) / 2 - this.xOffset;
/* 113 */     int $$1 = (this.height - 166) / 2;
/*     */     
/* 115 */     this.stackedContents.clear();
/* 116 */     this.minecraft.player.getInventory().fillStackedContents(this.stackedContents);
/* 117 */     this.menu.fillCraftSlotsStackedContents(this.stackedContents);
/*     */     
/* 119 */     String $$2 = (this.searchBox != null) ? this.searchBox.getValue() : "";
/* 120 */     Objects.requireNonNull(this.minecraft.font); this.searchBox = new EditBox(this.minecraft.font, $$0 + 25, $$1 + 13, 81, 9 + 5, (Component)Component.translatable("itemGroup.search"));
/* 121 */     this.searchBox.setMaxLength(50);
/* 122 */     this.searchBox.setVisible(true);
/* 123 */     this.searchBox.setTextColor(16777215);
/* 124 */     this.searchBox.setValue($$2);
/* 125 */     this.searchBox.setHint(SEARCH_HINT);
/*     */     
/* 127 */     this.recipeBookPage.init(this.minecraft, $$0, $$1);
/* 128 */     this.recipeBookPage.addListener(this);
/*     */     
/* 130 */     this.filterButton = new StateSwitchingButton($$0 + 110, $$1 + 12, 26, 16, this.book.isFiltering(this.menu));
/* 131 */     updateFilterButtonTooltip();
/* 132 */     initFilterButtonTextures();
/*     */     
/* 134 */     this.tabButtons.clear();
/*     */     
/* 136 */     for (RecipeBookCategories $$3 : RecipeBookCategories.getCategories(this.menu.getRecipeBookType())) {
/* 137 */       this.tabButtons.add(new RecipeBookTabButton($$3));
/*     */     }
/*     */     
/* 140 */     if (this.selectedTab != null) {
/* 141 */       this.selectedTab = this.tabButtons.stream().filter($$0 -> $$0.getCategory().equals(this.selectedTab.getCategory())).findFirst().orElse(null);
/*     */     }
/* 143 */     if (this.selectedTab == null) {
/* 144 */       this.selectedTab = this.tabButtons.get(0);
/*     */     }
/* 146 */     this.selectedTab.setStateTriggered(true);
/*     */     
/* 148 */     updateCollections(false);
/* 149 */     updateTabs();
/*     */   }
/*     */   
/*     */   private void updateFilterButtonTooltip() {
/* 153 */     this.filterButton.setTooltip(this.filterButton.isStateTriggered() ? Tooltip.create(getRecipeFilterName()) : Tooltip.create(ALL_RECIPES_TOOLTIP));
/*     */   }
/*     */   
/*     */   protected void initFilterButtonTextures() {
/* 157 */     this.filterButton.initTextureValues(FILTER_BUTTON_SPRITES);
/*     */   }
/*     */   
/*     */   public int updateScreenPosition(int $$0, int $$1) {
/*     */     int $$3;
/* 162 */     if (isVisible() && !this.widthTooNarrow) {
/* 163 */       int $$2 = 177 + ($$0 - $$1 - 200) / 2;
/*     */     } else {
/* 165 */       $$3 = ($$0 - $$1) / 2;
/*     */     } 
/*     */     
/* 168 */     return $$3;
/*     */   }
/*     */   
/*     */   public void toggleVisibility() {
/* 172 */     setVisible(!isVisible());
/*     */   }
/*     */   
/*     */   public boolean isVisible() {
/* 176 */     return this.visible;
/*     */   }
/*     */   
/*     */   private boolean isVisibleAccordingToBookData() {
/* 180 */     return this.book.isOpen(this.menu.getRecipeBookType());
/*     */   }
/*     */   
/*     */   protected void setVisible(boolean $$0) {
/* 184 */     if ($$0) {
/* 185 */       initVisuals();
/*     */     }
/*     */     
/* 188 */     this.visible = $$0;
/* 189 */     this.book.setOpen(this.menu.getRecipeBookType(), $$0);
/* 190 */     if (!$$0) {
/* 191 */       this.recipeBookPage.setInvisible();
/*     */     }
/* 193 */     sendUpdateSettings();
/*     */   }
/*     */   
/*     */   public void slotClicked(@Nullable Slot $$0) {
/* 197 */     if ($$0 != null && $$0.index < this.menu.getSize()) {
/* 198 */       this.ghostRecipe.clear();
/*     */       
/* 200 */       if (isVisible()) {
/* 201 */         updateStackedContents();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updateCollections(boolean $$0) {
/* 207 */     List<RecipeCollection> $$1 = this.book.getCollection(this.selectedTab.getCategory());
/*     */ 
/*     */     
/* 210 */     $$1.forEach($$0 -> $$0.canCraft(this.stackedContents, this.menu.getGridWidth(), this.menu.getGridHeight(), (RecipeBook)this.book));
/*     */     
/* 212 */     List<RecipeCollection> $$2 = Lists.newArrayList($$1);
/*     */ 
/*     */     
/* 215 */     $$2.removeIf($$0 -> !$$0.hasKnownRecipes());
/*     */ 
/*     */     
/* 218 */     $$2.removeIf($$0 -> !$$0.hasFitting());
/*     */ 
/*     */     
/* 221 */     String $$3 = this.searchBox.getValue();
/* 222 */     if (!$$3.isEmpty()) {
/* 223 */       ObjectLinkedOpenHashSet objectLinkedOpenHashSet = new ObjectLinkedOpenHashSet(this.minecraft.getSearchTree(SearchRegistry.RECIPE_COLLECTIONS).search($$3.toLowerCase(Locale.ROOT)));
/*     */       
/* 225 */       $$2.removeIf($$1 -> !$$0.contains($$1));
/*     */     } 
/*     */ 
/*     */     
/* 229 */     if (this.book.isFiltering(this.menu)) {
/* 230 */       $$2.removeIf($$0 -> !$$0.hasCraftable());
/*     */     }
/*     */     
/* 233 */     this.recipeBookPage.updateCollections($$2, $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateTabs() {
/* 238 */     int $$0 = (this.width - 147) / 2 - this.xOffset - 30;
/* 239 */     int $$1 = (this.height - 166) / 2 + 3;
/* 240 */     int $$2 = 27;
/*     */     
/* 242 */     int $$3 = 0;
/* 243 */     for (RecipeBookTabButton $$4 : this.tabButtons) {
/* 244 */       RecipeBookCategories $$5 = $$4.getCategory();
/* 245 */       if ($$5 == RecipeBookCategories.CRAFTING_SEARCH || $$5 == RecipeBookCategories.FURNACE_SEARCH) {
/* 246 */         $$4.visible = true;
/* 247 */         $$4.setPosition($$0, $$1 + 27 * $$3++);
/*     */         
/*     */         continue;
/*     */       } 
/* 251 */       if ($$4.updateVisibility(this.book)) {
/* 252 */         $$4.setPosition($$0, $$1 + 27 * $$3++);
/* 253 */         $$4.startAnimation(this.minecraft);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 260 */     boolean $$0 = isVisibleAccordingToBookData();
/* 261 */     if (isVisible() != $$0) {
/* 262 */       setVisible($$0);
/*     */     }
/*     */     
/* 265 */     if (!isVisible()) {
/*     */       return;
/*     */     }
/*     */     
/* 269 */     if (this.timesInventoryChanged != this.minecraft.player.getInventory().getTimesChanged()) {
/* 270 */       updateStackedContents();
/* 271 */       this.timesInventoryChanged = this.minecraft.player.getInventory().getTimesChanged();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updateStackedContents() {
/* 276 */     this.stackedContents.clear();
/* 277 */     this.minecraft.player.getInventory().fillStackedContents(this.stackedContents);
/* 278 */     this.menu.fillCraftSlotsStackedContents(this.stackedContents);
/*     */     
/* 280 */     updateCollections(false);
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 285 */     if (!isVisible()) {
/*     */       return;
/*     */     }
/*     */     
/* 289 */     $$0.pose().pushPose();
/* 290 */     $$0.pose().translate(0.0F, 0.0F, 100.0F);
/*     */     
/* 292 */     int $$4 = (this.width - 147) / 2 - this.xOffset;
/* 293 */     int $$5 = (this.height - 166) / 2;
/* 294 */     $$0.blit(RECIPE_BOOK_LOCATION, $$4, $$5, 1, 1, 147, 166);
/*     */     
/* 296 */     this.searchBox.render($$0, $$1, $$2, $$3);
/*     */     
/* 298 */     for (RecipeBookTabButton $$6 : this.tabButtons) {
/* 299 */       $$6.render($$0, $$1, $$2, $$3);
/*     */     }
/*     */     
/* 302 */     this.filterButton.render($$0, $$1, $$2, $$3);
/*     */     
/* 304 */     this.recipeBookPage.render($$0, $$4, $$5, $$1, $$2, $$3);
/*     */     
/* 306 */     $$0.pose().popPose();
/*     */   }
/*     */   
/*     */   public void renderTooltip(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4) {
/* 310 */     if (!isVisible()) {
/*     */       return;
/*     */     }
/*     */     
/* 314 */     this.recipeBookPage.renderTooltip($$0, $$3, $$4);
/*     */     
/* 316 */     renderGhostRecipeTooltip($$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */   
/*     */   protected Component getRecipeFilterName() {
/* 320 */     return ONLY_CRAFTABLES_TOOLTIP;
/*     */   }
/*     */   
/*     */   private void renderGhostRecipeTooltip(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4) {
/* 324 */     ItemStack $$5 = null;
/*     */     
/* 326 */     for (int $$6 = 0; $$6 < this.ghostRecipe.size(); $$6++) {
/* 327 */       GhostRecipe.GhostIngredient $$7 = this.ghostRecipe.get($$6);
/*     */       
/* 329 */       int $$8 = $$7.getX() + $$1;
/* 330 */       int $$9 = $$7.getY() + $$2;
/*     */       
/* 332 */       if ($$3 >= $$8 && $$4 >= $$9 && $$3 < $$8 + 16 && $$4 < $$9 + 16) {
/* 333 */         $$5 = $$7.getItem();
/*     */       }
/*     */     } 
/*     */     
/* 337 */     if ($$5 != null && this.minecraft.screen != null) {
/* 338 */       $$0.renderComponentTooltip(this.minecraft.font, Screen.getTooltipFromItem(this.minecraft, $$5), $$3, $$4);
/*     */     }
/*     */   }
/*     */   
/*     */   public void renderGhostRecipe(GuiGraphics $$0, int $$1, int $$2, boolean $$3, float $$4) {
/* 343 */     this.ghostRecipe.render($$0, this.minecraft, $$1, $$2, $$3, $$4);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(double $$0, double $$1, int $$2) {
/* 348 */     if (!isVisible() || this.minecraft.player.isSpectator()) {
/* 349 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 353 */     if (this.recipeBookPage.mouseClicked($$0, $$1, $$2, (this.width - 147) / 2 - this.xOffset, (this.height - 166) / 2, 147, 166)) {
/* 354 */       RecipeHolder<?> $$3 = this.recipeBookPage.getLastClickedRecipe();
/* 355 */       RecipeCollection $$4 = this.recipeBookPage.getLastClickedRecipeCollection();
/*     */       
/* 357 */       if ($$3 != null && $$4 != null) {
/* 358 */         if (!$$4.isCraftable($$3) && this.ghostRecipe.getRecipe() == $$3) {
/* 359 */           return false;
/*     */         }
/*     */         
/* 362 */         this.ghostRecipe.clear();
/*     */         
/* 364 */         this.minecraft.gameMode.handlePlaceRecipe(this.minecraft.player.containerMenu.containerId, $$3, Screen.hasShiftDown());
/*     */         
/* 366 */         if (!isOffsetNextToMainGUI()) {
/* 367 */           setVisible(false);
/*     */         }
/*     */       } 
/*     */       
/* 371 */       return true;
/*     */     } 
/*     */     
/* 374 */     if (this.searchBox.mouseClicked($$0, $$1, $$2)) {
/* 375 */       this.searchBox.setFocused(true);
/* 376 */       return true;
/*     */     } 
/* 378 */     this.searchBox.setFocused(false);
/*     */ 
/*     */     
/* 381 */     if (this.filterButton.mouseClicked($$0, $$1, $$2)) {
/* 382 */       boolean $$5 = toggleFiltering();
/* 383 */       this.filterButton.setStateTriggered($$5);
/* 384 */       updateFilterButtonTooltip();
/*     */       
/* 386 */       sendUpdateSettings();
/*     */       
/* 388 */       updateCollections(false);
/*     */       
/* 390 */       return true;
/*     */     } 
/*     */     
/* 393 */     for (RecipeBookTabButton $$6 : this.tabButtons) {
/* 394 */       if ($$6.mouseClicked($$0, $$1, $$2)) {
/* 395 */         if (this.selectedTab != $$6) {
/* 396 */           if (this.selectedTab != null) {
/* 397 */             this.selectedTab.setStateTriggered(false);
/*     */           }
/* 399 */           this.selectedTab = $$6;
/* 400 */           this.selectedTab.setStateTriggered(true);
/*     */           
/* 402 */           updateCollections(true);
/*     */         } 
/*     */         
/* 405 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 409 */     return false;
/*     */   }
/*     */   
/*     */   private boolean toggleFiltering() {
/* 413 */     RecipeBookType $$0 = this.menu.getRecipeBookType();
/* 414 */     boolean $$1 = !this.book.isFiltering($$0);
/* 415 */     this.book.setFiltering($$0, $$1);
/* 416 */     return $$1;
/*     */   }
/*     */   
/*     */   public boolean hasClickedOutside(double $$0, double $$1, int $$2, int $$3, int $$4, int $$5, int $$6) {
/* 420 */     if (!isVisible()) {
/* 421 */       return true;
/*     */     }
/*     */     
/* 424 */     boolean $$7 = ($$0 < $$2 || $$1 < $$3 || $$0 >= ($$2 + $$4) || $$1 >= ($$3 + $$5));
/* 425 */     boolean $$8 = (($$2 - 147) < $$0 && $$0 < $$2 && $$3 < $$1 && $$1 < ($$3 + $$5));
/*     */     
/* 427 */     return ($$7 && !$$8 && !this.selectedTab.isHoveredOrFocused());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean keyPressed(int $$0, int $$1, int $$2) {
/* 432 */     this.ignoreTextInput = false;
/* 433 */     if (!isVisible() || this.minecraft.player.isSpectator()) {
/* 434 */       return false;
/*     */     }
/*     */     
/* 437 */     if ($$0 == 256 && !isOffsetNextToMainGUI()) {
/* 438 */       setVisible(false);
/* 439 */       return true;
/*     */     } 
/*     */     
/* 442 */     if (this.searchBox.keyPressed($$0, $$1, $$2)) {
/* 443 */       checkSearchStringUpdate();
/* 444 */       return true;
/*     */     } 
/* 446 */     if (this.searchBox.isFocused() && this.searchBox.isVisible() && $$0 != 256)
/*     */     {
/* 448 */       return true;
/*     */     }
/*     */     
/* 451 */     if (this.minecraft.options.keyChat.matches($$0, $$1) && !this.searchBox.isFocused()) {
/* 452 */       this.ignoreTextInput = true;
/* 453 */       this.searchBox.setFocused(true);
/*     */       
/* 455 */       return true;
/*     */     } 
/*     */     
/* 458 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean keyReleased(int $$0, int $$1, int $$2) {
/* 463 */     this.ignoreTextInput = false;
/* 464 */     return super.keyReleased($$0, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean charTyped(char $$0, int $$1) {
/* 469 */     if (this.ignoreTextInput) {
/* 470 */       return false;
/*     */     }
/* 472 */     if (!isVisible() || this.minecraft.player.isSpectator()) {
/* 473 */       return false;
/*     */     }
/*     */     
/* 476 */     if (this.searchBox.charTyped($$0, $$1)) {
/* 477 */       checkSearchStringUpdate();
/* 478 */       return true;
/*     */     } 
/*     */     
/* 481 */     return super.charTyped($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isMouseOver(double $$0, double $$1) {
/* 486 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFocused(boolean $$0) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFocused() {
/* 496 */     return false;
/*     */   }
/*     */   
/*     */   private void checkSearchStringUpdate() {
/* 500 */     String $$0 = this.searchBox.getValue().toLowerCase(Locale.ROOT);
/* 501 */     pirateSpeechForThePeople($$0);
/*     */     
/* 503 */     if (!$$0.equals(this.lastSearch)) {
/* 504 */       updateCollections(false);
/* 505 */       this.lastSearch = $$0;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void pirateSpeechForThePeople(String $$0) {
/* 510 */     if ("excitedze".equals($$0)) {
/* 511 */       LanguageManager $$1 = this.minecraft.getLanguageManager();
/* 512 */       String $$2 = "en_pt";
/* 513 */       LanguageInfo $$3 = $$1.getLanguage("en_pt");
/* 514 */       if ($$3 == null || $$1.getSelected().equals("en_pt")) {
/*     */         return;
/*     */       }
/* 517 */       $$1.setSelected("en_pt");
/* 518 */       this.minecraft.options.languageCode = "en_pt";
/* 519 */       this.minecraft.reloadResourcePacks();
/* 520 */       this.minecraft.options.save();
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isOffsetNextToMainGUI() {
/* 525 */     return (this.xOffset == 86);
/*     */   }
/*     */   
/*     */   public void recipesUpdated() {
/* 529 */     updateTabs();
/*     */     
/* 531 */     if (isVisible())
/*     */     {
/* 533 */       updateCollections(false);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void recipesShown(List<RecipeHolder<?>> $$0) {
/* 539 */     for (RecipeHolder<?> $$1 : $$0) {
/* 540 */       this.minecraft.player.removeRecipeHighlight($$1);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setupGhostRecipe(RecipeHolder<?> $$0, List<Slot> $$1) {
/* 545 */     ItemStack $$2 = $$0.value().getResultItem(this.minecraft.level.registryAccess());
/* 546 */     this.ghostRecipe.setRecipe($$0);
/* 547 */     this.ghostRecipe.addIngredient(Ingredient.of(new ItemStack[] { $$2 }, ), ((Slot)$$1.get(0)).x, ((Slot)$$1.get(0)).y);
/*     */     
/* 549 */     placeRecipe(this.menu.getGridWidth(), this.menu.getGridHeight(), this.menu.getResultSlotIndex(), $$0, $$0.value().getIngredients().iterator(), 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addItemToSlot(Iterator<Ingredient> $$0, int $$1, int $$2, int $$3, int $$4) {
/* 554 */     Ingredient $$5 = $$0.next();
/* 555 */     if (!$$5.isEmpty()) {
/* 556 */       Slot $$6 = (Slot)this.menu.slots.get($$1);
/* 557 */       this.ghostRecipe.addIngredient($$5, $$6.x, $$6.y);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void sendUpdateSettings() {
/* 562 */     if (this.minecraft.getConnection() != null) {
/* 563 */       RecipeBookType $$0 = this.menu.getRecipeBookType();
/* 564 */       boolean $$1 = this.book.getBookSettings().isOpen($$0);
/* 565 */       boolean $$2 = this.book.getBookSettings().isFiltering($$0);
/* 566 */       this.minecraft.getConnection().send((Packet)new ServerboundRecipeBookChangeSettingsPacket($$0, $$1, $$2));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NarratableEntry.NarrationPriority narrationPriority() {
/* 574 */     return this.visible ? NarratableEntry.NarrationPriority.HOVERED : NarratableEntry.NarrationPriority.NONE;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateNarration(NarrationElementOutput $$0) {
/* 579 */     List<NarratableEntry> $$1 = Lists.newArrayList();
/* 580 */     this.recipeBookPage.listButtons($$1 -> {
/*     */           if ($$1.isActive()) {
/*     */             $$0.add($$1);
/*     */           }
/*     */         });
/* 585 */     $$1.add(this.searchBox);
/* 586 */     $$1.add(this.filterButton);
/* 587 */     $$1.addAll(this.tabButtons);
/*     */     
/* 589 */     Screen.NarratableSearchResult $$2 = Screen.findNarratableWidget($$1, null);
/* 590 */     if ($$2 != null)
/* 591 */       $$2.entry.updateNarration($$0.nest()); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\recipebook\RecipeBookComponent.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */