/*    */ package net.minecraft.util.eventlog;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonParseException;
/*    */ import com.google.gson.JsonParser;
/*    */ import com.google.gson.stream.JsonReader;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.DynamicOps;
/*    */ import com.mojang.serialization.JsonOps;
/*    */ import java.io.Closeable;
/*    */ import java.io.EOFException;
/*    */ import java.io.IOException;
/*    */ import java.io.Reader;
/*    */ import java.util.function.Function;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.Util;
/*    */ 
/*    */ public interface JsonEventLogReader<T> extends Closeable {
/*    */   static <T> JsonEventLogReader<T> create(final Codec<T> codec, Reader $$1) {
/* 19 */     final JsonReader jsonReader = new JsonReader($$1);
/* 20 */     $$2.setLenient(true);
/* 21 */     return new JsonEventLogReader<T>()
/*    */       {
/*    */         @Nullable
/*    */         public T next() throws IOException {
/*    */           try {
/* 26 */             if (!jsonReader.hasNext()) {
/* 27 */               return null;
/*    */             }
/* 29 */             JsonElement $$0 = JsonParser.parseReader(jsonReader);
/* 30 */             return (T)Util.getOrThrow(codec.parse((DynamicOps)JsonOps.INSTANCE, $$0), IOException::new);
/* 31 */           } catch (JsonParseException $$1) {
/* 32 */             throw new IOException($$1);
/* 33 */           } catch (EOFException $$2) {
/*    */             
/* 35 */             return null;
/*    */           } 
/*    */         }
/*    */ 
/*    */         
/*    */         public void close() throws IOException {
/* 41 */           jsonReader.close();
/*    */         }
/*    */       };
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   T next() throws IOException;
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\eventlog\JsonEventLogReader.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */