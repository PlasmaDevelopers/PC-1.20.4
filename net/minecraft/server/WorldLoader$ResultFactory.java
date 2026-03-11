package net.minecraft.server;

import net.minecraft.core.LayeredRegistryAccess;
import net.minecraft.server.packs.resources.CloseableResourceManager;

@FunctionalInterface
public interface ResultFactory<D, R> {
  R create(CloseableResourceManager paramCloseableResourceManager, ReloadableServerResources paramReloadableServerResources, LayeredRegistryAccess<RegistryLayer> paramLayeredRegistryAccess, D paramD);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\WorldLoader$ResultFactory.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */