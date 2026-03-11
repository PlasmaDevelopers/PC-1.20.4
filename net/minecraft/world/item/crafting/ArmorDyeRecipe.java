/*    */ package net.minecraft.world.item.crafting;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.List;
/*    */ import net.minecraft.core.RegistryAccess;
/*    */ import net.minecraft.world.Container;
/*    */ import net.minecraft.world.inventory.CraftingContainer;
/*    */ import net.minecraft.world.item.DyeItem;
/*    */ import net.minecraft.world.item.DyeableLeatherItem;
/*    */ import net.minecraft.world.item.Item;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.Level;
/*    */ 
/*    */ public class ArmorDyeRecipe extends CustomRecipe {
/*    */   public ArmorDyeRecipe(CraftingBookCategory $$0) {
/* 16 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(CraftingContainer $$0, Level $$1) {
/* 21 */     ItemStack $$2 = ItemStack.EMPTY;
/* 22 */     List<ItemStack> $$3 = Lists.newArrayList();
/*    */     
/* 24 */     for (int $$4 = 0; $$4 < $$0.getContainerSize(); $$4++) {
/* 25 */       ItemStack $$5 = $$0.getItem($$4);
/* 26 */       if (!$$5.isEmpty())
/*    */       {
/*    */ 
/*    */         
/* 30 */         if ($$5.getItem() instanceof DyeableLeatherItem) {
/* 31 */           if (!$$2.isEmpty()) {
/* 32 */             return false;
/*    */           }
/* 34 */           $$2 = $$5;
/* 35 */         } else if ($$5.getItem() instanceof DyeItem) {
/* 36 */           $$3.add($$5);
/*    */         } else {
/* 38 */           return false;
/*    */         } 
/*    */       }
/*    */     } 
/* 42 */     return (!$$2.isEmpty() && !$$3.isEmpty());
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack assemble(CraftingContainer $$0, RegistryAccess $$1) {
/* 47 */     List<DyeItem> $$2 = Lists.newArrayList();
/* 48 */     ItemStack $$3 = ItemStack.EMPTY;
/*    */     
/* 50 */     for (int $$4 = 0; $$4 < $$0.getContainerSize(); $$4++) {
/* 51 */       ItemStack $$5 = $$0.getItem($$4);
/* 52 */       if (!$$5.isEmpty()) {
/*    */ 
/*    */ 
/*    */         
/* 56 */         Item $$6 = $$5.getItem();
/* 57 */         if ($$6 instanceof DyeableLeatherItem) {
/* 58 */           if (!$$3.isEmpty()) {
/* 59 */             return ItemStack.EMPTY;
/*    */           }
/*    */           
/* 62 */           $$3 = $$5.copy();
/* 63 */         } else if ($$6 instanceof DyeItem) {
/* 64 */           $$2.add((DyeItem)$$6);
/*    */         } else {
/* 66 */           return ItemStack.EMPTY;
/*    */         } 
/*    */       } 
/*    */     } 
/* 70 */     if ($$3.isEmpty() || $$2.isEmpty()) {
/* 71 */       return ItemStack.EMPTY;
/*    */     }
/*    */     
/* 74 */     return DyeableLeatherItem.dyeArmor($$3, $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canCraftInDimensions(int $$0, int $$1) {
/* 79 */     return ($$0 * $$1 >= 2);
/*    */   }
/*    */ 
/*    */   
/*    */   public RecipeSerializer<?> getSerializer() {
/* 84 */     return RecipeSerializer.ARMOR_DYE;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\crafting\ArmorDyeRecipe.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */