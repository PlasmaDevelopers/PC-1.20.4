/*      */ package net.minecraft.server.network;
/*      */ import com.google.common.collect.Lists;
/*      */ import com.google.common.primitives.Floats;
/*      */ import com.mojang.authlib.GameProfile;
/*      */ import com.mojang.brigadier.CommandDispatcher;
/*      */ import com.mojang.brigadier.ParseResults;
/*      */ import com.mojang.brigadier.StringReader;
/*      */ import com.mojang.brigadier.suggestion.Suggestions;
/*      */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*      */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*      */ import java.net.SocketAddress;
/*      */ import java.util.Collections;
/*      */ import java.util.EnumSet;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Objects;
/*      */ import java.util.Optional;
/*      */ import java.util.Set;
/*      */ import java.util.concurrent.CancellationException;
/*      */ import java.util.concurrent.CompletableFuture;
/*      */ import java.util.concurrent.Executor;
/*      */ import java.util.function.BiFunction;
/*      */ import java.util.function.Consumer;
/*      */ import java.util.function.UnaryOperator;
/*      */ import java.util.stream.Collectors;
/*      */ import java.util.stream.Stream;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.ChatFormatting;
/*      */ import net.minecraft.SharedConstants;
/*      */ import net.minecraft.Util;
/*      */ import net.minecraft.advancements.AdvancementHolder;
/*      */ import net.minecraft.advancements.CriteriaTriggers;
/*      */ import net.minecraft.commands.CommandSigningContext;
/*      */ import net.minecraft.commands.CommandSourceStack;
/*      */ import net.minecraft.commands.Commands;
/*      */ import net.minecraft.core.BlockPos;
/*      */ import net.minecraft.core.Direction;
/*      */ import net.minecraft.core.registries.Registries;
/*      */ import net.minecraft.nbt.CompoundTag;
/*      */ import net.minecraft.nbt.ListTag;
/*      */ import net.minecraft.nbt.StringTag;
/*      */ import net.minecraft.nbt.Tag;
/*      */ import net.minecraft.network.Connection;
/*      */ import net.minecraft.network.PacketListener;
/*      */ import net.minecraft.network.TickablePacketListener;
/*      */ import net.minecraft.network.chat.ChatType;
/*      */ import net.minecraft.network.chat.Component;
/*      */ import net.minecraft.network.chat.LastSeenMessages;
/*      */ import net.minecraft.network.chat.LastSeenMessagesValidator;
/*      */ import net.minecraft.network.chat.MessageSignature;
/*      */ import net.minecraft.network.chat.MessageSignatureCache;
/*      */ import net.minecraft.network.chat.MutableComponent;
/*      */ import net.minecraft.network.chat.PlayerChatMessage;
/*      */ import net.minecraft.network.chat.RemoteChatSession;
/*      */ import net.minecraft.network.chat.SignableCommand;
/*      */ import net.minecraft.network.chat.SignedMessageBody;
/*      */ import net.minecraft.network.chat.SignedMessageChain;
/*      */ import net.minecraft.network.protocol.Packet;
/*      */ import net.minecraft.network.protocol.PacketUtils;
/*      */ import net.minecraft.network.protocol.common.ServerboundClientInformationPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundCommandSuggestionsPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundDisguisedChatPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundMoveVehiclePacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundPlayerPositionPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundSetCarriedItemPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundSystemChatPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundTagQueryPacket;
/*      */ import net.minecraft.network.protocol.game.ServerGamePacketListener;
/*      */ import net.minecraft.network.protocol.game.ServerboundAcceptTeleportationPacket;
/*      */ import net.minecraft.network.protocol.game.ServerboundBlockEntityTagQuery;
/*      */ import net.minecraft.network.protocol.game.ServerboundChangeDifficultyPacket;
/*      */ import net.minecraft.network.protocol.game.ServerboundChatAckPacket;
/*      */ import net.minecraft.network.protocol.game.ServerboundChatCommandPacket;
/*      */ import net.minecraft.network.protocol.game.ServerboundChatPacket;
/*      */ import net.minecraft.network.protocol.game.ServerboundChatSessionUpdatePacket;
/*      */ import net.minecraft.network.protocol.game.ServerboundChunkBatchReceivedPacket;
/*      */ import net.minecraft.network.protocol.game.ServerboundClientCommandPacket;
/*      */ import net.minecraft.network.protocol.game.ServerboundCommandSuggestionPacket;
/*      */ import net.minecraft.network.protocol.game.ServerboundConfigurationAcknowledgedPacket;
/*      */ import net.minecraft.network.protocol.game.ServerboundContainerButtonClickPacket;
/*      */ import net.minecraft.network.protocol.game.ServerboundContainerClickPacket;
/*      */ import net.minecraft.network.protocol.game.ServerboundContainerClosePacket;
/*      */ import net.minecraft.network.protocol.game.ServerboundContainerSlotStateChangedPacket;
/*      */ import net.minecraft.network.protocol.game.ServerboundEditBookPacket;
/*      */ import net.minecraft.network.protocol.game.ServerboundEntityTagQuery;
/*      */ import net.minecraft.network.protocol.game.ServerboundInteractPacket;
/*      */ import net.minecraft.network.protocol.game.ServerboundJigsawGeneratePacket;
/*      */ import net.minecraft.network.protocol.game.ServerboundLockDifficultyPacket;
/*      */ import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
/*      */ import net.minecraft.network.protocol.game.ServerboundMoveVehiclePacket;
/*      */ import net.minecraft.network.protocol.game.ServerboundPaddleBoatPacket;
/*      */ import net.minecraft.network.protocol.game.ServerboundPickItemPacket;
/*      */ import net.minecraft.network.protocol.game.ServerboundPlaceRecipePacket;
/*      */ import net.minecraft.network.protocol.game.ServerboundPlayerAbilitiesPacket;
/*      */ import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
/*      */ import net.minecraft.network.protocol.game.ServerboundPlayerCommandPacket;
/*      */ import net.minecraft.network.protocol.game.ServerboundPlayerInputPacket;
/*      */ import net.minecraft.network.protocol.game.ServerboundRecipeBookChangeSettingsPacket;
/*      */ import net.minecraft.network.protocol.game.ServerboundRecipeBookSeenRecipePacket;
/*      */ import net.minecraft.network.protocol.game.ServerboundRenameItemPacket;
/*      */ import net.minecraft.network.protocol.game.ServerboundSeenAdvancementsPacket;
/*      */ import net.minecraft.network.protocol.game.ServerboundSelectTradePacket;
/*      */ import net.minecraft.network.protocol.game.ServerboundSetBeaconPacket;
/*      */ import net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket;
/*      */ import net.minecraft.network.protocol.game.ServerboundSetCommandBlockPacket;
/*      */ import net.minecraft.network.protocol.game.ServerboundSetCommandMinecartPacket;
/*      */ import net.minecraft.network.protocol.game.ServerboundSetCreativeModeSlotPacket;
/*      */ import net.minecraft.network.protocol.game.ServerboundSetJigsawBlockPacket;
/*      */ import net.minecraft.network.protocol.game.ServerboundSetStructureBlockPacket;
/*      */ import net.minecraft.network.protocol.game.ServerboundSignUpdatePacket;
/*      */ import net.minecraft.network.protocol.game.ServerboundSwingPacket;
/*      */ import net.minecraft.network.protocol.game.ServerboundTeleportToEntityPacket;
/*      */ import net.minecraft.network.protocol.game.ServerboundUseItemOnPacket;
/*      */ import net.minecraft.network.protocol.game.ServerboundUseItemPacket;
/*      */ import net.minecraft.network.protocol.status.ClientboundPongResponsePacket;
/*      */ import net.minecraft.network.protocol.status.ServerboundPingRequestPacket;
/*      */ import net.minecraft.resources.ResourceKey;
/*      */ import net.minecraft.resources.ResourceLocation;
/*      */ import net.minecraft.server.MinecraftServer;
/*      */ import net.minecraft.server.level.ServerLevel;
/*      */ import net.minecraft.server.level.ServerPlayer;
/*      */ import net.minecraft.util.FutureChain;
/*      */ import net.minecraft.util.Mth;
/*      */ import net.minecraft.util.SignatureValidator;
/*      */ import net.minecraft.util.StringUtil;
/*      */ import net.minecraft.util.TaskChainer;
/*      */ import net.minecraft.world.Container;
/*      */ import net.minecraft.world.InteractionHand;
/*      */ import net.minecraft.world.InteractionResult;
/*      */ import net.minecraft.world.effect.MobEffects;
/*      */ import net.minecraft.world.entity.Entity;
/*      */ import net.minecraft.world.entity.HasCustomInventoryScreen;
/*      */ import net.minecraft.world.entity.LivingEntity;
/*      */ import net.minecraft.world.entity.MoverType;
/*      */ import net.minecraft.world.entity.PlayerRideableJumping;
/*      */ import net.minecraft.world.entity.RelativeMovement;
/*      */ import net.minecraft.world.entity.player.ChatVisiblity;
/*      */ import net.minecraft.world.entity.player.Inventory;
/*      */ import net.minecraft.world.entity.player.Player;
/*      */ import net.minecraft.world.entity.player.ProfilePublicKey;
/*      */ import net.minecraft.world.entity.vehicle.Boat;
/*      */ import net.minecraft.world.inventory.AbstractContainerMenu;
/*      */ import net.minecraft.world.inventory.AnvilMenu;
/*      */ import net.minecraft.world.inventory.BeaconMenu;
/*      */ import net.minecraft.world.inventory.CrafterMenu;
/*      */ import net.minecraft.world.inventory.MerchantMenu;
/*      */ import net.minecraft.world.inventory.RecipeBookMenu;
/*      */ import net.minecraft.world.item.BlockItem;
/*      */ import net.minecraft.world.item.Item;
/*      */ import net.minecraft.world.item.ItemStack;
/*      */ import net.minecraft.world.item.Items;
/*      */ import net.minecraft.world.item.crafting.RecipeHolder;
/*      */ import net.minecraft.world.level.BaseCommandBlock;
/*      */ import net.minecraft.world.level.BlockGetter;
/*      */ import net.minecraft.world.level.GameRules;
/*      */ import net.minecraft.world.level.GameType;
/*      */ import net.minecraft.world.level.ItemLike;
/*      */ import net.minecraft.world.level.Level;
/*      */ import net.minecraft.world.level.LevelReader;
/*      */ import net.minecraft.world.level.block.Blocks;
/*      */ import net.minecraft.world.level.block.CommandBlock;
/*      */ import net.minecraft.world.level.block.entity.BlockEntity;
/*      */ import net.minecraft.world.level.block.entity.CommandBlockEntity;
/*      */ import net.minecraft.world.level.block.entity.CrafterBlockEntity;
/*      */ import net.minecraft.world.level.block.entity.JigsawBlockEntity;
/*      */ import net.minecraft.world.level.block.entity.SignBlockEntity;
/*      */ import net.minecraft.world.level.block.entity.StructureBlockEntity;
/*      */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*      */ import net.minecraft.world.level.block.state.BlockState;
/*      */ import net.minecraft.world.level.block.state.properties.Property;
/*      */ import net.minecraft.world.phys.AABB;
/*      */ import net.minecraft.world.phys.BlockHitResult;
/*      */ import net.minecraft.world.phys.Vec3;
/*      */ import net.minecraft.world.phys.shapes.BooleanOp;
/*      */ import net.minecraft.world.phys.shapes.Shapes;
/*      */ import net.minecraft.world.phys.shapes.VoxelShape;
/*      */ 
/*      */ public class ServerGamePacketListenerImpl extends ServerCommonPacketListenerImpl implements ServerGamePacketListener, ServerPlayerConnection, TickablePacketListener {
/*  183 */   static final Logger LOGGER = LogUtils.getLogger();
/*  184 */   public static final double MAX_INTERACTION_DISTANCE = Mth.square(6.0D);
/*      */   private static final int NO_BLOCK_UPDATES_TO_ACK = -1;
/*      */   private static final int TRACKED_MESSAGE_DISCONNECT_THRESHOLD = 4096;
/*  187 */   private static final Component CHAT_VALIDATION_FAILED = (Component)Component.translatable("multiplayer.disconnect.chat_validation_failed");
/*      */   public ServerPlayer player;
/*      */   public final PlayerChunkSender chunkSender;
/*      */   private int tickCount;
/*  191 */   private int ackBlockChangesUpTo = -1;
/*      */   
/*      */   private int chatSpamTickCount;
/*      */   
/*      */   private int dropSpamTickCount;
/*      */   
/*      */   private double firstGoodX;
/*      */   
/*      */   private double firstGoodY;
/*      */   private double firstGoodZ;
/*      */   private double lastGoodX;
/*      */   private double lastGoodY;
/*      */   private double lastGoodZ;
/*      */   @Nullable
/*      */   private Entity lastVehicle;
/*      */   private double vehicleFirstGoodX;
/*      */   private double vehicleFirstGoodY;
/*      */   private double vehicleFirstGoodZ;
/*      */   private double vehicleLastGoodX;
/*      */   private double vehicleLastGoodY;
/*      */   private double vehicleLastGoodZ;
/*      */   @Nullable
/*      */   private Vec3 awaitingPositionFromClient;
/*      */   private int awaitingTeleport;
/*      */   private int awaitingTeleportTime;
/*      */   private boolean clientIsFloating;
/*      */   private int aboveGroundTickCount;
/*      */   private boolean clientVehicleIsFloating;
/*      */   private int aboveGroundVehicleTickCount;
/*      */   private int receivedMovePacketCount;
/*      */   private int knownMovePacketCount;
/*      */   @Nullable
/*      */   private RemoteChatSession chatSession;
/*      */   private SignedMessageChain.Decoder signedMessageDecoder;
/*  225 */   private final LastSeenMessagesValidator lastSeenMessages = new LastSeenMessagesValidator(20);
/*      */   
/*  227 */   private final MessageSignatureCache messageSignatureCache = MessageSignatureCache.createDefault();
/*      */   
/*      */   private final FutureChain chatMessageChain;
/*      */   private boolean waitingForSwitchToConfig;
/*      */   
/*      */   public ServerGamePacketListenerImpl(MinecraftServer $$0, Connection $$1, ServerPlayer $$2, CommonListenerCookie $$3) {
/*  233 */     super($$0, $$1, $$3);
/*  234 */     this.chunkSender = new PlayerChunkSender($$1.isMemoryConnection());
/*  235 */     $$1.setListener((PacketListener)this);
/*  236 */     this.player = $$2;
/*  237 */     $$2.connection = this;
/*      */     
/*  239 */     $$2.getTextFilter().join();
/*      */     
/*  241 */     Objects.requireNonNull($$0); this.signedMessageDecoder = SignedMessageChain.Decoder.unsigned($$2.getUUID(), $$0::enforceSecureProfile);
/*      */     
/*  243 */     this.chatMessageChain = new FutureChain((Executor)$$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public void tick() {
/*  248 */     if (this.ackBlockChangesUpTo > -1) {
/*  249 */       send((Packet<?>)new ClientboundBlockChangedAckPacket(this.ackBlockChangesUpTo));
/*  250 */       this.ackBlockChangesUpTo = -1;
/*      */     } 
/*      */     
/*  253 */     resetPosition();
/*  254 */     this.player.xo = this.player.getX();
/*  255 */     this.player.yo = this.player.getY();
/*  256 */     this.player.zo = this.player.getZ();
/*  257 */     this.player.doTick();
/*  258 */     this.player.absMoveTo(this.firstGoodX, this.firstGoodY, this.firstGoodZ, this.player.getYRot(), this.player.getXRot());
/*  259 */     this.tickCount++;
/*  260 */     this.knownMovePacketCount = this.receivedMovePacketCount;
/*      */     
/*  262 */     if (this.clientIsFloating && !this.player.isSleeping() && !this.player.isPassenger() && !this.player.isDeadOrDying()) {
/*  263 */       if (++this.aboveGroundTickCount > 80) {
/*  264 */         LOGGER.warn("{} was kicked for floating too long!", this.player.getName().getString());
/*  265 */         disconnect((Component)Component.translatable("multiplayer.disconnect.flying"));
/*      */         return;
/*      */       } 
/*      */     } else {
/*  269 */       this.clientIsFloating = false;
/*  270 */       this.aboveGroundTickCount = 0;
/*      */     } 
/*      */     
/*  273 */     this.lastVehicle = this.player.getRootVehicle();
/*  274 */     if (this.lastVehicle == this.player || this.lastVehicle.getControllingPassenger() != this.player) {
/*  275 */       this.lastVehicle = null;
/*  276 */       this.clientVehicleIsFloating = false;
/*  277 */       this.aboveGroundVehicleTickCount = 0;
/*      */     } else {
/*  279 */       this.vehicleFirstGoodX = this.lastVehicle.getX();
/*  280 */       this.vehicleFirstGoodY = this.lastVehicle.getY();
/*  281 */       this.vehicleFirstGoodZ = this.lastVehicle.getZ();
/*  282 */       this.vehicleLastGoodX = this.lastVehicle.getX();
/*  283 */       this.vehicleLastGoodY = this.lastVehicle.getY();
/*  284 */       this.vehicleLastGoodZ = this.lastVehicle.getZ();
/*  285 */       if (this.clientVehicleIsFloating && this.player.getRootVehicle().getControllingPassenger() == this.player) {
/*  286 */         if (++this.aboveGroundVehicleTickCount > 80) {
/*  287 */           LOGGER.warn("{} was kicked for floating a vehicle too long!", this.player.getName().getString());
/*  288 */           disconnect((Component)Component.translatable("multiplayer.disconnect.flying"));
/*      */           return;
/*      */         } 
/*      */       } else {
/*  292 */         this.clientVehicleIsFloating = false;
/*  293 */         this.aboveGroundVehicleTickCount = 0;
/*      */       } 
/*      */     } 
/*      */     
/*  297 */     keepConnectionAlive();
/*      */     
/*  299 */     if (this.chatSpamTickCount > 0) {
/*  300 */       this.chatSpamTickCount--;
/*      */     }
/*  302 */     if (this.dropSpamTickCount > 0) {
/*  303 */       this.dropSpamTickCount--;
/*      */     }
/*      */     
/*  306 */     if (this.player.getLastActionTime() > 0L && this.server.getPlayerIdleTimeout() > 0 && Util.getMillis() - this.player.getLastActionTime() > this.server.getPlayerIdleTimeout() * 1000L * 60L) {
/*  307 */       disconnect((Component)Component.translatable("multiplayer.disconnect.idling"));
/*      */     }
/*      */   }
/*      */   
/*      */   public void resetPosition() {
/*  312 */     this.firstGoodX = this.player.getX();
/*  313 */     this.firstGoodY = this.player.getY();
/*  314 */     this.firstGoodZ = this.player.getZ();
/*  315 */     this.lastGoodX = this.player.getX();
/*  316 */     this.lastGoodY = this.player.getY();
/*  317 */     this.lastGoodZ = this.player.getZ();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isAcceptingMessages() {
/*  322 */     return (this.connection.isConnected() && !this.waitingForSwitchToConfig);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean shouldHandleMessage(Packet<?> $$0) {
/*  327 */     if (super.shouldHandleMessage($$0)) {
/*  328 */       return true;
/*      */     }
/*      */     
/*  331 */     return (this.waitingForSwitchToConfig && this.connection.isConnected() && $$0 instanceof ServerboundConfigurationAcknowledgedPacket);
/*      */   }
/*      */ 
/*      */   
/*      */   protected GameProfile playerProfile() {
/*  336 */     return this.player.getGameProfile();
/*      */   }
/*      */   
/*      */   private <T, R> CompletableFuture<R> filterTextPacket(T $$0, BiFunction<TextFilter, T, CompletableFuture<R>> $$1) {
/*  340 */     return ((CompletableFuture)$$1.apply(this.player.getTextFilter(), $$0))
/*  341 */       .thenApply($$0 -> {
/*      */           if (!isAcceptingMessages()) {
/*      */             LOGGER.debug("Ignoring packet due to disconnection");
/*      */             throw new CancellationException("disconnected");
/*      */           } 
/*      */           return $$0;
/*      */         });
/*      */   }
/*      */ 
/*      */   
/*      */   private CompletableFuture<FilteredText> filterTextPacket(String $$0) {
/*  352 */     return filterTextPacket($$0, TextFilter::processStreamMessage);
/*      */   }
/*      */   
/*      */   private CompletableFuture<List<FilteredText>> filterTextPacket(List<String> $$0) {
/*  356 */     return filterTextPacket($$0, TextFilter::processMessageBundle);
/*      */   }
/*      */ 
/*      */   
/*      */   public void handlePlayerInput(ServerboundPlayerInputPacket $$0) {
/*  361 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, this.player.serverLevel());
/*  362 */     this.player.setPlayerInput($$0.getXxa(), $$0.getZza(), $$0.isJumping(), $$0.isShiftKeyDown());
/*      */   }
/*      */   
/*      */   private static boolean containsInvalidValues(double $$0, double $$1, double $$2, float $$3, float $$4) {
/*  366 */     return (Double.isNaN($$0) || Double.isNaN($$1) || Double.isNaN($$2) || !Floats.isFinite($$4) || !Floats.isFinite($$3));
/*      */   }
/*      */   
/*      */   private static double clampHorizontal(double $$0) {
/*  370 */     return Mth.clamp($$0, -3.0E7D, 3.0E7D);
/*      */   }
/*      */   
/*      */   private static double clampVertical(double $$0) {
/*  374 */     return Mth.clamp($$0, -2.0E7D, 2.0E7D);
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleMoveVehicle(ServerboundMoveVehiclePacket $$0) {
/*  379 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, this.player.serverLevel());
/*  380 */     if (containsInvalidValues($$0.getX(), $$0.getY(), $$0.getZ(), $$0.getYRot(), $$0.getXRot())) {
/*  381 */       disconnect((Component)Component.translatable("multiplayer.disconnect.invalid_vehicle_movement"));
/*      */       
/*      */       return;
/*      */     } 
/*  385 */     Entity $$1 = this.player.getRootVehicle();
/*  386 */     if ($$1 != this.player && $$1.getControllingPassenger() == this.player && $$1 == this.lastVehicle) {
/*  387 */       ServerLevel $$2 = this.player.serverLevel();
/*  388 */       double $$3 = $$1.getX();
/*  389 */       double $$4 = $$1.getY();
/*  390 */       double $$5 = $$1.getZ();
/*      */       
/*  392 */       double $$6 = clampHorizontal($$0.getX());
/*  393 */       double $$7 = clampVertical($$0.getY());
/*  394 */       double $$8 = clampHorizontal($$0.getZ());
/*  395 */       float $$9 = Mth.wrapDegrees($$0.getYRot());
/*  396 */       float $$10 = Mth.wrapDegrees($$0.getXRot());
/*      */       
/*  398 */       double $$11 = $$6 - this.vehicleFirstGoodX;
/*  399 */       double $$12 = $$7 - this.vehicleFirstGoodY;
/*  400 */       double $$13 = $$8 - this.vehicleFirstGoodZ;
/*      */       
/*  402 */       double $$14 = $$1.getDeltaMovement().lengthSqr();
/*  403 */       double $$15 = $$11 * $$11 + $$12 * $$12 + $$13 * $$13;
/*      */       
/*  405 */       if ($$15 - $$14 > 100.0D && !isSingleplayerOwner()) {
/*  406 */         LOGGER.warn("{} (vehicle of {}) moved too quickly! {},{},{}", new Object[] { $$1.getName().getString(), this.player.getName().getString(), Double.valueOf($$11), Double.valueOf($$12), Double.valueOf($$13) });
/*  407 */         send((Packet<?>)new ClientboundMoveVehiclePacket($$1));
/*      */         
/*      */         return;
/*      */       } 
/*  411 */       boolean $$16 = $$2.noCollision($$1, $$1.getBoundingBox().deflate(0.0625D));
/*      */ 
/*      */       
/*  414 */       $$11 = $$6 - this.vehicleLastGoodX;
/*  415 */       $$12 = $$7 - this.vehicleLastGoodY - 1.0E-6D;
/*  416 */       $$13 = $$8 - this.vehicleLastGoodZ;
/*  417 */       boolean $$17 = $$1.verticalCollisionBelow;
/*  418 */       if ($$1 instanceof LivingEntity) { LivingEntity $$18 = (LivingEntity)$$1; if ($$18.onClimbable())
/*  419 */           $$18.resetFallDistance();  }
/*      */       
/*  421 */       $$1.move(MoverType.PLAYER, new Vec3($$11, $$12, $$13));
/*      */       
/*  423 */       double $$19 = $$12;
/*      */       
/*  425 */       $$11 = $$6 - $$1.getX();
/*  426 */       $$12 = $$7 - $$1.getY();
/*  427 */       if ($$12 > -0.5D || $$12 < 0.5D) {
/*  428 */         $$12 = 0.0D;
/*      */       }
/*  430 */       $$13 = $$8 - $$1.getZ();
/*  431 */       $$15 = $$11 * $$11 + $$12 * $$12 + $$13 * $$13;
/*  432 */       boolean $$20 = false;
/*  433 */       if ($$15 > 0.0625D) {
/*  434 */         $$20 = true;
/*  435 */         LOGGER.warn("{} (vehicle of {}) moved wrongly! {}", new Object[] { $$1.getName().getString(), this.player.getName().getString(), Double.valueOf(Math.sqrt($$15)) });
/*      */       } 
/*  437 */       $$1.absMoveTo($$6, $$7, $$8, $$9, $$10);
/*      */       
/*  439 */       boolean $$21 = $$2.noCollision($$1, $$1.getBoundingBox().deflate(0.0625D));
/*  440 */       if ($$16 && ($$20 || !$$21)) {
/*  441 */         $$1.absMoveTo($$3, $$4, $$5, $$9, $$10);
/*  442 */         send((Packet<?>)new ClientboundMoveVehiclePacket($$1));
/*      */         
/*      */         return;
/*      */       } 
/*  446 */       this.player.serverLevel().getChunkSource().move(this.player);
/*  447 */       this.player.checkMovementStatistics(this.player.getX() - $$3, this.player.getY() - $$4, this.player.getZ() - $$5);
/*  448 */       this
/*      */ 
/*      */ 
/*      */         
/*  452 */         .clientVehicleIsFloating = ($$19 >= -0.03125D && !$$17 && !this.server.isFlightAllowed() && !$$1.isNoGravity() && noBlocksAround($$1));
/*      */       
/*  454 */       this.vehicleLastGoodX = $$1.getX();
/*  455 */       this.vehicleLastGoodY = $$1.getY();
/*  456 */       this.vehicleLastGoodZ = $$1.getZ();
/*      */     } 
/*      */   }
/*      */   
/*      */   private boolean noBlocksAround(Entity $$0) {
/*  461 */     return $$0.level().getBlockStates($$0.getBoundingBox().inflate(0.0625D).expandTowards(0.0D, -0.55D, 0.0D)).allMatch(BlockBehaviour.BlockStateBase::isAir);
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleAcceptTeleportPacket(ServerboundAcceptTeleportationPacket $$0) {
/*  466 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, this.player.serverLevel());
/*  467 */     if ($$0.getId() == this.awaitingTeleport) {
/*  468 */       if (this.awaitingPositionFromClient == null) {
/*  469 */         disconnect((Component)Component.translatable("multiplayer.disconnect.invalid_player_movement"));
/*      */         return;
/*      */       } 
/*  472 */       this.player.absMoveTo(this.awaitingPositionFromClient.x, this.awaitingPositionFromClient.y, this.awaitingPositionFromClient.z, this.player.getYRot(), this.player.getXRot());
/*      */       
/*  474 */       this.lastGoodX = this.awaitingPositionFromClient.x;
/*  475 */       this.lastGoodY = this.awaitingPositionFromClient.y;
/*  476 */       this.lastGoodZ = this.awaitingPositionFromClient.z;
/*  477 */       if (this.player.isChangingDimension()) {
/*  478 */         this.player.hasChangedDimension();
/*      */       }
/*      */       
/*  481 */       this.awaitingPositionFromClient = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleRecipeBookSeenRecipePacket(ServerboundRecipeBookSeenRecipePacket $$0) {
/*  487 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, this.player.serverLevel());
/*  488 */     Objects.requireNonNull(this.player.getRecipeBook()); this.server.getRecipeManager().byKey($$0.getRecipe()).ifPresent(this.player.getRecipeBook()::removeHighlight);
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleRecipeBookChangeSettingsPacket(ServerboundRecipeBookChangeSettingsPacket $$0) {
/*  493 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, this.player.serverLevel());
/*  494 */     this.player.getRecipeBook().setBookSetting($$0.getBookType(), $$0.isOpen(), $$0.isFiltering());
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleSeenAdvancements(ServerboundSeenAdvancementsPacket $$0) {
/*  499 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, this.player.serverLevel());
/*  500 */     if ($$0.getAction() == ServerboundSeenAdvancementsPacket.Action.OPENED_TAB) {
/*  501 */       ResourceLocation $$1 = Objects.<ResourceLocation>requireNonNull($$0.getTab());
/*  502 */       AdvancementHolder $$2 = this.server.getAdvancements().get($$1);
/*  503 */       if ($$2 != null) {
/*  504 */         this.player.getAdvancements().setSelectedTab($$2);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleCustomCommandSuggestions(ServerboundCommandSuggestionPacket $$0) {
/*  511 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, this.player.serverLevel());
/*  512 */     StringReader $$1 = new StringReader($$0.getCommand());
/*  513 */     if ($$1.canRead() && $$1.peek() == '/') {
/*  514 */       $$1.skip();
/*      */     }
/*  516 */     ParseResults<CommandSourceStack> $$2 = this.server.getCommands().getDispatcher().parse($$1, this.player.createCommandSourceStack());
/*  517 */     this.server.getCommands().getDispatcher().getCompletionSuggestions($$2).thenAccept($$1 -> send((Packet<?>)new ClientboundCommandSuggestionsPacket($$0.getId(), $$1)));
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleSetCommandBlock(ServerboundSetCommandBlockPacket $$0) {
/*  522 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, this.player.serverLevel());
/*  523 */     if (!this.server.isCommandBlockEnabled()) {
/*  524 */       this.player.sendSystemMessage((Component)Component.translatable("advMode.notEnabled"));
/*      */       return;
/*      */     } 
/*  527 */     if (!this.player.canUseGameMasterBlocks()) {
/*  528 */       this.player.sendSystemMessage((Component)Component.translatable("advMode.notAllowed"));
/*      */       return;
/*      */     } 
/*  531 */     BaseCommandBlock $$1 = null;
/*  532 */     CommandBlockEntity $$2 = null;
/*  533 */     BlockPos $$3 = $$0.getPos();
/*  534 */     BlockEntity $$4 = this.player.level().getBlockEntity($$3);
/*  535 */     if ($$4 instanceof CommandBlockEntity) {
/*  536 */       $$2 = (CommandBlockEntity)$$4;
/*  537 */       $$1 = $$2.getCommandBlock();
/*      */     } 
/*      */     
/*  540 */     String $$5 = $$0.getCommand();
/*  541 */     boolean $$6 = $$0.isTrackOutput();
/*      */     
/*  543 */     if ($$1 != null) {
/*  544 */       BlockState $$10, $$11, $$12; CommandBlockEntity.Mode $$7 = $$2.getMode();
/*      */       
/*  546 */       BlockState $$8 = this.player.level().getBlockState($$3);
/*  547 */       Direction $$9 = (Direction)$$8.getValue((Property)CommandBlock.FACING);
/*      */       
/*  549 */       switch ($$0.getMode()) {
/*      */         case PERFORM_RESPAWN:
/*  551 */           $$10 = Blocks.CHAIN_COMMAND_BLOCK.defaultBlockState();
/*      */           break;
/*      */         case REQUEST_STATS:
/*  554 */           $$11 = Blocks.REPEATING_COMMAND_BLOCK.defaultBlockState();
/*      */           break;
/*      */         
/*      */         default:
/*  558 */           $$12 = Blocks.COMMAND_BLOCK.defaultBlockState();
/*      */           break;
/*      */       } 
/*  561 */       BlockState $$13 = (BlockState)((BlockState)$$12.setValue((Property)CommandBlock.FACING, (Comparable)$$9)).setValue((Property)CommandBlock.CONDITIONAL, Boolean.valueOf($$0.isConditional()));
/*  562 */       if ($$13 != $$8) {
/*  563 */         this.player.level().setBlock($$3, $$13, 2);
/*      */         
/*  565 */         $$4.setBlockState($$13);
/*  566 */         this.player.level().getChunkAt($$3).setBlockEntity($$4);
/*      */       } 
/*      */       
/*  569 */       $$1.setCommand($$5);
/*  570 */       $$1.setTrackOutput($$6);
/*  571 */       if (!$$6) {
/*  572 */         $$1.setLastOutput(null);
/*      */       }
/*  574 */       $$2.setAutomatic($$0.isAutomatic());
/*  575 */       if ($$7 != $$0.getMode()) {
/*  576 */         $$2.onModeSwitch();
/*      */       }
/*  578 */       $$1.onUpdated();
/*  579 */       if (!StringUtil.isNullOrEmpty($$5)) {
/*  580 */         this.player.sendSystemMessage((Component)Component.translatable("advMode.setCommand.success", new Object[] { $$5 }));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleSetCommandMinecart(ServerboundSetCommandMinecartPacket $$0) {
/*  587 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, this.player.serverLevel());
/*  588 */     if (!this.server.isCommandBlockEnabled()) {
/*  589 */       this.player.sendSystemMessage((Component)Component.translatable("advMode.notEnabled"));
/*      */       return;
/*      */     } 
/*  592 */     if (!this.player.canUseGameMasterBlocks()) {
/*  593 */       this.player.sendSystemMessage((Component)Component.translatable("advMode.notAllowed"));
/*      */       return;
/*      */     } 
/*  596 */     BaseCommandBlock $$1 = $$0.getCommandBlock(this.player.level());
/*      */     
/*  598 */     if ($$1 != null) {
/*  599 */       $$1.setCommand($$0.getCommand());
/*  600 */       $$1.setTrackOutput($$0.isTrackOutput());
/*  601 */       if (!$$0.isTrackOutput()) {
/*  602 */         $$1.setLastOutput(null);
/*      */       }
/*  604 */       $$1.onUpdated();
/*  605 */       this.player.sendSystemMessage((Component)Component.translatable("advMode.setCommand.success", new Object[] { $$0.getCommand() }));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void handlePickItem(ServerboundPickItemPacket $$0) {
/*  611 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, this.player.serverLevel());
/*  612 */     this.player.getInventory().pickSlot($$0.getSlot());
/*  613 */     this.player.connection.send((Packet<?>)new ClientboundContainerSetSlotPacket(-2, 0, (this.player.getInventory()).selected, this.player.getInventory().getItem((this.player.getInventory()).selected)));
/*  614 */     this.player.connection.send((Packet<?>)new ClientboundContainerSetSlotPacket(-2, 0, $$0.getSlot(), this.player.getInventory().getItem($$0.getSlot())));
/*  615 */     this.player.connection.send((Packet<?>)new ClientboundSetCarriedItemPacket((this.player.getInventory()).selected));
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleRenameItem(ServerboundRenameItemPacket $$0) {
/*  620 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, this.player.serverLevel());
/*  621 */     AbstractContainerMenu abstractContainerMenu = this.player.containerMenu; if (abstractContainerMenu instanceof AnvilMenu) { AnvilMenu $$1 = (AnvilMenu)abstractContainerMenu;
/*  622 */       if (!$$1.stillValid((Player)this.player)) {
/*  623 */         LOGGER.debug("Player {} interacted with invalid menu {}", this.player, $$1);
/*      */         return;
/*      */       } 
/*  626 */       $$1.setItemName($$0.getName()); }
/*      */   
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleSetBeaconPacket(ServerboundSetBeaconPacket $$0) {
/*  632 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, this.player.serverLevel());
/*  633 */     AbstractContainerMenu abstractContainerMenu = this.player.containerMenu; if (abstractContainerMenu instanceof BeaconMenu) { BeaconMenu $$1 = (BeaconMenu)abstractContainerMenu;
/*  634 */       if (!this.player.containerMenu.stillValid((Player)this.player)) {
/*  635 */         LOGGER.debug("Player {} interacted with invalid menu {}", this.player, this.player.containerMenu);
/*      */         return;
/*      */       } 
/*  638 */       $$1.updateEffects($$0.getPrimary(), $$0.getSecondary()); }
/*      */   
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleSetStructureBlock(ServerboundSetStructureBlockPacket $$0) {
/*  644 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, this.player.serverLevel());
/*  645 */     if (!this.player.canUseGameMasterBlocks()) {
/*      */       return;
/*      */     }
/*  648 */     BlockPos $$1 = $$0.getPos();
/*  649 */     BlockState $$2 = this.player.level().getBlockState($$1);
/*  650 */     BlockEntity $$3 = this.player.level().getBlockEntity($$1);
/*  651 */     if ($$3 instanceof StructureBlockEntity) { StructureBlockEntity $$4 = (StructureBlockEntity)$$3;
/*  652 */       $$4.setMode($$0.getMode());
/*  653 */       $$4.setStructureName($$0.getName());
/*  654 */       $$4.setStructurePos($$0.getOffset());
/*  655 */       $$4.setStructureSize($$0.getSize());
/*  656 */       $$4.setMirror($$0.getMirror());
/*  657 */       $$4.setRotation($$0.getRotation());
/*  658 */       $$4.setMetaData($$0.getData());
/*  659 */       $$4.setIgnoreEntities($$0.isIgnoreEntities());
/*  660 */       $$4.setShowAir($$0.isShowAir());
/*  661 */       $$4.setShowBoundingBox($$0.isShowBoundingBox());
/*  662 */       $$4.setIntegrity($$0.getIntegrity());
/*  663 */       $$4.setSeed($$0.getSeed());
/*      */       
/*  665 */       if ($$4.hasStructureName()) {
/*  666 */         String $$5 = $$4.getStructureName();
/*  667 */         if ($$0.getUpdateType() == StructureBlockEntity.UpdateType.SAVE_AREA) {
/*  668 */           if ($$4.saveStructure()) {
/*  669 */             this.player.displayClientMessage((Component)Component.translatable("structure_block.save_success", new Object[] { $$5 }), false);
/*      */           } else {
/*  671 */             this.player.displayClientMessage((Component)Component.translatable("structure_block.save_failure", new Object[] { $$5 }), false);
/*      */           } 
/*  673 */         } else if ($$0.getUpdateType() == StructureBlockEntity.UpdateType.LOAD_AREA) {
/*  674 */           if (!$$4.isStructureLoadable()) {
/*  675 */             this.player.displayClientMessage((Component)Component.translatable("structure_block.load_not_found", new Object[] { $$5 }), false);
/*  676 */           } else if ($$4.placeStructureIfSameSize(this.player.serverLevel())) {
/*  677 */             this.player.displayClientMessage((Component)Component.translatable("structure_block.load_success", new Object[] { $$5 }), false);
/*      */           } else {
/*  679 */             this.player.displayClientMessage((Component)Component.translatable("structure_block.load_prepare", new Object[] { $$5 }), false);
/*      */           } 
/*  681 */         } else if ($$0.getUpdateType() == StructureBlockEntity.UpdateType.SCAN_AREA) {
/*  682 */           if ($$4.detectSize()) {
/*  683 */             this.player.displayClientMessage((Component)Component.translatable("structure_block.size_success", new Object[] { $$5 }), false);
/*      */           } else {
/*  685 */             this.player.displayClientMessage((Component)Component.translatable("structure_block.size_failure"), false);
/*      */           } 
/*      */         } 
/*      */       } else {
/*  689 */         this.player.displayClientMessage((Component)Component.translatable("structure_block.invalid_structure_name", new Object[] { $$0.getName() }), false);
/*      */       } 
/*      */       
/*  692 */       $$4.setChanged();
/*  693 */       this.player.level().sendBlockUpdated($$1, $$2, $$2, 3); }
/*      */   
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleSetJigsawBlock(ServerboundSetJigsawBlockPacket $$0) {
/*  699 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, this.player.serverLevel());
/*  700 */     if (!this.player.canUseGameMasterBlocks()) {
/*      */       return;
/*      */     }
/*  703 */     BlockPos $$1 = $$0.getPos();
/*  704 */     BlockState $$2 = this.player.level().getBlockState($$1);
/*  705 */     BlockEntity $$3 = this.player.level().getBlockEntity($$1);
/*  706 */     if ($$3 instanceof JigsawBlockEntity) { JigsawBlockEntity $$4 = (JigsawBlockEntity)$$3;
/*  707 */       $$4.setName($$0.getName());
/*  708 */       $$4.setTarget($$0.getTarget());
/*  709 */       $$4.setPool(ResourceKey.create(Registries.TEMPLATE_POOL, $$0.getPool()));
/*  710 */       $$4.setFinalState($$0.getFinalState());
/*  711 */       $$4.setJoint($$0.getJoint());
/*  712 */       $$4.setPlacementPriority($$0.getPlacementPriority());
/*  713 */       $$4.setSelectionPriority($$0.getSelectionPriority());
/*  714 */       $$4.setChanged();
/*  715 */       this.player.level().sendBlockUpdated($$1, $$2, $$2, 3); }
/*      */   
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleJigsawGenerate(ServerboundJigsawGeneratePacket $$0) {
/*  721 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, this.player.serverLevel());
/*  722 */     if (!this.player.canUseGameMasterBlocks()) {
/*      */       return;
/*      */     }
/*  725 */     BlockPos $$1 = $$0.getPos();
/*  726 */     BlockEntity $$2 = this.player.level().getBlockEntity($$1);
/*  727 */     if ($$2 instanceof JigsawBlockEntity) { JigsawBlockEntity $$3 = (JigsawBlockEntity)$$2;
/*  728 */       $$3.generate(this.player.serverLevel(), $$0.levels(), $$0.keepJigsaws()); }
/*      */   
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleSelectTrade(ServerboundSelectTradePacket $$0) {
/*  734 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, this.player.serverLevel());
/*  735 */     int $$1 = $$0.getItem();
/*  736 */     AbstractContainerMenu abstractContainerMenu = this.player.containerMenu; if (abstractContainerMenu instanceof MerchantMenu) { MerchantMenu $$2 = (MerchantMenu)abstractContainerMenu;
/*  737 */       if (!$$2.stillValid((Player)this.player)) {
/*  738 */         LOGGER.debug("Player {} interacted with invalid menu {}", this.player, $$2);
/*      */         return;
/*      */       } 
/*  741 */       $$2.setSelectionHint($$1);
/*  742 */       $$2.tryMoveItems($$1); }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleEditBook(ServerboundEditBookPacket $$0) {
/*  749 */     int $$1 = $$0.getSlot();
/*  750 */     if (!Inventory.isHotbarSlot($$1) && $$1 != 40) {
/*      */       return;
/*      */     }
/*      */     
/*  754 */     List<String> $$2 = Lists.newArrayList();
/*  755 */     Optional<String> $$3 = $$0.getTitle();
/*  756 */     Objects.requireNonNull($$2); $$3.ifPresent($$2::add);
/*  757 */     Objects.requireNonNull($$2); $$0.getPages().stream().limit(100L).forEach($$2::add);
/*      */ 
/*      */ 
/*      */     
/*  761 */     Consumer<List<FilteredText>> $$4 = $$3.isPresent() ? ($$1 -> signBook($$1.get(0), $$1.subList(1, $$1.size()), $$0)) : ($$1 -> updateBookContents($$1, $$0));
/*  762 */     filterTextPacket($$2).thenAcceptAsync($$4, (Executor)this.server);
/*      */   }
/*      */   
/*      */   private void updateBookContents(List<FilteredText> $$0, int $$1) {
/*  766 */     ItemStack $$2 = this.player.getInventory().getItem($$1);
/*  767 */     if (!$$2.is(Items.WRITABLE_BOOK)) {
/*      */       return;
/*      */     }
/*      */     
/*  771 */     updateBookPages($$0, UnaryOperator.identity(), $$2);
/*      */   }
/*      */   
/*      */   private void signBook(FilteredText $$0, List<FilteredText> $$1, int $$2) {
/*  775 */     ItemStack $$3 = this.player.getInventory().getItem($$2);
/*  776 */     if (!$$3.is(Items.WRITABLE_BOOK)) {
/*      */       return;
/*      */     }
/*      */     
/*  780 */     ItemStack $$4 = new ItemStack((ItemLike)Items.WRITTEN_BOOK);
/*  781 */     CompoundTag $$5 = $$3.getTag();
/*  782 */     if ($$5 != null) {
/*  783 */       $$4.setTag($$5.copy());
/*      */     }
/*  785 */     $$4.addTagElement("author", (Tag)StringTag.valueOf(this.player.getName().getString()));
/*      */     
/*  787 */     if (this.player.isTextFilteringEnabled()) {
/*  788 */       $$4.addTagElement("title", (Tag)StringTag.valueOf($$0.filteredOrEmpty()));
/*      */     } else {
/*  790 */       $$4.addTagElement("filtered_title", (Tag)StringTag.valueOf($$0.filteredOrEmpty()));
/*  791 */       $$4.addTagElement("title", (Tag)StringTag.valueOf($$0.raw()));
/*      */     } 
/*      */     
/*  794 */     updateBookPages($$1, $$0 -> Component.Serializer.toJson((Component)Component.literal($$0)), $$4);
/*  795 */     this.player.getInventory().setItem($$2, $$4);
/*      */   }
/*      */   
/*      */   private void updateBookPages(List<FilteredText> $$0, UnaryOperator<String> $$1, ItemStack $$2) {
/*  799 */     ListTag $$3 = new ListTag();
/*  800 */     if (this.player.isTextFilteringEnabled()) {
/*      */       
/*  802 */       Objects.requireNonNull($$3); $$0.stream().map($$1 -> StringTag.valueOf($$0.apply($$1.filteredOrEmpty()))).forEach($$3::add);
/*      */     } else {
/*  804 */       CompoundTag $$4 = new CompoundTag();
/*      */       
/*  806 */       for (int $$5 = 0, $$6 = $$0.size(); $$5 < $$6; $$5++) {
/*  807 */         FilteredText $$7 = $$0.get($$5);
/*  808 */         String $$8 = $$7.raw();
/*  809 */         $$3.add(StringTag.valueOf($$1.apply($$8)));
/*      */         
/*  811 */         if ($$7.isFiltered()) {
/*  812 */           $$4.putString(String.valueOf($$5), $$1.apply($$7.filteredOrEmpty()));
/*      */         }
/*      */       } 
/*  815 */       if (!$$4.isEmpty()) {
/*  816 */         $$2.addTagElement("filtered_pages", (Tag)$$4);
/*      */       }
/*      */     } 
/*  819 */     $$2.addTagElement("pages", (Tag)$$3);
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleEntityTagQuery(ServerboundEntityTagQuery $$0) {
/*  824 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, this.player.serverLevel());
/*      */     
/*  826 */     if (!this.player.hasPermissions(2)) {
/*      */       return;
/*      */     }
/*      */     
/*  830 */     Entity $$1 = this.player.level().getEntity($$0.getEntityId());
/*  831 */     if ($$1 != null) {
/*  832 */       CompoundTag $$2 = $$1.saveWithoutId(new CompoundTag());
/*  833 */       this.player.connection.send((Packet<?>)new ClientboundTagQueryPacket($$0.getTransactionId(), $$2));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleContainerSlotStateChanged(ServerboundContainerSlotStateChangedPacket $$0) {
/*  839 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, this.player.serverLevel());
/*      */     
/*  841 */     if (this.player.isSpectator() || $$0.containerId() != this.player.containerMenu.containerId) {
/*      */       return;
/*      */     }
/*      */     
/*  845 */     AbstractContainerMenu abstractContainerMenu = this.player.containerMenu; if (abstractContainerMenu instanceof CrafterMenu) { CrafterMenu $$1 = (CrafterMenu)abstractContainerMenu;
/*  846 */       Container container = $$1.getContainer(); if (container instanceof CrafterBlockEntity) { CrafterBlockEntity $$2 = (CrafterBlockEntity)container;
/*  847 */         $$2.setSlotState($$0.slotId(), $$0.newState()); }
/*      */        }
/*      */   
/*      */   }
/*      */   
/*      */   public void handleBlockEntityTagQuery(ServerboundBlockEntityTagQuery $$0) {
/*  853 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, this.player.serverLevel());
/*      */     
/*  855 */     if (!this.player.hasPermissions(2)) {
/*      */       return;
/*      */     }
/*      */     
/*  859 */     BlockEntity $$1 = this.player.level().getBlockEntity($$0.getPos());
/*  860 */     CompoundTag $$2 = ($$1 != null) ? $$1.saveWithoutMetadata() : null;
/*  861 */     this.player.connection.send((Packet<?>)new ClientboundTagQueryPacket($$0.getTransactionId(), $$2));
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleMovePlayer(ServerboundMovePlayerPacket $$0) {
/*  866 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, this.player.serverLevel());
/*  867 */     if (containsInvalidValues($$0.getX(0.0D), $$0.getY(0.0D), $$0.getZ(0.0D), $$0.getYRot(0.0F), $$0.getXRot(0.0F))) {
/*  868 */       disconnect((Component)Component.translatable("multiplayer.disconnect.invalid_player_movement"));
/*      */       return;
/*      */     } 
/*  871 */     ServerLevel $$1 = this.player.serverLevel();
/*      */     
/*  873 */     if (this.player.wonGame) {
/*      */       return;
/*      */     }
/*      */     
/*  877 */     if (this.tickCount == 0) {
/*  878 */       resetPosition();
/*      */     }
/*      */     
/*  881 */     if (this.awaitingPositionFromClient != null) {
/*      */       
/*  883 */       if (this.tickCount - this.awaitingTeleportTime > 20) {
/*  884 */         this.awaitingTeleportTime = this.tickCount;
/*  885 */         teleport(this.awaitingPositionFromClient.x, this.awaitingPositionFromClient.y, this.awaitingPositionFromClient.z, this.player.getYRot(), this.player.getXRot());
/*      */       } 
/*      */       
/*      */       return;
/*      */     } 
/*  890 */     this.awaitingTeleportTime = this.tickCount;
/*  891 */     double $$2 = clampHorizontal($$0.getX(this.player.getX()));
/*  892 */     double $$3 = clampVertical($$0.getY(this.player.getY()));
/*  893 */     double $$4 = clampHorizontal($$0.getZ(this.player.getZ()));
/*  894 */     float $$5 = Mth.wrapDegrees($$0.getYRot(this.player.getYRot()));
/*  895 */     float $$6 = Mth.wrapDegrees($$0.getXRot(this.player.getXRot()));
/*      */     
/*  897 */     if (this.player.isPassenger()) {
/*  898 */       this.player.absMoveTo(this.player.getX(), this.player.getY(), this.player.getZ(), $$5, $$6);
/*  899 */       this.player.serverLevel().getChunkSource().move(this.player);
/*      */       
/*      */       return;
/*      */     } 
/*  903 */     double $$7 = this.player.getX();
/*  904 */     double $$8 = this.player.getY();
/*  905 */     double $$9 = this.player.getZ();
/*      */     
/*  907 */     double $$10 = $$2 - this.firstGoodX;
/*  908 */     double $$11 = $$3 - this.firstGoodY;
/*  909 */     double $$12 = $$4 - this.firstGoodZ;
/*      */     
/*  911 */     double $$13 = this.player.getDeltaMovement().lengthSqr();
/*  912 */     double $$14 = $$10 * $$10 + $$11 * $$11 + $$12 * $$12;
/*      */     
/*  914 */     if (this.player.isSleeping()) {
/*  915 */       if ($$14 > 1.0D) {
/*  916 */         teleport(this.player.getX(), this.player.getY(), this.player.getZ(), $$5, $$6);
/*      */       }
/*      */       
/*      */       return;
/*      */     } 
/*  921 */     if ($$1.tickRateManager().runsNormally()) {
/*  922 */       this.receivedMovePacketCount++;
/*  923 */       int $$15 = this.receivedMovePacketCount - this.knownMovePacketCount;
/*  924 */       if ($$15 > 5) {
/*  925 */         LOGGER.debug("{} is sending move packets too frequently ({} packets since last tick)", this.player.getName().getString(), Integer.valueOf($$15));
/*  926 */         $$15 = 1;
/*      */       } 
/*      */       
/*  929 */       if (!this.player.isChangingDimension() && (
/*  930 */         !this.player.level().getGameRules().getBoolean(GameRules.RULE_DISABLE_ELYTRA_MOVEMENT_CHECK) || !this.player.isFallFlying())) {
/*      */ 
/*      */         
/*  933 */         float $$16 = this.player.isFallFlying() ? 300.0F : 100.0F;
/*  934 */         if ($$14 - $$13 > ($$16 * $$15) && !isSingleplayerOwner()) {
/*  935 */           LOGGER.warn("{} moved too quickly! {},{},{}", new Object[] { this.player.getName().getString(), Double.valueOf($$10), Double.valueOf($$11), Double.valueOf($$12) });
/*  936 */           teleport(this.player.getX(), this.player.getY(), this.player.getZ(), this.player.getYRot(), this.player.getXRot());
/*      */           
/*      */           return;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  943 */     AABB $$17 = this.player.getBoundingBox();
/*      */     
/*  945 */     $$10 = $$2 - this.lastGoodX;
/*  946 */     $$11 = $$3 - this.lastGoodY;
/*  947 */     $$12 = $$4 - this.lastGoodZ;
/*      */     
/*  949 */     boolean $$18 = ($$11 > 0.0D);
/*      */     
/*  951 */     if (this.player.onGround() && !$$0.isOnGround() && $$18)
/*      */     {
/*  953 */       this.player.jumpFromGround();
/*      */     }
/*  955 */     boolean $$19 = this.player.verticalCollisionBelow;
/*  956 */     this.player.move(MoverType.PLAYER, new Vec3($$10, $$11, $$12));
/*      */     
/*  958 */     double $$20 = $$11;
/*      */     
/*  960 */     $$10 = $$2 - this.player.getX();
/*  961 */     $$11 = $$3 - this.player.getY();
/*  962 */     if ($$11 > -0.5D || $$11 < 0.5D) {
/*  963 */       $$11 = 0.0D;
/*      */     }
/*  965 */     $$12 = $$4 - this.player.getZ();
/*  966 */     $$14 = $$10 * $$10 + $$11 * $$11 + $$12 * $$12;
/*  967 */     boolean $$21 = false;
/*  968 */     if (!this.player.isChangingDimension() && $$14 > 0.0625D && !this.player.isSleeping() && !this.player.gameMode.isCreative() && this.player.gameMode.getGameModeForPlayer() != GameType.SPECTATOR) {
/*  969 */       $$21 = true;
/*  970 */       LOGGER.warn("{} moved wrongly!", this.player.getName().getString());
/*      */     } 
/*      */     
/*  973 */     if (!this.player.noPhysics && !this.player.isSleeping() && ((
/*  974 */       $$21 && $$1.noCollision((Entity)this.player, $$17)) || isPlayerCollidingWithAnythingNew((LevelReader)$$1, $$17, $$2, $$3, $$4))) {
/*  975 */       teleport($$7, $$8, $$9, $$5, $$6);
/*  976 */       this.player.doCheckFallDamage(this.player.getX() - $$7, this.player.getY() - $$8, this.player.getZ() - $$9, $$0.isOnGround());
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  981 */     this.player.absMoveTo($$2, $$3, $$4, $$5, $$6);
/*      */     
/*  983 */     this
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  991 */       .clientIsFloating = ($$20 >= -0.03125D && !$$19 && this.player.gameMode.getGameModeForPlayer() != GameType.SPECTATOR && !this.server.isFlightAllowed() && !(this.player.getAbilities()).mayfly && !this.player.hasEffect(MobEffects.LEVITATION) && !this.player.isFallFlying() && !this.player.isAutoSpinAttack() && noBlocksAround((Entity)this.player));
/*      */     
/*  993 */     this.player.serverLevel().getChunkSource().move(this.player);
/*  994 */     this.player.doCheckFallDamage(this.player.getX() - $$7, this.player.getY() - $$8, this.player.getZ() - $$9, $$0.isOnGround());
/*  995 */     this.player.setOnGroundWithKnownMovement($$0.isOnGround(), new Vec3(this.player.getX() - $$7, this.player.getY() - $$8, this.player.getZ() - $$9));
/*      */ 
/*      */     
/*  998 */     if ($$18) {
/*  999 */       this.player.resetFallDistance();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1004 */     this.player.checkMovementStatistics(this.player.getX() - $$7, this.player.getY() - $$8, this.player.getZ() - $$9);
/*      */     
/* 1006 */     this.lastGoodX = this.player.getX();
/* 1007 */     this.lastGoodY = this.player.getY();
/* 1008 */     this.lastGoodZ = this.player.getZ();
/*      */   }
/*      */   
/*      */   private boolean isPlayerCollidingWithAnythingNew(LevelReader $$0, AABB $$1, double $$2, double $$3, double $$4) {
/* 1012 */     AABB $$5 = this.player.getBoundingBox().move($$2 - this.player.getX(), $$3 - this.player.getY(), $$4 - this.player.getZ());
/* 1013 */     Iterable<VoxelShape> $$6 = $$0.getCollisions((Entity)this.player, $$5.deflate(9.999999747378752E-6D));
/* 1014 */     VoxelShape $$7 = Shapes.create($$1.deflate(9.999999747378752E-6D));
/*      */     
/* 1016 */     for (VoxelShape $$8 : $$6) {
/* 1017 */       if (!Shapes.joinIsNotEmpty($$8, $$7, BooleanOp.AND)) {
/* 1018 */         return true;
/*      */       }
/*      */     } 
/* 1021 */     return false;
/*      */   }
/*      */   
/*      */   public void teleport(double $$0, double $$1, double $$2, float $$3, float $$4) {
/* 1025 */     teleport($$0, $$1, $$2, $$3, $$4, Collections.emptySet());
/*      */   }
/*      */   
/*      */   public void teleport(double $$0, double $$1, double $$2, float $$3, float $$4, Set<RelativeMovement> $$5) {
/* 1029 */     double $$6 = $$5.contains(RelativeMovement.X) ? this.player.getX() : 0.0D;
/* 1030 */     double $$7 = $$5.contains(RelativeMovement.Y) ? this.player.getY() : 0.0D;
/* 1031 */     double $$8 = $$5.contains(RelativeMovement.Z) ? this.player.getZ() : 0.0D;
/* 1032 */     float $$9 = $$5.contains(RelativeMovement.Y_ROT) ? this.player.getYRot() : 0.0F;
/* 1033 */     float $$10 = $$5.contains(RelativeMovement.X_ROT) ? this.player.getXRot() : 0.0F;
/* 1034 */     this.awaitingPositionFromClient = new Vec3($$0, $$1, $$2);
/*      */     
/* 1036 */     if (++this.awaitingTeleport == Integer.MAX_VALUE) {
/* 1037 */       this.awaitingTeleport = 0;
/*      */     }
/* 1039 */     this.awaitingTeleportTime = this.tickCount;
/* 1040 */     this.player.absMoveTo($$0, $$1, $$2, $$3, $$4);
/* 1041 */     this.player.connection.send((Packet<?>)new ClientboundPlayerPositionPacket($$0 - $$6, $$1 - $$7, $$2 - $$8, $$3 - $$9, $$4 - $$10, $$5, this.awaitingTeleport));
/*      */   }
/*      */ 
/*      */   
/*      */   public void handlePlayerAction(ServerboundPlayerActionPacket $$0) {
/* 1046 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, this.player.serverLevel());
/* 1047 */     BlockPos $$1 = $$0.getPos();
/* 1048 */     this.player.resetLastActionTime();
/*      */     
/* 1050 */     ServerboundPlayerActionPacket.Action $$2 = $$0.getAction();
/*      */     
/* 1052 */     switch ($$2) {
/*      */       case PERFORM_RESPAWN:
/* 1054 */         if (!this.player.isSpectator()) {
/* 1055 */           ItemStack $$3 = this.player.getItemInHand(InteractionHand.OFF_HAND);
/* 1056 */           this.player.setItemInHand(InteractionHand.OFF_HAND, this.player.getItemInHand(InteractionHand.MAIN_HAND));
/* 1057 */           this.player.setItemInHand(InteractionHand.MAIN_HAND, $$3);
/* 1058 */           this.player.stopUsingItem();
/*      */         } 
/*      */         return;
/*      */       case REQUEST_STATS:
/* 1062 */         if (!this.player.isSpectator()) {
/* 1063 */           this.player.drop(false);
/*      */         }
/*      */         return;
/*      */       case null:
/* 1067 */         if (!this.player.isSpectator()) {
/* 1068 */           this.player.drop(true);
/*      */         }
/*      */         return;
/*      */       case null:
/* 1072 */         this.player.releaseUsingItem();
/*      */         return;
/*      */       case null:
/*      */       case null:
/*      */       case null:
/* 1077 */         this.player.gameMode.handleBlockBreakAction($$1, $$2, $$0.getDirection(), this.player.level().getMaxBuildHeight(), $$0.getSequence());
/* 1078 */         this.player.connection.ackBlockChangesUpTo($$0.getSequence());
/*      */         return;
/*      */     } 
/* 1081 */     throw new IllegalArgumentException("Invalid player action");
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean wasBlockPlacementAttempt(ServerPlayer $$0, ItemStack $$1) {
/* 1086 */     if ($$1.isEmpty()) {
/* 1087 */       return false;
/*      */     }
/*      */     
/* 1090 */     Item $$2 = $$1.getItem();
/* 1091 */     return (($$2 instanceof BlockItem || $$2 instanceof net.minecraft.world.item.BucketItem) && !$$0.getCooldowns().isOnCooldown($$2));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleUseItemOn(ServerboundUseItemOnPacket $$0) {
/* 1101 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, this.player.serverLevel());
/* 1102 */     this.player.connection.ackBlockChangesUpTo($$0.getSequence());
/*      */     
/* 1104 */     ServerLevel $$1 = this.player.serverLevel();
/* 1105 */     InteractionHand $$2 = $$0.getHand();
/* 1106 */     ItemStack $$3 = this.player.getItemInHand($$2);
/*      */     
/* 1108 */     if (!$$3.isItemEnabled($$1.enabledFeatures())) {
/*      */       return;
/*      */     }
/*      */     
/* 1112 */     BlockHitResult $$4 = $$0.getHitResult();
/* 1113 */     Vec3 $$5 = $$4.getLocation();
/* 1114 */     BlockPos $$6 = $$4.getBlockPos();
/* 1115 */     Vec3 $$7 = Vec3.atCenterOf((Vec3i)$$6);
/* 1116 */     if (this.player.getEyePosition().distanceToSqr($$7) > MAX_INTERACTION_DISTANCE) {
/*      */       return;
/*      */     }
/* 1119 */     Vec3 $$8 = $$5.subtract($$7);
/* 1120 */     double $$9 = 1.0000001D;
/*      */     
/* 1122 */     if (Math.abs($$8.x()) >= 1.0000001D || Math.abs($$8.y()) >= 1.0000001D || Math.abs($$8.z()) >= 1.0000001D) {
/* 1123 */       LOGGER.warn("Rejecting UseItemOnPacket from {}: Location {} too far away from hit block {}.", new Object[] { this.player.getGameProfile().getName(), $$5, $$6 });
/*      */       
/*      */       return;
/*      */     } 
/* 1127 */     Direction $$10 = $$4.getDirection();
/* 1128 */     this.player.resetLastActionTime();
/*      */     
/* 1130 */     int $$11 = this.player.level().getMaxBuildHeight();
/* 1131 */     if ($$6.getY() < $$11) {
/* 1132 */       if (this.awaitingPositionFromClient == null && this.player.distanceToSqr($$6.getX() + 0.5D, $$6.getY() + 0.5D, $$6.getZ() + 0.5D) < 64.0D && 
/* 1133 */         $$1.mayInteract((Player)this.player, $$6)) {
/* 1134 */         InteractionResult $$12 = this.player.gameMode.useItemOn(this.player, (Level)$$1, $$3, $$2, $$4);
/*      */         
/* 1136 */         if ($$10 == Direction.UP && !$$12.consumesAction() && $$6.getY() >= $$11 - 1 && wasBlockPlacementAttempt(this.player, $$3)) {
/*      */           
/* 1138 */           MutableComponent mutableComponent = Component.translatable("build.tooHigh", new Object[] { Integer.valueOf($$11 - 1) }).withStyle(ChatFormatting.RED);
/* 1139 */           this.player.sendSystemMessage((Component)mutableComponent, true);
/* 1140 */         } else if ($$12.shouldSwing()) {
/* 1141 */           this.player.swing($$2, true);
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/* 1146 */       MutableComponent mutableComponent = Component.translatable("build.tooHigh", new Object[] { Integer.valueOf($$11 - 1) }).withStyle(ChatFormatting.RED);
/* 1147 */       this.player.sendSystemMessage((Component)mutableComponent, true);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1153 */     this.player.connection.send((Packet<?>)new ClientboundBlockUpdatePacket((BlockGetter)$$1, $$6));
/* 1154 */     this.player.connection.send((Packet<?>)new ClientboundBlockUpdatePacket((BlockGetter)$$1, $$6.relative($$10)));
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleUseItem(ServerboundUseItemPacket $$0) {
/* 1159 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, this.player.serverLevel());
/* 1160 */     ackBlockChangesUpTo($$0.getSequence());
/*      */     
/* 1162 */     ServerLevel $$1 = this.player.serverLevel();
/* 1163 */     InteractionHand $$2 = $$0.getHand();
/* 1164 */     ItemStack $$3 = this.player.getItemInHand($$2);
/* 1165 */     this.player.resetLastActionTime();
/*      */     
/* 1167 */     if ($$3.isEmpty() || !$$3.isItemEnabled($$1.enabledFeatures())) {
/*      */       return;
/*      */     }
/*      */     
/* 1171 */     InteractionResult $$4 = this.player.gameMode.useItem(this.player, (Level)$$1, $$3, $$2);
/* 1172 */     if ($$4.shouldSwing()) {
/* 1173 */       this.player.swing($$2, true);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleTeleportToEntityPacket(ServerboundTeleportToEntityPacket $$0) {
/* 1179 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, this.player.serverLevel());
/* 1180 */     if (this.player.isSpectator()) {
/* 1181 */       for (ServerLevel $$1 : this.server.getAllLevels()) {
/* 1182 */         Entity $$2 = $$0.getEntity($$1);
/*      */         
/* 1184 */         if ($$2 != null) {
/* 1185 */           this.player.teleportTo($$1, $$2.getX(), $$2.getY(), $$2.getZ(), $$2.getYRot(), $$2.getXRot());
/*      */           return;
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void handlePaddleBoat(ServerboundPaddleBoatPacket $$0) {
/* 1194 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, this.player.serverLevel());
/* 1195 */     Entity $$1 = this.player.getControlledVehicle();
/* 1196 */     if ($$1 instanceof Boat) { Boat $$2 = (Boat)$$1;
/* 1197 */       $$2.setPaddleState($$0.getLeft(), $$0.getRight()); }
/*      */   
/*      */   }
/*      */ 
/*      */   
/*      */   public void onDisconnect(Component $$0) {
/* 1203 */     LOGGER.info("{} lost connection: {}", this.player.getName().getString(), $$0.getString());
/* 1204 */     removePlayerFromWorld();
/*      */     
/* 1206 */     super.onDisconnect($$0);
/*      */   }
/*      */   
/*      */   private void removePlayerFromWorld() {
/* 1210 */     this.chatMessageChain.close();
/*      */     
/* 1212 */     this.server.invalidateStatus();
/* 1213 */     this.server.getPlayerList().broadcastSystemMessage((Component)Component.translatable("multiplayer.player.left", new Object[] { this.player.getDisplayName() }).withStyle(ChatFormatting.YELLOW), false);
/* 1214 */     this.player.disconnect();
/* 1215 */     this.server.getPlayerList().remove(this.player);
/*      */     
/* 1217 */     this.player.getTextFilter().leave();
/*      */   }
/*      */   
/*      */   public void ackBlockChangesUpTo(int $$0) {
/* 1221 */     if ($$0 < 0) {
/* 1222 */       throw new IllegalArgumentException("Expected packet sequence nr >= 0");
/*      */     }
/*      */     
/* 1225 */     this.ackBlockChangesUpTo = Math.max($$0, this.ackBlockChangesUpTo);
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleSetCarriedItem(ServerboundSetCarriedItemPacket $$0) {
/* 1230 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, this.player.serverLevel());
/* 1231 */     if ($$0.getSlot() < 0 || $$0.getSlot() >= Inventory.getSelectionSize()) {
/* 1232 */       LOGGER.warn("{} tried to set an invalid carried item", this.player.getName().getString());
/*      */       return;
/*      */     } 
/* 1235 */     if ((this.player.getInventory()).selected != $$0.getSlot() && this.player.getUsedItemHand() == InteractionHand.MAIN_HAND) {
/* 1236 */       this.player.stopUsingItem();
/*      */     }
/* 1238 */     (this.player.getInventory()).selected = $$0.getSlot();
/* 1239 */     this.player.resetLastActionTime();
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleChat(ServerboundChatPacket $$0) {
/* 1244 */     if (isChatMessageIllegal($$0.message())) {
/* 1245 */       disconnect((Component)Component.translatable("multiplayer.disconnect.illegal_characters"));
/*      */       
/*      */       return;
/*      */     } 
/* 1249 */     Optional<LastSeenMessages> $$1 = tryHandleChat($$0.lastSeenMessages());
/* 1250 */     if ($$1.isPresent()) {
/* 1251 */       this.server.submit(() -> {
/*      */             PlayerChatMessage $$2;
/*      */             try {
/*      */               $$2 = getSignedMessage($$0, $$1.get());
/* 1255 */             } catch (net.minecraft.network.chat.SignedMessageChain.DecodeException $$3) {
/*      */               handleMessageDecodeFailure($$3);
/*      */               return;
/*      */             } 
/*      */             CompletableFuture<FilteredText> $$5 = filterTextPacket($$2.signedContent());
/*      */             Component $$6 = this.server.getChatDecorator().decorate(this.player, $$2.decoratedContent());
/*      */             this.chatMessageChain.append($$5, ());
/*      */           });
/*      */     }
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
/*      */   public void handleChatCommand(ServerboundChatCommandPacket $$0) {
/* 1275 */     if (isChatMessageIllegal($$0.command())) {
/* 1276 */       disconnect((Component)Component.translatable("multiplayer.disconnect.illegal_characters"));
/*      */       
/*      */       return;
/*      */     } 
/* 1280 */     Optional<LastSeenMessages> $$1 = tryHandleChat($$0.lastSeenMessages());
/* 1281 */     if ($$1.isPresent())
/*      */     {
/*      */       
/* 1284 */       this.server.submit(() -> {
/*      */             performChatCommand($$0, $$1.get());
/*      */             detectRateSpam();
/*      */           }); } 
/*      */   }
/*      */   
/*      */   private void performChatCommand(ServerboundChatCommandPacket $$0, LastSeenMessages $$1) {
/*      */     Map<String, PlayerChatMessage> $$3;
/* 1292 */     ParseResults<CommandSourceStack> $$2 = parseCommand($$0.command());
/*      */ 
/*      */     
/*      */     try {
/* 1296 */       $$3 = collectSignedArguments($$0, SignableCommand.of($$2), $$1);
/* 1297 */     } catch (net.minecraft.network.chat.SignedMessageChain.DecodeException $$4) {
/* 1298 */       handleMessageDecodeFailure($$4);
/*      */       
/*      */       return;
/*      */     } 
/* 1302 */     CommandSigningContext.SignedArguments signedArguments = new CommandSigningContext.SignedArguments($$3);
/* 1303 */     $$2 = Commands.mapSource($$2, $$1 -> $$1.withSigningContext($$0, (TaskChainer)this.chatMessageChain));
/*      */     
/* 1305 */     this.server.getCommands().performCommand($$2, $$0.command());
/*      */   }
/*      */   
/*      */   private void handleMessageDecodeFailure(SignedMessageChain.DecodeException $$0) {
/* 1309 */     LOGGER.warn("Failed to update secure chat state for {}: '{}'", this.player.getGameProfile().getName(), $$0.getComponent().getString());
/* 1310 */     if ($$0.shouldDisconnect()) {
/* 1311 */       disconnect($$0.getComponent());
/*      */     } else {
/* 1313 */       this.player.sendSystemMessage((Component)$$0.getComponent().copy().withStyle(ChatFormatting.RED));
/*      */     } 
/*      */   }
/*      */   
/*      */   private Map<String, PlayerChatMessage> collectSignedArguments(ServerboundChatCommandPacket $$0, SignableCommand<?> $$1, LastSeenMessages $$2) throws SignedMessageChain.DecodeException {
/* 1318 */     Object2ObjectOpenHashMap<String, PlayerChatMessage> object2ObjectOpenHashMap = new Object2ObjectOpenHashMap();
/* 1319 */     for (SignableCommand.Argument<?> $$4 : (Iterable<SignableCommand.Argument<?>>)$$1.arguments()) {
/* 1320 */       MessageSignature $$5 = $$0.argumentSignatures().get($$4.name());
/*      */       
/* 1322 */       SignedMessageBody $$6 = new SignedMessageBody($$4.value(), $$0.timeStamp(), $$0.salt(), $$2);
/* 1323 */       object2ObjectOpenHashMap.put($$4.name(), this.signedMessageDecoder.unpack($$5, $$6));
/*      */     } 
/*      */     
/* 1326 */     return (Map<String, PlayerChatMessage>)object2ObjectOpenHashMap;
/*      */   }
/*      */   
/*      */   private ParseResults<CommandSourceStack> parseCommand(String $$0) {
/* 1330 */     CommandDispatcher<CommandSourceStack> $$1 = this.server.getCommands().getDispatcher();
/* 1331 */     return $$1.parse($$0, this.player.createCommandSourceStack());
/*      */   }
/*      */   
/*      */   private Optional<LastSeenMessages> tryHandleChat(LastSeenMessages.Update $$0) {
/* 1335 */     Optional<LastSeenMessages> $$1 = unpackAndApplyLastSeen($$0);
/*      */     
/* 1337 */     if (this.player.getChatVisibility() == ChatVisiblity.HIDDEN) {
/*      */ 
/*      */       
/* 1340 */       send((Packet<?>)new ClientboundSystemChatPacket((Component)Component.translatable("chat.disabled.options").withStyle(ChatFormatting.RED), false));
/* 1341 */       return Optional.empty();
/*      */     } 
/*      */     
/* 1344 */     this.player.resetLastActionTime();
/*      */     
/* 1346 */     return $$1;
/*      */   }
/*      */ 
/*      */   
/*      */   private Optional<LastSeenMessages> unpackAndApplyLastSeen(LastSeenMessages.Update $$0) {
/* 1351 */     synchronized (this.lastSeenMessages) {
/* 1352 */       Optional<LastSeenMessages> $$1 = this.lastSeenMessages.applyUpdate($$0);
/* 1353 */       if ($$1.isEmpty()) {
/* 1354 */         LOGGER.warn("Failed to validate message acknowledgements from {}", this.player.getName().getString());
/* 1355 */         disconnect(CHAT_VALIDATION_FAILED);
/*      */       } 
/* 1357 */       return $$1;
/*      */     } 
/*      */   }
/*      */   
/*      */   private static boolean isChatMessageIllegal(String $$0) {
/* 1362 */     for (int $$1 = 0; $$1 < $$0.length(); $$1++) {
/* 1363 */       if (!SharedConstants.isAllowedChatCharacter($$0.charAt($$1))) {
/* 1364 */         return true;
/*      */       }
/*      */     } 
/* 1367 */     return false;
/*      */   }
/*      */   
/*      */   private PlayerChatMessage getSignedMessage(ServerboundChatPacket $$0, LastSeenMessages $$1) throws SignedMessageChain.DecodeException {
/* 1371 */     SignedMessageBody $$2 = new SignedMessageBody($$0.message(), $$0.timeStamp(), $$0.salt(), $$1);
/* 1372 */     return this.signedMessageDecoder.unpack($$0.signature(), $$2);
/*      */   }
/*      */   
/*      */   private void broadcastChatMessage(PlayerChatMessage $$0) {
/* 1376 */     this.server.getPlayerList().broadcastChatMessage($$0, this.player, ChatType.bind(ChatType.CHAT, (Entity)this.player));
/* 1377 */     detectRateSpam();
/*      */   }
/*      */   
/*      */   private void detectRateSpam() {
/* 1381 */     this.chatSpamTickCount += 20;
/* 1382 */     if (this.chatSpamTickCount > 200 && !this.server.getPlayerList().isOp(this.player.getGameProfile())) {
/* 1383 */       disconnect((Component)Component.translatable("disconnect.spam"));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleChatAck(ServerboundChatAckPacket $$0) {
/* 1390 */     synchronized (this.lastSeenMessages) {
/* 1391 */       if (!this.lastSeenMessages.applyOffset($$0.offset())) {
/* 1392 */         LOGGER.warn("Failed to validate message acknowledgements from {}", this.player.getName().getString());
/* 1393 */         disconnect(CHAT_VALIDATION_FAILED);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleAnimate(ServerboundSwingPacket $$0) {
/* 1400 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, this.player.serverLevel());
/* 1401 */     this.player.resetLastActionTime();
/* 1402 */     this.player.swing($$0.getHand());
/*      */   }
/*      */   
/*      */   public void handlePlayerCommand(ServerboundPlayerCommandPacket $$0) {
/*      */     Entity entity;
/* 1407 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, this.player.serverLevel());
/* 1408 */     this.player.resetLastActionTime();
/* 1409 */     switch ($$0.getAction()) {
/*      */       case PERFORM_RESPAWN:
/* 1411 */         this.player.setShiftKeyDown(true);
/*      */         return;
/*      */       case REQUEST_STATS:
/* 1414 */         this.player.setShiftKeyDown(false);
/*      */         return;
/*      */       case null:
/* 1417 */         this.player.setSprinting(true);
/*      */         return;
/*      */       case null:
/* 1420 */         this.player.setSprinting(false);
/*      */         return;
/*      */       case null:
/* 1423 */         if (this.player.isSleeping()) {
/* 1424 */           this.player.stopSleepInBed(false, true);
/* 1425 */           this.awaitingPositionFromClient = this.player.position();
/*      */         } 
/*      */         return;
/*      */       case null:
/* 1429 */         entity = this.player.getControlledVehicle(); if (entity instanceof PlayerRideableJumping) { PlayerRideableJumping $$1 = (PlayerRideableJumping)entity;
/* 1430 */           int $$2 = $$0.getData();
/* 1431 */           if ($$1.canJump() && $$2 > 0) {
/* 1432 */             $$1.handleStartJump($$2);
/*      */           } }
/*      */         
/*      */         return;
/*      */       case null:
/* 1437 */         entity = this.player.getControlledVehicle(); if (entity instanceof PlayerRideableJumping) { PlayerRideableJumping $$3 = (PlayerRideableJumping)entity;
/* 1438 */           $$3.handleStopJump(); }
/*      */         
/*      */         return;
/*      */       case null:
/* 1442 */         entity = this.player.getVehicle(); if (entity instanceof HasCustomInventoryScreen) { HasCustomInventoryScreen $$4 = (HasCustomInventoryScreen)entity;
/* 1443 */           $$4.openCustomInventoryScreen((Player)this.player); }
/*      */         
/*      */         return;
/*      */       case null:
/* 1447 */         if (!this.player.tryToStartFallFlying())
/*      */         {
/* 1449 */           this.player.stopFallFlying();
/*      */         }
/*      */         return;
/*      */     } 
/* 1453 */     throw new IllegalArgumentException("Invalid client command!");
/*      */   }
/*      */   
/*      */   public void addPendingMessage(PlayerChatMessage $$0) {
/*      */     int $$2;
/* 1458 */     MessageSignature $$1 = $$0.signature();
/* 1459 */     if ($$1 == null) {
/*      */       return;
/*      */     }
/*      */     
/* 1463 */     this.messageSignatureCache.push($$0.signedBody(), $$0.signature());
/*      */ 
/*      */     
/* 1466 */     synchronized (this.lastSeenMessages) {
/* 1467 */       this.lastSeenMessages.addPending($$1);
/* 1468 */       $$2 = this.lastSeenMessages.trackedMessagesCount();
/*      */     } 
/* 1470 */     if ($$2 > 4096) {
/* 1471 */       disconnect((Component)Component.translatable("multiplayer.disconnect.too_many_pending_chats"));
/*      */     }
/*      */   }
/*      */   
/*      */   public void sendPlayerChatMessage(PlayerChatMessage $$0, ChatType.Bound $$1) {
/* 1476 */     send((Packet<?>)new ClientboundPlayerChatPacket($$0
/* 1477 */           .link().sender(), $$0
/* 1478 */           .link().index(), $$0
/* 1479 */           .signature(), $$0
/* 1480 */           .signedBody().pack(this.messageSignatureCache), $$0
/* 1481 */           .unsignedContent(), $$0
/* 1482 */           .filterMask(), $$1
/* 1483 */           .toNetwork(this.player.level().registryAccess())));
/*      */ 
/*      */     
/* 1486 */     addPendingMessage($$0);
/*      */   }
/*      */   
/*      */   public void sendDisguisedChatMessage(Component $$0, ChatType.Bound $$1) {
/* 1490 */     send((Packet<?>)new ClientboundDisguisedChatPacket($$0, $$1.toNetwork(this.player.level().registryAccess())));
/*      */   }
/*      */   
/*      */   public SocketAddress getRemoteAddress() {
/* 1494 */     return this.connection.getRemoteAddress();
/*      */   }
/*      */   
/*      */   public void switchToConfig() {
/* 1498 */     this.waitingForSwitchToConfig = true;
/* 1499 */     removePlayerFromWorld();
/* 1500 */     send((Packet<?>)new ClientboundStartConfigurationPacket());
/*      */   }
/*      */ 
/*      */   
/*      */   public void handlePingRequest(ServerboundPingRequestPacket $$0) {
/* 1505 */     this.connection.send((Packet)new ClientboundPongResponsePacket($$0.getTime()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleInteract(ServerboundInteractPacket $$0) {
/* 1515 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, this.player.serverLevel());
/* 1516 */     final ServerLevel level = this.player.serverLevel();
/* 1517 */     final Entity target = $$0.getTarget($$1);
/* 1518 */     this.player.resetLastActionTime();
/*      */     
/* 1520 */     this.player.setShiftKeyDown($$0.isUsingSecondaryAction());
/*      */     
/* 1522 */     if ($$2 != null) {
/* 1523 */       if (!$$1.getWorldBorder().isWithinBounds($$2.blockPosition())) {
/*      */         return;
/*      */       }
/*      */       
/* 1527 */       AABB $$3 = $$2.getBoundingBox();
/* 1528 */       if ($$3.distanceToSqr(this.player.getEyePosition()) < MAX_INTERACTION_DISTANCE)
/* 1529 */         $$0.dispatch(new ServerboundInteractPacket.Handler() {
/*      */               private void performInteraction(InteractionHand $$0, ServerGamePacketListenerImpl.EntityInteraction $$1) {
/* 1531 */                 ItemStack $$2 = ServerGamePacketListenerImpl.this.player.getItemInHand($$0);
/* 1532 */                 if (!$$2.isItemEnabled(level.enabledFeatures())) {
/*      */                   return;
/*      */                 }
/* 1535 */                 ItemStack $$3 = $$2.copy();
/* 1536 */                 InteractionResult $$4 = $$1.run(ServerGamePacketListenerImpl.this.player, target, $$0);
/* 1537 */                 if ($$4.consumesAction()) {
/* 1538 */                   CriteriaTriggers.PLAYER_INTERACTED_WITH_ENTITY.trigger(ServerGamePacketListenerImpl.this.player, $$3, target);
/* 1539 */                   if ($$4.shouldSwing()) {
/* 1540 */                     ServerGamePacketListenerImpl.this.player.swing($$0, true);
/*      */                   }
/*      */                 } 
/*      */               }
/*      */ 
/*      */               
/*      */               public void onInteraction(InteractionHand $$0) {
/* 1547 */                 performInteraction($$0, Player::interactOn);
/*      */               }
/*      */ 
/*      */               
/*      */               public void onInteraction(InteractionHand $$0, Vec3 $$1) {
/* 1552 */                 performInteraction($$0, ($$1, $$2, $$3) -> $$2.interactAt((Player)$$1, $$0, $$3));
/*      */               }
/*      */ 
/*      */               
/*      */               public void onAttack() {
/* 1557 */                 if (target instanceof net.minecraft.world.entity.item.ItemEntity || target instanceof net.minecraft.world.entity.ExperienceOrb || target instanceof net.minecraft.world.entity.projectile.AbstractArrow || target == ServerGamePacketListenerImpl.this.player) {
/* 1558 */                   ServerGamePacketListenerImpl.this.disconnect((Component)Component.translatable("multiplayer.disconnect.invalid_entity_attacked"));
/* 1559 */                   ServerGamePacketListenerImpl.LOGGER.warn("Player {} tried to attack an invalid entity", ServerGamePacketListenerImpl.this.player.getName().getString());
/*      */                   
/*      */                   return;
/*      */                 } 
/* 1563 */                 ItemStack $$0 = ServerGamePacketListenerImpl.this.player.getItemInHand(InteractionHand.MAIN_HAND);
/* 1564 */                 if (!$$0.isItemEnabled(level.enabledFeatures())) {
/*      */                   return;
/*      */                 }
/*      */                 
/* 1568 */                 ServerGamePacketListenerImpl.this.player.attack(target);
/*      */               }
/*      */             }); 
/*      */     } 
/*      */   }
/*      */   @FunctionalInterface
/*      */   private static interface EntityInteraction {
/*      */     InteractionResult run(ServerPlayer param1ServerPlayer, Entity param1Entity, InteractionHand param1InteractionHand); }
/*      */   public void handleClientCommand(ServerboundClientCommandPacket $$0) {
/* 1577 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, this.player.serverLevel());
/* 1578 */     this.player.resetLastActionTime();
/* 1579 */     ServerboundClientCommandPacket.Action $$1 = $$0.getAction();
/* 1580 */     switch ($$1) {
/*      */       case PERFORM_RESPAWN:
/* 1582 */         if (this.player.wonGame) {
/* 1583 */           this.player.wonGame = false;
/* 1584 */           this.player = this.server.getPlayerList().respawn(this.player, true);
/* 1585 */           CriteriaTriggers.CHANGED_DIMENSION.trigger(this.player, Level.END, Level.OVERWORLD); break;
/*      */         } 
/* 1587 */         if (this.player.getHealth() > 0.0F) {
/*      */           return;
/*      */         }
/* 1590 */         this.player = this.server.getPlayerList().respawn(this.player, false);
/* 1591 */         if (this.server.isHardcore()) {
/* 1592 */           this.player.setGameMode(GameType.SPECTATOR);
/* 1593 */           ((GameRules.BooleanValue)this.player.level().getGameRules().getRule(GameRules.RULE_SPECTATORSGENERATECHUNKS)).set(false, this.server);
/*      */         } 
/*      */         break;
/*      */       
/*      */       case REQUEST_STATS:
/* 1598 */         this.player.getStats().sendStats(this.player);
/*      */         break;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleContainerClose(ServerboundContainerClosePacket $$0) {
/* 1605 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, this.player.serverLevel());
/* 1606 */     this.player.doCloseContainer();
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleContainerClick(ServerboundContainerClickPacket $$0) {
/* 1611 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, this.player.serverLevel());
/* 1612 */     this.player.resetLastActionTime();
/* 1613 */     if (this.player.containerMenu.containerId != $$0.getContainerId()) {
/*      */       return;
/*      */     }
/*      */     
/* 1617 */     if (this.player.isSpectator()) {
/*      */       
/* 1619 */       this.player.containerMenu.sendAllDataToRemote();
/*      */       
/*      */       return;
/*      */     } 
/* 1623 */     if (!this.player.containerMenu.stillValid((Player)this.player)) {
/* 1624 */       LOGGER.debug("Player {} interacted with invalid menu {}", this.player, this.player.containerMenu);
/*      */       
/*      */       return;
/*      */     } 
/* 1628 */     int $$1 = $$0.getSlotNum();
/* 1629 */     if (!this.player.containerMenu.isValidSlotIndex($$1)) {
/*      */       
/* 1631 */       LOGGER.debug("Player {} clicked invalid slot index: {}, available slots: {}", new Object[] { this.player.getName(), Integer.valueOf($$1), Integer.valueOf(this.player.containerMenu.slots.size()) });
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 1636 */     boolean $$2 = ($$0.getStateId() != this.player.containerMenu.getStateId());
/*      */ 
/*      */     
/* 1639 */     this.player.containerMenu.suppressRemoteUpdates();
/* 1640 */     this.player.containerMenu.clicked($$1, $$0.getButtonNum(), $$0.getClickType(), (Player)this.player);
/*      */ 
/*      */     
/* 1643 */     for (ObjectIterator<Int2ObjectMap.Entry<ItemStack>> objectIterator = Int2ObjectMaps.fastIterable($$0.getChangedSlots()).iterator(); objectIterator.hasNext(); ) { Int2ObjectMap.Entry<ItemStack> $$3 = objectIterator.next();
/* 1644 */       this.player.containerMenu.setRemoteSlotNoCopy($$3.getIntKey(), (ItemStack)$$3.getValue()); }
/*      */     
/* 1646 */     this.player.containerMenu.setRemoteCarried($$0.getCarriedItem());
/*      */ 
/*      */     
/* 1649 */     this.player.containerMenu.resumeRemoteUpdates();
/* 1650 */     if ($$2) {
/* 1651 */       this.player.containerMenu.broadcastFullState();
/*      */     } else {
/* 1653 */       this.player.containerMenu.broadcastChanges();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void handlePlaceRecipe(ServerboundPlaceRecipePacket $$0) {
/* 1659 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, this.player.serverLevel());
/* 1660 */     this.player.resetLastActionTime();
/* 1661 */     if (this.player.isSpectator() || this.player.containerMenu.containerId != $$0.getContainerId() || !(this.player.containerMenu instanceof RecipeBookMenu)) {
/*      */       return;
/*      */     }
/*      */     
/* 1665 */     if (!this.player.containerMenu.stillValid((Player)this.player)) {
/* 1666 */       LOGGER.debug("Player {} interacted with invalid menu {}", this.player, this.player.containerMenu);
/*      */       
/*      */       return;
/*      */     } 
/* 1670 */     this.server.getRecipeManager().byKey($$0.getRecipe()).ifPresent($$1 -> ((RecipeBookMenu)this.player.containerMenu).handlePlacement($$0.isShiftDown(), $$1, this.player));
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleContainerButtonClick(ServerboundContainerButtonClickPacket $$0) {
/* 1675 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, this.player.serverLevel());
/* 1676 */     this.player.resetLastActionTime();
/* 1677 */     if (this.player.containerMenu.containerId != $$0.getContainerId() || this.player.isSpectator()) {
/*      */       return;
/*      */     }
/*      */     
/* 1681 */     if (!this.player.containerMenu.stillValid((Player)this.player)) {
/* 1682 */       LOGGER.debug("Player {} interacted with invalid menu {}", this.player, this.player.containerMenu);
/*      */       
/*      */       return;
/*      */     } 
/* 1686 */     boolean $$1 = this.player.containerMenu.clickMenuButton((Player)this.player, $$0.getButtonId());
/* 1687 */     if ($$1) {
/* 1688 */       this.player.containerMenu.broadcastChanges();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleSetCreativeModeSlot(ServerboundSetCreativeModeSlotPacket $$0) {
/* 1694 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, this.player.serverLevel());
/* 1695 */     if (this.player.gameMode.isCreative()) {
/* 1696 */       boolean $$1 = ($$0.getSlotNum() < 0);
/* 1697 */       ItemStack $$2 = $$0.getItem();
/*      */       
/* 1699 */       if (!$$2.isItemEnabled(this.player.level().enabledFeatures())) {
/*      */         return;
/*      */       }
/*      */       
/* 1703 */       CompoundTag $$3 = BlockItem.getBlockEntityData($$2);
/* 1704 */       if (!$$2.isEmpty() && $$3 != null && 
/* 1705 */         $$3.contains("x") && $$3.contains("y") && $$3.contains("z")) {
/* 1706 */         BlockPos $$4 = BlockEntity.getPosFromTag($$3);
/* 1707 */         if (this.player.level().isLoaded($$4)) {
/* 1708 */           BlockEntity $$5 = this.player.level().getBlockEntity($$4);
/* 1709 */           if ($$5 != null) {
/* 1710 */             $$5.saveToItem($$2);
/*      */           }
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1716 */       boolean $$6 = ($$0.getSlotNum() >= 1 && $$0.getSlotNum() <= 45);
/* 1717 */       boolean $$7 = ($$2.isEmpty() || ($$2.getDamageValue() >= 0 && $$2.getCount() <= 64 && !$$2.isEmpty()));
/*      */       
/* 1719 */       if ($$6 && $$7) {
/* 1720 */         this.player.inventoryMenu.getSlot($$0.getSlotNum()).setByPlayer($$2);
/* 1721 */         this.player.inventoryMenu.broadcastChanges();
/* 1722 */       } else if ($$1 && $$7 && 
/* 1723 */         this.dropSpamTickCount < 200) {
/* 1724 */         this.dropSpamTickCount += 20;
/*      */         
/* 1726 */         this.player.drop($$2, true);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleSignUpdate(ServerboundSignUpdatePacket $$0) {
/* 1734 */     List<String> $$1 = (List<String>)Stream.<String>of($$0.getLines()).map(ChatFormatting::stripFormatting).collect(Collectors.toList());
/* 1735 */     filterTextPacket($$1).thenAcceptAsync($$1 -> updateSignText($$0, $$1), (Executor)this.server);
/*      */   }
/*      */   
/*      */   private void updateSignText(ServerboundSignUpdatePacket $$0, List<FilteredText> $$1) {
/* 1739 */     this.player.resetLastActionTime();
/* 1740 */     ServerLevel $$2 = this.player.serverLevel();
/* 1741 */     BlockPos $$3 = $$0.getPos();
/* 1742 */     if ($$2.hasChunkAt($$3)) {
/* 1743 */       SignBlockEntity $$5; BlockEntity $$4 = $$2.getBlockEntity($$3);
/*      */       
/* 1745 */       if ($$4 instanceof SignBlockEntity) { $$5 = (SignBlockEntity)$$4; }
/*      */       else
/*      */       { return; }
/*      */       
/* 1749 */       $$5.updateSignText((Player)this.player, $$0.isFrontText(), $$1);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void handlePlayerAbilities(ServerboundPlayerAbilitiesPacket $$0) {
/* 1755 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, this.player.serverLevel());
/* 1756 */     (this.player.getAbilities()).flying = ($$0.isFlying() && (this.player.getAbilities()).mayfly);
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleClientInformation(ServerboundClientInformationPacket $$0) {
/* 1761 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, this.player.serverLevel());
/* 1762 */     this.player.updateOptions($$0.information());
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleChangeDifficulty(ServerboundChangeDifficultyPacket $$0) {
/* 1767 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, this.player.serverLevel());
/*      */     
/* 1769 */     if (!this.player.hasPermissions(2) && !isSingleplayerOwner()) {
/*      */       return;
/*      */     }
/*      */     
/* 1773 */     this.server.setDifficulty($$0.getDifficulty(), false);
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleLockDifficulty(ServerboundLockDifficultyPacket $$0) {
/* 1778 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, this.player.serverLevel());
/*      */     
/* 1780 */     if (!this.player.hasPermissions(2) && !isSingleplayerOwner()) {
/*      */       return;
/*      */     }
/*      */     
/* 1784 */     this.server.setDifficultyLocked($$0.isLocked());
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleChatSessionUpdate(ServerboundChatSessionUpdatePacket $$0) {
/* 1789 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, this.player.serverLevel());
/*      */     
/* 1791 */     RemoteChatSession.Data $$1 = $$0.chatSession();
/* 1792 */     ProfilePublicKey.Data $$2 = (this.chatSession != null) ? this.chatSession.profilePublicKey().data() : null;
/* 1793 */     ProfilePublicKey.Data $$3 = $$1.profilePublicKey();
/* 1794 */     if (Objects.equals($$2, $$3)) {
/*      */       return;
/*      */     }
/*      */     
/* 1798 */     if ($$2 != null && $$3.expiresAt().isBefore($$2.expiresAt())) {
/* 1799 */       disconnect(ProfilePublicKey.EXPIRED_PROFILE_PUBLIC_KEY);
/*      */       
/*      */       return;
/*      */     } 
/*      */     try {
/* 1804 */       SignatureValidator $$4 = this.server.getProfileKeySignatureValidator();
/* 1805 */       if ($$4 == null) {
/* 1806 */         LOGGER.warn("Ignoring chat session from {} due to missing Services public key", this.player.getGameProfile().getName());
/*      */         return;
/*      */       } 
/* 1809 */       resetPlayerChatState($$1.validate(this.player.getGameProfile(), $$4));
/* 1810 */     } catch (net.minecraft.world.entity.player.ProfilePublicKey.ValidationException $$5) {
/* 1811 */       LOGGER.error("Failed to validate profile key: {}", $$5.getMessage());
/* 1812 */       disconnect($$5.getComponent());
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleConfigurationAcknowledged(ServerboundConfigurationAcknowledgedPacket $$0) {
/* 1818 */     if (!this.waitingForSwitchToConfig) {
/* 1819 */       throw new IllegalStateException("Client acknowledged config, but none was requested");
/*      */     }
/*      */     
/* 1822 */     this.connection.setListener((PacketListener)new ServerConfigurationPacketListenerImpl(this.server, this.connection, createCookie(this.player.clientInformation())));
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleChunkBatchReceived(ServerboundChunkBatchReceivedPacket $$0) {
/* 1827 */     PacketUtils.ensureRunningOnSameThread((Packet)$$0, (PacketListener)this, this.player.serverLevel());
/* 1828 */     this.chunkSender.onChunkBatchReceivedByClient($$0.desiredChunksPerTick());
/*      */   }
/*      */   
/*      */   private void resetPlayerChatState(RemoteChatSession $$0) {
/* 1832 */     this.chatSession = $$0;
/* 1833 */     this.signedMessageDecoder = $$0.createMessageDecoder(this.player.getUUID());
/*      */ 
/*      */     
/* 1836 */     this.chatMessageChain.append(() -> {
/*      */           this.player.setChatSession($$0);
/*      */           this.server.getPlayerList().broadcastAll((Packet)new ClientboundPlayerInfoUpdatePacket(EnumSet.of(ClientboundPlayerInfoUpdatePacket.Action.INITIALIZE_CHAT), List.of(this.player)));
/*      */         });
/*      */   }
/*      */ 
/*      */   
/*      */   public ServerPlayer getPlayer() {
/* 1844 */     return this.player;
/*      */   }
/*      */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\network\ServerGamePacketListenerImpl.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */