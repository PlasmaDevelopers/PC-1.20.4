/*    */ package net.minecraft.world.phys.shapes;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.doubles.AbstractDoubleList;
/*    */ import it.unimi.dsi.fastutil.doubles.DoubleList;
/*    */ 
/*    */ public class NonOverlappingMerger extends AbstractDoubleList implements IndexMerger {
/*    */   private final DoubleList lower;
/*    */   private final DoubleList upper;
/*    */   private final boolean swap;
/*    */   
/*    */   protected NonOverlappingMerger(DoubleList $$0, DoubleList $$1, boolean $$2) {
/* 12 */     this.lower = $$0;
/* 13 */     this.upper = $$1;
/* 14 */     this.swap = $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   public int size() {
/* 19 */     return this.lower.size() + this.upper.size();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean forMergedIndexes(IndexMerger.IndexConsumer $$0) {
/* 24 */     if (this.swap) {
/* 25 */       return forNonSwappedIndexes(($$1, $$2, $$3) -> $$0.merge($$2, $$1, $$3));
/*    */     }
/* 27 */     return forNonSwappedIndexes($$0);
/*    */   }
/*    */   
/*    */   private boolean forNonSwappedIndexes(IndexMerger.IndexConsumer $$0) {
/* 31 */     int $$1 = this.lower.size();
/* 32 */     for (int $$2 = 0; $$2 < $$1; $$2++) {
/* 33 */       if (!$$0.merge($$2, -1, $$2)) {
/* 34 */         return false;
/*    */       }
/*    */     } 
/*    */     
/* 38 */     int $$3 = this.upper.size() - 1;
/* 39 */     for (int $$4 = 0; $$4 < $$3; $$4++) {
/* 40 */       if (!$$0.merge($$1 - 1, $$4, $$1 + $$4)) {
/* 41 */         return false;
/*    */       }
/*    */     } 
/* 44 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getDouble(int $$0) {
/* 49 */     if ($$0 < this.lower.size()) {
/* 50 */       return this.lower.getDouble($$0);
/*    */     }
/* 52 */     return this.upper.getDouble($$0 - this.lower.size());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public DoubleList getList() {
/* 58 */     return (DoubleList)this;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\phys\shapes\NonOverlappingMerger.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */