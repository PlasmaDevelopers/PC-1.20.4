/*    */ package net.minecraft.realms;
/*    */ 
/*    */ import com.mojang.realmsclient.dto.RealmsServer;
/*    */ import java.net.InetSocketAddress;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.multiplayer.ClientHandshakePacketListenerImpl;
/*    */ import net.minecraft.client.multiplayer.chat.report.ReportEnvironment;
/*    */ import net.minecraft.client.quickplay.QuickPlayLog;
/*    */ import net.minecraft.client.resources.server.ServerPackManager;
/*    */ import net.minecraft.network.Connection;
/*    */ import net.minecraft.network.chat.CommonComponents;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.network.protocol.login.ClientLoginPacketListener;
/*    */ import net.minecraft.network.protocol.login.ServerboundHelloPacket;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class null
/*    */   extends Thread
/*    */ {
/*    */   null(String $$1) {
/* 40 */     super($$1);
/*    */   }
/*    */   public void run() {
/* 43 */     InetSocketAddress $$0 = null;
/*    */     try {
/* 45 */       $$0 = new InetSocketAddress(hostname, port);
/*    */       
/* 47 */       if (RealmsConnect.this.aborted) {
/*    */         return;
/*    */       }
/*    */       
/* 51 */       RealmsConnect.this.connection = Connection.connectToServer($$0, minecraft.options.useNativeTransport(), minecraft.getDebugOverlay().getBandwidthLogger());
/*    */       
/* 53 */       if (RealmsConnect.this.aborted) {
/*    */         return;
/*    */       }
/*    */       
/* 57 */       ClientHandshakePacketListenerImpl $$1 = new ClientHandshakePacketListenerImpl(RealmsConnect.this.connection, minecraft, server.toServerData(hostname), RealmsConnect.this.onlineScreen, false, null, $$0 -> { 
/* 58 */           }); if (server.worldType == RealmsServer.WorldType.MINIGAME) {
/* 59 */         $$1.setMinigameName(server.minigameName);
/*    */       }
/*    */       
/* 62 */       if (RealmsConnect.this.aborted) {
/*    */         return;
/*    */       }
/*    */       
/* 66 */       RealmsConnect.this.connection.initiateServerboundPlayConnection(hostname, port, (ClientLoginPacketListener)$$1);
/*    */       
/* 68 */       if (RealmsConnect.this.aborted) {
/*    */         return;
/*    */       }
/*    */       
/* 72 */       RealmsConnect.this.connection.send((Packet)new ServerboundHelloPacket(minecraft.getUser().getName(), minecraft.getUser().getProfileId()));
/* 73 */       minecraft.updateReportEnvironment(ReportEnvironment.realm(server));
/* 74 */       minecraft.quickPlayLog().setWorldData(QuickPlayLog.Type.REALMS, String.valueOf(server.id), server.name);
/* 75 */       minecraft.getDownloadedPackSource().configureForServerControl(RealmsConnect.this.connection, ServerPackManager.PackPromptStatus.ALLOWED);
/* 76 */     } catch (Exception $$2) {
/* 77 */       minecraft.getDownloadedPackSource().cleanupAfterDisconnect();
/*    */       
/* 79 */       if (RealmsConnect.this.aborted) {
/*    */         return;
/*    */       }
/*    */       
/* 83 */       RealmsConnect.LOGGER.error("Couldn't connect to world", $$2);
/* 84 */       String $$3 = $$2.toString();
/*    */       
/* 86 */       if ($$0 != null) {
/* 87 */         String $$4 = "" + $$0 + ":" + $$0;
/* 88 */         $$3 = $$3.replaceAll($$4, "");
/*    */       } 
/*    */       
/* 91 */       DisconnectedRealmsScreen $$5 = new DisconnectedRealmsScreen(RealmsConnect.this.onlineScreen, CommonComponents.CONNECT_FAILED, (Component)Component.translatable("disconnect.genericReason", new Object[] { $$3 }));
/* 92 */       minecraft.execute(() -> $$0.setScreen($$1));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\realms\RealmsConnect$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */