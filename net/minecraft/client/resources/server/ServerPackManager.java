/*     */ package net.minecraft.client.resources.server;
/*     */ 
/*     */ import com.google.common.hash.HashCode;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.nio.file.Path;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.server.packs.DownloadQueue;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ServerPackManager
/*     */ {
/*     */   private final PackDownloader downloader;
/*     */   final PackLoadFeedback packLoadFeedback;
/*     */   private final PackReloadConfig reloadConfig;
/*     */   private final Runnable updateRequest;
/*     */   private PackPromptStatus packPromptStatus;
/*  52 */   final List<ServerPackData> packs = new ArrayList<>();
/*     */   
/*     */   public ServerPackManager(PackDownloader $$0, PackLoadFeedback $$1, PackReloadConfig $$2, Runnable $$3, PackPromptStatus $$4) {
/*  55 */     this.downloader = $$0;
/*  56 */     this.packLoadFeedback = $$1;
/*  57 */     this.reloadConfig = $$2;
/*  58 */     this.updateRequest = $$3;
/*  59 */     this.packPromptStatus = $$4;
/*     */   }
/*     */   
/*     */   void registerForUpdate() {
/*  63 */     this.updateRequest.run();
/*     */   }
/*     */   
/*     */   public enum PackPromptStatus {
/*  67 */     PENDING,
/*  68 */     ALLOWED,
/*  69 */     DECLINED;
/*     */   }
/*     */   
/*     */   private enum PackDownloadStatus {
/*  73 */     REQUESTED,
/*  74 */     PENDING,
/*  75 */     DONE;
/*     */   }
/*     */   
/*     */   private enum RemovalReason {
/*  79 */     DOWNLOAD_FAILED((String)PackLoadFeedback.FinalResult.DOWNLOAD_FAILED),
/*  80 */     ACTIVATION_FAILED((String)PackLoadFeedback.FinalResult.ACTIVATION_FAILED),
/*  81 */     DECLINED((String)PackLoadFeedback.FinalResult.DECLINED),
/*  82 */     DISCARDED((String)PackLoadFeedback.FinalResult.DISCARDED),
/*  83 */     SERVER_REMOVED(null),
/*  84 */     SERVER_REPLACED(null);
/*     */     
/*     */     @Nullable
/*     */     final PackLoadFeedback.FinalResult serverResponse;
/*     */ 
/*     */     
/*     */     RemovalReason(PackLoadFeedback.FinalResult $$0) {
/*  91 */       this.serverResponse = $$0;
/*     */     }
/*     */   }
/*     */   
/*     */   private enum ActivationStatus {
/*  96 */     INACTIVE,
/*  97 */     PENDING,
/*  98 */     ACTIVE;
/*     */   }
/*     */   
/*     */   private static class ServerPackData {
/*     */     final UUID id;
/*     */     final URL url;
/*     */     @Nullable
/*     */     final HashCode hash;
/*     */     @Nullable
/*     */     Path path;
/*     */     @Nullable
/*     */     ServerPackManager.RemovalReason removalReason;
/* 110 */     ServerPackManager.PackDownloadStatus downloadStatus = ServerPackManager.PackDownloadStatus.REQUESTED;
/* 111 */     ServerPackManager.ActivationStatus activationStatus = ServerPackManager.ActivationStatus.INACTIVE;
/*     */     boolean promptAccepted;
/*     */     
/*     */     ServerPackData(UUID $$0, URL $$1, @Nullable HashCode $$2) {
/* 115 */       this.id = $$0;
/* 116 */       this.url = $$1;
/* 117 */       this.hash = $$2;
/*     */     }
/*     */     
/*     */     public void setRemovalReasonIfNotSet(ServerPackManager.RemovalReason $$0) {
/* 121 */       if (this.removalReason == null) {
/* 122 */         this.removalReason = $$0;
/*     */       }
/*     */     }
/*     */     
/*     */     public boolean isRemoved() {
/* 127 */       return (this.removalReason != null);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void markExistingPacksAsRemoved(UUID $$0) {
/* 133 */     for (ServerPackData $$1 : this.packs) {
/* 134 */       if ($$1.id.equals($$0)) {
/* 135 */         $$1.setRemovalReasonIfNotSet(RemovalReason.SERVER_REPLACED);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void pushPack(UUID $$0, URL $$1, @Nullable HashCode $$2) {
/* 141 */     if (this.packPromptStatus == PackPromptStatus.DECLINED) {
/* 142 */       this.packLoadFeedback.reportFinalResult($$0, PackLoadFeedback.FinalResult.DECLINED);
/*     */       
/*     */       return;
/*     */     } 
/* 146 */     pushNewPack($$0, new ServerPackData($$0, $$1, $$2));
/*     */   }
/*     */   public void pushLocalPack(UUID $$0, Path $$1) {
/*     */     URL $$2;
/* 150 */     if (this.packPromptStatus == PackPromptStatus.DECLINED) {
/* 151 */       this.packLoadFeedback.reportFinalResult($$0, PackLoadFeedback.FinalResult.DECLINED);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*     */     try {
/* 157 */       $$2 = $$1.toUri().toURL();
/* 158 */     } catch (MalformedURLException $$3) {
/* 159 */       throw new IllegalStateException("Can't convert path to URL " + $$1, $$3);
/*     */     } 
/*     */     
/* 162 */     ServerPackData $$5 = new ServerPackData($$0, $$2, null);
/* 163 */     $$5.downloadStatus = PackDownloadStatus.DONE;
/* 164 */     $$5.path = $$1;
/* 165 */     pushNewPack($$0, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   private void pushNewPack(UUID $$0, ServerPackData $$1) {
/* 170 */     markExistingPacksAsRemoved($$0);
/*     */     
/* 172 */     this.packs.add($$1);
/*     */     
/* 174 */     if (this.packPromptStatus == PackPromptStatus.ALLOWED) {
/* 175 */       acceptPack($$1);
/*     */     }
/*     */     
/* 178 */     registerForUpdate();
/*     */   }
/*     */   
/*     */   private void acceptPack(ServerPackData $$0) {
/* 182 */     this.packLoadFeedback.reportUpdate($$0.id, PackLoadFeedback.Update.ACCEPTED);
/* 183 */     $$0.promptAccepted = true;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private ServerPackData findPackInfo(UUID $$0) {
/* 188 */     for (ServerPackData $$1 : this.packs) {
/* 189 */       if (!$$1.isRemoved() && $$1.id.equals($$0)) {
/* 190 */         return $$1;
/*     */       }
/*     */     } 
/* 193 */     return null;
/*     */   }
/*     */   
/*     */   public void popPack(UUID $$0) {
/* 197 */     ServerPackData $$1 = findPackInfo($$0);
/* 198 */     if ($$1 != null) {
/* 199 */       $$1.setRemovalReasonIfNotSet(RemovalReason.SERVER_REMOVED);
/* 200 */       registerForUpdate();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void popAll() {
/* 205 */     for (ServerPackData $$0 : this.packs) {
/* 206 */       $$0.setRemovalReasonIfNotSet(RemovalReason.SERVER_REMOVED);
/*     */     }
/* 208 */     registerForUpdate();
/*     */   }
/*     */   
/*     */   public void allowServerPacks() {
/* 212 */     this.packPromptStatus = PackPromptStatus.ALLOWED;
/*     */     
/* 214 */     for (ServerPackData $$0 : this.packs) {
/* 215 */       if (!$$0.promptAccepted && !$$0.isRemoved()) {
/* 216 */         acceptPack($$0);
/*     */       }
/*     */     } 
/*     */     
/* 220 */     registerForUpdate();
/*     */   }
/*     */   
/*     */   public void rejectServerPacks() {
/* 224 */     this.packPromptStatus = PackPromptStatus.DECLINED;
/* 225 */     for (ServerPackData $$0 : this.packs) {
/* 226 */       if (!$$0.promptAccepted) {
/* 227 */         $$0.setRemovalReasonIfNotSet(RemovalReason.DECLINED);
/*     */       }
/*     */     } 
/* 230 */     registerForUpdate();
/*     */   }
/*     */   
/*     */   public void resetPromptStatus() {
/* 234 */     this.packPromptStatus = PackPromptStatus.PENDING;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 239 */     boolean $$0 = updateDownloads();
/* 240 */     if (!$$0) {
/* 241 */       triggerReloadIfNeeded();
/*     */     }
/*     */     
/* 244 */     cleanupRemovedPacks();
/*     */   }
/*     */   
/*     */   private void cleanupRemovedPacks() {
/* 248 */     this.packs.removeIf($$0 -> {
/*     */           if ($$0.activationStatus != ActivationStatus.INACTIVE) {
/*     */             return false;
/*     */           }
/*     */           if ($$0.removalReason != null) {
/*     */             PackLoadFeedback.FinalResult $$1 = $$0.removalReason.serverResponse;
/*     */             if ($$1 != null) {
/*     */               this.packLoadFeedback.reportFinalResult($$0.id, $$1);
/*     */             }
/*     */             return true;
/*     */           } 
/*     */           return false;
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void onDownload(Collection<ServerPackData> $$0, DownloadQueue.BatchResult $$1) {
/* 267 */     if (!$$1.failed().isEmpty())
/*     */     {
/* 269 */       for (ServerPackData $$2 : this.packs) {
/* 270 */         if ($$2.activationStatus != ActivationStatus.ACTIVE) {
/* 271 */           if ($$1.failed().contains($$2.id)) {
/* 272 */             $$2.setRemovalReasonIfNotSet(RemovalReason.DOWNLOAD_FAILED); continue;
/*     */           } 
/* 274 */           $$2.setRemovalReasonIfNotSet(RemovalReason.DISCARDED);
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 280 */     for (ServerPackData $$3 : $$0) {
/* 281 */       Path $$4 = (Path)$$1.downloaded().get($$3.id);
/* 282 */       if ($$4 != null) {
/* 283 */         $$3.downloadStatus = PackDownloadStatus.DONE;
/* 284 */         $$3.path = $$4;
/*     */         
/* 286 */         if (!$$3.isRemoved()) {
/* 287 */           this.packLoadFeedback.reportUpdate($$3.id, PackLoadFeedback.Update.DOWNLOADED);
/*     */         }
/*     */       } 
/*     */     } 
/* 291 */     registerForUpdate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean updateDownloads() {
/* 299 */     List<ServerPackData> $$0 = new ArrayList<>();
/* 300 */     boolean $$1 = false;
/* 301 */     for (ServerPackData $$2 : this.packs) {
/* 302 */       if ($$2.isRemoved() || !$$2.promptAccepted) {
/*     */         continue;
/*     */       }
/*     */       
/* 306 */       if ($$2.downloadStatus != PackDownloadStatus.DONE) {
/* 307 */         $$1 = true;
/*     */       }
/*     */       
/* 310 */       if ($$2.downloadStatus == PackDownloadStatus.REQUESTED) {
/* 311 */         $$2.downloadStatus = PackDownloadStatus.PENDING;
/* 312 */         $$0.add($$2);
/*     */       } 
/*     */     } 
/*     */     
/* 316 */     if (!$$0.isEmpty()) {
/* 317 */       Map<UUID, DownloadQueue.DownloadRequest> $$3 = new HashMap<>();
/* 318 */       for (ServerPackData $$4 : $$0) {
/* 319 */         $$3.put($$4.id, new DownloadQueue.DownloadRequest($$4.url, $$4.hash));
/*     */       }
/* 321 */       this.downloader.download($$3, $$1 -> onDownload($$0, $$1));
/*     */     } 
/* 323 */     return $$1;
/*     */   }
/*     */   
/*     */   private void triggerReloadIfNeeded() {
/* 327 */     boolean $$0 = false;
/* 328 */     final List<ServerPackData> packsToLoad = new ArrayList<>();
/* 329 */     final List<ServerPackData> packsToUnload = new ArrayList<>();
/*     */     
/* 331 */     for (ServerPackData $$3 : this.packs) {
/* 332 */       if ($$3.activationStatus == ActivationStatus.PENDING) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 337 */       boolean $$4 = ($$3.promptAccepted && $$3.downloadStatus == PackDownloadStatus.DONE && !$$3.isRemoved());
/* 338 */       if ($$4 && $$3.activationStatus == ActivationStatus.INACTIVE) {
/* 339 */         $$1.add($$3);
/* 340 */         $$0 = true;
/*     */       } 
/*     */       
/* 343 */       if ($$3.activationStatus == ActivationStatus.ACTIVE) {
/* 344 */         if (!$$4) {
/* 345 */           $$0 = true;
/* 346 */           $$2.add($$3);
/*     */           continue;
/*     */         } 
/* 349 */         $$1.add($$3);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 354 */     if ($$0) {
/* 355 */       for (ServerPackData $$5 : $$1) {
/* 356 */         if ($$5.activationStatus != ActivationStatus.ACTIVE) {
/* 357 */           $$5.activationStatus = ActivationStatus.PENDING;
/*     */         }
/*     */       } 
/*     */       
/* 361 */       for (ServerPackData $$6 : $$2) {
/* 362 */         $$6.activationStatus = ActivationStatus.PENDING;
/*     */       }
/*     */       
/* 365 */       this.reloadConfig.scheduleReload(new PackReloadConfig.Callbacks()
/*     */           {
/*     */             public void onSuccess() {
/* 368 */               for (ServerPackManager.ServerPackData $$0 : packsToLoad) {
/* 369 */                 $$0.activationStatus = ServerPackManager.ActivationStatus.ACTIVE;
/* 370 */                 if ($$0.removalReason == null) {
/* 371 */                   ServerPackManager.this.packLoadFeedback.reportFinalResult($$0.id, PackLoadFeedback.FinalResult.APPLIED);
/*     */                 }
/*     */               } 
/* 374 */               for (ServerPackManager.ServerPackData $$1 : packsToUnload) {
/* 375 */                 $$1.activationStatus = ServerPackManager.ActivationStatus.INACTIVE;
/*     */               }
/* 377 */               ServerPackManager.this.registerForUpdate();
/*     */             }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             public void onFailure(boolean $$0) {
/*     */               // Byte code:
/*     */               //   0: iload_1
/*     */               //   1: ifne -> 135
/*     */               //   4: aload_0
/*     */               //   5: getfield val$packsToLoad : Ljava/util/List;
/*     */               //   8: invokeinterface clear : ()V
/*     */               //   13: aload_0
/*     */               //   14: getfield this$0 : Lnet/minecraft/client/resources/server/ServerPackManager;
/*     */               //   17: getfield packs : Ljava/util/List;
/*     */               //   20: invokeinterface iterator : ()Ljava/util/Iterator;
/*     */               //   25: astore_2
/*     */               //   26: aload_2
/*     */               //   27: invokeinterface hasNext : ()Z
/*     */               //   32: ifeq -> 125
/*     */               //   35: aload_2
/*     */               //   36: invokeinterface next : ()Ljava/lang/Object;
/*     */               //   41: checkcast net/minecraft/client/resources/server/ServerPackManager$ServerPackData
/*     */               //   44: astore_3
/*     */               //   45: getstatic net/minecraft/client/resources/server/ServerPackManager$2.$SwitchMap$net$minecraft$client$resources$server$ServerPackManager$ActivationStatus : [I
/*     */               //   48: aload_3
/*     */               //   49: getfield activationStatus : Lnet/minecraft/client/resources/server/ServerPackManager$ActivationStatus;
/*     */               //   52: invokevirtual ordinal : ()I
/*     */               //   55: iaload
/*     */               //   56: tableswitch default -> 122, 1 -> 84, 2 -> 98, 3 -> 115
/*     */               //   84: aload_0
/*     */               //   85: getfield val$packsToLoad : Ljava/util/List;
/*     */               //   88: aload_3
/*     */               //   89: invokeinterface add : (Ljava/lang/Object;)Z
/*     */               //   94: pop
/*     */               //   95: goto -> 122
/*     */               //   98: aload_3
/*     */               //   99: getstatic net/minecraft/client/resources/server/ServerPackManager$ActivationStatus.INACTIVE : Lnet/minecraft/client/resources/server/ServerPackManager$ActivationStatus;
/*     */               //   102: putfield activationStatus : Lnet/minecraft/client/resources/server/ServerPackManager$ActivationStatus;
/*     */               //   105: aload_3
/*     */               //   106: getstatic net/minecraft/client/resources/server/ServerPackManager$RemovalReason.ACTIVATION_FAILED : Lnet/minecraft/client/resources/server/ServerPackManager$RemovalReason;
/*     */               //   109: invokevirtual setRemovalReasonIfNotSet : (Lnet/minecraft/client/resources/server/ServerPackManager$RemovalReason;)V
/*     */               //   112: goto -> 122
/*     */               //   115: aload_3
/*     */               //   116: getstatic net/minecraft/client/resources/server/ServerPackManager$RemovalReason.DISCARDED : Lnet/minecraft/client/resources/server/ServerPackManager$RemovalReason;
/*     */               //   119: invokevirtual setRemovalReasonIfNotSet : (Lnet/minecraft/client/resources/server/ServerPackManager$RemovalReason;)V
/*     */               //   122: goto -> 26
/*     */               //   125: aload_0
/*     */               //   126: getfield this$0 : Lnet/minecraft/client/resources/server/ServerPackManager;
/*     */               //   129: invokevirtual registerForUpdate : ()V
/*     */               //   132: goto -> 187
/*     */               //   135: aload_0
/*     */               //   136: getfield this$0 : Lnet/minecraft/client/resources/server/ServerPackManager;
/*     */               //   139: getfield packs : Ljava/util/List;
/*     */               //   142: invokeinterface iterator : ()Ljava/util/Iterator;
/*     */               //   147: astore_2
/*     */               //   148: aload_2
/*     */               //   149: invokeinterface hasNext : ()Z
/*     */               //   154: ifeq -> 187
/*     */               //   157: aload_2
/*     */               //   158: invokeinterface next : ()Ljava/lang/Object;
/*     */               //   163: checkcast net/minecraft/client/resources/server/ServerPackManager$ServerPackData
/*     */               //   166: astore_3
/*     */               //   167: aload_3
/*     */               //   168: getfield activationStatus : Lnet/minecraft/client/resources/server/ServerPackManager$ActivationStatus;
/*     */               //   171: getstatic net/minecraft/client/resources/server/ServerPackManager$ActivationStatus.PENDING : Lnet/minecraft/client/resources/server/ServerPackManager$ActivationStatus;
/*     */               //   174: if_acmpne -> 184
/*     */               //   177: aload_3
/*     */               //   178: getstatic net/minecraft/client/resources/server/ServerPackManager$ActivationStatus.INACTIVE : Lnet/minecraft/client/resources/server/ServerPackManager$ActivationStatus;
/*     */               //   181: putfield activationStatus : Lnet/minecraft/client/resources/server/ServerPackManager$ActivationStatus;
/*     */               //   184: goto -> 148
/*     */               //   187: return
/*     */               // Line number table:
/*     */               //   Java source line number -> byte code offset
/*     */               //   #382	-> 0
/*     */               //   #384	-> 4
/*     */               //   #385	-> 13
/*     */               //   #386	-> 45
/*     */               //   #387	-> 84
/*     */               //   #389	-> 98
/*     */               //   #390	-> 105
/*     */               //   #391	-> 112
/*     */               //   #392	-> 115
/*     */               //   #394	-> 122
/*     */               //   #395	-> 125
/*     */               //   #398	-> 135
/*     */               //   #399	-> 167
/*     */               //   #400	-> 177
/*     */               //   #402	-> 184
/*     */               //   #404	-> 187
/*     */               // Local variable table:
/*     */               //   start	length	slot	name	descriptor
/*     */               //   0	188	0	this	Lnet/minecraft/client/resources/server/ServerPackManager$1;
/*     */               //   0	188	1	$$0	Z
/*     */               //   45	77	3	$$1	Lnet/minecraft/client/resources/server/ServerPackManager$ServerPackData;
/*     */               //   167	17	3	$$2	Lnet/minecraft/client/resources/server/ServerPackManager$ServerPackData;
/*     */             }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             public List<PackReloadConfig.IdAndPath> packsToLoad() {
/* 408 */               return packsToLoad.stream().map($$0 -> new PackReloadConfig.IdAndPath($$0.id, $$0.path)).toList();
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\server\ServerPackManager.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */