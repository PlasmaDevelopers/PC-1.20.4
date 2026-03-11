/*    */ package net.minecraft.world.level.block.state.properties;
/*    */ 
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.util.StringRepresentable;
/*    */ 
/*    */ public enum DoubleBlockHalf implements StringRepresentable {
/*  7 */   UPPER(Direction.DOWN),
/*  8 */   LOWER(Direction.UP);
/*    */   
/*    */   private final Direction directionToOther;
/*    */ 
/*    */   
/*    */   DoubleBlockHalf(Direction $$0) {
/* 14 */     this.directionToOther = $$0;
/*    */   }
/*    */   
/*    */   public Direction getDirectionToOther() {
/* 18 */     return this.directionToOther;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 23 */     return getSerializedName();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSerializedName() {
/* 28 */     return (this == UPPER) ? "upper" : "lower";
/*    */   }
/*    */   
/*    */   public DoubleBlockHalf getOtherHalf() {
/* 32 */     return (this == UPPER) ? LOWER : UPPER;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\state\properties\DoubleBlockHalf.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */