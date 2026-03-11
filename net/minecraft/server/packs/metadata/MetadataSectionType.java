/*    */ package net.minecraft.server.packs.metadata;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.DynamicOps;
/*    */ import com.mojang.serialization.JsonOps;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.Util;
/*    */ 
/*    */ public interface MetadataSectionType<T> extends MetadataSectionSerializer<T> {
/*    */   static <T> MetadataSectionType<T> fromCodec(final String name, final Codec<T> codec) {
/* 13 */     return new MetadataSectionType<T>()
/*    */       {
/*    */         public String getMetadataSectionName() {
/* 16 */           return name;
/*    */         }
/*    */ 
/*    */         
/*    */         public T fromJson(JsonObject $$0) {
/* 21 */           return (T)Util.getOrThrow(codec.parse((DynamicOps)JsonOps.INSTANCE, $$0), com.google.gson.JsonParseException::new);
/*    */         }
/*    */ 
/*    */         
/*    */         public JsonObject toJson(T $$0) {
/* 26 */           return ((JsonElement)Util.getOrThrow(codec.encodeStart((DynamicOps)JsonOps.INSTANCE, $$0), IllegalArgumentException::new)).getAsJsonObject();
/*    */         }
/*    */       };
/*    */   }
/*    */   
/*    */   JsonObject toJson(T paramT);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\packs\metadata\MetadataSectionType.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */