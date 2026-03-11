/*     */ package net.minecraft.network;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
/*     */ import java.util.EnumMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.network.protocol.BundleDelimiterPacket;
/*     */ import net.minecraft.network.protocol.BundlerInfo;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.network.protocol.PacketFlow;
/*     */ import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
/*     */ import net.minecraft.network.protocol.common.ClientboundDisconnectPacket;
/*     */ import net.minecraft.network.protocol.common.ClientboundKeepAlivePacket;
/*     */ import net.minecraft.network.protocol.common.ClientboundPingPacket;
/*     */ import net.minecraft.network.protocol.common.ClientboundResourcePackPopPacket;
/*     */ import net.minecraft.network.protocol.common.ClientboundResourcePackPushPacket;
/*     */ import net.minecraft.network.protocol.common.ClientboundUpdateTagsPacket;
/*     */ import net.minecraft.network.protocol.common.ServerboundClientInformationPacket;
/*     */ import net.minecraft.network.protocol.common.ServerboundCustomPayloadPacket;
/*     */ import net.minecraft.network.protocol.common.ServerboundKeepAlivePacket;
/*     */ import net.minecraft.network.protocol.common.ServerboundPongPacket;
/*     */ import net.minecraft.network.protocol.common.ServerboundResourcePackPacket;
/*     */ import net.minecraft.network.protocol.configuration.ClientboundFinishConfigurationPacket;
/*     */ import net.minecraft.network.protocol.configuration.ClientboundRegistryDataPacket;
/*     */ import net.minecraft.network.protocol.configuration.ClientboundUpdateEnabledFeaturesPacket;
/*     */ import net.minecraft.network.protocol.configuration.ServerboundFinishConfigurationPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundAddExperienceOrbPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundAwardStatsPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundBlockChangedAckPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundBlockDestructionPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundBlockEventPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundBossEventPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundBundlePacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundChangeDifficultyPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundChunkBatchFinishedPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundChunkBatchStartPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundChunksBiomesPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundClearTitlesPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundCommandSuggestionsPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundCommandsPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundContainerClosePacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundContainerSetContentPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundContainerSetDataPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundCooldownPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundCustomChatCompletionsPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundDamageEventPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundDeleteChatPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundDisguisedChatPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundEntityEventPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundExplodePacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundForgetLevelChunkPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundHorseScreenOpenPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundHurtAnimationPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundInitializeBorderPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundLevelEventPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundLightUpdatePacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundLoginPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundMapItemDataPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundMerchantOffersPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundMoveEntityPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundMoveVehiclePacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundOpenBookPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundOpenScreenPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundOpenSignEditorPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundPlaceGhostRecipePacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundPlayerAbilitiesPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundPlayerChatPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundPlayerCombatEndPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundPlayerCombatEnterPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundPlayerCombatKillPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundPlayerInfoRemovePacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundPlayerLookAtPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundPlayerPositionPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundRecipePacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundRemoveMobEffectPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundResetScorePacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundRespawnPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundRotateHeadPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundSectionBlocksUpdatePacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundSelectAdvancementsTabPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundServerDataPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundSetBorderCenterPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundSetBorderLerpSizePacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundSetBorderSizePacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundSetBorderWarningDelayPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundSetBorderWarningDistancePacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundSetCameraPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundSetCarriedItemPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundSetChunkCacheCenterPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundSetChunkCacheRadiusPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundSetDefaultSpawnPositionPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundSetDisplayObjectivePacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundSetEntityLinkPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundSetEquipmentPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundSetExperiencePacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundSetHealthPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundSetObjectivePacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundSetPassengersPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundSetPlayerTeamPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundSetScorePacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundSetSimulationDistancePacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundSetTimePacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundSetTitlesAnimationPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundSoundEntityPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundSoundPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundStartConfigurationPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundStopSoundPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundSystemChatPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundTabListPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundTagQueryPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundTakeItemEntityPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundTeleportEntityPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundTickingStatePacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundTickingStepPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundUpdateAdvancementsPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundUpdateAttributesPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundUpdateRecipesPacket;
/*     */ import net.minecraft.network.protocol.game.ServerboundAcceptTeleportationPacket;
/*     */ import net.minecraft.network.protocol.game.ServerboundBlockEntityTagQuery;
/*     */ import net.minecraft.network.protocol.game.ServerboundChangeDifficultyPacket;
/*     */ import net.minecraft.network.protocol.game.ServerboundChatAckPacket;
/*     */ import net.minecraft.network.protocol.game.ServerboundChatCommandPacket;
/*     */ import net.minecraft.network.protocol.game.ServerboundChatPacket;
/*     */ import net.minecraft.network.protocol.game.ServerboundChatSessionUpdatePacket;
/*     */ import net.minecraft.network.protocol.game.ServerboundChunkBatchReceivedPacket;
/*     */ import net.minecraft.network.protocol.game.ServerboundClientCommandPacket;
/*     */ import net.minecraft.network.protocol.game.ServerboundCommandSuggestionPacket;
/*     */ import net.minecraft.network.protocol.game.ServerboundConfigurationAcknowledgedPacket;
/*     */ import net.minecraft.network.protocol.game.ServerboundContainerButtonClickPacket;
/*     */ import net.minecraft.network.protocol.game.ServerboundContainerClickPacket;
/*     */ import net.minecraft.network.protocol.game.ServerboundContainerClosePacket;
/*     */ import net.minecraft.network.protocol.game.ServerboundContainerSlotStateChangedPacket;
/*     */ import net.minecraft.network.protocol.game.ServerboundEditBookPacket;
/*     */ import net.minecraft.network.protocol.game.ServerboundEntityTagQuery;
/*     */ import net.minecraft.network.protocol.game.ServerboundInteractPacket;
/*     */ import net.minecraft.network.protocol.game.ServerboundJigsawGeneratePacket;
/*     */ import net.minecraft.network.protocol.game.ServerboundLockDifficultyPacket;
/*     */ import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
/*     */ import net.minecraft.network.protocol.game.ServerboundMoveVehiclePacket;
/*     */ import net.minecraft.network.protocol.game.ServerboundPaddleBoatPacket;
/*     */ import net.minecraft.network.protocol.game.ServerboundPickItemPacket;
/*     */ import net.minecraft.network.protocol.game.ServerboundPlaceRecipePacket;
/*     */ import net.minecraft.network.protocol.game.ServerboundPlayerAbilitiesPacket;
/*     */ import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
/*     */ import net.minecraft.network.protocol.game.ServerboundPlayerCommandPacket;
/*     */ import net.minecraft.network.protocol.game.ServerboundPlayerInputPacket;
/*     */ import net.minecraft.network.protocol.game.ServerboundRecipeBookChangeSettingsPacket;
/*     */ import net.minecraft.network.protocol.game.ServerboundRecipeBookSeenRecipePacket;
/*     */ import net.minecraft.network.protocol.game.ServerboundRenameItemPacket;
/*     */ import net.minecraft.network.protocol.game.ServerboundSeenAdvancementsPacket;
/*     */ import net.minecraft.network.protocol.game.ServerboundSelectTradePacket;
/*     */ import net.minecraft.network.protocol.game.ServerboundSetBeaconPacket;
/*     */ import net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket;
/*     */ import net.minecraft.network.protocol.game.ServerboundSetCommandBlockPacket;
/*     */ import net.minecraft.network.protocol.game.ServerboundSetCommandMinecartPacket;
/*     */ import net.minecraft.network.protocol.game.ServerboundSetCreativeModeSlotPacket;
/*     */ import net.minecraft.network.protocol.game.ServerboundSetJigsawBlockPacket;
/*     */ import net.minecraft.network.protocol.game.ServerboundSetStructureBlockPacket;
/*     */ import net.minecraft.network.protocol.game.ServerboundSignUpdatePacket;
/*     */ import net.minecraft.network.protocol.game.ServerboundSwingPacket;
/*     */ import net.minecraft.network.protocol.game.ServerboundTeleportToEntityPacket;
/*     */ import net.minecraft.network.protocol.game.ServerboundUseItemOnPacket;
/*     */ import net.minecraft.network.protocol.game.ServerboundUseItemPacket;
/*     */ import net.minecraft.network.protocol.handshake.ClientIntentionPacket;
/*     */ import net.minecraft.network.protocol.login.ClientboundCustomQueryPacket;
/*     */ import net.minecraft.network.protocol.login.ClientboundGameProfilePacket;
/*     */ import net.minecraft.network.protocol.login.ClientboundHelloPacket;
/*     */ import net.minecraft.network.protocol.login.ClientboundLoginCompressionPacket;
/*     */ import net.minecraft.network.protocol.login.ClientboundLoginDisconnectPacket;
/*     */ import net.minecraft.network.protocol.login.ServerboundCustomQueryAnswerPacket;
/*     */ import net.minecraft.network.protocol.login.ServerboundHelloPacket;
/*     */ import net.minecraft.network.protocol.login.ServerboundKeyPacket;
/*     */ import net.minecraft.network.protocol.login.ServerboundLoginAcknowledgedPacket;
/*     */ import net.minecraft.network.protocol.status.ClientboundPongResponsePacket;
/*     */ import net.minecraft.network.protocol.status.ClientboundStatusResponsePacket;
/*     */ import net.minecraft.network.protocol.status.ServerboundPingRequestPacket;
/*     */ import net.minecraft.network.protocol.status.ServerboundStatusRequestPacket;
/*     */ import net.minecraft.util.VisibleForDebug;
/*     */ import org.slf4j.Logger;
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
/*     */ public enum ConnectionProtocol
/*     */ {
/* 220 */   HANDSHAKING("handshake", 
/* 221 */     protocol()
/* 222 */     .<PacketListener>addFlow(PacketFlow.CLIENTBOUND, new PacketSet<>())
/* 223 */     .addFlow(PacketFlow.SERVERBOUND, (new PacketSet<>())
/* 224 */       .addPacket(ClientIntentionPacket.class, ClientIntentionPacket::new))),
/*     */ 
/*     */   
/* 227 */   PLAY("play", 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 234 */     protocol()
/* 235 */     .<PacketListener>addFlow(PacketFlow.CLIENTBOUND, (new PacketSet<>())
/* 236 */       .<ClientboundBundlePacket>withBundlePacket(ClientboundBundlePacket.class, ClientboundBundlePacket::new)
/* 237 */       .<ClientboundAddEntityPacket>addPacket(ClientboundAddEntityPacket.class, ClientboundAddEntityPacket::new)
/* 238 */       .<ClientboundAddExperienceOrbPacket>addPacket(ClientboundAddExperienceOrbPacket.class, ClientboundAddExperienceOrbPacket::new)
/* 239 */       .<ClientboundAnimatePacket>addPacket(ClientboundAnimatePacket.class, ClientboundAnimatePacket::new)
/* 240 */       .<ClientboundAwardStatsPacket>addPacket(ClientboundAwardStatsPacket.class, ClientboundAwardStatsPacket::new)
/* 241 */       .<ClientboundBlockChangedAckPacket>addPacket(ClientboundBlockChangedAckPacket.class, ClientboundBlockChangedAckPacket::new)
/* 242 */       .<ClientboundBlockDestructionPacket>addPacket(ClientboundBlockDestructionPacket.class, ClientboundBlockDestructionPacket::new)
/* 243 */       .<ClientboundBlockEntityDataPacket>addPacket(ClientboundBlockEntityDataPacket.class, ClientboundBlockEntityDataPacket::new)
/* 244 */       .<ClientboundBlockEventPacket>addPacket(ClientboundBlockEventPacket.class, ClientboundBlockEventPacket::new)
/* 245 */       .<ClientboundBlockUpdatePacket>addPacket(ClientboundBlockUpdatePacket.class, ClientboundBlockUpdatePacket::new)
/* 246 */       .<ClientboundBossEventPacket>addPacket(ClientboundBossEventPacket.class, ClientboundBossEventPacket::new)
/* 247 */       .<ClientboundChangeDifficultyPacket>addPacket(ClientboundChangeDifficultyPacket.class, ClientboundChangeDifficultyPacket::new)
/* 248 */       .<ClientboundChunkBatchFinishedPacket>addPacket(ClientboundChunkBatchFinishedPacket.class, ClientboundChunkBatchFinishedPacket::new)
/* 249 */       .<ClientboundChunkBatchStartPacket>addPacket(ClientboundChunkBatchStartPacket.class, ClientboundChunkBatchStartPacket::new)
/* 250 */       .<ClientboundChunksBiomesPacket>addPacket(ClientboundChunksBiomesPacket.class, ClientboundChunksBiomesPacket::new)
/* 251 */       .<ClientboundClearTitlesPacket>addPacket(ClientboundClearTitlesPacket.class, ClientboundClearTitlesPacket::new)
/* 252 */       .<ClientboundCommandSuggestionsPacket>addPacket(ClientboundCommandSuggestionsPacket.class, ClientboundCommandSuggestionsPacket::new)
/* 253 */       .<ClientboundCommandsPacket>addPacket(ClientboundCommandsPacket.class, ClientboundCommandsPacket::new)
/* 254 */       .<ClientboundContainerClosePacket>addPacket(ClientboundContainerClosePacket.class, ClientboundContainerClosePacket::new)
/* 255 */       .<ClientboundContainerSetContentPacket>addPacket(ClientboundContainerSetContentPacket.class, ClientboundContainerSetContentPacket::new)
/* 256 */       .<ClientboundContainerSetDataPacket>addPacket(ClientboundContainerSetDataPacket.class, ClientboundContainerSetDataPacket::new)
/* 257 */       .<ClientboundContainerSetSlotPacket>addPacket(ClientboundContainerSetSlotPacket.class, ClientboundContainerSetSlotPacket::new)
/* 258 */       .<ClientboundCooldownPacket>addPacket(ClientboundCooldownPacket.class, ClientboundCooldownPacket::new)
/* 259 */       .<ClientboundCustomChatCompletionsPacket>addPacket(ClientboundCustomChatCompletionsPacket.class, ClientboundCustomChatCompletionsPacket::new)
/* 260 */       .<ClientboundCustomPayloadPacket>addPacket(ClientboundCustomPayloadPacket.class, ClientboundCustomPayloadPacket::new)
/* 261 */       .<ClientboundDamageEventPacket>addPacket(ClientboundDamageEventPacket.class, ClientboundDamageEventPacket::new)
/* 262 */       .<ClientboundDeleteChatPacket>addPacket(ClientboundDeleteChatPacket.class, ClientboundDeleteChatPacket::new)
/* 263 */       .<ClientboundDisconnectPacket>addPacket(ClientboundDisconnectPacket.class, ClientboundDisconnectPacket::new)
/* 264 */       .<ClientboundDisguisedChatPacket>addPacket(ClientboundDisguisedChatPacket.class, ClientboundDisguisedChatPacket::new)
/* 265 */       .<ClientboundEntityEventPacket>addPacket(ClientboundEntityEventPacket.class, ClientboundEntityEventPacket::new)
/* 266 */       .<ClientboundExplodePacket>addPacket(ClientboundExplodePacket.class, ClientboundExplodePacket::new)
/* 267 */       .<ClientboundForgetLevelChunkPacket>addPacket(ClientboundForgetLevelChunkPacket.class, ClientboundForgetLevelChunkPacket::new)
/* 268 */       .<ClientboundGameEventPacket>addPacket(ClientboundGameEventPacket.class, ClientboundGameEventPacket::new)
/* 269 */       .<ClientboundHorseScreenOpenPacket>addPacket(ClientboundHorseScreenOpenPacket.class, ClientboundHorseScreenOpenPacket::new)
/* 270 */       .<ClientboundHurtAnimationPacket>addPacket(ClientboundHurtAnimationPacket.class, ClientboundHurtAnimationPacket::new)
/* 271 */       .<ClientboundInitializeBorderPacket>addPacket(ClientboundInitializeBorderPacket.class, ClientboundInitializeBorderPacket::new)
/* 272 */       .<ClientboundKeepAlivePacket>addPacket(ClientboundKeepAlivePacket.class, ClientboundKeepAlivePacket::new)
/* 273 */       .<ClientboundLevelChunkWithLightPacket>addPacket(ClientboundLevelChunkWithLightPacket.class, ClientboundLevelChunkWithLightPacket::new)
/* 274 */       .<ClientboundLevelEventPacket>addPacket(ClientboundLevelEventPacket.class, ClientboundLevelEventPacket::new)
/* 275 */       .<ClientboundLevelParticlesPacket>addPacket(ClientboundLevelParticlesPacket.class, ClientboundLevelParticlesPacket::new)
/* 276 */       .<ClientboundLightUpdatePacket>addPacket(ClientboundLightUpdatePacket.class, ClientboundLightUpdatePacket::new)
/* 277 */       .<ClientboundLoginPacket>addPacket(ClientboundLoginPacket.class, ClientboundLoginPacket::new)
/* 278 */       .<ClientboundMapItemDataPacket>addPacket(ClientboundMapItemDataPacket.class, ClientboundMapItemDataPacket::new)
/* 279 */       .<ClientboundMerchantOffersPacket>addPacket(ClientboundMerchantOffersPacket.class, ClientboundMerchantOffersPacket::new)
/* 280 */       .<ClientboundMoveEntityPacket.Pos>addPacket(ClientboundMoveEntityPacket.Pos.class, ClientboundMoveEntityPacket.Pos::read)
/* 281 */       .<ClientboundMoveEntityPacket.PosRot>addPacket(ClientboundMoveEntityPacket.PosRot.class, ClientboundMoveEntityPacket.PosRot::read)
/* 282 */       .<ClientboundMoveEntityPacket.Rot>addPacket(ClientboundMoveEntityPacket.Rot.class, ClientboundMoveEntityPacket.Rot::read)
/* 283 */       .<ClientboundMoveVehiclePacket>addPacket(ClientboundMoveVehiclePacket.class, ClientboundMoveVehiclePacket::new)
/* 284 */       .<ClientboundOpenBookPacket>addPacket(ClientboundOpenBookPacket.class, ClientboundOpenBookPacket::new)
/* 285 */       .<ClientboundOpenScreenPacket>addPacket(ClientboundOpenScreenPacket.class, ClientboundOpenScreenPacket::new)
/* 286 */       .<ClientboundOpenSignEditorPacket>addPacket(ClientboundOpenSignEditorPacket.class, ClientboundOpenSignEditorPacket::new)
/* 287 */       .<ClientboundPingPacket>addPacket(ClientboundPingPacket.class, ClientboundPingPacket::new)
/* 288 */       .<ClientboundPongResponsePacket>addPacket(ClientboundPongResponsePacket.class, ClientboundPongResponsePacket::new)
/* 289 */       .<ClientboundPlaceGhostRecipePacket>addPacket(ClientboundPlaceGhostRecipePacket.class, ClientboundPlaceGhostRecipePacket::new)
/* 290 */       .<ClientboundPlayerAbilitiesPacket>addPacket(ClientboundPlayerAbilitiesPacket.class, ClientboundPlayerAbilitiesPacket::new)
/* 291 */       .<ClientboundPlayerChatPacket>addPacket(ClientboundPlayerChatPacket.class, ClientboundPlayerChatPacket::new)
/* 292 */       .<ClientboundPlayerCombatEndPacket>addPacket(ClientboundPlayerCombatEndPacket.class, ClientboundPlayerCombatEndPacket::new)
/* 293 */       .<ClientboundPlayerCombatEnterPacket>addPacket(ClientboundPlayerCombatEnterPacket.class, ClientboundPlayerCombatEnterPacket::new)
/* 294 */       .<ClientboundPlayerCombatKillPacket>addPacket(ClientboundPlayerCombatKillPacket.class, ClientboundPlayerCombatKillPacket::new)
/* 295 */       .<ClientboundPlayerInfoRemovePacket>addPacket(ClientboundPlayerInfoRemovePacket.class, ClientboundPlayerInfoRemovePacket::new)
/* 296 */       .<ClientboundPlayerInfoUpdatePacket>addPacket(ClientboundPlayerInfoUpdatePacket.class, ClientboundPlayerInfoUpdatePacket::new)
/* 297 */       .<ClientboundPlayerLookAtPacket>addPacket(ClientboundPlayerLookAtPacket.class, ClientboundPlayerLookAtPacket::new)
/* 298 */       .<ClientboundPlayerPositionPacket>addPacket(ClientboundPlayerPositionPacket.class, ClientboundPlayerPositionPacket::new)
/* 299 */       .<ClientboundRecipePacket>addPacket(ClientboundRecipePacket.class, ClientboundRecipePacket::new)
/* 300 */       .<ClientboundRemoveEntitiesPacket>addPacket(ClientboundRemoveEntitiesPacket.class, ClientboundRemoveEntitiesPacket::new)
/* 301 */       .<ClientboundRemoveMobEffectPacket>addPacket(ClientboundRemoveMobEffectPacket.class, ClientboundRemoveMobEffectPacket::new)
/* 302 */       .<ClientboundResetScorePacket>addPacket(ClientboundResetScorePacket.class, ClientboundResetScorePacket::new)
/* 303 */       .<ClientboundResourcePackPopPacket>addPacket(ClientboundResourcePackPopPacket.class, ClientboundResourcePackPopPacket::new)
/* 304 */       .<ClientboundResourcePackPushPacket>addPacket(ClientboundResourcePackPushPacket.class, ClientboundResourcePackPushPacket::new)
/* 305 */       .<ClientboundRespawnPacket>addPacket(ClientboundRespawnPacket.class, ClientboundRespawnPacket::new)
/* 306 */       .<ClientboundRotateHeadPacket>addPacket(ClientboundRotateHeadPacket.class, ClientboundRotateHeadPacket::new)
/* 307 */       .<ClientboundSectionBlocksUpdatePacket>addPacket(ClientboundSectionBlocksUpdatePacket.class, ClientboundSectionBlocksUpdatePacket::new)
/* 308 */       .<ClientboundSelectAdvancementsTabPacket>addPacket(ClientboundSelectAdvancementsTabPacket.class, ClientboundSelectAdvancementsTabPacket::new)
/* 309 */       .<ClientboundServerDataPacket>addPacket(ClientboundServerDataPacket.class, ClientboundServerDataPacket::new)
/* 310 */       .<ClientboundSetActionBarTextPacket>addPacket(ClientboundSetActionBarTextPacket.class, ClientboundSetActionBarTextPacket::new)
/* 311 */       .<ClientboundSetBorderCenterPacket>addPacket(ClientboundSetBorderCenterPacket.class, ClientboundSetBorderCenterPacket::new)
/* 312 */       .<ClientboundSetBorderLerpSizePacket>addPacket(ClientboundSetBorderLerpSizePacket.class, ClientboundSetBorderLerpSizePacket::new)
/* 313 */       .<ClientboundSetBorderSizePacket>addPacket(ClientboundSetBorderSizePacket.class, ClientboundSetBorderSizePacket::new)
/* 314 */       .<ClientboundSetBorderWarningDelayPacket>addPacket(ClientboundSetBorderWarningDelayPacket.class, ClientboundSetBorderWarningDelayPacket::new)
/* 315 */       .<ClientboundSetBorderWarningDistancePacket>addPacket(ClientboundSetBorderWarningDistancePacket.class, ClientboundSetBorderWarningDistancePacket::new)
/* 316 */       .<ClientboundSetCameraPacket>addPacket(ClientboundSetCameraPacket.class, ClientboundSetCameraPacket::new)
/* 317 */       .<ClientboundSetCarriedItemPacket>addPacket(ClientboundSetCarriedItemPacket.class, ClientboundSetCarriedItemPacket::new)
/* 318 */       .<ClientboundSetChunkCacheCenterPacket>addPacket(ClientboundSetChunkCacheCenterPacket.class, ClientboundSetChunkCacheCenterPacket::new)
/* 319 */       .<ClientboundSetChunkCacheRadiusPacket>addPacket(ClientboundSetChunkCacheRadiusPacket.class, ClientboundSetChunkCacheRadiusPacket::new)
/* 320 */       .<ClientboundSetDefaultSpawnPositionPacket>addPacket(ClientboundSetDefaultSpawnPositionPacket.class, ClientboundSetDefaultSpawnPositionPacket::new)
/* 321 */       .<ClientboundSetDisplayObjectivePacket>addPacket(ClientboundSetDisplayObjectivePacket.class, ClientboundSetDisplayObjectivePacket::new)
/* 322 */       .<ClientboundSetEntityDataPacket>addPacket(ClientboundSetEntityDataPacket.class, ClientboundSetEntityDataPacket::new)
/* 323 */       .<ClientboundSetEntityLinkPacket>addPacket(ClientboundSetEntityLinkPacket.class, ClientboundSetEntityLinkPacket::new)
/* 324 */       .<ClientboundSetEntityMotionPacket>addPacket(ClientboundSetEntityMotionPacket.class, ClientboundSetEntityMotionPacket::new)
/* 325 */       .<ClientboundSetEquipmentPacket>addPacket(ClientboundSetEquipmentPacket.class, ClientboundSetEquipmentPacket::new)
/* 326 */       .<ClientboundSetExperiencePacket>addPacket(ClientboundSetExperiencePacket.class, ClientboundSetExperiencePacket::new)
/* 327 */       .<ClientboundSetHealthPacket>addPacket(ClientboundSetHealthPacket.class, ClientboundSetHealthPacket::new)
/* 328 */       .<ClientboundSetObjectivePacket>addPacket(ClientboundSetObjectivePacket.class, ClientboundSetObjectivePacket::new)
/* 329 */       .<ClientboundSetPassengersPacket>addPacket(ClientboundSetPassengersPacket.class, ClientboundSetPassengersPacket::new)
/* 330 */       .<ClientboundSetPlayerTeamPacket>addPacket(ClientboundSetPlayerTeamPacket.class, ClientboundSetPlayerTeamPacket::new)
/* 331 */       .<ClientboundSetScorePacket>addPacket(ClientboundSetScorePacket.class, ClientboundSetScorePacket::new)
/* 332 */       .<ClientboundSetSimulationDistancePacket>addPacket(ClientboundSetSimulationDistancePacket.class, ClientboundSetSimulationDistancePacket::new)
/* 333 */       .<ClientboundSetSubtitleTextPacket>addPacket(ClientboundSetSubtitleTextPacket.class, ClientboundSetSubtitleTextPacket::new)
/* 334 */       .<ClientboundSetTimePacket>addPacket(ClientboundSetTimePacket.class, ClientboundSetTimePacket::new)
/* 335 */       .<ClientboundSetTitleTextPacket>addPacket(ClientboundSetTitleTextPacket.class, ClientboundSetTitleTextPacket::new)
/* 336 */       .<ClientboundSetTitlesAnimationPacket>addPacket(ClientboundSetTitlesAnimationPacket.class, ClientboundSetTitlesAnimationPacket::new)
/* 337 */       .<ClientboundSoundEntityPacket>addPacket(ClientboundSoundEntityPacket.class, ClientboundSoundEntityPacket::new)
/* 338 */       .<ClientboundSoundPacket>addPacket(ClientboundSoundPacket.class, ClientboundSoundPacket::new)
/* 339 */       .<ClientboundStartConfigurationPacket>addPacket(ClientboundStartConfigurationPacket.class, ClientboundStartConfigurationPacket::new)
/* 340 */       .<ClientboundStopSoundPacket>addPacket(ClientboundStopSoundPacket.class, ClientboundStopSoundPacket::new)
/* 341 */       .<ClientboundSystemChatPacket>addPacket(ClientboundSystemChatPacket.class, ClientboundSystemChatPacket::new)
/* 342 */       .<ClientboundTabListPacket>addPacket(ClientboundTabListPacket.class, ClientboundTabListPacket::new)
/* 343 */       .<ClientboundTagQueryPacket>addPacket(ClientboundTagQueryPacket.class, ClientboundTagQueryPacket::new)
/* 344 */       .<ClientboundTakeItemEntityPacket>addPacket(ClientboundTakeItemEntityPacket.class, ClientboundTakeItemEntityPacket::new)
/* 345 */       .<ClientboundTeleportEntityPacket>addPacket(ClientboundTeleportEntityPacket.class, ClientboundTeleportEntityPacket::new)
/* 346 */       .<ClientboundTickingStatePacket>addPacket(ClientboundTickingStatePacket.class, ClientboundTickingStatePacket::new)
/* 347 */       .<ClientboundTickingStepPacket>addPacket(ClientboundTickingStepPacket.class, ClientboundTickingStepPacket::new)
/* 348 */       .<ClientboundUpdateAdvancementsPacket>addPacket(ClientboundUpdateAdvancementsPacket.class, ClientboundUpdateAdvancementsPacket::new)
/* 349 */       .<ClientboundUpdateAttributesPacket>addPacket(ClientboundUpdateAttributesPacket.class, ClientboundUpdateAttributesPacket::new)
/* 350 */       .<ClientboundUpdateMobEffectPacket>addPacket(ClientboundUpdateMobEffectPacket.class, ClientboundUpdateMobEffectPacket::new)
/* 351 */       .<ClientboundUpdateRecipesPacket>addPacket(ClientboundUpdateRecipesPacket.class, ClientboundUpdateRecipesPacket::new)
/* 352 */       .addPacket(ClientboundUpdateTagsPacket.class, ClientboundUpdateTagsPacket::new))
/*     */     
/* 354 */     .addFlow(PacketFlow.SERVERBOUND, (new PacketSet<>())
/* 355 */       .<ServerboundAcceptTeleportationPacket>addPacket(ServerboundAcceptTeleportationPacket.class, ServerboundAcceptTeleportationPacket::new)
/* 356 */       .<ServerboundBlockEntityTagQuery>addPacket(ServerboundBlockEntityTagQuery.class, ServerboundBlockEntityTagQuery::new)
/* 357 */       .<ServerboundChangeDifficultyPacket>addPacket(ServerboundChangeDifficultyPacket.class, ServerboundChangeDifficultyPacket::new)
/* 358 */       .<ServerboundChatAckPacket>addPacket(ServerboundChatAckPacket.class, ServerboundChatAckPacket::new)
/* 359 */       .<ServerboundChatCommandPacket>addPacket(ServerboundChatCommandPacket.class, ServerboundChatCommandPacket::new)
/* 360 */       .<ServerboundChatPacket>addPacket(ServerboundChatPacket.class, ServerboundChatPacket::new)
/* 361 */       .<ServerboundChatSessionUpdatePacket>addPacket(ServerboundChatSessionUpdatePacket.class, ServerboundChatSessionUpdatePacket::new)
/* 362 */       .<ServerboundChunkBatchReceivedPacket>addPacket(ServerboundChunkBatchReceivedPacket.class, ServerboundChunkBatchReceivedPacket::new)
/* 363 */       .<ServerboundClientCommandPacket>addPacket(ServerboundClientCommandPacket.class, ServerboundClientCommandPacket::new)
/* 364 */       .<ServerboundClientInformationPacket>addPacket(ServerboundClientInformationPacket.class, ServerboundClientInformationPacket::new)
/* 365 */       .<ServerboundCommandSuggestionPacket>addPacket(ServerboundCommandSuggestionPacket.class, ServerboundCommandSuggestionPacket::new)
/* 366 */       .<ServerboundConfigurationAcknowledgedPacket>addPacket(ServerboundConfigurationAcknowledgedPacket.class, ServerboundConfigurationAcknowledgedPacket::new)
/* 367 */       .<ServerboundContainerButtonClickPacket>addPacket(ServerboundContainerButtonClickPacket.class, ServerboundContainerButtonClickPacket::new)
/* 368 */       .<ServerboundContainerClickPacket>addPacket(ServerboundContainerClickPacket.class, ServerboundContainerClickPacket::new)
/* 369 */       .<ServerboundContainerClosePacket>addPacket(ServerboundContainerClosePacket.class, ServerboundContainerClosePacket::new)
/* 370 */       .<ServerboundContainerSlotStateChangedPacket>addPacket(ServerboundContainerSlotStateChangedPacket.class, ServerboundContainerSlotStateChangedPacket::new)
/* 371 */       .<ServerboundCustomPayloadPacket>addPacket(ServerboundCustomPayloadPacket.class, ServerboundCustomPayloadPacket::new)
/* 372 */       .<ServerboundEditBookPacket>addPacket(ServerboundEditBookPacket.class, ServerboundEditBookPacket::new)
/* 373 */       .<ServerboundEntityTagQuery>addPacket(ServerboundEntityTagQuery.class, ServerboundEntityTagQuery::new)
/* 374 */       .<ServerboundInteractPacket>addPacket(ServerboundInteractPacket.class, ServerboundInteractPacket::new)
/* 375 */       .<ServerboundJigsawGeneratePacket>addPacket(ServerboundJigsawGeneratePacket.class, ServerboundJigsawGeneratePacket::new)
/* 376 */       .<ServerboundKeepAlivePacket>addPacket(ServerboundKeepAlivePacket.class, ServerboundKeepAlivePacket::new)
/* 377 */       .<ServerboundLockDifficultyPacket>addPacket(ServerboundLockDifficultyPacket.class, ServerboundLockDifficultyPacket::new)
/* 378 */       .<ServerboundMovePlayerPacket.Pos>addPacket(ServerboundMovePlayerPacket.Pos.class, ServerboundMovePlayerPacket.Pos::read)
/* 379 */       .<ServerboundMovePlayerPacket.PosRot>addPacket(ServerboundMovePlayerPacket.PosRot.class, ServerboundMovePlayerPacket.PosRot::read)
/* 380 */       .<ServerboundMovePlayerPacket.Rot>addPacket(ServerboundMovePlayerPacket.Rot.class, ServerboundMovePlayerPacket.Rot::read)
/* 381 */       .<ServerboundMovePlayerPacket.StatusOnly>addPacket(ServerboundMovePlayerPacket.StatusOnly.class, ServerboundMovePlayerPacket.StatusOnly::read)
/* 382 */       .<ServerboundMoveVehiclePacket>addPacket(ServerboundMoveVehiclePacket.class, ServerboundMoveVehiclePacket::new)
/* 383 */       .<ServerboundPaddleBoatPacket>addPacket(ServerboundPaddleBoatPacket.class, ServerboundPaddleBoatPacket::new)
/* 384 */       .<ServerboundPickItemPacket>addPacket(ServerboundPickItemPacket.class, ServerboundPickItemPacket::new)
/* 385 */       .<ServerboundPingRequestPacket>addPacket(ServerboundPingRequestPacket.class, ServerboundPingRequestPacket::new)
/* 386 */       .<ServerboundPlaceRecipePacket>addPacket(ServerboundPlaceRecipePacket.class, ServerboundPlaceRecipePacket::new)
/* 387 */       .<ServerboundPlayerAbilitiesPacket>addPacket(ServerboundPlayerAbilitiesPacket.class, ServerboundPlayerAbilitiesPacket::new)
/* 388 */       .<ServerboundPlayerActionPacket>addPacket(ServerboundPlayerActionPacket.class, ServerboundPlayerActionPacket::new)
/* 389 */       .<ServerboundPlayerCommandPacket>addPacket(ServerboundPlayerCommandPacket.class, ServerboundPlayerCommandPacket::new)
/* 390 */       .<ServerboundPlayerInputPacket>addPacket(ServerboundPlayerInputPacket.class, ServerboundPlayerInputPacket::new)
/* 391 */       .<ServerboundPongPacket>addPacket(ServerboundPongPacket.class, ServerboundPongPacket::new)
/* 392 */       .<ServerboundRecipeBookChangeSettingsPacket>addPacket(ServerboundRecipeBookChangeSettingsPacket.class, ServerboundRecipeBookChangeSettingsPacket::new)
/* 393 */       .<ServerboundRecipeBookSeenRecipePacket>addPacket(ServerboundRecipeBookSeenRecipePacket.class, ServerboundRecipeBookSeenRecipePacket::new)
/* 394 */       .<ServerboundRenameItemPacket>addPacket(ServerboundRenameItemPacket.class, ServerboundRenameItemPacket::new)
/* 395 */       .<ServerboundResourcePackPacket>addPacket(ServerboundResourcePackPacket.class, ServerboundResourcePackPacket::new)
/* 396 */       .<ServerboundSeenAdvancementsPacket>addPacket(ServerboundSeenAdvancementsPacket.class, ServerboundSeenAdvancementsPacket::new)
/* 397 */       .<ServerboundSelectTradePacket>addPacket(ServerboundSelectTradePacket.class, ServerboundSelectTradePacket::new)
/* 398 */       .<ServerboundSetBeaconPacket>addPacket(ServerboundSetBeaconPacket.class, ServerboundSetBeaconPacket::new)
/* 399 */       .<ServerboundSetCarriedItemPacket>addPacket(ServerboundSetCarriedItemPacket.class, ServerboundSetCarriedItemPacket::new)
/* 400 */       .<ServerboundSetCommandBlockPacket>addPacket(ServerboundSetCommandBlockPacket.class, ServerboundSetCommandBlockPacket::new)
/* 401 */       .<ServerboundSetCommandMinecartPacket>addPacket(ServerboundSetCommandMinecartPacket.class, ServerboundSetCommandMinecartPacket::new)
/* 402 */       .<ServerboundSetCreativeModeSlotPacket>addPacket(ServerboundSetCreativeModeSlotPacket.class, ServerboundSetCreativeModeSlotPacket::new)
/* 403 */       .<ServerboundSetJigsawBlockPacket>addPacket(ServerboundSetJigsawBlockPacket.class, ServerboundSetJigsawBlockPacket::new)
/* 404 */       .<ServerboundSetStructureBlockPacket>addPacket(ServerboundSetStructureBlockPacket.class, ServerboundSetStructureBlockPacket::new)
/* 405 */       .<ServerboundSignUpdatePacket>addPacket(ServerboundSignUpdatePacket.class, ServerboundSignUpdatePacket::new)
/* 406 */       .<ServerboundSwingPacket>addPacket(ServerboundSwingPacket.class, ServerboundSwingPacket::new)
/* 407 */       .<ServerboundTeleportToEntityPacket>addPacket(ServerboundTeleportToEntityPacket.class, ServerboundTeleportToEntityPacket::new)
/* 408 */       .<ServerboundUseItemOnPacket>addPacket(ServerboundUseItemOnPacket.class, ServerboundUseItemOnPacket::new)
/* 409 */       .addPacket(ServerboundUseItemPacket.class, ServerboundUseItemPacket::new))),
/*     */ 
/*     */   
/* 412 */   STATUS("status", 
/* 413 */     protocol()
/* 414 */     .<PacketListener>addFlow(PacketFlow.SERVERBOUND, (new PacketSet<>())
/* 415 */       .<ServerboundStatusRequestPacket>addPacket(ServerboundStatusRequestPacket.class, ServerboundStatusRequestPacket::new)
/* 416 */       .addPacket(ServerboundPingRequestPacket.class, ServerboundPingRequestPacket::new))
/*     */     
/* 418 */     .addFlow(PacketFlow.CLIENTBOUND, (new PacketSet<>())
/* 419 */       .<ClientboundStatusResponsePacket>addPacket(ClientboundStatusResponsePacket.class, ClientboundStatusResponsePacket::new)
/* 420 */       .addPacket(ClientboundPongResponsePacket.class, ClientboundPongResponsePacket::new))),
/*     */ 
/*     */   
/* 423 */   LOGIN("login", 
/*     */ 
/*     */ 
/*     */     
/* 427 */     protocol()
/* 428 */     .<PacketListener>addFlow(PacketFlow.CLIENTBOUND, (new PacketSet<>())
/* 429 */       .<ClientboundLoginDisconnectPacket>addPacket(ClientboundLoginDisconnectPacket.class, ClientboundLoginDisconnectPacket::new)
/* 430 */       .<ClientboundHelloPacket>addPacket(ClientboundHelloPacket.class, ClientboundHelloPacket::new)
/* 431 */       .<ClientboundGameProfilePacket>addPacket(ClientboundGameProfilePacket.class, ClientboundGameProfilePacket::new)
/* 432 */       .<ClientboundLoginCompressionPacket>addPacket(ClientboundLoginCompressionPacket.class, ClientboundLoginCompressionPacket::new)
/* 433 */       .addPacket(ClientboundCustomQueryPacket.class, ClientboundCustomQueryPacket::new))
/*     */     
/* 435 */     .addFlow(PacketFlow.SERVERBOUND, (new PacketSet<>())
/* 436 */       .<ServerboundHelloPacket>addPacket(ServerboundHelloPacket.class, ServerboundHelloPacket::new)
/* 437 */       .<ServerboundKeyPacket>addPacket(ServerboundKeyPacket.class, ServerboundKeyPacket::new)
/* 438 */       .<ServerboundCustomQueryAnswerPacket>addPacket(ServerboundCustomQueryAnswerPacket.class, ServerboundCustomQueryAnswerPacket::read)
/* 439 */       .addPacket(ServerboundLoginAcknowledgedPacket.class, ServerboundLoginAcknowledgedPacket::new))),
/*     */ 
/*     */   
/* 442 */   CONFIGURATION("configuration", 
/* 443 */     protocol()
/* 444 */     .<PacketListener>addFlow(PacketFlow.CLIENTBOUND, (new PacketSet<>())
/* 445 */       .<ClientboundCustomPayloadPacket>addPacket(ClientboundCustomPayloadPacket.class, ClientboundCustomPayloadPacket::new)
/* 446 */       .<ClientboundDisconnectPacket>addPacket(ClientboundDisconnectPacket.class, ClientboundDisconnectPacket::new)
/* 447 */       .<ClientboundFinishConfigurationPacket>addPacket(ClientboundFinishConfigurationPacket.class, ClientboundFinishConfigurationPacket::new)
/* 448 */       .<ClientboundKeepAlivePacket>addPacket(ClientboundKeepAlivePacket.class, ClientboundKeepAlivePacket::new)
/* 449 */       .<ClientboundPingPacket>addPacket(ClientboundPingPacket.class, ClientboundPingPacket::new)
/* 450 */       .<ClientboundRegistryDataPacket>addPacket(ClientboundRegistryDataPacket.class, ClientboundRegistryDataPacket::new)
/* 451 */       .<ClientboundResourcePackPopPacket>addPacket(ClientboundResourcePackPopPacket.class, ClientboundResourcePackPopPacket::new)
/* 452 */       .<ClientboundResourcePackPushPacket>addPacket(ClientboundResourcePackPushPacket.class, ClientboundResourcePackPushPacket::new)
/* 453 */       .<ClientboundUpdateEnabledFeaturesPacket>addPacket(ClientboundUpdateEnabledFeaturesPacket.class, ClientboundUpdateEnabledFeaturesPacket::new)
/* 454 */       .addPacket(ClientboundUpdateTagsPacket.class, ClientboundUpdateTagsPacket::new))
/*     */     
/* 456 */     .addFlow(PacketFlow.SERVERBOUND, (new PacketSet<>())
/* 457 */       .<ServerboundClientInformationPacket>addPacket(ServerboundClientInformationPacket.class, ServerboundClientInformationPacket::new)
/* 458 */       .<ServerboundCustomPayloadPacket>addPacket(ServerboundCustomPayloadPacket.class, ServerboundCustomPayloadPacket::new)
/* 459 */       .<ServerboundFinishConfigurationPacket>addPacket(ServerboundFinishConfigurationPacket.class, ServerboundFinishConfigurationPacket::new)
/* 460 */       .<ServerboundKeepAlivePacket>addPacket(ServerboundKeepAlivePacket.class, ServerboundKeepAlivePacket::new)
/* 461 */       .<ServerboundPongPacket>addPacket(ServerboundPongPacket.class, ServerboundPongPacket::new)
/* 462 */       .addPacket(ServerboundResourcePackPacket.class, ServerboundResourcePackPacket::new)));
/*     */   public static final int NOT_REGISTERED = -1;
/*     */   private final String id;
/*     */   private final Map<PacketFlow, CodecData<?>> flows;
/*     */   
/*     */   private static class PacketSet<T extends PacketListener>
/*     */   {
/*     */     PacketSet() {
/* 470 */       this.classToId = (Object2IntMap<Class<? extends Packet<? super T>>>)Util.make(new Object2IntOpenHashMap(), $$0 -> $$0.defaultReturnValue(-1));
/* 471 */       this.idToDeserializer = Lists.newArrayList();
/* 472 */       this.bundlerInfo = BundlerInfo.EMPTY;
/* 473 */       this.extraClasses = new HashSet<>();
/*     */     } private static final Logger LOGGER = LogUtils.getLogger(); final Object2IntMap<Class<? extends Packet<? super T>>> classToId;
/*     */     public <P extends Packet<? super T>> PacketSet<T> addPacket(Class<P> $$0, Function<FriendlyByteBuf, P> $$1) {
/* 476 */       int $$2 = this.idToDeserializer.size();
/* 477 */       int $$3 = this.classToId.put($$0, $$2);
/*     */       
/* 479 */       if ($$3 != -1) {
/* 480 */         String $$4 = "Packet " + $$0 + " is already registered to ID " + $$3;
/* 481 */         LOGGER.error(LogUtils.FATAL_MARKER, $$4);
/* 482 */         throw new IllegalArgumentException($$4);
/*     */       } 
/*     */       
/* 485 */       this.idToDeserializer.add($$1);
/* 486 */       return this;
/*     */     }
/*     */     private final List<Function<FriendlyByteBuf, ? extends Packet<? super T>>> idToDeserializer; private BundlerInfo bundlerInfo; private final Set<Class<? extends Packet<T>>> extraClasses;
/*     */     
/*     */     public <P extends net.minecraft.network.protocol.BundlePacket<T>> PacketSet<T> withBundlePacket(Class<P> $$0, Function<Iterable<Packet<T>>, P> $$1) {
/* 491 */       if (this.bundlerInfo != BundlerInfo.EMPTY) {
/* 492 */         throw new IllegalStateException("Bundle packet already configured");
/*     */       }
/*     */       
/* 495 */       BundleDelimiterPacket<T> $$2 = new BundleDelimiterPacket();
/* 496 */       addPacket(BundleDelimiterPacket.class, $$1 -> $$0);
/* 497 */       this.bundlerInfo = BundlerInfo.createForPacket($$0, $$1, $$2);
/* 498 */       this.extraClasses.add($$0);
/* 499 */       return this;
/*     */     }
/*     */     
/*     */     public int getId(Class<?> $$0) {
/* 503 */       return this.classToId.getInt($$0);
/*     */     }
/*     */     
/*     */     public boolean isKnownPacket(Class<?> $$0) {
/* 507 */       return (this.classToId.containsKey($$0) || this.extraClasses.contains($$0));
/*     */     }
/*     */     
/*     */     @Nullable
/*     */     public Packet<?> createPacket(int $$0, FriendlyByteBuf $$1) {
/* 512 */       Function<FriendlyByteBuf, ? extends Packet<? super T>> $$2 = this.idToDeserializer.get($$0);
/* 513 */       return ($$2 != null) ? $$2.apply($$1) : null;
/*     */     }
/*     */     
/*     */     public BundlerInfo bundlerInfo() {
/* 517 */       return this.bundlerInfo;
/*     */     }
/*     */   }
/*     */   
/*     */   private static ProtocolBuilder protocol() {
/* 522 */     return new ProtocolBuilder();
/*     */   }
/*     */   
/*     */   private static class ProtocolBuilder {
/* 526 */     private final Map<PacketFlow, ConnectionProtocol.PacketSet<?>> flows = Maps.newEnumMap(PacketFlow.class);
/*     */     
/*     */     public <T extends PacketListener> ProtocolBuilder addFlow(PacketFlow $$0, ConnectionProtocol.PacketSet<T> $$1) {
/* 529 */       this.flows.put($$0, $$1);
/* 530 */       return this;
/*     */     }
/*     */     
/*     */     public Map<PacketFlow, ConnectionProtocol.CodecData<?>> buildCodecs(ConnectionProtocol $$0) {
/* 534 */       Map<PacketFlow, ConnectionProtocol.CodecData<?>> $$1 = new EnumMap<>(PacketFlow.class);
/* 535 */       for (PacketFlow $$2 : PacketFlow.values()) {
/* 536 */         ConnectionProtocol.PacketSet<?> $$3 = this.flows.get($$2);
/* 537 */         if ($$3 == null) {
/* 538 */           throw new IllegalStateException("Missing packets for flow " + $$2 + " in protocol " + $$0);
/*     */         }
/* 540 */         $$1.put($$2, new ConnectionProtocol.CodecData($$0, $$2, $$3));
/*     */       } 
/* 542 */       return $$1;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ConnectionProtocol(String $$0, ProtocolBuilder $$1) {
/* 550 */     this.id = $$0;
/* 551 */     this.flows = $$1.buildCodecs(this);
/*     */   }
/*     */   
/*     */   @VisibleForDebug
/*     */   public Int2ObjectMap<Class<? extends Packet<?>>> getPacketsByIds(PacketFlow $$0) {
/* 556 */     return ((CodecData)this.flows.get($$0)).packetsByIds();
/*     */   }
/*     */   
/*     */   @VisibleForDebug
/*     */   public String id() {
/* 561 */     return this.id;
/*     */   }
/*     */   
/*     */   public CodecData<?> codec(PacketFlow $$0) {
/* 565 */     return this.flows.get($$0);
/*     */   }
/*     */   
/*     */   public static class CodecData<T extends PacketListener> implements BundlerInfo.Provider {
/*     */     private final ConnectionProtocol protocol;
/*     */     private final PacketFlow flow;
/*     */     private final ConnectionProtocol.PacketSet<T> packetSet;
/*     */     
/*     */     public CodecData(ConnectionProtocol $$0, PacketFlow $$1, ConnectionProtocol.PacketSet<T> $$2) {
/* 574 */       this.protocol = $$0;
/* 575 */       this.flow = $$1;
/* 576 */       this.packetSet = $$2;
/*     */     }
/*     */     
/*     */     public ConnectionProtocol protocol() {
/* 580 */       return this.protocol;
/*     */     }
/*     */     
/*     */     public PacketFlow flow() {
/* 584 */       return this.flow;
/*     */     }
/*     */     
/*     */     public int packetId(Packet<?> $$0) {
/* 588 */       return this.packetSet.getId($$0.getClass());
/*     */     }
/*     */ 
/*     */     
/*     */     public BundlerInfo bundlerInfo() {
/* 593 */       return this.packetSet.bundlerInfo();
/*     */     }
/*     */     
/*     */     Int2ObjectMap<Class<? extends Packet<?>>> packetsByIds() {
/* 597 */       Int2ObjectOpenHashMap int2ObjectOpenHashMap = new Int2ObjectOpenHashMap();
/* 598 */       this.packetSet.classToId.forEach(($$1, $$2) -> $$0.put($$2.intValue(), $$1));
/* 599 */       return (Int2ObjectMap<Class<? extends Packet<?>>>)int2ObjectOpenHashMap;
/*     */     }
/*     */     
/*     */     @Nullable
/*     */     public Packet<?> createPacket(int $$0, FriendlyByteBuf $$1) {
/* 604 */       return this.packetSet.createPacket($$0, $$1);
/*     */     }
/*     */     
/*     */     public boolean isValidPacketType(Packet<?> $$0) {
/* 608 */       return this.packetSet.isKnownPacket($$0.getClass());
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\ConnectionProtocol.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */