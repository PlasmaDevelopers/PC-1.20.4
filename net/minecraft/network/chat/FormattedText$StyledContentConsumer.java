package net.minecraft.network.chat;

import java.util.Optional;

public interface StyledContentConsumer<T> {
  Optional<T> accept(Style paramStyle, String paramString);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\chat\FormattedText$StyledContentConsumer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */