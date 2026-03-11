/*    */ package net.minecraft.world.level.block.state.properties;
/*    */ 
/*    */ import net.minecraft.util.StringRepresentable;
/*    */ 
/*    */ public enum RedstoneSide implements StringRepresentable {
/*  6 */   UP("up"),
/*  7 */   SIDE("side"),
/*  8 */   NONE("none");
/*    */   
/*    */   private final String name;
/*    */ 
/*    */   
/*    */   RedstoneSide(String $$0) {
/* 14 */     this.name = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 19 */     return getSerializedName();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSerializedName() {
/* 24 */     return this.name;
/*    */   }
/*    */   
/*    */   public boolean isConnected() {
/* 28 */     return (this != NONE);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\state\properties\RedstoneSide.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */