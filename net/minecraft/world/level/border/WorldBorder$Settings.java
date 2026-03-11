/*     */ package net.minecraft.world.level.border;
/*     */ 
/*     */ import com.mojang.serialization.DynamicLike;
/*     */ import net.minecraft.nbt.CompoundTag;
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
/*     */ public class Settings
/*     */ {
/*     */   private final double centerX;
/*     */   private final double centerZ;
/*     */   private final double damagePerBlock;
/*     */   private final double safeZone;
/*     */   private final int warningBlocks;
/*     */   private final int warningTime;
/*     */   private final double size;
/*     */   private final long sizeLerpTime;
/*     */   private final double sizeLerpTarget;
/*     */   
/*     */   Settings(double $$0, double $$1, double $$2, double $$3, int $$4, int $$5, double $$6, long $$7, double $$8) {
/* 472 */     this.centerX = $$0;
/* 473 */     this.centerZ = $$1;
/* 474 */     this.damagePerBlock = $$2;
/* 475 */     this.safeZone = $$3;
/* 476 */     this.warningBlocks = $$4;
/* 477 */     this.warningTime = $$5;
/* 478 */     this.size = $$6;
/* 479 */     this.sizeLerpTime = $$7;
/* 480 */     this.sizeLerpTarget = $$8;
/*     */   }
/*     */   
/*     */   Settings(WorldBorder $$0) {
/* 484 */     this.centerX = $$0.getCenterX();
/* 485 */     this.centerZ = $$0.getCenterZ();
/* 486 */     this.damagePerBlock = $$0.getDamagePerBlock();
/* 487 */     this.safeZone = $$0.getDamageSafeZone();
/* 488 */     this.warningBlocks = $$0.getWarningBlocks();
/* 489 */     this.warningTime = $$0.getWarningTime();
/* 490 */     this.size = $$0.getSize();
/* 491 */     this.sizeLerpTime = $$0.getLerpRemainingTime();
/* 492 */     this.sizeLerpTarget = $$0.getLerpTarget();
/*     */   }
/*     */   
/*     */   public double getCenterX() {
/* 496 */     return this.centerX;
/*     */   }
/*     */   
/*     */   public double getCenterZ() {
/* 500 */     return this.centerZ;
/*     */   }
/*     */   
/*     */   public double getDamagePerBlock() {
/* 504 */     return this.damagePerBlock;
/*     */   }
/*     */   
/*     */   public double getSafeZone() {
/* 508 */     return this.safeZone;
/*     */   }
/*     */   
/*     */   public int getWarningBlocks() {
/* 512 */     return this.warningBlocks;
/*     */   }
/*     */   
/*     */   public int getWarningTime() {
/* 516 */     return this.warningTime;
/*     */   }
/*     */   
/*     */   public double getSize() {
/* 520 */     return this.size;
/*     */   }
/*     */   
/*     */   public long getSizeLerpTime() {
/* 524 */     return this.sizeLerpTime;
/*     */   }
/*     */   
/*     */   public double getSizeLerpTarget() {
/* 528 */     return this.sizeLerpTarget;
/*     */   }
/*     */   
/*     */   public static Settings read(DynamicLike<?> $$0, Settings $$1) {
/* 532 */     double $$2 = Mth.clamp($$0.get("BorderCenterX").asDouble($$1.centerX), -2.9999984E7D, 2.9999984E7D);
/* 533 */     double $$3 = Mth.clamp($$0.get("BorderCenterZ").asDouble($$1.centerZ), -2.9999984E7D, 2.9999984E7D);
/* 534 */     double $$4 = $$0.get("BorderSize").asDouble($$1.size);
/* 535 */     long $$5 = $$0.get("BorderSizeLerpTime").asLong($$1.sizeLerpTime);
/* 536 */     double $$6 = $$0.get("BorderSizeLerpTarget").asDouble($$1.sizeLerpTarget);
/* 537 */     double $$7 = $$0.get("BorderSafeZone").asDouble($$1.safeZone);
/* 538 */     double $$8 = $$0.get("BorderDamagePerBlock").asDouble($$1.damagePerBlock);
/* 539 */     int $$9 = $$0.get("BorderWarningBlocks").asInt($$1.warningBlocks);
/* 540 */     int $$10 = $$0.get("BorderWarningTime").asInt($$1.warningTime);
/*     */     
/* 542 */     return new Settings($$2, $$3, $$8, $$7, $$9, $$10, $$4, $$5, $$6);
/*     */   }
/*     */   
/*     */   public void write(CompoundTag $$0) {
/* 546 */     $$0.putDouble("BorderCenterX", this.centerX);
/* 547 */     $$0.putDouble("BorderCenterZ", this.centerZ);
/* 548 */     $$0.putDouble("BorderSize", this.size);
/* 549 */     $$0.putLong("BorderSizeLerpTime", this.sizeLerpTime);
/* 550 */     $$0.putDouble("BorderSafeZone", this.safeZone);
/* 551 */     $$0.putDouble("BorderDamagePerBlock", this.damagePerBlock);
/* 552 */     $$0.putDouble("BorderSizeLerpTarget", this.sizeLerpTarget);
/* 553 */     $$0.putDouble("BorderWarningBlocks", this.warningBlocks);
/* 554 */     $$0.putDouble("BorderWarningTime", this.warningTime);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\border\WorldBorder$Settings.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */