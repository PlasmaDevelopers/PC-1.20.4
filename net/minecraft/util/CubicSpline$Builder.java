/*     */ package net.minecraft.util;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.Lists;
/*     */ import it.unimi.dsi.fastutil.floats.FloatArrayList;
/*     */ import it.unimi.dsi.fastutil.floats.FloatList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Builder<C, I extends ToFloatFunction<C>>
/*     */ {
/*     */   private final I coordinate;
/*     */   private final ToFloatFunction<Float> valueTransformer;
/* 299 */   private final FloatList locations = (FloatList)new FloatArrayList();
/* 300 */   private final List<CubicSpline<C, I>> values = Lists.newArrayList();
/* 301 */   private final FloatList derivatives = (FloatList)new FloatArrayList();
/*     */   
/*     */   protected Builder(I $$0) {
/* 304 */     this($$0, ToFloatFunction.IDENTITY);
/*     */   }
/*     */   
/*     */   protected Builder(I $$0, ToFloatFunction<Float> $$1) {
/* 308 */     this.coordinate = $$0;
/* 309 */     this.valueTransformer = $$1;
/*     */   }
/*     */   
/*     */   public Builder<C, I> addPoint(float $$0, float $$1) {
/* 313 */     return addPoint($$0, new CubicSpline.Constant<>(this.valueTransformer.apply(Float.valueOf($$1))), 0.0F);
/*     */   }
/*     */   
/*     */   public Builder<C, I> addPoint(float $$0, float $$1, float $$2) {
/* 317 */     return addPoint($$0, new CubicSpline.Constant<>(this.valueTransformer.apply(Float.valueOf($$1))), $$2);
/*     */   }
/*     */   
/*     */   public Builder<C, I> addPoint(float $$0, CubicSpline<C, I> $$1) {
/* 321 */     return addPoint($$0, $$1, 0.0F);
/*     */   }
/*     */   
/*     */   private Builder<C, I> addPoint(float $$0, CubicSpline<C, I> $$1, float $$2) {
/* 325 */     if (!this.locations.isEmpty() && $$0 <= this.locations.getFloat(this.locations.size() - 1)) {
/* 326 */       throw new IllegalArgumentException("Please register points in ascending order");
/*     */     }
/* 328 */     this.locations.add($$0);
/* 329 */     this.values.add($$1);
/* 330 */     this.derivatives.add($$2);
/* 331 */     return this;
/*     */   }
/*     */   
/*     */   public CubicSpline<C, I> build() {
/* 335 */     if (this.locations.isEmpty()) {
/* 336 */       throw new IllegalStateException("No elements added");
/*     */     }
/* 338 */     return CubicSpline.Multipoint.create(this.coordinate, this.locations.toFloatArray(), (List<CubicSpline<C, I>>)ImmutableList.copyOf(this.values), this.derivatives.toFloatArray());
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\CubicSpline$Builder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */