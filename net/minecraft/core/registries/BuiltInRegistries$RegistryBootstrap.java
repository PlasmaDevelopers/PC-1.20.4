package net.minecraft.core.registries;

import net.minecraft.core.Registry;

@FunctionalInterface
interface RegistryBootstrap<T> {
  T run(Registry<T> paramRegistry);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\registries\BuiltInRegistries$RegistryBootstrap.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */