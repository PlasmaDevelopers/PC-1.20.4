/*     */ package net.minecraft.client.renderer.block.model;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonDeserializer;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.renderer.block.model.multipart.MultiPart;
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
/*     */ public class Deserializer
/*     */   implements JsonDeserializer<BlockModelDefinition>
/*     */ {
/*     */   public BlockModelDefinition deserialize(JsonElement $$0, Type $$1, JsonDeserializationContext $$2) throws JsonParseException {
/* 142 */     JsonObject $$3 = $$0.getAsJsonObject();
/*     */     
/* 144 */     Map<String, MultiVariant> $$4 = getVariants($$2, $$3);
/* 145 */     MultiPart $$5 = getMultiPart($$2, $$3);
/*     */     
/* 147 */     if ($$4.isEmpty() && ($$5 == null || $$5.getMultiVariants().isEmpty())) {
/* 148 */       throw new JsonParseException("Neither 'variants' nor 'multipart' found");
/*     */     }
/*     */     
/* 151 */     return new BlockModelDefinition($$4, $$5);
/*     */   }
/*     */   
/*     */   protected Map<String, MultiVariant> getVariants(JsonDeserializationContext $$0, JsonObject $$1) {
/* 155 */     Map<String, MultiVariant> $$2 = Maps.newHashMap();
/*     */     
/* 157 */     if ($$1.has("variants")) {
/* 158 */       JsonObject $$3 = GsonHelper.getAsJsonObject($$1, "variants");
/* 159 */       for (Map.Entry<String, JsonElement> $$4 : (Iterable<Map.Entry<String, JsonElement>>)$$3.entrySet()) {
/* 160 */         $$2.put($$4.getKey(), (MultiVariant)$$0.deserialize($$4.getValue(), MultiVariant.class));
/*     */       }
/*     */     } 
/*     */     
/* 164 */     return $$2;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected MultiPart getMultiPart(JsonDeserializationContext $$0, JsonObject $$1) {
/* 169 */     if (!$$1.has("multipart")) {
/* 170 */       return null;
/*     */     }
/*     */     
/* 173 */     JsonArray $$2 = GsonHelper.getAsJsonArray($$1, "multipart");
/* 174 */     return (MultiPart)$$0.deserialize((JsonElement)$$2, MultiPart.class);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\block\model\BlockModelDefinition$Deserializer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */