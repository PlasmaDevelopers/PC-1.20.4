/*      */ package net.minecraft.client.multiplayer;
/*      */ import com.google.common.collect.Lists;
/*      */ import com.google.common.collect.Maps;
/*      */ import com.google.common.collect.Sets;
/*      */ import com.mojang.authlib.GameProfile;
/*      */ import com.mojang.brigadier.CommandDispatcher;
/*      */ import com.mojang.brigadier.ParseResults;
/*      */ import com.mojang.datafixers.util.Pair;
/*      */ import com.mojang.logging.LogUtils;
/*      */ import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
/*      */ import java.time.Instant;
/*      */ import java.util.BitSet;
/*      */ import java.util.Collection;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Objects;
/*      */ import java.util.Optional;
/*      */ import java.util.Set;
/*      */ import java.util.UUID;
/*      */ import java.util.concurrent.Executor;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.advancements.AdvancementHolder;
/*      */ import net.minecraft.client.ClientRecipeBook;
/*      */ import net.minecraft.client.DebugQueryHandler;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.Options;
/*      */ import net.minecraft.client.gui.MapRenderer;
/*      */ import net.minecraft.client.gui.components.toasts.RecipeToast;
/*      */ import net.minecraft.client.gui.components.toasts.SystemToast;
/*      */ import net.minecraft.client.gui.components.toasts.Toast;
/*      */ import net.minecraft.client.gui.screens.DeathScreen;
/*      */ import net.minecraft.client.gui.screens.DemoIntroScreen;
/*      */ import net.minecraft.client.gui.screens.ReceivingLevelScreen;
/*      */ import net.minecraft.client.gui.screens.Screen;
/*      */ import net.minecraft.client.gui.screens.WinScreen;
/*      */ import net.minecraft.client.gui.screens.inventory.BookViewScreen;
/*      */ import net.minecraft.client.gui.screens.inventory.CommandBlockEditScreen;
/*      */ import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
/*      */ import net.minecraft.client.gui.screens.multiplayer.ServerReconfigScreen;
/*      */ import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
/*      */ import net.minecraft.client.gui.screens.recipebook.RecipeCollection;
/*      */ import net.minecraft.client.gui.screens.recipebook.RecipeUpdateListener;
/*      */ import net.minecraft.client.particle.ItemPickupParticle;
/*      */ import net.minecraft.client.particle.Particle;
/*      */ import net.minecraft.client.player.Input;
/*      */ import net.minecraft.client.player.KeyboardInput;
/*      */ import net.minecraft.client.player.LocalPlayer;
/*      */ import net.minecraft.client.player.RemotePlayer;
/*      */ import net.minecraft.client.renderer.debug.BrainDebugRenderer;
/*      */ import net.minecraft.client.renderer.debug.NeighborsUpdateRenderer;
/*      */ import net.minecraft.client.renderer.debug.VillageSectionsDebugRenderer;
/*      */ import net.minecraft.client.renderer.debug.WorldGenAttemptRenderer;
/*      */ import net.minecraft.client.resources.sounds.BeeAggressiveSoundInstance;
/*      */ import net.minecraft.client.resources.sounds.BeeFlyingSoundInstance;
/*      */ import net.minecraft.client.resources.sounds.MinecartSoundInstance;
/*      */ import net.minecraft.client.resources.sounds.SnifferSoundInstance;
/*      */ import net.minecraft.client.resources.sounds.SoundInstance;
/*      */ import net.minecraft.client.resources.sounds.TickableSoundInstance;
/*      */ import net.minecraft.client.searchtree.SearchRegistry;
/*      */ import net.minecraft.commands.CommandBuildContext;
/*      */ import net.minecraft.commands.SharedSuggestionProvider;
/*      */ import net.minecraft.commands.arguments.ArgumentSignatures;
/*      */ import net.minecraft.core.BlockPos;
/*      */ import net.minecraft.core.Holder;
/*      */ import net.minecraft.core.RegistryAccess;
/*      */ import net.minecraft.core.SectionPos;
/*      */ import net.minecraft.core.particles.ParticleOptions;
/*      */ import net.minecraft.core.particles.ParticleTypes;
/*      */ import net.minecraft.core.registries.BuiltInRegistries;
/*      */ import net.minecraft.core.registries.Registries;
/*      */ import net.minecraft.nbt.CompoundTag;
/*      */ import net.minecraft.network.Connection;
/*      */ import net.minecraft.network.PacketListener;
/*      */ import net.minecraft.network.TickablePacketListener;
/*      */ import net.minecraft.network.chat.ChatType;
/*      */ import net.minecraft.network.chat.Component;
/*      */ import net.minecraft.network.chat.LastSeenMessagesTracker;
/*      */ import net.minecraft.network.chat.LocalChatSession;
/*      */ import net.minecraft.network.chat.MessageSignature;
/*      */ import net.minecraft.network.chat.MessageSignatureCache;
/*      */ import net.minecraft.network.chat.MutableComponent;
/*      */ import net.minecraft.network.chat.PlayerChatMessage;
/*      */ import net.minecraft.network.chat.RemoteChatSession;
/*      */ import net.minecraft.network.chat.SignableCommand;
/*      */ import net.minecraft.network.chat.SignedMessageBody;
/*      */ import net.minecraft.network.chat.SignedMessageChain;
/*      */ import net.minecraft.network.chat.SignedMessageLink;
/*      */ import net.minecraft.network.protocol.Packet;
/*      */ import net.minecraft.network.protocol.PacketUtils;
/*      */ import net.minecraft.network.protocol.common.ClientboundUpdateTagsPacket;
/*      */ import net.minecraft.network.protocol.common.custom.BeeDebugPayload;
/*      */ import net.minecraft.network.protocol.common.custom.BrainDebugPayload;
/*      */ import net.minecraft.network.protocol.common.custom.BreezeDebugPayload;
/*      */ import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
/*      */ import net.minecraft.network.protocol.common.custom.GameEventDebugPayload;
/*      */ import net.minecraft.network.protocol.common.custom.GameEventListenerDebugPayload;
/*      */ import net.minecraft.network.protocol.common.custom.GameTestAddMarkerDebugPayload;
/*      */ import net.minecraft.network.protocol.common.custom.GoalDebugPayload;
/*      */ import net.minecraft.network.protocol.common.custom.HiveDebugPayload;
/*      */ import net.minecraft.network.protocol.common.custom.NeighborUpdatesDebugPayload;
/*      */ import net.minecraft.network.protocol.common.custom.PathfindingDebugPayload;
/*      */ import net.minecraft.network.protocol.common.custom.PoiAddedDebugPayload;
/*      */ import net.minecraft.network.protocol.common.custom.PoiRemovedDebugPayload;
/*      */ import net.minecraft.network.protocol.common.custom.PoiTicketCountDebugPayload;
/*      */ import net.minecraft.network.protocol.common.custom.RaidsDebugPayload;
/*      */ import net.minecraft.network.protocol.common.custom.StructuresDebugPayload;
/*      */ import net.minecraft.network.protocol.common.custom.VillageSectionsDebugPayload;
/*      */ import net.minecraft.network.protocol.common.custom.WorldGenAttemptDebugPayload;
/*      */ import net.minecraft.network.protocol.game.ClientGamePacketListener;
/*      */ import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundAddExperienceOrbPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundAwardStatsPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundBlockChangedAckPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundBlockDestructionPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundBlockEventPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundBossEventPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundBundlePacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundChangeDifficultyPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundChunkBatchFinishedPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundChunkBatchStartPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundChunksBiomesPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundClearTitlesPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundCommandSuggestionsPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundCommandsPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundContainerClosePacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundContainerSetContentPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundContainerSetDataPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundCooldownPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundCustomChatCompletionsPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundDamageEventPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundDeleteChatPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundDisguisedChatPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundEntityEventPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundExplodePacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundForgetLevelChunkPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundHorseScreenOpenPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundHurtAnimationPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundInitializeBorderPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundLevelChunkPacketData;
/*      */ import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundLevelEventPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundLightUpdatePacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundLightUpdatePacketData;
/*      */ import net.minecraft.network.protocol.game.ClientboundLoginPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundMapItemDataPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundMerchantOffersPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundMoveEntityPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundMoveVehiclePacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundOpenBookPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundOpenScreenPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundOpenSignEditorPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundPlaceGhostRecipePacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundPlayerAbilitiesPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundPlayerChatPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundPlayerCombatEndPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundPlayerCombatEnterPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundPlayerCombatKillPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundPlayerInfoRemovePacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundPlayerLookAtPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundPlayerPositionPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundRecipePacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundRemoveMobEffectPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundResetScorePacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundRespawnPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundRotateHeadPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundSectionBlocksUpdatePacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundSelectAdvancementsTabPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundServerDataPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundSetBorderCenterPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundSetBorderLerpSizePacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundSetBorderSizePacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundSetBorderWarningDelayPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundSetBorderWarningDistancePacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundSetCameraPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundSetCarriedItemPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundSetChunkCacheCenterPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundSetChunkCacheRadiusPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundSetDefaultSpawnPositionPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundSetDisplayObjectivePacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundSetEntityLinkPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundSetEquipmentPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundSetExperiencePacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundSetHealthPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundSetObjectivePacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundSetPassengersPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundSetPlayerTeamPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundSetScorePacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundSetSimulationDistancePacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundSetTimePacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundSetTitlesAnimationPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundSoundEntityPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundSoundPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundStartConfigurationPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundStopSoundPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundSystemChatPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundTabListPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundTagQueryPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundTakeItemEntityPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundTeleportEntityPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundTickingStatePacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundTickingStepPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundUpdateAdvancementsPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundUpdateAttributesPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundUpdateRecipesPacket;
/*      */ import net.minecraft.network.protocol.game.CommonPlayerSpawnInfo;
/*      */ import net.minecraft.network.protocol.game.ServerboundAcceptTeleportationPacket;
/*      */ import net.minecraft.network.protocol.game.ServerboundChatCommandPacket;
/*      */ import net.minecraft.network.protocol.game.ServerboundChatPacket;
/*      */ import net.minecraft.network.protocol.game.ServerboundChatSessionUpdatePacket;
/*      */ import net.minecraft.network.protocol.game.ServerboundChunkBatchReceivedPacket;
/*      */ import net.minecraft.network.protocol.game.ServerboundClientCommandPacket;
/*      */ import net.minecraft.network.protocol.game.ServerboundConfigurationAcknowledgedPacket;
/*      */ import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
/*      */ import net.minecraft.network.protocol.game.ServerboundMoveVehiclePacket;
/*      */ import net.minecraft.network.protocol.game.VecDeltaCodec;
/*      */ import net.minecraft.network.protocol.status.ClientboundPongResponsePacket;
/*      */ import net.minecraft.network.syncher.SynchedEntityData;
/*      */ import net.minecraft.resources.ResourceKey;
/*      */ import net.minecraft.resources.ResourceLocation;
/*      */ import net.minecraft.sounds.SoundEvents;
/*      */ import net.minecraft.sounds.SoundSource;
/*      */ import net.minecraft.stats.RecipeBook;
/*      */ import net.minecraft.stats.Stat;
/*      */ import net.minecraft.stats.StatsCounter;
/*      */ import net.minecraft.util.Crypt;
/*      */ import net.minecraft.util.Mth;
/*      */ import net.minecraft.util.RandomSource;
/*      */ import net.minecraft.util.SignatureValidator;
/*      */ import net.minecraft.util.thread.BlockableEventLoop;
/*      */ import net.minecraft.world.Container;
/*      */ import net.minecraft.world.Difficulty;
/*      */ import net.minecraft.world.InteractionHand;
/*      */ import net.minecraft.world.SimpleContainer;
/*      */ import net.minecraft.world.TickRateManager;
/*      */ import net.minecraft.world.effect.MobEffect;
/*      */ import net.minecraft.world.effect.MobEffectInstance;
/*      */ import net.minecraft.world.entity.Entity;
/*      */ import net.minecraft.world.entity.EntityType;
/*      */ import net.minecraft.world.entity.EquipmentSlot;
/*      */ import net.minecraft.world.entity.ExperienceOrb;
/*      */ import net.minecraft.world.entity.LivingEntity;
/*      */ import net.minecraft.world.entity.Mob;
/*      */ import net.minecraft.world.entity.RelativeMovement;
/*      */ import net.minecraft.world.entity.ai.attributes.AttributeInstance;
/*      */ import net.minecraft.world.entity.ai.attributes.AttributeMap;
/*      */ import net.minecraft.world.entity.ai.attributes.AttributeModifier;
/*      */ import net.minecraft.world.entity.animal.Bee;
/*      */ import net.minecraft.world.entity.animal.horse.AbstractHorse;
/*      */ import net.minecraft.world.entity.animal.sniffer.Sniffer;
/*      */ import net.minecraft.world.entity.item.ItemEntity;
/*      */ import net.minecraft.world.entity.monster.Guardian;
/*      */ import net.minecraft.world.entity.player.Inventory;
/*      */ import net.minecraft.world.entity.player.Player;
/*      */ import net.minecraft.world.entity.player.ProfileKeyPair;
/*      */ import net.minecraft.world.entity.player.ProfilePublicKey;
/*      */ import net.minecraft.world.entity.vehicle.AbstractMinecart;
/*      */ import net.minecraft.world.flag.FeatureFlagSet;
/*      */ import net.minecraft.world.inventory.AbstractContainerMenu;
/*      */ import net.minecraft.world.inventory.HorseInventoryMenu;
/*      */ import net.minecraft.world.inventory.InventoryMenu;
/*      */ import net.minecraft.world.inventory.MerchantMenu;
/*      */ import net.minecraft.world.item.CreativeModeTabs;
/*      */ import net.minecraft.world.item.ItemStack;
/*      */ import net.minecraft.world.item.Items;
/*      */ import net.minecraft.world.item.MapItem;
/*      */ import net.minecraft.world.item.crafting.RecipeHolder;
/*      */ import net.minecraft.world.item.crafting.RecipeManager;
/*      */ import net.minecraft.world.level.ChunkPos;
/*      */ import net.minecraft.world.level.Explosion;
/*      */ import net.minecraft.world.level.ItemLike;
/*      */ import net.minecraft.world.level.Level;
/*      */ import net.minecraft.world.level.LightLayer;
/*      */ import net.minecraft.world.level.block.Blocks;
/*      */ import net.minecraft.world.level.block.entity.BlockEntity;
/*      */ import net.minecraft.world.level.block.entity.SignBlockEntity;
/*      */ import net.minecraft.world.level.block.state.BlockState;
/*      */ import net.minecraft.world.level.border.WorldBorder;
/*      */ import net.minecraft.world.level.chunk.DataLayer;
/*      */ import net.minecraft.world.level.chunk.LevelChunk;
/*      */ import net.minecraft.world.level.chunk.LevelChunkSection;
/*      */ import net.minecraft.world.level.dimension.DimensionType;
/*      */ import net.minecraft.world.level.lighting.LevelLightEngine;
/*      */ import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
/*      */ import net.minecraft.world.phys.Vec3;
/*      */ import net.minecraft.world.scores.Objective;
/*      */ import net.minecraft.world.scores.PlayerTeam;
/*      */ import net.minecraft.world.scores.ScoreAccess;
/*      */ import net.minecraft.world.scores.ScoreHolder;
/*      */ import net.minecraft.world.scores.Scoreboard;
/*      */ import net.minecraft.world.scores.Team;
/*      */ import net.minecraft.world.scores.criteria.ObjectiveCriteria;
/*      */ 
/*      */ public class ClientPacketListener extends ClientCommonPacketListenerImpl implements TickablePacketListener, ClientGamePacketListener {
/*  309 */   private static final Logger LOGGER = LogUtils.getLogger();
/*      */   
/*  311 */   private static final Component UNSECURE_SERVER_TOAST_TITLE = (Component)Component.translatable("multiplayer.unsecureserver.toast.title");
/*  312 */   private static final Component UNSERURE_SERVER_TOAST = (Component)Component.translatable("multiplayer.unsecureserver.toast");
/*  313 */   private static final Component INVALID_PACKET = (Component)Component.translatable("multiplayer.disconnect.invalid_packet");
/*  314 */   private static final Component CHAT_VALIDATION_FAILED_ERROR = (Component)Component.translatable("multiplayer.disconnect.chat_validation_failed");
/*  315 */   private static final Component RECONFIGURE_SCREEN_MESSAGE = (Component)Component.translatable("connect.reconfiguring");
/*      */   
/*      */   private static final int PENDING_OFFSET_THRESHOLD = 64;
/*      */   
/*      */   private final GameProfile localGameProfile;
/*      */   
/*      */   private ClientLevel level;
/*      */   
/*      */   private ClientLevel.ClientLevelData levelData;
/*  324 */   private final Map<UUID, PlayerInfo> playerInfoMap = Maps.newHashMap();
/*  325 */   private final Set<PlayerInfo> listedPlayers = (Set<PlayerInfo>)new ReferenceOpenHashSet();
/*      */   private final ClientAdvancements advancements;
/*      */   private final ClientSuggestionProvider suggestionsProvider;
/*  328 */   private final DebugQueryHandler debugQueryHandler = new DebugQueryHandler(this);
/*  329 */   private int serverChunkRadius = 3;
/*  330 */   private int serverSimulationDistance = 3;
/*      */ 
/*      */   
/*  333 */   private final RandomSource random = RandomSource.createThreadSafe();
/*  334 */   private CommandDispatcher<SharedSuggestionProvider> commands = new CommandDispatcher();
/*  335 */   private final RecipeManager recipeManager = new RecipeManager();
/*  336 */   private final UUID id = UUID.randomUUID();
/*      */   
/*      */   private Set<ResourceKey<Level>> levels;
/*      */   
/*      */   private final RegistryAccess.Frozen registryAccess;
/*      */   private final FeatureFlagSet enabledFeatures;
/*      */   @Nullable
/*      */   private LocalChatSession chatSession;
/*  344 */   private SignedMessageChain.Encoder signedMessageEncoder = SignedMessageChain.Encoder.UNSIGNED;
/*      */   
/*  346 */   private LastSeenMessagesTracker lastSeenMessages = new LastSeenMessagesTracker(20);
/*      */   
/*  348 */   private MessageSignatureCache messageSignatureCache = MessageSignatureCache.createDefault();
/*  349 */   private final ChunkBatchSizeCalculator chunkBatchSizeCalculator = new ChunkBatchSizeCalculator();
/*      */   
/*      */   private final PingDebugMonitor pingDebugMonitor;
/*      */   
/*      */   @Nullable
/*      */   private LevelLoadStatusManager levelLoadStatusManager;
/*      */   private boolean seenInsecureChatWarning = false;
/*      */   private volatile boolean closed;
/*      */   
/*      */   public ClientPacketListener(Minecraft $$0, Connection $$1, CommonListenerCookie $$2) {
/*  359 */     super($$0, $$1, $$2);
/*  360 */     this.localGameProfile = $$2.localGameProfile();
/*  361 */     this.registryAccess = $$2.receivedRegistries();
/*  362 */     this.enabledFeatures = $$2.enabledFeatures();
/*  363 */     this.advancements = new ClientAdvancements($$0, this.telemetryManager);
/*  364 */     this.suggestionsProvider = new ClientSuggestionProvider(this, $$0);
/*  365 */     this.pingDebugMonitor = new PingDebugMonitor(this, $$0.getDebugOverlay().getPingLogger());
/*      */   }
/*      */   
/*      */   public ClientSuggestionProvider getSuggestionsProvider() {
/*  369 */     return this.suggestionsProvider;
/*      */   }
/*      */   
/*      */   public void close() {
/*  373 */     this.closed = true;
/*  374 */     clearLevel();
/*  375 */     this.telemetryManager.onDisconnect();
/*      */   }
/*      */   
/*      */   public void clearLevel() {
/*  379 */     this.level = null;
/*  380 */     this.levelLoadStatusManager = null;
/*      */   }
/*      */   
/*      */   public RecipeManager getRecipeManager() {
/*  384 */     return this.recipeManager;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleLogin(ClientboundLoginPacket $$0) {
/*  391 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/*      */ 
/*      */     
/*  394 */     refreshTagDependentData();
/*      */     
/*  396 */     this.minecraft.gameMode = new MultiPlayerGameMode(this.minecraft, this);
/*  397 */     CommonPlayerSpawnInfo $$1 = $$0.commonPlayerSpawnInfo();
/*      */     
/*  399 */     List<ResourceKey<Level>> $$2 = Lists.newArrayList($$0.levels());
/*  400 */     Collections.shuffle($$2);
/*      */     
/*  402 */     this.levels = Sets.newLinkedHashSet($$2);
/*  403 */     ResourceKey<Level> $$3 = $$1.dimension();
/*  404 */     Holder.Reference reference = this.registryAccess.registryOrThrow(Registries.DIMENSION_TYPE).getHolderOrThrow($$1.dimensionType());
/*      */     
/*  406 */     this.serverChunkRadius = $$0.chunkRadius();
/*  407 */     this.serverSimulationDistance = $$0.simulationDistance();
/*      */     
/*  409 */     boolean $$5 = $$1.isDebug();
/*  410 */     boolean $$6 = $$1.isFlat();
/*  411 */     ClientLevel.ClientLevelData $$7 = new ClientLevel.ClientLevelData(Difficulty.NORMAL, $$0.hardcore(), $$6);
/*  412 */     this.levelData = $$7;
/*  413 */     Objects.requireNonNull(this.minecraft); this.level = new ClientLevel(this, $$7, $$3, (Holder<DimensionType>)reference, this.serverChunkRadius, this.serverSimulationDistance, this.minecraft::getProfiler, this.minecraft.levelRenderer, $$5, $$1.seed());
/*  414 */     this.minecraft.setLevel(this.level);
/*      */ 
/*      */     
/*  417 */     if (this.minecraft.player == null) {
/*  418 */       this.minecraft.player = this.minecraft.gameMode.createPlayer(this.level, new StatsCounter(), new ClientRecipeBook());
/*  419 */       this.minecraft.player.setYRot(-180.0F);
/*  420 */       if (this.minecraft.getSingleplayerServer() != null) {
/*  421 */         this.minecraft.getSingleplayerServer().setUUID(this.minecraft.player.getUUID());
/*      */       }
/*      */     } 
/*      */     
/*  425 */     this.minecraft.debugRenderer.clear();
/*      */     
/*  427 */     this.minecraft.player.resetPos();
/*  428 */     this.minecraft.player.setId($$0.playerId());
/*  429 */     this.level.addEntity((Entity)this.minecraft.player);
/*  430 */     this.minecraft.player.input = (Input)new KeyboardInput(this.minecraft.options);
/*  431 */     this.minecraft.gameMode.adjustPlayer((Player)this.minecraft.player);
/*  432 */     this.minecraft.cameraEntity = (Entity)this.minecraft.player;
/*  433 */     startWaitingForNewLevel(this.minecraft.player, this.level);
/*  434 */     this.minecraft.player.setReducedDebugInfo($$0.reducedDebugInfo());
/*  435 */     this.minecraft.player.setShowDeathScreen($$0.showDeathScreen());
/*  436 */     this.minecraft.player.setDoLimitedCrafting($$0.doLimitedCrafting());
/*  437 */     this.minecraft.player.setLastDeathLocation($$1.lastDeathLocation());
/*  438 */     this.minecraft.player.setPortalCooldown($$1.portalCooldown());
/*  439 */     this.minecraft.gameMode.setLocalMode($$1.gameType(), $$1.previousGameType());
/*  440 */     this.minecraft.options.setServerRenderDistance($$0.chunkRadius());
/*      */ 
/*      */ 
/*      */     
/*  444 */     this.chatSession = null;
/*  445 */     this.lastSeenMessages = new LastSeenMessagesTracker(20);
/*  446 */     this.messageSignatureCache = MessageSignatureCache.createDefault();
/*  447 */     if (this.connection.isEncrypted()) {
/*  448 */       this.minecraft.getProfileKeyPairManager().prepareKeyPair().thenAcceptAsync($$0 -> $$0.ifPresent(this::setKeyPair), (Executor)this.minecraft);
/*      */     }
/*      */     
/*  451 */     this.telemetryManager.onPlayerInfoReceived($$1.gameType(), $$0.hardcore());
/*  452 */     this.minecraft.quickPlayLog().log(this.minecraft);
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleAddEntity(ClientboundAddEntityPacket $$0) {
/*  457 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/*      */     
/*  459 */     Entity $$1 = createEntityFromPacket($$0);
/*  460 */     if ($$1 != null) {
/*  461 */       $$1.recreateFromPacket($$0);
/*  462 */       this.level.addEntity($$1);
/*      */       
/*  464 */       postAddEntitySoundInstance($$1);
/*      */     } else {
/*  466 */       LOGGER.warn("Skipping Entity with id {}", $$0.getType());
/*      */     } 
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   private Entity createEntityFromPacket(ClientboundAddEntityPacket $$0) {
/*  472 */     EntityType<?> $$1 = $$0.getType();
/*  473 */     if ($$1 == EntityType.PLAYER) {
/*  474 */       PlayerInfo $$2 = getPlayerInfo($$0.getUUID());
/*  475 */       if ($$2 == null) {
/*  476 */         LOGGER.warn("Server attempted to add player prior to sending player info (Player id {})", $$0.getUUID());
/*  477 */         return null;
/*      */       } 
/*  479 */       return (Entity)new RemotePlayer(this.level, $$2.getProfile());
/*      */     } 
/*  481 */     return $$1.create(this.level);
/*      */   }
/*      */   
/*      */   private void postAddEntitySoundInstance(Entity $$0) {
/*  485 */     if ($$0 instanceof AbstractMinecart) { AbstractMinecart $$1 = (AbstractMinecart)$$0;
/*  486 */       this.minecraft.getSoundManager().play((SoundInstance)new MinecartSoundInstance($$1)); }
/*  487 */     else if ($$0 instanceof Bee) { BeeFlyingSoundInstance beeFlyingSoundInstance; Bee $$2 = (Bee)$$0;
/*  488 */       boolean $$3 = $$2.isAngry();
/*      */       
/*  490 */       if ($$3) {
/*  491 */         BeeAggressiveSoundInstance beeAggressiveSoundInstance = new BeeAggressiveSoundInstance($$2);
/*      */       } else {
/*  493 */         beeFlyingSoundInstance = new BeeFlyingSoundInstance($$2);
/*      */       } 
/*  495 */       this.minecraft.getSoundManager().queueTickingSound((TickableSoundInstance)beeFlyingSoundInstance); }
/*      */   
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleAddExperienceOrb(ClientboundAddExperienceOrbPacket $$0) {
/*  501 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/*  502 */     double $$1 = $$0.getX();
/*  503 */     double $$2 = $$0.getY();
/*  504 */     double $$3 = $$0.getZ();
/*      */     
/*  506 */     ExperienceOrb experienceOrb = new ExperienceOrb(this.level, $$1, $$2, $$3, $$0.getValue());
/*  507 */     experienceOrb.syncPacketPositionCodec($$1, $$2, $$3);
/*  508 */     experienceOrb.setYRot(0.0F);
/*  509 */     experienceOrb.setXRot(0.0F);
/*  510 */     experienceOrb.setId($$0.getId());
/*  511 */     this.level.addEntity((Entity)experienceOrb);
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleSetEntityMotion(ClientboundSetEntityMotionPacket $$0) {
/*  516 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/*  517 */     Entity $$1 = this.level.getEntity($$0.getId());
/*  518 */     if ($$1 == null) {
/*      */       return;
/*      */     }
/*  521 */     $$1.lerpMotion($$0.getXa() / 8000.0D, $$0.getYa() / 8000.0D, $$0.getZa() / 8000.0D);
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleSetEntityData(ClientboundSetEntityDataPacket $$0) {
/*  526 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/*  527 */     Entity $$1 = this.level.getEntity($$0.id());
/*  528 */     if ($$1 != null) {
/*  529 */       $$1.getEntityData().assignValues($$0.packedItems());
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleTeleportEntity(ClientboundTeleportEntityPacket $$0) {
/*  535 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/*  536 */     Entity $$1 = this.level.getEntity($$0.getId());
/*  537 */     if ($$1 == null) {
/*      */       return;
/*      */     }
/*  540 */     double $$2 = $$0.getX();
/*  541 */     double $$3 = $$0.getY();
/*  542 */     double $$4 = $$0.getZ();
/*  543 */     $$1.syncPacketPositionCodec($$2, $$3, $$4);
/*      */     
/*  545 */     if (!$$1.isControlledByLocalInstance()) {
/*  546 */       float $$5 = ($$0.getyRot() * 360) / 256.0F;
/*  547 */       float $$6 = ($$0.getxRot() * 360) / 256.0F;
/*  548 */       $$1.lerpTo($$2, $$3, $$4, $$5, $$6, 3);
/*  549 */       $$1.setOnGround($$0.isOnGround());
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleTickingState(ClientboundTickingStatePacket $$0) {
/*  555 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/*  556 */     if (this.minecraft.level == null) {
/*      */       return;
/*      */     }
/*  559 */     TickRateManager $$1 = this.minecraft.level.tickRateManager();
/*  560 */     $$1.setTickRate($$0.tickRate());
/*  561 */     $$1.setFrozen($$0.isFrozen());
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleTickingStep(ClientboundTickingStepPacket $$0) {
/*  566 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/*  567 */     if (this.minecraft.level == null) {
/*      */       return;
/*      */     }
/*  570 */     TickRateManager $$1 = this.minecraft.level.tickRateManager();
/*  571 */     $$1.setFrozenTicksToRun($$0.tickSteps());
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleSetCarriedItem(ClientboundSetCarriedItemPacket $$0) {
/*  576 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/*  577 */     if (Inventory.isHotbarSlot($$0.getSlot())) {
/*  578 */       (this.minecraft.player.getInventory()).selected = $$0.getSlot();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleMoveEntity(ClientboundMoveEntityPacket $$0) {
/*  584 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/*  585 */     Entity $$1 = $$0.getEntity(this.level);
/*  586 */     if ($$1 == null) {
/*      */       return;
/*      */     }
/*  589 */     if (!$$1.isControlledByLocalInstance()) {
/*  590 */       if ($$0.hasPosition()) {
/*  591 */         VecDeltaCodec $$2 = $$1.getPositionCodec();
/*  592 */         Vec3 $$3 = $$2.decode($$0.getXa(), $$0.getYa(), $$0.getZa());
/*  593 */         $$2.setBase($$3);
/*  594 */         float $$4 = $$0.hasRotation() ? (($$0.getyRot() * 360) / 256.0F) : $$1.lerpTargetYRot();
/*  595 */         float $$5 = $$0.hasRotation() ? (($$0.getxRot() * 360) / 256.0F) : $$1.lerpTargetXRot();
/*  596 */         $$1.lerpTo($$3.x(), $$3.y(), $$3.z(), $$4, $$5, 3);
/*  597 */       } else if ($$0.hasRotation()) {
/*  598 */         float $$6 = ($$0.getyRot() * 360) / 256.0F;
/*  599 */         float $$7 = ($$0.getxRot() * 360) / 256.0F;
/*  600 */         $$1.lerpTo($$1.lerpTargetX(), $$1.lerpTargetY(), $$1.lerpTargetZ(), $$6, $$7, 3);
/*      */       } 
/*  602 */       $$1.setOnGround($$0.isOnGround());
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleRotateMob(ClientboundRotateHeadPacket $$0) {
/*  608 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/*  609 */     Entity $$1 = $$0.getEntity(this.level);
/*  610 */     if ($$1 == null) {
/*      */       return;
/*      */     }
/*  613 */     float $$2 = ($$0.getYHeadRot() * 360) / 256.0F;
/*  614 */     $$1.lerpHeadTo($$2, 3);
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleRemoveEntities(ClientboundRemoveEntitiesPacket $$0) {
/*  619 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/*  620 */     $$0.getEntityIds().forEach($$0 -> this.level.removeEntity($$0, Entity.RemovalReason.DISCARDED));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleMovePlayer(ClientboundPlayerPositionPacket $$0) {
/*      */     double $$8, $$9, $$12, $$13, $$16, $$17;
/*  638 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/*  639 */     LocalPlayer localPlayer = this.minecraft.player;
/*      */     
/*  641 */     Vec3 $$2 = localPlayer.getDeltaMovement();
/*      */     
/*  643 */     boolean $$3 = $$0.getRelativeArguments().contains(RelativeMovement.X);
/*  644 */     boolean $$4 = $$0.getRelativeArguments().contains(RelativeMovement.Y);
/*  645 */     boolean $$5 = $$0.getRelativeArguments().contains(RelativeMovement.Z);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  652 */     if ($$3) {
/*  653 */       double $$6 = $$2.x();
/*  654 */       double $$7 = localPlayer.getX() + $$0.getX();
/*  655 */       ((Player)localPlayer).xOld += $$0.getX();
/*  656 */       ((Player)localPlayer).xo += $$0.getX();
/*      */     } else {
/*  658 */       $$8 = 0.0D;
/*  659 */       $$9 = $$0.getX();
/*  660 */       ((Player)localPlayer).xOld = $$9;
/*  661 */       ((Player)localPlayer).xo = $$9;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  667 */     if ($$4) {
/*  668 */       double $$10 = $$2.y();
/*  669 */       double $$11 = localPlayer.getY() + $$0.getY();
/*  670 */       ((Player)localPlayer).yOld += $$0.getY();
/*  671 */       ((Player)localPlayer).yo += $$0.getY();
/*      */     } else {
/*  673 */       $$12 = 0.0D;
/*  674 */       $$13 = $$0.getY();
/*  675 */       ((Player)localPlayer).yOld = $$13;
/*  676 */       ((Player)localPlayer).yo = $$13;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  682 */     if ($$5) {
/*  683 */       double $$14 = $$2.z();
/*  684 */       double $$15 = localPlayer.getZ() + $$0.getZ();
/*  685 */       ((Player)localPlayer).zOld += $$0.getZ();
/*  686 */       ((Player)localPlayer).zo += $$0.getZ();
/*      */     } else {
/*  688 */       $$16 = 0.0D;
/*  689 */       $$17 = $$0.getZ();
/*  690 */       ((Player)localPlayer).zOld = $$17;
/*  691 */       ((Player)localPlayer).zo = $$17;
/*      */     } 
/*      */     
/*  694 */     localPlayer.setPos($$9, $$13, $$17);
/*  695 */     localPlayer.setDeltaMovement($$8, $$12, $$16);
/*      */     
/*  697 */     float $$18 = $$0.getYRot();
/*  698 */     float $$19 = $$0.getXRot();
/*      */     
/*  700 */     if ($$0.getRelativeArguments().contains(RelativeMovement.X_ROT)) {
/*  701 */       localPlayer.setXRot(localPlayer.getXRot() + $$19);
/*  702 */       ((Player)localPlayer).xRotO += $$19;
/*      */     } else {
/*  704 */       localPlayer.setXRot($$19);
/*  705 */       ((Player)localPlayer).xRotO = $$19;
/*      */     } 
/*  707 */     if ($$0.getRelativeArguments().contains(RelativeMovement.Y_ROT)) {
/*  708 */       localPlayer.setYRot(localPlayer.getYRot() + $$18);
/*  709 */       ((Player)localPlayer).yRotO += $$18;
/*      */     } else {
/*  711 */       localPlayer.setYRot($$18);
/*  712 */       ((Player)localPlayer).yRotO = $$18;
/*      */     } 
/*      */     
/*  715 */     this.connection.send((Packet)new ServerboundAcceptTeleportationPacket($$0.getId()));
/*  716 */     this.connection.send((Packet)new ServerboundMovePlayerPacket.PosRot(localPlayer.getX(), localPlayer.getY(), localPlayer.getZ(), localPlayer.getYRot(), localPlayer.getXRot(), false));
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleChunkBlocksUpdate(ClientboundSectionBlocksUpdatePacket $$0) {
/*  721 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/*      */     
/*  723 */     $$0.runUpdates(($$0, $$1) -> this.level.setServerVerifiedBlockState($$0, $$1, 19));
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleLevelChunkWithLight(ClientboundLevelChunkWithLightPacket $$0) {
/*  728 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/*  729 */     int $$1 = $$0.getX();
/*  730 */     int $$2 = $$0.getZ();
/*  731 */     updateLevelChunk($$1, $$2, $$0.getChunkData());
/*  732 */     ClientboundLightUpdatePacketData $$3 = $$0.getLightData();
/*  733 */     this.level.queueLightUpdate(() -> {
/*      */           applyLightData($$0, $$1, $$2);
/*      */           LevelChunk $$3 = this.level.getChunkSource().getChunk($$0, $$1, false);
/*      */           if ($$3 != null) {
/*      */             enableChunkLight($$3, $$0, $$1);
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleChunksBiomes(ClientboundChunksBiomesPacket $$0) {
/*  744 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/*      */     
/*  746 */     for (ClientboundChunksBiomesPacket.ChunkBiomeData $$1 : $$0.chunkBiomeData()) {
/*  747 */       this.level.getChunkSource().replaceBiomes(($$1.pos()).x, ($$1.pos()).z, $$1.getReadBuffer());
/*      */     }
/*      */     
/*  750 */     for (ClientboundChunksBiomesPacket.ChunkBiomeData $$2 : $$0.chunkBiomeData()) {
/*  751 */       this.level.onChunkLoaded(new ChunkPos(($$2.pos()).x, ($$2.pos()).z));
/*      */     }
/*      */     
/*  754 */     for (ClientboundChunksBiomesPacket.ChunkBiomeData $$3 : $$0.chunkBiomeData()) {
/*  755 */       for (int $$4 = -1; $$4 <= 1; $$4++) {
/*  756 */         for (int $$5 = -1; $$5 <= 1; $$5++) {
/*  757 */           for (int $$6 = this.level.getMinSection(); $$6 < this.level.getMaxSection(); $$6++) {
/*  758 */             this.minecraft.levelRenderer.setSectionDirty(($$3.pos()).x + $$4, $$6, ($$3.pos()).z + $$5);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void updateLevelChunk(int $$0, int $$1, ClientboundLevelChunkPacketData $$2) {
/*  766 */     this.level.getChunkSource().replaceWithPacketData($$0, $$1, $$2.getReadBuffer(), $$2
/*  767 */         .getHeightmaps(), $$2.getBlockEntitiesTagsConsumer($$0, $$1));
/*      */   }
/*      */   
/*      */   private void enableChunkLight(LevelChunk $$0, int $$1, int $$2) {
/*  771 */     LevelLightEngine $$3 = this.level.getChunkSource().getLightEngine();
/*  772 */     LevelChunkSection[] $$4 = $$0.getSections();
/*  773 */     ChunkPos $$5 = $$0.getPos();
/*  774 */     for (int $$6 = 0; $$6 < $$4.length; $$6++) {
/*  775 */       LevelChunkSection $$7 = $$4[$$6];
/*  776 */       int $$8 = this.level.getSectionYFromSectionIndex($$6);
/*  777 */       $$3.updateSectionStatus(SectionPos.of($$5, $$8), $$7.hasOnlyAir());
/*  778 */       this.level.setSectionDirtyWithNeighbors($$1, $$8, $$2);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleForgetLevelChunk(ClientboundForgetLevelChunkPacket $$0) {
/*  784 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/*  785 */     this.level.getChunkSource().drop($$0.pos());
/*  786 */     queueLightRemoval($$0);
/*      */   }
/*      */   
/*      */   private void queueLightRemoval(ClientboundForgetLevelChunkPacket $$0) {
/*  790 */     ChunkPos $$1 = $$0.pos();
/*  791 */     this.level.queueLightUpdate(() -> {
/*      */           LevelLightEngine $$1 = this.level.getLightEngine();
/*      */           $$1.setLightEnabled($$0, false);
/*      */           for (int $$2 = $$1.getMinLightSection(); $$2 < $$1.getMaxLightSection(); $$2++) {
/*      */             SectionPos $$3 = SectionPos.of($$0, $$2);
/*      */             $$1.queueSectionData(LightLayer.BLOCK, $$3, null);
/*      */             $$1.queueSectionData(LightLayer.SKY, $$3, null);
/*      */           } 
/*      */           for (int $$4 = this.level.getMinSection(); $$4 < this.level.getMaxSection(); $$4++) {
/*      */             $$1.updateSectionStatus(SectionPos.of($$0, $$4), true);
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleBlockUpdate(ClientboundBlockUpdatePacket $$0) {
/*  807 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/*  808 */     this.level.setServerVerifiedBlockState($$0.getPos(), $$0.getBlockState(), 19);
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleConfigurationStart(ClientboundStartConfigurationPacket $$0) {
/*  813 */     this.connection.suspendInboundAfterProtocolChange();
/*  814 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/*      */     
/*  816 */     this.minecraft.clearClientLevel((Screen)new ServerReconfigScreen(RECONFIGURE_SCREEN_MESSAGE, this.connection));
/*      */     
/*  818 */     this.connection.setListener((PacketListener)new ClientConfigurationPacketListenerImpl(this.minecraft, this.connection, new CommonListenerCookie(this.localGameProfile, this.telemetryManager, this.registryAccess, this.enabledFeatures, this.serverBrand, this.serverData, this.postDisconnectScreen)));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  831 */     this.connection.resumeInboundAfterProtocolChange();
/*  832 */     send((Packet<?>)new ServerboundConfigurationAcknowledgedPacket());
/*      */   }
/*      */   
/*      */   public void handleTakeItemEntity(ClientboundTakeItemEntityPacket $$0) {
/*      */     LocalPlayer localPlayer;
/*  837 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/*  838 */     Entity $$1 = this.level.getEntity($$0.getItemId());
/*  839 */     LivingEntity $$2 = (LivingEntity)this.level.getEntity($$0.getPlayerId());
/*  840 */     if ($$2 == null) {
/*  841 */       localPlayer = this.minecraft.player;
/*      */     }
/*  843 */     if ($$1 != null) {
/*  844 */       if ($$1 instanceof ExperienceOrb) {
/*  845 */         this.level.playLocalSound($$1.getX(), $$1.getY(), $$1.getZ(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 0.1F, (this.random.nextFloat() - this.random.nextFloat()) * 0.35F + 0.9F, false);
/*      */       } else {
/*  847 */         this.level.playLocalSound($$1.getX(), $$1.getY(), $$1.getZ(), SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.2F, (this.random.nextFloat() - this.random.nextFloat()) * 1.4F + 2.0F, false);
/*      */       } 
/*      */       
/*  850 */       this.minecraft.particleEngine.add((Particle)new ItemPickupParticle(this.minecraft.getEntityRenderDispatcher(), this.minecraft.renderBuffers(), this.level, $$1, (Entity)localPlayer));
/*  851 */       if ($$1 instanceof ItemEntity) { ItemEntity $$3 = (ItemEntity)$$1;
/*      */         
/*  853 */         ItemStack $$4 = $$3.getItem();
/*  854 */         if (!$$4.isEmpty()) {
/*  855 */           $$4.shrink($$0.getAmount());
/*      */         }
/*  857 */         if ($$4.isEmpty()) {
/*  858 */           this.level.removeEntity($$0.getItemId(), Entity.RemovalReason.DISCARDED);
/*      */         } }
/*  860 */       else if (!($$1 instanceof ExperienceOrb))
/*  861 */       { this.level.removeEntity($$0.getItemId(), Entity.RemovalReason.DISCARDED); }
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleSystemChat(ClientboundSystemChatPacket $$0) {
/*  868 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/*  869 */     this.minecraft.getChatListener().handleSystemMessage($$0.content(), $$0.overlay());
/*      */   }
/*      */   
/*      */   public void handlePlayerChat(ClientboundPlayerChatPacket $$0) {
/*      */     SignedMessageLink $$7;
/*  874 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/*      */     
/*  876 */     Optional<SignedMessageBody> $$1 = $$0.body().unpack(this.messageSignatureCache);
/*  877 */     Optional<ChatType.Bound> $$2 = $$0.chatType().resolve((RegistryAccess)this.registryAccess);
/*  878 */     if ($$1.isEmpty() || $$2.isEmpty()) {
/*  879 */       this.connection.disconnect(INVALID_PACKET);
/*      */       
/*      */       return;
/*      */     } 
/*  883 */     this.messageSignatureCache.push($$1.get(), $$0.signature());
/*      */     
/*  885 */     UUID $$3 = $$0.sender();
/*  886 */     PlayerInfo $$4 = getPlayerInfo($$3);
/*  887 */     if ($$4 == null) {
/*  888 */       LOGGER.error("Received player chat packet for unknown player with ID: {}", $$3);
/*  889 */       this.minecraft.getChatListener().handleChatMessageError($$3, $$2.get());
/*      */       
/*      */       return;
/*      */     } 
/*  893 */     RemoteChatSession $$5 = $$4.getChatSession();
/*      */     
/*  895 */     if ($$5 != null) {
/*  896 */       SignedMessageLink $$6 = new SignedMessageLink($$0.index(), $$3, $$5.sessionId());
/*      */     } else {
/*  898 */       $$7 = SignedMessageLink.unsigned($$3);
/*      */     } 
/*      */     
/*  901 */     PlayerChatMessage $$8 = new PlayerChatMessage($$7, $$0.signature(), $$1.get(), $$0.unsignedContent(), $$0.filterMask());
/*  902 */     $$8 = $$4.getMessageValidator().updateAndValidate($$8);
/*  903 */     if ($$8 != null) {
/*  904 */       this.minecraft.getChatListener().handlePlayerChatMessage($$8, $$4.getProfile(), $$2.get());
/*      */     } else {
/*  906 */       this.minecraft.getChatListener().handleChatMessageError($$3, $$2.get());
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleDisguisedChat(ClientboundDisguisedChatPacket $$0) {
/*  912 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/*      */     
/*  914 */     Optional<ChatType.Bound> $$1 = $$0.chatType().resolve((RegistryAccess)this.registryAccess);
/*  915 */     if ($$1.isEmpty()) {
/*  916 */       this.connection.disconnect(INVALID_PACKET);
/*      */       
/*      */       return;
/*      */     } 
/*  920 */     this.minecraft.getChatListener().handleDisguisedChatMessage($$0.message(), $$1.get());
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleDeleteChat(ClientboundDeleteChatPacket $$0) {
/*  925 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/*      */     
/*  927 */     Optional<MessageSignature> $$1 = $$0.messageSignature().unpack(this.messageSignatureCache);
/*  928 */     if ($$1.isEmpty()) {
/*  929 */       this.connection.disconnect(INVALID_PACKET);
/*      */       
/*      */       return;
/*      */     } 
/*  933 */     this.lastSeenMessages.ignorePending($$1.get());
/*      */     
/*  935 */     if (!this.minecraft.getChatListener().removeFromDelayedMessageQueue($$1.get())) {
/*  936 */       this.minecraft.gui.getChat().deleteMessage($$1.get());
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleAnimate(ClientboundAnimatePacket $$0) {
/*  942 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/*  943 */     Entity $$1 = this.level.getEntity($$0.getId());
/*  944 */     if ($$1 == null) {
/*      */       return;
/*      */     }
/*  947 */     if ($$0.getAction() == 0) {
/*  948 */       LivingEntity $$2 = (LivingEntity)$$1;
/*  949 */       $$2.swing(InteractionHand.MAIN_HAND);
/*  950 */     } else if ($$0.getAction() == 3) {
/*  951 */       LivingEntity $$3 = (LivingEntity)$$1;
/*  952 */       $$3.swing(InteractionHand.OFF_HAND);
/*  953 */     } else if ($$0.getAction() == 2) {
/*  954 */       Player $$4 = (Player)$$1;
/*  955 */       $$4.stopSleepInBed(false, false);
/*  956 */     } else if ($$0.getAction() == 4) {
/*  957 */       this.minecraft.particleEngine.createTrackingEmitter($$1, (ParticleOptions)ParticleTypes.CRIT);
/*  958 */     } else if ($$0.getAction() == 5) {
/*  959 */       this.minecraft.particleEngine.createTrackingEmitter($$1, (ParticleOptions)ParticleTypes.ENCHANTED_HIT);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleHurtAnimation(ClientboundHurtAnimationPacket $$0) {
/*  965 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/*  966 */     Entity $$1 = this.level.getEntity($$0.id());
/*  967 */     if ($$1 == null) {
/*      */       return;
/*      */     }
/*  970 */     $$1.animateHurt($$0.yaw());
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleSetTime(ClientboundSetTimePacket $$0) {
/*  975 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/*  976 */     this.minecraft.level.setGameTime($$0.getGameTime());
/*  977 */     this.minecraft.level.setDayTime($$0.getDayTime());
/*  978 */     this.telemetryManager.setTime($$0.getGameTime());
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleSetSpawn(ClientboundSetDefaultSpawnPositionPacket $$0) {
/*  983 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/*  984 */     this.minecraft.level.setDefaultSpawnPos($$0.getPos(), $$0.getAngle());
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleSetEntityPassengersPacket(ClientboundSetPassengersPacket $$0) {
/*  989 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/*  990 */     Entity $$1 = this.level.getEntity($$0.getVehicle());
/*  991 */     if ($$1 == null) {
/*  992 */       LOGGER.warn("Received passengers for unknown entity");
/*      */       
/*      */       return;
/*      */     } 
/*  996 */     boolean $$2 = $$1.hasIndirectPassenger((Entity)this.minecraft.player);
/*  997 */     $$1.ejectPassengers();
/*      */     
/*  999 */     for (int $$3 : $$0.getPassengers()) {
/* 1000 */       Entity $$4 = this.level.getEntity($$3);
/* 1001 */       if ($$4 != null) {
/* 1002 */         $$4.startRiding($$1, true);
/* 1003 */         if ($$4 == this.minecraft.player && !$$2) {
/* 1004 */           if ($$1 instanceof net.minecraft.world.entity.vehicle.Boat) {
/* 1005 */             this.minecraft.player.yRotO = $$1.getYRot();
/* 1006 */             this.minecraft.player.setYRot($$1.getYRot());
/* 1007 */             this.minecraft.player.setYHeadRot($$1.getYRot());
/*      */           } 
/* 1009 */           MutableComponent mutableComponent = Component.translatable("mount.onboard", new Object[] { this.minecraft.options.keyShift.getTranslatedKeyMessage() });
/* 1010 */           this.minecraft.gui.setOverlayMessage((Component)mutableComponent, false);
/* 1011 */           this.minecraft.getNarrator().sayNow((Component)mutableComponent);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleEntityLinkPacket(ClientboundSetEntityLinkPacket $$0) {
/* 1019 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/* 1020 */     Entity $$1 = this.level.getEntity($$0.getSourceId());
/* 1021 */     if ($$1 instanceof Mob) {
/* 1022 */       ((Mob)$$1).setDelayedLeashHolderId($$0.getDestId());
/*      */     }
/*      */   }
/*      */   
/*      */   private static ItemStack findTotem(Player $$0) {
/* 1027 */     for (InteractionHand $$1 : InteractionHand.values()) {
/* 1028 */       ItemStack $$2 = $$0.getItemInHand($$1);
/* 1029 */       if ($$2.is(Items.TOTEM_OF_UNDYING)) {
/* 1030 */         return $$2;
/*      */       }
/*      */     } 
/* 1033 */     return new ItemStack((ItemLike)Items.TOTEM_OF_UNDYING);
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleEntityEvent(ClientboundEntityEventPacket $$0) {
/* 1038 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/* 1039 */     Entity $$1 = $$0.getEntity(this.level);
/* 1040 */     if ($$1 != null) {
/*      */       int $$2;
/* 1042 */       switch ($$0.getEventId()) {
/*      */         case 63:
/* 1044 */           this.minecraft.getSoundManager().play((SoundInstance)new SnifferSoundInstance((Sniffer)$$1)); return;
/*      */         case 21:
/* 1046 */           this.minecraft.getSoundManager().play((SoundInstance)new GuardianAttackSoundInstance((Guardian)$$1)); return;
/*      */         case 35:
/* 1048 */           $$2 = 40;
/* 1049 */           this.minecraft.particleEngine.createTrackingEmitter($$1, (ParticleOptions)ParticleTypes.TOTEM_OF_UNDYING, 30);
/* 1050 */           this.level.playLocalSound($$1.getX(), $$1.getY(), $$1.getZ(), SoundEvents.TOTEM_USE, $$1.getSoundSource(), 1.0F, 1.0F, false);
/* 1051 */           if ($$1 == this.minecraft.player)
/* 1052 */             this.minecraft.gameRenderer.displayItemActivation(findTotem((Player)this.minecraft.player)); 
/*      */           return;
/*      */       } 
/* 1055 */       $$1.handleEntityEvent($$0.getEventId());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleDamageEvent(ClientboundDamageEventPacket $$0) {
/* 1062 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/* 1063 */     Entity $$1 = this.level.getEntity($$0.entityId());
/* 1064 */     if ($$1 == null) {
/*      */       return;
/*      */     }
/*      */     
/* 1068 */     $$1.handleDamageEvent($$0.getSource(this.level));
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleSetHealth(ClientboundSetHealthPacket $$0) {
/* 1073 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/* 1074 */     this.minecraft.player.hurtTo($$0.getHealth());
/* 1075 */     this.minecraft.player.getFoodData().setFoodLevel($$0.getFood());
/* 1076 */     this.minecraft.player.getFoodData().setSaturation($$0.getSaturation());
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleSetExperience(ClientboundSetExperiencePacket $$0) {
/* 1081 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/* 1082 */     this.minecraft.player.setExperienceValues($$0.getExperienceProgress(), $$0.getTotalExperience(), $$0.getExperienceLevel());
/*      */   }
/*      */   
/*      */   public void handleRespawn(ClientboundRespawnPacket $$0) {
/*      */     LocalPlayer $$11;
/* 1087 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/* 1088 */     CommonPlayerSpawnInfo $$1 = $$0.commonPlayerSpawnInfo();
/* 1089 */     ResourceKey<Level> $$2 = $$1.dimension();
/* 1090 */     Holder.Reference reference = this.registryAccess.registryOrThrow(Registries.DIMENSION_TYPE).getHolderOrThrow($$1.dimensionType());
/* 1091 */     LocalPlayer $$4 = this.minecraft.player;
/* 1092 */     if ($$2 != $$4.level().dimension()) {
/* 1093 */       Scoreboard $$5 = this.level.getScoreboard();
/* 1094 */       Map<String, MapItemSavedData> $$6 = this.level.getAllMapData();
/* 1095 */       boolean $$7 = $$1.isDebug();
/* 1096 */       boolean $$8 = $$1.isFlat();
/* 1097 */       ClientLevel.ClientLevelData $$9 = new ClientLevel.ClientLevelData(this.levelData.getDifficulty(), this.levelData.isHardcore(), $$8);
/* 1098 */       this.levelData = $$9;
/* 1099 */       Objects.requireNonNull(this.minecraft); this.level = new ClientLevel(this, $$9, $$2, (Holder<DimensionType>)reference, this.serverChunkRadius, this.serverSimulationDistance, this.minecraft::getProfiler, this.minecraft.levelRenderer, $$7, $$1.seed());
/* 1100 */       this.level.setScoreboard($$5);
/* 1101 */       this.level.addMapData($$6);
/* 1102 */       this.minecraft.setLevel(this.level);
/*      */     } 
/*      */     
/* 1105 */     this.minecraft.cameraEntity = null;
/* 1106 */     if ($$4.hasContainerOpen()) {
/* 1107 */       $$4.closeContainer();
/*      */     }
/*      */ 
/*      */     
/* 1111 */     if ($$0.shouldKeep((byte)2)) {
/* 1112 */       LocalPlayer $$10 = this.minecraft.gameMode.createPlayer(this.level, $$4.getStats(), $$4.getRecipeBook(), $$4.isShiftKeyDown(), $$4.isSprinting());
/*      */     } else {
/* 1114 */       $$11 = this.minecraft.gameMode.createPlayer(this.level, $$4.getStats(), $$4.getRecipeBook());
/*      */     } 
/*      */     
/* 1117 */     startWaitingForNewLevel($$11, this.level);
/*      */     
/* 1119 */     $$11.setId($$4.getId());
/* 1120 */     this.minecraft.player = $$11;
/* 1121 */     if ($$2 != $$4.level().dimension()) {
/* 1122 */       this.minecraft.getMusicManager().stopPlaying();
/*      */     }
/* 1124 */     this.minecraft.cameraEntity = (Entity)$$11;
/*      */     
/* 1126 */     if ($$0.shouldKeep((byte)2)) {
/* 1127 */       List<SynchedEntityData.DataValue<?>> $$12 = $$4.getEntityData().getNonDefaultValues();
/* 1128 */       if ($$12 != null) {
/* 1129 */         $$11.getEntityData().assignValues($$12);
/*      */       }
/*      */     } 
/*      */     
/* 1133 */     if ($$0.shouldKeep((byte)1)) {
/* 1134 */       $$11.getAttributes().assignValues($$4.getAttributes());
/*      */     }
/* 1136 */     $$11.resetPos();
/* 1137 */     this.level.addEntity((Entity)$$11);
/* 1138 */     $$11.setYRot(-180.0F);
/* 1139 */     $$11.input = (Input)new KeyboardInput(this.minecraft.options);
/* 1140 */     this.minecraft.gameMode.adjustPlayer((Player)$$11);
/* 1141 */     $$11.setReducedDebugInfo($$4.isReducedDebugInfo());
/* 1142 */     $$11.setShowDeathScreen($$4.shouldShowDeathScreen());
/* 1143 */     $$11.setLastDeathLocation($$1.lastDeathLocation());
/* 1144 */     $$11.setPortalCooldown($$1.portalCooldown());
/* 1145 */     $$11.spinningEffectIntensity = $$4.spinningEffectIntensity;
/* 1146 */     $$11.oSpinningEffectIntensity = $$4.oSpinningEffectIntensity;
/* 1147 */     if (this.minecraft.screen instanceof DeathScreen || this.minecraft.screen instanceof DeathScreen.TitleConfirmScreen) {
/* 1148 */       this.minecraft.setScreen(null);
/*      */     }
/* 1150 */     this.minecraft.gameMode.setLocalMode($$1.gameType(), $$1.previousGameType());
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleExplosion(ClientboundExplodePacket $$0) {
/* 1155 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/* 1156 */     Explosion $$1 = new Explosion(this.minecraft.level, null, $$0.getX(), $$0.getY(), $$0.getZ(), $$0.getPower(), $$0.getToBlow(), $$0.getBlockInteraction(), $$0.getSmallExplosionParticles(), $$0.getLargeExplosionParticles(), $$0.getExplosionSound());
/* 1157 */     $$1.finalizeExplosion(true);
/*      */     
/* 1159 */     this.minecraft.player.setDeltaMovement(this.minecraft.player.getDeltaMovement().add($$0
/* 1160 */           .getKnockbackX(), $$0
/* 1161 */           .getKnockbackY(), $$0
/* 1162 */           .getKnockbackZ()));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleHorseScreenOpen(ClientboundHorseScreenOpenPacket $$0) {
/* 1168 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/* 1169 */     Entity $$1 = this.level.getEntity($$0.getEntityId());
/* 1170 */     if ($$1 instanceof AbstractHorse) { AbstractHorse $$2 = (AbstractHorse)$$1;
/* 1171 */       LocalPlayer $$3 = this.minecraft.player;
/* 1172 */       SimpleContainer $$4 = new SimpleContainer($$0.getSize());
/* 1173 */       HorseInventoryMenu $$5 = new HorseInventoryMenu($$0.getContainerId(), $$3.getInventory(), (Container)$$4, $$2);
/* 1174 */       $$3.containerMenu = (AbstractContainerMenu)$$5;
/* 1175 */       this.minecraft.setScreen((Screen)new HorseInventoryScreen($$5, $$3.getInventory(), $$2)); }
/*      */   
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleOpenScreen(ClientboundOpenScreenPacket $$0) {
/* 1181 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/* 1182 */     MenuScreens.create($$0.getType(), this.minecraft, $$0.getContainerId(), $$0.getTitle());
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleContainerSetSlot(ClientboundContainerSetSlotPacket $$0) {
/* 1187 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/* 1188 */     LocalPlayer localPlayer = this.minecraft.player;
/* 1189 */     ItemStack $$2 = $$0.getItem();
/* 1190 */     int $$3 = $$0.getSlot();
/*      */     
/* 1192 */     this.minecraft.getTutorial().onGetItem($$2);
/*      */     
/* 1194 */     if ($$0.getContainerId() == -1) {
/*      */       
/* 1196 */       if (!(this.minecraft.screen instanceof CreativeModeInventoryScreen)) {
/* 1197 */         ((Player)localPlayer).containerMenu.setCarried($$2);
/*      */       }
/* 1199 */     } else if ($$0.getContainerId() == -2) {
/* 1200 */       localPlayer.getInventory().setItem($$3, $$2);
/*      */     } else {
/* 1202 */       boolean $$4 = false;
/*      */       
/* 1204 */       Screen screen = this.minecraft.screen; if (screen instanceof CreativeModeInventoryScreen) { CreativeModeInventoryScreen $$5 = (CreativeModeInventoryScreen)screen;
/* 1205 */         $$4 = !$$5.isInventoryOpen(); }
/*      */ 
/*      */       
/* 1208 */       if ($$0.getContainerId() == 0 && InventoryMenu.isHotbarSlot($$3)) {
/* 1209 */         if (!$$2.isEmpty()) {
/* 1210 */           ItemStack $$6 = ((Player)localPlayer).inventoryMenu.getSlot($$3).getItem();
/* 1211 */           if ($$6.isEmpty() || $$6.getCount() < $$2.getCount()) {
/* 1212 */             $$2.setPopTime(5);
/*      */           }
/*      */         } 
/* 1215 */         ((Player)localPlayer).inventoryMenu.setItem($$3, $$0.getStateId(), $$2);
/* 1216 */       } else if ($$0.getContainerId() == ((Player)localPlayer).containerMenu.containerId && ($$0.getContainerId() != 0 || !$$4)) {
/* 1217 */         ((Player)localPlayer).containerMenu.setItem($$3, $$0.getStateId(), $$2);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleContainerContent(ClientboundContainerSetContentPacket $$0) {
/* 1224 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/* 1225 */     LocalPlayer localPlayer = this.minecraft.player;
/* 1226 */     if ($$0.getContainerId() == 0) {
/* 1227 */       ((Player)localPlayer).inventoryMenu.initializeContents($$0.getStateId(), $$0.getItems(), $$0.getCarriedItem());
/* 1228 */     } else if ($$0.getContainerId() == ((Player)localPlayer).containerMenu.containerId) {
/* 1229 */       ((Player)localPlayer).containerMenu.initializeContents($$0.getStateId(), $$0.getItems(), $$0.getCarriedItem());
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleOpenSignEditor(ClientboundOpenSignEditorPacket $$0) {
/* 1235 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/* 1236 */     BlockPos $$1 = $$0.getPos();
/*      */     
/* 1238 */     BlockEntity blockEntity = this.level.getBlockEntity($$1); if (blockEntity instanceof SignBlockEntity) { SignBlockEntity $$2 = (SignBlockEntity)blockEntity;
/* 1239 */       this.minecraft.player.openTextEdit($$2, $$0.isFrontText()); }
/*      */     else
/* 1241 */     { BlockState $$3 = this.level.getBlockState($$1);
/* 1242 */       SignBlockEntity $$4 = new SignBlockEntity($$1, $$3);
/* 1243 */       $$4.setLevel(this.level);
/* 1244 */       this.minecraft.player.openTextEdit($$4, $$0.isFrontText()); }
/*      */   
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleBlockEntityData(ClientboundBlockEntityDataPacket $$0) {
/* 1250 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/* 1251 */     BlockPos $$1 = $$0.getPos();
/* 1252 */     this.minecraft.level.getBlockEntity($$1, $$0.getType()).ifPresent($$1 -> {
/*      */           CompoundTag $$2 = $$0.getTag();
/*      */           if ($$2 != null) {
/*      */             $$1.load($$2);
/*      */           }
/*      */           if ($$1 instanceof net.minecraft.world.level.block.entity.CommandBlockEntity && this.minecraft.screen instanceof CommandBlockEditScreen) {
/*      */             ((CommandBlockEditScreen)this.minecraft.screen).updateGui();
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleContainerSetData(ClientboundContainerSetDataPacket $$0) {
/* 1266 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/* 1267 */     LocalPlayer localPlayer = this.minecraft.player;
/* 1268 */     if (((Player)localPlayer).containerMenu != null && ((Player)localPlayer).containerMenu.containerId == $$0.getContainerId()) {
/* 1269 */       ((Player)localPlayer).containerMenu.setData($$0.getId(), $$0.getValue());
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleSetEquipment(ClientboundSetEquipmentPacket $$0) {
/* 1275 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/* 1276 */     Entity $$1 = this.level.getEntity($$0.getEntity());
/* 1277 */     if ($$1 != null) {
/* 1278 */       $$0.getSlots().forEach($$1 -> $$0.setItemSlot((EquipmentSlot)$$1.getFirst(), (ItemStack)$$1.getSecond()));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleContainerClose(ClientboundContainerClosePacket $$0) {
/* 1284 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/* 1285 */     this.minecraft.player.clientSideCloseContainer();
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleBlockEvent(ClientboundBlockEventPacket $$0) {
/* 1290 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/* 1291 */     this.minecraft.level.blockEvent($$0.getPos(), $$0.getBlock(), $$0.getB0(), $$0.getB1());
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleBlockDestruction(ClientboundBlockDestructionPacket $$0) {
/* 1296 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/* 1297 */     this.minecraft.level.destroyBlockProgress($$0.getId(), $$0.getPos(), $$0.getProgress());
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleGameEvent(ClientboundGameEventPacket $$0) {
/* 1302 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/* 1303 */     LocalPlayer localPlayer = this.minecraft.player;
/* 1304 */     ClientboundGameEventPacket.Type $$2 = $$0.getEvent();
/* 1305 */     float $$3 = $$0.getParam();
/* 1306 */     int $$4 = Mth.floor($$3 + 0.5F);
/* 1307 */     if ($$2 == ClientboundGameEventPacket.NO_RESPAWN_BLOCK_AVAILABLE) {
/* 1308 */       localPlayer.displayClientMessage((Component)Component.translatable("block.minecraft.spawn.not_valid"), false);
/* 1309 */     } else if ($$2 == ClientboundGameEventPacket.START_RAINING) {
/* 1310 */       this.level.getLevelData().setRaining(true);
/* 1311 */       this.level.setRainLevel(0.0F);
/* 1312 */     } else if ($$2 == ClientboundGameEventPacket.STOP_RAINING) {
/* 1313 */       this.level.getLevelData().setRaining(false);
/* 1314 */       this.level.setRainLevel(1.0F);
/* 1315 */     } else if ($$2 == ClientboundGameEventPacket.CHANGE_GAME_MODE) {
/* 1316 */       this.minecraft.gameMode.setLocalMode(GameType.byId($$4));
/* 1317 */     } else if ($$2 == ClientboundGameEventPacket.WIN_GAME) {
/* 1318 */       if ($$4 == 0) {
/* 1319 */         this.minecraft.player.connection.send((Packet<?>)new ServerboundClientCommandPacket(ServerboundClientCommandPacket.Action.PERFORM_RESPAWN));
/*      */         
/* 1321 */         this.minecraft.setScreen((Screen)new ReceivingLevelScreen(() -> false));
/* 1322 */       } else if ($$4 == 1) {
/* 1323 */         this.minecraft.setScreen((Screen)new WinScreen(true, () -> {
/*      */                 this.minecraft.player.connection.send((Packet<?>)new ServerboundClientCommandPacket(ServerboundClientCommandPacket.Action.PERFORM_RESPAWN));
/*      */                 this.minecraft.setScreen(null);
/*      */               }));
/*      */       } 
/* 1328 */     } else if ($$2 == ClientboundGameEventPacket.DEMO_EVENT) {
/* 1329 */       Options $$5 = this.minecraft.options;
/* 1330 */       if ($$3 == 0.0F) {
/* 1331 */         this.minecraft.setScreen((Screen)new DemoIntroScreen());
/* 1332 */       } else if ($$3 == 101.0F) {
/* 1333 */         this.minecraft.gui.getChat().addMessage((Component)Component.translatable("demo.help.movement", new Object[] { $$5.keyUp.getTranslatedKeyMessage(), $$5.keyLeft.getTranslatedKeyMessage(), $$5.keyDown.getTranslatedKeyMessage(), $$5.keyRight.getTranslatedKeyMessage() }));
/* 1334 */       } else if ($$3 == 102.0F) {
/* 1335 */         this.minecraft.gui.getChat().addMessage((Component)Component.translatable("demo.help.jump", new Object[] { $$5.keyJump.getTranslatedKeyMessage() }));
/* 1336 */       } else if ($$3 == 103.0F) {
/* 1337 */         this.minecraft.gui.getChat().addMessage((Component)Component.translatable("demo.help.inventory", new Object[] { $$5.keyInventory.getTranslatedKeyMessage() }));
/* 1338 */       } else if ($$3 == 104.0F) {
/* 1339 */         this.minecraft.gui.getChat().addMessage((Component)Component.translatable("demo.day.6", new Object[] { $$5.keyScreenshot.getTranslatedKeyMessage() }));
/*      */       } 
/* 1341 */     } else if ($$2 == ClientboundGameEventPacket.ARROW_HIT_PLAYER) {
/* 1342 */       this.level.playSound((Player)localPlayer, localPlayer.getX(), localPlayer.getEyeY(), localPlayer.getZ(), SoundEvents.ARROW_HIT_PLAYER, SoundSource.PLAYERS, 0.18F, 0.45F);
/* 1343 */     } else if ($$2 == ClientboundGameEventPacket.RAIN_LEVEL_CHANGE) {
/* 1344 */       this.level.setRainLevel($$3);
/* 1345 */     } else if ($$2 == ClientboundGameEventPacket.THUNDER_LEVEL_CHANGE) {
/* 1346 */       this.level.setThunderLevel($$3);
/* 1347 */     } else if ($$2 == ClientboundGameEventPacket.PUFFER_FISH_STING) {
/* 1348 */       this.level.playSound((Player)localPlayer, localPlayer.getX(), localPlayer.getY(), localPlayer.getZ(), SoundEvents.PUFFER_FISH_STING, SoundSource.NEUTRAL, 1.0F, 1.0F);
/* 1349 */     } else if ($$2 == ClientboundGameEventPacket.GUARDIAN_ELDER_EFFECT) {
/* 1350 */       this.level.addParticle((ParticleOptions)ParticleTypes.ELDER_GUARDIAN, localPlayer.getX(), localPlayer.getY(), localPlayer.getZ(), 0.0D, 0.0D, 0.0D);
/*      */       
/* 1352 */       if ($$4 == 1) {
/* 1353 */         this.level.playSound((Player)localPlayer, localPlayer.getX(), localPlayer.getY(), localPlayer.getZ(), SoundEvents.ELDER_GUARDIAN_CURSE, SoundSource.HOSTILE, 1.0F, 1.0F);
/*      */       }
/* 1355 */     } else if ($$2 == ClientboundGameEventPacket.IMMEDIATE_RESPAWN) {
/* 1356 */       this.minecraft.player.setShowDeathScreen(($$3 == 0.0F));
/* 1357 */     } else if ($$2 == ClientboundGameEventPacket.LIMITED_CRAFTING) {
/* 1358 */       this.minecraft.player.setDoLimitedCrafting(($$3 == 1.0F));
/* 1359 */     } else if ($$2 == ClientboundGameEventPacket.LEVEL_CHUNKS_LOAD_START && 
/* 1360 */       this.levelLoadStatusManager != null) {
/* 1361 */       this.levelLoadStatusManager.loadingPacketsReceived();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void startWaitingForNewLevel(LocalPlayer $$0, ClientLevel $$1) {
/* 1367 */     this.levelLoadStatusManager = new LevelLoadStatusManager($$0, $$1, this.minecraft.levelRenderer);
/* 1368 */     Objects.requireNonNull(this.levelLoadStatusManager); this.minecraft.setScreen((Screen)new ReceivingLevelScreen(this.levelLoadStatusManager::levelReady));
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleMapItemData(ClientboundMapItemDataPacket $$0) {
/* 1373 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/* 1374 */     MapRenderer $$1 = this.minecraft.gameRenderer.getMapRenderer();
/* 1375 */     int $$2 = $$0.getMapId();
/* 1376 */     String $$3 = MapItem.makeKey($$2);
/* 1377 */     MapItemSavedData $$4 = this.minecraft.level.getMapData($$3);
/*      */     
/* 1379 */     if ($$4 == null) {
/* 1380 */       $$4 = MapItemSavedData.createForClient($$0.getScale(), $$0.isLocked(), this.minecraft.level.dimension());
/* 1381 */       this.minecraft.level.overrideMapData($$3, $$4);
/*      */     } 
/*      */     
/* 1384 */     $$0.applyToMap($$4);
/* 1385 */     $$1.update($$2, $$4);
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleLevelEvent(ClientboundLevelEventPacket $$0) {
/* 1390 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/* 1391 */     if ($$0.isGlobalEvent()) {
/* 1392 */       this.minecraft.level.globalLevelEvent($$0.getType(), $$0.getPos(), $$0.getData());
/*      */     } else {
/* 1394 */       this.minecraft.level.levelEvent($$0.getType(), $$0.getPos(), $$0.getData());
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleUpdateAdvancementsPacket(ClientboundUpdateAdvancementsPacket $$0) {
/* 1400 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/* 1401 */     this.advancements.update($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleSelectAdvancementsTab(ClientboundSelectAdvancementsTabPacket $$0) {
/* 1406 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/* 1407 */     ResourceLocation $$1 = $$0.getTab();
/* 1408 */     if ($$1 == null) {
/* 1409 */       this.advancements.setSelectedTab(null, false);
/*      */     } else {
/* 1411 */       AdvancementHolder $$2 = this.advancements.get($$1);
/* 1412 */       this.advancements.setSelectedTab($$2, false);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleCommands(ClientboundCommandsPacket $$0) {
/* 1418 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/* 1419 */     this.commands = new CommandDispatcher($$0.getRoot(CommandBuildContext.simple((HolderLookup.Provider)this.registryAccess, this.enabledFeatures)));
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleStopSoundEvent(ClientboundStopSoundPacket $$0) {
/* 1424 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/* 1425 */     this.minecraft.getSoundManager().stop($$0.getName(), $$0.getSource());
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleCommandSuggestions(ClientboundCommandSuggestionsPacket $$0) {
/* 1430 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/* 1431 */     this.suggestionsProvider.completeCustomSuggestions($$0.getId(), $$0.getSuggestions());
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleUpdateRecipes(ClientboundUpdateRecipesPacket $$0) {
/* 1436 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/* 1437 */     this.recipeManager.replaceRecipes($$0.getRecipes());
/*      */     
/* 1439 */     ClientRecipeBook $$1 = this.minecraft.player.getRecipeBook();
/* 1440 */     $$1.setupCollections(this.recipeManager.getRecipes(), this.minecraft.level.registryAccess());
/* 1441 */     this.minecraft.populateSearchTree(SearchRegistry.RECIPE_COLLECTIONS, $$1.getCollections());
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleLookAt(ClientboundPlayerLookAtPacket $$0) {
/* 1446 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/* 1447 */     Vec3 $$1 = $$0.getPosition(this.level);
/* 1448 */     if ($$1 != null) {
/* 1449 */       this.minecraft.player.lookAt($$0.getFromAnchor(), $$1);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleTagQueryPacket(ClientboundTagQueryPacket $$0) {
/* 1455 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/*      */     
/* 1457 */     if (!this.debugQueryHandler.handleResponse($$0.getTransactionId(), $$0.getTag())) {
/* 1458 */       LOGGER.debug("Got unhandled response to tag query {}", Integer.valueOf($$0.getTransactionId()));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleAwardStats(ClientboundAwardStatsPacket $$0) {
/* 1464 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/*      */     
/* 1466 */     for (Map.Entry<Stat<?>, Integer> $$1 : (Iterable<Map.Entry<Stat<?>, Integer>>)$$0.getStats().entrySet()) {
/* 1467 */       Stat<?> $$2 = $$1.getKey();
/* 1468 */       int $$3 = ((Integer)$$1.getValue()).intValue();
/*      */       
/* 1470 */       this.minecraft.player.getStats().setValue((Player)this.minecraft.player, $$2, $$3);
/*      */     } 
/*      */     
/* 1473 */     if (this.minecraft.screen instanceof StatsUpdateListener) {
/* 1474 */       ((StatsUpdateListener)this.minecraft.screen).onStatsUpdated();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleAddOrRemoveRecipes(ClientboundRecipePacket $$0) {
/* 1480 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/*      */     
/* 1482 */     ClientRecipeBook $$1 = this.minecraft.player.getRecipeBook();
/* 1483 */     $$1.setBookSettings($$0.getBookSettings());
/*      */     
/* 1485 */     ClientboundRecipePacket.State $$2 = $$0.getState();
/* 1486 */     switch ($$2) {
/*      */       case INITIALIZE_CHAT:
/* 1488 */         for (ResourceLocation $$3 : $$0.getRecipes()) {
/* 1489 */           Objects.requireNonNull($$1); this.recipeManager.byKey($$3).ifPresent($$1::remove);
/*      */         } 
/*      */         break;
/*      */       case UPDATE_GAME_MODE:
/* 1493 */         for (ResourceLocation $$4 : $$0.getRecipes()) {
/* 1494 */           Objects.requireNonNull($$1); this.recipeManager.byKey($$4).ifPresent($$1::add);
/*      */         } 
/* 1496 */         for (ResourceLocation $$5 : $$0.getHighlights()) {
/* 1497 */           Objects.requireNonNull($$1); this.recipeManager.byKey($$5).ifPresent($$1::addHighlight);
/*      */         } 
/*      */         break;
/*      */       case UPDATE_LISTED:
/* 1501 */         for (ResourceLocation $$6 : $$0.getRecipes()) {
/* 1502 */           this.recipeManager.byKey($$6).ifPresent($$1 -> {
/*      */                 $$0.add($$1);
/*      */                 $$0.addHighlight($$1);
/*      */                 if ($$1.value().showNotification()) {
/*      */                   RecipeToast.addOrUpdate(this.minecraft.getToasts(), $$1);
/*      */                 }
/*      */               });
/*      */         } 
/*      */         break;
/*      */     } 
/*      */     
/* 1513 */     $$1.getCollections().forEach($$1 -> $$1.updateKnownRecipes((RecipeBook)$$0));
/*      */     
/* 1515 */     if (this.minecraft.screen instanceof RecipeUpdateListener) {
/* 1516 */       ((RecipeUpdateListener)this.minecraft.screen).recipesUpdated();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleUpdateMobEffect(ClientboundUpdateMobEffectPacket $$0) {
/* 1522 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/* 1523 */     Entity $$1 = this.level.getEntity($$0.getEntityId());
/* 1524 */     if (!($$1 instanceof LivingEntity)) {
/*      */       return;
/*      */     }
/*      */     
/* 1528 */     MobEffect $$2 = $$0.getEffect();
/* 1529 */     if ($$2 == null) {
/*      */       return;
/*      */     }
/*      */     
/* 1533 */     MobEffectInstance $$3 = new MobEffectInstance($$2, $$0.getEffectDurationTicks(), $$0.getEffectAmplifier(), $$0.isEffectAmbient(), $$0.isEffectVisible(), $$0.effectShowsIcon(), null, Optional.ofNullable($$0.getFactorData()));
/* 1534 */     ((LivingEntity)$$1).forceAddEffect($$3, null);
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleUpdateTags(ClientboundUpdateTagsPacket $$0) {
/* 1539 */     super.handleUpdateTags($$0);
/* 1540 */     refreshTagDependentData();
/*      */   }
/*      */   
/*      */   private void refreshTagDependentData() {
/* 1544 */     if (!this.connection.isMemoryConnection())
/*      */     {
/* 1546 */       Blocks.rebuildCache();
/*      */     }
/*      */     
/* 1549 */     CreativeModeTabs.searchTab().rebuildSearchTree();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void handlePlayerCombatEnd(ClientboundPlayerCombatEndPacket $$0) {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void handlePlayerCombatEnter(ClientboundPlayerCombatEnterPacket $$0) {}
/*      */ 
/*      */   
/*      */   public void handlePlayerCombatKill(ClientboundPlayerCombatKillPacket $$0) {
/* 1562 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/*      */     
/* 1564 */     Entity $$1 = this.level.getEntity($$0.getPlayerId());
/* 1565 */     if ($$1 == this.minecraft.player) {
/* 1566 */       if (this.minecraft.player.shouldShowDeathScreen()) {
/* 1567 */         this.minecraft.setScreen((Screen)new DeathScreen($$0.getMessage(), this.level.getLevelData().isHardcore()));
/*      */       } else {
/* 1569 */         this.minecraft.player.respawn();
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleChangeDifficulty(ClientboundChangeDifficultyPacket $$0) {
/* 1576 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/* 1577 */     this.levelData.setDifficulty($$0.getDifficulty());
/* 1578 */     this.levelData.setDifficultyLocked($$0.isLocked());
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleSetCamera(ClientboundSetCameraPacket $$0) {
/* 1583 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/* 1584 */     Entity $$1 = $$0.getEntity(this.level);
/* 1585 */     if ($$1 != null) {
/* 1586 */       this.minecraft.setCameraEntity($$1);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleInitializeBorder(ClientboundInitializeBorderPacket $$0) {
/* 1592 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/* 1593 */     WorldBorder $$1 = this.level.getWorldBorder();
/*      */     
/* 1595 */     $$1.setCenter($$0.getNewCenterX(), $$0.getNewCenterZ());
/*      */     
/* 1597 */     long $$2 = $$0.getLerpTime();
/* 1598 */     if ($$2 > 0L) {
/* 1599 */       $$1.lerpSizeBetween($$0.getOldSize(), $$0.getNewSize(), $$2);
/*      */     } else {
/* 1601 */       $$1.setSize($$0.getNewSize());
/*      */     } 
/*      */     
/* 1604 */     $$1.setAbsoluteMaxSize($$0.getNewAbsoluteMaxSize());
/* 1605 */     $$1.setWarningBlocks($$0.getWarningBlocks());
/* 1606 */     $$1.setWarningTime($$0.getWarningTime());
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleSetBorderCenter(ClientboundSetBorderCenterPacket $$0) {
/* 1611 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/* 1612 */     this.level.getWorldBorder().setCenter($$0.getNewCenterX(), $$0.getNewCenterZ());
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleSetBorderLerpSize(ClientboundSetBorderLerpSizePacket $$0) {
/* 1617 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/* 1618 */     this.level.getWorldBorder().lerpSizeBetween($$0.getOldSize(), $$0.getNewSize(), $$0.getLerpTime());
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleSetBorderSize(ClientboundSetBorderSizePacket $$0) {
/* 1623 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/* 1624 */     this.level.getWorldBorder().setSize($$0.getSize());
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleSetBorderWarningDistance(ClientboundSetBorderWarningDistancePacket $$0) {
/* 1629 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/* 1630 */     this.level.getWorldBorder().setWarningBlocks($$0.getWarningBlocks());
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleSetBorderWarningDelay(ClientboundSetBorderWarningDelayPacket $$0) {
/* 1635 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/* 1636 */     this.level.getWorldBorder().setWarningTime($$0.getWarningDelay());
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleTitlesClear(ClientboundClearTitlesPacket $$0) {
/* 1641 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/* 1642 */     this.minecraft.gui.clear();
/* 1643 */     if ($$0.shouldResetTimes()) {
/* 1644 */       this.minecraft.gui.resetTitleTimes();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleServerData(ClientboundServerDataPacket $$0) {
/* 1650 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/* 1651 */     if (this.serverData == null) {
/*      */       return;
/*      */     }
/*      */     
/* 1655 */     this.serverData.motd = $$0.getMotd();
/* 1656 */     Objects.requireNonNull(this.serverData); $$0.getIconBytes().map(ServerData::validateIcon).ifPresent(this.serverData::setIconBytes);
/* 1657 */     this.serverData.setEnforcesSecureChat($$0.enforcesSecureChat());
/*      */     
/* 1659 */     ServerList.saveSingleServer(this.serverData);
/*      */     
/* 1661 */     if (!this.seenInsecureChatWarning && !enforcesSecureChat()) {
/* 1662 */       SystemToast $$1 = SystemToast.multiline(this.minecraft, SystemToast.SystemToastId.UNSECURE_SERVER_WARNING, UNSECURE_SERVER_TOAST_TITLE, UNSERURE_SERVER_TOAST);
/* 1663 */       this.minecraft.getToasts().addToast((Toast)$$1);
/* 1664 */       this.seenInsecureChatWarning = true;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleCustomChatCompletions(ClientboundCustomChatCompletionsPacket $$0) {
/* 1670 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/* 1671 */     this.suggestionsProvider.modifyCustomCompletions($$0.action(), $$0.entries());
/*      */   }
/*      */ 
/*      */   
/*      */   public void setActionBarText(ClientboundSetActionBarTextPacket $$0) {
/* 1676 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/* 1677 */     this.minecraft.gui.setOverlayMessage($$0.getText(), false);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setTitleText(ClientboundSetTitleTextPacket $$0) {
/* 1682 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/* 1683 */     this.minecraft.gui.setTitle($$0.getText());
/*      */   }
/*      */ 
/*      */   
/*      */   public void setSubtitleText(ClientboundSetSubtitleTextPacket $$0) {
/* 1688 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/* 1689 */     this.minecraft.gui.setSubtitle($$0.getText());
/*      */   }
/*      */ 
/*      */   
/*      */   public void setTitlesAnimation(ClientboundSetTitlesAnimationPacket $$0) {
/* 1694 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/* 1695 */     this.minecraft.gui.setTimes($$0.getFadeIn(), $$0.getStay(), $$0.getFadeOut());
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleTabListCustomisation(ClientboundTabListPacket $$0) {
/* 1700 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/* 1701 */     this.minecraft.gui.getTabList().setHeader($$0.getHeader().getString().isEmpty() ? null : $$0.getHeader());
/* 1702 */     this.minecraft.gui.getTabList().setFooter($$0.getFooter().getString().isEmpty() ? null : $$0.getFooter());
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleRemoveMobEffect(ClientboundRemoveMobEffectPacket $$0) {
/* 1707 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/* 1708 */     Entity $$1 = $$0.getEntity(this.level);
/* 1709 */     if ($$1 instanceof LivingEntity) {
/* 1710 */       ((LivingEntity)$$1).removeEffectNoUpdate($$0.getEffect());
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void handlePlayerInfoRemove(ClientboundPlayerInfoRemovePacket $$0) {
/* 1716 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/* 1717 */     for (UUID $$1 : $$0.profileIds()) {
/* 1718 */       this.minecraft.getPlayerSocialManager().removePlayer($$1);
/* 1719 */       PlayerInfo $$2 = this.playerInfoMap.remove($$1);
/* 1720 */       if ($$2 != null) {
/* 1721 */         this.listedPlayers.remove($$2);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void handlePlayerInfoUpdate(ClientboundPlayerInfoUpdatePacket $$0) {
/* 1728 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/*      */     
/* 1730 */     for (ClientboundPlayerInfoUpdatePacket.Entry $$1 : $$0.newEntries()) {
/* 1731 */       PlayerInfo $$2 = new PlayerInfo(Objects.<GameProfile>requireNonNull($$1.profile()), enforcesSecureChat());
/* 1732 */       if (this.playerInfoMap.putIfAbsent($$1.profileId(), $$2) == null) {
/* 1733 */         this.minecraft.getPlayerSocialManager().addPlayer($$2);
/*      */       }
/*      */     } 
/*      */     
/* 1737 */     for (ClientboundPlayerInfoUpdatePacket.Entry $$3 : $$0.entries()) {
/* 1738 */       PlayerInfo $$4 = this.playerInfoMap.get($$3.profileId());
/* 1739 */       if ($$4 == null) {
/* 1740 */         LOGGER.warn("Ignoring player info update for unknown player {} ({})", $$3.profileId(), $$0.actions());
/*      */         
/*      */         continue;
/*      */       } 
/* 1744 */       for (ClientboundPlayerInfoUpdatePacket.Action $$5 : $$0.actions()) {
/* 1745 */         applyPlayerInfoUpdate($$5, $$3, $$4);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private void applyPlayerInfoUpdate(ClientboundPlayerInfoUpdatePacket.Action $$0, ClientboundPlayerInfoUpdatePacket.Entry $$1, PlayerInfo $$2) {
/* 1751 */     switch ($$0) { case INITIALIZE_CHAT:
/* 1752 */         initializeChatSession($$1, $$2); break;
/*      */       case UPDATE_GAME_MODE:
/* 1754 */         if ($$2.getGameMode() != $$1.gameMode() && this.minecraft.player != null && this.minecraft.player
/*      */           
/* 1756 */           .getUUID().equals($$1.profileId()))
/*      */         {
/* 1758 */           this.minecraft.player.onGameModeChanged($$1.gameMode());
/*      */         }
/* 1760 */         $$2.setGameMode($$1.gameMode());
/*      */         break;
/*      */       case UPDATE_LISTED:
/* 1763 */         if ($$1.listed()) {
/* 1764 */           this.listedPlayers.add($$2); break;
/*      */         } 
/* 1766 */         this.listedPlayers.remove($$2);
/*      */         break;
/*      */       case UPDATE_LATENCY:
/* 1769 */         $$2.setLatency($$1.latency()); break;
/* 1770 */       case UPDATE_DISPLAY_NAME: $$2.setTabListDisplayName($$1.displayName());
/*      */         break; }
/*      */   
/*      */   }
/*      */   private void initializeChatSession(ClientboundPlayerInfoUpdatePacket.Entry $$0, PlayerInfo $$1) {
/* 1775 */     GameProfile $$2 = $$1.getProfile();
/* 1776 */     SignatureValidator $$3 = this.minecraft.getProfileKeySignatureValidator();
/* 1777 */     if ($$3 == null) {
/* 1778 */       LOGGER.warn("Ignoring chat session from {} due to missing Services public key", $$2.getName());
/* 1779 */       $$1.clearChatSession(enforcesSecureChat());
/*      */       return;
/*      */     } 
/* 1782 */     RemoteChatSession.Data $$4 = $$0.chatSession();
/* 1783 */     if ($$4 != null) {
/*      */       try {
/* 1785 */         RemoteChatSession $$5 = $$4.validate($$2, $$3);
/* 1786 */         $$1.setChatSession($$5);
/* 1787 */       } catch (net.minecraft.world.entity.player.ProfilePublicKey.ValidationException $$6) {
/* 1788 */         LOGGER.error("Failed to validate profile key for player: '{}'", $$2.getName(), $$6);
/* 1789 */         $$1.clearChatSession(enforcesSecureChat());
/*      */       } 
/*      */     } else {
/* 1792 */       $$1.clearChatSession(enforcesSecureChat());
/*      */     } 
/*      */   }
/*      */   
/*      */   private boolean enforcesSecureChat() {
/* 1797 */     if (!this.minecraft.canValidateProfileKeys()) {
/* 1798 */       return false;
/*      */     }
/* 1800 */     return (this.serverData != null && this.serverData.enforcesSecureChat());
/*      */   }
/*      */ 
/*      */   
/*      */   public void handlePlayerAbilities(ClientboundPlayerAbilitiesPacket $$0) {
/* 1805 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/* 1806 */     LocalPlayer localPlayer = this.minecraft.player;
/* 1807 */     (localPlayer.getAbilities()).flying = $$0.isFlying();
/* 1808 */     (localPlayer.getAbilities()).instabuild = $$0.canInstabuild();
/* 1809 */     (localPlayer.getAbilities()).invulnerable = $$0.isInvulnerable();
/* 1810 */     (localPlayer.getAbilities()).mayfly = $$0.canFly();
/* 1811 */     localPlayer.getAbilities().setFlyingSpeed($$0.getFlyingSpeed());
/* 1812 */     localPlayer.getAbilities().setWalkingSpeed($$0.getWalkingSpeed());
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleSoundEvent(ClientboundSoundPacket $$0) {
/* 1817 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/* 1818 */     this.minecraft.level.playSeededSound((Player)this.minecraft.player, $$0.getX(), $$0.getY(), $$0.getZ(), $$0.getSound(), $$0.getSource(), $$0.getVolume(), $$0.getPitch(), $$0.getSeed());
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleSoundEntityEvent(ClientboundSoundEntityPacket $$0) {
/* 1823 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/* 1824 */     Entity $$1 = this.level.getEntity($$0.getId());
/* 1825 */     if ($$1 == null) {
/*      */       return;
/*      */     }
/* 1828 */     this.minecraft.level.playSeededSound((Player)this.minecraft.player, $$1, $$0.getSound(), $$0.getSource(), $$0.getVolume(), $$0.getPitch(), $$0.getSeed());
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleBossUpdate(ClientboundBossEventPacket $$0) {
/* 1833 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/* 1834 */     this.minecraft.gui.getBossOverlay().update($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleItemCooldown(ClientboundCooldownPacket $$0) {
/* 1839 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/* 1840 */     if ($$0.getDuration() == 0) {
/* 1841 */       this.minecraft.player.getCooldowns().removeCooldown($$0.getItem());
/*      */     } else {
/* 1843 */       this.minecraft.player.getCooldowns().addCooldown($$0.getItem(), $$0.getDuration());
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleMoveVehicle(ClientboundMoveVehiclePacket $$0) {
/* 1849 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/* 1850 */     Entity $$1 = this.minecraft.player.getRootVehicle();
/* 1851 */     if ($$1 != this.minecraft.player && $$1.isControlledByLocalInstance()) {
/* 1852 */       $$1.absMoveTo($$0.getX(), $$0.getY(), $$0.getZ(), $$0.getYRot(), $$0.getXRot());
/* 1853 */       this.connection.send((Packet)new ServerboundMoveVehiclePacket($$1));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleOpenBook(ClientboundOpenBookPacket $$0) {
/* 1859 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/* 1860 */     ItemStack $$1 = this.minecraft.player.getItemInHand($$0.getHand());
/* 1861 */     if ($$1.is(Items.WRITTEN_BOOK)) {
/* 1862 */       this.minecraft.setScreen((Screen)new BookViewScreen((BookViewScreen.BookAccess)new BookViewScreen.WrittenBookAccess($$1)));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleCustomPayload(CustomPacketPayload $$0) {
/* 1868 */     if ($$0 instanceof PathfindingDebugPayload) { PathfindingDebugPayload $$1 = (PathfindingDebugPayload)$$0;
/* 1869 */       this.minecraft.debugRenderer.pathfindingRenderer.addPath($$1.entityId(), $$1.path(), $$1.maxNodeDistance()); }
/* 1870 */     else if ($$0 instanceof NeighborUpdatesDebugPayload) { NeighborUpdatesDebugPayload $$2 = (NeighborUpdatesDebugPayload)$$0;
/* 1871 */       ((NeighborsUpdateRenderer)this.minecraft.debugRenderer.neighborsUpdateRenderer).addUpdate($$2.time(), $$2.pos()); }
/* 1872 */     else if ($$0 instanceof StructuresDebugPayload) { StructuresDebugPayload $$3 = (StructuresDebugPayload)$$0;
/* 1873 */       this.minecraft.debugRenderer.structureRenderer.addBoundingBox($$3.mainBB(), $$3.pieces(), $$3.dimension()); }
/* 1874 */     else if ($$0 instanceof WorldGenAttemptDebugPayload) { WorldGenAttemptDebugPayload $$4 = (WorldGenAttemptDebugPayload)$$0;
/* 1875 */       ((WorldGenAttemptRenderer)this.minecraft.debugRenderer.worldGenAttemptRenderer).addPos($$4.pos(), $$4.scale(), $$4.red(), $$4.green(), $$4.blue(), $$4.alpha()); }
/* 1876 */     else if ($$0 instanceof PoiTicketCountDebugPayload) { PoiTicketCountDebugPayload $$5 = (PoiTicketCountDebugPayload)$$0;
/* 1877 */       this.minecraft.debugRenderer.brainDebugRenderer.setFreeTicketCount($$5.pos(), $$5.freeTicketCount()); }
/* 1878 */     else if ($$0 instanceof PoiAddedDebugPayload) { PoiAddedDebugPayload $$6 = (PoiAddedDebugPayload)$$0;
/* 1879 */       BrainDebugRenderer.PoiInfo $$7 = new BrainDebugRenderer.PoiInfo($$6.pos(), $$6.type(), $$6.freeTicketCount());
/* 1880 */       this.minecraft.debugRenderer.brainDebugRenderer.addPoi($$7); }
/* 1881 */     else if ($$0 instanceof PoiRemovedDebugPayload) { PoiRemovedDebugPayload $$8 = (PoiRemovedDebugPayload)$$0;
/* 1882 */       this.minecraft.debugRenderer.brainDebugRenderer.removePoi($$8.pos()); }
/* 1883 */     else if ($$0 instanceof VillageSectionsDebugPayload) { VillageSectionsDebugPayload $$9 = (VillageSectionsDebugPayload)$$0;
/* 1884 */       VillageSectionsDebugRenderer $$10 = this.minecraft.debugRenderer.villageSectionsDebugRenderer;
/* 1885 */       Objects.requireNonNull($$10); $$9.villageChunks().forEach($$10::setVillageSection);
/* 1886 */       Objects.requireNonNull($$10); $$9.notVillageChunks().forEach($$10::setNotVillageSection); }
/* 1887 */     else if ($$0 instanceof GoalDebugPayload) { GoalDebugPayload $$11 = (GoalDebugPayload)$$0;
/* 1888 */       this.minecraft.debugRenderer.goalSelectorRenderer.addGoalSelector($$11.entityId(), $$11.pos(), $$11.goals()); }
/* 1889 */     else if ($$0 instanceof BrainDebugPayload) { BrainDebugPayload $$12 = (BrainDebugPayload)$$0;
/* 1890 */       this.minecraft.debugRenderer.brainDebugRenderer.addOrUpdateBrainDump($$12.brainDump()); }
/* 1891 */     else if ($$0 instanceof BeeDebugPayload) { BeeDebugPayload $$13 = (BeeDebugPayload)$$0;
/* 1892 */       this.minecraft.debugRenderer.beeDebugRenderer.addOrUpdateBeeInfo($$13.beeInfo()); }
/* 1893 */     else if ($$0 instanceof HiveDebugPayload) { HiveDebugPayload $$14 = (HiveDebugPayload)$$0;
/* 1894 */       this.minecraft.debugRenderer.beeDebugRenderer.addOrUpdateHiveInfo($$14.hiveInfo(), this.level.getGameTime()); }
/* 1895 */     else if ($$0 instanceof GameTestAddMarkerDebugPayload) { GameTestAddMarkerDebugPayload $$15 = (GameTestAddMarkerDebugPayload)$$0;
/* 1896 */       this.minecraft.debugRenderer.gameTestDebugRenderer.addMarker($$15.pos(), $$15.color(), $$15.text(), $$15.durationMs()); }
/* 1897 */     else if ($$0 instanceof net.minecraft.network.protocol.common.custom.GameTestClearMarkersDebugPayload)
/* 1898 */     { this.minecraft.debugRenderer.gameTestDebugRenderer.clear(); }
/* 1899 */     else if ($$0 instanceof RaidsDebugPayload) { RaidsDebugPayload $$16 = (RaidsDebugPayload)$$0;
/* 1900 */       this.minecraft.debugRenderer.raidDebugRenderer.setRaidCenters($$16.raidCenters()); }
/* 1901 */     else if ($$0 instanceof GameEventDebugPayload) { GameEventDebugPayload $$17 = (GameEventDebugPayload)$$0;
/* 1902 */       this.minecraft.debugRenderer.gameEventListenerRenderer.trackGameEvent($$17.type(), $$17.pos()); }
/* 1903 */     else if ($$0 instanceof GameEventListenerDebugPayload) { GameEventListenerDebugPayload $$18 = (GameEventListenerDebugPayload)$$0;
/* 1904 */       this.minecraft.debugRenderer.gameEventListenerRenderer.trackListener($$18.listenerPos(), $$18.listenerRange()); }
/* 1905 */     else if ($$0 instanceof BreezeDebugPayload) { BreezeDebugPayload $$19 = (BreezeDebugPayload)$$0;
/* 1906 */       this.minecraft.debugRenderer.breezeDebugRenderer.add($$19.breezeInfo()); }
/*      */     else
/* 1908 */     { handleUnknownCustomPayload($$0); }
/*      */   
/*      */   }
/*      */ 
/*      */   
/*      */   private void handleUnknownCustomPayload(CustomPacketPayload $$0) {
/* 1914 */     LOGGER.warn("Unknown custom packet payload: {}", $$0.id());
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleAddObjective(ClientboundSetObjectivePacket $$0) {
/* 1919 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/* 1920 */     Scoreboard $$1 = this.level.getScoreboard();
/*      */     
/* 1922 */     String $$2 = $$0.getObjectiveName();
/* 1923 */     if ($$0.getMethod() == 0) {
/* 1924 */       $$1.addObjective($$2, ObjectiveCriteria.DUMMY, $$0.getDisplayName(), $$0.getRenderType(), false, $$0.getNumberFormat());
/*      */     } else {
/* 1926 */       Objective $$3 = $$1.getObjective($$2);
/* 1927 */       if ($$3 != null) {
/* 1928 */         if ($$0.getMethod() == 1) {
/* 1929 */           $$1.removeObjective($$3);
/* 1930 */         } else if ($$0.getMethod() == 2) {
/* 1931 */           $$3.setRenderType($$0.getRenderType());
/* 1932 */           $$3.setDisplayName($$0.getDisplayName());
/* 1933 */           $$3.setNumberFormat($$0.getNumberFormat());
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleSetScore(ClientboundSetScorePacket $$0) {
/* 1941 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/* 1942 */     Scoreboard $$1 = this.level.getScoreboard();
/* 1943 */     String $$2 = $$0.objectiveName();
/*      */     
/* 1945 */     ScoreHolder $$3 = ScoreHolder.forNameOnly($$0.owner());
/* 1946 */     Objective $$4 = $$1.getObjective($$2);
/* 1947 */     if ($$4 != null) {
/* 1948 */       ScoreAccess $$5 = $$1.getOrCreatePlayerScore($$3, $$4, true);
/* 1949 */       $$5.set($$0.score());
/* 1950 */       $$5.display($$0.display());
/* 1951 */       $$5.numberFormatOverride($$0.numberFormat());
/*      */     } else {
/* 1953 */       LOGGER.warn("Received packet for unknown scoreboard objective: {}", $$2);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleResetScore(ClientboundResetScorePacket $$0) {
/* 1959 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/* 1960 */     Scoreboard $$1 = this.level.getScoreboard();
/* 1961 */     String $$2 = $$0.objectiveName();
/*      */     
/* 1963 */     ScoreHolder $$3 = ScoreHolder.forNameOnly($$0.owner());
/* 1964 */     if ($$2 == null) {
/* 1965 */       $$1.resetAllPlayerScores($$3);
/*      */     } else {
/* 1967 */       Objective $$4 = $$1.getObjective($$2);
/* 1968 */       if ($$4 != null) {
/* 1969 */         $$1.resetSinglePlayerScore($$3, $$4);
/*      */       } else {
/* 1971 */         LOGGER.warn("Received packet for unknown scoreboard objective: {}", $$2);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleSetDisplayObjective(ClientboundSetDisplayObjectivePacket $$0) {
/* 1978 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/* 1979 */     Scoreboard $$1 = this.level.getScoreboard();
/*      */     
/* 1981 */     String $$2 = $$0.getObjectiveName();
/* 1982 */     Objective $$3 = ($$2 == null) ? null : $$1.getObjective($$2);
/* 1983 */     $$1.setDisplayObjective($$0.getSlot(), $$3);
/*      */   }
/*      */   
/*      */   public void handleSetPlayerTeamPacket(ClientboundSetPlayerTeamPacket $$0) {
/*      */     PlayerTeam $$4;
/* 1988 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/* 1989 */     Scoreboard $$1 = this.level.getScoreboard();
/*      */ 
/*      */     
/* 1992 */     ClientboundSetPlayerTeamPacket.Action $$2 = $$0.getTeamAction();
/*      */     
/* 1994 */     if ($$2 == ClientboundSetPlayerTeamPacket.Action.ADD) {
/* 1995 */       PlayerTeam $$3 = $$1.addPlayerTeam($$0.getName());
/*      */     } else {
/* 1997 */       $$4 = $$1.getPlayerTeam($$0.getName());
/* 1998 */       if ($$4 == null) {
/* 1999 */         LOGGER.warn("Received packet for unknown team {}: team action: {}, player action: {}", new Object[] { $$0.getName(), $$0.getTeamAction(), $$0.getPlayerAction() });
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/* 2004 */     Optional<ClientboundSetPlayerTeamPacket.Parameters> $$5 = $$0.getParameters();
/*      */     
/* 2006 */     $$5.ifPresent($$1 -> {
/*      */           $$0.setDisplayName($$1.getDisplayName());
/*      */           
/*      */           $$0.setColor($$1.getColor());
/*      */           $$0.unpackOptions($$1.getOptions());
/*      */           Team.Visibility $$2 = Team.Visibility.byName($$1.getNametagVisibility());
/*      */           if ($$2 != null) {
/*      */             $$0.setNameTagVisibility($$2);
/*      */           }
/*      */           Team.CollisionRule $$3 = Team.CollisionRule.byName($$1.getCollisionRule());
/*      */           if ($$3 != null) {
/*      */             $$0.setCollisionRule($$3);
/*      */           }
/*      */           $$0.setPlayerPrefix($$1.getPlayerPrefix());
/*      */           $$0.setPlayerSuffix($$1.getPlayerSuffix());
/*      */         });
/* 2022 */     ClientboundSetPlayerTeamPacket.Action $$6 = $$0.getPlayerAction();
/* 2023 */     if ($$6 == ClientboundSetPlayerTeamPacket.Action.ADD) {
/* 2024 */       for (String $$7 : $$0.getPlayers()) {
/* 2025 */         $$1.addPlayerToTeam($$7, $$4);
/*      */       }
/* 2027 */     } else if ($$6 == ClientboundSetPlayerTeamPacket.Action.REMOVE) {
/* 2028 */       for (String $$8 : $$0.getPlayers()) {
/* 2029 */         $$1.removePlayerFromTeam($$8, $$4);
/*      */       }
/*      */     } 
/*      */     
/* 2033 */     if ($$2 == ClientboundSetPlayerTeamPacket.Action.REMOVE) {
/* 2034 */       $$1.removePlayerTeam($$4);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleParticleEvent(ClientboundLevelParticlesPacket $$0) {
/* 2040 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/* 2041 */     if ($$0.getCount() == 0) {
/* 2042 */       double $$1 = ($$0.getMaxSpeed() * $$0.getXDist());
/* 2043 */       double $$2 = ($$0.getMaxSpeed() * $$0.getYDist());
/* 2044 */       double $$3 = ($$0.getMaxSpeed() * $$0.getZDist());
/*      */       try {
/* 2046 */         this.level.addParticle($$0.getParticle(), $$0.isOverrideLimiter(), $$0.getX(), $$0.getY(), $$0.getZ(), $$1, $$2, $$3);
/* 2047 */       } catch (Throwable $$4) {
/* 2048 */         LOGGER.warn("Could not spawn particle effect {}", $$0.getParticle());
/*      */       } 
/*      */     } else {
/* 2051 */       for (int $$5 = 0; $$5 < $$0.getCount(); $$5++) {
/* 2052 */         double $$6 = this.random.nextGaussian() * $$0.getXDist();
/* 2053 */         double $$7 = this.random.nextGaussian() * $$0.getYDist();
/* 2054 */         double $$8 = this.random.nextGaussian() * $$0.getZDist();
/* 2055 */         double $$9 = this.random.nextGaussian() * $$0.getMaxSpeed();
/* 2056 */         double $$10 = this.random.nextGaussian() * $$0.getMaxSpeed();
/* 2057 */         double $$11 = this.random.nextGaussian() * $$0.getMaxSpeed();
/*      */         try {
/* 2059 */           this.level.addParticle($$0.getParticle(), $$0.isOverrideLimiter(), $$0.getX() + $$6, $$0.getY() + $$7, $$0.getZ() + $$8, $$9, $$10, $$11);
/* 2060 */         } catch (Throwable $$12) {
/* 2061 */           LOGGER.warn("Could not spawn particle effect {}", $$0.getParticle());
/*      */           return;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleUpdateAttributes(ClientboundUpdateAttributesPacket $$0) {
/* 2070 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/* 2071 */     Entity $$1 = this.level.getEntity($$0.getEntityId());
/* 2072 */     if ($$1 == null) {
/*      */       return;
/*      */     }
/* 2075 */     if (!($$1 instanceof LivingEntity)) {
/* 2076 */       throw new IllegalStateException("Server tried to update attributes of a non-living entity (actually: " + $$1 + ")");
/*      */     }
/*      */     
/* 2079 */     AttributeMap $$2 = ((LivingEntity)$$1).getAttributes();
/* 2080 */     for (ClientboundUpdateAttributesPacket.AttributeSnapshot $$3 : $$0.getValues()) {
/* 2081 */       AttributeInstance $$4 = $$2.getInstance($$3.getAttribute());
/*      */       
/* 2083 */       if ($$4 == null) {
/* 2084 */         LOGGER.warn("Entity {} does not have attribute {}", $$1, BuiltInRegistries.ATTRIBUTE.getKey($$3.getAttribute()));
/*      */         continue;
/*      */       } 
/* 2087 */       $$4.setBaseValue($$3.getBase());
/* 2088 */       $$4.removeModifiers();
/*      */       
/* 2090 */       for (AttributeModifier $$5 : $$3.getModifiers()) {
/* 2091 */         $$4.addTransientModifier($$5);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void handlePlaceRecipe(ClientboundPlaceGhostRecipePacket $$0) {
/* 2098 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/*      */     
/* 2100 */     AbstractContainerMenu $$1 = this.minecraft.player.containerMenu;
/* 2101 */     if ($$1.containerId != $$0.getContainerId()) {
/*      */       return;
/*      */     }
/*      */     
/* 2105 */     this.recipeManager.byKey($$0.getRecipe()).ifPresent($$1 -> {
/*      */           if (this.minecraft.screen instanceof RecipeUpdateListener) {
/*      */             RecipeBookComponent $$2 = ((RecipeUpdateListener)this.minecraft.screen).getRecipeBookComponent();
/*      */             $$2.setupGhostRecipe($$1, (List)$$0.slots);
/*      */           } 
/*      */         });
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleLightUpdatePacket(ClientboundLightUpdatePacket $$0) {
/* 2115 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/* 2116 */     int $$1 = $$0.getX();
/* 2117 */     int $$2 = $$0.getZ();
/* 2118 */     ClientboundLightUpdatePacketData $$3 = $$0.getLightData();
/* 2119 */     this.level.queueLightUpdate(() -> applyLightData($$0, $$1, $$2));
/*      */   }
/*      */   
/*      */   private void applyLightData(int $$0, int $$1, ClientboundLightUpdatePacketData $$2) {
/* 2123 */     LevelLightEngine $$3 = this.level.getChunkSource().getLightEngine();
/* 2124 */     BitSet $$4 = $$2.getSkyYMask();
/* 2125 */     BitSet $$5 = $$2.getEmptySkyYMask();
/* 2126 */     Iterator<byte[]> $$6 = (Iterator)$$2.getSkyUpdates().iterator();
/*      */     
/* 2128 */     readSectionList($$0, $$1, $$3, LightLayer.SKY, $$4, $$5, $$6);
/*      */     
/* 2130 */     BitSet $$7 = $$2.getBlockYMask();
/* 2131 */     BitSet $$8 = $$2.getEmptyBlockYMask();
/* 2132 */     Iterator<byte[]> $$9 = (Iterator)$$2.getBlockUpdates().iterator();
/* 2133 */     readSectionList($$0, $$1, $$3, LightLayer.BLOCK, $$7, $$8, $$9);
/*      */     
/* 2135 */     $$3.setLightEnabled(new ChunkPos($$0, $$1), true);
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleMerchantOffers(ClientboundMerchantOffersPacket $$0) {
/* 2140 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/* 2141 */     AbstractContainerMenu $$1 = this.minecraft.player.containerMenu;
/* 2142 */     if ($$0.getContainerId() == $$1.containerId && $$1 instanceof MerchantMenu) { MerchantMenu $$2 = (MerchantMenu)$$1;
/* 2143 */       $$2.setOffers($$0.getOffers());
/* 2144 */       $$2.setXp($$0.getVillagerXp());
/* 2145 */       $$2.setMerchantLevel($$0.getVillagerLevel());
/* 2146 */       $$2.setShowProgressBar($$0.showProgress());
/* 2147 */       $$2.setCanRestock($$0.canRestock()); }
/*      */   
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleSetChunkCacheRadius(ClientboundSetChunkCacheRadiusPacket $$0) {
/* 2153 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/* 2154 */     this.serverChunkRadius = $$0.getRadius();
/* 2155 */     this.minecraft.options.setServerRenderDistance(this.serverChunkRadius);
/* 2156 */     this.level.getChunkSource().updateViewRadius($$0.getRadius());
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleSetSimulationDistance(ClientboundSetSimulationDistancePacket $$0) {
/* 2161 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/* 2162 */     this.serverSimulationDistance = $$0.simulationDistance();
/* 2163 */     this.level.setServerSimulationDistance(this.serverSimulationDistance);
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleSetChunkCacheCenter(ClientboundSetChunkCacheCenterPacket $$0) {
/* 2168 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/* 2169 */     this.level.getChunkSource().updateViewCenter($$0.getX(), $$0.getZ());
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleBlockChangedAck(ClientboundBlockChangedAckPacket $$0) {
/* 2174 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/* 2175 */     this.level.handleBlockChangedAck($$0.sequence());
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleBundlePacket(ClientboundBundlePacket $$0) {
/* 2180 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, (BlockableEventLoop)this.minecraft);
/* 2181 */     for (Packet<ClientGamePacketListener> $$1 : (Iterable<Packet<ClientGamePacketListener>>)$$0.subPackets()) {
/* 2182 */       $$1.handle((PacketListener)this);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleChunkBatchStart(ClientboundChunkBatchStartPacket $$0) {
/* 2188 */     this.chunkBatchSizeCalculator.onBatchStart();
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleChunkBatchFinished(ClientboundChunkBatchFinishedPacket $$0) {
/* 2193 */     this.chunkBatchSizeCalculator.onBatchFinished($$0.batchSize());
/* 2194 */     send((Packet<?>)new ServerboundChunkBatchReceivedPacket(this.chunkBatchSizeCalculator.getDesiredChunksPerTick()));
/*      */   }
/*      */ 
/*      */   
/*      */   public void handlePongResponse(ClientboundPongResponsePacket $$0) {
/* 2199 */     this.pingDebugMonitor.onPongReceived($$0);
/*      */   }
/*      */   
/*      */   private void readSectionList(int $$0, int $$1, LevelLightEngine $$2, LightLayer $$3, BitSet $$4, BitSet $$5, Iterator<byte[]> $$6) {
/* 2203 */     for (int $$7 = 0; $$7 < $$2.getLightSectionCount(); $$7++) {
/* 2204 */       int $$8 = $$2.getMinLightSection() + $$7;
/* 2205 */       boolean $$9 = $$4.get($$7);
/* 2206 */       boolean $$10 = $$5.get($$7);
/* 2207 */       if ($$9 || $$10) {
/* 2208 */         $$2.queueSectionData($$3, SectionPos.of($$0, $$8, $$1), $$9 ? new DataLayer((byte[])((byte[])$$6.next()).clone()) : new DataLayer());
/* 2209 */         this.level.setSectionDirtyWithNeighbors($$0, $$8, $$1);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public Connection getConnection() {
/* 2215 */     return this.connection;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isAcceptingMessages() {
/* 2220 */     return (this.connection.isConnected() && !this.closed);
/*      */   }
/*      */   
/*      */   public Collection<PlayerInfo> getListedOnlinePlayers() {
/* 2224 */     return this.listedPlayers;
/*      */   }
/*      */   
/*      */   public Collection<PlayerInfo> getOnlinePlayers() {
/* 2228 */     return this.playerInfoMap.values();
/*      */   }
/*      */   
/*      */   public Collection<UUID> getOnlinePlayerIds() {
/* 2232 */     return this.playerInfoMap.keySet();
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public PlayerInfo getPlayerInfo(UUID $$0) {
/* 2237 */     return this.playerInfoMap.get($$0);
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public PlayerInfo getPlayerInfo(String $$0) {
/* 2242 */     for (PlayerInfo $$1 : this.playerInfoMap.values()) {
/* 2243 */       if ($$1.getProfile().getName().equals($$0)) {
/* 2244 */         return $$1;
/*      */       }
/*      */     } 
/*      */     
/* 2248 */     return null;
/*      */   }
/*      */   
/*      */   public GameProfile getLocalGameProfile() {
/* 2252 */     return this.localGameProfile;
/*      */   }
/*      */   
/*      */   public ClientAdvancements getAdvancements() {
/* 2256 */     return this.advancements;
/*      */   }
/*      */   
/*      */   public CommandDispatcher<SharedSuggestionProvider> getCommands() {
/* 2260 */     return this.commands;
/*      */   }
/*      */   
/*      */   public ClientLevel getLevel() {
/* 2264 */     return this.level;
/*      */   }
/*      */   
/*      */   public DebugQueryHandler getDebugQueryHandler() {
/* 2268 */     return this.debugQueryHandler;
/*      */   }
/*      */   
/*      */   public UUID getId() {
/* 2272 */     return this.id;
/*      */   }
/*      */   
/*      */   public Set<ResourceKey<Level>> levels() {
/* 2276 */     return this.levels;
/*      */   }
/*      */ 
/*      */   
/*      */   public RegistryAccess.Frozen registryAccess() {
/* 2281 */     return this.registryAccess;
/*      */   }
/*      */   
/*      */   public void markMessageAsProcessed(PlayerChatMessage $$0, boolean $$1) {
/* 2285 */     MessageSignature $$2 = $$0.signature();
/* 2286 */     if ($$2 != null && this.lastSeenMessages.addPending($$2, $$1) && 
/* 2287 */       this.lastSeenMessages.offset() > 64) {
/* 2288 */       sendChatAcknowledgement();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private void sendChatAcknowledgement() {
/* 2294 */     int $$0 = this.lastSeenMessages.getAndClearOffset();
/* 2295 */     if ($$0 > 0) {
/* 2296 */       send((Packet<?>)new ServerboundChatAckPacket($$0));
/*      */     }
/*      */   }
/*      */   
/*      */   public void sendChat(String $$0) {
/* 2301 */     Instant $$1 = Instant.now();
/* 2302 */     long $$2 = Crypt.SaltSupplier.getLong();
/* 2303 */     LastSeenMessagesTracker.Update $$3 = this.lastSeenMessages.generateAndApplyUpdate();
/* 2304 */     MessageSignature $$4 = this.signedMessageEncoder.pack(new SignedMessageBody($$0, $$1, $$2, $$3.lastSeen()));
/* 2305 */     send((Packet<?>)new ServerboundChatPacket($$0, $$1, $$2, $$4, $$3.update()));
/*      */   }
/*      */   
/*      */   public void sendCommand(String $$0) {
/* 2309 */     Instant $$1 = Instant.now();
/* 2310 */     long $$2 = Crypt.SaltSupplier.getLong();
/* 2311 */     LastSeenMessagesTracker.Update $$3 = this.lastSeenMessages.generateAndApplyUpdate();
/* 2312 */     ArgumentSignatures $$4 = ArgumentSignatures.signCommand(SignableCommand.of(parseCommand($$0)), $$3 -> {
/*      */           SignedMessageBody $$4 = new SignedMessageBody($$3, $$0, $$1, $$2.lastSeen());
/*      */           return this.signedMessageEncoder.pack($$4);
/*      */         });
/* 2316 */     send((Packet<?>)new ServerboundChatCommandPacket($$0, $$1, $$2, $$4, $$3.update()));
/*      */   }
/*      */   
/*      */   public boolean sendUnsignedCommand(String $$0) {
/* 2320 */     if (SignableCommand.of(parseCommand($$0)).arguments().isEmpty()) {
/* 2321 */       LastSeenMessagesTracker.Update $$1 = this.lastSeenMessages.generateAndApplyUpdate();
/* 2322 */       send((Packet<?>)new ServerboundChatCommandPacket($$0, Instant.now(), 0L, ArgumentSignatures.EMPTY, $$1.update()));
/* 2323 */       return true;
/*      */     } 
/* 2325 */     return false;
/*      */   }
/*      */   
/*      */   private ParseResults<SharedSuggestionProvider> parseCommand(String $$0) {
/* 2329 */     return this.commands.parse($$0, this.suggestionsProvider);
/*      */   }
/*      */ 
/*      */   
/*      */   public void tick() {
/* 2334 */     if (this.connection.isEncrypted()) {
/* 2335 */       ProfileKeyPairManager $$0 = this.minecraft.getProfileKeyPairManager();
/* 2336 */       if ($$0.shouldRefreshKeyPair()) {
/* 2337 */         $$0.prepareKeyPair().thenAcceptAsync($$0 -> $$0.ifPresent(this::setKeyPair), (Executor)this.minecraft);
/*      */       }
/*      */     } 
/*      */     
/* 2341 */     sendDeferredPackets();
/*      */     
/* 2343 */     if (this.minecraft.getDebugOverlay().showNetworkCharts()) {
/* 2344 */       this.pingDebugMonitor.tick();
/*      */     }
/*      */     
/* 2347 */     this.telemetryManager.tick();
/* 2348 */     if (this.levelLoadStatusManager != null) {
/* 2349 */       this.levelLoadStatusManager.tick();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void setKeyPair(ProfileKeyPair $$0) {
/* 2355 */     if (!this.minecraft.isLocalPlayer(this.localGameProfile.getId())) {
/*      */       return;
/*      */     }
/* 2358 */     if (this.chatSession != null && this.chatSession.keyPair().equals($$0)) {
/*      */       return;
/*      */     }
/*      */     
/* 2362 */     this.chatSession = LocalChatSession.create($$0);
/* 2363 */     this.signedMessageEncoder = this.chatSession.createMessageEncoder(this.localGameProfile.getId());
/* 2364 */     send((Packet<?>)new ServerboundChatSessionUpdatePacket(this.chatSession.asRemote().asData()));
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public ServerData getServerData() {
/* 2369 */     return this.serverData;
/*      */   }
/*      */   
/*      */   public FeatureFlagSet enabledFeatures() {
/* 2373 */     return this.enabledFeatures;
/*      */   }
/*      */   
/*      */   public boolean isFeatureEnabled(FeatureFlagSet $$0) {
/* 2377 */     return $$0.isSubsetOf(enabledFeatures());
/*      */   }
/*      */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\multiplayer\ClientPacketListener.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */