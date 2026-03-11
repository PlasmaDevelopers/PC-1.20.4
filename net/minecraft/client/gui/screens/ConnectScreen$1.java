/*     */ package net.minecraft.client.gui.screens;
/*     */ 
/*     */ import java.net.InetSocketAddress;
/*     */ import java.util.Optional;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.multiplayer.ClientHandshakePacketListenerImpl;
/*     */ import net.minecraft.client.multiplayer.ServerData;
/*     */ import net.minecraft.client.multiplayer.resolver.ResolvedServerAddress;
/*     */ import net.minecraft.client.multiplayer.resolver.ServerAddress;
/*     */ import net.minecraft.client.multiplayer.resolver.ServerNameResolver;
/*     */ import net.minecraft.client.resources.server.ServerPackManager;
/*     */ import net.minecraft.network.Connection;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.network.protocol.PacketFlow;
/*     */ import net.minecraft.network.protocol.login.ClientLoginPacketListener;
/*     */ import net.minecraft.network.protocol.login.ServerboundHelloPacket;
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
/*     */   extends Thread
/*     */ {
/*     */   null(String $$1) {
/*  71 */     super($$1);
/*     */   }
/*     */   public void run() {
/*  74 */     InetSocketAddress $$0 = null; try {
/*     */       Connection $$2;
/*  76 */       if (ConnectScreen.this.aborted) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/*  81 */       Optional<InetSocketAddress> $$1 = ServerNameResolver.DEFAULT.resolveAddress(hostAndPort).map(ResolvedServerAddress::asInetSocketAddress);
/*  82 */       if (ConnectScreen.this.aborted) {
/*     */         return;
/*     */       }
/*  85 */       if ($$1.isEmpty()) {
/*  86 */         minecraft.execute(() -> $$0.setScreen(new DisconnectedScreen(ConnectScreen.this.parent, ConnectScreen.this.connectFailedTitle, ConnectScreen.UNKNOWN_HOST_MESSAGE)));
/*     */         return;
/*     */       } 
/*  89 */       $$0 = $$1.get();
/*     */ 
/*     */       
/*  92 */       synchronized (ConnectScreen.this) {
/*  93 */         if (ConnectScreen.this.aborted) {
/*     */           return;
/*     */         }
/*  96 */         $$2 = new Connection(PacketFlow.CLIENTBOUND);
/*  97 */         $$2.setBandwidthLogger(minecraft.getDebugOverlay().getBandwidthLogger());
/*     */         
/*  99 */         ConnectScreen.this.channelFuture = Connection.connect($$0, minecraft.options.useNativeTransport(), $$2);
/*     */       } 
/* 101 */       ConnectScreen.this.channelFuture.syncUninterruptibly();
/*     */       
/* 103 */       synchronized (ConnectScreen.this) {
/* 104 */         if (ConnectScreen.this.aborted) {
/* 105 */           $$2.disconnect(ConnectScreen.ABORT_CONNECTION);
/*     */           return;
/*     */         } 
/* 108 */         ConnectScreen.this.connection = $$2;
/* 109 */         minecraft.getDownloadedPackSource().configureForServerControl($$2, (server != null) ? convertPackStatus(server.getResourcePackStatus()) : ServerPackManager.PackPromptStatus.PENDING);
/*     */       } 
/* 111 */       ConnectScreen.this.connection.initiateServerboundPlayConnection($$0
/* 112 */           .getHostName(), $$0
/* 113 */           .getPort(), (ClientLoginPacketListener)new ClientHandshakePacketListenerImpl(ConnectScreen.this.connection, minecraft, server, ConnectScreen.this.parent, false, null, ConnectScreen.this::updateStatus));
/*     */ 
/*     */       
/* 116 */       ConnectScreen.this.connection.send((Packet)new ServerboundHelloPacket(minecraft.getUser().getName(), minecraft.getUser().getProfileId()));
/* 117 */     } catch (Exception $$4) {
/* 118 */       Exception $$7; if (ConnectScreen.this.aborted) {
/*     */         return;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 125 */       Throwable throwable = $$4.getCause(); if (throwable instanceof Exception) { Exception $$5 = (Exception)throwable;
/* 126 */         Exception $$6 = $$5; }
/*     */       else
/* 128 */       { $$7 = $$4; }
/*     */ 
/*     */       
/* 131 */       ConnectScreen.LOGGER.error("Couldn't connect to server", $$4);
/* 132 */       String $$8 = ($$0 == null) ? $$7.getMessage() : $$7.getMessage().replaceAll($$0.getHostName() + ":" + $$0.getHostName(), "").replaceAll($$0.toString(), "");
/* 133 */       minecraft.execute(() -> $$0.setScreen(new DisconnectedScreen(ConnectScreen.this.parent, ConnectScreen.this.connectFailedTitle, (Component)Component.translatable("disconnect.genericReason", new Object[] { $$1 }))));
/*     */     } 
/*     */   }
/*     */   
/*     */   private static ServerPackManager.PackPromptStatus convertPackStatus(ServerData.ServerPackStatus $$0) {
/* 138 */     switch (ConnectScreen.null.$SwitchMap$net$minecraft$client$multiplayer$ServerData$ServerPackStatus[$$0.ordinal()]) { default: throw new IncompatibleClassChangeError();case 1: case 2: case 3: break; }  return 
/*     */ 
/*     */       
/* 141 */       ServerPackManager.PackPromptStatus.PENDING;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\ConnectScreen$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */