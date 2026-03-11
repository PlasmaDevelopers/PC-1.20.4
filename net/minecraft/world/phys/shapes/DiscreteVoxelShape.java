/*     */ package net.minecraft.world.phys.shapes;
/*     */ 
/*     */ import net.minecraft.core.AxisCycle;
/*     */ import net.minecraft.core.Direction;
/*     */ 
/*     */ public abstract class DiscreteVoxelShape {
/*   7 */   private static final Direction.Axis[] AXIS_VALUES = Direction.Axis.values();
/*     */   
/*     */   protected final int xSize;
/*     */   protected final int ySize;
/*     */   protected final int zSize;
/*     */   
/*     */   protected DiscreteVoxelShape(int $$0, int $$1, int $$2) {
/*  14 */     if ($$0 < 0 || $$1 < 0 || $$2 < 0) {
/*  15 */       throw new IllegalArgumentException("Need all positive sizes: x: " + $$0 + ", y: " + $$1 + ", z: " + $$2);
/*     */     }
/*  17 */     this.xSize = $$0;
/*  18 */     this.ySize = $$1;
/*  19 */     this.zSize = $$2;
/*     */   }
/*     */   
/*     */   public boolean isFullWide(AxisCycle $$0, int $$1, int $$2, int $$3) {
/*  23 */     return isFullWide($$0
/*  24 */         .cycle($$1, $$2, $$3, Direction.Axis.X), $$0
/*  25 */         .cycle($$1, $$2, $$3, Direction.Axis.Y), $$0
/*  26 */         .cycle($$1, $$2, $$3, Direction.Axis.Z));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullWide(int $$0, int $$1, int $$2) {
/*  31 */     if ($$0 < 0 || $$1 < 0 || $$2 < 0) {
/*  32 */       return false;
/*     */     }
/*  34 */     if ($$0 >= this.xSize || $$1 >= this.ySize || $$2 >= this.zSize) {
/*  35 */       return false;
/*     */     }
/*  37 */     return isFull($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   public boolean isFull(AxisCycle $$0, int $$1, int $$2, int $$3) {
/*  41 */     return isFull($$0
/*  42 */         .cycle($$1, $$2, $$3, Direction.Axis.X), $$0
/*  43 */         .cycle($$1, $$2, $$3, Direction.Axis.Y), $$0
/*  44 */         .cycle($$1, $$2, $$3, Direction.Axis.Z));
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract boolean isFull(int paramInt1, int paramInt2, int paramInt3);
/*     */   
/*     */   public abstract void fill(int paramInt1, int paramInt2, int paramInt3);
/*     */   
/*     */   public boolean isEmpty() {
/*  53 */     for (Direction.Axis $$0 : AXIS_VALUES) {
/*  54 */       if (firstFull($$0) >= lastFull($$0)) {
/*  55 */         return true;
/*     */       }
/*     */     } 
/*  58 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract int firstFull(Direction.Axis paramAxis);
/*     */   
/*     */   public abstract int lastFull(Direction.Axis paramAxis);
/*     */   
/*     */   public int firstFull(Direction.Axis $$0, int $$1, int $$2) {
/*  67 */     int $$3 = getSize($$0);
/*  68 */     if ($$1 < 0 || $$2 < 0) {
/*  69 */       return $$3;
/*     */     }
/*  71 */     Direction.Axis $$4 = AxisCycle.FORWARD.cycle($$0);
/*  72 */     Direction.Axis $$5 = AxisCycle.BACKWARD.cycle($$0);
/*  73 */     if ($$1 >= getSize($$4) || $$2 >= getSize($$5)) {
/*  74 */       return $$3;
/*     */     }
/*  76 */     AxisCycle $$6 = AxisCycle.between(Direction.Axis.X, $$0);
/*  77 */     for (int $$7 = 0; $$7 < $$3; $$7++) {
/*  78 */       if (isFull($$6, $$7, $$1, $$2)) {
/*  79 */         return $$7;
/*     */       }
/*     */     } 
/*  82 */     return $$3;
/*     */   }
/*     */   
/*     */   public int lastFull(Direction.Axis $$0, int $$1, int $$2) {
/*  86 */     if ($$1 < 0 || $$2 < 0) {
/*  87 */       return 0;
/*     */     }
/*  89 */     Direction.Axis $$3 = AxisCycle.FORWARD.cycle($$0);
/*  90 */     Direction.Axis $$4 = AxisCycle.BACKWARD.cycle($$0);
/*  91 */     if ($$1 >= getSize($$3) || $$2 >= getSize($$4)) {
/*  92 */       return 0;
/*     */     }
/*  94 */     int $$5 = getSize($$0);
/*  95 */     AxisCycle $$6 = AxisCycle.between(Direction.Axis.X, $$0);
/*  96 */     for (int $$7 = $$5 - 1; $$7 >= 0; $$7--) {
/*  97 */       if (isFull($$6, $$7, $$1, $$2)) {
/*  98 */         return $$7 + 1;
/*     */       }
/*     */     } 
/* 101 */     return 0;
/*     */   }
/*     */   
/*     */   public int getSize(Direction.Axis $$0) {
/* 105 */     return $$0.choose(this.xSize, this.ySize, this.zSize);
/*     */   }
/*     */   
/*     */   public int getXSize() {
/* 109 */     return getSize(Direction.Axis.X);
/*     */   }
/*     */   
/*     */   public int getYSize() {
/* 113 */     return getSize(Direction.Axis.Y);
/*     */   }
/*     */   
/*     */   public int getZSize() {
/* 117 */     return getSize(Direction.Axis.Z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void forAllEdges(IntLineConsumer $$0, boolean $$1) {
/* 129 */     forAllAxisEdges($$0, AxisCycle.NONE, $$1);
/* 130 */     forAllAxisEdges($$0, AxisCycle.FORWARD, $$1);
/* 131 */     forAllAxisEdges($$0, AxisCycle.BACKWARD, $$1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void forAllAxisEdges(IntLineConsumer $$0, AxisCycle $$1, boolean $$2) {
/* 139 */     AxisCycle $$3 = $$1.inverse();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 144 */     int $$4 = getSize($$3.cycle(Direction.Axis.X));
/* 145 */     int $$5 = getSize($$3.cycle(Direction.Axis.Y));
/* 146 */     int $$6 = getSize($$3.cycle(Direction.Axis.Z));
/*     */     
/* 148 */     for (int $$7 = 0; $$7 <= $$4; $$7++) {
/* 149 */       for (int $$8 = 0; $$8 <= $$5; $$8++) {
/* 150 */         int $$9 = -1;
/* 151 */         for (int $$10 = 0; $$10 <= $$6; $$10++) {
/* 152 */           int $$11 = 0;
/*     */           
/* 154 */           int $$12 = 0;
/* 155 */           for (int $$13 = 0; $$13 <= 1; $$13++) {
/* 156 */             for (int $$14 = 0; $$14 <= 1; $$14++) {
/* 157 */               if (isFullWide($$3, $$7 + $$13 - 1, $$8 + $$14 - 1, $$10)) {
/* 158 */                 $$11++;
/* 159 */                 $$12 ^= $$13 ^ $$14;
/*     */               } 
/*     */             } 
/*     */           } 
/* 163 */           if ($$11 == 1 || $$11 == 3 || ($$11 == 2 && ($$12 & 0x1) == 0)) {
/* 164 */             if ($$2) {
/*     */               
/* 166 */               if ($$9 == -1) {
/* 167 */                 $$9 = $$10;
/*     */               }
/*     */             } else {
/* 170 */               $$0.consume($$3
/* 171 */                   .cycle($$7, $$8, $$10, Direction.Axis.X), $$3
/* 172 */                   .cycle($$7, $$8, $$10, Direction.Axis.Y), $$3
/* 173 */                   .cycle($$7, $$8, $$10, Direction.Axis.Z), $$3
/* 174 */                   .cycle($$7, $$8, $$10 + 1, Direction.Axis.X), $$3
/* 175 */                   .cycle($$7, $$8, $$10 + 1, Direction.Axis.Y), $$3
/* 176 */                   .cycle($$7, $$8, $$10 + 1, Direction.Axis.Z));
/*     */             }
/*     */           
/* 179 */           } else if ($$9 != -1) {
/*     */             
/* 181 */             $$0.consume($$3
/* 182 */                 .cycle($$7, $$8, $$9, Direction.Axis.X), $$3
/* 183 */                 .cycle($$7, $$8, $$9, Direction.Axis.Y), $$3
/* 184 */                 .cycle($$7, $$8, $$9, Direction.Axis.Z), $$3
/* 185 */                 .cycle($$7, $$8, $$10, Direction.Axis.X), $$3
/* 186 */                 .cycle($$7, $$8, $$10, Direction.Axis.Y), $$3
/* 187 */                 .cycle($$7, $$8, $$10, Direction.Axis.Z));
/*     */             
/* 189 */             $$9 = -1;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void forAllBoxes(IntLineConsumer $$0, boolean $$1) {
/* 197 */     BitSetDiscreteVoxelShape.forAllBoxes(this, $$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void forAllFaces(IntFaceConsumer $$0) {
/* 202 */     forAllAxisFaces($$0, AxisCycle.NONE);
/* 203 */     forAllAxisFaces($$0, AxisCycle.FORWARD);
/* 204 */     forAllAxisFaces($$0, AxisCycle.BACKWARD);
/*     */   }
/*     */   
/*     */   private void forAllAxisFaces(IntFaceConsumer $$0, AxisCycle $$1) {
/* 208 */     AxisCycle $$2 = $$1.inverse();
/*     */     
/* 210 */     Direction.Axis $$3 = $$2.cycle(Direction.Axis.Z);
/*     */     
/* 212 */     int $$4 = getSize($$2.cycle(Direction.Axis.X));
/* 213 */     int $$5 = getSize($$2.cycle(Direction.Axis.Y));
/* 214 */     int $$6 = getSize($$3);
/*     */     
/* 216 */     Direction $$7 = Direction.fromAxisAndDirection($$3, Direction.AxisDirection.NEGATIVE);
/* 217 */     Direction $$8 = Direction.fromAxisAndDirection($$3, Direction.AxisDirection.POSITIVE);
/*     */     
/* 219 */     for (int $$9 = 0; $$9 < $$4; $$9++) {
/* 220 */       for (int $$10 = 0; $$10 < $$5; $$10++) {
/* 221 */         boolean $$11 = false;
/* 222 */         for (int $$12 = 0; $$12 <= $$6; $$12++) {
/* 223 */           boolean $$13 = ($$12 != $$6 && isFull($$2, $$9, $$10, $$12));
/* 224 */           if (!$$11 && $$13) {
/* 225 */             $$0.consume($$7, $$2
/*     */                 
/* 227 */                 .cycle($$9, $$10, $$12, Direction.Axis.X), $$2
/* 228 */                 .cycle($$9, $$10, $$12, Direction.Axis.Y), $$2
/* 229 */                 .cycle($$9, $$10, $$12, Direction.Axis.Z));
/*     */           }
/*     */           
/* 232 */           if ($$11 && !$$13) {
/* 233 */             $$0.consume($$8, $$2
/*     */                 
/* 235 */                 .cycle($$9, $$10, $$12 - 1, Direction.Axis.X), $$2
/* 236 */                 .cycle($$9, $$10, $$12 - 1, Direction.Axis.Y), $$2
/* 237 */                 .cycle($$9, $$10, $$12 - 1, Direction.Axis.Z));
/*     */           }
/*     */           
/* 240 */           $$11 = $$13;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static interface IntLineConsumer {
/*     */     void consume(int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5, int param1Int6);
/*     */   }
/*     */   
/*     */   public static interface IntFaceConsumer {
/*     */     void consume(Direction param1Direction, int param1Int1, int param1Int2, int param1Int3);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\phys\shapes\DiscreteVoxelShape.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */