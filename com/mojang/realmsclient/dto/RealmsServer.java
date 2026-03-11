/*     */ package com.mojang.realmsclient.dto;
/*     */ 
/*     */ import com.google.common.base.Joiner;
/*     */ import com.google.common.collect.ComparisonChain;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParser;
/*     */ import com.mojang.authlib.minecraft.MinecraftSessionService;
/*     */ import com.mojang.authlib.yggdrasil.ProfileResult;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.realmsclient.util.JsonUtils;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.multiplayer.ServerData;
/*     */ import org.apache.commons.lang3.builder.EqualsBuilder;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RealmsServer
/*     */   extends ValueObject
/*     */ {
/*  35 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private static final int NO_VALUE = -1;
/*     */   public long id;
/*     */   public String remoteSubscriptionId;
/*     */   public String name;
/*     */   public String motd;
/*     */   public State state;
/*     */   public String owner;
/*  44 */   public UUID ownerUUID = Util.NIL_UUID;
/*     */   
/*     */   public List<PlayerInfo> players;
/*     */   
/*     */   public Map<Integer, RealmsWorldOptions> slots;
/*     */   
/*     */   public boolean expired;
/*     */   
/*     */   public boolean expiredTrial;
/*     */   public int daysLeft;
/*     */   public WorldType worldType;
/*     */   public int activeSlot;
/*     */   public String minigameName;
/*     */   public int minigameId;
/*     */   public String minigameImage;
/*  59 */   public long parentWorldId = -1L;
/*     */   @Nullable
/*     */   public String parentWorldName;
/*  62 */   public String activeVersion = "";
/*  63 */   public Compatibility compatibility = Compatibility.UNVERIFIABLE;
/*     */   
/*  65 */   public RealmsServerPing serverPing = new RealmsServerPing();
/*     */   
/*     */   public String getDescription() {
/*  68 */     return this.motd;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  72 */     return this.name;
/*     */   }
/*     */   
/*     */   public String getMinigameName() {
/*  76 */     return this.minigameName;
/*     */   }
/*     */   
/*     */   public void setName(String $$0) {
/*  80 */     this.name = $$0;
/*     */   }
/*     */   
/*     */   public void setDescription(String $$0) {
/*  84 */     this.motd = $$0;
/*     */   }
/*     */   
/*     */   public void updateServerPing(RealmsServerPlayerList $$0) {
/*  88 */     List<String> $$1 = Lists.newArrayList();
/*     */     
/*  90 */     int $$2 = 0;
/*     */     
/*  92 */     MinecraftSessionService $$3 = Minecraft.getInstance().getMinecraftSessionService();
/*  93 */     for (UUID $$4 : $$0.players) {
/*  94 */       if (Minecraft.getInstance().isLocalPlayer($$4)) {
/*     */         continue;
/*     */       }
/*     */       try {
/*  98 */         ProfileResult $$5 = $$3.fetchProfile($$4, false);
/*  99 */         if ($$5 != null) {
/* 100 */           $$1.add($$5.profile().getName());
/*     */         }
/* 102 */         $$2++;
/* 103 */       } catch (Exception $$6) {
/*     */         
/* 105 */         LOGGER.error("Could not get name for {}", $$4, $$6);
/*     */       } 
/*     */     } 
/*     */     
/* 109 */     this.serverPing.nrOfPlayers = String.valueOf($$2);
/* 110 */     this.serverPing.playerList = Joiner.on('\n').join($$1);
/*     */   }
/*     */   
/*     */   public static RealmsServer parse(JsonObject $$0) {
/* 114 */     RealmsServer $$1 = new RealmsServer();
/*     */     try {
/* 116 */       $$1.id = JsonUtils.getLongOr("id", $$0, -1L);
/* 117 */       $$1.remoteSubscriptionId = JsonUtils.getStringOr("remoteSubscriptionId", $$0, null);
/* 118 */       $$1.name = JsonUtils.getStringOr("name", $$0, null);
/* 119 */       $$1.motd = JsonUtils.getStringOr("motd", $$0, null);
/* 120 */       $$1.state = getState(JsonUtils.getStringOr("state", $$0, State.CLOSED.name()));
/* 121 */       $$1.owner = JsonUtils.getStringOr("owner", $$0, null);
/* 122 */       if ($$0.get("players") != null && $$0.get("players").isJsonArray()) {
/* 123 */         $$1.players = parseInvited($$0.get("players").getAsJsonArray());
/* 124 */         sortInvited($$1);
/*     */       } else {
/* 126 */         $$1.players = Lists.newArrayList();
/*     */       } 
/* 128 */       $$1.daysLeft = JsonUtils.getIntOr("daysLeft", $$0, 0);
/* 129 */       $$1.expired = JsonUtils.getBooleanOr("expired", $$0, false);
/* 130 */       $$1.expiredTrial = JsonUtils.getBooleanOr("expiredTrial", $$0, false);
/* 131 */       $$1.worldType = getWorldType(JsonUtils.getStringOr("worldType", $$0, WorldType.NORMAL.name()));
/* 132 */       $$1.ownerUUID = JsonUtils.getUuidOr("ownerUUID", $$0, Util.NIL_UUID);
/* 133 */       if ($$0.get("slots") != null && $$0.get("slots").isJsonArray()) {
/* 134 */         $$1.slots = parseSlots($$0.get("slots").getAsJsonArray());
/*     */       } else {
/* 136 */         $$1.slots = createEmptySlots();
/*     */       } 
/*     */       
/* 139 */       $$1.minigameName = JsonUtils.getStringOr("minigameName", $$0, null);
/* 140 */       $$1.activeSlot = JsonUtils.getIntOr("activeSlot", $$0, -1);
/* 141 */       $$1.minigameId = JsonUtils.getIntOr("minigameId", $$0, -1);
/* 142 */       $$1.minigameImage = JsonUtils.getStringOr("minigameImage", $$0, null);
/* 143 */       $$1.parentWorldId = JsonUtils.getLongOr("parentWorldId", $$0, -1L);
/* 144 */       $$1.parentWorldName = JsonUtils.getStringOr("parentWorldName", $$0, null);
/* 145 */       $$1.activeVersion = JsonUtils.getStringOr("activeVersion", $$0, "");
/* 146 */       $$1.compatibility = getCompatibility(JsonUtils.getStringOr("compatibility", $$0, Compatibility.UNVERIFIABLE.name()));
/* 147 */     } catch (Exception $$2) {
/* 148 */       LOGGER.error("Could not parse McoServer: {}", $$2.getMessage());
/*     */     } 
/* 150 */     return $$1;
/*     */   }
/*     */   
/*     */   private static void sortInvited(RealmsServer $$0) {
/* 154 */     $$0.players.sort(($$0, $$1) -> ComparisonChain.start().compareFalseFirst($$1.getAccepted(), $$0.getAccepted()).compare($$0.getName().toLowerCase(Locale.ROOT), $$1.getName().toLowerCase(Locale.ROOT)).result());
/*     */   }
/*     */   
/*     */   private static List<PlayerInfo> parseInvited(JsonArray $$0) {
/* 158 */     List<PlayerInfo> $$1 = Lists.newArrayList();
/* 159 */     for (JsonElement $$2 : $$0) {
/*     */       try {
/* 161 */         JsonObject $$3 = $$2.getAsJsonObject();
/* 162 */         PlayerInfo $$4 = new PlayerInfo();
/* 163 */         $$4.setName(JsonUtils.getStringOr("name", $$3, null));
/* 164 */         $$4.setUuid(JsonUtils.getUuidOr("uuid", $$3, Util.NIL_UUID));
/* 165 */         $$4.setOperator(JsonUtils.getBooleanOr("operator", $$3, false));
/* 166 */         $$4.setAccepted(JsonUtils.getBooleanOr("accepted", $$3, false));
/* 167 */         $$4.setOnline(JsonUtils.getBooleanOr("online", $$3, false));
/* 168 */         $$1.add($$4);
/* 169 */       } catch (Exception exception) {}
/*     */     } 
/*     */     
/* 172 */     return $$1;
/*     */   }
/*     */   
/*     */   private static Map<Integer, RealmsWorldOptions> parseSlots(JsonArray $$0) {
/* 176 */     Map<Integer, RealmsWorldOptions> $$1 = Maps.newHashMap();
/* 177 */     for (JsonElement $$2 : $$0) {
/*     */       try {
/*     */         RealmsWorldOptions $$7;
/* 180 */         JsonObject $$3 = $$2.getAsJsonObject();
/*     */         
/* 182 */         JsonParser $$4 = new JsonParser();
/* 183 */         JsonElement $$5 = $$4.parse($$3.get("options").getAsString());
/*     */         
/* 185 */         if ($$5 == null) {
/* 186 */           RealmsWorldOptions $$6 = RealmsWorldOptions.createDefaults();
/*     */         } else {
/* 188 */           $$7 = RealmsWorldOptions.parse($$5.getAsJsonObject());
/*     */         } 
/*     */         
/* 191 */         int $$8 = JsonUtils.getIntOr("slotId", $$3, -1);
/*     */         
/* 193 */         $$1.put(Integer.valueOf($$8), $$7);
/* 194 */       } catch (Exception exception) {}
/*     */     } 
/*     */ 
/*     */     
/* 198 */     for (int $$9 = 1; $$9 <= 3; $$9++) {
/* 199 */       if (!$$1.containsKey(Integer.valueOf($$9))) {
/* 200 */         $$1.put(Integer.valueOf($$9), RealmsWorldOptions.createEmptyDefaults());
/*     */       }
/*     */     } 
/*     */     
/* 204 */     return $$1;
/*     */   }
/*     */   
/*     */   private static Map<Integer, RealmsWorldOptions> createEmptySlots() {
/* 208 */     Map<Integer, RealmsWorldOptions> $$0 = Maps.newHashMap();
/* 209 */     $$0.put(Integer.valueOf(1), RealmsWorldOptions.createEmptyDefaults());
/* 210 */     $$0.put(Integer.valueOf(2), RealmsWorldOptions.createEmptyDefaults());
/* 211 */     $$0.put(Integer.valueOf(3), RealmsWorldOptions.createEmptyDefaults());
/*     */     
/* 213 */     return $$0;
/*     */   }
/*     */   
/*     */   public static RealmsServer parse(String $$0) {
/*     */     try {
/* 218 */       return parse((new JsonParser()).parse($$0).getAsJsonObject());
/* 219 */     } catch (Exception $$1) {
/* 220 */       LOGGER.error("Could not parse McoServer: {}", $$1.getMessage());
/*     */       
/* 222 */       return new RealmsServer();
/*     */     } 
/*     */   }
/*     */   private static State getState(String $$0) {
/*     */     try {
/* 227 */       return State.valueOf($$0);
/* 228 */     } catch (Exception $$1) {
/* 229 */       return State.CLOSED;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static WorldType getWorldType(String $$0) {
/*     */     try {
/* 235 */       return WorldType.valueOf($$0);
/* 236 */     } catch (Exception $$1) {
/* 237 */       return WorldType.NORMAL;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static Compatibility getCompatibility(@Nullable String $$0) {
/*     */     try {
/* 243 */       return Compatibility.valueOf($$0);
/* 244 */     } catch (Exception $$1) {
/* 245 */       return Compatibility.UNVERIFIABLE;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isCompatible() {
/* 250 */     return this.compatibility.isCompatible();
/*     */   }
/*     */   
/*     */   public boolean needsUpgrade() {
/* 254 */     return this.compatibility.needsUpgrade();
/*     */   }
/*     */   
/*     */   public boolean needsDowngrade() {
/* 258 */     return this.compatibility.needsDowngrade();
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 263 */     return Objects.hash(new Object[] { Long.valueOf(this.id), this.name, this.motd, this.state, this.owner, Boolean.valueOf(this.expired) });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object $$0) {
/* 268 */     if ($$0 == null) {
/* 269 */       return false;
/*     */     }
/* 271 */     if ($$0 == this) {
/* 272 */       return true;
/*     */     }
/* 274 */     if ($$0.getClass() != getClass()) {
/* 275 */       return false;
/*     */     }
/* 277 */     RealmsServer $$1 = (RealmsServer)$$0;
/*     */     
/* 279 */     return (new EqualsBuilder())
/* 280 */       .append(this.id, $$1.id)
/* 281 */       .append(this.name, $$1.name)
/* 282 */       .append(this.motd, $$1.motd)
/* 283 */       .append(this.state, $$1.state)
/* 284 */       .append(this.owner, $$1.owner)
/* 285 */       .append(this.expired, $$1.expired)
/* 286 */       .append(this.worldType, this.worldType).isEquals();
/*     */   }
/*     */ 
/*     */   
/*     */   public RealmsServer clone() {
/* 291 */     RealmsServer $$0 = new RealmsServer();
/* 292 */     $$0.id = this.id;
/* 293 */     $$0.remoteSubscriptionId = this.remoteSubscriptionId;
/* 294 */     $$0.name = this.name;
/* 295 */     $$0.motd = this.motd;
/* 296 */     $$0.state = this.state;
/* 297 */     $$0.owner = this.owner;
/* 298 */     $$0.players = this.players;
/* 299 */     $$0.slots = cloneSlots(this.slots);
/* 300 */     $$0.expired = this.expired;
/* 301 */     $$0.expiredTrial = this.expiredTrial;
/* 302 */     $$0.daysLeft = this.daysLeft;
/* 303 */     $$0.serverPing = new RealmsServerPing();
/* 304 */     $$0.serverPing.nrOfPlayers = this.serverPing.nrOfPlayers;
/* 305 */     $$0.serverPing.playerList = this.serverPing.playerList;
/* 306 */     $$0.worldType = this.worldType;
/* 307 */     $$0.ownerUUID = this.ownerUUID;
/* 308 */     $$0.minigameName = this.minigameName;
/* 309 */     $$0.activeSlot = this.activeSlot;
/* 310 */     $$0.minigameId = this.minigameId;
/* 311 */     $$0.minigameImage = this.minigameImage;
/* 312 */     $$0.parentWorldName = this.parentWorldName;
/* 313 */     $$0.parentWorldId = this.parentWorldId;
/* 314 */     $$0.activeVersion = this.activeVersion;
/* 315 */     $$0.compatibility = this.compatibility;
/* 316 */     return $$0;
/*     */   }
/*     */   
/*     */   public Map<Integer, RealmsWorldOptions> cloneSlots(Map<Integer, RealmsWorldOptions> $$0) {
/* 320 */     Map<Integer, RealmsWorldOptions> $$1 = Maps.newHashMap();
/*     */     
/* 322 */     for (Map.Entry<Integer, RealmsWorldOptions> $$2 : $$0.entrySet()) {
/* 323 */       $$1.put($$2.getKey(), ((RealmsWorldOptions)$$2.getValue()).clone());
/*     */     }
/*     */     
/* 326 */     return $$1;
/*     */   }
/*     */   
/*     */   public boolean isSnapshotRealm() {
/* 330 */     return (this.parentWorldId != -1L);
/*     */   }
/*     */   
/*     */   public String getWorldName(int $$0) {
/* 334 */     return this.name + " (" + this.name + ")";
/*     */   }
/*     */   
/*     */   public ServerData toServerData(String $$0) {
/* 338 */     return new ServerData(this.name, $$0, ServerData.Type.REALM);
/*     */   }
/*     */   
/*     */   public static class McoServerComparator implements Comparator<RealmsServer> {
/*     */     private final String refOwner;
/*     */     
/*     */     public McoServerComparator(String $$0) {
/* 345 */       this.refOwner = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public int compare(RealmsServer $$0, RealmsServer $$1) {
/* 350 */       return ComparisonChain.start()
/* 351 */         .compareTrueFirst($$0.isSnapshotRealm(), $$1.isSnapshotRealm())
/* 352 */         .compareTrueFirst(($$0.state == RealmsServer.State.UNINITIALIZED), ($$1.state == RealmsServer.State.UNINITIALIZED))
/* 353 */         .compareTrueFirst($$0.expiredTrial, $$1.expiredTrial)
/* 354 */         .compareTrueFirst($$0.owner.equals(this.refOwner), $$1.owner.equals(this.refOwner))
/* 355 */         .compareFalseFirst($$0.expired, $$1.expired)
/* 356 */         .compareTrueFirst(($$0.state == RealmsServer.State.OPEN), ($$1.state == RealmsServer.State.OPEN))
/* 357 */         .compare($$0.id, $$1.id).result();
/*     */     }
/*     */   }
/*     */   
/*     */   public enum State {
/* 362 */     CLOSED,
/* 363 */     OPEN,
/* 364 */     UNINITIALIZED;
/*     */   }
/*     */   
/*     */   public enum WorldType {
/* 368 */     NORMAL,
/* 369 */     MINIGAME,
/* 370 */     ADVENTUREMAP,
/* 371 */     EXPERIENCE,
/* 372 */     INSPIRATION;
/*     */   }
/*     */   
/*     */   public enum Compatibility {
/* 376 */     UNVERIFIABLE,
/* 377 */     INCOMPATIBLE,
/* 378 */     NEEDS_DOWNGRADE,
/* 379 */     NEEDS_UPGRADE,
/* 380 */     COMPATIBLE;
/*     */     
/*     */     public boolean isCompatible() {
/* 383 */       return (this == COMPATIBLE);
/*     */     }
/*     */     
/*     */     public boolean needsUpgrade() {
/* 387 */       return (this == NEEDS_UPGRADE);
/*     */     }
/*     */     
/*     */     public boolean needsDowngrade() {
/* 391 */       return (this == NEEDS_DOWNGRADE);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\dto\RealmsServer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */