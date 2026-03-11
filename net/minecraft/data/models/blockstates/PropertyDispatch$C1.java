/*     */ package net.minecraft.data.models.blockstates;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.function.Function;
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
/*     */ public class C1<T1 extends Comparable<T1>>
/*     */   extends PropertyDispatch
/*     */ {
/*     */   private final Property<T1> property1;
/*     */   
/*     */   C1(Property<T1> $$0) {
/*  69 */     this.property1 = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Property<?>> getDefinedProperties() {
/*  74 */     return (List<Property<?>>)ImmutableList.of(this.property1);
/*     */   }
/*     */   
/*     */   public C1<T1> select(T1 $$0, List<Variant> $$1) {
/*  78 */     Selector $$2 = Selector.of((Property.Value<?>[])new Property.Value[] { this.property1
/*  79 */           .value((Comparable)$$0) });
/*     */     
/*  81 */     putValue($$2, $$1);
/*  82 */     return this;
/*     */   }
/*     */   
/*     */   public C1<T1> select(T1 $$0, Variant $$1) {
/*  86 */     return select($$0, Collections.singletonList($$1));
/*     */   }
/*     */   
/*     */   public PropertyDispatch generate(Function<T1, Variant> $$0) {
/*  90 */     this.property1.getPossibleValues().forEach($$1 -> select((T1)$$1, $$0.apply($$1)));
/*     */ 
/*     */     
/*  93 */     return this;
/*     */   }
/*     */   
/*     */   public PropertyDispatch generateList(Function<T1, List<Variant>> $$0) {
/*  97 */     this.property1.getPossibleValues().forEach($$1 -> select((T1)$$1, $$0.apply($$1)));
/*     */ 
/*     */     
/* 100 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\models\blockstates\PropertyDispatch$C1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */