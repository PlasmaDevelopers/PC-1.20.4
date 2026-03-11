/*     */ package net.minecraft.data.models.blockstates;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class C4<T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>, T4 extends Comparable<T4>>
/*     */   extends PropertyDispatch
/*     */ {
/*     */   private final Property<T1> property1;
/*     */   private final Property<T2> property2;
/*     */   private final Property<T3> property3;
/*     */   private final Property<T4> property4;
/*     */   
/*     */   C4(Property<T1> $$0, Property<T2> $$1, Property<T3> $$2, Property<T4> $$3) {
/* 210 */     this.property1 = $$0;
/* 211 */     this.property2 = $$1;
/* 212 */     this.property3 = $$2;
/* 213 */     this.property4 = $$3;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Property<?>> getDefinedProperties() {
/* 218 */     return (List<Property<?>>)ImmutableList.of(this.property1, this.property2, this.property3, this.property4);
/*     */   }
/*     */   
/*     */   public C4<T1, T2, T3, T4> select(T1 $$0, T2 $$1, T3 $$2, T4 $$3, List<Variant> $$4) {
/* 222 */     Selector $$5 = Selector.of((Property.Value<?>[])new Property.Value[] { this.property1
/* 223 */           .value((Comparable)$$0), this.property2
/* 224 */           .value((Comparable)$$1), this.property3
/* 225 */           .value((Comparable)$$2), this.property4
/* 226 */           .value((Comparable)$$3) });
/*     */     
/* 228 */     putValue($$5, $$4);
/* 229 */     return this;
/*     */   }
/*     */   
/*     */   public C4<T1, T2, T3, T4> select(T1 $$0, T2 $$1, T3 $$2, T4 $$3, Variant $$4) {
/* 233 */     return select($$0, $$1, $$2, $$3, Collections.singletonList($$4));
/*     */   }
/*     */   
/*     */   public PropertyDispatch generate(PropertyDispatch.QuadFunction<T1, T2, T3, T4, Variant> $$0) {
/* 237 */     this.property1.getPossibleValues().forEach($$1 -> this.property2.getPossibleValues().forEach(()));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 246 */     return this;
/*     */   }
/*     */   
/*     */   public PropertyDispatch generateList(PropertyDispatch.QuadFunction<T1, T2, T3, T4, List<Variant>> $$0) {
/* 250 */     this.property1.getPossibleValues().forEach($$1 -> this.property2.getPossibleValues().forEach(()));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 259 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\models\blockstates\PropertyDispatch$C4.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */