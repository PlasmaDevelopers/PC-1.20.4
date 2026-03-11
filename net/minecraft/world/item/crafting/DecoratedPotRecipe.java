/*    */ package net.minecraft.world.item.crafting;
/*    */ import net.minecraft.core.RegistryAccess;
/*    */ import net.minecraft.tags.ItemTags;
/*    */ import net.minecraft.world.Container;
/*    */ import net.minecraft.world.inventory.CraftingContainer;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.Items;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity;
/*    */ 
/*    */ public class DecoratedPotRecipe extends CustomRecipe {
/*    */   public DecoratedPotRecipe(CraftingBookCategory $$0) {
/* 13 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(CraftingContainer $$0, Level $$1) {
/* 18 */     if (!canCraftInDimensions($$0.getWidth(), $$0.getHeight())) {
/* 19 */       return false;
/*    */     }
/* 21 */     for (int $$2 = 0; $$2 < $$0.getContainerSize(); $$2++) {
/* 22 */       ItemStack $$3 = $$0.getItem($$2);
/* 23 */       switch ($$2) { case 1: case 3: case 5:
/*    */         case 7:
/* 25 */           if (!$$3.is(ItemTags.DECORATED_POT_INGREDIENTS)) {
/* 26 */             return false;
/*    */           }
/*    */           break;
/*    */         default:
/* 30 */           if (!$$3.is(Items.AIR)) {
/* 31 */             return false;
/*    */           }
/*    */           break; }
/*    */     
/*    */     } 
/* 36 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack assemble(CraftingContainer $$0, RegistryAccess $$1) {
/* 45 */     DecoratedPotBlockEntity.Decorations $$2 = new DecoratedPotBlockEntity.Decorations($$0.getItem(1).getItem(), $$0.getItem(3).getItem(), $$0.getItem(5).getItem(), $$0.getItem(7).getItem());
/*    */     
/* 47 */     return DecoratedPotBlockEntity.createDecoratedPotItem($$2);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canCraftInDimensions(int $$0, int $$1) {
/* 52 */     return ($$0 == 3 && $$1 == 3);
/*    */   }
/*    */ 
/*    */   
/*    */   public RecipeSerializer<?> getSerializer() {
/* 57 */     return RecipeSerializer.DECORATED_POT_RECIPE;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\crafting\DecoratedPotRecipe.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */