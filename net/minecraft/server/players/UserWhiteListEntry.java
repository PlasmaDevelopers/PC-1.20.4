/*    */ package net.minecraft.server.players;
/*    */ 
/*    */ import com.google.gson.JsonObject;
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import java.util.UUID;
/*    */ 
/*    */ public class UserWhiteListEntry
/*    */   extends StoredUserEntry<GameProfile> {
/*    */   public UserWhiteListEntry(GameProfile $$0) {
/* 10 */     super($$0);
/*    */   }
/*    */   
/*    */   public UserWhiteListEntry(JsonObject $$0) {
/* 14 */     super(createGameProfile($$0));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void serialize(JsonObject $$0) {
/* 19 */     if (getUser() == null) {
/*    */       return;
/*    */     }
/* 22 */     $$0.addProperty("uuid", (getUser().getId() == null) ? "" : getUser().getId().toString());
/* 23 */     $$0.addProperty("name", getUser().getName());
/*    */   }
/*    */   private static GameProfile createGameProfile(JsonObject $$0) {
/*    */     UUID $$2;
/* 27 */     if (!$$0.has("uuid") || !$$0.has("name")) {
/* 28 */       return null;
/*    */     }
/* 30 */     String $$1 = $$0.get("uuid").getAsString();
/*    */     
/*    */     try {
/* 33 */       $$2 = UUID.fromString($$1);
/* 34 */     } catch (Throwable $$3) {
/* 35 */       return null;
/*    */     } 
/* 37 */     return new GameProfile($$2, $$0.get("name").getAsString());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\players\UserWhiteListEntry.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */