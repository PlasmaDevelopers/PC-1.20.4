/*     */ package net.minecraft.world.level.biome;
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Function7;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.DataResult;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.Arrays;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.function.Function;
/*     */ import java.util.stream.Collectors;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.QuartPos;
/*     */ import net.minecraft.util.ExtraCodecs;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.level.levelgen.DensityFunction;
/*     */ import net.minecraft.world.level.levelgen.DensityFunctions;
/*     */ 
/*     */ public class Climate {
/*     */   private static final boolean DEBUG_SLOW_BIOME_SEARCH = false;
/*     */   private static final float QUANTIZATION_FACTOR = 10000.0F;
/*     */   @VisibleForTesting
/*     */   protected static final int PARAMETER_COUNT = 7;
/*     */   
/*     */   public static TargetPoint target(float $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/*  35 */     return new TargetPoint(quantizeCoord($$0), quantizeCoord($$1), quantizeCoord($$2), quantizeCoord($$3), quantizeCoord($$4), quantizeCoord($$5));
/*     */   }
/*     */   
/*     */   public static ParameterPoint parameters(float $$0, float $$1, float $$2, float $$3, float $$4, float $$5, float $$6) {
/*  39 */     return new ParameterPoint(Parameter.point($$0), Parameter.point($$1), Parameter.point($$2), Parameter.point($$3), Parameter.point($$4), Parameter.point($$5), quantizeCoord($$6));
/*     */   }
/*     */   
/*     */   public static ParameterPoint parameters(Parameter $$0, Parameter $$1, Parameter $$2, Parameter $$3, Parameter $$4, Parameter $$5, float $$6) {
/*  43 */     return new ParameterPoint($$0, $$1, $$2, $$3, $$4, $$5, quantizeCoord($$6));
/*     */   }
/*     */   
/*     */   public static long quantizeCoord(float $$0) {
/*  47 */     return (long)($$0 * 10000.0F);
/*     */   }
/*     */   
/*     */   public static float unquantizeCoord(long $$0) {
/*  51 */     return (float)$$0 / 10000.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final class RTree<T>
/*     */   {
/*     */     private static final int CHILDREN_PER_NODE = 6;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final Node<T> root;
/*     */ 
/*     */ 
/*     */     
/*  69 */     private final ThreadLocal<Leaf<T>> lastResult = new ThreadLocal<>();
/*     */     
/*     */     private RTree(Node<T> $$0) {
/*  72 */       this.root = $$0;
/*     */     }
/*     */     
/*     */     static abstract class Node<T> {
/*     */       protected final Climate.Parameter[] parameterSpace;
/*     */       
/*     */       protected Node(List<Climate.Parameter> $$0) {
/*  79 */         this.parameterSpace = $$0.<Climate.Parameter>toArray(new Climate.Parameter[0]);
/*     */       }
/*     */       
/*     */       protected abstract Climate.RTree.Leaf<T> search(long[] param2ArrayOflong, @Nullable Climate.RTree.Leaf<T> param2Leaf, Climate.DistanceMetric<T> param2DistanceMetric);
/*     */       
/*     */       protected long distance(long[] $$0) {
/*  85 */         long $$1 = 0L;
/*  86 */         for (int $$2 = 0; $$2 < 7; $$2++) {
/*  87 */           $$1 += Mth.square(this.parameterSpace[$$2].distance($$0[$$2]));
/*     */         }
/*  89 */         return $$1;
/*     */       }
/*     */ 
/*     */       
/*     */       public String toString() {
/*  94 */         return Arrays.toString((Object[])this.parameterSpace);
/*     */       }
/*     */     }
/*     */     
/*     */     private static final class Leaf<T> extends Node<T> {
/*     */       final T value;
/*     */       
/*     */       Leaf(Climate.ParameterPoint $$0, T $$1) {
/* 102 */         super($$0.parameterSpace());
/* 103 */         this.value = $$1;
/*     */       }
/*     */ 
/*     */       
/*     */       protected Leaf<T> search(long[] $$0, @Nullable Leaf<T> $$1, Climate.DistanceMetric<T> $$2) {
/* 108 */         return this;
/*     */       }
/*     */     }
/*     */     
/*     */     private static final class SubTree<T> extends Node<T> {
/*     */       final Climate.RTree.Node<T>[] children;
/*     */       
/*     */       protected SubTree(List<? extends Climate.RTree.Node<T>> $$0) {
/* 116 */         this(Climate.RTree.buildParameterSpace($$0), $$0);
/*     */       }
/*     */       
/*     */       protected SubTree(List<Climate.Parameter> $$0, List<? extends Climate.RTree.Node<T>> $$1) {
/* 120 */         super($$0);
/* 121 */         this.children = $$1.<Climate.RTree.Node<T>>toArray((Climate.RTree.Node<T>[])new Climate.RTree.Node[0]);
/*     */       }
/*     */ 
/*     */       
/*     */       protected Climate.RTree.Leaf<T> search(long[] $$0, @Nullable Climate.RTree.Leaf<T> $$1, Climate.DistanceMetric<T> $$2) {
/* 126 */         long $$3 = ($$1 == null) ? Long.MAX_VALUE : $$2.distance($$1, $$0);
/* 127 */         Climate.RTree.Leaf<T> $$4 = $$1;
/*     */         
/* 129 */         for (Climate.RTree.Node<T> $$5 : this.children) {
/* 130 */           long $$6 = $$2.distance($$5, $$0);
/* 131 */           if ($$3 > $$6) {
/*     */             
/* 133 */             Climate.RTree.Leaf<T> $$7 = $$5.search($$0, $$4, $$2);
/* 134 */             long $$8 = ($$5 == $$7) ? $$6 : $$2.distance($$7, $$0);
/* 135 */             if ($$3 > $$8) {
/* 136 */               $$3 = $$8;
/* 137 */               $$4 = $$7;
/*     */             } 
/*     */           } 
/*     */         } 
/* 141 */         return $$4;
/*     */       }
/*     */     }
/*     */     
/*     */     public static <T> RTree<T> create(List<Pair<Climate.ParameterPoint, T>> $$0) {
/* 146 */       if ($$0.isEmpty()) {
/* 147 */         throw new IllegalArgumentException("Need at least one value to build the search tree.");
/*     */       }
/* 149 */       int $$1 = ((Climate.ParameterPoint)((Pair)$$0.get(0)).getFirst()).parameterSpace().size();
/* 150 */       if ($$1 != 7) {
/* 151 */         throw new IllegalStateException("Expecting parameter space to be 7, got " + $$1);
/*     */       }
/*     */       
/* 154 */       List<Leaf<T>> $$2 = (List<Leaf<T>>)$$0.stream().map($$0 -> new Leaf((Climate.ParameterPoint)$$0.getFirst(), $$0.getSecond())).collect(Collectors.toCollection(java.util.ArrayList::new));
/*     */       
/* 156 */       return new RTree<>(build($$1, (List)$$2));
/*     */     }
/*     */     
/*     */     private static <T> Node<T> build(int $$0, List<? extends Node<T>> $$1) {
/* 160 */       if ($$1.isEmpty()) {
/* 161 */         throw new IllegalStateException("Need at least one child to build a node");
/*     */       }
/* 163 */       if ($$1.size() == 1) {
/* 164 */         return $$1.get(0);
/*     */       }
/* 166 */       if ($$1.size() <= 6) {
/* 167 */         $$1.sort(Comparator.comparingLong($$1 -> {
/*     */                 long $$2 = 0L;
/*     */                 for (int $$3 = 0; $$3 < $$0; $$3++) {
/*     */                   Climate.Parameter $$4 = $$1.parameterSpace[$$3];
/*     */                   $$2 += Math.abs(($$4.min() + $$4.max()) / 2L);
/*     */                 } 
/*     */                 return $$2;
/*     */               }));
/* 175 */         return new SubTree<>($$1);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 182 */       long $$2 = Long.MAX_VALUE;
/* 183 */       int $$3 = -1;
/* 184 */       List<SubTree<T>> $$4 = null;
/*     */       
/* 186 */       for (int $$5 = 0; $$5 < $$0; $$5++) {
/* 187 */         sort($$1, $$0, $$5, false);
/* 188 */         List<SubTree<T>> $$6 = bucketize($$1);
/*     */         
/* 190 */         long $$7 = 0L;
/* 191 */         for (SubTree<T> $$8 : $$6) {
/* 192 */           $$7 += cost($$8.parameterSpace);
/*     */         }
/*     */         
/* 195 */         if ($$2 > $$7) {
/* 196 */           $$2 = $$7;
/* 197 */           $$3 = $$5;
/* 198 */           $$4 = $$6;
/*     */         } 
/*     */       } 
/*     */       
/* 202 */       sort((List)$$4, $$0, $$3, true);
/*     */       
/* 204 */       return new SubTree<>((List<? extends Node<T>>)$$4.stream().map($$1 -> build($$0, Arrays.asList((Node<?>[])$$1.children))).collect(Collectors.toList()));
/*     */     }
/*     */     
/*     */     private static <T> void sort(List<? extends Node<T>> $$0, int $$1, int $$2, boolean $$3) {
/* 208 */       Comparator<Node<T>> $$4 = comparator($$2, $$3);
/* 209 */       for (int $$5 = 1; $$5 < $$1; $$5++) {
/* 210 */         $$4 = $$4.thenComparing((Comparator)comparator(($$2 + $$5) % $$1, $$3));
/*     */       }
/* 212 */       $$0.sort($$4);
/*     */     }
/*     */     
/*     */     private static <T> Comparator<Node<T>> comparator(int $$0, boolean $$1) {
/* 216 */       return Comparator.comparingLong($$2 -> {
/*     */             Climate.Parameter $$3 = $$2.parameterSpace[$$0];
/*     */             long $$4 = ($$3.min() + $$3.max()) / 2L;
/*     */             return $$1 ? Math.abs($$4) : $$4;
/*     */           });
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static <T> List<SubTree<T>> bucketize(List<? extends Node<T>> $$0) {
/* 227 */       List<SubTree<T>> $$1 = Lists.newArrayList();
/*     */       
/* 229 */       List<Node<T>> $$2 = Lists.newArrayList();
/* 230 */       int $$3 = (int)Math.pow(6.0D, Math.floor(Math.log($$0.size() - 0.01D) / Math.log(6.0D)));
/* 231 */       for (Node<T> $$4 : $$0) {
/* 232 */         $$2.add($$4);
/* 233 */         if ($$2.size() >= $$3) {
/* 234 */           $$1.add(new SubTree<>($$2));
/* 235 */           $$2 = Lists.newArrayList();
/*     */         } 
/*     */       } 
/* 238 */       if (!$$2.isEmpty()) {
/* 239 */         $$1.add(new SubTree<>($$2));
/*     */       }
/* 241 */       return $$1;
/*     */     }
/*     */     
/*     */     private static long cost(Climate.Parameter[] $$0) {
/* 245 */       long $$1 = 0L;
/* 246 */       for (Climate.Parameter $$2 : $$0) {
/* 247 */         $$1 += Math.abs($$2.max() - $$2.min());
/*     */       }
/* 249 */       return $$1;
/*     */     }
/*     */     
/*     */     static <T> List<Climate.Parameter> buildParameterSpace(List<? extends Node<T>> $$0) {
/* 253 */       if ($$0.isEmpty()) {
/* 254 */         throw new IllegalArgumentException("SubTree needs at least one child");
/*     */       }
/* 256 */       int $$1 = 7;
/* 257 */       List<Climate.Parameter> $$2 = Lists.newArrayList();
/* 258 */       for (int $$3 = 0; $$3 < 7; $$3++) {
/* 259 */         $$2.add((Climate.Parameter)null);
/*     */       }
/* 261 */       for (Node<T> $$4 : $$0) {
/* 262 */         for (int $$5 = 0; $$5 < 7; $$5++) {
/* 263 */           $$2.set($$5, $$4.parameterSpace[$$5].span($$2.get($$5)));
/*     */         }
/*     */       } 
/* 266 */       return $$2;
/*     */     }
/*     */     
/*     */     public T search(Climate.TargetPoint $$0, Climate.DistanceMetric<T> $$1) {
/* 270 */       long[] $$2 = $$0.toParameterArray();
/* 271 */       Leaf<T> $$3 = this.root.search($$2, this.lastResult.get(), $$1);
/* 272 */       this.lastResult.set($$3);
/* 273 */       return $$3.value;
/*     */     } }
/*     */   static abstract class Node<T> {
/*     */     protected final Climate.Parameter[] parameterSpace;
/*     */     protected Node(List<Climate.Parameter> $$0) { this.parameterSpace = $$0.<Climate.Parameter>toArray(new Climate.Parameter[0]); } protected abstract Climate.RTree.Leaf<T> search(long[] param1ArrayOflong, @Nullable Climate.RTree.Leaf<T> param1Leaf, Climate.DistanceMetric<T> param1DistanceMetric); protected long distance(long[] $$0) { long $$1 = 0L; for (int $$2 = 0; $$2 < 7; $$2++) $$1 += Mth.square(this.parameterSpace[$$2].distance($$0[$$2]));  return $$1; } public String toString() { return Arrays.toString((Object[])this.parameterSpace); }
/*     */   } private static final class Leaf<T> extends RTree.Node<T> {
/*     */     final T value; Leaf(Climate.ParameterPoint $$0, T $$1) { super($$0.parameterSpace()); this.value = $$1; } protected Leaf<T> search(long[] $$0, @Nullable Leaf<T> $$1, Climate.DistanceMetric<T> $$2) { return this; }
/*     */   } private static final class SubTree<T> extends RTree.Node<T> {
/*     */     final Climate.RTree.Node<T>[] children; protected SubTree(List<? extends Climate.RTree.Node<T>> $$0) { this(Climate.RTree.buildParameterSpace($$0), $$0); } protected SubTree(List<Climate.Parameter> $$0, List<? extends Climate.RTree.Node<T>> $$1) { super($$0); this.children = $$1.<Climate.RTree.Node<T>>toArray((Climate.RTree.Node<T>[])new Climate.RTree.Node[0]); } protected Climate.RTree.Leaf<T> search(long[] $$0, @Nullable Climate.RTree.Leaf<T> $$1, Climate.DistanceMetric<T> $$2) { long $$3 = ($$1 == null) ? Long.MAX_VALUE : $$2.distance($$1, $$0); Climate.RTree.Leaf<T> $$4 = $$1; for (Climate.RTree.Node<T> $$5 : this.children) { long $$6 = $$2.distance($$5, $$0); if ($$3 > $$6) { Climate.RTree.Leaf<T> $$7 = $$5.search($$0, $$4, $$2); long $$8 = ($$5 == $$7) ? $$6 : $$2.distance($$7, $$0); if ($$3 > $$8) { $$3 = $$8; $$4 = $$7; }  }  }  return $$4; }
/* 282 */   } public static class ParameterList<T> { public static <T> Codec<ParameterList<T>> codec(MapCodec<T> $$0) { return ExtraCodecs.nonEmptyList(RecordCodecBuilder.create($$1 -> $$1.group((App)Climate.ParameterPoint.CODEC.fieldOf("parameters").forGetter(Pair::getFirst), (App)$$0.forGetter(Pair::getSecond)).apply((Applicative)$$1, Pair::of))
/*     */ 
/*     */           
/* 285 */           .listOf()).xmap(ParameterList::new, ParameterList::values); }
/*     */     
/*     */     private final List<Pair<Climate.ParameterPoint, T>> values; private final Climate.RTree<T> index;
/*     */     public ParameterList(List<Pair<Climate.ParameterPoint, T>> $$0) {
/* 289 */       this.values = $$0;
/* 290 */       this.index = Climate.RTree.create($$0);
/*     */     }
/*     */     
/*     */     public List<Pair<Climate.ParameterPoint, T>> values() {
/* 294 */       return this.values;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public T findValue(Climate.TargetPoint $$0) {
/* 301 */       return findValueIndex($$0);
/*     */     }
/*     */     
/*     */     @VisibleForTesting
/*     */     public T findValueBruteForce(Climate.TargetPoint $$0) {
/* 306 */       Iterator<Pair<Climate.ParameterPoint, T>> $$1 = values().iterator();
/*     */ 
/*     */       
/* 309 */       Pair<Climate.ParameterPoint, T> $$2 = $$1.next();
/* 310 */       long $$3 = ((Climate.ParameterPoint)$$2.getFirst()).fitness($$0);
/* 311 */       T $$4 = (T)$$2.getSecond();
/*     */       
/* 313 */       while ($$1.hasNext()) {
/* 314 */         Pair<Climate.ParameterPoint, T> $$5 = $$1.next();
/* 315 */         long $$6 = ((Climate.ParameterPoint)$$5.getFirst()).fitness($$0);
/* 316 */         if ($$6 < $$3) {
/* 317 */           $$3 = $$6;
/* 318 */           $$4 = (T)$$5.getSecond();
/*     */         } 
/*     */       } 
/* 321 */       return $$4;
/*     */     }
/*     */     
/*     */     public T findValueIndex(Climate.TargetPoint $$0) {
/* 325 */       return findValueIndex($$0, Climate.RTree.Node::distance);
/*     */     }
/*     */     
/*     */     protected T findValueIndex(Climate.TargetPoint $$0, Climate.DistanceMetric<T> $$1) {
/* 329 */       return this.index.search($$0, $$1);
/*     */     } }
/*     */   public static final class TargetPoint extends Record { final long temperature; final long humidity;
/*     */     final long continentalness;
/*     */     final long erosion;
/*     */     final long depth;
/*     */     final long weirdness;
/*     */     
/* 337 */     public TargetPoint(long $$0, long $$1, long $$2, long $$3, long $$4, long $$5) { this.temperature = $$0; this.humidity = $$1; this.continentalness = $$2; this.erosion = $$3; this.depth = $$4; this.weirdness = $$5; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/biome/Climate$TargetPoint;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #337	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/biome/Climate$TargetPoint; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/biome/Climate$TargetPoint;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #337	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/biome/Climate$TargetPoint; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/biome/Climate$TargetPoint;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #337	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/world/level/biome/Climate$TargetPoint;
/* 337 */       //   0	8	1	$$0	Ljava/lang/Object; } public long temperature() { return this.temperature; } public long humidity() { return this.humidity; } public long continentalness() { return this.continentalness; } public long erosion() { return this.erosion; } public long depth() { return this.depth; } public long weirdness() { return this.weirdness; }
/*     */      @VisibleForTesting
/*     */     protected long[] toParameterArray() {
/* 340 */       return new long[] { this.temperature, this.humidity, this.continentalness, this.erosion, this.depth, this.weirdness, 0L };
/*     */     } }
/*     */   public static final class ParameterPoint extends Record { private final Climate.Parameter temperature; private final Climate.Parameter humidity; private final Climate.Parameter continentalness; private final Climate.Parameter erosion; private final Climate.Parameter depth; private final Climate.Parameter weirdness; private final long offset; public static final Codec<ParameterPoint> CODEC;
/*     */     
/* 344 */     public ParameterPoint(Climate.Parameter $$0, Climate.Parameter $$1, Climate.Parameter $$2, Climate.Parameter $$3, Climate.Parameter $$4, Climate.Parameter $$5, long $$6) { this.temperature = $$0; this.humidity = $$1; this.continentalness = $$2; this.erosion = $$3; this.depth = $$4; this.weirdness = $$5; this.offset = $$6; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/biome/Climate$ParameterPoint;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #344	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/biome/Climate$ParameterPoint; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/biome/Climate$ParameterPoint;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #344	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/biome/Climate$ParameterPoint; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/biome/Climate$ParameterPoint;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #344	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/world/level/biome/Climate$ParameterPoint;
/* 344 */       //   0	8	1	$$0	Ljava/lang/Object; } public Climate.Parameter temperature() { return this.temperature; } public Climate.Parameter humidity() { return this.humidity; } public Climate.Parameter continentalness() { return this.continentalness; } public Climate.Parameter erosion() { return this.erosion; } public Climate.Parameter depth() { return this.depth; } public Climate.Parameter weirdness() { return this.weirdness; } public long offset() { return this.offset; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/* 353 */       CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Climate.Parameter.CODEC.fieldOf("temperature").forGetter(()), (App)Climate.Parameter.CODEC.fieldOf("humidity").forGetter(()), (App)Climate.Parameter.CODEC.fieldOf("continentalness").forGetter(()), (App)Climate.Parameter.CODEC.fieldOf("erosion").forGetter(()), (App)Climate.Parameter.CODEC.fieldOf("depth").forGetter(()), (App)Climate.Parameter.CODEC.fieldOf("weirdness").forGetter(()), (App)Codec.floatRange(0.0F, 1.0F).fieldOf("offset").xmap(Climate::quantizeCoord, Climate::unquantizeCoord).forGetter(())).apply((Applicative)$$0, ParameterPoint::new));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     long fitness(Climate.TargetPoint $$0) {
/* 364 */       return Mth.square(this.temperature.distance($$0.temperature)) + 
/* 365 */         Mth.square(this.humidity.distance($$0.humidity)) + 
/* 366 */         Mth.square(this.continentalness.distance($$0.continentalness)) + 
/* 367 */         Mth.square(this.erosion.distance($$0.erosion)) + 
/* 368 */         Mth.square(this.depth.distance($$0.depth)) + 
/* 369 */         Mth.square(this.weirdness.distance($$0.weirdness)) + 
/* 370 */         Mth.square(this.offset);
/*     */     }
/*     */ 
/*     */     
/*     */     protected List<Climate.Parameter> parameterSpace() {
/* 375 */       return (List<Climate.Parameter>)ImmutableList.of(this.temperature, this.humidity, this.continentalness, this.erosion, this.depth, this.weirdness, new Climate.Parameter(this.offset, this.offset));
/*     */     } }
/*     */ 
/*     */   
/*     */   public static final class Parameter
/*     */     extends Record
/*     */   {
/*     */     private final long min;
/*     */     private final long max;
/*     */     public static final Codec<Parameter> CODEC;
/*     */     
/*     */     public Parameter(long $$0, long $$1) {
/* 387 */       this.min = $$0; this.max = $$1; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/biome/Climate$Parameter;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #387	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/biome/Climate$Parameter; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/biome/Climate$Parameter;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #387	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/world/level/biome/Climate$Parameter;
/* 387 */       //   0	8	1	$$0	Ljava/lang/Object; } public long min() { return this.min; } public long max() { return this.max; } static {
/* 388 */       CODEC = ExtraCodecs.intervalCodec(Codec.floatRange(-2.0F, 2.0F), "min", "max", ($$0, $$1) -> ($$0.compareTo($$1) > 0) ? DataResult.error(()) : DataResult.success(new Parameter(Climate.quantizeCoord($$0.floatValue()), Climate.quantizeCoord($$1.floatValue()))), $$0 -> Float.valueOf(Climate.unquantizeCoord($$0.min())), $$0 -> Float.valueOf(Climate.unquantizeCoord($$0.max())));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static Parameter point(float $$0) {
/* 396 */       return span($$0, $$0);
/*     */     }
/*     */     
/*     */     public static Parameter span(float $$0, float $$1) {
/* 400 */       if ($$0 > $$1) {
/* 401 */         throw new IllegalArgumentException("min > max: " + $$0 + " " + $$1);
/*     */       }
/* 403 */       return new Parameter(Climate.quantizeCoord($$0), Climate.quantizeCoord($$1));
/*     */     }
/*     */     
/*     */     public static Parameter span(Parameter $$0, Parameter $$1) {
/* 407 */       if ($$0.min() > $$1.max()) {
/* 408 */         throw new IllegalArgumentException("min > max: " + $$0 + " " + $$1);
/*     */       }
/* 410 */       return new Parameter($$0.min(), $$1.max());
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 415 */       return (this.min == this.max) ? String.format(Locale.ROOT, "%d", new Object[] { Long.valueOf(this.min) }) : String.format(Locale.ROOT, "[%d-%d]", new Object[] { Long.valueOf(this.min), Long.valueOf(this.max) });
/*     */     }
/*     */     
/*     */     public long distance(long $$0) {
/* 419 */       long $$1 = $$0 - this.max;
/* 420 */       long $$2 = this.min - $$0;
/* 421 */       if ($$1 > 0L) {
/* 422 */         return $$1;
/*     */       }
/* 424 */       return Math.max($$2, 0L);
/*     */     }
/*     */     
/*     */     public long distance(Parameter $$0) {
/* 428 */       long $$1 = $$0.min() - this.max;
/* 429 */       long $$2 = this.min - $$0.max();
/*     */       
/* 431 */       if ($$1 > 0L) {
/* 432 */         return $$1;
/*     */       }
/* 434 */       return Math.max($$2, 0L);
/*     */     }
/*     */     
/*     */     public Parameter span(@Nullable Parameter $$0) {
/* 438 */       return ($$0 == null) ? this : new Parameter(Math.min(this.min, $$0.min()), Math.max(this.max, $$0.max()));
/*     */     }
/*     */   }
/*     */   
/*     */   public static Sampler empty() {
/* 443 */     DensityFunction $$0 = DensityFunctions.zero();
/* 444 */     return new Sampler($$0, $$0, $$0, $$0, $$0, $$0, List.of());
/*     */   }
/*     */   public static final class Sampler extends Record { private final DensityFunction temperature; private final DensityFunction humidity; private final DensityFunction continentalness; private final DensityFunction erosion; private final DensityFunction depth; private final DensityFunction weirdness; private final List<Climate.ParameterPoint> spawnTarget;
/* 447 */     public Sampler(DensityFunction $$0, DensityFunction $$1, DensityFunction $$2, DensityFunction $$3, DensityFunction $$4, DensityFunction $$5, List<Climate.ParameterPoint> $$6) { this.temperature = $$0; this.humidity = $$1; this.continentalness = $$2; this.erosion = $$3; this.depth = $$4; this.weirdness = $$5; this.spawnTarget = $$6; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/biome/Climate$Sampler;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #447	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/biome/Climate$Sampler; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/biome/Climate$Sampler;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #447	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/biome/Climate$Sampler; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/biome/Climate$Sampler;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #447	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/world/level/biome/Climate$Sampler;
/* 447 */       //   0	8	1	$$0	Ljava/lang/Object; } public DensityFunction temperature() { return this.temperature; } public DensityFunction humidity() { return this.humidity; } public DensityFunction continentalness() { return this.continentalness; } public DensityFunction erosion() { return this.erosion; } public DensityFunction depth() { return this.depth; } public DensityFunction weirdness() { return this.weirdness; } public List<Climate.ParameterPoint> spawnTarget() { return this.spawnTarget; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Climate.TargetPoint sample(int $$0, int $$1, int $$2) {
/* 457 */       int $$3 = QuartPos.toBlock($$0);
/* 458 */       int $$4 = QuartPos.toBlock($$1);
/* 459 */       int $$5 = QuartPos.toBlock($$2);
/*     */       
/* 461 */       DensityFunction.SinglePointContext $$6 = new DensityFunction.SinglePointContext($$3, $$4, $$5);
/*     */       
/* 463 */       return Climate.target(
/* 464 */           (float)this.temperature.compute((DensityFunction.FunctionContext)$$6), 
/* 465 */           (float)this.humidity.compute((DensityFunction.FunctionContext)$$6), 
/* 466 */           (float)this.continentalness.compute((DensityFunction.FunctionContext)$$6), 
/* 467 */           (float)this.erosion.compute((DensityFunction.FunctionContext)$$6), 
/* 468 */           (float)this.depth.compute((DensityFunction.FunctionContext)$$6), 
/* 469 */           (float)this.weirdness.compute((DensityFunction.FunctionContext)$$6));
/*     */     }
/*     */ 
/*     */     
/*     */     public BlockPos findSpawnPosition() {
/* 474 */       if (this.spawnTarget.isEmpty()) {
/* 475 */         return BlockPos.ZERO;
/*     */       }
/* 477 */       return Climate.findSpawnPosition(this.spawnTarget, this);
/*     */     } }
/*     */   private static class SpawnFinder { Result result;
/*     */     private static final class Result extends Record { private final BlockPos location; private final long fitness;
/*     */       
/* 482 */       Result(BlockPos $$0, long $$1) { this.location = $$0; this.fitness = $$1; } public final String toString() { // Byte code:
/*     */         //   0: aload_0
/*     */         //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/biome/Climate$SpawnFinder$Result;)Ljava/lang/String;
/*     */         //   6: areturn
/*     */         // Line number table:
/*     */         //   Java source line number -> byte code offset
/*     */         //   #482	-> 0
/*     */         // Local variable table:
/*     */         //   start	length	slot	name	descriptor
/*     */         //   0	7	0	this	Lnet/minecraft/world/level/biome/Climate$SpawnFinder$Result; } public final int hashCode() { // Byte code:
/*     */         //   0: aload_0
/*     */         //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/biome/Climate$SpawnFinder$Result;)I
/*     */         //   6: ireturn
/*     */         // Line number table:
/*     */         //   Java source line number -> byte code offset
/*     */         //   #482	-> 0
/*     */         // Local variable table:
/*     */         //   start	length	slot	name	descriptor
/*     */         //   0	7	0	this	Lnet/minecraft/world/level/biome/Climate$SpawnFinder$Result; } public final boolean equals(Object $$0) { // Byte code:
/*     */         //   0: aload_0
/*     */         //   1: aload_1
/*     */         //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/biome/Climate$SpawnFinder$Result;Ljava/lang/Object;)Z
/*     */         //   7: ireturn
/*     */         // Line number table:
/*     */         //   Java source line number -> byte code offset
/*     */         //   #482	-> 0
/*     */         // Local variable table:
/*     */         //   start	length	slot	name	descriptor
/*     */         //   0	8	0	this	Lnet/minecraft/world/level/biome/Climate$SpawnFinder$Result;
/* 482 */         //   0	8	1	$$0	Ljava/lang/Object; } public BlockPos location() { return this.location; } public long fitness() { return this.fitness; }
/*     */        }
/*     */ 
/*     */     
/*     */     SpawnFinder(List<Climate.ParameterPoint> $$0, Climate.Sampler $$1) {
/* 487 */       this.result = getSpawnPositionAndFitness($$0, $$1, 0, 0);
/*     */ 
/*     */       
/* 490 */       radialSearch($$0, $$1, 2048.0F, 512.0F);
/*     */       
/* 492 */       radialSearch($$0, $$1, 512.0F, 32.0F);
/*     */     }
/*     */     
/*     */     private void radialSearch(List<Climate.ParameterPoint> $$0, Climate.Sampler $$1, float $$2, float $$3) {
/* 496 */       float $$4 = 0.0F;
/* 497 */       float $$5 = $$3;
/* 498 */       BlockPos $$6 = this.result.location();
/* 499 */       while ($$5 <= $$2) {
/* 500 */         int $$7 = $$6.getX() + (int)(Math.sin($$4) * $$5);
/* 501 */         int $$8 = $$6.getZ() + (int)(Math.cos($$4) * $$5);
/* 502 */         Result $$9 = getSpawnPositionAndFitness($$0, $$1, $$7, $$8);
/* 503 */         if ($$9.fitness() < this.result.fitness()) {
/* 504 */           this.result = $$9;
/*     */         }
/*     */         
/* 507 */         $$4 += $$3 / $$5;
/* 508 */         if ($$4 > 6.283185307179586D) {
/* 509 */           $$4 = 0.0F;
/* 510 */           $$5 += $$3;
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     private static Result getSpawnPositionAndFitness(List<Climate.ParameterPoint> $$0, Climate.Sampler $$1, int $$2, int $$3) {
/* 516 */       double $$4 = Mth.square(2500.0D);
/* 517 */       int $$5 = 2;
/*     */       
/* 519 */       long $$6 = (long)(Mth.square(10000.0F) * Math.pow((Mth.square($$2) + Mth.square($$3)) / $$4, 2.0D));
/*     */       
/* 521 */       Climate.TargetPoint $$7 = $$1.sample(QuartPos.fromBlock($$2), 0, QuartPos.fromBlock($$3));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 528 */       Climate.TargetPoint $$8 = new Climate.TargetPoint($$7.temperature(), $$7.humidity(), $$7.continentalness(), $$7.erosion(), 0L, $$7.weirdness());
/*     */ 
/*     */       
/* 531 */       long $$9 = Long.MAX_VALUE;
/* 532 */       for (Climate.ParameterPoint $$10 : $$0) {
/* 533 */         $$9 = Math.min($$9, $$10.fitness($$8));
/*     */       }
/*     */       
/* 536 */       return new Result(new BlockPos($$2, 0, $$3), $$6 + $$9);
/*     */     } } private static final class Result extends Record { private final BlockPos location; private final long fitness; Result(BlockPos $$0, long $$1) { this.location = $$0; this.fitness = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/biome/Climate$SpawnFinder$Result;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #482	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/biome/Climate$SpawnFinder$Result; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/biome/Climate$SpawnFinder$Result;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #482	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/biome/Climate$SpawnFinder$Result; }
/*     */     public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/biome/Climate$SpawnFinder$Result;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #482	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/world/level/biome/Climate$SpawnFinder$Result;
/*     */       //   0	8	1	$$0	Ljava/lang/Object; }
/*     */     public BlockPos location() { return this.location; }
/*     */     public long fitness() { return this.fitness; } }
/* 541 */   public static BlockPos findSpawnPosition(List<ParameterPoint> $$0, Sampler $$1) { return (new SpawnFinder($$0, $$1)).result.location(); }
/*     */ 
/*     */   
/*     */   static interface DistanceMetric<T> {
/*     */     long distance(Climate.RTree.Node<T> param1Node, long[] param1ArrayOflong);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\biome\Climate.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */