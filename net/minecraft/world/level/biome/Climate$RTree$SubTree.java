/*     */ package net.minecraft.world.level.biome;
/*     */ 
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class SubTree<T>
/*     */   extends Climate.RTree.Node<T>
/*     */ {
/*     */   final Climate.RTree.Node<T>[] children;
/*     */   
/*     */   protected SubTree(List<? extends Climate.RTree.Node<T>> $$0) {
/* 116 */     this(Climate.RTree.buildParameterSpace($$0), $$0);
/*     */   }
/*     */   
/*     */   protected SubTree(List<Climate.Parameter> $$0, List<? extends Climate.RTree.Node<T>> $$1) {
/* 120 */     super($$0);
/* 121 */     this.children = $$1.<Climate.RTree.Node<T>>toArray((Climate.RTree.Node<T>[])new Climate.RTree.Node[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Climate.RTree.Leaf<T> search(long[] $$0, @Nullable Climate.RTree.Leaf<T> $$1, Climate.DistanceMetric<T> $$2) {
/* 126 */     long $$3 = ($$1 == null) ? Long.MAX_VALUE : $$2.distance($$1, $$0);
/* 127 */     Climate.RTree.Leaf<T> $$4 = $$1;
/*     */     
/* 129 */     for (Climate.RTree.Node<T> $$5 : this.children) {
/* 130 */       long $$6 = $$2.distance($$5, $$0);
/* 131 */       if ($$3 > $$6) {
/*     */         
/* 133 */         Climate.RTree.Leaf<T> $$7 = $$5.search($$0, $$4, $$2);
/* 134 */         long $$8 = ($$5 == $$7) ? $$6 : $$2.distance($$7, $$0);
/* 135 */         if ($$3 > $$8) {
/* 136 */           $$3 = $$8;
/* 137 */           $$4 = $$7;
/*     */         } 
/*     */       } 
/*     */     } 
/* 141 */     return $$4;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\biome\Climate$RTree$SubTree.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */