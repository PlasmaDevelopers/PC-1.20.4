/*     */ package net.minecraft.client.multiplayer;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.core.RegistryAccess;
/*     */ import net.minecraft.network.Connection;
/*     */ import net.minecraft.network.PacketListener;
/*     */ import net.minecraft.network.TickablePacketListener;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.network.protocol.PacketUtils;
/*     */ import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
/*     */ import net.minecraft.network.protocol.configuration.ClientboundFinishConfigurationPacket;
/*     */ import net.minecraft.network.protocol.configuration.ClientboundRegistryDataPacket;
/*     */ import net.minecraft.network.protocol.configuration.ClientboundUpdateEnabledFeaturesPacket;
/*     */ import net.minecraft.network.protocol.configuration.ServerboundFinishConfigurationPacket;
/*     */ import net.minecraft.util.thread.BlockableEventLoop;
/*     */ import net.minecraft.world.flag.FeatureFlagSet;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class ClientConfigurationPacketListenerImpl extends ClientCommonPacketListenerImpl implements TickablePacketListener, ClientConfigurationPacketListener {
/*  22 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private final GameProfile localGameProfile;
/*     */   private RegistryAccess.Frozen receivedRegistries;
/*     */   private FeatureFlagSet enabledFeatures;
/*     */   
/*     */   public ClientConfigurationPacketListenerImpl(Minecraft $$0, Connection $$1, CommonListenerCookie $$2) {
/*  29 */     super($$0, $$1, $$2);
/*  30 */     this.localGameProfile = $$2.localGameProfile();
/*  31 */     this.receivedRegistries = $$2.receivedRegistries();
/*  32 */     this.enabledFeatures = $$2.enabledFeatures();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAcceptingMessages() {
/*  37 */     return this.connection.isConnected();
/*     */   }
/*     */ 
/*     */   
/*     */   protected RegistryAccess.Frozen registryAccess() {
/*  42 */     return this.receivedRegistries;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void handleCustomPayload(CustomPacketPayload $$0) {
/*  47 */     handleUnknownCustomPayload($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   private void handleUnknownCustomPayload(CustomPacketPayload $$0) {
/*  52 */     LOGGER.warn("Unknown custom packet payload: {}", $$0.id());
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleRegistryData(ClientboundRegistryDataPacket $$0) {
/*  57 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/*     */ 
/*     */ 
/*     */     
/*  61 */     RegistryAccess.Frozen $$1 = ClientRegistryLayer.createRegistryAccess().replaceFrom(ClientRegistryLayer.REMOTE, new RegistryAccess.Frozen[] { $$0.registryHolder() }).compositeAccess();
/*     */     
/*  63 */     if (!this.connection.isMemoryConnection())
/*     */     {
/*  65 */       $$1.registries().forEach($$0 -> $$0.value().resetTags());
/*     */     }
/*     */     
/*  68 */     this.receivedRegistries = $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleEnabledFeatures(ClientboundUpdateEnabledFeaturesPacket $$0) {
/*  73 */     this.enabledFeatures = FeatureFlags.REGISTRY.fromNames($$0.features());
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleConfigurationFinished(ClientboundFinishConfigurationPacket $$0) {
/*  78 */     this.connection.suspendInboundAfterProtocolChange();
/*     */     
/*  80 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/*     */     
/*  82 */     this.connection.setListener((PacketListener)new ClientPacketListener(this.minecraft, this.connection, new CommonListenerCookie(this.localGameProfile, this.telemetryManager, this.receivedRegistries, this.enabledFeatures, this.serverBrand, this.serverData, this.postDisconnectScreen)));
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
/*  95 */     this.connection.resumeInboundAfterProtocolChange();
/*  96 */     this.connection.send((Packet)new ServerboundFinishConfigurationPacket());
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 101 */     sendDeferredPackets();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisconnect(Component $$0) {
/* 106 */     super.onDisconnect($$0);
/*     */     
/* 108 */     this.minecraft.clearDownloadedResourcePacks();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\multiplayer\ClientConfigurationPacketListenerImpl.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */