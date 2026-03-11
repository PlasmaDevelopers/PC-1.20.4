/*     */ package net.minecraft.world.level.levelgen;
/*     */ 
/*     */ import java.util.Optional;
/*     */ import java.util.OptionalInt;
/*     */ import java.util.function.Predicate;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.world.level.LevelSimulatedReader;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Column
/*     */ {
/*     */   public static Range around(int $$0, int $$1) {
/*  23 */     return new Range($$0 - 1, $$1 + 1);
/*     */   }
/*     */   
/*     */   public static Range inside(int $$0, int $$1) {
/*  27 */     return new Range($$0, $$1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Column below(int $$0) {
/*  34 */     return new Ray($$0, false);
/*     */   }
/*     */   
/*     */   public static Column fromHighest(int $$0) {
/*  38 */     return new Ray($$0 + 1, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Column above(int $$0) {
/*  45 */     return new Ray($$0, true);
/*     */   }
/*     */   
/*     */   public static Column fromLowest(int $$0) {
/*  49 */     return new Ray($$0 - 1, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Column line() {
/*  56 */     return Line.INSTANCE;
/*     */   }
/*     */   
/*     */   public static Column create(OptionalInt $$0, OptionalInt $$1) {
/*  60 */     if ($$0.isPresent() && $$1.isPresent()) {
/*  61 */       return inside($$0.getAsInt(), $$1.getAsInt());
/*     */     }
/*     */     
/*  64 */     if ($$0.isPresent()) {
/*  65 */       return above($$0.getAsInt());
/*     */     }
/*     */     
/*  68 */     if ($$1.isPresent()) {
/*  69 */       return below($$1.getAsInt());
/*     */     }
/*     */     
/*  72 */     return line();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract OptionalInt getCeiling();
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract OptionalInt getFloor();
/*     */ 
/*     */   
/*     */   public abstract OptionalInt getHeight();
/*     */ 
/*     */   
/*     */   public Column withFloor(OptionalInt $$0) {
/*  88 */     return create($$0, getCeiling());
/*     */   }
/*     */   
/*     */   public Column withCeiling(OptionalInt $$0) {
/*  92 */     return create(getFloor(), $$0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Optional<Column> scan(LevelSimulatedReader $$0, BlockPos $$1, int $$2, Predicate<BlockState> $$3, Predicate<BlockState> $$4) {
/* 102 */     BlockPos.MutableBlockPos $$5 = $$1.mutable();
/* 103 */     if (!$$0.isStateAtPosition($$1, $$3)) {
/* 104 */       return Optional.empty();
/*     */     }
/*     */ 
/*     */     
/* 108 */     int $$6 = $$1.getY();
/* 109 */     OptionalInt $$7 = scanDirection($$0, $$2, $$3, $$4, $$5, $$6, Direction.UP);
/* 110 */     OptionalInt $$8 = scanDirection($$0, $$2, $$3, $$4, $$5, $$6, Direction.DOWN);
/*     */     
/* 112 */     return Optional.of(create($$8, $$7));
/*     */   }
/*     */   
/*     */   private static OptionalInt scanDirection(LevelSimulatedReader $$0, int $$1, Predicate<BlockState> $$2, Predicate<BlockState> $$3, BlockPos.MutableBlockPos $$4, int $$5, Direction $$6) {
/* 116 */     $$4.setY($$5);
/* 117 */     for (int $$7 = 1; $$7 < $$1 && 
/* 118 */       $$0.isStateAtPosition((BlockPos)$$4, $$2); $$7++) {
/* 119 */       $$4.move($$6);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 125 */     return $$0.isStateAtPosition((BlockPos)$$4, $$3) ? OptionalInt.of($$4.getY()) : OptionalInt.empty();
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class Range
/*     */     extends Column
/*     */   {
/*     */     private final int floor;
/*     */     private final int ceiling;
/*     */     
/*     */     protected Range(int $$0, int $$1) {
/* 136 */       this.floor = $$0;
/* 137 */       this.ceiling = $$1;
/* 138 */       if (height() < 0) {
/* 139 */         throw new IllegalArgumentException("Column of negative height: " + this);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public OptionalInt getCeiling() {
/* 145 */       return OptionalInt.of(this.ceiling);
/*     */     }
/*     */ 
/*     */     
/*     */     public OptionalInt getFloor() {
/* 150 */       return OptionalInt.of(this.floor);
/*     */     }
/*     */ 
/*     */     
/*     */     public OptionalInt getHeight() {
/* 155 */       return OptionalInt.of(height());
/*     */     }
/*     */     
/*     */     public int ceiling() {
/* 159 */       return this.ceiling;
/*     */     }
/*     */     
/*     */     public int floor() {
/* 163 */       return this.floor;
/*     */     }
/*     */     
/*     */     public int height() {
/* 167 */       return this.ceiling - this.floor - 1;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 172 */       return "C(" + this.ceiling + "-" + this.floor + ")";
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class Line
/*     */     extends Column
/*     */   {
/* 180 */     static final Line INSTANCE = new Line();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public OptionalInt getCeiling() {
/* 187 */       return OptionalInt.empty();
/*     */     }
/*     */ 
/*     */     
/*     */     public OptionalInt getFloor() {
/* 192 */       return OptionalInt.empty();
/*     */     }
/*     */ 
/*     */     
/*     */     public OptionalInt getHeight() {
/* 197 */       return OptionalInt.empty();
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 202 */       return "C(-)";
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class Ray
/*     */     extends Column
/*     */   {
/*     */     private final int edge;
/*     */     private final boolean pointingUp;
/*     */     
/*     */     public Ray(int $$0, boolean $$1) {
/* 214 */       this.edge = $$0;
/* 215 */       this.pointingUp = $$1;
/*     */     }
/*     */ 
/*     */     
/*     */     public OptionalInt getCeiling() {
/* 220 */       return this.pointingUp ? OptionalInt.empty() : OptionalInt.of(this.edge);
/*     */     }
/*     */ 
/*     */     
/*     */     public OptionalInt getFloor() {
/* 225 */       return this.pointingUp ? OptionalInt.of(this.edge) : OptionalInt.empty();
/*     */     }
/*     */ 
/*     */     
/*     */     public OptionalInt getHeight() {
/* 230 */       return OptionalInt.empty();
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 235 */       return this.pointingUp ? ("C(" + this.edge + "-)") : ("C(-" + this.edge + ")");
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\Column.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */