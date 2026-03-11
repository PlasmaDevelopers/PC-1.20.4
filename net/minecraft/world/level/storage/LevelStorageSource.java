/*     */ package net.minecraft.world.level.storage;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.io.Files;
/*     */ import com.mojang.datafixers.DataFixer;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.serialization.Dynamic;
/*     */ import com.mojang.serialization.DynamicOps;
/*     */ import com.mojang.serialization.Lifecycle;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.UncheckedIOException;
/*     */ import java.nio.file.FileVisitResult;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.InvalidPathException;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.PathMatcher;
/*     */ import java.nio.file.Paths;
/*     */ import java.nio.file.SimpleFileVisitor;
/*     */ import java.nio.file.attribute.BasicFileAttributes;
/*     */ import java.nio.file.attribute.FileAttribute;
/*     */ import java.time.Instant;
/*     */ import java.time.LocalDateTime;
/*     */ import java.time.format.DateTimeFormatter;
/*     */ import java.time.format.DateTimeFormatterBuilder;
/*     */ import java.time.format.SignStyle;
/*     */ import java.time.temporal.ChronoField;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.stream.Collectors;
/*     */ import java.util.stream.Stream;
/*     */ import java.util.zip.ZipEntry;
/*     */ import java.util.zip.ZipOutputStream;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.CrashReport;
/*     */ import net.minecraft.CrashReportCategory;
/*     */ import net.minecraft.FileUtil;
/*     */ import net.minecraft.ReportedException;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.Registry;
/*     */ import net.minecraft.core.RegistryAccess;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.NbtAccounter;
/*     */ import net.minecraft.nbt.NbtFormatException;
/*     */ import net.minecraft.nbt.NbtIo;
/*     */ import net.minecraft.nbt.NbtOps;
/*     */ import net.minecraft.nbt.NbtUtils;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.nbt.visitors.FieldSelector;
/*     */ import net.minecraft.nbt.visitors.SkipFields;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.RegistryOps;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.WorldLoader;
/*     */ import net.minecraft.server.packs.repository.PackRepository;
/*     */ import net.minecraft.util.DirectoryLock;
/*     */ import net.minecraft.util.MemoryReserve;
/*     */ import net.minecraft.util.datafix.DataFixTypes;
/*     */ import net.minecraft.world.flag.FeatureFlagSet;
/*     */ import net.minecraft.world.flag.FeatureFlags;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelSettings;
/*     */ import net.minecraft.world.level.WorldDataConfiguration;
/*     */ import net.minecraft.world.level.dimension.DimensionType;
/*     */ import net.minecraft.world.level.dimension.LevelStem;
/*     */ import net.minecraft.world.level.levelgen.WorldDimensions;
/*     */ import net.minecraft.world.level.levelgen.WorldGenSettings;
/*     */ import net.minecraft.world.level.validation.ContentValidationException;
/*     */ import net.minecraft.world.level.validation.DirectoryValidator;
/*     */ import net.minecraft.world.level.validation.ForbiddenSymlinkInfo;
/*     */ import net.minecraft.world.level.validation.PathAllowList;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class LevelStorageSource {
/*  83 */   static final Logger LOGGER = LogUtils.getLogger();
/*  84 */   static final DateTimeFormatter FORMATTER = (new DateTimeFormatterBuilder())
/*  85 */     .appendValue(ChronoField.YEAR, 4, 10, SignStyle.EXCEEDS_PAD)
/*  86 */     .appendLiteral('-')
/*  87 */     .appendValue(ChronoField.MONTH_OF_YEAR, 2)
/*  88 */     .appendLiteral('-')
/*  89 */     .appendValue(ChronoField.DAY_OF_MONTH, 2)
/*  90 */     .appendLiteral('_')
/*  91 */     .appendValue(ChronoField.HOUR_OF_DAY, 2)
/*  92 */     .appendLiteral('-')
/*  93 */     .appendValue(ChronoField.MINUTE_OF_HOUR, 2)
/*  94 */     .appendLiteral('-')
/*  95 */     .appendValue(ChronoField.SECOND_OF_MINUTE, 2)
/*  96 */     .toFormatter();
/*     */   
/*     */   private static final String TAG_DATA = "Data";
/*     */   
/*     */   private static final PathMatcher NO_SYMLINKS_ALLOWED = $$0 -> false;
/*     */   
/*     */   public static final String ALLOWED_SYMLINKS_CONFIG_NAME = "allowed_symlinks.txt";
/*     */   private static final int UNCOMPRESSED_NBT_QUOTA = 104857600;
/*     */   private final Path baseDir;
/*     */   private final Path backupDir;
/*     */   final DataFixer fixerUpper;
/*     */   private final DirectoryValidator worldDirValidator;
/*     */   
/*     */   public LevelStorageSource(Path $$0, Path $$1, DirectoryValidator $$2, DataFixer $$3) {
/* 110 */     this.fixerUpper = $$3;
/*     */     try {
/* 112 */       FileUtil.createDirectoriesSafe($$0);
/* 113 */     } catch (IOException $$4) {
/* 114 */       throw new UncheckedIOException($$4);
/*     */     } 
/* 116 */     this.baseDir = $$0;
/* 117 */     this.backupDir = $$1;
/*     */     
/* 119 */     this.worldDirValidator = $$2;
/*     */   }
/*     */   
/*     */   public static DirectoryValidator parseValidator(Path $$0) {
/* 123 */     if (Files.exists($$0, new java.nio.file.LinkOption[0])) {
/* 124 */       try { BufferedReader $$1 = Files.newBufferedReader($$0); 
/* 125 */         try { DirectoryValidator directoryValidator = new DirectoryValidator((PathMatcher)PathAllowList.readPlain($$1));
/* 126 */           if ($$1 != null) $$1.close();  return directoryValidator; } catch (Throwable throwable) { if ($$1 != null) try { $$1.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (Exception $$2)
/* 127 */       { LOGGER.error("Failed to parse {}, disallowing all symbolic links", "allowed_symlinks.txt", $$2); }
/*     */     
/*     */     }
/*     */     
/* 131 */     return new DirectoryValidator(NO_SYMLINKS_ALLOWED);
/*     */   }
/*     */   
/*     */   public static LevelStorageSource createDefault(Path $$0) {
/* 135 */     DirectoryValidator $$1 = parseValidator($$0.resolve("allowed_symlinks.txt"));
/* 136 */     return new LevelStorageSource($$0, $$0.resolve("../backups"), $$1, DataFixers.getDataFixer());
/*     */   }
/*     */   
/*     */   public static WorldDataConfiguration readDataConfig(Dynamic<?> $$0) {
/* 140 */     Objects.requireNonNull(LOGGER); return WorldDataConfiguration.CODEC.parse($$0).resultOrPartial(LOGGER::error).orElse(WorldDataConfiguration.DEFAULT);
/*     */   }
/*     */   
/*     */   public static WorldLoader.PackConfig getPackConfig(Dynamic<?> $$0, PackRepository $$1, boolean $$2) {
/* 144 */     return new WorldLoader.PackConfig($$1, readDataConfig($$0), $$2, false);
/*     */   }
/*     */   
/*     */   public static LevelDataAndDimensions getLevelDataAndDimensions(Dynamic<?> $$0, WorldDataConfiguration $$1, Registry<LevelStem> $$2, RegistryAccess.Frozen $$3) {
/* 148 */     Dynamic<?> $$4 = wrapWithRegistryOps($$0, $$3);
/* 149 */     Dynamic<?> $$5 = $$4.get("WorldGenSettings").orElseEmptyMap();
/* 150 */     Objects.requireNonNull(LOGGER); WorldGenSettings $$6 = (WorldGenSettings)WorldGenSettings.CODEC.parse($$5).getOrThrow(false, Util.prefix("WorldGenSettings: ", LOGGER::error));
/* 151 */     LevelSettings $$7 = LevelSettings.parse($$4, $$1);
/* 152 */     WorldDimensions.Complete $$8 = $$6.dimensions().bake($$2);
/* 153 */     Lifecycle $$9 = $$8.lifecycle().add($$3.allRegistriesLifecycle());
/*     */     
/* 155 */     PrimaryLevelData $$10 = PrimaryLevelData.parse($$4, $$7, $$8.specialWorldProperty(), $$6.options(), $$9);
/* 156 */     return new LevelDataAndDimensions($$10, $$8);
/*     */   }
/*     */   
/*     */   private static <T> Dynamic<T> wrapWithRegistryOps(Dynamic<T> $$0, RegistryAccess.Frozen $$1) {
/* 160 */     RegistryOps<T> $$2 = RegistryOps.create($$0.getOps(), (HolderLookup.Provider)$$1);
/* 161 */     return new Dynamic((DynamicOps)$$2, $$0.getValue());
/*     */   }
/*     */   
/*     */   public String getName() {
/* 165 */     return "Anvil";
/*     */   }
/*     */   
/*     */   public LevelCandidates findLevelCandidates() throws LevelStorageException {
/* 169 */     if (!Files.isDirectory(this.baseDir, new java.nio.file.LinkOption[0])) {
/* 170 */       throw new LevelStorageException(Component.translatable("selectWorld.load_folder_access"));
/*     */     }
/*     */     
/* 173 */     try { Stream<Path> $$0 = Files.list(this.baseDir);
/*     */ 
/*     */ 
/*     */       
/* 177 */       try { List<LevelDirectory> $$1 = $$0.filter($$0 -> Files.isDirectory($$0, new java.nio.file.LinkOption[0])).map(LevelDirectory::new).filter($$0 -> (Files.isRegularFile($$0.dataFile(), new java.nio.file.LinkOption[0]) || Files.isRegularFile($$0.oldDataFile(), new java.nio.file.LinkOption[0]))).toList();
/*     */         
/* 179 */         LevelCandidates levelCandidates = new LevelCandidates($$1);
/* 180 */         if ($$0 != null) $$0.close();  return levelCandidates; } catch (Throwable throwable) { if ($$0 != null) try { $$0.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException $$2)
/* 181 */     { throw new LevelStorageException(Component.translatable("selectWorld.load_folder_access")); }
/*     */   
/*     */   }
/*     */   
/*     */   public CompletableFuture<List<LevelSummary>> loadLevelSummaries(LevelCandidates $$0) {
/* 186 */     List<CompletableFuture<LevelSummary>> $$1 = new ArrayList<>($$0.levels.size());
/* 187 */     for (Iterator<LevelDirectory> iterator = $$0.levels.iterator(); iterator.hasNext(); ) { LevelDirectory $$2 = iterator.next();
/* 188 */       $$1.add(CompletableFuture.supplyAsync(() -> {
/*     */               boolean $$1;
/*     */               try {
/*     */                 $$1 = DirectoryLock.isLocked($$0.path());
/* 192 */               } catch (Exception $$2) {
/*     */                 LOGGER.warn("Failed to read {} lock", $$0.path(), $$2);
/*     */                 
/*     */                 return null;
/*     */               } 
/*     */               try {
/*     */                 return readLevelSummary($$0, $$1);
/* 199 */               } catch (OutOfMemoryError $$4) {
/*     */                 MemoryReserve.release();
/*     */                 
/*     */                 System.gc();
/*     */                 
/*     */                 String $$5 = "Ran out of memory trying to read summary of world folder \"" + $$0.directoryName() + "\"";
/*     */                 
/*     */                 LOGGER.error(LogUtils.FATAL_MARKER, $$5);
/*     */                 OutOfMemoryError $$6 = new OutOfMemoryError("Ran out of memory reading level data");
/*     */                 $$6.initCause($$4);
/*     */                 CrashReport $$7 = CrashReport.forThrowable($$6, $$5);
/*     */                 CrashReportCategory $$8 = $$7.addCategory("World details");
/*     */                 $$8.setDetail("Folder Name", $$0.directoryName());
/*     */                 try {
/*     */                   long $$9 = Files.size($$0.dataFile());
/*     */                   $$8.setDetail("level.dat size", Long.valueOf($$9));
/* 215 */                 } catch (IOException $$10) {
/*     */                   $$8.setDetailError("level.dat size", $$10);
/*     */                 } 
/*     */                 
/*     */                 throw new ReportedException($$7);
/*     */               } 
/* 221 */             }Util.backgroundExecutor())); }
/*     */ 
/*     */     
/* 224 */     return Util.sequenceFailFastAndCancel($$1)
/* 225 */       .thenApply($$0 -> $$0.stream().filter(Objects::nonNull).sorted().toList());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getStorageVersion() {
/* 232 */     return 19133;
/*     */   }
/*     */   
/*     */   static CompoundTag readLevelDataTagRaw(Path $$0) throws IOException {
/* 236 */     return NbtIo.readCompressed($$0, NbtAccounter.create(104857600L));
/*     */   }
/*     */   
/*     */   static Dynamic<?> readLevelDataTagFixed(Path $$0, DataFixer $$1) throws IOException {
/* 240 */     CompoundTag $$2 = readLevelDataTagRaw($$0);
/* 241 */     CompoundTag $$3 = $$2.getCompound("Data");
/*     */     
/* 243 */     int $$4 = NbtUtils.getDataVersion($$3, -1);
/* 244 */     Dynamic<?> $$5 = DataFixTypes.LEVEL.updateToCurrentVersion($$1, new Dynamic((DynamicOps)NbtOps.INSTANCE, $$3), $$4);
/*     */     
/* 246 */     Dynamic<?> $$6 = $$5.get("Player").orElseEmptyMap();
/* 247 */     Dynamic<?> $$7 = DataFixTypes.PLAYER.updateToCurrentVersion($$1, $$6, $$4);
/* 248 */     $$5 = $$5.set("Player", $$7);
/*     */     
/* 250 */     Dynamic<?> $$8 = $$5.get("WorldGenSettings").orElseEmptyMap();
/* 251 */     Dynamic<?> $$9 = DataFixTypes.WORLD_GEN_SETTINGS.updateToCurrentVersion($$1, $$8, $$4);
/* 252 */     $$5 = $$5.set("WorldGenSettings", $$9);
/*     */     
/* 254 */     return $$5;
/*     */   }
/*     */   
/*     */   private LevelSummary readLevelSummary(LevelDirectory $$0, boolean $$1) {
/* 258 */     Path $$2 = $$0.dataFile();
/* 259 */     if (Files.exists($$2, new java.nio.file.LinkOption[0])) {
/*     */       try {
/* 261 */         if (Files.isSymbolicLink($$2)) {
/* 262 */           List<ForbiddenSymlinkInfo> $$3 = this.worldDirValidator.validateSymlink($$2);
/* 263 */           if (!$$3.isEmpty()) {
/* 264 */             LOGGER.warn("{}", ContentValidationException.getMessage($$2, $$3));
/* 265 */             return new LevelSummary.SymlinkLevelSummary($$0.directoryName(), $$0.iconFile());
/*     */           } 
/*     */         } 
/* 268 */         Tag $$4 = readLightweightData($$2);
/* 269 */         if ($$4 instanceof CompoundTag) { CompoundTag $$5 = (CompoundTag)$$4;
/* 270 */           CompoundTag $$6 = $$5.getCompound("Data");
/* 271 */           int $$7 = NbtUtils.getDataVersion($$6, -1);
/* 272 */           Dynamic<?> $$8 = DataFixTypes.LEVEL.updateToCurrentVersion(this.fixerUpper, new Dynamic((DynamicOps)NbtOps.INSTANCE, $$6), $$7);
/* 273 */           return makeLevelSummary($$8, $$0, $$1); }
/*     */         
/* 275 */         LOGGER.warn("Invalid root tag in {}", $$2);
/*     */       }
/* 277 */       catch (Exception $$9) {
/* 278 */         LOGGER.error("Exception reading {}", $$2, $$9);
/*     */       } 
/*     */     }
/* 281 */     return new LevelSummary.CorruptedLevelSummary($$0.directoryName(), $$0.iconFile(), getFileModificationTime($$0));
/*     */   }
/*     */   
/*     */   private static long getFileModificationTime(LevelDirectory $$0) {
/* 285 */     Instant $$1 = getFileModificationTime($$0.dataFile());
/* 286 */     if ($$1 == null) {
/* 287 */       $$1 = getFileModificationTime($$0.oldDataFile());
/*     */     }
/* 289 */     return ($$1 == null) ? -1L : $$1.toEpochMilli();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   static Instant getFileModificationTime(Path $$0) {
/*     */     try {
/* 295 */       return Files.getLastModifiedTime($$0, new java.nio.file.LinkOption[0]).toInstant();
/* 296 */     } catch (IOException iOException) {
/*     */       
/* 298 */       return null;
/*     */     } 
/*     */   }
/*     */   LevelSummary makeLevelSummary(Dynamic<?> $$0, LevelDirectory $$1, boolean $$2) {
/* 302 */     LevelVersion $$3 = LevelVersion.parse($$0);
/* 303 */     int $$4 = $$3.levelDataVersion();
/* 304 */     if ($$4 == 19132 || $$4 == 19133) {
/* 305 */       boolean $$5 = ($$4 != getStorageVersion());
/* 306 */       Path $$6 = $$1.iconFile();
/* 307 */       WorldDataConfiguration $$7 = readDataConfig($$0);
/* 308 */       LevelSettings $$8 = LevelSettings.parse($$0, $$7);
/* 309 */       FeatureFlagSet $$9 = parseFeatureFlagsFromSummary($$0);
/* 310 */       boolean $$10 = FeatureFlags.isExperimental($$9);
/* 311 */       return new LevelSummary($$8, $$3, $$1.directoryName(), $$5, $$2, $$10, $$6);
/*     */     } 
/* 313 */     throw new NbtFormatException("Unknown data version: " + Integer.toHexString($$4));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static FeatureFlagSet parseFeatureFlagsFromSummary(Dynamic<?> $$0) {
/* 321 */     Set<ResourceLocation> $$1 = (Set<ResourceLocation>)$$0.get("enabled_features").asStream().flatMap($$0 -> $$0.asString().result().map(ResourceLocation::tryParse).stream()).collect(Collectors.toSet());
/*     */     
/* 323 */     return FeatureFlags.REGISTRY.fromNames($$1, $$0 -> {
/*     */         
/*     */         });
/*     */   } @Nullable
/*     */   private static Tag readLightweightData(Path $$0) throws IOException {
/* 328 */     SkipFields $$1 = new SkipFields(new FieldSelector[] { new FieldSelector("Data", CompoundTag.TYPE, "Player"), new FieldSelector("Data", CompoundTag.TYPE, "WorldGenSettings") });
/*     */ 
/*     */ 
/*     */     
/* 332 */     NbtIo.parseCompressed($$0, (StreamTagVisitor)$$1, NbtAccounter.create(104857600L));
/* 333 */     return $$1.getResult();
/*     */   }
/*     */   
/*     */   public boolean isNewLevelIdAcceptable(String $$0) {
/*     */     try {
/* 338 */       Path $$1 = getLevelPath($$0);
/* 339 */       Files.createDirectory($$1, (FileAttribute<?>[])new FileAttribute[0]);
/* 340 */       Files.deleteIfExists($$1);
/* 341 */       return true;
/* 342 */     } catch (IOException $$2) {
/* 343 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean levelExists(String $$0) {
/*     */     try {
/* 349 */       return Files.isDirectory(getLevelPath($$0), new java.nio.file.LinkOption[0]);
/* 350 */     } catch (InvalidPathException $$1) {
/* 351 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public Path getLevelPath(String $$0) {
/* 356 */     return this.baseDir.resolve($$0);
/*     */   }
/*     */   
/*     */   public Path getBaseDir() {
/* 360 */     return this.baseDir;
/*     */   }
/*     */   
/*     */   public Path getBackupPath() {
/* 364 */     return this.backupDir;
/*     */   }
/*     */   
/*     */   public LevelStorageAccess validateAndCreateAccess(String $$0) throws IOException, ContentValidationException {
/* 368 */     Path $$1 = getLevelPath($$0);
/* 369 */     List<ForbiddenSymlinkInfo> $$2 = this.worldDirValidator.validateDirectory($$1, true);
/* 370 */     if (!$$2.isEmpty()) {
/* 371 */       throw new ContentValidationException($$1, $$2);
/*     */     }
/* 373 */     return new LevelStorageAccess($$0, $$1);
/*     */   }
/*     */   
/*     */   public LevelStorageAccess createAccess(String $$0) throws IOException {
/* 377 */     Path $$1 = getLevelPath($$0);
/* 378 */     return new LevelStorageAccess($$0, $$1);
/*     */   }
/*     */   
/*     */   public DirectoryValidator getWorldDirValidator() {
/* 382 */     return this.worldDirValidator;
/*     */   }
/*     */   
/*     */   public class LevelStorageAccess implements AutoCloseable {
/*     */     final DirectoryLock lock;
/*     */     final LevelStorageSource.LevelDirectory levelDirectory;
/*     */     private final String levelId;
/* 389 */     private final Map<LevelResource, Path> resources = Maps.newHashMap();
/*     */     
/*     */     LevelStorageAccess(String $$1, Path $$2) throws IOException {
/* 392 */       this.levelId = $$1;
/* 393 */       this.levelDirectory = new LevelStorageSource.LevelDirectory($$2);
/* 394 */       this.lock = DirectoryLock.create($$2);
/*     */     }
/*     */     
/*     */     public void safeClose() {
/*     */       try {
/* 399 */         close();
/* 400 */       } catch (IOException $$0) {
/* 401 */         LevelStorageSource.LOGGER.warn("Failed to unlock access to level {}", getLevelId(), $$0);
/*     */       } 
/*     */     }
/*     */     
/*     */     public LevelStorageSource parent() {
/* 406 */       return LevelStorageSource.this;
/*     */     }
/*     */     
/*     */     public LevelStorageSource.LevelDirectory getLevelDirectory() {
/* 410 */       return this.levelDirectory;
/*     */     }
/*     */     
/*     */     public String getLevelId() {
/* 414 */       return this.levelId;
/*     */     }
/*     */     
/*     */     public Path getLevelPath(LevelResource $$0) {
/* 418 */       Objects.requireNonNull(this.levelDirectory); return this.resources.computeIfAbsent($$0, this.levelDirectory::resourcePath);
/*     */     }
/*     */     
/*     */     public Path getDimensionPath(ResourceKey<Level> $$0) {
/* 422 */       return DimensionType.getStorageFolder($$0, this.levelDirectory.path());
/*     */     }
/*     */     
/*     */     private void checkLock() {
/* 426 */       if (!this.lock.isValid()) {
/* 427 */         throw new IllegalStateException("Lock is no longer valid");
/*     */       }
/*     */     }
/*     */     
/*     */     public PlayerDataStorage createPlayerStorage() {
/* 432 */       checkLock();
/* 433 */       return new PlayerDataStorage(this, LevelStorageSource.this.fixerUpper);
/*     */     }
/*     */     
/*     */     public LevelSummary getSummary(Dynamic<?> $$0) {
/* 437 */       checkLock();
/* 438 */       return LevelStorageSource.this.makeLevelSummary($$0, this.levelDirectory, false);
/*     */     }
/*     */     
/*     */     public Dynamic<?> getDataTag() throws IOException {
/* 442 */       return getDataTag(false);
/*     */     }
/*     */     
/*     */     public Dynamic<?> getDataTagFallback() throws IOException {
/* 446 */       return getDataTag(true);
/*     */     }
/*     */     
/*     */     private Dynamic<?> getDataTag(boolean $$0) throws IOException {
/* 450 */       checkLock();
/* 451 */       return LevelStorageSource.readLevelDataTagFixed($$0 ? this.levelDirectory.oldDataFile() : this.levelDirectory.dataFile(), LevelStorageSource.this.fixerUpper);
/*     */     }
/*     */     
/*     */     public void saveDataTag(RegistryAccess $$0, WorldData $$1) {
/* 455 */       saveDataTag($$0, $$1, null);
/*     */     }
/*     */     
/*     */     public void saveDataTag(RegistryAccess $$0, WorldData $$1, @Nullable CompoundTag $$2) {
/* 459 */       CompoundTag $$3 = $$1.createTag($$0, $$2);
/*     */       
/* 461 */       CompoundTag $$4 = new CompoundTag();
/* 462 */       $$4.put("Data", (Tag)$$3);
/*     */       
/* 464 */       saveLevelData($$4);
/*     */     }
/*     */     
/*     */     private void saveLevelData(CompoundTag $$0) {
/* 468 */       Path $$1 = this.levelDirectory.path();
/*     */       try {
/* 470 */         Path $$2 = Files.createTempFile($$1, "level", ".dat", (FileAttribute<?>[])new FileAttribute[0]);
/* 471 */         NbtIo.writeCompressed($$0, $$2);
/*     */         
/* 473 */         Path $$3 = this.levelDirectory.oldDataFile();
/* 474 */         Path $$4 = this.levelDirectory.dataFile();
/* 475 */         Util.safeReplaceFile($$4, $$2, $$3);
/* 476 */       } catch (Exception $$5) {
/* 477 */         LevelStorageSource.LOGGER.error("Failed to save level {}", $$1, $$5);
/*     */       } 
/*     */     }
/*     */     
/*     */     public Optional<Path> getIconFile() {
/* 482 */       if (!this.lock.isValid()) {
/* 483 */         return Optional.empty();
/*     */       }
/* 485 */       return Optional.of(this.levelDirectory.iconFile());
/*     */     }
/*     */     
/*     */     public void deleteLevel() throws IOException {
/* 489 */       checkLock();
/*     */       
/* 491 */       final Path lockPath = this.levelDirectory.lockFile();
/*     */       
/* 493 */       LevelStorageSource.LOGGER.info("Deleting level {}", this.levelId);
/* 494 */       for (int $$1 = 1; $$1 <= 5; $$1++) {
/* 495 */         LevelStorageSource.LOGGER.info("Attempt {}...", Integer.valueOf($$1));
/*     */         
/*     */         try {
/* 498 */           Files.walkFileTree(this.levelDirectory.path(), new SimpleFileVisitor<Path>()
/*     */               {
/*     */                 public FileVisitResult visitFile(Path $$0, BasicFileAttributes $$1) throws IOException {
/* 501 */                   if (!$$0.equals(lockPath)) {
/* 502 */                     LevelStorageSource.LOGGER.debug("Deleting {}", $$0);
/* 503 */                     Files.delete($$0);
/*     */                   } 
/* 505 */                   return FileVisitResult.CONTINUE;
/*     */                 }
/*     */ 
/*     */                 
/*     */                 public FileVisitResult postVisitDirectory(Path $$0, @Nullable IOException $$1) throws IOException {
/* 510 */                   if ($$1 != null) {
/* 511 */                     throw $$1;
/*     */                   }
/*     */                   
/* 514 */                   if ($$0.equals(LevelStorageSource.LevelStorageAccess.this.levelDirectory.path())) {
/*     */                     
/* 516 */                     LevelStorageSource.LevelStorageAccess.this.lock.close();
/* 517 */                     Files.deleteIfExists(lockPath);
/*     */                   } 
/* 519 */                   Files.delete($$0);
/* 520 */                   return FileVisitResult.CONTINUE;
/*     */                 }
/*     */               });
/*     */           break;
/* 524 */         } catch (IOException $$2) {
/* 525 */           if ($$1 < 5) {
/* 526 */             LevelStorageSource.LOGGER.warn("Failed to delete {}", this.levelDirectory.path(), $$2);
/*     */             try {
/* 528 */               Thread.sleep(500L);
/* 529 */             } catch (InterruptedException interruptedException) {}
/*     */           } else {
/*     */             
/* 532 */             throw $$2;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     public void renameLevel(String $$0) throws IOException {
/* 539 */       modifyLevelDataWithoutDatafix($$1 -> $$1.putString("LevelName", $$0.trim()));
/*     */     }
/*     */     
/*     */     public void renameAndDropPlayer(String $$0) throws IOException {
/* 543 */       modifyLevelDataWithoutDatafix($$1 -> {
/*     */             $$1.putString("LevelName", $$0.trim());
/*     */             $$1.remove("Player");
/*     */           });
/*     */     }
/*     */     
/*     */     private void modifyLevelDataWithoutDatafix(Consumer<CompoundTag> $$0) throws IOException {
/* 550 */       checkLock();
/*     */       
/* 552 */       CompoundTag $$1 = LevelStorageSource.readLevelDataTagRaw(this.levelDirectory.dataFile());
/* 553 */       $$0.accept($$1.getCompound("Data"));
/* 554 */       saveLevelData($$1);
/*     */     }
/*     */     
/*     */     public long makeWorldBackup() throws IOException {
/* 558 */       checkLock();
/* 559 */       String $$0 = LocalDateTime.now().format(LevelStorageSource.FORMATTER) + "_" + LocalDateTime.now().format(LevelStorageSource.FORMATTER);
/*     */       
/* 561 */       Path $$1 = LevelStorageSource.this.getBackupPath();
/*     */       try {
/* 563 */         FileUtil.createDirectoriesSafe($$1);
/* 564 */       } catch (IOException $$2) {
/* 565 */         throw new RuntimeException($$2);
/*     */       } 
/* 567 */       Path $$3 = $$1.resolve(FileUtil.findAvailableName($$1, $$0, ".zip"));
/*     */       
/* 569 */       final ZipOutputStream stream = new ZipOutputStream(new BufferedOutputStream(Files.newOutputStream($$3, new java.nio.file.OpenOption[0]))); 
/* 570 */       try { final Path rootPath = Paths.get(this.levelId, new String[0]);
/*     */         
/* 572 */         Files.walkFileTree(this.levelDirectory.path(), new SimpleFileVisitor<Path>()
/*     */             {
/*     */               public FileVisitResult visitFile(Path $$0, BasicFileAttributes $$1) throws IOException {
/* 575 */                 if ($$0.endsWith("session.lock")) {
/* 576 */                   return FileVisitResult.CONTINUE;
/*     */                 }
/* 578 */                 String $$2 = rootPath.resolve(LevelStorageSource.LevelStorageAccess.this.levelDirectory.path().relativize($$0)).toString().replace('\\', '/');
/* 579 */                 ZipEntry $$3 = new ZipEntry($$2);
/* 580 */                 stream.putNextEntry($$3);
/* 581 */                 Files.asByteSource($$0.toFile()).copyTo(stream);
/* 582 */                 stream.closeEntry();
/* 583 */                 return FileVisitResult.CONTINUE;
/*     */               }
/*     */             });
/* 586 */         $$4.close(); } catch (Throwable throwable) { try { $$4.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */          throw throwable; }
/* 588 */        return Files.size($$3);
/*     */     }
/*     */     
/*     */     public boolean hasWorldData() {
/* 592 */       return (Files.exists(this.levelDirectory.dataFile(), new java.nio.file.LinkOption[0]) || Files.exists(this.levelDirectory.oldDataFile(), new java.nio.file.LinkOption[0]));
/*     */     }
/*     */ 
/*     */     
/*     */     public void close() throws IOException {
/* 597 */       this.lock.close();
/*     */     }
/*     */     
/*     */     public boolean restoreLevelDataFromOld() {
/* 601 */       return Util.safeReplaceOrMoveFile(this.levelDirectory.dataFile(), this.levelDirectory.oldDataFile(), this.levelDirectory.corruptedDataFile(LocalDateTime.now()), true);
/*     */     }
/*     */     
/*     */     @Nullable
/*     */     public Instant getFileModificationTime(boolean $$0) {
/* 606 */       return LevelStorageSource.getFileModificationTime($$0 ? this.levelDirectory.oldDataFile() : this.levelDirectory.dataFile());
/*     */     }
/*     */   } class null extends SimpleFileVisitor<Path> { public FileVisitResult visitFile(Path $$0, BasicFileAttributes $$1) throws IOException { if (!$$0.equals(lockPath)) { LevelStorageSource.LOGGER.debug("Deleting {}", $$0); Files.delete($$0); }  return FileVisitResult.CONTINUE; } public FileVisitResult postVisitDirectory(Path $$0, @Nullable IOException $$1) throws IOException { if ($$1 != null) throw $$1;  if ($$0.equals(LevelStorageSource.LevelStorageAccess.this.levelDirectory.path())) { LevelStorageSource.LevelStorageAccess.this.lock.close(); Files.deleteIfExists(lockPath); }  Files.delete($$0); return FileVisitResult.CONTINUE; } } class null extends SimpleFileVisitor<Path> {
/*     */     public FileVisitResult visitFile(Path $$0, BasicFileAttributes $$1) throws IOException { if ($$0.endsWith("session.lock")) return FileVisitResult.CONTINUE;  String $$2 = rootPath.resolve(LevelStorageSource.LevelStorageAccess.this.levelDirectory.path().relativize($$0)).toString().replace('\\', '/'); ZipEntry $$3 = new ZipEntry($$2); stream.putNextEntry($$3); Files.asByteSource($$0.toFile()).copyTo(stream); stream.closeEntry(); return FileVisitResult.CONTINUE; }
/* 610 */   } public static final class LevelCandidates extends Record implements Iterable<LevelDirectory> { public LevelCandidates(List<LevelStorageSource.LevelDirectory> $$0) { this.levels = $$0; } final List<LevelStorageSource.LevelDirectory> levels; public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/storage/LevelStorageSource$LevelCandidates;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #610	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/storage/LevelStorageSource$LevelCandidates; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/storage/LevelStorageSource$LevelCandidates;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #610	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/storage/LevelStorageSource$LevelCandidates; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/storage/LevelStorageSource$LevelCandidates;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #610	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/world/level/storage/LevelStorageSource$LevelCandidates;
/* 610 */       //   0	8	1	$$0	Ljava/lang/Object; } public List<LevelStorageSource.LevelDirectory> levels() { return this.levels; }
/*     */      public boolean isEmpty() {
/* 612 */       return this.levels.isEmpty();
/*     */     }
/*     */ 
/*     */     
/*     */     public Iterator<LevelStorageSource.LevelDirectory> iterator() {
/* 617 */       return this.levels.iterator();
/*     */     } }
/*     */   public static final class LevelDirectory extends Record { private final Path path;
/*     */     
/* 621 */     public LevelDirectory(Path $$0) { this.path = $$0; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/storage/LevelStorageSource$LevelDirectory;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #621	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/storage/LevelStorageSource$LevelDirectory; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/storage/LevelStorageSource$LevelDirectory;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #621	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/storage/LevelStorageSource$LevelDirectory; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/storage/LevelStorageSource$LevelDirectory;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #621	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/world/level/storage/LevelStorageSource$LevelDirectory;
/* 621 */       //   0	8	1	$$0	Ljava/lang/Object; } public Path path() { return this.path; }
/*     */      public String directoryName() {
/* 623 */       return this.path.getFileName().toString();
/*     */     }
/*     */     
/*     */     public Path dataFile() {
/* 627 */       return resourcePath(LevelResource.LEVEL_DATA_FILE);
/*     */     }
/*     */     
/*     */     public Path oldDataFile() {
/* 631 */       return resourcePath(LevelResource.OLD_LEVEL_DATA_FILE);
/*     */     }
/*     */     
/*     */     public Path corruptedDataFile(LocalDateTime $$0) {
/* 635 */       return this.path.resolve(LevelResource.LEVEL_DATA_FILE.getId() + "_corrupted_" + LevelResource.LEVEL_DATA_FILE.getId());
/*     */     }
/*     */     
/*     */     public Path rawDataFile(LocalDateTime $$0) {
/* 639 */       return this.path.resolve(LevelResource.LEVEL_DATA_FILE.getId() + "_raw_" + LevelResource.LEVEL_DATA_FILE.getId());
/*     */     }
/*     */     
/*     */     public Path iconFile() {
/* 643 */       return resourcePath(LevelResource.ICON_FILE);
/*     */     }
/*     */     
/*     */     public Path lockFile() {
/* 647 */       return resourcePath(LevelResource.LOCK_FILE);
/*     */     }
/*     */     
/*     */     public Path resourcePath(LevelResource $$0) {
/* 651 */       return this.path.resolve($$0.getId());
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\LevelStorageSource.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */