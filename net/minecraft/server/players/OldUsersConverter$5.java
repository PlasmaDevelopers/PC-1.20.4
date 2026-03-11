/*     */ package net.minecraft.server.players;
/*     */ 
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.authlib.ProfileLookupCallback;
/*     */ import java.io.File;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.server.dedicated.DedicatedServer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class null
/*     */   implements ProfileLookupCallback
/*     */ {
/*     */   public void onProfileLookupSucceeded(GameProfile $$0) {
/* 306 */     server.getProfileCache().add($$0);
/* 307 */     UUID $$1 = $$0.getId();
/* 308 */     movePlayerFile(worldNewPlayerDirectory, getFileNameForProfile($$0.getName()), $$1.toString());
/*     */   }
/*     */ 
/*     */   
/*     */   public void onProfileLookupFailed(String $$0, Exception $$1) {
/* 313 */     OldUsersConverter.LOGGER.warn("Could not lookup user uuid for {}", $$0, $$1);
/* 314 */     if ($$1 instanceof com.mojang.authlib.yggdrasil.ProfileNotFoundException) {
/* 315 */       String $$2 = getFileNameForProfile($$0);
/* 316 */       movePlayerFile(unknownPlayerDirectory, $$2, $$2);
/*     */     } else {
/* 318 */       throw new OldUsersConverter.ConversionError("Could not request user " + $$0 + " from backend systems", $$1);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void movePlayerFile(File $$0, String $$1, String $$2) {
/* 323 */     File $$3 = new File(worldPlayerDirectory, $$1 + ".dat");
/* 324 */     File $$4 = new File($$0, $$2 + ".dat");
/* 325 */     OldUsersConverter.ensureDirectoryExists($$0);
/* 326 */     if (!$$3.renameTo($$4)) {
/* 327 */       throw new OldUsersConverter.ConversionError("Could not convert file for " + $$1);
/*     */     }
/*     */   }
/*     */   
/*     */   private String getFileNameForProfile(String $$0) {
/* 332 */     String $$1 = null;
/* 333 */     for (String $$2 : names) {
/* 334 */       if ($$2 != null && $$2.equalsIgnoreCase($$0)) {
/* 335 */         $$1 = $$2;
/*     */         break;
/*     */       } 
/*     */     } 
/* 339 */     if ($$1 == null) {
/* 340 */       throw new OldUsersConverter.ConversionError("Could not find the filename for " + $$0 + " anymore");
/*     */     }
/* 342 */     return $$1;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\players\OldUsersConverter$5.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */