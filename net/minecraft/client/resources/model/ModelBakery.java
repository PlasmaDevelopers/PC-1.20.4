/*     */ package net.minecraft.client.resources.model;
/*     */ 
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import com.google.common.base.Splitter;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.math.Transformation;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.Reader;
/*     */ import java.io.StringReader;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import java.util.function.BiFunction;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Predicate;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.stream.Collectors;
/*     */ import java.util.stream.IntStream;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.client.color.block.BlockColors;
/*     */ import net.minecraft.client.renderer.RenderType;
/*     */ import net.minecraft.client.renderer.Sheets;
/*     */ import net.minecraft.client.renderer.block.BlockModelShaper;
/*     */ import net.minecraft.client.renderer.block.model.BlockModel;
/*     */ import net.minecraft.client.renderer.block.model.BlockModelDefinition;
/*     */ import net.minecraft.client.renderer.block.model.ItemModelGenerator;
/*     */ import net.minecraft.client.renderer.block.model.MultiVariant;
/*     */ import net.minecraft.client.renderer.block.model.multipart.MultiPart;
/*     */ import net.minecraft.client.renderer.block.model.multipart.Selector;
/*     */ import net.minecraft.client.renderer.entity.ItemRenderer;
/*     */ import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlas;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.resources.FileToIdConverter;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.profiling.ProfilerFiller;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.RenderShape;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BooleanProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class ModelBakery {
/*  63 */   public static final Material FIRE_0 = new Material(TextureAtlas.LOCATION_BLOCKS, new ResourceLocation("block/fire_0"));
/*  64 */   public static final Material FIRE_1 = new Material(TextureAtlas.LOCATION_BLOCKS, new ResourceLocation("block/fire_1"));
/*  65 */   public static final Material LAVA_FLOW = new Material(TextureAtlas.LOCATION_BLOCKS, new ResourceLocation("block/lava_flow"));
/*  66 */   public static final Material WATER_FLOW = new Material(TextureAtlas.LOCATION_BLOCKS, new ResourceLocation("block/water_flow"));
/*  67 */   public static final Material WATER_OVERLAY = new Material(TextureAtlas.LOCATION_BLOCKS, new ResourceLocation("block/water_overlay"));
/*     */   
/*  69 */   public static final Material BANNER_BASE = new Material(Sheets.BANNER_SHEET, new ResourceLocation("entity/banner_base"));
/*  70 */   public static final Material SHIELD_BASE = new Material(Sheets.SHIELD_SHEET, new ResourceLocation("entity/shield_base"));
/*  71 */   public static final Material NO_PATTERN_SHIELD = new Material(Sheets.SHIELD_SHEET, new ResourceLocation("entity/shield_base_nopattern")); public static final int DESTROY_STAGE_COUNT = 10; public static final List<ResourceLocation> DESTROY_STAGES; public static final List<ResourceLocation> BREAKING_LOCATIONS;
/*     */   
/*     */   static {
/*  74 */     DESTROY_STAGES = (List<ResourceLocation>)IntStream.range(0, 10).mapToObj($$0 -> new ResourceLocation("block/destroy_stage_" + $$0)).collect(Collectors.toList());
/*     */ 
/*     */ 
/*     */     
/*  78 */     BREAKING_LOCATIONS = (List<ResourceLocation>)DESTROY_STAGES.stream().map($$0 -> new ResourceLocation("textures/" + $$0.getPath() + ".png")).collect(Collectors.toList());
/*     */   }
/*  80 */   public static final List<RenderType> DESTROY_TYPES = (List<RenderType>)BREAKING_LOCATIONS.stream().map(RenderType::crumbling).collect(Collectors.toList());
/*     */   
/*     */   static final int SINGLETON_MODEL_GROUP = -1;
/*     */   
/*     */   private static final int INVISIBLE_MODEL_GROUP = 0;
/*  85 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private static final String BUILTIN_SLASH = "builtin/";
/*     */   private static final String BUILTIN_SLASH_GENERATED = "builtin/generated";
/*     */   private static final String BUILTIN_BLOCK_ENTITY = "builtin/entity";
/*     */   private static final String MISSING_MODEL_NAME = "missing";
/*  91 */   public static final ModelResourceLocation MISSING_MODEL_LOCATION = ModelResourceLocation.vanilla("builtin/missing", "missing");
/*     */   
/*  93 */   public static final FileToIdConverter BLOCKSTATE_LISTER = FileToIdConverter.json("blockstates");
/*  94 */   public static final FileToIdConverter MODEL_LISTER = FileToIdConverter.json("models");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @VisibleForTesting
/* 100 */   public static final String MISSING_MODEL_MESH = ("{    'textures': {       'particle': '" + MissingTextureAtlasSprite.getLocation().getPath() + "',       'missingno': '" + 
/* 101 */     MissingTextureAtlasSprite.getLocation().getPath() + "'    },    'elements': [         {  'from': [ 0, 0, 0 ],            'to': [ 16, 16, 16 ],            'faces': {                'down':  { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'down',  'texture': '#missingno' },                'up':    { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'up',    'texture': '#missingno' },                'north': { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'north', 'texture': '#missingno' },                'south': { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'south', 'texture': '#missingno' },                'west':  { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'west',  'texture': '#missingno' },                'east':  { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'east',  'texture': '#missingno' }            }        }    ]}")
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
/*     */     
/* 116 */     .replace('\'', '"');
/*     */   
/* 118 */   private static final Map<String, String> BUILTIN_MODELS = Maps.newHashMap((Map)ImmutableMap.of("missing", MISSING_MODEL_MESH));
/*     */   
/* 120 */   private static final Splitter COMMA_SPLITTER = Splitter.on(',');
/* 121 */   private static final Splitter EQUAL_SPLITTER = Splitter.on('=').limit(2); public static final BlockModel GENERATION_MARKER; public static final BlockModel BLOCK_ENTITY_MARKER;
/*     */   static {
/* 123 */     GENERATION_MARKER = (BlockModel)Util.make(BlockModel.fromString("{\"gui_light\": \"front\"}"), $$0 -> $$0.name = "generation marker");
/* 124 */     BLOCK_ENTITY_MARKER = (BlockModel)Util.make(BlockModel.fromString("{\"gui_light\": \"side\"}"), $$0 -> $$0.name = "block entity marker");
/* 125 */   } private static final StateDefinition<Block, BlockState> ITEM_FRAME_FAKE_DEFINITION = (new StateDefinition.Builder(Blocks.AIR)).add(new Property[] { (Property)BooleanProperty.create("map") }).create(Block::defaultBlockState, BlockState::new);
/* 126 */   static final ItemModelGenerator ITEM_MODEL_GENERATOR = new ItemModelGenerator();
/* 127 */   private static final Map<ResourceLocation, StateDefinition<Block, BlockState>> STATIC_DEFINITIONS = (Map<ResourceLocation, StateDefinition<Block, BlockState>>)ImmutableMap.of(new ResourceLocation("item_frame"), ITEM_FRAME_FAKE_DEFINITION, new ResourceLocation("glow_item_frame"), ITEM_FRAME_FAKE_DEFINITION);
/*     */ 
/*     */   
/*     */   private final BlockColors blockColors;
/*     */ 
/*     */   
/*     */   private final Map<ResourceLocation, BlockModel> modelResources;
/*     */   
/*     */   private final Map<ResourceLocation, List<LoadedJson>> blockStateResources;
/*     */   
/* 137 */   private final Set<ResourceLocation> loadingStack = Sets.newHashSet();
/* 138 */   private final BlockModelDefinition.Context context = new BlockModelDefinition.Context();
/*     */   
/* 140 */   private final Map<ResourceLocation, UnbakedModel> unbakedCache = Maps.newHashMap();
/* 141 */   final Map<BakedCacheKey, BakedModel> bakedCache = Maps.newHashMap();
/*     */   
/* 143 */   private final Map<ResourceLocation, UnbakedModel> topLevelModels = Maps.newHashMap();
/* 144 */   private final Map<ResourceLocation, BakedModel> bakedTopLevelModels = Maps.newHashMap();
/*     */   
/* 146 */   private int nextModelGroup = 1; private final Object2IntMap<BlockState> modelGroups; public ModelBakery(BlockColors $$0, ProfilerFiller $$1, Map<ResourceLocation, BlockModel> $$2, Map<ResourceLocation, List<LoadedJson>> $$3) {
/* 147 */     this.modelGroups = (Object2IntMap<BlockState>)Util.make(new Object2IntOpenHashMap(), $$0 -> $$0.defaultReturnValue(-1));
/*     */ 
/*     */     
/* 150 */     this.blockColors = $$0;
/* 151 */     this.modelResources = $$2;
/* 152 */     this.blockStateResources = $$3;
/*     */     
/* 154 */     $$1.push("missing_model");
/*     */     try {
/* 156 */       this.unbakedCache.put(MISSING_MODEL_LOCATION, loadBlockModel(MISSING_MODEL_LOCATION));
/* 157 */       loadTopLevel(MISSING_MODEL_LOCATION);
/* 158 */     } catch (IOException $$4) {
/* 159 */       LOGGER.error("Error loading missing model, should never happen :(", $$4);
/* 160 */       throw new RuntimeException($$4);
/*     */     } 
/*     */     
/* 163 */     $$1.popPush("static_definitions");
/* 164 */     STATIC_DEFINITIONS.forEach(($$0, $$1) -> $$1.getPossibleStates().forEach(()));
/*     */     
/* 166 */     $$1.popPush("blocks");
/* 167 */     for (Block $$5 : BuiltInRegistries.BLOCK) {
/* 168 */       $$5.getStateDefinition().getPossibleStates().forEach($$0 -> loadTopLevel(BlockModelShaper.stateToModelLocation($$0)));
/*     */     }
/*     */     
/* 171 */     $$1.popPush("items");
/* 172 */     for (ResourceLocation $$6 : BuiltInRegistries.ITEM.keySet()) {
/* 173 */       loadTopLevel(new ModelResourceLocation($$6, "inventory"));
/*     */     }
/*     */     
/* 176 */     $$1.popPush("special");
/* 177 */     loadTopLevel(ItemRenderer.TRIDENT_IN_HAND_MODEL);
/* 178 */     loadTopLevel(ItemRenderer.SPYGLASS_IN_HAND_MODEL);
/*     */     
/* 180 */     this.topLevelModels.values().forEach($$0 -> $$0.resolveParents(this::getModel));
/* 181 */     $$1.pop();
/*     */   }
/*     */   
/*     */   public void bakeModels(BiFunction<ResourceLocation, Material, TextureAtlasSprite> $$0) {
/* 185 */     this.topLevelModels.keySet().forEach($$1 -> {
/*     */           BakedModel $$2 = null;
/*     */           try {
/*     */             $$2 = (new ModelBakerImpl($$0, $$1)).bake($$1, BlockModelRotation.X0_Y0);
/* 189 */           } catch (Exception $$3) {
/*     */             LOGGER.warn("Unable to bake model: '{}': {}", $$1, $$3);
/*     */           } 
/*     */           if ($$2 != null) {
/*     */             this.bakedTopLevelModels.put($$1, $$2);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   private static Predicate<BlockState> predicate(StateDefinition<Block, BlockState> $$0, String $$1) {
/* 200 */     Map<Property<?>, Comparable<?>> $$2 = Maps.newHashMap();
/* 201 */     for (String $$3 : COMMA_SPLITTER.split($$1)) {
/* 202 */       Iterator<String> $$4 = EQUAL_SPLITTER.split($$3).iterator();
/* 203 */       if ($$4.hasNext()) {
/* 204 */         String $$5 = $$4.next();
/* 205 */         Property<?> $$6 = $$0.getProperty($$5);
/* 206 */         if ($$6 != null && $$4.hasNext()) {
/* 207 */           String $$7 = $$4.next();
/* 208 */           Comparable<?> $$8 = (Comparable<?>)getValueHelper($$6, $$7);
/* 209 */           if ($$8 != null) {
/* 210 */             $$2.put($$6, $$8); continue;
/*     */           } 
/* 212 */           throw new RuntimeException("Unknown value: '" + $$7 + "' for blockstate property: '" + $$5 + "' " + $$6.getPossibleValues());
/*     */         } 
/* 214 */         if (!$$5.isEmpty()) {
/* 215 */           throw new RuntimeException("Unknown blockstate property: '" + $$5 + "'");
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 220 */     Block $$9 = (Block)$$0.getOwner();
/*     */     
/* 222 */     return $$2 -> {
/*     */         if ($$2 == null || !$$2.is($$0)) {
/*     */           return false;
/*     */         }
/*     */         for (Map.Entry<Property<?>, Comparable<?>> $$3 : (Iterable<Map.Entry<Property<?>, Comparable<?>>>)$$1.entrySet()) {
/*     */           if (!Objects.equals($$2.getValue($$3.getKey()), $$3.getValue())) {
/*     */             return false;
/*     */           }
/*     */         } 
/*     */         return true;
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   static <T extends Comparable<T>> T getValueHelper(Property<T> $$0, String $$1) {
/* 239 */     return (T)$$0.getValue($$1).orElse(null);
/*     */   }
/*     */   
/*     */   public UnbakedModel getModel(ResourceLocation $$0) {
/* 243 */     if (this.unbakedCache.containsKey($$0)) {
/* 244 */       return this.unbakedCache.get($$0);
/*     */     }
/* 246 */     if (this.loadingStack.contains($$0)) {
/* 247 */       throw new IllegalStateException("Circular reference while loading " + $$0);
/*     */     }
/* 249 */     this.loadingStack.add($$0);
/*     */     
/* 251 */     UnbakedModel $$1 = this.unbakedCache.get(MISSING_MODEL_LOCATION);
/* 252 */     while (!this.loadingStack.isEmpty()) {
/* 253 */       ResourceLocation $$2 = this.loadingStack.iterator().next();
/*     */       try {
/* 255 */         if (!this.unbakedCache.containsKey($$2)) {
/* 256 */           loadModel($$2);
/*     */         }
/* 258 */       } catch (BlockStateDefinitionException $$3) {
/* 259 */         LOGGER.warn($$3.getMessage());
/* 260 */         this.unbakedCache.put($$2, $$1);
/* 261 */       } catch (Exception $$4) {
/* 262 */         LOGGER.warn("Unable to load model: '{}' referenced from: {}: {}", new Object[] { $$2, $$0, $$4 });
/* 263 */         this.unbakedCache.put($$2, $$1);
/*     */       } finally {
/* 265 */         this.loadingStack.remove($$2);
/*     */       } 
/*     */     } 
/*     */     
/* 269 */     return this.unbakedCache.getOrDefault($$0, $$1);
/*     */   }
/*     */   
/*     */   private void loadModel(ResourceLocation $$0) throws Exception {
/* 273 */     if (!($$0 instanceof ModelResourceLocation)) {
/* 274 */       cacheAndQueueDependencies($$0, (UnbakedModel)loadBlockModel($$0));
/*     */       
/*     */       return;
/*     */     } 
/* 278 */     ModelResourceLocation $$1 = (ModelResourceLocation)$$0;
/* 279 */     if (Objects.equals($$1.getVariant(), "inventory")) {
/* 280 */       ResourceLocation $$2 = $$0.withPrefix("item/");
/* 281 */       BlockModel $$3 = loadBlockModel($$2);
/* 282 */       cacheAndQueueDependencies($$1, (UnbakedModel)$$3);
/* 283 */       this.unbakedCache.put($$2, $$3);
/*     */     } else {
/* 285 */       ResourceLocation $$4 = new ResourceLocation($$0.getNamespace(), $$0.getPath());
/*     */       
/* 287 */       StateDefinition<Block, BlockState> $$5 = Optional.<StateDefinition<Block, BlockState>>ofNullable(STATIC_DEFINITIONS.get($$4)).orElseGet(() -> ((Block)BuiltInRegistries.BLOCK.get($$0)).getStateDefinition());
/* 288 */       this.context.setDefinition($$5);
/*     */       
/* 290 */       ImmutableList immutableList = ImmutableList.copyOf(this.blockColors.getColoringProperties((Block)$$5.getOwner()));
/*     */       
/* 292 */       ImmutableList<BlockState> $$7 = $$5.getPossibleStates();
/*     */       
/* 294 */       Map<ModelResourceLocation, BlockState> $$8 = Maps.newHashMap();
/* 295 */       $$7.forEach($$2 -> $$0.put(BlockModelShaper.stateToModelLocation($$1, $$2), $$2));
/*     */       
/* 297 */       Map<BlockState, Pair<UnbakedModel, Supplier<ModelGroupKey>>> $$9 = Maps.newHashMap();
/*     */       
/* 299 */       ResourceLocation $$10 = BLOCKSTATE_LISTER.idToFile($$0);
/* 300 */       UnbakedModel $$11 = this.unbakedCache.get(MISSING_MODEL_LOCATION);
/* 301 */       ModelGroupKey $$12 = new ModelGroupKey((List<UnbakedModel>)ImmutableList.of($$11), (List<Object>)ImmutableList.of());
/* 302 */       Pair<UnbakedModel, Supplier<ModelGroupKey>> $$13 = Pair.of($$11, () -> $$0);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/*     */         Map<ModelGroupKey, Set<BlockState>> $$20;
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 313 */         List<Pair<String, BlockModelDefinition>> $$14 = ((List)this.blockStateResources.getOrDefault($$10, List.of())).stream().map($$1 -> { try { return Pair.of($$1.source, BlockModelDefinition.fromJsonElement(this.context, $$1.data)); } catch (Exception $$2) { throw new BlockStateDefinitionException(String.format(Locale.ROOT, "Exception loading blockstate definition: '%s' in resourcepack: '%s': %s", new Object[] { $$0, $$1.source, $$2.getMessage() })); }  }).toList();
/*     */         
/* 315 */         for (Pair<String, BlockModelDefinition> $$15 : $$14) {
/* 316 */           MultiPart $$19; BlockModelDefinition $$16 = (BlockModelDefinition)$$15.getSecond();
/* 317 */           Map<BlockState, Pair<UnbakedModel, Supplier<ModelGroupKey>>> $$17 = Maps.newIdentityHashMap();
/*     */ 
/*     */           
/* 320 */           if ($$16.isMultiPart()) {
/* 321 */             MultiPart $$18 = $$16.getMultiPart();
/* 322 */             $$7.forEach($$3 -> $$0.put($$3, Pair.of($$1, ())));
/*     */           } else {
/* 324 */             $$19 = null;
/*     */           } 
/*     */           
/* 327 */           $$16.getVariants().forEach(($$9, $$10) -> {
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/*     */                 try {
/*     */ 
/*     */                   
/*     */                   $$0.stream().filter(predicate($$1, $$9)).forEach(());
/* 336 */                 } catch (Exception $$11) {
/*     */                   LOGGER.warn("Exception loading blockstate definition: '{}' in resourcepack: '{}' for variant: '{}': {}", new Object[] { $$7, $$8.getFirst(), $$9, $$11.getMessage() });
/*     */                 } 
/*     */               });
/*     */           
/* 341 */           $$9.putAll($$17);
/*     */         } 
/* 343 */       } catch (BlockStateDefinitionException $$21) {
/* 344 */         throw $$21;
/* 345 */       } catch (Exception $$22) {
/* 346 */         throw new BlockStateDefinitionException(String.format(Locale.ROOT, "Exception loading blockstate definition: '%s': %s", new Object[] { $$10, $$22 }));
/*     */       } finally {
/* 348 */         Map<ModelGroupKey, Set<BlockState>> $$23 = Maps.newHashMap();
/* 349 */         $$8.forEach(($$4, $$5) -> {
/*     */               Pair<UnbakedModel, Supplier<ModelGroupKey>> $$6 = (Pair<UnbakedModel, Supplier<ModelGroupKey>>)$$0.get($$5);
/*     */               
/*     */               if ($$6 == null) {
/*     */                 LOGGER.warn("Exception loading blockstate definition: '{}' missing model for variant: '{}'", $$1, $$4);
/*     */                 $$6 = $$2;
/*     */               } 
/*     */               cacheAndQueueDependencies($$4, (UnbakedModel)$$6.getFirst());
/*     */               try {
/*     */                 ModelGroupKey $$7 = ((Supplier<ModelGroupKey>)$$6.getSecond()).get();
/*     */                 ((Set<BlockState>)$$3.computeIfAbsent($$7, ())).add($$5);
/* 360 */               } catch (Exception $$8) {
/*     */                 LOGGER.warn("Exception evaluating model definition: '{}'", $$4, $$8);
/*     */               } 
/*     */             });
/*     */         
/* 365 */         $$23.forEach(($$0, $$1) -> {
/*     */               Iterator<BlockState> $$2 = $$1.iterator();
/*     */               while ($$2.hasNext()) {
/*     */                 BlockState $$3 = $$2.next();
/*     */                 if ($$3.getRenderShape() != RenderShape.MODEL) {
/*     */                   $$2.remove();
/*     */                   this.modelGroups.put($$3, 0);
/*     */                 } 
/*     */               } 
/*     */               if ($$1.size() > 1) {
/*     */                 registerModelGroup($$1);
/*     */               }
/*     */             });
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void cacheAndQueueDependencies(ResourceLocation $$0, UnbakedModel $$1) {
/* 384 */     this.unbakedCache.put($$0, $$1);
/* 385 */     this.loadingStack.addAll($$1.getDependencies());
/*     */   }
/*     */   
/*     */   private void loadTopLevel(ModelResourceLocation $$0) {
/* 389 */     UnbakedModel $$1 = getModel($$0);
/* 390 */     this.unbakedCache.put($$0, $$1);
/* 391 */     this.topLevelModels.put($$0, $$1);
/*     */   }
/*     */   
/*     */   private void registerModelGroup(Iterable<BlockState> $$0) {
/* 395 */     int $$1 = this.nextModelGroup++;
/* 396 */     $$0.forEach($$1 -> this.modelGroups.put($$1, $$0));
/*     */   }
/*     */   
/*     */   private class ModelBakerImpl implements ModelBaker {
/*     */     private final Function<Material, TextureAtlasSprite> modelTextureGetter;
/*     */     
/*     */     ModelBakerImpl(BiFunction<ResourceLocation, Material, TextureAtlasSprite> $$0, ResourceLocation $$1) {
/* 403 */       this.modelTextureGetter = ($$2 -> (TextureAtlasSprite)$$0.apply($$1, $$2));
/*     */     }
/*     */ 
/*     */     
/*     */     public UnbakedModel getModel(ResourceLocation $$0) {
/* 408 */       return ModelBakery.this.getModel($$0);
/*     */     }
/*     */ 
/*     */     
/*     */     public BakedModel bake(ResourceLocation $$0, ModelState $$1) {
/* 413 */       ModelBakery.BakedCacheKey $$2 = new ModelBakery.BakedCacheKey($$0, $$1.getRotation(), $$1.isUvLocked());
/* 414 */       BakedModel $$3 = ModelBakery.this.bakedCache.get($$2);
/* 415 */       if ($$3 != null) {
/* 416 */         return $$3;
/*     */       }
/*     */       
/* 419 */       UnbakedModel $$4 = getModel($$0);
/*     */       
/* 421 */       if ($$4 instanceof BlockModel) { BlockModel $$5 = (BlockModel)$$4;
/* 422 */         if ($$5.getRootModel() == ModelBakery.GENERATION_MARKER) {
/* 423 */           return ModelBakery.ITEM_MODEL_GENERATOR.generateBlockModel(this.modelTextureGetter, $$5).bake(this, $$5, this.modelTextureGetter, $$1, $$0, false);
/*     */         } }
/*     */       
/* 426 */       BakedModel $$6 = $$4.bake(this, this.modelTextureGetter, $$1, $$0);
/* 427 */       ModelBakery.this.bakedCache.put($$2, $$6);
/* 428 */       return $$6;
/*     */     }
/*     */   }
/*     */   
/*     */   private BlockModel loadBlockModel(ResourceLocation $$0) throws IOException {
/* 433 */     String $$1 = $$0.getPath();
/*     */     
/* 435 */     if ("builtin/generated".equals($$1))
/* 436 */       return GENERATION_MARKER; 
/* 437 */     if ("builtin/entity".equals($$1))
/* 438 */       return BLOCK_ENTITY_MARKER; 
/* 439 */     if ($$1.startsWith("builtin/")) {
/*     */       
/* 441 */       String $$2 = $$1.substring("builtin/".length());
/* 442 */       String $$3 = BUILTIN_MODELS.get($$2);
/* 443 */       if ($$3 == null) {
/* 444 */         throw new FileNotFoundException($$0.toString());
/*     */       }
/*     */       
/* 447 */       Reader $$4 = new StringReader($$3);
/* 448 */       BlockModel $$5 = BlockModel.fromStream($$4);
/* 449 */       $$5.name = $$0.toString();
/* 450 */       return $$5;
/*     */     } 
/* 452 */     ResourceLocation $$6 = MODEL_LISTER.idToFile($$0);
/* 453 */     BlockModel $$7 = this.modelResources.get($$6);
/* 454 */     if ($$7 == null) {
/* 455 */       throw new FileNotFoundException($$6.toString());
/*     */     }
/* 457 */     $$7.name = $$0.toString();
/* 458 */     return $$7;
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<ResourceLocation, BakedModel> getBakedTopLevelModels() {
/* 463 */     return this.bakedTopLevelModels;
/*     */   }
/*     */   
/*     */   public Object2IntMap<BlockState> getModelGroups() {
/* 467 */     return this.modelGroups;
/*     */   }
/*     */   
/*     */   private static class BlockStateDefinitionException extends RuntimeException {
/*     */     public BlockStateDefinitionException(String $$0) {
/* 472 */       super($$0);
/*     */     } }
/*     */   private static final class BakedCacheKey extends Record { private final ResourceLocation id; private final Transformation transformation; private final boolean isUvLocked;
/*     */     
/* 476 */     BakedCacheKey(ResourceLocation $$0, Transformation $$1, boolean $$2) { this.id = $$0; this.transformation = $$1; this.isUvLocked = $$2; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/client/resources/model/ModelBakery$BakedCacheKey;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #476	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 476 */       //   0	7	0	this	Lnet/minecraft/client/resources/model/ModelBakery$BakedCacheKey; } public ResourceLocation id() { return this.id; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/resources/model/ModelBakery$BakedCacheKey;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #476	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/resources/model/ModelBakery$BakedCacheKey; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/client/resources/model/ModelBakery$BakedCacheKey;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #476	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/client/resources/model/ModelBakery$BakedCacheKey;
/* 476 */       //   0	8	1	$$0	Ljava/lang/Object; } public Transformation transformation() { return this.transformation; } public boolean isUvLocked() { return this.isUvLocked; }
/*     */      }
/*     */   
/*     */   private static class ModelGroupKey { private final List<UnbakedModel> models;
/*     */     private final List<Object> coloringValues;
/*     */     
/*     */     public ModelGroupKey(List<UnbakedModel> $$0, List<Object> $$1) {
/* 483 */       this.models = $$0;
/* 484 */       this.coloringValues = $$1;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object $$0) {
/* 489 */       if (this == $$0) {
/* 490 */         return true;
/*     */       }
/* 492 */       if ($$0 instanceof ModelGroupKey) { ModelGroupKey $$1 = (ModelGroupKey)$$0;
/* 493 */         return (Objects.equals(this.models, $$1.models) && Objects.equals(this.coloringValues, $$1.coloringValues)); }
/*     */ 
/*     */       
/* 496 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 501 */       return 31 * this.models.hashCode() + this.coloringValues.hashCode();
/*     */     }
/*     */     
/*     */     public static ModelGroupKey create(BlockState $$0, MultiPart $$1, Collection<Property<?>> $$2) {
/* 505 */       StateDefinition<Block, BlockState> $$3 = $$0.getBlock().getStateDefinition();
/*     */ 
/*     */ 
/*     */       
/* 509 */       List<UnbakedModel> $$4 = (List<UnbakedModel>)$$1.getSelectors().stream().filter($$2 -> $$2.getPredicate($$0).test($$1)).map(Selector::getVariant).collect(ImmutableList.toImmutableList());
/* 510 */       List<Object> $$5 = getColoringValues($$0, $$2);
/* 511 */       return new ModelGroupKey($$4, $$5);
/*     */     }
/*     */     
/*     */     public static ModelGroupKey create(BlockState $$0, UnbakedModel $$1, Collection<Property<?>> $$2) {
/* 515 */       List<Object> $$3 = getColoringValues($$0, $$2);
/* 516 */       return new ModelGroupKey((List<UnbakedModel>)ImmutableList.of($$1), $$3);
/*     */     }
/*     */     
/*     */     private static List<Object> getColoringValues(BlockState $$0, Collection<Property<?>> $$1) {
/* 520 */       Objects.requireNonNull($$0); return (List<Object>)$$1.stream().map($$0::getValue).collect(ImmutableList.toImmutableList());
/*     */     } }
/*     */   public static final class LoadedJson extends Record { final String source; final JsonElement data;
/*     */     
/* 524 */     public LoadedJson(String $$0, JsonElement $$1) { this.source = $$0; this.data = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/client/resources/model/ModelBakery$LoadedJson;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #524	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/resources/model/ModelBakery$LoadedJson; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/resources/model/ModelBakery$LoadedJson;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #524	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/resources/model/ModelBakery$LoadedJson; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/client/resources/model/ModelBakery$LoadedJson;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #524	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/client/resources/model/ModelBakery$LoadedJson;
/* 524 */       //   0	8	1	$$0	Ljava/lang/Object; } public String source() { return this.source; } public JsonElement data() { return this.data; }
/*     */      }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\model\ModelBakery.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */