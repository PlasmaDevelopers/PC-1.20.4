/*     */ package net.minecraft.data;
/*     */ 
/*     */ import com.google.common.hash.HashCode;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.attribute.FileAttribute;
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class CacheUpdater
/*     */   implements CachedOutput
/*     */ {
/*     */   private final String provider;
/*     */   private final HashCache.ProviderCache oldCache;
/*     */   private final HashCache.ProviderCacheBuilder newCache;
/* 100 */   private final AtomicInteger writes = new AtomicInteger();
/*     */   private volatile boolean closed;
/*     */   
/*     */   CacheUpdater(String $$0, String $$1, HashCache.ProviderCache $$2) {
/* 104 */     this.provider = $$0;
/* 105 */     this.oldCache = $$2;
/* 106 */     this.newCache = new HashCache.ProviderCacheBuilder($$1);
/*     */   }
/*     */   
/*     */   private boolean shouldWrite(Path $$0, HashCode $$1) {
/* 110 */     return (!Objects.equals(this.oldCache.get($$0), $$1) || !Files.exists($$0, new java.nio.file.LinkOption[0]));
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeIfNeeded(Path $$0, byte[] $$1, HashCode $$2) throws IOException {
/* 115 */     if (this.closed) {
/* 116 */       throw new IllegalStateException("Cannot write to cache as it has already been closed");
/*     */     }
/* 118 */     if (shouldWrite($$0, $$2)) {
/* 119 */       this.writes.incrementAndGet();
/* 120 */       Files.createDirectories($$0.getParent(), (FileAttribute<?>[])new FileAttribute[0]);
/* 121 */       Files.write($$0, $$1, new java.nio.file.OpenOption[0]);
/*     */     } 
/* 123 */     this.newCache.put($$0, $$2);
/*     */   }
/*     */   
/*     */   public HashCache.UpdateResult close() {
/* 127 */     this.closed = true;
/* 128 */     return new HashCache.UpdateResult(this.provider, this.newCache.build(), this.writes.get());
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\HashCache$CacheUpdater.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */