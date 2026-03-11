/*     */ package net.minecraft.server.dedicated;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.datafixers.DataFixer;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.Writer;
/*     */ import java.net.InetAddress;
/*     */ import java.net.Proxy;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.function.BooleanSupplier;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.DefaultUncaughtExceptionHandler;
/*     */ import net.minecraft.DefaultUncaughtExceptionHandlerWithName;
/*     */ import net.minecraft.SharedConstants;
/*     */ import net.minecraft.SystemReport;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.RegistryAccess;
/*     */ import net.minecraft.server.ConsoleInput;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.server.ServerInterface;
/*     */ import net.minecraft.server.Services;
/*     */ import net.minecraft.server.WorldStem;
/*     */ import net.minecraft.server.gui.MinecraftServerGui;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.server.level.progress.ChunkProgressListenerFactory;
/*     */ import net.minecraft.server.network.TextFilter;
/*     */ import net.minecraft.server.network.TextFilterClient;
/*     */ import net.minecraft.server.packs.repository.PackRepository;
/*     */ import net.minecraft.server.players.GameProfileCache;
/*     */ import net.minecraft.server.players.OldUsersConverter;
/*     */ import net.minecraft.server.players.PlayerList;
/*     */ import net.minecraft.server.rcon.RconConsoleSource;
/*     */ import net.minecraft.server.rcon.thread.QueryThreadGs4;
/*     */ import net.minecraft.server.rcon.thread.RconThread;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.monitoring.jmx.MinecraftServerStatistics;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.level.GameRules;
/*     */ import net.minecraft.world.level.GameType;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.entity.SkullBlockEntity;
/*     */ import net.minecraft.world.level.storage.LevelStorageSource;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class DedicatedServer extends MinecraftServer implements ServerInterface {
/*  58 */   static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private static final int CONVERSION_RETRY_DELAY_MS = 5000;
/*     */   private static final int CONVERSION_RETRIES = 2;
/*  62 */   private final List<ConsoleInput> consoleInput = Collections.synchronizedList(Lists.newArrayList());
/*     */   @Nullable
/*     */   private QueryThreadGs4 queryThreadGs4;
/*     */   private final RconConsoleSource rconConsoleSource;
/*     */   @Nullable
/*     */   private RconThread rconThread;
/*     */   private final DedicatedServerSettings settings;
/*     */   @Nullable
/*     */   private MinecraftServerGui gui;
/*     */   @Nullable
/*     */   private final TextFilterClient textFilterClient;
/*     */   
/*     */   public DedicatedServer(Thread $$0, LevelStorageSource.LevelStorageAccess $$1, PackRepository $$2, WorldStem $$3, DedicatedServerSettings $$4, DataFixer $$5, Services $$6, ChunkProgressListenerFactory $$7) {
/*  75 */     super($$0, $$1, $$2, $$3, Proxy.NO_PROXY, $$5, $$6, $$7);
/*  76 */     this.settings = $$4;
/*  77 */     this.rconConsoleSource = new RconConsoleSource(this);
/*  78 */     this.textFilterClient = TextFilterClient.createFromConfig(($$4.getProperties()).textFilteringConfig);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean initServer() throws IOException {
/*  83 */     Thread $$0 = new Thread("Server console handler")
/*     */       {
/*     */         public void run() {
/*  86 */           BufferedReader $$0 = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
/*     */           try {
/*     */             String $$1;
/*  89 */             while (!DedicatedServer.this.isStopped() && DedicatedServer.this.isRunning() && ($$1 = $$0.readLine()) != null) {
/*  90 */               DedicatedServer.this.handleConsoleInput($$1, DedicatedServer.this.createCommandSourceStack());
/*     */             }
/*  92 */           } catch (IOException $$2) {
/*  93 */             DedicatedServer.LOGGER.error("Exception handling console input", $$2);
/*     */           } 
/*     */         }
/*     */       };
/*  97 */     $$0.setDaemon(true);
/*  98 */     $$0.setUncaughtExceptionHandler((Thread.UncaughtExceptionHandler)new DefaultUncaughtExceptionHandler(LOGGER));
/*  99 */     $$0.start();
/*     */     
/* 101 */     LOGGER.info("Starting minecraft server version {}", SharedConstants.getCurrentVersion().getName());
/*     */     
/* 103 */     if (Runtime.getRuntime().maxMemory() / 1024L / 1024L < 512L) {
/* 104 */       LOGGER.warn("To start the server with more ram, launch it as \"java -Xmx1024M -Xms1024M -jar minecraft_server.jar\"");
/*     */     }
/*     */     
/* 107 */     LOGGER.info("Loading properties");
/* 108 */     DedicatedServerProperties $$1 = this.settings.getProperties();
/*     */     
/* 110 */     if (isSingleplayer()) {
/* 111 */       setLocalIp("127.0.0.1");
/*     */     } else {
/* 113 */       setUsesAuthentication($$1.onlineMode);
/* 114 */       setPreventProxyConnections($$1.preventProxyConnections);
/* 115 */       setLocalIp($$1.serverIp);
/*     */     } 
/*     */     
/* 118 */     setPvpAllowed($$1.pvp);
/* 119 */     setFlightAllowed($$1.allowFlight);
/* 120 */     setMotd($$1.motd);
/* 121 */     super.setPlayerIdleTimeout(((Integer)$$1.playerIdleTimeout.get()).intValue());
/* 122 */     setEnforceWhitelist($$1.enforceWhitelist);
/*     */     
/* 124 */     this.worldData.setGameType($$1.gamemode);
/* 125 */     LOGGER.info("Default game type: {}", $$1.gamemode);
/*     */     
/* 127 */     InetAddress $$2 = null;
/* 128 */     if (!getLocalIp().isEmpty()) {
/* 129 */       $$2 = InetAddress.getByName(getLocalIp());
/*     */     }
/* 131 */     if (getPort() < 0) {
/* 132 */       setPort($$1.serverPort);
/*     */     }
/*     */     
/* 135 */     initializeKeyPair();
/*     */     
/* 137 */     LOGGER.info("Starting Minecraft server on {}:{}", getLocalIp().isEmpty() ? "*" : getLocalIp(), Integer.valueOf(getPort()));
/*     */     try {
/* 139 */       getConnection().startTcpServerListener($$2, getPort());
/* 140 */     } catch (IOException $$3) {
/* 141 */       LOGGER.warn("**** FAILED TO BIND TO PORT!");
/* 142 */       LOGGER.warn("The exception was: {}", $$3.toString());
/* 143 */       LOGGER.warn("Perhaps a server is already running on that port?");
/* 144 */       return false;
/*     */     } 
/*     */     
/* 147 */     if (!usesAuthentication()) {
/* 148 */       LOGGER.warn("**** SERVER IS RUNNING IN OFFLINE/INSECURE MODE!");
/* 149 */       LOGGER.warn("The server will make no attempt to authenticate usernames. Beware.");
/* 150 */       LOGGER.warn("While this makes the game possible to play without internet access, it also opens up the ability for hackers to connect with any username they choose.");
/* 151 */       LOGGER.warn("To change this, set \"online-mode\" to \"true\" in the server.properties file.");
/*     */     } 
/*     */     
/* 154 */     if (convertOldUsers()) {
/* 155 */       getProfileCache().save();
/*     */     }
/* 157 */     if (!OldUsersConverter.serverReadyAfterUserconversion(this)) {
/* 158 */       return false;
/*     */     }
/*     */     
/* 161 */     setPlayerList(new DedicatedPlayerList(this, registries(), this.playerDataStorage));
/*     */     
/* 163 */     long $$4 = Util.getNanos();
/*     */     
/* 165 */     SkullBlockEntity.setup(this.services, (Executor)this);
/* 166 */     GameProfileCache.setUsesAuthentication(usesAuthentication());
/*     */     
/* 168 */     LOGGER.info("Preparing level \"{}\"", getLevelIdName());
/* 169 */     loadLevel();
/* 170 */     long $$5 = Util.getNanos() - $$4;
/* 171 */     String $$6 = String.format(Locale.ROOT, "%.3fs", new Object[] { Double.valueOf($$5 / 1.0E9D) });
/* 172 */     LOGGER.info("Done ({})! For help, type \"help\"", $$6);
/*     */ 
/*     */     
/* 175 */     if ($$1.announcePlayerAchievements != null) {
/* 176 */       ((GameRules.BooleanValue)getGameRules().getRule(GameRules.RULE_ANNOUNCE_ADVANCEMENTS)).set($$1.announcePlayerAchievements.booleanValue(), this);
/*     */     }
/*     */     
/* 179 */     if ($$1.enableQuery) {
/* 180 */       LOGGER.info("Starting GS4 status listener");
/* 181 */       this.queryThreadGs4 = QueryThreadGs4.create(this);
/*     */     } 
/* 183 */     if ($$1.enableRcon) {
/* 184 */       LOGGER.info("Starting remote control listener");
/* 185 */       this.rconThread = RconThread.create(this);
/*     */     } 
/*     */     
/* 188 */     if (getMaxTickLength() > 0L) {
/* 189 */       Thread $$7 = new Thread(new ServerWatchdog(this));
/* 190 */       $$7.setUncaughtExceptionHandler((Thread.UncaughtExceptionHandler)new DefaultUncaughtExceptionHandlerWithName(LOGGER));
/* 191 */       $$7.setName("Server Watchdog");
/* 192 */       $$7.setDaemon(true);
/* 193 */       $$7.start();
/*     */     } 
/*     */     
/* 196 */     if ($$1.enableJmxMonitoring) {
/* 197 */       MinecraftServerStatistics.registerJmxMonitoring(this);
/* 198 */       LOGGER.info("JMX monitoring enabled");
/*     */     } 
/*     */     
/* 201 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSpawningAnimals() {
/* 206 */     return ((getProperties()).spawnAnimals && super.isSpawningAnimals());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSpawningMonsters() {
/* 211 */     return ((this.settings.getProperties()).spawnMonsters && super.isSpawningMonsters());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean areNpcsEnabled() {
/* 216 */     return ((this.settings.getProperties()).spawnNpcs && super.areNpcsEnabled());
/*     */   }
/*     */ 
/*     */   
/*     */   public DedicatedServerProperties getProperties() {
/* 221 */     return this.settings.getProperties();
/*     */   }
/*     */ 
/*     */   
/*     */   public void forceDifficulty() {
/* 226 */     setDifficulty((getProperties()).difficulty, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isHardcore() {
/* 231 */     return (getProperties()).hardcore;
/*     */   }
/*     */ 
/*     */   
/*     */   public SystemReport fillServerSystemReport(SystemReport $$0) {
/* 236 */     $$0.setDetail("Is Modded", () -> getModdedStatus().fullDescription());
/* 237 */     $$0.setDetail("Type", () -> "Dedicated Server (map_server.txt)");
/*     */     
/* 239 */     return $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void dumpServerProperties(Path $$0) throws IOException {
/* 244 */     DedicatedServerProperties $$1 = getProperties();
/*     */     
/* 246 */     Writer $$2 = Files.newBufferedWriter($$0, new java.nio.file.OpenOption[0]); 
/* 247 */     try { $$2.write(String.format(Locale.ROOT, "sync-chunk-writes=%s%n", new Object[] { Boolean.valueOf($$1.syncChunkWrites) }));
/* 248 */       $$2.write(String.format(Locale.ROOT, "gamemode=%s%n", new Object[] { $$1.gamemode }));
/* 249 */       $$2.write(String.format(Locale.ROOT, "spawn-monsters=%s%n", new Object[] { Boolean.valueOf($$1.spawnMonsters) }));
/* 250 */       $$2.write(String.format(Locale.ROOT, "entity-broadcast-range-percentage=%d%n", new Object[] { Integer.valueOf($$1.entityBroadcastRangePercentage) }));
/* 251 */       $$2.write(String.format(Locale.ROOT, "max-world-size=%d%n", new Object[] { Integer.valueOf($$1.maxWorldSize) }));
/* 252 */       $$2.write(String.format(Locale.ROOT, "spawn-npcs=%s%n", new Object[] { Boolean.valueOf($$1.spawnNpcs) }));
/* 253 */       $$2.write(String.format(Locale.ROOT, "view-distance=%d%n", new Object[] { Integer.valueOf($$1.viewDistance) }));
/* 254 */       $$2.write(String.format(Locale.ROOT, "simulation-distance=%d%n", new Object[] { Integer.valueOf($$1.simulationDistance) }));
/* 255 */       $$2.write(String.format(Locale.ROOT, "spawn-animals=%s%n", new Object[] { Boolean.valueOf($$1.spawnAnimals) }));
/* 256 */       $$2.write(String.format(Locale.ROOT, "generate-structures=%s%n", new Object[] { Boolean.valueOf($$1.worldOptions.generateStructures()) }));
/* 257 */       $$2.write(String.format(Locale.ROOT, "use-native=%s%n", new Object[] { Boolean.valueOf($$1.useNativeTransport) }));
/* 258 */       $$2.write(String.format(Locale.ROOT, "rate-limit=%d%n", new Object[] { Integer.valueOf($$1.rateLimitPacketsPerSecond) }));
/* 259 */       if ($$2 != null) $$2.close();  }
/*     */     catch (Throwable throwable) { if ($$2 != null)
/*     */         try { $$2.close(); }
/*     */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/* 264 */      } public void onServerExit() { if (this.textFilterClient != null) {
/* 265 */       this.textFilterClient.close();
/*     */     }
/*     */     
/* 268 */     if (this.gui != null) {
/* 269 */       this.gui.close();
/*     */     }
/*     */     
/* 272 */     if (this.rconThread != null) {
/* 273 */       this.rconThread.stop();
/*     */     }
/*     */     
/* 276 */     if (this.queryThreadGs4 != null) {
/* 277 */       this.queryThreadGs4.stop();
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   public void tickChildren(BooleanSupplier $$0) {
/* 283 */     super.tickChildren($$0);
/* 284 */     handleConsoleInputs();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isNetherEnabled() {
/* 289 */     return (getProperties()).allowNether;
/*     */   }
/*     */   
/*     */   public void handleConsoleInput(String $$0, CommandSourceStack $$1) {
/* 293 */     this.consoleInput.add(new ConsoleInput($$0, $$1));
/*     */   }
/*     */   
/*     */   public void handleConsoleInputs() {
/* 297 */     while (!this.consoleInput.isEmpty()) {
/* 298 */       ConsoleInput $$0 = this.consoleInput.remove(0);
/* 299 */       getCommands().performPrefixedCommand($$0.source, $$0.msg);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDedicatedServer() {
/* 305 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRateLimitPacketsPerSecond() {
/* 310 */     return (getProperties()).rateLimitPacketsPerSecond;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEpollEnabled() {
/* 315 */     return (getProperties()).useNativeTransport;
/*     */   }
/*     */ 
/*     */   
/*     */   public DedicatedPlayerList getPlayerList() {
/* 320 */     return (DedicatedPlayerList)super.getPlayerList();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPublished() {
/* 325 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getServerIp() {
/* 330 */     return getLocalIp();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getServerPort() {
/* 335 */     return getPort();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getServerName() {
/* 340 */     return getMotd();
/*     */   }
/*     */   
/*     */   public void showGui() {
/* 344 */     if (this.gui == null) {
/* 345 */       this.gui = MinecraftServerGui.showFrameFor(this);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasGui() {
/* 351 */     return (this.gui != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCommandBlockEnabled() {
/* 356 */     return (getProperties()).enableCommandBlock;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSpawnProtectionRadius() {
/* 361 */     return (getProperties()).spawnProtection;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isUnderSpawnProtection(ServerLevel $$0, BlockPos $$1, Player $$2) {
/* 366 */     if ($$0.dimension() != Level.OVERWORLD) {
/* 367 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 371 */     if (getPlayerList().getOps().isEmpty()) {
/* 372 */       return false;
/*     */     }
/* 374 */     if (getPlayerList().isOp($$2.getGameProfile())) {
/* 375 */       return false;
/*     */     }
/* 377 */     if (getSpawnProtectionRadius() <= 0) {
/* 378 */       return false;
/*     */     }
/*     */     
/* 381 */     BlockPos $$3 = $$0.getSharedSpawnPos();
/* 382 */     int $$4 = Mth.abs($$1.getX() - $$3.getX());
/* 383 */     int $$5 = Mth.abs($$1.getZ() - $$3.getZ());
/* 384 */     int $$6 = Math.max($$4, $$5);
/*     */     
/* 386 */     return ($$6 <= getSpawnProtectionRadius());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean repliesToStatus() {
/* 391 */     return (getProperties()).enableStatus;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hidesOnlinePlayers() {
/* 396 */     return (getProperties()).hideOnlinePlayers;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getOperatorUserPermissionLevel() {
/* 401 */     return (getProperties()).opPermissionLevel;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getFunctionCompilationLevel() {
/* 406 */     return (getProperties()).functionPermissionLevel;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPlayerIdleTimeout(int $$0) {
/* 411 */     super.setPlayerIdleTimeout($$0);
/* 412 */     this.settings.update($$1 -> (DedicatedServerProperties)$$1.playerIdleTimeout.update((RegistryAccess)registryAccess(), Integer.valueOf($$0)));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldRconBroadcast() {
/* 417 */     return (getProperties()).broadcastRconToOps;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldInformAdmins() {
/* 422 */     return (getProperties()).broadcastConsoleToOps;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAbsoluteMaxWorldSize() {
/* 427 */     return (getProperties()).maxWorldSize;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCompressionThreshold() {
/* 432 */     return (getProperties()).networkCompressionThreshold;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean enforceSecureProfile() {
/* 437 */     DedicatedServerProperties $$0 = getProperties();
/* 438 */     return ($$0.enforceSecureProfile && $$0.onlineMode && this.services.canValidateProfileKeys());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean logIPs() {
/* 443 */     return (getProperties()).logIPs;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean convertOldUsers() {
/* 449 */     boolean $$0 = false;
/* 450 */     int $$1 = 0;
/* 451 */     while (!$$0 && $$1 <= 2) {
/* 452 */       if ($$1 > 0) {
/* 453 */         LOGGER.warn("Encountered a problem while converting the user banlist, retrying in a few seconds");
/* 454 */         waitForRetry();
/*     */       } 
/* 456 */       $$0 = OldUsersConverter.convertUserBanlist(this);
/* 457 */       $$1++;
/*     */     } 
/*     */     
/* 460 */     boolean $$2 = false;
/* 461 */     $$1 = 0;
/* 462 */     while (!$$2 && $$1 <= 2) {
/* 463 */       if ($$1 > 0) {
/* 464 */         LOGGER.warn("Encountered a problem while converting the ip banlist, retrying in a few seconds");
/* 465 */         waitForRetry();
/*     */       } 
/* 467 */       $$2 = OldUsersConverter.convertIpBanlist(this);
/* 468 */       $$1++;
/*     */     } 
/*     */     
/* 471 */     boolean $$3 = false;
/* 472 */     $$1 = 0;
/* 473 */     while (!$$3 && $$1 <= 2) {
/* 474 */       if ($$1 > 0) {
/* 475 */         LOGGER.warn("Encountered a problem while converting the op list, retrying in a few seconds");
/* 476 */         waitForRetry();
/*     */       } 
/* 478 */       $$3 = OldUsersConverter.convertOpsList(this);
/* 479 */       $$1++;
/*     */     } 
/*     */     
/* 482 */     boolean $$4 = false;
/* 483 */     $$1 = 0;
/* 484 */     while (!$$4 && $$1 <= 2) {
/* 485 */       if ($$1 > 0) {
/* 486 */         LOGGER.warn("Encountered a problem while converting the whitelist, retrying in a few seconds");
/* 487 */         waitForRetry();
/*     */       } 
/* 489 */       $$4 = OldUsersConverter.convertWhiteList(this);
/* 490 */       $$1++;
/*     */     } 
/*     */     
/* 493 */     boolean $$5 = false;
/* 494 */     $$1 = 0;
/* 495 */     while (!$$5 && $$1 <= 2) {
/* 496 */       if ($$1 > 0) {
/* 497 */         LOGGER.warn("Encountered a problem while converting the player save files, retrying in a few seconds");
/* 498 */         waitForRetry();
/*     */       } 
/* 500 */       $$5 = OldUsersConverter.convertPlayers(this);
/* 501 */       $$1++;
/*     */     } 
/*     */     
/* 504 */     return ($$0 || $$2 || $$3 || $$4 || $$5);
/*     */   }
/*     */   
/*     */   private void waitForRetry() {
/*     */     try {
/* 509 */       Thread.sleep(5000L);
/* 510 */     } catch (InterruptedException $$0) {
/*     */       return;
/*     */     } 
/*     */   }
/*     */   
/*     */   public long getMaxTickLength() {
/* 516 */     return (getProperties()).maxTickTime;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxChainedNeighborUpdates() {
/* 521 */     return (getProperties()).maxChainedNeighborUpdates;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPluginNames() {
/* 526 */     return "";
/*     */   }
/*     */ 
/*     */   
/*     */   public String runCommand(String $$0) {
/* 531 */     this.rconConsoleSource.prepareForCommand();
/* 532 */     executeBlocking(() -> getCommands().performPrefixedCommand(this.rconConsoleSource.createCommandSourceStack(), $$0));
/* 533 */     return this.rconConsoleSource.getCommandResponse();
/*     */   }
/*     */   
/*     */   public void storeUsingWhiteList(boolean $$0) {
/* 537 */     this.settings.update($$1 -> (DedicatedServerProperties)$$1.whiteList.update((RegistryAccess)registryAccess(), Boolean.valueOf($$0)));
/*     */   }
/*     */ 
/*     */   
/*     */   public void stopServer() {
/* 542 */     super.stopServer();
/* 543 */     Util.shutdownExecutors();
/* 544 */     SkullBlockEntity.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSingleplayerOwner(GameProfile $$0) {
/* 549 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getScaledTrackingDistance(int $$0) {
/* 554 */     return (getProperties()).entityBroadcastRangePercentage * $$0 / 100;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getLevelIdName() {
/* 559 */     return this.storageSource.getLevelId();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean forceSynchronousWrites() {
/* 564 */     return (this.settings.getProperties()).syncChunkWrites;
/*     */   }
/*     */ 
/*     */   
/*     */   public TextFilter createTextFilterForPlayer(ServerPlayer $$0) {
/* 569 */     if (this.textFilterClient != null) {
/* 570 */       return this.textFilterClient.createContext($$0.getGameProfile());
/*     */     }
/* 572 */     return TextFilter.DUMMY;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public GameType getForcedGameType() {
/* 578 */     return (this.settings.getProperties()).forceGameMode ? this.worldData.getGameType() : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Optional<MinecraftServer.ServerResourcePackInfo> getServerResourcePack() {
/* 583 */     return (this.settings.getProperties()).serverResourcePackInfo;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\dedicated\DedicatedServer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */