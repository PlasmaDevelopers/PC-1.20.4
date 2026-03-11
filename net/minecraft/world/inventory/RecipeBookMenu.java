/*    */ package net.minecraft.world.inventory;
/*    */ 
/*    */ import net.minecraft.recipebook.ServerPlaceRecipe;
/*    */ import net.minecraft.server.level.ServerPlayer;
/*    */ import net.minecraft.world.Container;
/*    */ import net.minecraft.world.entity.player.StackedContents;
/*    */ import net.minecraft.world.item.crafting.Recipe;
/*    */ import net.minecraft.world.item.crafting.RecipeHolder;
/*    */ 
/*    */ public abstract class RecipeBookMenu<C extends Container> extends AbstractContainerMenu {
/*    */   public RecipeBookMenu(MenuType<?> $$0, int $$1) {
/* 12 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handlePlacement(boolean $$0, RecipeHolder<?> $$1, ServerPlayer $$2) {
/* 17 */     (new ServerPlaceRecipe(this)).recipeClicked($$2, $$1, $$0);
/*    */   }
/*    */   
/*    */   public abstract void fillCraftSlotsStackedContents(StackedContents paramStackedContents);
/*    */   
/*    */   public abstract void clearCraftingContent();
/*    */   
/*    */   public abstract boolean recipeMatches(RecipeHolder<? extends Recipe<C>> paramRecipeHolder);
/*    */   
/*    */   public abstract int getResultSlotIndex();
/*    */   
/*    */   public abstract int getGridWidth();
/*    */   
/*    */   public abstract int getGridHeight();
/*    */   
/*    */   public abstract int getSize();
/*    */   
/*    */   public abstract RecipeBookType getRecipeBookType();
/*    */   
/*    */   public abstract boolean shouldMoveToInventory(int paramInt);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\inventory\RecipeBookMenu.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */