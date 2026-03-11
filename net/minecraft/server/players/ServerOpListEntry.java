/*    */ package net.minecraft.server.players;
/*    */ 
/*    */ import com.google.gson.JsonObject;
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import java.util.UUID;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class ServerOpListEntry
/*    */   extends StoredUserEntry<GameProfile>
/*    */ {
/*    */   private final int level;
/*    */   private final boolean bypassesPlayerLimit;
/*    */   
/*    */   public ServerOpListEntry(GameProfile $$0, int $$1, boolean $$2) {
/* 15 */     super($$0);
/* 16 */     this.level = $$1;
/* 17 */     this.bypassesPlayerLimit = $$2;
/*    */   }
/*    */   
/*    */   public ServerOpListEntry(JsonObject $$0) {
/* 21 */     super(createGameProfile($$0));
/* 22 */     this.level = $$0.has("level") ? $$0.get("level").getAsInt() : 0;
/* 23 */     this.bypassesPlayerLimit = ($$0.has("bypassesPlayerLimit") && $$0.get("bypassesPlayerLimit").getAsBoolean());
/*    */   }
/*    */   
/*    */   public int getLevel() {
/* 27 */     return this.level;
/*    */   }
/*    */   
/*    */   public boolean getBypassesPlayerLimit() {
/* 31 */     return this.bypassesPlayerLimit;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void serialize(JsonObject $$0) {
/* 36 */     if (getUser() == null) {
/*    */       return;
/*    */     }
/* 39 */     $$0.addProperty("uuid", getUser().getId().toString());
/* 40 */     $$0.addProperty("name", getUser().getName());
/* 41 */     $$0.addProperty("level", Integer.valueOf(this.level));
/* 42 */     $$0.addProperty("bypassesPlayerLimit", Boolean.valueOf(this.bypassesPlayerLimit));
/*    */   }
/*    */   @Nullable
/*    */   private static GameProfile createGameProfile(JsonObject $$0) {
/*    */     UUID $$2;
/* 47 */     if (!$$0.has("uuid") || !$$0.has("name")) {
/* 48 */       return null;
/*    */     }
/* 50 */     String $$1 = $$0.get("uuid").getAsString();
/*    */     
/*    */     try {
/* 53 */       $$2 = UUID.fromString($$1);
/* 54 */     } catch (Throwable $$3) {
/* 55 */       return null;
/*    */     } 
/* 57 */     return new GameProfile($$2, $$0.get("name").getAsString());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\players\ServerOpListEntry.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */