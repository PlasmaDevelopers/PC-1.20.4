/*     */ package net.minecraft.world.level.block;
/*     */ 
/*     */ import com.mojang.math.OctahedralGroup;
/*     */ import com.mojang.serialization.Codec;
/*     */ import java.util.List;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.util.StringRepresentable;
/*     */ 
/*     */ public enum Rotation
/*     */   implements StringRepresentable
/*     */ {
/*     */   public static final Codec<Rotation> CODEC;
/*  15 */   NONE("none", OctahedralGroup.IDENTITY),
/*  16 */   CLOCKWISE_90("clockwise_90", OctahedralGroup.ROT_90_Y_NEG),
/*  17 */   CLOCKWISE_180("180", OctahedralGroup.ROT_180_FACE_XZ),
/*  18 */   COUNTERCLOCKWISE_90("counterclockwise_90", OctahedralGroup.ROT_90_Y_POS);
/*     */   
/*     */   static {
/*  21 */     CODEC = (Codec<Rotation>)StringRepresentable.fromEnum(Rotation::values);
/*     */   }
/*     */   
/*     */   private final String id;
/*     */   
/*     */   Rotation(String $$0, OctahedralGroup $$1) {
/*  27 */     this.id = $$0;
/*  28 */     this.rotation = $$1;
/*     */   }
/*     */   private final OctahedralGroup rotation;
/*     */   public Rotation getRotated(Rotation $$0) {
/*  32 */     switch ($$0) {
/*     */       case CLOCKWISE_180:
/*  34 */         switch (this) {
/*     */           case NONE:
/*  36 */             return CLOCKWISE_180;
/*     */           case CLOCKWISE_90:
/*  38 */             return COUNTERCLOCKWISE_90;
/*     */           case CLOCKWISE_180:
/*  40 */             return NONE;
/*     */           case COUNTERCLOCKWISE_90:
/*  42 */             return CLOCKWISE_90;
/*     */         } 
/*     */       case COUNTERCLOCKWISE_90:
/*  45 */         switch (this) {
/*     */           case NONE:
/*  47 */             return COUNTERCLOCKWISE_90;
/*     */           case CLOCKWISE_90:
/*  49 */             return NONE;
/*     */           case CLOCKWISE_180:
/*  51 */             return CLOCKWISE_90;
/*     */           case COUNTERCLOCKWISE_90:
/*  53 */             return CLOCKWISE_180;
/*     */         } 
/*     */       case CLOCKWISE_90:
/*  56 */         switch (this) {
/*     */           case NONE:
/*  58 */             return CLOCKWISE_90;
/*     */           case CLOCKWISE_90:
/*  60 */             return CLOCKWISE_180;
/*     */           case CLOCKWISE_180:
/*  62 */             return COUNTERCLOCKWISE_90;
/*     */           case COUNTERCLOCKWISE_90:
/*  64 */             return NONE;
/*     */         }  break;
/*     */     } 
/*  67 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public OctahedralGroup rotation() {
/*  72 */     return this.rotation;
/*     */   }
/*     */   
/*     */   public Direction rotate(Direction $$0) {
/*  76 */     if ($$0.getAxis() == Direction.Axis.Y) {
/*  77 */       return $$0;
/*     */     }
/*  79 */     switch (this) {
/*     */       case CLOCKWISE_180:
/*  81 */         return $$0.getOpposite();
/*     */       case COUNTERCLOCKWISE_90:
/*  83 */         return $$0.getCounterClockWise();
/*     */       case CLOCKWISE_90:
/*  85 */         return $$0.getClockWise();
/*     */     } 
/*  87 */     return $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int rotate(int $$0, int $$1) {
/*  92 */     switch (this) {
/*     */       case CLOCKWISE_180:
/*  94 */         return ($$0 + $$1 / 2) % $$1;
/*     */       case COUNTERCLOCKWISE_90:
/*  96 */         return ($$0 + $$1 * 3 / 4) % $$1;
/*     */       case CLOCKWISE_90:
/*  98 */         return ($$0 + $$1 / 4) % $$1;
/*     */     } 
/* 100 */     return $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Rotation getRandom(RandomSource $$0) {
/* 105 */     return (Rotation)Util.getRandom((Object[])values(), $$0);
/*     */   }
/*     */   
/*     */   public static List<Rotation> getShuffled(RandomSource $$0) {
/* 109 */     return Util.shuffledCopy((Object[])values(), $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSerializedName() {
/* 114 */     return this.id;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\Rotation.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */