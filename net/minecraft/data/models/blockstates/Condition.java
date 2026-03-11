/*     */ package net.minecraft.data.models.blockstates;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.stream.Collectors;
/*     */ import java.util.stream.Stream;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ 
/*     */ public interface Condition extends Supplier<JsonElement> {
/*     */   void validate(StateDefinition<?, ?> paramStateDefinition);
/*     */   
/*     */   public enum Operation {
/*  21 */     AND("AND"),
/*  22 */     OR("OR");
/*     */     
/*     */     final String id;
/*     */ 
/*     */     
/*     */     Operation(String $$0) {
/*  28 */       this.id = $$0;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class CompositeCondition implements Condition {
/*     */     private final Condition.Operation operation;
/*     */     private final List<Condition> subconditions;
/*     */     
/*     */     CompositeCondition(Condition.Operation $$0, List<Condition> $$1) {
/*  37 */       this.operation = $$0;
/*  38 */       this.subconditions = $$1;
/*     */     }
/*     */ 
/*     */     
/*     */     public void validate(StateDefinition<?, ?> $$0) {
/*  43 */       this.subconditions.forEach($$1 -> $$1.validate($$0));
/*     */     }
/*     */ 
/*     */     
/*     */     public JsonElement get() {
/*  48 */       JsonArray $$0 = new JsonArray();
/*  49 */       Objects.requireNonNull($$0); this.subconditions.stream().map(Supplier::get).forEach($$0::add);
/*     */       
/*  51 */       JsonObject $$1 = new JsonObject();
/*  52 */       $$1.add(this.operation.id, (JsonElement)$$0);
/*  53 */       return (JsonElement)$$1;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class TerminalCondition implements Condition {
/*  58 */     private final Map<Property<?>, String> terms = Maps.newHashMap();
/*     */     
/*     */     private static <T extends Comparable<T>> String joinValues(Property<T> $$0, Stream<T> $$1) {
/*  61 */       Objects.requireNonNull($$0); return $$1.<CharSequence>map($$0::getName).collect(Collectors.joining("|"));
/*     */     }
/*     */     
/*     */     private static <T extends Comparable<T>> String getTerm(Property<T> $$0, T $$1, T[] $$2) {
/*  65 */       return joinValues($$0, Stream.concat(Stream.of($$1), Stream.of($$2)));
/*     */     }
/*     */     
/*     */     private <T extends Comparable<T>> void putValue(Property<T> $$0, String $$1) {
/*  69 */       String $$2 = this.terms.put($$0, $$1);
/*  70 */       if ($$2 != null) {
/*  71 */         throw new IllegalStateException("Tried to replace " + $$0 + " value from " + $$2 + " to " + $$1);
/*     */       }
/*     */     }
/*     */     
/*     */     public final <T extends Comparable<T>> TerminalCondition term(Property<T> $$0, T $$1) {
/*  76 */       putValue($$0, $$0.getName((Comparable)$$1));
/*  77 */       return this;
/*     */     }
/*     */     
/*     */     @SafeVarargs
/*     */     public final <T extends Comparable<T>> TerminalCondition term(Property<T> $$0, T $$1, T... $$2) {
/*  82 */       putValue($$0, getTerm($$0, $$1, $$2));
/*  83 */       return this;
/*     */     }
/*     */     
/*     */     public final <T extends Comparable<T>> TerminalCondition negatedTerm(Property<T> $$0, T $$1) {
/*  87 */       putValue($$0, "!" + $$0.getName((Comparable)$$1));
/*  88 */       return this;
/*     */     }
/*     */     
/*     */     @SafeVarargs
/*     */     public final <T extends Comparable<T>> TerminalCondition negatedTerm(Property<T> $$0, T $$1, T... $$2) {
/*  93 */       putValue($$0, "!" + getTerm($$0, $$1, $$2));
/*  94 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public JsonElement get() {
/*  99 */       JsonObject $$0 = new JsonObject();
/* 100 */       this.terms.forEach(($$1, $$2) -> $$0.addProperty($$1.getName(), $$2));
/* 101 */       return (JsonElement)$$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public void validate(StateDefinition<?, ?> $$0) {
/* 106 */       List<Property<?>> $$1 = (List<Property<?>>)this.terms.keySet().stream().filter($$1 -> ($$0.getProperty($$1.getName()) != $$1)).collect(Collectors.toList());
/* 107 */       if (!$$1.isEmpty()) {
/* 108 */         throw new IllegalStateException("Properties " + $$1 + " are missing from " + $$0);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   static TerminalCondition condition() {
/* 114 */     return new TerminalCondition();
/*     */   }
/*     */   
/*     */   static Condition and(Condition... $$0) {
/* 118 */     return new CompositeCondition(Operation.AND, Arrays.asList($$0));
/*     */   }
/*     */   
/*     */   static Condition or(Condition... $$0) {
/* 122 */     return new CompositeCondition(Operation.OR, Arrays.asList($$0));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\models\blockstates\Condition.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */