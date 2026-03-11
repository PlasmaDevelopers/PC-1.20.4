/*     */ package net.minecraft.client.server;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.io.IOException;
/*     */ import java.net.DatagramPacket;
/*     */ import java.net.InetAddress;
/*     */ import java.net.MulticastSocket;
/*     */ import java.net.SocketTimeoutException;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.DefaultUncaughtExceptionHandler;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class LanServerDetection
/*     */ {
/*  19 */   static final AtomicInteger UNIQUE_THREAD_ID = new AtomicInteger(0);
/*  20 */   static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   public static class LanServerList {
/*  23 */     private final List<LanServer> servers = Lists.newArrayList();
/*     */     private boolean isDirty;
/*     */     
/*     */     @Nullable
/*     */     public synchronized List<LanServer> takeDirtyServers() {
/*  28 */       if (this.isDirty) {
/*  29 */         List<LanServer> $$0 = List.copyOf(this.servers);
/*  30 */         this.isDirty = false;
/*  31 */         return $$0;
/*     */       } 
/*  33 */       return null;
/*     */     }
/*     */     
/*     */     public synchronized void addServer(String $$0, InetAddress $$1) {
/*  37 */       String $$2 = LanServerPinger.parseMotd($$0);
/*  38 */       String $$3 = LanServerPinger.parseAddress($$0);
/*  39 */       if ($$3 == null) {
/*     */         return;
/*     */       }
/*     */       
/*  43 */       $$3 = $$1.getHostAddress() + ":" + $$1.getHostAddress();
/*     */       
/*  45 */       boolean $$4 = false;
/*  46 */       for (LanServer $$5 : this.servers) {
/*  47 */         if ($$5.getAddress().equals($$3)) {
/*  48 */           $$5.updatePingTime();
/*  49 */           $$4 = true;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*  54 */       if (!$$4) {
/*  55 */         this.servers.add(new LanServer($$2, $$3));
/*  56 */         this.isDirty = true;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public static class LanServerDetector
/*     */     extends Thread {
/*     */     private final LanServerDetection.LanServerList serverList;
/*     */     private final InetAddress pingGroup;
/*     */     private final MulticastSocket socket;
/*     */     
/*     */     public LanServerDetector(LanServerDetection.LanServerList $$0) throws IOException {
/*  68 */       super("LanServerDetector #" + LanServerDetection.UNIQUE_THREAD_ID.incrementAndGet());
/*  69 */       this.serverList = $$0;
/*  70 */       setDaemon(true);
/*  71 */       setUncaughtExceptionHandler((Thread.UncaughtExceptionHandler)new DefaultUncaughtExceptionHandler(LanServerDetection.LOGGER));
/*     */       
/*  73 */       this.socket = new MulticastSocket(4445);
/*  74 */       this.pingGroup = InetAddress.getByName("224.0.2.60");
/*  75 */       this.socket.setSoTimeout(5000);
/*  76 */       this.socket.joinGroup(this.pingGroup);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void run() {
/*  82 */       byte[] $$0 = new byte[1024];
/*     */       
/*  84 */       while (!isInterrupted()) {
/*  85 */         DatagramPacket $$1 = new DatagramPacket($$0, $$0.length);
/*     */         try {
/*  87 */           this.socket.receive($$1);
/*  88 */         } catch (SocketTimeoutException $$2) {
/*     */           continue;
/*  90 */         } catch (IOException $$3) {
/*  91 */           LanServerDetection.LOGGER.error("Couldn't ping server", $$3);
/*     */           
/*     */           break;
/*     */         } 
/*  95 */         String $$4 = new String($$1.getData(), $$1.getOffset(), $$1.getLength(), StandardCharsets.UTF_8);
/*  96 */         LanServerDetection.LOGGER.debug("{}: {}", $$1.getAddress(), $$4);
/*  97 */         this.serverList.addServer($$4, $$1.getAddress());
/*     */       } 
/*     */       
/*     */       try {
/* 101 */         this.socket.leaveGroup(this.pingGroup);
/* 102 */       } catch (IOException iOException) {}
/*     */       
/* 104 */       this.socket.close();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\server\LanServerDetection.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */