/*     */ package net.minecraft.client.server;
/*     */ 
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.io.IOException;
/*     */ import java.net.DatagramPacket;
/*     */ import java.net.DatagramSocket;
/*     */ import java.net.InetAddress;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import net.minecraft.DefaultUncaughtExceptionHandler;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class LanServerPinger
/*     */   extends Thread {
/*  15 */   private static final AtomicInteger UNIQUE_THREAD_ID = new AtomicInteger(0);
/*  16 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   public static final String MULTICAST_GROUP = "224.0.2.60";
/*     */   
/*     */   public static final int PING_PORT = 4445;
/*     */   private static final long PING_INTERVAL = 1500L;
/*     */   private final String motd;
/*     */   private final DatagramSocket socket;
/*     */   private boolean isRunning = true;
/*     */   private final String serverAddress;
/*     */   
/*     */   public LanServerPinger(String $$0, String $$1) throws IOException {
/*  28 */     super("LanServerPinger #" + UNIQUE_THREAD_ID.incrementAndGet());
/*  29 */     this.motd = $$0;
/*  30 */     this.serverAddress = $$1;
/*  31 */     setDaemon(true);
/*  32 */     setUncaughtExceptionHandler((Thread.UncaughtExceptionHandler)new DefaultUncaughtExceptionHandler(LOGGER));
/*     */     
/*  34 */     this.socket = new DatagramSocket();
/*     */   }
/*     */ 
/*     */   
/*     */   public void run() {
/*  39 */     String $$0 = createPingString(this.motd, this.serverAddress);
/*  40 */     byte[] $$1 = $$0.getBytes(StandardCharsets.UTF_8);
/*     */     
/*  42 */     while (!isInterrupted() && this.isRunning) {
/*     */       try {
/*  44 */         InetAddress $$2 = InetAddress.getByName("224.0.2.60");
/*     */         
/*  46 */         DatagramPacket $$3 = new DatagramPacket($$1, $$1.length, $$2, 4445);
/*  47 */         this.socket.send($$3);
/*  48 */       } catch (IOException $$4) {
/*  49 */         LOGGER.warn("LanServerPinger: {}", $$4.getMessage());
/*     */         
/*     */         break;
/*     */       } 
/*     */       try {
/*  54 */         sleep(1500L);
/*  55 */       } catch (InterruptedException interruptedException) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void interrupt() {
/*  62 */     super.interrupt();
/*     */ 
/*     */     
/*  65 */     this.isRunning = false;
/*     */   }
/*     */   
/*     */   public static String createPingString(String $$0, String $$1) {
/*  69 */     return "[MOTD]" + $$0 + "[/MOTD][AD]" + $$1 + "[/AD]";
/*     */   }
/*     */   
/*     */   public static String parseMotd(String $$0) {
/*  73 */     int $$1 = $$0.indexOf("[MOTD]");
/*  74 */     if ($$1 < 0) {
/*  75 */       return "missing no";
/*     */     }
/*  77 */     int $$2 = $$0.indexOf("[/MOTD]", $$1 + "[MOTD]".length());
/*  78 */     if ($$2 < $$1) {
/*  79 */       return "missing no";
/*     */     }
/*  81 */     return $$0.substring($$1 + "[MOTD]".length(), $$2);
/*     */   }
/*     */   
/*     */   public static String parseAddress(String $$0) {
/*  85 */     int $$1 = $$0.indexOf("[/MOTD]");
/*  86 */     if ($$1 < 0) {
/*  87 */       return null;
/*     */     }
/*     */     
/*  90 */     int $$2 = $$0.indexOf("[/MOTD]", $$1 + "[/MOTD]".length());
/*  91 */     if ($$2 >= 0)
/*     */     {
/*  93 */       return null;
/*     */     }
/*     */     
/*  96 */     int $$3 = $$0.indexOf("[AD]", $$1 + "[/MOTD]".length());
/*  97 */     if ($$3 < 0) {
/*  98 */       return null;
/*     */     }
/* 100 */     int $$4 = $$0.indexOf("[/AD]", $$3 + "[AD]".length());
/* 101 */     if ($$4 < $$3) {
/* 102 */       return null;
/*     */     }
/* 104 */     return $$0.substring($$3 + "[AD]".length(), $$4);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\server\LanServerPinger.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */