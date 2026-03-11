/*    */ package net.minecraft.server.players;
/*    */ 
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import com.mojang.authlib.ProfileLookupCallback;
/*    */ import java.util.Date;
/*    */ import java.util.Locale;
/*    */ import java.util.Map;
/*    */ import net.minecraft.server.MinecraftServer;
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
/*    */ 
/*    */ 
/*    */ class null
/*    */   implements ProfileLookupCallback
/*    */ {
/*    */   public void onProfileLookupSucceeded(GameProfile $$0) {
/* 79 */     server.getProfileCache().add($$0);
/* 80 */     String[] $$1 = (String[])userMap.get($$0.getName().toLowerCase(Locale.ROOT));
/* 81 */     if ($$1 == null) {
/* 82 */       OldUsersConverter.LOGGER.warn("Could not convert user banlist entry for {}", $$0.getName());
/* 83 */       throw new OldUsersConverter.ConversionError("Profile not in the conversionlist");
/*    */     } 
/*    */     
/* 86 */     Date $$2 = ($$1.length > 1) ? OldUsersConverter.parseDate($$1[1], null) : null;
/* 87 */     String $$3 = ($$1.length > 2) ? $$1[2] : null;
/* 88 */     Date $$4 = ($$1.length > 3) ? OldUsersConverter.parseDate($$1[3], null) : null;
/* 89 */     String $$5 = ($$1.length > 4) ? $$1[4] : null;
/* 90 */     bans.add(new UserBanListEntry($$0, $$2, $$3, $$4, $$5));
/*    */   }
/*    */ 
/*    */   
/*    */   public void onProfileLookupFailed(String $$0, Exception $$1) {
/* 95 */     OldUsersConverter.LOGGER.warn("Could not lookup user banlist entry for {}", $$0, $$1);
/* 96 */     if (!($$1 instanceof com.mojang.authlib.yggdrasil.ProfileNotFoundException))
/* 97 */       throw new OldUsersConverter.ConversionError("Could not request user " + $$0 + " from backend systems", $$1); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\players\OldUsersConverter$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */