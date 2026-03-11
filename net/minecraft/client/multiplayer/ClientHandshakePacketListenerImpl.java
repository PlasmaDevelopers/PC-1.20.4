/*     */ package net.minecraft.client.multiplayer;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.authlib.exceptions.AuthenticationException;
/*     */ import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
/*     */ import com.mojang.authlib.exceptions.InsufficientPrivilegesException;
/*     */ import com.mojang.authlib.exceptions.InvalidCredentialsException;
/*     */ import com.mojang.authlib.exceptions.UserBannedException;
/*     */ import com.mojang.authlib.minecraft.MinecraftSessionService;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.math.BigInteger;
/*     */ import java.security.PublicKey;
/*     */ import java.time.Duration;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.atomic.AtomicReference;
/*     */ import java.util.function.Consumer;
/*     */ import javax.annotation.Nullable;
/*     */ import javax.crypto.Cipher;
/*     */ import javax.crypto.SecretKey;
/*     */ import net.minecraft.CrashReportCategory;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.client.ClientBrandRetriever;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.screens.DisconnectedScreen;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.network.Connection;
/*     */ import net.minecraft.network.PacketListener;
/*     */ import net.minecraft.network.PacketSendListener;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.network.protocol.common.ServerboundClientInformationPacket;
/*     */ import net.minecraft.network.protocol.common.ServerboundCustomPayloadPacket;
/*     */ import net.minecraft.network.protocol.common.custom.BrandPayload;
/*     */ import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
/*     */ import net.minecraft.network.protocol.login.ClientLoginPacketListener;
/*     */ import net.minecraft.network.protocol.login.ClientboundCustomQueryPacket;
/*     */ import net.minecraft.network.protocol.login.ClientboundGameProfilePacket;
/*     */ import net.minecraft.network.protocol.login.ClientboundHelloPacket;
/*     */ import net.minecraft.network.protocol.login.ClientboundLoginCompressionPacket;
/*     */ import net.minecraft.network.protocol.login.ClientboundLoginDisconnectPacket;
/*     */ import net.minecraft.network.protocol.login.ServerboundCustomQueryAnswerPacket;
/*     */ import net.minecraft.network.protocol.login.ServerboundKeyPacket;
/*     */ import net.minecraft.network.protocol.login.ServerboundLoginAcknowledgedPacket;
/*     */ import net.minecraft.realms.DisconnectedRealmsScreen;
/*     */ import net.minecraft.util.Crypt;
/*     */ import net.minecraft.world.flag.FeatureFlags;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class ClientHandshakePacketListenerImpl implements ClientLoginPacketListener {
/*  50 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private final Minecraft minecraft;
/*     */   
/*     */   @Nullable
/*     */   private final ServerData serverData;
/*     */   
/*     */   @Nullable
/*     */   private final Screen parent;
/*     */   private final Consumer<Component> updateStatus;
/*     */   private final Connection connection;
/*     */   private final boolean newWorld;
/*     */   @Nullable
/*     */   private final Duration worldLoadDuration;
/*     */   @Nullable
/*     */   private String minigameName;
/*  66 */   private final AtomicReference<State> state = new AtomicReference<>(State.CONNECTING);
/*     */   
/*     */   public ClientHandshakePacketListenerImpl(Connection $$0, Minecraft $$1, @Nullable ServerData $$2, @Nullable Screen $$3, boolean $$4, @Nullable Duration $$5, Consumer<Component> $$6) {
/*  69 */     this.connection = $$0;
/*  70 */     this.minecraft = $$1;
/*  71 */     this.serverData = $$2;
/*  72 */     this.parent = $$3;
/*  73 */     this.updateStatus = $$6;
/*  74 */     this.newWorld = $$4;
/*  75 */     this.worldLoadDuration = $$5;
/*     */   }
/*     */   
/*     */   private void switchState(State $$0) {
/*  79 */     State $$1 = this.state.updateAndGet($$1 -> {
/*     */           if (!$$0.fromStates.contains($$1)) {
/*     */             throw new IllegalStateException("Tried to switch to " + $$0 + " from " + $$1 + ", but expected one of " + $$0.fromStates);
/*     */           }
/*     */           return $$0;
/*     */         });
/*  85 */     this.updateStatus.accept($$1.message);
/*     */   } public void handleHello(ClientboundHelloPacket $$0) {
/*     */     Cipher $$4, $$5;
/*     */     String $$3;
/*     */     ServerboundKeyPacket $$7;
/*  90 */     switchState(State.AUTHORIZING);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  98 */       SecretKey $$1 = Crypt.generateSecretKey();
/*  99 */       PublicKey $$2 = $$0.getPublicKey();
/*     */       
/* 101 */       $$3 = (new BigInteger(Crypt.digestData($$0.getServerId(), $$2, $$1))).toString(16);
/*     */       
/* 103 */       $$4 = Crypt.getCipher(2, $$1);
/* 104 */       $$5 = Crypt.getCipher(1, $$1);
/*     */       
/* 106 */       byte[] $$6 = $$0.getChallenge();
/* 107 */       $$7 = new ServerboundKeyPacket($$1, $$2, $$6);
/* 108 */     } catch (Exception $$8) {
/* 109 */       throw new IllegalStateException("Protocol error", $$8);
/*     */     } 
/*     */     
/* 112 */     Util.ioPool().submit(() -> {
/*     */           Component $$4 = authenticateServer($$0);
/*     */           if ($$4 != null) {
/*     */             if (this.serverData != null && this.serverData.isLan()) {
/*     */               LOGGER.warn($$4.getString());
/*     */             } else {
/*     */               this.connection.disconnect($$4);
/*     */               return;
/*     */             } 
/*     */           }
/*     */           switchState(State.ENCRYPTING);
/*     */           this.connection.send((Packet)$$1, PacketSendListener.thenRun(()));
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private Component authenticateServer(String $$0) {
/*     */     try {
/* 131 */       getMinecraftSessionService().joinServer(this.minecraft.getUser().getProfileId(), this.minecraft.getUser().getAccessToken(), $$0);
/* 132 */     } catch (AuthenticationUnavailableException $$1) {
/* 133 */       return (Component)Component.translatable("disconnect.loginFailedInfo", new Object[] { Component.translatable("disconnect.loginFailedInfo.serversUnavailable") });
/* 134 */     } catch (InvalidCredentialsException $$2) {
/* 135 */       return (Component)Component.translatable("disconnect.loginFailedInfo", new Object[] { Component.translatable("disconnect.loginFailedInfo.invalidSession") });
/* 136 */     } catch (InsufficientPrivilegesException $$3) {
/* 137 */       return (Component)Component.translatable("disconnect.loginFailedInfo", new Object[] { Component.translatable("disconnect.loginFailedInfo.insufficientPrivileges") });
/* 138 */     } catch (UserBannedException|com.mojang.authlib.exceptions.ForcedUsernameChangeException $$4) {
/* 139 */       return (Component)Component.translatable("disconnect.loginFailedInfo", new Object[] { Component.translatable("disconnect.loginFailedInfo.userBanned") });
/* 140 */     } catch (AuthenticationException $$5) {
/* 141 */       return (Component)Component.translatable("disconnect.loginFailedInfo", new Object[] { $$5.getMessage() });
/*     */     } 
/*     */     
/* 144 */     return null;
/*     */   }
/*     */   
/*     */   private MinecraftSessionService getMinecraftSessionService() {
/* 148 */     return this.minecraft.getMinecraftSessionService();
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleGameProfile(ClientboundGameProfilePacket $$0) {
/* 153 */     switchState(State.JOINING);
/*     */     
/* 155 */     GameProfile $$1 = $$0.getGameProfile();
/* 156 */     this.connection.send((Packet)new ServerboundLoginAcknowledgedPacket());
/* 157 */     this.connection.setListener((PacketListener)new ClientConfigurationPacketListenerImpl(this.minecraft, this.connection, new CommonListenerCookie($$1, this.minecraft
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 162 */             .getTelemetryManager().createWorldSessionManager(this.newWorld, this.worldLoadDuration, this.minigameName), 
/* 163 */             ClientRegistryLayer.createRegistryAccess().compositeAccess(), FeatureFlags.DEFAULT_FLAGS, null, this.serverData, this.parent)));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 170 */     this.connection.send((Packet)new ServerboundCustomPayloadPacket((CustomPacketPayload)new BrandPayload(ClientBrandRetriever.getClientModName())));
/* 171 */     this.connection.send((Packet)new ServerboundClientInformationPacket(this.minecraft.options.buildPlayerInformation()));
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisconnect(Component $$0) {
/* 176 */     if (this.serverData != null && this.serverData.isRealm()) {
/* 177 */       this.minecraft.setScreen((Screen)new DisconnectedRealmsScreen(this.parent, CommonComponents.CONNECT_FAILED, $$0));
/*     */     } else {
/* 179 */       this.minecraft.setScreen((Screen)new DisconnectedScreen(this.parent, CommonComponents.CONNECT_FAILED, $$0));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAcceptingMessages() {
/* 185 */     return this.connection.isConnected();
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleDisconnect(ClientboundLoginDisconnectPacket $$0) {
/* 190 */     this.connection.disconnect($$0.getReason());
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleCompression(ClientboundLoginCompressionPacket $$0) {
/* 195 */     if (!this.connection.isMemoryConnection())
/*     */     {
/* 197 */       this.connection.setupCompression($$0.getCompressionThreshold(), false);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleCustomQuery(ClientboundCustomQueryPacket $$0) {
/* 203 */     this.updateStatus.accept(Component.translatable("connect.negotiating"));
/* 204 */     this.connection.send((Packet)new ServerboundCustomQueryAnswerPacket($$0.transactionId(), null));
/*     */   }
/*     */   
/*     */   public void setMinigameName(String $$0) {
/* 208 */     this.minigameName = $$0;
/*     */   }
/*     */   
/*     */   private enum State {
/* 212 */     CONNECTING((String)Component.translatable("connect.connecting"), Set.of()),
/* 213 */     AUTHORIZING((String)Component.translatable("connect.authorizing"), Set.of(CONNECTING)),
/* 214 */     ENCRYPTING((String)Component.translatable("connect.encrypting"), Set.of(AUTHORIZING)),
/*     */     
/* 216 */     JOINING((String)Component.translatable("connect.joining"), Set.of(ENCRYPTING, CONNECTING));
/*     */     
/*     */     final Component message;
/*     */     
/*     */     final Set<State> fromStates;
/*     */     
/*     */     State(Component $$0, Set<State> $$1) {
/* 223 */       this.message = $$0;
/* 224 */       this.fromStates = $$1;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void fillListenerSpecificCrashDetails(CrashReportCategory $$0) {
/* 230 */     $$0.setDetail("Server type", () -> (this.serverData != null) ? this.serverData.type().toString() : "<unknown>");
/* 231 */     $$0.setDetail("Login phase", () -> ((State)this.state.get()).toString());
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\multiplayer\ClientHandshakePacketListenerImpl.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */