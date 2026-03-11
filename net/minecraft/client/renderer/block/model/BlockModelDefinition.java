/*     */ package net.minecraft.client.renderer.block.model;
/*     */ 
/*     */ import com.google.common.annotations.VisibleForTesting;
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
/*     */ import java.io.Reader;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.renderer.block.model.multipart.MultiPart;
/*     */ import net.minecraft.client.renderer.block.model.multipart.Selector;
/*     */ import net.minecraft.util.GsonHelper;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockModelDefinition
/*     */ {
/*     */   public static final class Context
/*     */   {
/*  34 */     protected final Gson gson = (new GsonBuilder())
/*  35 */       .registerTypeAdapter(BlockModelDefinition.class, new BlockModelDefinition.Deserializer())
/*  36 */       .registerTypeAdapter(Variant.class, new Variant.Deserializer())
/*  37 */       .registerTypeAdapter(MultiVariant.class, new MultiVariant.Deserializer())
/*  38 */       .registerTypeAdapter(MultiPart.class, new MultiPart.Deserializer(this))
/*  39 */       .registerTypeAdapter(Selector.class, new Selector.Deserializer())
/*  40 */       .create();
/*     */     private StateDefinition<Block, BlockState> definition;
/*     */     
/*     */     public StateDefinition<Block, BlockState> getDefinition() {
/*  44 */       return this.definition;
/*     */     }
/*     */     
/*     */     public void setDefinition(StateDefinition<Block, BlockState> $$0) {
/*  48 */       this.definition = $$0;
/*     */     }
/*     */   }
/*     */   
/*  52 */   private final Map<String, MultiVariant> variants = Maps.newLinkedHashMap();
/*     */   private MultiPart multiPart;
/*     */   
/*     */   public static BlockModelDefinition fromStream(Context $$0, Reader $$1) {
/*  56 */     return (BlockModelDefinition)GsonHelper.fromJson($$0.gson, $$1, BlockModelDefinition.class);
/*     */   }
/*     */   
/*     */   public static BlockModelDefinition fromJsonElement(Context $$0, JsonElement $$1) {
/*  60 */     return (BlockModelDefinition)$$0.gson.fromJson($$1, BlockModelDefinition.class);
/*     */   }
/*     */   
/*     */   public BlockModelDefinition(Map<String, MultiVariant> $$0, MultiPart $$1) {
/*  64 */     this.multiPart = $$1;
/*  65 */     this.variants.putAll($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockModelDefinition(List<BlockModelDefinition> $$0) {
/*  70 */     BlockModelDefinition $$1 = null;
/*  71 */     for (BlockModelDefinition $$2 : $$0) {
/*  72 */       if ($$2.isMultiPart()) {
/*  73 */         this.variants.clear();
/*  74 */         $$1 = $$2;
/*     */       } 
/*  76 */       this.variants.putAll($$2.variants);
/*     */     } 
/*     */     
/*  79 */     if ($$1 != null) {
/*  80 */       this.multiPart = $$1.multiPart;
/*     */     }
/*     */   }
/*     */   
/*     */   @VisibleForTesting
/*     */   public boolean hasVariant(String $$0) {
/*  86 */     return (this.variants.get($$0) != null);
/*     */   }
/*     */   
/*     */   @VisibleForTesting
/*     */   public MultiVariant getVariant(String $$0) {
/*  91 */     MultiVariant $$1 = this.variants.get($$0);
/*  92 */     if ($$1 == null) {
/*  93 */       throw new MissingVariantException();
/*     */     }
/*  95 */     return $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object $$0) {
/* 100 */     if (this == $$0) {
/* 101 */       return true;
/*     */     }
/* 103 */     if ($$0 instanceof BlockModelDefinition) { BlockModelDefinition $$1 = (BlockModelDefinition)$$0;
/* 104 */       if (this.variants.equals($$1.variants)) {
/* 105 */         return isMultiPart() ? this.multiPart.equals($$1.multiPart) : (!$$1.isMultiPart());
/*     */       } }
/*     */     
/* 108 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 113 */     return 31 * this.variants.hashCode() + (isMultiPart() ? this.multiPart.hashCode() : 0);
/*     */   }
/*     */   
/*     */   public Map<String, MultiVariant> getVariants() {
/* 117 */     return this.variants;
/*     */   }
/*     */   
/*     */   @VisibleForTesting
/*     */   public Set<MultiVariant> getMultiVariants() {
/* 122 */     Set<MultiVariant> $$0 = Sets.newHashSet(this.variants.values());
/*     */     
/* 124 */     if (isMultiPart()) {
/* 125 */       $$0.addAll(this.multiPart.getMultiVariants());
/*     */     }
/*     */     
/* 128 */     return $$0;
/*     */   }
/*     */   
/*     */   public boolean isMultiPart() {
/* 132 */     return (this.multiPart != null);
/*     */   }
/*     */   
/*     */   public MultiPart getMultiPart() {
/* 136 */     return this.multiPart;
/*     */   }
/*     */   
/*     */   public static class Deserializer
/*     */     implements JsonDeserializer<BlockModelDefinition> {
/*     */     public BlockModelDefinition deserialize(JsonElement $$0, Type $$1, JsonDeserializationContext $$2) throws JsonParseException {
/* 142 */       JsonObject $$3 = $$0.getAsJsonObject();
/*     */       
/* 144 */       Map<String, MultiVariant> $$4 = getVariants($$2, $$3);
/* 145 */       MultiPart $$5 = getMultiPart($$2, $$3);
/*     */       
/* 147 */       if ($$4.isEmpty() && ($$5 == null || $$5.getMultiVariants().isEmpty())) {
/* 148 */         throw new JsonParseException("Neither 'variants' nor 'multipart' found");
/*     */       }
/*     */       
/* 151 */       return new BlockModelDefinition($$4, $$5);
/*     */     }
/*     */     
/*     */     protected Map<String, MultiVariant> getVariants(JsonDeserializationContext $$0, JsonObject $$1) {
/* 155 */       Map<String, MultiVariant> $$2 = Maps.newHashMap();
/*     */       
/* 157 */       if ($$1.has("variants")) {
/* 158 */         JsonObject $$3 = GsonHelper.getAsJsonObject($$1, "variants");
/* 159 */         for (Map.Entry<String, JsonElement> $$4 : (Iterable<Map.Entry<String, JsonElement>>)$$3.entrySet()) {
/* 160 */           $$2.put($$4.getKey(), (MultiVariant)$$0.deserialize($$4.getValue(), MultiVariant.class));
/*     */         }
/*     */       } 
/*     */       
/* 164 */       return $$2;
/*     */     }
/*     */     
/*     */     @Nullable
/*     */     protected MultiPart getMultiPart(JsonDeserializationContext $$0, JsonObject $$1) {
/* 169 */       if (!$$1.has("multipart")) {
/* 170 */         return null;
/*     */       }
/*     */       
/* 173 */       JsonArray $$2 = GsonHelper.getAsJsonArray($$1, "multipart");
/* 174 */       return (MultiPart)$$0.deserialize((JsonElement)$$2, MultiPart.class);
/*     */     }
/*     */   }
/*     */   
/*     */   protected class MissingVariantException extends RuntimeException {}
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\block\model\BlockModelDefinition.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */