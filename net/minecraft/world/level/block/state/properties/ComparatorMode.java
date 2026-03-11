/*    */ package net.minecraft.world.level.block.state.properties;
/*    */ 
/*    */ import net.minecraft.util.StringRepresentable;
/*    */ 
/*    */ public enum ComparatorMode implements StringRepresentable {
/*  6 */   COMPARE("compare"),
/*  7 */   SUBTRACT("subtract");
/*    */   
/*    */   private final String name;
/*    */ 
/*    */   
/*    */   ComparatorMode(String $$0) {
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


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\state\properties\ComparatorMode.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */