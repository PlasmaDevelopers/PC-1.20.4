/*    */ package net.minecraft.client.color.block;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
/*    */ import java.util.Arrays;
/*    */ import java.util.concurrent.locks.ReentrantReadWriteLock;
/*    */ import net.minecraft.util.Mth;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class CacheData
/*    */ {
/* 19 */   private final Int2ObjectArrayMap<int[]> cache = new Int2ObjectArrayMap(16);
/* 20 */   private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
/* 21 */   private static final int BLOCKS_PER_LAYER = Mth.square(16);
/*    */   
/*    */   private volatile boolean invalidated;
/*    */   
/*    */   public int[] getLayer(int $$0) {
/* 26 */     this.lock.readLock().lock();
/*    */     try {
/* 28 */       int[] $$1 = (int[])this.cache.get($$0);
/* 29 */       if ($$1 != null) {
/* 30 */         return $$1;
/*    */       }
/*    */     } finally {
/* 33 */       this.lock.readLock().unlock();
/*    */     } 
/*    */     
/* 36 */     this.lock.writeLock().lock();
/*    */     
/*    */     try {
/* 39 */       return (int[])this.cache.computeIfAbsent($$0, $$0 -> allocateLayer());
/*    */     } finally {
/* 41 */       this.lock.writeLock().unlock();
/*    */     } 
/*    */   }
/*    */   
/*    */   private int[] allocateLayer() {
/* 46 */     int[] $$0 = new int[BLOCKS_PER_LAYER];
/* 47 */     Arrays.fill($$0, -1);
/* 48 */     return $$0;
/*    */   }
/*    */   
/*    */   public boolean isInvalidated() {
/* 52 */     return this.invalidated;
/*    */   }
/*    */   
/*    */   public void invalidate() {
/* 56 */     this.invalidated = true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\color\block\BlockTintCache$CacheData.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */