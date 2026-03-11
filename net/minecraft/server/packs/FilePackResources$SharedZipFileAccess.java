/*     */ package net.minecraft.server.packs;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.zip.ZipFile;
/*     */ import javax.annotation.Nullable;
/*     */ import org.apache.commons.io.IOUtils;
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
/*     */ class SharedZipFileAccess
/*     */   implements AutoCloseable
/*     */ {
/*     */   final File file;
/*     */   @Nullable
/*     */   private ZipFile zipFile;
/*     */   private boolean failedToLoad;
/*     */   
/*     */   SharedZipFileAccess(File $$0) {
/* 161 */     this.file = $$0;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   ZipFile getOrCreateZipFile() {
/* 166 */     if (this.failedToLoad) {
/* 167 */       return null;
/*     */     }
/*     */     
/* 170 */     if (this.zipFile == null) {
/*     */       try {
/* 172 */         this.zipFile = new ZipFile(this.file);
/* 173 */       } catch (IOException $$0) {
/* 174 */         FilePackResources.LOGGER.error("Failed to open pack {}", this.file, $$0);
/* 175 */         this.failedToLoad = true;
/* 176 */         return null;
/*     */       } 
/*     */     }
/*     */     
/* 180 */     return this.zipFile;
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() {
/* 185 */     if (this.zipFile != null) {
/* 186 */       IOUtils.closeQuietly(this.zipFile);
/* 187 */       this.zipFile = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void finalize() throws Throwable {
/* 194 */     close();
/* 195 */     super.finalize();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\packs\FilePackResources$SharedZipFileAccess.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */