/*     */ package net.minecraft.client.server;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.DatagramPacket;
/*     */ import java.net.InetAddress;
/*     */ import java.net.MulticastSocket;
/*     */ import java.net.SocketTimeoutException;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import net.minecraft.DefaultUncaughtExceptionHandler;
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
/*     */ public class LanServerDetector
/*     */   extends Thread
/*     */ {
/*     */   private final LanServerDetection.LanServerList serverList;
/*     */   private final InetAddress pingGroup;
/*     */   private final MulticastSocket socket;
/*     */   
/*     */   public LanServerDetector(LanServerDetection.LanServerList $$0) throws IOException {
/*  68 */     super("LanServerDetector #" + LanServerDetection.UNIQUE_THREAD_ID.incrementAndGet());
/*  69 */     this.serverList = $$0;
/*  70 */     setDaemon(true);
/*  71 */     setUncaughtExceptionHandler((Thread.UncaughtExceptionHandler)new DefaultUncaughtExceptionHandler(LanServerDetection.LOGGER));
/*     */     
/*  73 */     this.socket = new MulticastSocket(4445);
/*  74 */     this.pingGroup = InetAddress.getByName("224.0.2.60");
/*  75 */     this.socket.setSoTimeout(5000);
/*  76 */     this.socket.joinGroup(this.pingGroup);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void run() {
/*  82 */     byte[] $$0 = new byte[1024];
/*     */     
/*  84 */     while (!isInterrupted()) {
/*  85 */       DatagramPacket $$1 = new DatagramPacket($$0, $$0.length);
/*     */       try {
/*  87 */         this.socket.receive($$1);
/*  88 */       } catch (SocketTimeoutException $$2) {
/*     */         continue;
/*  90 */       } catch (IOException $$3) {
/*  91 */         LanServerDetection.LOGGER.error("Couldn't ping server", $$3);
/*     */         
/*     */         break;
/*     */       } 
/*  95 */       String $$4 = new String($$1.getData(), $$1.getOffset(), $$1.getLength(), StandardCharsets.UTF_8);
/*  96 */       LanServerDetection.LOGGER.debug("{}: {}", $$1.getAddress(), $$4);
/*  97 */       this.serverList.addServer($$4, $$1.getAddress());
/*     */     } 
/*     */     
/*     */     try {
/* 101 */       this.socket.leaveGroup(this.pingGroup);
/* 102 */     } catch (IOException iOException) {}
/*     */     
/* 104 */     this.socket.close();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\server\LanServerDetection$LanServerDetector.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */