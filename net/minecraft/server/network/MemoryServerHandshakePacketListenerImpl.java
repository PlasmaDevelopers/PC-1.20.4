/*    */ package net.minecraft.server.network;
/*    */ 
/*    */ import net.minecraft.network.Connection;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.protocol.handshake.ClientIntent;
/*    */ import net.minecraft.network.protocol.handshake.ClientIntentionPacket;
/*    */ import net.minecraft.network.protocol.handshake.ServerHandshakePacketListener;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ 
/*    */ public class MemoryServerHandshakePacketListenerImpl implements ServerHandshakePacketListener {
/*    */   private final MinecraftServer server;
/*    */   
/*    */   public MemoryServerHandshakePacketListenerImpl(MinecraftServer $$0, Connection $$1) {
/* 15 */     this.server = $$0;
/* 16 */     this.connection = $$1;
/*    */   }
/*    */   private final Connection connection;
/*    */   
/*    */   public void handleIntention(ClientIntentionPacket $$0) {
/* 21 */     if ($$0.intention() != ClientIntent.LOGIN) {
/* 22 */       throw new UnsupportedOperationException("Invalid intention " + $$0.intention());
/*    */     }
/* 24 */     this.connection.setClientboundProtocolAfterHandshake(ClientIntent.LOGIN);
/* 25 */     this.connection.setListener((PacketListener)new ServerLoginPacketListenerImpl(this.server, this.connection));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onDisconnect(Component $$0) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isAcceptingMessages() {
/* 35 */     return this.connection.isConnected();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\network\MemoryServerHandshakePacketListenerImpl.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */