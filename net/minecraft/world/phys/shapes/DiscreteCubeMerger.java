/*    */ package net.minecraft.world.phys.shapes;
/*    */ 
/*    */ import com.google.common.math.IntMath;
/*    */ import it.unimi.dsi.fastutil.doubles.DoubleList;
/*    */ 
/*    */ public final class DiscreteCubeMerger implements IndexMerger {
/*    */   private final CubePointRange result;
/*    */   private final int firstDiv;
/*    */   private final int secondDiv;
/*    */   
/*    */   DiscreteCubeMerger(int $$0, int $$1) {
/* 12 */     this.result = new CubePointRange((int)Shapes.lcm($$0, $$1));
/*    */     
/* 14 */     int $$2 = IntMath.gcd($$0, $$1);
/* 15 */     this.firstDiv = $$0 / $$2;
/* 16 */     this.secondDiv = $$1 / $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean forMergedIndexes(IndexMerger.IndexConsumer $$0) {
/* 21 */     int $$1 = this.result.size() - 1;
/* 22 */     for (int $$2 = 0; $$2 < $$1; $$2++) {
/* 23 */       if (!$$0.merge($$2 / this.secondDiv, $$2 / this.firstDiv, $$2)) {
/* 24 */         return false;
/*    */       }
/*    */     } 
/* 27 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public int size() {
/* 32 */     return this.result.size();
/*    */   }
/*    */ 
/*    */   
/*    */   public DoubleList getList() {
/* 37 */     return (DoubleList)this.result;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\phys\shapes\DiscreteCubeMerger.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */