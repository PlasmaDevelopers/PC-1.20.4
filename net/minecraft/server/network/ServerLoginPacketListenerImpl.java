/*     */ package net.minecraft.server.network;
/*     */ 
/*     */ import com.google.common.primitives.Ints;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
/*     */ import com.mojang.authlib.yggdrasil.ProfileResult;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.math.BigInteger;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.SocketAddress;
/*     */ import java.security.PrivateKey;
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import javax.annotation.Nullable;
/*     */ import javax.crypto.Cipher;
/*     */ import javax.crypto.SecretKey;
/*     */ import net.minecraft.CrashReportCategory;
/*     */ import net.minecraft.DefaultUncaughtExceptionHandler;
/*     */ import net.minecraft.core.UUIDUtil;
/*     */ import net.minecraft.network.Connection;
/*     */ import net.minecraft.network.PacketListener;
/*     */ import net.minecraft.network.PacketSendListener;
/*     */ import net.minecraft.network.TickablePacketListener;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.network.protocol.login.ClientboundGameProfilePacket;
/*     */ import net.minecraft.network.protocol.login.ClientboundHelloPacket;
/*     */ import net.minecraft.network.protocol.login.ClientboundLoginCompressionPacket;
/*     */ import net.minecraft.network.protocol.login.ClientboundLoginDisconnectPacket;
/*     */ import net.minecraft.network.protocol.login.ServerLoginPacketListener;
/*     */ import net.minecraft.network.protocol.login.ServerboundCustomQueryAnswerPacket;
/*     */ import net.minecraft.network.protocol.login.ServerboundHelloPacket;
/*     */ import net.minecraft.network.protocol.login.ServerboundKeyPacket;
/*     */ import net.minecraft.network.protocol.login.ServerboundLoginAcknowledgedPacket;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.server.players.PlayerList;
/*     */ import net.minecraft.util.Crypt;
/*     */ import net.minecraft.util.CryptException;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import org.apache.commons.lang3.Validate;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class ServerLoginPacketListenerImpl implements ServerLoginPacketListener, TickablePacketListener {
/*  46 */   private static final AtomicInteger UNIQUE_THREAD_ID = new AtomicInteger(0);
/*  47 */   static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private static final int MAX_TICKS_BEFORE_LOGIN = 600;
/*  50 */   private static final Component DISCONNECT_UNEXPECTED_QUERY = (Component)Component.translatable("multiplayer.disconnect.unexpected_query_response");
/*     */   
/*     */   private final byte[] challenge;
/*     */   final MinecraftServer server;
/*     */   final Connection connection;
/*  55 */   private volatile State state = State.HELLO;
/*     */   private int tick;
/*     */   @Nullable
/*     */   String requestedUsername;
/*     */   @Nullable
/*     */   private GameProfile authenticatedProfile;
/*  61 */   private final String serverId = "";
/*     */   
/*     */   public ServerLoginPacketListenerImpl(MinecraftServer $$0, Connection $$1) {
/*  64 */     this.server = $$0;
/*  65 */     this.connection = $$1;
/*  66 */     this.challenge = Ints.toByteArray(RandomSource.create().nextInt());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void tick() {
/*  73 */     if (this.state == State.VERIFYING) {
/*  74 */       verifyLoginAndFinishConnectionSetup(Objects.<GameProfile>requireNonNull(this.authenticatedProfile));
/*     */     }
/*     */     
/*  77 */     if (this.state == State.WAITING_FOR_DUPE_DISCONNECT && 
/*  78 */       !isPlayerAlreadyInWorld(Objects.<GameProfile>requireNonNull(this.authenticatedProfile))) {
/*  79 */       finishLoginAndWaitForClient(this.authenticatedProfile);
/*     */     }
/*     */ 
/*     */     
/*  83 */     if (this.tick++ == 600) {
/*  84 */       disconnect((Component)Component.translatable("multiplayer.disconnect.slow_login"));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAcceptingMessages() {
/*  90 */     return this.connection.isConnected();
/*     */   }
/*     */   
/*     */   public void disconnect(Component $$0) {
/*     */     try {
/*  95 */       LOGGER.info("Disconnecting {}: {}", getUserName(), $$0.getString());
/*  96 */       this.connection.send((Packet)new ClientboundLoginDisconnectPacket($$0));
/*  97 */       this.connection.disconnect($$0);
/*  98 */     } catch (Exception $$1) {
/*  99 */       LOGGER.error("Error whilst disconnecting player", $$1);
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isPlayerAlreadyInWorld(GameProfile $$0) {
/* 104 */     return (this.server.getPlayerList().getPlayer($$0.getId()) != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisconnect(Component $$0) {
/* 109 */     LOGGER.info("{} lost connection: {}", getUserName(), $$0.getString());
/*     */   }
/*     */   
/*     */   public String getUserName() {
/* 113 */     String $$0 = this.connection.getLoggableAddress(this.server.logIPs());
/* 114 */     if (this.requestedUsername != null) {
/* 115 */       return this.requestedUsername + " (" + this.requestedUsername + ")";
/*     */     }
/* 117 */     return $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleHello(ServerboundHelloPacket $$0) {
/* 122 */     Validate.validState((this.state == State.HELLO), "Unexpected hello packet", new Object[0]);
/* 123 */     Validate.validState(Player.isValidUsername($$0.name()), "Invalid characters in username", new Object[0]);
/*     */     
/* 125 */     this.requestedUsername = $$0.name();
/*     */     
/* 127 */     GameProfile $$1 = this.server.getSingleplayerProfile();
/* 128 */     if ($$1 != null && this.requestedUsername.equalsIgnoreCase($$1.getName())) {
/* 129 */       startClientVerification($$1);
/*     */       
/*     */       return;
/*     */     } 
/* 133 */     if (this.server.usesAuthentication() && !this.connection.isMemoryConnection()) {
/* 134 */       this.state = State.KEY;
/* 135 */       this.connection.send((Packet)new ClientboundHelloPacket("", this.server.getKeyPair().getPublic().getEncoded(), this.challenge));
/*     */     } else {
/* 137 */       startClientVerification(UUIDUtil.createOfflineProfile(this.requestedUsername));
/*     */     } 
/*     */   }
/*     */   
/*     */   void startClientVerification(GameProfile $$0) {
/* 142 */     this.authenticatedProfile = $$0;
/* 143 */     this.state = State.VERIFYING;
/*     */   }
/*     */ 
/*     */   
/*     */   private void verifyLoginAndFinishConnectionSetup(GameProfile $$0) {
/* 148 */     PlayerList $$1 = this.server.getPlayerList();
/* 149 */     Component $$2 = $$1.canPlayerLogin(this.connection.getRemoteAddress(), $$0);
/* 150 */     if ($$2 != null) {
/* 151 */       disconnect($$2);
/*     */     } else {
/* 153 */       if (this.server.getCompressionThreshold() >= 0 && !this.connection.isMemoryConnection()) {
/* 154 */         this.connection.send((Packet)new ClientboundLoginCompressionPacket(this.server.getCompressionThreshold()), PacketSendListener.thenRun(() -> this.connection.setupCompression(this.server.getCompressionThreshold(), true)));
/*     */       }
/*     */ 
/*     */       
/* 158 */       boolean $$3 = $$1.disconnectAllPlayersWithProfile($$0);
/*     */       
/* 160 */       if ($$3) {
/* 161 */         this.state = State.WAITING_FOR_DUPE_DISCONNECT;
/*     */       } else {
/* 163 */         finishLoginAndWaitForClient($$0);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void finishLoginAndWaitForClient(GameProfile $$0) {
/* 169 */     this.state = State.PROTOCOL_SWITCHING;
/*     */     
/* 171 */     this.connection.send((Packet)new ClientboundGameProfilePacket($$0));
/*     */   }
/*     */   
/*     */   public void handleKey(ServerboundKeyPacket $$0) {
/*     */     final String digest;
/* 176 */     Validate.validState((this.state == State.KEY), "Unexpected key packet", new Object[0]);
/*     */ 
/*     */     
/*     */     try {
/* 180 */       PrivateKey $$1 = this.server.getKeyPair().getPrivate();
/*     */       
/* 182 */       if (!$$0.isChallengeValid(this.challenge, $$1)) {
/* 183 */         throw new IllegalStateException("Protocol error");
/*     */       }
/*     */       
/* 186 */       SecretKey $$2 = $$0.getSecretKey($$1);
/* 187 */       Cipher $$3 = Crypt.getCipher(2, $$2);
/* 188 */       Cipher $$4 = Crypt.getCipher(1, $$2);
/* 189 */       $$5 = (new BigInteger(Crypt.digestData("", this.server.getKeyPair().getPublic(), $$2))).toString(16);
/*     */       
/* 191 */       this.state = State.AUTHENTICATING;
/* 192 */       this.connection.setEncryptionKey($$3, $$4);
/* 193 */     } catch (CryptException $$6) {
/* 194 */       throw new IllegalStateException("Protocol error", $$6);
/*     */     } 
/*     */     
/* 197 */     Thread $$8 = new Thread("User Authenticator #" + UNIQUE_THREAD_ID.incrementAndGet())
/*     */       {
/*     */         public void run() {
/* 200 */           String $$0 = Objects.<String>requireNonNull(ServerLoginPacketListenerImpl.this.requestedUsername, "Player name not initialized");
/*     */           try {
/* 202 */             ProfileResult $$1 = ServerLoginPacketListenerImpl.this.server.getSessionService().hasJoinedServer($$0, digest, getAddress());
/*     */             
/* 204 */             if ($$1 != null) {
/* 205 */               GameProfile $$2 = $$1.profile();
/* 206 */               ServerLoginPacketListenerImpl.LOGGER.info("UUID of player {} is {}", $$2.getName(), $$2.getId());
/* 207 */               ServerLoginPacketListenerImpl.this.startClientVerification($$2);
/* 208 */             } else if (ServerLoginPacketListenerImpl.this.server.isSingleplayer()) {
/* 209 */               ServerLoginPacketListenerImpl.LOGGER.warn("Failed to verify username but will let them in anyway!");
/* 210 */               ServerLoginPacketListenerImpl.this.startClientVerification(UUIDUtil.createOfflineProfile($$0));
/*     */             } else {
/* 212 */               ServerLoginPacketListenerImpl.this.disconnect((Component)Component.translatable("multiplayer.disconnect.unverified_username"));
/* 213 */               ServerLoginPacketListenerImpl.LOGGER.error("Username '{}' tried to join with an invalid session", $$0);
/*     */             } 
/* 215 */           } catch (AuthenticationUnavailableException $$3) {
/* 216 */             if (ServerLoginPacketListenerImpl.this.server.isSingleplayer()) {
/* 217 */               ServerLoginPacketListenerImpl.LOGGER.warn("Authentication servers are down but will let them in anyway!");
/* 218 */               ServerLoginPacketListenerImpl.this.startClientVerification(UUIDUtil.createOfflineProfile($$0));
/*     */             } else {
/* 220 */               ServerLoginPacketListenerImpl.this.disconnect((Component)Component.translatable("multiplayer.disconnect.authservers_down"));
/* 221 */               ServerLoginPacketListenerImpl.LOGGER.error("Couldn't verify username because servers are unavailable");
/*     */             } 
/*     */           } 
/*     */         }
/*     */         
/*     */         @Nullable
/*     */         private InetAddress getAddress() {
/* 228 */           SocketAddress $$0 = ServerLoginPacketListenerImpl.this.connection.getRemoteAddress();
/* 229 */           return (ServerLoginPacketListenerImpl.this.server.getPreventProxyConnections() && $$0 instanceof InetSocketAddress) ? ((InetSocketAddress)$$0).getAddress() : null;
/*     */         }
/*     */       };
/* 232 */     $$8.setUncaughtExceptionHandler((Thread.UncaughtExceptionHandler)new DefaultUncaughtExceptionHandler(LOGGER));
/* 233 */     $$8.start();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleCustomQueryPacket(ServerboundCustomQueryAnswerPacket $$0) {
/* 239 */     disconnect(DISCONNECT_UNEXPECTED_QUERY);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleLoginAcknowledgement(ServerboundLoginAcknowledgedPacket $$0) {
/* 245 */     Validate.validState((this.state == State.PROTOCOL_SWITCHING), "Unexpected login acknowledgement packet", new Object[0]);
/* 246 */     CommonListenerCookie $$1 = CommonListenerCookie.createInitial(Objects.<GameProfile>requireNonNull(this.authenticatedProfile));
/* 247 */     ServerConfigurationPacketListenerImpl $$2 = new ServerConfigurationPacketListenerImpl(this.server, this.connection, $$1);
/* 248 */     this.connection.setListener((PacketListener)$$2);
/* 249 */     $$2.startConfiguration();
/* 250 */     this.state = State.ACCEPTED;
/*     */   }
/*     */ 
/*     */   
/*     */   public void fillListenerSpecificCrashDetails(CrashReportCategory $$0) {
/* 255 */     $$0.setDetail("Login phase", () -> this.state.toString());
/*     */   }
/*     */   
/*     */   private enum State {
/* 259 */     HELLO,
/* 260 */     KEY,
/* 261 */     AUTHENTICATING,
/* 262 */     NEGOTIATING,
/* 263 */     VERIFYING,
/* 264 */     WAITING_FOR_DUPE_DISCONNECT,
/* 265 */     PROTOCOL_SWITCHING,
/* 266 */     ACCEPTED;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\network\ServerLoginPacketListenerImpl.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */