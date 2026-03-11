/*     */ package net.minecraft.server.players;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.serialization.Dynamic;
/*     */ import com.mojang.serialization.DynamicOps;
/*     */ import java.io.File;
/*     */ import java.net.SocketAddress;
/*     */ import java.nio.file.Path;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.time.Instant;
/*     */ import java.util.Collection;
/*     */ import java.util.EnumSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Predicate;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.FileUtil;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.LayeredRegistryAccess;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.NbtOps;
/*     */ import net.minecraft.network.Connection;
/*     */ import net.minecraft.network.chat.ChatType;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.network.chat.OutgoingChatMessage;
/*     */ import net.minecraft.network.chat.PlayerChatMessage;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.network.protocol.common.ClientboundUpdateTagsPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundChangeDifficultyPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundEntityEventPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundInitializeBorderPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundLoginPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundPlayerAbilitiesPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundPlayerInfoRemovePacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundRespawnPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundSetBorderCenterPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundSetBorderLerpSizePacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundSetBorderSizePacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundSetBorderWarningDelayPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundSetBorderWarningDistancePacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundSetCarriedItemPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundSetChunkCacheRadiusPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundSetDefaultSpawnPositionPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundSetExperiencePacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundSetPlayerTeamPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundSetSimulationDistancePacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundSetTimePacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundSoundPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundUpdateRecipesPacket;
/*     */ import net.minecraft.network.protocol.status.ServerStatus;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.server.PlayerAdvancements;
/*     */ import net.minecraft.server.RegistryLayer;
/*     */ import net.minecraft.server.ServerScoreboard;
/*     */ import net.minecraft.server.level.ClientInformation;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.server.network.CommonListenerCookie;
/*     */ import net.minecraft.server.network.ServerGamePacketListenerImpl;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.stats.ServerStatsCounter;
/*     */ import net.minecraft.stats.Stats;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.tags.TagNetworkSerialization;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.effect.MobEffectInstance;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.level.GameRules;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.border.BorderChangeListener;
/*     */ import net.minecraft.world.level.border.WorldBorder;
/*     */ import net.minecraft.world.level.dimension.DimensionType;
/*     */ import net.minecraft.world.level.storage.LevelData;
/*     */ import net.minecraft.world.level.storage.LevelResource;
/*     */ import net.minecraft.world.level.storage.PlayerDataStorage;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import net.minecraft.world.scores.DisplaySlot;
/*     */ import net.minecraft.world.scores.Objective;
/*     */ import net.minecraft.world.scores.PlayerTeam;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ 
/*     */ public abstract class PlayerList
/*     */ {
/* 108 */   public static final File USERBANLIST_FILE = new File("banned-players.json");
/* 109 */   public static final File IPBANLIST_FILE = new File("banned-ips.json");
/* 110 */   public static final File OPLIST_FILE = new File("ops.json");
/* 111 */   public static final File WHITELIST_FILE = new File("whitelist.json");
/* 112 */   public static final Component CHAT_FILTERED_FULL = (Component)Component.translatable("chat.filtered_full");
/* 113 */   public static final Component DUPLICATE_LOGIN_DISCONNECT_MESSAGE = (Component)Component.translatable("multiplayer.disconnect.duplicate_login");
/* 114 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   private static final int SEND_PLAYER_INFO_INTERVAL = 600;
/* 116 */   private static final SimpleDateFormat BAN_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
/*     */   
/*     */   private final MinecraftServer server;
/* 119 */   private final List<ServerPlayer> players = Lists.newArrayList();
/* 120 */   private final Map<UUID, ServerPlayer> playersByUUID = Maps.newHashMap();
/* 121 */   private final UserBanList bans = new UserBanList(USERBANLIST_FILE);
/* 122 */   private final IpBanList ipBans = new IpBanList(IPBANLIST_FILE);
/* 123 */   private final ServerOpList ops = new ServerOpList(OPLIST_FILE);
/* 124 */   private final UserWhiteList whitelist = new UserWhiteList(WHITELIST_FILE);
/* 125 */   private final Map<UUID, ServerStatsCounter> stats = Maps.newHashMap();
/* 126 */   private final Map<UUID, PlayerAdvancements> advancements = Maps.newHashMap();
/*     */   private final PlayerDataStorage playerIo;
/*     */   private boolean doWhiteList;
/*     */   private final LayeredRegistryAccess<RegistryLayer> registries;
/*     */   protected final int maxPlayers;
/*     */   private int viewDistance;
/*     */   private int simulationDistance;
/*     */   private boolean allowCheatsForAllPlayers;
/*     */   private static final boolean ALLOW_LOGOUTIVATOR = false;
/*     */   private int sendAllPlayerInfoIn;
/*     */   
/* 137 */   public PlayerList(MinecraftServer $$0, LayeredRegistryAccess<RegistryLayer> $$1, PlayerDataStorage $$2, int $$3) { this.server = $$0;
/* 138 */     this.registries = $$1;
/* 139 */     this.maxPlayers = $$3;
/* 140 */     this.playerIo = $$2; } public void placeNewPlayer(Connection $$0, ServerPlayer $$1, CommonListenerCookie $$2) {
/*     */     String $$7;
/*     */     ServerLevel $$12;
/*     */     MutableComponent $$21;
/* 144 */     GameProfile $$3 = $$1.getGameProfile();
/*     */     
/* 146 */     GameProfileCache $$4 = this.server.getProfileCache();
/*     */     
/* 148 */     if ($$4 != null) {
/* 149 */       Optional<GameProfile> $$5 = $$4.get($$3.getId());
/* 150 */       String $$6 = $$5.<String>map(GameProfile::getName).orElse($$3.getName());
/* 151 */       $$4.add($$3);
/*     */     } else {
/* 153 */       $$7 = $$3.getName();
/*     */     } 
/*     */     
/* 156 */     CompoundTag $$8 = load($$1);
/*     */     
/* 158 */     Objects.requireNonNull(LOGGER); ResourceKey<Level> $$9 = ($$8 != null) ? DimensionType.parseLegacy(new Dynamic((DynamicOps)NbtOps.INSTANCE, $$8.get("Dimension"))).resultOrPartial(LOGGER::error).orElse(Level.OVERWORLD) : Level.OVERWORLD;
/* 159 */     ServerLevel $$10 = this.server.getLevel($$9);
/*     */     
/* 161 */     if ($$10 == null) {
/* 162 */       LOGGER.warn("Unknown respawn dimension {}, defaulting to overworld", $$9);
/* 163 */       ServerLevel $$11 = this.server.overworld();
/*     */     } else {
/* 165 */       $$12 = $$10;
/*     */     } 
/* 167 */     $$1.setServerLevel($$12);
/*     */     
/* 169 */     String $$13 = $$0.getLoggableAddress(this.server.logIPs());
/* 170 */     LOGGER.info("{}[{}] logged in with entity id {} at ({}, {}, {})", new Object[] { $$1.getName().getString(), $$13, Integer.valueOf($$1.getId()), Double.valueOf($$1.getX()), Double.valueOf($$1.getY()), Double.valueOf($$1.getZ()) });
/*     */     
/* 172 */     LevelData $$14 = $$12.getLevelData();
/*     */ 
/*     */     
/* 175 */     $$1.loadGameTypes($$8);
/*     */     
/* 177 */     ServerGamePacketListenerImpl $$15 = new ServerGamePacketListenerImpl(this.server, $$0, $$1, $$2);
/* 178 */     GameRules $$16 = $$12.getGameRules();
/* 179 */     boolean $$17 = $$16.getBoolean(GameRules.RULE_DO_IMMEDIATE_RESPAWN);
/* 180 */     boolean $$18 = $$16.getBoolean(GameRules.RULE_REDUCEDDEBUGINFO);
/* 181 */     boolean $$19 = $$16.getBoolean(GameRules.RULE_LIMITED_CRAFTING);
/* 182 */     $$15.send((Packet)new ClientboundLoginPacket($$1
/* 183 */           .getId(), $$14
/* 184 */           .isHardcore(), this.server
/* 185 */           .levelKeys(), 
/* 186 */           getMaxPlayers(), this.viewDistance, this.simulationDistance, $$18, !$$17, $$19, $$1
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 192 */           .createCommonSpawnInfo($$12)));
/*     */     
/* 194 */     $$15.send((Packet)new ClientboundChangeDifficultyPacket($$14.getDifficulty(), $$14.isDifficultyLocked()));
/* 195 */     $$15.send((Packet)new ClientboundPlayerAbilitiesPacket($$1.getAbilities()));
/* 196 */     $$15.send((Packet)new ClientboundSetCarriedItemPacket(($$1.getInventory()).selected));
/* 197 */     $$15.send((Packet)new ClientboundUpdateRecipesPacket(this.server.getRecipeManager().getRecipes()));
/* 198 */     sendPlayerPermissionLevel($$1);
/*     */     
/* 200 */     $$1.getStats().markAllDirty();
/*     */     
/* 202 */     $$1.getRecipeBook().sendInitialRecipeBook($$1);
/*     */     
/* 204 */     updateEntireScoreboard($$12.getScoreboard(), $$1);
/*     */     
/* 206 */     this.server.invalidateStatus();
/*     */     
/* 208 */     if ($$1.getGameProfile().getName().equalsIgnoreCase($$7)) {
/* 209 */       MutableComponent $$20 = Component.translatable("multiplayer.player.joined", new Object[] { $$1.getDisplayName() });
/*     */     } else {
/* 211 */       $$21 = Component.translatable("multiplayer.player.joined.renamed", new Object[] { $$1.getDisplayName(), $$7 });
/*     */     } 
/* 213 */     broadcastSystemMessage((Component)$$21.withStyle(ChatFormatting.YELLOW), false);
/* 214 */     $$15.teleport($$1.getX(), $$1.getY(), $$1.getZ(), $$1.getYRot(), $$1.getXRot());
/*     */     
/* 216 */     ServerStatus $$22 = this.server.getStatus();
/* 217 */     if ($$22 != null) {
/* 218 */       $$1.sendServerStatus($$22);
/*     */     }
/* 220 */     $$1.connection.send((Packet)ClientboundPlayerInfoUpdatePacket.createPlayerInitializing(this.players));
/*     */     
/* 222 */     this.players.add($$1);
/* 223 */     this.playersByUUID.put($$1.getUUID(), $$1);
/*     */     
/* 225 */     broadcastAll((Packet<?>)ClientboundPlayerInfoUpdatePacket.createPlayerInitializing(List.of($$1)));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 235 */     sendLevelInfo($$1, $$12);
/*     */ 
/*     */     
/* 238 */     $$12.addNewPlayer($$1);
/*     */     
/* 240 */     this.server.getCustomBossEvents().onPlayerConnect($$1);
/*     */     
/* 242 */     for (MobEffectInstance $$23 : $$1.getActiveEffects()) {
/* 243 */       $$15.send((Packet)new ClientboundUpdateMobEffectPacket($$1.getId(), $$23));
/*     */     }
/*     */     
/* 246 */     if ($$8 != null && $$8.contains("RootVehicle", 10)) {
/* 247 */       CompoundTag $$24 = $$8.getCompound("RootVehicle");
/* 248 */       Entity $$25 = EntityType.loadEntityRecursive($$24.getCompound("Entity"), (Level)$$12, $$1 -> !$$0.addWithUUID($$1) ? null : $$1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 254 */       if ($$25 != null) {
/*     */         UUID $$27;
/* 256 */         if ($$24.hasUUID("Attach")) {
/* 257 */           UUID $$26 = $$24.getUUID("Attach");
/*     */         } else {
/* 259 */           $$27 = null;
/*     */         } 
/* 261 */         if ($$25.getUUID().equals($$27)) {
/* 262 */           $$1.startRiding($$25, true);
/*     */         } else {
/* 264 */           for (Entity $$28 : $$25.getIndirectPassengers()) {
/* 265 */             if ($$28.getUUID().equals($$27)) {
/* 266 */               $$1.startRiding($$28, true);
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/* 271 */         if (!$$1.isPassenger()) {
/* 272 */           LOGGER.warn("Couldn't reattach entity to player");
/* 273 */           $$25.discard();
/* 274 */           for (Entity $$29 : $$25.getIndirectPassengers()) {
/* 275 */             $$29.discard();
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 281 */     $$1.initInventoryMenu();
/*     */   }
/*     */   
/*     */   protected void updateEntireScoreboard(ServerScoreboard $$0, ServerPlayer $$1) {
/* 285 */     Set<Objective> $$2 = Sets.newHashSet();
/*     */     
/* 287 */     for (PlayerTeam $$3 : $$0.getPlayerTeams()) {
/* 288 */       $$1.connection.send((Packet)ClientboundSetPlayerTeamPacket.createAddOrModifyPacket($$3, true));
/*     */     }
/*     */     
/* 291 */     for (DisplaySlot $$4 : DisplaySlot.values()) {
/* 292 */       Objective $$5 = $$0.getDisplayObjective($$4);
/*     */       
/* 294 */       if ($$5 != null && !$$2.contains($$5)) {
/* 295 */         List<Packet<?>> $$6 = $$0.getStartTrackingPackets($$5);
/*     */         
/* 297 */         for (Packet<?> $$7 : $$6) {
/* 298 */           $$1.connection.send($$7);
/*     */         }
/*     */         
/* 301 */         $$2.add($$5);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addWorldborderListener(ServerLevel $$0) {
/* 307 */     $$0.getWorldBorder().addListener(new BorderChangeListener()
/*     */         {
/*     */           public void onBorderSizeSet(WorldBorder $$0, double $$1) {
/* 310 */             PlayerList.this.broadcastAll((Packet<?>)new ClientboundSetBorderSizePacket($$0));
/*     */           }
/*     */ 
/*     */           
/*     */           public void onBorderSizeLerping(WorldBorder $$0, double $$1, double $$2, long $$3) {
/* 315 */             PlayerList.this.broadcastAll((Packet<?>)new ClientboundSetBorderLerpSizePacket($$0));
/*     */           }
/*     */ 
/*     */           
/*     */           public void onBorderCenterSet(WorldBorder $$0, double $$1, double $$2) {
/* 320 */             PlayerList.this.broadcastAll((Packet<?>)new ClientboundSetBorderCenterPacket($$0));
/*     */           }
/*     */ 
/*     */           
/*     */           public void onBorderSetWarningTime(WorldBorder $$0, int $$1) {
/* 325 */             PlayerList.this.broadcastAll((Packet<?>)new ClientboundSetBorderWarningDelayPacket($$0));
/*     */           }
/*     */ 
/*     */           
/*     */           public void onBorderSetWarningBlocks(WorldBorder $$0, int $$1) {
/* 330 */             PlayerList.this.broadcastAll((Packet<?>)new ClientboundSetBorderWarningDistancePacket($$0));
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public void onBorderSetDamagePerBlock(WorldBorder $$0, double $$1) {}
/*     */ 
/*     */           
/*     */           public void onBorderSetDamageSafeZOne(WorldBorder $$0, double $$1) {}
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public CompoundTag load(ServerPlayer $$0) {
/* 345 */     CompoundTag $$3, $$1 = this.server.getWorldData().getLoadedPlayerTag();
/*     */ 
/*     */     
/* 348 */     if (this.server.isSingleplayerOwner($$0.getGameProfile()) && $$1 != null) {
/* 349 */       CompoundTag $$2 = $$1;
/* 350 */       $$0.load($$2);
/* 351 */       LOGGER.debug("loading single player");
/*     */     } else {
/* 353 */       $$3 = this.playerIo.load((Player)$$0);
/*     */     } 
/* 355 */     return $$3;
/*     */   }
/*     */   
/*     */   protected void save(ServerPlayer $$0) {
/* 359 */     this.playerIo.save((Player)$$0);
/* 360 */     ServerStatsCounter $$1 = this.stats.get($$0.getUUID());
/* 361 */     if ($$1 != null) {
/* 362 */       $$1.save();
/*     */     }
/* 364 */     PlayerAdvancements $$2 = this.advancements.get($$0.getUUID());
/* 365 */     if ($$2 != null) {
/* 366 */       $$2.save();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void remove(ServerPlayer $$0) {
/* 376 */     ServerLevel $$1 = $$0.serverLevel();
/* 377 */     $$0.awardStat(Stats.LEAVE_GAME);
/* 378 */     save($$0);
/* 379 */     if ($$0.isPassenger()) {
/* 380 */       Entity $$2 = $$0.getRootVehicle();
/* 381 */       if ($$2.hasExactlyOnePlayerPassenger()) {
/* 382 */         LOGGER.debug("Removing player mount");
/* 383 */         $$0.stopRiding();
/* 384 */         $$2.getPassengersAndSelf().forEach($$0 -> $$0.setRemoved(Entity.RemovalReason.UNLOADED_WITH_PLAYER));
/*     */       } 
/*     */     } 
/* 387 */     $$0.unRide();
/* 388 */     $$1.removePlayerImmediately($$0, Entity.RemovalReason.UNLOADED_WITH_PLAYER);
/*     */     
/* 390 */     $$0.getAdvancements().stopListening();
/* 391 */     this.players.remove($$0);
/* 392 */     this.server.getCustomBossEvents().onPlayerDisconnect($$0);
/* 393 */     UUID $$3 = $$0.getUUID();
/* 394 */     ServerPlayer $$4 = this.playersByUUID.get($$3);
/* 395 */     if ($$4 == $$0) {
/* 396 */       this.playersByUUID.remove($$3);
/* 397 */       this.stats.remove($$3);
/* 398 */       this.advancements.remove($$3);
/*     */     } 
/* 400 */     broadcastAll((Packet<?>)new ClientboundPlayerInfoRemovePacket(List.of($$0.getUUID())));
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Component canPlayerLogin(SocketAddress $$0, GameProfile $$1) {
/* 405 */     if (this.bans.isBanned($$1)) {
/* 406 */       UserBanListEntry $$2 = this.bans.get($$1);
/* 407 */       MutableComponent $$3 = Component.translatable("multiplayer.disconnect.banned.reason", new Object[] { $$2.getReason() });
/*     */       
/* 409 */       if ($$2.getExpires() != null) {
/* 410 */         $$3.append((Component)Component.translatable("multiplayer.disconnect.banned.expiration", new Object[] { BAN_DATE_FORMAT.format($$2.getExpires()) }));
/*     */       }
/*     */       
/* 413 */       return (Component)$$3;
/*     */     } 
/*     */     
/* 416 */     if (!isWhiteListed($$1)) {
/* 417 */       return (Component)Component.translatable("multiplayer.disconnect.not_whitelisted");
/*     */     }
/*     */     
/* 420 */     if (this.ipBans.isBanned($$0)) {
/* 421 */       IpBanListEntry $$4 = this.ipBans.get($$0);
/* 422 */       MutableComponent $$5 = Component.translatable("multiplayer.disconnect.banned_ip.reason", new Object[] { $$4.getReason() });
/*     */       
/* 424 */       if ($$4.getExpires() != null) {
/* 425 */         $$5.append((Component)Component.translatable("multiplayer.disconnect.banned_ip.expiration", new Object[] { BAN_DATE_FORMAT.format($$4.getExpires()) }));
/*     */       }
/*     */       
/* 428 */       return (Component)$$5;
/*     */     } 
/*     */     
/* 431 */     if (this.players.size() >= this.maxPlayers && !canBypassPlayerLimit($$1)) {
/* 432 */       return (Component)Component.translatable("multiplayer.disconnect.server_full");
/*     */     }
/*     */     
/* 435 */     return null;
/*     */   }
/*     */   
/*     */   public ServerPlayer getPlayerForLogin(GameProfile $$0, ClientInformation $$1) {
/* 439 */     return new ServerPlayer(this.server, this.server.overworld(), $$0, $$1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean disconnectAllPlayersWithProfile(GameProfile $$0) {
/* 446 */     UUID $$1 = $$0.getId();
/* 447 */     Set<ServerPlayer> $$2 = Sets.newIdentityHashSet();
/* 448 */     for (ServerPlayer $$3 : this.players) {
/* 449 */       if ($$3.getUUID().equals($$1)) {
/* 450 */         $$2.add($$3);
/*     */       }
/*     */     } 
/* 453 */     ServerPlayer $$4 = this.playersByUUID.get($$0.getId());
/* 454 */     if ($$4 != null) {
/* 455 */       $$2.add($$4);
/*     */     }
/* 457 */     for (ServerPlayer $$5 : $$2) {
/* 458 */       $$5.connection.disconnect(DUPLICATE_LOGIN_DISCONNECT_MESSAGE);
/*     */     }
/* 460 */     return !$$2.isEmpty();
/*     */   }
/*     */   public ServerPlayer respawn(ServerPlayer $$0, boolean $$1) {
/*     */     Optional<Vec3> $$7;
/* 464 */     this.players.remove($$0);
/*     */     
/* 466 */     $$0.serverLevel().removePlayerImmediately($$0, Entity.RemovalReason.DISCARDED);
/*     */     
/* 468 */     BlockPos $$2 = $$0.getRespawnPosition();
/* 469 */     float $$3 = $$0.getRespawnAngle();
/* 470 */     boolean $$4 = $$0.isRespawnForced();
/*     */     
/* 472 */     ServerLevel $$5 = this.server.getLevel($$0.getRespawnDimension());
/*     */ 
/*     */     
/* 475 */     if ($$5 != null && $$2 != null) {
/* 476 */       Optional<Vec3> $$6 = Player.findRespawnPositionAndUseSpawnBlock($$5, $$2, $$3, $$4, $$1);
/*     */     } else {
/* 478 */       $$7 = Optional.empty();
/*     */     } 
/*     */     
/* 481 */     ServerLevel $$8 = ($$5 != null && $$7.isPresent()) ? $$5 : this.server.overworld();
/* 482 */     ServerPlayer $$9 = new ServerPlayer(this.server, $$8, $$0.getGameProfile(), $$0.clientInformation());
/*     */     
/* 484 */     $$9.connection = $$0.connection;
/* 485 */     $$9.restoreFrom($$0, $$1);
/* 486 */     $$9.setId($$0.getId());
/* 487 */     $$9.setMainArm($$0.getMainArm());
/* 488 */     for (String $$10 : $$0.getTags()) {
/* 489 */       $$9.addTag($$10);
/*     */     }
/*     */     
/* 492 */     boolean $$11 = false;
/* 493 */     if ($$7.isPresent()) {
/* 494 */       float $$17; BlockState $$12 = $$8.getBlockState($$2);
/* 495 */       boolean $$13 = $$12.is(Blocks.RESPAWN_ANCHOR);
/*     */       
/* 497 */       Vec3 $$14 = $$7.get();
/*     */       
/* 499 */       if ($$12.is(BlockTags.BEDS) || $$13) {
/* 500 */         Vec3 $$15 = Vec3.atBottomCenterOf((Vec3i)$$2).subtract($$14).normalize();
/* 501 */         float $$16 = (float)Mth.wrapDegrees(Mth.atan2($$15.z, $$15.x) * 57.2957763671875D - 90.0D);
/*     */       } else {
/* 503 */         $$17 = $$3;
/*     */       } 
/* 505 */       $$9.moveTo($$14.x, $$14.y, $$14.z, $$17, 0.0F);
/*     */       
/* 507 */       $$9.setRespawnPosition($$8.dimension(), $$2, $$3, $$4, false);
/* 508 */       $$11 = (!$$1 && $$13);
/* 509 */     } else if ($$2 != null) {
/* 510 */       $$9.connection.send((Packet)new ClientboundGameEventPacket(ClientboundGameEventPacket.NO_RESPAWN_BLOCK_AVAILABLE, 0.0F));
/*     */     } 
/*     */     
/* 513 */     while (!$$8.noCollision((Entity)$$9) && $$9.getY() < $$8.getMaxBuildHeight()) {
/* 514 */       $$9.setPos($$9.getX(), $$9.getY() + 1.0D, $$9.getZ());
/*     */     }
/*     */ 
/*     */     
/* 518 */     byte $$18 = $$1 ? 1 : 0;
/*     */     
/* 520 */     ServerLevel $$19 = $$9.serverLevel();
/* 521 */     LevelData $$20 = $$19.getLevelData();
/* 522 */     $$9.connection.send((Packet)new ClientboundRespawnPacket($$9
/* 523 */           .createCommonSpawnInfo($$19), $$18));
/*     */ 
/*     */     
/* 526 */     $$9.connection.teleport($$9.getX(), $$9.getY(), $$9.getZ(), $$9.getYRot(), $$9.getXRot());
/* 527 */     $$9.connection.send((Packet)new ClientboundSetDefaultSpawnPositionPacket($$8.getSharedSpawnPos(), $$8.getSharedSpawnAngle()));
/* 528 */     $$9.connection.send((Packet)new ClientboundChangeDifficultyPacket($$20.getDifficulty(), $$20.isDifficultyLocked()));
/* 529 */     $$9.connection.send((Packet)new ClientboundSetExperiencePacket($$9.experienceProgress, $$9.totalExperience, $$9.experienceLevel));
/* 530 */     sendLevelInfo($$9, $$8);
/* 531 */     sendPlayerPermissionLevel($$9);
/*     */     
/* 533 */     $$8.addRespawnedPlayer($$9);
/* 534 */     this.players.add($$9);
/* 535 */     this.playersByUUID.put($$9.getUUID(), $$9);
/*     */     
/* 537 */     $$9.initInventoryMenu();
/* 538 */     $$9.setHealth($$9.getHealth());
/*     */     
/* 540 */     if ($$11)
/*     */     {
/* 542 */       $$9.connection.send((Packet)new ClientboundSoundPacket((Holder)SoundEvents.RESPAWN_ANCHOR_DEPLETE, SoundSource.BLOCKS, $$2.getX(), $$2.getY(), $$2.getZ(), 1.0F, 1.0F, $$8.getRandom().nextLong()));
/*     */     }
/*     */     
/* 545 */     return $$9;
/*     */   }
/*     */   
/*     */   public void sendPlayerPermissionLevel(ServerPlayer $$0) {
/* 549 */     GameProfile $$1 = $$0.getGameProfile();
/* 550 */     int $$2 = this.server.getProfilePermissions($$1);
/* 551 */     sendPlayerPermissionLevel($$0, $$2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void tick() {
/* 557 */     if (++this.sendAllPlayerInfoIn > 600) {
/* 558 */       broadcastAll((Packet<?>)new ClientboundPlayerInfoUpdatePacket(EnumSet.of(ClientboundPlayerInfoUpdatePacket.Action.UPDATE_LATENCY), this.players));
/* 559 */       this.sendAllPlayerInfoIn = 0;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void broadcastAll(Packet<?> $$0) {
/* 564 */     for (ServerPlayer $$1 : this.players) {
/* 565 */       $$1.connection.send($$0);
/*     */     }
/*     */   }
/*     */   
/*     */   public void broadcastAll(Packet<?> $$0, ResourceKey<Level> $$1) {
/* 570 */     for (ServerPlayer $$2 : this.players) {
/* 571 */       if ($$2.level().dimension() == $$1) {
/* 572 */         $$2.connection.send($$0);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void broadcastSystemToTeam(Player $$0, Component $$1) {
/* 578 */     PlayerTeam playerTeam = $$0.getTeam();
/* 579 */     if (playerTeam == null) {
/*     */       return;
/*     */     }
/* 582 */     Collection<String> $$3 = playerTeam.getPlayers();
/* 583 */     for (String $$4 : $$3) {
/* 584 */       ServerPlayer $$5 = getPlayerByName($$4);
/* 585 */       if ($$5 == null || $$5 == $$0) {
/*     */         continue;
/*     */       }
/* 588 */       $$5.sendSystemMessage($$1);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void broadcastSystemToAllExceptTeam(Player $$0, Component $$1) {
/* 593 */     PlayerTeam playerTeam = $$0.getTeam();
/* 594 */     if (playerTeam == null) {
/* 595 */       broadcastSystemMessage($$1, false);
/*     */       return;
/*     */     } 
/* 598 */     for (int $$3 = 0; $$3 < this.players.size(); $$3++) {
/* 599 */       ServerPlayer $$4 = this.players.get($$3);
/* 600 */       if ($$4.getTeam() != playerTeam) {
/* 601 */         $$4.sendSystemMessage($$1);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public String[] getPlayerNamesArray() {
/* 607 */     String[] $$0 = new String[this.players.size()];
/* 608 */     for (int $$1 = 0; $$1 < this.players.size(); $$1++) {
/* 609 */       $$0[$$1] = ((ServerPlayer)this.players.get($$1)).getGameProfile().getName();
/*     */     }
/* 611 */     return $$0;
/*     */   }
/*     */   
/*     */   public UserBanList getBans() {
/* 615 */     return this.bans;
/*     */   }
/*     */   
/*     */   public IpBanList getIpBans() {
/* 619 */     return this.ipBans;
/*     */   }
/*     */   
/*     */   public void op(GameProfile $$0) {
/* 623 */     this.ops.add(new ServerOpListEntry($$0, this.server.getOperatorUserPermissionLevel(), this.ops.canBypassPlayerLimit($$0)));
/* 624 */     ServerPlayer $$1 = getPlayer($$0.getId());
/* 625 */     if ($$1 != null) {
/* 626 */       sendPlayerPermissionLevel($$1);
/*     */     }
/*     */   }
/*     */   
/*     */   public void deop(GameProfile $$0) {
/* 631 */     this.ops.remove($$0);
/* 632 */     ServerPlayer $$1 = getPlayer($$0.getId());
/* 633 */     if ($$1 != null) {
/* 634 */       sendPlayerPermissionLevel($$1);
/*     */     }
/*     */   }
/*     */   
/*     */   private void sendPlayerPermissionLevel(ServerPlayer $$0, int $$1) {
/* 639 */     if ($$0.connection != null) {
/*     */       byte $$4;
/* 641 */       if ($$1 <= 0) {
/* 642 */         byte $$2 = 24;
/* 643 */       } else if ($$1 >= 4) {
/* 644 */         byte $$3 = 28;
/*     */       } else {
/* 646 */         $$4 = (byte)(24 + $$1);
/*     */       } 
/* 648 */       $$0.connection.send((Packet)new ClientboundEntityEventPacket((Entity)$$0, $$4));
/*     */     } 
/* 650 */     this.server.getCommands().sendCommands($$0);
/*     */   }
/*     */   
/*     */   public boolean isWhiteListed(GameProfile $$0) {
/* 654 */     return (!this.doWhiteList || this.ops.contains($$0) || this.whitelist.contains($$0));
/*     */   }
/*     */   
/*     */   public boolean isOp(GameProfile $$0) {
/* 658 */     return (this.ops.contains($$0) || (this.server.isSingleplayerOwner($$0) && this.server.getWorldData().getAllowCommands()) || this.allowCheatsForAllPlayers);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public ServerPlayer getPlayerByName(String $$0) {
/* 664 */     int $$1 = this.players.size();
/* 665 */     for (int $$2 = 0; $$2 < $$1; $$2++) {
/* 666 */       ServerPlayer $$3 = this.players.get($$2);
/* 667 */       if ($$3.getGameProfile().getName().equalsIgnoreCase($$0)) {
/* 668 */         return $$3;
/*     */       }
/*     */     } 
/* 671 */     return null;
/*     */   }
/*     */   
/*     */   public void broadcast(@Nullable Player $$0, double $$1, double $$2, double $$3, double $$4, ResourceKey<Level> $$5, Packet<?> $$6) {
/* 675 */     for (int $$7 = 0; $$7 < this.players.size(); $$7++) {
/* 676 */       ServerPlayer $$8 = this.players.get($$7);
/* 677 */       if ($$8 != $$0)
/*     */       {
/*     */         
/* 680 */         if ($$8.level().dimension() == $$5) {
/*     */ 
/*     */           
/* 683 */           double $$9 = $$1 - $$8.getX();
/* 684 */           double $$10 = $$2 - $$8.getY();
/* 685 */           double $$11 = $$3 - $$8.getZ();
/* 686 */           if ($$9 * $$9 + $$10 * $$10 + $$11 * $$11 < $$4 * $$4)
/* 687 */             $$8.connection.send($$6); 
/*     */         }  } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void saveAll() {
/* 693 */     for (int $$0 = 0; $$0 < this.players.size(); $$0++) {
/* 694 */       save(this.players.get($$0));
/*     */     }
/*     */   }
/*     */   
/*     */   public UserWhiteList getWhiteList() {
/* 699 */     return this.whitelist;
/*     */   }
/*     */   
/*     */   public String[] getWhiteListNames() {
/* 703 */     return this.whitelist.getUserList();
/*     */   }
/*     */   
/*     */   public ServerOpList getOps() {
/* 707 */     return this.ops;
/*     */   }
/*     */   
/*     */   public String[] getOpNames() {
/* 711 */     return this.ops.getUserList();
/*     */   }
/*     */ 
/*     */   
/*     */   public void reloadWhiteList() {}
/*     */ 
/*     */   
/*     */   public void sendLevelInfo(ServerPlayer $$0, ServerLevel $$1) {
/* 719 */     WorldBorder $$2 = this.server.overworld().getWorldBorder();
/* 720 */     $$0.connection.send((Packet)new ClientboundInitializeBorderPacket($$2));
/* 721 */     $$0.connection.send((Packet)new ClientboundSetTimePacket($$1.getGameTime(), $$1.getDayTime(), $$1.getGameRules().getBoolean(GameRules.RULE_DAYLIGHT)));
/* 722 */     $$0.connection.send((Packet)new ClientboundSetDefaultSpawnPositionPacket($$1.getSharedSpawnPos(), $$1.getSharedSpawnAngle()));
/*     */     
/* 724 */     if ($$1.isRaining()) {
/* 725 */       $$0.connection.send((Packet)new ClientboundGameEventPacket(ClientboundGameEventPacket.START_RAINING, 0.0F));
/* 726 */       $$0.connection.send((Packet)new ClientboundGameEventPacket(ClientboundGameEventPacket.RAIN_LEVEL_CHANGE, $$1.getRainLevel(1.0F)));
/* 727 */       $$0.connection.send((Packet)new ClientboundGameEventPacket(ClientboundGameEventPacket.THUNDER_LEVEL_CHANGE, $$1.getThunderLevel(1.0F)));
/*     */     } 
/*     */     
/* 730 */     $$0.connection.send((Packet)new ClientboundGameEventPacket(ClientboundGameEventPacket.LEVEL_CHUNKS_LOAD_START, 0.0F));
/*     */     
/* 732 */     this.server.tickRateManager().updateJoiningPlayer($$0);
/*     */   }
/*     */   
/*     */   public void sendAllPlayerInfo(ServerPlayer $$0) {
/* 736 */     $$0.inventoryMenu.sendAllDataToRemote();
/* 737 */     $$0.resetSentInfo();
/* 738 */     $$0.connection.send((Packet)new ClientboundSetCarriedItemPacket(($$0.getInventory()).selected));
/*     */   }
/*     */   
/*     */   public int getPlayerCount() {
/* 742 */     return this.players.size();
/*     */   }
/*     */   
/*     */   public int getMaxPlayers() {
/* 746 */     return this.maxPlayers;
/*     */   }
/*     */   
/*     */   public boolean isUsingWhitelist() {
/* 750 */     return this.doWhiteList;
/*     */   }
/*     */   
/*     */   public void setUsingWhiteList(boolean $$0) {
/* 754 */     this.doWhiteList = $$0;
/*     */   }
/*     */   
/*     */   public List<ServerPlayer> getPlayersWithAddress(String $$0) {
/* 758 */     List<ServerPlayer> $$1 = Lists.newArrayList();
/*     */     
/* 760 */     for (ServerPlayer $$2 : this.players) {
/* 761 */       if ($$2.getIpAddress().equals($$0)) {
/* 762 */         $$1.add($$2);
/*     */       }
/*     */     } 
/*     */     
/* 766 */     return $$1;
/*     */   }
/*     */   
/*     */   public int getViewDistance() {
/* 770 */     return this.viewDistance;
/*     */   }
/*     */   
/*     */   public int getSimulationDistance() {
/* 774 */     return this.simulationDistance;
/*     */   }
/*     */   
/*     */   public MinecraftServer getServer() {
/* 778 */     return this.server;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public CompoundTag getSingleplayerData() {
/* 783 */     return null;
/*     */   }
/*     */   
/*     */   public void setAllowCheatsForAllPlayers(boolean $$0) {
/* 787 */     this.allowCheatsForAllPlayers = $$0;
/*     */   }
/*     */   
/*     */   public void removeAll() {
/* 791 */     for (int $$0 = 0; $$0 < this.players.size(); $$0++) {
/* 792 */       ((ServerPlayer)this.players.get($$0)).connection.disconnect((Component)Component.translatable("multiplayer.disconnect.server_shutdown"));
/*     */     }
/*     */   }
/*     */   
/*     */   public void broadcastSystemMessage(Component $$0, boolean $$1) {
/* 797 */     broadcastSystemMessage($$0, $$1 -> $$0, $$1);
/*     */   }
/*     */   
/*     */   public void broadcastSystemMessage(Component $$0, Function<ServerPlayer, Component> $$1, boolean $$2) {
/* 801 */     this.server.sendSystemMessage($$0);
/* 802 */     for (ServerPlayer $$3 : this.players) {
/* 803 */       Component $$4 = $$1.apply($$3);
/* 804 */       if ($$4 != null) {
/* 805 */         $$3.sendSystemMessage($$4, $$2);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void broadcastChatMessage(PlayerChatMessage $$0, CommandSourceStack $$1, ChatType.Bound $$2) {
/* 811 */     Objects.requireNonNull($$1); broadcastChatMessage($$0, $$1::shouldFilterMessageTo, $$1.getPlayer(), $$2);
/*     */   }
/*     */   
/*     */   public void broadcastChatMessage(PlayerChatMessage $$0, ServerPlayer $$1, ChatType.Bound $$2) {
/* 815 */     Objects.requireNonNull($$1); broadcastChatMessage($$0, $$1::shouldFilterMessageTo, $$1, $$2);
/*     */   }
/*     */   private void broadcastChatMessage(PlayerChatMessage $$0, Predicate<ServerPlayer> $$1, @Nullable ServerPlayer $$2, ChatType.Bound $$3) {
/*     */     int i;
/* 819 */     boolean $$4 = verifyChatTrusted($$0);
/* 820 */     this.server.logChatMessage($$0.decoratedContent(), $$3, $$4 ? null : "Not Secure");
/* 821 */     OutgoingChatMessage $$5 = OutgoingChatMessage.create($$0);
/*     */     
/* 823 */     boolean $$6 = false;
/*     */     
/* 825 */     for (ServerPlayer $$7 : this.players) {
/* 826 */       boolean $$8 = $$1.test($$7);
/* 827 */       $$7.sendChatMessage($$5, $$8, $$3);
/* 828 */       i = $$6 | (($$8 && $$0.isFullyFiltered()) ? 1 : 0);
/*     */     } 
/*     */     
/* 831 */     if (i != 0 && $$2 != null) {
/* 832 */       $$2.sendSystemMessage(CHAT_FILTERED_FULL);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean verifyChatTrusted(PlayerChatMessage $$0) {
/* 837 */     return ($$0.hasSignature() && !$$0.hasExpiredServer(Instant.now()));
/*     */   }
/*     */   
/*     */   public ServerStatsCounter getPlayerStats(Player $$0) {
/* 841 */     UUID $$1 = $$0.getUUID();
/* 842 */     ServerStatsCounter $$2 = this.stats.get($$1);
/*     */     
/* 844 */     if ($$2 == null) {
/* 845 */       File $$3 = this.server.getWorldPath(LevelResource.PLAYER_STATS_DIR).toFile();
/* 846 */       File $$4 = new File($$3, "" + $$1 + ".json");
/*     */       
/* 848 */       if (!$$4.exists()) {
/*     */         
/* 850 */         File $$5 = new File($$3, $$0.getName().getString() + ".json");
/* 851 */         Path $$6 = $$5.toPath();
/* 852 */         if (FileUtil.isPathNormalized($$6) && FileUtil.isPathPortable($$6) && $$6.startsWith($$3.getPath()) && $$5.isFile()) {
/* 853 */           $$5.renameTo($$4);
/*     */         }
/*     */       } 
/*     */       
/* 857 */       $$2 = new ServerStatsCounter(this.server, $$4);
/* 858 */       this.stats.put($$1, $$2);
/*     */     } 
/*     */     
/* 861 */     return $$2;
/*     */   }
/*     */   
/*     */   public PlayerAdvancements getPlayerAdvancements(ServerPlayer $$0) {
/* 865 */     UUID $$1 = $$0.getUUID();
/* 866 */     PlayerAdvancements $$2 = this.advancements.get($$1);
/* 867 */     if ($$2 == null) {
/* 868 */       Path $$3 = this.server.getWorldPath(LevelResource.PLAYER_ADVANCEMENTS_DIR).resolve("" + $$1 + ".json");
/* 869 */       $$2 = new PlayerAdvancements(this.server.getFixerUpper(), this, this.server.getAdvancements(), $$3, $$0);
/* 870 */       this.advancements.put($$1, $$2);
/*     */     } 
/*     */     
/* 873 */     $$2.setPlayer($$0);
/*     */     
/* 875 */     return $$2;
/*     */   }
/*     */   
/*     */   public void setViewDistance(int $$0) {
/* 879 */     this.viewDistance = $$0;
/* 880 */     broadcastAll((Packet<?>)new ClientboundSetChunkCacheRadiusPacket($$0));
/*     */     
/* 882 */     for (ServerLevel $$1 : this.server.getAllLevels()) {
/* 883 */       if ($$1 != null) {
/* 884 */         $$1.getChunkSource().setViewDistance($$0);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setSimulationDistance(int $$0) {
/* 890 */     this.simulationDistance = $$0;
/* 891 */     broadcastAll((Packet<?>)new ClientboundSetSimulationDistancePacket($$0));
/*     */     
/* 893 */     for (ServerLevel $$1 : this.server.getAllLevels()) {
/* 894 */       if ($$1 != null) {
/* 895 */         $$1.getChunkSource().setSimulationDistance($$0);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public List<ServerPlayer> getPlayers() {
/* 901 */     return this.players;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public ServerPlayer getPlayer(UUID $$0) {
/* 906 */     return this.playersByUUID.get($$0);
/*     */   }
/*     */   
/*     */   public boolean canBypassPlayerLimit(GameProfile $$0) {
/* 910 */     return false;
/*     */   }
/*     */   
/*     */   public void reloadResources() {
/* 914 */     for (PlayerAdvancements $$0 : this.advancements.values()) {
/* 915 */       $$0.reload(this.server.getAdvancements());
/*     */     }
/* 917 */     broadcastAll((Packet<?>)new ClientboundUpdateTagsPacket(TagNetworkSerialization.serializeTagsToNetwork(this.registries)));
/* 918 */     ClientboundUpdateRecipesPacket $$1 = new ClientboundUpdateRecipesPacket(this.server.getRecipeManager().getRecipes());
/* 919 */     for (ServerPlayer $$2 : this.players) {
/* 920 */       $$2.connection.send((Packet)$$1);
/* 921 */       $$2.getRecipeBook().sendInitialRecipeBook($$2);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isAllowCheatsForAllPlayers() {
/* 926 */     return this.allowCheatsForAllPlayers;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\players\PlayerList.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */