/*    */ package net.minecraft.client.multiplayer.prediction;
/*    */ 
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.phys.Vec3;
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
/*    */ class ServerVerifiedState
/*    */ {
/*    */   final Vec3 playerPos;
/*    */   int sequence;
/*    */   BlockState blockState;
/*    */   
/*    */   ServerVerifiedState(int $$0, BlockState $$1, Vec3 $$2) {
/* 74 */     this.sequence = $$0;
/* 75 */     this.blockState = $$1;
/* 76 */     this.playerPos = $$2;
/*    */   }
/*    */   
/*    */   ServerVerifiedState setSequence(int $$0) {
/* 80 */     this.sequence = $$0;
/* 81 */     return this;
/*    */   }
/*    */   
/*    */   void setBlockState(BlockState $$0) {
/* 85 */     this.blockState = $$0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\multiplayer\prediction\BlockStatePredictionHandler$ServerVerifiedState.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */