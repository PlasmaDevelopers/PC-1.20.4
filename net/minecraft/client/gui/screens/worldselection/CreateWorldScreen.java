/*     */ package net.minecraft.client.gui.screens.worldselection;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import com.mojang.serialization.DataResult;
/*     */ import com.mojang.serialization.DynamicOps;
/*     */ import com.mojang.serialization.JsonOps;
/*     */ import com.mojang.serialization.Lifecycle;
/*     */ import java.io.IOException;
/*     */ import java.io.UncheckedIOException;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.attribute.FileAttribute;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.OptionalLong;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.stream.Stream;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.FileUtil;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.Font;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.AbstractWidget;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.gui.components.CycleButton;
/*     */ import net.minecraft.client.gui.components.EditBox;
/*     */ import net.minecraft.client.gui.components.Tooltip;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.client.gui.components.tabs.GridLayoutTab;
/*     */ import net.minecraft.client.gui.components.tabs.Tab;
/*     */ import net.minecraft.client.gui.components.tabs.TabManager;
/*     */ import net.minecraft.client.gui.components.tabs.TabNavigationBar;
/*     */ import net.minecraft.client.gui.components.toasts.SystemToast;
/*     */ import net.minecraft.client.gui.layouts.CommonLayouts;
/*     */ import net.minecraft.client.gui.layouts.GridLayout;
/*     */ import net.minecraft.client.gui.layouts.LayoutElement;
/*     */ import net.minecraft.client.gui.layouts.LayoutSettings;
/*     */ import net.minecraft.client.gui.navigation.ScreenRectangle;
/*     */ import net.minecraft.client.gui.screens.ConfirmScreen;
/*     */ import net.minecraft.client.gui.screens.GenericDirtMessageScreen;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.client.gui.screens.packs.PackSelectionScreen;
/*     */ import net.minecraft.core.HolderLookup;
/*     */ import net.minecraft.core.LayeredRegistryAccess;
/*     */ import net.minecraft.core.RegistryAccess;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.resources.RegistryOps;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.RegistryLayer;
/*     */ import net.minecraft.server.ReloadableServerResources;
/*     */ import net.minecraft.server.WorldLoader;
/*     */ import net.minecraft.server.packs.repository.PackRepository;
/*     */ import net.minecraft.server.packs.repository.RepositorySource;
/*     */ import net.minecraft.server.packs.repository.ServerPacksSource;
/*     */ import net.minecraft.server.packs.resources.CloseableResourceManager;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.Difficulty;
/*     */ import net.minecraft.world.flag.FeatureFlagSet;
/*     */ import net.minecraft.world.flag.FeatureFlags;
/*     */ import net.minecraft.world.level.DataPackConfig;
/*     */ import net.minecraft.world.level.GameRules;
/*     */ import net.minecraft.world.level.LevelSettings;
/*     */ import net.minecraft.world.level.WorldDataConfiguration;
/*     */ import net.minecraft.world.level.levelgen.WorldDimensions;
/*     */ import net.minecraft.world.level.levelgen.WorldGenSettings;
/*     */ import net.minecraft.world.level.levelgen.WorldOptions;
/*     */ import net.minecraft.world.level.levelgen.presets.WorldPreset;
/*     */ import net.minecraft.world.level.levelgen.presets.WorldPresets;
/*     */ import net.minecraft.world.level.storage.LevelResource;
/*     */ import net.minecraft.world.level.storage.LevelStorageSource;
/*     */ import net.minecraft.world.level.storage.PrimaryLevelData;
/*     */ import net.minecraft.world.level.validation.DirectoryValidator;
/*     */ import org.apache.commons.lang3.mutable.MutableObject;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class CreateWorldScreen extends Screen {
/*     */   private static final int GROUP_BOTTOM = 1;
/*     */   private static final int TAB_COLUMN_WIDTH = 210;
/*     */   private static final int FOOTER_HEIGHT = 36;
/*  90 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   private static final String TEMP_WORLD_PREFIX = "mcworld-";
/*  92 */   static final Component GAME_MODEL_LABEL = (Component)Component.translatable("selectWorld.gameMode");
/*  93 */   static final Component NAME_LABEL = (Component)Component.translatable("selectWorld.enterName");
/*  94 */   static final Component EXPERIMENTS_LABEL = (Component)Component.translatable("selectWorld.experiments");
/*  95 */   static final Component ALLOW_CHEATS_INFO = (Component)Component.translatable("selectWorld.allowCommands.info");
/*  96 */   private static final Component PREPARING_WORLD_DATA = (Component)Component.translatable("createWorld.preparing");
/*     */   
/*     */   private static final int HORIZONTAL_BUTTON_SPACING = 10;
/*     */   private static final int VERTICAL_BUTTON_SPACING = 8;
/* 100 */   public static final ResourceLocation HEADER_SEPERATOR = new ResourceLocation("textures/gui/header_separator.png");
/* 101 */   public static final ResourceLocation FOOTER_SEPERATOR = new ResourceLocation("textures/gui/footer_separator.png");
/* 102 */   public static final ResourceLocation LIGHT_DIRT_BACKGROUND = new ResourceLocation("textures/gui/light_dirt_background.png");
/*     */   final WorldCreationUiState uiState;
/*     */   private final TabManager tabManager;
/*     */   private boolean recreated;
/*     */   private final DirectoryValidator packValidator;
/*     */   @Nullable
/*     */   private final Screen lastScreen;
/*     */   @Nullable
/*     */   private Path tempDataPackDir;
/*     */   @Nullable
/*     */   private PackRepository tempDataPackRepository;
/*     */   @Nullable
/*     */   private GridLayout bottomButtons;
/*     */   @Nullable
/*     */   private TabNavigationBar tabNavigationBar;
/*     */   
/*     */   private class GameTab
/*     */     extends GridLayoutTab
/*     */   {
/* 121 */     private static final Component TITLE = (Component)Component.translatable("createWorld.tab.game.title");
/* 122 */     private static final Component ALLOW_CHEATS = (Component)Component.translatable("selectWorld.allowCommands");
/*     */     private final EditBox nameEdit;
/*     */     
/*     */     GameTab() {
/* 126 */       super(TITLE);
/*     */ 
/*     */ 
/*     */       
/* 130 */       GridLayout.RowHelper $$0 = this.layout.rowSpacing(8).createRowHelper(1);
/* 131 */       LayoutSettings $$1 = $$0.newCellSettings();
/*     */       
/* 133 */       this.nameEdit = new EditBox(CreateWorldScreen.this.font, 208, 20, (Component)Component.translatable("selectWorld.enterName"));
/* 134 */       this.nameEdit.setValue(CreateWorldScreen.this.uiState.getName());
/* 135 */       Objects.requireNonNull(CreateWorldScreen.this.uiState); this.nameEdit.setResponder(CreateWorldScreen.this.uiState::setName);
/* 136 */       CreateWorldScreen.this.uiState.addListener($$0 -> this.nameEdit.setTooltip(Tooltip.create((Component)Component.translatable("selectWorld.targetFolder", new Object[] { Component.literal($$0.getTargetFolder()).withStyle(ChatFormatting.ITALIC) }))));
/* 137 */       CreateWorldScreen.this.setInitialFocus((GuiEventListener)this.nameEdit);
/* 138 */       $$0.addChild((LayoutElement)CommonLayouts.labeledElement(CreateWorldScreen.this.font, (LayoutElement)this.nameEdit, CreateWorldScreen.NAME_LABEL), $$0.newCellSettings().alignHorizontallyCenter());
/*     */       
/* 140 */       CycleButton<WorldCreationUiState.SelectedGameMode> $$2 = (CycleButton<WorldCreationUiState.SelectedGameMode>)$$0.addChild(
/* 141 */           (LayoutElement)CycleButton.builder($$0 -> $$0.displayName)
/* 142 */           .withValues((Object[])new WorldCreationUiState.SelectedGameMode[] { WorldCreationUiState.SelectedGameMode.SURVIVAL, WorldCreationUiState.SelectedGameMode.HARDCORE, WorldCreationUiState.SelectedGameMode.CREATIVE
/* 143 */             }, ).create(0, 0, 210, 20, CreateWorldScreen.GAME_MODEL_LABEL, ($$0, $$1) -> CreateWorldScreen.this.uiState.setGameMode($$1)), $$1);
/*     */ 
/*     */ 
/*     */       
/* 147 */       CreateWorldScreen.this.uiState.addListener($$1 -> {
/*     */             $$0.setValue($$1.getGameMode());
/*     */             
/*     */             $$0.active = !$$1.isDebug();
/*     */             $$0.setTooltip(Tooltip.create($$1.getGameMode().getInfo()));
/*     */           });
/* 153 */       CycleButton<Difficulty> $$3 = (CycleButton<Difficulty>)$$0.addChild(
/* 154 */           (LayoutElement)CycleButton.builder(Difficulty::getDisplayName)
/* 155 */           .withValues((Object[])Difficulty.values())
/* 156 */           .create(0, 0, 210, 20, (Component)Component.translatable("options.difficulty"), ($$0, $$1) -> CreateWorldScreen.this.uiState.setDifficulty($$1)), $$1);
/*     */ 
/*     */       
/* 159 */       CreateWorldScreen.this.uiState.addListener($$1 -> {
/*     */             $$0.setValue(CreateWorldScreen.this.uiState.getDifficulty());
/*     */             
/*     */             $$0.active = !CreateWorldScreen.this.uiState.isHardcore();
/*     */             $$0.setTooltip(Tooltip.create(CreateWorldScreen.this.uiState.getDifficulty().getInfo()));
/*     */           });
/* 165 */       CycleButton<Boolean> $$4 = (CycleButton<Boolean>)$$0.addChild((LayoutElement)CycleButton.onOffBuilder()
/* 166 */           .withTooltip($$0 -> Tooltip.create(CreateWorldScreen.ALLOW_CHEATS_INFO))
/* 167 */           .create(0, 0, 210, 20, ALLOW_CHEATS, ($$0, $$1) -> CreateWorldScreen.this.uiState.setAllowCheats($$1.booleanValue())));
/*     */       
/* 169 */       CreateWorldScreen.this.uiState.addListener($$1 -> {
/*     */             $$0.setValue(Boolean.valueOf(CreateWorldScreen.this.uiState.isAllowCheats()));
/* 171 */             $$0.active = (!CreateWorldScreen.this.uiState.isDebug() && !CreateWorldScreen.this.uiState.isHardcore());
/*     */           });
/*     */       
/* 174 */       if (!SharedConstants.getCurrentVersion().isStable())
/* 175 */         $$0.addChild((LayoutElement)Button.builder(CreateWorldScreen.EXPERIMENTS_LABEL, $$0 -> CreateWorldScreen.this.openExperimentsScreen(CreateWorldScreen.this.uiState.getSettings().dataConfiguration())).width(210).build()); 
/*     */     }
/*     */   }
/*     */   
/*     */   private class WorldTab
/*     */     extends GridLayoutTab {
/* 181 */     private static final Component TITLE = (Component)Component.translatable("createWorld.tab.world.title");
/* 182 */     private static final Component AMPLIFIED_HELP_TEXT = (Component)Component.translatable("generator.minecraft.amplified.info");
/* 183 */     private static final Component GENERATE_STRUCTURES = (Component)Component.translatable("selectWorld.mapFeatures");
/* 184 */     private static final Component GENERATE_STRUCTURES_INFO = (Component)Component.translatable("selectWorld.mapFeatures.info");
/* 185 */     private static final Component BONUS_CHEST = (Component)Component.translatable("selectWorld.bonusItems");
/* 186 */     private static final Component SEED_LABEL = (Component)Component.translatable("selectWorld.enterSeed");
/* 187 */     static final Component SEED_EMPTY_HINT = (Component)Component.translatable("selectWorld.seedInfo").withStyle(ChatFormatting.DARK_GRAY);
/*     */     private static final int WORLD_TAB_WIDTH = 310;
/*     */     private final EditBox seedEdit;
/*     */     private final Button customizeTypeButton;
/*     */     
/*     */     WorldTab() {
/* 193 */       super(TITLE);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 198 */       GridLayout.RowHelper $$0 = this.layout.columnSpacing(10).rowSpacing(8).createRowHelper(2);
/*     */       
/* 200 */       CycleButton<WorldCreationUiState.WorldTypeEntry> $$1 = (CycleButton<WorldCreationUiState.WorldTypeEntry>)$$0.addChild((LayoutElement)CycleButton.builder(WorldCreationUiState.WorldTypeEntry::describePreset)
/* 201 */           .withValues(createWorldTypeValueSupplier())
/* 202 */           .withCustomNarration(WorldTab::createTypeButtonNarration)
/* 203 */           .create(0, 0, 150, 20, (Component)Component.translatable("selectWorld.mapType"), ($$0, $$1) -> CreateWorldScreen.this.uiState.setWorldType($$1)));
/*     */ 
/*     */       
/* 206 */       $$1.setValue(CreateWorldScreen.this.uiState.getWorldType());
/* 207 */       CreateWorldScreen.this.uiState.addListener($$1 -> {
/*     */             WorldCreationUiState.WorldTypeEntry $$2 = $$1.getWorldType();
/*     */             
/*     */             $$0.setValue($$2);
/*     */             
/*     */             if ($$2.isAmplified()) {
/*     */               $$0.setTooltip(Tooltip.create(AMPLIFIED_HELP_TEXT));
/*     */             } else {
/*     */               $$0.setTooltip(null);
/*     */             } 
/*     */             $$0.active = (CreateWorldScreen.this.uiState.getWorldType().preset() != null);
/*     */           });
/* 219 */       this.customizeTypeButton = (Button)$$0.addChild((LayoutElement)Button.builder((Component)Component.translatable("selectWorld.customizeType"), $$0 -> openPresetEditor()).build());
/* 220 */       CreateWorldScreen.this.uiState.addListener($$0 -> this.customizeTypeButton.active = (!$$0.isDebug() && $$0.getPresetEditor() != null));
/*     */       
/* 222 */       this.seedEdit = new EditBox(CreateWorldScreen.this.font, 308, 20, (Component)Component.translatable("selectWorld.enterSeed"))
/*     */         {
/*     */           protected MutableComponent createNarrationMessage() {
/* 225 */             return super.createNarrationMessage().append(CommonComponents.NARRATION_SEPARATOR).append(CreateWorldScreen.WorldTab.SEED_EMPTY_HINT);
/*     */           }
/*     */         };
/* 228 */       this.seedEdit.setHint(SEED_EMPTY_HINT);
/* 229 */       this.seedEdit.setValue(CreateWorldScreen.this.uiState.getSeed());
/* 230 */       this.seedEdit.setResponder($$0 -> CreateWorldScreen.this.uiState.setSeed(this.seedEdit.getValue()));
/* 231 */       $$0.addChild((LayoutElement)CommonLayouts.labeledElement(CreateWorldScreen.this.font, (LayoutElement)this.seedEdit, SEED_LABEL), 2);
/*     */       
/* 233 */       SwitchGrid.Builder $$2 = SwitchGrid.builder(310);
/* 234 */       Objects.requireNonNull(CreateWorldScreen.this.uiState); Objects.requireNonNull(CreateWorldScreen.this.uiState); $$2.addSwitch(GENERATE_STRUCTURES, CreateWorldScreen.this.uiState::isGenerateStructures, CreateWorldScreen.this.uiState::setGenerateStructures)
/* 235 */         .withIsActiveCondition(() -> !CreateWorldScreen.this.uiState.isDebug())
/* 236 */         .withInfo(GENERATE_STRUCTURES_INFO);
/* 237 */       Objects.requireNonNull(CreateWorldScreen.this.uiState); Objects.requireNonNull(CreateWorldScreen.this.uiState); $$2.addSwitch(BONUS_CHEST, CreateWorldScreen.this.uiState::isBonusChest, CreateWorldScreen.this.uiState::setBonusChest)
/* 238 */         .withIsActiveCondition(() -> (!CreateWorldScreen.this.uiState.isHardcore() && !CreateWorldScreen.this.uiState.isDebug()));
/* 239 */       SwitchGrid $$3 = $$2.build($$1 -> $$0.addChild($$1, 2));
/* 240 */       CreateWorldScreen.this.uiState.addListener($$1 -> $$0.refreshStates());
/*     */     }
/*     */     
/*     */     private void openPresetEditor() {
/* 244 */       PresetEditor $$0 = CreateWorldScreen.this.uiState.getPresetEditor();
/* 245 */       if ($$0 != null) {
/* 246 */         CreateWorldScreen.this.minecraft.setScreen($$0.createEditScreen(CreateWorldScreen.this, CreateWorldScreen.this.uiState.getSettings()));
/*     */       }
/*     */     }
/*     */     
/*     */     private CycleButton.ValueListSupplier<WorldCreationUiState.WorldTypeEntry> createWorldTypeValueSupplier() {
/* 251 */       return new CycleButton.ValueListSupplier<WorldCreationUiState.WorldTypeEntry>()
/*     */         {
/*     */           public List<WorldCreationUiState.WorldTypeEntry> getSelectedList() {
/* 254 */             return CycleButton.DEFAULT_ALT_LIST_SELECTOR.getAsBoolean() ? CreateWorldScreen.this.uiState.getAltPresetList() : CreateWorldScreen.this.uiState.getNormalPresetList();
/*     */           }
/*     */ 
/*     */           
/*     */           public List<WorldCreationUiState.WorldTypeEntry> getDefaultList() {
/* 259 */             return CreateWorldScreen.this.uiState.getNormalPresetList();
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     private static MutableComponent createTypeButtonNarration(CycleButton<WorldCreationUiState.WorldTypeEntry> $$0) {
/* 265 */       if (((WorldCreationUiState.WorldTypeEntry)$$0.getValue()).isAmplified()) {
/* 266 */         return CommonComponents.joinForNarration(new Component[] { (Component)$$0.createDefaultNarrationMessage(), AMPLIFIED_HELP_TEXT });
/*     */       }
/* 268 */       return $$0.createDefaultNarrationMessage();
/*     */     }
/*     */   } class null extends EditBox { null(Font $$1, int $$2, int $$3, Component $$4) { super($$1, $$2, $$3, $$4); } protected MutableComponent createNarrationMessage() { return super.createNarrationMessage().append(CommonComponents.NARRATION_SEPARATOR).append(CreateWorldScreen.WorldTab.SEED_EMPTY_HINT); } } class null implements CycleButton.ValueListSupplier<WorldCreationUiState.WorldTypeEntry> {
/*     */     public List<WorldCreationUiState.WorldTypeEntry> getSelectedList() { return CycleButton.DEFAULT_ALT_LIST_SELECTOR.getAsBoolean() ? CreateWorldScreen.this.uiState.getAltPresetList() : CreateWorldScreen.this.uiState.getNormalPresetList(); } public List<WorldCreationUiState.WorldTypeEntry> getDefaultList() { return CreateWorldScreen.this.uiState.getNormalPresetList(); }
/*     */   } private class MoreTab extends GridLayoutTab {
/* 273 */     private static final Component TITLE = (Component)Component.translatable("createWorld.tab.more.title");
/* 274 */     private static final Component GAME_RULES_LABEL = (Component)Component.translatable("selectWorld.gameRules");
/* 275 */     private static final Component DATA_PACKS_LABEL = (Component)Component.translatable("selectWorld.dataPacks");
/*     */     
/*     */     MoreTab() {
/* 278 */       super(TITLE);
/*     */ 
/*     */ 
/*     */       
/* 282 */       GridLayout.RowHelper $$0 = this.layout.rowSpacing(8).createRowHelper(1);
/*     */       
/* 284 */       $$0.addChild((LayoutElement)Button.builder(GAME_RULES_LABEL, $$0 -> openGameRulesScreen())
/* 285 */           .width(210)
/* 286 */           .build());
/*     */ 
/*     */       
/* 289 */       $$0.addChild((LayoutElement)Button.builder(CreateWorldScreen.EXPERIMENTS_LABEL, $$0 -> CreateWorldScreen.this.openExperimentsScreen(CreateWorldScreen.this.uiState.getSettings().dataConfiguration()))
/* 290 */           .width(210)
/* 291 */           .build());
/*     */       
/* 293 */       $$0.addChild((LayoutElement)Button.builder(DATA_PACKS_LABEL, $$0 -> CreateWorldScreen.this.openDataPackSelectionScreen(CreateWorldScreen.this.uiState.getSettings().dataConfiguration()))
/* 294 */           .width(210)
/* 295 */           .build());
/*     */     }
/*     */     
/*     */     private void openGameRulesScreen() {
/* 299 */       CreateWorldScreen.this.minecraft.setScreen(new EditGameRulesScreen(CreateWorldScreen.this.uiState.getGameRules().copy(), $$0 -> {
/*     */               CreateWorldScreen.this.minecraft.setScreen(CreateWorldScreen.this);
/*     */               Objects.requireNonNull(CreateWorldScreen.this.uiState);
/*     */               $$0.ifPresent(CreateWorldScreen.this.uiState::setGameRules);
/*     */             }));
/*     */     } }
/*     */   
/*     */   public static void openFresh(Minecraft $$0, @Nullable Screen $$1) {
/* 307 */     queueLoadScreen($$0, PREPARING_WORLD_DATA);
/*     */     
/* 309 */     PackRepository $$2 = new PackRepository(new RepositorySource[] { (RepositorySource)new ServerPacksSource($$0.directoryValidator()) });
/* 310 */     WorldLoader.InitConfig $$3 = createDefaultLoadConfig($$2, WorldDataConfiguration.DEFAULT);
/*     */     
/* 312 */     CompletableFuture<WorldCreationContext> $$4 = WorldLoader.load($$3, $$0 -> new WorldLoader.DataLoadOutput(new DataPackReloadCookie(new WorldGenSettings(WorldOptions.defaultWithRandomSeed(), WorldPresets.createNormalWorldDimensions((RegistryAccess)$$0.datapackWorldgen())), $$0.dataConfiguration()), $$0.datapackDimensions()), ($$0, $$1, $$2, $$3) -> {
/*     */           $$0.close();
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
/*     */           return new WorldCreationContext($$3.worldGenSettings(), $$2, $$1, $$3.dataConfiguration());
/* 328 */         }Util.backgroundExecutor(), (Executor)$$0);
/*     */ 
/*     */     
/* 331 */     Objects.requireNonNull($$4); $$0.managedBlock($$4::isDone);
/*     */ 
/*     */     
/* 334 */     $$0.setScreen(new CreateWorldScreen($$0, $$1, $$4.join(), Optional.of(WorldPresets.NORMAL), OptionalLong.empty()));
/*     */   }
/*     */   
/*     */   public static CreateWorldScreen createFromExisting(Minecraft $$0, @Nullable Screen $$1, LevelSettings $$2, WorldCreationContext $$3, @Nullable Path $$4) {
/* 338 */     CreateWorldScreen $$5 = new CreateWorldScreen($$0, $$1, $$3, WorldPresets.fromSettings($$3.selectedDimensions().dimensions()), OptionalLong.of($$3.options().seed()));
/* 339 */     $$5.recreated = true;
/*     */     
/* 341 */     $$5.uiState.setName($$2.levelName());
/* 342 */     $$5.uiState.setAllowCheats($$2.allowCommands());
/* 343 */     $$5.uiState.setDifficulty($$2.difficulty());
/* 344 */     $$5.uiState.getGameRules().assignFrom($$2.gameRules(), null);
/*     */     
/* 346 */     if ($$2.hardcore()) {
/* 347 */       $$5.uiState.setGameMode(WorldCreationUiState.SelectedGameMode.HARDCORE);
/* 348 */     } else if ($$2.gameType().isSurvival()) {
/* 349 */       $$5.uiState.setGameMode(WorldCreationUiState.SelectedGameMode.SURVIVAL);
/* 350 */     } else if ($$2.gameType().isCreative()) {
/* 351 */       $$5.uiState.setGameMode(WorldCreationUiState.SelectedGameMode.CREATIVE);
/*     */     } 
/* 353 */     $$5.tempDataPackDir = $$4;
/* 354 */     return $$5;
/*     */   }
/*     */   
/*     */   private CreateWorldScreen(Minecraft $$0, @Nullable Screen $$1, WorldCreationContext $$2, Optional<ResourceKey<WorldPreset>> $$3, OptionalLong $$4) {
/* 358 */     super((Component)Component.translatable("selectWorld.create")); this.tabManager = new TabManager(this::addRenderableWidget, $$1 -> $$0.removeWidget($$1));
/* 359 */     this.lastScreen = $$1;
/* 360 */     this.packValidator = $$0.directoryValidator();
/*     */     
/* 362 */     this.uiState = new WorldCreationUiState($$0.getLevelSource().getBaseDir(), $$2, $$3, $$4);
/*     */   }
/*     */   
/*     */   public WorldCreationUiState getUiState() {
/* 366 */     return this.uiState;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void init() {
/* 371 */     this
/*     */       
/* 373 */       .tabNavigationBar = TabNavigationBar.builder(this.tabManager, this.width).addTabs(new Tab[] { (Tab)new GameTab(), (Tab)new WorldTab(), (Tab)new MoreTab() }).build();
/* 374 */     addRenderableWidget(this.tabNavigationBar);
/*     */     
/* 376 */     this.bottomButtons = (new GridLayout()).columnSpacing(10);
/* 377 */     GridLayout.RowHelper $$0 = this.bottomButtons.createRowHelper(2);
/*     */     
/* 379 */     $$0.addChild((LayoutElement)Button.builder((Component)Component.translatable("selectWorld.create"), $$0 -> onCreate()).build());
/* 380 */     $$0.addChild((LayoutElement)Button.builder(CommonComponents.GUI_CANCEL, $$0 -> popScreen()).build());
/*     */     
/* 382 */     this.bottomButtons.visitWidgets($$0 -> {
/*     */           $$0.setTabOrderGroup(1);
/*     */           
/*     */           addRenderableWidget($$0);
/*     */         });
/* 387 */     this.tabNavigationBar.selectTab(0, false);
/*     */ 
/*     */     
/* 390 */     this.uiState.onChanged();
/*     */     
/* 392 */     repositionElements();
/*     */   }
/*     */ 
/*     */   
/*     */   public void repositionElements() {
/* 397 */     if (this.tabNavigationBar == null || this.bottomButtons == null) {
/*     */       return;
/*     */     }
/* 400 */     this.tabNavigationBar.setWidth(this.width);
/* 401 */     this.tabNavigationBar.arrangeElements();
/*     */     
/* 403 */     this.bottomButtons.arrangeElements();
/* 404 */     FrameLayout.centerInRectangle((LayoutElement)this.bottomButtons, 0, this.height - 36, this.width, 36);
/*     */     
/* 406 */     int $$0 = this.tabNavigationBar.getRectangle().bottom();
/* 407 */     ScreenRectangle $$1 = new ScreenRectangle(0, $$0, this.width, this.bottomButtons.getY() - $$0);
/* 408 */     this.tabManager.setTabArea($$1);
/*     */   }
/*     */   
/*     */   private static void queueLoadScreen(Minecraft $$0, Component $$1) {
/* 412 */     $$0.forceSetScreen((Screen)new GenericDirtMessageScreen($$1));
/*     */   }
/*     */   
/*     */   private void onCreate() {
/* 416 */     WorldCreationContext $$0 = this.uiState.getSettings();
/* 417 */     WorldDimensions.Complete $$1 = $$0.selectedDimensions().bake($$0.datapackDimensions());
/*     */     
/* 419 */     LayeredRegistryAccess<RegistryLayer> $$2 = $$0.worldgenRegistries().replaceFrom(RegistryLayer.DIMENSIONS, new RegistryAccess.Frozen[] { $$1.dimensionsRegistryAccess() });
/* 420 */     Lifecycle $$3 = FeatureFlags.isExperimental($$0.dataConfiguration().enabledFeatures()) ? Lifecycle.experimental() : Lifecycle.stable();
/* 421 */     Lifecycle $$4 = $$2.compositeAccess().allRegistriesLifecycle();
/* 422 */     Lifecycle $$5 = $$4.add($$3);
/*     */ 
/*     */     
/* 425 */     boolean $$6 = (!this.recreated && $$4 == Lifecycle.stable());
/* 426 */     WorldOpenFlows.confirmWorldCreation(this.minecraft, this, $$5, () -> createNewWorld($$0.specialWorldProperty(), $$1, $$2), $$6);
/*     */   }
/*     */   
/*     */   private void createNewWorld(PrimaryLevelData.SpecialWorldProperty $$0, LayeredRegistryAccess<RegistryLayer> $$1, Lifecycle $$2) {
/* 430 */     queueLoadScreen(this.minecraft, PREPARING_WORLD_DATA);
/*     */     
/* 432 */     Optional<LevelStorageSource.LevelStorageAccess> $$3 = createNewWorldDirectory();
/* 433 */     if ($$3.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/* 437 */     removeTempDataPackDir();
/*     */     
/* 439 */     boolean $$4 = ($$0 == PrimaryLevelData.SpecialWorldProperty.DEBUG);
/*     */     
/* 441 */     WorldCreationContext $$5 = this.uiState.getSettings();
/* 442 */     LevelSettings $$6 = createLevelSettings($$4);
/* 443 */     PrimaryLevelData primaryLevelData = new PrimaryLevelData($$6, $$5.options(), $$0, $$2);
/* 444 */     this.minecraft.createWorldOpenFlows().createLevelFromExistingSettings($$3.get(), $$5.dataPackResources(), $$1, (WorldData)primaryLevelData);
/*     */   }
/*     */   
/*     */   private LevelSettings createLevelSettings(boolean $$0) {
/* 448 */     String $$1 = this.uiState.getName().trim();
/* 449 */     if ($$0) {
/* 450 */       GameRules $$2 = new GameRules();
/* 451 */       ((GameRules.BooleanValue)$$2.getRule(GameRules.RULE_DAYLIGHT)).set(false, null);
/* 452 */       return new LevelSettings($$1, GameType.SPECTATOR, false, Difficulty.PEACEFUL, true, $$2, WorldDataConfiguration.DEFAULT);
/*     */     } 
/* 454 */     return new LevelSettings($$1, (this.uiState.getGameMode()).gameType, this.uiState.isHardcore(), this.uiState.getDifficulty(), this.uiState.isAllowCheats(), this.uiState.getGameRules(), this.uiState.getSettings().dataConfiguration());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean keyPressed(int $$0, int $$1, int $$2) {
/* 459 */     if (this.tabNavigationBar.keyPressed($$0)) {
/* 460 */       return true;
/*     */     }
/*     */     
/* 463 */     if (super.keyPressed($$0, $$1, $$2)) {
/* 464 */       return true;
/*     */     }
/*     */     
/* 467 */     if ($$0 == 257 || $$0 == 335) {
/* 468 */       onCreate();
/* 469 */       return true;
/*     */     } 
/*     */     
/* 472 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onClose() {
/* 477 */     popScreen();
/*     */   }
/*     */   
/*     */   public void popScreen() {
/* 481 */     this.minecraft.setScreen(this.lastScreen);
/* 482 */     removeTempDataPackDir();
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 487 */     super.render($$0, $$1, $$2, $$3);
/* 488 */     $$0.blit(FOOTER_SEPERATOR, 0, Mth.roundToward(this.height - 36 - 2, 2), 0.0F, 0.0F, this.width, 2, 32, 2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderDirtBackground(GuiGraphics $$0) {
/* 493 */     int $$1 = 32;
/* 494 */     $$0.blit(LIGHT_DIRT_BACKGROUND, 0, 0, 0, 0.0F, 0.0F, this.width, this.height, 32, 32);
/*     */   }
/*     */ 
/*     */   
/*     */   protected <T extends GuiEventListener & net.minecraft.client.gui.narration.NarratableEntry> T addWidget(T $$0) {
/* 499 */     return (T)super.addWidget((GuiEventListener)$$0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected <T extends GuiEventListener & net.minecraft.client.gui.components.Renderable & net.minecraft.client.gui.narration.NarratableEntry> T addRenderableWidget(T $$0) {
/* 504 */     return (T)super.addRenderableWidget((GuiEventListener)$$0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private Path getTempDataPackDir() {
/* 511 */     if (this.tempDataPackDir == null) {
/*     */       try {
/* 513 */         this.tempDataPackDir = Files.createTempDirectory("mcworld-", (FileAttribute<?>[])new FileAttribute[0]);
/* 514 */       } catch (IOException $$0) {
/* 515 */         LOGGER.warn("Failed to create temporary dir", $$0);
/* 516 */         SystemToast.onPackCopyFailure(this.minecraft, this.uiState.getTargetFolder());
/* 517 */         popScreen();
/*     */       } 
/*     */     }
/*     */     
/* 521 */     return this.tempDataPackDir;
/*     */   }
/*     */   
/*     */   void openExperimentsScreen(WorldDataConfiguration $$0) {
/* 525 */     Pair<Path, PackRepository> $$1 = getDataPackSelectionSettings($$0);
/*     */     
/* 527 */     if ($$1 != null) {
/* 528 */       this.minecraft.setScreen(new ExperimentsScreen(this, (PackRepository)$$1.getSecond(), $$0 -> tryApplyNewDataPacks($$0, false, this::openExperimentsScreen)));
/*     */     }
/*     */   }
/*     */   
/*     */   void openDataPackSelectionScreen(WorldDataConfiguration $$0) {
/* 533 */     Pair<Path, PackRepository> $$1 = getDataPackSelectionSettings($$0);
/*     */     
/* 535 */     if ($$1 != null) {
/* 536 */       this.minecraft.setScreen((Screen)new PackSelectionScreen((PackRepository)$$1.getSecond(), $$0 -> tryApplyNewDataPacks($$0, true, this::openDataPackSelectionScreen), (Path)$$1.getFirst(), (Component)Component.translatable("dataPack.title")));
/*     */     }
/*     */   }
/*     */   
/*     */   private void tryApplyNewDataPacks(PackRepository $$0, boolean $$1, Consumer<WorldDataConfiguration> $$2) {
/* 541 */     ImmutableList immutableList = ImmutableList.copyOf($$0.getSelectedIds());
/* 542 */     List<String> $$4 = (List<String>)$$0.getAvailableIds().stream().filter($$1 -> !$$0.contains($$1)).collect(ImmutableList.toImmutableList());
/* 543 */     WorldDataConfiguration $$5 = new WorldDataConfiguration(new DataPackConfig((List)immutableList, $$4), this.uiState.getSettings().dataConfiguration().enabledFeatures());
/*     */     
/* 545 */     if (this.uiState.tryUpdateDataConfiguration($$5)) {
/*     */       
/* 547 */       this.minecraft.setScreen(this);
/*     */       
/*     */       return;
/*     */     } 
/* 551 */     FeatureFlagSet $$6 = $$0.getRequestedFeatureFlags();
/* 552 */     if (FeatureFlags.isExperimental($$6) && $$1) {
/* 553 */       this.minecraft.setScreen(new ConfirmExperimentalFeaturesScreen($$0
/* 554 */             .getSelectedPacks(), $$3 -> {
/*     */ 
/*     */               
/*     */               if ($$3) {
/*     */                 applyNewPackConfig($$0, $$1, $$2);
/*     */               } else {
/*     */                 $$2.accept(this.uiState.getSettings().dataConfiguration());
/*     */               } 
/*     */             }));
/*     */     } else {
/* 564 */       applyNewPackConfig($$0, $$5, $$2);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void applyNewPackConfig(PackRepository $$0, WorldDataConfiguration $$1, Consumer<WorldDataConfiguration> $$2) {
/* 570 */     this.minecraft.forceSetScreen((Screen)new GenericDirtMessageScreen((Component)Component.translatable("dataPack.validation.working")));
/*     */     
/* 572 */     WorldLoader.InitConfig $$3 = createDefaultLoadConfig($$0, $$1);
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
/* 604 */     Objects.requireNonNull(this.uiState); WorldLoader.load($$3, $$0 -> { if ($$0.datapackWorldgen().registryOrThrow(Registries.WORLD_PRESET).size() == 0) throw new IllegalStateException("Needs at least one world preset to continue");  if ($$0.datapackWorldgen().registryOrThrow(Registries.BIOME).size() == 0) throw new IllegalStateException("Needs at least one biome continue");  WorldCreationContext $$1 = this.uiState.getSettings(); RegistryOps registryOps1 = RegistryOps.create((DynamicOps)JsonOps.INSTANCE, (HolderLookup.Provider)$$1.worldgenLoadContext()); DataResult<JsonElement> $$3 = WorldGenSettings.encode((DynamicOps)registryOps1, $$1.options(), $$1.selectedDimensions()).setLifecycle(Lifecycle.stable()); RegistryOps registryOps2 = RegistryOps.create((DynamicOps)JsonOps.INSTANCE, (HolderLookup.Provider)$$0.datapackWorldgen()); Objects.requireNonNull(LOGGER); WorldGenSettings $$5 = (WorldGenSettings)$$3.flatMap(()).getOrThrow(false, Util.prefix("Error parsing worldgen settings after loading data packs: ", LOGGER::error)); return new WorldLoader.DataLoadOutput(new DataPackReloadCookie($$5, $$0.dataConfiguration()), $$0.datapackDimensions()); }($$0, $$1, $$2, $$3) -> { $$0.close(); return new WorldCreationContext($$3.worldGenSettings(), $$2, $$1, $$3.dataConfiguration()); }Util.backgroundExecutor(), (Executor)this.minecraft).thenAcceptAsync(this.uiState::setSettings, (Executor)this.minecraft)
/* 605 */       .handle(($$1, $$2) -> {
/*     */           if ($$2 != null) {
/*     */             LOGGER.warn("Failed to validate datapack", $$2);
/*     */             this.minecraft.setScreen((Screen)new ConfirmScreen((), (Component)Component.translatable("dataPack.validation.failed"), CommonComponents.EMPTY, (Component)Component.translatable("dataPack.validation.back"), (Component)Component.translatable("dataPack.validation.reset")));
/*     */           } else {
/*     */             this.minecraft.setScreen(this);
/*     */           } 
/*     */           return null;
/*     */         });
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
/*     */   private static WorldLoader.InitConfig createDefaultLoadConfig(PackRepository $$0, WorldDataConfiguration $$1) {
/* 628 */     WorldLoader.PackConfig $$2 = new WorldLoader.PackConfig($$0, $$1, false, true);
/* 629 */     return new WorldLoader.InitConfig($$2, Commands.CommandSelection.INTEGRATED, 2);
/*     */   }
/*     */   
/*     */   private void removeTempDataPackDir() {
/* 633 */     if (this.tempDataPackDir != null) { 
/* 634 */       try { Stream<Path> $$0 = Files.walk(this.tempDataPackDir, new java.nio.file.FileVisitOption[0]); 
/* 635 */         try { $$0.sorted(Comparator.reverseOrder()).forEach($$0 -> {
/*     */                 try {
/*     */                   Files.delete($$0);
/* 638 */                 } catch (IOException $$1) {
/*     */                   LOGGER.warn("Failed to remove temporary file {}", $$0, $$1);
/*     */                 } 
/*     */               });
/* 642 */           if ($$0 != null) $$0.close();  } catch (Throwable throwable) { if ($$0 != null) try { $$0.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException $$1)
/* 643 */       { LOGGER.warn("Failed to list temporary dir {}", this.tempDataPackDir); }
/*     */       
/* 645 */       this.tempDataPackDir = null; }
/*     */   
/*     */   }
/*     */   
/*     */   private static void copyBetweenDirs(Path $$0, Path $$1, Path $$2) {
/*     */     try {
/* 651 */       Util.copyBetweenDirs($$0, $$1, $$2);
/* 652 */     } catch (IOException $$3) {
/* 653 */       LOGGER.warn("Failed to copy datapack file from {} to {}", $$2, $$1);
/* 654 */       throw new UncheckedIOException($$3);
/*     */     } 
/*     */   }
/*     */   
/*     */   private Optional<LevelStorageSource.LevelStorageAccess> createNewWorldDirectory() {
/* 659 */     String $$0 = this.uiState.getTargetFolder();
/*     */     
/*     */     try {
/* 662 */       LevelStorageSource.LevelStorageAccess $$1 = this.minecraft.getLevelSource().createAccess($$0);
/* 663 */       if (this.tempDataPackDir == null)
/* 664 */         return Optional.of($$1); 
/*     */       
/* 666 */       try { Stream<Path> $$2 = Files.walk(this.tempDataPackDir, new java.nio.file.FileVisitOption[0]); 
/* 667 */         try { Path $$3 = $$1.getLevelPath(LevelResource.DATAPACK_DIR);
/* 668 */           FileUtil.createDirectoriesSafe($$3);
/* 669 */           $$2.filter($$0 -> !$$0.equals(this.tempDataPackDir)).forEach($$1 -> copyBetweenDirs(this.tempDataPackDir, $$0, $$1));
/* 670 */           Optional<LevelStorageSource.LevelStorageAccess> optional = Optional.of($$1);
/* 671 */           if ($$2 != null) $$2.close();  return optional; } catch (Throwable throwable) { if ($$2 != null) try { $$2.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException|UncheckedIOException $$4)
/* 672 */       { LOGGER.warn("Failed to copy datapacks to world {}", $$0, $$4);
/* 673 */         $$1.close(); }
/*     */     
/* 675 */     } catch (IOException|UncheckedIOException $$5) {
/* 676 */       LOGGER.warn("Failed to create access for {}", $$0, $$5);
/*     */     } 
/* 678 */     SystemToast.onPackCopyFailure(this.minecraft, $$0);
/* 679 */     popScreen();
/* 680 */     return Optional.empty();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static Path createTempDataPackDirFromExistingWorld(Path $$0, Minecraft $$1) {
/* 685 */     MutableObject<Path> $$2 = new MutableObject(); 
/* 686 */     try { Stream<Path> $$3 = Files.walk($$0, new java.nio.file.FileVisitOption[0]); 
/* 687 */       try { $$3.filter($$1 -> !$$1.equals($$0)).forEach($$2 -> {
/*     */               Path $$3 = (Path)$$0.getValue();
/*     */               
/*     */               if ($$3 == null) {
/*     */                 try {
/*     */                   $$3 = Files.createTempDirectory("mcworld-", (FileAttribute<?>[])new FileAttribute[0]);
/* 693 */                 } catch (IOException $$4) {
/*     */                   LOGGER.warn("Failed to create temporary dir");
/*     */                   throw new UncheckedIOException($$4);
/*     */                 } 
/*     */                 $$0.setValue($$3);
/*     */               } 
/*     */               copyBetweenDirs($$1, $$3, $$2);
/*     */             });
/* 701 */         if ($$3 != null) $$3.close();  } catch (Throwable throwable) { if ($$3 != null) try { $$3.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException|UncheckedIOException $$4)
/* 702 */     { LOGGER.warn("Failed to copy datapacks from world {}", $$0, $$4);
/* 703 */       SystemToast.onPackCopyFailure($$1, $$0.toString());
/* 704 */       return null; }
/*     */     
/* 706 */     return (Path)$$2.getValue();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private Pair<Path, PackRepository> getDataPackSelectionSettings(WorldDataConfiguration $$0) {
/* 711 */     Path $$1 = getTempDataPackDir();
/* 712 */     if ($$1 != null) {
/* 713 */       if (this.tempDataPackRepository == null) {
/* 714 */         this.tempDataPackRepository = ServerPacksSource.createPackRepository($$1, this.packValidator);
/* 715 */         this.tempDataPackRepository.reload();
/*     */       } 
/*     */       
/* 718 */       this.tempDataPackRepository.setSelected($$0.dataPacks().getEnabled());
/* 719 */       return Pair.of($$1, this.tempDataPackRepository);
/*     */     } 
/*     */     
/* 722 */     return null;
/*     */   } private static final class DataPackReloadCookie extends Record {
/*     */     private final WorldGenSettings worldGenSettings; private final WorldDataConfiguration dataConfiguration;
/* 725 */     DataPackReloadCookie(WorldGenSettings $$0, WorldDataConfiguration $$1) { this.worldGenSettings = $$0; this.dataConfiguration = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/client/gui/screens/worldselection/CreateWorldScreen$DataPackReloadCookie;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #725	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/gui/screens/worldselection/CreateWorldScreen$DataPackReloadCookie; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/gui/screens/worldselection/CreateWorldScreen$DataPackReloadCookie;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #725	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/gui/screens/worldselection/CreateWorldScreen$DataPackReloadCookie; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/client/gui/screens/worldselection/CreateWorldScreen$DataPackReloadCookie;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #725	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/client/gui/screens/worldselection/CreateWorldScreen$DataPackReloadCookie;
/* 725 */       //   0	8	1	$$0	Ljava/lang/Object; } public WorldGenSettings worldGenSettings() { return this.worldGenSettings; } public WorldDataConfiguration dataConfiguration() { return this.dataConfiguration; }
/*     */   
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\worldselection\CreateWorldScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */