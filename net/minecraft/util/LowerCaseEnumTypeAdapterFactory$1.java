/*    */ package net.minecraft.util;
/*    */ 
/*    */ import com.google.gson.TypeAdapter;
/*    */ import com.google.gson.stream.JsonReader;
/*    */ import com.google.gson.stream.JsonToken;
/*    */ import com.google.gson.stream.JsonWriter;
/*    */ import java.io.IOException;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nullable;
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
/*    */ class null
/*    */   extends TypeAdapter<T>
/*    */ {
/*    */   public void write(JsonWriter $$0, T $$1) throws IOException {
/* 37 */     if ($$1 == null) {
/* 38 */       $$0.nullValue();
/*    */     } else {
/* 40 */       $$0.value(LowerCaseEnumTypeAdapterFactory.this.toLowercase($$1));
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public T read(JsonReader $$0) throws IOException {
/* 47 */     if ($$0.peek() == JsonToken.NULL) {
/* 48 */       $$0.nextNull();
/* 49 */       return null;
/*    */     } 
/* 51 */     return (T)lowercaseToConstant.get($$0.nextString());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\LowerCaseEnumTypeAdapterFactory$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */