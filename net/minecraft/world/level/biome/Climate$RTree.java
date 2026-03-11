/*     */ package net.minecraft.world.level.biome;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import java.util.Arrays;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.stream.Collectors;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.util.Mth;
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
/*     */ public final class RTree<T>
/*     */ {
/*     */   private static final int CHILDREN_PER_NODE = 6;
/*     */   private final Node<T> root;
/*  69 */   private final ThreadLocal<Leaf<T>> lastResult = new ThreadLocal<>();
/*     */   
/*     */   private RTree(Node<T> $$0) {
/*  72 */     this.root = $$0;
/*     */   }
/*     */   
/*     */   static abstract class Node<T> {
/*     */     protected final Climate.Parameter[] parameterSpace;
/*     */     
/*     */     protected Node(List<Climate.Parameter> $$0) {
/*  79 */       this.parameterSpace = $$0.<Climate.Parameter>toArray(new Climate.Parameter[0]);
/*     */     }
/*     */     
/*     */     protected abstract Climate.RTree.Leaf<T> search(long[] param2ArrayOflong, @Nullable Climate.RTree.Leaf<T> param2Leaf, Climate.DistanceMetric<T> param2DistanceMetric);
/*     */     
/*     */     protected long distance(long[] $$0) {
/*  85 */       long $$1 = 0L;
/*  86 */       for (int $$2 = 0; $$2 < 7; $$2++) {
/*  87 */         $$1 += Mth.square(this.parameterSpace[$$2].distance($$0[$$2]));
/*     */       }
/*  89 */       return $$1;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/*  94 */       return Arrays.toString((Object[])this.parameterSpace);
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class Leaf<T> extends Node<T> {
/*     */     final T value;
/*     */     
/*     */     Leaf(Climate.ParameterPoint $$0, T $$1) {
/* 102 */       super($$0.parameterSpace());
/* 103 */       this.value = $$1;
/*     */     }
/*     */ 
/*     */     
/*     */     protected Leaf<T> search(long[] $$0, @Nullable Leaf<T> $$1, Climate.DistanceMetric<T> $$2) {
/* 108 */       return this;
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class SubTree<T> extends Node<T> {
/*     */     final Climate.RTree.Node<T>[] children;
/*     */     
/*     */     protected SubTree(List<? extends Climate.RTree.Node<T>> $$0) {
/* 116 */       this(Climate.RTree.buildParameterSpace($$0), $$0);
/*     */     }
/*     */     
/*     */     protected SubTree(List<Climate.Parameter> $$0, List<? extends Climate.RTree.Node<T>> $$1) {
/* 120 */       super($$0);
/* 121 */       this.children = $$1.<Climate.RTree.Node<T>>toArray((Climate.RTree.Node<T>[])new Climate.RTree.Node[0]);
/*     */     }
/*     */ 
/*     */     
/*     */     protected Climate.RTree.Leaf<T> search(long[] $$0, @Nullable Climate.RTree.Leaf<T> $$1, Climate.DistanceMetric<T> $$2) {
/* 126 */       long $$3 = ($$1 == null) ? Long.MAX_VALUE : $$2.distance($$1, $$0);
/* 127 */       Climate.RTree.Leaf<T> $$4 = $$1;
/*     */       
/* 129 */       for (Climate.RTree.Node<T> $$5 : this.children) {
/* 130 */         long $$6 = $$2.distance($$5, $$0);
/* 131 */         if ($$3 > $$6) {
/*     */           
/* 133 */           Climate.RTree.Leaf<T> $$7 = $$5.search($$0, $$4, $$2);
/* 134 */           long $$8 = ($$5 == $$7) ? $$6 : $$2.distance($$7, $$0);
/* 135 */           if ($$3 > $$8) {
/* 136 */             $$3 = $$8;
/* 137 */             $$4 = $$7;
/*     */           } 
/*     */         } 
/*     */       } 
/* 141 */       return $$4;
/*     */     }
/*     */   }
/*     */   
/*     */   public static <T> RTree<T> create(List<Pair<Climate.ParameterPoint, T>> $$0) {
/* 146 */     if ($$0.isEmpty()) {
/* 147 */       throw new IllegalArgumentException("Need at least one value to build the search tree.");
/*     */     }
/* 149 */     int $$1 = ((Climate.ParameterPoint)((Pair)$$0.get(0)).getFirst()).parameterSpace().size();
/* 150 */     if ($$1 != 7) {
/* 151 */       throw new IllegalStateException("Expecting parameter space to be 7, got " + $$1);
/*     */     }
/*     */     
/* 154 */     List<Leaf<T>> $$2 = (List<Leaf<T>>)$$0.stream().map($$0 -> new Leaf((Climate.ParameterPoint)$$0.getFirst(), $$0.getSecond())).collect(Collectors.toCollection(java.util.ArrayList::new));
/*     */     
/* 156 */     return new RTree<>(build($$1, (List)$$2));
/*     */   }
/*     */   
/*     */   private static <T> Node<T> build(int $$0, List<? extends Node<T>> $$1) {
/* 160 */     if ($$1.isEmpty()) {
/* 161 */       throw new IllegalStateException("Need at least one child to build a node");
/*     */     }
/* 163 */     if ($$1.size() == 1) {
/* 164 */       return $$1.get(0);
/*     */     }
/* 166 */     if ($$1.size() <= 6) {
/* 167 */       $$1.sort(Comparator.comparingLong($$1 -> {
/*     */               long $$2 = 0L;
/*     */               for (int $$3 = 0; $$3 < $$0; $$3++) {
/*     */                 Climate.Parameter $$4 = $$1.parameterSpace[$$3];
/*     */                 $$2 += Math.abs(($$4.min() + $$4.max()) / 2L);
/*     */               } 
/*     */               return $$2;
/*     */             }));
/* 175 */       return new SubTree<>($$1);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 182 */     long $$2 = Long.MAX_VALUE;
/* 183 */     int $$3 = -1;
/* 184 */     List<SubTree<T>> $$4 = null;
/*     */     
/* 186 */     for (int $$5 = 0; $$5 < $$0; $$5++) {
/* 187 */       sort($$1, $$0, $$5, false);
/* 188 */       List<SubTree<T>> $$6 = bucketize($$1);
/*     */       
/* 190 */       long $$7 = 0L;
/* 191 */       for (SubTree<T> $$8 : $$6) {
/* 192 */         $$7 += cost($$8.parameterSpace);
/*     */       }
/*     */       
/* 195 */       if ($$2 > $$7) {
/* 196 */         $$2 = $$7;
/* 197 */         $$3 = $$5;
/* 198 */         $$4 = $$6;
/*     */       } 
/*     */     } 
/*     */     
/* 202 */     sort((List)$$4, $$0, $$3, true);
/*     */     
/* 204 */     return new SubTree<>((List<? extends Node<T>>)$$4.stream().map($$1 -> build($$0, Arrays.asList((Node<?>[])$$1.children))).collect(Collectors.toList()));
/*     */   }
/*     */   
/*     */   private static <T> void sort(List<? extends Node<T>> $$0, int $$1, int $$2, boolean $$3) {
/* 208 */     Comparator<Node<T>> $$4 = comparator($$2, $$3);
/* 209 */     for (int $$5 = 1; $$5 < $$1; $$5++) {
/* 210 */       $$4 = $$4.thenComparing((Comparator)comparator(($$2 + $$5) % $$1, $$3));
/*     */     }
/* 212 */     $$0.sort($$4);
/*     */   }
/*     */   
/*     */   private static <T> Comparator<Node<T>> comparator(int $$0, boolean $$1) {
/* 216 */     return Comparator.comparingLong($$2 -> {
/*     */           Climate.Parameter $$3 = $$2.parameterSpace[$$0];
/*     */           long $$4 = ($$3.min() + $$3.max()) / 2L;
/*     */           return $$1 ? Math.abs($$4) : $$4;
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static <T> List<SubTree<T>> bucketize(List<? extends Node<T>> $$0) {
/* 227 */     List<SubTree<T>> $$1 = Lists.newArrayList();
/*     */     
/* 229 */     List<Node<T>> $$2 = Lists.newArrayList();
/* 230 */     int $$3 = (int)Math.pow(6.0D, Math.floor(Math.log($$0.size() - 0.01D) / Math.log(6.0D)));
/* 231 */     for (Node<T> $$4 : $$0) {
/* 232 */       $$2.add($$4);
/* 233 */       if ($$2.size() >= $$3) {
/* 234 */         $$1.add(new SubTree<>($$2));
/* 235 */         $$2 = Lists.newArrayList();
/*     */       } 
/*     */     } 
/* 238 */     if (!$$2.isEmpty()) {
/* 239 */       $$1.add(new SubTree<>($$2));
/*     */     }
/* 241 */     return $$1;
/*     */   }
/*     */   
/*     */   private static long cost(Climate.Parameter[] $$0) {
/* 245 */     long $$1 = 0L;
/* 246 */     for (Climate.Parameter $$2 : $$0) {
/* 247 */       $$1 += Math.abs($$2.max() - $$2.min());
/*     */     }
/* 249 */     return $$1;
/*     */   }
/*     */   
/*     */   static <T> List<Climate.Parameter> buildParameterSpace(List<? extends Node<T>> $$0) {
/* 253 */     if ($$0.isEmpty()) {
/* 254 */       throw new IllegalArgumentException("SubTree needs at least one child");
/*     */     }
/* 256 */     int $$1 = 7;
/* 257 */     List<Climate.Parameter> $$2 = Lists.newArrayList();
/* 258 */     for (int $$3 = 0; $$3 < 7; $$3++) {
/* 259 */       $$2.add((Climate.Parameter)null);
/*     */     }
/* 261 */     for (Node<T> $$4 : $$0) {
/* 262 */       for (int $$5 = 0; $$5 < 7; $$5++) {
/* 263 */         $$2.set($$5, $$4.parameterSpace[$$5].span($$2.get($$5)));
/*     */       }
/*     */     } 
/* 266 */     return $$2;
/*     */   }
/*     */   
/*     */   public T search(Climate.TargetPoint $$0, Climate.DistanceMetric<T> $$1) {
/* 270 */     long[] $$2 = $$0.toParameterArray();
/* 271 */     Leaf<T> $$3 = this.root.search($$2, this.lastResult.get(), $$1);
/* 272 */     this.lastResult.set($$3);
/* 273 */     return $$3.value;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\biome\Climate$RTree.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */