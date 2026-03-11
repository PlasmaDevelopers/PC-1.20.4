/*    */ package net.minecraft.world.item.crafting;
/*    */ import net.minecraft.core.RegistryAccess;
/*    */ import net.minecraft.world.Container;
/*    */ import net.minecraft.world.inventory.CraftingContainer;
/*    */ import net.minecraft.world.item.DyeItem;
/*    */ import net.minecraft.world.item.Item;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.Items;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.block.ShulkerBoxBlock;
/*    */ 
/*    */ public class ShulkerBoxColoring extends CustomRecipe {
/*    */   public ShulkerBoxColoring(CraftingBookCategory $$0) {
/* 15 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(CraftingContainer $$0, Level $$1) {
/* 20 */     int $$2 = 0;
/* 21 */     int $$3 = 0;
/*    */     
/* 23 */     for (int $$4 = 0; $$4 < $$0.getContainerSize(); $$4++) {
/* 24 */       ItemStack $$5 = $$0.getItem($$4);
/*    */       
/* 26 */       if (!$$5.isEmpty()) {
/*    */ 
/*    */ 
/*    */         
/* 30 */         if (Block.byItem($$5.getItem()) instanceof ShulkerBoxBlock) {
/* 31 */           $$2++;
/* 32 */         } else if ($$5.getItem() instanceof DyeItem) {
/* 33 */           $$3++;
/*    */         } else {
/* 35 */           return false;
/*    */         } 
/*    */         
/* 38 */         if ($$3 > 1 || $$2 > 1) {
/* 39 */           return false;
/*    */         }
/*    */       } 
/*    */     } 
/* 43 */     return ($$2 == 1 && $$3 == 1);
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack assemble(CraftingContainer $$0, RegistryAccess $$1) {
/* 48 */     ItemStack $$2 = ItemStack.EMPTY;
/* 49 */     DyeItem $$3 = (DyeItem)Items.WHITE_DYE;
/*    */     
/* 51 */     for (int $$4 = 0; $$4 < $$0.getContainerSize(); $$4++) {
/* 52 */       ItemStack $$5 = $$0.getItem($$4);
/*    */       
/* 54 */       if (!$$5.isEmpty()) {
/*    */ 
/*    */ 
/*    */         
/* 58 */         Item $$6 = $$5.getItem();
/* 59 */         if (Block.byItem($$6) instanceof ShulkerBoxBlock) {
/* 60 */           $$2 = $$5;
/* 61 */         } else if ($$6 instanceof DyeItem) {
/* 62 */           $$3 = (DyeItem)$$6;
/*    */         } 
/*    */       } 
/*    */     } 
/* 66 */     ItemStack $$7 = ShulkerBoxBlock.getColoredItemStack($$3.getDyeColor());
/* 67 */     if ($$2.hasTag()) {
/* 68 */       $$7.setTag($$2.getTag().copy());
/*    */     }
/*    */     
/* 71 */     return $$7;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canCraftInDimensions(int $$0, int $$1) {
/* 76 */     return ($$0 * $$1 >= 2);
/*    */   }
/*    */ 
/*    */   
/*    */   public RecipeSerializer<?> getSerializer() {
/* 81 */     return RecipeSerializer.SHULKER_BOX_COLORING;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\crafting\ShulkerBoxColoring.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */