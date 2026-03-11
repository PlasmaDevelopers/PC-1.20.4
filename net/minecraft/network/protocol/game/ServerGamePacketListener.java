/*   */ package net.minecraft.network.protocol.game;
/*   */ 
/*   */ import net.minecraft.network.ConnectionProtocol;
/*   */ import net.minecraft.network.protocol.common.ServerCommonPacketListener;
/*   */ 
/*   */ public interface ServerGamePacketListener
/*   */   extends ServerPingPacketListener, ServerCommonPacketListener {
/*   */   default ConnectionProtocol protocol() {
/* 9 */     return ConnectionProtocol.PLAY;
/*   */   }
/*   */   
/*   */   void handleAnimate(ServerboundSwingPacket paramServerboundSwingPacket);
/*   */   
/*   */   void handleChat(ServerboundChatPacket paramServerboundChatPacket);
/*   */   
/*   */   void handleChatCommand(ServerboundChatCommandPacket paramServerboundChatCommandPacket);
/*   */   
/*   */   void handleChatAck(ServerboundChatAckPacket paramServerboundChatAckPacket);
/*   */   
/*   */   void handleClientCommand(ServerboundClientCommandPacket paramServerboundClientCommandPacket);
/*   */   
/*   */   void handleContainerButtonClick(ServerboundContainerButtonClickPacket paramServerboundContainerButtonClickPacket);
/*   */   
/*   */   void handleContainerClick(ServerboundContainerClickPacket paramServerboundContainerClickPacket);
/*   */   
/*   */   void handlePlaceRecipe(ServerboundPlaceRecipePacket paramServerboundPlaceRecipePacket);
/*   */   
/*   */   void handleContainerClose(ServerboundContainerClosePacket paramServerboundContainerClosePacket);
/*   */   
/*   */   void handleInteract(ServerboundInteractPacket paramServerboundInteractPacket);
/*   */   
/*   */   void handleMovePlayer(ServerboundMovePlayerPacket paramServerboundMovePlayerPacket);
/*   */   
/*   */   void handlePlayerAbilities(ServerboundPlayerAbilitiesPacket paramServerboundPlayerAbilitiesPacket);
/*   */   
/*   */   void handlePlayerAction(ServerboundPlayerActionPacket paramServerboundPlayerActionPacket);
/*   */   
/*   */   void handlePlayerCommand(ServerboundPlayerCommandPacket paramServerboundPlayerCommandPacket);
/*   */   
/*   */   void handlePlayerInput(ServerboundPlayerInputPacket paramServerboundPlayerInputPacket);
/*   */   
/*   */   void handleSetCarriedItem(ServerboundSetCarriedItemPacket paramServerboundSetCarriedItemPacket);
/*   */   
/*   */   void handleSetCreativeModeSlot(ServerboundSetCreativeModeSlotPacket paramServerboundSetCreativeModeSlotPacket);
/*   */   
/*   */   void handleSignUpdate(ServerboundSignUpdatePacket paramServerboundSignUpdatePacket);
/*   */   
/*   */   void handleUseItemOn(ServerboundUseItemOnPacket paramServerboundUseItemOnPacket);
/*   */   
/*   */   void handleUseItem(ServerboundUseItemPacket paramServerboundUseItemPacket);
/*   */   
/*   */   void handleTeleportToEntityPacket(ServerboundTeleportToEntityPacket paramServerboundTeleportToEntityPacket);
/*   */   
/*   */   void handlePaddleBoat(ServerboundPaddleBoatPacket paramServerboundPaddleBoatPacket);
/*   */   
/*   */   void handleMoveVehicle(ServerboundMoveVehiclePacket paramServerboundMoveVehiclePacket);
/*   */   
/*   */   void handleAcceptTeleportPacket(ServerboundAcceptTeleportationPacket paramServerboundAcceptTeleportationPacket);
/*   */   
/*   */   void handleRecipeBookSeenRecipePacket(ServerboundRecipeBookSeenRecipePacket paramServerboundRecipeBookSeenRecipePacket);
/*   */   
/*   */   void handleRecipeBookChangeSettingsPacket(ServerboundRecipeBookChangeSettingsPacket paramServerboundRecipeBookChangeSettingsPacket);
/*   */   
/*   */   void handleSeenAdvancements(ServerboundSeenAdvancementsPacket paramServerboundSeenAdvancementsPacket);
/*   */   
/*   */   void handleCustomCommandSuggestions(ServerboundCommandSuggestionPacket paramServerboundCommandSuggestionPacket);
/*   */   
/*   */   void handleSetCommandBlock(ServerboundSetCommandBlockPacket paramServerboundSetCommandBlockPacket);
/*   */   
/*   */   void handleSetCommandMinecart(ServerboundSetCommandMinecartPacket paramServerboundSetCommandMinecartPacket);
/*   */   
/*   */   void handlePickItem(ServerboundPickItemPacket paramServerboundPickItemPacket);
/*   */   
/*   */   void handleRenameItem(ServerboundRenameItemPacket paramServerboundRenameItemPacket);
/*   */   
/*   */   void handleSetBeaconPacket(ServerboundSetBeaconPacket paramServerboundSetBeaconPacket);
/*   */   
/*   */   void handleSetStructureBlock(ServerboundSetStructureBlockPacket paramServerboundSetStructureBlockPacket);
/*   */   
/*   */   void handleSelectTrade(ServerboundSelectTradePacket paramServerboundSelectTradePacket);
/*   */   
/*   */   void handleEditBook(ServerboundEditBookPacket paramServerboundEditBookPacket);
/*   */   
/*   */   void handleEntityTagQuery(ServerboundEntityTagQuery paramServerboundEntityTagQuery);
/*   */   
/*   */   void handleContainerSlotStateChanged(ServerboundContainerSlotStateChangedPacket paramServerboundContainerSlotStateChangedPacket);
/*   */   
/*   */   void handleBlockEntityTagQuery(ServerboundBlockEntityTagQuery paramServerboundBlockEntityTagQuery);
/*   */   
/*   */   void handleSetJigsawBlock(ServerboundSetJigsawBlockPacket paramServerboundSetJigsawBlockPacket);
/*   */   
/*   */   void handleJigsawGenerate(ServerboundJigsawGeneratePacket paramServerboundJigsawGeneratePacket);
/*   */   
/*   */   void handleChangeDifficulty(ServerboundChangeDifficultyPacket paramServerboundChangeDifficultyPacket);
/*   */   
/*   */   void handleLockDifficulty(ServerboundLockDifficultyPacket paramServerboundLockDifficultyPacket);
/*   */   
/*   */   void handleChatSessionUpdate(ServerboundChatSessionUpdatePacket paramServerboundChatSessionUpdatePacket);
/*   */   
/*   */   void handleConfigurationAcknowledged(ServerboundConfigurationAcknowledgedPacket paramServerboundConfigurationAcknowledgedPacket);
/*   */   
/*   */   void handleChunkBatchReceived(ServerboundChunkBatchReceivedPacket paramServerboundChunkBatchReceivedPacket);
/*   */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ServerGamePacketListener.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */