/*    */ package net.minecraft.world.level.block.state.properties;
/*    */ 
/*    */ import net.minecraft.util.StringRepresentable;
/*    */ 
/*    */ public enum DripstoneThickness implements StringRepresentable {
/*  6 */   TIP_MERGE("tip_merge"),
/*  7 */   TIP("tip"),
/*  8 */   FRUSTUM("frustum"),
/*  9 */   MIDDLE("middle"),
/* 10 */   BASE("base");
/*    */   
/*    */   private final String name;
/*    */   
/*    */   DripstoneThickness(String $$0) {
/* 15 */     this.name = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 20 */     return this.name;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSerializedName() {
/* 25 */     return this.name;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\state\properties\DripstoneThickness.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */