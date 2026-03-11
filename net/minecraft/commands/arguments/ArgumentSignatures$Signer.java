package net.minecraft.commands.arguments;

import javax.annotation.Nullable;
import net.minecraft.network.chat.MessageSignature;

@FunctionalInterface
public interface Signer {
  @Nullable
  MessageSignature sign(String paramString);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\ArgumentSignatures$Signer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */