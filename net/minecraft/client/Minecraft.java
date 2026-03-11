/*      */ package net.minecraft.client;
/*      */ import com.google.common.collect.ImmutableList;
/*      */ import com.google.common.collect.ImmutableMap;
/*      */ import com.google.common.collect.Lists;
/*      */ import com.google.common.collect.Queues;
/*      */ import com.google.common.collect.UnmodifiableIterator;
/*      */ import com.mojang.authlib.GameProfile;
/*      */ import com.mojang.authlib.exceptions.AuthenticationException;
/*      */ import com.mojang.authlib.minecraft.BanDetails;
/*      */ import com.mojang.authlib.minecraft.MinecraftSessionService;
/*      */ import com.mojang.authlib.minecraft.UserApiService;
/*      */ import com.mojang.authlib.yggdrasil.ProfileActionType;
/*      */ import com.mojang.authlib.yggdrasil.ProfileResult;
/*      */ import com.mojang.authlib.yggdrasil.ServicesKeyType;
/*      */ import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
/*      */ import com.mojang.blaze3d.pipeline.MainTarget;
/*      */ import com.mojang.blaze3d.pipeline.RenderTarget;
/*      */ import com.mojang.blaze3d.pipeline.TextureTarget;
/*      */ import com.mojang.blaze3d.platform.DisplayData;
/*      */ import com.mojang.blaze3d.platform.GlDebug;
/*      */ import com.mojang.blaze3d.platform.GlUtil;
/*      */ import com.mojang.blaze3d.platform.IconSet;
/*      */ import com.mojang.blaze3d.platform.Window;
/*      */ import com.mojang.blaze3d.platform.WindowEventHandler;
/*      */ import com.mojang.blaze3d.systems.RenderSystem;
/*      */ import com.mojang.blaze3d.systems.TimerQuery;
/*      */ import com.mojang.blaze3d.vertex.BufferBuilder;
/*      */ import com.mojang.blaze3d.vertex.BufferUploader;
/*      */ import com.mojang.blaze3d.vertex.DefaultVertexFormat;
/*      */ import com.mojang.blaze3d.vertex.PoseStack;
/*      */ import com.mojang.blaze3d.vertex.Tesselator;
/*      */ import com.mojang.blaze3d.vertex.VertexFormat;
/*      */ import com.mojang.datafixers.DataFixer;
/*      */ import com.mojang.logging.LogUtils;
/*      */ import com.mojang.realmsclient.client.RealmsClient;
/*      */ import com.mojang.realmsclient.gui.RealmsDataFetcher;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.io.UncheckedIOException;
/*      */ import java.lang.management.ManagementFactory;
/*      */ import java.net.Proxy;
/*      */ import java.net.SocketAddress;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.file.Path;
/*      */ import java.nio.file.Paths;
/*      */ import java.text.DecimalFormat;
/*      */ import java.text.DecimalFormatSymbols;
/*      */ import java.time.Duration;
/*      */ import java.time.Instant;
/*      */ import java.util.ArrayList;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.MissingResourceException;
/*      */ import java.util.Objects;
/*      */ import java.util.Optional;
/*      */ import java.util.Queue;
/*      */ import java.util.Set;
/*      */ import java.util.UUID;
/*      */ import java.util.concurrent.CompletableFuture;
/*      */ import java.util.concurrent.CompletionStage;
/*      */ import java.util.concurrent.Executor;
/*      */ import java.util.concurrent.atomic.AtomicReference;
/*      */ import java.util.function.Consumer;
/*      */ import java.util.function.Function;
/*      */ import java.util.function.LongSupplier;
/*      */ import java.util.function.Supplier;
/*      */ import java.util.stream.Stream;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.ChatFormatting;
/*      */ import net.minecraft.CrashReport;
/*      */ import net.minecraft.CrashReportCategory;
/*      */ import net.minecraft.FileUtil;
/*      */ import net.minecraft.Optionull;
/*      */ import net.minecraft.ReportedException;
/*      */ import net.minecraft.SharedConstants;
/*      */ import net.minecraft.SystemReport;
/*      */ import net.minecraft.Util;
/*      */ import net.minecraft.client.color.block.BlockColors;
/*      */ import net.minecraft.client.color.item.ItemColors;
/*      */ import net.minecraft.client.gui.Font;
/*      */ import net.minecraft.client.gui.Gui;
/*      */ import net.minecraft.client.gui.GuiGraphics;
/*      */ import net.minecraft.client.gui.GuiSpriteManager;
/*      */ import net.minecraft.client.gui.components.toasts.SystemToast;
/*      */ import net.minecraft.client.gui.components.toasts.ToastComponent;
/*      */ import net.minecraft.client.gui.components.toasts.TutorialToast;
/*      */ import net.minecraft.client.gui.font.FontManager;
/*      */ import net.minecraft.client.gui.screens.AccessibilityOnboardingScreen;
/*      */ import net.minecraft.client.gui.screens.BanNoticeScreens;
/*      */ import net.minecraft.client.gui.screens.ConfirmLinkScreen;
/*      */ import net.minecraft.client.gui.screens.DeathScreen;
/*      */ import net.minecraft.client.gui.screens.GenericDirtMessageScreen;
/*      */ import net.minecraft.client.gui.screens.InBedChatScreen;
/*      */ import net.minecraft.client.gui.screens.LevelLoadingScreen;
/*      */ import net.minecraft.client.gui.screens.LoadingOverlay;
/*      */ import net.minecraft.client.gui.screens.OutOfMemoryScreen;
/*      */ import net.minecraft.client.gui.screens.Overlay;
/*      */ import net.minecraft.client.gui.screens.PauseScreen;
/*      */ import net.minecraft.client.gui.screens.ProgressScreen;
/*      */ import net.minecraft.client.gui.screens.Screen;
/*      */ import net.minecraft.client.gui.screens.TitleScreen;
/*      */ import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
/*      */ import net.minecraft.client.gui.screens.recipebook.RecipeCollection;
/*      */ import net.minecraft.client.gui.screens.social.PlayerSocialManager;
/*      */ import net.minecraft.client.gui.screens.social.SocialInteractionsScreen;
/*      */ import net.minecraft.client.gui.screens.worldselection.WorldOpenFlows;
/*      */ import net.minecraft.client.main.GameConfig;
/*      */ import net.minecraft.client.main.SilentInitException;
/*      */ import net.minecraft.client.model.geom.EntityModelSet;
/*      */ import net.minecraft.client.multiplayer.ClientHandshakePacketListenerImpl;
/*      */ import net.minecraft.client.multiplayer.ClientLevel;
/*      */ import net.minecraft.client.multiplayer.ClientPacketListener;
/*      */ import net.minecraft.client.multiplayer.ProfileKeyPairManager;
/*      */ import net.minecraft.client.multiplayer.ServerData;
/*      */ import net.minecraft.client.multiplayer.chat.ChatListener;
/*      */ import net.minecraft.client.multiplayer.chat.report.ReportEnvironment;
/*      */ import net.minecraft.client.multiplayer.chat.report.ReportingContext;
/*      */ import net.minecraft.client.particle.ParticleEngine;
/*      */ import net.minecraft.client.player.LocalPlayer;
/*      */ import net.minecraft.client.profiling.ClientMetricsSamplersProvider;
/*      */ import net.minecraft.client.quickplay.QuickPlay;
/*      */ import net.minecraft.client.quickplay.QuickPlayLog;
/*      */ import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
/*      */ import net.minecraft.client.renderer.FogRenderer;
/*      */ import net.minecraft.client.renderer.GameRenderer;
/*      */ import net.minecraft.client.renderer.GpuWarnlistManager;
/*      */ import net.minecraft.client.renderer.LevelRenderer;
/*      */ import net.minecraft.client.renderer.RenderBuffers;
/*      */ import net.minecraft.client.renderer.VirtualScreen;
/*      */ import net.minecraft.client.renderer.block.BlockModelShaper;
/*      */ import net.minecraft.client.renderer.block.BlockRenderDispatcher;
/*      */ import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
/*      */ import net.minecraft.client.renderer.debug.DebugRenderer;
/*      */ import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
/*      */ import net.minecraft.client.renderer.entity.EntityRenderers;
/*      */ import net.minecraft.client.renderer.entity.ItemRenderer;
/*      */ import net.minecraft.client.renderer.texture.TextureAtlas;
/*      */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*      */ import net.minecraft.client.renderer.texture.TextureManager;
/*      */ import net.minecraft.client.resources.ClientPackSource;
/*      */ import net.minecraft.client.resources.FoliageColorReloadListener;
/*      */ import net.minecraft.client.resources.GrassColorReloadListener;
/*      */ import net.minecraft.client.resources.MobEffectTextureManager;
/*      */ import net.minecraft.client.resources.PaintingTextureManager;
/*      */ import net.minecraft.client.resources.SkinManager;
/*      */ import net.minecraft.client.resources.SplashManager;
/*      */ import net.minecraft.client.resources.language.I18n;
/*      */ import net.minecraft.client.resources.language.LanguageManager;
/*      */ import net.minecraft.client.resources.model.BakedModel;
/*      */ import net.minecraft.client.resources.model.ModelManager;
/*      */ import net.minecraft.client.resources.server.DownloadedPackSource;
/*      */ import net.minecraft.client.searchtree.FullTextSearchTree;
/*      */ import net.minecraft.client.searchtree.IdSearchTree;
/*      */ import net.minecraft.client.searchtree.RefreshableSearchTree;
/*      */ import net.minecraft.client.searchtree.SearchRegistry;
/*      */ import net.minecraft.client.searchtree.SearchTree;
/*      */ import net.minecraft.client.server.IntegratedServer;
/*      */ import net.minecraft.client.sounds.MusicManager;
/*      */ import net.minecraft.client.sounds.SoundManager;
/*      */ import net.minecraft.client.telemetry.ClientTelemetryManager;
/*      */ import net.minecraft.client.telemetry.TelemetryProperty;
/*      */ import net.minecraft.client.telemetry.events.GameLoadTimesEvent;
/*      */ import net.minecraft.client.tutorial.Tutorial;
/*      */ import net.minecraft.core.BlockPos;
/*      */ import net.minecraft.core.Direction;
/*      */ import net.minecraft.core.Holder;
/*      */ import net.minecraft.core.RegistryAccess;
/*      */ import net.minecraft.core.registries.BuiltInRegistries;
/*      */ import net.minecraft.nbt.CompoundTag;
/*      */ import net.minecraft.nbt.ListTag;
/*      */ import net.minecraft.nbt.StringTag;
/*      */ import net.minecraft.nbt.Tag;
/*      */ import net.minecraft.network.Connection;
/*      */ import net.minecraft.network.chat.ClickEvent;
/*      */ import net.minecraft.network.chat.CommonComponents;
/*      */ import net.minecraft.network.chat.Component;
/*      */ import net.minecraft.network.chat.MutableComponent;
/*      */ import net.minecraft.network.chat.Style;
/*      */ import net.minecraft.network.chat.contents.KeybindResolver;
/*      */ import net.minecraft.network.protocol.Packet;
/*      */ import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
/*      */ import net.minecraft.network.protocol.login.ClientLoginPacketListener;
/*      */ import net.minecraft.network.protocol.login.ServerboundHelloPacket;
/*      */ import net.minecraft.resources.ResourceLocation;
/*      */ import net.minecraft.server.Bootstrap;
/*      */ import net.minecraft.server.MinecraftServer;
/*      */ import net.minecraft.server.Services;
/*      */ import net.minecraft.server.WorldStem;
/*      */ import net.minecraft.server.level.progress.ChunkProgressListener;
/*      */ import net.minecraft.server.level.progress.ProcessorChunkProgressListener;
/*      */ import net.minecraft.server.level.progress.StoringChunkProgressListener;
/*      */ import net.minecraft.server.packs.PackResources;
/*      */ import net.minecraft.server.packs.PackType;
/*      */ import net.minecraft.server.packs.VanillaPackResources;
/*      */ import net.minecraft.server.packs.repository.FolderRepositorySource;
/*      */ import net.minecraft.server.packs.repository.PackRepository;
/*      */ import net.minecraft.server.packs.repository.PackSource;
/*      */ import net.minecraft.server.packs.repository.RepositorySource;
/*      */ import net.minecraft.server.packs.resources.PreparableReloadListener;
/*      */ import net.minecraft.server.packs.resources.ReloadInstance;
/*      */ import net.minecraft.server.packs.resources.ReloadableResourceManager;
/*      */ import net.minecraft.server.packs.resources.ResourceManager;
/*      */ import net.minecraft.server.players.GameProfileCache;
/*      */ import net.minecraft.sounds.Music;
/*      */ import net.minecraft.sounds.Musics;
/*      */ import net.minecraft.tags.BiomeTags;
/*      */ import net.minecraft.tags.TagKey;
/*      */ import net.minecraft.util.FileZipper;
/*      */ import net.minecraft.util.MemoryReserve;
/*      */ import net.minecraft.util.ModCheck;
/*      */ import net.minecraft.util.Mth;
/*      */ import net.minecraft.util.SignatureValidator;
/*      */ import net.minecraft.util.TimeUtil;
/*      */ import net.minecraft.util.Unit;
/*      */ import net.minecraft.util.datafix.DataFixers;
/*      */ import net.minecraft.util.profiling.ContinuousProfiler;
/*      */ import net.minecraft.util.profiling.EmptyProfileResults;
/*      */ import net.minecraft.util.profiling.InactiveProfiler;
/*      */ import net.minecraft.util.profiling.ProfileResults;
/*      */ import net.minecraft.util.profiling.ProfilerFiller;
/*      */ import net.minecraft.util.profiling.ResultField;
/*      */ import net.minecraft.util.profiling.SingleTickProfiler;
/*      */ import net.minecraft.util.profiling.metrics.profiling.ActiveMetricsRecorder;
/*      */ import net.minecraft.util.profiling.metrics.profiling.InactiveMetricsRecorder;
/*      */ import net.minecraft.util.profiling.metrics.profiling.MetricsRecorder;
/*      */ import net.minecraft.util.profiling.metrics.storage.MetricsPersister;
/*      */ import net.minecraft.world.InteractionHand;
/*      */ import net.minecraft.world.InteractionResult;
/*      */ import net.minecraft.world.TickRateManager;
/*      */ import net.minecraft.world.entity.Entity;
/*      */ import net.minecraft.world.entity.EntityType;
/*      */ import net.minecraft.world.entity.player.ChatVisiblity;
/*      */ import net.minecraft.world.entity.player.Inventory;
/*      */ import net.minecraft.world.entity.player.Player;
/*      */ import net.minecraft.world.item.BlockItem;
/*      */ import net.minecraft.world.item.CreativeModeTabs;
/*      */ import net.minecraft.world.item.Item;
/*      */ import net.minecraft.world.item.ItemStack;
/*      */ import net.minecraft.world.item.TooltipFlag;
/*      */ import net.minecraft.world.item.crafting.RecipeHolder;
/*      */ import net.minecraft.world.level.Level;
/*      */ import net.minecraft.world.level.LevelReader;
/*      */ import net.minecraft.world.level.biome.Biome;
/*      */ import net.minecraft.world.level.block.Block;
/*      */ import net.minecraft.world.level.block.entity.BlockEntity;
/*      */ import net.minecraft.world.level.block.entity.SkullBlockEntity;
/*      */ import net.minecraft.world.level.block.state.BlockState;
/*      */ import net.minecraft.world.level.storage.LevelStorageSource;
/*      */ import net.minecraft.world.level.validation.DirectoryValidator;
/*      */ import net.minecraft.world.phys.BlockHitResult;
/*      */ import net.minecraft.world.phys.EntityHitResult;
/*      */ import net.minecraft.world.phys.HitResult;
/*      */ import org.apache.commons.io.FileUtils;
/*      */ import org.joml.Matrix4f;
/*      */ import org.lwjgl.util.tinyfd.TinyFileDialogs;
/*      */ 
/*      */ public class Minecraft extends ReentrantBlockableEventLoop<Runnable> implements WindowEventHandler {
/*      */   static Minecraft instance;
/*  260 */   private static final Logger LOGGER = LogUtils.getLogger();
/*  261 */   public static final boolean ON_OSX = (Util.getPlatform() == Util.OS.OSX);
/*      */   
/*      */   private static final int MAX_TICKS_PER_UPDATE = 10;
/*  264 */   public static final ResourceLocation DEFAULT_FONT = new ResourceLocation("default");
/*  265 */   public static final ResourceLocation UNIFORM_FONT = new ResourceLocation("uniform");
/*  266 */   public static final ResourceLocation ALT_FONT = new ResourceLocation("alt");
/*  267 */   private static final ResourceLocation REGIONAL_COMPLIANCIES = new ResourceLocation("regional_compliancies.json");
/*  268 */   private static final CompletableFuture<Unit> RESOURCE_RELOAD_INITIAL_TASK = CompletableFuture.completedFuture(Unit.INSTANCE);
/*      */   
/*  270 */   private static final Component SOCIAL_INTERACTIONS_NOT_AVAILABLE = (Component)Component.translatable("multiplayer.socialInteractions.not_available");
/*      */   
/*      */   public static final String UPDATE_DRIVERS_ADVICE = "Please make sure you have up-to-date drivers (see aka.ms/mcdriver for instructions).";
/*      */   
/*      */   private final long canary;
/*      */   
/*      */   private final Path resourcePackDirectory;
/*      */   
/*      */   private final CompletableFuture<ProfileResult> profileFuture;
/*      */   
/*      */   private final TextureManager textureManager;
/*      */   
/*      */   private final DataFixer fixerUpper;
/*      */   
/*      */   private final VirtualScreen virtualScreen;
/*      */   
/*      */   private final Window window;
/*      */   
/*      */   private final Timer timer;
/*      */   
/*      */   private final RenderBuffers renderBuffers;
/*      */   
/*      */   public final LevelRenderer levelRenderer;
/*      */   
/*      */   private final EntityRenderDispatcher entityRenderDispatcher;
/*      */   
/*      */   private final ItemRenderer itemRenderer;
/*      */   
/*      */   public final ParticleEngine particleEngine;
/*      */   
/*      */   private final SearchRegistry searchRegistry;
/*      */   
/*      */   private final User user;
/*      */   
/*      */   public final Font font;
/*      */   
/*      */   public final Font fontFilterFishy;
/*      */   
/*      */   public final GameRenderer gameRenderer;
/*      */   
/*      */   public final DebugRenderer debugRenderer;
/*      */   
/*      */   private final AtomicReference<StoringChunkProgressListener> progressListener;
/*      */   
/*      */   public final Gui gui;
/*      */   
/*      */   public final Options options;
/*      */   
/*      */   private final HotbarManager hotbarManager;
/*      */   
/*      */   public final MouseHandler mouseHandler;
/*      */   
/*      */   public final KeyboardHandler keyboardHandler;
/*      */   
/*      */   private InputType lastInputType;
/*      */   
/*      */   public final File gameDirectory;
/*      */   
/*      */   private final String launchedVersion;
/*      */   private final String versionType;
/*      */   private final Proxy proxy;
/*      */   private final LevelStorageSource levelSource;
/*      */   private final boolean is64bit;
/*      */   private final boolean demo;
/*      */   private final boolean allowsMultiplayer;
/*      */   private final boolean allowsChat;
/*      */   private final ReloadableResourceManager resourceManager;
/*      */   private final VanillaPackResources vanillaPackResources;
/*      */   private final DownloadedPackSource downloadedPackSource;
/*      */   private final PackRepository resourcePackRepository;
/*      */   private final LanguageManager languageManager;
/*      */   private final BlockColors blockColors;
/*      */   private final ItemColors itemColors;
/*      */   private final RenderTarget mainRenderTarget;
/*      */   private final SoundManager soundManager;
/*      */   private final MusicManager musicManager;
/*      */   private final FontManager fontManager;
/*      */   private final SplashManager splashManager;
/*      */   private final GpuWarnlistManager gpuWarnlistManager;
/*      */   private final PeriodicNotificationManager regionalCompliancies;
/*      */   private final YggdrasilAuthenticationService authenticationService;
/*      */   private final MinecraftSessionService minecraftSessionService;
/*      */   private final UserApiService userApiService;
/*      */   private final CompletableFuture<UserApiService.UserProperties> userPropertiesFuture;
/*      */   private final SkinManager skinManager;
/*      */   private final ModelManager modelManager;
/*      */   private final BlockRenderDispatcher blockRenderer;
/*      */   private final PaintingTextureManager paintingTextures;
/*      */   private final MobEffectTextureManager mobEffectTextures;
/*      */   private final GuiSpriteManager guiSprites;
/*      */   private final ToastComponent toast;
/*      */   private final Tutorial tutorial;
/*      */   private final PlayerSocialManager playerSocialManager;
/*      */   private final EntityModelSet entityModels;
/*      */   private final BlockEntityRenderDispatcher blockEntityRenderDispatcher;
/*      */   private final ClientTelemetryManager telemetryManager;
/*      */   private final ProfileKeyPairManager profileKeyPairManager;
/*      */   private final RealmsDataFetcher realmsDataFetcher;
/*      */   private final QuickPlayLog quickPlayLog;
/*      */   @Nullable
/*      */   public MultiPlayerGameMode gameMode;
/*      */   @Nullable
/*      */   public ClientLevel level;
/*      */   @Nullable
/*      */   public LocalPlayer player;
/*      */   @Nullable
/*      */   private IntegratedServer singleplayerServer;
/*      */   @Nullable
/*      */   private Connection pendingConnection;
/*      */   private boolean isLocalServer;
/*      */   @Nullable
/*      */   public Entity cameraEntity;
/*      */   @Nullable
/*      */   public Entity crosshairPickEntity;
/*      */   @Nullable
/*      */   public HitResult hitResult;
/*      */   private int rightClickDelay;
/*      */   protected int missTime;
/*      */   private volatile boolean pause;
/*      */   private float pausePartialTick;
/*      */   private long lastNanoTime;
/*      */   private long lastTime;
/*      */   private int frames;
/*      */   public boolean noRender;
/*      */   @Nullable
/*      */   public Screen screen;
/*      */   @Nullable
/*      */   private Overlay overlay;
/*      */   private boolean clientLevelTeardownInProgress;
/*      */   private Thread gameThread;
/*      */   private volatile boolean running;
/*      */   @Nullable
/*      */   private Supplier<CrashReport> delayedCrash;
/*      */   private static int fps;
/*      */   public String fpsString;
/*      */   private long frameTimeNs;
/*      */   public boolean wireframe;
/*      */   public boolean sectionPath;
/*      */   public boolean sectionVisibility;
/*      */   public boolean smartCull;
/*      */   private boolean windowActive;
/*      */   private final Queue<Runnable> progressTasks;
/*      */   @Nullable
/*      */   private CompletableFuture<Void> pendingReload;
/*      */   @Nullable
/*      */   private TutorialToast socialInteractionsToast;
/*      */   private ProfilerFiller profiler;
/*      */   private int fpsPieRenderTicks;
/*      */   private final ContinuousProfiler fpsPieProfiler;
/*      */   @Nullable
/*      */   private ProfileResults fpsPieResults;
/*      */   private MetricsRecorder metricsRecorder;
/*      */   private final ResourceLoadStateTracker reloadStateTracker;
/*      */   private long savedCpuDuration;
/*      */   private double gpuUtilization;
/*      */   @Nullable
/*      */   private TimerQuery.FrameProfile currentFrameProfile;
/*      */   private final Realms32BitWarningStatus realms32BitWarningStatus;
/*      */   private final GameNarrator narrator;
/*      */   private final ChatListener chatListener;
/*      */   private ReportingContext reportingContext;
/*      */   private final CommandHistory commandHistory;
/*      */   private final DirectoryValidator directoryValidator;
/*      */   private boolean gameLoadFinished;
/*      */   private final long clientStartTimeMs;
/*      */   private long clientTickCount;
/*      */   private String debugPath;
/*      */   
/*      */   public Minecraft(GameConfig $$0) {
/*  439 */     super("Client");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     DisplayData $$6;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     this.canary = Double.doubleToLongBits(Math.PI);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     this.timer = new Timer(20.0F, 0L, this::getTickTargetMillis);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     this.searchRegistry = new SearchRegistry();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     this.progressListener = new AtomicReference<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     this.lastInputType = InputType.NONE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     this.regionalCompliancies = new PeriodicNotificationManager(REGIONAL_COMPLIANCIES, Minecraft::countryEqualsISO3);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     this.lastNanoTime = Util.getNanos();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     this.fpsString = "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     this.smartCull = true;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     this.progressTasks = Queues.newConcurrentLinkedQueue();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     this.profiler = (ProfilerFiller)InactiveProfiler.INSTANCE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     this.fpsPieProfiler = new ContinuousProfiler((LongSupplier)Util.timeSource, () -> this.fpsPieRenderTicks);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     this.metricsRecorder = InactiveMetricsRecorder.INSTANCE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     this.reloadStateTracker = new ResourceLoadStateTracker();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1624 */     this.debugPath = "root"; instance = this; this.clientStartTimeMs = System.currentTimeMillis(); this.gameDirectory = $$0.location.gameDirectory; File $$1 = $$0.location.assetDirectory; this.resourcePackDirectory = $$0.location.resourcePackDirectory.toPath(); this.launchedVersion = $$0.game.launchVersion; this.versionType = $$0.game.versionType; Path $$2 = this.gameDirectory.toPath(); this.directoryValidator = LevelStorageSource.parseValidator($$2.resolve("allowed_symlinks.txt")); ClientPackSource $$3 = new ClientPackSource($$0.location.getExternalAssetSource(), this.directoryValidator); this.downloadedPackSource = new DownloadedPackSource(this, $$2.resolve("downloads"), $$0.user); FolderRepositorySource folderRepositorySource = new FolderRepositorySource(this.resourcePackDirectory, PackType.CLIENT_RESOURCES, PackSource.DEFAULT, this.directoryValidator); this.resourcePackRepository = new PackRepository(new RepositorySource[] { (RepositorySource)$$3, this.downloadedPackSource.createRepositorySource(), (RepositorySource)folderRepositorySource }); this.vanillaPackResources = $$3.getVanillaPack(); this.proxy = $$0.user.proxy; this.authenticationService = new YggdrasilAuthenticationService(this.proxy); this.minecraftSessionService = this.authenticationService.createMinecraftSessionService(); this.user = $$0.user.user; this.profileFuture = CompletableFuture.supplyAsync(() -> this.minecraftSessionService.fetchProfile(this.user.getProfileId(), true), Util.nonCriticalIoPool()); this.userApiService = createUserApiService(this.authenticationService, $$0); this.userPropertiesFuture = CompletableFuture.supplyAsync(() -> { try { return this.userApiService.fetchProperties(); } catch (AuthenticationException $$0) { LOGGER.error("Failed to fetch user properties", (Throwable)$$0); return UserApiService.OFFLINE_PROPERTIES; }  }Util.nonCriticalIoPool()); LOGGER.info("Setting user: {}", this.user.getName()); LOGGER.debug("(Session ID is {})", this.user.getSessionId()); this.demo = $$0.game.demo; this.allowsMultiplayer = !$$0.game.disableMultiplayer; this.allowsChat = !$$0.game.disableChat; this.is64bit = checkIs64Bit(); this.singleplayerServer = null; KeybindResolver.setKeyResolver(KeyMapping::createNameSupplier); this.fixerUpper = DataFixers.getDataFixer(); this.toast = new ToastComponent(this); this.gameThread = Thread.currentThread(); this.options = new Options(this, this.gameDirectory); RenderSystem.setShaderGlintAlpha(((Double)this.options.glintStrength().get()).doubleValue()); this.running = true; this.tutorial = new Tutorial(this, this.options); this.hotbarManager = new HotbarManager($$2, this.fixerUpper); LOGGER.info("Backend library: {}", RenderSystem.getBackendDescription()); if (this.options.overrideHeight > 0 && this.options.overrideWidth > 0) { DisplayData $$5 = new DisplayData(this.options.overrideWidth, this.options.overrideHeight, $$0.display.fullscreenWidth, $$0.display.fullscreenHeight, $$0.display.isFullscreen); } else { $$6 = $$0.display; }  Util.timeSource = RenderSystem.initBackendSystem(); this.virtualScreen = new VirtualScreen(this); this.window = this.virtualScreen.newWindow($$6, this.options.fullscreenVideoModeString, createTitle()); setWindowActive(true); GameLoadTimesEvent.INSTANCE.endStep(TelemetryProperty.LOAD_TIME_PRE_WINDOW_MS); try { this.window.setIcon((PackResources)this.vanillaPackResources, SharedConstants.getCurrentVersion().isStable() ? IconSet.RELEASE : IconSet.SNAPSHOT); } catch (IOException $$7) { LOGGER.error("Couldn't set icon", $$7); }  this.window.setFramerateLimit(((Integer)this.options.framerateLimit().get()).intValue()); this.mouseHandler = new MouseHandler(this); this.mouseHandler.setup(this.window.getWindow()); this.keyboardHandler = new KeyboardHandler(this); this.keyboardHandler.setup(this.window.getWindow()); RenderSystem.initRenderer(this.options.glDebugVerbosity, false); this.mainRenderTarget = (RenderTarget)new MainTarget(this.window.getWidth(), this.window.getHeight()); this.mainRenderTarget.setClearColor(0.0F, 0.0F, 0.0F, 0.0F); this.mainRenderTarget.clear(ON_OSX); this.resourceManager = new ReloadableResourceManager(PackType.CLIENT_RESOURCES); this.resourcePackRepository.reload(); this.options.loadSelectedResourcePacks(this.resourcePackRepository); this.languageManager = new LanguageManager(this.options.languageCode); this.resourceManager.registerReloadListener((PreparableReloadListener)this.languageManager); this.textureManager = new TextureManager((ResourceManager)this.resourceManager); this.resourceManager.registerReloadListener((PreparableReloadListener)this.textureManager); this.skinManager = new SkinManager(this.textureManager, $$1.toPath().resolve("skins"), this.minecraftSessionService, (Executor)this); this.levelSource = new LevelStorageSource($$2.resolve("saves"), $$2.resolve("backups"), this.directoryValidator, this.fixerUpper); this.commandHistory = new CommandHistory($$2); this.soundManager = new SoundManager(this.options); this.resourceManager.registerReloadListener((PreparableReloadListener)this.soundManager); this.splashManager = new SplashManager(this.user); this.resourceManager.registerReloadListener((PreparableReloadListener)this.splashManager); this.musicManager = new MusicManager(this); this.fontManager = new FontManager(this.textureManager); this.font = this.fontManager.createFont(); this.fontFilterFishy = this.fontManager.createFontFilterFishy(); this.resourceManager.registerReloadListener((PreparableReloadListener)this.fontManager); selectMainFont(isEnforceUnicode()); this.resourceManager.registerReloadListener((PreparableReloadListener)new GrassColorReloadListener()); this.resourceManager.registerReloadListener((PreparableReloadListener)new FoliageColorReloadListener()); this.window.setErrorSection("Startup"); RenderSystem.setupDefaultState(0, 0, this.window.getWidth(), this.window.getHeight()); this.window.setErrorSection("Post startup"); this.blockColors = BlockColors.createDefault(); this.itemColors = ItemColors.createDefault(this.blockColors); this.modelManager = new ModelManager(this.textureManager, this.blockColors, ((Integer)this.options.mipmapLevels().get()).intValue()); this.resourceManager.registerReloadListener((PreparableReloadListener)this.modelManager); this.entityModels = new EntityModelSet(); this.resourceManager.registerReloadListener((PreparableReloadListener)this.entityModels); this.blockEntityRenderDispatcher = new BlockEntityRenderDispatcher(this.font, this.entityModels, this::getBlockRenderer, this::getItemRenderer, this::getEntityRenderDispatcher); this.resourceManager.registerReloadListener((PreparableReloadListener)this.blockEntityRenderDispatcher); BlockEntityWithoutLevelRenderer $$8 = new BlockEntityWithoutLevelRenderer(this.blockEntityRenderDispatcher, this.entityModels); this.resourceManager.registerReloadListener((PreparableReloadListener)$$8); this.itemRenderer = new ItemRenderer(this, this.textureManager, this.modelManager, this.itemColors, $$8); this.resourceManager.registerReloadListener((PreparableReloadListener)this.itemRenderer); try { int $$9 = Runtime.getRuntime().availableProcessors(); int $$10 = is64Bit() ? $$9 : Math.min($$9, 4); Tesselator.init(); this.renderBuffers = new RenderBuffers($$10); } catch (OutOfMemoryError $$11) { TinyFileDialogs.tinyfd_messageBox("Minecraft", "Oh no! The game was unable to allocate memory off-heap while trying to start. You may try to free some memory by closing other applications on your computer, check that your system meets the minimum requirements, and try again. If the problem persists, please visit: https://aka.ms/Minecraft-Support", "ok", "error", true); throw new SilentInitException("Unable to allocate render buffers", $$11); }  this.playerSocialManager = new PlayerSocialManager(this, this.userApiService); this.blockRenderer = new BlockRenderDispatcher(this.modelManager.getBlockModelShaper(), $$8, this.blockColors); this.resourceManager.registerReloadListener((PreparableReloadListener)this.blockRenderer); this.entityRenderDispatcher = new EntityRenderDispatcher(this, this.textureManager, this.itemRenderer, this.blockRenderer, this.font, this.options, this.entityModels); this.resourceManager.registerReloadListener((PreparableReloadListener)this.entityRenderDispatcher); this.gameRenderer = new GameRenderer(this, this.entityRenderDispatcher.getItemInHandRenderer(), (ResourceManager)this.resourceManager, this.renderBuffers); this.resourceManager.registerReloadListener(this.gameRenderer.createReloadListener()); this.levelRenderer = new LevelRenderer(this, this.entityRenderDispatcher, this.blockEntityRenderDispatcher, this.renderBuffers); this.resourceManager.registerReloadListener((PreparableReloadListener)this.levelRenderer); createSearchTrees(); this.resourceManager.registerReloadListener((PreparableReloadListener)this.searchRegistry); this.particleEngine = new ParticleEngine(this.level, this.textureManager); this.resourceManager.registerReloadListener((PreparableReloadListener)this.particleEngine); this.paintingTextures = new PaintingTextureManager(this.textureManager); this.resourceManager.registerReloadListener((PreparableReloadListener)this.paintingTextures); this.mobEffectTextures = new MobEffectTextureManager(this.textureManager); this.resourceManager.registerReloadListener((PreparableReloadListener)this.mobEffectTextures); this.guiSprites = new GuiSpriteManager(this.textureManager); this.resourceManager.registerReloadListener((PreparableReloadListener)this.guiSprites); this.gpuWarnlistManager = new GpuWarnlistManager(); this.resourceManager.registerReloadListener((PreparableReloadListener)this.gpuWarnlistManager); this.resourceManager.registerReloadListener((PreparableReloadListener)this.regionalCompliancies); this.gui = new Gui(this, this.itemRenderer); this.debugRenderer = new DebugRenderer(this); RealmsClient $$12 = RealmsClient.create(this); this.realmsDataFetcher = new RealmsDataFetcher($$12); RenderSystem.setErrorCallback(this::onFullscreenError); if (this.mainRenderTarget.width != this.window.getWidth() || this.mainRenderTarget.height != this.window.getHeight()) { StringBuilder $$13 = new StringBuilder("Recovering from unsupported resolution (" + this.window.getWidth() + "x" + this.window.getHeight() + ").\nPlease make sure you have up-to-date drivers (see aka.ms/mcdriver for instructions)."); if (GlDebug.isDebugEnabled()) $$13.append("\n\nReported GL debug messages:\n").append(String.join("\n", GlDebug.getLastOpenGlDebugMessages()));  this.window.setWindowed(this.mainRenderTarget.width, this.mainRenderTarget.height); TinyFileDialogs.tinyfd_messageBox("Minecraft", $$13.toString(), "ok", "error", false); } else if (((Boolean)this.options.fullscreen().get()).booleanValue() && !this.window.isFullscreen()) { this.window.toggleFullScreen(); this.options.fullscreen().set(Boolean.valueOf(this.window.isFullscreen())); }  this.window.updateVsync(((Boolean)this.options.enableVsync().get()).booleanValue()); this.window.updateRawMouseInput(((Boolean)this.options.rawMouseInput().get()).booleanValue()); this.window.setDefaultErrorCallback(); resizeDisplay(); this.gameRenderer.preloadUiShader(this.vanillaPackResources.asProvider()); this.telemetryManager = new ClientTelemetryManager(this, this.userApiService, this.user); this.profileKeyPairManager = ProfileKeyPairManager.create(this.userApiService, this.user, $$2); this.realms32BitWarningStatus = new Realms32BitWarningStatus(this); this.narrator = new GameNarrator(this); this.narrator.checkStatus((this.options.narrator().get() != NarratorStatus.OFF)); this.chatListener = new ChatListener(this); this.chatListener.setMessageDelay(((Double)this.options.chatDelay().get()).doubleValue()); this.reportingContext = ReportingContext.create(ReportEnvironment.local(), this.userApiService); LoadingOverlay.registerTextures(this); setScreen((Screen)new GenericDirtMessageScreen((Component)Component.translatable("gui.loadingMinecraft"))); List<PackResources> $$14 = this.resourcePackRepository.openAllSelected(); this.reloadStateTracker.startReload(ResourceLoadStateTracker.ReloadReason.INITIAL, $$14); ReloadInstance $$15 = this.resourceManager.createReload(Util.backgroundExecutor(), (Executor)this, RESOURCE_RELOAD_INITIAL_TASK, $$14); GameLoadTimesEvent.INSTANCE.beginStep(TelemetryProperty.LOAD_TIME_LOADING_OVERLAY_MS); GameLoadCookie $$16 = new GameLoadCookie($$12, $$0.quickPlay); setOverlay((Overlay)new LoadingOverlay(this, $$15, $$1 -> Util.ifElse($$1, (), ()), false)); this.quickPlayLog = QuickPlayLog.of($$0.quickPlay.path());
/*      */   } private void onResourceLoadFinished(@Nullable GameLoadCookie $$0) { if (!this.gameLoadFinished) { this.gameLoadFinished = true; onGameLoadFinished($$0); }  } private void onGameLoadFinished(@Nullable GameLoadCookie $$0) { Runnable $$1 = buildInitialScreens($$0); GameLoadTimesEvent.INSTANCE.endStep(TelemetryProperty.LOAD_TIME_LOADING_OVERLAY_MS); GameLoadTimesEvent.INSTANCE.endStep(TelemetryProperty.LOAD_TIME_TOTAL_TIME_MS); GameLoadTimesEvent.INSTANCE.send(this.telemetryManager.getOutsideSessionSender()); $$1.run(); } public boolean isGameLoadFinished() { return this.gameLoadFinished; } private Runnable buildInitialScreens(@Nullable GameLoadCookie $$0) { List<Function<Runnable, Screen>> $$1 = new ArrayList<>(); addInitialScreens($$1); Runnable $$2 = () -> { if ($$0 != null && $$0.quickPlayData().isEnabled()) { QuickPlay.connect(this, $$0.quickPlayData(), $$0.realmsClient()); } else { setScreen((Screen)new TitleScreen(true)); }  }; for (Function<Runnable, Screen> $$3 : (Iterable<Function<Runnable, Screen>>)Lists.reverse($$1)) { Screen $$4 = $$3.apply($$2); $$2 = (() -> setScreen($$0)); }  return $$2; } private void addInitialScreens(List<Function<Runnable, Screen>> $$0) { if (this.options.onboardAccessibility) $$0.add($$0 -> new AccessibilityOnboardingScreen(this.options, $$0));  BanDetails $$1 = multiplayerBan(); if ($$1 != null) $$0.add($$1 -> BanNoticeScreens.create((), $$0));  ProfileResult $$2 = this.profileFuture.join(); if ($$2 != null) { GameProfile $$3 = $$2.profile(); Set<ProfileActionType> $$4 = $$2.actions(); if ($$4.contains(ProfileActionType.FORCED_NAME_CHANGE)) $$0.add($$1 -> BanNoticeScreens.createNameBan($$0.getName(), $$1));  if ($$4.contains(ProfileActionType.USING_BANNED_SKIN)) $$0.add(BanNoticeScreens::createSkinBan);  }  } private static boolean countryEqualsISO3(Object $$0) { try { return Locale.getDefault().getISO3Country().equals($$0); } catch (MissingResourceException $$1) { return false; }  } public void updateTitle() { this.window.setTitle(createTitle()); } private String createTitle() { StringBuilder $$0 = new StringBuilder("Minecraft"); if (checkModStatus().shouldReportAsModified()) $$0.append("*");  $$0.append(" "); $$0.append(SharedConstants.getCurrentVersion().getName()); ClientPacketListener $$1 = getConnection(); if ($$1 != null && $$1.getConnection().isConnected()) { $$0.append(" - "); ServerData $$2 = getCurrentServer(); if (this.singleplayerServer != null && !this.singleplayerServer.isPublished()) { $$0.append(I18n.get("title.singleplayer", new Object[0])); } else if ($$2 != null && $$2.isRealm()) { $$0.append(I18n.get("title.multiplayer.realms", new Object[0])); } else if (this.singleplayerServer != null || ($$2 != null && $$2.isLan())) { $$0.append(I18n.get("title.multiplayer.lan", new Object[0])); } else { $$0.append(I18n.get("title.multiplayer.other", new Object[0])); }  }  return $$0.toString(); } private UserApiService createUserApiService(YggdrasilAuthenticationService $$0, GameConfig $$1) { return $$0.createUserApiService($$1.user.user.getAccessToken()); } public static ModCheck checkModStatus() { return ModCheck.identify("vanilla", ClientBrandRetriever::getClientModName, "Client", Minecraft.class); } private void rollbackResourcePacks(Throwable $$0, @Nullable GameLoadCookie $$1) { if (this.resourcePackRepository.getSelectedIds().size() > 1) { clearResourcePacksOnError($$0, (Component)null, $$1); } else { Util.throwAsRuntime($$0); }  } public void clearResourcePacksOnError(Throwable $$0, @Nullable Component $$1, @Nullable GameLoadCookie $$2) { LOGGER.info("Caught error loading resourcepacks, removing all selected resourcepacks", $$0); this.reloadStateTracker.startRecovery($$0); this.downloadedPackSource.onRecovery(); this.resourcePackRepository.setSelected(Collections.emptyList()); this.options.resourcePacks.clear(); this.options.incompatibleResourcePacks.clear(); this.options.save(); reloadResourcePacks(true, $$2).thenRun(() -> addResourcePackLoadFailToast($$0)); } private void abortResourcePackRecovery() { setOverlay((Overlay)null); if (this.level != null) { this.level.disconnect(); disconnect(); }  setScreen((Screen)new TitleScreen()); addResourcePackLoadFailToast((Component)null); } private void addResourcePackLoadFailToast(@Nullable Component $$0) { ToastComponent $$1 = getToasts(); SystemToast.addOrUpdate($$1, SystemToast.SystemToastId.PACK_LOAD_FAILURE, (Component)Component.translatable("resourcePack.load_fail"), $$0); } public void run() { this.gameThread = Thread.currentThread(); if (Runtime.getRuntime().availableProcessors() > 4) this.gameThread.setPriority(10);  try { boolean $$0 = false; while (this.running) { handleDelayedCrash(); try { SingleTickProfiler $$1 = SingleTickProfiler.createTickProfiler("Renderer"); boolean $$2 = getDebugOverlay().showProfilerChart(); this.profiler = constructProfiler($$2, $$1); this.profiler.startTick(); this.metricsRecorder.startTick(); runTick(!$$0); this.metricsRecorder.endTick(); this.profiler.endTick(); finishProfilers($$2, $$1); } catch (OutOfMemoryError $$3) { if ($$0) throw $$3;  emergencySave(); setScreen((Screen)new OutOfMemoryScreen()); System.gc(); LOGGER.error(LogUtils.FATAL_MARKER, "Out of memory", $$3); $$0 = true; }  }  } catch (ReportedException $$4) { LOGGER.error(LogUtils.FATAL_MARKER, "Reported exception thrown!", (Throwable)$$4); emergencySaveAndCrash($$4.getReport()); } catch (Throwable $$5) { LOGGER.error(LogUtils.FATAL_MARKER, "Unreported exception thrown!", $$5); emergencySaveAndCrash(new CrashReport("Unexpected error", $$5)); }  } void selectMainFont(boolean $$0) { this.fontManager.setRenames($$0 ? (Map)ImmutableMap.of(DEFAULT_FONT, UNIFORM_FONT) : (Map)ImmutableMap.of()); } private void createSearchTrees() { this.searchRegistry.register(SearchRegistry.CREATIVE_NAMES, $$0 -> new FullTextSearchTree((), (), $$0)); this.searchRegistry.register(SearchRegistry.CREATIVE_TAGS, $$0 -> new IdSearchTree((), $$0)); this.searchRegistry.register(SearchRegistry.RECIPE_COLLECTIONS, $$0 -> new FullTextSearchTree((), (), $$0)); CreativeModeTabs.searchTab().setSearchTreeBuilder($$0 -> { populateSearchTree(SearchRegistry.CREATIVE_NAMES, $$0); populateSearchTree(SearchRegistry.CREATIVE_TAGS, $$0); }); } private void onFullscreenError(int $$0, long $$1) { this.options.enableVsync().set(Boolean.valueOf(false)); this.options.save(); } private static boolean checkIs64Bit() { String[] $$0 = { "sun.arch.data.model", "com.ibm.vm.bitmode", "os.arch" }; for (String $$1 : $$0) { String $$2 = System.getProperty($$1); if ($$2 != null && $$2.contains("64")) return true;  }  return false; } public RenderTarget getMainRenderTarget() { return this.mainRenderTarget; } public String getLaunchedVersion() { return this.launchedVersion; } public String getVersionType() { return this.versionType; } public void delayCrash(CrashReport $$0) { this.delayedCrash = (() -> fillReport($$0)); } public void delayCrashRaw(CrashReport $$0) { this.delayedCrash = (() -> $$0); }
/*      */   private void handleDelayedCrash() { if (this.delayedCrash != null) crash(this, this.gameDirectory, this.delayedCrash.get());  }
/* 1627 */   public void debugFpsMeterKeyPress(int $$0) { if (this.fpsPieResults == null) {
/*      */       return;
/*      */     }
/* 1630 */     List<ResultField> $$1 = this.fpsPieResults.getTimes(this.debugPath);
/* 1631 */     if ($$1.isEmpty()) {
/*      */       return;
/*      */     }
/*      */     
/* 1635 */     ResultField $$2 = $$1.remove(0);
/* 1636 */     if ($$0 == 0)
/* 1637 */     { if (!$$2.name.isEmpty()) {
/* 1638 */         int $$3 = this.debugPath.lastIndexOf('\036');
/* 1639 */         if ($$3 >= 0) {
/* 1640 */           this.debugPath = this.debugPath.substring(0, $$3);
/*      */         }
/*      */       }  }
/*      */     else
/* 1644 */     { $$0--;
/* 1645 */       if ($$0 < $$1.size() && !"unspecified".equals(((ResultField)$$1.get($$0)).name))
/* 1646 */       { if (!this.debugPath.isEmpty()) {
/* 1647 */           this.debugPath += "\036";
/*      */         }
/* 1649 */         this.debugPath += this.debugPath; }  }  }
/*      */   public void emergencySaveAndCrash(CrashReport $$0) { CrashReport $$1 = fillReport($$0); emergencySave(); crash(this, this.gameDirectory, $$1); }
/*      */   public static void crash(@Nullable Minecraft $$0, File $$1, CrashReport $$2) { File $$3 = new File($$1, "crash-reports"); File $$4 = new File($$3, "crash-" + Util.getFilenameFormattedDateTime() + "-client.txt"); Bootstrap.realStdoutPrintln($$2.getFriendlyReport()); if ($$0 != null) $$0.soundManager.emergencyShutdown();  if ($$2.getSaveFile() != null) { Bootstrap.realStdoutPrintln("#@!@# Game crashed! Crash report saved to: #@!@# " + $$2.getSaveFile()); System.exit(-1); } else if ($$2.saveToFile($$4)) { Bootstrap.realStdoutPrintln("#@!@# Game crashed! Crash report saved to: #@!@# " + $$4.getAbsolutePath()); System.exit(-1); } else { Bootstrap.realStdoutPrintln("#@?@# Game crashed! Crash report could not be saved. #@?@#"); System.exit(-2); }  }
/*      */   public boolean isEnforceUnicode() { return ((Boolean)this.options.forceUnicodeFont().get()).booleanValue(); }
/*      */   public CompletableFuture<Void> reloadResourcePacks() { return reloadResourcePacks(false, (GameLoadCookie)null); }
/*      */   private CompletableFuture<Void> reloadResourcePacks(boolean $$0, @Nullable GameLoadCookie $$1) { if (this.pendingReload != null) return this.pendingReload;  CompletableFuture<Void> $$2 = new CompletableFuture<>(); if (!$$0 && this.overlay instanceof LoadingOverlay) { this.pendingReload = $$2; return $$2; }  this.resourcePackRepository.reload(); List<PackResources> $$3 = this.resourcePackRepository.openAllSelected(); if (!$$0) this.reloadStateTracker.startReload(ResourceLoadStateTracker.ReloadReason.MANUAL, $$3);  setOverlay((Overlay)new LoadingOverlay(this, this.resourceManager.createReload(Util.backgroundExecutor(), (Executor)this, RESOURCE_RELOAD_INITIAL_TASK, $$3), $$3 -> Util.ifElse($$3, (), ()), !$$0)); return $$2; }
/* 1655 */   private void selfTest() { boolean $$0 = false; BlockModelShaper $$1 = getBlockRenderer().getBlockModelShaper(); BakedModel $$2 = $$1.getModelManager().getMissingModel(); for (Block $$3 : BuiltInRegistries.BLOCK) { for (UnmodifiableIterator<BlockState> unmodifiableIterator = $$3.getStateDefinition().getPossibleStates().iterator(); unmodifiableIterator.hasNext(); ) { BlockState $$4 = unmodifiableIterator.next(); if ($$4.getRenderShape() == RenderShape.MODEL) { BakedModel $$5 = $$1.getBlockModel($$4); if ($$5 == $$2) { LOGGER.debug("Missing model for: {}", $$4); $$0 = true; }  }  }  }  TextureAtlasSprite $$6 = $$2.getParticleIcon(); for (Block $$7 : BuiltInRegistries.BLOCK) { for (UnmodifiableIterator<BlockState> unmodifiableIterator = $$7.getStateDefinition().getPossibleStates().iterator(); unmodifiableIterator.hasNext(); ) { BlockState $$8 = unmodifiableIterator.next(); TextureAtlasSprite $$9 = $$1.getParticleIcon($$8); if (!$$8.isAir() && $$9 == $$6) LOGGER.debug("Missing particle icon for: {}", $$8);  }  }  for (Item $$10 : BuiltInRegistries.ITEM) { ItemStack $$11 = $$10.getDefaultInstance(); String $$12 = $$11.getDescriptionId(); String $$13 = Component.translatable($$12).getString(); if ($$13.toLowerCase(Locale.ROOT).equals($$10.getDescriptionId())) LOGGER.debug("Missing translation for: {} {} {}", new Object[] { $$11, $$12, $$10 });  }  $$0 |= MenuScreens.selfTest(); $$0 |= EntityRenderers.validateRegistrations(); if ($$0) throw new IllegalStateException("Your game data is foobar, fix the errors above!");  } public LevelStorageSource getLevelSource() { return this.levelSource; } private void openChatScreen(String $$0) { ChatStatus $$1 = getChatStatus(); if (!$$1.isChatAllowed(isLocalServer())) { if (this.gui.isShowingChatDisabledByPlayer()) { this.gui.setChatDisabledByPlayerShown(false); setScreen((Screen)new ConfirmLinkScreen($$0 -> { if ($$0) Util.getPlatform().openUri("https://aka.ms/JavaAccountSettings");  setScreen((Screen)null); }ChatStatus.INFO_DISABLED_BY_PROFILE, "https://aka.ms/JavaAccountSettings", true)); } else { Component $$2 = $$1.getMessage(); this.gui.setOverlayMessage($$2, false); this.narrator.sayNow($$2); this.gui.setChatDisabledByPlayerShown(($$1 == ChatStatus.DISABLED_BY_PROFILE)); }  } else { setScreen((Screen)new ChatScreen($$0)); }  } public void setScreen(@Nullable Screen $$0) { TitleScreen titleScreen; DeathScreen deathScreen; if (SharedConstants.IS_RUNNING_IN_IDE && Thread.currentThread() != this.gameThread) LOGGER.error("setScreen called from non-game thread");  if (this.screen != null) this.screen.removed();  if ($$0 == null && this.clientLevelTeardownInProgress) throw new IllegalStateException("Trying to return to in-game GUI during disconnection");  if ($$0 == null && this.level == null) { titleScreen = new TitleScreen(); } else if (titleScreen == null && this.player.isDeadOrDying()) { if (this.player.shouldShowDeathScreen()) { deathScreen = new DeathScreen(null, this.level.getLevelData().isHardcore()); } else { this.player.respawn(); }  }  this.screen = (Screen)deathScreen; if (this.screen != null) this.screen.added();  BufferUploader.reset(); if (deathScreen != null) { this.mouseHandler.releaseMouse(); KeyMapping.releaseAll(); deathScreen.init(this, this.window.getGuiScaledWidth(), this.window.getGuiScaledHeight()); this.noRender = false; } else { this.soundManager.resume(); this.mouseHandler.grabMouse(); }  updateTitle(); } public void setOverlay(@Nullable Overlay $$0) { this.overlay = $$0; } public void destroy() { try { LOGGER.info("Stopping!"); try { this.narrator.destroy(); } catch (Throwable throwable) {} try { if (this.level != null) this.level.disconnect();  disconnect(); } catch (Throwable throwable) {} if (this.screen != null) this.screen.removed();  close(); } finally { Util.timeSource = System::nanoTime; if (this.delayedCrash == null) System.exit(0);  }  } public void close() { if (this.currentFrameProfile != null) this.currentFrameProfile.cancel();  try { this.telemetryManager.close(); this.regionalCompliancies.close(); this.modelManager.close(); this.fontManager.close(); this.gameRenderer.close(); this.levelRenderer.close(); this.soundManager.destroy(); this.particleEngine.close(); this.mobEffectTextures.close(); this.paintingTextures.close(); this.guiSprites.close(); this.textureManager.close(); this.resourceManager.close(); Util.shutdownExecutors(); } catch (Throwable $$0) { LOGGER.error("Shutdown failure!", $$0); throw $$0; } finally { this.virtualScreen.close(); this.window.close(); }  } private void renderFpsMeter(GuiGraphics $$0, ProfileResults $$1) { List<ResultField> $$2 = $$1.getTimes(this.debugPath);
/* 1656 */     ResultField $$3 = $$2.remove(0);
/*      */     
/* 1658 */     RenderSystem.clear(256, ON_OSX);
/* 1659 */     RenderSystem.setShader(GameRenderer::getPositionColorShader);
/*      */     
/* 1661 */     Matrix4f $$4 = (new Matrix4f()).setOrtho(0.0F, this.window.getWidth(), this.window.getHeight(), 0.0F, 1000.0F, 3000.0F);
/* 1662 */     RenderSystem.setProjectionMatrix($$4, VertexSorting.ORTHOGRAPHIC_Z);
/*      */     
/* 1664 */     PoseStack $$5 = RenderSystem.getModelViewStack();
/* 1665 */     $$5.pushPose();
/* 1666 */     $$5.setIdentity();
/* 1667 */     $$5.translate(0.0F, 0.0F, -2000.0F);
/* 1668 */     RenderSystem.applyModelViewMatrix();
/*      */     
/* 1670 */     RenderSystem.lineWidth(1.0F);
/* 1671 */     Tesselator $$6 = Tesselator.getInstance();
/* 1672 */     BufferBuilder $$7 = $$6.getBuilder();
/*      */     
/* 1674 */     int $$8 = 160;
/* 1675 */     int $$9 = this.window.getWidth() - 160 - 10;
/* 1676 */     int $$10 = this.window.getHeight() - 320;
/* 1677 */     RenderSystem.enableBlend();
/* 1678 */     $$7.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
/* 1679 */     $$7.vertex(($$9 - 176.0F), ($$10 - 96.0F - 16.0F), 0.0D).color(200, 0, 0, 0).endVertex();
/* 1680 */     $$7.vertex(($$9 - 176.0F), ($$10 + 320), 0.0D).color(200, 0, 0, 0).endVertex();
/* 1681 */     $$7.vertex(($$9 + 176.0F), ($$10 + 320), 0.0D).color(200, 0, 0, 0).endVertex();
/* 1682 */     $$7.vertex(($$9 + 176.0F), ($$10 - 96.0F - 16.0F), 0.0D).color(200, 0, 0, 0).endVertex();
/* 1683 */     $$6.end();
/* 1684 */     RenderSystem.disableBlend();
/*      */     
/* 1686 */     double $$11 = 0.0D;
/* 1687 */     for (ResultField $$12 : $$2) {
/* 1688 */       int $$13 = Mth.floor($$12.percentage / 4.0D) + 1;
/*      */       
/* 1690 */       $$7.begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION_COLOR);
/* 1691 */       int $$14 = $$12.getColor();
/* 1692 */       int $$15 = $$14 >> 16 & 0xFF;
/* 1693 */       int $$16 = $$14 >> 8 & 0xFF;
/* 1694 */       int $$17 = $$14 & 0xFF;
/* 1695 */       $$7.vertex($$9, $$10, 0.0D).color($$15, $$16, $$17, 255).endVertex();
/* 1696 */       for (int $$18 = $$13; $$18 >= 0; $$18--) {
/* 1697 */         float $$19 = (float)(($$11 + $$12.percentage * $$18 / $$13) * 6.2831854820251465D / 100.0D);
/* 1698 */         float $$20 = Mth.sin($$19) * 160.0F;
/* 1699 */         float $$21 = Mth.cos($$19) * 160.0F * 0.5F;
/* 1700 */         $$7.vertex(($$9 + $$20), ($$10 - $$21), 0.0D).color($$15, $$16, $$17, 255).endVertex();
/*      */       } 
/* 1702 */       $$6.end();
/*      */       
/* 1704 */       $$7.begin(VertexFormat.Mode.TRIANGLE_STRIP, DefaultVertexFormat.POSITION_COLOR);
/* 1705 */       for (int $$22 = $$13; $$22 >= 0; $$22--) {
/* 1706 */         float $$23 = (float)(($$11 + $$12.percentage * $$22 / $$13) * 6.2831854820251465D / 100.0D);
/* 1707 */         float $$24 = Mth.sin($$23) * 160.0F;
/* 1708 */         float $$25 = Mth.cos($$23) * 160.0F * 0.5F;
/* 1709 */         if ($$25 <= 0.0F) {
/*      */ 
/*      */           
/* 1712 */           $$7.vertex(($$9 + $$24), ($$10 - $$25), 0.0D).color($$15 >> 1, $$16 >> 1, $$17 >> 1, 255).endVertex();
/* 1713 */           $$7.vertex(($$9 + $$24), ($$10 - $$25 + 10.0F), 0.0D).color($$15 >> 1, $$16 >> 1, $$17 >> 1, 255).endVertex();
/*      */         } 
/* 1715 */       }  $$6.end();
/*      */       
/* 1717 */       $$11 += $$12.percentage;
/*      */     } 
/* 1719 */     DecimalFormat $$26 = new DecimalFormat("##0.00");
/* 1720 */     $$26.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
/*      */ 
/*      */     
/* 1723 */     String $$27 = ProfileResults.demanglePath($$3.name);
/* 1724 */     String $$28 = "";
/* 1725 */     if (!"unspecified".equals($$27)) {
/* 1726 */       $$28 = $$28 + "[0] ";
/*      */     }
/* 1728 */     if ($$27.isEmpty()) {
/* 1729 */       $$28 = $$28 + "ROOT ";
/*      */     } else {
/* 1731 */       $$28 = $$28 + $$28 + " ";
/*      */     } 
/* 1733 */     int $$29 = 16777215;
/* 1734 */     $$0.drawString(this.font, $$28, $$9 - 160, $$10 - 80 - 16, 16777215);
/*      */     
/* 1736 */     $$28 = $$26.format($$3.globalPercentage) + "%";
/* 1737 */     $$0.drawString(this.font, $$28, $$9 + 160 - this.font.width($$28), $$10 - 80 - 16, 16777215);
/*      */ 
/*      */     
/* 1740 */     for (int $$30 = 0; $$30 < $$2.size(); $$30++) {
/* 1741 */       ResultField $$31 = $$2.get($$30);
/* 1742 */       StringBuilder $$32 = new StringBuilder();
/* 1743 */       if ("unspecified".equals($$31.name)) {
/* 1744 */         $$32.append("[?] ");
/*      */       } else {
/* 1746 */         $$32.append("[").append($$30 + 1).append("] ");
/*      */       } 
/*      */       
/* 1749 */       String $$33 = $$32.append($$31.name).toString();
/* 1750 */       $$0.drawString(this.font, $$33, $$9 - 160, $$10 + 80 + $$30 * 8 + 20, $$31.getColor());
/* 1751 */       $$33 = $$26.format($$31.percentage) + "%";
/* 1752 */       $$0.drawString(this.font, $$33, $$9 + 160 - 50 - this.font.width($$33), $$10 + 80 + $$30 * 8 + 20, $$31.getColor());
/* 1753 */       $$33 = $$26.format($$31.globalPercentage) + "%";
/* 1754 */       $$0.drawString(this.font, $$33, $$9 + 160 - this.font.width($$33), $$10 + 80 + $$30 * 8 + 20, $$31.getColor());
/*      */     } 
/*      */     
/* 1757 */     $$5.popPose();
/* 1758 */     RenderSystem.applyModelViewMatrix(); }
/*      */   private void runTick(boolean $$0) { boolean $$8; this.window.setErrorSection("Pre render"); long $$1 = Util.getNanos(); if (this.window.shouldClose()) stop();  if (this.pendingReload != null && !(this.overlay instanceof LoadingOverlay)) { CompletableFuture<Void> $$2 = this.pendingReload; this.pendingReload = null; reloadResourcePacks().thenRun(() -> $$0.complete(null)); }  Runnable $$3; while (($$3 = this.progressTasks.poll()) != null) $$3.run();  if ($$0) { int $$4 = this.timer.advanceTime(Util.getMillis()); this.profiler.push("scheduledExecutables"); runAllTasks(); this.profiler.pop(); this.profiler.push("tick"); for (int $$5 = 0; $$5 < Math.min(10, $$4); $$5++) { this.profiler.incrementCounter("clientTick"); tick(); }  this.profiler.pop(); }  this.mouseHandler.turnPlayer(); this.window.setErrorSection("Render"); this.profiler.push("sound"); this.soundManager.updateSource(this.gameRenderer.getMainCamera()); this.profiler.pop(); this.profiler.push("render"); long $$6 = Util.getNanos(); if (getDebugOverlay().showDebugScreen() || this.metricsRecorder.isRecording()) { boolean $$7 = (this.currentFrameProfile == null || this.currentFrameProfile.isDone()); if ($$7) TimerQuery.getInstance().ifPresent(TimerQuery::beginProfile);  } else { $$8 = false; this.gpuUtilization = 0.0D; }  RenderSystem.clear(16640, ON_OSX); this.mainRenderTarget.bindWrite(true); FogRenderer.setupNoFog(); this.profiler.push("display"); RenderSystem.enableCull(); this.profiler.pop(); if (!this.noRender) { this.profiler.popPush("gameRenderer"); this.gameRenderer.render(this.pause ? this.pausePartialTick : this.timer.partialTick, $$1, $$0); this.profiler.pop(); }  if (this.fpsPieResults != null) { this.profiler.push("fpsPie"); GuiGraphics $$9 = new GuiGraphics(this, this.renderBuffers.bufferSource()); renderFpsMeter($$9, this.fpsPieResults); $$9.flush(); this.profiler.pop(); }  this.profiler.push("blit"); this.mainRenderTarget.unbindWrite(); this.mainRenderTarget.blitToScreen(this.window.getWidth(), this.window.getHeight()); this.frameTimeNs = Util.getNanos() - $$6; if ($$8) TimerQuery.getInstance().ifPresent($$0 -> this.currentFrameProfile = $$0.endProfile());  this.profiler.popPush("updateDisplay"); this.window.updateDisplay(); int $$10 = getFramerateLimit(); if ($$10 < 260) RenderSystem.limitDisplayFPS($$10);  this.profiler.popPush("yield"); Thread.yield(); this.profiler.pop(); this.window.setErrorSection("Post render"); this.frames++; boolean $$11 = (hasSingleplayerServer() && ((this.screen != null && this.screen.isPauseScreen()) || (this.overlay != null && this.overlay.isPauseScreen())) && !this.singleplayerServer.isPublished()); if (this.pause != $$11) { if ($$11) { this.pausePartialTick = this.timer.partialTick; } else { this.timer.partialTick = this.pausePartialTick; }  this.pause = $$11; }  long $$12 = Util.getNanos(); long $$13 = $$12 - this.lastNanoTime; if ($$8) this.savedCpuDuration = $$13;  getDebugOverlay().logFrameDuration($$13); this.lastNanoTime = $$12; this.profiler.push("fpsUpdate"); if (this.currentFrameProfile != null && this.currentFrameProfile.isDone()) this.gpuUtilization = this.currentFrameProfile.get() * 100.0D / this.savedCpuDuration;  while (Util.getMillis() >= this.lastTime + 1000L) { String $$15; if (this.gpuUtilization > 0.0D) { String $$14 = " GPU: " + ((this.gpuUtilization > 100.0D) ? ("" + ChatFormatting.RED + "100%") : ("" + Math.round(this.gpuUtilization) + "%")); } else { $$15 = ""; }  fps = this.frames; this.fpsString = String.format(Locale.ROOT, "%d fps T: %s%s%s%s B: %d%s", new Object[] { Integer.valueOf(fps), ($$10 == 260) ? "inf" : Integer.valueOf($$10), ((Boolean)this.options.enableVsync().get()).booleanValue() ? " vsync" : "", this.options.graphicsMode().get(), (this.options.cloudStatus().get() == CloudStatus.OFF) ? "" : ((this.options.cloudStatus().get() == CloudStatus.FAST) ? " fast-clouds" : " fancy-clouds"), this.options.biomeBlendRadius().get(), $$15 }); this.lastTime += 1000L; this.frames = 0; }  this.profiler.pop(); }
/*      */   private ProfilerFiller constructProfiler(boolean $$0, @Nullable SingleTickProfiler $$1) { InactiveProfiler inactiveProfiler; ProfilerFiller profilerFiller; if (!$$0) { this.fpsPieProfiler.disable(); if (!this.metricsRecorder.isRecording() && $$1 == null) return (ProfilerFiller)InactiveProfiler.INSTANCE;  }  if ($$0) { if (!this.fpsPieProfiler.isEnabled()) { this.fpsPieRenderTicks = 0; this.fpsPieProfiler.enable(); }  this.fpsPieRenderTicks++; profilerFiller = this.fpsPieProfiler.getFiller(); } else { inactiveProfiler = InactiveProfiler.INSTANCE; }  if (this.metricsRecorder.isRecording()) profilerFiller = ProfilerFiller.tee((ProfilerFiller)inactiveProfiler, this.metricsRecorder.getProfiler());  return SingleTickProfiler.decorateFiller(profilerFiller, $$1); }
/*      */   private void finishProfilers(boolean $$0, @Nullable SingleTickProfiler $$1) { if ($$1 != null) $$1.endTick();  if ($$0) { this.fpsPieResults = this.fpsPieProfiler.getResults(); } else { this.fpsPieResults = null; }  this.profiler = this.fpsPieProfiler.getFiller(); }
/* 1762 */   public void resizeDisplay() { int $$0 = this.window.calculateScale(((Integer)this.options.guiScale().get()).intValue(), isEnforceUnicode()); this.window.setGuiScale($$0); if (this.screen != null) this.screen.resize(this, this.window.getGuiScaledWidth(), this.window.getGuiScaledHeight());  RenderTarget $$1 = getMainRenderTarget(); $$1.resize(this.window.getWidth(), this.window.getHeight(), ON_OSX); this.gameRenderer.resize(this.window.getWidth(), this.window.getHeight()); this.mouseHandler.setIgnoreFirstMove(); } public void cursorEntered() { this.mouseHandler.cursorEntered(); } public int getFps() { return fps; } public long getFrameTimeNs() { return this.frameTimeNs; } private int getFramerateLimit() { if (this.level == null && (this.screen != null || this.overlay != null)) return 60;  return this.window.getFramerateLimit(); } private void emergencySave() { try { MemoryReserve.release(); this.levelRenderer.clear(); } catch (Throwable throwable) {} try { System.gc(); if (this.isLocalServer && this.singleplayerServer != null) this.singleplayerServer.halt(true);  disconnect((Screen)new GenericDirtMessageScreen((Component)Component.translatable("menu.savingLevel"))); } catch (Throwable throwable) {} System.gc(); } public boolean debugClientMetricsStart(Consumer<Component> $$0) { Consumer<Path> $$8; if (this.metricsRecorder.isRecording()) { debugClientMetricsStop(); return false; }  Consumer<ProfileResults> $$1 = $$1 -> { if ($$1 == EmptyProfileResults.EMPTY) return;  int $$2 = $$1.getTickDuration(); double $$3 = $$1.getNanoDuration() / TimeUtil.NANOSECONDS_PER_SECOND; execute(()); }; Consumer<Path> $$2 = $$1 -> { MutableComponent mutableComponent = Component.literal($$1.toString()).withStyle(ChatFormatting.UNDERLINE).withStyle(()); execute(()); }; SystemReport $$3 = fillSystemReport(new SystemReport(), this, this.languageManager, this.launchedVersion, this.options); Consumer<List<Path>> $$4 = $$2 -> { Path $$3 = archiveProfilingReport($$0, $$2); $$1.accept($$3); }; if (this.singleplayerServer == null) { Consumer<Path> $$5 = $$1 -> $$0.accept(ImmutableList.of($$1)); } else { this.singleplayerServer.fillSystemReport($$3); CompletableFuture<Path> $$6 = new CompletableFuture<>(); CompletableFuture<Path> $$7 = new CompletableFuture<>(); CompletableFuture.allOf((CompletableFuture<?>[])new CompletableFuture[] { $$6, $$7 }).thenRunAsync(() -> $$0.accept(ImmutableList.of($$1.join(), $$2.join())), Util.ioPool()); Objects.requireNonNull($$7); this.singleplayerServer.startRecordingMetrics($$0 -> {  }$$7::complete); Objects.requireNonNull($$6); $$8 = $$6::complete; }  this.metricsRecorder = (MetricsRecorder)ActiveMetricsRecorder.createStarted((MetricsSamplerProvider)new ClientMetricsSamplersProvider((LongSupplier)Util.timeSource, this.levelRenderer), (LongSupplier)Util.timeSource, Util.ioPool(), new MetricsPersister("client"), $$1 -> { this.metricsRecorder = InactiveMetricsRecorder.INSTANCE; $$0.accept($$1); }$$8); return true; } private void debugClientMetricsStop() { this.metricsRecorder.end(); if (this.singleplayerServer != null) this.singleplayerServer.finishRecordingMetrics();  } private void debugClientMetricsCancel() { this.metricsRecorder.cancel(); if (this.singleplayerServer != null) this.singleplayerServer.cancelRecordingMetrics();  } private Path archiveProfilingReport(SystemReport $$0, List<Path> $$1) { Path $$7; String $$4; if (isLocalServer()) { String $$2 = getSingleplayerServer().getWorldData().getLevelName(); } else { ServerData $$3 = getCurrentServer(); $$4 = ($$3 != null) ? $$3.name : "unknown"; }  try { String $$5 = String.format(Locale.ROOT, "%s-%s-%s", new Object[] { Util.getFilenameFormattedDateTime(), $$4, SharedConstants.getCurrentVersion().getId() }); String $$6 = FileUtil.findAvailableName(MetricsPersister.PROFILING_RESULTS_DIR, $$5, ".zip"); $$7 = MetricsPersister.PROFILING_RESULTS_DIR.resolve($$6); } catch (IOException $$8) { throw new UncheckedIOException($$8); }  try { FileZipper $$10 = new FileZipper($$7); try { $$10.add(Paths.get("system.txt", new String[0]), $$0.toLineSeparatedString()); $$10.add(Paths.get("client", new String[0]).resolve(this.options.getFile().getName()), this.options.dumpOptionsForReport()); Objects.requireNonNull($$10); $$1.forEach($$10::add); $$10.close(); } catch (Throwable throwable) { try { $$10.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } finally { for (Path $$13 : $$1) { try { FileUtils.forceDelete($$13.toFile()); } catch (IOException $$14) { LOGGER.warn("Failed to delete temporary profiling result {}", $$13, $$14); }  }  }  return $$7; } public void stop() { this.running = false; }
/*      */ 
/*      */   
/*      */   public boolean isRunning() {
/* 1766 */     return this.running;
/*      */   }
/*      */   
/*      */   public void pauseGame(boolean $$0) {
/* 1770 */     if (this.screen != null) {
/*      */       return;
/*      */     }
/*      */     
/* 1774 */     boolean $$1 = (hasSingleplayerServer() && !this.singleplayerServer.isPublished());
/* 1775 */     if ($$1) {
/* 1776 */       setScreen((Screen)new PauseScreen(!$$0));
/* 1777 */       this.soundManager.pause();
/*      */     } else {
/* 1779 */       setScreen((Screen)new PauseScreen(true));
/*      */     } 
/*      */   }
/*      */   
/*      */   private void continueAttack(boolean $$0) {
/* 1784 */     if (!$$0) {
/* 1785 */       this.missTime = 0;
/*      */     }
/* 1787 */     if (this.missTime > 0 || this.player.isUsingItem()) {
/*      */       return;
/*      */     }
/*      */     
/* 1791 */     if ($$0 && this.hitResult != null && this.hitResult.getType() == HitResult.Type.BLOCK) {
/* 1792 */       BlockHitResult $$1 = (BlockHitResult)this.hitResult;
/* 1793 */       BlockPos $$2 = $$1.getBlockPos();
/*      */       
/* 1795 */       if (!this.level.getBlockState($$2).isAir()) {
/* 1796 */         Direction $$3 = $$1.getDirection();
/* 1797 */         if (this.gameMode.continueDestroyBlock($$2, $$3)) {
/* 1798 */           this.particleEngine.crack($$2, $$3);
/* 1799 */           this.player.swing(InteractionHand.MAIN_HAND);
/*      */         } 
/*      */       } 
/*      */       
/*      */       return;
/*      */     } 
/* 1805 */     this.gameMode.stopDestroyBlock();
/*      */   } private boolean startAttack() {
/*      */     BlockHitResult $$2;
/*      */     BlockPos $$3;
/* 1809 */     if (this.missTime > 0) {
/* 1810 */       return false;
/*      */     }
/*      */     
/* 1813 */     if (this.hitResult == null) {
/* 1814 */       LOGGER.error("Null returned as 'hitResult', this shouldn't happen!");
/* 1815 */       if (this.gameMode.hasMissTime()) {
/* 1816 */         this.missTime = 10;
/*      */       }
/* 1818 */       return false;
/*      */     } 
/*      */     
/* 1821 */     if (this.player.isHandsBusy()) {
/* 1822 */       return false;
/*      */     }
/*      */     
/* 1825 */     ItemStack $$0 = this.player.getItemInHand(InteractionHand.MAIN_HAND);
/* 1826 */     if (!$$0.isItemEnabled(this.level.enabledFeatures())) {
/* 1827 */       return false;
/*      */     }
/*      */     
/* 1830 */     boolean $$1 = false;
/* 1831 */     switch (this.hitResult.getType()) {
/*      */       case ENTITY:
/* 1833 */         this.gameMode.attack((Player)this.player, ((EntityHitResult)this.hitResult).getEntity());
/*      */         break;
/*      */       case BLOCK:
/* 1836 */         $$2 = (BlockHitResult)this.hitResult;
/* 1837 */         $$3 = $$2.getBlockPos();
/*      */         
/* 1839 */         if (!this.level.getBlockState($$3).isAir()) {
/* 1840 */           this.gameMode.startDestroyBlock($$3, $$2.getDirection());
/* 1841 */           if (this.level.getBlockState($$3).isAir()) {
/* 1842 */             $$1 = true;
/*      */           }
/*      */           break;
/*      */         } 
/*      */       
/*      */       case MISS:
/* 1848 */         if (this.gameMode.hasMissTime()) {
/* 1849 */           this.missTime = 10;
/*      */         }
/* 1851 */         this.player.resetAttackStrengthTicker();
/*      */         break;
/*      */     } 
/*      */ 
/*      */     
/* 1856 */     this.player.swing(InteractionHand.MAIN_HAND);
/* 1857 */     return $$1;
/*      */   }
/*      */   
/*      */   private void startUseItem() {
/* 1861 */     if (this.gameMode.isDestroying()) {
/*      */       return;
/*      */     }
/*      */     
/* 1865 */     this.rightClickDelay = 4;
/*      */     
/* 1867 */     if (this.player.isHandsBusy()) {
/*      */       return;
/*      */     }
/*      */     
/* 1871 */     if (this.hitResult == null) {
/* 1872 */       LOGGER.warn("Null returned as 'hitResult', this shouldn't happen!");
/*      */     }
/*      */     
/* 1875 */     for (InteractionHand $$0 : InteractionHand.values()) {
/* 1876 */       ItemStack $$1 = this.player.getItemInHand($$0);
/* 1877 */       if (!$$1.isItemEnabled(this.level.enabledFeatures())) {
/*      */         return;
/*      */       }
/*      */       
/* 1881 */       if (this.hitResult != null) {
/* 1882 */         EntityHitResult $$2; Entity $$3; InteractionResult $$4; BlockHitResult $$5; int $$6; InteractionResult $$7; switch (this.hitResult.getType()) {
/*      */           case ENTITY:
/* 1884 */             $$2 = (EntityHitResult)this.hitResult;
/* 1885 */             $$3 = $$2.getEntity();
/* 1886 */             if (!this.level.getWorldBorder().isWithinBounds($$3.blockPosition())) {
/*      */               return;
/*      */             }
/* 1889 */             $$4 = this.gameMode.interactAt((Player)this.player, $$3, $$2, $$0);
/* 1890 */             if (!$$4.consumesAction()) {
/* 1891 */               $$4 = this.gameMode.interact((Player)this.player, $$3, $$0);
/*      */             }
/*      */             
/* 1894 */             if ($$4.consumesAction()) {
/* 1895 */               if ($$4.shouldSwing()) {
/* 1896 */                 this.player.swing($$0);
/*      */               }
/*      */               return;
/*      */             } 
/*      */             break;
/*      */           
/*      */           case BLOCK:
/* 1903 */             $$5 = (BlockHitResult)this.hitResult;
/*      */             
/* 1905 */             $$6 = $$1.getCount();
/* 1906 */             $$7 = this.gameMode.useItemOn(this.player, $$0, $$5);
/* 1907 */             if ($$7.consumesAction()) {
/* 1908 */               if ($$7.shouldSwing()) {
/* 1909 */                 this.player.swing($$0);
/*      */ 
/*      */                 
/* 1912 */                 if (!$$1.isEmpty() && ($$1.getCount() != $$6 || this.gameMode.hasInfiniteItems())) {
/* 1913 */                   this.gameRenderer.itemInHandRenderer.itemUsed($$0);
/*      */                 }
/*      */               } 
/*      */               return;
/*      */             } 
/* 1918 */             if ($$7 == InteractionResult.FAIL) {
/*      */               return;
/*      */             }
/*      */             break;
/*      */         } 
/*      */       
/*      */       } 
/* 1925 */       if (!$$1.isEmpty()) {
/* 1926 */         InteractionResult $$8 = this.gameMode.useItem((Player)this.player, $$0);
/* 1927 */         if ($$8.consumesAction()) {
/* 1928 */           if ($$8.shouldSwing()) {
/* 1929 */             this.player.swing($$0);
/*      */           }
/*      */           
/* 1932 */           this.gameRenderer.itemInHandRenderer.itemUsed($$0);
/*      */           return;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public MusicManager getMusicManager() {
/* 1940 */     return this.musicManager;
/*      */   }
/*      */   
/*      */   public void tick() {
/* 1944 */     this.clientTickCount++;
/*      */     
/* 1946 */     if (this.level != null && !this.pause) {
/* 1947 */       this.level.tickRateManager().tick();
/*      */     }
/* 1949 */     if (this.rightClickDelay > 0) {
/* 1950 */       this.rightClickDelay--;
/*      */     }
/*      */     
/* 1953 */     this.profiler.push("gui");
/* 1954 */     this.chatListener.tick();
/* 1955 */     this.gui.tick(this.pause);
/* 1956 */     this.profiler.pop();
/* 1957 */     this.gameRenderer.pick(1.0F);
/* 1958 */     this.tutorial.onLookAt(this.level, this.hitResult);
/*      */     
/* 1960 */     this.profiler.push("gameMode");
/* 1961 */     if (!this.pause && this.level != null) {
/* 1962 */       this.gameMode.tick();
/*      */     }
/* 1964 */     this.profiler.popPush("textures");
/* 1965 */     boolean $$0 = (this.level == null || this.level.tickRateManager().runsNormally());
/* 1966 */     if ($$0) {
/* 1967 */       this.textureManager.tick();
/*      */     }
/*      */     
/* 1970 */     if (this.screen == null && this.player != null)
/* 1971 */     { if (this.player.isDeadOrDying() && !(this.screen instanceof DeathScreen)) {
/* 1972 */         setScreen((Screen)null);
/* 1973 */       } else if (this.player.isSleeping() && this.level != null) {
/* 1974 */         setScreen((Screen)new InBedChatScreen());
/*      */       }  }
/* 1976 */     else { Screen screen = this.screen; if (screen instanceof InBedChatScreen) { InBedChatScreen $$1 = (InBedChatScreen)screen; if (!this.player.isSleeping())
/* 1977 */           $$1.onPlayerWokeUp();  }
/*      */        }
/*      */     
/* 1980 */     if (this.screen != null) {
/* 1981 */       this.missTime = 10000;
/*      */     }
/*      */     
/* 1984 */     if (this.screen != null) {
/* 1985 */       Screen.wrapScreenError(() -> this.screen.tick(), "Ticking screen", this.screen.getClass().getCanonicalName());
/*      */     }
/* 1987 */     if (!getDebugOverlay().showDebugScreen()) {
/* 1988 */       this.gui.clearCache();
/*      */     }
/*      */     
/* 1991 */     if (this.overlay == null && this.screen == null) {
/* 1992 */       this.profiler.popPush("Keybindings");
/* 1993 */       handleKeybinds();
/*      */       
/* 1995 */       if (this.missTime > 0) {
/* 1996 */         this.missTime--;
/*      */       }
/*      */     } 
/*      */     
/* 2000 */     if (this.level != null) {
/* 2001 */       this.profiler.popPush("gameRenderer");
/* 2002 */       if (!this.pause) {
/* 2003 */         this.gameRenderer.tick();
/*      */       }
/* 2005 */       this.profiler.popPush("levelRenderer");
/* 2006 */       if (!this.pause) {
/* 2007 */         this.levelRenderer.tick();
/*      */       }
/* 2009 */       this.profiler.popPush("level");
/* 2010 */       if (!this.pause) {
/* 2011 */         this.level.tickEntities();
/*      */       }
/* 2013 */     } else if (this.gameRenderer.currentEffect() != null) {
/* 2014 */       this.gameRenderer.shutdownEffect();
/*      */     } 
/*      */ 
/*      */     
/* 2018 */     if (!this.pause) {
/* 2019 */       this.musicManager.tick();
/*      */     }
/* 2021 */     this.soundManager.tick(this.pause);
/*      */     
/* 2023 */     if (this.level != null) {
/* 2024 */       if (!this.pause) {
/* 2025 */         if (!this.options.joinedFirstServer && isMultiplayerServer()) {
/* 2026 */           MutableComponent mutableComponent1 = Component.translatable("tutorial.socialInteractions.title");
/* 2027 */           MutableComponent mutableComponent2 = Component.translatable("tutorial.socialInteractions.description", new Object[] { Tutorial.key("socialInteractions") });
/* 2028 */           this.socialInteractionsToast = new TutorialToast(TutorialToast.Icons.SOCIAL_INTERACTIONS, (Component)mutableComponent1, (Component)mutableComponent2, true);
/*      */           
/* 2030 */           this.tutorial.addTimedToast(this.socialInteractionsToast, 160);
/* 2031 */           this.options.joinedFirstServer = true;
/* 2032 */           this.options.save();
/*      */         } 
/*      */         
/* 2035 */         this.tutorial.tick();
/*      */         
/*      */         try {
/* 2038 */           this.level.tick(() -> true);
/* 2039 */         } catch (Throwable $$4) {
/* 2040 */           CrashReport $$5 = CrashReport.forThrowable($$4, "Exception in world tick");
/* 2041 */           if (this.level == null) {
/* 2042 */             CrashReportCategory $$6 = $$5.addCategory("Affected level");
/* 2043 */             $$6.setDetail("Problem", "Level is null!");
/*      */           } else {
/* 2045 */             this.level.fillReportDetails($$5);
/*      */           } 
/* 2047 */           throw new ReportedException($$5);
/*      */         } 
/*      */       } 
/* 2050 */       this.profiler.popPush("animateTick");
/* 2051 */       if (!this.pause && $$0) {
/* 2052 */         this.level.animateTick(this.player.getBlockX(), this.player.getBlockY(), this.player.getBlockZ());
/*      */       }
/* 2054 */       this.profiler.popPush("particles");
/* 2055 */       if (!this.pause && $$0) {
/* 2056 */         this.particleEngine.tick();
/*      */       }
/* 2058 */     } else if (this.pendingConnection != null) {
/* 2059 */       this.profiler.popPush("pendingConnection");
/* 2060 */       this.pendingConnection.tick();
/*      */     } 
/*      */     
/* 2063 */     this.profiler.popPush("keyboard");
/* 2064 */     this.keyboardHandler.tick();
/*      */     
/* 2066 */     this.profiler.pop();
/*      */   }
/*      */   
/*      */   private boolean isMultiplayerServer() {
/* 2070 */     return (!this.isLocalServer || (this.singleplayerServer != null && this.singleplayerServer.isPublished()));
/*      */   }
/*      */   
/*      */   private void handleKeybinds() {
/* 2074 */     while (this.options.keyTogglePerspective.consumeClick()) {
/* 2075 */       CameraType $$0 = this.options.getCameraType();
/* 2076 */       this.options.setCameraType(this.options.getCameraType().cycle());
/* 2077 */       if ($$0.isFirstPerson() != this.options.getCameraType().isFirstPerson()) {
/* 2078 */         this.gameRenderer.checkEntityPostEffect(this.options.getCameraType().isFirstPerson() ? getCameraEntity() : null);
/*      */       }
/* 2080 */       this.levelRenderer.needsUpdate();
/*      */     } 
/*      */     
/* 2083 */     while (this.options.keySmoothCamera.consumeClick()) {
/* 2084 */       this.options.smoothCamera = !this.options.smoothCamera;
/*      */     }
/*      */     
/* 2087 */     for (int $$1 = 0; $$1 < 9; $$1++) {
/* 2088 */       boolean $$2 = this.options.keySaveHotbarActivator.isDown();
/* 2089 */       boolean $$3 = this.options.keyLoadHotbarActivator.isDown();
/* 2090 */       if (this.options.keyHotbarSlots[$$1].consumeClick()) {
/* 2091 */         if (this.player.isSpectator()) {
/* 2092 */           this.gui.getSpectatorGui().onHotbarSelected($$1);
/* 2093 */         } else if (this.player.isCreative() && this.screen == null && ($$3 || $$2)) {
/* 2094 */           CreativeModeInventoryScreen.handleHotbarLoadOrSave(this, $$1, $$3, $$2);
/*      */         } else {
/* 2096 */           (this.player.getInventory()).selected = $$1;
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/* 2101 */     while (this.options.keySocialInteractions.consumeClick()) {
/* 2102 */       if (!isMultiplayerServer()) {
/* 2103 */         this.player.displayClientMessage(SOCIAL_INTERACTIONS_NOT_AVAILABLE, true);
/* 2104 */         this.narrator.sayNow(SOCIAL_INTERACTIONS_NOT_AVAILABLE); continue;
/*      */       } 
/* 2106 */       if (this.socialInteractionsToast != null) {
/* 2107 */         this.tutorial.removeTimedToast(this.socialInteractionsToast);
/* 2108 */         this.socialInteractionsToast = null;
/*      */       } 
/* 2110 */       setScreen((Screen)new SocialInteractionsScreen());
/*      */     } 
/*      */ 
/*      */     
/* 2114 */     while (this.options.keyInventory.consumeClick()) {
/* 2115 */       if (this.gameMode.isServerControlledInventory()) {
/* 2116 */         this.player.sendOpenInventory(); continue;
/*      */       } 
/* 2118 */       this.tutorial.onOpenInventory();
/* 2119 */       setScreen((Screen)new InventoryScreen((Player)this.player));
/*      */     } 
/*      */     
/* 2122 */     while (this.options.keyAdvancements.consumeClick()) {
/* 2123 */       setScreen((Screen)new AdvancementsScreen(this.player.connection.getAdvancements()));
/*      */     }
/* 2125 */     while (this.options.keySwapOffhand.consumeClick()) {
/* 2126 */       if (!this.player.isSpectator()) {
/* 2127 */         getConnection().send((Packet)new ServerboundPlayerActionPacket(ServerboundPlayerActionPacket.Action.SWAP_ITEM_WITH_OFFHAND, BlockPos.ZERO, Direction.DOWN));
/*      */       }
/*      */     } 
/* 2130 */     while (this.options.keyDrop.consumeClick()) {
/* 2131 */       if (!this.player.isSpectator() && 
/* 2132 */         this.player.drop(Screen.hasControlDown())) {
/* 2133 */         this.player.swing(InteractionHand.MAIN_HAND);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 2138 */     while (this.options.keyChat.consumeClick()) {
/* 2139 */       openChatScreen("");
/*      */     }
/* 2141 */     if (this.screen == null && this.overlay == null && this.options.keyCommand.consumeClick()) {
/* 2142 */       openChatScreen("/");
/*      */     }
/*      */     
/* 2145 */     boolean $$4 = false;
/* 2146 */     if (this.player.isUsingItem()) {
/* 2147 */       if (!this.options.keyUse.isDown()) {
/* 2148 */         this.gameMode.releaseUsingItem((Player)this.player);
/*      */       }
/*      */       
/* 2151 */       while (this.options.keyAttack.consumeClick());
/*      */       
/* 2153 */       while (this.options.keyUse.consumeClick());
/*      */       
/* 2155 */       while (this.options.keyPickItem.consumeClick());
/*      */     } else {
/*      */       
/* 2158 */       while (this.options.keyAttack.consumeClick()) {
/* 2159 */         $$4 |= startAttack();
/*      */       }
/* 2161 */       while (this.options.keyUse.consumeClick()) {
/* 2162 */         startUseItem();
/*      */       }
/* 2164 */       while (this.options.keyPickItem.consumeClick()) {
/* 2165 */         pickBlock();
/*      */       }
/*      */     } 
/*      */     
/* 2169 */     if (this.options.keyUse.isDown() && this.rightClickDelay == 0 && !this.player.isUsingItem()) {
/* 2170 */       startUseItem();
/*      */     }
/*      */     
/* 2173 */     continueAttack((this.screen == null && !$$4 && this.options.keyAttack.isDown() && this.mouseHandler.isMouseGrabbed()));
/*      */   }
/*      */   
/*      */   public ClientTelemetryManager getTelemetryManager() {
/* 2177 */     return this.telemetryManager;
/*      */   }
/*      */   
/*      */   public double getGpuUtilization() {
/* 2181 */     return this.gpuUtilization;
/*      */   }
/*      */   
/*      */   public ProfileKeyPairManager getProfileKeyPairManager() {
/* 2185 */     return this.profileKeyPairManager;
/*      */   }
/*      */   
/*      */   public WorldOpenFlows createWorldOpenFlows() {
/* 2189 */     return new WorldOpenFlows(this, this.levelSource);
/*      */   }
/*      */   
/*      */   public void doWorldLoad(LevelStorageSource.LevelStorageAccess $$0, PackRepository $$1, WorldStem $$2, boolean $$3) {
/* 2193 */     disconnect();
/* 2194 */     this.progressListener.set(null);
/* 2195 */     Instant $$4 = Instant.now();
/*      */     
/*      */     try {
/* 2198 */       $$0.saveDataTag((RegistryAccess)$$2.registries().compositeAccess(), $$2.worldData());
/* 2199 */       Services $$5 = Services.create(this.authenticationService, this.gameDirectory);
/* 2200 */       $$5.profileCache().setExecutor((Executor)this);
/* 2201 */       SkullBlockEntity.setup($$5, (Executor)this);
/* 2202 */       GameProfileCache.setUsesAuthentication(false);
/*      */       
/* 2204 */       this.singleplayerServer = (IntegratedServer)MinecraftServer.spin($$4 -> new IntegratedServer($$4, this, $$0, $$1, $$2, $$3, ()));
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2209 */       this.isLocalServer = true;
/* 2210 */       updateReportEnvironment(ReportEnvironment.local());
/* 2211 */       this.quickPlayLog.setWorldData(QuickPlayLog.Type.SINGLEPLAYER, $$0.getLevelId(), $$2.worldData().getLevelName());
/* 2212 */     } catch (Throwable $$6) {
/* 2213 */       CrashReport $$7 = CrashReport.forThrowable($$6, "Starting integrated server");
/* 2214 */       CrashReportCategory $$8 = $$7.addCategory("Starting integrated server");
/*      */       
/* 2216 */       $$8.setDetail("Level ID", $$0.getLevelId());
/* 2217 */       $$8.setDetail("Level Name", () -> $$0.worldData().getLevelName());
/*      */       
/* 2219 */       throw new ReportedException($$7);
/*      */     } 
/*      */     
/* 2222 */     while (this.progressListener.get() == null) {
/* 2223 */       Thread.yield();
/*      */     }
/* 2225 */     LevelLoadingScreen $$9 = new LevelLoadingScreen(this.progressListener.get());
/*      */     
/* 2227 */     setScreen((Screen)$$9);
/* 2228 */     this.profiler.push("waitForServer");
/* 2229 */     while (!this.singleplayerServer.isReady() || this.overlay != null) {
/* 2230 */       $$9.tick();
/* 2231 */       runTick(false);
/*      */       try {
/* 2233 */         Thread.sleep(16L);
/* 2234 */       } catch (InterruptedException interruptedException) {}
/*      */       
/* 2236 */       handleDelayedCrash();
/*      */     } 
/* 2238 */     this.profiler.pop();
/* 2239 */     Duration $$10 = Duration.between($$4, Instant.now());
/*      */     
/* 2241 */     SocketAddress $$11 = this.singleplayerServer.getConnection().startMemoryChannel();
/* 2242 */     Connection $$12 = Connection.connectToLocalServer($$11);
/* 2243 */     $$12.initiateServerboundPlayConnection($$11
/* 2244 */         .toString(), 0, (ClientLoginPacketListener)new ClientHandshakePacketListenerImpl($$12, this, null, null, $$3, $$10, $$0 -> {
/*      */           
/*      */           }));
/*      */     
/* 2248 */     $$12.send((Packet)new ServerboundHelloPacket(getUser().getName(), getUser().getProfileId()));
/* 2249 */     this.pendingConnection = $$12;
/*      */   }
/*      */   
/*      */   public void setLevel(ClientLevel $$0) {
/* 2253 */     ProgressScreen $$1 = new ProgressScreen(true);
/* 2254 */     $$1.progressStartNoAbort((Component)Component.translatable("connect.joining"));
/* 2255 */     updateScreenAndTick((Screen)$$1);
/* 2256 */     this.level = $$0;
/* 2257 */     updateLevelInEngines($$0);
/*      */     
/* 2259 */     if (!this.isLocalServer) {
/* 2260 */       Services $$2 = Services.create(this.authenticationService, this.gameDirectory);
/* 2261 */       $$2.profileCache().setExecutor((Executor)this);
/* 2262 */       SkullBlockEntity.setup($$2, (Executor)this);
/* 2263 */       GameProfileCache.setUsesAuthentication(false);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void disconnect() {
/* 2268 */     disconnect((Screen)new ProgressScreen(true));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void disconnect(Screen $$0) {
/* 2274 */     ClientPacketListener $$1 = getConnection();
/* 2275 */     if ($$1 != null) {
/* 2276 */       dropAllTasks();
/* 2277 */       $$1.close();
/* 2278 */       clearDownloadedResourcePacks();
/*      */     } 
/* 2280 */     this.playerSocialManager.stopOnlineMode();
/*      */     
/* 2282 */     if (this.metricsRecorder.isRecording()) {
/* 2283 */       debugClientMetricsCancel();
/*      */     }
/*      */     
/* 2286 */     IntegratedServer $$2 = this.singleplayerServer;
/* 2287 */     this.singleplayerServer = null;
/*      */     
/* 2289 */     this.gameRenderer.resetData();
/* 2290 */     this.gameMode = null;
/*      */     
/* 2292 */     this.narrator.clear();
/* 2293 */     this.clientLevelTeardownInProgress = true;
/*      */     try {
/* 2295 */       updateScreenAndTick($$0);
/*      */       
/* 2297 */       if (this.level != null) {
/* 2298 */         if ($$2 != null) {
/* 2299 */           this.profiler.push("waitForServer");
/* 2300 */           while (!$$2.isShutdown()) {
/* 2301 */             runTick(false);
/*      */           }
/* 2303 */           this.profiler.pop();
/*      */         } 
/* 2305 */         this.gui.onDisconnected();
/* 2306 */         this.isLocalServer = false;
/*      */       } 
/*      */       
/* 2309 */       this.level = null;
/* 2310 */       updateLevelInEngines((ClientLevel)null);
/* 2311 */       this.player = null;
/*      */     } finally {
/* 2313 */       this.clientLevelTeardownInProgress = false;
/*      */     } 
/* 2315 */     SkullBlockEntity.clear();
/*      */   }
/*      */   
/*      */   public void clearDownloadedResourcePacks() {
/* 2319 */     this.downloadedPackSource.cleanupAfterDisconnect();
/*      */     
/* 2321 */     runAllTasks();
/*      */   }
/*      */   
/*      */   public void clearClientLevel(Screen $$0) {
/* 2325 */     ClientPacketListener $$1 = getConnection();
/* 2326 */     if ($$1 != null) {
/* 2327 */       $$1.clearLevel();
/*      */     }
/*      */     
/* 2330 */     if (this.metricsRecorder.isRecording()) {
/* 2331 */       debugClientMetricsCancel();
/*      */     }
/*      */     
/* 2334 */     this.gameRenderer.resetData();
/* 2335 */     this.gameMode = null;
/*      */     
/* 2337 */     this.narrator.clear();
/* 2338 */     this.clientLevelTeardownInProgress = true;
/*      */     try {
/* 2340 */       updateScreenAndTick($$0);
/*      */ 
/*      */ 
/*      */       
/* 2344 */       this.gui.onDisconnected();
/* 2345 */       this.level = null;
/* 2346 */       updateLevelInEngines((ClientLevel)null);
/* 2347 */       this.player = null;
/*      */     } finally {
/* 2349 */       this.clientLevelTeardownInProgress = false;
/*      */     } 
/* 2351 */     SkullBlockEntity.clear();
/*      */   }
/*      */   
/*      */   private void updateScreenAndTick(Screen $$0) {
/* 2355 */     this.profiler.push("forcedTick");
/* 2356 */     this.soundManager.stop();
/*      */     
/* 2358 */     this.cameraEntity = null;
/* 2359 */     this.pendingConnection = null;
/*      */     
/* 2361 */     setScreen($$0);
/* 2362 */     runTick(false);
/* 2363 */     this.profiler.pop();
/*      */   }
/*      */   
/*      */   public void forceSetScreen(Screen $$0) {
/* 2367 */     this.profiler.push("forcedTick");
/* 2368 */     setScreen($$0);
/* 2369 */     runTick(false);
/* 2370 */     this.profiler.pop();
/*      */   }
/*      */   
/*      */   private void updateLevelInEngines(@Nullable ClientLevel $$0) {
/* 2374 */     this.levelRenderer.setLevel($$0);
/* 2375 */     this.particleEngine.setLevel($$0);
/* 2376 */     this.blockEntityRenderDispatcher.setLevel((Level)$$0);
/* 2377 */     updateTitle();
/*      */   }
/*      */   
/*      */   private UserApiService.UserProperties userProperties() {
/* 2381 */     return this.userPropertiesFuture.join();
/*      */   }
/*      */   
/*      */   public boolean telemetryOptInExtra() {
/* 2385 */     return (extraTelemetryAvailable() && ((Boolean)this.options.telemetryOptInExtra().get()).booleanValue());
/*      */   }
/*      */   
/*      */   public boolean extraTelemetryAvailable() {
/* 2389 */     return (allowsTelemetry() && userProperties().flag(UserApiService.UserFlag.OPTIONAL_TELEMETRY_AVAILABLE));
/*      */   }
/*      */   
/*      */   public boolean allowsTelemetry() {
/* 2393 */     if (SharedConstants.IS_RUNNING_IN_IDE) {
/* 2394 */       return false;
/*      */     }
/* 2396 */     return userProperties().flag(UserApiService.UserFlag.TELEMETRY_ENABLED);
/*      */   }
/*      */   
/*      */   public boolean allowsMultiplayer() {
/* 2400 */     return (this.allowsMultiplayer && 
/* 2401 */       userProperties().flag(UserApiService.UserFlag.SERVERS_ALLOWED) && 
/* 2402 */       multiplayerBan() == null && 
/* 2403 */       !isNameBanned());
/*      */   }
/*      */   
/*      */   public boolean allowsRealms() {
/* 2407 */     return (userProperties().flag(UserApiService.UserFlag.REALMS_ALLOWED) && multiplayerBan() == null);
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public BanDetails multiplayerBan() {
/* 2412 */     return (BanDetails)userProperties().bannedScopes().get("MULTIPLAYER");
/*      */   }
/*      */   
/*      */   public boolean isNameBanned() {
/* 2416 */     ProfileResult $$0 = this.profileFuture.getNow(null);
/* 2417 */     return ($$0 != null && $$0.actions().contains(ProfileActionType.FORCED_NAME_CHANGE));
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isBlocked(UUID $$0) {
/* 2422 */     if (!getChatStatus().isChatAllowed(false)) {
/* 2423 */       return ((this.player == null || !$$0.equals(this.player.getUUID())) && !$$0.equals(Util.NIL_UUID));
/*      */     }
/* 2425 */     return this.playerSocialManager.shouldHideMessageFrom($$0);
/*      */   }
/*      */   
/*      */   public ChatStatus getChatStatus() {
/* 2429 */     if (this.options.chatVisibility().get() == ChatVisiblity.HIDDEN) {
/* 2430 */       return ChatStatus.DISABLED_BY_OPTIONS;
/*      */     }
/* 2432 */     if (!this.allowsChat) {
/* 2433 */       return ChatStatus.DISABLED_BY_LAUNCHER;
/*      */     }
/* 2435 */     if (!userProperties().flag(UserApiService.UserFlag.CHAT_ALLOWED)) {
/* 2436 */       return ChatStatus.DISABLED_BY_PROFILE;
/*      */     }
/* 2438 */     return ChatStatus.ENABLED;
/*      */   }
/*      */   
/*      */   public final boolean isDemo() {
/* 2442 */     return this.demo;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public ClientPacketListener getConnection() {
/* 2447 */     return (this.player == null) ? null : this.player.connection;
/*      */   }
/*      */   
/*      */   public static boolean renderNames() {
/* 2451 */     return !instance.options.hideGui;
/*      */   }
/*      */   
/*      */   public static boolean useFancyGraphics() {
/* 2455 */     return (((GraphicsStatus)instance.options.graphicsMode().get()).getId() >= GraphicsStatus.FANCY.getId());
/*      */   }
/*      */   
/*      */   public static boolean useShaderTransparency() {
/* 2459 */     return (!instance.gameRenderer.isPanoramicMode() && ((GraphicsStatus)instance.options.graphicsMode().get()).getId() >= GraphicsStatus.FABULOUS.getId());
/*      */   }
/*      */   
/*      */   public static boolean useAmbientOcclusion() {
/* 2463 */     return ((Boolean)instance.options.ambientOcclusion().get()).booleanValue();
/*      */   }
/*      */   private void pickBlock() {
/*      */     ItemStack $$8;
/* 2467 */     if (this.hitResult == null || this.hitResult.getType() == HitResult.Type.MISS) {
/*      */       return;
/*      */     }
/*      */     
/* 2471 */     boolean $$0 = (this.player.getAbilities()).instabuild;
/* 2472 */     BlockEntity $$1 = null;
/*      */ 
/*      */     
/* 2475 */     HitResult.Type $$2 = this.hitResult.getType();
/* 2476 */     if ($$2 == HitResult.Type.BLOCK) {
/* 2477 */       BlockPos $$3 = ((BlockHitResult)this.hitResult).getBlockPos();
/*      */       
/* 2479 */       BlockState $$4 = this.level.getBlockState($$3);
/*      */       
/* 2481 */       if ($$4.isAir()) {
/*      */         return;
/*      */       }
/*      */       
/* 2485 */       Block $$5 = $$4.getBlock();
/* 2486 */       ItemStack $$6 = $$5.getCloneItemStack((LevelReader)this.level, $$3, $$4);
/* 2487 */       if ($$6.isEmpty()) {
/*      */         return;
/*      */       }
/*      */       
/* 2491 */       if ($$0 && Screen.hasControlDown() && $$4.hasBlockEntity()) {
/* 2492 */         $$1 = this.level.getBlockEntity($$3);
/*      */       }
/* 2494 */     } else if ($$2 == HitResult.Type.ENTITY && $$0) {
/* 2495 */       Entity $$7 = ((EntityHitResult)this.hitResult).getEntity();
/* 2496 */       $$8 = $$7.getPickResult();
/* 2497 */       if ($$8 == null) {
/*      */         return;
/*      */       }
/*      */     } else {
/*      */       return;
/*      */     } 
/*      */     
/* 2504 */     if ($$8.isEmpty()) {
/* 2505 */       String $$10 = "";
/* 2506 */       if ($$2 == HitResult.Type.BLOCK) {
/* 2507 */         $$10 = BuiltInRegistries.BLOCK.getKey(this.level.getBlockState(((BlockHitResult)this.hitResult).getBlockPos()).getBlock()).toString();
/* 2508 */       } else if ($$2 == HitResult.Type.ENTITY) {
/* 2509 */         $$10 = BuiltInRegistries.ENTITY_TYPE.getKey(((EntityHitResult)this.hitResult).getEntity().getType()).toString();
/*      */       } 
/* 2511 */       LOGGER.warn("Picking on: [{}] {} gave null item", $$2, $$10);
/*      */       
/*      */       return;
/*      */     } 
/* 2515 */     Inventory $$11 = this.player.getInventory();
/* 2516 */     if ($$1 != null) {
/* 2517 */       addCustomNbtData($$8, $$1);
/*      */     }
/*      */     
/* 2520 */     int $$12 = $$11.findSlotMatchingItem($$8);
/* 2521 */     if ($$0) {
/*      */       
/* 2523 */       $$11.setPickedItem($$8);
/*      */       
/* 2525 */       this.gameMode.handleCreativeModeItemAdd(this.player.getItemInHand(InteractionHand.MAIN_HAND), 36 + $$11.selected);
/* 2526 */     } else if ($$12 != -1) {
/* 2527 */       if (Inventory.isHotbarSlot($$12)) {
/* 2528 */         $$11.selected = $$12;
/*      */       } else {
/* 2530 */         this.gameMode.handlePickItem($$12);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void addCustomNbtData(ItemStack $$0, BlockEntity $$1) {
/* 2537 */     CompoundTag $$2 = $$1.saveWithFullMetadata();
/*      */     
/* 2539 */     BlockItem.setBlockEntityData($$0, $$1.getType(), $$2);
/* 2540 */     if ($$0.getItem() instanceof net.minecraft.world.item.PlayerHeadItem && $$2.contains("SkullOwner")) {
/* 2541 */       CompoundTag $$3 = $$2.getCompound("SkullOwner");
/* 2542 */       CompoundTag $$4 = $$0.getOrCreateTag();
/* 2543 */       $$4.put("SkullOwner", (Tag)$$3);
/* 2544 */       CompoundTag $$5 = $$4.getCompound("BlockEntityTag");
/* 2545 */       $$5.remove("SkullOwner");
/*      */ 
/*      */       
/* 2548 */       $$5.remove("x");
/* 2549 */       $$5.remove("y");
/* 2550 */       $$5.remove("z");
/*      */       
/*      */       return;
/*      */     } 
/* 2554 */     CompoundTag $$6 = new CompoundTag();
/* 2555 */     ListTag $$7 = new ListTag();
/* 2556 */     $$7.add(StringTag.valueOf("\"(+NBT)\""));
/* 2557 */     $$6.put("Lore", (Tag)$$7);
/* 2558 */     $$0.addTagElement("display", (Tag)$$6);
/*      */   }
/*      */   
/*      */   public CrashReport fillReport(CrashReport $$0) {
/* 2562 */     SystemReport $$1 = $$0.getSystemReport();
/* 2563 */     fillSystemReport($$1, this, this.languageManager, this.launchedVersion, this.options);
/* 2564 */     fillUptime($$0.addCategory("Uptime"));
/*      */     
/* 2566 */     if (this.level != null) {
/* 2567 */       this.level.fillReportDetails($$0);
/*      */     }
/*      */     
/* 2570 */     if (this.singleplayerServer != null) {
/* 2571 */       this.singleplayerServer.fillSystemReport($$1);
/*      */     }
/*      */     
/* 2574 */     this.reloadStateTracker.fillCrashReport($$0);
/* 2575 */     return $$0;
/*      */   }
/*      */   
/*      */   public static void fillReport(@Nullable Minecraft $$0, @Nullable LanguageManager $$1, String $$2, @Nullable Options $$3, CrashReport $$4) {
/* 2579 */     SystemReport $$5 = $$4.getSystemReport();
/* 2580 */     fillSystemReport($$5, $$0, $$1, $$2, $$3);
/*      */   }
/*      */   
/*      */   private static String formatSeconds(double $$0) {
/* 2584 */     return String.format(Locale.ROOT, "%.3fs", new Object[] { Double.valueOf($$0) });
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void fillUptime(CrashReportCategory $$0) {
/* 2590 */     $$0.setDetail("JVM uptime", () -> formatSeconds(ManagementFactory.getRuntimeMXBean().getUptime() / 1000.0D));
/*      */     
/* 2592 */     $$0.setDetail("Wall uptime", () -> formatSeconds((System.currentTimeMillis() - this.clientStartTimeMs) / 1000.0D));
/*      */     
/* 2594 */     $$0.setDetail("High-res time", () -> formatSeconds(Util.getMillis() / 1000.0D));
/*      */     
/* 2596 */     $$0.setDetail("Client ticks", () -> String.format(Locale.ROOT, "%d ticks / %.3fs", new Object[] { Long.valueOf(this.clientTickCount), Double.valueOf(this.clientTickCount / 20.0D) }));
/*      */   }
/*      */   
/*      */   private static SystemReport fillSystemReport(SystemReport $$0, @Nullable Minecraft $$1, @Nullable LanguageManager $$2, String $$3, @Nullable Options $$4) {
/* 2600 */     $$0.setDetail("Launched Version", () -> $$0);
/* 2601 */     String $$5 = getLauncherBrand();
/* 2602 */     if ($$5 != null) {
/* 2603 */       $$0.setDetail("Launcher name", $$5);
/*      */     }
/* 2605 */     $$0.setDetail("Backend library", RenderSystem::getBackendDescription);
/* 2606 */     $$0.setDetail("Backend API", RenderSystem::getApiDescription);
/* 2607 */     $$0.setDetail("Window size", () -> ($$0 != null) ? ("" + $$0.window.getWidth() + "x" + $$0.window.getWidth()) : "<not initialized>");
/*      */     
/* 2609 */     $$0.setDetail("GL Caps", RenderSystem::getCapsString);
/* 2610 */     $$0.setDetail("GL debug messages", () -> GlDebug.isDebugEnabled() ? String.join("\n", GlDebug.getLastOpenGlDebugMessages()) : "<disabled>");
/* 2611 */     $$0.setDetail("Using VBOs", () -> "Yes");
/* 2612 */     $$0.setDetail("Is Modded", () -> checkModStatus().fullDescription());
/* 2613 */     $$0.setDetail("Universe", () -> ($$0 != null) ? Long.toHexString($$0.canary) : "404");
/*      */     
/* 2615 */     $$0.setDetail("Type", "Client (map_client.txt)");
/* 2616 */     if ($$4 != null) {
/* 2617 */       if ($$1 != null) {
/* 2618 */         String $$6 = $$1.getGpuWarnlistManager().getAllWarnings();
/* 2619 */         if ($$6 != null) {
/* 2620 */           $$0.setDetail("GPU Warnings", $$6);
/*      */         }
/*      */       } 
/* 2623 */       $$0.setDetail("Graphics mode", ((GraphicsStatus)$$4.graphicsMode().get()).toString());
/* 2624 */       $$0.setDetail("Render Distance", "" + $$4.getEffectiveRenderDistance() + "/" + $$4.getEffectiveRenderDistance() + " chunks");
/* 2625 */       $$0.setDetail("Resource Packs", () -> {
/*      */             StringBuilder $$1 = new StringBuilder();
/*      */             
/*      */             for (String $$2 : $$0.resourcePacks) {
/*      */               if ($$1.length() > 0) {
/*      */                 $$1.append(", ");
/*      */               }
/*      */               $$1.append($$2);
/*      */               if ($$0.incompatibleResourcePacks.contains($$2)) {
/*      */                 $$1.append(" (incompatible)");
/*      */               }
/*      */             } 
/*      */             return $$1.toString();
/*      */           });
/*      */     } 
/* 2640 */     if ($$2 != null) {
/* 2641 */       $$0.setDetail("Current Language", () -> $$0.getSelected());
/*      */     }
/* 2643 */     $$0.setDetail("Locale", String.valueOf(Locale.getDefault()));
/* 2644 */     $$0.setDetail("CPU", GlUtil::getCpuInfo);
/* 2645 */     return $$0;
/*      */   }
/*      */   
/*      */   public static Minecraft getInstance() {
/* 2649 */     return instance;
/*      */   }
/*      */   
/*      */   public CompletableFuture<Void> delayTextureReload() {
/* 2653 */     return submit(this::reloadResourcePacks).thenCompose($$0 -> $$0);
/*      */   }
/*      */   
/*      */   public void updateReportEnvironment(ReportEnvironment $$0) {
/* 2657 */     if (!this.reportingContext.matches($$0)) {
/* 2658 */       this.reportingContext = ReportingContext.create($$0, this.userApiService);
/*      */     }
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public ServerData getCurrentServer() {
/* 2664 */     return (ServerData)Optionull.map(getConnection(), ClientPacketListener::getServerData);
/*      */   }
/*      */   
/*      */   public boolean isLocalServer() {
/* 2668 */     return this.isLocalServer;
/*      */   }
/*      */   
/*      */   public boolean hasSingleplayerServer() {
/* 2672 */     return (this.isLocalServer && this.singleplayerServer != null);
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public IntegratedServer getSingleplayerServer() {
/* 2677 */     return this.singleplayerServer;
/*      */   }
/*      */   
/*      */   public boolean isSingleplayer() {
/* 2681 */     IntegratedServer $$0 = getSingleplayerServer();
/* 2682 */     return ($$0 != null && !$$0.isPublished());
/*      */   }
/*      */   
/*      */   public boolean isLocalPlayer(UUID $$0) {
/* 2686 */     return $$0.equals(getUser().getProfileId());
/*      */   }
/*      */   
/*      */   public User getUser() {
/* 2690 */     return this.user;
/*      */   }
/*      */   
/*      */   public GameProfile getGameProfile() {
/* 2694 */     ProfileResult $$0 = this.profileFuture.join();
/* 2695 */     if ($$0 != null) {
/* 2696 */       return $$0.profile();
/*      */     }
/* 2698 */     return new GameProfile(this.user.getProfileId(), this.user.getName());
/*      */   }
/*      */   
/*      */   public Proxy getProxy() {
/* 2702 */     return this.proxy;
/*      */   }
/*      */   
/*      */   public TextureManager getTextureManager() {
/* 2706 */     return this.textureManager;
/*      */   }
/*      */   
/*      */   public ResourceManager getResourceManager() {
/* 2710 */     return (ResourceManager)this.resourceManager;
/*      */   }
/*      */   
/*      */   public PackRepository getResourcePackRepository() {
/* 2714 */     return this.resourcePackRepository;
/*      */   }
/*      */   
/*      */   public VanillaPackResources getVanillaPackResources() {
/* 2718 */     return this.vanillaPackResources;
/*      */   }
/*      */   
/*      */   public DownloadedPackSource getDownloadedPackSource() {
/* 2722 */     return this.downloadedPackSource;
/*      */   }
/*      */   
/*      */   public Path getResourcePackDirectory() {
/* 2726 */     return this.resourcePackDirectory;
/*      */   }
/*      */   
/*      */   public LanguageManager getLanguageManager() {
/* 2730 */     return this.languageManager;
/*      */   }
/*      */   
/*      */   public Function<ResourceLocation, TextureAtlasSprite> getTextureAtlas(ResourceLocation $$0) {
/* 2734 */     Objects.requireNonNull(this.modelManager.getAtlas($$0)); return this.modelManager.getAtlas($$0)::getSprite;
/*      */   }
/*      */   
/*      */   public boolean is64Bit() {
/* 2738 */     return this.is64bit;
/*      */   }
/*      */   
/*      */   public boolean isPaused() {
/* 2742 */     return this.pause;
/*      */   }
/*      */   
/*      */   public GpuWarnlistManager getGpuWarnlistManager() {
/* 2746 */     return this.gpuWarnlistManager;
/*      */   }
/*      */   
/*      */   public SoundManager getSoundManager() {
/* 2750 */     return this.soundManager;
/*      */   }
/*      */   
/*      */   public Music getSituationalMusic() {
/* 2754 */     Music $$0 = (Music)Optionull.map(this.screen, Screen::getBackgroundMusic);
/* 2755 */     if ($$0 != null) {
/* 2756 */       return $$0;
/*      */     }
/* 2758 */     if (this.player != null) {
/* 2759 */       if (this.player.level().dimension() == Level.END) {
/* 2760 */         if (this.gui.getBossOverlay().shouldPlayMusic()) {
/* 2761 */           return Musics.END_BOSS;
/*      */         }
/* 2763 */         return Musics.END;
/*      */       } 
/*      */ 
/*      */       
/* 2767 */       Holder<Biome> $$1 = this.player.level().getBiome(this.player.blockPosition());
/* 2768 */       if (this.musicManager.isPlayingMusic(Musics.UNDER_WATER) || (this.player.isUnderWater() && $$1.is(BiomeTags.PLAYS_UNDERWATER_MUSIC))) {
/* 2769 */         return Musics.UNDER_WATER;
/*      */       }
/*      */       
/* 2772 */       if (this.player.level().dimension() != Level.NETHER && (this.player.getAbilities()).instabuild && (this.player.getAbilities()).mayfly) {
/* 2773 */         return Musics.CREATIVE;
/*      */       }
/*      */       
/* 2776 */       return ((Biome)$$1.value()).getBackgroundMusic().orElse(Musics.GAME);
/*      */     } 
/*      */     
/* 2779 */     return Musics.MENU;
/*      */   }
/*      */   
/*      */   public MinecraftSessionService getMinecraftSessionService() {
/* 2783 */     return this.minecraftSessionService;
/*      */   }
/*      */   
/*      */   public SkinManager getSkinManager() {
/* 2787 */     return this.skinManager;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public Entity getCameraEntity() {
/* 2792 */     return this.cameraEntity;
/*      */   }
/*      */   
/*      */   public void setCameraEntity(Entity $$0) {
/* 2796 */     this.cameraEntity = $$0;
/* 2797 */     this.gameRenderer.checkEntityPostEffect($$0);
/*      */   }
/*      */   
/*      */   public boolean shouldEntityAppearGlowing(Entity $$0) {
/* 2801 */     return ($$0.isCurrentlyGlowing() || (this.player != null && this.player.isSpectator() && this.options.keySpectatorOutlines.isDown() && $$0.getType() == EntityType.PLAYER));
/*      */   }
/*      */ 
/*      */   
/*      */   protected Thread getRunningThread() {
/* 2806 */     return this.gameThread;
/*      */   }
/*      */ 
/*      */   
/*      */   protected Runnable wrapRunnable(Runnable $$0) {
/* 2811 */     return $$0;
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean shouldRun(Runnable $$0) {
/* 2816 */     return true;
/*      */   }
/*      */   
/*      */   public BlockRenderDispatcher getBlockRenderer() {
/* 2820 */     return this.blockRenderer;
/*      */   }
/*      */   
/*      */   public EntityRenderDispatcher getEntityRenderDispatcher() {
/* 2824 */     return this.entityRenderDispatcher;
/*      */   }
/*      */   
/*      */   public BlockEntityRenderDispatcher getBlockEntityRenderDispatcher() {
/* 2828 */     return this.blockEntityRenderDispatcher;
/*      */   }
/*      */   
/*      */   public ItemRenderer getItemRenderer() {
/* 2832 */     return this.itemRenderer;
/*      */   }
/*      */   
/*      */   public <T> SearchTree<T> getSearchTree(SearchRegistry.Key<T> $$0) {
/* 2836 */     return this.searchRegistry.getTree($$0);
/*      */   }
/*      */   
/*      */   public <T> void populateSearchTree(SearchRegistry.Key<T> $$0, List<T> $$1) {
/* 2840 */     this.searchRegistry.populate($$0, $$1);
/*      */   }
/*      */   
/*      */   public DataFixer getFixerUpper() {
/* 2844 */     return this.fixerUpper;
/*      */   }
/*      */   
/*      */   public float getFrameTime() {
/* 2848 */     return this.timer.partialTick;
/*      */   }
/*      */   
/*      */   public float getDeltaFrameTime() {
/* 2852 */     return this.timer.tickDelta;
/*      */   }
/*      */   
/*      */   public BlockColors getBlockColors() {
/* 2856 */     return this.blockColors;
/*      */   }
/*      */   
/*      */   public boolean showOnlyReducedInfo() {
/* 2860 */     return ((this.player != null && this.player.isReducedDebugInfo()) || ((Boolean)this.options.reducedDebugInfo().get()).booleanValue());
/*      */   }
/*      */   
/*      */   public ToastComponent getToasts() {
/* 2864 */     return this.toast;
/*      */   }
/*      */   
/*      */   public Tutorial getTutorial() {
/* 2868 */     return this.tutorial;
/*      */   }
/*      */   
/*      */   public boolean isWindowActive() {
/* 2872 */     return this.windowActive;
/*      */   }
/*      */   
/*      */   public HotbarManager getHotbarManager() {
/* 2876 */     return this.hotbarManager;
/*      */   }
/*      */   
/*      */   public ModelManager getModelManager() {
/* 2880 */     return this.modelManager;
/*      */   }
/*      */   
/*      */   public PaintingTextureManager getPaintingTextures() {
/* 2884 */     return this.paintingTextures;
/*      */   }
/*      */   
/*      */   public MobEffectTextureManager getMobEffectTextures() {
/* 2888 */     return this.mobEffectTextures;
/*      */   }
/*      */   
/*      */   public GuiSpriteManager getGuiSprites() {
/* 2892 */     return this.guiSprites;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setWindowActive(boolean $$0) {
/* 2897 */     this.windowActive = $$0;
/*      */   }
/*      */   
/*      */   public Component grabPanoramixScreenshot(File $$0, int $$1, int $$2) {
/* 2901 */     int $$3 = this.window.getWidth();
/* 2902 */     int $$4 = this.window.getHeight();
/* 2903 */     TextureTarget textureTarget = new TextureTarget($$1, $$2, true, ON_OSX);
/*      */     
/* 2905 */     float $$6 = this.player.getXRot();
/* 2906 */     float $$7 = this.player.getYRot();
/* 2907 */     float $$8 = this.player.xRotO;
/* 2908 */     float $$9 = this.player.yRotO;
/* 2909 */     this.gameRenderer.setRenderBlockOutline(false);
/*      */     
/*      */     try {
/* 2912 */       this.gameRenderer.setPanoramicMode(true);
/* 2913 */       this.levelRenderer.graphicsChanged();
/*      */       
/* 2915 */       this.window.setWidth($$1);
/* 2916 */       this.window.setHeight($$2);
/* 2917 */       for (int $$10 = 0; $$10 < 6; $$10++) {
/* 2918 */         switch ($$10) {
/*      */           case 0:
/* 2920 */             this.player.setYRot($$7);
/* 2921 */             this.player.setXRot(0.0F);
/*      */             break;
/*      */           case 1:
/* 2924 */             this.player.setYRot(($$7 + 90.0F) % 360.0F);
/* 2925 */             this.player.setXRot(0.0F);
/*      */             break;
/*      */           case 2:
/* 2928 */             this.player.setYRot(($$7 + 180.0F) % 360.0F);
/* 2929 */             this.player.setXRot(0.0F);
/*      */             break;
/*      */           case 3:
/* 2932 */             this.player.setYRot(($$7 - 90.0F) % 360.0F);
/* 2933 */             this.player.setXRot(0.0F);
/*      */             break;
/*      */           case 4:
/* 2936 */             this.player.setYRot($$7);
/* 2937 */             this.player.setXRot(-90.0F);
/*      */             break;
/*      */           
/*      */           default:
/* 2941 */             this.player.setYRot($$7);
/* 2942 */             this.player.setXRot(90.0F);
/*      */             break;
/*      */         } 
/* 2945 */         this.player.yRotO = this.player.getYRot();
/* 2946 */         this.player.xRotO = this.player.getXRot();
/*      */         
/* 2948 */         textureTarget.bindWrite(true);
/* 2949 */         this.gameRenderer.renderLevel(1.0F, 0L, new PoseStack());
/*      */         try {
/* 2951 */           Thread.sleep(10L);
/* 2952 */         } catch (InterruptedException interruptedException) {}
/*      */         
/* 2954 */         Screenshot.grab($$0, "panorama_" + $$10 + ".png", (RenderTarget)textureTarget, $$0 -> {
/*      */             
/*      */             });
/* 2957 */       }  MutableComponent mutableComponent = Component.literal($$0.getName()).withStyle(ChatFormatting.UNDERLINE).withStyle($$1 -> $$1.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, $$0.getAbsolutePath())));
/*      */ 
/*      */       
/* 2960 */       return (Component)Component.translatable("screenshot.success", new Object[] { mutableComponent });
/* 2961 */     } catch (Exception $$12) {
/* 2962 */       LOGGER.error("Couldn't save image", $$12);
/* 2963 */       return (Component)Component.translatable("screenshot.failure", new Object[] { $$12.getMessage() });
/*      */     } finally {
/* 2965 */       this.player.setXRot($$6);
/* 2966 */       this.player.setYRot($$7);
/* 2967 */       this.player.xRotO = $$8;
/* 2968 */       this.player.yRotO = $$9;
/*      */       
/* 2970 */       this.gameRenderer.setRenderBlockOutline(true);
/*      */       
/* 2972 */       this.window.setWidth($$3);
/* 2973 */       this.window.setHeight($$4);
/* 2974 */       textureTarget.destroyBuffers();
/*      */       
/* 2976 */       this.gameRenderer.setPanoramicMode(false);
/* 2977 */       this.levelRenderer.graphicsChanged();
/*      */       
/* 2979 */       getMainRenderTarget().bindWrite(true);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Component grabHugeScreenshot(File $$0, int $$1, int $$2, int $$3, int $$4) {
/*      */     try {
/* 2989 */       ByteBuffer $$5 = GlUtil.allocateMemory($$1 * $$2 * 3);
/*      */       
/* 2991 */       Screenshot $$6 = new Screenshot($$0, $$3, $$4, $$2);
/*      */       
/* 2993 */       float $$7 = $$3 / $$1;
/* 2994 */       float $$8 = $$4 / $$2;
/* 2995 */       float $$9 = ($$7 > $$8) ? $$7 : $$8;
/*      */       int $$10;
/* 2997 */       for ($$10 = ($$4 - 1) / $$2 * $$2; $$10 >= 0; $$10 -= $$2) {
/* 2998 */         int $$11; for ($$11 = 0; $$11 < $$3; $$11 += $$1) {
/* 2999 */           RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_BLOCKS);
/*      */           
/* 3001 */           float $$12 = ($$3 - $$1) / 2.0F * 2.0F - ($$11 * 2);
/* 3002 */           float $$13 = ($$4 - $$2) / 2.0F * 2.0F - ($$10 * 2);
/* 3003 */           $$12 /= $$1;
/* 3004 */           $$13 /= $$2;
/* 3005 */           this.gameRenderer.renderZoomed($$9, $$12, $$13);
/* 3006 */           $$5.clear();
/* 3007 */           RenderSystem.pixelStore(3333, 1);
/* 3008 */           RenderSystem.pixelStore(3317, 1);
/* 3009 */           RenderSystem.readPixels(0, 0, $$1, $$2, 32992, 5121, $$5);
/* 3010 */           $$6.addRegion($$5, $$11, $$10, $$1, $$2);
/*      */         } 
/* 3012 */         $$6.saveRow();
/*      */       } 
/*      */       
/* 3015 */       File $$14 = $$6.close();
/* 3016 */       GlUtil.freeMemory($$5);
/* 3017 */       MutableComponent mutableComponent = Component.literal($$14.getName()).withStyle(ChatFormatting.UNDERLINE).withStyle($$1 -> $$1.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, $$0.getAbsolutePath())));
/*      */ 
/*      */       
/* 3020 */       return (Component)Component.translatable("screenshot.success", new Object[] { mutableComponent });
/* 3021 */     } catch (Exception $$16) {
/* 3022 */       LOGGER.warn("Couldn't save screenshot", $$16);
/* 3023 */       return (Component)Component.translatable("screenshot.failure", new Object[] { $$16.getMessage() });
/*      */     } 
/*      */   }
/*      */   
/*      */   public ProfilerFiller getProfiler() {
/* 3028 */     return this.profiler;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public StoringChunkProgressListener getProgressListener() {
/* 3033 */     return this.progressListener.get();
/*      */   }
/*      */   
/*      */   public SplashManager getSplashManager() {
/* 3037 */     return this.splashManager;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public Overlay getOverlay() {
/* 3042 */     return this.overlay;
/*      */   }
/*      */   
/*      */   public PlayerSocialManager getPlayerSocialManager() {
/* 3046 */     return this.playerSocialManager;
/*      */   }
/*      */   
/*      */   public boolean renderOnThread() {
/* 3050 */     return false;
/*      */   }
/*      */   
/*      */   public Window getWindow() {
/* 3054 */     return this.window;
/*      */   }
/*      */   
/*      */   public DebugScreenOverlay getDebugOverlay() {
/* 3058 */     return this.gui.getDebugOverlay();
/*      */   }
/*      */   
/*      */   public RenderBuffers renderBuffers() {
/* 3062 */     return this.renderBuffers;
/*      */   }
/*      */   
/*      */   public void updateMaxMipLevel(int $$0) {
/* 3066 */     this.modelManager.updateMaxMipLevel($$0);
/*      */   }
/*      */   
/*      */   public EntityModelSet getEntityModels() {
/* 3070 */     return this.entityModels;
/*      */   }
/*      */   
/*      */   public boolean isTextFilteringEnabled() {
/* 3074 */     return userProperties().flag(UserApiService.UserFlag.PROFANITY_FILTER_ENABLED);
/*      */   }
/*      */   
/*      */   public void prepareForMultiplayer() {
/* 3078 */     this.playerSocialManager.startOnlineMode();
/*      */     
/* 3080 */     getProfileKeyPairManager().prepareKeyPair();
/*      */   }
/*      */   
/*      */   public Realms32BitWarningStatus getRealms32BitWarningStatus() {
/* 3084 */     return this.realms32BitWarningStatus;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public SignatureValidator getProfileKeySignatureValidator() {
/* 3089 */     return SignatureValidator.from(this.authenticationService.getServicesKeySet(), ServicesKeyType.PROFILE_KEY);
/*      */   }
/*      */   
/*      */   public boolean canValidateProfileKeys() {
/* 3093 */     return !this.authenticationService.getServicesKeySet().keys(ServicesKeyType.PROFILE_KEY).isEmpty();
/*      */   }
/*      */   
/*      */   public InputType getLastInputType() {
/* 3097 */     return this.lastInputType;
/*      */   }
/*      */   
/*      */   public void setLastInputType(InputType $$0) {
/* 3101 */     this.lastInputType = $$0;
/*      */   }
/*      */   
/*      */   public GameNarrator getNarrator() {
/* 3105 */     return this.narrator;
/*      */   }
/*      */   
/*      */   public ChatListener getChatListener() {
/* 3109 */     return this.chatListener;
/*      */   }
/*      */   
/*      */   public ReportingContext getReportingContext() {
/* 3113 */     return this.reportingContext;
/*      */   }
/*      */   
/*      */   public RealmsDataFetcher realmsDataFetcher() {
/* 3117 */     return this.realmsDataFetcher;
/*      */   }
/*      */   
/*      */   public QuickPlayLog quickPlayLog() {
/* 3121 */     return this.quickPlayLog;
/*      */   }
/*      */   
/*      */   public CommandHistory commandHistory() {
/* 3125 */     return this.commandHistory;
/*      */   }
/*      */   
/*      */   public DirectoryValidator directoryValidator() {
/* 3129 */     return this.directoryValidator;
/*      */   }
/*      */   
/*      */   private float getTickTargetMillis(float $$0) {
/* 3133 */     if (this.level != null) {
/* 3134 */       TickRateManager $$1 = this.level.tickRateManager();
/* 3135 */       if ($$1.runsNormally()) {
/* 3136 */         return Math.max($$0, $$1.millisecondsPerTick());
/*      */       }
/*      */     } 
/* 3139 */     return $$0;
/*      */   }
/*      */   
/*      */   public enum ChatStatus {
/* 3143 */     ENABLED((String)CommonComponents.EMPTY)
/*      */     {
/*      */       public boolean isChatAllowed(boolean $$0) {
/* 3146 */         return true;
/*      */       }
/*      */     },
/* 3149 */     DISABLED_BY_OPTIONS((String)Component.translatable("chat.disabled.options").withStyle(ChatFormatting.RED))
/*      */     {
/*      */       public boolean isChatAllowed(boolean $$0) {
/* 3152 */         return false;
/*      */       }
/*      */     },
/* 3155 */     DISABLED_BY_LAUNCHER((String)Component.translatable("chat.disabled.launcher").withStyle(ChatFormatting.RED))
/*      */     {
/*      */       public boolean isChatAllowed(boolean $$0) {
/* 3158 */         return $$0;
/*      */       }
/*      */     },
/* 3161 */     DISABLED_BY_PROFILE((String)Component.translatable("chat.disabled.profile", new Object[] { Component.keybind(Minecraft.instance.options.keyChat.getName()) }).withStyle(ChatFormatting.RED))
/*      */     {
/*      */       public boolean isChatAllowed(boolean $$0) {
/* 3164 */         return $$0;
/*      */       }
/*      */     };
/*      */ 
/*      */     
/* 3169 */     static final Component INFO_DISABLED_BY_PROFILE = (Component)Component.translatable("chat.disabled.profile.moreInfo"); private final Component message;
/*      */     static {
/*      */     
/*      */     }
/*      */     ChatStatus(Component $$0) {
/* 3174 */       this.message = $$0;
/*      */     }
/*      */     
/*      */     public Component getMessage() {
/* 3178 */       return this.message;
/*      */     } public abstract boolean isChatAllowed(boolean param1Boolean);
/*      */   } enum null { public boolean isChatAllowed(boolean $$0) { return true; } } enum null { public boolean isChatAllowed(boolean $$0) { return false; } }
/*      */   enum null { public boolean isChatAllowed(boolean $$0) { return $$0; } }
/*      */   enum null { public boolean isChatAllowed(boolean $$0) { return $$0; } }
/*      */   private static final class GameLoadCookie extends Record { private final RealmsClient realmsClient; private final GameConfig.QuickPlayData quickPlayData;
/* 3184 */     GameLoadCookie(RealmsClient $$0, GameConfig.QuickPlayData $$1) { this.realmsClient = $$0; this.quickPlayData = $$1; } public final String toString() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> toString : (Lnet/minecraft/client/Minecraft$GameLoadCookie;)Ljava/lang/String;
/*      */       //   6: areturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #3184	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lnet/minecraft/client/Minecraft$GameLoadCookie; } public final int hashCode() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/Minecraft$GameLoadCookie;)I
/*      */       //   6: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #3184	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lnet/minecraft/client/Minecraft$GameLoadCookie; } public final boolean equals(Object $$0) { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: aload_1
/*      */       //   2: <illegal opcode> equals : (Lnet/minecraft/client/Minecraft$GameLoadCookie;Ljava/lang/Object;)Z
/*      */       //   7: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #3184	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	8	0	this	Lnet/minecraft/client/Minecraft$GameLoadCookie;
/* 3184 */       //   0	8	1	$$0	Ljava/lang/Object; } public RealmsClient realmsClient() { return this.realmsClient; } public GameConfig.QuickPlayData quickPlayData() { return this.quickPlayData; }
/*      */      }
/*      */   
/*      */   @Nullable
/*      */   public static String getLauncherBrand() {
/* 3189 */     return System.getProperty("minecraft.launcher.brand");
/*      */   }
/*      */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\Minecraft.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */