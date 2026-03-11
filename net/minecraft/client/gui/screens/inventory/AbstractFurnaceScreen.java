/*     */ package net.minecraft.client.gui.screens.inventory;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.gui.components.ImageButton;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.client.gui.screens.recipebook.AbstractFurnaceRecipeBookComponent;
/*     */ import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
/*     */ import net.minecraft.client.gui.screens.recipebook.RecipeUpdateListener;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.FormattedText;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.player.Inventory;
/*     */ import net.minecraft.world.inventory.AbstractFurnaceMenu;
/*     */ import net.minecraft.world.inventory.ClickType;
/*     */ import net.minecraft.world.inventory.RecipeBookMenu;
/*     */ import net.minecraft.world.inventory.Slot;
/*     */ 
/*     */ public abstract class AbstractFurnaceScreen<T extends AbstractFurnaceMenu> extends AbstractContainerScreen<T> implements RecipeUpdateListener {
/*     */   public final AbstractFurnaceRecipeBookComponent recipeBookComponent;
/*     */   private boolean widthTooNarrow;
/*     */   
/*     */   public AbstractFurnaceScreen(T $$0, AbstractFurnaceRecipeBookComponent $$1, Inventory $$2, Component $$3, ResourceLocation $$4, ResourceLocation $$5, ResourceLocation $$6) {
/*  24 */     super($$0, $$2, $$3);
/*  25 */     this.recipeBookComponent = $$1;
/*     */     
/*  27 */     this.texture = $$4;
/*  28 */     this.litProgressSprite = $$5;
/*  29 */     this.burnProgressSprite = $$6;
/*     */   }
/*     */   private final ResourceLocation texture; private final ResourceLocation litProgressSprite; private final ResourceLocation burnProgressSprite;
/*     */   
/*     */   public void init() {
/*  34 */     super.init();
/*     */     
/*  36 */     this.widthTooNarrow = (this.width < 379);
/*  37 */     this.recipeBookComponent.init(this.width, this.height, this.minecraft, this.widthTooNarrow, (RecipeBookMenu)this.menu);
/*  38 */     this.leftPos = this.recipeBookComponent.updateScreenPosition(this.width, this.imageWidth);
/*     */     
/*  40 */     addRenderableWidget((GuiEventListener)new ImageButton(this.leftPos + 20, this.height / 2 - 49, 20, 18, RecipeBookComponent.RECIPE_BUTTON_SPRITES, $$0 -> {
/*     */             this.recipeBookComponent.toggleVisibility();
/*     */             
/*     */             this.leftPos = this.recipeBookComponent.updateScreenPosition(this.width, this.imageWidth);
/*     */             
/*     */             $$0.setPosition(this.leftPos + 20, this.height / 2 - 49);
/*     */           }));
/*  47 */     this.titleLabelX = (this.imageWidth - this.font.width((FormattedText)this.title)) / 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void containerTick() {
/*  52 */     super.containerTick();
/*     */     
/*  54 */     this.recipeBookComponent.tick();
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/*  59 */     if (this.recipeBookComponent.isVisible() && this.widthTooNarrow) {
/*  60 */       renderBackground($$0, $$1, $$2, $$3);
/*  61 */       this.recipeBookComponent.render($$0, $$1, $$2, $$3);
/*     */     } else {
/*  63 */       super.render($$0, $$1, $$2, $$3);
/*  64 */       this.recipeBookComponent.render($$0, $$1, $$2, $$3);
/*  65 */       this.recipeBookComponent.renderGhostRecipe($$0, this.leftPos, this.topPos, true, $$3);
/*     */     } 
/*     */     
/*  68 */     renderTooltip($$0, $$1, $$2);
/*  69 */     this.recipeBookComponent.renderTooltip($$0, this.leftPos, this.topPos, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderBg(GuiGraphics $$0, float $$1, int $$2, int $$3) {
/*  74 */     int $$4 = this.leftPos;
/*  75 */     int $$5 = this.topPos;
/*  76 */     $$0.blit(this.texture, $$4, $$5, 0, 0, this.imageWidth, this.imageHeight);
/*  77 */     if (((AbstractFurnaceMenu)this.menu).isLit()) {
/*  78 */       int $$6 = 14;
/*  79 */       int $$7 = Mth.ceil(((AbstractFurnaceMenu)this.menu).getLitProgress() * 13.0F) + 1;
/*  80 */       $$0.blitSprite(this.litProgressSprite, 14, 14, 0, 14 - $$7, $$4 + 56, $$5 + 36 + 14 - $$7, 14, $$7);
/*     */     } 
/*     */     
/*  83 */     int $$8 = 24;
/*  84 */     int $$9 = Mth.ceil(((AbstractFurnaceMenu)this.menu).getBurnProgress() * 24.0F);
/*  85 */     $$0.blitSprite(this.burnProgressSprite, 24, 16, 0, 0, $$4 + 79, $$5 + 34, $$9, 16);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(double $$0, double $$1, int $$2) {
/*  90 */     if (this.recipeBookComponent.mouseClicked($$0, $$1, $$2)) {
/*  91 */       return true;
/*     */     }
/*     */     
/*  94 */     if (this.widthTooNarrow && this.recipeBookComponent.isVisible()) {
/*  95 */       return true;
/*     */     }
/*     */     
/*  98 */     return super.mouseClicked($$0, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void slotClicked(Slot $$0, int $$1, int $$2, ClickType $$3) {
/* 103 */     super.slotClicked($$0, $$1, $$2, $$3);
/*     */     
/* 105 */     this.recipeBookComponent.slotClicked($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean keyPressed(int $$0, int $$1, int $$2) {
/* 110 */     if (this.recipeBookComponent.keyPressed($$0, $$1, $$2)) {
/* 111 */       return false;
/*     */     }
/* 113 */     return super.keyPressed($$0, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean hasClickedOutside(double $$0, double $$1, int $$2, int $$3, int $$4) {
/* 118 */     boolean $$5 = ($$0 < $$2 || $$1 < $$3 || $$0 >= ($$2 + this.imageWidth) || $$1 >= ($$3 + this.imageHeight));
/* 119 */     return (this.recipeBookComponent.hasClickedOutside($$0, $$1, this.leftPos, this.topPos, this.imageWidth, this.imageHeight, $$4) && $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean charTyped(char $$0, int $$1) {
/* 124 */     if (this.recipeBookComponent.charTyped($$0, $$1)) {
/* 125 */       return true;
/*     */     }
/* 127 */     return super.charTyped($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void recipesUpdated() {
/* 132 */     this.recipeBookComponent.recipesUpdated();
/*     */   }
/*     */ 
/*     */   
/*     */   public RecipeBookComponent getRecipeBookComponent() {
/* 137 */     return (RecipeBookComponent)this.recipeBookComponent;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\inventory\AbstractFurnaceScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */