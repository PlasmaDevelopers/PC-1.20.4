/*     */ package net.minecraft.world.level.biome;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.Lists;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.TreeMap;
/*     */ import java.util.TreeSet;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.ToIntFunction;
/*     */ import java.util.stream.Collectors;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.HolderSet;
/*     */ import net.minecraft.util.Graph;
/*     */ import net.minecraft.world.level.levelgen.placement.PlacedFeature;
/*     */ import org.apache.commons.lang3.mutable.MutableInt;
/*     */ 
/*     */ public class FeatureSorter {
/*     */   public static final class StepFeatureData extends Record {
/*     */     private final List<PlacedFeature> features;
/*     */     private final ToIntFunction<PlacedFeature> indexMapping;
/*     */     
/*  28 */     public ToIntFunction<PlacedFeature> indexMapping() { return this.indexMapping; } public List<PlacedFeature> features() { return this.features; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/biome/FeatureSorter$StepFeatureData;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #28	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/world/level/biome/FeatureSorter$StepFeatureData;
/*  28 */       //   0	8	1	$$0	Ljava/lang/Object; } public StepFeatureData(List<PlacedFeature> $$0, ToIntFunction<PlacedFeature> $$1) { this.features = $$0; this.indexMapping = $$1; } public final int hashCode() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/biome/FeatureSorter$StepFeatureData;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #28	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/biome/FeatureSorter$StepFeatureData;
/*     */     } public final String toString() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/biome/FeatureSorter$StepFeatureData;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #28	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/biome/FeatureSorter$StepFeatureData;
/*     */     } StepFeatureData(List<PlacedFeature> $$0) {
/*  33 */       this($$0, Util.createIndexIdentityLookup($$0));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static <T> List<StepFeatureData> buildFeaturesPerStep(List<T> $$0, Function<T, List<HolderSet<PlacedFeature>>> $$1, boolean $$2) {
/*  39 */     Object2IntOpenHashMap object2IntOpenHashMap = new Object2IntOpenHashMap();
/*  40 */     MutableInt $$4 = new MutableInt(0);
/*     */     static final class FeatureData extends Record { private final int featureIndex; private final int step; private final PlacedFeature feature;
/*  42 */       FeatureData(int $$0, int $$1, PlacedFeature $$2) { this.featureIndex = $$0; this.step = $$1; this.feature = $$2; } public final String toString() { // Byte code:
/*     */         //   0: aload_0
/*     */         //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/biome/FeatureSorter$1FeatureData;)Ljava/lang/String;
/*     */         //   6: areturn
/*     */         // Line number table:
/*     */         //   Java source line number -> byte code offset
/*     */         //   #42	-> 0
/*     */         // Local variable table:
/*     */         //   start	length	slot	name	descriptor
/*  42 */         //   0	7	0	this	Lnet/minecraft/world/level/biome/FeatureSorter$1FeatureData; } public int featureIndex() { return this.featureIndex; } public final int hashCode() { // Byte code:
/*     */         //   0: aload_0
/*     */         //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/biome/FeatureSorter$1FeatureData;)I
/*     */         //   6: ireturn
/*     */         // Line number table:
/*     */         //   Java source line number -> byte code offset
/*     */         //   #42	-> 0
/*     */         // Local variable table:
/*     */         //   start	length	slot	name	descriptor
/*     */         //   0	7	0	this	Lnet/minecraft/world/level/biome/FeatureSorter$1FeatureData; } public final boolean equals(Object $$0) { // Byte code:
/*     */         //   0: aload_0
/*     */         //   1: aload_1
/*     */         //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/biome/FeatureSorter$1FeatureData;Ljava/lang/Object;)Z
/*     */         //   7: ireturn
/*     */         // Line number table:
/*     */         //   Java source line number -> byte code offset
/*     */         //   #42	-> 0
/*     */         // Local variable table:
/*     */         //   start	length	slot	name	descriptor
/*     */         //   0	8	0	this	Lnet/minecraft/world/level/biome/FeatureSorter$1FeatureData;
/*  42 */         //   0	8	1	$$0	Ljava/lang/Object; } public int step() { return this.step; } public PlacedFeature feature() { return this.feature; } };
/*  43 */     Comparator<FeatureData> $$5 = Comparator.<FeatureData>comparingInt(FeatureData::step).thenComparingInt(FeatureData::featureIndex);
/*     */     
/*  45 */     Map<FeatureData, Set<FeatureData>> $$6 = new TreeMap<>($$5);
/*     */     
/*  47 */     int $$7 = 0;
/*  48 */     for (T $$8 : $$0) {
/*  49 */       List<FeatureData> $$9 = Lists.newArrayList();
/*  50 */       List<HolderSet<PlacedFeature>> $$10 = $$1.apply($$8);
/*  51 */       $$7 = Math.max($$7, $$10.size());
/*  52 */       for (int $$11 = 0; $$11 < $$10.size(); $$11++) {
/*  53 */         for (Holder<PlacedFeature> $$12 : $$10.get($$11)) {
/*  54 */           PlacedFeature $$13 = (PlacedFeature)$$12.value();
/*  55 */           $$9.add(new FeatureData(object2IntOpenHashMap.computeIfAbsent($$13, $$1 -> $$0.getAndIncrement()), $$11, $$13));
/*     */         } 
/*     */       } 
/*  58 */       for (int $$14 = 0; $$14 < $$9.size(); $$14++) {
/*  59 */         Set<FeatureData> $$15 = $$6.computeIfAbsent($$9.get($$14), $$1 -> new TreeSet($$0));
/*  60 */         if ($$14 < $$9.size() - 1) {
/*  61 */           $$15.add($$9.get($$14 + 1));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  66 */     Set<FeatureData> $$16 = new TreeSet<>($$5);
/*  67 */     Set<FeatureData> $$17 = new TreeSet<>($$5);
/*  68 */     List<FeatureData> $$18 = Lists.newArrayList();
/*     */     
/*  70 */     for (FeatureData $$19 : $$6.keySet()) {
/*  71 */       if (!$$17.isEmpty()) {
/*  72 */         throw new IllegalStateException("You somehow broke the universe; DFS bork (iteration finished with non-empty in-progress vertex set");
/*     */       }
/*  74 */       if ($$16.contains($$19)) {
/*     */         continue;
/*     */       }
/*     */       
/*  78 */       Objects.requireNonNull($$18); if (Graph.depthFirstSearch($$6, $$16, $$17, $$18::add, $$19)) {
/*  79 */         if ($$2) {
/*  80 */           int $$21; List<T> $$20 = new ArrayList<>($$0);
/*     */ 
/*     */           
/*     */           do {
/*  84 */             $$21 = $$20.size();
/*  85 */             ListIterator<T> $$22 = $$20.listIterator();
/*  86 */             while ($$22.hasNext()) {
/*  87 */               T $$23 = $$22.next();
/*  88 */               $$22.remove();
/*     */               try {
/*  90 */                 buildFeaturesPerStep($$20, $$1, false);
/*  91 */               } catch (IllegalStateException $$24) {
/*     */                 continue;
/*     */               } 
/*     */               
/*  95 */               $$22.add($$23);
/*     */             } 
/*  97 */           } while ($$21 != $$20.size());
/*     */           
/*  99 */           throw new IllegalStateException("Feature order cycle found, involved sources: " + $$20);
/*     */         } 
/*     */         
/* 102 */         throw new IllegalStateException("Feature order cycle found");
/*     */       } 
/*     */     } 
/*     */     
/* 106 */     Collections.reverse($$18);
/*     */     
/* 108 */     ImmutableList.Builder<StepFeatureData> $$25 = ImmutableList.builder();
/* 109 */     for (int $$26 = 0; $$26 < $$7; $$26++) {
/* 110 */       int $$27 = $$26;
/* 111 */       List<PlacedFeature> $$28 = (List<PlacedFeature>)$$18.stream().filter($$1 -> ($$1.step() == $$0)).map(FeatureData::feature).collect(Collectors.toList());
/* 112 */       $$25.add(new StepFeatureData($$28));
/*     */     } 
/* 114 */     return (List<StepFeatureData>)$$25.build();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\biome\FeatureSorter.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */