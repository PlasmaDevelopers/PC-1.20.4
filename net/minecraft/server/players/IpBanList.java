/*    */ package net.minecraft.server.players;
/*    */ 
/*    */ import com.google.gson.JsonObject;
/*    */ import java.io.File;
/*    */ import java.net.SocketAddress;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class IpBanList
/*    */   extends StoredUserList<String, IpBanListEntry> {
/*    */   public IpBanList(File $$0) {
/* 11 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   protected StoredUserEntry<String> createEntry(JsonObject $$0) {
/* 16 */     return new IpBanListEntry($$0);
/*    */   }
/*    */   
/*    */   public boolean isBanned(SocketAddress $$0) {
/* 20 */     String $$1 = getIpFromAddress($$0);
/* 21 */     return contains($$1);
/*    */   }
/*    */   
/*    */   public boolean isBanned(String $$0) {
/* 25 */     return contains($$0);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public IpBanListEntry get(SocketAddress $$0) {
/* 30 */     String $$1 = getIpFromAddress($$0);
/* 31 */     return get($$1);
/*    */   }
/*    */   
/*    */   private String getIpFromAddress(SocketAddress $$0) {
/* 35 */     String $$1 = $$0.toString();
/* 36 */     if ($$1.contains("/")) {
/* 37 */       $$1 = $$1.substring($$1.indexOf('/') + 1);
/*    */     }
/* 39 */     if ($$1.contains(":")) {
/* 40 */       $$1 = $$1.substring(0, $$1.indexOf(':'));
/*    */     }
/* 42 */     return $$1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\players\IpBanList.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */