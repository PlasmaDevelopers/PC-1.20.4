/*     */ package net.minecraft.data;
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.google.common.collect.UnmodifiableIterator;
/*     */ import com.google.common.hash.HashCode;
/*     */ import com.google.common.hash.Hashing;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.IOException;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.attribute.FileAttribute;
/*     */ import java.time.LocalDateTime;
/*     */ import java.time.format.DateTimeFormatter;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import java.util.stream.Stream;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.WorldVersion;
/*     */ import org.apache.commons.lang3.mutable.MutableInt;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class HashCache {
/*  33 */   static final Logger LOGGER = LogUtils.getLogger(); private static final String HEADER_MARKER = "// "; private final Path rootDir; private final Path cacheDir; private final String versionId; private final Map<String, ProviderCache> caches;
/*     */   private static final class ProviderCache extends Record { final String version; private final ImmutableMap<Path, HashCode> data;
/*     */     
/*  36 */     ProviderCache(String $$0, ImmutableMap<Path, HashCode> $$1) { this.version = $$0; this.data = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/data/HashCache$ProviderCache;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #36	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*  36 */       //   0	7	0	this	Lnet/minecraft/data/HashCache$ProviderCache; } public String version() { return this.version; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/data/HashCache$ProviderCache;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #36	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/data/HashCache$ProviderCache; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/data/HashCache$ProviderCache;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #36	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/data/HashCache$ProviderCache;
/*  36 */       //   0	8	1	$$0	Ljava/lang/Object; } public ImmutableMap<Path, HashCode> data() { return this.data; }
/*     */      @Nullable
/*     */     public HashCode get(Path $$0) {
/*  39 */       return (HashCode)this.data.get($$0);
/*     */     }
/*     */     
/*     */     public int count() {
/*  43 */       return this.data.size();
/*     */     }
/*     */     
/*     */     public static ProviderCache load(Path $$0, Path $$1) throws IOException {
/*  47 */       BufferedReader $$2 = Files.newBufferedReader($$1, StandardCharsets.UTF_8); 
/*  48 */       try { String $$3 = $$2.readLine();
/*  49 */         if (!$$3.startsWith("// ")) {
/*  50 */           throw new IllegalStateException("Missing cache file header");
/*     */         }
/*  52 */         String[] $$4 = $$3.substring("// ".length()).split("\t", 2);
/*  53 */         String $$5 = $$4[0];
/*  54 */         ImmutableMap.Builder<Path, HashCode> $$6 = ImmutableMap.builder();
/*  55 */         $$2.lines().forEach($$2 -> {
/*     */               int $$3 = $$2.indexOf(' ');
/*     */               $$0.put($$1.resolve($$2.substring($$3 + 1)), HashCode.fromString($$2.substring(0, $$3)));
/*     */             });
/*  59 */         ProviderCache providerCache = new ProviderCache($$5, $$6.build());
/*  60 */         if ($$2 != null) $$2.close();  return providerCache; } catch (Throwable throwable) { if ($$2 != null)
/*     */           try { $$2.close(); }
/*     */           catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */             throw throwable; }
/*  64 */        } public void save(Path $$0, Path $$1, String $$2) { try { BufferedWriter $$3 = Files.newBufferedWriter($$1, StandardCharsets.UTF_8, new java.nio.file.OpenOption[0]); 
/*  65 */         try { $$3.write("// ");
/*  66 */           $$3.write(this.version);
/*  67 */           $$3.write(9);
/*  68 */           $$3.write($$2);
/*  69 */           $$3.newLine();
/*  70 */           for (UnmodifiableIterator<Map.Entry<Path, HashCode>> unmodifiableIterator = this.data.entrySet().iterator(); unmodifiableIterator.hasNext(); ) { Map.Entry<Path, HashCode> $$4 = unmodifiableIterator.next();
/*  71 */             $$3.write(((HashCode)$$4.getValue()).toString());
/*  72 */             $$3.write(32);
/*  73 */             $$3.write($$0.relativize($$4.getKey()).toString());
/*  74 */             $$3.newLine(); }
/*     */           
/*  76 */           if ($$3 != null) $$3.close();  } catch (Throwable throwable) { if ($$3 != null) try { $$3.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException $$5)
/*  77 */       { HashCache.LOGGER.warn("Unable write cachefile {}: {}", $$1, $$5); }
/*     */        }
/*     */      }
/*     */   private static final class ProviderCacheBuilder extends Record { private final String version; private final ConcurrentMap<Path, HashCode> data;
/*     */     
/*  82 */     private ProviderCacheBuilder(String $$0, ConcurrentMap<Path, HashCode> $$1) { this.version = $$0; this.data = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/data/HashCache$ProviderCacheBuilder;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #82	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/data/HashCache$ProviderCacheBuilder; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/data/HashCache$ProviderCacheBuilder;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #82	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/data/HashCache$ProviderCacheBuilder; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/data/HashCache$ProviderCacheBuilder;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #82	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/data/HashCache$ProviderCacheBuilder;
/*  82 */       //   0	8	1	$$0	Ljava/lang/Object; } public String version() { return this.version; } public ConcurrentMap<Path, HashCode> data() { return this.data; }
/*     */      ProviderCacheBuilder(String $$0) {
/*  84 */       this($$0, new ConcurrentHashMap<>());
/*     */     }
/*     */     
/*     */     public void put(Path $$0, HashCode $$1) {
/*  88 */       this.data.put($$0, $$1);
/*     */     }
/*     */     
/*     */     public HashCache.ProviderCache build() {
/*  92 */       return new HashCache.ProviderCache(this.version, ImmutableMap.copyOf(this.data));
/*     */     } }
/*     */ 
/*     */   
/*     */   private class CacheUpdater implements CachedOutput {
/*     */     private final String provider;
/*     */     private final HashCache.ProviderCache oldCache;
/*     */     private final HashCache.ProviderCacheBuilder newCache;
/* 100 */     private final AtomicInteger writes = new AtomicInteger();
/*     */     private volatile boolean closed;
/*     */     
/*     */     CacheUpdater(String $$0, String $$1, HashCache.ProviderCache $$2) {
/* 104 */       this.provider = $$0;
/* 105 */       this.oldCache = $$2;
/* 106 */       this.newCache = new HashCache.ProviderCacheBuilder($$1);
/*     */     }
/*     */     
/*     */     private boolean shouldWrite(Path $$0, HashCode $$1) {
/* 110 */       return (!Objects.equals(this.oldCache.get($$0), $$1) || !Files.exists($$0, new java.nio.file.LinkOption[0]));
/*     */     }
/*     */ 
/*     */     
/*     */     public void writeIfNeeded(Path $$0, byte[] $$1, HashCode $$2) throws IOException {
/* 115 */       if (this.closed) {
/* 116 */         throw new IllegalStateException("Cannot write to cache as it has already been closed");
/*     */       }
/* 118 */       if (shouldWrite($$0, $$2)) {
/* 119 */         this.writes.incrementAndGet();
/* 120 */         Files.createDirectories($$0.getParent(), (FileAttribute<?>[])new FileAttribute[0]);
/* 121 */         Files.write($$0, $$1, new java.nio.file.OpenOption[0]);
/*     */       } 
/* 123 */       this.newCache.put($$0, $$2);
/*     */     }
/*     */     
/*     */     public HashCache.UpdateResult close() {
/* 127 */       this.closed = true;
/* 128 */       return new HashCache.UpdateResult(this.provider, this.newCache.build(), this.writes.get());
/*     */     } }
/*     */   public static final class UpdateResult extends Record { private final String providerId; private final HashCache.ProviderCache cache; private final int writes;
/*     */     
/* 132 */     public UpdateResult(String $$0, HashCache.ProviderCache $$1, int $$2) { this.providerId = $$0; this.cache = $$1; this.writes = $$2; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/data/HashCache$UpdateResult;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #132	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/data/HashCache$UpdateResult; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/data/HashCache$UpdateResult;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #132	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/data/HashCache$UpdateResult; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/data/HashCache$UpdateResult;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #132	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/data/HashCache$UpdateResult;
/* 132 */       //   0	8	1	$$0	Ljava/lang/Object; } public String providerId() { return this.providerId; } public HashCache.ProviderCache cache() { return this.cache; } public int writes() { return this.writes; }
/*     */      }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 139 */   private final Set<String> cachesToWrite = new HashSet<>();
/* 140 */   private final Set<Path> cachePaths = new HashSet<>();
/*     */   
/*     */   private final int initialCount;
/*     */   private int writes;
/*     */   
/*     */   private Path getProviderCachePath(String $$0) {
/* 146 */     return this.cacheDir.resolve(Hashing.sha1().hashString($$0, StandardCharsets.UTF_8).toString());
/*     */   }
/*     */   
/*     */   public HashCache(Path $$0, Collection<String> $$1, WorldVersion $$2) throws IOException {
/* 150 */     this.versionId = $$2.getName();
/* 151 */     this.rootDir = $$0;
/* 152 */     this.cacheDir = $$0.resolve(".cache");
/* 153 */     Files.createDirectories(this.cacheDir, (FileAttribute<?>[])new FileAttribute[0]);
/*     */     
/* 155 */     Map<String, ProviderCache> $$3 = new HashMap<>();
/* 156 */     int $$4 = 0;
/* 157 */     for (String $$5 : $$1) {
/* 158 */       Path $$6 = getProviderCachePath($$5);
/* 159 */       this.cachePaths.add($$6);
/* 160 */       ProviderCache $$7 = readCache($$0, $$6);
/* 161 */       $$3.put($$5, $$7);
/* 162 */       $$4 += $$7.count();
/*     */     } 
/* 164 */     this.caches = $$3;
/* 165 */     this.initialCount = $$4;
/*     */   }
/*     */   
/*     */   private static ProviderCache readCache(Path $$0, Path $$1) {
/* 169 */     if (Files.isReadable($$1)) {
/*     */       try {
/* 171 */         return ProviderCache.load($$0, $$1);
/* 172 */       } catch (Exception $$2) {
/* 173 */         LOGGER.warn("Failed to parse cache {}, discarding", $$1, $$2);
/*     */       } 
/*     */     }
/* 176 */     return new ProviderCache("unknown", ImmutableMap.of());
/*     */   }
/*     */   
/*     */   public boolean shouldRunInThisVersion(String $$0) {
/* 180 */     ProviderCache $$1 = this.caches.get($$0);
/* 181 */     return ($$1 == null || !$$1.version.equals(this.versionId));
/*     */   }
/*     */   
/*     */   public CompletableFuture<UpdateResult> generateUpdate(String $$0, UpdateFunction $$1) {
/* 185 */     ProviderCache $$2 = this.caches.get($$0);
/* 186 */     if ($$2 == null) {
/* 187 */       throw new IllegalStateException("Provider not registered: " + $$0);
/*     */     }
/* 189 */     CacheUpdater $$3 = new CacheUpdater($$0, this.versionId, $$2);
/* 190 */     return $$1.update($$3).thenApply($$1 -> $$0.close());
/*     */   }
/*     */   
/*     */   public void applyUpdate(UpdateResult $$0) {
/* 194 */     this.caches.put($$0.providerId(), $$0.cache());
/* 195 */     this.cachesToWrite.add($$0.providerId());
/* 196 */     this.writes += $$0.writes();
/*     */   }
/*     */   
/*     */   public void purgeStaleAndWrite() throws IOException {
/* 200 */     Set<Path> $$0 = new HashSet<>();
/* 201 */     this.caches.forEach(($$1, $$2) -> {
/*     */           if (this.cachesToWrite.contains($$1)) {
/*     */             Path $$3 = getProviderCachePath($$1);
/*     */             
/*     */             $$2.save(this.rootDir, $$3, DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(LocalDateTime.now()) + "\t" + DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(LocalDateTime.now()));
/*     */           } 
/*     */           $$0.addAll((Collection)$$2.data().keySet());
/*     */         });
/* 209 */     $$0.add(this.rootDir.resolve("version.json"));
/*     */     
/* 211 */     MutableInt $$1 = new MutableInt();
/* 212 */     MutableInt $$2 = new MutableInt();
/* 213 */     Stream<Path> $$3 = Files.walk(this.rootDir, new java.nio.file.FileVisitOption[0]); 
/* 214 */     try { $$3.forEach($$3 -> {
/*     */             if (Files.isDirectory($$3, new java.nio.file.LinkOption[0])) {
/*     */               return;
/*     */             }
/*     */             if (this.cachePaths.contains($$3)) {
/*     */               return;
/*     */             }
/*     */             $$0.increment();
/*     */             if ($$1.contains($$3)) {
/*     */               return;
/*     */             }
/*     */             try {
/*     */               Files.delete($$3);
/* 227 */             } catch (IOException $$4) {
/*     */               LOGGER.warn("Failed to delete file {}", $$3, $$4);
/*     */             } 
/*     */             $$2.increment();
/*     */           });
/* 232 */       if ($$3 != null) $$3.close();  } catch (Throwable throwable) { if ($$3 != null)
/* 233 */         try { $$3.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  LOGGER.info("Caching: total files: {}, old count: {}, new count: {}, removed stale: {}, written: {}", new Object[] { $$1, Integer.valueOf(this.initialCount), Integer.valueOf($$0.size()), $$2, Integer.valueOf(this.writes) });
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface UpdateFunction {
/*     */     CompletableFuture<?> update(CachedOutput param1CachedOutput);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\HashCache.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */