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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class C5<T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>, T4 extends Comparable<T4>, T5 extends Comparable<T5>>
/*     */   extends PropertyDispatch
/*     */ {
/*     */   private final Property<T1> property1;
/*     */   private final Property<T2> property2;
/*     */   private final Property<T3> property3;
/*     */   private final Property<T4> property4;
/*     */   private final Property<T5> property5;
/*     */   
/*     */   C5(Property<T1> $$0, Property<T2> $$1, Property<T3> $$2, Property<T4> $$3, Property<T5> $$4) {
/* 271 */     this.property1 = $$0;
/* 272 */     this.property2 = $$1;
/* 273 */     this.property3 = $$2;
/* 274 */     this.property4 = $$3;
/* 275 */     this.property5 = $$4;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Property<?>> getDefinedProperties() {
/* 280 */     return (List<Property<?>>)ImmutableList.of(this.property1, this.property2, this.property3, this.property4, this.property5);
/*     */   }
/*     */   
/*     */   public C5<T1, T2, T3, T4, T5> select(T1 $$0, T2 $$1, T3 $$2, T4 $$3, T5 $$4, List<Variant> $$5) {
/* 284 */     Selector $$6 = Selector.of((Property.Value<?>[])new Property.Value[] { this.property1
/* 285 */           .value((Comparable)$$0), this.property2
/* 286 */           .value((Comparable)$$1), this.property3
/* 287 */           .value((Comparable)$$2), this.property4
/* 288 */           .value((Comparable)$$3), this.property5
/* 289 */           .value((Comparable)$$4) });
/*     */     
/* 291 */     putValue($$6, $$5);
/* 292 */     return this;
/*     */   }
/*     */   
/*     */   public C5<T1, T2, T3, T4, T5> select(T1 $$0, T2 $$1, T3 $$2, T4 $$3, T5 $$4, Variant $$5) {
/* 296 */     return select($$0, $$1, $$2, $$3, $$4, Collections.singletonList($$5));
/*     */   }
/*     */   
/*     */   public PropertyDispatch generate(PropertyDispatch.PentaFunction<T1, T2, T3, T4, T5, Variant> $$0) {
/* 300 */     this.property1.getPossibleValues().forEach($$1 -> this.property2.getPossibleValues().forEach(()));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 311 */     return this;
/*     */   }
/*     */   
/*     */   public PropertyDispatch generateList(PropertyDispatch.PentaFunction<T1, T2, T3, T4, T5, List<Variant>> $$0) {
/* 315 */     this.property1.getPossibleValues().forEach($$1 -> this.property2.getPossibleValues().forEach(()));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 326 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\models\blockstates\PropertyDispatch$C5.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */