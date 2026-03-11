/*    */ package net.minecraft.server.network;
/*    */ import net.minecraft.SharedConstants;
/*    */ import net.minecraft.network.Connection;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.chat.MutableComponent;
/*    */ import net.minecraft.network.protocol.handshake.ClientIntent;
/*    */ import net.minecraft.network.protocol.handshake.ClientIntentionPacket;
/*    */ import net.minecraft.network.protocol.login.ClientboundLoginDisconnectPacket;
/*    */ import net.minecraft.network.protocol.status.ServerStatus;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ 
/*    */ public class ServerHandshakePacketListenerImpl implements ServerHandshakePacketListener {
/* 14 */   private static final Component IGNORE_STATUS_REASON = (Component)Component.translatable("disconnect.ignoring_status_request");
/*    */   
/*    */   private final MinecraftServer server;
/*    */   private final Connection connection;
/*    */   
/*    */   public ServerHandshakePacketListenerImpl(MinecraftServer $$0, Connection $$1) {
/* 20 */     this.server = $$0;
/* 21 */     this.connection = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public void handleIntention(ClientIntentionPacket $$0) {
/*    */     ServerStatus $$3;
/* 27 */     switch ($$0.intention()) {
/*    */       case LOGIN:
/* 29 */         this.connection.setClientboundProtocolAfterHandshake(ClientIntent.LOGIN);
/* 30 */         if ($$0.protocolVersion() != SharedConstants.getCurrentVersion().getProtocolVersion()) {
/*    */           MutableComponent mutableComponent;
/*    */ 
/*    */ 
/*    */           
/* 35 */           if ($$0.protocolVersion() < 754) {
/* 36 */             mutableComponent = Component.translatable("multiplayer.disconnect.outdated_client", new Object[] { SharedConstants.getCurrentVersion().getName() });
/*    */           } else {
/* 38 */             mutableComponent = Component.translatable("multiplayer.disconnect.incompatible", new Object[] { SharedConstants.getCurrentVersion().getName() });
/*    */           } 
/* 40 */           this.connection.send((Packet)new ClientboundLoginDisconnectPacket((Component)mutableComponent));
/* 41 */           this.connection.disconnect((Component)mutableComponent);
/*    */         } else {
/* 43 */           this.connection.setListener((PacketListener)new ServerLoginPacketListenerImpl(this.server, this.connection));
/*    */         } 
/*    */         return;
/*    */       case STATUS:
/* 47 */         $$3 = this.server.getStatus();
/* 48 */         if (this.server.repliesToStatus() && $$3 != null) {
/* 49 */           this.connection.setClientboundProtocolAfterHandshake(ClientIntent.STATUS);
/* 50 */           this.connection.setListener((PacketListener)new ServerStatusPacketListenerImpl($$3, this.connection));
/*    */         } else {
/* 52 */           this.connection.disconnect(IGNORE_STATUS_REASON);
/*    */         }  return;
/*    */     } 
/* 55 */     throw new UnsupportedOperationException("Invalid intention " + $$0.intention());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onDisconnect(Component $$0) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isAcceptingMessages() {
/* 66 */     return this.connection.isConnected();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\network\ServerHandshakePacketListenerImpl.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */