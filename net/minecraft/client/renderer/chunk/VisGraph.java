/*     */ package net.minecraft.client.renderer.chunk;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.ints.IntArrayFIFOQueue;
/*     */ import java.util.BitSet;
/*     */ import java.util.EnumSet;
/*     */ import java.util.Set;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ 
/*     */ 
/*     */ public class VisGraph
/*     */ {
/*     */   private static final int SIZE_IN_BITS = 4;
/*     */   private static final int LEN = 16;
/*     */   private static final int MASK = 15;
/*     */   private static final int SIZE = 4096;
/*     */   private static final int X_SHIFT = 0;
/*     */   private static final int Z_SHIFT = 4;
/*     */   private static final int Y_SHIFT = 8;
/*  21 */   private static final int DX = (int)Math.pow(16.0D, 0.0D);
/*  22 */   private static final int DZ = (int)Math.pow(16.0D, 1.0D);
/*  23 */   private static final int DY = (int)Math.pow(16.0D, 2.0D);
/*     */   private static final int INVALID_INDEX = -1;
/*  25 */   private static final Direction[] DIRECTIONS = Direction.values();
/*     */   
/*  27 */   private final BitSet bitSet = new BitSet(4096);
/*     */   static {
/*  29 */     INDEX_OF_EDGES = (int[])Util.make(new int[1352], $$0 -> {
/*     */           int $$1 = 0;
/*     */           int $$2 = 15;
/*     */           int $$3 = 0;
/*     */           for (int $$4 = 0; $$4 < 16; $$4++) {
/*     */             for (int $$5 = 0; $$5 < 16; $$5++) {
/*     */               for (int $$6 = 0; $$6 < 16; $$6++) {
/*     */                 if ($$4 == 0 || $$4 == 15 || $$5 == 0 || $$5 == 15 || $$6 == 0 || $$6 == 15)
/*     */                   $$0[$$3++] = getIndex($$4, $$5, $$6); 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         });
/*     */   }
/*     */   
/*     */   private static final int[] INDEX_OF_EDGES;
/*  45 */   private int empty = 4096;
/*     */   
/*     */   public void setOpaque(BlockPos $$0) {
/*  48 */     this.bitSet.set(getIndex($$0), true);
/*  49 */     this.empty--;
/*     */   }
/*     */   
/*     */   private static int getIndex(BlockPos $$0) {
/*  53 */     return getIndex($$0.getX() & 0xF, $$0.getY() & 0xF, $$0.getZ() & 0xF);
/*     */   }
/*     */   
/*     */   private static int getIndex(int $$0, int $$1, int $$2) {
/*  57 */     return $$0 << 0 | $$1 << 8 | $$2 << 4;
/*     */   }
/*     */   
/*     */   public VisibilitySet resolve() {
/*  61 */     VisibilitySet $$0 = new VisibilitySet();
/*     */     
/*  63 */     if (4096 - this.empty < 256) {
/*  64 */       $$0.setAll(true);
/*  65 */     } else if (this.empty == 0) {
/*  66 */       $$0.setAll(false);
/*     */     } else {
/*  68 */       for (int $$1 : INDEX_OF_EDGES) {
/*  69 */         if (!this.bitSet.get($$1)) {
/*  70 */           $$0.add(floodFill($$1));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  75 */     return $$0;
/*     */   }
/*     */   
/*     */   private Set<Direction> floodFill(int $$0) {
/*  79 */     Set<Direction> $$1 = EnumSet.noneOf(Direction.class);
/*     */     
/*  81 */     IntArrayFIFOQueue intArrayFIFOQueue = new IntArrayFIFOQueue();
/*  82 */     intArrayFIFOQueue.enqueue($$0);
/*  83 */     this.bitSet.set($$0, true);
/*     */     
/*  85 */     while (!intArrayFIFOQueue.isEmpty()) {
/*  86 */       int $$3 = intArrayFIFOQueue.dequeueInt();
/*  87 */       addEdges($$3, $$1);
/*     */       
/*  89 */       for (Direction $$4 : DIRECTIONS) {
/*  90 */         int $$5 = getNeighborIndexAtFace($$3, $$4);
/*  91 */         if ($$5 >= 0 && !this.bitSet.get($$5)) {
/*  92 */           this.bitSet.set($$5, true);
/*  93 */           intArrayFIFOQueue.enqueue($$5);
/*     */         } 
/*     */       } 
/*     */     } 
/*  97 */     return $$1;
/*     */   }
/*     */   
/*     */   private void addEdges(int $$0, Set<Direction> $$1) {
/* 101 */     int $$2 = $$0 >> 0 & 0xF;
/* 102 */     if ($$2 == 0) {
/* 103 */       $$1.add(Direction.WEST);
/* 104 */     } else if ($$2 == 15) {
/* 105 */       $$1.add(Direction.EAST);
/*     */     } 
/*     */     
/* 108 */     int $$3 = $$0 >> 8 & 0xF;
/* 109 */     if ($$3 == 0) {
/* 110 */       $$1.add(Direction.DOWN);
/* 111 */     } else if ($$3 == 15) {
/* 112 */       $$1.add(Direction.UP);
/*     */     } 
/*     */     
/* 115 */     int $$4 = $$0 >> 4 & 0xF;
/* 116 */     if ($$4 == 0) {
/* 117 */       $$1.add(Direction.NORTH);
/* 118 */     } else if ($$4 == 15) {
/* 119 */       $$1.add(Direction.SOUTH);
/*     */     } 
/*     */   }
/*     */   
/*     */   private int getNeighborIndexAtFace(int $$0, Direction $$1) {
/* 124 */     switch ($$1) {
/*     */       
/*     */       case DOWN:
/* 127 */         if (($$0 >> 8 & 0xF) == 0) {
/* 128 */           return -1;
/*     */         }
/* 130 */         return $$0 - DY;
/*     */       
/*     */       case UP:
/* 133 */         if (($$0 >> 8 & 0xF) == 15) {
/* 134 */           return -1;
/*     */         }
/* 136 */         return $$0 + DY;
/*     */       
/*     */       case NORTH:
/* 139 */         if (($$0 >> 4 & 0xF) == 0) {
/* 140 */           return -1;
/*     */         }
/* 142 */         return $$0 - DZ;
/*     */       
/*     */       case SOUTH:
/* 145 */         if (($$0 >> 4 & 0xF) == 15) {
/* 146 */           return -1;
/*     */         }
/* 148 */         return $$0 + DZ;
/*     */       
/*     */       case WEST:
/* 151 */         if (($$0 >> 0 & 0xF) == 0) {
/* 152 */           return -1;
/*     */         }
/* 154 */         return $$0 - DX;
/*     */       
/*     */       case EAST:
/* 157 */         if (($$0 >> 0 & 0xF) == 15) {
/* 158 */           return -1;
/*     */         }
/* 160 */         return $$0 + DX;
/*     */     } 
/* 162 */     return -1;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\chunk\VisGraph.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */