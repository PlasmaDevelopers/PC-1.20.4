/*     */ package net.minecraft.client.gui.screens;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.util.Optional;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.DefaultUncaughtExceptionHandler;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.client.GameNarrator;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.multiplayer.ClientHandshakePacketListenerImpl;
/*     */ import net.minecraft.client.multiplayer.ServerData;
/*     */ import net.minecraft.client.multiplayer.chat.report.ReportEnvironment;
/*     */ import net.minecraft.client.multiplayer.resolver.ResolvedServerAddress;
/*     */ import net.minecraft.client.multiplayer.resolver.ServerAddress;
/*     */ import net.minecraft.client.multiplayer.resolver.ServerNameResolver;
/*     */ import net.minecraft.client.quickplay.QuickPlay;
/*     */ import net.minecraft.client.quickplay.QuickPlayLog;
/*     */ import net.minecraft.client.resources.server.ServerPackManager;
/*     */ import net.minecraft.network.Connection;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.network.protocol.PacketFlow;
/*     */ import net.minecraft.network.protocol.login.ClientLoginPacketListener;
/*     */ import net.minecraft.network.protocol.login.ServerboundHelloPacket;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class ConnectScreen extends Screen {
/*  33 */   private static final AtomicInteger UNIQUE_THREAD_ID = new AtomicInteger(0);
/*  34 */   static final Logger LOGGER = LogUtils.getLogger();
/*     */   private static final long NARRATION_DELAY_MS = 2000L;
/*  36 */   public static final Component ABORT_CONNECTION = (Component)Component.translatable("connect.aborted");
/*  37 */   public static final Component UNKNOWN_HOST_MESSAGE = (Component)Component.translatable("disconnect.genericReason", new Object[] { Component.translatable("disconnect.unknownHost") });
/*     */   
/*     */   @Nullable
/*     */   volatile Connection connection;
/*     */   @Nullable
/*     */   ChannelFuture channelFuture;
/*     */   volatile boolean aborted;
/*     */   final Screen parent;
/*  45 */   private Component status = (Component)Component.translatable("connect.connecting");
/*  46 */   private long lastNarration = -1L;
/*     */   final Component connectFailedTitle;
/*     */   
/*     */   private ConnectScreen(Screen $$0, Component $$1) {
/*  50 */     super(GameNarrator.NO_TITLE);
/*  51 */     this.parent = $$0;
/*  52 */     this.connectFailedTitle = $$1;
/*     */   }
/*     */   
/*     */   public static void startConnecting(Screen $$0, Minecraft $$1, ServerAddress $$2, ServerData $$3, boolean $$4) {
/*  56 */     if ($$1.screen instanceof ConnectScreen) {
/*  57 */       LOGGER.error("Attempt to connect while already connecting");
/*     */       return;
/*     */     } 
/*  60 */     ConnectScreen $$5 = new ConnectScreen($$0, $$4 ? QuickPlay.ERROR_TITLE : CommonComponents.CONNECT_FAILED);
/*  61 */     $$1.disconnect();
/*  62 */     $$1.prepareForMultiplayer();
/*  63 */     $$1.updateReportEnvironment(ReportEnvironment.thirdParty(($$3 != null) ? $$3.ip : $$2.getHost()));
/*  64 */     $$1.quickPlayLog().setWorldData(QuickPlayLog.Type.MULTIPLAYER, $$3.ip, $$3.name);
/*  65 */     $$1.setScreen($$5);
/*  66 */     $$5.connect($$1, $$2, $$3);
/*     */   }
/*     */   
/*     */   private void connect(final Minecraft minecraft, final ServerAddress hostAndPort, @Nullable final ServerData server) {
/*  70 */     LOGGER.info("Connecting to {}, {}", hostAndPort.getHost(), Integer.valueOf(hostAndPort.getPort()));
/*  71 */     Thread $$3 = new Thread("Server Connector #" + UNIQUE_THREAD_ID.incrementAndGet())
/*     */       {
/*     */         public void run() {
/*  74 */           InetSocketAddress $$0 = null; try {
/*     */             Connection $$2;
/*  76 */             if (ConnectScreen.this.aborted) {
/*     */               return;
/*     */             }
/*     */ 
/*     */             
/*  81 */             Optional<InetSocketAddress> $$1 = ServerNameResolver.DEFAULT.resolveAddress(hostAndPort).map(ResolvedServerAddress::asInetSocketAddress);
/*  82 */             if (ConnectScreen.this.aborted) {
/*     */               return;
/*     */             }
/*  85 */             if ($$1.isEmpty()) {
/*  86 */               minecraft.execute(() -> $$0.setScreen(new DisconnectedScreen(ConnectScreen.this.parent, ConnectScreen.this.connectFailedTitle, ConnectScreen.UNKNOWN_HOST_MESSAGE)));
/*     */               return;
/*     */             } 
/*  89 */             $$0 = $$1.get();
/*     */ 
/*     */             
/*  92 */             synchronized (ConnectScreen.this) {
/*  93 */               if (ConnectScreen.this.aborted) {
/*     */                 return;
/*     */               }
/*  96 */               $$2 = new Connection(PacketFlow.CLIENTBOUND);
/*  97 */               $$2.setBandwidthLogger(minecraft.getDebugOverlay().getBandwidthLogger());
/*     */               
/*  99 */               ConnectScreen.this.channelFuture = Connection.connect($$0, minecraft.options.useNativeTransport(), $$2);
/*     */             } 
/* 101 */             ConnectScreen.this.channelFuture.syncUninterruptibly();
/*     */             
/* 103 */             synchronized (ConnectScreen.this) {
/* 104 */               if (ConnectScreen.this.aborted) {
/* 105 */                 $$2.disconnect(ConnectScreen.ABORT_CONNECTION);
/*     */                 return;
/*     */               } 
/* 108 */               ConnectScreen.this.connection = $$2;
/* 109 */               minecraft.getDownloadedPackSource().configureForServerControl($$2, (server != null) ? convertPackStatus(server.getResourcePackStatus()) : ServerPackManager.PackPromptStatus.PENDING);
/*     */             } 
/* 111 */             ConnectScreen.this.connection.initiateServerboundPlayConnection($$0
/* 112 */                 .getHostName(), $$0
/* 113 */                 .getPort(), (ClientLoginPacketListener)new ClientHandshakePacketListenerImpl(ConnectScreen.this.connection, minecraft, server, ConnectScreen.this.parent, false, null, ConnectScreen.this::updateStatus));
/*     */ 
/*     */             
/* 116 */             ConnectScreen.this.connection.send((Packet)new ServerboundHelloPacket(minecraft.getUser().getName(), minecraft.getUser().getProfileId()));
/* 117 */           } catch (Exception $$4) {
/* 118 */             Exception $$7; if (ConnectScreen.this.aborted) {
/*     */               return;
/*     */             }
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 125 */             Throwable throwable = $$4.getCause(); if (throwable instanceof Exception) { Exception $$5 = (Exception)throwable;
/* 126 */               Exception $$6 = $$5; }
/*     */             else
/* 128 */             { $$7 = $$4; }
/*     */ 
/*     */             
/* 131 */             ConnectScreen.LOGGER.error("Couldn't connect to server", $$4);
/* 132 */             String $$8 = ($$0 == null) ? $$7.getMessage() : $$7.getMessage().replaceAll($$0.getHostName() + ":" + $$0.getHostName(), "").replaceAll($$0.toString(), "");
/* 133 */             minecraft.execute(() -> $$0.setScreen(new DisconnectedScreen(ConnectScreen.this.parent, ConnectScreen.this.connectFailedTitle, (Component)Component.translatable("disconnect.genericReason", new Object[] { $$1 }))));
/*     */           } 
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         private static ServerPackManager.PackPromptStatus convertPackStatus(ServerData.ServerPackStatus $$0) {
/*     */           // Byte code:
/*     */           //   0: getstatic net/minecraft/client/gui/screens/ConnectScreen$2.$SwitchMap$net$minecraft$client$multiplayer$ServerData$ServerPackStatus : [I
/*     */           //   3: aload_0
/*     */           //   4: invokevirtual ordinal : ()I
/*     */           //   7: iaload
/*     */           //   8: tableswitch default -> 36, 1 -> 44, 2 -> 50, 3 -> 56
/*     */           //   36: new java/lang/IncompatibleClassChangeError
/*     */           //   39: dup
/*     */           //   40: invokespecial <init> : ()V
/*     */           //   43: athrow
/*     */           //   44: getstatic net/minecraft/client/resources/server/ServerPackManager$PackPromptStatus.ALLOWED : Lnet/minecraft/client/resources/server/ServerPackManager$PackPromptStatus;
/*     */           //   47: goto -> 59
/*     */           //   50: getstatic net/minecraft/client/resources/server/ServerPackManager$PackPromptStatus.DECLINED : Lnet/minecraft/client/resources/server/ServerPackManager$PackPromptStatus;
/*     */           //   53: goto -> 59
/*     */           //   56: getstatic net/minecraft/client/resources/server/ServerPackManager$PackPromptStatus.PENDING : Lnet/minecraft/client/resources/server/ServerPackManager$PackPromptStatus;
/*     */           //   59: areturn
/*     */           // Line number table:
/*     */           //   Java source line number -> byte code offset
/*     */           //   #138	-> 0
/*     */           //   #139	-> 44
/*     */           //   #140	-> 50
/*     */           //   #141	-> 56
/*     */           //   #138	-> 59
/*     */           // Local variable table:
/*     */           //   start	length	slot	name	descriptor
/*     */           //   0	60	0	$$0	Lnet/minecraft/client/multiplayer/ServerData$ServerPackStatus;
/*     */         }
/*     */       };
/* 145 */     $$3.setUncaughtExceptionHandler((Thread.UncaughtExceptionHandler)new DefaultUncaughtExceptionHandler(LOGGER));
/* 146 */     $$3.start();
/*     */   }
/*     */   
/*     */   private void updateStatus(Component $$0) {
/* 150 */     this.status = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 155 */     if (this.connection != null) {
/* 156 */       if (this.connection.isConnected()) {
/* 157 */         this.connection.tick();
/*     */       } else {
/* 159 */         this.connection.handleDisconnection();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldCloseOnEsc() {
/* 166 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void init() {
/* 171 */     addRenderableWidget(Button.builder(CommonComponents.GUI_CANCEL, $$0 -> {
/*     */             synchronized (this) {
/*     */               this.aborted = true;
/*     */               if (this.channelFuture != null) {
/*     */                 this.channelFuture.cancel(true);
/*     */                 this.channelFuture = null;
/*     */               } 
/*     */               if (this.connection != null) {
/*     */                 this.connection.disconnect(ABORT_CONNECTION);
/*     */               }
/*     */             } 
/*     */             this.minecraft.setScreen(this.parent);
/* 183 */           }).bounds(this.width / 2 - 100, this.height / 4 + 120 + 12, 200, 20).build());
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 188 */     super.render($$0, $$1, $$2, $$3);
/*     */     
/* 190 */     long $$4 = Util.getMillis();
/* 191 */     if ($$4 - this.lastNarration > 2000L) {
/* 192 */       this.lastNarration = $$4;
/*     */       
/* 194 */       this.minecraft.getNarrator().sayNow((Component)Component.translatable("narrator.joining"));
/*     */     } 
/*     */     
/* 197 */     $$0.drawCenteredString(this.font, this.status, this.width / 2, this.height / 2 - 50, 16777215);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\ConnectScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */