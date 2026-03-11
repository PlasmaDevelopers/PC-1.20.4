/*    */ package net.minecraft.world.level.block.state.properties;
/*    */ 
/*    */ import net.minecraft.util.StringRepresentable;
/*    */ 
/*    */ public enum Tilt implements StringRepresentable {
/*  6 */   NONE("none", true),
/*  7 */   UNSTABLE("unstable", false),
/*  8 */   PARTIAL("partial", true),
/*  9 */   FULL("full", true);
/*    */   
/*    */   private final String name;
/*    */   
/*    */   private final boolean causesVibration;
/*    */   
/*    */   Tilt(String $$0, boolean $$1) {
/* 16 */     this.name = $$0;
/* 17 */     this.causesVibration = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSerializedName() {
/* 22 */     return this.name;
/*    */   }
/*    */   
/*    */   public boolean causesVibration() {
/* 26 */     return this.causesVibration;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\state\properties\Tilt.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */