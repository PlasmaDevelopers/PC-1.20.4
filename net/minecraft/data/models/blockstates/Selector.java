/*    */ package net.minecraft.data.models.blockstates;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.Comparator;
/*    */ import java.util.List;
/*    */ import java.util.stream.Collectors;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ 
/*    */ public final class Selector
/*    */ {
/* 11 */   private static final Selector EMPTY = new Selector((List<Property.Value<?>>)ImmutableList.of()); private static final Comparator<Property.Value<?>> COMPARE_BY_NAME; static {
/* 12 */     COMPARE_BY_NAME = Comparator.comparing($$0 -> $$0.property().getName());
/*    */   }
/*    */   private final List<Property.Value<?>> values;
/*    */   
/*    */   public Selector extend(Property.Value<?> $$0) {
/* 17 */     return new Selector((List<Property.Value<?>>)ImmutableList.builder().addAll(this.values).add($$0).build());
/*    */   }
/*    */   
/*    */   public Selector extend(Selector $$0) {
/* 21 */     return new Selector((List<Property.Value<?>>)ImmutableList.builder().addAll(this.values).addAll($$0.values).build());
/*    */   }
/*    */   
/*    */   private Selector(List<Property.Value<?>> $$0) {
/* 25 */     this.values = $$0;
/*    */   }
/*    */   
/*    */   public static Selector empty() {
/* 29 */     return EMPTY;
/*    */   }
/*    */   
/*    */   public static Selector of(Property.Value<?>... $$0) {
/* 33 */     return new Selector((List<Property.Value<?>>)ImmutableList.copyOf((Object[])$$0));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object $$0) {
/* 38 */     return (this == $$0 || ($$0 instanceof Selector && this.values.equals(((Selector)$$0).values)));
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 43 */     return this.values.hashCode();
/*    */   }
/*    */   
/*    */   public String getKey() {
/* 47 */     return this.values.stream().sorted(COMPARE_BY_NAME).map(Property.Value::toString).collect(Collectors.joining(","));
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 52 */     return getKey();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\models\blockstates\Selector.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */