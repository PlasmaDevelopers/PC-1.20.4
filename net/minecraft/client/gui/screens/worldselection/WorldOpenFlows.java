/*     */ package net.minecraft.client.gui.screens.worldselection;
/*     */ 
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.serialization.Dynamic;
/*     */ import com.mojang.serialization.Lifecycle;
/*     */ import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.util.Objects;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.CompletionStage;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.CrashReport;
/*     */ import net.minecraft.CrashReportCategory;
/*     */ import net.minecraft.ReportedException;
/*     */ import net.minecraft.SharedConstants;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.components.toasts.SystemToast;
/*     */ import net.minecraft.client.gui.screens.AlertScreen;
/*     */ import net.minecraft.client.gui.screens.BackupConfirmScreen;
/*     */ import net.minecraft.client.gui.screens.ConfirmScreen;
/*     */ import net.minecraft.client.gui.screens.DatapackLoadFailureScreen;
/*     */ import net.minecraft.client.gui.screens.GenericDirtMessageScreen;
/*     */ import net.minecraft.client.gui.screens.NoticeWithLinkScreen;
/*     */ import net.minecraft.client.gui.screens.RecoverWorldDataScreen;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.client.resources.server.DownloadedPackSource;
/*     */ import net.minecraft.commands.Commands;
/*     */ import net.minecraft.core.LayeredRegistryAccess;
/*     */ import net.minecraft.core.MappedRegistry;
/*     */ import net.minecraft.core.Registry;
/*     */ import net.minecraft.core.RegistryAccess;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.server.RegistryLayer;
/*     */ import net.minecraft.server.ReloadableServerResources;
/*     */ import net.minecraft.server.WorldLoader;
/*     */ import net.minecraft.server.WorldStem;
/*     */ import net.minecraft.server.packs.repository.PackRepository;
/*     */ import net.minecraft.server.packs.repository.ServerPacksSource;
/*     */ import net.minecraft.server.packs.resources.CloseableResourceManager;
/*     */ import net.minecraft.util.MemoryReserve;
/*     */ import net.minecraft.world.level.LevelSettings;
/*     */ import net.minecraft.world.level.WorldDataConfiguration;
/*     */ import net.minecraft.world.level.dimension.LevelStem;
/*     */ import net.minecraft.world.level.levelgen.WorldDimensions;
/*     */ import net.minecraft.world.level.levelgen.WorldOptions;
/*     */ import net.minecraft.world.level.storage.LevelDataAndDimensions;
/*     */ import net.minecraft.world.level.storage.LevelResource;
/*     */ import net.minecraft.world.level.storage.LevelStorageSource;
/*     */ import net.minecraft.world.level.storage.LevelSummary;
/*     */ import net.minecraft.world.level.storage.PrimaryLevelData;
/*     */ import net.minecraft.world.level.storage.WorldData;
/*     */ import net.minecraft.world.level.validation.ContentValidationException;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class WorldOpenFlows
/*     */ {
/*  67 */   private static final Logger LOGGER = LogUtils.getLogger();
/*  68 */   private static final UUID WORLD_PACK_ID = UUID.fromString("640a6a92-b6cb-48a0-b391-831586500359");
/*     */   
/*     */   private final Minecraft minecraft;
/*     */   private final LevelStorageSource levelSource;
/*     */   
/*     */   public WorldOpenFlows(Minecraft $$0, LevelStorageSource $$1) {
/*  74 */     this.minecraft = $$0;
/*  75 */     this.levelSource = $$1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void createFreshLevel(String $$0, LevelSettings $$1, WorldOptions $$2, Function<RegistryAccess, WorldDimensions> $$3, Screen $$4) {
/*  82 */     this.minecraft.forceSetScreen((Screen)new GenericDirtMessageScreen((Component)Component.translatable("selectWorld.data_read")));
/*  83 */     LevelStorageSource.LevelStorageAccess $$5 = createWorldAccess($$0);
/*  84 */     if ($$5 == null) {
/*     */       return;
/*     */     }
/*     */     
/*  88 */     PackRepository $$6 = ServerPacksSource.createPackRepository($$5);
/*     */     
/*  90 */     WorldDataConfiguration $$7 = $$1.getDataConfiguration();
/*     */     try {
/*  92 */       WorldLoader.PackConfig $$8 = new WorldLoader.PackConfig($$6, $$7, false, false);
/*  93 */       WorldStem $$9 = loadWorldDataBlocking($$8, $$3 -> { WorldDimensions.Complete $$4 = ((WorldDimensions)$$0.apply($$3.datapackWorldgen())).bake($$3.datapackDimensions().registryOrThrow(Registries.LEVEL_STEM)); return new WorldLoader.DataLoadOutput(new PrimaryLevelData($$1, $$2, $$4.specialWorldProperty(), $$4.lifecycle()), $$4.dimensionsRegistryAccess()); }WorldStem::new);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 103 */       this.minecraft.doWorldLoad($$5, $$6, $$9, true);
/* 104 */     } catch (Exception $$10) {
/* 105 */       LOGGER.warn("Failed to load datapacks, can't proceed with server load", $$10);
/* 106 */       $$5.safeClose();
/* 107 */       this.minecraft.setScreen($$4);
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private LevelStorageSource.LevelStorageAccess createWorldAccess(String $$0) {
/*     */     try {
/* 114 */       return this.levelSource.validateAndCreateAccess($$0);
/* 115 */     } catch (IOException $$1) {
/* 116 */       LOGGER.warn("Failed to read level {} data", $$0, $$1);
/* 117 */       SystemToast.onWorldAccessFailure(this.minecraft, $$0);
/* 118 */       this.minecraft.setScreen(null);
/* 119 */       return null;
/* 120 */     } catch (ContentValidationException $$2) {
/* 121 */       LOGGER.warn("{}", $$2.getMessage());
/* 122 */       this.minecraft.setScreen(NoticeWithLinkScreen.createWorldSymlinkWarningScreen(() -> this.minecraft.setScreen(null)));
/* 123 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void createLevelFromExistingSettings(LevelStorageSource.LevelStorageAccess $$0, ReloadableServerResources $$1, LayeredRegistryAccess<RegistryLayer> $$2, WorldData $$3) {
/* 129 */     PackRepository $$4 = ServerPacksSource.createPackRepository($$0);
/* 130 */     CloseableResourceManager $$5 = (CloseableResourceManager)(new WorldLoader.PackConfig($$4, $$3.getDataConfiguration(), false, false)).createResourceManager().getSecond();
/* 131 */     this.minecraft.doWorldLoad($$0, $$4, new WorldStem($$5, $$1, $$2, $$3), true);
/*     */   }
/*     */   
/*     */   public WorldStem loadWorldStem(Dynamic<?> $$0, boolean $$1, PackRepository $$2) throws Exception {
/* 135 */     WorldLoader.PackConfig $$3 = LevelStorageSource.getPackConfig($$0, $$2, $$1);
/* 136 */     return loadWorldDataBlocking($$3, $$1 -> { Registry<LevelStem> $$2 = $$1.datapackDimensions().registryOrThrow(Registries.LEVEL_STEM); LevelDataAndDimensions $$3 = LevelStorageSource.getLevelDataAndDimensions($$0, $$1.dataConfiguration(), $$2, $$1.datapackWorldgen()); return new WorldLoader.DataLoadOutput($$3.worldData(), $$3.dimensions().dimensionsRegistryAccess()); }WorldStem::new);
/*     */   }
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
/*     */   public Pair<LevelSettings, WorldCreationContext> recreateWorldData(LevelStorageSource.LevelStorageAccess $$0) throws Exception {
/* 151 */     PackRepository $$1 = ServerPacksSource.createPackRepository($$0);
/*     */     
/* 153 */     Dynamic<?> $$2 = $$0.getDataTag();
/* 154 */     WorldLoader.PackConfig $$3 = LevelStorageSource.getPackConfig($$2, $$1, false);
/* 155 */     return loadWorldDataBlocking($$3, $$1 -> {
/*     */           Registry<LevelStem> $$2 = (new MappedRegistry(Registries.LEVEL_STEM, Lifecycle.stable())).freeze();
/*     */           LevelDataAndDimensions $$3 = LevelStorageSource.getLevelDataAndDimensions($$0, $$1.dataConfiguration(), $$2, $$1.datapackWorldgen());
/*     */           static final class Data extends Record {
/*     */             final LevelSettings levelSettings;
/*     */             final WorldOptions options;
/*     */             final Registry<LevelStem> existingDimensions;
/*     */             Data(LevelSettings $$0, WorldOptions $$1, Registry<LevelStem> $$2) { this.levelSettings = $$0;
/*     */               this.options = $$1;
/*     */               this.existingDimensions = $$2; } public final String toString() { // Byte code:
/*     */               //   0: aload_0
/*     */               //   1: <illegal opcode> toString : (Lnet/minecraft/client/gui/screens/worldselection/WorldOpenFlows$1Data;)Ljava/lang/String;
/*     */               //   6: areturn
/*     */               // Line number table:
/*     */               //   Java source line number -> byte code offset
/*     */               //   #150	-> 0
/*     */               // Local variable table:
/*     */               //   start	length	slot	name	descriptor
/*     */               //   0	7	0	this	Lnet/minecraft/client/gui/screens/worldselection/WorldOpenFlows$1Data; } public final int hashCode() { // Byte code:
/*     */               //   0: aload_0
/*     */               //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/gui/screens/worldselection/WorldOpenFlows$1Data;)I
/*     */               //   6: ireturn
/*     */               // Line number table:
/*     */               //   Java source line number -> byte code offset
/*     */               //   #150	-> 0
/*     */               // Local variable table:
/*     */               //   start	length	slot	name	descriptor
/*     */               //   0	7	0	this	Lnet/minecraft/client/gui/screens/worldselection/WorldOpenFlows$1Data; } public final boolean equals(Object $$0) {
/*     */               // Byte code:
/*     */               //   0: aload_0
/*     */               //   1: aload_1
/*     */               //   2: <illegal opcode> equals : (Lnet/minecraft/client/gui/screens/worldselection/WorldOpenFlows$1Data;Ljava/lang/Object;)Z
/*     */               //   7: ireturn
/*     */               // Line number table:
/*     */               //   Java source line number -> byte code offset
/*     */               //   #150	-> 0
/*     */               // Local variable table:
/*     */               //   start	length	slot	name	descriptor
/*     */               //   0	8	0	this	Lnet/minecraft/client/gui/screens/worldselection/WorldOpenFlows$1Data;
/*     */               //   0	8	1	$$0	Ljava/lang/Object;
/*     */             } public LevelSettings levelSettings() {
/*     */               return this.levelSettings;
/*     */             } public WorldOptions options() {
/*     */               return this.options;
/*     */             } public Registry<LevelStem> existingDimensions() {
/*     */               return this.existingDimensions;
/*     */             } };
/*     */           return new WorldLoader.DataLoadOutput(new Data($$3.worldData().getLevelSettings(), $$3.worldData().worldGenOptions(), $$3.dimensions().dimensions()), $$1.datapackDimensions());
/*     */         }($$0, $$1, $$2, $$3) -> {
/*     */           $$0.close();
/*     */           return Pair.of($$3.levelSettings, new WorldCreationContext($$3.options, new WorldDimensions($$3.existingDimensions), $$2, $$1, $$3.levelSettings.getDataConfiguration()));
/*     */         });
/*     */   } private <D, R> R loadWorldDataBlocking(WorldLoader.PackConfig $$0, WorldLoader.WorldDataSupplier<D> $$1, WorldLoader.ResultFactory<D, R> $$2) throws Exception {
/* 179 */     WorldLoader.InitConfig $$3 = new WorldLoader.InitConfig($$0, Commands.CommandSelection.INTEGRATED, 2);
/* 180 */     CompletableFuture<R> $$4 = WorldLoader.load($$3, $$1, $$2, Util.backgroundExecutor(), (Executor)this.minecraft);
/* 181 */     Objects.requireNonNull($$4); this.minecraft.managedBlock($$4::isDone);
/* 182 */     return $$4.get();
/*     */   }
/*     */ 
/*     */   
/*     */   private void askForBackup(LevelStorageSource.LevelStorageAccess $$0, boolean $$1, Runnable $$2, Runnable $$3) {
/*     */     MutableComponent mutableComponent1, mutableComponent2;
/* 188 */     if ($$1) {
/* 189 */       mutableComponent1 = Component.translatable("selectWorld.backupQuestion.customized");
/* 190 */       mutableComponent2 = Component.translatable("selectWorld.backupWarning.customized");
/*     */     } else {
/* 192 */       mutableComponent1 = Component.translatable("selectWorld.backupQuestion.experimental");
/* 193 */       mutableComponent2 = Component.translatable("selectWorld.backupWarning.experimental");
/*     */     } 
/*     */     
/* 196 */     this.minecraft.setScreen((Screen)new BackupConfirmScreen($$3, ($$2, $$3) -> { if ($$2) EditWorldScreen.makeBackupAndShowToast($$0);  $$1.run(); }(Component)mutableComponent1, (Component)mutableComponent2, false));
/*     */   }
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
/*     */   public static void confirmWorldCreation(Minecraft $$0, CreateWorldScreen $$1, Lifecycle $$2, Runnable $$3, boolean $$4) {
/* 211 */     BooleanConsumer $$5 = $$3 -> {
/*     */         if ($$3) {
/*     */           $$0.run();
/*     */         } else {
/*     */           $$1.setScreen($$2);
/*     */         } 
/*     */       };
/* 218 */     if ($$4 || $$2 == Lifecycle.stable()) {
/* 219 */       $$3.run();
/* 220 */     } else if ($$2 == Lifecycle.experimental()) {
/* 221 */       $$0.setScreen((Screen)new ConfirmScreen($$5, 
/*     */             
/* 223 */             (Component)Component.translatable("selectWorld.warning.experimental.title"), 
/* 224 */             (Component)Component.translatable("selectWorld.warning.experimental.question")));
/*     */     }
/*     */     else {
/*     */       
/* 228 */       $$0.setScreen((Screen)new ConfirmScreen($$5, 
/*     */             
/* 230 */             (Component)Component.translatable("selectWorld.warning.deprecated.title"), 
/* 231 */             (Component)Component.translatable("selectWorld.warning.deprecated.question")));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void checkForBackupAndLoad(String $$0, Runnable $$1) {
/* 237 */     this.minecraft.forceSetScreen((Screen)new GenericDirtMessageScreen((Component)Component.translatable("selectWorld.data_read")));
/* 238 */     LevelStorageSource.LevelStorageAccess $$2 = createWorldAccess($$0);
/* 239 */     if ($$2 == null) {
/*     */       return;
/*     */     }
/* 242 */     checkForBackupAndLoad($$2, $$1);
/*     */   } private void checkForBackupAndLoad(LevelStorageSource.LevelStorageAccess $$0, Runnable $$1) {
/*     */     Dynamic<?> $$2;
/*     */     LevelSummary $$3;
/* 246 */     this.minecraft.forceSetScreen((Screen)new GenericDirtMessageScreen((Component)Component.translatable("selectWorld.data_read")));
/*     */ 
/*     */     
/*     */     try {
/* 250 */       $$2 = $$0.getDataTag();
/* 251 */       $$3 = $$0.getSummary($$2);
/* 252 */     } catch (IOException|net.minecraft.nbt.NbtException|net.minecraft.nbt.ReportedNbtException $$4) {
/* 253 */       this.minecraft.setScreen((Screen)new RecoverWorldDataScreen(this.minecraft, $$2 -> { if ($$2) { checkForBackupAndLoad($$0, $$1); } else { $$0.safeClose(); $$1.run(); }  }$$0));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       return;
/* 262 */     } catch (OutOfMemoryError $$5) {
/* 263 */       MemoryReserve.release();
/* 264 */       System.gc();
/*     */       
/* 266 */       String $$6 = "Ran out of memory trying to read level data of world folder \"" + $$0.getLevelId() + "\"";
/* 267 */       LOGGER.error(LogUtils.FATAL_MARKER, $$6);
/*     */       
/* 269 */       OutOfMemoryError $$7 = new OutOfMemoryError("Ran out of memory reading level data");
/* 270 */       $$7.initCause($$5);
/* 271 */       CrashReport $$8 = CrashReport.forThrowable($$7, $$6);
/* 272 */       CrashReportCategory $$9 = $$8.addCategory("World details");
/* 273 */       $$9.setDetail("World folder", $$0.getLevelId());
/*     */       
/* 275 */       throw new ReportedException($$8);
/*     */     } 
/* 277 */     if (!$$3.isCompatible()) {
/* 278 */       $$0.safeClose();
/* 279 */       this.minecraft.setScreen((Screen)new AlertScreen($$1, (Component)Component.translatable("selectWorld.incompatible.title").withColor(-65536), (Component)Component.translatable("selectWorld.incompatible.description", new Object[] { $$3.getWorldVersionName() })));
/*     */       return;
/*     */     } 
/* 282 */     LevelSummary.BackupStatus $$12 = $$3.backupStatus();
/* 283 */     if ($$12.shouldBackup()) {
/* 284 */       String $$13 = "selectWorld.backupQuestion." + $$12.getTranslationKey();
/* 285 */       String $$14 = "selectWorld.backupWarning." + $$12.getTranslationKey();
/* 286 */       MutableComponent $$15 = Component.translatable($$13);
/* 287 */       if ($$12.isSevere()) {
/* 288 */         $$15.withColor(-2142128);
/*     */       }
/* 290 */       MutableComponent mutableComponent1 = Component.translatable($$14, new Object[] { $$3.getWorldVersionName(), SharedConstants.getCurrentVersion().getName() });
/*     */       
/* 292 */       this.minecraft.setScreen((Screen)new BackupConfirmScreen(() -> { $$0.safeClose(); $$1.run(); }($$3, $$4) -> { if ($$3) EditWorldScreen.makeBackupAndShowToast($$0);  loadLevel($$0, $$1, false, true, $$2); }(Component)$$15, (Component)mutableComponent1, false));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 308 */       loadLevel($$0, $$2, false, true, $$1);
/*     */     } 
/*     */   }
/*     */   
/*     */   public CompletableFuture<Void> loadBundledResourcePack(DownloadedPackSource $$0, LevelStorageSource.LevelStorageAccess $$1) {
/* 313 */     Path $$2 = $$1.getLevelPath(LevelResource.MAP_RESOURCE_FILE);
/* 314 */     if (Files.exists($$2, new java.nio.file.LinkOption[0]) && !Files.isDirectory($$2, new java.nio.file.LinkOption[0])) {
/* 315 */       $$0.configureForLocalWorld();
/* 316 */       CompletableFuture<Void> $$3 = $$0.waitForPackFeedback(WORLD_PACK_ID);
/* 317 */       $$0.pushLocalPack(WORLD_PACK_ID, $$2);
/* 318 */       return $$3;
/*     */     } 
/* 320 */     return CompletableFuture.completedFuture(null);
/*     */   }
/*     */   
/*     */   private void loadLevel(LevelStorageSource.LevelStorageAccess $$0, Dynamic<?> $$1, boolean $$2, boolean $$3, Runnable $$4) {
/*     */     WorldStem $$6;
/* 325 */     this.minecraft.forceSetScreen((Screen)new GenericDirtMessageScreen((Component)Component.translatable("selectWorld.resource_load")));
/* 326 */     PackRepository $$5 = ServerPacksSource.createPackRepository($$0);
/*     */ 
/*     */     
/*     */     try {
/* 330 */       $$6 = loadWorldStem($$1, $$2, $$5);
/* 331 */     } catch (Exception $$7) {
/* 332 */       LOGGER.warn("Failed to load level data or datapacks, can't proceed with server load", $$7);
/* 333 */       if (!$$2) {
/* 334 */         this.minecraft.setScreen((Screen)new DatapackLoadFailureScreen(() -> {
/*     */                 $$0.safeClose();
/*     */                 $$1.run();
/*     */               }() -> loadLevel($$0, $$1, true, $$2, $$3)));
/*     */       } else {
/* 339 */         $$0.safeClose();
/* 340 */         this.minecraft.setScreen((Screen)new AlertScreen($$4, (Component)Component.translatable("datapackFailure.safeMode.failed.title"), (Component)Component.translatable("datapackFailure.safeMode.failed.description"), CommonComponents.GUI_BACK, true));
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/* 345 */     WorldData $$9 = $$6.worldData();
/*     */     
/* 347 */     boolean $$10 = $$9.worldGenOptions().isOldCustomizedWorld();
/* 348 */     boolean $$11 = ($$9.worldGenSettingsLifecycle() != Lifecycle.stable());
/*     */     
/* 350 */     if ($$3 && ($$10 || $$11)) {
/* 351 */       askForBackup($$0, $$10, () -> loadLevel($$0, $$1, $$2, false, $$3), () -> {
/*     */             $$0.safeClose();
/*     */             $$1.run();
/*     */           });
/* 355 */       $$6.close();
/*     */       
/*     */       return;
/*     */     } 
/* 359 */     DownloadedPackSource $$12 = this.minecraft.getDownloadedPackSource();
/* 360 */     loadBundledResourcePack($$12, $$0)
/* 361 */       .thenApply($$0 -> Boolean.valueOf(true))
/* 362 */       .exceptionallyComposeAsync($$0 -> {
/*     */           LOGGER.warn("Failed to load pack: ", $$0);
/*     */           
/*     */           return promptBundledPackLoadFailure();
/* 366 */         }(Executor)this.minecraft).thenAcceptAsync($$5 -> {
/*     */           if ($$5.booleanValue()) {
/*     */             this.minecraft.doWorldLoad($$0, $$1, $$2, false);
/*     */           } else {
/*     */             $$2.close();
/*     */             
/*     */             $$0.safeClose();
/*     */             $$3.popAll();
/*     */             $$4.run();
/*     */           } 
/* 376 */         }(Executor)this.minecraft).exceptionally($$0 -> {
/*     */           this.minecraft.delayCrash(CrashReport.forThrowable($$0, "Load world"));
/*     */           return null;
/*     */         });
/*     */   }
/*     */   
/*     */   private CompletableFuture<Boolean> promptBundledPackLoadFailure() {
/* 383 */     CompletableFuture<Boolean> $$0 = new CompletableFuture<>();
/*     */     
/* 385 */     Objects.requireNonNull($$0); this.minecraft.setScreen((Screen)new ConfirmScreen($$0::complete, 
/* 386 */           (Component)Component.translatable("multiplayer.texturePrompt.failure.line1"), 
/* 387 */           (Component)Component.translatable("multiplayer.texturePrompt.failure.line2"), CommonComponents.GUI_PROCEED, CommonComponents.GUI_CANCEL));
/*     */ 
/*     */ 
/*     */     
/* 391 */     return $$0;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\worldselection\WorldOpenFlows.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */