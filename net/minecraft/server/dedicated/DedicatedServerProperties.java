/*     */ package net.minecraft.server.dedicated;
/*     */ import com.google.common.base.Splitter;
/*     */ import com.google.common.base.Strings;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.serialization.Dynamic;
/*     */ import com.mojang.serialization.DynamicOps;
/*     */ import com.mojang.serialization.JsonOps;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.nio.file.Path;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.Properties;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.HolderLookup;
/*     */ import net.minecraft.core.Registry;
/*     */ import net.minecraft.core.RegistryAccess;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.RegistryOps;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.GsonHelper;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.Difficulty;
/*     */ import net.minecraft.world.level.DataPackConfig;
/*     */ import net.minecraft.world.level.GameType;
/*     */ import net.minecraft.world.level.WorldDataConfiguration;
/*     */ import net.minecraft.world.level.chunk.ChunkGenerator;
/*     */ import net.minecraft.world.level.levelgen.FlatLevelSource;
/*     */ import net.minecraft.world.level.levelgen.WorldDimensions;
/*     */ import net.minecraft.world.level.levelgen.WorldOptions;
/*     */ import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorSettings;
/*     */ import net.minecraft.world.level.levelgen.presets.WorldPreset;
/*     */ import net.minecraft.world.level.levelgen.presets.WorldPresets;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class DedicatedServerProperties extends Settings<DedicatedServerProperties> {
/*  48 */   static final Logger LOGGER = LogUtils.getLogger();
/*  49 */   private static final Pattern SHA1 = Pattern.compile("^[a-fA-F0-9]{40}$");
/*  50 */   private static final Splitter COMMA_SPLITTER = Splitter.on(',').trimResults();
/*     */   
/*  52 */   public final boolean onlineMode = get("online-mode", true);
/*  53 */   public final boolean preventProxyConnections = get("prevent-proxy-connections", false);
/*  54 */   public final String serverIp = get("server-ip", "");
/*  55 */   public final boolean spawnAnimals = get("spawn-animals", true);
/*  56 */   public final boolean spawnNpcs = get("spawn-npcs", true);
/*  57 */   public final boolean pvp = get("pvp", true);
/*  58 */   public final boolean allowFlight = get("allow-flight", false);
/*  59 */   public final String motd = get("motd", "A Minecraft Server");
/*  60 */   public final boolean forceGameMode = get("force-gamemode", false);
/*  61 */   public final boolean enforceWhitelist = get("enforce-whitelist", false);
/*  62 */   public final Difficulty difficulty = (Difficulty)get("difficulty", dispatchNumberOrString(Difficulty::byId, Difficulty::byName), Difficulty::getKey, Difficulty.EASY);
/*  63 */   public final GameType gamemode = (GameType)get("gamemode", dispatchNumberOrString(GameType::byId, GameType::byName), GameType::getName, GameType.SURVIVAL);
/*  64 */   public final String levelName = get("level-name", "world");
/*  65 */   public final int serverPort = get("server-port", 25565);
/*     */   @Nullable
/*  67 */   public final Boolean announcePlayerAchievements = getLegacyBoolean("announce-player-achievements");
/*  68 */   public final boolean enableQuery = get("enable-query", false);
/*  69 */   public final int queryPort = get("query.port", 25565);
/*  70 */   public final boolean enableRcon = get("enable-rcon", false);
/*  71 */   public final int rconPort = get("rcon.port", 25575);
/*  72 */   public final String rconPassword = get("rcon.password", "");
/*  73 */   public final boolean hardcore = get("hardcore", false);
/*  74 */   public final boolean allowNether = get("allow-nether", true);
/*  75 */   public final boolean spawnMonsters = get("spawn-monsters", true);
/*  76 */   public final boolean useNativeTransport = get("use-native-transport", true);
/*  77 */   public final boolean enableCommandBlock = get("enable-command-block", false);
/*  78 */   public final int spawnProtection = get("spawn-protection", 16);
/*  79 */   public final int opPermissionLevel = get("op-permission-level", 4);
/*  80 */   public final int functionPermissionLevel = get("function-permission-level", 2);
/*  81 */   public final long maxTickTime = get("max-tick-time", TimeUnit.MINUTES.toMillis(1L));
/*  82 */   public final int maxChainedNeighborUpdates = get("max-chained-neighbor-updates", 1000000);
/*  83 */   public final int rateLimitPacketsPerSecond = get("rate-limit", 0);
/*  84 */   public final int viewDistance = get("view-distance", 10);
/*  85 */   public final int simulationDistance = get("simulation-distance", 10);
/*  86 */   public final int maxPlayers = get("max-players", 20);
/*  87 */   public final int networkCompressionThreshold = get("network-compression-threshold", 256);
/*  88 */   public final boolean broadcastRconToOps = get("broadcast-rcon-to-ops", true);
/*  89 */   public final boolean broadcastConsoleToOps = get("broadcast-console-to-ops", true);
/*     */   
/*     */   public final int maxWorldSize;
/*     */   
/*     */   public final boolean syncChunkWrites;
/*     */   
/*     */   public final boolean enableJmxMonitoring;
/*     */   public final boolean enableStatus;
/*     */   public final boolean hideOnlinePlayers;
/*     */   public final int entityBroadcastRangePercentage;
/*     */   public final String textFilteringConfig;
/*     */   public final Optional<MinecraftServer.ServerResourcePackInfo> serverResourcePackInfo;
/*     */   public final DataPackConfig initialDataPackConfiguration;
/*     */   public final Settings<DedicatedServerProperties>.MutableValue<Integer> playerIdleTimeout;
/*     */   public final Settings<DedicatedServerProperties>.MutableValue<Boolean> whiteList;
/*     */   public final boolean enforceSecureProfile;
/*     */   public final boolean logIPs;
/*     */   private final WorldDimensionData worldDimensionData;
/*     */   public final WorldOptions worldOptions;
/*     */   
/*     */   public DedicatedServerProperties(Properties $$0) {
/* 110 */     super($$0); this.maxWorldSize = get("max-world-size", $$0 -> Integer.valueOf(Mth.clamp($$0.intValue(), 1, 29999984)), 29999984); this.syncChunkWrites = get("sync-chunk-writes", true); this.enableJmxMonitoring = get("enable-jmx-monitoring", false); this.enableStatus = get("enable-status", true); this.hideOnlinePlayers = get("hide-online-players", false); this.entityBroadcastRangePercentage = get("entity-broadcast-range-percentage", $$0 -> Integer.valueOf(Mth.clamp($$0.intValue(), 10, 1000)), 100); this.textFilteringConfig = get("text-filtering-config", ""); this.playerIdleTimeout = getMutable("player-idle-timeout", 0); this.whiteList = getMutable("white-list", false); this.enforceSecureProfile = get("enforce-secure-profile", true);
/*     */     this.logIPs = get("log-ips", true);
/* 112 */     String $$1 = get("level-seed", "");
/* 113 */     boolean $$2 = get("generate-structures", true);
/*     */     
/* 115 */     long $$3 = WorldOptions.parseSeed($$1).orElse(WorldOptions.randomSeed());
/* 116 */     this.worldOptions = new WorldOptions($$3, $$2, false);
/*     */     
/* 118 */     this
/*     */       
/* 120 */       .worldDimensionData = new WorldDimensionData((JsonObject)get("generator-settings", $$0 -> GsonHelper.parse(!$$0.isEmpty() ? $$0 : "{}"), new JsonObject()), (String)get("level-type", $$0 -> $$0.toLowerCase(Locale.ROOT), WorldPresets.NORMAL.location().toString()));
/*     */     
/* 122 */     this.serverResourcePackInfo = getServerPackInfo(
/* 123 */         get("resource-pack-id", ""), 
/* 124 */         get("resource-pack", ""), 
/* 125 */         get("resource-pack-sha1", ""), 
/* 126 */         getLegacyString("resource-pack-hash"), 
/* 127 */         get("require-resource-pack", false), 
/* 128 */         get("resource-pack-prompt", ""));
/*     */ 
/*     */     
/* 131 */     this.initialDataPackConfiguration = getDatapackConfig(
/* 132 */         get("initial-enabled-packs", String.join(",", WorldDataConfiguration.DEFAULT.dataPacks().getEnabled())), 
/* 133 */         get("initial-disabled-packs", String.join(",", WorldDataConfiguration.DEFAULT.dataPacks().getDisabled())));
/*     */   }
/*     */ 
/*     */   
/*     */   public static DedicatedServerProperties fromFile(Path $$0) {
/* 138 */     return new DedicatedServerProperties(loadFromFile($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   protected DedicatedServerProperties reload(RegistryAccess $$0, Properties $$1) {
/* 143 */     return new DedicatedServerProperties($$1);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private static Component parseResourcePackPrompt(String $$0) {
/* 148 */     if (!Strings.isNullOrEmpty($$0)) {
/*     */       try {
/* 150 */         return (Component)Component.Serializer.fromJson($$0);
/* 151 */       } catch (Exception $$1) {
/* 152 */         LOGGER.warn("Failed to parse resource pack prompt '{}'", $$0, $$1);
/*     */       } 
/*     */     }
/* 155 */     return null;
/*     */   } private static Optional<MinecraftServer.ServerResourcePackInfo> getServerPackInfo(String $$0, String $$1, String $$2, @Nullable String $$3, boolean $$4, String $$5) {
/*     */     String $$8;
/*     */     UUID $$11;
/* 159 */     if ($$1.isEmpty()) {
/* 160 */       return Optional.empty();
/*     */     }
/*     */ 
/*     */     
/* 164 */     if (!$$2.isEmpty()) {
/* 165 */       String $$6 = $$2;
/* 166 */       if (!Strings.isNullOrEmpty($$3)) {
/* 167 */         LOGGER.warn("resource-pack-hash is deprecated and found along side resource-pack-sha1. resource-pack-hash will be ignored.");
/*     */       }
/* 169 */     } else if (!Strings.isNullOrEmpty($$3)) {
/* 170 */       LOGGER.warn("resource-pack-hash is deprecated. Please use resource-pack-sha1 instead.");
/* 171 */       String $$7 = $$3;
/*     */     } else {
/* 173 */       $$8 = "";
/*     */     } 
/*     */     
/* 176 */     if ($$8.isEmpty()) {
/* 177 */       LOGGER.warn("You specified a resource pack without providing a sha1 hash. Pack will be updated on the client only if you change the name of the pack.");
/* 178 */     } else if (!SHA1.matcher($$8).matches()) {
/* 179 */       LOGGER.warn("Invalid sha1 for resource-pack-sha1");
/*     */     } 
/*     */     
/* 182 */     Component $$9 = parseResourcePackPrompt($$5);
/*     */ 
/*     */     
/* 185 */     if ($$0.isEmpty()) {
/* 186 */       UUID $$10 = UUID.nameUUIDFromBytes($$1.getBytes(StandardCharsets.UTF_8));
/* 187 */       LOGGER.warn("resource-pack-id missing, using default of {}", $$10);
/*     */     } else {
/*     */       try {
/* 190 */         $$11 = UUID.fromString($$0);
/* 191 */       } catch (IllegalArgumentException $$12) {
/* 192 */         LOGGER.warn("Failed to parse '{}' into UUID", $$0);
/* 193 */         return Optional.empty();
/*     */       } 
/*     */     } 
/*     */     
/* 197 */     return Optional.of(new MinecraftServer.ServerResourcePackInfo($$11, $$1, $$8, $$4, $$9));
/*     */   }
/*     */   
/*     */   private static DataPackConfig getDatapackConfig(String $$0, String $$1) {
/* 201 */     List<String> $$2 = COMMA_SPLITTER.splitToList($$0);
/* 202 */     List<String> $$3 = COMMA_SPLITTER.splitToList($$1);
/* 203 */     return new DataPackConfig($$2, $$3);
/*     */   }
/*     */   
/*     */   public WorldDimensions createDimensions(RegistryAccess $$0) {
/* 207 */     return this.worldDimensionData.create($$0);
/*     */   }
/*     */   private static final class WorldDimensionData extends Record { private final JsonObject generatorSettings; private final String levelType;
/* 210 */     WorldDimensionData(JsonObject $$0, String $$1) { this.generatorSettings = $$0; this.levelType = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/server/dedicated/DedicatedServerProperties$WorldDimensionData;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #210	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 210 */       //   0	7	0	this	Lnet/minecraft/server/dedicated/DedicatedServerProperties$WorldDimensionData; } public JsonObject generatorSettings() { return this.generatorSettings; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/server/dedicated/DedicatedServerProperties$WorldDimensionData;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #210	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/server/dedicated/DedicatedServerProperties$WorldDimensionData; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/server/dedicated/DedicatedServerProperties$WorldDimensionData;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #210	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/server/dedicated/DedicatedServerProperties$WorldDimensionData;
/* 210 */       //   0	8	1	$$0	Ljava/lang/Object; } public String levelType() { return this.levelType; }
/* 211 */      private static final Map<String, ResourceKey<WorldPreset>> LEGACY_PRESET_NAMES = Map.of("default", WorldPresets.NORMAL, "largebiomes", WorldPresets.LARGE_BIOMES);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public WorldDimensions create(RegistryAccess $$0) {
/* 217 */       Registry<WorldPreset> $$1 = $$0.registryOrThrow(Registries.WORLD_PRESET);
/*     */       
/* 219 */       Holder.Reference<WorldPreset> $$2 = (Holder.Reference<WorldPreset>)$$1.getHolder(WorldPresets.NORMAL).or(() -> $$0.holders().findAny()).orElseThrow(() -> new IllegalStateException("Invalid datapack contents: can't find default preset"));
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 224 */       Objects.requireNonNull($$1);
/* 225 */       Holder<WorldPreset> $$3 = Optional.<ResourceLocation>ofNullable(ResourceLocation.tryParse(this.levelType)).map($$0 -> ResourceKey.create(Registries.WORLD_PRESET, $$0)).or(() -> Optional.ofNullable(LEGACY_PRESET_NAMES.get(this.levelType))).flatMap($$1::getHolder).orElseGet(() -> {
/*     */             DedicatedServerProperties.LOGGER.warn("Failed to parse level-type {}, defaulting to {}", this.levelType, $$0.key().location());
/*     */             
/*     */             return $$0;
/*     */           });
/* 230 */       WorldDimensions $$4 = ((WorldPreset)$$3.value()).createWorldDimensions();
/*     */ 
/*     */       
/* 233 */       if ($$3.is(WorldPresets.FLAT)) {
/* 234 */         RegistryOps<JsonElement> $$5 = RegistryOps.create((DynamicOps)JsonOps.INSTANCE, (HolderLookup.Provider)$$0);
/* 235 */         Objects.requireNonNull(DedicatedServerProperties.LOGGER); Optional<FlatLevelGeneratorSettings> $$6 = FlatLevelGeneratorSettings.CODEC.parse(new Dynamic((DynamicOps)$$5, generatorSettings())).resultOrPartial(DedicatedServerProperties.LOGGER::error);
/* 236 */         if ($$6.isPresent()) {
/* 237 */           return $$4.replaceOverworldGenerator($$0, (ChunkGenerator)new FlatLevelSource($$6.get()));
/*     */         }
/*     */       } 
/*     */       
/* 241 */       return $$4;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\dedicated\DedicatedServerProperties.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */