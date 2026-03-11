/*    */ package net.minecraft.client.multiplayer.resolver;
/*    */ 
/*    */ import com.mojang.logging.LogUtils;
/*    */ import java.util.Hashtable;
/*    */ import java.util.Optional;
/*    */ import javax.naming.directory.Attribute;
/*    */ import javax.naming.directory.Attributes;
/*    */ import javax.naming.directory.DirContext;
/*    */ import javax.naming.directory.InitialDirContext;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ 
/*    */ @FunctionalInterface
/*    */ public interface ServerRedirectHandler
/*    */ {
/* 16 */   public static final Logger LOGGER = LogUtils.getLogger();
/*    */ 
/*    */   
/*    */   public static final ServerRedirectHandler EMPTY = $$0 -> Optional.empty();
/*    */ 
/*    */   
/*    */   static ServerRedirectHandler createDnsSrvRedirectHandler() {
/*    */     DirContext $$2;
/*    */     try {
/* 25 */       String $$0 = "com.sun.jndi.dns.DnsContextFactory";
/*    */       
/* 27 */       Class.forName("com.sun.jndi.dns.DnsContextFactory");
/*    */       
/* 29 */       Hashtable<String, String> $$1 = new Hashtable<>();
/* 30 */       $$1.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
/* 31 */       $$1.put("java.naming.provider.url", "dns:");
/* 32 */       $$1.put("com.sun.jndi.dns.timeout.retries", "1");
/* 33 */       $$2 = new InitialDirContext($$1);
/* 34 */     } catch (Throwable $$3) {
/* 35 */       LOGGER.error("Failed to initialize SRV redirect resolved, some servers might not work", $$3);
/* 36 */       return EMPTY;
/*    */     } 
/*    */     
/* 39 */     return $$1 -> {
/*    */         if ($$1.getPort() == 25565)
/*    */           try {
/*    */             Attributes $$2 = $$0.getAttributes("_minecraft._tcp." + $$1.getHost(), new String[] { "SRV" });
/*    */             
/*    */             Attribute $$3 = $$2.get("srv");
/*    */             if ($$3 != null) {
/*    */               String[] $$4 = $$3.get().toString().split(" ", 4);
/*    */               return Optional.of(new ServerAddress($$4[3], ServerAddress.parsePort($$4[2])));
/*    */             } 
/* 49 */           } catch (Throwable throwable) {} 
/*    */         return Optional.empty();
/*    */       };
/*    */   }
/*    */   
/*    */   Optional<ServerAddress> lookupRedirect(ServerAddress paramServerAddress);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\multiplayer\resolver\ServerRedirectHandler.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */