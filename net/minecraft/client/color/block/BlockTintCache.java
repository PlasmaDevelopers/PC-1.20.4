/*     */ package net.minecraft.client.color.block;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
/*     */ import java.util.Arrays;
/*     */ import java.util.concurrent.locks.ReentrantReadWriteLock;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.function.ToIntFunction;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.SectionPos;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ 
/*     */ public class BlockTintCache {
/*     */   private static final int MAX_CACHE_ENTRIES = 256;
/*     */   
/*     */   private static class CacheData {
/*  19 */     private final Int2ObjectArrayMap<int[]> cache = new Int2ObjectArrayMap(16);
/*  20 */     private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
/*  21 */     private static final int BLOCKS_PER_LAYER = Mth.square(16);
/*     */     
/*     */     private volatile boolean invalidated;
/*     */     
/*     */     public int[] getLayer(int $$0) {
/*  26 */       this.lock.readLock().lock();
/*     */       try {
/*  28 */         int[] $$1 = (int[])this.cache.get($$0);
/*  29 */         if ($$1 != null) {
/*  30 */           return $$1;
/*     */         }
/*     */       } finally {
/*  33 */         this.lock.readLock().unlock();
/*     */       } 
/*     */       
/*  36 */       this.lock.writeLock().lock();
/*     */       
/*     */       try {
/*  39 */         return (int[])this.cache.computeIfAbsent($$0, $$0 -> allocateLayer());
/*     */       } finally {
/*  41 */         this.lock.writeLock().unlock();
/*     */       } 
/*     */     }
/*     */     
/*     */     private int[] allocateLayer() {
/*  46 */       int[] $$0 = new int[BLOCKS_PER_LAYER];
/*  47 */       Arrays.fill($$0, -1);
/*  48 */       return $$0;
/*     */     }
/*     */     
/*     */     public boolean isInvalidated() {
/*  52 */       return this.invalidated;
/*     */     }
/*     */     
/*     */     public void invalidate() {
/*  56 */       this.invalidated = true;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class LatestCacheInfo {
/*  61 */     public int x = Integer.MIN_VALUE;
/*  62 */     public int z = Integer.MIN_VALUE;
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     BlockTintCache.CacheData cache;
/*     */   }
/*     */ 
/*     */   
/*  70 */   private final ThreadLocal<LatestCacheInfo> latestChunkOnThread = ThreadLocal.withInitial(LatestCacheInfo::new);
/*     */ 
/*     */ 
/*     */   
/*  74 */   private final Long2ObjectLinkedOpenHashMap<CacheData> cache = new Long2ObjectLinkedOpenHashMap(256, 0.25F);
/*  75 */   private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
/*     */   private final ToIntFunction<BlockPos> source;
/*     */   
/*     */   public BlockTintCache(ToIntFunction<BlockPos> $$0) {
/*  79 */     this.source = $$0;
/*     */   }
/*     */   
/*     */   public int getColor(BlockPos $$0) {
/*  83 */     int $$1 = SectionPos.blockToSectionCoord($$0.getX());
/*  84 */     int $$2 = SectionPos.blockToSectionCoord($$0.getZ());
/*     */     
/*  86 */     LatestCacheInfo $$3 = this.latestChunkOnThread.get();
/*  87 */     if ($$3.x != $$1 || $$3.z != $$2 || $$3.cache == null || $$3.cache.isInvalidated()) {
/*  88 */       $$3.x = $$1;
/*  89 */       $$3.z = $$2;
/*  90 */       $$3.cache = findOrCreateChunkCache($$1, $$2);
/*     */     } 
/*  92 */     int[] $$4 = $$3.cache.getLayer($$0.getY());
/*     */     
/*  94 */     int $$5 = $$0.getX() & 0xF;
/*  95 */     int $$6 = $$0.getZ() & 0xF;
/*  96 */     int $$7 = $$6 << 4 | $$5;
/*  97 */     int $$8 = $$4[$$7];
/*  98 */     if ($$8 != -1) {
/*  99 */       return $$8;
/*     */     }
/* 101 */     int $$9 = this.source.applyAsInt($$0);
/* 102 */     $$4[$$7] = $$9;
/* 103 */     return $$9;
/*     */   }
/*     */   
/*     */   public void invalidateForChunk(int $$0, int $$1) {
/*     */     try {
/* 108 */       this.lock.writeLock().lock();
/*     */       
/* 110 */       for (int $$2 = -1; $$2 <= 1; $$2++) {
/* 111 */         for (int $$3 = -1; $$3 <= 1; $$3++) {
/* 112 */           long $$4 = ChunkPos.asLong($$0 + $$2, $$1 + $$3);
/* 113 */           CacheData $$5 = (CacheData)this.cache.remove($$4);
/* 114 */           if ($$5 != null) {
/* 115 */             $$5.invalidate();
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } finally {
/* 120 */       this.lock.writeLock().unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void invalidateAll() {
/*     */     try {
/* 126 */       this.lock.writeLock().lock();
/* 127 */       this.cache.values().forEach(CacheData::invalidate);
/* 128 */       this.cache.clear();
/*     */     } finally {
/* 130 */       this.lock.writeLock().unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   private CacheData findOrCreateChunkCache(int $$0, int $$1) {
/* 135 */     long $$2 = ChunkPos.asLong($$0, $$1);
/* 136 */     this.lock.readLock().lock();
/*     */     try {
/* 138 */       CacheData $$3 = (CacheData)this.cache.get($$2);
/* 139 */       if ($$3 != null) {
/* 140 */         return $$3;
/*     */       }
/*     */     } finally {
/* 143 */       this.lock.readLock().unlock();
/*     */     } 
/*     */     
/* 146 */     this.lock.writeLock().lock();
/*     */     
/*     */     try {
/* 149 */       CacheData $$4 = (CacheData)this.cache.get($$2);
/* 150 */       if ($$4 != null) {
/* 151 */         return $$4;
/*     */       }
/* 153 */       CacheData $$5 = new CacheData();
/*     */       
/* 155 */       if (this.cache.size() >= 256) {
/* 156 */         CacheData $$6 = (CacheData)this.cache.removeFirst();
/* 157 */         if ($$6 != null) {
/* 158 */           $$6.invalidate();
/*     */         }
/*     */       } 
/* 161 */       this.cache.put($$2, $$5);
/* 162 */       return $$5;
/*     */     } finally {
/*     */       
/* 165 */       this.lock.writeLock().unlock();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\color\block\BlockTintCache.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */