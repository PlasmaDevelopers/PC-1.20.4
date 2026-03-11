/*    */ package net.minecraft.server.packs.resources;
/*    */ 
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import com.google.gson.JsonObject;
/*    */ import java.io.BufferedReader;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.InputStreamReader;
/*    */ import java.nio.charset.StandardCharsets;
/*    */ import java.util.Collection;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
/*    */ import net.minecraft.util.GsonHelper;
/*    */ 
/*    */ public interface ResourceMetadata
/*    */ {
/* 17 */   public static final ResourceMetadata EMPTY = new ResourceMetadata()
/*    */     {
/*    */       public <T> Optional<T> getSection(MetadataSectionSerializer<T> $$0) {
/* 20 */         return Optional.empty();
/*    */       }
/*    */     };
/*    */   
/*    */   public static final IoSupplier<ResourceMetadata> EMPTY_SUPPLIER = () -> EMPTY;
/*    */   
/* 26 */   static ResourceMetadata fromJsonStream(InputStream $$0) throws IOException { BufferedReader $$1 = new BufferedReader(new InputStreamReader($$0, StandardCharsets.UTF_8)); 
/* 27 */     try { final JsonObject metadata = GsonHelper.parse($$1);
/*    */       
/* 29 */       ResourceMetadata resourceMetadata = new ResourceMetadata()
/*    */         {
/*    */           public <T> Optional<T> getSection(MetadataSectionSerializer<T> $$0) {
/* 32 */             String $$1 = $$0.getMetadataSectionName();
/* 33 */             return metadata.has($$1) ? Optional.<T>of((T)$$0.fromJson(GsonHelper.getAsJsonObject(metadata, $$1))) : Optional.<T>empty();
/*    */           }
/*    */         };
/* 36 */       $$1.close(); return resourceMetadata; }
/*    */     catch (Throwable throwable) { try {
/*    */         $$1.close();
/*    */       } catch (Throwable throwable1) {
/*    */         throwable.addSuppressed(throwable1);
/*    */       }  throw throwable; }
/* 42 */      } default ResourceMetadata copySections(Collection<MetadataSectionSerializer<?>> $$0) { Builder $$1 = new Builder();
/* 43 */     for (MetadataSectionSerializer<?> $$2 : $$0) {
/* 44 */       copySection($$1, $$2);
/*    */     }
/* 46 */     return $$1.build(); }
/*    */ 
/*    */   
/*    */   private <T> void copySection(Builder $$0, MetadataSectionSerializer<T> $$1) {
/* 50 */     getSection($$1).ifPresent($$2 -> $$0.put($$1, $$2));
/*    */   }
/*    */   <T> Optional<T> getSection(MetadataSectionSerializer<T> paramMetadataSectionSerializer);
/*    */   
/* 54 */   public static class Builder { private final ImmutableMap.Builder<MetadataSectionSerializer<?>, Object> map = ImmutableMap.builder();
/*    */     
/*    */     public <T> Builder put(MetadataSectionSerializer<T> $$0, T $$1) {
/* 57 */       this.map.put($$0, $$1);
/* 58 */       return this;
/*    */     }
/*    */     
/*    */     public ResourceMetadata build() {
/* 62 */       final ImmutableMap<MetadataSectionSerializer<?>, Object> map = this.map.build();
/* 63 */       if ($$0.isEmpty()) {
/* 64 */         return ResourceMetadata.EMPTY;
/*    */       }
/* 66 */       return new ResourceMetadata()
/*    */         {
/*    */           public <T> Optional<T> getSection(MetadataSectionSerializer<T> $$0)
/*    */           {
/* 70 */             return Optional.ofNullable((T)map.get($$0)); } }; } } class null implements ResourceMetadata { public <T> Optional<T> getSection(MetadataSectionSerializer<T> $$0) { return Optional.ofNullable((T)map.get($$0)); }
/*    */      }
/*    */ 
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\packs\resources\ResourceMetadata.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */