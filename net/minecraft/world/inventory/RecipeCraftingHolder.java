/*    */ package net.minecraft.world.inventory;
/*    */ 
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.server.level.ServerPlayer;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.crafting.RecipeHolder;
/*    */ import net.minecraft.world.level.GameRules;
/*    */ import net.minecraft.world.level.Level;
/*    */ 
/*    */ public interface RecipeCraftingHolder
/*    */ {
/*    */   void setRecipeUsed(@Nullable RecipeHolder<?> paramRecipeHolder);
/*    */   
/*    */   @Nullable
/*    */   RecipeHolder<?> getRecipeUsed();
/*    */   
/*    */   default void awardUsedRecipes(Player $$0, List<ItemStack> $$1) {
/* 21 */     RecipeHolder<?> $$2 = getRecipeUsed();
/* 22 */     if ($$2 != null) {
/* 23 */       $$0.triggerRecipeCrafted($$2, $$1);
/* 24 */       if (!$$2.value().isSpecial()) {
/* 25 */         $$0.awardRecipes(Collections.singleton($$2));
/* 26 */         setRecipeUsed(null);
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   default boolean setRecipeUsed(Level $$0, ServerPlayer $$1, RecipeHolder<?> $$2) {
/* 32 */     if ($$2.value().isSpecial() || !$$0.getGameRules().getBoolean(GameRules.RULE_LIMITED_CRAFTING) || $$1.getRecipeBook().contains($$2)) {
/* 33 */       setRecipeUsed($$2);
/* 34 */       return true;
/*    */     } 
/*    */     
/* 37 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\inventory\RecipeCraftingHolder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */