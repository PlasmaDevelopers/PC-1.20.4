package net.minecraft.client;

import com.mojang.serialization.Codec;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.client.gui.components.AbstractWidget;

interface ValueSet<T> {
  Function<OptionInstance<T>, AbstractWidget> createButton(OptionInstance.TooltipSupplier<T> paramTooltipSupplier, Options paramOptions, int paramInt1, int paramInt2, int paramInt3, Consumer<T> paramConsumer);
  
  Optional<T> validateValue(T paramT);
  
  Codec<T> codec();
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\OptionInstance$ValueSet.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */