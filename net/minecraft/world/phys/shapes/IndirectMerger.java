/*    */ package net.minecraft.world.phys.shapes;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
/*    */ import it.unimi.dsi.fastutil.doubles.DoubleList;
/*    */ import it.unimi.dsi.fastutil.doubles.DoubleLists;
/*    */ 
/*    */ public class IndirectMerger implements IndexMerger {
/*  8 */   private static final DoubleList EMPTY = DoubleLists.unmodifiable((DoubleList)DoubleArrayList.wrap(new double[] { 0.0D }));
/*    */   
/*    */   private final double[] result;
/*    */   
/*    */   private final int[] firstIndices;
/*    */   
/*    */   private final int[] secondIndices;
/*    */   
/*    */   private final int resultLength;
/*    */ 
/*    */   
/*    */   public IndirectMerger(DoubleList $$0, DoubleList $$1, boolean $$2, boolean $$3) {
/* 20 */     double $$4 = Double.NaN;
/*    */     
/* 22 */     int $$5 = $$0.size();
/* 23 */     int $$6 = $$1.size();
/* 24 */     int $$7 = $$5 + $$6;
/* 25 */     this.result = new double[$$7];
/* 26 */     this.firstIndices = new int[$$7];
/* 27 */     this.secondIndices = new int[$$7];
/*    */     
/* 29 */     boolean $$8 = !$$2;
/* 30 */     boolean $$9 = !$$3;
/*    */     
/* 32 */     int $$10 = 0;
/* 33 */     int $$11 = 0;
/* 34 */     int $$12 = 0;
/*    */     
/*    */     while (true) {
/* 37 */       boolean $$13 = ($$11 >= $$5);
/* 38 */       boolean $$14 = ($$12 >= $$6);
/*    */       
/* 40 */       if ($$13 && $$14) {
/*    */         break;
/*    */       }
/* 43 */       boolean $$15 = (!$$13 && ($$14 || $$0.getDouble($$11) < $$1.getDouble($$12) + 1.0E-7D));
/*    */       
/* 45 */       if ($$15) {
/* 46 */         $$11++;
/* 47 */         if ($$8 && ($$12 == 0 || $$14)) {
/*    */           continue;
/*    */         }
/*    */       } else {
/* 51 */         $$12++;
/* 52 */         if ($$9 && ($$11 == 0 || $$13)) {
/*    */           continue;
/*    */         }
/*    */       } 
/*    */       
/* 57 */       int $$16 = $$11 - 1;
/* 58 */       int $$17 = $$12 - 1;
/*    */       
/* 60 */       double $$18 = $$15 ? $$0.getDouble($$16) : $$1.getDouble($$17);
/* 61 */       if ($$4 < $$18 - 1.0E-7D) {
/* 62 */         this.firstIndices[$$10] = $$16;
/* 63 */         this.secondIndices[$$10] = $$17;
/* 64 */         this.result[$$10] = $$18;
/* 65 */         $$10++;
/* 66 */         $$4 = $$18; continue;
/*    */       } 
/* 68 */       this.firstIndices[$$10 - 1] = $$16;
/* 69 */       this.secondIndices[$$10 - 1] = $$17;
/*    */     } 
/*    */ 
/*    */     
/* 73 */     this.resultLength = Math.max(1, $$10);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean forMergedIndexes(IndexMerger.IndexConsumer $$0) {
/* 78 */     int $$1 = this.resultLength - 1;
/* 79 */     for (int $$2 = 0; $$2 < $$1; $$2++) {
/* 80 */       if (!$$0.merge(this.firstIndices[$$2], this.secondIndices[$$2], $$2)) {
/* 81 */         return false;
/*    */       }
/*    */     } 
/* 84 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public int size() {
/* 89 */     return this.resultLength;
/*    */   }
/*    */ 
/*    */   
/*    */   public DoubleList getList() {
/* 94 */     return (this.resultLength <= 1) ? EMPTY : (DoubleList)DoubleArrayList.wrap(this.result, this.resultLength);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\phys\shapes\IndirectMerger.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */