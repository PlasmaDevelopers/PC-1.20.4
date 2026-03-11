/*    */ package net.minecraft.client.multiplayer.resolver;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.function.Predicate;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class null
/*    */   implements AddressCheck
/*    */ {
/*    */   public boolean isAllowed(ResolvedServerAddress $$0) {
/* 25 */     String $$1 = $$0.getHostName();
/* 26 */     String $$2 = $$0.getHostIp();
/* 27 */     return blockLists.stream().noneMatch($$2 -> ($$2.test($$0) || $$2.test($$1)));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isAllowed(ServerAddress $$0) {
/* 32 */     String $$1 = $$0.getHost();
/* 33 */     return blockLists.stream().noneMatch($$1 -> $$1.test($$0));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\multiplayer\resolver\AddressCheck$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */