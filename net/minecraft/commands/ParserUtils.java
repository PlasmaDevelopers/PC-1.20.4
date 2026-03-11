/*    */ package net.minecraft.commands;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonParseException;
/*    */ import com.google.gson.internal.Streams;
/*    */ import com.google.gson.stream.JsonReader;
/*    */ import com.mojang.brigadier.StringReader;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.DynamicOps;
/*    */ import com.mojang.serialization.JsonOps;
/*    */ import java.io.StringReader;
/*    */ import java.lang.reflect.Field;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.Util;
/*    */ 
/*    */ public class ParserUtils {
/*    */   private static final Field JSON_READER_POS;
/*    */   
/*    */   static {
/* 19 */     JSON_READER_POS = (Field)Util.make(() -> {
/*    */           try {
/*    */             Field $$0 = JsonReader.class.getDeclaredField("pos");
/*    */             $$0.setAccessible(true);
/*    */             return $$0;
/* 24 */           } catch (NoSuchFieldException $$1) {
/*    */             throw new IllegalStateException("Couldn't get field 'pos' for JsonReader", $$1);
/*    */           } 
/*    */         });
/*    */     
/* 29 */     JSON_READER_LINESTART = (Field)Util.make(() -> {
/*    */           try {
/*    */             Field $$0 = JsonReader.class.getDeclaredField("lineStart");
/*    */             $$0.setAccessible(true);
/*    */             return $$0;
/* 34 */           } catch (NoSuchFieldException $$1) {
/*    */             throw new IllegalStateException("Couldn't get field 'lineStart' for JsonReader", $$1);
/*    */           } 
/*    */         });
/*    */   } private static final Field JSON_READER_LINESTART;
/*    */   private static int getPos(JsonReader $$0) {
/*    */     try {
/* 41 */       return JSON_READER_POS.getInt($$0) - JSON_READER_LINESTART.getInt($$0) + 1;
/* 42 */     } catch (IllegalAccessException $$1) {
/* 43 */       throw new IllegalStateException("Couldn't read position of JsonReader", $$1);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static <T> T parseJson(StringReader $$0, Codec<T> $$1) {
/* 48 */     JsonReader $$2 = new JsonReader(new StringReader($$0.getRemaining()));
/* 49 */     $$2.setLenient(false);
/*    */     try {
/* 51 */       JsonElement $$3 = Streams.parse($$2);
/* 52 */       return (T)Util.getOrThrow($$1.parse((DynamicOps)JsonOps.INSTANCE, $$3), JsonParseException::new);
/* 53 */     } catch (StackOverflowError $$4) {
/* 54 */       throw new JsonParseException($$4);
/*    */     } finally {
/* 56 */       $$0.setCursor($$0.getCursor() + getPos($$2));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\ParserUtils.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */