/*    */ package net.minecraft.world.level.gameevent;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Position;
/*    */ import net.minecraft.core.SectionPos;
/*    */ import net.minecraft.network.protocol.game.DebugPackets;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.chunk.LevelChunk;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public class GameEventDispatcher {
/*    */   public GameEventDispatcher(ServerLevel $$0) {
/* 17 */     this.level = $$0;
/*    */   }
/*    */   private final ServerLevel level;
/*    */   public void post(GameEvent $$0, Vec3 $$1, GameEvent.Context $$2) {
/* 21 */     int $$3 = $$0.getNotificationRadius();
/* 22 */     BlockPos $$4 = BlockPos.containing((Position)$$1);
/* 23 */     int $$5 = SectionPos.blockToSectionCoord($$4.getX() - $$3);
/* 24 */     int $$6 = SectionPos.blockToSectionCoord($$4.getY() - $$3);
/* 25 */     int $$7 = SectionPos.blockToSectionCoord($$4.getZ() - $$3);
/* 26 */     int $$8 = SectionPos.blockToSectionCoord($$4.getX() + $$3);
/* 27 */     int $$9 = SectionPos.blockToSectionCoord($$4.getY() + $$3);
/* 28 */     int $$10 = SectionPos.blockToSectionCoord($$4.getZ() + $$3);
/*    */     
/* 30 */     List<GameEvent.ListenerInfo> $$11 = new ArrayList<>();
/*    */     
/* 32 */     GameEventListenerRegistry.ListenerVisitor $$12 = ($$4, $$5) -> {
/*    */         if ($$4.getDeliveryMode() == GameEventListener.DeliveryMode.BY_DISTANCE) {
/*    */           $$0.add(new GameEvent.ListenerInfo($$1, $$2, $$3, $$4, $$5));
/*    */         } else {
/*    */           $$4.handleGameEvent(this.level, $$1, $$3, $$2);
/*    */         } 
/*    */       };
/*    */     
/* 40 */     boolean $$13 = false;
/* 41 */     for (int $$14 = $$5; $$14 <= $$8; $$14++) {
/* 42 */       for (int $$15 = $$7; $$15 <= $$10; $$15++) {
/* 43 */         LevelChunk levelChunk = this.level.getChunkSource().getChunkNow($$14, $$15);
/*    */         
/* 45 */         if (levelChunk != null) {
/* 46 */           for (int $$17 = $$6; $$17 <= $$9; $$17++) {
/* 47 */             $$13 |= levelChunk.getListenerRegistry($$17).visitInRangeListeners($$0, $$1, $$2, $$12);
/*    */           }
/*    */         }
/*    */       } 
/*    */     } 
/* 52 */     if (!$$11.isEmpty()) {
/* 53 */       handleGameEventMessagesInQueue($$11);
/*    */     }
/* 55 */     if ($$13) {
/* 56 */       DebugPackets.sendGameEventInfo((Level)this.level, $$0, $$1);
/*    */     }
/*    */   }
/*    */   
/*    */   private void handleGameEventMessagesInQueue(List<GameEvent.ListenerInfo> $$0) {
/* 61 */     Collections.sort($$0);
/* 62 */     for (GameEvent.ListenerInfo $$1 : $$0) {
/* 63 */       GameEventListener $$2 = $$1.recipient();
/* 64 */       $$2.handleGameEvent(this.level, $$1.gameEvent(), $$1.context(), $$1.source());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\gameevent\GameEventDispatcher.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */