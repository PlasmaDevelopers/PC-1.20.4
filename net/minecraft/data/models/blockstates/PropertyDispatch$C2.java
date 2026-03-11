/*     */ package net.minecraft.data.models.blockstates;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.function.BiFunction;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
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
/*     */ public class C2<T1 extends Comparable<T1>, T2 extends Comparable<T2>>
/*     */   extends PropertyDispatch
/*     */ {
/*     */   private final Property<T1> property1;
/*     */   private final Property<T2> property2;
/*     */   
/*     */   C2(Property<T1> $$0, Property<T2> $$1) {
/* 109 */     this.property1 = $$0;
/* 110 */     this.property2 = $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Property<?>> getDefinedProperties() {
/* 115 */     return (List<Property<?>>)ImmutableList.of(this.property1, this.property2);
/*     */   }
/*     */   
/*     */   public C2<T1, T2> select(T1 $$0, T2 $$1, List<Variant> $$2) {
/* 119 */     Selector $$3 = Selector.of((Property.Value<?>[])new Property.Value[] { this.property1
/* 120 */           .value((Comparable)$$0), this.property2
/* 121 */           .value((Comparable)$$1) });
/*     */     
/* 123 */     putValue($$3, $$2);
/* 124 */     return this;
/*     */   }
/*     */   
/*     */   public C2<T1, T2> select(T1 $$0, T2 $$1, Variant $$2) {
/* 128 */     return select($$0, $$1, Collections.singletonList($$2));
/*     */   }
/*     */   
/*     */   public PropertyDispatch generate(BiFunction<T1, T2, Variant> $$0) {
/* 132 */     this.property1.getPossibleValues().forEach($$1 -> this.property2.getPossibleValues().forEach(()));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 137 */     return this;
/*     */   }
/*     */   
/*     */   public PropertyDispatch generateList(BiFunction<T1, T2, List<Variant>> $$0) {
/* 141 */     this.property1.getPossibleValues().forEach($$1 -> this.property2.getPossibleValues().forEach(()));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 146 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\models\blockstates\PropertyDispatch$C2.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */