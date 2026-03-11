/*     */ package net.minecraft.client.renderer.block.model.multipart;
/*     */ 
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import com.google.common.collect.Streams;
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonDeserializer;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.stream.Collectors;
/*     */ import net.minecraft.client.renderer.block.model.MultiVariant;
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
/*     */ public class Deserializer
/*     */   implements JsonDeserializer<Selector>
/*     */ {
/*     */   public Selector deserialize(JsonElement $$0, Type $$1, JsonDeserializationContext $$2) throws JsonParseException {
/*  59 */     JsonObject $$3 = $$0.getAsJsonObject();
/*     */     
/*  61 */     return new Selector(getSelector($$3), (MultiVariant)$$2.deserialize($$3.get("apply"), MultiVariant.class));
/*     */   }
/*     */   
/*     */   private Condition getSelector(JsonObject $$0) {
/*  65 */     if ($$0.has("when")) {
/*  66 */       return getCondition(GsonHelper.getAsJsonObject($$0, "when"));
/*     */     }
/*     */     
/*  69 */     return Condition.TRUE;
/*     */   }
/*     */   
/*     */   @VisibleForTesting
/*     */   static Condition getCondition(JsonObject $$0) {
/*  74 */     Set<Map.Entry<String, JsonElement>> $$1 = $$0.entrySet();
/*     */     
/*  76 */     if ($$1.isEmpty()) {
/*  77 */       throw new JsonParseException("No elements found in selector");
/*     */     }
/*     */     
/*  80 */     if ($$1.size() == 1) {
/*  81 */       if ($$0.has("OR")) {
/*     */ 
/*     */         
/*  84 */         List<Condition> $$2 = (List<Condition>)Streams.stream((Iterable)GsonHelper.getAsJsonArray($$0, "OR")).map($$0 -> getCondition($$0.getAsJsonObject())).collect(Collectors.toList());
/*  85 */         return new OrCondition($$2);
/*  86 */       }  if ($$0.has("AND")) {
/*     */ 
/*     */         
/*  89 */         List<Condition> $$3 = (List<Condition>)Streams.stream((Iterable)GsonHelper.getAsJsonArray($$0, "AND")).map($$0 -> getCondition($$0.getAsJsonObject())).collect(Collectors.toList());
/*  90 */         return new AndCondition($$3);
/*     */       } 
/*  92 */       return getKeyValueCondition($$1.iterator().next());
/*     */     } 
/*     */     
/*  95 */     return new AndCondition((Iterable<? extends Condition>)$$1.stream().map(Deserializer::getKeyValueCondition).collect(Collectors.toList()));
/*     */   }
/*     */ 
/*     */   
/*     */   private static Condition getKeyValueCondition(Map.Entry<String, JsonElement> $$0) {
/* 100 */     return new KeyValueCondition($$0.getKey(), ((JsonElement)$$0.getValue()).getAsString());
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\block\model\multipart\Selector$Deserializer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */