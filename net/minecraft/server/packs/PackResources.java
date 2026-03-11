/*    */ package net.minecraft.server.packs;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.util.Set;
/*    */ import java.util.function.BiConsumer;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
/*    */ import net.minecraft.server.packs.resources.IoSupplier;
/*    */ 
/*    */ public interface PackResources
/*    */   extends AutoCloseable {
/*    */   public static final String METADATA_EXTENSION = ".mcmeta";
/*    */   public static final String PACK_META = "pack.mcmeta";
/*    */   
/*    */   @Nullable
/*    */   IoSupplier<InputStream> getRootResource(String... paramVarArgs);
/*    */   
/*    */   @Nullable
/*    */   IoSupplier<InputStream> getResource(PackType paramPackType, ResourceLocation paramResourceLocation);
/*    */   
/*    */   void listResources(PackType paramPackType, String paramString1, String paramString2, ResourceOutput paramResourceOutput);
/*    */   
/*    */   Set<String> getNamespaces(PackType paramPackType);
/*    */   
/*    */   @Nullable
/*    */   <T> T getMetadataSection(MetadataSectionSerializer<T> paramMetadataSectionSerializer) throws IOException;
/*    */   
/*    */   String packId();
/*    */   
/*    */   default boolean isBuiltin() {
/* 33 */     return false;
/*    */   }
/*    */   
/*    */   void close();
/*    */   
/*    */   @FunctionalInterface
/*    */   public static interface ResourceOutput extends BiConsumer<ResourceLocation, IoSupplier<InputStream>> {}
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\packs\PackResources.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */