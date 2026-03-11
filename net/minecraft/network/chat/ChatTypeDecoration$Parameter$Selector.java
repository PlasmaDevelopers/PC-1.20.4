package net.minecraft.network.chat;

import javax.annotation.Nullable;

public interface Selector {
  @Nullable
  Component select(Component paramComponent, ChatType.Bound paramBound);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\chat\ChatTypeDecoration$Parameter$Selector.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */