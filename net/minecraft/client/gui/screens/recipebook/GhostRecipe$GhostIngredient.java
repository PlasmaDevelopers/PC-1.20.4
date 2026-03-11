/*     */ package net.minecraft.client.gui.screens.recipebook;
/*     */ 
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.crafting.Ingredient;
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
/*     */ public class GhostIngredient
/*     */ {
/*     */   private final Ingredient ingredient;
/*     */   private final int x;
/*     */   private final int y;
/*     */   
/*     */   public GhostIngredient(Ingredient $$1, int $$2, int $$3) {
/*  84 */     this.ingredient = $$1;
/*  85 */     this.x = $$2;
/*  86 */     this.y = $$3;
/*     */   }
/*     */   
/*     */   public int getX() {
/*  90 */     return this.x;
/*     */   }
/*     */   
/*     */   public int getY() {
/*  94 */     return this.y;
/*     */   }
/*     */   
/*     */   public ItemStack getItem() {
/*  98 */     ItemStack[] $$0 = this.ingredient.getItems();
/*  99 */     if ($$0.length == 0) {
/* 100 */       return ItemStack.EMPTY;
/*     */     }
/* 102 */     return $$0[Mth.floor(GhostRecipe.this.time / 30.0F) % $$0.length];
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\recipebook\GhostRecipe$GhostIngredient.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */