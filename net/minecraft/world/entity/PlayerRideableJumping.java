/*    */ package net.minecraft.world.entity;
/*    */ 
/*    */ public interface PlayerRideableJumping extends PlayerRideable {
/*    */   void onPlayerJump(int paramInt);
/*    */   
/*    */   boolean canJump();
/*    */   
/*    */   void handleStartJump(int paramInt);
/*    */   
/*    */   void handleStopJump();
/*    */   
/*    */   default int getJumpCooldown() {
/* 13 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\PlayerRideableJumping.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */