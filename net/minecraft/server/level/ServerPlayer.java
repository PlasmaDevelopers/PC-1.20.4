/*      */ package net.minecraft.server.level;
/*      */ 
/*      */ import com.google.common.net.InetAddresses;
/*      */ import com.mojang.authlib.GameProfile;
/*      */ import com.mojang.datafixers.util.Either;
/*      */ import com.mojang.logging.LogUtils;
/*      */ import com.mojang.serialization.Dynamic;
/*      */ import com.mojang.serialization.DynamicOps;
/*      */ import java.net.InetSocketAddress;
/*      */ import java.net.SocketAddress;
/*      */ import java.util.Collection;
/*      */ import java.util.List;
/*      */ import java.util.Objects;
/*      */ import java.util.Optional;
/*      */ import java.util.OptionalInt;
/*      */ import java.util.Set;
/*      */ import java.util.stream.Collectors;
/*      */ import java.util.stream.Stream;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.BlockUtil;
/*      */ import net.minecraft.ChatFormatting;
/*      */ import net.minecraft.CrashReport;
/*      */ import net.minecraft.CrashReportCategory;
/*      */ import net.minecraft.ReportedException;
/*      */ import net.minecraft.Util;
/*      */ import net.minecraft.advancements.CriteriaTriggers;
/*      */ import net.minecraft.commands.arguments.EntityAnchorArgument;
/*      */ import net.minecraft.core.BlockPos;
/*      */ import net.minecraft.core.Direction;
/*      */ import net.minecraft.core.GlobalPos;
/*      */ import net.minecraft.core.NonNullList;
/*      */ import net.minecraft.core.Position;
/*      */ import net.minecraft.core.SectionPos;
/*      */ import net.minecraft.core.Vec3i;
/*      */ import net.minecraft.core.registries.BuiltInRegistries;
/*      */ import net.minecraft.nbt.CompoundTag;
/*      */ import net.minecraft.nbt.NbtOps;
/*      */ import net.minecraft.nbt.Tag;
/*      */ import net.minecraft.network.PacketSendListener;
/*      */ import net.minecraft.network.chat.ChatType;
/*      */ import net.minecraft.network.chat.CommonComponents;
/*      */ import net.minecraft.network.chat.Component;
/*      */ import net.minecraft.network.chat.HoverEvent;
/*      */ import net.minecraft.network.chat.MutableComponent;
/*      */ import net.minecraft.network.chat.OutgoingChatMessage;
/*      */ import net.minecraft.network.chat.RemoteChatSession;
/*      */ import net.minecraft.network.chat.Style;
/*      */ import net.minecraft.network.protocol.Packet;
/*      */ import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundChangeDifficultyPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundContainerClosePacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundContainerSetContentPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundContainerSetDataPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundEntityEventPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundHorseScreenOpenPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundHurtAnimationPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundLevelEventPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundMerchantOffersPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundOpenBookPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundOpenScreenPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundOpenSignEditorPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundPlayerAbilitiesPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundPlayerCombatEndPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundPlayerCombatEnterPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundPlayerCombatKillPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundPlayerLookAtPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundRemoveMobEffectPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundRespawnPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundServerDataPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundSetCameraPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundSetExperiencePacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundSetHealthPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundSoundPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundSystemChatPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
/*      */ import net.minecraft.network.protocol.game.CommonPlayerSpawnInfo;
/*      */ import net.minecraft.network.protocol.status.ServerStatus;
/*      */ import net.minecraft.resources.ResourceKey;
/*      */ import net.minecraft.resources.ResourceLocation;
/*      */ import net.minecraft.server.MinecraftServer;
/*      */ import net.minecraft.server.PlayerAdvancements;
/*      */ import net.minecraft.server.network.ServerGamePacketListenerImpl;
/*      */ import net.minecraft.server.network.TextFilter;
/*      */ import net.minecraft.server.players.PlayerList;
/*      */ import net.minecraft.sounds.SoundEvent;
/*      */ import net.minecraft.sounds.SoundSource;
/*      */ import net.minecraft.stats.RecipeBook;
/*      */ import net.minecraft.stats.ServerRecipeBook;
/*      */ import net.minecraft.stats.ServerStatsCounter;
/*      */ import net.minecraft.stats.Stat;
/*      */ import net.minecraft.stats.Stats;
/*      */ import net.minecraft.tags.DamageTypeTags;
/*      */ import net.minecraft.tags.FluidTags;
/*      */ import net.minecraft.util.Mth;
/*      */ import net.minecraft.util.RandomSource;
/*      */ import net.minecraft.util.Unit;
/*      */ import net.minecraft.world.Container;
/*      */ import net.minecraft.world.InteractionHand;
/*      */ import net.minecraft.world.MenuProvider;
/*      */ import net.minecraft.world.damagesource.DamageSource;
/*      */ import net.minecraft.world.effect.MobEffectInstance;
/*      */ import net.minecraft.world.effect.MobEffects;
/*      */ import net.minecraft.world.entity.Entity;
/*      */ import net.minecraft.world.entity.EntitySelector;
/*      */ import net.minecraft.world.entity.HumanoidArm;
/*      */ import net.minecraft.world.entity.LivingEntity;
/*      */ import net.minecraft.world.entity.Mob;
/*      */ import net.minecraft.world.entity.NeutralMob;
/*      */ import net.minecraft.world.entity.RelativeMovement;
/*      */ import net.minecraft.world.entity.animal.horse.AbstractHorse;
/*      */ import net.minecraft.world.entity.item.ItemEntity;
/*      */ import net.minecraft.world.entity.monster.Monster;
/*      */ import net.minecraft.world.entity.monster.warden.WardenSpawnTracker;
/*      */ import net.minecraft.world.entity.player.ChatVisiblity;
/*      */ import net.minecraft.world.entity.player.Inventory;
/*      */ import net.minecraft.world.entity.player.Player;
/*      */ import net.minecraft.world.entity.projectile.AbstractArrow;
/*      */ import net.minecraft.world.inventory.AbstractContainerMenu;
/*      */ import net.minecraft.world.inventory.ContainerListener;
/*      */ import net.minecraft.world.inventory.ContainerSynchronizer;
/*      */ import net.minecraft.world.inventory.HorseInventoryMenu;
/*      */ import net.minecraft.world.inventory.Slot;
/*      */ import net.minecraft.world.item.ComplexItem;
/*      */ import net.minecraft.world.item.ItemCooldowns;
/*      */ import net.minecraft.world.item.ItemStack;
/*      */ import net.minecraft.world.item.Items;
/*      */ import net.minecraft.world.item.ServerItemCooldowns;
/*      */ import net.minecraft.world.item.WrittenBookItem;
/*      */ import net.minecraft.world.item.crafting.RecipeHolder;
/*      */ import net.minecraft.world.item.trading.MerchantOffers;
/*      */ import net.minecraft.world.level.BlockGetter;
/*      */ import net.minecraft.world.level.ChunkPos;
/*      */ import net.minecraft.world.level.GameRules;
/*      */ import net.minecraft.world.level.GameType;
/*      */ import net.minecraft.world.level.Level;
/*      */ import net.minecraft.world.level.biome.BiomeManager;
/*      */ import net.minecraft.world.level.block.Blocks;
/*      */ import net.minecraft.world.level.block.HorizontalDirectionalBlock;
/*      */ import net.minecraft.world.level.block.NetherPortalBlock;
/*      */ import net.minecraft.world.level.block.entity.BlockEntity;
/*      */ import net.minecraft.world.level.block.entity.CommandBlockEntity;
/*      */ import net.minecraft.world.level.block.entity.SignBlockEntity;
/*      */ import net.minecraft.world.level.block.state.BlockState;
/*      */ import net.minecraft.world.level.block.state.properties.Property;
/*      */ import net.minecraft.world.level.border.WorldBorder;
/*      */ import net.minecraft.world.level.gameevent.GameEvent;
/*      */ import net.minecraft.world.level.portal.PortalInfo;
/*      */ import net.minecraft.world.level.storage.LevelData;
/*      */ import net.minecraft.world.phys.AABB;
/*      */ import net.minecraft.world.phys.Vec3;
/*      */ import net.minecraft.world.scores.PlayerTeam;
/*      */ import net.minecraft.world.scores.ScoreAccess;
/*      */ import net.minecraft.world.scores.ScoreHolder;
/*      */ import net.minecraft.world.scores.Team;
/*      */ import net.minecraft.world.scores.criteria.ObjectiveCriteria;
/*      */ import org.slf4j.Logger;
/*      */ 
/*      */ public class ServerPlayer
/*      */   extends Player {
/*  164 */   private static final Logger LOGGER = LogUtils.getLogger();
/*      */   private static final int NEUTRAL_MOB_DEATH_NOTIFICATION_RADII_XZ = 32;
/*      */   private static final int NEUTRAL_MOB_DEATH_NOTIFICATION_RADII_Y = 10;
/*      */   private static final int FLY_STAT_RECORDING_SPEED = 25;
/*      */   public ServerGamePacketListenerImpl connection;
/*      */   public final MinecraftServer server;
/*      */   public final ServerPlayerGameMode gameMode;
/*      */   private final PlayerAdvancements advancements;
/*      */   private final ServerStatsCounter stats;
/*  173 */   private float lastRecordedHealthAndAbsorption = Float.MIN_VALUE;
/*  174 */   private int lastRecordedFoodLevel = Integer.MIN_VALUE;
/*  175 */   private int lastRecordedAirLevel = Integer.MIN_VALUE;
/*  176 */   private int lastRecordedArmor = Integer.MIN_VALUE;
/*  177 */   private int lastRecordedLevel = Integer.MIN_VALUE;
/*  178 */   private int lastRecordedExperience = Integer.MIN_VALUE;
/*  179 */   private float lastSentHealth = -1.0E8F;
/*  180 */   private int lastSentFood = -99999999;
/*      */   private boolean lastFoodSaturationZero = true;
/*  182 */   private int lastSentExp = -99999999;
/*  183 */   private int spawnInvulnerableTime = 60;
/*  184 */   private ChatVisiblity chatVisibility = ChatVisiblity.FULL;
/*      */   private boolean canChatColor = true;
/*  186 */   private long lastActionTime = Util.getMillis();
/*      */   @Nullable
/*      */   private Entity camera;
/*      */   private boolean isChangingDimension;
/*      */   private boolean seenCredits;
/*  191 */   private final ServerRecipeBook recipeBook = new ServerRecipeBook();
/*      */   @Nullable
/*      */   private Vec3 levitationStartPos;
/*      */   private int levitationStartTime;
/*      */   private boolean disconnected;
/*  196 */   private int requestedViewDistance = 2;
/*  197 */   private String language = "en_us";
/*      */   
/*      */   @Nullable
/*      */   private Vec3 startingToFallPosition;
/*      */   
/*      */   @Nullable
/*      */   private Vec3 enteredNetherPosition;
/*      */   @Nullable
/*      */   private Vec3 enteredLavaOnVehiclePosition;
/*  206 */   private SectionPos lastSectionPos = SectionPos.of(0, 0, 0);
/*  207 */   private ChunkTrackingView chunkTrackingView = ChunkTrackingView.EMPTY;
/*      */   
/*  209 */   private ResourceKey<Level> respawnDimension = Level.OVERWORLD;
/*      */   
/*      */   @Nullable
/*      */   private BlockPos respawnPosition;
/*      */   
/*      */   private boolean respawnForced;
/*      */   
/*      */   private float respawnAngle;
/*      */   private final TextFilter textFilter;
/*      */   private boolean textFilteringEnabled;
/*      */   private boolean allowsListing;
/*  220 */   private WardenSpawnTracker wardenSpawnTracker = new WardenSpawnTracker(0, 0, 0);
/*      */   
/*  222 */   private final ContainerSynchronizer containerSynchronizer = new ContainerSynchronizer()
/*      */     {
/*      */       public void sendInitialData(AbstractContainerMenu $$0, NonNullList<ItemStack> $$1, ItemStack $$2, int[] $$3) {
/*  225 */         ServerPlayer.this.connection.send((Packet)new ClientboundContainerSetContentPacket($$0.containerId, $$0.incrementStateId(), $$1, $$2));
/*  226 */         for (int $$4 = 0; $$4 < $$3.length; $$4++) {
/*  227 */           broadcastDataValue($$0, $$4, $$3[$$4]);
/*      */         }
/*      */       }
/*      */ 
/*      */       
/*      */       public void sendSlotChange(AbstractContainerMenu $$0, int $$1, ItemStack $$2) {
/*  233 */         ServerPlayer.this.connection.send((Packet)new ClientboundContainerSetSlotPacket($$0.containerId, $$0.incrementStateId(), $$1, $$2));
/*      */       }
/*      */ 
/*      */       
/*      */       public void sendCarriedChange(AbstractContainerMenu $$0, ItemStack $$1) {
/*  238 */         ServerPlayer.this.connection.send((Packet)new ClientboundContainerSetSlotPacket(-1, $$0.incrementStateId(), -1, $$1));
/*      */       }
/*      */ 
/*      */       
/*      */       public void sendDataChange(AbstractContainerMenu $$0, int $$1, int $$2) {
/*  243 */         broadcastDataValue($$0, $$1, $$2);
/*      */       }
/*      */       
/*      */       private void broadcastDataValue(AbstractContainerMenu $$0, int $$1, int $$2) {
/*  247 */         ServerPlayer.this.connection.send((Packet)new ClientboundContainerSetDataPacket($$0.containerId, $$1, $$2));
/*      */       }
/*      */     };
/*      */   
/*  251 */   private final ContainerListener containerListener = new ContainerListener()
/*      */     {
/*      */       public void slotChanged(AbstractContainerMenu $$0, int $$1, ItemStack $$2) {
/*  254 */         Slot $$3 = $$0.getSlot($$1);
/*  255 */         if ($$3 instanceof net.minecraft.world.inventory.ResultSlot) {
/*      */           return;
/*      */         }
/*      */         
/*  259 */         if ($$3.container == ServerPlayer.this.getInventory()) {
/*  260 */           CriteriaTriggers.INVENTORY_CHANGED.trigger(ServerPlayer.this, ServerPlayer.this.getInventory(), $$2);
/*      */         }
/*      */       }
/*      */ 
/*      */       
/*      */       public void dataChanged(AbstractContainerMenu $$0, int $$1, int $$2) {}
/*      */     };
/*      */   
/*      */   @Nullable
/*      */   private RemoteChatSession chatSession;
/*      */   private int containerCounter;
/*      */   public boolean wonGame;
/*      */   
/*      */   public ServerPlayer(MinecraftServer $$0, ServerLevel $$1, GameProfile $$2, ClientInformation $$3) {
/*  274 */     super($$1, $$1.getSharedSpawnPos(), $$1.getSharedSpawnAngle(), $$2);
/*  275 */     this.textFilter = $$0.createTextFilterForPlayer(this);
/*  276 */     this.gameMode = $$0.createGameModeForPlayer(this);
/*      */     
/*  278 */     this.server = $$0;
/*  279 */     this.stats = $$0.getPlayerList().getPlayerStats(this);
/*  280 */     this.advancements = $$0.getPlayerList().getPlayerAdvancements(this);
/*  281 */     setMaxUpStep(1.0F);
/*      */     
/*  283 */     fudgeSpawnLocation($$1);
/*  284 */     updateOptions($$3);
/*      */   }
/*      */   
/*      */   private void fudgeSpawnLocation(ServerLevel $$0) {
/*  288 */     BlockPos $$1 = $$0.getSharedSpawnPos();
/*      */ 
/*      */     
/*  291 */     if ($$0.dimensionType().hasSkyLight() && $$0.getServer().getWorldData().getGameType() != GameType.ADVENTURE) {
/*  292 */       int $$2 = Math.max(0, this.server.getSpawnRadius($$0));
/*  293 */       int $$3 = Mth.floor($$0.getWorldBorder().getDistanceToBorder($$1.getX(), $$1.getZ()));
/*  294 */       if ($$3 < $$2) {
/*  295 */         $$2 = $$3;
/*      */       }
/*  297 */       if ($$3 <= 1) {
/*  298 */         $$2 = 1;
/*      */       }
/*      */       
/*  301 */       long $$4 = ($$2 * 2 + 1);
/*  302 */       long $$5 = $$4 * $$4;
/*  303 */       int $$6 = ($$5 > 2147483647L) ? Integer.MAX_VALUE : (int)$$5;
/*  304 */       int $$7 = getCoprime($$6);
/*  305 */       int $$8 = RandomSource.create().nextInt($$6);
/*      */       
/*  307 */       for (int $$9 = 0; $$9 < $$6; $$9++) {
/*  308 */         int $$10 = ($$8 + $$7 * $$9) % $$6;
/*  309 */         int $$11 = $$10 % ($$2 * 2 + 1);
/*  310 */         int $$12 = $$10 / ($$2 * 2 + 1);
/*      */         
/*  312 */         BlockPos $$13 = PlayerRespawnLogic.getOverworldRespawnPos($$0, $$1.getX() + $$11 - $$2, $$1.getZ() + $$12 - $$2);
/*      */         
/*  314 */         if ($$13 != null) {
/*  315 */           moveTo($$13, 0.0F, 0.0F);
/*  316 */           if ($$0.noCollision((Entity)this)) {
/*      */             break;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } else {
/*  322 */       moveTo($$1, 0.0F, 0.0F);
/*  323 */       while (!$$0.noCollision((Entity)this) && getY() < ($$0.getMaxBuildHeight() - 1)) {
/*  324 */         setPos(getX(), getY() + 1.0D, getZ());
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private int getCoprime(int $$0) {
/*  331 */     return ($$0 <= 16) ? ($$0 - 1) : 17;
/*      */   }
/*      */ 
/*      */   
/*      */   public void readAdditionalSaveData(CompoundTag $$0) {
/*  336 */     super.readAdditionalSaveData($$0);
/*      */     
/*  338 */     if ($$0.contains("warden_spawn_tracker", 10)) {
/*      */       
/*  340 */       Objects.requireNonNull(LOGGER); WardenSpawnTracker.CODEC.parse(new Dynamic((DynamicOps)NbtOps.INSTANCE, $$0.get("warden_spawn_tracker"))).resultOrPartial(LOGGER::error)
/*  341 */         .ifPresent($$0 -> this.wardenSpawnTracker = $$0);
/*      */     } 
/*      */     
/*  344 */     if ($$0.contains("enteredNetherPosition", 10)) {
/*  345 */       CompoundTag $$1 = $$0.getCompound("enteredNetherPosition");
/*  346 */       this.enteredNetherPosition = new Vec3($$1.getDouble("x"), $$1.getDouble("y"), $$1.getDouble("z"));
/*      */     } 
/*      */     
/*  349 */     this.seenCredits = $$0.getBoolean("seenCredits");
/*      */     
/*  351 */     if ($$0.contains("recipeBook", 10)) {
/*  352 */       this.recipeBook.fromNbt($$0.getCompound("recipeBook"), this.server.getRecipeManager());
/*      */     }
/*      */     
/*  355 */     if (isSleeping()) {
/*  356 */       stopSleeping();
/*      */     }
/*      */     
/*  359 */     if ($$0.contains("SpawnX", 99) && $$0.contains("SpawnY", 99) && $$0.contains("SpawnZ", 99)) {
/*  360 */       this.respawnPosition = new BlockPos($$0.getInt("SpawnX"), $$0.getInt("SpawnY"), $$0.getInt("SpawnZ"));
/*  361 */       this.respawnForced = $$0.getBoolean("SpawnForced");
/*  362 */       this.respawnAngle = $$0.getFloat("SpawnAngle");
/*  363 */       if ($$0.contains("SpawnDimension")) {
/*  364 */         Objects.requireNonNull(LOGGER); this.respawnDimension = Level.RESOURCE_KEY_CODEC.parse((DynamicOps)NbtOps.INSTANCE, $$0.get("SpawnDimension")).resultOrPartial(LOGGER::error).orElse(Level.OVERWORLD);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void addAdditionalSaveData(CompoundTag $$0) {
/*  371 */     super.addAdditionalSaveData($$0);
/*      */ 
/*      */     
/*  374 */     Objects.requireNonNull(LOGGER); WardenSpawnTracker.CODEC.encodeStart((DynamicOps)NbtOps.INSTANCE, this.wardenSpawnTracker).resultOrPartial(LOGGER::error)
/*  375 */       .ifPresent($$1 -> $$0.put("warden_spawn_tracker", $$1));
/*      */     
/*  377 */     storeGameTypes($$0);
/*  378 */     $$0.putBoolean("seenCredits", this.seenCredits);
/*      */     
/*  380 */     if (this.enteredNetherPosition != null) {
/*  381 */       CompoundTag $$1 = new CompoundTag();
/*  382 */       $$1.putDouble("x", this.enteredNetherPosition.x);
/*  383 */       $$1.putDouble("y", this.enteredNetherPosition.y);
/*  384 */       $$1.putDouble("z", this.enteredNetherPosition.z);
/*  385 */       $$0.put("enteredNetherPosition", (Tag)$$1);
/*      */     } 
/*      */     
/*  388 */     Entity $$2 = getRootVehicle();
/*  389 */     Entity $$3 = getVehicle();
/*  390 */     if ($$3 != null && $$2 != this && $$2.hasExactlyOnePlayerPassenger()) {
/*  391 */       CompoundTag $$4 = new CompoundTag();
/*  392 */       CompoundTag $$5 = new CompoundTag();
/*  393 */       $$2.save($$5);
/*      */       
/*  395 */       $$4.putUUID("Attach", $$3.getUUID());
/*  396 */       $$4.put("Entity", (Tag)$$5);
/*  397 */       $$0.put("RootVehicle", (Tag)$$4);
/*      */     } 
/*      */     
/*  400 */     $$0.put("recipeBook", (Tag)this.recipeBook.toNbt());
/*      */     
/*  402 */     $$0.putString("Dimension", level().dimension().location().toString());
/*      */     
/*  404 */     if (this.respawnPosition != null) {
/*  405 */       $$0.putInt("SpawnX", this.respawnPosition.getX());
/*  406 */       $$0.putInt("SpawnY", this.respawnPosition.getY());
/*  407 */       $$0.putInt("SpawnZ", this.respawnPosition.getZ());
/*  408 */       $$0.putBoolean("SpawnForced", this.respawnForced);
/*  409 */       $$0.putFloat("SpawnAngle", this.respawnAngle);
/*  410 */       Objects.requireNonNull(LOGGER); ResourceLocation.CODEC.encodeStart((DynamicOps)NbtOps.INSTANCE, this.respawnDimension.location()).resultOrPartial(LOGGER::error).ifPresent($$1 -> $$0.put("SpawnDimension", $$1));
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setExperiencePoints(int $$0) {
/*  415 */     float $$1 = getXpNeededForNextLevel();
/*  416 */     float $$2 = ($$1 - 1.0F) / $$1;
/*  417 */     this.experienceProgress = Mth.clamp($$0 / $$1, 0.0F, $$2);
/*  418 */     this.lastSentExp = -1;
/*      */   }
/*      */   
/*      */   public void setExperienceLevels(int $$0) {
/*  422 */     this.experienceLevel = $$0;
/*  423 */     this.lastSentExp = -1;
/*      */   }
/*      */ 
/*      */   
/*      */   public void giveExperienceLevels(int $$0) {
/*  428 */     super.giveExperienceLevels($$0);
/*  429 */     this.lastSentExp = -1;
/*      */   }
/*      */ 
/*      */   
/*      */   public void onEnchantmentPerformed(ItemStack $$0, int $$1) {
/*  434 */     super.onEnchantmentPerformed($$0, $$1);
/*  435 */     this.lastSentExp = -1;
/*      */   }
/*      */   
/*      */   private void initMenu(AbstractContainerMenu $$0) {
/*  439 */     $$0.addSlotListener(this.containerListener);
/*  440 */     $$0.setSynchronizer(this.containerSynchronizer);
/*      */   }
/*      */   
/*      */   public void initInventoryMenu() {
/*  444 */     initMenu((AbstractContainerMenu)this.inventoryMenu);
/*      */   }
/*      */ 
/*      */   
/*      */   public void onEnterCombat() {
/*  449 */     super.onEnterCombat();
/*      */     
/*  451 */     this.connection.send((Packet)new ClientboundPlayerCombatEnterPacket());
/*      */   }
/*      */ 
/*      */   
/*      */   public void onLeaveCombat() {
/*  456 */     super.onLeaveCombat();
/*      */     
/*  458 */     this.connection.send((Packet)new ClientboundPlayerCombatEndPacket(getCombatTracker()));
/*      */   }
/*      */ 
/*      */   
/*      */   protected void onInsideBlock(BlockState $$0) {
/*  463 */     CriteriaTriggers.ENTER_BLOCK.trigger(this, $$0);
/*      */   }
/*      */ 
/*      */   
/*      */   protected ItemCooldowns createItemCooldowns() {
/*  468 */     return (ItemCooldowns)new ServerItemCooldowns(this);
/*      */   }
/*      */ 
/*      */   
/*      */   public void tick() {
/*  473 */     this.gameMode.tick();
/*      */     
/*  475 */     this.wardenSpawnTracker.tick();
/*      */     
/*  477 */     this.spawnInvulnerableTime--;
/*  478 */     if (this.invulnerableTime > 0) {
/*  479 */       this.invulnerableTime--;
/*      */     }
/*  481 */     this.containerMenu.broadcastChanges();
/*      */     
/*  483 */     if (!(level()).isClientSide && 
/*  484 */       !this.containerMenu.stillValid(this)) {
/*  485 */       closeContainer();
/*  486 */       this.containerMenu = (AbstractContainerMenu)this.inventoryMenu;
/*      */     } 
/*      */ 
/*      */     
/*  490 */     Entity $$0 = getCamera();
/*  491 */     if ($$0 != this) {
/*  492 */       if ($$0.isAlive()) {
/*      */         
/*  494 */         absMoveTo($$0.getX(), $$0.getY(), $$0.getZ(), $$0.getYRot(), $$0.getXRot());
/*  495 */         serverLevel().getChunkSource().move(this);
/*  496 */         if (wantsToStopRiding())
/*      */         {
/*  498 */           setCamera((Entity)this);
/*      */         }
/*      */       } else {
/*  501 */         setCamera((Entity)this);
/*      */       } 
/*      */     }
/*      */     
/*  505 */     CriteriaTriggers.TICK.trigger(this);
/*  506 */     if (this.levitationStartPos != null) {
/*  507 */       CriteriaTriggers.LEVITATION.trigger(this, this.levitationStartPos, this.tickCount - this.levitationStartTime);
/*      */     }
/*      */     
/*  510 */     trackStartFallingPosition();
/*  511 */     trackEnteredOrExitedLavaOnVehicle();
/*      */     
/*  513 */     this.advancements.flushDirty(this);
/*      */   }
/*      */   
/*      */   public void doTick() {
/*      */     try {
/*  518 */       if (!isSpectator() || !touchingUnloadedChunk()) {
/*  519 */         super.tick();
/*      */       }
/*      */       
/*  522 */       for (int $$0 = 0; $$0 < getInventory().getContainerSize(); $$0++) {
/*  523 */         ItemStack $$1 = getInventory().getItem($$0);
/*  524 */         if ($$1.getItem().isComplex()) {
/*  525 */           Packet<?> $$2 = ((ComplexItem)$$1.getItem()).getUpdatePacket($$1, level(), this);
/*  526 */           if ($$2 != null) {
/*  527 */             this.connection.send($$2);
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/*  532 */       if (getHealth() != this.lastSentHealth || this.lastSentFood != this.foodData.getFoodLevel() || ((this.foodData.getSaturationLevel() == 0.0F)) != this.lastFoodSaturationZero) {
/*  533 */         this.connection.send((Packet)new ClientboundSetHealthPacket(getHealth(), this.foodData.getFoodLevel(), this.foodData.getSaturationLevel()));
/*  534 */         this.lastSentHealth = getHealth();
/*  535 */         this.lastSentFood = this.foodData.getFoodLevel();
/*  536 */         this.lastFoodSaturationZero = (this.foodData.getSaturationLevel() == 0.0F);
/*      */       } 
/*      */       
/*  539 */       if (getHealth() + getAbsorptionAmount() != this.lastRecordedHealthAndAbsorption) {
/*  540 */         this.lastRecordedHealthAndAbsorption = getHealth() + getAbsorptionAmount();
/*  541 */         updateScoreForCriteria(ObjectiveCriteria.HEALTH, Mth.ceil(this.lastRecordedHealthAndAbsorption));
/*      */       } 
/*      */       
/*  544 */       if (this.foodData.getFoodLevel() != this.lastRecordedFoodLevel) {
/*  545 */         this.lastRecordedFoodLevel = this.foodData.getFoodLevel();
/*  546 */         updateScoreForCriteria(ObjectiveCriteria.FOOD, Mth.ceil(this.lastRecordedFoodLevel));
/*      */       } 
/*      */       
/*  549 */       if (getAirSupply() != this.lastRecordedAirLevel) {
/*  550 */         this.lastRecordedAirLevel = getAirSupply();
/*  551 */         updateScoreForCriteria(ObjectiveCriteria.AIR, Mth.ceil(this.lastRecordedAirLevel));
/*      */       } 
/*      */       
/*  554 */       if (getArmorValue() != this.lastRecordedArmor) {
/*  555 */         this.lastRecordedArmor = getArmorValue();
/*  556 */         updateScoreForCriteria(ObjectiveCriteria.ARMOR, Mth.ceil(this.lastRecordedArmor));
/*      */       } 
/*      */       
/*  559 */       if (this.totalExperience != this.lastRecordedExperience) {
/*  560 */         this.lastRecordedExperience = this.totalExperience;
/*  561 */         updateScoreForCriteria(ObjectiveCriteria.EXPERIENCE, Mth.ceil(this.lastRecordedExperience));
/*      */       } 
/*      */       
/*  564 */       if (this.experienceLevel != this.lastRecordedLevel) {
/*  565 */         this.lastRecordedLevel = this.experienceLevel;
/*  566 */         updateScoreForCriteria(ObjectiveCriteria.LEVEL, Mth.ceil(this.lastRecordedLevel));
/*      */       } 
/*      */       
/*  569 */       if (this.totalExperience != this.lastSentExp) {
/*  570 */         this.lastSentExp = this.totalExperience;
/*  571 */         this.connection.send((Packet)new ClientboundSetExperiencePacket(this.experienceProgress, this.totalExperience, this.experienceLevel));
/*      */       } 
/*      */       
/*  574 */       if (this.tickCount % 20 == 0) {
/*  575 */         CriteriaTriggers.LOCATION.trigger(this);
/*      */       }
/*  577 */     } catch (Throwable $$3) {
/*  578 */       CrashReport $$4 = CrashReport.forThrowable($$3, "Ticking player");
/*  579 */       CrashReportCategory $$5 = $$4.addCategory("Player being ticked");
/*      */       
/*  581 */       fillCrashReportCategory($$5);
/*      */       
/*  583 */       throw new ReportedException($$4);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void resetFallDistance() {
/*  589 */     if (getHealth() > 0.0F && this.startingToFallPosition != null) {
/*  590 */       CriteriaTriggers.FALL_FROM_HEIGHT.trigger(this, this.startingToFallPosition);
/*      */     }
/*  592 */     this.startingToFallPosition = null;
/*  593 */     super.resetFallDistance();
/*      */   }
/*      */   
/*      */   public void trackStartFallingPosition() {
/*  597 */     if (this.fallDistance > 0.0F && this.startingToFallPosition == null) {
/*  598 */       this.startingToFallPosition = position();
/*      */     }
/*      */   }
/*      */   
/*      */   public void trackEnteredOrExitedLavaOnVehicle() {
/*  603 */     if (getVehicle() != null && getVehicle().isInLava()) {
/*  604 */       if (this.enteredLavaOnVehiclePosition == null) {
/*  605 */         this.enteredLavaOnVehiclePosition = position();
/*      */       } else {
/*  607 */         CriteriaTriggers.RIDE_ENTITY_IN_LAVA_TRIGGER.trigger(this, this.enteredLavaOnVehiclePosition);
/*      */       } 
/*      */     }
/*  610 */     if (this.enteredLavaOnVehiclePosition != null && (getVehicle() == null || !getVehicle().isInLava())) {
/*  611 */       this.enteredLavaOnVehiclePosition = null;
/*      */     }
/*      */   }
/*      */   
/*      */   private void updateScoreForCriteria(ObjectiveCriteria $$0, int $$1) {
/*  616 */     getScoreboard().forAllObjectives($$0, (ScoreHolder)this, $$1 -> $$1.set($$0));
/*      */   }
/*      */ 
/*      */   
/*      */   public void die(DamageSource $$0) {
/*  621 */     gameEvent(GameEvent.ENTITY_DIE);
/*  622 */     boolean $$1 = level().getGameRules().getBoolean(GameRules.RULE_SHOWDEATHMESSAGES);
/*      */     
/*  624 */     if ($$1) {
/*  625 */       Component $$2 = getCombatTracker().getDeathMessage();
/*  626 */       this.connection.send((Packet)new ClientboundPlayerCombatKillPacket(getId(), $$2), 
/*  627 */           PacketSendListener.exceptionallySend(() -> {
/*      */               int $$1 = 256;
/*      */               
/*      */               String $$2 = $$0.getString(256);
/*      */               MutableComponent mutableComponent1 = Component.translatable("death.attack.message_too_long", new Object[] { Component.literal($$2).withStyle(ChatFormatting.YELLOW) });
/*      */               MutableComponent mutableComponent2 = Component.translatable("death.attack.even_more_magic", new Object[] { getDisplayName() }).withStyle(());
/*      */               return (Packet)new ClientboundPlayerCombatKillPacket(getId(), (Component)mutableComponent2);
/*      */             }));
/*  635 */       PlayerTeam playerTeam = getTeam();
/*  636 */       if (playerTeam == null || playerTeam.getDeathMessageVisibility() == Team.Visibility.ALWAYS) {
/*  637 */         this.server.getPlayerList().broadcastSystemMessage($$2, false);
/*  638 */       } else if (playerTeam.getDeathMessageVisibility() == Team.Visibility.HIDE_FOR_OTHER_TEAMS) {
/*  639 */         this.server.getPlayerList().broadcastSystemToTeam(this, $$2);
/*  640 */       } else if (playerTeam.getDeathMessageVisibility() == Team.Visibility.HIDE_FOR_OWN_TEAM) {
/*  641 */         this.server.getPlayerList().broadcastSystemToAllExceptTeam(this, $$2);
/*      */       } 
/*      */     } else {
/*  644 */       this.connection.send((Packet)new ClientboundPlayerCombatKillPacket(getId(), CommonComponents.EMPTY));
/*      */     } 
/*  646 */     removeEntitiesOnShoulder();
/*  647 */     if (level().getGameRules().getBoolean(GameRules.RULE_FORGIVE_DEAD_PLAYERS)) {
/*  648 */       tellNeutralMobsThatIDied();
/*      */     }
/*      */     
/*  651 */     if (!isSpectator()) {
/*  652 */       dropAllDeathLoot($$0);
/*      */     }
/*      */     
/*  655 */     getScoreboard().forAllObjectives(ObjectiveCriteria.DEATH_COUNT, (ScoreHolder)this, ScoreAccess::increment);
/*      */     
/*  657 */     LivingEntity $$4 = getKillCredit();
/*  658 */     if ($$4 != null) {
/*  659 */       awardStat(Stats.ENTITY_KILLED_BY.get($$4.getType()));
/*  660 */       $$4.awardKillScore((Entity)this, this.deathScore, $$0);
/*      */       
/*  662 */       createWitherRose($$4);
/*      */     } 
/*      */     
/*  665 */     level().broadcastEntityEvent((Entity)this, (byte)3);
/*      */     
/*  667 */     awardStat(Stats.DEATHS);
/*  668 */     resetStat(Stats.CUSTOM.get(Stats.TIME_SINCE_DEATH));
/*  669 */     resetStat(Stats.CUSTOM.get(Stats.TIME_SINCE_REST));
/*  670 */     clearFire();
/*  671 */     setTicksFrozen(0);
/*  672 */     setSharedFlagOnFire(false);
/*  673 */     getCombatTracker().recheckStatus();
/*  674 */     setLastDeathLocation(Optional.of(GlobalPos.of(level().dimension(), blockPosition())));
/*      */   }
/*      */   
/*      */   private void tellNeutralMobsThatIDied() {
/*  678 */     AABB $$0 = (new AABB(blockPosition())).inflate(32.0D, 10.0D, 32.0D);
/*  679 */     level().getEntitiesOfClass(Mob.class, $$0, EntitySelector.NO_SPECTATORS).stream()
/*  680 */       .filter($$0 -> $$0 instanceof NeutralMob)
/*  681 */       .forEach($$0 -> ((NeutralMob)$$0).playerDied(this));
/*      */   }
/*      */ 
/*      */   
/*      */   public void awardKillScore(Entity $$0, int $$1, DamageSource $$2) {
/*  686 */     if ($$0 == this) {
/*      */       return;
/*      */     }
/*  689 */     super.awardKillScore($$0, $$1, $$2);
/*  690 */     increaseScore($$1);
/*      */     
/*  692 */     getScoreboard().forAllObjectives(ObjectiveCriteria.KILL_COUNT_ALL, (ScoreHolder)this, ScoreAccess::increment);
/*      */     
/*  694 */     if ($$0 instanceof Player) {
/*  695 */       awardStat(Stats.PLAYER_KILLS);
/*  696 */       getScoreboard().forAllObjectives(ObjectiveCriteria.KILL_COUNT_PLAYERS, (ScoreHolder)this, ScoreAccess::increment);
/*      */     } else {
/*  698 */       awardStat(Stats.MOB_KILLS);
/*      */     } 
/*      */     
/*  701 */     handleTeamKill((ScoreHolder)this, (ScoreHolder)$$0, ObjectiveCriteria.TEAM_KILL);
/*  702 */     handleTeamKill((ScoreHolder)$$0, (ScoreHolder)this, ObjectiveCriteria.KILLED_BY_TEAM);
/*      */     
/*  704 */     CriteriaTriggers.PLAYER_KILLED_ENTITY.trigger(this, $$0, $$2);
/*      */   }
/*      */   
/*      */   private void handleTeamKill(ScoreHolder $$0, ScoreHolder $$1, ObjectiveCriteria[] $$2) {
/*  708 */     PlayerTeam $$3 = getScoreboard().getPlayersTeam($$1.getScoreboardName());
/*  709 */     if ($$3 != null) {
/*  710 */       int $$4 = $$3.getColor().getId();
/*  711 */       if ($$4 >= 0 && $$4 < $$2.length) {
/*  712 */         getScoreboard().forAllObjectives($$2[$$4], $$0, ScoreAccess::increment);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hurt(DamageSource $$0, float $$1) {
/*  719 */     if (isInvulnerableTo($$0)) {
/*  720 */       return false;
/*      */     }
/*      */     
/*  723 */     boolean $$2 = (this.server.isDedicatedServer() && isPvpAllowed() && $$0.is(DamageTypeTags.IS_FALL));
/*  724 */     if (!$$2 && this.spawnInvulnerableTime > 0 && !$$0.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
/*  725 */       return false;
/*      */     }
/*      */     
/*  728 */     Entity $$3 = $$0.getEntity();
/*  729 */     if ($$3 instanceof Player) { Player $$4 = (Player)$$3; if (!canHarmPlayer($$4))
/*  730 */         return false;  }
/*      */     
/*  732 */     if ($$3 instanceof AbstractArrow) { AbstractArrow $$5 = (AbstractArrow)$$3;
/*  733 */       Entity $$6 = $$5.getOwner();
/*  734 */       if ($$6 instanceof Player) { Player $$7 = (Player)$$6; if (!canHarmPlayer($$7))
/*  735 */           return false;  }
/*      */        }
/*      */     
/*  738 */     return super.hurt($$0, $$1);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canHarmPlayer(Player $$0) {
/*  743 */     if (!isPvpAllowed()) {
/*  744 */       return false;
/*      */     }
/*  746 */     return super.canHarmPlayer($$0);
/*      */   }
/*      */   
/*      */   private boolean isPvpAllowed() {
/*  750 */     return this.server.isPvpAllowed();
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   protected PortalInfo findDimensionEntryPoint(ServerLevel $$0) {
/*  756 */     PortalInfo $$1 = super.findDimensionEntryPoint($$0);
/*      */     
/*  758 */     if ($$1 != null && level().dimension() == Level.OVERWORLD && $$0.dimension() == Level.END) {
/*      */       
/*  760 */       Vec3 $$2 = $$1.pos.add(0.0D, -1.0D, 0.0D);
/*      */       
/*  762 */       return new PortalInfo($$2, Vec3.ZERO, 90.0F, 0.0F);
/*      */     } 
/*      */     
/*  765 */     return $$1;
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public Entity changeDimension(ServerLevel $$0) {
/*  771 */     this.isChangingDimension = true;
/*  772 */     ServerLevel $$1 = serverLevel();
/*  773 */     ResourceKey<Level> $$2 = $$1.dimension();
/*      */     
/*  775 */     if ($$2 == Level.END && $$0.dimension() == Level.OVERWORLD) {
/*  776 */       unRide();
/*  777 */       serverLevel().removePlayerImmediately(this, Entity.RemovalReason.CHANGED_DIMENSION);
/*  778 */       if (!this.wonGame) {
/*  779 */         this.wonGame = true;
/*  780 */         this.connection.send((Packet)new ClientboundGameEventPacket(ClientboundGameEventPacket.WIN_GAME, this.seenCredits ? 0.0F : 1.0F));
/*  781 */         this.seenCredits = true;
/*      */       } 
/*  783 */       return (Entity)this;
/*      */     } 
/*      */     
/*  786 */     LevelData $$3 = $$0.getLevelData();
/*      */     
/*  788 */     this.connection.send((Packet)new ClientboundRespawnPacket(
/*  789 */           createCommonSpawnInfo($$0), (byte)3));
/*      */ 
/*      */     
/*  792 */     this.connection.send((Packet)new ClientboundChangeDifficultyPacket($$3.getDifficulty(), $$3.isDifficultyLocked()));
/*  793 */     PlayerList $$4 = this.server.getPlayerList();
/*      */     
/*  795 */     $$4.sendPlayerPermissionLevel(this);
/*      */     
/*  797 */     $$1.removePlayerImmediately(this, Entity.RemovalReason.CHANGED_DIMENSION);
/*      */     
/*  799 */     unsetRemoved();
/*      */     
/*  801 */     PortalInfo $$5 = findDimensionEntryPoint($$0);
/*  802 */     if ($$5 != null) {
/*  803 */       $$1.getProfiler().push("moving");
/*  804 */       if ($$2 == Level.OVERWORLD && $$0.dimension() == Level.NETHER) {
/*  805 */         this.enteredNetherPosition = position();
/*  806 */       } else if ($$0.dimension() == Level.END) {
/*  807 */         createEndPlatform($$0, BlockPos.containing((Position)$$5.pos));
/*      */       } 
/*  809 */       $$1.getProfiler().pop();
/*      */       
/*  811 */       $$1.getProfiler().push("placing");
/*      */       
/*  813 */       setServerLevel($$0);
/*      */       
/*  815 */       this.connection.teleport($$5.pos.x, $$5.pos.y, $$5.pos.z, $$5.yRot, $$5.xRot);
/*  816 */       this.connection.resetPosition();
/*      */       
/*  818 */       $$0.addDuringPortalTeleport(this);
/*      */       
/*  820 */       $$1.getProfiler().pop();
/*      */       
/*  822 */       triggerDimensionChangeTriggers($$1);
/*      */       
/*  824 */       this.connection.send((Packet)new ClientboundPlayerAbilitiesPacket(getAbilities()));
/*  825 */       $$4.sendLevelInfo(this, $$0);
/*  826 */       $$4.sendAllPlayerInfo(this);
/*      */       
/*  828 */       for (MobEffectInstance $$6 : getActiveEffects()) {
/*  829 */         this.connection.send((Packet)new ClientboundUpdateMobEffectPacket(getId(), $$6));
/*      */       }
/*  831 */       this.connection.send((Packet)new ClientboundLevelEventPacket(1032, BlockPos.ZERO, 0, false));
/*      */       
/*  833 */       this.lastSentExp = -1;
/*  834 */       this.lastSentHealth = -1.0F;
/*  835 */       this.lastSentFood = -1;
/*      */     } 
/*  837 */     return (Entity)this;
/*      */   }
/*      */   
/*      */   private void createEndPlatform(ServerLevel $$0, BlockPos $$1) {
/*  841 */     BlockPos.MutableBlockPos $$2 = $$1.mutable();
/*      */     
/*  843 */     for (int $$3 = -2; $$3 <= 2; $$3++) {
/*  844 */       for (int $$4 = -2; $$4 <= 2; $$4++) {
/*  845 */         for (int $$5 = -1; $$5 < 3; $$5++) {
/*  846 */           BlockState $$6 = ($$5 == -1) ? Blocks.OBSIDIAN.defaultBlockState() : Blocks.AIR.defaultBlockState();
/*  847 */           $$0.setBlockAndUpdate((BlockPos)$$2.set((Vec3i)$$1).move($$4, $$5, $$3), $$6);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Optional<BlockUtil.FoundRectangle> getExitPortal(ServerLevel $$0, BlockPos $$1, boolean $$2, WorldBorder $$3) {
/*  858 */     Optional<BlockUtil.FoundRectangle> $$4 = super.getExitPortal($$0, $$1, $$2, $$3);
/*  859 */     if ($$4.isPresent()) {
/*  860 */       return $$4;
/*      */     }
/*      */     
/*  863 */     Direction.Axis $$5 = level().getBlockState(this.portalEntrancePos).getOptionalValue((Property)NetherPortalBlock.AXIS).orElse(Direction.Axis.X);
/*  864 */     Optional<BlockUtil.FoundRectangle> $$6 = $$0.getPortalForcer().createPortal($$1, $$5);
/*  865 */     if ($$6.isEmpty()) {
/*  866 */       LOGGER.error("Unable to create a portal, likely target out of worldborder");
/*      */     }
/*      */     
/*  869 */     return $$6;
/*      */   }
/*      */   
/*      */   private void triggerDimensionChangeTriggers(ServerLevel $$0) {
/*  873 */     ResourceKey<Level> $$1 = $$0.dimension();
/*  874 */     ResourceKey<Level> $$2 = level().dimension();
/*  875 */     CriteriaTriggers.CHANGED_DIMENSION.trigger(this, $$1, $$2);
/*      */     
/*  877 */     if ($$1 == Level.NETHER && $$2 == Level.OVERWORLD && this.enteredNetherPosition != null) {
/*  878 */       CriteriaTriggers.NETHER_TRAVEL.trigger(this, this.enteredNetherPosition);
/*      */     }
/*  880 */     if ($$2 != Level.NETHER) {
/*  881 */       this.enteredNetherPosition = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean broadcastToPlayer(ServerPlayer $$0) {
/*  887 */     if ($$0.isSpectator()) {
/*  888 */       return (getCamera() == this);
/*      */     }
/*      */     
/*  891 */     if (isSpectator()) {
/*  892 */       return false;
/*      */     }
/*      */     
/*  895 */     return super.broadcastToPlayer($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public void take(Entity $$0, int $$1) {
/*  900 */     super.take($$0, $$1);
/*  901 */     this.containerMenu.broadcastChanges();
/*      */   }
/*      */ 
/*      */   
/*      */   public Either<Player.BedSleepingProblem, Unit> startSleepInBed(BlockPos $$0) {
/*  906 */     Direction $$1 = (Direction)level().getBlockState($$0).getValue((Property)HorizontalDirectionalBlock.FACING);
/*  907 */     if (isSleeping() || !isAlive()) {
/*  908 */       return Either.left(Player.BedSleepingProblem.OTHER_PROBLEM);
/*      */     }
/*      */     
/*  911 */     if (!level().dimensionType().natural())
/*      */     {
/*  913 */       return Either.left(Player.BedSleepingProblem.NOT_POSSIBLE_HERE);
/*      */     }
/*      */     
/*  916 */     if (!bedInRange($$0, $$1)) {
/*  917 */       return Either.left(Player.BedSleepingProblem.TOO_FAR_AWAY);
/*      */     }
/*      */     
/*  920 */     if (bedBlocked($$0, $$1)) {
/*  921 */       return Either.left(Player.BedSleepingProblem.OBSTRUCTED);
/*      */     }
/*      */ 
/*      */     
/*  925 */     setRespawnPosition(level().dimension(), $$0, getYRot(), false, true);
/*  926 */     if (level().isDay()) {
/*  927 */       return Either.left(Player.BedSleepingProblem.NOT_POSSIBLE_NOW);
/*      */     }
/*      */     
/*  930 */     if (!isCreative()) {
/*  931 */       double $$2 = 8.0D;
/*  932 */       double $$3 = 5.0D;
/*  933 */       Vec3 $$4 = Vec3.atBottomCenterOf((Vec3i)$$0);
/*  934 */       List<Monster> $$5 = level().getEntitiesOfClass(Monster.class, new AABB($$4.x() - 8.0D, $$4.y() - 5.0D, $$4.z() - 8.0D, $$4.x() + 8.0D, $$4.y() + 5.0D, $$4.z() + 8.0D), $$0 -> $$0.isPreventingPlayerRest(this));
/*  935 */       if (!$$5.isEmpty()) {
/*  936 */         return Either.left(Player.BedSleepingProblem.NOT_SAFE);
/*      */       }
/*      */     } 
/*      */     
/*  940 */     Either<Player.BedSleepingProblem, Unit> $$6 = super.startSleepInBed($$0).ifRight($$0 -> {
/*      */           awardStat(Stats.SLEEP_IN_BED);
/*      */           CriteriaTriggers.SLEPT_IN_BED.trigger(this);
/*      */         });
/*  944 */     if (!serverLevel().canSleepThroughNights()) {
/*  945 */       displayClientMessage((Component)Component.translatable("sleep.not_possible"), true);
/*      */     }
/*      */     
/*  948 */     ((ServerLevel)level()).updateSleepingPlayerList();
/*  949 */     return $$6;
/*      */   }
/*      */ 
/*      */   
/*      */   public void startSleeping(BlockPos $$0) {
/*  954 */     resetStat(Stats.CUSTOM.get(Stats.TIME_SINCE_REST));
/*  955 */     super.startSleeping($$0);
/*      */   }
/*      */   
/*      */   private boolean bedInRange(BlockPos $$0, Direction $$1) {
/*  959 */     return (isReachableBedBlock($$0) || isReachableBedBlock($$0.relative($$1.getOpposite())));
/*      */   }
/*      */   
/*      */   private boolean isReachableBedBlock(BlockPos $$0) {
/*  963 */     Vec3 $$1 = Vec3.atBottomCenterOf((Vec3i)$$0);
/*  964 */     return (Math.abs(getX() - $$1.x()) <= 3.0D && Math.abs(getY() - $$1.y()) <= 2.0D && Math.abs(getZ() - $$1.z()) <= 3.0D);
/*      */   }
/*      */   
/*      */   private boolean bedBlocked(BlockPos $$0, Direction $$1) {
/*  968 */     BlockPos $$2 = $$0.above();
/*  969 */     return (!freeAt($$2) || !freeAt($$2.relative($$1.getOpposite())));
/*      */   }
/*      */ 
/*      */   
/*      */   public void stopSleepInBed(boolean $$0, boolean $$1) {
/*  974 */     if (isSleeping()) {
/*  975 */       serverLevel().getChunkSource().broadcastAndSend((Entity)this, (Packet<?>)new ClientboundAnimatePacket((Entity)this, 2));
/*      */     }
/*  977 */     super.stopSleepInBed($$0, $$1);
/*  978 */     if (this.connection != null) {
/*  979 */       this.connection.teleport(getX(), getY(), getZ(), getYRot(), getXRot());
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void dismountTo(double $$0, double $$1, double $$2) {
/*  985 */     removeVehicle();
/*  986 */     setPos($$0, $$1, $$2);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isInvulnerableTo(DamageSource $$0) {
/*  991 */     return (super.isInvulnerableTo($$0) || isChangingDimension());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void checkFallDamage(double $$0, boolean $$1, BlockState $$2, BlockPos $$3) {}
/*      */ 
/*      */ 
/*      */   
/*      */   protected void onChangedBlock(BlockPos $$0) {
/* 1001 */     if (!isSpectator()) {
/* 1002 */       super.onChangedBlock($$0);
/*      */     }
/*      */   }
/*      */   
/*      */   public void doCheckFallDamage(double $$0, double $$1, double $$2, boolean $$3) {
/* 1007 */     if (touchingUnloadedChunk()) {
/*      */       return;
/*      */     }
/* 1010 */     checkSupportingBlock($$3, new Vec3($$0, $$1, $$2));
/* 1011 */     BlockPos $$4 = getOnPosLegacy();
/* 1012 */     super.checkFallDamage($$1, $$3, level().getBlockState($$4), $$4);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void pushEntities() {
/* 1018 */     if (level().tickRateManager().runsNormally()) {
/* 1019 */       super.pushEntities();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void openTextEdit(SignBlockEntity $$0, boolean $$1) {
/* 1026 */     this.connection.send((Packet)new ClientboundBlockUpdatePacket((BlockGetter)level(), $$0.getBlockPos()));
/* 1027 */     this.connection.send((Packet)new ClientboundOpenSignEditorPacket($$0.getBlockPos(), $$1));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void nextContainerCounter() {
/* 1034 */     this.containerCounter = this.containerCounter % 100 + 1;
/*      */   }
/*      */ 
/*      */   
/*      */   public OptionalInt openMenu(@Nullable MenuProvider $$0) {
/* 1039 */     if ($$0 == null) {
/* 1040 */       return OptionalInt.empty();
/*      */     }
/*      */     
/* 1043 */     if (this.containerMenu != this.inventoryMenu) {
/* 1044 */       closeContainer();
/*      */     }
/*      */     
/* 1047 */     nextContainerCounter();
/*      */     
/* 1049 */     AbstractContainerMenu $$1 = $$0.createMenu(this.containerCounter, getInventory(), this);
/* 1050 */     if ($$1 == null) {
/* 1051 */       if (isSpectator()) {
/* 1052 */         displayClientMessage((Component)Component.translatable("container.spectatorCantOpen").withStyle(ChatFormatting.RED), true);
/*      */       }
/* 1054 */       return OptionalInt.empty();
/*      */     } 
/* 1056 */     this.connection.send((Packet)new ClientboundOpenScreenPacket($$1.containerId, $$1.getType(), $$0.getDisplayName()));
/* 1057 */     initMenu($$1);
/* 1058 */     this.containerMenu = $$1;
/* 1059 */     return OptionalInt.of(this.containerCounter);
/*      */   }
/*      */ 
/*      */   
/*      */   public void sendMerchantOffers(int $$0, MerchantOffers $$1, int $$2, int $$3, boolean $$4, boolean $$5) {
/* 1064 */     this.connection.send((Packet)new ClientboundMerchantOffersPacket($$0, $$1, $$2, $$3, $$4, $$5));
/*      */   }
/*      */ 
/*      */   
/*      */   public void openHorseInventory(AbstractHorse $$0, Container $$1) {
/* 1069 */     if (this.containerMenu != this.inventoryMenu) {
/* 1070 */       closeContainer();
/*      */     }
/* 1072 */     nextContainerCounter();
/* 1073 */     this.connection.send((Packet)new ClientboundHorseScreenOpenPacket(this.containerCounter, $$1.getContainerSize(), $$0.getId()));
/* 1074 */     this.containerMenu = (AbstractContainerMenu)new HorseInventoryMenu(this.containerCounter, getInventory(), $$1, $$0);
/* 1075 */     initMenu(this.containerMenu);
/*      */   }
/*      */ 
/*      */   
/*      */   public void openItemGui(ItemStack $$0, InteractionHand $$1) {
/* 1080 */     if ($$0.is(Items.WRITTEN_BOOK)) {
/*      */ 
/*      */       
/* 1083 */       if (WrittenBookItem.resolveBookComponents($$0, createCommandSourceStack(), this)) {
/* 1084 */         this.containerMenu.broadcastChanges();
/*      */       }
/*      */       
/* 1087 */       this.connection.send((Packet)new ClientboundOpenBookPacket($$1));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void openCommandBlock(CommandBlockEntity $$0) {
/* 1093 */     this.connection.send((Packet)ClientboundBlockEntityDataPacket.create((BlockEntity)$$0, BlockEntity::saveWithoutMetadata));
/*      */   }
/*      */ 
/*      */   
/*      */   public void closeContainer() {
/* 1098 */     this.connection.send((Packet)new ClientboundContainerClosePacket(this.containerMenu.containerId));
/* 1099 */     doCloseContainer();
/*      */   }
/*      */ 
/*      */   
/*      */   public void doCloseContainer() {
/* 1104 */     this.containerMenu.removed(this);
/*      */     
/* 1106 */     this.inventoryMenu.transferState(this.containerMenu);
/* 1107 */     this.containerMenu = (AbstractContainerMenu)this.inventoryMenu;
/*      */   }
/*      */   
/*      */   public void setPlayerInput(float $$0, float $$1, boolean $$2, boolean $$3) {
/* 1111 */     if (isPassenger()) {
/* 1112 */       if ($$0 >= -1.0F && $$0 <= 1.0F) {
/* 1113 */         this.xxa = $$0;
/*      */       }
/* 1115 */       if ($$1 >= -1.0F && $$1 <= 1.0F) {
/* 1116 */         this.zza = $$1;
/*      */       }
/* 1118 */       this.jumping = $$2;
/* 1119 */       setShiftKeyDown($$3);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void travel(Vec3 $$0) {
/* 1125 */     double $$1 = getX();
/* 1126 */     double $$2 = getY();
/* 1127 */     double $$3 = getZ();
/* 1128 */     super.travel($$0);
/* 1129 */     checkMovementStatistics(getX() - $$1, getY() - $$2, getZ() - $$3);
/*      */   }
/*      */ 
/*      */   
/*      */   public void rideTick() {
/* 1134 */     double $$0 = getX();
/* 1135 */     double $$1 = getY();
/* 1136 */     double $$2 = getZ();
/* 1137 */     super.rideTick();
/* 1138 */     checkRidingStatistics(getX() - $$0, getY() - $$1, getZ() - $$2);
/*      */   }
/*      */   
/*      */   public void checkMovementStatistics(double $$0, double $$1, double $$2) {
/* 1142 */     if (isPassenger() || didNotMove($$0, $$1, $$2)) {
/*      */       return;
/*      */     }
/*      */     
/* 1146 */     if (isSwimming()) {
/* 1147 */       int $$3 = Math.round((float)Math.sqrt($$0 * $$0 + $$1 * $$1 + $$2 * $$2) * 100.0F);
/* 1148 */       if ($$3 > 0) {
/* 1149 */         awardStat(Stats.SWIM_ONE_CM, $$3);
/* 1150 */         causeFoodExhaustion(0.01F * $$3 * 0.01F);
/*      */       } 
/* 1152 */     } else if (isEyeInFluid(FluidTags.WATER)) {
/* 1153 */       int $$4 = Math.round((float)Math.sqrt($$0 * $$0 + $$1 * $$1 + $$2 * $$2) * 100.0F);
/* 1154 */       if ($$4 > 0) {
/* 1155 */         awardStat(Stats.WALK_UNDER_WATER_ONE_CM, $$4);
/* 1156 */         causeFoodExhaustion(0.01F * $$4 * 0.01F);
/*      */       } 
/* 1158 */     } else if (isInWater()) {
/* 1159 */       int $$5 = Math.round((float)Math.sqrt($$0 * $$0 + $$2 * $$2) * 100.0F);
/* 1160 */       if ($$5 > 0) {
/* 1161 */         awardStat(Stats.WALK_ON_WATER_ONE_CM, $$5);
/* 1162 */         causeFoodExhaustion(0.01F * $$5 * 0.01F);
/*      */       } 
/* 1164 */     } else if (onClimbable()) {
/* 1165 */       if ($$1 > 0.0D) {
/* 1166 */         awardStat(Stats.CLIMB_ONE_CM, (int)Math.round($$1 * 100.0D));
/*      */       }
/* 1168 */     } else if (onGround()) {
/* 1169 */       int $$6 = Math.round((float)Math.sqrt($$0 * $$0 + $$2 * $$2) * 100.0F);
/* 1170 */       if ($$6 > 0) {
/* 1171 */         if (isSprinting()) {
/* 1172 */           awardStat(Stats.SPRINT_ONE_CM, $$6);
/* 1173 */           causeFoodExhaustion(0.1F * $$6 * 0.01F);
/* 1174 */         } else if (isCrouching()) {
/* 1175 */           awardStat(Stats.CROUCH_ONE_CM, $$6);
/* 1176 */           causeFoodExhaustion(0.0F * $$6 * 0.01F);
/*      */         } else {
/* 1178 */           awardStat(Stats.WALK_ONE_CM, $$6);
/* 1179 */           causeFoodExhaustion(0.0F * $$6 * 0.01F);
/*      */         } 
/*      */       }
/* 1182 */     } else if (isFallFlying()) {
/* 1183 */       int $$7 = Math.round((float)Math.sqrt($$0 * $$0 + $$1 * $$1 + $$2 * $$2) * 100.0F);
/* 1184 */       awardStat(Stats.AVIATE_ONE_CM, $$7);
/*      */     } else {
/* 1186 */       int $$8 = Math.round((float)Math.sqrt($$0 * $$0 + $$2 * $$2) * 100.0F);
/* 1187 */       if ($$8 > 25) {
/* 1188 */         awardStat(Stats.FLY_ONE_CM, $$8);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkRidingStatistics(double $$0, double $$1, double $$2) {
/* 1194 */     if (!isPassenger() || didNotMove($$0, $$1, $$2)) {
/*      */       return;
/*      */     }
/*      */     
/* 1198 */     int $$3 = Math.round((float)Math.sqrt($$0 * $$0 + $$1 * $$1 + $$2 * $$2) * 100.0F);
/* 1199 */     Entity $$4 = getVehicle();
/* 1200 */     if ($$4 instanceof net.minecraft.world.entity.vehicle.AbstractMinecart) {
/* 1201 */       awardStat(Stats.MINECART_ONE_CM, $$3);
/* 1202 */     } else if ($$4 instanceof net.minecraft.world.entity.vehicle.Boat) {
/* 1203 */       awardStat(Stats.BOAT_ONE_CM, $$3);
/* 1204 */     } else if ($$4 instanceof net.minecraft.world.entity.animal.Pig) {
/* 1205 */       awardStat(Stats.PIG_ONE_CM, $$3);
/* 1206 */     } else if ($$4 instanceof AbstractHorse) {
/* 1207 */       awardStat(Stats.HORSE_ONE_CM, $$3);
/* 1208 */     } else if ($$4 instanceof net.minecraft.world.entity.monster.Strider) {
/* 1209 */       awardStat(Stats.STRIDER_ONE_CM, $$3);
/*      */     } 
/*      */   }
/*      */   
/*      */   private static boolean didNotMove(double $$0, double $$1, double $$2) {
/* 1214 */     return ($$0 == 0.0D && $$1 == 0.0D && $$2 == 0.0D);
/*      */   }
/*      */ 
/*      */   
/*      */   public void awardStat(Stat<?> $$0, int $$1) {
/* 1219 */     this.stats.increment(this, $$0, $$1);
/* 1220 */     getScoreboard().forAllObjectives((ObjectiveCriteria)$$0, (ScoreHolder)this, $$1 -> $$1.add($$0));
/*      */   }
/*      */ 
/*      */   
/*      */   public void resetStat(Stat<?> $$0) {
/* 1225 */     this.stats.setValue(this, $$0, 0);
/* 1226 */     getScoreboard().forAllObjectives((ObjectiveCriteria)$$0, (ScoreHolder)this, ScoreAccess::reset);
/*      */   }
/*      */ 
/*      */   
/*      */   public int awardRecipes(Collection<RecipeHolder<?>> $$0) {
/* 1231 */     return this.recipeBook.addRecipes($$0, this);
/*      */   }
/*      */ 
/*      */   
/*      */   public void triggerRecipeCrafted(RecipeHolder<?> $$0, List<ItemStack> $$1) {
/* 1236 */     CriteriaTriggers.RECIPE_CRAFTED.trigger(this, $$0.id(), $$1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void awardRecipesByKey(List<ResourceLocation> $$0) {
/* 1243 */     List<RecipeHolder<?>> $$1 = (List<RecipeHolder<?>>)$$0.stream().flatMap($$0 -> this.server.getRecipeManager().byKey($$0).stream()).collect(Collectors.toList());
/* 1244 */     awardRecipes($$1);
/*      */   }
/*      */ 
/*      */   
/*      */   public int resetRecipes(Collection<RecipeHolder<?>> $$0) {
/* 1249 */     return this.recipeBook.removeRecipes($$0, this);
/*      */   }
/*      */ 
/*      */   
/*      */   public void giveExperiencePoints(int $$0) {
/* 1254 */     super.giveExperiencePoints($$0);
/* 1255 */     this.lastSentExp = -1;
/*      */   }
/*      */   
/*      */   public void disconnect() {
/* 1259 */     this.disconnected = true;
/* 1260 */     ejectPassengers();
/* 1261 */     if (isSleeping()) {
/* 1262 */       stopSleepInBed(true, false);
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean hasDisconnected() {
/* 1267 */     return this.disconnected;
/*      */   }
/*      */   
/*      */   public void resetSentInfo() {
/* 1271 */     this.lastSentHealth = -1.0E8F;
/*      */   }
/*      */ 
/*      */   
/*      */   public void displayClientMessage(Component $$0, boolean $$1) {
/* 1276 */     sendSystemMessage($$0, $$1);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void completeUsingItem() {
/* 1281 */     if (!this.useItem.isEmpty() && isUsingItem()) {
/* 1282 */       this.connection.send((Packet)new ClientboundEntityEventPacket((Entity)this, (byte)9));
/* 1283 */       super.completeUsingItem();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void lookAt(EntityAnchorArgument.Anchor $$0, Vec3 $$1) {
/* 1289 */     super.lookAt($$0, $$1);
/* 1290 */     this.connection.send((Packet)new ClientboundPlayerLookAtPacket($$0, $$1.x, $$1.y, $$1.z));
/*      */   }
/*      */   
/*      */   public void lookAt(EntityAnchorArgument.Anchor $$0, Entity $$1, EntityAnchorArgument.Anchor $$2) {
/* 1294 */     Vec3 $$3 = $$2.apply($$1);
/* 1295 */     super.lookAt($$0, $$3);
/* 1296 */     this.connection.send((Packet)new ClientboundPlayerLookAtPacket($$0, $$1, $$2));
/*      */   }
/*      */   
/*      */   public void restoreFrom(ServerPlayer $$0, boolean $$1) {
/* 1300 */     this.wardenSpawnTracker = $$0.wardenSpawnTracker;
/* 1301 */     this.chatSession = $$0.chatSession;
/*      */ 
/*      */     
/* 1304 */     this.gameMode.setGameModeForPlayer($$0.gameMode
/* 1305 */         .getGameModeForPlayer(), $$0.gameMode
/* 1306 */         .getPreviousGameModeForPlayer());
/*      */     
/* 1308 */     onUpdateAbilities();
/*      */     
/* 1310 */     if ($$1) {
/* 1311 */       getInventory().replaceWith($$0.getInventory());
/*      */       
/* 1313 */       setHealth($$0.getHealth());
/* 1314 */       this.foodData = $$0.foodData;
/*      */       
/* 1316 */       this.experienceLevel = $$0.experienceLevel;
/* 1317 */       this.totalExperience = $$0.totalExperience;
/* 1318 */       this.experienceProgress = $$0.experienceProgress;
/*      */       
/* 1320 */       setScore($$0.getScore());
/* 1321 */       this.portalEntrancePos = $$0.portalEntrancePos;
/* 1322 */     } else if (level().getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY) || $$0.isSpectator()) {
/* 1323 */       getInventory().replaceWith($$0.getInventory());
/*      */       
/* 1325 */       this.experienceLevel = $$0.experienceLevel;
/* 1326 */       this.totalExperience = $$0.totalExperience;
/* 1327 */       this.experienceProgress = $$0.experienceProgress;
/* 1328 */       setScore($$0.getScore());
/*      */     } 
/*      */     
/* 1331 */     this.enchantmentSeed = $$0.enchantmentSeed;
/* 1332 */     this.enderChestInventory = $$0.enderChestInventory;
/* 1333 */     getEntityData().set(DATA_PLAYER_MODE_CUSTOMISATION, $$0.getEntityData().get(DATA_PLAYER_MODE_CUSTOMISATION));
/* 1334 */     this.lastSentExp = -1;
/* 1335 */     this.lastSentHealth = -1.0F;
/* 1336 */     this.lastSentFood = -1;
/* 1337 */     this.recipeBook.copyOverData((RecipeBook)$$0.recipeBook);
/* 1338 */     this.seenCredits = $$0.seenCredits;
/* 1339 */     this.enteredNetherPosition = $$0.enteredNetherPosition;
/* 1340 */     this.chunkTrackingView = $$0.chunkTrackingView;
/*      */     
/* 1342 */     setShoulderEntityLeft($$0.getShoulderEntityLeft());
/* 1343 */     setShoulderEntityRight($$0.getShoulderEntityRight());
/* 1344 */     setLastDeathLocation($$0.getLastDeathLocation());
/*      */   }
/*      */ 
/*      */   
/*      */   protected void onEffectAdded(MobEffectInstance $$0, @Nullable Entity $$1) {
/* 1349 */     super.onEffectAdded($$0, $$1);
/* 1350 */     this.connection.send((Packet)new ClientboundUpdateMobEffectPacket(getId(), $$0));
/*      */     
/* 1352 */     if ($$0.getEffect() == MobEffects.LEVITATION) {
/* 1353 */       this.levitationStartTime = this.tickCount;
/* 1354 */       this.levitationStartPos = position();
/*      */     } 
/*      */     
/* 1357 */     CriteriaTriggers.EFFECTS_CHANGED.trigger(this, $$1);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void onEffectUpdated(MobEffectInstance $$0, boolean $$1, @Nullable Entity $$2) {
/* 1362 */     super.onEffectUpdated($$0, $$1, $$2);
/* 1363 */     this.connection.send((Packet)new ClientboundUpdateMobEffectPacket(getId(), $$0));
/*      */     
/* 1365 */     CriteriaTriggers.EFFECTS_CHANGED.trigger(this, $$2);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void onEffectRemoved(MobEffectInstance $$0) {
/* 1370 */     super.onEffectRemoved($$0);
/* 1371 */     this.connection.send((Packet)new ClientboundRemoveMobEffectPacket(getId(), $$0.getEffect()));
/*      */     
/* 1373 */     if ($$0.getEffect() == MobEffects.LEVITATION) {
/* 1374 */       this.levitationStartPos = null;
/*      */     }
/*      */     
/* 1377 */     CriteriaTriggers.EFFECTS_CHANGED.trigger(this, null);
/*      */   }
/*      */ 
/*      */   
/*      */   public void teleportTo(double $$0, double $$1, double $$2) {
/* 1382 */     this.connection.teleport($$0, $$1, $$2, getYRot(), getXRot(), RelativeMovement.ROTATION);
/*      */   }
/*      */ 
/*      */   
/*      */   public void teleportRelative(double $$0, double $$1, double $$2) {
/* 1387 */     this.connection.teleport(getX() + $$0, getY() + $$1, getZ() + $$2, getYRot(), getXRot(), RelativeMovement.ALL);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean teleportTo(ServerLevel $$0, double $$1, double $$2, double $$3, Set<RelativeMovement> $$4, float $$5, float $$6) {
/* 1392 */     ChunkPos $$7 = new ChunkPos(BlockPos.containing($$1, $$2, $$3));
/* 1393 */     $$0.getChunkSource().addRegionTicket(TicketType.POST_TELEPORT, $$7, 1, Integer.valueOf(getId()));
/* 1394 */     stopRiding();
/*      */     
/* 1396 */     if (isSleeping()) {
/* 1397 */       stopSleepInBed(true, true);
/*      */     }
/*      */     
/* 1400 */     if ($$0 == level()) {
/* 1401 */       this.connection.teleport($$1, $$2, $$3, $$5, $$6, $$4);
/*      */     } else {
/* 1403 */       teleportTo($$0, $$1, $$2, $$3, $$5, $$6);
/*      */     } 
/* 1405 */     setYHeadRot($$5);
/* 1406 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public void moveTo(double $$0, double $$1, double $$2) {
/* 1411 */     super.moveTo($$0, $$1, $$2);
/* 1412 */     this.connection.resetPosition();
/*      */   }
/*      */ 
/*      */   
/*      */   public void crit(Entity $$0) {
/* 1417 */     serverLevel().getChunkSource().broadcastAndSend((Entity)this, (Packet<?>)new ClientboundAnimatePacket($$0, 4));
/*      */   }
/*      */ 
/*      */   
/*      */   public void magicCrit(Entity $$0) {
/* 1422 */     serverLevel().getChunkSource().broadcastAndSend((Entity)this, (Packet<?>)new ClientboundAnimatePacket($$0, 5));
/*      */   }
/*      */ 
/*      */   
/*      */   public void onUpdateAbilities() {
/* 1427 */     if (this.connection == null) {
/*      */       return;
/*      */     }
/* 1430 */     this.connection.send((Packet)new ClientboundPlayerAbilitiesPacket(getAbilities()));
/* 1431 */     updateInvisibilityStatus();
/*      */   }
/*      */   
/*      */   public ServerLevel serverLevel() {
/* 1435 */     return (ServerLevel)level();
/*      */   }
/*      */   
/*      */   public boolean setGameMode(GameType $$0) {
/* 1439 */     if (!this.gameMode.changeGameModeForPlayer($$0)) {
/* 1440 */       return false;
/*      */     }
/* 1442 */     this.connection.send((Packet)new ClientboundGameEventPacket(ClientboundGameEventPacket.CHANGE_GAME_MODE, $$0.getId()));
/*      */     
/* 1444 */     if ($$0 == GameType.SPECTATOR) {
/* 1445 */       removeEntitiesOnShoulder();
/* 1446 */       stopRiding();
/*      */     } else {
/* 1448 */       setCamera((Entity)this);
/*      */     } 
/*      */     
/* 1451 */     onUpdateAbilities();
/* 1452 */     updateEffectVisibility();
/* 1453 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isSpectator() {
/* 1458 */     return (this.gameMode.getGameModeForPlayer() == GameType.SPECTATOR);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isCreative() {
/* 1463 */     return (this.gameMode.getGameModeForPlayer() == GameType.CREATIVE);
/*      */   }
/*      */ 
/*      */   
/*      */   public void sendSystemMessage(Component $$0) {
/* 1468 */     sendSystemMessage($$0, false);
/*      */   }
/*      */   
/*      */   public void sendSystemMessage(Component $$0, boolean $$1) {
/* 1472 */     if (!acceptsSystemMessages($$1)) {
/*      */       return;
/*      */     }
/*      */     
/* 1476 */     this.connection.send((Packet)new ClientboundSystemChatPacket($$0, $$1), PacketSendListener.exceptionallySend(() -> {
/*      */             if (acceptsSystemMessages(false)) {
/*      */               int $$1 = 256;
/*      */               String $$2 = $$0.getString(256);
/*      */               MutableComponent mutableComponent = Component.literal($$2).withStyle(ChatFormatting.YELLOW);
/*      */               return (Packet)new ClientboundSystemChatPacket((Component)Component.translatable("multiplayer.message_not_delivered", new Object[] { mutableComponent }).withStyle(ChatFormatting.RED), false);
/*      */             } 
/*      */             return null;
/*      */           }));
/*      */   }
/*      */   
/*      */   public void sendChatMessage(OutgoingChatMessage $$0, boolean $$1, ChatType.Bound $$2) {
/* 1488 */     if (acceptsChatMessages()) {
/* 1489 */       $$0.sendToPlayer(this, $$1, $$2);
/*      */     }
/*      */   }
/*      */   
/*      */   public String getIpAddress() {
/* 1494 */     SocketAddress $$0 = this.connection.getRemoteAddress();
/* 1495 */     if ($$0 instanceof InetSocketAddress) { InetSocketAddress $$1 = (InetSocketAddress)$$0;
/* 1496 */       return InetAddresses.toAddrString($$1.getAddress()); }
/*      */     
/* 1498 */     return "<unknown>";
/*      */   }
/*      */   
/*      */   public void updateOptions(ClientInformation $$0) {
/* 1502 */     this.language = $$0.language();
/* 1503 */     this.requestedViewDistance = $$0.viewDistance();
/* 1504 */     this.chatVisibility = $$0.chatVisibility();
/* 1505 */     this.canChatColor = $$0.chatColors();
/* 1506 */     this.textFilteringEnabled = $$0.textFilteringEnabled();
/* 1507 */     this.allowsListing = $$0.allowsListing();
/*      */     
/* 1509 */     getEntityData().set(DATA_PLAYER_MODE_CUSTOMISATION, Byte.valueOf((byte)$$0.modelCustomisation()));
/* 1510 */     getEntityData().set(DATA_PLAYER_MAIN_HAND, Byte.valueOf((byte)$$0.mainHand().getId()));
/*      */   }
/*      */   
/*      */   public ClientInformation clientInformation() {
/* 1514 */     int $$0 = ((Byte)getEntityData().get(DATA_PLAYER_MODE_CUSTOMISATION)).byteValue();
/* 1515 */     HumanoidArm $$1 = HumanoidArm.BY_ID.apply(((Byte)getEntityData().get(DATA_PLAYER_MAIN_HAND)).byteValue());
/*      */     
/* 1517 */     return new ClientInformation(this.language, this.requestedViewDistance, this.chatVisibility, this.canChatColor, $$0, $$1, this.textFilteringEnabled, this.allowsListing);
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
/*      */   public boolean canChatInColor() {
/* 1530 */     return this.canChatColor;
/*      */   }
/*      */   
/*      */   public ChatVisiblity getChatVisibility() {
/* 1534 */     return this.chatVisibility;
/*      */   }
/*      */   
/*      */   private boolean acceptsSystemMessages(boolean $$0) {
/* 1538 */     if (this.chatVisibility == ChatVisiblity.HIDDEN) {
/* 1539 */       return $$0;
/*      */     }
/* 1541 */     return true;
/*      */   }
/*      */   
/*      */   private boolean acceptsChatMessages() {
/* 1545 */     return (this.chatVisibility == ChatVisiblity.FULL);
/*      */   }
/*      */   
/*      */   public int requestedViewDistance() {
/* 1549 */     return this.requestedViewDistance;
/*      */   }
/*      */   
/*      */   public void sendServerStatus(ServerStatus $$0) {
/* 1553 */     this.connection.send((Packet)new ClientboundServerDataPacket($$0.description(), $$0.favicon().map(ServerStatus.Favicon::iconBytes), $$0.enforcesSecureChat()));
/*      */   }
/*      */ 
/*      */   
/*      */   protected int getPermissionLevel() {
/* 1558 */     return this.server.getProfilePermissions(getGameProfile());
/*      */   }
/*      */   
/*      */   public void resetLastActionTime() {
/* 1562 */     this.lastActionTime = Util.getMillis();
/*      */   }
/*      */   
/*      */   public ServerStatsCounter getStats() {
/* 1566 */     return this.stats;
/*      */   }
/*      */   
/*      */   public ServerRecipeBook getRecipeBook() {
/* 1570 */     return this.recipeBook;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void updateInvisibilityStatus() {
/* 1575 */     if (isSpectator()) {
/* 1576 */       removeEffectParticles();
/* 1577 */       setInvisible(true);
/*      */     } else {
/* 1579 */       super.updateInvisibilityStatus();
/*      */     } 
/*      */   }
/*      */   
/*      */   public Entity getCamera() {
/* 1584 */     return (this.camera == null) ? (Entity)this : this.camera;
/*      */   }
/*      */   
/*      */   public void setCamera(@Nullable Entity $$0) {
/* 1588 */     Entity $$1 = getCamera();
/* 1589 */     this.camera = ($$0 == null) ? (Entity)this : $$0;
/*      */     
/* 1591 */     if ($$1 != this.camera) {
/* 1592 */       Level level = this.camera.level(); if (level instanceof ServerLevel) { ServerLevel $$2 = (ServerLevel)level;
/* 1593 */         teleportTo($$2, this.camera.getX(), this.camera.getY(), this.camera.getZ(), Set.of(), getYRot(), getXRot()); }
/*      */       
/* 1595 */       if ($$0 != null)
/*      */       {
/* 1597 */         serverLevel().getChunkSource().move(this);
/*      */       }
/* 1599 */       this.connection.send((Packet)new ClientboundSetCameraPacket(this.camera));
/* 1600 */       this.connection.resetPosition();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void processPortalCooldown() {
/* 1606 */     if (!this.isChangingDimension) {
/* 1607 */       super.processPortalCooldown();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void attack(Entity $$0) {
/* 1613 */     if (this.gameMode.getGameModeForPlayer() == GameType.SPECTATOR) {
/* 1614 */       setCamera($$0);
/*      */     } else {
/* 1616 */       super.attack($$0);
/*      */     } 
/*      */   }
/*      */   
/*      */   public long getLastActionTime() {
/* 1621 */     return this.lastActionTime;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public Component getTabListDisplayName() {
/* 1626 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public void swing(InteractionHand $$0) {
/* 1631 */     super.swing($$0);
/* 1632 */     resetAttackStrengthTicker();
/*      */   }
/*      */   
/*      */   public boolean isChangingDimension() {
/* 1636 */     return this.isChangingDimension;
/*      */   }
/*      */   
/*      */   public void hasChangedDimension() {
/* 1640 */     this.isChangingDimension = false;
/*      */   }
/*      */   
/*      */   public PlayerAdvancements getAdvancements() {
/* 1644 */     return this.advancements;
/*      */   }
/*      */   
/*      */   public void teleportTo(ServerLevel $$0, double $$1, double $$2, double $$3, float $$4, float $$5) {
/* 1648 */     setCamera((Entity)this);
/* 1649 */     stopRiding();
/* 1650 */     if ($$0 == level()) {
/* 1651 */       this.connection.teleport($$1, $$2, $$3, $$4, $$5);
/*      */     } else {
/* 1653 */       ServerLevel $$6 = serverLevel();
/*      */       
/* 1655 */       LevelData $$7 = $$0.getLevelData();
/*      */       
/* 1657 */       this.connection.send((Packet)new ClientboundRespawnPacket(
/* 1658 */             createCommonSpawnInfo($$0), (byte)3));
/*      */ 
/*      */       
/* 1661 */       this.connection.send((Packet)new ClientboundChangeDifficultyPacket($$7.getDifficulty(), $$7.isDifficultyLocked()));
/* 1662 */       this.server.getPlayerList().sendPlayerPermissionLevel(this);
/*      */       
/* 1664 */       $$6.removePlayerImmediately(this, Entity.RemovalReason.CHANGED_DIMENSION);
/*      */       
/* 1666 */       unsetRemoved();
/*      */       
/* 1668 */       moveTo($$1, $$2, $$3, $$4, $$5);
/*      */       
/* 1670 */       setServerLevel($$0);
/* 1671 */       $$0.addDuringCommandTeleport(this);
/*      */       
/* 1673 */       triggerDimensionChangeTriggers($$6);
/*      */       
/* 1675 */       this.connection.teleport($$1, $$2, $$3, $$4, $$5);
/* 1676 */       this.server.getPlayerList().sendLevelInfo(this, $$0);
/* 1677 */       this.server.getPlayerList().sendAllPlayerInfo(this);
/*      */     } 
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public BlockPos getRespawnPosition() {
/* 1683 */     return this.respawnPosition;
/*      */   }
/*      */   
/*      */   public float getRespawnAngle() {
/* 1687 */     return this.respawnAngle;
/*      */   }
/*      */   
/*      */   public ResourceKey<Level> getRespawnDimension() {
/* 1691 */     return this.respawnDimension;
/*      */   }
/*      */   
/*      */   public boolean isRespawnForced() {
/* 1695 */     return this.respawnForced;
/*      */   }
/*      */   
/*      */   public void setRespawnPosition(ResourceKey<Level> $$0, @Nullable BlockPos $$1, float $$2, boolean $$3, boolean $$4) {
/* 1699 */     if ($$1 != null) {
/* 1700 */       boolean $$5 = ($$1.equals(this.respawnPosition) && $$0.equals(this.respawnDimension));
/* 1701 */       if ($$4 && !$$5) {
/* 1702 */         sendSystemMessage((Component)Component.translatable("block.minecraft.set_spawn"));
/*      */       }
/* 1704 */       this.respawnPosition = $$1;
/* 1705 */       this.respawnDimension = $$0;
/* 1706 */       this.respawnAngle = $$2;
/* 1707 */       this.respawnForced = $$3;
/*      */     } else {
/* 1709 */       this.respawnPosition = null;
/* 1710 */       this.respawnDimension = Level.OVERWORLD;
/* 1711 */       this.respawnAngle = 0.0F;
/* 1712 */       this.respawnForced = false;
/*      */     } 
/*      */   }
/*      */   
/*      */   public SectionPos getLastSectionPos() {
/* 1717 */     return this.lastSectionPos;
/*      */   }
/*      */   
/*      */   public void setLastSectionPos(SectionPos $$0) {
/* 1721 */     this.lastSectionPos = $$0;
/*      */   }
/*      */   
/*      */   public ChunkTrackingView getChunkTrackingView() {
/* 1725 */     return this.chunkTrackingView;
/*      */   }
/*      */   
/*      */   public void setChunkTrackingView(ChunkTrackingView $$0) {
/* 1729 */     this.chunkTrackingView = $$0;
/*      */   }
/*      */ 
/*      */   
/*      */   public void playNotifySound(SoundEvent $$0, SoundSource $$1, float $$2, float $$3) {
/* 1734 */     this.connection.send((Packet)new ClientboundSoundPacket(BuiltInRegistries.SOUND_EVENT.wrapAsHolder($$0), $$1, getX(), getY(), getZ(), $$2, $$3, this.random.nextLong()));
/*      */   }
/*      */ 
/*      */   
/*      */   public ItemEntity drop(ItemStack $$0, boolean $$1, boolean $$2) {
/* 1739 */     ItemEntity $$3 = super.drop($$0, $$1, $$2);
/* 1740 */     if ($$3 == null) {
/* 1741 */       return null;
/*      */     }
/*      */     
/* 1744 */     level().addFreshEntity((Entity)$$3);
/* 1745 */     ItemStack $$4 = $$3.getItem();
/* 1746 */     if ($$2) {
/* 1747 */       if (!$$4.isEmpty()) {
/* 1748 */         awardStat(Stats.ITEM_DROPPED.get($$4.getItem()), $$0.getCount());
/*      */       }
/* 1750 */       awardStat(Stats.DROP);
/*      */     } 
/*      */     
/* 1753 */     return $$3;
/*      */   }
/*      */   
/*      */   public TextFilter getTextFilter() {
/* 1757 */     return this.textFilter;
/*      */   }
/*      */   
/*      */   public void setServerLevel(ServerLevel $$0) {
/* 1761 */     setLevel($$0);
/* 1762 */     this.gameMode.setLevel($$0);
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   private static GameType readPlayerMode(@Nullable CompoundTag $$0, String $$1) {
/* 1767 */     return ($$0 != null && $$0.contains($$1, 99)) ? GameType.byId($$0.getInt($$1)) : null;
/*      */   }
/*      */ 
/*      */   
/*      */   private GameType calculateGameModeForNewPlayer(@Nullable GameType $$0) {
/* 1772 */     GameType $$1 = this.server.getForcedGameType();
/* 1773 */     if ($$1 != null) {
/* 1774 */       return $$1;
/*      */     }
/*      */     
/* 1777 */     return ($$0 != null) ? $$0 : this.server.getDefaultGameType();
/*      */   }
/*      */   
/*      */   public void loadGameTypes(@Nullable CompoundTag $$0) {
/* 1781 */     this.gameMode.setGameModeForPlayer(
/* 1782 */         calculateGameModeForNewPlayer(readPlayerMode($$0, "playerGameType")), 
/* 1783 */         readPlayerMode($$0, "previousPlayerGameType"));
/*      */   }
/*      */ 
/*      */   
/*      */   private void storeGameTypes(CompoundTag $$0) {
/* 1788 */     $$0.putInt("playerGameType", this.gameMode.getGameModeForPlayer().getId());
/* 1789 */     GameType $$1 = this.gameMode.getPreviousGameModeForPlayer();
/* 1790 */     if ($$1 != null) {
/* 1791 */       $$0.putInt("previousPlayerGameType", $$1.getId());
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isTextFilteringEnabled() {
/* 1797 */     return this.textFilteringEnabled;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean shouldFilterMessageTo(ServerPlayer $$0) {
/* 1802 */     if ($$0 == this) {
/* 1803 */       return false;
/*      */     }
/* 1805 */     return (this.textFilteringEnabled || $$0.textFilteringEnabled);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean mayInteract(Level $$0, BlockPos $$1) {
/* 1810 */     return (super.mayInteract($$0, $$1) && $$0.mayInteract(this, $$1));
/*      */   }
/*      */ 
/*      */   
/*      */   protected void updateUsingItem(ItemStack $$0) {
/* 1815 */     CriteriaTriggers.USING_ITEM.trigger(this, $$0);
/* 1816 */     super.updateUsingItem($$0);
/*      */   }
/*      */   
/*      */   public boolean drop(boolean $$0) {
/* 1820 */     Inventory $$1 = getInventory();
/* 1821 */     ItemStack $$2 = $$1.removeFromSelected($$0);
/* 1822 */     this.containerMenu.findSlot((Container)$$1, $$1.selected).ifPresent($$1 -> this.containerMenu.setRemoteSlot($$1, $$0.getSelected()));
/*      */ 
/*      */     
/* 1825 */     return (drop($$2, false, true) != null);
/*      */   }
/*      */   
/*      */   public boolean allowsListing() {
/* 1829 */     return this.allowsListing;
/*      */   }
/*      */ 
/*      */   
/*      */   public Optional<WardenSpawnTracker> getWardenSpawnTracker() {
/* 1834 */     return Optional.of(this.wardenSpawnTracker);
/*      */   }
/*      */ 
/*      */   
/*      */   public void onItemPickup(ItemEntity $$0) {
/* 1839 */     super.onItemPickup($$0);
/* 1840 */     Entity $$1 = $$0.getOwner();
/* 1841 */     if ($$1 != null) {
/* 1842 */       CriteriaTriggers.THROWN_ITEM_PICKED_UP_BY_PLAYER.trigger(this, $$0.getItem(), $$1);
/*      */     }
/*      */   }
/*      */   
/*      */   public void setChatSession(RemoteChatSession $$0) {
/* 1847 */     this.chatSession = $$0;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public RemoteChatSession getChatSession() {
/* 1852 */     if (this.chatSession != null && this.chatSession.hasExpired()) {
/* 1853 */       return null;
/*      */     }
/* 1855 */     return this.chatSession;
/*      */   }
/*      */ 
/*      */   
/*      */   public void indicateDamage(double $$0, double $$1) {
/* 1860 */     this.hurtDir = (float)(Mth.atan2($$1, $$0) * 57.2957763671875D - getYRot());
/* 1861 */     this.connection.send((Packet)new ClientboundHurtAnimationPacket((LivingEntity)this));
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean startRiding(Entity $$0, boolean $$1) {
/* 1866 */     if (super.startRiding($$0, $$1)) {
/*      */       
/* 1868 */       $$0.positionRider((Entity)this);
/* 1869 */       this.connection.teleport(getX(), getY(), getZ(), getYRot(), getXRot());
/* 1870 */       if ($$0 instanceof LivingEntity) { LivingEntity $$2 = (LivingEntity)$$0;
/* 1871 */         for (MobEffectInstance $$3 : $$2.getActiveEffects()) {
/* 1872 */           this.connection.send((Packet)new ClientboundUpdateMobEffectPacket($$0.getId(), $$3));
/*      */         } }
/*      */       
/* 1875 */       return true;
/*      */     } 
/* 1877 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public void stopRiding() {
/* 1882 */     Entity $$0 = getVehicle();
/* 1883 */     super.stopRiding();
/* 1884 */     if ($$0 instanceof LivingEntity) { LivingEntity $$1 = (LivingEntity)$$0;
/* 1885 */       for (MobEffectInstance $$2 : $$1.getActiveEffects()) {
/* 1886 */         this.connection.send((Packet)new ClientboundRemoveMobEffectPacket($$0.getId(), $$2.getEffect()));
/*      */       } }
/*      */   
/*      */   }
/*      */   
/*      */   public CommonPlayerSpawnInfo createCommonSpawnInfo(ServerLevel $$0) {
/* 1892 */     return new CommonPlayerSpawnInfo($$0
/* 1893 */         .dimensionTypeId(), $$0
/* 1894 */         .dimension(), 
/* 1895 */         BiomeManager.obfuscateSeed($$0.getSeed()), this.gameMode
/* 1896 */         .getGameModeForPlayer(), this.gameMode
/* 1897 */         .getPreviousGameModeForPlayer(), $$0
/* 1898 */         .isDebug(), $$0
/* 1899 */         .isFlat(), 
/* 1900 */         getLastDeathLocation(), 
/* 1901 */         getPortalCooldown());
/*      */   }
/*      */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\level\ServerPlayer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */