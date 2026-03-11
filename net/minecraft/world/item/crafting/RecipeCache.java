/*    */ package net.minecraft.world.item.crafting;
/*    */ 
/*    */ import java.lang.ref.WeakReference;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import java.util.Optional;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.NonNullList;
/*    */ import net.minecraft.world.inventory.CraftingContainer;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.Level;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RecipeCache
/*    */ {
/*    */   private final Entry[] entries;
/* 21 */   private WeakReference<RecipeManager> cachedRecipeManager = new WeakReference<>(null);
/*    */   
/*    */   public RecipeCache(int $$0) {
/* 24 */     this.entries = new Entry[$$0];
/*    */   }
/*    */   
/*    */   public Optional<CraftingRecipe> get(Level $$0, CraftingContainer $$1) {
/* 28 */     if ($$1.isEmpty()) {
/* 29 */       return Optional.empty();
/*    */     }
/*    */     
/* 32 */     validateRecipeManager($$0);
/*    */     
/* 34 */     for (int $$2 = 0; $$2 < this.entries.length; $$2++) {
/* 35 */       Entry $$3 = this.entries[$$2];
/* 36 */       if ($$3 != null && $$3.matches($$1.getItems())) {
/* 37 */         moveEntryToFront($$2);
/* 38 */         return Optional.ofNullable($$3.value());
/*    */       } 
/*    */     } 
/* 41 */     return compute($$1, $$0);
/*    */   }
/*    */   
/*    */   private void validateRecipeManager(Level $$0) {
/* 45 */     RecipeManager $$1 = $$0.getRecipeManager();
/* 46 */     if ($$1 != this.cachedRecipeManager.get()) {
/*    */       
/* 48 */       this.cachedRecipeManager = new WeakReference<>($$1);
/* 49 */       Arrays.fill((Object[])this.entries, (Object)null);
/*    */     } 
/*    */   }
/*    */   private Optional<CraftingRecipe> compute(CraftingContainer $$0, Level $$1) {
/* 53 */     Optional<RecipeHolder<CraftingRecipe>> $$2 = $$1.getRecipeManager().getRecipeFor(RecipeType.CRAFTING, $$0, $$1);
/* 54 */     insert($$0.getItems(), $$2.<CraftingRecipe>map(RecipeHolder::value).orElse(null));
/* 55 */     return $$2.map(RecipeHolder::value);
/*    */   }
/*    */   
/*    */   private void moveEntryToFront(int $$0) {
/* 59 */     if ($$0 > 0) {
/* 60 */       Entry $$1 = this.entries[$$0];
/* 61 */       System.arraycopy(this.entries, 0, this.entries, 1, $$0);
/* 62 */       this.entries[0] = $$1;
/*    */     } 
/*    */   }
/*    */   
/*    */   private void insert(List<ItemStack> $$0, @Nullable CraftingRecipe $$1) {
/* 67 */     NonNullList<ItemStack> $$2 = NonNullList.withSize($$0.size(), ItemStack.EMPTY);
/* 68 */     for (int $$3 = 0; $$3 < $$0.size(); $$3++) {
/* 69 */       $$2.set($$3, ((ItemStack)$$0.get($$3)).copyWithCount(1));
/*    */     }
/* 71 */     System.arraycopy(this.entries, 0, this.entries, 1, this.entries.length - 1);
/* 72 */     this.entries[0] = new Entry($$2, $$1);
/*    */   } private static final class Entry extends Record { private final NonNullList<ItemStack> key; @Nullable
/*    */     private final CraftingRecipe value;
/* 75 */     Entry(NonNullList<ItemStack> $$0, @Nullable CraftingRecipe $$1) { this.key = $$0; this.value = $$1; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/item/crafting/RecipeCache$Entry;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #75	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/* 75 */       //   0	7	0	this	Lnet/minecraft/world/item/crafting/RecipeCache$Entry; } public NonNullList<ItemStack> key() { return this.key; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/item/crafting/RecipeCache$Entry;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #75	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/world/item/crafting/RecipeCache$Entry; } public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/item/crafting/RecipeCache$Entry;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #75	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/world/item/crafting/RecipeCache$Entry;
/* 75 */       //   0	8	1	$$0	Ljava/lang/Object; } @Nullable public CraftingRecipe value() { return this.value; }
/*    */      public boolean matches(List<ItemStack> $$0) {
/* 77 */       if (this.key.size() != $$0.size()) {
/* 78 */         return false;
/*    */       }
/* 80 */       for (int $$1 = 0; $$1 < this.key.size(); $$1++) {
/* 81 */         if (!ItemStack.isSameItemSameTags((ItemStack)this.key.get($$1), $$0.get($$1))) {
/* 82 */           return false;
/*    */         }
/*    */       } 
/* 85 */       return true;
/*    */     } }
/*    */ 
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\crafting\RecipeCache.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */