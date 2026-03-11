/*     */ package com.mojang.realmsclient.client;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.realmsclient.RealmsMainScreen;
/*     */ import com.mojang.realmsclient.dto.BackupList;
/*     */ import com.mojang.realmsclient.dto.GuardedSerializer;
/*     */ import com.mojang.realmsclient.dto.Ops;
/*     */ import com.mojang.realmsclient.dto.PendingInvite;
/*     */ import com.mojang.realmsclient.dto.PendingInvitesList;
/*     */ import com.mojang.realmsclient.dto.PingResult;
/*     */ import com.mojang.realmsclient.dto.PlayerInfo;
/*     */ import com.mojang.realmsclient.dto.RealmsDescriptionDto;
/*     */ import com.mojang.realmsclient.dto.RealmsNews;
/*     */ import com.mojang.realmsclient.dto.RealmsNotification;
/*     */ import com.mojang.realmsclient.dto.RealmsServer;
/*     */ import com.mojang.realmsclient.dto.RealmsServerAddress;
/*     */ import com.mojang.realmsclient.dto.RealmsServerList;
/*     */ import com.mojang.realmsclient.dto.RealmsServerPlayerLists;
/*     */ import com.mojang.realmsclient.dto.RealmsWorldOptions;
/*     */ import com.mojang.realmsclient.dto.RealmsWorldResetDto;
/*     */ import com.mojang.realmsclient.dto.ReflectionBasedSerialization;
/*     */ import com.mojang.realmsclient.dto.ServerActivityList;
/*     */ import com.mojang.realmsclient.dto.Subscription;
/*     */ import com.mojang.realmsclient.dto.UploadInfo;
/*     */ import com.mojang.realmsclient.dto.WorldDownload;
/*     */ import com.mojang.realmsclient.dto.WorldTemplatePaginatedList;
/*     */ import com.mojang.realmsclient.exception.RealmsHttpException;
/*     */ import com.mojang.realmsclient.exception.RealmsServiceException;
/*     */ import com.mojang.realmsclient.exception.RetryCallException;
/*     */ import com.mojang.realmsclient.util.WorldGenerationInfo;
/*     */ import com.mojang.util.UndashedUuid;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.SharedConstants;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class RealmsClient {
/*     */   public enum Environment {
/*  47 */     PRODUCTION("pc.realms.minecraft.net", "https"),
/*  48 */     STAGE("pc-stage.realms.minecraft.net", "https"),
/*  49 */     LOCAL("localhost:8080", "http");
/*     */     
/*     */     public final String baseUrl;
/*     */     public final String protocol;
/*     */     
/*     */     Environment(String $$0, String $$1) {
/*  55 */       this.baseUrl = $$0;
/*  56 */       this.protocol = $$1;
/*     */     }
/*     */     
/*     */     public static Optional<Environment> byName(String $$0) {
/*  60 */       switch ($$0.toLowerCase(Locale.ROOT)) { case "production": case "local": case "stage": case "staging":  }  return 
/*     */ 
/*     */ 
/*     */         
/*  64 */         Optional.empty();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*  69 */   public static final Environment ENVIRONMENT = Optional.<String>ofNullable(System.getenv("realms.environment"))
/*  70 */     .or(() -> Optional.ofNullable(System.getProperty("realms.environment")))
/*  71 */     .flatMap(Environment::byName)
/*  72 */     .orElse(Environment.PRODUCTION);
/*     */   
/*  74 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private final String sessionId;
/*     */   
/*     */   private final String username;
/*     */   
/*     */   private final Minecraft minecraft;
/*     */   
/*     */   private static final String WORLDS_RESOURCE_PATH = "worlds";
/*     */   private static final String INVITES_RESOURCE_PATH = "invites";
/*     */   private static final String MCO_RESOURCE_PATH = "mco";
/*     */   private static final String SUBSCRIPTION_RESOURCE = "subscriptions";
/*     */   private static final String ACTIVITIES_RESOURCE = "activities";
/*     */   private static final String OPS_RESOURCE = "ops";
/*     */   private static final String REGIONS_RESOURCE = "regions/ping/stat";
/*     */   private static final String TRIALS_RESOURCE = "trial";
/*     */   private static final String NOTIFICATIONS_RESOURCE = "notifications";
/*     */   private static final String PATH_LIST_ALL_REALMS = "/listUserWorldsOfType/any";
/*     */   private static final String PATH_CREATE_SNAPSHOT_REALM = "/$PARENT_WORLD_ID/createPrereleaseRealm";
/*     */   private static final String PATH_SNAPSHOT_ELIGIBLE_REALMS = "/listPrereleaseEligibleWorlds";
/*     */   private static final String PATH_INITIALIZE = "/$WORLD_ID/initialize";
/*     */   private static final String PATH_GET_ACTIVTIES = "/$WORLD_ID";
/*     */   private static final String PATH_GET_LIVESTATS = "/liveplayerlist";
/*     */   private static final String PATH_GET_SUBSCRIPTION = "/$WORLD_ID";
/*     */   private static final String PATH_OP = "/$WORLD_ID/$PROFILE_UUID";
/*     */   private static final String PATH_PUT_INTO_MINIGAMES_MODE = "/minigames/$MINIGAME_ID/$WORLD_ID";
/*     */   private static final String PATH_AVAILABLE = "/available";
/*     */   private static final String PATH_TEMPLATES = "/templates/$WORLD_TYPE";
/*     */   private static final String PATH_WORLD_JOIN = "/v1/$ID/join/pc";
/*     */   private static final String PATH_WORLD_GET = "/$ID";
/*     */   private static final String PATH_WORLD_INVITES = "/$WORLD_ID";
/*     */   private static final String PATH_WORLD_UNINVITE = "/$WORLD_ID/invite/$UUID";
/*     */   private static final String PATH_PENDING_INVITES_COUNT = "/count/pending";
/*     */   private static final String PATH_PENDING_INVITES = "/pending";
/*     */   private static final String PATH_ACCEPT_INVITE = "/accept/$INVITATION_ID";
/*     */   private static final String PATH_REJECT_INVITE = "/reject/$INVITATION_ID";
/*     */   private static final String PATH_UNINVITE_MYSELF = "/$WORLD_ID";
/*     */   private static final String PATH_WORLD_UPDATE = "/$WORLD_ID";
/*     */   private static final String PATH_SLOT = "/$WORLD_ID/slot/$SLOT_ID";
/*     */   private static final String PATH_WORLD_OPEN = "/$WORLD_ID/open";
/*     */   private static final String PATH_WORLD_CLOSE = "/$WORLD_ID/close";
/*     */   private static final String PATH_WORLD_RESET = "/$WORLD_ID/reset";
/*     */   private static final String PATH_DELETE_WORLD = "/$WORLD_ID";
/*     */   private static final String PATH_WORLD_BACKUPS = "/$WORLD_ID/backups";
/*     */   private static final String PATH_WORLD_DOWNLOAD = "/$WORLD_ID/slot/$SLOT_ID/download";
/*     */   private static final String PATH_WORLD_UPLOAD = "/$WORLD_ID/backups/upload";
/*     */   private static final String PATH_CLIENT_COMPATIBLE = "/client/compatible";
/*     */   private static final String PATH_TOS_AGREED = "/tos/agreed";
/*     */   private static final String PATH_NEWS = "/v1/news";
/*     */   private static final String PATH_MARK_NOTIFICATIONS_SEEN = "/seen";
/*     */   private static final String PATH_DISMISS_NOTIFICATIONS = "/dismiss";
/* 125 */   private static final GuardedSerializer GSON = new GuardedSerializer();
/*     */   
/*     */   public static RealmsClient create() {
/* 128 */     Minecraft $$0 = Minecraft.getInstance();
/* 129 */     return create($$0);
/*     */   }
/*     */   
/*     */   public static RealmsClient create(Minecraft $$0) {
/* 133 */     String $$1 = $$0.getUser().getName();
/* 134 */     String $$2 = $$0.getUser().getSessionId();
/* 135 */     return new RealmsClient($$2, $$1, $$0);
/*     */   }
/*     */   
/*     */   public RealmsClient(String $$0, String $$1, Minecraft $$2) {
/* 139 */     this.sessionId = $$0;
/* 140 */     this.username = $$1;
/* 141 */     this.minecraft = $$2;
/*     */     
/* 143 */     RealmsClientConfig.setProxy($$2.getProxy());
/*     */   }
/*     */   
/*     */   public RealmsServerList listWorlds() throws RealmsServiceException {
/* 147 */     String $$0 = url("worlds");
/* 148 */     if (RealmsMainScreen.isSnapshot()) {
/* 149 */       $$0 = $$0 + "/listUserWorldsOfType/any";
/*     */     }
/* 151 */     String $$1 = execute(Request.get($$0));
/* 152 */     return RealmsServerList.parse($$1);
/*     */   }
/*     */   
/*     */   public List<RealmsServer> listSnapshotEligibleRealms() throws RealmsServiceException {
/* 156 */     String $$0 = url("worlds/listPrereleaseEligibleWorlds");
/* 157 */     String $$1 = execute(Request.get($$0));
/* 158 */     return (RealmsServerList.parse($$1)).servers;
/*     */   }
/*     */   
/*     */   public RealmsServer createSnapshotRealm(Long $$0) throws RealmsServiceException {
/* 162 */     String $$1 = String.valueOf($$0);
/* 163 */     String $$2 = url("worlds" + "/$PARENT_WORLD_ID/createPrereleaseRealm".replace("$PARENT_WORLD_ID", $$1));
/* 164 */     return RealmsServer.parse(execute(Request.post($$2, $$1)));
/*     */   }
/*     */   
/*     */   public List<RealmsNotification> getNotifications() throws RealmsServiceException {
/* 168 */     String $$0 = url("notifications");
/* 169 */     String $$1 = execute(Request.get($$0));
/* 170 */     return RealmsNotification.parseList($$1);
/*     */   }
/*     */   
/*     */   private static JsonArray uuidListToJsonArray(List<UUID> $$0) {
/* 174 */     JsonArray $$1 = new JsonArray();
/* 175 */     for (UUID $$2 : $$0) {
/* 176 */       if ($$2 != null) {
/* 177 */         $$1.add($$2.toString());
/*     */       }
/*     */     } 
/* 180 */     return $$1;
/*     */   }
/*     */   
/*     */   public void notificationsSeen(List<UUID> $$0) throws RealmsServiceException {
/* 184 */     String $$1 = url("notifications/seen");
/* 185 */     execute(Request.post($$1, GSON.toJson((JsonElement)uuidListToJsonArray($$0))));
/*     */   }
/*     */   
/*     */   public void notificationsDismiss(List<UUID> $$0) throws RealmsServiceException {
/* 189 */     String $$1 = url("notifications/dismiss");
/* 190 */     execute(Request.post($$1, GSON.toJson((JsonElement)uuidListToJsonArray($$0))));
/*     */   }
/*     */   
/*     */   public RealmsServer getOwnWorld(long $$0) throws RealmsServiceException {
/* 194 */     String $$1 = url("worlds" + "/$ID".replace("$ID", String.valueOf($$0)));
/* 195 */     String $$2 = execute(Request.get($$1));
/* 196 */     return RealmsServer.parse($$2);
/*     */   }
/*     */   
/*     */   public ServerActivityList getActivity(long $$0) throws RealmsServiceException {
/* 200 */     String $$1 = url("activities" + "/$WORLD_ID".replace("$WORLD_ID", String.valueOf($$0)));
/* 201 */     String $$2 = execute(Request.get($$1));
/* 202 */     return ServerActivityList.parse($$2);
/*     */   }
/*     */   
/*     */   public RealmsServerPlayerLists getLiveStats() throws RealmsServiceException {
/* 206 */     String $$0 = url("activities/liveplayerlist");
/* 207 */     String $$1 = execute(Request.get($$0));
/* 208 */     return RealmsServerPlayerLists.parse($$1);
/*     */   }
/*     */   
/*     */   public RealmsServerAddress join(long $$0) throws RealmsServiceException {
/* 212 */     String $$1 = url("worlds" + "/v1/$ID/join/pc".replace("$ID", "" + $$0));
/* 213 */     String $$2 = execute(Request.get($$1, 5000, 30000));
/* 214 */     return RealmsServerAddress.parse($$2);
/*     */   }
/*     */   
/*     */   public void initializeWorld(long $$0, String $$1, String $$2) throws RealmsServiceException {
/* 218 */     RealmsDescriptionDto $$3 = new RealmsDescriptionDto($$1, $$2);
/* 219 */     String $$4 = url("worlds" + "/$WORLD_ID/initialize".replace("$WORLD_ID", String.valueOf($$0)));
/* 220 */     String $$5 = GSON.toJson((ReflectionBasedSerialization)$$3);
/* 221 */     execute(Request.post($$4, $$5, 5000, 10000));
/*     */   }
/*     */   
/*     */   public boolean hasParentalConsent() throws RealmsServiceException {
/* 225 */     String $$0 = url("mco/available");
/* 226 */     String $$1 = execute(Request.get($$0));
/* 227 */     return Boolean.parseBoolean($$1);
/*     */   }
/*     */   public CompatibleVersionResponse clientCompatible() throws RealmsServiceException {
/*     */     CompatibleVersionResponse $$2;
/* 231 */     String $$0 = url("mco/client/compatible");
/* 232 */     String $$1 = execute(Request.get($$0));
/*     */ 
/*     */     
/*     */     try {
/* 236 */       $$2 = CompatibleVersionResponse.valueOf($$1);
/* 237 */     } catch (IllegalArgumentException $$3) {
/* 238 */       throw new RealmsServiceException(RealmsError.CustomError.unknownCompatibilityResponse($$1));
/*     */     } 
/*     */     
/* 241 */     return $$2;
/*     */   }
/*     */   
/*     */   public void uninvite(long $$0, UUID $$1) throws RealmsServiceException {
/* 245 */     String $$2 = url("invites" + "/$WORLD_ID/invite/$UUID".replace("$WORLD_ID", String.valueOf($$0)).replace("$UUID", UndashedUuid.toString($$1)));
/* 246 */     execute(Request.delete($$2));
/*     */   }
/*     */   
/*     */   public void uninviteMyselfFrom(long $$0) throws RealmsServiceException {
/* 250 */     String $$1 = url("invites" + "/$WORLD_ID".replace("$WORLD_ID", String.valueOf($$0)));
/* 251 */     execute(Request.delete($$1));
/*     */   }
/*     */   
/*     */   public RealmsServer invite(long $$0, String $$1) throws RealmsServiceException {
/* 255 */     PlayerInfo $$2 = new PlayerInfo();
/* 256 */     $$2.setName($$1);
/*     */     
/* 258 */     String $$3 = url("invites" + "/$WORLD_ID".replace("$WORLD_ID", String.valueOf($$0)));
/* 259 */     String $$4 = execute(Request.post($$3, GSON.toJson((ReflectionBasedSerialization)$$2)));
/* 260 */     return RealmsServer.parse($$4);
/*     */   }
/*     */   
/*     */   public BackupList backupsFor(long $$0) throws RealmsServiceException {
/* 264 */     String $$1 = url("worlds" + "/$WORLD_ID/backups".replace("$WORLD_ID", String.valueOf($$0)));
/* 265 */     String $$2 = execute(Request.get($$1));
/* 266 */     return BackupList.parse($$2);
/*     */   }
/*     */   
/*     */   public void update(long $$0, String $$1, String $$2) throws RealmsServiceException {
/* 270 */     RealmsDescriptionDto $$3 = new RealmsDescriptionDto($$1, $$2);
/* 271 */     String $$4 = url("worlds" + "/$WORLD_ID".replace("$WORLD_ID", String.valueOf($$0)));
/* 272 */     execute(Request.post($$4, GSON.toJson((ReflectionBasedSerialization)$$3)));
/*     */   }
/*     */   
/*     */   public void updateSlot(long $$0, int $$1, RealmsWorldOptions $$2) throws RealmsServiceException {
/* 276 */     String $$3 = url("worlds" + "/$WORLD_ID/slot/$SLOT_ID".replace("$WORLD_ID", String.valueOf($$0)).replace("$SLOT_ID", String.valueOf($$1)));
/* 277 */     String $$4 = $$2.toJson();
/* 278 */     execute(Request.post($$3, $$4));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean switchSlot(long $$0, int $$1) throws RealmsServiceException {
/* 283 */     String $$2 = url("worlds" + "/$WORLD_ID/slot/$SLOT_ID".replace("$WORLD_ID", String.valueOf($$0)).replace("$SLOT_ID", String.valueOf($$1)));
/* 284 */     String $$3 = execute(Request.put($$2, ""));
/* 285 */     return Boolean.valueOf($$3).booleanValue();
/*     */   }
/*     */   
/*     */   public void restoreWorld(long $$0, String $$1) throws RealmsServiceException {
/* 289 */     String $$2 = url("worlds" + "/$WORLD_ID/backups".replace("$WORLD_ID", String.valueOf($$0)), "backupId=" + $$1);
/* 290 */     execute(Request.put($$2, "", 40000, 600000));
/*     */   }
/*     */   
/*     */   public WorldTemplatePaginatedList fetchWorldTemplates(int $$0, int $$1, RealmsServer.WorldType $$2) throws RealmsServiceException {
/* 294 */     String $$3 = url("worlds" + "/templates/$WORLD_TYPE".replace("$WORLD_TYPE", $$2.toString()), String.format(Locale.ROOT, "page=%d&pageSize=%d", new Object[] { Integer.valueOf($$0), Integer.valueOf($$1) }));
/* 295 */     String $$4 = execute(Request.get($$3));
/* 296 */     return WorldTemplatePaginatedList.parse($$4);
/*     */   }
/*     */   
/*     */   public Boolean putIntoMinigameMode(long $$0, String $$1) throws RealmsServiceException {
/* 300 */     String $$2 = "/minigames/$MINIGAME_ID/$WORLD_ID".replace("$MINIGAME_ID", $$1).replace("$WORLD_ID", String.valueOf($$0));
/* 301 */     String $$3 = url("worlds" + $$2);
/* 302 */     return Boolean.valueOf(execute(Request.put($$3, "")));
/*     */   }
/*     */   
/*     */   public Ops op(long $$0, UUID $$1) throws RealmsServiceException {
/* 306 */     String $$2 = "/$WORLD_ID/$PROFILE_UUID".replace("$WORLD_ID", String.valueOf($$0)).replace("$PROFILE_UUID", UndashedUuid.toString($$1));
/* 307 */     String $$3 = url("ops" + $$2);
/* 308 */     return Ops.parse(execute(Request.post($$3, "")));
/*     */   }
/*     */   
/*     */   public Ops deop(long $$0, UUID $$1) throws RealmsServiceException {
/* 312 */     String $$2 = "/$WORLD_ID/$PROFILE_UUID".replace("$WORLD_ID", String.valueOf($$0)).replace("$PROFILE_UUID", UndashedUuid.toString($$1));
/* 313 */     String $$3 = url("ops" + $$2);
/* 314 */     return Ops.parse(execute(Request.delete($$3)));
/*     */   }
/*     */   
/*     */   public Boolean open(long $$0) throws RealmsServiceException {
/* 318 */     String $$1 = url("worlds" + "/$WORLD_ID/open".replace("$WORLD_ID", String.valueOf($$0)));
/* 319 */     String $$2 = execute(Request.put($$1, ""));
/* 320 */     return Boolean.valueOf($$2);
/*     */   }
/*     */   
/*     */   public Boolean close(long $$0) throws RealmsServiceException {
/* 324 */     String $$1 = url("worlds" + "/$WORLD_ID/close".replace("$WORLD_ID", String.valueOf($$0)));
/* 325 */     String $$2 = execute(Request.put($$1, ""));
/* 326 */     return Boolean.valueOf($$2);
/*     */   }
/*     */   
/*     */   public Boolean resetWorldWithSeed(long $$0, WorldGenerationInfo $$1) throws RealmsServiceException {
/* 330 */     RealmsWorldResetDto $$2 = new RealmsWorldResetDto($$1.seed(), -1L, $$1.levelType().getDtoIndex(), $$1.generateStructures(), $$1.experiments());
/* 331 */     String $$3 = url("worlds" + "/$WORLD_ID/reset".replace("$WORLD_ID", String.valueOf($$0)));
/* 332 */     String $$4 = execute(Request.post($$3, GSON.toJson((ReflectionBasedSerialization)$$2), 30000, 80000));
/* 333 */     return Boolean.valueOf($$4);
/*     */   }
/*     */   
/*     */   public Boolean resetWorldWithTemplate(long $$0, String $$1) throws RealmsServiceException {
/* 337 */     RealmsWorldResetDto $$2 = new RealmsWorldResetDto(null, Long.valueOf($$1).longValue(), -1, false, Set.of());
/* 338 */     String $$3 = url("worlds" + "/$WORLD_ID/reset".replace("$WORLD_ID", String.valueOf($$0)));
/* 339 */     String $$4 = execute(Request.post($$3, GSON.toJson((ReflectionBasedSerialization)$$2), 30000, 80000));
/* 340 */     return Boolean.valueOf($$4);
/*     */   }
/*     */   
/*     */   public Subscription subscriptionFor(long $$0) throws RealmsServiceException {
/* 344 */     String $$1 = url("subscriptions" + "/$WORLD_ID".replace("$WORLD_ID", String.valueOf($$0)));
/* 345 */     String $$2 = execute(Request.get($$1));
/* 346 */     return Subscription.parse($$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public int pendingInvitesCount() throws RealmsServiceException {
/* 351 */     return (pendingInvites()).pendingInvites.size();
/*     */   }
/*     */   
/*     */   public PendingInvitesList pendingInvites() throws RealmsServiceException {
/* 355 */     String $$0 = url("invites/pending");
/* 356 */     String $$1 = execute(Request.get($$0));
/* 357 */     PendingInvitesList $$2 = PendingInvitesList.parse($$1);
/* 358 */     $$2.pendingInvites.removeIf(this::isBlocked);
/* 359 */     return $$2;
/*     */   }
/*     */   
/*     */   private boolean isBlocked(PendingInvite $$0) {
/* 363 */     return this.minecraft.getPlayerSocialManager().isBlocked($$0.worldOwnerUuid);
/*     */   }
/*     */   
/*     */   public void acceptInvitation(String $$0) throws RealmsServiceException {
/* 367 */     String $$1 = url("invites" + "/accept/$INVITATION_ID".replace("$INVITATION_ID", $$0));
/* 368 */     execute(Request.put($$1, ""));
/*     */   }
/*     */   
/*     */   public WorldDownload requestDownloadInfo(long $$0, int $$1) throws RealmsServiceException {
/* 372 */     String $$2 = url("worlds" + "/$WORLD_ID/slot/$SLOT_ID/download".replace("$WORLD_ID", String.valueOf($$0)).replace("$SLOT_ID", String.valueOf($$1)));
/* 373 */     String $$3 = execute(Request.get($$2));
/* 374 */     return WorldDownload.parse($$3);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public UploadInfo requestUploadInfo(long $$0, @Nullable String $$1) throws RealmsServiceException {
/* 379 */     String $$2 = url("worlds" + "/$WORLD_ID/backups/upload".replace("$WORLD_ID", String.valueOf($$0)));
/* 380 */     return UploadInfo.parse(execute(Request.put($$2, UploadInfo.createRequest($$1))));
/*     */   }
/*     */   
/*     */   public void rejectInvitation(String $$0) throws RealmsServiceException {
/* 384 */     String $$1 = url("invites" + "/reject/$INVITATION_ID".replace("$INVITATION_ID", $$0));
/* 385 */     execute(Request.put($$1, ""));
/*     */   }
/*     */   
/*     */   public void agreeToTos() throws RealmsServiceException {
/* 389 */     String $$0 = url("mco/tos/agreed");
/* 390 */     execute(Request.post($$0, ""));
/*     */   }
/*     */   
/*     */   public RealmsNews getNews() throws RealmsServiceException {
/* 394 */     String $$0 = url("mco/v1/news");
/* 395 */     String $$1 = execute(Request.get($$0, 5000, 10000));
/* 396 */     return RealmsNews.parse($$1);
/*     */   }
/*     */   
/*     */   public void sendPingResults(PingResult $$0) throws RealmsServiceException {
/* 400 */     String $$1 = url("regions/ping/stat");
/* 401 */     execute(Request.post($$1, GSON.toJson((ReflectionBasedSerialization)$$0)));
/*     */   }
/*     */   
/*     */   public Boolean trialAvailable() throws RealmsServiceException {
/* 405 */     String $$0 = url("trial");
/* 406 */     String $$1 = execute(Request.get($$0));
/* 407 */     return Boolean.valueOf($$1);
/*     */   }
/*     */   
/*     */   public void deleteWorld(long $$0) throws RealmsServiceException {
/* 411 */     String $$1 = url("worlds" + "/$WORLD_ID".replace("$WORLD_ID", String.valueOf($$0)));
/* 412 */     execute(Request.delete($$1));
/*     */   }
/*     */   
/*     */   private String url(String $$0) {
/* 416 */     return url($$0, null);
/*     */   }
/*     */   
/*     */   private String url(String $$0, @Nullable String $$1) {
/*     */     try {
/* 421 */       return (new URI(ENVIRONMENT.protocol, ENVIRONMENT.baseUrl, "/" + $$0, $$1, null)).toASCIIString();
/* 422 */     } catch (URISyntaxException $$2) {
/* 423 */       throw new IllegalArgumentException($$0, $$2);
/*     */     } 
/*     */   }
/*     */   
/*     */   private String execute(Request<?> $$0) throws RealmsServiceException {
/* 428 */     $$0.cookie("sid", this.sessionId);
/* 429 */     $$0.cookie("user", this.username);
/* 430 */     $$0.cookie("version", SharedConstants.getCurrentVersion().getName());
/* 431 */     $$0.addSnapshotHeader(RealmsMainScreen.isSnapshot());
/*     */     
/*     */     try {
/* 434 */       int $$1 = $$0.responseCode();
/*     */       
/* 436 */       if ($$1 == 503 || $$1 == 277) {
/* 437 */         int $$2 = $$0.getRetryAfterHeader();
/* 438 */         throw new RetryCallException($$2, $$1);
/*     */       } 
/*     */       
/* 441 */       String $$3 = $$0.text();
/*     */       
/* 443 */       if ($$1 < 200 || $$1 >= 300) {
/* 444 */         if ($$1 == 401) {
/* 445 */           String $$4 = $$0.getHeader("WWW-Authenticate");
/* 446 */           LOGGER.info("Could not authorize you against Realms server: {}", $$4);
/* 447 */           throw new RealmsServiceException(new RealmsError.AuthenticationError($$4));
/*     */         } 
/*     */         
/* 450 */         RealmsError $$5 = RealmsError.parse($$1, $$3);
/* 451 */         throw new RealmsServiceException($$5);
/*     */       } 
/*     */       
/* 454 */       return $$3;
/* 455 */     } catch (RealmsHttpException $$6) {
/* 456 */       throw new RealmsServiceException(RealmsError.CustomError.connectivityError($$6));
/*     */     } 
/*     */   }
/*     */   
/*     */   public enum CompatibleVersionResponse {
/* 461 */     COMPATIBLE,
/* 462 */     OUTDATED,
/* 463 */     OTHER;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\client\RealmsClient.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */