/*    */ package net.minecraft.client.multiplayer.resolver;
/*    */ 
/*    */ import com.google.common.net.HostAndPort;
/*    */ import com.mojang.logging.LogUtils;
/*    */ import java.net.IDN;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ 
/*    */ public final class ServerAddress
/*    */ {
/* 11 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   
/*    */   private final HostAndPort hostAndPort;
/*    */   
/* 15 */   private static final ServerAddress INVALID = new ServerAddress(HostAndPort.fromParts("server.invalid", 25565));
/*    */   
/*    */   public ServerAddress(String $$0, int $$1) {
/* 18 */     this(HostAndPort.fromParts($$0, $$1));
/*    */   }
/*    */   
/*    */   private ServerAddress(HostAndPort $$0) {
/* 22 */     this.hostAndPort = $$0;
/*    */   }
/*    */   
/*    */   public String getHost() {
/*    */     try {
/* 27 */       return IDN.toASCII(this.hostAndPort.getHost());
/* 28 */     } catch (IllegalArgumentException $$0) {
/* 29 */       return "";
/*    */     } 
/*    */   }
/*    */   
/*    */   public int getPort() {
/* 34 */     return this.hostAndPort.getPort();
/*    */   }
/*    */   
/*    */   public static ServerAddress parseString(String $$0) {
/* 38 */     if ($$0 == null) {
/* 39 */       return INVALID;
/*    */     }
/*    */     
/*    */     try {
/* 43 */       HostAndPort $$1 = HostAndPort.fromString($$0).withDefaultPort(25565);
/* 44 */       if ($$1.getHost().isEmpty()) {
/* 45 */         return INVALID;
/*    */       }
/* 47 */       return new ServerAddress($$1);
/*    */     }
/* 49 */     catch (IllegalArgumentException $$2) {
/* 50 */       LOGGER.info("Failed to parse URL {}", $$0, $$2);
/* 51 */       return INVALID;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static boolean isValidAddress(String $$0) {
/*    */     try {
/* 57 */       HostAndPort $$1 = HostAndPort.fromString($$0);
/* 58 */       String $$2 = $$1.getHost();
/* 59 */       if (!$$2.isEmpty()) {
/* 60 */         IDN.toASCII($$2);
/* 61 */         return true;
/*    */       } 
/* 63 */     } catch (IllegalArgumentException illegalArgumentException) {}
/*    */     
/* 65 */     return false;
/*    */   }
/*    */   
/*    */   static int parsePort(String $$0) {
/*    */     try {
/* 70 */       return Integer.parseInt($$0.trim());
/* 71 */     } catch (Exception exception) {
/*    */ 
/*    */       
/* 74 */       return 25565;
/*    */     } 
/*    */   }
/*    */   
/*    */   public String toString() {
/* 79 */     return this.hostAndPort.toString();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object $$0) {
/* 84 */     if (this == $$0) {
/* 85 */       return true;
/*    */     }
/* 87 */     if ($$0 instanceof ServerAddress) {
/* 88 */       return this.hostAndPort.equals(((ServerAddress)$$0).hostAndPort);
/*    */     }
/*    */     
/* 91 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 96 */     return this.hostAndPort.hashCode();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\multiplayer\resolver\ServerAddress.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */