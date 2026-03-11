/*    */ package net.minecraft.client.multiplayer.resolver;
/*    */ 
/*    */ import com.google.common.annotations.VisibleForTesting;
/*    */ import java.util.Objects;
/*    */ import java.util.Optional;
/*    */ 
/*    */ public class ServerNameResolver {
/*  8 */   public static final ServerNameResolver DEFAULT = new ServerNameResolver(ServerAddressResolver.SYSTEM, ServerRedirectHandler.createDnsSrvRedirectHandler(), AddressCheck.createFromService());
/*    */   
/*    */   private final ServerAddressResolver resolver;
/*    */   private final ServerRedirectHandler redirectHandler;
/*    */   private final AddressCheck addressCheck;
/*    */   
/*    */   @VisibleForTesting
/*    */   ServerNameResolver(ServerAddressResolver $$0, ServerRedirectHandler $$1, AddressCheck $$2) {
/* 16 */     this.resolver = $$0;
/* 17 */     this.redirectHandler = $$1;
/* 18 */     this.addressCheck = $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   public Optional<ResolvedServerAddress> resolveAddress(ServerAddress $$0) {
/* 23 */     Optional<ResolvedServerAddress> $$1 = this.resolver.resolve($$0);
/*    */ 
/*    */     
/* 26 */     if (($$1.isPresent() && !this.addressCheck.isAllowed($$1.get())) || 
/* 27 */       !this.addressCheck.isAllowed($$0)) {
/* 28 */       return Optional.empty();
/*    */     }
/*    */     
/* 31 */     Optional<ServerAddress> $$2 = this.redirectHandler.lookupRedirect($$0);
/* 32 */     if ($$2.isPresent()) {
/*    */       
/* 34 */       Objects.requireNonNull(this.addressCheck); $$1 = this.resolver.resolve($$2.get()).filter(this.addressCheck::isAllowed);
/*    */     } 
/*    */     
/* 37 */     return $$1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\multiplayer\resolver\ServerNameResolver.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */