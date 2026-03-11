package net.minecraft.client.gui.spectator;

import java.util.List;
import net.minecraft.network.chat.Component;

public interface SpectatorMenuCategory {
  List<SpectatorMenuItem> getItems();
  
  Component getPrompt();
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\spectator\SpectatorMenuCategory.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */