/*    */ package net.minecraft.util;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import com.google.gson.Gson;
/*    */ import com.google.gson.TypeAdapter;
/*    */ import com.google.gson.TypeAdapterFactory;
/*    */ import com.google.gson.reflect.TypeToken;
/*    */ import com.google.gson.stream.JsonReader;
/*    */ import com.google.gson.stream.JsonToken;
/*    */ import com.google.gson.stream.JsonWriter;
/*    */ import java.io.IOException;
/*    */ import java.util.Locale;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LowerCaseEnumTypeAdapterFactory
/*    */   implements TypeAdapterFactory
/*    */ {
/*    */   @Nullable
/*    */   public <T> TypeAdapter<T> create(Gson $$0, TypeToken<T> $$1) {
/* 24 */     Class<T> $$2 = $$1.getRawType();
/* 25 */     if (!$$2.isEnum()) {
/* 26 */       return null;
/*    */     }
/*    */     
/* 29 */     final Map<String, T> lowercaseToConstant = Maps.newHashMap();
/* 30 */     for (T $$4 : $$2.getEnumConstants()) {
/* 31 */       $$3.put(toLowercase($$4), $$4);
/*    */     }
/*    */     
/* 34 */     return new TypeAdapter<T>()
/*    */       {
/*    */         public void write(JsonWriter $$0, T $$1) throws IOException {
/* 37 */           if ($$1 == null) {
/* 38 */             $$0.nullValue();
/*    */           } else {
/* 40 */             $$0.value(LowerCaseEnumTypeAdapterFactory.this.toLowercase($$1));
/*    */           } 
/*    */         }
/*    */ 
/*    */         
/*    */         @Nullable
/*    */         public T read(JsonReader $$0) throws IOException {
/* 47 */           if ($$0.peek() == JsonToken.NULL) {
/* 48 */             $$0.nextNull();
/* 49 */             return null;
/*    */           } 
/* 51 */           return (T)lowercaseToConstant.get($$0.nextString());
/*    */         }
/*    */       };
/*    */   }
/*    */ 
/*    */   
/*    */   String toLowercase(Object $$0) {
/* 58 */     if ($$0 instanceof Enum) {
/* 59 */       return ((Enum)$$0).name().toLowerCase(Locale.ROOT);
/*    */     }
/* 61 */     return $$0.toString().toLowerCase(Locale.ROOT);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\LowerCaseEnumTypeAdapterFactory.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */