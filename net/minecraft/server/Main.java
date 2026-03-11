/*     */ package net.minecraft.server;
/*     */ import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
/*     */ import com.mojang.datafixers.DataFixer;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.serialization.Dynamic;
/*     */ import com.mojang.serialization.Lifecycle;
/*     */ import java.awt.GraphicsEnvironment;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.UncheckedIOException;
/*     */ import java.net.Proxy;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.Paths;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.function.BooleanSupplier;
/*     */ import javax.annotation.Nullable;
/*     */ import joptsimple.AbstractOptionSpec;
/*     */ import joptsimple.ArgumentAcceptingOptionSpec;
/*     */ import joptsimple.OptionParser;
/*     */ import joptsimple.OptionSet;
/*     */ import joptsimple.OptionSpec;
/*     */ import joptsimple.OptionSpecBuilder;
/*     */ import joptsimple.util.PathConverter;
/*     */ import net.minecraft.CrashReport;
/*     */ import net.minecraft.DefaultUncaughtExceptionHandler;
/*     */ import net.minecraft.SharedConstants;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.commands.Commands;
/*     */ import net.minecraft.core.Registry;
/*     */ import net.minecraft.core.RegistryAccess;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.obfuscate.DontObfuscate;
/*     */ import net.minecraft.server.dedicated.DedicatedServer;
/*     */ import net.minecraft.server.dedicated.DedicatedServerProperties;
/*     */ import net.minecraft.server.dedicated.DedicatedServerSettings;
/*     */ import net.minecraft.server.level.progress.ChunkProgressListenerFactory;
/*     */ import net.minecraft.server.packs.repository.PackRepository;
/*     */ import net.minecraft.server.packs.repository.ServerPacksSource;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.datafix.DataFixers;
/*     */ import net.minecraft.util.profiling.jfr.Environment;
/*     */ import net.minecraft.util.profiling.jfr.JvmProfiler;
/*     */ import net.minecraft.util.worldupdate.WorldUpgrader;
/*     */ import net.minecraft.world.flag.FeatureFlags;
/*     */ import net.minecraft.world.level.GameRules;
/*     */ import net.minecraft.world.level.LevelSettings;
/*     */ import net.minecraft.world.level.WorldDataConfiguration;
/*     */ import net.minecraft.world.level.dimension.LevelStem;
/*     */ import net.minecraft.world.level.levelgen.WorldDimensions;
/*     */ import net.minecraft.world.level.levelgen.WorldOptions;
/*     */ import net.minecraft.world.level.levelgen.presets.WorldPresets;
/*     */ import net.minecraft.world.level.storage.LevelDataAndDimensions;
/*     */ import net.minecraft.world.level.storage.LevelStorageSource;
/*     */ import net.minecraft.world.level.storage.LevelSummary;
/*     */ import net.minecraft.world.level.storage.PrimaryLevelData;
/*     */ import net.minecraft.world.level.storage.WorldData;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class Main {
/*  63 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   @DontObfuscate
/*     */   public static void main(String[] $$0) {
/*  67 */     SharedConstants.tryDetectVersion();
/*     */     
/*  69 */     OptionParser $$1 = new OptionParser();
/*  70 */     OptionSpecBuilder optionSpecBuilder1 = $$1.accepts("nogui");
/*  71 */     OptionSpecBuilder optionSpecBuilder2 = $$1.accepts("initSettings", "Initializes 'server.properties' and 'eula.txt', then quits");
/*  72 */     OptionSpecBuilder optionSpecBuilder3 = $$1.accepts("demo");
/*  73 */     OptionSpecBuilder optionSpecBuilder4 = $$1.accepts("bonusChest");
/*  74 */     OptionSpecBuilder optionSpecBuilder5 = $$1.accepts("forceUpgrade");
/*  75 */     OptionSpecBuilder optionSpecBuilder6 = $$1.accepts("eraseCache");
/*  76 */     OptionSpecBuilder optionSpecBuilder7 = $$1.accepts("safeMode", "Loads level with vanilla datapack only");
/*  77 */     AbstractOptionSpec abstractOptionSpec = $$1.accepts("help").forHelp();
/*  78 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec1 = $$1.accepts("universe").withRequiredArg().defaultsTo(".", (Object[])new String[0]);
/*  79 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec2 = $$1.accepts("world").withRequiredArg();
/*  80 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec3 = $$1.accepts("port").withRequiredArg().ofType(Integer.class).defaultsTo(Integer.valueOf(-1), (Object[])new Integer[0]);
/*  81 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec4 = $$1.accepts("serverId").withRequiredArg();
/*  82 */     OptionSpecBuilder optionSpecBuilder8 = $$1.accepts("jfrProfile");
/*  83 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec5 = $$1.accepts("pidFile").withRequiredArg().withValuesConvertedBy((ValueConverter)new PathConverter(new joptsimple.util.PathProperties[0]));
/*  84 */     NonOptionArgumentSpec nonOptionArgumentSpec = $$1.nonOptions(); try {
/*     */       Dynamic<?> $$37;
/*     */       WorldStem $$42;
/*  87 */       OptionSet $$17 = $$1.parse($$0);
/*     */       
/*  89 */       if ($$17.has((OptionSpec)abstractOptionSpec)) {
/*  90 */         $$1.printHelpOn(System.err);
/*     */         
/*     */         return;
/*     */       } 
/*  94 */       Path $$18 = (Path)$$17.valueOf((OptionSpec)argumentAcceptingOptionSpec5);
/*  95 */       if ($$18 != null) {
/*  96 */         writePidFile($$18);
/*     */       }
/*     */       
/*  99 */       CrashReport.preload();
/*     */ 
/*     */       
/* 102 */       if ($$17.has((OptionSpec)optionSpecBuilder8)) {
/* 103 */         JvmProfiler.INSTANCE.start(Environment.SERVER);
/*     */       }
/*     */       
/* 106 */       Bootstrap.bootStrap();
/* 107 */       Bootstrap.validate();
/*     */       
/* 109 */       Util.startTimerHackThread();
/*     */       
/* 111 */       Path $$19 = Paths.get("server.properties", new String[0]);
/* 112 */       DedicatedServerSettings $$20 = new DedicatedServerSettings($$19);
/* 113 */       $$20.forceSave();
/*     */       
/* 115 */       Path $$21 = Paths.get("eula.txt", new String[0]);
/* 116 */       Eula $$22 = new Eula($$21);
/*     */       
/* 118 */       if ($$17.has((OptionSpec)optionSpecBuilder2)) {
/* 119 */         LOGGER.info("Initialized '{}' and '{}'", $$19.toAbsolutePath(), $$21.toAbsolutePath());
/*     */         
/*     */         return;
/*     */       } 
/* 123 */       if (!$$22.hasAgreedToEULA()) {
/* 124 */         LOGGER.info("You need to agree to the EULA in order to run the server. Go to eula.txt for more info.");
/*     */         
/*     */         return;
/*     */       } 
/* 128 */       File $$23 = new File((String)$$17.valueOf((OptionSpec)argumentAcceptingOptionSpec1));
/* 129 */       Services $$24 = Services.create(new YggdrasilAuthenticationService(Proxy.NO_PROXY), $$23);
/*     */       
/* 131 */       String $$25 = Optional.<String>ofNullable((String)$$17.valueOf((OptionSpec)argumentAcceptingOptionSpec2)).orElse(($$20.getProperties()).levelName);
/* 132 */       LevelStorageSource $$26 = LevelStorageSource.createDefault($$23.toPath());
/* 133 */       LevelStorageSource.LevelStorageAccess $$27 = $$26.validateAndCreateAccess($$25);
/*     */ 
/*     */       
/* 136 */       if ($$27.hasWorldData()) {
/*     */         LevelSummary $$33;
/*     */         try {
/* 139 */           Dynamic<?> $$28 = $$27.getDataTag();
/* 140 */           LevelSummary $$29 = $$27.getSummary($$28);
/* 141 */         } catch (IOException|net.minecraft.nbt.NbtException|net.minecraft.nbt.ReportedNbtException $$30) {
/* 142 */           LevelStorageSource.LevelDirectory $$31 = $$27.getLevelDirectory();
/* 143 */           LOGGER.warn("Failed to load world data from {}", $$31.dataFile(), $$30);
/* 144 */           LOGGER.info("Attempting to use fallback");
/*     */           try {
/* 146 */             Dynamic<?> $$32 = $$27.getDataTagFallback();
/* 147 */             $$33 = $$27.getSummary($$32);
/* 148 */           } catch (IOException|net.minecraft.nbt.NbtException|net.minecraft.nbt.ReportedNbtException $$34) {
/* 149 */             LOGGER.error("Failed to load world data from {}", $$31.oldDataFile(), $$34);
/* 150 */             LOGGER.error("Failed to load world data from {} and {}. World files may be corrupted. Shutting down.", $$31.dataFile(), $$31.oldDataFile());
/*     */             return;
/*     */           } 
/* 153 */           $$27.restoreLevelDataFromOld();
/*     */         } 
/* 155 */         if ($$33.requiresManualConversion()) {
/* 156 */           LOGGER.info("This world must be opened in an older version (like 1.6.4) to be safely converted");
/*     */           return;
/*     */         } 
/* 159 */         if (!$$33.isCompatible()) {
/* 160 */           LOGGER.info("This world was created by an incompatible version.");
/*     */           return;
/*     */         } 
/*     */       } else {
/* 164 */         $$37 = null;
/*     */       } 
/* 166 */       Dynamic<?> $$38 = $$37;
/*     */       
/* 168 */       boolean $$39 = $$17.has((OptionSpec)optionSpecBuilder7);
/* 169 */       if ($$39) {
/* 170 */         LOGGER.warn("Safe mode active, only vanilla datapack will be loaded");
/*     */       }
/*     */       
/* 173 */       PackRepository $$40 = ServerPacksSource.createPackRepository($$27);
/*     */ 
/*     */       
/*     */       try {
/* 177 */         WorldLoader.InitConfig $$41 = loadOrCreateConfig($$20.getProperties(), $$38, $$39, $$40);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 225 */         $$42 = Util.blockUntilDone($$6 -> WorldLoader.load($$0, (), WorldStem::new, Util.backgroundExecutor(), $$6)).get();
/* 226 */       } catch (Exception $$43) {
/* 227 */         LOGGER.warn("Failed to load datapacks, can't proceed with server load. You can either fix your datapacks or reset to vanilla with --safeMode", $$43);
/*     */         
/*     */         return;
/*     */       } 
/* 231 */       RegistryAccess.Frozen $$45 = $$42.registries().compositeAccess();
/*     */       
/* 233 */       if ($$17.has((OptionSpec)optionSpecBuilder5)) {
/* 234 */         forceUpgrade($$27, DataFixers.getDataFixer(), $$17.has((OptionSpec)optionSpecBuilder6), () -> true, $$45.registryOrThrow(Registries.LEVEL_STEM));
/*     */       }
/*     */       
/* 237 */       WorldData $$46 = $$42.worldData();
/* 238 */       $$27.saveDataTag((RegistryAccess)$$45, $$46);
/*     */       
/* 240 */       final DedicatedServer dedicatedServer = MinecraftServer.<DedicatedServer>spin($$11 -> {
/*     */             DedicatedServer $$12 = new DedicatedServer($$11, $$0, $$1, $$2, $$3, DataFixers.getDataFixer(), $$4, net.minecraft.server.level.progress.LoggerChunkProgressListener::new);
/*     */             
/*     */             $$12.setPort(((Integer)$$5.valueOf($$6)).intValue());
/*     */             
/*     */             $$12.setDemo($$5.has($$7));
/*     */             $$12.setId((String)$$5.valueOf($$8));
/* 247 */             boolean $$13 = (!$$5.has($$9) && !$$5.valuesOf($$10).contains("nogui"));
/*     */             
/*     */             if ($$13 && !GraphicsEnvironment.isHeadless()) {
/*     */               $$12.showGui();
/*     */             }
/*     */             return $$12;
/*     */           });
/* 254 */       Thread $$48 = new Thread("Server Shutdown Thread")
/*     */         {
/*     */           public void run() {
/* 257 */             dedicatedServer.halt(true);
/*     */           }
/*     */         };
/* 260 */       $$48.setUncaughtExceptionHandler((Thread.UncaughtExceptionHandler)new DefaultUncaughtExceptionHandler(LOGGER));
/* 261 */       Runtime.getRuntime().addShutdownHook($$48);
/* 262 */     } catch (Exception $$49) {
/* 263 */       LOGGER.error(LogUtils.FATAL_MARKER, "Failed to start the minecraft server", $$49);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void writePidFile(Path $$0) {
/*     */     try {
/* 269 */       long $$1 = ProcessHandle.current().pid();
/* 270 */       Files.writeString($$0, Long.toString($$1), new java.nio.file.OpenOption[0]);
/* 271 */     } catch (IOException $$2) {
/* 272 */       throw new UncheckedIOException($$2);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static WorldLoader.InitConfig loadOrCreateConfig(DedicatedServerProperties $$0, @Nullable Dynamic<?> $$1, boolean $$2, PackRepository $$3) {
/*     */     boolean $$7;
/*     */     WorldDataConfiguration $$8;
/* 279 */     if ($$1 != null) {
/* 280 */       WorldDataConfiguration $$4 = LevelStorageSource.readDataConfig($$1);
/* 281 */       boolean $$5 = false;
/* 282 */       WorldDataConfiguration $$6 = $$4;
/*     */     } else {
/* 284 */       $$7 = true;
/* 285 */       $$8 = new WorldDataConfiguration($$0.initialDataPackConfiguration, FeatureFlags.DEFAULT_FLAGS);
/*     */     } 
/* 287 */     WorldLoader.PackConfig $$9 = new WorldLoader.PackConfig($$3, $$8, $$2, $$7);
/* 288 */     return new WorldLoader.InitConfig($$9, Commands.CommandSelection.DEDICATED, $$0.functionPermissionLevel);
/*     */   }
/*     */   
/*     */   private static void forceUpgrade(LevelStorageSource.LevelStorageAccess $$0, DataFixer $$1, boolean $$2, BooleanSupplier $$3, Registry<LevelStem> $$4) {
/* 292 */     LOGGER.info("Forcing world upgrade!");
/*     */     
/* 294 */     WorldUpgrader $$5 = new WorldUpgrader($$0, $$1, $$4, $$2);
/* 295 */     Component $$6 = null;
/* 296 */     while (!$$5.isFinished()) {
/* 297 */       Component $$7 = $$5.getStatus();
/* 298 */       if ($$6 != $$7) {
/* 299 */         $$6 = $$7;
/* 300 */         LOGGER.info($$5.getStatus().getString());
/*     */       } 
/* 302 */       int $$8 = $$5.getTotalChunks();
/* 303 */       if ($$8 > 0) {
/* 304 */         int $$9 = $$5.getConverted() + $$5.getSkipped();
/* 305 */         LOGGER.info("{}% completed ({} / {} chunks)...", new Object[] { Integer.valueOf(Mth.floor($$9 / $$8 * 100.0F)), Integer.valueOf($$9), Integer.valueOf($$8) });
/*     */       } 
/*     */       
/* 308 */       if (!$$3.getAsBoolean()) {
/* 309 */         $$5.cancel(); continue;
/*     */       } 
/*     */       try {
/* 312 */         Thread.sleep(1000L);
/* 313 */       } catch (InterruptedException interruptedException) {}
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\Main.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */