/*     */ package net.minecraft.world.level.block.state;
/*     */ 
/*     */ import com.google.common.base.MoreObjects;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.google.common.collect.ImmutableSortedMap;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.UnmodifiableIterator;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.Decoder;
/*     */ import com.mojang.serialization.Encoder;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.regex.Pattern;
/*     */ import java.util.stream.Collectors;
/*     */ import java.util.stream.Stream;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ 
/*     */ public class StateDefinition<O, S extends StateHolder<O, S>> {
/*  28 */   static final Pattern NAME_PATTERN = Pattern.compile("^[a-z0-9_]+$");
/*     */   
/*     */   private final O owner;
/*     */   private final ImmutableSortedMap<String, Property<?>> propertiesByName;
/*     */   private final ImmutableList<S> states;
/*     */   
/*     */   protected StateDefinition(Function<O, S> $$0, O $$1, Factory<O, S> $$2, Map<String, Property<?>> $$3) {
/*  35 */     this.owner = $$1;
/*  36 */     this.propertiesByName = ImmutableSortedMap.copyOf($$3);
/*     */     
/*  38 */     Supplier<S> $$4 = () -> (StateHolder)$$0.apply($$1);
/*  39 */     MapCodec<S> $$5 = MapCodec.of(Encoder.empty(), Decoder.unit($$4));
/*  40 */     for (UnmodifiableIterator<Map.Entry<String, Property<?>>> unmodifiableIterator = this.propertiesByName.entrySet().iterator(); unmodifiableIterator.hasNext(); ) { Map.Entry<String, Property<?>> $$6 = unmodifiableIterator.next();
/*  41 */       $$5 = appendPropertyCodec($$5, $$4, $$6.getKey(), (Property<Comparable>)$$6.getValue()); }
/*     */ 
/*     */     
/*  44 */     MapCodec<S> $$7 = $$5;
/*     */ 
/*     */     
/*  47 */     Map<Map<Property<?>, Comparable<?>>, S> $$8 = Maps.newLinkedHashMap();
/*  48 */     List<S> $$9 = Lists.newArrayList();
/*     */     
/*  50 */     Stream<List<Pair<Property<?>, Comparable<?>>>> $$10 = Stream.of(Collections.emptyList());
/*  51 */     for (UnmodifiableIterator<Property> unmodifiableIterator1 = this.propertiesByName.values().iterator(); unmodifiableIterator1.hasNext(); ) { Property<?> $$11 = unmodifiableIterator1.next();
/*  52 */       $$10 = $$10.flatMap($$1 -> $$0.getPossibleValues().stream().map(())); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  59 */     $$10.forEach($$5 -> {
/*     */           ImmutableMap<Property<?>, Comparable<?>> $$6 = (ImmutableMap<Property<?>, Comparable<?>>)$$5.stream().collect(ImmutableMap.toImmutableMap(Pair::getFirst, Pair::getSecond));
/*     */           
/*     */           StateHolder stateHolder = $$0.create($$1, $$6, $$2);
/*     */           
/*     */           $$3.put($$6, stateHolder);
/*     */           $$4.add(stateHolder);
/*     */         });
/*  67 */     for (StateHolder stateHolder : $$9) {
/*  68 */       stateHolder.populateNeighbours($$8);
/*     */     }
/*     */     
/*  71 */     this.states = ImmutableList.copyOf($$9);
/*     */   }
/*     */   
/*     */   private static <S extends StateHolder<?, S>, T extends Comparable<T>> MapCodec<S> appendPropertyCodec(MapCodec<S> $$0, Supplier<S> $$1, String $$2, Property<T> $$3) {
/*  75 */     return Codec.mapPair($$0, $$3
/*     */         
/*  77 */         .valueCodec().fieldOf($$2).orElseGet($$0 -> { 
/*  78 */           }() -> $$0.value($$1.get()))).xmap($$1 -> (StateHolder)((StateHolder)$$1.getFirst()).setValue($$0, ((Property.Value)$$1.getSecond()).value()), $$1 -> Pair.of($$1, $$0.value($$1)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ImmutableList<S> getPossibleStates() {
/*  85 */     return this.states;
/*     */   }
/*     */   
/*     */   public S any() {
/*  89 */     return (S)this.states.get(0);
/*     */   }
/*     */   
/*     */   public O getOwner() {
/*  93 */     return this.owner;
/*     */   }
/*     */   
/*     */   public Collection<Property<?>> getProperties() {
/*  97 */     return (Collection<Property<?>>)this.propertiesByName.values();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 102 */     return MoreObjects.toStringHelper(this)
/* 103 */       .add("block", this.owner)
/* 104 */       .add("properties", this.propertiesByName.values().stream().map(Property::getName).collect(Collectors.toList()))
/* 105 */       .toString();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Property<?> getProperty(String $$0) {
/* 110 */     return (Property)this.propertiesByName.get($$0);
/*     */   }
/*     */   
/*     */   public static interface Factory<O, S> {
/*     */     S create(O param1O, ImmutableMap<Property<?>, Comparable<?>> param1ImmutableMap, MapCodec<S> param1MapCodec);
/*     */   }
/*     */   
/*     */   public static class Builder<O, S extends StateHolder<O, S>> {
/*     */     private final O owner;
/* 119 */     private final Map<String, Property<?>> properties = Maps.newHashMap();
/*     */     
/*     */     public Builder(O $$0) {
/* 122 */       this.owner = $$0;
/*     */     }
/*     */     
/*     */     public Builder<O, S> add(Property<?>... $$0) {
/* 126 */       for (Property<?> $$1 : $$0) {
/* 127 */         validateProperty($$1);
/* 128 */         this.properties.put($$1.getName(), $$1);
/*     */       } 
/* 130 */       return this;
/*     */     }
/*     */     
/*     */     private <T extends Comparable<T>> void validateProperty(Property<T> $$0) {
/* 134 */       String $$1 = $$0.getName();
/* 135 */       if (!StateDefinition.NAME_PATTERN.matcher($$1).matches()) {
/* 136 */         throw new IllegalArgumentException("" + this.owner + " has invalidly named property: " + this.owner);
/*     */       }
/*     */       
/* 139 */       Collection<T> $$2 = $$0.getPossibleValues();
/* 140 */       if ($$2.size() <= 1) {
/* 141 */         throw new IllegalArgumentException("" + this.owner + " attempted use property " + this.owner + " with <= 1 possible values");
/*     */       }
/*     */       
/* 144 */       for (Comparable comparable : $$2) {
/* 145 */         String $$4 = $$0.getName(comparable);
/* 146 */         if (!StateDefinition.NAME_PATTERN.matcher($$4).matches()) {
/* 147 */           throw new IllegalArgumentException("" + this.owner + " has property: " + this.owner + " with invalidly named value: " + $$1);
/*     */         }
/*     */       } 
/*     */       
/* 151 */       if (this.properties.containsKey($$1)) {
/* 152 */         throw new IllegalArgumentException("" + this.owner + " has duplicate property: " + this.owner);
/*     */       }
/*     */     }
/*     */     
/*     */     public StateDefinition<O, S> create(Function<O, S> $$0, StateDefinition.Factory<O, S> $$1) {
/* 157 */       return new StateDefinition<>($$0, this.owner, $$1, this.properties);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\state\StateDefinition.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */