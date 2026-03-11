/*    */ package net.minecraft.world.level.block.state.properties;
/*    */ 
/*    */ import net.minecraft.util.StringRepresentable;
/*    */ 
/*    */ public enum ChestType implements StringRepresentable {
/*  6 */   SINGLE("single"),
/*  7 */   LEFT("left"),
/*  8 */   RIGHT("right");
/*    */   
/*    */   private final String name;
/*    */ 
/*    */   
/*    */   ChestType(String $$0) {
/* 14 */     this.name = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSerializedName() {
/* 19 */     return this.name;
/*    */   }
/*    */   
/*    */   public ChestType getOpposite() {
/* 23 */     switch (this) { default: throw new IncompatibleClassChangeError();case SINGLE: case LEFT: case RIGHT: break; }  return 
/*    */ 
/*    */       
/* 26 */       LEFT;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\state\properties\ChestType.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */