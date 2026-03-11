/*     */ package net.minecraft.client.renderer.block.model;
/*     */ 
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import com.google.common.base.Joiner;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonDeserializer;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.mojang.datafixers.util.Either;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.io.Reader;
/*     */ import java.io.StringReader;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import java.util.function.Function;
/*     */ import java.util.stream.Collectors;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlas;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.resources.model.BakedModel;
/*     */ import net.minecraft.client.resources.model.BuiltInModel;
/*     */ import net.minecraft.client.resources.model.Material;
/*     */ import net.minecraft.client.resources.model.ModelBaker;
/*     */ import net.minecraft.client.resources.model.ModelBakery;
/*     */ import net.minecraft.client.resources.model.ModelState;
/*     */ import net.minecraft.client.resources.model.SimpleBakedModel;
/*     */ import net.minecraft.client.resources.model.UnbakedModel;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.GsonHelper;
/*     */ import net.minecraft.world.item.ItemDisplayContext;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class BlockModel
/*     */   implements UnbakedModel {
/*  49 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*  51 */   private static final FaceBakery FACE_BAKERY = new FaceBakery();
/*     */   
/*     */   @VisibleForTesting
/*  54 */   static final Gson GSON = (new GsonBuilder())
/*  55 */     .registerTypeAdapter(BlockModel.class, new Deserializer())
/*  56 */     .registerTypeAdapter(BlockElement.class, new BlockElement.Deserializer())
/*  57 */     .registerTypeAdapter(BlockElementFace.class, new BlockElementFace.Deserializer())
/*  58 */     .registerTypeAdapter(BlockFaceUV.class, new BlockFaceUV.Deserializer())
/*  59 */     .registerTypeAdapter(ItemTransform.class, new ItemTransform.Deserializer())
/*  60 */     .registerTypeAdapter(ItemTransforms.class, new ItemTransforms.Deserializer())
/*  61 */     .registerTypeAdapter(ItemOverride.class, new ItemOverride.Deserializer())
/*  62 */     .create();
/*     */   
/*     */   private static final char REFERENCE_CHAR = '#';
/*     */   
/*     */   public static final String PARTICLE_TEXTURE_REFERENCE = "particle";
/*     */   private static final boolean DEFAULT_AMBIENT_OCCLUSION = true;
/*     */   private final List<BlockElement> elements;
/*     */   @Nullable
/*     */   private final GuiLight guiLight;
/*     */   @Nullable
/*     */   private final Boolean hasAmbientOcclusion;
/*     */   private final ItemTransforms transforms;
/*     */   private final List<ItemOverride> overrides;
/*  75 */   public String name = "";
/*     */   
/*     */   @VisibleForTesting
/*     */   protected final Map<String, Either<Material, String>> textureMap;
/*     */   
/*     */   @Nullable
/*     */   protected BlockModel parent;
/*     */   @Nullable
/*     */   protected ResourceLocation parentLocation;
/*     */   
/*     */   public static BlockModel fromStream(Reader $$0) {
/*  86 */     return (BlockModel)GsonHelper.fromJson(GSON, $$0, BlockModel.class);
/*     */   }
/*     */   
/*     */   public static BlockModel fromString(String $$0) {
/*  90 */     return fromStream(new StringReader($$0));
/*     */   }
/*     */   
/*     */   public BlockModel(@Nullable ResourceLocation $$0, List<BlockElement> $$1, Map<String, Either<Material, String>> $$2, @Nullable Boolean $$3, @Nullable GuiLight $$4, ItemTransforms $$5, List<ItemOverride> $$6) {
/*  94 */     this.elements = $$1;
/*  95 */     this.hasAmbientOcclusion = $$3;
/*  96 */     this.guiLight = $$4;
/*  97 */     this.textureMap = $$2;
/*  98 */     this.parentLocation = $$0;
/*  99 */     this.transforms = $$5;
/* 100 */     this.overrides = $$6;
/*     */   }
/*     */   
/*     */   public List<BlockElement> getElements() {
/* 104 */     if (this.elements.isEmpty() && this.parent != null) {
/* 105 */       return this.parent.getElements();
/*     */     }
/*     */     
/* 108 */     return this.elements;
/*     */   }
/*     */   
/*     */   public boolean hasAmbientOcclusion() {
/* 112 */     if (this.hasAmbientOcclusion != null) {
/* 113 */       return this.hasAmbientOcclusion.booleanValue();
/*     */     }
/* 115 */     if (this.parent != null) {
/* 116 */       return this.parent.hasAmbientOcclusion();
/*     */     }
/* 118 */     return true;
/*     */   }
/*     */   
/*     */   public GuiLight getGuiLight() {
/* 122 */     if (this.guiLight != null) {
/* 123 */       return this.guiLight;
/*     */     }
/* 125 */     if (this.parent != null) {
/* 126 */       return this.parent.getGuiLight();
/*     */     }
/* 128 */     return GuiLight.SIDE;
/*     */   }
/*     */   
/*     */   public boolean isResolved() {
/* 132 */     return (this.parentLocation == null || (this.parent != null && this.parent.isResolved()));
/*     */   }
/*     */   
/*     */   public List<ItemOverride> getOverrides() {
/* 136 */     return this.overrides;
/*     */   }
/*     */   
/*     */   private ItemOverrides getItemOverrides(ModelBaker $$0, BlockModel $$1) {
/* 140 */     if (this.overrides.isEmpty()) {
/* 141 */       return ItemOverrides.EMPTY;
/*     */     }
/* 143 */     return new ItemOverrides($$0, $$1, this.overrides);
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<ResourceLocation> getDependencies() {
/* 148 */     Set<ResourceLocation> $$0 = Sets.newHashSet();
/* 149 */     for (ItemOverride $$1 : this.overrides) {
/* 150 */       $$0.add($$1.getModel());
/*     */     }
/*     */     
/* 153 */     if (this.parentLocation != null) {
/* 154 */       $$0.add(this.parentLocation);
/*     */     }
/* 156 */     return $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void resolveParents(Function<ResourceLocation, UnbakedModel> $$0) {
/* 161 */     Set<UnbakedModel> $$1 = Sets.newLinkedHashSet();
/*     */     
/* 163 */     BlockModel $$2 = this;
/*     */     
/* 165 */     while ($$2.parentLocation != null && $$2.parent == null) {
/* 166 */       $$1.add($$2);
/* 167 */       UnbakedModel $$3 = $$0.apply($$2.parentLocation);
/*     */       
/* 169 */       if ($$3 == null) {
/* 170 */         LOGGER.warn("No parent '{}' while loading model '{}'", this.parentLocation, $$2);
/*     */       }
/*     */       
/* 173 */       if ($$1.contains($$3)) {
/* 174 */         LOGGER.warn("Found 'parent' loop while loading model '{}' in chain: {} -> {}", new Object[] { $$2, $$1.stream().map(Object::toString).collect(Collectors.joining(" -> ")), this.parentLocation });
/* 175 */         $$3 = null;
/*     */       } 
/*     */       
/* 178 */       if ($$3 == null) {
/* 179 */         $$2.parentLocation = (ResourceLocation)ModelBakery.MISSING_MODEL_LOCATION;
/* 180 */         $$3 = $$0.apply($$2.parentLocation);
/*     */       } 
/*     */       
/* 183 */       if ($$3 instanceof BlockModel) {
/* 184 */         $$2.parent = (BlockModel)$$3;
/*     */       } else {
/* 186 */         throw new IllegalStateException("BlockModel parent has to be a block model.");
/*     */       } 
/*     */       
/* 189 */       $$2 = $$2.parent;
/*     */     } 
/*     */     
/* 192 */     this.overrides.forEach($$1 -> {
/*     */           UnbakedModel $$2 = $$0.apply($$1.getModel());
/*     */           if (Objects.equals($$2, this)) {
/*     */             return;
/*     */           }
/*     */           $$2.resolveParents($$0);
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public BakedModel bake(ModelBaker $$0, Function<Material, TextureAtlasSprite> $$1, ModelState $$2, ResourceLocation $$3) {
/* 203 */     return bake($$0, this, $$1, $$2, $$3, true);
/*     */   }
/*     */   
/*     */   public BakedModel bake(ModelBaker $$0, BlockModel $$1, Function<Material, TextureAtlasSprite> $$2, ModelState $$3, ResourceLocation $$4, boolean $$5) {
/* 207 */     TextureAtlasSprite $$6 = $$2.apply(getMaterial("particle"));
/*     */     
/* 209 */     if (getRootModel() == ModelBakery.BLOCK_ENTITY_MARKER) {
/* 210 */       return (BakedModel)new BuiltInModel(getTransforms(), getItemOverrides($$0, $$1), $$6, getGuiLight().lightLikeBlock());
/*     */     }
/*     */     
/* 213 */     SimpleBakedModel.Builder $$7 = (new SimpleBakedModel.Builder(this, getItemOverrides($$0, $$1), $$5)).particle($$6);
/*     */     
/* 215 */     for (BlockElement $$8 : getElements()) {
/* 216 */       for (Direction $$9 : $$8.faces.keySet()) {
/* 217 */         BlockElementFace $$10 = $$8.faces.get($$9);
/* 218 */         TextureAtlasSprite $$11 = $$2.apply(getMaterial($$10.texture));
/*     */         
/* 220 */         if ($$10.cullForDirection == null) {
/* 221 */           $$7.addUnculledFace(bakeFace($$8, $$10, $$11, $$9, $$3, $$4)); continue;
/*     */         } 
/* 223 */         $$7.addCulledFace(Direction.rotate($$3.getRotation().getMatrix(), $$10.cullForDirection), bakeFace($$8, $$10, $$11, $$9, $$3, $$4));
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 228 */     return $$7.build();
/*     */   }
/*     */   
/*     */   private static BakedQuad bakeFace(BlockElement $$0, BlockElementFace $$1, TextureAtlasSprite $$2, Direction $$3, ModelState $$4, ResourceLocation $$5) {
/* 232 */     return FACE_BAKERY.bakeQuad($$0.from, $$0.to, $$1, $$2, $$3, $$4, $$0.rotation, $$0.shade, $$5);
/*     */   }
/*     */   
/*     */   public boolean hasTexture(String $$0) {
/* 236 */     return !MissingTextureAtlasSprite.getLocation().equals(getMaterial($$0).texture());
/*     */   }
/*     */   
/*     */   public Material getMaterial(String $$0) {
/* 240 */     if (isTextureReference($$0)) {
/* 241 */       $$0 = $$0.substring(1);
/*     */     }
/*     */     
/* 244 */     List<String> $$1 = Lists.newArrayList();
/*     */     while (true) {
/* 246 */       Either<Material, String> $$2 = findTextureEntry($$0);
/* 247 */       Optional<Material> $$3 = $$2.left();
/* 248 */       if ($$3.isPresent()) {
/* 249 */         return $$3.get();
/*     */       }
/*     */       
/* 252 */       $$0 = $$2.right().get();
/* 253 */       if ($$1.contains($$0)) {
/* 254 */         LOGGER.warn("Unable to resolve texture due to reference chain {}->{} in {}", new Object[] { Joiner.on("->").join($$1), $$0, this.name });
/* 255 */         return new Material(TextureAtlas.LOCATION_BLOCKS, MissingTextureAtlasSprite.getLocation());
/*     */       } 
/* 257 */       $$1.add($$0);
/*     */     } 
/*     */   }
/*     */   
/*     */   private Either<Material, String> findTextureEntry(String $$0) {
/* 262 */     BlockModel $$1 = this;
/* 263 */     while ($$1 != null) {
/* 264 */       Either<Material, String> $$2 = $$1.textureMap.get($$0);
/* 265 */       if ($$2 != null) {
/* 266 */         return $$2;
/*     */       }
/* 268 */       $$1 = $$1.parent;
/*     */     } 
/* 270 */     return Either.left(new Material(TextureAtlas.LOCATION_BLOCKS, MissingTextureAtlasSprite.getLocation()));
/*     */   }
/*     */   
/*     */   static boolean isTextureReference(String $$0) {
/* 274 */     return ($$0.charAt(0) == '#');
/*     */   }
/*     */   
/*     */   public BlockModel getRootModel() {
/* 278 */     return (this.parent == null) ? this : this.parent.getRootModel();
/*     */   }
/*     */   
/*     */   public ItemTransforms getTransforms() {
/* 282 */     ItemTransform $$0 = getTransform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND);
/* 283 */     ItemTransform $$1 = getTransform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND);
/* 284 */     ItemTransform $$2 = getTransform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND);
/* 285 */     ItemTransform $$3 = getTransform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND);
/* 286 */     ItemTransform $$4 = getTransform(ItemDisplayContext.HEAD);
/* 287 */     ItemTransform $$5 = getTransform(ItemDisplayContext.GUI);
/* 288 */     ItemTransform $$6 = getTransform(ItemDisplayContext.GROUND);
/* 289 */     ItemTransform $$7 = getTransform(ItemDisplayContext.FIXED);
/* 290 */     return new ItemTransforms($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7);
/*     */   }
/*     */   
/*     */   private ItemTransform getTransform(ItemDisplayContext $$0) {
/* 294 */     if (this.parent != null && !this.transforms.hasTransform($$0)) {
/* 295 */       return this.parent.getTransform($$0);
/*     */     }
/* 297 */     return this.transforms.getTransform($$0);
/*     */   }
/*     */   
/*     */   public static class Deserializer
/*     */     implements JsonDeserializer<BlockModel> {
/*     */     public BlockModel deserialize(JsonElement $$0, Type $$1, JsonDeserializationContext $$2) throws JsonParseException {
/* 303 */       JsonObject $$3 = $$0.getAsJsonObject();
/*     */       
/* 305 */       List<BlockElement> $$4 = getElements($$2, $$3);
/* 306 */       String $$5 = getParentName($$3);
/*     */       
/* 308 */       Map<String, Either<Material, String>> $$6 = getTextureMap($$3);
/* 309 */       Boolean $$7 = getAmbientOcclusion($$3);
/*     */       
/* 311 */       ItemTransforms $$8 = ItemTransforms.NO_TRANSFORMS;
/* 312 */       if ($$3.has("display")) {
/* 313 */         JsonObject $$9 = GsonHelper.getAsJsonObject($$3, "display");
/* 314 */         $$8 = (ItemTransforms)$$2.deserialize((JsonElement)$$9, ItemTransforms.class);
/*     */       } 
/*     */       
/* 317 */       List<ItemOverride> $$10 = getOverrides($$2, $$3);
/*     */       
/* 319 */       BlockModel.GuiLight $$11 = null;
/* 320 */       if ($$3.has("gui_light")) {
/* 321 */         $$11 = BlockModel.GuiLight.getByName(GsonHelper.getAsString($$3, "gui_light"));
/*     */       }
/*     */       
/* 324 */       ResourceLocation $$12 = $$5.isEmpty() ? null : new ResourceLocation($$5);
/* 325 */       return new BlockModel($$12, $$4, $$6, $$7, $$11, $$8, $$10);
/*     */     }
/*     */     
/*     */     protected List<ItemOverride> getOverrides(JsonDeserializationContext $$0, JsonObject $$1) {
/* 329 */       List<ItemOverride> $$2 = Lists.newArrayList();
/* 330 */       if ($$1.has("overrides")) {
/* 331 */         JsonArray $$3 = GsonHelper.getAsJsonArray($$1, "overrides");
/* 332 */         for (JsonElement $$4 : $$3) {
/* 333 */           $$2.add((ItemOverride)$$0.deserialize($$4, ItemOverride.class));
/*     */         }
/*     */       } 
/* 336 */       return $$2;
/*     */     }
/*     */     
/*     */     private Map<String, Either<Material, String>> getTextureMap(JsonObject $$0) {
/* 340 */       ResourceLocation $$1 = TextureAtlas.LOCATION_BLOCKS;
/*     */       
/* 342 */       Map<String, Either<Material, String>> $$2 = Maps.newHashMap();
/*     */       
/* 344 */       if ($$0.has("textures")) {
/* 345 */         JsonObject $$3 = GsonHelper.getAsJsonObject($$0, "textures");
/* 346 */         for (Map.Entry<String, JsonElement> $$4 : (Iterable<Map.Entry<String, JsonElement>>)$$3.entrySet()) {
/* 347 */           $$2.put($$4.getKey(), parseTextureLocationOrReference($$1, ((JsonElement)$$4.getValue()).getAsString()));
/*     */         }
/*     */       } 
/*     */       
/* 351 */       return $$2;
/*     */     }
/*     */     
/*     */     private static Either<Material, String> parseTextureLocationOrReference(ResourceLocation $$0, String $$1) {
/* 355 */       if (BlockModel.isTextureReference($$1)) {
/* 356 */         return Either.right($$1.substring(1));
/*     */       }
/* 358 */       ResourceLocation $$2 = ResourceLocation.tryParse($$1);
/* 359 */       if ($$2 == null) {
/* 360 */         throw new JsonParseException($$1 + " is not valid resource location");
/*     */       }
/* 362 */       return Either.left(new Material($$0, $$2));
/*     */     }
/*     */ 
/*     */     
/*     */     private String getParentName(JsonObject $$0) {
/* 367 */       return GsonHelper.getAsString($$0, "parent", "");
/*     */     }
/*     */     
/*     */     @Nullable
/*     */     protected Boolean getAmbientOcclusion(JsonObject $$0) {
/* 372 */       if ($$0.has("ambientocclusion")) {
/* 373 */         return Boolean.valueOf(GsonHelper.getAsBoolean($$0, "ambientocclusion"));
/*     */       }
/* 375 */       return null;
/*     */     }
/*     */     
/*     */     protected List<BlockElement> getElements(JsonDeserializationContext $$0, JsonObject $$1) {
/* 379 */       List<BlockElement> $$2 = Lists.newArrayList();
/*     */       
/* 381 */       if ($$1.has("elements")) {
/* 382 */         for (JsonElement $$3 : GsonHelper.getAsJsonArray($$1, "elements")) {
/* 383 */           $$2.add((BlockElement)$$0.deserialize($$3, BlockElement.class));
/*     */         }
/*     */       }
/* 386 */       return $$2;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class LoopException extends RuntimeException {
/*     */     public LoopException(String $$0) {
/* 392 */       super($$0);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 398 */     return this.name;
/*     */   }
/*     */   
/*     */   public enum GuiLight {
/* 402 */     FRONT("front"),
/* 403 */     SIDE("side");
/*     */     
/*     */     private final String name;
/*     */     
/*     */     GuiLight(String $$0) {
/* 408 */       this.name = $$0;
/*     */     }
/*     */     
/*     */     public static GuiLight getByName(String $$0) {
/* 412 */       for (GuiLight $$1 : values()) {
/* 413 */         if ($$1.name.equals($$0)) {
/* 414 */           return $$1;
/*     */         }
/*     */       } 
/* 417 */       throw new IllegalArgumentException("Invalid gui light: " + $$0);
/*     */     }
/*     */     
/*     */     public boolean lightLikeBlock() {
/* 421 */       return (this == SIDE);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\block\model\BlockModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */