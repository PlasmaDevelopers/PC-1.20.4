package net.minecraft.network.protocol;

import javax.annotation.Nullable;

public interface Bundler {
  @Nullable
  Packet<?> addPacket(Packet<?> paramPacket);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\BundlerInfo$Bundler.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */