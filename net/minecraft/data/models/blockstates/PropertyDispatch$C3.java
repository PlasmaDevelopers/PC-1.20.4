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
/*     */ public class C3<T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>>
/*     */   extends PropertyDispatch
/*     */ {
/*     */   private final Property<T1> property1;
/*     */   private final Property<T2> property2;
/*     */   private final Property<T3> property3;
/*     */   
/*     */   C3(Property<T1> $$0, Property<T2> $$1, Property<T3> $$2) {
/* 156 */     this.property1 = $$0;
/* 157 */     this.property2 = $$1;
/* 158 */     this.property3 = $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Property<?>> getDefinedProperties() {
/* 163 */     return (List<Property<?>>)ImmutableList.of(this.property1, this.property2, this.property3);
/*     */   }
/*     */   
/*     */   public C3<T1, T2, T3> select(T1 $$0, T2 $$1, T3 $$2, List<Variant> $$3) {
/* 167 */     Selector $$4 = Selector.of((Property.Value<?>[])new Property.Value[] { this.property1
/* 168 */           .value((Comparable)$$0), this.property2
/* 169 */           .value((Comparable)$$1), this.property3
/* 170 */           .value((Comparable)$$2) });
/*     */     
/* 172 */     putValue($$4, $$3);
/* 173 */     return this;
/*     */   }
/*     */   
/*     */   public C3<T1, T2, T3> select(T1 $$0, T2 $$1, T3 $$2, Variant $$3) {
/* 177 */     return select($$0, $$1, $$2, Collections.singletonList($$3));
/*     */   }
/*     */   
/*     */   public PropertyDispatch generate(PropertyDispatch.TriFunction<T1, T2, T3, Variant> $$0) {
/* 181 */     this.property1.getPossibleValues().forEach($$1 -> this.property2.getPossibleValues().forEach(()));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 188 */     return this;
/*     */   }
/*     */   
/*     */   public PropertyDispatch generateList(PropertyDispatch.TriFunction<T1, T2, T3, List<Variant>> $$0) {
/* 192 */     this.property1.getPossibleValues().forEach($$1 -> this.property2.getPossibleValues().forEach(()));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 199 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\models\blockstates\PropertyDispatch$C3.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */