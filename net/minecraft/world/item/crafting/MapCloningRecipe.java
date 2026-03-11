/*    */ package net.minecraft.world.item.crafting;
/*    */ import net.minecraft.core.RegistryAccess;
/*    */ import net.minecraft.world.Container;
/*    */ import net.minecraft.world.inventory.CraftingContainer;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.Items;
/*    */ import net.minecraft.world.level.Level;
/*    */ 
/*    */ public class MapCloningRecipe extends CustomRecipe {
/*    */   public MapCloningRecipe(CraftingBookCategory $$0) {
/* 11 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(CraftingContainer $$0, Level $$1) {
/* 16 */     int $$2 = 0;
/* 17 */     ItemStack $$3 = ItemStack.EMPTY;
/*    */     
/* 19 */     for (int $$4 = 0; $$4 < $$0.getContainerSize(); $$4++) {
/* 20 */       ItemStack $$5 = $$0.getItem($$4);
/* 21 */       if (!$$5.isEmpty())
/*    */       {
/*    */ 
/*    */         
/* 25 */         if ($$5.is(Items.FILLED_MAP)) {
/* 26 */           if (!$$3.isEmpty()) {
/* 27 */             return false;
/*    */           }
/* 29 */           $$3 = $$5;
/* 30 */         } else if ($$5.is(Items.MAP)) {
/* 31 */           $$2++;
/*    */         } else {
/* 33 */           return false;
/*    */         } 
/*    */       }
/*    */     } 
/* 37 */     return (!$$3.isEmpty() && $$2 > 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack assemble(CraftingContainer $$0, RegistryAccess $$1) {
/* 42 */     int $$2 = 0;
/* 43 */     ItemStack $$3 = ItemStack.EMPTY;
/*    */     
/* 45 */     for (int $$4 = 0; $$4 < $$0.getContainerSize(); $$4++) {
/* 46 */       ItemStack $$5 = $$0.getItem($$4);
/* 47 */       if (!$$5.isEmpty())
/*    */       {
/*    */ 
/*    */         
/* 51 */         if ($$5.is(Items.FILLED_MAP)) {
/* 52 */           if (!$$3.isEmpty()) {
/* 53 */             return ItemStack.EMPTY;
/*    */           }
/* 55 */           $$3 = $$5;
/* 56 */         } else if ($$5.is(Items.MAP)) {
/* 57 */           $$2++;
/*    */         } else {
/* 59 */           return ItemStack.EMPTY;
/*    */         } 
/*    */       }
/*    */     } 
/* 63 */     if ($$3.isEmpty() || $$2 < 1) {
/* 64 */       return ItemStack.EMPTY;
/*    */     }
/*    */     
/* 67 */     return $$3.copyWithCount($$2 + 1);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canCraftInDimensions(int $$0, int $$1) {
/* 72 */     return ($$0 >= 3 && $$1 >= 3);
/*    */   }
/*    */ 
/*    */   
/*    */   public RecipeSerializer<?> getSerializer() {
/* 77 */     return RecipeSerializer.MAP_CLONING;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\crafting\MapCloningRecipe.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */