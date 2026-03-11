/*     */ package net.minecraft.client.renderer.block.model;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonDeserializer;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.mojang.datafixers.util.Either;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlas;
/*     */ import net.minecraft.client.resources.model.Material;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.GsonHelper;
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
/*     */ public class Deserializer
/*     */   implements JsonDeserializer<BlockModel>
/*     */ {
/*     */   public BlockModel deserialize(JsonElement $$0, Type $$1, JsonDeserializationContext $$2) throws JsonParseException {
/* 303 */     JsonObject $$3 = $$0.getAsJsonObject();
/*     */     
/* 305 */     List<BlockElement> $$4 = getElements($$2, $$3);
/* 306 */     String $$5 = getParentName($$3);
/*     */     
/* 308 */     Map<String, Either<Material, String>> $$6 = getTextureMap($$3);
/* 309 */     Boolean $$7 = getAmbientOcclusion($$3);
/*     */     
/* 311 */     ItemTransforms $$8 = ItemTransforms.NO_TRANSFORMS;
/* 312 */     if ($$3.has("display")) {
/* 313 */       JsonObject $$9 = GsonHelper.getAsJsonObject($$3, "display");
/* 314 */       $$8 = (ItemTransforms)$$2.deserialize((JsonElement)$$9, ItemTransforms.class);
/*     */     } 
/*     */     
/* 317 */     List<ItemOverride> $$10 = getOverrides($$2, $$3);
/*     */     
/* 319 */     BlockModel.GuiLight $$11 = null;
/* 320 */     if ($$3.has("gui_light")) {
/* 321 */       $$11 = BlockModel.GuiLight.getByName(GsonHelper.getAsString($$3, "gui_light"));
/*     */     }
/*     */     
/* 324 */     ResourceLocation $$12 = $$5.isEmpty() ? null : new ResourceLocation($$5);
/* 325 */     return new BlockModel($$12, $$4, $$6, $$7, $$11, $$8, $$10);
/*     */   }
/*     */   
/*     */   protected List<ItemOverride> getOverrides(JsonDeserializationContext $$0, JsonObject $$1) {
/* 329 */     List<ItemOverride> $$2 = Lists.newArrayList();
/* 330 */     if ($$1.has("overrides")) {
/* 331 */       JsonArray $$3 = GsonHelper.getAsJsonArray($$1, "overrides");
/* 332 */       for (JsonElement $$4 : $$3) {
/* 333 */         $$2.add((ItemOverride)$$0.deserialize($$4, ItemOverride.class));
/*     */       }
/*     */     } 
/* 336 */     return $$2;
/*     */   }
/*     */   
/*     */   private Map<String, Either<Material, String>> getTextureMap(JsonObject $$0) {
/* 340 */     ResourceLocation $$1 = TextureAtlas.LOCATION_BLOCKS;
/*     */     
/* 342 */     Map<String, Either<Material, String>> $$2 = Maps.newHashMap();
/*     */     
/* 344 */     if ($$0.has("textures")) {
/* 345 */       JsonObject $$3 = GsonHelper.getAsJsonObject($$0, "textures");
/* 346 */       for (Map.Entry<String, JsonElement> $$4 : (Iterable<Map.Entry<String, JsonElement>>)$$3.entrySet()) {
/* 347 */         $$2.put($$4.getKey(), parseTextureLocationOrReference($$1, ((JsonElement)$$4.getValue()).getAsString()));
/*     */       }
/*     */     } 
/*     */     
/* 351 */     return $$2;
/*     */   }
/*     */   
/*     */   private static Either<Material, String> parseTextureLocationOrReference(ResourceLocation $$0, String $$1) {
/* 355 */     if (BlockModel.isTextureReference($$1)) {
/* 356 */       return Either.right($$1.substring(1));
/*     */     }
/* 358 */     ResourceLocation $$2 = ResourceLocation.tryParse($$1);
/* 359 */     if ($$2 == null) {
/* 360 */       throw new JsonParseException($$1 + " is not valid resource location");
/*     */     }
/* 362 */     return Either.left(new Material($$0, $$2));
/*     */   }
/*     */ 
/*     */   
/*     */   private String getParentName(JsonObject $$0) {
/* 367 */     return GsonHelper.getAsString($$0, "parent", "");
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected Boolean getAmbientOcclusion(JsonObject $$0) {
/* 372 */     if ($$0.has("ambientocclusion")) {
/* 373 */       return Boolean.valueOf(GsonHelper.getAsBoolean($$0, "ambientocclusion"));
/*     */     }
/* 375 */     return null;
/*     */   }
/*     */   
/*     */   protected List<BlockElement> getElements(JsonDeserializationContext $$0, JsonObject $$1) {
/* 379 */     List<BlockElement> $$2 = Lists.newArrayList();
/*     */     
/* 381 */     if ($$1.has("elements")) {
/* 382 */       for (JsonElement $$3 : GsonHelper.getAsJsonArray($$1, "elements")) {
/* 383 */         $$2.add((BlockElement)$$0.deserialize($$3, BlockElement.class));
/*     */       }
/*     */     }
/* 386 */     return $$2;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\block\model\BlockModel$Deserializer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */