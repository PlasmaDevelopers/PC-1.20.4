/*     */ package net.minecraft.data.models.blockstates;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.function.BiFunction;
/*     */ import java.util.function.Function;
/*     */ import java.util.stream.Collectors;
/*     */ import java.util.stream.Stream;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ 
/*     */ public abstract class PropertyDispatch {
/*  17 */   private final Map<Selector, List<Variant>> values = Maps.newHashMap();
/*     */   
/*     */   protected void putValue(Selector $$0, List<Variant> $$1) {
/*  20 */     List<Variant> $$2 = this.values.put($$0, $$1);
/*  21 */     if ($$2 != null) {
/*  22 */       throw new IllegalStateException("Value " + $$0 + " is already defined");
/*     */     }
/*     */   }
/*     */   
/*     */   Map<Selector, List<Variant>> getEntries() {
/*  27 */     verifyComplete();
/*  28 */     return (Map<Selector, List<Variant>>)ImmutableMap.copyOf(this.values);
/*     */   }
/*     */   
/*     */   private void verifyComplete() {
/*  32 */     List<Property<?>> $$0 = getDefinedProperties();
/*  33 */     Stream<Selector> $$1 = Stream.of(Selector.empty());
/*  34 */     for (Property<?> $$2 : $$0) {
/*  35 */       $$1 = $$1.flatMap($$1 -> { Objects.requireNonNull($$1); return $$0.getAllValues().map($$1::extend);
/*     */           });
/*  37 */     }  List<Selector> $$3 = (List<Selector>)$$1.filter($$0 -> !this.values.containsKey($$0)).collect(Collectors.toList());
/*  38 */     if (!$$3.isEmpty()) {
/*  39 */       throw new IllegalStateException("Missing definition for properties: " + $$3);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T1 extends Comparable<T1>> C1<T1> property(Property<T1> $$0) {
/*  46 */     return new C1<>($$0);
/*     */   }
/*     */   
/*     */   public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>> C2<T1, T2> properties(Property<T1> $$0, Property<T2> $$1) {
/*  50 */     return new C2<>($$0, $$1);
/*     */   }
/*     */   
/*     */   public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>> C3<T1, T2, T3> properties(Property<T1> $$0, Property<T2> $$1, Property<T3> $$2) {
/*  54 */     return new C3<>($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>, T4 extends Comparable<T4>> C4<T1, T2, T3, T4> properties(Property<T1> $$0, Property<T2> $$1, Property<T3> $$2, Property<T4> $$3) {
/*  58 */     return new C4<>($$0, $$1, $$2, $$3);
/*     */   }
/*     */   
/*     */   public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>, T4 extends Comparable<T4>, T5 extends Comparable<T5>> C5<T1, T2, T3, T4, T5> properties(Property<T1> $$0, Property<T2> $$1, Property<T3> $$2, Property<T4> $$3, Property<T5> $$4) {
/*  62 */     return new C5<>($$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */   abstract List<Property<?>> getDefinedProperties();
/*     */   
/*     */   public static class C1<T1 extends Comparable<T1>> extends PropertyDispatch { private final Property<T1> property1;
/*     */     
/*     */     C1(Property<T1> $$0) {
/*  69 */       this.property1 = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public List<Property<?>> getDefinedProperties() {
/*  74 */       return (List<Property<?>>)ImmutableList.of(this.property1);
/*     */     }
/*     */     
/*     */     public C1<T1> select(T1 $$0, List<Variant> $$1) {
/*  78 */       Selector $$2 = Selector.of((Property.Value<?>[])new Property.Value[] { this.property1
/*  79 */             .value((Comparable)$$0) });
/*     */       
/*  81 */       putValue($$2, $$1);
/*  82 */       return this;
/*     */     }
/*     */     
/*     */     public C1<T1> select(T1 $$0, Variant $$1) {
/*  86 */       return select($$0, Collections.singletonList($$1));
/*     */     }
/*     */     
/*     */     public PropertyDispatch generate(Function<T1, Variant> $$0) {
/*  90 */       this.property1.getPossibleValues().forEach($$1 -> select((T1)$$1, $$0.apply($$1)));
/*     */ 
/*     */       
/*  93 */       return this;
/*     */     }
/*     */     
/*     */     public PropertyDispatch generateList(Function<T1, List<Variant>> $$0) {
/*  97 */       this.property1.getPossibleValues().forEach($$1 -> select((T1)$$1, $$0.apply($$1)));
/*     */ 
/*     */       
/* 100 */       return this;
/*     */     } }
/*     */ 
/*     */   
/*     */   public static class C2<T1 extends Comparable<T1>, T2 extends Comparable<T2>> extends PropertyDispatch {
/*     */     private final Property<T1> property1;
/*     */     private final Property<T2> property2;
/*     */     
/*     */     C2(Property<T1> $$0, Property<T2> $$1) {
/* 109 */       this.property1 = $$0;
/* 110 */       this.property2 = $$1;
/*     */     }
/*     */ 
/*     */     
/*     */     public List<Property<?>> getDefinedProperties() {
/* 115 */       return (List<Property<?>>)ImmutableList.of(this.property1, this.property2);
/*     */     }
/*     */     
/*     */     public C2<T1, T2> select(T1 $$0, T2 $$1, List<Variant> $$2) {
/* 119 */       Selector $$3 = Selector.of((Property.Value<?>[])new Property.Value[] { this.property1
/* 120 */             .value((Comparable)$$0), this.property2
/* 121 */             .value((Comparable)$$1) });
/*     */       
/* 123 */       putValue($$3, $$2);
/* 124 */       return this;
/*     */     }
/*     */     
/*     */     public C2<T1, T2> select(T1 $$0, T2 $$1, Variant $$2) {
/* 128 */       return select($$0, $$1, Collections.singletonList($$2));
/*     */     }
/*     */     
/*     */     public PropertyDispatch generate(BiFunction<T1, T2, Variant> $$0) {
/* 132 */       this.property1.getPossibleValues().forEach($$1 -> this.property2.getPossibleValues().forEach(()));
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 137 */       return this;
/*     */     }
/*     */     
/*     */     public PropertyDispatch generateList(BiFunction<T1, T2, List<Variant>> $$0) {
/* 141 */       this.property1.getPossibleValues().forEach($$1 -> this.property2.getPossibleValues().forEach(()));
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 146 */       return this;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class C3<T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>> extends PropertyDispatch {
/*     */     private final Property<T1> property1;
/*     */     private final Property<T2> property2;
/*     */     private final Property<T3> property3;
/*     */     
/*     */     C3(Property<T1> $$0, Property<T2> $$1, Property<T3> $$2) {
/* 156 */       this.property1 = $$0;
/* 157 */       this.property2 = $$1;
/* 158 */       this.property3 = $$2;
/*     */     }
/*     */ 
/*     */     
/*     */     public List<Property<?>> getDefinedProperties() {
/* 163 */       return (List<Property<?>>)ImmutableList.of(this.property1, this.property2, this.property3);
/*     */     }
/*     */     
/*     */     public C3<T1, T2, T3> select(T1 $$0, T2 $$1, T3 $$2, List<Variant> $$3) {
/* 167 */       Selector $$4 = Selector.of((Property.Value<?>[])new Property.Value[] { this.property1
/* 168 */             .value((Comparable)$$0), this.property2
/* 169 */             .value((Comparable)$$1), this.property3
/* 170 */             .value((Comparable)$$2) });
/*     */       
/* 172 */       putValue($$4, $$3);
/* 173 */       return this;
/*     */     }
/*     */     
/*     */     public C3<T1, T2, T3> select(T1 $$0, T2 $$1, T3 $$2, Variant $$3) {
/* 177 */       return select($$0, $$1, $$2, Collections.singletonList($$3));
/*     */     }
/*     */     
/*     */     public PropertyDispatch generate(PropertyDispatch.TriFunction<T1, T2, T3, Variant> $$0) {
/* 181 */       this.property1.getPossibleValues().forEach($$1 -> this.property2.getPossibleValues().forEach(()));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 188 */       return this;
/*     */     }
/*     */     
/*     */     public PropertyDispatch generateList(PropertyDispatch.TriFunction<T1, T2, T3, List<Variant>> $$0) {
/* 192 */       this.property1.getPossibleValues().forEach($$1 -> this.property2.getPossibleValues().forEach(()));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 199 */       return this;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class C4<T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>, T4 extends Comparable<T4>> extends PropertyDispatch {
/*     */     private final Property<T1> property1;
/*     */     private final Property<T2> property2;
/*     */     private final Property<T3> property3;
/*     */     private final Property<T4> property4;
/*     */     
/*     */     C4(Property<T1> $$0, Property<T2> $$1, Property<T3> $$2, Property<T4> $$3) {
/* 210 */       this.property1 = $$0;
/* 211 */       this.property2 = $$1;
/* 212 */       this.property3 = $$2;
/* 213 */       this.property4 = $$3;
/*     */     }
/*     */ 
/*     */     
/*     */     public List<Property<?>> getDefinedProperties() {
/* 218 */       return (List<Property<?>>)ImmutableList.of(this.property1, this.property2, this.property3, this.property4);
/*     */     }
/*     */     
/*     */     public C4<T1, T2, T3, T4> select(T1 $$0, T2 $$1, T3 $$2, T4 $$3, List<Variant> $$4) {
/* 222 */       Selector $$5 = Selector.of((Property.Value<?>[])new Property.Value[] { this.property1
/* 223 */             .value((Comparable)$$0), this.property2
/* 224 */             .value((Comparable)$$1), this.property3
/* 225 */             .value((Comparable)$$2), this.property4
/* 226 */             .value((Comparable)$$3) });
/*     */       
/* 228 */       putValue($$5, $$4);
/* 229 */       return this;
/*     */     }
/*     */     
/*     */     public C4<T1, T2, T3, T4> select(T1 $$0, T2 $$1, T3 $$2, T4 $$3, Variant $$4) {
/* 233 */       return select($$0, $$1, $$2, $$3, Collections.singletonList($$4));
/*     */     }
/*     */     
/*     */     public PropertyDispatch generate(PropertyDispatch.QuadFunction<T1, T2, T3, T4, Variant> $$0) {
/* 237 */       this.property1.getPossibleValues().forEach($$1 -> this.property2.getPossibleValues().forEach(()));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 246 */       return this;
/*     */     }
/*     */     
/*     */     public PropertyDispatch generateList(PropertyDispatch.QuadFunction<T1, T2, T3, T4, List<Variant>> $$0) {
/* 250 */       this.property1.getPossibleValues().forEach($$1 -> this.property2.getPossibleValues().forEach(()));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 259 */       return this;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class C5<T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>, T4 extends Comparable<T4>, T5 extends Comparable<T5>> extends PropertyDispatch {
/*     */     private final Property<T1> property1;
/*     */     private final Property<T2> property2;
/*     */     private final Property<T3> property3;
/*     */     private final Property<T4> property4;
/*     */     private final Property<T5> property5;
/*     */     
/*     */     C5(Property<T1> $$0, Property<T2> $$1, Property<T3> $$2, Property<T4> $$3, Property<T5> $$4) {
/* 271 */       this.property1 = $$0;
/* 272 */       this.property2 = $$1;
/* 273 */       this.property3 = $$2;
/* 274 */       this.property4 = $$3;
/* 275 */       this.property5 = $$4;
/*     */     }
/*     */ 
/*     */     
/*     */     public List<Property<?>> getDefinedProperties() {
/* 280 */       return (List<Property<?>>)ImmutableList.of(this.property1, this.property2, this.property3, this.property4, this.property5);
/*     */     }
/*     */     
/*     */     public C5<T1, T2, T3, T4, T5> select(T1 $$0, T2 $$1, T3 $$2, T4 $$3, T5 $$4, List<Variant> $$5) {
/* 284 */       Selector $$6 = Selector.of((Property.Value<?>[])new Property.Value[] { this.property1
/* 285 */             .value((Comparable)$$0), this.property2
/* 286 */             .value((Comparable)$$1), this.property3
/* 287 */             .value((Comparable)$$2), this.property4
/* 288 */             .value((Comparable)$$3), this.property5
/* 289 */             .value((Comparable)$$4) });
/*     */       
/* 291 */       putValue($$6, $$5);
/* 292 */       return this;
/*     */     }
/*     */     
/*     */     public C5<T1, T2, T3, T4, T5> select(T1 $$0, T2 $$1, T3 $$2, T4 $$3, T5 $$4, Variant $$5) {
/* 296 */       return select($$0, $$1, $$2, $$3, $$4, Collections.singletonList($$5));
/*     */     }
/*     */     
/*     */     public PropertyDispatch generate(PropertyDispatch.PentaFunction<T1, T2, T3, T4, T5, Variant> $$0) {
/* 300 */       this.property1.getPossibleValues().forEach($$1 -> this.property2.getPossibleValues().forEach(()));
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
/* 311 */       return this;
/*     */     }
/*     */     
/*     */     public PropertyDispatch generateList(PropertyDispatch.PentaFunction<T1, T2, T3, T4, T5, List<Variant>> $$0) {
/* 315 */       this.property1.getPossibleValues().forEach($$1 -> this.property2.getPossibleValues().forEach(()));
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
/* 326 */       return this;
/*     */     }
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface PentaFunction<P1, P2, P3, P4, P5, R> {
/*     */     R apply(P1 param1P1, P2 param1P2, P3 param1P3, P4 param1P4, P5 param1P5);
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface QuadFunction<P1, P2, P3, P4, R> {
/*     */     R apply(P1 param1P1, P2 param1P2, P3 param1P3, P4 param1P4);
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface TriFunction<P1, P2, P3, R> {
/*     */     R apply(P1 param1P1, P2 param1P2, P3 param1P3);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\models\blockstates\PropertyDispatch.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */