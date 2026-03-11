/*     */ package net.minecraft.server.packs;
/*     */ 
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import com.google.common.collect.Sets;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.file.Path;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Enumeration;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Set;
/*     */ import java.util.zip.ZipEntry;
/*     */ import java.util.zip.ZipFile;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.packs.repository.Pack;
/*     */ import net.minecraft.server.packs.resources.IoSupplier;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class FilePackResources
/*     */   extends AbstractPackResources {
/*  26 */   static final Logger LOGGER = LogUtils.getLogger();
/*     */   private final SharedZipFileAccess zipFileAccess;
/*     */   private final String prefix;
/*     */   
/*     */   FilePackResources(String $$0, SharedZipFileAccess $$1, boolean $$2, String $$3) {
/*  31 */     super($$0, $$2);
/*  32 */     this.zipFileAccess = $$1;
/*  33 */     this.prefix = $$3;
/*     */   }
/*     */   
/*     */   private static String getPathFromLocation(PackType $$0, ResourceLocation $$1) {
/*  37 */     return String.format(Locale.ROOT, "%s/%s/%s", new Object[] { $$0.getDirectory(), $$1.getNamespace(), $$1.getPath() });
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public IoSupplier<InputStream> getRootResource(String... $$0) {
/*  43 */     return getResource(String.join("/", (CharSequence[])$$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public IoSupplier<InputStream> getResource(PackType $$0, ResourceLocation $$1) {
/*  48 */     return getResource(getPathFromLocation($$0, $$1));
/*     */   }
/*     */   
/*     */   private String addPrefix(String $$0) {
/*  52 */     if (this.prefix.isEmpty()) {
/*  53 */       return $$0;
/*     */     }
/*     */     
/*  56 */     return this.prefix + "/" + this.prefix;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private IoSupplier<InputStream> getResource(String $$0) {
/*  61 */     ZipFile $$1 = this.zipFileAccess.getOrCreateZipFile();
/*  62 */     if ($$1 == null) {
/*  63 */       return null;
/*     */     }
/*     */     
/*  66 */     ZipEntry $$2 = $$1.getEntry(addPrefix($$0));
/*  67 */     if ($$2 == null) {
/*  68 */       return null;
/*     */     }
/*     */     
/*  71 */     return IoSupplier.create($$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<String> getNamespaces(PackType $$0) {
/*  76 */     ZipFile $$1 = this.zipFileAccess.getOrCreateZipFile();
/*  77 */     if ($$1 == null) {
/*  78 */       return Set.of();
/*     */     }
/*     */     
/*  81 */     Enumeration<? extends ZipEntry> $$2 = $$1.entries();
/*     */     
/*  83 */     Set<String> $$3 = Sets.newHashSet();
/*     */     
/*  85 */     String $$4 = addPrefix($$0.getDirectory() + "/");
/*     */     
/*  87 */     while ($$2.hasMoreElements()) {
/*  88 */       ZipEntry $$5 = $$2.nextElement();
/*     */       
/*  90 */       String $$6 = $$5.getName();
/*  91 */       String $$7 = extractNamespace($$4, $$6);
/*  92 */       if (!$$7.isEmpty()) {
/*  93 */         if (ResourceLocation.isValidNamespace($$7)) {
/*  94 */           $$3.add($$7); continue;
/*     */         } 
/*  96 */         LOGGER.warn("Non [a-z0-9_.-] character in namespace {} in pack {}, ignoring", $$7, this.zipFileAccess.file);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 101 */     return $$3;
/*     */   }
/*     */   
/*     */   @VisibleForTesting
/*     */   public static String extractNamespace(String $$0, String $$1) {
/* 106 */     if (!$$1.startsWith($$0)) {
/* 107 */       return "";
/*     */     }
/*     */     
/* 110 */     int $$2 = $$0.length();
/* 111 */     int $$3 = $$1.indexOf('/', $$2);
/* 112 */     if ($$3 == -1) {
/* 113 */       return $$1.substring($$2);
/*     */     }
/* 115 */     return $$1.substring($$2, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() {
/* 120 */     this.zipFileAccess.close();
/*     */   }
/*     */ 
/*     */   
/*     */   public void listResources(PackType $$0, String $$1, String $$2, PackResources.ResourceOutput $$3) {
/* 125 */     ZipFile $$4 = this.zipFileAccess.getOrCreateZipFile();
/* 126 */     if ($$4 == null) {
/*     */       return;
/*     */     }
/* 129 */     Enumeration<? extends ZipEntry> $$5 = $$4.entries();
/* 130 */     String $$6 = addPrefix($$0.getDirectory() + "/" + $$0.getDirectory() + "/");
/* 131 */     String $$7 = $$6 + $$6 + "/";
/*     */     
/* 133 */     while ($$5.hasMoreElements()) {
/* 134 */       ZipEntry $$8 = $$5.nextElement();
/* 135 */       if ($$8.isDirectory()) {
/*     */         continue;
/*     */       }
/*     */       
/* 139 */       String $$9 = $$8.getName();
/* 140 */       if (!$$9.startsWith($$7)) {
/*     */         continue;
/*     */       }
/*     */       
/* 144 */       String $$10 = $$9.substring($$6.length());
/* 145 */       ResourceLocation $$11 = ResourceLocation.tryBuild($$1, $$10);
/* 146 */       if ($$11 != null) {
/* 147 */         $$3.accept($$11, IoSupplier.create($$4, $$8)); continue;
/*     */       } 
/* 149 */       LOGGER.warn("Invalid path in datapack: {}:{}, ignoring", $$1, $$10);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static class SharedZipFileAccess
/*     */     implements AutoCloseable {
/*     */     final File file;
/*     */     @Nullable
/*     */     private ZipFile zipFile;
/*     */     private boolean failedToLoad;
/*     */     
/*     */     SharedZipFileAccess(File $$0) {
/* 161 */       this.file = $$0;
/*     */     }
/*     */     
/*     */     @Nullable
/*     */     ZipFile getOrCreateZipFile() {
/* 166 */       if (this.failedToLoad) {
/* 167 */         return null;
/*     */       }
/*     */       
/* 170 */       if (this.zipFile == null) {
/*     */         try {
/* 172 */           this.zipFile = new ZipFile(this.file);
/* 173 */         } catch (IOException $$0) {
/* 174 */           FilePackResources.LOGGER.error("Failed to open pack {}", this.file, $$0);
/* 175 */           this.failedToLoad = true;
/* 176 */           return null;
/*     */         } 
/*     */       }
/*     */       
/* 180 */       return this.zipFile;
/*     */     }
/*     */ 
/*     */     
/*     */     public void close() {
/* 185 */       if (this.zipFile != null) {
/* 186 */         IOUtils.closeQuietly(this.zipFile);
/* 187 */         this.zipFile = null;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected void finalize() throws Throwable {
/* 194 */       close();
/* 195 */       super.finalize();
/*     */     }
/*     */   }
/*     */   
/*     */   public static class FileResourcesSupplier implements Pack.ResourcesSupplier {
/*     */     private final File content;
/*     */     private final boolean isBuiltin;
/*     */     
/*     */     public FileResourcesSupplier(Path $$0, boolean $$1) {
/* 204 */       this($$0.toFile(), $$1);
/*     */     }
/*     */     
/*     */     public FileResourcesSupplier(File $$0, boolean $$1) {
/* 208 */       this.isBuiltin = $$1;
/* 209 */       this.content = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public PackResources openPrimary(String $$0) {
/* 214 */       FilePackResources.SharedZipFileAccess $$1 = new FilePackResources.SharedZipFileAccess(this.content);
/* 215 */       return new FilePackResources($$0, $$1, this.isBuiltin, "");
/*     */     }
/*     */ 
/*     */     
/*     */     public PackResources openFull(String $$0, Pack.Info $$1) {
/* 220 */       FilePackResources.SharedZipFileAccess $$2 = new FilePackResources.SharedZipFileAccess(this.content);
/*     */       
/* 222 */       PackResources $$3 = new FilePackResources($$0, $$2, this.isBuiltin, "");
/* 223 */       List<String> $$4 = $$1.overlays();
/* 224 */       if ($$4.isEmpty()) {
/* 225 */         return $$3;
/*     */       }
/*     */       
/* 228 */       List<PackResources> $$5 = new ArrayList<>($$4.size());
/* 229 */       for (String $$6 : $$4) {
/* 230 */         $$5.add(new FilePackResources($$0, $$2, this.isBuiltin, $$6));
/*     */       }
/*     */       
/* 233 */       return new CompositePackResources($$3, $$5);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\packs\FilePackResources.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */