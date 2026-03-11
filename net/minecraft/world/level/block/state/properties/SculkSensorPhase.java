/*    */ package net.minecraft.world.level.block.state.properties;
/*    */ 
/*    */ import net.minecraft.util.StringRepresentable;
/*    */ 
/*    */ public enum SculkSensorPhase implements StringRepresentable {
/*  6 */   INACTIVE("inactive"),
/*  7 */   ACTIVE("active"),
/*  8 */   COOLDOWN("cooldown");
/*    */   
/*    */   private final String name;
/*    */   
/*    */   SculkSensorPhase(String $$0) {
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


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\state\properties\SculkSensorPhase.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */