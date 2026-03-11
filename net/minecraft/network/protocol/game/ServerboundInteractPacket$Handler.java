package net.minecraft.network.protocol.game;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.Vec3;

public interface Handler {
  void onInteraction(InteractionHand paramInteractionHand);
  
  void onInteraction(InteractionHand paramInteractionHand, Vec3 paramVec3);
  
  void onAttack();
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ServerboundInteractPacket$Handler.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */