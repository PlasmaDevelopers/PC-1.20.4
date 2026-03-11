/*    */ package net.minecraft.data.models.blockstates;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import com.google.gson.JsonArray;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.function.Supplier;
/*    */ 
/*    */ public class Variant
/*    */   implements Supplier<JsonElement> {
/* 13 */   private final Map<VariantProperty<?>, VariantProperty<?>.Value> values = Maps.newLinkedHashMap();
/*    */   
/*    */   public <T> Variant with(VariantProperty<T> $$0, T $$1) {
/* 16 */     VariantProperty<?>.Value $$2 = this.values.put($$0, $$0.withValue($$1));
/* 17 */     if ($$2 != null) {
/* 18 */       throw new IllegalStateException("Replacing value of " + $$2 + " with " + $$1);
/*    */     }
/* 20 */     return this;
/*    */   }
/*    */   
/*    */   public static Variant variant() {
/* 24 */     return new Variant();
/*    */   }
/*    */   
/*    */   public static Variant merge(Variant $$0, Variant $$1) {
/* 28 */     Variant $$2 = new Variant();
/* 29 */     $$2.values.putAll($$0.values);
/* 30 */     $$2.values.putAll($$1.values);
/* 31 */     return $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   public JsonElement get() {
/* 36 */     JsonObject $$0 = new JsonObject();
/* 37 */     this.values.values().forEach($$1 -> $$1.addToVariant($$0));
/* 38 */     return (JsonElement)$$0;
/*    */   }
/*    */   
/*    */   public static JsonElement convertList(List<Variant> $$0) {
/* 42 */     if ($$0.size() == 1) {
/* 43 */       return ((Variant)$$0.get(0)).get();
/*    */     }
/*    */     
/* 46 */     JsonArray $$1 = new JsonArray();
/* 47 */     $$0.forEach($$1 -> $$0.add($$1.get()));
/* 48 */     return (JsonElement)$$1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\models\blockstates\Variant.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */