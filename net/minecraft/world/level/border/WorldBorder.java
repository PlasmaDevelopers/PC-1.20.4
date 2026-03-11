/*     */ package net.minecraft.world.level.border;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.serialization.DynamicLike;
/*     */ import java.util.List;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.phys.AABB;
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
/*     */ public class WorldBorder
/*     */ {
/*     */   public static final double MAX_SIZE = 5.9999968E7D;
/*     */   public static final double MAX_CENTER_COORDINATE = 2.9999984E7D;
/*     */   
/*     */   private class MovingBorderExtent
/*     */     implements BorderExtent
/*     */   {
/*     */     private final double from;
/*     */     private final double to;
/*     */     private final long lerpEnd;
/*     */     private final long lerpBegin;
/*     */     private final double lerpDuration;
/*     */     
/*     */     MovingBorderExtent(double $$0, double $$1, long $$2) {
/*  62 */       this.from = $$0;
/*  63 */       this.to = $$1;
/*     */       
/*  65 */       this.lerpDuration = $$2;
/*  66 */       this.lerpBegin = Util.getMillis();
/*  67 */       this.lerpEnd = this.lerpBegin + $$2;
/*     */     }
/*     */ 
/*     */     
/*     */     public double getMinX() {
/*  72 */       return Mth.clamp(WorldBorder.this.getCenterX() - getSize() / 2.0D, -WorldBorder.this.absoluteMaxSize, WorldBorder.this.absoluteMaxSize);
/*     */     }
/*     */ 
/*     */     
/*     */     public double getMinZ() {
/*  77 */       return Mth.clamp(WorldBorder.this.getCenterZ() - getSize() / 2.0D, -WorldBorder.this.absoluteMaxSize, WorldBorder.this.absoluteMaxSize);
/*     */     }
/*     */ 
/*     */     
/*     */     public double getMaxX() {
/*  82 */       return Mth.clamp(WorldBorder.this.getCenterX() + getSize() / 2.0D, -WorldBorder.this.absoluteMaxSize, WorldBorder.this.absoluteMaxSize);
/*     */     }
/*     */ 
/*     */     
/*     */     public double getMaxZ() {
/*  87 */       return Mth.clamp(WorldBorder.this.getCenterZ() + getSize() / 2.0D, -WorldBorder.this.absoluteMaxSize, WorldBorder.this.absoluteMaxSize);
/*     */     }
/*     */ 
/*     */     
/*     */     public double getSize() {
/*  92 */       double $$0 = (Util.getMillis() - this.lerpBegin) / this.lerpDuration;
/*  93 */       return ($$0 < 1.0D) ? Mth.lerp($$0, this.from, this.to) : this.to;
/*     */     }
/*     */ 
/*     */     
/*     */     public double getLerpSpeed() {
/*  98 */       return Math.abs(this.from - this.to) / (this.lerpEnd - this.lerpBegin);
/*     */     }
/*     */ 
/*     */     
/*     */     public long getLerpRemainingTime() {
/* 103 */       return this.lerpEnd - Util.getMillis();
/*     */     }
/*     */ 
/*     */     
/*     */     public double getLerpTarget() {
/* 108 */       return this.to;
/*     */     }
/*     */ 
/*     */     
/*     */     public BorderStatus getStatus() {
/* 113 */       return (this.to < this.from) ? BorderStatus.SHRINKING : BorderStatus.GROWING;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void onCenterChange() {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void onAbsoluteMaxSizeChange() {}
/*     */ 
/*     */     
/*     */     public WorldBorder.BorderExtent update() {
/* 126 */       if (getLerpRemainingTime() <= 0L) {
/* 127 */         return new WorldBorder.StaticBorderExtent(this.to);
/*     */       }
/*     */       
/* 130 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public VoxelShape getCollisionShape() {
/* 135 */       return Shapes.join(Shapes.INFINITY, Shapes.box(
/* 136 */             Math.floor(getMinX()), Double.NEGATIVE_INFINITY, Math.floor(getMinZ()), 
/* 137 */             Math.ceil(getMaxX()), Double.POSITIVE_INFINITY, Math.ceil(getMaxZ())), BooleanOp.ONLY_FIRST);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private class StaticBorderExtent
/*     */     implements BorderExtent
/*     */   {
/*     */     private final double size;
/*     */     private double minX;
/*     */     private double minZ;
/*     */     private double maxX;
/*     */     private double maxZ;
/*     */     private VoxelShape shape;
/*     */     
/*     */     public StaticBorderExtent(double $$0) {
/* 153 */       this.size = $$0;
/* 154 */       updateBox();
/*     */     }
/*     */ 
/*     */     
/*     */     public double getMinX() {
/* 159 */       return this.minX;
/*     */     }
/*     */ 
/*     */     
/*     */     public double getMaxX() {
/* 164 */       return this.maxX;
/*     */     }
/*     */ 
/*     */     
/*     */     public double getMinZ() {
/* 169 */       return this.minZ;
/*     */     }
/*     */ 
/*     */     
/*     */     public double getMaxZ() {
/* 174 */       return this.maxZ;
/*     */     }
/*     */ 
/*     */     
/*     */     public double getSize() {
/* 179 */       return this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public BorderStatus getStatus() {
/* 184 */       return BorderStatus.STATIONARY;
/*     */     }
/*     */ 
/*     */     
/*     */     public double getLerpSpeed() {
/* 189 */       return 0.0D;
/*     */     }
/*     */ 
/*     */     
/*     */     public long getLerpRemainingTime() {
/* 194 */       return 0L;
/*     */     }
/*     */ 
/*     */     
/*     */     public double getLerpTarget() {
/* 199 */       return this.size;
/*     */     }
/*     */     
/*     */     private void updateBox() {
/* 203 */       this.minX = Mth.clamp(WorldBorder.this.getCenterX() - this.size / 2.0D, -WorldBorder.this.absoluteMaxSize, WorldBorder.this.absoluteMaxSize);
/* 204 */       this.minZ = Mth.clamp(WorldBorder.this.getCenterZ() - this.size / 2.0D, -WorldBorder.this.absoluteMaxSize, WorldBorder.this.absoluteMaxSize);
/* 205 */       this.maxX = Mth.clamp(WorldBorder.this.getCenterX() + this.size / 2.0D, -WorldBorder.this.absoluteMaxSize, WorldBorder.this.absoluteMaxSize);
/* 206 */       this.maxZ = Mth.clamp(WorldBorder.this.getCenterZ() + this.size / 2.0D, -WorldBorder.this.absoluteMaxSize, WorldBorder.this.absoluteMaxSize);
/*     */       
/* 208 */       this.shape = Shapes.join(Shapes.INFINITY, Shapes.box(
/* 209 */             Math.floor(getMinX()), Double.NEGATIVE_INFINITY, Math.floor(getMinZ()), 
/* 210 */             Math.ceil(getMaxX()), Double.POSITIVE_INFINITY, Math.ceil(getMaxZ())), BooleanOp.ONLY_FIRST);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void onAbsoluteMaxSizeChange() {
/* 216 */       updateBox();
/*     */     }
/*     */ 
/*     */     
/*     */     public void onCenterChange() {
/* 221 */       updateBox();
/*     */     }
/*     */ 
/*     */     
/*     */     public WorldBorder.BorderExtent update() {
/* 226 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public VoxelShape getCollisionShape() {
/* 231 */       return this.shape;
/*     */     }
/*     */   }
/*     */   
/* 235 */   private final List<BorderChangeListener> listeners = Lists.newArrayList();
/*     */   
/* 237 */   private double damagePerBlock = 0.2D;
/* 238 */   private double damageSafeZone = 5.0D;
/* 239 */   private int warningTime = 15;
/* 240 */   private int warningBlocks = 5;
/*     */   
/*     */   private double centerX;
/*     */   
/*     */   private double centerZ;
/* 245 */   int absoluteMaxSize = 29999984;
/*     */   
/* 247 */   private BorderExtent extent = new StaticBorderExtent(5.9999968E7D);
/*     */   
/*     */   public boolean isWithinBounds(BlockPos $$0) {
/* 250 */     return (($$0.getX() + 1) > getMinX() && $$0.getX() < getMaxX() && ($$0.getZ() + 1) > getMinZ() && $$0.getZ() < getMaxZ());
/*     */   }
/*     */   
/*     */   public boolean isWithinBounds(ChunkPos $$0) {
/* 254 */     return ($$0.getMaxBlockX() > getMinX() && $$0.getMinBlockX() < getMaxX() && $$0.getMaxBlockZ() > getMinZ() && $$0.getMinBlockZ() < getMaxZ());
/*     */   }
/*     */   
/*     */   public boolean isWithinBounds(double $$0, double $$1) {
/* 258 */     return ($$0 > getMinX() && $$0 < getMaxX() && $$1 > getMinZ() && $$1 < getMaxZ());
/*     */   }
/*     */   
/*     */   public boolean isWithinBounds(double $$0, double $$1, double $$2) {
/* 262 */     return ($$0 > getMinX() - $$2 && $$0 < getMaxX() + $$2 && $$1 > getMinZ() - $$2 && $$1 < getMaxZ() + $$2);
/*     */   }
/*     */   
/*     */   public boolean isWithinBounds(AABB $$0) {
/* 266 */     return ($$0.maxX > getMinX() && $$0.minX < getMaxX() && $$0.maxZ > getMinZ() && $$0.minZ < getMaxZ());
/*     */   }
/*     */   
/*     */   public BlockPos clampToBounds(double $$0, double $$1, double $$2) {
/* 270 */     return BlockPos.containing(Mth.clamp($$0, getMinX(), getMaxX()), $$1, Mth.clamp($$2, getMinZ(), getMaxZ()));
/*     */   }
/*     */   
/*     */   public double getDistanceToBorder(Entity $$0) {
/* 274 */     return getDistanceToBorder($$0.getX(), $$0.getZ());
/*     */   }
/*     */   
/*     */   public VoxelShape getCollisionShape() {
/* 278 */     return this.extent.getCollisionShape();
/*     */   }
/*     */   
/*     */   public double getDistanceToBorder(double $$0, double $$1) {
/* 282 */     double $$2 = $$1 - getMinZ();
/* 283 */     double $$3 = getMaxZ() - $$1;
/* 284 */     double $$4 = $$0 - getMinX();
/* 285 */     double $$5 = getMaxX() - $$0;
/* 286 */     double $$6 = Math.min($$4, $$5);
/* 287 */     $$6 = Math.min($$6, $$2);
/* 288 */     return Math.min($$6, $$3);
/*     */   }
/*     */   
/*     */   public boolean isInsideCloseToBorder(Entity $$0, AABB $$1) {
/* 292 */     double $$2 = Math.max(Mth.absMax($$1.getXsize(), $$1.getZsize()), 1.0D);
/* 293 */     return (getDistanceToBorder($$0) < $$2 * 2.0D && isWithinBounds($$0.getX(), $$0.getZ(), $$2));
/*     */   }
/*     */   
/*     */   public BorderStatus getStatus() {
/* 297 */     return this.extent.getStatus();
/*     */   }
/*     */   
/*     */   public double getMinX() {
/* 301 */     return this.extent.getMinX();
/*     */   }
/*     */   
/*     */   public double getMinZ() {
/* 305 */     return this.extent.getMinZ();
/*     */   }
/*     */   
/*     */   public double getMaxX() {
/* 309 */     return this.extent.getMaxX();
/*     */   }
/*     */   
/*     */   public double getMaxZ() {
/* 313 */     return this.extent.getMaxZ();
/*     */   }
/*     */   
/*     */   public double getCenterX() {
/* 317 */     return this.centerX;
/*     */   }
/*     */   
/*     */   public double getCenterZ() {
/* 321 */     return this.centerZ;
/*     */   }
/*     */   
/*     */   public void setCenter(double $$0, double $$1) {
/* 325 */     this.centerX = $$0;
/* 326 */     this.centerZ = $$1;
/*     */     
/* 328 */     this.extent.onCenterChange();
/*     */     
/* 330 */     for (BorderChangeListener $$2 : getListeners()) {
/* 331 */       $$2.onBorderCenterSet(this, $$0, $$1);
/*     */     }
/*     */   }
/*     */   
/*     */   public double getSize() {
/* 336 */     return this.extent.getSize();
/*     */   }
/*     */   
/*     */   public long getLerpRemainingTime() {
/* 340 */     return this.extent.getLerpRemainingTime();
/*     */   }
/*     */   
/*     */   public double getLerpTarget() {
/* 344 */     return this.extent.getLerpTarget();
/*     */   }
/*     */   
/*     */   public void setSize(double $$0) {
/* 348 */     this.extent = new StaticBorderExtent($$0);
/*     */     
/* 350 */     for (BorderChangeListener $$1 : getListeners()) {
/* 351 */       $$1.onBorderSizeSet(this, $$0);
/*     */     }
/*     */   }
/*     */   
/*     */   public void lerpSizeBetween(double $$0, double $$1, long $$2) {
/* 356 */     this.extent = ($$0 == $$1) ? new StaticBorderExtent($$1) : new MovingBorderExtent($$0, $$1, $$2);
/*     */     
/* 358 */     for (BorderChangeListener $$3 : getListeners()) {
/* 359 */       $$3.onBorderSizeLerping(this, $$0, $$1, $$2);
/*     */     }
/*     */   }
/*     */   
/*     */   protected List<BorderChangeListener> getListeners() {
/* 364 */     return Lists.newArrayList(this.listeners);
/*     */   }
/*     */   
/*     */   public void addListener(BorderChangeListener $$0) {
/* 368 */     this.listeners.add($$0);
/*     */   }
/*     */   
/*     */   public void removeListener(BorderChangeListener $$0) {
/* 372 */     this.listeners.remove($$0);
/*     */   }
/*     */   
/*     */   public void setAbsoluteMaxSize(int $$0) {
/* 376 */     this.absoluteMaxSize = $$0;
/* 377 */     this.extent.onAbsoluteMaxSizeChange();
/*     */   }
/*     */   
/*     */   public int getAbsoluteMaxSize() {
/* 381 */     return this.absoluteMaxSize;
/*     */   }
/*     */   
/*     */   public double getDamageSafeZone() {
/* 385 */     return this.damageSafeZone;
/*     */   }
/*     */   
/*     */   public void setDamageSafeZone(double $$0) {
/* 389 */     this.damageSafeZone = $$0;
/*     */     
/* 391 */     for (BorderChangeListener $$1 : getListeners()) {
/* 392 */       $$1.onBorderSetDamageSafeZOne(this, $$0);
/*     */     }
/*     */   }
/*     */   
/*     */   public double getDamagePerBlock() {
/* 397 */     return this.damagePerBlock;
/*     */   }
/*     */   
/*     */   public void setDamagePerBlock(double $$0) {
/* 401 */     this.damagePerBlock = $$0;
/*     */     
/* 403 */     for (BorderChangeListener $$1 : getListeners()) {
/* 404 */       $$1.onBorderSetDamagePerBlock(this, $$0);
/*     */     }
/*     */   }
/*     */   
/*     */   public double getLerpSpeed() {
/* 409 */     return this.extent.getLerpSpeed();
/*     */   }
/*     */   
/*     */   public int getWarningTime() {
/* 413 */     return this.warningTime;
/*     */   }
/*     */   
/*     */   public void setWarningTime(int $$0) {
/* 417 */     this.warningTime = $$0;
/*     */     
/* 419 */     for (BorderChangeListener $$1 : getListeners()) {
/* 420 */       $$1.onBorderSetWarningTime(this, $$0);
/*     */     }
/*     */   }
/*     */   
/*     */   public int getWarningBlocks() {
/* 425 */     return this.warningBlocks;
/*     */   }
/*     */   
/*     */   public void setWarningBlocks(int $$0) {
/* 429 */     this.warningBlocks = $$0;
/*     */     
/* 431 */     for (BorderChangeListener $$1 : getListeners()) {
/* 432 */       $$1.onBorderSetWarningBlocks(this, $$0);
/*     */     }
/*     */   }
/*     */   
/*     */   public void tick() {
/* 437 */     this.extent = this.extent.update();
/*     */   }
/*     */   
/*     */   public Settings createSettings() {
/* 441 */     return new Settings(this);
/*     */   }
/*     */   
/*     */   public void applySettings(Settings $$0) {
/* 445 */     setCenter($$0.getCenterX(), $$0.getCenterZ());
/* 446 */     setDamagePerBlock($$0.getDamagePerBlock());
/* 447 */     setDamageSafeZone($$0.getSafeZone());
/* 448 */     setWarningBlocks($$0.getWarningBlocks());
/* 449 */     setWarningTime($$0.getWarningTime());
/*     */     
/* 451 */     if ($$0.getSizeLerpTime() > 0L) {
/* 452 */       lerpSizeBetween($$0.getSize(), $$0.getSizeLerpTarget(), $$0.getSizeLerpTime());
/*     */     } else {
/* 454 */       setSize($$0.getSize());
/*     */     } 
/*     */   }
/*     */   
/* 458 */   public static final Settings DEFAULT_SETTINGS = new Settings(0.0D, 0.0D, 0.2D, 5.0D, 5, 15, 5.9999968E7D, 0L, 0.0D);
/*     */   
/*     */   public static class Settings {
/*     */     private final double centerX;
/*     */     private final double centerZ;
/*     */     private final double damagePerBlock;
/*     */     private final double safeZone;
/*     */     private final int warningBlocks;
/*     */     private final int warningTime;
/*     */     private final double size;
/*     */     private final long sizeLerpTime;
/*     */     private final double sizeLerpTarget;
/*     */     
/*     */     Settings(double $$0, double $$1, double $$2, double $$3, int $$4, int $$5, double $$6, long $$7, double $$8) {
/* 472 */       this.centerX = $$0;
/* 473 */       this.centerZ = $$1;
/* 474 */       this.damagePerBlock = $$2;
/* 475 */       this.safeZone = $$3;
/* 476 */       this.warningBlocks = $$4;
/* 477 */       this.warningTime = $$5;
/* 478 */       this.size = $$6;
/* 479 */       this.sizeLerpTime = $$7;
/* 480 */       this.sizeLerpTarget = $$8;
/*     */     }
/*     */     
/*     */     Settings(WorldBorder $$0) {
/* 484 */       this.centerX = $$0.getCenterX();
/* 485 */       this.centerZ = $$0.getCenterZ();
/* 486 */       this.damagePerBlock = $$0.getDamagePerBlock();
/* 487 */       this.safeZone = $$0.getDamageSafeZone();
/* 488 */       this.warningBlocks = $$0.getWarningBlocks();
/* 489 */       this.warningTime = $$0.getWarningTime();
/* 490 */       this.size = $$0.getSize();
/* 491 */       this.sizeLerpTime = $$0.getLerpRemainingTime();
/* 492 */       this.sizeLerpTarget = $$0.getLerpTarget();
/*     */     }
/*     */     
/*     */     public double getCenterX() {
/* 496 */       return this.centerX;
/*     */     }
/*     */     
/*     */     public double getCenterZ() {
/* 500 */       return this.centerZ;
/*     */     }
/*     */     
/*     */     public double getDamagePerBlock() {
/* 504 */       return this.damagePerBlock;
/*     */     }
/*     */     
/*     */     public double getSafeZone() {
/* 508 */       return this.safeZone;
/*     */     }
/*     */     
/*     */     public int getWarningBlocks() {
/* 512 */       return this.warningBlocks;
/*     */     }
/*     */     
/*     */     public int getWarningTime() {
/* 516 */       return this.warningTime;
/*     */     }
/*     */     
/*     */     public double getSize() {
/* 520 */       return this.size;
/*     */     }
/*     */     
/*     */     public long getSizeLerpTime() {
/* 524 */       return this.sizeLerpTime;
/*     */     }
/*     */     
/*     */     public double getSizeLerpTarget() {
/* 528 */       return this.sizeLerpTarget;
/*     */     }
/*     */     
/*     */     public static Settings read(DynamicLike<?> $$0, Settings $$1) {
/* 532 */       double $$2 = Mth.clamp($$0.get("BorderCenterX").asDouble($$1.centerX), -2.9999984E7D, 2.9999984E7D);
/* 533 */       double $$3 = Mth.clamp($$0.get("BorderCenterZ").asDouble($$1.centerZ), -2.9999984E7D, 2.9999984E7D);
/* 534 */       double $$4 = $$0.get("BorderSize").asDouble($$1.size);
/* 535 */       long $$5 = $$0.get("BorderSizeLerpTime").asLong($$1.sizeLerpTime);
/* 536 */       double $$6 = $$0.get("BorderSizeLerpTarget").asDouble($$1.sizeLerpTarget);
/* 537 */       double $$7 = $$0.get("BorderSafeZone").asDouble($$1.safeZone);
/* 538 */       double $$8 = $$0.get("BorderDamagePerBlock").asDouble($$1.damagePerBlock);
/* 539 */       int $$9 = $$0.get("BorderWarningBlocks").asInt($$1.warningBlocks);
/* 540 */       int $$10 = $$0.get("BorderWarningTime").asInt($$1.warningTime);
/*     */       
/* 542 */       return new Settings($$2, $$3, $$8, $$7, $$9, $$10, $$4, $$5, $$6);
/*     */     }
/*     */     
/*     */     public void write(CompoundTag $$0) {
/* 546 */       $$0.putDouble("BorderCenterX", this.centerX);
/* 547 */       $$0.putDouble("BorderCenterZ", this.centerZ);
/* 548 */       $$0.putDouble("BorderSize", this.size);
/* 549 */       $$0.putLong("BorderSizeLerpTime", this.sizeLerpTime);
/* 550 */       $$0.putDouble("BorderSafeZone", this.safeZone);
/* 551 */       $$0.putDouble("BorderDamagePerBlock", this.damagePerBlock);
/* 552 */       $$0.putDouble("BorderSizeLerpTarget", this.sizeLerpTarget);
/* 553 */       $$0.putDouble("BorderWarningBlocks", this.warningBlocks);
/* 554 */       $$0.putDouble("BorderWarningTime", this.warningTime);
/*     */     }
/*     */   }
/*     */   
/*     */   private static interface BorderExtent {
/*     */     double getMinX();
/*     */     
/*     */     double getMaxX();
/*     */     
/*     */     double getMinZ();
/*     */     
/*     */     double getMaxZ();
/*     */     
/*     */     double getSize();
/*     */     
/*     */     double getLerpSpeed();
/*     */     
/*     */     long getLerpRemainingTime();
/*     */     
/*     */     double getLerpTarget();
/*     */     
/*     */     BorderStatus getStatus();
/*     */     
/*     */     void onAbsoluteMaxSizeChange();
/*     */     
/*     */     void onCenterChange();
/*     */     
/*     */     BorderExtent update();
/*     */     
/*     */     VoxelShape getCollisionShape();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\border\WorldBorder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */