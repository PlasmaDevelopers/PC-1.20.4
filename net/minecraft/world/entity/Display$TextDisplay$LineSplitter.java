package net.minecraft.world.entity;

import net.minecraft.network.chat.Component;

@FunctionalInterface
public interface LineSplitter {
  Display.TextDisplay.CachedInfo split(Component paramComponent, int paramInt);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\Display$TextDisplay$LineSplitter.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */