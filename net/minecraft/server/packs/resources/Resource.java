/*    */ package net.minecraft.server.packs.resources;
/*    */ 
/*    */ import java.io.BufferedReader;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.InputStreamReader;
/*    */ import java.nio.charset.StandardCharsets;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.server.packs.PackResources;
/*    */ 
/*    */ 
/*    */ public class Resource
/*    */ {
/*    */   private final PackResources source;
/*    */   private final IoSupplier<InputStream> streamSupplier;
/*    */   private final IoSupplier<ResourceMetadata> metadataSupplier;
/*    */   @Nullable
/*    */   private ResourceMetadata cachedMetadata;
/*    */   
/*    */   public Resource(PackResources $$0, IoSupplier<InputStream> $$1, IoSupplier<ResourceMetadata> $$2) {
/* 21 */     this.source = $$0;
/* 22 */     this.streamSupplier = $$1;
/* 23 */     this.metadataSupplier = $$2;
/*    */   }
/*    */   
/*    */   public Resource(PackResources $$0, IoSupplier<InputStream> $$1) {
/* 27 */     this.source = $$0;
/* 28 */     this.streamSupplier = $$1;
/* 29 */     this.metadataSupplier = ResourceMetadata.EMPTY_SUPPLIER;
/* 30 */     this.cachedMetadata = ResourceMetadata.EMPTY;
/*    */   }
/*    */   
/*    */   public PackResources source() {
/* 34 */     return this.source;
/*    */   }
/*    */   
/*    */   public String sourcePackId() {
/* 38 */     return this.source.packId();
/*    */   }
/*    */   
/*    */   public boolean isBuiltin() {
/* 42 */     return this.source.isBuiltin();
/*    */   }
/*    */   
/*    */   public InputStream open() throws IOException {
/* 46 */     return this.streamSupplier.get();
/*    */   }
/*    */   
/*    */   public BufferedReader openAsReader() throws IOException {
/* 50 */     return new BufferedReader(new InputStreamReader(open(), StandardCharsets.UTF_8));
/*    */   }
/*    */   
/*    */   public ResourceMetadata metadata() throws IOException {
/* 54 */     if (this.cachedMetadata == null) {
/* 55 */       this.cachedMetadata = this.metadataSupplier.get();
/*    */     }
/* 57 */     return this.cachedMetadata;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\packs\resources\Resource.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */