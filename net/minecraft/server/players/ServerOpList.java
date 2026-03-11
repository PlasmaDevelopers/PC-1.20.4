/*    */ package net.minecraft.server.players;
/*    */ 
/*    */ import com.google.gson.JsonObject;
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import java.io.File;
/*    */ import java.util.Objects;
/*    */ 
/*    */ public class ServerOpList
/*    */   extends StoredUserList<GameProfile, ServerOpListEntry> {
/*    */   public ServerOpList(File $$0) {
/* 11 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   protected StoredUserEntry<GameProfile> createEntry(JsonObject $$0) {
/* 16 */     return new ServerOpListEntry($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getUserList() {
/* 21 */     return (String[])getEntries().stream().map(StoredUserEntry::getUser).filter(Objects::nonNull).map(GameProfile::getName).toArray($$0 -> new String[$$0]);
/*    */   }
/*    */   
/*    */   public boolean canBypassPlayerLimit(GameProfile $$0) {
/* 25 */     ServerOpListEntry $$1 = get($$0);
/*    */     
/* 27 */     if ($$1 != null) {
/* 28 */       return $$1.getBypassesPlayerLimit();
/*    */     }
/*    */     
/* 31 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   protected String getKeyForUser(GameProfile $$0) {
/* 36 */     return $$0.getId().toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\players\ServerOpList.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */