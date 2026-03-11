/*     */ package net.minecraft.world.level.block.state;
/*     */ 
/*     */ import com.google.common.collect.ArrayTable;
/*     */ import com.google.common.collect.HashBasedTable;
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Table;
/*     */ import com.google.common.collect.UnmodifiableIterator;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import java.util.function.Function;
/*     */ import java.util.stream.Collectors;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ 
/*     */ public abstract class StateHolder<O, S> {
/*     */   public static final String NAME_TAG = "Name";
/*     */   public static final String PROPERTIES_TAG = "Properties";
/*     */   
/*  25 */   private static final Function<Map.Entry<Property<?>, Comparable<?>>, String> PROPERTY_ENTRY_TO_STRING_FUNCTION = new Function<Map.Entry<Property<?>, Comparable<?>>, String>()
/*     */     {
/*     */       public String apply(@Nullable Map.Entry<Property<?>, Comparable<?>> $$0) {
/*  28 */         if ($$0 == null) {
/*  29 */           return "<NULL>";
/*     */         }
/*     */         
/*  32 */         Property<?> $$1 = $$0.getKey();
/*  33 */         return $$1.getName() + "=" + $$1.getName();
/*     */       }
/*     */ 
/*     */       
/*     */       private <T extends Comparable<T>> String getName(Property<T> $$0, Comparable<?> $$1) {
/*  38 */         return $$0.getName($$1);
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   protected final O owner;
/*     */   private final ImmutableMap<Property<?>, Comparable<?>> values;
/*     */   private Table<Property<?>, Comparable<?>, S> neighbours;
/*     */   protected final MapCodec<S> propertiesCodec;
/*     */   
/*     */   protected StateHolder(O $$0, ImmutableMap<Property<?>, Comparable<?>> $$1, MapCodec<S> $$2) {
/*  49 */     this.owner = $$0;
/*  50 */     this.values = $$1;
/*  51 */     this.propertiesCodec = $$2;
/*     */   }
/*     */   
/*     */   public <T extends Comparable<T>> S cycle(Property<T> $$0) {
/*  55 */     return setValue($$0, findNextInCollection($$0.getPossibleValues(), getValue($$0)));
/*     */   }
/*     */   
/*     */   protected static <T> T findNextInCollection(Collection<T> $$0, T $$1) {
/*  59 */     Iterator<T> $$2 = $$0.iterator();
/*     */     
/*  61 */     while ($$2.hasNext()) {
/*  62 */       if ($$2.next().equals($$1)) {
/*  63 */         if ($$2.hasNext()) {
/*  64 */           return $$2.next();
/*     */         }
/*  66 */         return $$0.iterator().next();
/*     */       } 
/*     */     } 
/*     */     
/*  70 */     return $$2.next();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  75 */     StringBuilder $$0 = new StringBuilder();
/*  76 */     $$0.append(this.owner);
/*     */     
/*  78 */     if (!getValues().isEmpty()) {
/*  79 */       $$0.append('[');
/*  80 */       $$0.append(getValues().entrySet().stream().<CharSequence>map((Function)PROPERTY_ENTRY_TO_STRING_FUNCTION).collect(Collectors.joining(",")));
/*  81 */       $$0.append(']');
/*     */     } 
/*     */     
/*  84 */     return $$0.toString();
/*     */   }
/*     */   
/*     */   public Collection<Property<?>> getProperties() {
/*  88 */     return Collections.unmodifiableCollection((Collection<? extends Property<?>>)this.values.keySet());
/*     */   }
/*     */   
/*     */   public <T extends Comparable<T>> boolean hasProperty(Property<T> $$0) {
/*  92 */     return this.values.containsKey($$0);
/*     */   }
/*     */   
/*     */   public <T extends Comparable<T>> T getValue(Property<T> $$0) {
/*  96 */     Comparable<?> $$1 = (Comparable)this.values.get($$0);
/*  97 */     if ($$1 == null) {
/*  98 */       throw new IllegalArgumentException("Cannot get property " + $$0 + " as it does not exist in " + this.owner);
/*     */     }
/*     */     
/* 101 */     return (T)$$0.getValueClass().cast($$1);
/*     */   }
/*     */   
/*     */   public <T extends Comparable<T>> Optional<T> getOptionalValue(Property<T> $$0) {
/* 105 */     Comparable<?> $$1 = (Comparable)this.values.get($$0);
/* 106 */     if ($$1 == null) {
/* 107 */       return Optional.empty();
/*     */     }
/*     */     
/* 110 */     return Optional.of((T)$$0.getValueClass().cast($$1));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T extends Comparable<T>, V extends T> S setValue(Property<T> $$0, V $$1) {
/* 120 */     Comparable<?> $$2 = (Comparable)this.values.get($$0);
/* 121 */     if ($$2 == null) {
/* 122 */       throw new IllegalArgumentException("Cannot set property " + $$0 + " as it does not exist in " + this.owner);
/*     */     }
/* 124 */     if ($$2.equals($$1)) {
/* 125 */       return (S)this;
/*     */     }
/*     */     
/* 128 */     S $$3 = (S)this.neighbours.get($$0, $$1);
/* 129 */     if ($$3 == null) {
/* 130 */       throw new IllegalArgumentException("Cannot set property " + $$0 + " to " + $$1 + " on " + this.owner + ", it is not an allowed value");
/*     */     }
/*     */     
/* 133 */     return $$3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T extends Comparable<T>, V extends T> S trySetValue(Property<T> $$0, V $$1) {
/* 143 */     Comparable<?> $$2 = (Comparable)this.values.get($$0);
/* 144 */     if ($$2 == null || $$2.equals($$1)) {
/* 145 */       return (S)this;
/*     */     }
/*     */     
/* 148 */     S $$3 = (S)this.neighbours.get($$0, $$1);
/* 149 */     if ($$3 == null) {
/* 150 */       throw new IllegalArgumentException("Cannot set property " + $$0 + " to " + $$1 + " on " + this.owner + ", it is not an allowed value");
/*     */     }
/*     */     
/* 153 */     return $$3;
/*     */   }
/*     */   
/*     */   public void populateNeighbours(Map<Map<Property<?>, Comparable<?>>, S> $$0) {
/* 157 */     if (this.neighbours != null) {
/* 158 */       throw new IllegalStateException();
/*     */     }
/*     */     
/* 161 */     HashBasedTable hashBasedTable = HashBasedTable.create();
/* 162 */     for (UnmodifiableIterator<Map.Entry<Property<?>, Comparable<?>>> unmodifiableIterator = this.values.entrySet().iterator(); unmodifiableIterator.hasNext(); ) { Map.Entry<Property<?>, Comparable<?>> $$2 = unmodifiableIterator.next();
/* 163 */       Property<?> $$3 = $$2.getKey();
/* 164 */       for (Comparable<?> $$4 : (Iterable<Comparable<?>>)$$3.getPossibleValues()) {
/* 165 */         if (!$$4.equals($$2.getValue())) {
/* 166 */           hashBasedTable.put($$3, $$4, $$0.get(makeNeighbourValues($$3, $$4)));
/*     */         }
/*     */       }  }
/*     */ 
/*     */     
/* 171 */     this.neighbours = hashBasedTable.isEmpty() ? (Table<Property<?>, Comparable<?>, S>)hashBasedTable : (Table<Property<?>, Comparable<?>, S>)ArrayTable.create((Table)hashBasedTable);
/*     */   }
/*     */   
/*     */   private Map<Property<?>, Comparable<?>> makeNeighbourValues(Property<?> $$0, Comparable<?> $$1) {
/* 175 */     Map<Property<?>, Comparable<?>> $$2 = Maps.newHashMap((Map)this.values);
/* 176 */     $$2.put($$0, $$1);
/* 177 */     return $$2;
/*     */   }
/*     */   
/*     */   public ImmutableMap<Property<?>, Comparable<?>> getValues() {
/* 181 */     return this.values;
/*     */   }
/*     */   
/*     */   protected static <O, S extends StateHolder<O, S>> Codec<S> codec(Codec<O> $$0, Function<O, S> $$1) {
/* 185 */     return $$0.dispatch("Name", $$0 -> $$0.owner, $$1 -> {
/*     */           StateHolder stateHolder = $$0.apply($$1);
/*     */           return stateHolder.getValues().isEmpty() ? Codec.unit(stateHolder) : stateHolder.propertiesCodec.codec().optionalFieldOf("Properties").xmap((), Optional::of).codec();
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\state\StateHolder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */