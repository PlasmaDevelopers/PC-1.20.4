/*     */ package net.minecraft.client.multiplayer;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import io.netty.bootstrap.Bootstrap;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelInitializer;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.channel.socket.nio.NioSocketChannel;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.UnknownHostException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.client.gui.screens.ConnectScreen;
/*     */ import net.minecraft.client.multiplayer.resolver.ResolvedServerAddress;
/*     */ import net.minecraft.client.multiplayer.resolver.ServerAddress;
/*     */ import net.minecraft.client.multiplayer.resolver.ServerNameResolver;
/*     */ import net.minecraft.network.Connection;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.network.protocol.status.ClientStatusPacketListener;
/*     */ import net.minecraft.network.protocol.status.ClientboundPongResponsePacket;
/*     */ import net.minecraft.network.protocol.status.ClientboundStatusResponsePacket;
/*     */ import net.minecraft.network.protocol.status.ServerStatus;
/*     */ import net.minecraft.network.protocol.status.ServerboundPingRequestPacket;
/*     */ import net.minecraft.network.protocol.status.ServerboundStatusRequestPacket;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class ServerStatusPinger {
/*  40 */   private static final Logger LOGGER = LogUtils.getLogger();
/*  41 */   private static final Component CANT_CONNECT_MESSAGE = (Component)Component.translatable("multiplayer.status.cannot_connect").withColor(-65536);
/*     */   
/*  43 */   private final List<Connection> connections = Collections.synchronizedList(Lists.newArrayList());
/*     */   
/*     */   public void pingServer(final ServerData data, final Runnable onPersistentDataChange) throws UnknownHostException {
/*  46 */     final ServerAddress rawAddress = ServerAddress.parseString(data.ip);
/*     */ 
/*     */     
/*  49 */     Optional<InetSocketAddress> $$3 = ServerNameResolver.DEFAULT.resolveAddress($$2).map(ResolvedServerAddress::asInetSocketAddress);
/*  50 */     if ($$3.isEmpty()) {
/*  51 */       onPingFailed(ConnectScreen.UNKNOWN_HOST_MESSAGE, data);
/*     */       
/*     */       return;
/*     */     } 
/*  55 */     final InetSocketAddress address = $$3.get();
/*  56 */     final Connection connection = Connection.connectToServer($$4, false, null);
/*     */     
/*  58 */     this.connections.add($$5);
/*     */     
/*  60 */     data.motd = (Component)Component.translatable("multiplayer.status.pinging");
/*  61 */     data.ping = -1L;
/*  62 */     data.playerList = Collections.emptyList();
/*     */     
/*  64 */     ClientStatusPacketListener $$6 = new ClientStatusPacketListener()
/*     */       {
/*     */         private boolean success;
/*     */         private boolean receivedPing;
/*     */         private long pingStart;
/*     */         
/*     */         public void handleStatusResponse(ClientboundStatusResponsePacket $$0) {
/*  71 */           if (this.receivedPing) {
/*  72 */             connection.disconnect((Component)Component.translatable("multiplayer.status.unrequested"));
/*     */             return;
/*     */           } 
/*  75 */           this.receivedPing = true;
/*  76 */           ServerStatus $$1 = $$0.status();
/*  77 */           data.motd = $$1.description();
/*     */           
/*  79 */           $$1.version().ifPresentOrElse($$1 -> {
/*     */                 $$0.version = (Component)Component.literal($$1.name());
/*     */                 
/*     */                 $$0.protocol = $$1.protocol();
/*     */               }() -> {
/*     */                 $$0.version = (Component)Component.translatable("multiplayer.status.old");
/*     */                 $$0.protocol = 0;
/*     */               });
/*  87 */           $$1.players().ifPresentOrElse($$1 -> {
/*     */                 $$0.status = ServerStatusPinger.formatPlayerCount($$1.online(), $$1.max());
/*     */                 
/*     */                 $$0.players = $$1;
/*     */                 
/*     */                 if (!$$1.sample().isEmpty()) {
/*     */                   List<Component> $$2 = new ArrayList<>($$1.sample().size());
/*     */                   
/*     */                   for (GameProfile $$3 : $$1.sample()) {
/*     */                     $$2.add(Component.literal($$3.getName()));
/*     */                   }
/*     */                   
/*     */                   if ($$1.sample().size() < $$1.online()) {
/*     */                     $$2.add(Component.translatable("multiplayer.status.and_more", new Object[] { Integer.valueOf($$1.online() - $$1.sample().size()) }));
/*     */                   }
/*     */                   $$0.playerList = $$2;
/*     */                 } else {
/*     */                   $$0.playerList = List.of();
/*     */                 } 
/*     */               }() -> $$0.status = (Component)Component.translatable("multiplayer.status.unknown").withStyle(ChatFormatting.DARK_GRAY));
/* 107 */           $$1.favicon().ifPresent($$2 -> {
/*     */                 if (!Arrays.equals($$2.iconBytes(), $$0.getIconBytes())) {
/*     */                   $$0.setIconBytes(ServerData.validateIcon($$2.iconBytes()));
/*     */                   
/*     */                   $$1.run();
/*     */                 } 
/*     */               });
/* 114 */           this.pingStart = Util.getMillis();
/* 115 */           connection.send((Packet)new ServerboundPingRequestPacket(this.pingStart));
/* 116 */           this.success = true;
/*     */         }
/*     */ 
/*     */         
/*     */         public void handlePongResponse(ClientboundPongResponsePacket $$0) {
/* 121 */           long $$1 = this.pingStart;
/* 122 */           long $$2 = Util.getMillis();
/* 123 */           data.ping = $$2 - $$1;
/*     */           
/* 125 */           connection.disconnect((Component)Component.translatable("multiplayer.status.finished"));
/*     */         }
/*     */ 
/*     */         
/*     */         public void onDisconnect(Component $$0) {
/* 130 */           if (!this.success) {
/* 131 */             ServerStatusPinger.this.onPingFailed($$0, data);
/* 132 */             ServerStatusPinger.this.pingLegacyServer(address, rawAddress, data);
/*     */           } 
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean isAcceptingMessages() {
/* 138 */           return connection.isConnected();
/*     */         }
/*     */       };
/*     */     
/*     */     try {
/* 143 */       $$5.initiateServerboundStatusConnection($$2.getHost(), $$2.getPort(), $$6);
/* 144 */       $$5.send((Packet)new ServerboundStatusRequestPacket());
/* 145 */     } catch (Throwable $$7) {
/* 146 */       LOGGER.error("Failed to ping server {}", $$2, $$7);
/*     */     } 
/*     */   }
/*     */   
/*     */   void onPingFailed(Component $$0, ServerData $$1) {
/* 151 */     LOGGER.error("Can't ping {}: {}", $$1.ip, $$0.getString());
/* 152 */     $$1.motd = CANT_CONNECT_MESSAGE;
/* 153 */     $$1.status = CommonComponents.EMPTY;
/*     */   }
/*     */   
/*     */   void pingLegacyServer(InetSocketAddress $$0, final ServerAddress rawAddress, final ServerData data) {
/* 157 */     ((Bootstrap)((Bootstrap)((Bootstrap)(new Bootstrap())
/* 158 */       .group(Connection.NETWORK_WORKER_GROUP.get()))
/* 159 */       .handler((ChannelHandler)new ChannelInitializer<Channel>()
/*     */         {
/*     */           protected void initChannel(Channel $$0)
/*     */           {
/*     */             try {
/* 164 */               $$0.config().setOption(ChannelOption.TCP_NODELAY, Boolean.valueOf(true));
/* 165 */             } catch (ChannelException channelException) {}
/*     */ 
/*     */             
/* 168 */             $$0.pipeline().addLast(new ChannelHandler[] { (ChannelHandler)new LegacyServerPinger(this.val$rawAddress, ($$1, $$2, $$3, $$4, $$5) -> {
/*     */                       $$0.protocol = -1;
/*     */                       
/*     */                       $$0.version = (Component)Component.literal($$2);
/*     */                       
/*     */                       $$0.motd = (Component)Component.literal($$3);
/*     */                       
/*     */                       $$0.status = ServerStatusPinger.formatPlayerCount($$4, $$5);
/*     */                       $$0.players = new ServerStatus.Players($$5, $$4, List.of());
/*     */                     }) });
/*     */           }
/* 179 */         })).channel(NioSocketChannel.class))
/* 180 */       .connect($$0.getAddress(), $$0.getPort());
/*     */   }
/*     */   
/*     */   public static Component formatPlayerCount(int $$0, int $$1) {
/* 184 */     MutableComponent mutableComponent1 = Component.literal(Integer.toString($$0)).withStyle(ChatFormatting.GRAY);
/* 185 */     MutableComponent mutableComponent2 = Component.literal(Integer.toString($$1)).withStyle(ChatFormatting.GRAY);
/* 186 */     return (Component)Component.translatable("multiplayer.status.player_count", new Object[] { mutableComponent1, mutableComponent2 }).withStyle(ChatFormatting.DARK_GRAY);
/*     */   }
/*     */   
/*     */   public void tick() {
/* 190 */     synchronized (this.connections) {
/* 191 */       Iterator<Connection> $$0 = this.connections.iterator();
/* 192 */       while ($$0.hasNext()) {
/* 193 */         Connection $$1 = $$0.next();
/*     */         
/* 195 */         if ($$1.isConnected()) {
/* 196 */           $$1.tick(); continue;
/*     */         } 
/* 198 */         $$0.remove();
/* 199 */         $$1.handleDisconnection();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeAll() {
/* 206 */     synchronized (this.connections) {
/* 207 */       Iterator<Connection> $$0 = this.connections.iterator();
/* 208 */       while ($$0.hasNext()) {
/* 209 */         Connection $$1 = $$0.next();
/*     */         
/* 211 */         if ($$1.isConnected()) {
/* 212 */           $$0.remove();
/* 213 */           $$1.disconnect((Component)Component.translatable("multiplayer.status.cancelled"));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\multiplayer\ServerStatusPinger.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */