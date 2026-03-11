/*    */ package net.minecraft.server.network;
/*    */ import net.minecraft.network.Connection;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.network.protocol.status.ClientboundPongResponsePacket;
/*    */ import net.minecraft.network.protocol.status.ClientboundStatusResponsePacket;
/*    */ import net.minecraft.network.protocol.status.ServerStatus;
/*    */ import net.minecraft.network.protocol.status.ServerStatusPacketListener;
/*    */ import net.minecraft.network.protocol.status.ServerboundPingRequestPacket;
/*    */ import net.minecraft.network.protocol.status.ServerboundStatusRequestPacket;
/*    */ 
/*    */ public class ServerStatusPacketListenerImpl implements ServerStatusPacketListener {
/* 13 */   private static final Component DISCONNECT_REASON = (Component)Component.translatable("multiplayer.status.request_handled");
/*    */   
/*    */   private final ServerStatus status;
/*    */   private final Connection connection;
/*    */   private boolean hasRequestedStatus;
/*    */   
/*    */   public ServerStatusPacketListenerImpl(ServerStatus $$0, Connection $$1) {
/* 20 */     this.status = $$0;
/* 21 */     this.connection = $$1;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onDisconnect(Component $$0) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isAcceptingMessages() {
/* 31 */     return this.connection.isConnected();
/*    */   }
/*    */ 
/*    */   
/*    */   public void handleStatusRequest(ServerboundStatusRequestPacket $$0) {
/* 36 */     if (this.hasRequestedStatus) {
/* 37 */       this.connection.disconnect(DISCONNECT_REASON);
/*    */       return;
/*    */     } 
/* 40 */     this.hasRequestedStatus = true;
/* 41 */     this.connection.send((Packet)new ClientboundStatusResponsePacket(this.status));
/*    */   }
/*    */ 
/*    */   
/*    */   public void handlePingRequest(ServerboundPingRequestPacket $$0) {
/* 46 */     this.connection.send((Packet)new ClientboundPongResponsePacket($$0.getTime()));
/* 47 */     this.connection.disconnect(DISCONNECT_REASON);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\network\ServerStatusPacketListenerImpl.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */