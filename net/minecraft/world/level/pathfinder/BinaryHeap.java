/*     */ package net.minecraft.world.level.pathfinder;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ 
/*     */ public class BinaryHeap {
/*   6 */   private Node[] heap = new Node[128];
/*     */   
/*     */   private int size;
/*     */   
/*     */   public Node insert(Node $$0) {
/*  11 */     if ($$0.heapIdx >= 0) {
/*  12 */       throw new IllegalStateException("OW KNOWS!");
/*     */     }
/*     */     
/*  15 */     if (this.size == this.heap.length) {
/*  16 */       Node[] $$1 = new Node[this.size << 1];
/*  17 */       System.arraycopy(this.heap, 0, $$1, 0, this.size);
/*  18 */       this.heap = $$1;
/*     */     } 
/*     */ 
/*     */     
/*  22 */     this.heap[this.size] = $$0;
/*  23 */     $$0.heapIdx = this.size;
/*  24 */     upHeap(this.size++);
/*     */     
/*  26 */     return $$0;
/*     */   }
/*     */   
/*     */   public void clear() {
/*  30 */     this.size = 0;
/*     */   }
/*     */   
/*     */   public Node peek() {
/*  34 */     return this.heap[0];
/*     */   }
/*     */   
/*     */   public Node pop() {
/*  38 */     Node $$0 = this.heap[0];
/*  39 */     this.heap[0] = this.heap[--this.size];
/*  40 */     this.heap[this.size] = null;
/*  41 */     if (this.size > 0) {
/*  42 */       downHeap(0);
/*     */     }
/*  44 */     $$0.heapIdx = -1;
/*  45 */     return $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void remove(Node $$0) {
/*  50 */     this.heap[$$0.heapIdx] = this.heap[--this.size];
/*  51 */     this.heap[this.size] = null;
/*  52 */     if (this.size > $$0.heapIdx) {
/*  53 */       if ((this.heap[$$0.heapIdx]).f < $$0.f) {
/*  54 */         upHeap($$0.heapIdx);
/*     */       } else {
/*  56 */         downHeap($$0.heapIdx);
/*     */       } 
/*     */     }
/*     */     
/*  60 */     $$0.heapIdx = -1;
/*     */   }
/*     */   
/*     */   public void changeCost(Node $$0, float $$1) {
/*  64 */     float $$2 = $$0.f;
/*  65 */     $$0.f = $$1;
/*  66 */     if ($$1 < $$2) {
/*  67 */       upHeap($$0.heapIdx);
/*     */     } else {
/*  69 */       downHeap($$0.heapIdx);
/*     */     } 
/*     */   }
/*     */   
/*     */   public int size() {
/*  74 */     return this.size;
/*     */   }
/*     */   
/*     */   private void upHeap(int $$0) {
/*  78 */     Node $$1 = this.heap[$$0];
/*  79 */     float $$2 = $$1.f;
/*  80 */     while ($$0 > 0) {
/*  81 */       int $$3 = $$0 - 1 >> 1;
/*  82 */       Node $$4 = this.heap[$$3];
/*  83 */       if ($$2 < $$4.f) {
/*  84 */         this.heap[$$0] = $$4;
/*  85 */         $$4.heapIdx = $$0;
/*  86 */         $$0 = $$3;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  91 */     this.heap[$$0] = $$1;
/*  92 */     $$1.heapIdx = $$0;
/*     */   }
/*     */   
/*     */   private void downHeap(int $$0) {
/*  96 */     Node $$1 = this.heap[$$0];
/*  97 */     float $$2 = $$1.f; while (true) {
/*     */       Node $$9;
/*     */       float $$10;
/* 100 */       int $$3 = 1 + ($$0 << 1);
/* 101 */       int $$4 = $$3 + 1;
/*     */       
/* 103 */       if ($$3 >= this.size) {
/*     */         break;
/*     */       }
/*     */ 
/*     */       
/* 108 */       Node $$5 = this.heap[$$3];
/* 109 */       float $$6 = $$5.f;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 114 */       if ($$4 >= this.size) {
/*     */         
/* 116 */         Node $$7 = null;
/* 117 */         float $$8 = Float.POSITIVE_INFINITY;
/*     */       } else {
/* 119 */         $$9 = this.heap[$$4];
/* 120 */         $$10 = $$9.f;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 125 */       if ($$6 < $$10) {
/* 126 */         if ($$6 < $$2) {
/* 127 */           this.heap[$$0] = $$5;
/* 128 */           $$5.heapIdx = $$0;
/* 129 */           $$0 = $$3;
/*     */           continue;
/*     */         } 
/*     */         break;
/*     */       } 
/* 134 */       if ($$10 < $$2) {
/* 135 */         this.heap[$$0] = $$9;
/* 136 */         $$9.heapIdx = $$0;
/* 137 */         $$0 = $$4;
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/*     */       break;
/*     */     } 
/* 144 */     this.heap[$$0] = $$1;
/* 145 */     $$1.heapIdx = $$0;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 149 */     return (this.size == 0);
/*     */   }
/*     */   
/*     */   public Node[] getHeap() {
/* 153 */     return Arrays.<Node>copyOf(this.heap, this.size);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\pathfinder\BinaryHeap.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */