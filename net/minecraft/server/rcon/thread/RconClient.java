/*     */ package net.minecraft.server.rcon.thread;
/*     */ 
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.net.Socket;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.Locale;
/*     */ import net.minecraft.server.ServerInterface;
/*     */ import net.minecraft.server.rcon.PktUtils;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class RconClient
/*     */   extends GenericThread {
/*  17 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   private static final int SERVERDATA_AUTH = 3;
/*     */   private static final int SERVERDATA_EXECCOMMAND = 2;
/*     */   private static final int SERVERDATA_RESPONSE_VALUE = 0;
/*     */   private static final int SERVERDATA_AUTH_RESPONSE = 2;
/*     */   private static final int SERVERDATA_AUTH_FAILURE = -1;
/*     */   private boolean authed;
/*     */   private final Socket client;
/*  25 */   private final byte[] buf = new byte[1460];
/*     */   private final String rconPassword;
/*     */   private final ServerInterface serverInterface;
/*     */   
/*     */   RconClient(ServerInterface $$0, String $$1, Socket $$2) {
/*  30 */     super("RCON Client " + $$2.getInetAddress());
/*  31 */     this.serverInterface = $$0;
/*  32 */     this.client = $$2;
/*     */     
/*     */     try {
/*  35 */       this.client.setSoTimeout(0);
/*  36 */     } catch (Exception $$3) {
/*  37 */       this.running = false;
/*     */     } 
/*     */     
/*  40 */     this.rconPassword = $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void run() {
/*     */     
/*  46 */     try { while (this.running) {
/*  47 */         String $$6; BufferedInputStream $$0 = new BufferedInputStream(this.client.getInputStream());
/*  48 */         int $$1 = $$0.read(this.buf, 0, 1460);
/*     */         
/*  50 */         if (10 > $$1) {
/*     */           return;
/*     */         }
/*     */         
/*  54 */         int $$2 = 0;
/*  55 */         int $$3 = PktUtils.intFromByteArray(this.buf, 0, $$1);
/*  56 */         if ($$3 != $$1 - 4) {
/*     */           return;
/*     */         }
/*     */         
/*  60 */         $$2 += 4;
/*  61 */         int $$4 = PktUtils.intFromByteArray(this.buf, $$2, $$1);
/*  62 */         $$2 += 4;
/*     */         
/*  64 */         int $$5 = PktUtils.intFromByteArray(this.buf, $$2);
/*  65 */         $$2 += 4;
/*  66 */         switch ($$5) {
/*     */           case 3:
/*  68 */             $$6 = PktUtils.stringFromByteArray(this.buf, $$2, $$1);
/*  69 */             $$2 += $$6.length();
/*  70 */             if (!$$6.isEmpty() && $$6.equals(this.rconPassword)) {
/*  71 */               this.authed = true;
/*  72 */               send($$4, 2, ""); continue;
/*     */             } 
/*  74 */             this.authed = false;
/*  75 */             sendAuthFailure();
/*     */             continue;
/*     */           
/*     */           case 2:
/*  79 */             if (this.authed) {
/*  80 */               String $$7 = PktUtils.stringFromByteArray(this.buf, $$2, $$1);
/*     */               try {
/*  82 */                 sendCmdResponse($$4, this.serverInterface.runCommand($$7));
/*  83 */               } catch (Exception $$8) {
/*  84 */                 sendCmdResponse($$4, "Error executing: " + $$7 + " (" + $$8.getMessage() + ")");
/*     */               }  continue;
/*     */             } 
/*  87 */             sendAuthFailure();
/*     */             continue;
/*     */         } 
/*     */         
/*  91 */         sendCmdResponse($$4, String.format(Locale.ROOT, "Unknown request %s", new Object[] { Integer.toHexString($$5) }));
/*     */       }
/*     */        }
/*  94 */     catch (IOException iOException) {  }
/*  95 */     catch (Exception $$9)
/*  96 */     { LOGGER.error("Exception whilst parsing RCON input", $$9); }
/*     */     finally
/*  98 */     { closeSocket();
/*  99 */       LOGGER.info("Thread {} shutting down", this.name);
/* 100 */       this.running = false; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void send(int $$0, int $$1, String $$2) throws IOException {
/* 107 */     ByteArrayOutputStream $$3 = new ByteArrayOutputStream(1248);
/* 108 */     DataOutputStream $$4 = new DataOutputStream($$3);
/* 109 */     byte[] $$5 = $$2.getBytes(StandardCharsets.UTF_8);
/* 110 */     $$4.writeInt(Integer.reverseBytes($$5.length + 10));
/* 111 */     $$4.writeInt(Integer.reverseBytes($$0));
/* 112 */     $$4.writeInt(Integer.reverseBytes($$1));
/* 113 */     $$4.write($$5);
/* 114 */     $$4.write(0);
/* 115 */     $$4.write(0);
/* 116 */     this.client.getOutputStream().write($$3.toByteArray());
/*     */   }
/*     */   
/*     */   private void sendAuthFailure() throws IOException {
/* 120 */     send(-1, 2, "");
/*     */   }
/*     */   
/*     */   private void sendCmdResponse(int $$0, String $$1) throws IOException {
/* 124 */     int $$2 = $$1.length();
/*     */     
/*     */     do {
/* 127 */       int $$3 = (4096 <= $$2) ? 4096 : $$2;
/* 128 */       send($$0, 0, $$1.substring(0, $$3));
/* 129 */       $$1 = $$1.substring($$3);
/* 130 */       $$2 = $$1.length();
/* 131 */     } while (0 != $$2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void stop() {
/* 139 */     this.running = false;
/* 140 */     closeSocket();
/* 141 */     super.stop();
/*     */   }
/*     */   
/*     */   private void closeSocket() {
/*     */     try {
/* 146 */       this.client.close();
/* 147 */     } catch (IOException $$0) {
/* 148 */       LOGGER.warn("Failed to close socket", $$0);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\rcon\thread\RconClient.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */