/*     */ package net.minecraft.world.item.crafting;
/*     */ import net.minecraft.core.NonNullList;
/*     */ import net.minecraft.core.RegistryAccess;
/*     */ import net.minecraft.world.Container;
/*     */ import net.minecraft.world.inventory.CraftingContainer;
/*     */ import net.minecraft.world.item.BannerItem;
/*     */ import net.minecraft.world.item.DyeColor;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.entity.BannerBlockEntity;
/*     */ 
/*     */ public class BannerDuplicateRecipe extends CustomRecipe {
/*     */   public BannerDuplicateRecipe(CraftingBookCategory $$0) {
/*  15 */     super($$0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean matches(CraftingContainer $$0, Level $$1) {
/*  21 */     DyeColor $$2 = null;
/*  22 */     ItemStack $$3 = null;
/*  23 */     ItemStack $$4 = null;
/*     */     
/*  25 */     for (int $$5 = 0; $$5 < $$0.getContainerSize(); $$5++) {
/*  26 */       ItemStack $$6 = $$0.getItem($$5);
/*  27 */       if (!$$6.isEmpty()) {
/*     */ 
/*     */         
/*  30 */         Item $$7 = $$6.getItem();
/*  31 */         if (!($$7 instanceof BannerItem)) {
/*  32 */           return false;
/*     */         }
/*     */         
/*  35 */         BannerItem $$8 = (BannerItem)$$7;
/*     */         
/*  37 */         if ($$2 == null) {
/*  38 */           $$2 = $$8.getColor();
/*  39 */         } else if ($$2 != $$8.getColor()) {
/*  40 */           return false;
/*     */         } 
/*     */         
/*  43 */         int $$9 = BannerBlockEntity.getPatternCount($$6);
/*  44 */         if ($$9 > 6) {
/*  45 */           return false;
/*     */         }
/*     */         
/*  48 */         if ($$9 > 0) {
/*  49 */           if ($$3 == null) {
/*  50 */             $$3 = $$6;
/*     */           } else {
/*  52 */             return false;
/*     */           }
/*     */         
/*  55 */         } else if ($$4 == null) {
/*  56 */           $$4 = $$6;
/*     */         } else {
/*  58 */           return false;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  63 */     return ($$3 != null && $$4 != null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack assemble(CraftingContainer $$0, RegistryAccess $$1) {
/*  69 */     for (int $$2 = 0; $$2 < $$0.getContainerSize(); $$2++) {
/*  70 */       ItemStack $$3 = $$0.getItem($$2);
/*  71 */       if (!$$3.isEmpty()) {
/*     */ 
/*     */         
/*  74 */         int $$4 = BannerBlockEntity.getPatternCount($$3);
/*  75 */         if ($$4 > 0 && $$4 <= 6) {
/*  76 */           return $$3.copyWithCount(1);
/*     */         }
/*     */       } 
/*     */     } 
/*  80 */     return ItemStack.EMPTY;
/*     */   }
/*     */ 
/*     */   
/*     */   public NonNullList<ItemStack> getRemainingItems(CraftingContainer $$0) {
/*  85 */     NonNullList<ItemStack> $$1 = NonNullList.withSize($$0.getContainerSize(), ItemStack.EMPTY);
/*     */     
/*  87 */     for (int $$2 = 0; $$2 < $$1.size(); $$2++) {
/*  88 */       ItemStack $$3 = $$0.getItem($$2);
/*  89 */       if (!$$3.isEmpty()) {
/*  90 */         if ($$3.getItem().hasCraftingRemainingItem()) {
/*  91 */           $$1.set($$2, new ItemStack((ItemLike)$$3.getItem().getCraftingRemainingItem()));
/*  92 */         } else if ($$3.hasTag() && 
/*  93 */           BannerBlockEntity.getPatternCount($$3) > 0) {
/*  94 */           $$1.set($$2, $$3.copyWithCount(1));
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 100 */     return $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public RecipeSerializer<?> getSerializer() {
/* 105 */     return RecipeSerializer.BANNER_DUPLICATE;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canCraftInDimensions(int $$0, int $$1) {
/* 110 */     return ($$0 * $$1 >= 2);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\crafting\BannerDuplicateRecipe.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */