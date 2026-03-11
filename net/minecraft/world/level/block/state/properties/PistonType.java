/*    */ package net.minecraft.world.level.block.state.properties;
/*    */ 
/*    */ import net.minecraft.util.StringRepresentable;
/*    */ 
/*    */ public enum PistonType implements StringRepresentable {
/*  6 */   DEFAULT("normal"),
/*  7 */   STICKY("sticky");
/*    */   
/*    */   private final String name;
/*    */   
/*    */   PistonType(String $$0) {
/* 12 */     this.name = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 17 */     return this.name;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSerializedName() {
/* 22 */     return this.name;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\state\properties\PistonType.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */