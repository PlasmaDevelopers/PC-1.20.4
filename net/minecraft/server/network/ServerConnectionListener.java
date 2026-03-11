/*     */ package net.minecraft.server.network;
/*     */ import com.google.common.base.Suppliers;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.util.concurrent.ThreadFactoryBuilder;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import io.netty.bootstrap.ServerBootstrap;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelInboundHandlerAdapter;
/*     */ import io.netty.channel.ChannelInitializer;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.channel.ChannelPipeline;
/*     */ import io.netty.channel.EventLoopGroup;
/*     */ import io.netty.channel.epoll.Epoll;
/*     */ import io.netty.channel.epoll.EpollEventLoopGroup;
/*     */ import io.netty.channel.epoll.EpollServerSocketChannel;
/*     */ import io.netty.channel.local.LocalAddress;
/*     */ import io.netty.channel.local.LocalServerChannel;
/*     */ import io.netty.channel.nio.NioEventLoopGroup;
/*     */ import io.netty.channel.socket.nio.NioServerSocketChannel;
/*     */ import io.netty.handler.timeout.ReadTimeoutHandler;
/*     */ import io.netty.util.HashedWheelTimer;
/*     */ import io.netty.util.Timeout;
/*     */ import io.netty.util.Timer;
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.SocketAddress;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.CrashReport;
/*     */ import net.minecraft.ReportedException;
/*     */ import net.minecraft.network.Connection;
/*     */ import net.minecraft.network.PacketListener;
/*     */ import net.minecraft.network.PacketSendListener;
/*     */ import net.minecraft.network.RateKickingConnection;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.network.protocol.PacketFlow;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.server.ServerInfo;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class ServerConnectionListener {
/*  52 */   private static final Logger LOGGER = LogUtils.getLogger();
/*  53 */   public static final Supplier<NioEventLoopGroup> SERVER_EVENT_GROUP = (Supplier<NioEventLoopGroup>)Suppliers.memoize(() -> new NioEventLoopGroup(0, (new ThreadFactoryBuilder()).setNameFormat("Netty Server IO #%d").setDaemon(true).build()));
/*     */ 
/*     */   
/*  56 */   public static final Supplier<EpollEventLoopGroup> SERVER_EPOLL_EVENT_GROUP = (Supplier<EpollEventLoopGroup>)Suppliers.memoize(() -> new EpollEventLoopGroup(0, (new ThreadFactoryBuilder()).setNameFormat("Netty Epoll Server IO #%d").setDaemon(true).build()));
/*     */   
/*     */   final MinecraftServer server;
/*     */   
/*     */   public volatile boolean running;
/*     */   
/*  62 */   private final List<ChannelFuture> channels = Collections.synchronizedList(Lists.newArrayList());
/*  63 */   final List<Connection> connections = Collections.synchronizedList(Lists.newArrayList());
/*     */   
/*     */   public ServerConnectionListener(MinecraftServer $$0) {
/*  66 */     this.server = $$0;
/*  67 */     this.running = true;
/*     */   }
/*     */   
/*     */   public void startTcpServerListener(@Nullable InetAddress $$0, int $$1) throws IOException {
/*  71 */     synchronized (this.channels) {
/*     */       Class<NioServerSocketChannel> clazz;
/*     */       EventLoopGroup $$5;
/*  74 */       if (Epoll.isAvailable() && this.server.isEpollEnabled()) {
/*  75 */         Class<EpollServerSocketChannel> clazz1 = EpollServerSocketChannel.class;
/*  76 */         EventLoopGroup $$3 = (EventLoopGroup)SERVER_EPOLL_EVENT_GROUP.get();
/*  77 */         LOGGER.info("Using epoll channel type");
/*     */       } else {
/*  79 */         clazz = NioServerSocketChannel.class;
/*  80 */         $$5 = (EventLoopGroup)SERVER_EVENT_GROUP.get();
/*  81 */         LOGGER.info("Using default channel type");
/*     */       } 
/*     */       
/*  84 */       this.channels.add(((ServerBootstrap)((ServerBootstrap)(new ServerBootstrap())
/*  85 */           .channel(clazz))
/*  86 */           .childHandler((ChannelHandler)new ChannelInitializer<Channel>()
/*     */             {
/*     */               protected void initChannel(Channel $$0) {
/*  89 */                 Connection.setInitialProtocolAttributes($$0);
/*     */                 
/*     */                 try {
/*  92 */                   $$0.config().setOption(ChannelOption.TCP_NODELAY, Boolean.valueOf(true));
/*  93 */                 } catch (ChannelException channelException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/*  98 */                 ChannelPipeline $$1 = $$0.pipeline().addLast("timeout", (ChannelHandler)new ReadTimeoutHandler(30)).addLast("legacy_query", (ChannelHandler)new LegacyQueryHandler((ServerInfo)ServerConnectionListener.this.getServer()));
/*     */                 
/* 100 */                 Connection.configureSerialization($$1, PacketFlow.SERVERBOUND, null);
/*     */                 
/* 102 */                 int $$2 = ServerConnectionListener.this.server.getRateLimitPacketsPerSecond();
/* 103 */                 Connection $$3 = ($$2 > 0) ? (Connection)new RateKickingConnection($$2) : new Connection(PacketFlow.SERVERBOUND);
/* 104 */                 ServerConnectionListener.this.connections.add($$3);
/* 105 */                 $$3.configurePacketHandler($$1);
/* 106 */                 $$3.setListenerForServerboundHandshake((PacketListener)new ServerHandshakePacketListenerImpl(ServerConnectionListener.this.server, $$3));
/*     */               }
/* 109 */             }).group($$5)
/* 110 */           .localAddress($$0, $$1))
/* 111 */           .bind()
/* 112 */           .syncUninterruptibly());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public SocketAddress startMemoryChannel() {
/*     */     ChannelFuture $$0;
/* 119 */     synchronized (this.channels) {
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
/* 144 */       $$0 = ((ServerBootstrap)((ServerBootstrap)(new ServerBootstrap()).channel(LocalServerChannel.class)).childHandler((ChannelHandler)new ChannelInitializer<Channel>() { protected void initChannel(Channel $$0) { Connection.setInitialProtocolAttributes($$0); Connection $$1 = new Connection(PacketFlow.SERVERBOUND); $$1.setListenerForServerboundHandshake((PacketListener)new MemoryServerHandshakePacketListenerImpl(ServerConnectionListener.this.server, $$1)); ServerConnectionListener.this.connections.add($$1); ChannelPipeline $$2 = $$0.pipeline(); Connection.configureInMemoryPipeline($$2, PacketFlow.SERVERBOUND); $$1.configurePacketHandler($$2); } }).group((EventLoopGroup)SERVER_EVENT_GROUP.get()).localAddress((SocketAddress)LocalAddress.ANY)).bind().syncUninterruptibly();
/*     */       
/* 146 */       this.channels.add($$0);
/*     */     } 
/*     */     
/* 149 */     return $$0.channel().localAddress();
/*     */   }
/*     */   
/*     */   public void stop() {
/* 153 */     this.running = false;
/*     */     
/* 155 */     for (ChannelFuture $$0 : this.channels) {
/*     */       try {
/* 157 */         $$0.channel().close().sync();
/* 158 */       } catch (InterruptedException $$1) {
/* 159 */         LOGGER.error("Interrupted whilst closing channel");
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void tick() {
/* 165 */     synchronized (this.connections) {
/* 166 */       Iterator<Connection> $$0 = this.connections.iterator();
/*     */       
/* 168 */       while ($$0.hasNext()) {
/* 169 */         Connection $$1 = $$0.next();
/*     */         
/* 171 */         if ($$1.isConnecting()) {
/*     */           continue;
/*     */         }
/* 174 */         if ($$1.isConnected()) {
/*     */           try {
/* 176 */             $$1.tick();
/* 177 */           } catch (Exception $$2) {
/* 178 */             if ($$1.isMemoryConnection()) {
/* 179 */               throw new ReportedException(CrashReport.forThrowable($$2, "Ticking memory connection"));
/*     */             }
/* 181 */             LOGGER.warn("Failed to handle packet for {}", $$1.getLoggableAddress(this.server.logIPs()), $$2);
/* 182 */             MutableComponent mutableComponent = Component.literal("Internal server error");
/* 183 */             $$1.send((Packet)new ClientboundDisconnectPacket((Component)mutableComponent), PacketSendListener.thenRun(() -> $$0.disconnect($$1)));
/* 184 */             $$1.setReadOnly();
/*     */           } 
/*     */           continue;
/*     */         } 
/* 188 */         $$0.remove();
/* 189 */         $$1.handleDisconnection();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public MinecraftServer getServer() {
/* 196 */     return this.server;
/*     */   }
/*     */   
/*     */   private static class LatencySimulator extends ChannelInboundHandlerAdapter {
/* 200 */     private static final Timer TIMER = (Timer)new HashedWheelTimer();
/*     */     
/*     */     private final int delay;
/*     */     private final int jitter;
/* 204 */     private final List<DelayedMessage> queuedMessages = Lists.newArrayList();
/*     */     
/*     */     public LatencySimulator(int $$0, int $$1) {
/* 207 */       this.delay = $$0;
/* 208 */       this.jitter = $$1;
/*     */     }
/*     */ 
/*     */     
/*     */     public void channelRead(ChannelHandlerContext $$0, Object $$1) {
/* 213 */       delayDownstream($$0, $$1);
/*     */     }
/*     */     
/*     */     private void delayDownstream(ChannelHandlerContext $$0, Object $$1) {
/* 217 */       int $$2 = this.delay + (int)(Math.random() * this.jitter);
/* 218 */       this.queuedMessages.add(new DelayedMessage($$0, $$1));
/* 219 */       TIMER.newTimeout(this::onTimeout, $$2, TimeUnit.MILLISECONDS);
/*     */     }
/*     */     
/*     */     private void onTimeout(Timeout $$0) {
/* 223 */       DelayedMessage $$1 = this.queuedMessages.remove(0);
/* 224 */       $$1.ctx.fireChannelRead($$1.msg);
/*     */     }
/*     */     
/*     */     private static class DelayedMessage
/*     */     {
/*     */       public final ChannelHandlerContext ctx;
/*     */       public final Object msg;
/*     */       
/* 232 */       public DelayedMessage(ChannelHandlerContext $$0, Object $$1) { this.ctx = $$0;
/* 233 */         this.msg = $$1; } } } private static class DelayedMessage { public final ChannelHandlerContext ctx; public DelayedMessage(ChannelHandlerContext $$0, Object $$1) { this.ctx = $$0; this.msg = $$1; }
/*     */ 
/*     */     
/*     */     public final Object msg; }
/*     */   
/*     */   public List<Connection> getConnections() {
/* 239 */     return this.connections;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\network\ServerConnectionListener.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */