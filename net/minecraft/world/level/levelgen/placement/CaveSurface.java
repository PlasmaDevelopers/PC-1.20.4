/*    */ package net.minecraft.world.level.levelgen.placement;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.util.StringRepresentable;
/*    */ 
/*    */ public enum CaveSurface implements StringRepresentable {
/*  8 */   CEILING(Direction.UP, 1, "ceiling"),
/*  9 */   FLOOR(Direction.DOWN, -1, "floor"); public static final Codec<CaveSurface> CODEC;
/*    */   static {
/* 11 */     CODEC = (Codec<CaveSurface>)StringRepresentable.fromEnum(CaveSurface::values);
/*    */   }
/*    */   private final Direction direction;
/*    */   private final int y;
/*    */   private final String id;
/*    */   
/*    */   CaveSurface(Direction $$0, int $$1, String $$2) {
/* 18 */     this.direction = $$0;
/* 19 */     this.y = $$1;
/* 20 */     this.id = $$2;
/*    */   }
/*    */   
/*    */   public Direction getDirection() {
/* 24 */     return this.direction;
/*    */   }
/*    */   
/*    */   public int getY() {
/* 28 */     return this.y;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSerializedName() {
/* 33 */     return this.id;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\placement\CaveSurface.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */