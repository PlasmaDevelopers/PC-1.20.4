/*     */ package net.minecraft.client.gui.screens.recipebook;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.minecraft.core.RegistryAccess;
/*     */ import net.minecraft.stats.RecipeBook;
/*     */ import net.minecraft.world.entity.player.StackedContents;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.crafting.RecipeHolder;
/*     */ 
/*     */ 
/*     */ public class RecipeCollection
/*     */ {
/*     */   private final RegistryAccess registryAccess;
/*     */   private final List<RecipeHolder<?>> recipes;
/*     */   private final boolean singleResultItem;
/*  20 */   private final Set<RecipeHolder<?>> craftable = Sets.newHashSet();
/*  21 */   private final Set<RecipeHolder<?>> fitsDimensions = Sets.newHashSet();
/*  22 */   private final Set<RecipeHolder<?>> known = Sets.newHashSet();
/*     */   
/*     */   public RecipeCollection(RegistryAccess $$0, List<RecipeHolder<?>> $$1) {
/*  25 */     this.registryAccess = $$0;
/*  26 */     this.recipes = (List<RecipeHolder<?>>)ImmutableList.copyOf($$1);
/*     */     
/*  28 */     if ($$1.size() <= 1) {
/*  29 */       this.singleResultItem = true;
/*     */     } else {
/*  31 */       this.singleResultItem = allRecipesHaveSameResult($$0, $$1);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static boolean allRecipesHaveSameResult(RegistryAccess $$0, List<RecipeHolder<?>> $$1) {
/*  36 */     int $$2 = $$1.size();
/*  37 */     ItemStack $$3 = ((RecipeHolder)$$1.get(0)).value().getResultItem($$0);
/*  38 */     for (int $$4 = 1; $$4 < $$2; $$4++) {
/*  39 */       ItemStack $$5 = ((RecipeHolder)$$1.get($$4)).value().getResultItem($$0);
/*  40 */       if (!ItemStack.isSameItemSameTags($$3, $$5)) {
/*  41 */         return false;
/*     */       }
/*     */     } 
/*  44 */     return true;
/*     */   }
/*     */   
/*     */   public RegistryAccess registryAccess() {
/*  48 */     return this.registryAccess;
/*     */   }
/*     */   
/*     */   public boolean hasKnownRecipes() {
/*  52 */     return !this.known.isEmpty();
/*     */   }
/*     */   
/*     */   public void updateKnownRecipes(RecipeBook $$0) {
/*  56 */     for (RecipeHolder<?> $$1 : this.recipes) {
/*  57 */       if ($$0.contains($$1)) {
/*  58 */         this.known.add($$1);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void canCraft(StackedContents $$0, int $$1, int $$2, RecipeBook $$3) {
/*  64 */     for (RecipeHolder<?> $$4 : this.recipes) {
/*  65 */       boolean $$5 = ($$4.value().canCraftInDimensions($$1, $$2) && $$3.contains($$4));
/*  66 */       if ($$5) {
/*  67 */         this.fitsDimensions.add($$4);
/*     */       } else {
/*  69 */         this.fitsDimensions.remove($$4);
/*     */       } 
/*  71 */       if ($$5 && $$0.canCraft($$4.value(), null)) {
/*  72 */         this.craftable.add($$4); continue;
/*     */       } 
/*  74 */       this.craftable.remove($$4);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCraftable(RecipeHolder<?> $$0) {
/*  80 */     return this.craftable.contains($$0);
/*     */   }
/*     */   
/*     */   public boolean hasCraftable() {
/*  84 */     return !this.craftable.isEmpty();
/*     */   }
/*     */   
/*     */   public boolean hasFitting() {
/*  88 */     return !this.fitsDimensions.isEmpty();
/*     */   }
/*     */   
/*     */   public List<RecipeHolder<?>> getRecipes() {
/*  92 */     return this.recipes;
/*     */   }
/*     */   
/*     */   public List<RecipeHolder<?>> getRecipes(boolean $$0) {
/*  96 */     List<RecipeHolder<?>> $$1 = Lists.newArrayList();
/*  97 */     Set<RecipeHolder<?>> $$2 = $$0 ? this.craftable : this.fitsDimensions;
/*     */     
/*  99 */     for (RecipeHolder<?> $$3 : this.recipes) {
/* 100 */       if ($$2.contains($$3)) {
/* 101 */         $$1.add($$3);
/*     */       }
/*     */     } 
/*     */     
/* 105 */     return $$1;
/*     */   }
/*     */   
/*     */   public List<RecipeHolder<?>> getDisplayRecipes(boolean $$0) {
/* 109 */     List<RecipeHolder<?>> $$1 = Lists.newArrayList();
/*     */     
/* 111 */     for (RecipeHolder<?> $$2 : this.recipes) {
/* 112 */       if (this.fitsDimensions.contains($$2) && this.craftable.contains($$2) == $$0) {
/* 113 */         $$1.add($$2);
/*     */       }
/*     */     } 
/*     */     
/* 117 */     return $$1;
/*     */   }
/*     */   
/*     */   public boolean hasSingleResultItem() {
/* 121 */     return this.singleResultItem;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\recipebook\RecipeCollection.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */