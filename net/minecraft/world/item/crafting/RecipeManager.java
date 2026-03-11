/*     */ package net.minecraft.world.item.crafting;
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.serialization.DynamicOps;
/*     */ import com.mojang.serialization.JsonOps;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import java.util.function.Function;
/*     */ import java.util.stream.Collectors;
/*     */ import java.util.stream.Stream;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.NonNullList;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.packs.resources.ResourceManager;
/*     */ import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
/*     */ import net.minecraft.util.GsonHelper;
/*     */ import net.minecraft.util.profiling.ProfilerFiller;
/*     */ import net.minecraft.world.Container;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.Level;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class RecipeManager extends SimpleJsonResourceReloadListener {
/*  36 */   private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
/*  37 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*  39 */   private Map<RecipeType<?>, Map<ResourceLocation, RecipeHolder<?>>> recipes = (Map<RecipeType<?>, Map<ResourceLocation, RecipeHolder<?>>>)ImmutableMap.of();
/*  40 */   private Map<ResourceLocation, RecipeHolder<?>> byName = (Map<ResourceLocation, RecipeHolder<?>>)ImmutableMap.of();
/*     */   private boolean hasErrors;
/*     */   
/*     */   public RecipeManager() {
/*  44 */     super(GSON, "recipes");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void apply(Map<ResourceLocation, JsonElement> $$0, ResourceManager $$1, ProfilerFiller $$2) {
/*  49 */     this.hasErrors = false;
/*  50 */     Map<RecipeType<?>, ImmutableMap.Builder<ResourceLocation, RecipeHolder<?>>> $$3 = Maps.newHashMap();
/*  51 */     ImmutableMap.Builder<ResourceLocation, RecipeHolder<?>> $$4 = ImmutableMap.builder();
/*     */     
/*  53 */     for (Map.Entry<ResourceLocation, JsonElement> $$5 : $$0.entrySet()) {
/*  54 */       ResourceLocation $$6 = $$5.getKey();
/*     */       try {
/*  56 */         RecipeHolder<?> $$7 = fromJson($$6, GsonHelper.convertToJsonObject($$5.getValue(), "top element"));
/*  57 */         ((ImmutableMap.Builder)$$3.computeIfAbsent($$7.value().getType(), $$0 -> ImmutableMap.builder())).put($$6, $$7);
/*  58 */         $$4.put($$6, $$7);
/*  59 */       } catch (JsonParseException|IllegalArgumentException $$8) {
/*  60 */         LOGGER.error("Parsing error loading recipe {}", $$6, $$8);
/*     */       } 
/*     */     } 
/*     */     
/*  64 */     this.recipes = (Map<RecipeType<?>, Map<ResourceLocation, RecipeHolder<?>>>)$$3.entrySet().stream().collect(ImmutableMap.toImmutableMap(Map.Entry::getKey, $$0 -> ((ImmutableMap.Builder)$$0.getValue()).build()));
/*  65 */     this.byName = (Map<ResourceLocation, RecipeHolder<?>>)$$4.build();
/*  66 */     LOGGER.info("Loaded {} recipes", Integer.valueOf($$3.size()));
/*     */   }
/*     */   
/*     */   public boolean hadErrorsLoading() {
/*  70 */     return this.hasErrors;
/*     */   }
/*     */   
/*     */   public <C extends Container, T extends Recipe<C>> Optional<RecipeHolder<T>> getRecipeFor(RecipeType<T> $$0, C $$1, Level $$2) {
/*  74 */     return byType($$0).values().stream().filter($$2 -> $$2.value().matches($$0, $$1)).findFirst();
/*     */   }
/*     */   
/*     */   public <C extends Container, T extends Recipe<C>> Optional<Pair<ResourceLocation, RecipeHolder<T>>> getRecipeFor(RecipeType<T> $$0, C $$1, Level $$2, @Nullable ResourceLocation $$3) {
/*  78 */     Map<ResourceLocation, RecipeHolder<T>> $$4 = byType($$0);
/*  79 */     if ($$3 != null) {
/*  80 */       RecipeHolder<T> $$5 = $$4.get($$3);
/*  81 */       if ($$5 != null && $$5.value().matches($$1, $$2)) {
/*  82 */         return Optional.of(Pair.of($$3, $$5));
/*     */       }
/*     */     } 
/*  85 */     return $$4.entrySet().stream().filter($$2 -> ((RecipeHolder)$$2.getValue()).value().matches($$0, $$1)).findFirst().map($$0 -> Pair.of($$0.getKey(), $$0.getValue()));
/*     */   }
/*     */   
/*     */   public <C extends Container, T extends Recipe<C>> List<RecipeHolder<T>> getAllRecipesFor(RecipeType<T> $$0) {
/*  89 */     return List.copyOf(byType($$0).values());
/*     */   }
/*     */   
/*     */   public <C extends Container, T extends Recipe<C>> List<RecipeHolder<T>> getRecipesFor(RecipeType<T> $$0, C $$1, Level $$2) {
/*  93 */     return (List<RecipeHolder<T>>)byType($$0).values()
/*  94 */       .stream()
/*  95 */       .filter($$2 -> $$2.value().matches($$0, $$1))
/*  96 */       .sorted(Comparator.comparing($$1 -> $$1.value().getResultItem($$0.registryAccess()).getDescriptionId()))
/*  97 */       .collect(Collectors.toList());
/*     */   }
/*     */ 
/*     */   
/*     */   private <C extends Container, T extends Recipe<C>> Map<ResourceLocation, RecipeHolder<T>> byType(RecipeType<T> $$0) {
/* 102 */     return (Map<ResourceLocation, RecipeHolder<T>>)this.recipes.getOrDefault($$0, Collections.emptyMap());
/*     */   }
/*     */   
/*     */   public <C extends Container, T extends Recipe<C>> NonNullList<ItemStack> getRemainingItemsFor(RecipeType<T> $$0, C $$1, Level $$2) {
/* 106 */     Optional<RecipeHolder<T>> $$3 = getRecipeFor($$0, $$1, $$2);
/* 107 */     if ($$3.isPresent()) {
/* 108 */       return ((RecipeHolder)$$3.get()).value().getRemainingItems($$1);
/*     */     }
/*     */     
/* 111 */     NonNullList<ItemStack> $$4 = NonNullList.withSize($$1.getContainerSize(), ItemStack.EMPTY);
/* 112 */     for (int $$5 = 0; $$5 < $$4.size(); $$5++) {
/* 113 */       $$4.set($$5, $$1.getItem($$5));
/*     */     }
/* 115 */     return $$4;
/*     */   }
/*     */   
/*     */   public Optional<RecipeHolder<?>> byKey(ResourceLocation $$0) {
/* 119 */     return Optional.ofNullable(this.byName.get($$0));
/*     */   }
/*     */   
/*     */   public Collection<RecipeHolder<?>> getRecipes() {
/* 123 */     return (Collection<RecipeHolder<?>>)this.recipes.values().stream().flatMap($$0 -> $$0.values().stream()).collect(Collectors.toSet());
/*     */   }
/*     */   
/*     */   public Stream<ResourceLocation> getRecipeIds() {
/* 127 */     return this.recipes.values().stream().flatMap($$0 -> $$0.keySet().stream());
/*     */   }
/*     */   
/*     */   protected static RecipeHolder<?> fromJson(ResourceLocation $$0, JsonObject $$1) {
/* 131 */     Recipe<?> $$2 = (Recipe)Util.getOrThrow(Recipe.CODEC.parse((DynamicOps)JsonOps.INSTANCE, $$1), JsonParseException::new);
/* 132 */     return new RecipeHolder($$0, $$2);
/*     */   }
/*     */   
/*     */   public void replaceRecipes(Iterable<RecipeHolder<?>> $$0) {
/* 136 */     this.hasErrors = false;
/*     */     
/* 138 */     Map<RecipeType<?>, Map<ResourceLocation, RecipeHolder<?>>> $$1 = Maps.newHashMap();
/* 139 */     ImmutableMap.Builder<ResourceLocation, RecipeHolder<?>> $$2 = ImmutableMap.builder();
/* 140 */     $$0.forEach($$2 -> {
/*     */           Map<ResourceLocation, RecipeHolder<?>> $$3 = $$0.computeIfAbsent($$2.value().getType(), ());
/*     */           
/*     */           ResourceLocation $$4 = $$2.id();
/*     */           RecipeHolder<?> $$5 = $$3.put($$4, $$2);
/*     */           $$1.put($$4, $$2);
/*     */           if ($$5 != null) {
/*     */             throw new IllegalStateException("Duplicate recipe ignored with ID " + $$4);
/*     */           }
/*     */         });
/* 150 */     this.recipes = (Map<RecipeType<?>, Map<ResourceLocation, RecipeHolder<?>>>)ImmutableMap.copyOf($$1);
/* 151 */     this.byName = (Map<ResourceLocation, RecipeHolder<?>>)$$2.build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <C extends Container, T extends Recipe<C>> CachedCheck<C, T> createCheck(final RecipeType<T> type) {
/* 159 */     return new CachedCheck<C, T>()
/*     */       {
/*     */         @Nullable
/*     */         private ResourceLocation lastRecipe;
/*     */         
/*     */         public Optional<RecipeHolder<T>> getRecipeFor(C $$0, Level $$1) {
/* 165 */           RecipeManager $$2 = $$1.getRecipeManager();
/* 166 */           Optional<Pair<ResourceLocation, RecipeHolder<T>>> $$3 = (Optional)$$2.getRecipeFor(type, (Container)$$0, $$1, this.lastRecipe);
/* 167 */           if ($$3.isPresent()) {
/* 168 */             Pair<ResourceLocation, RecipeHolder<T>> $$4 = $$3.get();
/* 169 */             this.lastRecipe = (ResourceLocation)$$4.getFirst();
/* 170 */             return Optional.of((RecipeHolder<T>)$$4.getSecond());
/*     */           } 
/* 172 */           return Optional.empty();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public static interface CachedCheck<C extends Container, T extends Recipe<C>> {
/*     */     Optional<RecipeHolder<T>> getRecipeFor(C param1C, Level param1Level);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\crafting\RecipeManager.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */