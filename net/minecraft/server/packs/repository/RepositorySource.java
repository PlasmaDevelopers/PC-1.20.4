package net.minecraft.server.packs.repository;

import java.util.function.Consumer;

@FunctionalInterface
public interface RepositorySource {
  void loadPacks(Consumer<Pack> paramConsumer);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\packs\repository\RepositorySource.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */