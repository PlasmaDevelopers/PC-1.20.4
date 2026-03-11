/*     */ package net.minecraft.client.server;
/*     */ import com.google.common.base.MoreObjects;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.function.BooleanSupplier;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.CrashReport;
/*     */ import net.minecraft.SharedConstants;
/*     */ import net.minecraft.SystemReport;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.multiplayer.ClientPacketListener;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.server.Services;
/*     */ import net.minecraft.server.WorldStem;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.server.level.progress.ChunkProgressListenerFactory;
/*     */ import net.minecraft.server.packs.repository.PackRepository;
/*     */ import net.minecraft.stats.Stats;
/*     */ import net.minecraft.util.ModCheck;
/*     */ import net.minecraft.util.profiling.ProfilerFiller;
/*     */ import net.minecraft.world.entity.player.ProfileKeyPair;
/*     */ import net.minecraft.world.level.GameType;
/*     */ import net.minecraft.world.level.storage.LevelStorageSource;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class IntegratedServer extends MinecraftServer {
/*  34 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private static final int MIN_SIM_DISTANCE = 2;
/*     */   
/*     */   private final Minecraft minecraft;
/*     */   private boolean paused = true;
/*  40 */   private int publishedPort = -1;
/*     */   @Nullable
/*     */   private GameType publishedGameType;
/*     */   @Nullable
/*     */   private LanServerPinger lanPinger;
/*     */   @Nullable
/*     */   private UUID uuid;
/*  47 */   private int previousSimulationDistance = 0;
/*     */   
/*     */   public IntegratedServer(Thread $$0, Minecraft $$1, LevelStorageSource.LevelStorageAccess $$2, PackRepository $$3, WorldStem $$4, Services $$5, ChunkProgressListenerFactory $$6) {
/*  50 */     super($$0, $$2, $$3, $$4, $$1.getProxy(), $$1.getFixerUpper(), $$5, $$6);
/*     */     
/*  52 */     setSingleplayerProfile($$1.getGameProfile());
/*  53 */     setDemo($$1.isDemo());
/*  54 */     setPlayerList(new IntegratedPlayerList(this, registries(), this.playerDataStorage));
/*     */     
/*  56 */     this.minecraft = $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean initServer() {
/*  61 */     LOGGER.info("Starting integrated minecraft server version {}", SharedConstants.getCurrentVersion().getName());
/*     */     
/*  63 */     setUsesAuthentication(true);
/*  64 */     setPvpAllowed(true);
/*  65 */     setFlightAllowed(true);
/*     */     
/*  67 */     initializeKeyPair();
/*     */     
/*  69 */     loadLevel();
/*     */     
/*  71 */     GameProfile $$0 = getSingleplayerProfile();
/*  72 */     String $$1 = getWorldData().getLevelName();
/*  73 */     setMotd(($$0 != null) ? ($$0.getName() + " - " + $$0.getName()) : $$1);
/*     */     
/*  75 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPaused() {
/*  80 */     return this.paused;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tickServer(BooleanSupplier $$0) {
/*  85 */     boolean $$1 = this.paused;
/*  86 */     this.paused = Minecraft.getInstance().isPaused();
/*     */     
/*  88 */     ProfilerFiller $$2 = getProfiler();
/*  89 */     if (!$$1 && this.paused) {
/*  90 */       $$2.push("autoSave");
/*  91 */       LOGGER.info("Saving and pausing game...");
/*  92 */       saveEverything(false, false, false);
/*  93 */       $$2.pop();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  98 */     boolean $$3 = (Minecraft.getInstance().getConnection() != null);
/*  99 */     if ($$3 && this.paused) {
/* 100 */       tickPaused();
/*     */       
/*     */       return;
/*     */     } 
/* 104 */     if ($$1 && !this.paused)
/*     */     {
/*     */       
/* 107 */       forceTimeSynchronization();
/*     */     }
/*     */     
/* 110 */     super.tickServer($$0);
/*     */     
/* 112 */     int $$4 = Math.max(2, ((Integer)this.minecraft.options.renderDistance().get()).intValue());
/* 113 */     if ($$4 != getPlayerList().getViewDistance()) {
/* 114 */       LOGGER.info("Changing view distance to {}, from {}", Integer.valueOf($$4), Integer.valueOf(getPlayerList().getViewDistance()));
/* 115 */       getPlayerList().setViewDistance($$4);
/*     */     } 
/* 117 */     int $$5 = Math.max(2, ((Integer)this.minecraft.options.simulationDistance().get()).intValue());
/* 118 */     if ($$5 != this.previousSimulationDistance) {
/* 119 */       LOGGER.info("Changing simulation distance to {}, from {}", Integer.valueOf($$5), Integer.valueOf(this.previousSimulationDistance));
/* 120 */       getPlayerList().setSimulationDistance($$5);
/* 121 */       this.previousSimulationDistance = $$5;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void logTickTime(long $$0) {
/* 127 */     this.minecraft.getDebugOverlay().logTickDuration($$0);
/*     */   }
/*     */   
/*     */   private void tickPaused() {
/* 131 */     for (ServerPlayer $$0 : getPlayerList().getPlayers()) {
/* 132 */       $$0.awardStat(Stats.TOTAL_WORLD_TIME);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldRconBroadcast() {
/* 138 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldInformAdmins() {
/* 143 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public File getServerDirectory() {
/* 148 */     return this.minecraft.gameDirectory;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDedicatedServer() {
/* 153 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRateLimitPacketsPerSecond() {
/* 158 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEpollEnabled() {
/* 163 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onServerCrash(CrashReport $$0) {
/* 168 */     this.minecraft.delayCrashRaw($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public SystemReport fillServerSystemReport(SystemReport $$0) {
/* 173 */     $$0.setDetail("Type", "Integrated Server (map_client.txt)");
/* 174 */     $$0.setDetail("Is Modded", () -> getModdedStatus().fullDescription());
/* 175 */     Objects.requireNonNull(this.minecraft); $$0.setDetail("Launched Version", this.minecraft::getLaunchedVersion);
/*     */     
/* 177 */     return $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public ModCheck getModdedStatus() {
/* 182 */     return Minecraft.checkModStatus().merge(super.getModdedStatus());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean publishServer(@Nullable GameType $$0, boolean $$1, int $$2) {
/*     */     try {
/* 188 */       this.minecraft.prepareForMultiplayer();
/* 189 */       this.minecraft.getProfileKeyPairManager().prepareKeyPair().thenAcceptAsync($$0 -> $$0.ifPresent(()), (Executor)this.minecraft);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 196 */       getConnection().startTcpServerListener(null, $$2);
/* 197 */       LOGGER.info("Started serving on {}", Integer.valueOf($$2));
/* 198 */       this.publishedPort = $$2;
/*     */       
/* 200 */       this.lanPinger = new LanServerPinger(getMotd(), "" + $$2);
/* 201 */       this.lanPinger.start();
/*     */       
/* 203 */       this.publishedGameType = $$0;
/* 204 */       getPlayerList().setAllowCheatsForAllPlayers($$1);
/* 205 */       int $$3 = getProfilePermissions(this.minecraft.player.getGameProfile());
/* 206 */       this.minecraft.player.setPermissionLevel($$3);
/* 207 */       for (ServerPlayer $$4 : getPlayerList().getPlayers()) {
/* 208 */         getCommands().sendCommands($$4);
/*     */       }
/*     */       
/* 211 */       return true;
/* 212 */     } catch (IOException iOException) {
/*     */       
/* 214 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void stopServer() {
/* 219 */     super.stopServer();
/*     */     
/* 221 */     if (this.lanPinger != null) {
/* 222 */       this.lanPinger.interrupt();
/* 223 */       this.lanPinger = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void halt(boolean $$0) {
/* 229 */     executeBlocking(() -> {
/*     */           List<ServerPlayer> $$0 = Lists.newArrayList(getPlayerList().getPlayers());
/*     */           
/*     */           for (ServerPlayer $$1 : $$0) {
/*     */             if (!$$1.getUUID().equals(this.uuid)) {
/*     */               getPlayerList().remove($$1);
/*     */             }
/*     */           } 
/*     */         });
/*     */     
/* 239 */     super.halt($$0);
/*     */     
/* 241 */     if (this.lanPinger != null) {
/* 242 */       this.lanPinger.interrupt();
/* 243 */       this.lanPinger = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPublished() {
/* 249 */     return (this.publishedPort > -1);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPort() {
/* 254 */     return this.publishedPort;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDefaultGameType(GameType $$0) {
/* 259 */     super.setDefaultGameType($$0);
/*     */     
/* 261 */     this.publishedGameType = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCommandBlockEnabled() {
/* 266 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getOperatorUserPermissionLevel() {
/* 271 */     return 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getFunctionCompilationLevel() {
/* 276 */     return 2;
/*     */   }
/*     */   
/*     */   public void setUUID(UUID $$0) {
/* 280 */     this.uuid = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSingleplayerOwner(GameProfile $$0) {
/* 285 */     return (getSingleplayerProfile() != null && $$0.getName().equalsIgnoreCase(getSingleplayerProfile().getName()));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getScaledTrackingDistance(int $$0) {
/* 290 */     return (int)(((Double)this.minecraft.options.entityDistanceScaling().get()).doubleValue() * $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean forceSynchronousWrites() {
/* 295 */     return this.minecraft.options.syncWrites;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public GameType getForcedGameType() {
/* 301 */     if (isPublished()) {
/* 302 */       return (GameType)MoreObjects.firstNonNull(this.publishedGameType, this.worldData.getGameType());
/*     */     }
/* 304 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\server\IntegratedServer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */