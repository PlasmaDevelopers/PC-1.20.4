/*     */ package net.minecraft.client.gui.screens.recipebook;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.AbstractWidget;
/*     */ import net.minecraft.client.gui.components.Renderable;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.client.gui.narration.NarrationElementOutput;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.recipebook.PlaceRecipe;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.inventory.RecipeBookMenu;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.crafting.Ingredient;
/*     */ import net.minecraft.world.item.crafting.RecipeHolder;
/*     */ 
/*     */ public class OverlayRecipeComponent
/*     */   implements Renderable, GuiEventListener
/*     */ {
/*  27 */   private static final ResourceLocation OVERLAY_RECIPE_SPRITE = new ResourceLocation("recipe_book/overlay_recipe");
/*  28 */   static final ResourceLocation FURNACE_OVERLAY_HIGHLIGHTED_SPRITE = new ResourceLocation("recipe_book/furnace_overlay_highlighted");
/*  29 */   static final ResourceLocation FURNACE_OVERLAY_SPRITE = new ResourceLocation("recipe_book/furnace_overlay");
/*  30 */   static final ResourceLocation CRAFTING_OVERLAY_HIGHLIGHTED_SPRITE = new ResourceLocation("recipe_book/crafting_overlay_highlighted");
/*  31 */   static final ResourceLocation CRAFTING_OVERLAY_SPRITE = new ResourceLocation("recipe_book/crafting_overlay");
/*  32 */   static final ResourceLocation FURNACE_OVERLAY_DISABLED_HIGHLIGHTED_SPRITE = new ResourceLocation("recipe_book/furnace_overlay_disabled_highlighted");
/*  33 */   static final ResourceLocation FURNACE_OVERLAY_DISABLED_SPRITE = new ResourceLocation("recipe_book/furnace_overlay_disabled");
/*  34 */   static final ResourceLocation CRAFTING_OVERLAY_DISABLED_HIGHLIGHTED_SPRITE = new ResourceLocation("recipe_book/crafting_overlay_disabled_highlighted");
/*  35 */   static final ResourceLocation CRAFTING_OVERLAY_DISABLED_SPRITE = new ResourceLocation("recipe_book/crafting_overlay_disabled");
/*     */   
/*     */   private static final int MAX_ROW = 4;
/*     */   private static final int MAX_ROW_LARGE = 5;
/*     */   private static final float ITEM_RENDER_SCALE = 0.375F;
/*     */   public static final int BUTTON_SIZE = 25;
/*  41 */   private final List<OverlayRecipeButton> recipeButtons = Lists.newArrayList();
/*     */   
/*     */   private boolean isVisible;
/*     */   
/*     */   private int x;
/*     */   
/*     */   private int y;
/*     */   
/*     */   private Minecraft minecraft;
/*     */   private RecipeCollection collection;
/*     */   @Nullable
/*     */   private RecipeHolder<?> lastRecipeClicked;
/*     */   float time;
/*     */   boolean isFurnaceMenu;
/*     */   
/*     */   public void init(Minecraft $$0, RecipeCollection $$1, int $$2, int $$3, int $$4, int $$5, float $$6) {
/*  57 */     this.minecraft = $$0;
/*  58 */     this.collection = $$1;
/*     */     
/*  60 */     if ($$0.player.containerMenu instanceof net.minecraft.world.inventory.AbstractFurnaceMenu) {
/*  61 */       this.isFurnaceMenu = true;
/*     */     }
/*     */     
/*  64 */     boolean $$7 = $$0.player.getRecipeBook().isFiltering((RecipeBookMenu)$$0.player.containerMenu);
/*     */     
/*  66 */     List<RecipeHolder<?>> $$8 = $$1.getDisplayRecipes(true);
/*  67 */     List<RecipeHolder<?>> $$9 = $$7 ? Collections.<RecipeHolder<?>>emptyList() : $$1.getDisplayRecipes(false);
/*     */     
/*  69 */     int $$10 = $$8.size();
/*  70 */     int $$11 = $$10 + $$9.size();
/*     */     
/*  72 */     int $$12 = ($$11 <= 16) ? 4 : 5;
/*  73 */     int $$13 = (int)Math.ceil(($$11 / $$12));
/*  74 */     this.x = $$2;
/*  75 */     this.y = $$3;
/*     */ 
/*     */     
/*  78 */     float $$14 = (this.x + Math.min($$11, $$12) * 25);
/*  79 */     float $$15 = ($$4 + 50);
/*  80 */     if ($$14 > $$15) {
/*  81 */       this.x = (int)(this.x - $$6 * (int)(($$14 - $$15) / $$6));
/*     */     }
/*     */     
/*  84 */     float $$16 = (this.y + $$13 * 25);
/*  85 */     float $$17 = ($$5 + 50);
/*  86 */     if ($$16 > $$17) {
/*  87 */       this.y = (int)(this.y - $$6 * Mth.ceil(($$16 - $$17) / $$6));
/*     */     }
/*     */     
/*  90 */     float $$18 = this.y;
/*  91 */     float $$19 = ($$5 - 100);
/*  92 */     if ($$18 < $$19) {
/*  93 */       this.y = (int)(this.y - $$6 * Mth.ceil(($$18 - $$19) / $$6));
/*     */     }
/*     */     
/*  96 */     this.isVisible = true;
/*     */     
/*  98 */     this.recipeButtons.clear();
/*  99 */     for (int $$20 = 0; $$20 < $$11; $$20++) {
/* 100 */       boolean $$21 = ($$20 < $$10);
/* 101 */       RecipeHolder<?> $$22 = $$21 ? $$8.get($$20) : $$9.get($$20 - $$10);
/*     */       
/* 103 */       int $$23 = this.x + 4 + 25 * $$20 % $$12;
/* 104 */       int $$24 = this.y + 5 + 25 * $$20 / $$12;
/* 105 */       if (this.isFurnaceMenu) {
/* 106 */         this.recipeButtons.add(new OverlaySmeltingRecipeButton($$23, $$24, $$22, $$21));
/*     */       } else {
/* 108 */         this.recipeButtons.add(new OverlayRecipeButton($$23, $$24, $$22, $$21));
/*     */       } 
/*     */     } 
/*     */     
/* 112 */     this.lastRecipeClicked = null;
/*     */   }
/*     */   
/*     */   public RecipeCollection getRecipeCollection() {
/* 116 */     return this.collection;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public RecipeHolder<?> getLastRecipeClicked() {
/* 121 */     return this.lastRecipeClicked;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(double $$0, double $$1, int $$2) {
/* 126 */     if ($$2 != 0) {
/* 127 */       return false;
/*     */     }
/*     */     
/* 130 */     for (OverlayRecipeButton $$3 : this.recipeButtons) {
/* 131 */       if ($$3.mouseClicked($$0, $$1, $$2)) {
/* 132 */         this.lastRecipeClicked = $$3.recipe;
/* 133 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 137 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isMouseOver(double $$0, double $$1) {
/* 142 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 147 */     if (!this.isVisible) {
/*     */       return;
/*     */     }
/*     */     
/* 151 */     this.time += $$3;
/*     */     
/* 153 */     RenderSystem.enableBlend();
/*     */     
/* 155 */     $$0.pose().pushPose();
/* 156 */     $$0.pose().translate(0.0F, 0.0F, 1000.0F);
/*     */     
/* 158 */     int $$4 = (this.recipeButtons.size() <= 16) ? 4 : 5;
/* 159 */     int $$5 = Math.min(this.recipeButtons.size(), $$4);
/* 160 */     int $$6 = Mth.ceil(this.recipeButtons.size() / $$4);
/*     */     
/* 162 */     int $$7 = 4;
/* 163 */     $$0.blitSprite(OVERLAY_RECIPE_SPRITE, this.x, this.y, $$5 * 25 + 8, $$6 * 25 + 8);
/*     */     
/* 165 */     RenderSystem.disableBlend();
/*     */     
/* 167 */     for (OverlayRecipeButton $$8 : this.recipeButtons) {
/* 168 */       $$8.render($$0, $$1, $$2, $$3);
/*     */     }
/*     */     
/* 171 */     $$0.pose().popPose();
/*     */   }
/*     */   
/*     */   public void setVisible(boolean $$0) {
/* 175 */     this.isVisible = $$0;
/*     */   }
/*     */   
/*     */   public boolean isVisible() {
/* 179 */     return this.isVisible;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFocused(boolean $$0) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFocused() {
/* 189 */     return false;
/*     */   }
/*     */   
/*     */   private class OverlaySmeltingRecipeButton extends OverlayRecipeButton {
/*     */     public OverlaySmeltingRecipeButton(int $$0, int $$1, RecipeHolder<?> $$2, boolean $$3) {
/* 194 */       super($$0, $$1, $$2, $$3);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void calculateIngredientsPositions(RecipeHolder<?> $$0) {
/* 199 */       Ingredient $$1 = (Ingredient)$$0.value().getIngredients().get(0);
/* 200 */       ItemStack[] $$2 = $$1.getItems();
/* 201 */       this.ingredientPos.add(new OverlayRecipeComponent.OverlayRecipeButton.Pos(10, 10, $$2));
/*     */     }
/*     */   }
/*     */   
/*     */   private class OverlayRecipeButton extends AbstractWidget implements PlaceRecipe<Ingredient> {
/*     */     final RecipeHolder<?> recipe;
/*     */     private final boolean isCraftable;
/* 208 */     protected final List<Pos> ingredientPos = Lists.newArrayList();
/*     */     
/*     */     public OverlayRecipeButton(int $$0, int $$1, RecipeHolder<?> $$2, boolean $$3) {
/* 211 */       super($$0, $$1, 200, 20, CommonComponents.EMPTY);
/* 212 */       this.width = 24;
/* 213 */       this.height = 24;
/* 214 */       this.recipe = $$2;
/* 215 */       this.isCraftable = $$3;
/*     */       
/* 217 */       calculateIngredientsPositions($$2);
/*     */     }
/*     */     
/*     */     protected void calculateIngredientsPositions(RecipeHolder<?> $$0) {
/* 221 */       placeRecipe(3, 3, -1, $$0, $$0.value().getIngredients().iterator(), 0);
/*     */     }
/*     */ 
/*     */     
/*     */     public void updateWidgetNarration(NarrationElementOutput $$0) {
/* 226 */       defaultButtonNarrationText($$0);
/*     */     }
/*     */ 
/*     */     
/*     */     public void addItemToSlot(Iterator<Ingredient> $$0, int $$1, int $$2, int $$3, int $$4) {
/* 231 */       ItemStack[] $$5 = ((Ingredient)$$0.next()).getItems();
/* 232 */       if ($$5.length != 0) {
/* 233 */         this.ingredientPos.add(new Pos(3 + $$4 * 7, 3 + $$3 * 7, $$5));
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public void renderWidget(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/*     */       ResourceLocation $$7;
/* 240 */       if (this.isCraftable) {
/* 241 */         if (OverlayRecipeComponent.this.isFurnaceMenu) {
/* 242 */           ResourceLocation $$4 = isHoveredOrFocused() ? OverlayRecipeComponent.FURNACE_OVERLAY_HIGHLIGHTED_SPRITE : OverlayRecipeComponent.FURNACE_OVERLAY_SPRITE;
/*     */         } else {
/* 244 */           ResourceLocation $$5 = isHoveredOrFocused() ? OverlayRecipeComponent.CRAFTING_OVERLAY_HIGHLIGHTED_SPRITE : OverlayRecipeComponent.CRAFTING_OVERLAY_SPRITE;
/*     */         }
/*     */       
/* 247 */       } else if (OverlayRecipeComponent.this.isFurnaceMenu) {
/* 248 */         ResourceLocation $$6 = isHoveredOrFocused() ? OverlayRecipeComponent.FURNACE_OVERLAY_DISABLED_HIGHLIGHTED_SPRITE : OverlayRecipeComponent.FURNACE_OVERLAY_DISABLED_SPRITE;
/*     */       } else {
/* 250 */         $$7 = isHoveredOrFocused() ? OverlayRecipeComponent.CRAFTING_OVERLAY_DISABLED_HIGHLIGHTED_SPRITE : OverlayRecipeComponent.CRAFTING_OVERLAY_DISABLED_SPRITE;
/*     */       } 
/*     */       
/* 253 */       $$0.blitSprite($$7, getX(), getY(), this.width, this.height);
/*     */       
/* 255 */       $$0.pose().pushPose();
/* 256 */       $$0.pose().translate((getX() + 2), (getY() + 2), 150.0D);
/*     */       
/* 258 */       for (Pos $$8 : this.ingredientPos) {
/* 259 */         $$0.pose().pushPose();
/*     */         
/* 261 */         $$0.pose().translate($$8.x, $$8.y, 0.0D);
/* 262 */         $$0.pose().scale(0.375F, 0.375F, 1.0F);
/* 263 */         $$0.pose().translate(-8.0D, -8.0D, 0.0D);
/*     */         
/* 265 */         if ($$8.ingredients.length > 0) {
/* 266 */           $$0.renderItem($$8.ingredients[Mth.floor(OverlayRecipeComponent.this.time / 30.0F) % $$8.ingredients.length], 0, 0);
/*     */         }
/*     */         
/* 269 */         $$0.pose().popPose();
/*     */       } 
/* 271 */       $$0.pose().popPose();
/*     */     }
/*     */     
/*     */     protected class Pos
/*     */     {
/*     */       public final ItemStack[] ingredients;
/*     */       public final int x;
/*     */       public final int y;
/*     */       
/* 280 */       public Pos(int $$1, int $$2, ItemStack[] $$3) { this.x = $$1;
/* 281 */         this.y = $$2;
/* 282 */         this.ingredients = $$3; } } } protected class Pos { public Pos(int $$1, int $$2, ItemStack[] $$3) { this.x = $$1; this.y = $$2; this.ingredients = $$3; }
/*     */ 
/*     */     
/*     */     public final ItemStack[] ingredients;
/*     */     public final int x;
/*     */     public final int y; }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\recipebook\OverlayRecipeComponent.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */