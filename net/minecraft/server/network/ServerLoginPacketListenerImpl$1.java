/*     */ package net.minecraft.server.network;
/*     */ 
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
/*     */ import com.mojang.authlib.yggdrasil.ProfileResult;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.SocketAddress;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.UUIDUtil;
/*     */ import net.minecraft.network.chat.Component;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class null
/*     */   extends Thread
/*     */ {
/*     */   null(String $$1) {
/* 197 */     super($$1);
/*     */   }
/*     */   public void run() {
/* 200 */     String $$0 = Objects.<String>requireNonNull(ServerLoginPacketListenerImpl.this.requestedUsername, "Player name not initialized");
/*     */     try {
/* 202 */       ProfileResult $$1 = ServerLoginPacketListenerImpl.this.server.getSessionService().hasJoinedServer($$0, digest, getAddress());
/*     */       
/* 204 */       if ($$1 != null) {
/* 205 */         GameProfile $$2 = $$1.profile();
/* 206 */         ServerLoginPacketListenerImpl.LOGGER.info("UUID of player {} is {}", $$2.getName(), $$2.getId());
/* 207 */         ServerLoginPacketListenerImpl.this.startClientVerification($$2);
/* 208 */       } else if (ServerLoginPacketListenerImpl.this.server.isSingleplayer()) {
/* 209 */         ServerLoginPacketListenerImpl.LOGGER.warn("Failed to verify username but will let them in anyway!");
/* 210 */         ServerLoginPacketListenerImpl.this.startClientVerification(UUIDUtil.createOfflineProfile($$0));
/*     */       } else {
/* 212 */         ServerLoginPacketListenerImpl.this.disconnect((Component)Component.translatable("multiplayer.disconnect.unverified_username"));
/* 213 */         ServerLoginPacketListenerImpl.LOGGER.error("Username '{}' tried to join with an invalid session", $$0);
/*     */       } 
/* 215 */     } catch (AuthenticationUnavailableException $$3) {
/* 216 */       if (ServerLoginPacketListenerImpl.this.server.isSingleplayer()) {
/* 217 */         ServerLoginPacketListenerImpl.LOGGER.warn("Authentication servers are down but will let them in anyway!");
/* 218 */         ServerLoginPacketListenerImpl.this.startClientVerification(UUIDUtil.createOfflineProfile($$0));
/*     */       } else {
/* 220 */         ServerLoginPacketListenerImpl.this.disconnect((Component)Component.translatable("multiplayer.disconnect.authservers_down"));
/* 221 */         ServerLoginPacketListenerImpl.LOGGER.error("Couldn't verify username because servers are unavailable");
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private InetAddress getAddress() {
/* 228 */     SocketAddress $$0 = ServerLoginPacketListenerImpl.this.connection.getRemoteAddress();
/* 229 */     return (ServerLoginPacketListenerImpl.this.server.getPreventProxyConnections() && $$0 instanceof InetSocketAddress) ? ((InetSocketAddress)$$0).getAddress() : null;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\network\ServerLoginPacketListenerImpl$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */