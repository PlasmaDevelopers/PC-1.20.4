/*     */ package net.minecraft.gametest.framework;
/*     */ 
/*     */ import com.google.common.base.Stopwatch;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.authlib.yggdrasil.ServicesKeySet;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.serialization.Lifecycle;
/*     */ import java.net.Proxy;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.function.BooleanSupplier;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.CrashReport;
/*     */ import net.minecraft.SystemReport;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.commands.Commands;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.LayeredRegistryAccess;
/*     */ import net.minecraft.core.MappedRegistry;
/*     */ import net.minecraft.core.Registry;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.server.RegistryLayer;
/*     */ import net.minecraft.server.Services;
/*     */ import net.minecraft.server.WorldLoader;
/*     */ import net.minecraft.server.WorldStem;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.server.level.progress.ChunkProgressListenerFactory;
/*     */ import net.minecraft.server.packs.repository.PackRepository;
/*     */ import net.minecraft.server.players.PlayerList;
/*     */ import net.minecraft.util.datafix.DataFixers;
/*     */ import net.minecraft.world.Difficulty;
/*     */ import net.minecraft.world.flag.FeatureFlags;
/*     */ import net.minecraft.world.level.DataPackConfig;
/*     */ import net.minecraft.world.level.GameRules;
/*     */ import net.minecraft.world.level.GameType;
/*     */ import net.minecraft.world.level.LevelSettings;
/*     */ import net.minecraft.world.level.WorldDataConfiguration;
/*     */ import net.minecraft.world.level.block.Rotation;
/*     */ import net.minecraft.world.level.dimension.LevelStem;
/*     */ import net.minecraft.world.level.levelgen.WorldDimensions;
/*     */ import net.minecraft.world.level.levelgen.WorldOptions;
/*     */ import net.minecraft.world.level.levelgen.presets.WorldPreset;
/*     */ import net.minecraft.world.level.levelgen.presets.WorldPresets;
/*     */ import net.minecraft.world.level.storage.LevelStorageSource;
/*     */ import net.minecraft.world.level.storage.PlayerDataStorage;
/*     */ import net.minecraft.world.level.storage.PrimaryLevelData;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class GameTestServer extends MinecraftServer {
/*  56 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */ 
/*     */   
/*     */   private static final int PROGRESS_REPORT_INTERVAL = 20;
/*     */ 
/*     */   
/*     */   private static final int TEST_POSITION_RANGE = 14999992;
/*     */   
/*  64 */   private static final Services NO_SERVICES = new Services(null, ServicesKeySet.EMPTY, null, null);
/*     */   
/*     */   private final List<GameTestBatch> testBatches;
/*     */   
/*     */   private final BlockPos spawnPos;
/*     */   
/*     */   private static final GameRules TEST_GAME_RULES;
/*     */ 
/*     */   
/*     */   static {
/*  74 */     TEST_GAME_RULES = (GameRules)Util.make(new GameRules(), $$0 -> {
/*     */           ((GameRules.BooleanValue)$$0.getRule(GameRules.RULE_DOMOBSPAWNING)).set(false, null);
/*     */           ((GameRules.BooleanValue)$$0.getRule(GameRules.RULE_WEATHER_CYCLE)).set(false, null);
/*     */         });
/*     */   }
/*  79 */   private static final WorldOptions WORLD_OPTIONS = new WorldOptions(0L, false, false);
/*     */   
/*     */   @Nullable
/*     */   private MultipleTestTracker testTracker;
/*     */   
/*     */   public static GameTestServer create(Thread $$0, LevelStorageSource.LevelStorageAccess $$1, PackRepository $$2, Collection<GameTestBatch> $$3, BlockPos $$4) {
/*  85 */     if ($$3.isEmpty()) {
/*  86 */       throw new IllegalArgumentException("No test batches were given!");
/*     */     }
/*     */ 
/*     */     
/*  90 */     $$2.reload();
/*     */ 
/*     */ 
/*     */     
/*  94 */     WorldDataConfiguration $$5 = new WorldDataConfiguration(new DataPackConfig(new ArrayList($$2.getAvailableIds()), List.of()), FeatureFlags.REGISTRY.allFlags());
/*     */ 
/*     */     
/*  97 */     LevelSettings $$6 = new LevelSettings("Test Level", GameType.CREATIVE, false, Difficulty.NORMAL, true, TEST_GAME_RULES, $$5);
/*     */     
/*  99 */     WorldLoader.PackConfig $$7 = new WorldLoader.PackConfig($$2, $$5, false, true);
/* 100 */     WorldLoader.InitConfig $$8 = new WorldLoader.InitConfig($$7, Commands.CommandSelection.DEDICATED, 4);
/*     */     
/*     */     try {
/* 103 */       LOGGER.debug("Starting resource loading");
/* 104 */       Stopwatch $$9 = Stopwatch.createStarted();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 121 */       WorldStem $$10 = Util.blockUntilDone($$2 -> WorldLoader.load($$0, (), WorldStem::new, Util.backgroundExecutor(), $$2)).get();
/* 122 */       $$9.stop();
/* 123 */       LOGGER.debug("Finished resource loading after {} ms", Long.valueOf($$9.elapsed(TimeUnit.MILLISECONDS)));
/* 124 */       return new GameTestServer($$0, $$1, $$2, $$10, $$3, $$4);
/* 125 */     } catch (Exception $$11) {
/* 126 */       LOGGER.warn("Failed to load vanilla datapack, bit oops", $$11);
/* 127 */       System.exit(-1);
/* 128 */       throw new IllegalStateException();
/*     */     } 
/*     */   }
/*     */   
/*     */   private GameTestServer(Thread $$0, LevelStorageSource.LevelStorageAccess $$1, PackRepository $$2, WorldStem $$3, Collection<GameTestBatch> $$4, BlockPos $$5) {
/* 133 */     super($$0, $$1, $$2, $$3, Proxy.NO_PROXY, DataFixers.getDataFixer(), NO_SERVICES, net.minecraft.server.level.progress.LoggerChunkProgressListener::new);
/* 134 */     this.testBatches = Lists.newArrayList($$4);
/* 135 */     this.spawnPos = $$5;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean initServer() {
/* 140 */     setPlayerList(new PlayerList(this, registries(), this.playerDataStorage, 1) {  });
/* 141 */     loadLevel();
/* 142 */     ServerLevel $$0 = overworld();
/* 143 */     $$0.setDefaultSpawnPos(this.spawnPos, 0.0F);
/* 144 */     int $$1 = 20000000;
/* 145 */     $$0.setWeatherParameters(20000000, 20000000, false, false);
/* 146 */     LOGGER.info("Started game test server");
/* 147 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tickServer(BooleanSupplier $$0) {
/* 152 */     super.tickServer($$0);
/*     */     
/* 154 */     ServerLevel $$1 = overworld();
/*     */     
/* 156 */     if (!haveTestsStarted()) {
/* 157 */       startTests($$1);
/*     */     }
/*     */     
/* 160 */     if ($$1.getGameTime() % 20L == 0L) {
/* 161 */       LOGGER.info(this.testTracker.getProgressBar());
/*     */     }
/*     */     
/* 164 */     if (this.testTracker.isDone()) {
/* 165 */       halt(false);
/* 166 */       LOGGER.info(this.testTracker.getProgressBar());
/*     */       
/* 168 */       GlobalTestReporter.finish();
/*     */       
/* 170 */       LOGGER.info("========= {} GAME TESTS COMPLETE ======================", Integer.valueOf(this.testTracker.getTotalCount()));
/* 171 */       if (this.testTracker.hasFailedRequired()) {
/* 172 */         LOGGER.info("{} required tests failed :(", Integer.valueOf(this.testTracker.getFailedRequiredCount()));
/* 173 */         this.testTracker.getFailedRequired().forEach($$0 -> LOGGER.info("   - {}", $$0.getTestName()));
/*     */       } else {
/* 175 */         LOGGER.info("All {} required tests passed :)", Integer.valueOf(this.testTracker.getTotalCount()));
/*     */       } 
/* 177 */       if (this.testTracker.hasFailedOptional()) {
/* 178 */         LOGGER.info("{} optional tests failed", Integer.valueOf(this.testTracker.getFailedOptionalCount()));
/* 179 */         this.testTracker.getFailedOptional().forEach($$0 -> LOGGER.info("   - {}", $$0.getTestName()));
/*     */       } 
/* 181 */       LOGGER.info("====================================================");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void waitUntilNextTick() {
/* 187 */     runAllTasks();
/*     */   }
/*     */ 
/*     */   
/*     */   public SystemReport fillServerSystemReport(SystemReport $$0) {
/* 192 */     $$0.setDetail("Type", "Game test server");
/* 193 */     return $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onServerExit() {
/* 198 */     super.onServerExit();
/* 199 */     LOGGER.info("Game test server shutting down");
/* 200 */     System.exit(this.testTracker.getFailedRequiredCount());
/*     */   }
/*     */ 
/*     */   
/*     */   public void onServerCrash(CrashReport $$0) {
/* 205 */     super.onServerCrash($$0);
/* 206 */     LOGGER.error("Game test server crashed\n{}", $$0.getFriendlyReport());
/* 207 */     System.exit(1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void startTests(ServerLevel $$0) {
/* 214 */     BlockPos $$1 = new BlockPos($$0.random.nextIntBetweenInclusive(-14999992, 14999992), -59, $$0.random.nextIntBetweenInclusive(-14999992, 14999992));
/*     */     
/* 216 */     Collection<GameTestInfo> $$2 = GameTestRunner.runTestBatches(this.testBatches, $$1, Rotation.NONE, $$0, GameTestTicker.SINGLETON, 8);
/* 217 */     this.testTracker = new MultipleTestTracker($$2);
/* 218 */     LOGGER.info("{} tests are now running at position {}!", Integer.valueOf(this.testTracker.getTotalCount()), $$1.toShortString());
/*     */   }
/*     */   
/*     */   private boolean haveTestsStarted() {
/* 222 */     return (this.testTracker != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isHardcore() {
/* 227 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getOperatorUserPermissionLevel() {
/* 232 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getFunctionCompilationLevel() {
/* 237 */     return 4;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldRconBroadcast() {
/* 242 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDedicatedServer() {
/* 247 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRateLimitPacketsPerSecond() {
/* 252 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEpollEnabled() {
/* 257 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCommandBlockEnabled() {
/* 262 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPublished() {
/* 267 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldInformAdmins() {
/* 272 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSingleplayerOwner(GameProfile $$0) {
/* 277 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\gametest\framework\GameTestServer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */