/*     */ package net.minecraft.world.level.border;
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class StaticBorderExtent
/*     */   implements WorldBorder.BorderExtent
/*     */ {
/*     */   private final double size;
/*     */   private double minX;
/*     */   private double minZ;
/*     */   private double maxX;
/*     */   private double maxZ;
/*     */   private VoxelShape shape;
/*     */   
/*     */   public StaticBorderExtent(double $$0) {
/* 153 */     this.size = $$0;
/* 154 */     updateBox();
/*     */   }
/*     */ 
/*     */   
/*     */   public double getMinX() {
/* 159 */     return this.minX;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getMaxX() {
/* 164 */     return this.maxX;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getMinZ() {
/* 169 */     return this.minZ;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getMaxZ() {
/* 174 */     return this.maxZ;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getSize() {
/* 179 */     return this.size;
/*     */   }
/*     */ 
/*     */   
/*     */   public BorderStatus getStatus() {
/* 184 */     return BorderStatus.STATIONARY;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getLerpSpeed() {
/* 189 */     return 0.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public long getLerpRemainingTime() {
/* 194 */     return 0L;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getLerpTarget() {
/* 199 */     return this.size;
/*     */   }
/*     */   
/*     */   private void updateBox() {
/* 203 */     this.minX = Mth.clamp(WorldBorder.this.getCenterX() - this.size / 2.0D, -WorldBorder.this.absoluteMaxSize, WorldBorder.this.absoluteMaxSize);
/* 204 */     this.minZ = Mth.clamp(WorldBorder.this.getCenterZ() - this.size / 2.0D, -WorldBorder.this.absoluteMaxSize, WorldBorder.this.absoluteMaxSize);
/* 205 */     this.maxX = Mth.clamp(WorldBorder.this.getCenterX() + this.size / 2.0D, -WorldBorder.this.absoluteMaxSize, WorldBorder.this.absoluteMaxSize);
/* 206 */     this.maxZ = Mth.clamp(WorldBorder.this.getCenterZ() + this.size / 2.0D, -WorldBorder.this.absoluteMaxSize, WorldBorder.this.absoluteMaxSize);
/*     */     
/* 208 */     this.shape = Shapes.join(Shapes.INFINITY, Shapes.box(
/* 209 */           Math.floor(getMinX()), Double.NEGATIVE_INFINITY, Math.floor(getMinZ()), 
/* 210 */           Math.ceil(getMaxX()), Double.POSITIVE_INFINITY, Math.ceil(getMaxZ())), BooleanOp.ONLY_FIRST);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onAbsoluteMaxSizeChange() {
/* 216 */     updateBox();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onCenterChange() {
/* 221 */     updateBox();
/*     */   }
/*     */ 
/*     */   
/*     */   public WorldBorder.BorderExtent update() {
/* 226 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getCollisionShape() {
/* 231 */     return this.shape;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\border\WorldBorder$StaticBorderExtent.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */