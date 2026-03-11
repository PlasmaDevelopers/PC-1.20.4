/*    */ package net.minecraft.server.packs.resources;
/*    */ 
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
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
/*    */ public class Builder
/*    */ {
/* 54 */   private final ImmutableMap.Builder<MetadataSectionSerializer<?>, Object> map = ImmutableMap.builder();
/*    */   
/*    */   public <T> Builder put(MetadataSectionSerializer<T> $$0, T $$1) {
/* 57 */     this.map.put($$0, $$1);
/* 58 */     return this;
/*    */   }
/*    */   
/*    */   public ResourceMetadata build() {
/* 62 */     final ImmutableMap<MetadataSectionSerializer<?>, Object> map = this.map.build();
/* 63 */     if ($$0.isEmpty()) {
/* 64 */       return ResourceMetadata.EMPTY;
/*    */     }
/* 66 */     return new ResourceMetadata()
/*    */       {
/*    */         public <T> Optional<T> getSection(MetadataSectionSerializer<T> $$0)
/*    */         {
/* 70 */           return Optional.ofNullable((T)map.get($$0));
/*    */         }
/*    */       };
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\packs\resources\ResourceMetadata$Builder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */