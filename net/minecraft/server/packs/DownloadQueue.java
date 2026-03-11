/*     */ package net.minecraft.server.packs;
/*     */ import com.google.common.hash.HashCode;
/*     */ import com.google.common.hash.HashFunction;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Either;
/*     */ import com.mojang.datafixers.util.Function5;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.io.IOException;
/*     */ import java.net.Proxy;
/*     */ import java.net.URL;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.time.Instant;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.function.BiFunction;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.FileUtil;
/*     */ import net.minecraft.core.UUIDUtil;
/*     */ import net.minecraft.util.ExtraCodecs;
/*     */ import net.minecraft.util.HttpUtil;
/*     */ import net.minecraft.util.eventlog.JsonEventLog;
/*     */ import net.minecraft.util.thread.ProcessorMailbox;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class DownloadQueue implements AutoCloseable {
/*  34 */   private static final Logger LOGGER = LogUtils.getLogger(); private static final int MAX_KEPT_PACKS = 20; private final Path cacheDir; private final JsonEventLog<LogEntry> eventLog;
/*     */   private static final class FileInfoEntry extends Record { private final String name; private final long size;
/*     */     public static final Codec<FileInfoEntry> CODEC;
/*     */     
/*  38 */     FileInfoEntry(String $$0, long $$1) { this.name = $$0; this.size = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/server/packs/DownloadQueue$FileInfoEntry;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #38	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*  38 */       //   0	7	0	this	Lnet/minecraft/server/packs/DownloadQueue$FileInfoEntry; } public String name() { return this.name; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/server/packs/DownloadQueue$FileInfoEntry;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #38	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/server/packs/DownloadQueue$FileInfoEntry; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/server/packs/DownloadQueue$FileInfoEntry;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #38	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/server/packs/DownloadQueue$FileInfoEntry;
/*  38 */       //   0	8	1	$$0	Ljava/lang/Object; } public long size() { return this.size; }
/*     */ 
/*     */     
/*     */     static {
/*  42 */       CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Codec.STRING.fieldOf("name").forGetter(FileInfoEntry::name), (App)Codec.LONG.fieldOf("size").forGetter(FileInfoEntry::size)).apply((Applicative)$$0, FileInfoEntry::new));
/*     */     } }
/*     */   private static final class LogEntry extends Record { private final UUID id; private final String url; private final Instant time; private final Optional<String> hash;
/*     */     private final Either<String, DownloadQueue.FileInfoEntry> errorOrFileInfo;
/*     */     public static final Codec<LogEntry> CODEC;
/*     */     
/*  48 */     LogEntry(UUID $$0, String $$1, Instant $$2, Optional<String> $$3, Either<String, DownloadQueue.FileInfoEntry> $$4) { this.id = $$0; this.url = $$1; this.time = $$2; this.hash = $$3; this.errorOrFileInfo = $$4; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/server/packs/DownloadQueue$LogEntry;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #48	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/server/packs/DownloadQueue$LogEntry; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/server/packs/DownloadQueue$LogEntry;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #48	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/server/packs/DownloadQueue$LogEntry; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/server/packs/DownloadQueue$LogEntry;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #48	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/server/packs/DownloadQueue$LogEntry;
/*  48 */       //   0	8	1	$$0	Ljava/lang/Object; } public UUID id() { return this.id; } public String url() { return this.url; } public Instant time() { return this.time; } public Optional<String> hash() { return this.hash; } public Either<String, DownloadQueue.FileInfoEntry> errorOrFileInfo() { return this.errorOrFileInfo; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/*  55 */       CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)UUIDUtil.STRING_CODEC.fieldOf("id").forGetter(LogEntry::id), (App)Codec.STRING.fieldOf("url").forGetter(LogEntry::url), (App)ExtraCodecs.INSTANT_ISO8601.fieldOf("time").forGetter(LogEntry::time), (App)Codec.STRING.optionalFieldOf("hash").forGetter(LogEntry::hash), (App)Codec.mapEither(Codec.STRING.fieldOf("error"), DownloadQueue.FileInfoEntry.CODEC.fieldOf("file")).forGetter(LogEntry::errorOrFileInfo)).apply((Applicative)$$0, LogEntry::new));
/*     */     } }
/*     */ 
/*     */   
/*     */   public static final class BatchResult extends Record
/*     */   {
/*     */     final Map<UUID, Path> downloaded;
/*     */     final Set<UUID> failed;
/*     */     
/*  64 */     public Set<UUID> failed() { return this.failed; } public Map<UUID, Path> downloaded() { return this.downloaded; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/server/packs/DownloadQueue$BatchResult;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #64	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/server/packs/DownloadQueue$BatchResult;
/*     */       //   0	8	1	$$0	Ljava/lang/Object; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/server/packs/DownloadQueue$BatchResult;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #64	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/server/packs/DownloadQueue$BatchResult; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/server/packs/DownloadQueue$BatchResult;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #64	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*  64 */       //   0	7	0	this	Lnet/minecraft/server/packs/DownloadQueue$BatchResult; } public BatchResult(Map<UUID, Path> $$0, Set<UUID> $$1) { this.downloaded = $$0; this.failed = $$1; }
/*     */ 
/*     */ 
/*     */     
/*     */     public BatchResult() {
/*  69 */       this(new HashMap<>(), new HashSet<>());
/*     */     } }
/*     */   
/*     */   public static final class DownloadRequest extends Record { final URL url;
/*     */     @Nullable
/*     */     final HashCode hash;
/*     */     
/*  76 */     public DownloadRequest(URL $$0, @Nullable HashCode $$1) { this.url = $$0; this.hash = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/server/packs/DownloadQueue$DownloadRequest;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #76	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/server/packs/DownloadQueue$DownloadRequest; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/server/packs/DownloadQueue$DownloadRequest;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #76	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/server/packs/DownloadQueue$DownloadRequest; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/server/packs/DownloadQueue$DownloadRequest;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #76	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/server/packs/DownloadQueue$DownloadRequest;
/*  76 */       //   0	8	1	$$0	Ljava/lang/Object; } public URL url() { return this.url; } @Nullable public HashCode hash() { return this.hash; }
/*     */      }
/*     */   public static final class BatchConfig extends Record { final HashFunction hashFunction; final int maxSize; final Map<String, String> headers; final Proxy proxy;
/*     */     final HttpUtil.DownloadProgressListener listener;
/*     */     
/*  81 */     public BatchConfig(HashFunction $$0, int $$1, Map<String, String> $$2, Proxy $$3, HttpUtil.DownloadProgressListener $$4) { this.hashFunction = $$0; this.maxSize = $$1; this.headers = $$2; this.proxy = $$3; this.listener = $$4; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/server/packs/DownloadQueue$BatchConfig;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #81	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/server/packs/DownloadQueue$BatchConfig; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/server/packs/DownloadQueue$BatchConfig;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #81	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/server/packs/DownloadQueue$BatchConfig; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/server/packs/DownloadQueue$BatchConfig;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #81	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/server/packs/DownloadQueue$BatchConfig;
/*  81 */       //   0	8	1	$$0	Ljava/lang/Object; } public HashFunction hashFunction() { return this.hashFunction; } public int maxSize() { return this.maxSize; } public Map<String, String> headers() { return this.headers; } public Proxy proxy() { return this.proxy; } public HttpUtil.DownloadProgressListener listener() { return this.listener; }
/*     */      }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  91 */   private final ProcessorMailbox<Runnable> tasks = ProcessorMailbox.create(Util.nonCriticalIoPool(), "download-queue");
/*     */   
/*     */   public DownloadQueue(Path $$0) throws IOException {
/*  94 */     this.cacheDir = $$0;
/*  95 */     FileUtil.createDirectoriesSafe($$0);
/*  96 */     this.eventLog = JsonEventLog.open(LogEntry.CODEC, $$0.resolve("log.json"));
/*     */     
/*  98 */     DownloadCacheCleaner.vacuumCacheDir($$0, 20);
/*     */   }
/*     */   
/*     */   private BatchResult runDownload(BatchConfig $$0, Map<UUID, DownloadRequest> $$1) {
/* 102 */     BatchResult $$2 = new BatchResult();
/* 103 */     $$1.forEach(($$2, $$3) -> {
/*     */           Path $$4 = this.cacheDir.resolve($$2.toString());
/*     */           Path $$5 = null;
/*     */           try {
/*     */             $$5 = HttpUtil.downloadFile($$4, $$3.url, $$0.headers, $$0.hashFunction, $$3.hash, $$0.maxSize, $$0.proxy, $$0.listener);
/*     */             $$1.downloaded.put($$2, $$5);
/* 109 */           } catch (Exception $$6) {
/*     */             LOGGER.error("Failed to download {}", $$3.url, $$6);
/*     */ 
/*     */ 
/*     */             
/*     */             $$1.failed.add($$2);
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/*     */           try {
/*     */             this.eventLog.write(new LogEntry($$2, $$3.url.toString(), Instant.now(), Optional.<HashCode>ofNullable($$3.hash).map(HashCode::toString), ($$5 != null) ? getFileInfo($$5) : Either.left("download_failed")));
/* 121 */           } catch (Exception $$7) {
/*     */             LOGGER.error("Failed to log download of {}", $$3.url, $$7);
/*     */           } 
/*     */         });
/* 125 */     return $$2;
/*     */   }
/*     */   
/*     */   private Either<String, FileInfoEntry> getFileInfo(Path $$0) {
/*     */     try {
/* 130 */       long $$1 = Files.size($$0);
/* 131 */       Path $$2 = this.cacheDir.relativize($$0);
/* 132 */       return Either.right(new FileInfoEntry($$2.toString(), $$1));
/* 133 */     } catch (IOException $$3) {
/* 134 */       LOGGER.error("Failed to get file size of {}", $$0, $$3);
/* 135 */       return Either.left("no_access");
/*     */     } 
/*     */   }
/*     */   
/*     */   public CompletableFuture<BatchResult> downloadBatch(BatchConfig $$0, Map<UUID, DownloadRequest> $$1) {
/* 140 */     Objects.requireNonNull(this.tasks); return CompletableFuture.supplyAsync(() -> runDownload($$0, $$1), this.tasks::tell);
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 145 */     this.tasks.close();
/* 146 */     this.eventLog.close();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\packs\DownloadQueue.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */