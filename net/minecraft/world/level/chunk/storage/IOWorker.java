/*     */ package net.minecraft.world.level.chunk.storage;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import com.mojang.datafixers.util.Either;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.Path;
/*     */ import java.util.BitSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.CompletionStage;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.IntTag;
/*     */ import net.minecraft.nbt.StreamTagVisitor;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.nbt.visitors.CollectFields;
/*     */ import net.minecraft.nbt.visitors.FieldSelector;
/*     */ import net.minecraft.util.Unit;
/*     */ import net.minecraft.util.thread.ProcessorHandle;
/*     */ import net.minecraft.util.thread.ProcessorMailbox;
/*     */ import net.minecraft.util.thread.StrictQueue;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IOWorker
/*     */   implements ChunkScanAccess, AutoCloseable
/*     */ {
/*  48 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private enum Priority {
/*  51 */     FOREGROUND, BACKGROUND, SHUTDOWN;
/*     */   }
/*     */   
/*     */   private static class PendingStore {
/*     */     @Nullable
/*     */     CompoundTag data;
/*  57 */     final CompletableFuture<Void> result = new CompletableFuture<>();
/*     */     
/*     */     public PendingStore(@Nullable CompoundTag $$0) {
/*  60 */       this.data = $$0;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*  65 */   private final AtomicBoolean shutdownRequested = new AtomicBoolean();
/*     */   
/*     */   private final ProcessorMailbox<StrictQueue.IntRunnable> mailbox;
/*     */   
/*     */   private final RegionFileStorage storage;
/*  70 */   private final Map<ChunkPos, PendingStore> pendingWrites = Maps.newLinkedHashMap();
/*     */   
/*  72 */   private final Long2ObjectLinkedOpenHashMap<CompletableFuture<BitSet>> regionCacheForBlender = new Long2ObjectLinkedOpenHashMap();
/*     */   private static final int REGION_CACHE_SIZE = 1024;
/*     */   
/*     */   protected IOWorker(Path $$0, boolean $$1, String $$2) {
/*  76 */     this.storage = new RegionFileStorage($$0, $$1);
/*  77 */     this.mailbox = new ProcessorMailbox((StrictQueue)new StrictQueue.FixedPriorityQueue((Priority.values()).length), Util.ioPool(), "IOWorker-" + $$2);
/*     */   }
/*     */   
/*     */   public boolean isOldChunkAround(ChunkPos $$0, int $$1) {
/*  81 */     ChunkPos $$2 = new ChunkPos($$0.x - $$1, $$0.z - $$1);
/*  82 */     ChunkPos $$3 = new ChunkPos($$0.x + $$1, $$0.z + $$1);
/*     */     
/*  84 */     for (int $$4 = $$2.getRegionX(); $$4 <= $$3.getRegionX(); $$4++) {
/*  85 */       for (int $$5 = $$2.getRegionZ(); $$5 <= $$3.getRegionZ(); $$5++) {
/*     */         
/*  87 */         BitSet $$6 = getOrCreateOldDataForRegion($$4, $$5).join();
/*  88 */         if (!$$6.isEmpty()) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*  93 */           ChunkPos $$7 = ChunkPos.minFromRegion($$4, $$5);
/*  94 */           int $$8 = Math.max($$2.x - $$7.x, 0);
/*  95 */           int $$9 = Math.max($$2.z - $$7.z, 0);
/*  96 */           int $$10 = Math.min($$3.x - $$7.x, 31);
/*  97 */           int $$11 = Math.min($$3.z - $$7.z, 31);
/*     */           
/*  99 */           for (int $$12 = $$8; $$12 <= $$10; $$12++) {
/* 100 */             for (int $$13 = $$9; $$13 <= $$11; $$13++) {
/* 101 */               int $$14 = $$13 * 32 + $$12;
/* 102 */               if ($$6.get($$14)) {
/* 103 */                 return true;
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 110 */     return false;
/*     */   }
/*     */   
/*     */   private CompletableFuture<BitSet> getOrCreateOldDataForRegion(int $$0, int $$1) {
/* 114 */     long $$2 = ChunkPos.asLong($$0, $$1);
/* 115 */     synchronized (this.regionCacheForBlender) {
/* 116 */       CompletableFuture<BitSet> $$3 = (CompletableFuture<BitSet>)this.regionCacheForBlender.getAndMoveToFirst($$2);
/* 117 */       if ($$3 == null) {
/* 118 */         $$3 = createOldDataForRegion($$0, $$1);
/*     */         
/* 120 */         this.regionCacheForBlender.putAndMoveToFirst($$2, $$3);
/* 121 */         if (this.regionCacheForBlender.size() > 1024) {
/* 122 */           this.regionCacheForBlender.removeLast();
/*     */         }
/*     */       } 
/* 125 */       return $$3;
/*     */     } 
/*     */   }
/*     */   
/*     */   private CompletableFuture<BitSet> createOldDataForRegion(int $$0, int $$1) {
/* 130 */     return CompletableFuture.supplyAsync(() -> {
/*     */           ChunkPos $$2 = ChunkPos.minFromRegion($$0, $$1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           ChunkPos $$3 = ChunkPos.maxFromRegion($$0, $$1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           BitSet $$4 = new BitSet();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           ChunkPos.rangeClosed($$2, $$3).forEach(());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           return $$4;
/* 158 */         }Util.backgroundExecutor());
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isOldChunk(CompoundTag $$0) {
/* 163 */     if (!$$0.contains("DataVersion", 99) || $$0.getInt("DataVersion") < 3441) {
/* 164 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 168 */     return $$0.contains("blending_data", 10);
/*     */   }
/*     */   
/*     */   public CompletableFuture<Void> store(ChunkPos $$0, @Nullable CompoundTag $$1) {
/* 172 */     return submitTask(() -> {
/*     */           PendingStore $$2 = this.pendingWrites.computeIfAbsent($$0, ());
/*     */           $$2.data = $$1;
/*     */           return Either.left($$2.result);
/* 176 */         }).thenCompose(Function.identity());
/*     */   }
/*     */   
/*     */   public CompletableFuture<Optional<CompoundTag>> loadAsync(ChunkPos $$0) {
/* 180 */     return submitTask(() -> {
/*     */           PendingStore $$1 = this.pendingWrites.get($$0);
/*     */           if ($$1 != null) {
/*     */             return Either.left(Optional.ofNullable($$1.data));
/*     */           }
/*     */           try {
/*     */             CompoundTag $$2 = this.storage.read($$0);
/*     */             return Either.left(Optional.ofNullable($$2));
/* 188 */           } catch (Exception $$3) {
/*     */             LOGGER.warn("Failed to read chunk {}", $$0, $$3);
/*     */             return Either.right($$3);
/*     */           } 
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public CompletableFuture<Void> synchronize(boolean $$0) {
/* 197 */     CompletableFuture<Void> $$1 = submitTask(() -> Either.left(CompletableFuture.allOf((CompletableFuture<?>[])this.pendingWrites.values().stream().map(()).toArray(())))).thenCompose(Function.identity());
/* 198 */     if ($$0) {
/* 199 */       return $$1.thenCompose($$0 -> submitTask(()));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 209 */     return $$1.thenCompose($$0 -> submitTask(()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public CompletableFuture<Void> scanChunk(ChunkPos $$0, StreamTagVisitor $$1) {
/* 215 */     return submitTask(() -> {
/*     */           try {
/*     */             PendingStore $$2 = this.pendingWrites.get($$0);
/*     */             if ($$2 != null) {
/*     */               if ($$2.data != null) {
/*     */                 $$2.data.acceptAsRoot($$1);
/*     */               }
/*     */             } else {
/*     */               this.storage.scanChunk($$0, $$1);
/*     */             } 
/*     */             return Either.left(null);
/* 226 */           } catch (Exception $$3) {
/*     */             LOGGER.warn("Failed to bulk scan chunk {}", $$0, $$3);
/*     */             return Either.right($$3);
/*     */           } 
/*     */         });
/*     */   }
/*     */   
/*     */   private <T> CompletableFuture<T> submitTask(Supplier<Either<T, Exception>> $$0) {
/* 234 */     return this.mailbox.askEither($$1 -> new StrictQueue.IntRunnable(Priority.FOREGROUND.ordinal(), ()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void storePendingChunk() {
/* 243 */     if (this.pendingWrites.isEmpty()) {
/*     */       return;
/*     */     }
/* 246 */     Iterator<Map.Entry<ChunkPos, PendingStore>> $$0 = this.pendingWrites.entrySet().iterator();
/*     */     
/* 248 */     Map.Entry<ChunkPos, PendingStore> $$1 = $$0.next();
/* 249 */     $$0.remove();
/* 250 */     runStore($$1.getKey(), $$1.getValue());
/* 251 */     tellStorePending();
/*     */   }
/*     */   
/*     */   private void tellStorePending() {
/* 255 */     this.mailbox.tell(new StrictQueue.IntRunnable(Priority.BACKGROUND.ordinal(), this::storePendingChunk));
/*     */   }
/*     */   
/*     */   private void runStore(ChunkPos $$0, PendingStore $$1) {
/*     */     try {
/* 260 */       this.storage.write($$0, $$1.data);
/* 261 */       $$1.result.complete(null);
/* 262 */     } catch (Exception $$2) {
/* 263 */       LOGGER.error("Failed to store chunk {}", $$0, $$2);
/* 264 */       $$1.result.completeExceptionally($$2);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 270 */     if (!this.shutdownRequested.compareAndSet(false, true)) {
/*     */       return;
/*     */     }
/*     */     
/* 274 */     this.mailbox.ask($$0 -> new StrictQueue.IntRunnable(Priority.SHUTDOWN.ordinal(), ())).join();
/* 275 */     this.mailbox.close();
/*     */     
/*     */     try {
/* 278 */       this.storage.close();
/* 279 */     } catch (Exception $$0) {
/* 280 */       LOGGER.error("Failed to close storage", $$0);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\chunk\storage\IOWorker.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */