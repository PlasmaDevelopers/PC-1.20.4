/*     */ package net.minecraft.server.packs;
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.io.IOException;
/*     */ import java.net.URI;
/*     */ import java.net.URL;
/*     */ import java.nio.file.FileSystemAlreadyExistsException;
/*     */ import java.nio.file.FileSystemNotFoundException;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.Paths;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.EnumMap;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.function.Consumer;
/*     */ import net.minecraft.Util;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class VanillaPackResourcesBuilder {
/*     */   public static Consumer<VanillaPackResourcesBuilder> developmentConfig = $$0 -> {
/*     */     
/*     */     };
/*  31 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private static final Map<PackType, Path> ROOT_DIR_BY_TYPE;
/*     */   
/*     */   private static Path safeGetPath(URI $$0) throws IOException {
/*     */     try {
/*  37 */       return Paths.get($$0);
/*  38 */     } catch (FileSystemNotFoundException fileSystemNotFoundException) {
/*     */     
/*  40 */     } catch (Throwable $$1) {
/*  41 */       LOGGER.warn("Unable to get path for: {}", $$0, $$1);
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/*  46 */       FileSystems.newFileSystem($$0, Collections.emptyMap());
/*  47 */     } catch (FileSystemAlreadyExistsException fileSystemAlreadyExistsException) {}
/*     */ 
/*     */ 
/*     */     
/*  51 */     return Paths.get($$0);
/*     */   }
/*     */   static {
/*  54 */     ROOT_DIR_BY_TYPE = (Map<PackType, Path>)Util.make(() -> {
/*     */           synchronized (VanillaPackResources.class) {
/*     */             ImmutableMap.Builder<PackType, Path> $$0 = ImmutableMap.builder();
/*     */             
/*     */             for (PackType $$1 : PackType.values()) {
/*     */               String $$2 = "/" + $$1.getDirectory() + "/.mcassetsroot";
/*     */               
/*     */               URL $$3 = VanillaPackResources.class.getResource($$2);
/*     */               if ($$3 == null) {
/*     */                 LOGGER.error("File {} does not exist in classpath", $$2);
/*     */               } else {
/*     */                 try {
/*     */                   URI $$4 = $$3.toURI();
/*     */                   String $$5 = $$4.getScheme();
/*     */                   if (!"jar".equals($$5) && !"file".equals($$5)) {
/*     */                     LOGGER.warn("Assets URL '{}' uses unexpected schema", $$4);
/*     */                   }
/*     */                   Path $$6 = safeGetPath($$4);
/*     */                   $$0.put($$1, $$6.getParent());
/*  73 */                 } catch (Exception $$7) {
/*     */                   LOGGER.error("Couldn't resolve path to vanilla assets", $$7);
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */             return $$0.build();
/*     */           } 
/*     */         });
/*  81 */   } private final Set<Path> rootPaths = new LinkedHashSet<>();
/*  82 */   private final Map<PackType, Set<Path>> pathsForType = new EnumMap<>(PackType.class);
/*     */   
/*  84 */   private BuiltInMetadata metadata = BuiltInMetadata.of();
/*  85 */   private final Set<String> namespaces = new HashSet<>();
/*     */   
/*     */   private boolean validateDirPath(Path $$0) {
/*  88 */     if (!Files.exists($$0, new java.nio.file.LinkOption[0])) {
/*  89 */       return false;
/*     */     }
/*  91 */     if (!Files.isDirectory($$0, new java.nio.file.LinkOption[0])) {
/*  92 */       throw new IllegalArgumentException("Path " + $$0.toAbsolutePath() + " is not directory");
/*     */     }
/*  94 */     return true;
/*     */   }
/*     */   
/*     */   private void pushRootPath(Path $$0) {
/*  98 */     if (validateDirPath($$0)) {
/*  99 */       this.rootPaths.add($$0);
/*     */     }
/*     */   }
/*     */   
/*     */   private void pushPathForType(PackType $$0, Path $$1) {
/* 104 */     if (validateDirPath($$1)) {
/* 105 */       ((Set<Path>)this.pathsForType.computeIfAbsent($$0, $$0 -> new LinkedHashSet())).add($$1);
/*     */     }
/*     */   }
/*     */   
/*     */   public VanillaPackResourcesBuilder pushJarResources() {
/* 110 */     ROOT_DIR_BY_TYPE.forEach(($$0, $$1) -> {
/*     */           pushRootPath($$1.getParent());
/*     */           pushPathForType($$0, $$1);
/*     */         });
/* 114 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public VanillaPackResourcesBuilder pushClasspathResources(PackType $$0, Class<?> $$1) {
/* 120 */     Enumeration<URL> $$2 = null;
/*     */     try {
/* 122 */       $$2 = $$1.getClassLoader().getResources($$0.getDirectory() + "/");
/* 123 */     } catch (IOException iOException) {}
/*     */ 
/*     */     
/* 126 */     while ($$2 != null && $$2.hasMoreElements()) {
/* 127 */       URL $$3 = $$2.nextElement();
/*     */       try {
/* 129 */         URI $$4 = $$3.toURI();
/* 130 */         if ("file".equals($$4.getScheme())) {
/* 131 */           Path $$5 = Paths.get($$4);
/* 132 */           pushRootPath($$5.getParent());
/* 133 */           pushPathForType($$0, $$5);
/*     */         } 
/* 135 */       } catch (Exception $$6) {
/* 136 */         LOGGER.error("Failed to extract path from {}", $$3, $$6);
/*     */       } 
/*     */     } 
/* 139 */     return this;
/*     */   }
/*     */   
/*     */   public VanillaPackResourcesBuilder applyDevelopmentConfig() {
/* 143 */     developmentConfig.accept(this);
/* 144 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VanillaPackResourcesBuilder pushUniversalPath(Path $$0) {
/* 151 */     pushRootPath($$0);
/* 152 */     for (PackType $$1 : PackType.values()) {
/* 153 */       pushPathForType($$1, $$0.resolve($$1.getDirectory()));
/*     */     }
/* 155 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VanillaPackResourcesBuilder pushAssetPath(PackType $$0, Path $$1) {
/* 162 */     pushRootPath($$1);
/* 163 */     pushPathForType($$0, $$1);
/* 164 */     return this;
/*     */   }
/*     */   
/*     */   public VanillaPackResourcesBuilder setMetadata(BuiltInMetadata $$0) {
/* 168 */     this.metadata = $$0;
/* 169 */     return this;
/*     */   }
/*     */   
/*     */   public VanillaPackResourcesBuilder exposeNamespace(String... $$0) {
/* 173 */     this.namespaces.addAll(Arrays.asList($$0));
/* 174 */     return this;
/*     */   }
/*     */   
/*     */   public VanillaPackResources build() {
/* 178 */     Map<PackType, List<Path>> $$0 = new EnumMap<>(PackType.class);
/*     */     
/* 180 */     for (PackType $$1 : PackType.values()) {
/* 181 */       List<Path> $$2 = copyAndReverse(this.pathsForType.getOrDefault($$1, Set.of()));
/* 182 */       $$0.put($$1, $$2);
/*     */     } 
/*     */     
/* 185 */     return new VanillaPackResources(this.metadata, 
/*     */         
/* 187 */         Set.copyOf(this.namespaces), 
/* 188 */         copyAndReverse(this.rootPaths), $$0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static List<Path> copyAndReverse(Collection<Path> $$0) {
/* 194 */     List<Path> $$1 = new ArrayList<>($$0);
/* 195 */     Collections.reverse($$1);
/* 196 */     return List.copyOf($$1);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\packs\VanillaPackResourcesBuilder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */