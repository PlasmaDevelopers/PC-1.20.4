/*    */ package net.minecraft.server.players;
/*    */ 
/*    */ import com.google.gson.JsonObject;
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import java.util.Date;
/*    */ import java.util.UUID;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.network.chat.Component;
/*    */ 
/*    */ public class UserBanListEntry
/*    */   extends BanListEntry<GameProfile> {
/*    */   public UserBanListEntry(@Nullable GameProfile $$0) {
/* 13 */     this($$0, null, null, null, null);
/*    */   }
/*    */   
/*    */   public UserBanListEntry(@Nullable GameProfile $$0, @Nullable Date $$1, @Nullable String $$2, @Nullable Date $$3, @Nullable String $$4) {
/* 17 */     super($$0, $$1, $$2, $$3, $$4);
/*    */   }
/*    */   
/*    */   public UserBanListEntry(JsonObject $$0) {
/* 21 */     super(createGameProfile($$0), $$0);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void serialize(JsonObject $$0) {
/* 26 */     if (getUser() == null) {
/*    */       return;
/*    */     }
/* 29 */     $$0.addProperty("uuid", getUser().getId().toString());
/* 30 */     $$0.addProperty("name", getUser().getName());
/* 31 */     super.serialize($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public Component getDisplayName() {
/* 36 */     GameProfile $$0 = getUser();
/* 37 */     return ($$0 != null) ? (Component)Component.literal($$0.getName()) : (Component)Component.translatable("commands.banlist.entry.unknown");
/*    */   }
/*    */   @Nullable
/*    */   private static GameProfile createGameProfile(JsonObject $$0) {
/*    */     UUID $$2;
/* 42 */     if (!$$0.has("uuid") || !$$0.has("name")) {
/* 43 */       return null;
/*    */     }
/* 45 */     String $$1 = $$0.get("uuid").getAsString();
/*    */     
/*    */     try {
/* 48 */       $$2 = UUID.fromString($$1);
/* 49 */     } catch (Throwable $$3) {
/* 50 */       return null;
/*    */     } 
/* 52 */     return new GameProfile($$2, $$0.get("name").getAsString());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\players\UserBanListEntry.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */