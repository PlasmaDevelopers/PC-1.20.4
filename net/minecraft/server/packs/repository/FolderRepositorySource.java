/*     */ package net.minecraft.server.packs.repository;
/*     */ 
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.DirectoryStream;
/*     */ import java.nio.file.FileSystem;
/*     */ import java.nio.file.FileSystems;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.Consumer;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.FileUtil;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.server.packs.FilePackResources;
/*     */ import net.minecraft.server.packs.PackType;
/*     */ import net.minecraft.server.packs.PathPackResources;
/*     */ import net.minecraft.world.level.validation.ContentValidationException;
/*     */ import net.minecraft.world.level.validation.DirectoryValidator;
/*     */ import net.minecraft.world.level.validation.ForbiddenSymlinkInfo;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class FolderRepositorySource
/*     */   implements RepositorySource
/*     */ {
/*  28 */   static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private final Path folder;
/*     */   private final PackType packType;
/*     */   private final PackSource packSource;
/*     */   private final DirectoryValidator validator;
/*     */   
/*     */   public FolderRepositorySource(Path $$0, PackType $$1, PackSource $$2, DirectoryValidator $$3) {
/*  36 */     this.folder = $$0;
/*  37 */     this.packType = $$1;
/*  38 */     this.packSource = $$2;
/*  39 */     this.validator = $$3;
/*     */   }
/*     */   
/*     */   private static String nameFromPath(Path $$0) {
/*  43 */     return $$0.getFileName().toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadPacks(Consumer<Pack> $$0) {
/*     */     try {
/*  49 */       FileUtil.createDirectoriesSafe(this.folder);
/*  50 */       discoverPacks(this.folder, this.validator, false, ($$1, $$2) -> {
/*     */             String $$3 = nameFromPath($$1);
/*     */ 
/*     */             
/*     */             Pack $$4 = Pack.readMetaAndCreate("file/" + $$3, (Component)Component.literal($$3), false, $$2, this.packType, Pack.Position.TOP, this.packSource);
/*     */             
/*     */             if ($$4 != null) {
/*     */               $$0.accept($$4);
/*     */             }
/*     */           });
/*  60 */     } catch (IOException $$1) {
/*  61 */       LOGGER.warn("Failed to list packs in {}", this.folder, $$1);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void discoverPacks(Path $$0, DirectoryValidator $$1, boolean $$2, BiConsumer<Path, Pack.ResourcesSupplier> $$3) throws IOException {
/*  66 */     FolderPackDetector $$4 = new FolderPackDetector($$1, $$2);
/*     */     
/*  68 */     DirectoryStream<Path> $$5 = Files.newDirectoryStream($$0); 
/*  69 */     try { for (Path $$6 : $$5) {
/*     */         try {
/*  71 */           List<ForbiddenSymlinkInfo> $$7 = new ArrayList<>();
/*  72 */           Pack.ResourcesSupplier $$8 = $$4.detectPackResources($$6, $$7);
/*  73 */           if (!$$7.isEmpty()) {
/*  74 */             LOGGER.warn("Ignoring potential pack entry: {}", ContentValidationException.getMessage($$6, $$7)); continue;
/*  75 */           }  if ($$8 != null) {
/*  76 */             $$3.accept($$6, $$8); continue;
/*     */           } 
/*  78 */           LOGGER.info("Found non-pack entry '{}', ignoring", $$6);
/*     */         }
/*  80 */         catch (IOException $$9) {
/*  81 */           LOGGER.warn("Failed to read properties of '{}', ignoring", $$6, $$9);
/*     */         } 
/*     */       } 
/*  84 */       if ($$5 != null) $$5.close();  }
/*     */     catch (Throwable throwable) { if ($$5 != null)
/*     */         try {
/*     */           $$5.close();
/*     */         } catch (Throwable throwable1) {
/*     */           throwable.addSuppressed(throwable1);
/*     */         }   throw throwable; }
/*  91 */      } private static class FolderPackDetector extends PackDetector<Pack.ResourcesSupplier> { protected FolderPackDetector(DirectoryValidator $$0, boolean $$1) { super($$0);
/*  92 */       this.isBuiltin = $$1; }
/*     */     
/*     */     private final boolean isBuiltin;
/*     */     
/*     */     @Nullable
/*     */     protected Pack.ResourcesSupplier createZipPack(Path $$0) {
/*  98 */       FileSystem $$1 = $$0.getFileSystem();
/*  99 */       if ($$1 == FileSystems.getDefault() || $$1 instanceof net.minecraft.server.packs.linkfs.LinkFileSystem) {
/* 100 */         return (Pack.ResourcesSupplier)new FilePackResources.FileResourcesSupplier($$0, this.isBuiltin);
/*     */       }
/* 102 */       FolderRepositorySource.LOGGER.info("Can't open pack archive at {}", $$0);
/* 103 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     protected Pack.ResourcesSupplier createDirectoryPack(Path $$0) {
/* 108 */       return (Pack.ResourcesSupplier)new PathPackResources.PathResourcesSupplier($$0, this.isBuiltin);
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\packs\repository\FolderRepositorySource.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */