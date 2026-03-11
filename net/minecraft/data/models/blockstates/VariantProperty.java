/*    */ package net.minecraft.data.models.blockstates;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import java.util.function.Function;
/*    */ 
/*    */ public class VariantProperty<T>
/*    */ {
/*    */   final String key;
/*    */   final Function<T, JsonElement> serializer;
/*    */   
/*    */   public VariantProperty(String $$0, Function<T, JsonElement> $$1) {
/* 13 */     this.key = $$0;
/* 14 */     this.serializer = $$1;
/*    */   }
/*    */   
/*    */   public Value withValue(T $$0) {
/* 18 */     return new Value($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 23 */     return this.key;
/*    */   }
/*    */   
/*    */   public class Value {
/*    */     private final T value;
/*    */     
/*    */     public Value(T $$1) {
/* 30 */       this.value = $$1;
/*    */     }
/*    */     
/*    */     public VariantProperty<T> getKey() {
/* 34 */       return VariantProperty.this;
/*    */     }
/*    */     
/*    */     public void addToVariant(JsonObject $$0) {
/* 38 */       $$0.add(VariantProperty.this.key, VariantProperty.this.serializer.apply(this.value));
/*    */     }
/*    */ 
/*    */     
/*    */     public String toString() {
/* 43 */       return VariantProperty.this.key + "=" + VariantProperty.this.key;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\models\blockstates\VariantProperty.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */