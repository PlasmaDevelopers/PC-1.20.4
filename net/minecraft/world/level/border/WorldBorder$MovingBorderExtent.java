/*     */ package net.minecraft.world.level.border;
/*     */ 
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.phys.shapes.BooleanOp;
/*     */ import net.minecraft.world.phys.shapes.Shapes;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class MovingBorderExtent
/*     */   implements WorldBorder.BorderExtent
/*     */ {
/*     */   private final double from;
/*     */   private final double to;
/*     */   private final long lerpEnd;
/*     */   private final long lerpBegin;
/*     */   private final double lerpDuration;
/*     */   
/*     */   MovingBorderExtent(double $$0, double $$1, long $$2) {
/*  62 */     this.from = $$0;
/*  63 */     this.to = $$1;
/*     */     
/*  65 */     this.lerpDuration = $$2;
/*  66 */     this.lerpBegin = Util.getMillis();
/*  67 */     this.lerpEnd = this.lerpBegin + $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getMinX() {
/*  72 */     return Mth.clamp(WorldBorder.this.getCenterX() - getSize() / 2.0D, -WorldBorder.this.absoluteMaxSize, WorldBorder.this.absoluteMaxSize);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getMinZ() {
/*  77 */     return Mth.clamp(WorldBorder.this.getCenterZ() - getSize() / 2.0D, -WorldBorder.this.absoluteMaxSize, WorldBorder.this.absoluteMaxSize);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getMaxX() {
/*  82 */     return Mth.clamp(WorldBorder.this.getCenterX() + getSize() / 2.0D, -WorldBorder.this.absoluteMaxSize, WorldBorder.this.absoluteMaxSize);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getMaxZ() {
/*  87 */     return Mth.clamp(WorldBorder.this.getCenterZ() + getSize() / 2.0D, -WorldBorder.this.absoluteMaxSize, WorldBorder.this.absoluteMaxSize);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getSize() {
/*  92 */     double $$0 = (Util.getMillis() - this.lerpBegin) / this.lerpDuration;
/*  93 */     return ($$0 < 1.0D) ? Mth.lerp($$0, this.from, this.to) : this.to;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getLerpSpeed() {
/*  98 */     return Math.abs(this.from - this.to) / (this.lerpEnd - this.lerpBegin);
/*     */   }
/*     */ 
/*     */   
/*     */   public long getLerpRemainingTime() {
/* 103 */     return this.lerpEnd - Util.getMillis();
/*     */   }
/*     */ 
/*     */   
/*     */   public double getLerpTarget() {
/* 108 */     return this.to;
/*     */   }
/*     */ 
/*     */   
/*     */   public BorderStatus getStatus() {
/* 113 */     return (this.to < this.from) ? BorderStatus.SHRINKING : BorderStatus.GROWING;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onCenterChange() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void onAbsoluteMaxSizeChange() {}
/*     */ 
/*     */   
/*     */   public WorldBorder.BorderExtent update() {
/* 126 */     if (getLerpRemainingTime() <= 0L) {
/* 127 */       return new WorldBorder.StaticBorderExtent(WorldBorder.this, this.to);
/*     */     }
/*     */     
/* 130 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getCollisionShape() {
/* 135 */     return Shapes.join(Shapes.INFINITY, Shapes.box(
/* 136 */           Math.floor(getMinX()), Double.NEGATIVE_INFINITY, Math.floor(getMinZ()), 
/* 137 */           Math.ceil(getMaxX()), Double.POSITIVE_INFINITY, Math.ceil(getMaxZ())), BooleanOp.ONLY_FIRST);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\border\WorldBorder$MovingBorderExtent.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */