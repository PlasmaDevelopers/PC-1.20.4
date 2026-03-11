/*    */ package net.minecraft.world.phys;
/*    */ 
/*    */ import net.minecraft.util.Mth;
/*    */ 
/*    */ public class Vec2
/*    */ {
/*  7 */   public static final Vec2 ZERO = new Vec2(0.0F, 0.0F);
/*  8 */   public static final Vec2 ONE = new Vec2(1.0F, 1.0F);
/*  9 */   public static final Vec2 UNIT_X = new Vec2(1.0F, 0.0F);
/* 10 */   public static final Vec2 NEG_UNIT_X = new Vec2(-1.0F, 0.0F);
/* 11 */   public static final Vec2 UNIT_Y = new Vec2(0.0F, 1.0F);
/* 12 */   public static final Vec2 NEG_UNIT_Y = new Vec2(0.0F, -1.0F);
/* 13 */   public static final Vec2 MAX = new Vec2(Float.MAX_VALUE, Float.MAX_VALUE);
/* 14 */   public static final Vec2 MIN = new Vec2(Float.MIN_VALUE, Float.MIN_VALUE);
/*    */   
/*    */   public final float x;
/*    */   public final float y;
/*    */   
/*    */   public Vec2(float $$0, float $$1) {
/* 20 */     this.x = $$0;
/* 21 */     this.y = $$1;
/*    */   }
/*    */   
/*    */   public Vec2 scale(float $$0) {
/* 25 */     return new Vec2(this.x * $$0, this.y * $$0);
/*    */   }
/*    */   
/*    */   public float dot(Vec2 $$0) {
/* 29 */     return this.x * $$0.x + this.y * $$0.y;
/*    */   }
/*    */   
/*    */   public Vec2 add(Vec2 $$0) {
/* 33 */     return new Vec2(this.x + $$0.x, this.y + $$0.y);
/*    */   }
/*    */   
/*    */   public Vec2 add(float $$0) {
/* 37 */     return new Vec2(this.x + $$0, this.y + $$0);
/*    */   }
/*    */   
/*    */   public boolean equals(Vec2 $$0) {
/* 41 */     return (this.x == $$0.x && this.y == $$0.y);
/*    */   }
/*    */   
/*    */   public Vec2 normalized() {
/* 45 */     float $$0 = Mth.sqrt(this.x * this.x + this.y * this.y);
/* 46 */     return ($$0 < 1.0E-4F) ? ZERO : new Vec2(this.x / $$0, this.y / $$0);
/*    */   }
/*    */   
/*    */   public float length() {
/* 50 */     return Mth.sqrt(this.x * this.x + this.y * this.y);
/*    */   }
/*    */   
/*    */   public float lengthSquared() {
/* 54 */     return this.x * this.x + this.y * this.y;
/*    */   }
/*    */   
/*    */   public float distanceToSqr(Vec2 $$0) {
/* 58 */     float $$1 = $$0.x - this.x;
/* 59 */     float $$2 = $$0.y - this.y;
/* 60 */     return $$1 * $$1 + $$2 * $$2;
/*    */   }
/*    */   
/*    */   public Vec2 negated() {
/* 64 */     return new Vec2(-this.x, -this.y);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\phys\Vec2.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */