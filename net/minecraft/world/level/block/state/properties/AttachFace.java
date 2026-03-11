/*    */ package net.minecraft.world.level.block.state.properties;
/*    */ 
/*    */ import net.minecraft.util.StringRepresentable;
/*    */ 
/*    */ public enum AttachFace implements StringRepresentable {
/*  6 */   FLOOR("floor"),
/*  7 */   WALL("wall"),
/*  8 */   CEILING("ceiling");
/*    */   
/*    */   private final String name;
/*    */ 
/*    */   
/*    */   AttachFace(String $$0) {
/* 14 */     this.name = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSerializedName() {
/* 19 */     return this.name;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\state\properties\AttachFace.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */