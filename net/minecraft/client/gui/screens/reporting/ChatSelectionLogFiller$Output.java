package net.minecraft.client.gui.screens.reporting;

import net.minecraft.client.multiplayer.chat.LoggedChatMessage;
import net.minecraft.network.chat.Component;

public interface Output {
  void acceptMessage(int paramInt, LoggedChatMessage.Player paramPlayer);
  
  void acceptDivider(Component paramComponent);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\reporting\ChatSelectionLogFiller$Output.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */