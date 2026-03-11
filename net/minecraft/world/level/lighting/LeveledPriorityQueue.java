/*    */ package net.minecraft.world.level.lighting;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.longs.LongLinkedOpenHashSet;
/*    */ 
/*    */ public class LeveledPriorityQueue
/*    */ {
/*    */   private final int levelCount;
/*    */   private final LongLinkedOpenHashSet[] queues;
/*    */   private int firstQueuedLevel;
/*    */   
/*    */   public LeveledPriorityQueue(int $$0, final int minSize) {
/* 12 */     this.levelCount = $$0;
/* 13 */     this.queues = new LongLinkedOpenHashSet[$$0];
/* 14 */     for (int $$2 = 0; $$2 < $$0; $$2++) {
/* 15 */       this.queues[$$2] = new LongLinkedOpenHashSet(minSize, 0.5F)
/*    */         {
/*    */           protected void rehash(int $$0) {
/* 18 */             if ($$0 > minSize) {
/* 19 */               super.rehash($$0);
/*    */             }
/*    */           }
/*    */         };
/*    */     } 
/* 24 */     this.firstQueuedLevel = $$0;
/*    */   }
/*    */   
/*    */   public long removeFirstLong() {
/* 28 */     LongLinkedOpenHashSet $$0 = this.queues[this.firstQueuedLevel];
/* 29 */     long $$1 = $$0.removeFirstLong();
/* 30 */     if ($$0.isEmpty()) {
/* 31 */       checkFirstQueuedLevel(this.levelCount);
/*    */     }
/* 33 */     return $$1;
/*    */   }
/*    */   
/*    */   public boolean isEmpty() {
/* 37 */     return (this.firstQueuedLevel >= this.levelCount);
/*    */   }
/*    */   
/*    */   public void dequeue(long $$0, int $$1, int $$2) {
/* 41 */     LongLinkedOpenHashSet $$3 = this.queues[$$1];
/* 42 */     $$3.remove($$0);
/* 43 */     if ($$3.isEmpty() && this.firstQueuedLevel == $$1) {
/* 44 */       checkFirstQueuedLevel($$2);
/*    */     }
/*    */   }
/*    */   
/*    */   public void enqueue(long $$0, int $$1) {
/* 49 */     this.queues[$$1].add($$0);
/* 50 */     if (this.firstQueuedLevel > $$1) {
/* 51 */       this.firstQueuedLevel = $$1;
/*    */     }
/*    */   }
/*    */   
/*    */   private void checkFirstQueuedLevel(int $$0) {
/* 56 */     int $$1 = this.firstQueuedLevel;
/* 57 */     this.firstQueuedLevel = $$0;
/* 58 */     for (int $$2 = $$1 + 1; $$2 < $$0; $$2++) {
/* 59 */       if (!this.queues[$$2].isEmpty()) {
/* 60 */         this.firstQueuedLevel = $$2;
/*    */         break;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\lighting\LeveledPriorityQueue.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */