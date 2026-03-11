/*     */ package net.minecraft.client.gui.screens.inventory;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.resources.sounds.SimpleSoundInstance;
/*     */ import net.minecraft.client.resources.sounds.SoundInstance;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.player.Inventory;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.inventory.StonecutterMenu;
/*     */ import net.minecraft.world.item.crafting.RecipeHolder;
/*     */ import net.minecraft.world.item.crafting.StonecutterRecipe;
/*     */ 
/*     */ public class StonecutterScreen extends AbstractContainerScreen<StonecutterMenu> {
/*  18 */   private static final ResourceLocation SCROLLER_SPRITE = new ResourceLocation("container/stonecutter/scroller");
/*  19 */   private static final ResourceLocation SCROLLER_DISABLED_SPRITE = new ResourceLocation("container/stonecutter/scroller_disabled");
/*  20 */   private static final ResourceLocation RECIPE_SELECTED_SPRITE = new ResourceLocation("container/stonecutter/recipe_selected");
/*  21 */   private static final ResourceLocation RECIPE_HIGHLIGHTED_SPRITE = new ResourceLocation("container/stonecutter/recipe_highlighted");
/*  22 */   private static final ResourceLocation RECIPE_SPRITE = new ResourceLocation("container/stonecutter/recipe");
/*  23 */   private static final ResourceLocation BG_LOCATION = new ResourceLocation("textures/gui/container/stonecutter.png");
/*     */   
/*     */   private static final int SCROLLER_WIDTH = 12;
/*     */   
/*     */   private static final int SCROLLER_HEIGHT = 15;
/*     */   
/*     */   private static final int RECIPES_COLUMNS = 4;
/*     */   private static final int RECIPES_ROWS = 3;
/*     */   private static final int RECIPES_IMAGE_SIZE_WIDTH = 16;
/*     */   private static final int RECIPES_IMAGE_SIZE_HEIGHT = 18;
/*     */   private static final int SCROLLER_FULL_HEIGHT = 54;
/*     */   private static final int RECIPES_X = 52;
/*     */   private static final int RECIPES_Y = 14;
/*     */   private float scrollOffs;
/*     */   private boolean scrolling;
/*     */   private int startIndex;
/*     */   private boolean displayRecipes;
/*     */   
/*     */   public StonecutterScreen(StonecutterMenu $$0, Inventory $$1, Component $$2) {
/*  42 */     super($$0, $$1, $$2);
/*  43 */     $$0.registerUpdateListener(this::containerChanged);
/*  44 */     this.titleLabelY--;
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/*  49 */     super.render($$0, $$1, $$2, $$3);
/*  50 */     renderTooltip($$0, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderBg(GuiGraphics $$0, float $$1, int $$2, int $$3) {
/*  55 */     int $$4 = this.leftPos;
/*  56 */     int $$5 = this.topPos;
/*  57 */     $$0.blit(BG_LOCATION, $$4, $$5, 0, 0, this.imageWidth, this.imageHeight);
/*     */     
/*  59 */     int $$6 = (int)(41.0F * this.scrollOffs);
/*  60 */     ResourceLocation $$7 = isScrollBarActive() ? SCROLLER_SPRITE : SCROLLER_DISABLED_SPRITE;
/*  61 */     $$0.blitSprite($$7, $$4 + 119, $$5 + 15 + $$6, 12, 15);
/*     */     
/*  63 */     int $$8 = this.leftPos + 52;
/*  64 */     int $$9 = this.topPos + 14;
/*     */     
/*  66 */     int $$10 = this.startIndex + 12;
/*     */     
/*  68 */     renderButtons($$0, $$2, $$3, $$8, $$9, $$10);
/*  69 */     renderRecipes($$0, $$8, $$9, $$10);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderTooltip(GuiGraphics $$0, int $$1, int $$2) {
/*  74 */     super.renderTooltip($$0, $$1, $$2);
/*     */     
/*  76 */     if (this.displayRecipes) {
/*  77 */       int $$3 = this.leftPos + 52;
/*  78 */       int $$4 = this.topPos + 14;
/*     */       
/*  80 */       int $$5 = this.startIndex + 12;
/*  81 */       List<RecipeHolder<StonecutterRecipe>> $$6 = this.menu.getRecipes();
/*  82 */       for (int $$7 = this.startIndex; $$7 < $$5 && $$7 < this.menu.getNumRecipes(); $$7++) {
/*  83 */         int $$8 = $$7 - this.startIndex;
/*     */         
/*  85 */         int $$9 = $$3 + $$8 % 4 * 16;
/*  86 */         int $$10 = $$4 + $$8 / 4 * 18 + 2;
/*  87 */         if ($$1 >= $$9 && $$1 < $$9 + 16 && $$2 >= $$10 && $$2 < $$10 + 18) {
/*  88 */           $$0.renderTooltip(this.font, ((StonecutterRecipe)((RecipeHolder)$$6.get($$7)).value()).getResultItem(this.minecraft.level.registryAccess()), $$1, $$2);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void renderButtons(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5) {
/*  95 */     for (int $$6 = this.startIndex; $$6 < $$5 && $$6 < this.menu.getNumRecipes(); $$6++) {
/*  96 */       ResourceLocation $$13; int $$7 = $$6 - this.startIndex;
/*  97 */       int $$8 = $$3 + $$7 % 4 * 16;
/*  98 */       int $$9 = $$7 / 4;
/*  99 */       int $$10 = $$4 + $$9 * 18 + 2;
/*     */ 
/*     */       
/* 102 */       if ($$6 == this.menu.getSelectedRecipeIndex()) {
/* 103 */         ResourceLocation $$11 = RECIPE_SELECTED_SPRITE;
/* 104 */       } else if ($$1 >= $$8 && $$2 >= $$10 && $$1 < $$8 + 16 && $$2 < $$10 + 18) {
/* 105 */         ResourceLocation $$12 = RECIPE_HIGHLIGHTED_SPRITE;
/*     */       } else {
/* 107 */         $$13 = RECIPE_SPRITE;
/*     */       } 
/*     */       
/* 110 */       $$0.blitSprite($$13, $$8, $$10 - 1, 16, 18);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void renderRecipes(GuiGraphics $$0, int $$1, int $$2, int $$3) {
/* 115 */     List<RecipeHolder<StonecutterRecipe>> $$4 = this.menu.getRecipes();
/* 116 */     for (int $$5 = this.startIndex; $$5 < $$3 && $$5 < this.menu.getNumRecipes(); $$5++) {
/* 117 */       int $$6 = $$5 - this.startIndex;
/* 118 */       int $$7 = $$1 + $$6 % 4 * 16;
/* 119 */       int $$8 = $$6 / 4;
/* 120 */       int $$9 = $$2 + $$8 * 18 + 2;
/*     */       
/* 122 */       $$0.renderItem(((StonecutterRecipe)((RecipeHolder)$$4.get($$5)).value()).getResultItem(this.minecraft.level.registryAccess()), $$7, $$9);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(double $$0, double $$1, int $$2) {
/* 128 */     this.scrolling = false;
/* 129 */     if (this.displayRecipes) {
/* 130 */       int $$3 = this.leftPos + 52;
/* 131 */       int $$4 = this.topPos + 14;
/*     */       
/* 133 */       int $$5 = this.startIndex + 12;
/* 134 */       for (int $$6 = this.startIndex; $$6 < $$5; $$6++) {
/* 135 */         int $$7 = $$6 - this.startIndex;
/* 136 */         double $$8 = $$0 - ($$3 + $$7 % 4 * 16);
/* 137 */         double $$9 = $$1 - ($$4 + $$7 / 4 * 18);
/* 138 */         if ($$8 >= 0.0D && $$9 >= 0.0D && $$8 < 16.0D && $$9 < 18.0D && this.menu.clickMenuButton((Player)this.minecraft.player, $$6)) {
/* 139 */           Minecraft.getInstance().getSoundManager().play((SoundInstance)SimpleSoundInstance.forUI(SoundEvents.UI_STONECUTTER_SELECT_RECIPE, 1.0F));
/* 140 */           this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, $$6);
/* 141 */           return true;
/*     */         } 
/*     */       } 
/*     */       
/* 145 */       $$3 = this.leftPos + 119;
/* 146 */       $$4 = this.topPos + 9;
/* 147 */       if ($$0 >= $$3 && $$0 < ($$3 + 12) && $$1 >= $$4 && $$1 < ($$4 + 54)) {
/* 148 */         this.scrolling = true;
/*     */       }
/*     */     } 
/*     */     
/* 152 */     return super.mouseClicked($$0, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseDragged(double $$0, double $$1, int $$2, double $$3, double $$4) {
/* 157 */     if (this.scrolling && isScrollBarActive()) {
/* 158 */       int $$5 = this.topPos + 14;
/* 159 */       int $$6 = $$5 + 54;
/*     */       
/* 161 */       this.scrollOffs = ((float)$$1 - $$5 - 7.5F) / (($$6 - $$5) - 15.0F);
/* 162 */       this.scrollOffs = Mth.clamp(this.scrollOffs, 0.0F, 1.0F);
/*     */       
/* 164 */       this.startIndex = (int)((this.scrollOffs * getOffscreenRows()) + 0.5D) * 4;
/*     */       
/* 166 */       return true;
/*     */     } 
/* 168 */     return super.mouseDragged($$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseScrolled(double $$0, double $$1, double $$2, double $$3) {
/* 173 */     if (isScrollBarActive()) {
/* 174 */       int $$4 = getOffscreenRows();
/* 175 */       float $$5 = (float)$$3 / $$4;
/* 176 */       this.scrollOffs = Mth.clamp(this.scrollOffs - $$5, 0.0F, 1.0F);
/* 177 */       this.startIndex = (int)((this.scrollOffs * $$4) + 0.5D) * 4;
/*     */     } 
/* 179 */     return true;
/*     */   }
/*     */   
/*     */   private boolean isScrollBarActive() {
/* 183 */     return (this.displayRecipes && this.menu.getNumRecipes() > 12);
/*     */   }
/*     */   
/*     */   protected int getOffscreenRows() {
/* 187 */     return (this.menu.getNumRecipes() + 4 - 1) / 4 - 3;
/*     */   }
/*     */   
/*     */   private void containerChanged() {
/* 191 */     this.displayRecipes = this.menu.hasInputItem();
/* 192 */     if (!this.displayRecipes) {
/* 193 */       this.scrollOffs = 0.0F;
/* 194 */       this.startIndex = 0;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\inventory\StonecutterScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */