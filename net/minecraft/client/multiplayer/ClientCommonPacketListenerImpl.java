/*     */ package net.minecraft.client.multiplayer;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.time.Duration;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.UUID;
/*     */ import java.util.function.BooleanSupplier;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.CrashReportCategory;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.screens.ConfirmScreen;
/*     */ import net.minecraft.client.gui.screens.DisconnectedScreen;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.client.gui.screens.TitleScreen;
/*     */ import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
/*     */ import net.minecraft.client.resources.server.DownloadedPackSource;
/*     */ import net.minecraft.client.telemetry.WorldSessionTelemetryManager;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.Registry;
/*     */ import net.minecraft.core.RegistryAccess;
/*     */ import net.minecraft.network.Connection;
/*     */ import net.minecraft.network.PacketListener;
/*     */ import net.minecraft.network.ServerboundPacketListener;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.network.protocol.PacketUtils;
/*     */ import net.minecraft.network.protocol.common.ClientCommonPacketListener;
/*     */ import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
/*     */ import net.minecraft.network.protocol.common.ClientboundDisconnectPacket;
/*     */ import net.minecraft.network.protocol.common.ClientboundKeepAlivePacket;
/*     */ import net.minecraft.network.protocol.common.ClientboundPingPacket;
/*     */ import net.minecraft.network.protocol.common.ClientboundResourcePackPopPacket;
/*     */ import net.minecraft.network.protocol.common.ClientboundResourcePackPushPacket;
/*     */ import net.minecraft.network.protocol.common.ClientboundUpdateTagsPacket;
/*     */ import net.minecraft.network.protocol.common.ServerboundKeepAlivePacket;
/*     */ import net.minecraft.network.protocol.common.ServerboundPongPacket;
/*     */ import net.minecraft.network.protocol.common.ServerboundResourcePackPacket;
/*     */ import net.minecraft.network.protocol.common.custom.BrandPayload;
/*     */ import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
/*     */ import net.minecraft.realms.DisconnectedRealmsScreen;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.tags.TagKey;
/*     */ import net.minecraft.tags.TagNetworkSerialization;
/*     */ import net.minecraft.util.thread.BlockableEventLoop;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public abstract class ClientCommonPacketListenerImpl
/*     */   implements ClientCommonPacketListener {
/*  61 */   private static final Component GENERIC_DISCONNECT_MESSAGE = (Component)Component.translatable("disconnect.lost");
/*     */   
/*  63 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   protected final Minecraft minecraft;
/*     */   
/*     */   protected final Connection connection;
/*     */   @Nullable
/*     */   protected final ServerData serverData;
/*     */   @Nullable
/*     */   protected String serverBrand;
/*     */   protected final WorldSessionTelemetryManager telemetryManager;
/*     */   @Nullable
/*     */   protected final Screen postDisconnectScreen;
/*  75 */   private final List<DeferredPacket> deferredPackets = new ArrayList<>();
/*     */   
/*     */   protected ClientCommonPacketListenerImpl(Minecraft $$0, Connection $$1, CommonListenerCookie $$2) {
/*  78 */     this.minecraft = $$0;
/*  79 */     this.connection = $$1;
/*  80 */     this.serverData = $$2.serverData();
/*  81 */     this.serverBrand = $$2.serverBrand();
/*  82 */     this.telemetryManager = $$2.telemetryManager();
/*  83 */     this.postDisconnectScreen = $$2.postDisconnectScreen();
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleKeepAlive(ClientboundKeepAlivePacket $$0) {
/*  88 */     sendWhen((Packet<? extends ServerboundPacketListener>)new ServerboundKeepAlivePacket($$0.getId()), () -> !RenderSystem.isFrozenAtPollEvents(), Duration.ofMinutes(1L));
/*     */   }
/*     */ 
/*     */   
/*     */   public void handlePing(ClientboundPingPacket $$0) {
/*  93 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/*  94 */     send((Packet<?>)new ServerboundPongPacket($$0.getId()));
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleCustomPayload(ClientboundCustomPayloadPacket $$0) {
/*  99 */     CustomPacketPayload $$1 = $$0.payload();
/* 100 */     if ($$1 instanceof net.minecraft.network.protocol.common.custom.DiscardedPayload) {
/*     */       return;
/*     */     }
/*     */     
/* 104 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/* 105 */     if ($$1 instanceof BrandPayload) { BrandPayload $$2 = (BrandPayload)$$1;
/* 106 */       this.serverBrand = $$2.brand();
/* 107 */       this.telemetryManager.onServerBrandReceived($$2.brand()); }
/*     */     else
/* 109 */     { handleCustomPayload($$1); }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleResourcePackPush(ClientboundResourcePackPushPacket $$0) {
/* 119 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/* 120 */     UUID $$1 = $$0.id();
/* 121 */     URL $$2 = parseResourcePackUrl($$0.url());
/* 122 */     if ($$2 == null) {
/* 123 */       this.connection.send((Packet)new ServerboundResourcePackPacket($$1, ServerboundResourcePackPacket.Action.INVALID_URL));
/*     */       
/*     */       return;
/*     */     } 
/* 127 */     String $$3 = $$0.hash();
/* 128 */     boolean $$4 = $$0.required();
/*     */     
/* 130 */     ServerData.ServerPackStatus $$5 = (this.serverData != null) ? this.serverData.getResourcePackStatus() : ServerData.ServerPackStatus.PROMPT;
/* 131 */     if ($$5 == ServerData.ServerPackStatus.PROMPT || ($$4 && $$5 == ServerData.ServerPackStatus.DISABLED)) {
/*     */       
/* 133 */       this.minecraft.setScreen(addOrUpdatePackPrompt($$1, $$2, $$3, $$4, $$0.prompt()));
/*     */     } else {
/*     */       
/* 136 */       this.minecraft.getDownloadedPackSource().pushPack($$1, $$2, $$3);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleResourcePackPop(ClientboundResourcePackPopPacket $$0) {
/* 142 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/* 143 */     $$0.id().ifPresentOrElse($$0 -> this.minecraft.getDownloadedPackSource().popPack($$0), () -> this.minecraft.getDownloadedPackSource().popAll());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Component preparePackPrompt(Component $$0, @Nullable Component $$1) {
/* 150 */     if ($$1 == null) {
/* 151 */       return $$0;
/*     */     }
/* 153 */     return (Component)Component.translatable("multiplayer.texturePrompt.serverPrompt", new Object[] { $$0, $$1 });
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private static URL parseResourcePackUrl(String $$0) {
/*     */     try {
/* 160 */       URL $$1 = new URL($$0);
/* 161 */       String $$2 = $$1.getProtocol();
/* 162 */       if ("http".equals($$2) || "https".equals($$2)) {
/* 163 */         return $$1;
/*     */       }
/* 165 */     } catch (MalformedURLException $$3) {
/* 166 */       return null;
/*     */     } 
/* 168 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleUpdateTags(ClientboundUpdateTagsPacket $$0) {
/* 173 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/* 174 */     $$0.getTags().forEach(this::updateTagsForRegistry);
/*     */   }
/*     */   
/*     */   private <T> void updateTagsForRegistry(ResourceKey<? extends Registry<? extends T>> $$0, TagNetworkSerialization.NetworkPayload $$1) {
/* 178 */     if ($$1.isEmpty()) {
/*     */       return;
/*     */     }
/* 181 */     Registry<T> $$2 = (Registry<T>)registryAccess().registry($$0).orElseThrow(() -> new IllegalStateException("Unknown registry " + $$0));
/* 182 */     ResourceKey<? extends Registry<? extends T>> resourceKey = $$0;
/*     */     
/* 184 */     Map<TagKey<T>, List<Holder<T>>> $$4 = new HashMap<>();
/* 185 */     Objects.requireNonNull($$4); TagNetworkSerialization.deserializeTagsFromNetwork(resourceKey, $$2, $$1, $$4::put);
/* 186 */     $$2.bindTags($$4);
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleDisconnect(ClientboundDisconnectPacket $$0) {
/* 191 */     this.connection.disconnect($$0.getReason());
/*     */   }
/*     */   
/*     */   protected void sendDeferredPackets() {
/* 195 */     Iterator<DeferredPacket> $$0 = this.deferredPackets.iterator();
/* 196 */     while ($$0.hasNext()) {
/* 197 */       DeferredPacket $$1 = $$0.next();
/* 198 */       if ($$1.sendCondition().getAsBoolean()) {
/* 199 */         send($$1.packet);
/* 200 */         $$0.remove(); continue;
/* 201 */       }  if ($$1.expirationTime() <= Util.getMillis()) {
/* 202 */         $$0.remove();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void send(Packet<?> $$0) {
/* 208 */     this.connection.send($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisconnect(Component $$0) {
/* 213 */     this.telemetryManager.onDisconnect();
/* 214 */     this.minecraft.disconnect(createDisconnectScreen($$0));
/* 215 */     LOGGER.warn("Client disconnected with reason: {}", $$0.getString());
/*     */   }
/*     */ 
/*     */   
/*     */   public void fillListenerSpecificCrashDetails(CrashReportCategory $$0) {
/* 220 */     $$0.setDetail("Server type", () -> (this.serverData != null) ? this.serverData.type().toString() : "<none>");
/* 221 */     $$0.setDetail("Server brand", () -> this.serverBrand);
/*     */   }
/*     */   
/*     */   protected Screen createDisconnectScreen(Component $$0) {
/* 225 */     Screen $$1 = Objects.<Screen>requireNonNullElseGet(this.postDisconnectScreen, () -> new JoinMultiplayerScreen((Screen)new TitleScreen()));
/* 226 */     if (this.serverData != null && this.serverData.isRealm()) {
/* 227 */       return (Screen)new DisconnectedRealmsScreen($$1, GENERIC_DISCONNECT_MESSAGE, $$0);
/*     */     }
/* 229 */     return (Screen)new DisconnectedScreen($$1, GENERIC_DISCONNECT_MESSAGE, $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String serverBrand() {
/* 235 */     return this.serverBrand;
/*     */   }
/*     */   
/*     */   private void sendWhen(Packet<? extends ServerboundPacketListener> $$0, BooleanSupplier $$1, Duration $$2) {
/* 239 */     if ($$1.getAsBoolean()) {
/* 240 */       send($$0);
/*     */     } else {
/* 242 */       this.deferredPackets.add(new DeferredPacket($$0, $$1, Util.getMillis() + $$2.toMillis()));
/*     */     } 
/*     */   }
/*     */   private static final class DeferredPacket extends Record { final Packet<? extends ServerboundPacketListener> packet; private final BooleanSupplier sendCondition; private final long expirationTime;
/* 246 */     DeferredPacket(Packet<? extends ServerboundPacketListener> $$0, BooleanSupplier $$1, long $$2) { this.packet = $$0; this.sendCondition = $$1; this.expirationTime = $$2; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/client/multiplayer/ClientCommonPacketListenerImpl$DeferredPacket;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #246	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 246 */       //   0	7	0	this	Lnet/minecraft/client/multiplayer/ClientCommonPacketListenerImpl$DeferredPacket; } public Packet<? extends ServerboundPacketListener> packet() { return this.packet; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/multiplayer/ClientCommonPacketListenerImpl$DeferredPacket;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #246	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/multiplayer/ClientCommonPacketListenerImpl$DeferredPacket; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/client/multiplayer/ClientCommonPacketListenerImpl$DeferredPacket;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #246	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/client/multiplayer/ClientCommonPacketListenerImpl$DeferredPacket;
/* 246 */       //   0	8	1	$$0	Ljava/lang/Object; } public BooleanSupplier sendCondition() { return this.sendCondition; } public long expirationTime() { return this.expirationTime; }
/*     */      }
/*     */ 
/*     */   
/*     */   private Screen addOrUpdatePackPrompt(UUID $$0, URL $$1, String $$2, boolean $$3, @Nullable Component $$4) {
/* 251 */     Screen $$5 = this.minecraft.screen;
/* 252 */     if ($$5 instanceof PackConfirmScreen) { PackConfirmScreen $$6 = (PackConfirmScreen)$$5;
/* 253 */       return (Screen)$$6.update(this.minecraft, $$0, $$1, $$2, $$3, $$4); }
/*     */     
/* 255 */     return (Screen)new PackConfirmScreen(this.minecraft, $$5, List.of(new PackConfirmScreen.PendingRequest($$0, $$1, $$2)), $$3, $$4);
/*     */   } protected abstract void handleCustomPayload(CustomPacketPayload paramCustomPacketPayload); protected abstract RegistryAccess.Frozen registryAccess(); private class PackConfirmScreen extends ConfirmScreen { private final List<PendingRequest> requests; @Nullable
/*     */     private final Screen parentScreen;
/*     */     private static final class PendingRequest extends Record { final UUID id; final URL url; final String hash;
/* 259 */       PendingRequest(UUID $$0, URL $$1, String $$2) { this.id = $$0; this.url = $$1; this.hash = $$2; } public final String toString() { // Byte code:
/*     */         //   0: aload_0
/*     */         //   1: <illegal opcode> toString : (Lnet/minecraft/client/multiplayer/ClientCommonPacketListenerImpl$PackConfirmScreen$PendingRequest;)Ljava/lang/String;
/*     */         //   6: areturn
/*     */         // Line number table:
/*     */         //   Java source line number -> byte code offset
/*     */         //   #259	-> 0
/*     */         // Local variable table:
/*     */         //   start	length	slot	name	descriptor
/*     */         //   0	7	0	this	Lnet/minecraft/client/multiplayer/ClientCommonPacketListenerImpl$PackConfirmScreen$PendingRequest; } public final int hashCode() { // Byte code:
/*     */         //   0: aload_0
/*     */         //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/multiplayer/ClientCommonPacketListenerImpl$PackConfirmScreen$PendingRequest;)I
/*     */         //   6: ireturn
/*     */         // Line number table:
/*     */         //   Java source line number -> byte code offset
/*     */         //   #259	-> 0
/*     */         // Local variable table:
/*     */         //   start	length	slot	name	descriptor
/*     */         //   0	7	0	this	Lnet/minecraft/client/multiplayer/ClientCommonPacketListenerImpl$PackConfirmScreen$PendingRequest; } public final boolean equals(Object $$0) { // Byte code:
/*     */         //   0: aload_0
/*     */         //   1: aload_1
/*     */         //   2: <illegal opcode> equals : (Lnet/minecraft/client/multiplayer/ClientCommonPacketListenerImpl$PackConfirmScreen$PendingRequest;Ljava/lang/Object;)Z
/*     */         //   7: ireturn
/*     */         // Line number table:
/*     */         //   Java source line number -> byte code offset
/*     */         //   #259	-> 0
/*     */         // Local variable table:
/*     */         //   start	length	slot	name	descriptor
/*     */         //   0	8	0	this	Lnet/minecraft/client/multiplayer/ClientCommonPacketListenerImpl$PackConfirmScreen$PendingRequest;
/* 259 */         //   0	8	1	$$0	Ljava/lang/Object; } public UUID id() { return this.id; } public URL url() { return this.url; } public String hash() { return this.hash; }
/*     */        }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     PackConfirmScreen(@Nullable Minecraft $$0, Screen $$1, List<PendingRequest> $$2, @Nullable boolean $$3, Component $$4) {
/* 267 */       super($$5 -> {
/*     */             $$0.setScreen($$1);
/*     */             
/*     */             DownloadedPackSource $$6 = $$0.getDownloadedPackSource();
/*     */             
/*     */             if ($$5) {
/*     */               if ($$4.serverData != null) {
/*     */                 $$4.serverData.setResourcePackStatus(ServerData.ServerPackStatus.ENABLED);
/*     */               }
/*     */               
/*     */               $$6.allowServerPacks();
/*     */             } else {
/*     */               $$6.rejectServerPacks();
/*     */               
/*     */               if ($$2) {
/*     */                 $$4.connection.disconnect((Component)Component.translatable("multiplayer.requiredTexturePrompt.disconnect"));
/*     */               } else if ($$4.serverData != null) {
/*     */                 $$4.serverData.setResourcePackStatus(ServerData.ServerPackStatus.DISABLED);
/*     */               } 
/*     */             } 
/*     */             
/*     */             for (PendingRequest $$7 : $$3) {
/*     */               $$6.pushPack($$7.id, $$7.url, $$7.hash);
/*     */             }
/*     */             if ($$4.serverData != null) {
/*     */               ServerList.saveSingleServer($$4.serverData);
/*     */             }
/* 294 */           }$$3 ? (Component)Component.translatable("multiplayer.requiredTexturePrompt.line1") : (Component)Component.translatable("multiplayer.texturePrompt.line1"), 
/* 295 */           ClientCommonPacketListenerImpl.preparePackPrompt($$3 ? (Component)Component.translatable("multiplayer.requiredTexturePrompt.line2").withStyle(new ChatFormatting[] { ChatFormatting.YELLOW, ChatFormatting.BOLD }, ) : (Component)Component.translatable("multiplayer.texturePrompt.line2"), $$4), 
/* 296 */           $$3 ? CommonComponents.GUI_PROCEED : CommonComponents.GUI_YES, 
/* 297 */           $$3 ? CommonComponents.GUI_DISCONNECT : CommonComponents.GUI_NO);
/*     */       
/* 299 */       this.requests = $$2;
/* 300 */       this.parentScreen = $$1;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public PackConfirmScreen update(Minecraft $$0, UUID $$1, URL $$2, String $$3, boolean $$4, @Nullable Component $$5) {
/* 307 */       ImmutableList immutableList = ImmutableList.builderWithExpectedSize(this.requests.size() + 1).addAll(this.requests).add(new PendingRequest($$1, $$2, $$3)).build();
/* 308 */       return new PackConfirmScreen($$0, this.parentScreen, (List<PendingRequest>)immutableList, $$4, $$5);
/*     */     } }
/*     */ 
/*     */   
/*     */   private static final class PendingRequest extends Record {
/*     */     final UUID id;
/*     */     final URL url;
/*     */     final String hash;
/*     */     
/*     */     PendingRequest(UUID $$0, URL $$1, String $$2) {
/*     */       this.id = $$0;
/*     */       this.url = $$1;
/*     */       this.hash = $$2;
/*     */     }
/*     */     
/*     */     public final String toString() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/client/multiplayer/ClientCommonPacketListenerImpl$PackConfirmScreen$PendingRequest;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #259	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/multiplayer/ClientCommonPacketListenerImpl$PackConfirmScreen$PendingRequest;
/*     */     }
/*     */     
/*     */     public final int hashCode() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/multiplayer/ClientCommonPacketListenerImpl$PackConfirmScreen$PendingRequest;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #259	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/multiplayer/ClientCommonPacketListenerImpl$PackConfirmScreen$PendingRequest;
/*     */     }
/*     */     
/*     */     public final boolean equals(Object $$0) {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/client/multiplayer/ClientCommonPacketListenerImpl$PackConfirmScreen$PendingRequest;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #259	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/client/multiplayer/ClientCommonPacketListenerImpl$PackConfirmScreen$PendingRequest;
/*     */       //   0	8	1	$$0	Ljava/lang/Object;
/*     */     }
/*     */     
/*     */     public UUID id() {
/*     */       return this.id;
/*     */     }
/*     */     
/*     */     public URL url() {
/*     */       return this.url;
/*     */     }
/*     */     
/*     */     public String hash() {
/*     */       return this.hash;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\multiplayer\ClientCommonPacketListenerImpl.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */