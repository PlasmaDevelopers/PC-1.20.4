/*     */ package net.minecraft.world.item.crafting;
/*     */ import net.minecraft.core.NonNullList;
/*     */ import net.minecraft.core.RegistryAccess;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.world.Container;
/*     */ import net.minecraft.world.inventory.CraftingContainer;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.item.WrittenBookItem;
/*     */ import net.minecraft.world.level.Level;
/*     */ 
/*     */ public class BookCloningRecipe extends CustomRecipe {
/*     */   public BookCloningRecipe(CraftingBookCategory $$0) {
/*  14 */     super($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean matches(CraftingContainer $$0, Level $$1) {
/*  19 */     int $$2 = 0;
/*  20 */     ItemStack $$3 = ItemStack.EMPTY;
/*     */     
/*  22 */     for (int $$4 = 0; $$4 < $$0.getContainerSize(); $$4++) {
/*  23 */       ItemStack $$5 = $$0.getItem($$4);
/*  24 */       if (!$$5.isEmpty())
/*     */       {
/*     */ 
/*     */         
/*  28 */         if ($$5.is(Items.WRITTEN_BOOK)) {
/*  29 */           if (!$$3.isEmpty()) {
/*  30 */             return false;
/*     */           }
/*  32 */           $$3 = $$5;
/*  33 */         } else if ($$5.is(Items.WRITABLE_BOOK)) {
/*  34 */           $$2++;
/*     */         } else {
/*  36 */           return false;
/*     */         } 
/*     */       }
/*     */     } 
/*  40 */     return (!$$3.isEmpty() && $$3.hasTag() && $$2 > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack assemble(CraftingContainer $$0, RegistryAccess $$1) {
/*  45 */     int $$2 = 0;
/*  46 */     ItemStack $$3 = ItemStack.EMPTY;
/*     */     
/*  48 */     for (int $$4 = 0; $$4 < $$0.getContainerSize(); $$4++) {
/*  49 */       ItemStack $$5 = $$0.getItem($$4);
/*  50 */       if (!$$5.isEmpty())
/*     */       {
/*     */ 
/*     */         
/*  54 */         if ($$5.is(Items.WRITTEN_BOOK)) {
/*  55 */           if (!$$3.isEmpty()) {
/*  56 */             return ItemStack.EMPTY;
/*     */           }
/*  58 */           $$3 = $$5;
/*  59 */         } else if ($$5.is(Items.WRITABLE_BOOK)) {
/*  60 */           $$2++;
/*     */         } else {
/*  62 */           return ItemStack.EMPTY;
/*     */         } 
/*     */       }
/*     */     } 
/*  66 */     if ($$3.isEmpty() || !$$3.hasTag() || $$2 < 1 || WrittenBookItem.getGeneration($$3) >= 2) {
/*  67 */       return ItemStack.EMPTY;
/*     */     }
/*     */     
/*  70 */     ItemStack $$6 = new ItemStack((ItemLike)Items.WRITTEN_BOOK, $$2);
/*     */     
/*  72 */     CompoundTag $$7 = $$3.getTag().copy();
/*     */     
/*  74 */     $$7.putInt("generation", WrittenBookItem.getGeneration($$3) + 1);
/*  75 */     $$6.setTag($$7);
/*     */     
/*  77 */     return $$6;
/*     */   }
/*     */ 
/*     */   
/*     */   public NonNullList<ItemStack> getRemainingItems(CraftingContainer $$0) {
/*  82 */     NonNullList<ItemStack> $$1 = NonNullList.withSize($$0.getContainerSize(), ItemStack.EMPTY);
/*     */     
/*  84 */     for (int $$2 = 0; $$2 < $$1.size(); $$2++) {
/*  85 */       ItemStack $$3 = $$0.getItem($$2);
/*  86 */       if ($$3.getItem().hasCraftingRemainingItem()) {
/*  87 */         $$1.set($$2, new ItemStack((ItemLike)$$3.getItem().getCraftingRemainingItem()));
/*  88 */       } else if ($$3.getItem() instanceof WrittenBookItem) {
/*  89 */         $$1.set($$2, $$3.copyWithCount(1));
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*  94 */     return $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public RecipeSerializer<?> getSerializer() {
/*  99 */     return RecipeSerializer.BOOK_CLONING;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canCraftInDimensions(int $$0, int $$1) {
/* 104 */     return ($$0 >= 3 && $$1 >= 3);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\crafting\BookCloningRecipe.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */