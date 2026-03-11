/*     */ package net.minecraft.server.network;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.CrashReport;
/*     */ import net.minecraft.CrashReportCategory;
/*     */ import net.minecraft.ReportedException;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.network.Connection;
/*     */ import net.minecraft.network.PacketListener;
/*     */ import net.minecraft.network.PacketSendListener;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.network.protocol.PacketUtils;
/*     */ import net.minecraft.network.protocol.common.ClientboundKeepAlivePacket;
/*     */ import net.minecraft.network.protocol.common.ServerCommonPacketListener;
/*     */ import net.minecraft.network.protocol.common.ServerboundCustomPayloadPacket;
/*     */ import net.minecraft.network.protocol.common.ServerboundKeepAlivePacket;
/*     */ import net.minecraft.network.protocol.common.ServerboundPongPacket;
/*     */ import net.minecraft.network.protocol.common.ServerboundResourcePackPacket;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.server.level.ClientInformation;
/*     */ import net.minecraft.util.VisibleForDebug;
/*     */ import net.minecraft.util.thread.BlockableEventLoop;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public abstract class ServerCommonPacketListenerImpl implements ServerCommonPacketListener {
/*  29 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   public static final int LATENCY_CHECK_INTERVAL = 15000;
/*  31 */   private static final Component TIMEOUT_DISCONNECTION_MESSAGE = (Component)Component.translatable("disconnect.timeout");
/*     */   
/*     */   protected final MinecraftServer server;
/*     */   
/*     */   protected final Connection connection;
/*     */   private long keepAliveTime;
/*     */   private boolean keepAlivePending;
/*     */   private long keepAliveChallenge;
/*     */   private int latency;
/*     */   private volatile boolean suspendFlushingOnServerThread = false;
/*     */   
/*     */   public ServerCommonPacketListenerImpl(MinecraftServer $$0, Connection $$1, CommonListenerCookie $$2) {
/*  43 */     this.server = $$0;
/*  44 */     this.connection = $$1;
/*  45 */     this.keepAliveTime = Util.getMillis();
/*  46 */     this.latency = $$2.latency();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisconnect(Component $$0) {
/*  51 */     if (isSingleplayerOwner()) {
/*  52 */       LOGGER.info("Stopping singleplayer server as player logged out");
/*  53 */       this.server.halt(false);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleKeepAlive(ServerboundKeepAlivePacket $$0) {
/*  59 */     if (this.keepAlivePending && $$0.getId() == this.keepAliveChallenge) {
/*  60 */       int $$1 = (int)(Util.getMillis() - this.keepAliveTime);
/*  61 */       this.latency = (this.latency * 3 + $$1) / 4;
/*  62 */       this.keepAlivePending = false;
/*     */     }
/*  64 */     else if (!isSingleplayerOwner()) {
/*  65 */       disconnect(TIMEOUT_DISCONNECTION_MESSAGE);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void handlePong(ServerboundPongPacket $$0) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleCustomPayload(ServerboundCustomPayloadPacket $$0) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleResourcePackResponse(ServerboundResourcePackPacket $$0) {
/*  80 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.server);
/*  81 */     if ($$0.action() == ServerboundResourcePackPacket.Action.DECLINED && this.server.isResourcePackRequired()) {
/*  82 */       LOGGER.info("Disconnecting {} due to resource pack {} rejection", playerProfile().getName(), $$0.id());
/*  83 */       disconnect((Component)Component.translatable("multiplayer.requiredTexturePrompt.disconnect"));
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void keepConnectionAlive() {
/*  88 */     this.server.getProfiler().push("keepAlive");
/*  89 */     long $$0 = Util.getMillis();
/*  90 */     if ($$0 - this.keepAliveTime >= 15000L) {
/*  91 */       if (this.keepAlivePending) {
/*  92 */         disconnect(TIMEOUT_DISCONNECTION_MESSAGE);
/*     */       } else {
/*  94 */         this.keepAlivePending = true;
/*  95 */         this.keepAliveTime = $$0;
/*  96 */         this.keepAliveChallenge = $$0;
/*  97 */         send((Packet<?>)new ClientboundKeepAlivePacket(this.keepAliveChallenge));
/*     */       } 
/*     */     }
/* 100 */     this.server.getProfiler().pop();
/*     */   }
/*     */   
/*     */   public void suspendFlushing() {
/* 104 */     this.suspendFlushingOnServerThread = true;
/*     */   }
/*     */   
/*     */   public void resumeFlushing() {
/* 108 */     this.suspendFlushingOnServerThread = false;
/* 109 */     this.connection.flushChannel();
/*     */   }
/*     */   
/*     */   public void send(Packet<?> $$0) {
/* 113 */     send($$0, null);
/*     */   }
/*     */   
/*     */   public void send(Packet<?> $$0, @Nullable PacketSendListener $$1) {
/* 117 */     boolean $$2 = (!this.suspendFlushingOnServerThread || !this.server.isSameThread());
/*     */     try {
/* 119 */       this.connection.send($$0, $$1, $$2);
/* 120 */     } catch (Throwable $$3) {
/* 121 */       CrashReport $$4 = CrashReport.forThrowable($$3, "Sending packet");
/* 122 */       CrashReportCategory $$5 = $$4.addCategory("Packet being sent");
/*     */       
/* 124 */       $$5.setDetail("Packet class", () -> $$0.getClass().getCanonicalName());
/*     */       
/* 126 */       throw new ReportedException($$4);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void disconnect(Component $$0) {
/* 131 */     this.connection.send((Packet)new ClientboundDisconnectPacket($$0), PacketSendListener.thenRun(() -> this.connection.disconnect($$0)));
/* 132 */     this.connection.setReadOnly();
/* 133 */     Objects.requireNonNull(this.connection); this.server.executeBlocking(this.connection::handleDisconnection);
/*     */   }
/*     */   
/*     */   protected boolean isSingleplayerOwner() {
/* 137 */     return this.server.isSingleplayerOwner(playerProfile());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @VisibleForDebug
/*     */   public GameProfile getOwner() {
/* 144 */     return playerProfile();
/*     */   }
/*     */   
/*     */   public int latency() {
/* 148 */     return this.latency;
/*     */   }
/*     */   
/*     */   protected CommonListenerCookie createCookie(ClientInformation $$0) {
/* 152 */     return new CommonListenerCookie(
/* 153 */         playerProfile(), this.latency, $$0);
/*     */   }
/*     */   
/*     */   protected abstract GameProfile playerProfile();
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\network\ServerCommonPacketListenerImpl.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */