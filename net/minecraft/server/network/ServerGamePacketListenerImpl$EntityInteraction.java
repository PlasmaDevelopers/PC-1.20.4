package net.minecraft.server.network;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;

@FunctionalInterface
interface EntityInteraction {
  InteractionResult run(ServerPlayer paramServerPlayer, Entity paramEntity, InteractionHand paramInteractionHand);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\network\ServerGamePacketListenerImpl$EntityInteraction.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */