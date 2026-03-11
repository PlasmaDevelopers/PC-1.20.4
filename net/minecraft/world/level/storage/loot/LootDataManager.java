/*     */ package net.minecraft.world.level.storage.loot;
/*     */ 
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.google.common.collect.ImmutableMultimap;
/*     */ import com.google.common.collect.Multimap;
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.Executor;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.packs.resources.PreparableReloadListener;
/*     */ import net.minecraft.server.packs.resources.ResourceManager;
/*     */ import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
/*     */ import net.minecraft.util.ProblemReporter;
/*     */ import net.minecraft.util.profiling.ProfilerFiller;
/*     */ import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class LootDataManager implements PreparableReloadListener, LootDataResolver {
/*  27 */   private static final Logger LOGGER = LogUtils.getLogger();
/*  28 */   private static final Gson GSON = (new GsonBuilder()).create();
/*     */   
/*  30 */   public static final LootDataId<LootTable> EMPTY_LOOT_TABLE_KEY = new LootDataId<>(LootDataType.TABLE, BuiltInLootTables.EMPTY);
/*     */   
/*  32 */   private Map<LootDataId<?>, ?> elements = Map.of();
/*  33 */   private Multimap<LootDataType<?>, ResourceLocation> typeKeys = (Multimap<LootDataType<?>, ResourceLocation>)ImmutableMultimap.of();
/*     */ 
/*     */   
/*     */   public final CompletableFuture<Void> reload(PreparableReloadListener.PreparationBarrier $$0, ResourceManager $$1, ProfilerFiller $$2, ProfilerFiller $$3, Executor $$4, Executor $$5) {
/*  37 */     Map<LootDataType<?>, Map<ResourceLocation, ?>> $$6 = new HashMap<>();
/*  38 */     CompletableFuture[] arrayOfCompletableFuture = (CompletableFuture[])LootDataType.values().map($$3 -> scheduleElementParse($$3, $$0, $$1, $$2)).toArray($$0 -> new CompletableFuture[$$0]);
/*     */ 
/*     */     
/*  41 */     Objects.requireNonNull($$0); return CompletableFuture.allOf((CompletableFuture<?>[])arrayOfCompletableFuture).thenCompose($$0::wait)
/*  42 */       .thenAcceptAsync($$1 -> apply($$0), $$5);
/*     */   }
/*     */   
/*     */   private static <T> CompletableFuture<?> scheduleElementParse(LootDataType<T> $$0, ResourceManager $$1, Executor $$2, Map<LootDataType<?>, Map<ResourceLocation, ?>> $$3) {
/*  46 */     Map<ResourceLocation, T> $$4 = new HashMap<>();
/*  47 */     $$3.put($$0, $$4);
/*  48 */     return CompletableFuture.runAsync(() -> { Map<ResourceLocation, JsonElement> $$3 = new HashMap<>(); SimpleJsonResourceReloadListener.scanDirectory($$0, $$1.directory(), GSON, $$3); $$3.forEach(()); }$$2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void apply(Map<LootDataType<?>, Map<ResourceLocation, ?>> $$0) {
/*  56 */     Object $$1 = ((Map)$$0.get(LootDataType.TABLE)).remove(BuiltInLootTables.EMPTY);
/*  57 */     if ($$1 != null) {
/*  58 */       LOGGER.warn("Datapack tried to redefine {} loot table, ignoring", BuiltInLootTables.EMPTY);
/*     */     }
/*     */     
/*  61 */     ImmutableMap.Builder<LootDataId<?>, Object> $$2 = ImmutableMap.builder();
/*  62 */     ImmutableMultimap.Builder<LootDataType<?>, ResourceLocation> $$3 = ImmutableMultimap.builder();
/*     */     
/*  64 */     $$0.forEach(($$2, $$3) -> $$3.forEach(()));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  71 */     $$2.put(EMPTY_LOOT_TABLE_KEY, LootTable.EMPTY);
/*     */     
/*  73 */     ProblemReporter.Collector $$4 = new ProblemReporter.Collector();
/*     */     
/*  75 */     final ImmutableMap bakedElements = $$2.build();
/*  76 */     ValidationContext $$6 = new ValidationContext((ProblemReporter)$$4, LootContextParamSets.ALL_PARAMS, new LootDataResolver()
/*     */         {
/*     */           @Nullable
/*     */           public <T> T getElement(LootDataId<T> $$0)
/*     */           {
/*  81 */             return (T)bakedElements.get($$0);
/*     */           }
/*     */         });
/*  84 */     immutableMap.forEach(($$1, $$2) -> castAndValidate($$0, $$1, $$2));
/*  85 */     $$4.get().forEach(($$0, $$1) -> LOGGER.warn("Found loot table element validation problem in {}: {}", $$0, $$1));
/*     */     
/*  87 */     this.elements = (Map<LootDataId<?>, ?>)immutableMap;
/*  88 */     this.typeKeys = (Multimap<LootDataType<?>, ResourceLocation>)$$3.build();
/*     */   }
/*     */ 
/*     */   
/*     */   private static <T> void castAndValidate(ValidationContext $$0, LootDataId<T> $$1, Object $$2) {
/*  93 */     $$1.type().runValidation($$0, $$1, (T)$$2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public <T> T getElement(LootDataId<T> $$0) {
/* 100 */     return (T)this.elements.get($$0);
/*     */   }
/*     */   
/*     */   public Collection<ResourceLocation> getKeys(LootDataType<?> $$0) {
/* 104 */     return this.typeKeys.get($$0);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\LootDataManager.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */