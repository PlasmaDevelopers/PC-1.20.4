/*     */ package net.minecraft.server.packs.repository;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.file.FileSystem;
/*     */ import java.nio.file.FileSystems;
/*     */ import java.nio.file.Path;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.server.packs.FilePackResources;
/*     */ import net.minecraft.server.packs.PathPackResources;
/*     */ import net.minecraft.world.level.validation.DirectoryValidator;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class FolderPackDetector
/*     */   extends PackDetector<Pack.ResourcesSupplier>
/*     */ {
/*     */   private final boolean isBuiltin;
/*     */   
/*     */   protected FolderPackDetector(DirectoryValidator $$0, boolean $$1) {
/*  91 */     super($$0);
/*  92 */     this.isBuiltin = $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected Pack.ResourcesSupplier createZipPack(Path $$0) {
/*  98 */     FileSystem $$1 = $$0.getFileSystem();
/*  99 */     if ($$1 == FileSystems.getDefault() || $$1 instanceof net.minecraft.server.packs.linkfs.LinkFileSystem) {
/* 100 */       return (Pack.ResourcesSupplier)new FilePackResources.FileResourcesSupplier($$0, this.isBuiltin);
/*     */     }
/* 102 */     FolderRepositorySource.LOGGER.info("Can't open pack archive at {}", $$0);
/* 103 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Pack.ResourcesSupplier createDirectoryPack(Path $$0) {
/* 108 */     return (Pack.ResourcesSupplier)new PathPackResources.PathResourcesSupplier($$0, this.isBuiltin);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\packs\repository\FolderRepositorySource$FolderPackDetector.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */