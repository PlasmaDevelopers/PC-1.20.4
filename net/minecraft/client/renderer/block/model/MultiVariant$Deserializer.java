/*    */ package net.minecraft.client.renderer.block.model;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import com.google.gson.JsonArray;
/*    */ import com.google.gson.JsonDeserializationContext;
/*    */ import com.google.gson.JsonDeserializer;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonParseException;
/*    */ import java.lang.reflect.Type;
/*    */ import java.util.List;
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
/*    */   implements JsonDeserializer<MultiVariant>
/*    */ {
/*    */   public MultiVariant deserialize(JsonElement $$0, Type $$1, JsonDeserializationContext $$2) throws JsonParseException {
/* 84 */     List<Variant> $$3 = Lists.newArrayList();
/* 85 */     if ($$0.isJsonArray()) {
/* 86 */       JsonArray $$4 = $$0.getAsJsonArray();
/* 87 */       if ($$4.size() == 0) {
/* 88 */         throw new JsonParseException("Empty variant array");
/*    */       }
/*    */       
/* 91 */       for (JsonElement $$5 : $$4) {
/* 92 */         $$3.add((Variant)$$2.deserialize($$5, Variant.class));
/*    */       }
/*    */     } else {
/* 95 */       $$3.add((Variant)$$2.deserialize($$0, Variant.class));
/*    */     } 
/* 97 */     return new MultiVariant($$3);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\block\model\MultiVariant$Deserializer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */