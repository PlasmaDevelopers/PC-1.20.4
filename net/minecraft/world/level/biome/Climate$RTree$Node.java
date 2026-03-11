/*    */ package net.minecraft.world.level.biome;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.util.Mth;
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
/*    */ abstract class Node<T>
/*    */ {
/*    */   protected final Climate.Parameter[] parameterSpace;
/*    */   
/*    */   protected Node(List<Climate.Parameter> $$0) {
/* 79 */     this.parameterSpace = $$0.<Climate.Parameter>toArray(new Climate.Parameter[0]);
/*    */   }
/*    */   
/*    */   protected abstract Climate.RTree.Leaf<T> search(long[] paramArrayOflong, @Nullable Climate.RTree.Leaf<T> paramLeaf, Climate.DistanceMetric<T> paramDistanceMetric);
/*    */   
/*    */   protected long distance(long[] $$0) {
/* 85 */     long $$1 = 0L;
/* 86 */     for (int $$2 = 0; $$2 < 7; $$2++) {
/* 87 */       $$1 += Mth.square(this.parameterSpace[$$2].distance($$0[$$2]));
/*    */     }
/* 89 */     return $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 94 */     return Arrays.toString((Object[])this.parameterSpace);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\biome\Climate$RTree$Node.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */