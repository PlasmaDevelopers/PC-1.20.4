/*     */ package net.minecraft.world.level.storage;
/*     */ import com.mojang.serialization.DataResult;
/*     */ import com.mojang.serialization.Dynamic;
/*     */ import com.mojang.serialization.DynamicOps;
/*     */ import com.mojang.serialization.Lifecycle;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.CrashReportCategory;
/*     */ import net.minecraft.SharedConstants;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.RegistryAccess;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.ListTag;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.resources.RegistryOps;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.world.level.GameType;
/*     */ import net.minecraft.world.level.LevelSettings;
/*     */ import net.minecraft.world.level.WorldDataConfiguration;
/*     */ import net.minecraft.world.level.border.WorldBorder;
/*     */ import net.minecraft.world.level.dimension.end.EndDragonFight;
/*     */ import net.minecraft.world.level.levelgen.WorldOptions;
/*     */ import net.minecraft.world.level.timers.TimerQueue;
/*     */ 
/*     */ public class PrimaryLevelData implements ServerLevelData, WorldData {
/*     */   public static final String LEVEL_NAME = "LevelName";
/*     */   protected static final String PLAYER = "Player";
/*     */   protected static final String WORLD_GEN_SETTINGS = "WorldGenSettings";
/*     */   private LevelSettings settings;
/*     */   private final WorldOptions worldOptions;
/*     */   private final SpecialWorldProperty specialWorldProperty;
/*     */   private final Lifecycle worldGenSettingsLifecycle;
/*     */   private int xSpawn;
/*     */   private int ySpawn;
/*     */   private int zSpawn;
/*     */   private float spawnAngle;
/*     */   private long gameTime;
/*     */   private long dayTime;
/*     */   @Nullable
/*     */   private final CompoundTag loadedPlayerTag;
/*     */   private final int version;
/*     */   private int clearWeatherTime;
/*  45 */   private static final Logger LOGGER = LogUtils.getLogger(); private boolean raining; private int rainTime; private boolean thundering; private int thunderTime; private boolean initialized;
/*     */   private boolean difficultyLocked;
/*     */   private WorldBorder.Settings worldBorder;
/*     */   private EndDragonFight.Data endDragonFightData;
/*     */   @Nullable
/*     */   private CompoundTag customBossEvents;
/*     */   private int wanderingTraderSpawnDelay;
/*     */   private int wanderingTraderSpawnChance;
/*     */   @Nullable
/*     */   private UUID wanderingTraderId;
/*     */   private final Set<String> knownServerBrands;
/*     */   private boolean wasModded;
/*     */   private final Set<String> removedFeatureFlags;
/*     */   private final TimerQueue<MinecraftServer> scheduledEvents;
/*     */   
/*     */   @Deprecated
/*  61 */   public enum SpecialWorldProperty { NONE,
/*  62 */     FLAT,
/*  63 */     DEBUG; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PrimaryLevelData(@Nullable CompoundTag $$0, boolean $$1, int $$2, int $$3, int $$4, float $$5, long $$6, long $$7, int $$8, int $$9, int $$10, boolean $$11, int $$12, boolean $$13, boolean $$14, boolean $$15, WorldBorder.Settings $$16, int $$17, int $$18, @Nullable UUID $$19, Set<String> $$20, Set<String> $$21, TimerQueue<MinecraftServer> $$22, @Nullable CompoundTag $$23, EndDragonFight.Data $$24, LevelSettings $$25, WorldOptions $$26, SpecialWorldProperty $$27, Lifecycle $$28) {
/* 138 */     this.wasModded = $$1;
/* 139 */     this.xSpawn = $$2;
/* 140 */     this.ySpawn = $$3;
/* 141 */     this.zSpawn = $$4;
/* 142 */     this.spawnAngle = $$5;
/* 143 */     this.gameTime = $$6;
/* 144 */     this.dayTime = $$7;
/* 145 */     this.version = $$8;
/* 146 */     this.clearWeatherTime = $$9;
/* 147 */     this.rainTime = $$10;
/* 148 */     this.raining = $$11;
/* 149 */     this.thunderTime = $$12;
/* 150 */     this.thundering = $$13;
/* 151 */     this.initialized = $$14;
/* 152 */     this.difficultyLocked = $$15;
/* 153 */     this.worldBorder = $$16;
/* 154 */     this.wanderingTraderSpawnDelay = $$17;
/* 155 */     this.wanderingTraderSpawnChance = $$18;
/* 156 */     this.wanderingTraderId = $$19;
/* 157 */     this.knownServerBrands = $$20;
/* 158 */     this.removedFeatureFlags = $$21;
/* 159 */     this.loadedPlayerTag = $$0;
/* 160 */     this.scheduledEvents = $$22;
/* 161 */     this.customBossEvents = $$23;
/* 162 */     this.endDragonFightData = $$24;
/* 163 */     this.settings = $$25;
/* 164 */     this.worldOptions = $$26;
/* 165 */     this.specialWorldProperty = $$27;
/* 166 */     this.worldGenSettingsLifecycle = $$28;
/*     */   }
/*     */   
/*     */   public PrimaryLevelData(LevelSettings $$0, WorldOptions $$1, SpecialWorldProperty $$2, Lifecycle $$3) {
/* 170 */     this(null, false, 0, 0, 0, 0.0F, 0L, 0L, 19133, 0, 0, false, 0, false, false, false, WorldBorder.DEFAULT_SETTINGS, 0, 0, null, 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 191 */         Sets.newLinkedHashSet(), new HashSet<>(), new TimerQueue(TimerCallbacks.SERVER_CALLBACKS), null, EndDragonFight.Data.DEFAULT, $$0
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 196 */         .copy(), $$1, $$2, $$3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> PrimaryLevelData parse(Dynamic<T> $$0, LevelSettings $$1, SpecialWorldProperty $$2, WorldOptions $$3, Lifecycle $$4) {
/* 204 */     long $$5 = $$0.get("Time").asLong(0L);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 231 */     Objects.requireNonNull(LOGGER); return new PrimaryLevelData(CompoundTag.CODEC.parse($$0.get("Player").orElseEmptyMap()).result().orElse(null), $$0.get("WasModded").asBoolean(false), $$0.get("SpawnX").asInt(0), $$0.get("SpawnY").asInt(0), $$0.get("SpawnZ").asInt(0), $$0.get("SpawnAngle").asFloat(0.0F), $$5, $$0.get("DayTime").asLong($$5), LevelVersion.parse($$0).levelDataVersion(), $$0.get("clearWeatherTime").asInt(0), $$0.get("rainTime").asInt(0), $$0.get("raining").asBoolean(false), $$0.get("thunderTime").asInt(0), $$0.get("thundering").asBoolean(false), $$0.get("initialized").asBoolean(true), $$0.get("DifficultyLocked").asBoolean(false), WorldBorder.Settings.read((DynamicLike)$$0, WorldBorder.DEFAULT_SETTINGS), $$0.get("WanderingTraderSpawnDelay").asInt(0), $$0.get("WanderingTraderSpawnChance").asInt(0), $$0.get("WanderingTraderId").read((Decoder)UUIDUtil.CODEC).result().orElse(null), (Set<String>)$$0.get("ServerBrands").asStream().flatMap($$0 -> $$0.asString().result().stream()).collect(Collectors.toCollection(Sets::newLinkedHashSet)), (Set<String>)$$0.get("removed_features").asStream().flatMap($$0 -> $$0.asString().result().stream()).collect(Collectors.toSet()), new TimerQueue(TimerCallbacks.SERVER_CALLBACKS, $$0.get("ScheduledEvents").asStream()), (CompoundTag)$$0.get("CustomBossEvents").orElseEmptyMap().getValue(), $$0.get("DragonFight").read((Decoder)EndDragonFight.Data.CODEC).resultOrPartial(LOGGER::error).orElse(EndDragonFight.Data.DEFAULT), $$1, $$3, $$2, $$4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CompoundTag createTag(RegistryAccess $$0, @Nullable CompoundTag $$1) {
/* 241 */     if ($$1 == null) {
/* 242 */       $$1 = this.loadedPlayerTag;
/*     */     }
/* 244 */     CompoundTag $$2 = new CompoundTag();
/* 245 */     setTagData($$0, $$2, $$1);
/* 246 */     return $$2;
/*     */   }
/*     */   
/*     */   private void setTagData(RegistryAccess $$0, CompoundTag $$1, @Nullable CompoundTag $$2) {
/* 250 */     $$1.put("ServerBrands", (Tag)stringCollectionToTag(this.knownServerBrands));
/* 251 */     $$1.putBoolean("WasModded", this.wasModded);
/*     */     
/* 253 */     if (!this.removedFeatureFlags.isEmpty()) {
/* 254 */       $$1.put("removed_features", (Tag)stringCollectionToTag(this.removedFeatureFlags));
/*     */     }
/*     */     
/* 257 */     CompoundTag $$3 = new CompoundTag();
/* 258 */     $$3.putString("Name", SharedConstants.getCurrentVersion().getName());
/* 259 */     $$3.putInt("Id", SharedConstants.getCurrentVersion().getDataVersion().getVersion());
/* 260 */     $$3.putBoolean("Snapshot", !SharedConstants.getCurrentVersion().isStable());
/* 261 */     $$3.putString("Series", SharedConstants.getCurrentVersion().getDataVersion().getSeries());
/* 262 */     $$1.put("Version", (Tag)$$3);
/*     */     
/* 264 */     NbtUtils.addCurrentDataVersion($$1);
/*     */     
/* 266 */     RegistryOps registryOps = RegistryOps.create((DynamicOps)NbtOps.INSTANCE, (HolderLookup.Provider)$$0);
/*     */ 
/*     */     
/* 269 */     Objects.requireNonNull(LOGGER); WorldGenSettings.encode((DynamicOps)registryOps, this.worldOptions, $$0).resultOrPartial(Util.prefix("WorldGenSettings: ", LOGGER::error))
/* 270 */       .ifPresent($$1 -> $$0.put("WorldGenSettings", $$1));
/*     */     
/* 272 */     $$1.putInt("GameType", this.settings.gameType().getId());
/* 273 */     $$1.putInt("SpawnX", this.xSpawn);
/* 274 */     $$1.putInt("SpawnY", this.ySpawn);
/* 275 */     $$1.putInt("SpawnZ", this.zSpawn);
/* 276 */     $$1.putFloat("SpawnAngle", this.spawnAngle);
/* 277 */     $$1.putLong("Time", this.gameTime);
/* 278 */     $$1.putLong("DayTime", this.dayTime);
/* 279 */     $$1.putLong("LastPlayed", Util.getEpochMillis());
/* 280 */     $$1.putString("LevelName", this.settings.levelName());
/* 281 */     $$1.putInt("version", 19133);
/* 282 */     $$1.putInt("clearWeatherTime", this.clearWeatherTime);
/* 283 */     $$1.putInt("rainTime", this.rainTime);
/* 284 */     $$1.putBoolean("raining", this.raining);
/* 285 */     $$1.putInt("thunderTime", this.thunderTime);
/* 286 */     $$1.putBoolean("thundering", this.thundering);
/* 287 */     $$1.putBoolean("hardcore", this.settings.hardcore());
/* 288 */     $$1.putBoolean("allowCommands", this.settings.allowCommands());
/* 289 */     $$1.putBoolean("initialized", this.initialized);
/* 290 */     this.worldBorder.write($$1);
/* 291 */     $$1.putByte("Difficulty", (byte)this.settings.difficulty().getId());
/* 292 */     $$1.putBoolean("DifficultyLocked", this.difficultyLocked);
/* 293 */     $$1.put("GameRules", (Tag)this.settings.gameRules().createTag());
/*     */     
/* 295 */     $$1.put("DragonFight", (Tag)Util.getOrThrow(EndDragonFight.Data.CODEC.encodeStart((DynamicOps)NbtOps.INSTANCE, this.endDragonFightData), IllegalStateException::new));
/*     */     
/* 297 */     if ($$2 != null) {
/* 298 */       $$1.put("Player", (Tag)$$2);
/*     */     }
/*     */     
/* 301 */     DataResult<Tag> $$5 = WorldDataConfiguration.CODEC.encodeStart((DynamicOps)NbtOps.INSTANCE, this.settings.getDataConfiguration());
/* 302 */     $$5.get()
/* 303 */       .ifLeft($$1 -> $$0.merge((CompoundTag)$$1))
/* 304 */       .ifRight($$0 -> LOGGER.warn("Failed to encode configuration {}", $$0.message()));
/*     */     
/* 306 */     if (this.customBossEvents != null) {
/* 307 */       $$1.put("CustomBossEvents", (Tag)this.customBossEvents);
/*     */     }
/*     */     
/* 310 */     $$1.put("ScheduledEvents", (Tag)this.scheduledEvents.store());
/*     */     
/* 312 */     $$1.putInt("WanderingTraderSpawnDelay", this.wanderingTraderSpawnDelay);
/* 313 */     $$1.putInt("WanderingTraderSpawnChance", this.wanderingTraderSpawnChance);
/* 314 */     if (this.wanderingTraderId != null) {
/* 315 */       $$1.putUUID("WanderingTraderId", this.wanderingTraderId);
/*     */     }
/*     */   }
/*     */   
/*     */   private static ListTag stringCollectionToTag(Set<String> $$0) {
/* 320 */     ListTag $$1 = new ListTag();
/* 321 */     Objects.requireNonNull($$1); $$0.stream().map(StringTag::valueOf).forEach($$1::add);
/* 322 */     return $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getXSpawn() {
/* 327 */     return this.xSpawn;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getYSpawn() {
/* 332 */     return this.ySpawn;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getZSpawn() {
/* 337 */     return this.zSpawn;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getSpawnAngle() {
/* 342 */     return this.spawnAngle;
/*     */   }
/*     */ 
/*     */   
/*     */   public long getGameTime() {
/* 347 */     return this.gameTime;
/*     */   }
/*     */ 
/*     */   
/*     */   public long getDayTime() {
/* 352 */     return this.dayTime;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public CompoundTag getLoadedPlayerTag() {
/* 358 */     return this.loadedPlayerTag;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setXSpawn(int $$0) {
/* 363 */     this.xSpawn = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setYSpawn(int $$0) {
/* 368 */     this.ySpawn = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setZSpawn(int $$0) {
/* 373 */     this.zSpawn = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSpawnAngle(float $$0) {
/* 378 */     this.spawnAngle = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setGameTime(long $$0) {
/* 383 */     this.gameTime = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDayTime(long $$0) {
/* 388 */     this.dayTime = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSpawn(BlockPos $$0, float $$1) {
/* 393 */     this.xSpawn = $$0.getX();
/* 394 */     this.ySpawn = $$0.getY();
/* 395 */     this.zSpawn = $$0.getZ();
/* 396 */     this.spawnAngle = $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getLevelName() {
/* 401 */     return this.settings.levelName();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getVersion() {
/* 406 */     return this.version;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getClearWeatherTime() {
/* 411 */     return this.clearWeatherTime;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setClearWeatherTime(int $$0) {
/* 416 */     this.clearWeatherTime = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isThundering() {
/* 421 */     return this.thundering;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setThundering(boolean $$0) {
/* 426 */     this.thundering = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getThunderTime() {
/* 431 */     return this.thunderTime;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setThunderTime(int $$0) {
/* 436 */     this.thunderTime = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isRaining() {
/* 441 */     return this.raining;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRaining(boolean $$0) {
/* 446 */     this.raining = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRainTime() {
/* 451 */     return this.rainTime;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRainTime(int $$0) {
/* 456 */     this.rainTime = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public GameType getGameType() {
/* 461 */     return this.settings.gameType();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setGameType(GameType $$0) {
/* 466 */     this.settings = this.settings.withGameType($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isHardcore() {
/* 471 */     return this.settings.hardcore();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getAllowCommands() {
/* 476 */     return this.settings.allowCommands();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInitialized() {
/* 481 */     return this.initialized;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setInitialized(boolean $$0) {
/* 486 */     this.initialized = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public GameRules getGameRules() {
/* 491 */     return this.settings.gameRules();
/*     */   }
/*     */ 
/*     */   
/*     */   public WorldBorder.Settings getWorldBorder() {
/* 496 */     return this.worldBorder;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setWorldBorder(WorldBorder.Settings $$0) {
/* 501 */     this.worldBorder = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public Difficulty getDifficulty() {
/* 506 */     return this.settings.difficulty();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDifficulty(Difficulty $$0) {
/* 511 */     this.settings = this.settings.withDifficulty($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDifficultyLocked() {
/* 516 */     return this.difficultyLocked;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDifficultyLocked(boolean $$0) {
/* 521 */     this.difficultyLocked = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public TimerQueue<MinecraftServer> getScheduledEvents() {
/* 526 */     return this.scheduledEvents;
/*     */   }
/*     */ 
/*     */   
/*     */   public void fillCrashReportCategory(CrashReportCategory $$0, LevelHeightAccessor $$1) {
/* 531 */     super.fillCrashReportCategory($$0, $$1);
/* 532 */     fillCrashReportCategory($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public WorldOptions worldGenOptions() {
/* 537 */     return this.worldOptions;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFlatWorld() {
/* 542 */     return (this.specialWorldProperty == SpecialWorldProperty.FLAT);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDebugWorld() {
/* 547 */     return (this.specialWorldProperty == SpecialWorldProperty.DEBUG);
/*     */   }
/*     */ 
/*     */   
/*     */   public Lifecycle worldGenSettingsLifecycle() {
/* 552 */     return this.worldGenSettingsLifecycle;
/*     */   }
/*     */ 
/*     */   
/*     */   public EndDragonFight.Data endDragonFightData() {
/* 557 */     return this.endDragonFightData;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setEndDragonFightData(EndDragonFight.Data $$0) {
/* 562 */     this.endDragonFightData = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public WorldDataConfiguration getDataConfiguration() {
/* 567 */     return this.settings.getDataConfiguration();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDataConfiguration(WorldDataConfiguration $$0) {
/* 572 */     this.settings = this.settings.withDataConfiguration($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public CompoundTag getCustomBossEvents() {
/* 578 */     return this.customBossEvents;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCustomBossEvents(@Nullable CompoundTag $$0) {
/* 583 */     this.customBossEvents = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWanderingTraderSpawnDelay() {
/* 588 */     return this.wanderingTraderSpawnDelay;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setWanderingTraderSpawnDelay(int $$0) {
/* 593 */     this.wanderingTraderSpawnDelay = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWanderingTraderSpawnChance() {
/* 598 */     return this.wanderingTraderSpawnChance;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setWanderingTraderSpawnChance(int $$0) {
/* 603 */     this.wanderingTraderSpawnChance = $$0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public UUID getWanderingTraderId() {
/* 610 */     return this.wanderingTraderId;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setWanderingTraderId(UUID $$0) {
/* 615 */     this.wanderingTraderId = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setModdedInfo(String $$0, boolean $$1) {
/* 620 */     this.knownServerBrands.add($$0);
/* 621 */     this.wasModded |= $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean wasModded() {
/* 626 */     return this.wasModded;
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<String> getKnownServerBrands() {
/* 631 */     return (Set<String>)ImmutableSet.copyOf(this.knownServerBrands);
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<String> getRemovedFeatureFlags() {
/* 636 */     return Set.copyOf(this.removedFeatureFlags);
/*     */   }
/*     */ 
/*     */   
/*     */   public ServerLevelData overworldData() {
/* 641 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public LevelSettings getLevelSettings() {
/* 646 */     return this.settings.copy();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\PrimaryLevelData.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */