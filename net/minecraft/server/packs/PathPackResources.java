/*     */ package net.minecraft.server.packs;
/*     */ 
/*     */ import com.google.common.base.Joiner;
/*     */ import com.google.common.collect.Sets;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.serialization.DataResult;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.file.DirectoryStream;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.NoSuchFileException;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.attribute.BasicFileAttributes;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Set;
/*     */ import java.util.stream.Stream;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.FileUtil;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.packs.repository.Pack;
/*     */ import net.minecraft.server.packs.resources.IoSupplier;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class PathPackResources
/*     */   extends AbstractPackResources
/*     */ {
/*  30 */   private static final Logger LOGGER = LogUtils.getLogger();
/*  31 */   private static final Joiner PATH_JOINER = Joiner.on("/");
/*     */   
/*     */   private final Path root;
/*     */   
/*     */   public PathPackResources(String $$0, Path $$1, boolean $$2) {
/*  36 */     super($$0, $$2);
/*  37 */     this.root = $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public IoSupplier<InputStream> getRootResource(String... $$0) {
/*  43 */     FileUtil.validatePath($$0);
/*     */     
/*  45 */     Path $$1 = FileUtil.resolvePath(this.root, List.of($$0));
/*  46 */     if (Files.exists($$1, new java.nio.file.LinkOption[0])) {
/*  47 */       return IoSupplier.create($$1);
/*     */     }
/*  49 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean validatePath(Path $$0) {
/*  57 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public IoSupplier<InputStream> getResource(PackType $$0, ResourceLocation $$1) {
/*  74 */     Path $$2 = this.root.resolve($$0.getDirectory()).resolve($$1.getNamespace());
/*  75 */     return getResource($$1, $$2);
/*     */   }
/*     */   
/*     */   public static IoSupplier<InputStream> getResource(ResourceLocation $$0, Path $$1) {
/*  79 */     return (IoSupplier<InputStream>)FileUtil.decomposePath($$0.getPath()).get().map($$1 -> {
/*     */           Path $$2 = FileUtil.resolvePath($$0, $$1);
/*     */           return returnFileIfExists($$2);
/*     */         }$$1 -> {
/*     */           LOGGER.error("Invalid path {}: {}", $$0, $$1.message());
/*     */           return null;
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private static IoSupplier<InputStream> returnFileIfExists(Path $$0) {
/*  91 */     if (Files.exists($$0, new java.nio.file.LinkOption[0]) && validatePath($$0)) {
/*  92 */       return IoSupplier.create($$0);
/*     */     }
/*  94 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void listResources(PackType $$0, String $$1, String $$2, PackResources.ResourceOutput $$3) {
/*  99 */     FileUtil.decomposePath($$2).get()
/* 100 */       .ifLeft($$3 -> {
/*     */           Path $$4 = this.root.resolve($$0.getDirectory()).resolve($$1);
/*     */           
/*     */           listPath($$1, $$4, $$3, $$2);
/* 104 */         }).ifRight($$1 -> LOGGER.error("Invalid path {}: {}", $$0, $$1.message()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void listPath(String $$0, Path $$1, List<String> $$2, PackResources.ResourceOutput $$3) {
/* 110 */     Path $$4 = FileUtil.resolvePath($$1, $$2); 
/* 111 */     try { Stream<Path> $$5 = Files.find($$4, 2147483647, ($$0, $$1) -> $$1.isRegularFile(), new java.nio.file.FileVisitOption[0]); 
/* 112 */       try { $$5.forEach($$3 -> {
/*     */               String $$4 = PATH_JOINER.join($$0.relativize($$3));
/*     */               ResourceLocation $$5 = ResourceLocation.tryBuild($$1, $$4);
/*     */               if ($$5 == null) {
/*     */                 Util.logAndPauseIfInIde(String.format(Locale.ROOT, "Invalid path in pack: %s:%s, ignoring", new Object[] { $$1, $$4 }));
/*     */               } else {
/*     */                 $$2.accept($$5, IoSupplier.create($$3));
/*     */               } 
/*     */             });
/* 121 */         if ($$5 != null) $$5.close();  } catch (Throwable throwable) { if ($$5 != null) try { $$5.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (NoSuchFileException|java.nio.file.NotDirectoryException noSuchFileException)
/*     */     {  }
/* 123 */     catch (IOException $$6)
/* 124 */     { LOGGER.error("Failed to list path {}", $$4, $$6); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<String> getNamespaces(PackType $$0) {
/* 130 */     Set<String> $$1 = Sets.newHashSet();
/* 131 */     Path $$2 = this.root.resolve($$0.getDirectory());
/*     */     
/* 133 */     try { DirectoryStream<Path> $$3 = Files.newDirectoryStream($$2); 
/* 134 */       try { for (Path $$4 : $$3) {
/* 135 */           String $$5 = $$4.getFileName().toString();
/*     */           
/* 137 */           if (ResourceLocation.isValidNamespace($$5)) {
/* 138 */             $$1.add($$5); continue;
/*     */           } 
/* 140 */           LOGGER.warn("Non [a-z0-9_.-] character in namespace {} in pack {}, ignoring", $$5, this.root);
/*     */         } 
/*     */         
/* 143 */         if ($$3 != null) $$3.close();  } catch (Throwable throwable) { if ($$3 != null) try { $$3.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (NoSuchFileException|java.nio.file.NotDirectoryException noSuchFileException)
/*     */     {  }
/* 145 */     catch (IOException $$6)
/* 146 */     { LOGGER.error("Failed to list path {}", $$2, $$6); }
/*     */     
/* 148 */     return $$1;
/*     */   }
/*     */   
/*     */   public void close() {}
/*     */   
/*     */   public static class PathResourcesSupplier
/*     */     implements Pack.ResourcesSupplier
/*     */   {
/*     */     private final Path content;
/*     */     private final boolean isBuiltin;
/*     */     
/*     */     public PathResourcesSupplier(Path $$0, boolean $$1) {
/* 160 */       this.content = $$0;
/* 161 */       this.isBuiltin = $$1;
/*     */     }
/*     */ 
/*     */     
/*     */     public PackResources openPrimary(String $$0) {
/* 166 */       return new PathPackResources($$0, this.content, this.isBuiltin);
/*     */     }
/*     */ 
/*     */     
/*     */     public PackResources openFull(String $$0, Pack.Info $$1) {
/* 171 */       PackResources $$2 = openPrimary($$0);
/*     */       
/* 173 */       List<String> $$3 = $$1.overlays();
/* 174 */       if ($$3.isEmpty()) {
/* 175 */         return $$2;
/*     */       }
/*     */       
/* 178 */       List<PackResources> $$4 = new ArrayList<>($$3.size());
/* 179 */       for (String $$5 : $$3) {
/* 180 */         Path $$6 = this.content.resolve($$5);
/* 181 */         $$4.add(new PathPackResources($$0, $$6, this.isBuiltin));
/*     */       } 
/*     */       
/* 184 */       return new CompositePackResources($$2, $$4);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\packs\PathPackResources.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */