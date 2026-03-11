/*     */ package net.minecraft.server.rcon.thread;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.ServerSocket;
/*     */ import java.net.Socket;
/*     */ import java.net.SocketTimeoutException;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.server.ServerInterface;
/*     */ import net.minecraft.server.dedicated.DedicatedServerProperties;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class RconThread
/*     */   extends GenericThread {
/*  18 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private final ServerSocket socket;
/*     */   private final String rconPassword;
/*  22 */   private final List<RconClient> clients = Lists.newArrayList();
/*     */   private final ServerInterface serverInterface;
/*     */   
/*     */   private RconThread(ServerInterface $$0, ServerSocket $$1, String $$2) {
/*  26 */     super("RCON Listener");
/*  27 */     this.serverInterface = $$0;
/*  28 */     this.socket = $$1;
/*  29 */     this.rconPassword = $$2;
/*     */   }
/*     */   
/*     */   private void clearClients() {
/*  33 */     this.clients.removeIf($$0 -> !$$0.isRunning());
/*     */   }
/*     */ 
/*     */   
/*     */   public void run() {
/*     */     try {
/*  39 */       while (this.running) {
/*     */         
/*     */         try {
/*  42 */           Socket $$0 = this.socket.accept();
/*  43 */           RconClient $$1 = new RconClient(this.serverInterface, this.rconPassword, $$0);
/*  44 */           $$1.start();
/*  45 */           this.clients.add($$1);
/*     */ 
/*     */           
/*  48 */           clearClients();
/*  49 */         } catch (SocketTimeoutException $$2) {
/*     */           
/*  51 */           clearClients();
/*  52 */         } catch (IOException $$3) {
/*  53 */           if (this.running) {
/*  54 */             LOGGER.info("IO exception: ", $$3);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } finally {
/*  59 */       closeSocket(this.socket);
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static RconThread create(ServerInterface $$0) {
/*  65 */     DedicatedServerProperties $$1 = $$0.getProperties();
/*     */     
/*  67 */     String $$2 = $$0.getServerIp();
/*  68 */     if ($$2.isEmpty()) {
/*  69 */       $$2 = "0.0.0.0";
/*     */     }
/*     */     
/*  72 */     int $$3 = $$1.rconPort;
/*  73 */     if (0 >= $$3 || 65535 < $$3) {
/*  74 */       LOGGER.warn("Invalid rcon port {} found in server.properties, rcon disabled!", Integer.valueOf($$3));
/*  75 */       return null;
/*     */     } 
/*     */     
/*  78 */     String $$4 = $$1.rconPassword;
/*  79 */     if ($$4.isEmpty()) {
/*  80 */       LOGGER.warn("No rcon password set in server.properties, rcon disabled!");
/*  81 */       return null;
/*     */     } 
/*     */     
/*     */     try {
/*  85 */       ServerSocket $$5 = new ServerSocket($$3, 0, InetAddress.getByName($$2));
/*  86 */       $$5.setSoTimeout(500);
/*     */       
/*  88 */       RconThread $$6 = new RconThread($$0, $$5, $$4);
/*  89 */       if (!$$6.start()) {
/*  90 */         return null;
/*     */       }
/*  92 */       LOGGER.info("RCON running on {}:{}", $$2, Integer.valueOf($$3));
/*  93 */       return $$6;
/*  94 */     } catch (IOException $$7) {
/*  95 */       LOGGER.warn("Unable to initialise RCON on {}:{}", new Object[] { $$2, Integer.valueOf($$3), $$7 });
/*     */ 
/*     */       
/*  98 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void stop() {
/* 103 */     this.running = false;
/* 104 */     closeSocket(this.socket);
/* 105 */     super.stop();
/*     */     
/* 107 */     for (RconClient $$0 : this.clients) {
/* 108 */       if ($$0.isRunning()) {
/* 109 */         $$0.stop();
/*     */       }
/*     */     } 
/* 112 */     this.clients.clear();
/*     */   }
/*     */   
/*     */   private void closeSocket(ServerSocket $$0) {
/* 116 */     LOGGER.debug("closeSocket: {}", $$0);
/*     */     
/*     */     try {
/* 119 */       $$0.close();
/* 120 */     } catch (IOException $$1) {
/* 121 */       LOGGER.warn("Failed to close socket", $$1);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\rcon\thread\RconThread.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */