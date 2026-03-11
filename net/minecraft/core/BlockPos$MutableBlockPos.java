/*     */ package net.minecraft.core;
/*     */ 
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.level.block.Rotation;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MutableBlockPos
/*     */   extends BlockPos
/*     */ {
/*     */   public MutableBlockPos() {
/* 266 */     this(0, 0, 0);
/*     */   }
/*     */   
/*     */   public MutableBlockPos(int $$0, int $$1, int $$2) {
/* 270 */     super($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   public MutableBlockPos(double $$0, double $$1, double $$2) {
/* 274 */     this(Mth.floor($$0), Mth.floor($$1), Mth.floor($$2));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos offset(int $$0, int $$1, int $$2) {
/* 279 */     return super.offset($$0, $$1, $$2).immutable();
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos multiply(int $$0) {
/* 284 */     return super.multiply($$0).immutable();
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos relative(Direction $$0, int $$1) {
/* 289 */     return super.relative($$0, $$1).immutable();
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos relative(Direction.Axis $$0, int $$1) {
/* 294 */     return super.relative($$0, $$1).immutable();
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos rotate(Rotation $$0) {
/* 299 */     return super.rotate($$0).immutable();
/*     */   }
/*     */   
/*     */   public MutableBlockPos set(int $$0, int $$1, int $$2) {
/* 303 */     setX($$0);
/* 304 */     setY($$1);
/* 305 */     setZ($$2);
/* 306 */     return this;
/*     */   }
/*     */   
/*     */   public MutableBlockPos set(double $$0, double $$1, double $$2) {
/* 310 */     return set(Mth.floor($$0), Mth.floor($$1), Mth.floor($$2));
/*     */   }
/*     */   
/*     */   public MutableBlockPos set(Vec3i $$0) {
/* 314 */     return set($$0.getX(), $$0.getY(), $$0.getZ());
/*     */   }
/*     */   
/*     */   public MutableBlockPos set(long $$0) {
/* 318 */     return set(getX($$0), getY($$0), getZ($$0));
/*     */   }
/*     */   
/*     */   public MutableBlockPos set(AxisCycle $$0, int $$1, int $$2, int $$3) {
/* 322 */     return set($$0
/* 323 */         .cycle($$1, $$2, $$3, Direction.Axis.X), $$0
/* 324 */         .cycle($$1, $$2, $$3, Direction.Axis.Y), $$0
/* 325 */         .cycle($$1, $$2, $$3, Direction.Axis.Z));
/*     */   }
/*     */ 
/*     */   
/*     */   public MutableBlockPos setWithOffset(Vec3i $$0, Direction $$1) {
/* 330 */     return set($$0.getX() + $$1.getStepX(), $$0.getY() + $$1.getStepY(), $$0.getZ() + $$1.getStepZ());
/*     */   }
/*     */   
/*     */   public MutableBlockPos setWithOffset(Vec3i $$0, int $$1, int $$2, int $$3) {
/* 334 */     return set($$0.getX() + $$1, $$0.getY() + $$2, $$0.getZ() + $$3);
/*     */   }
/*     */   
/*     */   public MutableBlockPos setWithOffset(Vec3i $$0, Vec3i $$1) {
/* 338 */     return set($$0.getX() + $$1.getX(), $$0.getY() + $$1.getY(), $$0.getZ() + $$1.getZ());
/*     */   }
/*     */   
/*     */   public MutableBlockPos move(Direction $$0) {
/* 342 */     return move($$0, 1);
/*     */   }
/*     */   
/*     */   public MutableBlockPos move(Direction $$0, int $$1) {
/* 346 */     return set(getX() + $$0.getStepX() * $$1, getY() + $$0.getStepY() * $$1, getZ() + $$0.getStepZ() * $$1);
/*     */   }
/*     */   
/*     */   public MutableBlockPos move(int $$0, int $$1, int $$2) {
/* 350 */     return set(getX() + $$0, getY() + $$1, getZ() + $$2);
/*     */   }
/*     */   
/*     */   public MutableBlockPos move(Vec3i $$0) {
/* 354 */     return set(getX() + $$0.getX(), getY() + $$0.getY(), getZ() + $$0.getZ());
/*     */   }
/*     */   
/*     */   public MutableBlockPos clamp(Direction.Axis $$0, int $$1, int $$2) {
/* 358 */     switch (BlockPos.null.$SwitchMap$net$minecraft$core$Direction$Axis[$$0.ordinal()]) {
/*     */       case 1:
/* 360 */         return set(Mth.clamp(getX(), $$1, $$2), getY(), getZ());
/*     */       case 2:
/* 362 */         return set(getX(), Mth.clamp(getY(), $$1, $$2), getZ());
/*     */       case 3:
/* 364 */         return set(getX(), getY(), Mth.clamp(getZ(), $$1, $$2));
/*     */     } 
/* 366 */     throw new IllegalStateException("Unable to clamp axis " + $$0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MutableBlockPos setX(int $$0) {
/* 372 */     super.setX($$0);
/* 373 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public MutableBlockPos setY(int $$0) {
/* 378 */     super.setY($$0);
/* 379 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public MutableBlockPos setZ(int $$0) {
/* 384 */     super.setZ($$0);
/* 385 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos immutable() {
/* 390 */     return new BlockPos(this);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\BlockPos$MutableBlockPos.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */