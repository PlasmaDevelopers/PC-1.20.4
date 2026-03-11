/*     */ package net.minecraft.client.gui.screens.recipebook;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import java.util.function.Consumer;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.AbstractWidget;
/*     */ import net.minecraft.client.gui.components.StateSwitchingButton;
/*     */ import net.minecraft.client.gui.components.WidgetSprites;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.FormattedText;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.stats.RecipeBook;
/*     */ import net.minecraft.world.item.crafting.RecipeHolder;
/*     */ 
/*     */ public class RecipeBookPage {
/*     */   public static final int ITEMS_PER_PAGE = 20;
/*  22 */   private static final WidgetSprites PAGE_FORWARD_SPRITES = new WidgetSprites(new ResourceLocation("recipe_book/page_forward"), new ResourceLocation("recipe_book/page_forward_highlighted"));
/*     */ 
/*     */ 
/*     */   
/*  26 */   private static final WidgetSprites PAGE_BACKWARD_SPRITES = new WidgetSprites(new ResourceLocation("recipe_book/page_backward"), new ResourceLocation("recipe_book/page_backward_highlighted"));
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  31 */   private final List<RecipeButton> buttons = Lists.newArrayListWithCapacity(20);
/*     */   
/*     */   @Nullable
/*     */   private RecipeButton hoveredButton;
/*  35 */   private final OverlayRecipeComponent overlay = new OverlayRecipeComponent();
/*     */   
/*     */   private Minecraft minecraft;
/*     */   
/*  39 */   private final List<RecipeShownListener> showListeners = Lists.newArrayList();
/*     */   
/*  41 */   private List<RecipeCollection> recipeCollections = (List<RecipeCollection>)ImmutableList.of();
/*     */   
/*     */   private StateSwitchingButton forwardButton;
/*     */   
/*     */   private StateSwitchingButton backButton;
/*     */   private int totalPages;
/*     */   private int currentPage;
/*     */   private RecipeBook recipeBook;
/*     */   @Nullable
/*     */   private RecipeHolder<?> lastClickedRecipe;
/*     */   @Nullable
/*     */   private RecipeCollection lastClickedRecipeCollection;
/*     */   
/*     */   public RecipeBookPage() {
/*  55 */     for (int $$0 = 0; $$0 < 20; $$0++) {
/*  56 */       this.buttons.add(new RecipeButton());
/*     */     }
/*     */   }
/*     */   
/*     */   public void init(Minecraft $$0, int $$1, int $$2) {
/*  61 */     this.minecraft = $$0;
/*  62 */     this.recipeBook = (RecipeBook)$$0.player.getRecipeBook();
/*     */     
/*  64 */     for (int $$3 = 0; $$3 < this.buttons.size(); $$3++) {
/*  65 */       ((RecipeButton)this.buttons.get($$3)).setPosition($$1 + 11 + 25 * $$3 % 5, $$2 + 31 + 25 * $$3 / 5);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  71 */     this.forwardButton = new StateSwitchingButton($$1 + 93, $$2 + 137, 12, 17, false);
/*  72 */     this.forwardButton.initTextureValues(PAGE_FORWARD_SPRITES);
/*  73 */     this.backButton = new StateSwitchingButton($$1 + 38, $$2 + 137, 12, 17, true);
/*  74 */     this.backButton.initTextureValues(PAGE_BACKWARD_SPRITES);
/*     */   }
/*     */   
/*     */   public void addListener(RecipeBookComponent $$0) {
/*  78 */     this.showListeners.remove($$0);
/*  79 */     this.showListeners.add($$0);
/*     */   }
/*     */   
/*     */   public void updateCollections(List<RecipeCollection> $$0, boolean $$1) {
/*  83 */     this.recipeCollections = $$0;
/*  84 */     this.totalPages = (int)Math.ceil($$0.size() / 20.0D);
/*     */     
/*  86 */     if (this.totalPages <= this.currentPage || $$1) {
/*  87 */       this.currentPage = 0;
/*     */     }
/*     */     
/*  90 */     updateButtonsForPage();
/*     */   }
/*     */   
/*     */   private void updateButtonsForPage() {
/*  94 */     int $$0 = 20 * this.currentPage;
/*  95 */     for (int $$1 = 0; $$1 < this.buttons.size(); $$1++) {
/*  96 */       RecipeButton $$2 = this.buttons.get($$1);
/*     */       
/*  98 */       if ($$0 + $$1 < this.recipeCollections.size()) {
/*  99 */         RecipeCollection $$3 = this.recipeCollections.get($$0 + $$1);
/*     */         
/* 101 */         $$2.init($$3, this);
/*     */         
/* 103 */         $$2.visible = true;
/*     */       } else {
/* 105 */         $$2.visible = false;
/*     */       } 
/*     */     } 
/*     */     
/* 109 */     updateArrowButtons();
/*     */   }
/*     */   
/*     */   private void updateArrowButtons() {
/* 113 */     this.forwardButton.visible = (this.totalPages > 1 && this.currentPage < this.totalPages - 1);
/* 114 */     this.backButton.visible = (this.totalPages > 1 && this.currentPage > 0);
/*     */   }
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, float $$5) {
/* 118 */     if (this.totalPages > 1) {
/* 119 */       MutableComponent mutableComponent = Component.translatable("gui.recipebook.page", new Object[] { Integer.valueOf(this.currentPage + 1), Integer.valueOf(this.totalPages) });
/* 120 */       int $$7 = this.minecraft.font.width((FormattedText)mutableComponent);
/* 121 */       $$0.drawString(this.minecraft.font, (Component)mutableComponent, $$1 - $$7 / 2 + 73, $$2 + 141, -1, false);
/*     */     } 
/*     */     
/* 124 */     this.hoveredButton = null;
/* 125 */     for (RecipeButton $$8 : this.buttons) {
/* 126 */       $$8.render($$0, $$3, $$4, $$5);
/* 127 */       if ($$8.visible && $$8.isHoveredOrFocused()) {
/* 128 */         this.hoveredButton = $$8;
/*     */       }
/*     */     } 
/*     */     
/* 132 */     this.backButton.render($$0, $$3, $$4, $$5);
/* 133 */     this.forwardButton.render($$0, $$3, $$4, $$5);
/*     */     
/* 135 */     this.overlay.render($$0, $$3, $$4, $$5);
/*     */   }
/*     */   
/*     */   public void renderTooltip(GuiGraphics $$0, int $$1, int $$2) {
/* 139 */     if (this.minecraft.screen != null && this.hoveredButton != null && !this.overlay.isVisible()) {
/* 140 */       $$0.renderComponentTooltip(this.minecraft.font, this.hoveredButton.getTooltipText(), $$1, $$2);
/*     */     }
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public RecipeHolder<?> getLastClickedRecipe() {
/* 146 */     return this.lastClickedRecipe;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public RecipeCollection getLastClickedRecipeCollection() {
/* 151 */     return this.lastClickedRecipeCollection;
/*     */   }
/*     */   
/*     */   public void setInvisible() {
/* 155 */     this.overlay.setVisible(false);
/*     */   }
/*     */   
/*     */   public boolean mouseClicked(double $$0, double $$1, int $$2, int $$3, int $$4, int $$5, int $$6) {
/* 159 */     this.lastClickedRecipe = null;
/* 160 */     this.lastClickedRecipeCollection = null;
/*     */     
/* 162 */     if (this.overlay.isVisible()) {
/* 163 */       if (this.overlay.mouseClicked($$0, $$1, $$2)) {
/* 164 */         this.lastClickedRecipe = this.overlay.getLastRecipeClicked();
/* 165 */         this.lastClickedRecipeCollection = this.overlay.getRecipeCollection();
/*     */       } else {
/* 167 */         this.overlay.setVisible(false);
/*     */       } 
/*     */       
/* 170 */       return true;
/*     */     } 
/*     */     
/* 173 */     if (this.forwardButton.mouseClicked($$0, $$1, $$2)) {
/* 174 */       this.currentPage++;
/* 175 */       updateButtonsForPage();
/* 176 */       return true;
/* 177 */     }  if (this.backButton.mouseClicked($$0, $$1, $$2)) {
/* 178 */       this.currentPage--;
/* 179 */       updateButtonsForPage();
/* 180 */       return true;
/*     */     } 
/*     */     
/* 183 */     for (RecipeButton $$7 : this.buttons) {
/* 184 */       if ($$7.mouseClicked($$0, $$1, $$2)) {
/* 185 */         if ($$2 == 0) {
/* 186 */           this.lastClickedRecipe = $$7.getRecipe();
/* 187 */           this.lastClickedRecipeCollection = $$7.getCollection();
/* 188 */         } else if ($$2 == 1 && 
/* 189 */           !this.overlay.isVisible() && !$$7.isOnlyOption()) {
/* 190 */           this.overlay.init(this.minecraft, $$7.getCollection(), $$7.getX(), $$7.getY(), $$3 + $$5 / 2, $$4 + 13 + $$6 / 2, $$7.getWidth());
/*     */         } 
/*     */         
/* 193 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 197 */     return false;
/*     */   }
/*     */   
/*     */   public void recipesShown(List<RecipeHolder<?>> $$0) {
/* 201 */     for (RecipeShownListener $$1 : this.showListeners) {
/* 202 */       $$1.recipesShown($$0);
/*     */     }
/*     */   }
/*     */   
/*     */   public Minecraft getMinecraft() {
/* 207 */     return this.minecraft;
/*     */   }
/*     */   
/*     */   public RecipeBook getRecipeBook() {
/* 211 */     return this.recipeBook;
/*     */   }
/*     */   
/*     */   protected void listButtons(Consumer<AbstractWidget> $$0) {
/* 215 */     $$0.accept(this.forwardButton);
/* 216 */     $$0.accept(this.backButton);
/* 217 */     this.buttons.forEach($$0);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\recipebook\RecipeBookPage.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */