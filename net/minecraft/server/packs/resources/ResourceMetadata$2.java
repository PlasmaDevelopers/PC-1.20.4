/*    */ package net.minecraft.server.packs.resources;
/*    */ 
/*    */ import com.google.gson.JsonObject;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
/*    */ import net.minecraft.util.GsonHelper;
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
/*    */   implements ResourceMetadata
/*    */ {
/*    */   public <T> Optional<T> getSection(MetadataSectionSerializer<T> $$0) {
/* 32 */     String $$1 = $$0.getMetadataSectionName();
/* 33 */     return metadata.has($$1) ? Optional.<T>of((T)$$0.fromJson(GsonHelper.getAsJsonObject(metadata, $$1))) : Optional.<T>empty();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\packs\resources\ResourceMetadata$2.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */