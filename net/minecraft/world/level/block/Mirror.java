/*    */ package net.minecraft.world.level.block;
/*    */ 
/*    */ import com.mojang.math.OctahedralGroup;
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.util.StringRepresentable;
/*    */ 
/*    */ public enum Mirror
/*    */   implements StringRepresentable
/*    */ {
/* 12 */   NONE("none", OctahedralGroup.IDENTITY),
/* 13 */   LEFT_RIGHT("left_right", OctahedralGroup.INVERT_Z),
/* 14 */   FRONT_BACK("front_back", OctahedralGroup.INVERT_X); public static final Codec<Mirror> CODEC;
/*    */   
/*    */   static {
/* 17 */     CODEC = (Codec<Mirror>)StringRepresentable.fromEnum(Mirror::values);
/*    */   }
/*    */   private final String id;
/*    */   private final Component symbol;
/*    */   private final OctahedralGroup rotation;
/*    */   
/*    */   Mirror(String $$0, OctahedralGroup $$1) {
/* 24 */     this.id = $$0;
/* 25 */     this.symbol = (Component)Component.translatable("mirror." + $$0);
/* 26 */     this.rotation = $$1;
/*    */   }
/*    */   
/*    */   public int mirror(int $$0, int $$1) {
/* 30 */     int $$2 = $$1 / 2;
/* 31 */     int $$3 = ($$0 > $$2) ? ($$0 - $$1) : $$0;
/* 32 */     switch (this) {
/*    */       case FRONT_BACK:
/* 34 */         return ($$1 - $$3) % $$1;
/*    */       case LEFT_RIGHT:
/* 36 */         return ($$2 - $$3 + $$1) % $$1;
/*    */     } 
/* 38 */     return $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public Rotation getRotation(Direction $$0) {
/* 43 */     Direction.Axis $$1 = $$0.getAxis();
/* 44 */     return ((this == LEFT_RIGHT && $$1 == Direction.Axis.Z) || (this == FRONT_BACK && $$1 == Direction.Axis.X)) ? Rotation.CLOCKWISE_180 : Rotation.NONE;
/*    */   }
/*    */   
/*    */   public Direction mirror(Direction $$0) {
/* 48 */     if (this == FRONT_BACK && $$0.getAxis() == Direction.Axis.X) {
/* 49 */       return $$0.getOpposite();
/*    */     }
/* 51 */     if (this == LEFT_RIGHT && $$0.getAxis() == Direction.Axis.Z) {
/* 52 */       return $$0.getOpposite();
/*    */     }
/* 54 */     return $$0;
/*    */   }
/*    */   
/*    */   public OctahedralGroup rotation() {
/* 58 */     return this.rotation;
/*    */   }
/*    */   
/*    */   public Component symbol() {
/* 62 */     return this.symbol;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSerializedName() {
/* 67 */     return this.id;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\Mirror.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */