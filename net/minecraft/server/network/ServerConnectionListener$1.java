/*     */ package net.minecraft.server.network;
/*     */ 
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelInitializer;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.channel.ChannelPipeline;
/*     */ import io.netty.handler.timeout.ReadTimeoutHandler;
/*     */ import net.minecraft.network.Connection;
/*     */ import net.minecraft.network.PacketListener;
/*     */ import net.minecraft.network.RateKickingConnection;
/*     */ import net.minecraft.network.protocol.PacketFlow;
/*     */ import net.minecraft.server.ServerInfo;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class null
/*     */   extends ChannelInitializer<Channel>
/*     */ {
/*     */   protected void initChannel(Channel $$0) {
/*  89 */     Connection.setInitialProtocolAttributes($$0);
/*     */     
/*     */     try {
/*  92 */       $$0.config().setOption(ChannelOption.TCP_NODELAY, Boolean.valueOf(true));
/*  93 */     } catch (ChannelException channelException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  98 */     ChannelPipeline $$1 = $$0.pipeline().addLast("timeout", (ChannelHandler)new ReadTimeoutHandler(30)).addLast("legacy_query", (ChannelHandler)new LegacyQueryHandler((ServerInfo)ServerConnectionListener.this.getServer()));
/*     */     
/* 100 */     Connection.configureSerialization($$1, PacketFlow.SERVERBOUND, null);
/*     */     
/* 102 */     int $$2 = ServerConnectionListener.this.server.getRateLimitPacketsPerSecond();
/* 103 */     Connection $$3 = ($$2 > 0) ? (Connection)new RateKickingConnection($$2) : new Connection(PacketFlow.SERVERBOUND);
/* 104 */     ServerConnectionListener.this.connections.add($$3);
/* 105 */     $$3.configurePacketHandler($$1);
/* 106 */     $$3.setListenerForServerboundHandshake((PacketListener)new ServerHandshakePacketListenerImpl(ServerConnectionListener.this.server, $$3));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\network\ServerConnectionListener$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */