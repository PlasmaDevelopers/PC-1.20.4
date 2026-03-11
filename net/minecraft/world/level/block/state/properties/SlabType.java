/*    */ package net.minecraft.world.level.block.state.properties;
/*    */ 
/*    */ import net.minecraft.util.StringRepresentable;
/*    */ 
/*    */ public enum SlabType implements StringRepresentable {
/*  6 */   TOP("top"),
/*  7 */   BOTTOM("bottom"),
/*  8 */   DOUBLE("double");
/*    */   
/*    */   private final String name;
/*    */ 
/*    */   
/*    */   SlabType(String $$0) {
/* 14 */     this.name = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 19 */     return this.name;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSerializedName() {
/* 24 */     return this.name;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\state\properties\SlabType.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */