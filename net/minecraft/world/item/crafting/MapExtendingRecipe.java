/*    */ package net.minecraft.world.item.crafting;
/*    */ import java.util.Map;
/*    */ import net.minecraft.core.RegistryAccess;
/*    */ import net.minecraft.world.Container;
/*    */ import net.minecraft.world.inventory.CraftingContainer;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.Items;
/*    */ import net.minecraft.world.item.MapItem;
/*    */ import net.minecraft.world.level.ItemLike;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
/*    */ 
/*    */ public class MapExtendingRecipe extends ShapedRecipe {
/*    */   public MapExtendingRecipe(CraftingBookCategory $$0) {
/* 15 */     super("", $$0, 
/* 16 */         ShapedRecipePattern.of(
/* 17 */           Map.of(
/* 18 */             Character.valueOf('#'), Ingredient.of(new ItemLike[] { (ItemLike)Items.PAPER
/* 19 */               }, ), Character.valueOf('x'), Ingredient.of(new ItemLike[] { (ItemLike)Items.FILLED_MAP })), new String[] { "###", "#x#", "###" }), new ItemStack((ItemLike)Items.MAP));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean matches(CraftingContainer $$0, Level $$1) {
/* 31 */     if (!super.matches($$0, $$1)) {
/* 32 */       return false;
/*    */     }
/* 34 */     ItemStack $$2 = findFilledMap($$0);
/*    */     
/* 36 */     if ($$2.isEmpty()) {
/* 37 */       return false;
/*    */     }
/* 39 */     MapItemSavedData $$3 = MapItem.getSavedData($$2, $$1);
/* 40 */     if ($$3 == null) {
/* 41 */       return false;
/*    */     }
/*    */     
/* 44 */     if ($$3.isExplorationMap()) {
/* 45 */       return false;
/*    */     }
/*    */     
/* 48 */     return ($$3.scale < 4);
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack assemble(CraftingContainer $$0, RegistryAccess $$1) {
/* 53 */     ItemStack $$2 = findFilledMap($$0).copyWithCount(1);
/* 54 */     $$2.getOrCreateTag().putInt("map_scale_direction", 1);
/* 55 */     return $$2;
/*    */   }
/*    */   
/*    */   private static ItemStack findFilledMap(CraftingContainer $$0) {
/* 59 */     for (int $$1 = 0; $$1 < $$0.getContainerSize(); $$1++) {
/* 60 */       ItemStack $$2 = $$0.getItem($$1);
/* 61 */       if ($$2.is(Items.FILLED_MAP)) {
/* 62 */         return $$2;
/*    */       }
/*    */     } 
/* 65 */     return ItemStack.EMPTY;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isSpecial() {
/* 70 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public RecipeSerializer<?> getSerializer() {
/* 75 */     return RecipeSerializer.MAP_EXTENDING;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\crafting\MapExtendingRecipe.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */