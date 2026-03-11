/*    */ package net.minecraft.server.packs.resources;
/*    */ 
/*    */ import java.io.BufferedReader;
/*    */ import java.io.FileNotFoundException;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.util.Map;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @FunctionalInterface
/*    */ public interface ResourceProvider
/*    */ {
/*    */   default Resource getResourceOrThrow(ResourceLocation $$0) throws FileNotFoundException {
/* 21 */     return getResource($$0).<Throwable>orElseThrow(() -> new FileNotFoundException($$0.toString()));
/*    */   }
/*    */   
/*    */   default InputStream open(ResourceLocation $$0) throws IOException {
/* 25 */     return getResourceOrThrow($$0).open();
/*    */   }
/*    */   
/*    */   default BufferedReader openAsReader(ResourceLocation $$0) throws IOException {
/* 29 */     return getResourceOrThrow($$0).openAsReader();
/*    */   }
/*    */   
/*    */   static ResourceProvider fromMap(Map<ResourceLocation, Resource> $$0) {
/* 33 */     return $$1 -> Optional.ofNullable((Resource)$$0.get($$1));
/*    */   }
/*    */   
/*    */   Optional<Resource> getResource(ResourceLocation paramResourceLocation);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\packs\resources\ResourceProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */