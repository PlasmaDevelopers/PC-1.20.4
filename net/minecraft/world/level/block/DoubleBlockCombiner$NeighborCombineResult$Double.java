/*    */ package net.minecraft.world.level.block;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class Double<S>
/*    */   implements DoubleBlockCombiner.NeighborCombineResult<S>
/*    */ {
/*    */   private final S first;
/*    */   private final S second;
/*    */   
/*    */   public Double(S $$0, S $$1) {
/* 77 */     this.first = $$0;
/* 78 */     this.second = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public <T> T apply(DoubleBlockCombiner.Combiner<? super S, T> $$0) {
/* 83 */     return $$0.acceptDouble(this.first, this.second);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\DoubleBlockCombiner$NeighborCombineResult$Double.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */