/*     */ package net.minecraft.client.resources.model;
/*     */ import com.google.common.collect.HashMultimap;
/*     */ import com.google.common.collect.Multimap;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*     */ import java.io.Reader;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.CompletionStage;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.stream.Collectors;
/*     */ import java.util.stream.Stream;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.client.color.block.BlockColors;
/*     */ import net.minecraft.client.renderer.Sheets;
/*     */ import net.minecraft.client.renderer.block.BlockModelShaper;
/*     */ import net.minecraft.client.renderer.block.model.BlockModel;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlas;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.packs.resources.PreparableReloadListener;
/*     */ import net.minecraft.server.packs.resources.Resource;
/*     */ import net.minecraft.server.packs.resources.ResourceManager;
/*     */ import net.minecraft.util.GsonHelper;
/*     */ import net.minecraft.util.profiling.ProfilerFiller;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class ModelManager implements PreparableReloadListener, AutoCloseable {
/*  41 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*  43 */   private static final Map<ResourceLocation, ResourceLocation> VANILLA_ATLASES = Map.of(Sheets.BANNER_SHEET, new ResourceLocation("banner_patterns"), Sheets.BED_SHEET, new ResourceLocation("beds"), Sheets.CHEST_SHEET, new ResourceLocation("chests"), Sheets.SHIELD_SHEET, new ResourceLocation("shield_patterns"), Sheets.SIGN_SHEET, new ResourceLocation("signs"), Sheets.SHULKER_SHEET, new ResourceLocation("shulker_boxes"), Sheets.ARMOR_TRIMS_SHEET, new ResourceLocation("armor_trims"), Sheets.DECORATED_POT_SHEET, new ResourceLocation("decorated_pot"), TextureAtlas.LOCATION_BLOCKS, new ResourceLocation("blocks"));
/*     */ 
/*     */   
/*     */   private Map<ResourceLocation, BakedModel> bakedRegistry;
/*     */ 
/*     */   
/*     */   private final AtlasSet atlases;
/*     */ 
/*     */   
/*     */   private final BlockModelShaper blockModelShaper;
/*     */   
/*     */   private final BlockColors blockColors;
/*     */   
/*     */   private int maxMipmapLevels;
/*     */   
/*     */   private BakedModel missingModel;
/*     */   
/*     */   private Object2IntMap<BlockState> modelGroups;
/*     */ 
/*     */   
/*     */   public ModelManager(TextureManager $$0, BlockColors $$1, int $$2) {
/*  64 */     this.blockColors = $$1;
/*  65 */     this.maxMipmapLevels = $$2;
/*  66 */     this.blockModelShaper = new BlockModelShaper(this);
/*  67 */     this.atlases = new AtlasSet(VANILLA_ATLASES, $$0);
/*     */   }
/*     */   
/*     */   public BakedModel getModel(ModelResourceLocation $$0) {
/*  71 */     return this.bakedRegistry.getOrDefault($$0, this.missingModel);
/*     */   }
/*     */   
/*     */   public BakedModel getMissingModel() {
/*  75 */     return this.missingModel;
/*     */   }
/*     */   
/*     */   public BlockModelShaper getBlockModelShaper() {
/*  79 */     return this.blockModelShaper;
/*     */   }
/*     */ 
/*     */   
/*     */   public final CompletableFuture<Void> reload(PreparableReloadListener.PreparationBarrier $$0, ResourceManager $$1, ProfilerFiller $$2, ProfilerFiller $$3, Executor $$4, Executor $$5) {
/*  84 */     $$2.startTick();
/*  85 */     CompletableFuture<Map<ResourceLocation, BlockModel>> $$6 = loadBlockModels($$1, $$4);
/*  86 */     CompletableFuture<Map<ResourceLocation, List<ModelBakery.LoadedJson>>> $$7 = loadBlockStates($$1, $$4);
/*  87 */     CompletableFuture<ModelBakery> $$8 = $$6.thenCombineAsync($$7, ($$1, $$2) -> new ModelBakery(this.blockColors, $$0, $$1, $$2), $$4);
/*     */     
/*  89 */     Map<ResourceLocation, CompletableFuture<AtlasSet.StitchResult>> $$9 = this.atlases.scheduleLoad($$1, this.maxMipmapLevels, $$4);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  94 */     Objects.requireNonNull($$0); return CompletableFuture.allOf((CompletableFuture<?>[])Stream.concat($$9.values().stream(), Stream.of($$8)).toArray($$0 -> new CompletableFuture[$$0])).thenApplyAsync($$3 -> loadModels($$0, (Map<ResourceLocation, AtlasSet.StitchResult>)$$1.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, ())), $$2.join()), $$4).thenCompose($$0 -> $$0.readyForUpload.thenApply(())).thenCompose($$0::wait)
/*  95 */       .thenAcceptAsync($$1 -> apply($$1, $$0), $$5);
/*     */   }
/*     */   
/*     */   private static CompletableFuture<Map<ResourceLocation, BlockModel>> loadBlockModels(ResourceManager $$0, Executor $$1) {
/*  99 */     return CompletableFuture.supplyAsync(() -> ModelBakery.MODEL_LISTER.listMatchingResources($$0), $$1)
/* 100 */       .thenCompose($$1 -> {
/*     */           List<CompletableFuture<Pair<ResourceLocation, BlockModel>>> $$2 = new ArrayList<>($$1.size());
/*     */           for (Map.Entry<ResourceLocation, Resource> $$3 : (Iterable<Map.Entry<ResourceLocation, Resource>>)$$1.entrySet()) {
/*     */             $$2.add(CompletableFuture.supplyAsync((), $$0));
/*     */           }
/*     */           return Util.sequence($$2).thenApply(());
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static CompletableFuture<Map<ResourceLocation, List<ModelBakery.LoadedJson>>> loadBlockStates(ResourceManager $$0, Executor $$1) {
/* 117 */     return CompletableFuture.supplyAsync(() -> ModelBakery.BLOCKSTATE_LISTER.listMatchingResourceStacks($$0), $$1)
/* 118 */       .thenCompose($$1 -> {
/*     */           List<CompletableFuture<Pair<ResourceLocation, List<ModelBakery.LoadedJson>>>> $$2 = new ArrayList<>($$1.size());
/*     */           for (Map.Entry<ResourceLocation, List<Resource>> $$3 : (Iterable<Map.Entry<ResourceLocation, List<Resource>>>)$$1.entrySet()) {
/*     */             $$2.add(CompletableFuture.supplyAsync((), $$0));
/*     */           }
/*     */           return Util.sequence($$2).thenApply(());
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ReloadState loadModels(ProfilerFiller $$0, Map<ResourceLocation, AtlasSet.StitchResult> $$1, ModelBakery $$2) {
/* 140 */     $$0.push("load");
/*     */     
/* 142 */     $$0.popPush("baking");
/* 143 */     HashMultimap hashMultimap = HashMultimap.create();
/*     */     
/* 145 */     $$2.bakeModels(($$2, $$3) -> {
/*     */           AtlasSet.StitchResult $$4 = (AtlasSet.StitchResult)$$0.get($$3.atlasLocation());
/*     */           
/*     */           TextureAtlasSprite $$5 = $$4.getSprite($$3.texture());
/*     */           if ($$5 != null) {
/*     */             return $$5;
/*     */           }
/*     */           $$1.put($$2, $$3);
/*     */           return $$4.missing();
/*     */         });
/* 155 */     hashMultimap.asMap().forEach(($$0, $$1) -> LOGGER.warn("Missing textures in model {}:\n{}", $$0, $$1.stream().sorted(Material.COMPARATOR).map(()).collect(Collectors.joining("\n"))));
/*     */ 
/*     */ 
/*     */     
/* 159 */     $$0.popPush("dispatch");
/* 160 */     Map<ResourceLocation, BakedModel> $$4 = $$2.getBakedTopLevelModels();
/* 161 */     BakedModel $$5 = $$4.get(ModelBakery.MISSING_MODEL_LOCATION);
/*     */     
/* 163 */     Map<BlockState, BakedModel> $$6 = new IdentityHashMap<>();
/* 164 */     for (Block $$7 : BuiltInRegistries.BLOCK) {
/* 165 */       $$7.getStateDefinition().getPossibleStates().forEach($$3 -> {
/*     */             ResourceLocation $$4 = $$3.getBlock().builtInRegistryHolder().key().location();
/*     */             
/*     */             BakedModel $$5 = (BakedModel)$$0.getOrDefault(BlockModelShaper.stateToModelLocation($$4, $$3), $$1);
/*     */             $$2.put($$3, $$5);
/*     */           });
/*     */     } 
/* 172 */     CompletableFuture<Void> $$8 = CompletableFuture.allOf((CompletableFuture<?>[])$$1.values().stream().map(AtlasSet.StitchResult::readyForUpload).toArray($$0 -> new CompletableFuture[$$0]));
/* 173 */     $$0.pop();
/* 174 */     $$0.endTick();
/* 175 */     return new ReloadState($$2, $$5, $$6, $$1, $$8);
/*     */   }
/*     */   
/*     */   private void apply(ReloadState $$0, ProfilerFiller $$1) {
/* 179 */     $$1.startTick();
/*     */     
/* 181 */     $$1.push("upload");
/* 182 */     $$0.atlasPreparations.values().forEach(AtlasSet.StitchResult::upload);
/*     */     
/* 184 */     ModelBakery $$2 = $$0.modelBakery;
/* 185 */     this.bakedRegistry = $$2.getBakedTopLevelModels();
/* 186 */     this.modelGroups = $$2.getModelGroups();
/* 187 */     this.missingModel = $$0.missingModel;
/* 188 */     $$1.popPush("cache");
/* 189 */     this.blockModelShaper.replaceCache($$0.modelCache);
/*     */     
/* 191 */     $$1.pop();
/* 192 */     $$1.endTick();
/*     */   }
/*     */   
/*     */   public boolean requiresRender(BlockState $$0, BlockState $$1) {
/* 196 */     if ($$0 == $$1) {
/* 197 */       return false;
/*     */     }
/* 199 */     int $$2 = this.modelGroups.getInt($$0);
/* 200 */     if ($$2 != -1) {
/* 201 */       int $$3 = this.modelGroups.getInt($$1);
/* 202 */       if ($$2 == $$3) {
/* 203 */         FluidState $$4 = $$0.getFluidState();
/* 204 */         FluidState $$5 = $$1.getFluidState();
/* 205 */         return ($$4 != $$5);
/*     */       } 
/*     */     } 
/*     */     
/* 209 */     return true;
/*     */   }
/*     */   
/*     */   public TextureAtlas getAtlas(ResourceLocation $$0) {
/* 213 */     return this.atlases.getAtlas($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() {
/* 218 */     this.atlases.close();
/*     */   }
/*     */   
/*     */   public void updateMaxMipLevel(int $$0) {
/* 222 */     this.maxMipmapLevels = $$0;
/*     */   }
/*     */   private static final class ReloadState extends Record { final ModelBakery modelBakery; final BakedModel missingModel; final Map<BlockState, BakedModel> modelCache; final Map<ResourceLocation, AtlasSet.StitchResult> atlasPreparations; final CompletableFuture<Void> readyForUpload;
/* 225 */     ReloadState(ModelBakery $$0, BakedModel $$1, Map<BlockState, BakedModel> $$2, Map<ResourceLocation, AtlasSet.StitchResult> $$3, CompletableFuture<Void> $$4) { this.modelBakery = $$0; this.missingModel = $$1; this.modelCache = $$2; this.atlasPreparations = $$3; this.readyForUpload = $$4; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/client/resources/model/ModelManager$ReloadState;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #225	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 225 */       //   0	7	0	this	Lnet/minecraft/client/resources/model/ModelManager$ReloadState; } public ModelBakery modelBakery() { return this.modelBakery; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/resources/model/ModelManager$ReloadState;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #225	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/resources/model/ModelManager$ReloadState; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/client/resources/model/ModelManager$ReloadState;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #225	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/client/resources/model/ModelManager$ReloadState;
/* 225 */       //   0	8	1	$$0	Ljava/lang/Object; } public BakedModel missingModel() { return this.missingModel; } public Map<BlockState, BakedModel> modelCache() { return this.modelCache; } public Map<ResourceLocation, AtlasSet.StitchResult> atlasPreparations() { return this.atlasPreparations; } public CompletableFuture<Void> readyForUpload() { return this.readyForUpload; }
/*     */      }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\model\ModelManager.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */