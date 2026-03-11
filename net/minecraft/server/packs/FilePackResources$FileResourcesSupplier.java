/*     */ package net.minecraft.server.packs;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.nio.file.Path;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.server.packs.repository.Pack;
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
/*     */ public class FileResourcesSupplier
/*     */   implements Pack.ResourcesSupplier
/*     */ {
/*     */   private final File content;
/*     */   private final boolean isBuiltin;
/*     */   
/*     */   public FileResourcesSupplier(Path $$0, boolean $$1) {
/* 204 */     this($$0.toFile(), $$1);
/*     */   }
/*     */   
/*     */   public FileResourcesSupplier(File $$0, boolean $$1) {
/* 208 */     this.isBuiltin = $$1;
/* 209 */     this.content = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public PackResources openPrimary(String $$0) {
/* 214 */     FilePackResources.SharedZipFileAccess $$1 = new FilePackResources.SharedZipFileAccess(this.content);
/* 215 */     return new FilePackResources($$0, $$1, this.isBuiltin, "");
/*     */   }
/*     */ 
/*     */   
/*     */   public PackResources openFull(String $$0, Pack.Info $$1) {
/* 220 */     FilePackResources.SharedZipFileAccess $$2 = new FilePackResources.SharedZipFileAccess(this.content);
/*     */     
/* 222 */     PackResources $$3 = new FilePackResources($$0, $$2, this.isBuiltin, "");
/* 223 */     List<String> $$4 = $$1.overlays();
/* 224 */     if ($$4.isEmpty()) {
/* 225 */       return $$3;
/*     */     }
/*     */     
/* 228 */     List<PackResources> $$5 = new ArrayList<>($$4.size());
/* 229 */     for (String $$6 : $$4) {
/* 230 */       $$5.add(new FilePackResources($$0, $$2, this.isBuiltin, $$6));
/*     */     }
/*     */     
/* 233 */     return new CompositePackResources($$3, $$5);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\packs\FilePackResources$FileResourcesSupplier.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */