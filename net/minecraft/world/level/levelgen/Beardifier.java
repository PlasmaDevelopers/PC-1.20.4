/*     */ package net.minecraft.world.level.levelgen;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectList;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectListIterator;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.levelgen.structure.BoundingBox;
/*     */ import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
/*     */ import net.minecraft.world.level.levelgen.structure.StructurePiece;
/*     */ import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
/*     */ import net.minecraft.world.level.levelgen.structure.pools.JigsawJunction;
/*     */ import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
/*     */ 
/*     */ public class Beardifier implements DensityFunctions.BeardifierOrMarker {
/*     */   public static final int BEARD_KERNEL_RADIUS = 12;
/*     */   private static final int BEARD_KERNEL_SIZE = 24;
/*     */   private static final float[] BEARD_KERNEL;
/*     */   private final ObjectListIterator<Rigid> pieceIterator;
/*     */   private final ObjectListIterator<JigsawJunction> junctionIterator;
/*     */   
/*     */   static {
/*  22 */     BEARD_KERNEL = (float[])Util.make(new float[13824], $$0 -> {
/*     */           for (int $$1 = 0; $$1 < 24; $$1++) {
/*     */             for (int $$2 = 0; $$2 < 24; $$2++) {
/*     */               for (int $$3 = 0; $$3 < 24; $$3++)
/*     */                 $$0[$$1 * 24 * 24 + $$2 * 24 + $$3] = (float)computeBeardContribution($$2 - 12, $$3 - 12, $$1 - 12); 
/*     */             } 
/*     */           } 
/*     */         });
/*     */   } @VisibleForTesting
/*     */   public static final class Rigid extends Record { private final BoundingBox box; private final TerrainAdjustment terrainAdjustment; private final int groundLevelDelta;
/*  32 */     public int groundLevelDelta() { return this.groundLevelDelta; } public TerrainAdjustment terrainAdjustment() { return this.terrainAdjustment; } public BoundingBox box() { return this.box; }
/*     */     public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/Beardifier$Rigid;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #32	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/world/level/levelgen/Beardifier$Rigid;
/*  33 */       //   0	8	1	$$0	Ljava/lang/Object; } public Rigid(BoundingBox $$0, TerrainAdjustment $$1, int $$2) { this.box = $$0; this.terrainAdjustment = $$1; this.groundLevelDelta = $$2; }
/*     */      public final int hashCode() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/Beardifier$Rigid;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #32	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/Beardifier$Rigid;
/*     */     } public final String toString() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/Beardifier$Rigid;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #32	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/Beardifier$Rigid;
/*     */     } } public static Beardifier forStructuresInChunk(StructureManager $$0, ChunkPos $$1) {
/*  39 */     int $$2 = $$1.getMinBlockX();
/*  40 */     int $$3 = $$1.getMinBlockZ();
/*     */     
/*  42 */     ObjectArrayList objectArrayList1 = new ObjectArrayList(10);
/*  43 */     ObjectArrayList objectArrayList2 = new ObjectArrayList(32);
/*     */ 
/*     */     
/*  46 */     $$0.startsForStructure($$1, $$0 -> ($$0.terrainAdaptation() != TerrainAdjustment.NONE)).forEach($$5 -> {
/*     */           TerrainAdjustment $$6 = $$5.getStructure().terrainAdaptation();
/*     */           
/*     */           for (StructurePiece $$7 : $$5.getPieces()) {
/*     */             if (!$$7.isCloseToChunk($$0, 12)) {
/*     */               continue;
/*     */             }
/*     */             
/*     */             if ($$7 instanceof PoolElementStructurePiece) {
/*     */               PoolElementStructurePiece $$8 = (PoolElementStructurePiece)$$7;
/*     */               
/*     */               StructureTemplatePool.Projection $$9 = $$8.getElement().getProjection();
/*     */               
/*     */               if ($$9 == StructureTemplatePool.Projection.RIGID) {
/*     */                 $$1.add(new Rigid($$8.getBoundingBox(), $$6, $$8.getGroundLevelDelta()));
/*     */               }
/*     */               
/*     */               for (JigsawJunction $$10 : $$8.getJunctions()) {
/*     */                 int $$11 = $$10.getSourceX();
/*     */                 
/*     */                 int $$12 = $$10.getSourceZ();
/*     */                 if ($$11 <= $$2 - 12 || $$12 <= $$3 - 12 || $$11 >= $$2 + 15 + 12 || $$12 >= $$3 + 15 + 12) {
/*     */                   continue;
/*     */                 }
/*     */                 $$4.add($$10);
/*     */               } 
/*     */               continue;
/*     */             } 
/*     */             $$1.add(new Rigid($$7.getBoundingBox(), $$6, 0));
/*     */           } 
/*     */         });
/*  77 */     return new Beardifier(objectArrayList1.iterator(), objectArrayList2.iterator());
/*     */   }
/*     */   
/*     */   @VisibleForTesting
/*     */   public Beardifier(ObjectListIterator<Rigid> $$0, ObjectListIterator<JigsawJunction> $$1) {
/*  82 */     this.pieceIterator = $$0;
/*  83 */     this.junctionIterator = $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public double compute(DensityFunction.FunctionContext $$0) {
/*  88 */     int $$1 = $$0.blockX();
/*  89 */     int $$2 = $$0.blockY();
/*  90 */     int $$3 = $$0.blockZ();
/*     */     
/*  92 */     double $$4 = 0.0D;
/*     */     
/*  94 */     while (this.pieceIterator.hasNext()) {
/*  95 */       Rigid $$5 = (Rigid)this.pieceIterator.next();
/*  96 */       BoundingBox $$6 = $$5.box();
/*  97 */       int $$7 = $$5.groundLevelDelta();
/*     */ 
/*     */ 
/*     */       
/* 101 */       int $$8 = Math.max(0, Math.max($$6.minX() - $$1, $$1 - $$6.maxX()));
/* 102 */       int $$9 = Math.max(0, Math.max($$6.minZ() - $$3, $$3 - $$6.maxZ()));
/*     */       
/* 104 */       int $$10 = $$6.minY() + $$7;
/* 105 */       int $$11 = $$2 - $$10;
/* 106 */       switch ($$5.terrainAdjustment()) { default: throw new IncompatibleClassChangeError();
/*     */         case NONE: 
/*     */         case BURY: case BEARD_THIN: 
/* 109 */         case BEARD_BOX: break; }  int $$12 = Math.max(0, Math.max($$10 - $$2, $$2 - $$6.maxY()));
/*     */ 
/*     */       
/* 112 */       switch ($$5.terrainAdjustment()) { default: throw new IncompatibleClassChangeError();case NONE: case BURY: case BEARD_THIN: case BEARD_BOX: break; }  $$4 += 
/*     */ 
/*     */         
/* 115 */         getBeardContribution($$8, $$12, $$9, $$11) * 0.8D;
/*     */     } 
/*     */     
/* 118 */     this.pieceIterator.back(2147483647);
/*     */     
/* 120 */     while (this.junctionIterator.hasNext()) {
/* 121 */       JigsawJunction $$13 = (JigsawJunction)this.junctionIterator.next();
/* 122 */       int $$14 = $$1 - $$13.getSourceX();
/* 123 */       int $$15 = $$2 - $$13.getSourceGroundY();
/* 124 */       int $$16 = $$3 - $$13.getSourceZ();
/*     */       
/* 126 */       $$4 += getBeardContribution($$14, $$15, $$16, $$15) * 0.4D;
/*     */     } 
/* 128 */     this.junctionIterator.back(2147483647);
/*     */     
/* 130 */     return $$4;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double minValue() {
/* 136 */     return Double.NEGATIVE_INFINITY;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double maxValue() {
/* 142 */     return Double.POSITIVE_INFINITY;
/*     */   }
/*     */ 
/*     */   
/*     */   private static double getBuryContribution(int $$0, int $$1, int $$2) {
/* 147 */     double $$3 = Mth.length($$0, $$1 / 2.0D, $$2);
/* 148 */     return Mth.clampedMap($$3, 0.0D, 6.0D, 1.0D, 0.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static double getBeardContribution(int $$0, int $$1, int $$2, int $$3) {
/* 158 */     int $$4 = $$0 + 12;
/* 159 */     int $$5 = $$1 + 12;
/* 160 */     int $$6 = $$2 + 12;
/* 161 */     if (!isInKernelRange($$4) || !isInKernelRange($$5) || !isInKernelRange($$6)) {
/* 162 */       return 0.0D;
/*     */     }
/*     */     
/* 165 */     double $$7 = $$3 + 0.5D;
/* 166 */     double $$8 = Mth.lengthSquared($$0, $$7, $$2);
/* 167 */     double $$9 = -$$7 * Mth.fastInvSqrt($$8 / 2.0D) / 2.0D;
/* 168 */     return $$9 * BEARD_KERNEL[$$6 * 24 * 24 + $$4 * 24 + $$5];
/*     */   }
/*     */   
/*     */   private static boolean isInKernelRange(int $$0) {
/* 172 */     return ($$0 >= 0 && $$0 < 24);
/*     */   }
/*     */ 
/*     */   
/*     */   private static double computeBeardContribution(int $$0, int $$1, int $$2) {
/* 177 */     return computeBeardContribution($$0, $$1 + 0.5D, $$2);
/*     */   }
/*     */   
/*     */   private static double computeBeardContribution(int $$0, double $$1, int $$2) {
/* 181 */     double $$3 = Mth.lengthSquared($$0, $$1, $$2);
/* 182 */     double $$4 = Math.pow(Math.E, -$$3 / 16.0D);
/*     */     
/* 184 */     return $$4;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\Beardifier.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */