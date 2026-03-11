/*     */ package net.minecraft.world.level.levelgen;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class null
/*     */   implements DensityFunction.Visitor
/*     */ {
/*  95 */   private final Map<DensityFunction, DensityFunction> wrapped = new HashMap<>();
/*     */   
/*     */   private DensityFunction wrapNew(DensityFunction $$0) {
/*  98 */     if ($$0 instanceof DensityFunctions.HolderHolder) { DensityFunctions.HolderHolder $$1 = (DensityFunctions.HolderHolder)$$0;
/*  99 */       return (DensityFunction)$$1.function().value(); }
/*     */     
/* 101 */     if ($$0 instanceof DensityFunctions.Marker) { DensityFunctions.Marker $$2 = (DensityFunctions.Marker)$$0;
/* 102 */       return $$2.wrapped(); }
/*     */     
/* 104 */     return $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public DensityFunction apply(DensityFunction $$0) {
/* 109 */     return this.wrapped.computeIfAbsent($$0, this::wrapNew);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\RandomState$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */