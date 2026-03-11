/*     */ package net.minecraft.server.players;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.io.Files;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.authlib.ProfileLookupCallback;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.text.ParseException;
/*     */ import java.util.Collection;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.UUIDUtil;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.server.dedicated.DedicatedServer;
/*     */ import net.minecraft.util.StringUtil;
/*     */ import net.minecraft.world.level.storage.LevelResource;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OldUsersConverter
/*     */ {
/*  32 */   static final Logger LOGGER = LogUtils.getLogger();
/*  33 */   public static final File OLD_IPBANLIST = new File("banned-ips.txt");
/*  34 */   public static final File OLD_USERBANLIST = new File("banned-players.txt");
/*  35 */   public static final File OLD_OPLIST = new File("ops.txt");
/*  36 */   public static final File OLD_WHITELIST = new File("white-list.txt");
/*     */   
/*     */   static List<String> readOldListFormat(File $$0, Map<String, String[]> $$1) throws IOException {
/*  39 */     List<String> $$2 = Files.readLines($$0, StandardCharsets.UTF_8);
/*  40 */     for (String $$3 : $$2) {
/*  41 */       $$3 = $$3.trim();
/*  42 */       if ($$3.startsWith("#") || $$3.length() < 1) {
/*     */         continue;
/*     */       }
/*  45 */       String[] $$4 = $$3.split("\\|");
/*  46 */       $$1.put($$4[0].toLowerCase(Locale.ROOT), $$4);
/*     */     } 
/*  48 */     return $$2;
/*     */   }
/*     */   
/*     */   private static void lookupPlayers(MinecraftServer $$0, Collection<String> $$1, ProfileLookupCallback $$2) {
/*  52 */     String[] $$3 = (String[])$$1.stream().filter($$0 -> !StringUtil.isNullOrEmpty($$0)).toArray($$0 -> new String[$$0]);
/*  53 */     if ($$0.usesAuthentication()) {
/*  54 */       $$0.getProfileRepository().findProfilesByNames($$3, $$2);
/*     */     } else {
/*  56 */       for (String $$4 : $$3) {
/*  57 */         $$2.onProfileLookupSucceeded(UUIDUtil.createOfflineProfile($$4));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean convertUserBanlist(final MinecraftServer server) {
/*  63 */     final UserBanList bans = new UserBanList(PlayerList.USERBANLIST_FILE);
/*  64 */     if (OLD_USERBANLIST.exists() && OLD_USERBANLIST.isFile()) {
/*  65 */       if ($$1.getFile().exists()) {
/*     */         try {
/*  67 */           $$1.load();
/*  68 */         } catch (IOException $$2) {
/*  69 */           LOGGER.warn("Could not load existing file {}", $$1.getFile().getName(), $$2);
/*     */         } 
/*     */       }
/*     */       try {
/*  73 */         final Map<String, String[]> userMap = Maps.newHashMap();
/*  74 */         readOldListFormat(OLD_USERBANLIST, $$3);
/*     */         
/*  76 */         ProfileLookupCallback $$4 = new ProfileLookupCallback()
/*     */           {
/*     */             public void onProfileLookupSucceeded(GameProfile $$0) {
/*  79 */               server.getProfileCache().add($$0);
/*  80 */               String[] $$1 = (String[])userMap.get($$0.getName().toLowerCase(Locale.ROOT));
/*  81 */               if ($$1 == null) {
/*  82 */                 OldUsersConverter.LOGGER.warn("Could not convert user banlist entry for {}", $$0.getName());
/*  83 */                 throw new OldUsersConverter.ConversionError("Profile not in the conversionlist");
/*     */               } 
/*     */               
/*  86 */               Date $$2 = ($$1.length > 1) ? OldUsersConverter.parseDate($$1[1], null) : null;
/*  87 */               String $$3 = ($$1.length > 2) ? $$1[2] : null;
/*  88 */               Date $$4 = ($$1.length > 3) ? OldUsersConverter.parseDate($$1[3], null) : null;
/*  89 */               String $$5 = ($$1.length > 4) ? $$1[4] : null;
/*  90 */               bans.add(new UserBanListEntry($$0, $$2, $$3, $$4, $$5));
/*     */             }
/*     */ 
/*     */             
/*     */             public void onProfileLookupFailed(String $$0, Exception $$1) {
/*  95 */               OldUsersConverter.LOGGER.warn("Could not lookup user banlist entry for {}", $$0, $$1);
/*  96 */               if (!($$1 instanceof com.mojang.authlib.yggdrasil.ProfileNotFoundException)) {
/*  97 */                 throw new OldUsersConverter.ConversionError("Could not request user " + $$0 + " from backend systems", $$1);
/*     */               }
/*     */             }
/*     */           };
/* 101 */         lookupPlayers(server, $$3.keySet(), $$4);
/* 102 */         $$1.save();
/* 103 */         renameOldFile(OLD_USERBANLIST);
/* 104 */       } catch (IOException $$5) {
/* 105 */         LOGGER.warn("Could not read old user banlist to convert it!", $$5);
/* 106 */         return false;
/* 107 */       } catch (ConversionError $$6) {
/* 108 */         LOGGER.error("Conversion failed, please try again later", $$6);
/* 109 */         return false;
/*     */       } 
/* 111 */       return true;
/*     */     } 
/* 113 */     return true;
/*     */   }
/*     */   
/*     */   public static boolean convertIpBanlist(MinecraftServer $$0) {
/* 117 */     IpBanList $$1 = new IpBanList(PlayerList.IPBANLIST_FILE);
/* 118 */     if (OLD_IPBANLIST.exists() && OLD_IPBANLIST.isFile()) {
/* 119 */       if ($$1.getFile().exists()) {
/*     */         try {
/* 121 */           $$1.load();
/* 122 */         } catch (IOException $$2) {
/* 123 */           LOGGER.warn("Could not load existing file {}", $$1.getFile().getName(), $$2);
/*     */         } 
/*     */       }
/*     */       try {
/* 127 */         Map<String, String[]> $$3 = Maps.newHashMap();
/* 128 */         readOldListFormat(OLD_IPBANLIST, $$3);
/*     */         
/* 130 */         for (String $$4 : $$3.keySet()) {
/* 131 */           String[] $$5 = $$3.get($$4);
/* 132 */           Date $$6 = ($$5.length > 1) ? parseDate($$5[1], null) : null;
/* 133 */           String $$7 = ($$5.length > 2) ? $$5[2] : null;
/* 134 */           Date $$8 = ($$5.length > 3) ? parseDate($$5[3], null) : null;
/* 135 */           String $$9 = ($$5.length > 4) ? $$5[4] : null;
/* 136 */           $$1.add(new IpBanListEntry($$4, $$6, $$7, $$8, $$9));
/*     */         } 
/* 138 */         $$1.save();
/* 139 */         renameOldFile(OLD_IPBANLIST);
/* 140 */       } catch (IOException $$10) {
/* 141 */         LOGGER.warn("Could not parse old ip banlist to convert it!", $$10);
/* 142 */         return false;
/*     */       } 
/* 144 */       return true;
/*     */     } 
/* 146 */     return true;
/*     */   }
/*     */   
/*     */   public static boolean convertOpsList(final MinecraftServer server) {
/* 150 */     final ServerOpList opsList = new ServerOpList(PlayerList.OPLIST_FILE);
/* 151 */     if (OLD_OPLIST.exists() && OLD_OPLIST.isFile()) {
/* 152 */       if ($$1.getFile().exists()) {
/*     */         try {
/* 154 */           $$1.load();
/* 155 */         } catch (IOException $$2) {
/* 156 */           LOGGER.warn("Could not load existing file {}", $$1.getFile().getName(), $$2);
/*     */         } 
/*     */       }
/*     */       try {
/* 160 */         List<String> $$3 = Files.readLines(OLD_OPLIST, StandardCharsets.UTF_8);
/* 161 */         ProfileLookupCallback $$4 = new ProfileLookupCallback()
/*     */           {
/*     */             public void onProfileLookupSucceeded(GameProfile $$0) {
/* 164 */               server.getProfileCache().add($$0);
/* 165 */               opsList.add(new ServerOpListEntry($$0, server.getOperatorUserPermissionLevel(), false));
/*     */             }
/*     */ 
/*     */             
/*     */             public void onProfileLookupFailed(String $$0, Exception $$1) {
/* 170 */               OldUsersConverter.LOGGER.warn("Could not lookup oplist entry for {}", $$0, $$1);
/* 171 */               if (!($$1 instanceof com.mojang.authlib.yggdrasil.ProfileNotFoundException)) {
/* 172 */                 throw new OldUsersConverter.ConversionError("Could not request user " + $$0 + " from backend systems", $$1);
/*     */               }
/*     */             }
/*     */           };
/* 176 */         lookupPlayers(server, $$3, $$4);
/* 177 */         $$1.save();
/* 178 */         renameOldFile(OLD_OPLIST);
/* 179 */       } catch (IOException $$5) {
/* 180 */         LOGGER.warn("Could not read old oplist to convert it!", $$5);
/* 181 */         return false;
/* 182 */       } catch (ConversionError $$6) {
/* 183 */         LOGGER.error("Conversion failed, please try again later", $$6);
/* 184 */         return false;
/*     */       } 
/* 186 */       return true;
/*     */     } 
/* 188 */     return true;
/*     */   }
/*     */   
/*     */   public static boolean convertWhiteList(final MinecraftServer server) {
/* 192 */     final UserWhiteList whitelist = new UserWhiteList(PlayerList.WHITELIST_FILE);
/* 193 */     if (OLD_WHITELIST.exists() && OLD_WHITELIST.isFile()) {
/* 194 */       if ($$1.getFile().exists()) {
/*     */         try {
/* 196 */           $$1.load();
/* 197 */         } catch (IOException $$2) {
/* 198 */           LOGGER.warn("Could not load existing file {}", $$1.getFile().getName(), $$2);
/*     */         } 
/*     */       }
/*     */       try {
/* 202 */         List<String> $$3 = Files.readLines(OLD_WHITELIST, StandardCharsets.UTF_8);
/* 203 */         ProfileLookupCallback $$4 = new ProfileLookupCallback()
/*     */           {
/*     */             public void onProfileLookupSucceeded(GameProfile $$0) {
/* 206 */               server.getProfileCache().add($$0);
/* 207 */               whitelist.add(new UserWhiteListEntry($$0));
/*     */             }
/*     */ 
/*     */             
/*     */             public void onProfileLookupFailed(String $$0, Exception $$1) {
/* 212 */               OldUsersConverter.LOGGER.warn("Could not lookup user whitelist entry for {}", $$0, $$1);
/* 213 */               if (!($$1 instanceof com.mojang.authlib.yggdrasil.ProfileNotFoundException)) {
/* 214 */                 throw new OldUsersConverter.ConversionError("Could not request user " + $$0 + " from backend systems", $$1);
/*     */               }
/*     */             }
/*     */           };
/* 218 */         lookupPlayers(server, $$3, $$4);
/* 219 */         $$1.save();
/* 220 */         renameOldFile(OLD_WHITELIST);
/* 221 */       } catch (IOException $$5) {
/* 222 */         LOGGER.warn("Could not read old whitelist to convert it!", $$5);
/* 223 */         return false;
/* 224 */       } catch (ConversionError $$6) {
/* 225 */         LOGGER.error("Conversion failed, please try again later", $$6);
/* 226 */         return false;
/*     */       } 
/* 228 */       return true;
/*     */     } 
/* 230 */     return true;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static UUID convertMobOwnerIfNecessary(final MinecraftServer server, String $$1) {
/* 235 */     if (StringUtil.isNullOrEmpty($$1) || $$1.length() > 16) {
/*     */       try {
/* 237 */         return UUID.fromString($$1);
/* 238 */       } catch (IllegalArgumentException $$2) {
/* 239 */         return null;
/*     */       } 
/*     */     }
/*     */     
/* 243 */     Optional<UUID> $$3 = server.getProfileCache().get($$1).map(GameProfile::getId);
/* 244 */     if ($$3.isPresent()) {
/* 245 */       return $$3.get();
/*     */     }
/* 247 */     if (server.isSingleplayer() || !server.usesAuthentication()) {
/* 248 */       return UUIDUtil.createOfflinePlayerUUID($$1);
/*     */     }
/* 250 */     final List<GameProfile> profiles = Lists.newArrayList();
/* 251 */     ProfileLookupCallback $$5 = new ProfileLookupCallback()
/*     */       {
/*     */         public void onProfileLookupSucceeded(GameProfile $$0) {
/* 254 */           server.getProfileCache().add($$0);
/* 255 */           profiles.add($$0);
/*     */         }
/*     */ 
/*     */         
/*     */         public void onProfileLookupFailed(String $$0, Exception $$1) {
/* 260 */           OldUsersConverter.LOGGER.warn("Could not lookup user whitelist entry for {}", $$0, $$1);
/*     */         }
/*     */       };
/* 263 */     lookupPlayers(server, Lists.newArrayList((Object[])new String[] { $$1 }, ), $$5);
/* 264 */     if (!$$4.isEmpty()) {
/* 265 */       return ((GameProfile)$$4.get(0)).getId();
/*     */     }
/*     */     
/* 268 */     return null;
/*     */   }
/*     */   
/*     */   private static class ConversionError extends RuntimeException {
/*     */     ConversionError(String $$0, Throwable $$1) {
/* 273 */       super($$0, $$1);
/*     */     }
/*     */     
/*     */     ConversionError(String $$0) {
/* 277 */       super($$0);
/*     */     }
/*     */   }
/*     */   
/*     */   public static boolean convertPlayers(final DedicatedServer server) {
/* 282 */     final File worldPlayerDirectory = getWorldPlayersDirectory((MinecraftServer)server);
/* 283 */     final File worldNewPlayerDirectory = new File($$1.getParentFile(), "playerdata");
/* 284 */     final File unknownPlayerDirectory = new File($$1.getParentFile(), "unknownplayers");
/* 285 */     if (!$$1.exists() || !$$1.isDirectory()) {
/* 286 */       return true;
/*     */     }
/* 288 */     File[] $$4 = $$1.listFiles();
/* 289 */     List<String> $$5 = Lists.newArrayList();
/* 290 */     for (File $$6 : $$4) {
/* 291 */       String $$7 = $$6.getName();
/* 292 */       if ($$7.toLowerCase(Locale.ROOT).endsWith(".dat")) {
/*     */ 
/*     */         
/* 295 */         String $$8 = $$7.substring(0, $$7.length() - ".dat".length());
/* 296 */         if (!$$8.isEmpty()) {
/* 297 */           $$5.add($$8);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     try {
/* 302 */       final String[] names = $$5.<String>toArray(new String[$$5.size()]);
/* 303 */       ProfileLookupCallback $$10 = new ProfileLookupCallback()
/*     */         {
/*     */           public void onProfileLookupSucceeded(GameProfile $$0) {
/* 306 */             server.getProfileCache().add($$0);
/* 307 */             UUID $$1 = $$0.getId();
/* 308 */             movePlayerFile(worldNewPlayerDirectory, getFileNameForProfile($$0.getName()), $$1.toString());
/*     */           }
/*     */ 
/*     */           
/*     */           public void onProfileLookupFailed(String $$0, Exception $$1) {
/* 313 */             OldUsersConverter.LOGGER.warn("Could not lookup user uuid for {}", $$0, $$1);
/* 314 */             if ($$1 instanceof com.mojang.authlib.yggdrasil.ProfileNotFoundException) {
/* 315 */               String $$2 = getFileNameForProfile($$0);
/* 316 */               movePlayerFile(unknownPlayerDirectory, $$2, $$2);
/*     */             } else {
/* 318 */               throw new OldUsersConverter.ConversionError("Could not request user " + $$0 + " from backend systems", $$1);
/*     */             } 
/*     */           }
/*     */           
/*     */           private void movePlayerFile(File $$0, String $$1, String $$2) {
/* 323 */             File $$3 = new File(worldPlayerDirectory, $$1 + ".dat");
/* 324 */             File $$4 = new File($$0, $$2 + ".dat");
/* 325 */             OldUsersConverter.ensureDirectoryExists($$0);
/* 326 */             if (!$$3.renameTo($$4)) {
/* 327 */               throw new OldUsersConverter.ConversionError("Could not convert file for " + $$1);
/*     */             }
/*     */           }
/*     */           
/*     */           private String getFileNameForProfile(String $$0) {
/* 332 */             String $$1 = null;
/* 333 */             for (String $$2 : names) {
/* 334 */               if ($$2 != null && $$2.equalsIgnoreCase($$0)) {
/* 335 */                 $$1 = $$2;
/*     */                 break;
/*     */               } 
/*     */             } 
/* 339 */             if ($$1 == null) {
/* 340 */               throw new OldUsersConverter.ConversionError("Could not find the filename for " + $$0 + " anymore");
/*     */             }
/* 342 */             return $$1;
/*     */           }
/*     */         };
/* 345 */       lookupPlayers((MinecraftServer)server, Lists.newArrayList((Object[])$$9), $$10);
/* 346 */     } catch (ConversionError $$11) {
/* 347 */       LOGGER.error("Conversion failed, please try again later", $$11);
/* 348 */       return false;
/*     */     } 
/*     */     
/* 351 */     return true;
/*     */   }
/*     */   
/*     */   static void ensureDirectoryExists(File $$0) {
/* 355 */     if ($$0.exists()) {
/* 356 */       if ($$0.isDirectory()) {
/*     */         return;
/*     */       }
/* 359 */       throw new ConversionError("Can't create directory " + $$0.getName() + " in world save directory.");
/*     */     } 
/*     */     
/* 362 */     if (!$$0.mkdirs()) {
/* 363 */       throw new ConversionError("Can't create directory " + $$0.getName() + " in world save directory.");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean serverReadyAfterUserconversion(MinecraftServer $$0) {
/* 369 */     boolean $$1 = areOldUserlistsRemoved();
/* 370 */     $$1 = ($$1 && areOldPlayersConverted($$0));
/* 371 */     return $$1;
/*     */   }
/*     */   
/*     */   private static boolean areOldUserlistsRemoved() {
/* 375 */     boolean $$0 = false;
/* 376 */     if (OLD_USERBANLIST.exists() && OLD_USERBANLIST.isFile()) {
/* 377 */       $$0 = true;
/*     */     }
/* 379 */     boolean $$1 = false;
/* 380 */     if (OLD_IPBANLIST.exists() && OLD_IPBANLIST.isFile()) {
/* 381 */       $$1 = true;
/*     */     }
/* 383 */     boolean $$2 = false;
/* 384 */     if (OLD_OPLIST.exists() && OLD_OPLIST.isFile()) {
/* 385 */       $$2 = true;
/*     */     }
/* 387 */     boolean $$3 = false;
/* 388 */     if (OLD_WHITELIST.exists() && OLD_WHITELIST.isFile()) {
/* 389 */       $$3 = true;
/*     */     }
/*     */     
/* 392 */     if ($$0 || $$1 || $$2 || $$3) {
/* 393 */       LOGGER.warn("**** FAILED TO START THE SERVER AFTER ACCOUNT CONVERSION!");
/* 394 */       LOGGER.warn("** please remove the following files and restart the server:");
/* 395 */       if ($$0) {
/* 396 */         LOGGER.warn("* {}", OLD_USERBANLIST.getName());
/*     */       }
/* 398 */       if ($$1) {
/* 399 */         LOGGER.warn("* {}", OLD_IPBANLIST.getName());
/*     */       }
/* 401 */       if ($$2) {
/* 402 */         LOGGER.warn("* {}", OLD_OPLIST.getName());
/*     */       }
/* 404 */       if ($$3) {
/* 405 */         LOGGER.warn("* {}", OLD_WHITELIST.getName());
/*     */       }
/* 407 */       return false;
/*     */     } 
/* 409 */     return true;
/*     */   }
/*     */   
/*     */   private static boolean areOldPlayersConverted(MinecraftServer $$0) {
/* 413 */     File $$1 = getWorldPlayersDirectory($$0);
/* 414 */     if ($$1.exists() && $$1.isDirectory() && ((
/* 415 */       $$1.list()).length > 0 || !$$1.delete())) {
/* 416 */       LOGGER.warn("**** DETECTED OLD PLAYER DIRECTORY IN THE WORLD SAVE");
/* 417 */       LOGGER.warn("**** THIS USUALLY HAPPENS WHEN THE AUTOMATIC CONVERSION FAILED IN SOME WAY");
/* 418 */       LOGGER.warn("** please restart the server and if the problem persists, remove the directory '{}'", $$1.getPath());
/* 419 */       return false;
/*     */     } 
/*     */     
/* 422 */     return true;
/*     */   }
/*     */   
/*     */   private static File getWorldPlayersDirectory(MinecraftServer $$0) {
/* 426 */     return $$0.getWorldPath(LevelResource.PLAYER_OLD_DATA_DIR).toFile();
/*     */   }
/*     */   
/*     */   private static void renameOldFile(File $$0) {
/* 430 */     File $$1 = new File($$0.getName() + ".converted");
/* 431 */     $$0.renameTo($$1);
/*     */   }
/*     */   
/*     */   static Date parseDate(String $$0, Date $$1) {
/*     */     Date $$4;
/*     */     try {
/* 437 */       Date $$2 = BanListEntry.DATE_FORMAT.parse($$0);
/* 438 */     } catch (ParseException $$3) {
/* 439 */       $$4 = $$1;
/*     */     } 
/* 441 */     return $$4;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\players\OldUsersConverter.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */