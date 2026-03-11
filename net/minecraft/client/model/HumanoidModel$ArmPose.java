/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum ArmPose
/*    */ {
/* 37 */   EMPTY(false),
/* 38 */   ITEM(false),
/* 39 */   BLOCK(false),
/* 40 */   BOW_AND_ARROW(true),
/* 41 */   THROW_SPEAR(false),
/* 42 */   CROSSBOW_CHARGE(true),
/* 43 */   CROSSBOW_HOLD(true),
/* 44 */   SPYGLASS(false),
/* 45 */   TOOT_HORN(false),
/* 46 */   BRUSH(false);
/*    */   
/*    */   private final boolean twoHanded;
/*    */ 
/*    */   
/*    */   ArmPose(boolean $$0) {
/* 52 */     this.twoHanded = $$0;
/*    */   }
/*    */   
/*    */   public boolean isTwoHanded() {
/* 56 */     return this.twoHanded;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\HumanoidModel$ArmPose.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */