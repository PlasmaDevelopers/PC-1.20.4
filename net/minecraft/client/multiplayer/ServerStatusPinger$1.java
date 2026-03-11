/*     */ package net.minecraft.client.multiplayer;
/*     */ 
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.client.multiplayer.resolver.ServerAddress;
/*     */ import net.minecraft.network.Connection;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.network.protocol.status.ClientStatusPacketListener;
/*     */ import net.minecraft.network.protocol.status.ClientboundPongResponsePacket;
/*     */ import net.minecraft.network.protocol.status.ClientboundStatusResponsePacket;
/*     */ import net.minecraft.network.protocol.status.ServerStatus;
/*     */ import net.minecraft.network.protocol.status.ServerboundPingRequestPacket;
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
/*     */ class null
/*     */   implements ClientStatusPacketListener
/*     */ {
/*     */   private boolean success;
/*     */   private boolean receivedPing;
/*     */   private long pingStart;
/*     */   
/*     */   public void handleStatusResponse(ClientboundStatusResponsePacket $$0) {
/*  71 */     if (this.receivedPing) {
/*  72 */       connection.disconnect((Component)Component.translatable("multiplayer.status.unrequested"));
/*     */       return;
/*     */     } 
/*  75 */     this.receivedPing = true;
/*  76 */     ServerStatus $$1 = $$0.status();
/*  77 */     data.motd = $$1.description();
/*     */     
/*  79 */     $$1.version().ifPresentOrElse($$1 -> {
/*     */           $$0.version = (Component)Component.literal($$1.name());
/*     */           
/*     */           $$0.protocol = $$1.protocol();
/*     */         }() -> {
/*     */           $$0.version = (Component)Component.translatable("multiplayer.status.old");
/*     */           $$0.protocol = 0;
/*     */         });
/*  87 */     $$1.players().ifPresentOrElse($$1 -> {
/*     */           $$0.status = ServerStatusPinger.formatPlayerCount($$1.online(), $$1.max());
/*     */           
/*     */           $$0.players = $$1;
/*     */           
/*     */           if (!$$1.sample().isEmpty()) {
/*     */             List<Component> $$2 = new ArrayList<>($$1.sample().size());
/*     */             
/*     */             for (GameProfile $$3 : $$1.sample()) {
/*     */               $$2.add(Component.literal($$3.getName()));
/*     */             }
/*     */             
/*     */             if ($$1.sample().size() < $$1.online()) {
/*     */               $$2.add(Component.translatable("multiplayer.status.and_more", new Object[] { Integer.valueOf($$1.online() - $$1.sample().size()) }));
/*     */             }
/*     */             $$0.playerList = $$2;
/*     */           } else {
/*     */             $$0.playerList = List.of();
/*     */           } 
/*     */         }() -> $$0.status = (Component)Component.translatable("multiplayer.status.unknown").withStyle(ChatFormatting.DARK_GRAY));
/* 107 */     $$1.favicon().ifPresent($$2 -> {
/*     */           if (!Arrays.equals($$2.iconBytes(), $$0.getIconBytes())) {
/*     */             $$0.setIconBytes(ServerData.validateIcon($$2.iconBytes()));
/*     */             
/*     */             $$1.run();
/*     */           } 
/*     */         });
/* 114 */     this.pingStart = Util.getMillis();
/* 115 */     connection.send((Packet)new ServerboundPingRequestPacket(this.pingStart));
/* 116 */     this.success = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void handlePongResponse(ClientboundPongResponsePacket $$0) {
/* 121 */     long $$1 = this.pingStart;
/* 122 */     long $$2 = Util.getMillis();
/* 123 */     data.ping = $$2 - $$1;
/*     */     
/* 125 */     connection.disconnect((Component)Component.translatable("multiplayer.status.finished"));
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisconnect(Component $$0) {
/* 130 */     if (!this.success) {
/* 131 */       ServerStatusPinger.this.onPingFailed($$0, data);
/* 132 */       ServerStatusPinger.this.pingLegacyServer(address, rawAddress, data);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAcceptingMessages() {
/* 138 */     return connection.isConnected();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\multiplayer\ServerStatusPinger$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */