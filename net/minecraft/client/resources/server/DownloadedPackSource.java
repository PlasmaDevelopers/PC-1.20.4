/*     */ package net.minecraft.client.resources.server;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.hash.HashCode;
/*     */ import com.google.common.hash.HashFunction;
/*     */ import com.google.common.hash.Hashing;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.realmsclient.Unit;
/*     */ import com.mojang.util.UndashedUuid;
/*     */ import java.io.IOException;
/*     */ import java.io.UncheckedIOException;
/*     */ import java.net.Proxy;
/*     */ import java.net.URL;
/*     */ import java.nio.file.Path;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.OptionalLong;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.SharedConstants;
/*     */ import net.minecraft.WorldVersion;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.User;
/*     */ import net.minecraft.client.gui.components.toasts.SystemToast;
/*     */ import net.minecraft.client.main.GameConfig;
/*     */ import net.minecraft.network.Connection;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.server.packs.DownloadQueue;
/*     */ import net.minecraft.server.packs.FilePackResources;
/*     */ import net.minecraft.server.packs.PackType;
/*     */ import net.minecraft.server.packs.repository.Pack;
/*     */ import net.minecraft.server.packs.repository.PackSource;
/*     */ import net.minecraft.server.packs.repository.RepositorySource;
/*     */ import net.minecraft.util.HttpUtil;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class DownloadedPackSource
/*     */   implements AutoCloseable {
/*  46 */   private static final Component SERVER_NAME = (Component)Component.translatable("resourcePack.server.name");
/*     */   
/*  48 */   private static final Pattern SHA1 = Pattern.compile("^[a-fA-F0-9]{40}$");
/*     */   
/*  50 */   static final Logger LOGGER = LogUtils.getLogger(); private static final RepositorySource EMPTY_SOURCE = $$0 -> {
/*     */     
/*     */     };
/*  53 */   private static final PackLoadFeedback LOG_ONLY_FEEDBACK = new PackLoadFeedback()
/*     */     {
/*     */       public void reportUpdate(UUID $$0, PackLoadFeedback.Update $$1) {
/*  56 */         DownloadedPackSource.LOGGER.debug("Downloaded pack {} changed state to {}", $$0, $$1);
/*     */       }
/*     */ 
/*     */       
/*     */       public void reportFinalResult(UUID $$0, PackLoadFeedback.FinalResult $$1) {
/*  61 */         DownloadedPackSource.LOGGER.debug("Downloaded pack {} finished with state {}", $$0, $$1);
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   final Minecraft minecraft;
/*  67 */   private RepositorySource packSource = EMPTY_SOURCE;
/*     */   
/*     */   @Nullable
/*     */   private PackReloadConfig.Callbacks pendingReload;
/*     */   
/*     */   final ServerPackManager manager;
/*     */   
/*     */   private final DownloadQueue downloadQueue;
/*  75 */   private PackSource packType = PackSource.SERVER;
/*  76 */   PackLoadFeedback packFeedback = LOG_ONLY_FEEDBACK;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int packIdSerialNumber;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DownloadedPackSource(Minecraft $$0, Path $$1, GameConfig.UserData $$2) {
/*  87 */     this.minecraft = $$0;
/*     */     try {
/*  89 */       this.downloadQueue = new DownloadQueue($$1);
/*  90 */     } catch (IOException $$3) {
/*  91 */       throw new UncheckedIOException("Failed to open download queue in directory " + $$1, $$3);
/*     */     } 
/*     */     
/*  94 */     Objects.requireNonNull($$0); Executor $$4 = $$0::tell;
/*  95 */     this
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 110 */       .manager = new ServerPackManager(createDownloader(this.downloadQueue, $$4, $$2.user, $$2.proxy), new PackLoadFeedback() { public void reportUpdate(UUID $$0, PackLoadFeedback.Update $$1) { DownloadedPackSource.this.packFeedback.reportUpdate($$0, $$1); } public void reportFinalResult(UUID $$0, PackLoadFeedback.FinalResult $$1) { DownloadedPackSource.this.packFeedback.reportFinalResult($$0, $$1); } }createReloadConfig(), createUpdateScheduler($$4), ServerPackManager.PackPromptStatus.PENDING);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   HttpUtil.DownloadProgressListener createDownloadNotifier(final int totalCount) {
/* 116 */     return new HttpUtil.DownloadProgressListener()
/*     */       {
/* 118 */         private final SystemToast.SystemToastId toastId = new SystemToast.SystemToastId();
/* 119 */         private Component title = (Component)Component.empty(); @Nullable
/* 120 */         private Component message = null;
/*     */         
/*     */         private int count;
/*     */         private int failCount;
/* 124 */         private OptionalLong totalBytes = OptionalLong.empty();
/*     */         
/*     */         private void updateToast() {
/* 127 */           SystemToast.addOrUpdate(DownloadedPackSource.this.minecraft.getToasts(), this.toastId, this.title, this.message);
/*     */         }
/*     */         
/*     */         private void updateProgress(long $$0) {
/* 131 */           if (this.totalBytes.isPresent()) {
/* 132 */             this.message = (Component)Component.translatable("download.pack.progress.percent", new Object[] { Long.valueOf($$0 * 100L / this.totalBytes.getAsLong()) });
/*     */           } else {
/* 134 */             this.message = (Component)Component.translatable("download.pack.progress.bytes", new Object[] { Unit.humanReadable($$0) });
/*     */           } 
/* 136 */           updateToast();
/*     */         }
/*     */ 
/*     */         
/*     */         public void requestStart() {
/* 141 */           this.count++;
/* 142 */           this.title = (Component)Component.translatable("download.pack.title", new Object[] { Integer.valueOf(this.count), Integer.valueOf(this.val$totalCount) });
/* 143 */           updateToast();
/* 144 */           DownloadedPackSource.LOGGER.debug("Starting pack {}/{} download", Integer.valueOf(this.count), Integer.valueOf(totalCount));
/*     */         }
/*     */ 
/*     */         
/*     */         public void downloadStart(OptionalLong $$0) {
/* 149 */           DownloadedPackSource.LOGGER.debug("File size = {} bytes", $$0);
/* 150 */           this.totalBytes = $$0;
/* 151 */           updateProgress(0L);
/*     */         }
/*     */ 
/*     */         
/*     */         public void downloadedBytes(long $$0) {
/* 156 */           DownloadedPackSource.LOGGER.debug("Progress for pack {}: {} bytes", Integer.valueOf(this.count), Long.valueOf($$0));
/* 157 */           updateProgress($$0);
/*     */         }
/*     */ 
/*     */         
/*     */         public void requestFinished(boolean $$0) {
/* 162 */           if (!$$0) {
/* 163 */             DownloadedPackSource.LOGGER.info("Pack {} failed to download", Integer.valueOf(this.count));
/* 164 */             this.failCount++;
/*     */           } else {
/* 166 */             DownloadedPackSource.LOGGER.debug("Download ended for pack {}", Integer.valueOf(this.count));
/*     */           } 
/*     */           
/* 169 */           if (this.count == totalCount) {
/* 170 */             if (this.failCount > 0) {
/* 171 */               this.title = (Component)Component.translatable("download.pack.failed", new Object[] { Integer.valueOf(this.failCount), Integer.valueOf(this.val$totalCount) });
/* 172 */               this.message = null;
/* 173 */               updateToast();
/*     */             } else {
/* 175 */               SystemToast.forceHide(DownloadedPackSource.this.minecraft.getToasts(), this.toastId);
/*     */             } 
/*     */           }
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   private PackDownloader createDownloader(final DownloadQueue downloadQueue, final Executor mainThreadExecutor, final User user, final Proxy proxy) {
/* 183 */     return new PackDownloader()
/*     */       {
/*     */         private static final int MAX_PACK_SIZE_BYTES = 262144000;
/* 186 */         private static final HashFunction CACHE_HASHING_FUNCTION = Hashing.sha1();
/*     */         
/*     */         private Map<String, String> createDownloadHeaders() {
/* 189 */           WorldVersion $$0 = SharedConstants.getCurrentVersion();
/* 190 */           return Map.of("X-Minecraft-Username", user
/* 191 */               .getName(), "X-Minecraft-UUID", 
/* 192 */               UndashedUuid.toString(user.getProfileId()), "X-Minecraft-Version", $$0
/* 193 */               .getName(), "X-Minecraft-Version-ID", $$0
/* 194 */               .getId(), "X-Minecraft-Pack-Format", 
/* 195 */               String.valueOf($$0.getPackVersion(PackType.CLIENT_RESOURCES)), "User-Agent", "Minecraft Java/" + $$0
/* 196 */               .getName());
/*     */         }
/*     */ 
/*     */         
/*     */         public void download(Map<UUID, DownloadQueue.DownloadRequest> $$0, Consumer<DownloadQueue.BatchResult> $$1) {
/* 201 */           downloadQueue.downloadBatch(new DownloadQueue.BatchConfig(CACHE_HASHING_FUNCTION, 262144000, 
/*     */ 
/*     */ 
/*     */                 
/* 205 */                 createDownloadHeaders(), proxy, DownloadedPackSource.this
/*     */                 
/* 207 */                 .createDownloadNotifier($$0.size())), $$0)
/*     */ 
/*     */             
/* 210 */             .thenAcceptAsync($$1, mainThreadExecutor);
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   private Runnable createUpdateScheduler(final Executor mainThreadExecutor) {
/* 216 */     return new Runnable()
/*     */       {
/*     */         private boolean scheduledInMainExecutor;
/*     */         private boolean hasUpdates;
/*     */         
/*     */         public void run() {
/* 222 */           this.hasUpdates = true;
/* 223 */           if (!this.scheduledInMainExecutor) {
/* 224 */             this.scheduledInMainExecutor = true;
/* 225 */             mainThreadExecutor.execute(this::runAllUpdates);
/*     */           } 
/*     */         }
/*     */         
/*     */         private void runAllUpdates() {
/* 230 */           while (this.hasUpdates) {
/* 231 */             this.hasUpdates = false;
/* 232 */             DownloadedPackSource.this.manager.tick();
/*     */           } 
/* 234 */           this.scheduledInMainExecutor = false;
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   private PackReloadConfig createReloadConfig() {
/* 240 */     return this::startReload;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private List<Pack> loadRequestedPacks(List<PackReloadConfig.IdAndPath> $$0) {
/* 245 */     List<Pack> $$1 = new ArrayList<>($$0.size());
/*     */     
/* 247 */     for (PackReloadConfig.IdAndPath $$2 : Lists.reverse($$0)) {
/* 248 */       String $$3 = String.format(Locale.ROOT, "server/%08X/%s", new Object[] { Integer.valueOf(this.packIdSerialNumber++), $$2.id() });
/* 249 */       Path $$4 = $$2.path();
/*     */       
/* 251 */       FilePackResources.FileResourcesSupplier fileResourcesSupplier = new FilePackResources.FileResourcesSupplier($$4, false);
/*     */       
/* 253 */       int $$6 = SharedConstants.getCurrentVersion().getPackVersion(PackType.CLIENT_RESOURCES);
/* 254 */       Pack.Info $$7 = Pack.readPackInfo($$3, (Pack.ResourcesSupplier)fileResourcesSupplier, $$6);
/* 255 */       if ($$7 == null) {
/* 256 */         LOGGER.warn("Invalid pack metadata in {}, ignoring all", $$4);
/* 257 */         return null;
/*     */       } 
/* 259 */       $$1.add(Pack.create($$3, SERVER_NAME, true, (Pack.ResourcesSupplier)fileResourcesSupplier, $$7, Pack.Position.TOP, true, this.packType));
/*     */     } 
/*     */     
/* 262 */     return $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public RepositorySource createRepositorySource() {
/* 267 */     return $$0 -> this.packSource.loadPacks($$0);
/*     */   }
/*     */   
/*     */   private static RepositorySource configureSource(List<Pack> $$0) {
/* 271 */     if ($$0.isEmpty()) {
/* 272 */       return EMPTY_SOURCE;
/*     */     }
/* 274 */     Objects.requireNonNull($$0); return $$0::forEach;
/*     */   }
/*     */   
/*     */   private void startReload(PackReloadConfig.Callbacks $$0) {
/* 278 */     this.pendingReload = $$0;
/* 279 */     List<PackReloadConfig.IdAndPath> $$1 = $$0.packsToLoad();
/* 280 */     List<Pack> $$2 = loadRequestedPacks($$1);
/* 281 */     if ($$2 == null) {
/*     */       
/* 283 */       $$0.onFailure(false);
/* 284 */       List<PackReloadConfig.IdAndPath> $$3 = $$0.packsToLoad();
/*     */       
/* 286 */       $$2 = loadRequestedPacks($$3);
/* 287 */       if ($$2 == null) {
/* 288 */         LOGGER.warn("Double failure in loading server packs");
/* 289 */         $$2 = List.of();
/*     */       } 
/*     */     } 
/*     */     
/* 293 */     this.packSource = configureSource($$2);
/* 294 */     this.minecraft.reloadResourcePacks();
/*     */   }
/*     */   
/*     */   public void onRecovery() {
/* 298 */     if (this.pendingReload != null) {
/* 299 */       this.pendingReload.onFailure(false);
/* 300 */       List<Pack> $$0 = loadRequestedPacks(this.pendingReload.packsToLoad());
/* 301 */       if ($$0 == null) {
/* 302 */         LOGGER.warn("Double failure in loading server packs");
/* 303 */         $$0 = List.of();
/*     */       } 
/* 305 */       this.packSource = configureSource($$0);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void onRecoveryFailure() {
/* 310 */     if (this.pendingReload != null) {
/* 311 */       this.pendingReload.onFailure(true);
/* 312 */       this.pendingReload = null;
/* 313 */       this.packSource = EMPTY_SOURCE;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onReloadSuccess() {
/* 319 */     if (this.pendingReload != null) {
/* 320 */       this.pendingReload.onSuccess();
/* 321 */       this.pendingReload = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private static HashCode tryParseSha1Hash(@Nullable String $$0) {
/* 328 */     if ($$0 != null && SHA1.matcher($$0).matches()) {
/* 329 */       return HashCode.fromString($$0.toLowerCase(Locale.ROOT));
/*     */     }
/* 331 */     return null;
/*     */   }
/*     */   
/*     */   public void pushPack(UUID $$0, URL $$1, @Nullable String $$2) {
/* 335 */     HashCode $$3 = tryParseSha1Hash($$2);
/* 336 */     this.manager.pushPack($$0, $$1, $$3);
/*     */   }
/*     */   
/*     */   public void pushLocalPack(UUID $$0, Path $$1) {
/* 340 */     this.manager.pushLocalPack($$0, $$1);
/*     */   }
/*     */   
/*     */   public void popPack(UUID $$0) {
/* 344 */     this.manager.popPack($$0);
/*     */   }
/*     */   
/*     */   public void popAll() {
/* 348 */     this.manager.popAll();
/*     */   }
/*     */   
/*     */   private static PackLoadFeedback createPackResponseSender(final Connection connection) {
/* 352 */     return new PackLoadFeedback()
/*     */       {
/*     */         public void reportUpdate(UUID $$0, PackLoadFeedback.Update $$1) {
/*     */           // Byte code:
/*     */           //   0: getstatic net/minecraft/client/resources/server/DownloadedPackSource.LOGGER : Lorg/slf4j/Logger;
/*     */           //   3: ldc 'Pack {} changed status to {}'
/*     */           //   5: aload_1
/*     */           //   6: aload_2
/*     */           //   7: invokeinterface debug : (Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V
/*     */           //   12: getstatic net/minecraft/client/resources/server/DownloadedPackSource$8.$SwitchMap$net$minecraft$client$resources$server$PackLoadFeedback$Update : [I
/*     */           //   15: aload_2
/*     */           //   16: invokevirtual ordinal : ()I
/*     */           //   19: iaload
/*     */           //   20: lookupswitch default -> 48, 1 -> 56, 2 -> 62
/*     */           //   48: new java/lang/IncompatibleClassChangeError
/*     */           //   51: dup
/*     */           //   52: invokespecial <init> : ()V
/*     */           //   55: athrow
/*     */           //   56: getstatic net/minecraft/network/protocol/common/ServerboundResourcePackPacket$Action.ACCEPTED : Lnet/minecraft/network/protocol/common/ServerboundResourcePackPacket$Action;
/*     */           //   59: goto -> 65
/*     */           //   62: getstatic net/minecraft/network/protocol/common/ServerboundResourcePackPacket$Action.DOWNLOADED : Lnet/minecraft/network/protocol/common/ServerboundResourcePackPacket$Action;
/*     */           //   65: astore_3
/*     */           //   66: aload_0
/*     */           //   67: getfield val$connection : Lnet/minecraft/network/Connection;
/*     */           //   70: new net/minecraft/network/protocol/common/ServerboundResourcePackPacket
/*     */           //   73: dup
/*     */           //   74: aload_1
/*     */           //   75: aload_3
/*     */           //   76: invokespecial <init> : (Ljava/util/UUID;Lnet/minecraft/network/protocol/common/ServerboundResourcePackPacket$Action;)V
/*     */           //   79: invokevirtual send : (Lnet/minecraft/network/protocol/Packet;)V
/*     */           //   82: return
/*     */           // Line number table:
/*     */           //   Java source line number -> byte code offset
/*     */           //   #355	-> 0
/*     */           //   #356	-> 12
/*     */           //   #357	-> 56
/*     */           //   #358	-> 62
/*     */           //   #360	-> 66
/*     */           //   #361	-> 82
/*     */           // Local variable table:
/*     */           //   start	length	slot	name	descriptor
/*     */           //   0	83	0	this	Lnet/minecraft/client/resources/server/DownloadedPackSource$6;
/*     */           //   0	83	1	$$0	Ljava/util/UUID;
/*     */           //   0	83	2	$$1	Lnet/minecraft/client/resources/server/PackLoadFeedback$Update;
/*     */           //   66	17	3	$$2	Lnet/minecraft/network/protocol/common/ServerboundResourcePackPacket$Action;
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public void reportFinalResult(UUID $$0, PackLoadFeedback.FinalResult $$1) {
/*     */           // Byte code:
/*     */           //   0: getstatic net/minecraft/client/resources/server/DownloadedPackSource.LOGGER : Lorg/slf4j/Logger;
/*     */           //   3: ldc 'Pack {} changed status to {}'
/*     */           //   5: aload_1
/*     */           //   6: aload_2
/*     */           //   7: invokeinterface debug : (Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V
/*     */           //   12: getstatic net/minecraft/client/resources/server/DownloadedPackSource$8.$SwitchMap$net$minecraft$client$resources$server$PackLoadFeedback$FinalResult : [I
/*     */           //   15: aload_2
/*     */           //   16: invokevirtual ordinal : ()I
/*     */           //   19: iaload
/*     */           //   20: tableswitch default -> 56, 1 -> 64, 2 -> 70, 3 -> 76, 4 -> 82, 5 -> 88
/*     */           //   56: new java/lang/IncompatibleClassChangeError
/*     */           //   59: dup
/*     */           //   60: invokespecial <init> : ()V
/*     */           //   63: athrow
/*     */           //   64: getstatic net/minecraft/network/protocol/common/ServerboundResourcePackPacket$Action.SUCCESSFULLY_LOADED : Lnet/minecraft/network/protocol/common/ServerboundResourcePackPacket$Action;
/*     */           //   67: goto -> 91
/*     */           //   70: getstatic net/minecraft/network/protocol/common/ServerboundResourcePackPacket$Action.FAILED_DOWNLOAD : Lnet/minecraft/network/protocol/common/ServerboundResourcePackPacket$Action;
/*     */           //   73: goto -> 91
/*     */           //   76: getstatic net/minecraft/network/protocol/common/ServerboundResourcePackPacket$Action.DECLINED : Lnet/minecraft/network/protocol/common/ServerboundResourcePackPacket$Action;
/*     */           //   79: goto -> 91
/*     */           //   82: getstatic net/minecraft/network/protocol/common/ServerboundResourcePackPacket$Action.DISCARDED : Lnet/minecraft/network/protocol/common/ServerboundResourcePackPacket$Action;
/*     */           //   85: goto -> 91
/*     */           //   88: getstatic net/minecraft/network/protocol/common/ServerboundResourcePackPacket$Action.FAILED_RELOAD : Lnet/minecraft/network/protocol/common/ServerboundResourcePackPacket$Action;
/*     */           //   91: astore_3
/*     */           //   92: aload_0
/*     */           //   93: getfield val$connection : Lnet/minecraft/network/Connection;
/*     */           //   96: new net/minecraft/network/protocol/common/ServerboundResourcePackPacket
/*     */           //   99: dup
/*     */           //   100: aload_1
/*     */           //   101: aload_3
/*     */           //   102: invokespecial <init> : (Ljava/util/UUID;Lnet/minecraft/network/protocol/common/ServerboundResourcePackPacket$Action;)V
/*     */           //   105: invokevirtual send : (Lnet/minecraft/network/protocol/Packet;)V
/*     */           //   108: return
/*     */           // Line number table:
/*     */           //   Java source line number -> byte code offset
/*     */           //   #365	-> 0
/*     */           //   #366	-> 12
/*     */           //   #367	-> 64
/*     */           //   #368	-> 70
/*     */           //   #369	-> 76
/*     */           //   #370	-> 82
/*     */           //   #371	-> 88
/*     */           //   #373	-> 92
/*     */           //   #374	-> 108
/*     */           // Local variable table:
/*     */           //   start	length	slot	name	descriptor
/*     */           //   0	109	0	this	Lnet/minecraft/client/resources/server/DownloadedPackSource$6;
/*     */           //   0	109	1	$$0	Ljava/util/UUID;
/*     */           //   0	109	2	$$1	Lnet/minecraft/client/resources/server/PackLoadFeedback$FinalResult;
/*     */           //   92	17	3	$$2	Lnet/minecraft/network/protocol/common/ServerboundResourcePackPacket$Action;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void configureForServerControl(Connection $$0, ServerPackManager.PackPromptStatus $$1) {
/* 379 */     this.packType = PackSource.SERVER;
/* 380 */     this.packFeedback = createPackResponseSender($$0);
/* 381 */     switch ($$1) { case ALLOWED:
/* 382 */         this.manager.allowServerPacks(); break;
/* 383 */       case DECLINED: this.manager.rejectServerPacks(); break;
/* 384 */       case PENDING: this.manager.resetPromptStatus();
/*     */         break; }
/*     */   
/*     */   }
/*     */   public void configureForLocalWorld() {
/* 389 */     this.packType = PackSource.WORLD;
/* 390 */     this.packFeedback = LOG_ONLY_FEEDBACK;
/* 391 */     this.manager.allowServerPacks();
/*     */   }
/*     */   
/*     */   public void allowServerPacks() {
/* 395 */     this.manager.allowServerPacks();
/*     */   }
/*     */   
/*     */   public void rejectServerPacks() {
/* 399 */     this.manager.rejectServerPacks();
/*     */   }
/*     */   
/*     */   public CompletableFuture<Void> waitForPackFeedback(final UUID packId) {
/* 403 */     final CompletableFuture<Void> result = new CompletableFuture<>();
/* 404 */     final PackLoadFeedback original = this.packFeedback;
/* 405 */     this.packFeedback = new PackLoadFeedback()
/*     */       {
/*     */         public void reportUpdate(UUID $$0, PackLoadFeedback.Update $$1) {
/* 408 */           original.reportUpdate($$0, $$1);
/*     */         }
/*     */ 
/*     */         
/*     */         public void reportFinalResult(UUID $$0, PackLoadFeedback.FinalResult $$1) {
/* 413 */           if (packId.equals($$0)) {
/* 414 */             DownloadedPackSource.this.packFeedback = original;
/* 415 */             if ($$1 == PackLoadFeedback.FinalResult.APPLIED) {
/* 416 */               result.complete(null);
/*     */             } else {
/* 418 */               result.completeExceptionally(new IllegalStateException("Failed to apply pack " + $$0 + ", reason: " + $$1));
/*     */             } 
/*     */           } 
/* 421 */           original.reportFinalResult($$0, $$1);
/*     */         }
/*     */       };
/* 424 */     return $$1;
/*     */   }
/*     */   
/*     */   public void cleanupAfterDisconnect() {
/* 428 */     this.manager.popAll();
/* 429 */     this.packFeedback = LOG_ONLY_FEEDBACK;
/* 430 */     this.manager.resetPromptStatus();
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 435 */     this.downloadQueue.close();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\server\DownloadedPackSource.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */