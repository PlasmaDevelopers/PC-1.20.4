package net.minecraft.network.chat;

import java.util.Optional;

public interface ContentConsumer<T> {
  Optional<T> accept(String paramString);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\chat\FormattedText$ContentConsumer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */