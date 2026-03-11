/*     */ package net.minecraft.core;
/*     */ 
/*     */ import java.util.function.Predicate;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.util.StringRepresentable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum Axis
/*     */   implements StringRepresentable, Predicate<Direction>
/*     */ {
/* 360 */   X("x")
/*     */   {
/*     */     public int choose(int $$0, int $$1, int $$2) {
/* 363 */       return $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public double choose(double $$0, double $$1, double $$2) {
/* 368 */       return $$0;
/*     */     }
/*     */   },
/* 371 */   Y("y")
/*     */   {
/*     */     public int choose(int $$0, int $$1, int $$2) {
/* 374 */       return $$1;
/*     */     }
/*     */ 
/*     */     
/*     */     public double choose(double $$0, double $$1, double $$2) {
/* 379 */       return $$1;
/*     */     }
/*     */   },
/* 382 */   Z("z")
/*     */   {
/*     */     public int choose(int $$0, int $$1, int $$2) {
/* 385 */       return $$2;
/*     */     }
/*     */ 
/*     */     
/*     */     public double choose(double $$0, double $$1, double $$2) {
/* 390 */       return $$2;
/*     */     } };
/*     */   public static final Axis[] VALUES; public static final StringRepresentable.EnumCodec<Axis> CODEC; private final String name;
/*     */   
/*     */   static {
/* 395 */     VALUES = values();
/*     */     
/* 397 */     CODEC = StringRepresentable.fromEnum(Axis::values);
/*     */   }
/*     */ 
/*     */   
/*     */   Axis(String $$0) {
/* 402 */     this.name = $$0;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static Axis byName(String $$0) {
/* 407 */     return (Axis)CODEC.byName($$0);
/*     */   }
/*     */   
/*     */   public String getName() {
/* 411 */     return this.name;
/*     */   }
/*     */   
/*     */   public boolean isVertical() {
/* 415 */     return (this == Y);
/*     */   }
/*     */   
/*     */   public boolean isHorizontal() {
/* 419 */     return (this == X || this == Z);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 424 */     return this.name;
/*     */   }
/*     */   
/*     */   public static Axis getRandom(RandomSource $$0) {
/* 428 */     return (Axis)Util.getRandom((Object[])VALUES, $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean test(@Nullable Direction $$0) {
/* 433 */     return ($$0 != null && $$0.getAxis() == this);
/*     */   }
/*     */   
/*     */   public Direction.Plane getPlane() {
/* 437 */     switch (Direction.null.$SwitchMap$net$minecraft$core$Direction$Axis[ordinal()]) { default: throw new IncompatibleClassChangeError();case 1: case 2: case 3: break; }  return 
/*     */       
/* 439 */       Direction.Plane.VERTICAL;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSerializedName() {
/* 445 */     return this.name;
/*     */   }
/*     */   
/*     */   public abstract int choose(int paramInt1, int paramInt2, int paramInt3);
/*     */   
/*     */   public abstract double choose(double paramDouble1, double paramDouble2, double paramDouble3);
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\Direction$Axis.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */