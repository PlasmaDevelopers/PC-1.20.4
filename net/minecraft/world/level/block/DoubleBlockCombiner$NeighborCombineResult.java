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
/*    */ public interface NeighborCombineResult<S>
/*    */ {
/*    */   <T> T apply(DoubleBlockCombiner.Combiner<? super S, T> paramCombiner);
/*    */   
/*    */   public static final class Double<S>
/*    */     implements NeighborCombineResult<S>
/*    */   {
/*    */     private final S first;
/*    */     private final S second;
/*    */     
/*    */     public Double(S $$0, S $$1) {
/* 77 */       this.first = $$0;
/* 78 */       this.second = $$1;
/*    */     }
/*    */ 
/*    */     
/*    */     public <T> T apply(DoubleBlockCombiner.Combiner<? super S, T> $$0) {
/* 83 */       return $$0.acceptDouble(this.first, this.second);
/*    */     }
/*    */   }
/*    */   
/*    */   public static final class Single<S> implements NeighborCombineResult<S> {
/*    */     private final S single;
/*    */     
/*    */     public Single(S $$0) {
/* 91 */       this.single = $$0;
/*    */     }
/*    */ 
/*    */     
/*    */     public <T> T apply(DoubleBlockCombiner.Combiner<? super S, T> $$0) {
/* 96 */       return $$0.acceptSingle(this.single);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\DoubleBlockCombiner$NeighborCombineResult.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */