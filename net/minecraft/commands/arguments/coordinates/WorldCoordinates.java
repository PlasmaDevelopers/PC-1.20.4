/*     */ package net.minecraft.commands.arguments.coordinates;
/*     */ 
/*     */ import com.mojang.brigadier.StringReader;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.world.phys.Vec2;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class WorldCoordinates implements Coordinates {
/*     */   private final WorldCoordinate x;
/*     */   private final WorldCoordinate y;
/*     */   private final WorldCoordinate z;
/*     */   
/*     */   public WorldCoordinates(WorldCoordinate $$0, WorldCoordinate $$1, WorldCoordinate $$2) {
/*  15 */     this.x = $$0;
/*  16 */     this.y = $$1;
/*  17 */     this.z = $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public Vec3 getPosition(CommandSourceStack $$0) {
/*  22 */     Vec3 $$1 = $$0.getPosition();
/*  23 */     return new Vec3(this.x.get($$1.x), this.y.get($$1.y), this.z.get($$1.z));
/*     */   }
/*     */ 
/*     */   
/*     */   public Vec2 getRotation(CommandSourceStack $$0) {
/*  28 */     Vec2 $$1 = $$0.getRotation();
/*  29 */     return new Vec2((float)this.x.get($$1.x), (float)this.y.get($$1.y));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isXRelative() {
/*  34 */     return this.x.isRelative();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isYRelative() {
/*  39 */     return this.y.isRelative();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isZRelative() {
/*  44 */     return this.z.isRelative();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object $$0) {
/*  49 */     if (this == $$0) {
/*  50 */       return true;
/*     */     }
/*  52 */     if (!($$0 instanceof WorldCoordinates)) {
/*  53 */       return false;
/*     */     }
/*     */     
/*  56 */     WorldCoordinates $$1 = (WorldCoordinates)$$0;
/*     */     
/*  58 */     if (!this.x.equals($$1.x)) {
/*  59 */       return false;
/*     */     }
/*  61 */     if (!this.y.equals($$1.y)) {
/*  62 */       return false;
/*     */     }
/*  64 */     return this.z.equals($$1.z);
/*     */   }
/*     */   
/*     */   public static WorldCoordinates parseInt(StringReader $$0) throws CommandSyntaxException {
/*  68 */     int $$1 = $$0.getCursor();
/*  69 */     WorldCoordinate $$2 = WorldCoordinate.parseInt($$0);
/*  70 */     if (!$$0.canRead() || $$0.peek() != ' ') {
/*  71 */       $$0.setCursor($$1);
/*  72 */       throw Vec3Argument.ERROR_NOT_COMPLETE.createWithContext($$0);
/*     */     } 
/*  74 */     $$0.skip();
/*  75 */     WorldCoordinate $$3 = WorldCoordinate.parseInt($$0);
/*  76 */     if (!$$0.canRead() || $$0.peek() != ' ') {
/*  77 */       $$0.setCursor($$1);
/*  78 */       throw Vec3Argument.ERROR_NOT_COMPLETE.createWithContext($$0);
/*     */     } 
/*  80 */     $$0.skip();
/*  81 */     WorldCoordinate $$4 = WorldCoordinate.parseInt($$0);
/*  82 */     return new WorldCoordinates($$2, $$3, $$4);
/*     */   }
/*     */   
/*     */   public static WorldCoordinates parseDouble(StringReader $$0, boolean $$1) throws CommandSyntaxException {
/*  86 */     int $$2 = $$0.getCursor();
/*  87 */     WorldCoordinate $$3 = WorldCoordinate.parseDouble($$0, $$1);
/*  88 */     if (!$$0.canRead() || $$0.peek() != ' ') {
/*  89 */       $$0.setCursor($$2);
/*  90 */       throw Vec3Argument.ERROR_NOT_COMPLETE.createWithContext($$0);
/*     */     } 
/*  92 */     $$0.skip();
/*  93 */     WorldCoordinate $$4 = WorldCoordinate.parseDouble($$0, false);
/*  94 */     if (!$$0.canRead() || $$0.peek() != ' ') {
/*  95 */       $$0.setCursor($$2);
/*  96 */       throw Vec3Argument.ERROR_NOT_COMPLETE.createWithContext($$0);
/*     */     } 
/*  98 */     $$0.skip();
/*  99 */     WorldCoordinate $$5 = WorldCoordinate.parseDouble($$0, $$1);
/* 100 */     return new WorldCoordinates($$3, $$4, $$5);
/*     */   }
/*     */   
/*     */   public static WorldCoordinates absolute(double $$0, double $$1, double $$2) {
/* 104 */     return new WorldCoordinates(new WorldCoordinate(false, $$0), new WorldCoordinate(false, $$1), new WorldCoordinate(false, $$2));
/*     */   }
/*     */   
/*     */   public static WorldCoordinates absolute(Vec2 $$0) {
/* 108 */     return new WorldCoordinates(new WorldCoordinate(false, $$0.x), new WorldCoordinate(false, $$0.y), new WorldCoordinate(true, 0.0D));
/*     */   }
/*     */   
/*     */   public static WorldCoordinates current() {
/* 112 */     return new WorldCoordinates(new WorldCoordinate(true, 0.0D), new WorldCoordinate(true, 0.0D), new WorldCoordinate(true, 0.0D));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 117 */     int $$0 = this.x.hashCode();
/* 118 */     $$0 = 31 * $$0 + this.y.hashCode();
/* 119 */     $$0 = 31 * $$0 + this.z.hashCode();
/* 120 */     return $$0;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\coordinates\WorldCoordinates.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */