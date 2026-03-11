/*    */ package net.minecraft.world.level.block.state.properties;
/*    */ 
/*    */ import net.minecraft.util.StringRepresentable;
/*    */ 
/*    */ public enum StairsShape implements StringRepresentable {
/*  6 */   STRAIGHT("straight"),
/*  7 */   INNER_LEFT("inner_left"),
/*  8 */   INNER_RIGHT("inner_right"),
/*  9 */   OUTER_LEFT("outer_left"),
/* 10 */   OUTER_RIGHT("outer_right");
/*    */   
/*    */   private final String name;
/*    */ 
/*    */   
/*    */   StairsShape(String $$0) {
/* 16 */     this.name = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 21 */     return this.name;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSerializedName() {
/* 26 */     return this.name;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\state\properties\StairsShape.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */