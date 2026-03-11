/*    */ package net.minecraft.world.item.crafting;
/*    */ 
/*    */ import net.minecraft.core.RegistryAccess;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ 
/*    */ public abstract class CustomRecipe implements CraftingRecipe {
/*    */   private final CraftingBookCategory category;
/*    */   
/*    */   public CustomRecipe(CraftingBookCategory $$0) {
/* 10 */     this.category = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isSpecial() {
/* 15 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack getResultItem(RegistryAccess $$0) {
/* 20 */     return ItemStack.EMPTY;
/*    */   }
/*    */ 
/*    */   
/*    */   public CraftingBookCategory category() {
/* 25 */     return this.category;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\crafting\CustomRecipe.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */