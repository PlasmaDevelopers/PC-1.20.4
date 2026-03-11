package net.minecraft.core;

import com.mojang.serialization.Lifecycle;
import net.minecraft.resources.ResourceKey;

public interface WritableRegistry<T> extends Registry<T> {
  Holder.Reference<T> register(ResourceKey<T> paramResourceKey, T paramT, Lifecycle paramLifecycle);
  
  boolean isEmpty();
  
  HolderGetter<T> createRegistrationLookup();
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\WritableRegistry.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */