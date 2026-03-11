/*    */ package net.minecraft.util.datafix.schemas;
/*    */ 
/*    */ import com.mojang.serialization.DataResult;
/*    */ import com.mojang.serialization.DynamicOps;
/*    */ import com.mojang.serialization.codecs.PrimitiveCodec;
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
/*    */ class null
/*    */   implements PrimitiveCodec<String>
/*    */ {
/*    */   public <T> DataResult<String> read(DynamicOps<T> $$0, T $$1) {
/* 28 */     return $$0
/* 29 */       .getStringValue($$1)
/* 30 */       .map(NamespacedSchema::ensureNamespaced);
/*    */   }
/*    */ 
/*    */   
/*    */   public <T> T write(DynamicOps<T> $$0, String $$1) {
/* 35 */     return (T)$$0.createString($$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 40 */     return "NamespacedString";
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\schemas\NamespacedSchema$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */