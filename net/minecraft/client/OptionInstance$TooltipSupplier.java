package net.minecraft.client;

import javax.annotation.Nullable;
import net.minecraft.client.gui.components.Tooltip;

@FunctionalInterface
public interface TooltipSupplier<T> {
  @Nullable
  Tooltip apply(T paramT);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\OptionInstance$TooltipSupplier.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */