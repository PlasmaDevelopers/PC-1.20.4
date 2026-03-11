/*    */ package net.minecraft.client.multiplayer.prediction;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
/*    */ import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*    */ import net.minecraft.client.multiplayer.ClientLevel;
/*    */ import net.minecraft.client.player.LocalPlayer;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public class BlockStatePredictionHandler implements AutoCloseable {
/* 13 */   private final Long2ObjectOpenHashMap<ServerVerifiedState> serverVerifiedStates = new Long2ObjectOpenHashMap();
/*    */   private int currentSequenceNr;
/*    */   private boolean isPredicting;
/*    */   
/*    */   public void retainKnownServerState(BlockPos $$0, BlockState $$1, LocalPlayer $$2) {
/* 18 */     this.serverVerifiedStates.compute($$0.asLong(), ($$2, $$3) -> ($$3 != null) ? $$3.setSequence(this.currentSequenceNr) : new ServerVerifiedState(this.currentSequenceNr, $$0, $$1.position()));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean updateKnownServerState(BlockPos $$0, BlockState $$1) {
/* 27 */     ServerVerifiedState $$2 = (ServerVerifiedState)this.serverVerifiedStates.get($$0.asLong());
/* 28 */     if ($$2 == null) {
/* 29 */       return false;
/*    */     }
/* 31 */     $$2.setBlockState($$1);
/* 32 */     return true;
/*    */   }
/*    */   
/*    */   public void endPredictionsUpTo(int $$0, ClientLevel $$1) {
/* 36 */     ObjectIterator<Long2ObjectMap.Entry<ServerVerifiedState>> $$2 = this.serverVerifiedStates.long2ObjectEntrySet().iterator();
/* 37 */     while ($$2.hasNext()) {
/* 38 */       Long2ObjectMap.Entry<ServerVerifiedState> $$3 = (Long2ObjectMap.Entry<ServerVerifiedState>)$$2.next();
/* 39 */       ServerVerifiedState $$4 = (ServerVerifiedState)$$3.getValue();
/* 40 */       if ($$4.sequence <= $$0) {
/* 41 */         BlockPos $$5 = BlockPos.of($$3.getLongKey());
/* 42 */         $$2.remove();
/*    */         
/* 44 */         $$1.syncBlockState($$5, $$4.blockState, $$4.playerPos);
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public BlockStatePredictionHandler startPredicting() {
/* 50 */     this.currentSequenceNr++;
/* 51 */     this.isPredicting = true;
/* 52 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public void close() {
/* 57 */     this.isPredicting = false;
/*    */   }
/*    */   
/*    */   public int currentSequence() {
/* 61 */     return this.currentSequenceNr;
/*    */   }
/*    */   
/*    */   public boolean isPredicting() {
/* 65 */     return this.isPredicting;
/*    */   }
/*    */   
/*    */   private static class ServerVerifiedState {
/*    */     final Vec3 playerPos;
/*    */     int sequence;
/*    */     BlockState blockState;
/*    */     
/*    */     ServerVerifiedState(int $$0, BlockState $$1, Vec3 $$2) {
/* 74 */       this.sequence = $$0;
/* 75 */       this.blockState = $$1;
/* 76 */       this.playerPos = $$2;
/*    */     }
/*    */     
/*    */     ServerVerifiedState setSequence(int $$0) {
/* 80 */       this.sequence = $$0;
/* 81 */       return this;
/*    */     }
/*    */     
/*    */     void setBlockState(BlockState $$0) {
/* 85 */       this.blockState = $$0;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\multiplayer\prediction\BlockStatePredictionHandler.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */