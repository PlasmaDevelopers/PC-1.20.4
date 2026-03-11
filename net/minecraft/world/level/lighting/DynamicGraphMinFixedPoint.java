/*     */ package net.minecraft.world.level.lighting;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.longs.Long2ByteMap;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ByteOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.longs.LongArrayList;
/*     */ import it.unimi.dsi.fastutil.longs.LongList;
/*     */ import java.util.function.LongPredicate;
/*     */ import net.minecraft.util.Mth;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class DynamicGraphMinFixedPoint
/*     */ {
/*     */   public static final long SOURCE = 9223372036854775807L;
/*     */   private static final int NO_COMPUTED_LEVEL = 255;
/*     */   protected final int levelCount;
/*     */   private final LeveledPriorityQueue priorityQueue;
/*     */   private final Long2ByteMap computedLevels;
/*     */   private volatile boolean hasWork;
/*     */   
/*     */   protected DynamicGraphMinFixedPoint(int $$0, int $$1, final int minMapSize) {
/*  40 */     if ($$0 >= 254) {
/*  41 */       throw new IllegalArgumentException("Level count must be < 254.");
/*     */     }
/*  43 */     this.levelCount = $$0;
/*     */     
/*  45 */     this.priorityQueue = new LeveledPriorityQueue($$0, $$1);
/*     */     
/*  47 */     this.computedLevels = (Long2ByteMap)new Long2ByteOpenHashMap(minMapSize, 0.5F)
/*     */       {
/*     */         protected void rehash(int $$0) {
/*  50 */           if ($$0 > minMapSize) {
/*  51 */             super.rehash($$0);
/*     */           }
/*     */         }
/*     */       };
/*  55 */     this.computedLevels.defaultReturnValue((byte)-1);
/*     */   }
/*     */   
/*     */   protected void removeFromQueue(long $$0) {
/*  59 */     int $$1 = this.computedLevels.remove($$0) & 0xFF;
/*  60 */     if ($$1 == 255) {
/*     */       return;
/*     */     }
/*  63 */     int $$2 = getLevel($$0);
/*  64 */     int $$3 = calculatePriority($$2, $$1);
/*  65 */     this.priorityQueue.dequeue($$0, $$3, this.levelCount);
/*  66 */     this.hasWork = !this.priorityQueue.isEmpty();
/*     */   }
/*     */   
/*     */   public void removeIf(LongPredicate $$0) {
/*  70 */     LongArrayList longArrayList = new LongArrayList();
/*     */     
/*  72 */     this.computedLevels.keySet().forEach($$2 -> {
/*     */           if ($$0.test($$2)) {
/*     */             $$1.add($$2);
/*     */           }
/*     */         });
/*     */     
/*  78 */     longArrayList.forEach(this::removeFromQueue);
/*     */   }
/*     */   
/*     */   private int calculatePriority(int $$0, int $$1) {
/*  82 */     return Math.min(Math.min($$0, $$1), this.levelCount - 1);
/*     */   }
/*     */   
/*     */   protected void checkNode(long $$0) {
/*  86 */     checkEdge($$0, $$0, this.levelCount - 1, false);
/*     */   }
/*     */   
/*     */   protected void checkEdge(long $$0, long $$1, int $$2, boolean $$3) {
/*  90 */     checkEdge($$0, $$1, $$2, getLevel($$1), this.computedLevels.get($$1) & 0xFF, $$3);
/*  91 */     this.hasWork = !this.priorityQueue.isEmpty();
/*     */   }
/*     */   private void checkEdge(long $$0, long $$1, int $$2, int $$3, int $$4, boolean $$5) {
/*     */     int $$8;
/*  95 */     if (isSource($$1)) {
/*     */       return;
/*     */     }
/*  98 */     $$2 = Mth.clamp($$2, 0, this.levelCount - 1);
/*  99 */     $$3 = Mth.clamp($$3, 0, this.levelCount - 1);
/* 100 */     boolean $$6 = ($$4 == 255);
/* 101 */     if ($$6) {
/* 102 */       $$4 = $$3;
/*     */     }
/*     */     
/* 105 */     if ($$5) {
/*     */       
/* 107 */       int $$7 = Math.min($$4, $$2);
/*     */     } else {
/* 109 */       $$8 = Mth.clamp(getComputedLevel($$1, $$0, $$2), 0, this.levelCount - 1);
/*     */     } 
/* 111 */     int $$9 = calculatePriority($$3, $$4);
/* 112 */     if ($$3 != $$8) {
/* 113 */       int $$10 = calculatePriority($$3, $$8);
/* 114 */       if ($$9 != $$10 && !$$6) {
/* 115 */         this.priorityQueue.dequeue($$1, $$9, $$10);
/*     */       }
/* 117 */       this.priorityQueue.enqueue($$1, $$10);
/* 118 */       this.computedLevels.put($$1, (byte)$$8);
/* 119 */     } else if (!$$6) {
/* 120 */       this.priorityQueue.dequeue($$1, $$9, this.levelCount);
/* 121 */       this.computedLevels.remove($$1);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected final void checkNeighbor(long $$0, long $$1, int $$2, boolean $$3) {
/* 126 */     int $$4 = this.computedLevels.get($$1) & 0xFF;
/* 127 */     int $$5 = Mth.clamp(computeLevelFromNeighbor($$0, $$1, $$2), 0, this.levelCount - 1);
/* 128 */     if ($$3) {
/* 129 */       checkEdge($$0, $$1, $$5, getLevel($$1), $$4, $$3);
/*     */     } else {
/*     */       int $$8;
/* 132 */       boolean $$6 = ($$4 == 255);
/* 133 */       if ($$6) {
/* 134 */         int $$7 = Mth.clamp(getLevel($$1), 0, this.levelCount - 1);
/*     */       } else {
/* 136 */         $$8 = $$4;
/*     */       } 
/*     */       
/* 139 */       if ($$5 == $$8)
/*     */       {
/* 141 */         checkEdge($$0, $$1, this.levelCount - 1, $$6 ? $$8 : getLevel($$1), $$4, $$3);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   protected final boolean hasWork() {
/* 147 */     return this.hasWork;
/*     */   }
/*     */   
/*     */   protected final int runUpdates(int $$0) {
/* 151 */     if (this.priorityQueue.isEmpty()) {
/* 152 */       return $$0;
/*     */     }
/* 154 */     while (!this.priorityQueue.isEmpty() && $$0 > 0) {
/* 155 */       $$0--;
/* 156 */       long $$1 = this.priorityQueue.removeFirstLong();
/* 157 */       int $$2 = Mth.clamp(getLevel($$1), 0, this.levelCount - 1);
/* 158 */       int $$3 = this.computedLevels.remove($$1) & 0xFF;
/* 159 */       if ($$3 < $$2) {
/*     */         
/* 161 */         setLevel($$1, $$3);
/* 162 */         checkNeighborsAfterUpdate($$1, $$3, true); continue;
/* 163 */       }  if ($$3 > $$2) {
/*     */         
/* 165 */         setLevel($$1, this.levelCount - 1);
/* 166 */         if ($$3 != this.levelCount - 1) {
/*     */           
/* 168 */           this.priorityQueue.enqueue($$1, calculatePriority(this.levelCount - 1, $$3));
/* 169 */           this.computedLevels.put($$1, (byte)$$3);
/*     */         } 
/* 171 */         checkNeighborsAfterUpdate($$1, $$2, false);
/*     */       } 
/*     */     } 
/* 174 */     this.hasWork = !this.priorityQueue.isEmpty();
/* 175 */     return $$0;
/*     */   }
/*     */   
/*     */   public int getQueueSize() {
/* 179 */     return this.computedLevels.size();
/*     */   }
/*     */   
/*     */   protected boolean isSource(long $$0) {
/* 183 */     return ($$0 == Long.MAX_VALUE);
/*     */   }
/*     */   
/*     */   protected abstract int getComputedLevel(long paramLong1, long paramLong2, int paramInt);
/*     */   
/*     */   protected abstract void checkNeighborsAfterUpdate(long paramLong, int paramInt, boolean paramBoolean);
/*     */   
/*     */   protected abstract int getLevel(long paramLong);
/*     */   
/*     */   protected abstract void setLevel(long paramLong, int paramInt);
/*     */   
/*     */   protected abstract int computeLevelFromNeighbor(long paramLong1, long paramLong2, int paramInt);
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\lighting\DynamicGraphMinFixedPoint.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */