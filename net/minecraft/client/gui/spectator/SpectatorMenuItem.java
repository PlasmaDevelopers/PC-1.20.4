package net.minecraft.client.gui.spectator;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public interface SpectatorMenuItem {
  void selectItem(SpectatorMenu paramSpectatorMenu);
  
  Component getName();
  
  void renderIcon(GuiGraphics paramGuiGraphics, float paramFloat, int paramInt);
  
  boolean isEnabled();
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\spectator\SpectatorMenuItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */