package net.minecraft.world;

import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.MenuConstructor;

public interface MenuProvider extends MenuConstructor {
  Component getDisplayName();
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\MenuProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */