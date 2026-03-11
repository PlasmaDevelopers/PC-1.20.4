/*    */ package net.minecraft.client.multiplayer.resolver;
/*    */ 
/*    */ import com.mojang.logging.LogUtils;
/*    */ import java.net.InetAddress;
/*    */ import java.net.InetSocketAddress;
/*    */ import java.net.UnknownHostException;
/*    */ import java.util.Optional;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ @FunctionalInterface
/*    */ public interface ServerAddressResolver
/*    */ {
/* 13 */   public static final Logger LOGGER = LogUtils.getLogger();
/*    */   static {
/* 15 */     SYSTEM = ($$0 -> {
/*    */         try {
/*    */           InetAddress $$1 = InetAddress.getByName($$0.getHost());
/*    */           return Optional.of(ResolvedServerAddress.from(new InetSocketAddress($$1, $$0.getPort())));
/* 19 */         } catch (UnknownHostException $$2) {
/*    */           LOGGER.debug("Couldn't resolve server {} address", $$0.getHost(), $$2);
/*    */           return Optional.empty();
/*    */         } 
/*    */       });
/*    */   }
/*    */   
/*    */   public static final ServerAddressResolver SYSTEM;
/*    */   
/*    */   Optional<ResolvedServerAddress> resolve(ServerAddress paramServerAddress);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\multiplayer\resolver\ServerAddressResolver.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */