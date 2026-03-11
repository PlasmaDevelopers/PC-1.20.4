/*    */ package net.minecraft.world.item.crafting;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.List;
/*    */ import net.minecraft.core.RegistryAccess;
/*    */ import net.minecraft.world.Container;
/*    */ import net.minecraft.world.inventory.CraftingContainer;
/*    */ import net.minecraft.world.item.DyeItem;
/*    */ import net.minecraft.world.item.Item;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.Items;
/*    */ import net.minecraft.world.level.ItemLike;
/*    */ import net.minecraft.world.level.Level;
/*    */ 
/*    */ public class FireworkStarFadeRecipe extends CustomRecipe {
/* 16 */   private static final Ingredient STAR_INGREDIENT = Ingredient.of(new ItemLike[] { (ItemLike)Items.FIREWORK_STAR });
/*    */   
/*    */   public FireworkStarFadeRecipe(CraftingBookCategory $$0) {
/* 19 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(CraftingContainer $$0, Level $$1) {
/* 24 */     boolean $$2 = false;
/* 25 */     boolean $$3 = false;
/*    */     
/* 27 */     for (int $$4 = 0; $$4 < $$0.getContainerSize(); $$4++) {
/* 28 */       ItemStack $$5 = $$0.getItem($$4);
/* 29 */       if (!$$5.isEmpty())
/*    */       {
/*    */ 
/*    */         
/* 33 */         if ($$5.getItem() instanceof DyeItem) {
/* 34 */           $$2 = true;
/* 35 */         } else if (STAR_INGREDIENT.test($$5)) {
/* 36 */           if ($$3) {
/* 37 */             return false;
/*    */           }
/* 39 */           $$3 = true;
/*    */         } else {
/* 41 */           return false;
/*    */         } 
/*    */       }
/*    */     } 
/* 45 */     return ($$3 && $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack assemble(CraftingContainer $$0, RegistryAccess $$1) {
/* 50 */     List<Integer> $$2 = Lists.newArrayList();
/* 51 */     ItemStack $$3 = null;
/*    */     
/* 53 */     for (int $$4 = 0; $$4 < $$0.getContainerSize(); $$4++) {
/* 54 */       ItemStack $$5 = $$0.getItem($$4);
/*    */       
/* 56 */       Item $$6 = $$5.getItem();
/* 57 */       if ($$6 instanceof DyeItem) {
/* 58 */         $$2.add(Integer.valueOf(((DyeItem)$$6).getDyeColor().getFireworkColor()));
/* 59 */       } else if (STAR_INGREDIENT.test($$5)) {
/* 60 */         $$3 = $$5.copyWithCount(1);
/*    */       } 
/*    */     } 
/*    */ 
/*    */     
/* 65 */     if ($$3 == null || $$2.isEmpty()) {
/* 66 */       return ItemStack.EMPTY;
/*    */     }
/*    */     
/* 69 */     $$3.getOrCreateTagElement("Explosion").putIntArray("FadeColors", $$2);
/*    */     
/* 71 */     return $$3;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canCraftInDimensions(int $$0, int $$1) {
/* 76 */     return ($$0 * $$1 >= 2);
/*    */   }
/*    */ 
/*    */   
/*    */   public RecipeSerializer<?> getSerializer() {
/* 81 */     return RecipeSerializer.FIREWORK_STAR_FADE;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\crafting\FireworkStarFadeRecipe.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */