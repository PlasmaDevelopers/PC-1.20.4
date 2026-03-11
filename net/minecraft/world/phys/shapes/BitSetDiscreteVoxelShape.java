/*     */ package net.minecraft.world.phys.shapes;
/*     */ 
/*     */ import java.util.BitSet;
/*     */ import net.minecraft.core.Direction;
/*     */ 
/*     */ public final class BitSetDiscreteVoxelShape
/*     */   extends DiscreteVoxelShape {
/*     */   private final BitSet storage;
/*     */   private int xMin;
/*     */   private int yMin;
/*     */   private int zMin;
/*     */   private int xMax;
/*     */   private int yMax;
/*     */   private int zMax;
/*     */   
/*     */   public BitSetDiscreteVoxelShape(int $$0, int $$1, int $$2) {
/*  17 */     super($$0, $$1, $$2);
/*  18 */     this.storage = new BitSet($$0 * $$1 * $$2);
/*  19 */     this.xMin = $$0;
/*  20 */     this.yMin = $$1;
/*  21 */     this.zMin = $$2;
/*     */   }
/*     */   
/*     */   public static BitSetDiscreteVoxelShape withFilledBounds(int $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, int $$8) {
/*  25 */     BitSetDiscreteVoxelShape $$9 = new BitSetDiscreteVoxelShape($$0, $$1, $$2);
/*     */     
/*  27 */     $$9.xMin = $$3;
/*  28 */     $$9.yMin = $$4;
/*  29 */     $$9.zMin = $$5;
/*  30 */     $$9.xMax = $$6;
/*  31 */     $$9.yMax = $$7;
/*  32 */     $$9.zMax = $$8;
/*     */     
/*  34 */     for (int $$10 = $$3; $$10 < $$6; $$10++) {
/*  35 */       for (int $$11 = $$4; $$11 < $$7; $$11++) {
/*  36 */         for (int $$12 = $$5; $$12 < $$8; $$12++) {
/*  37 */           $$9.fillUpdateBounds($$10, $$11, $$12, false);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  42 */     return $$9;
/*     */   }
/*     */   
/*     */   public BitSetDiscreteVoxelShape(DiscreteVoxelShape $$0) {
/*  46 */     super($$0.xSize, $$0.ySize, $$0.zSize);
/*  47 */     if ($$0 instanceof BitSetDiscreteVoxelShape) {
/*  48 */       this.storage = (BitSet)((BitSetDiscreteVoxelShape)$$0).storage.clone();
/*     */     } else {
/*  50 */       this.storage = new BitSet(this.xSize * this.ySize * this.zSize);
/*  51 */       for (int $$1 = 0; $$1 < this.xSize; $$1++) {
/*  52 */         for (int $$2 = 0; $$2 < this.ySize; $$2++) {
/*  53 */           for (int $$3 = 0; $$3 < this.zSize; $$3++) {
/*  54 */             if ($$0.isFull($$1, $$2, $$3)) {
/*  55 */               this.storage.set(getIndex($$1, $$2, $$3));
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  62 */     this.xMin = $$0.firstFull(Direction.Axis.X);
/*  63 */     this.yMin = $$0.firstFull(Direction.Axis.Y);
/*  64 */     this.zMin = $$0.firstFull(Direction.Axis.Z);
/*     */     
/*  66 */     this.xMax = $$0.lastFull(Direction.Axis.X);
/*  67 */     this.yMax = $$0.lastFull(Direction.Axis.Y);
/*  68 */     this.zMax = $$0.lastFull(Direction.Axis.Z);
/*     */   }
/*     */   
/*     */   protected int getIndex(int $$0, int $$1, int $$2) {
/*  72 */     return ($$0 * this.ySize + $$1) * this.zSize + $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFull(int $$0, int $$1, int $$2) {
/*  77 */     return this.storage.get(getIndex($$0, $$1, $$2));
/*     */   }
/*     */   
/*     */   private void fillUpdateBounds(int $$0, int $$1, int $$2, boolean $$3) {
/*  81 */     this.storage.set(getIndex($$0, $$1, $$2));
/*     */     
/*  83 */     if ($$3) {
/*  84 */       this.xMin = Math.min(this.xMin, $$0);
/*  85 */       this.yMin = Math.min(this.yMin, $$1);
/*  86 */       this.zMin = Math.min(this.zMin, $$2);
/*     */       
/*  88 */       this.xMax = Math.max(this.xMax, $$0 + 1);
/*  89 */       this.yMax = Math.max(this.yMax, $$1 + 1);
/*  90 */       this.zMax = Math.max(this.zMax, $$2 + 1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void fill(int $$0, int $$1, int $$2) {
/*  96 */     fillUpdateBounds($$0, $$1, $$2, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 101 */     return this.storage.isEmpty();
/*     */   }
/*     */ 
/*     */   
/*     */   public int firstFull(Direction.Axis $$0) {
/* 106 */     return $$0.choose(this.xMin, this.yMin, this.zMin);
/*     */   }
/*     */ 
/*     */   
/*     */   public int lastFull(Direction.Axis $$0) {
/* 111 */     return $$0.choose(this.xMax, this.yMax, this.zMax);
/*     */   }
/*     */   
/*     */   static BitSetDiscreteVoxelShape join(DiscreteVoxelShape $$0, DiscreteVoxelShape $$1, IndexMerger $$2, IndexMerger $$3, IndexMerger $$4, BooleanOp $$5) {
/* 115 */     BitSetDiscreteVoxelShape $$6 = new BitSetDiscreteVoxelShape($$2.size() - 1, $$3.size() - 1, $$4.size() - 1);
/* 116 */     int[] $$7 = { Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 125 */     $$2.forMergedIndexes(($$7, $$8, $$9) -> {
/*     */           boolean[] $$10 = { false };
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           $$0.forMergedIndexes(());
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           if ($$10[0]) {
/*     */             $$6[0] = Math.min($$6[0], $$9);
/*     */ 
/*     */ 
/*     */             
/*     */             $$6[3] = Math.max($$6[3], $$9);
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/*     */           return true;
/*     */         });
/*     */ 
/*     */ 
/*     */     
/* 151 */     $$6.xMin = $$7[0];
/* 152 */     $$6.yMin = $$7[1];
/* 153 */     $$6.zMin = $$7[2];
/* 154 */     $$6.xMax = $$7[3] + 1;
/* 155 */     $$6.yMax = $$7[4] + 1;
/* 156 */     $$6.zMax = $$7[5] + 1;
/* 157 */     return $$6;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static void forAllBoxes(DiscreteVoxelShape $$0, DiscreteVoxelShape.IntLineConsumer $$1, boolean $$2) {
/* 165 */     BitSetDiscreteVoxelShape $$3 = new BitSetDiscreteVoxelShape($$0);
/* 166 */     for (int $$4 = 0; $$4 < $$3.ySize; $$4++) {
/* 167 */       for (int $$5 = 0; $$5 < $$3.xSize; $$5++) {
/* 168 */         int $$6 = -1;
/* 169 */         for (int $$7 = 0; $$7 <= $$3.zSize; $$7++) {
/* 170 */           if ($$3.isFullWide($$5, $$4, $$7)) {
/* 171 */             if ($$2) {
/*     */               
/* 173 */               if ($$6 == -1) {
/* 174 */                 $$6 = $$7;
/*     */               }
/*     */             } else {
/* 177 */               $$1.consume($$5, $$4, $$7, $$5 + 1, $$4 + 1, $$7 + 1);
/*     */             } 
/* 179 */           } else if ($$6 != -1) {
/*     */ 
/*     */             
/* 182 */             int $$8 = $$5;
/* 183 */             int $$9 = $$4;
/*     */ 
/*     */             
/* 186 */             $$3.clearZStrip($$6, $$7, $$5, $$4);
/*     */ 
/*     */             
/* 189 */             while ($$3.isZStripFull($$6, $$7, $$8 + 1, $$4)) {
/* 190 */               $$3.clearZStrip($$6, $$7, $$8 + 1, $$4);
/* 191 */               $$8++;
/*     */             } 
/*     */ 
/*     */             
/* 195 */             while ($$3.isXZRectangleFull($$5, $$8 + 1, $$6, $$7, $$9 + 1)) {
/* 196 */               for (int $$10 = $$5; $$10 <= $$8; $$10++) {
/* 197 */                 $$3.clearZStrip($$6, $$7, $$10, $$9 + 1);
/*     */               }
/* 199 */               $$9++;
/*     */             } 
/*     */             
/* 202 */             $$1.consume($$5, $$4, $$6, $$8 + 1, $$9 + 1, $$7);
/* 203 */             $$6 = -1;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isZStripFull(int $$0, int $$1, int $$2, int $$3) {
/* 212 */     if ($$2 >= this.xSize || $$3 >= this.ySize) {
/* 213 */       return false;
/*     */     }
/* 215 */     return (this.storage.nextClearBit(getIndex($$2, $$3, $$0)) >= getIndex($$2, $$3, $$1));
/*     */   }
/*     */   
/*     */   private boolean isXZRectangleFull(int $$0, int $$1, int $$2, int $$3, int $$4) {
/* 219 */     for (int $$5 = $$0; $$5 < $$1; $$5++) {
/* 220 */       if (!isZStripFull($$2, $$3, $$5, $$4)) {
/* 221 */         return false;
/*     */       }
/*     */     } 
/* 224 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private void clearZStrip(int $$0, int $$1, int $$2, int $$3) {
/* 229 */     this.storage.clear(getIndex($$2, $$3, $$0), getIndex($$2, $$3, $$1));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\phys\shapes\BitSetDiscreteVoxelShape.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */