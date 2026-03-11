/*    */ package net.minecraft.world.item.crafting;
/*    */ import net.minecraft.core.RegistryAccess;
/*    */ import net.minecraft.world.Container;
/*    */ import net.minecraft.world.inventory.CraftingContainer;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.Items;
/*    */ import net.minecraft.world.item.alchemy.PotionUtils;
/*    */ import net.minecraft.world.level.Level;
/*    */ 
/*    */ public class TippedArrowRecipe extends CustomRecipe {
/*    */   public TippedArrowRecipe(CraftingBookCategory $$0) {
/* 12 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(CraftingContainer $$0, Level $$1) {
/* 17 */     if ($$0.getWidth() != 3 || $$0.getHeight() != 3) {
/* 18 */       return false;
/*    */     }
/*    */     
/* 21 */     for (int $$2 = 0; $$2 < $$0.getWidth(); $$2++) {
/* 22 */       for (int $$3 = 0; $$3 < $$0.getHeight(); $$3++) {
/* 23 */         ItemStack $$4 = $$0.getItem($$2 + $$3 * $$0.getWidth());
/*    */         
/* 25 */         if ($$4.isEmpty()) {
/* 26 */           return false;
/*    */         }
/*    */         
/* 29 */         if ($$2 == 1 && $$3 == 1) {
/* 30 */           if (!$$4.is(Items.LINGERING_POTION)) {
/* 31 */             return false;
/*    */           }
/* 33 */         } else if (!$$4.is(Items.ARROW)) {
/* 34 */           return false;
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 39 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack assemble(CraftingContainer $$0, RegistryAccess $$1) {
/* 44 */     ItemStack $$2 = $$0.getItem(1 + $$0.getWidth());
/* 45 */     if (!$$2.is(Items.LINGERING_POTION)) {
/* 46 */       return ItemStack.EMPTY;
/*    */     }
/*    */     
/* 49 */     ItemStack $$3 = new ItemStack((ItemLike)Items.TIPPED_ARROW, 8);
/* 50 */     PotionUtils.setPotion($$3, PotionUtils.getPotion($$2));
/* 51 */     PotionUtils.setCustomEffects($$3, PotionUtils.getCustomEffects($$2));
/*    */     
/* 53 */     return $$3;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canCraftInDimensions(int $$0, int $$1) {
/* 58 */     return ($$0 >= 2 && $$1 >= 2);
/*    */   }
/*    */ 
/*    */   
/*    */   public RecipeSerializer<?> getSerializer() {
/* 63 */     return RecipeSerializer.TIPPED_ARROW;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\crafting\TippedArrowRecipe.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */