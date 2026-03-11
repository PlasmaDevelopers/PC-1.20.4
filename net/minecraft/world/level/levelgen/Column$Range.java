/*     */ package net.minecraft.world.level.levelgen;
/*     */ 
/*     */ import java.util.OptionalInt;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Range
/*     */   extends Column
/*     */ {
/*     */   private final int floor;
/*     */   private final int ceiling;
/*     */   
/*     */   protected Range(int $$0, int $$1) {
/* 136 */     this.floor = $$0;
/* 137 */     this.ceiling = $$1;
/* 138 */     if (height() < 0) {
/* 139 */       throw new IllegalArgumentException("Column of negative height: " + this);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public OptionalInt getCeiling() {
/* 145 */     return OptionalInt.of(this.ceiling);
/*     */   }
/*     */ 
/*     */   
/*     */   public OptionalInt getFloor() {
/* 150 */     return OptionalInt.of(this.floor);
/*     */   }
/*     */ 
/*     */   
/*     */   public OptionalInt getHeight() {
/* 155 */     return OptionalInt.of(height());
/*     */   }
/*     */   
/*     */   public int ceiling() {
/* 159 */     return this.ceiling;
/*     */   }
/*     */   
/*     */   public int floor() {
/* 163 */     return this.floor;
/*     */   }
/*     */   
/*     */   public int height() {
/* 167 */     return this.ceiling - this.floor - 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 172 */     return "C(" + this.ceiling + "-" + this.floor + ")";
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\Column$Range.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */