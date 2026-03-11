/*     */ package net.minecraft.core;
/*     */ 
/*     */ import com.google.common.collect.Iterators;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.DataResult;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.function.Predicate;
/*     */ import java.util.stream.Stream;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.util.ExtraCodecs;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.util.StringRepresentable;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import org.joml.Matrix4f;
/*     */ import org.joml.Quaternionf;
/*     */ import org.joml.Vector3f;
/*     */ import org.joml.Vector4f;
/*     */ 
/*     */ public enum Direction
/*     */   implements StringRepresentable {
/*  27 */   DOWN(0, 1, -1, "down", AxisDirection.NEGATIVE, Axis.Y, new Vec3i(0, -1, 0)),
/*  28 */   UP(1, 0, -1, "up", AxisDirection.POSITIVE, Axis.Y, new Vec3i(0, 1, 0)),
/*  29 */   NORTH(2, 3, 2, "north", AxisDirection.NEGATIVE, Axis.Z, new Vec3i(0, 0, -1)),
/*  30 */   SOUTH(3, 2, 0, "south", AxisDirection.POSITIVE, Axis.Z, new Vec3i(0, 0, 1)),
/*  31 */   WEST(4, 5, 1, "west", AxisDirection.NEGATIVE, Axis.X, new Vec3i(-1, 0, 0)),
/*  32 */   EAST(5, 4, 3, "east", AxisDirection.POSITIVE, Axis.X, new Vec3i(1, 0, 0)); public static final StringRepresentable.EnumCodec<Direction> CODEC; public static final Codec<Direction> VERTICAL_CODEC; private final int data3d; private final int oppositeIndex; private final int data2d; private final String name; private final Axis axis; private final AxisDirection axisDirection; private final Vec3i normal; private static final Direction[] VALUES; private static final Direction[] BY_3D_DATA; private static final Direction[] BY_2D_DATA;
/*     */   
/*     */   static {
/*  35 */     CODEC = StringRepresentable.fromEnum(Direction::values);
/*  36 */     VERTICAL_CODEC = ExtraCodecs.validate((Codec)CODEC, Direction::verifyVertical);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  46 */     VALUES = values();
/*     */     
/*  48 */     BY_3D_DATA = (Direction[])Arrays.<Direction>stream(VALUES).sorted(Comparator.comparingInt($$0 -> $$0.data3d)).toArray($$0 -> new Direction[$$0]);
/*  49 */     BY_2D_DATA = (Direction[])Arrays.<Direction>stream(VALUES).filter($$0 -> $$0.getAxis().isHorizontal()).sorted(Comparator.comparingInt($$0 -> $$0.data2d)).toArray($$0 -> new Direction[$$0]);
/*     */   }
/*     */   Direction(int $$0, int $$1, int $$2, String $$3, AxisDirection $$4, Axis $$5, Vec3i $$6) {
/*  52 */     this.data3d = $$0;
/*  53 */     this.data2d = $$2;
/*  54 */     this.oppositeIndex = $$1;
/*  55 */     this.name = $$3;
/*  56 */     this.axis = $$5;
/*  57 */     this.axisDirection = $$4;
/*  58 */     this.normal = $$6;
/*     */   }
/*     */   
/*     */   public static Direction[] orderedByNearest(Entity $$0) {
/*  62 */     float $$1 = $$0.getViewXRot(1.0F) * 0.017453292F;
/*  63 */     float $$2 = -$$0.getViewYRot(1.0F) * 0.017453292F;
/*     */     
/*  65 */     float $$3 = Mth.sin($$1);
/*  66 */     float $$4 = Mth.cos($$1);
/*  67 */     float $$5 = Mth.sin($$2);
/*  68 */     float $$6 = Mth.cos($$2);
/*     */     
/*  70 */     boolean $$7 = ($$5 > 0.0F);
/*  71 */     boolean $$8 = ($$3 < 0.0F);
/*  72 */     boolean $$9 = ($$6 > 0.0F);
/*     */     
/*  74 */     float $$10 = $$7 ? $$5 : -$$5;
/*  75 */     float $$11 = $$8 ? -$$3 : $$3;
/*  76 */     float $$12 = $$9 ? $$6 : -$$6;
/*     */     
/*  78 */     float $$13 = $$10 * $$4;
/*  79 */     float $$14 = $$12 * $$4;
/*     */     
/*  81 */     Direction $$15 = $$7 ? EAST : WEST;
/*  82 */     Direction $$16 = $$8 ? UP : DOWN;
/*  83 */     Direction $$17 = $$9 ? SOUTH : NORTH;
/*     */     
/*  85 */     if ($$10 > $$12) {
/*  86 */       if ($$11 > $$13)
/*  87 */         return makeDirectionArray($$16, $$15, $$17); 
/*  88 */       if ($$14 > $$11) {
/*  89 */         return makeDirectionArray($$15, $$17, $$16);
/*     */       }
/*  91 */       return makeDirectionArray($$15, $$16, $$17);
/*     */     } 
/*     */     
/*  94 */     if ($$11 > $$14)
/*  95 */       return makeDirectionArray($$16, $$17, $$15); 
/*  96 */     if ($$13 > $$11) {
/*  97 */       return makeDirectionArray($$17, $$15, $$16);
/*     */     }
/*  99 */     return makeDirectionArray($$17, $$16, $$15);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static Direction[] makeDirectionArray(Direction $$0, Direction $$1, Direction $$2) {
/* 105 */     return new Direction[] { $$0, $$1, $$2, $$2.getOpposite(), $$1.getOpposite(), $$0.getOpposite() };
/*     */   }
/*     */   
/*     */   public static Direction rotate(Matrix4f $$0, Direction $$1) {
/* 109 */     Vec3i $$2 = $$1.getNormal();
/* 110 */     Vector4f $$3 = $$0.transform(new Vector4f($$2.getX(), $$2.getY(), $$2.getZ(), 0.0F));
/* 111 */     return getNearest($$3.x(), $$3.y(), $$3.z());
/*     */   }
/*     */   
/*     */   public static Collection<Direction> allShuffled(RandomSource $$0) {
/* 115 */     return Util.shuffledCopy((Object[])values(), $$0);
/*     */   }
/*     */   
/*     */   public static Stream<Direction> stream() {
/* 119 */     return Stream.of(VALUES);
/*     */   }
/*     */   
/*     */   public Quaternionf getRotation() {
/* 123 */     switch (this) { default: throw new IncompatibleClassChangeError();case X: case Z: case Y: case null: case null: case null: break; }  return (
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 129 */       new Quaternionf()).rotationXYZ(1.5707964F, 0.0F, -1.5707964F);
/*     */   }
/*     */ 
/*     */   
/*     */   public int get3DDataValue() {
/* 134 */     return this.data3d;
/*     */   }
/*     */   
/*     */   public int get2DDataValue() {
/* 138 */     return this.data2d;
/*     */   }
/*     */   
/*     */   public AxisDirection getAxisDirection() {
/* 142 */     return this.axisDirection;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Direction getFacingAxis(Entity $$0, Axis $$1) {
/* 149 */     switch ($$1) { default: throw new IncompatibleClassChangeError();case X: return 
/* 150 */           EAST.isFacingAngle($$0.getViewYRot(1.0F)) ? EAST : WEST;
/* 151 */       case Z: return SOUTH.isFacingAngle($$0.getViewYRot(1.0F)) ? SOUTH : NORTH;
/* 152 */       case Y: break; }  return ($$0.getViewXRot(1.0F) < 0.0F) ? UP : DOWN;
/*     */   }
/*     */ 
/*     */   
/*     */   public Direction getOpposite() {
/* 157 */     return from3DDataValue(this.oppositeIndex);
/*     */   }
/*     */   
/*     */   public Direction getClockWise(Axis $$0) {
/* 161 */     switch ($$0) { default: throw new IncompatibleClassChangeError();case X: return 
/* 162 */           (this == WEST || this == EAST) ? this : getClockWiseX();
/* 163 */       case Y: return (this == UP || this == DOWN) ? this : getClockWise();
/* 164 */       case Z: break; }  return (this == NORTH || this == SOUTH) ? this : getClockWiseZ();
/*     */   }
/*     */ 
/*     */   
/*     */   public Direction getCounterClockWise(Axis $$0) {
/* 169 */     switch ($$0) { default: throw new IncompatibleClassChangeError();case X: return 
/* 170 */           (this == WEST || this == EAST) ? this : getCounterClockWiseX();
/* 171 */       case Y: return (this == UP || this == DOWN) ? this : getCounterClockWise();
/* 172 */       case Z: break; }  return (this == NORTH || this == SOUTH) ? this : getCounterClockWiseZ();
/*     */   }
/*     */ 
/*     */   
/*     */   public Direction getClockWise() {
/* 177 */     switch (this) { case Y: 
/*     */       case null: 
/*     */       case null:
/*     */       
/*     */       case null:
/* 182 */        }  throw new IllegalStateException("Unable to get Y-rotated facing of " + this);
/*     */   }
/*     */ 
/*     */   
/*     */   private Direction getClockWiseX() {
/* 187 */     switch (this) { case Z: 
/*     */       case Y: 
/*     */       case X:
/*     */       
/*     */       case null:
/* 192 */        }  throw new IllegalStateException("Unable to get X-rotated facing of " + this);
/*     */   }
/*     */ 
/*     */   
/*     */   private Direction getCounterClockWiseX() {
/* 197 */     switch (this) { case Z: 
/*     */       case null: 
/*     */       case X:
/*     */       
/*     */       case Y:
/* 202 */        }  throw new IllegalStateException("Unable to get X-rotated facing of " + this);
/*     */   }
/*     */ 
/*     */   
/*     */   private Direction getClockWiseZ() {
/* 207 */     switch (this) { case Z: 
/*     */       case null: 
/*     */       case X:
/*     */       
/*     */       case null:
/* 212 */        }  throw new IllegalStateException("Unable to get Z-rotated facing of " + this);
/*     */   }
/*     */ 
/*     */   
/*     */   private Direction getCounterClockWiseZ() {
/* 217 */     switch (this) { case Z: 
/*     */       case null: 
/*     */       case X:
/*     */       
/*     */       case null:
/* 222 */        }  throw new IllegalStateException("Unable to get Z-rotated facing of " + this);
/*     */   }
/*     */ 
/*     */   
/*     */   public Direction getCounterClockWise() {
/* 227 */     switch (this) { case Y: 
/*     */       case null: 
/*     */       case null:
/*     */       
/*     */       case null:
/* 232 */        }  throw new IllegalStateException("Unable to get CCW facing of " + this);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getStepX() {
/* 237 */     return this.normal.getX();
/*     */   }
/*     */   
/*     */   public int getStepY() {
/* 241 */     return this.normal.getY();
/*     */   }
/*     */   
/*     */   public int getStepZ() {
/* 245 */     return this.normal.getZ();
/*     */   }
/*     */   
/*     */   public Vector3f step() {
/* 249 */     return new Vector3f(getStepX(), getStepY(), getStepZ());
/*     */   }
/*     */   
/*     */   public String getName() {
/* 253 */     return this.name;
/*     */   }
/*     */   
/*     */   public Axis getAxis() {
/* 257 */     return this.axis;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static Direction byName(@Nullable String $$0) {
/* 262 */     return (Direction)CODEC.byName($$0);
/*     */   }
/*     */   
/*     */   public static Direction from3DDataValue(int $$0) {
/* 266 */     return BY_3D_DATA[Mth.abs($$0 % BY_3D_DATA.length)];
/*     */   }
/*     */   
/*     */   public static Direction from2DDataValue(int $$0) {
/* 270 */     return BY_2D_DATA[Mth.abs($$0 % BY_2D_DATA.length)];
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static Direction fromDelta(int $$0, int $$1, int $$2) {
/* 275 */     if ($$0 == 0) {
/* 276 */       if ($$1 == 0) {
/* 277 */         if ($$2 > 0) {
/* 278 */           return SOUTH;
/*     */         }
/* 280 */         if ($$2 < 0) {
/* 281 */           return NORTH;
/*     */         }
/* 283 */       } else if ($$2 == 0) {
/* 284 */         if ($$1 > 0) {
/* 285 */           return UP;
/*     */         }
/* 287 */         return DOWN;
/*     */       } 
/* 289 */     } else if ($$1 == 0 && $$2 == 0) {
/* 290 */       if ($$0 > 0) {
/* 291 */         return EAST;
/*     */       }
/* 293 */       return WEST;
/*     */     } 
/* 295 */     return null;
/*     */   }
/*     */   
/*     */   public static Direction fromYRot(double $$0) {
/* 299 */     return from2DDataValue(Mth.floor($$0 / 90.0D + 0.5D) & 0x3);
/*     */   }
/*     */   
/*     */   public static Direction fromAxisAndDirection(Axis $$0, AxisDirection $$1) {
/* 303 */     switch ($$0) { default: throw new IncompatibleClassChangeError();case X: return 
/* 304 */           ($$1 == AxisDirection.POSITIVE) ? EAST : WEST;
/* 305 */       case Y: return ($$1 == AxisDirection.POSITIVE) ? UP : DOWN;
/* 306 */       case Z: break; }  return ($$1 == AxisDirection.POSITIVE) ? SOUTH : NORTH;
/*     */   }
/*     */ 
/*     */   
/*     */   public float toYRot() {
/* 311 */     return ((this.data2d & 0x3) * 90);
/*     */   }
/*     */   
/*     */   public static Direction getRandom(RandomSource $$0) {
/* 315 */     return (Direction)Util.getRandom((Object[])VALUES, $$0);
/*     */   }
/*     */   
/*     */   public static Direction getNearest(double $$0, double $$1, double $$2) {
/* 319 */     return getNearest((float)$$0, (float)$$1, (float)$$2);
/*     */   }
/*     */   
/*     */   public static Direction getNearest(float $$0, float $$1, float $$2) {
/* 323 */     Direction $$3 = NORTH;
/* 324 */     float $$4 = Float.MIN_VALUE;
/* 325 */     for (Direction $$5 : VALUES) {
/* 326 */       float $$6 = $$0 * $$5.normal.getX() + $$1 * $$5.normal.getY() + $$2 * $$5.normal.getZ();
/*     */       
/* 328 */       if ($$6 > $$4) {
/* 329 */         $$4 = $$6;
/* 330 */         $$3 = $$5;
/*     */       } 
/*     */     } 
/* 333 */     return $$3;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 338 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSerializedName() {
/* 343 */     return this.name;
/*     */   }
/*     */   
/*     */   private static DataResult<Direction> verifyVertical(Direction $$0) {
/* 347 */     return $$0.getAxis().isVertical() ? DataResult.success($$0) : DataResult.error(() -> "Expected a vertical direction");
/*     */   }
/*     */   
/*     */   public static Direction get(AxisDirection $$0, Axis $$1) {
/* 351 */     for (Direction $$2 : VALUES) {
/* 352 */       if ($$2.getAxisDirection() == $$0 && $$2.getAxis() == $$1) {
/* 353 */         return $$2;
/*     */       }
/*     */     } 
/* 356 */     throw new IllegalArgumentException("No such direction: " + $$0 + " " + $$1);
/*     */   }
/*     */   
/*     */   public enum Axis implements StringRepresentable, Predicate<Direction> {
/* 360 */     X("x")
/*     */     {
/*     */       public int choose(int $$0, int $$1, int $$2) {
/* 363 */         return $$0;
/*     */       }
/*     */ 
/*     */       
/*     */       public double choose(double $$0, double $$1, double $$2) {
/* 368 */         return $$0;
/*     */       }
/*     */     },
/* 371 */     Y("y")
/*     */     {
/*     */       public int choose(int $$0, int $$1, int $$2) {
/* 374 */         return $$1;
/*     */       }
/*     */ 
/*     */       
/*     */       public double choose(double $$0, double $$1, double $$2) {
/* 379 */         return $$1;
/*     */       }
/*     */     },
/* 382 */     Z("z")
/*     */     {
/*     */       public int choose(int $$0, int $$1, int $$2) {
/* 385 */         return $$2;
/*     */       }
/*     */ 
/*     */       
/*     */       public double choose(double $$0, double $$1, double $$2) {
/* 390 */         return $$2;
/*     */       }
/*     */     };
/*     */ 
/*     */     
/* 395 */     public static final Axis[] VALUES = values();
/*     */     
/* 397 */     public static final StringRepresentable.EnumCodec<Axis> CODEC = StringRepresentable.fromEnum(Axis::values); private final String name;
/*     */     static {
/*     */     
/*     */     }
/*     */     Axis(String $$0) {
/* 402 */       this.name = $$0;
/*     */     }
/*     */     
/*     */     @Nullable
/*     */     public static Axis byName(String $$0) {
/* 407 */       return (Axis)CODEC.byName($$0);
/*     */     }
/*     */     
/*     */     public String getName() {
/* 411 */       return this.name;
/*     */     }
/*     */     
/*     */     public boolean isVertical() {
/* 415 */       return (this == Y);
/*     */     }
/*     */     
/*     */     public boolean isHorizontal() {
/* 419 */       return (this == X || this == Z);
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 424 */       return this.name;
/*     */     }
/*     */     
/*     */     public static Axis getRandom(RandomSource $$0) {
/* 428 */       return (Axis)Util.getRandom((Object[])VALUES, $$0);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean test(@Nullable Direction $$0) {
/* 433 */       return ($$0 != null && $$0.getAxis() == this);
/*     */     }
/*     */     
/*     */     public Direction.Plane getPlane() {
/*     */       // Byte code:
/*     */       //   0: getstatic net/minecraft/core/Direction$1.$SwitchMap$net$minecraft$core$Direction$Axis : [I
/*     */       //   3: aload_0
/*     */       //   4: invokevirtual ordinal : ()I
/*     */       //   7: iaload
/*     */       //   8: tableswitch default -> 36, 1 -> 44, 2 -> 44, 3 -> 50
/*     */       //   36: new java/lang/IncompatibleClassChangeError
/*     */       //   39: dup
/*     */       //   40: invokespecial <init> : ()V
/*     */       //   43: athrow
/*     */       //   44: getstatic net/minecraft/core/Direction$Plane.HORIZONTAL : Lnet/minecraft/core/Direction$Plane;
/*     */       //   47: goto -> 53
/*     */       //   50: getstatic net/minecraft/core/Direction$Plane.VERTICAL : Lnet/minecraft/core/Direction$Plane;
/*     */       //   53: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #437	-> 0
/*     */       //   #438	-> 44
/*     */       //   #439	-> 50
/*     */       //   #437	-> 53
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	54	0	this	Lnet/minecraft/core/Direction$Axis;
/*     */     }
/*     */     
/*     */     public abstract int choose(int param1Int1, int param1Int2, int param1Int3);
/*     */     
/*     */     public abstract double choose(double param1Double1, double param1Double2, double param1Double3);
/*     */     
/*     */     public String getSerializedName() {
/* 445 */       return this.name;
/*     */     } }
/*     */   enum null {
/*     */     public int choose(int $$0, int $$1, int $$2) { return $$0; }
/*     */     public double choose(double $$0, double $$1, double $$2) { return $$0; }
/*     */   } enum null {
/*     */     public int choose(int $$0, int $$1, int $$2) { return $$1; } public double choose(double $$0, double $$1, double $$2) { return $$1; }
/*     */   } enum null {
/*     */     public int choose(int $$0, int $$1, int $$2) { return $$2; } public double choose(double $$0, double $$1, double $$2) { return $$2; }
/* 454 */   } public enum AxisDirection { POSITIVE(1, "Towards positive"),
/* 455 */     NEGATIVE(-1, "Towards negative");
/*     */     
/*     */     private final int step;
/*     */     
/*     */     private final String name;
/*     */     
/*     */     AxisDirection(int $$0, String $$1) {
/* 462 */       this.step = $$0;
/* 463 */       this.name = $$1;
/*     */     }
/*     */     
/*     */     public int getStep() {
/* 467 */       return this.step;
/*     */     }
/*     */     
/*     */     public String getName() {
/* 471 */       return this.name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 476 */       return this.name;
/*     */     }
/*     */     
/*     */     public AxisDirection opposite() {
/* 480 */       return (this == POSITIVE) ? NEGATIVE : POSITIVE;
/*     */     } }
/*     */ 
/*     */   
/*     */   public Vec3i getNormal() {
/* 485 */     return this.normal;
/*     */   }
/*     */   
/*     */   public boolean isFacingAngle(float $$0) {
/* 489 */     float $$1 = $$0 * 0.017453292F;
/* 490 */     float $$2 = -Mth.sin($$1);
/* 491 */     float $$3 = Mth.cos($$1);
/* 492 */     return (this.normal.getX() * $$2 + this.normal.getZ() * $$3 > 0.0F);
/*     */   }
/*     */   
/*     */   public enum Plane implements Iterable<Direction>, Predicate<Direction> {
/* 496 */     HORIZONTAL((String)new Direction[] { Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST }, new Direction.Axis[] { Direction.Axis.X, Direction.Axis.Z }),
/* 497 */     VERTICAL((String)new Direction[] { Direction.UP, Direction.DOWN }, new Direction.Axis[] { Direction.Axis.Y });
/*     */     
/*     */     private final Direction[] faces;
/*     */     
/*     */     private final Direction.Axis[] axis;
/*     */     
/*     */     Plane(Direction[] $$0, Direction.Axis[] $$1) {
/* 504 */       this.faces = $$0;
/* 505 */       this.axis = $$1;
/*     */     }
/*     */     
/*     */     public Direction getRandomDirection(RandomSource $$0) {
/* 509 */       return (Direction)Util.getRandom((Object[])this.faces, $$0);
/*     */     }
/*     */     
/*     */     public Direction.Axis getRandomAxis(RandomSource $$0) {
/* 513 */       return (Direction.Axis)Util.getRandom((Object[])this.axis, $$0);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean test(@Nullable Direction $$0) {
/* 518 */       return ($$0 != null && $$0.getAxis().getPlane() == this);
/*     */     }
/*     */ 
/*     */     
/*     */     public Iterator<Direction> iterator() {
/* 523 */       return (Iterator<Direction>)Iterators.forArray((Object[])this.faces);
/*     */     }
/*     */     
/*     */     public Stream<Direction> stream() {
/* 527 */       return Arrays.stream(this.faces);
/*     */     }
/*     */     
/*     */     public List<Direction> shuffledCopy(RandomSource $$0) {
/* 531 */       return Util.shuffledCopy((Object[])this.faces, $$0);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\Direction.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */