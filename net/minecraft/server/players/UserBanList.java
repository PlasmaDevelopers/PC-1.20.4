/*    */ package net.minecraft.server.players;
/*    */ 
/*    */ import com.google.gson.JsonObject;
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import java.io.File;
/*    */ import java.util.Objects;
/*    */ 
/*    */ public class UserBanList
/*    */   extends StoredUserList<GameProfile, UserBanListEntry> {
/*    */   public UserBanList(File $$0) {
/* 11 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   protected StoredUserEntry<GameProfile> createEntry(JsonObject $$0) {
/* 16 */     return new UserBanListEntry($$0);
/*    */   }
/*    */   
/*    */   public boolean isBanned(GameProfile $$0) {
/* 20 */     return contains($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getUserList() {
/* 25 */     return (String[])getEntries().stream().map(StoredUserEntry::getUser).filter(Objects::nonNull).map(GameProfile::getName).toArray($$0 -> new String[$$0]);
/*    */   }
/*    */ 
/*    */   
/*    */   protected String getKeyForUser(GameProfile $$0) {
/* 30 */     return $$0.getId().toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\players\UserBanList.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */