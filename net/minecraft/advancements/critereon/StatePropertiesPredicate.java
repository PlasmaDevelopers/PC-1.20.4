/*     */ package net.minecraft.advancements.critereon;
/*     */ 
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.util.Either;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import net.minecraft.util.ExtraCodecs;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.StateHolder;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ 
/*     */ public final class StatePropertiesPredicate extends Record {
/*     */   private final List<PropertyMatcher> properties;
/*     */   private static final Codec<List<PropertyMatcher>> PROPERTIES_CODEC;
/*     */   
/*  19 */   public StatePropertiesPredicate(List<PropertyMatcher> $$0) { this.properties = $$0; } public final String toString() { // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/critereon/StatePropertiesPredicate;)Ljava/lang/String;
/*     */     //   6: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #19	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*  19 */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/StatePropertiesPredicate; } public List<PropertyMatcher> properties() { return this.properties; } public final int hashCode() { // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/critereon/StatePropertiesPredicate;)I
/*     */     //   6: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #19	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/StatePropertiesPredicate; } public final boolean equals(Object $$0) { // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/critereon/StatePropertiesPredicate;Ljava/lang/Object;)Z
/*     */     //   7: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #19	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	8	0	this	Lnet/minecraft/advancements/critereon/StatePropertiesPredicate;
/*     */     //   0	8	1	$$0	Ljava/lang/Object; }
/*  20 */   static { PROPERTIES_CODEC = Codec.unboundedMap((Codec)Codec.STRING, ValueMatcher.CODEC).xmap($$0 -> $$0.entrySet().stream().map(()).toList(), $$0 -> (Map)$$0.stream().collect(Collectors.toMap(PropertyMatcher::name, PropertyMatcher::valueMatcher))); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  25 */   public static final Codec<StatePropertiesPredicate> CODEC = PROPERTIES_CODEC.xmap(StatePropertiesPredicate::new, StatePropertiesPredicate::properties);
/*     */   private static final class PropertyMatcher extends Record { private final String name; private final StatePropertiesPredicate.ValueMatcher valueMatcher;
/*  27 */     PropertyMatcher(String $$0, StatePropertiesPredicate.ValueMatcher $$1) { this.name = $$0; this.valueMatcher = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/critereon/StatePropertiesPredicate$PropertyMatcher;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #27	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*  27 */       //   0	7	0	this	Lnet/minecraft/advancements/critereon/StatePropertiesPredicate$PropertyMatcher; } public String name() { return this.name; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/critereon/StatePropertiesPredicate$PropertyMatcher;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #27	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/advancements/critereon/StatePropertiesPredicate$PropertyMatcher; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/critereon/StatePropertiesPredicate$PropertyMatcher;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #27	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/advancements/critereon/StatePropertiesPredicate$PropertyMatcher;
/*  27 */       //   0	8	1	$$0	Ljava/lang/Object; } public StatePropertiesPredicate.ValueMatcher valueMatcher() { return this.valueMatcher; }
/*     */      public <S extends StateHolder<?, S>> boolean match(StateDefinition<?, S> $$0, S $$1) {
/*  29 */       Property<?> $$2 = $$0.getProperty(this.name);
/*  30 */       return ($$2 != null && this.valueMatcher.match((StateHolder<?, ?>)$$1, $$2));
/*     */     }
/*     */     
/*     */     public Optional<String> checkState(StateDefinition<?, ?> $$0) {
/*  34 */       Property<?> $$1 = $$0.getProperty(this.name);
/*  35 */       return ($$1 != null) ? Optional.<String>empty() : Optional.<String>of(this.name);
/*     */     } }
/*     */   private static interface ValueMatcher { public static final Codec<ValueMatcher> CODEC;
/*     */     
/*     */     static {
/*  40 */       CODEC = Codec.either(StatePropertiesPredicate.ExactMatcher.CODEC, StatePropertiesPredicate.RangedMatcher.CODEC).xmap($$0 -> (ValueMatcher)$$0.map((), ()), $$0 -> {
/*     */             if ($$0 instanceof StatePropertiesPredicate.ExactMatcher) {
/*     */               StatePropertiesPredicate.ExactMatcher $$1 = (StatePropertiesPredicate.ExactMatcher)$$0;
/*     */               return Either.left($$1);
/*     */             } 
/*     */             if ($$0 instanceof StatePropertiesPredicate.RangedMatcher) {
/*     */               StatePropertiesPredicate.RangedMatcher $$2 = (StatePropertiesPredicate.RangedMatcher)$$0;
/*     */               return Either.right($$2);
/*     */             } 
/*     */             throw new UnsupportedOperationException();
/*     */           });
/*     */     }
/*     */     <T extends Comparable<T>> boolean match(StateHolder<?, ?> param1StateHolder, Property<T> param1Property); }
/*     */   private static final class ExactMatcher extends Record implements ValueMatcher { private final String value;
/*     */     
/*  55 */     ExactMatcher(String $$0) { this.value = $$0; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/critereon/StatePropertiesPredicate$ExactMatcher;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #55	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/advancements/critereon/StatePropertiesPredicate$ExactMatcher; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/critereon/StatePropertiesPredicate$ExactMatcher;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #55	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/advancements/critereon/StatePropertiesPredicate$ExactMatcher; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/critereon/StatePropertiesPredicate$ExactMatcher;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #55	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/advancements/critereon/StatePropertiesPredicate$ExactMatcher;
/*  55 */       //   0	8	1	$$0	Ljava/lang/Object; } public String value() { return this.value; }
/*  56 */      public static final Codec<ExactMatcher> CODEC = Codec.STRING.xmap(ExactMatcher::new, ExactMatcher::value);
/*     */ 
/*     */     
/*     */     public <T extends Comparable<T>> boolean match(StateHolder<?, ?> $$0, Property<T> $$1) {
/*  60 */       Comparable<Comparable> comparable = $$0.getValue($$1);
/*  61 */       Optional<T> $$3 = $$1.getValue(this.value);
/*  62 */       return ($$3.isPresent() && comparable.compareTo((Comparable)$$3.get()) == 0);
/*     */     } }
/*     */   private static final class RangedMatcher extends Record implements ValueMatcher { private final Optional<String> minValue; private final Optional<String> maxValue; public static final Codec<RangedMatcher> CODEC;
/*     */     
/*  66 */     private RangedMatcher(Optional<String> $$0, Optional<String> $$1) { this.minValue = $$0; this.maxValue = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/critereon/StatePropertiesPredicate$RangedMatcher;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #66	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/advancements/critereon/StatePropertiesPredicate$RangedMatcher; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/critereon/StatePropertiesPredicate$RangedMatcher;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #66	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/advancements/critereon/StatePropertiesPredicate$RangedMatcher; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/critereon/StatePropertiesPredicate$RangedMatcher;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #66	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/advancements/critereon/StatePropertiesPredicate$RangedMatcher;
/*  66 */       //   0	8	1	$$0	Ljava/lang/Object; } public Optional<String> minValue() { return this.minValue; } public Optional<String> maxValue() { return this.maxValue; } static {
/*  67 */       CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.strictOptionalField((Codec)Codec.STRING, "min").forGetter(RangedMatcher::minValue), (App)ExtraCodecs.strictOptionalField((Codec)Codec.STRING, "max").forGetter(RangedMatcher::maxValue)).apply((Applicative)$$0, RangedMatcher::new));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public <T extends Comparable<T>> boolean match(StateHolder<?, ?> $$0, Property<T> $$1) {
/*  74 */       Comparable<Comparable> comparable = $$0.getValue($$1);
/*     */       
/*  76 */       if (this.minValue.isPresent()) {
/*  77 */         Optional<T> $$3 = $$1.getValue(this.minValue.get());
/*  78 */         if ($$3.isEmpty() || comparable.compareTo((Comparable)$$3.get()) < 0) {
/*  79 */           return false;
/*     */         }
/*     */       } 
/*     */       
/*  83 */       if (this.maxValue.isPresent()) {
/*  84 */         Optional<T> $$4 = $$1.getValue(this.maxValue.get());
/*  85 */         if ($$4.isEmpty() || comparable.compareTo((Comparable)$$4.get()) > 0) {
/*  86 */           return false;
/*     */         }
/*     */       } 
/*     */       
/*  90 */       return true;
/*     */     } }
/*     */ 
/*     */   
/*     */   public <S extends StateHolder<?, S>> boolean matches(StateDefinition<?, S> $$0, S $$1) {
/*  95 */     for (PropertyMatcher $$2 : this.properties) {
/*  96 */       if (!$$2.match($$0, $$1)) {
/*  97 */         return false;
/*     */       }
/*     */     } 
/* 100 */     return true;
/*     */   }
/*     */   
/*     */   public boolean matches(BlockState $$0) {
/* 104 */     return matches($$0.getBlock().getStateDefinition(), $$0);
/*     */   }
/*     */   
/*     */   public boolean matches(FluidState $$0) {
/* 108 */     return matches($$0.getType().getStateDefinition(), $$0);
/*     */   }
/*     */   
/*     */   public Optional<String> checkState(StateDefinition<?, ?> $$0) {
/* 112 */     for (PropertyMatcher $$1 : this.properties) {
/* 113 */       Optional<String> $$2 = $$1.checkState($$0);
/* 114 */       if ($$2.isPresent()) {
/* 115 */         return $$2;
/*     */       }
/*     */     } 
/* 118 */     return Optional.empty();
/*     */   }
/*     */   
/*     */   public static class Builder {
/* 122 */     private final ImmutableList.Builder<StatePropertiesPredicate.PropertyMatcher> matchers = ImmutableList.builder();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static Builder properties() {
/* 128 */       return new Builder();
/*     */     }
/*     */     
/*     */     public Builder hasProperty(Property<?> $$0, String $$1) {
/* 132 */       this.matchers.add(new StatePropertiesPredicate.PropertyMatcher($$0.getName(), new StatePropertiesPredicate.ExactMatcher($$1)));
/* 133 */       return this;
/*     */     }
/*     */     
/*     */     public Builder hasProperty(Property<Integer> $$0, int $$1) {
/* 137 */       return hasProperty($$0, Integer.toString($$1));
/*     */     }
/*     */     
/*     */     public Builder hasProperty(Property<Boolean> $$0, boolean $$1) {
/* 141 */       return hasProperty($$0, Boolean.toString($$1));
/*     */     }
/*     */     
/*     */     public <T extends Comparable<T> & StringRepresentable> Builder hasProperty(Property<T> $$0, T $$1) {
/* 145 */       return hasProperty($$0, ((StringRepresentable)$$1).getSerializedName());
/*     */     }
/*     */     
/*     */     public Optional<StatePropertiesPredicate> build() {
/* 149 */       return Optional.of(new StatePropertiesPredicate((List<StatePropertiesPredicate.PropertyMatcher>)this.matchers.build()));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\StatePropertiesPredicate.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */