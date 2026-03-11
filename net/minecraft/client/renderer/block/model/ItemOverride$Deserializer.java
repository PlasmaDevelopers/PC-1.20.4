/*    */ package net.minecraft.client.renderer.block.model;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import com.google.common.collect.Maps;
/*    */ import com.google.gson.JsonDeserializationContext;
/*    */ import com.google.gson.JsonDeserializer;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonParseException;
/*    */ import java.lang.reflect.Type;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.util.GsonHelper;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Deserializer
/*    */   implements JsonDeserializer<ItemOverride>
/*    */ {
/*    */   public ItemOverride deserialize(JsonElement $$0, Type $$1, JsonDeserializationContext $$2) throws JsonParseException {
/* 38 */     JsonObject $$3 = $$0.getAsJsonObject();
/*    */     
/* 40 */     ResourceLocation $$4 = new ResourceLocation(GsonHelper.getAsString($$3, "model"));
/* 41 */     List<ItemOverride.Predicate> $$5 = getPredicates($$3);
/*    */     
/* 43 */     return new ItemOverride($$4, $$5);
/*    */   }
/*    */   
/*    */   protected List<ItemOverride.Predicate> getPredicates(JsonObject $$0) {
/* 47 */     Map<ResourceLocation, Float> $$1 = Maps.newLinkedHashMap();
/*    */     
/* 49 */     JsonObject $$2 = GsonHelper.getAsJsonObject($$0, "predicate");
/* 50 */     for (Map.Entry<String, JsonElement> $$3 : (Iterable<Map.Entry<String, JsonElement>>)$$2.entrySet()) {
/* 51 */       $$1.put(new ResourceLocation($$3
/* 52 */             .getKey()), 
/* 53 */           Float.valueOf(GsonHelper.convertToFloat($$3.getValue(), $$3.getKey())));
/*    */     }
/*    */ 
/*    */     
/* 57 */     return (List<ItemOverride.Predicate>)$$1.entrySet().stream().map($$0 -> new ItemOverride.Predicate((ResourceLocation)$$0.getKey(), ((Float)$$0.getValue()).floatValue())).collect(ImmutableList.toImmutableList());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\block\model\ItemOverride$Deserializer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */