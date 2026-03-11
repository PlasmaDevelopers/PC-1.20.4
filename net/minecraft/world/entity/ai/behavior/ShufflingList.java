/*     */ package net.minecraft.world.entity.ai.behavior;
/*     */ 
/*     */ import com.google.common.collect.Iterators;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.DataResult;
/*     */ import com.mojang.serialization.Dynamic;
/*     */ import com.mojang.serialization.DynamicOps;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Function;
/*     */ import java.util.stream.Stream;
/*     */ import net.minecraft.util.RandomSource;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ShufflingList<U>
/*     */   implements Iterable<U>
/*     */ {
/*     */   protected final List<WeightedEntry<U>> entries;
/*  25 */   private final RandomSource random = RandomSource.create();
/*     */   
/*     */   public ShufflingList() {
/*  28 */     this.entries = Lists.newArrayList();
/*     */   }
/*     */   
/*     */   private ShufflingList(List<WeightedEntry<U>> $$0) {
/*  32 */     this.entries = Lists.newArrayList($$0);
/*     */   }
/*     */   
/*     */   public static <U> Codec<ShufflingList<U>> codec(Codec<U> $$0) {
/*  36 */     return WeightedEntry.<E>codec($$0).listOf().xmap(ShufflingList::new, $$0 -> $$0.entries);
/*     */   }
/*     */   
/*     */   public ShufflingList<U> add(U $$0, int $$1) {
/*  40 */     this.entries.add(new WeightedEntry<>($$0, $$1));
/*  41 */     return this;
/*     */   }
/*     */   
/*     */   public ShufflingList<U> shuffle() {
/*  45 */     this.entries.forEach($$0 -> $$0.setRandom(this.random.nextFloat()));
/*  46 */     this.entries.sort(Comparator.comparingDouble(WeightedEntry::getRandWeight));
/*  47 */     return this;
/*     */   }
/*     */   
/*     */   public Stream<U> stream() {
/*  51 */     return this.entries.stream().map(WeightedEntry::getData);
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<U> iterator() {
/*  56 */     return Iterators.transform(this.entries.iterator(), WeightedEntry::getData);
/*     */   }
/*     */   
/*     */   public static class WeightedEntry<T> {
/*     */     final T data;
/*     */     final int weight;
/*     */     private double randWeight;
/*     */     
/*     */     WeightedEntry(T $$0, int $$1) {
/*  65 */       this.weight = $$1;
/*  66 */       this.data = $$0;
/*     */     }
/*     */     
/*     */     private double getRandWeight() {
/*  70 */       return this.randWeight;
/*     */     }
/*     */     
/*     */     void setRandom(float $$0) {
/*  74 */       this.randWeight = -Math.pow($$0, (1.0F / this.weight));
/*     */     }
/*     */     
/*     */     public T getData() {
/*  78 */       return this.data;
/*     */     }
/*     */     
/*     */     public int getWeight() {
/*  82 */       return this.weight;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/*  87 */       return "" + this.weight + ":" + this.weight;
/*     */     }
/*     */     
/*     */     public static <E> Codec<WeightedEntry<E>> codec(final Codec<E> elementCodec) {
/*  91 */       return new Codec<WeightedEntry<E>>()
/*     */         {
/*     */           public <T> DataResult<Pair<ShufflingList.WeightedEntry<E>, T>> decode(DynamicOps<T> $$0, T $$1) {
/*  94 */             Dynamic<T> $$2 = new Dynamic($$0, $$1);
/*     */             
/*  96 */             Objects.requireNonNull(elementCodec); return $$2.get("data").flatMap(elementCodec::parse)
/*  97 */               .map($$1 -> new ShufflingList.WeightedEntry($$1, $$0.get("weight").asInt(1)))
/*  98 */               .map($$1 -> Pair.of($$1, $$0.empty()));
/*     */           }
/*     */           
/*     */           public <T> DataResult<T> encode(ShufflingList.WeightedEntry<E> $$0, DynamicOps<T> $$1, T $$2)
/*     */           {
/* 103 */             return $$1.mapBuilder()
/* 104 */               .add("weight", $$1.createInt($$0.weight))
/* 105 */               .add("data", elementCodec.encodeStart($$1, $$0.data))
/* 106 */               .build($$2); } }; } } class null implements Codec<WeightedEntry<E>> { public <T> DataResult<T> encode(ShufflingList.WeightedEntry<E> $$0, DynamicOps<T> $$1, T $$2) { return $$1.mapBuilder().add("weight", $$1.createInt($$0.weight)).add("data", elementCodec.encodeStart($$1, $$0.data)).build($$2); }
/*     */     
/*     */     public <T> DataResult<Pair<ShufflingList.WeightedEntry<E>, T>> decode(DynamicOps<T> $$0, T $$1) {
/*     */       Dynamic<T> $$2 = new Dynamic($$0, $$1);
/*     */       Objects.requireNonNull(elementCodec);
/*     */       return $$2.get("data").flatMap(elementCodec::parse).map($$1 -> new ShufflingList.WeightedEntry($$1, $$0.get("weight").asInt(1))).map($$1 -> Pair.of($$1, $$0.empty()));
/*     */     } }
/*     */   public String toString() {
/* 114 */     return "ShufflingList[" + this.entries + "]";
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\ShufflingList.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */