/*     */ package net.minecraft.network;
/*     */ 
/*     */ import com.google.common.base.Suppliers;
/*     */ import com.google.common.collect.Queues;
/*     */ import com.google.common.util.concurrent.ThreadFactoryBuilder;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import io.netty.bootstrap.Bootstrap;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelInitializer;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.channel.ChannelPipeline;
/*     */ import io.netty.channel.DefaultEventLoopGroup;
/*     */ import io.netty.channel.EventLoopGroup;
/*     */ import io.netty.channel.SimpleChannelInboundHandler;
/*     */ import io.netty.channel.epoll.Epoll;
/*     */ import io.netty.channel.epoll.EpollEventLoopGroup;
/*     */ import io.netty.channel.epoll.EpollSocketChannel;
/*     */ import io.netty.channel.local.LocalChannel;
/*     */ import io.netty.channel.nio.NioEventLoopGroup;
/*     */ import io.netty.channel.socket.nio.NioSocketChannel;
/*     */ import io.netty.handler.flow.FlowControlHandler;
/*     */ import io.netty.handler.timeout.ReadTimeoutHandler;
/*     */ import io.netty.util.AttributeKey;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.SocketAddress;
/*     */ import java.util.Objects;
/*     */ import java.util.Queue;
/*     */ import java.util.concurrent.RejectedExecutionException;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nullable;
/*     */ import javax.crypto.Cipher;
/*     */ import net.minecraft.SharedConstants;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.network.protocol.PacketFlow;
/*     */ import net.minecraft.network.protocol.common.ClientboundDisconnectPacket;
/*     */ import net.minecraft.network.protocol.handshake.ClientIntent;
/*     */ import net.minecraft.network.protocol.handshake.ClientIntentionPacket;
/*     */ import net.minecraft.network.protocol.login.ClientLoginPacketListener;
/*     */ import net.minecraft.network.protocol.login.ClientboundLoginDisconnectPacket;
/*     */ import net.minecraft.network.protocol.status.ClientStatusPacketListener;
/*     */ import net.minecraft.server.RunningOnDifferentThreadException;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.SampleLogger;
/*     */ import org.apache.commons.lang3.Validate;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.Marker;
/*     */ import org.slf4j.MarkerFactory;
/*     */ 
/*     */ public class Connection
/*     */   extends SimpleChannelInboundHandler<Packet<?>> {
/*     */   private static final float AVERAGE_PACKETS_SMOOTHING = 0.75F;
/*  63 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*  65 */   public static final Marker ROOT_MARKER = MarkerFactory.getMarker("NETWORK"); public static final Marker PACKET_MARKER; public static final Marker PACKET_RECEIVED_MARKER; public static final Marker PACKET_SENT_MARKER;
/*     */   static {
/*  67 */     PACKET_MARKER = (Marker)Util.make(MarkerFactory.getMarker("NETWORK_PACKETS"), $$0 -> $$0.add(ROOT_MARKER));
/*     */     
/*  69 */     PACKET_RECEIVED_MARKER = (Marker)Util.make(MarkerFactory.getMarker("PACKET_RECEIVED"), $$0 -> $$0.add(PACKET_MARKER));
/*  70 */     PACKET_SENT_MARKER = (Marker)Util.make(MarkerFactory.getMarker("PACKET_SENT"), $$0 -> $$0.add(PACKET_MARKER));
/*     */   }
/*  72 */   public static final AttributeKey<ConnectionProtocol.CodecData<?>> ATTRIBUTE_SERVERBOUND_PROTOCOL = AttributeKey.valueOf("serverbound_protocol");
/*  73 */   public static final AttributeKey<ConnectionProtocol.CodecData<?>> ATTRIBUTE_CLIENTBOUND_PROTOCOL = AttributeKey.valueOf("clientbound_protocol");
/*     */ 
/*     */   
/*  76 */   public static final Supplier<NioEventLoopGroup> NETWORK_WORKER_GROUP = (Supplier<NioEventLoopGroup>)Suppliers.memoize(() -> new NioEventLoopGroup(0, (new ThreadFactoryBuilder()).setNameFormat("Netty Client IO #%d").setDaemon(true).build()));
/*     */ 
/*     */   
/*  79 */   public static final Supplier<EpollEventLoopGroup> NETWORK_EPOLL_WORKER_GROUP = (Supplier<EpollEventLoopGroup>)Suppliers.memoize(() -> new EpollEventLoopGroup(0, (new ThreadFactoryBuilder()).setNameFormat("Netty Epoll Client IO #%d").setDaemon(true).build()));
/*     */ 
/*     */   
/*  82 */   public static final Supplier<DefaultEventLoopGroup> LOCAL_WORKER_GROUP = (Supplier<DefaultEventLoopGroup>)Suppliers.memoize(() -> new DefaultEventLoopGroup(0, (new ThreadFactoryBuilder()).setNameFormat("Netty Local Client IO #%d").setDaemon(true).build()));
/*     */ 
/*     */   
/*     */   private final PacketFlow receiving;
/*     */   
/*  87 */   private final Queue<Consumer<Connection>> pendingActions = Queues.newConcurrentLinkedQueue();
/*     */   private Channel channel;
/*     */   private SocketAddress address;
/*     */   @Nullable
/*     */   private volatile PacketListener disconnectListener;
/*     */   @Nullable
/*     */   private volatile PacketListener packetListener;
/*     */   @Nullable
/*     */   private Component disconnectedReason;
/*     */   private boolean encrypted;
/*     */   private boolean disconnectionHandled;
/*     */   private int receivedPackets;
/*     */   private int sentPackets;
/*     */   private float averageReceivedPackets;
/*     */   private float averageSentPackets;
/*     */   private int tickCount;
/*     */   private boolean handlingFault;
/*     */   @Nullable
/*     */   private volatile Component delayedDisconnect;
/*     */   @Nullable
/*     */   BandwidthDebugMonitor bandwidthDebugMonitor;
/*     */   
/*     */   public Connection(PacketFlow $$0) {
/* 110 */     this.receiving = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelActive(ChannelHandlerContext $$0) throws Exception {
/* 115 */     super.channelActive($$0);
/* 116 */     this.channel = $$0.channel();
/* 117 */     this.address = this.channel.remoteAddress();
/* 118 */     if (this.delayedDisconnect != null) {
/* 119 */       disconnect(this.delayedDisconnect);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void setInitialProtocolAttributes(Channel $$0) {
/* 124 */     $$0.attr(ATTRIBUTE_SERVERBOUND_PROTOCOL).set(ConnectionProtocol.HANDSHAKING.codec(PacketFlow.SERVERBOUND));
/* 125 */     $$0.attr(ATTRIBUTE_CLIENTBOUND_PROTOCOL).set(ConnectionProtocol.HANDSHAKING.codec(PacketFlow.CLIENTBOUND));
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelInactive(ChannelHandlerContext $$0) {
/* 130 */     disconnect((Component)Component.translatable("disconnect.endOfStream"));
/*     */   }
/*     */ 
/*     */   
/*     */   public void exceptionCaught(ChannelHandlerContext $$0, Throwable $$1) {
/* 135 */     if ($$1 instanceof SkipPacketException) {
/* 136 */       LOGGER.debug("Skipping packet due to errors", $$1.getCause());
/*     */       
/*     */       return;
/*     */     } 
/* 140 */     boolean $$2 = !this.handlingFault;
/* 141 */     this.handlingFault = true;
/*     */     
/* 143 */     if (!this.channel.isOpen()) {
/*     */       return;
/*     */     }
/*     */     
/* 147 */     if ($$1 instanceof io.netty.handler.timeout.TimeoutException) {
/* 148 */       LOGGER.debug("Timeout", $$1);
/* 149 */       disconnect((Component)Component.translatable("disconnect.timeout"));
/*     */     } else {
/* 151 */       MutableComponent mutableComponent = Component.translatable("disconnect.genericReason", new Object[] { "Internal Exception: " + $$1 });
/*     */       
/* 153 */       if ($$2) {
/* 154 */         LOGGER.debug("Failed to sent packet", $$1);
/* 155 */         if (getSending() == PacketFlow.CLIENTBOUND) {
/* 156 */           ConnectionProtocol $$4 = ((ConnectionProtocol.CodecData)this.channel.attr(ATTRIBUTE_CLIENTBOUND_PROTOCOL).get()).protocol();
/* 157 */           Packet<?> $$5 = ($$4 == ConnectionProtocol.LOGIN) ? (Packet<?>)new ClientboundLoginDisconnectPacket((Component)mutableComponent) : (Packet<?>)new ClientboundDisconnectPacket((Component)mutableComponent);
/* 158 */           send($$5, PacketSendListener.thenRun(() -> disconnect($$0)));
/*     */         } else {
/* 160 */           disconnect((Component)mutableComponent);
/*     */         } 
/* 162 */         setReadOnly();
/*     */       } else {
/* 164 */         LOGGER.debug("Double fault", $$1);
/* 165 */         disconnect((Component)mutableComponent);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void channelRead0(ChannelHandlerContext $$0, Packet<?> $$1) {
/* 172 */     if (!this.channel.isOpen()) {
/*     */       return;
/*     */     }
/* 175 */     PacketListener $$2 = this.packetListener;
/* 176 */     if ($$2 == null) {
/* 177 */       throw new IllegalStateException("Received a packet before the packet listener was initialized");
/*     */     }
/* 179 */     if ($$2.shouldHandleMessage($$1)) {
/*     */       
/* 181 */       try { genericsFtw($$1, $$2); }
/* 182 */       catch (RunningOnDifferentThreadException runningOnDifferentThreadException) {  }
/* 183 */       catch (RejectedExecutionException $$3)
/* 184 */       { disconnect((Component)Component.translatable("multiplayer.disconnect.server_shutdown")); }
/* 185 */       catch (ClassCastException $$4)
/*     */       
/* 187 */       { LOGGER.error("Received {} that couldn't be processed", $$1.getClass(), $$4);
/* 188 */         disconnect((Component)Component.translatable("multiplayer.disconnect.invalid_packet")); }
/*     */       
/* 190 */       this.receivedPackets++;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static <T extends PacketListener> void genericsFtw(Packet<T> $$0, PacketListener $$1) {
/* 196 */     $$0.handle($$1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void suspendInboundAfterProtocolChange() {
/* 204 */     this.channel.config().setAutoRead(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resumeInboundAfterProtocolChange() {
/* 211 */     this.channel.config().setAutoRead(true);
/*     */   }
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
/*     */   public void setListener(PacketListener $$0) {
/* 229 */     Validate.notNull($$0, "packetListener", new Object[0]);
/* 230 */     PacketFlow $$1 = $$0.flow();
/* 231 */     if ($$1 != this.receiving) {
/* 232 */       throw new IllegalStateException("Trying to set listener for wrong side: connection is " + this.receiving + ", but listener is " + $$1);
/*     */     }
/* 234 */     ConnectionProtocol $$2 = $$0.protocol();
/* 235 */     ConnectionProtocol $$3 = ((ConnectionProtocol.CodecData)this.channel.attr(getProtocolKey($$1)).get()).protocol();
/* 236 */     if ($$3 != $$2) {
/* 237 */       throw new IllegalStateException("Trying to set listener for protocol " + $$2.id() + ", but current " + $$1 + " protocol is " + $$3.id());
/*     */     }
/*     */     
/* 240 */     this.packetListener = $$0;
/* 241 */     this.disconnectListener = null;
/*     */   }
/*     */   
/*     */   public void setListenerForServerboundHandshake(PacketListener $$0) {
/* 245 */     if (this.packetListener != null) {
/* 246 */       throw new IllegalStateException("Listener already set");
/*     */     }
/* 248 */     if (this.receiving != PacketFlow.SERVERBOUND || $$0
/* 249 */       .flow() != PacketFlow.SERVERBOUND || $$0
/* 250 */       .protocol() != ConnectionProtocol.HANDSHAKING)
/*     */     {
/* 252 */       throw new IllegalStateException("Invalid initial listener");
/*     */     }
/* 254 */     this.packetListener = $$0;
/*     */   }
/*     */   
/*     */   public void initiateServerboundStatusConnection(String $$0, int $$1, ClientStatusPacketListener $$2) {
/* 258 */     initiateServerboundConnection($$0, $$1, (PacketListener)$$2, ClientIntent.STATUS);
/*     */   }
/*     */   
/*     */   public void initiateServerboundPlayConnection(String $$0, int $$1, ClientLoginPacketListener $$2) {
/* 262 */     initiateServerboundConnection($$0, $$1, (PacketListener)$$2, ClientIntent.LOGIN);
/*     */   }
/*     */ 
/*     */   
/*     */   private void initiateServerboundConnection(String $$0, int $$1, PacketListener $$2, ClientIntent $$3) {
/* 267 */     this.disconnectListener = $$2;
/* 268 */     runOnceConnected($$4 -> {
/*     */           $$4.setClientboundProtocolAfterHandshake($$0);
/*     */           setListener($$1);
/*     */           $$4.sendPacket((Packet<?>)new ClientIntentionPacket(SharedConstants.getCurrentVersion().getProtocolVersion(), $$2, $$3, $$0), null, true);
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setClientboundProtocolAfterHandshake(ClientIntent $$0) {
/* 282 */     this.channel.attr(ATTRIBUTE_CLIENTBOUND_PROTOCOL).set($$0.protocol().codec(PacketFlow.CLIENTBOUND));
/*     */   }
/*     */   
/*     */   public void send(Packet<?> $$0) {
/* 286 */     send($$0, null);
/*     */   }
/*     */   
/*     */   public void send(Packet<?> $$0, @Nullable PacketSendListener $$1) {
/* 290 */     send($$0, $$1, true);
/*     */   }
/*     */   
/*     */   public void send(Packet<?> $$0, @Nullable PacketSendListener $$1, boolean $$2) {
/* 294 */     if (isConnected()) {
/* 295 */       flushQueue();
/* 296 */       sendPacket($$0, $$1, $$2);
/*     */     } else {
/* 298 */       this.pendingActions.add($$3 -> $$3.sendPacket($$0, $$1, $$2));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void runOnceConnected(Consumer<Connection> $$0) {
/* 303 */     if (isConnected()) {
/* 304 */       flushQueue();
/* 305 */       $$0.accept(this);
/*     */     } else {
/* 307 */       this.pendingActions.add($$0);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void sendPacket(Packet<?> $$0, @Nullable PacketSendListener $$1, boolean $$2) {
/* 312 */     this.sentPackets++;
/* 313 */     if (this.channel.eventLoop().inEventLoop()) {
/* 314 */       doSendPacket($$0, $$1, $$2);
/*     */     } else {
/* 316 */       this.channel.eventLoop().execute(() -> doSendPacket($$0, $$1, $$2));
/*     */     } 
/*     */   }
/*     */   
/*     */   private void doSendPacket(Packet<?> $$0, @Nullable PacketSendListener $$1, boolean $$2) {
/* 321 */     ChannelFuture $$3 = $$2 ? this.channel.writeAndFlush($$0) : this.channel.write($$0);
/* 322 */     if ($$1 != null) {
/* 323 */       $$3.addListener($$1 -> {
/*     */             if ($$1.isSuccess()) {
/*     */               $$0.onSuccess();
/*     */             } else {
/*     */               Packet<?> $$2 = $$0.onFailure();
/*     */               if ($$2 != null) {
/*     */                 ChannelFuture $$3 = this.channel.writeAndFlush($$2);
/*     */                 $$3.addListener((GenericFutureListener)ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
/*     */               } 
/*     */             } 
/*     */           });
/*     */     }
/* 335 */     $$3.addListener((GenericFutureListener)ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
/*     */   }
/*     */   
/*     */   public void flushChannel() {
/* 339 */     if (isConnected()) {
/* 340 */       flush();
/*     */     } else {
/* 342 */       this.pendingActions.add(Connection::flush);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void flush() {
/* 347 */     if (this.channel.eventLoop().inEventLoop()) {
/* 348 */       this.channel.flush();
/*     */     } else {
/* 350 */       this.channel.eventLoop().execute(() -> this.channel.flush());
/*     */     } 
/*     */   }
/*     */   
/*     */   private static AttributeKey<ConnectionProtocol.CodecData<?>> getProtocolKey(PacketFlow $$0) {
/* 355 */     switch ($$0) { default: throw new IncompatibleClassChangeError();case CLIENTBOUND: case SERVERBOUND: break; }  return 
/*     */       
/* 357 */       ATTRIBUTE_SERVERBOUND_PROTOCOL;
/*     */   }
/*     */ 
/*     */   
/*     */   private void flushQueue() {
/* 362 */     if (this.channel == null || !this.channel.isOpen()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 367 */     synchronized (this.pendingActions) {
/*     */       Consumer<Connection> $$0;
/* 369 */       while (($$0 = this.pendingActions.poll()) != null) {
/* 370 */         $$0.accept(this);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void tick() {
/* 376 */     flushQueue();
/*     */     
/* 378 */     PacketListener packetListener = this.packetListener; if (packetListener instanceof TickablePacketListener) { TickablePacketListener $$0 = (TickablePacketListener)packetListener;
/* 379 */       $$0.tick(); }
/*     */ 
/*     */     
/* 382 */     if (!isConnected() && !this.disconnectionHandled) {
/* 383 */       handleDisconnection();
/*     */     }
/*     */     
/* 386 */     if (this.channel != null) {
/* 387 */       this.channel.flush();
/*     */     }
/*     */     
/* 390 */     if (this.tickCount++ % 20 == 0) {
/* 391 */       tickSecond();
/*     */     }
/* 393 */     if (this.bandwidthDebugMonitor != null) {
/* 394 */       this.bandwidthDebugMonitor.tick();
/*     */     }
/*     */   }
/*     */   
/*     */   protected void tickSecond() {
/* 399 */     this.averageSentPackets = Mth.lerp(0.75F, this.sentPackets, this.averageSentPackets);
/* 400 */     this.averageReceivedPackets = Mth.lerp(0.75F, this.receivedPackets, this.averageReceivedPackets);
/* 401 */     this.sentPackets = 0;
/* 402 */     this.receivedPackets = 0;
/*     */   }
/*     */   
/*     */   public SocketAddress getRemoteAddress() {
/* 406 */     return this.address;
/*     */   }
/*     */   
/*     */   public String getLoggableAddress(boolean $$0) {
/* 410 */     if (this.address == null) {
/* 411 */       return "local";
/*     */     }
/* 413 */     if ($$0) {
/* 414 */       return this.address.toString();
/*     */     }
/* 416 */     return "IP hidden";
/*     */   }
/*     */   
/*     */   public void disconnect(Component $$0) {
/* 420 */     if (this.channel == null) {
/* 421 */       this.delayedDisconnect = $$0;
/*     */     }
/* 423 */     if (isConnected()) {
/* 424 */       this.channel.close().awaitUninterruptibly();
/*     */       
/* 426 */       this.disconnectedReason = $$0;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isMemoryConnection() {
/* 431 */     return (this.channel instanceof LocalChannel || this.channel instanceof io.netty.channel.local.LocalServerChannel);
/*     */   }
/*     */   
/*     */   public PacketFlow getReceiving() {
/* 435 */     return this.receiving;
/*     */   }
/*     */   
/*     */   public PacketFlow getSending() {
/* 439 */     return this.receiving.getOpposite();
/*     */   }
/*     */   
/*     */   public static Connection connectToServer(InetSocketAddress $$0, boolean $$1, @Nullable SampleLogger $$2) {
/* 443 */     Connection $$3 = new Connection(PacketFlow.CLIENTBOUND);
/*     */     
/* 445 */     if ($$2 != null) {
/* 446 */       $$3.setBandwidthLogger($$2);
/*     */     }
/* 448 */     ChannelFuture $$4 = connect($$0, $$1, $$3);
/* 449 */     $$4.syncUninterruptibly();
/*     */     
/* 451 */     return $$3;
/*     */   }
/*     */   
/*     */   public static ChannelFuture connect(InetSocketAddress $$0, boolean $$1, final Connection connection) {
/*     */     Class<NioSocketChannel> clazz;
/*     */     EventLoopGroup $$6;
/* 457 */     if (Epoll.isAvailable() && $$1) {
/* 458 */       Class<EpollSocketChannel> clazz1 = EpollSocketChannel.class;
/* 459 */       EventLoopGroup $$4 = (EventLoopGroup)NETWORK_EPOLL_WORKER_GROUP.get();
/*     */     } else {
/* 461 */       clazz = NioSocketChannel.class;
/* 462 */       $$6 = (EventLoopGroup)NETWORK_WORKER_GROUP.get();
/*     */     } 
/*     */     
/* 465 */     return ((Bootstrap)((Bootstrap)((Bootstrap)(new Bootstrap()).group($$6)).handler((ChannelHandler)new ChannelInitializer<Channel>()
/*     */         {
/*     */           protected void initChannel(Channel $$0) {
/* 468 */             Connection.setInitialProtocolAttributes($$0);
/*     */             
/*     */             try {
/* 471 */               $$0.config().setOption(ChannelOption.TCP_NODELAY, Boolean.valueOf(true));
/* 472 */             } catch (ChannelException channelException) {}
/*     */ 
/*     */ 
/*     */             
/* 476 */             ChannelPipeline $$1 = $$0.pipeline().addLast("timeout", (ChannelHandler)new ReadTimeoutHandler(30));
/*     */             
/* 478 */             Connection.configureSerialization($$1, PacketFlow.CLIENTBOUND, connection.bandwidthDebugMonitor);
/*     */             
/* 480 */             connection.configurePacketHandler($$1);
/*     */           }
/* 482 */         })).channel(clazz)).connect($$0.getAddress(), $$0.getPort());
/*     */   }
/*     */   
/*     */   public static void configureSerialization(ChannelPipeline $$0, PacketFlow $$1, @Nullable BandwidthDebugMonitor $$2) {
/* 486 */     PacketFlow $$3 = $$1.getOpposite();
/* 487 */     AttributeKey<ConnectionProtocol.CodecData<?>> $$4 = getProtocolKey($$1);
/* 488 */     AttributeKey<ConnectionProtocol.CodecData<?>> $$5 = getProtocolKey($$3);
/* 489 */     $$0
/*     */       
/* 491 */       .addLast("splitter", (ChannelHandler)new Varint21FrameDecoder($$2))
/*     */       
/* 493 */       .addLast("decoder", (ChannelHandler)new PacketDecoder($$4))
/*     */ 
/*     */       
/* 496 */       .addLast("prepender", (ChannelHandler)new Varint21LengthFieldPrepender())
/*     */       
/* 498 */       .addLast("encoder", (ChannelHandler)new PacketEncoder($$5))
/*     */       
/* 500 */       .addLast("unbundler", (ChannelHandler)new PacketBundleUnpacker((AttributeKey)$$5))
/* 501 */       .addLast("bundler", (ChannelHandler)new PacketBundlePacker((AttributeKey)$$4));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void configurePacketHandler(ChannelPipeline $$0) {
/* 507 */     $$0
/* 508 */       .addLast(new ChannelHandler[] { (ChannelHandler)new FlowControlHandler()
/* 509 */         }).addLast("packet_handler", (ChannelHandler)this);
/*     */   }
/*     */   
/*     */   private static void configureInMemoryPacketValidation(ChannelPipeline $$0, PacketFlow $$1) {
/* 513 */     PacketFlow $$2 = $$1.getOpposite();
/* 514 */     AttributeKey<ConnectionProtocol.CodecData<?>> $$3 = getProtocolKey($$1);
/* 515 */     AttributeKey<ConnectionProtocol.CodecData<?>> $$4 = getProtocolKey($$2);
/* 516 */     $$0
/* 517 */       .addLast("validator", (ChannelHandler)new PacketFlowValidator($$3, $$4));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void configureInMemoryPipeline(ChannelPipeline $$0, PacketFlow $$1) {
/* 524 */     configureInMemoryPacketValidation($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Connection connectToLocalServer(SocketAddress $$0) {
/* 529 */     final Connection connection = new Connection(PacketFlow.CLIENTBOUND);
/*     */     
/* 531 */     ((Bootstrap)((Bootstrap)((Bootstrap)(new Bootstrap()).group((EventLoopGroup)LOCAL_WORKER_GROUP.get())).handler((ChannelHandler)new ChannelInitializer<Channel>()
/*     */         {
/*     */           protected void initChannel(Channel $$0) {
/* 534 */             Connection.setInitialProtocolAttributes($$0);
/*     */             
/* 536 */             ChannelPipeline $$1 = $$0.pipeline();
/* 537 */             Connection.configureInMemoryPipeline($$1, PacketFlow.CLIENTBOUND);
/* 538 */             connection.configurePacketHandler($$1);
/*     */           }
/* 540 */         })).channel(LocalChannel.class)).connect($$0).syncUninterruptibly();
/*     */     
/* 542 */     return $$1;
/*     */   }
/*     */   
/*     */   public void setEncryptionKey(Cipher $$0, Cipher $$1) {
/* 546 */     this.encrypted = true;
/* 547 */     this.channel.pipeline().addBefore("splitter", "decrypt", (ChannelHandler)new CipherDecoder($$0));
/* 548 */     this.channel.pipeline().addBefore("prepender", "encrypt", (ChannelHandler)new CipherEncoder($$1));
/*     */   }
/*     */   
/*     */   public boolean isEncrypted() {
/* 552 */     return this.encrypted;
/*     */   }
/*     */   
/*     */   public boolean isConnected() {
/* 556 */     return (this.channel != null && this.channel.isOpen());
/*     */   }
/*     */   
/*     */   public boolean isConnecting() {
/* 560 */     return (this.channel == null);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public PacketListener getPacketListener() {
/* 565 */     return this.packetListener;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Component getDisconnectedReason() {
/* 570 */     return this.disconnectedReason;
/*     */   }
/*     */   
/*     */   public void setReadOnly() {
/* 574 */     if (this.channel != null) {
/* 575 */       this.channel.config().setAutoRead(false);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setupCompression(int $$0, boolean $$1) {
/* 580 */     if ($$0 >= 0) {
/* 581 */       if (this.channel.pipeline().get("decompress") instanceof CompressionDecoder) {
/* 582 */         ((CompressionDecoder)this.channel.pipeline().get("decompress")).setThreshold($$0, $$1);
/*     */       } else {
/* 584 */         this.channel.pipeline().addBefore("decoder", "decompress", (ChannelHandler)new CompressionDecoder($$0, $$1));
/*     */       } 
/*     */       
/* 587 */       if (this.channel.pipeline().get("compress") instanceof CompressionEncoder) {
/* 588 */         ((CompressionEncoder)this.channel.pipeline().get("compress")).setThreshold($$0);
/*     */       } else {
/* 590 */         this.channel.pipeline().addBefore("encoder", "compress", (ChannelHandler)new CompressionEncoder($$0));
/*     */       } 
/*     */     } else {
/* 593 */       if (this.channel.pipeline().get("decompress") instanceof CompressionDecoder) {
/* 594 */         this.channel.pipeline().remove("decompress");
/*     */       }
/*     */       
/* 597 */       if (this.channel.pipeline().get("compress") instanceof CompressionEncoder) {
/* 598 */         this.channel.pipeline().remove("compress");
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void handleDisconnection() {
/* 604 */     if (this.channel == null || this.channel.isOpen()) {
/*     */       return;
/*     */     }
/*     */     
/* 608 */     if (this.disconnectionHandled) {
/* 609 */       LOGGER.warn("handleDisconnection() called twice");
/*     */       
/*     */       return;
/*     */     } 
/* 613 */     this.disconnectionHandled = true;
/* 614 */     PacketListener $$0 = getPacketListener();
/* 615 */     PacketListener $$1 = ($$0 != null) ? $$0 : this.disconnectListener;
/* 616 */     if ($$1 != null) {
/* 617 */       Component $$2 = Objects.<Component>requireNonNullElseGet(getDisconnectedReason(), () -> Component.translatable("multiplayer.disconnect.generic"));
/* 618 */       $$1.onDisconnect($$2);
/*     */     } 
/*     */   }
/*     */   
/*     */   public float getAverageReceivedPackets() {
/* 623 */     return this.averageReceivedPackets;
/*     */   }
/*     */   
/*     */   public float getAverageSentPackets() {
/* 627 */     return this.averageSentPackets;
/*     */   }
/*     */   
/*     */   public void setBandwidthLogger(SampleLogger $$0) {
/* 631 */     this.bandwidthDebugMonitor = new BandwidthDebugMonitor($$0);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\Connection.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */