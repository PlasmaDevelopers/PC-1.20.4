/*    */ package net.minecraft.world.phys.shapes;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.doubles.DoubleList;
/*    */ 
/*    */ public class IdenticalMerger implements IndexMerger {
/*    */   private final DoubleList coords;
/*    */   
/*    */   public IdenticalMerger(DoubleList $$0) {
/*  9 */     this.coords = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean forMergedIndexes(IndexMerger.IndexConsumer $$0) {
/* 14 */     int $$1 = this.coords.size() - 1;
/* 15 */     for (int $$2 = 0; $$2 < $$1; $$2++) {
/* 16 */       if (!$$0.merge($$2, $$2, $$2)) {
/* 17 */         return false;
/*    */       }
/*    */     } 
/* 20 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public int size() {
/* 25 */     return this.coords.size();
/*    */   }
/*    */ 
/*    */   
/*    */   public DoubleList getList() {
/* 30 */     return this.coords;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\phys\shapes\IdenticalMerger.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */