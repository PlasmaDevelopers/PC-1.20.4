/*    */ package net.minecraft.world.item.crafting;
/*    */ import net.minecraft.core.RegistryAccess;
/*    */ import net.minecraft.tags.ItemTags;
/*    */ import net.minecraft.world.Container;
/*    */ import net.minecraft.world.inventory.CraftingContainer;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.Items;
/*    */ import net.minecraft.world.level.ItemLike;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.block.SuspiciousEffectHolder;
/*    */ 
/*    */ public class SuspiciousStewRecipe extends CustomRecipe {
/*    */   public SuspiciousStewRecipe(CraftingBookCategory $$0) {
/* 15 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(CraftingContainer $$0, Level $$1) {
/* 20 */     boolean $$2 = false;
/* 21 */     boolean $$3 = false;
/* 22 */     boolean $$4 = false;
/* 23 */     boolean $$5 = false;
/*    */     
/* 25 */     for (int $$6 = 0; $$6 < $$0.getContainerSize(); $$6++) {
/* 26 */       ItemStack $$7 = $$0.getItem($$6);
/* 27 */       if (!$$7.isEmpty())
/*    */       {
/*    */ 
/*    */         
/* 31 */         if ($$7.is(Blocks.BROWN_MUSHROOM.asItem()) && !$$4) {
/* 32 */           $$4 = true;
/* 33 */         } else if ($$7.is(Blocks.RED_MUSHROOM.asItem()) && !$$3) {
/* 34 */           $$3 = true;
/* 35 */         } else if ($$7.is(ItemTags.SMALL_FLOWERS) && !$$2) {
/* 36 */           $$2 = true;
/* 37 */         } else if ($$7.is(Items.BOWL) && !$$5) {
/* 38 */           $$5 = true;
/*    */         } else {
/* 40 */           return false;
/*    */         } 
/*    */       }
/*    */     } 
/* 44 */     return ($$2 && $$4 && $$3 && $$5);
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack assemble(CraftingContainer $$0, RegistryAccess $$1) {
/* 49 */     ItemStack $$2 = new ItemStack((ItemLike)Items.SUSPICIOUS_STEW, 1);
/*    */     
/* 51 */     for (int $$3 = 0; $$3 < $$0.getContainerSize(); $$3++) {
/* 52 */       ItemStack $$4 = $$0.getItem($$3);
/* 53 */       if (!$$4.isEmpty()) {
/*    */ 
/*    */ 
/*    */         
/* 57 */         SuspiciousEffectHolder $$5 = SuspiciousEffectHolder.tryGet((ItemLike)$$4.getItem());
/* 58 */         if ($$5 != null) {
/* 59 */           SuspiciousStewItem.saveMobEffects($$2, $$5.getSuspiciousEffects());
/*    */           break;
/*    */         } 
/*    */       } 
/*    */     } 
/* 64 */     return $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canCraftInDimensions(int $$0, int $$1) {
/* 69 */     return ($$0 >= 2 && $$1 >= 2);
/*    */   }
/*    */ 
/*    */   
/*    */   public RecipeSerializer<?> getSerializer() {
/* 74 */     return RecipeSerializer.SUSPICIOUS_STEW;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\crafting\SuspiciousStewRecipe.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */