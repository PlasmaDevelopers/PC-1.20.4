package net.minecraft.client.multiplayer.prediction;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerGamePacketListener;

@FunctionalInterface
public interface PredictiveAction {
  Packet<ServerGamePacketListener> predict(int paramInt);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\multiplayer\prediction\PredictiveAction.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */