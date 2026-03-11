package net.minecraft.resources;

import java.util.Optional;
import net.minecraft.core.Registry;

public interface RegistryInfoLookup {
  <T> Optional<RegistryOps.RegistryInfo<T>> lookup(ResourceKey<? extends Registry<? extends T>> paramResourceKey);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\resources\RegistryOps$RegistryInfoLookup.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */