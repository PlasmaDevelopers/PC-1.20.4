/*    */ package net.minecraft.world.level.block.state.properties;
/*    */ 
/*    */ import net.minecraft.util.StringRepresentable;
/*    */ 
/*    */ public enum Half implements StringRepresentable {
/*  6 */   TOP("top"),
/*  7 */   BOTTOM("bottom");
/*    */   
/*    */   private final String name;
/*    */ 
/*    */   
/*    */   Half(String $$0) {
/* 13 */     this.name = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 18 */     return this.name;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSerializedName() {
/* 23 */     return this.name;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\state\properties\Half.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */