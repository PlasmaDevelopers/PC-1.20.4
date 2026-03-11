/*    */ package net.minecraft.world.inventory;
/*    */ 
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.NonNullList;
/*    */ import net.minecraft.world.Container;
/*    */ import net.minecraft.world.ContainerHelper;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.crafting.RecipeHolder;
/*    */ 
/*    */ public class ResultContainer implements Container, RecipeCraftingHolder {
/* 13 */   private final NonNullList<ItemStack> itemStacks = NonNullList.withSize(1, ItemStack.EMPTY);
/*    */   
/*    */   @Nullable
/*    */   private RecipeHolder<?> recipeUsed;
/*    */   
/*    */   public int getContainerSize() {
/* 19 */     return 1;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isEmpty() {
/* 24 */     for (ItemStack $$0 : this.itemStacks) {
/* 25 */       if (!$$0.isEmpty()) {
/* 26 */         return false;
/*    */       }
/*    */     } 
/* 29 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack getItem(int $$0) {
/* 34 */     return (ItemStack)this.itemStacks.get(0);
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack removeItem(int $$0, int $$1) {
/* 39 */     return ContainerHelper.takeItem((List)this.itemStacks, 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack removeItemNoUpdate(int $$0) {
/* 44 */     return ContainerHelper.takeItem((List)this.itemStacks, 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setItem(int $$0, ItemStack $$1) {
/* 49 */     this.itemStacks.set(0, $$1);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setChanged() {}
/*    */ 
/*    */   
/*    */   public boolean stillValid(Player $$0) {
/* 58 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void clearContent() {
/* 63 */     this.itemStacks.clear();
/*    */   }
/*    */ 
/*    */   
/*    */   public void setRecipeUsed(@Nullable RecipeHolder<?> $$0) {
/* 68 */     this.recipeUsed = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public RecipeHolder<?> getRecipeUsed() {
/* 74 */     return this.recipeUsed;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\inventory\ResultContainer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */