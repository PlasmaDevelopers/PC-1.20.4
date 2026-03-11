/*     */ package net.minecraft.server.packs;
/*     */ 
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.serialization.DataResult;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import java.util.function.Consumer;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.FileUtil;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
/*     */ import net.minecraft.server.packs.resources.IoSupplier;
/*     */ import net.minecraft.server.packs.resources.Resource;
/*     */ import net.minecraft.server.packs.resources.ResourceProvider;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class VanillaPackResources implements PackResources {
/*  26 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private final BuiltInMetadata metadata;
/*     */   
/*     */   private final Set<String> namespaces;
/*     */   
/*     */   private final List<Path> rootPaths;
/*     */   private final Map<PackType, List<Path>> pathsForType;
/*     */   
/*     */   VanillaPackResources(BuiltInMetadata $$0, Set<String> $$1, List<Path> $$2, Map<PackType, List<Path>> $$3) {
/*  36 */     this.metadata = $$0;
/*  37 */     this.namespaces = $$1;
/*  38 */     this.rootPaths = $$2;
/*  39 */     this.pathsForType = $$3;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public IoSupplier<InputStream> getRootResource(String... $$0) {
/*  45 */     FileUtil.validatePath($$0);
/*     */     
/*  47 */     List<String> $$1 = List.of($$0);
/*  48 */     for (Path $$2 : this.rootPaths) {
/*  49 */       Path $$3 = FileUtil.resolvePath($$2, $$1);
/*  50 */       if (Files.exists($$3, new java.nio.file.LinkOption[0]) && PathPackResources.validatePath($$3)) {
/*  51 */         return IoSupplier.create($$3);
/*     */       }
/*     */     } 
/*  54 */     return null;
/*     */   }
/*     */   
/*     */   public void listRawPaths(PackType $$0, ResourceLocation $$1, Consumer<Path> $$2) {
/*  58 */     FileUtil.decomposePath($$1.getPath()).get()
/*  59 */       .ifLeft($$3 -> {
/*     */           String $$4 = $$0.getNamespace();
/*     */ 
/*     */           
/*     */           for (Path $$5 : this.pathsForType.get($$1)) {
/*     */             Path $$6 = $$5.resolve($$4);
/*     */             
/*     */             $$2.accept(FileUtil.resolvePath($$6, $$3));
/*     */           } 
/*  68 */         }).ifRight($$1 -> LOGGER.error("Invalid path {}: {}", $$0, $$1.message()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void listResources(PackType $$0, String $$1, String $$2, PackResources.ResourceOutput $$3) {
/*  75 */     FileUtil.decomposePath($$2).get()
/*  76 */       .ifLeft($$3 -> {
/*     */           List<Path> $$4 = this.pathsForType.get($$0);
/*     */           
/*     */           int $$5 = $$4.size();
/*     */           
/*     */           if ($$5 == 1) {
/*     */             getResources($$1, $$2, $$4.get(0), $$3);
/*     */           } else if ($$5 > 1) {
/*     */             Map<ResourceLocation, IoSupplier<InputStream>> $$6 = new HashMap<>();
/*     */             
/*     */             for (int $$7 = 0; $$7 < $$5 - 1; $$7++) {
/*     */               Objects.requireNonNull($$6);
/*     */               getResources($$6::putIfAbsent, $$2, $$4.get($$7), $$3);
/*     */             } 
/*     */             Path $$8 = $$4.get($$5 - 1);
/*     */             if ($$6.isEmpty()) {
/*     */               getResources($$1, $$2, $$8, $$3);
/*     */             } else {
/*     */               Objects.requireNonNull($$6);
/*     */               getResources($$6::putIfAbsent, $$2, $$8, $$3);
/*     */               $$6.forEach($$1);
/*     */             } 
/*     */           } 
/*  99 */         }).ifRight($$1 -> LOGGER.error("Invalid path {}: {}", $$0, $$1.message()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void getResources(PackResources.ResourceOutput $$0, String $$1, Path $$2, List<String> $$3) {
/* 105 */     Path $$4 = $$2.resolve($$1);
/* 106 */     PathPackResources.listPath($$1, $$4, $$3, $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public IoSupplier<InputStream> getResource(PackType $$0, ResourceLocation $$1) {
/* 112 */     return (IoSupplier<InputStream>)FileUtil.decomposePath($$1.getPath()).get().map($$2 -> {
/*     */           String $$3 = $$0.getNamespace();
/*     */           for (Path $$4 : this.pathsForType.get($$1)) {
/*     */             Path $$5 = FileUtil.resolvePath($$4.resolve($$3), $$2);
/*     */             if (Files.exists($$5, new java.nio.file.LinkOption[0]) && PathPackResources.validatePath($$5)) {
/*     */               return IoSupplier.create($$5);
/*     */             }
/*     */           } 
/*     */           return null;
/*     */         }$$1 -> {
/*     */           LOGGER.error("Invalid path {}: {}", $$0, $$1.message());
/*     */           return null;
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getNamespaces(PackType $$0) {
/* 132 */     return this.namespaces;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public <T> T getMetadataSection(MetadataSectionSerializer<T> $$0) {
/* 138 */     IoSupplier<InputStream> $$1 = getRootResource(new String[] { "pack.mcmeta" });
/* 139 */     if ($$1 != null) {
/* 140 */       try { InputStream $$2 = (InputStream)$$1.get(); 
/* 141 */         try { T $$3 = AbstractPackResources.getMetadataFromStream($$0, $$2);
/* 142 */           if ($$3 != null)
/* 143 */           { T t = $$3;
/*     */             
/* 145 */             if ($$2 != null) $$2.close();  return t; }  if ($$2 != null) $$2.close();  } catch (Throwable throwable) { if ($$2 != null) try { $$2.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException iOException) {}
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 150 */     return this.metadata.get($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public String packId() {
/* 155 */     return "vanilla";
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBuiltin() {
/* 160 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResourceProvider asProvider() {
/* 172 */     return $$0 -> Optional.<IoSupplier<InputStream>>ofNullable(getResource(PackType.CLIENT_RESOURCES, $$0)).map(());
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\packs\VanillaPackResources.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */