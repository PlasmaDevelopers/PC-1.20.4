/*    */ package net.minecraft.data.models.model;
/*    */ 
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import com.google.common.collect.Streams;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import java.util.Map;
/*    */ import java.util.Objects;
/*    */ import java.util.Optional;
/*    */ import java.util.Set;
/*    */ import java.util.function.BiConsumer;
/*    */ import java.util.function.Function;
/*    */ import java.util.function.Supplier;
/*    */ import java.util.stream.Stream;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ 
/*    */ public class ModelTemplate
/*    */ {
/*    */   private final Optional<ResourceLocation> model;
/*    */   private final Set<TextureSlot> requiredSlots;
/*    */   private final Optional<String> suffix;
/*    */   
/*    */   public ModelTemplate(Optional<ResourceLocation> $$0, Optional<String> $$1, TextureSlot... $$2) {
/* 26 */     this.model = $$0;
/* 27 */     this.suffix = $$1;
/* 28 */     this.requiredSlots = (Set<TextureSlot>)ImmutableSet.copyOf((Object[])$$2);
/*    */   }
/*    */   
/*    */   public ResourceLocation getDefaultModelLocation(Block $$0) {
/* 32 */     return ModelLocationUtils.getModelLocation($$0, this.suffix.orElse(""));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ResourceLocation create(Block $$0, TextureMapping $$1, BiConsumer<ResourceLocation, Supplier<JsonElement>> $$2) {
/* 40 */     return create(ModelLocationUtils.getModelLocation($$0, this.suffix.orElse("")), $$1, $$2);
/*    */   }
/*    */   
/*    */   public ResourceLocation createWithSuffix(Block $$0, String $$1, TextureMapping $$2, BiConsumer<ResourceLocation, Supplier<JsonElement>> $$3) {
/* 44 */     return create(ModelLocationUtils.getModelLocation($$0, $$1 + $$1), $$2, $$3);
/*    */   }
/*    */   
/*    */   public ResourceLocation createWithOverride(Block $$0, String $$1, TextureMapping $$2, BiConsumer<ResourceLocation, Supplier<JsonElement>> $$3) {
/* 48 */     return create(ModelLocationUtils.getModelLocation($$0, $$1), $$2, $$3);
/*    */   }
/*    */   
/*    */   public ResourceLocation create(ResourceLocation $$0, TextureMapping $$1, BiConsumer<ResourceLocation, Supplier<JsonElement>> $$2) {
/* 52 */     return create($$0, $$1, $$2, this::createBaseTemplate);
/*    */   }
/*    */   
/*    */   public ResourceLocation create(ResourceLocation $$0, TextureMapping $$1, BiConsumer<ResourceLocation, Supplier<JsonElement>> $$2, JsonFactory $$3) {
/* 56 */     Map<TextureSlot, ResourceLocation> $$4 = createMap($$1);
/* 57 */     $$2.accept($$0, () -> $$0.create($$1, $$2));
/* 58 */     return $$0;
/*    */   }
/*    */   
/*    */   public JsonObject createBaseTemplate(ResourceLocation $$0, Map<TextureSlot, ResourceLocation> $$1) {
/* 62 */     JsonObject $$2 = new JsonObject();
/* 63 */     this.model.ifPresent($$1 -> $$0.addProperty("parent", $$1.toString()));
/* 64 */     if (!$$1.isEmpty()) {
/* 65 */       JsonObject $$3 = new JsonObject();
/* 66 */       $$1.forEach(($$1, $$2) -> $$0.addProperty($$1.getId(), $$2.toString()));
/* 67 */       $$2.add("textures", (JsonElement)$$3);
/*    */     } 
/* 69 */     return $$2;
/*    */   }
/*    */   
/*    */   private Map<TextureSlot, ResourceLocation> createMap(TextureMapping $$0) {
/* 73 */     Objects.requireNonNull($$0); return (Map<TextureSlot, ResourceLocation>)Streams.concat(new Stream[] { this.requiredSlots.stream(), $$0.getForced() }).collect(ImmutableMap.toImmutableMap(Function.identity(), $$0::get));
/*    */   }
/*    */   
/*    */   public static interface JsonFactory {
/*    */     JsonObject create(ResourceLocation param1ResourceLocation, Map<TextureSlot, ResourceLocation> param1Map);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\models\model\ModelTemplate.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */