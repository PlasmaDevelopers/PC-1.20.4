/*     */ package net.minecraft.client.gui.screens.recipebook;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.AbstractWidget;
/*     */ import net.minecraft.client.gui.narration.NarrationElementOutput;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.recipebook.PlaceRecipe;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.crafting.Ingredient;
/*     */ import net.minecraft.world.item.crafting.RecipeHolder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class OverlayRecipeButton
/*     */   extends AbstractWidget
/*     */   implements PlaceRecipe<Ingredient>
/*     */ {
/*     */   final RecipeHolder<?> recipe;
/*     */   private final boolean isCraftable;
/* 208 */   protected final List<Pos> ingredientPos = Lists.newArrayList();
/*     */   
/*     */   public OverlayRecipeButton(int $$0, int $$1, RecipeHolder<?> $$2, boolean $$3) {
/* 211 */     super($$0, $$1, 200, 20, CommonComponents.EMPTY);
/* 212 */     this.width = 24;
/* 213 */     this.height = 24;
/* 214 */     this.recipe = $$2;
/* 215 */     this.isCraftable = $$3;
/*     */     
/* 217 */     calculateIngredientsPositions($$2);
/*     */   }
/*     */   
/*     */   protected void calculateIngredientsPositions(RecipeHolder<?> $$0) {
/* 221 */     placeRecipe(3, 3, -1, $$0, $$0.value().getIngredients().iterator(), 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateWidgetNarration(NarrationElementOutput $$0) {
/* 226 */     defaultButtonNarrationText($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addItemToSlot(Iterator<Ingredient> $$0, int $$1, int $$2, int $$3, int $$4) {
/* 231 */     ItemStack[] $$5 = ((Ingredient)$$0.next()).getItems();
/* 232 */     if ($$5.length != 0) {
/* 233 */       this.ingredientPos.add(new Pos(3 + $$4 * 7, 3 + $$3 * 7, $$5));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderWidget(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/*     */     ResourceLocation $$7;
/* 240 */     if (this.isCraftable) {
/* 241 */       if (OverlayRecipeComponent.this.isFurnaceMenu) {
/* 242 */         ResourceLocation $$4 = isHoveredOrFocused() ? OverlayRecipeComponent.FURNACE_OVERLAY_HIGHLIGHTED_SPRITE : OverlayRecipeComponent.FURNACE_OVERLAY_SPRITE;
/*     */       } else {
/* 244 */         ResourceLocation $$5 = isHoveredOrFocused() ? OverlayRecipeComponent.CRAFTING_OVERLAY_HIGHLIGHTED_SPRITE : OverlayRecipeComponent.CRAFTING_OVERLAY_SPRITE;
/*     */       }
/*     */     
/* 247 */     } else if (OverlayRecipeComponent.this.isFurnaceMenu) {
/* 248 */       ResourceLocation $$6 = isHoveredOrFocused() ? OverlayRecipeComponent.FURNACE_OVERLAY_DISABLED_HIGHLIGHTED_SPRITE : OverlayRecipeComponent.FURNACE_OVERLAY_DISABLED_SPRITE;
/*     */     } else {
/* 250 */       $$7 = isHoveredOrFocused() ? OverlayRecipeComponent.CRAFTING_OVERLAY_DISABLED_HIGHLIGHTED_SPRITE : OverlayRecipeComponent.CRAFTING_OVERLAY_DISABLED_SPRITE;
/*     */     } 
/*     */     
/* 253 */     $$0.blitSprite($$7, getX(), getY(), this.width, this.height);
/*     */     
/* 255 */     $$0.pose().pushPose();
/* 256 */     $$0.pose().translate((getX() + 2), (getY() + 2), 150.0D);
/*     */     
/* 258 */     for (Pos $$8 : this.ingredientPos) {
/* 259 */       $$0.pose().pushPose();
/*     */       
/* 261 */       $$0.pose().translate($$8.x, $$8.y, 0.0D);
/* 262 */       $$0.pose().scale(0.375F, 0.375F, 1.0F);
/* 263 */       $$0.pose().translate(-8.0D, -8.0D, 0.0D);
/*     */       
/* 265 */       if ($$8.ingredients.length > 0) {
/* 266 */         $$0.renderItem($$8.ingredients[Mth.floor(OverlayRecipeComponent.this.time / 30.0F) % $$8.ingredients.length], 0, 0);
/*     */       }
/*     */       
/* 269 */       $$0.pose().popPose();
/*     */     } 
/* 271 */     $$0.pose().popPose();
/*     */   }
/*     */   
/*     */   protected class Pos {
/*     */     public final ItemStack[] ingredients;
/*     */     public final int x;
/*     */     public final int y;
/*     */     
/*     */     public Pos(int $$1, int $$2, ItemStack[] $$3) {
/* 280 */       this.x = $$1;
/* 281 */       this.y = $$2;
/* 282 */       this.ingredients = $$3;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\recipebook\OverlayRecipeComponent$OverlayRecipeButton.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */