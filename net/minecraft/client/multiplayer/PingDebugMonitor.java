/*    */ package net.minecraft.client.multiplayer;
/*    */ 
/*    */ import net.minecraft.Util;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.network.protocol.status.ClientboundPongResponsePacket;
/*    */ import net.minecraft.network.protocol.status.ServerboundPingRequestPacket;
/*    */ import net.minecraft.util.SampleLogger;
/*    */ 
/*    */ public class PingDebugMonitor {
/*    */   private final ClientPacketListener connection;
/*    */   
/*    */   public PingDebugMonitor(ClientPacketListener $$0, SampleLogger $$1) {
/* 13 */     this.connection = $$0;
/* 14 */     this.delayTimer = $$1;
/*    */   }
/*    */   private final SampleLogger delayTimer;
/*    */   public void tick() {
/* 18 */     this.connection.send((Packet<?>)new ServerboundPingRequestPacket(Util.getMillis()));
/*    */   }
/*    */   
/*    */   public void onPongReceived(ClientboundPongResponsePacket $$0) {
/* 22 */     this.delayTimer.logSample(Util.getMillis() - $$0.getTime());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\multiplayer\PingDebugMonitor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */