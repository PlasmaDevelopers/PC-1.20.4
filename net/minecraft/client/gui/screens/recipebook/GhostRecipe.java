/*     */ package net.minecraft.client.gui.screens.recipebook;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.client.renderer.RenderType;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.crafting.Ingredient;
/*     */ import net.minecraft.world.item.crafting.RecipeHolder;
/*     */ 
/*     */ public class GhostRecipe
/*     */ {
/*     */   @Nullable
/*     */   private RecipeHolder<?> recipe;
/*  19 */   private final List<GhostIngredient> ingredients = Lists.newArrayList();
/*     */   float time;
/*     */   
/*     */   public void clear() {
/*  23 */     this.recipe = null;
/*  24 */     this.ingredients.clear();
/*  25 */     this.time = 0.0F;
/*     */   }
/*     */   
/*     */   public void addIngredient(Ingredient $$0, int $$1, int $$2) {
/*  29 */     this.ingredients.add(new GhostIngredient($$0, $$1, $$2));
/*     */   }
/*     */   
/*     */   public GhostIngredient get(int $$0) {
/*  33 */     return this.ingredients.get($$0);
/*     */   }
/*     */   
/*     */   public int size() {
/*  37 */     return this.ingredients.size();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public RecipeHolder<?> getRecipe() {
/*  42 */     return this.recipe;
/*     */   }
/*     */   
/*     */   public void setRecipe(RecipeHolder<?> $$0) {
/*  46 */     this.recipe = $$0;
/*     */   }
/*     */   
/*     */   public void render(GuiGraphics $$0, Minecraft $$1, int $$2, int $$3, boolean $$4, float $$5) {
/*  50 */     if (!Screen.hasControlDown()) {
/*  51 */       this.time += $$5;
/*     */     }
/*     */     
/*  54 */     for (int $$6 = 0; $$6 < this.ingredients.size(); $$6++) {
/*  55 */       GhostIngredient $$7 = this.ingredients.get($$6);
/*     */       
/*  57 */       int $$8 = $$7.getX() + $$2;
/*  58 */       int $$9 = $$7.getY() + $$3;
/*     */       
/*  60 */       if ($$6 == 0 && $$4) {
/*  61 */         $$0.fill($$8 - 4, $$9 - 4, $$8 + 20, $$9 + 20, 822018048);
/*     */       } else {
/*  63 */         $$0.fill($$8, $$9, $$8 + 16, $$9 + 16, 822018048);
/*     */       } 
/*     */       
/*  66 */       ItemStack $$10 = $$7.getItem();
/*     */       
/*  68 */       $$0.renderFakeItem($$10, $$8, $$9);
/*     */       
/*  70 */       $$0.fill(RenderType.guiGhostRecipeOverlay(), $$8, $$9, $$8 + 16, $$9 + 16, 822083583);
/*     */       
/*  72 */       if ($$6 == 0)
/*  73 */         $$0.renderItemDecorations($$1.font, $$10, $$8, $$9); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public class GhostIngredient
/*     */   {
/*     */     private final Ingredient ingredient;
/*     */     private final int x;
/*     */     private final int y;
/*     */     
/*     */     public GhostIngredient(Ingredient $$1, int $$2, int $$3) {
/*  84 */       this.ingredient = $$1;
/*  85 */       this.x = $$2;
/*  86 */       this.y = $$3;
/*     */     }
/*     */     
/*     */     public int getX() {
/*  90 */       return this.x;
/*     */     }
/*     */     
/*     */     public int getY() {
/*  94 */       return this.y;
/*     */     }
/*     */     
/*     */     public ItemStack getItem() {
/*  98 */       ItemStack[] $$0 = this.ingredient.getItems();
/*  99 */       if ($$0.length == 0) {
/* 100 */         return ItemStack.EMPTY;
/*     */       }
/* 102 */       return $$0[Mth.floor(GhostRecipe.this.time / 30.0F) % $$0.length];
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\recipebook\GhostRecipe.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */