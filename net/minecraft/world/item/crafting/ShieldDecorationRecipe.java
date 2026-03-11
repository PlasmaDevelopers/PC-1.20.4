/*    */ package net.minecraft.world.item.crafting;
/*    */ 
/*    */ import net.minecraft.core.RegistryAccess;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.world.Container;
/*    */ import net.minecraft.world.inventory.CraftingContainer;
/*    */ import net.minecraft.world.item.BannerItem;
/*    */ import net.minecraft.world.item.BlockItem;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.Items;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*    */ 
/*    */ public class ShieldDecorationRecipe extends CustomRecipe {
/*    */   public ShieldDecorationRecipe(CraftingBookCategory $$0) {
/* 16 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(CraftingContainer $$0, Level $$1) {
/* 21 */     ItemStack $$2 = ItemStack.EMPTY;
/* 22 */     ItemStack $$3 = ItemStack.EMPTY;
/*    */     
/* 24 */     for (int $$4 = 0; $$4 < $$0.getContainerSize(); $$4++) {
/* 25 */       ItemStack $$5 = $$0.getItem($$4);
/* 26 */       if (!$$5.isEmpty())
/*    */       {
/*    */ 
/*    */         
/* 30 */         if ($$5.getItem() instanceof BannerItem) {
/* 31 */           if (!$$3.isEmpty())
/*    */           {
/* 33 */             return false;
/*    */           }
/* 35 */           $$3 = $$5;
/* 36 */         } else if ($$5.is(Items.SHIELD)) {
/* 37 */           if (!$$2.isEmpty())
/*    */           {
/* 39 */             return false;
/*    */           }
/* 41 */           if (BlockItem.getBlockEntityData($$5) != null)
/*    */           {
/* 43 */             return false;
/*    */           }
/* 45 */           $$2 = $$5;
/*    */         } else {
/*    */           
/* 48 */           return false;
/*    */         } 
/*    */       }
/*    */     } 
/* 52 */     if ($$2.isEmpty() || $$3.isEmpty())
/*    */     {
/* 54 */       return false;
/*    */     }
/*    */     
/* 57 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack assemble(CraftingContainer $$0, RegistryAccess $$1) {
/* 62 */     ItemStack $$2 = ItemStack.EMPTY;
/* 63 */     ItemStack $$3 = ItemStack.EMPTY;
/*    */     
/* 65 */     for (int $$4 = 0; $$4 < $$0.getContainerSize(); $$4++) {
/* 66 */       ItemStack $$5 = $$0.getItem($$4);
/* 67 */       if (!$$5.isEmpty())
/*    */       {
/*    */ 
/*    */         
/* 71 */         if ($$5.getItem() instanceof BannerItem) {
/* 72 */           $$2 = $$5;
/* 73 */         } else if ($$5.is(Items.SHIELD)) {
/* 74 */           $$3 = $$5.copy();
/*    */         } 
/*    */       }
/*    */     } 
/* 78 */     if ($$3.isEmpty()) {
/* 79 */       return $$3;
/*    */     }
/*    */     
/* 82 */     CompoundTag $$6 = BlockItem.getBlockEntityData($$2);
/* 83 */     CompoundTag $$7 = ($$6 == null) ? new CompoundTag() : $$6.copy();
/* 84 */     $$7.putInt("Base", ((BannerItem)$$2.getItem()).getColor().getId());
/* 85 */     BlockItem.setBlockEntityData($$3, BlockEntityType.BANNER, $$7);
/*    */     
/* 87 */     return $$3;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canCraftInDimensions(int $$0, int $$1) {
/* 92 */     return ($$0 * $$1 >= 2);
/*    */   }
/*    */ 
/*    */   
/*    */   public RecipeSerializer<?> getSerializer() {
/* 97 */     return RecipeSerializer.SHIELD_DECORATION;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\crafting\ShieldDecorationRecipe.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */