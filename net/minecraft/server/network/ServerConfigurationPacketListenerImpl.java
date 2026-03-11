/*     */ package net.minecraft.server.network;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.util.Queue;
/*     */ import java.util.concurrent.ConcurrentLinkedQueue;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.LayeredRegistryAccess;
/*     */ import net.minecraft.core.RegistryAccess;
/*     */ import net.minecraft.core.RegistrySynchronization;
/*     */ import net.minecraft.network.Connection;
/*     */ import net.minecraft.network.PacketListener;
/*     */ import net.minecraft.network.TickablePacketListener;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.network.protocol.PacketUtils;
/*     */ import net.minecraft.network.protocol.common.ClientboundUpdateTagsPacket;
/*     */ import net.minecraft.network.protocol.common.ServerboundClientInformationPacket;
/*     */ import net.minecraft.network.protocol.common.ServerboundResourcePackPacket;
/*     */ import net.minecraft.network.protocol.common.custom.BrandPayload;
/*     */ import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
/*     */ import net.minecraft.network.protocol.configuration.ClientboundRegistryDataPacket;
/*     */ import net.minecraft.network.protocol.configuration.ClientboundUpdateEnabledFeaturesPacket;
/*     */ import net.minecraft.network.protocol.configuration.ServerConfigurationPacketListener;
/*     */ import net.minecraft.network.protocol.configuration.ServerboundFinishConfigurationPacket;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.server.RegistryLayer;
/*     */ import net.minecraft.server.level.ClientInformation;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.server.network.config.JoinWorldTask;
/*     */ import net.minecraft.server.network.config.ServerResourcePackConfigurationTask;
/*     */ import net.minecraft.server.players.PlayerList;
/*     */ import net.minecraft.tags.TagNetworkSerialization;
/*     */ import net.minecraft.util.thread.BlockableEventLoop;
/*     */ import net.minecraft.world.flag.FeatureFlags;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class ServerConfigurationPacketListenerImpl extends ServerCommonPacketListenerImpl implements TickablePacketListener, ServerConfigurationPacketListener {
/*  38 */   private static final Logger LOGGER = LogUtils.getLogger();
/*  39 */   private static final Component DISCONNECT_REASON_INVALID_DATA = (Component)Component.translatable("multiplayer.disconnect.invalid_player_data");
/*     */   
/*     */   private final GameProfile gameProfile;
/*  42 */   private final Queue<ConfigurationTask> configurationTasks = new ConcurrentLinkedQueue<>();
/*     */   @Nullable
/*     */   private ConfigurationTask currentTask;
/*     */   private ClientInformation clientInformation;
/*     */   
/*     */   public ServerConfigurationPacketListenerImpl(MinecraftServer $$0, Connection $$1, CommonListenerCookie $$2) {
/*  48 */     super($$0, $$1, $$2);
/*  49 */     this.gameProfile = $$2.gameProfile();
/*  50 */     this.clientInformation = $$2.clientInformation();
/*     */   }
/*     */ 
/*     */   
/*     */   protected GameProfile playerProfile() {
/*  55 */     return this.gameProfile;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisconnect(Component $$0) {
/*  60 */     LOGGER.info("{} lost connection: {}", this.gameProfile, $$0.getString());
/*  61 */     super.onDisconnect($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAcceptingMessages() {
/*  66 */     return this.connection.isConnected();
/*     */   }
/*     */   
/*     */   public void startConfiguration() {
/*  70 */     send((Packet<?>)new ClientboundCustomPayloadPacket((CustomPacketPayload)new BrandPayload(this.server.getServerModName())));
/*  71 */     LayeredRegistryAccess<RegistryLayer> $$0 = this.server.registries();
/*  72 */     send((Packet<?>)new ClientboundUpdateEnabledFeaturesPacket(FeatureFlags.REGISTRY.toNames(this.server.getWorldData().enabledFeatures())));
/*  73 */     send((Packet<?>)new ClientboundRegistryDataPacket((new RegistryAccess.ImmutableRegistryAccess(RegistrySynchronization.networkedRegistries($$0))).freeze()));
/*  74 */     send((Packet<?>)new ClientboundUpdateTagsPacket(TagNetworkSerialization.serializeTagsToNetwork($$0)));
/*  75 */     addOptionalTasks();
/*     */ 
/*     */     
/*  78 */     this.configurationTasks.add(new JoinWorldTask());
/*  79 */     startNextTask();
/*     */   }
/*     */   
/*     */   public void returnToWorld() {
/*  83 */     this.configurationTasks.add(new JoinWorldTask());
/*  84 */     startNextTask();
/*     */   }
/*     */   
/*     */   private void addOptionalTasks() {
/*  88 */     this.server.getServerResourcePack().ifPresent($$0 -> this.configurationTasks.add(new ServerResourcePackConfigurationTask($$0)));
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleClientInformation(ServerboundClientInformationPacket $$0) {
/*  93 */     this.clientInformation = $$0.information();
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleResourcePackResponse(ServerboundResourcePackPacket $$0) {
/*  98 */     super.handleResourcePackResponse($$0);
/*     */     
/* 100 */     if ($$0.action().isTerminal()) {
/* 101 */       finishCurrentTask(ServerResourcePackConfigurationTask.TYPE);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleConfigurationFinished(ServerboundFinishConfigurationPacket $$0) {
/* 107 */     this.connection.suspendInboundAfterProtocolChange();
/* 108 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.server);
/* 109 */     finishCurrentTask(JoinWorldTask.TYPE);
/*     */ 
/*     */     
/*     */     try {
/* 113 */       PlayerList $$1 = this.server.getPlayerList();
/* 114 */       if ($$1.getPlayer(this.gameProfile.getId()) != null) {
/*     */ 
/*     */         
/* 117 */         disconnect(PlayerList.DUPLICATE_LOGIN_DISCONNECT_MESSAGE);
/*     */         return;
/*     */       } 
/* 120 */       Component $$2 = $$1.canPlayerLogin(this.connection.getRemoteAddress(), this.gameProfile);
/* 121 */       if ($$2 != null) {
/* 122 */         disconnect($$2);
/*     */         
/*     */         return;
/*     */       } 
/* 126 */       ServerPlayer $$3 = $$1.getPlayerForLogin(this.gameProfile, this.clientInformation);
/*     */       
/* 128 */       $$1.placeNewPlayer(this.connection, $$3, createCookie(this.clientInformation));
/* 129 */       this.connection.resumeInboundAfterProtocolChange();
/* 130 */     } catch (Exception $$4) {
/* 131 */       LOGGER.error("Couldn't place player in world", $$4);
/* 132 */       this.connection.send((Packet)new ClientboundDisconnectPacket(DISCONNECT_REASON_INVALID_DATA));
/* 133 */       this.connection.disconnect(DISCONNECT_REASON_INVALID_DATA);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 139 */     keepConnectionAlive();
/*     */   }
/*     */   
/*     */   private void startNextTask() {
/* 143 */     if (this.currentTask != null) {
/* 144 */       throw new IllegalStateException("Task " + this.currentTask.type().id() + " has not finished yet");
/*     */     }
/*     */     
/* 147 */     if (!isAcceptingMessages()) {
/*     */       return;
/*     */     }
/*     */     
/* 151 */     ConfigurationTask $$0 = this.configurationTasks.poll();
/* 152 */     if ($$0 != null) {
/* 153 */       this.currentTask = $$0;
/* 154 */       $$0.start(this::send);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void finishCurrentTask(ConfigurationTask.Type $$0) {
/* 159 */     ConfigurationTask.Type $$1 = (this.currentTask != null) ? this.currentTask.type() : null;
/* 160 */     if (!$$0.equals($$1)) {
/* 161 */       throw new IllegalStateException("Unexpected request for task finish, current task: " + $$1 + ", requested: " + $$0);
/*     */     }
/* 163 */     this.currentTask = null;
/* 164 */     startNextTask();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\network\ServerConfigurationPacketListenerImpl.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */