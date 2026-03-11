package net.minecraft.core;

import net.minecraft.data.worldgen.BootstapContext;

@FunctionalInterface
public interface RegistryBootstrap<T> {
  void run(BootstapContext<T> paramBootstapContext);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\RegistrySetBuilder$RegistryBootstrap.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */