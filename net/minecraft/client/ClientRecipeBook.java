/*     */ package net.minecraft.client;
/*     */ import com.google.common.collect.HashBasedTable;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.stream.Stream;
/*     */ import net.minecraft.client.gui.screens.recipebook.RecipeCollection;
/*     */ import net.minecraft.core.RegistryAccess;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.stats.RecipeBook;
/*     */ import net.minecraft.world.item.crafting.AbstractCookingRecipe;
/*     */ import net.minecraft.world.item.crafting.CookingBookCategory;
/*     */ import net.minecraft.world.item.crafting.CraftingBookCategory;
/*     */ import net.minecraft.world.item.crafting.CraftingRecipe;
/*     */ import net.minecraft.world.item.crafting.Recipe;
/*     */ import net.minecraft.world.item.crafting.RecipeHolder;
/*     */ import net.minecraft.world.item.crafting.RecipeType;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class ClientRecipeBook extends RecipeBook {
/*  27 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*  29 */   private Map<RecipeBookCategories, List<RecipeCollection>> collectionsByTab = (Map<RecipeBookCategories, List<RecipeCollection>>)ImmutableMap.of();
/*  30 */   private List<RecipeCollection> allCollections = (List<RecipeCollection>)ImmutableList.of();
/*     */   
/*     */   public void setupCollections(Iterable<RecipeHolder<?>> $$0, RegistryAccess $$1) {
/*  33 */     Map<RecipeBookCategories, List<List<RecipeHolder<?>>>> $$2 = categorizeAndGroupRecipes($$0);
/*     */     
/*  35 */     Map<RecipeBookCategories, List<RecipeCollection>> $$3 = Maps.newHashMap();
/*  36 */     ImmutableList.Builder<RecipeCollection> $$4 = ImmutableList.builder();
/*     */     
/*  38 */     $$2.forEach(($$3, $$4) -> {
/*     */           Objects.requireNonNull($$2);
/*     */           $$0.put($$3, (List)$$4.stream().map(()).peek($$2::add).collect(ImmutableList.toImmutableList()));
/*     */         });
/*  42 */     RecipeBookCategories.AGGREGATE_CATEGORIES.forEach(($$1, $$2) -> $$0.put($$1, (List)$$2.stream().flatMap(()).collect(ImmutableList.toImmutableList())));
/*     */ 
/*     */ 
/*     */     
/*  46 */     this.collectionsByTab = (Map<RecipeBookCategories, List<RecipeCollection>>)ImmutableMap.copyOf($$3);
/*  47 */     this.allCollections = (List<RecipeCollection>)$$4.build();
/*     */   }
/*     */   
/*     */   private static Map<RecipeBookCategories, List<List<RecipeHolder<?>>>> categorizeAndGroupRecipes(Iterable<RecipeHolder<?>> $$0) {
/*  51 */     Map<RecipeBookCategories, List<List<RecipeHolder<?>>>> $$1 = Maps.newHashMap();
/*  52 */     HashBasedTable hashBasedTable = HashBasedTable.create();
/*     */     
/*  54 */     for (RecipeHolder<?> $$3 : $$0) {
/*  55 */       Recipe<?> $$4 = $$3.value();
/*  56 */       if ($$4.isSpecial() || $$4.isIncomplete()) {
/*     */         continue;
/*     */       }
/*     */       
/*  60 */       RecipeBookCategories $$5 = getCategory($$3);
/*  61 */       String $$6 = $$4.getGroup();
/*     */       
/*  63 */       if ($$6.isEmpty()) {
/*     */         
/*  65 */         ((List<ImmutableList>)$$1.computeIfAbsent($$5, $$0 -> Lists.newArrayList())).add(ImmutableList.of($$3)); continue;
/*     */       } 
/*  67 */       List<RecipeHolder<?>> $$7 = (List<RecipeHolder<?>>)hashBasedTable.get($$5, $$6);
/*  68 */       if ($$7 == null) {
/*  69 */         $$7 = Lists.newArrayList();
/*  70 */         hashBasedTable.put($$5, $$6, $$7);
/*  71 */         ((List<List<RecipeHolder<?>>>)$$1.computeIfAbsent($$5, $$0 -> Lists.newArrayList())).add($$7);
/*     */       } 
/*  73 */       $$7.add($$3);
/*     */     } 
/*     */     
/*  76 */     return $$1;
/*     */   }
/*     */   
/*     */   private static RecipeBookCategories getCategory(RecipeHolder<?> $$0) {
/*  80 */     Recipe<?> $$1 = $$0.value();
/*  81 */     if ($$1 instanceof CraftingRecipe) { CraftingRecipe $$2 = (CraftingRecipe)$$1;
/*  82 */       switch ($$2.category()) { default: throw new IncompatibleClassChangeError();case BLOCKS: case FOOD: case MISC: case null: break; }  return 
/*     */ 
/*     */ 
/*     */         
/*  86 */         RecipeBookCategories.CRAFTING_MISC; }
/*     */ 
/*     */ 
/*     */     
/*  90 */     RecipeType<?> $$3 = $$1.getType();
/*  91 */     if ($$1 instanceof AbstractCookingRecipe) { AbstractCookingRecipe $$4 = (AbstractCookingRecipe)$$1;
/*  92 */       CookingBookCategory $$5 = $$4.category();
/*  93 */       if ($$3 == RecipeType.SMELTING) {
/*  94 */         switch ($$5) { default: throw new IncompatibleClassChangeError();case BLOCKS: case FOOD: case MISC: break; }  return 
/*     */ 
/*     */           
/*  97 */           RecipeBookCategories.FURNACE_MISC;
/*     */       } 
/*     */ 
/*     */       
/* 101 */       if ($$3 == RecipeType.BLASTING) {
/* 102 */         return ($$5 == CookingBookCategory.BLOCKS) ? RecipeBookCategories.BLAST_FURNACE_BLOCKS : RecipeBookCategories.BLAST_FURNACE_MISC;
/*     */       }
/* 104 */       if ($$3 == RecipeType.SMOKING) {
/* 105 */         return RecipeBookCategories.SMOKER_FOOD;
/*     */       }
/* 107 */       if ($$3 == RecipeType.CAMPFIRE_COOKING) {
/* 108 */         return RecipeBookCategories.CAMPFIRE;
/*     */       } }
/*     */ 
/*     */     
/* 112 */     if ($$3 == RecipeType.STONECUTTING) {
/* 113 */       return RecipeBookCategories.STONECUTTER;
/*     */     }
/* 115 */     if ($$3 == RecipeType.SMITHING) {
/* 116 */       return RecipeBookCategories.SMITHING;
/*     */     }
/*     */     
/* 119 */     Objects.requireNonNull($$0); LOGGER.warn("Unknown recipe category: {}/{}", LogUtils.defer(() -> BuiltInRegistries.RECIPE_TYPE.getKey($$0.getType())), LogUtils.defer($$0::id));
/* 120 */     return RecipeBookCategories.UNKNOWN;
/*     */   }
/*     */   
/*     */   public List<RecipeCollection> getCollections() {
/* 124 */     return this.allCollections;
/*     */   }
/*     */   
/*     */   public List<RecipeCollection> getCollection(RecipeBookCategories $$0) {
/* 128 */     return this.collectionsByTab.getOrDefault($$0, Collections.emptyList());
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\ClientRecipeBook.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */