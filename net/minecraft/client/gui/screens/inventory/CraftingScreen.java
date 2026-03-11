/*     */ package net.minecraft.client.gui.screens.inventory;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.gui.components.ImageButton;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.entity.player.Inventory;
/*     */ import net.minecraft.world.inventory.ClickType;
/*     */ import net.minecraft.world.inventory.CraftingMenu;
/*     */ import net.minecraft.world.inventory.Slot;
/*     */ 
/*     */ public class CraftingScreen extends AbstractContainerScreen<CraftingMenu> implements RecipeUpdateListener {
/*  15 */   private static final ResourceLocation CRAFTING_TABLE_LOCATION = new ResourceLocation("textures/gui/container/crafting_table.png");
/*     */   
/*  17 */   private final RecipeBookComponent recipeBookComponent = new RecipeBookComponent();
/*     */   
/*     */   private boolean widthTooNarrow;
/*     */   
/*     */   public CraftingScreen(CraftingMenu $$0, Inventory $$1, Component $$2) {
/*  22 */     super($$0, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void init() {
/*  27 */     super.init();
/*     */     
/*  29 */     this.widthTooNarrow = (this.width < 379);
/*  30 */     this.recipeBookComponent.init(this.width, this.height, this.minecraft, this.widthTooNarrow, (RecipeBookMenu)this.menu);
/*  31 */     this.leftPos = this.recipeBookComponent.updateScreenPosition(this.width, this.imageWidth);
/*     */     
/*  33 */     addRenderableWidget((GuiEventListener)new ImageButton(this.leftPos + 5, this.height / 2 - 49, 20, 18, RecipeBookComponent.RECIPE_BUTTON_SPRITES, $$0 -> {
/*     */             this.recipeBookComponent.toggleVisibility();
/*     */             
/*     */             this.leftPos = this.recipeBookComponent.updateScreenPosition(this.width, this.imageWidth);
/*     */             $$0.setPosition(this.leftPos + 5, this.height / 2 - 49);
/*     */           }));
/*  39 */     addWidget((GuiEventListener)this.recipeBookComponent);
/*  40 */     setInitialFocus((GuiEventListener)this.recipeBookComponent);
/*     */     
/*  42 */     this.titleLabelX = 29;
/*     */   }
/*     */ 
/*     */   
/*     */   public void containerTick() {
/*  47 */     super.containerTick();
/*     */     
/*  49 */     this.recipeBookComponent.tick();
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/*  54 */     if (this.recipeBookComponent.isVisible() && this.widthTooNarrow) {
/*  55 */       renderBackground($$0, $$1, $$2, $$3);
/*  56 */       this.recipeBookComponent.render($$0, $$1, $$2, $$3);
/*     */     } else {
/*  58 */       super.render($$0, $$1, $$2, $$3);
/*  59 */       this.recipeBookComponent.render($$0, $$1, $$2, $$3);
/*  60 */       this.recipeBookComponent.renderGhostRecipe($$0, this.leftPos, this.topPos, true, $$3);
/*     */     } 
/*     */     
/*  63 */     renderTooltip($$0, $$1, $$2);
/*  64 */     this.recipeBookComponent.renderTooltip($$0, this.leftPos, this.topPos, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderBg(GuiGraphics $$0, float $$1, int $$2, int $$3) {
/*  69 */     int $$4 = this.leftPos;
/*  70 */     int $$5 = (this.height - this.imageHeight) / 2;
/*  71 */     $$0.blit(CRAFTING_TABLE_LOCATION, $$4, $$5, 0, 0, this.imageWidth, this.imageHeight);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isHovering(int $$0, int $$1, int $$2, int $$3, double $$4, double $$5) {
/*  76 */     return ((!this.widthTooNarrow || !this.recipeBookComponent.isVisible()) && super.isHovering($$0, $$1, $$2, $$3, $$4, $$5));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(double $$0, double $$1, int $$2) {
/*  81 */     if (this.recipeBookComponent.mouseClicked($$0, $$1, $$2)) {
/*  82 */       setFocused((GuiEventListener)this.recipeBookComponent);
/*  83 */       return true;
/*     */     } 
/*     */     
/*  86 */     if (this.widthTooNarrow && this.recipeBookComponent.isVisible()) {
/*  87 */       return true;
/*     */     }
/*     */     
/*  90 */     return super.mouseClicked($$0, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean hasClickedOutside(double $$0, double $$1, int $$2, int $$3, int $$4) {
/*  95 */     boolean $$5 = ($$0 < $$2 || $$1 < $$3 || $$0 >= ($$2 + this.imageWidth) || $$1 >= ($$3 + this.imageHeight));
/*  96 */     return (this.recipeBookComponent.hasClickedOutside($$0, $$1, this.leftPos, this.topPos, this.imageWidth, this.imageHeight, $$4) && $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void slotClicked(Slot $$0, int $$1, int $$2, ClickType $$3) {
/* 101 */     super.slotClicked($$0, $$1, $$2, $$3);
/*     */     
/* 103 */     this.recipeBookComponent.slotClicked($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void recipesUpdated() {
/* 108 */     this.recipeBookComponent.recipesUpdated();
/*     */   }
/*     */ 
/*     */   
/*     */   public RecipeBookComponent getRecipeBookComponent() {
/* 113 */     return this.recipeBookComponent;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\inventory\CraftingScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */