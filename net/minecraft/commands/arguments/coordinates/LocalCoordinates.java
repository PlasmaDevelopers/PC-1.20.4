/*     */ package net.minecraft.commands.arguments.coordinates;
/*     */ 
/*     */ import com.mojang.brigadier.StringReader;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import java.util.Objects;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.phys.Vec2;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class LocalCoordinates
/*     */   implements Coordinates
/*     */ {
/*     */   public static final char PREFIX_LOCAL_COORDINATE = '^';
/*     */   private final double left;
/*     */   private final double up;
/*     */   private final double forwards;
/*     */   
/*     */   public LocalCoordinates(double $$0, double $$1, double $$2) {
/*  20 */     this.left = $$0;
/*  21 */     this.up = $$1;
/*  22 */     this.forwards = $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public Vec3 getPosition(CommandSourceStack $$0) {
/*  27 */     Vec2 $$1 = $$0.getRotation();
/*  28 */     Vec3 $$2 = $$0.getAnchor().apply($$0);
/*  29 */     float $$3 = Mth.cos(($$1.y + 90.0F) * 0.017453292F);
/*  30 */     float $$4 = Mth.sin(($$1.y + 90.0F) * 0.017453292F);
/*  31 */     float $$5 = Mth.cos(-$$1.x * 0.017453292F);
/*  32 */     float $$6 = Mth.sin(-$$1.x * 0.017453292F);
/*  33 */     float $$7 = Mth.cos((-$$1.x + 90.0F) * 0.017453292F);
/*  34 */     float $$8 = Mth.sin((-$$1.x + 90.0F) * 0.017453292F);
/*  35 */     Vec3 $$9 = new Vec3(($$3 * $$5), $$6, ($$4 * $$5));
/*  36 */     Vec3 $$10 = new Vec3(($$3 * $$7), $$8, ($$4 * $$7));
/*  37 */     Vec3 $$11 = $$9.cross($$10).scale(-1.0D);
/*  38 */     double $$12 = $$9.x * this.forwards + $$10.x * this.up + $$11.x * this.left;
/*  39 */     double $$13 = $$9.y * this.forwards + $$10.y * this.up + $$11.y * this.left;
/*  40 */     double $$14 = $$9.z * this.forwards + $$10.z * this.up + $$11.z * this.left;
/*  41 */     return new Vec3($$2.x + $$12, $$2.y + $$13, $$2.z + $$14);
/*     */   }
/*     */ 
/*     */   
/*     */   public Vec2 getRotation(CommandSourceStack $$0) {
/*  46 */     return Vec2.ZERO;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isXRelative() {
/*  51 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isYRelative() {
/*  56 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isZRelative() {
/*  61 */     return true;
/*     */   }
/*     */   
/*     */   public static LocalCoordinates parse(StringReader $$0) throws CommandSyntaxException {
/*  65 */     int $$1 = $$0.getCursor();
/*  66 */     double $$2 = readDouble($$0, $$1);
/*  67 */     if (!$$0.canRead() || $$0.peek() != ' ') {
/*  68 */       $$0.setCursor($$1);
/*  69 */       throw Vec3Argument.ERROR_NOT_COMPLETE.createWithContext($$0);
/*     */     } 
/*  71 */     $$0.skip();
/*  72 */     double $$3 = readDouble($$0, $$1);
/*  73 */     if (!$$0.canRead() || $$0.peek() != ' ') {
/*  74 */       $$0.setCursor($$1);
/*  75 */       throw Vec3Argument.ERROR_NOT_COMPLETE.createWithContext($$0);
/*     */     } 
/*  77 */     $$0.skip();
/*  78 */     double $$4 = readDouble($$0, $$1);
/*  79 */     return new LocalCoordinates($$2, $$3, $$4);
/*     */   }
/*     */   
/*     */   private static double readDouble(StringReader $$0, int $$1) throws CommandSyntaxException {
/*  83 */     if (!$$0.canRead()) {
/*  84 */       throw WorldCoordinate.ERROR_EXPECTED_DOUBLE.createWithContext($$0);
/*     */     }
/*     */     
/*  87 */     if ($$0.peek() != '^') {
/*  88 */       $$0.setCursor($$1);
/*  89 */       throw Vec3Argument.ERROR_MIXED_TYPE.createWithContext($$0);
/*     */     } 
/*  91 */     $$0.skip();
/*     */     
/*  93 */     return ($$0.canRead() && $$0.peek() != ' ') ? $$0.readDouble() : 0.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object $$0) {
/*  98 */     if (this == $$0) {
/*  99 */       return true;
/*     */     }
/* 101 */     if (!($$0 instanceof LocalCoordinates)) {
/* 102 */       return false;
/*     */     }
/*     */     
/* 105 */     LocalCoordinates $$1 = (LocalCoordinates)$$0;
/*     */     
/* 107 */     return (this.left == $$1.left && this.up == $$1.up && this.forwards == $$1.forwards);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 112 */     return Objects.hash(new Object[] { Double.valueOf(this.left), Double.valueOf(this.up), Double.valueOf(this.forwards) });
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\coordinates\LocalCoordinates.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */