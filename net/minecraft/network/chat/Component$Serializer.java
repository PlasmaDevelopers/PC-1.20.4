/*     */ package net.minecraft.network.chat;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonParser;
/*     */ import com.google.gson.stream.JsonReader;
/*     */ import com.mojang.serialization.DynamicOps;
/*     */ import com.mojang.serialization.JsonOps;
/*     */ import java.io.StringReader;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Serializer
/*     */ {
/*     */   static MutableComponent deserialize(JsonElement $$0) {
/* 159 */     return (MutableComponent)Util.getOrThrow(ComponentSerialization.CODEC.parse((DynamicOps)JsonOps.INSTANCE, $$0), com.google.gson.JsonParseException::new);
/*     */   }
/*     */   
/*     */   static JsonElement serialize(Component $$0) {
/* 163 */     return (JsonElement)Util.getOrThrow(ComponentSerialization.CODEC.encodeStart((DynamicOps)JsonOps.INSTANCE, $$0), com.google.gson.JsonParseException::new);
/*     */   }
/*     */   
/* 166 */   private static final Gson GSON = (new GsonBuilder()).disableHtmlEscaping().create();
/*     */   
/*     */   public static String toJson(Component $$0) {
/* 169 */     return GSON.toJson(serialize($$0));
/*     */   }
/*     */   
/*     */   public static JsonElement toJsonTree(Component $$0) {
/* 173 */     return serialize($$0);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static MutableComponent fromJson(String $$0) {
/* 178 */     JsonElement $$1 = JsonParser.parseString($$0);
/* 179 */     if ($$1 == null) {
/* 180 */       return null;
/*     */     }
/* 182 */     return deserialize($$1);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static MutableComponent fromJson(@Nullable JsonElement $$0) {
/* 187 */     if ($$0 == null) {
/* 188 */       return null;
/*     */     }
/* 190 */     return deserialize($$0);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static MutableComponent fromJsonLenient(String $$0) {
/* 195 */     JsonReader $$1 = new JsonReader(new StringReader($$0));
/* 196 */     $$1.setLenient(true);
/* 197 */     JsonElement $$2 = JsonParser.parseReader($$1);
/* 198 */     if ($$2 == null) {
/* 199 */       return null;
/*     */     }
/* 201 */     return deserialize($$2);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\chat\Component$Serializer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */