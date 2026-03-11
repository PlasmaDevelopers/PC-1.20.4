/*     */ package net.minecraft.client.gui.screens.packs;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.file.DirectoryStream;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.LinkOption;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.StandardWatchEventKinds;
/*     */ import java.nio.file.WatchEvent;
/*     */ import java.nio.file.WatchKey;
/*     */ import java.nio.file.WatchService;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class Watcher
/*     */   implements AutoCloseable
/*     */ {
/*     */   private final WatchService watcher;
/*     */   private final Path packPath;
/*     */   
/*     */   public Watcher(Path $$0) throws IOException {
/* 308 */     this.packPath = $$0;
/* 309 */     this.watcher = $$0.getFileSystem().newWatchService();
/*     */ 
/*     */     
/* 312 */     try { watchDir($$0);
/*     */ 
/*     */       
/* 315 */       DirectoryStream<Path> $$1 = Files.newDirectoryStream($$0); 
/* 316 */       try { for (Path $$2 : $$1) {
/* 317 */           if (Files.isDirectory($$2, new LinkOption[] { LinkOption.NOFOLLOW_LINKS })) {
/* 318 */             watchDir($$2);
/*     */           }
/*     */         } 
/* 321 */         if ($$1 != null) $$1.close();  } catch (Throwable throwable) { if ($$1 != null)
/* 322 */           try { $$1.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (Exception $$3)
/* 323 */     { this.watcher.close();
/* 324 */       throw $$3; }
/*     */   
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static Watcher create(Path $$0) {
/*     */     try {
/* 331 */       return new Watcher($$0);
/* 332 */     } catch (IOException $$1) {
/* 333 */       PackSelectionScreen.LOGGER.warn("Failed to initialize pack directory {} monitoring", $$0, $$1);
/* 334 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void watchDir(Path $$0) throws IOException {
/* 339 */     $$0.register(this.watcher, (WatchEvent.Kind<?>[])new WatchEvent.Kind[] { StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY });
/*     */   }
/*     */   
/*     */   public boolean pollForChanges() throws IOException {
/* 343 */     boolean $$0 = false;
/*     */     
/*     */     WatchKey $$1;
/*     */     
/* 347 */     while (($$1 = this.watcher.poll()) != null) {
/* 348 */       List<WatchEvent<?>> $$2 = $$1.pollEvents();
/* 349 */       for (WatchEvent<?> $$3 : $$2) {
/* 350 */         $$0 = true;
/*     */         
/* 352 */         Path $$4 = this.packPath.resolve((Path)$$3.context());
/* 353 */         if ($$1.watchable() == this.packPath && $$3.kind() == StandardWatchEventKinds.ENTRY_CREATE && Files.isDirectory($$4, new LinkOption[] { LinkOption.NOFOLLOW_LINKS })) {
/* 354 */           watchDir($$4);
/*     */         }
/*     */       } 
/*     */       
/* 358 */       $$1.reset();
/*     */     } 
/*     */     
/* 361 */     return $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 366 */     this.watcher.close();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\packs\PackSelectionScreen$Watcher.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */