/*    */ package net.minecraft.client.gui.screens.recipebook;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ import java.util.function.Function;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.client.gui.components.WidgetSprites;
/*    */ import net.minecraft.core.NonNullList;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.inventory.Slot;
/*    */ import net.minecraft.world.item.Item;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.crafting.Ingredient;
/*    */ import net.minecraft.world.item.crafting.RecipeHolder;
/*    */ 
/*    */ public abstract class AbstractFurnaceRecipeBookComponent
/*    */   extends RecipeBookComponent {
/* 19 */   private static final WidgetSprites FILTER_SPRITES = new WidgetSprites(new ResourceLocation("recipe_book/furnace_filter_enabled"), new ResourceLocation("recipe_book/furnace_filter_disabled"), new ResourceLocation("recipe_book/furnace_filter_enabled_highlighted"), new ResourceLocation("recipe_book/furnace_filter_disabled_highlighted"));
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   private Ingredient fuels;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void initFilterButtonTextures() {
/* 31 */     this.filterButton.initTextureValues(FILTER_SPRITES);
/*    */   }
/*    */ 
/*    */   
/*    */   public void slotClicked(@Nullable Slot $$0) {
/* 36 */     super.slotClicked($$0);
/*    */     
/* 38 */     if ($$0 != null && $$0.index < this.menu.getSize()) {
/* 39 */       this.ghostRecipe.clear();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void setupGhostRecipe(RecipeHolder<?> $$0, List<Slot> $$1) {
/* 45 */     ItemStack $$2 = $$0.value().getResultItem(this.minecraft.level.registryAccess());
/* 46 */     this.ghostRecipe.setRecipe($$0);
/* 47 */     this.ghostRecipe.addIngredient(Ingredient.of(new ItemStack[] { $$2 }, ), ((Slot)$$1.get(2)).x, ((Slot)$$1.get(2)).y);
/*    */     
/* 49 */     NonNullList<Ingredient> $$3 = $$0.value().getIngredients();
/*    */     
/* 51 */     Slot $$4 = $$1.get(1);
/* 52 */     if ($$4.getItem().isEmpty()) {
/* 53 */       if (this.fuels == null) {
/* 54 */         this.fuels = Ingredient.of(getFuelItems().stream().filter($$0 -> $$0.isEnabled(this.minecraft.level.enabledFeatures())).map(ItemStack::new));
/*    */       }
/* 56 */       this.ghostRecipe.addIngredient(this.fuels, $$4.x, $$4.y);
/*    */     } 
/*    */     
/* 59 */     Iterator<Ingredient> $$5 = $$3.iterator();
/* 60 */     for (int $$6 = 0; $$6 < 2; $$6++) {
/* 61 */       if (!$$5.hasNext()) {
/*    */         return;
/*    */       }
/*    */       
/* 65 */       Ingredient $$7 = $$5.next();
/* 66 */       if (!$$7.isEmpty()) {
/* 67 */         Slot $$8 = $$1.get($$6);
/* 68 */         this.ghostRecipe.addIngredient($$7, $$8.x, $$8.y);
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   protected abstract Set<Item> getFuelItems();
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\recipebook\AbstractFurnaceRecipeBookComponent.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */