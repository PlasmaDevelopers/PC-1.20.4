/*     */ package net.minecraft.world.level.levelgen;
/*     */ 
/*     */ import net.minecraft.util.Mth;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NoiseInterpolator
/*     */   implements DensityFunctions.MarkerOrMarked, NoiseChunk.NoiseChunkDensityFunction
/*     */ {
/*     */   double[][] slice0;
/*     */   double[][] slice1;
/*     */   private final DensityFunction noiseFiller;
/*     */   private double noise000;
/*     */   private double noise001;
/*     */   private double noise100;
/*     */   private double noise101;
/*     */   private double noise010;
/*     */   private double noise011;
/*     */   private double noise110;
/*     */   private double noise111;
/*     */   private double valueXZ00;
/*     */   private double valueXZ10;
/*     */   private double valueXZ01;
/*     */   private double valueXZ11;
/*     */   private double valueZ0;
/*     */   private double valueZ1;
/*     */   private double value;
/*     */   
/*     */   NoiseInterpolator(DensityFunction $$1) {
/* 558 */     this.noiseFiller = $$1;
/* 559 */     this.slice0 = allocateSlice($$0.cellCountY, $$0.cellCountXZ);
/* 560 */     this.slice1 = allocateSlice($$0.cellCountY, $$0.cellCountXZ);
/*     */     
/* 562 */     $$0.interpolators.add(this);
/*     */   }
/*     */   
/*     */   private double[][] allocateSlice(int $$0, int $$1) {
/* 566 */     int $$2 = $$1 + 1;
/* 567 */     int $$3 = $$0 + 1;
/* 568 */     double[][] $$4 = new double[$$2][$$3];
/* 569 */     for (int $$5 = 0; $$5 < $$2; $$5++) {
/* 570 */       $$4[$$5] = new double[$$3];
/*     */     }
/* 572 */     return $$4;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void selectCellYZ(int $$0, int $$1) {
/* 582 */     this.noise000 = this.slice0[$$1][$$0];
/* 583 */     this.noise001 = this.slice0[$$1 + 1][$$0];
/* 584 */     this.noise100 = this.slice1[$$1][$$0];
/* 585 */     this.noise101 = this.slice1[$$1 + 1][$$0];
/*     */     
/* 587 */     this.noise010 = this.slice0[$$1][$$0 + 1];
/* 588 */     this.noise011 = this.slice0[$$1 + 1][$$0 + 1];
/* 589 */     this.noise110 = this.slice1[$$1][$$0 + 1];
/* 590 */     this.noise111 = this.slice1[$$1 + 1][$$0 + 1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void updateForY(double $$0) {
/* 599 */     this.valueXZ00 = Mth.lerp($$0, this.noise000, this.noise010);
/* 600 */     this.valueXZ10 = Mth.lerp($$0, this.noise100, this.noise110);
/* 601 */     this.valueXZ01 = Mth.lerp($$0, this.noise001, this.noise011);
/* 602 */     this.valueXZ11 = Mth.lerp($$0, this.noise101, this.noise111);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void updateForX(double $$0) {
/* 611 */     this.valueZ0 = Mth.lerp($$0, this.valueXZ00, this.valueXZ10);
/* 612 */     this.valueZ1 = Mth.lerp($$0, this.valueXZ01, this.valueXZ11);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void updateForZ(double $$0) {
/* 621 */     this.value = Mth.lerp($$0, this.valueZ0, this.valueZ1);
/*     */   }
/*     */ 
/*     */   
/*     */   public double compute(DensityFunction.FunctionContext $$0) {
/* 626 */     if ($$0 != NoiseChunk.this) {
/* 627 */       return this.noiseFiller.compute($$0);
/*     */     }
/* 629 */     if (!NoiseChunk.this.interpolating) {
/* 630 */       throw new IllegalStateException("Trying to sample interpolator outside the interpolation loop");
/*     */     }
/* 632 */     if (NoiseChunk.this.fillingCell) {
/* 633 */       return Mth.lerp3(NoiseChunk.this.inCellX / NoiseChunk.this.cellWidth, NoiseChunk.this.inCellY / NoiseChunk.this.cellHeight, NoiseChunk.this.inCellZ / NoiseChunk.this.cellWidth, this.noise000, this.noise100, this.noise010, this.noise110, this.noise001, this.noise101, this.noise011, this.noise111);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 641 */     return this.value;
/*     */   }
/*     */ 
/*     */   
/*     */   public void fillArray(double[] $$0, DensityFunction.ContextProvider $$1) {
/* 646 */     if (NoiseChunk.this.fillingCell) {
/*     */       
/* 648 */       $$1.fillAllDirectly($$0, this);
/*     */       return;
/*     */     } 
/* 651 */     wrapped().fillArray($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public DensityFunction wrapped() {
/* 656 */     return this.noiseFiller;
/*     */   }
/*     */ 
/*     */   
/*     */   private void swapSlices() {
/* 661 */     double[][] $$0 = this.slice0;
/* 662 */     this.slice0 = this.slice1;
/* 663 */     this.slice1 = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public DensityFunctions.Marker.Type type() {
/* 668 */     return DensityFunctions.Marker.Type.Interpolated;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\NoiseChunk$NoiseInterpolator.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */