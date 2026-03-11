/*     */ package net.minecraft.world.level.levelgen;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */   implements DensityFunction.ContextProvider
/*     */ {
/*     */   public DensityFunction.FunctionContext forIndex(int $$0) {
/*  84 */     NoiseChunk.this.cellStartBlockY = ($$0 + NoiseChunk.this.cellNoiseMinY) * NoiseChunk.this.cellHeight;
/*  85 */     NoiseChunk.this.interpolationCounter++;
/*     */     
/*  87 */     NoiseChunk.this.inCellY = 0;
/*  88 */     NoiseChunk.this.arrayIndex = $$0;
/*  89 */     return NoiseChunk.this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void fillAllDirectly(double[] $$0, DensityFunction $$1) {
/*  95 */     for (int $$2 = 0; $$2 < NoiseChunk.this.cellCountY + 1; $$2++) {
/*  96 */       NoiseChunk.this.cellStartBlockY = ($$2 + NoiseChunk.this.cellNoiseMinY) * NoiseChunk.this.cellHeight;
/*  97 */       NoiseChunk.this.interpolationCounter++;
/*     */       
/*  99 */       NoiseChunk.this.inCellY = 0;
/* 100 */       NoiseChunk.this.arrayIndex = $$2;
/*     */       
/* 102 */       $$0[$$2] = $$1.compute(NoiseChunk.this);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\NoiseChunk$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */