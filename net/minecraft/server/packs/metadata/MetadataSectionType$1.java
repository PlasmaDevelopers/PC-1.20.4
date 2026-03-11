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
/*    */ 
/*    */ class null
/*    */   implements MetadataSectionType<T>
/*    */ {
/*    */   public String getMetadataSectionName() {
/* 16 */     return name;
/*    */   }
/*    */ 
/*    */   
/*    */   public T fromJson(JsonObject $$0) {
/* 21 */     return (T)Util.getOrThrow(codec.parse((DynamicOps)JsonOps.INSTANCE, $$0), com.google.gson.JsonParseException::new);
/*    */   }
/*    */ 
/*    */   
/*    */   public JsonObject toJson(T $$0) {
/* 26 */     return ((JsonElement)Util.getOrThrow(codec.encodeStart((DynamicOps)JsonOps.INSTANCE, $$0), IllegalArgumentException::new)).getAsJsonObject();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\packs\metadata\MetadataSectionType$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */