/*     */ package net.minecraft.data.models;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import com.google.gson.JsonElement;
/*     */ import java.nio.file.Path;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Supplier;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.data.CachedOutput;
/*     */ import net.minecraft.data.DataProvider;
/*     */ import net.minecraft.data.PackOutput;
/*     */ import net.minecraft.data.models.blockstates.BlockStateGenerator;
/*     */ import net.minecraft.data.models.model.DelegatedModel;
/*     */ import net.minecraft.data.models.model.ModelLocationUtils;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ 
/*     */ public class ModelProvider
/*     */   implements DataProvider
/*     */ {
/*     */   private final PackOutput.PathProvider blockStatePathProvider;
/*     */   private final PackOutput.PathProvider modelPathProvider;
/*     */   
/*     */   public ModelProvider(PackOutput $$0) {
/*  34 */     this.blockStatePathProvider = $$0.createPathProvider(PackOutput.Target.RESOURCE_PACK, "blockstates");
/*  35 */     this.modelPathProvider = $$0.createPathProvider(PackOutput.Target.RESOURCE_PACK, "models");
/*     */   }
/*     */ 
/*     */   
/*     */   public CompletableFuture<?> run(CachedOutput $$0) {
/*  40 */     Map<Block, BlockStateGenerator> $$1 = Maps.newHashMap();
/*  41 */     Consumer<BlockStateGenerator> $$2 = $$1 -> {
/*     */         Block $$2 = $$1.getBlock();
/*     */         
/*     */         BlockStateGenerator $$3 = $$0.put($$2, $$1);
/*     */         if ($$3 != null) {
/*     */           throw new IllegalStateException("Duplicate blockstate definition for " + $$2);
/*     */         }
/*     */       };
/*  49 */     Map<ResourceLocation, Supplier<JsonElement>> $$3 = Maps.newHashMap();
/*  50 */     Set<Item> $$4 = Sets.newHashSet();
/*     */     
/*  52 */     BiConsumer<ResourceLocation, Supplier<JsonElement>> $$5 = ($$1, $$2) -> {
/*     */         Supplier<JsonElement> $$3 = $$0.put($$1, $$2);
/*     */         
/*     */         if ($$3 != null) {
/*     */           throw new IllegalStateException("Duplicate model definition for " + $$1);
/*     */         }
/*     */       };
/*  59 */     Objects.requireNonNull($$4); Consumer<Item> $$6 = $$4::add;
/*     */     
/*  61 */     (new BlockModelGenerators($$2, $$5, $$6)).run();
/*  62 */     (new ItemModelGenerators($$5)).run();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  70 */     List<Block> $$7 = BuiltInRegistries.BLOCK.entrySet().stream().filter($$0 -> true).map(Map.Entry::getValue).filter($$1 -> !$$0.containsKey($$1)).toList();
/*  71 */     if (!$$7.isEmpty()) {
/*  72 */       throw new IllegalStateException("Missing blockstate definitions for: " + $$7);
/*     */     }
/*     */     
/*  75 */     BuiltInRegistries.BLOCK.forEach($$2 -> {
/*     */           Item $$3 = (Item)Item.BY_BLOCK.get($$2);
/*     */           
/*     */           if ($$3 != null) {
/*     */             if ($$0.contains($$3)) {
/*     */               return;
/*     */             }
/*     */             ResourceLocation $$4 = ModelLocationUtils.getModelLocation($$3);
/*     */             if (!$$1.containsKey($$4)) {
/*     */               $$1.put($$4, new DelegatedModel(ModelLocationUtils.getModelLocation($$2)));
/*     */             }
/*     */           } 
/*     */         });
/*  88 */     (new CompletableFuture[2])[0] = 
/*  89 */       saveCollection($$0, (Map)$$1, $$0 -> this.blockStatePathProvider.json($$0.builtInRegistryHolder().key().location()));
/*  90 */     Objects.requireNonNull(this.modelPathProvider); return CompletableFuture.allOf((CompletableFuture<?>[])new CompletableFuture[] { null, saveCollection($$0, $$3, this.modelPathProvider::json) });
/*     */   }
/*     */ 
/*     */   
/*     */   private <T> CompletableFuture<?> saveCollection(CachedOutput $$0, Map<T, ? extends Supplier<JsonElement>> $$1, Function<T, Path> $$2) {
/*  95 */     return CompletableFuture.allOf((CompletableFuture<?>[])$$1.entrySet().stream()
/*  96 */         .map($$2 -> {
/*     */             Path $$3 = (Path)$$0.apply($$2.getKey());
/*     */             
/*     */             JsonElement $$4 = ((Supplier<JsonElement>)$$2.getValue()).get();
/*     */             return DataProvider.saveStable($$1, $$4, $$3);
/* 101 */           }).toArray($$0 -> new CompletableFuture[$$0]));
/*     */   }
/*     */ 
/*     */   
/*     */   public final String getName() {
/* 106 */     return "Model Definitions";
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\models\ModelProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */