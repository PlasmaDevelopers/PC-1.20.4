/*    */ package net.minecraft.data.models.blockstates;
/*    */ 
/*    */ import com.google.gson.JsonObject;
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
/*    */ public class Value
/*    */ {
/*    */   private final T value;
/*    */   
/*    */   public Value(T $$1) {
/* 30 */     this.value = $$1;
/*    */   }
/*    */   
/*    */   public VariantProperty<T> getKey() {
/* 34 */     return VariantProperty.this;
/*    */   }
/*    */   
/*    */   public void addToVariant(JsonObject $$0) {
/* 38 */     $$0.add(VariantProperty.this.key, VariantProperty.this.serializer.apply(this.value));
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 43 */     return VariantProperty.this.key + "=" + VariantProperty.this.key;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\models\blockstates\VariantProperty$Value.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */