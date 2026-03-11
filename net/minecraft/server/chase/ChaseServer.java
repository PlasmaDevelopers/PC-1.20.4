/*     */ package net.minecraft.server.chase;
/*     */ 
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.net.InetAddress;
/*     */ import java.net.ServerSocket;
/*     */ import java.net.Socket;
/*     */ import java.nio.channels.ClosedByInterruptException;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ import java.util.stream.Collectors;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.server.commands.ChaseCommand;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.server.players.PlayerList;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChaseServer
/*     */ {
/*  31 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private final String serverBindAddress;
/*     */   private final int serverPort;
/*     */   private final PlayerList playerList;
/*     */   private final int broadcastIntervalMs;
/*     */   private volatile boolean wantsToRun;
/*     */   @Nullable
/*     */   private ServerSocket serverSocket;
/*  40 */   private final CopyOnWriteArrayList<Socket> clientSockets = new CopyOnWriteArrayList<>();
/*     */   
/*     */   public ChaseServer(String $$0, int $$1, PlayerList $$2, int $$3) {
/*  43 */     this.serverBindAddress = $$0;
/*  44 */     this.serverPort = $$1;
/*  45 */     this.playerList = $$2;
/*  46 */     this.broadcastIntervalMs = $$3;
/*     */   }
/*     */   
/*     */   public void start() throws IOException {
/*  50 */     if (this.serverSocket != null && !this.serverSocket.isClosed()) {
/*  51 */       LOGGER.warn("Remote control server was asked to start, but it is already running. Will ignore.");
/*     */       return;
/*     */     } 
/*  54 */     this.wantsToRun = true;
/*  55 */     this.serverSocket = new ServerSocket(this.serverPort, 50, InetAddress.getByName(this.serverBindAddress));
/*  56 */     Thread $$0 = new Thread(this::runAcceptor, "chase-server-acceptor");
/*  57 */     $$0.setDaemon(true);
/*  58 */     $$0.start();
/*     */     
/*  60 */     Thread $$1 = new Thread(this::runSender, "chase-server-sender");
/*  61 */     $$1.setDaemon(true);
/*  62 */     $$1.start();
/*     */   }
/*     */ 
/*     */   
/*     */   private void runSender() {
/*  67 */     PlayerPosition $$0 = null;
/*  68 */     while (this.wantsToRun) {
/*  69 */       if (!this.clientSockets.isEmpty()) {
/*  70 */         PlayerPosition $$1 = getPlayerPosition();
/*  71 */         if ($$1 != null && !$$1.equals($$0)) {
/*  72 */           $$0 = $$1;
/*  73 */           byte[] $$2 = $$1.format().getBytes(StandardCharsets.US_ASCII);
/*  74 */           for (Socket $$3 : this.clientSockets) {
/*  75 */             if ($$3.isClosed()) {
/*     */               continue;
/*     */             }
/*  78 */             Util.ioPool().submit(() -> {
/*     */                   try {
/*     */                     OutputStream $$2 = $$0.getOutputStream();
/*     */                     $$2.write($$1);
/*     */                     $$2.flush();
/*  83 */                   } catch (IOException $$3) {
/*     */                     LOGGER.info("Remote control client socket got an IO exception and will be closed", $$3);
/*     */                     IOUtils.closeQuietly($$0);
/*     */                   } 
/*     */                 });
/*     */           } 
/*     */         } 
/*  90 */         List<Socket> $$4 = (List<Socket>)this.clientSockets.stream().filter(Socket::isClosed).collect(Collectors.toList());
/*  91 */         this.clientSockets.removeAll($$4);
/*     */       } 
/*  93 */       if (this.wantsToRun) {
/*     */         try {
/*  95 */           Thread.sleep(this.broadcastIntervalMs);
/*  96 */         } catch (InterruptedException interruptedException) {}
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void stop() {
/* 103 */     this.wantsToRun = false;
/*     */     
/* 105 */     IOUtils.closeQuietly(this.serverSocket);
/* 106 */     this.serverSocket = null;
/*     */   }
/*     */   
/*     */   private void runAcceptor() {
/*     */     try {
/* 111 */       while (this.wantsToRun) {
/* 112 */         if (this.serverSocket != null) {
/* 113 */           LOGGER.info("Remote control server is listening for connections on port {}", Integer.valueOf(this.serverPort));
/* 114 */           Socket $$0 = this.serverSocket.accept();
/* 115 */           LOGGER.info("Remote control server received client connection on port {}", Integer.valueOf($$0.getPort()));
/* 116 */           this.clientSockets.add($$0);
/*     */         } 
/*     */       } 
/* 119 */     } catch (ClosedByInterruptException $$1) {
/* 120 */       if (this.wantsToRun) {
/* 121 */         LOGGER.info("Remote control server closed by interrupt");
/*     */       }
/* 123 */     } catch (IOException $$2) {
/* 124 */       if (this.wantsToRun) {
/* 125 */         LOGGER.error("Remote control server closed because of an IO exception", $$2);
/*     */       }
/*     */     } finally {
/* 128 */       IOUtils.closeQuietly(this.serverSocket);
/*     */     } 
/* 130 */     LOGGER.info("Remote control server is now stopped");
/* 131 */     this.wantsToRun = false;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private PlayerPosition getPlayerPosition() {
/* 136 */     List<ServerPlayer> $$0 = this.playerList.getPlayers();
/* 137 */     if ($$0.isEmpty()) {
/* 138 */       return null;
/*     */     }
/* 140 */     ServerPlayer $$1 = $$0.get(0);
/* 141 */     String $$2 = (String)ChaseCommand.DIMENSION_NAMES.inverse().get($$1.level().dimension());
/* 142 */     if ($$2 == null) {
/* 143 */       return null;
/*     */     }
/* 145 */     return new PlayerPosition($$2, $$1.getX(), $$1.getY(), $$1.getZ(), $$1.getYRot(), $$1.getXRot());
/*     */   }
/*     */   private static final class PlayerPosition extends Record { private final String dimensionName; private final double x; private final double y; private final double z; private final float yRot; private final float xRot;
/* 148 */     PlayerPosition(String $$0, double $$1, double $$2, double $$3, float $$4, float $$5) { this.dimensionName = $$0; this.x = $$1; this.y = $$2; this.z = $$3; this.yRot = $$4; this.xRot = $$5; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/server/chase/ChaseServer$PlayerPosition;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #148	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 148 */       //   0	7	0	this	Lnet/minecraft/server/chase/ChaseServer$PlayerPosition; } public String dimensionName() { return this.dimensionName; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/server/chase/ChaseServer$PlayerPosition;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #148	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/server/chase/ChaseServer$PlayerPosition; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/server/chase/ChaseServer$PlayerPosition;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #148	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/server/chase/ChaseServer$PlayerPosition;
/* 148 */       //   0	8	1	$$0	Ljava/lang/Object; } public double x() { return this.x; } public double y() { return this.y; } public double z() { return this.z; } public float yRot() { return this.yRot; } public float xRot() { return this.xRot; }
/*     */      String format() {
/* 150 */       return String.format(Locale.ROOT, "t %s %.2f %.2f %.2f %.2f %.2f\n", new Object[] { this.dimensionName, Double.valueOf(this.x), Double.valueOf(this.y), Double.valueOf(this.z), Float.valueOf(this.yRot), Float.valueOf(this.xRot) });
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\chase\ChaseServer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */