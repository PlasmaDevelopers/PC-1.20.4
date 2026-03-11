/*     */ package net.minecraft.client.resources.server;
/*     */ 
/*     */ import com.google.common.hash.HashFunction;
/*     */ import com.google.common.hash.Hashing;
/*     */ import com.mojang.util.UndashedUuid;
/*     */ import java.net.Proxy;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.function.Consumer;
/*     */ import net.minecraft.SharedConstants;
/*     */ import net.minecraft.WorldVersion;
/*     */ import net.minecraft.client.User;
/*     */ import net.minecraft.server.packs.DownloadQueue;
/*     */ import net.minecraft.server.packs.PackType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class null
/*     */   implements PackDownloader
/*     */ {
/*     */   private static final int MAX_PACK_SIZE_BYTES = 262144000;
/* 186 */   private static final HashFunction CACHE_HASHING_FUNCTION = Hashing.sha1();
/*     */   
/*     */   private Map<String, String> createDownloadHeaders() {
/* 189 */     WorldVersion $$0 = SharedConstants.getCurrentVersion();
/* 190 */     return Map.of("X-Minecraft-Username", user
/* 191 */         .getName(), "X-Minecraft-UUID", 
/* 192 */         UndashedUuid.toString(user.getProfileId()), "X-Minecraft-Version", $$0
/* 193 */         .getName(), "X-Minecraft-Version-ID", $$0
/* 194 */         .getId(), "X-Minecraft-Pack-Format", 
/* 195 */         String.valueOf($$0.getPackVersion(PackType.CLIENT_RESOURCES)), "User-Agent", "Minecraft Java/" + $$0
/* 196 */         .getName());
/*     */   }
/*     */ 
/*     */   
/*     */   public void download(Map<UUID, DownloadQueue.DownloadRequest> $$0, Consumer<DownloadQueue.BatchResult> $$1) {
/* 201 */     downloadQueue.downloadBatch(new DownloadQueue.BatchConfig(CACHE_HASHING_FUNCTION, 262144000, 
/*     */ 
/*     */ 
/*     */           
/* 205 */           createDownloadHeaders(), proxy, DownloadedPackSource.this
/*     */           
/* 207 */           .createDownloadNotifier($$0.size())), $$0)
/*     */ 
/*     */       
/* 210 */       .thenAcceptAsync($$1, mainThreadExecutor);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\server\DownloadedPackSource$4.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */